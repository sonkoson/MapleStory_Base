
import os
import re

# Fix L-stroke Mojibake
def fix_l_mojibake(root_dir):
    replacements = {
        "Ł": "ล",
        "แชนแนŁ": "แชนแนล",
        "อѵราครԵิคอŁ": "อัตราคริติคอล",
        "ข้อมูŁ": "ข้อมูล",
        "สกิŁ": "สกิล",
        "รางวัŁ": "รางวัล",
        "เลเวŁ": "เลเวล",
        "หรื́": "หรือ",
        "น่าเสี´ҁ": "น่าเสียดาย",
        "กรسา": "กรุณา",
        "แล้ǁ": "แล้ว",
        "ชื่͵": "ชื่อ",
    }
    
    print("Fixing Ł Mojibake and others...")
    for root, dirs, files in os.walk(root_dir):
        for file in files:
            if file.endswith(".java"):
                path = os.path.join(root, file)
                try:
                    with open(path, 'r', encoding='utf-8') as f:
                        content = f.read()
                    
                    new_content = content
                    for bad, good in replacements.items():
                        new_content = new_content.replace(bad, good)
                        
                    if new_content != content:
                        with open(path, 'w', encoding='utf-8') as f:
                            f.write(new_content)
                        print(f"Fixed {path}")
                except Exception as e:
                    pass

# Map of Job ID to English Name for GameConstants.java replacement
JOB_NAMES = {
    0: "Beginner",
    100: "Warrior",
    110: "Fighter",
    111: "Crusader",
    112: "Hero",
    120: "Page",
    121: "White Knight",
    122: "Paladin",
    130: "Spearman",
    131: "Dragon Knight",
    132: "Dark Knight",
    200: "Magician",
    210: "Wizard (Fire/Poison)",
    211: "Mage (Fire/Poison)",
    212: "Archmage (Fire/Poison)",
    220: "Wizard (Ice/Lightning)",
    221: "Mage (Ice/Lightning)",
    222: "Archmage (Ice/Lightning)",
    230: "Cleric",
    231: "Priest",
    232: "Bishop",
    300: "Bowman",
    310: "Hunter",
    311: "Ranger",
    312: "Bowmaster",
    320: "Crossbowman",
    321: "Sniper",
    322: "Marksman",
    400: "Thief",
    410: "Assassin",
    411: "Hermit",
    412: "Night Lord",
    420: "Bandit",
    421: "Chief Bandit",
    422: "Shadower",
    430: "Blade Recruit",
    431: "Blade Acolyte",
    432: "Blade Specialist",
    433: "Blade Lord",
    434: "Blade Master",
    500: "Pirate",
    510: "Brawler",
    511: "Marauder",
    512: "Buccaneer",
    520: "Gunslinger",
    521: "Outlaw",
    522: "Corsair",
    530: "Cannoneer",
    531: "Cannon Trooper",
    532: "Cannon Master",
    # Add more as needed or use a generic fallback in Java logic
}

def fix_game_constants_job_names(file_path):
    print(f"Fixing Job Names in {file_path}...")
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            lines = f.readlines()
            
        new_lines = []
        in_method = False
        method_replaced = False
        
        for line in lines:
            if "public static final String getJobNameById(int job) {" in line:
                in_method = True
                new_lines.append(line)
                # Inject new logic here
                new_lines.append("      switch (job) {\n")
                for job_id, job_name in JOB_NAMES.items():
                    new_lines.append(f"         case {job_id}: return \"{job_name}\";\n")
                
                # Add default behavior for others
                new_lines.append("         default: return \"Unknown Job (\" + job + \")\";\n")
                new_lines.append("      }\n")
                new_lines.append("   }\n") 
                method_replaced = True
                continue
            
            if in_method:
                if line.strip() == "}":
                    in_method = False # End of original method, skip as we wrote our own
                # Skip all lines inside the original method
                continue
            
            new_lines.append(line)
            
        with open(file_path, 'w', encoding='utf-8') as f:
            f.writelines(new_lines)
        print("GameConstants.java patched.")
            
    except Exception as e:
        print(f"Error patching GameConstants: {e}")

if __name__ == "__main__":
    fix_l_mojibake("src")
    # Note: Logic for GameConstants replacement above is a bit risky if formatting differs.
    # We will use a targeted replacement for the switch block if possible, 
    # but since the content is Mojibake, re-writing the whole function is safer.
    # However, the simple state machine above assumes the method ends with a single "}".
    # Let's double check GameConstants structure via read first? 
    # No, we saw it earlier. It has a switch. We can just replace the whole file's content for that method.
    
    # Actually, to be safe, let's ONLY find the lines with Mojibake in that function and valid cases.
    pass
