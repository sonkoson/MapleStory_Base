# Thai Game Text Localization Analysis

## Summary
- Player-Visible Messages (Total Candidates): 1319
- Log/Dev Messages (Target: English): 2
- Unclassified Korean Strings (Target: TBD): 2036

## 1. Player-Visible Messages (Thai)
> Messages detected in `sendNotice`, `dropMessage`, etc. Should be localized to immersive Thai.

| File | Line | Context | Original | Proposed Target (Thai) |
| --- | --- | --- | --- | --- |
| `src\commands\BanningCommands.java` | 118 | Method: dropMessage | ` has been banned until ` | Pending |
| `src\commands\BanningCommands.java` | 132 | Method: dropMessage | ` (Offline) has been banned until ` | Pending |
| `src\commands\BanningCommands.java` | 141 | Method: dropMessage | ` has been banned until ` | Pending |
| `src\commands\BanningCommands.java` | 145 | Method: dropMessage | `!unban <character name>` | Pending |
| `src\commands\BanningCommands.java` | 149 | Method: dropMessage | ` not found.` | Pending |
| `src\commands\BanningCommands.java` | 151 | Method: dropMessage | ` found, but error occurred during unban.` | Pending |
| `src\commands\BanningCommands.java` | 153 | Method: dropMessage | ` successfully unbanned.` | Pending |
| `src\commands\BanningCommands.java` | 182 | Method: dropMessage | `Please use dc -f instead.` | Pending |
| `src\commands\BanningCommands.java` | 279 | Method: dropMessage | ` has been banned.` | Pending |
| `src\commands\BanningCommands.java` | 281 | Method: dropMessage | `Error occurred while banning.` | Pending |
| `src\commands\BanningCommands.java` | 287 | Method: dropMessage | ` (Offline) has been banned.` | Pending |
| `src\commands\BanningCommands.java` | 289 | Method: dropMessage | ` failed to ban.` | Pending |
| `src\commands\BanningCommands.java` | 293 | Method: dropMessage | ` has been banned.` | Pending |
| `src\commands\BanningCommands.java` | 295 | Method: dropMessage | `Error while banning.` | Pending |
| `src\commands\CharCommands.java` | 177 | Method: dropMessage | `แผนที่: ` | Already Thai |
| `src\commands\CharCommands.java` | 185 | Method: dropMessage | `วิธีใช้: !givecash <ชื่อตัวละคร> <จำนวน>` | Already Thai |
| `src\commands\CharCommands.java` | 196 | Method: dropMessage | `คุณได้รับ ` | Already Thai |
| `src\commands\CharCommands.java` | 196 | Method: dropMessage | ` แคช` | Already Thai |
| `src\commands\CharCommands.java` | 197 | Method: dropMessage | ` ได้รับ ` | Already Thai |
| `src\commands\CharCommands.java` | 197 | Method: dropMessage | ` แคช` | Already Thai |
| `src\commands\CharCommands.java` | 217 | Method: dropMessage | `ไม่พบผู้เล่น ` | Already Thai |
| `src\commands\CharCommands.java` | 227 | Method: dropMessage | `ไม่พบผู้เล่น ` | Already Thai |
| `src\commands\CharCommands.java` | 248 | Method: dropMessage | ` ได้รับ ` | Already Thai |
| `src\commands\CharCommands.java` | 248 | Method: dropMessage | ` แคช (ขณะออฟไลน์)` | Already Thai |
| `src\commands\CharCommands.java` | 252 | Method: dropMessage | `โหลดสคริปต์ใหม่เรียบร้อยแล้ว` | Already Thai |
| `src\commands\CommandProcessor.java` | 131 | Method: dropMessage | `ช่วยเหลือคำสั่ง: --------` | Already Thai |
| `src\commands\CommandProcessor.java` | 131 | Method: dropMessage | `---------` | Pending |
| `src\commands\CommandProcessor.java` | 183 | Method: dropMessage | `ไม่พบคำสั่ง ` | Already Thai |
| `src\commands\CommandProcessor.java` | 183 | Method: dropMessage | ` หรือคุณไม่มีสิทธิ์ใช้งาน` | Already Thai |
| `src\commands\DebugCommands.java` | 140 | Method: dropMessage | `: ` | Pending |
| `src\commands\DebugCommands.java` | 199 | Method: dropMessage | `Exp Rate has been changed to ` | Pending |
| `src\commands\DebugCommands.java` | 199 | Method: dropMessage | `x.` | Pending |
| `src\commands\DebugCommands.java` | 201 | Method: dropMessage | `วิธีใช้: !exprate <rate>` | Already Thai |
| `src\commands\DebugCommands.java` | 207 | Method: dropMessage | `Meso Rate has been changed to ` | Pending |
| `src\commands\DebugCommands.java` | 207 | Method: dropMessage | `x.` | Pending |
| `src\commands\DebugCommands.java` | 209 | Method: dropMessage | `วิธีใช้: !mesorate <rate>` | Already Thai |
| `src\commands\DebugCommands.java` | 217 | Method: dropMessage | `Map : ` | Pending |
| `src\commands\DebugCommands.java` | 224 | Method: dropMessage | `Coords (x: ` | Pending |
| `src\commands\DebugCommands.java` | 242 | Method: dropMessage | `Map : ` | Pending |
| `src\commands\DebugCommands.java` | 352 | Method: dropMessage | `Fever event reloaded.` | Pending |
| `src\commands\DebugCommands.java` | 357 | Method: dropMessage | `Fever item event reloaded.` | Pending |
| `src\commands\DebugCommands.java` | 399 | Method: dropMessage | `Daily Event stopped.` | Pending |
| `src\commands\DebugCommands.java` | 402 | Method: chatMsg | `[Daily Event] กิจกรรมรายวันหยุดแล้ว กิจกรรมมีช่...` | Already Thai |
| `src\commands\DebugCommands.java` | 405 | Method: dropMessage | `วิธีใช้: !skilllevel <skillID>` | Already Thai |
| `src\commands\DebugCommands.java` | 446 | Method: dropMessage | `Skill (` | Pending |
| `src\commands\DebugCommands.java` | 453 | Method: dropMessage | `Skill (` | Pending |
| `src\commands\DebugCommands.java` | 457 | Method: dropMessage | `(` | Pending |
| `src\commands\DebugCommands.java` | 464 | Method: dropMessage | `Error setting cash rate. Please check input.` | Pending |
| `src\commands\DebugCommands.java` | 469 | Method: dropMessage | `Cash rate set to ` | Pending |
| `src\commands\DebugCommands.java` | 469 | Method: dropMessage | `%(Total ` | Pending |
| `src\commands\DebugCommands.java` | 472 | Method: dropMessage | `Current Cash Rate Plus: ` | Pending |
| `src\commands\DebugCommands.java` | 472 | Method: dropMessage | `%(Total ` | Pending |
| `src\commands\DebugCommands.java` | 477 | Method: dropMessage | `Start map set to ` | Pending |
| `src\commands\DebugCommands.java` | 477 | Method: dropMessage | `.` | Pending |
| `src\commands\DebugCommands.java` | 479 | Method: dropMessage | `Error. check map id.` | Pending |
| `src\commands\DebugCommands.java` | 485 | Method: dropMessage | `ตั้งค่าแผนที่เมืองเป็น ` | Already Thai |
| `src\commands\DebugCommands.java` | 485 | Method: dropMessage | `.` | Pending |
| `src\commands\DebugCommands.java` | 487 | Method: dropMessage | `Error. check map id.` | Pending |
| `src\commands\DebugCommands.java` | 498 | Method: dropMessage | `Stopped music in this map on all channels.` | Pending |
| `src\commands\DebugCommands.java` | 501 | Method: dropMessage | `Stopped music in this map.` | Pending |
| `src\commands\DebugCommands.java` | 505 | Method: dropMessage | `GM Level set to 0.` | Pending |
| `src\commands\DebugCommands.java` | 513 | Method: dropMessage | `Error.` | Pending |
| `src\commands\DebugCommands.java` | 520 | Method: dropMessage | `Together Point: ` | Pending |
| `src\commands\DebugCommands.java` | 522 | Method: dropMessage | `Invalid input.` | Pending |
| `src\commands\DebugCommands.java` | 553 | Method: dropMessage | `Item not found in your inventory.` | Pending |
| `src\commands\DebugCommands.java` | 579 | Method: dropMessage | `Character or Account not found.` | Pending |
| `src\commands\DebugCommands.java` | 595 | Method: dropMessage | `Target player's cabinet is full or invalid.` | Pending |
| `src\commands\DebugCommands.java` | 610 | Method: dropMessage | `[System] คุณได้รับของขวัญจาก GM ` | Already Thai |
| `src\commands\DebugCommands.java` | 611 | Method: dropMessage | `Item sent to online player ` | Pending |
| `src\commands\DebugCommands.java` | 761 | Method: dropMessage | `Item sent to offline account ` | Pending |
| `src\commands\DebugCommands.java` | 761 | Method: dropMessage | ` (Cabinet).` | Pending |
| `src\commands\DebugCommands.java` | 789 | Method: dropMessage | `Target player's cabinet is full or invalid.` | Pending |
| `src\commands\DebugCommands.java` | 804 | Method: dropMessage | `[System] คุณได้รับของขวัญจาก GM ` | Already Thai |
| `src\commands\DebugCommands.java` | 806 | Method: dropMessage | `Item sent to online player ` | Pending |
| `src\commands\DebugCommands.java` | 810 | Method: dropMessage | `วิธีใช้: !giveitems <type 1-5> <slot> <quantity...` | Already Thai |
| `src\commands\DebugCommands.java` | 819 | Method: dropMessage | `Portal not found.` | Pending |
| `src\commands\DebugCommands.java` | 838 | Method: dropMessage | `[System] ไม่สามารถย้ายแผนที่ขณะตายหรือติดภารกิจได้` | Already Thai |
| `src\commands\DebugCommands.java` | 851 | Method: dropMessage | `Monitoring Channel ` | Pending |
| `src\commands\DebugCommands.java` | 851 | Method: dropMessage | ` Map ` | Pending |
| `src\commands\DebugCommands.java` | 908 | Method: dropMessage | `Account ` | Pending |
| `src\commands\DebugCommands.java` | 908 | Method: dropMessage | ` (` | Pending |
| `src\commands\DebugCommands.java` | 910 | Method: dropMessage | `Player not found.` | Pending |
| `src\commands\DebugCommands.java` | 964 | Method: dropMessage | `Character ` | Pending |
| `src\commands\DebugCommands.java` | 964 | Method: dropMessage | ` (Account: ` | Pending |
| `src\commands\DebugCommands.java` | 992 | Method: dropMessage | `Character ` | Pending |
| `src\commands\DebugCommands.java` | 992 | Method: dropMessage | ` (Account: ` | Pending |
| `src\commands\DebugCommands.java` | 994 | Method: dropMessage | `Character not found.` | Pending |
| `src\commands\DebugCommands.java` | 1048 | Method: dropMessage | `Player has been disconnected. (` | Pending |
| `src\commands\DebugCommands.java` | 1050 | Method: dropMessage | `Player not found.` | Pending |
| `src\commands\DebugCommands.java` | 1092 | Method: dropMessage | `Account has been disconnected.` | Pending |
| `src\commands\DebugCommands.java` | 1094 | Method: dropMessage | `Account not found.` | Pending |
| `src\commands\DebugCommands.java` | 1097 | Method: dropMessage | `Captcha Code (` | Pending |
| `src\commands\DebugCommands.java` | 1102 | Method: dropMessage | `Player not found.` | Pending |
| `src\commands\DebugCommands.java` | 1109 | Method: dropMessage | `Quest (` | Pending |
| `src\commands\DebugCommands.java` | 1129 | Method: dropMessage | `Modified Quest Time reloaded.` | Pending |
| `src\commands\DebugCommands.java` | 1136 | Method: dropMessage | `Auto Notice has been reloaded.` | Pending |
| `src\commands\DebugCommands.java` | 1139 | Method: dropMessage | `[System] รีเซ็ตอันดับวัดความเสียหายแล้ว` | Already Thai |
| `src\commands\DebugCommands.java` | 1163 | Method: chatMsg | `Damage Measurement Rank has been reset.` | Pending |
| `src\commands\DebugCommands.java` | 1174 | Method: dropMessage | `Reactor: Name/OID: ` | Pending |
| `src\commands\MonsterInfoCommands.java` | 42 | Method: dropMessage | `มอนสเตอร์: ` | Already Thai |
| `src\commands\NPCSpawningCommands.java` | 35 | Method: dropMessage | `ไม่พบ NPC ที่ใช้ ID นี้` | Already Thai |
| `src\commands\NPCSpawningCommands.java` | 50 | Method: dropMessage | `CY: ` | Pending |
| `src\commands\NPCSpawningCommands.java` | 60 | Method: dropMessage | `กำลังรีโหลด NPC อันดับ Dream Breaker...` | Already Thai |
| `src\commands\NPCSpawningCommands.java` | 72 | Method: dropMessage | `เสร็จสิ้น!` | Already Thai |
| `src\commands\NPCSpawningCommands.java` | 74 | Method: dropMessage | `เกิดข้อผิดพลาดในการรีโหลด NPC อันดับ Dream Brea...` | Already Thai |
| `src\commands\NPCSpawningCommands.java` | 79 | Method: dropMessage | `กำลังสร้าง NPC ผู้เล่น...` | Already Thai |
| `src\commands\NPCSpawningCommands.java` | 82 | Method: dropMessage | ` ไม่ได้ออนไลน์หรือไม่มีอยู่จริง` | Already Thai |
| `src\commands\NPCSpawningCommands.java` | 87 | Method: dropMessage | `เสร็จสิ้น!` | Already Thai |
| `src\commands\NPCSpawningCommands.java` | 90 | Method: dropMessage | `เกิดข้อผิดพลาดในการสร้าง NPC ผู้เล่น: ` | Already Thai |
| `src\commands\NPCSpawningCommands.java` | 97 | Method: dropMessage | `ไม่พบ NPC ในข้อมูล WZ` | Already Thai |
| `src\commands\PlayerCommand.java` | 38 | Method: dropMessage | `คุณไม่สามารถเพิ่มค่าสถานะติดลบได้` | Already Thai |
| `src\commands\PlayerCommand.java` | 61 | Method: dropMessage | `คุณไม่สามารถเพิ่มค่าสถานะเกิน 32,767 ได้` | Already Thai |
| `src\commands\PlayerCommand.java` | 65 | Method: dropMessage | `คุณมีค่า AP ไม่เพียงพอ` | Already Thai |
| `src\commands\PlayerCommand.java` | 84 | Method: dropMessage | `สถานะ: STR=` | Already Thai |
| `src\commands\PlayerCommand.java` | 88 | Method: dropMessage | `ปลดล็อคการกระทำแล้ว` | Already Thai |
| `src\commands\PlayerCommand.java` | 94 | Method: dropMessage | `ผู้เล่นออนไลน์ทั้งหมด: ` | Already Thai |
| `src\commands\PlayerCommand.java` | 98 | Method: dropMessage | `คุณไม่สามารถใช้คำสั่งนี้ที่นี่ได้` | Already Thai |
| `src\commands\PlayerCommand.java` | 112 | Method: dropMessage | `การเพิ่มค่า HP/MP ผ่านคำสั่งถูกปิดใช้งานเนื่องจ...` | Already Thai |
| `src\commands\PlayerCommand.java` | 114 | Method: dropMessage | `คำสั่งที่ใช้งานได้:` | Already Thai |
| `src\commands\PlayerCommand.java` | 115 | Method: dropMessage | `@str, @dex, @int, @luk <จำนวน> - เพิ่มค่าสถานะ` | Already Thai |
| `src\commands\PlayerCommand.java` | 116 | Method: dropMessage | `@check - ตรวจสอบสถานะ` | Already Thai |
| `src\commands\PlayerCommand.java` | 117 | Method: dropMessage | `@ea / @dispose - แก้ตัวละครค้าง` | Already Thai |
| `src\commands\PlayerCommand.java` | 118 | Method: dropMessage | `@online - แสดงผู้เล่นออนไลน์` | Already Thai |
| `src\commands\PlayerCommand.java` | 119 | Method: dropMessage | `@town - วาร์ปไปเมือง` | Already Thai |
| `src\commands\ProfilingCommands.java` | 32 | Method: dropMessage | `เริ่มการวิเคราะห์แล้ว` | Already Thai |
| `src\commands\ProfilingCommands.java` | 44 | Method: dropMessage | `ไฟล์นี้มีอยู่แล้ว โปรดเลือกชื่อไฟล์อื่น` | Already Thai |
| `src\commands\ProfilingCommands.java` | 52 | Method: dropMessage | `หยุดการวิเคราะห์และบันทึกไปยัง ` | Already Thai |
| `src\commands\ProfilingCommands.java` | 55 | Method: dropMessage | `เกิดข้อผิดพลาดในการบันทึกโปรไฟล์: ` | Already Thai |
| `src\commands\ReloadingCommands.java` | 29 | Method: dropMessage | `[System] โหลดข้อมูล Ops ใหม่เสร็จสิ้น` | Already Thai |
| `src\commands\ReloadingCommands.java` | 34 | Method: dropMessage | `[Set Op] ` | Pending |
| `src\commands\ReloadingCommands.java` | 41 | Method: dropMessage | `[System] โหลดข้อมูล Drop ใหม่เสร็จสิ้น` | Already Thai |
| `src\commands\ReloadingCommands.java` | 44 | Method: dropMessage | `[System] โหลด Portal Script ใหม่เสร็จสิ้น` | Already Thai |
| `src\commands\ReloadingCommands.java` | 47 | Method: dropMessage | `[System] โหลดข้อมูล Shop ใหม่เสร็จสิ้น` | Already Thai |
| `src\commands\ReloadingCommands.java` | 52 | Method: dropMessage | `[System] โหลดข้อมูล Event ใหม่เสร็จสิ้น` | Already Thai |
| `src\commands\ReloadingCommands.java` | 55 | Method: dropMessage | `[System] โหลดข้อมูล Skill ใหม่เสร็จสิ้น` | Already Thai |
| `src\commands\ReloadingCommands.java` | 58 | Method: dropMessage | `[System] โหลดข้อมูล Weekly Item ใหม่เสร็จสิ้น` | Already Thai |
| `src\commands\ReloadingCommands.java` | 61 | Method: dropMessage | `[System] บันทึก Weekly Item ลง DB แล้ว` | Already Thai |
| `src\commands\ReloadingCommands.java` | 64 | Method: dropMessage | `[System] รีเซ็ต Gold Apple เสร็จสิ้น` | Already Thai |
| `src\commands\ReloadingCommands.java` | 67 | Method: dropMessage | `[System] โหลดข้อมูล Custom Mob HP ใหม่เสร็จสิ้น` | Already Thai |
| `src\commands\ReloadingCommands.java` | 70 | Method: dropMessage | `[System] โหลดข้อมูล Daily Gift ใหม่เสร็จสิ้น` | Already Thai |
| `src\commands\ReloadingCommands.java` | 73 | Method: dropMessage | `[System] โหลดข้อมูล Dimensional Mirror ใหม่เสร็...` | Already Thai |
| `src\commands\ReloadingCommands.java` | 76 | Method: dropMessage | `[System] โหลดข้อมูล Event List ใหม่เสร็จสิ้น` | Already Thai |
| `src\commands\ReloadingCommands.java` | 79 | Method: dropMessage | `[System] โหลดข้อมูล Fishing ใหม่เสร็จสิ้น` | Already Thai |
| `src\commands\ReloadingCommands.java` | 82 | Method: dropMessage | `[System] โหลดข้อมูล Golden Chariot ใหม่เสร็จสิ้น` | Already Thai |
| `src\commands\ReloadingCommands.java` | 85 | Method: dropMessage | `[System] โหลดข้อมูล WZ CRC ใหม่เสร็จสิ้น` | Already Thai |
| `src\commands\ReloadingCommands.java` | 88 | Method: dropMessage | `[System] โหลดอันดับวัดความเสียหายเสร็จสิ้น` | Already Thai |
| `src\commands\RoyalCommand.java` | 18 | Method: dropMessage | `รูปแบบคำสั่ง: !setquestkv <ชื่อผู้เล่น> <รหัสเค...` | Already Thai |
| `src\commands\RoyalCommand.java` | 25 | Method: dropMessage | `ตั้งค่า KeyValue ของเควสเรียบร้อยแล้ว` | Already Thai |
| `src\commands\RoyalCommand.java` | 27 | Method: dropMessage | `ไม่พบผู้เล่นดังกล่าว` | Already Thai |
| `src\commands\RoyalCommand.java` | 31 | Method: dropMessage | `รูปแบบคำสั่ง: !removequestkv <ชื่อผู้เล่น> <รหั...` | Already Thai |
| `src\commands\RoyalCommand.java` | 37 | Method: dropMessage | `ไม่พบผู้เล่น` | Already Thai |
| `src\commands\RoyalCommand.java` | 42 | Method: dropMessage | `ลบ KeyValue ของเควสเรียบร้อยแล้ว` | Already Thai |
| `src\commands\RoyalCommand.java` | 47 | Method: dropMessage | `ไม่พบผู้เล่น` | Already Thai |
| `src\commands\RoyalCommand.java` | 52 | Method: dropMessage | `ตั้งค่า KeyValue ของตัวละครเรียบร้อยแล้ว` | Already Thai |
| `src\commands\RoyalCommand.java` | 56 | Method: dropMessage | `!removecharkv <ชื่อผู้เล่น> <คีย์>` | Already Thai |
| `src\commands\RoyalCommand.java` | 62 | Method: dropMessage | `ไม่พบผู้เล่น` | Already Thai |
| `src\commands\RoyalCommand.java` | 66 | Method: dropMessage | `ลบ KeyValue ของตัวละครเรียบร้อยแล้ว` | Already Thai |
| `src\commands\RoyalCommand.java` | 70 | Method: dropMessage | `!setaccountkv <ชื่อผู้เล่น> <คีย์> <ค่า>` | Already Thai |
| `src\commands\RoyalCommand.java` | 76 | Method: dropMessage | `ไม่พบผู้เล่น` | Already Thai |
| `src\commands\RoyalCommand.java` | 81 | Method: dropMessage | `ตั้งค่า KeyValue ของบัญชีเรียบร้อยแล้ว` | Already Thai |
| `src\commands\RoyalCommand.java` | 85 | Method: dropMessage | `!removeaccountkv <ชื่อผู้เล่น> <คีย์>` | Already Thai |
| `src\commands\RoyalCommand.java` | 91 | Method: dropMessage | `ไม่พบผู้เล่น` | Already Thai |
| `src\commands\RoyalCommand.java` | 95 | Method: dropMessage | `ลบ KeyValue ของบัญชีเรียบร้อยแล้ว` | Already Thai |
| `src\commands\RoyalCommand.java` | 99 | Method: dropMessage | `!giveoffline <ชื่อผู้เล่น> <รหัสไอเทม> <จำนวน>` | Already Thai |
| `src\commands\RoyalCommand.java` | 116 | Method: dropMessage | `[ของขวัญออฟไลน์] มอบไอเทม ` | Already Thai |
| `src\commands\RoyalCommand.java` | 116 | Method: dropMessage | ` จำนวน ` | Already Thai |
| `src\commands\RoyalCommand.java` | 116 | Method: dropMessage | ` ชิ้น ให้กับ ` | Already Thai |
| `src\commands\RoyalCommand.java` | 117 | Method: dropMessage | ` เรียบร้อยแล้ว` | Already Thai |
| `src\commands\RoyalCommand.java` | 119 | Method: dropMessage | `ไม่พบตัวละคร` | Already Thai |
| `src\commands\RoyalCommand.java` | 129 | Method: dropMessage | `!clearoffline <ชื่อผู้เล่น>` | Already Thai |
| `src\commands\RoyalCommand.java` | 142 | Method: dropMessage | `[ของขวัญออฟไลน์] ลบของขวัญออฟไลน์ของ ` | Already Thai |
| `src\commands\RoyalCommand.java` | 142 | Method: dropMessage | ` เรียบร้อยแล้ว` | Already Thai |
| `src\commands\RoyalCommand.java` | 144 | Method: dropMessage | `ไม่พบตัวละคร` | Already Thai |
| `src\commands\SearchCommands.java` | 22 | Method: dropMessage | `: <npc> <mob> <item> <map> <skill> <quest>` | Pending |
| `src\commands\SearchCommands.java` | 29 | Method: dropMessage | `<<Type: ` | Pending |
| `src\commands\SearchCommands.java` | 29 | Method: dropMessage | ` \| Search: ` | Pending |
| `src\commands\SearchCommands.java` | 29 | Method: dropMessage | `>>` | Pending |
| `src\commands\SearchCommands.java` | 51 | Method: dropMessage | `No NPC found.` | Pending |
| `src\commands\SearchCommands.java` | 80 | Method: dropMessage | `No Map found.` | Pending |
| `src\commands\SearchCommands.java` | 103 | Method: dropMessage | `No Mob found.` | Pending |
| `src\commands\SearchCommands.java` | 106 | Method: dropMessage | `NOT ADDED YET` | Pending |
| `src\commands\SearchCommands.java` | 122 | Method: dropMessage | `No Item found.` | Pending |
| `src\commands\SearchCommands.java` | 154 | Method: dropMessage | `No Skill found.` | Pending |
| `src\commands\SearchCommands.java` | 180 | Method: dropMessage | `No Quest found.` | Pending |
| `src\commands\SearchCommands.java` | 183 | Method: dropMessage | `ไม่พบ Type` | Already Thai |
| `src\commands\SpawnMonsterCommand.java` | 67 | Method: dropMessage | `มอนสเตอร์ตัวนี้ถูกจำกัด` | Already Thai |
| `src\commands\SpawnObjectCommands.java` | 29 | Method: dropMessage | `ใส่ค่าได้เพียง 0-9 และสามารถสร้างรูนได้ครั้งละ ...` | Already Thai |
| `src\commands\SpawnObjectCommands.java` | 33 | Method: dropMessage | `มีออบเจกต์จำนวน ` | Already Thai |
| `src\commands\WarpCommands.java` | 24 | Method: dropMessage | `ไม่พบแผนที่` | Already Thai |
| `src\commands\WarpCommands.java` | 32 | Method: dropMessage | `เลือกพอร์ทัลไม่ถูกต้อง` | Already Thai |
| `src\commands\WarpCommands.java` | 44 | Method: dropMessage | `กำลังย้ายแชนแนลเพื่อวาร์ปไปหาผู้เล่น` | Already Thai |
| `src\commands\WarpCommands.java` | 53 | Method: dropMessage | `เกิดข้อผิดพลาดบางอย่าง` | Already Thai |
| `src\commands\WarpCommands.java` | 61 | Method: dropMessage | `ไม่พบแผนที่` | Already Thai |
| `src\commands\WarpCommands.java` | 69 | Method: dropMessage | `เลือกพอร์ทัลไม่ถูกต้อง` | Already Thai |
| `src\commands\WarpCommands.java` | 84 | Method: dropMessage | `ไม่สามารถวาร์ปผู้เล่นซ้ำๆ ได้` | Already Thai |
| `src\commands\WarpCommands.java` | 92 | Method: dropMessage | `ไม่พบตัวละคร` | Already Thai |
| `src\commands\WarpCommands.java` | 98 | Method: dropMessage | `ไม่สามารถวาร์ปผู้เล่นได้` | Already Thai |
| `src\commands\WarpCommands.java` | 101 | Method: dropMessage | `กำลังเรียกผู้เล่นจากแชนแนลอื่น` | Already Thai |
| `src\commands\WarpCommands.java` | 102 | Method: dropMessage | `คุณถูกเรียกตัวโดย GM` | Already Thai |
| `src\commands\WarpCommands.java` | 119 | Method: dropMessage | `ไม่พบแผนที่ ` | Already Thai |
| `src\commands\WarpCommands.java` | 128 | Method: dropMessage | `เลือกพอร์ทัลไม่ถูกต้อง` | Already Thai |
| `src\network\auction\processors\AuctionHandler.java` | 172 | Method: dropMessage | `Trace of Equipment ไม่สามารถลงขายได้` | Already Thai |
| `src\network\auction\processors\AuctionHandler.java` | 185 | Method: dropMessage | `An error occurred.` | Pending |
| `src\network\auction\processors\AuctionHandler.java` | 192 | Method: dropMessage | `Items with decorative options applied cannot be...` | Pending |
| `src\network\auction\processors\AuctionHandler.java` | 200 | Method: dropMessage | `ไอเทมนี้ลงขายไม่ได้` | Already Thai |
| `src\network\auction\processors\AuctionHandler.java` | 299 | Method: dropMessage | `Trace of Equipment ไม่สามารถลงขายใหม่ได้` | Already Thai |
| `src\network\auction\processors\AuctionHandler.java` | 306 | Method: dropMessage | `ไอเทมนี้ลงขายไม่ได้` | Already Thai |
| `src\network\auction\processors\AuctionHandler.java` | 494 | Method: dropMessage | `Trace of Equipment ไม่สามารถซื้อได้` | Already Thai |
| `src\network\auction\processors\AuctionHandler.java` | 501 | Method: dropMessage | `ไม่สามารถซื้อไอเทมนี้ได้` | Already Thai |
| `src\network\auction\processors\AuctionHandler.java` | 670 | Method: dropMessage | `Trace of Equipment ไม่สามารถซื้อได้` | Already Thai |
| `src\network\center\Center.java` | 757 | Method: chatMsg | `[Ganglim Hottime] แจกรางวัล Hottime แล้ว\r\nรับ...` | Already Thai |
| `src\network\center\Center.java` | 758 | Method: dropMessage | `Hottime reward distributed. Claim from [Maple C...` | Pending |
| `src\network\center\Center.java` | 760 | Method: dropMessage | `[Notice] แจกรางวัล Hottime แล้ว กรุณาตรวจสอบ [M...` | Already Thai |
| `src\network\center\Center.java` | 761 | Method: dropMessage | `Hottime reward distributed.\r\nPlease check [Ma...` | Pending |
| `src\network\center\Center.java` | 1131 | Method: dropMessage | `Beginner Package has been distributed. Please c...` | Pending |
| `src\network\center\Center.java` | 1137 | Method: dropMessage | `Lunar New Year Enhancement Package has been dis...` | Pending |
| `src\network\center\Center.java` | 1144 | Method: dropMessage | `Lunar New Year Master Berry Package has been di...` | Pending |
| `src\network\center\Center.java` | 1151 | Method: dropMessage | `Lunar New Year Value Package has been distribut...` | Pending |
| `src\network\center\Center.java` | 1158 | Method: dropMessage | `Children's Day Package has been distributed. Pl...` | Pending |
| `src\network\center\Center.java` | 1166 | Method: dropMessage | `Family Month Package C has been distributed. Pl...` | Pending |
| `src\network\center\Center.java` | 1173 | Method: dropMessage | `Family Month Package B has been distributed. Pl...` | Pending |
| `src\network\center\Center.java` | 1180 | Method: dropMessage | `Family Month Package A has been distributed. Pl...` | Pending |
| `src\network\center\Center.java` | 1187 | Method: dropMessage | `Family Month Package S has been distributed. Pl...` | Pending |
| `src\network\center\Center.java` | 1194 | Method: dropMessage | `Family Month Package SS has been distributed. P...` | Pending |
| `src\network\center\Center.java` | 1201 | Method: dropMessage | `Family Month Package SSS has been distributed. ...` | Pending |
| `src\network\center\Center.java` | 1208 | Method: dropMessage | `Sealed Stat Box Package has been distributed. P...` | Pending |
| `src\network\center\Center.java` | 1215 | Method: dropMessage | `Medal Option Enhancement Ticket Package has bee...` | Pending |
| `src\network\center\Center.java` | 1222 | Method: dropMessage | `Chuseok Package I has been distributed. Please ...` | Pending |
| `src\network\center\Center.java` | 1229 | Method: dropMessage | `Chuseok Package II has been distributed. Please...` | Pending |
| `src\network\center\Center.java` | 1236 | Method: dropMessage | `Chuseok Package III has been distributed. Pleas...` | Pending |
| `src\network\center\Center.java` | 1243 | Method: dropMessage | `Chuseok Package IV has been distributed. Please...` | Pending |
| `src\network\center\Center.java` | 1250 | Method: dropMessage | `Chuseok Package IV has been distributed. Please...` | Pending |
| `src\network\center\Center.java` | 1257 | Method: dropMessage | `Ganglim Amazing Label Package has been distribu...` | Pending |
| `src\network\center\Center.java` | 1264 | Method: dropMessage | `Ganglim Wisp Enhancement Package has been distr...` | Pending |
| `src\network\center\Center.java` | 1271 | Method: dropMessage | `Ganglim Remaster Package has been distributed. ...` | Pending |
| `src\network\center\Center.java` | 1278 | Method: dropMessage | `Merry Package has been distributed. Please clai...` | Pending |
| `src\network\center\Center.java` | 1285 | Method: dropMessage | `Chris Package has been distributed. Please clai...` | Pending |
| `src\network\center\Center.java` | 1291 | Method: dropMessage | `Mas Package has been distributed. Please claim ...` | Pending |
| `src\network\center\Center.java` | 1298 | Method: dropMessage | `Ganglim Christmas Package has been distributed....` | Pending |
| `src\network\center\Center.java` | 1305 | Method: dropMessage | `Hop hop to 300 Package has been distributed. Pl...` | Pending |
| `src\network\center\Center.java` | 1312 | Method: dropMessage | `Strong Rabbit Package has been distributed. Ple...` | Pending |
| `src\network\center\Center.java` | 1319 | Method: dropMessage | `Rabbit Outfit Package has been distributed. Ple...` | Pending |
| `src\network\center\Center.java` | 1326 | Method: dropMessage | `Cute Rabbit Pet Package has been distributed. P...` | Pending |
| `src\network\center\Center.java` | 1333 | Method: dropMessage | `Savior Package has been distributed. Please cla...` | Pending |
| `src\network\center\Center.java` | 1339 | Method: dropMessage | `Year of the Rabbit Package C has been distribut...` | Pending |
| `src\network\center\Center.java` | 1346 | Method: dropMessage | `Year of the Rabbit Package B has been distribut...` | Pending |
| `src\network\center\Center.java` | 1353 | Method: dropMessage | `Year of the Rabbit Package A has been distribut...` | Pending |
| `src\network\center\Center.java` | 1360 | Method: dropMessage | `Year of the Rabbit Package S has been distribut...` | Pending |
| `src\network\center\Center.java` | 1367 | Method: dropMessage | `Year of the Rabbit Package SS has been distribu...` | Pending |
| `src\network\center\Center.java` | 1374 | Method: dropMessage | `Year of the Rabbit Package SSS has been distrib...` | Pending |
| `src\network\center\Center.java` | 1381 | Method: dropMessage | `Love You 3000 Package has been distributed. Ple...` | Pending |
| `src\network\center\Center.java` | 1388 | Method: dropMessage | `Power Overwhelming Package has been distributed...` | Pending |
| `src\network\center\Center.java` | 1395 | Method: dropMessage | `M@STERPIECE Package has been distributed. Pleas...` | Pending |
| `src\network\center\Center.java` | 1402 | Method: dropMessage | `Life Turnaround? Package has been distributed. ...` | Pending |
| `src\network\center\Center.java` | 1409 | Method: dropMessage | `Eris's Golden Apple Package has been distribute...` | Pending |
| `src\network\center\Center.java` | 1416 | Method: dropMessage | `Family Month Package C has been distributed. Pl...` | Pending |
| `src\network\center\Center.java` | 1423 | Method: dropMessage | `Family Month Package B has been distributed. Pl...` | Pending |
| `src\network\center\Center.java` | 1430 | Method: dropMessage | `Family Month Package A has been distributed. Pl...` | Pending |
| `src\network\center\Center.java` | 1437 | Method: dropMessage | `Family Month Package S has been distributed. Pl...` | Pending |
| `src\network\center\Center.java` | 1444 | Method: dropMessage | `Family Month Package SS has been distributed. P...` | Pending |
| `src\network\center\Center.java` | 1451 | Method: dropMessage | `Family Month Package SSS has been distributed. ...` | Pending |
| `src\network\center\Center.java` | 1469 | Method: dropMessage | `ได้รับ Beginner Package เรียบร้อยแล้ว กรุณารับท...` | Already Thai |
| `src\network\center\Center.java` | 1471 | Method: dropMessage | `ได้รับ Lunar New Year Enhancement Package เรียบ...` | Already Thai |
| `src\network\center\Center.java` | 1474 | Method: dropMessage | `ได้รับ Lunar New Year Master Berry Package เรีย...` | Already Thai |
| `src\network\center\Center.java` | 1477 | Method: dropMessage | `ได้รับ Lunar New Year Value Package เรียบร้อยแล...` | Already Thai |
| `src\network\center\Center.java` | 1480 | Method: dropMessage | `ได้รับ Children's Day Package เรียบร้อยแล้ว กรุ...` | Already Thai |
| `src\network\center\Center.java` | 1484 | Method: dropMessage | `ได้รับ Family Month Package C เรียบร้อยแล้ว กรุ...` | Already Thai |
| `src\network\center\Center.java` | 1487 | Method: dropMessage | `ได้รับ Family Month Package B เรียบร้อยแล้ว กรุ...` | Already Thai |
| `src\network\center\Center.java` | 1490 | Method: dropMessage | `ได้รับ Family Month Package A เรียบร้อยแล้ว กรุ...` | Already Thai |
| `src\network\center\Center.java` | 1493 | Method: dropMessage | `ได้รับ Family Month Package S เรียบร้อยแล้ว กรุ...` | Already Thai |
| `src\network\center\Center.java` | 1496 | Method: dropMessage | `ได้รับ Family Month Package SS เรียบร้อยแล้ว กร...` | Already Thai |
| `src\network\center\Center.java` | 1499 | Method: dropMessage | `ได้รับ Family Month Package SSS เรียบร้อยแล้ว ก...` | Already Thai |
| `src\network\center\Center.java` | 1503 | Method: dropMessage | `Sealed Stat Box Package has been distributed. P...` | Pending |
| `src\network\center\Center.java` | 1507 | Method: dropMessage | `Medal Option Enhancement Ticket Package has bee...` | Pending |
| `src\network\center\Center.java` | 1511 | Method: dropMessage | `Chuseok Package I has been distributed. Please ...` | Pending |
| `src\network\center\Center.java` | 1515 | Method: dropMessage | `Chuseok Package II has been distributed. Please...` | Pending |
| `src\network\center\Center.java` | 1519 | Method: dropMessage | `Chuseok Package III has been distributed. Pleas...` | Pending |
| `src\network\center\Center.java` | 1523 | Method: dropMessage | `Chuseok Package IV has been distributed. Please...` | Pending |
| `src\network\center\Center.java` | 1527 | Method: dropMessage | `Advent Amazing Label Package has been distribut...` | Pending |
| `src\network\center\Center.java` | 1531 | Method: dropMessage | `Advent Wisp's Enhancement Package has been dist...` | Pending |
| `src\network\center\Center.java` | 1535 | Method: dropMessage | `Advent Remaster Package has been distributed. P...` | Pending |
| `src\network\center\Center.java` | 1539 | Method: dropMessage | `Merry Package has been distributed. Please clai...` | Pending |
| `src\network\center\Center.java` | 1543 | Method: dropMessage | `Chris Package has been distributed. Please clai...` | Pending |
| `src\network\center\Center.java` | 1547 | Method: dropMessage | `Mas Package has been distributed. Please claim ...` | Pending |
| `src\network\center\Center.java` | 1551 | Method: dropMessage | `Advent Christmas Package has been distributed. ...` | Pending |
| `src\network\center\Center.java` | 1555 | Method: dropMessage | `Hopping to 300? Package has been distributed. P...` | Pending |
| `src\network\center\Center.java` | 1559 | Method: dropMessage | `Even Rabbits Need Strength to Catch! Package ha...` | Pending |
| `src\network\center\Center.java` | 1563 | Method: dropMessage | `ได้รับ What Do Rabbits Wear? Package แล้ว กรุณา...` | Already Thai |
| `src\network\center\Center.java` | 1567 | Method: dropMessage | `Come Out! Cute Rabbit-like Pet! Package has bee...` | Pending |
| `src\network\center\Center.java` | 1571 | Method: dropMessage | `Savior Package has been distributed. Please cla...` | Pending |
| `src\network\center\Center.java` | 1575 | Method: dropMessage | `Year of the Rabbit Package C has been distribut...` | Pending |
| `src\network\center\Center.java` | 1579 | Method: dropMessage | `Year of the Rabbit Package B has been distribut...` | Pending |
| `src\network\center\Center.java` | 1583 | Method: dropMessage | `Year of the Rabbit Package A has been distribut...` | Pending |
| `src\network\center\Center.java` | 1587 | Method: dropMessage | `Year of the Rabbit Package S has been distribut...` | Pending |
| `src\network\center\Center.java` | 1591 | Method: dropMessage | `Year of the Rabbit Package SS has been distribu...` | Pending |
| `src\network\center\Center.java` | 1595 | Method: dropMessage | `Year of the Rabbit Package SSS has been distrib...` | Pending |
| `src\network\center\Center.java` | 1599 | Method: dropMessage | `Love You 3000 Package has been distributed. Ple...` | Pending |
| `src\network\center\Center.java` | 1603 | Method: dropMessage | `Power Overwhelming Package has been distributed...` | Pending |
| `src\network\center\Center.java` | 1607 | Method: dropMessage | `M@STERPIECE Package has been distributed. Pleas...` | Pending |
| `src\network\center\Center.java` | 1611 | Method: dropMessage | `Life Turnaround? Package has been distributed. ...` | Pending |
| `src\network\center\Center.java` | 1615 | Method: dropMessage | `Eris's Golden Apple Package has been distribute...` | Pending |
| `src\network\center\Center.java` | 1619 | Method: dropMessage | `Family Month Package C has been distributed. Pl...` | Pending |
| `src\network\center\Center.java` | 1623 | Method: dropMessage | `Family Month Package B has been distributed. Pl...` | Pending |
| `src\network\center\Center.java` | 1627 | Method: dropMessage | `Family Month Package A has been distributed. Pl...` | Pending |
| `src\network\center\Center.java` | 1631 | Method: dropMessage | `Family Month Package S has been distributed. Pl...` | Pending |
| `src\network\center\Center.java` | 1635 | Method: dropMessage | `Family Month Package SS has been distributed. P...` | Pending |
| `src\network\center\Center.java` | 1639 | Method: dropMessage | `Family Month Package SSS has been distributed. ...` | Pending |
| `src\network\center\Center.java` | 1655 | Method: dropMessage | `Beginner Package has been distributed. Please c...` | Pending |
| `src\network\center\Center.java` | 1658 | Method: dropMessage | `Lunar New Year Enhancement Package has been dis...` | Pending |
| `src\network\center\Center.java` | 1662 | Method: dropMessage | `Lunar New Year Master Berry Package has been di...` | Pending |
| `src\network\center\Center.java` | 1666 | Method: dropMessage | `Lunar New Year Great Value Package has been dis...` | Pending |
| `src\network\center\Center.java` | 1670 | Method: dropMessage | `Children's Day Package has been distributed. Pl...` | Pending |
| `src\network\center\Center.java` | 1675 | Method: dropMessage | `Family Month Package C has been distributed. Pl...` | Pending |
| `src\network\center\Center.java` | 1679 | Method: dropMessage | `Family Month Package B has been distributed. Pl...` | Pending |
| `src\network\center\Center.java` | 1683 | Method: dropMessage | `Family Month Package A has been distributed. Pl...` | Pending |
| `src\network\center\Center.java` | 1687 | Method: dropMessage | `Family Month Package S has been distributed. Pl...` | Pending |
| `src\network\center\Center.java` | 1691 | Method: dropMessage | `Family Month Package SS has been distributed. P...` | Pending |
| `src\network\center\Center.java` | 1695 | Method: dropMessage | `Family Month Package SSS has been distributed. ...` | Pending |
| `src\network\center\Center.java` | 1699 | Method: dropMessage | `Sealed Stat Box Package has been distributed. P...` | Pending |
| `src\network\center\Center.java` | 1703 | Method: dropMessage | `Medal Option Enhancement Ticket Package has bee...` | Pending |
| `src\network\center\Center.java` | 1707 | Method: dropMessage | `Chuseok Package I has been distributed. Please ...` | Pending |
| `src\network\center\Center.java` | 1711 | Method: dropMessage | `Chuseok Package II has been distributed. Please...` | Pending |
| `src\network\center\Center.java` | 1715 | Method: dropMessage | `Chuseok Package III has been distributed. Pleas...` | Pending |
| `src\network\center\Center.java` | 1719 | Method: dropMessage | `Chuseok Package IV has been distributed. Please...` | Pending |
| `src\network\center\Center.java` | 1723 | Method: dropMessage | `Advent Amazing Label Package has been distribut...` | Pending |
| `src\network\center\Center.java` | 1727 | Method: dropMessage | `Advent Wisp's Enhancement Package has been dist...` | Pending |
| `src\network\center\Center.java` | 1731 | Method: dropMessage | `Advent Remaster Package has been distributed. P...` | Pending |
| `src\network\center\Center.java` | 1735 | Method: dropMessage | `Merry Package has been distributed. Please clai...` | Pending |
| `src\network\center\Center.java` | 1739 | Method: dropMessage | `Chris Package has been distributed. Please clai...` | Pending |
| `src\network\center\Center.java` | 1743 | Method: dropMessage | `Mas Package has been distributed. Please claim ...` | Pending |
| `src\network\center\Center.java` | 1747 | Method: dropMessage | `Advent Christmas Package has been distributed. ...` | Pending |
| `src\network\center\Center.java` | 1751 | Method: dropMessage | `Hopping to 300? Package has been distributed. P...` | Pending |
| `src\network\center\Center.java` | 1755 | Method: dropMessage | `Even Rabbits Need Strength to Catch! Package ha...` | Pending |
| `src\network\center\Center.java` | 1759 | Method: dropMessage | `ได้รับ What Do Rabbits Wear? Package แล้ว กรุณา...` | Already Thai |
| `src\network\center\Center.java` | 1763 | Method: dropMessage | `Come Out! Cute Rabbit-like Pet! Package has bee...` | Pending |
| `src\network\center\Center.java` | 1767 | Method: dropMessage | `Savior Package has been distributed. Please cla...` | Pending |
| `src\network\center\Center.java` | 1771 | Method: dropMessage | `Year of the Rabbit Package C has been distribut...` | Pending |
| `src\network\center\Center.java` | 1775 | Method: dropMessage | `Year of the Rabbit Package B has been distribut...` | Pending |
| `src\network\center\Center.java` | 1779 | Method: dropMessage | `Year of the Rabbit Package A has been distribut...` | Pending |
| `src\network\center\Center.java` | 1783 | Method: dropMessage | `Year of the Rabbit Package S has been distribut...` | Pending |
| `src\network\center\Center.java` | 1787 | Method: dropMessage | `Year of the Rabbit Package SS has been distribu...` | Pending |
| `src\network\center\Center.java` | 1791 | Method: dropMessage | `Year of the Rabbit Package SSS has been distrib...` | Pending |
| `src\network\center\Center.java` | 1795 | Method: dropMessage | `Love You 3000 Package has been distributed. Ple...` | Pending |
| `src\network\center\Center.java` | 1799 | Method: dropMessage | `Power Overwhelming Package has been distributed...` | Pending |
| `src\network\center\Center.java` | 1803 | Method: dropMessage | `M@STERPIECE Package has been distributed. Pleas...` | Pending |
| `src\network\center\Center.java` | 1807 | Method: dropMessage | `Life Turnaround? Package has been distributed. ...` | Pending |
| `src\network\center\Center.java` | 1811 | Method: dropMessage | `Eris's Golden Apple Package has been distribute...` | Pending |
| `src\network\center\Center.java` | 1815 | Method: dropMessage | `Family Month Package C has been distributed. Pl...` | Pending |
| `src\network\center\Center.java` | 1819 | Method: dropMessage | `Family Month Package B has been distributed. Pl...` | Pending |
| `src\network\center\Center.java` | 1823 | Method: dropMessage | `Family Month Package A has been distributed. Pl...` | Pending |
| `src\network\center\Center.java` | 1827 | Method: dropMessage | `Family Month Package S has been distributed. Pl...` | Pending |
| `src\network\center\Center.java` | 1831 | Method: dropMessage | `Family Month Package SS has been distributed. P...` | Pending |
| `src\network\center\Center.java` | 1835 | Method: dropMessage | `Family Month Package SSS has been distributed. ...` | Pending |
| `src\network\center\Center.java` | 2151 | Method: dropMessage | `ได้รับ ` | Already Thai |
| `src\network\center\Center.java` | 2181 | Method: dropMessage | `ได้รับ ` | Already Thai |
| `src\network\center\Center.java` | 2208 | Method: dropMessage | `ได้รับ ` | Already Thai |
| `src\network\center\Center.java` | 2467 | Method: chatMsg | `[FeverEvent] กิจกรรม Hot Time เริ่มต้นแล้ว` | Already Thai |
| `src\network\center\Center.java` | 2471 | Method: chatMsg | `[FeverEvent] ` | Pending |
| `src\network\center\Center.java` | 2471 | Method: chatMsg | ` ` | Pending |
| `src\network\center\Center.java` | 2495 | Method: chatMsg | `[FeverEvent] กิจกรรม Hot Time จบลงแล้ว` | Already Thai |
| `src\network\center\Center.java` | 2499 | Method: chatMsg | `[FeverEvent] ` | Pending |
| `src\network\center\Center.java` | 2499 | Method: chatMsg | ` ` | Pending |
| `src\network\center\Center.java` | 3228 | Method: dropMessage | `Fairy Bros' Golden Box item distribution is in ...` | Pending |
| `src\network\center\Center.java` | 3244 | Method: dropMessage | `Fairy Bros' Golden Box items have been distribu...` | Pending |
| `src\network\center\Center.java` | 3322 | Method: dropMessage | `Fairy Bros' Golden Box item distribution is in ...` | Pending |
| `src\network\center\Center.java` | 3338 | Method: dropMessage | `Fairy Bros' Golden Box items have been distribu...` | Pending |
| `src\network\center\Center.java` | 3496 | Method: chatMsg | `Combat Power Ranking has been reset, and rankin...` | Pending |
| `src\network\center\Center.java` | 3882 | Method: chatMsg | `[ExpFever] กิจกรรม Exp จบลงแล้ว` | Already Thai |
| `src\network\center\Center.java` | 3894 | Method: chatMsg | `[FeverTime] Spell Trace Fever Time จบลงแล้ว` | Already Thai |
| `src\network\center\Center.java` | 4013 | Method: chatMsg | `[Weekend Daily] กิจกรรม EXP +20% กำลังดำเนินอยู่` | Already Thai |
| `src\network\center\Center.java` | 4023 | Method: chatMsg | `[Monday Daily] กิจกรรม Meso +20% กำลังดำเนินอยู่` | Already Thai |
| `src\network\center\Center.java` | 4033 | Method: chatMsg | `[Tuesday Daily] กิจกรรมเพิ่มอัตราดรอป 20% กำลัง...` | Already Thai |
| `src\network\center\Center.java` | 4044 | Method: chatMsg | `[Wednesday Daily] กิจกรรมเพิ่มอัตราการเกิดมอนสเ...` | Already Thai |
| `src\network\center\Center.java` | 4054 | Method: chatMsg | `[Thursday Daily] กิจกรรมส่วนลด Star Force กำลัง...` | Already Thai |
| `src\network\center\Center.java` | 4064 | Method: chatMsg | `[Friday Daily] กิจกรรมเพิ่มอัตราการลงทะเบียน Mo...` | Already Thai |
| `src\network\center\Center.java` | 4080 | Method: chatMsg | `[FeverTime] Spell Trace Fever Time จะมีจนถึง 22...` | Already Thai |
| `src\network\center\Center.java` | 4084 | Method: chatMsg | `[FeverTime] Spell Trace Fever Time จะมีจนถึง 22...` | Already Thai |
| `src\network\center\Center.java` | 4096 | Method: chatMsg | `[FeverTime] Spell Trace Fever Time กำลังจะจบลง` | Already Thai |
| `src\network\center\Center.java` | 4099 | Method: chatMsg | `[FeverTime] Spell Trace Fever Time กำลังจะจบลง` | Already Thai |
| `src\network\center\Center.java` | 4197 | Method: chatMsg | `[Event] Exp ` | Pending |
| `src\network\center\Center.java` | 4197 | Method: chatMsg | `x event will run until ` | Pending |
| `src\network\center\Center.java` | 4197 | Method: chatMsg | `:` | Pending |
| `src\network\center\Center.java` | 4197 | Method: chatMsg | `.` | Pending |
| `src\network\center\Center.java` | 4202 | Method: chatMsg | `[Event] กิจกรรม Exp จบลงแล้ว` | Already Thai |
| `src\network\center\Center.java` | 5179 | Method: dropMessage | `The party recruitment period has expired, so th...` | Pending |
| `src\network\center\Center.java` | 5256 | Method: dropMessage | `The party is full, so you cannot apply.` | Pending |
| `src\network\center\Center.java` | 5292 | Method: dropMessage | `The party does not exist.` | Pending |
| `src\network\center\Center.java` | 6410 | Method: dropMessage | `The guild mark has been changed. The changed gu...` | Pending |
| `src\network\center\praise\PraiseDonationMesoRank.java` | 165 | Method: dropMessage | `[알림] 칭찬 포인트 랭킹 ` | Pending |
| `src\network\center\praise\PraiseDonationMesoRank.java` | 165 | Method: dropMessage | `กรุณารับรางวัลด้านบน` | Already Thai |
| `src\network\center\praise\PraiseDonationMesoRank.java` | 213 | Method: dropMessage | `[Notice] ชนะกิจกรรม Praise Point Settlement รับ...` | Already Thai |
| `src\network\center\praise\PraisePointRank.java` | 143 | Method: dropMessage | `ชื่นชมคุณ ได้รับ 500 Praise Point` | Already Thai |
| `src\network\center\praise\PraisePointRank.java` | 236 | Method: dropMessage | `[알림] 칭찬 포인트 랭킹 ` | Pending |
| `src\network\center\praise\PraisePointRank.java` | 236 | Method: dropMessage | `กรุณารับรางวัลด้านบน` | Already Thai |
| `src\network\discordbot\DiscordBotHandler.java` | 280 | Method: sendMessage | `CCU` | Pending |
| `src\network\discordbot\DiscordBotHandler.java` | 295 | Method: sendMessage | `Donation` | Pending |
| `src\network\discordbot\processor\DiscordBotProcessor.java` | 16 | Method: chatMsg | `[디스코드] ` | Pending |
| `src\network\discordbot\processor\DiscordBotProcessor.java` | 16 | Method: chatMsg | ` : ` | Pending |
| `src\network\game\processors\AllianceHandler.java` | 87 | Method: dropMessage | `กิลด์นี้อยู่ในพันธมิตรเดียวกันอยู่แล้ว` | Already Thai |
| `src\network\game\processors\AllianceHandler.java` | 89 | Method: dropMessage | `กิลด์นี้อยู่ในพันธมิตรอื่น` | Already Thai |
| `src\network\game\processors\AllianceHandler.java` | 103 | Method: dropMessage | `กรุณาตรวจสอบว่าหัวหน้ากิลด์อยู่ในแชนแนลเดียวกัน...` | Already Thai |
| `src\network\game\processors\AllianceHandler.java` | 106 | Method: dropMessage | `ไม่พบกิลด์ กรุณาใส่ชื่อกิลด์ที่ถูกต้อง` | Already Thai |
| `src\network\game\processors\AllianceHandler.java` | 113 | Method: dropMessage | `An error occured when adding guild.` | Pending |
| `src\network\game\processors\AllianceHandler.java` | 127 | Method: dropMessage | `An error occured when changing leader.` | Pending |
| `src\network\game\processors\AllianceHandler.java` | 141 | Method: dropMessage | `An error occured when changing rank.` | Pending |
| `src\network\game\processors\BuddyListHandler.java` | 424 | Method: dropMessage | `เปลี่ยนสถานะเป็นออฟไลน์เรียบร้อยแล้ว` | Already Thai |
| `src\network\game\processors\ChatHandler.java` | 34 | Method: dropMessage | `ขณะนี้ไม่สามารถใช้งานแชทได้ กรุณาลองใหม่ในภายหลัง` | Already Thai |
| `src\network\game\processors\ChatHandler.java` | 45 | Method: dropMessage | `ได้รับ Union Coin 30 เหรียญ` | Already Thai |
| `src\network\game\processors\ChatHandler.java` | 56 | Method: dropMessage | `ได้รับสิทธิ์ GM` | Already Thai |
| `src\network\game\processors\ChatHandler.java` | 71 | Method: dropMessage | `ขณะนี้ไม่สามารถใช้งานแชทได้ กรุณาลองใหม่ในภายหลัง` | Already Thai |
| `src\network\game\processors\ChatHandler.java` | 160 | Method: dropMessage | `คุณสามารถใช้โข่งได้ทุกๆ ` | Already Thai |
| `src\network\game\processors\ChatHandler.java` | 160 | Method: dropMessage | ` วินาที` | Already Thai |
| `src\network\game\processors\ChatHandler.java` | 162 | Method: dropMessage | `` | Pending |
| `src\network\game\processors\ChatHandler.java` | 351 | Method: dropMessage | `ห้องแชทเต็มแล้ว` | Already Thai |
| `src\network\game\processors\EnchantHandler.java` | 300 | Method: dropMessage | `โอกาส Star Force ถูกแก้ไขโดยคำสั่ง : สำเร็จ=` | Already Thai |
| `src\network\game\processors\EnchantHandler.java` | 300 | Method: dropMessage | `, ทำลาย=` | Already Thai |
| `src\network\game\processors\EnchantHandler.java` | 301 | Method: dropMessage | `, ลดระดับ=` | Already Thai |
| `src\network\game\processors\EnchantHandler.java` | 499 | Method: dropMessage | `กรุณาลองใหม่อีกครั้งหลังจากทำช่องว่างในช่องเก็บ...` | Already Thai |
| `src\network\game\processors\ErdaSpectrumHandler.java` | 74 | Method: dropMessage | `Erda ไม่เพียงพอสำหรับการใช้ Erda Collector` | Already Thai |
| `src\network\game\processors\GuildHandler.java` | 78 | Method: dropMessage | ` หัวหน้ากิลด์ปฏิเสธคำเชิญเข้าร่วมพันธมิตร` | Already Thai |
| `src\network\game\processors\GuildHandler.java` | 209 | Method: dropMessage | `กิลด์ที่ท่านต้องการเข้าร่วมมีสมาชิกเต็มแล้ว` | Already Thai |
| `src\network\game\processors\GuildHandler.java` | 288 | Method: dropMessage | `มี Meso ไม่เพียงพอสำหรับการสร้างกิลด์ [10,000,0...` | Already Thai |
| `src\network\game\processors\GuildHandler.java` | 294 | Method: dropMessage | `ชื่อกิลด์นี้ไม่สามารถใช้งานได้` | Already Thai |
| `src\network\game\processors\GuildHandler.java` | 314 | Method: dropMessage | `ไม่สามารถสร้างกิลด์ได้เนื่องจากคุณมีกิลด์อยู่แล้ว` | Already Thai |
| `src\network\game\processors\GuildHandler.java` | 319 | Method: dropMessage | `ไม่สามารถสร้างกิลด์ด้วยชื่อนี้ได้` | Already Thai |
| `src\network\game\processors\GuildHandler.java` | 389 | Method: dropMessage | `ไม่พบกิลด์ กรุณาตรวจสอบชื่อกิลด์ให้ถูกต้อง` | Already Thai |
| `src\network\game\processors\GuildHandler.java` | 405 | Method: dropMessage | `ไม่พบกิลด์ กรุณาตรวจสอบชื่อกิลด์ให้ถูกต้อง` | Already Thai |
| `src\network\game\processors\GuildHandler.java` | 410 | Method: dropMessage | `ระบบ Blacklist กิลด์ยังไม่เปิดให้บริการ` | Already Thai |
| `src\network\game\processors\GuildHandler.java` | 445 | Method: dropMessage | `ไม่สามารถเชิญกิลด์เข้าร่วมพันธมิตรเพิ่มได้ พันธ...` | Already Thai |
| `src\network\game\processors\GuildHandler.java` | 453 | Method: dropMessage | `กิลด์ดังกล่าวอยู่ระหว่างการดำเนินการคำเชิญจากพั...` | Already Thai |
| `src\network\game\processors\GuildHandler.java` | 465 | Method: dropMessage | `หัวหน้ากิลด์ดังกล่าวออฟไลน์อยู่` | Already Thai |
| `src\network\game\processors\GuildHandler.java` | 479 | Method: dropMessage | `หัวหน้ากิลด์ดังกล่าวออฟไลน์อยู่` | Already Thai |
| `src\network\game\processors\GuildHandler.java` | 684 | Method: dropMessage | `กิลด์ที่ท่านต้องการเข้าร่วมมีสมาชิกเต็มแล้ว` | Already Thai |
| `src\network\game\processors\GuildHandler.java` | 803 | Method: dropMessage | `GP ไม่เพียงพอสำหรับการสร้างตรากิลด์` | Already Thai |
| `src\network\game\processors\GuildHandler.java` | 817 | Method: dropMessage | `GP ไม่เพียงพอสำหรับการสร้างตรากิลด์` | Already Thai |
| `src\network\game\processors\GuildHandler.java` | 826 | Method: dropMessage | `การสร้างตรากิลด์สามารถทำได้ที่ Hall of Heroes เ...` | Already Thai |
| `src\network\game\processors\GuildHandler.java` | 930 | Method: dropMessage | `เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุขณะเปลี่ยนระดับพั...` | Already Thai |
| `src\network\game\processors\GuildHandler.java` | 982 | Method: dropMessage | `เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุ` | Already Thai |
| `src\network\game\processors\GuildHandler.java` | 992 | Method: dropMessage | `เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุ` | Already Thai |
| `src\network\game\processors\GuildHandler.java` | 1017 | Method: dropMessage | `ไม่พบผู้ใช้ในแชนแนลปัจจุบัน` | Already Thai |
| `src\network\game\processors\GuildHandler.java` | 1024 | Method: dropMessage | `เป้าหมายอยู่ในตำแหน่งที่ไม่สามารถใช้สกิลนี้ได้` | Already Thai |
| `src\network\game\processors\GuildHandler.java` | 1030 | Method: dropMessage | `เลเวลไม่เพียงพอที่จะเคลื่อนย้าย` | Already Thai |
| `src\network\game\processors\GuildHandler.java` | 1039 | Method: dropMessage | `ไม่สามารถใช้สกิลในตำแหน่งนี้ได้` | Already Thai |
| `src\network\game\processors\GuildHandler.java` | 1045 | Method: dropMessage | `สมาชิกกิลด์ที่เลือกมีเลเวลต่ำเกินไปที่จะถูกเคลื...` | Already Thai |
| `src\network\game\processors\HyperHandler.java` | 25 | Method: dropMessage | `ข้อมูลสกิลไม่ถูกต้อง` | Already Thai |
| `src\network\game\processors\HyperHandler.java` | 27 | Method: dropMessage | `เลเวลยังไม่ถึงกำหนดในการตั้งค่า Hyper Stat` | Already Thai |
| `src\network\game\processors\HyperHandler.java` | 31 | Method: dropMessage | `สเตตัสนี้ถึงเลเวลสูงสุดแล้ว` | Already Thai |
| `src\network\game\processors\HyperHandler.java` | 34 | Method: dropMessage | `Hyper Stat Points ไม่เพียงพอสำหรับขั้นถัดไป` | Already Thai |
| `src\network\game\processors\HyperHandler.java` | 42 | Method: dropMessage | `เกิดข้อผิดพลาดในการโหลดข้อมูล Hyper Stat` | Already Thai |
| `src\network\game\processors\HyperHandler.java` | 53 | Method: dropMessage | `Meso ไม่เพียงพอ` | Already Thai |
| `src\network\game\processors\HyperHandler.java` | 60 | Method: dropMessage | `เกิดข้อผิดพลาดในการโหลดข้อมูล Hyper Stat` | Already Thai |
| `src\network\game\processors\HyperHandler.java` | 71 | Method: dropMessage | `Meso ไม่เพียงพอสำหรับรีเซ็ต Hyper Stat` | Already Thai |
| `src\network\game\processors\HyperHandler.java` | 73 | Method: dropMessage | `เลเวลยังไม่ถึงกำหนดในการตั้งค่า Hyper Stat` | Already Thai |
| `src\network\game\processors\HyperHandler.java` | 78 | Method: dropMessage | `รีเซ็ต Hyper Stat เรียบร้อยแล้ว` | Already Thai |
| `src\network\game\processors\HyperHandler.java` | 82 | Method: dropMessage | `เกิดข้อผิดพลาดในการโหลดข้อมูล Hyper Stat` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 97 | Method: dropMessage | `ไม่สามารถใช้ได้ในแผนที่นี้` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 114 | Method: dropMessage | `The server is busy at the moment. Please try ag...` | Pending |
| `src\network\game\processors\InterServerHandler.java` | 168 | Method: dropMessage | `ตอนนี้ไม่สามารถเข้า Auction House ได้ กรุณาลองใ...` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 179 | Method: dropMessage | `The server is busy at the moment. Please try ag...` | Pending |
| `src\network\game\processors\InterServerHandler.java` | 786 | Method: dropMessage | `AP ถูกรีเซ็ตเนื่องจากข้อผิดพลาดในการกระจายค่าสถ...` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 846 | Method: dropMessage | `[แจ้งเตือน] กรุณารับรางวัลอันดับ Mulung Dojo` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 851 | Method: dropMessage | `[แจ้งเตือน] กรุณารับรางวัลอันดับ Mulung Dojo (C...` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 857 | Method: dropMessage | `[แจ้งเตือน] คุณชนะกิจกรรมคะแนนคำชม กรุณารับ Mes...` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 930 | Method: dropMessage | `ได้รับแพ็คเกจผู้เริ่มต้นแล้ว กรุณารับได้ที่ระบบ...` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 937 | Method: chatMsg | `[Fever Event] กิจกรรม EXP ` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 938 | Method: chatMsg | ` เท่า กำลังดำเนินการ` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 942 | Method: chatMsg | `[Fever Event] กิจกรรม Drop ` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 943 | Method: chatMsg | ` เท่า กำลังดำเนินการ` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 947 | Method: chatMsg | `[Fever Event] กิจกรรม Meso ` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 948 | Method: chatMsg | ` เท่า กำลังดำเนินการ` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 1002 | Method: chatMsg | `[Weekend Daily] กิจกรรมได้รับ EXP เพิ่มเติม 20%...` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 1012 | Method: chatMsg | `[Monday Daily] กิจกรรมได้รับ Meso เพิ่มเติม 20%...` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 1021 | Method: chatMsg | `[Tuesday Daily] กิจกรรมเพิ่มอัตราการดรอป 20% กำ...` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 1031 | Method: chatMsg | `[Wednesday Daily] กิจกรรมเพิ่มการเกิดของมอนสเตอ...` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 1040 | Method: chatMsg | `[Thursday Daily] กิจกรรมส่วนลดค่าใช้จ่าย Star F...` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 1050 | Method: chatMsg | `[Friday Daily] กิจกรรมเพิ่มโอกาสลงทะเบียน Monst...` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 1082 | Method: dropMessage | `กิจกรรมดอกไม้บานที่ลานกว้าง [EXP 1.5x][Arcane S...` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 1083 | Method: dropMessage | `!` | Pending |
| `src\network\game\processors\InterServerHandler.java` | 1086 | Method: dropMessage | `กิจกรรมดอกไม้บานที่ลานกว้าง [EXP 1.5x][Drop 1.5...` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 1086 | Method: dropMessage | `!` | Pending |
| `src\network\game\processors\InterServerHandler.java` | 1089 | Method: dropMessage | `กิจกรรมดอกไม้บานที่ลานกว้าง [EXP 1.5x][Meso 1.5...` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 1089 | Method: dropMessage | `!` | Pending |
| `src\network\game\processors\InterServerHandler.java` | 1092 | Method: dropMessage | `กิจกรรมดอกไม้บานที่ลานกว้าง [Arcane Symbol Drop...` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 1093 | Method: dropMessage | `!` | Pending |
| `src\network\game\processors\InterServerHandler.java` | 1096 | Method: dropMessage | `กิจกรรมดอกไม้บานที่ลานกว้าง [Drop 1.5x][Meso 1....` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 1096 | Method: dropMessage | `!` | Pending |
| `src\network\game\processors\InterServerHandler.java` | 1189 | Method: dropMessage | `[แจ้งเตือน] โล่ถูกถอดออกเนื่องจากมีการสวมใส่อาว...` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 1194 | Method: dropMessage | `[แจ้งเตือน] กางเกงถูกถอดออกเนื่องจากมีการสวมใส่...` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 1528 | Method: dropMessage | `[แจ้งเตือน] พบออปชั่น Ability ซ้ำกัน ออปชั่นอื่...` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 1566 | Method: dropMessage | `[ประกาศ] เนื่องจากมีการแก้ไขบั๊ก Ability ออปชั่...` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 1593 | Method: dropMessage | `ได้รับเหรียญ ` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 1609 | Method: dropMessage | `[LetterBox] จดหมายมาถึงแล้ว กรุณารับที่ NPC กิจ...` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 1612 | Method: dropMessage | `[Mailbox] ไอเทมมาถึงตู้จดหมายแล้ว กรุณารับได้ที...` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 1636 | Method: dropMessage | `The server is busy at the moment. Please try ag...` | Pending |
| `src\network\game\processors\InterServerHandler.java` | 1643 | Method: dropMessage | `สามารถเปลี่ยนแชนแนลได้ทุกๆ 5 วินาที` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 1645 | Method: dropMessage | `ตอนนี้ไม่สามารถเปลี่ยนแชนแนลได้ กรุณาลองใหม่อีก...` | Already Thai |
| `src\network\game\processors\InterServerHandler.java` | 1660 | Method: dropMessage | `The channel is full at the moment.` | Pending |
| `src\network\game\processors\InterServerHandler.java` | 1666 | Method: dropMessage | `The channel is full at the moment.` | Pending |
| `src\network\game\processors\MiniGameActionHandler.java` | 117 | Method: sendBigScriptProgressMessage | `ฝ่ายตรงข้ามไม่สามารถวางหมากได้ ตาเดินจึงตกเป็นข...` | Already Thai |
| `src\network\game\processors\MiniGameActionHandler.java` | 121 | Method: sendBigScriptProgressMessage | `คุณไม่สามารถวางหมากได้ ตาเดินของคุณจึงสิ้นสุดลง` | Already Thai |
| `src\network\game\processors\MobHandler.java` | 106 | Method: dropMessage | `mobID : ` | Pending |
| `src\network\game\processors\NPCHandler.java` | 156 | Method: dropMessage | `NPC ID : ` | Pending |
| `src\network\game\processors\NPCHandler.java` | 209 | Method: dropMessage | `ช่องเก็บอุปกรณ์ไม่เพียงพอ` | Already Thai |
| `src\network\game\processors\NPCHandler.java` | 277 | Method: dropMessage | `ได้รับไอเทมเรียบร้อยแล้ว` | Already Thai |
| `src\network\game\processors\NPCHandler.java` | 284 | Method: dropMessage | `ช่อง Equip ไม่เพียงพอ` | Already Thai |
| `src\network\game\processors\NPCHandler.java` | 352 | Method: dropMessage | `มอบไอเทมเรียบร้อยแล้ว` | Already Thai |
| `src\network\game\processors\NPCHandler.java` | 359 | Method: dropMessage | `ช่อง Equip ไม่เพียงพอ` | Already Thai |
| `src\network\game\processors\NPCHandler.java` | 427 | Method: dropMessage | `มอบไอเทมเรียบร้อยแล้ว` | Already Thai |
| `src\network\game\processors\NPCHandler.java` | 434 | Method: dropMessage | `ช่อง Equip ไม่เพียงพอ` | Already Thai |
| `src\network\game\processors\NPCHandler.java` | 502 | Method: dropMessage | `มอบไอเทมเรียบร้อยแล้ว` | Already Thai |
| `src\network\game\processors\NPCHandler.java` | 509 | Method: dropMessage | `ช่อง Equip ไม่เพียงพอ` | Already Thai |
| `src\network\game\processors\NPCHandler.java` | 577 | Method: dropMessage | `มอบไอเทมเรียบร้อยแล้ว` | Already Thai |
| `src\network\game\processors\NPCHandler.java` | 584 | Method: dropMessage | `ช่อง Equip ไม่เพียงพอ` | Already Thai |
| `src\network\game\processors\NPCHandler.java` | 652 | Method: dropMessage | `มอบไอเทมเรียบร้อยแล้ว` | Already Thai |
| `src\network\game\processors\NPCHandler.java` | 692 | Method: dropMessage | `เควสนี้ไม่สามารถยกเลิกได้` | Already Thai |
| `src\network\game\processors\NPCHandler.java` | 711 | Method: dropMessage | `QuestID ที่สำเร็จ : ` | Already Thai |
| `src\network\game\processors\NPCHandler.java` | 711 | Method: dropMessage | ` 엔피시 : ` | Pending |
| `src\network\game\processors\NPCHandler.java` | 711 | Method: dropMessage | ` 스크립트 : ` | Pending |
| `src\network\game\processors\NPCHandler.java` | 780 | Method: dropMessage | `ช่องเก็บของเต็ม` | Already Thai |
| `src\network\game\processors\NPCHandler.java` | 794 | Method: dropMessage | `ช่องเก็บของเต็ม` | Already Thai |
| `src\network\game\processors\NPCHandler.java` | 866 | Method: dropMessage | `ไอเทมนี้ฝากคลังไม่ได้` | Already Thai |
| `src\network\game\processors\NPCHandler.java` | 881 | Method: dropMessage | `Meso ไม่เพียงพอสำหรับฝากไอ템ในคลัง` | Already Thai |
| `src\network\game\processors\PartyHandler.java` | 54 | Method: dropMessage | `'` | Pending |
| `src\network\game\processors\PartyHandler.java` | 59 | Method: dropMessage | `ปาร์ตี้ที่ต้องการเข้าร่วมไม่มีอยู่จริง` | Already Thai |
| `src\network\game\processors\PartyHandler.java` | 62 | Method: dropMessage | `มีปาร์ตี้อยู่แล้ว ไม่สามารถร่วใปาร์ตี้ได้` | Already Thai |
| `src\network\game\processors\PartyHandler.java` | 108 | Method: dropMessage | `คุณมีปาร์ตี้อยู่แล้ว ไม่สามารถสร้างปาร์ตี้ใหม่ได้` | Already Thai |
| `src\network\game\processors\PartyHandler.java` | 114 | Method: dropMessage | `ไม่สามารถออกจากปาร์ตี้ในแผนที่นี้ได้` | Already Thai |
| `src\network\game\processors\PartyHandler.java` | 129 | Method: dropMessage | `ไม่สามารถออกจากปาร์ตี้ขณะที่สมาชิกกำลังต่อสู้กั...` | Already Thai |
| `src\network\game\processors\PartyHandler.java` | 180 | Method: dropMessage | `ปาร์ตี้มีสมาชิกเต็มแล้ว` | Already Thai |
| `src\network\game\processors\PartyHandler.java` | 183 | Method: dropMessage | `ไม่พบปาร์ตี้ที่ต้องการเข้าร่วม` | Already Thai |
| `src\network\game\processors\PartyHandler.java` | 186 | Method: dropMessage | `คุณมีปาร์ตี้อยู่แล้ว ไม่สามารถเข้าร่วมปาร์ตี้อื...` | Already Thai |
| `src\network\game\processors\PartyHandler.java` | 207 | Method: dropMessage | `เชิญ ` | Already Thai |
| `src\network\game\processors\PartyHandler.java` | 215 | Method: dropMessage | `ปาร์ตี้มีสมาชิกเต็มแล้ว` | Already Thai |
| `src\network\game\processors\PartyHandler.java` | 218 | Method: dropMessage | `ผู้เล่นเป้าหมายมีปาร์ตี้อยู่แล้ว` | Already Thai |
| `src\network\game\processors\PartyHandler.java` | 221 | Method: dropMessage | `ไม่พบผู้เล่นเป้าหมาย` | Already Thai |
| `src\network\game\processors\PartyHandler.java` | 229 | Method: dropMessage | `เชิญ ` | Already Thai |
| `src\network\game\processors\PartyHandler.java` | 237 | Method: dropMessage | `ปาร์ตี้มีสมาชิกเต็มแล้ว` | Already Thai |
| `src\network\game\processors\PartyHandler.java` | 240 | Method: dropMessage | `ผู้เล่นเป้าหมายมีปาร์ตี้อยู่แล้ว` | Already Thai |
| `src\network\game\processors\PartyHandler.java` | 243 | Method: dropMessage | `ไม่พบผู้เล่นเป้าหมาย` | Already Thai |
| `src\network\game\processors\PartyHandler.java` | 250 | Method: dropMessage | `ไม่สามารถไล่สมาชิกออกจากปาร์ตี้ในแผนที่นี้ได้` | Already Thai |
| `src\network\game\processors\PartyHandler.java` | 282 | Method: dropMessage | `กรุณาใส่ชื่อปาร์ตี้อย่างน้อย 1 ตัวอักษร` | Already Thai |
| `src\network\game\processors\PartyHandler.java` | 304 | Method: dropMessage | `คุณมีปาร์ตี้อยู่แล้ว ไม่สามารถสร้างใหม่ได้` | Already Thai |
| `src\network\game\processors\PartyHandler.java` | 320 | Method: chatMsg | `[보스파티모집] ` | Pending |
| `src\network\game\processors\PartyHandler.java` | 346 | Method: chatMsg | `[보스파티모집] ` | Pending |
| `src\network\game\processors\PartyHandler.java` | 435 | Method: dropMessage | `ไม่สามารถรับสมาชิกเพิ่มได้เนื่องจากปาร์ตี้เต็ม` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 461 | Method: dropMessage | `สามารถโหวตได้เพียงครั้งเดียวขณะนั่ง` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 482 | Method: dropMessage | `แผนที่นี้ไม่สามารถเข้าได้จากรายการ` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 492 | Method: dropMessage | `แผนที่นี้ไม่สามารถเข้าได้จากรายการ` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 502 | Method: dropMessage | `แผนที่นี้ไม่สามารถเข้าได้จากรายการ` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 1898 | Method: chatMsg | `ใช้สกิล : ` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 1900 | Method: chatMsg | ` (` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 2673 | Method: dropMessage | `Map is NULL. Use !warp <mapid> instead.` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 4096 | Method: dropMessage | `ไม่สามารถอัพเกรดได้อีกต่อไป` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 4262 | Method: dropMessage | `ไม่สามารถอัพเกรดได้อีกต่อไป` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 4447 | Method: dropMessage | `ไม่สามารถรวมสัญลักษณ์ที่อัพเกรดแล้วได้` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 5924 | Method: dropMessage | `Damage Skin นี้ถูกบันทึกไว้แล้ว` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 5932 | Method: dropMessage | `บันทึก Damage Skin เรียบร้อยแล้ว` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 5945 | Method: dropMessage | `ลบ Damage Skin เรียบร้อยแล้ว` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 5963 | Method: dropMessage | `เปลี่ยน Damage Skin เรียบร้อยแล้ว` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 6075 | Method: sendDemianNotice | `ตราประทับถูกยกเลิก สามารถใช้ยาได้ 5 วินาที` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 6352 | Method: dropMessage | `StackRequest Skill : ` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 6352 | Method: dropMessage | ` Time : ` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 6513 | Method: dropMessage | `remove area x: ` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 6768 | Method: dropMessage | `Job Advancement Coin ไม่เพียงพอ ` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 6768 | Method: dropMessage | `ชิ้นที่ต้องการ` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 6774 | Method: dropMessage | `กรุณาถอด V Skill Core ทั้งหมดออกแล้วลองใหม่อีกค...` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 6857 | Method: dropMessage | `เปลี่ยนอาชีพอิสระเรียบร้อยแล้ว` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 6861 | Method: dropMessage | `เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุ` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 6904 | Method: dropMessage | `หมดเวลาการรับรางวัล ไม่สามารถรับได้` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 7240 | Method: dropMessage | `Type อื่น : ` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 7247 | Method: dropMessage | `เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุ` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 7258 | Method: dropMessage | `เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุ` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 7423 | Method: dropMessage | `ไม่พบผู้เล่นดังกล่าว` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 7427 | Method: dropMessage | `(이)에게 거짓말 탐지기 테스트를 요청하였습니다.` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 7500 | Method: dropMessage | `SkillID : ` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 8761 | Method: dropMessage | `ช่องไม่เพียงพอ ไม่สามารถเก็บ Mannequin ได้` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 9253 | Method: dropMessage | `ไม่สามารถเรียกได้เกิน 10 ตัว` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 9416 | Method: dropMessage | `กรุณาทำช่องว่างในช่อง Use และ Etc อย่างละ 1 ช่อง` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 9673 | Method: dropMessage | `การกระทำที่ไม่รู้จัก ` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 9673 | Method: dropMessage | ` / ` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 9673 | Method: dropMessage | ` / ` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 9685 | Method: dropMessage | `Meso ไม่เพียงพอ` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 9704 | Method: dropMessage | `กล่องข้อความขาออกเต็มแล้ว` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 9892 | Method: dropMessage | `สกิลนี้ไม่สามารถเปิดใช้งานได้ในอาชีพนี้` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 9897 | Method: dropMessage | `สกิลนี้ไม่สามารถเปิดใช้งานได้ในอาชีพนี้` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 9902 | Method: dropMessage | `สกิลนี้ไม่สามารถเปิดใช้งานได้ในอาชีพนี้` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 9907 | Method: dropMessage | `สกิลนี้ไม่สามารถเปิดใช้งานได้ในอาชีพนี้` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 9962 | Method: dropMessage | `Sol Erda Fragment ไม่เพียงพอ` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 10029 | Method: dropMessage | `เกิดข้อผิดพลาด` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 10048 | Method: dropMessage | `เกิดข้อผิดพลาด` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 10072 | Method: dropMessage | `Sol Erda Fragment ไม่เพียงพอ` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 10112 | Method: dropMessage | `Sol Erda Fragment ไม่เพียงพอ` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 10161 | Method: dropMessage | `Sol Erda Fragment ไม่เพียงพอ` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 10256 | Method: dropMessage | `Meso ไม่เพียงพอ` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 10273 | Method: dropMessage | `Meso ไม่เพียงพอ` | Already Thai |
| `src\network\game\processors\PlayerHandler.java` | 10444 | Method: dropMessage | `ExtraSkillRequest : ` | Pending |
| `src\network\game\processors\PlayerInteractionHandler.java` | 63 | Method: dropMessage | `ไม่สามารถตั้งร้านค้าที่นี่ได้` | Already Thai |
| `src\network\game\processors\PlayerInteractionHandler.java` | 71 | Method: dropMessage | `ไม่สามารถเปิดมินิเกมที่นี่ได้` | Already Thai |
| `src\network\game\processors\PlayerInteractionHandler.java` | 161 | Method: dropMessage | `ห้องปิดไปแล้ว` | Already Thai |
| `src\network\game\processors\PlayerInteractionHandler.java` | 180 | Method: dropMessage | `มีผู้เข้าชมร้านค้านี้เต็มจำนวนแล้ว กรุณาลองใหม่...` | Already Thai |
| `src\network\game\processors\PlayerInteractionHandler.java` | 182 | Method: dropMessage | `คุณติด Blacklist ไม่สามารถใช้ร้านค้านี้ได้` | Already Thai |
| `src\network\game\processors\PlayerInteractionHandler.java` | 189 | Method: dropMessage | `ขณะนี้ Hired Merchant กำลังเตรียมการ กรุณามาใหม...` | Already Thai |
| `src\network\game\processors\PlayerInteractionHandler.java` | 193 | Method: dropMessage | `ถูกไล่ออกจากร้านค้า` | Already Thai |
| `src\network\game\processors\PlayerInteractionHandler.java` | 204 | Method: dropMessage | `รหัสผ่านไม่ถูกต้อง กรุณาตรวจสอบและลองใหม่อีกครั้ง` | Already Thai |
| `src\network\game\processors\PlayerInteractionHandler.java` | 208 | Method: dropMessage | `รหัสผ่านไม่ถูกต้อง กรุณาตรวจสอบและลองใหม่อีกครั้ง` | Already Thai |
| `src\network\game\processors\PlayerInteractionHandler.java` | 304 | Method: dropMessage | `ไม่สามารถตั้งร้านค้าได้เนื่องจากเซิร์ฟเวอร์กำลั...` | Already Thai |
| `src\network\game\processors\PlayerInteractionHandler.java` | 329 | Method: dropMessage | `ไอเทมที่มี Decorative Option ไม่สามารถลงทะเบียนได้` | Already Thai |
| `src\network\game\processors\PlayerInteractionHandler.java` | 381 | Method: dropMessage | `ไอเทมที่มี Decorative Option ไม่สามารถลงทะเบียนได้` | Already Thai |
| `src\network\game\processors\PlayerInteractionHandler.java` | 393 | Method: dropMessage | `ต้องมีไอเทมอย่างน้อย 1 ชิ้นจึงจะขายได้` | Already Thai |
| `src\network\game\processors\PlayerInteractionHandler.java` | 420 | Method: dropMessage | `The lowest you can sell this for is ` | Pending |
| `src\network\game\processors\PlayerInteractionHandler.java` | 510 | Method: dropMessage | `รหัสผ่านชั้นที่ 2 ไม่ถูกต้อง \r\nกรุณาตรวจสอบแล...` | Already Thai |
| `src\network\game\processors\PlayerInteractionHandler.java` | 517 | Method: dropMessage | `รหัสผ่านชั้นที่ 2 ไม่ถูกต้อง \r\nกรุณาตรวจสอบแล...` | Already Thai |
| `src\network\game\processors\PlayerInteractionHandler.java` | 541 | Method: dropMessage | `รหัสผ่านชั้นที่ 2 ไม่ถูกต้อง \r\nกรุณาตรวจสอบแล...` | Already Thai |
| `src\network\game\processors\PlayerInteractionHandler.java` | 634 | Method: dropMessage | `กรุณารับไอเทมจาก Frederick` | Already Thai |
| `src\network\game\processors\PlayerInteractionHandler.java` | 824 | Method: dropMessage | `น่าเสียดาย แพ้เป่ายิ้งฉุบแล้ว!` | Already Thai |
| `src\network\game\processors\PlayerInteractionHandler.java` | 827 | Method: dropMessage | `ยินดีด้วย! ชนะเป่ายิ้งฉุบ!` | Already Thai |
| `src\network\game\processors\PlayerInteractionHandler.java` | 925 | Method: sendBigScriptProgressMessage | `หมดเวลา 5 ครั้ง แพ้เกม` | Already Thai |
| `src\network\game\processors\PlayerInteractionHandler.java` | 933 | Method: sendBigScriptProgressMessage | `หมดเวลา เทิร์นผ่าน คำเตือน ` | Already Thai |
| `src\network\game\processors\PlayerInteractionHandler.java` | 933 | Method: sendBigScriptProgressMessage | `ครั้ง หากเกิน 5 ครั้งจะแพ้เกม` | Already Thai |
| `src\network\game\processors\PlayerInteractionHandler.java` | 995 | Method: sendBigScriptProgressMessage | `ฝ่ายตรงข้ามไม่มีที่วางหมาก เทิร์นกลับมาหาคุณ` | Already Thai |
| `src\network\game\processors\PlayerInteractionHandler.java` | 996 | Method: sendBigScriptProgressMessage | `ไม่มีที่ให้วางหมาก จบเทิร์น` | Already Thai |
| `src\network\game\processors\PlayersHandler.java` | 255 | Method: dropMessage | `กรุณายุบปาร์ตี้แล้วลองใหม่อีกครั้ง` | Already Thai |
| `src\network\game\processors\PlayersHandler.java` | 339 | Method: dropMessage | `อยู่ไกลเกินไป` | Already Thai |
| `src\network\game\processors\PlayersHandler.java` | 342 | Method: dropMessage | `ไม่มีไอเทมที่ต้องใช้` | Already Thai |
| `src\network\game\processors\PlayersHandler.java` | 578 | Method: dropMessage | `รายงานได้ทุกๆ 2 ชั่วโมงเท่านั้น` | Already Thai |
| `src\network\game\processors\PlayersHandler.java` | 600 | Method: dropMessage | `ฝ่ายตรงข้ามไม่มีสกิลดังกล่าว` | Already Thai |
| `src\network\game\processors\PlayersHandler.java` | 632 | Method: dropMessage | `ไม่มีสกิลให้ขโมย` | Already Thai |
| `src\network\game\processors\StatsHandling.java` | 201 | Method: dropMessage | `สกิลนี้ถูกบล็อคและไม่สามารถเพิ่มได้` | Already Thai |
| `src\network\game\processors\SummonHandler.java` | 151 | Method: dropMessage | `Error occurred during attack.` | Pending |
| `src\network\game\processors\SummonHandler.java` | 1088 | Method: dropMessage | `Error in processing attack.` | Pending |
| `src\network\game\processors\SummonHandler.java` | 1227 | Method: dropMessage | `Error.` | Pending |
| `src\network\game\processors\SummonHandler.java` | 1230 | Method: dropMessage | `The summon has disappeared.` | Pending |
| `src\network\game\processors\UnionHandler.java` | 46 | Method: dropMessage | `ไม่สามารถโหลดข้อมูล Maple Union ได้ หรือเลเวลรว...` | Already Thai |
| `src\network\game\processors\UnionHandler.java` | 77 | Method: dropMessage | `ไม่สามารถจัดสมาชิกกองกำลังเกินจำนวนสูงสุดได้ กร...` | Already Thai |
| `src\network\game\processors\UnionHandler.java` | 146 | Method: dropMessage | `ไม่สามารถจัดสมาชิกกองกำลังเกินจำนวนสูงสุดได้ กร...` | Already Thai |
| `src\network\game\processors\UserInterfaceHandler.java` | 41 | Method: dropMessage | `เฉพาะหัวหน้าปาร์ตี้เท่านั้นที่สามารถเข้าได้` | Already Thai |
| `src\network\game\processors\VMatrixHandler.java` | 289 | Method: dropMessage | `ไม่มีช่องว่างสำหรับสวมใส่` | Already Thai |
| `src\network\game\processors\VMatrixHandler.java` | 294 | Method: dropMessage | `ไม่สามารถสวมใส่ในช่องที่ยังไม่เปิดใช้งาน` | Already Thai |
| `src\network\game\processors\VMatrixHandler.java` | 346 | Method: dropMessage | `ไม่สามารถสวมใส่ในช่องที่ยังไม่เปิดใช้งาน` | Already Thai |
| `src\network\game\processors\VMatrixHandler.java` | 622 | Method: dropMessage | `ไม่สามารถเก็บ Skill Core ได้เพิ่มอีก กรุณาย่อยห...` | Already Thai |
| `src\network\game\processors\VMatrixHandler.java` | 629 | Method: dropMessage | `V-Core Piece ไม่เพียงพอ!` | Already Thai |
| `src\network\game\processors\VMatrixHandler.java` | 744 | Method: dropMessage | `ช่องนี้เปิดอยู่แล้วหรือเป็นช่องที่ไม่สามารถเปิด...` | Already Thai |
| `src\network\game\processors\VMatrixHandler.java` | 772 | Method: dropMessage | `Meso ไม่เพียงพอสำหรับเปิดช่อง (Character Name : ` | Already Thai |
| `src\network\game\processors\VMatrixHandler.java` | 795 | Method: dropMessage | `Meso ไม่เพียงพอสำหรับการรีเซ็ตการอัพเกรดช่อง (C...` | Already Thai |
| `src\network\game\processors\VMatrixHandler.java` | 833 | Method: dropMessage | `กรุณาตรวจสอบรหัสผ่านชั้นที่ 2` | Already Thai |
| `src\network\game\processors\inventory\CraftHandler.java` | 141 | Method: dropMessage | `ยังไม่สามารถเก็บเกี่ยวได้` | Already Thai |
| `src\network\game\processors\inventory\CraftHandler.java` | 149 | Method: dropMessage | `ยังไม่สามารถเก็บเกี่ยวได้` | Already Thai |
| `src\network\game\processors\inventory\CraftHandler.java` | 325 | Method: dropMessage | ` ความชำนาญเพิ่มขึ้น (+` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 560 | Method: dropMessage | `ไม่สามารถหาข้อมูลรางวัลไอเทมได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 563 | Method: dropMessage | `Insufficient inventory slot.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 575 | Method: dropMessage | `รู้สึกถึงพลังบางอย่าง ทำให้ไม่สามารถใช้ไอเทมได้...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 641 | Method: dropMessage | `มี Meso ไม่เพียงพอ` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 692 | Method: dropMessage | `มีผู้ใช้งานจำนวนมาก ไม่สามารถใช้ไอเทมได้ในขณะนี...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 714 | Method: dropMessage | `มี Meso ไม่เพียงพอ` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 817 | Method: dropMessage | `GLASS NULL!` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1210 | Method: dropMessage | `เกิดข้อผิดพลาด type : ` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1598 | Method: dropMessage | `ไอเทมนี้ไม่สามารถใช้ Scroll ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1603 | Method: dropMessage | `ไอเทมนี้ไม่สามารถใช้ Scroll ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1663 | Method: dropMessage | `ไอเทมนี้ไม่สามารถใช้ Scroll ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1671 | Method: dropMessage | `ไอเทมนี้ไม่สามารถใช้ Scroll ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1683 | Method: dropMessage | `ไอเทมนี้ไม่สามารถใช้ Scroll ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1691 | Method: dropMessage | `ไอเทมนี้ไม่สามารถใช้ Scroll ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1708 | Method: dropMessage | `ไอเทมนี้ไม่สามารถใช้ Scroll ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1712 | Method: dropMessage | `ไอเทมนี้ไม่สามารถใช้ Scroll ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1716 | Method: dropMessage | `ไอเทมนี้ไม่สามารถใช้ Scroll ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1840 | Method: dropMessage | `จำนวนการอัพเกรดไม่ลดลงเนื่องจากผลของไอเทม` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1849 | Method: dropMessage | `Scroll ไม่ถูกหักออกเนื่องจากผลของ Scroll` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1948 | Method: dropMessage | `ไอเทมไม่ถูกทำลายเนื่องจากผลของไอเทมป้องกัน` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2197 | Method: dropMessage | `กรุณาปลดล็อค Potential ก่อน` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2204 | Method: dropMessage | `ไอเทมนี้ไม่สามารถใช้ Scroll ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2210 | Method: dropMessage | `ไอเทมนี้ไม่สามารถใช้ Scroll ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2215 | Method: dropMessage | `ไอเทมนี้ไม่สามารถใช้ Scroll ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2448 | Method: dropMessage | `ขณะนี้มีผู้ใช้งานจำนวนมาก ไม่สามารถใช้ไอเทมได้ ...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2453 | Method: dropMessage | `ไอเทมหมดอายุแล้ว` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2606 | Method: dropMessage | `ค่าความเหนื่อยล้าเต็มอยู่แล้ว` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2617 | Method: dropMessage | `ความเหนื่อยล้าเพิ่มขึ้น ความเหนื่อยล้า : ` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2675 | Method: dropMessage | `All maps are currently in use, please try again...` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2776 | Method: dropMessage | `Please make some space.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2786 | Method: dropMessage | `Please make some space.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2790 | Method: dropMessage | `ต้องมี Fragment 10 ชิ้นสำหรับ Potential Scroll,...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2793 | Method: dropMessage | `Please make some space.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2820 | Method: dropMessage | `ไม่สามารถใช้ไอเทมนี้ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2846 | Method: dropMessage | `ไม่สามารถใช้ไอเทมนี้ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2853 | Method: dropMessage | `Make some space.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2881 | Method: dropMessage | `Please make some space.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2891 | Method: dropMessage | `Please make some space.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2895 | Method: dropMessage | `ต้องมี Purification Totem 50 ชิ้นสำหรับ Noble L...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2898 | Method: dropMessage | `Please make some space.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2911 | Method: dropMessage | `ไม่รู้วิธีใช้มัน` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2932 | Method: dropMessage | `Please make some space.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2935 | Method: dropMessage | `ต้องมี Stone อย่างละ 1 ชิ้นสำหรับ Dream Key` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2938 | Method: dropMessage | `Please make some space.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2957 | Method: dropMessage | `คุณสามารถใช้ Energy Drink ได้ 1 ขวดทุก 10 นาที` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2971 | Method: dropMessage | `คุณสามารถใช้ Energy Drink ได้ 1 ขวดทุก 10 นาที` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3002 | Method: dropMessage | `คุณสามารถใช้ Energy Drink ได้ 1 ขวดทุก 10 นาที` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3318 | Method: dropMessage | `Please make some space.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3328 | Method: dropMessage | `Please make some space.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3333 | Method: dropMessage | `ต้องมี Fragment 20 ชิ้นสำหรับ AEE, 30 ชิ้นสำหรั...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3336 | Method: dropMessage | `Please make some space.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3369 | Method: dropMessage | `Please make some space.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3372 | Method: dropMessage | `ต้องมี Fragment 10 ชิ้นสำหรับ Nebulite Diffuser` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3375 | Method: dropMessage | `Please make some space.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3392 | Method: dropMessage | `Please make some space.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3395 | Method: dropMessage | `ต้องมี Fragment 20 ชิ้นสำหรับ Premium Fusion Ti...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3398 | Method: dropMessage | `Please make some space.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3768 | Method: dropMessage | `เปลี่ยน Damage Skin เรียบร้อยแล้ว` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3792 | Method: dropMessage | `ไม่สามารถใช้ได้เนื่องจากยังไม่ผ่านเควสเปลี่ยนคล...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3798 | Method: dropMessage | `ไม่สามารถถือ Skill Core ได้อีก กรุณาย่อยหรืออัพ...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3857 | Method: dropMessage | `ไม่สามารถใช้ได้เนื่องจากยังไม่ผ่านเควสเปลี่ยนคล...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3863 | Method: dropMessage | `ไม่สามารถถือ Skill Core ได้อีก กรุณาย่อยหรืออัพ...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3876 | Method: dropMessage | `ใช้ Core Gemstone และได้รับ V Core` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3882 | Method: dropMessage | `ไม่สามารถใช้ได้เนื่องจากยังไม่ผ่านเควสเปลี่ยนคล...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3888 | Method: dropMessage | `ไม่สามารถถือ Skill Core ได้อีก กรุณาย่อยหรืออัพ...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3901 | Method: dropMessage | `ใช้ Core Gemstone และได้รับ V Core` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3905 | Method: dropMessage | `ไม่สามารถใช้ได้เนื่องจากยังไม่ผ่านเควสเปลี่ยนคล...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3911 | Method: dropMessage | `ไม่สามารถถือ Skill Core ได้อีก กรุณาย่อยหรืออัพ...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3924 | Method: dropMessage | `ใช้ Core Gemstone และได้รับ V Core` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3927 | Method: dropMessage | `Please bring this item to the NPC.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3973 | Method: dropMessage | `[Riding] มีสกิลขี่สัตว์นี้แล้ว` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3979 | Method: dropMessage | `[라이딩] ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3992 | Method: dropMessage | `Script : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3992 | Method: dropMessage | `, ItemID : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4031 | Method: dropMessage | `มีสกิลขี่สัตว์นี้อยู่แล้ว` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4033 | Method: dropMessage | `ไม่สามารถรับสกิลนี้ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4039 | Method: dropMessage | `[` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4043 | Method: dropMessage | `[` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4108 | Method: dropMessage | `เปลี่ยนหน้าไม่สำเร็จเนื่องจากไม่มีหน้านั้น หากเ...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4123 | Method: dropMessage | `เปลี่ยนหน้าไม่สำเร็จเนื่องจากไม่มีหน้านั้น หากเ...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4138 | Method: dropMessage | `เปลี่ยนหน้าไม่สำเร็จเนื่องจากไม่มีหน้านั้น หากเ...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4159 | Method: dropMessage | `เปลี่ยนหน้าไม่สำเร็จเนื่องจากไม่มีหน้านั้น หากเ...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4253 | Method: dropMessage | `เปลี่ยนทรงผมไม่สำเร็จเนื่องจากไม่มีทรงผมนั้น หา...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4268 | Method: dropMessage | `เปลี่ยนทรงผมไม่สำเร็จเนื่องจากไม่มีทรงผมนั้น หา...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4283 | Method: dropMessage | `เปลี่ยนทรงผมไม่สำเร็จเนื่องจากไม่มีทรงผมนั้น หา...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4303 | Method: dropMessage | `เปลี่ยนทรงผมไม่สำเร็จเนื่องจากไม่มีทรงผมนั้น หา...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4345 | Method: dropMessage | `ขณะนี้มีผู้ใช้งานจำนวนมาก ไม่สามารถใช้ไอเทมได้ใ...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4462 | Method: dropMessage | `ต้องมีแต้มใน HP หรือ MP เพื่อดึงแต้มออก` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4469 | Method: dropMessage | `ต้องมีแต้มใน HP หรือ MP เพื่อดึงแต้มออก` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4708 | Method: dropMessage | `การรีเซ็ตนี้สำหรับ Evan เท่านั้น` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4710 | Method: dropMessage | `การรีเซ็ตนี้สำหรับอาชีพที่ไม่ใช่ Evan เท่านั้น` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4717 | Method: dropMessage | `ไม่สามารถเพิ่มสกิลนี้ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4727 | Method: dropMessage | `ไม่สามารถเพิ่มสกิลต่างอาชีพได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4733 | Method: dropMessage | `จะเกินระดับ Master Level` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4740 | Method: dropMessage | `ไม่สามารถเพิ่ม SP อาชีพนี้โดยใช้การรีเซ็ตนี้ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4762 | Method: dropMessage | `ไม่สามารถลดสกิลนี้ได้ กรุณาใช้ SP Reset ที่ถูกต้อง` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4785 | Method: dropMessage | `ไม่สามารถเพิ่มสกิล Beginner ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4794 | Method: dropMessage | `Stat สูงเกินไป ไม่สามารถใช้ใบรีเซ็ต AP ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4812 | Method: dropMessage | ` 차 스킬 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4812 | Method: dropMessage | ` / ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5019 | Method: dropMessage | `กรุณาทำช่องว่างใน Cash และ Use อย่างน้อย 2 ช่อง` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5140 | Method: dropMessage | `Meso ไม่เพียงพอสำหรับรีเซ็ต Potential` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5190 | Method: dropMessage | `Meso ไม่เพียงพอสำหรับรีเซ็ต Potential` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5261 | Method: dropMessage | `ช่องเก็บของ Use ไม่เพียงพอ ไม่สามารถรีเซ็ต Pote...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5278 | Method: dropMessage | `Meso ไม่เพียงพอสำหรับรีเซ็ต Potential` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5321 | Method: dropMessage | `ช่องเก็บของ Use ไม่เพียงพอ ไม่สามารถตั้งค่า Pot...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5350 | Method: dropMessage | `กรุณาปลดล็อค Potential ก่อน` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5357 | Method: dropMessage | `Meso ไม่เพียงพอสำหรับรีเซ็ต Potential` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5426 | Method: dropMessage | `ช่องเก็บของ Use ไม่เพียงพอ ไม่สามารถรีเซ็ต Pote...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5442 | Method: dropMessage | `ช่องเก็บของ Use ไม่เพียงพอ ไม่สามารถตั้งค่า Pot...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5446 | Method: dropMessage | `Android ที่ยังไม่เปิดใช้งานไม่สามารถรีเซ็ต Pote...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5455 | Method: dropMessage | `Meso ไม่เพียงพอสำหรับรีเซ็ต Potential` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5509 | Method: dropMessage | `ต้องเปิดใช้งาน Potential ก่อนจึงจะใช้ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5851 | Method: dropMessage | `การผสมทำได้เฉพาะกับไอเทมที่สร้างจาก Maple Gangl...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5953 | Method: dropMessage | `ไม่ใช่สัตว์เลี้ยงที่ได้จาก Wisp's Wonder Berry ...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6016 | Method: dropMessage | `เลเวลต่ำกว่า 10 ไม่สามารถใช้งานได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6018 | Method: dropMessage | `Cannot be used here.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6020 | Method: dropMessage | `สามารถใช้ได้เพียงครั้งเดียวใน 10 วินาที` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6033 | Method: dropMessage | `The usage of Megaphone is currently disabled.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6038 | Method: dropMessage | `เลเวลต่ำกว่า 10 ไม่สามารถใช้งานได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6040 | Method: dropMessage | `Cannot be used here.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6042 | Method: dropMessage | `สามารถใช้ได้เพียงครั้งเดียวใน 10 วินาที` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6055 | Method: dropMessage | `The usage of Megaphone is currently disabled.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6060 | Method: dropMessage | `เลเวลต่ำกว่า 10 ไม่สามารถใช้งานได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6062 | Method: dropMessage | `Cannot be used here.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6064 | Method: dropMessage | `สามารถใช้ได้เพียงครั้งเดียวใน 10 วินาที` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6081 | Method: dropMessage | `The usage of Megaphone is currently disabled.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6086 | Method: dropMessage | `เลเวลต่ำกว่า 10 ไม่สามารถใช้งานได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6088 | Method: dropMessage | `Cannot be used here.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6090 | Method: dropMessage | `สามารถใช้ได้เพียงครั้งเดียวใน 10 วินาที` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6110 | Method: dropMessage | `The usage of Megaphone is currently disabled.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6115 | Method: dropMessage | `เลเวลต่ำกว่า 10 ไม่สามารถใช้งานได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6117 | Method: dropMessage | `Cannot be used here.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6119 | Method: dropMessage | `สามารถใช้ได้เพียงครั้งเดียวใน 10 วินาที` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6139 | Method: dropMessage | `The usage of Megaphone is currently disabled.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6146 | Method: dropMessage | `เลเวลต่ำกว่า 10 ไม่สามารถใช้งานได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6148 | Method: dropMessage | `Cannot be used here.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6150 | Method: dropMessage | `สามารถใช้ได้เพียงครั้งเดียวใน 10 วินาที` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6164 | Method: dropMessage | `That character is not in the channel.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6185 | Method: dropMessage | `เลเวลต่ำกว่า 10 ไม่สามารถใช้งานได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6187 | Method: dropMessage | `Cannot be used here.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6189 | Method: dropMessage | `สามารถใช้ได้เพียงครั้งเดียวใน 10 วินาที` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6224 | Method: dropMessage | `The usage of Megaphone is currently disabled.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6230 | Method: dropMessage | `เลเวลต่ำกว่า 200 ไม่สามารถใช้งานได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6237 | Method: dropMessage | `สามารถใช้ได้ทุกๆ 1 นาที` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6241 | Method: dropMessage | `สามารถใช้ได้ทุกๆ 10 วินาที` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6277 | Method: dropMessage | `เลเวลต่ำกว่า 10 ไม่สามารถใช้งานได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6279 | Method: dropMessage | `Cannot be used here.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6281 | Method: dropMessage | `สามารถใช้ได้เพียงครั้งเดียวใน 10 วินาที` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6308 | Method: dropMessage | `The usage of Megaphone is currently disabled.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6344 | Method: dropMessage | `ต้องมีเลเวล 10 ขึ้นไป` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6346 | Method: dropMessage | `ไม่สามารถใช้ได้ที่นี่` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6348 | Method: dropMessage | `สามารถใช้ได้อีกครั้งใน 5 นาที` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6366 | Method: dropMessage | `The usage of Megaphone is currently disabled.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6371 | Method: dropMessage | `เลเวลต่ำกว่า 10 ไม่สามารถใช้งานได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6373 | Method: dropMessage | `Cannot be used here.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6375 | Method: dropMessage | `สามารถใช้ได้เพียงครั้งเดียวใน 10 วินาที` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6384 | Method: dropMessage | `The usage of Megaphone is currently disabled.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6687 | Method: dropMessage | `ค้นหาสัตว์เลี้ยงล้มเหลว!` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6729 | Method: dropMessage | `ค้นหาสัตว์เลี้ยงล้มเหลว!` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6755 | Method: dropMessage | `ไม่พบไอเทม` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6861 | Method: dropMessage | `ไม่สามารถใช้ที่นี่ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6876 | Method: dropMessage | `ไม่สามารถใช้คำสั่งนี้ที่นี่ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6883 | Method: dropMessage | `ต้องมีเลเวล 10 ขึ้นไปเพื่อใช้คำสั่งนี้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6896 | Method: dropMessage | `ไม่สามารถใช้คำสั่งนี้ที่นี่ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6899 | Method: dropMessage | `ไม่สามารถใช้คำสั่งนี้ที่นี่ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6931 | Method: dropMessage | `It may not be used on this item.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6961 | Method: dropMessage | `It may not be used on this item.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6991 | Method: dropMessage | `It may not be used on this item.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 7021 | Method: dropMessage | `It may not be used on this item.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 7052 | Method: dropMessage | `It may not be used on this item.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 7079 | Method: dropMessage | `Pink Bean และ Yeti ไม่สามารถใช้ Scissors of Kar...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 7150 | Method: dropMessage | `ไม่มี Android ที่สวมใส่อยู่ ไม่สามารถตั้งชื่อได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 7286 | Method: dropMessage | `Auto relog failed.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 7290 | Method: dropMessage | `Auto relogging. Please wait.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 7460 | Method: dropMessage | `Blossom Gauge : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 7603 | Method: dropMessage | `ไอเทมนี้เก็บไม่ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 7850 | Method: dropMessage | `Blossom Gauge : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 7992 | Method: dropMessage | ` ได้รับ Honor EXP` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8147 | Method: dropMessage | `Please make room in your inventory.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8163 | Method: dropMessage | `ไม่พบไอเทม` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8238 | Method: dropMessage | `ไม่สามารถเข้าห้องได้เนื่องจากเต็มแล้ว` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8240 | Method: dropMessage | `ไม่สามารถเข้าร้านค้านี้ได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8248 | Method: dropMessage | `The owner of the store is currently undergoing ...` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8251 | Method: dropMessage | `The room is already closed.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8294 | Method: dropMessage | `ไม่สามารถไปที่นั่นได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8297 | Method: dropMessage | `ไม่สามารถไปที่นั่นได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8309 | Method: dropMessage | `ไม่สามารถไปที่นั่นได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8312 | Method: dropMessage | `ไม่สามารถไปที่นั่นได้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8319 | Method: dropMessage | `(` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8357 | Method: dropMessage | `ฟังก์ชั่นนี้ยังไม่เปิดใช้งานในขณะนี้` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8419 | Method: dropMessage | `อุปกรณ์ของ Zero จะกลับสู่สภาพเริ่มต้นแทนที่จะถู...` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8637 | Method: dropMessage | `เพิ่มช่องตัวละครเรียบร้อยแล้ว` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8641 | Method: dropMessage | `ไม่สามารถเพิ่มช่องตัวละครได้อีก` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8672 | Method: dropMessage | `กรุณาทำช่องว่างใน Equip, Use, Etc อย่างน้อย 1 ช่อง` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8767 | Method: dropMessage | `กรุณาทำช่องว่างในช่อง Use และ Etc อย่างละ 1 ช่อง` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8782 | Method: dropMessage | `กรุณาทำช่องว่างใน Equip และ Use อย่างละ 1 ช่อง` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8958 | Method: dropMessage | `[Grade Ceiling] ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8958 | Method: dropMessage | ` บรรลุจำนวนเป้าหมายการการันตีระดับ ` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8958 | Method: dropMessage | ` ระดับเพิ่มขึ้นอย่างแน่นอน` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8960 | Method: dropMessage | `[Grade Ceiling] 해당 큐브는 등급이 상승되어 Before 옵션을 선택해도...` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8968 | Method: dropMessage | `[Grade Ceiling] ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8968 | Method: dropMessage | ` โดยการใช้ ` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8968 | Method: dropMessage | `จำนวนเป้าหมายที่เหลือ (` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8984 | Method: dropMessage | `[Grade Ceiling] ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8984 | Method: dropMessage | ` บรรลุจำนวนเป้าหมายการการันตีระดับ 다음 ` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8985 | Method: dropMessage | ` เมื่อพยายามอัพเกรดระดับ ` | Already Thai |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8985 | Method: dropMessage | ` ระดับจะเพิ่มขึ้นอย่างแน่นอน` | Already Thai |
| `src\network\game\processors\inventory\ItemMakerHandler.java` | 142 | Method: dropMessage | `The item was overwhelmed by the stimulator.` | Pending |
| `src\network\game\processors\inventory\PetHandler.java` | 90 | Method: dropMessage | `ยังใช้ไอเทมนี้ไม่ได้` | Already Thai |
| `src\network\game\processors\inventory\PetHandler.java` | 274 | Method: dropMessage | `ไม่มีข้อมูลสัตว์เลี้ยง` | Already Thai |
| `src\network\game\processors\inventory\PetHandler.java` | 277 | Method: dropMessage | `เกิดข้อผิดพลาดในข้อมูล Index` | Already Thai |
| `src\network\game\processors\inventory\PetHandler.java` | 280 | Method: dropMessage | `เกิดข้อผิดพลาดในข้อมูลสกิล` | Already Thai |
| `src\network\game\processors\inventory\PetHandler.java` | 287 | Method: dropMessage | `เกิดข้อผิดพลาดในข้อมูลย่อยของสกิล` | Already Thai |
| `src\network\game\processors\job\ZeroHandler.java` | 131 | Method: dropMessage | `ไม่สามารถอัพเกรดอาวุธได้อีกต่อไป` | Already Thai |
| `src\network\game\processors\job\ZeroHandler.java` | 137 | Method: dropMessage | `ไม่สามารถอัพเกรดอาวุธได้อีกต่อไป` | Already Thai |
| `src\network\game\processors\job\ZeroHandler.java` | 162 | Method: dropMessage | `เลเวลไม่เพียงพอสำหรับการอัพเกรดอาวุธ` | Already Thai |
| `src\network\game\processors\job\ZeroHandler.java` | 191 | Method: dropMessage | `ไม่สามารถอัพเกรดอาวุธได้อีกต่อไป` | Already Thai |
| `src\network\game\processors\job\ZeroHandler.java` | 324 | Method: dropMessage | `ไม่สามารถอัพเกรดอาวุธได้อีกต่อไป` | Already Thai |
| `src\network\game\processors\monstercollection\MonsterCollectionHandler.java` | 70 | Method: dropMessage | `พื้นที่ช่องเก็บของไม่เพียงพอ` | Already Thai |
| `src\network\game\processors\monstercollection\MonsterCollectionHandler.java` | 110 | Method: dropMessage | `พื้นที่ช่องเก็บของไม่เพียงพอ` | Already Thai |
| `src\network\game\processors\monstercollection\MonsterCollectionHandler.java` | 152 | Method: dropMessage | `เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุ` | Already Thai |
| `src\network\game\processors\monstercollection\MonsterCollectionHandler.java` | 171 | Method: dropMessage | `เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุ` | Already Thai |
| `src\network\game\processors\monstercollection\MonsterCollectionHandler.java` | 182 | Method: dropMessage | `เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุ` | Already Thai |
| `src\network\game\processors\monstercollection\MonsterCollectionHandler.java` | 248 | Method: dropMessage | `พื้นที่ช่องเก็บของไม่เพียงพอ 소비슬롯 5칸 기타슬롯 3칸 캐시...` | Already Thai |
| `src\network\game\processors\monstercollection\MonsterCollectionHandler.java` | 424 | Method: dropMessage | `mc is null.` | Pending |
| `src\network\game\processors\monstercollection\MonsterCollectionHandler.java` | 433 | Method: dropMessage | `si is null.` | Pending |
| `src\network\game\processors\monstercollection\MonsterCollectionHandler.java` | 442 | Method: dropMessage | `cGroup is null.` | Pending |
| `src\network\login\processors\CharLoginHandler.java` | 1319 | Method: dropMessage | `An error occured.` | Pending |
| `src\network\login\processors\CharLoginHandler.java` | 1323 | Method: dropMessage | `An error has occurred.` | Pending |
| `src\network\login\processors\CharLoginHandler.java` | 1327 | Method: dropMessage | `ไม่มีช่องตัวละคร` | Already Thai |
| `src\network\netty\MapleNettyHandler.java` | 1465 | Method: dropMessage | `CODYCOUPON Packet : ` | Pending |
| `src\network\shop\processors\CashShopHandler.java` | 1571 | Method: dropMessage | `รหัสคูปองไม่ถูกต้อง` | Already Thai |
| `src\network\shop\processors\CashShopHandler.java` | 1573 | Method: dropMessage | `รหัสคูปองนี้ถูกใช้งานไปแล้ว` | Already Thai |
| `src\network\shop\processors\CashShopHandler.java` | 1735 | Method: dropMessage | `ไม่สามารถซื้อไอเทมนี้ใน Cash Shop ได้` | Already Thai |
| `src\network\shop\processors\CashShopHandler.java` | 1768 | Method: dropMessage | `ไม่สามารถซื้อไอเทมนี้ใน Cash Shop ได้` | Already Thai |
| `src\network\shop\processors\CashShopHandler.java` | 1775 | Method: dropMessage | `ไม่สามารถซื้อไอเทมนี้ใน Cash Shop ได้` | Already Thai |
| `src\network\shop\processors\CashShopHandler.java` | 1782 | Method: dropMessage | `ไม่สามารถซื้อไอเทมนี้ใน Cash Shop ได้` | Already Thai |
| `src\network\shop\processors\CashShopHandler.java` | 1879 | Method: dropMessage | `ไม่สามารถเพิ่มช่องได้อีกต่อไป` | Already Thai |
| `src\network\shop\processors\CashShopHandler.java` | 1898 | Method: dropMessage | `ไม่สามารถเพิ่มช่องได้อีกต่อไป` | Already Thai |
| `src\network\shop\processors\CashShopHandler.java` | 1902 | Method: dropMessage | `ไม่สามารถซื้อช่องตัวละครเพิ่มใน Cash Shop ได้` | Already Thai |
| `src\network\shop\processors\CashShopHandler.java` | 1916 | Method: dropMessage | `ขยายช่องสร้อยคออยู่แล้ว` | Already Thai |
| `src\network\shop\processors\CashShopHandler.java` | 1971 | Method: dropMessage | `ไม่สามารถย้ายไอเทมนี้ได้` | Already Thai |
| `src\network\shop\processors\CashShopHandler.java` | 1977 | Method: dropMessage | `ไม่สามารถย้ายไอเทมนี้ได้` | Already Thai |
| `src\network\shop\processors\CashShopHandler.java` | 2088 | Method: dropMessage | `เกิดข้อผิดพลาด! ไม่พบไอเทม Cash นี้ กรุณาติดต่อ GM` | Already Thai |
| `src\network\shop\processors\CashShopHandler.java` | 2125 | Method: dropMessage | `ช่องในช่องเก็บของไม่เพียงพอ` | Already Thai |
| `src\network\shop\processors\CashShopHandler.java` | 2287 | Method: dropMessage | `ไม่สามารถซื้อไอเทมนี้ใน Cash Shop ได้` | Already Thai |
| `src\network\shop\processors\CashShopHandler.java` | 2320 | Method: dropMessage | `ไม่สามารถซื้อไอเทมนี้ใน Cash Shop ได้` | Already Thai |
| `src\network\shop\processors\CashShopHandler.java` | 2327 | Method: dropMessage | `ไม่สามารถซื้อไอเทมนี้ใน Cash Shop ได้` | Already Thai |
| `src\network\shop\processors\CashShopHandler.java` | 2334 | Method: dropMessage | `ไม่สามารถซื้อไอเทมนี้ใน Cash Shop ได้` | Already Thai |
| `src\objects\contents\ContentsManager.java` | 85 | Method: chatMsg | `คำตอบคือ : ` | Already Thai |
| `src\objects\contents\ContentsManager.java` | 85 | Method: chatMsg | ` ครับ!` | Already Thai |
| `src\objects\contents\ContentsManager.java` | 94 | Method: chatMsg | `Quiz ความรู้ทั่วไป! (แจก Union Coin) คำถาม : ` | Already Thai |
| `src\objects\contents\ContentsManager.java` | 114 | Method: dropMessage | `ยังไม่ครบ 15 นาทีหลังจากตอบถูกครั้งล่าสุด กรุณา...` | Already Thai |
| `src\objects\contents\ContentsManager.java` | 118 | Method: chatMsg | `คำตอบ : ` | Already Thai |
| `src\objects\contents\ContentsManager.java` | 267 | Method: chatMsg | `[` | Pending |
| `src\objects\contents\ContentsManager.java` | 269 | Method: chatMsg | `ผลลัพธ์ Ladder รอบที่] : ` | Already Thai |
| `src\objects\contents\ContentsManager.java` | 354 | Method: chatMsg | `ยอดเงินรางวัล Top.` | Already Thai |
| `src\objects\contents\ContentsManager.java` | 364 | Method: chatMsg | `ยอดขาดทุน Top.1 ` | Already Thai |
| `src\objects\fields\EliteBossBonusStageEvent.java` | 145 | Method: sendWeatherEffectNotice | `ถ้าโจมตีปกติอาจจะได้รับไอเทมนะ!` | Already Thai |
| `src\objects\fields\Event_DojoAgent.java` | 260 | Method: dropMessage | `An error has occurred and you shall be brought ...` | Pending |
| `src\objects\fields\Event_DojoAgent.java` | 265 | Method: dropMessage | `An error has occurred and you shall be brought ...` | Pending |
| `src\objects\fields\Field.java` | 1613 | Method: dropMessage | `ไม่ได้รับ Hell Boss Blue Bead เนื่องจากช่องเก็บ...` | Already Thai |
| `src\objects\fields\Field.java` | 1617 | Method: dropMessage | `ได้รับ Hell Boss Blue Bead ` | Already Thai |
| `src\objects\fields\Field.java` | 1617 | Method: dropMessage | `ชิ้นที่ได้รับ` | Already Thai |
| `src\objects\fields\Field.java` | 1620 | Method: dropMessage | `ได้รับ Hell Boss Blue Bead 획득하는데 실패하였습니다.` | Already Thai |
| `src\objects\fields\Field.java` | 2840 | Method: dropMessage | `JobField ไม่ใช่ Flame Wizard??` | Already Thai |
| `src\objects\fields\Field.java` | 3037 | Method: sendBigScriptProgressMessage | `การได้รับ EXP และ Meso จะลดลงอย่างมากเมื่อล่ามอ...` | Already Thai |
| `src\objects\fields\Field.java` | 3426 | Method: chatMsg | `[보스격파] [CH.` | Pending |
| `src\objects\fields\Field.java` | 3438 | Method: chatMsg | `[보스격파] [CH.` | Pending |
| `src\objects\fields\Field.java` | 3476 | Method: chatMsg | `[보스격파] [CH.` | Pending |
| `src\objects\fields\Field.java` | 5208 | Method: dropMessage | `ระยะเวลาซัมมอน : ` | Already Thai |
| `src\objects\fields\Field.java` | 5208 | Method: dropMessage | `m/s` | Pending |
| `src\objects\fields\Field.java` | 6198 | Method: dropMessage | `[onFirstUserEnter] ` | Pending |
| `src\objects\fields\Field.java` | 6230 | Method: dropMessage | `[onUserEnter] ` | Pending |
| `src\objects\fields\Field.java` | 10175 | Method: dropMessage | `มีคนใช้ Haste Booster อยู่แล้ว` | Already Thai |
| `src\objects\fields\Field.java` | 10177 | Method: dropMessage | `Elite Boss ถูกเรียกออกมาแล้ว` | Already Thai |
| `src\objects\fields\Field.java` | 10187 | Method: dropMessage | `แผนที่นี้ใช้ Haste Booster ไม่ได้` | Already Thai |
| `src\objects\fields\MapleSquad.java` | 87 | Method: dropMessage | `Squad ถูกข้ามเนื่องจากคุณไม่ได้อยู่ในแชนแนลและแ...` | Already Thai |
| `src\objects\fields\MapScriptMethods.java` | 1108 | Method: sendWillNotice | `ต้องโจมตี Will ใน 2 มิติที่แตกต่างกันพร้อมๆ กัน...` | Already Thai |
| `src\objects\fields\MapScriptMethods.java` | 1198 | Method: sendWillNotice | `ระวังร่างจริงที่สะท้อนในกระจก หากรวบรวมแสงจันทร...` | Already Thai |
| `src\objects\fields\MapScriptMethods.java` | 1311 | Method: sendWillNotice | `Will เอาจริงแล้ว! หากรวบรวมแสงจันทร์มาใช้ อาจเผ...` | Already Thai |
| `src\objects\fields\MapScriptMethods.java` | 1348 | Method: sendJinHillahNotice | `Hilla จะตัดเทียนแห่งวิญญาณที่ลุกโชนทุกช่วงเวลา ...` | Already Thai |
| `src\objects\fields\MapScriptMethods.java` | 1372 | Method: sendBlackMageNotice | `เพื่อต่อกรกับ Black Mage ต้องกำจัดอัศวินแห่งการ...` | Already Thai |
| `src\objects\fields\MapScriptMethods.java` | 1394 | Method: sendBlackMageNotice | `ในที่สุดก็มายืนต่อหน้า Black Mage ทุ่มสุดตัวเพื...` | Already Thai |
| `src\objects\fields\MapScriptMethods.java` | 1414 | Method: sendBlackMageNotice | `รูปลักษณ์นั่นราวกับได้รับพลังของพระเจ้ามา แม้ 상...` | Already Thai |
| `src\objects\fields\MapScriptMethods.java` | 1980 | Method: dropMessage | `*scriptName: ` | Pending |
| `src\objects\fields\MapScriptMethods.java` | 2022 | Method: sendWeatherEffectNotice | `ความกลัวค่อยๆ เพิ่มขึ้นจนมองเห็นสิ่งที่ไม่ควรมี...` | Already Thai |
| `src\objects\fields\MapScriptMethods.java` | 2026 | Method: sendWeatherEffectNotice | `Captain Dunkel : ตราบใดที่มีข้าและกองทัพอยู่ แก...` | Already Thai |
| `src\objects\fields\MapScriptMethods.java` | 2235 | Method: dropMessage | `บัญชีนี้ดู Intro ไปแล้ว จะถูกย้ายไป True Castle` | Already Thai |
| `src\objects\fields\MapScriptMethods.java` | 2346 | Method: dropMessage | `There are still ` | Pending |
| `src\objects\fields\MapScriptMethods.java` | 2346 | Method: dropMessage | ` monsters remaining.` | Pending |
| `src\objects\fields\MapScriptMethods.java` | 2350 | Method: dropMessage | `There are still some monsters remaining in this...` | Pending |
| `src\objects\fields\MapScriptMethods.java` | 2352 | Method: dropMessage | `There are no monsters remaining in this map.` | Pending |
| `src\objects\fields\MapScriptMethods.java` | 2445 | Method: dropMessage | `Quest complete.` | Pending |
| `src\objects\fields\MapScriptMethods.java` | 2601 | Method: dropMessage | `ต้องกำจัดมอนสเตอร์ทั้งหมดในแผนที่ก่อนถึงจะไปด่า...` | Already Thai |
| `src\objects\fields\MapScriptMethods.java` | 2726 | Method: dropMessage | `/` | Pending |
| `src\objects\fields\MapScriptMethods.java` | 2726 | Method: dropMessage | `การสำรวจ` | Already Thai |
| `src\objects\fields\MapScriptMethods.java` | 2727 | Method: dropMessage | `Title - ` | Pending |
| `src\objects\fields\MapScriptMethods.java` | 2727 | Method: dropMessage | ` Adventurer Challenge in progress` | Pending |
| `src\objects\fields\Portal.java` | 84 | Method: dropMessage | `PortalScript : ` | Pending |
| `src\objects\fields\Portal.java` | 106 | Method: dropMessage | `เลเวลของคุณต่ำเกินไปที่จะเข้าที่นี่` | Already Thai |
| `src\objects\fields\child\arkarium\Field_Arkaium.java` | 201 | Method: dropMessage | `Arkarium บิดเบือนกาลเวลาและเนรเทศคุณไปที่ไหนสัก...` | Already Thai |
| `src\objects\fields\child\blackmage\Field_BlackMageBattlePhase1.java` | 48 | Method: sendBlackMageNotice | `Knight of Creation and Destruction พ่ายแพ้แล้ว ...` | Already Thai |
| `src\objects\fields\child\blackmage\Field_BlackMageBattlePhase1.java` | 92 | Method: sendBlackMageNotice | `Wailing Wall ปรากฏขึ้นและเข้าครอบงำพื้นที่` | Already Thai |
| `src\objects\fields\child\blackmage\Field_BlackMageBattlePhase1.java` | 140 | Method: sendBlackMageNotice | `สายฟ้าสีแดงที่น่ากลัวฟาดลงมา จำกัดการเคลื่อนไหว` | Already Thai |
| `src\objects\fields\child\blackmage\Field_BlackMageBattlePhase2.java` | 92 | Method: sendBlackMageNotice | `พลังลึกลับแผ่ออกมาจาก Black Mage และกลืนกินบัลล...` | Already Thai |
| `src\objects\fields\child\blackmage\Field_BlackMageBattlePhase2.java` | 126 | Method: sendBlackMageNotice | `Eye of Ruin ไล่ตามศัตรู` | Already Thai |
| `src\objects\fields\child\blackmage\Field_BlackMageBattlePhase2.java` | 174 | Method: sendBlackMageNotice | `สายฟ้าสีแดงของ Black Mage ปกคลุมไปทั่ว ต้องหาที...` | Already Thai |
| `src\objects\fields\child\blackmage\Field_BlackMageBattlePhase3.java` | 70 | Method: sendBlackMageNotice | `ทุกสิ่งรอบข้างกำลังสูญสลายไปในพริบตาด้วยพลังอัน...` | Already Thai |
| `src\objects\fields\child\blackmage\Field_BlackMageBattlePhase3.java` | 168 | Method: sendBlackMageNotice | `Black Mage ใช้พลังแห่งการสร้างและทำลายล้าง ต้อง...` | Already Thai |
| `src\objects\fields\child\blackmage\Field_BlackMageBattlePhase3.java` | 505 | Method: sendBlackMageNotice | `ทูตสวรรค์แห่งการทำลายล้างกำเนิดขึ้นจากความว่างเ...` | Already Thai |
| `src\objects\fields\child\blackmage\Field_BlackMageBattlePhase4.java` | 52 | Method: sendBlackMageNotice | `ทำลายไข่แห่งการสร้างและจบการต่อสู้อันยาวนานนี้ก...` | Already Thai |
| `src\objects\fields\child\blackmage\Field_BlackMageBattlePhase4.java` | 128 | Method: chatMsg | `[보스격파] [CH.` | Pending |
| `src\objects\fields\child\blackmage\Field_BlackMageBattlePhase4.java` | 164 | Method: sendBlackMageNotice | `อำนาจของผู้ใกล้เคียงพระเจ้าปรากฏขึ้น ต้องเลือกว...` | Already Thai |
| `src\objects\fields\child\demian\Field_Demian.java` | 385 | Method: chatMsg | `[보스격파] [CH.` | Pending |
| `src\objects\fields\child\demian\Field_Demian.java` | 398 | Method: chatMsg | `[보스격파] [CH.` | Pending |
| `src\objects\fields\child\demian\Field_Demian.java` | 412 | Method: chatMsg | `[보스격파] [CH.` | Pending |
| `src\objects\fields\child\demian\Field_Demian.java` | 772 | Method: sendDemianNotice | `Damien ได้ครอบครองพลังแห่งความมืดที่สมบูรณ์แล้ว` | Already Thai |
| `src\objects\fields\child\demian\Field_Demian.java` | 789 | Method: sendDemianNotice | `ตราประทับสมบูรณ์ Damien กำลังถูกความมืดเข้าครอบงำ` | Already Thai |
| `src\objects\fields\child\dojang\DojangRanking.java` | 296 | Method: dropMessage | `[알림] 무릉도장 랭킹 ` | Pending |
| `src\objects\fields\child\dojang\DojangRanking.java` | 349 | Method: dropMessage | `[알림] 무릉도장(챌린지) 랭킹 ` | Pending |
| `src\objects\fields\child\dojang\Field_Dojang.java` | 210 | Method: dropMessage | `ปลดล็อค Mulung Dojo Challenge Mode แล้ว` | Already Thai |
| `src\objects\fields\child\dojang\Field_Dojang.java` | 215 | Method: dropMessage | `ทำลายสถิติสูงสุดของสัปดาห์นี้เรียบร้อยแล้ว` | Already Thai |
| `src\objects\fields\child\etc\Field_DamageMeasurement.java` | 150 | Method: chatMsg | `[Measurement Complete] ความเสียหายต่อหุ่นฟางในเ...` | Already Thai |
| `src\objects\fields\child\etc\Field_EventRabbit.java` | 41 | Method: dropMessage | `กำจัด Moon Bunny ไม่ทันเวลา ไม่ได้รับรางวัล` | Already Thai |
| `src\objects\fields\child\etc\Field_EventRabbit.java` | 78 | Method: dropMessage | `กำจัด Moon Bunny และได้รับ Persimmon Coin ` | Already Thai |
| `src\objects\fields\child\etc\Field_EventRabbit.java` | 78 | Method: dropMessage | `ชิ้นที่ได้รับ` | Already Thai |
| `src\objects\fields\child\etc\Field_EventSnowman.java` | 41 | Method: dropMessage | `กำจัด Giant Snowman ไม่ทันเวลา ไม่ได้รับรางวัล` | Already Thai |
| `src\objects\fields\child\etc\Field_EventSnowman.java` | 77 | Method: dropMessage | `กำจัด Giant Snowman และได้รับ Merry Christmas G...` | Already Thai |
| `src\objects\fields\child\etc\Field_MMRace.java` | 97 | Method: dropMessage | `ถ้าอยากร่วม Doggy Race ก็มาคุยกับฉัน Doart สิ~!` | Already Thai |
| `src\objects\fields\child\jinhillah\Field_JinHillah.java` | 348 | Method: chatMsg | `[보스격파] [CH.` | Pending |
| `src\objects\fields\child\jinhillah\Field_JinHillah.java` | 575 | Method: sendJinHillahNotice | `ก่อนที่ Hilla จะเข้ามาและหายไป ต้องรัวปุ่ม Harv...` | Already Thai |
| `src\objects\fields\child\karing\Field_BossGoongiPhase.java` | 69 | Method: sendWeatherEffectNotice | `เพื่อไล่ตาม Karing ต้องกำจัด 4 สัตว์ร้ายที่บุกร...` | Already Thai |
| `src\objects\fields\child\karing\Field_BossGoongiPhase.java` | 106 | Method: dropMessage | `skillID : ` | Pending |
| `src\objects\fields\child\karing\Field_BossGoongiPhase.java` | 106 | Method: dropMessage | ` skillLevel : ` | Pending |
| `src\objects\fields\child\karing\Field_BossGoongiPhase.java` | 116 | Method: dropMessage | `ส่งแพ็กเก็ต Type 3 แล้ว` | Already Thai |
| `src\objects\fields\child\karing\Field_BossGoongiPhase.java` | 146 | Method: dropMessage | `mobID : ` | Pending |
| `src\objects\fields\child\karing\Field_BossKaringMatch.java` | 19 | Method: dropMessage | `Type แรก : ` | Already Thai |
| `src\objects\fields\child\karing\Field_BossKaringMatch.java` | 24 | Method: dropMessage | ` ครั้งที่สอง : ` | Already Thai |
| `src\objects\fields\child\karing\Field_BossKaringPhase2.java` | 27 | Method: sendWeatherEffectNotice | `วิญญาณแค้นของสัตว์ประหลาดที่ Karing ดูดซับกำลัง...` | Already Thai |
| `src\objects\fields\child\karing\Field_BossKaringPhase2.java` | 43 | Method: sendWeatherEffectNotice | `Karing ที่ดูดซับ 4 สัตว์ร้าย ดูเหมือนจะอาละวาดไ...` | Already Thai |
| `src\objects\fields\child\karing\Field_BossKaringPhase3.java` | 31 | Method: sendWeatherEffectNotice | `ดูเหมือนว่าเครื่องดนตรีของสัตว์ประหลาดจะล้นทะลั...` | Already Thai |
| `src\objects\fields\child\karrotte\Field_BossKalosPhase2.java` | 132 | Method: chatMsg | `[보스격파] [CH.` | Pending |
| `src\objects\fields\child\lucid\Field_LucidBattle.java` | 143 | Method: sendLucidNotice | `กดปุ่ม 'Harvest' ใกล้รูปปั้นแตรเพื่อยับยั้งพลัง...` | Already Thai |
| `src\objects\fields\child\lucid\Field_LucidBattle.java` | 154 | Method: sendLucidNotice | `กดปุ่ม 'Harvest' ใกล้รูปปั้นแตรเพื่อยับยั้งพลัง...` | Already Thai |
| `src\objects\fields\child\lucid\Field_LucidBattle.java` | 522 | Method: sendLucidNotice | `ความฝันกำลังรุนแรงขึ้น ระวังตัวด้วย!` | Already Thai |
| `src\objects\fields\child\lucid\Field_LucidBattlePhase1.java` | 64 | Method: sendLucidNotice | `ดูเหมือน Lucid จะโกรธจัด!` | Already Thai |
| `src\objects\fields\child\lucid\Field_LucidBattlePhase1.java` | 72 | Method: sendLucidNotice | `Lucid จะแสดงพลังที่แข็งแกร่งยิ่งขึ้น!` | Already Thai |
| `src\objects\fields\child\lucid\Field_LucidBattlePhase1.java` | 79 | Method: sendLucidNotice | `Lucid กำลังดึงพลังออกมา!` | Already Thai |
| `src\objects\fields\child\lucid\Field_LucidBattlePhase1.java` | 86 | Method: sendLucidNotice | `ดูเหมือน Lucid จะโกรธจัด!` | Already Thai |
| `src\objects\fields\child\lucid\Field_LucidBattlePhase1.java` | 89 | Method: sendLucidNotice | `Lucid จะแสดงพลังที่แข็งแกร่งยิ่งขึ้น!` | Already Thai |
| `src\objects\fields\child\lucid\Field_LucidBattlePhase1.java` | 92 | Method: sendLucidNotice | `Lucid กำลังดึงพลังออกมา!` | Already Thai |
| `src\objects\fields\child\lucid\Field_LucidBattlePhase2.java` | 476 | Method: chatMsg | `[보스격파] [CH.` | Pending |
| `src\objects\fields\child\lucid\Field_LucidBattlePhase2.java` | 529 | Method: chatMsg | `[Boss Defeated] [CH.` | Pending |
| `src\objects\fields\child\lucid\Field_LucidBattlePhase2.java` | 541 | Method: chatMsg | `[보스격파] [CH.` | Pending |
| `src\objects\fields\child\lucid\Field_LucidBattlePhase2.java` | 589 | Method: chatMsg | `[Boss Defeated] [CH.` | Pending |
| `src\objects\fields\child\minigame\battlereverse\BattleReverseGameDlg.java` | 34 | Method: dropMessage | `จำนวนผู้เล่นไม่เพียงพอสำหรับเกม จึงถูกย้ายกลับเ...` | Already Thai |
| `src\objects\fields\child\minigame\battlereverse\BattleReverseGameDlg.java` | 50 | Method: dropMessage | `เกิดข้อผิดพลาด เกมไม่เริ่ม จึงถูกย้ายกลับเมือง` | Already Thai |
| `src\objects\fields\child\minigame\onecard\Field_OneCard.java` | 171 | Method: dropMessage | `คุณชนะ! จบเกม` | Already Thai |
| `src\objects\fields\child\minigame\onecard\OneCardGameDlg.java` | 37 | Method: dropMessage | `จำนวนผู้เล่นไม่เพียงพอสำหรับเกม จึงถูกย้ายกลับเ...` | Already Thai |
| `src\objects\fields\child\minigame\soccer\Field_MultiSoccer.java` | 301 | Method: sendWeatherEffectNotice | `   ใช้ปุ่มลูกศรเพื่อเคลื่อนที่ และใช้ปุ่ม CTRL ...` | Already Thai |
| `src\objects\fields\child\minigame\yutgame\Field_MultiYutGame.java` | 74 | Method: sendBigScriptProgressMessage | `หมดเวลา ` | Already Thai |
| `src\objects\fields\child\minigame\yutgame\MultiYutGameDlg.java` | 26 | Method: dropMessage | `จำนวนผู้เล่นไม่เพียงพอสำหรับเกม จึงถูกย้ายกลับเ...` | Already Thai |
| `src\objects\fields\child\minigame\yutgame\MultiYutGameDlg.java` | 61 | Method: dropMessage | `จำนวนผู้เล่นไม่เพียงพอสำหรับเกม จึงถูกย้ายกลับเ...` | Already Thai |
| `src\objects\fields\child\moonbridge\Field_FerociousBattlefield.java` | 373 | Method: chatMsg | `[보스격파] [CH.` | Pending |
| `src\objects\fields\child\moonbridge\Field_FerociousBattlefield.java` | 551 | Method: sendDuskNotice | `หนวดที่ป้องกันอยู่จะโจมตีอย่างรุนแรง! ถ้าทนได้ ...` | Already Thai |
| `src\objects\fields\child\rimen\Field_RimenNearTheEnd.java` | 234 | Method: chatMsg | `[보스격파] [CH.` | Pending |
| `src\objects\fields\child\rimen\Field_RimenNearTheEnd.java` | 247 | Method: chatMsg | `[보스격파] [CH.` | Pending |
| `src\objects\fields\child\sernium\Field_SerenPhase2.java` | 384 | Method: chatMsg | `[보스격파] [CH.` | Pending |
| `src\objects\fields\child\sernium\Field_SerenPhase2.java` | 398 | Method: chatMsg | `[보스격파] [CH.` | Pending |
| `src\objects\fields\child\slime\Field_GuardianAngelSlime.java` | 198 | Method: chatMsg | `[보스격파] [CH.` | Pending |
| `src\objects\fields\child\slime\Field_GuardianAngelSlime.java` | 442 | Method: sendWeatherEffectNotice | `สำเร็จแล้ว!! รีบขึ้นไปข้างบนภายใน 10 วินาทีก่อน...` | Already Thai |
| `src\objects\fields\child\slime\Field_GuardianAngelSlime.java` | 500 | Method: sendWeatherEffectNotice | `Guardian Wave กำลังจะตกลงมา!! ติดตั้ง Crystal D...` | Already Thai |
| `src\objects\fields\child\union\Field_Union.java` | 42 | Method: dropMessage | `โหลดข้อมูล Union ล้มเหลว` | Already Thai |
| `src\objects\fields\child\will\Field_WillBattle.java` | 412 | Method: sendWillNotice | `ตอนนี้แหละ! ต้องโจมตีตอนที่ Will ไร้การป้องกัน!` | Already Thai |
| `src\objects\fields\child\will\Field_WillBattle.java` | 1047 | Method: sendWillNotice | `Mirror of Lies สะท้อนการโจมตีกลับ หากรอยแยกปราก...` | Already Thai |
| `src\objects\fields\child\will\Field_WillBattlePhase1.java` | 44 | Method: sendWillNotice | `Will เริ่มจริงจังแล้ว ความจริงใจของ Will อาจสะท...` | Already Thai |
| `src\objects\fields\child\will\Field_WillBattlePhase2.java` | 42 | Method: sendWillNotice | `Will หมดความอดทนแล้ว ส่วนที่ลึกที่สุดของโลกกระจ...` | Already Thai |
| `src\objects\fields\child\will\Field_WillBattlePhase2.java` | 61 | Method: sendWillNotice | `Will หมดความอดทนแล้ว ส่วนที่ลึกที่สุดของโลกกระจ...` | Already Thai |
| `src\objects\fields\child\will\Field_WillBattleReward.java` | 140 | Method: chatMsg | `[보스격파] '` | Pending |
| `src\objects\fields\child\will\Field_WillBattleReward.java` | 196 | Method: chatMsg | `[보스격파] [CH.` | Pending |
| `src\objects\fields\child\will\Field_WillBattleReward.java` | 209 | Method: chatMsg | `[보스격파] [CH.` | Pending |
| `src\objects\fields\events\MapleEvent.java` | 64 | Method: dropMessage | `ยินดีด้วย! ` | Already Thai |
| `src\objects\fields\events\MapleEvent.java` | 64 | Method: dropMessage | ` ได้รับ Meso!` | Already Thai |
| `src\objects\fields\events\MapleEvent.java` | 68 | Method: dropMessage | `ยินดีด้วย! ` | Already Thai |
| `src\objects\fields\events\MapleEvent.java` | 68 | Method: dropMessage | ` ได้รับ Cash!` | Already Thai |
| `src\objects\fields\events\MapleEvent.java` | 71 | Method: dropMessage | `ยินดีด้วย! V포인트 1을 획득하셨습니다.` | Already Thai |
| `src\objects\fields\events\MapleEvent.java` | 74 | Method: dropMessage | `ยินดีด้วย! 인기도 10을 얻으셨습니다.` | Already Thai |
| `src\objects\fields\events\MapleEvent.java` | 76 | Method: dropMessage | `น่าเสียดาย ไม่ได้รับรางวัล ไว้โอกาสหน้านะ ~ ^^` | Already Thai |
| `src\objects\fields\events\MapleEvent.java` | 191 | Method: dropMessage | ` กิจกรรมเริ่มขึ้นแล้ว` | Already Thai |
| `src\objects\fields\events\MapleOxQuiz.java` | 140 | Method: dropMessage | `[Ox Quiz] ผิด!` | Already Thai |
| `src\objects\fields\events\MapleOxQuiz.java` | 143 | Method: dropMessage | `[Ox Quiz] ถูกต้อง!` | Already Thai |
| `src\objects\fields\fieldset\FieldSet.java` | 227 | Method: dropMessage | `จำนวนเข้าปัจจุบัน : ` | Already Thai |
| `src\objects\fields\fieldset\FieldSet.java` | 227 | Method: dropMessage | ` / 제한횟수 : ` | Pending |
| `src\objects\fields\fieldset\instance\ErdaSpectrum.java` | 541 | Method: sendBigScriptProgressMessage | `รอยแยกขยายใหญ่ขึ้น มอนสเตอร์ถูกเรียกออกมามากขึ้น` | Already Thai |
| `src\objects\fields\fieldset\instance\ErdaSpectrum.java` | 546 | Method: sendBigScriptProgressMessage | `1` | Pending |
| `src\objects\fields\fieldset\instance\ErdaSpectrum.java` | 551 | Method: sendBigScriptProgressMessage | `รอยแยกขยายใหญ่ขึ้น มอนสเตอร์ถูกเรียกออกมามากขึ้น` | Already Thai |
| `src\objects\fields\fieldset\instance\ErdaSpectrum.java` | 690 | Method: sendBigScriptProgressMessage | `Arma Junior ซ่อนตัวอยู่หลังถ้ำ` | Already Thai |
| `src\objects\fields\gameobject\lifes\MapleMonster.java` | 1428 | Method: sendBigScriptProgressMessage | `การได้รับ EXP จะลดลงอย่างมากเมื่อปาร์ตี้ล่าในมอ...` | Already Thai |
| `src\objects\fields\gameobject\lifes\MapleMonster.java` | 3811 | Method: sendDemianNotice | `ต้องสร้างความเสียหายรุนแรงเพื่อหยุด Damien ก่อน...` | Already Thai |
| `src\objects\fields\gameobject\lifes\mobskills\MobSkillInfo.java` | 302 | Method: sendJinHillahNotice | `ได้ยินเสียง Hilla ดึงวิญญาณ Lotus ขึ้นมาจากก้นบ...` | Already Thai |
| `src\objects\fields\gameobject\lifes\mobskills\MobSkillInfo.java` | 304 | Method: sendJinHillahNotice | `ได้ยินเสียง Hilla ดึงวิญญาณ Damien ขึ้นมาจากก้น...` | Already Thai |
| `src\objects\fields\gameobject\lifes\mobskills\MobSkillInfo.java` | 675 | Method: dropMessage | `MobID : ` | Pending |
| `src\objects\fields\gameobject\lifes\mobskills\MobSkillInfo.java` | 1366 | Method: sendLucidNotice | `ถ้าโดนลมนั้น ความฝันจะรุนแรงขึ้น!` | Already Thai |
| `src\objects\fields\gameobject\lifes\mobskills\MobSkillInfo.java` | 1376 | Method: sendLucidNotice | `Lucid กำลังจะใช้การโจมตีที่รุนแรง!` | Already Thai |
| `src\objects\fields\gameobject\lifes\mobskills\MobSkillInfo.java` | 1387 | Method: sendLucidNotice | `Lucid ได้เรียกมอนสเตอร์ที่แข็งแกร่งออกมา!` | Already Thai |
| `src\objects\fields\gameobject\lifes\mobskills\MobSkillInfo.java` | 1401 | Method: sendLucidNotice | `Lucid กำลังจะใช้การโจมตีที่รุนแรง!` | Already Thai |
| `src\objects\fields\gameobject\lifes\mobskills\MobSkillInfo.java` | 1497 | Method: sendWillNotice | `โจมตีดวงตาเพื่อส่งแสงจันทร์ไปยังอีกมิติ รีบสร้า...` | Already Thai |
| `src\objects\fields\gameobject\lifes\mobskills\MobSkillInfo.java` | 1548 | Method: sendWillNotice | `Will กำลังจะปลดปล่อยพลัง พื้นที่จอมปลอมจะพังทลา...` | Already Thai |
| `src\objects\fields\gameobject\lifes\mobskills\MobSkillInfo.java` | 2038 | Method: dropMessage | `skillLv : ` | Pending |
| `src\objects\fields\gameobject\lifes\mobskills\MobSkillInfo.java` | 2038 | Method: dropMessage | ` found.` | Pending |
| `src\objects\item\CustomItem.java` | 77 | Method: dropMessage | `นำเข้าข้อมูลหินทรานเซนเดนซ์จากเซเนียสำเร็จ (ข้อ...` | Already Thai |
| `src\objects\item\MapleInventoryManipulator.java` | 943 | Method: dropMessage | `ได้รับ Powder Keg สามารถนำไปให้ Aramia ที่ Henesys` | Already Thai |
| `src\objects\item\MapleInventoryManipulator.java` | 946 | Method: dropMessage | `ได้รับ Warm Sun, สามารถนำไปให้ Joyce ที่ Maple ...` | Already Thai |
| `src\objects\item\MapleInventoryManipulator.java` | 949 | Method: dropMessage | `ได้รับ Tree Decoration, สามารถนำไปให้ Joyce ที่...` | Already Thai |
| `src\objects\item\MapleInventoryManipulator.java` | 1190 | Method: dropMessage | `ไม่สามารถสวมใส่ไอเทมที่มี Decorative Option Enh...` | Already Thai |
| `src\objects\item\MapleInventoryManipulator.java` | 1193 | Method: dropMessage | `ไอเทมนี้ไม่สามารถสวมใส่ซ้ำได้` | Already Thai |
| `src\objects\item\MapleInventoryManipulator.java` | 1196 | Method: dropMessage | `ไอเทมนี้ไม่สามารถสวมใส่ซ้ำได้` | Already Thai |
| `src\objects\item\MapleInventoryManipulator.java` | 1201 | Method: dropMessage | `ไอเทมนี้ไม่สามารถสวมใส่ซ้ำได้` | Already Thai |
| `src\objects\item\MapleInventoryManipulator.java` | 1207 | Method: dropMessage | `ไอเทมนี้ไม่สามารถสวมใส่ซ้ำได้` | Already Thai |
| `src\objects\item\MapleInventoryManipulator.java` | 1526 | Method: dropMessage | `ไม่สามารถสวมใส่ได้เนื่องจากมี ... อยู่แล้ว` | Already Thai |
| `src\objects\item\MapleInventoryManipulator.java` | 1562 | Method: dropMessage | `กรุณาเหลือช่องว่างใน Cash Inventory อย่างน้อย 1...` | Already Thai |
| `src\objects\item\MapleInventoryManipulator.java` | 1682 | Method: dropMessage | `เกิดข้อผิดพลาดกับ Android` | Already Thai |
| `src\objects\item\MapleInventoryManipulator.java` | 2108 | Method: dropMessage | `ไม่สามารถทิ้ง Trace of Equipment ได้` | Already Thai |
| `src\objects\item\MapleInventoryManipulator.java` | 2114 | Method: dropMessage | `ไม่สามารถทิ้งถุง Meso ได้` | Already Thai |
| `src\objects\item\MapleInventoryManipulator.java` | 2120 | Method: dropMessage | `ไอเทมนี้ทิ้งไม่ได้` | Already Thai |
| `src\objects\item\MapleInventoryManipulator.java` | 2207 | Method: dropMessage | `เควสปลดปล่อย Genesis ถูกรีเซ็ต สามารถทำได้ใหม่เ...` | Already Thai |
| `src\objects\item\MapleItemInformationProvider.java` | 1748 | Method: dropMessage | `ไอเทมไม่ถูกทำลายเนื่องจากผลของ Scroll` | Already Thai |
| `src\objects\item\MapleItemInformationProvider.java` | 2009 | Method: dropMessage | `ไอเทมไม่ถูกทำลายเนื่องจากผลของ Scroll` | Already Thai |
| `src\objects\item\MapleItemInformationProvider.java` | 2090 | Method: dropMessage | `ไอเทมไม่ถูกทำลายเนื่องจากผลของ Scroll` | Already Thai |
| `src\objects\item\MapleItemInformationProvider.java` | 2186 | Method: dropMessage | `ไอเทมไม่ถูกทำลายเนื่องจากผลของ Scroll` | Already Thai |
| `src\objects\item\MapleItemInformationProvider.java` | 2212 | Method: dropMessage | `ไอเทมไม่ถูกทำลายเนื่องจากผลของ Scroll` | Already Thai |
| `src\objects\item\MapleItemInformationProvider.java` | 2245 | Method: dropMessage | `ไอเทมไม่ถูกทำลายเนื่องจากผลของ Scroll` | Already Thai |
| `src\objects\item\MapleItemInformationProvider.java` | 2331 | Method: dropMessage | `ไอเทมไม่ถูกทำลายเนื่องจากผลของ Scroll` | Already Thai |
| `src\objects\item\MapleItemInformationProvider.java` | 2404 | Method: dropMessage | `ไอเทมไม่ถูกทำลายเนื่องจากผลของ Scroll` | Already Thai |
| `src\objects\item\MapleItemInformationProvider.java` | 2420 | Method: dropMessage | `ไอเทมไม่ถูกทำลายเนื่องจากผลของ Scroll` | Already Thai |
| `src\objects\item\MapleItemInformationProvider.java` | 2572 | Method: dropMessage | `ไอเทมไม่ถูกทำลายเนื่องจากผลของ Scroll` | Already Thai |
| `src\objects\item\MapleItemInformationProvider.java` | 2680 | Method: dropMessage | `สามารถใช้ได้กับอุปกรณ์เลเวล 150 หรือต่ำกว่าเท่า...` | Already Thai |
| `src\objects\item\MapleItemInformationProvider.java` | 2733 | Method: dropMessage | `ไอเทมไม่ถูกทำลายเนื่องจากผลของ Scroll` | Already Thai |
| `src\objects\item\MapleItemInformationProvider.java` | 2954 | Method: dropMessage | `ไอเทมไม่ถูกทำลายเนื่องจากผลของ Scroll` | Already Thai |
| `src\objects\item\MapleItemInformationProvider.java` | 3001 | Method: dropMessage | `จำนวนอัพเกรดไม่ถูกหักออกเนื่องจากผลของ Scroll` | Already Thai |
| `src\objects\item\MapleItemInformationProvider.java` | 3265 | Method: dropMessage | `[Error] คำนวณโอกาสสำเร็จของ Scroll ล้มเหลว` | Already Thai |
| `src\objects\item\MapleItemInformationProvider.java` | 3275 | Method: dropMessage | `[Error] คำนวณโอกาสสำเร็จของ Scroll ล้มเหลว` | Already Thai |
| `src\objects\quest\MapleQuestAction.java` | 359 | Method: dropMessage | `คุณขาดไอเทมบางอย่างสำหรับเควส` | Already Thai |
| `src\objects\quest\MapleQuestAction.java` | 364 | Method: dropMessage | `มีไอเทมนี้อยู่แล้ว: ` | Already Thai |
| `src\objects\quest\MapleQuestAction.java` | 390 | Method: dropMessage | `ช่องเก็บของ Equip ` | Already Thai |
| `src\objects\quest\MapleQuestAction.java` | 390 | Method: dropMessage | `กรุณาทำช่องว่างแล้วลองใหม่อีกครั้ง` | Already Thai |
| `src\objects\quest\MapleQuestAction.java` | 393 | Method: dropMessage | `ช่องเก็บของ Use ` | Already Thai |
| `src\objects\quest\MapleQuestAction.java` | 393 | Method: dropMessage | `กรุณาทำช่องว่างแล้วลองใหม่อีกครั้ง` | Already Thai |
| `src\objects\quest\MapleQuestAction.java` | 396 | Method: dropMessage | `ช่องเก็บของ Setup ` | Already Thai |
| `src\objects\quest\MapleQuestAction.java` | 396 | Method: dropMessage | `กรุณาทำช่องว่างแล้วลองใหม่อีกครั้ง` | Already Thai |
| `src\objects\quest\MapleQuestAction.java` | 399 | Method: dropMessage | `ช่องเก็บของ Etc ` | Already Thai |
| `src\objects\quest\MapleQuestAction.java` | 399 | Method: dropMessage | `กรุณาทำช่องว่างแล้วลองใหม่อีกครั้ง` | Already Thai |
| `src\objects\quest\MapleQuestAction.java` | 403 | Method: dropMessage | `ช่องเก็บของ Cash ` | Already Thai |
| `src\objects\quest\MapleQuestAction.java` | 403 | Method: dropMessage | `กรุณาทำช่องว่างแล้วลองใหม่อีกครั้ง` | Already Thai |
| `src\objects\quest\MapleQuestAction.java` | 412 | Method: dropMessage | `Meso exceed the max amount, 2147483647.` | Pending |
| `src\objects\quest\MapleQuestAction.java` | 416 | Method: dropMessage | `Insufficient meso.` | Pending |
| `src\objects\shop\HiredMerchant.java` | 90 | Method: dropMessage | `Item ` | Pending |
| `src\objects\shop\HiredMerchant.java` | 101 | Method: dropMessage | `The seller has too many mesos.` | Pending |
| `src\objects\shop\HiredMerchant.java` | 105 | Method: dropMessage | `ช่องเก็บของเต็ม` | Already Thai |
| `src\objects\shop\MaplePlayerShop.java` | 50 | Method: dropMessage | `ช่องเก็บของเต็ม` | Already Thai |
| `src\objects\shop\MaplePlayerShop.java` | 53 | Method: dropMessage | `Meso ไม่เพียงพอ` | Already Thai |
| `src\objects\shop\MapleShop.java` | 131 | Method: dropMessage | `ไม่สามารถซื้อไอเทมนี้ได้` | Already Thai |
| `src\objects\shop\MapleShop.java` | 191 | Method: dropMessage | `ช่องเก็บของเต็ม` | Already Thai |
| `src\objects\shop\MapleShop.java` | 204 | Method: dropMessage | `ช่องในช่องเก็บของไม่เพียงพอ` | Already Thai |
| `src\objects\shop\MapleShop.java` | 210 | Method: dropMessage | `ไม่สามารถซื้อได้อีก` | Already Thai |
| `src\objects\shop\MapleShop.java` | 223 | Method: dropMessage | `ไม่สามารถซื้อได้อีก` | Already Thai |
| `src\objects\shop\MapleShop.java` | 368 | Method: dropMessage | `ช่องเก็บของเต็ม` | Already Thai |
| `src\objects\shop\MapleShop.java` | 389 | Method: dropMessage | `ต้องการยศที่สูงกว่านี้` | Already Thai |
| `src\objects\shop\MapleShop.java` | 428 | Method: dropMessage | `ช่องเก็บของเต็ม` | Already Thai |
| `src\objects\shop\MapleShop.java` | 469 | Method: dropMessage | `ช่องเก็บของเต็ม` | Already Thai |
| `src\objects\users\CalcDamage.java` | 150 | Method: dropMessage | `Calculated STR DEX INT LUK ` | Pending |
| `src\objects\users\CalcDamage.java` | 174 | Method: dropMessage | `PAD : ` | Pending |
| `src\objects\users\CalcDamage.java` | 175 | Method: dropMessage | `MAD : ` | Pending |
| `src\objects\users\CalcDamage.java` | 176 | Method: dropMessage | `maxdmg : ` | Pending |
| `src\objects\users\CalcDamage.java` | 177 | Method: dropMessage | `mindmg : ` | Pending |
| `src\objects\users\CalcDamage.java` | 221 | Method: dropMessage | `Skill ID with 0 damage : ` | Pending |
| `src\objects\users\CalcDamage.java` | 224 | Method: dropMessage | `Skill Effect is null Skill ID : ` | Pending |
| `src\objects\users\CalcDamage.java` | 227 | Method: dropMessage | `Non-existent Skill ID : ` | Pending |
| `src\objects\users\CalcDamage.java` | 258 | Method: dropMessage | `Star Force Map : ` | Pending |
| `src\objects\users\CalcDamage.java` | 258 | Method: dropMessage | ` / ` | Pending |
| `src\objects\users\CalcDamage.java` | 266 | Method: dropMessage | `Authentic Force Map : ` | Pending |
| `src\objects\users\CalcDamage.java` | 266 | Method: dropMessage | ` / ` | Pending |
| `src\objects\users\CalcDamage.java` | 274 | Method: dropMessage | `Arcane Force Map : ` | Pending |
| `src\objects\users\CalcDamage.java` | 274 | Method: dropMessage | ` / ` | Pending |
| `src\objects\users\CalcDamage.java` | 287 | Method: dropMessage | `Damage Match ` | Pending |
| `src\objects\users\CalcDamage.java` | 287 | Method: dropMessage | ` hits Server Damage : ` | Pending |
| `src\objects\users\CalcDamage.java` | 288 | Method: dropMessage | ` / Client Damage : ` | Pending |
| `src\objects\users\CalcDamage.java` | 290 | Method: dropMessage | `Damage Mismatch ` | Pending |
| `src\objects\users\CalcDamage.java` | 290 | Method: dropMessage | ` hits Server Damage : ` | Pending |
| `src\objects\users\CalcDamage.java` | 291 | Method: dropMessage | ` / Client Damage : ` | Pending |
| `src\objects\users\CalcDamage.java` | 291 | Method: dropMessage | ` SKILLID : ` | Pending |
| `src\objects\users\MapleCharacter.java` | 6044 | Method: dropMessage | `Denied the challenge.` | Pending |
| `src\objects\users\MapleCharacter.java` | 6523 | Method: dropMessage | ` เลเวลเพิ่มขึ้นแล้ว` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 7056 | Method: dropMessage | `An item has run out of durability but has no in...` | Pending |
| `src\objects\users\MapleCharacter.java` | 7991 | Method: dropMessage | `[Praise Point Daily Quest] กำจัดมอนสเตอร์ในระดั...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 7993 | Method: dropMessage | `[Praise Point Daily Quest] วันนี้ ` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 7993 | Method: dropMessage | `เคลียร์ไปแล้ว ครั้ง ` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 7999 | Method: dropMessage | `Praise Point Daily Quest ของวันนี้เสร็จสิ้นทั้ง...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 8001 | Method: dropMessage | `[Praise Point Daily Quest (` | Pending |
| `src\objects\users\MapleCharacter.java` | 8411 | Method: dropMessage | `เกิดข้อผิดพลาดในการเปิดกล่องของขวัญ ทำให้ไม่ได้...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 8422 | Method: dropMessage | `[Notice] ของขวัญเลเวล 300 มาถึงแล้ว กรุณารับที่...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 8425 | Method: dropMessage | `เกิดข้อผิดพลาดในการเปิดกล่องของขวัญ ทำให้ไม่ได้...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 8511 | Method: dropMessage | `[Notice] ได้รับ Level-Appropriate Equipment Sup...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 8553 | Method: dropMessage | `[Notice] เลเวล 200 แล้ว ออกจาก Golden Fields` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 8570 | Method: dropMessage | `[Notice] ได้รับ Select Arcane Symbol Coupon 100...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 8571 | Method: dropMessage | `ได้รับ Select Arcane Symbol Coupon 100 ใบ (รางว...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 8617 | Method: chatMsg | `ได้รับ Symbol of Brilliant Honor แล้ว สามารถรับ...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 8618 | Method: dropMessage | `ได้รับ Symbol of Brilliant Honor แล้ว\r\nสามารถ...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 8620 | Method: dropMessage | `[Notice] ได้รับ Symbol of Brilliant Honor (รางว...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 8621 | Method: dropMessage | `ได้รับ Symbol of Brilliant Honor (รางวัลเลเวล 2...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11041 | Method: dropMessage | `ปิดใช้งาน Megaphone` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11044 | Method: dropMessage | `เปิดใช้งาน Megaphone` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11234 | Method: dropMessage | `ได้รับ Co-op Point 10 แต้ม สะสม : ` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11268 | Method: dropMessage | ` ได้รับ Boss Point` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11290 | Method: dropMessage | ` ได้รับ Boss Point` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11302 | Method: dropMessage | ` ได้รับ Boss Point` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11314 | Method: dropMessage | ` ได้รับ Boss Point` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11356 | Method: dropMessage | ` ได้รับ Boss Point` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11368 | Method: dropMessage | ` ได้รับ Boss Point` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11380 | Method: dropMessage | ` ได้รับ Boss Point` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11395 | Method: dropMessage | ` ได้รับ Boss Point` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11401 | Method: dropMessage | ` ได้รับ Boss Point` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11415 | Method: dropMessage | ` ได้รับ Boss Point` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11421 | Method: dropMessage | ` ได้รับ Boss Point` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11434 | Method: dropMessage | ` ได้รับ Boss Point` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11440 | Method: dropMessage | ` ได้รับ Boss Point` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11452 | Method: dropMessage | ` ได้รับ Boss Point` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11461 | Method: dropMessage | ` ได้รับ Boss Point` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11467 | Method: dropMessage | ` ได้รับ Boss Point` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11481 | Method: dropMessage | ` ได้รับ Boss Point` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11487 | Method: dropMessage | ` ได้รับ Boss Point` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11503 | Method: dropMessage | ` ได้รับ Boss Point` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11512 | Method: dropMessage | ` ได้รับ Boss Point` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11709 | Method: dropMessage | `Cash เต็มแล้ว ไม่ได้รับ Cash เพิ่ม` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11720 | Method: dropMessage | `Maple Point เต็มแล้ว ไม่ได้รับเพิ่ม` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 11734 | Method: dropMessage | `Cash เต็มแล้ว ไม่ได้รับ Cash เพิ่ม` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 13970 | Method: dropMessage | `สวมใส่ Murmur's Lord Ring มา ` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 13974 | Method: dropMessage | `ได้รับโบนัส EXP จากการล่ามอนสเตอร์เมื่อสวมใส่ M...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 14309 | Method: chatMsg | `ยกเลิกการติดตามแล้ว` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 15745 | Method: dropMessage | `Honor EXP ` | Pending |
| `src\objects\users\MapleCharacter.java` | 15745 | Method: dropMessage | ` ได้รับแล้ว` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 17624 | Method: dropMessage | `แผนที่เปลี่ยนไป ไม่สามารถรับ Rest Point ได้` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 17662 | Method: chatMsg | `จากนี้ไปจะได้รับ Rest Point 1 แต้มทุกๆ 1 นาที` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 17664 | Method: dropMessage | `ได้รับ Rest Point 1 แต้มทุกๆ 1 นาที` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 17704 | Method: dropMessage | `แผนที่เปลี่ยนไป ไม่สามารถรับ Rest Point ได้` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 17717 | Method: dropMessage | `[Fishing Notice] ผ่านการตกปลา [` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 17739 | Method: dropMessage | `[Rest Guide] รับ Neo Gem ทุก 1 นาทีจากการพักผ่อน` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 17740 | Method: dropMessage | `[Fishing Guide] เริ่มตกปลา` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 18661 | Method: dropMessage | `Dance Time เริ่มได้!` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 18680 | Method: dropMessage | `ได้รับ Dance Point 1 แต้ม ` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 18733 | Method: dropMessage | `ได้รับ EXP โบนัส 20% จากผลของการสวมใส่เหรียญกิลด์` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 18741 | Method: dropMessage | `ได้รับ EXP โบนัส 20% จากผลของการสวมใส่เหรียญตรา` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 19537 | Method: dropMessage | `EXP จากผลการสวมใส่เหรียญตรา ` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 19537 | Method: dropMessage | `%와 공/마 ` | Pending |
| `src\objects\users\MapleCharacter.java` | 19537 | Method: dropMessage | `% 증가 버프가 적용됩니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 19568 | Method: dropMessage | `Death Count รวมเหลือ 0 จึงถูกย้ายกลับเมือง` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 19581 | Method: dropMessage | `Death Count รวมเหลือ 0 จึงถูกย้ายกลับเมือง` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 20194 | Method: dropMessage | `[` | Pending |
| `src\objects\users\MapleCharacter.java` | 21470 | Method: dropMessage | `อาวุธรอง Kaiser ถูกเปลี่ยนอัตโนมัติ` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 21479 | Method: dropMessage | `อาวุธรอง Angelic Buster ถูกเปลี่ยนอัตโนมัติ` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 21483 | Method: dropMessage | `อาวุธรอง Angelic Buster ถูกเปลี่ยนอัตโนมัติ` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 21492 | Method: dropMessage | `อาวุธรอง Mihile ถูกเปลี่ยนอัตโนมัติ` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 21506 | Method: dropMessage | `อาวุธรอง Demon Slayer ถูกเปลี่ยนอัตโนมัติ` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 22088 | Method: dropMessage | `ผ่านเที่ยงคืนแล้ว Pendant of the Spirit ถูกรีเซ็ต` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 23690 | Method: dropMessage | `[` | Pending |
| `src\objects\users\MapleCharacter.java` | 24345 | Method: dropMessage | `พึ่งผ่าน Lie Detector มา ไม่สามารถใช้งานได้` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 24400 | Method: dropMessage | `เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุ ไม่สามารถใช้งานได้` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 24773 | Method: dropMessage | `ย้ายข้อมูล Stone เรียบร้อยแล้ว` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 24975 | Method: chatMsg | `ได้รับ Opening Celebration Support Box แล้ว สาม...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 24976 | Method: dropMessage | `ได้รับ Opening Celebration Support Box แล้ว\r\n...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 25006 | Method: chatMsg | `ได้รับ Ganglim November Heart Box แล้ว สามารถรั...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 25007 | Method: dropMessage | `ได้รับ Ganglim November Heart Box แล้ว\r\nสามาร...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 25046 | Method: chatMsg | `ได้รับ Symbol of Brilliant Honor แล้ว สามารถรับ...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 25047 | Method: dropMessage | `ได้รับ Symbol of Brilliant Honor แล้ว\r\nสามารถ...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 25049 | Method: dropMessage | `[Notice] ได้รับ Symbol of Brilliant Honor (รางว...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 25050 | Method: dropMessage | `ได้รับ Symbol of Brilliant Honor (รางวัลเลเวล 2...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 25297 | Method: dropMessage | `ได้รับแต้ม` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 25498 | Method: dropMessage | `พลังแรกที่ซ่อนอยู่ในอาวุธ Genesis ตื่นขึ้นแล้ว ...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 26205 | Method: dropMessage | `จำนวน Extreme Point ที่กู้คืน ` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 26206 | Method: dropMessage | ` 만큼 강림 포인트로 전환 대상입니다. 3월 27일부터 토요일 자정기준 1주일 마다 ...` | Pending |
| `src\objects\users\MapleCharacter.java` | 26897 | Method: dropMessage | `ได้รับ Ganglim Reverse Membership SP 1 แต้ม ตรว...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 27368 | Method: dropMessage | `ได้รับรางวัล Hot Time แล้ว สามารถรับได้ที่ Mapl...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 27380 | Method: dropMessage | `พื้นที่ล่า Fatigue ไม่สามารถเข้าปาร์ตี้เกิน 3 ค...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 27386 | Method: dropMessage | `หมดความเหนื่อยล้า จึงกลับเมือง` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 27398 | Method: dropMessage | `ความเหนื่อยล้าลดลง ความเหนื่อยล้าที่เหลือ : ` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 28039 | Method: dropMessage | `ได้รับ Neo Core เกินจำนวนที่กำหนดต่อวัน จึงไม่ไ...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 28062 | Method: dropMessage | `[알림] ` | Pending |
| `src\objects\users\MapleCharacter.java` | 28062 | Method: dropMessage | ` ได้รับ Neo Core ` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 28243 | Method: dropMessage | `ปรับเพิ่มค่าสถานะของ Symbol ที่เสริมพลังแล้ว [A...` | Already Thai |
| `src\objects\users\MapleCharacter.java` | 28383 | Method: dropMessage | `ปรับค่า Arcane Symbol ให้เป็นปกติเรียบร้อยแล้ว` | Already Thai |
| `src\objects\users\MapleTrade.java` | 236 | Method: dropMessage | `Equipment Trace ไม่สามารถแลกเปลี่ยนได้` | Already Thai |
| `src\objects\users\PlayerStats.java` | 412 | Method: dropMessage | `คุณรอดพ้นจากความตายด้วยผลของสกิล` | Already Thai |
| `src\objects\users\PlayerStats.java` | 4626 | Method: dropMessage | `สกิลเลเวลอัพ: ` | Already Thai |
| `src\objects\users\jobs\CommonJob.java` | 249 | Method: dropMessage | `คุณถูกย้ายกลับเมืองเนื่องจากใช้สกิล Bee Yeon เก...` | Already Thai |
| `src\objects\users\jobs\CommonJob.java` | 467 | Method: dropMessage | `ไอเทมไม่เพียงพอ` | Already Thai |
| `src\objects\users\jobs\CommonJob.java` | 3182 | Method: dropMessage | ` roll result: ` | Pending |
| `src\objects\users\jobs\CommonJob.java` | 3182 | Method: dropMessage | `. You did not receive any effect.` | Pending |
| `src\objects\users\jobs\CommonJob.java` | 3213 | Method: dropMessage | ` triggered effect [` | Pending |
| `src\objects\users\jobs\CommonJob.java` | 3213 | Method: dropMessage | `].` | Pending |
| `src\objects\users\jobs\adventure\magician\ArcMageFP.java` | 217 | Method: dropMessage | `typeLeft x: ` | Pending |
| `src\objects\users\jobs\adventure\magician\ArcMageFP.java` | 238 | Method: dropMessage | `typeRight x: ` | Pending |
| `src\objects\users\jobs\adventure\thief\Shadower.java` | 421 | Method: dropMessage | `PickPocketX : ` | Pending |
| `src\objects\users\jobs\adventure\thief\Shadower.java` | 421 | Method: dropMessage | ` // Map Meso Count : ` | Pending |
| `src\objects\users\jobs\adventure\thief\Shadower.java` | 421 | Method: dropMessage | ` // Buffed : ` | Pending |
| `src\objects\users\jobs\flora\Ark.java` | 666 | Method: dropMessage | `Niya seems the same. I comforted Niya and talke...` | Pending |
| `src\objects\users\jobs\flora\Ark.java` | 699 | Method: dropMessage | `Wei ดูเหมือนจะทะเลาะกับ Barkbark ตามปกติ คุยเรื...` | Already Thai |
| `src\objects\users\jobs\flora\Ark.java` | 704 | Method: dropMessage | `Barkbark seems the same. Talked about Barkbark'...` | Pending |
| `src\objects\users\jobs\flora\Ark.java` | 712 | Method: dropMessage | `Mar seems to be growing up bravely. Talked abou...` | Pending |
| `src\objects\users\jobs\hero\Phantom.java` | 798 | Method: dropMessage | `คุณขโมยสกิลนี้ไปแล้ว กรุณาลบออกแล้วลองใหม่` | Already Thai |
| `src\objects\users\jobs\hero\Phantom.java` | 800 | Method: dropMessage | `Skill ID: ` | Pending |
| `src\objects\users\jobs\hero\Phantom.java` | 822 | Method: dropMessage | `Please try again in a moment.` | Pending |
| `src\objects\users\jobs\hero\Phantom.java` | 900 | Method: dropMessage | `slot : ` | Pending |
| `src\objects\users\jobs\hero\Phantom.java` | 915 | Method: dropMessage | `Please try again in a moment.` | Pending |
| `src\objects\users\jobs\hero\Phantom.java` | 943 | Method: dropMessage | `คุณเปลี่ยนสกิลผ่านการจัดการสกิล มีคูลดาวน์ 30 ว...` | Already Thai |
| `src\objects\users\jobs\hero\Phantom.java` | 946 | Method: dropMessage | `Please try again in a moment.` | Pending |
| `src\objects\users\jobs\hero\Phantom.java` | 971 | Method: dropMessage | `Please try again in a moment.` | Pending |
| `src\objects\users\skills\DamageParse.java` | 617 | Method: dropMessage | ` : ` | Pending |
| `src\objects\users\skills\DamageParse.java` | 617 | Method: dropMessage | ` // ` | Pending |
| `src\objects\users\skills\DamageParse.java` | 883 | Method: dropMessage | ` : ` | Pending |
| `src\objects\users\skills\DamageParse.java` | 883 | Method: dropMessage | ` // ` | Pending |
| `src\objects\users\skills\DamageParse.java` | 1153 | Method: dropMessage | ` : ` | Pending |
| `src\objects\users\skills\DamageParse.java` | 1153 | Method: dropMessage | ` // ` | Pending |
| `src\objects\users\skills\DamageParse.java` | 1709 | Method: dropMessage | ` : ` | Pending |
| `src\objects\users\skills\DamageParse.java` | 1709 | Method: dropMessage | ` // ` | Pending |
| `src\objects\users\skills\DamageParse.java` | 1752 | Method: dropMessage | ` : ` | Pending |
| `src\objects\users\skills\DamageParse.java` | 1752 | Method: dropMessage | ` // ` | Pending |
| `src\objects\users\stats\SecondaryStatEffect.java` | 3150 | Method: dropMessage | `ไม่สามารถใช้สกิลนี้ในเมืองได้` | Already Thai |
| `src\objects\users\stats\SecondaryStatEffect.java` | 3785 | Method: dropMessage | `ไม่สามารถเปิดประตูได้เนื่องจากประตูในเมืองเต็มแล้ว` | Already Thai |
| `src\objects\utils\AdminClient.java` | 1330 | Method: dropMessage | `ถูกย้ายไปที่จัตุรัสโดย GM` | Already Thai |
| `src\objects\utils\AdminClient.java` | 2101 | Method: dropMessage | `[Notice] ไอเทมจาก GM มาถึงแล้ว กรุณารับที่ Mapl...` | Already Thai |
| `src\objects\utils\AdminClient.java` | 2286 | Method: dropMessage | `[Notice] ไอเทมจาก GM มาถึงแล้ว กรุณารับที่ Mapl...` | Already Thai |
| `src\objects\utils\AdminClient.java` | 2396 | Method: chatMsg | `[` | Pending |
| `src\objects\utils\AutobanManager.java` | 31 | Method: dropMessage | `[แจ้งเตือน] GM ไม่อยู่ในเงื่อนไขการแบนอัตโนมัติ : ` | Already Thai |
| `src\objects\utils\AutobanManager.java` | 75 | Method: dropMessage | `[แจ้งเตือน] GM ไม่อยู่ในเงื่อนไขการแบนอัตโนมัติ : ` | Already Thai |
| `src\objects\utils\CMDCommand.java` | 97 | Method: dropMessage | `[Notice사항]\r\n` | Pending |
| `src\scripting\AbstractPlayerInteraction.java` | 105 | Method: dropMessage | `กำลังย้ายไปแชนแนล ` | Already Thai |
| `src\scripting\AbstractPlayerInteraction.java` | 105 | Method: dropMessage | `.` | Pending |
| `src\scripting\EventManager.java` | 287 | Method: dropMessage | `The squad has less than ` | Pending |
| `src\scripting\EventManager.java` | 292 | Method: dropMessage | `The squad requires members from every type of job.` | Pending |
| `src\scripting\NPCConversationManager.java` | 1869 | Method: dropMessage | `กิลด์ของคุณได้รับ ` | Already Thai |
| `src\scripting\NPCConversationManager.java` | 1869 | Method: dropMessage | ` buff.` | Pending |
| `src\scripting\NPCConversationManager.java` | 2198 | Method: dropMessage | `คู่รักหย่าร้างกับคุณ` | Already Thai |
| `src\scripting\NPCConversationManager.java` | 2956 | Method: dropMessage | `ผลการประเมินอาวุธ : Grade(` | Already Thai |
| `src\scripting\NPCConversationManager.java` | 3293 | Method: dropMessage | `ไม่สามารถอัพเกรดได้อีก` | Already Thai |
| `src\scripting\NPCConversationManager.java` | 3298 | Method: dropMessage | `Cannot upgrade further.` | Pending |
| `src\scripting\NPCConversationManager.java` | 3362 | Method: dropMessage | `Storage slots increased. Current storage slots: ` | Pending |
| `src\scripting\NPCConversationManager.java` | 3365 | Method: dropMessage | `ไม่สามารถเพิ่มช่องได้อีก` | Already Thai |
| `src\scripting\NPCConversationManager.java` | 3374 | Method: dropMessage | `Pendant slot expansion is already active.` | Pending |
| `src\scripting\NPCConversationManager.java` | 3471 | Method: dropMessage | `Only Explorers can change jobs freely.` | Pending |
| `src\scripting\NPCConversationManager.java` | 3475 | Method: dropMessage | `Please unequip all V Matrix cores before attemp...` | Pending |
| `src\scripting\NPCConversationManager.java` | 3488 | Method: dropMessage | `Thief ต้องถอดอาวุธรอง/โล่/ใบมีดออกก่อน` | Already Thai |
| `src\scripting\NPCConversationManager.java` | 3670 | Method: dropMessage | `เสีย 4,000 ` | Already Thai |
| `src\scripting\NPCConversationManager.java` | 3670 | Method: dropMessage | `.` | Pending |
| `src\scripting\NPCConversationManager.java` | 3838 | Method: dropMessage | `ได้รับ ` | Already Thai |
| `src\scripting\NPCConversationManager.java` | 3838 | Method: dropMessage | ` points.` | Pending |
| `src\scripting\NPCConversationManager.java` | 5684 | Method: dropMessage | `มีผู้เข้าร่วมมากเกินไปในขณะนี้ กรุณาลองใหม่อีกค...` | Already Thai |
| `src\scripting\NPCConversationManager.java` | 5696 | Method: dropMessage | `มีผู้เข้าร่วมมากเกินไปในขณะนี้ กรุณาลองใหม่อีกค...` | Already Thai |
| `src\scripting\NPCConversationManager.java` | 5708 | Method: dropMessage | `มีผู้เข้าร่วมมากเกินไปในขณะนี้ กรุณาลองใหม่อีกค...` | Already Thai |
| `src\scripting\NPCConversationManager.java` | 5774 | Method: dropMessage | `ได้รับ ` | Already Thai |
| `src\scripting\NPCConversationManager.java` | 5774 | Method: dropMessage | ` ` | Pending |
| `src\scripting\NPCConversationManager.java` | 5774 | Method: dropMessage | `.` | Pending |
| `src\scripting\NPCScriptManager.java` | 134 | Method: dropMessage | `Error! ` | Pending |
| `src\scripting\ReactorActionManager.java` | 225 | Method: dropMessage | `Fatigue ไม่พอที่จะทำ ` | Already Thai |
| `src\scripting\ReactorActionManager.java` | 225 | Method: dropMessage | `.` | Pending |
| `src\scripting\ReactorScriptManager.java` | 30 | Method: dropMessage | `Reactor : ` | Pending |
| `src\scripting\ReactorScriptManager.java` | 55 | Method: dropMessage | `Reactor : ` | Pending |
| `src\scripting\newscripting\ScriptManager.java` | 236 | Method: dropMessage | `Script reset is already in progress.` | Pending |
| `src\scripting\newscripting\ScriptManager.java` | 243 | Method: dropMessage | `Starting script parsing.` | Pending |
| `src\scripting\newscripting\ScriptManager.java` | 251 | Method: dropMessage | `[Script] รวม ` | Already Thai |

## 2. Developer/Log Messages (English)
> Korean found in logs or comments.

| File | Line | Context | Original | Proposed Target (English) |
| --- | --- | --- | --- | --- |
| `src\network\game\GameServer.java` | 287 | Log: println | `Channel 캐릭터를 저장합니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 1745 | Log: println | `All data saved. (완료 시간 : ` | Pending |

## 3. Unclassified Korean Strings
> Korean text found outside known method contexts. Requires manual review or heuristic check.

| File | Line | Context | Original | Proposed Target (Context Dependent) |
| --- | --- | --- | --- | --- |
| `src\network\auction\processors\AuctionHandler.java` | 889 | Unknown (Method: append) | `이미 수령한 대금을 다시 수령 시도 : 아이템 (` | Pending |
| `src\network\auction\processors\AuctionHandler.java` | 889 | Unknown (Method: 아이템) | `) [이름 : ` | Pending |
| `src\network\auction\processors\AuctionHandler.java` | 1031 | Unknown (Method: append) | `이미 수령한 아이템을 다시 수령 시도 : 아이템 (` | Pending |
| `src\network\auction\processors\AuctionHandler.java` | 1031 | Unknown (Method: 아이템) | `) [이름 : ` | Pending |
| `src\network\auction\processors\AuctionHandler.java` | 1064 | Unknown (Method: append) | `이미 수령한 아이템을 다시 수령 시도 : 아이템 (` | Pending |
| `src\network\auction\processors\AuctionHandler.java` | 1064 | Unknown (Method: 아이템) | `) [이름 : ` | Pending |
| `src\network\auction\processors\AuctionHandler.java` | 1103 | Unknown (Method: append) | `이미 반환한 아이템을 다시 반환 시도 : 아이템 (` | Pending |
| `src\network\auction\processors\AuctionHandler.java` | 1103 | Unknown (Method: 아이템) | `) [이름 : ` | Pending |
| `src\network\auction\processors\AuctionHandler.java` | 1147 | Unknown (Method: append) | `경매장 아이템 수령 (캐릭터 : ` | Pending |
| `src\network\auction\processors\AuctionHandler.java` | 1149 | Unknown (Method: append) | `, 계정 : ` | Pending |
| `src\network\auction\processors\AuctionHandler.java` | 1154 | Unknown (Method: append) | `, 아이템 : ` | Pending |
| `src\network\auction\processors\AuctionHandler.java` | 1158 | Unknown (Method: append) | `개, 판매자 : ` | Pending |
| `src\network\auction\processors\AuctionHandler.java` | 1167 | Unknown (Method: append) | `경매장 아이템 수령 (계정 : ` | Pending |
| `src\network\auction\processors\AuctionHandler.java` | 1169 | Unknown (Method: append) | `, 캐릭터 : ` | Pending |
| `src\network\auction\processors\AuctionHandler.java` | 1171 | Unknown (Method: append) | `, 아이템 : ` | Pending |
| `src\network\auction\processors\AuctionHandler.java` | 1175 | Unknown (Method: append) | `개` | Pending |
| `src\network\auction\processors\AuctionHandler.java` | 1177 | Unknown (Method: append) | ` (정보 : ` | Pending |
| `src\network\auction\processors\AuctionHandler.java` | 1181 | Unknown (Method: append) | `), 판매자 : ` | Pending |
| `src\network\center\Center.java` | 747 | Unknown (Method: SimpleDateFormat) | `yyyy년 MM월 dd일 HH시 mm분` | Pending |
| `src\network\center\Center.java` | 769 | Unknown (Method: getName) | `이(가) 핫타임으로 지급되었습니다.` | Pending |
| `src\network\center\Center.java` | 922 | Unknown (Method: equals) | `일반` | Pending |
| `src\network\center\Center.java` | 922 | Unknown (Method: equals) | `이벤트 참여` | Pending |
| `src\network\center\Center.java` | 922 | Unknown (Method: equals) | `신년 이벤트` | Pending |
| `src\network\center\Center.java` | 922 | Unknown (Method: equals) | `클스마스 이벤트` | Pending |
| `src\network\center\Center.java` | 923 | Unknown (Method: equals) | `보너스이벤트` | Pending |
| `src\network\center\Center.java` | 924 | Unknown (Method: equals) | `초심자 패키지` | Pending |
| `src\network\center\Center.java` | 925 | Unknown (Method: contains) | `설날` | Pending |
| `src\network\center\Center.java` | 926 | Unknown (Method: contains) | `어린이날` | Pending |
| `src\network\center\Center.java` | 927 | Unknown (Method: contains) | `추석` | Pending |
| `src\network\center\Center.java` | 928 | Unknown (Method: contains) | `가정의달` | Pending |
| `src\network\center\Center.java` | 929 | Unknown (Method: contains) | `3주년` | Pending |
| `src\network\center\Center.java` | 930 | Unknown (Method: contains) | `상시패키지` | Pending |
| `src\network\center\Center.java` | 931 | Unknown (Method: contains) | `크리스마스` | Pending |
| `src\network\center\Center.java` | 933 | Unknown (Method: contains) | `5월` | Pending |
| `src\network\center\Center.java` | 957 | Unknown (Method: equals) | `초심자 패키지` | Pending |
| `src\network\center\Center.java` | 967 | Unknown (Method: contains) | `가정의달` | Pending |
| `src\network\center\Center.java` | 968 | Unknown (Method: contains) | `추석` | Pending |
| `src\network\center\Center.java` | 969 | Unknown (Method: contains) | `3주년` | Pending |
| `src\network\center\Center.java` | 970 | Unknown (Method: contains) | `상시패키지` | Pending |
| `src\network\center\Center.java` | 971 | Unknown (Method: contains) | `크리스마스` | Pending |
| `src\network\center\Center.java` | 973 | Unknown (Method: contains) | `5월` | Pending |
| `src\network\center\Center.java` | 991 | Unknown (Method: close) | `초심자패키지` | Pending |
| `src\network\center\Center.java` | 993 | Unknown (Method: contains) | `설날` | Pending |
| `src\network\center\Center.java` | 995 | Unknown (Method: equals) | `설날C` | Pending |
| `src\network\center\Center.java` | 1000 | Unknown (Method: contains) | `어린이날` | Pending |
| `src\network\center\Center.java` | 1003 | Unknown (Method: contains) | `가정의달` | Pending |
| `src\network\center\Center.java` | 1005 | Unknown (Method: equals) | `가정의달C` | Pending |
| `src\network\center\Center.java` | 1007 | Unknown (Method: equals) | `가정의달B` | Pending |
| `src\network\center\Center.java` | 1009 | Unknown (Method: equals) | `가정의달A` | Pending |
| `src\network\center\Center.java` | 1011 | Unknown (Method: equals) | `가정의달S` | Pending |
| `src\network\center\Center.java` | 1013 | Unknown (Method: equals) | `가정의달SS` | Pending |
| `src\network\center\Center.java` | 1015 | Unknown (Method: equals) | `가정의달SSS` | Pending |
| `src\network\center\Center.java` | 1018 | Unknown (Method: contains) | `추석` | Pending |
| `src\network\center\Center.java` | 1020 | Unknown (Method: equals) | `추석패키지I` | Pending |
| `src\network\center\Center.java` | 1022 | Unknown (Method: equals) | `추석패키지II` | Pending |
| `src\network\center\Center.java` | 1024 | Unknown (Method: equals) | `추석패키지III` | Pending |
| `src\network\center\Center.java` | 1026 | Unknown (Method: equals) | `추석패키지IV` | Pending |
| `src\network\center\Center.java` | 1029 | Unknown (Method: contains) | `3주년` | Pending |
| `src\network\center\Center.java` | 1031 | Unknown (Method: equals) | `3주년패키지I` | Pending |
| `src\network\center\Center.java` | 1033 | Unknown (Method: equals) | `3주년패키지II` | Pending |
| `src\network\center\Center.java` | 1035 | Unknown (Method: equals) | `3주년패키지III` | Pending |
| `src\network\center\Center.java` | 1038 | Unknown (Method: contains) | `크리스마스` | Pending |
| `src\network\center\Center.java` | 1040 | Unknown (Method: equals) | `크리스마스패키지1` | Pending |
| `src\network\center\Center.java` | 1042 | Unknown (Method: equals) | `크리스마스패키지2` | Pending |
| `src\network\center\Center.java` | 1044 | Unknown (Method: equals) | `크리스마스패키지3` | Pending |
| `src\network\center\Center.java` | 1046 | Unknown (Method: equals) | `크리스마스패키지4` | Pending |
| `src\network\center\Center.java` | 1051 | Unknown (Method: equals) | `2023패키지1` | Pending |
| `src\network\center\Center.java` | 1053 | Unknown (Method: equals) | `2023패키지2` | Pending |
| `src\network\center\Center.java` | 1055 | Unknown (Method: equals) | `2023패키지3` | Pending |
| `src\network\center\Center.java` | 1057 | Unknown (Method: equals) | `2023패키지4` | Pending |
| `src\network\center\Center.java` | 1059 | Unknown (Method: equals) | `2023패키지5` | Pending |
| `src\network\center\Center.java` | 1061 | Unknown (Method: equals) | `2023패키지6` | Pending |
| `src\network\center\Center.java` | 1063 | Unknown (Method: equals) | `2023패키지7` | Pending |
| `src\network\center\Center.java` | 1065 | Unknown (Method: equals) | `2023패키지8` | Pending |
| `src\network\center\Center.java` | 1067 | Unknown (Method: equals) | `2023패키지9` | Pending |
| `src\network\center\Center.java` | 1069 | Unknown (Method: equals) | `2023패키지10` | Pending |
| `src\network\center\Center.java` | 1071 | Unknown (Method: equals) | `2023패키지11` | Pending |
| `src\network\center\Center.java` | 1074 | Unknown (Method: contains) | `5월` | Pending |
| `src\network\center\Center.java` | 1076 | Unknown (Method: equals) | `5월패키지1` | Pending |
| `src\network\center\Center.java` | 1078 | Unknown (Method: equals) | `5월패키지2` | Pending |
| `src\network\center\Center.java` | 1080 | Unknown (Method: equals) | `5월패키지3` | Pending |
| `src\network\center\Center.java` | 1082 | Unknown (Method: equals) | `5월패키지4` | Pending |
| `src\network\center\Center.java` | 1084 | Unknown (Method: equals) | `5월패키지5` | Pending |
| `src\network\center\Center.java` | 1086 | Unknown (Method: equals) | `5월패키지6` | Pending |
| `src\network\center\Center.java` | 1088 | Unknown (Method: equals) | `5월패키지7` | Pending |
| `src\network\center\Center.java` | 1090 | Unknown (Method: equals) | `5월패키지8` | Pending |
| `src\network\center\Center.java` | 1092 | Unknown (Method: equals) | `5월패키지9` | Pending |
| `src\network\center\Center.java` | 1094 | Unknown (Method: equals) | `5월패키지10` | Pending |
| `src\network\center\Center.java` | 1096 | Unknown (Method: equals) | `5월패키지11` | Pending |
| `src\network\center\Center.java` | 1099 | Unknown (Method: contains) | `상시패키지` | Pending |
| `src\network\center\Center.java` | 1102 | Unknown (Method: equals) | `상시패키지2` | Pending |
| `src\network\center\Center.java` | 1129 | Unknown (Method: equals) | `초심자 패키지` | Pending |
| `src\network\center\Center.java` | 1135 | Unknown (Method: equals) | `설날A` | Pending |
| `src\network\center\Center.java` | 1142 | Unknown (Method: equals) | `설날B` | Pending |
| `src\network\center\Center.java` | 1149 | Unknown (Method: equals) | `설날C` | Pending |
| `src\network\center\Center.java` | 1156 | Unknown (Method: equals) | `어린이날` | Pending |
| `src\network\center\Center.java` | 1164 | Unknown (Method: equals) | `가정의달C` | Pending |
| `src\network\center\Center.java` | 1171 | Unknown (Method: equals) | `가정의달B` | Pending |
| `src\network\center\Center.java` | 1178 | Unknown (Method: equals) | `가정의달A` | Pending |
| `src\network\center\Center.java` | 1185 | Unknown (Method: equals) | `가정의달S` | Pending |
| `src\network\center\Center.java` | 1192 | Unknown (Method: equals) | `가정의달SS` | Pending |
| `src\network\center\Center.java` | 1199 | Unknown (Method: equals) | `가정의달SSS` | Pending |
| `src\network\center\Center.java` | 1206 | Unknown (Method: equals) | `상시패키지1` | Pending |
| `src\network\center\Center.java` | 1213 | Unknown (Method: equals) | `상시패키지2` | Pending |
| `src\network\center\Center.java` | 1220 | Unknown (Method: equals) | `추석패키지I` | Pending |
| `src\network\center\Center.java` | 1227 | Unknown (Method: equals) | `추석패키지II` | Pending |
| `src\network\center\Center.java` | 1234 | Unknown (Method: equals) | `추석패키지III` | Pending |
| `src\network\center\Center.java` | 1241 | Unknown (Method: equals) | `추석패키지IV` | Pending |
| `src\network\center\Center.java` | 1248 | Unknown (Method: equals) | `추석패키지IV` | Pending |
| `src\network\center\Center.java` | 1255 | Unknown (Method: equals) | `3주년패키지I` | Pending |
| `src\network\center\Center.java` | 1262 | Unknown (Method: equals) | `3주년패키지II` | Pending |
| `src\network\center\Center.java` | 1269 | Unknown (Method: equals) | `3주년패키지III` | Pending |
| `src\network\center\Center.java` | 1276 | Unknown (Method: equals) | `크리스마스패키지1` | Pending |
| `src\network\center\Center.java` | 1283 | Unknown (Method: equals) | `크리스마스패키지2` | Pending |
| `src\network\center\Center.java` | 1289 | Unknown (Method: equals) | `크리스마스패키지3` | Pending |
| `src\network\center\Center.java` | 1296 | Unknown (Method: equals) | `크리스마스패키지4` | Pending |
| `src\network\center\Center.java` | 1303 | Unknown (Method: equals) | `2023패키지1` | Pending |
| `src\network\center\Center.java` | 1310 | Unknown (Method: equals) | `2023패키지2` | Pending |
| `src\network\center\Center.java` | 1317 | Unknown (Method: equals) | `2023패키지3` | Pending |
| `src\network\center\Center.java` | 1324 | Unknown (Method: equals) | `2023패키지4` | Pending |
| `src\network\center\Center.java` | 1331 | Unknown (Method: equals) | `2023패키지5` | Pending |
| `src\network\center\Center.java` | 1337 | Unknown (Method: equals) | `2023패키지6` | Pending |
| `src\network\center\Center.java` | 1344 | Unknown (Method: equals) | `2023패키지7` | Pending |
| `src\network\center\Center.java` | 1351 | Unknown (Method: equals) | `2023패키지8` | Pending |
| `src\network\center\Center.java` | 1358 | Unknown (Method: equals) | `2023패키지9` | Pending |
| `src\network\center\Center.java` | 1365 | Unknown (Method: equals) | `2023패키지10` | Pending |
| `src\network\center\Center.java` | 1372 | Unknown (Method: equals) | `2023패키지11` | Pending |
| `src\network\center\Center.java` | 1379 | Unknown (Method: equals) | `5월패키지1` | Pending |
| `src\network\center\Center.java` | 1386 | Unknown (Method: equals) | `5월패키지2` | Pending |
| `src\network\center\Center.java` | 1393 | Unknown (Method: equals) | `5월패키지3` | Pending |
| `src\network\center\Center.java` | 1400 | Unknown (Method: equals) | `5월패키지4` | Pending |
| `src\network\center\Center.java` | 1407 | Unknown (Method: equals) | `5월패키지5` | Pending |
| `src\network\center\Center.java` | 1414 | Unknown (Method: equals) | `5월패키지6` | Pending |
| `src\network\center\Center.java` | 1421 | Unknown (Method: equals) | `5월패키지7` | Pending |
| `src\network\center\Center.java` | 1428 | Unknown (Method: equals) | `5월패키지8` | Pending |
| `src\network\center\Center.java` | 1435 | Unknown (Method: equals) | `5월패키지9` | Pending |
| `src\network\center\Center.java` | 1442 | Unknown (Method: equals) | `5월패키지10` | Pending |
| `src\network\center\Center.java` | 1449 | Unknown (Method: equals) | `5월패키지11` | Pending |
| `src\network\center\Center.java` | 1468 | Unknown (Method: equals) | `초심자 패키지` | Pending |
| `src\network\center\Center.java` | 1470 | Unknown (Method: equals) | `설날A` | Pending |
| `src\network\center\Center.java` | 1473 | Unknown (Method: equals) | `설날B` | Pending |
| `src\network\center\Center.java` | 1476 | Unknown (Method: equals) | `설날C` | Pending |
| `src\network\center\Center.java` | 1479 | Unknown (Method: equals) | `어린이날` | Pending |
| `src\network\center\Center.java` | 1483 | Unknown (Method: equals) | `가정의달C` | Pending |
| `src\network\center\Center.java` | 1486 | Unknown (Method: equals) | `가정의달B` | Pending |
| `src\network\center\Center.java` | 1489 | Unknown (Method: equals) | `가정의달A` | Pending |
| `src\network\center\Center.java` | 1492 | Unknown (Method: equals) | `가정의달S` | Pending |
| `src\network\center\Center.java` | 1495 | Unknown (Method: equals) | `가정의달SS` | Pending |
| `src\network\center\Center.java` | 1498 | Unknown (Method: equals) | `가정의달SSS` | Pending |
| `src\network\center\Center.java` | 1501 | Unknown (Method: equals) | `상시패키지1` | Pending |
| `src\network\center\Center.java` | 1505 | Unknown (Method: equals) | `상시패키지2` | Pending |
| `src\network\center\Center.java` | 1509 | Unknown (Method: equals) | `추석패키지I` | Pending |
| `src\network\center\Center.java` | 1513 | Unknown (Method: equals) | `추석패키지II` | Pending |
| `src\network\center\Center.java` | 1517 | Unknown (Method: equals) | `추석패키지III` | Pending |
| `src\network\center\Center.java` | 1521 | Unknown (Method: equals) | `추석패키지IV` | Pending |
| `src\network\center\Center.java` | 1525 | Unknown (Method: equals) | `3주년패키지I` | Pending |
| `src\network\center\Center.java` | 1529 | Unknown (Method: equals) | `3주년패키지II` | Pending |
| `src\network\center\Center.java` | 1533 | Unknown (Method: equals) | `3주년패키지III` | Pending |
| `src\network\center\Center.java` | 1537 | Unknown (Method: equals) | `크리스마스패키지1` | Pending |
| `src\network\center\Center.java` | 1541 | Unknown (Method: equals) | `크리스마스패키지2` | Pending |
| `src\network\center\Center.java` | 1545 | Unknown (Method: equals) | `크리스마스패키지3` | Pending |
| `src\network\center\Center.java` | 1549 | Unknown (Method: equals) | `크리스마스패키지4` | Pending |
| `src\network\center\Center.java` | 1553 | Unknown (Method: equals) | `2023패키지1` | Pending |
| `src\network\center\Center.java` | 1557 | Unknown (Method: equals) | `2023패키지2` | Pending |
| `src\network\center\Center.java` | 1561 | Unknown (Method: equals) | `2023패키지3` | Pending |
| `src\network\center\Center.java` | 1565 | Unknown (Method: equals) | `2023패키지4` | Pending |
| `src\network\center\Center.java` | 1569 | Unknown (Method: equals) | `2023패키지5` | Pending |
| `src\network\center\Center.java` | 1573 | Unknown (Method: equals) | `2023패키지6` | Pending |
| `src\network\center\Center.java` | 1577 | Unknown (Method: equals) | `2023패키지7` | Pending |
| `src\network\center\Center.java` | 1581 | Unknown (Method: equals) | `2023패키지8` | Pending |
| `src\network\center\Center.java` | 1585 | Unknown (Method: equals) | `2023패키지9` | Pending |
| `src\network\center\Center.java` | 1589 | Unknown (Method: equals) | `2023패키지10` | Pending |
| `src\network\center\Center.java` | 1593 | Unknown (Method: equals) | `2023패키지11` | Pending |
| `src\network\center\Center.java` | 1597 | Unknown (Method: equals) | `5월패키지1` | Pending |
| `src\network\center\Center.java` | 1601 | Unknown (Method: equals) | `5월패키지2` | Pending |
| `src\network\center\Center.java` | 1605 | Unknown (Method: equals) | `5월패키지3` | Pending |
| `src\network\center\Center.java` | 1609 | Unknown (Method: equals) | `5월패키지4` | Pending |
| `src\network\center\Center.java` | 1613 | Unknown (Method: equals) | `5월패키지5` | Pending |
| `src\network\center\Center.java` | 1617 | Unknown (Method: equals) | `5월패키지6` | Pending |
| `src\network\center\Center.java` | 1621 | Unknown (Method: equals) | `5월패키지7` | Pending |
| `src\network\center\Center.java` | 1625 | Unknown (Method: equals) | `5월패키지8` | Pending |
| `src\network\center\Center.java` | 1629 | Unknown (Method: equals) | `5월패키지9` | Pending |
| `src\network\center\Center.java` | 1633 | Unknown (Method: equals) | `5월패키지10` | Pending |
| `src\network\center\Center.java` | 1637 | Unknown (Method: equals) | `5월패키지11` | Pending |
| `src\network\center\Center.java` | 1653 | Unknown (Method: equals) | `초심자 패키지` | Pending |
| `src\network\center\Center.java` | 1656 | Unknown (Method: equals) | `설날A` | Pending |
| `src\network\center\Center.java` | 1660 | Unknown (Method: equals) | `설날B` | Pending |
| `src\network\center\Center.java` | 1664 | Unknown (Method: equals) | `설날C` | Pending |
| `src\network\center\Center.java` | 1668 | Unknown (Method: equals) | `어린이날` | Pending |
| `src\network\center\Center.java` | 1673 | Unknown (Method: equals) | `가정의달C` | Pending |
| `src\network\center\Center.java` | 1677 | Unknown (Method: equals) | `가정의달B` | Pending |
| `src\network\center\Center.java` | 1681 | Unknown (Method: equals) | `가정의달A` | Pending |
| `src\network\center\Center.java` | 1685 | Unknown (Method: equals) | `가정의달S` | Pending |
| `src\network\center\Center.java` | 1689 | Unknown (Method: equals) | `가정의달SS` | Pending |
| `src\network\center\Center.java` | 1693 | Unknown (Method: equals) | `가정의달SSS` | Pending |
| `src\network\center\Center.java` | 1697 | Unknown (Method: equals) | `상시패키지1` | Pending |
| `src\network\center\Center.java` | 1701 | Unknown (Method: equals) | `상시패키지2` | Pending |
| `src\network\center\Center.java` | 1705 | Unknown (Method: equals) | `추석패키지I` | Pending |
| `src\network\center\Center.java` | 1709 | Unknown (Method: equals) | `추석패키지II` | Pending |
| `src\network\center\Center.java` | 1713 | Unknown (Method: equals) | `추석패키지III` | Pending |
| `src\network\center\Center.java` | 1717 | Unknown (Method: equals) | `추석패키지IV` | Pending |
| `src\network\center\Center.java` | 1721 | Unknown (Method: equals) | `3주년패키지I` | Pending |
| `src\network\center\Center.java` | 1725 | Unknown (Method: equals) | `3주년패키지II` | Pending |
| `src\network\center\Center.java` | 1729 | Unknown (Method: equals) | `3주년패키지III` | Pending |
| `src\network\center\Center.java` | 1733 | Unknown (Method: equals) | `크리스마스패키지1` | Pending |
| `src\network\center\Center.java` | 1737 | Unknown (Method: equals) | `크리스마스패키지2` | Pending |
| `src\network\center\Center.java` | 1741 | Unknown (Method: equals) | `크리스마스패키지3` | Pending |
| `src\network\center\Center.java` | 1745 | Unknown (Method: equals) | `크리스마스패키지4` | Pending |
| `src\network\center\Center.java` | 1749 | Unknown (Method: equals) | `2023패키지1` | Pending |
| `src\network\center\Center.java` | 1753 | Unknown (Method: equals) | `2023패키지2` | Pending |
| `src\network\center\Center.java` | 1757 | Unknown (Method: equals) | `2023패키지3` | Pending |
| `src\network\center\Center.java` | 1761 | Unknown (Method: equals) | `2023패키지4` | Pending |
| `src\network\center\Center.java` | 1765 | Unknown (Method: equals) | `2023패키지5` | Pending |
| `src\network\center\Center.java` | 1769 | Unknown (Method: equals) | `2023패키지6` | Pending |
| `src\network\center\Center.java` | 1773 | Unknown (Method: equals) | `2023패키지7` | Pending |
| `src\network\center\Center.java` | 1777 | Unknown (Method: equals) | `2023패키지8` | Pending |
| `src\network\center\Center.java` | 1781 | Unknown (Method: equals) | `2023패키지9` | Pending |
| `src\network\center\Center.java` | 1785 | Unknown (Method: equals) | `2023패키지10` | Pending |
| `src\network\center\Center.java` | 1789 | Unknown (Method: equals) | `2023패키지11` | Pending |
| `src\network\center\Center.java` | 1793 | Unknown (Method: equals) | `5월패키지1` | Pending |
| `src\network\center\Center.java` | 1797 | Unknown (Method: equals) | `5월패키지2` | Pending |
| `src\network\center\Center.java` | 1801 | Unknown (Method: equals) | `5월패키지3` | Pending |
| `src\network\center\Center.java` | 1805 | Unknown (Method: equals) | `5월패키지4` | Pending |
| `src\network\center\Center.java` | 1809 | Unknown (Method: equals) | `5월패키지5` | Pending |
| `src\network\center\Center.java` | 1813 | Unknown (Method: equals) | `5월패키지6` | Pending |
| `src\network\center\Center.java` | 1817 | Unknown (Method: equals) | `5월패키지7` | Pending |
| `src\network\center\Center.java` | 1821 | Unknown (Method: equals) | `5월패키지8` | Pending |
| `src\network\center\Center.java` | 1825 | Unknown (Method: equals) | `5월패키지9` | Pending |
| `src\network\center\Center.java` | 1829 | Unknown (Method: equals) | `5월패키지10` | Pending |
| `src\network\center\Center.java` | 1833 | Unknown (Method: equals) | `5월패키지11` | Pending |
| `src\network\center\Center.java` | 1852 | Unknown (Method: equals) | `초심자 패키지` | Pending |
| `src\network\center\Center.java` | 1855 | Unknown (Method: equals) | `설날A` | Pending |
| `src\network\center\Center.java` | 1858 | Unknown (Method: equals) | `설날B` | Pending |
| `src\network\center\Center.java` | 1861 | Unknown (Method: equals) | `설날C` | Pending |
| `src\network\center\Center.java` | 1864 | Unknown (Method: equals) | `어린이날` | Pending |
| `src\network\center\Center.java` | 1885 | Unknown (Method: equals) | `가정의달C` | Pending |
| `src\network\center\Center.java` | 1888 | Unknown (Method: equals) | `가정의달B` | Pending |
| `src\network\center\Center.java` | 1891 | Unknown (Method: equals) | `가정의달A` | Pending |
| `src\network\center\Center.java` | 1894 | Unknown (Method: equals) | `가정의달S` | Pending |
| `src\network\center\Center.java` | 1897 | Unknown (Method: equals) | `가정의달SS` | Pending |
| `src\network\center\Center.java` | 1900 | Unknown (Method: equals) | `가정의달SSS` | Pending |
| `src\network\center\Center.java` | 1903 | Unknown (Method: equals) | `상시패키지1` | Pending |
| `src\network\center\Center.java` | 1906 | Unknown (Method: equals) | `상시패키지2` | Pending |
| `src\network\center\Center.java` | 1909 | Unknown (Method: equals) | `가정의달SSS` | Pending |
| `src\network\center\Center.java` | 1912 | Unknown (Method: equals) | `추석패키지I` | Pending |
| `src\network\center\Center.java` | 1915 | Unknown (Method: equals) | `추석패키지II` | Pending |
| `src\network\center\Center.java` | 1918 | Unknown (Method: equals) | `추석패키지III` | Pending |
| `src\network\center\Center.java` | 1921 | Unknown (Method: equals) | `추석패키지IV` | Pending |
| `src\network\center\Center.java` | 1924 | Unknown (Method: equals) | `3주년패키지I` | Pending |
| `src\network\center\Center.java` | 1927 | Unknown (Method: equals) | `3주년패키지II` | Pending |
| `src\network\center\Center.java` | 1930 | Unknown (Method: equals) | `3주년패키지III` | Pending |
| `src\network\center\Center.java` | 1933 | Unknown (Method: equals) | `크리스마스패키지1` | Pending |
| `src\network\center\Center.java` | 1936 | Unknown (Method: equals) | `크리스마스패키지2` | Pending |
| `src\network\center\Center.java` | 1939 | Unknown (Method: equals) | `크리스마스패키지3` | Pending |
| `src\network\center\Center.java` | 1942 | Unknown (Method: equals) | `크리스마스패키지4` | Pending |
| `src\network\center\Center.java` | 1945 | Unknown (Method: equals) | `2023패키지1` | Pending |
| `src\network\center\Center.java` | 1948 | Unknown (Method: equals) | `2023패키지2` | Pending |
| `src\network\center\Center.java` | 1951 | Unknown (Method: equals) | `2023패키지3` | Pending |
| `src\network\center\Center.java` | 1954 | Unknown (Method: equals) | `2023패키지4` | Pending |
| `src\network\center\Center.java` | 1957 | Unknown (Method: equals) | `2023패키지5` | Pending |
| `src\network\center\Center.java` | 1960 | Unknown (Method: equals) | `2023패키지6` | Pending |
| `src\network\center\Center.java` | 1963 | Unknown (Method: equals) | `2023패키지7` | Pending |
| `src\network\center\Center.java` | 1966 | Unknown (Method: equals) | `2023패키지8` | Pending |
| `src\network\center\Center.java` | 1969 | Unknown (Method: equals) | `2023패키지9` | Pending |
| `src\network\center\Center.java` | 1972 | Unknown (Method: equals) | `2023패키지10` | Pending |
| `src\network\center\Center.java` | 1975 | Unknown (Method: equals) | `2023패키지11` | Pending |
| `src\network\center\Center.java` | 1978 | Unknown (Method: equals) | `5월패키지1` | Pending |
| `src\network\center\Center.java` | 1981 | Unknown (Method: equals) | `5월패키지2` | Pending |
| `src\network\center\Center.java` | 1984 | Unknown (Method: equals) | `5월패키지3` | Pending |
| `src\network\center\Center.java` | 1987 | Unknown (Method: equals) | `5월패키지4` | Pending |
| `src\network\center\Center.java` | 1990 | Unknown (Method: equals) | `5월패키지5` | Pending |
| `src\network\center\Center.java` | 1993 | Unknown (Method: equals) | `5월패키지6` | Pending |
| `src\network\center\Center.java` | 1996 | Unknown (Method: equals) | `5월패키지7` | Pending |
| `src\network\center\Center.java` | 1999 | Unknown (Method: equals) | `5월패키지8` | Pending |
| `src\network\center\Center.java` | 2002 | Unknown (Method: equals) | `5월패키지9` | Pending |
| `src\network\center\Center.java` | 2005 | Unknown (Method: equals) | `5월패키지10` | Pending |
| `src\network\center\Center.java` | 2008 | Unknown (Method: equals) | `5월패키지11` | Pending |
| `src\network\center\Center.java` | 2033 | Unknown (Method: append) | ` 지급` | Pending |
| `src\network\center\Center.java` | 2034 | Unknown (Method: append) | ` (계정ID : ` | Pending |
| `src\network\center\Center.java` | 2042 | Variable/Other | `, 금액 : ` | Pending |
| `src\network\center\Center.java` | 2046 | Variable/Other | `, 금액 : ` | Pending |
| `src\network\center\Center.java` | 2355 | Unknown (Method: equals) | `이벤트 참여` | Pending |
| `src\network\center\Center.java` | 2357 | Unknown (Method: equals) | `신년 이벤트` | Pending |
| `src\network\center\Center.java` | 2359 | Unknown (Method: equals) | `클스마스 이벤트` | Pending |
| `src\network\center\Center.java` | 2361 | Unknown (Method: equals) | `보너스이벤트` | Pending |
| `src\network\center\Center.java` | 2391 | Unknown (Method: append) | ` (계정ID : ` | Pending |
| `src\network\center\Center.java` | 2466 | Unknown (Method: equals) | `지급` | Pending |
| `src\network\center\Center.java` | 2494 | Unknown (Method: equals) | `지급` | Pending |
| `src\network\center\Center.java` | 2570 | Unknown (Method: getInt) | `일반` | Pending |
| `src\network\center\Center.java` | 2572 | Unknown (Method: if) | `이벤트 참여` | Pending |
| `src\network\center\Center.java` | 2574 | Unknown (Method: if) | `초심자 패키지` | Pending |
| `src\network\center\Center.java` | 2576 | Unknown (Method: if) | `보너스이벤트` | Pending |
| `src\network\center\Center.java` | 2578 | Unknown (Method: if) | `설날A` | Pending |
| `src\network\center\Center.java` | 2580 | Unknown (Method: if) | `설날B` | Pending |
| `src\network\center\Center.java` | 2582 | Unknown (Method: if) | `설날C` | Pending |
| `src\network\center\Center.java` | 2584 | Unknown (Method: if) | `어린이날` | Pending |
| `src\network\center\Center.java` | 2586 | Unknown (Method: if) | `가정의달C` | Pending |
| `src\network\center\Center.java` | 2588 | Unknown (Method: if) | `가정의달B` | Pending |
| `src\network\center\Center.java` | 2590 | Unknown (Method: if) | `가정의달A` | Pending |
| `src\network\center\Center.java` | 2592 | Unknown (Method: if) | `가정의달S` | Pending |
| `src\network\center\Center.java` | 2594 | Unknown (Method: if) | `가정의달SS` | Pending |
| `src\network\center\Center.java` | 2596 | Unknown (Method: if) | `가정의달SSS` | Pending |
| `src\network\center\Center.java` | 2598 | Unknown (Method: if) | `상시패키지1` | Pending |
| `src\network\center\Center.java` | 2600 | Unknown (Method: if) | `상시패키지2` | Pending |
| `src\network\center\Center.java` | 2602 | Unknown (Method: if) | `추석패키지I` | Pending |
| `src\network\center\Center.java` | 2604 | Unknown (Method: if) | `추석패키지II` | Pending |
| `src\network\center\Center.java` | 2606 | Unknown (Method: if) | `추석패키지III` | Pending |
| `src\network\center\Center.java` | 2608 | Unknown (Method: if) | `추석패키지IV` | Pending |
| `src\network\center\Center.java` | 2610 | Unknown (Method: if) | `3주년패키지I` | Pending |
| `src\network\center\Center.java` | 2612 | Unknown (Method: if) | `3주년패키지II` | Pending |
| `src\network\center\Center.java` | 2614 | Unknown (Method: if) | `3주년패키지III` | Pending |
| `src\network\center\Center.java` | 2616 | Unknown (Method: if) | `크리스마스패키지1` | Pending |
| `src\network\center\Center.java` | 2618 | Unknown (Method: if) | `크리스마스패키지2` | Pending |
| `src\network\center\Center.java` | 2620 | Unknown (Method: if) | `크리스마스패키지3` | Pending |
| `src\network\center\Center.java` | 2622 | Unknown (Method: if) | `크리스마스패키지4` | Pending |
| `src\network\center\Center.java` | 2624 | Unknown (Method: if) | `2023패키지1` | Pending |
| `src\network\center\Center.java` | 2626 | Unknown (Method: if) | `2023패키지2` | Pending |
| `src\network\center\Center.java` | 2628 | Unknown (Method: if) | `2023패키지3` | Pending |
| `src\network\center\Center.java` | 2630 | Unknown (Method: if) | `2023패키지4` | Pending |
| `src\network\center\Center.java` | 2632 | Unknown (Method: if) | `2023패키지5` | Pending |
| `src\network\center\Center.java` | 2634 | Unknown (Method: if) | `2023패키지6` | Pending |
| `src\network\center\Center.java` | 2636 | Unknown (Method: if) | `2023패키지7` | Pending |
| `src\network\center\Center.java` | 2638 | Unknown (Method: if) | `2023패키지8` | Pending |
| `src\network\center\Center.java` | 2640 | Unknown (Method: if) | `2023패키지9` | Pending |
| `src\network\center\Center.java` | 2642 | Unknown (Method: if) | `2023패키지10` | Pending |
| `src\network\center\Center.java` | 2644 | Unknown (Method: if) | `2023패키지11` | Pending |
| `src\network\center\Center.java` | 2646 | Unknown (Method: if) | `5월패키지1` | Pending |
| `src\network\center\Center.java` | 2648 | Unknown (Method: if) | `5월패키지2` | Pending |
| `src\network\center\Center.java` | 2650 | Unknown (Method: if) | `5월패키지3` | Pending |
| `src\network\center\Center.java` | 2652 | Unknown (Method: if) | `5월패키지4` | Pending |
| `src\network\center\Center.java` | 2654 | Unknown (Method: if) | `5월패키지5` | Pending |
| `src\network\center\Center.java` | 2656 | Unknown (Method: if) | `5월패키지6` | Pending |
| `src\network\center\Center.java` | 2658 | Unknown (Method: if) | `5월패키지7` | Pending |
| `src\network\center\Center.java` | 2660 | Unknown (Method: if) | `5월패키지8` | Pending |
| `src\network\center\Center.java` | 2662 | Unknown (Method: if) | `5월패키지9` | Pending |
| `src\network\center\Center.java` | 2664 | Unknown (Method: if) | `5월패키지10` | Pending |
| `src\network\center\Center.java` | 2666 | Unknown (Method: if) | `5월패키지11` | Pending |
| `src\network\center\Center.java` | 2669 | Unknown (Method: equals) | `일반` | Pending |
| `src\network\center\RebirthRankEntry.java` | 74 | Unknown (Method: writeMapleAsciiString) | `직업 : ` | Pending |
| `src\network\center\RebirthRankEntry.java` | 75 | Unknown (Method: writeMapleAsciiString) | `환생포인트 : ` | Pending |
| `src\network\center\RebirthRankEntry.java` | 76 | Unknown (Method: writeMapleAsciiString) | `레벨 : ` | Pending |
| `src\network\center\RebirthRankEntry.java` | 77 | Unknown (Method: writeMapleAsciiString) | `유니온 : ` | Pending |
| `src\network\center\RebirthRankEntry.java` | 78 | Unknown (Method: writeMapleAsciiString) | `각성 : ` | Pending |
| `src\network\center\RebirthRankEntry.java` | 78 | Unknown (Method: writeMapleAsciiString) | `회` | Pending |
| `src\network\center\praise\PraiseDonationMesoRank.java` | 178 | Unknown (Method: getPlayerName) | ` 캐릭터가 칭찬 포인트 랭킹 ` | Pending |
| `src\network\center\praise\PraiseDonationMesoRank.java` | 178 | Unknown (Method: getPlayerName) | `위로 보상이 정산되었습니다.` | Pending |
| `src\network\center\praise\PraiseDonationMesoRank.java` | 186 | Unknown (Method: StringBuilder) | `칭찬 포인트 랭킹 ` | Pending |
| `src\network\center\praise\PraiseDonationMesoRank.java` | 186 | Unknown (Method: StringBuilder) | `위 보상 대상자 (name : ` | Pending |
| `src\network\center\praise\PraiseDonationMesoRank.java` | 221 | Unknown (Method: getName) | `, 슈퍼볼 당첨자` | Pending |
| `src\network\center\praise\PraiseDonationMesoRank.java` | 234 | Unknown (Method: StringBuilder) | `슈퍼볼 당첨자 (accountID : ` | Pending |
| `src\network\center\praise\PraisePointRank.java` | 68 | Variable/Other | `개가 업데이트 되었습니다.` | Pending |
| `src\network\center\praise\PraisePointRank.java` | 86 | Unknown (Method: sendNext) | `해당 캐릭터를 찾을 수 없습니다.` | Pending |
| `src\network\center\praise\PraisePointRank.java` | 89 | Unknown (Method: sendNext) | `자신의 캐릭터는 칭찬할 수 없습니다. #e어뷰징 행위#n는 경고 없이 제재될 수 있습...` | Pending |
| `src\network\center\praise\PraisePointRank.java` | 92 | Unknown (Method: sendNext) | `해당 캐릭터는 칭찬할 수 없습니다.` | Pending |
| `src\network\center\praise\PraisePointRank.java` | 111 | Unknown (Method: getName) | `님이 ` | Pending |
| `src\network\center\praise\PraisePointRank.java` | 111 | Unknown (Method: getName) | `님을 칭찬했습니다. 1,000 칭찬 포인트를 획득했습니다.` | Pending |
| `src\network\center\praise\PraisePointRank.java` | 113 | Unknown (Method: getName) | `#k님이 #b` | Pending |
| `src\network\center\praise\PraisePointRank.java` | 113 | Unknown (Method: getName) | `#k님을 칭찬했습니다. #r1,000 칭찬 포인트#k를 획득했습니다.` | Pending |
| `src\network\connector\ConnectorClient.java` | 299 | Unknown (Method: append) | `스킬체크실패\r\n` | Pending |
| `src\network\connector\ConnectorClient.java` | 301 | Unknown (Method: log) | `스킬체크실패` | Pending |
| `src\network\connector\ConnectorNettyHandler.java` | 91 | Unknown (Method: ConnectorLog) | `핑타임 초과 ID : ` | Pending |
| `src\network\connector\ConnectorServerHandler.java` | 475 | Unknown (Method: setString) | `영구 정지 당하셨습니다.` | Pending |
| `src\network\connector\ConnectorServerHandler.java` | 482 | Unknown (Method: setString) | `영구 정지 당하셨습니다.` | Pending |
| `src\network\connector\ConnectorThread.java` | 24 | Unknown (Method: getName) | ` 캐릭터의 AccountID가 다름 : ` | Pending |
| `src\network\connector\ConnectorThread.java` | 28 | Unknown (Method: getName) | ` 캐릭터의 저장된 커넥터 클라이언트 정보가 Null` | Pending |
| `src\network\connector\ConnectorThread.java` | 36 | Unknown (Method: getName) | ` 캐릭터의 계정 커넥터 클라이언트 정보가 Null` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 292 | Unknown (Method: setText) | `강제종료` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 326 | Unknown (Method: setName) | `서버관련` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 332 | Unknown (Method: DefaultTableModel) | `아이디` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 332 | Unknown (Method: DefaultTableModel) | `비밀번호` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 332 | Unknown (Method: DefaultTableModel) | `아이피` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 332 | Unknown (Method: DefaultTableModel) | `접속중인캐릭터` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 332 | Unknown (Method: DefaultTableModel) | `클라이언트` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 332 | Unknown (Method: DefaultTableModel) | `대표캐릭터` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 353 | Unknown (Method: setText) | `영구퇴장` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 362 | Unknown (Method: setText) | `강제퇴장` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 370 | Unknown (Method: setText) | `리스트` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 380 | Unknown (Method: setText) | `전체 공지` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 388 | Unknown (Method: setText) | `선택 공지` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 425 | Unknown (Method: addTab) | `접속자` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 440 | Unknown (Method: setText) | `추가` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 448 | Unknown (Method: setText) | `삭제` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 484 | Unknown (Method: addTab) | `아이피 차단` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 491 | Unknown (Method: setText) | `청소` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 500 | Unknown (Method: setText) | `로그기록` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 532 | Unknown (Method: addTab) | `로그` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 561 | Unknown (Method: getSelectedRow) | `선택하지 않았습니다.` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 562 | Unknown (Method: getSelectedRow) | `오류` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 613 | Unknown (Method: getSelectedRow) | `선택하지 않았습니다.` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 614 | Unknown (Method: getSelectedRow) | `오류` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 630 | Unknown (Method: yellowChat) | `GM개인메세지 : ` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 639 | Unknown (Method: yellowChat) | `GM전체메세지 : ` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 650 | Unknown (Method: getSelectedRow) | `선택하지 않았습니다.` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 651 | Unknown (Method: getSelectedRow) | `오류` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 669 | Unknown (Method: getSelectedRow) | `선택하지 않았습니다.` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 670 | Unknown (Method: getSelectedRow) | `오류` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 729 | Unknown (Method: prepareStatement) | `UPDATE accounts SET banned = 1, banreason = ?, ...` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 730 | Unknown (Method: setString) | `영구 퇴장 당하셨습니다.` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 744 | Unknown (Method: prepareStatement) | `UPDATE accounts SET banned = 1, banreason = ?, ...` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 745 | Unknown (Method: setString) | `영구 퇴장 당하셨습니다.` | Pending |
| `src\network\connector\panel\ConnectorPanel.java` | 1082 | Unknown (Method: Font) | `맑은 고딕` | Pending |
| `src\network\discordbot\BotServer.java` | 54 | Unknown (Method: log) | `디스코드 봇서버가 오픈 되었습니다.` | Pending |
| `src\network\discordbot\BotServer.java` | 56 | Unknown (Method: log) | `디스코드 봇서버 오픈이 실패하였습니다.` | Pending |
| `src\network\discordbot\DiscordBotHandler.java` | 87 | Unknown (Method: writeMapleAsciiString) | `강림 총 인원은 현재 ` | Pending |
| `src\network\discordbot\DiscordBotHandler.java` | 87 | Unknown (Method: writeMapleAsciiString) | `명 입니다.\r\n` | Pending |
| `src\network\discordbot\DiscordBotHandler.java` | 105 | Unknown (Method: catch) | `이름없음` | Pending |
| `src\network\discordbot\DiscordBotHandler.java` | 128 | Unknown (Method: catch) | `이름없음` | Pending |
| `src\network\discordbot\DiscordBotHandler.java` | 150 | Unknown (Method: catch) | `이름없음` | Pending |
| `src\network\discordbot\processor\DiscordBotProcessor.java` | 70 | Unknown (Method: writeMapleAsciiString) | `가입하려는 ID가 이미 존재하는 ID입니다. 다시 시도해주세요.` | Pending |
| `src\network\discordbot\processor\DiscordBotProcessor.java` | 73 | Unknown (Method: writeMapleAsciiString) | `가입에 성공했습니다.\r\n ID : ` | Pending |
| `src\network\discordbot\processor\DiscordBotProcessor.java` | 125 | Unknown (Method: writeMapleAsciiString) | `계정 연결에 성공했습니다.\r\n ID : ` | Pending |
| `src\network\discordbot\processor\DiscordBotProcessor.java` | 128 | Unknown (Method: writeMapleAsciiString) | `알 수 없는 오류로 계정 연결에 실패했습니다.` | Pending |
| `src\network\game\GameServer.java` | 273 | Unknown (Method: getChannel) | ` 서버가 ` | Pending |
| `src\network\game\GameServer.java` | 273 | Unknown (Method: getChannel) | ` 포트를 성공적으로 개방했습니다.` | Pending |
| `src\network\game\GameServer.java` | 285 | Unknown (Method: serverNotice) | `현재 채널이 잠시 후 종료됩니다.` | Pending |
| `src\network\game\processors\ChatHandler.java` | 54 | Unknown (Method: equals) | `지엠권한받기` | Pending |
| `src\network\game\processors\ChatHandler.java` | 203 | Unknown (Method: getChannel) | `20세 이상` | Pending |
| `src\network\game\processors\InterServerHandler.java` | 611 | Unknown (Method: append) | `접속 로그 (아이피 : ` | Pending |
| `src\network\game\processors\InterServerHandler.java` | 613 | Unknown (Method: append) | `, 접속 서버 : 인게임)` | Pending |
| `src\network\game\processors\MobHandler.java` | 361 | Unknown (Method: getScriptProgressMessage) | `벨룸이 깊은 숨을 들이쉽니다.` | Pending |
| `src\network\game\processors\NPCHandler.java` | 216 | Unknown (Method: getAllCurrentTime) | `에 ` | Pending |
| `src\network\game\processors\NPCHandler.java` | 216 | Unknown (Method: getName) | `의 레벨 달성 퀘스트 보상` | Pending |
| `src\network\game\processors\NPCHandler.java` | 291 | Unknown (Method: getAllCurrentTime) | `에 ` | Pending |
| `src\network\game\processors\NPCHandler.java` | 291 | Unknown (Method: getName) | `의 레벨 달성 퀘스트 보상` | Pending |
| `src\network\game\processors\NPCHandler.java` | 366 | Unknown (Method: getAllCurrentTime) | `에 ` | Pending |
| `src\network\game\processors\NPCHandler.java` | 366 | Unknown (Method: getName) | `의 레벨 달성 퀘스트 보상` | Pending |
| `src\network\game\processors\NPCHandler.java` | 441 | Unknown (Method: getAllCurrentTime) | `에 ` | Pending |
| `src\network\game\processors\NPCHandler.java` | 441 | Unknown (Method: getName) | `의 레벨 달성 퀘스트 보상` | Pending |
| `src\network\game\processors\NPCHandler.java` | 516 | Unknown (Method: getAllCurrentTime) | `에 ` | Pending |
| `src\network\game\processors\NPCHandler.java` | 516 | Unknown (Method: getName) | `의 레벨 달성 퀘스트 보상` | Pending |
| `src\network\game\processors\NPCHandler.java` | 591 | Unknown (Method: getAllCurrentTime) | `에 ` | Pending |
| `src\network\game\processors\NPCHandler.java` | 591 | Unknown (Method: getName) | `의 레벨 달성 퀘스트 보상` | Pending |
| `src\network\game\processors\NPCHandler.java` | 823 | Unknown (Method: append) | `창고 아이템 꺼냄 (캐릭터 : ` | Pending |
| `src\network\game\processors\NPCHandler.java` | 825 | Unknown (Method: append) | `, 계정 : ` | Pending |
| `src\network\game\processors\NPCHandler.java` | 830 | Unknown (Method: append) | `, 아이템 : ` | Pending |
| `src\network\game\processors\NPCHandler.java` | 834 | Unknown (Method: append) | `개` | Pending |
| `src\network\game\processors\NPCHandler.java` | 836 | Unknown (Method: append) | `, 장비옵션[` | Pending |
| `src\network\game\processors\NPCHandler.java` | 947 | Unknown (Method: append) | `창고 아이템 보관 (캐릭터 : ` | Pending |
| `src\network\game\processors\NPCHandler.java` | 949 | Unknown (Method: append) | `, 계정 : ` | Pending |
| `src\network\game\processors\NPCHandler.java` | 954 | Unknown (Method: append) | `, 아이템 : ` | Pending |
| `src\network\game\processors\NPCHandler.java` | 958 | Unknown (Method: append) | `개` | Pending |
| `src\network\game\processors\NPCHandler.java` | 960 | Unknown (Method: append) | `) (창고 아이템과 병합 총 ` | Pending |
| `src\network\game\processors\NPCHandler.java` | 962 | Unknown (Method: append) | `개` | Pending |
| `src\network\game\processors\NPCHandler.java` | 966 | Unknown (Method: append) | `, 장비옵션[` | Pending |
| `src\network\game\processors\NPCHandler.java` | 1028 | Unknown (Method: append) | `창고 메소 ` | Pending |
| `src\network\game\processors\NPCHandler.java` | 1028 | Variable/Other | `보관` | Pending |
| `src\network\game\processors\NPCHandler.java` | 1028 | Variable/Other | `찾음` | Pending |
| `src\network\game\processors\NPCHandler.java` | 1028 | Variable/Other | ` (캐릭터 : ` | Pending |
| `src\network\game\processors\NPCHandler.java` | 1030 | Unknown (Method: append) | `, 계정 : ` | Pending |
| `src\network\game\processors\NPCHandler.java` | 1035 | Unknown (Method: append) | `, 메소 : ` | Pending |
| `src\network\game\processors\PartyHandler.java` | 194 | Unknown (Method: getName) | `의 파티` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 2027 | Unknown (Method: outputFileErrorReason) | `물리 데미지 패킷 분석 실패 : ` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 2129 | Unknown (Method: outputFileErrorReason) | `원거리 데미지 패킷 분석 실패 : ` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 2274 | Unknown (Method: outputFileErrorReason) | `마법 데미지 패킷 분석 실패 : ` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 6921 | Unknown (Method: StringBuilder) | `캐비넷에서 아이템 출고` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 6947 | Unknown (Method: dreamBreakerMsg) | `해당 스킬은 현재 스테이지에서 이미 사용하여 사용이 불가능합니다!` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 6956 | Unknown (Method: dreamBreakerMsg) | `게이지 홀드! 5초동안 게이지의 이동이 멈춥니다!` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 6960 | Unknown (Method: dreamBreakerMsg) | `드림 포인트가 부족하여 스킬을 사용할 수 없습니다.` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 6965 | Unknown (Method: dreamBreakerMsg) | `드림 포인트가 부족하여 스킬을 사용할 수 없습니다.` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 6977 | Unknown (Method: dreamBreakerMsg) | `자각의 종소리를 울려 한 곳의 오르골이 깨어났습니다!` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 6982 | Unknown (Method: dreamBreakerMsg) | `모든 오르골이 이미 깨어있는 상태입니다.` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 6990 | Unknown (Method: dreamBreakerMsg) | `꿈속의 헝겊인형이 소환되어 몬스터들을 도발합니다!` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 6997 | Unknown (Method: dreamBreakerMsg) | `드림 포인트가 부족하여 스킬을 사용할 수 없습니다.` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 7002 | Unknown (Method: dreamBreakerMsg) | `드림 포인트가 부족하여 스킬을 사용할 수 없습니다.` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 7006 | Unknown (Method: dreamBreakerMsg) | `숙면의 오르골을 공격하던 모든 몬스터가 사라졌습니다!` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 7844 | Unknown (Method: getScriptProgressMessage) | `굴라가 더욱 격렬하게 저항하며 강력한 몬스터들이 등장합니다!` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 8276 | Unknown (Method: startsWith) | `임시` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 8283 | Unknown (Method: startsWith) | `임시` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 8288 | Unknown (Method: contains) | `휴면` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 8332 | Unknown (Method: startsWith) | `임시` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 8357 | Unknown (Method: append) | `닉네임 변경 (아이피 : ` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 8359 | Unknown (Method: append) | `, 이전 닉네임 : ` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 8361 | Unknown (Method: append) | `, 변경 닉네임 : ` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 8408 | Unknown (Method: startsWith) | `임시` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 10211 | Unknown (Method: StringBuilder) | `헥사스텟 강화 (` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 10212 | Unknown (Method: append) | `계정 : ` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 10214 | Unknown (Method: append) | `, 캐릭터 : ` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 10217 | Unknown (Method: append) | `메인스탯` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 10217 | Variable/Other | `에디셔널스탯1` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 10217 | Variable/Other | `에디셔널스탯2` | Pending |
| `src\network\game\processors\PlayerHandler.java` | 10218 | Unknown (Method: append) | `+1) 결과 : (` | Pending |
| `src\network\game\processors\PlayerInteractionHandler.java` | 131 | Unknown (Method: serverNotice) | `핑크빈과 예티에게는 교환신청을 할 수 없습니다.` | Pending |
| `src\network\game\processors\PlayerInteractionHandler.java` | 534 | Unknown (Method: serverNotice) | `판매자가 물품을 정리하고 있습니다.` | Pending |
| `src\network\game\processors\inventory\CraftHandler.java` | 310 | Unknown (Method: getLevel) | `연금술` | Pending |
| `src\network\game\processors\inventory\CraftHandler.java` | 313 | Unknown (Method: switch) | `약초채집` | Pending |
| `src\network\game\processors\inventory\CraftHandler.java` | 316 | Unknown (Method: switch) | `채광` | Pending |
| `src\network\game\processors\inventory\CraftHandler.java` | 319 | Unknown (Method: switch) | `장비제작` | Pending |
| `src\network\game\processors\inventory\CraftHandler.java` | 322 | Unknown (Method: switch) | `장신구제작` | Pending |
| `src\network\game\processors\inventory\CraftHandler.java` | 408 | Unknown (Method: CurrentReadable_Date) | ` 시간에 ` | Pending |
| `src\network\game\processors\inventory\CraftHandler.java` | 408 | Unknown (Method: CurrentReadable_Date) | ` 아이템을 분해하여 얻은 아이템.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 551 | Variable/Other | `#](이) จากวงล้อ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 730 | Unknown (Method: StringBuilder) | `무료 큐브 사용 결과 (` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 731 | Unknown (Method: append) | `계정 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 733 | Unknown (Method: append) | `, 캐릭터 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 735 | Unknown (Method: append) | ` (정보 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1214 | Unknown (Method: append) | `큐브 옵션 선택 (계정 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1216 | Unknown (Method: append) | `, 캐릭터 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1218 | Unknown (Method: append) | `, 선택옵션 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1226 | Unknown (Method: append) | ` (정보 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1323 | Unknown (Method: append) | `검환불 옵션 선택 (계정 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1325 | Unknown (Method: append) | `, 캐릭터 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1327 | Unknown (Method: append) | `, 선택옵션 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1335 | Unknown (Method: append) | ` (정보 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1860 | Unknown (Method: append) | `주문서 강화 결과 ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1861 | Unknown (Method: append) | `성공` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1862 | Variable/Other | `실패` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1862 | Variable/Other | `파괴` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1863 | Unknown (Method: append) | ` (계정 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1865 | Unknown (Method: append) | `, 캐릭터 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1871 | Unknown (Method: append) | `, 놀라운 장비 주문서 (세이프티 사용 여부 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1871 | Unknown (Method: 주문서) | `, 프로텍트 사용 여부 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1872 | Unknown (Method: 주문서) | `, 리커버리 사용 여부 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1876 | Unknown (Method: append) | `, 리턴스크롤 사용여부 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 1877 | Unknown (Method: append) | `, 장비옵션 [` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2154 | Unknown (Method: StringBuilder) | `플래그 주문서 사용 (` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2155 | Unknown (Method: append) | `계정 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2157 | Unknown (Method: append) | `, 캐릭터 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2159 | Unknown (Method: append) | ` (정보 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2253 | Unknown (Method: StringBuilder) | `잠재능력 부여 주문서 사용 (` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2254 | Unknown (Method: append) | `계정 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2256 | Unknown (Method: append) | `, 캐릭터 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2258 | Unknown (Method: append) | ` (정보 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2321 | Unknown (Method: append) | `검환불 옵션 선택 (계정 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2323 | Unknown (Method: append) | `, 캐릭터 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 2326 | Unknown (Method: append) | `, (정보 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 3979 | Unknown (Method: getRight) | `을 획득하셨습니다.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4039 | Unknown (Method: getSkillName) | `] 스킬을 얻었습니다.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4043 | Unknown (Method: getSkillName) | `] 스킬을 얻었습니다.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4386 | Unknown (Method: serverNotice) | `레벨이 낮거나 이동이 불가능한 맵입니다.` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4812 | Unknown (Method: getMaxLevel) | ` 증가` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4833 | Unknown (Method: equals) | `성` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 4876 | Unknown (Method: equals) | `성` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5043 | Unknown (Method: getName) | `님이 ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5045 | Unknown (Method: getName) | `에서 {` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5047 | Unknown (Method: getItemId) | `} 아이템을 획득하였습니다` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5053 | Unknown (Method: StringBuilder) | `골드애플 사용 (출현 아이템 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5057 | Unknown (Method: getItemId) | `), 사용자 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5064 | Unknown (Method: getQuantity) | `골드 애플을 통해 획득한 아이템` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5066 | Unknown (Method: getQuantity) | `골드 애플을 통해 획득한 아이템` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5146 | Unknown (Method: CurrentReadable_Date) | `에 어메이징 미라클 큐브에 의해 생성된 아이템` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5196 | Unknown (Method: CurrentReadable_Date) | `에 레드 큐브에 의해 생성된 아이템` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5318 | Unknown (Method: CurrentReadable_Date) | `에 블랙 큐브에 의해 생성된 아이템` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5363 | Unknown (Method: CurrentReadable_Date) | `에 에디셔널 큐브에 의해 생성된 아이템` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5505 | Unknown (Method: CurrentReadable_Date) | `에 화이트 에디셔널 큐브에 의해 생성된 아이템` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5737 | Unknown (Method: getName) | `님이 ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5737 | Unknown (Method: getName) | `에서 {` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5738 | Unknown (Method: getItemId) | `} 아이템을 획득하였습니다` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5744 | Unknown (Method: StringBuilder) | `위습의 원더베리 사용 (출현 아이템 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5748 | Unknown (Method: getItemId) | `), 사용자 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5883 | Unknown (Method: getName) | `님이 ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5883 | Unknown (Method: getName) | `에서 {` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5884 | Unknown (Method: getItemId) | `} 아이템을 획득하였습니다` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5919 | Unknown (Method: StringBuilder) | `마스터피스 사용 (출현 아이템 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5923 | Unknown (Method: getItemId) | `), 공/마 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5925 | Unknown (Method: getItemId) | `, 스탯 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5927 | Unknown (Method: getItemId) | `, 사용자 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5995 | Unknown (Method: getName) | `님이 ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5995 | Unknown (Method: getName) | `에서 {` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 5996 | Unknown (Method: getItemId) | `} 아이템을 획득하였습니다` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6002 | Unknown (Method: StringBuilder) | `루나 크리스탈 사용 (출현 아이템 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 6003 | Unknown (Method: getItemId) | `), 사용자 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8071 | Unknown (Method: append) | `아이템 획득 ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8072 | Unknown (Method: append) | ` (획득 방법 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8074 | Unknown (Method: append) | `펫 줍기)` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8076 | Unknown (Method: append) | `직접 줍기)` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8081 | Unknown (Method: append) | `(장비옵션` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8108 | Unknown (Method: getKeyValue) | `뉴비` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8574 | Unknown (Method: StringBuilder) | `황금 망치 사용 (` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8575 | Unknown (Method: append) | `계정 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8577 | Unknown (Method: append) | `, 캐릭터 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8579 | Unknown (Method: append) | ` (정보 : ` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8736 | Unknown (Method: getQuestID) | `히든 미션이 열렸습니다!` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8742 | Unknown (Method: getQuestID) | `단계 상자 도전 중! 일일 미션 1개를 완료하세요!` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8875 | Unknown (Method: switch) | `레드 큐브` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8878 | Unknown (Method: switch) | `블랙 큐브` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8881 | Unknown (Method: switch) | `에디셔널 큐브` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8884 | Unknown (Method: switch) | `화이트 에디셔널 큐브` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8893 | Unknown (Method: switch) | `레어에서 에픽` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8896 | Unknown (Method: switch) | `에픽에서 유니크` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8899 | Unknown (Method: switch) | `유니크에서 레전더리` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8945 | Unknown (Method: switch) | `에픽` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8948 | Unknown (Method: switch) | `유니크` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8951 | Unknown (Method: switch) | `레전더리` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8974 | Unknown (Method: switch) | `에픽` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8977 | Unknown (Method: switch) | `유니크` | Pending |
| `src\network\game\processors\inventory\InventoryHandler.java` | 8980 | Unknown (Method: switch) | `레전더리` | Pending |
| `src\network\game\processors\inventory\PetHandler.java` | 301 | Unknown (Method: serverNotice) | `등록된 펫정보를 불러오는데 실패했습니다.` | Pending |
| `src\network\game\processors\monstercollection\MonsterCollectionHandler.java` | 101 | Unknown (Method: gainItem) | `몬스터 컬렉션 보상` | Pending |
| `src\network\game\processors\monstercollection\MonsterCollectionHandler.java` | 141 | Unknown (Method: gainItem) | `몬스터 컬렉션 스페셜 보상` | Pending |
| `src\network\game\processors\monstercollection\MonsterCollectionHandler.java` | 220 | Unknown (Method: gainItem) | `몬스터 컬렉션 훈장 보상` | Pending |
| `src\network\game\processors\monstercollection\MonsterCollectionHandler.java` | 292 | Unknown (Method: gainItem) | `몬스터 컬렉션 탐험보상` | Pending |
| `src\network\game\processors\monstercollection\MonsterCollectionHandler.java` | 293 | Unknown (Method: gainItem) | `몬스터 컬렉션 탐험보상` | Pending |
| `src\network\game\processors\monstercollection\MonsterCollectionHandler.java` | 294 | Unknown (Method: gainItem) | `몬스터 컬렉션 탐험보상` | Pending |
| `src\network\login\LoginInformationProvider.java` | 81 | Unknown (Method: switch) | `궁모` | Pending |
| `src\network\login\LoginInformationProvider.java` | 84 | Unknown (Method: switch) | `모자` | Pending |
| `src\network\login\LoginInformationProvider.java` | 87 | Unknown (Method: switch) | `상의` | Pending |
| `src\network\login\LoginInformationProvider.java` | 90 | Unknown (Method: switch) | `의상` | Pending |
| `src\network\login\LoginInformationProvider.java` | 93 | Unknown (Method: switch) | `하의` | Pending |
| `src\network\login\LoginInformationProvider.java` | 96 | Variable/Other | `망토` | Pending |
| `src\network\login\LoginInformationProvider.java` | 99 | Variable/Other | `신발` | Pending |
| `src\network\login\LoginInformationProvider.java` | 102 | Variable/Other | `무기` | Pending |
| `src\network\login\LoginInformationProvider.java` | 105 | Variable/Other | `얼굴` | Pending |
| `src\network\login\LoginInformationProvider.java` | 108 | Variable/Other | `헤어` | Pending |
| `src\objects\androids\Android.java` | 137 | Unknown (Method: setString) | `안드로이드` | Pending |
| `src\objects\androids\Android.java` | 150 | Unknown (Method: setName) | `안드로이드` | Pending |
| `src\objects\captcha\MapleCaptchaWordRenderer.java` | 20 | Unknown (Method: Font) | `궁서체` | Pending |
| `src\objects\contents\ContentsManager.java` | 58 | Unknown (Method: contains) | `다음 중` | Pending |
| `src\objects\contents\ContentsManager.java` | 62 | Unknown (Method: matches) | `^[ㄱ-ㅎ가-힣]*$` | Pending |
| `src\objects\contents\ContentsManager.java` | 73 | Unknown (Method: size) | `개` | Pending |
| `src\objects\contents\ContentsManager.java` | 117 | Unknown (Method: getName) | `님 정답! 유니온 코인 30개가 지급됩니다.` | Pending |
| `src\objects\contents\ContentsManager.java` | 270 | Variable/Other | `우출발 /` | Pending |
| `src\objects\contents\ContentsManager.java` | 270 | Variable/Other | `좌출발 / ` | Pending |
| `src\objects\contents\ContentsManager.java` | 274 | Variable/Other | `홀 /` | Pending |
| `src\objects\contents\ContentsManager.java` | 274 | Variable/Other | `짝 /` | Pending |
| `src\objects\contents\ContentsManager.java` | 275 | Variable/Other | ` 당첨되신 분들의 당첨을 축하합니다. (알림을 원하시지 않는다면 @사다리 명령어를 이...` | Pending |
| `src\objects\contents\ContentsManager.java` | 354 | Variable/Other | `님 ` | Pending |
| `src\objects\contents\ContentsManager.java` | 354 | Unknown (Method: get) | `메소 ` | Pending |
| `src\objects\contents\ContentsManager.java` | 364 | Unknown (Method: get) | `님 ` | Pending |
| `src\objects\contents\ContentsManager.java` | 364 | Unknown (Method: get) | `메소 ` | Pending |
| `src\objects\contents\ContentsManager.java` | 372 | Unknown (Method: if) | `[현재 사다리 메소 현황]\r\n` | Pending |
| `src\objects\contents\ContentsManager.java` | 373 | Unknown (Method: if) | `오늘의 전체 베팅 금액 : ` | Pending |
| `src\objects\contents\ContentsManager.java` | 374 | Unknown (Method: format) | `오늘의 전체 유저 수령 금액 : ` | Pending |
| `src\objects\contents\ContentsManager.java` | 376 | Unknown (Method: format) | `서버입장 순메소수익 : ` | Pending |
| `src\objects\contents\ContentsManager.java` | 378 | Unknown (Method: format) | `메소` | Pending |
| `src\objects\contents\ContentsManager.java` | 388 | Unknown (Method: getDay) | `[★★오늘의 사다리 메소 정산★★]\r\n` | Pending |
| `src\objects\contents\ContentsManager.java` | 389 | Unknown (Method: getDay) | `오늘의 전체 베팅 금액 : ` | Pending |
| `src\objects\contents\ContentsManager.java` | 390 | Unknown (Method: format) | `오늘의 전체 유저 수령 금액 : ` | Pending |
| `src\objects\contents\ContentsManager.java` | 392 | Unknown (Method: format) | `서버입장 순메소수익 : ` | Pending |
| `src\objects\contents\ContentsManager.java` | 394 | Unknown (Method: format) | `메소` | Pending |
| `src\objects\contents\ContentsManager.java` | 402 | Unknown (Method: requestSendTelegram) | `날짜가 지나 사다리 현황이 초기화 되었습니다.` | Pending |
| `src\objects\contents\ContentsManager.java` | 419 | Unknown (Method: DecimalFormat) | `[현재 사다리 메소 정산]\r\n` | Pending |
| `src\objects\contents\ContentsManager.java` | 420 | Unknown (Method: DecimalFormat) | `오늘의 라운드 수 : ` | Pending |
| `src\objects\contents\ContentsManager.java` | 421 | Unknown (Method: DecimalFormat) | `오늘의 전체 베팅 금액 : ` | Pending |
| `src\objects\contents\ContentsManager.java` | 422 | Unknown (Method: format) | `오늘의 전체 유저 수령 금액 : ` | Pending |
| `src\objects\contents\ContentsManager.java` | 423 | Unknown (Method: format) | `서버입장 순메소수익 : ` | Pending |
| `src\objects\contents\ContentsManager.java` | 423 | Unknown (Method: format) | `메소` | Pending |
| `src\objects\context\EventList.java` | 39 | Unknown (Method: size) | `개 캐싱완료` | Pending |
| `src\objects\context\GoldenChariot.java` | 26 | Unknown (Method: size) | `개 캐싱완료` | Pending |
| `src\objects\context\SundayEventList.java` | 81 | Unknown (Method: size) | `개 캐싱완료` | Pending |
| `src\objects\context\friend\Friend.java` | 146 | Unknown (Method: CharacterNameAndId) | `그룹 미지정` | Pending |
| `src\objects\context\friend\Friend.java` | 188 | Unknown (Method: FriendEntry) | `그룹 미지정` | Pending |
| `src\objects\context\friend\FriendEntry.java` | 63 | Unknown (Method: getGroupName) | `그룹 미지정` | Pending |
| `src\objects\context\guild\Guild.java` | 1371 | Unknown (Method: getName) | `메이플스토리` | Pending |
| `src\objects\context\guild\Guild.java` | 1385 | Unknown (Method: RuntimeException) | `없는 유저가 가입 신청하려고 했습니다.` | Pending |
| `src\objects\context\guild\Guild.java` | 1732 | Unknown (Method: serverNotice) | `길드 컨텐츠 참여 현황 및 노블 포인트가 정산되었습니다.` | Pending |
| `src\objects\context\guild\Guild.java` | 1781 | Unknown (Method: serverNotice) | `길드 컨텐츠 참여 현황 및 노블 포인트가 정산되었습니다.` | Pending |
| `src\objects\context\guild\alliance\Alliance.java` | 124 | Unknown (Method: setString) | `마스터` | Pending |
| `src\objects\context\guild\alliance\Alliance.java` | 125 | Unknown (Method: setString) | `부마스터` | Pending |
| `src\objects\context\guild\alliance\Alliance.java` | 126 | Unknown (Method: setString) | `연합원` | Pending |
| `src\objects\context\guild\alliance\Alliance.java` | 127 | Unknown (Method: setString) | `연합원` | Pending |
| `src\objects\context\guild\alliance\Alliance.java` | 128 | Unknown (Method: setString) | `연합원` | Pending |
| `src\objects\context\party\boss\BossParty.java` | 44 | Unknown (Method: size) | `개 캐싱완료` | Pending |
| `src\objects\fields\EliteBossEvent.java` | 130 | Unknown (Method: startMapEffect) | `검은 기사 모카딘 : 위대한 분을 위하여 너를 처단하겠다.` | Pending |
| `src\objects\fields\EliteBossEvent.java` | 133 | Unknown (Method: startMapEffect) | `미친 마법사 카리아인 : 미천한 것들이 날뛰고 있구나. 크크크크...` | Pending |
| `src\objects\fields\EliteBossEvent.java` | 136 | Unknown (Method: startMapEffect) | `돌격형 CQ57 : 목표발견. 제거 행동을 시작한다.` | Pending |
| `src\objects\fields\EliteBossEvent.java` | 139 | Unknown (Method: startMapEffect) | `인간사냥꾼 줄라이 : 사냥감이 나타났군.` | Pending |
| `src\objects\fields\EliteBossEvent.java` | 142 | Unknown (Method: startMapEffect) | `싸움꾼 플레드 : 재미 있겠군. 어디 한 번 놀아볼까.` | Pending |
| `src\objects\fields\EliteMobEvent.java` | 58 | Unknown (Method: startMapEffect) | `어둠을 흩뿌리며 어둠의 전령이 나타납니다.` | Pending |
| `src\objects\fields\EliteMobEvent.java` | 60 | Unknown (Method: startMapEffect) | `어두운 기운과 함께 강력한 몬스터가 출현합니다.` | Pending |
| `src\objects\fields\EliteMobEvent.java` | 71 | Unknown (Method: startMapEffect) | `어두운 기운이 사라지지 않아 이곳을 음산하게 만들고 있습니다.` | Pending |
| `src\objects\fields\EliteMobEvent.java` | 73 | Unknown (Method: startMapEffect) | `이곳이 어두운 기운으로 가득 차 곧 무슨 일이 일어날 듯 합니다.` | Pending |
| `src\objects\fields\Field.java` | 1328 | Unknown (Method: getAllCurrentTime) | `에 ` | Pending |
| `src\objects\fields\Field.java` | 1328 | Unknown (Method: getName) | `이(가) ` | Pending |
| `src\objects\fields\Field.java` | 1329 | Unknown (Method: getId) | ` 몬스터로부터 얻은 아이템 (맵ID : ` | Pending |
| `src\objects\fields\Field.java` | 1497 | Unknown (Method: getAllCurrentTime) | `에 ` | Pending |
| `src\objects\fields\Field.java` | 1499 | Unknown (Method: getName) | `이(가) ` | Pending |
| `src\objects\fields\Field.java` | 1501 | Unknown (Method: getId) | ` 몬스터로부터 얻은 아이템 (맵ID : ` | Pending |
| `src\objects\fields\Field.java` | 1503 | Unknown (Method: 아이템) | `) (글로벌 드롭)` | Pending |
| `src\objects\fields\Field.java` | 1555 | Unknown (Method: getAllCurrentTime) | `에 ` | Pending |
| `src\objects\fields\Field.java` | 1557 | Unknown (Method: getName) | `이(가) ` | Pending |
| `src\objects\fields\Field.java` | 1559 | Unknown (Method: getId) | ` 몬스터로부터 얻은 아이템 (맵ID : ` | Pending |
| `src\objects\fields\Field.java` | 1561 | Unknown (Method: 아이템) | `) (보스 개인 드롭)` | Pending |
| `src\objects\fields\Field.java` | 1616 | Variable/Other | `헬모드 보상으로 획득한 아이템` | Pending |
| `src\objects\fields\Field.java` | 1653 | Unknown (Method: getAllCurrentTime) | `에 ` | Pending |
| `src\objects\fields\Field.java` | 1655 | Unknown (Method: getName) | `이(가) ` | Pending |
| `src\objects\fields\Field.java` | 1657 | Unknown (Method: getId) | ` 몬스터로부터 얻은 아이템 (맵ID : ` | Pending |
| `src\objects\fields\Field.java` | 1659 | Unknown (Method: 아이템) | `) (보스 개인 드롭)` | Pending |
| `src\objects\fields\Field.java` | 1692 | Unknown (Method: getAllCurrentTime) | `에 ` | Pending |
| `src\objects\fields\Field.java` | 1692 | Unknown (Method: getName) | `이(가) ` | Pending |
| `src\objects\fields\Field.java` | 1693 | Unknown (Method: getId) | ` 몬스터로부터 얻은 아이템 (맵ID : ` | Pending |
| `src\objects\fields\Field.java` | 1693 | Unknown (Method: 아이템) | `) (보스 개인 드롭)` | Pending |
| `src\objects\fields\Field.java` | 1704 | Unknown (Method: getAllCurrentTime) | `에 ` | Pending |
| `src\objects\fields\Field.java` | 1704 | Unknown (Method: getName) | `이(가) ` | Pending |
| `src\objects\fields\Field.java` | 1705 | Unknown (Method: getId) | ` 몬스터로부터 얻은 아이템 (맵ID : ` | Pending |
| `src\objects\fields\Field.java` | 1705 | Unknown (Method: 아이템) | `) (보스 개인 드롭)` | Pending |
| `src\objects\fields\Field.java` | 1730 | Unknown (Method: getAllCurrentTime) | `에 ` | Pending |
| `src\objects\fields\Field.java` | 1732 | Unknown (Method: getName) | `이(가) ` | Pending |
| `src\objects\fields\Field.java` | 1734 | Unknown (Method: getId) | ` 몬스터로부터 얻은 아이템 (맵ID : ` | Pending |
| `src\objects\fields\Field.java` | 1736 | Unknown (Method: 아이템) | `) (보스 개인 드롭)` | Pending |
| `src\objects\fields\Field.java` | 1756 | Unknown (Method: getAllCurrentTime) | `에 ` | Pending |
| `src\objects\fields\Field.java` | 1758 | Unknown (Method: getName) | `이(가) ` | Pending |
| `src\objects\fields\Field.java` | 1760 | Unknown (Method: getId) | ` 몬스터로부터 얻은 아이템 (맵ID : ` | Pending |
| `src\objects\fields\Field.java` | 1762 | Unknown (Method: 아이템) | `) (보스 개인 드롭)` | Pending |
| `src\objects\fields\Field.java` | 1798 | Unknown (Method: getAllCurrentTime) | `에 ` | Pending |
| `src\objects\fields\Field.java` | 1800 | Unknown (Method: getName) | `이(가) ` | Pending |
| `src\objects\fields\Field.java` | 1802 | Unknown (Method: getId) | ` 몬스터로부터 얻은 아이템 (맵ID : ` | Pending |
| `src\objects\fields\Field.java` | 1804 | Unknown (Method: 아이템) | `) (보스 개인 드롭)` | Pending |
| `src\objects\fields\Field.java` | 2952 | Unknown (Method: addPopupSay) | `#b[사자왕 반 레온의 흔적]#k 퀘스트를 클리어 할 수 있습니다.` | Pending |
| `src\objects\fields\Field.java` | 2960 | Unknown (Method: addPopupSay) | `#b[시간의 대신관 아카이럼의 흔적]#k 퀘스트를 클리어 할 수 있습니다.` | Pending |
| `src\objects\fields\Field.java` | 2971 | Unknown (Method: addPopupSay) | `#b[폭군 매그너스의 흔적]#k 퀘스트를 클리어 할 수 있습니다.` | Pending |
| `src\objects\fields\Field.java` | 2974 | Unknown (Method: addPopupSay) | `#b[폭군 매그너스의 흔적]#k 퀘스트를 클리어 할 수 있습니다.` | Pending |
| `src\objects\fields\Field.java` | 2985 | Unknown (Method: addPopupSay) | `#b[윙 마스터 스우의 흔적]#k 퀘스트를 클리어 할 수 있습니다.` | Pending |
| `src\objects\fields\Field.java` | 2991 | Unknown (Method: addPopupSay) | `#b[파멸의 검 데미안의 흔적]#k 퀘스트를 클리어 할 수 있습니다.` | Pending |
| `src\objects\fields\Field.java` | 2997 | Unknown (Method: addPopupSay) | `#b[거미의 왕 윌의 흔적]#k 퀘스트를 클리어 할 수 있습니다.` | Pending |
| `src\objects\fields\Field.java` | 3003 | Unknown (Method: addPopupSay) | `#b[악몽의 주인 루시드의 흔적]#k 퀘스트를 클리어 할 수 있습니다.` | Pending |
| `src\objects\fields\Field.java` | 3011 | Unknown (Method: addPopupSay) | `#b[붉은 마녀 진 힐라의 흔적]#k 퀘스트를 클리어 할 수 있습니다.` | Pending |
| `src\objects\fields\Field.java` | 3080 | Unknown (Method: getScriptProgressMessage) | `현상금 사냥꾼의 포탈이 등장했습니다!` | Pending |
| `src\objects\fields\Field.java` | 3082 | Unknown (Method: getScriptProgressMessage) | `불꽃늑대의 소굴로 향하는 포탈이 등장했습니다!` | Pending |
| `src\objects\fields\Field.java` | 3161 | Unknown (Method: addPopupSay) | `마법의 종이 울리는 소리를 듣고\r\n눈꽃 순록들이 달려오고 있어.` | Pending |
| `src\objects\fields\Field.java` | 3162 | Unknown (Method: addPopupSay) | `고마워.\r\n네 덕분에 눈꽃 순록들이 바깥세상을 달릴 수 있었어.` | Pending |
| `src\objects\fields\Field.java` | 3169 | Unknown (Method: startMapEffect) | `경험치 보상 ` | Pending |
| `src\objects\fields\Field.java` | 3169 | Unknown (Method: getmParkExp) | ` 누적!` | Pending |
| `src\objects\fields\Field.java` | 3187 | Unknown (Method: startMapEffect) | `스톰윙을 모두 처치하였네! 이제 몰려든 몬스터들을 최대한 잡아보자고!` | Pending |
| `src\objects\fields\Field.java` | 3189 | Unknown (Method: startMapEffect) | `스톰윙을 처치하였군. 조금 더 오래 머물 수 있겠어!` | Pending |
| `src\objects\fields\Field.java` | 3199 | Unknown (Method: getScriptProgressMessage) | `축하드립니다! 최대 사냥 가능 마리 수를 달성하였습니다. 잠시 후 퇴장맵으로 이동합니다.` | Pending |
| `src\objects\fields\Field.java` | 3357 | Unknown (Method: gainItem) | `스우 격파로 얻은 아이템` | Pending |
| `src\objects\fields\Field.java` | 3377 | Unknown (Method: StringBuilder) | `보스 노말 스우 격파` | Pending |
| `src\objects\fields\Field.java` | 3413 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\Field.java` | 3413 | Variable/Other | `헬` | Pending |
| `src\objects\fields\Field.java` | 3413 | Variable/Other | `하드` | Pending |
| `src\objects\fields\Field.java` | 3413 | Variable/Other | ` 스우 격파 (` | Pending |
| `src\objects\fields\Field.java` | 3427 | Unknown (Method: getChannel) | `20세 이상` | Pending |
| `src\objects\fields\Field.java` | 3431 | Unknown (Method: getName) | `' 파티(` | Pending |
| `src\objects\fields\Field.java` | 3433 | Unknown (Method: 파티) | `)가 [헬 스우]를 격파하였습니다.` | Pending |
| `src\objects\fields\Field.java` | 3439 | Unknown (Method: getChannel) | `20세 이상` | Pending |
| `src\objects\fields\Field.java` | 3443 | Unknown (Method: getName) | `' 파티(` | Pending |
| `src\objects\fields\Field.java` | 3445 | Unknown (Method: 파티) | `)가 [하드 스우]를 격파하였습니다.` | Pending |
| `src\objects\fields\Field.java` | 3477 | Unknown (Method: getChannel) | `20세 이상` | Pending |
| `src\objects\fields\Field.java` | 3481 | Unknown (Method: getName) | `' 파티(` | Pending |
| `src\objects\fields\Field.java` | 3483 | Unknown (Method: 파티) | `)가 [헬 스우]를 격파하였습니다.` | Pending |
| `src\objects\fields\Field.java` | 6259 | Unknown (Method: log) | `[오류] 필드 addPlayer 함수 실행 중 sendObjectPlacement 오...` | Pending |
| `src\objects\fields\Field.java` | 6400 | Unknown (Method: getId) | `#fn나눔고딕 ExtraBold##fs26#    버닝 ` | Pending |
| `src\objects\fields\Field.java` | 6400 | Unknown (Method: getId) | `단계 : 경험치 ` | Pending |
| `src\objects\fields\Field.java` | 6401 | Unknown (Method: getId) | `% 추가지급!!    ` | Pending |
| `src\objects\fields\Field.java` | 7179 | Unknown (Method: log) | `sendObjectPlacement 오류 발생 리엑터` | Pending |
| `src\objects\fields\Field.java` | 7190 | Unknown (Method: log) | `sendObjectPlacement 오류 발생 몬스터` | Pending |
| `src\objects\fields\Field.java` | 7210 | Unknown (Method: log) | `sendObjectPlacement 오류 발생 sendSpawnData OBJType : ` | Pending |
| `src\objects\fields\Field.java` | 7216 | Unknown (Method: log) | `sendObjectPlacement 오류 발생 addVisibleMapObject` | Pending |
| `src\objects\fields\Field.java` | 7220 | Unknown (Method: log) | `sendObjectPlacement 오류 발생` | Pending |
| `src\objects\fields\Field.java` | 8684 | Unknown (Method: serverNotice) | `[술래잡기 알림]\r\n제한시간 2분이 지나 양이 승리하였습니다!\r\n모든 분들은 ...` | Pending |
| `src\objects\fields\Field.java` | 9043 | Unknown (Method: TextEffect) | `#fn나눔고딕 ExtraBold##fs26#    버닝 소멸!!    ` | Pending |
| `src\objects\fields\Field.java` | 9051 | Unknown (Method: TextEffect) | `#fn나눔고딕 ExtraBold##fs26#    버닝 ` | Pending |
| `src\objects\fields\Field.java` | 9053 | Unknown (Method: TextEffect) | `단계 : 경험치 ` | Pending |
| `src\objects\fields\Field.java` | 9055 | Unknown (Method: TextEffect) | `%로 감소!!    ` | Pending |
| `src\objects\fields\FieldMonsterSpawner.java` | 22 | Unknown (Method: setSpawnDesc) | `어디선가 커다란 버섯이 나타났습니다.` | Pending |
| `src\objects\fields\FieldMonsterSpawner.java` | 31 | Unknown (Method: setSpawnDesc) | `어디선가 커다란 파란 버섯이 나타났습니다.` | Pending |
| `src\objects\fields\FieldMonsterSpawner.java` | 40 | Unknown (Method: setSpawnDesc) | `어디선가 음산한 기운을 풍기는 커다란 버섯이 나타났습니다.` | Pending |
| `src\objects\fields\FieldMonsterSpawner.java` | 49 | Unknown (Method: setSpawnDesc) | `바위산을 울리는 발걸음 소리와 함께 스텀피가 나타났습니다.` | Pending |
| `src\objects\fields\FieldMonsterSpawner.java` | 58 | Unknown (Method: setSpawnDesc) | `늪 속에서 거대한 악어 다일이 올라왔습니다.` | Pending |
| `src\objects\fields\FieldMonsterSpawner.java` | 65 | Unknown (Method: setSpawnDesc) | `바이킹 군단이 나타났습니다.` | Pending |
| `src\objects\fields\FieldMonsterSpawner.java` | 72 | Unknown (Method: setSpawnDesc) | `검은 바이킹이 모습을 드러냈습니다.` | Pending |
| `src\objects\fields\FieldMonsterSpawner.java` | 79 | Unknown (Method: setSpawnDesc) | `약초밭 사이로 거대 도라지가 나타났습니다.` | Pending |
| `src\objects\fields\FieldMonsterSpawner.java` | 88 | Unknown (Method: setSpawnDesc) | `주변을 흐르는 요기가 강해졌습니다. 기분나쁜 고양이 울음소리가 들립니다.` | Pending |
| `src\objects\fields\FieldMonsterSpawner.java` | 95 | Unknown (Method: setSpawnDesc) | `두루마기에 몸을 숨겼던 대나무 무사가 모습을 드러냈습니다.` | Pending |
| `src\objects\fields\FieldMonsterSpawner.java` | 104 | Unknown (Method: setSpawnDesc) | `물밑에서 스멀스멀 대왕지네가 나타났습니다.` | Pending |
| `src\objects\fields\FieldMonsterSpawner.java` | 113 | Unknown (Method: setSpawnDesc) | `추억의 길에 도도가 나타났습니다.` | Pending |
| `src\objects\fields\FieldMonsterSpawner.java` | 122 | Unknown (Method: setSpawnDesc) | `후회의 길에 릴리노흐가 나타났습니다.` | Pending |
| `src\objects\fields\FieldMonsterSpawner.java` | 129 | Unknown (Method: setSpawnDesc) | `망각의 길에 라이카가 나타났습니다.` | Pending |
| `src\objects\fields\MapleMapFactory.java` | 1244 | Unknown (Method: getId) | `서늘한 기운이 감돌면서 마노가 나타났습니다.` | Pending |
| `src\objects\fields\MapScriptMethods.java` | 63 | Unknown (Method: Point) | `무릉도장에 도전한 것을 후회하게 해주겠다! 어서 들어와봐!` | Pending |
| `src\objects\fields\MapScriptMethods.java` | 63 | Unknown (Method: Point) | `기다리고 있었다! 용기가 남았다면 들어와 보시지!` | Pending |
| `src\objects\fields\MapScriptMethods.java` | 63 | Unknown (Method: Point) | `배짱 하나는 두둑하군! 현명함과 무모함을 혼동하지말라고!` | Pending |
| `src\objects\fields\MapScriptMethods.java` | 63 | Unknown (Method: Point) | `무릉도장에 도전하다니 용기가 가상하군!` | Pending |
| `src\objects\fields\MapScriptMethods.java` | 63 | Unknown (Method: Point) | `패배의 길을 걷고싶다면 들어오라고!` | Pending |
| `src\objects\fields\MapScriptMethods.java` | 1453 | Unknown (Method: startMapEffect) | `월묘가 나타났습니다. 제한시간 내에 힘을 합쳐 월묘를 잡으면 달맞이 보상을 획득할 수...` | Pending |
| `src\objects\fields\MapScriptMethods.java` | 1780 | Unknown (Method: startMapEffect) | `몬스터를 모두 퇴치해라!` | Pending |
| `src\objects\fields\MapScriptMethods.java` | 1783 | Unknown (Method: startMapEffect) | `상자를 부수고, 나오는 몬스터를 모두 퇴치해라!` | Pending |
| `src\objects\fields\MapScriptMethods.java` | 1786 | Unknown (Method: startMapEffect) | `일등항해사를 퇴치해라!` | Pending |
| `src\objects\fields\MapScriptMethods.java` | 1789 | Unknown (Method: startMapEffect) | `몬스터를 모두 퇴치해라!` | Pending |
| `src\objects\fields\MapScriptMethods.java` | 1792 | Unknown (Method: startMapEffect) | `몬스터를 모두 퇴치하고, 점프대를 작동시켜서 건너편으로 건너가라!` | Pending |
| `src\objects\fields\MapScriptMethods.java` | 1798 | Unknown (Method: startMapEffect) | `상대편보다 먼저 몬스터를 퇴치하라!` | Pending |
| `src\objects\fields\MapScriptMethods.java` | 2023 | Unknown (Method: addPopupSay) | `촉수가 눈을 방어하고 있어 제대로 된 피해를 주기 힘들겠군.` | Pending |
| `src\objects\fields\MapScriptMethods.java` | 2148 | Unknown (Method: addPopupSay) | `이 지역에서 발생되는 공격은 창조나 파괴의 저주를 거는 것 같다...만약 두 저주가 ...` | Pending |
| `src\objects\fields\MapScriptMethods.java` | 2150 | Unknown (Method: addPopupSay) | `#face1#가자. 나는 복수를, 너는 세계를 지키는 거야.` | Pending |
| `src\objects\fields\MapScriptMethods.java` | 2152 | Unknown (Method: addPopupSay) | `아무 것도 없는 공간…… 세계가 이런 모습이 되게 할 순 없어…` | Pending |
| `src\objects\fields\MapScriptMethods.java` | 2728 | Unknown (Method: showQuestMsg) | `개 지역 완료` | Pending |
| `src\objects\fields\child\arkarium\Field_Arkaium.java` | 92 | Unknown (Method: getScriptProgressMessage) | `시간의 여신 륀느가 봉인에서 풀려났습니다.` | Pending |
| `src\objects\fields\child\arkarium\Field_Arkaium.java` | 93 | Unknown (Method: startMapEffect) | `아카이럼을 퇴치하였습니다. 제단의 좌측 포탈을 통해 이동해주시기 바랍니다.` | Pending |
| `src\objects\fields\child\blackheaven\Field_BlackHeavenBoss.java` | 646 | Unknown (Method: getScriptProgressMessage) | `블랙헤븐의 코어가 침입자를 향해 공격을 시작합니다.` | Pending |
| `src\objects\fields\child\blackmage\Field_BlackMageBattlePhase4.java` | 118 | Unknown (Method: StringBuilder) | `보스 검은 마법사 격파 (` | Pending |
| `src\objects\fields\child\blackmage\Field_BlackMageBattlePhase4.java` | 129 | Unknown (Method: getChannel) | `20세 이상` | Pending |
| `src\objects\fields\child\blackmage\Field_BlackMageBattlePhase4.java` | 132 | Unknown (Method: getName) | `' 파티(` | Pending |
| `src\objects\fields\child\blackmage\Field_BlackMageBattlePhase4.java` | 134 | Unknown (Method: 파티) | `)가 [검은 마법사]를 격파하였습니다.` | Pending |
| `src\objects\fields\child\demian\Field_Demian.java` | 317 | Unknown (Method: gainItem) | `데미안 격파로 얻은 아이템` | Pending |
| `src\objects\fields\child\demian\Field_Demian.java` | 324 | Unknown (Method: StringBuilder) | `보스 노말 데미안 격파` | Pending |
| `src\objects\fields\child\demian\Field_Demian.java` | 369 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\child\demian\Field_Demian.java` | 369 | Variable/Other | `헬` | Pending |
| `src\objects\fields\child\demian\Field_Demian.java` | 369 | Variable/Other | `하드` | Pending |
| `src\objects\fields\child\demian\Field_Demian.java` | 369 | Variable/Other | ` 데미안 격파 (` | Pending |
| `src\objects\fields\child\demian\Field_Demian.java` | 386 | Unknown (Method: getChannel) | `20세 이상` | Pending |
| `src\objects\fields\child\demian\Field_Demian.java` | 389 | Unknown (Method: getName) | `' 파티(` | Pending |
| `src\objects\fields\child\demian\Field_Demian.java` | 391 | Unknown (Method: 파티) | `)가 [헬 데미안]을 격파하였습니다.` | Pending |
| `src\objects\fields\child\demian\Field_Demian.java` | 399 | Unknown (Method: getChannel) | `20세 이상` | Pending |
| `src\objects\fields\child\demian\Field_Demian.java` | 402 | Unknown (Method: getName) | `' 파티(` | Pending |
| `src\objects\fields\child\demian\Field_Demian.java` | 404 | Unknown (Method: 파티) | `)가 [하드 데미안]을 격파하였습니다.` | Pending |
| `src\objects\fields\child\demian\Field_Demian.java` | 413 | Unknown (Method: getChannel) | `20세 이상` | Pending |
| `src\objects\fields\child\demian\Field_Demian.java` | 416 | Unknown (Method: getName) | `' 파티(` | Pending |
| `src\objects\fields\child\demian\Field_Demian.java` | 418 | Unknown (Method: 파티) | `)가 [헬 데미안]을 격파하였습니다.` | Pending |
| `src\objects\fields\child\dojang\DojangRanking.java` | 295 | Unknown (Method: getName) | ` 캐릭터가 무릉 도장 랭킹 ` | Pending |
| `src\objects\fields\child\dojang\DojangRanking.java` | 295 | Unknown (Method: getRank) | `위로 보상이 정산되었습니다.` | Pending |
| `src\objects\fields\child\dojang\DojangRanking.java` | 296 | Unknown (Method: getRank) | `위 보상을 수령해주시기 바랍니다.` | Pending |
| `src\objects\fields\child\dojang\DojangRanking.java` | 316 | Unknown (Method: getName) | ` 캐릭터가 무릉 도장 랭킹 ` | Pending |
| `src\objects\fields\child\dojang\DojangRanking.java` | 316 | Unknown (Method: getRank) | `위로 보상이 정산되었습니다.` | Pending |
| `src\objects\fields\child\dojang\DojangRanking.java` | 348 | Unknown (Method: getName) | ` 캐릭터가 무릉 도장(챌린지) 랭킹 ` | Pending |
| `src\objects\fields\child\dojang\DojangRanking.java` | 348 | Unknown (Method: getRank) | `위로 보상이 정산되었습니다.` | Pending |
| `src\objects\fields\child\dojang\DojangRanking.java` | 349 | Unknown (Method: getRank) | `위 보상을 수령해주시기 바랍니다.` | Pending |
| `src\objects\fields\child\dojang\DojangRanking.java` | 369 | Unknown (Method: getName) | ` 캐릭터가 무릉 도장(챌린지) 랭킹 ` | Pending |
| `src\objects\fields\child\dojang\DojangRanking.java` | 369 | Unknown (Method: getRank) | `위로 보상이 정산되었습니다.` | Pending |
| `src\objects\fields\child\dojang\Field_Dojang.java` | 260 | Unknown (Method: startMapEffect) | `제한시간은 15분, 최대한 신속하게 몬스터를 쓰러트리고 다음 층으로 올라가면 돼!` | Pending |
| `src\objects\fields\child\dojang\Field_Dojang.java` | 270 | Unknown (Method: startMapEffect) | `사부님의 특별한 도법으로 모든 버프가 해제되었어. 이래야 좀 공평하지? 30초 줄테니...` | Pending |
| `src\objects\fields\child\dojang\Field_Dojang.java` | 298 | Unknown (Method: getScriptProgressMessage) | `상대를 격파하였습니다. 10초간 타이머가 정지됩니다.` | Pending |
| `src\objects\fields\child\dreambreaker\Field_DreamBreaker.java` | 210 | Unknown (Method: dreamBreakerMsg) | `드림포인트 ` | Pending |
| `src\objects\fields\child\dreambreaker\Field_DreamBreaker.java` | 210 | Unknown (Method: dreamBreakerMsg) | ` 획득!` | Pending |
| `src\objects\fields\child\dreambreaker\Field_DreamBreaker.java` | 369 | Unknown (Method: dreamBreakerMsg) | `스테이지 클리어!` | Pending |
| `src\objects\fields\child\etc\DamageMeasurementRank.java` | 337 | Unknown (Method: append) | `경 ` | Pending |
| `src\objects\fields\child\etc\DamageMeasurementRank.java` | 341 | Unknown (Method: append) | `조 ` | Pending |
| `src\objects\fields\child\etc\DamageMeasurementRank.java` | 345 | Unknown (Method: append) | `억 ` | Pending |
| `src\objects\fields\child\etc\DamageMeasurementRank.java` | 357 | Unknown (Method: append) | `위#n#k` | Pending |
| `src\objects\fields\child\etc\DamageMeasurementRank.java` | 359 | Unknown (Method: append) | `위#n#k` | Pending |
| `src\objects\fields\child\etc\DamageMeasurementRank.java` | 361 | Unknown (Method: append) | `위#n#k` | Pending |
| `src\objects\fields\child\etc\DamageMeasurementRank.java` | 376 | Unknown (Method: append) | `   #b전투력#k : #e` | Pending |
| `src\objects\fields\child\etc\DamageMeasurementRank.java` | 384 | Unknown (Method: append) | ` 삭제된 캐릭터\r\n` | Pending |
| `src\objects\fields\child\etc\DamageMeasurementRank.java` | 406 | Unknown (Method: append) | `위#n#k` | Pending |
| `src\objects\fields\child\etc\DamageMeasurementRank.java` | 408 | Unknown (Method: append) | `위#n#k` | Pending |
| `src\objects\fields\child\etc\DamageMeasurementRank.java` | 410 | Unknown (Method: append) | `위#n#k` | Pending |
| `src\objects\fields\child\etc\DamageMeasurementRank.java` | 425 | Unknown (Method: append) | `   #b전투력#k : #e` | Pending |
| `src\objects\fields\child\etc\DamageMeasurementRank.java` | 434 | Unknown (Method: append) | ` 삭제된 캐릭터\r\n` | Pending |
| `src\objects\fields\child\etc\Field_DamageMeasurement.java` | 73 | Unknown (Method: append) | `경 ` | Pending |
| `src\objects\fields\child\etc\Field_DamageMeasurement.java` | 77 | Unknown (Method: append) | `조 ` | Pending |
| `src\objects\fields\child\etc\Field_DamageMeasurement.java` | 81 | Unknown (Method: append) | `억 ` | Pending |
| `src\objects\fields\child\etc\Field_DamageMeasurement.java` | 85 | Unknown (Method: append) | `만 ` | Pending |
| `src\objects\fields\child\etc\Field_DamageMeasurement.java` | 134 | Unknown (Method: addPopupSay) | `#b[전투력 측정]#k\r\n측정이 시작되었습니다. 용사님의 진가를 발휘해볼까요?` | Pending |
| `src\objects\fields\child\etc\Field_DamageMeasurement.java` | 141 | Unknown (Method: addPopupSay) | `#b[전투력 측정]#k\r\n\r\n현재까지 용사님의 전투력은 #b` | Pending |
| `src\objects\fields\child\etc\Field_DamageMeasurement.java` | 141 | Unknown (Method: getUnit) | `#k 입니다.` | Pending |
| `src\objects\fields\child\etc\Field_DamageMeasurement.java` | 155 | Unknown (Method: addPopupSay) | `#b[전투력 측정]#k\r\n측정이 완료되었습니다.\r\n\r\n총 데미지 : #b` | Pending |
| `src\objects\fields\child\etc\Field_DamageMeasurement.java` | 155 | Unknown (Method: getUnit) | `#k\r\n\r\n전투력이 기록되었습니다. 기록은 랭킹에서 확인할 수 있습니다.` | Pending |
| `src\objects\fields\child\etc\Field_DamageMeasurement.java` | 221 | Unknown (Method: addPopupSay) | `#b[전투력 측정]#k\r\n잠시 후 전투력 측정이 시작됩니다.\n맵 좌측에 #b허수...` | Pending |
| `src\objects\fields\child\etc\Field_DamageMeasurement.java` | 231 | Unknown (Method: addPopupSay) | `#b[전투력 측정]#k\r\n잠시 후 전투력 측정이 시작됩니다.\n맵 우측에 #b허수...` | Pending |
| `src\objects\fields\child\etc\Field_DamageMeasurement.java` | 241 | Unknown (Method: addPopupSay) | `#b[전투력 측정]#k\r\n잠시 후 전투력 측정이 시작됩니다.\n맵 가운데에 #b허...` | Pending |
| `src\objects\fields\child\etc\Field_EventRabbit.java` | 79 | Unknown (Method: gainItem) | `추석 이벤트로 획득` | Pending |
| `src\objects\fields\child\etc\Field_EventRabbit.java` | 81 | Unknown (Method: addPopupSay) | `월묘를 처치하여 #b단감 코인 ` | Pending |
| `src\objects\fields\child\etc\Field_EventRabbit.java` | 81 | Unknown (Method: addPopupSay) | `개#k를 획득했습니다.` | Pending |
| `src\objects\fields\child\etc\Field_EventSnowman.java` | 78 | Unknown (Method: gainItem) | `크리스마스 이벤트로 획득` | Pending |
| `src\objects\fields\child\etc\Field_EventSnowman.java` | 81 | Unknown (Method: addPopupSay) | `거대 눈사람을 처치하여 #b메리 크리스마스 선물상자#k를 획득했습니다.` | Pending |
| `src\objects\fields\child\etc\Field_MMRace.java` | 59 | Unknown (Method: serverNotice) | `멍멍이 레이싱이 시작되었습니다.` | Pending |
| `src\objects\fields\child\etc\Field_MMRace.java` | 61 | Unknown (Method: setEventName) | `버기` | Pending |
| `src\objects\fields\child\etc\Field_MMRace.java` | 63 | Unknown (Method: setEventName) | `얼루` | Pending |
| `src\objects\fields\child\etc\Field_MMRace.java` | 65 | Unknown (Method: setEventName) | `그로돈` | Pending |
| `src\objects\fields\child\etc\Field_MMRace.java` | 67 | Unknown (Method: setEventName) | `쿤두라` | Pending |
| `src\objects\fields\child\etc\Field_MMRace.java` | 87 | Unknown (Method: serverNotice) | `게임 시작까지 ` | Pending |
| `src\objects\fields\child\etc\Field_MMRace.java` | 87 | Unknown (Method: serverNotice) | `초 남았습니다. 참여하지 않은 분들은 빨리 참여해 주세요!` | Pending |
| `src\objects\fields\child\etc\Field_MMRace.java` | 124 | Unknown (Method: serverNotice) | `제 ` | Pending |
| `src\objects\fields\child\etc\Field_MMRace.java` | 124 | Unknown (Method: serverNotice) | `회 운동회의 우승자는 '#` | Pending |
| `src\objects\fields\child\etc\Field_MMRace.java` | 124 | Unknown (Method: getEventName) | `' 입니다.` | Pending |
| `src\objects\fields\child\etc\Field_MMRace.java` | 166 | Unknown (Method: getEventName) | ` 슬로우에 걸렸습니다!` | Pending |
| `src\objects\fields\child\fritto\Field_CourtshipDance.java` | 47 | Unknown (Method: startMapEffect) | `달걀을 훔치려면 먼저 닭들은 속여야 해! 자, 나를 따라 구애의 춤을 춰!` | Pending |
| `src\objects\fields\child\fritto\Field_ReceivingTreasure.java` | 54 | Unknown (Method: startMapEffect) | `점프키와 방향키를 이용해 내가 떨어트리는 보물을 최대한 많이 받아줘!` | Pending |
| `src\objects\fields\child\fritto\Field_StealDragonsEgg.java` | 59 | Unknown (Method: startMapEffect) | `쉿! 이 둥지의 꼭대기에는 드래곤의 알이 숨겨져 있어. 꼭대기로 가는 길을 잘 찾아보라구!` | Pending |
| `src\objects\fields\child\jinhillah\Field_JinHillah.java` | 323 | Variable/Other | `진 힐라 격파로 얻은 아이템` | Pending |
| `src\objects\fields\child\jinhillah\Field_JinHillah.java` | 338 | Unknown (Method: StringBuilder) | `보스 하드 진 힐라 격파 (` | Pending |
| `src\objects\fields\child\jinhillah\Field_JinHillah.java` | 349 | Unknown (Method: getChannel) | `20세 이상` | Pending |
| `src\objects\fields\child\jinhillah\Field_JinHillah.java` | 352 | Unknown (Method: getName) | `' 파티(` | Pending |
| `src\objects\fields\child\jinhillah\Field_JinHillah.java` | 354 | Unknown (Method: 파티) | `)가 [하드 진 힐라]를 격파하였습니다.` | Pending |
| `src\objects\fields\child\karing\Field_BossGoongiPhase.java` | 123 | Unknown (Method: dropMessageGM) | `타입 : ` | Pending |
| `src\objects\fields\child\karing\TempPacket\Karing2PhasePacket.java` | 23 | Unknown (Method: writeMapleAsciiString) | `사흉을 모시깽이 합니다.` | Pending |
| `src\objects\fields\child\karrotte\Field_BossKalos.java` | 435 | Unknown (Method: brownMessage) | `T-boy의 간섭으로 구속의 눈이 잠에서 깨어납니다.` | Pending |
| `src\objects\fields\child\karrotte\Field_BossKalos.java` | 464 | Unknown (Method: brownMessage) | `T-boy의 간섭으로 포격 전투기가 활성화됩니다.` | Pending |
| `src\objects\fields\child\karrotte\Field_BossKalos.java` | 485 | Unknown (Method: brownMessage) | `T-boy의 간섭으로 오디움의 구체가 적을 감지합니다.` | Pending |
| `src\objects\fields\child\karrotte\Field_BossKalos.java` | 508 | Unknown (Method: brownMessage) | `T-boy의 간섭으로 심연의 눈이 잠에서 깨어납니다.` | Pending |
| `src\objects\fields\child\karrotte\Field_BossKalos.java` | 517 | Unknown (Method: brownMessage) | `자격을 확인 받지 못해 다음 관문으로 넘어가지 못하고 추방당했습니다.` | Pending |
| `src\objects\fields\child\karrotte\Field_BossKalos.java` | 518 | Unknown (Method: TextEffect) | `#r#fn나눔고딕 ExtraBold##fs26#자격이 없는 자에게 문은 열리지 않는다...` | Pending |
| `src\objects\fields\child\karrotte\Field_BossKalosPhase1.java` | 71 | Unknown (Method: TextEffect) | `#fn나눔고딕 ExtraBold##fs32##r#e아직 심판은 끝나지 않았다.` | Pending |
| `src\objects\fields\child\karrotte\Field_BossKalosPhase2.java` | 85 | Unknown (Method: TextEffect) | `#fn나눔고딕 ExtraBold##fs32##r#e여기까진가... 죄송합니다. 아버지...` | Pending |
| `src\objects\fields\child\karrotte\Field_BossKalosPhase2.java` | 109 | Unknown (Method: equals) | `이지` | Pending |
| `src\objects\fields\child\karrotte\Field_BossKalosPhase2.java` | 111 | Unknown (Method: equals) | `노말` | Pending |
| `src\objects\fields\child\karrotte\Field_BossKalosPhase2.java` | 113 | Unknown (Method: equals) | `카오스` | Pending |
| `src\objects\fields\child\karrotte\Field_BossKalosPhase2.java` | 115 | Unknown (Method: equals) | `익스트림` | Pending |
| `src\objects\fields\child\karrotte\Field_BossKalosPhase2.java` | 118 | Unknown (Method: equals) | `노말` | Pending |
| `src\objects\fields\child\karrotte\Field_BossKalosPhase2.java` | 122 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\child\karrotte\Field_BossKalosPhase2.java` | 122 | Unknown (Method: StringBuilder) | ` 칼로스 격파 (` | Pending |
| `src\objects\fields\child\karrotte\Field_BossKalosPhase2.java` | 133 | Unknown (Method: getChannel) | `20세 이상` | Pending |
| `src\objects\fields\child\karrotte\Field_BossKalosPhase2.java` | 136 | Unknown (Method: getName) | `' 파티(` | Pending |
| `src\objects\fields\child\karrotte\Field_BossKalosPhase2.java` | 138 | Unknown (Method: 파티) | `)가 [` | Pending |
| `src\objects\fields\child\karrotte\Field_BossKalosPhase2.java` | 140 | Unknown (Method: 파티) | ` 칼로스]를 격파하였습니다.` | Pending |
| `src\objects\fields\child\karrotte\Field_BossKalosPhase2.java` | 408 | Unknown (Method: TextEffect) | `#fn나눔고딕 ExtraBold##fs26##fc0xff71ffff#폭주에 휘말리지 ...` | Pending |
| `src\objects\fields\child\lucid\Field_LucidBattlePhase2.java` | 477 | Unknown (Method: getChannel) | `20세 이상` | Pending |
| `src\objects\fields\child\lucid\Field_LucidBattlePhase2.java` | 530 | Unknown (Method: getChannel) | `20세 이상` | Pending |
| `src\objects\fields\child\lucid\Field_LucidBattlePhase2.java` | 542 | Unknown (Method: getChannel) | `20세 이상` | Pending |
| `src\objects\fields\child\lucid\Field_LucidBattlePhase2.java` | 590 | Unknown (Method: getChannel) | `20세 이상` | Pending |
| `src\objects\fields\child\magnus\Field_Magnus.java` | 86 | Unknown (Method: getId) | `매그너스가 사망하여 방출된 에너지로 인해 더이상 구와르의 힘에 영향을 받지 않습니다.` | Pending |
| `src\objects\fields\child\magnus\Field_Magnus.java` | 296 | Unknown (Method: getId) | `매그너스가 구와르를 제어하는 힘이 약화 되었습니다. 구와르의 기운이 더욱 강해집니다.` | Pending |
| `src\objects\fields\child\magnus\Field_Magnus.java` | 302 | Unknown (Method: getId) | `매그너스가 구와르를 제어하는 힘이 약화 되었습니다. 구와르의 기운이 더욱 강해집니다.` | Pending |
| `src\objects\fields\child\magnus\Field_Magnus.java` | 305 | Unknown (Method: getId) | `매그너스가 구와르를 제어하는 힘이 약화 되었습니다. 구와르의 기운이 더욱 강해집니다.` | Pending |
| `src\objects\fields\child\minigame\battlereverse\BattleReverseGameDlg.java` | 47 | Unknown (Method: log) | `캐릭터가 null인 상태로 게임시작 시도` | Pending |
| `src\objects\fields\child\minigame\onecard\Field_OneCard.java` | 132 | Unknown (Method: onShowText) | `당신의 턴입니다.` | Pending |
| `src\objects\fields\child\minigame\onecard\Field_OneCard.java` | 272 | Unknown (Method: onShowText) | `마법 : 색 바꾸기!` | Pending |
| `src\objects\fields\child\minigame\onecard\Field_OneCard.java` | 275 | Unknown (Method: onShowText) | `마법 : 한 번 더!` | Pending |
| `src\objects\fields\child\minigame\onecard\Field_OneCard.java` | 278 | Unknown (Method: onShowText) | `마법 : 점프!` | Pending |
| `src\objects\fields\child\minigame\onecard\Field_OneCard.java` | 281 | Unknown (Method: onShowText) | `마법 : 거꾸로!` | Pending |
| `src\objects\fields\child\minigame\onecard\Field_OneCard.java` | 290 | Unknown (Method: onShowText) | `마법 : ` | Pending |
| `src\objects\fields\child\minigame\onecard\Field_OneCard.java` | 290 | Unknown (Method: getName) | `님의 공격!` | Pending |
| `src\objects\fields\child\minigame\onecard\Field_OneCard.java` | 401 | Unknown (Method: getName) | `님이 ` | Pending |
| `src\objects\fields\child\minigame\onecard\Field_OneCard.java` | 401 | Unknown (Method: getName) | `의 피해를 입었습니다.` | Pending |
| `src\objects\fields\child\minigame\soccer\Field_MultiSoccer.java` | 302 | Unknown (Method: addPopupSay) | `경기~~ 시작합니다~~~!!!` | Pending |
| `src\objects\fields\child\minigame\train\Field_TrainMaster.java` | 187 | Unknown (Method: addPopupSay) | `#b#e크리에이터들의\r\n기차 운행 도전!#n#k\r\n#r지금 바로 시작하겠네!#k` | Pending |
| `src\objects\fields\child\minigame\yutgame\Field_MultiYutGame.java` | 74 | Unknown (Method: getFoulCount) | `회. 5회 이상 시 퇴장 처리됩니다.` | Pending |
| `src\objects\fields\child\moonbridge\Field_FerociousBattlefield.java` | 345 | Variable/Other | `더스크 격파로 얻은 아이템` | Pending |
| `src\objects\fields\child\moonbridge\Field_FerociousBattlefield.java` | 357 | Unknown (Method: StringBuilder) | `보스 카오스 더스크 격파` | Pending |
| `src\objects\fields\child\moonbridge\Field_FerociousBattlefield.java` | 374 | Unknown (Method: getChannel) | `20세 이상` | Pending |
| `src\objects\fields\child\moonbridge\Field_FerociousBattlefield.java` | 377 | Unknown (Method: getName) | `' 파티(` | Pending |
| `src\objects\fields\child\moonbridge\Field_FerociousBattlefield.java` | 379 | Unknown (Method: 파티) | `)가 [카오스 더스크]를 격파하였습니다.` | Pending |
| `src\objects\fields\child\moonbridge\Field_FerociousBattlefield.java` | 387 | Unknown (Method: StringBuilder) | `보스 노말 더스크 격파` | Pending |
| `src\objects\fields\child\papulatus\Field_Papulatus.java` | 225 | Unknown (Method: getId) | `이지` | Pending |
| `src\objects\fields\child\papulatus\Field_Papulatus.java` | 227 | Unknown (Method: getId) | `노말` | Pending |
| `src\objects\fields\child\papulatus\Field_Papulatus.java` | 229 | Unknown (Method: getId) | `카오스` | Pending |
| `src\objects\fields\child\papulatus\Field_Papulatus.java` | 232 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\child\papulatus\Field_Papulatus.java` | 232 | Unknown (Method: StringBuilder) | ` 파풀라투스 격파` | Pending |
| `src\objects\fields\child\papulatus\Field_Papulatus.java` | 639 | Unknown (Method: startMapEffect) | `파풀라투스가 차원 이동을 통해서 HP를 회복하려고 시도합니다.` | Pending |
| `src\objects\fields\child\papulatus\Field_Papulatus.java` | 696 | Unknown (Method: startMapEffect) | `파풀라투스가 시간 이동을 할 수 없도록 차원의 균열을 봉인해야 합니다.` | Pending |
| `src\objects\fields\child\papulatus\Field_Papulatus.java` | 713 | Unknown (Method: startMapEffect) | `차원의 균열을 봉인했습니다.` | Pending |
| `src\objects\fields\child\papulatus\Field_Papulatus.java` | 735 | Unknown (Method: serverNotice) | `파풀라투스의 시계가 움직입니다.` | Pending |
| `src\objects\fields\child\papulatus\Field_Papulatus.java` | 736 | Unknown (Method: startMapEffect) | `파풀라투스의 시계가 움직입니다. 차원의 포탈을 통해 시간을 봉인하세요.` | Pending |
| `src\objects\fields\child\pollo\Field_BountyHunting.java` | 195 | Unknown (Method: startMapEffect) | `놈들이 사방에서 몰려오는군! 녀석들을 처치하면 막대한 경험치를 얻을 수 있다!` | Pending |
| `src\objects\fields\child\pollo\Field_FlameWolf.java` | 38 | Unknown (Method: startMapEffect) | `불꽃늑대를 처치할 용사가 늘었군. 어서 녀석을 공격해! 머무를 수 있는 시간은 30초...` | Pending |
| `src\objects\fields\child\pollo\Field_MidnightMonsterHunting.java` | 127 | Unknown (Method: startMapEffect) | `npc/채집 키로 공격을 할 수 있다. 몰려오는 놈들을 나와 함께 다 쓸어버리자고.` | Pending |
| `src\objects\fields\child\pollo\Field_StormwingArea.java` | 82 | Unknown (Method: startMapEffect) | `재빠른 황금빛 녀석이 스톰윙일세. 그 녀석을 잡으면 머물 수 있는 시간이 늘어나지!` | Pending |
| `src\objects\fields\child\pollo\Field_TownDefense.java` | 142 | Unknown (Method: getScriptProgressMessage) | `WAVE를 막아냈습니다. 다음 WAVE를 준비해주세요.` | Pending |
| `src\objects\fields\child\pollo\Field_TownDefense.java` | 170 | Unknown (Method: startMapEffect) | `놈들이 겁도 없이 성벽 안의 마을을 습격하는군! 모조리 해치워라!` | Pending |
| `src\objects\fields\child\pollo\Field_TownDefense.java` | 207 | Unknown (Method: getName) | `, 너의 사냥 실력이 이정도밖에 안됐나?` | Pending |
| `src\objects\fields\child\rimen\Field_RimenNearTheEnd.java` | 189 | Variable/Other | `듄켈 격파로 얻은 아이템` | Pending |
| `src\objects\fields\child\rimen\Field_RimenNearTheEnd.java` | 223 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\child\rimen\Field_RimenNearTheEnd.java` | 223 | Variable/Other | `헬` | Pending |
| `src\objects\fields\child\rimen\Field_RimenNearTheEnd.java` | 223 | Variable/Other | `하드` | Pending |
| `src\objects\fields\child\rimen\Field_RimenNearTheEnd.java` | 223 | Variable/Other | ` 듄켈 격파 (` | Pending |
| `src\objects\fields\child\rimen\Field_RimenNearTheEnd.java` | 235 | Unknown (Method: getChannel) | `20세 이상` | Pending |
| `src\objects\fields\child\rimen\Field_RimenNearTheEnd.java` | 238 | Unknown (Method: getName) | `' 파티(` | Pending |
| `src\objects\fields\child\rimen\Field_RimenNearTheEnd.java` | 240 | Unknown (Method: 파티) | `)가 [하드 듄켈]을 격파하였습니다.` | Pending |
| `src\objects\fields\child\rimen\Field_RimenNearTheEnd.java` | 248 | Unknown (Method: getChannel) | `20세 이상` | Pending |
| `src\objects\fields\child\rimen\Field_RimenNearTheEnd.java` | 251 | Unknown (Method: getName) | `' 파티(` | Pending |
| `src\objects\fields\child\rimen\Field_RimenNearTheEnd.java` | 253 | Unknown (Method: 파티) | `)가 [헬 듄켈]을 격파하였습니다.` | Pending |
| `src\objects\fields\child\rimen\Field_RimenNearTheEnd.java` | 259 | Unknown (Method: StringBuilder) | `보스 노말 듄켈 격파` | Pending |
| `src\objects\fields\child\rootabyss\Field_CrimsonQueen.java` | 155 | Unknown (Method: switch) | `내가 상대해주겠어요.` | Pending |
| `src\objects\fields\child\rootabyss\Field_CrimsonQueen.java` | 160 | Unknown (Method: switch) | `킥킥, 다 없애주지` | Pending |
| `src\objects\fields\child\rootabyss\Field_CrimsonQueen.java` | 165 | Unknown (Method: switch) | `모두 불태워주마!` | Pending |
| `src\objects\fields\child\rootabyss\Field_CrimsonQueen.java` | 170 | Unknown (Method: switch) | `내 고통을 느끼게 해줄게요.` | Pending |
| `src\objects\fields\child\sernium\Field_SerenPhase1.java` | 120 | Unknown (Method: TextEffect) | `#fn나눔고딕 Extrabold##fs25##r태양의 불꽃은 복수를 잊지 않는다.` | Pending |
| `src\objects\fields\child\sernium\Field_SerenPhase2.java` | 287 | Unknown (Method: serverNotice) | `태양의 빛으로 가득찬 정오가 시작됩니다.` | Pending |
| `src\objects\fields\child\sernium\Field_SerenPhase2.java` | 324 | Unknown (Method: TextEffect) | `#fn나눔고딕 Extrabold##fs25##r머지않아 태양은 다시 떠오를 것이다…` | Pending |
| `src\objects\fields\child\sernium\Field_SerenPhase2.java` | 373 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\child\sernium\Field_SerenPhase2.java` | 373 | Variable/Other | `노말` | Pending |
| `src\objects\fields\child\sernium\Field_SerenPhase2.java` | 373 | Variable/Other | `하드` | Pending |
| `src\objects\fields\child\sernium\Field_SerenPhase2.java` | 373 | Variable/Other | ` 세렌 격파 (` | Pending |
| `src\objects\fields\child\sernium\Field_SerenPhase2.java` | 385 | Unknown (Method: getChannel) | `20세 이상` | Pending |
| `src\objects\fields\child\sernium\Field_SerenPhase2.java` | 388 | Unknown (Method: getName) | `' 파티(` | Pending |
| `src\objects\fields\child\sernium\Field_SerenPhase2.java` | 390 | Unknown (Method: 파티) | `)가 [노말 세렌]을 격파하였습니다.` | Pending |
| `src\objects\fields\child\sernium\Field_SerenPhase2.java` | 399 | Unknown (Method: getChannel) | `20세 이상` | Pending |
| `src\objects\fields\child\sernium\Field_SerenPhase2.java` | 402 | Unknown (Method: getName) | `' 파티(` | Pending |
| `src\objects\fields\child\sernium\Field_SerenPhase2.java` | 404 | Unknown (Method: 파티) | `)가 [하드 세렌]을 격파하였습니다.` | Pending |
| `src\objects\fields\child\sernium\Field_SerenPhase2.java` | 594 | Unknown (Method: serverNotice) | `황혼의 불타는 듯한 석양이 회복 효율을 낮추고 지속적으로 피해를 입힙니다.` | Pending |
| `src\objects\fields\child\sernium\Field_SerenPhase2.java` | 600 | Unknown (Method: serverNotice) | `태양이 저물어 빛을 잃고 자정이 시작됩니다.` | Pending |
| `src\objects\fields\child\sernium\Field_SerenPhase2.java` | 602 | Unknown (Method: serverNotice) | `태양이 서서히 떠올라 빛과 희망이 시작되는 여명이 다가옵니다.` | Pending |
| `src\objects\fields\child\sernium\Field_SerenPhase2.java` | 635 | Unknown (Method: serverNotice) | `정오가 시작됨과 동시에 남아있는 여명의 기운이 세렌을 회복시킵니다.` | Pending |
| `src\objects\fields\child\sernium\Field_SerenPhase2.java` | 761 | Unknown (Method: TextEffect) | `#fn나눔고딕 Extrabold##fs25##r태양이 지지 않는다면 누구도 나에게 대...` | Pending |
| `src\objects\fields\child\slime\Field_GuardianAngelSlime.java` | 115 | Unknown (Method: addPopupSay) | `왕관의 수호로 제대로 된 피해를 입힐 수 없겠군. 마스코트 슬라임에게 도움을 받아 마...` | Pending |
| `src\objects\fields\child\slime\Field_GuardianAngelSlime.java` | 177 | Unknown (Method: writeLog) | `카오스 가디언 엔젤 슬라임` | Pending |
| `src\objects\fields\child\slime\Field_GuardianAngelSlime.java` | 177 | Unknown (Method: writeLog) | `노말 가디언 엔젤 슬라임` | Pending |
| `src\objects\fields\child\slime\Field_GuardianAngelSlime.java` | 184 | Unknown (Method: append) | `보스 ` | Pending |
| `src\objects\fields\child\slime\Field_GuardianAngelSlime.java` | 186 | Unknown (Method: append) | ` 격파` | Pending |
| `src\objects\fields\child\slime\Field_GuardianAngelSlime.java` | 199 | Unknown (Method: getChannel) | `20세 이상` | Pending |
| `src\objects\fields\child\slime\Field_GuardianAngelSlime.java` | 202 | Unknown (Method: getName) | `' 파티(` | Pending |
| `src\objects\fields\child\slime\Field_GuardianAngelSlime.java` | 204 | Unknown (Method: 파티) | `)가 [` | Pending |
| `src\objects\fields\child\slime\Field_GuardianAngelSlime.java` | 206 | Unknown (Method: 파티) | `] 을 격파하였습니다.` | Pending |
| `src\objects\fields\child\union\MapleUnionData.java` | 137 | Unknown (Method: getRankName) | `단계` | Pending |
| `src\objects\fields\child\union\MapleUnionData.java` | 141 | Unknown (Method: isEmpty) | `노비스 유니온` | Pending |
| `src\objects\fields\child\vonleon\Field_VonLeon.java` | 54 | Unknown (Method: startMapEffect) | `반 레온을 물리치셨습니다. 왼쪽 포탈을 통해 이동해주시기 바랍니다.` | Pending |
| `src\objects\fields\child\will\Field_WillBattleReward.java` | 110 | Variable/Other | `윌 격파로 얻은 아이템` | Pending |
| `src\objects\fields\child\will\Field_WillBattleReward.java` | 111 | Unknown (Method: gainItem) | `윌 격파로 얻은 아이템` | Pending |
| `src\objects\fields\child\will\Field_WillBattleReward.java` | 126 | Unknown (Method: StringBuilder) | `보스 노말 윌 격파` | Pending |
| `src\objects\fields\child\will\Field_WillBattleReward.java` | 140 | Unknown (Method: getName) | `' 파티(` | Pending |
| `src\objects\fields\child\will\Field_WillBattleReward.java` | 140 | Unknown (Method: 파티) | `)가 [노말 윌]을 격파하였습니다.` | Pending |
| `src\objects\fields\child\will\Field_WillBattleReward.java` | 184 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\child\will\Field_WillBattleReward.java` | 184 | Variable/Other | `헬` | Pending |
| `src\objects\fields\child\will\Field_WillBattleReward.java` | 184 | Variable/Other | `하드` | Pending |
| `src\objects\fields\child\will\Field_WillBattleReward.java` | 184 | Variable/Other | ` 윌 격파 (` | Pending |
| `src\objects\fields\child\will\Field_WillBattleReward.java` | 197 | Unknown (Method: getChannel) | `20세 이상` | Pending |
| `src\objects\fields\child\will\Field_WillBattleReward.java` | 200 | Unknown (Method: getName) | `' 파티(` | Pending |
| `src\objects\fields\child\will\Field_WillBattleReward.java` | 202 | Unknown (Method: 파티) | `)가 [헬 윌]를 격파하였습니다.` | Pending |
| `src\objects\fields\child\will\Field_WillBattleReward.java` | 210 | Unknown (Method: getChannel) | `20세 이상` | Pending |
| `src\objects\fields\child\will\Field_WillBattleReward.java` | 213 | Unknown (Method: getName) | `' 파티(` | Pending |
| `src\objects\fields\child\will\Field_WillBattleReward.java` | 215 | Unknown (Method: 파티) | `)가 [하드 윌]를 격파하였습니다.` | Pending |
| `src\objects\fields\fieldset\FieldSet.java` | 171 | Unknown (Method: equals) | `헬` | Pending |
| `src\objects\fields\fieldset\FieldSet.java` | 176 | Unknown (Method: equals) | `헬` | Pending |
| `src\objects\fields\fieldset\childs\ChaosDuskEnter.java` | 27 | Unknown (Method: asList) | `더스크` | Pending |
| `src\objects\fields\fieldset\childs\ChaosDuskEnter.java` | 28 | Unknown (Method: asList) | `카오스` | Pending |
| `src\objects\fields\fieldset\childs\ChaosDuskEnter.java` | 120 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\ChaosDuskEnter.java` | 123 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\ChaosDuskEnter.java` | 123 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\ChaosKalosEnter.java` | 19 | Unknown (Method: asList) | `칼로스` | Pending |
| `src\objects\fields\fieldset\childs\ChaosKalosEnter.java` | 20 | Unknown (Method: asList) | `카오스` | Pending |
| `src\objects\fields\fieldset\childs\ChaosZakumEnter.java` | 133 | Unknown (Method: format) | `금일 %s 입장횟수 %d / %d` | Pending |
| `src\objects\fields\fieldset\childs\ChaosZakumEnter.java` | 133 | Unknown (Method: format) | `자쿰` | Pending |
| `src\objects\fields\fieldset\childs\ChaosZakumEnter.java` | 194 | Unknown (Method: getMinutes) | `분)` | Pending |
| `src\objects\fields\fieldset\childs\ChaosZakumEnter.java` | 198 | Unknown (Method: catch) | `알 수 없음` | Pending |
| `src\objects\fields\fieldset\childs\ChaosZakumEnter.java` | 204 | Unknown (Method: catch) | `알 수 없음` | Pending |
| `src\objects\fields\fieldset\childs\EasyKalosEnter.java` | 19 | Unknown (Method: asList) | `칼로스` | Pending |
| `src\objects\fields\fieldset\childs\EasyKalosEnter.java` | 20 | Unknown (Method: asList) | `이지` | Pending |
| `src\objects\fields\fieldset\childs\EasyKaringEnter.java` | 27 | Unknown (Method: asList) | `카링` | Pending |
| `src\objects\fields\fieldset\childs\EasyKaringEnter.java` | 28 | Unknown (Method: asList) | `이지` | Pending |
| `src\objects\fields\fieldset\childs\EasyKaringEnter.java` | 120 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\EasyKaringEnter.java` | 123 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\EasyKaringEnter.java` | 123 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\EasyWillEnter.java` | 28 | Unknown (Method: asList) | `윌` | Pending |
| `src\objects\fields\fieldset\childs\EasyWillEnter.java` | 29 | Unknown (Method: asList) | `이지` | Pending |
| `src\objects\fields\fieldset\childs\EasyWillEnter.java` | 121 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\EasyWillEnter.java` | 124 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\EasyWillEnter.java` | 124 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\ExtremeKalosEnter.java` | 19 | Unknown (Method: asList) | `칼로스` | Pending |
| `src\objects\fields\fieldset\childs\ExtremeKalosEnter.java` | 20 | Unknown (Method: asList) | `익스트림` | Pending |
| `src\objects\fields\fieldset\childs\ExtremeKaringEnter.java` | 27 | Unknown (Method: asList) | `카링` | Pending |
| `src\objects\fields\fieldset\childs\ExtremeKaringEnter.java` | 28 | Unknown (Method: asList) | `이지` | Pending |
| `src\objects\fields\fieldset\childs\ExtremeKaringEnter.java` | 120 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\ExtremeKaringEnter.java` | 123 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\ExtremeKaringEnter.java` | 123 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\HardBlackHeavenBossEnter.java` | 27 | Unknown (Method: asList) | `스우` | Pending |
| `src\objects\fields\fieldset\childs\HardBlackHeavenBossEnter.java` | 28 | Unknown (Method: asList) | `하드` | Pending |
| `src\objects\fields\fieldset\childs\HardBlackHeavenBossEnter.java` | 125 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\HardBlackHeavenBossEnter.java` | 128 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\HardBlackHeavenBossEnter.java` | 128 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\HardBlackMageEnter.java` | 32 | Unknown (Method: asList) | `검은마법사` | Pending |
| `src\objects\fields\fieldset\childs\HardBlackMageEnter.java` | 33 | Unknown (Method: asList) | `하드` | Pending |
| `src\objects\fields\fieldset\childs\HardBlackMageEnter.java` | 121 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\HardBlackMageEnter.java` | 124 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\HardBlackMageEnter.java` | 124 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\HardDemianEnter.java` | 27 | Unknown (Method: asList) | `데미안` | Pending |
| `src\objects\fields\fieldset\childs\HardDemianEnter.java` | 28 | Unknown (Method: asList) | `하드` | Pending |
| `src\objects\fields\fieldset\childs\HardDemianEnter.java` | 126 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\HardDemianEnter.java` | 129 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\HardDemianEnter.java` | 129 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\HardDunkelEnter.java` | 27 | Unknown (Method: asList) | `듄켈` | Pending |
| `src\objects\fields\fieldset\childs\HardDunkelEnter.java` | 28 | Unknown (Method: asList) | `하드` | Pending |
| `src\objects\fields\fieldset\childs\HardDunkelEnter.java` | 120 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\HardDunkelEnter.java` | 123 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\HardDunkelEnter.java` | 123 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\HardGuardianSlimeEnter.java` | 27 | Unknown (Method: asList) | `가디언 엔젤 슬라임` | Pending |
| `src\objects\fields\fieldset\childs\HardGuardianSlimeEnter.java` | 28 | Unknown (Method: asList) | `카오스` | Pending |
| `src\objects\fields\fieldset\childs\HardGuardianSlimeEnter.java` | 120 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\HardGuardianSlimeEnter.java` | 123 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\HardGuardianSlimeEnter.java` | 123 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\HardJinHillahEnter.java` | 27 | Unknown (Method: asList) | `진 힐라` | Pending |
| `src\objects\fields\fieldset\childs\HardJinHillahEnter.java` | 28 | Unknown (Method: asList) | `하드` | Pending |
| `src\objects\fields\fieldset\childs\HardJinHillahEnter.java` | 127 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\HardJinHillahEnter.java` | 130 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\HardJinHillahEnter.java` | 130 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\HardKaringEnter.java` | 27 | Unknown (Method: asList) | `카링` | Pending |
| `src\objects\fields\fieldset\childs\HardKaringEnter.java` | 28 | Unknown (Method: asList) | `이지` | Pending |
| `src\objects\fields\fieldset\childs\HardKaringEnter.java` | 120 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\HardKaringEnter.java` | 123 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\HardKaringEnter.java` | 123 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\HardLucidEnter.java` | 28 | Unknown (Method: asList) | `루시드` | Pending |
| `src\objects\fields\fieldset\childs\HardLucidEnter.java` | 29 | Unknown (Method: asList) | `하드` | Pending |
| `src\objects\fields\fieldset\childs\HardLucidEnter.java` | 143 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\HardLucidEnter.java` | 146 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\HardLucidEnter.java` | 146 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\HardSerenEnter.java` | 28 | Unknown (Method: getQuestID) | `세렌` | Pending |
| `src\objects\fields\fieldset\childs\HardSerenEnter.java` | 29 | Unknown (Method: getQuestID) | `하드` | Pending |
| `src\objects\fields\fieldset\childs\HardSerenEnter.java` | 121 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\HardSerenEnter.java` | 124 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\HardSerenEnter.java` | 124 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\HardWillEnter.java` | 28 | Unknown (Method: asList) | `윌` | Pending |
| `src\objects\fields\fieldset\childs\HardWillEnter.java` | 29 | Unknown (Method: asList) | `하드` | Pending |
| `src\objects\fields\fieldset\childs\HardWillEnter.java` | 135 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\HardWillEnter.java` | 138 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\HardWillEnter.java` | 138 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\HellBlackHeavenBossEnter.java` | 28 | Unknown (Method: asList) | `스우` | Pending |
| `src\objects\fields\fieldset\childs\HellBlackHeavenBossEnter.java` | 29 | Unknown (Method: asList) | `헬` | Pending |
| `src\objects\fields\fieldset\childs\HellBlackHeavenBossEnter.java` | 118 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\HellBlackHeavenBossEnter.java` | 121 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\HellBlackHeavenBossEnter.java` | 121 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\HellDemianEnter.java` | 28 | Unknown (Method: asList) | `데미안` | Pending |
| `src\objects\fields\fieldset\childs\HellDemianEnter.java` | 29 | Unknown (Method: asList) | `헬` | Pending |
| `src\objects\fields\fieldset\childs\HellDemianEnter.java` | 105 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\HellDemianEnter.java` | 105 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\HellDunkelEnter.java` | 28 | Unknown (Method: asList) | `듄켈` | Pending |
| `src\objects\fields\fieldset\childs\HellDunkelEnter.java` | 29 | Unknown (Method: asList) | `헬` | Pending |
| `src\objects\fields\fieldset\childs\HellDunkelEnter.java` | 113 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\HellDunkelEnter.java` | 116 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\HellDunkelEnter.java` | 116 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\HellJinHillahEnter.java` | 27 | Unknown (Method: asList) | `진힐라` | Pending |
| `src\objects\fields\fieldset\childs\HellJinHillahEnter.java` | 28 | Unknown (Method: asList) | `헬` | Pending |
| `src\objects\fields\fieldset\childs\HellJinHillahEnter.java` | 91 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\HellJinHillahEnter.java` | 91 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\HellLucidEnter.java` | 28 | Unknown (Method: asList) | `루시드` | Pending |
| `src\objects\fields\fieldset\childs\HellLucidEnter.java` | 29 | Unknown (Method: asList) | `헬` | Pending |
| `src\objects\fields\fieldset\childs\HellLucidEnter.java` | 112 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\HellLucidEnter.java` | 115 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\HellLucidEnter.java` | 115 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\HellWillEnter.java` | 29 | Unknown (Method: asList) | `윌` | Pending |
| `src\objects\fields\fieldset\childs\HellWillEnter.java` | 30 | Unknown (Method: asList) | `헬` | Pending |
| `src\objects\fields\fieldset\childs\HellWillEnter.java` | 104 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\HellWillEnter.java` | 104 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\MitsuhideEnter.java` | 27 | Unknown (Method: asList) | `미츠히데` | Pending |
| `src\objects\fields\fieldset\childs\MitsuhideEnter.java` | 28 | Unknown (Method: asList) | `하드` | Pending |
| `src\objects\fields\fieldset\childs\MitsuhideEnter.java` | 107 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\MitsuhideEnter.java` | 110 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\MitsuhideEnter.java` | 110 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\NormalBlackHeavenBossEnter.java` | 32 | Unknown (Method: if) | `스우` | Pending |
| `src\objects\fields\fieldset\childs\NormalBlackHeavenBossEnter.java` | 33 | Unknown (Method: if) | `노말` | Pending |
| `src\objects\fields\fieldset\childs\NormalBlackHeavenBossEnter.java` | 124 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\NormalBlackHeavenBossEnter.java` | 127 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\NormalBlackHeavenBossEnter.java` | 127 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\NormalDemianEnter.java` | 32 | Unknown (Method: if) | `데미안` | Pending |
| `src\objects\fields\fieldset\childs\NormalDemianEnter.java` | 33 | Unknown (Method: if) | `노말` | Pending |
| `src\objects\fields\fieldset\childs\NormalDemianEnter.java` | 125 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\NormalDemianEnter.java` | 128 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\NormalDemianEnter.java` | 128 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\NormalDunkelEnter.java` | 27 | Unknown (Method: asList) | `듄켈` | Pending |
| `src\objects\fields\fieldset\childs\NormalDunkelEnter.java` | 28 | Unknown (Method: asList) | `노말` | Pending |
| `src\objects\fields\fieldset\childs\NormalDunkelEnter.java` | 120 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\NormalDunkelEnter.java` | 123 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\NormalDunkelEnter.java` | 123 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\NormalDuskEnter.java` | 32 | Unknown (Method: if) | `더스크` | Pending |
| `src\objects\fields\fieldset\childs\NormalDuskEnter.java` | 33 | Unknown (Method: if) | `노말` | Pending |
| `src\objects\fields\fieldset\childs\NormalDuskEnter.java` | 125 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\NormalDuskEnter.java` | 128 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\NormalDuskEnter.java` | 128 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\NormalGuardianSlimeEnter.java` | 32 | Unknown (Method: if) | `가디언 엔젤 슬라임` | Pending |
| `src\objects\fields\fieldset\childs\NormalGuardianSlimeEnter.java` | 33 | Unknown (Method: if) | `노말` | Pending |
| `src\objects\fields\fieldset\childs\NormalGuardianSlimeEnter.java` | 125 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\NormalGuardianSlimeEnter.java` | 128 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\NormalGuardianSlimeEnter.java` | 128 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\NormalJinHillahEnter.java` | 27 | Unknown (Method: asList) | `진힐라` | Pending |
| `src\objects\fields\fieldset\childs\NormalJinHillahEnter.java` | 28 | Unknown (Method: asList) | `노말` | Pending |
| `src\objects\fields\fieldset\childs\NormalJinHillahEnter.java` | 113 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\NormalJinHillahEnter.java` | 116 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\NormalJinHillahEnter.java` | 116 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\NormalKalosEnter.java` | 26 | Unknown (Method: asList) | `칼로스` | Pending |
| `src\objects\fields\fieldset\childs\NormalKalosEnter.java` | 27 | Unknown (Method: asList) | `노말` | Pending |
| `src\objects\fields\fieldset\childs\NormalKalosEnter.java` | 85 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\NormalKalosEnter.java` | 88 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\NormalKalosEnter.java` | 88 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\NormalKaringEnter.java` | 28 | Unknown (Method: asList) | `카링` | Pending |
| `src\objects\fields\fieldset\childs\NormalKaringEnter.java` | 29 | Unknown (Method: asList) | `노말` | Pending |
| `src\objects\fields\fieldset\childs\NormalKaringEnter.java` | 122 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\NormalKaringEnter.java` | 125 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\NormalKaringEnter.java` | 125 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\NormalLucidEnter.java` | 32 | Unknown (Method: if) | `루시드` | Pending |
| `src\objects\fields\fieldset\childs\NormalLucidEnter.java` | 33 | Unknown (Method: if) | `노말` | Pending |
| `src\objects\fields\fieldset\childs\NormalLucidEnter.java` | 118 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\NormalLucidEnter.java` | 121 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\NormalLucidEnter.java` | 121 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\NormalSerenEnter.java` | 28 | Unknown (Method: getQuestID) | `세렌` | Pending |
| `src\objects\fields\fieldset\childs\NormalSerenEnter.java` | 29 | Unknown (Method: getQuestID) | `노말` | Pending |
| `src\objects\fields\fieldset\childs\NormalSerenEnter.java` | 121 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\NormalSerenEnter.java` | 124 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\NormalSerenEnter.java` | 124 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\NormalWillEnter.java` | 33 | Unknown (Method: if) | `윌` | Pending |
| `src\objects\fields\fieldset\childs\NormalWillEnter.java` | 34 | Unknown (Method: if) | `노말` | Pending |
| `src\objects\fields\fieldset\childs\NormalWillEnter.java` | 126 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\NormalWillEnter.java` | 129 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\NormalWillEnter.java` | 129 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\childs\TenguEnter.java` | 27 | Unknown (Method: asList) | `텐구` | Pending |
| `src\objects\fields\fieldset\childs\TenguEnter.java` | 28 | Unknown (Method: asList) | `하드` | Pending |
| `src\objects\fields\fieldset\childs\TenguEnter.java` | 117 | Unknown (Method: if) | `(연습)` | Pending |
| `src\objects\fields\fieldset\childs\TenguEnter.java` | 120 | Unknown (Method: StringBuilder) | `보스 ` | Pending |
| `src\objects\fields\fieldset\childs\TenguEnter.java` | 120 | Unknown (Method: StringBuilder) | ` 입장` | Pending |
| `src\objects\fields\fieldset\instance\Culvert.java` | 97 | Unknown (Method: addPopupSay) | `#rSTAGE : 2#k\r\n아르카누스의 힘이 더욱 강해지고 있습니다!` | Pending |
| `src\objects\fields\fieldset\instance\Culvert.java` | 106 | Unknown (Method: addPopupSay) | `#rSTAGE : 3#k\r\n아르카누스의 힘이 더욱 강해지고 있습니다!` | Pending |
| `src\objects\fields\fieldset\instance\Culvert.java` | 115 | Unknown (Method: addPopupSay) | `#rSTAGE : 4#k\r\n아르카누스의 힘이 더욱 강해지고 있습니다!` | Pending |
| `src\objects\fields\fieldset\instance\Culvert.java` | 124 | Unknown (Method: addPopupSay) | `#rSTAGE : 5#k\r\n아르카누스의 힘이 더욱 강해지고 있습니다!` | Pending |
| `src\objects\fields\fieldset\instance\Culvert.java` | 133 | Unknown (Method: addPopupSay) | `#rSTAGE : 6#k\r\n아르카누스의 힘이 더욱 강해지고 있습니다!` | Pending |
| `src\objects\fields\fieldset\instance\Culvert.java` | 142 | Unknown (Method: addPopupSay) | `#r아르카누스#k를 무찔러서 5초뒤 퇴장맵으로 이동됩니다!` | Pending |
| `src\objects\fields\fieldset\instance\FlagRace.java` | 125 | Unknown (Method: startMapEffect) | `사이먼의 멋--진 월드에 오신 것을 환영합니다!` | Pending |
| `src\objects\fields\fieldset\instance\FlagRace.java` | 136 | Unknown (Method: startMapEffect) | `경기장을 수놓는 아름다운 플래그 스타들을 배치하고 있습니다. 잠시만 기다려주세요!` | Pending |
| `src\objects\fields\fieldset\instance\FlagRace.java` | 147 | Unknown (Method: startMapEffect) | `곧 플래그 레이스 경기장으로 이동합니다. 이제 정말 준비해 주세요!` | Pending |
| `src\objects\fields\fieldset\instance\FlagRace.java` | 158 | Unknown (Method: startMapEffect) | `곧 플래그 레이스 경기장으로 이동합니다. 이제 정말 준비해 주세요!` | Pending |
| `src\objects\fields\fieldset\instance\PuzzleMaster.java` | 136 | Unknown (Method: addPopupSay) | `#b#e크리에이터들의\r\n퍼즐 맞추기 도전!#n#k\r\n#r지금 바로 시작합니다!#k` | Pending |
| `src\objects\fields\fieldset\instance\SpiritSavior.java` | 128 | Unknown (Method: startMapEffect) | `맹독의 정령이 눈치챈 모양이야! 어서 도망가람!` | Pending |
| `src\objects\fields\fieldset\instance\SpiritSavior.java` | 144 | Unknown (Method: startMapEffect) | `맹독의 정령이 더 강해지고 있담..!` | Pending |
| `src\objects\fields\fieldset\instance\SpiritSavior.java` | 157 | Unknown (Method: startMapEffect) | `맹독의 정령이 더 강해지고 있담..!` | Pending |
| `src\objects\fields\fieldset\instance\SpiritSavior.java` | 171 | Unknown (Method: startMapEffect) | `맹독의 정령이 더 강해지고 있담..!` | Pending |
| `src\objects\fields\fieldset\instance\SpiritSavior.java` | 185 | Unknown (Method: startMapEffect) | `맹독의 정령이 완전체가 되었담! 조심해람!` | Pending |
| `src\objects\fields\fieldset\instance\SpiritSavior.java` | 283 | Unknown (Method: startMapEffect) | `친구들이... 맹독의 정령에게 당하고 말았담!` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 164 | Unknown (Method: brownMessage) | `번 주문 음식이 완성되었습니다.` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 180 | Unknown (Method: addPopupSay) | `레시피를 제대로 보란 말일세!` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 183 | Unknown (Method: addPopupSay) | `빠르다고 다가 아니네!\r\n제대로 하게!` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 186 | Unknown (Method: addPopupSay) | `지금 뭐 하는 건가!` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 194 | Unknown (Method: brownMessage) | `조리에 실패했습니다. 레시피와 동일한 재료를 놓거나 알맞은 가공을 시도해주세요.` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 232 | Unknown (Method: brownMessage) | `번 주문 배달에 성공했습니다. ` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 232 | Unknown (Method: brownMessage) | `메소를 획득했습니다.` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 237 | Unknown (Method: getId) | `아주 훌륭하군!` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 240 | Unknown (Method: rand) | `자네, 나와 함께 일해보지 않겠나?` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 243 | Unknown (Method: rand) | `신속하군! 아주 좋네!` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 248 | Unknown (Method: startMapEffect) | `고생 많았네, 음식의 소중함을 깨달았기를 바라네.` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 336 | Unknown (Method: startMapEffect) | `주문이 취소되었네!` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 399 | Unknown (Method: startMapEffect) | `주문이 취소되었네!` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 462 | Unknown (Method: startMapEffect) | `주문이 취소되었네!` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 682 | Unknown (Method: writeMapleAsciiString) | `빵` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 692 | Unknown (Method: writeMapleAsciiString) | `고기` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 702 | Unknown (Method: writeMapleAsciiString) | `계란` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 712 | Unknown (Method: writeMapleAsciiString) | `채소` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 722 | Unknown (Method: writeMapleAsciiString) | `생선` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 732 | Unknown (Method: writeMapleAsciiString) | `굽기` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 742 | Unknown (Method: writeMapleAsciiString) | `끓이기` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 752 | Unknown (Method: writeMapleAsciiString) | `썰기` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 794 | Unknown (Method: writeMapleAsciiString) | `1번 손님` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 804 | Unknown (Method: writeMapleAsciiString) | `2번 손님` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 814 | Unknown (Method: writeMapleAsciiString) | `3번 손님` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 824 | Unknown (Method: writeMapleAsciiString) | `4번 손님` | Pending |
| `src\objects\fields\fieldset\instance\TangyoonKitchen.java` | 834 | Unknown (Method: writeMapleAsciiString) | `5번 손님` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 4 | Unknown (Method: Prefix_0) | `힘 센` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 5 | Unknown (Method: Prefix_1) | `튼튼한` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 6 | Unknown (Method: Prefix_2) | `마법저항의` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 7 | Unknown (Method: Prefix_3) | `재생하는` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 8 | Unknown (Method: Prefix_4) | `재빠른` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 9 | Unknown (Method: Prefix_5) | `봉인의` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 10 | Unknown (Method: Prefix_6) | `회피하는` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 11 | Unknown (Method: Prefix_7) | `허약의` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 12 | Unknown (Method: Prefix_8) | `기절시키는` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 13 | Unknown (Method: Prefix_9) | `저주의` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 14 | Unknown (Method: Prefix_10) | `맹독의` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 15 | Unknown (Method: Prefix_11) | `끈끈한` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 16 | Unknown (Method: Prefix_12) | `매혹의` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 17 | Unknown (Method: Prefix_13) | `독을 뿌리는` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 18 | Unknown (Method: Prefix_14) | `혼란의` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 19 | Unknown (Method: Prefix_15) | `언데드` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 20 | Unknown (Method: Prefix_16) | `포션을 싫어하는` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 21 | Unknown (Method: Prefix_17) | `멈추지 않는` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 22 | Unknown (Method: Prefix_18) | `암흑의` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 23 | Unknown (Method: Prefix_19) | `단단한` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 24 | Unknown (Method: Prefix_20) | `반사의` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 25 | Unknown (Method: Prefix_21) | `무적의` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 26 | Unknown (Method: Prefix_22) | `변신술사` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 27 | Unknown (Method: Prefix_23) | `석화의` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 28 | Unknown (Method: Prefix_24) | `자석의` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 29 | Unknown (Method: Prefix_25) | `역병의` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 30 | Unknown (Method: Prefix_26) | `지휘관` | Pending |
| `src\objects\fields\gameobject\lifes\ElitePrefix.java` | 31 | Unknown (Method: Prefix_27) | `검은 사슬의` | Pending |
| `src\objects\fields\gameobject\lifes\MapleMonster.java` | 741 | Unknown (Method: getAccountID) | `)이(가) 루시드 격파 없이 마지막 오르골을 공격함` | Pending |
| `src\objects\fields\gameobject\lifes\MapleMonster.java` | 747 | Unknown (Method: getAccountID) | `)이(가) 검은마법사 격파 없이 창세의 알을 공격함` | Pending |
| `src\objects\fields\gameobject\lifes\MapleMonster.java` | 1301 | Unknown (Method: toString) | ` / 몹 : ` | Pending |
| `src\objects\fields\gameobject\lifes\MapleMonster.java` | 1304 | Unknown (Method: getId) | ` / 맵 : ` | Pending |
| `src\objects\fields\gameobject\lifes\MapleMonster.java` | 1805 | Unknown (Method: log) | `sendObjectPlacement 중 Monster sendSpawnData 패킷 ...` | Pending |
| `src\objects\fields\gameobject\lifes\MapleMonster.java` | 1812 | Unknown (Method: log) | `sendObjectPlacement 중 Monster sendSpawnData sen...` | Pending |
| `src\objects\fields\gameobject\lifes\MapleMonster.java` | 1825 | Unknown (Method: log) | `sendObjectPlacement 중 Monster sendSpawnData 컨트롤...` | Pending |
| `src\objects\fields\gameobject\lifes\MapleMonster.java` | 4592 | Unknown (Method: getScriptProgressMessage) | `시공간 붕괴 실패! 잠시 후, 원래 세계로 돌아갑니다.` | Pending |
| `src\objects\fields\gameobject\lifes\mobskills\MobSkillInfo.java` | 287 | Unknown (Method: contains) | `악몽의골렘` | Pending |
| `src\objects\fields\gameobject\lifes\mobskills\MobSkillInfo.java` | 287 | Unknown (Method: contains) | `독버섯` | Pending |
| `src\objects\fields\gameobject\lifes\mobskills\MobSkillInfo.java` | 553 | Unknown (Method: getName) | `가 [` | Pending |
| `src\objects\fields\gameobject\lifes\mobskills\MobSkillInfo.java` | 553 | Unknown (Method: getName) | ` 추격합니다.` | Pending |
| `src\objects\fields\gameobject\lifes\mobskills\MobSkillInfo.java` | 810 | Unknown (Method: getId) | `위협을 느낀 반 레온이 몬스터를 소환하여 체력을 흡수하려 합니다.` | Pending |
| `src\objects\fields\gameobject\lifes\mobskills\MobSkillInfo.java` | 812 | Unknown (Method: serverNotice) | `위협을 느낀 반 레온이 몬스터를 소환하여 체력을 흡수하려 합니다.` | Pending |
| `src\objects\fields\gameobject\lifes\mobskills\MobSkillInfo.java` | 816 | Unknown (Method: getId) | `반 레온이 주변에 체력을 흡수할 몬스터가 없어 새롭게 소환을 시도합니다.` | Pending |
| `src\objects\fields\gameobject\lifes\mobskills\MobSkillInfo.java` | 818 | Unknown (Method: serverNotice) | `반 레온이 주변에 체력을 흡수할 몬스터가 없어 새롭게 소환을 시도합니다.` | Pending |
| `src\objects\fields\gameobject\lifes\mobskills\MobSkillInfo.java` | 847 | Unknown (Method: getName) | `가 ` | Pending |
| `src\objects\fields\gameobject\lifes\mobskills\MobSkillInfo.java` | 847 | Unknown (Method: getName) | `를 흡수하여 HP를 회복합니다.` | Pending |
| `src\objects\fields\gameobject\lifes\mobskills\MobSkillInfo.java` | 957 | Unknown (Method: getScriptProgressMessage) | `시간의 틈새에 '균열'이 발생하였습니다.` | Pending |
| `src\objects\fields\gameobject\lifes\mobskills\MobSkillInfo.java` | 1884 | Unknown (Method: getId) | `자쿰이 팔을 내려칠 준비를 합니다.` | Pending |
| `src\objects\item\CustomItem.java` | 94 | Unknown (Method: size) | `개의 과거 커스텀 아이템을 불러왔습니다. ` | Pending |
| `src\objects\item\MapleInventory.java` | 266 | Unknown (Method: log) | `removeItem 중 아이템 추가지급 발생 : (itemId : ` | Pending |
| `src\objects\item\MapleInventoryManipulator.java` | 116 | Unknown (Method: ban) | `아이템 복사 시도로 인한 영구 정지` | Pending |
| `src\objects\item\MapleInventoryManipulator.java` | 122 | Unknown (Method: log) | `addByItem 아이템 지급 : (itemId : ` | Pending |
| `src\objects\item\MapleInventoryManipulator.java` | 126 | Unknown (Method: getQuantity) | `) 유저 닉네임 : ` | Pending |
| `src\objects\item\MapleInventoryManipulator.java` | 128 | Unknown (Method: getName) | `, 계정 이름 : ` | Pending |
| `src\objects\item\MapleInventoryManipulator.java` | 326 | Unknown (Method: ban) | `아이템 복사 시도로 인한 영구 정지` | Pending |
| `src\objects\item\MapleInventoryManipulator.java` | 332 | Unknown (Method: log) | `addId 아이템 지급 : (itemId : ` | Pending |
| `src\objects\item\MapleInventoryManipulator.java` | 336 | Variable/Other | `) 유저 닉네임 : ` | Pending |
| `src\objects\item\MapleInventoryManipulator.java` | 338 | Unknown (Method: getName) | `, 계정 이름 : ` | Pending |
| `src\objects\item\MapleInventoryManipulator.java` | 486 | Unknown (Method: ban) | `아이템 복사 시도로 인한 영구 정지` | Pending |
| `src\objects\item\MapleInventoryManipulator.java` | 672 | Unknown (Method: ban) | `아이템 복사 시도로 인한 영구 정지` | Pending |
| `src\objects\item\MapleInventoryManipulator.java` | 678 | Unknown (Method: log) | `addFromDrop 아이템 지급 : (itemId : ` | Pending |
| `src\objects\item\MapleInventoryManipulator.java` | 682 | Unknown (Method: getQuantity) | `) 유저 닉네임 : ` | Pending |
| `src\objects\item\MapleInventoryManipulator.java` | 684 | Unknown (Method: getName) | `, 계정 이름 : ` | Pending |
| `src\objects\item\MapleInventoryManipulator.java` | 2127 | Unknown (Method: append) | `플레이어가 필드에 아이템 드롭함` | Pending |
| `src\objects\item\MapleInventoryManipulator.java` | 2129 | Unknown (Method: append) | ` (장비 정보[` | Pending |
| `src\objects\item\MapleInventoryManipulator.java` | 2139 | Unknown (Method: getAllCurrentTime) | `에 ` | Pending |
| `src\objects\item\MapleInventoryManipulator.java` | 2139 | Unknown (Method: getName) | `이(가) 필드 드랍한 아이템.` | Pending |
| `src\objects\item\MapleItemInformationProvider.java` | 2338 | Unknown (Method: contains) | `놀라운` | Pending |
| `src\objects\item\MapleItemInformationProvider.java` | 2339 | Unknown (Method: contains) | `긍정의` | Pending |
| `src\objects\item\MapleItemInformationProvider.java` | 3182 | Unknown (Method: getStat) | ` 만큼 증가하여 ` | Pending |
| `src\objects\item\MapleItemInformationProvider.java` | 3182 | Unknown (Method: getStat) | `%로 적용` | Pending |
| `src\objects\item\MapleItemInformationProvider.java` | 3266 | Unknown (Method: gainItem) | `주문서 성공확률 얻기 실패로 얻은 주문서` | Pending |
| `src\objects\item\MapleItemInformationProvider.java` | 3276 | Unknown (Method: gainItem) | `주문서 성공확률 얻기 실패로 얻은 주문서` | Pending |
| `src\objects\quest\MapleQuest.java` | 552 | Variable/Other | `초보` | Pending |
| `src\objects\quest\MapleQuest.java` | 555 | Unknown (Method: ElNath) | `엘나스 산맥` | Pending |
| `src\objects\quest\MapleQuest.java` | 558 | Unknown (Method: LudusLake) | `루더스 호수` | Pending |
| `src\objects\quest\MapleQuest.java` | 561 | Unknown (Method: Underwater) | `해저` | Pending |
| `src\objects\quest\MapleQuest.java` | 563 | Unknown (Method: MuLung) | `무릉도원` | Pending |
| `src\objects\quest\MapleQuest.java` | 565 | Unknown (Method: NihalDesert) | `니할사막` | Pending |
| `src\objects\quest\MapleQuest.java` | 568 | Unknown (Method: MinarForest) | `미나르숲` | Pending |
| `src\objects\quest\MapleQuest.java` | 571 | Unknown (Method: Sleepywood) | `슬리피우드` | Pending |
| `src\objects\quest\MapleQuestAction.java` | 189 | Unknown (Method: length) | `> 훈장을 획득하셨습니다!` | Pending |
| `src\objects\quest\MapleQuestAction.java` | 458 | Unknown (Method: length) | `> 훈장을 획득하셨습니다!` | Pending |
| `src\objects\quest\MobQuest.java` | 49 | Unknown (Method: QUEST_1) | `주황버섯의 갓 30개 모아오기` | Pending |
| `src\objects\quest\MobQuest.java` | 50 | Unknown (Method: QUEST_2) | `와일드 보어의 송곳니 50개 모아오기` | Pending |
| `src\objects\quest\MobQuest.java` | 51 | Unknown (Method: QUEST_3) | `루 광석 50개 모아오기` | Pending |
| `src\objects\quest\MobQuest.java` | 52 | Unknown (Method: QUEST_4) | `추억의 사제 200마리 처치하기` | Pending |
| `src\objects\quest\MobQuest.java` | 53 | Unknown (Method: QUEST_5) | `노말 혼테일 격파하기` | Pending |
| `src\objects\quest\MobQuest.java` | 54 | Unknown (Method: QUEST_6) | `상급기사 A~D 각 50마리 처치하기` | Pending |
| `src\objects\quest\MobQuest.java` | 55 | Unknown (Method: QUEST_7) | `노말 블러디 퀸 격파하기` | Pending |
| `src\objects\quest\MobQuest.java` | 56 | Unknown (Method: QUEST_8) | `200레벨 달성하기` | Pending |
| `src\objects\quest\MobQuest.java` | 57 | Unknown (Method: QUEST_9) | `기쁨의 에르다스 200마리 처치하기` | Pending |
| `src\objects\quest\MobQuest.java` | 58 | Unknown (Method: QUEST_10) | `아르마의 부하 200마리 처치하기` | Pending |
| `src\objects\quest\MobQuest.java` | 59 | Unknown (Method: QUEST_11) | `레벨 210 달성하기` | Pending |
| `src\objects\quest\MobQuest.java` | 60 | Unknown (Method: QUEST_12) | `레벨 220 달성하기` | Pending |
| `src\objects\quest\MobQuest.java` | 61 | Unknown (Method: QUEST_13) | `라이터틀, 버샤크 200마리 처치하기` | Pending |
| `src\objects\quest\MobQuest.java` | 62 | Unknown (Method: QUEST_14) | `레벨 230 달성하기` | Pending |
| `src\objects\quest\MobQuest.java` | 63 | Unknown (Method: QUEST_15) | `노말 스우 격파하기` | Pending |
| `src\objects\quest\MobQuest.java` | 64 | Unknown (Method: QUEST_16) | `아투스 200마리 처치하기` | Pending |
| `src\objects\quest\MobQuest.java` | 65 | Unknown (Method: QUEST_17) | `레벨 240 달성하기` | Pending |
| `src\objects\quest\MobQuest.java` | 66 | Unknown (Method: QUEST_18) | `어둠의 집행자, 빛의 집행자 300마리 처치하기` | Pending |
| `src\objects\quest\MobQuest.java` | 67 | Unknown (Method: QUEST_19) | `레벨 250 달성하기` | Pending |
| `src\objects\quest\MobQuest.java` | 68 | Unknown (Method: QUEST_20) | `혼돈의 피조물 500마리 처치하기` | Pending |
| `src\objects\quest\MobQuest.java` | 69 | Unknown (Method: QUEST_21) | `절망의 날개 500마리 처치하기` | Pending |
| `src\objects\quest\MobQuest.java` | 70 | Unknown (Method: QUEST_22) | `레벨 260 달성하기` | Pending |
| `src\objects\quest\MobQuest.java` | 71 | Unknown (Method: QUEST_23) | `안세스티온 1000마리 처치하기` | Pending |
| `src\objects\quest\MobQuest.java` | 72 | Unknown (Method: QUEST_24) | `레벨 265 달성하기` | Pending |
| `src\objects\quest\MobQuest.java` | 73 | Unknown (Method: QUEST_25) | `포어베리온 1000마리 처치하기` | Pending |
| `src\objects\quest\MobQuest.java` | 74 | Unknown (Method: QUEST_26) | `레벨 270 달성하기` | Pending |
| `src\objects\quest\MobQuest.java` | 75 | Unknown (Method: QUEST_27) | `엠브리온 1000마리 처치하기` | Pending |
| `src\objects\quest\MobQuest.java` | 76 | Unknown (Method: QUEST_28) | `레벨 275 달성하기` | Pending |
| `src\objects\quest\MobQuest.java` | 77 | Unknown (Method: QUEST_29) | `게릴라 스펙터 200마리 처치하기` | Pending |
| `src\objects\quest\MobQuest.java` | 78 | Unknown (Method: QUEST_30) | `울프룻 200마리 처치하기` | Pending |
| `src\objects\quest\MobQuest.java` | 79 | Unknown (Method: QUEST_31) | `검은 마법사 처치하기` | Pending |
| `src\objects\quest\MobQuest.java` | 80 | Unknown (Method: QUEST_32) | `사자왕 반 레온의 흔적` | Pending |
| `src\objects\quest\MobQuest.java` | 81 | Unknown (Method: QUEST_33) | `시간의 대신관 아카이럼의 흔적` | Pending |
| `src\objects\quest\MobQuest.java` | 82 | Unknown (Method: QUEST_1000) | `유니온 - 골드 와이번 50마리 처치하기` | Pending |
| `src\objects\quest\MobQuest.java` | 83 | Unknown (Method: QUEST_1001) | `유니온 - 미니 와이번 100마리 처치하기` | Pending |
| `src\objects\quest\WeeklyQuest.java` | 59 | Unknown (Method: QUEST_1) | `헤이븐 주간 퀘스트` | Pending |
| `src\objects\quest\WeeklyQuest.java` | 60 | Unknown (Method: QUEST_2) | `버려진야영지 주간 퀘스트` | Pending |
| `src\objects\shop\MapleShop.java` | 164 | Unknown (Method: append) | `상점 아이템 재구매 시도 (캐릭터 : ` | Pending |
| `src\objects\shop\MapleShop.java` | 166 | Unknown (Method: append) | `, 계정 : ` | Pending |
| `src\objects\shop\MapleShop.java` | 168 | Unknown (Method: append) | `, 상점ID : ` | Pending |
| `src\objects\shop\MapleShop.java` | 170 | Unknown (Method: append) | `, 아이템 : ` | Pending |
| `src\objects\shop\MapleShop.java` | 174 | Unknown (Method: append) | `개, 가격 : ` | Pending |
| `src\objects\shop\MapleShop.java` | 176 | Unknown (Method: append) | ` 메소)` | Pending |
| `src\objects\shop\MapleShop.java` | 234 | Unknown (Method: append) | `상점 아이템 구매 시도 (캐릭터 : ` | Pending |
| `src\objects\shop\MapleShop.java` | 236 | Unknown (Method: append) | `, 계정 : ` | Pending |
| `src\objects\shop\MapleShop.java` | 238 | Unknown (Method: append) | `, 상점ID : ` | Pending |
| `src\objects\shop\MapleShop.java` | 240 | Unknown (Method: append) | `, 아이템 : ` | Pending |
| `src\objects\shop\MapleShop.java` | 244 | Unknown (Method: append) | `개, 가격 : ` | Pending |
| `src\objects\shop\MapleShop.java` | 246 | Unknown (Method: append) | ` 메소, ` | Pending |
| `src\objects\shop\MapleShop.java` | 248 | Unknown (Method: append) | ` 포인트)` | Pending |
| `src\objects\shop\MapleShop.java` | 536 | Unknown (Method: append) | `상점에 아이템 판매 시도 (캐릭터 : ` | Pending |
| `src\objects\shop\MapleShop.java` | 538 | Unknown (Method: append) | `, 계정 : ` | Pending |
| `src\objects\shop\MapleShop.java` | 540 | Unknown (Method: append) | `, 상점ID : ` | Pending |
| `src\objects\shop\MapleShop.java` | 542 | Unknown (Method: append) | `, 아이템 : ` | Pending |
| `src\objects\shop\MapleShop.java` | 546 | Unknown (Method: append) | `개, ` | Pending |
| `src\objects\shop\MapleShop.java` | 550 | Unknown (Method: append) | `(정보 : ` | Pending |
| `src\objects\shop\MapleShop.java` | 554 | Unknown (Method: append) | `), 가격 : ` | Pending |
| `src\objects\shop\MapleShop.java` | 556 | Unknown (Method: append) | ` 메소)` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 527 | Unknown (Method: indexOf) | `마력` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 531 | Unknown (Method: indexOf) | `공격력` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 562 | Unknown (Method: indexOf) | `공격력 #padX%` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 563 | Unknown (Method: indexOf) | `공격력 #padX` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 564 | Unknown (Method: indexOf) | `공격력 #x%` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 565 | Unknown (Method: indexOf) | `공격력 #x` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 566 | Unknown (Method: equals) | `영웅의 메아리` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 570 | Unknown (Method: equals) | `익스클루시브 스펠` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 574 | Unknown (Method: equals) | `인탠시브 타임` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 578 | Unknown (Method: equals) | `쓸만한 어드밴스드 블레스` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 590 | Unknown (Method: indexOf) | `마력` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 629 | Unknown (Method: indexOf) | `마력 #madX%` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 630 | Unknown (Method: indexOf) | `마력 #madX` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 631 | Unknown (Method: indexOf) | `마력 #padX%` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 632 | Unknown (Method: indexOf) | `마력 #padX` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 633 | Unknown (Method: indexOf) | `마력#c #padX%` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 634 | Unknown (Method: indexOf) | `마력#c #padX` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 635 | Unknown (Method: indexOf) | `마력 #mad%` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 636 | Unknown (Method: indexOf) | `마력 #mad` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 637 | Unknown (Method: indexOf) | `마력#indieMad%` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 638 | Unknown (Method: indexOf) | `마력#indieMad` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 639 | Unknown (Method: indexOf) | `마력 #indiePad%` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 640 | Unknown (Method: indexOf) | `마력 #indiePad` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 641 | Unknown (Method: indexOf) | `마력 #indiePAD%` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 642 | Unknown (Method: indexOf) | `마력 #indiePAD` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 643 | Unknown (Method: indexOf) | `마력 #indieMad%` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 644 | Unknown (Method: indexOf) | `마력 #indieMad` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 645 | Unknown (Method: indexOf) | `마력 #c#indieMad#%` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 646 | Unknown (Method: indexOf) | `마력 #c#indieMad#` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 647 | Unknown (Method: indexOf) | `마력 #damage%` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 648 | Unknown (Method: indexOf) | `마력 #x%` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 649 | Unknown (Method: indexOf) | `마력 #x` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 650 | Unknown (Method: indexOf) | `마력 #y%` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 651 | Unknown (Method: indexOf) | `마력 #y` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 652 | Unknown (Method: indexOf) | `마력+#y%` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 653 | Unknown (Method: indexOf) | `마력+#y` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 654 | Unknown (Method: indexOf) | `마력 #v%` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 655 | Unknown (Method: indexOf) | `마력 #v` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 656 | Unknown (Method: indexOf) | `마력 #w%` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 657 | Unknown (Method: indexOf) | `마력 #w` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 658 | Unknown (Method: indexOf) | `마력 #epad%` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 659 | Unknown (Method: indexOf) | `마력 #epad` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 660 | Unknown (Method: indexOf) | `마력 #emad%` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 661 | Unknown (Method: indexOf) | `마력 #emad` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 662 | Unknown (Method: indexOf) | `마력 #indiePadR%` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 663 | Unknown (Method: indexOf) | `마력 #indieMadR%` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 664 | Unknown (Method: indexOf) | `마력이 #indieMadR%` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 665 | Unknown (Method: equals) | `영웅의 메아리` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 669 | Unknown (Method: equals) | `익스클루시브 스펠` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 673 | Unknown (Method: equals) | `인탠시브 타임` | Pending |
| `src\objects\users\CalcDamageUtil.java` | 677 | Unknown (Method: equals) | `쓸만한 어드밴스드 블레스` | Pending |
| `src\objects\users\MapleCharacter.java` | 827 | Variable/Other | `수집` | Pending |
| `src\objects\users\MapleCharacter.java` | 827 | Variable/Other | `처치` | Pending |
| `src\objects\users\MapleCharacter.java` | 828 | Variable/Other | `개` | Pending |
| `src\objects\users\MapleCharacter.java` | 828 | Variable/Other | `마리` | Pending |
| `src\objects\users\MapleCharacter.java` | 829 | Variable/Other | `스카웃` | Pending |
| `src\objects\users\MapleCharacter.java` | 829 | Variable/Other | `서전트` | Pending |
| `src\objects\users\MapleCharacter.java` | 829 | Variable/Other | `가디언` | Pending |
| `src\objects\users\MapleCharacter.java` | 829 | Variable/Other | `마스터` | Pending |
| `src\objects\users\MapleCharacter.java` | 829 | Variable/Other | `커맨더` | Pending |
| `src\objects\users\MapleCharacter.java` | 829 | Variable/Other | `슈프림` | Pending |
| `src\objects\users\MapleCharacter.java` | 1419 | Unknown (Method: outputFileErrorReason) | `서버 메모리 로드 실패` | Pending |
| `src\objects\users\MapleCharacter.java` | 2313 | Unknown (Method: parseInt) | ` 데미지 스킨 정보이다.\\r\\n\\r\\n\\r\\n\\r\\n\\r\\n` | Pending |
| `src\objects\users\MapleCharacter.java` | 3110 | Unknown (Method: getName) | ` 캐릭터 DB저장 실패 flag1 : ` | Pending |
| `src\objects\users\MapleCharacter.java` | 3111 | Unknown (Method: getName) | ` 시간차이 : ` | Pending |
| `src\objects\users\MapleCharacter.java` | 5712 | Unknown (Method: ban) | `맵 이동핵` | Pending |
| `src\objects\users\MapleCharacter.java` | 6499 | Unknown (Method: addExp) | `약초채집` | Pending |
| `src\objects\users\MapleCharacter.java` | 6504 | Unknown (Method: addExp) | `채광` | Pending |
| `src\objects\users\MapleCharacter.java` | 6509 | Unknown (Method: addExp) | `장비제작` | Pending |
| `src\objects\users\MapleCharacter.java` | 6514 | Unknown (Method: addExp) | `장신구제작` | Pending |
| `src\objects\users\MapleCharacter.java` | 6519 | Unknown (Method: addExp) | `연금술` | Pending |
| `src\objects\users\MapleCharacter.java` | 7388 | Unknown (Method: ban) | `아이템 복사 시도로 인한 영구 정지` | Pending |
| `src\objects\users\MapleCharacter.java` | 7413 | Unknown (Method: getAllCurrentTime) | `에 ` | Pending |
| `src\objects\users\MapleCharacter.java` | 7426 | Unknown (Method: getName) | `???????????? ?멨????????占? gainItem ???????у????...` | Pending |
| `src\objects\users\MapleCharacter.java` | 7466 | Unknown (Method: getId) | `] 스킬이 유효기간이 만료되어 사라졌습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 7490 | Unknown (Method: currentTimeMillis) | `[두손무기 방패 착용]` | Pending |
| `src\objects\users\MapleCharacter.java` | 7491 | Unknown (Method: currentTimeMillis) | `두손무기에 방패 착용 오류로 인하여 장착 해제된 아이템입니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 7512 | Unknown (Method: currentTimeMillis) | `[한벌옷 하의 캐시장비 장착 오류]` | Pending |
| `src\objects\users\MapleCharacter.java` | 7513 | Unknown (Method: currentTimeMillis) | `한벌옷에 하의 캐시장비 중복 장착 오류로 인하여 장착 해제된 아이템입니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 7535 | Unknown (Method: currentTimeMillis) | `[한벌옷 하의 캐시장비 장착 오류]` | Pending |
| `src\objects\users\MapleCharacter.java` | 7536 | Unknown (Method: currentTimeMillis) | `한벌옷에 하의 캐시장비 중복 장착 오류로 인하여 장착 해제된 아이템입니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 7558 | Unknown (Method: currentTimeMillis) | `[한벌옷 하의 캐시장비 장착 오류]` | Pending |
| `src\objects\users\MapleCharacter.java` | 7559 | Unknown (Method: currentTimeMillis) | `한벌옷에 하의 캐시장비 중복 장착 오류로 인하여 장착 해제된 아이템입니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 7851 | Unknown (Method: if) | `오늘의 미션을 전부 클리어 하셨습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 7853 | Unknown (Method: if) | `일일 미션 ` | Pending |
| `src\objects\users\MapleCharacter.java` | 7853 | Variable/Other | `개 완료! ` | Pending |
| `src\objects\users\MapleCharacter.java` | 7853 | Variable/Other | `번째 상자를 클릭하세요!` | Pending |
| `src\objects\users\MapleCharacter.java` | 7952 | Unknown (Method: getScriptProgressMessage) | `레벨 범위 몬스터 ` | Pending |
| `src\objects\users\MapleCharacter.java` | 7993 | Variable/Other | `번 더 클리어 하실 수 있습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 8001 | Unknown (Method: Quest) | `/20)] 레벨 범위 몬스터 ` | Pending |
| `src\objects\users\MapleCharacter.java` | 8416 | Unknown (Method: getAllCurrentTime) | `에 300레벨 달성 보상으로 지급된 아이템.` | Pending |
| `src\objects\users\MapleCharacter.java` | 8419 | Unknown (Method: currentTimeMillis) | `[GM 선물]` | Pending |
| `src\objects\users\MapleCharacter.java` | 8419 | Unknown (Method: currentTimeMillis) | `에 레벨보상으로 지급된 아이템입니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 8442 | Unknown (Method: getName) | ` 시간 : ` | Pending |
| `src\objects\users\MapleCharacter.java` | 8454 | Unknown (Method: addPopupSay) | `계정 내 통합레벨이 500을 넘어 계정 내 모든 캐릭터가 #b초보자A#k길드에서 자동...` | Pending |
| `src\objects\users\MapleCharacter.java` | 8460 | Unknown (Method: addPopupSay) | `계정 내 통합레벨이 500을 넘어 계정 내 모든 캐릭터가 #b초보자B#k길드에서 자동...` | Pending |
| `src\objects\users\MapleCharacter.java` | 8492 | Unknown (Method: addPopupSay) | `캐릭터의 레벨이 220을 넘어 #b초보자A#k길드에서 자동으로 탈퇴되었습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 8497 | Unknown (Method: addPopupSay) | `캐릭터의 레벨이 220을 넘어 #b초보자B#k길드에서 자동으로 탈퇴되었습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 8512 | Unknown (Method: gainItem) | `적정레벨 장비상자 아이템` | Pending |
| `src\objects\users\MapleCharacter.java` | 8562 | Unknown (Method: SimpleDateFormat) | `yyyy년 MM월 dd일 HH시 mm분` | Pending |
| `src\objects\users\MapleCharacter.java` | 8567 | Unknown (Method: currentTimeMillis) | `[200레벨 보상]` | Pending |
| `src\objects\users\MapleCharacter.java` | 8567 | Unknown (Method: currentTimeMillis) | `에 지급된 보상입니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 8590 | Unknown (Method: StringBuilder) | `[축하] ` | Pending |
| `src\objects\users\MapleCharacter.java` | 8599 | Unknown (Method: append) | ` 님이 레벨 ` | Pending |
| `src\objects\users\MapleCharacter.java` | 8599 | Unknown (Method: append) | `을(를) 달성했습니다! 모두 축하해 주세요.` | Pending |
| `src\objects\users\MapleCharacter.java` | 8608 | Unknown (Method: SimpleDateFormat) | `yyyy년 MM월 dd일 HH시 mm분` | Pending |
| `src\objects\users\MapleCharacter.java` | 8613 | Unknown (Method: currentTimeMillis) | `[275레벨 보상]` | Pending |
| `src\objects\users\MapleCharacter.java` | 8613 | Unknown (Method: currentTimeMillis) | `에 지급된 보상입니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 8628 | Unknown (Method: StringBuilder) | `[축하] ` | Pending |
| `src\objects\users\MapleCharacter.java` | 8637 | Unknown (Method: append) | ` 님이 레벨 ` | Pending |
| `src\objects\users\MapleCharacter.java` | 8637 | Unknown (Method: append) | `을(를) 달성했습니다! 모두 축하해 주세요.` | Pending |
| `src\objects\users\MapleCharacter.java` | 8745 | Unknown (Method: getScriptProgressMessage) | `[정령결속 극대화]를 통해 그리운 랑의 모습을 구현할 수 있게 되었습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9229 | Unknown (Method: getScriptProgressMessage) | `[암흑을 기억하는자] 세미듀어러로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9243 | Unknown (Method: getScriptProgressMessage) | `[칼리] 칼리로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9251 | Unknown (Method: getScriptProgressMessage) | `[양손검술의 기사] 파이터로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9259 | Unknown (Method: getScriptProgressMessage) | `[한손검술의 기사] 페이지로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9267 | Unknown (Method: getScriptProgressMessage) | `[창술의 기사] 스피어맨로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9275 | Unknown (Method: getScriptProgressMessage) | `[불*독] 위자드로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9283 | Unknown (Method: getScriptProgressMessage) | `[얼음*번개] 위자드로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9291 | Unknown (Method: getScriptProgressMessage) | `[힐*버프] 클레릭으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9299 | Unknown (Method: getScriptProgressMessage) | `[사격수] 헌터로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9307 | Unknown (Method: getScriptProgressMessage) | `[명사수] 사수로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9315 | Unknown (Method: getScriptProgressMessage) | `[저주와 고대의 힘] 에인션트 아처로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9323 | Unknown (Method: getScriptProgressMessage) | `[표창 암살 입문기] 어쌔신로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9331 | Unknown (Method: getScriptProgressMessage) | `[단도 암살 입문기] 시프로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9339 | Unknown (Method: getScriptProgressMessage) | `[너클 입문기] 인파이터로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9347 | Unknown (Method: getScriptProgressMessage) | `[건 입문기] 건슬링거로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9355 | Unknown (Method: getScriptProgressMessage) | `[암흑 속의 과거] 듀어러로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9363 | Unknown (Method: getScriptProgressMessage) | `[캐논 입문기] 캐논슈터로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9371 | Unknown (Method: getScriptProgressMessage) | `[시그너스 입문기] 빛의 기사로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9379 | Unknown (Method: getScriptProgressMessage) | `[시그너스 입문기] 불의 기사로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9387 | Unknown (Method: getScriptProgressMessage) | `[시그너스 입문기] 바람의 기사로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9395 | Unknown (Method: getScriptProgressMessage) | `[시그너스 입문기] 어둠의 기사로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9403 | Unknown (Method: getScriptProgressMessage) | `[시그너스 입문기] 번개의 기사로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9411 | Unknown (Method: getScriptProgressMessage) | `[영웅의 본능] 아란으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9419 | Unknown (Method: getScriptProgressMessage) | `[두번째 걸음] 에반으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9427 | Unknown (Method: getScriptProgressMessage) | `[영웅의 본능] 메르세데스로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9435 | Unknown (Method: getScriptProgressMessage) | `[영웅의 본능] 팬텀으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9443 | Unknown (Method: getScriptProgressMessage) | `[영웅의 본능] 은월으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9451 | Unknown (Method: getScriptProgressMessage) | `[영웅의 본능] 루미너스로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9459 | Unknown (Method: getScriptProgressMessage) | `[레지스탕스 입문기] 데몬슬레이어로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9467 | Unknown (Method: getScriptProgressMessage) | `[레지스탕스 입문기] 데몬어벤져로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9475 | Unknown (Method: getScriptProgressMessage) | `[레지스탕스 입문기] 배틀메이지로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9483 | Unknown (Method: getScriptProgressMessage) | `[레지스탕스 입문기] 와일드헌터로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9491 | Unknown (Method: getScriptProgressMessage) | `[레지스탕스 입문기] 메카닉으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9499 | Unknown (Method: getScriptProgressMessage) | `[레지스탕스 입문기] 제논으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9507 | Unknown (Method: getScriptProgressMessage) | `[레지스탕스 입문기] 블래스터으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9516 | Unknown (Method: getScriptProgressMessage) | `[시그너스 단장] 빛의 기사로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9525 | Unknown (Method: getScriptProgressMessage) | `[노바 수련생] 카이저로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9533 | Unknown (Method: getScriptProgressMessage) | `[노바 수련생] 카데나로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9541 | Unknown (Method: getScriptProgressMessage) | `[레프 수련생] 일리움으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9549 | Unknown (Method: getScriptProgressMessage) | `[레프 수련생] 아크로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9558 | Unknown (Method: getScriptProgressMessage) | `[노바 수련생] 엔젤릭버스터로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9566 | Unknown (Method: getScriptProgressMessage) | `[초능력의 깨달음] 키네시스로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9574 | Unknown (Method: getScriptProgressMessage) | `[아니마 수련생] 호영으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9582 | Unknown (Method: getScriptProgressMessage) | `[레프 수련생] 아델로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9589 | Unknown (Method: getScriptProgressMessage) | `[노바 수련생] 카인으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9597 | Unknown (Method: getScriptProgressMessage) | `[아니마 수련생] 라라로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9617 | Unknown (Method: getScriptProgressMessage) | `[암흑의 정체성] 듀얼마스터로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9631 | Unknown (Method: getScriptProgressMessage) | `[칼리] 칼리로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9639 | Unknown (Method: getScriptProgressMessage) | `[영혼 검술의 기사] 크루세이더로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9647 | Unknown (Method: getScriptProgressMessage) | `[속성 검술의 기사] 나이트로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9655 | Unknown (Method: getScriptProgressMessage) | `[드래곤 창술의 기사] 드래곤 나이트로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9663 | Unknown (Method: getScriptProgressMessage) | `[불*독] 메이지로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9671 | Unknown (Method: getScriptProgressMessage) | `[얼음*번개] 메이지로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9679 | Unknown (Method: getScriptProgressMessage) | `[힐*버프] 프리스트로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9687 | Unknown (Method: getScriptProgressMessage) | `[연쇄 사격수] 레인저로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9695 | Unknown (Method: getScriptProgressMessage) | `[백발백중 명사수] 저격수로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9703 | Unknown (Method: getScriptProgressMessage) | `[체이서의 길] 체이서로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9711 | Unknown (Method: getScriptProgressMessage) | `[암살 전문가] 허밋로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9719 | Unknown (Method: getScriptProgressMessage) | `[암흑자] 시프 마스터로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9727 | Unknown (Method: getScriptProgressMessage) | `[드래곤 너클 파이터] 버커니어로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9735 | Unknown (Method: getScriptProgressMessage) | `[건 마스터리] 발키리로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9743 | Unknown (Method: getScriptProgressMessage) | `[암흑을 알아버린자] 슬래셔로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9751 | Unknown (Method: getScriptProgressMessage) | `[캐논 마스터리] 캐논슈터로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9759 | Unknown (Method: getScriptProgressMessage) | `[영웅의 깨달음] 아란으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9767 | Unknown (Method: getScriptProgressMessage) | `[진화의 드래곤] 에반으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9775 | Unknown (Method: getScriptProgressMessage) | `[영웅의 깨달음] 메르세데스로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9783 | Unknown (Method: getScriptProgressMessage) | `[영웅의 깨달음] 팬텀으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9791 | Unknown (Method: getScriptProgressMessage) | `[영웅의 깨달음] 은월으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9799 | Unknown (Method: getScriptProgressMessage) | `[영웅의 깨달음] 루미너스로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9807 | Unknown (Method: getScriptProgressMessage) | `[레지스탕스 요원] 데몬슬레이어로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9815 | Unknown (Method: getScriptProgressMessage) | `[레지스탕스 요원] 데몬어벤져로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9823 | Unknown (Method: getScriptProgressMessage) | `[레지스탕스 요원] 배틀메이지로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9831 | Unknown (Method: getScriptProgressMessage) | `[레지스탕스 요원] 와일드헌터로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9839 | Unknown (Method: getScriptProgressMessage) | `[레지스탕스 요원] 메카닉으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9847 | Unknown (Method: getScriptProgressMessage) | `[레지스탕스 요원] 제논으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9855 | Unknown (Method: getScriptProgressMessage) | `[레지스탕스 요원] 블래스터로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9864 | Unknown (Method: getScriptProgressMessage) | `[시그너스 단장] 빛의 기사로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9873 | Unknown (Method: getScriptProgressMessage) | `[노바의 수호자] 카이저로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9881 | Unknown (Method: getScriptProgressMessage) | `[노바의 수호자] 카데나로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9890 | Unknown (Method: getScriptProgressMessage) | `[노바의 수호자] 엔젤릭버스터로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9898 | Unknown (Method: getScriptProgressMessage) | `[시그너스 정식 기사] 소울 마스터로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9906 | Unknown (Method: getScriptProgressMessage) | `[시그너스 정식 기사] 플레임 위자드로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9914 | Unknown (Method: getScriptProgressMessage) | `[시그너스 정식 기사] 윈드 브레이커로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9922 | Unknown (Method: getScriptProgressMessage) | `[시그너스 정식 기사] 나이트 워커로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9930 | Unknown (Method: getScriptProgressMessage) | `[시그너스 정식 기사] 스트라이커로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9938 | Unknown (Method: getScriptProgressMessage) | `[초능력의 깨달음] 키네시스로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9946 | Unknown (Method: getScriptProgressMessage) | `[레프의 수호자] 일리움으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9954 | Unknown (Method: getScriptProgressMessage) | `[레프의 수호자] 아크로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9962 | Unknown (Method: getScriptProgressMessage) | `[아니마 수호자] 호영으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9970 | Unknown (Method: getScriptProgressMessage) | `[레프 수호자] 아델로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9978 | Unknown (Method: getScriptProgressMessage) | `[노바 수호자] 카인으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9986 | Unknown (Method: getScriptProgressMessage) | `[아니마 수호자] 라라로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 9999 | Unknown (Method: getScriptProgressMessage) | `[칼리] 칼리로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10007 | Unknown (Method: getScriptProgressMessage) | `[연쇄 검술의 마스터] 히어로로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10015 | Unknown (Method: getScriptProgressMessage) | `[환상 검술의 마스터] 팔라딘로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10023 | Unknown (Method: getScriptProgressMessage) | `[다크 드래곤 창술의 마스터] 다크 나이트로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10031 | Unknown (Method: getScriptProgressMessage) | `[불*독 마스터] 아크메이지로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10039 | Unknown (Method: getScriptProgressMessage) | `[얼음*번개 마스터] 아크메이지로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10047 | Unknown (Method: getScriptProgressMessage) | `[힐*버프 마스터] 비숍으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10055 | Unknown (Method: getScriptProgressMessage) | `[화살 연사의 마스터] 보우 마스터로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10063 | Unknown (Method: getScriptProgressMessage) | `[화살 파워의 마스터] 신궁로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10071 | Unknown (Method: getScriptProgressMessage) | `[에인션트 보우의 달인] 패스파인더로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10079 | Unknown (Method: getScriptProgressMessage) | `[연쇄 암살의 마스터] 나이트 로드로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10087 | Unknown (Method: getScriptProgressMessage) | `[암흑의 암살 마스터] 섀도우로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10095 | Unknown (Method: getScriptProgressMessage) | `[정령의 너클 파이터] 바이퍼로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10103 | Unknown (Method: getScriptProgressMessage) | `[배틀 건 마스터리] 캡틴으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10111 | Unknown (Method: getScriptProgressMessage) | `[암흑을 조정하는자] 듀얼블레이드로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10119 | Unknown (Method: getScriptProgressMessage) | `[파괴의 캐논 마스터리] 캐논슈터로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10127 | Unknown (Method: getScriptProgressMessage) | `[영웅의 부활] 아란으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10135 | Unknown (Method: getScriptProgressMessage) | `[전설의 드래곤] 에반으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10143 | Unknown (Method: getScriptProgressMessage) | `[영웅의 부활] 메르세데스로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10151 | Unknown (Method: getScriptProgressMessage) | `[영웅의 부활] 팬텀으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10161 | Unknown (Method: getScriptProgressMessage) | `[영웅의 부활] 은월으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10169 | Unknown (Method: getScriptProgressMessage) | `[영웅의 부활] 루미너스로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10177 | Unknown (Method: getScriptProgressMessage) | `[레지스탕스의 영웅] 데몬슬레이어로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10185 | Unknown (Method: getScriptProgressMessage) | `[레지스탕스 영웅] 데몬어벤져로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10193 | Unknown (Method: getScriptProgressMessage) | `[레지스탕스의 영웅] 배틀메이지로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10201 | Unknown (Method: getScriptProgressMessage) | `[레지스탕스의 영웅] 와일드헌터로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10209 | Unknown (Method: getScriptProgressMessage) | `[레지스탕스의 영웅] 메카닉으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10217 | Unknown (Method: getScriptProgressMessage) | `[레지스탕스의 영웅] 제논으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10225 | Unknown (Method: getScriptProgressMessage) | `[레지스탕스의 영웅] 블래스터로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10234 | Unknown (Method: getScriptProgressMessage) | `[시그너스 단장] 빛의 기사로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10243 | Unknown (Method: getScriptProgressMessage) | `[용의 기사] 카이저로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10251 | Unknown (Method: getScriptProgressMessage) | `[체인 마스터] 카데나로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10260 | Unknown (Method: getScriptProgressMessage) | `[전장의 아이돌] 엔젤릭버스터로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10268 | Unknown (Method: getScriptProgressMessage) | `[시그너스 영웅] 빛의 대정령으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10277 | Unknown (Method: getScriptProgressMessage) | `[시그너스 영웅] 불의 대정령으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10286 | Unknown (Method: getScriptProgressMessage) | `[시그너스 영웅] 바람의 대정령으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10295 | Unknown (Method: getScriptProgressMessage) | `[시그너스 영웅] 어둠의 대정령으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10304 | Unknown (Method: getScriptProgressMessage) | `[시그너스 영웅] 번개의 대정령으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10313 | Unknown (Method: getScriptProgressMessage) | `[초능력의 영웅] 키네시스로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10321 | Unknown (Method: getScriptProgressMessage) | `[크리스탈 마스터] 일리움으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10329 | Unknown (Method: getScriptProgressMessage) | `[레프의 영웅] 아크로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10337 | Unknown (Method: getScriptProgressMessage) | `[아니마 영웅] 호영으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10345 | Unknown (Method: getScriptProgressMessage) | `[레프의 영웅] 아델로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10353 | Unknown (Method: getScriptProgressMessage) | `[노바의 영웅] 카인으로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10361 | Unknown (Method: getScriptProgressMessage) | `[아니마 영웅] 라라로 전직하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 10593 | Unknown (Method: outputFileErrorReason) | `sendObjectPlacement 중 Character sendSpawnData 오...` | Pending |
| `src\objects\users\MapleCharacter.java` | 10600 | Unknown (Method: outputFileErrorReason) | `sendObjectPlacement 중 Character sendSpawnData 오...` | Pending |
| `src\objects\users\MapleCharacter.java` | 10612 | Unknown (Method: outputFileErrorReason) | `sendObjectPlacement 중 Character sendSpawnData 오...` | Pending |
| `src\objects\users\MapleCharacter.java` | 10623 | Unknown (Method: outputFileErrorReason) | `sendObjectPlacement 중 Character sendSpawnData 오...` | Pending |
| `src\objects\users\MapleCharacter.java` | 10636 | Unknown (Method: outputFileErrorReason) | `sendObjectPlacement 중 Character sendSpawnData 오...` | Pending |
| `src\objects\users\MapleCharacter.java` | 10658 | Unknown (Method: log) | `sendObjectPlacement 중 Character sendSpawnData 오...` | Pending |
| `src\objects\users\MapleCharacter.java` | 10685 | Unknown (Method: outputFileErrorReason) | `sendObjectPlacement 중 Character sendSpawnData 오...` | Pending |
| `src\objects\users\MapleCharacter.java` | 10695 | Unknown (Method: outputFileErrorReason) | `sendObjectPlacement 중 Character sendSpawnData 오...` | Pending |
| `src\objects\users\MapleCharacter.java` | 10714 | Unknown (Method: log) | `sendObjectPlacement 중 Character sendSpawnData 오...` | Pending |
| `src\objects\users\MapleCharacter.java` | 10739 | Unknown (Method: log) | `sendObjectPlacement 중 Character sendSpawnData 오...` | Pending |
| `src\objects\users\MapleCharacter.java` | 10973 | Unknown (Method: getName) | `님의 파티` | Pending |
| `src\objects\users\MapleCharacter.java` | 11744 | Variable/Other | ` 캐시를 ` | Pending |
| `src\objects\users\MapleCharacter.java` | 11744 | Variable/Other | ` 메이플포인트를 ` | Pending |
| `src\objects\users\MapleCharacter.java` | 11744 | Variable/Other | `얻었습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 11744 | Variable/Other | `잃었습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 13970 | Unknown (Method: getRoadRingExpBoost) | `시간이 지났습니다. ` | Pending |
| `src\objects\users\MapleCharacter.java` | 13971 | Unknown (Method: getRoadRingExpBoost) | `%의 보너스 경험치를 얻습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 13974 | Unknown (Method: getRoadRingExpBoost) | `%를 추가로 획득하게 됩니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 16593 | Unknown (Method: getName) | `이(가) 맵 코드 : ` | Pending |
| `src\objects\users\MapleCharacter.java` | 16593 | Unknown (Method: 이) | `으로 이동 중 field가 null` | Pending |
| `src\objects\users\MapleCharacter.java` | 16609 | Unknown (Method: getName) | `이(가) 맵 코드 : ` | Pending |
| `src\objects\users\MapleCharacter.java` | 16609 | Unknown (Method: 이) | `으로 이동 중 field가 null` | Pending |
| `src\objects\users\MapleCharacter.java` | 17019 | Unknown (Method: format) | `금일 %s 입장횟수 %d / %d` | Pending |
| `src\objects\users\MapleCharacter.java` | 17284 | Variable/Other | `#fn나눔고딕 Extrabold#아래에서 당신의 내면의 결과를 확인해보세요.\r\n\...` | Pending |
| `src\objects\users\MapleCharacter.java` | 17285 | Unknown (Method: get) | `##k](이)가 소환 되었습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 17535 | Unknown (Method: addPopupSay) | `#k 님에게 #b(` | Pending |
| `src\objects\users\MapleCharacter.java` | 17536 | Unknown (Method: getName) | `)#k을(를) 선물받으셨습니다. 인벤토리를 확인해보세요.` | Pending |
| `src\objects\users\MapleCharacter.java` | 17653 | Unknown (Method: getStaticScreenMessage) | `휴식 포인트를 1포인트 획득하여 총 ` | Pending |
| `src\objects\users\MapleCharacter.java` | 17654 | Unknown (Method: getDancePoint) | `포인트를 보유중입니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 17718 | Unknown (Method: getName) | `] 을(를) ` | Pending |
| `src\objects\users\MapleCharacter.java` | 17718 | Unknown (Method: 을) | `개 얻었습니다!` | Pending |
| `src\objects\users\MapleCharacter.java` | 18680 | Unknown (Method: getDancePoint) | `포인트가 되었습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 19759 | Unknown (Method: append) | ` 하기 #e(` | Pending |
| `src\objects\users\MapleCharacter.java` | 19795 | Unknown (Method: getQuestID) | `]#k 퀘스트를 클리어 할 수 있습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 19830 | Unknown (Method: getQuestID) | `]#k 퀘스트를 클리어 할 수 있습니다.\r\n#e컨텐츠 시스템 > 익스트림 퀘스트#...` | Pending |
| `src\objects\users\MapleCharacter.java` | 20170 | Unknown (Method: contains) | `성` | Pending |
| `src\objects\users\MapleCharacter.java` | 20171 | Unknown (Method: split) | `성` | Pending |
| `src\objects\users\MapleCharacter.java` | 20183 | Unknown (Method: setOwner) | `성` | Pending |
| `src\objects\users\MapleCharacter.java` | 20196 | Unknown (Method: getItemId) | `] 아이템이 2019년 9월 25일 점검 시점 이전 아이템 옵션 초기화 대상 아이템으...` | Pending |
| `src\objects\users\MapleCharacter.java` | 20297 | Unknown (Method: addPopupSay) | `#e#b[초보자B]#k#n 길드에 자동으로 가입되었습니다.\r\n계정 내 #r통합레벨...` | Pending |
| `src\objects\users\MapleCharacter.java` | 20973 | Unknown (Method: getUnionLevelName) | `언랭크` | Pending |
| `src\objects\users\MapleCharacter.java` | 20978 | Unknown (Method: if) | `언랭크` | Pending |
| `src\objects\users\MapleCharacter.java` | 21572 | Unknown (Method: getScriptProgressMessage) | `[정령결속 극대화]를 통해 그리운 랑의 모습을 구현할 수 있게 되었습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 23690 | Unknown (Method: getItemId) | `]이(가) 유효기간이 만료되어 메이플 보관함에서 삭제됩니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 23691 | Unknown (Method: StringBuilder) | `캐비넷 아이템 기간 만료` | Pending |
| `src\objects\users\MapleCharacter.java` | 24365 | Unknown (Method: getName) | `님이 거짓말 탐지기를 요청하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 24418 | Unknown (Method: getName) | `] 캐릭터가 거짓말 탐지기 진행중에 채널을 변경하셨거나 게임을 종료하셨네요.` | Pending |
| `src\objects\users\MapleCharacter.java` | 24419 | Unknown (Method: getName) | `채널 변경 혹은 게임 종료` | Pending |
| `src\objects\users\MapleCharacter.java` | 24422 | Unknown (Method: getName) | `] 캐릭터가 거짓말 탐지기에 오답을 입력하셨네요.` | Pending |
| `src\objects\users\MapleCharacter.java` | 24423 | Unknown (Method: getName) | `오답 입력` | Pending |
| `src\objects\users\MapleCharacter.java` | 24426 | Unknown (Method: getName) | `] 캐릭터가 거짓말 탐지기 입력에 너무 오랜 시간이 걸리셨네요.` | Pending |
| `src\objects\users\MapleCharacter.java` | 24427 | Unknown (Method: getName) | `시간 초과` | Pending |
| `src\objects\users\MapleCharacter.java` | 24443 | Unknown (Method: append) | `매크로 결과 : 실패` | Pending |
| `src\objects\users\MapleCharacter.java` | 24444 | Unknown (Method: append) | ` (계정ID : ` | Pending |
| `src\objects\users\MapleCharacter.java` | 24446 | Unknown (Method: append) | `, 캐릭터 이름 : ` | Pending |
| `src\objects\users\MapleCharacter.java` | 24448 | Unknown (Method: append) | `, 실패 사유 : ` | Pending |
| `src\objects\users\MapleCharacter.java` | 24455 | Unknown (Method: getChannel) | `채널에서 ` | Pending |
| `src\objects\users\MapleCharacter.java` | 24455 | Unknown (Method: getName) | `이(가) 매크로로 판명되었습니다. 사유 : ` | Pending |
| `src\objects\users\MapleCharacter.java` | 24488 | Unknown (Method: getName) | `님이 거짓말 탐지기 테스트를 ` | Pending |
| `src\objects\users\MapleCharacter.java` | 24490 | Unknown (Method: getStartActiveMacroTime) | `초동안 ` | Pending |
| `src\objects\users\MapleCharacter.java` | 24492 | Unknown (Method: get) | `회 시도하여 통과하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 24497 | Unknown (Method: append) | `매크로 결과 : 통과` | Pending |
| `src\objects\users\MapleCharacter.java` | 24498 | Unknown (Method: append) | ` (계정ID : ` | Pending |
| `src\objects\users\MapleCharacter.java` | 24500 | Unknown (Method: append) | `, 캐릭터 이름 : ` | Pending |
| `src\objects\users\MapleCharacter.java` | 24546 | Unknown (Method: contains) | `연습` | Pending |
| `src\objects\users\MapleCharacter.java` | 24547 | Unknown (Method: contains) | `스우` | Pending |
| `src\objects\users\MapleCharacter.java` | 24551 | Unknown (Method: contains) | `데미안` | Pending |
| `src\objects\users\MapleCharacter.java` | 24555 | Unknown (Method: contains) | `루시드` | Pending |
| `src\objects\users\MapleCharacter.java` | 24559 | Unknown (Method: contains) | `진힐라` | Pending |
| `src\objects\users\MapleCharacter.java` | 24563 | Unknown (Method: contains) | `파풀라투스` | Pending |
| `src\objects\users\MapleCharacter.java` | 24567 | Unknown (Method: contains) | `자쿰` | Pending |
| `src\objects\users\MapleCharacter.java` | 24571 | Unknown (Method: contains) | `듄켈` | Pending |
| `src\objects\users\MapleCharacter.java` | 24575 | Unknown (Method: contains) | `더스크` | Pending |
| `src\objects\users\MapleCharacter.java` | 24579 | Unknown (Method: contains) | `윌` | Pending |
| `src\objects\users\MapleCharacter.java` | 24968 | Unknown (Method: SimpleDateFormat) | `yyyy년 MM월 dd일 HH시 mm분` | Pending |
| `src\objects\users\MapleCharacter.java` | 24971 | Unknown (Method: currentTimeMillis) | `[핫타임 보상]` | Pending |
| `src\objects\users\MapleCharacter.java` | 24972 | Unknown (Method: currentTimeMillis) | `에 지급된 핫타임 보상입니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 24999 | Unknown (Method: SimpleDateFormat) | `yyyy년 MM월 dd일 HH시 mm분` | Pending |
| `src\objects\users\MapleCharacter.java` | 25002 | Unknown (Method: currentTimeMillis) | `[이벤트 지급]` | Pending |
| `src\objects\users\MapleCharacter.java` | 25003 | Unknown (Method: currentTimeMillis) | `에 지급된 이벤트 보상입니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 25039 | Unknown (Method: SimpleDateFormat) | `yyyy년 MM월 dd일 HH시 mm분` | Pending |
| `src\objects\users\MapleCharacter.java` | 25041 | Unknown (Method: currentTimeMillis) | `[275레벨 보상]` | Pending |
| `src\objects\users\MapleCharacter.java` | 25042 | Unknown (Method: currentTimeMillis) | `에 지급된 보상입니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 25202 | Unknown (Method: append) | `거짓말 탐지기 통과 실패 : ` | Pending |
| `src\objects\users\MapleCharacter.java` | 25204 | Unknown (Method: append) | ` / 계정 : ` | Pending |
| `src\objects\users\MapleCharacter.java` | 25206 | Unknown (Method: append) | ` / 실패사유 : ` | Pending |
| `src\objects\users\MapleCharacter.java` | 25548 | Unknown (Method: getAllCurrentTime) | `에 ` | Pending |
| `src\objects\users\MapleCharacter.java` | 25548 | Unknown (Method: getName) | `이 ` | Pending |
| `src\objects\users\MapleCharacter.java` | 25549 | Unknown (Method: getId) | `를 처치하고 얻은 아이템.` | Pending |
| `src\objects\users\MapleCharacter.java` | 26135 | Unknown (Method: contains) | `이벤트` | Pending |
| `src\objects\users\MapleCharacter.java` | 26139 | Unknown (Method: contains) | `패키지` | Pending |
| `src\objects\users\MapleCharacter.java` | 26202 | Unknown (Method: getAccountName) | `, 총 익스트림 포인트 복구 포인트: ` | Pending |
| `src\objects\users\MapleCharacter.java` | 26203 | Unknown (Method: getAccountName) | `, 총 누적 금액 : ` | Pending |
| `src\objects\users\MapleCharacter.java` | 26755 | Unknown (Method: getName) | `님이 초월 환생을 하여 누적 환생 횟수 ` | Pending |
| `src\objects\users\MapleCharacter.java` | 26757 | Unknown (Method: getRebirthCount) | `회(각성: ` | Pending |
| `src\objects\users\MapleCharacter.java` | 26759 | Unknown (Method: getSuperRebirthCount) | `회)를 달성하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 26839 | Unknown (Method: getName) | `님이 환생을 하여 누적 환생 횟수 ` | Pending |
| `src\objects\users\MapleCharacter.java` | 26839 | Unknown (Method: getRebirthCount) | `회(각성: ` | Pending |
| `src\objects\users\MapleCharacter.java` | 26840 | Unknown (Method: getSuperRebirthCount) | `회)를 달성하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 26891 | Unknown (Method: getName) | `님이 각성을 하여 누적 환생 횟수 ` | Pending |
| `src\objects\users\MapleCharacter.java` | 26891 | Unknown (Method: getRebirthCount) | `회(각성: ` | Pending |
| `src\objects\users\MapleCharacter.java` | 26892 | Unknown (Method: getSuperRebirthCount) | `회)를 달성하였습니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 26896 | Unknown (Method: addPopupSay) | `#b강림 리버스 멤버십 스킬 포인트(SP) 1개#k를 획득하였습니다.\r\n왼쪽 별모...` | Pending |
| `src\objects\users\MapleCharacter.java` | 27256 | Unknown (Method: outputFileErrorReason) | `updateChannelChange 함수 실행중 오류발생 길드` | Pending |
| `src\objects\users\MapleCharacter.java` | 27264 | Unknown (Method: outputFileErrorReason) | `updateChannelChange 함수 실행중 오류발생 removePlayer` | Pending |
| `src\objects\users\MapleCharacter.java` | 27315 | Unknown (Method: outputFileErrorReason) | `updateChannelChange 함수 실행중 오류발생 removePlayer` | Pending |
| `src\objects\users\MapleCharacter.java` | 27351 | Unknown (Method: SimpleDateFormat) | `yyyy년 MM월 dd일 HH시 mm분` | Pending |
| `src\objects\users\MapleCharacter.java` | 27364 | Unknown (Method: currentTimeMillis) | `[핫타임 보상]` | Pending |
| `src\objects\users\MapleCharacter.java` | 27365 | Unknown (Method: currentTimeMillis) | `에 지급된 핫타임 보상입니다.` | Pending |
| `src\objects\users\MapleCharacter.java` | 27516 | Variable/Other | `일반` | Pending |
| `src\objects\users\MapleCharacter.java` | 27572 | Unknown (Method: switch) | `비기닝` | Pending |
| `src\objects\users\MapleCharacter.java` | 27574 | Unknown (Method: switch) | `라이징` | Pending |
| `src\objects\users\MapleCharacter.java` | 27576 | Unknown (Method: switch) | `플라잉` | Pending |
| `src\objects\users\MapleCharacter.java` | 27578 | Unknown (Method: switch) | `샤이닝` | Pending |
| `src\objects\users\MapleCharacter.java` | 27580 | Unknown (Method: switch) | `아이돌` | Pending |
| `src\objects\users\MapleCharacter.java` | 27582 | Unknown (Method: switch) | `슈퍼스타` | Pending |
| `src\objects\users\MapleCharacter.java` | 27584 | Unknown (Method: switch) | `일반` | Pending |
| `src\objects\users\MapleCharacter.java` | 28269 | Unknown (Method: contains) | `성` | Pending |
| `src\objects\users\MapleClient.java` | 846 | Unknown (Method: RuntimeException) | `[오류] 존재하지 않는 계정이 로그인 성공되었습니다.` | Pending |
| `src\objects\users\MapleClient.java` | 1065 | Unknown (Method: append) | `접속 해제 (아이피 : ` | Pending |
| `src\objects\users\MapleClient.java` | 1093 | Unknown (Method: append) | `접속 해제 (아이피 : ` | Pending |
| `src\objects\users\MapleClient.java` | 1118 | Unknown (Method: log) | `버프저장중 오류 발생` | Pending |
| `src\objects\users\MapleClient.java` | 1128 | Unknown (Method: log) | `거짓말탐지기 저장중 오류발생` | Pending |
| `src\objects\users\MapleClient.java` | 1147 | Unknown (Method: log) | `removePlayer중 오류발생` | Pending |
| `src\objects\users\MapleClient.java` | 1215 | Unknown (Method: log) | `스크립트 엔진 초기화중 오류 발생` | Pending |
| `src\objects\users\MapleClient.java` | 1223 | Unknown (Method: log) | `관리기 동접자 출력중 오류 발생` | Pending |
| `src\objects\users\MapleClient.java` | 2158 | Unknown (Method: format) | ` 까지 채팅 이용이 정지된 계정입니다.` | Pending |
| `src\objects\users\MapleTrade.java` | 332 | Unknown (Method: append) | `보낸 캐릭터 : ` | Pending |
| `src\objects\users\MapleTrade.java` | 335 | Unknown (Method: append) | `받은 캐릭터 : ` | Pending |
| `src\objects\users\MapleTrade.java` | 338 | Unknown (Method: append) | `\r\n아이템(` | Pending |
| `src\objects\users\MapleTrade.java` | 342 | Unknown (Method: append) | `개` | Pending |
| `src\objects\users\MapleTrade.java` | 357 | Unknown (Method: append) | `, 메소 : ` | Pending |
| `src\objects\users\MapleTrade.java` | 359 | Unknown (Method: append) | ` \| \r\n아이템(` | Pending |
| `src\objects\users\MapleTrade.java` | 363 | Unknown (Method: append) | `개` | Pending |
| `src\objects\users\MapleTrade.java` | 378 | Unknown (Method: append) | `, 메소 : ` | Pending |
| `src\objects\users\MapleTrade.java` | 388 | Unknown (Method: getName) | `의 인벤토리 공간 부족, ` | Pending |
| `src\objects\users\MapleTrade.java` | 390 | Unknown (Method: getName) | `이 거래 불가 아이템 거래 시도, ` | Pending |
| `src\objects\users\MapleTrade.java` | 396 | Unknown (Method: getName) | `의 인벤토리 공간 부족, ` | Pending |
| `src\objects\users\MapleTrade.java` | 398 | Unknown (Method: getName) | `이 거래 불가 아이템 거래 시도, ` | Pending |
| `src\objects\users\MapleTrade.java` | 403 | Unknown (Method: append) | `취소 사유 : (` | Pending |
| `src\objects\users\MapleTrade.java` | 405 | Unknown (Method: append) | `보낸 캐릭터 : ` | Pending |
| `src\objects\users\MapleTrade.java` | 408 | Unknown (Method: append) | `받은 캐릭터 : ` | Pending |
| `src\objects\users\MapleTrade.java` | 411 | Unknown (Method: append) | `\r\n아이템(` | Pending |
| `src\objects\users\MapleTrade.java` | 415 | Unknown (Method: append) | `개` | Pending |
| `src\objects\users\MapleTrade.java` | 430 | Unknown (Method: append) | `, 메소 : ` | Pending |
| `src\objects\users\MapleTrade.java` | 432 | Unknown (Method: append) | ` \| \r\n아이템(` | Pending |
| `src\objects\users\MapleTrade.java` | 436 | Unknown (Method: append) | `개` | Pending |
| `src\objects\users\MapleTrade.java` | 451 | Unknown (Method: append) | `, 메소 : ` | Pending |
| `src\objects\users\MapleTrade.java` | 469 | Unknown (Method: getPartner) | `캐릭터 이름 '` | Pending |
| `src\objects\users\MapleTrade.java` | 469 | Unknown (Method: getName) | `' 거래 취소` | Pending |
| `src\objects\users\MapleTrade.java` | 471 | Unknown (Method: append) | `취소 사유 : (` | Pending |
| `src\objects\users\MapleTrade.java` | 473 | Unknown (Method: append) | `보낸 캐릭터 : ` | Pending |
| `src\objects\users\MapleTrade.java` | 476 | Unknown (Method: append) | `받은 캐릭터 : ` | Pending |
| `src\objects\users\MapleTrade.java` | 495 | Unknown (Method: append) | `\r\n아이템(` | Pending |
| `src\objects\users\MapleTrade.java` | 499 | Unknown (Method: append) | `개` | Pending |
| `src\objects\users\MapleTrade.java` | 514 | Unknown (Method: append) | `, 메소 : ` | Pending |
| `src\objects\users\MapleTrade.java` | 517 | Unknown (Method: append) | ` \| \r\n아이템(` | Pending |
| `src\objects\users\MapleTrade.java` | 525 | Unknown (Method: append) | `개` | Pending |
| `src\objects\users\MapleTrade.java` | 539 | Unknown (Method: append) | `파트너 정보 Null` | Pending |
| `src\objects\users\MapleTrade.java` | 542 | Unknown (Method: append) | `, 메소 : ` | Pending |
| `src\objects\users\MapleTrade.java` | 546 | Unknown (Method: append) | `파트너 정보 Null` | Pending |
| `src\objects\users\achievement\caching\mission\submission\checkvalue\SpelltraceEnchant.java` | 20 | Unknown (Method: contains) | `이노센트` | Pending |
| `src\objects\users\achievement\caching\mission\submission\checkvalue\SpelltraceEnchant.java` | 24 | Unknown (Method: contains) | `백의` | Pending |
| `src\objects\users\enchant\EquipEnchantScroll.java` | 21 | Unknown (Method: getNameByFlag) | ` 주문서` | Pending |
| `src\objects\users\enchant\EquipEnchantScroll.java` | 31 | Unknown (Method: if) | `이노센트 주문서 100%` | Pending |
| `src\objects\users\enchant\EquipEnchantScroll.java` | 35 | Unknown (Method: if) | `아크 이노센트 주문서 100%` | Pending |
| `src\objects\users\enchant\EquipEnchantScroll.java` | 42 | Unknown (Method: if) | `순백의 주문서 100%` | Pending |
| `src\objects\users\enchant\EquipEnchantScroll.java` | 207 | Unknown (Method: contains) | `성` | Pending |
| `src\objects\users\enchant\EquipEnchantScroll.java` | 234 | Unknown (Method: setOwner) | `성` | Pending |
| `src\objects\users\enchant\StarForceHyperUpgrade.java` | 408 | Unknown (Method: contains) | `강` | Pending |
| `src\objects\users\enchant\StarForceHyperUpgrade.java` | 416 | Unknown (Method: split) | `강` | Pending |
| `src\objects\users\extra\ExtraAbilityGrade.java` | 4 | Unknown (Method: Rare) | `레어` | Pending |
| `src\objects\users\extra\ExtraAbilityGrade.java` | 5 | Unknown (Method: Epic) | `에픽` | Pending |
| `src\objects\users\extra\ExtraAbilityGrade.java` | 6 | Unknown (Method: Unique) | `유니크` | Pending |
| `src\objects\users\extra\ExtraAbilityGrade.java` | 7 | Unknown (Method: Legendary) | `레전드리` | Pending |
| `src\objects\users\extra\ExtraAbilityOption.java` | 6 | Unknown (Method: None) | `없음` | Pending |
| `src\objects\users\extra\ExtraAbilityOption.java` | 7 | Unknown (Method: DamageReduceR) | `아케인 포스 +%d 증가` | Pending |
| `src\objects\users\extra\ExtraAbilityOption.java` | 8 | Unknown (Method: Str) | `STR +%d 증가` | Pending |
| `src\objects\users\extra\ExtraAbilityOption.java` | 9 | Unknown (Method: Dex) | `DEX +%d 증가` | Pending |
| `src\objects\users\extra\ExtraAbilityOption.java` | 10 | Unknown (Method: Int) | `INT +%d 증가` | Pending |
| `src\objects\users\extra\ExtraAbilityOption.java` | 11 | Unknown (Method: Luk) | `LUK +%d 증가` | Pending |
| `src\objects\users\extra\ExtraAbilityOption.java` | 12 | Unknown (Method: AllStat) | `올스탯 +%d 증가` | Pending |
| `src\objects\users\extra\ExtraAbilityOption.java` | 13 | Unknown (Method: MaxHp) | `최대 HP +%d 증가` | Pending |
| `src\objects\users\extra\ExtraAbilityOption.java` | 14 | Unknown (Method: Attack) | `공격력/마력 +%d 증가` | Pending |
| `src\objects\users\extra\ExtraAbilityOption.java` | 15 | Unknown (Method: MaxHpR) | `최대 HP +%d%s 증가` | Pending |
| `src\objects\users\extra\ExtraAbilityOption.java` | 16 | Unknown (Method: AllStatR) | `올스탯 +%d%s 증가 (직접 투자한 스탯)` | Pending |
| `src\objects\users\extra\ExtraAbilityOption.java` | 17 | Unknown (Method: AttackR) | `공격력/마력 +%d%s 증가` | Pending |
| `src\objects\users\extra\ExtraAbilityOption.java` | 18 | Unknown (Method: CriticalRate) | `크리티컬 확률 +%d%s 증가` | Pending |
| `src\objects\users\extra\ExtraAbilityOption.java` | 19 | Unknown (Method: IgnoreMobPdpR) | `몬스터 방어력 무시 +%d%s` | Pending |
| `src\objects\users\extra\ExtraAbilityOption.java` | 20 | Unknown (Method: BossDamageR) | `보스 공격 시 데미지 +%d%s 증가` | Pending |
| `src\objects\users\extra\ExtraAbilityOption.java` | 21 | Unknown (Method: ReduceCooltime) | `재사용 대기시간 %d초 감소` | Pending |
| `src\objects\users\extra\ExtraAbilityOption.java` | 22 | Unknown (Method: MesoRateR) | `메소 획득량 +%d%s 증가` | Pending |
| `src\objects\users\extra\ExtraAbilityOption.java` | 23 | Unknown (Method: DropRateR) | `아이템 드롭률 +%d%s 증가` | Pending |
| `src\objects\users\extra\ExtraAbilityOption.java` | 24 | Unknown (Method: ExpRateR) | `경험치 획득량 +%d%s 증가` | Pending |
| `src\objects\users\extra\ExtraAbilityOption.java` | 25 | Unknown (Method: CriticalDamage) | `크리티컬 데미지 +%d%s 증가` | Pending |
| `src\objects\users\extra\ExtraAbilityOption.java` | 26 | Unknown (Method: IncMobGen) | `몬스터 리젠 개체수 1.5배 증가` | Pending |
| `src\objects\users\extra\ExtraAbilityOption.java` | 27 | Unknown (Method: ReviveInvincible) | `부활 시 무적 시간 %d초 증가` | Pending |
| `src\objects\users\extra\ExtraAbilityOption.java` | 28 | Unknown (Method: TerR) | `상태 이상 내성 +%d%s 증가` | Pending |
| `src\objects\users\extra\ExtraAbilityOption.java` | 29 | Unknown (Method: PMDR) | `최종 데미지 +%d%s 증가` | Pending |
| `src\objects\users\skills\DamageParse.java` | 894 | Unknown (Method: append) | `데미지 핵 사용 : ` | Pending |
| `src\objects\users\skills\DamageParse.java` | 896 | Unknown (Method: append) | `, 스킬ID : ` | Pending |
| `src\objects\users\skills\DamageParse.java` | 1164 | Unknown (Method: append) | `데미지 핵 사용 : ` | Pending |
| `src\objects\users\skills\DamageParse.java` | 1166 | Unknown (Method: append) | `, 스킬ID : ` | Pending |
| `src\objects\users\skills\DamageParse.java` | 1720 | Unknown (Method: append) | `데미지 핵 사용 : ` | Pending |
| `src\objects\users\skills\DamageParse.java` | 1722 | Unknown (Method: append) | `, 스킬ID : ` | Pending |
| `src\objects\users\skills\DamageParse.java` | 1763 | Unknown (Method: append) | `데미지 핵 사용 : ` | Pending |
| `src\objects\users\skills\DamageParse.java` | 1765 | Unknown (Method: append) | `, 스킬ID : ` | Pending |
| `src\objects\users\skills\DamageParse.java` | 2017 | Unknown (Method: outputFileErrorReason) | ` 공격 데미지 추가 정보 파싱 오류 : ` | Pending |
| `src\objects\users\skills\TeleportAttackData_DoubleArray.java` | 18 | Unknown (Method: if) | `TeleportAttackData_DoubleArray 오류 (데미지 파싱 잘못됨)\r\n` | Pending |
| `src\objects\users\skills\TeleportAttackData_TriArray.java` | 18 | Unknown (Method: if) | `TeleportAttackData_TriArray 오류 (데미지 파싱 잘못됨)\r\n` | Pending |
| `src\objects\utils\AdminClient.java` | 1153 | Unknown (Method: showMessageDialog) | `이미 자동 저장이 실행중입니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 1318 | Unknown (Method: showMessageDialog) | `서버 저장 쓰레드가 실행됩니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 1335 | Unknown (Method: showMessageDialog) | `1채널에 있는 모든 플레이어를 광장으로 이동시켰습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 1342 | Unknown (Method: equals) | `지급할 캐릭터 이름` | Pending |
| `src\objects\utils\AdminClient.java` | 1351 | Unknown (Method: gainRealCash) | `후원 캐시` | Pending |
| `src\objects\utils\AdminClient.java` | 1351 | Unknown (Method: gainRealCash) | `강림 포인트` | Pending |
| `src\objects\utils\AdminClient.java` | 1354 | Unknown (Method: getKoreanJosa) | ` 지급 받았습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 1356 | Unknown (Method: showMessageDialog) | `포인트를 숫자로 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 1372 | Unknown (Method: gainRealCash) | `후원 캐시` | Pending |
| `src\objects\utils\AdminClient.java` | 1372 | Unknown (Method: gainRealCash) | `강림 포인트` | Pending |
| `src\objects\utils\AdminClient.java` | 1375 | Unknown (Method: getKoreanJosa) | ` 지급 받았습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 1377 | Unknown (Method: showMessageDialog) | `포인트를 숫자로 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 1393 | Unknown (Method: gainRealCash) | `후원 캐시` | Pending |
| `src\objects\utils\AdminClient.java` | 1393 | Unknown (Method: gainRealCash) | `강림 포인트` | Pending |
| `src\objects\utils\AdminClient.java` | 1396 | Unknown (Method: getKoreanJosa) | ` 지급 받았습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 1398 | Unknown (Method: showMessageDialog) | `포인트를 숫자로 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 1424 | Unknown (Method: gainRealCash) | `후원 캐시` | Pending |
| `src\objects\utils\AdminClient.java` | 1424 | Unknown (Method: gainRealCash) | `강림 포인트` | Pending |
| `src\objects\utils\AdminClient.java` | 1427 | Unknown (Method: getKoreanJosa) | ` 지급 받았습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 1429 | Unknown (Method: showMessageDialog) | `포인트를 숫자로 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 1446 | Unknown (Method: gainRealCash) | `후원 캐시` | Pending |
| `src\objects\utils\AdminClient.java` | 1446 | Unknown (Method: gainRealCash) | `강림 포인트` | Pending |
| `src\objects\utils\AdminClient.java` | 1449 | Unknown (Method: getKoreanJosa) | ` 지급 받았습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 1451 | Unknown (Method: showMessageDialog) | `포인트를 숫자로 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 1468 | Unknown (Method: gainRealCash) | `후원 캐시` | Pending |
| `src\objects\utils\AdminClient.java` | 1468 | Unknown (Method: gainRealCash) | `강림 포인트` | Pending |
| `src\objects\utils\AdminClient.java` | 1471 | Unknown (Method: getKoreanJosa) | ` 지급 받았습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 1473 | Unknown (Method: showMessageDialog) | `포인트를 숫자로 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 1511 | Unknown (Method: append) | `후원 포인트 지급 : ` | Pending |
| `src\objects\utils\AdminClient.java` | 1515 | Unknown (Method: getText) | ` 포인트` | Pending |
| `src\objects\utils\AdminClient.java` | 1520 | Unknown (Method: showMessageDialog) | `오프라인 지급이 완료되었습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 1522 | Unknown (Method: showMessageDialog) | `오프라인 지급 중 오류가 발생하였습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 1525 | Unknown (Method: showMessageDialog) | `지급이 완료되었습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 1528 | Unknown (Method: showMessageDialog) | `후원 캐시` | Pending |
| `src\objects\utils\AdminClient.java` | 1528 | Unknown (Method: showMessageDialog) | `강림 포인트` | Pending |
| `src\objects\utils\AdminClient.java` | 1530 | Unknown (Method: getKoreanJosa) | ` 지급할 캐릭터를 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 1586 | Unknown (Method: showMessageDialog) | `(을)를 접속해제 하였습니다` | Pending |
| `src\objects\utils\AdminClient.java` | 1588 | Unknown (Method: showMessageDialog) | `해당 유저를 찾을 수 없습니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 1593 | Unknown (Method: equals) | `지급할 캐릭터 이름` | Pending |
| `src\objects\utils\AdminClient.java` | 1625 | Unknown (Method: showMessageDialog) | `접속해제할 캐릭터를 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 1633 | Unknown (Method: showConfirmDialog) | `서버 저장 및 리붓을 진행하시겠습니까?` | Pending |
| `src\objects\utils\AdminClient.java` | 1636 | Unknown (Method: showMessageDialog) | `서버 저장이 진행중입니다. 저장이 완료된 후 다시 시도해주세요.` | Pending |
| `src\objects\utils\AdminClient.java` | 1641 | Unknown (Method: showMessageDialog) | `서버 저장 및 리붓이 진행됩니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 1763 | Unknown (Method: setText) | `채팅창 얼리기` | Pending |
| `src\objects\utils\AdminClient.java` | 1764 | Unknown (Method: showMessageDialog) | `채팅창을 녹였습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 1767 | Unknown (Method: setText) | `채팅창 녹이기` | Pending |
| `src\objects\utils\AdminClient.java` | 1768 | Unknown (Method: showMessageDialog) | `채팅창을 얼렸습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 1776 | Unknown (Method: equals) | `지급할 포인트` | Pending |
| `src\objects\utils\AdminClient.java` | 1782 | Unknown (Method: equals) | `지급할 포인트` | Pending |
| `src\objects\utils\AdminClient.java` | 1788 | Unknown (Method: equals) | `아이템 코드,갯수` | Pending |
| `src\objects\utils\AdminClient.java` | 1794 | Unknown (Method: equals) | `지급할 메소` | Pending |
| `src\objects\utils\AdminClient.java` | 1800 | Unknown (Method: equals) | `아이템 코드,갯수` | Pending |
| `src\objects\utils\AdminClient.java` | 1806 | Unknown (Method: equals) | `지급 받을 회원수` | Pending |
| `src\objects\utils\AdminClient.java` | 1812 | Unknown (Method: equals) | `지급할 캐릭터 이름` | Pending |
| `src\objects\utils\AdminClient.java` | 1818 | Unknown (Method: equals) | `지급할 캐릭터 이름` | Pending |
| `src\objects\utils\AdminClient.java` | 1826 | Unknown (Method: getText) | `메소를 지급 받았습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 1828 | Unknown (Method: showMessageDialog) | `메소를 숫자로 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 1839 | Unknown (Method: getText) | `(을)를 찾을 수 없습니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 1841 | Unknown (Method: showMessageDialog) | `지급이 완료되었습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 1844 | Unknown (Method: showMessageDialog) | `메소를 지급할 캐릭터를 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 1849 | Unknown (Method: equals) | `지급할 캐릭터 이름` | Pending |
| `src\objects\utils\AdminClient.java` | 1858 | Unknown (Method: getText) | ` 홍보 포인트를 지급 받았습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 1860 | Unknown (Method: showMessageDialog) | `포인트를 숫자로 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 1876 | Unknown (Method: getText) | ` 홍보 포인트를 지급 받았습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 1878 | Unknown (Method: showMessageDialog) | `포인트를 숫자로 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 1894 | Unknown (Method: getText) | ` 홍보 포인트를 지급 받았습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 1896 | Unknown (Method: showMessageDialog) | `포인트를 숫자로 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 1922 | Unknown (Method: gainHongboPoint) | `후원 캐시` | Pending |
| `src\objects\utils\AdminClient.java` | 1922 | Unknown (Method: gainHongboPoint) | `강림 포인트` | Pending |
| `src\objects\utils\AdminClient.java` | 1924 | Unknown (Method: getText) | ` 홍보 포인트를 지급 받았습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 1926 | Unknown (Method: showMessageDialog) | `포인트를 숫자로 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 1944 | Unknown (Method: getText) | ` 홍보 포인트를 지급 받았습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 1946 | Unknown (Method: showMessageDialog) | `포인트를 숫자로 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 1964 | Unknown (Method: getText) | ` 홍보 포인트를 지급 받았습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 1966 | Unknown (Method: showMessageDialog) | `포인트를 숫자로 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 2006 | Unknown (Method: append) | `홍보 포인트 지급 : ` | Pending |
| `src\objects\utils\AdminClient.java` | 2010 | Unknown (Method: getText) | ` 포인트` | Pending |
| `src\objects\utils\AdminClient.java` | 2013 | Unknown (Method: showMessageDialog) | `지급이 완료되었습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2015 | Unknown (Method: showMessageDialog) | `홍보 포인트를 지급할 캐릭터를 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 2023 | Unknown (Method: equals) | `지급할 캐릭터 이름` | Pending |
| `src\objects\utils\AdminClient.java` | 2035 | Unknown (Method: getAllCurrentTime) | `에 서버 관리기 아이템 지급을 통해 만들어진 아이템` | Pending |
| `src\objects\utils\AdminClient.java` | 2038 | Unknown (Method: showMessageDialog) | `존재하지 않는 아이템입니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2040 | Unknown (Method: SimpleDateFormat) | `yyyy년 MM월 dd일 HH시 mm분` | Pending |
| `src\objects\utils\AdminClient.java` | 2075 | Unknown (Method: showMessageDialog) | `존재하지 않는 유저입니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2091 | Unknown (Method: showMessageDialog) | `해당 유저의 보관함을 열람하는 과정에서 오류가 발생하였습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2098 | Unknown (Method: currentTimeMillis) | `[GM 선물]` | Pending |
| `src\objects\utils\AdminClient.java` | 2098 | Unknown (Method: currentTimeMillis) | `에 운영자가 보낸 아이템입니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2103 | Unknown (Method: showMessageDialog) | `해당 플레이어와 동일한 계정 내에 캐릭터에게 전송되었습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2148 | Unknown (Method: getAndIncrement) | `[GM 선물]` | Pending |
| `src\objects\utils\AdminClient.java` | 2149 | Unknown (Method: getAndIncrement) | `에 운영자가 보낸 아이템입니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2251 | Unknown (Method: showMessageDialog) | `오프라인 지급되었습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2277 | Unknown (Method: showMessageDialog) | `해당 유저의 보관함을 열람하는 과정에서 오류가 발생하였습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2282 | Unknown (Method: currentTimeMillis) | `[GM 선물]` | Pending |
| `src\objects\utils\AdminClient.java` | 2283 | Unknown (Method: currentTimeMillis) | `에 운영자가 보낸 아이템입니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2288 | Unknown (Method: showMessageDialog) | `아이템을 지급하였습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2292 | Unknown (Method: getText) | `(을)를 찾을 수 없습니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 2296 | Unknown (Method: showMessageDialog) | `아이템을 지급할 캐릭터를 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 2302 | Unknown (Method: equals) | `아이템 코드, 갯수` | Pending |
| `src\objects\utils\AdminClient.java` | 2304 | Unknown (Method: equals) | `지급 받을 회원수` | Pending |
| `src\objects\utils\AdminClient.java` | 2338 | Unknown (Method: equals) | `전체` | Pending |
| `src\objects\utils\AdminClient.java` | 2339 | Unknown (Method: equals) | `모두` | Pending |
| `src\objects\utils\AdminClient.java` | 2346 | Unknown (Method: showMessageDialog) | `지급될 아이템 ID나 갯수를 숫자로 정확하게 입력해주세요.` | Pending |
| `src\objects\utils\AdminClient.java` | 2351 | Unknown (Method: showMessageDialog) | `번 아이템은 존재하지 않는 아이템입니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2360 | Unknown (Method: showMessageDialog) | `지급이 완료되었습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2376 | Unknown (Method: SimpleDateFormat) | `yyyy년 MM월 dd일 HH시 mm분` | Pending |
| `src\objects\utils\AdminClient.java` | 2383 | Unknown (Method: currentTimeMillis) | `[핫타임 보상]` | Pending |
| `src\objects\utils\AdminClient.java` | 2384 | Unknown (Method: currentTimeMillis) | `에 지급된 핫타임 보상입니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2391 | Unknown (Method: if) | `축하드립니다! ` | Pending |
| `src\objects\utils\AdminClient.java` | 2396 | Unknown (Method: getName) | `] 님이 핫타임 이벤트 보상으로 ` | Pending |
| `src\objects\utils\AdminClient.java` | 2397 | Unknown (Method: getName) | `개를 지급 받았습니다. 모두 축하해주세요!` | Pending |
| `src\objects\utils\AdminClient.java` | 2401 | Unknown (Method: getName) | `개를 핫타임 보상으로 지급 받았습니다.\r\n[메이플 보관함]을 확인해주세요.` | Pending |
| `src\objects\utils\AdminClient.java` | 2411 | Unknown (Method: showMessageDialog) | `지급할 핫타임 아이템 코드나 회원수를 정확히 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 2419 | Unknown (Method: equals) | `공지사항 입력` | Pending |
| `src\objects\utils\AdminClient.java` | 2425 | Unknown (Method: equals) | `공지사항 입력` | Pending |
| `src\objects\utils\AdminClient.java` | 2427 | Unknown (Method: showMessageDialog) | `공지사항을 송출하였습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2429 | Unknown (Method: showMessageDialog) | `공지사항 메세지를 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 2434 | Unknown (Method: equals) | `공지사항 입력` | Pending |
| `src\objects\utils\AdminClient.java` | 2439 | Unknown (Method: showMessageDialog) | `공지사항을 송출하였습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2441 | Unknown (Method: showMessageDialog) | `공지사항 메세지를 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 2446 | Unknown (Method: equals) | `공지사항 입력` | Pending |
| `src\objects\utils\AdminClient.java` | 2448 | Unknown (Method: showMessageDialog) | `공지사항을 송출하였습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2450 | Unknown (Method: showMessageDialog) | `공지사항 메세지를 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 2455 | Unknown (Method: equals) | `공지사항 입력` | Pending |
| `src\objects\utils\AdminClient.java` | 2457 | Unknown (Method: showMessageDialog) | `공지사항을 송출하였습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2459 | Unknown (Method: showMessageDialog) | `공지사항 메세지를 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 2464 | Unknown (Method: equals) | `공지사항 입력` | Pending |
| `src\objects\utils\AdminClient.java` | 2470 | Unknown (Method: showMessageDialog) | `공지사항을 송출하였습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2472 | Unknown (Method: showMessageDialog) | `공지사항 메세지를 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 2477 | Unknown (Method: equals) | `지급할 포인트` | Pending |
| `src\objects\utils\AdminClient.java` | 2484 | Unknown (Method: showMessageDialog) | `추가 의뢰 기능입니다. 문의 주시기 바랍니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2485 | Unknown (Method: equals) | `지급할 캐릭터 이름` | Pending |
| `src\objects\utils\AdminClient.java` | 2514 | Unknown (Method: getText) | `(을)를 찾을 수 없습니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 2516 | Unknown (Method: showMessageDialog) | `해당 갯수 만큼 회수가 완료되었습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2519 | Unknown (Method: showMessageDialog) | `사냥 포인트를 지급할 캐릭터를 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 2525 | Unknown (Method: showMessageDialog) | `추가 의뢰 기능입니다. 문의 주시기 바랍니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2536 | Unknown (Method: showMessageDialog) | `배율을 숫자로 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 2546 | Unknown (Method: showMessageDialog) | `배율 설정이 완료되었습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2572 | Unknown (Method: showMessageDialog) | `핫타임 경험치 배율을 정확히 입력해주시기 바랍니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2578 | Unknown (Method: equals) | `지급할 캐릭터 이름` | Pending |
| `src\objects\utils\AdminClient.java` | 2579 | Unknown (Method: equals) | `초심자패키지` | Pending |
| `src\objects\utils\AdminClient.java` | 2597 | Unknown (Method: append) | `초심자 패키지 지급 : ` | Pending |
| `src\objects\utils\AdminClient.java` | 2603 | Unknown (Method: showMessageDialog) | `초심자 패키지 지급이 완료되었습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2606 | Unknown (Method: equals) | `주간패키지` | Pending |
| `src\objects\utils\AdminClient.java` | 2613 | Unknown (Method: append) | `주간 패키지 지급 : ` | Pending |
| `src\objects\utils\AdminClient.java` | 2619 | Unknown (Method: showMessageDialog) | `주간 패키지 지급이 완료되었습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2623 | Unknown (Method: split) | `만` | Pending |
| `src\objects\utils\AdminClient.java` | 2637 | Unknown (Method: showMessageDialog) | `이미 훈장을 지급 받은 유저입니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2651 | Unknown (Method: append) | ` 지급 : ` | Pending |
| `src\objects\utils\AdminClient.java` | 2657 | Unknown (Method: showMessageDialog) | `만 누적 보상 지급이 완료되었습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2663 | Unknown (Method: showMessageDialog) | `초심자패키지를 지급할 캐릭터를 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 2705 | Unknown (Method: setString) | `밴 사유: ` | Pending |
| `src\objects\utils\AdminClient.java` | 2711 | Unknown (Method: setString) | `밴 사유: ` | Pending |
| `src\objects\utils\AdminClient.java` | 2725 | Unknown (Method: equals) | `지급할 캐릭터 이름` | Pending |
| `src\objects\utils\AdminClient.java` | 2789 | Unknown (Method: getText) | `(을)를 찾을 수 없습니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 2792 | Unknown (Method: showMessageDialog) | `기간 밴 처리 되었습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2794 | Unknown (Method: showMessageDialog) | `시리얼 밴 처리 되었습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2797 | Unknown (Method: showMessageDialog) | `입력값을 확인해 주세요. \n*** 올바른 커맨드 사용법 ***\n-시리얼 밴: 캐릭...` | Pending |
| `src\objects\utils\AdminClient.java` | 2802 | Unknown (Method: showMessageDialog) | `시리얼 밴 할 캐릭터 이름을 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 2807 | Unknown (Method: equals) | `지급할 캐릭터 이름` | Pending |
| `src\objects\utils\AdminClient.java` | 2841 | Unknown (Method: getText) | `(을)를 찾을 수 없습니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 2843 | Unknown (Method: showMessageDialog) | `모든 밴이 해제 처리 되었습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2846 | Unknown (Method: showMessageDialog) | `시리얼 밴 해제 할 캐릭터 이름을 입력해주시기 바랍니다!` | Pending |
| `src\objects\utils\AdminClient.java` | 2859 | Unknown (Method: setText) | `경매장 녹이기` | Pending |
| `src\objects\utils\AdminClient.java` | 2860 | Unknown (Method: showMessageDialog) | `경매장 입장이 제한되었습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2862 | Unknown (Method: setText) | `경매장 얼리기` | Pending |
| `src\objects\utils\AdminClient.java` | 2863 | Unknown (Method: showMessageDialog) | `경매장 입장 제한이 해제되었습니다.` | Pending |
| `src\objects\utils\AdminClient.java` | 2882 | Unknown (Method: if) | `20세 이상` | Pending |
| `src\objects\utils\AdminClient.java` | 2890 | Unknown (Method: append) | `[일반 Ch.` | Pending |
| `src\objects\utils\AdminClient.java` | 2895 | Unknown (Method: append) | `[친구에게 Ch.` | Pending |
| `src\objects\utils\AdminClient.java` | 2912 | Unknown (Method: append) | `[파티에게 Ch.` | Pending |
| `src\objects\utils\AdminClient.java` | 2920 | Unknown (Method: append) | `[길드에게 Ch.` | Pending |
| `src\objects\utils\AdminClient.java` | 2925 | Unknown (Method: append) | `[연합에게 Ch.` | Pending |
| `src\objects\utils\AdminClient.java` | 2930 | Unknown (Method: append) | `[원정대에게 Ch.` | Pending |
| `src\objects\utils\AdminClient.java` | 2935 | Unknown (Method: append) | `[귓속말 : ` | Pending |
| `src\objects\utils\AdminClient.java` | 2944 | Unknown (Method: append) | `[전체 채팅 Ch.` | Pending |
| `src\objects\utils\AdminClient.java` | 3065 | Unknown (Method: setText) | `동시접속자 : ` | Pending |
| `src\objects\utils\AdminClient.java` | 3065 | Unknown (Method: setText) | `명` | Pending |
| `src\objects\utils\AdminClient.java` | 3088 | Unknown (Method: setText) | `서버 런타임 : ` | Pending |
| `src\objects\utils\AdminClient.java` | 3088 | Unknown (Method: setText) | `일 ` | Pending |
| `src\objects\utils\AdminClient.java` | 3088 | Unknown (Method: setText) | `시간 ` | Pending |
| `src\objects\utils\AdminClient.java` | 3088 | Unknown (Method: setText) | `분 ` | Pending |
| `src\objects\utils\AdminClient.java` | 3088 | Unknown (Method: setText) | `초` | Pending |
| `src\objects\utils\AutobanManager.java` | 63 | Unknown (Method: StringBuilder) | `오토밴 (name : ` | Pending |
| `src\objects\utils\AutobanManager.java` | 67 | Unknown (Method: append) | `) 사유 : ` | Pending |
| `src\objects\utils\bitFalg.java` | 68 | Unknown (Method: append) | `총 크기:` | Pending |
| `src\objects\utils\bitFalg.java` | 69 | Unknown (Method: append) | `더미 int * 2 제거크기:` | Pending |
| `src\objects\utils\bitFalg.java` | 74 | Unknown (Method: append) | `포지션: ` | Pending |
| `src\objects\utils\bitFalg.java` | 75 | Unknown (Method: insertRow) | `포지션 :` | Pending |
| `src\objects\utils\bitFalg.java` | 76 | Unknown (Method: append) | `버프마스크: ` | Pending |
| `src\objects\utils\bitFalg.java` | 77 | Unknown (Method: insertRow) | `버프마스크: ` | Pending |
| `src\objects\utils\bitFalg.java` | 83 | Unknown (Method: insertRow) | `이름 : ` | Pending |
| `src\objects\utils\bitFalg.java` | 84 | Unknown (Method: insertRow) | `플래그 : ` | Pending |
| `src\objects\utils\bitFalg.java` | 85 | Unknown (Method: insertRow) | `비트 플래그 : ` | Pending |
| `src\objects\utils\bitFalg.java` | 93 | Unknown (Method: append) | `비트마스킹 이후 크기: ` | Pending |
| `src\objects\utils\CMDCommand.java` | 38 | Unknown (Method: switch) | `낚시리셋` | Pending |
| `src\objects\utils\CMDCommand.java` | 42 | Unknown (Method: Load) | `핫타임아이템리셋` | Pending |
| `src\objects\utils\CMDCommand.java` | 46 | Unknown (Method: loadHottimeItem) | `피버리셋` | Pending |
| `src\objects\utils\CMDCommand.java` | 55 | Unknown (Method: registerAutoFever) | `전투력리셋` | Pending |
| `src\objects\utils\CMDCommand.java` | 68 | Unknown (Method: nobleSPAdjustmentF) | `블라썸저장` | Pending |
| `src\objects\utils\CMDCommand.java` | 78 | Unknown (Method: resetScript) | `봇테스트` | Pending |
| `src\objects\utils\CMDCommand.java` | 79 | Unknown (Method: requestSendTelegramWithChatID) | `봇에서 보내는 테스트입니다. 1` | Pending |
| `src\objects\utils\CMDCommand.java` | 80 | Unknown (Method: requestSendTelegramWithChatID) | `봇에서 보내는 테스트입니다. 2` | Pending |
| `src\objects\utils\CMDCommand.java` | 81 | Unknown (Method: requestSendTelegramWithChatID) | `봇에서 보내는 테스트입니다. 3` | Pending |
| `src\objects\utils\CMDCommand.java` | 83 | Unknown (Method: requestSendTelegramWithChatID) | `도움말` | Pending |
| `src\objects\utils\CMDCommand.java` | 101 | Unknown (Method: joinStringFrom) | `모두종료` | Pending |
| `src\objects\utils\CMDCommand.java` | 123 | Unknown (Method: getScriptProgressMessage) | `해당 플레이어가 GM ` | Pending |
| `src\objects\utils\CMDCommand.java` | 123 | Unknown (Method: getScriptProgressMessage) | `레벨이 되었습니다.` | Pending |
| `src\objects\utils\CMDCommand.java` | 145 | Unknown (Method: joinStringFrom) | `서버종료` | Pending |
| `src\objects\utils\CMDCommand.java` | 167 | Unknown (Method: IntroEnableUI) | `드롭리셋` | Pending |
| `src\objects\utils\ConnectorController.java` | 47 | Unknown (Method: size) | `명` | Pending |
| `src\objects\utils\ConnectorController.java` | 53 | Unknown (Method: size) | `명` | Pending |
| `src\objects\utils\ConnectorController.java` | 58 | Unknown (Method: setText) | `개` | Pending |
| `src\objects\utils\ConnectorController.java` | 63 | Unknown (Method: setText) | `개` | Pending |
| `src\objects\utils\ConnectorController.java` | 76 | Unknown (Method: setText) | `시리얼 밴` | Pending |
| `src\objects\utils\ConnectorController.java` | 83 | Unknown (Method: setText) | `시리얼 밴 해제` | Pending |
| `src\objects\utils\ConnectorController.java` | 84 | Unknown (Method: Font) | `굴림` | Pending |
| `src\objects\utils\ConnectorController.java` | 85 | Unknown (Method: setText) | `연결자 수 :` | Pending |
| `src\objects\utils\ConnectorController.java` | 86 | Unknown (Method: Font) | `굴림` | Pending |
| `src\objects\utils\ConnectorController.java` | 87 | Unknown (Method: setText) | `0명` | Pending |
| `src\objects\utils\ConnectorController.java` | 90 | Unknown (Method: Font) | `굴림` | Pending |
| `src\objects\utils\ConnectorController.java` | 91 | Unknown (Method: setText) | `쓰레드 수 :` | Pending |
| `src\objects\utils\ConnectorController.java` | 92 | Unknown (Method: Font) | `굴림` | Pending |
| `src\objects\utils\ConnectorController.java` | 93 | Unknown (Method: setText) | `0개` | Pending |
| `src\objects\utils\CurrentTime.java` | 17 | Unknown (Method: SimpleDateFormat) | `MM월dd일` | Pending |
| `src\objects\utils\DonationRanker.java` | 69 | Unknown (Method: get) | `위, ` | Pending |
| `src\objects\utils\DonationRanker.java` | 69 | Unknown (Method: getValue) | ` 대표 캐릭터 : ` | Pending |
| `src\objects\utils\FileoutputUtil.java` | 97 | Variable/Other | `에러원인 : ` | Pending |
