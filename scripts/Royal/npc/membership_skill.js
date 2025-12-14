var status = -1;
var f = -1;
var lv = -1;
var sp = -1;

var skillNames = [
	"Increase Attack/Magic Attack",
	"Increase All Stats, Max HP/MP",
	"Increase Ignore Defense",
	"Increase Normal Monster Damage",
	"Increase Boss Monster Damage",
	"Increase Buff Duration",
	"Increase Critical Rate",
	"Increase Arcane Force",
	"Increase EXP Obtained"
];
var needPoints = [1, 2, 3];

function start(flag) {
	status = -1;
	f = flag;
	lv = 0;
	sp = 0;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == 1)
		status++;
	else
		status--;
	if (status == 0) {
		if (f == 0) {
			f = 0; // Increase Attack/Magic Attack
		} else if (f == 0x100) {
			f = 1; // Increase All Stats, Max HP/MP
		} else if (f == 0x200) {
			f = 2; // Increase Ignore Defense
		} else if (f == 0x300) {
			f = 3; // Increase Normal Monster Damage
		} else if (f == 0x400) {
			f = 4; // Increase Boss Monster Damage
		} else if (f == 0x500) {
			f = 5; // Increase Buff Duration
		} else if (f == 0x600) {
			f = 6; // Increase Critical Rate
		} else if (f == 0x700) {
			f = 7; // Increase Arcane Force
		} else if (f == 0x800) {
			f = 8; // Increase EXP Obtained
		}
		var v0 = "ต้องการอัพสกิล #e#b[" + skillNames[f] + "]#n#k หรือไม่?\r\n\r\n";
		lv = cm.getPlayer().getOneInfoQuestInteger(501046, f + "");
		if (lv >= 3) {
			cm.sendNext("ไม่สามารถอัพเลเวลสกิลนี้ได้อีก");
			cm.dispose();
			return;
		}

		sp = cm.getPlayer().getOneInfoQuestInteger(501045, "sp");
		v0 += "- Skill Point ที่ต้องการ : #e#r" + needPoints[lv] + "#k#n\r\n";
		v0 += "- Skill Point ที่มี : #e#b" + sp + "#k#n";
		cm.sendYesNo(v0);
	} else if (status == 1) {
		if (sp < needPoints[lv]) {
			cm.sendNext("ไม่สามารถอัพเลเวลได้เนื่องจาก Skill Point ไม่เพียงพอ");
			cm.dispose();
			return;
		}
		cm.getPlayer().updateOneInfo(501045, "sp", (sp - needPoints[lv]) + "");
		lv += 1;
		cm.getPlayer().updateOneInfo(501046, f + "", lv + "");
		cm.sendNext("สกิล #e#b" + skillNames[f] + "#k#n กลายเป็น #e#rเลเวล " + lv + "#k#n แล้ว");
		cm.bigScriptProgressMessage("สกิล " + skillNames[f] + " กลายเป็นเลเวล " + lv + " แล้ว!");
		cm.dispose();
	}
}