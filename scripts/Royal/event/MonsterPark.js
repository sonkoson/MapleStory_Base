importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);
importPackage(java.awt);

importPackage(Packages.objects.wz.provider);
importPackage(Packages.objects.users);
importPackage(Packages.objects.item);
importPackage(Packages.objects.utils);
importPackage(Packages.network.models);
importPackage(Packages.objects.fields);
importPackage(Packages.objects.fields.gameobject);
importPackage(Packages.objects.fields.gameobject.lifes);


/*

    Party Quest


        Properties Set : 
        Global_StartMap : Start Map
        Global_ExitMap  : Exit Map
        Global_MinPerson: Min Players
        Global_RewardMap: Reward Map to go to with clearPQ method.

        What needs to be done with the entry NPC : 
        var eim = em.readyInstance();
        eim.setProperty("Global_StartMap", [StartMap]+"");
        eim.setProperty("Global_ExitMap", [ExitMap]+"");
        eim.setProperty("Global_MinPerson", [MinPerson]+"");
        eim.setProperty("Global_RewardMap", [RewardMap]+"");
        eim.startEventTimer([TimeLimit]);
        eim.registerParty([PureParty Type], [PureMap Type]);
*/

function init() {

}

function setup(eim) {
    var a = Packages.objects.utils.Randomizer.nextInt();
    while (em.getInstance("MonsterPark_ID" + a) != null) {
        a = Packages.objects.utils.Randomizer.nextInt();
    }
    var eim = em.newInstance("MonsterPark_ID" + a);
    return eim;
}

function playerEntry(eim, player) {
    var map = parseInt(eim.getProperty("Global_StartMap"));
    player.changeMap(eim.getMapFactory().getMap(map), eim.getMapFactory().getMap(map).getPortal("sp"));
    player.message(6, "[ประกาศ] คุณเข้าสู่ดันเจี้ยน Monster Park แล้ว ขอให้โชคดี! :)");
}



function changedMap(eim, player, mapid) {

}

function scheduledTimeout(eim) {

    var exit = em.getChannelServer().getMapFactory().getMap(parseInt(eim.getProperty("Global_ExitMap")));
    var it = eim.getPlayers().iterator();
    while (it.hasNext()) {
        var chr = it.next();
        if (chr == null) {
            System.out.println("Character is NULL!");
        }
        if (exit == null) {
            System.out.println("EXIT Map is NULL!");
        }
        if (exit.getPortal("sp") == null) {
            System.out.println("EXIT Map Portal is NULL!");
        }
        chr.changeMap(exit, exit.getPortal("sp"));
        chr.Message(8, "หมดเวลาแล้ว กรุณาเริ่มใหม่ตั้งแต่ต้น");
    }
    eim.unregisterAll();
    if (eim != null) {
        eim.dispose();
    }
}

function allMonstersDead(eim) {
    var startmap = parseInt(eim.getProperty("Global_StartMap"));
    var curstage = parseInt(eim.getProperty("CurrentStage"));
    var curmap = (startmap + ((curstage - 1) * 100));
    var map = eim.getMapFactory().getMap(curmap);
    if (curstage < 6) {
        map.broadcastMessage(CField.showEffect("monsterPark/clear"));
    } else {
        map.broadcastMessage(CField.showEffect("monsterPark/clearF"));
    }
    map.broadcastMessage(CField.playSound("Party1/Clear"));
    eim.setProperty("CurrentStage", (curstage + 1) + "");
}

function playerRevive(eim, player) {

}

function playerDisconnected(eim, player) {
    /* 0 : Instance maintained until everyone leaves
     * 1 ~ : Instance maintained as long as a certain number of people remain, regardless of who leaves
     * -1 ~ or less : Maintained as long as a certain number of people remain, but deleted if the leader leaves
     */
    if (eim.getProperty("Global_MinPerson") == null) {
        return 0;
    }
    return -parseInt(eim.getProperty("Global_MinPerson"));
}

function monsterValue(eim, mobid) {
    return 1;
}

function leftParty(eim, player) {
    if (eim.getPlayerCount() < parseInt(eim.getProperty("Global_MinPerson"))) {
        var exit = em.getChannelServer().getMapFactory().getMap(parseInt(eim.getProperty("Global_ExitMap")));
        var it = eim.getPlayers().iterator();
        while (it.hasNext()) {
            var chr = it.next();
            eim.stopEventTimer();
            chr.changeMap(exit, exit.getPortal(0));
            chr.Message("สมาชิกในปาร์ตี้ออกจากกลุ่ม ทำให้ไม่สามารถดำเนินการเควสต์ต่อได้");
        }
        eim.unregisterAll();
        if (eim != null) {
            eim.dispose();
        }
    }

}

function disbandParty(eim) {
    var exit = eim.getPlayers().get(0).getClient().getChannelServer().getMapFactory().getMap(parseInt(eim.getProperty("Global_ExitMap")));
    var it = eim.getPlayers().iterator();
    while (it.hasNext()) {
        var chr = it.next();
        eim.stopEventTimer();
        chr.changeMap(exit, exit.getPortal(0));
        chr.Message("หัวหน้าปาร์ตี้ออกจากกลุ่ม ทำให้ไม่สามารถดำเนินการเควสต์ต่อได้");
    }
    eim.unregisterAll();
    if (eim != null) {
        eim.dispose();
    }
}

function clearPQ(eim) {

}

function playerExit(eim, player) {
    var exit = eim.getPlayers().get(0).getClient().getChannelServer().getMapFactory().getMap(parseInt(eim.getProperty("Global_ExitMap")));
    var it = eim.getPlayers().iterator();
    while (it.hasNext()) {
        var chr = it.next();
        eim.stopEventTimer();
        chr.changeMap(exit, exit.getPortal(0));
        chr.Message("ยกเลิกปาร์ตี้เควสต์ ทำให้ไม่สามารถดำเนินการต่อได้");
    }
    eim.unregisterAll();
    if (eim != null) {
        eim.dispose();
    }
}

function onMapLoad(eim, player) {

}

function cancelSchedule(a) {

}

function playerDead(eim, player) {
    eim.unregisterPlayer(player);
    eim.dispose();
}