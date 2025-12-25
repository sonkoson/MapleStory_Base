function enter(pi) {
	if (pi.isQuestActive(22588)) {
		pi.forceCompleteQuest(22588);
		pi.forceCompleteQuest(22589);
		pi.playerMessage(5, "ไปคุยกับ Olaf ที่ Lith Harbor เพื่อไปยังเกาะแห่งมังกรนิทรา!");
	}
}