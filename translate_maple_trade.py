
import os

filepath = "src/objects/users/MapleTrade.java"

with open(filepath, 'r', encoding='utf-8') as f:
    content = f.read()

replacements = [
    # General Logging
    ('sb.append("보낸 캐릭터 : ");', 'sb.append("Sender : ");'),
    ('sb.append("받은 캐릭터 : ");', 'sb.append("Receiver : ");'),
    ('sb.append("\r\n아이템(")', 'sb.append("\r\nItem(")'),
    ('sb.append("개");', 'sb.append("Qty");'),
    ('sb.append(", 메소 : ");', 'sb.append(", Meso : ");'),
    
    # Cancel Reasons
    ('sbx.append("취소 사์œ  : (");', 'sbx.append("Cancel Reason : (");'),
    ('sbx.append("보낸 캐릭터 : ");', 'sbx.append("Sender : ");'),
    ('sbx.append("받은 캐릭터 : ");', 'sbx.append("Receiver : ");'),
    ('sbx.append("\r\n아이템(")', 'sbx.append("\r\nItem(")'),
    ('sbx.append("개");', 'sbx.append("Qty");'),
    ('sbx.append(", 메소 : ");', 'sbx.append(", Meso : ");'),
    
    # Logic Strings (Reason construction)
    (' + "의 인벤ํ† 리 공간 부족, "', ' + "\'s Inventory Full, "'),
    (' + "이 거래 불가 아이템 거래 시도, "', ' + " attempted to trade untradable item, "'),
    ('"캐릭터 이름 \'" + chr.getName() + "\' 거래 취소"', '"Character \'" + chr.getName() + "\' Cancelled Trade"'),
    ('sb.append("취소 사์œ  : (");', 'sb.append("Cancel Reason : (");'), 
    ('sb.append("보낸 캐릭터 : ");', 'sb.append("Sender : ");'),
    ('sb.append("받은 캐릭터 : ");', 'sb.append("Receiver : ");'),
    ('sb.append("\r\n아이템(")', 'sb.append("\r\nItem(")'),
    
    # Null info
    ('sb.append("파트너 ์ •보 Null");', 'sb.append("Partner Info Null");'),
]

for old, new_str in replacements:
    content = content.replace(old, new_str)

with open(filepath, 'w', encoding='utf-8') as f:
    f.write(content)

print(f"Translated {filepath}")
