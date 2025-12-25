var baseid = 221040000;
var dungeonid = 221040001;

function enter(pi) {
	try {
		if (pi.getMapId() == baseid) {
			if (pi.getParty() != null) {
				if (pi.isLeader()) {
					if (pi.getPlayerCount(dungeonid) == 0) {
						pi.warpParty(dungeonid);
						return;
					}
				} else {
					pi.playerMessage(5, "เจ้าไม่ใช่หัวหน้าปาร์ตี้");
				}
			} else {
				if (pi.getPlayerCount(dungeonid) == 0) {
					pi.warp(dungeonid);
					return;
				}
			}
			pi.playerMessage(5, "มินิดันเจี้ยนทั้งหมดกำลังถูกใช้งาน กรุณาลองใหม่ในภายหลัง");
		} else {
			pi.playPortalSE();
			pi.warp(baseid, "MD00");
		}
	} catch (e) {
		pi.playerMessage("Error: " + e);
	}
}