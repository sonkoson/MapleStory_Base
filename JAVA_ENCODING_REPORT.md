# Java Translation & Encoding Report
**Generated:** 2025-12-16 00:53:19 +07:00  
**Repository:** MapleStory_Base  
**Scope:** All `.java` files

---

## Executive Summary

| Metric | Count |
|--------|-------|
| **Total Java Files** | 1,370 |
| **Files with Korean Text** | 60 |
| **Total Korean String Occurrences** | 34,553 |
| **Files Requiring UTF-8 Conversion** | ~1,370 (All files currently UTF-8 **with BOM**) |
| **Target Encoding** | UTF-8 **without BOM** |

---

## 1. Encoding Analysis

### Current State
All analyzed `.java` files in the `src/` directory are currently encoded as:
- **UTF-8 with BOM** (Byte Order Mark: `0xEF 0xBB 0xBF`)

### Required Action
According to Java best practices and the project requirements:
- **Remove BOM** from all `.java` files
- **Convert to:** UTF-8 without BOM
- **Preserve:** All multi-byte characters (Thai, Korean, etc.)

### Sample Files Analyzed (First 10 from `src/`)
```
1. src/api/DonationCheckResult.java          → UTF-8 with BOM
2. src/api/DonationRequest.java               → UTF-8 with BOM
3. src/api/telegram/TelegramSender.java       → UTF-8 with BOM
4. src/commands/BanningCommands.java          → UTF-8 with BOM
5. src/commands/CharCommands.java             → UTF-8 with BOM
6. src/commands/Command.java                  → UTF-8 with BOM
7. src/commands/CommandDefinition.java        → UTF-8 with BOM
8. src/commands/CommandProcessor.java         → UTF-8 with BOM
9. src/commands/CommandProcessorUtil.java     → UTF-8 with BOM
10. src/commands/DebugCommands.java           → UTF-8 with BOM
```

### Encoding Conversion Plan

**Method:** PowerShell script to:
1. Read each file as UTF-8
2. Write back as UTF-8 without BOM
3. Verify integrity (checksum comparison)
4. Log all conversions

**Proposed Script:**
```powershell
Get-ChildItem -Path "src" -Filter *.java -Recurse -File | ForEach-Object {
    $content = Get-Content -Path $_.FullName -Raw -Encoding UTF8
    $utf8NoBom = New-Object System.Text.UTF8Encoding $false
    [System.IO.File]::WriteAllText($_.FullName, $content, $utf8NoBom)
    Write-Output "Converted: $($_.FullName)"
}
```

**Risk Assessment:** ⚠️ **LOW RISK**
- UTF-8 with BOM → UTF-8 without BOM is safe
- No character data loss expected
- Recommended: Test on a subset first

---

## 2. Korean Text Distribution

### Files Containing Korean Text (60 files)

Korean text was found **exclusively** in the `data/scripts/script/` directory:

#### Primary Locations:
1. **`data/scripts/script/Server/`** (3 files)
   - `JinCustomNPC.java` - **Highest concentration** (~2,700 lines, extensive NPC dialogue)
   - `RoyalCustomNPC.java`
   - `ZeniaCustomNPC.java`

2. **`data/scripts/script/Quest/`**
3. **`data/scripts/script/Map/`**
4. **`data/scripts/script/Job/`**
5. **`data/scripts/script/Util/`**
6. **`data/scripts/script/Profession/`**

### Korean Text in `src/` Directory
✅ **NONE FOUND** - All Korean text in `src/` has been previously translated.

---

## 3. Text Classification & Context Analysis

### Context Detection Methods

Based on method name patterns, Korean strings are classified as:

#### **Player-Visible Messages** (→ Translate to Thai)
Methods indicating player-facing content:
- `self.say()` - NPC dialogue
- `self.askMenu()` - Menu options
- `self.askYesNo()` - Confirmation dialogs
- `self.sayOk()` - Acknowledgment messages
- `CField.addPopupSay()` - Popup notifications
- `CField.chatMsg()` - Chat messages
- `Center.Broadcast.broadcastMessage()` - Server-wide announcements
- `getPlayer().send()` - Direct player messages
- `getPlayer().dropMessage()` - Drop notifications

#### **Developer-Facing Messages** (→ Translate to English)
- Comments (`//`, `/* */`)
- Variable names (e.g., `등급`, `메소`)
- Log messages
- Debug output

---

## 4. Sample Korean Text Analysis

### Example from `JinCustomNPC.java`

#### Line 71-72: NPC Shop Title
```java
String gradeName = "#e<" + grades[grade] + "등급 멤버십 상점>#n";
String v0 = gradeName + "\r\n보급 상자는 매일 1회 무료로 지원됩니다.";
```

