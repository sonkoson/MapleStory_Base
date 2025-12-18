importPackage(Packages.objects.utils);

// Event manager initialization (applied per channel)
function init() {
    //Total 8 clickable maps (Easy and Normal are combined)
    em.setProperty("status0", "0");
    //em.setProperty("status1", "0");
    //em.setProperty("status2", "0");
    //em.setProperty("status3", "0");
    //em.setProperty("status4", "0");
    //em.setProperty("status5", "0");
    //em.setProperty("status7", "0");

    em.setProperty("Nstatus0", "0");
    //em.setProperty("Cstatus1", "0");
    //em.setProperty("Cstatus2", "0");
    //em.setProperty("Cstatus3", "0");
    //em.setProperty("Cstatus4", "0");
    //em.setProperty("Cstatus5", "0");
    //em.setProperty("Cstatus7", "0");

}

function playerRevive(eim, player) {
    return true;
}

function setup(eim) {
    var a = Packages.objects.utils.Randomizer.nextInt();
    while (em.getInstance("Cygnus_" + a) != null) {
        a = Packages.objects.utils.Randomizer.nextInt();
    }
    var eim = em.newInstance("Cygnus_" + a);
    eim.startEventTimer(60000 * 30);
    return eim;
}

function playerEntry(eim, player) {
    player.changeMap(parseInt(eim.getProperty("map")));
    player.setDeathCount(5);
}

function scheduledTimeout(eim) {
    //standby
    var it = eim.getPlayers().iterator();
    var exitMap = 271041200;
    if (eim.getProperty("mode") == "normal") {
        exitMap = 271040200;
    }
    while (it.hasNext()) {
        var chr = it.next();
        eim.unregisterPlayer(chr);
        var tese = chr.getWarpMap(exitMap);
        chr.changeMap(tese, tese.getPortal(0));
    }
    eim.unregisterAll();
    if (eim != null) {
        var mode = eim.getProperty("mode");
        if (mode == "easy") {
            eim.getEventManager().setProperty("status0", "0");
        } else if (mode == "normal") {
            eim.getEventManager().setProperty("Nstatus0", "0");
        }
        eim.dispose();
    }
}

function playerDead(eim, player) {
    dc = player.getDeathCount();
    var exitMap = 271041200;
    if (eim.getProperty("mode") == "normal") {
        exitMap = 271040200;
    }
    if (dc != null) {
        if (dc > 0) {
            player.setAutoRespawn(30, 5);
            player.setDeathCount(dc - 1);
        } else {
            player.setClock(0);
            var tese = player.getWarpMap(exitMap);
            player.changeMap(tese, tese.getPortal(0));
            eim.unregisterPlayer(player);
            if (eim.getPlayerCount() <= 0) {
                eim.unregisterAll();
                var mode = eim.getProperty("mode");
                if (mode == "easy") {
                    eim.getEventManager().setProperty("status0", "0");
                } else if (mode == "normal") {
                    eim.getEventManager().setProperty("Nstatus0", "0");
                }
                eim.dispose();
            }
        }
    }
}

function monsterValue(eim, mobid) {
    return 0;
}

function onMapLoad(eim, player) {
    player.deathCount();
}

function playerDisconnected(eim, player) {
    eim.unregisterPlayer(player);
    if (eim != null) {
        var mode = eim.getProperty("mode");
        if (mode == "easy") {
            eim.getEventManager().setProperty("status0", "0");
        } else if (mode == "normal") {
            eim.getEventManager().setProperty("Nstatus0", "0");
        }
        eim.dispose();
    }
}

function changedMap(eim, player, mapid) {
    if (mapid != 271040300 && mapid != 271041300 && mapid != 271041100 && mapid != 271040100) {
        eim.unregisterPlayer(player);
        if (eim != null) {
            if (eim.getPlayerCount() <= 0) {
                var mode = eim.getProperty("mode");
                if (mode == "easy") {
                    eim.getEventManager().setProperty("status0", "0");
                } else if (mode == "normal") {
                    eim.getEventManager().setProperty("Nstatus0", "0");
                }
                eim.dispose();
            }
        }
    }
}

function playerExit(eim, player) {
    eim.unregisterPlayer(player);
    if (eim != null) {
        if (eim.getPlayerCount() <= 0) {
            var mode = eim.getProperty("mode");
            if (mode == "easy") {
                eim.getEventManager().setProperty("status0", "0");
            } else if (mode == "normal") {
                eim.getEventManager().setProperty("Nstatus0", "0");
            }
            eim.dispose();
        }
    }
}

function allMonstersDead(eim) { }

function leftParty(eim, player) {
    // Player left party
    var exitMap = 271041200;
    if (eim.getProperty("mode") == "normal") {
        exitMap = 271040200;
    }
    playerExit(eim, player);
    player.changeMap(exitMap);
}

function disbandParty(eim) {
    var exitMap = 271041200;
    if (eim.getProperty("mode") == "normal") {
        exitMap = 271040200;
    }
    var it = eim.getPlayers().iterator();
    while (it.hasNext()) {
        var chr = it.next();
        eim.unregisterPlayer(chr);
        var tese = chr.getWarpMap(exitMap);
        chr.changeMap(tese, tese.getPortal(0));
    }
    eim.unregisterAll();
    if (eim != null) {
        var mode = eim.getProperty("mode");
        if (mode == "easy") {
            eim.getEventManager().setProperty("status0", "0");
        } else if (mode == "normal") {
            eim.getEventManager().setProperty("Nstatus0", "0");
        }
        eim.dispose();
    }
}

function cancelSchedule() { }
