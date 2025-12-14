importPackage(Packages.objects.utils);
importPackage(Packages.scripting);

var status = -1;
var enter = "\r\n";

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == -1) {
		cm.dispose();
		return;
	}
	if (mode == 0) {
		status--;
		cm.dispose();
	}
	if (mode == 1) {
		status++;
	}

	if (status == 0) {
		var msg = "#r#eการดวลกับมังกรยักษ์#n#k กำลังรอท่านนักรบอยู่!\r\n#b#eต้องการเข้าสู่ Union Raid#n#k หรือไม่?";
		cm.sendYesNo(msg);
	} else if (status == 1) {
		var a = Packages.objects.utils.Randomizer.rand(0, 1);
		var b = a == 1 ? 100 : 0;

		var find = false;
		var mapID = 0;
		for (var i = 0; i < 99; ++i) {
			mapID = 921172000 + b + i;

			if (cm.getPlayerCount(mapID) > 0) {
				continue;
			}
			find = true;
			cm.resetMap(mapID);
			break;
		}
		if (!find) {
			cm.sendNext("ขณะนี้มีนักรบเข้าสู่ Union Battle จำนวนมาก จึงไม่สามารถเข้าได้ กรุณาใช้แชแนลอื่นหรือลองใหม่อีกครั้งในภายหลัง");
			cm.dispose();
			return;
		}

		var em = cm.getEventManager("unionRaid");
		var eim = em.readyInstance();
		if (eim == null) {
			cm.sendNext("ไม่สามารถดำเนินการได้เนื่องจากข้อผิดพลาดที่ไม่ทราบสาเหตุ");
			dispose();
			return;
		}
		eim.setProperty("MapID", mapID);
		eim.registerPlayer(cm.getPlayer());
		cm.dispose();
	}
}