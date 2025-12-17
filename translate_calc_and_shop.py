
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

# MapleShop.java
replacements_shop = [
    ('"상์   아이템 재구매 시도"', '"Shop Item Rebuy Attempt"'),
    ('"상์  ID"', '"Shop ID"'),
    ('"상์   아이템 구매 시도"', '"Shop Item Buy Attempt"'),
    ('"상์  에 아이템 판매 시도"', '"Shop Item Sell Attempt"'),
    ('" 메소, "', '" Meso, "'),
    ('"개, "', '"Qty, "'),
    ('"상์  "', '"Shop "'), # General cleanup if "상์  " appears alone or in other contexts
    ('"가격 : "', '"Price : "')
]
process_file('src/objects/shop/MapleShop.java', replacements_shop)

# CalcDamageUtil.java
replacements_calc = [
    ('"마๋ ฅ"', '"Magic Attack"'),
    ('"공격๋ ฅ"', '"Attack Power"'),
    ('"영웅의 메아리"', '"Echo of Hero"'),
    ('"익스클루시브 스ํŽ "', '"Exclusive Spell"'),
    ('"인ํƒ 시브 타임"', '"Intensive Time"'),
    ('"쓸만한 어드밴스드 블๋ ˆ스"', '"Decent Advanced Bless"')
]
process_file('src/objects/users/CalcDamageUtil.java', replacements_calc)
