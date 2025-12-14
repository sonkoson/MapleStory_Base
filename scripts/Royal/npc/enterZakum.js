function start() {
    status = -1;
    action(1, 0, 0);
    cm.sendSimple("เดี๋ยวก่อน ท่านต้องการเดินทางไปที่แท่นบูชา Zakum ระดับใด?\r\n#b" +
        "#L211042400#Normal Zakum#l\r\n" +
        "#L211042401#Chaos Zakum#l\r\n");
}

function action(M, T, S) {
    if (M != 1) {
        cm.dispose();
        return;
    }

    if (M == 1)
        status++;
    else
        status--;

    if (status == 1) {
        S0 = S;
        needItem = (S0 == 211042402) ? 4001796 : 4001017;
        getZakum = (S0 == 211042401) ? "Chaos " : "Normal ";
        if (!cm.haveItem(needItem)) {
            cm.getPlayer().dropMessage(5, "ไม่สามารถเดินทางไปได้เนื่องจากไม่มีเครื่องบรรณาการสำหรับ " + getZakum + "Zakum");
            cm.dispose();
            return;
        }
        cm.dispose();
        cm.warp(S0);
    }
}