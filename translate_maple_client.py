
import re

file_path = 'src/objects/users/MapleClient.java'

with open(file_path, 'r', encoding='utf-8') as f:
    content = f.read()

# Fix Mojibake: กรسา -> กรุณา
content = content.replace('กรسา', 'กรุณา')

# Fix other potential Mojibake (common patterns)
content = content.replace('แล้ǁ', 'แล้ว')
content = content.replace('สามาö', 'สามารถ')
content = content.replace('คس', 'คุณ')
content = content.replace('ไมไ่ด้', 'ไม่ได้')

with open(file_path, 'w', encoding='utf-8') as f:
    f.write(content)

print(f"Processed {file_path}")
