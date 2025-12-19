function enter(pi) {
    if (!pi.haveItem(4032743, 1)) {
        pi.getPlayer().dropMessage(-1, "ไม่มี Card Key ไม่สามารถขึ้นได้");
        pi.getPlayer().dropMessage(5, "ไม่มี Card Key ไม่สามารถขึ้นได้");
        return false;
    } else {
        if (pi.getPlayerCount(310030211) > 0) {
            pi.getPlayer().dropMessage(-1, "มีคนกำลังใช้งานอยู่ กรุณารอสักครู่");
            pi.getPlayer().dropMessage(5, "มีคนกำลังใช้งานอยู่ กรุณารอสักครู่");
        } else {
            pi.gainItem(4032743, -1);
            pi.TimeMoveMap(310030211, 304050000, 20);
            return true;
        }
    }
}