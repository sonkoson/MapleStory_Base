#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
MapleStory JavaScript Localization Script v2
=============================================
This script processes JavaScript files to:
1. Normalize encoding to UTF-8 (NO BOM)
2. Translate Korean content to Thai (player-facing) and English (system/admin)
3. Translate Korean comments to English
"""

import os
import re
import sys
from pathlib import Path
from datetime import datetime

# Try to import chardet for encoding detection
try:
    import chardet
    HAS_CHARDET = True
except ImportError:
    HAS_CHARDET = False
    print("Warning: chardet not available, using fallback encoding detection")


# Korean to Thai translations (player-facing - MapleStory style, playful & friendly)
KOREAN_TO_THAI = {
    # Common system messages - translate to Thai
    "이동되었습니다.": "ย้ายแล้ว",
    "시간이 지나, Party가 해체됩니다.": "หมดเวลาแล้ว ปาร์ตี้ถูกยุบ",
    "데스카운트를 모두 소모하였습니다.": "ใช้ Death Count หมดแล้ว",
    "Party원 혹은 Party장이 Party를 그만두거나 Map을 이동하여 원정대가 해체됩니다.": "สมาชิกหรือหัวหน้าปาร์ตี้ออกจากปาร์ตี้หรือย้ายแมพ ทำให้ปาร์ตี้ถูกยุบ",
    
    # Phase/Page transitions
    "2페이지로 넘어갑니다.": "กำลังไปเฟส 2",
    "3페이지로 넘어갑니다.": "กำลังไปเฟส 3",
    
    # Clear messages - MapleStory style Thai (friendly, playful)
    "클리어를 축하드립니다!": "ยินดีด้วยนะ เคลียร์แล้ว!",  
    "자동으로 Exit됩니다.": "จะย้ายออกไปโดยอัตโนมัติ",
    "오르골이 지급되었으니, Enter 엔피시에게 가져다 주시길 바랍니다.": "ได้รับกล่องดนตรีแล้ว เอาไปให้ NPC Enter ด้วยนะ",
    "토벌 증표가 지급되었으니": "ได้รับเหรียญปราบแล้ว",
    "엔피시에게 가져다 주시길 바랍니다.": "เอาไปให้ NPC ด้วยนะ",
    
    # Boss names - keep as romanized/English for game consistency
    "검은마법사": "Black Mage",
    "카오스 핑크빈": "Chaos Pink Bean",
    "카오스 파풀라투스": "Chaos Papulatus",
    "하드 힐라": "Hard Hilla",
    "반 레온": "Von Leon",
    "매그 너스": "Magnus",
    "매그너스": "Magnus",
    "아카이럼": "Arkarium",
    "시그 너스": "Cygnus",
    "시그너스": "Cygnus",
    "카오스 혼테일": "Chaos Horntail",
    "혼테일": "Horntail",
    "카오스 피에르": "Chaos Pierre",
    "카오스 반반": "Chaos Von Bon",
    "카오스 벨룸": "Chaos Vellum",
    "블러디 퀸": "Bloody Queen",
    "벨룸": "Vellum",
    "윌": "Will",
    "진힐라": "Jin Hilla",
    "듄켈": "Dunkel",
    "더스크": "Dusk",
    "파풀라투스": "Papulatus",
    "진격의거인": "Attack on Titan",
    "루시드": "Lucid",
    "데미안": "Demian",
    "힐라": "Hilla",
    
    # Common dialogue phrases - Thai with MapleStory tone
    "안녕하세요": "สวัสดี",
    "감사합니다": "ขอบคุณนะ",
    "축하합니다": "ยินดีด้วย",
    "환영합니다": "ยินดีต้อนรับ",
    "도움이 필요하신가요": "ต้องการความช่วยเหลือไหม",
    "무엇을 도와드릴까요": "ให้ช่วยอะไรดี",
    "다시 한번 생각해 보세요": "ลองคิดดูอีกทีนะ",
    "잘 생각해 보세요": "คิดให้ดีนะ",
    "다음에 만나요": "แล้วเจอกันใหม่นะ",
    "행운을 빕니다": "ขอให้โชคดี",
    "조심하세요": "ระวังตัวด้วยนะ",
    "화이팅": "สู้ๆ นะ",
    "파이팅": "สู้ๆ นะ",
    "힘내세요": "สู้ๆ นะ",
    "수고하셨습니다": "เหนื่อยแล้วนะ",
    "고생하셨습니다": "เหนื่อยแล้วนะ",
    "실패했습니다": "ล้มเหลว",
    "성공했습니다": "สำเร็จแล้ว",
    "완료되었습니다": "เสร็จสิ้นแล้ว",
    "진행 중입니다": "กำลังดำเนินการ",
    "취소되었습니다": "ยกเลิกแล้ว",
    "확인해 주세요": "ตรวจสอบด้วยนะ",
    "선택해 주세요": "เลือกเลย",
    "입력해 주세요": "กรอกด้วยนะ",
    "기다려 주세요": "รอสักครู่นะ",
    "잠시만 기다려 주세요": "รอสักครู่นะ",
    "다시 시도해 주세요": "ลองใหม่อีกครั้งนะ",
    "오류가 발생했습니다": "เกิดข้อผิดพลาด",
    "접속이 끊어졌습니다": "การเชื่อมต่อขาดหาย",
    "서버 점검 중입니다": "เซิร์ฟเวอร์กำลังปิดปรับปรุง",
    "업데이트 중입니다": "กำลังอัพเดท",
    "로그인해 주세요": "ล็อกอินด้วยนะ",
    "로그아웃되었습니다": "ล็อกเอาท์แล้ว",
    
    # Quest/Item related - Thai
    "퀘스트": "เควส",
    "아이템": "ไอเทม",
    "장비": "อุปกรณ์",
    "무기": "อาวุธ",
    "방어구": "ชุดเกราะ",
    "악세서리": "เครื่องประดับ",
    "물약": "โพชั่น",
    "스킬": "สกิล",
    "레벨": "เลเวล",
    "경험치": "ค่าประสบการณ์",
    "메소": "เมโซ",
    "포인트": "พอยท์",
    "보상": "รางวัล",
    "미션": "มิชชั่น",
    "이벤트": "อีเวนท์",
    "상점": "ร้านค้า",
    "창고": "คลังเก็บของ",
    "인벤토리": "อินเวนทอรี่",
    "가방": "กระเป๋า",
    "슬롯": "สล็อต",
    "강화": "เสริมพลัง",
    "주문서": "ม้วน Scroll",
    "스타포스": "Star Force",
    "큐브": "คิวบ์",
    "옵션": "ออปชั่น",
    "잠재능력": "ศักยภาพ",
    "추가옵션": "ออปชั่นเสริม",
    
    # Party/Guild - Thai
    "파티": "ปาร์ตี้",
    "길드": "กิลด์",
    "원정대": "สหพันธ์",
    "친구": "เพื่อน",
    "귓속말": "กระซิบ",
    "채팅": "แชท",
    "초대": "เชิญ",
    "추방": "เตะออก",
    "탈퇴": "ออกจากกลุ่ม",
    "가입": "เข้าร่วม",
    "해체": "ยุบกลุ่ม",
    
    # Map/Location - Thai
    "마을": "หมู่บ้าน",
    "필드": "ทุ่ง",
    "던전": "ดันเจี้ยน",
    "보스": "บอส",
    "맵": "แมพ",
    "포탈": "พอร์ทัล",
    "입구": "ทางเข้า",
    "출구": "ทางออก",
    "이동": "ย้าย",
    "워프": "วาร์ป",
    "텔레포트": "เทเลพอร์ต",
}

# Korean comments to English (for system/debug code comments)
KOREAN_COMMENTS_TO_ENGLISH = {
    "이벤트매니저 초기화할 내용": "Event manager initialization content",
    "Channel별로 적용됨": "Applied per Channel",
    "ㄱㄷㄱㄷ": "standby",
    "탈퇴": "Leave party",
}

# Track statistics
stats = {
    "files_scanned": 0,
    "files_with_korean": 0,
    "files_converted_utf8": 0,
    "dialogues_translated": 0,
    "comments_translated": 0,
    "files_modified": 0,
    "preserved_korean": [],
    "errors": [],
}


def detect_encoding(file_path):
    """Detect file encoding using chardet or fallback."""
    with open(file_path, 'rb') as f:
        raw = f.read()
    
    # Check for BOM first
    if raw.startswith(b'\xef\xbb\xbf'):
        return 'utf-8-sig', raw[3:]
    if raw.startswith(b'\xff\xfe'):
        return 'utf-16-le', raw[2:]
    if raw.startswith(b'\xfe\xff'):
        return 'utf-16-be', raw[2:]
    
    if HAS_CHARDET:
        result = chardet.detect(raw)
        encoding = result['encoding']
        confidence = result['confidence']
        
        # Handle common encoding names
        if encoding:
            encoding = encoding.lower()
            if encoding in ['euc-kr', 'iso-8859-1', 'windows-1252']:
                # Try UTF-8 first for mixed content
                try:
                    raw.decode('utf-8')
                    return 'utf-8', raw
                except UnicodeDecodeError:
                    pass
            return encoding, raw
    
    # Manual fallback detection
    encodings_to_try = ['utf-8', 'euc-kr', 'cp949', 'iso-8859-1']
    for enc in encodings_to_try:
        try:
            raw.decode(enc)
            return enc, raw
        except UnicodeDecodeError:
            continue
    
    return 'utf-8', raw  # Default


def has_korean(text):
    """Check if text contains Korean characters."""
    return bool(re.search(r'[\uAC00-\uD7AF\u1100-\u11FF\u3130-\u318F]', text))


def translate_korean_strings(content):
    """Translate Korean strings to Thai/English for player-facing content."""
    translated_count = 0
    
    # Sort by length (longest first) to avoid partial replacements
    sorted_translations = sorted(KOREAN_TO_THAI.items(), key=lambda x: len(x[0]), reverse=True)
    
    for korean, replacement in sorted_translations:
        if korean in content:
            count = content.count(korean)
            content = content.replace(korean, replacement)
            translated_count += count
    
    return content, translated_count


def translate_korean_comments(content):
    """Translate Korean comments to English."""
    translated_count = 0
    
    for korean, english in KOREAN_COMMENTS_TO_ENGLISH.items():
        if korean in content:
            content = content.replace(korean, english)
            translated_count += 1
    
    return content, translated_count


def process_file(file_path):
    """Process a single JavaScript file."""
    global stats
    stats["files_scanned"] += 1
    
    try:
        # Detect original encoding
        original_encoding, raw_bytes = detect_encoding(file_path)
        
        # Decode content
        try:
            content = raw_bytes.decode(original_encoding)
        except (UnicodeDecodeError, LookupError):
            # Fallback to utf-8 with replacement
            content = raw_bytes.decode('utf-8', errors='replace')
            original_encoding = 'utf-8-replaced'
        
        original_content = content
        encoding_changed = original_encoding not in ['utf-8']
        
        if encoding_changed:
            stats["files_converted_utf8"] += 1
        
        # Check for Korean content
        if has_korean(content):
            stats["files_with_korean"] += 1
            
            # Translate Korean strings
            content, dialog_count = translate_korean_strings(content)
            stats["dialogues_translated"] += dialog_count
            
            # Translate Korean comments
            content, comment_count = translate_korean_comments(content)
            stats["comments_translated"] += comment_count
            
            # Check for remaining Korean (preserved)
            remaining_korean = set(re.findall(r'[\uAC00-\uD7AF]+', content))
            if remaining_korean:
                stats["preserved_korean"].append({
                    "file": str(file_path.name),
                    "korean": list(remaining_korean)[:10]
                })
        
        # Only write if content changed
        if content != original_content:
            with open(file_path, 'w', encoding='utf-8', newline='\r\n') as f:
                f.write(content)
            stats["files_modified"] += 1
            return True
        
        return False
        
    except Exception as e:
        stats["errors"].append(f"{file_path.name}: {str(e)}")
        return False


def find_js_files(directory):
    """Find all JavaScript files in the directory."""
    js_files = []
    for root, dirs, files in os.walk(directory):
        for file in files:
            if file.endswith('.js'):
                js_files.append(Path(root) / file)
    return js_files


def main():
    """Main entry point."""
    script_dir = Path(__file__).parent
    scripts_dir = script_dir / "scripts" / "Royal"
    
    if not scripts_dir.exists():
        print(f"Error: Scripts directory not found at {scripts_dir}")
        return 1
    
    print("=" * 70)
    print("MapleStory JavaScript Localization Script v2")
    print("=" * 70)
    print(f"Start time: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
    print(f"Target directory: {scripts_dir}")
    print(f"Chardet available: {HAS_CHARDET}")
    print()
    
    # Find all JS files
    js_files = find_js_files(scripts_dir)
    total_files = len(js_files)
    print(f"Found {total_files} JavaScript files")
    print()
    
    # Process each file
    print("Processing files...")
    
    for i, file_path in enumerate(js_files):
        process_file(file_path)
        
        # Progress indicator
        if (i + 1) % 200 == 0:
            print(f"  Processed {i + 1}/{total_files} files...")
    
    print()
    print("=" * 70)
    print("SUMMARY REPORT")
    print("=" * 70)
    print(f"Total JS files scanned:     {stats['files_scanned']}")
    print(f"Files with Korean content:  {stats['files_with_korean']}")
    print(f"Files converted to UTF-8:   {stats['files_converted_utf8']}")
    print(f"Dialogues translated:       {stats['dialogues_translated']}")
    print(f"Comments translated:        {stats['comments_translated']}")
    print(f"Files modified:             {stats['files_modified']}")
    print()
    
    if stats["preserved_korean"]:
        print(f"Files with preserved Korean: {len(stats['preserved_korean'])}")
        for item in stats["preserved_korean"][:10]:
            korean_preview = ', '.join(item['korean'][:3])
            if len(item['korean']) > 3:
                korean_preview += '...'
            print(f"  - {item['file']}: {korean_preview}")
        if len(stats["preserved_korean"]) > 10:
            print(f"  ... and {len(stats['preserved_korean']) - 10} more files")
    
    if stats["errors"]:
        print()
        print(f"Errors: {len(stats['errors'])}")
        for err in stats["errors"][:5]:
            print(f"  - {err}")
    
    print()
    print(f"End time: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
    print("=" * 70)
    
    return 0


if __name__ == "__main__":
    sys.exit(main())
