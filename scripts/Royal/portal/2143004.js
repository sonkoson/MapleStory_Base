/*var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
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
        if (cm.getPlayer().getMapId() == 271040000) {
            cm.sendOk("Hello?");
            cm.dispose();
        } else {
            cm.sendYesNo("Do you want to stop the Cygnus expedition and leave?");
        }
    } else if (status == 1) {
        if (cm.getPlayer().getMapId() == 271040000) {
            if (cm.getPlayer().getParty() == null) {
                cm.sendOk("Please create a party and enter.");
                cm.dispose();
            } else if (!cm.isLeader()) {
                cm.sendOk("Only the party leader can apply for entry.");
                cm.dispose();
            } else if (cm.getPlayerCount(271040100) > 0) {
        cm.sendOk("Another party is already fighting Cygnus.");
        cm.dispose();
        } else {
        cm.resetMap(271040100);
                cm.warpParty(271040100);
                cm.spawnMob(8850011, -280, 117);
                cm.getPlayer().getMap().startMapEffect("It's been a long time since I've seen someone come here. But no one has returned safely.", 5120043);
                cm.dispose();
            }
        } else {
            cm.warp(271040000, 1);
            cm.dispose();
        }
    }
}*/
function start() {
    cm.warp(271040100);
    cm.dispose();
}