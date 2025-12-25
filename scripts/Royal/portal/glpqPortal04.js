function enter(pi) {
    if (java.lang.Math.floor(((pi.getPlayer().getJob() % 1000) / 100) * 100 - (pi.getPlayer().getJob() % 100)) == 500) {
        pi.warp(610030550, 0);
    } else {
        pi.playerMessage(5, "เฉพาะอาชีพ Pirates เท่านั้นที่สามารถเข้าพอร์ทัลนี้ได้");
    }
}