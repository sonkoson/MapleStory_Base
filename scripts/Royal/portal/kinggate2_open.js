/*
Stage 5: Door before Ergoth - Guild Quest

@Author Lerk
*/

function enter(pi) {
	if (pi.getMap().getReactorByName("kinggate").getState() == 1) {
		pi.warp(990000900, 2);
		if (pi.getEventInstance().getProperty("boss") != null && pi.getEventInstance().getProperty("boss").equals("true")) {
			pi.changeMusic("Bgm10/Eregos");
		}
		return true;
	} else {
		pi.playerMessage("รอยแตกนี้ดูเหมือนจะถูกประตูใกล้ๆ ปิดกั้นอยู่");
		return false;
	}
}
