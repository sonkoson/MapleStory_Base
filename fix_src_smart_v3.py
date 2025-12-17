
import os
import sys

def char_to_byte(c):
    if ord(c) < 0x80: return ord(c)
    if 0x0E01 <= ord(c) <= 0x0E5B: return ord(c) - 0x0E00 + 0xA0
    if ord(c) == 0x0E40: return 0xE0
    if ord(c) == 0x0E41: return 0xE1
    if ord(c) == 0x0E42: return 0xE2
    if ord(c) == 0x0E43: return 0xE3
    if ord(c) == 0x0E44: return 0xE4
    
    cp1252_map = {
        '\u20AC': 0x80, '\u201A': 0x82, '\u0192': 0x83, '\u201E': 0x84, 
        '\u2026': 0x85, '\u2020': 0x86, '\u2021': 0x87, '\u02C6': 0x88, 
        '\u2030': 0x89, '\u0160': 0x8A, '\u2039': 0x8B, '\u0152': 0x8C, 
        '\u017D': 0x8E, '\u2018': 0x91, '\u2019': 0x92, '\u201C': 0x93, 
        '\u201D': 0x94, '\u2022': 0x95, '\u2013': 0x96, '\u2014': 0x97, 
        '\u02DC': 0x98, '\u2122': 0x99, '\u0161': 0x9A, '\u203A': 0x9B, 
        '\u0153': 0x9C, '\u017E': 0x9E, '\u0178': 0x9F,
    }
    if c in cp1252_map: return cp1252_map[c]
    return -1

def smart_decode(content, debug=False):
    res = []
    i = 0
    changes = 0
    while i < len(content):
        c = content[i]
        b = char_to_byte(c)
        
        if b != -1 and b < 0x80:
            res.append(c)
            i += 1
            continue
            
        if b != -1 and (b >= 0xC0):
            needed = 0
            if (b & 0xE0) == 0xC0: needed = 1
            elif (b & 0xF0) == 0xE0: needed = 2
            elif (b & 0xF8) == 0xF0: needed = 3
            
            if i + needed < len(content):
                bytes_seq = bytearray()
                bytes_seq.append(b)
                valid_seq = True
                chars_consumed = 1
                
                for k in range(needed):
                    next_c = content[i + 1 + k]
                    next_b = char_to_byte(next_c)
                    
                    if next_b == 0x20:
                         if (0x81 & 0xC0) == 0x80: 
                             next_b = 0x81
                             
                    if next_b == -1 or (next_b & 0xC0) != 0x80:
                        valid_seq = False
                        break
                    bytes_seq.append(next_b)
                    chars_consumed += 1
                
                if valid_seq:
                    try:
                        decoded = bytes_seq.decode('utf-8')
                        res.append(decoded)
                        i += chars_consumed
                        changes += 1
                        continue
                    except:
                        pass
        
        res.append(c)
        i += 1
        
    return "".join(res), changes

def fix_file(filepath):
    try:
        with open(filepath, 'r', encoding='utf-8') as f:
            content = f.read()
    except:
        return

    debug = False
    if "NPCConversationManager" in filepath:
        debug = True
        print(f"Scanning {filepath} len={len(content)}")

    fixed, changes = smart_decode(content, debug)
    
    if changes > 0:
        if debug:
            print(f"Made {changes} replacements")
            idx = content.find("buffGuil")
            if idx != -1:
                # Find corresponding index in fixed text is hard because lengths changed.
                # Search for "buffGuild" in fixed
                fidx = fixed.find("buffGuild")
                if fidx != -1:
                    print("Original Context:")
                    print(content[idx:idx+500])
                    print("Fixed Context:")
                    print(fixed[fidx:fidx+500])

        if "กิลด์" in fixed or "คุณ" in fixed or "ได้รับ" in fixed or "ไม่สามารถ" in fixed or "บัฟ" in fixed:
            print(f"Fixing {filepath} (Changes: {changes})")
            with open(filepath, 'w', encoding='utf-8') as f:
                f.write(fixed)
        elif debug:
             print("Sanity failed.")

def scan_and_fix(root_dir):
    for root, dirs, files in os.walk(root_dir):
        for file in files:
            if file.endswith(".java"):
                fix_file(os.path.join(root, file))

scan_and_fix("src")
