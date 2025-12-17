
import os

filepath = "src/objects/users/MapleCharacter.java"

with open(filepath, 'r', encoding='utf-8') as f:
    content = f.read()

replacements = [
    ('public void 아이템지급', 'public void giveItemStats'),
    ('아이템지급(', 'giveItemStats('),
    ('"언랭크"', '"Unranked"'),
    ('ปԴใช้งาน Megaphone', 'ปิดใช้งาน Megaphone'),
    ('"[명사수] 사수로 ์ „직하였습니다."', '"[Sharpshooter] เลื่อนขั้นเป็น Marksman เรียบร้อยแล้ว"'),
    ('"[(?s).*] 에인션트 아처로 ์ „직하였습니다."', '"[Power of Curse and Ancient] เลื่อนขั้นเป็น Ancient Archer เรียบร้อยแล้ว"'),
    # Fallback for the complex string if regex fails (simple string replace)
    ('"[์ €주와 ๊ณ 대의 힘] 에인션트 아처로 ์ „직하였습니다."', '"[Power of Curse and Ancient] เลื่อนขั้นเป็น Ancient Archer เรียบร้อยแล้ว"')
]

for old, new_str in replacements:
    content = content.replace(old, new_str)

with open(filepath, 'w', encoding='utf-8') as f:
    f.write(content)

print(f"Translated {filepath}")
