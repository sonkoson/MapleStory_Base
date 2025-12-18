#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
MapleStory JavaScript Localization Script v3
=============================================
Complete sentence-based translation for accuracy.
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


# FULL SENTENCE TRANSLATIONS - exact strings found in files
# Format: "original Korean" -> "Thai/English translation"
FULL_TRANSLATIONS = {
    # Boss.js - scheduledTimeout messages
    "이동되었습니다.": "ย้ายแล้ว",
    "시간이 지나, Party가 해체됩니다.": "หมดเวลาแล้ว ปาร์ตี้ถูกยุบ",
    
    # Boss.js - playerDead comments
    "데스카운트를 모두 소모하였습니다.": "ใช้ Death Count หมดแล้ว",
    
    # Boss.js - allMonstersDead phases
    "2페이지로 넘어갑니다.": "กำลังไปเฟส 2",
    "3페이지로 넘어갑니다.": "กำลังไปเฟส 3",
    
    # Boss.js - Lucid clear message
    "클리어를 축하드립니다! 오르골이 지급되었으니, Enter 엔피시에게 가져다 주시길 바랍니다. 자동으로 Exit됩니다.": "ยินดีด้วยนะ เคลียร์แล้ว! ได้รับกล่องดนตรีแล้ว เอาไปให้ NPC Enter ด้วยนะ จะย้ายออกอัตโนมัติ",
    
    # Boss.js - Attack on Titan clear
    "클리어를 축하드립니다! 토벌 증표가 지급되었으니, 진격의 거인 엔피시에게 가져다 주시길 바랍니다. 자동으로 Exit됩니다.": "ยินดีด้วยนะ เคลียร์แล้ว! ได้รับเหรียญปราบแล้ว เอาไปให้ NPC Attack on Titan ด้วยนะ จะย้ายออกอัตโนมัติ",
    
    # Boss.js - Simple clear messages with boss name prefix
    "파풀라투스 클리어를 축하드립니다! 자동으로 퇴장됩니다.": "ยินดีด้วยนะ เคลียร์ Papulatus แล้ว! จะย้ายออกอัตโนมัติ",
    "힐라 클리어를 축하드립니다! 자동으로 퇴장됩니다.": "ยินดีด้วยนะ เคลียร์ Hilla แล้ว! จะย้ายออกอัตโนมัติ",
    "카오스 핑크빈 클리어를 축하드립니다! 자동으로 퇴장됩니다.": "ยินดีด้วยนะ เคลียร์ Chaos Pink Bean แล้ว! จะย้ายออกอัตโนมัติ",
    "반 레온 클리어를 축하드립니다! 자동으로 퇴장됩니다.": "ยินดีด้วยนะ เคลียร์ Von Leon แล้ว! จะย้ายออกอัตโนมัติ",
    "매그너스 클리어를 축하드립니다! 자동으로 퇴장됩니다.": "ยินดีด้วยนะ เคลียร์ Magnus แล้ว! จะย้ายออกอัตโนมัติ",
    "아카이럼 클리어를 축하드립니다! 자동으로 퇴장됩니다.": "ยินดีด้วยนะ เคลียร์ Arkarium แล้ว! จะย้ายออกอัตโนมัติ",
    "시그너스 클리어를 축하드립니다! 자동으로 퇴장됩니다.": "ยินดีด้วยนะ เคลียร์ Cygnus แล้ว! จะย้ายออกอัตโนมัติ",
    "카오스 혼테일 클리어를 축하드립니다! 자동으로 퇴장됩니다.": "ยินดีด้วยนะ เคลียร์ Chaos Horntail แล้ว! จะย้ายออกอัตโนมัติ",
    "카오스 피에르 클리어를 축하드립니다! 자동으로 퇴장됩니다.": "ยินดีด้วยนะ เคลียร์ Chaos Pierre แล้ว! จะย้ายออกอัตโนมัติ",
    "카오스 반반 클리어를 축하드립니다! 자동으로 퇴장됩니다.": "ยินดีด้วยนะ เคลียร์ Chaos Von Bon แล้ว! จะย้ายออกอัตโนมัติ",
    "카오스 벨룸 클리어를 축하드립니다! 자동으로 퇴장됩니다.": "ยินดีด้วยนะ เคลียร์ Chaos Vellum แล้ว! จะย้ายออกอัตโนมัติ",
    "클리어를 축하드립니다! 자동으로 퇴장됩니다.": "ยินดีด้วยนะ เคลียร์แล้ว! จะย้ายออกอัตโนมัติ",
    
    # Boss.js - Party disband messages  
    "Party원 혹은 Party장이 Party를 그만두거나 Map을 이동하여 원정대가 해체됩니다.": "สมาชิกหรือหัวหน้าปาร์ตี้ออกจากปาร์ตี้หรือย้ายแมพ ทำให้ปาร์ตี้ถูกยุบ",
    
    # Boss names in string comparisons (keep in romanized form for server matching)
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
    
    # Arkarium.js comments
    "이벤트매니저 초기화할 내용(Channel별로 적용됨)": "// Event manager initialization (Applied per Channel)",  
    "ㄱㄷㄱㄷ": "// standby",
    "탈퇴": "// Leave party",
    
    # Common messages
    "클리어 됐습니다.": "เคลียร์แล้ว",
    "클리어됐습니다.": "เคลียร์แล้ว",
    
    # Storage NPC (1002005.js)
    "ยินดีต้อนรับ": "ยินดีต้อนรับ",  # Already Thai - keep
    "คลังเก็บของทั่วไป": "คลังเก็บของทั่วไป",  # Already Thai
}

