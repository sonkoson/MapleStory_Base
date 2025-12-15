# Thai Game Text Localization Report

## Scope
This report covers player-visible text found in the following Java files:
1. `src/commands/CharCommands.java`
2. `src/commands/PlayerCommand.java`
3. `src/network/login/processors/CharLoginHandler.java`

## Localization Matrix

### 1. File: `src/commands/CharCommands.java`
**Context**: Admin/GM Command responses and notifications.
**Tone**: Systemic, Informative, Warning.

| Line | Original Text (EN) | Proposed Thai Localization | Context | Notes |
|------|--------------------|----------------------------|---------|-------|
| 177 | "Map: " + ... + " x: " + ... | "แผนที่: " + ... + " x: " + ... | Command Output | "Map" translated to "แผนที่". Coordinates X/Y kept as is. |
| 185 | "Usage: !givecash <player> <amount>" | "วิธีใช้: !givecash <ชื่อตัวละคร> <จำนวน>" | Error/Usage | Standard command usage format. |
| 196 | "You have received " + rc + " Cash." | "คุณได้รับ " + rc + " แคช" | Notification | "Cash" transliterated as "แคช". |
| 197 | targetName + " has received " + rc + " Cash." | targetName + " ได้รับ " + rc + " แคช" | Notification | |
| 217 | targetName + " does not exist." | "ไม่พบผู้เล่น " + targetName | Error | Adjusted structure for natural Thai phrasing. |
| 248 | targetName + " has received " + rc + " Cash (Offline)." | targetName + " ได้รับ " + rc + " แคช (ออฟไลน์)" | Notification | |
| 252 | "Scripts reloaded." | "โหลดสคริปต์ใหม่เรียบร้อยแล้ว" | System | |
| 295 | "Heals yourself." | "ฟื้นฟูเลือดและมานาของคุณ" | Help Text | Natural MMORPG term for Heal. |
| 296 | "Changes your job." | "เปลี่ยนอาชีพของคุณ" | Help Text | |
| 297 | "Levels up." | "เพิ่มเลเวล" | Help Text | |
| 314 | "Reloads scripts from disk." | "โหลดสคริปต์ใหม่จากดิสก์" | Help Text | |

### 2. File: `src/commands/PlayerCommand.java`
**Context**: Utility commands for players (`@str`, `@check`, `@town`, etc.).
**Tone**: Helpful, User-friendly.

| Line | Original Text (EN) | Proposed Thai Localization | Context | Notes |
|------|--------------------|----------------------------|---------|-------|
| 38 | "You cannot add negative stats." | "คุณไม่สามารถเพิ่มค่าสถานะติดลบได้" | Error | |
| 61 | "You cannot raise a stat above 32,767." | "คุณไม่สามารถเพิ่มค่าสถานะเกิน 32,767 ได้" | Error | |
| 65 | "You do not have enough AP." | "คุณมีค่า AP ไม่เพียงพอ" | Error | |
| 88 | "Actions enabled." | "ปลดล็อคการกระทำแล้ว" | Command Feedback | "Actions enabled" usually refers to `@ea` or `@dismis` equivalent. |
| 94 | "Total Online Players: " + total | "ผู้เล่นออนไลน์ทั้งหมด: " + total | Information | |
| 98 | "You cannot use this command here." | "คุณไม่สามารถใช้คำสั่งนี้ที่นี่ได้" | Error | |
| 112 | "HP/MP Stat raising is currently disabled..." | "การเพิ่มค่า HP/MP ผ่านคำสั่งถูกปิดใช้งาน..." | Error | |
| 114 | "Available Commands:" | "คำสั่งที่ใช้งานได้:" | Help Header | |

### 3. File: `src/network/login/processors/CharLoginHandler.java`
**Context**: Login screen messages, ban notices, secondary password prompts.
**Tone**: Formal, Strict (for bans), Informative.

