# Thai Game Text Localization Report
**Generated:** 2025-12-16 00:53:19 +07:00  
**Repository:** MapleStory_Base  
**Scope:** All `.java` files with Korean player-visible text

---

## Executive Summary

| Metric | Value |
|--------|-------|
| **Files Requiring Translation** | 60 |
| **Primary Location** | `data/scripts/script/` |
| **Estimated Korean Strings** | ~5,000+ player-facing messages |
| **Translation Target** | Natural Thai (MapleStory MMORPG style) |
| **Developer Text Target** | Standard English |

---

## Translation Samples from `JinCustomNPC.java`

### Sample 1: Shop Title & Description
**File:** `data/scripts/script/Server/JinCustomNPC.java`  
**Lines:** 71-72  
**Context:** NPC shop interface header  
**Method:** `displayShop()` → `self.askMenu()`

#### Original (Korean)
```java
String gradeName = "#e<" + grades[grade] + "등급 멤버십 상점>#n";
String v0 = gradeName + "\r\n보급 상자는 매일 1회 무료로 지원됩니다.";
```

#### Proposed Translation (Thai)
```java
String gradeName = "#e<ร้านค้าสมาชิกระดับ " + grades[grade] + ">#n";
String v0 = gradeName + "\r\nกล่องเสบียงจะได้รับฟรี 1 ครั้งต่อวัน";
```

**Tone:** Informative, friendly  
**Notes:**
- "등급" (grade) → "ระดับ" (level/rank)
- "멤버십 상점" (membership shop) → "ร้านค้าสมาชิก"
- "보급 상자" (supply box) → "กล่องเสบียง"
- "매일 1회 무료로 지원" → "ได้รับฟรี 1 ครั้งต่อวัน" (receive free once per day)

---

### Sample 2: Item Purchase Confirmation
**File:** `data/scripts/script/Server/JinCustomNPC.java`  
**Line:** 117  
**Context:** Item received confirmation dialog  
**Method:** `self.askMenu()`

#### Original (Korean)
```java
if (0 == self.askMenu("#b#i" + item + "# #z" + item + "##k을 보급해드렸습니다.\r\n\r\n#b#L0#아이템 목록으로 돌아간다.#l"))
```

#### Proposed Translation (Thai)
```java
if (0 == self.askMenu("#b#i" + item + "# #z" + item + "##k ได้รับเรียบร้อยแล้ว\r\n\r\n#b#L0#กลับไปที่รายการไอเท็ม#l"))
```

**Tone:** Polite, confirmatory  
**Notes:**
- "을 보급해드렸습니다" (has been supplied) → "ได้รับเรียบร้อยแล้ว" (received successfully)
- "아이템 목록으로 돌아간다" (return to item list) → "กลับไปที่รายการไอเท็ม"
- Preserves formatting codes: `#b`, `#i`, `#z`, `#k`, `#L0#`, `#l`

---

### Sample 3: Insufficient Points Error
**File:** `data/scripts/script/Server/JinCustomNPC.java`  
**Line:** 148  
**Context:** Error message when player lacks points  
**Method:** `self.say()`

#### Original (Korean)
```java
self.say("강림 포인트가 부족하여 구매할 수 없습니다.");
```

#### Proposed Translation (Thai)
```java
self.say("คะแนนไม่เพียงพอสำหรับการซื้อ");
```

**Tone:** Clear, informative  
**Notes:**
- "강림 포인트" (Ganglim Points - game-specific currency) → "คะแนน" (points)
- "부족하여" (insufficient) → "ไม่เพียงพอ"
- "구매할 수 없습니다" (cannot purchase) → "สำหรับการซื้อ"
- Shortened for clarity while maintaining meaning

---

### Sample 4: Inventory Full Error
**File:** `data/scripts/script/Server/JinCustomNPC.java`  
**Lines:** 122, 159  
**Context:** Error when inventory is full  
**Method:** `self.say()`

