importPackage(java.lang);
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
        var dialog = "#e#r< Royal Maple : Premium Season Pass >#b\r\n\r\n\r\n";
        dialog += "#L1#ใช้ Premium Season Pass";
        cm.sendSimple(dialog);
    } else if (status == 1) {
        if (selection == 1) {
            if (cm.haveItem(4001760, 1)) {
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 9062284, "PremiumPass");
            } else {
                cm.sendOk("#e#rเธอไม่มี #i4001760##z4001760# จึงไม่สามารถใช้งานได้นะจ๊ะ#b");
                cm.dispose();
                return;
            }
        } else {
            cm.dispose();
            return;
        }
    }
}




