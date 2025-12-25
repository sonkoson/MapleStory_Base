function enter(pi) {
	if (pi.getPlayer().getLevel() < 50) {
		pi.playerMessage(5, "เจ้าต้องมีเลเวลอย่างน้อย 50");
		return false;
	}
	pi.warp(950101000, 0);
	return true;
}