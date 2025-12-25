function enter(pi) {
	if (pi.getQuestStatus(20601) == 1 || pi.getQuestStatus(20602) == 1 || pi.getQuestStatus(20603) == 1 || pi.getQuestStatus(20604) == 1 || pi.getQuestStatus(20605) == 1) {
		if (pi.getPlayerCount(913010200) == 0) {
			var map = pi.getMap(913010200);
			map.killAllMonsters(false);
			map.respawn(true);
			pi.warp(913010200, 0);
		} else {
			pi.playerMessage("มีคนกำลังพยายามจัดการบอสอยู่แล้ว กรุณากลับมาใหม่ภายหลัง");
		}
	} else {
		pi.playerMessage("ทางเดียวที่จะเข้าสู่ Hall #3 ได้คือเมื่อกำลังฝึกฝนทักษะเลเวล 100 เท่านั้น");
	}
}