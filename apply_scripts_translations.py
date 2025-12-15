# -*- coding: utf-8 -*-
import os

# Translation Map for frequent lines found in scripts/
TRANSLATION_MAP = {
    # Frequency: 145
    '// 세팅': '// Setting',
    # Frequency: 120
    '[[0, 0], 0], // 옌터': '[[0, 0], 0], // Enter',
    # Frequency: 112
    'cm.showInfo("[스킬] "+name+" 라이딩 획득!!");': 'cm.showInfo("[Skill] "+name+" Riding Acquired!!");',
    # Frequency: 73
    'one = Math.floor(Math.random() * 5) + 1 // 최소 10 최대 35 , 혼테일': 'one = Math.floor(Math.random() * 5) + 1 // Min 10 Max 35 , Horntail',
    # Frequency: 38
    'cm.sendOk("아이템이 나왔습니다.");': 'cm.sendOk("ได้รับไอเทมเรียบร้อยแล้ว");',
    # Frequency: 32
    '별노 = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"': 'StarYellow = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"',
    '색 = "#fc0xFF6600CC#"': 'Color = "#fc0xFF6600CC#"',
    '검은색 = "#fc0xFF000000#"': 'Black = "#fc0xFF000000#"',
    "cm.showInfo('[스킬] '+name+' 라이딩 획득!!');": "cm.showInfo('[Skill] '+name+' Riding Acquired!!');",
    # Frequency: 31
    '별파 = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"': 'StarBlue = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"',
    '별흰 = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"': 'StarWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"',
    '별갈 = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"': 'StarBrown = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"',
    '별빨 = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"': 'StarRed = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"',
    '별검 = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"': 'StarBlack = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"',
    '별보 = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"': 'StarPurple = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"',
    '별 = "#fUI/FarmUI.img/objectStatus/star/whole#"': 'Star = "#fUI/FarmUI.img/objectStatus/star/whole#"',
    '보상 = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"': 'Reward = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"',
    # Frequency: 30
    '보라 = "#fMap/MapHelper.img/weather/starPlanet/7#";': 'Purple = "#fMap/MapHelper.img/weather/starPlanet/7#";',
    '파랑 = "#fMap/MapHelper.img/weather/starPlanet/8#";': 'Blue = "#fMap/MapHelper.img/weather/starPlanet/8#";',
    '획득 = "#fUI/UIWindow2.img/QuestIcon/4/0#"': 'Obtain = "#fUI/UIWindow2.img/QuestIcon/4/0#"',
    '핑크색 ="#fc0xFFFF3366#"': 'Pink = "#fc0xFFFF3366#"',
    '분홍색 = "#fc0xFFF781D8#"': 'Pink = "#fc0xFFF781D8#"',
    'cm.sendOk("#fn나눔고딕##r장비창을 확인해주세요");': 'cm.sendOk("#fnArial##rกรุณาตรวจสอบช่องเก็บอุปกรณ์");',
    # Frequency: 29
    'cm.sendOk("#b#i"+need+"##z"+need+"##k가 없는 것 같은데요?");': 'cm.sendOk("#b#i"+need+"##z"+need+"##k ดูเหมือนจะไม่มีนะครับ?");',
    # Frequency: 28
    '//로그작성': '//Log',
    # Frequency: 20
    '엔터 = "\\r\\n"': 'Enter = "\\r\\n"',
    '엔터2 = "\\r\\n\\r\\n"': 'Enter2 = "\\r\\n\\r\\n"',
    'say += "#r<아이템 정보>\\r\\n";': 'say += "#r<Item Information>\\r\\n";',
    '//[[아이템코드, 갯수], 확률]': '//[[ItemCode, Qty], Probability]',
    '[[2439962,1], 1], // 초케인 무기': '[[2439962,1], 1], // Arcane Umbra Weapon',
    '[[2439961,1], 1], // 초케인 장비': '[[2439961,1], 1], // Arcane Umbra Equipment',
    # Frequency: 18
    'say += "#fs11##r<아이템 정보>\\r\\n";': 'say += "#fs11##r<Item Information>\\r\\n";',
    'cm.sendOk("#fn나눔고딕#교환완료");': 'cm.sendOk("#fnArial#การแลกเปลี่ยนเสร็จสมบูรณ์");',
    'cm.sendOk("#fs11#장비,소비,기타 칸을 5칸 이상 비워주세요");': 'cm.sendOk("#fs11#กรุณาเว้นช่องว่างในกระเป๋า Equip, Use, Etc อย่างน้อย 5 ช่อง");',
    'cm.sendOk("#fs11#네");': 'cm.sendOk("#fs11#ครับ/ค่ะ");',
    # Frequency: 12
    'cm.sendOk("오류 오류 박스 없음");': 'cm.sendOk("ข้อผิดพลาด: ไม่พบกล่อง");',
    'cm.sendOk("장비칸에 빈 공간이 없습니다.");': 'cm.sendOk("ไม่มีช่องว่างในกระเป๋าอุปกรณ์");',
    # Frequency: 11
    '//입장 가능한 맵 갯수 총8개씩(이지와 노말은 통합)': '//Total 8 clickable maps (Easy and Normal are combined)',
    'msg += "\\r\\n해당 아이템 리스트를 확인하고 지급을 받으시겠어요?"': 'msg += "\\r\\nตรวจสอบรายการไอเทมแล้วต้องการรับของรางวัลหรือไม่?"',
    '// 	cm.sendOk("오류 오류 박스 없음");': '// 	cm.sendOk("Error: Box not found");',
    'msg += "\\r\\n아이템이 정상적으로 지급되었습니다."': 'msg += "\\r\\nได้รับไอเทมเรียบร้อยแล้ว"',
    'cm.sendOk("라이딩스킬이 성공적으로 적용 되었습니다.");': 'cm.sendOk("สกิล Riding ถูกใช้งานเรียบร้อยแล้ว");',
    'cm.sendOk("장비를 지급받을 수 있는 직업단계가 아닙니다. 전직이 가능한 레벨인 경우, 전직을 하신 후 장비를 받을 수 있습니다.");': 'cm.sendOk("ระดับอาชีพยังไม่สามารถรับอุปกรณ์ได้ หากเลเวลถึงกำหนด กรุณาเปลี่ยนคลาสก่อนรับอุปกรณ์");',
    'cm.sendOk("인벤토리를 확인하세요");': 'cm.sendOk("กรุณาตรวจสอบช่องเก็บของ");',
    'cm.sendOk("부족합니다.");': 'cm.sendOk("ไม่เพียงพอ");',
    'cm.sendOk("장비창에 공간이 부족합니다.");': 'cm.sendOk("พื้นที่ในช่อง Equip ไม่เพียงพอ");',
    # Frequency: 10
    'var unitWords    = [\'\', \'만 \', \'억 \', \'조 \', \'경 \'];': 'var unitWords    = [\'\', \'Ten Thousand \', \'Hundred Million \', \'Trillion \', \'Quadrillion \'];',
    'cm.sendOk("인벤토리 공간이 최소한 9칸은 필요합니다. 장비 탭의 공간을 9칸이상 비워주신 후 다시 열어주세요.");': 'cm.sendOk("ต้องการช่องว่างอย่างน้อย 9 ช่อง กรุณาเคลียร์ช่อง Equip แล้วลองใหม่");',
    'wList.push(1402252); // 두손검': 'wList.push(1402252); // Two-Handed Sword',
    'text += "상자를 개봉하시겠습니까?";': 'text += "คุณต้องการเปิดกล่องหรือไม่?";',
    # Frequency: 9
    'var 별 = "#fUI/FarmUI.img/objectStatus/star/whole#";': 'var Star = "#fUI/FarmUI.img/objectStatus/star/whole#";',
    'cm.sendOk("강화할 장비를 소지하고 있는지 확인해 주세요.");': 'cm.sendOk("กรุณาตรวจสอบว่ามีอุปกรณ์ที่ต้องการตีบวกหรือไม่");',
    'cm.sendOk("#fs11#심볼 아이템은 강화할 수 없습니다.");': 'cm.sendOk("#fs11#ไอเทม Symbol ไม่สามารถตีบวกได้");',
    'cm.sendOk("비정상적인 접근");': 'cm.sendOk("การเข้าถึงผิดปกติ");',
    # Frequency: 7
    'cm.sendOk("#fs11#이미 20강까지 강화가 완료된 아이템 입니다.");': 'cm.sendOk("#fs11#ไอเทมนี้ตีบวก +20 เรียบร้อยแล้ว");',
    'cm.sendOk("#fs11#이미 초월 강화가 진행된 아이템 입니다.");': 'cm.sendOk("#fs11#ไอเทมนี้ได้ทำการตีบวก Transcendence แล้ว");',
    'cm.sendOk("#fs11#이미 한 번 이상 강화가 된 아이템에는 사용할 수 없습니다.");': 'cm.sendOk("#fs11#ไม่สามารถใช้กับไอเทมที่ตีบวกไปแล้วได้");',
    'cm.sendOk("수수료가 부족하여 강화가 불가능합니다.");': 'cm.sendOk("ค่าธรรมเนียมไม่พอ ไม่สามารถตีบวกได้");',
    'cm.sendOk("#fs11#강화가 성공하였습니다.");': 'cm.sendOk("#fs11#ตีบวกสำเร็จ!");',
    'msg += "#L1#아이템 리스트보기 (남)"+enter;': 'msg += "#L1#View Item List (Male)"+enter;',
    'msg += "#L1#남성"+enter;': 'msg += "#L1#Male"+enter;',
    'msg += "#L2#여성"+enter;': 'msg += "#L2#Female"+enter;',
    'msg += "#L1##b사용하기" + enter;': 'msg += "#L1##bUse" + enter;',
    'cm.sendOk("#fs11#상자가 부족합니다");': 'cm.sendOk("#fs11#กล่องไม่เพียงพอ");',
    'cm.sendOk("#fs11#교환이 완료되었습니다.");': 'cm.sendOk("#fs11#แลกเปลี่ยนเรียบร้อยแล้ว");',
    # Frequency: 6
    'cm.sendOk("#fs11#저장된 데미지 스킨이 없습니다.");': 'cm.sendOk("#fs11#ไม่มี Damage Skin ที่บันทึกไว้");',
    'cm.sendOk("소비창과 기타창을 10칸이상 비워주세요.");': 'cm.sendOk("กรุณาเว้นช่องว่างใน Use และ Etc อย่างน้อย 10 ช่อง");',
    'cm.sendOk("#fn나눔고딕#교환완료\\r\\n");': 'cm.sendOk("#fnArial#การแลกเปลี่ยนเสร็จสมบูรณ์\\r\\n");',
    'cm.sendOk("장비창을 1칸 이상 비운 뒤 다시 사용해 주세요.");': 'cm.sendOk("กรุณาเว้นช่องว่างใน Equip อย่างน้อย 1 ช่องแล้วลองใหม่");',
    'msg += "\\r\\n\\r\\n#b < 교환 아이템 안내 >\\r\\n\\r\\n";': 'msg += "\\r\\n\\r\\n#b < Item Exchange Info >\\r\\n\\r\\n";',
    'msg += "#L2#아이템 리스트보기 (여)"+enter+enter;': 'msg += "#L2#View Item List (Female)"+enter+enter;',
    'cm.sendOk("#fs11#지급이 완료되었습니다.");': 'cm.sendOk("#fs11#ได้รับของเรียบร้อยแล้ว");',
    'cm.sendOk("#fs11#오류가 발생 했어요.");': 'cm.sendOk("#fs11#เกิดข้อผิดพลาด");',
    # Frequency: 5
    'cm.sendOk("장비창에 공간이 부족해");': 'cm.sendOk("ช่อง Equip ไม่เพียงพอ");',
    'cm.sendOk("조각이 모자랍니다");': 'cm.sendOk("ชิ้นส่วนไม่เพียงพอ");',
    'cm.sendOk("#fs11#장비칸에 빈 공간이 없습니다.");': 'cm.sendOk("#fs11#ไม่มีช่องว่างในกระเป๋าอุปกรณ์");',
    '끝 = "#fc0xFF000000#"': 'End = "#fc0xFF000000#"',
    'cm.sendOk("#fs11#네");': 'cm.sendOk("#fs11#ครับ/ค่ะ");',
    'cm.sendOk("해당 캐릭터가 접속 등을 종료하여 연결이 끊어졌습니다.");': 'cm.sendOk("ตัวละครดังกล่าวออกจากเกมหรือขาดการเชื่อมต่อ");',
    '보라색 = "#fc0xFF6600CC#"': 'Purple = "#fc0xFF6600CC#"',
    '// 강화 가능한 아이템만 출력': '// Output only upgradeable items',
    '/* 전사 */': '/* Warrior */',
    '/* 마법사 */': '/* Magician */',
    'cm.sendOk("아이템이 지급되었습니다.");': 'cm.sendOk("ได้รับไอเทมแล้ว");',
    'cm.sendOk("데미지 스킨 저장 슬롯이 이미 최대입니다.");': 'cm.sendOk("ช่องเก็บ Damage Skin เต็มแล้ว");',
    'cm.sendOk("#r조건이 일치하지 않습니다.#k");': 'cm.sendOk("#rเงื่อนไขไม่ตรงกัน#k");',
    '[5060048, 10]//레드애플': '[5060048, 10]//Golden Apple',
    '[5060048, 20]//레드애플': '[5060048, 20]//Golden Apple',
    '} else if (sel == 1) { // 성형 관련': '} else if (sel == 1) { // Face Surgery',
    '} else if (sel == 2) { // 안드로이드': '} else if (sel == 2) { // Android',
    'if(S3 < 10) // 인벤토리': 'if(S3 < 10) // Inventory',
    'cm.sendOk("파티를 맺어야만 입장할 수 있다.");': 'cm.sendOk("ต้องมีปาร์ตี้จึงจะเข้าได้");',
    'cm.sendOk("파티장만 입장을 신청할 수 있다.");': 'cm.sendOk("หัวหน้าปาร์ตี้เท่านั้นที่สามารถขอเข้าได้");',
    'cm.sendOk("1인 이상의 파티에 속해야만 입장할 수 있습니다.");': 'cm.sendOk("ต้องอยู่ในปาร์ตี้ที่มีสมาชิก 1 คนขึ้นไปจึงจะเข้าได้");',
    'cm.sendOk("오늘은 더 이상 입장할 수 없다. 다음에 다시 보도록 하지.");': 'cm.sendOk("วันนี้ไม่สามารถเข้าได้อีกแล้ว ไว้เจอกันใหม่คราวหลัง");',
    'cm.sendOkS("#fs11#네", 2);': 'cm.sendOkS("#fs11#ครับ/ค่ะ", 2);'
}

