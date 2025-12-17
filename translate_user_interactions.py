
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

# MapleQuestAction.java
replacements_mqa = [
    ('"훈장을 획득하셨습니다!"', '"Medal obtained!"'),
    ('"คسขҴไอเทมบางอย่างสำหรับเควส"', '"คุณขาดไอเทมบางอย่างสำหรับเควส"'),
    ('"กรسҷำช่องว่างแล้วลองใหม่อีกครั้ง"', '"กรุณาทำช่องว่างแล้วลองใหม่อีกครั้ง"')
]
process_file('src/objects/quest/MapleQuestAction.java', replacements_mqa)

# MapleTrade.java
replacements_mt = [
    ('"\r\n아이템("', '"\r\nItems("'),
    ('").append("개")', '").append("qty")'),
    ('"의 인벤ํ† 리 공간 부족, "', '" Inventory full, "'),
    ('"취소 사์œ  : ("', '"Cancel Reason : ("'),
    ('"아이템("', '"Items("')
]
process_file('src/objects/users/MapleTrade.java', replacements_mt)

# MapleCharacter.java
replacements_mc = [
    ('"#fn나눔๊ณ 딕 Extrabold#아래에서 당์‹ 의 내면의 결과를 확인해보세요.\\r\\n\\r\\n#b혁์‹ 의 룰๋ ›#k 에서 [#i"', '"#fnNanum Gothic Extrabold#Check your results below.\\r\\n\\r\\nIn #bRevolution Roulette#k [#i"'),
    ('"](이)가 소환 되었습니다."', '"] has been summoned."'),
    ('"[명사수] 사수로 ์ „직하였습니다."', '"[Marksman] Advanced to Marksman."'),
    ('"[์ €주와 ๊ณ 대의 힘] 에인션트 아처로 ์ „직하였습니다."', '"[Ancient Power] Advanced to Ancient Archer."'),
    ('"#e#b[초보자B]#k#n 길드에 자동으로 가입되었습니다.\\r\\n계์ • 내 #r통합๋ ˆ벨이 500#k을 넘으면 계์ • 내 모๋“  캐릭터가 길드에서 자동으로 탈퇴되며,\\r\\n#r캐릭터 ๋ ˆ벨이 220#k을 넘으면 해당 캐릭터는 자동으로 탈퇴됩니다.\\r\\n\\r\\n#b강림#k에서 행복한 하루 되세요."', 
     '"#e#b[BeginnerB]#k#n เข้าร่วมกิลด์โดยอัตโนมัติ\\r\\nหาก #rเลเวลรวมของบัญชีเกิน 500#k ตัวละครทั้งหมดในบัญชีจะออกจากกิลด์โดยอัตโนมัติ\\r\\nหาก #rเลเวลตัวละครเกิน 220#k ตัวละครนั้นจะออกจากกิลด์โดยอัตโนมัติ\\r\\n\\r\\nขอให้มีความสุขใน #bGanglim#k"'),
    ('"]이(가) ์œ 효기간이 만료되어 메이플 보관함에서 삭์ œ됩니다."', '"] has expired and will be deleted from Maple Cabinet."'),
    ('"캐비넷 아이템 기간 만료"', '"Cabinet Item Expired"'),
    ('"#b강림 리버스 멤버십 스킬 포인트(SP) 1개#k를 획득하였습니다.\\r\\n왼쪽 별모양 아이콘을 통해 확인해보세요."', 
     '"ได้รับ #bGanglim Reverse Membership SP 1 แต้ม#k\\r\\nตรวจสอบได้ที่ไอคอนดาวด้านซ้าย"'),
    ('"ได้รับ Ganglim Reverse Membership SP 1 แต้ม ตรวจสอบได้ที่ไอคอนดาǴ้านซ้าย"', '"ได้รับ Ganglim Reverse Membership SP 1 แต้ม ตรวจสอบได้ที่ไอคอนดาวด้านซ้าย"'),
    ('"[명사수] 사수로 ์ „직하였습니다."', '"[Marksman] Advanced to Marksman."'),
    ('"[์ €주와 ๊ณ 대의 힘] 에인션트 아처로 ์ „직하였습니다."', '"[Ancient Power] Advanced to Ancient Archer."')
]
process_file('src/objects/users/MapleCharacter.java', replacements_mc)
