var status = -1;

var purple = "#fMap/MapHelper.img/weather/starPlanet/7#";
var blue = "#fMap/MapHelper.img/weather/starPlanet/8#";
var starBlue = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
var starYellow = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
var starWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
var starBrown = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
var starRed = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
var starBlack = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
var starPurple = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
var star = "#fUI/FarmUI.img/objectStatus/star/whole#"
var S = "#fUI/CashShop.img/CSEffect/today/0#"
var rewardIcon = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
var obtainIcon = "#fUI/UIWindow2.img/QuestIcon/4/0#"
var color = "#fc0xFF6600CC#"
var black = "#fc0xFF000000#"
var enter = "\r\n"
var enter2 = "\r\n\r\n"
var fs = "#fs11#"

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }
    if (status == 0) {
        NullKeyValue();
        var chat = "#fs11#";
        chat += "#fc0xFF000000#นี่คือระบบ #eSeason Pass#n ของ Royal Maple จ้ะ\r\n";
        chat += "#fc0xFF000000#ระดับ Season Pass ปัจจุบันของ #fc0xFFFF3366##h ##fc0xFF000000# : #fc0xFFFF3366#" + cm.getPlayer().getKeyValue(0, "Tear_Upgrade") + "#fc0xFF000000##b\r\n"
        if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade") == 10) {
            chat += "#r#L3#เนื่องจากเลื่อนขั้นถึงระดับสูงสุดแล้ว จึงไม่สามารถเลื่อนขั้นได้อีกจ้ะ#k\r\n";
        } else {
            chat += "#L1#" + color + purple + " เลื่อนระดับ Season Pass ขั้นถัดไป\r\n";
            chat += "#L2#" + color + blue + " เกี่ยวกับ Season Pass\r\n";
        }
        cm.sendSimple(chat);

    } else if (status == 1) {
        if (selection == 1) {
            var currentTeir = cm.getPlayer().getKeyValue(0, "Tear_Upgrade");
            var nextTeir = currentTeir + 1;
            var requiredQuantity = 0;
            var rewardText = "";

            // Define requirements and rewards based on current tier
            // Using switch or if-else based on original logic

            if (currentTeir == 0) { // To Tier 1
                requiredQuantity = 10;
                rewardText = "#i4310237##z4310237# #r500 ชิ้น";
            } else if (currentTeir == 1) { // To Tier 2
                requiredQuantity = 30;
                rewardText = "#i4009005##z4009005# #r50 ชิ้น";
            } else if (currentTeir == 2) { // To Tier 3
                requiredQuantity = 50;
                rewardText = "#i5060048##z5060048# #r5 ชิ้น";
            } else if (currentTeir == 3) { // To Tier 4
                requiredQuantity = 70;
                rewardText = "#i5068305##z5068305# #r2 ชิ้น";
            } else if (currentTeir == 4) { // To Tier 5
                requiredQuantity = 90;
                rewardText = "#i2049376##z2049376# #r1 ชิ้น";
            } else if (currentTeir == 5) { // To Tier 6
                requiredQuantity = 110;
                rewardText = "#i4310308##z4310308# #r200 ชิ้น";
            } else if (currentTeir == 6) { // To Tier 7
                requiredQuantity = 130;
                rewardText = "#i4001715##z4001715# #r100 ชิ้น";
            } else if (currentTeir == 7) { // To Tier 8
                requiredQuantity = 150;
                rewardText = "#i2430043##z2430043# #r1 ชิ้น";
            } else if (currentTeir == 8) { // To Tier 9
                requiredQuantity = 170;
                rewardText = "#i4310266##z4310266# #r700 ชิ้น";
            } else if (currentTeir == 9) { // To Tier 10
                requiredQuantity = 200;
                rewardText = "#i4310266##z4310266# #r1000 ชิ้น";
            }

            if (requiredQuantity > 0) {
                var chat = "#fs11#" + color + "ในการเลื่อนขั้นเป็นระดับ " + nextTeir + " จำเป็นต้องใช้วัตถุดิบดังต่อไปนี้จ้ะ\r\n\r\n";
                chat += "#i4001753# #b#z4001753##k #r" + requiredQuantity + " ชิ้น#k\r\n\r\n"; // Assuming Item ID 4001753 is the ticket
                chat += "#Cgray##fs11#????????????????????????????????????????\r\n#fc0xFFFF3300#";
                chat += "#b<รางวัล Season Pass ระดับ " + nextTeir + ">#l\r\n";
                chat += rewardText + "\r\n\r\n";
                chat += "#L" + currentTeir + "##r#eต้องการเลื่อนขั้นเป็นระดับ " + nextTeir + " หรือไม่?#k#n";
                cm.sendSimple(chat);
            }

        } else if (selection == 2) {
            var chat = "#fs11##e" + color + "< คำแนะนำ Season Pass >\r\n\r\n";
            chat += "สามารถรับไอเทม#k#n\r\n\r\n#i4001753# #b#z4001753##k" + black + " ได้ผ่านภารกิจรายวันจ้ะ#k\r\n\r\n";
            chat += black + "สามารถใช้ตั๋วที่ได้มาเพื่ออัพเกรดระดับ Season Pass ได้\r\nการเลื่อนระดับจะไม่ใช้ตั๋วจ้ะ\r\n\r\n";
            chat += black + "เมื่อเลื่อนระดับจะได้รับของรางวัล\r\nPremium Pass จะได้รับของรางวัลที่ดีกว่าจ้ะ\r\n\r\n";

            chat += "#b<รางวัล Season Pass ระดับ 1>#l\r\n";
            chat += "#i4310237##z4310237# #r500 ชิ้น\r\n\r\n";
            chat += "#b<รางวัล Season Pass ระดับ 2>#l\r\n";
            chat += "#i4009005##z4009005# #r50 ชิ้น\r\n\r\n";
            chat += "#b<รางวัล Season Pass ระดับ 3>#l\r\n";
            chat += "#i5060048##z5060048# #r5 ชิ้น\r\n\r\n";
            chat += "#b<รางวัล Season Pass ระดับ 4>#l\r\n";
            chat += "#i5068305##z5068305# #r2 ชิ้น\r\n\r\n";
            chat += "#b<รางวัล Season Pass ระดับ 5>#l\r\n";
            chat += "#i2049376##z2049376# #r1 ชิ้น\r\n\r\n";
            chat += "#b<รางวัล Season Pass ระดับ 6>#l\r\n";
            chat += "#i4310308##z4310308# #r200 ชิ้น\r\n\r\n";
            chat += "#b<รางวัล Season Pass ระดับ 7>#l\r\n";
            chat += "#i4001715##z4001715# #r100 ชิ้น\r\n\r\n";
            chat += "#b<รางวัล Season Pass ระดับ 8>#l\r\n";
            chat += "#i2430043##z2430043# #r1 ชิ้น\r\n\r\n";
            chat += "#b<รางวัล Season Pass ระดับ 9>#l\r\n";
            chat += "#i4310266##z4310266# #r700 ชิ้น\r\n\r\n";
            chat += "#b<รางวัล Season Pass ระดับ 10>#l\r\n";
            chat += "#i4310266##z4310266# #r1000 ชิ้น\r\n\r\n";
            cm.sendOk(chat);
            cm.dispose();
            return;
        } else if (selection == 3) {
            cm.dispose();
            return;
        }

    } else if (status == 2) {
        var currentTeir = selection; // Selection passed from status 1 corresponds to current tier
        var requiredQuantity = 0;
        var rewardId = 0;
        var rewardQty = 0;
        var nextTeir = currentTeir + 1;

        if (currentTeir == 0) { requiredQuantity = 10; rewardId = 4310237; rewardQty = 500; }
        else if (currentTeir == 1) { requiredQuantity = 30; rewardId = 4009005; rewardQty = 50; }
        else if (currentTeir == 2) { requiredQuantity = 50; rewardId = 5060048; rewardQty = 5; }
        else if (currentTeir == 3) { requiredQuantity = 70; rewardId = 5068305; rewardQty = 2; }
        else if (currentTeir == 4) { requiredQuantity = 90; rewardId = 2049376; rewardQty = 1; }
        else if (currentTeir == 5) { requiredQuantity = 110; rewardId = 4310308; rewardQty = 200; }
        else if (currentTeir == 6) { requiredQuantity = 130; rewardId = 4001715; rewardQty = 100; }
        else if (currentTeir == 7) { requiredQuantity = 150; rewardId = 2430043; rewardQty = 1; }
        else if (currentTeir == 8) { requiredQuantity = 170; rewardId = 4310266; rewardQty = 700; }
        else if (currentTeir == 9) { requiredQuantity = 200; rewardId = 4310266; rewardQty = 1000; }

        // Safety check for selection 11 logic in legacy code (Challenger tier), mapped to 9 logic?
        // Legacy had if selection == 11. But menu only has logic for currentTeir 0-9.
        // I will stick to 0-9.

        if (cm.haveItem(4001753, requiredQuantity)) {
            cm.gainItem(rewardId, rewardQty);
            cm.getPlayer().setKeyValue(0, "Tear_Upgrade", nextTeir.toString());
            cm.sendOk("เลื่อนขั้นเป็นระดับ " + nextTeir + " เรียบร้อยแล้ว ยินดีด้วยจ้ะ!");
            cm.dispose();
        } else {
            cm.sendOk("วัตถุดิบไม่พอจ้ะ");
            cm.dispose();
        }
    }
}

function NullKeyValue() {
    if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade") == -1) {
        cm.getPlayer().setKeyValue(0, "Tear_Upgrade", "0");
    }
}



