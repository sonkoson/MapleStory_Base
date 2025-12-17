
import os

filepath = "src/objects/item/MapleItemInformationProvider.java"

with open(filepath, 'r', encoding='utf-8') as f:
    content = f.read()

replacements = [
    ('boolean incredible = itemName.contains("놀라운");', 'boolean incredible = itemName.contains("놀라운") || itemName.contains("Incredible");'),
    ('boolean goodness = itemName.contains("긍์ •의");', 'boolean goodness = itemName.contains("긍정의") || itemName.contains("긍์ •의") || itemName.contains("Goodness");'),
    # Thai spacing fix
    ('สามารถใช้ได้กับอุปกรณ์เลเวล150', 'สามารถใช้ได้กับอุปกรณ์เลเวล 150'),
]

for old, new_str in replacements:
    content = content.replace(old, new_str)

with open(filepath, 'w', encoding='utf-8') as f:
    f.write(content)

print(f"Translated {filepath}")
