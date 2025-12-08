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
		cm.getPlayer().gainDonationPoint(700000);
                cm.gainItem(5068305, 10); // ∫Ì∑¢ ∫£∏Æ
                cm.gainItem(5062005, 20); // æÓ∏ﬁ¿Ã¬° ≈•∫Í
                cm.gainItem(4031466, 600); // æÓµ“¿«øµ»•ºÆ
                cm.gainItem(3994385, 10); // »≤±›¥‹«≥¿Ÿ
                cm.gainItem(4033114, 500); // æ«¿«¡§ºˆ
                cm.gainItem(3994718, 5); // øπª€µπ∏Õ¿Ã
		cm.gainItem(2434281, -1);
		cm.dispose();
	}
}

