importPackage(Packages.scripting.newscripting)
importPackage(Packages.constants)

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

var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        cm.dispose();
        cm.openUI(1338); // Custom Boss UI
        return;

        // The code below is currently unreachable due to the return above
        if (cm.inBoss() && !cm.isLeader) {
            cm.getPlayer().dropMessage(5, "ไม่สามารถใช้งานได้ในขณะที่กำลังท้าทายบอสอยู่จ้ะ");
            cm.dispose();
            return;
        }

        var maxEntryCount = 1;
        maxEntryCount += cm.getPlayer().getBossTier();
        var message = "#fs11#";

        message += "#fc0xFF000000#? เฉพาะหัวหน้าปาร์ตี้เท่านั้นที่สามารถเคลื่อนย้ายไปหาบอสได้นะจ๊ะ\r\n\r\n";
        message += "#fc0xFF000000##e#r[EASY]#k#l\r\n";
        message += "#L1##fc0xFF6799FF#[" + cm.getPlayer().getOneInfoQuestInteger(QuestExConstants.ChaosZakum.getQuestID(), "eNum") + "/" + maxEntryCount + "]#d " + starYellow + " | Zakum#l?? ??";
        message += "#L3##fc0xFF6799FF#[" + cm.getPlayer().getOneInfoQuestInteger(QuestExConstants.Horntail.getQuestID(), "eNum") + "/" + maxEntryCount + "]#d " + starYellow + " | Horntail#l\r\n";
        message += "#L5##fc0xFF6799FF#[" + cm.getPlayer().getOneInfoQuestInteger(1234571, "vonleon_clear") + "/" + maxEntryCount + "]#d " + starYellow + " | Von Leon#l?? ?";
        message += "#L6##fc0xFF6799FF#[" + cm.getPlayer().getOneInfoQuestInteger(1234570, "akairum_clear") + "/" + maxEntryCount + "]#d " + starYellow + " | Arkarium#l\r\n";
        message += "#L7##fc0xFF6799FF#[" + cm.getPlayer().getOneInfoQuestInteger(1234569, "pinkbean_clear") + "/" + maxEntryCount + "]#d " + starYellow + " | Pink Bean#l?? ?";
        message += "#L8##fc0xFF6799FF#[" + cm.getPlayer().getOneInfoQuestInteger(QuestExConstants.Cygnus.getQuestID(), "eNum") + "/" + maxEntryCount + "]#d " + starYellow + " | Cygnus#l#k\r\n";

        message += "\r\n#fs11##fc0xFF000000##e#r[NORMAL]#k#l#k\r\n";
        message += "#L9##fc0xFF6799FF#[" + cm.getPlayer().getOneInfoQuestInteger(QuestExConstants.Magnus.getQuestID(), "eNum") + "/" + maxEntryCount + "][" + cm.getPlayer().getOneInfoQuestInteger(QuestExConstants.HardMagnus.getQuestID(), "eNum") + "/" + maxEntryCount + "]#d " + starBlue + " | Magnus#l";
        message += "#L11##fc0xFF6799FF#[" + cm.getPlayer().getOneInfoQuestInteger(1234569, "papulatus_clear") + "/" + maxEntryCount + "][" + cm.getPlayer().getOneInfoQuestInteger(1234569, "chaos_papulatus_clear") + "/" + maxEntryCount + "]#d " + starBlue + " | Papulatus#k#l\r\n";
        message += "#L10##fc0xFF6799FF#[" + cm.getPlayer().getOneInfoQuestInteger(QuestExConstants.CrimsonQueen.getQuestID(), "eNum") + "/" + maxEntryCount + "][" + cm.getPlayer().getOneInfoQuestInteger(QuestExConstants.ChaosCrimsonQueen.getQuestID(), "eNum") + "/" + maxEntryCount + "]#d " + starBlue + " | Bloody Queen#l";
        message += "#L10##fc0xFF6799FF#[" + cm.getPlayer().getOneInfoQuestInteger(QuestExConstants.Pierre.getQuestID(), "eNum") + "/" + maxEntryCount + "][" + cm.getPlayer().getOneInfoQuestInteger(QuestExConstants.ChaosPierre.getQuestID(), "eNum") + "/" + maxEntryCount + "]#d " + starBlue + " | Pierre#l\r\n";
        message += "#L10##fc0xFF6799FF#[" + cm.getPlayer().getOneInfoQuestInteger(QuestExConstants.VonBon.getQuestID(), "eNum") + "/" + maxEntryCount + "][" + cm.getPlayer().getOneInfoQuestInteger(QuestExConstants.ChaosVonBon.getQuestID(), "eNum") + "/" + maxEntryCount + "]#d " + starBlue + " | Von Bon#l??";
        message += "#L10##fc0xFF6799FF#[" + cm.getPlayer().getOneInfoQuestInteger(QuestExConstants.Vellum.getQuestID(), "eNum") + "/" + maxEntryCount + "][" + cm.getPlayer().getOneInfoQuestInteger(QuestExConstants.ChaosVellum.getQuestID(), "eNum") + "/" + maxEntryCount + "]#d " + starBlue + " | Vellum#l\r\n";

        message += "\r\n#fs11##fc0xFF000000##e#r[HARD]#k#l#k\r\n";
        message += "#L12##fc0xFF6799FF#[" + cm.getPlayer().getOneInfoQuestInteger(1234570, "swoo_clear") + "/" + maxEntryCount + "][" + cm.getPlayer().getOneInfoQuestInteger(1234569, "swoo_clear") + "/" + maxEntryCount + "]#d " + starRed + " | Lotus (Swoo)#l??";
        message += "#L13##fc0xFF6799FF#[" + cm.getPlayer().getOneInfoQuestInteger(1234570, "demian_clear") + "/" + maxEntryCount + "][" + cm.getPlayer().getOneInfoQuestInteger(1234569, "demian_clear") + "/" + maxEntryCount + "]#d " + starRed + " | Damien#l\r\n";
        message += "#L14##fc0xFF6799FF#[" + cm.getPlayer().getOneInfoQuestInteger(1234570, "lucid_clear") + "/" + maxEntryCount + "][" + cm.getPlayer().getOneInfoQuestInteger(1234569, "lucid_clear") + "/" + maxEntryCount + "]#d " + starRed + " | Lucid#l?";
        message += "#L15##fc0xFF6799FF#[" + cm.getPlayer().getOneInfoQuestInteger(1234570, "will_clear") + "/" + maxEntryCount + "][" + cm.getPlayer().getOneInfoQuestInteger(1234569, "will_clear") + "/" + maxEntryCount + "]#d " + starRed + " | Will#l\r\n";
        message += "#L19##fc0xFF6799FF#[0/0][" + cm.getPlayer().getOneInfoQuestInteger(1234589, "dunkel_clear") + "/" + maxEntryCount + "]#d " + starRed + " | Dunkel#l??";
        message += "#L20##fc0xFF6799FF#[" + cm.getPlayer().getOneInfoQuestInteger(1234590, "dusk_clear") + "/" + maxEntryCount + "][" + cm.getPlayer().getOneInfoQuestInteger(1234589, "dusk_clear") + "/" + maxEntryCount + "]#d " + starRed + " | Dusk#l\r\n";
        message += "#L23##fc0xFF6799FF#[" + cm.getPlayer().getOneInfoQuestInteger(1234570, "guardian_angel_slime_clear") + "/" + maxEntryCount + "][" + cm.getPlayer().getOneInfoQuestInteger(1234569, "guardian_angel_slime_clear") + "/" + maxEntryCount + "]#d " + starRed + " | Guardian Angel Slime#l\r\n\r\n\r\n";

        message += "???#L18##fc0xFF6799FF#[" + cm.getPlayer().getOneInfoQuestInteger(1234569, "jinhillah_clear") + "/" + maxEntryCount + "]#d " + starBlack + " | Verus Hilla (Jin Hillah)#l\r\n";
        message += "???#L21##fc0xFF6799FF#[" + cm.getPlayer().getOneInfoQuestInteger(1234570, "blackmage_clear") + "/" + maxEntryCount + "]#d " + starBlack + " | Black Mage#l\r\n";
        message += "???#L22##fc0xFF6799FF#[" + cm.getPlayer().getOneInfoQuestInteger(QuestExConstants.SerniumSeren.getQuestID(), "clear") + "/" + maxEntryCount + "]#d " + starBlack + " | Chosen Seren#l\r\n ";
        cm.sendSimpleS(message, 4);
    } else if (status == 1) {
        selectedBossOption = selection;

        if (!cm.isLeader()) {
            cm.getPlayer().dropMessage(5, "เธอต้องเป็นหัวหน้าปาร์ตี้ถึงจะใช้งานฟังก์ชันนี้ได้นะจ๊ะ");
            cm.dispose();
            return;
        }

        if (cm.inBoss() && !cm.isLeader) {
            cm.getPlayer().dropMessage(5, "ไม่สามารถใช้งานได้ในขณะที่กำลังท้าทายบอสอยู่จ้ะ");
            cm.dispose();
            return;
        }

        if (selectedBossOption <= 0) {
            cm.dispose();
            return;
        }

        switch (selectedBossOption) {
            case 2:
                cm.dispose();
                cm.warpParty(221030900, 0);
                return;
            case 1:
                cm.dispose();
                cm.warpParty(211042300, 1);
                return;

            case 3:
                cm.dispose();
                cm.warpParty(240050000, 0);
                return;

            case 4:
                cm.dispose();
                cm.warpParty(262000000, 0);
                return;

            case 5:
                cm.dispose();
                cm.warpParty(211070000, 2);
                return;

            case 6:
                cm.dispose();
                cm.warpParty(272020110, 0);
                return;

            case 7:
                cm.dispose();
                cm.warpParty(270050000, 0);
                return;

            case 8:
                cm.dispose();
                cm.sendOk("#fs11#ตอนนี้ยังไม่สามารถท้าทาย Cygnus ได้จ้ะ");
                return;

            case 9:
                cm.dispose();
                cm.warpParty(401060000, 0);
                return;

            case 10:
                cm.dispose();
                cm.warpParty(105200000, 0);
                return;

            case 11:
                cm.dispose();
                cm.warpParty(220080000, 6);
                return;

            case 12:
                cm.dispose();
                cm.warpParty(350060300, 1);
                return;

            case 13:
                cm.dispose();
                cm.warpParty(105300303, 1);
                return;

            case 14:
                cm.dispose();
                cm.warpParty(450004000, 0);
                return;

            case 15:
                cm.dispose();
                cm.warpParty(450007240, 0);
                return;

            case 16:
                cm.dispose();
                cm.openNpc(9120012);
                return;

            case 17:
                cm.dispose();
                cm.warpParty(970072200, 0);
                return;

            case 18:
                cm.dispose();
                cm.warpParty(450011990, 0);
                return;

            case 19:
                cm.dispose();
                cm.warpParty(450012200, 0);
                return;

            case 20:
                cm.dispose();
                cm.warpParty(450009301, 1);
                return;

            case 21:
                cm.dispose();
                cm.warpParty(450012500, 0);
                return;

            case 22:
                cm.dispose();
                cm.warpParty(410000670, 2);
                return;
            case 23:
                cm.dispose();
                cm.warpParty(160000000, 0);
                return;
            case 30:
                cm.dispose();
                ScriptManager.runScript(cm.getClient(), "tengu_enter", null);
                return;
            case 31:
                cm.dispose();
                ScriptManager.runScript(cm.getClient(), "mitsuhide_enter", null);
                return;
        }
    }
}




