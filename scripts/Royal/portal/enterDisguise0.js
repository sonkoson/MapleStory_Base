function enter(pi) {
	if (pi.getJob() >= 1000) {
		if (pi.haveItem(4032179)) { // Search warrent
			pi.playerMessage("การค้นหา Erev เริ่มขึ้นแล้ว");
		}
		pi.playPortalSE();
		pi.warp(130010000, 3);
	} else {
		pi.playerMessage("เฉพาะอัศวินแห่ง Cygnus เท่านั้นที่เข้าร่วมได้");
	}
}