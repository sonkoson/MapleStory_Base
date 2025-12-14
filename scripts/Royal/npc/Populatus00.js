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
    220080100, // Easy
    220080200, // Normal
    220080300, // Chaos
    220080300, // Chaos Practice
];
var exitmap = 15;
var bossname = "Papulatus"
var time = 30;
var deathcount = 5;
var practice = false;
var checkItem = false;
var diff = ["Easy", "Normal", "Chaos", "Chaos Practice"];

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
        if (cm.getPlayer().getParty() == null) {
            cm.sayNpc("ต้องมีปาร์ตี้อย่างน้อย 1 คนถึงจะเข้าได้", GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
            cm.dispose();
            return;
        }
        text = "#e<Boss: Papulatus>#n\r\n"
        text += "ต้องหยุดยั้งเจ้าตัวป่วน Papulatus ไม่ให้ทำลายมิติ ช่วยหน่อยได้ไหม?\r\n\r\n\r\n"
        text += "#L1# Normal Mode ( เลเวล 155 ขึ้นไป )"
        text += " #r[" + cm.getPlayer().getOneInfoQuestInteger(1234569, "papulatus_clear") + "/" + (cm.getPlayer().getBossTier() + 1) + "]#l#k\r\n";
        text += "#L2# Chaos Mode ( เลเวล 190 ขึ้นไป )"
        text += " #r[" + cm.getPlayer().getOneInfoQuestInteger(1234569, "chaos_papulatus_clear") + "/" + (cm.getPlayer().getBossTier() + 1) + "]#l#k";
        for (i = 0; i < cm.getPlayer().getPartyMembers().size(); i++) {
            chr = cm.getPlayer().getPartyMembers().get(i);
            chr.dropMessage(5, "[Normal Papulatus] วันนี้เข้าบอสนี้ไปแล้ว " + chr.getOneInfoQuestInteger(1234569, "papulatus_clear") + " ครั้ง เข้าได้ทั้งหมด " + (chr.getBossTier() + 1) + " ครั้ง");
            chr.dropMessage(5, "[Chaos Papulatus] วันนี้เข้าบอสนี้ไปแล้ว " + chr.getOneInfoQuestInteger(1234569, "chaos_papulatus_clear") + " ครั้ง เข้าได้ทั้งหมด " + (chr.getBossTier() + 1) + " ครั้ง");
        }
        text += "\r\n#L3# Chaos Practice Mode ( เลเวล 190 ขึ้นไป )#l";

        cm.askMenu(text, GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
    } else if (status == 1) {
        difficulty = selection;
        bn = diff[difficulty] + " " + bossname;
        if (selection == 3) {
            practice = true;
        }
        if (!cm.haveItem(4031179)) {
            if (!cm.canHold(4031179, 1)) {
                cm.sayNpc("ช่องเก็บของ ETC ไม่เพียงพอ กรุณาทำช่องว่างให้เพียงพอ", GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
                cm.dispose();
                return;
            }
            cm.gainItem(4031179, 1);
            cm.sayNpc("ดูเหมือนจะไม่มีชิ้นส่วนรอยแยกมิติ จำเป็นต้องมีเพื่อไปเจอ Papulatus ข้าจะให้ส่วนที่ข้ามี ดังนั้นช่วยหยุดยั้ง Papulatus จากการทำลายมิติด้วยนะ!", GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
            checkItem = true;
        } else {
            if (practice) {
                cm.askYesNo("เลือกเข้าสู่โหมดฝึกซ้อม ในโหมดฝึกซ้อม #b#eจะไม่ได้รับ EXP และของรางวัล#n#k และเข้าได้เพียง #b#eวันละ 5 ครั้ง#n#k โดยไม่คำนึงถึงชนิดบอส ต้องการเข้าหรือไม่?", GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
            } else {
                enterBoss();
            }
        }
    } else if (status == 2) {
        if (checkItem) {
            if (practice) {
                cm.askYesNo("เลือกเข้าสู่โหมดฝึกซ้อม ในโหมดฝึกซ้อม #b#eจะไม่ได้รับ EXP และของรางวัล#n#k และเข้าได้เพียง #b#eวันละ 5 ครั้ง#n#k โดยไม่คำนึงถึงชนิดบอส ต้องการเข้าหรือไม่?", GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
            } else {
                enterBoss();
            }
        } else {
            enterBoss();
        }
    } else if (status == 3) {
        enterBoss();
    }
}

function enterBoss() {
    var n = "papulatus_clear";
    if (difficulty == 2) {
        // Chaos 1/week, Normal/Easy 1/day (Standard logic, but keeping script logic)
        n = "chaos_papulatus_clear";
    }
    if (cm.getPlayer().getParty() == null) {
        cm.sayNpc("ต้องมีปาร์ตี้อย่างน้อย 1 คนถึงจะเข้าได้", GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
        cm.dispose();
        return;
    }
    var p = cm.getPlayer();

    if (!practice) {
        // Clear Check
        for (i = 0; i < cm.getPlayer().getPartyMembers().size(); i++) {
            chr = cm.getPlayer().getPartyMembers().get(i);
            var q = chr.getOneInfoQuestInteger(1234569, n);
            if (q >= chr.getBossTier() + 1) {
                if (DBConfig.isGanglim) {
                    cm.sayNpc("มีสมาชิกในปาร์ตี้ใช้จำนวนครั้งเข้าเล่นหมดแล้ว สามารถลองใหม่ได้หลังรีเซ็ตตอนเที่ยงคืน", GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
                } else {
                    cm.sayNpc("มีสมาชิกในปาร์ตี้ที่ชนะบอสไปแล้ววันนี้ สามารถลองใหม่ได้หลังรีเซ็ตตอนเที่ยงคืน", GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
                }
                cm.dispose();
                return;
            }
        }
    }
    var keyValue = "papulatus_c"; // Easy/Normal Combined (Exclude Chaos)
    if (practice) {
        keyValue = "boss_practice";
        if (!cm.CountCheck(keyValue, 20)) {
            cm.sayNpc("สามารถเข้าฝึกซ้อมได้วันละ 20 ครั้งเท่านั้น", GameObjectType.User, ScriptMessageFlag.NpcReplacedByNpc);
            cm.dispose();
            return;
        }
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
    if (!practice && difficulty == 2 && !DBConfig.isGanglim) {
        if (!cm.canEnterBoss("papulatus_can_time")) {
            return;
        }
    }
    var it = cm.getClient().getChannelServer().getPartyMembers(cm.getParty()).iterator();
    var countPass = true;
    var timePass = true;
    var levelLimit = 115;
    if (difficulty == 1) {
        levelLimit = 155;
    } else if (difficulty == 2) {
        levelLimit = 190;
    }
    while (it.hasNext()) {
        var chr = it.next();
        if (chr.getLevel() < levelLimit) {
            countPass = false;
            break;
        }
        var q = chr.getOneInfoQuest(1234569, n);
        if (q != null && !q.isEmpty() && q >= cm.getPlayer().getBossTier() + 1) {
            countPass = false;
            break;
        }
        if (!DBConfig.isGanglim && !chr.canEnterBoss("papulatus_can_time") && difficulty == 2) {
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
        if (!DBConfig.isGanglim) {
            var canTimeKey = null;
            if (!practice && difficulty == 2) {
                canTimeKey = "papulatus_can_time";
            }
        }
        cm.setBossEnter(cm.getParty(), bn, keyValue, canTimeKey, 3);
    }

    it = cm.getClient().getChannelServer().getPartyMembers(cm.getParty()).iterator();
    while (it.hasNext()) {
        var chr = it.next();
        chr.setCurrentBossPhase(1);
        if (DBConfig.isGanglim) {
            if (!practice) {
                chr.updateOneInfo(1234569, n, (chr.getOneInfoQuestInteger(1234569, n) + 1) + "");
            }
        }
    }
    cm.resetMap(startmap[difficulty]);
    cm.dispose();

    if (difficulty == 0) { // Easy Mode Death Count 50
        deathcount = 50;
    }
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