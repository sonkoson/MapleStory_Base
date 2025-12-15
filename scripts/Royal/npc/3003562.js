var status = -1;

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
        if (cm.getPlayer().getMap().getNumMonsters() == 0 && (cm.getPlayer().getMapId() == 450008350)) {
            cm.sendYesNoS("You have defeated Will. Would you like to return to 'The Throne of Light Reflected in the Mirror'?", 0x26);
        } else {
            cm.sendYesNoS("This place is dangerous. Do you want to give up the battle and leave?", 0x26);
        }
    } else if (status == 1) {
        cm.getPlayer().setKeyValue("bossPractice", "0");
        cm.getPlayer().cancelEffectFromBuffStat(Packages.client.MapleBuffStat.DebuffIncHp);
        cm.warp(450007240, 1);
        cm.dispose();
    }
}