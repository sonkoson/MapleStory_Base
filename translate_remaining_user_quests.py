
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

# MapleClient.java
replacements_client = [
    ('throw new RuntimeException("[오류] 존재하지 않는 계์ •이 로그인 성공되었습니다.");', 'throw new RuntimeException("[Error] Login successful for non-existent account.");'),
    ('sb.append("์ ‘속 해์ œ (아이피 : ");', 'sb.append("Disconnection (IP : ");'),
    ('FileoutputUtil.log("Log_Disconnect_Except.rtf", "버프์ €장중 오류 발생");', 'FileoutputUtil.log("Log_Disconnect_Except.rtf", "Error occurred while saving buffs");'),
    ('FileoutputUtil.log("Log_Disconnect_Except.rtf", "거짓말탐지기 ์ €장중 오류발생");', 'FileoutputUtil.log("Log_Disconnect_Except.rtf", "Error occurred while saving lie detector");'),
    ('FileoutputUtil.log("Log_Disconnect_Except.rtf", "removePlayer중 오류발생");', 'FileoutputUtil.log("Log_Disconnect_Except.rtf", "Error occurred during removePlayer");'),
    ('FileoutputUtil.log("Log_Disconnect_Except.rtf", "스크립트 엔진 초기화중 오류 발생");', 'FileoutputUtil.log("Log_Disconnect_Except.rtf", "Error occurred during script engine initialization");'),
    ('FileoutputUtil.log("Log_Disconnect_Except.rtf", "관리기 동์ ‘자 출๋ ฅ중 오류 발생");', 'FileoutputUtil.log("Log_Disconnect_Except.rtf", "Error occurred during Manager Worker output");'),
    ('String info = sdf.format(this.accountChatBan) + " 까지 채팅 이용이 ์ •지된 계์ •입니다.";', 'String info = "บัญชีนี้ถูกระงับการแชทจนถึง " + sdf.format(this.accountChatBan);')
]
process_file('src/objects/users/MapleClient.java', replacements_client)

# MobQuest.java
replacements_mq = [
    ('"추억의 사์ œ 200마리 처치하기"', '"กำจัด Memory Monk 200 ตัว"'),
    ('"200๋ ˆ벨 달성하기"', '"เข้าสู่เลเวล 200"'),
    ('"๋ ˆ벨 210 달성하기"', '"เข้าสู่เลเวล 210"'),
    ('"๋ ˆ벨 220 달성하기"', '"เข้าสู่เลเวล 220"'),
    ('"๋ ˆ벨 230 달성하기"', '"เข้าสู่เลเวล 230"'),
    ('"๋ ˆ벨 240 달성하기"', '"เข้าสู่เลเวล 240"'),
    ('"๋ ˆ벨 250 달성하기"', '"เข้าสู่เลเวล 250"'),
    ('"์ ˆ망의 ๋‚ 개 500마리 처치하기"', '"กำจัด Wings of Despair 500 ตัว"'),
    ('"๋ ˆ벨 260 달성하기"', '"เข้าสู่เลเวล 260"'),
    ('"๋ ˆ벨 265 달성하기"', '"เข้าสู่เลเวล 265"'),
    ('"๋ ˆ벨 270 달성하기"', '"เข้าสู่เลเวล 270"'),
    ('"๋ ˆ벨 275 달성하기"', '"เข้าสู่เลเวล 275"'),
    ('"사자왕 반 ๋ ˆ온의 흔์  "', '"ร่องรอยของ Lion King Van Leon"'),
    ('"시간의 대์‹ 관 아카이럼의 흔์  "', '"ร่องรอยของ Arkarium"'),
    ('"์œ 니온 - 골드 와이번 50마리 처치하기"', '"Union - กำจัด Gold Wyvern 50 ตัว"'),
    ('"์œ 니온 - 미니 와이번 100마리 처치하기"', '"Union - กำจัด Mini Wyvern 100 ตัว"')
]
process_file('src/objects/quest/MobQuest.java', replacements_mq)
