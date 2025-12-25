function enter(pi) {
	if (pi.isQuestActive(22583)) {
		pi.forceCompleteQuest(22583);
		pi.playerMessage(5, "วิญญาณอิสระถูกปลดปล่อย");
	}
	if (pi.isQuestActive(22584)) {
		pi.forceCompleteQuest(22584);
		pi.playerMessage(5, "ตัวล็อคประตูถูกทำลาย");
	}
	pi.warp(220011001, 0);
	pi.playPortalSE();
}