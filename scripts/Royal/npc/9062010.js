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
        cm.sendOk("สวัสดี! ฉันคือ #bMr. New Name#k ฉันสามารถเปลี่ยนชื่อให้เธอได้ มีอะไรให้ช่วยไหม?\r\n\r\n#b#L0# เปลี่ยนชื่อตัวละคร\r\n#L1# จบการสนทนา");
    } else if (status == 1) {
        if (selection == 0) {
            if (!cm.haveItem(4034803)) {
                cm.renameResult(3); // No Rename Coupon
                cm.dispose();
                return;
            }
            cm.openRenameUI();
            cm.dispose();
        } else if (selection == 1) {
            cm.dispose();
        }
    }
}