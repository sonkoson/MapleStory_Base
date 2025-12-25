function enter(pi) {
	if (pi.isQuestActive(22010) || pi.getPlayer().getJob() != 2001) {
		pi.warp(100030310);
	} else {
		pi.playerMessage("ไม่สามารถเข้าสู่ Lush Forest ได้โดยไม่มีเหตุผล");
	}
	return true;
}