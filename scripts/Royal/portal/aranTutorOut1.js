function enter(pi) {
	if (pi.getQuestStatus(21000) == 0) {
		pi.playerMessage(5, "เจ้าสามารถออกไปได้หลังจากรับภารกิจจาก Athena Pierce ที่อยู่ทางขวาของเจ้าเท่านั้น");
	} else {
		pi.teachSkill(20000017, 0, -1);
		pi.teachSkill(20000018, 0, -1);
		pi.teachSkill(20000017, 1, 0);
		pi.teachSkill(20000018, 1, 0);
		pi.playPortalSE();
		pi.warp(914000200, 1);
	}
}