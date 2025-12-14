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
        cm.dispose();
        cm.sendOkS("This system is under development.", 0x24, 1052206);
        //cm.openNpcCustom(cm.getClient(), 1530050, "1530050");
    }
}
