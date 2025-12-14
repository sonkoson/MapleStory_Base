var status = -1;

purple = "#fMap/MapHelper.img/weather/starPlanet/7#";
blue = "#fMap/MapHelper.img/weather/starPlanet/8#";
starBlue = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
starYellow = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
starWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
starBrown = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
starRed = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
starBlack = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
starPurple = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
star = "#fUI/FarmUI.img/objectStatus/star/whole#"
S = "#fUI/CashShop.img/CSEffect/today/0#"
reward = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
obtain = "#fUI/UIWindow2.img/QuestIcon/4/0#"
color = "#fc0xFF6600CC#"
black = "#fc0xFF000000#"
enter = "\r\n"
enter2 = "\r\n\r\n"
size = "#fs11#"

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
        chat += "#fc0xFF000000#นี่คือ #eระบบ Season Pass#n ของ Royal Maple\r\n";
        chat += "#fc0xFF000000#ระดับ Season Pass ของ #fc0xFFFF3366##h ##fc0xFF000000# ปัจจุบัน : #fc0xFFFF3366#" + cm.getPlayer().getKeyValue(0, "Tear_Upgrade") + "#fc0xFF000000##b\r\n"
        if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade") == 10) {
            chat += "#r#L3#ระดับปัจจุบันของคุณสูงสุดแล้ว ไม่สามารถเลื่อนระดับได้อีก#k\r\n";
        } else {
            chat += "#L1#" + color + purple + " เลื่อนระดับ Season Pass ขั้นถัดไป\r\n";
            chat += "#L2#" + color + blue + " เกี่ยวกับ Season Pass\r\n";
        }
        cm.sendSimple(chat);

    } else if (status == 1) {
        if (selection == 1) {
            if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade") == 0) { // Step 1
                var chat = "#fs11#" + color + "ในการเลื่อนระดับเป็นขั้นที่ " + (cm.getPlayer().getKeyValue(0, "Tear_Upgrade") + 1) + " ต้องใช้วัตถุดิบต่อไปนี้\r\n\r\n";
                chat += "#i4001753# #b#z4001753##k #r10 ชิ้น#k\r\n\r\n";
                chat += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――\r\n#fc0xFFFF3300#";
                chat += "#b<รางวัล Season Pass ขั้นที่ 1>#l\r\n";
                chat += "#i4310237##z4310237# #r500 ชิ้น\r\n\r\n";
                chat += "#L0##r#eคุณต้องการเลื่อนระดับเป็นขั้นที่ 1 หรือไม่?#k#n";
                cm.sendSimple(chat);
            }

            if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade") == 1) { // Step 2
                var chat = "#fs11#" + color + "ในการเลื่อนระดับเป็นขั้นที่ " + (cm.getPlayer().getKeyValue(0, "Tear_Upgrade") + 1) + " ต้องใช้วัตถุดิบต่อไปนี้\r\n\r\n";
                chat += "#i4001753# #b#z4001753##k #r30 ชิ้น#k\r\n";
                chat += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――\r\n#fc0xFFFF3300#";
                chat += "#b<รางวัล Season Pass ขั้นที่ 2>#l\r\n";
                chat += "#i4009005##z4009005# #r50 ชิ้น\r\n\r\n";
                chat += "#L1##r#eคุณต้องการเลื่อนระดับเป็นขั้นที่ 2 หรือไม่?#k#n";
                cm.sendSimple(chat);
            }

            if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade") == 2) { // Step 3
                var chat = "#fs11#" + color + "ในการเลื่อนระดับเป็นขั้นที่ " + (cm.getPlayer().getKeyValue(0, "Tear_Upgrade") + 1) + " ต้องใช้วัตถุดิบต่อไปนี้\r\n\r\n";
                chat += "#i4001753# #b#z4001753##k #r50 ชิ้น#k\r\n";
                chat += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――\r\n#fc0xFFFF3300#";
                chat += "#b<รางวัล Season Pass ขั้นที่ 3>#l\r\n";
                chat += "#i5060048##z5060048# #r5 ชิ้น\r\n\r\n";
                chat += "#L2##r#eคุณต้องการเลื่อนระดับเป็นขั้นที่ 3 หรือไม่?#k#n";
                cm.sendSimple(chat);
            }

            if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade") == 3) { // Step 4
                var chat = "#fs11#" + color + "ในการเลื่อนระดับเป็นขั้นที่ " + (cm.getPlayer().getKeyValue(0, "Tear_Upgrade") + 1) + " ต้องใช้วัตถุดิบต่อไปนี้\r\n\r\n";
                chat += "#i4001753# #b#z4001753##k #r70 ชิ้น#k\r\n";
                chat += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――\r\n#fc0xFFFF3300#";
                chat += "#b<รางวัล Season Pass ขั้นที่ 4>#l\r\n";
                chat += "#i5068305##z5068305# #r2 ชิ้น\r\n\r\n";
                chat += "#L3##r#eคุณต้องการเลื่อนระดับเป็นขั้นที่ 4 หรือไม่?#k#n";
                cm.sendSimple(chat);
            }

            if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade") == 4) { // Step 5
                var chat = "#fs11#" + color + "ในการเลื่อนระดับเป็นขั้นที่ " + (cm.getPlayer().getKeyValue(0, "Tear_Upgrade") + 1) + " ต้องใช้วัตถุดิบต่อไปนี้\r\n\r\n";
                chat += "#i4001753# #b#z4001753##k #r90 ชิ้น#k\r\n";
                chat += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――\r\n#fc0xFFFF3300#";
                chat += "#b<รางวัล Season Pass ขั้นที่ 5>#l\r\n";
                chat += "#i2049376##z2049376# #r1 ชิ้น\r\n\r\n";
                chat += "#L4##r#eคุณต้องการเลื่อนระดับเป็นขั้นที่ 5 หรือไม่?#k#n";
                cm.sendSimple(chat);
            }

            if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade") == 5) { // Step 6
                var chat = "#fs11#" + color + "ในการเลื่อนระดับเป็นขั้นที่ " + (cm.getPlayer().getKeyValue(0, "Tear_Upgrade") + 1) + " ต้องใช้วัตถุดิบต่อไปนี้\r\n\r\n";
                chat += "#i4001753# #b#z4001753##k #r110 ชิ้น#k\r\n";
                chat += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――\r\n#fc0xFFFF3300#";
                chat += "#b<รางวัล Season Pass ขั้นที่ 6>#l\r\n";
                chat += "#i4310308##z4310308# #r200 ชิ้น\r\n\r\n";
                chat += "#L5##r#eคุณต้องการเลื่อนระดับเป็นขั้นที่ 6 หรือไม่?#k#n";
                cm.sendSimple(chat);
            }

            if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade") == 6) { // Step 7
                var chat = "#fs11#" + color + "ในการเลื่อนระดับเป็นขั้นที่ " + (cm.getPlayer().getKeyValue(0, "Tear_Upgrade") + 1) + " ต้องใช้วัตถุดิบต่อไปนี้\r\n\r\n";
                chat += "#i4001753# #b#z4001753##k #r130 ชิ้น#k\r\n";
                chat += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――\r\n#fc0xFFFF3300#";
                chat += "#b<รางวัล Season Pass ขั้นที่ 7>#l\r\n";
                chat += "#i4001715##z4001715# #r100 ชิ้น\r\n\r\n";
                chat += "#L6##r#eคุณต้องการเลื่อนระดับเป็นขั้นที่ 7 หรือไม่?#k#n";
                cm.sendSimple(chat);
            }

            if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade") == 7) { // Step 8
                var chat = "#fs11#" + color + "ในการเลื่อนระดับเป็นขั้นที่ " + (cm.getPlayer().getKeyValue(0, "Tear_Upgrade") + 1) + " ต้องใช้วัตถุดิบต่อไปนี้\r\n\r\n";
                chat += "#i4001753# #b#z4001753##k #r150 ชิ้น#k\r\n";
                chat += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――\r\n#fc0xFFFF3300#";
                chat += "#b<รางวัล Season Pass ขั้นที่ 8>#l\r\n";
                chat += "#i2430043##z2430043# #r1 ชิ้น\r\n\r\n";
                chat += "#L7##r#eคุณต้องการเลื่อนระดับเป็นขั้นที่ 8 หรือไม่?#k#n";
                cm.sendSimple(chat);
            }

            if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade") == 8) { // Step 9
                var chat = "#fs11#" + color + "ในการเลื่อนระดับเป็นขั้นที่ " + (cm.getPlayer().getKeyValue(0, "Tear_Upgrade") + 1) + " ต้องใช้วัตถุดิบต่อไปนี้\r\n\r\n";
                chat += "#i4001753# #b#z4001753##k #r170 ชิ้น#k\r\n";
                chat += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――\r\n#fc0xFFFF3300#";
                chat += "#b<รางวัล Season Pass ขั้นที่ 9>#l\r\n";
                chat += "#i4310266##z4310266# #r700 ชิ้น\r\n\r\n";
                chat += "#L8##r#eคุณต้องการเลื่อนระดับเป็นขั้นที่ 9 หรือไม่?#k#n";
                cm.sendSimple(chat);
            }
            if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade") == 9) { // Step 10
                var chat = "#fs11#" + color + "ในการเลื่อนระดับเป็นขั้นที่ " + (cm.getPlayer().getKeyValue(0, "Tear_Upgrade") + 1) + " ต้องใช้วัตถุดิบต่อไปนี้\r\n\r\n";
                chat += "#i4001753# #b#z4001753##k #r200 ชิ้น#k\r\n";
                chat += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――\r\n#fc0xFFFF3300#";
                chat += "#b<รางวัล Season Pass ขั้นที่ 10>#l\r\n";
                chat += "#i4310266##z4310266# #r1000 ชิ้น\r\n\r\n";
                chat += "#L9##r#eคุณต้องการเลื่อนระดับเป็นขั้นที่ 10 หรือไม่?#k#n";
                cm.sendSimple(chat);
            }
        } else if (selection == 2) {
            var chat = "#fs11##e" + color + "< คู่มือ Season Pass >\r\n\r\n";
            chat += "คุณสามารถรับไอเท็ม#k#n #i4001753# #b#z4001753##k" + black + " ได้ผ่านเควสรายวัน#k\r\n\r\n";
            chat += black + "คุณสามารถใช้ตั๋วที่ได้รับเพื่อเพิ่มระดับ Season Pass ได้\r\nการเลื่อนระดับจะไม่ใช้ตั๋ว\r\n\r\n";
            chat += black + "คุณจะได้รับรางวัลทุกครั้งที่เลื่อนระดับ\r\nPremium Pass จะได้รับรางวัลที่ดีกว่า\r\n\r\n";

            chat += "#b<รางวัล Season Pass ขั้นที่ 1>#l\r\n";
            chat += "#i4310237##z4310237# #r500 ชิ้น\r\n\r\n";
            chat += "#b<รางวัล Season Pass ขั้นที่ 2>#l\r\n";
            chat += "#i4009005##z4009005# #r50 ชิ้น\r\n\r\n";
            chat += "#b<รางวัล Season Pass ขั้นที่ 3>#l\r\n";
            chat += "#i5060048##z5060048# #r5 ชิ้น\r\n\r\n";
            chat += "#b<รางวัล Season Pass ขั้นที่ 4>#l\r\n";
            chat += "#i5068305##z5068305# #r2 ชิ้น\r\n\r\n";
            chat += "#b<รางวัล Season Pass ขั้นที่ 5>#l\r\n";
            chat += "#i2049376##z2049376# #r1 ชิ้น\r\n\r\n";
            chat += "#b<รางวัล Season Pass ขั้นที่ 6>#l\r\n";
            chat += "#i4310308##z4310308# #r200 ชิ้น\r\n\r\n";
            chat += "#b<รางวัล Season Pass ขั้นที่ 7>#l\r\n";
            chat += "#i4001715##z4001715# #r100 ชิ้น\r\n\r\n";
            chat += "#b<รางวัล Season Pass ขั้นที่ 8>#l\r\n";
            chat += "#i2430043##z2430043# #r1 ชิ้น\r\n\r\n";
            chat += "#b<รางวัล Season Pass ขั้นที่ 9>#l\r\n";
            chat += "#i4310266##z4310266# #r700 ชิ้น\r\n\r\n";
            chat += "#b<รางวัล Season Pass ขั้นที่ 10>#l\r\n";
            chat += "#i4310266##z4310266# #r1000 ชิ้น\r\n\r\n";
            cm.sendOk(chat);
            cm.dispose();
            return;
        } else if (selection == 3) {
            cm.dispose();
            return;
        }

    } else if (status == 2) {
        if (selection == 0) {
            if (cm.haveItem(4001753, 10)) {
                cm.gainItem(4310237, 500);
                cm.getPlayer().setKeyValue(0, "Tear_Upgrade", "1");
                cm.sendOk("ขอแสดงความยินดี คุณเลื่อนระดับเป็นขั้นที่ 1 แล้ว");
                cm.dispose();
            } else {
                cm.sendOk("วัตถุดิบไม่เพียงพอ");
                cm.dispose();
            }
        }

        if (selection == 1) {
            if (cm.haveItem(4001753, 30)) {
                cm.gainItem(4009005, 50);
                cm.getPlayer().setKeyValue(0, "Tear_Upgrade", "2");
                cm.sendOk("ขอแสดงความยินดี คุณเลื่อนระดับเป็นขั้นที่ 2 แล้ว");
                cm.dispose();
            } else {
                cm.sendOk("วัตถุดิบไม่เพียงพอ");
                cm.dispose();
            }
        }

        if (selection == 2) {
            if (cm.haveItem(4001753, 50)) {
                cm.gainItem(5060048, 5);
                cm.getPlayer().setKeyValue(0, "Tear_Upgrade", "3");
                //Packages.handling.world.World.Broadcast.broadcastMessage(Packages.network.models.CField.getGameMessage(8, cm.getPlayer().getName() + " has been promoted to Gold Tier."));
                cm.sendOk("ขอแสดงความยินดี คุณเลื่อนระดับเป็นขั้นที่ 3 แล้ว");
                cm.dispose();
            } else {
                cm.sendOk("วัตถุดิบไม่เพียงพอ");
                cm.dispose();
            }
        }

        if (selection == 3) {
            if (cm.haveItem(4001753, 70)) {
                cm.gainItem(5068305, 2);
                cm.getPlayer().setKeyValue(0, "Tear_Upgrade", "4");
                //Packages.handling.world.World.Broadcast.broadcastMessage(Packages.network.models.CField.getGameMessage(8, cm.getPlayer().getName() + " has been promoted to Platinum Tier."));
                cm.sendOk("ขอแสดงความยินดี คุณเลื่อนระดับเป็นขั้นที่ 4 แล้ว");
                cm.dispose();
            } else {
                cm.sendOk("วัตถุดิบไม่เพียงพอ");
                cm.dispose();
            }
        }

        if (selection == 4) {
            if (cm.haveItem(4001753, 90)) {
                cm.gainItem(2049376, 1);
                cm.getPlayer().setKeyValue(0, "Tear_Upgrade", "5");
                //Packages.handling.world.World.Broadcast.broadcastMessage(Packages.network.models.CField.getGameMessage(8, cm.getPlayer().getName() + " has been promoted to Diamond Tier."));
                cm.sendOk("ขอแสดงความยินดี คุณเลื่อนระดับเป็นขั้นที่ 5 แล้ว");
                cm.dispose();
            } else {
                cm.sendOk("วัตถุดิบไม่เพียงพอ");
                cm.dispose();
            }
        }

        if (selection == 5) {
            if (cm.haveItem(4001753, 110)) {
                cm.gainItem(4310308, 200);
                cm.getPlayer().setKeyValue(0, "Tear_Upgrade", "6");
                //Packages.handling.world.World.Broadcast.broadcastMessage(Packages.network.models.CField.getGameMessage(8, cm.getPlayer().getName() + " has been promoted to Master Tier."));
                cm.sendOk("ขอแสดงความยินดี คุณเลื่อนระดับเป็นขั้นที่ 6 แล้ว");
                cm.dispose();
            } else {
                cm.sendOk("วัตถุดิบไม่เพียงพอ");
                cm.dispose();
            }
        }

        if (selection == 6) {
            if (cm.haveItem(4001753, 130)) {
                cm.gainItem(4001715, 100);
                cm.getPlayer().setKeyValue(0, "Tear_Upgrade", "7");
                //Packages.handling.world.World.Broadcast.broadcastMessage(Packages.network.models.CField.getGameMessage(8, cm.getPlayer().getName() + " has been promoted to Grandmaster Tier."));
                cm.sendOk("ขอแสดงความยินดี คุณเลื่อนระดับเป็นขั้นที่ 7 แล้ว");
                cm.dispose();
            } else {
                cm.sendOk("วัตถุดิบไม่เพียงพอ");
                cm.dispose();
            }
        }

        if (selection == 7) {
            if (cm.haveItem(4001753, 150)) {
                cm.gainItem(2430043, 1);
                cm.getPlayer().setKeyValue(0, "Tear_Upgrade", "8");
                //Packages.handling.world.World.Broadcast.broadcastMessage(Packages.network.models.CField.getGameMessage(8, cm.getPlayer().getName() + " has been promoted to Legend Tier."));
                cm.sendOk("ขอแสดงความยินดี คุณเลื่อนระดับเป็นขั้นที่ 8 แล้ว");
                cm.dispose();
            } else {
                cm.sendOk("วัตถุดิบไม่เพียงพอ");
                cm.dispose();
            }
        }
        if (selection == 8) {
            if (cm.haveItem(4001753, 170)) {
                cm.gainItem(4310266, 700);
                cm.getPlayer().setKeyValue(0, "Tear_Upgrade", "9");
                //Packages.handling.world.World.Broadcast.broadcastMessage(Packages.network.models.CField.getGameMessage(8, cm.getPlayer().getName() + " has been promoted to Legend Tier."));
                cm.sendOk("ขอแสดงความยินดี คุณเลื่อนระดับเป็นขั้นที่ 9 แล้ว");
                cm.dispose();
            } else {
                cm.sendOk("วัตถุดิบไม่เพียงพอ");
                cm.dispose();
            }
        }

        if (selection == 9) {
            if (cm.haveItem(4001753, 200)) {
                cm.gainItem(4310266, 1000);
                cm.getPlayer().setKeyValue(0, "Tear_Upgrade", "10");
                //Packages.handling.world.World.Broadcast.broadcastMessage(Packages.network.models.CField.getGameMessage(8, cm.getPlayer().getName() + " has been promoted to Legend Tier."));
                cm.sendOk("ขอแสดงความยินดี คุณเลื่อนระดับเป็นขั้นที่ 10 แล้ว");
                cm.dispose();
            } else {
                cm.sendOk("วัตถุดิบไม่เพียงพอ");
                cm.dispose();
            }
        }

        if (selection == 11) {
            if (cm.haveItem(4001753, 200)) {
                cm.gainItem(4310266, 1000);
                cm.getPlayer().setKeyValue(0, "Tear_Upgrade", "10");
                //Packages.handling.world.World.Broadcast.broadcastMessage(Packages.network.models.CField.getGameMessage(8, cm.getPlayer().getName() + " has been promoted to Challenger Tier."));
                cm.sendOk("ขอแสดงความยินดี คุณเลื่อนระดับเป็นขั้นที่ 10 แล้ว");
                cm.dispose();
            } else {
                cm.sendOk("วัตถุดิบไม่เพียงพอ");
                cm.dispose();
            }
        }
    }
}

function NullKeyValue() {
    if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade") == -1) {
        cm.getPlayer().setKeyValue(0, "Tear_Upgrade", "0");
    }
}
