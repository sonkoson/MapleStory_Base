function enter(pi) {
	try {
		if (pi.haveItem(4031346)) {
			if (pi.getMapId() == 240010100) {
				pi.playPortalSE();
				pi.warp(101030100, "minar00");
			} else {
				pi.playPortalSE();
				pi.warp(240010100, "elli00");
			}
			pi.gainItem(4031346, -1);
			pi.playerMessage("Magic Seed ถูกใช้ไปแล้ว และเจ้าถูกส่งไปยังที่ไหนสักแห่ง");
			return true;
		} else {
			pi.playerMessage("จำเป็นต้องใช้ Magic Seed เพื่อผ่านพอร์ทัลนี้");
			return false;
		}
	} catch (e) {
		pi.playerMessage("Error: " + e);
	}
}
