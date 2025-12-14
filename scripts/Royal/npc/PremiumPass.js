var status = -1;

purple = "#fMap/MapHelper.img/weather/starPlanet/7#";
blue = "#fMap/MapHelper.img/weather/starPlanet/8#";
blueStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
yellowStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
whiteStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
brownStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
redStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
blackStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
purpleStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
star = "#fUI/FarmUI.img/objectStatus/star/whole#"
S = "#fUI/CashShop.img/CSEffect/today/0#"
reward = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
gain = "#fUI/UIWindow2.img/QuestIcon/4/0#"
color = "#fc0xFF6600CC#"
black = "#fc0xFF000000#"
pink = "#fc0xFFFF3366#"
lightPink = "#fc0xFFF781D8#"
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
        chat += "#fc0xFF000000#นี่คือระบบ #ePremium Season Pass ของ Royal Maple#n\r\n";
        chat += "#fc0xFF000000#ระดับ [Premium] Season Pass ของ #fc0xFFFF3366##h ##fc0xFF000000# ปัจจุบัน : #fc0xFFFF3366#" + cm.getPlayer().getKeyValue(0, "Tear_Upgrade1") + "#fc0xFF000000##b\r\n"
        if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade1") == 10) {
            chat += "#r#L3#ระดับ Pass ปัจจุบันคือระดับสูงสุดแล้ว#k\r\n";
        } else {
            chat += "#L1#" + color + purple + " อัปเกรดเป็นระดับ Premium Season Pass ถัดไป\r\n";
            chat += "#L2#" + color + blue + " เกี่ยวกับ Premium Season Pass\r\n";
        }
        cm.sendSimple(chat);

    } else if (status == 1) {
        if (selection == 1) {
            if (!cm.haveItem(4001760, 1)) {
                cm.sendOk("#fs11#" + color + "#i4001760# คุณไม่มี #z4001760##b");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade1") == 0) { // Grade 1
                var chat = "#fs11#" + color + (cm.getPlayer().getKeyValue(0, "Tear_Upgrade1") + 1) + " เพื่ออัปเกรดเป็นระดับ 1 ต้องใช้วัสดุดังต่อไปนี้\r\n\r\n";
                chat += "#i4001753# #b#z4001753##k #r10 ชิ้น#k\r\n\r\n";
                chat += "----------------------------------\r\n";
                chat += "#b<ของรางวัล Premium Season Pass ระดับ 1>#l\r\n";
                chat += "#i4310237##z4310237# #r2000 ชิ้น\r\n\r\n";
                chat += "#L0##r#eคุณต้องการอัปเกรดเป็นระดับ 1 หรือไม่ ?#k#n";
                cm.sendSimple(chat);
            }

            if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade1") == 1) { // Grade 2
                var chat = "#fs11#" + color + (cm.getPlayer().getKeyValue(0, "Tear_Upgrade1") + 1) + " เพื่ออัปเกรดเป็นระดับ 2 ต้องใช้วัสดุดังต่อไปนี้\r\n\r\n";
                chat += "#i4001753# #b#z4001753##k #r30 ชิ้น#k\r\n";
                chat += "----------------------------------\r\n";
                chat += "#b<ของรางวัล Premium Season Pass ระดับ 2>#l\r\n";
                chat += "#i4009005##z4009005# #r500 ชิ้น\r\n\r\n";
                chat += "#L1##r#eคุณต้องการอัปเกรดเป็นระดับ 2 หรือไม่ ?#k#n";
                cm.sendSimple(chat);
            }

            if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade1") == 2) { // Grade 3
                var chat = "#fs11#" + color + (cm.getPlayer().getKeyValue(0, "Tear_Upgrade1") + 1) + " เพื่ออัปเกรดเป็นระดับ 3 ต้องใช้วัสดุดังต่อไปนี้\r\n\r\n";
                chat += "#i4001753# #b#z4001753##k #r50 ชิ้น#k\r\n";
                chat += "----------------------------------\r\n";
                chat += "#b<ของรางวัล Premium Season Pass ระดับ 3>#l\r\n";
                chat += "#i5060048##z5060048# #r15 ชิ้น\r\n\r\n";
                chat += "#L2##r#eคุณต้องการอัปเกรดเป็นระดับ 3 หรือไม่ ?#k#n";
                cm.sendSimple(chat);
            }

            if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade1") == 3) { // Grade 4
                var chat = "#fs11#" + color + (cm.getPlayer().getKeyValue(0, "Tear_Upgrade1") + 1) + " เพื่ออัปเกรดเป็นระดับ 4 ต้องใช้วัสดุดังต่อไปนี้\r\n\r\n";
                chat += "#i4001753# #b#z4001753##k #r70 ชิ้น#k\r\n";
                chat += "----------------------------------\r\n";
                chat += "#b<ของรางวัล Premium Season Pass ระดับ 4>#l\r\n";
                chat += "#i5068305##z5068305# #r5 ชิ้น\r\n\r\n";
                chat += "#L3##r#eคุณต้องการอัปเกรดเป็นระดับ 4 หรือไม่ ?#k#n";
                cm.sendSimple(chat);
            }

            if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade1") == 4) { // Grade 5
                var chat = "#fs11#" + color + (cm.getPlayer().getKeyValue(0, "Tear_Upgrade1") + 1) + " เพื่ออัปเกรดเป็นระดับ 5 ต้องใช้วัสดุดังต่อไปนี้\r\n\r\n";
                chat += "#i4001753# #b#z4001753##k #r90 ชิ้น#k\r\n";
                chat += "----------------------------------\r\n";
                chat += "#b<ของรางวัล Premium Season Pass ระดับ 5>#l\r\n";
                chat += "#i2049376##z2049376# #r5 ชิ้น\r\n\r\n";
                chat += "#L4##r#eคุณต้องการอัปเกรดเป็นระดับ 5 หรือไม่ ?#k#n";
                cm.sendSimple(chat);
            }

            if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade1") == 5) { // Grade 6
                var chat = "#fs11#" + color + (cm.getPlayer().getKeyValue(0, "Tear_Upgrade1") + 1) + " เพื่ออัปเกรดเป็นระดับ 6 ต้องใช้วัสดุดังต่อไปนี้\r\n\r\n";
                chat += "#i4001753# #b#z4001753##k #r110 ชิ้น#k\r\n";
                chat += "----------------------------------\r\n";
                chat += "#b<ของรางวัล Premium Season Pass ระดับ 6>#l\r\n";
                chat += "#i4310308##z4310308# #r500 ชิ้น\r\n\r\n";
                chat += "#L5##r#eคุณต้องการอัปเกรดเป็นระดับ 6 หรือไม่ ?#k#n";
                cm.sendSimple(chat);
            }

            if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade1") == 6) { // Grade 7
                var chat = "#fs11#" + color + (cm.getPlayer().getKeyValue(0, "Tear_Upgrade1") + 1) + " เพื่ออัปเกรดเป็นระดับ 7 ต้องใช้วัสดุดังต่อไปนี้\r\n\r\n";
                chat += "#i4001753# #b#z4001753##k #r130 ชิ้น#k\r\n";
                chat += "----------------------------------\r\n";
                chat += "#b<ของรางวัล Premium Season Pass ระดับ 7>#l\r\n";
                chat += "#i4001715##z4001715# #r500 ชิ้น\r\n\r\n";
                chat += "#L6##r#eคุณต้องการอัปเกรดเป็นระดับ 7 หรือไม่ ?#k#n";
                cm.sendSimple(chat);
            }

            if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade1") == 7) { // Grade 8
                var chat = "#fs11#" + color + (cm.getPlayer().getKeyValue(0, "Tear_Upgrade1") + 1) + " เพื่ออัปเกรดเป็นระดับ 8 ต้องใช้วัสดุดังต่อไปนี้\r\n\r\n";
                chat += "#i4001753# #b#z4001753##k #r150 ชิ้น#k\r\n";
                chat += "----------------------------------\r\n";
                chat += "#b<ของรางวัล Premium Season Pass ระดับ 8>#l\r\n";
                chat += "#i2430044##z2430044# #r1 ชิ้น\r\n\r\n";
                chat += "#L7##r#eคุณต้องการอัปเกรดเป็นระดับ 8 หรือไม่ ?#k#n";
                cm.sendSimple(chat);
            }

            if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade1") == 8) { // Grade 9
                var chat = "#fs11#" + color + (cm.getPlayer().getKeyValue(0, "Tear_Upgrade1") + 1) + " เพื่ออัปเกรดเป็นระดับ 9 ต้องใช้วัสดุดังต่อไปนี้\r\n\r\n";
                chat += "#i4001753# #b#z4001753##k #r170 ชิ้น#k\r\n";
                chat += "----------------------------------\r\n";
                chat += "#b<ของรางวัล Premium Season Pass ระดับ 9>#l\r\n";
                chat += "#i4310266##z4310266# #r2000 ชิ้น\r\n\r\n";
                chat += "#L8##r#eคุณต้องการอัปเกรดเป็นระดับ 9 หรือไม่ ?#k#n";
                cm.sendSimple(chat);
            }
            if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade1") == 9) { // Grade 10
                var chat = "#fs11#" + color + (cm.getPlayer().getKeyValue(0, "Tear_Upgrade1") + 1) + " เพื่ออัปเกรดเป็นระดับ 10 ต้องใช้วัสดุดังต่อไปนี้\r\n\r\n";
                chat += "#i4001753# #b#z4001753##k #r200 ชิ้น#k\r\n";
                chat += "----------------------------------\r\n";
                chat += "#b<ของรางวัล Premium Season Pass ระดับ 10>#l\r\n";
                chat += "#i2430891##z2430891# #r1 ชิ้น\r\n\r\n";
                chat += "#L9##r#eคุณต้องการอัปเกรดเป็นระดับ 10 หรือไม่ ?#k#n";
                cm.sendSimple(chat);
            }

        } else if (selection == 2) {
            var chat = "#fs11##e" + color + "< แนะนำ Season Pass >\r\n\r\n";
            chat += "คุณสามารถรับไอเทม#k#n\r\n\r\n#i4001753# #b#z4001753##k" + black + " ได้ผ่านเควสประจำวัน#k\r\n\r\n";
            chat += black + "สามารถใช้ตั๋วที่ได้รับเพื่ออัปเกรดระดับ Season Pass ได้\r\nและตั๋วจะไม่ถูกใช้เมื่อทำการอัปเกรด\r\n\r\n";
            chat += black + "สามารถรับของรางวัลได้ทุกครั้งที่อัปเกรด\r\n\r\nหากคุณมี #i4001760# #b#z4001760#" + black + " จะสามารถรับของรางวัล Premium Season Pass\r\nซึ่งดีกว่าได้\r\n\r\n";

            chat += "#b<ของรางวัล Premium Season Pass ระดับ 1>#l\r\n";
            chat += "#i4310237##z4310237# #r2000 ชิ้น\r\n\r\n";
            chat += "#b<ของรางวัล Premium Season Pass ระดับ 2>#l\r\n";
            chat += "#i4009005##z4009005# #r500 ชิ้น\r\n\r\n";
            chat += "#b<ของรางวัล Premium Season Pass ระดับ 3>#l\r\n";
            chat += "#i5060048##z5060048# #r15 ชิ้น\r\n\r\n";
            chat += "#b<ของรางวัล Premium Season Pass ระดับ 4>#l\r\n";
            chat += "#i5068305##z5068305# #r5 ชิ้น\r\n\r\n";
            chat += "#b<ของรางวัล Premium Season Pass ระดับ 5>#l\r\n";
            chat += "#i2049376##z2049376# #r5 ชิ้น\r\n\r\n";
            chat += "#b<ของรางวัล Premium Season Pass ระดับ 6>#l\r\n";
            chat += "#i4310308##z4310308# #r500 ชิ้น\r\n\r\n";
            chat += "#b<ของรางวัล Premium Season Pass ระดับ 7>#l\r\n";
            chat += "#i4001715##z4001715# #r500 ชิ้น\r\n\r\n";
            chat += "#b<ของรางวัล Premium Season Pass ระดับ 8>#l\r\n";
            chat += "#i2430044##z2430044# #r1 ชิ้น\r\n\r\n";
            chat += "#b<ของรางวัล Premium Season Pass ระดับ 9>#l\r\n";
            chat += "#i4310266##z4310266# #r2000 ชิ้น\r\n\r\n";
            chat += "#b<ของรางวัล Premium Season Pass ระดับ 10>#l\r\n";
            chat += "#i2430891##z2430891# #r1 ชิ้น\r\n\r\n";
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
                cm.gainItem(4310237, 2000);
                cm.getPlayer().setKeyValue(0, "Tear_Upgrade1", "1");
                cm.sendOk("ยินดีด้วย! คุณอัปเกรดเป็นระดับ 1 สำเร็จแล้ว");
                cm.dispose();
            } else {
                cm.sendOk("วัสดุไม่เพียงพอ");
                cm.dispose();
            }
        }

        if (selection == 1) {
            if (cm.haveItem(4001753, 30)) {
                cm.gainItem(4009005, 500);
                cm.getPlayer().setKeyValue(0, "Tear_Upgrade1", "2");
                cm.sendOk("ยินดีด้วย! คุณอัปเกรดเป็นระดับ 2 สำเร็จแล้ว");
                cm.dispose();
            } else {
                cm.sendOk("วัสดุไม่เพียงพอ");
                cm.dispose();
            }
        }

        if (selection == 2) {
            if (cm.haveItem(4001753, 50)) {
                cm.gainItem(5060048, 15);
                cm.getPlayer().setKeyValue(0, "Tear_Upgrade1", "3");
                //Packages.network.center.Center.Broadcast.broadcastMessage(Packages.network.models.CField.chatMsg(8, cm.getPlayer().getName() + " has been promoted to Gold Tier."));
                cm.sendOk("ยินดีด้วย! คุณอัปเกรดเป็นระดับ 3 สำเร็จแล้ว");
                cm.dispose();
            } else {
                cm.sendOk("วัสดุไม่เพียงพอ");
                cm.dispose();
            }
        }

        if (selection == 3) {
            if (cm.haveItem(4001753, 70)) {
                cm.gainItem(5068305, 5);
                cm.getPlayer().setKeyValue(0, "Tear_Upgrade1", "4");
                //Packages.network.center.Center.Broadcast.broadcastMessage(Packages.network.models.CField.chatMsg(8, cm.getPlayer().getName() + " has been promoted to Platinum Tier."));
                cm.sendOk("ยินดีด้วย! คุณอัปเกรดเป็นระดับ 4 สำเร็จแล้ว");
                cm.dispose();
            } else {
                cm.sendOk("วัสดุไม่เพียงพอ");
                cm.dispose();
            }
        }

        if (selection == 4) {
            if (cm.haveItem(4001753, 90)) {
                cm.gainItem(2049376, 5);
                cm.getPlayer().setKeyValue(0, "Tear_Upgrade1", "5");
                //Packages.network.center.Center.Broadcast.broadcastMessage(Packages.network.models.CField.chatMsg(8, cm.getPlayer().getName() + " has been promoted to Diamond Tier."));
                cm.sendOk("ยินดีด้วย! คุณอัปเกรดเป็นระดับ 5 สำเร็จแล้ว");
                cm.dispose();
            } else {
                cm.sendOk("วัสดุไม่เพียงพอ");
                cm.dispose();
            }
        }

        if (selection == 5) {
            if (cm.haveItem(4001753, 110)) {
                cm.gainItem(4310308, 500);
                cm.getPlayer().setKeyValue(0, "Tear_Upgrade1", "6");
                //Packages.network.center.Center.Broadcast.broadcastMessage(Packages.network.models.CField.chatMsg(8, cm.getPlayer().getName() + " has been promoted to Master Tier."));
                cm.sendOk("ยินดีด้วย! คุณอัปเกรดเป็นระดับ 6 สำเร็จแล้ว");
                cm.dispose();
            } else {
                cm.sendOk("วัสดุไม่เพียงพอ");
                cm.dispose();
            }
        }

        if (selection == 6) {
            if (cm.haveItem(4001753, 130)) {
                cm.gainItem(4001715, 500);
                cm.getPlayer().setKeyValue(0, "Tear_Upgrade1", "7");
                //Packages.network.center.Center.Broadcast.broadcastMessage(Packages.network.models.CField.chatMsg(8, cm.getPlayer().getName() + " has been promoted to Grand Master Tier."));
                cm.sendOk("ยินดีด้วย! คุณอัปเกรดเป็นระดับ 7 สำเร็จแล้ว");
                cm.dispose();
            } else {
                cm.sendOk("วัสดุไม่เพียงพอ");
                cm.dispose();
            }
        }

        if (selection == 7) {
            if (cm.haveItem(4001753, 150)) {
                cm.gainItem(2430044, 1);
                cm.getPlayer().setKeyValue(0, "Tear_Upgrade1", "8");
                //Packages.network.center.Center.Broadcast.broadcastMessage(Packages.network.models.CField.chatMsg(8, cm.getPlayer().getName() + " has been promoted to Legend Tier."));
                cm.sendOk("ยินดีด้วย! คุณอัปเกรดเป็นระดับ 8 สำเร็จแล้ว");
                cm.dispose();
            } else {
                cm.sendOk("วัสดุไม่เพียงพอ");
                cm.dispose();
            }
        }
        if (selection == 8) {
            if (cm.haveItem(4001753, 170)) {
                cm.gainItem(4310266, 2000);
                cm.getPlayer().setKeyValue(0, "Tear_Upgrade1", "9");
                //Packages.network.center.Center.Broadcast.broadcastMessage(Packages.network.models.CField.chatMsg(8, cm.getPlayer().getName() + " has been promoted to Legend Tier."));
                cm.sendOk("ยินดีด้วย! คุณอัปเกรดเป็นระดับ 9 สำเร็จแล้ว");
                cm.dispose();
            } else {
                cm.sendOk("วัสดุไม่เพียงพอ");
                cm.dispose();
            }
        }

        if (selection == 9) {
            if (cm.haveItem(4001753, 200)) {
                cm.gainItem(2430891, 1);
                cm.getPlayer().setKeyValue(0, "Tear_Upgrade1", "10");
                //Packages.network.center.Center.Broadcast.broadcastMessage(Packages.network.models.CField.chatMsg(8, cm.getPlayer().getName() + " has been promoted to Legend Tier."));
                cm.sendOk("ยินดีด้วย! คุณอัปเกรดเป็นระดับ 10 สำเร็จแล้ว");
                cm.dispose();
            } else {
                cm.sendOk("วัสดุไม่เพียงพอ");
                cm.dispose();
            }
        }

        if (selection == 11) {
            if (cm.haveItem(4001753, 200)) {
                cm.gainItem(4310266, 1000);
                cm.getPlayer().setKeyValue(0, "Tear_Upgrade1", "10");
                //Packages.network.center.Center.Broadcast.broadcastMessage(Packages.network.models.CField.chatMsg(8, cm.getPlayer().getName() + " has been promoted to Challenger Tier."));
                cm.sendOk("ยินดีด้วย! คุณอัปเกรดเป็นระดับ 10 สำเร็จแล้ว");
                cm.dispose();
            } else {
                cm.sendOk("วัสดุไม่เพียงพอ");
                cm.dispose();
            }
        }
    }
}

function NullKeyValue() {
    if (cm.getPlayer().getKeyValue(0, "Tear_Upgrade1") == -1) {
        cm.getPlayer().setKeyValue(0, "Tear_Upgrade1", "0");
    }
}
