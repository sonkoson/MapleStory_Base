function enter(pi) {
    if (pi.getPlayer().getLevel() >= 30) {
        pi.warp(103050370, 0);
        pi.playPortalSE();
    } else {
        pi.playerMessage(5, "เจ้าต้องมีเลเวล 30");
    }
}