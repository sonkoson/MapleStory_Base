
import os
import sys

# Define mapping to recover bytes
def char_to_byte(c):
    # Standard ASCII
    if ord(c) < 0x80:
        return ord(c)
    
    # Common TIS-620 mapping
    if 0x0E01 <= ord(c) <= 0x0E5B:
        return ord(c) - 0x0E00 + 0xA0
        
    if ord(c) == 0x0E40: return 0xE0
    if ord(c) == 0x0E41: return 0xE1
    if ord(c) == 0x0E42: return 0xE2
    if ord(c) == 0x0E43: return 0xE3
    if ord(c) == 0x0E44: return 0xE4
    
    # CP1252 recovery
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

def fix_file(filepath):
    try:
        with open(filepath, 'r', encoding='utf-8') as f:
            content = f.read()
    except:
        return

    recovered_ba = bytearray()
    
    i = 0
    if "NPCConversationManager.java" in filepath:
        print(f"Checking {filepath}...")
    
    while i < len(content):
        c = content[i]
        b = char_to_byte(c)
        
        if b != -1:
            if b == 0x20:
                if len(recovered_ba) >= 2:
                    if recovered_ba[-2] == 0xE0 and recovered_ba[-1] == 0xB8:
                        b = 0x81 # Recover ก
            recovered_ba.append(b)
        else:
            recovered_ba.extend(c.encode('utf-8'))
        i += 1
        
    try:
        fixed_text = recovered_ba.decode('utf-8')
        if "NPCConversationManager.java" in filepath:
             print("Decoded snippet around 'Guild':")
             idx = fixed_text.find("กิลด์")
             if idx != -1:
                 print(fixed_text[idx:idx+20])
             else:
                 print("Could not find 'กิลด์'")
                 
        if "กิลด์" in fixed_text or "คุณ" in fixed_text or "ได้รับ" in fixed_text:
            if fixed_text != content:
                print(f"Fixing {filepath}")
                with open(filepath, 'w', encoding='utf-8') as f:
                    f.write(fixed_text)
                return True
    except Exception as e:
        if "NPCConversationManager.java" in filepath:
            print(f"Decode failed: {e}")

def scan_and_fix(root_dir):
    for root, dirs, files in os.walk(root_dir):
        for file in files:
            if file.endswith(".java"):
                fix_file(os.path.join(root, file))

scan_and_fix("src")
