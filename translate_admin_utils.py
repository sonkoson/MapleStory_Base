
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

# Process bitFalg.java (BitFlag.java)
replacements_bitflag = [
    ('"총 크기:"', '"Total Size:"'),
    ('"더미 int * 2 ์ œ거크기:"', '"Dummy int * 2 Removed Size:"'),
    ('"포지션: "', '"Position: "'),
    ('"포지션 :"', '"Position :"'),
    ('"버프마스크: "', '"Buff Mask: "'),
    ('"이름 : "', '"Name : "'),
    ('"플래그 : "', '"Flag : "'),
    ('"비트 플래그 : "', '"Bit Flag : "'),
    ('"비트마스킹 이후 크기: "', '"Size after bitmasking: "')
]
process_file('src/objects/utils/bitFalg.java', replacements_bitflag)

# Process AutobanManager.java
replacements_autoban = [
    ('"[แจ้งเตือน] GM ไม่อยู่ในเงื่อนไขการแบนอѵโนมѵԁ: "', '"[Notice] GM is not subject to automatic ban: "'), # Fixing mojibake and translating context if appropriate, or just fixing mojibake
    ('อѵโนมѵԁ', 'อัตโนมัติ'), # Fix mojibake
    ('สาเ˵ุ', 'สาเหตุ'), # Fix mojibake
    ('"오ํ† 밴 (name : "', '"Auto Ban (name : "'),
    ('") 사์œ  : "', '") Reason : "'),
    ('" ถูกแบน (สาเ˵ุ: "', '" was banned (Reason: ')
]
process_file('src/objects/utils/AutobanManager.java', replacements_autoban)

# Process AdminClient.java
replacements_admin = [
    ('[일반 Ch.', '[General Ch.'),
    ('ch = "20세 이상";', 'ch = "Over 20";'),
    ('"밴 사์œ : "', '"Ban Reason: "') # Fix Mojibake/Korean
]
process_file('src/objects/utils/AdminClient.java', replacements_admin)
