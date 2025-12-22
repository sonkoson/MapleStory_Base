
var enter = "\r\n";
var seld = -1;

var purple = "#fMap/MapHelper.img/weather/starPlanet/7#";
var blue = "#fMap/MapHelper.img/weather/starPlanet/8#";
var starBlue = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
var starYellow = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
var starWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
var starBrown = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
var starRed = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
var starBlack = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
var starPurple = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
var star = "#fUI/FarmUI.img/objectStatus/star/whole#"
var S = "#fUI/CashShop.img/CSEffect/today/0#"
var reward = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
var obtain = "#fUI/UIWindow2.img/QuestIcon/4/0#"
var color = "#fc0xFF6600CC#"
var black = "#fc0xFF000000#"
var pink = "#fc0xFFFF3366#"
var pink2 = "#fc0xFFF781D8#"
var enter2 = "\r\n\r\n"

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
        if (cm.inBoss()) {
            cm.getPlayer().dropMessage(5, "‰¡Ë “¡“√∂„™Èß“π‰¥È¢≥– ŸÈ°—∫∫Õ ®È–");
            cm.dispose();
            return;
        }

        var msg = "";
        msg += "#fs12##b#e   [Daily Quest]#k#n" + enter + enter;
        msg += "#L1##fs11##fUI/UIWindow.img/Quest/icon0#  (Lv. 190)#d Abandoned Camp#l#n#k" + enter;
        msg += "#L2##fs11##fUI/UIWindow.img/Quest/icon0#  (Lv. 190)#d Haven#l#n#k" + enter;
        msg += "#L3##fs11##fUI/UIWindow.img/Quest/icon0#  (Lv. 200)#d Arcane Force#l#n#k" + enter;
        msg += "#L4##fs11##fUI/UIWindow.img/Quest/icon0#  (Lv. 265)#d Authentic Force#l#n" + enter;
        msg += "#L5##fs11##fUI/UIWindow.img/Quest/icon0#  (Lv. 265)#d Royal World Exploration#l#n#k" + enter;

        msg += enter + enter;

        msg += "#fs12##b#e   [Repeatable Quest]#k#n" + enter + enter;
        msg += "#L9##fs11##fUI/UIWindow.img/Quest/icon0#  Lost Crayon#l" + enter;
        cm.sendSimple(msg);
    } else if (status == 1) {
        seld = sel;
        switch (sel) {
            case 1:
                cm.dispose();

                if (cm.getPlayer().getLevel() < 190) {
                    cm.sendOk("#fs11#" + color + "‡≈‡«≈‰¡Ë∂÷ß®È–");
                    return;
                }

                cm.openNpcCustom(cm.getClient(), 3004434, "AbandonedCamp");
                break;
            case 2:
                cm.dispose();

                if (cm.getPlayer().getLevel() < 190) {
                    cm.sendOk("#fs11#" + color + "‡≈‡«≈‰¡Ë∂÷ß®È–");
                    return;
                }

                cm.openNpcCustom(cm.getClient(), 3004434, "HavenDailyQuest");
                break;
            case 3:
                cm.dispose();

                if (cm.getPlayer().getLevel() < 200) {
                    cm.sendOk("#fs11#" + color + "‡≈‡«≈‰¡Ë∂÷ß®È–");
                    return;
                }

                cm.openNpcCustom(cm.getClient(), 3004434, "ArcaneDailyQuest");
                break;
            case 4:
                cm.dispose();

                if (cm.getPlayer().getLevel() < 265) {
                    cm.sendOk("#fs11#" + color + "‡≈‡«≈‰¡Ë∂÷ß®È–");
                    return;
                }

                cm.openNpcCustom(cm.getClient(), 3004434, "AuthenticDailyQuest");
                break;
            case 5:
                cm.dispose();

                if (cm.getPlayer().getLevel() < 265) {
                    cm.sendOk("#fs11#" + color + "‡≈‡«≈‰¡Ë∂÷ß®È–");
                    return;
                }

                cm.openNpc(1052230, "DailyQuestRainbowLight");
                break;
            case 9:
                cm.dispose();
                cm.openNpc(2082006);
                break;
        }

    }
}



