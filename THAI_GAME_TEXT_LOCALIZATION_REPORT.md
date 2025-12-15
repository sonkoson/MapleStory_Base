# Thai Game Text Localization Report

## 1. Summary
*   **Scope**: Player-visible strings in `.java` files.
*   **Primary Languages Found**: English (Dominant), Thai (Partial), Korean (Rare/Mixed).
*   **Target Language**: Natural, Immersive Thai (MapleStory Style).

## 2. Detected Text & Localization Plan

### A. `src/scripting/NPCConversationManager.java`
**Context**: This class handles NPC interactions and seems to contain default messages or hardcoded script fallbacks.
**Impact**: High (Direct player interaction).

| Line | Method | Original Text (EN/KR) | Proposed Thai Localization | Tone |
| :--- | :--- | :--- | :--- | :--- |
| 1463 | `serverNotice` | "You do not have enough mesos." | "Meso ของคุณไม่เพียงพอ" | Neutral |
| 2163 | `sendNext` | "Please make sure you have a marriage." | "กรุณาตรวจสอบสถานะการแต่งงานของคุณ" | Formal |
| 2424 | `sendOk` | "Please enter at least two characters for the search term." | "กรุณากรอกคำค้นหาอย่างน้อย 2 ตัวอักษร" | System |
| 3014 | `sendNext` | "There are no overseas cash items available right now. Please come back later!" | "ขณะนี้ยังไม่มีไอเทม Cash จากต่างประเทศ กรุณากลับมาใหม่ภายหลัง" | Polite |
| 3298 | `dropMessage` | "Cannot upgrade further." | "ไม่สามารถอัปเกรดได้อีกต่อไป" | System |
| 3471 | `dropMessage` | "Only Explorers can change jobs freely." | "เฉพาะอาชีพ Explorer เท่านั้นที่สามารถเปลี่ยนอาชีพได้อย่างอิสระ" | Info |
| 3670 | `dropMessage` | "เสีย 4,000 " + cashName + "." | "ใช้จ่าย 4,000 " + cashName | System |
| 3989 | `sendNext` | "Reward distributed... Use it well." | "ได้รับของรางวัลแล้ว... จงใช้มันให้คุ้มค่า" | Epic |

### B. `src/objects/users/MapleCharacter.java`
**Context**: Core character logic, system messages, and notifications.
**Impact**: Very High (System messages seen frequently).

| Line | Method | Original Text (EN/KR/Mixed) | Proposed Thai Localization | Tone |
| :--- | :--- | :--- | :--- | :--- |
| 1341 | `dropMessage` | "ได้รับ Dance Point 1 แต้ม " + ... + "포인트가 되었습니다." | "ได้รับ Dance Point 1 แต้ม รวมเป็น " + ... + " แต้ม" | System |
| 101 | `dropMessage` | "The seller has too many mesos." | "ผู้ขายมี Meso มากเกินไป" | System |

### C. `src/objects/shop/HiredMerchant.java`
**Context**: Player shop interactions.

| Line | Method | Original Text (EN) | Proposed Thai Localization | Tone |
| :--- | :--- | :--- | :--- | :--- |
| 88 | `dropMessage` | "Item " + name + " (" + qty + ") x " + bundle + " has sold..." | "ไอเทม " + name + " (" + qty + ") x " + bundle + " ถูกขายแล้ว..." | System |

## 3. General Rules for Translators
*   **Do Not Translate**: Item Names (`#z...#`), Map Names, Job Names (unless official Thai names are provided).
*   **Placeholders**: Maintain `%s`, `%d`, `{0}`, and string concatenations `+ variable +` strictly.
*   **Tone**: Use polite but firm system messages. Use immersive language for NPC defaults.

## 4. Ambiguities & Questions
*   **GM Messages**: Messages in `AdminClient` and `CheatTracker` (e.g., "[GM Message]") are proposed to remain **English** per "Developer / Server Messages" rule.
*   **Partial Thai**: Some strings are already Thai strings. These will be reviewed for consistency but not rewritten unless awkward.
