
import os
import re

replacements = {
    # HiredMerchant.java
    "ผู้ขายมՁMeso มҡเกԹไป": "ผู้ขายมี Meso มากเกินไป",
    
    # ImprintedStoneOption.java
    "พลังโจมตՁ": "พลังโจมตี",
    
    # MapleCharacter.java
    "เสՁ": "เสีย",
    
    # EquipEnchantMan.java
    "พลังโจมตՁ(STR)": "พลังโจมตี (STR)",
    "พลังโจมตՁ(DEX)": "พลังโจมตี (DEX)",
    "พลังโจมตՁ(LUK)": "พลังโจมตี (LUK)",
    "พลังโจมตՁ(MaxHP)": "พลังโจมตี (MaxHP)",
    
    # GuildHandler.java
    "มՁMeso ไม่เพียงพอ": "มี Meso ไม่เพียงพอ",
    "กิŴ์": "กิลด์",
    
    # InventoryHandler.java
    "ต้องมՁFragment": "ต้องมี Fragment",
    "ต้องมՁPurification": "ต้องมี Purification",
    "ต้องมՁStone": "ต้องมี Stone",
    "อย่างลЁ1": "อย่างละ 1",
    "ไม่มՁAndroid": "ไม่มี Android",
    
    # NPCConversationManager.java
    "กรุณҡลับมาใหม่": "กรุณากลับมาใหม่",
    
    # General Fixes
    "มՁ": "มี",
    "มҡ": "มาก",
    "เกԹ": "เกิน",
    "ขณеาย": "ขณะตาย", # From previous reports if missed
    "หรื͵": "หรือ", # From previous reports if missed
    "ไม辺": "ไม่มี", # From previous reports if missed
    "ผู้เล蹁": "ผู้เล่น", # From previous reports if missed
    "มอนสเตอร์ด้ǁ": "มอนสเตอร์ด้วย", # From previous reports if missed
    "โหŴ": "โหลด"
}

def process_file(file_path):
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()
            
        original_content = content
        
        for bad, good in replacements.items():
            if bad in content:
                content = content.replace(bad, good)
        
        # General pattern for 'มՁ' -> 'มี', 'มҡ' -> 'มาก', 'เกԹ' -> 'เกิน' 
        # CAUTION: 'Ձ' might be used for other vowels in this corrupted encoding scheme.
        # Based on examples:
        # Ձ -> ี (sara ii)
        # ҡ -> า (sara aa)
        # Թ -> ิ (sara i)  Wait, "เกิน" is sara i + nor nu? No. "เกิน" = sara e + k + sara i + n ? 
        # Actually 'เกԹ' -> 'เกิน'. 'ก' is ok. 'เ' is ok. 
        # If 'Թ' is 'ิน' (in) or 'น' (n)?
        # Let's check "เกԹไป" -> "เกินไป". 
        # 'เ' is not corrupted. 'ก' is not corrupted. "เกิน" writes vowel 'i' over 'k', then 'n'.
        # So 'Թ' likely represents 'ิน' or just 'ิ' (sara i) + 'น' (n).
        # In HiredMerchant: "เกԹ" -> "เกิน" ?
        # In TIS-620, 'ิ' is 0xD4. 'น' is 0xB9.
        # This looks like double encoding or shift.
        
        # Let's trust specific replacements first.
        
        if content != original_content:
            with open(file_path, 'w', encoding='utf-8') as f:
                f.write(content)
            print(f"Fixed {file_path}")
            
    except Exception as e:
        print(f"Error processing {file_path}: {e}")

if __name__ == "__main__":
    files_to_check = [
        "src/objects/users/stone/ImprintedStoneOption.java",
        "src/objects/users/MapleCharacter.java",
        "src/objects/shop/HiredMerchant.java",
        "src/objects/users/enchant/EquipEnchantMan.java",
        "src/network/game/processors/GuildHandler.java",
        "src/network/game/processors/inventory/InventoryHandler.java",
        "src/scripting/NPCConversationManager.java"
    ]
    
    for relative_path in files_to_check:
        full_path = os.path.join(os.getcwd(), relative_path.replace("/", os.sep))
        if os.path.exists(full_path):
            process_file(full_path)
        else:
            print(f"File not found: {full_path}")
