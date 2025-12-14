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
    450012210, // Normal
    450012600, // Hard
    450012210, // Normal Practice
    450012600, // Hard Practice
];
var exitmap = 15;
var bossname = "Dunkel"
var limit = [
    3, // Normal
    3, // Hard
    20, // Normal Practice
    20 // Hard Practice
];
var time = 30;
var deathcount = 5;
var practice = false;

var diff = ["Normal", "Hard", "Normal Practice", "Hard Practice"];

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
        cm.askMenuScenarioSelf("ต้องหยุดยั้งหัวหน้าหน่วยองครักษ์ Dunkel และหน่วยองครักษ์ของเขา\r\n#L0##bไปยังสถานที่ใกล้จุดจบ (Normal Mode)#r(Lv. 255+)#k#l\r\n#L1##bไปยังสถานที่ใกล้จุดจบ (Hard Mode)#r(Lv. 255+)#k#l\r\n#L2##bไปยังสถานที่ใกล้จุดจบ (Normal Practice Mode)#r(Lv. 255+)#k#l\r\n#L3##bไปยังสถานที่ใกล้จุดจบ (Hard Practice Mode)#r(Lv. 255+)#k#l", ScriptMessageFlag.FlipImage);
    } else if (status == 1) {
        difficulty = selection;
        bn = diff[difficulty] + " " + bossname;
        if (selection == 2 || selection == 3) {
            practice = true;
        }
        if (practice) {
            cm.askYesNo("เลือกเข้าสู่โหมดฝึกซ้อม ในโหมดฝึกซ้อม #b#eจะไม่ได้รับ EXP และของรางวัล#n#k และเข้าได้เพียง #b#eวันละ 5 ครั้ง#n#k โดยไม่คำนึงถึงชนิดบอส ต้องการเข้าหรือไม่?", GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
        } else {
            enterBoss();
        }
    } else if (status == 2) {
        enterBoss();
    }
}

function enterBoss() {
    var n = "dunkel_clear";
    var p = cm.getPlayer();
    var q = cm.getPlayer().getOneInfoQuest(1234589, n);
    if (q != null && !q.isEmpty() && q == "1" && !practice) {
        cm.sayNpc("สัปดาห์นี้ได้กำจัดไปแล้ว จะสามารถท้าทายได้ใหม่หลังจากรีเซ็ตในวันพฤหัสบดี เวลา 00:00 น.", GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
        cm.dispose();
        return;
    }

    var keyValue = bn + "c";
    if (practice) {
        keyValue = "boss_practice";
    }
    if (!cm.CountCheck(keyValue, limit[difficulty])) {
        cm.sayNpc("สามารถท้าทายได้วันละ " + limit[difficulty] + " ครั้ง และหลังจากกำจัดได้แล้ว จะสามารถท้าทายได้ใหม่ตั้งแต่วันพฤหัสบดีเป็นต้นไป", GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
        cm.dispose();
        return;
    }
    if (cm.getPlayer().getParty() == null) {
        cm.sayNpc("ต้องมีปาร์ตี้อย่างน้อย 1 คนถึงจะเข้าได้", GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
        cm.dispose();
        return;
    }
    if (!isPartyLeader()) {
        cm.sayNpc("เฉพาะหัวหน้าปาร์ตี้เท่านั้นที่สามารถขอเข้าได้", GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
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
        if (!cm.canEnterBoss("dunkel_can_time")) {
            return;
        }
    }
    var it = cm.getClient().getChannelServer().getPartyMembers(cm.getParty()).iterator();
    var countPass = true;
    var timePass = true;
    while (it.hasNext()) {
        var chr = it.next();
        if (chr.getLevel() < 255) {
            countPass = false;
            break;
        }
        if (!CC(chr, keyValue, limit[difficulty])) {
            countPass = false;
            break;
        }
        if (!chr.canEnterBoss("dunkel_can_time")) {
            timePass = false;
            break;
        }
    }
    if (!timePass && !practice) {
        cm.sayNpc("มีสมาชิกในปาร์ตี้ที่ยังติดเวลาหน่วงการเข้าอยู่", GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
        cm.dispose();
        return;
    }
    if (!countPass) {
        cm.sayNpc("มีสมาชิกในปาร์ตี้ที่เข้าได้ไม่พอหรือเลเวลไม่ถึงเกณฑ์", GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
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
        player.updateCanTime("dunkel_can_time", 0);
    }
}

function CC(player, boss, limit) {
    var q = player.getOneInfoQuest(1234589, "dunkel_clear");
    if (q != null && !q.isEmpty() && q == "1" && !practice) {
        return false;
    }
    return player.CountCheck(boss, limit);
}