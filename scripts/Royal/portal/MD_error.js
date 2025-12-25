var baseid = 261020300;
var dungeonid = 261020301;
var dungeons = 39;

function enter(pi) {
	if (pi.getMapId() == baseid) {
		if (pi.getParty() != null) {
			if (pi.isLeader()) {
				for (var i = 0; i < dungeons; i++) {
					if (pi.getPlayerCount(dungeonid + i) == 0) {
						pi.warpParty(dungeonid + i);
						return;
					}
				}
			} else {
				pi.playerMessage(5, "เจ้าไม่ใช่หัวหน้าปาร์ตี้");
			}
		} else {
			for (var i = 0; i < dungeons; i++) {
				if (pi.getPlayerCount(dungeonid + i) == 0) {
					pi.warp(dungeonid + i);
					return;
				}
			}
		}
		pi.playerMessage(5, "มินิดันเจี้ยนทั้งหมดกำลังถูกใช้งาน กรุณาลองใหม่ในภายหลัง");
	} else {
		pi.playPortalSE();
		pi.warp(baseid, "MD00");
	}
}