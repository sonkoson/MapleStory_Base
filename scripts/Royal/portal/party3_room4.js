function enter(pi) {
	if (pi.getPlayer().getParty() != null && pi.isLeader()) {
		pi.warpParty(920010500);
		pi.playPortalSE();
	} else {
		pi.playerMessage(5, "โปรดให้หัวหน้าปาร์ตี้เข้าพอร์ทัลนี้");
	}
}