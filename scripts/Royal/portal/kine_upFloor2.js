function enter(pi) {
	if (pi.getQuestStatus(5) == 2 && pi.getQuestStatus(4) == 2 && pi.getQuestStatus(6) == 2) {
		pi.warp(331002100);
		pi.playerMessage(5, "[ประกาศ] ไปที่ห้องเรียน 2-1!");
	} else {
		pi.playerMessage(5, "[ประกาศ] โปรดทำภารกิจก่อนหน้าให้สำเร็จก่อน");
	}
}