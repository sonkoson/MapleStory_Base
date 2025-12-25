function enter(pi) {
	var em = pi.getEventManager("CWKPQ");
	if (em != null) {
		if (!em.getProperty("glpq4").equals("5")) {
			pi.playerMessage("พอร์ทัลยังไม่เปิด");
		} else {
			pi.warp(610030500, 0);
		}
	}
}