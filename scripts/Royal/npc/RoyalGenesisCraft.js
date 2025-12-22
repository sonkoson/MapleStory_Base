var status = -1;

var nitem = [
    [4001878, 200],// Hunting Rice Cake
    [4001879, 200],// Boss Rice Cake
    [4310308, 3000],// Neo Core
    [4031227, 3000],// Glowing Crystal
    [4310266, 3000],// Upgrade Coin
    [4001715, 3000],// 100M Currency
    [4001894, 30],
    [2591427, 3],
    [2591572, 3],
    [2591590, 3],
    [2591676, 3],
    [2591659, 3]
];
var allstat = 1000;
var msg = "#fs11#"

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {

    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        status--;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
        msg += "#fc0xFFB677FF##e[Royal] Genesis Weapon Crafting#n  \r\n\r\n"
        msg += "#fc0xFF990033#Genesis Weapon #fc0xFF191919#นำวัสดุด้านล่างมาเพื่อคราฟ:\r\n"
        for (i = 0; i < nitem.length; i++) {
            msg += "#i" + nitem[i][0] + "#  #z" + nitem[i][0] + "# " + nitem[i][1] + " ชิ้น\r\n"
        }
        msg += "\r\n";
        msg += "#fc0xFF990033#Genesis Weapon#fc0xFF191919# #fc0xFFF15F5F#ไม่สามารถเพิ่ม Star Force#fc0xFF191919#ได้\r\n\r\n"
        msg += "#fc0xFF990033#Genesis Weapon#fc0xFF191919# จะได้รับ #rออปชั่นที่ทรงพลังมาก#k\r\n\r\n"
        msg += "#fc0xFF990033#Genesis Weapon#fc0xFF191919# #bสามารถเลือก Potential ได้ทั้ง 6 บรรทัด#k\r\n\r\n"
        msg += "ถ้าต้องการคราฟ #fc0xFF990033#Genesis Weapon#fc0xFF191919# กด Yes, ถ้าไม่ต้องการกด No\r\n\r\n"
        cm.sendYesNo(msg);
    } else if (status == 1) {
        msg = "";
        if (checkitem()) {
            msg += "#fs11#วัสดุสำหรับคราฟ #z2439959# ไม่เพียงพอ\r\n\r\n"
            msg += "กรุณานำวัสดุด้านล่างมาเพิ่ม:\r\n\r\n"
            needitem();
            cm.sendOk(msg);
            cm.dispose();
            return;
        }
        for (i = 0; i < nitem.length; i++) {
            cm.gainItem(nitem[i][0], -nitem[i][1]);
        }
        cm.gainItem(2439959, 1);
        cm.worldGMMessage(21, "[Genesis] " + cm.getPlayer().getName() + " ได้สร้าง [Genesis Weapon] สำเร็จ! ขอแสดงความยินดีด้วย!");
        cm.sendOk("#fs11##i2439959##z2439959# สร้างสำเร็จแล้ว!");
        cm.dispose();
    }
}

function checkitem() {
    for (i = 0; i < nitem.length; i++) {
        if (!cm.haveItem(nitem[i][0], nitem[i][1])) {
            return true;
        }
    }
    return false;
}

function needitem() {
    for (i = 0; i < nitem.length; i++) {
        if (!cm.haveItem(nitem[i][0], nitem[i][1])) {
            msg += "#i" + nitem[i][0] + "#  #z" + nitem[i][0] + "# " + (nitem[i][1] - cm.itemQuantity(nitem[i][0])) + " ชิ้น\r\n";
        }
    }
}




