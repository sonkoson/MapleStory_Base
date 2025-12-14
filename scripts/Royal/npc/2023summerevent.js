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
    { 'itemid': 4317001, 'qty': 1 },
    { 'itemid': 4317002, 'qty': 1 },
    { 'itemid': 4317003, 'qty': 1 }
]

var tocoin = 4310176, toqty = 1;

var bosang = [1009901, 1109901, 1089901, 1079901, 1112047, 1012991, 1022991, 1032991];

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
    // Give title duration
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

        var msg = "　#i4310176# #fs11##e[Royal Maple] - 2023 SUMMER Event#n#fs11# #i4310176#\r\n#fs11##Cblue#              ขอบคุณที่ใช้บริการ Royal Maple เสมอมาครับ#k\r\n";
        msg += Pink + "                               #L100#รายละเอียดกิจกรรม#fc0xFF000000##l\r\n\r\n";
        msg += "                      ระยะเวลากิจกรรม : #b07.12 ~ 09.30\r\n";
        msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        msg += "#L3##fc0xFF6600CC#ใช้ร้านค้าเหรียญกิจกรรม#fc0xFF000000##l\r\n";
        msg += "#L4##fc0xFF6600CC#ตรวจสอบแหล่งหาไอเท็มกิจกรรม#fc0xFF000000##l\r\n";
        msg += "#L5##fc0xFF6600CC#รับไอเท็มพิเศษ#fc0xFF000000##l\r\n";
        //msg += "#L6##fc0xFF6600CC#เช็คชื่อ#fc0xFF000000#[ " + count + "วัน/14วัน ]#l\r\n";
        msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";

        cm.sendSimple(msg);
    } else if (status == 1) {
        seld = sel;
        switch (sel) {
            case 100:
                var msg = "#fnArial##d(เมื่อล่ามอนสเตอร์และออนไลน์จะมีเหรียญดรอป..)#k\r\n\r\n#bต้องรีบสะสมเหรียญแล้วสิ!\r\n\r\n";
                msg += "#rว่าแต่ เหรียญกิจกรรมเอาไปใช้ที่ไหนนะ..?\r\n";
                cm.sendNextS(msg, 2);
                break;

            case 1:
                cm.warp(993031000, 0);
                cm.dispose();

                break;

            case 2:
                var msg = "#fs11#" + Pink + "รวบรวมลูกกวาดมาแล้วเหรอ?! แลกเป็นเหรียญ SUMMER Coin ได้นะ!" + Black + enter + enter;

                for (i = 0; i < need.length; i++) {
                    if (i != need.length - 1) msg += "#i" + need[i]['itemid'] + "##z" + need[i]['itemid'] + "# " + need[i]['qty'] + " ชิ้น และ" + enter;
                    else msg += "#fs11##i" + need[i]['itemid'] + "##z" + need[i]['itemid'] + "# " + need[i]['qty'] + " ชิ้น\r\nถ้าท่านนำมาให้ข้า ข้าจะมอบ #b#i" + tocoin + "##z" + tocoin + "#" + Black + " ให้แทน!";
                }

                if (haveNeed(1))
                    cm.sendNext(msg);
                else {
                    msg += enter + enter + "แต่ว่า.. ท่านนักผจญภัยมีลูกกวาดไม่ครบนี่นา";
                    cm.sendOk(msg);
                    cm.dispose();
                }
                break;

            case 3:
                cm.dispose();
                cm.openShop(4444);
                break;

            case 4:
                var msg = "#fs11#";
                msg += "#fs11##b#i4310176##z4310176#\r\n\r\n";
                msg += "#r#e[ตกปลา]#b มีโอกาสได้รับเหรียญข้างต้น\r\n";
                msg += "#r#e[ล่ามอนสเตอร์]#b มีโอกาสได้รับเหรียญข้างต้น";
                cm.sendOk(msg);
                cm.dispose();
                break;


            case 5:
                if (cm.getClient().getKeyValue("2023summereventitem") == null) {
                    msg = "#fs11#ท่านต้องการรับไอเท็มพิเศษหรือไม่?\r\n\r\n#r※ ไอเท็มพิเศษรับได้ 1 ครั้งต่อบัญชี\r\n※ ไอเท็มพิเศษมีอายุ 30 วัน และจะถูกลบเมื่อหมดอายุ";
                    msg += "\r\n\r\n#b < รายการไอเท็มพิเศษ 1 เดือน >\r\n\r\n";
                    for (i = 0; i < bosang.length; i++) {
                        msg += Color + "#i" + bosang[i] + "# #z" + bosang[i] + "#" + enter;
                    }
                    cm.sendYesNo(msg);
                } else {
                    msg = "#fs11#" + Black + "ท่านได้รับ #fc0xFFFF3366#ไอเท็มพิเศษ" + Black + " ไปแล้ว";
                    cm.dispose();
                    cm.sendOk(msg);
                }
                break;

            case 6:
                // Check attendance
                // Get Date
                var months = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"];
                today = new Date();
                month = months[today.getMonth()];
                day = today.getDate();

                today = month + day;

                count = cm.getClient().getKeyValue("2023summerday");

                if (count >= 14) {
                    cm.sendOk("#fs11#" + Black + "ท่านได้ทำการ #fc0xFFFF3366#เช็คชื่อ" + Black + " ครบแล้ว\r\n\r\nจำนวนวันที่เช็คชื่อ : " + count + "/14");
                    cm.dispose();
                }

                if (cm.getClient().getKeyValue("2023summerdaylast") == today) {
                    cm.sendOk("#fs11#" + Black + "วันนี้ท่านได้ทำการ #fc0xFFFF3366#เช็คชื่อ" + Black + " ไปแล้ว\r\n\r\nจำนวนวันที่เช็คชื่อ : " + count + "/14");
                    cm.dispose();
                }

                msg = "#fs11#ต้องการเช็คชื่อหรือไม่?\r\n\r\n#r※ ของรางวัลเช็คชื่อวันนี้คือ.\r\n\r\n";
                for (i = 0; i < dayitem.length; i++) {
                    msg += Color + "#i" + dayitem[i][0] + "# #z" + dayitem[i][0] + "# " + dayitem[i][1] + "ชิ้น" + enter;
                }
                cm.sendYesNo(msg);

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
                cm.sendOk("#fs11#" + Color + "ท่านสามารถซื้อไอเท็มหลากหลายได้ด้วย #i4310176# #z4310176# ในร้านค้าเหรียญกิจกรรม!");
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
                    cm.sendOk("#fs11#ช่องเก็บของไม่เพียงพอ\r\nต้องการช่องว่างในแถบแต่งตัว (Cash) 7 ช่อง");
                    cm.dispose();
                    return;
                }

                msg = "#fs11#" + GetIcon + enter + enter;
                for (i = 0; i < bosang.length; i++) {
                    msg += Color + "#i" + bosang[i] + "# #z" + bosang[i] + "#" + enter;
                    MakeItem(bosang[i]);
                }
                cm.getClient().setKeyValue("2023summereventitem", "1");
                msg += Pink + "\r\nได้รับไอเท็มตามรายการข้างต้นแล้ว";
                cm.sendOk(msg);
                cm.dispose();
                break;

            case 6:
                // Check Attendance
                // Get Date
                var months = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"];
                today = new Date();
                month = months[today.getMonth()];
                day = today.getDate();

                today = month + day

                count = cm.getClient().getKeyValue("2023summerday");
                plustcount = parseInt(count) + 1;

                if (cm.getInvSlots(1) < 1 || cm.getInvSlots(2) < 1 || cm.getInvSlots(3) < 1 || cm.getInvSlots(4) < 1 || cm.getInvSlots(5) < 1) {
                    cm.sendOkS("#fs11##fc0xFF6600CC#กรุณาทำช่องว่างในช่องเก็บของอย่างน้อยแถบละ 1 ช่อง", 2);
                    cm.dispose();
                    return;
                }

                cm.getClient().setKeyValue("2023summerday", plustcount);
                cm.getClient().setKeyValue("2023summerdaylast", today);
                msg = "#fs11#เช็คชื่อเรียบร้อยแล้ว\r\n\r\n#r※ ได้รับของรางวัลเช็คชื่อวันนี้แล้ว\r\n\r\n";
                for (i = 0; i < dayitem.length; i++) {
                    msg += Color + "#i" + dayitem[i][0] + "# #z" + dayitem[i][0] + "# " + dayitem[i][1] + "ชิ้น" + enter;
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
                    cm.sendOk("#fs11#ลูกกวาดของท่านไม่เพียงพอ");
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


function getFirst() {
    return cm.getClient().getKeyValue("2023summereventFirst");
}