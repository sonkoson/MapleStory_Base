
import os
import re

# Specific Mojibake characters found: Ձ (Armenian), ҡ (Cyrillic), Թ (Armenian)
# Expanding to a broader range of "Suspicious" high-bit characters that shouldn't appear in Thai strings
# Thai range: \u0E00-\u0E7F
# suspicious: \u0400-\u04FF (Cyrillic), \u0530-\u058F (Armenian), \u0080-\u00FF (Latin-1 Supplement/Windows-1252 high bits often mapped incorrectly)

def scan_for_mix(root_dir):
    suspicious_pattern = re.compile(r'[\u0E00-\u0E7F].*[^\u0000-\u007F\u0E00-\u0E7F]') 
    # Thai char followed by something that is NOT ASCII and NOT Thai
    # This might match valid mixed language text if it's e.g. Thai + Japanese, but here we are looking for Mojibake.
    # Actually, simpler: Look for Thai AND (Armenian OR Cyrillic OR Generic Currency Symbols) in the same line.
    
    specific_mojibake = re.compile(r'[\u0530-\u058F\u0400-\u04FF]')

    logging = []

    for root, dirs, files in os.walk(root_dir):
        for file in files:
            if file.endswith(".java"):
                path = os.path.join(root, file)
                try:
                    with open(path, 'r', encoding='utf-8', errors='replace') as f:
                        lines = f.readlines()
                    
                    for i, line in enumerate(lines):
                        if specific_mojibake.search(line) and re.search(r'[\u0E00-\u0E7F]', line):
                             logging.append(f"{path}:{i+1}: {line.strip()}")
                except Exception as e:
                    pass
                    
    return logging

if __name__ == "__main__":
    results = scan_for_mix("src")
    with open("suspicious_thai_mix.txt", "w", encoding="utf-8") as f:
        for r in results:
            f.write(r + "\n")
            print(r)
