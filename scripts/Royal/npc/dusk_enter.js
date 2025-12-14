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
importPackage(Packages.objects.fields.gameobject);
importPackage(Packages.objects.fields.gameobject.lifes);
importPackage(Packages.database);
importPackage(Packages.scripting);

var startmap = [
    450009400, // Normal
    450009450, // Hard
    450009400, // Normal Practice
    450009450, // Hard Practice
];
var exitmap = 15;
var bossname = "Gloom"
var limit = [
    3, // Normal
    3, // Chaos
    20, // Normal Practice
    20 // Chaos Practice
];
var time = 30;
var deathcount = 5;
var practice = false;

var diff = ["Normal", "Chaos", "Normal Practice", "Chaos Practice"];

function start() {
    practice = false;
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
        text = "เราไม่สามารถปล่อยสัตว์ศิลายักษ์ Gloom ที่เกิดจากความคิดชั่วร้ายของ Black Mage ไว้ได้\r\n\r\n"
        text += "#L0##bไปยัง Eye of the Void (Normal Mode)#r(Level 245+)#k#l\r\n"
        text += "#L1##bไปยัง Eye of the Void (Chaos Mode)#r(Level 245+)#k#l\r\n"
        text += "#L2##bไปยัง Eye of the Void (Normal Practice Mode)#r(Level 245+)#k#l\r\n"
        text += "#L3##bไปยัง Eye of the Void (Chaos Practice Mode)#r(Level 245+)#k#l\r\n\r\n"
        text += "#L4#ไม่ต้องการย้าย#l";
        cm.askMenuSelfNew(text);
    } else if (status == 1) {
        if (selection == 4) {
            cm.dispose();
            return;
        }
        difficulty = selection;
        bn = diff[difficulty] + " " + bossname;
        if (selection == 2 || selection == 3) {
            practice = true;
        }
        if (practice) {
            cm.askYesNo("คุณได้เลือกเข้าสู่โหมดฝึกซ้อม ในโหมดฝึกซ้อม #b#eคุณจะไม่ได้รับ EXP และของรางวัล#n#k และสามารถเข้าได้ #b#eวันละ 5 ครั้ง#n#k เท่านั้น ต้องการเข้าสู่หรือไม่?", GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
        } else {
            enterBoss();
        }
    } else if (status == 2) {
        enterBoss();
    }
}

function enterBoss() {
    var n = "dusk_clear";
    var p = cm.getPlayer();
    var q = cm.getPlayer().getOneInfoQuest(1234589, n);
    if (q != null && !q.isEmpty() && q == "1" && !practice) {
        cm.sayNpc("คุณได้กำจัดบอสไปแล้วในสัปดาห์นี้ สามารถเข้าได้อีกครั้งหลังจากการรีเซ็ตในวันพฤหัสบดีเวลา 00:00 น.", GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
        cm.dispose();
        return;
    }

    var keyValue = bn + "c";
    if (practice) {
        keyValue = "boss_practice";
    }
    if (!cm.CountCheck(keyValue, limit[difficulty])) {
        cm.sayNpc("สามารถเข้าได้ " + limit[difficulty] + " ครั้งต่อวัน และสามารถท้าทายได้ใหม่ตั้งแต่วันพฤหัสบดีหลังจากกำจัดบอสแล้ว", GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
        cm.dispose();
        return;
    }
    if (cm.getPlayer().getParty() == null) {
        cm.sayNpc("ต้องอยู่ในปาร์ตี้ที่มีสมาชิก 1 คนขึ้นไปจึงจะสามารถเข้าได้", GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
        cm.dispose();
        return;
    }
    if (!isPartyLeader()) {
        cm.sayNpc("เฉพาะหัวหน้าปาร์ตี้เท่านั้นที่สามารถสมัครได้", GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
        cm.dispose();
        return;
    }
    if (!cm.allMembersHere()) {
        cm.sayNpc("สมาชิกปาร์ตี้ทุกคนต้องมารวมตัวกันที่นี่", GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
        cm.dispose();
        return;
    }
    if (cm.getPlayerCount(startmap[difficulty]) > 0) {
        cm.sayNpc("มีคนกำลังท้าทายอยู่แล้ว\r\n#bกรุณาใช้แชแนลอื่น#k", GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
        cm.dispose();
        return;
    }
    if (!practice) {
        if (!cm.canEnterBoss("dusk_can_time")) {
            return;
        }
    }
    var it = cm.getClient().getChannelServer().getPartyMembers(cm.getParty()).iterator();
    var countPass = true;
    var timePass = true;
    while (it.hasNext()) {
        var chr = it.next();
        if (chr.getLevel() < 245) {
            countPass = false;
            break;
        }
        if (!CC(chr, keyValue, limit[difficulty])) {
            countPass = false;
            break;
        }
        if (!chr.canEnterBoss("dusk_can_time")) {
            timePass = false;
            break;
        }
    }
    if (!timePass && !practice) {
        cm.sayNpc("ไม่สามารถเข้าได้เนื่องจากมีสมาชิกปาร์ตี้ที่ยังติดเวลาจำกัดการเข้า", GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
        cm.dispose();
        return;
    }
    if (!countPass) {
        cm.sayNpc("ไม่สามารถเข้าได้เนื่องจากมีสมาชิกปาร์ตี้ที่มีจำนวนครั้งการเข้าไม่เพียงพอหรือเลเวลไม่ถึงเกณฑ์", GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
        cm.dispose();
        return;
    } else {
        var it = cm.getClient().getChannelServer().getPartyMembers(cm.getParty()).iterator();
        var countPass = true;
        while (it.hasNext()) {
            var chr = it.next();
            var con = DBConnection.getConnection();
            var ps = con.prepareStatement("INSERT INTO boss_log(bossname, name) VALUES('" + bn + "', '" + chr.getName() + "')");
            ps.executeQuery();
            ps.close();
            con.close();
            AC(chr, keyValue);
            chr.setCurrentBossPhase(1);
        }
    }
    cm.resetMap(startmap[difficulty]);
    cm.dispose();
    var em = cm.getEventManager("Boss");
    var eim = em.readyInstance();
    eim.setProperty("StartMap", startmap[difficulty]);
    eim.setProperty("ExitMap", exitmap);
    eim.setProperty("BossName", bn);
    eim.setProperty("KillCount", 0);
    eim.setProperty("Leader", cm.getPlayer().getParty().getLeader().getName());
    eim.setProperty("DeathCount", deathcount);
    eim.setProperty("BossMode", practice ? 1 : 0);
    eim.startEventTimer(60000 * time); // 30 min
    eim.registerParty(cm.getPlayer().getParty(), cm.getPlayer().getMap());
}

function isPartyLeader() {
    if (cm.getPlayer().getParty().getLeader().getId() == cm.getPlayer().getId())
        return true;
    else
        return false;
}

function AC(player, boss) {
    player.CountAdd(boss);
    if (!practice) {
        player.updateCanTime("dusk_can_time", 1);
    }
}

function CC(player, boss, limit) {
    var q = player.getOneInfoQuest(1234589, "dusk_clear");
    if (q != null && !q.isEmpty() && q == "1" && !practice) {
        return false;
    }
    return player.CountCheck(boss, limit);
}