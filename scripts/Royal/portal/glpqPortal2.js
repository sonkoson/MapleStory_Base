function enter(pi) {
	var em = pi.getEventManager("CWKPQ");
	if (em != null) {
		pi.warpS(610030300, 0);
		if (!em.getProperty("glpq3").equals("10")) {
			em.setProperty("glpq3", parseInt(em.getProperty("glpq3")) + 1);
			pi.mapMessage(6, "นักผจญภัยได้ผ่านเข้ามาแล้ว!");
			if (em.getProperty("glpq3").equals("10")) {
				pi.mapMessage(6, "Antellion อนุญาตให้เข้าสู่พอร์ทัลถัดไป! ไปต่อได้!");
				pi.getMap().changeEnvironment("3pt", 2);
			}
		}
	}
}