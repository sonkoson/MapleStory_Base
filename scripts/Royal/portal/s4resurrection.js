function enter(pi) {
	if (pi.getQuestStatus(6132) == 1) {
		var em = pi.getEventManager("s4resurrection");
		if (em == null) {
			pi.playerMessage("เจ้าไม่ได้รับอนุญาตให้เข้าไปด้วยเหตุผลที่ไม่ทราบสาเหตุ ลองอีกครั้ง");
		} else { // 923000100
			var prop = em.getProperty("started");
			if (prop == null || prop.equals("false")) {
				em.startInstance(pi.getPlayer());
				return true;
			} else {
				pi.playerMessage("มีคนกำลังทำภารกิจนี้อยู่");
			}
		}
	} else {
		pi.playerMessage("เจ้าไม่สามารถเข้าสู่สถานที่ที่ถูกปิดผนึกได้");
	}
	return false;
}