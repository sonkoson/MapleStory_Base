var Purple = "#fMap/MapHelper.img/weather/starPlanet/7#";
var Blue = "#fMap/MapHelper.img/weather/starPlanet/8#";
var StarBlue = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
var StarYellow = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
var StarWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
var StarBrown = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
var StarRed = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
var StarBlack = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
var StarPurple = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
var Star = "#fUI/FarmUI.img/objectStatus/star/whole#"
var S = "#fUI/CashShop.img/CSEffect/today/0#"
var Reward = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
var Obtain = "#fUI/UIWindow2.img/QuestIcon/4/0#"
var Color = "#fc0xFF6600CC#"
var BlackColor = "#fc0xFF000000#"
var PinkColor = "#fc0xFFFF3366#"
var LightPinkColor = "#fc0xFFF781D8#"
var Enter = "\r\n"
var Enter2 = "\r\n\r\n"
enter = "\r\n";

importPackage(Packages.objects.item);
importPackage(Packages.constants);


var RequiredItems = [
    { 'itemid': 3994408, 'qty': 1 },
    { 'itemid': 3994409, 'qty': 1 },
    { 'itemid': 3994410, 'qty': 1 }
]

var tocoin = 4310185, toqty = 1;

var RewardItems = [
    [2430030, 2],
    [2430031, 2],
    [2430032, 10],
    [2430033, 10],
    [2450134, 5],
    [4310266, 1000]
]

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, sel) {
    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        // First Event Click Animation
        if (getFirst() == null) {
            cm.dispose();
            cm.getClient().setKeyValue("kdayeventFirst", "1");
            cm.warp(307090001);
            cm.openNpcCustom(cm.getClient(), 9001102, "cntjreventeffect");
            return;
        }

        // Count Start
        if (getCount() == null)
            cm.getClient().setKeyValue("kdayevent", "0");

        if (cm.getPlayer().getMapId() == 120040000) {
            msg = "#fs11#ท่านได้ประทับตราไปแล้ว หรือลำดับไม่ถูกต้อง!\r\n\r\n" + PinkColor + Star + " จำนวนตราประทับปัจจุบัน : " + getCount() + " / 7";
            if (getCount() == 0) {
                cm.getClient().setKeyValue("kdayevent", "1");
                cm.showEffect(false, "Effect/CharacterEff.img/GradeUp");
                cm.showEffect(false, "Effect/EventEffect.img/SalonDebut/screenEff/1366");
                msg = "#fs11#ประทับตราให้เรียบร้อยแล้วครับ!\r\n\r\n" + PinkColor + Star + " จำนวนตราประทับปัจจุบัน : " + getCount() + " / 7";
            }
            cm.sendOk(msg);
            cm.dispose();
            return;
        }

        if (cm.getPlayer().getMapId() == 101000000) {
            msg = "#fs11#ท่านได้ประทับตราไปแล้ว หรือลำดับไม่ถูกต้อง!\r\n\r\n" + PinkColor + Star + " จำนวนตราประทับปัจจุบัน : " + getCount() + " / 7";
            if (getCount() == 1) {
                cm.getClient().setKeyValue("kdayevent", "2");
                cm.showEffect(false, "Effect/CharacterEff.img/GradeUp");
                cm.showEffect(false, "Effect/EventEffect.img/SalonDebut/screenEff/1366");
                msg = "#fs11#ประทับตราให้เรียบร้อยแล้วครับ!\r\n\r\n" + PinkColor + Star + " จำนวนตราประทับปัจจุบัน : " + getCount() + " / 7";
            }
            cm.sendOk(msg);
            cm.dispose();
            return;
        }

        if (cm.getPlayer().getMapId() == 120000000) {
            msg = "#fs11#ท่านได้ประทับตราไปแล้ว หรือลำดับไม่ถูกต้อง!\r\n\r\n" + PinkColor + Star + " จำนวนตราประทับปัจจุบัน : " + getCount() + " / 7";
            if (getCount() == 2) {
                cm.getClient().setKeyValue("kdayevent", "3");
                cm.showEffect(false, "Effect/CharacterEff.img/GradeUp");
                cm.showEffect(false, "Effect/EventEffect.img/SalonDebut/screenEff/1366");
                msg = "#fs11#ประทับตราให้เรียบร้อยแล้วครับ!\r\n\r\n" + PinkColor + Star + " จำนวนตราประทับปัจจุบัน : " + getCount() + " / 7";
            }
            cm.sendOk(msg);
            cm.dispose();
            return;
        }

        if (cm.getPlayer().getMapId() == 102000000) {
            msg = "#fs11#ท่านได้ประทับตราไปแล้ว หรือลำดับไม่ถูกต้อง!\r\n\r\n" + PinkColor + Star + " จำนวนตราประทับปัจจุบัน : " + getCount() + " / 7";
            if (getCount() == 3) {
                cm.getClient().setKeyValue("kdayevent", "4");
                cm.showEffect(false, "Effect/CharacterEff.img/GradeUp");
                cm.showEffect(false, "Effect/EventEffect.img/SalonDebut/screenEff/1366");
                msg = "#fs11#ประทับตราให้เรียบร้อยแล้วครับ!\r\n\r\n" + PinkColor + Star + " จำนวนตราประทับปัจจุบัน : " + getCount() + " / 7";
            }
            cm.sendOk(msg);
            cm.dispose();
            return;
        }

        if (cm.getPlayer().getMapId() == 230000000) {
            msg = "#fs11#ท่านได้ประทับตราไปแล้ว หรือลำดับไม่ถูกต้อง!\r\n\r\n" + PinkColor + Star + " จำนวนตราประทับปัจจุบัน : " + getCount() + " / 7";
            if (getCount() == 4) {
                cm.getClient().setKeyValue("kdayevent", "5");
                cm.showEffect(false, "Effect/CharacterEff.img/GradeUp");
                cm.showEffect(false, "Effect/EventEffect.img/SalonDebut/screenEff/1366");
                msg = "#fs11#ประทับตราให้เรียบร้อยแล้วครับ!\r\n\r\n" + PinkColor + Star + " จำนวนตราประทับปัจจุบัน : " + getCount() + " / 7";
            }
            cm.sendOk(msg);
            cm.dispose();
            return;
        }

        if (cm.getPlayer().getMapId() == 211000000) {
            msg = "#fs11#ท่านได้ประทับตราไปแล้ว หรือลำดับไม่ถูกต้อง!\r\n\r\n" + PinkColor + Star + " จำนวนตราประทับปัจจุบัน : " + getCount() + " / 7";
            if (getCount() == 5) {
                cm.getClient().setKeyValue("kdayevent", "6");
                cm.showEffect(false, "Effect/CharacterEff.img/GradeUp");
                cm.showEffect(false, "Effect/EventEffect.img/SalonDebut/screenEff/1366");
                msg = "#fs11#ประทับตราให้เรียบร้อยแล้วครับ!\r\n\r\n" + PinkColor + Star + " จำนวนตราประทับปัจจุบัน : " + getCount() + " / 7";
            }
            cm.sendOk(msg);
            cm.dispose();
            return;
        }

        if (cm.getPlayer().getMapId() == 104020000) {
            msg = "#fs11#ท่านได้ประทับตราไปแล้ว หรือลำดับไม่ถูกต้อง!\r\n\r\n" + PinkColor + Star + " จำนวนตราประทับปัจจุบัน : " + getCount() + " / 7";
            if (getCount() == 6) {
                cm.getClient().setKeyValue("kdayevent", "7");
                cm.showEffect(false, "Effect/CharacterEff.img/GradeUp");
                cm.showEffect(false, "Effect/EventEffect.img/SalonDebut/screenEff/1366");
                msg = "#fs11#ประทับตราให้เรียบร้อยแล้วครับ!\r\n\r\n" + PinkColor + Star + " จำนวนตราประทับปัจจุบัน : " + getCount() + " / 7";
            }
            if (getCount() == 7) {
                msg = "#fs11#ท่านได้รวบรวมตราประทับครบแล้ว! รีบไปที่แผนที่กิจกรรมกันเถอะ\r\n\r\n" + PinkColor + Star + " จำนวนตราประทับปัจจุบัน : " + getCount() + " / 7";
            }
            cm.sendOk(msg);
            cm.dispose();
            return;
        }


        var msg = "　　　#i4031187# #fs14##e[Royal Maple] - Chuseok Event#n#fs11# #i4031187#\r\n#fs11##Cblue#              ขอบคุณที่ใช้บริการ Royal Maple เสมอมาครับ#k\r\n";
        msg += PinkColor + "                               #L100#ข้อมูลกิจกรรม#fc0xFF000000##l\r\n\r\n";
        msg += "                      ระยะเวลากิจกรรม : #b09.05 ~ 09.26\r\n";
        msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        msg += "#L1##fc0xFFFF3366#ไปยังแผนที่กิจกรรม#fc0xFF000000#\r\n";
        msg += "#L2##fc0xFFFF3366#แลกไอเท็ม#fc0xFF000000# กิจกรรม#l\r\n";
        msg += "#L3##fc0xFF6600CC#ร้านค้าไอเท็ม#fc0xFF000000# กิจกรรม#l\r\n";
        msg += "#L4##fc0xFF6600CC#ตรวจสอบแหล่งหาไอเท็ม#fc0xFF000000# กิจกรรม#l\r\n";
        msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        if (cm.getPlayer().getMapId() == 307090001) {
            var msg = "　　　#i4031187# #fs14##e[Royal Maple] - Chuseok Event#n#fs11# #i4031187#\r\n#fs11##Cblue#              ขอบคุณที่ใช้บริการ Royal Maple เสมอมาครับ#k\r\n";
            msg += PinkColor + "                               #L100#ข้อมูลกิจกรรม#fc0xFF000000##l\r\n\r\n";
            msg += "                      ระยะเวลากิจกรรม : #b09.05 ~ 09.26\r\n";
            msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
            msg += "#L1001##fc0xFFFF3366#Royal Castle#fc0xFF000000# วาร์ป#l\r\n";
            msg += "#L1000##fc0xFFFF3366#ตรวจสอบ#fc0xFF000000# ตราประทับที่ได้รับ#l\r\n";
            msg += "#L2##fc0xFF6600CC#แลกไอเท็ม#fc0xFF000000# กิจกรรม#l\r\n";
            msg += "#L3##fc0xFF6600CC#ร้านค้าไอเท็ม#fc0xFF000000# กิจกรรม#l\r\n";
            msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        }
        cm.sendSimple(msg);
    } else if (status == 1) {
        seld = sel;
        switch (sel) {
            case 100:
                var msg = "#fnArial Extrabold##d(ถ้าออกล่ามอนสเตอร์ จะมีขนมซงพยอนดรอปออกมาสินะ..)#k\r\n\r\n#bรีบไปล่ามอนสเตอร์แล้วรวบรวมขนมซงพยอนกันดีกว่า!\r\n\r\n";
                msg += "#L96##rแต่ว่าไอเท็มกิจกรรมเอาไปใช้ทำอะไรได้บ้างนะ..?#l\r\n";
                cm.sendNextS(msg, 2);
                break;

            case 1:
                cm.warp(307090001, 0);
                cm.dispose();

                break;

            case 2:
                var msg = "#fs11#" + PinkColor + "รวบรวมขนมซงพยอนมาแล้วเหรอ?! สามารถแลกเป็น Chuseok Coin ได้นะ!" + BlackColor + Enter + Enter;

                for (i = 0; i < RequiredItems.length; i++) {
                    if (i != RequiredItems.length - 1) msg += "#i" + RequiredItems[i]['itemid'] + "##z" + RequiredItems[i]['itemid'] + "# " + RequiredItems[i]['qty'] + " ชิ้น และ" + Enter;
                    else msg += "#fs11##i" + RequiredItems[i]['itemid'] + "##z" + RequiredItems[i]['itemid'] + "# " + RequiredItems[i]['qty'] + "ชิ้น \r\nถ้าเอามาให้ฉัน ฉันจะให้ #b#i" + tocoin + "##z" + tocoin + "#" + BlackColor + "เป็นการแลกเปลี่ยน!";
                }

                if (haveNeed(1))
                    cm.sendNext(msg);
                else {
                    msg += enter + enter + "แต่ว่า.. ท่านนักผจญภัยยังมีขนมซงพยอนไม่ครบเลยนี่นา";
                    cm.sendOk(msg);
                    cm.dispose();
                }
                break;

            case 3:
                cm.dispose();
                cm.openShop(8888);
                break;

            case 4:
                var msg = "#fs11#";
                msg += "#fs11##b#i3994408##z3994408#\r\n#i3994409##z3994409#\r\n#i3994410##z3994410#\r\n\r\n";
                msg += "#r#e[ตกปลา]#b มีโอกาสได้รับไอเท็ม 3 ชนิดข้างต้น\r\n";
                msg += "#r#e[ล่ามอนสเตอร์]#b มีโอกาสได้รับไอเท็ม 3 ชนิดข้างต้น\r\n\r\n";
                msg += PinkColor + "                   ขนมซงพยอน 3 ชนิดข้างต้น\r\n\r\n#b#i4310185# #z4310185#" + PinkColor + " สามารถนำไปแลกเปลี่ยนได้";
                cm.sendOk(msg);
                cm.dispose();
                break;


            case 1000:
                if (getCount() == 7) {
                    if (getClear() == null) {
                        cm.sendYesNo("#fs11#ท่านรวบรวมตราประทับครบแล้วเหรอ! ต้องการรับของขวัญพิเศษหรือไม่?\r\n\r\n#r※ ไอเท็มพิเศษจะได้รับเพียง 1 ครั้งต่อบัญชีเท่านั้น");
                        break;
                    } else {
                        msg = "#fs11#ท่านได้รับของขวัญพิเศษไปแล้ว!";
                    }
                } else {
                    msg = "#fs11#ท่านยังรวบรวมตราประทับไม่ครบเลย..\r\n\r\n" + PinkColor + Star + " จำนวนตราประทับปัจจุบัน : " + getCount() + " / 7";
                }
                cm.sendOk(msg);
                cm.dispose();
                break;

            case 1001:
                cm.warp(ServerConstants.TownMap, 0);
                cm.dispose();
                break;

        }

    } else if (status == 2) {
        switch (seld) {
            case 100:
                cm.sendOk("#fs11#" + Color + "สามารถใช้ #i4310185# #z4310185# ซื้อไอเท็มต่างๆ ได้ที่ร้านค้ากิจกรรม!");
                cm.dispose();
                break;

            case 2:
                temp = [];
                for (i = 0; i < RequiredItems.length; i++) {
                    temp.push(Math.floor(cm.itemQuantity(RequiredItems[i]['itemid']) / RequiredItems[i]['qty']));
                }
                temp.sort();
                max = temp[0];
                cm.sendGetNumber("#fs11#นักผจญภัยสามารถแลกสูงสุด #b #i" + tocoin + "##z" + tocoin + "# " + max + " ชิ้น#k \r\n\r\nต้องการแลกกี่ชิ้น?", 1, 1, max);
                break;

            case 1000:
                if (cm.getInvSlots(1) < 3 || cm.getInvSlots(2) < 3 || cm.getInvSlots(3) < 3 || cm.getInvSlots(4) < 3 || cm.getInvSlots(5) < 3) {
                    cm.sendOkS("#fs11##fc0xFF6600CC#กรุณาทำช่องว่างในกระเป๋าอย่างน้อย 3 ช่องในแต่ละแท็บ", 2);
                    cm.dispose();
                    return;
                }

                msg = "#fs11#" + Obtain + Enter;
                for (i = 0; i < RewardItems.length; i++) {
                    msg += Color + "#i" + RewardItems[i][0] + "# #z" + RewardItems[i][0] + "##r " + RewardItems[i][1] + " ชิ้น#k" + Enter;
                    cm.gainItem(RewardItems[i][0], RewardItems[i][1]);
                }
                msg += PinkColor + "\r\nไอเท็มด้านบนถูกมอบให้แล้ว";
                cm.sendOk(msg);
                cm.getClient().setKeyValue("kdayevent1", "1");
                cm.dispose();
                break;
        }
    } else if (status == 3) {
        switch (seld) {
            case 2:
                if (!haveNeed(sel)) {
                    cm.sendOk("#fs11#ขนมซงพยอนของนักผจญภัยมีไม่พอ");
                    cm.dispose();
                    return;
                }
                for (i = 0; i < RequiredItems.length; i++) {
                    cm.gainItem(RequiredItems[i]['itemid'], -(RequiredItems[i]['qty'] * sel));
                }
                cm.gainItem(tocoin, (toqty * sel));
                cm.sendOk("#fs11#แลกเปลี่ยนเรียบร้อยแล้ว!");
                cm.dispose();
                break;

        }
    }
}

function haveNeed(a) {
    var ret = true;
    for (i = 0; i < RequiredItems.length; i++) {
        if (!cm.haveItem(RequiredItems[i]['itemid'], (RequiredItems[i]['qty'] * a)))
            ret = false;
    }
    return ret;
}

function getCount() {
    return cm.getClient().getKeyValue("kdayevent");
}

function getFirst() {
    return cm.getClient().getKeyValue("kdayeventFirst");
}

function getClear() {
    return cm.getClient().getKeyValue("kdayevent1");
}