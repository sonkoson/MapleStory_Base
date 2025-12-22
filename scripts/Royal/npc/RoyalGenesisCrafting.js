var status = -1;

var nitem = [
    [4001878, 200], // Hunt Loot
    [4001879, 200], // Boss Loot
    [4310308, 3000], // Neo Core
    [4031227, 3000], // Brilliant Light Crystal
    [4310266, 3000], // Upgrade Coin
    [4001715, 3000], // 100M Currency
    [4001894, 30],
    [2591427, 3],
    [2591572, 3],
    [2591590, 3],
    [2591676, 3],
    [2591659, 3]
];
var allstat = 1000;
var talk = "#fs11#"

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
        talk += "#fc0xFFB677FF##e[Royal] สร้างอาวุธ Genesis#n  \r\n\r\n"
        talk += "#fc0xFF990033#สำหรับการสร้างอาวุธ Genesis #fc0xFF191919#กรุณานำวัตถุดิบดังนี้มา\r\n"
        for (i = 0; i < nitem.length; i++) {
            talk += "#i" + nitem[i][0] + "#  #z" + nitem[i][0] + "# " + nitem[i][1] + " ชิ้น\r\n"
        }
        talk += "\r\n";
        talk += "#fc0xFF990033#อาวุธ Genesis#fc0xFF191919# #fc0xFFF15F5F#ไม่สามารถตีบวก Star Force ได้#fc0xFF191919#\r\n\r\n"
        talk += "#fc0xFF990033#อาวุธ Genesis#fc0xFF191919# มี #rออพชั่นที่ทรงพลังมาก#k\r\n\r\n"
        talk += "#fc0xFF990033#อาวุธ Genesis#fc0xFF191919# สามารถ #bเลือกศักยภาพได้ทั้ง 6 แถว#k\r\n\r\n"
        talk += "กด 'ใช่' เพื่อสร้างอาวุธ Genesis หรือกด 'ไม่' เพื่อยกเลิก\r\n\r\n"
        cm.sendYesNo(talk);
    } else if (status == 1) {
        talk = "";
        if (checkitem()) {
            talk += "#fs11#วัตถุดิบไม่เพียงพอสำหรับสร้าง #z2439959#\r\n\r\n"
            talk += "กรุณารวบรวมไอเท็มดังนี้เพิ่มเติม\r\n\r\n"
            needitem();
            cm.sendOk(talk);
            cm.dispose();
            return;
        }
        for (i = 0; i < nitem.length; i++) {
            cm.gainItem(nitem[i][0], -nitem[i][1]);
        }
        cm.gainItem(2439959, 1);
        cm.worldGMMessage(21, "[Genesis] " + cm.getPlayer().getName() + " ได้ทำการสร้าง [Genesis Weapon] สำเร็จ! ขอแสดงความยินดีด้วย!");
        cm.sendOk("#fs11#สร้าง #i2439959##z2439959# สำเร็จแล้ว!");
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
            talk += "#i" + nitem[i][0] + "#  #z" + nitem[i][0] + "# " + (nitem[i][1] - cm.itemQuantity(nitem[i][0])) + " ชิ้น\r\n";
        }
    }
}




