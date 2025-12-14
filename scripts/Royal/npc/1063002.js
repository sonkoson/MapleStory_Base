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
        cm.sendYesNo("#fnArial##d(เห็นอะไรบางอย่างเปล่งประกายอยู่หลังกองดอกไม้สีขาวนั่น?...)#k\r\n\r\nต้องการจะเด็ด #bดอกไม้สีขาว#k ที่เปล่งประกายนั่นไปไหม?");
    } else if (status == 1) {
        cm.sendNextS("#fnArial##bดีล่ะ!.. ทีนี้ก็กลับไปหาท่าน #fs14#Krishrama#fs12# กันเถอะ#k", 2);
    } else if (status == 2) {
        cm.warp(ServerConstants.TownMap, 0);
        cm.gainItem(4034685, 1);
        cm.dispose();
    }
}
