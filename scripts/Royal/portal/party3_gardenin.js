function enter(pi) {
	if (pi.getPlayer().getParty() != null && pi.isLeader() && pi.haveItem(4001055, 1)) {
		pi.warpParty(920010100);
		pi.playPortalSE();
	} else {
		pi.playerMessage(5, "โปรดให้หัวหน้าปาร์ตี้เข้าพอร์ทัลนี้ และตรวจสอบให้แน่ใจว่าเจ้ามี Root of Life");
	}
}