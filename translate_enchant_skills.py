
import re
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

# Process TeleportAttackData_DoubleArray.java
replacements_ta_double = [
    ('"TeleportAttackData_DoubleArray 오류 (데미지 파싱 잘못됨)\\r\\n"', '"TeleportAttackData_DoubleArray Error (Damage parsing incorrect)\\r\\n"')
]
process_file('src/objects/users/skills/TeleportAttackData_DoubleArray.java', replacements_ta_double)

# Process TeleportAttackData_TriArray.java
replacements_ta_tri = [
    ('"TeleportAttackData_TriArray 오류 (데미지 파싱 잘못됨)\\r\\n"', '"TeleportAttackData_TriArray Error (Damage parsing incorrect)\\r\\n"')
]
process_file('src/objects/users/skills/TeleportAttackData_TriArray.java', replacements_ta_tri)

# Process EquipEnchantScroll.java
replacements_ees = [
    ('" 주문서"', '" Scroll"'),
    ('"이노센트 주문서 100%"', '"Innocent Scroll 100%"'),
    ('"아크 이노센트 주문서 100%"', '"Ark Innocent Scroll 100%"'),
    ('"순백의 주문서 100%"', '"Clean Slate Scroll 100%"'),
    ('contains("성")', 'contains("Star")'),
    ('+ "성"', '+ "Star"')
]
process_file('src/objects/users/enchant/EquipEnchantScroll.java', replacements_ees)

# Process StarForceHyperUpgrade.java
# Only translating "강" if it's safe. It's in a DBConfig.isGanglim block, which seems specific.
# "강" usually means enhancement level. I'll translate it to "Star" to match other parts if they are consistent, or "Enhancement".
# Given EquipEnchantScroll uses "Star" (originally "성"), "강" likely serves a similar purpose in a different server mode ("Ganglim").
# I will strictly translate the string literal.
replacements_sfhu = [
    ('.contains("강")', '.contains("Star")'),
    ('.split("강")', '.split("Star")')
]
process_file('src/objects/users/enchant/StarForceHyperUpgrade.java', replacements_sfhu)
