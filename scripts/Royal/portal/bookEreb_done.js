function enter(pi) {
    if (pi.getPlayerCount(310030210) > 0) {
        pi.getPlayer().dropMessage(-1, "มีคนกำลังใช้งานอยู่ กรุณารอสักครู่");
        pi.getPlayer().dropMessage(5, "มีคนกำลังใช้งานอยู่ กรุณารอสักครู่");
    } else {
        pi.TimeMoveMap(310030210, 304000000, 20);
        return true;
    }
}