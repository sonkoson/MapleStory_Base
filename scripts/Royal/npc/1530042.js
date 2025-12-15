// Royal Growth System
var enter = "\r\n";
var seld = -1;

var Pocket = "#fUI/Basic.img/RoyalBtn/theblackcoin/23#";
var BlackMage = "#fUI/Basic.img/RoyalBtn/theblackcoin/42#";

var Purple = "#fMap/MapHelper.img/weather/starPlanet/7#";
var Blue = "#fMap/MapHelper.img/weather/starPlanet/8#";
var StarBlue = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
var StarYellow = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
var StarWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
var StarBrown = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
var StarRed = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
var StarBlack = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
var StarPurple = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
var Star = "#fUI/FarmUI.img/objectStatus/star/whole#"
var S = "#fUI/CashShop.img/CSEffect/today/0#"
var Reward = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
var Obtain = "#fUI/UIWindow2.img/QuestIcon/4/0#"
var Color = "#fc0xFF6600CC#"
var Black = "#fc0xFF000000#"
var Pink = "#fc0xFFFF3366#"
var LightPink = "#fc0xFFF781D8#"
var Enter = "\r\n"
var Enter2 = "\r\n\r\n"
enter = "\r\n"

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
            cm.getPlayer().dropMessage(5, "You cannot use this feature while in a boss battle.");
            cm.dispose();
            return;
        }

        cm.getClient().setKeyValue("current", "1");
        // [Temp] Daily Quest Error Fix

        var msg = "#fs11#       #fUI/Basic.img/Zenia/SC/0#\r\n";
        msg += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――\r\n";
        msg += "        #L1##fUI/Basic.img/Zenia/SCBtn/400##l      ";//Growth Quest
        msg += "#L2##fUI/Basic.img/Zenia/SCBtn/402##l\r\n";//Daily Quest
        msg += "        #L1000##fUI/Basic.img/Zenia/SCBtn/405##l      ";//Stat Rank
        msg += "#L3##fUI/Basic.img/Zenia/SCBtn/403##l";//Level-up Reward
        //msg +="#L999##fUI/Basic.img/Zenia/SCBtn/404##l";//Hunt Pass
        //msg +="#L777##fUI/Basic.img/Zenia/SCBtn/401##l";//Meso Pass
        cm.sendSimple(msg);
    } else if (status == 1) {
        seld = sel;
        switch (sel) {
            case 1:
                cm.dispose();
                cm.openNpc(9000368);
                break;
            case 2:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 1530042, "DailyQuest");
                break;
            case 3:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 1530042, "LevelReward");
                break;

            case 777: // Season Pass
                var msg = "#fs11#";
                msg += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――\r\n#fc0xFFFF3300#";
                msg += Color + "            #L1#" + BlackMage + " Season Pass#l#L2#" + Pocket + Pink + " [Premium]" + Color + " Season Pass#l\r\n\r\n";
                msg += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――\r\n#fc0xFFFF3300#";
                cm.sendSimple(msg);
                break;
            case 999: // Ability
                cm.dispose();
                Packages.scripting.newscripting.ScriptManager.runScript(cm.getPlayer().getClient(), "test", null);
                break;
            case 1000: // Rank Up
                var msg = "#fs11#";

                msg += "    #L1##bMain Rank Up#l  ";
                msg += "#L2#Boss Rank Up#l  ";
                msg += "#L3#Stat Rank Up#l";

                cm.sendSimple(msg);
                break;
            case 1001: // Equipment Enhancement
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 1530050, "eqpupgrade");
                break;
        }
    } else if (status == 2) {
        switch (seld) {
            case 777:
                switch (sel) {
                    case 1:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 9000213, "시즌패스");
                        break;
                    case 2:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 9000213, "프리미엄패스");
                        break;
                }
                break;
            case 1000:
                switch (sel) {
                    case 1:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 9071000, "ZodiacRank");
                        break;
                    case 2:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 9071000, "BossRank");
                        break;
                    case 3:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 9071000, "StatRank");
                        break;
                }
                break;
        }
    }
}