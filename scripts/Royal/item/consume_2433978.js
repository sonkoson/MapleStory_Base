importPackage(java.lang);
importPackage(java.text);
importPackage(Packages.objects.wz.provider);
importPackage(Packages.objects.users);
importPackage(Packages.objects.item);
importPackage(Packages.objects.utils);
importPackage(Packages.network.models);

var status = -1;
var code = 2433978;


function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
	cm.gainItem(code, -1);
	cm.getPlayer().gainMeso(100000000, true);
	cm.getPlayer().dropMessage(5, "1¾ï ¸Þ¼Ò¸¦ È¹µæÇß½À´Ï´Ù.");
	cm.dispose();
	}
}