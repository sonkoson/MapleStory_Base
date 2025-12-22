importPackage(Packages.handling.world);
importPackage(Packages.tools.packet);

var purpleIcon = "#fMap/MapHelper.img/weather/starPlanet/7#";
var blueIcon = "#fMap/MapHelper.img/weather/starPlanet/8#";
var starBlue = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
var starYellow = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
var starWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
var starBrown = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
var starRed = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
var starBlack = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
var starPurple = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
var starIcon = "#fUI/FarmUI.img/objectStatus/star/whole#"
var sIcon = "#fUI/CashShop.img/CSEffect/today/0#"
var rewardIcon = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
var obtainIcon = "#fUI/UIWindow2.img/QuestIcon/4/0#"
var colorTag = "#fc0xFF6600CC#"
var blackTag = "#fc0xFF000000#"
var pinkTag = "#fc0xFFFF3366#"
var lightPinkTag = "#fc0xFFF781D8#"
var lineBreak = "\r\n";
var doubleLineBreak = "\r\n\r\n";
var endTag = "#fc0xFF000000#"

var status = -1;
var dialogue = "";
var normalItems = [
    [[2439988, 1], 0.1], // 0.1% Pitch Black Select
    [[2439961, 1], 0.1], // 0.1% Hon-Cain Select
    [[2439960, 1], 0.1], // 0.1% Hon-Cain Random
    [[2630782, 1], 4.6], // 4.8% Arcane Random

    [[0, 0], 0], // Empty space/Separator

    [[2049380, 1], 0.2], // 0.2% Star Force 25 Stars
    [[2430045, 1], 0.3], // 0.3% Enhancement 18
    [[2430044, 1], 1], // 1% Enhancement 17
    [[2430043, 1], 1], // 1% Enhancement 15
    [[2049377, 1], 1], // 1.0% Star Force 22 Stars
    [[2049376, 1], 1.5], // 1.5% Star Force 20 Stars
    [[2430042, 1], 7], // 7% Enhancement 13
    [[2430041, 1], 11], // 11% Enhancement 10

    [[0, 0], 0], // Empty space/Separator

    [[2439944, 1], 2], // 2% Magnet Pet Tier 4
    [[2439943, 1], 2], // 2% Magnet Pet Tier 3
    [[2439942, 1], 3], // 3% Magnet Pet Tier 2
    [[2630127, 1], 4], // 4% Magnet Pet Tier 1

    [[0, 0], 0], // Empty space/Separator

    [[4001715, 50], 11], // 11% 50x 100M Meso
    [[4031227, 50], 11], // 11% 50x Radiant Light
    [[4310308, 200], 11], // 11% 200x Neo Core
    [[4310266, 200], 12], // 12% 200x Promotion Coin
    [[4310237, 200], 16] // 16% 200x Hunt Coin
]

var specialItems = [
    //[[itemCode, count], probability]
    [[2439958, 1], 1], // Destruction Select
    [[2439988, 1], 1], // Pitch Black Select
    [[2430047, 1], 1], // Enhancement 20
    [[2430046, 1], 1], // Enhancement 19
    [[5068306, 1], 1], // Rainbow Berry
    [[2439960, 1], 5], // Hon-Cain Random
    [[2439961, 1], 5], // Hon-Cain Select
    [[2439962, 1], 5], // Hon-Cain Weapon
    [[1113305, 1], 5], // Glona's Heart
    [[1122439, 1], 5], // Dark Legacy
    [[1113070, 1], 5], // Scarlet Ring
    [[1012632, 1], 5], // Lucent Eye
    [[1022278, 1], 5], // Magic Eyepatch
    [[2430017, 20], 5], // 200k Points
    [[5062005, 20], 5], // 20x Amazing Cube
    [[5062503, 20], 5], // 20x White Cube
    [[4031227, 1000], 5], // 1000x Radiant Light
    [[4310308, 2000], 5], // 2000x Neo Core
    [[4310266, 2000], 5], // 2000x Promotion Coin
    [[2049380, 1], 5], // Star Force 25 Stars
    [[2049377, 1], 5], // Star Force 22 Stars
    [[2430044, 1], 5], // Enhancement 17
    [[4001715, 100], 10] // 10B Meso
]

