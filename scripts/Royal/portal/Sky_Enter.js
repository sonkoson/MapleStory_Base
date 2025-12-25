function enter(pi) {
	var eim = pi.getDisconnected("Dragonica");
	if (eim != null && pi.getPlayer().getParty() != null) { //only skip if not null
		eim.registerPlayer(pi.getPlayer());
		return true;
	}
	if (pi.getPlayer().getParty() == null || !pi.isLeader()) {
		pi.playerMessage(5, "หัวหน้าปาร์ตี้ต้องอยู่ที่นี่");
		return false;
	}
	var party = pi.getPlayer().getParty().getMembers();
	var next = true;
	var size = 0;
	var it = party.iterator();
	while (it.hasNext()) {
		var cPlayer = it.next();
		var ccPlayer = pi.getPlayer().getMap().getCharacterById(cPlayer.getId());
		if (ccPlayer == null || ccPlayer.getLevel() < 120 || (ccPlayer.getSkillLevel(ccPlayer.getStat().getSkillByJob(1026, ccPlayer.getJob())) <= 0)) {
			next = false;
			break;
		} else if (ccPlayer.isGM()) {
			size += 4;
		} else {
			size++;
		}
	}
	if (next && size >= 2) {
		var em = pi.getEventManager("Dragonica");
		if (em == null) {
			pi.playerMessage(5, "กิจกรรมนี้ยังไม่เปิดให้บริการในขณะนี้");
		} else {
			var prop = em.getProperty("state");
			if (prop == null || prop.equals("0")) {
				em.startInstance(pi.getParty(), pi.getMap(), 200);
			} else {
				pi.playerMessage(5, "มีคนกำลังต่อสู้กับบอสนี้อยู่");
			}
		}
	} else {
		pi.playerMessage(5, "ตรวจสอบให้แน่ใจว่าสมาชิกทุกคนในปาร์ตี้ (2 คนขึ้นไป) อยู่ในแผนที่นี้ มีเลเวล 120+ และมีทักษะ Soaring");
		return false;
	}
	return true;
}