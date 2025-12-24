var status = -1;

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
        cm.askCustomMixHairAndProb("กรุณาเลือกค่าที่ต้องการ");
    } else if (status == 1) {
        cm.sendOkS("ดำเนินการเรียบร้อยแล้ว", 0x24);
        cm.dispose();
    }
}

