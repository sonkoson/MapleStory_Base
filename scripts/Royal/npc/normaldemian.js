importPackage(Packages.client.items);
importPackage(Packages.server.items);
importPackage(Packages.tools);
importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);
importPackage(java.awt);
importPackage(Packages.server);
importPackage(Packages.tools.packet);
importPackage(Packages.server.life);


var enter = "\r\n";
var data, date, day;


var bossid = 8880101;
//var secondboss = 100135;
var startmap = 350160140;
//var secondmap = 450004250;
var exitmap = 910001000;
var bossname = "Normal Damien"
var limit = 3;
var time = 10;
var x = 1091,
    y = 17;
var deathcount = 10;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, sel) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }
    if (status == 0) {
        var msg = "#e<Expedition: Normal Damien>#n\r\n" + "พร้อมที่จะเผชิญหน้ากับ Damien แล้วหรือยัง?\r\n";
        msg += "<เวลาจำกัด : #r" + time + " นาที#k>\r\n#d<จำนวนครั้งที่เข้าได้ต่อวัน#k #d: #b" + cm.GetCount(bossname + "c") + " ครั้ง#k / #r" + limit + " ครั้ง#k>#k" + enter;
        msg += "#d<Death Count :#k #r10 ครั้ง#k>#k" + enter;
        msg += "<Boss Reward : ยังไม่กำหนด>" + enter;
        msg += "#L0##b ขอเข้าสู่ Normal Damien";
        cm.sendSimple(msg);
    } else if (status == 1) {
        if (!cm.CountCheck(bossname + "c", limit)) {
            cm.sendOk("เข้าได้เพียงวันละ " + limit + " ครั้งเท่านั้น");
            cm.dispose();
            return;
        }
        if (cm.getPlayer().getParty() == null) {
            cm.sendOk("กรุณาสร้างปาร์ตี้ก่อนเข้าท้าทาย");
            cm.dispose();
            return;
        }
        if (!isPartyLeader()) {
            cm.sendOk("เฉพาะหัวหน้าปาร์ตี้เท่านั้นที่สามารถขอเข้าได้");
            cm.dispose();
            return;
        }
        if (!cm.allMembersHere()) {
            cm.sendOk("สมาชิกปาร์ตี้ทุกคนต้องมารวมตัวกันที่นี่");
            cm.dispose();
            return;
        }
        if (cm.getPlayerCount(startmap) > 0) {
            cm.sendOk("มีคนกำลังท้าทายอยู่แล้ว\r\n#bกรุณาใช้แชแนลอื่น#k");
            cm.dispose();
            return;
        }
        var it = cm.getClient().getChannelServer().getPartyMembers(cm.getParty()).iterator();
        var countPass = true;
        while (it.hasNext()) {
            var chr = it.next();
            if (!CC(chr, bossname + "c", limit)) {
                countPass = false;
                break;
            }
        }
        if (!countPass) {
            cm.sendOk("มีสมาชิกในปาร์ตี้ที่เข้าดันเจี้ยนครบจำนวนครั้งแล้ว");
            cm.dispose();
            return;
        } else {
            var it = cm.getClient().getChannelServer().getPartyMembers(cm.getParty()).iterator();
            var countPass = true;
            while (it.hasNext()) {
                var chr = it.next();
                AC(chr, bossname + "c");
            }
        }
        if (cm.getPlayerCount(startmap) > 0) {
            cm.sendOk("มีคนกำลังท้าทายอยู่แล้ว\r\n#bกรุณาใช้แชแนลอื่น#k");
            cm.dispose();
            return;
        }
        cm.resetMap(startmap);
        var em = cm.getEventManager("Boss");
        var eim = em.readyInstance();
        eim.setProperty("StartMap", startmap);
        //eim.setProperty("SecondMap", secondmap);
        eim.setProperty("ExitMap", exitmap);
        eim.setProperty("BossName", bossname);
        eim.setProperty("Boss_ID", bossid);
        //eim.setProperty("Boss_Second", secondboss);
        eim.setProperty("Boss_x", x);
        eim.setProperty("Boss_y", y);
        //eim.setProperty("Boss_x_2", x_2);
        //eim.setProperty("Boss_y_2", y_2);
        eim.setProperty("KillCount", 0);
        eim.setProperty("Leader", cm.getPlayer().getParty().getLeader().getName());
        eim.setProperty("DeathCount", deathcount);
        eim.startEventTimer(60000 * time);
        eim.registerParty(cm.getPlayer().getParty(), cm.getPlayer().getMap());
        cm.dispose();
    }
}


function isPartyLeader() {
    if (cm.getPlayer().getParty().getLeader().getId() == cm.getPlayer().getId())
        return true;
    else
        return false;
}

function AC(player, boss) {
    player.CountAdd(boss);
}

function CC(player, boss, limit) {
    return player.CountCheck(boss, limit);
}