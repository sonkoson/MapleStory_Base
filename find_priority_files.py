
import os
import re

SEARCH_DIR = "scripts"
# Regex to match Korean characters
KOREAN_REGEX = re.compile(r'[가-힣]+')

file_scores = []

for root, dirs, files in os.walk(SEARCH_DIR):
    for file in files:
        if not file.endswith(".js"):
            continue
        path = os.path.join(root, file)
        try:
            with open(path, 'r', encoding='utf-8') as f:
                content = f.read()
            
            # Count matches
            matches = KOREAN_REGEX.findall(content)
            if matches:
                # Score is simply the number of Korean sequences or characters
                score = sum(len(m) for m in matches)
                file_scores.append({"path": path, "score": score, "count": len(matches)})
        except Exception as e:
            pass

# Sort by score descending
file_scores.sort(key=lambda x: x["score"], reverse=True)

print("Top 40 files with Korean content:")
for item in file_scores[:40]:
    print(f"{item['score']} chars | {item['count']} chunks | {item['path']}")
