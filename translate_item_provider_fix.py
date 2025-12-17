
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

# MapleItemInformationProvider.java
replacements_miip = [
    ('"จำนวนอัพเกôไม่ถูกหักออกเนื่องจากผลของ Scroll"', '"จำนวนอัพเกรดไม่ถูกหักออกเนื่องจากผลของ Scroll"'),
    ('"[Error] คำนǳโอกาสสำเร็จของ Scroll ล้มเหลว"', '"[Error] คำนวณโอกาสสำเร็จของ Scroll ล้มเหลว"'),
    ('"놀라운"', '"Incredible"'),
    ('"긍정의"', '"Chaos of Goodness"'),
    ('"긍์ •의"', '"Chaos of Goodness"'),
    ('" 만큼 증가하여 "', '" increased by "'),
    ('"%로 ์  용"', '"% applied"'),
    ('"주문서 성공확๋ฅ  얻기 실패로 얻은 주문서"', '"Scroll returned due to calculation error"')
]
process_file('src/objects/item/MapleItemInformationProvider.java', replacements_miip)
