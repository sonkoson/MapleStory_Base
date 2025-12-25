function enter(pi) {
	if (!pi.isLeader()) {
		pi.playerMessage(5, "หัวหน้าปาร์ตี้เท่านั้นที่สามารถดำเนินการไปยังด่านถัดไปได้");
	} else {
		if (pi.getMap().getAllMonstersThreadsafe().size() != 0) {
			pi.playerMessage(5, "เจ้าต้องกำจัดมอนสเตอร์ทั้งหมดเพื่อไปยังด่านถัดไป");
			return;
		}
		if (((pi.getMapId() % 10) | 0) == 4) { //last stage
			if (!pi.getPlayer().isGM()) {
				if (pi.getMap().getReactorByName("switch0").getState() < 1 || pi.getMap().getReactorByName("switch1").getState() < 1) {
					pi.playerMessage(5, "เจ้าต้องเปิดใช้งานสวิตช์ทั้งหมดเพื่อไปยังด่านถัดไป");
					return;
				}
			}
			var bossroom = pi.getMapId() + 66;//90-14 = 76, 90-24=66
			if (((bossroom % 100) | 0) != 90) {
				bossroom += 10;
			}
			pi.warpParty(bossroom, 0);
		} else {
			pi.warpParty(pi.getMapId() + 1, ((pi.getMapId() % 10) | 0) == 3 ? 1 : 2);
		}
	}
}