var amount = 1;
var retryType = "";

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
        var chat = "#fs11#"
        chat += "สวัสดีจ้ะคุณ #h0# ยินดีต้อนรับสู่กงล้อเสี่ยงโชคแห่ง #b#e[Royal World]#k#n จ้ะ!" + lineBreak
        chat += "ลองใช้ตั๋วกงล้อธรรมดาหรือตั๋วกงล้อพิเศษเพื่อลุ้นรับไอเท็มสุดเจ๋งดูไหมจ๊ะ?" + lineBreak + lineBreak
        chat += "#L0#" + colorTag + "[กงล้อธรรมดา]" + endTag + " เริ่มสุ่ม#l?"
        chat += "#L1#" + colorTag + "[กงล้อพิเศษ]" + endTag + " เริ่มสุ่ม#l"
        chat += "\r\n"
        chat += "#L98#" + colorTag + "[กงล้อธรรมดา]" + endTag + " รายการไอเท็ม#l "
        chat += "#L99#" + colorTag + "[กงล้อพิเศษ]" + endTag + " รายการไอเท็ม#l"
        cm.sendSimple(chat);
    } else if (status == 1) {
        switch (selection) {
            case 0: // Normal Gacha
                if (!cm.haveItem(4036660, 1)) {
                    cm.sendOkS("#i4036660# #z4036660# ดูเหมือนเธอจะยังไม่มีตั๋วสำหรับสุ่มกงล้อธรรมดานะจ๊ะ", 700);
                    cm.dispose();
                    return;
                }
                if (cm.getInvSlots(1) < 3 || cm.getInvSlots(2) < 3 || cm.getInvSlots(3) < 3 || cm.getInvSlots(4) < 3 || cm.getInvSlots(5) < 3) {
                    cm.sendOkS("#fs11##fc0xFF6600CC#รบกวนเธอช่วยเว้นช่องว่างในกระเป๋าอย่างน้อย 3 ช่องในทุกแท็บด้วยนะจ๊ะ", 2);
                    cm.dispose();
                    return;
                }

                cm.gainItem(4036660, -1);
                var winMessage = "";
                for (var x = 0; x < amount; x++) {
                    var percentage = 0;
                    var chance = Math.random() * 100;
                    for (var i = 0; i < normalItems.length; i++) {
                        percentage += normalItems[i][1];
                        if (percentage > chance) {
                            winMessage = "#fs11#" + colorTag + "#i" + normalItems[i][0][0] + "##z" + normalItems[i][0][0] + "# #r" + normalItems[i][0][1] + " ชิ้น #bยินดีด้วยนะจ๊ะ เธอได้รับไอเท็มนี้จ้ะ#k#n" + lineBreak + lineBreak;
                            cm.gainItem(normalItems[i][0][0], normalItems[i][0][1]);
                            break;
                        }
                    }
                }
                winMessage += pinkTag + starIcon + " หวังว่าจะถูกใจเธอเข้านะจ๊ะ ต้องการจะสุ่มอีกรอบไหม?";
                retryType = "NORMAL";
                cm.sendYesNo(winMessage);
                break;

            case 1: // Special Gacha (MVP)
                if (!cm.haveItem(4036661, 1)) {
                    cm.sendOkS("#i4036661# #z4036661# ดูเหมือนเธอจะยังไม่มีตั๋วสำหรับสุ่มกงล้อพิเศษนะจ๊ะ", 700);
                    cm.dispose();
                    return;
                }
                if (cm.getInvSlots(1) < 1 || cm.getInvSlots(2) < 1 || cm.getInvSlots(3) < 1 || cm.getInvSlots(4) < 1 || cm.getInvSlots(5) < 1) {
                    cm.sendOkS("#fs11##fc0xFF6600CC#รบกวนเธอช่วยเว้นช่องว่างในกระเป๋าอย่างน้อย 1 ช่องในทุกแท็บด้วยนะจ๊ะ", 2);
                    cm.dispose();
                    return;
                }

                cm.gainItem(4036661, -1);
                var winMessage = "";
                for (var x = 0; x < amount; x++) {
                    var percentage = 0;
                    var chance = Math.random() * 100;
                    for (var i = 0; i < specialItems.length; i++) {
                        percentage += specialItems[i][1];
                        if (percentage > chance) {
                            winMessage = colorTag + "#fs11#" + colorTag + "#i" + specialItems[i][0][0] + "##z" + specialItems[i][0][0] + "# #r" + specialItems[i][0][1] + " ชิ้น #bยินดีด้วยนะจ๊ะ เธอได้รับไอเท็มนี้จ้ะ#k#n" + lineBreak + lineBreak;
                            cm.gainItem(specialItems[i][0][0], specialItems[i][0][1]);
                            break;
                        }
                    }
                }
                winMessage += pinkTag + starIcon + " หวังว่าจะถูกใจเธอเข้านะจ๊ะ ต้องการจะสุ่มอีกรอบไหม?";
                retryType = "MVP";
                cm.sendYesNo(winMessage);
                break;

            case 98: // Normal List
                var listMsg = starBlue + "#fs11##fc0xFF000000# รายการไอเท็มที่สุ่มได้จากกงล้อธรรมดาก็คือตามนี้เลยจ้ะ! #fs11#" + starBlue + lineBreak + lineBreak;
                for (var i = 0; i < normalItems.length; i++) {
                    if (normalItems[i][0][0] == 0) {
                        listMsg += lineBreak + lineBreak;
                    } else {
                        listMsg += colorTag + "#i" + normalItems[i][0][0] + "##z" + normalItems[i][0][0] + "##r " + normalItems[i][0][1] + " ชิ้น#k" + lineBreak;
                    }
                }
                cm.sendOk(listMsg);
                cm.dispose();
                break;

            case 99: // Special List
                var listMsg = starBlue + "#fs11##fc0xFF000000# รายการไอเท็มที่สุ่มได้จากกงล้อพิเศษก็คือตามนี้เลยจ้ะ! #fs11#" + starBlue + lineBreak + lineBreak;
                for (var i = 0; i < specialItems.length; i++) {
                    listMsg += colorTag + "#i" + specialItems[i][0][0] + "##z" + specialItems[i][0][0] + "##r " + specialItems[i][0][1] + " ชิ้น #b" + specialItems[i][1] + "%#k" + lineBreak;
                }
                cm.sendOk(listMsg);
                cm.dispose();
                break;
        }
    } else if (status == 2) {
        if (retryType == "NORMAL") {
            status = 0;
            action(1, 0, 0);
        } else if (retryType == "MVP") {
            status = 0;
            action(1, 0, 1);
        } else {
            cm.dispose();
        }
    }
}




