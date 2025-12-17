
import os

def process_file(file_path, replacements):
    try:
        if not os.path.exists(file_path):
             print(f"File not found: {file_path}")
             return
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()

        for old, new in replacements:
            content = content.replace(old, new)

        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"Processed {file_path}")
    except Exception as e:
        print(f"Error processing {file_path}: {e}")

# AutobanManager.java
replacements_ab = [
    ('StringBuilder("오ํ† 밴 (name : ");', 'StringBuilder("Autoban (name : ");'),
    ('.append(") 사์œ  : ");', '.append(") Reason : ");')
]
process_file('src/objects/utils/AutobanManager.java', replacements_ab)
