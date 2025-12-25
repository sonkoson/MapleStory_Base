// Captain Battleship quest

function enter(pi) {
	var pt = pi.getEventManager("KyrinTrainingGroundC");
	if (pt == null) {
		pi.warp(120000101, 0);
	} else {
		if (pt.getInstance("KyrinTrainingGroundC").getTimeLeft() < 120000) { // 2 minutes left
			pi.warp(912010200, 0);
		} else {
			pi.playerMessage("โปรดอดทนต่อการโจมตีของ Kyrin อีกสักครู่!");
			return false;
		}
	}
	return true;
}