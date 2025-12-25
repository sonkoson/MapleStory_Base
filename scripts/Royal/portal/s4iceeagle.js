function enter(pi) {
	if (pi.getQuestStatus(6242) == 1 || pi.getQuestStatus(6243) == 1) {
		if (!pi.haveItem(4001114)) {
			if (pi.getPlayerCount(921100200) == 0) {
				pi.playPortalSE();
				pi.warp(921100210, 0);
				return true;
			} else {
				pi.playerMessage("ตัวละครอื่นกำลังทำภารกิจอยู่ เจ้าไม่สามารถเข้าไปได้");
			}
		} else {
			pi.playerMessage("เจ้าไม่มีไข่ของ Freezer เจ้าไม่สามารถเข้าไปได้");
		}
	} else if (pi.getQuestStatus(6242) == 2 && pi.getQuestStatus(6243) == 0) {
		if (!pi.haveItem(4001114)) {
			pi.playPortalSE();
			pi.warp(921100210, 0);
			return true;
		} else {
			pi.playerMessage("เจ้าไม่มีไข่ของ Freezer เจ้าไม่สามารถเข้าไปได้");
		}
	} else {
		pi.playerMessage("เจ้าไม่สามารถเข้าสู่สถานที่ที่ถูกปิดผนึกได้");
	}
	return false;
}