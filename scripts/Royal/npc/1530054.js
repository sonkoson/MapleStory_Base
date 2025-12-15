var status = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1)
        status++;
    else
        status--;
    if (status == 0) {
        if (cm.inBoss()) {
            cm.getPlayer().dropMessage(5, "This feature cannot be used during boss battles.");
            cm.dispose();
            return;
        }

        cm.dispose();
        cm.openNpcCustom(cm.getClient(), 9062611, "Event");
    }
}