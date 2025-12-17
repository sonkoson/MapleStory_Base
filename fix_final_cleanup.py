
import os
import re

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
    210: "Wizard (F/P)",
    211: "Mage (F/P)",
    212: "Archmage (F/P)",
    220: "Wizard (I/L)",
    221: "Mage (I/L)",
    222: "Archmage (I/L)",
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
    330: "Archer (Pathfinder)",
    331: "Chaser",
    332: "Pathfinder",
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
    501: "Pirate (Cannon)",
    1000: "Noblesse",
    1100: "Dawn Warrior (1)",
    1110: "Dawn Warrior (2)",
    1111: "Dawn Warrior (3)",
    1112: "Dawn Warrior (4)",
    1200: "Blaze Wizard (1)",
    1210: "Blaze Wizard (2)",
    1211: "Blaze Wizard (3)",
    1212: "Blaze Wizard (4)",
    1300: "Wind Archer (1)",
    1310: "Wind Archer (2)",
    1311: "Wind Archer (3)",
    1312: "Wind Archer (4)",
    1400: "Night Walker (1)",
    1410: "Night Walker (2)",
    1411: "Night Walker (3)",
    1412: "Night Walker (4)",
    1500: "Thunder Breaker (1)",
    1510: "Thunder Breaker (2)",
    1511: "Thunder Breaker (3)",
    1512: "Thunder Breaker (4)",
    2000: "Aran",
    2001: "Evan",
    2002: "Mercedes",
    2003: "Phantom",
    2004: "Luminous",
    2005: "Shade",
    2100: "Aran (1)",
    2110: "Aran (2)",
    2111: "Aran (3)",
    2112: "Aran (4)",
    2200: "Evan (1)",
    2210: "Evan (2)",
    2211: "Evan (3)",
    2212: "Evan (4)",
    2213: "Evan (5)",
    2214: "Evan (6)",
    2215: "Evan (7)",
    2216: "Evan (8)",
    2217: "Evan (9)",
    2218: "Evan (10)",
    2300: "Mercedes (1)",
    2310: "Mercedes (2)",
    2311: "Mercedes (3)",
    2312: "Mercedes (4)",
    2400: "Phantom (1)",
    2410: "Phantom (2)",
    2411: "Phantom (3)",
    2412: "Phantom (4)",
    2500: "Shade (1)",
    2510: "Shade (2)",
    2511: "Shade (3)",
    2512: "Shade (4)",
    2700: "Luminous (1)",
    2710: "Luminous (2)",
    2711: "Luminous (3)",
    2712: "Luminous (4)",
    3000: "Citizen",
    3001: "Demon",
    3100: "Demon Slayer (1)",
    3110: "Demon Slayer (2)",
    3111: "Demon Slayer (3)",
    3112: "Demon Slayer (4)",
    3101: "Demon Avenger (1)",
    3120: "Demon Avenger (2)",
    3121: "Demon Avenger (3)",
    3122: "Demon Avenger (4)",
    3200: "Battle Mage (1)",
    3210: "Battle Mage (2)",
    3211: "Battle Mage (3)",
    3212: "Battle Mage (4)",
    3300: "Wild Hunter (1)",
    3310: "Wild Hunter (2)",
    3311: "Wild Hunter (3)",
    3312: "Wild Hunter (4)",
    3500: "Mechanic (1)",
    3510: "Mechanic (2)",
    3511: "Mechanic (3)",
    3512: "Mechanic (4)",
    3600: "Xenon (1)",
    3610: "Xenon (2)",
    3611: "Xenon (3)",
    3612: "Xenon (4)",
    3700: "Blaster (1)",
    3710: "Blaster (2)",
    3711: "Blaster (3)",
    3712: "Blaster (4)",
    4001: "Hayato",
    4002: "Kanna",
    4100: "Hayato (1)",
    4110: "Hayato (2)",
    4111: "Hayato (3)",
    4112: "Hayato (4)",
    4200: "Kanna (1)",
    4210: "Kanna (2)",
    4211: "Kanna (3)",
    4212: "Kanna (4)",
    6000: "Kaiser",
    6001: "Angelic Buster",
    6002: "Cadena",
    6100: "Kaiser (1)",
    6110: "Kaiser (2)",
    6111: "Kaiser (3)",
    6112: "Kaiser (4)",
    6400: "Cadena (1)",
    6410: "Cadena (2)",
    6411: "Cadena (3)",
    6412: "Cadena (4)",
    6500: "Angelic Buster (1)",
    6510: "Angelic Buster (2)",
    6511: "Angelic Buster (3)",
    6512: "Angelic Buster (4)",
    10000: "Zero",
    10100: "Zero (Alpha)",
    10110: "Zero (Beta)",
    10111: "Zero (Alpha)",
    10112: "Zero (Beta)",
    11200: "Beast Tamer (1)",
    11210: "Beast Tamer (2)",
    11211: "Beast Tamer (3)",
    11212: "Beast Tamer (4)",
    14000: "Kinesis (0)",
    14200: "Kinesis (1)",
    14210: "Kinesis (2)",
    14211: "Kinesis (3)",
    14212: "Kinesis (4)",
    15000: "Illium (0)",
    15001: "Ark (0)",
    15002: "Adele (0)",
    15200: "Illium (1)",
    15210: "Illium (2)",
    15211: "Illium (3)",
    15212: "Illium (4)",
    15500: "Ark (1)",
    15510: "Ark (2)",
    15511: "Ark (3)",
    15512: "Ark (4)",
    15100: "Adele (1)",
    15110: "Adele (2)",
    15111: "Adele (3)",
    15112: "Adele (4)",
    16000: "Hoyoung (0)",
    16200: "Hoyoung (1)",
    16400: "Hoyoung (2)",
    16410: "Hoyoung (3)",
    16411: "Hoyoung (4)",
    16412: "Hoyoung (5)"
    # This list is not exhaustive but covers most base cases
}

