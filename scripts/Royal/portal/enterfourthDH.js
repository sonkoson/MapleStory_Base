function enter(pi) {
	if (pi.getQuestStatus(20611) == 1 || pi.getQuestStatus(20612) == 1 || pi.getQuestStatus(20613) == 1 || pi.getQuestStatus(20614) == 1 || pi.getQuestStatus(20615) == 1) {
		if (pi.getPlayerCount(913020300) == 0) {
			var map = pi.getMap(913020300);
			map.killAllMonsters(false);
			map.respawn(true);
			pi.warp(913020300, 0);
		} else {
			pi.playerMessage("มีคนกำลังพยายามจัดการบอสอยู่แล้ว กรุณากลับมาใหม่ภายหลัง");
		}
	} else {
		pi.playerMessage("Hall #4 เข้าได้เฉพาะผู้ที่กำลังฝึกฝนทักษะเลเวล 110 เท่านั้น");
	}
}