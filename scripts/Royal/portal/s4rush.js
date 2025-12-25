function enter(pi) {
	if (pi.getQuestStatus(6110) == 1) {
		if (pi.getParty() != null) {
			if (!pi.isLeader()) {
				pi.playerMessage("หัวหน้าปาร์ตี้ซึ่งประกอบด้วย Warrior สองคนสามารถตัดสินใจเข้าได้");
			} else {
				if (pi.getParty().getMembers().size < 2) {
					pi.playerMessage("เจ้าสามารถทำภารกิจได้เมื่อมีปาร์ตี้สองคน โปรดจัดปาร์ตี้ที่มีสมาชิกสองคน");
				} else {
					if (!pi.isAllPartyMembersAllowedJob(1)) {
						pi.playerMessage("เจ้าไม่สามารถเข้าได้ อาชีพของสมาชิกในปาร์ตี้ไม่ใช่ Warrior หรือปาร์ตี้ของเจ้าไม่ได้ประกอบด้วยสมาชิกสองคน");
					} else {
						var em = pi.getEventManager("4jrush");
						if (em == null) {
							pi.playerMessage("เจ้าไม่ได้รับอนุญาตให้เข้าไปด้วยเหตุผลที่ไม่ทราบสาเหตุ ลองอีกครั้ง");
						} else {
							em.startInstance(pi.getParty(), pi.getMap());
							return true;
						}
					}
				}
			}
		} else {
			pi.playerMessage(5, "เจ้าไม่มีปาร์ตี้ เจ้าสามารถท้าทายได้ด้วยปาร์ตี้");
		}
	} else {
		pi.playerMessage("เจ้าไม่สามารถเข้าสู่สถานที่ที่ถูกปิดผนึกได้");
	}
	return false;
}