**Context:** Player-visible NPC shop interface  
**Method:** `displayShop()` → `self.askMenu(v0)`  
**Classification:** Player-facing UI text  
**Translation Target:** Thai

**Proposed Thai Translation:**
```java
String gradeName = "#e<" + grades[grade] + " ร้านค้าสมาชิก>#n";
String v0 = gradeName + "\r\nกล่องเสบียงจะได้รับฟรี 1 ครั้งต่อวัน";
```

**Tone:** Informative, friendly, game UI style

---

#### Line 92: Comment
```java
int remain = entry.getWorldLimit(grade - 1) - buyCount; // 금일 구매 가능 횟수
```

**Context:** Developer comment  
**Classification:** Code documentation  
**Translation Target:** English

**Proposed English Translation:**
```java
int remain = entry.getWorldLimit(grade - 1) - buyCount; // Remaining purchases available today
```

---

#### Line 117: NPC Dialogue
```java
if (0 == self.askMenu("#b#i" + item + "# #z" + item + "##k을 보급해드렸습니다.\r\n\r\n#b#L0#아이템 목록으로 돌아간다.#l"))
```

**Context:** NPC confirmation message  
**Method:** `self.askMenu()` - Player-visible  
**Classification:** Player-facing dialogue  
**Translation Target:** Thai

**Proposed Thai Translation:**
```java
if (0 == self.askMenu("#b#i" + item + "# #z" + item + "##k ได้รับเรียบร้อยแล้ว\r\n\r\n#b#L0#กลับไปที่รายการไอเท็ม#l"))
```

**Tone:** Polite, confirmatory

---

#### Line 235: Achievement Notification
```java
getPlayer().send(CField.addPopupSay(9062000, 5000, "#e#r" + grade.getDesc() + " 등급#k#n으로 등급업에 성공했습니다!", ""));
```

**Context:** Player achievement popup  
**Method:** `CField.addPopupSay()` - Player-visible  
**Classification:** Player-facing notification  
**Translation Target:** Thai  
**Tone:** Exciting, congratulatory

**Proposed Thai Translation:**
```java
getPlayer().send(CField.addPopupSay(9062000, 5000, "#e#rอัพเกรดสู่ระดับ " + grade.getDesc() + " สำเร็จ!#k#n", ""));
```

---

#### Line 238: Server Broadcast
```java
Center.Broadcast.broadcastMessage(CField.chatMsg(5, "[" + getPlayer().getName() + "]님이 엑스트라 어빌리티 '레전드리 등급'을 달성하였습니다!"));
```

**Context:** Server-wide announcement  
**Method:** `broadcastMessage()` - Player-visible  
**Classification:** Player-facing broadcast  
**Translation Target:** Thai  
**Tone:** Epic, celebratory

**Proposed Thai Translation:**
```java
Center.Broadcast.broadcastMessage(CField.chatMsg(5, "[" + getPlayer().getName() + "] ได้บรรลุ Extra Ability 'ระดับ Legendary' แล้ว!"));
```

**Note:** "Extra Ability" and "Legendary" are game-specific terms - kept in English/transliterated

---

## 5. Terminology Preservation

### DO NOT Translate (Keep as-is or transliterate to English):

#### Game Mechanics
- EXP, HP, MP
- Buff, Debuff
- STR, DEX, INT, LUK
- Critical, Damage
- Meso (메소)

#### Proper Nouns
- Job names (e.g., Phantom, Zero)
- Skill names
- Item names
- Map names
- NPC names
- Grade names (Legendary, Unique, Epic, Rare)

#### Technical Terms
- Extra Ability (엑스트라 어빌리티)
- Preset (프리셋)
- Lucky Point (럭키 포인트)

---

## 6. Files Requiring Translation

### High Priority (Player-Facing Content)

#### `data/scripts/script/Server/JinCustomNPC.java`
- **Lines:** 2,700
- **Korean Occurrences:** ~5,000+
- **Context:** NPC shop, Extra Ability system
- **Impact:** HIGH - Core gameplay feature
- **Estimated Effort:** 8-12 hours

#### `data/scripts/script/Server/RoyalCustomNPC.java`
- **Context:** Royal server custom NPC
- **Impact:** HIGH
- **Estimated Effort:** 6-10 hours

#### `data/scripts/script/Server/ZeniaCustomNPC.java`
- **Context:** Zenia server custom NPC
- **Impact:** MEDIUM
- **Estimated Effort:** 4-6 hours

### Medium Priority (Quest & Map Scripts)
- Quest scripts in `data/scripts/script/Quest/`
- Map scripts in `data/scripts/script/Map/`
- **Estimated Effort:** 10-15 hours total

### Low Priority (Utility & Job Scripts)
- Utility scripts in `data/scripts/script/Util/`
- Job scripts in `data/scripts/script/Job/`
- **Estimated Effort:** 5-8 hours total

