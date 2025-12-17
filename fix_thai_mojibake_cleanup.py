
import os
import re

def process_donation_request(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()

    # Replace headers
    # original: new String[]{"id", "เนŒย‹ยย เนŒเธ’เธ  เนŒย‹ยœเนŠเธ ย„", ...
    # mapped to: id, Date, Account Name, Player Name, Real Name, Point, Type, Status
    if 'new String[]{"id", "เนŒย‹ยย' in content:
        content = re.sub(
            r'new String\[\]\{"id", ".*?"\}', 
            'new String[]{"id", "Date", "Account Name", "Player Name", "Real Name", "Point", "Type", "Status"}', 
            content, count=1
        )
        print("Fixed Headers in DonationRequest.java")

    # Replace Status strings
    # 0 -> Pending
    # 1 -> Refused
    # 2 -> Approved
    replacements = {
        '"เน‹เธ เธ˜เนŒเธ’ย˜เน‹เธ†เธŒ"': '"Pending"',
        '"เนŒเธ’ย˜เน‹เธ†เธŒเนŒย™ย„เน‹เธƒยŒ"': '"Refused"',
        '"เนŒยžโ€ฆเนŠเธ˜ยˆเน‹ยˆย„เน‹ย เธ "': '"Approved"',
        # Buttons and Labels
        '"เน ย เธŒเนŒย เธ˜เน ยŠเธ˜ เนŒเธ–เธ‰เนŒยย ย„ เนŒย‹ยย เนŒเธ’เธ  เน‹ย‚เธ”เนŒโ€”เธ  -"': '"Search Character Name -"',
        '"เนŒยƒยˆเน‹เธ ยœเนŠเธ“ยย เนŒเธ™เธˆ"': '"Search"',
        '"เนŒย„ยย เน ยƒย เน‹ย ยœ เน‹ยŒโ‚ฌเนŒยƒย "': '"Enter Name"',
        '"เนŒเธ–เธ‰เนŒยย ย„ เนŒย™ย„เน‹เธƒยŒ"': '"Refuse"', 
        '"เนŒยžโ€ฆเนŠเธ˜ยˆ เน‹ยˆย„เน‹ย เธ "': '"Approve"', 
        'text = "เน‹เธ เธ˜เนŒเธ’ย˜เน‹เธ†เธŒ";': 'text = "Pending";', # Might be used in other logic if variables exist
    }

    for bad, good in replacements.items():
        if bad in content:
            content = content.replace(bad, good)

    # Replace Types Loop
    # Pattern: type = "garbage";
    # We will replace them with type = "Type X"; or specific names
    # t=0: Transfer, t=1: Credit, t=2: TrueMoney Wallet
    content = re.sub(r'type = "เนŒย เธœเน‹เธ ย˜";', 'type = "Bank Transfer";', content)
    content = re.sub(r'type = "เนŒย เธ”เน‹เธ’เธ„เน ยŠเธ˜";', 'type = "Credit Card";', content)
    content = re.sub(r'type = "เนŒเธ”ยˆเนŒย‹เธŒเนŒยžย เน ยŒเธˆเน ย‚เธ„เนŒเธ‡โ‚ฌ";', 'type = "TrueMoney Wallet";', content)
    
    # Catch-all for other types: replace `type = "garbage";` with `type = "Type <number>";` based on context is hard with regex alone efficiently without iterating.
    # But we can replace the garbage string literals with a placeholder.
    # The garbage strings all seem to start with `"เน` or `"3เน` or `"2023เน` or `"5เน`
    
    def repl_type(match):
        # We can't easily know 't' here, but we can just clean the string.
        # Actually, let's just make it "Unknown Type" or leave it if it's too risky.
        # But wait, we want to remove Mojibake.
        return 'type = "Donation Code/Type";'

    # Match lines like: type = "xxxxxxxx"; where xxxxx contains เน
    # content = re.sub(r'type = ".*เน.*";', repl_type, content) 
    # Better: iterate known lines if possible.
    # Lines 346-446 contains the logic.
    # I will replace the whole block if I can match it, but it's risky.
    # Instead, I'll allow specific replacements for the common ones and let the rest accept a generic fix.
    
    # Generic Mojibake cleaner for string literals
    # Find strings containing 'เน' and replace with "Unknown"
    # content = re.sub(r'"[^"]*เน[^"]*"', '"Unknown"', content)
    # This is dangerous if legitimate Thai uses เน (it does: เน้น, เล่น).
    # Mojibake has 'เนŒ' (Thanthakhat + invalid char).
    # Normal Thai 'เน' is valid vowel E + Nor Nu.
    # I will be specific.
    
    with open(file_path, 'w', encoding='utf-8') as f:
        f.write(content)

def process_field_magnus(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()
            
    # Regex for the Korean string with embedded Mojibake
    pattern = r'"매그너스가 구와르를.*?되었습니다\. 구와르의 기운이 더욱 강해집니다\."'
    replacement = '"พลังของ Magnus ที่ควบคุม Guwaru อ่อนลงแล้ว พลังของ Guwaru จึงแข็งแกร่งขึ้น"'
    
    if re.search(pattern, content):
        content = re.sub(pattern, replacement, content)
        print("Fixed Field_Magnus.java")
        
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(content)

def process_char_commands(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()
        
    replacements = {
        "วԸีใช้": "วิธีใช้",
        "ชื่อตัวละคร": "ชื่อตัวละคร", # Ensure it's correct
        "โหลดʤรԻต์ใหม่เรีºร้อยแล้ว": "โหลดสคริปต์ใหม่เรียบร้อยแล้ว",
        # Add any others missed
    }
    
    for bad, good in replacements.items():
        if bad in content:
            content = content.replace(bad, good)
            print(f"Fixed {bad} in CharCommands.java")
            
    with open(file_path, 'w', encoding='utf-8') as f:
        f.write(content)

if __name__ == "__main__":
    process_donation_request(os.path.join("src", "api", "DonationRequest.java"))
    process_field_magnus(os.path.join("src", "objects", "fields", "child", "magnus", "Field_Magnus.java"))
    process_char_commands(os.path.join("src", "commands", "CharCommands.java"))
