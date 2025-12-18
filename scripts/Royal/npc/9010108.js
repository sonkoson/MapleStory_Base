importPackage(java.lang);

importPackage(Packages.constants);

var status = -1;
var s = -1;
var coin = 0;

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
		var v = cm.getPlayer().getOneInfoQuest(18790, "coin");
		coin = 0;
		if (v != null && !v.isEmpty()) {
			coin = parseInt(v);
		}
		if (coin > 0) {
			s = 0;
			cm.sendNext("เก็บ #b#i4310229:##t4310229##k ได้ #b" + coin + " ชิ้น#k เลยเหรอ? เก่งมาก~\r\n\r\nงั้นจะส่งไปที่จัตุรัสนะ ลาก่อน~");
		} else {
			s = 1;
			cm.sendNext("อืม... ยังไม่ได้ Union Coin เลย ถ้ายากเกินไป ลองเข้ามาใหม่ทีหลังนะ สมาชิก Union จะช่วยเก็บ Coin ไว้ให้");
		}
	} else if (status == 1) {
		if (s == 0) {
			var point = getUnionCoin() + coin;
			cm.getPlayer().gainItem(4310229, coin);
			cm.getPlayer().updateOneInfo(500629, "point", point + "");
			cm.getPlayer().updateOneInfo(18098, "coin", "0");
			cm.getPlayer().updateOneInfo(18790, "coin", "0");
			cm.warp(ServerConstants.TownMap, 0);
			cm.dispose();
		} else {
			cm.sendNext("งั้นจะส่งไปที่จัตุรัสนะ ลาก่อน~");
		}
	} else if (status == 2) {
		cm.warp(15);
		cm.dispose();
	}
}

function getUnionCoin() {
	var pv = cm.getPlayer().getOneInfoQuest(500629, "point");
	var point = 0;
	if (pv != null && !pv.isEmpty()) {
		point = parseInt(pv);
	}
	return point;
}