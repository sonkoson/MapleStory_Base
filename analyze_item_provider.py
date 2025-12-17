
import os

file_path = 'src/objects/item/MapleItemInformationProvider.java'
output_path = 'item_provider_analysis.txt'

if os.path.exists(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        lines = f.readlines()
    
    with open(output_path, 'w', encoding='utf-8') as out:
        for i, line in enumerate(lines):
            # Korean range or Thai specific keywords or known Mojibake fragments
            # Using broader filter?
            # 0xAC00-0xD7A3 is Hangul Syllables
            # Thai is 0x0E00-0x0E7F
            if any(0xAC00 <= ord(c) <= 0xD7A3 for c in line) or any(0x0E00 <= ord(c) <= 0x0E7F for c in line):
               out.write(f"{i+1}: {line}")
    print(f"Analysis written to {output_path}")
else:
    print("File not found.")
