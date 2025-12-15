var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    var count = 1;
    setting = [
        ["Normal_Arkarium", count, 272020200, 140],
    ]
    name = ["Normal"]
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        if (cm.getPlayer().getMapId() == 272020110) {
            talk = "#e<Boss: Arkarium>#n\r\n"
            talk += "Great Hero, have you finished preparing to face the Black Mage's evil Commander?\r\n\r\n"
            talk += "#L0# #bApply to enter <Boss: Arkarium>.";
            cm.sendSimple(talk);
        } else if (cm.getPlayer().getMapId() == 272020100) {
            cm.warp(272020110);
            cm.dispose();
        } else {
            cm.sendYesNo("Would you like to leave Arkarium's Altar after finishing the battle?");
        }
    } else if (status == 1) {
        if (cm.getPlayer().getMapId() == 272020110) {
            talk = "#e<Boss: Arkarium>#n\r\n"
            talk += "Please select the mode you want.\r\n\r\n"
            talk += "#L0#Normal Mode (Level 140+)#l\r\n";
            cm.sendSimple(talk);
        } else {
            cm.warp(272020110);
            cm.dispose();
        }
    } else if (status == 2) {
        st = selection;
        if (cm.getPlayer().getParty() == null) {
            cm.sendOk("You must belong to a party of at least 1 person to enter.");
            cm.dispose();
            return;
        } else if (!cm.isLeader()) {
            cm.sendOk("Only the party leader can apply for entry.");
            cm.dispose();
            return;
        } else if (!cm.allMembersHere()) {
            cm.sendOk("All members must be in the same place.");
            cm.dispose();
            return;
        } else if (cm.getPlayerCount(setting[st][2]) >= 1 || cm.getPlayerCount(setting[st][2] + 100) >= 1 || cm.getPlayerCount(setting[st][2] + 200) >= 1) {
            cm.sendNext("Another party is already challenging Arkarium inside.");
            cm.dispose();
            return;
        }
        if (!cm.isBossAvailable(setting[st][0], setting[st][1])) {
            talk = "Among the party members, #b#e"
            for (i = 0; i < cm.BossNotAvailableChrList(setting[st][0], setting[st][1]).length; i++) {
                if (i != 0) {
                    talk += ", "
                }
                talk += "#b#e" + cm.BossNotAvailableChrList(setting[st][0], setting[st][1])[i] + ""
            }
            talk += "#k#n has already entered today. <Boss: Arkarium> can only be challenged " + setting[st][1] + " times a day.";
            cm.sendOk(talk);
            cm.dispose();
            return;
        } else if (!cm.isLevelAvailable(setting[st][3])) {
            talk = "Among the party members, #b#e"
            for (i = 0; i < cm.LevelNotAvailableChrList(setting[st][3]).length; i++) {
                if (i != 0) {
                    talk += ", "
                }
                talk += "#b#e" + cm.LevelNotAvailableChrList(setting[st][3])[i] + ""
            }
            talk += "#k#n's level is insufficient. <Boss: Arkarium> can only be challenged if you are level " + setting[st][3] + " or higher.";
        } else {
            cm.addBoss(setting[st][0]);
            em = cm.getEventManager(setting[st][0]);
            if (em != null) {
                cm.getEventManager(setting[st][0]).startInstance_Party(setting[st][2] + "", cm.getPlayer());
            }
            cm.dispose();
        }
    }
}