var enter = "\r\n";
var seld = -1;

starBlue = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
starYellow = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
starWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
starBrown = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
starRed = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
starBlack = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
starPurple = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
star = "#fUI/FarmUI.img/objectStatus/star/whole#";
S = "#fUI/CashShop.img/CSEffect/today/0#"
color = "#fc0xFF6600CC#"

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
        var msg = "#fs11#พวกเรานักบวชแห่งกาลเวลากำลังสำรวจพื้นที่ Royal Maple ที่ถูกย้อมด้วยแสงสายรุ้ง คุณมีลูกแก้วและชิ้นส่วนที่ถูกย้อมด้วยแสงสายรุ้งหรือไม่?" + enter;

        for (i = 0; i < need.length; i++) {
            if (i != need.length - 1) msg += "#i" + need[i]['itemid'] + "##z" + need[i]['itemid'] + "# " + need[i]['qty'] + " ชิ้นและ" + enter;
            else msg += "#fs11##i" + need[i]['itemid'] + "##z" + need[i]['itemid'] + "# " + need[i]['qty'] + " ชิ้น ถ้าคุณให้เรา จะเป็นประโยชน์ต่อการสำรวจอย่างมาก แลกเปลี่ยน ฉันจะให้ #b#i" + tocoin + "##z" + tocoin + "##k แก่คุณ\r\n" + star + color + " ลูกแก้วได้จาก Daily Quest, ชิ้นส่วนได้จาก Boss ระดับสูง" + enter;
        }

        if (haveNeed(1))
            cm.sendNext(msg);
        else {
            msg += enter + enter + "แต่... ดูเหมือนคุณไม่มีไอเท็มที่สามารถแลกเปลี่ยนได้...";
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
        cm.sendGetNumber("คุณสามารถแลกเปลี่ยนได้สูงสุด #b" + max + " ชิ้น#k\r\nต้องการแลกเปลี่ยนกี่ชิ้น?", 1, 1, max);
    } else if (status == 2) {
        if (!haveNeed(sel)) {
            cm.sendOk("ไอเท็มที่คุณมีไม่เพียงพอ");
            cm.dispose();
            return;
        }
        for (i = 0; i < need.length; i++) {
            cm.gainItem(need[i]['itemid'], -(need[i]['qty'] * sel));
        }
        cm.gainItem(tocoin, (toqty * sel));
        cm.sendOk("ได้รับ #z4310249# แล้ว");
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
