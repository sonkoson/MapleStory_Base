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
var reward = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
var obtain = "#fUI/UIWindow2.img/QuestIcon/4/0#"
var color = "#fc0xFF6600CC#"
var black = "#fc0xFF000000#"
var pink = "#fc0xFFFF3366#"
var lightPink = "#fc0xFFF781D8#"
var enter = "\r\n"
var enter2 = "\r\n\r\n"
enter = "\r\n";

importPackage(Packages.objects.item);
importPackage(Packages.constants);


var need = [
    { 'itemid': 4317001, 'qty': 1 },
    { 'itemid': 4317002, 'qty': 1 },
    { 'itemid': 4317003, 'qty': 1 }
]

var tocoin = 4310184, toqty = 1;

var bosang = [1002186, 1082102, 1102039, 1072153, 1703129, 1012104, 1022079, 1032024];

var bosang1 = [1404022];

function MakeItem(itemid) {
    var ii = Packages.objects.item.MapleItemInformationProvider.getInstance();
    var it = ii.getEquipById(itemid);
    it.setStr(1500);
    it.setDex(1500);
    it.setInt(1500);
    it.setLuk(1500);
    it.setWatk(1000);
    it.setMatk(1000);
    it.setCHUC(30);
    it.setAllStat(10);
    it.setOwner("SAVIOR");
    it.setExpiration((new Date()).getTime() + (1000 * 60 * 60 * 24 * 30));
    Packages.objects.item.MapleInventoryManipulator.addFromDrop(cm.getPlayer().getClient(), it, false);
}

