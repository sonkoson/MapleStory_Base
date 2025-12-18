
import os
import re
import json

SEARCH_DIR = "scripts"
OUTPUT_FILE = "korean_items.json"

# Regex for strings (single or double quoted, handling escapes)
STRING_REGEX = re.compile(r'(["\'])((?:(?=(\\?))\3.)*?)\1')
# Regex for single line comments
COMMENT_SINGLE = re.compile(r'//(.*)')
# Regex for multi line comments
COMMENT_MULTI = re.compile(r'/\*([\s\S]*?)\*/')
# Regex for Korean char
KOREAN_CHAR = re.compile(r'[가-힣]')

unique_items = {}

print("Scanning for full Korean strings and comments...")

for root, dirs, files in os.walk(SEARCH_DIR):
    for file in files:
        if not file.endswith(".js"):
            continue
        path = os.path.join(root, file)
        
        try:
            with open(path, 'r', encoding='utf-8') as f:
                content = f.read()
        except Exception as e:
            print(f"Skipping {path}: {e}")
            continue

        # finding strings
        for match in STRING_REGEX.finditer(content):
            full_str = match.group(0) # "content"
            inner_str = match.group(2)
            if KOREAN_CHAR.search(inner_str):
                # Detected Korean in string
                if full_str not in unique_items:
                    unique_items[full_str] = {"type": "string", "count": 1}
                else:
                    unique_items[full_str]["count"] += 1

        # finding comments (simplified, might overlap with strings if not careful, but okay for extraction)
        # We strip strings first to find comments/identifiers? 
        # Actually, let's just grab them. If a comment looks like a string inside code, it's fine.
        
        for match in COMMENT_SINGLE.finditer(content):
            c_content = match.group(1)
            if KOREAN_CHAR.search(c_content):
                full_match = match.group(0)
                if full_match not in unique_items:
                    unique_items[full_match] = {"type": "comment", "count": 1}
        
        for match in COMMENT_MULTI.finditer(content):
            c_content = match.group(1)
            if KOREAN_CHAR.search(c_content):
                full_match = match.group(0)
                if full_match not in unique_items:
                    unique_items[full_match] = {"type": "comment", "count": 1}

# Convert to list
results = []
for k, v in unique_items.items():
    results.append({"original": k, "type": v["type"], "count": v["count"]})

# Sort by count desc
results.sort(key=lambda x: x["count"], reverse=True)

print(f"Found {len(results)} unique items containing Korean.")

with open(OUTPUT_FILE, 'w', encoding='utf-8') as f:
    json.dump(results, f, ensure_ascii=False, indent=2)
