
import re
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

# Process ExtraAbilityOption.java
replacements_eao = [
    ('"없음"', '"ไม่มี"'),
    ('"아케인 포스 +%d 증가"', '"Arcane Force +%d"'),
    ('"STR +%d 증가"', '"STR +%d"'),
    ('"DEX +%d 증가"', '"DEX +%d"'),
    ('"INT +%d 증가"', '"INT +%d"'),
    ('"LUK +%d 증가"', '"LUK +%d"'),
    ('"올스탯 +%d 증가"', '"All Stat +%d"'),
    ('"최대 HP +%d 증가"', '"Max HP +%d"'),
    ('"공격๋ ฅ/마๋ ฅ +%d 증가"', '"Attack Power/Magic Power +%d"'),
    ('"최대 HP +%d%s 증가"', '"Max HP +%d%s"'),
    ('"올스탯 +%d%s 증가 (직์ ‘ 투자한 스탯)"', '"All Stat +%d%s (AP Stats)"'),
    ('"공격๋ ฅ/마๋ ฅ +%d%s 증가"', '"Attack Power/Magic Power +%d%s"'),
    ('"크리티컬 확๋ฅ  +%d%s 증가"', '"Critical Rate +%d%s"'),
    ('"몬스터 방어๋ ฅ 무시 +%d%s"', '"Ignore Monster Defense +%d%s"'),
    ('"보스 공격 시 데미지 +%d%s 증가"', '"Boss Damage +%d%s"'),
    ('"재사용 대기시간 %d초 감소"', '"Cooldown -%d sec"'),
    ('"메소 획득량 +%d%s 증가"', '"Meso Drop Rate +%d%s"'),
    ('"아이템 드롭๋ฅ  +%d%s 증가"', '"Item Drop Rate +%d%s"'),
    ('"경험치 획득량 +%d%s 증가"', '"EXP Rate +%d%s"'),
    ('"크리티컬 데미지 +%d%s 증가"', '"Critical Damage +%d%s"'),
    ('"몬스터 리์   개체수 1.5배 증가"', '"Max Mobs 1.5x"'),
    ('"부활 시 무์   시간 %d초 증가"', '"Invincibility after Revive +%d sec"'),
    ('"상태 이상 내성 +%d%s 증가"', '"Status Resistance +%d%s"'),
    ('"최종 데미지 +%d%s 증가"', '"Final Damage +%d%s"')
]
process_file('src/objects/users/extra/ExtraAbilityOption.java', replacements_eao)

# Process ExtraAbilityGrade.java
replacements_eag = [
    (' "๋ ˆ어"', ' "Rare"'),
    (' "에픽"', ' "Epic"'),
    (' "์œ 니크"', ' "Unique"'),
    (' "๋ ˆ์ „드리"', ' "Legendary"')
]
process_file('src/objects/users/extra/ExtraAbilityGrade.java', replacements_eag)

# Process WeeklyQuest.java
replacements_wq = [
    ('"헤이븐 주간 퀘스트"', '"เควสรายสัปดาห์ Haven"'),
    ('"버๋ ค진야영지 주간 퀘스트"', '"เควสรายสัปดาห์ Dark World Tree"')
]
process_file('src/objects/quest/WeeklyQuest.java', replacements_wq)

