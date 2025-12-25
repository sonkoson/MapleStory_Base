function enter(pi) {
	var returnMap = pi.getSavedLocation("MULUNG_TC");
	if (returnMap < 0) {
		returnMap = 103000000; // เพื่อแก้ไขผู้ที่เข้าสู่ตลาดเสรีด้วยวิธีที่ไม่ปกติ
	}
	pi.clearSavedLocation("MULUNG_TC");
	pi.warp(returnMap, "unityPortal2");
	return true;
}