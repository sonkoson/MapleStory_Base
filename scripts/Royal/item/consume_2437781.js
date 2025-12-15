importPackage(Packages.objects.item);

var bosang1 = [1009902, 1109902, 1089902, 1079902, 1112048, 1012992, 1022992, 1032992];

var boxcode = 2437781;

function MakeItem(itemid) {
    var ii = Packages.objects.item.MapleItemInformationProvider.getInstance();
    var it = ii.getEquipById(itemid);
    it.setStr(444);
    it.setDex(444);
    it.setInt(444);
    it.setLuk(444);
    it.setWatk(333);
    it.setMatk(333);
    it.setCHUC(30);
    it.setAllStat(5);
    it.setOwner("강림월드");
    it.setExpiration((new Date()).getTime() + (1000 * 60 * 60 * 24 * 30));
    Packages.objects.item.MapleInventoryManipulator.addFromDrop(cm.getPlayer().getClient(), it, false);
}

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
		var msg = "#fs11#스페셜 아이템을 지급받으시겠어요?\r\n\r\n#r※ 스페셜 아이템은 계정당 1회만 지급됩니다 (캐릭터 이동 불가)\r\n※ 스페셜 아이템은 30일 기간제이며 기간만료시 삭제됩니다";
        msg += "\r\n\r\n#b < 1개월 스페셜 아이템 리스트 >\r\n\r\n";
        for (i = 0; i < bosang1.length; i++) {
            msg += 색 + "#i"+bosang1[i]+"# #z"+bosang1[i]+"#" +enter;
        }
        cm.sendYesNo(msg);
	} else if(St == 1) {
		var invenEQUIP = cm.getPlayer().getInventory(MapleInventoryType.CASH_EQUIP).getNumFreeSlot();
		if(invenEQUIP < 4){
			cm.sendOk("치장칸을 4칸이상 비워주세요.");
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
		var msg = "#fs11#" + 획득 + enter + enter;
        for (i = 0; i < bosang1.length; i++) {
            msg += 색 + "#i"+bosang1[i]+"# #z"+bosang1[i]+"#" +enter;
            MakeItem(bosang1[i]);
        }
        msg += 핑크색 + "\r\n위와 같은 아이템이 지급되었습니다";
        cm.gainItem(boxcode, -1);
        cm.sendOk(msg);
        cm.dispose();
	}
}
