
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

# Field_DamageMeasurement.java
replacements_dm = [
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
    ('"시간내에 용사님께서 가진 ์ „투๋ ฅ을 마음껏 발휘해보시기 바랍니다."', '"Please show your full power within the time limit."'),
    ('.append("경 ");', '.append("Q ");'), # Quadrillion
    ('.append("조 ");', '.append("T ");'), # Trillion
    ('.append("억 ");', '.append("B ");'), # Billion
    ('.append("만 ");', '.append("0K ");') # Ten Thousand
]
process_file('src/objects/fields/child/etc/Field_DamageMeasurement.java', replacements_dm)

# DamageMeasurementRank.java
replacements_dmr = [
    ('.append("경 ");', '.append("Q ");'),
    ('.append("조 ");', '.append("T ");'),
    ('.append("억 ");', '.append("B ");'),
    ('.append("위#n#k");', '.append("Place#n#k");'),
    ('"   #b์ „투๋ ฅ#k : #e"', '"   #bCombat Power#k : #e"'),
    ('" 삭์ œ된 캐릭터\\r\\n"', '" Deleted Character\\r\\n"')
]
process_file('src/objects/fields/child/etc/DamageMeasurementRank.java', replacements_dmr)
