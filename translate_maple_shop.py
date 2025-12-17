
import os

filepath = "src/objects/shop/MapleShop.java"

with open(filepath, 'r', encoding='utf-8') as f:
    content = f.read()

replacements = [
    # Internal logging (Korean -> English)
    ('sb.append("상์   아이템 재구매 시도 (캐릭터 : ");', 'sb.append("Shop Item Rebuy Attempt (Character : ");'),
    ('sb.append(", 계์ • : ");', 'sb.append(", Account : ");'),
    ('sb.append(", 상์  ID : ");', 'sb.append(", Shop ID : ");'),
    ('sb.append(", 아이템 : ");', 'sb.append(", Item : ");'),
    ('sb.append("개, 가격 : ");', 'sb.append("Qty, Price : ");'),
    ('sb.append(" 메소)");', 'sb.append(" Meso)");'),
    ('sb.append("상์   아이템 구매 시도 (캐릭터 : ");', 'sb.append("Shop Item Buy Attempt (Character : ");'),
    ('sb.append(" 포인트)");', 'sb.append(" Point)");'),
    ('sb.append("상์  에 아이템 판매 시도 (캐릭터 : ");', 'sb.append("Shop Item Sell Attempt (Character : ");'),
    ('sb.append("(์ •보 : ");', 'sb.append("(Info : ");'),
    # Thai Mojibake
    ('ต้องการยȷี่สูงกว่านี้', 'ต้องการยศที่สูงกว่านี้'),
    # Fallback to be safe
    ('ยȷี่', 'ยศที่')
]

for old, new_str in replacements:
    content = content.replace(old, new_str)

with open(filepath, 'w', encoding='utf-8') as f:
    f.write(content)

print(f"Translated {filepath}")