function MakeItem1(itemid) {
    //Grant equipment item options
    var ii = Packages.objects.item.MapleItemInformationProvider.getInstance();
    var it = ii.getEquipById(itemid);
    it.setStr(1000);
    it.setDex(1000);
    it.setInt(1000);
    it.setLuk(1000);
    it.setWatk(800);
    it.setMatk(800);
    it.setCHUC(25);
    it.setAllStat(0);
    it.setTotalDamage(100);
    it.setBossDamage(100);
    it.setIgnorePDR(100);
    it.setLines(3);
    it.setState(20);
    it.setPotential1(40603);
    it.setPotential2(40603);
    it.setPotential3(40603);
    it.setPotential4(40603);
    it.setPotential5(40603);
    it.setPotential6(40603);
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
        // Animation play on first click

        var msg = "　　　#i1802959# #fs11##e[Royal Maple] - SAVIOR Event#n#fs11# #i1802959#\r\n#fs11##Cblue#              ขอบคุณที่ใช้บริการ Royal Maple เสมอมา#k\r\n";
        msg += pink + "                               #L100#แนะนำกิจกรรม#fc0xFF000000##l\r\n\r\n";
        msg += "                      ระยะเวลากิจกรรม : #b02.20 ~ 03.31\r\n";
        msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        //msg += "#L1##fc0xFFFF3366#Map Event#fc0xFF000000# Warp#l\r\n";
        msg += "#L2##fc0xFFFF3366#แลกเปลี่ยน#fc0xFF000000# ไอเทมกิจกรรม#l\r\n";
        msg += "#L3##fc0xFF6600CC#ร้านค้า#fc0xFF000000# ไอเทมกิจกรรม#l\r\n";
        msg += "#L4##fc0xFF6600CC#ตรวจสอบ#fc0xFF000000# แหล่งหาไอเทมกิจกรรม#l\r\n";
        msg += "#L5##fc0xFF6600CC#รับ#fc0xFF000000# ไอเทมพิเศษ#l\r\n";
        msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        cm.sendSimple(msg);
    } else if (status == 1) {
        seld = sel;
        switch (sel) {
            case 100:
                var msg = "#fnNanumGothic Extrabold##d(ถ้าล่ามอนสเตอร์และ AFK จะมีลูกอมต่างๆ ดรอปออกมา..)#k\r\n\r\n#bต้องรีบสะสมลูกอมแล้ว!\r\n\r\n";
                msg += "#rว่าแต่เหรียญกิจกรรมเอาไว้ใช้ทำอะไรนะ..?\r\n";
                cm.sendNextS(msg, 2);
                break;

            case 1:
                cm.warp(993031000, 0);
                cm.dispose();

                break;

            case 2:
                var msg = "#fs11#" + pink + "สะสมลูกอมมาแล้วเหรอ?! แลกเป็นเหรียญ SAVIOR ให้ได้นะ!" + black + enter + enter;

                for (i = 0; i < need.length; i++) {
                    if (i != need.length - 1) msg += "#i" + need[i]['itemid'] + "##z" + need[i]['itemid'] + "# " + need[i]['qty'] + "ชิ้น และ" + enter;
                    else msg += "#fs11##i" + need[i]['itemid'] + "##z" + need[i]['itemid'] + "# " + need[i]['qty'] + "ชิ้น ถ้าให้ฉันล่ะก็\r\nจะให้ #b#i" + tocoin + "##z" + tocoin + "#" + black + " เป็นการตอบแทน!";
                }

                if (haveNeed(1))
                    cm.sendNext(msg);
                else {
                    msg += enter + enter + "แต่ว่า.. ท่านนักผจญภัยมีลูกอมไม่ครบนี่นา";
                    cm.sendOk(msg);
                    cm.dispose();
                }
                break;

            case 3:
                cm.dispose();
                cm.openShop(6666);
                break;

            case 4:
                var msg = "#fs11#";
                msg += "#fs11##b#i4317001##z4317001#\r\n#i4317002##z4317002#\r\n#i4317003##z4317003#\r\n\r\n";
                msg += "#r#e[ตกปลา]#b มีโอกาสได้รับไอเทม 3 อย่างข้างต้น\r\n";
                msg += "#r#e[ล่ามอนสเตอร์]#b มีโอกาสได้รับไอเทม 3 อย่างข้างต้น\r\n\r\n";
                msg += pink + "                   นำลูกอม 3 อย่างข้างต้น\r\n\r\n#b#i4310184# #z4310184#" + pink + " มาแลกเปลี่ยนเป็น";
                cm.sendOk(msg);
                cm.dispose();
                break;


            case 5:
                if (cm.getClient().getKeyValue("savioreventitem") == null) {
                    msg = "#fs11#ต้องการรับไอเทมพิเศษหรือไม่?\r\n\r\n#r※ ไอเทมพิเศษรับได้ 1 ครั้งต่อบัญชีเท่านั้น\r\n※ ไอเทมพิเศษมีอายุ 30 วัน และจะถูกลบเมื่อหมดอายุ";
                    msg += "\r\n\r\n < รายการไอเทมแน่นอน >\r\n\r\n";
                    msg += color + "#i" + bosang1[0] + "# #z" + bosang1[0] + "#" + enter;
                    msg += "\r\n\r\n < รายการไอเทมสุ่ม >\r\n\r\n";
                    for (i = 0; i < bosang.length; i++) {
                        msg += color + "#i" + bosang[i] + "# #z" + bosang[i] + "#" + enter;
                    }
                    cm.sendYesNo(msg);
                } else {
                    msg = "#fs11#" + black + "ได้รับ #fc0xFFFF3366#ไอเทมพิเศษ" + black + " ไปแล้ว";
                    cm.dispose();
                    cm.sendOk(msg);
                }
                break;

            case 1000:
                if (getCount() == 7) {
                    if (getClear() == null) {
                        cm.sendYesNo("#fs11#สะสมตราประทับครบแล้วเหรอ! ต้องการรับของขวัญพิเศษหรือไม่?\r\n\r\n#r※ ไอเทมพิเศษรับได้ 1 ครั้งต่อบัญชีเท่านั้น");
                        break;
                    } else {
                        msg = "#fs11#ได้รับของขวัญพิเศษไปแล้ว!";
                    }
                } else {
                    msg = "#fs11#สะสมตราประทับยังไม่ครบ..\r\n\r\n" + pink + star + " จำนวนตราประทับปัจจุบัน : " + getCount() + " / 7";
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
                cm.sendOk("#fs11#" + color + "สามารถซื้อไอเทมต่างๆ ได้ที่ร้านค้ากิจกรรมด้วย #i4310184# #z4310184#!");
                cm.dispose();
                break;

            case 2:
                temp = [];
                for (i = 0; i < need.length; i++) {
                    temp.push(Math.floor(cm.itemQuantity(need[i]['itemid']) / need[i]['qty']));
                }
                temp.sort();
                max = temp[0];
                cm.sendGetNumber("#fs11#ท่านนักผจญภัยสามารถแลกได้สูงสุด #b #i" + tocoin + "##z" + tocoin + "# " + max + "ชิ้น#k ..\r\n\r\nต้องการแลกกี่ชิ้น?", 1, 1, max);
                break;

            case 5:
                i = Packages.objects.utils.Randomizer.rand(0, 7);

                if (!cm.canHold(bosang[i], 1)) {
                    cm.sendOk("#fs11#ช่องเก็บของไม่พอ");
                    cm.dispose();
                    return;
                }

                if (!cm.canHold(bosang1[0], 1)) {
                    cm.sendOk("#fs11#ช่องเก็บของไม่พอ");
                    cm.dispose();
                    return;
                }

                msg = "#fs11#" + obtain + enter;
                msg += color + "#i" + bosang1[0] + "# #z" + bosang1[0] + "#" + enter;
                msg += color + "#i" + bosang[i] + "# #z" + bosang[i] + "#" + enter;
                MakeItem(bosang[i]);
                MakeItem1(bosang1[0]);
                cm.getClient().setKeyValue("savioreventitem", "1");
                msg += pink + "\r\nได้รับไอเทมดังต่อไปนี้";
                cm.sendOk(msg);
                cm.dispose();
                break;

        }
    } else if (status == 3) {
        switch (seld) {
            case 2:
                if (!haveNeed(sel)) {
                    cm.sendOk("#fs11#ลูกอมของท่านนักผจญภัยไม่พอ");
                    cm.dispose();
                    return;
                }
                for (i = 0; i < need.length; i++) {
                    cm.gainItem(need[i]['itemid'], -(need[i]['qty'] * sel));
                }
                cm.gainItem(tocoin, (toqty * sel));
                cm.sendOk("#fs11#การแลกเปลี่ยนเสร็จสมบูรณ์!");
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
    return cm.getClient().getKeyValue("savioreventFirst");
}