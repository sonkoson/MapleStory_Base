
import os

# Define replacements per file or directory
# Format: 'filename_suffix': [ (old, new), ... ]
# I will use full relative paths or unique suffixes.

replacements_map = {
    'arkarium/Field_Arkaium.java': [
        ('CWvsContext.getScriptProgressMessage("시간의 여์‹  륀느가 봉인에서 풀๋ ค났습니다.")', 'CWvsContext.getScriptProgressMessage("The Goddess of Time, Rhinne, has been unsealed.")'),
        ('eim.getMapInstance(Integer.parseInt(eim.getProperty("map"))).startMapEffect("아카이럼을 퇴치하였습니다. ์ œ단의 좌측 포탈을 통해 이동해주시기 바랍니다.", 5120026)', 'eim.getMapInstance(Integer.parseInt(eim.getProperty("map"))).startMapEffect("Arkarium has been defeated. Please exit through the portal on the left.", 5120026)'),
        ('"Arkarium บԴเบือนกาลเวลาและเนรเทศคسไปที่ไหนสักแห่ง"', '"Arkarium บิดเบือนกาลเวลาและเนรเทศคุณไปที่ไหนสักแห่ง"') # Mojibake fix
    ],
    'blackheaven/Field_BlackHeavenBoss.java': [
        ('"블랙헤븐의 코어가 침입자를 향해 공격을 시작합니다."', '"Black Heaven\'s Core begins attacking the intruder."')
    ],
    'blackmage/Field_BlackMageBattlePhase1.java': [
        ('"Knight of Creation and Destruction พ่ายแพ้แล้ǁเส้นทางสู่ Black Mage เปԴออก"', '"Knight of Creation and Destruction พ่ายแพ้แล้ว เส้นทางสู่ Black Mage เปิดออก"'),
        ('"Wailing Wall ปรากฏขึ้นและเข้าครอบงำพื้นที่"', '"Wailing Wall ปรากฏขึ้นและเข้าครอบงำพื้นที่"'), # Seems fine? Check Mojibake "พื้นที่" ok. "ปรากฏ" ok.
        ('"สายฟ้าสีแดงที่น่ากลัวฟҴลงมҁจำกѴการเคลื่อนไหว"', '"สายฟ้าสีแดงที่น่ากลัวฟาดลงมาจำกัดการเคลื่อนไหว"')
    ],
    'blackmage/Field_BlackMageBattlePhase2.java': [
         ('"พลังลึกลับแผ่ออกมาจาก Black Mage และกลืนกินบัลลังก์แห่งความม״"', '"พลังลึกลับแผ่ออกมาจาก Black Mage และกลืนกินบัลลังก์แห่งความมืด"'),
         ('"Eye of Ruin ไล่ตามศѵรู"', '"Eye of Ruin ไล่ตามศัตรู"'),
         ('"สายฟ้าสีแดงของ Black Mage ปกคลุมไปทั่ǁต้องหҷี่หลบเธ ัย"', '"สายฟ้าสีแดงของ Black Mage ปกคลุมไปทั่ว ต้องหาที่หลบภัย"')
    ],
    'blackmage/Field_BlackMageBattlePhase3.java': [
         ('"ทุกสิ่งรอบข้างกำลังสูญสลายไปในพริบตҴ้วยพลังอันท่วมท้น"', '"ทุกสิ่งรอบข้างกำลังสูญสลายไปในพริบตาด้วยพลังอันท่วมท้น"'),
         ('"Black Mage ใช้พลังแห่งการสร้างแลзำลายล้าง ต้องเลือกหลบหลีกไปทางบนหรือล่าง"', '"Black Mage ใช้พลังแห่งการสร้างและทำลายล้าง ต้องเลือกหลบหลีกไปทางบนหรือล่าง"'),
         ('"ทٵสวรรค์แห่งกา÷ำลายล้างกำเนԴขึ้นจากความว่างเปล่า"', '"ทูตสวรรค์แห่งการทำลายล้างกำเนิดขึ้นจากความว่างเปล่า"')
    ],
    'blackmage/Field_BlackMageBattlePhase4.java': [
         ('"ทำลายไข่แห่งการสร้างและจบกาõ่อสู้อันยาวนานนี้กันเถอะ"', '"ทำลายไข่แห่งการสร้างและจบการต่อสู้อันยาวนานนี้กันเถอะ"'),
         ('"보스 검은 마법사 격파 ("', '"Boss Black Mage Defeated ("'),
         ('"[보스격파] [CH."', '"[Boss Defeat] [CH."'),
         ('"20세 이상"', '"20+"'),
         ("' 파티(", "' Party("),
         ('")가 [검은 마법사]를 격파하였습니다."', '") has defeated [Black Mage]."'),
         ('"อำนาจของผู้ใกล้เคียงพระเจ้าปรากฏขึ้น ต้องเลือกว่าจะรับพลังแห่งการสร้างหรืͷำลายล้าง"', '"อำนาจของผู้ใกล้เคียงพระเจ้าปรากฏขึ้น ต้องเลือกว่าจะรับพลังแห่งการสร้างหรือทำลายล้าง"')
    ],
    'cygnus/Field_Cygnus.java': [
        ('"คسได้กำจѴ Cygnus แล้ǁกรسาเดินผ่านปรеูหลักของ Hall of Cygnus เพื่ʹำเนินกาõ่อ"', '"คุณได้กำจัด Cygnus แล้ว กรุณาเดินผ่านประตูหลักของ Hall of Cygnus เพื่อดำเนินการต่อ"')
    ],
    'demian/Field_Demian.java': [
        ('"데미안 격파로 얻은 아이템"', '"Item obtained from defeating Damien"'),
        ('"보스 노말 데미안 격파"', '"Boss Normal Damien Defeated"'),
        ('"보스 " + (hell ? "헬" : "하드") + " 데미안 격파 ("', '"Boss " + (hell ? "Hell" : "Hard") + " Damien Defeated ("'),
        ('"[보스격파] [CH."', '"[Boss Defeat] [CH."'),
        ('"20세 이상"', '"20+"'),
        ("' 파티(", "' Party("),
        ('")가 [헬 데미안]을 격파하였습니다."', '") has defeated [Hell Damien]."'),
        ('")가 [하드 데미안]을 격파하였습니다."', '") has defeated [Hard Damien]."'),
        ('"Damien ได้ครอบครองพลังแห่งความม״ที่สมบูó์แล้ว"', '"Damien ได้ครอบครองพลังแห่งความมืดที่สมบูรณ์แล้ว"'),
        ('"ตราปรзับสมบูó์ Damien กำลังถูกความม״เข้าครอบงำ"', '"ตราประทับสมบูรณ์ Damien กำลังถูกความมืดเข้าครอบงำ"')
    ],
    'dojang/DojangRanking.java': [
         ('" 캐릭터가 무릉 도장 랭킹 " + e.getRank() + "위로 보상이 ์ •산되었습니다."', '" character reward settled for Mu Lung Dojo Rank " + e.getRank() + "."'),
         ('"[알림] 무릉도장 랭킹 " + e.getRank() + "위 보상을 수๋ น해주시기 바랍니다."', '"[Notice] Please claim your Mu Lung Dojo Rank " + e.getRank() + " reward."'),
         ('" 캐릭터가 무릉 도장(챌린지) 랭킹 " + e.getRank() + "위로 보상이 ์ •산되었습니다."', '" character reward settled for Mu Lung Dojo (Challenge) Rank " + e.getRank() + "."'),
         ('"[알림] 무릉도장(챌린지) 랭킹 " + e.getRank() + "위 보상을 수๋ น해주시기 바랍니다."', '"[Notice] Please claim your Mu Lung Dojo (Challenge) Rank " + e.getRank() + " reward."')
    ],
    'dojang/Field_Dojang.java': [
        ('"ปŴล็อค Mulung Dojo Challenge Mode แล้ว"', '"ปลดล็อค Mulung Dojo Challenge Mode แล้ว"'),
        ('"ทำลายʶԵิสูงสشของสัปดาห์นี้เรียบร้อยแล้ว"', '"ทำลายสถิติสูงสุดของสัปดาห์นี้เรียบร้อยแล้ว"'),
        ('"์ œ한시간은 15분, 최대한 ์‹ 속하게 몬스터를 쓰러트리๊ณ  다음 층으로 올라가면 돼!"', '"Time limit is 15 minutes. Defeat monsters as fast as possible and go up to the next floor!"'),
        ('"사부님의 특별한 도법으로 모๋“  버프가 해์ œ되었어. 이래야 좀 공평하지? 30초 줄테니까 준비해서 올라가라๊ณ ."', '"All buffs have been removed by Master\'s special technique. Fair enough? You have 30 seconds to prepare!"'),
        ('"상대를 격파하였습니다. 10초간 타이머가 ์ •지됩니다."', '"Opponent defeated. Timer paused for 10 seconds."')
    ],
    'etc/Field_DamageMeasurement.java': [
         ('#b[์ „투๋ ฅ 측์ •]#k', '#b[Combat Power Measurement]#k'),
         ('"측์ •이 시작되었습니다. 용사님의 진가를 발휘해볼까요?"', '"Measurement started. Shall we show your true value?"'),
         ('"현재까지 용사님의 ์ „투๋ ฅ은 #b"', '"Your current Combat Power is #b"'),
         ('"측์ •이 완료되었습니다."', '"Measurement complete."'),
         ('"총 데미지 : "', '"Total Damage : "'),
         ('"์ „투๋ ฅ이 기록되었습니다. 기록은 랭킹에서 확인ํ•  수 있습니다."', '"Combat Power recorded. Check rankings for details."'),
         ('"ความเสียหาµ่อหุ่นฟางในเวลҷี่กำหนด"', '"Damage to Straw Target in given time"'),
         ('"์ž 시 후 ์ „투๋ ฅ 측์ •이 시작됩니다."', '"Combat Power Measurement will start shortly."'),
         ('"허수아비#k가 소환되며, 측์ •은 #b2분#k간 진행됩니다."', '"#bScarecrow#k will be summoned. Measurement lasts #b2 minutes#k."'),
         ('"측์ •된 ์ „투๋ ฅ은 자동으로 기록되며 랭킹에 등록되게 됩니다."', '"Measured Combat Power is automatically recorded in the rankings."'),
         ('"시간내에 용사님께서 가진 ์ „투๋ ฅ을 마음껏 발휘해보시기 바랍니다."', '"Please show your full power within the time limit."')
    ],
    'etc/Field_EventRabbit.java': [
        ('"กำจѴ Moon Bunny ไม่ทันเวลҁไม่ได้รับรางวัล"', '"กำจัด Moon Bunny ไม่ทันเวลา ไม่ได้รับรางวัล"'),
        ('"กำจѴ Moon Bunny และได้รับ Persimmon Coin " + quantity + "ชิ้นที่ได้รับ"', '"กำจัด Moon Bunny และได้รับ Persimmon Coin " + quantity + " ชิ้น"'),
        ('"추석 이벤트로 획득"', '"Obtained from Chuseok Event"'),
        ('"월묘를 처치하여 #b단감 코인 " + quantity + "개#k를 획득했습니다."', '"Defeated Moon Bunny and obtained #b" + quantity + " Persimmon Coins#k."')
    ],
    'union/MapleUnionData.java': [
        ('"노비스 ์œ 니온"', '"Novice Union"'),
        ('" 단계"', '" Stage"') # "단계" -> Stage/Tier
    ]
}

def process_recursive(root_dir, replacements_map):
    for root, dirs, files in os.walk(root_dir):
        for file in files:
            if file.endswith(".java"):
                file_path = os.path.join(root, file)
                # Check if this file has replacements
                # We identify keys by checking if they end with 'subdir/filename' or just 'filename'
                relative_path = os.path.relpath(file_path, root_dir).replace('\\', '/')
                
                # Try simple match
                matched_replacements = []
                for key, reps in replacements_map.items():
                    if file_path.replace('\\', '/').endswith(key):
                       matched_replacements.extend(reps)
                
                if matched_replacements:
                    try:
                        with open(file_path, 'r', encoding='utf-8') as f:
                            content = f.read()
                        
                        modified = False
                        for old, new in matched_replacements:
                            if old in content:
                                content = content.replace(old, new)
                                modified = True
                        
                        if modified:
                            with open(file_path, 'w', encoding='utf-8') as f:
                                f.write(content)
                            print(f"Processed {file_path}")
                    except Exception as e:
                        print(f"Error processing {file_path}: {e}")

process_recursive('src/objects/fields/child', replacements_map)
