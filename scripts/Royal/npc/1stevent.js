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
var Black = "#fc0xFF000000#"
var Pink = "#fc0xFFFF3366#"
var LightPink = "#fc0xFFF781D8#"
var Enter = "\r\n"
var Enter2 = "\r\n\r\n"

importPackage(Packages.objects.item);
importPackage(Packages.constants);


var need = [
    { 'itemid': 4317001, 'qty': 1 },
    { 'itemid': 4317002, 'qty': 1 },
    { 'itemid': 4317003, 'qty': 1 }
]

var tocoin = 4310184, toqty = 1;

var bosang = [1009900, 1109900, 1089900, 1079900, 1112046, 1012990, 1022990, 1032990];

var bosang1 = [3700599];

var dayitem = [];

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

function MakeItem1(itemid) {
    // Grant Title Period
    var item = new Item(itemid, 1, 1, 0);
    item.setExpiration((new Date()).getTime() + (1000 * 60 * 60 * 24 * 60));
    Packages.objects.item.MapleInventoryManipulator.addFromDrop(cm.getPlayer().getClient(), item, false);
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
        // Play animation on first event click

        if (cm.getClient().getKeyValue("1stday") == null) {
            cm.getClient().setKeyValue("1stday", 0);
        }

        count = cm.getClient().getKeyValue("1stday");
        if (count == 0) {
            dayitem = [
                [4310282, 30]
            ]
        } else if (count == 1) {
            dayitem = [
                [4310237, 1000]
            ]
        } else if (count == 2) {
            dayitem = [
                [4310266, 1000]
            ]
        } else if (count == 3) {
            dayitem = [
                [4310229, 1000]
            ]
        } else if (count == 4) {
            dayitem = [
                [4001715, 100]
            ]
        } else if (count == 5) {
            dayitem = [
                [2630127, 1]
            ]
        } else if (count == 6) {
            dayitem = [
                [2439990, 2]
            ]
        } else if (count == 7) {
            dayitem = [
                [5060048, 5]
            ]
        } else if (count == 8) {
            dayitem = [
                [4036660, 10]
            ]
        } else if (count == 9) {
            dayitem = [
                [5068305, 5]
            ]
        } else if (count == 10) {
            dayitem = [
                [4036661, 2]
            ]
        } else if (count == 11) {
            dayitem = [
                [2439932, 1]
            ]
        } else if (count == 12) {
            dayitem = [
                [2439932, 2]
            ]
        } else if (count == 13) {
            dayitem = [
                [2439990, 2]
            ]
        }

        var msg = "　#i4310175# #fs11##e[Royal Maple] - 1St Anniversary Event#n#fs11# #i4310175#\r\n#fs11##Cblue#              ขอบคุณที่ใช้บริการ Royal Maple ครับ#k\r\n";
        msg += Pink + "                               #L100#รายละเอียดกิจกรรม#fc0xFF000000##l\r\n\r\n";
        msg += "                      ระยะเวลากิจกรรม : #b04.17 ~ 05.31\r\n";
        msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        //msg += "#L1##fc0xFFFF3366#ไปที่แผนที่กิจกรรม#fc0xFF000000# #l\r\n";
        //msg += "#L2##fc0xFFFF3366#แลกเปลี่ยนไอเท็มกิจกรรม#fc0xFF000000# #l\r\n";
        msg += "#L3##fc0xFF6600CC#ใช้ร้านค้าเหรียญกิจกรรม#fc0xFF000000##l\r\n";
        msg += "#L4##fc0xFF6600CC#ตรวจสอบแหล่งหาไอเท็มกิจกรรม#fc0xFF000000# #l\r\n";
        msg += "#L5##fc0xFF6600CC#รับไอเท็มพิเศษ#fc0xFF000000# #l\r\n\r\n";
        msg += "#L6##fc0xFF6600CC#เช็คชื่อ#fc0xFF000000#[ " + count + "วัน/14วัน ]#l\r\n";
        msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";

        cm.sendSimple(msg);
    } else if (status == 1) {
        seld = sel;
        switch (sel) {
            case 100:
                var msg = "#d(ล่ามอนสเตอร์และตกปลาก็จะมีเหรียญดรอปนะ..)#k\r\n\r\n#bต้องรีบไปสะสมเหรียญแล้วสิ!\r\n\r\n";
                msg += "#rแต่ว่าเหรียญกิจกรรมเอาไปใช้ที่ไหนกันนะ..?\r\n";
                cm.sendNextS(msg, 2);
                break;

            case 1:
                cm.warp(993031000, 0);
                cm.dispose();

                break;

            case 2:
                var msg = "#fs11#" + Pink + "สะสมลูกอมมาแล้วเหรอคะ?! ฉันสามารถแลกเป็น 1St Anniversary Coin ให้ได้นะ!" + Black + Enter + Enter;

                for (i = 0; i < need.length; i++) {
                    if (i != need.length - 1) msg += "#i" + need[i]['itemid'] + "##z" + need[i]['itemid'] + "# " + need[i]['qty'] + " ชิ้น และ" + Enter;
                    else msg += "#fs11##i" + need[i]['itemid'] + "##z" + need[i]['itemid'] + "# " + need[i]['qty'] + " ชิ้น\r\nถ้าคุณให้ฉัน ฉันจะให้ #b#i" + tocoin + "##z" + tocoin + "#" + Black + " แทนค่ะ!";
                }

                if (haveNeed(1))
                    cm.sendNext(msg);
                else {
                    msg += Enter + Enter + "แต่ว่า.. ท่านนักผจญภัยยังมีลูกอมไม่ครบนี่คะ";
                    cm.sendOk(msg);
                    cm.dispose();
                }
                break;

            case 3:
                cm.dispose();
                cm.openShop(5555);
                break;

            case 4:
                var msg = "#fs11#";
                msg += "#fs11##b#i4310175##z4310175#\r\n\r\n";
                msg += "#r#e[ตกปลา]#b มีโอกาสได้รับเหรียญข้างต้น\r\n";
                msg += "#r#e[ล่ามอนสเตอร์]#b มีโอกาสได้รับเหรียญข้างต้น";
                cm.sendOk(msg);
                cm.dispose();
                break;


            case 5:
                if (cm.getClient().getKeyValue("1steventitem") == null) {
                    msg = "#fs11#ต้องการรับไอเท็มพิเศษหรือไม่?\r\n\r\n#r※ ไอเท็มพิเศษรับได้ 1 ครั้งต่อบัญชีเท่านั้น\r\n※ ไอเท็มพิเศษมีอายุ 30 วัน และจะถูกลบเมื่อหมดอายุ";
                    msg += "\r\n\r\n#b < รายการไอเท็มพิเศษ 2 เดือน >\r\n\r\n";
                    msg += Color + "#i" + bosang1[0] + "# #z" + bosang1[0] + "#" + Enter;
                    msg += "\r\n\r\n#b < รายการไอเท็มพิเศษ 1 เดือน >\r\n\r\n";
                    for (i = 0; i < bosang.length; i++) {
                        msg += Color + "#i" + bosang[i] + "# #z" + bosang[i] + "#" + Enter;
                    }
                    cm.sendYesNo(msg);
                } else {
                    msg = "#fs11#" + Black + "คุณได้รับ #fc0xFFFF3366#ไอเท็มพิเศษ" + Black + " ไปแล้ว";
                    cm.dispose();
                    cm.sendOk(msg);
                }
                break;

            case 6:
                // Check
                // Date
                var months = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"];
                today = new Date();
                month = months[today.getMonth()];
                day = today.getDate();

                today = month + day;

                count = cm.getClient().getKeyValue("1stday");

                if (count >= 14) {
                    cm.sendOk("#fs11#" + Black + "คุณเช็คชื่อ #fc0xFFFF3366#ครบแล้ว" + Black + " ทั้งหมด\r\n\r\nจำนวนวันที่เช็คชื่อ : " + count + "/14");
                    cm.dispose();
                }

                if (cm.getClient().getKeyValue("1stdaylast") == today) {
                    cm.sendOk("#fs11#" + Black + "วันนี้คุณได้ทำการ #fc0xFFFF3366#เช็คชื่อ" + Black + " ไปแล้ว\r\n\r\nจำนวนวันที่เช็คชื่อ : " + count + "/14");
                    cm.dispose();
                }

                msg = "#fs11#ต้องการเช็คชื่อหรือไม่?\r\n\r\n#r※ ของรางวัลเช็คชื่อวันนี้คือ.\r\n\r\n";
                for (i = 0; i < dayitem.length; i++) {
                    msg += Color + "#i" + dayitem[i][0] + "# #z" + dayitem[i][0] + "# " + dayitem[i][1] + " ชิ้น" + Enter;
                }
                cm.sendYesNo(msg);

                break;

            case 1000:
                if (getCount() == 7) {
                    if (getClear() == null) {
                        cm.sendYesNo("#fs11#ท่านรวบรวมตราประทับครบแล้ว! ต้องการรับของขวัญพิเศษหรือไม่?\r\n\r\n#r※ ไอเท็มพิเศษจะได้รับเพียง 1 ครั้งต่อบัญชีเท่านั้น");
                        break;
                    } else {
                        msg = "#fs11#ท่านได้รับของขวัญพิเศษไปแล้ว!";
                    }
                } else {
                    msg = "#fs11#ท่านยังรวบรวมตราประทับไม่ครบเลย..\r\n\r\n" + Pink + Star + " จำนวนตราประทับปัจจุบัน : " + getCount() + " / 7";
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
                cm.sendOk("#fs11#" + Color + "สามารถซื้อไอเท็มหลากหลายได้ที่ Event Coin Shop ด้วย #i4310175# #z4310175# !");
                cm.dispose();
                break;

            case 2:
                temp = [];
                for (i = 0; i < need.length; i++) {
                    temp.push(Math.floor(cm.itemQuantity(need[i]['itemid']) / need[i]['qty']));
                }
                temp.sort();
                max = temp[0];
                cm.sendGetNumber("#fs11#ท่านนักผจญภัยสามารถแลกสูงสุด #b #i" + tocoin + "##z" + tocoin + "# " + max + " ชิ้น#k \r\n\r\nต้องการแลกเท่าไหร่คะ?", 1, 1, max);
                break;

            case 5:
                if (cm.getInvSlots(3) < 1 || cm.getInvSlots(6) < 7) {
                    cm.sendOk("#fs11#ช่องเก็บของไม่เพียงพอ\r\nต้องการช่องว่างในแถบ Setup 1 ช่อง และ Cash 7 ช่อง");
                    cm.dispose();
                    return;
                }

                msg = "#fs11#" + Obtain + Enter + Enter;
                msg += Color + "#i" + bosang1[0] + "# #z" + bosang1[0] + "#" + Enter;
                for (i = 0; i < bosang.length; i++) {
                    msg += Color + "#i" + bosang[i] + "# #z" + bosang[i] + "#" + Enter;
                    MakeItem(bosang[i]);
                }
                MakeItem1(bosang1[0], 1);
                cm.getClient().setKeyValue("1steventitem", "1");
                msg += Pink + "\r\nได้รับไอเท็มเรียบร้อยแล้ว";
                cm.sendOk(msg);
                cm.dispose();
                break;

            case 6:
                // Attendance Check
                // Get Date
                var months = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"];
                today = new Date();
                month = months[today.getMonth()];
                day = today.getDate();

                today = month + day

                count = cm.getClient().getKeyValue("1stday");
                plustcount = parseInt(count) + 1;

                if (cm.getInvSlots(1) < 1 || cm.getInvSlots(2) < 1 || cm.getInvSlots(3) < 1 || cm.getInvSlots(4) < 1 || cm.getInvSlots(5) < 1) {
                    cm.sendOkS("#fs11##fc0xFF6600CC#กรุณาเคลียร์ช่องเก็บของแต่ละแท็บให้ว่างอย่างน้อย 1 ช่อง", 2);
                    cm.dispose();
                    return;
                }

                cm.getClient().setKeyValue("1stday", plustcount);
                cm.getClient().setKeyValue("1stdaylast", today);
                msg = "#fs11#เช็คชื่อเรียบร้อยแล้ว\r\n\r\n#r※ ของรางวัลเช็คชื่อวันนี้คือ.\r\n\r\n";
                for (i = 0; i < dayitem.length; i++) {
                    msg += Color + "#i" + dayitem[i][0] + "# #z" + dayitem[i][0] + "# " + dayitem[i][1] + " ชิ้น" + Enter;
                    cm.gainItem(dayitem[i][0], dayitem[i][1]);
                }
                cm.sendOk(msg);
                cm.dispose();
                break;

        }
    } else if (status == 3) {
        switch (seld) {
            case 2:
                if (!haveNeed(sel)) {
                    cm.sendOk("#fs11#ลูกอมของท่านนักผจญภัยไม่เพียงพอค่ะ");
                    cm.dispose();
                    return;
                }
                for (i = 0; i < need.length; i++) {
                    cm.gainItem(need[i]['itemid'], -(need[i]['qty'] * sel));
                }
                cm.gainItem(tocoin, (toqty * sel));
                cm.sendOk("#fs11#แลกเปลี่ยนเรียบร้อยแล้วค่ะ!");
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


function getFirst() {
    return cm.getClient().getKeyValue("1steventFirst");
}