function enter(pi) {
	if (pi.getQuestStatus(6240) == 1 || pi.getQuestStatus(6241) == 1) {
		if (!pi.haveItem(4001113)) {
			if (pi.getPlayerCount(921100200) == 0) {
				pi.playPortalSE();
				pi.warp(921100200, 0);
				return true;
			} else {
				pi.playerMessage("ตัวละครอื่นกำลังทำภารกิจอยู่ เจ้าไม่สามารถเข้าไปได้");
			}
		} else {
			pi.playerMessage("เจ้ามีไข่ของ Phoenix แล้ว เจ้าไม่สามารถเข้าไปได้");
		}
	} else if (pi.getQuestStatus(6240) == 2 && pi.getQuestStatus(6241) == 0) {
		if (!pi.haveItem(4001113)) {
			pi.playPortalSE();
			pi.warp(921100200, 0);
			return true;
		} else {
			pi.playerMessage("เจ้ามีไข่ของ Phoenix แล้ว เจ้าไม่สามารถเข้าไปได้");
		}
	} else {
		pi.playerMessage("เจ้าไม่สามารถเข้าสู่สถานที่ที่ถูกปิดผนึกได้");
	}
	return false;
}