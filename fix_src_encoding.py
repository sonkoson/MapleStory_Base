
import os
import sys

# Define mapping to recover bytes
def char_to_byte(c):
    # Standard ASCII
    if ord(c) < 0x80:
        return ord(c)
    
    # Common TIS-620 mapping
    # 0xA1 (ก) to 0xFB (๙) maps to U+0E01 - U+0E5B
    if 0x0E01 <= ord(c) <= 0x0E5B:
        return ord(c) - 0x0E00 + 0xA0
        
    # Tone marks & others 0xE0-0xFF in TIS-620 map to U+0Exx ?
    # U+0E40 (เ) -> 0xE0
    if ord(c) == 0x0E40: return 0xE0
    if ord(c) == 0x0E41: return 0xE1
    if ord(c) == 0x0E42: return 0xE2
    if ord(c) == 0x0E43: return 0xE3
    if ord(c) == 0x0E44: return 0xE4
    
    # 0x80-0x9F Range recovery (CP1252 artifacts)
    cp1252_map = {
        '\u20AC': 0x80, # €
        # 0x81 Undefined -> Mapped to Space?
        '\u201A': 0x82, # ‚
        '\u0192': 0x83, # ƒ
        '\u201E': 0x84, # „
        '\u2026': 0x85, # …
        '\u2020': 0x86, # †
        '\u2021': 0x87, # ‡
        '\u02C6': 0x88, # ˆ
        '\u2030': 0x89, # ‰
        '\u0160': 0x8A, # Š
        '\u2039': 0x8B, # ‹
        '\u0152': 0x8C, # Œ 
        # 0x8D Undefined
        '\u017D': 0x8E, # Ž
        # 0x8F Undefined
        # 0x90 Undefined
        '\u2018': 0x91, # ‘
        '\u2019': 0x92, # ’
        '\u201C': 0x93, # “
        '\u201D': 0x94, # ”
        '\u2022': 0x95, # •
        '\u2013': 0x96, # –
        '\u2014': 0x97, # —
        '\u02DC': 0x98, # ˜
        '\u2122': 0x99, # ™
        '\u0161': 0x9A, # š
        '\u203A': 0x9B, # ›
        '\u0153': 0x9C, # œ
        # 0x9D Undefined
        '\u017E': 0x9E, # ž
        '\u0178': 0x9F, # Ÿ
    }
    
    if c in cp1252_map:
        return cp1252_map[c]
        
    return -1

def fix_file(filepath):
    try:
        with open(filepath, 'r', encoding='utf-8') as f:
            content = f.read()
    except UnicodeDecodeError:
        # If it's not UTF-8, it might already be fixed or binary
        return False

    recovered_ba = bytearray()
    changed = False
    
    # Heuristic check: does it contain TIS-620 chars?
    has_thai_chars = any(0x0E00 <= ord(c) <= 0x0E7F for c in content)
    if not has_thai_chars:
        return False

    i = 0
    while i < len(content):
        c = content[i]
        b = char_to_byte(c)
        
        if b != -1:
            # Check for Space -> 0x81/0x8D/0x8F/0x90/0x9D recovery
            if b == 0x20:
                # Look back at previous 2 bytes to see if we satisfy UTF-8 prefix
                if len(recovered_ba) >= 2:
                    b1 = recovered_ba[-2]
                    b2 = recovered_ba[-1]
                    
                    # Target: 0x81 (ก - E0 B8 81)
                    if b1 == 0xE0 and b2 == 0xB8:
                        b = 0x81 # Recover ก
                    
                    # Target: 0x8D (ญ - E0 B8 8D)
                    elif b1 == 0xE0 and b2 == 0xB8 and False: # Rare, assume ก for now unless context proves otherwise
                        pass 
                    
                    # Target: 0x8F (ฏ - E0 B8 8F)
                    # Target: 0x90 (ฐ - E0 B8 90)
                    # Target: 0x9D (ฝ - E0 B8 9D)
            
            recovered_ba.append(b)
        else:
            # Fallback: encode as utf-8 and append? 
            # If we encounter a character we can't map back (e.g. valid Korean or English), 
            # we should just use its standard utf-8 bytes.
            # But wait, if we are in "Mojibake string", even English chars might be correct as-is?
            # Yes, if input is "Hello", char_to_byte returns ASCII.
            # If input is "홍", it returns -1.
            # If we mix methods, we get garbage.
            # BUT, the file is likely "valid UTF-8" representing "TIS-620 bytes".
            # If we see a character that IS NOT a TIS-620 byte representation, maybe it's just a regular character that was survived?
            # e.g. Korean text.
            # If we have "홍", ord is 0xD64D.
            # If we append utf-8 bytes of "홍" (ED 99 8D), we are adding 3 bytes.
            # Then we decode the whole bytearray as utf-8 at the end.
            
            # Use raw utf-8 bytes for unmapped chars
            recovered_ba.extend(c.encode('utf-8'))
            
        i += 1
        
    try:
        fixed_text = recovered_ba.decode('utf-8')
        # Sanity check: does it look like Thai?
        # Check for common keywords "กิลด์" (Guild), "คุณ" (You)
        if "กิลด์" in fixed_text or "คุณ" in fixed_text or "ได้รับ" in fixed_text:
            if fixed_text != content:
                print(f"Fixing {filepath}")
                with open(filepath, 'w', encoding='utf-8') as f:
                    f.write(fixed_text)
                return True
    except Exception as e:
        # print(f"Failed to decode recovered bytes for {filepath}: {e}")
        pass
        
    return False

def scan_and_fix(root_dir):
    for root, dirs, files in os.walk(root_dir):
        for file in files:
            if file.endswith(".java"):
                fix_file(os.path.join(root, file))

scan_and_fix("src")
