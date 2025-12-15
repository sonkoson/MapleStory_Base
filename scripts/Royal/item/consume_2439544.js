importPackage(Packages.objects.item);
importPackage(java.util);
importPackage(java.io);

var item =[
	[1143906, 1],
]
var day = 30;
var boxcode = 2439544;

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
		var msg = "#fs11#[R] 요정의 가호 훈장로 교환 하시겠어요?\r\n\r\n#r※ 사용 할 캐릭터에 수령 권장합니다. (교환 불가 아이템)\r\n※ 지급되는 아이템은 30일 기간제 아이템으로 지급됩니다.";
        msg += "\r\n\r\n#b < Item Exchange Info >\r\n\r\n";
		msg += "해당 아이템은 창고로 이동이 불가능합니다.\r\n"+enter
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
		var msg = "#r#fs11#더욱 더 빠르게 성장 해보세요. 모험가님!\r\n#b"+enter
		for(var i = 0; i<item.length; i++) {
			msg += "#i"+item[i][0]+"# #z"+item[i][0]+"# ("+item[i][1]+")"+enter
		}
		getitem(item[0][0])
		msg += "\r\nได้รับไอเทมเรียบร้อยแล้ว"
		//cm.getClient().setKeyValue(keyname, "1");
		cm.gainItem(boxcode, -1);
		cm.sendOk(msg);
		cm.dispose();
	}
}

function getitem(itemcode){
	item = Packages.objects.item.MapleItemInformationProvider.getInstance().getEquipById(itemcode);
	item.setExpiration((new Date()).getTime() + (1000 * 60 * 60 * 24 * day));
	Packages.objects.item.MapleInventoryManipulator.addbyItem(cm.getClient(), item, false);
	cm.dispose();
}
