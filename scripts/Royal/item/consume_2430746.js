 var status;
importPackage(Packages.server);
importPackage(Packages.client.inventory);
importPackage(Packages.server);
importPackage(Packages.server.items);
one = Math.floor(Math.random() * 5) + 1 // √÷º“ 10 √÷¥Î 35 , »•≈◊¿œ
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
		cm.getPlayer().gainDonationPoint(1500000);
                cm.gainItem(5068305, 15); // ∫Ì∑¢ ∫£∏Æ
                cm.gainItem(5062005, 35); // æÓ∏ﬁ¿Ã¬° ≈•∫Í
                cm.gainItem(4031466, 1000); // æÓµ“¿«øµ»•ºÆ
                cm.gainItem(3994385, 20); // »≤±›¥‹«≥¿Ÿ
                cm.gainItem(4033114, 1000); // æ«¿«¡§ºˆ
                cm.gainItem(3994718, 8); // øπª€µπ∏Õ¿Ã
		cm.gainItem(2430746, -1);
		cm.dispose();
	}
}

