var baseid = 683000000;
var dungeonid = 683000100;
var dungeons = 10;

function enter(pi) {
	try {
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
	} catch (e) {
		pi.playerMessage("Error: " + e);
	}
}