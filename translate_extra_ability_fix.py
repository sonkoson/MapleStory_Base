
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

# ExtraAbilityGrade.java
replacements_grade = [
    ('"๋ ˆ어"', '"Rare"'),
    ('"์œ 니크"', '"Unique"'),
    ('"๋ ˆ์ „드리"', '"Legendary"')
]
process_file('src/objects/users/extra/ExtraAbilityGrade.java', replacements_grade)

# ExtraAbilityOption.java
replacements_option = [
    ('"부활 시 무์   시간 %d초 증가"', '"Invincibility duration +%d sec upon revival"')
]
process_file('src/objects/users/extra/ExtraAbilityOption.java', replacements_option)
