
import re

file_path = 'src/objects/item/MapleInventoryManipulator.java'

with open(file_path, 'r', encoding='utf-8') as f:
    content = f.read()

# Fix Mojibake: กรسา -> กรุณา
content = content.replace('กรسา', 'กรุณา')
content = content.replace('น้́1', 'น้อย 1')

# Fix mixed Mojibake/Korean strings
content = content.replace(') ์œ ์ € 닉네임 :', ') User Nickname :')
content = content.replace('sb.append("플๋ ˆ이어가 필드에 아이템 드롭함");', 'sb.append("Player dropped item on field");')

# General Thai Mojibake fixes just in case
content = content.replace('แล้ǁ', 'แล้ว')
content = content.replace('สามาö', 'สามารถ')
content = content.replace('คس', 'คุณ')

with open(file_path, 'w', encoding='utf-8') as f:
    f.write(content)

print(f"Processed {file_path}")
