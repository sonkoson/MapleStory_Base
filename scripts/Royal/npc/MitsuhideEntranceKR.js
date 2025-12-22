var status = -1;

var purpleIcon = "#fMap/MapHelper.img/weather/starPlanet/7#";
var blueIcon = "#fMap/MapHelper.img/weather/starPlanet/8#";
var starBlue = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
var starYellow = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
var starWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
var starBrown = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
var starRed = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
var starBlack = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
var starPurple = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
var starIcon = "#fUI/FarmUI.img/objectStatus/star/whole#"
var sIcon = "#fUI/CashShop.img/CSEffect/today/0#"
var rewardIcon = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
var obtainIcon = "#fUI/UIWindow2.img/QuestIcon/4/0#"
var colorTag = "#fc0xFF6600CC#"
var lineBreak = "\r\n";
var doubleLineBreak = "\r\n\r\n";

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    var count = 1;
    var bossSettings = [
        ["BossAMN", 1, 874004002, 220], // Normal
        ["BossAMH", 1, 874004002, 280], // Hard
    ]
    var bossNames = ["Akechi Mitsuhide (Normal)", "Akechi Mitsuhide (Hard)"]
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        if (cm.getPlayer().getMapId() == 874004002) {
            cm.sendYesNo("การต่อสู้จบลงแล้ว เธอต้องการจะออกจากที่นี่เพื่อกลับไปยังหมู่บ้านเลยไหมจ๊ะ?");
        } else {
            var dialogue = "#fs11#เธอพร้อมที่จะเผชิญหน้ากับซามูไรแห่งความตาย Akechi Mitsuhide หรือยังจ๊ะ?\r\n\r\n"
            dialogue += colorTag + "#L0# [Event Boss] - #b[Normal] #rAkechi Mitsuhide #bลงทะเบียนเข้าท้าทาย\r\n";
            dialogue += colorTag + "#L1# [Event Boss] - #b[Hard] #rAkechi Mitsuhide #bลงทะเบียนเข้าท้าทาย";
            cm.sendSimple(dialogue);
        }
    } else if (status == 1) {
        if (cm.getPlayer().getMapId() == 874004002) {
            cm.warp(100000000);
            cm.dispose();
            return;
        }
        var selectedBossIndex = selection;
        if (cm.getPlayer().getParty() == null) {
            cm.sendOk("เธอต้องอยู่ในปาร์ตี้อย่างน้อย 1 คนขึ้นไปถึงจะเข้าได้นะจ๊ะ");
            cm.dispose();
            return;
        } else if (!cm.isLeader()) {
            cm.sendOk("ให้หัวหน้าปาร์ตี้เป็นคนมาคุยกับฉันนะจ๊ะ");
            cm.dispose();
            return;
        } else if (!cm.allMembersHere()) {
            cm.sendOk("สมาชิกทุกคนในปาร์ตี้ต้องอยู่ที่นี่ด้วยกันถึงจะเข้าได้จ้ะ");
            cm.dispose();
            return;
        } else if (cm.getPlayerCount(bossSettings[selectedBossIndex][2]) >= 1) {
            cm.sendNext("ตอนนี้มีคนอื่นกำลังท้าทายอยู่จ้ะ รบกวนเธอย้ายแชลแนลแล้วลองใหม่นะ");
            cm.dispose();
            return;
        }
        if (!cm.isBossAvailable2(bossSettings[selectedBossIndex][0], bossSettings[selectedBossIndex][1])) {
            var unavailableList = "#fs11#สมาชิกในปาร์ตี้ของเธอ: "
            for (var i = 0; i < cm.BossNotAvailableChrList2(bossSettings[selectedBossIndex][0], bossSettings[selectedBossIndex][1]).length; i++) {
                if (i != 0) {
                    unavailableList += ", "
                }
                unavailableList += "#b#e" + cm.BossNotAvailableChrList2(bossSettings[selectedBossIndex][0], bossSettings[selectedBossIndex][1])[i] + ""
            }
            unavailableList += "#k#n ใช้สิทธิ์ในการท้าทายของวันนี้ไปหมดแล้วจ้ะ";
            cm.sendOk(unavailableList);
            cm.dispose();
            return;
        } else if (!cm.isLevelAvailable(bossSettings[selectedBossIndex][3])) {
            var lowLevelList = "#fs11#สมาชิกในปาร์ตี้ของเธอ: #b#e"
            for (var i = 0; i < cm.LevelNotAvailableChrList(bossSettings[selectedBossIndex][3]).length; i++) {
                if (i != 0) {
                    lowLevelList += ", "
                }
                lowLevelList += "#b#e" + cm.LevelNotAvailableChrList(bossSettings[selectedBossIndex][3])[i] + ""
            }
            lowLevelList += "#k#n เลเวลยังไม่ถึงเกณฑ์นะจ๊ะ สำหรับ " + bossNames[selectedBossIndex] + " ต้องมีเลเวลอย่างน้อย " + bossSettings[selectedBossIndex][3] + " ขึ้นไปนะ";
            cm.sendOk(lowLevelList);
            cm.dispose();
            return;
        } else {
            cm.addBoss(bossSettings[selectedBossIndex][0]);
            var eventManager = cm.getEventManager(bossSettings[selectedBossIndex][0]);
            if (eventManager != null) {
                eventManager.startInstance_Party(bossSettings[selectedBossIndex][2] + "", cm.getPlayer());
            }
            cm.dispose();
        }
    }
}



