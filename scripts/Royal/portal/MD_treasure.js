var baseid = 251010402;
var dungeonid = 251010410;
var dungeons = 30;

function enter(pi) {
	if (pi.getMapId() == baseid) {
		if (pi.getPlayer().getFame() < 10) {
			pi.playerMessage(5, "เจ้าต้องมีชื่อเสียง 10 แต้มเพื่อเข้าสู่พื้นที่");
			return;
		}
		if (pi.getParty() != null) {
			if (pi.isLeader()) {
				for (var i = 0; i < dungeons; i++) {
					if (pi.getPlayerCount(dungeonid + i) == 0) {
						pi.warpParty(dungeonid + i);
						return true;
					}
				}
			} else {
				pi.playerMessage(5, "เจ้าไม่ใช่หัวหน้าปาร์ตี้");
				return false;
			}
		} else {
			for (var i = 0; i < dungeons; i++) {
				if (pi.getPlayerCount(dungeonid + i) == 0) {
					pi.warp(dungeonid + i);
					return true;
				}
			}
		}
		pi.playerMessage(5, "มินิดันเจี้ยนทั้งหมดกำลังถูกใช้งาน กรุณาลองใหม่ในภายหลัง");
		return false;
	} else {
		pi.playPortalSE();
		pi.warp(baseid, "MD00");
		return true;
	}
}