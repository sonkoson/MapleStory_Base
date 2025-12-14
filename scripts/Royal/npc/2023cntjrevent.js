var Violet = "#fMap/MapHelper.img/weather/starPlanet/7#";
var Blue = "#fMap/MapHelper.img/weather/starPlanet/8#";
var StarBlue = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
var StarYellow = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
var StarWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
var StarBrown = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
var StarRed = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
var StarBlack = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
var StarViolet = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
var Star = "#fUI/FarmUI.img/objectStatus/star/whole#"
var S = "#fUI/CashShop.img/CSEffect/today/0#"
var RewardIcon = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
var GetIcon = "#fUI/UIWindow2.img/QuestIcon/4/0#"
var Color = "#fc0xFF6600CC#"
var Black = "#fc0xFF000000#"
var Pink = "#fc0xFFFF3366#"
var LightPink = "#fc0xFFF781D8#"
var Enter = "\r\n"
var Enter2 = "\r\n\r\n"
var enter = "\r\n";

importPackage(Packages.objects.item);
importPackage(Packages.constants);


var need = [
    { 'itemid': 3994408, 'qty': 1 },
    { 'itemid': 3994409, 'qty': 1 },
    { 'itemid': 3994410, 'qty': 1 }
]

var tocoin = 4310185, toqty = 1;

var bosang = [
    [2430030, 2],
    [2430031, 2],
    [2430032, 10],
    [2430033, 10],
    [2450134, 5],
    [4310266, 1000]
]

var bosang1 = [1009902, 1109902, 1089902, 1079902, 1112048, 1012992, 1022992, 1032992];

