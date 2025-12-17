
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

# ConnectorController.java
replacements_cc = [
    ('this.banOffBtn.setText("시리얼 밴 해์ œ");', 'this.banOffBtn.setText("Unban Serial");'),
    ('this.online1.setText("쓰๋ ˆ드 수 :");', 'this.online1.setText("Threads :");')
]
process_file('src/objects/utils/ConnectorController.java', replacements_cc)

# bitFalg.java
replacements_bf = [
    ('.append("더미 int * 2 ์ œ거크기:")', '.append("Dummy int * 2 removed size:")')
]
process_file('src/objects/utils/bitFalg.java', replacements_bf)

# MapleQuestAction.java
replacements_mq = [
    ('String msg = "<" + name + "> 훈장을 획득하셨습니다!";', 'String msg = "You obtained <" + name + "> Medal!";')
]
process_file('src/objects/quest/MapleQuestAction.java', replacements_mq)
