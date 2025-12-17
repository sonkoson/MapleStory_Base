
import os

# Define the mojibake replacements
replacements = [
    ('สามาö', 'สามารถ'),
    ('แล้ǁ', 'แล้ว'),
    ('ครั駁', 'ครั้ง'),
    ('เปԴ', 'เปิด'),
    ('ปรеู', 'ประตู'),
    ('อุปกó์', 'อุปกรณ์'),
    ('เลเวŁ', 'เลเวล'),
    ('หรื͵', 'หรือ'),
    ('มՁ', 'มี'),
    ('เกԴ', 'เกิด'),
    ('ข้อผԴพลҴ', 'ข้อผิดพลาด'),
    ('สาเ˵؁', 'สาเหตุ'),
    ('วѹนี้', 'วันนี้'),
    ('รางวัŁ', 'รางวัล'),
    ('หนǴที่', 'หน้าที่'),
    ('ถ้ҷ', 'ถ้า'),
    ('โจมตմ', 'โจมตี'),
    ('่ำกว่า', 'ต่ำกว่า'),
    ('เควสปŴปล่́', 'เควสปลดปล่อย'),
    ('มҁ', 'มา'),
    ('พื้นที่ล่ҁ', 'พื้นที่ล่า'),
    ('ไม่สามาö', 'ไม่สามารถ'), # Redundant but safe
    ('ว่ҡิŴ์', 'ว่ากิลด์'),   # From previous memory just in case
    ('คس', 'คุณ'),           # From previous memory
    ('ö', 'ถ'),             # Careful with this one, only if standalone? No, usually part of 'สามาö'
]

# List of files identified by grep
files_to_fix = [
    r"src/objects/users/MapleTrade.java",
    r"src/objects/users/stats/SecondaryStatEffect.java",
    r"src/objects/users/MapleCharacter.java",
    r"src/objects/users/enchant/InnerAbility.java",
    r"src/objects/shop/MapleShop.java",
    r"src/objects/item/MapleItemInformationProvider.java",
    r"src/objects/item/MapleInventoryManipulator.java",
    r"src/objects/fields/child/moonbridge/Field_FerociousBattlefield.java",
    r"src/network/auction/processors/AuctionHandler.java",
    r"src/network/shop/processors/CashShopHandler.java"
]

base_path = os.getcwd()

for rel_path in files_to_fix:
    full_path = os.path.join(base_path, rel_path.replace("/", os.sep))
    if not os.path.exists(full_path):
        print(f"Skipping {rel_path} (not found)")
        continue

    with open(full_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    original_content = content
    for old, new in replacements:
        content = content.replace(old, new)
        
    if content != original_content:
        with open(full_path, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"Fixed artifacts in {rel_path}")
    else:
        print(f"No artifacts found in {rel_path}")
