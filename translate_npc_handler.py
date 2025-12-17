
import os

filepath = "src/network/game/processors/NPCHandler.java"

with open(filepath, 'r', encoding='utf-8') as f:
    content = f.read()

replacements = [
    ('CurrentTime.getAllCurrentTime() + "에 " + c.getPlayer().getName() + "의 ๋ ˆ벨 달성 퀘스트 보상"', 'CurrentTime.getAllCurrentTime() + " - " + c.getPlayer().getName() + "\'s Level Up Quest Reward"'),
    ('QuestID ที่สำเร็จ : " + quest + " 엔피시 : " + npc + " 스크립트 : " + q.getEndscript()', 'QuestID Completed : " + quest + " NPC : " + npc + " Script : " + q.getEndscript()'),
    ('sb.append("창๊ณ  아이템 꺼냄 (캐릭터 : ");', 'sb.append("Storage Item Retrieve (Character : ");'),
    ('sb.append(", 계์ • : ");', 'sb.append(", Account : ");'),
    ('sb.append(", 아이템 : ");', 'sb.append(", Item : ");'),
    ('sb.append("개");', 'sb.append("pcs");'),
    ('sb.append(", 장비옵션[");', 'sb.append(", Equip Options[");'),
    ('sbx.append("창๊ณ  아이템 보관 (캐릭터 : ");', 'sbx.append("Storage Item Store (Character : ");'),
    ('sbx.append(", 계์ • : ");', 'sbx.append(", Account : ");'),
    ('sbx.append(", 아이템 : ");', 'sbx.append(", Item : ");'),
    ('sbx.append("개");', 'sbx.append("pcs");'),
    ('sbx.append(") (창๊ณ  아이템과 병합 총 ");', 'sbx.append(") (Storage Item Merge Total ");'),
    ('sbx.append(", 장비옵션[");', 'sbx.append(", Equip Options[");'),
    ('sbxx.append("창๊ณ  메소 " + (meso < 0L ? "보관" : "찾음") + " (캐릭터 : ");', 'sbxx.append("Storage Meso " + (meso < 0L ? "Store" : "Retrieve") + " (Character : ");'),
    ('sbxx.append(", 계์ • : ");', 'sbxx.append(", Account : ");'),
    ('sbxx.append(", 메소 : ");', 'sbxx.append(", Meso : ");')
]

for old, new_str in replacements:
    content = content.replace(old, new_str)

with open(filepath, 'w', encoding='utf-8') as f:
    f.write(content)

print(f"Translated {filepath}")
