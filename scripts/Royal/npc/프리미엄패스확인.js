importPackage(java.lang);
var dialogStatus;
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
        dialogStatus++;
    }
    if (dialogStatus == 0) {
        var dialog = "#e#r<โรยัลเมเปิล : พาสซีซั่นพรีเมี่ยม>#b\\r\\n\\r\\n\\r\\n";
        dialog += "#L1#ใช้พาสซีซั่นพรีเมี่ยม";
        cm.sendSimple(dialog);
    } else if (dialogStatus == 1) {
        if (selection == 1) {
            if (cm.haveItem(4001760, 1)) {
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 9062284, "PremiumPass");
            } else {
                cm.sendOk("#e#r#i4001760##z4001760#ไม่มี คุณไม่สามารถเข้าสู่ระบบได้#b");
                cm.dispose();
                return;
            }
        } else {
            cm.dispose();
            return;
        }
    }
}
