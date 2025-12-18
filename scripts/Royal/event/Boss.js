importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);
importPackage(java.text);
importPackage(java.awt);

importPackage(Packages.objects.item);
importPackage(Packages.objects.users);
importPackage(Packages.objects.utils);
importPackage(Packages.constants);
importPackage(Packages.network.models);
importPackage(Packages.objects.wz.provider);
importPackage(Packages.objects.fields);
importPackage(Packages.objects.fields.obstacle);
importPackage(Packages.objects.fields.gameobject);
importPackage(Packages.objects.fields.gameobject.lifes);

var rucif = 8880166;

function init() { }


function playerRevive(eim, player) {
    return true;
}

function spawnBoss(bossid, point, mapid, eim) {
    var mobzz = em.getMonster(bossid);
    eim.registerMonster(mobzz);
    var map = eim.getMapFactory().getMap(mapid);

    map.spawnMonsterOnGroundBelow(mobzz, point);
    /*if (eim.getProperty("BossName").contains("Lotus") && eim.getProperty("KillCount") == 0) {
        eim.startSpawnSelfDestructMob(mobzz, map);
    }*/
}

function setup(eim) {
    var a = Packages.objects.utils.Randomizer.nextInt();
    while (em.getInstance("Boss_" + a) != null) {
        a = Packages.objects.utils.Randomizer.nextInt();
    }
    var eim = em.newInstance("Boss_" + a);
    return eim;
}



function playerEntry(eim, player) {
    if (eim.getProperty("Boss_ID") != null) {
        mobid = Integer.parseInt(eim.getProperty("Boss_ID"));
        x = Integer.parseInt(eim.getProperty("Boss_x"));
        y = Integer.parseInt(eim.getProperty("Boss_y"));
    }
    mapid = Integer.parseInt(eim.getProperty("StartMap"));
    player.warp(mapid);
    var deathCount = Integer.parseInt(eim.getProperty("DeathCount"));
    if (eim.getProperty("BossName") != "Black Mage") {
        player.setDeathCount(deathCount);
    }
    var bossMode = 0;
    if (eim.getProperty("BossMode") != null) {
        bossMode = Integer.parseInt(eim.getProperty("BossMode"));
    }
    player.setBossMode(bossMode);
    if (player.isLeader() && eim.getProperty("Boss_ID") != null) {
        var map = eim.getMapFactory().getMap(mapid);
        if (eim.getProperty("BossName") == "Chaos Pink Bean") {
            map.spawnMonsterOnGroundBelow(em.getMonster(8820115), new Point(x, y));
            map.spawnMonsterOnGroundBelow(em.getMonster(8820116), new Point(x, y));
            map.spawnMonsterOnGroundBelow(em.getMonster(8820117), new Point(x, y));
            map.spawnMonsterOnGroundBelow(em.getMonster(8820118), new Point(x, y));
            map.spawnMonsterOnGroundBelow(em.getMonster(8820102), new Point(4, y));
            spawnBoss(8820210, new Point(x, y), mapid, eim);
        } else {
            if (!eim.getProperty("BossName").contains("Lotus") && !eim.getProperty("BossName").contains("Bloody Queen") && !eim.getProperty("BossName").contains("Horntail") && !eim.getProperty("BossName").contains("Vellum") && !eim.getProperty("BossName").contains("Demian") && !eim.getProperty("BossName").contains("Lucid") && !eim.getProperty("BossName").contains("Will") && !eim.getProperty("BossName").contains("Jin Hilla") && !eim.getProperty("BossName").contains("Dunkel") && !eim.getProperty("BossName").contains("Dusk") && !eim.getProperty("BossName").contains("Papulatus")) {
                spawnBoss(mobid, new Point(x, y), mapid, eim);
            }
        }

    }

}

function spawnz(eim) {
    var mobzz = em.getMonster(9460026);
    mobzz.setFh(90);
    eim.registerMonster(mobzz);

    eim.getMapFactory().getMap(814031200).spawnMonsterOnGroundBelow(mobzz, new Point(96, -2377));
}


function scheduledTimeout(eim) {
    var isClear = eim.getProperty("IsClear");
    var it = eim.getPlayers().iterator();
    if (isClear == "1") {
        while (it.hasNext()) {
            var chr = it.next();
            var tese = chr.getWarpMap(ServerConstants.TownMap);
            chr.setClock(0);
            chr.changeMap(tese, tese.getPortal(0));
            chr.dropMessage(5, "ย้ายกลับเมืองเรียบร้อยแล้ว");

        }
    } else {
        while (it.hasNext()) {
            var chr = it.next();
            var tese = chr.getWarpMap(ServerConstants.TownMap);
            chr.setClock(0);
            chr.changeMap(tese, tese.getPortal(0));
            chr.dropMessage(5, "หมดเวลาแล้ว ปาร์ตี้ถูกยุบ");

        }
    }
    eim.unregisterAll();
    if (eim != null) {
        eim.dispose();
    }
}


