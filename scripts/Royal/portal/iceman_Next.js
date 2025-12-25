function enter(pi) {
	try {
		if (pi.getPlayer().getParty() != null && pi.getMap().getMonsterById(9300438) == null && pi.isLeader()) {
			//if (pi.getPlayer().getEventInstance() != null) {
			//	pi.warpParty_Instanced(((pi.getPlayer().getMapId() / 100) + 1) * 100 - (pi.getPlayer().getMapId() % 100));
			//} else {
			pi.warpParty(((pi.getPlayer().getMapId() / 100) + 1) * 100 - (pi.getPlayer().getMapId() % 100));
			//}
			pi.playPortalSE();
		} else {
			pi.playerMessage(5, "กรุณาให้หัวหน้าปาร์ตี้เข้ามาในพอร์ทัลนี้ และตรวจสอบว่า Ice Knight อยู่ที่นี่");
		}
	} catch (e) {
		pi.playerMessage(5, "Error: " + e);
	}
}