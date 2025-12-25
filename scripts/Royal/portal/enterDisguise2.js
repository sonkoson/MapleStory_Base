function enter(pi) {
	if (pi.getQuestStatus(20301) == 1 ||
		pi.getQuestStatus(20302) == 1 ||
		pi.getQuestStatus(20303) == 1 ||
		pi.getQuestStatus(20304) == 1 ||
		pi.getQuestStatus(20305) == 1) {
		if (pi.getPlayerCount(913002100) == 0) {
			if (pi.haveItem(4032179, 1)) {
				pi.removeNpc(913002100, 1104102);
				var map = pi.getMap(913002100);
				map.killAllMonsters(false);
				map.spawnNpc(1104102, new java.awt.Point(3307, 88));
				pi.warp(913002100, 0);
			} else {
				pi.playerMessage("เจ้าไม่มีหมายค้น Erev กรุณาไปรับจาก Nineheart");
			}
		} else {
			pi.playerMessage("ป่าแห่งนี้กำลังถูกค้นหาโดยคนอื่นอยู่ กรุณากลับมาใหม่ภายหลัง");
		}
	} else {
		pi.warp(130010020, "out00");
	}
}