def apply_translations(root_dir):
    modified_count = 0
    for subdir, dirs, files in os.walk(root_dir):
        for file in files:
            if file.endswith(".js"):
                path = os.path.join(subdir, file)
                try:
                    with open(path, 'r', encoding='utf-8') as f:
                        lines = f.readlines()
                    
                    new_lines = []
                    file_modified = False
                    for line in lines:
                        original_line = line
                        # Check precise matches first (stripped)
                        stripped = line.strip()
                        
                        # We iterate through the map to find if the line CONTAINS the key
                        # This allows matching lines with indentation
                        # However, for safety, we should probably prefer exact match of the content parts
                        
                        matched = False
                        for korean, translation in TRANSLATION_MAP.items():
                            if korean in line:
                                # Replace only the Korean part if it's a substring match, 
                                # but the dictionary keys are mostly full lines or statements.
                                # BE CAREFUL: if key is "색 = ..." and line is "var 색 = ...", direct replace might work if key matches exactly.
                                
                                # Let's use simple string replacement
                                new_line = line.replace(korean, translation)
                                if new_line != line:
                                    new_lines.append(new_line)
                                    file_modified = True
                                    matched = True
                                    break
                        
                        if not matched:
                            new_lines.append(line)
                    
                    if file_modified:
                        with open(path, 'w', encoding='utf-8') as f:
                            f.writelines(new_lines)
                        modified_count += 1
                        print(f"Modified: {path}")
                except Exception as e:
                    pass
    print(f"Total files modified: {modified_count}")

if __name__ == "__main__":
    apply_translations(r"c:\Users\sonku\OneDrive\เอกสาร\GitHub\MapleStory_Base\scripts")
