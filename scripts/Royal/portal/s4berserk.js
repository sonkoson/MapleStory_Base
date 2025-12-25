function enter(pi) {
	if (pi.getQuestStatus(6153) == 1) {
		if (!pi.haveItem(4031471)) {
			if (pi.haveItem(4031475)) {
				var em = pi.getEventManager("4jberserk");
				if (em == null) {
					pi.playerMessage("เจ้าไม่ได้รับอนุญาตให้เข้าไปด้วยเหตุผลที่ไม่ทราบสาเหตุ ลองอีกครั้ง");
				} else {
					em.startInstance(pi.getPlayer());
					return true;
				}
				// start event here
				// if ( ret != 0 ) target.message( "Other character is on the quest currently. Please try again later." );
			} else {
				pi.playerMessage("ในการเข้า เจ้าต้องมีกุญแจสู่ Forgotten Shrine");
			}
		} else {
			pi.playerMessage("Sayram มีโล่อยู่แล้ว");
		}
	} else {
		pi.playerMessage("เจ้าไม่สามารถเข้าสู่สถานที่ที่ถูกปิดผนึกได้");
	}
	return false;
}