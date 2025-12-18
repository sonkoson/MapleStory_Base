
import os
import re
import json
import codecs

SEARCH_DIR = "scripts"
OUTPUT_STRINGS = "korean_strings.json"
OUTPUT_VARS = "korean_identifiers.json"
REPORT_FILE = "phase1_report.txt"

# Korean Unicode Range: \uAC00-\uD7A3 (Hangul Syllables)
# Also \u3130-\u318F (Hangul Compatibility Jamo)
KOREAN_REGEX = re.compile(r'[가-힣]+')

def try_read(path):
    with open(path, 'rb') as f:
        raw = f.read()
    
    encodings = ['utf-8', 'euc-kr', 'cp949', 'latin-1']
    for enc in encodings:
        try:
            return raw.decode(enc), enc
        except UnicodeDecodeError:
            continue
    return  raw.decode('utf-8', errors='ignore'), 'unknown'

unique_strings = set()
unique_identifiers = set()
files_processed = 0
files_converted = 0
files_with_korean = 0

print(f"Scanning directory: {SEARCH_DIR}")

for root, dirs, files in os.walk(SEARCH_DIR):
    for file in files:
        if not file.endswith(".js"):
            continue
            
        path = os.path.join(root, file)
        files_processed += 1
        
        content, encoding = try_read(path)
        
        # Phase 2: Normalize to UTF-8
        # Always write back as UTF-8 if it wasn't or if sure (to standardise)
        # Verify content didn't get corrupted roughly? No, relying on decode success.
        
        if encoding != 'utf-8' and encoding != 'unknown':
            files_converted += 1
            with open(path, 'w', encoding='utf-8') as f:
                f.write(content)
        
        # Scan for Korean
        lines = content.splitlines()
        has_korean = False
        for line in lines:
            matches = KOREAN_REGEX.findall(line)
            if matches:
                has_korean = True
                # Simple heuristic: if likely string or likely identifier?
                # We'll just dump all Korean sequences for now.
                # In JS, identifiers are often mixed, but string literals are "..." or '...'
                # We can't easily parse JS fully with regex, but we can capture the Korean parts.
                for m in matches:
                    unique_strings.add(m)
        
        if has_korean:
            files_with_korean += 1

print(f"Processed {files_processed} files.")
print(f"Converted {files_converted} files to UTF-8.")
print(f"Found {files_with_korean} files containing Korean characters.")
print(f"Unique Korean tokens found: {len(unique_strings)}")

with open(OUTPUT_STRINGS, 'w', encoding='utf-8') as f:
    json.dump(list(unique_strings), f, ensure_ascii=False, indent=2)

with open(REPORT_FILE, 'w', encoding='utf-8') as f:
    f.write(f"Files Processed: {files_processed}\n")
    f.write(f"Files Converted to UTF-8: {files_converted}\n")
    f.write(f"Files with Korean: {files_with_korean}\n")
    f.write(f"Unique Korean Tokens: {len(unique_strings)}\n")
