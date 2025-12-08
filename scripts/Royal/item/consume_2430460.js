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
		cm.getPlayer().gainDonationPoint(1);
                cm.gainItem(4260003, 2); // ºí·¢ º£¸®
                cm.gainItem(4260004, 2); // ¾î¸ÞÀÌÂ¡ Å¥ºê·£´ý
                cm.gainItem(4260005, 2); // °­È­¼®·£´ý
                cm.gainItem(4310261, 300); // ¿¹»Ûµ¹¸ÍÀÌ
                cm.gainItem(4001715, 5); // ¿¹»Ûµ¹¸ÍÀÌ
		cm.gainItem(2430460, -1);
		cm.dispose();
	}
}