def fix_game_constants_jobs(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        lines = f.readlines()
        
    new_lines = []
    current_case = None
    
    # Iterate lines to find switch cases and replace return values
    for line in lines:
        stripped = line.strip()
        
        # Match case definition
        case_match = re.search(r'case\s+(\d+):', stripped)
        if case_match:
            current_case = int(case_match.group(1))
            new_lines.append(line)
            continue
            
        # Match return statement that looks like mojibake (contains strange chars)
        # Or just replace ANY return string in this block if we have a match
        if current_case is not None and stripped.startswith("return") and "\"" in stripped:
            if current_case in JOB_NAMES:
                # Replace with our clean English name
                indent = line[:line.find("return")]
                new_lines.append(f'{indent}return "{JOB_NAMES[current_case]}";\n')
                current_case = None # Consumed
                continue
            else:
                 # If we don't have it in our dict, maybe just try to clean up if it's obvious Mojibake
                 # But sticking to original if unknown is safer than breaking syntax.
                 # Let's check if it has Thai chars which confuse the compiler or user
                 if re.search(r'[\u0E00-\u0E7F]', stripped):
                      indent = line[:line.find("return")]
                      new_lines.append(f'{indent}return "Simulated Job {current_case}";\n')
                      current_case = None
                      continue
        
        # Reset case if we hit break or closing brace or another case
        if "break;" in stripped or "}" in stripped:
            current_case = None
            
        new_lines.append(line)
        
    with open(file_path, 'w', encoding='utf-8') as f:
        f.writelines(new_lines)
    print("Fixed GameConstants.java Job Names")

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
        "ครั้§": "ครั้ง",
        "ทั้§": "ทั้ง",
        "นั้§": "นั้น",
        "ยั§": "ยัง",
        "ผู้เล蹁": "ผู้เล่น",
        # New ones from report
        "ครั้งลЁ": "ครั้งละ",
        "ใส่ค่าได้เพียง 0-9 และสามารถสร้างรูนได้ครั้งลЁ1 อันเท่านั้น": "ใส่ค่าได้เพียง 0-9 และสามารถสร้างรูนได้ครั้งละ 1 อันเท่านั้น",
        "มҡ": "มาก",
        "มՁ": "มี",
        "เกԹ": "เกิน",
    }
    
    print("Fixing Mojibake in src...")
    for root, dirs, files in os.walk(root_dir):
        for file in files:
            if file.endswith(".java") or file.endswith(".js"):
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

if __name__ == "__main__":
    fix_game_constants_jobs(os.path.join("src", "constants", "GameConstants.java"))
    fix_l_mojibake("src")
