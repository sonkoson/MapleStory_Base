import os
import re

def count_korean_chars(file_path):
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()
        
        # Check for Mojibake (too many ????)
        if content.count('????') > 5:
            return -1 # Mark as Mojibake/Skip
            
        korean_chars = re.findall(r'[\uac00-\ud7a3]', content)
        return len(korean_chars)
    except Exception as e:
        return 0

def get_next_batch(start_dir, batch_size=40):
    korean_files = []
    
    for root, dirs, files in os.walk(start_dir):
        for file in files:
            if file.endswith(".js"):
                file_path = os.path.join(root, file)
                count = count_korean_chars(file_path)
                if count > 0:
                    korean_files.append((file_path, count))
    
    # Sort by count descending
    korean_files.sort(key=lambda x: x[1], reverse=True)
    
    return korean_files[:batch_size]

if __name__ == "__main__":
    base_dir = r"c:\Users\sonku\OneDrive\เอกสาร\GitHub\MapleStory_Base\scripts"
    batch = get_next_batch(base_dir, 40)
    
    print(f"Found {len(batch)} files with Korean content.")
    for f, c in batch:
        print(f"{c} | {f}")
