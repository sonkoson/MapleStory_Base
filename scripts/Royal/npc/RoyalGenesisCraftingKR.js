var status = -1;

var requiredItemList = [
    [4001878, 200], // Arcane River Droplet Stone (Hunting)
    [4001879, 200], // Butterfly Soul Stone (Boss)
    [4310308, 3000], // Neo Core
    [4031227, 3000], // Brilliant Soul Crystal
    [4310266, 3000], // Promotion Coin
    [4001715, 3000], // 100 Million Meso Coin
    [4001894, 30],
    [2591427, 3],
    [2591572, 3],
    [2591590, 3],
    [2591676, 3],
    [2591659, 3]
];
var allStatBonus = 1000;
var message = "#fs11#";

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
        message += "#fc0xFFB677FF##e[Royal] ผลิตอาวุธ Genesis#n  \r\n\r\n";
        message += "#fc0xFF990033#อาวุธ Genesis #fc0xFF191919#กรุณานำวัตถุดิบเหล่านี้มาเพื่อใช้สร้างนะจ๊ะ\r\n";
        for (var i = 0; i < requiredItemList.length; i++) {
            message += "#i" + requiredItemList[i][0] + "#  #z" + requiredItemList[i][0] + "# " + requiredItemList[i][1] + " ชิ้น\r\n";
        }
        message += "\r\n";
        message += "#fc0xFF990033#อาวุธ Genesis#fc0xFF191919# นั้น #fc0xFFF15F5F#ไม่สามารถเสริมพลัง Star Force ได้#fc0xFF191919# นะ\r\n\r\n";
        message += "#fc0xFF990033#อาวุธ Genesis#fc0xFF191919# จะได้รับ #rออฟชั่นที่แข็งแกร่งมากๆ#k เลยล่ะ\r\n\r\n";
        message += "#fc0xFF990033#อาวุธ Genesis#fc0xFF191919# เธอสามารถเลือก #bPotential ได้ครบทั้ง 6 แถว#k เลยนะ\r\n\r\n";
        message += "#fc0xFF990033#ถ้าเธอต้องการสร้างอาวุธ Genesis#fc0xFF191919# ให้กด 'ตกลง' แต่ถ้าไม่ต้องการให้กด 'ยกเลิก' นะจ๊ะ\r\n\r\n";
        cm.sendYesNo(message);
    } else if (status == 1) {
        message = "";
        if (checkRequiredItems()) {
            message += "#fs11#ดูเหมือนวัตถุดิบสำหรับสร้าง #z2439959# ของเธอจะยังไม่พอสินะ\r\n\r\n";
            message += "รบกวนเธอช่วยไปสะสมไอเทมเหล่านี้มาเพิ่มหน่อยนะจ๊ะ\r\n\r\n";
            appendMissingItems();
            cm.sendOk(message);
            cm.dispose();
            return;
        }
        for (var i = 0; i < requiredItemList.length; i++) {
            cm.gainItem(requiredItemList[i][0], -requiredItemList[i][1]);
        }
        cm.gainItem(2439959, 1);
        cm.worldGMMessage(21, "[Genesis] คุณ " + cm.getPlayer().getName() + " ได้สร้าง [อาวุธ Genesis] สำเร็จแล้ว! มาร่วมยินดีกันเถอะ!");
        cm.sendOk("#fs11##i2439959# สร้าง #z2439959# เรียบร้อยแล้วจ้า!");
        cm.dispose();
    }
}

function checkRequiredItems() {
    for (var i = 0; i < requiredItemList.length; i++) {
        if (!cm.haveItem(requiredItemList[i][0], requiredItemList[i][1])) {
            return true;
        }
    }
    return false;
}

function appendMissingItems() {
    for (var i = 0; i < requiredItemList.length; i++) {
        if (!cm.haveItem(requiredItemList[i][0], requiredItemList[i][1])) {
            message += "#i" + requiredItemList[i][0] + "#  #z" + requiredItemList[i][0] + "# " + (requiredItemList[i][1] - cm.itemQuantity(requiredItemList[i][0])) + " ชิ้น\r\n";
        }
    }
}




