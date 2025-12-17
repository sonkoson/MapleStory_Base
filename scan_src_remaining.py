
import os
import re

def scan_src_for_issues(root_dir):
    # Pattern for Korean characters
    korean_pattern = re.compile(r'[가-힣]')
    
    # Pattern for suspicious Mojibake (Simplified: Thai chars mixed with specific high-bit chars)
    # We look for lines containing Thai AND (Cyrillic OR Armenian OR certain symbols)
    # This is a heuristic.
    thai_char = r'[\u0E00-\u0E7F]'
    suspicious_chars = r'[\u0400-\u04FF\u0530-\u058F\u0080-\u00FF]'
    
    # We will just look for Thai mixed with specific chars that we know are bad from previous turns:
    # Ձ (Armenian Capital Letter Ja) \u053Ձ
    # ҡ (Cyrillic Small Letter Qa) \u04A1
    # Թ (Armenian Capital Letter To) \u0539
    # Ŵ (Latin Capital Letter W with Circumflex) \u0174
    # ʴ (Latin Small Letter Script G) \u02B4 ??? No, from earlier logs: 'ʴ' was used.
    # Let's use a known bad set matching the user's previous examples.
    
    bad_markers = ['Ձ', 'ҡ', 'Թ', 'Ŵ', '辺', '蹁', 'à¸', 'à¹', 'เนŒ', 'เนŠ', 'เน\xa0']

    found_issues = []

    print(f"Scanning {root_dir} for Korean and suspicious Thai Mojibake...")

    for root, dirs, files in os.walk(root_dir):
        for file in files:
            if file.endswith(".java") or file.endswith(".js"):
                file_path = os.path.join(root, file)
                try:
                    with open(file_path, 'r', encoding='utf-8', errors='replace') as f:
                        lines = f.readlines()
                    
                    for i, line in enumerate(lines):
                        line_stripped = line.strip()
                        # Check Korean
                        if korean_pattern.search(line_stripped):
                            found_issues.append(f"[KOREAN] {file_path}:{i+1}: {line_stripped}")
                            continue # Don't double report if both
                        
                        # Check Mojibake markers
                        for marker in bad_markers:
                            if marker in line_stripped and re.search(thai_char, line_stripped):
                                found_issues.append(f"[MOJIBAKE] {file_path}:{i+1}: {line_stripped}")
                                break
                                
                except Exception as e:
                    pass

    return found_issues

if __name__ == "__main__":
    results = scan_src_for_issues("src")
    
    if results:
        with open("scan_results_src.txt", "w", encoding="utf-8") as f:
            for r in results:
                f.write(r + "\n")
        print(f"Found {len(results)} issues. Saved to scan_results_src.txt")
        # Print first 20 for immediate feedback
        for r in results[:20]:
            print(r)
        if len(results) > 20:
            print(f"... and {len(results)-20} more.")
    else:
        print("No obvious issues found in src.")
