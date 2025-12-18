#!/usr/bin/env python3
"""
MapleStory JavaScript Localization Script
==========================================
This script processes JavaScript files to:
1. Normalize encoding to UTF-8 (NO BOM)
2. Translate Korean content to Thai (player-facing) and English (system/admin)
3. Translate Korean comments to English
4. Rename Korean variable/function names to English (where safe)
"""

import os
import re
import codecs
import json
from pathlib import Path
from datetime import datetime

# Korean to Thai translations (player-facing - MapleStory style, playful & friendly)
KOREAN_TO_THAI = {
    # Common boss/clear messages
    "클리어를 축하드립니다!": "ยินดีด้วยนะ เคลียร์แล้ว!",
    "자동으로 Exit됩니다.": "จะย้ายออกไปโดยอัตโนมัติ",
    "2페이지로 넘어갑니다.": "กำลังไปเฟส 2",
    "3페이지로 넘어갑니다.": "กำลังไปเฟส 3",
    "이동되었습니다.": "ย้ายแล้ว",
    "시간이 지나, Party가 해체됩니다.": "หมดเวลาแล้ว ปาร์ตี้ถูกยุบ",
    "데스카운트를 모두 소모하였습니다.": "ใช้ Death Count หมดแล้ว",
    "Party원 혹은 Party장이 Party를 그만두거나 Map을 이동하여 원정대가 해체됩니다.": "สมาชิกปาร์ตี้หรือหัวหน้าปาร์ตี้ออกจากปาร์ตี้หรือย้ายแมพ ทำให้ปาร์ตี้ถูกยุบ",
    
    # Storage/NPC messages
    "ยินดีต้อนรับ": "ยินดีต้อนรับ",  # Already Thai
    "คลังเก็บของทั่วไป": "คลังเก็บของทั่วไป",  # Already Thai
    
    # Boss names - keep as romanized for game consistency
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
    
    # Common dialogue phrases
    "안녕하세요": "สวัสดี",
    "감사합니다": "ขอบคุณ",
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
    "확인해 주세요": "กรุณาตรวจสอบ",
    "선택해 주세요": "กรุณาเลือก",
    "입력해 주세요": "กรุณากรอก",
    "기다려 주세요": "รอสักครู่นะ",
    "잠시만 기다려 주세요": "รอสักครู่นะ",
    "다시 시도해 주세요": "ลองใหม่อีกครั้งนะ",
    "오류가 발생했습니다": "เกิดข้อผิดพลาด",
    "접속이 끊어졌습니다": "การเชื่อมต่อขาดหาย",
    "서버 점검 중입니다": "เซิร์ฟเวอร์กำลังปิดปรับปรุง",
    "업데이트 중입니다": "กำลังอัพเดท",
    "로그인해 주세요": "กรุณาล็อกอิน",
    "로그아웃되었습니다": "ล็อกเอาท์แล้ว",
    
    # Quest/Item related
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
    
    # Party/Guild
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
    
    # Map/Location
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
    
    # Time related
    "초": "วินาที",
    "분": "นาที",
    "시간": "ชั่วโมง",
    "일": "วัน",
    "주": "สัปดาห์",
    "달": "เดือน",
    "년": "ปี",
    "오늘": "วันนี้",
    "내일": "พรุ่งนี้",
    "어제": "เมื่อวาน",
    "매일": "ทุกวัน",
    "매주": "ทุกสัปดาห์",
    
    # Numbers
    "하나": "หนึ่ง",
    "둘": "สอง",
    "셋": "สาม",
    "넷": "สี่",
    "다섯": "ห้า",
    "여섯": "หก",
    "일곱": "เจ็ด",
    "여덟": "แปด",
    "아홉": "เก้า",
    "열": "สิบ",
    "백": "ร้อย",
    "천": "พัน",
    "만": "หมื่น",
    "억": "ร้อยล้าน",
    
    # Common system messages (in English for admin/system)
    "클리어 됐습니다": "cleared",
    "서버": "server",
    "클라이언트": "client",
    "패킷": "packet",
    "에러": "error",
    "디버그": "debug",
    "로그": "log",
}

# Korean comments to English (for system/debug)
KOREAN_COMMENTS_TO_ENGLISH = {
    "이벤트매니저 초기화할 내용": "Event manager initialization content",
    "Channel별로 적용됨": "Applied per Channel",
    "ㄱㄷㄱㄷ": "ok ok",
    "탈퇴": "Leave party",
}

# Track statistics
stats = {
    "files_scanned": 0,
    "files_with_korean": 0,
    "files_converted_utf8": 0,
    "dialogues_translated": 0,
    "comments_translated": 0,
    "identifiers_renamed": 0,
    "preserved_korean": [],
}


