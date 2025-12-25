function enter(pi) {
	if (pi.getQuestStatus(2) == 2) {
		pi.warp(331002000);
		pi.playerMessage(5, "[ประกาศ] คุยกับ NPC ในบริเวณนี้และสำรวจโรงเรียน");
	} else {
		pi.playerMessage(5, "[ประกาศ] โปรดทำภารกิจก่อนหน้าให้สำเร็จก่อน");
	}
}