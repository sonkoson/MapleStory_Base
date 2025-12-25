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

var mapid = 932200001;
var returnmap = 932200003;


function init() {

}

function setup(eim) {
    var a = Packages.objects.utils.Randomizer.nextInt();
    while (em.getInstance("Mapia" + a) != null) {
        a = Packages.objects.utils.Randomizer.nextInt();
    }
    var eim = em.newInstance("Mapia" + a);
    return eim;
}

function playerEntry(eim, player) {
    em.setProperty("Stage", "start");
    em.setProperty("select_kill", "0");
    em.setProperty("select_police", "0");
    player.changeMap(eim.getMapFactory().getMap(mapid), eim.getMapFactory().getMap(mapid).getPortal("sp"));
    em.setProperty("killed", "");
    if (player.isLeader()) {
        eim.getMapFactory().getMap(mapid).broadcastMessage(CField.addPopupSay(9000233, 3000, "จะทำการสุ่มบทบาทในอีก 30 วินาที", ""));
    }
}

function changedMap(eim, player, mapid) {

}

function scheduledTimeout(eim) {
    arr = [0, 1, 2, 3, 4, 5];
    stage = em.getProperty("Stage");
    if (stage == "start") {
        for (i = 0; i < 6; i++) {
            d = Math.floor(Math.random() * arr.length);
            rd = arr[d]
            if (i < 2) {
                em.setProperty("mapia" + i, rd + "");
            } else if (i == 2) {
                em.setProperty("police", rd + "");
            } else if (i == 3) {
                em.setProperty("doctor", rd + "");
            } else {
                em.setProperty("man", rd + "");
            }
            arr.splice(d, 1);
            var it = eim.getPlayers()
            chr = it.get(rd)
            var job = i < 2 ? "มาเฟีย" : i == 2 ? "ตำรวจ" : i == 3 ? "หมอ" : "พลเมือง"
            chr.dropMessage(6, "บทบาทของคุณคือ " + job);
            em.setProperty("Stage", "night");
            eim.restartEventTimer(60000);
        }
        eim.getMapFactory().getMap(mapid).broadcastMessage(CField.addPopupSay(9000233, 3000, "เวลากลางคืนแล้ว แต่ละบทบาทกรุณาทำหน้าที่ผ่าน NPC", ""));
    } else if (stage == "night") {
        if (em.getProperty("kill") < 0 || em.getProperty("kill") == null || (em.getProperty("kill") == em.getProperty("cure"))) {
            eim.getMapFactory().getMap(mapid).broadcastMessage(CField.addPopupSay(9000233, 3000, "เช้าแล้ว ไม่มีเหตุการณ์ใดเกิดขึ้น เชิญหารือกันได้ตามอิสระ", ""));
            em.setProperty("kill", "-1");
            em.setProperty("Stage", "morning");
            eim.restartEventTimer(90000);
        } else {
            em.setProperty("killed", em.getProperty("killed") + em.getProperty("kill"));
            if (isEnd(eim) == 0) {
                eim.getMapFactory().getMap(mapid).broadcastMessage(CField.addPopupSay(9000233, 3000, "เมื่อคืน " + eim.getPlayers().get(parseInt(em.getProperty("kill"))).getName() + " เสียชีวิต ฝ่ายพลเมืองชนะ! มาเฟียคือ " + eim.getPlayers().get(parseInt(em.getProperty("mapia0"))).getName() + ", " + eim.getPlayers().get(parseInt(em.getProperty("mapia1"))).getName(), ""));
                em.setProperty("Stage", "end");
                eim.restartEventTimer(10000);
            } else if (isEnd(eim) == 1) {
                eim.getMapFactory().getMap(mapid).broadcastMessage(CField.addPopupSay(9000233, 3000, "เมื่อคืน " + eim.getPlayers().get(parseInt(em.getProperty("kill"))).getName() + " เสียชีวิต ฝ่ายมาเฟียชนะ! มาเฟียคือ " + eim.getPlayers().get(parseInt(em.getProperty("mapia0"))).getName() + ", " + eim.getPlayers().get(parseInt(em.getProperty("mapia1"))).getName(), ""));
                em.setProperty("Stage", "end");
                eim.restartEventTimer(10000);
            } else {
                eim.getMapFactory().getMap(mapid).broadcastMessage(CField.addPopupSay(9000233, 3000, "เช้าแล้ว เมื่อคืน " + eim.getPlayers().get(parseInt(em.getProperty("kill"))).getName() + " ถูกมาเฟียฆ่าเสียชีวิต เชิญหารือกันได้ตามอิสระ", ""));
                em.setProperty("kill", "-1");
                em.setProperty("Stage", "morning");
                eim.restartEventTimer(90000);
            }

        }
    } else if (stage == "morning") {
        eim.getMapFactory().getMap(mapid).broadcastMessage(CField.addPopupSay(9000233, 3000, "ได้เวลาโหวตแล้ว กรุณาโหวตคนที่คุณคิดว่าเป็นมาเฟีย", ""));
        em.setProperty("Stage", "vote");
        for (i = 0; i < 6; i++) {
            em.setProperty("vote_" + i, "0");
        }
        em.setProperty("votemember", "");
        eim.restartEventTimer(30000);
    } else if (stage == "vote") {
        kill = -1;
        vote = 0;
        for (i = 0; i < 6; i++) {
            if (Integer.parseInt(em.getProperty("vote_" + i)) > vote) {
                kill = i;
                vote = Integer.parseInt(em.getProperty("vote_" + i))
            } else if (Integer.parseInt(em.getProperty("vote_" + i)) == vote) {
                kill = -1;
            }
        }
        em.setProperty("kill", "" + kill);
        if (em.getProperty("kill") != "-1") {
            em.setProperty("killed", em.getProperty("killed") + em.getProperty("kill"));
            talk = "ผลการโหวต " + eim.getPlayers().get(em.getProperty("kill")).getName() + " เสียชีวิต"
            if (isEnd(eim) == 0) {
                talk += " ฝ่ายพลเมืองชนะ! มาเฟียคือ " + eim.getPlayers().get(parseInt(em.getProperty("mapia0"))).getName() + ", " + eim.getPlayers().get(parseInt(em.getProperty("mapia1"))).getName();
                eim.getMapFactory().getMap(mapid).broadcastMessage(CField.addPopupSay(9000233, 3000, talk, ""));
                em.setProperty("Stage", "end");
                eim.restartEventTimer(10000);
            } else if (isEnd(eim) == 1) {
                talk += " ฝ่ายมาเฟียชนะ! มาเฟียคือ " + eim.getPlayers().get(parseInt(em.getProperty("mapia0"))).getName() + ", " + eim.getPlayers().get(parseInt(em.getProperty("mapia1"))).getName();
                eim.getMapFactory().getMap(mapid).broadcastMessage(CField.addPopupSay(9000233, 3000, talk, ""));
                em.setProperty("Stage", "end");
                eim.restartEventTimer(10000);
            } else {
                em.setProperty("Stage", "readynight");
                eim.restartEventTimer(30000);

            }
        } else {
            talk = "ไม่มีผู้เสียชีวิตเนื่องจากคะแนนโหวตเท่ากันหรือไม่มีการโหวต";
            em.setProperty("Stage", "readynight");
        }
        eim.getMapFactory().getMap(mapid).broadcastMessage(CField.addPopupSay(9000233, 3000, talk, ""));
        eim.restartEventTimer(30000);
    } else if (stage == "readynight") {
        eim.getMapFactory().getMap(mapid).broadcastMessage(CField.addPopupSay(9000233, 3000, "เวลากลางคืนแล้ว แต่ละบทบาทกรุณาทำหน้าที่ผ่าน NPC", ""));
        em.setProperty("select_kill", "0");
        em.setProperty("select_police", "0");
        em.setProperty("Stage", "night");
        eim.restartEventTimer(60000);
    } else if (stage == "end") {
        MapiaEnd(eim);
    } else {
        eim.getMapFactory().getMap(mapid).broadcastMessage(CField.addPopupSay(9000233, 3000, "[Error] [CODE : " + stage + "] เกิดข้อผิดพลาดที่ไม่คาดคิด กรุณาติดต่อ GM จะถูกส่งออกใน 30 วินาที", ""));
        em.setProperty("Stage", "end");
        eim.restartEventTimer(30000);
    }
}

