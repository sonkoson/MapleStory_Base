function enter(pi) {
	if (pi.haveItem(4001094)) {
		pi.getMap().getReactorByName("dragonBaby").hitReactor(pi.getClient());
		pi.getMap().getReactorByName("dragonBaby2").hitReactor(pi.getClient());
		pi.playerMessage(5, "Egg of Nine Spirit ที่วางไว้อย่างปลอดภัย ได้เปล่งแสงลึกลับและกลับคืนสู่รังของมัน");
		pi.gainItem(4001094, -1);
	}
}