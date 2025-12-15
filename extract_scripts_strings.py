import os
import re
import json

root_dir = r"c:\Users\sonku\OneDrive\เอกสาร\GitHub\MapleStory_Base\scripts"
korean_regex = re.compile(r'[가-힣]+')

extracted = {}

for subdir, dirs, files in os.walk(root_dir):
    for file in files:
        if file.endswith(".js"):
            path = os.path.join(subdir, file)
            try:
                with open(path, 'r', encoding='utf-8') as f:
                    content = f.read()
                    matches = korean_regex.findall(content)
                    if matches:
                        # Store unique strings per file or globally?
                        # Globally is better to see volume
                        for m in matches:
                            extracted[m] = extracted.get(m, 0) + 1
            except Exception as e:
                # Try cp949 just in case
                try:
                    with open(path, 'r', encoding='cp949') as f:
                        content = f.read()
                        matches = korean_regex.findall(content)
                        for m in matches:
                            extracted[m] = extracted.get(m, 0) + 1
                except:
                    pass

sorted_extracted = dict(sorted(extracted.items(), key=lambda item: item[1], reverse=True))

with open('scripts_korean_strings.json', 'w', encoding='utf-8') as f:
    json.dump(sorted_extracted, f, ensure_ascii=False, indent=4)

print(f"Extracted {len(extracted)} unique Korean strings.")
