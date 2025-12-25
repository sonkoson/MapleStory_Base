function enter(pi) {
	if (pi.getQuestStatus(6241) == 1 || pi.getQuestStatus(6243) == 1) {
		if (pi.getJob() == 312) {
			if (pi.haveItem(4001113)) {
				if (pi.getPlayerCount(924000100) > 0) {
					pi.playerMessage("ตัวละครอื่นกำลังทำภารกิจอยู่ เจ้าไม่สามารถเข้าไปได้");
					return false;
				}
				var em = pi.getEventManager("s4nest");
				if (em == null) {
					pi.playerMessage("เจ้าไม่ได้รับอนุญาตให้เข้าไปด้วยเหตุผลที่ไม่ทราบสาเหตุ ลองอีกครั้ง");
				} else {
					em.startInstance(pi.getPlayer());
					return true;
				}
			} else {
				pi.playerMessage("เจ้าไม่มีไข่ของ Phoenix เจ้าไม่สามารถเข้าไปได้");
			}
		} else if (pi.getJob() == 322) {
			if (pi.haveItem(4001114)) {
				if (pi.getPlayerCount(924000100) > 0) {
					pi.playerMessage("ตัวละครอื่นกำลังทำภารกิจอยู่ เจ้าไม่สามารถเข้าไปได้");
					return false;
				}
				var em = pi.getEventManager("s4nest");
				if (em == null) {
					pi.playerMessage("เจ้าไม่ได้รับอนุญาตให้เข้าไปด้วยเหตุผลที่ไม่ทราบสาเหตุ ลองอีกครั้ง");
				} else {
					em.startInstance(pi.getPlayer());
					return true;
				}
			} else {
				pi.playerMessage("เจ้าไม่มีไข่ของ Freezer เจ้าไม่สามารถเข้าไปได้");
			}
		}
	} else {
		pi.playerMessage("เจ้าไม่สามารถเข้าสู่สถานที่ที่ถูกปิดผนึกได้");
	}
	return false;
}