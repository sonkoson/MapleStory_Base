var status = -1;
var info = false;

function start() {
	status = -1;
	info = false;
	action(1, 0, 0);
}



function action(mode, type, selection) {
	if (mode == 0 && type == 3 && selection == -1) {
		info = true;
		cm.sendNext("#e#b<VIP Membership Tier>#n#k จะเพิ่มขึ้นตาม #e#r<VIP Membership Points>#n#k ที่ท่านซื้อ\r\nถือเป็นการ #eตรวจสอบขั้นต่ำ#n เพื่อรับสิทธิพิเศษ");
		return;
	}
	if (mode == 1)
		status++;
	else
		status--;
	if (status == 0) {
		var v0 = "สวัสดีครับ คุณ #e#b#h0##k#n\r\nมีอะไรให้ช่วยไหมครับ?\r\n\r\n";
		v0 += "#L0#ต้องการซื้อ #e#r<VIP Membership Point>#k#n#l";
		cm.sendSimple(v0);
	} else if (status == 1) {
		cm.sendYesNo("ท่านต้องการใช้ #e#rNeo Stone 500 ชิ้น#k#n เพื่อซื้อ #e#b<VIP Membership Point>#k#n หรือไม่?");
	} else if (status == 2) {
		if (info) {
			cm.sendNext("ยิ่งระดับสูงขึ้น #e#rสิทธิประโยชน์และบริการ#n#k ที่เตรียมไว้ก็จะดียิ่งขึ้นไปอีก");
			cm.dispose();
		} else {
			var coinCount = cm.getPlayer().getStackEventGauge(0);
			if (coinCount <= 500) {
				cm.sendNext("ดูเหมือน Neo Stone จะไม่พอนะ? ต้องการ #bNeo Stone 500 ชิ้น#k");
				cm.dispose();
				return;
			} else {
				var lv = cm.getPlayer().getOneInfoQuestInteger(501045, "lv");
				if (lv >= 4) {
					cm.sendNext("คุณ #e#b#h0##k#n อยู่ในระดับ #e#rVIP PRESTIGE#k#n แล้ว ไม่สามารถซื้อ #e#rVIP Membership Point#k#n เพิ่มได้อีก");
					cm.dispose();
					return;
				}
				if (cm.getPlayer().getOneInfoQuestInteger(501045, "mp") >= 1) {
					cm.sendNext("วันนี้ไม่สามารถซื้อ #e#rVIP Membership Point#k#n ได้อีกแล้ว");
					cm.dispose();
					return;
				}
				cm.getPlayer().updateOneInfo(501045, "mp", "1");
				cm.bigScriptProgressMessage("ซื้อ VIP Membership Point แล้ว!");
				cm.getPlayer().updateOneInfo(501045, "sp", cm.getPlayer().getOneInfoQuestInteger(501045, "sp") + 1);
				var point = cm.getPlayer().getOneInfoQuestInteger(501045, "point") + 1;
				var point2 = cm.getPlayer().getOneInfoQuestInteger(501053, "point") + 1;
				cm.getPlayer().updateOneInfo(501045, "point", point);
				cm.getPlayer().updateOneInfo(501053, "point", point2);
				//cm.getPlayer().gainKillPoint(-20000);
				cm.getPlayer().gainStackEventGauge(0, -500, true);
				if (checkLevelUp(point, lv)) {
					cm.getPlayer().updateOneInfo(501045, "lv", lv + 1);
					cm.getPlayer().updateOneInfo(501045, "point", "0");
					var grade = "VIP ";
					if (lv == 1) {
						grade += "ELITE";
					} else if (lv == 2) {
						grade += "PREMIUM";
					} else if (lv == 3) {
						grade += "PRESTIGE";
					}
					cm.addPopupSay("ขอแสดงความยินดีที่ได้เลื่อนระดับเป็น #e#r" + grade + "#k#n !", "", 9062294, 3000);
				}
				cm.dispose();
			}
		}
	}
}

function checkLevelUp(point, lv) {
	if (lv == 1) {
		if (point >= 5) {
			return true;
		}
	} else if (lv == 2) {
		if (point >= 10) {
			return true;
		}
	} else if (lv == 3) {
		if (point >= 20) {
			return true;
		}
	}
	return false;
}