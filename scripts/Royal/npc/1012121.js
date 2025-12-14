importPackage(Packages.constants);

var status = 0;

function start() {
	action(1, 0, 0);
}

function action(mode, type, selection) {
	cm.dispose();
	cm.openNpcCustom(cm.getClient(), 1012121, "ItemDiscard");
}
