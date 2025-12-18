#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
MapleStory JavaScript Localization Script v4 - Final
=====================================================
Complete exact-match translations from Korean source files.
"""

import os
import re
import sys
from pathlib import Path
from datetime import datetime

try:
    import chardet
    HAS_CHARDET = True
except ImportError:
    HAS_CHARDET = False


# EXACT STRING TRANSLATIONS
# Extracted directly from source files - sorted by length (longest first)
TRANSLATIONS = {
    # Boss.js - Party disband messages (lines 403, 420)
    "파티원 혹은 파티장이 파티를 그만두거나 맵을 이동하여 원정대가 해체됩니다.": 
        "สมาชิกหรือหัวหน้าปาร์ตี้ออกจากปาร์ตี้หรือย้ายแมพ ทำให้ปาร์ตี้ถูกยุบ",
    
    # Boss.js - Lucid clear (line 231)
    "클리어를 축하드립니다! 오르골이 지급되었으니, 입장 엔피시에게 가져다 주시길 바랍니다. 자동으로 퇴장됩니다.":
        "ยินดีด้วยนะ เคลียร์แล้ว! ได้รับกล่องดนตรี เอาไปให้ NPC ที่ทางเข้าด้วยนะ จะย้ายออกอัตโนมัติ",
    
    # Boss.js - Attack on Titan clear (line 242)
    "클리어를 축하드립니다! 토벌 증표가 지급되었으니, 진격의 거인 엔피시에게 가져다 주시길 바랍니다. 자동으로 퇴장됩니다.":
        "ยินดีด้วยนะ เคลียร์แล้ว! ได้รับเหรียญปราบ เอาไปให้ NPC Attack on Titan ด้วยนะ จะย้ายออกอัตโนมัติ",
    
    # Boss.js - Clear messages with boss names
    "파풀라투스 클리어를 축하드립니다! 자동으로 퇴장됩니다.": 
        "ยินดีด้วยนะ เคลียร์ Papulatus แล้ว! จะย้ายออกอัตโนมัติ",
    "힐라 클리어를 축하드립니다! 자동으로 퇴장됩니다.": 
        "ยินดีด้วยนะ เคลียร์ Hilla แล้ว! จะย้ายออกอัตโนมัติ",
    "카오스 핑크빈 클리어를 축하드립니다! 자동으로 퇴장됩니다.": 
        "ยินดีด้วยนะ เคลียร์ Chaos Pink Bean แล้ว! จะย้ายออกอัตโนมัติ",
    "반 레온 클리어를 축하드립니다! 자동으로 퇴장됩니다.": 
        "ยินดีด้วยนะ เคลียร์ Von Leon แล้ว! จะย้ายออกอัตโนมัติ",
    "매그너스 클리어를 축하드립니다! 자동으로 퇴장됩니다.": 
        "ยินดีด้วยนะ เคลียร์ Magnus แล้ว! จะย้ายออกอัตโนมัติ",
    "아카이럼 클리어를 축하드립니다! 자동으로 퇴장됩니다.": 
        "ยินดีด้วยนะ เคลียร์ Arkarium แล้ว! จะย้ายออกอัตโนมัติ",
    "시그너스 클리어를 축하드립니다! 자동으로 퇴장됩니다.": 
        "ยินดีด้วยนะ เคลียร์ Cygnus แล้ว! จะย้ายออกอัตโนมัติ",
    "카오스 혼테일 클리어를 축하드립니다! 자동으로 퇴장됩니다.": 
        "ยินดีด้วยนะ เคลียร์ Chaos Horntail แล้ว! จะย้ายออกอัตโนมัติ",
    "카오스 피에르 클리어를 축하드립니다! 자동으로 퇴장됩니다.": 
        "ยินดีด้วยนะ เคลียร์ Chaos Pierre แล้ว! จะย้ายออกอัตโนมัติ",
    "카오스 반반 클리어를 축하드립니다! 자동으로 퇴장됩니다.": 
        "ยินดีด้วยนะ เคลียร์ Chaos Von Bon แล้ว! จะย้ายออกอัตโนมัติ",
    "카오스 벨룸 클리어를 축하드립니다! 자동으로 퇴장됩니다.": 
        "ยินดีด้วยนะ เคลียร์ Chaos Vellum แล้ว! จะย้ายออกอัตโนมัติ",
    "클리어를 축하드립니다! 자동으로 퇴장됩니다.": 
        "ยินดีด้วยนะ เคลียร์แล้ว! จะย้ายออกอัตโนมัติ",
    
    # Boss.js - Other messages
    "시간이 지나, 파티가 해체됩니다.": "หมดเวลาแล้ว ปาร์ตี้ถูกยุบ",
    "이동되었습니다.": "ย้ายแล้ว",
    "2페이지로 넘어갑니다.": "กำลังไปเฟส 2",
    "3페이지로 넘어갑니다.": "กำลังไปเฟส 3",
    "데스카운트를 모두 소모하였습니다.": "ใช้ Death Count หมดแล้ว",
    
    # Boss name comparisons (for server matching - use English/romanized)
    "검은마법사": "Black Mage",
    "카오스 파풀라투스": "Chaos Papulatus",
    "카오스 핑크빈": "Chaos Pink Bean",
    "하드 힐라": "Hard Hilla",
    "반 레온": "Von Leon",
    "매그 너스": "Magnus",
    "아카이럼": "Arkarium",
    "시그 너스": "Cygnus",
    "카오스 혼테일": "Chaos Horntail",
    "카오스 피에르": "Chaos Pierre",
    "카오스 반반": "Chaos Von Bon",
    "카오스 벨룸": "Chaos Vellum",
    "블러디 퀸": "Bloody Queen",
    "진힐라": "Jin Hilla",
    "진격의거인": "Attack on Titan",
    "루시드": "Lucid",
    "데미안": "Demian",
    "매그너스": "Magnus",
    "시그너스": "Cygnus",
    "혼테일": "Horntail",
    "벨룸": "Vellum",
    "듄켈": "Dunkel",
    "더스크": "Dusk",
    "파풀라투스": "Papulatus",
    "힐라": "Hilla",
    "스우": "Lotus",
    "윌": "Will",
    
    # Arkarium.js Korean comments
    "이벤트매니저 초기화할 내용(Channel별로 적용됨)": "Event manager initialization (Applied per Channel)",
    "ㄱㄷㄱㄷ": "standby",
    "// 탈퇴": "// Leave party",
    "탈퇴": "Leave party",
    
    # Common words for partial matches - game terms
    "파티": "ปาร์ตี้",
    "원정대": "สหพันธ์",
    "맵": "แมพ",
    "보스": "บอส",
    "클리어": "เคลียร์",
    "자동으로": "อัตโนมัติ",
    "엔피시": "NPC",
    
    # Top 50 Korean words by frequency - Thai translations
    "아이템을": "ไอเทม",
    "아이템이": "ไอเทม",
    "아이템": "ไอเทม",
    "데미지": "ดาเมจ",
    "있습니다": "",
    "교환": "แลกเปลี่ยน",
    "나눔고딕": "NanumGothic",  # Font name - keep as is
    "현재": "ปัจจุบัน",
    "다시": "อีกครั้ง",
    "스타포스": "Star Force",
    "올스탯": "All Stat",
    "강화": "เสริมพลัง",
    "공격력": "พลังโจมตี",
    "주세요": "",
    "스페셜": "Special",
    "헤어": "ผม",
    "캐시": "Cash",
    "개를": "ชิ้น",
    "보상": "รางวัล",
    "메소": "เมโซ",
    "에서": "",
    "패키지명": "ชื่อแพกเกจ",
    "루나": "Luna",
    "쁘띠": "Petit",
    "아케인셰이드": "Arcane Shade",
    "입니다": "",
    "없습니다": "",
    "선택": "เลือก",
    "공마": "Attack/Magic",
    "부족합니다": " ไม่เพียงพอ",
    "바랍니다": "",
    "심볼": "Symbol",
    "이상": "ขึ้นไป",
    "레벨": "Level",
    "아케인": "Arcane",
    "인벤토리": "อินเวนทอรี่",
    "이미": "แล้ว",
    "패키지코드": "รหัสแพกเกจ",
    "있는": "",
    "정말": "จริงๆ",
    "횟수": "ครั้ง",
    "마력": "พลังเวทย์",
    "라이딩": "Riding",
    "보조무기": "อาวุธรอง",
    "지급": "ได้รับ",
    "가능한": "ที่สามารถ",
    "해당": "",
    "소울조각": "Soul Piece",
    "전사": "Warrior",
    "검은색": "สีดำ",
    "장비": "อุปกรณ์",
    "무기": "อาวุธ",
    "방어구": "ชุดเกราะ",
    "물약": "โพชั่น",
    "스킬": "สกิล",
    "경험치": "EXP",
    "포인트": "พอยท์",
    "퀘스트": "เควส",
    "던전": "ดันเจี้ยน",
    "길드": "กิลด์",
    "친구": "เพื่อน",
    "채팅": "แชท",
    "초대": "เชิญ",
    "확인": "ยืนยัน",
    "취소": "ยกเลิก",
    "완료": "เสร็จสิ้น",
    "실패": "ล้มเหลว",
    "성공": "สำเร็จ",
    
    # Second pass - remaining frequent Korean words
    "후원": "สนับสนุน",
    "으로": "",
    "되었습니다": "แล้ว",
    "상자": "กล่อง",
    "받을": "รับ",
    "사용": "ใช้",
    "스킨": "Skin",
    "스탯": "Stat",
    "이볼빙": "Evolving",
    "계정": "บัญชี",
    "받고": "รับ",
    "다른": "อื่น",
    "스크립트": "Script",
    "싶은": "",
    "메강": "เมโซเสริมพลัง",
    "입장": "เข้า",
    "불가능합니다": "ไม่สามารถ",
    "갯수": "จำนวน",
    "방지": "ป้องกัน",
    "나왔습니다": "ออกมาแล้ว",
    "메이플": "Maple",
    "확률": "โอกาส",
    "사용하여": "ใช้",
    "시스템": "ระบบ",
    "획득": "ได้รับ",
    "번째": "ที่",
    "장착중": "กำลังสวมใส่",
    "합니다": "",
    "충전": "เติม",
    "저장": "บันทึก",
    "최대": "สูงสุด",
    "최소": "ต่ำสุด",
    "필요": "ต้องการ",
    "가격": "ราคา",
    "구매": "ซื้อ",
    "판매": "ขาย",
    "등록": "ลงทะเบียน",
    "삭제": "ลบ",
    "수정": "แก้ไข",
    "추가": "เพิ่ม",
    "변경": "เปลี่ยน",
    "설정": "ตั้งค่า",
    "결과": "ผลลัพธ์",
    "오류": "ข้อผิดพลาด",
    "경고": "คำเตือน",
    "정보": "ข้อมูล",
}

# Statistics
stats = {
    "files_scanned": 0,
    "files_with_korean": 0,
    "files_converted_utf8": 0,
    "translations_applied": 0,
    "files_modified": 0,
    "preserved_korean": [],
    "errors": [],
}


def detect_encoding(file_path):
    """Detect file encoding."""
    with open(file_path, 'rb') as f:
        raw = f.read()
    
    if raw.startswith(b'\xef\xbb\xbf'):
        return 'utf-8-sig', raw[3:]
    if raw.startswith(b'\xff\xfe'):
        return 'utf-16-le', raw[2:]
    if raw.startswith(b'\xfe\xff'):
        return 'utf-16-be', raw[2:]
    
    if HAS_CHARDET:
        result = chardet.detect(raw)
        if result['encoding']:
            return result['encoding'].lower(), raw
    
    for enc in ['utf-8', 'euc-kr', 'cp949']:
        try:
            raw.decode(enc)
            return enc, raw
        except UnicodeDecodeError:
            continue
    
    return 'utf-8', raw


def has_korean(text):
    """Check for Korean characters."""
    return bool(re.search(r'[\uAC00-\uD7AF\u1100-\u11FF\u3130-\u318F]', text))


def translate_content(content):
    """Apply translations - longest matches first."""
    count = 0
    
    # Sort by length descending for proper matching
    sorted_trans = sorted(TRANSLATIONS.items(), key=lambda x: len(x[0]), reverse=True)
    
    for korean, translation in sorted_trans:
        if korean in content:
            num = content.count(korean)
            content = content.replace(korean, translation)
            count += num
    
    return content, count


def process_file(file_path):
    """Process single JS file."""
    global stats
    stats["files_scanned"] += 1
    
    try:
        encoding, raw = detect_encoding(file_path)
        
        try:
            content = raw.decode(encoding)
        except (UnicodeDecodeError, LookupError):
            content = raw.decode('utf-8', errors='replace')
        
        original = content
        
        if encoding not in ['utf-8']:
            stats["files_converted_utf8"] += 1
        
        if has_korean(content):
            stats["files_with_korean"] += 1
            content, count = translate_content(content)
            stats["translations_applied"] += count
            
            remaining = set(re.findall(r'[\uAC00-\uD7AF]+', content))
            if remaining:
                stats["preserved_korean"].append({
                    "file": file_path.name,
                    "korean": list(remaining)[:5]
                })
        
        if content != original:
            with open(file_path, 'w', encoding='utf-8', newline='\n') as f:
                f.write(content)
            stats["files_modified"] += 1
            return True
        
        return False
        
    except Exception as e:
        stats["errors"].append(f"{file_path.name}: {str(e)}")
        return False


def find_js_files(directory):
    """Find all JS files."""
    return [Path(root) / f for root, _, files in os.walk(directory) for f in files if f.endswith('.js')]


def main():
    base = Path(__file__).parent / "scripts" / "Royal"
    
    if not base.exists():
        print(f"Error: {base} not found", file=sys.stderr)
        return 1
    
    js_files = find_js_files(base)
    
    for f in js_files:
        process_file(f)
    
    # Print summary (ASCII-safe)
    print("=" * 60)
    print("LOCALIZATION COMPLETE")
    print("=" * 60)
    print(f"Files scanned:       {stats['files_scanned']}")
    print(f"Files with Korean:   {stats['files_with_korean']}")
    print(f"Files to UTF-8:      {stats['files_converted_utf8']}")
    print(f"Translations:        {stats['translations_applied']}")
    print(f"Files modified:      {stats['files_modified']}")
    print(f"Preserved Korean:    {len(stats['preserved_korean'])} files")
    print(f"Errors:              {len(stats['errors'])}")
    print("=" * 60)
    
    # Save report
    import json
    report = {
        "timestamp": datetime.now().isoformat(),
        "stats": {k: v for k, v in stats.items() if k not in ["preserved_korean", "errors"]},
        "preserved_files": len(stats["preserved_korean"]),
        "error_count": len(stats["errors"])
    }
    
    with open(Path(__file__).parent / "localization_report.json", 'w', encoding='utf-8') as f:
        json.dump(report, f, indent=2)
    
    return 0


if __name__ == "__main__":
    sys.exit(main())
