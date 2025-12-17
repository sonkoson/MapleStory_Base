
import re

# File 1: DonationRanker.java
file_path_1 = 'src/objects/utils/DonationRanker.java'
try:
    with open(file_path_1, 'r', encoding='utf-8') as f:
        content = f.read()

    # Translate log string
    # System.out.println(ai.get() + "위, " + a.getKey() + "(" + accID + ") : " + a.getValue() + " 대표 캐릭터 : " + name);
    content = content.replace('"위, "', '" Rank, "')
    content = content.replace('" 대표 캐릭터 : "', '" Main Character : "')

    with open(file_path_1, 'w', encoding='utf-8') as f:
        f.write(content)
    print(f"Processed {file_path_1}")
except Exception as e:
    print(f"Error processing {file_path_1}: {e}")

# File 2: ConnectorController.java
file_path_2 = 'src/objects/utils/ConnectorController.java'
try:
    with open(file_path_2, 'r', encoding='utf-8') as f:
        content = f.read()

    content = content.replace('+ "명"', '+ " users"')
    content = content.replace('+ "개"', '+ " threads"')
    content = content.replace('.setText("시리얼 밴");', '.setText("Serial Ban");')
    content = content.replace('.setText("시리얼 밴 해์ œ");', '.setText("Unban Serial");')
    content = content.replace('.setText("연결자 수 :");', '.setText("Connected Users :");')
    content = content.replace('.setText("0명");', '.setText("0 users");')
    content = content.replace('.setText("쓰๋ ˆ드 수 :");', '.setText("Thread Count :");')
    content = content.replace('.setText("0개");', '.setText("0 threads");')
    
    # Change Font
    content = content.replace('new Font("굴림"', 'new Font("Tahoma"')

    with open(file_path_2, 'w', encoding='utf-8') as f:
        f.write(content)
    print(f"Processed {file_path_2}")
except Exception as e:
    print(f"Error processing {file_path_2}: {e}")
