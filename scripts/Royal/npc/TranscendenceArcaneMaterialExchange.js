var enter = "\r\n";
var seld = -1;

var pearl = "#fMap/MapHelper.img/weather/starPlanet/7#";
var blue = "#fMap/MapHelper.img/weather/starPlanet/8#";
var starBlue = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
var starYellow = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
var starWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
var starBrown = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
var starRed = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
var starBlack = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
var starPurple = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
var star = "#fUI/FarmUI.img/objectStatus/star/whole#";
var sEffect = "#fUI/CashShop.img/CSEffect/today/0#"
var colorPurple = "#fc0xFF6600CC#"

var need = [
    { 'itemid': 4038001, 'qty': 10 },
    { 'itemid': 4038000, 'qty': 1 }
]
var tocoin = 4318000, toqty = 1;

function start() {
    status = -1;
    action(1, 0, 0);
}
function action(mode, type, sel) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }
    if (status == 0) {
        var msg = "#fs11#เหล่าบาทหลวงแห่งเวลาของเรากำลังตรวจสอบพื้นที่ Royal Maple ที่ถูกย้อมด้วยสีรุ้ง คุณมีลูกแก้วหรือชิ้นส่วนที่ถูกย้อมด้วยสีรุ้งบ้างไหม?" + enter;

        for (i = 0; i < need.length; i++) {
            if (i != need.length - 1) msg += "#i" + need[i]['itemid'] + "##z" + need[i]['itemid'] + "# " + need[i]['qty'] + " ชิ้น และ" + enter;
            else msg += "#fs11##i" + need[i]['itemid'] + "##z" + need[i]['itemid'] + "# " + need[i]['qty'] + " ชิ้น ถ้าคุณให้ฉัน มันจะเป็นประโยชน์ต่อการตรวจสอบมาก ฉันจะให้ #b#i" + tocoin + "##z" + tocoin + "##k เป็นสิ่งตอบแทน\r\n" + star + colorPurple + " ลูกแก้วหาได้จากภารกิจรายวัน ส่วนชิ้นส่วนน่าจะหาได้จากบอสระดับสูงนะ?" + enter;
        }

        if (haveNeed(1))
            cm.sendNext(msg);
        else {
            msg += enter + enter + "แต่ว่า.. ดูเหมือนคุณจะไม่มีไอเท็มที่สามารถแลกเปลี่ยนได้นะ..";
            cm.sendOk(msg);
            cm.dispose();
        }
    } else if (status == 1) {
        temp = [];
        for (i = 0; i < need.length; i++) {
            temp.push(Math.floor(cm.itemQuantity(need[i]['itemid']) / need[i]['qty']));
        }
        temp.sort();
        max = temp[0];
        cm.sendGetNumber("คุณสามารถแลกเปลี่ยนได้สูงสุด #b" + max + " ครั้ง#k..\r\nคุณต้องการแลกเปลี่ยนกี่ครั้ง...?", 1, 1, max);
    } else if (status == 2) {
        if (!haveNeed(sel)) {
            cm.sendOk("คุณมีไอเท็มไม่เพียงพอ");
            cm.dispose();
            return;
        }
        for (i = 0; i < need.length; i++) {
            cm.gainItem(need[i]['itemid'], -(need[i]['qty'] * sel));
        }
        cm.gainItem(tocoin, (toqty * sel));
        cm.sendOk("ได้รับ #z4310249# เรียบร้อยแล้ว");
        cm.dispose();
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