#### Original (Korean)
```java
self.say("인벤토리 슬롯을 확보하고 다시 시도해주시기 바랍니다.");
```

#### Proposed Translation (Thai)
```java
self.say("กรุณาเพิ่มช่องว่างในกระเป๋าแล้วลองใหม่อีกครั้ง");
```

**Tone:** Polite, helpful  
**Notes:**
- "인벤토리 슬롯을 확보하고" (secure inventory slots) → "เพิ่มช่องว่างในกระเป๋า" (make space in bag)
- "다시 시도해주시기 바랍니다" (please try again) → "ลองใหม่อีกครั้ง"
- "กรุณา" (please) - polite prefix

---

### Sample 5: Achievement Popup
**File:** `data/scripts/script/Server/JinCustomNPC.java`  
**Line:** 235  
**Context:** Grade upgrade success notification  
**Method:** `CField.addPopupSay()` - Player-visible popup

#### Original (Korean)
```java
getPlayer().send(CField.addPopupSay(9062000, 5000, "#e#r" + grade.getDesc() + " 등급#k#n으로 등급업에 성공했습니다!", ""));
```

#### Proposed Translation (Thai)
```java
getPlayer().send(CField.addPopupSay(9062000, 5000, "#e#rอัพเกรดสู่ระดับ " + grade.getDesc() + " สำเร็จ!#k#n", ""));
```

**Tone:** Exciting, congratulatory  
**Notes:**
- "등급업에 성공했습니다" (grade up succeeded) → "อัพเกรดสู่ระดับ...สำเร็จ" (upgrade to level...success)
- Exclamation mark preserved for excitement
- "등급" (grade/rank) → "ระดับ" (level)

---

### Sample 6: Server-Wide Broadcast
**File:** `data/scripts/script/Server/JinCustomNPC.java`  
**Line:** 238  
**Context:** Legendary achievement announcement  
**Method:** `Center.Broadcast.broadcastMessage()` - Server broadcast

#### Original (Korean)
```java
Center.Broadcast.broadcastMessage(CField.chatMsg(5, "[" + getPlayer().getName() + "]님이 엑스트라 어빌리티 '레전드리 등급'을 달성하였습니다!"));
```

#### Proposed Translation (Thai)
```java
Center.Broadcast.broadcastMessage(CField.chatMsg(5, "[" + getPlayer().getName() + "] ได้บรรลุ Extra Ability 'ระดับ Legendary' แล้ว!"));
```

