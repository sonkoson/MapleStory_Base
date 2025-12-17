
import os
import re

replacements = {
    # Found Issues
    "ครั้งลЁ1": "ครั้งละ 1",
    "แกЁ": "แกะ", # หมาป่าจับแกะ? (Wolf catches Sheep? Game context)
    "แลЁ": "และ", # and
    "จะŴ": "จะลด", # จะลดลง? Ŵ -> ลด? or just Ŵ -> ด
    "รдับ": "ระดับ",
    "กรسҷำ": "กรุณาทำ",
    "อย่างน้́": "อย่างน้อย",
    "หมาป่ҁ": "หมาป่า",
    "อย่ҧ": "อย่าง", # Hypothesized
    
    # Contextual from grep lines
    "ใส่ค่าได้เพียง 0-9 และสามารถสร้างรูนได้ครั้งลЁ1 อันเท่านั้น": "ใส่ค่าได้เพียง 0-9 และสามารถสร้างรูนได้ครั้งละ 1 อันเท่านั้น",
    "จับแกЁ": "จับแกะ",
    "EXP แลЁMeso": "EXP และ Meso",
    "จะŴลงอย่างมาก": "จะลดลงอย่างมาก",
    "รдับเลเวล": "ระดับเลเวล",
    "กรسҷำช่องว่าง": "กรุณาทำช่องว่าง",
    "Cash แลЁUse": "Cash และ Use",
    "อย่างน้́2": "อย่างน้อย 2",
    "อย่างน้́": "อย่างน้อย",
    "Coupon แลЁMasterpiece": "Coupon และ Masterpiece",
    "Pink Bean แลЁYeti": "Pink Bean และ Yeti",
    "ช่อง Use แลЁEtc": "ช่อง Use และ Etc",
    "ช่อง Equip แลЁUse": "ช่อง Equip และ Use",
    "หมาป่ҁ": "หมาป่า"
}

def process_file(file_path):
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()
            
        original_content = content
        
        for bad, good in replacements.items():
            if bad in content:
                content = content.replace(bad, good)
                
        # Heuristic fix for "Ё" -> "ะ" if not matched above specific
        # But "แลЁ" -> "และ". "ครั้งลЁ" -> "ครั้งละ".
        # So "Ё" -> "ะ".
        # Let's verify: "แกЁ" -> "แกะ". Yes.
        
        if "Ё" in content:
            # Dangerous to replace single char blindly?
            # Cyrillic IO. Should not be in Thai text.
            content = content.replace("Ё", "ะ")
            
        if "Ŵ" in content:
             # Found "จะŴลง" -> "จะลดลง". So Ŵ -> ด ??
             # But previously "กิŴ์" -> "กิลด์" (ล?).
             # So Ŵ is inconsistent or context dependent mojibake.
             # "จะŴลง" -> "จะลดลง" (D?)
             # "กิŴ์" -> "กิลด์" (L?)
             # Wait, "กิลด์" uses اللinggue? No.
             # Let's rely on specific dictionary replacement for now.
             pass

        if "д" in content:
             # "รдับ" -> "ระดับ". д -> ะ ? No, ระ (Ra).
             # "ร" (R) + "ะ" (a) + "ด" (d) ...
             # "รдับ" -> "ร" match. "ด" match? No.
             # It seems д = ะ (sara a)?
             # Let's assume specific replacements are safer.
             pass

        if content != original_content:
            with open(file_path, 'w', encoding='utf-8') as f:
                f.write(content)
            print(f"Fixed {file_path}")
            
    except Exception as e:
        print(f"Error processing {file_path}: {e}")

if __name__ == "__main__":
    files_to_check = [
        "src/commands/SpawnObjectCommands.java",
        "src/objects/users/skills/DamageParse.java",
        "src/objects/fields/Field.java",
        "src/network/game/processors/inventory/InventoryHandler.java",
        "src/network/game/processors/PlayerHandler.java"
    ]
    
    for relative_path in files_to_check:
        full_path = os.path.join(os.getcwd(), relative_path.replace("/", os.sep))
        if os.path.exists(full_path):
            process_file(full_path)
