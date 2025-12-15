function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }

    if (status == 0) {
        if (cm.inBoss()) {
            cm.getPlayer().dropMessage(5, "This feature cannot be used during boss battles.");
            cm.dispose();
            return;
        }

        cm.dispose();
        Packages.scripting.newscripting.ScriptManager.runScript(cm.getPlayer().getClient(), "codySystem", null);
    }
}