| Line | Original Text (KR/EN) | Proposed Thai Localization | Context | Notes |
|------|-----------------------|----------------------------|---------|-------|
| 127 | "현재 서버 데이터를 로딩하고 있습니다.\r\n잠시 후 다시 시도해주시기 바랍니다." | "กำลังโหลดข้อมูลเซิร์ฟเวอร์\r\nกรุณาลองใหม่อีกครั้งในภายหลัง" | Login Error | Standard server loading message. |
| 135 | "2ndPassword : " + ... | "รหัสผ่านชั้นที่ 2: " + ... | System Info | "2ndPassword" to "รหัสผ่านชั้นที่ 2". |
| 137 | "Don't have data :(" | "ไม่พบข้อมูล :(" | Error | Retained emoticon as it conveys tone. |
| 143 | "접속기를 통해 로그인해주시기 바랍니다." | "กรุณาเข้าสู่ระบบผ่านตัวเปิดเกม (Launcher)" | System Restriction | "Launcher" is commonly used. |
| 147 | "해당 계정은 이용이 정지되었습니다." | "บัญชีนี้ถูกระงับการใช้งาน" | Account Ban | Standard ban message. |
| 403 | "2차 비밀번호 변경은 디스코드 내에 ..." | "สำหรับการเปลี่ยนรหัสผ่านชั้นที่ 2 กรุณาใช้ห้อง [คำสั่งบอท] ใน Discord" | System Info | "Discord" transliterated or kept as proper noun. |
| 405 | "2차 비밀번호 변경은 홈페이지 고객센터로 문의해 주세요." | "สำหรับการเปลี่ยนรหัสผ่านชั้นที่ 2 กรุณาติดต่อฝ่ายบริการลูกค้าที่หน้าเว็บไซต์" | System Info | Adapted "Homepage Customer Center" to "Website Customer Support". |
| 441 | "해당 계정은 이용이 제한된 계정입니다.... " | "บัญชีนี้ถูกจำกัดการใช้งาน\r\n\r\nไม่สามารถเข้าใช้งานได้จนถึง:\r\n\r\n" | Temporary Ban | Preserved formatting with `\r\n`. |
| 473 | "해당 기기는 접속이 제한되었습니다." | "อุปกรณ์นี้ถูกจำกัดการเข้าถึง" | Hardware Ban | |
| 504 | "해당 IP는 접속이 제한되었습니다." | "IP นี้ถูกจำกัดการเข้าถึง" | IP Ban | |
| 548 | "해당 계정은 영구 이용정지 처리되었습니다." | "บัญชีนี้ถูกระงับการใช้งานถาวร" | Permanent Ban | |

### 4. File: `src/network/login/LoginWorker.java`
**Context**: Server Maintenance Checks during login.
**Tone**: Formal.

| Line | Original Text (KR) | Proposed Thai Localization | Context | Notes |
|------|--------------------|----------------------------|---------|-------|
| 16 | "현재 서버 점검중입니다. 자세한 내용은 홈페이지를 참고해주세요." | "เซิร์ฟเวอร์กำลังปิดปรับปรุง กรุณาตรวจสอบรายละเอียดที่หน้าเว็บไซต์" | Maintenance | Standard maintenance message. |

## Pending Files (Scan Results)
The following files have been identified as containing Korean text and require further review:
- `src/network/Start.java`
- `src/network/ShutdownServer.java`
- `src/network/login/LoginServer.java`
- `src/network/login/LoginInformationProvider.java`
- `src/network/shop/CashItemFactory.java`
- `src/network/shop/CashShopServer.java`
- `src/network/shop/processors/CashShopHandler.java`
- `src/network/models/CWvsContext.java` (Packet definitions likely containing system strings)
- `src/network/game/processors/*` (Handlers for various game mechanics)

## Summary & Recommendations
1. **Consistency**: Ensure terms like "Account", "Character", "Stats", and "Cash" are consistent across all files.
2. **Formatting**: Formatting codes (e.g., `%s`, `\r\n`) must be preserved exactly in the implementation phase.
3. **Ambiguities**: Some Korean error messages in `CharLoginHandler` are quite specific ("팅겼다고인마" - "You got disconnected, dude"). This sounds like a debug message but if it appears to players, it should be professionalized or kept as a generic disconnection message. The report treats debug logs as internal, only targeting explicitly sent packets (`serverNotice`).

Awaiting approval to apply these changes.
