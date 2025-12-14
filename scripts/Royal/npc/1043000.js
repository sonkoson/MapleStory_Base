var status = -1;

importPackage(Packages.constants);

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
        cm.sendYesNo("#fs11#มีดอกไม้ #dเปล่งประกาย#k อยู่หลัง #dกองดอกไม้#k\r\nคุณต้องการหยิบ #bดอกไม้#k ไปหรือไม่?");
    } else if (status == 1) {
        cm.sendNextS("#bเอาล่ะ, ทีนี้กลับไปหา #fs14#John#fs12# กันดีไหม..?#k", 2);
    } else if (status == 2) {
        cm.warp(ServerConstants.TownMap, 0);
        cm.gainItem(4033970, 1);
        cm.dispose();

    }
}