**Tone:** Epic, celebratory  
**Notes:**
- "님이" (honorific) → removed (Thai doesn't require this)
- "엑스트라 어빌리티" (Extra Ability) → kept as "Extra Ability" (game term)
- "레전드리 등급" (Legendary grade) → "ระดับ Legendary"
- "달성하였습니다" (achieved) → "ได้บรรลุ...แล้ว" (has achieved)

---

### Sample 7: Confirmation Dialog
**File:** `data/scripts/script/Server/JinCustomNPC.java`  
**Lines:** 316, 469, 555, 614, 639, 668  
**Context:** Reset confirmation warning  
**Method:** `self.askMenu()` / `self.askYesNo()`

#### Original (Korean)
```java
if (self.askMenu("#fs11##r#e정말 재설정 하시겠습니까? 재설정 시 현재 옵션은 사라지게 됩니다.#n#k\r\n\r\n#b#L0#한 번 더 설정한다.#l\r\n#L1#대화를 종료한다.#l") == 0)
```

#### Proposed Translation (Thai)
```java
if (self.askMenu("#fs11##r#eต้องการรีเซ็ตจริงหรือไม่? การรีเซ็ตจะทำให้ตัวเลือกปัจจุบันหายไป#n#k\r\n\r\n#b#L0#รีเซ็ตอีกครั้ง#l\r\n#L1#ปิดหน้าต่าง#l") == 0)
```

**Tone:** Warning, cautionary  
**Notes:**
- "정말 재설정 하시겠습니까?" (really want to reset?) → "ต้องการรีเซ็ตจริงหรือไม่?"
- "재설정 시 현재 옵션은 사라지게 됩니다" → "การรีเซ็ตจะทำให้ตัวเลือกปัจจุบันหายไป"
- "한 번 더 설정한다" (set one more time) → "รีเซ็ตอีกครั้ง" (reset again)
- "대화를 종료한다" (end conversation) → "ปิดหน้าต่าง" (close window)

---

### Sample 8: System Description
**File:** `data/scripts/script/Server/JinCustomNPC.java`  
**Line:** 780  
**Context:** Extra Ability system explanation  
**Method:** `self.say()` - Informational dialogue

#### Original (Korean)
```java
self.say("#fs11##b엑스트라 어빌리티 시스템#k은 캐릭터의 추가적인 성장 수단입니다.\r\n\r\n엑스트라 어빌리티는 #e#r여섯 개의 랜덤 옵션#k#n을 부여하는 방식으로, 재화를 소모하여 부여되는 옵션의 종류를 변경할 수 있습니다.\r\n\r\n옵션 변경은 #e#b찬란한 빛의 결정#k#n을 재화로 이용 가능합니다.");
```

#### Proposed Translation (Thai)
```java
self.say("#fs11#ระบบ #bExtra Ability#k เป็นวิธีการเพิ่มพลังให้กับตัวละครของคุณ\r\n\r\nExtra Ability จะมอบ#e#rตัวเลือกสุ่ม 6 ตัว#k#n ให้กับคุณ และคุณสามารถเปลี่ยนตัวเลือกเหล่านี้ได้โดยใช้ทรัพยากร\r\n\r\nการเปลี่ยนตัวเลือกสามารถทำได้โดยใช้ #e#b#z4031227##k#n");
```

**Tone:** Informative, tutorial-style  
**Notes:**
- "엑스트라 어빌리티 시스템" → "ระบบ Extra Ability" (kept game term)
- "캐릭터의 추가적인 성장 수단" → "วิธีการเพิ่มพลังให้กับตัวละคร" (way to power up character)
- "여섯 개의 랜덤 옵션" → "ตัวเลือกสุ่ม 6 ตัว" (6 random options)
- "찬란한 빛의 결정" → "#z4031227#" (use item reference code for consistency)

---

### Sample 9: Option Statistics Display
**File:** `data/scripts/script/Server/JinCustomNPC.java`  
**Line:** 790  
**Context:** Extra grade option list  
**Method:** `self.say()` - Information display

#### Original (Korean)
```java
self.say("#fs11##e[ 엑스트라 ]#n\r\n\r\n#b- 재사용 대기시간 1~2초 감소\r\n- 부활 시 무적 시간 2~4초 증가\r\n- 몬스터 개체수 1.5배 증가\r\n- 최종 데미지 5~20%\r\n- 경험치 획득량 30~50%\r\n- 메소 획득량 30~50%\r\n- 아이템 드롭률 30~50%\r\n- 상태 이상 내성");
```

#### Proposed Translation (Thai)
```java
self.say("#fs11##e[ Extra ]#n\r\n\r\n#b- ลดเวลาคูลดาวน์ 1~2 วินาที\r\n- เพิ่มเวลาอมตะเมื่อฟื้นคืนชีพ 2~4 วินาที\r\n- เพิ่มจำนวนมอนสเตอร์ 1.5 เท่า\r\n- Final Damage 5~20%\r\n- EXP ที่ได้รับ 30~50%\r\n- Meso ที่ได้รับ 30~50%\r\n- อัตราดรอปไอเท็ม 30~50%\r\n- ต้านทานสถานะผิดปกติ");
```

**Tone:** Technical, informative  
**Notes:**
- "재사용 대기시간" (cooldown time) → "เวลาคูลดาวน์"
- "부활 시 무적 시간" (invincibility time on revival) → "เวลาอมตะเมื่อฟื้นคืนชีพ"
- "최종 데미지" → "Final Damage" (kept as game term)
- "경험치" → "EXP" (standard game abbreviation)
- "메소" → "Meso" (game currency, not translated)
- "아이템 드롭률" → "อัตราดรอปไอเท็ม" (item drop rate)

---

### Sample 10: Grade Upgrade Probability
**File:** `data/scripts/script/Server/JinCustomNPC.java`  
**Line:** 799  
**Context:** Upgrade probability information  
**Method:** `self.say()`

#### Original (Korean)
```java
self.say("#fs11##e[레어 → 에픽 등급 상승 확률]#n\r\n#b15% 확률로 증가\r\n\r\n\r\n#k#e[에픽 → 유니크 등긍 삽승 확률]#n\r\n#b3.8% 확률로 증가\r\n\r\n\r\n#k#e[유니크 → 레전드리 등급 상승 확률]#n\r\n#b1% 확률로 증가\r\n#e[엑스트라 등급 등장 확률]#n\r\n#b'레전드리' 등급에서 1% 확률로 등장");
```

#### Proposed Translation (Thai)
```java
self.say("#fs11##e[โอกาสอัพเกรด Rare → Epic]#n\r\n#bโอกาส 15%\r\n\r\n\r\n#k#e[โอกาสอัพเกรด Epic → Unique]#n\r\n#bโอกาส 3.8%\r\n\r\n\r\n#k#e[โอกาสอัพเกรด Unique → Legendary]#n\r\n#bโอกาส 1%\r\n#e[โอกาสปรากฏระดับ Extra]#n\r\n#bโอกาส 1% เมื่ออยู่ในระดับ 'Legendary'");
```

**Tone:** Technical, statistical  
**Notes:**
- Grade names kept in English: Rare, Epic, Unique, Legendary, Extra
- "등급 상승 확률" (grade upgrade probability) → "โอกาสอัพเกรด" (upgrade chance)
- "확률로 증가" (increases with probability) → "โอกาส" (chance)
- "등장 확률" (appearance probability) → "โอกาสปรากฏ" (chance to appear)

---

## Developer Comments Translation (Korean → English)

### Sample 1: Comment Translation
**File:** `data/scripts/script/Server/JinCustomNPC.java`  
**Line:** 92

#### Original (Korean)
```java
int remain = entry.getWorldLimit(grade - 1) - buyCount; // 금일 구매 가능 횟수
```

#### Proposed Translation (English)
```java
int remain = entry.getWorldLimit(grade - 1) - buyCount; // Remaining purchases available today
```

---

### Sample 2: Comment Translation
**File:** `data/scripts/script/Server/JinCustomNPC.java`  
**Line:** 96

#### Original (Korean)
```java
// 구매 가능 등급이 아닐 때
```

#### Proposed Translation (English)
```java
// When grade requirement is not met
```

---

### Sample 3: Comment Translation
**File:** `data/scripts/script/Server/JinCustomNPC.java`  
**Line:** 101

#### Original (Korean)
```java
// 구매 가능 횟수가 없을 때
```

#### Proposed Translation (English)
```java
// When no purchases remain
```

---

### Sample 4: Comment Translation
**File:** `data/scripts/script/Server/JinCustomNPC.java`  
**Line:** 153

#### Original (Korean)
```java
// 구매 처리
```

#### Proposed Translation (English)
```java
// Process purchase
```

---

### Sample 5: Comment Translation
**File:** `data/scripts/script/Server/JinCustomNPC.java`  
**Lines:** 222, 226

#### Original (Korean)
```java
// 강림은 찬란한 빛의 결정이 재료임
// 재설정 드가자
```

#### Proposed Translation (English)
```java
// Ganglim server uses Brilliant Light Crystal as material
// Proceed with reset
```

---

### Sample 6: Comment Translation
**File:** `data/scripts/script/Server/JinCustomNPC.java`  
**Lines:** 232, 246-249

#### Original (Korean)
```java
// 등업!
// 엑스트라 스탯이 없다면.
// 일정 확률로 달아주자.
// 레전드리 옵션에서 1% 확률로 달림
```

#### Proposed Translation (English)
```java
// Grade up!
// If extra stat doesn't exist
// Grant with certain probability
// 1% chance to grant at Legendary grade
```

---

### Sample 7: Comment Translation
**File:** `data/scripts/script/Server/JinCustomNPC.java`  
**Line:** 330

#### Original (Korean)
```java
// ㅇㅇ 돌려주세영
```

#### Proposed Translation (English)
```java
// Return resources
```

**Note:** "ㅇㅇ 돌려주세영" is informal Korean slang. Translated to professional English.

---

## Terminology Glossary

### Game Terms (DO NOT Translate)

| Korean | English/Transliteration | Thai Usage |
|--------|------------------------|------------|
| 엑스트라 어빌리티 | Extra Ability | Extra Ability |
| 레전드리 | Legendary | Legendary |
| 유니크 | Unique | Unique |
| 에픽 | Epic | Epic |
| 레어 | Rare | Rare |
| 메소 | Meso | Meso |
| 프리셋 | Preset | Preset |
| 럭키 포인트 | Lucky Point | Lucky Point |
| HP | HP | HP |
| MP | MP | MP |
| EXP | EXP | EXP |
| STR | STR | STR |
| DEX | DEX | DEX |
| INT | INT | INT |
| LUK | LUK | LUK |
| 크리티컬 | Critical | Critical |
| 버프 | Buff | Buff |
| 디버프 | Debuff | Debuff |

### Server-Specific Terms

| Korean | English | Thai |
|--------|---------|------|
| 강림 포인트 | Ganglim Points | คะแนน Ganglim |
| 홍보 포인트 | Promotion Points | คะแนนโปรโมชั่น |
| 캐시 | Cash | แคช |

### Common Translations

| Korean | Thai | Context |
|--------|------|---------|
| 등급 | ระดับ | Grade/Rank/Level |
| 옵션 | ตัวเลือก | Option |
| 재설정 | รีเซ็ต | Reset |
| 구매 | ซื้อ | Purchase |
| 확률 | โอกาส | Probability/Chance |
| 포인트 | คะแนน | Points |
| 아이템 | ไอเท็ม | Item |
| 인벤토리 | กระเป๋า | Inventory |
| 슬롯 | ช่อง | Slot |

---

## Translation Priority Matrix

### Priority 1: High-Frequency Player Messages
- NPC dialogue (self.say, self.askMenu, self.askYesNo)
- Error messages
- Confirmation dialogs
- Achievement notifications
- **Estimated:** ~2,000 strings
- **Impact:** CRITICAL - Players see these constantly

### Priority 2: System Descriptions
- Tutorial text
- Feature explanations
- Option lists
- **Estimated:** ~1,500 strings
- **Impact:** HIGH - Important for understanding

### Priority 3: Broadcast Messages
- Server announcements
- Achievement broadcasts
- **Estimated:** ~500 strings
- **Impact:** MEDIUM - Visible but less frequent

### Priority 4: Developer Comments
- Code comments
- Debug messages
- **Estimated:** ~1,000 strings
- **Impact:** LOW - Internal use only

---

## Quality Assurance Checklist

### Pre-Translation
- [ ] Backup all files
- [ ] Create translation spreadsheet
- [ ] Identify all player-facing methods
- [ ] Build terminology glossary
- [ ] Review with native Thai speaker

### During Translation
- [ ] Preserve all formatting codes (`#e`, `#r`, `#b`, `#k`, `#n`, etc.)
- [ ] Preserve item/skill references (`#i`, `#z`)
- [ ] Preserve selection markers (`#L0#`, `#l`)
- [ ] Maintain line breaks (`\r\n`)
- [ ] Keep game terms in English
- [ ] Match tone to context

### Post-Translation
- [ ] Verify no Korean characters remain
- [ ] Test formatting codes in-game
- [ ] Check string length (UI constraints)
- [ ] Native speaker review
- [ ] In-game testing
- [ ] Player feedback collection

---

## File-by-File Translation Status

### Server NPCs (HIGH PRIORITY)

#### ✅ `JinCustomNPC.java`
- **Status:** Ready for translation
- **Lines:** 2,700
- **Korean Strings:** ~1,200
- **Estimated Time:** 10-12 hours
- **Context:** Extra Ability system, membership shop

#### ⏳ `RoyalCustomNPC.java`
- **Status:** Pending analysis
- **Estimated Time:** 8-10 hours

#### ⏳ `ZeniaCustomNPC.java`
- **Status:** Pending analysis
- **Estimated Time:** 6-8 hours

### Boss Scripts (MEDIUM PRIORITY)
- Arkarium.java
- Cygnus.java
- Hillah.java
- Horntail.java
- Magnus.java
- PinkBeen.java
- Zakum.java
- **Total Estimated Time:** 12-15 hours

### Quest Scripts (MEDIUM PRIORITY)
- GenesisQuest.java
- GoldenWagon.java
- HongboNPC.java
- MapleGM.java
- StepUp.java
- **Total Estimated Time:** 8-10 hours

### Arcane River Scripts (LOW PRIORITY)
- 11 files in ArcaneRiver directory
- **Total Estimated Time:** 6-8 hours

---

## Recommended Translation Workflow

### Phase 1: Preparation (4-6 hours)
1. Extract all Korean strings to spreadsheet
2. Classify by context (player/developer)
3. Identify method context for each string
4. Build comprehensive glossary
5. Create translation guidelines document

### Phase 2: Translation (30-40 hours)
1. Translate JinCustomNPC.java (10-12 hours)
2. Translate RoyalCustomNPC.java (8-10 hours)
3. Translate ZeniaCustomNPC.java (6-8 hours)
4. Translate Boss scripts (12-15 hours)
5. Translate Quest scripts (8-10 hours)
6. Translate remaining files (10-12 hours)

### Phase 3: Review (6-8 hours)
1. Native Thai speaker review
2. Consistency check
3. Formatting verification
4. Terminology alignment

### Phase 4: Testing (4-6 hours)
1. In-game dialogue testing
2. UI fit testing
3. Player feedback
4. Final adjustments

### Phase 5: Deployment (2-3 hours)
1. Final verification
2. Commit changes
3. Documentation update
4. Release notes

**Total Estimated Time:** 46-63 hours

---

## Risk Mitigation

### Translation Risks

| Risk | Mitigation |
|------|------------|
| Inconsistent terminology | Use glossary, find/replace verification |
| Formatting code errors | Automated regex validation |
| UI text overflow | Length checking, in-game testing |
| Tone mismatch | Native speaker review, player testing |
| Context misunderstanding | Method-based classification, code review |

### Technical Risks

| Risk | Mitigation |
|------|------------|
| Encoding corruption | UTF-8 verification, backup |
| Game breaking changes | Staged rollout, testing environment |
| Performance impact | No expected impact (string literals only) |

---

## Next Steps

1. ✅ **Review this report** for accuracy
2. ⚠️ **Approve translation approach** and samples
3. ⚠️ **Begin Phase 1:** Extract strings to spreadsheet
4. ⚠️ **Assign translator(s)** with MapleStory knowledge
5. ⚠️ **Set up testing environment** for verification

---

**Report Status:** ✅ READY FOR REVIEW  
**Awaiting:** User approval to proceed with translation