# Process MobQuest.java
replacements_mq = [
    ('"주황버섯의 갓 30개 모아오기"', '"รวบรวม Orange Mushroom Cap 30 ชิ้น"'),
    ('"와일드 보어의 송곳니 50개 모아오기"', '"รวบรวม Wild Boar Tooth 50 ชิ้น"'),
    ('"루 광석 50개 모아오기"', '"รวบรวม Ore 50 ชิ้น"'),
    ('"추억의 사์ œ 200마리 처치하기"', '"กำจัด Memory Monk 200 ตัว"'),
    ('"노말 혼테일 격파하기"', '"กำจัด Normal Horntail"'),
    ('"상급기사 A~D 각 50마리 처치하기"', '"กำจัด Advanced Knight A~D อย่างละ 50 ตัว"'),
    ('"노말 블러디 퀸 격파하기"', '"กำจัด Normal Bloody Queen"'),
    ('"200๋ ˆ벨 달성하기"', '"บรรลุเลเวล 200"'),
    ('"기쁨의 에르다스 200마리 처치하기"', '"กำจัด Erdas of Joy 200 ตัว"'),
    ('"아르마의 부하 200마리 처치하기"', '"กำจัด Arma\'s Subordinate 200 ตัว"'),
    ('"๋ ˆ벨 210 달성하기"', '"บรรลุเลเวล 210"'),
    ('"๋ ˆ벨 220 달성하기"', '"บรรลุเลเวล 220"'),
    ('"라이터틀, 버샤크 200마리 처치하기"', '"กำจัด Ryturtle, Vyshark 200 ตัว"'),
    ('"๋ ˆ벨 230 달성하기"', '"บรรลุเลเวล 230"'),
    ('"노말 스우 격파하기"', '"กำจัด Normal Lotus"'),
    ('"아투스 200마리 처치하기"', '"กำจัด Atus 200 ตัว"'),
    ('"๋ ˆ벨 240 달성하기"', '"บรรลุเลเวล 240"'),
    ('"어๋‘ 의 집행자, 빛의 집행자 300마리 처치하기"', '"กำจัด Executioner of Darkness, Executioner of Light 300 ตัว"'),
    ('"๋ ˆ벨 250 달성하기"', '"บรรลุเลเวล 250"'),
    ('"혼돈의 피조물 500마리 처치하기"', '"กำจัด Creature of Chaos 500 ตัว"'),
    ('"์ ˆ망의 ๋‚ 개 500마리 처치하기"', '"กำจัด Wing of Despair 500 ตัว"'),
    ('"๋ ˆ벨 260 달성하기"', '"บรรลุเลเวล 260"'),
    ('"안세스티온 1000마리 처치하기"', '"กำจัด Ancestion 1000 ตัว"'),
    ('"๋ ˆ벨 265 달성하기"', '"บรรลุเลเวล 265"'),
    ('"포어๋ฒ 리온 1000마리 처치하기"', '"กำจัด Foreberion 1000 ตัว"'),
    ('"๋ ˆ벨 270 달성하기"', '"บรรลุเลเวล 270"'),
    ('"์— 브리온 1000마리 처치하기"', '"กำจัด Embrion 1000 ตัว"'),
    ('"๋ ˆ벨 275 달성하기"', '"บรรลุเลเวล 275"'),
    ('"게릴라 스펙터 200마리 처치하기"', '"กำจัด Guerrilla Specter 200 ตัว"'),
    ('"울프룻 200마리 처치하기"', '"กำจัด Wolfroot 200 ตัว"'),
    ('"검은 마법사 처치하기"', '"กำจัด Black Mage"'),
    ('"사자왕 반 ๋ ˆ온의 흔์  "', '"ร่องรอยของ Lion King Von Leon"'),
    ('"시간의 대์‹ 관 아카이럼의 흔์  "', '"ร่องรอยของ Arkarium"'),
    ('"์œ 니온 - 골드 와이번 50마리 처치하기"', '"Union - กำจัด Gold Wyvern 50 ตัว"'),
    ('"์œ 니온 - 미니 와이번 100마리 처치하기"', '"Union - กำจัด Mini Wyvern 100 ตัว"')
]
process_file('src/objects/quest/MobQuest.java', replacements_mq)

# Process MapleInventory.java
replacements_mi = [
    ('"removeItem 중 아이템 추가지급 발생 : (itemId : "', '"Additional item payment occurred during removeItem : (itemId : "')
]
process_file('src/objects/item/MapleInventory.java', replacements_mi)