function MakeItem(itemid) {
    var ii = Packages.objects.item.MapleItemInformationProvider.getInstance();
    var it = ii.getEquipById(itemid);
    it.setStr(444);
    it.setDex(444);
    it.setInt(444);
    it.setLuk(444);
    it.setWatk(333);
    it.setMatk(333);
    it.setCHUC(30);
    it.setAllStat(5);
    it.setOwner("Royal Maple");
    it.setExpiration((new Date()).getTime() + (1000 * 60 * 60 * 24 * 30));
    Packages.objects.item.MapleInventoryManipulator.addFromDrop(cm.getPlayer().getClient(), it, false);
}

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
        cm.getClient().removeKeyValue("2023cntjreventFirst");

        // Count start
        if (getCount() == null)
            cm.getClient().setKeyValue("2023cntjrevent", "0");

        if (cm.getPlayer().getMapId() == 120040000) {
            msg = "#fs11#ท่านได้ประทับตราที่นี่ไปแล้ว หรือลำดับผิดพลาด!\r\n\r\n" + Pink + Star + " จำนวนตราที่ประทับ: " + getCount() + " / 7";
            if (getCount() == 0) {
                cm.getClient().setKeyValue("2023cntjrevent", "1");
                cm.showEffect(false, "Effect/CharacterEff.img/GradeUp");
                cm.showEffect(false, "Effect/EventEffect.img/SalonDebut/screenEff/1366");
                msg = "#fs11#ประทับตราเรียบร้อยแล้ว!\r\n\r\n" + Pink + Star + " จำนวนตราที่ประทับ: " + getCount() + " / 7";
            }
            cm.sendOk(msg);
            cm.dispose();
            return;
        }

        if (cm.getPlayer().getMapId() == 100000000) {
            msg = "#fs11#ท่านได้ประทับตราที่นี่ไปแล้ว หรือลำดับผิดพลาด!\r\n\r\n" + Pink + Star + " จำนวนตราที่ประทับ: " + getCount() + " / 7";
            if (getCount() == 1) {
                cm.getClient().setKeyValue("2023cntjrevent", "2");
                cm.showEffect(false, "Effect/CharacterEff.img/GradeUp");
                cm.showEffect(false, "Effect/EventEffect.img/SalonDebut/screenEff/1366");
                msg = "#fs11#ประทับตราเรียบร้อยแล้ว!\r\n\r\n" + Pink + Star + " จำนวนตราที่ประทับ: " + getCount() + " / 7";
            }
            cm.sendOk(msg);
            cm.dispose();
            return;
        }

        if (cm.getPlayer().getMapId() == 101000000) {
            msg = "#fs11#ท่านได้ประทับตราที่นี่ไปแล้ว หรือลำดับผิดพลาด!\r\n\r\n" + Pink + Star + " จำนวนตราที่ประทับ: " + getCount() + " / 7";
            if (getCount() == 2) {
                cm.getClient().setKeyValue("2023cntjrevent", "3");
                cm.showEffect(false, "Effect/CharacterEff.img/GradeUp");
                cm.showEffect(false, "Effect/EventEffect.img/SalonDebut/screenEff/1366");
                msg = "#fs11#ประทับตราเรียบร้อยแล้ว!\r\n\r\n" + Pink + Star + " จำนวนตราที่ประทับ: " + getCount() + " / 7";
            }
            cm.sendOk(msg);
            cm.dispose();
            return;
        }

        if (cm.getPlayer().getMapId() == 102000000) {
            msg = "#fs11#ท่านได้ประทับตราที่นี่ไปแล้ว หรือลำดับผิดพลาด!\r\n\r\n" + Pink + Star + " จำนวนตราที่ประทับ: " + getCount() + " / 7";
            if (getCount() == 3) {
                cm.getClient().setKeyValue("2023cntjrevent", "4");
                cm.showEffect(false, "Effect/CharacterEff.img/GradeUp");
                cm.showEffect(false, "Effect/EventEffect.img/SalonDebut/screenEff/1366");
                msg = "#fs11#ประทับตราเรียบร้อยแล้ว!\r\n\r\n" + Pink + Star + " จำนวนตราที่ประทับ: " + getCount() + " / 7";
            }
            cm.sendOk(msg);
            cm.dispose();
            return;
        }

        if (cm.getPlayer().getMapId() == 120000000) {
            msg = "#fs11#ท่านได้ประทับตราที่นี่ไปแล้ว หรือลำดับผิดพลาด!\r\n\r\n" + Pink + Star + " จำนวนตราที่ประทับ: " + getCount() + " / 7";
            if (getCount() == 4) {
                cm.getClient().setKeyValue("2023cntjrevent", "5");
                cm.showEffect(false, "Effect/CharacterEff.img/GradeUp");
                cm.showEffect(false, "Effect/EventEffect.img/SalonDebut/screenEff/1366");
                msg = "#fs11#ประทับตราเรียบร้อยแล้ว!\r\n\r\n" + Pink + Star + " จำนวนตราที่ประทับ: " + getCount() + " / 7";
            }
            cm.sendOk(msg);
            cm.dispose();
            return;
        }

        if (cm.getPlayer().getMapId() == 200000000) {
            msg = "#fs11#ท่านได้ประทับตราที่นี่ไปแล้ว หรือลำดับผิดพลาด!\r\n\r\n" + Pink + Star + " จำนวนตราที่ประทับ: " + getCount() + " / 7";
            if (getCount() == 5) {
                cm.getClient().setKeyValue("2023cntjrevent", "6");
                cm.showEffect(false, "Effect/CharacterEff.img/GradeUp");
                cm.showEffect(false, "Effect/EventEffect.img/SalonDebut/screenEff/1366");
                msg = "#fs11#ประทับตราเรียบร้อยแล้ว!\r\n\r\n" + Pink + Star + " จำนวนตราที่ประทับ: " + getCount() + " / 7";
            }
            cm.sendOk(msg);
            cm.dispose();
            return;
        }

        if (cm.getPlayer().getMapId() == 101050000) {
            msg = "#fs11#ท่านได้ประทับตราที่นี่ไปแล้ว หรือลำดับผิดพลาด!\r\n\r\n" + Pink + Star + " จำนวนตราที่ประทับ: " + getCount() + " / 7";
            if (getCount() == 6) {
                cm.getClient().setKeyValue("2023cntjrevent", "7");
                cm.showEffect(false, "Effect/CharacterEff.img/GradeUp");
                cm.showEffect(false, "Effect/EventEffect.img/SalonDebut/screenEff/1366");
                msg = "#fs11#ประทับตราเรียบร้อยแล้ว!\r\n\r\n" + Pink + Star + " จำนวนตราที่ประทับ: " + getCount() + " / 7";
            }
            if (getCount() == 7) {
                msg = "#fs11#ประทับตราครบทั้งหมดแล้ว! รีบไปที่แผนที่กิจกรรมในแชแนล 1 สิ\r\n\r\n" + Pink + Star + " จำนวนตราที่ประทับ: " + getCount() + " / 7";
            }
            cm.sendOk(msg);
            cm.dispose();
            return;
        }


        var msg = "　　　#i4031187# #fs14##e[Royal Maple] - Open Event#n#fs11# #i4031187#\r\n#fs11##Cblue#              ขอบคุณที่ใช้บริการ Royal Maple เสมอมาครับ#k\r\n";
        msg += Pink + "                               #L100#รายละเอียดกิจกรรม#fc0xFF000000##l\r\n\r\n";
        msg += "                      ระยะเวลากิจกรรม : #b23.12.15~24.01.31\r\n";
        msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        //         msg += "#L1##fc0xFFFF3366#ย้ายไปแมพกิจกรรม#fc0xFF000000##l\r\n";
        msg += "#L2##fc0xFFFF3366#แลกไอเท็มกิจกรรม#fc0xFF000000##l\r\n";
        msg += "#L3##fc0xFF6600CC#ร้านค้าไอเท็มกิจกรรม#fc0xFF000000##l\r\n";
        msg += "#L4##fc0xFF6600CC#ตรวจสอบแหล่งหาไอเท็มกิจกรรม#fc0xFF000000##l\r\n";
        msg += "#L5##fc0xFF6600CC#รับไอเท็มพิเศษ#fc0xFF000000##l\r\n";
        msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        if (cm.getPlayer().getMapId() == 307090001) {
            var msg = "　　　#i4031187# #fs14##e[Royal Maple] - Open Event#n#fs11# #i4031187#\r\n#fs11##Cblue#              ขอบคุณที่ใช้บริการ Royal Maple เสมอมาครับ#k\r\n";
            msg += Pink + "                               #L100#รายละเอียดกิจกรรม#fc0xFF000000##l\r\n\r\n";
            msg += "                      ระยะเวลากิจกรรม : #b23.12.15~24.01.31\r\n";
            msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
            msg += "#L1001##fc0xFFFF3366#ย้ายไป Royal Garden#fc0xFF000000##l\r\n";
            msg += "#L1000##fc0xFFFF3366#ตรวจสอบตราประทับ#fc0xFF000000##l\r\n";
            msg += "#L2##fc0xFF6600CC#แลกไอเท็มกิจกรรม#fc0xFF000000##l\r\n";
            msg += "#L3##fc0xFF6600CC#ร้านค้าไอเท็มกิจกรรม#fc0xFF000000##l\r\n";
            msg += "#L5##fc0xFF6600CC#รับไอเท็มพิเศษ#fc0xFF000000##l\r\n";
            msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        }
        cm.sendSimple(msg);
    } else if (status == 1) {
        seld = sel;
        switch (sel) {
            case 100:
                var msg = "#fnArial##d(ถ้าล่ามอนสเตอร์จะมีขนมไหว้พระจันทร์ดรอปมาหลากหลายแบบ..)#k\r\n\r\n#bต้องรีบไปล่ามอนสเตอร์แล้วรวบรวมขนมไหว้พระจันทร์!\r\n\r\n";
                msg += "#L96##rแต่ไอเท็มกิจกรรมเอาไปใช้ที่ไหนนะ..?#l\r\n";
                cm.sendNextS(msg, 2);
                break;

            case 1:
                cm.warp(307090001, 0);
                cm.dispose();

                break;

            case 2:
                var msg = "#fs11#" + Pink + "รวบรวมขนมไหว้พระจันทร์มาแล้วเหรอ?! แลกเป็นเหรียญ Chuseok Coin ได้นะ!" + Black + enter + enter;

                for (i = 0; i < need.length; i++) {
                    if (i != need.length - 1) msg += "#i" + need[i]['itemid'] + "##z" + need[i]['itemid'] + "# " + need[i]['qty'] + " ชิ้น และ" + enter;
                    else msg += "#fs11##i" + need[i]['itemid'] + "##z" + need[i]['itemid'] + "# " + need[i]['qty'] + " ชิ้น\r\nถ้าท่านนำมาให้ข้า ข้าจะมอบ #b#i" + tocoin + "##z" + tocoin + "#" + Black + " ให้แทน!";
                }

                if (haveNeed(1))
                    cm.sendNext(msg);
                else {
                    msg += enter + enter + "แต่ว่า.. ท่านนักผจญภัยมีขนมไหว้พระจันทร์ไม่ครบนี่นา";
                    cm.sendOk(msg);
                    cm.dispose();
                }
                break;

            case 3:
                cm.dispose();
                cm.openShop(3333);
                break;

            case 4:
                var msg = "#fs11#";
                msg += "#fs11##b#i3994408##z3994408#\r\n#i3994409##z3994409#\r\n#i3994410##z3994410#\r\n\r\n";
                msg += "#r#e[ตกปลา]#b มีโอกาสได้รับไอเท็มทั้ง 3 ชนิดข้างต้น\r\n";
                msg += "#r#e[ล่ามอนสเตอร์]#b มีโอกาสได้รับไอเท็มทั้ง 3 ชนิดข้างต้น\r\n\r\n";
                msg += Pink + "                   ท่านสามารถนำขนมไหว้พระจันทร์ทั้ง 3 ชนิด\r\n\r\n#b#i4310185# #z4310185#" + Pink + " ไปแลกเปลี่ยนได้";
                cm.sendOk(msg);
                cm.dispose();
                break;

            case 5:
                if (cm.getClient().getKeyValue("2023cntjreventitem") == null) {
                    msg = "#fs11#ท่านต้องการรับไอเท็มพิเศษหรือไม่?\r\n\r\n#r※ ไอเท็มพิเศษรับได้ 1 ครั้งต่อบัญชี (ไม่สามารถย้ายตัวละครได้)\r\n※ ไอเท็มพิเศษมีอายุ 30 วัน และจะถูกลบเมื่อหมดอายุ";
                    msg += "\r\n\r\n#b < รายการไอเท็มพิเศษ 1 เดือน >\r\n\r\n";
                    for (i = 0; i < bosang1.length; i++) {
                        msg += Color + "#i" + bosang1[i] + "# #z" + bosang1[i] + "#" + enter;
                    }
                    cm.sendYesNo(msg);
                } else {
                    msg = "#fs11#" + Black + "ท่านได้รับ #fc0xFFFF3366#ไอเท็มพิเศษ" + Black + " ไปแล้ว";
                    cm.dispose();
                    cm.sendOk(msg);
                }
                break;

            case 1000:
                if (getCount() == 7) {
                    if (getClear() == null) {
                        cm.sendYesNo("#fs11#ท่านสะสมตราประทับครบแล้ว! ต้องการรับของขวัญพิเศษหรือไม่?\r\n\r\n#r※ ไอเท็มพิเศษรับได้ 1 ครั้งต่อบัญชี");
                        break;
                    } else {
                        msg = "#fs11#ท่านได้รับของขวัญพิเศษไปแล้ว!";
                    }
                } else {
                    msg = "#fs11#ท่านยังสะสมตราประทับไม่ครบ..\r\n\r\n" + Pink + Star + " จำนวนตราที่ประทับ: " + getCount() + " / 7";
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
                cm.sendOk("#fs11#" + Color + "ท่านสามารถซื้อไอเท็มหลากหลายได้ด้วย #i4310185# #z4310185# ในร้านค้ากิจกรรม!");
                cm.dispose();
                break;

            case 2:
                temp = [];
                for (i = 0; i < need.length; i++) {
                    temp.push(Math.floor(cm.itemQuantity(need[i]['itemid']) / need[i]['qty']));
                }
                temp.sort();
                max = temp[0];
                cm.sendGetNumber("#fs11#ท่านสามารถแลก #b #i" + tocoin + "##z" + tocoin + "# ได้สูงสุด " + max + " ชิ้น..\r\n\r\nต้องการแลกกี่ชิ้น?", 1, 1, max);
                break;

            case 5:
                if (cm.getInvSlots(6) < 8) {
                    cm.sendOk("#fs11#ช่องเก็บของไม่เพียงพอ\r\nต้องการช่องว่างในแถบแต่งตัว (Cash) 8 ช่อง");
                    cm.dispose();
                    return;
                }

                msg = "#fs11#" + GetIcon + enter + enter;
                for (i = 0; i < bosang1.length; i++) {
                    msg += Color + "#i" + bosang1[i] + "# #z" + bosang1[i] + "#" + enter;
                    MakeItem(bosang1[i]);
                }
                cm.getClient().setKeyValue("2023cntjreventitem", "1");
                msg += Pink + "\r\nได้รับไอเท็มตามรายการข้างต้นแล้ว";
                cm.sendOk(msg);
                cm.dispose();
                break;

            case 1000:
                if (cm.getInvSlots(1) < 3 || cm.getInvSlots(2) < 3 || cm.getInvSlots(3) < 3 || cm.getInvSlots(4) < 3 || cm.getInvSlots(5) < 3) {
                    cm.sendOkS("#fs11##fc0xFF6600CC#กรุณาทำช่องว่างในช่องเก็บของอย่างน้อยแถบละ 3 ช่อง", 2);
                    cm.dispose();
                    return;
                }

                msg = "#fs11#" + GetIcon + enter;
                for (i = 0; i < bosang.length; i++) {
                    msg += Color + "#i" + bosang[i][0] + "# #z" + bosang[i][0] + "##r " + bosang[i][1] + " ชิ้น#k" + enter;
                    cm.gainItem(bosang[i][0], bosang[i][1]);
                }
                msg += Pink + "\r\nได้รับไอเท็มตามรายการข้างต้นแล้ว";
                cm.sendOk(msg);
                cm.getClient().setKeyValue("2023cntjrevent1", "1");
                cm.dispose();
                break;
        }
    } else if (status == 3) {
        switch (seld) {
            case 2:
                if (!haveNeed(sel)) {
                    cm.sendOk("#fs11#ขนมไหว้พระจันทร์ของท่านไม่เพียงพอ");
                    cm.dispose();
                    return;
                }
                for (i = 0; i < need.length; i++) {
                    cm.gainItem(need[i]['itemid'], -(need[i]['qty'] * sel));
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
    for (i = 0; i < need.length; i++) {
        if (!cm.haveItem(need[i]['itemid'], (need[i]['qty'] * a)))
            ret = false;
    }
    return ret;
}

function getCount() {
    return cm.getClient().getKeyValue("2023cntjrevent");
}

function getFirst() {
    return cm.getClient().getKeyValue("2023cntjreventFirst");
}

function getClear() {
    return cm.getClient().getKeyValue("2023cntjrevent1");
}