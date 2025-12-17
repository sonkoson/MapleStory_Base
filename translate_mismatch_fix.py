
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

# Field_TrainMaster.java
replacements_train = [
    ('#b#e크리에이터들의\r\n기차 운행 도์ „!#n#k\r\n#r지금 바로 시작하๊ฒ 네!#k', '#b#eCreators\'\r\nTrain Operation Challenge!#n#k\r\n#rStarting Right Now!#k')
]
process_file('src/objects/fields/child/minigame/train/Field_TrainMaster.java', replacements_train)

# Field_ReceivingTreasure.java
replacements_treasure = [
    ('์  프키와 방향키를 이용해 내가 떨어트리는 보물을 최대한 많이 받아줘!', 'Use Jump and Arrow keys to catch as many treasures as possible!')
]
process_file('src/objects/fields/child/fritto/Field_ReceivingTreasure.java', replacements_treasure)

# MapleUnionData.java
replacements_union = [
    ('노비스 ์œ 니온', 'Novice Union'),
    (' 단계', ' Stage')
]
process_file('src/objects/fields/child/union/MapleUnionData.java', replacements_union)
