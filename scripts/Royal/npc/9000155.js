var status = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1)
        status++;
    else
        status--;
    if (status == 0) {
        if (cm.inBoss()) {
            cm.getPlayer().dropMessage(5, "ไม่สามารถใช้งานได้ขณะต่อสู้กับบอส");
            cm.dispose();
            return;
        }

        cm.dispose();
        cm.openNpcCustom(cm.getClient(), 9000155, "Gachapon");
    }
}