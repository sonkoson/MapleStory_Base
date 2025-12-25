function enter(pi) {
    if (java.lang.Math.floor(((pi.getPlayer().getJob() % 1000) / 100) * 100 - (pi.getPlayer().getJob() % 100)) == 100) {
        pi.warp(610030510, 0);
    } else {
        pi.playerMessage(5, "เฉพาะอาชีพ Warriors เท่านั้นที่สามารถเข้าพอร์ทัลนี้ได้");
        //pi.playerMessage(5, "Your job: " + (((pi.getPlayer().getJob() % 1000) / 100) * 100 - (pi.getPlayer().getJob() % 100)));
    }
}