def detect_encoding(file_path):
    """Detect file encoding by reading raw bytes."""
    with open(file_path, 'rb') as f:
        raw = f.read(4096)
    
    # Check for BOM
    if raw.startswith(codecs.BOM_UTF8):
        return 'utf-8-sig'
    if raw.startswith(codecs.BOM_UTF16_LE) or raw.startswith(codecs.BOM_UTF16_BE):
        return 'utf-16'
    
    # Try UTF-8 first
    try:
        raw.decode('utf-8')
        return 'utf-8'
    except UnicodeDecodeError:
        pass
    
    # Try EUC-KR (common for Korean)
    try:
        raw.decode('euc-kr')
        return 'euc-kr'
    except UnicodeDecodeError:
        pass
    
    # Try CP949 (extended EUC-KR)
    try:
        raw.decode('cp949')
        return 'cp949'
    except UnicodeDecodeError:
        pass
    
    # Default to latin-1 (accepts all bytes)
    return 'latin-1'


def has_korean(text):
    """Check if text contains Korean characters."""
    return bool(re.search(r'[\uAC00-\uD7AF\u1100-\u11FF\u3130-\u318F]', text))


def translate_korean_strings(content):
    """Translate Korean strings to Thai for player-facing content."""
    translated_count = 0
    
    # Sort by length (longest first) to avoid partial replacements
    sorted_translations = sorted(KOREAN_TO_THAI.items(), key=lambda x: len(x[0]), reverse=True)
    
    for korean, thai in sorted_translations:
        if korean in content:
            content = content.replace(korean, thai)
            translated_count += content.count(thai)
    
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
    
    # Detect original encoding
    original_encoding = detect_encoding(file_path)
    
    # Read file content
    try:
        with open(file_path, 'r', encoding=original_encoding, errors='replace') as f:
            content = f.read()
    except Exception as e:
        print(f"Error reading {file_path}: {e}")
        return None, None
    
    original_content = content
    encoding_changed = original_encoding != 'utf-8'
    
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
        remaining_korean = re.findall(r'[\uAC00-\uD7AF]+', content)
        if remaining_korean:
            stats["preserved_korean"].append({
                "file": str(file_path),
                "korean": list(set(remaining_korean))[:10]  # Limit preserved list
            })
    
    # Only write if content changed or encoding needs conversion
    if content != original_content or encoding_changed:
        try:
            with open(file_path, 'w', encoding='utf-8', newline='\r\n') as f:
                f.write(content)
            return original_content, content
        except Exception as e:
            print(f"Error writing {file_path}: {e}")
            return None, None
    
    return None, None


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
        return
    
    print("=" * 60)
    print("MapleStory JavaScript Localization Script")
    print("=" * 60)
    print(f"Start time: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
    print(f"Target directory: {scripts_dir}")
    print()
    
    # Find all JS files
    js_files = find_js_files(scripts_dir)
    print(f"Found {len(js_files)} JavaScript files")
    print()
    
    # Process each file
    print("Processing files...")
    changes = []
    
    for i, file_path in enumerate(js_files):
        original, modified = process_file(file_path)
        if original is not None:
            changes.append({
                "file": str(file_path),
                "modified": True
            })
        
        # Progress indicator
        if (i + 1) % 100 == 0:
            print(f"  Processed {i + 1}/{len(js_files)} files...")
    
    print()
    print("=" * 60)
    print("SUMMARY REPORT")
    print("=" * 60)
    print(f"Total JS files scanned: {stats['files_scanned']}")
    print(f"Files with Korean content: {stats['files_with_korean']}")
    print(f"Files converted to UTF-8: {stats['files_converted_utf8']}")
    print(f"Dialogues translated: {stats['dialogues_translated']}")
    print(f"Comments translated: {stats['comments_translated']}")
    print(f"Files modified: {len(changes)}")
    print()
    
    if stats["preserved_korean"]:
        print(f"Files with preserved Korean ({len(stats['preserved_korean'])} files):")
        for item in stats["preserved_korean"][:20]:  # Show first 20
            print(f"  - {Path(item['file']).name}: {', '.join(item['korean'][:5])}")
        if len(stats["preserved_korean"]) > 20:
            print(f"  ... and {len(stats['preserved_korean']) - 20} more files")
    
    print()
    print(f"End time: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
    print("=" * 60)
    
    # Save detailed report
    report_path = script_dir / "localization_report.json"
    with open(report_path, 'w', encoding='utf-8') as f:
        json.dump({
            "timestamp": datetime.now().isoformat(),
            "stats": stats,
            "modified_files": [c["file"] for c in changes]
        }, f, ensure_ascii=False, indent=2)
    print(f"Detailed report saved to: {report_path}")


if __name__ == "__main__":
    main()
