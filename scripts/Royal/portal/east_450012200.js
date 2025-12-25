function enter(pi) {
	if (pi.getPlayer().getLevel() >= 255) {
		pi.warp(450012300, "sp");
		return true;
	} else {
		pi.playerMessage("ต้องมีเลเวล 255 ขึ้นไปจึงจะใช้พอร์ทัลนี้ได้");
	}
}