function isAlive(chrid) {
    getKillNumber = em.getProperty("killed");
    if (getKillNumber == null) {
        return true;
    } else if (getKillNumber.contains(chrid + "")) {
        return false;
    } else {
        return true;
    }
}

function isEnd(eim) {
    mapia = 0;
    normal = 0;
    for (i = 0; i < 6; i++) {
        if (isAlive(i)) {
            if (Integer.parseInt(em.getProperty("mapia0")) == i || Integer.parseInt(em.getProperty("mapia1")) == i) {
                mapia++;
            } else {
                normal++;
            }
        }
    }
    if (mapia == 0) {
        return 0; // Citizen Wins
    } else if (mapia >= normal) {
        return 1; // Mafia Wins
    } else {
        return 2; // Not Finished
    }
}

function monsterKilled(eim, player, mobid) {
}



function playerDead(eim, player) {

}


function monsterValue(eim, mobid) {
    return 0;
}


function disbandParty(eim) {
    var it = eim.getPlayers().iterator();
    while (it.hasNext()) {
        var chr = it.next();
        var tese = chr.getWarpMap(932200003);
        chr.changeMap(tese, tese.getPortal(0));
        chr.dropMessage(5, "หัวหน้าปาร์ตี้ออกจากกลุ่ม ทำให้ปาร์ตี้ถูกยุบ");
    }
    eim.unregisterAll();
    if (eim != null) {
        eim.dispose();
    }
}


