function enter(pi) {
	var em = pi.getEventManager("CWKPQ");
	if (em != null) {
		if (em.getProperty("glpq1").equals("1")) {
			em.setProperty("glpq1", "2");
			pi.warp(pi.getMapId(), 0);
			pi.mapMessage("[Expedition] นักผจญภัยได้ผ่านพอร์ทัลแล้ว!");
		} else if (em.getProperty("glpq1").equals("2")) {
			pi.warp(610030200, 0);
		} else {
			pi.playerMessage(5, "โปรดตรวจสอบให้แน่ใจว่าหัวหน้าปาร์ตี้ได้แจ้ง Jack เกี่ยวกับสถานการณ์แล้ว!");
		}
	}
}