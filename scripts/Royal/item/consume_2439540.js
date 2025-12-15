importPackage(Packages.objects.item);

var item =[
	[2439990, 2],
	[2439993, 2],
	[2439994, 2],
	[4036660, 10],
	[5068305, 2],
	[4034803, 1]
]

var boxcode = 2439540;

var keyname = "testbox";

var enter = "\r\n";

Purple = "#fMap/MapHelper.img/weather/starPlanet/7#";
Blue = "#fMap/MapHelper.img/weather/starPlanet/8#";
StarBlue = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
StarYellow = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
StarWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
StarBrown = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
StarRed = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
StarBlack = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
StarPurple = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
Star = "#fUI/FarmUI.img/objectStatus/star/whole#"
Reward = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
Obtain = "#fUI/UIWindow2.img/QuestIcon/4/0#"
Color = "#fc0xFF6600CC#"
Black = "#fc0xFF000000#"
Pink = "#fc0xFFFF3366#"
Pink = "#fc0xFFF781D8#"

function start() {
	St = -1;
	action(1, 0, 0);
}

function action(M, T, S) {
	if(M != 1) {
		cm.dispose();
		return;
	}

	if(M == 1)
	    St++;

	if(St == 0) {
		var msg = "#fs11#[R] 요정의 선물 상자 - 토요일을 사용 하시겠어요?\r\n\r\n#r※ 본 캐릭터에 수령 권장합니다.\r\n※ 해당 상자는 이벤트 기간 내에 사용하시기 바랍니다.";
        msg += "\r\n\r\n#b < [R] 요정의 선물 상자 - 토요일 보상 목록 >\r\n\r\n";
		msg += "이벤트에 참여 해주셔서 감사합니다. 모험가님!\r\n"+enter
		for(var i = 0; i<item.length; i++) {
			msg += "#i"+item[i][0]+"# #z"+item[i][0]+"# ("+item[i][1]+")"+enter
		}
		msg += "\r\nตรวจสอบรายการไอเทมแล้วต้องการรับของรางวัลหรือไม่?"
		cm.sendYesNo(msg);
	} else if(St == 1) {
		// if (cm.getClient().getKeyValue(keyname) != null) {
		// 	cm.sendOk("ข้อผิดพลาด: ไม่พบกล่อง");
		// 	cm.gainItem(boxcode, -1);
		// 	cm.addCustomLog(99, boxcode+" / "+cm.getPlayer().getNamte()+"");
		// 	cm.dispose();
		// 	return;
		// }
		var invenuse = cm.getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot();
		var invenetc = cm.getPlayer().getInventory(MapleInventoryType.ETC).getNumFreeSlot();
		if(invenuse < 20 || invenetc < 20){
			cm.sendOk("소비창과 기타창을 20칸이상 비워주세요.");
			cm.dispose();
			return;
		}
		if(!cm.haveItem(boxcode, 1)){
			cm.gainItem(boxcode, -1);
			cm.addCustomLog(99, boxcode+" / "+cm.getPlayer().getNamte()+"");
			cm.sendOk("ข้อผิดพลาด: ไม่พบกล่อง");
			cm.dispose();
			return;
		}
		var msg = "#r#fs11#더욱 더 강해지시길 응원합니다. 모험가님!\r\n#b"+enter
		for(var i = 0; i<item.length; i++) {
			msg += "#i"+item[i][0]+"# #z"+item[i][0]+"# ("+item[i][1]+")"+enter
			cm.gainItem(item[i][0], item[i][1]);
		}
		msg += "\r\nได้รับไอเทมเรียบร้อยแล้ว"
		//cm.getClient().setKeyValue(keyname, "1");
		cm.gainItem(boxcode, -1);
		cm.sendOk(msg);
		cm.dispose();
	}
}
