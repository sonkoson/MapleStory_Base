
import os

boss_names = {
    '"스우"': '"Lotus"',
    '"데미안"': '"Damien"',
    '"루시드"': '"Lucid"',
    '"윌"': '"Will"',
    '"더스크"': '"Dusk"',
    '"듄켈"': '"Dunkel"',
    '"진 힐라"': '"Jin Hilla"',
    '"검은 마법사"': '"Black Mage"',
    '"세렌"': '"Seren"',
    '"칼로스"': '"Kalos"',
    '"카링"': '"Karing"',
    '"가디언 엔젤 슬라임"': '"Guardian Angel Slime"',
    '"미츠히데"': '"Mitsuhide"',
    '"아케치 미츠히데"': '"Akechi Mitsuhide"',
    '"파풀라투스"': '"Papulatus"'
}

difficulties = {
    '"이지"': '"Easy"',
    '"노말"': '"Normal"',
    '"하드"': '"Hard"',
    '"카오스"': '"Chaos"',
    '"익스트림"': '"Extreme"',
    '"헬"': '"Hell"'
}

def process_file_content(content):
    modified = False
    for kor, eng in boss_names.items():
        if kor in content:
            content = content.replace(kor, eng)
            modified = True
    for kor, eng in difficulties.items():
        if kor in content:
            content = content.replace(kor, eng)
            modified = True
    return content, modified

def process_recursive(root_dir):
    for root, dirs, files in os.walk(root_dir):
        for file in files:
            if file.endswith(".java"):
                file_path = os.path.join(root, file)
                try:
                    with open(file_path, 'r', encoding='utf-8') as f:
                        content = f.read()
                    
                    new_content, modified = process_file_content(content)
                    
                    if modified:
                        with open(file_path, 'w', encoding='utf-8') as f:
                            f.write(new_content)
                        print(f"Processed {file_path}")
                except Exception as e:
                    print(f"Error processing {file_path}: {e}")

# Process childs
process_recursive('src/objects/fields/fieldset/childs')

# Process FieldSet.java
fieldset_path = 'src/objects/fields/fieldset/FieldSet.java'
if os.path.exists(fieldset_path):
    with open(fieldset_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    replacements = [
        ('!Objects.equals(this.difficulty, "헬")', '!Objects.equals(this.difficulty, "Hell")'),
        ('this.difficulty.equals("헬")', 'this.difficulty.equals("Hell")'),
        ('" / ์ œ한횟수 : "', '" / Limit : "'),
        ('"จำนวนเข้าปัจจุบัน : "', '"จำนวนการเข้าปัจจุบัน : "')
    ]
    
    modified = False
    for old, new in replacements:
        if old in content:
            content = content.replace(old, new)
            modified = True
    
    if modified:
        with open(fieldset_path, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"Processed {fieldset_path}")
