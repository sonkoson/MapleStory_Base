var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        var eim = cm.getPlayer().getEventInstance();
        if (eim.getProperty("stage") == "4") {
            cm.sendYesNoS("กำจัด Damien สำเร็จแล้ว. จะกลับไปที่ 'Road to Fallen World Tree' หรือไม่?", 0x26);
        } else {
            cm.sendYesNoS("ที่นี่อันตรายเกินไป. จะยอมแพ้การต่อสู้และออกไปข้างนอกหรือไม่?", 0x26);
        }
    } else if (status == 1) {
        cm.warp(105300303, 1);
        cm.dispose();
    }
}