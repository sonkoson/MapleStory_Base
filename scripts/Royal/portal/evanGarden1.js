function enter(pi) {
	if (pi.isQuestActive(22008)) {
		pi.warp(100030103, "west00");
	} else {
		pi.playerMessage("เจ้าไม่สามารถไปที่ Back Yard ได้โดยไม่มีเหตุผล");
	}
	return true;
}  