# Additional word/phrase translations for remaining Korean
WORD_TRANSLATIONS = {
    # Time
    "초": "วินาที",
    "분": "นาที",
    "시간": "ชั่วโมง",
    
    # Actions
    "입장": "เข้า",
    "퇴장": "ออก",
    "클리어": "เคลียร์",
    
    # Items
    "아이템": "ไอเทม",
    "장비": "อุปกรณ์",
    
    # Keep these as English (used in server matching)
    "스우": "Lotus",  # Boss name - server uses this
}

# Statistics tracking
stats = {
    "files_scanned": 0,
    "files_with_korean": 0,
    "files_converted_utf8": 0,
    "full_translations": 0,
    "word_translations": 0,
    "files_modified": 0,
    "preserved_korean": [],
    "errors": [],
}


def detect_encoding(file_path):
    """Detect file encoding using chardet."""
    with open(file_path, 'rb') as f:
        raw = f.read()
    
    # Check for BOM
    if raw.startswith(b'\xef\xbb\xbf'):
        return 'utf-8-sig', raw[3:]
    if raw.startswith(b'\xff\xfe'):
        return 'utf-16-le', raw[2:]
    if raw.startswith(b'\xfe\xff'):
        return 'utf-16-be', raw[2:]
    
    if HAS_CHARDET:
        result = chardet.detect(raw)
        encoding = result['encoding']
        if encoding:
            return encoding.lower(), raw
    
    # Fallback
    for enc in ['utf-8', 'euc-kr', 'cp949']:
        try:
            raw.decode(enc)
            return enc, raw
        except UnicodeDecodeError:
            continue
    
    return 'utf-8', raw


def has_korean(text):
    """Check if text contains Korean characters."""
    return bool(re.search(r'[\uAC00-\uD7AF\u1100-\u11FF\u3130-\u318F]', text))


def translate_content(content):
    """Translate Korean content to Thai/English."""
    full_count = 0
    word_count = 0
    
    # First: Apply full sentence translations (longest first)
    sorted_full = sorted(FULL_TRANSLATIONS.items(), key=lambda x: len(x[0]), reverse=True)
    for korean, translation in sorted_full:
        if korean in content:
            count = content.count(korean)
            content = content.replace(korean, translation)
            full_count += count
    
    # Second: Apply word translations for remaining Korean
    sorted_words = sorted(WORD_TRANSLATIONS.items(), key=lambda x: len(x[0]), reverse=True)
    for korean, translation in sorted_words:
        if korean in content:
            count = content.count(korean)
            content = content.replace(korean, translation)
            word_count += count
    
    return content, full_count, word_count


def process_file(file_path):
    """Process a single JavaScript file."""
    global stats
    stats["files_scanned"] += 1
    
    try:
        original_encoding, raw_bytes = detect_encoding(file_path)
        
        try:
            content = raw_bytes.decode(original_encoding)
        except (UnicodeDecodeError, LookupError):
            content = raw_bytes.decode('utf-8', errors='replace')
            original_encoding = 'utf-8-replaced'
        
        original_content = content
        encoding_changed = original_encoding not in ['utf-8']
        
        if encoding_changed:
            stats["files_converted_utf8"] += 1
        
        # Check for Korean content
        if has_korean(content):
            stats["files_with_korean"] += 1
            
            # Translate
            content, full_count, word_count = translate_content(content)
            stats["full_translations"] += full_count
            stats["word_translations"] += word_count
            
            # Track preserved Korean
            remaining = set(re.findall(r'[\uAC00-\uD7AF]+', content))
            if remaining:
                stats["preserved_korean"].append({
                    "file": str(file_path.name),
                    "korean": list(remaining)[:5]
                })
        
        # Write if changed
        if content != original_content:
            with open(file_path, 'w', encoding='utf-8', newline='\n') as f:
                f.write(content)
            stats["files_modified"] += 1
            return True
        
        return False
        
    except Exception as e:
        stats["errors"].append(f"{file_path.name}: {str(e)}")
        return False


def find_js_files(directory):
    """Find all JavaScript files."""
    js_files = []
    for root, dirs, files in os.walk(directory):
        for file in files:
            if file.endswith('.js'):
                js_files.append(Path(root) / file)
    return js_files


def main():
    script_dir = Path(__file__).parent
    scripts_dir = script_dir / "scripts" / "Royal"
    
    if not scripts_dir.exists():
        print(f"Error: Not found: {scripts_dir}", file=sys.stderr)
        return 1
    
    # Find and process files
    js_files = find_js_files(scripts_dir)
    
    for file_path in js_files:
        process_file(file_path)
    
    # Output summary (ASCII safe)
    print("=" * 60)
    print("LOCALIZATION COMPLETE")
    print("=" * 60)
    print(f"Files scanned:        {stats['files_scanned']}")
    print(f"Files with Korean:    {stats['files_with_korean']}")
    print(f"Files to UTF-8:       {stats['files_converted_utf8']}")
    print(f"Full translations:    {stats['full_translations']}")
    print(f"Word translations:    {stats['word_translations']}")
    print(f"Files modified:       {stats['files_modified']}")
    print(f"Files with preserved: {len(stats['preserved_korean'])}")
    
    if stats["errors"]:
        print(f"Errors: {len(stats['errors'])}")
    
    print("=" * 60)
    
    # Save detailed report as JSON
    import json
    report = {
        "timestamp": datetime.now().isoformat(),
        "stats": {k: v for k, v in stats.items() if k != "preserved_korean"},
        "preserved_korean_files": [p["file"] for p in stats["preserved_korean"][:50]]
    }
    
    report_path = script_dir / "localization_report.json"
    with open(report_path, 'w', encoding='utf-8') as f:
        json.dump(report, f, ensure_ascii=False, indent=2)
    
    print(f"Report saved: {report_path}")
    
    return 0


if __name__ == "__main__":
    sys.exit(main())
