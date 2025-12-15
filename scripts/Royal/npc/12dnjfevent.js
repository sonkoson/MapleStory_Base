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
var enter = "\r\n";

importPackage(Packages.objects.item);
importPackage(Packages.constants);


var need = [
    { 'itemid': 4317001, 'qty': 1 },
    { 'itemid': 4317002, 'qty': 1 },
    { 'itemid': 4317003, 'qty': 1 }
]

var tocoin = 4310320, toqty = 1;

var bosang = [
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
        // 첫 이벤트 클릭시 애니메이션재생
        if (getFirst() == null) {
            cm.dispose();
            cm.getClient().setKeyValue("12dnjfeventFirst", "1");
            cm.warp(209000013, 0);
            cm.openNpcCustom(cm.getClient(), 2001004, "12dnjfeventeffect");
            return;
        }

        var msg = "　#i4001395# #fs11##e[Royal Maple] - Winter & Christmas Event#n#fs11# #i4001395#\r\n#fs11##Cblue#              ขอบคุณที่ใช้บริการ Royal Maple ครับ#k\r\n";
        msg += Pink + "                               #L100#รายละเอียดกิจกรรม#fc0xFF000000##l\r\n\r\n";
        msg += "                      ระยะเวลากิจกรรม : #b11.28 ~ 12.26\r\n";
        msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        msg += "#L1##fc0xFFFF3366#ไปที่แผนที่กิจกรรม#fc0xFF000000# #l\r\n";
        msg += "#L2##fc0xFFFF3366#แลกเปลี่ยนไอเท็มกิจกรรม#fc0xFF000000# #l\r\n";
        msg += "#L3##fc0xFF6600CC#ร้านค้าไอเท็มกิจกรรม#fc0xFF000000# #l\r\n";
        msg += "#L4##fc0xFF6600CC#ตรวจสอบแหล่งที่มาของไอเท็มกิจกรรม#fc0xFF000000# #l\r\n";
        msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        if (cm.getPlayer().getMapId() == 209000013) {
            var msg = "　#i4001395# #fs11##e[Royal Maple] - Winter & Christmas Event#n#fs11# #i4001395#\r\n#fs11##Cblue#              ขอบคุณที่ใช้บริการ Royal Maple ครับ#k\r\n";
            msg += Pink + "                               #L100#รายละเอียดกิจกรรม#fc0xFF000000##l\r\n\r\n";
            msg += "                      ระยะเวลากิจกรรม : #b11.28 ~ 12.26\r\n";
            msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
            msg += "#L1001##fc0xFFFF3366#ไปที่ Royal Castle#fc0xFF000000# #l\r\n";
            msg += "#L2##fc0xFF6600CC#แลกเปลี่ยนไอเท็มกิจกรรม#fc0xFF000000# #l\r\n";
            msg += "#L3##fc0xFF6600CC#ร้านค้าไอเท็มกิจกรรม#fc0xFF000000# #l\r\n";
            msg += "#L4##fc0xFF6600CC#ตรวจสอบแหล่งที่มาของไอเท็มกิจกรรม#fc0xFF000000# #l\r\n";
            msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        }
        cm.sendSimple(msg);
    } else if (status == 1) {
        seld = sel;
        switch (sel) {
            case 100:
                var msg = "#fnNanumGothic Extrabold##d(ล่ามอนสเตอร์แล้วลูกอมหลากหลายชนิดก็ดรอปลงมา..)#k\r\n\r\n#bต้องรีบไปล่ามอนสเตอร์สะสมลูกอมแล้วสิ!\r\n\r\n";
                msg += "#L96##rแต่ว่าไอเท็มกิจกรรมเอาไปใช้ทำอะไรได้นะ..?#l\r\n";
                cm.sendNextS(msg, 2);
                break;

            case 1:
                cm.warp(209000013, 0);
                cm.dispose();

                break;

            case 2:
                var msg = "#fs11#" + Pink + "สะสมลูกอมมาแล้วเหรอคะ?! ฉันสามารถแลกเป็น Christmas Coin ให้ได้นะ!" + Black + enter + enter;

                for (i = 0; i < need.length; i++) {
                    if (i != need.length - 1) msg += "#i" + need[i]['itemid'] + "##z" + need[i]['itemid'] + "# " + need[i]['qty'] + " ชิ้น กับ" + enter;
                    else msg += "#fs11##i" + need[i]['itemid'] + "##z" + need[i]['itemid'] + "# " + need[i]['qty'] + " ชิ้น\r\nถ้าคุณให้ฉัน ฉันจะให้ #b#i" + tocoin + "##z" + tocoin + "#" + Black + " แทนค่ะ!";
                }

                if (haveNeed(1))
                    cm.sendNext(msg);
                else {
                    msg += enter + enter + "แต่ว่า.. ท่านนักผจญภัยยังมีลูกอมไม่ครบนี่คะ";
                    cm.sendOk(msg);
                    cm.dispose();
                }
                break;

            case 3:
                cm.dispose();
                cm.openShop(9999);
                break;

            case 4:
                var msg = "#fs11#";
                msg += "#fs11##b#i4317001##z4317001#\r\n#i4317002##z4317002#\r\n#i4317003##z4317003#\r\n\r\n";
                msg += "#r#e[ตกปลา]#b มีโอกาสได้รับไอเท็ม 3 ชนิดข้างต้น\r\n";
                msg += "#r#e[ล่ามอนสเตอร์]#b มีโอกาสได้รับไอเท็ม 3 ชนิดข้างต้น\r\n\r\n";
                msg += Pink + "                   สามารถนำลูกอม 3 ชนิดข้างต้น\r\n\r\n#b#i4310320# #z4310320#" + Pink + " มาแลกเปลี่ยนได้";
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
                cm.sendOk("#fs11#" + Color + "สามารถซื้อไอเท็มหลากหลายได้ที่ร้านค้าไอเท็มกิจกรรมด้วย #i4310320# #z4310320# !");
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
    return cm.getClient().getKeyValue("12dnjfeventFirst");
}