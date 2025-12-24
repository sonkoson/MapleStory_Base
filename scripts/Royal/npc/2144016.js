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
        cm.dispose();
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        cm.sendYesNo("#fs11##fc0xFF6600CC#ต้องการจบการต่อสู้และกลับสู่เมืองหรือไม่?");
    } else if (status == 1) {
        cm.warp(272020110, 0);
        cm.dispose();
    }
}