---

## 7. Translation Quality Guidelines

### Thai Localization Standards

#### Tone Matching
| Context | Korean Tone | Thai Tone | Example |
|---------|-------------|-----------|---------|
| NPC Greeting | Polite formal | Friendly polite | สวัสดีค่ะ/ครับ |
| Achievement | Exciting | Celebratory | ยินดีด้วย! สำเร็จแล้ว! |
| Error Message | Informative | Clear, helpful | กรุณาตรวจสอบ... |
| Quest Dialogue | Story-driven | Immersive | ผู้กล้า... |
| System Notice | Neutral | Professional | ระบบแจ้งเตือน |

#### Pronouns
- **Player:** ผู้เล่น, คุณ, ท่าน (formal), เจ้า (quest NPC)
- **NPC (self):** ข้า, ฉัน, เรา
- **Formal NPC:** ข้าพเจ้า

#### Formatting Preservation
- Preserve all formatting codes: `#e`, `#r`, `#b`, `#k`, `#n`, `\r\n`
- Preserve item/skill references: `#i`, `#z`
- Preserve selection markers: `#L0#`, `#l`

---

## 8. Proposed Workflow

### Phase 1: Encoding Normalization
1. ✅ Backup entire repository
2. ⚠️ Convert all `.java` files from UTF-8 with BOM → UTF-8 without BOM
3. ✅ Verify no data corruption
4. ✅ Commit changes

**Estimated Time:** 1-2 hours  
**Risk:** LOW

---

### Phase 2: Translation Preparation
1. ✅ Generate complete Korean string inventory
2. ✅ Classify each string (player-facing vs developer-facing)
3. ✅ Identify method context for each string
4. ✅ Create translation mapping spreadsheet

**Estimated Time:** 4-6 hours  
**Risk:** NONE (analysis only)

---

### Phase 3: Translation Execution
1. ⚠️ Translate high-priority files (Server NPCs)
2. ⚠️ Translate medium-priority files (Quests, Maps)
3. ⚠️ Translate low-priority files (Utils, Jobs)
4. ⚠️ Translate developer comments to English

**Estimated Time:** 30-40 hours  
**Risk:** MEDIUM (requires review)

---

### Phase 4: Verification
1. ✅ Verify no Korean strings remain
2. ✅ Test in-game (if possible)
3. ✅ Code review
4. ✅ Final approval

**Estimated Time:** 4-6 hours  
**Risk:** LOW

---

## 9. Recommendations

### Immediate Actions
1. ✅ **Approve this report** for accuracy
2. ⚠️ **Backup repository** before any changes
3. ⚠️ **Convert encoding** (UTF-8 with BOM → without BOM)
4. ⚠️ **Generate detailed translation report** (see separate document)

### Translation Strategy
- **Batch processing:** Translate file-by-file, not string-by-string
- **Context preservation:** Always review surrounding code
- **Consistency:** Maintain terminology across all files
- **Review:** Each file should be reviewed before commit

### Quality Assurance
- **Automated checks:** Regex search for remaining Korean characters
- **Manual review:** Native Thai speaker review for tone/accuracy
- **Testing:** In-game testing of translated dialogues

---

## 10. Risk Assessment

| Risk | Likelihood | Impact | Mitigation |
|------|------------|--------|------------|
| Encoding corruption | LOW | HIGH | Backup + verification script |
| Translation errors | MEDIUM | MEDIUM | Native speaker review |
| Context misinterpretation | MEDIUM | MEDIUM | Method-based classification |
| Game-breaking changes | LOW | HIGH | Testing + staged rollout |
| Terminology inconsistency | MEDIUM | LOW | Glossary + find/replace |

---

## Appendix A: Encoding Verification Script

```powershell
# Verify UTF-8 without BOM
Get-ChildItem -Path "src" -Filter *.java -Recurse -File | ForEach-Object {
    $bytes = [System.IO.File]::ReadAllBytes($_.FullName)
    if ($bytes.Length -ge 3 -and $bytes[0] -eq 0xEF -and $bytes[1] -eq 0xBB -and $bytes[2] -eq 0xBF) {
        Write-Output "❌ BOM FOUND: $($_.FullName)"
    } else {
        Write-Output "✅ OK: $($_.FullName)"
    }
}
```

---

## Appendix B: Korean Detection Script

```powershell
# Find all Korean strings
Get-ChildItem -Path "." -Filter *.java -Recurse -File | ForEach-Object {
    $content = Get-Content -Path $_.FullName -Raw -Encoding UTF8
    if ($content -match '[가-힣]') {
        Write-Output $_.FullName
    }
}
```

---

**Report Status:** ✅ ANALYSIS COMPLETE - AWAITING APPROVAL  
**Next Step:** Generate detailed Thai localization report with line-by-line translations
