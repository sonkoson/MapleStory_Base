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
        cm.sendYesNo("#eยินดีต้อนรับคุณ #d#h 0##k!#b\r\n\r\nต้องการกลับหมู่บ้านหรือไม่?");
    } else if (status == 1) {
        cm.dispose();
        cm.setDeathcount(0);
        cm.warp(1000000, 0);
    }
}

