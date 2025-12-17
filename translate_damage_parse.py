
import re

file_path = 'src/objects/users/skills/DamageParse.java'

with open(file_path, 'r', encoding='utf-8') as f:
    content = f.read()

# Fix Korean strings
content = content.replace('sb.append("데미지 핵 사용 : ");', 'sb.append("Damage Hack Detected : ");')
content = content.replace('sb.append(", 스킬ID : ");', 'sb.append(", SkillID : ");')

with open(file_path, 'w', encoding='utf-8') as f:
    f.write(content)

print(f"Processed {file_path}")
