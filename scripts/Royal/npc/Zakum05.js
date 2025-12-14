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
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
        talk = "เดี๋ยวก่อน!! เจ้าต้องการไปยังแท่นบูชาของ Zakum อันไหน?\r\n\r\n"
        talk += "#L0# #bNormal Zakum#l\r\n";
        talk += "#L1# #bChaos Zakum#l\r\n";
        cm.sendSimple(talk);
    } else if (status == 1) {
        if (cm.itemQuantity(4001017) == 0) {
            nameplus = selection == 0 ? "Normal" : "Chaos"
            cm.getPlayer().dropMessage(5, "ไม่สามารถเคลื่อนย้ายได้เนื่องจากไม่มีของบูชาสำหรับ " + nameplus + " Zakum");
            cm.dispose();
            return;
        }
        cm.warp(211042400 + selection, 0);
        cm.dispose();
    }
}