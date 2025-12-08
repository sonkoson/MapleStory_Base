 var status;
importPackage(Packages.server);
importPackage(Packages.client.inventory);
importPackage(Packages.server);
importPackage(Packages.server.items);
one = Math.floor(Math.random() * 5) + 1 // ÃÖ¼Ò 10 ÃÖ´ë 35 , È¥Å×ÀÏ
function start() {
    status = -1;
    action(1, 1, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        status --;
    }
    if (mode == 1) {
        status++;
    }
        if (status == 0) {
		        item = Packages.objects.item.MapleItemInformationProvider.getInstance().getEquipById(1712001);
		        item.setArcEXP(9999);
		        Packages.objects.item.MapleInventoryManipulator.addbyItem(cm.getClient(), item, false);
		        cm.sendOk("#b¸¸·¾ #i1712001##z1712001##b 1°³#fc0xFF000000#¸¦ È¹µæ ÇÏ¿´½À´Ï´Ù.");
		cm.gainItem(2632130, -1);
		cm.dispose();
	}
}

