importPackage(Packages.objects.item);

var item =[
	[3700514, 1],
	[2434628, 1],
	[4310237, 1000],
	[4310229, 1000],
	[4310266, 1000],
	[4310308, 500],
	[2435885, 1],
	[2437781, 1],
	[2431940, 20],
	[2450163, 10],
	[2024084, 10],
	[2024156, 10],
	[2439279, 10],
	[2438412, 1],
	[2632972, 1],
	[2437092, 7],
	[2438644, 5],
	[2434290, 50],
	[2633590, 4],
	[2633591, 2],
	[2430042, 5],
	[2634291, 1],
	[4001716, 30],
	[2436577, 50]
]

var boxcode = 2439542;

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
		var msg = "#fs11#[R] 요정의 지원 꾸러미를 사용 하시겠어요?\r\n\r\n#r※ 본 캐릭터에 수령 권장합니다. (교불 포함됨)\r\n※ 해당 상자는 이벤트 기간 내에 사용하시기 바랍니다.";
        msg += "\r\n\r\n#b < [R] 요정의 지원 꾸러미 보상 목록 >\r\n\r\n";
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
		if(invenuse < 30 || invenetc < 30){
			cm.sendOk("소비창과 기타창을 30칸이상 비워주세요.");
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
