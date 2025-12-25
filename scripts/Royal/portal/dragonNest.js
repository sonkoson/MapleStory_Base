function enter(pi) {
	if (pi.haveItem(4001094)) {
		if (pi.getQuestStatus(3706) > 0) {
			if (pi.getPlayerCount(240040611) == 0) {
				pi.removeNpc(240040611, 2081008);
				pi.resetMap(240040611);
				pi.playPortalSE();
				pi.warp(240040611, "sp");
			} else {
				pi.playerMessage(5, "มีคนอื่นอยู่ข้างในกำลังทำภารกิจ กรุณาลองใหม่ในภายหลัง");
			}
		} else {
			pi.playerMessage(5, "เจ้ายังไม่ได้เริ่มภารกิจ กรุณาลองใหม่ในภายหลัง");
		}
	} else {
		pi.playerMessage(5, "ในการเข้าสู่พื้นที่นี้ เจ้าต้องมีไข่ของ Nine Spirit");
	}
}