function removePlayer(eim, player) {
    eim.dispose();
}


function playerExit(eim, player) {
    eim.unregisterPlayer(player);
    if (eim != null) {
        if (eim.getPlayerCount() <= 0) {
            eim.dispose();
        }
    }
}

function onMapLoad(eim, player) {

}

function playerDisconnected(eim, player) {
    if (eim.getProperty("Global_MinPerson") == null) {
        return -1;
    }
    return -Integer.parseInt(eim.getProperty("Global_MinPerson"));
}

function MapiaEnd(eim) {
    var exit = em.getChannelServer().getMapFactory().getMap(returnmap);
    var it = eim.getPlayers().iterator();
    while (it.hasNext()) {
        var chr = it.next();
        chr.changeMap(exit, exit.getPortal(0));
    }
    eim.unregisterAll();
    if (eim != null) {
        eim.dispose();
    }
}

function leftParty(eim, player) {
    var it = eim.getPlayers().iterator();
    while (it.hasNext()) {
        var chr = it.next();
        var tese = chr.getWarpMap(932200003);
        chr.changeMap(tese, tese.getPortal(0));
        chr.dropMessage(5, "สมาชิกในปาร์ตี้ออกจากกลุ่ม ทำให้ปาร์ตี้ถูกยุบ");
    }
    eim.unregisterAll();
    if (eim != null) {
        eim.dispose();
    }
}

function disbandParty(eim) {
    var exit = eim.getPlayers().get(0).getClient().getChannelServer().getMapFactory().getMap(returnmap);
    var it = eim.getPlayers().iterator();
    while (it.hasNext()) {
        var chr = it.next();
        chr.changeMap(exit, exit.getPortal(0));
        chr.Message("หัวหน้าปาร์ตี้ออกจากกลุ่ม ทำให้ไม่สามารถดำเนินการเควสต์ต่อได้");
    }
    eim.unregisterAll();
    if (eim != null) {
        eim.dispose();
    }
}

function cancelSchedule() {
}