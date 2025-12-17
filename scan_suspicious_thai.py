
import os
import re

def is_suspicious(line, file_path):
    # Pattern 1: UTF-8 Thai characters interpreted as Windows-1252 (e.g., à¸£, à¹, etc.)
    # typical lead bytes for Thai in UTF-8 are 0xE0 0xB8 (à¸) and 0xE0 0xB9 (à¹)
    if "à¸" in line or "à¹" in line:
        return "Possible UTF-8 Mojibake (à¸/à¹)"
        
    # Pattern 2: Thai characters mixed with Korean characters in the same string literal
    # We find quoted strings first
    strings = re.findall(r'"([^"]*)"', line)
    for s in strings:
        has_thai = bool(re.search(r'[\u0E00-\u0E7F]', s))
        has_korean = bool(re.search(r'[\uAC00-\uD7A3]', s))
        if has_thai and has_korean:
            return "Mixed Thai and Korean in string"
            
        # Pattern 3: Thai mixed with suspicious extended ASCII/Control chars
        # Often Mojibake looks like random symbols interspersed
        # Check for meaningful Thai but surrounded by garbage like 'Ã', '¹', '§', 'ª', '±' which are common TIS-620 mojibake in ISO-8859-1
        # Common TIS-620 Mojibake chars: ¢ £ ¤ ¥ ¦ § ¨ © ª « ¬ ...
        if has_thai:
            # If we see Thai chars, check if there are also high-bit chars that AREN'T Thai or common punctuation
            # This is a heuristic.
            suspicious_chars = re.findall(r'[^\u0000-\u007F\u0E00-\u0E7F]', s)
            if suspicious_chars:
                # Filter out some common legitimate unicode symbols if any
                # But generally, having unrelated high-bit chars next to Thai is suspicious
                return f"Thai mixed with unknown chars: {''.join(suspicious_chars[:5])}..."

    return None

def scan_directory(root_dir):
    print(f"Scanning {root_dir}...")
    issues_found = []
    
    for root, dirs, files in os.walk(root_dir):
        for file in files:
            if file.endswith(".java") or file.endswith(".js"):
                file_path = os.path.join(root, file)
                try:
                    with open(file_path, 'r', encoding='utf-8', errors='replace') as f:
                        lines = f.readlines()
                    
                    for i, line in enumerate(lines):
                        reason = is_suspicious(line, file_path)
                        if reason:
                            issues_found.append({
                                'file': file_path,
                                'line': i + 1,
                                'content': line.strip(),
                                'reason': reason
                            })
                except Exception as e:
                    print(f"Could not read {file_path}: {e}")

    return issues_found

if __name__ == "__main__":
    src_issues = scan_directory("src")
    
    # Also scan data/scripts since users sometimes edit those
    script_issues = scan_directory("data/scripts") 

    all_issues = src_issues + script_issues
    
    with open("suspicious_thai_scan.txt", "w", encoding="utf-8") as f:
        if not all_issues:
            f.write("No obvious suspicious Thai patterns found.\n")
        else:
            for issue in all_issues:
                f.write(f"File: {issue['file']}\n")
                f.write(f"Line {issue['line']}: {issue['content']}\n")
                f.write(f"Reason: {issue['reason']}\n")
                f.write("-" * 40 + "\n")
    
    print(f"Scan complete. Found {len(all_issues)} suspicious items. Output saved to suspicious_thai_scan.txt")
