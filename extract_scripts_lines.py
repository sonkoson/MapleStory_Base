import os
import re
import json

root_dir = r"c:\Users\sonku\OneDrive\เอกสาร\GitHub\MapleStory_Base\scripts"
korean_regex = re.compile(r'[가-힣]+')

extracted_lines = {}

for subdir, dirs, files in os.walk(root_dir):
    for file in files:
        if file.endswith(".js"):
            path = os.path.join(subdir, file)
            try:
                with open(path, 'r', encoding='utf-8') as f:
                    lines = f.readlines()
                    for line in lines:
                        line = line.strip()
                        if korean_regex.search(line):
                            # Clean the line a bit? or keep as is?
                            # Keep as is for exact matching.
                            extracted_lines[line] = extracted_lines.get(line, 0) + 1
            except Exception as e:
                try:
                    with open(path, 'r', encoding='cp949') as f:
                        lines = f.readlines()
                        for line in lines:
                            line = line.strip()
                            if korean_regex.search(line):
                                extracted_lines[line] = extracted_lines.get(line, 0) + 1
                except:
                    pass

sorted_lines = dict(sorted(extracted_lines.items(), key=lambda item: item[1], reverse=True))

with open('scripts_korean_lines.json', 'w', encoding='utf-8') as f:
    json.dump(sorted_lines, f, ensure_ascii=False, indent=4)

print(f"Extracted {len(extracted_lines)} unique Korean lines.")
