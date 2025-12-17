
import os

root_dir = 'src/objects/fields/child'
output_path = 'fields_child_analysis.txt'

with open(output_path, 'w', encoding='utf-8') as out:
    for root, dirs, files in os.walk(root_dir):
        for file in files:
            if file.endswith(".java"):
                file_path = os.path.join(root, file)
                try:
                    with open(file_path, 'r', encoding='utf-8') as f:
                        lines = f.readlines()
                        for i, line in enumerate(lines):
                            if any(0xAC00 <= ord(c) <= 0xD7A3 for c in line) or any(0x0E00 <= ord(c) <= 0x0E7F for c in line):
                               out.write(f"{file_path}:{i+1}: {line}")
                except Exception as e:
                    print(f"Error reading {file_path}: {e}")
print(f"Analysis written to {output_path}")
