importPackage(java.lang);

var Purple = "#fMap/MapHelper.img/weather/starPlanet/7#";
var Blue = "#fMap/MapHelper.img/weather/starPlanet/8#";
var StarBlue = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
var StarYellow = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
var StarWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
var StarBrown = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
var StarRed = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
var StarBlack = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
var StarPurple = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
var Star = "#fUI/FarmUI.img/objectStatus/star/whole#"
var S = "#fUI/CashShop.img/CSEffect/today/0#"
var Reward = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
var Obtain = "#fUI/UIWindow2.img/QuestIcon/4/0#"
var Color = "#fc0xFF6600CC#"
var Black = "#fc0xFF000000#"
엔터 = "\r\n"
엔터2 = "\r\n\r\n"

var enter = "\r\n";
var seld = -1;

function start() {
	status = -1;
	action(1, 0, 0);
}
function action(mode, type, sel) {
	if (mode == 1) {
		status++;
	} else {
		cm.dispose();
		return;
	}
	if (status == 0) {
		var msg = "#fs11#ยินดีต้อนรับ #h #! ฉันรับฝากของได้ทุกอย่างเลยนะ\r\n#fs11##d" + enter;
		msg += "#L1#" + Color + "[คลังเก็บของทั่วไป] " + Black + "ฝาก/ถอนของ" + enter;
		//msg += "#L2#" + Color + "[Cash Storage] " + Black + "Use (Unavailable)";
		cm.sendSimple(msg);
		//cm.sendStorage();
		//cm.openNpcCustom(cm.getClient(), 9000213, "캐시창고");
	} else if (status == 1) {
		seld = sel;
		switch (seld) {
			case 1:
				cm.dispose();
				cm.sendStorage();
				break;
			case 2:
				cm.dispose();
				cm.openNpcCustom(cm.getClient(), 9000213, "캐시창고");
				break;
		}
	}
}