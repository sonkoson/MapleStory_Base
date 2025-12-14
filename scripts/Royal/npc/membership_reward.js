var status = -1;
var f = -1;
var reward = 0;
var v = "";

function start(flag) {
	status = -1;
	v = "";
	f = flag;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == 1)
		status++;
	else
		status--;
	if (status == 0) {
		if (f == 0) {
			reward = 2630402;
			v = "reward0";
		} else if (f == 256) {
			reward = 2631878;
			v = "reward1";
		} else if (f == 512) {
			reward = 2439660;
			v = "reward2";
		}
		if (reward == 0) {
			cm.sendNext("เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุ");
			cm.dispose();
			return;
		}
		cm.sendYesNo("ต้องการรับ #b#i" + reward + "# #z" + reward + "##k หรือไม่?");
	} else if (status == 1) {
		if (!cm.canHold(reward, 1)) {
			cm.sendNext("กรุณาเคลียร์ช่องเก็บของแล้วลองใหม่อีกครั้ง");
			cm.dispose();
			return;
		}
		if (cm.getPlayer().getOneInfoQuestInteger(501045, v) > 0) {
			cm.sendNext("ได้รับรางวัลของระดับนี้ไปแล้ว");
			cm.dispose();
			return;
		}
		cm.getPlayer().updateOneInfo(501045, v, "1");
		cm.gainItem(reward, 1);
		cm.sendNext("ได้รับ #b#i" + reward + "# #z" + reward + "##k แล้ว");
		cm.dispose();
	}
}