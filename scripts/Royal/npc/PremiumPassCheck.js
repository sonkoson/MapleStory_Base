importPackage(java.lang);
var status;
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
        var chat = "#e#r<Royal Maple: Premium Season Pass>#b\r\n\r\n\r\n";
        chat += "#L1#ใช้งาน Royal Maple: Premium Season Pass";
        cm.sendSimple(chat);
    } else if (status == 1) {
        if (selection == 1) {
            if (cm.haveItem(4001760, 1)) {
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 9062284, "PremiumPass");
            } else {
                cm.sendOk("#e#rคุณไม่มี #i4001760##z4001760# ไม่สามารถเข้าใช้งานได้#b");
                cm.dispose();
                return;
            }
        } else {
            cm.dispose();
            return;
        }
    }
}
