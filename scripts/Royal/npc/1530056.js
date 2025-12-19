importPackage(java.text);
importPackage(java.lang);

var status = -1;
var nf = NumberFormat.getInstance();

function start() {
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
        if (cm.inBoss()) {
            cm.getPlayer().dropMessage(5, "ไม่สามารถใช้งานได้ขณะอยู่ในบอส");
            cm.dispose();
            return;
        }

        choose = "#fs11##b#i4021031##z4021031##fc0xFF000000# ดูเหมือนจะเป็นตู้กดที่สามารถสุ่มได้หลากหลายอย่าง จะสุ่มอะไรดีล่ะ?\r\n\r\n";
        choose += "#L31##bสุ่มเก้าอี้#l\r\n";
        choose += "#L32##bสุ่มพาหนะ#l\r\n";
        choose += "#L33##bสุ่ม Damage Skin#l\r\n";
        choose += "#L34##bสุ่มตำแหน่ง#l\r\n";
        choose += "#L35##bสุ่มเหรียญตรา#l";
        cm.sendSimple(choose);

    } else if (status == 1) {
        var s = selection;
        cm.dispose();
        if (s == 33) {
            cm.dispose();
            cm.openNpcCustom(cm.getClient(), 1052014, "Damageskin");
        } else if (s == 2) {
            //InterServerHandler.EnterCS(cm.getClient(), cm.getPlayer(), false);
        } else if (s == 31) {
            cm.dispose();
            cm.openNpcCustom(cm.getClient(), 1052014, "Chair");
        } else if (s == 32) {
            cm.dispose();
            cm.openNpcCustom(cm.getClient(), 1052014, "Riding");
        } else if (s == 33) {
            cm.dispose();
            cm.openNpcCustom(cm.getClient(), 1052014, "Damageskin");
        } else if (s == 34) {
            cm.dispose();
            cm.openNpcCustom(cm.getClient(), 1052014, "title");
        } else if (s == 35) {
            cm.dispose();
            cm.openNpcCustom(cm.getClient(), 1052014, "hoonjang");
        } else if (s == 36) {
            cm.openShop(9031015);
        } else if (s == 40) {
            cm.openNpc(1530330);
        } else if (s == 41) {
            cm.openShop(9001212);
        } else if (s == 42) {
            cm.openShop(1540105);
        } else if (s == 43) {
            cm.openShop(9031003);
        } else if (s == 8) {
            cm.openNpc(1530707);
        } else if (s == 9) {
            cm.openNpc(1540873);
        } else if (s == 10) {
            cm.openNpc(1530110);
        } else if (s == 11) {
            cm.openNpc(2040047);
        } else if (s == 12) {
            cm.openNpc(2040048);
        } else if (s == 13) {
            cm.openNpc(1540105);
        } else if (s == 14) {
            cm.openNpc(2040045);
        } else if (s == 15) {
            cm.openNpc(9001008);
        } else if (s == 16) {
            cm.openNpc(9001009);
        } else if (s == 17) {
            cm.openNpc(2040040);
        } else if (s == 20) {
            cm.openNpc(1530706);
        } else if (s == 21) {
            cm.openNpc(1540321);
        } else if (s == 22) {
            cm.openNpc(1540205);
        } else if (s == 23) {
            cm.openNpc(3003541);
        }
    }
}
