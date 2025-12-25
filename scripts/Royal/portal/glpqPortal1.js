function enter(pi) {
	var em = pi.getEventManager("CWKPQ");
	if (em != null) {
		if (em.getProperty("glpq2").equals("5")) {
			pi.warp(610030300, 0);
		} else {
			pi.playerMessage(5, "พอร์ทัลยังไม่เปิดใช้งาน!");
		}
	}
}