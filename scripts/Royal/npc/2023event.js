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

var tocoin = 4310198, toqty = 1;

var bosang = [1002186, 1082102, 1102039, 1072153, 1703129, 1012104, 1022079, 1032024]

function MakeItem(itemid) {
    var ii = Packages.objects.item.MapleItemInformationProvider.getInstance();
    var it = ii.getEquipById(itemid);
    it.setStr(1000);
    it.setDex(1000);
    it.setInt(1000);
    it.setLuk(1000);
    it.setWatk(500);
    it.setMatk(500);
    it.setCHUC(30);
    it.setAllStat(10);
    it.setOwner("Royal Maple");
    it.setExpiration((new Date()).getTime() + (1000 * 60 * 60 * 24 * 14));
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
        // First event click, play animation
        if (getFirst() == null) {
            cm.dispose();
            cm.getClient().setKeyValue("2023eventFirst", "1");
            cm.warp(993031000, 0);
            cm.openNpcCustom(cm.getClient(), 2071002, "2023eventeffect");
            return;
        }

        var msg = "　　　#i5080000# #fs11##e[Royal Maple] - New Year Event#n#fs11# #i5080000#\r\n#fs11##Cblue#              ขอบคุณที่ใช้บริการ Royal Maple เสมอมาครับ#k\r\n";
        msg += Pink + "                               #L100#รายละเอียดกิจกรรม#fc0xFF000000##l\r\n\r\n";
        msg += "                      ระยะเวลากิจกรรม : #b01.02 ~ 01.30\r\n";
        msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        //     msg += "#L1##fc0xFFFF3366#ย้ายไปแผนที่กิจกรรม#fc0xFF000000##l\r\n";
        msg += "#L2##fc0xFFFF3366#แลกไอเท็มกิจกรรม#fc0xFF000000##l\r\n";
        msg += "#L3##fc0xFF6600CC#ร้านค้าไอเท็มกิจกรรม#fc0xFF000000##l\r\n";
        msg += "#L4##fc0xFF6600CC#ตรวจสอบแหล่งหาไอเท็มกิจกรรม#fc0xFF000000##l\r\n";
        msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        if (cm.getPlayer().getMapId() == 993031000) {
            var msg = "　　　#i5080000# #fs11##e[Royal Maple] - New Year Event#n#fs11# #i5080000#\r\n#fs11##Cblue#              ขอบคุณที่ใช้บริการ Royal Maple เสมอมาครับ#k\r\n";
            msg += Pink + "                               #L100#รายละเอียดกิจกรรม#fc0xFF000000##l\r\n\r\n";
            msg += "                      ระยะเวลากิจกรรม : #b01.02 ~ 01.30\r\n";
            msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
            msg += "#L1001##fc0xFFFF3366#ย้ายไปหมู่บ้าน Royal#fc0xFF000000##l\r\n";
            msg += "#L2##fc0xFFFF3366#แลกไอเท็มกิจกรรม#fc0xFF000000##l\r\n";
            msg += "#L3##fc0xFF6600CC#ร้านค้าไอเท็มกิจกรรม#fc0xFF000000##l\r\n";
            msg += "#L5##fc0xFF6600CC#รับไอเท็มพิเศษ#fc0xFF000000##l\r\n";

            msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        }
        cm.sendSimple(msg);
    } else if (status == 1) {
        seld = sel;
        switch (sel) {
            case 100:
                var msg = "#fnArial##d(เมื่อล่ามอนสเตอร์จะมีลูกกวาดหลากหลายชนิดดรอปมา..)#k\r\n\r\n#bต้องรีบไปล่ามอนสเตอร์แล้วรวบรวมลูกกวาดซะแล้ว!\r\n\r\n";
                msg += "#L96##rว่าแต่ ไอเท็มกิจกรรมเอาไปใช้ที่ไหนนะ..?#l\r\n";
                cm.sendNextS(msg, 2);
                break;

            case 1:
                cm.warp(993031000, 0);
                cm.dispose();

                break;

            case 2:
                var msg = "#fs11#" + Pink + "รวบรวมลูกกวาดมาแล้วเหรอ?! แลกเป็นเหรียญ Christmas Coin ได้นะ!" + Black + enter + enter;

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
                cm.openShop(2023);
                break;

            case 4:
                var msg = "#fs11#";
                msg += "#fs11##b#i4317001##z4317001#\r\n#i4317002##z4317002#\r\n#i4317003##z4317003#\r\n\r\n";
                msg += "#r#e[ตกปลา]#b มีโอกาสได้รับไอเท็มทั้ง 3 ชนิดข้างต้น\r\n";
                msg += "#r#e[ล่ามอนสเตอร์]#b มีโอกาสได้รับไอเท็มทั้ง 3 ชนิดข้างต้น\r\n\r\n";
                msg += Pink + "                   ท่านสามารถนำลูกกวาดทั้ง 3 ชนิด\r\n\r\n#b#i4310198# #z4310198#" + Pink + " ไปแลกเปลี่ยนได้";
                cm.sendOk(msg);
                cm.dispose();
                break;


            case 5:
                if (cm.getClient().getKeyValue("2023eventitem") == null) {
                    msg = "#fs11#ท่านต้องการรับไอเท็มพิเศษหรือไม่?\r\n\r\n#r※ ไอเท็มพิเศษรับได้ 1 ครั้งต่อบัญชี";
                    msg += "\r\n\r\n < รายการไอเท็มสุ่ม >\r\n\r\n";
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

            case 1000:
                if (getCount() == 7) {
                    if (getClear() == null) {
                        cm.sendYesNo("#fs11#ท่านสะสมตราประทับครบแล้ว! ต้องการรับของขวัญพิเศษหรือไม่?\r\n\r\n#r※ ไอเท็มพิเศษรับได้ 1 ครั้งต่อบัญชี";
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
                cm.sendOk("#fs11#" + Color + "ท่านสามารถซื้อไอเท็มหลากหลายได้ด้วย #i4310198# #z4310198# ในร้านค้ากิจกรรม!");
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
                i = Packages.objects.utils.Randomizer.rand(0, 7);

                if (!cm.canHold(bosang[i], 1)) {
                    cm.sendOk("#fs11#ช่องเก็บของไม่เพียงพอ");
                    cm.dispose();
                    return;
                }

                msg = "#fs11#" + GetIcon + enter;
                msg += Color + "#i" + bosang[i] + "# #z" + bosang[i] + "#" + enter;
                MakeItem(bosang[i]);
                cm.getClient().setKeyValue("2023eventitem", "1");
                msg += Pink + "\r\nได้รับไอเท็มตามรายการข้างต้นแล้ว";
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
    return cm.getClient().getKeyValue("2023eventFirst");
}