function playerDead(eim, player) {
    dc = player.getDeathCount();
    if (dc != null) {
        if (dc > 0) {
            if (!(player.getMapId() >= 450013000 && player.getMapId() <= 450013700)) {
                player.setDeathCount(dc - 1);
            }
            player.setAutoRespawn(30, 5);
            //player.warp(player.getMap().getId());
            //player.addHP(500000);
        } else {
            /*var it = eim.getPlayers().iterator();
            eim.unregisterAll();
            while (it.hasNext()) {
                var chr = it.next();
                chr.dropMessage(5, "ใช้ Death Count หมดแล้ว");
                chr.setClock(0);
                var tese = chr.getWarpMap(ServerConstants.TownMap);
                chr.changeMap(tese, tese.getPortal(0));

            }
            if (eim != null) {
                eim.dispose();
            }*/
            player.setClock(0);
            var tese = player.getWarpMap(ServerConstants.TownMap);
            player.changeMap(tese, tese.getPortal(0));
            eim.unregisterPlayer(player);

            if (eim.getPlayerCount() <= 0) {
                eim.unregisterAll();
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

function playerDisconnected(eim, player) { }


function changedMap(eim, player, mapid) {

    if (mapid == ServerConstants.TownMap) {
        eim.unregisterPlayer(player);
    }
}


function removePlayer(eim, player) {
    eim.unregisterPlayer(player);
    if (eim != null) {
        if (eim.getPlayerCount() <= 0) {
            eim.dispose();
        }
    }
}


function playerExit(eim, player) {
    eim.unregisterPlayer(player);
    if (eim != null) {
        if (eim.getPlayerCount() <= 0) {
            eim.dispose();
        }
    }
}


function allMonstersDead(eim) {
    kc = Integer.parseInt(eim.getProperty("KillCount"));
    if (eim.getProperty("BossName") == "Lucid") {
        if (kc == 0) {
            eim.setProperty("KillCount", 99);
            var it = eim.getPlayers().iterator();
            var x = Integer.parseInt(eim.getProperty("Boss_x_2"));
            var y = Integer.parseInt(eim.getProperty("Boss_y_2"));
            while (it.hasNext()) {
                var chr = it.next();
                chr.warp(Integer.parseInt(eim.getProperty("SecondMap")));
                eim.getMapFactory().getMap(Integer.parseInt(eim.getProperty("SecondMap"))).spawnMonsterOnGroundBelow(em.getMonster(8880171), new Point(297, -125));
                eim.getMapFactory().getMap(Integer.parseInt(eim.getProperty("SecondMap"))).spawnMonsterOnGroundBelow(em.getMonster(8880171), new Point(786, -194));
                eim.getMapFactory().getMap(Integer.parseInt(eim.getProperty("SecondMap"))).spawnMonsterOnGroundBelow(em.getMonster(8880164), new Point(964, -331));
                eim.getMapFactory().getMap(Integer.parseInt(eim.getProperty("SecondMap"))).spawnMonsterOnGroundBelow(em.getMonster(8880164), new Point(148, -550));
                eim.getMapFactory().getMap(Integer.parseInt(eim.getProperty("SecondMap"))).spawnMonsterOnGroundBelow(em.getMonster(8880171), new Point(353, -375));
                eim.getMapFactory().getMap(Integer.parseInt(eim.getProperty("SecondMap"))).spawnMonsterOnGroundBelow(em.getMonster(8880171), new Point(763, -490));
                eim.getMapFactory().getMap(Integer.parseInt(eim.getProperty("SecondMap"))).spawnMonsterOnGroundBelow(em.getMonster(8880165), new Point(1085, -619));
                eim.getMapFactory().getMap(Integer.parseInt(eim.getProperty("SecondMap"))).spawnMonsterOnGroundBelow(em.getMonster(8880168), new Point(525, -685));
                eim.getMapFactory().getMap(Integer.parseInt(eim.getProperty("SecondMap"))).spawnMonsterOnGroundBelow(em.getMonster(8880171), new Point(329, -855));
                chr.dropMessage(5, "กำลังเข้าสู่เฟส 2...");
            }
            spawnBoss(Integer.parseInt(eim.getProperty("Boss_Second")), new Point(x, y), Integer.parseInt(eim.getProperty("SecondMap")), eim);
        } else if (kc == 99) {
            eim.restartEventTimer(10000);
            eim.setProperty("IsClear", "1");
            var it = eim.getPlayers().iterator();
            while (it.hasNext()) {
                var chr = it.next();
                chr.gainItem(4033804, 1);
                chr.dropMessage(5, "ยินดีด้วยนะ เคลียร์สำเร็จแล้ว! ได้รับกล่องดนตรีแล้ว เอาไปให้ NPC ที่ทางเข้าได้เลย จะย้ายออกอัตโนมัติ");
                //chr.worldGMMessage(7, "[Clear] Lucid boss has been cleared.");
            }
        }
    } else if (eim.getProperty("BossName") == "Attack on Titan") {
        eim.restartEventTimer(10000);
        eim.setProperty("IsClear", "1");
        var it = eim.getPlayers().iterator();
        while (it.hasNext()) {
            var chr = it.next();
            chr.gainItem(Integer.parseInt(eim.getProperty("Boss_Reward")), 1);
            chr.dropMessage(5, "ยินดีด้วยนะ เคลียร์สำเร็จแล้ว! ได้รับเหรียญปราบศัตรูแล้ว เอาไปให้ NPC ได้เลย จะย้ายออกอัตโนมัติ");
        }
    } else if (eim.getProperty("BossName") == "Chaos Papulatus") {
        if (kc == 0) {
            eim.restartEventTimer(20000);
            eim.setProperty("IsClear", "1");
            var it = eim.getPlayers().iterator();
            while (it.hasNext()) {
                var chr = it.next();
                chr.dropMessage(5, "ยินดีด้วยนะ เคลียร์ Papulatus สำเร็จแล้ว! จะย้ายออกอัตโนมัติ");
            }
        }
    } else if (eim.getProperty("BossName") == "Hard Hilla") {
        if (kc == 0) {
            eim.restartEventTimer(20000);
            eim.setProperty("IsClear", "1");
            var it = eim.getPlayers().iterator();
            while (it.hasNext()) {
                var chr = it.next();
                chr.dropMessage(5, "ยินดีด้วยนะ เคลียร์ Hilla สำเร็จแล้ว! จะย้ายออกอัตโนมัติ");
            }
        }
    } else if (eim.getProperty("BossName") == "Chaos Pink Bean") {
        if (kc == 0) {
            eim.restartEventTimer(20000);
            eim.setProperty("IsClear", "1");
            var it = eim.getPlayers().iterator();
            while (it.hasNext()) {
                var chr = it.next();
                chr.dropMessage(5, "ยินดีด้วยนะ เคลียร์ Chaos Pink Bean สำเร็จแล้ว! จะย้ายออกอัตโนมัติ");
            }
        }
    } else if (eim.getProperty("BossName") == "Von Leon") {
        if (kc == 0) {
            eim.restartEventTimer(20000);
            eim.setProperty("IsClear", "1");
            var it = eim.getPlayers().iterator();
            while (it.hasNext()) {
                var chr = it.next();
                chr.dropMessage(5, "ยินดีด้วยนะ เคลียร์ Von Leon สำเร็จแล้ว! จะย้ายออกอัตโนมัติ");
            }
        }
    } else if (eim.getProperty("BossName") == "Magnus") {
        if (kc == 0) {
            eim.restartEventTimer(20000);
            eim.setProperty("IsClear", "1");
            var it = eim.getPlayers().iterator();
            while (it.hasNext()) {
                var chr = it.next();
                chr.dropMessage(5, "ยินดีด้วยนะ เคลียร์ Magnus สำเร็จแล้ว! จะย้ายออกอัตโนมัติ");
            }
        }
    } else if (eim.getProperty("BossName") == "Arkarium") {
        if (kc == 0) {
            eim.restartEventTimer(20000);
            eim.setProperty("IsClear", "1");
            var it = eim.getPlayers().iterator();
            while (it.hasNext()) {
                var chr = it.next();
                chr.gainItem(2434589, 1);
                chr.dropMessage(5, "ยินดีด้วยนะ เคลียร์ Arkarium สำเร็จแล้ว! จะย้ายออกอัตโนมัติ");
            }
        }
    } else if (eim.getProperty("BossName") == "Cygnus") {
        if (kc == 0) {
            eim.restartEventTimer(20000);
            eim.setProperty("IsClear", "1");
            var it = eim.getPlayers().iterator();
            while (it.hasNext()) {
                var chr = it.next();
                chr.gainItem(2434588, 1);
                chr.dropMessage(5, "ยินดีด้วยนะ เคลียร์ Cygnus สำเร็จแล้ว! จะย้ายออกอัตโนมัติ");
            }
        }
    } else if (eim.getProperty("BossName") == "Chaos Horntail") {
        if (kc == 0) {
            eim.restartEventTimer(20000);
            eim.setProperty("IsClear", "1");
            var it = eim.getPlayers().iterator();
            while (it.hasNext()) {
                var chr = it.next();
                chr.dropMessage(5, "ยินดีด้วยนะ เคลียร์ Chaos Horntail สำเร็จแล้ว! จะย้ายออกอัตโนมัติ");
            }
        }
    } else if (eim.getProperty("BossName") == "Chaos Pierre") {
        if (kc == 0) {
            var x = 491;
            var y = 551;
            eim.setProperty("KillCount", 1);
            var it = eim.getPlayers().iterator();
            while (it.hasNext()) {
                var chr = it.next();
                chr.warp(105200611);
                chr.dropMessage(5, "กำลังเข้าสู่เฟส 2...");
            }
            eim.getMapFactory().getMap(105200611).resetFully();
            spawnBoss(8900001, new Point(x, y), 105200611, eim);
        } else if (kc == 1) {
            var x = 491;
            var y = 552;
            eim.setProperty("KillCount", 99);
            var it = eim.getPlayers().iterator();
            while (it.hasNext()) {
                var chr = it.next();
                chr.warp(105200612);
                chr.dropMessage(5, "กำลังเข้าสู่เฟส 3...");
            }
            eim.getMapFactory().getMap(105200612).resetFully();
            spawnBoss(8900002, new Point(x, y), 105200612, eim);
        } else if (kc == 99) {
            eim.restartEventTimer(20000);
            eim.setProperty("IsClear", "1");
            var it = eim.getPlayers().iterator();
            while (it.hasNext()) {
                var chr = it.next();
                chr.dropMessage(5, "ยินดีด้วยนะ เคลียร์ Chaos Pierre สำเร็จแล้ว! จะย้ายออกอัตโนมัติ");
            }
        }
    } else if (eim.getProperty("BossName") == "Chaos Von Bon") {
        if (kc == 0) {
            eim.restartEventTimer(20000);
            eim.setProperty("IsClear", "1");
            var it = eim.getPlayers().iterator();
            while (it.hasNext()) {
                var chr = it.next();
                chr.dropMessage(5, "ยินดีด้วยนะ เคลียร์ Chaos Von Bon สำเร็จแล้ว! จะย้ายออกอัตโนมัติ");
            }
        }
    } else if (eim.getProperty("BossName") == "Chaos Vellum") {
        if (kc == 0) {
            eim.restartEventTimer(20000);
            eim.setProperty("IsClear", "1");
            var it = eim.getPlayers().iterator();
            while (it.hasNext()) {
                var chr = it.next();
                chr.dropMessage(5, "ยินดีด้วยนะ เคลียร์ Chaos Vellum สำเร็จแล้ว! จะย้ายออกอัตโนมัติ");
            }
        }
    } else {
        if (!eim.getProperty("BossName").contains("Lotus")) {
            eim.restartEventTimer(20000);
            eim.setProperty("IsClear", "1");
            var it = eim.getPlayers().iterator();
            while (it.hasNext()) {
                var chr = it.next();
                chr.dropMessage(5, "ยินดีด้วยนะ เคลียร์สำเร็จแล้ว! จะย้ายออกอัตโนมัติ");
            }
        }
    }
}

function monsterKilled(eim, player, point) { }


function leftParty(eim, player) {
    var it = eim.getPlayers().iterator();
    while (it.hasNext()) {
        var chr = it.next();
        var tese = chr.getWarpMap(ServerConstants.TownMap);
        chr.setClock(0);
        chr.changeMap(tese, tese.getPortal(0));
        chr.dropMessage(5, "สมาชิกหรือหัวหน้าปาร์ตี้ออกจากปาร์ตี้หรือย้ายแมพ ทำให้ปาร์ตี้ถูกยุบ");
    }
    eim.unregisterAll();
    if (eim != null) {
        eim.dispose();
    }
}



function disbandParty(eim) {
    var it = eim.getPlayers().iterator();
    while (it.hasNext()) {
        var chr = it.next();
        chr.setClock(0);
        var tese = chr.getWarpMap(ServerConstants.TownMap);
        chr.changeMap(tese, tese.getPortal(0));
        chr.dropMessage(5, "สมาชิกหรือหัวหน้าปาร์ตี้ออกจากปาร์ตี้หรือย้ายแมพ ทำให้ปาร์ตี้ถูกยุบ");
    }
    eim.unregisterAll();
    if (eim != null) {
        eim.dispose();
    }
}