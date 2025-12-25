function enter(pi) {
    if (pi.getQuestStatus(21014) == 2 || pi.getPlayer().getJob() != 2000) {
        pi.playPortalSE();
        pi.warp(140010100, 2);
    } else {
        pi.playerMessage(5, "เมือง Rien อยู่ทางขวา ใช้พอร์ทัลทางขวาและเข้าไปในเมืองเพื่อพบกับ Lilin");
    }
}