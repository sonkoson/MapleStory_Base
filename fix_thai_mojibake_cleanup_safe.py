
import os
import re

def process_donation_request(file_path):
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()

        # Replace headers with English
        if 'new String[]{"id", "เนŒย‹ยย' in content:
            content = re.sub(
                r'new String\[\]\{"id", ".*?"\}', 
                'new String[]{"id", "Date", "Account Name", "Player Name", "Real Name", "Point", "Type", "Status"}', 
                content, count=1
            )
            
        replacements = {
            '"เน‹เธ เธ˜เนŒเธ’ย˜เน‹เธ†เธŒ"': '"Pending"',
            '"เนŒเธ’ย˜เน‹เธ†เธŒเนŒย™ย„เน‹เธƒยŒ"': '"Refused"',
            '"เนŒยžโ€ฆเนŠเธ˜ยˆเน‹ยˆย„เน‹ย เธ "': '"Approved"',
            '"เน ย เธŒเนŒย เธ˜เน ยŠเธ˜ เนŒเธ–เธ‰เนŒยย ย„ เนŒย‹ยย เนŒเธ’เธ  เน‹ย‚เธ”เนŒโ€”เธ  -"': '"Search Character Name -"',
            '"เนŒยƒยˆเน‹เธ ยœเนŠเธ“ยย เนŒเธ™เธˆ"': '"Search"',
            '"เนŒย„ยย เน ยƒย เน‹ย ยœ เน‹ยŒโ‚ฌเนŒยƒย "': '"Enter Name"',
            '"เนŒเธ–เธ‰เนŒยย ย„ เนŒย™ย„เน‹เธƒยŒ"': '"Refuse"', 
            '"เนŒยžโ€ฆเนŠเธ˜ยˆ เน‹ยˆย„เน‹ย เธ "': '"Approve"', 
            'text = "เน‹เธ เธ˜เนŒเธ’ย˜เน‹เธ†เธŒ";': 'text = "Pending";'
        }

        for bad, good in replacements.items():
            if bad in content:
                content = content.replace(bad, good)

        content = re.sub(r'type = "เนŒย เธœเน‹เธ ย˜";', 'type = "Bank Transfer";', content)
        content = re.sub(r'type = "เนŒย เธ”เน‹เธ’เธ„เน ยŠเธ˜";', 'type = "Credit Card";', content)
        content = re.sub(r'type = "เนŒเธ”ยˆเนŒย‹เธŒเนŒยžย เน ยŒเธˆเน ย‚เธ„เนŒเธ‡โ‚ฌ";', 'type = "TrueMoney Wallet";', content)
        
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(content)
        print("Processed DonationRequest.java")
    except Exception as e:
        print(f"Error processing DonationRequest: {e}")

def process_field_magnus(file_path):
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()
            
        pattern = r'"매그너스가 구와르를.*?되었습니다\. 구와르의 기운이 더욱 강해집니다\."'
        replacement = '"พลังของ Magnus ที่ควบคุม Guwaru อ่อนลงแล้ว พลังของ Guwaru จึงแข็งแกร่งขึ้น"'
        
        if re.search(pattern, content):
            content = re.sub(pattern, replacement, content)
            
            with open(file_path, 'w', encoding='utf-8') as f:
                f.write(content)
            print("Processed Field_Magnus.java")
    except Exception as e:
        print(f"Error processing Field_Magnus: {e}")

def process_char_commands(file_path):
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()
            
        replacements = {
            "วԸีใช้": "วิธีใช้",
            "โหลดʤรԻต์ใหม่เรีºร้อยแล้ว": "โหลดสคริปต์ใหม่เรียบร้อยแล้ว",
        }
        
        changed = False
        for bad, good in replacements.items():
            if bad in content:
                content = content.replace(bad, good)
                changed = True
                
        if changed:
            with open(file_path, 'w', encoding='utf-8') as f:
                f.write(content)
            print("Processed CharCommands.java")
    except Exception as e:
        print(f"Error processing CharCommands: {e}")

if __name__ == "__main__":
    process_donation_request(os.path.join("src", "api", "DonationRequest.java"))
    process_field_magnus(os.path.join("src", "objects", "fields", "child", "magnus", "Field_Magnus.java"))
    process_char_commands(os.path.join("src", "commands", "CharCommands.java"))
