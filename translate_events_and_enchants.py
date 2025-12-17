
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

# EliteBossEvent.java
replacements_ebe = [
    ('"검은 기사 모카딘 : 위대한 분을 위하여 너를 처단하๊ฒ 다."', '"Black Knight Mokadin : เพื่อท่านผู้นั้น ข้าจะกำจัดเจ้า"'),
    ('"미친 마법사 카리아인 : 미천한 것들이 ๋‚ 뛰๊ณ  있구나. 크크크크..."', '"Crazy Wizard Karianne : พวกชั้นต่ำกำลังอาละวาดสินะ คึคึคึ..."'),
    ('"돌격형 CQ57 : 목표발견. ์ œ거 행동을 시작한다."', '"Assault Type CQ57 : พบเป้าหมาย เริ่มการกำจัด"'),
    ('"인간사냥꾼 줄라이 : 사냥감이 나타났군."', '"Manhunter July : เหยื่อปรากฏตัวแล้วสินะ"'),
    ('"싸움꾼 플๋ ˆ드 : 재미 있๊ฒ 군. 어디 한 번 놀아볼까."', '"Fighter Plaid : น่าสนุกนี่ มาเล่นกันหน่อยไหม"')
]
process_file('src/objects/fields/EliteBossEvent.java', replacements_ebe)

# MapleEvent.java
replacements_me = [
    ('"ยินดմ้วย! "', '"ยินดีด้วย! "'),
    ('"น่าเสี´ҁไม่ได้รับรางวัŁไว้โอกาสหน้านЁ~ ^^"', '"น่าเสียดาย ไม่ได้รับรางวัล ไว้โอกาสหน้านะ~ ^^"'),
    ('"V포인트 1을 획득하셨습니다."', '"ได้รับ V Point 1 แต้ม"'),
    ('"인기도 10을 얻으셨습니다."', '"ได้รับ Fame 10 แต้ม"'),
    ('" กิจกรรมเริ่มขึ้นแล้ว"', '" กิจกรรมเริ่มขึ้นแล้ว"') # Ensuring encoding
]
process_file('src/objects/fields/events/MapleEvent.java', replacements_me)

# SpelltraceEnchant.java
replacements_se = [
    ('"이노센트"', '"Innocent"'),
    ('"백의"', '"Clean Slate"')
]
process_file('src/objects/users/achievement/caching/mission/submission/checkvalue/SpelltraceEnchant.java', replacements_se)
