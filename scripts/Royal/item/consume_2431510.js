var status;
importPackage(Packages.server);

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
	//cm.getPlayer().AddStarDustCoin(50);
	cm.getPlayer().dropMessage(6, "¸®ÇÁ Æ÷ÀÎÆ® 50 È¹µæ!");
	cm.gainItem(2431510, -1);
	cm.dispose();
	}
}
