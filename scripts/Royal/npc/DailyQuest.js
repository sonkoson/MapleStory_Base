var enter = "\r\n";
var seld = -1;

var pearl = "#fMap/MapHelper.img/weather/starPlanet/7#";
var blue = "#fMap/MapHelper.img/weather/starPlanet/8#";
var starBlue = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
var starYellow = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
var starWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
var starBrown = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
var starRed = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
var starBlack = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
var starPurple = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
var star = "#fUI/FarmUI.img/objectStatus/star/whole#"
var sEffect = "#fUI/CashShop.img/CSEffect/today/0#"
var rewardIcon = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
var obtainIcon = "#fUI/UIWindow2.img/QuestIcon/4/0#"
var colorPurple = "#fc0xFF6600CC#"
var colorBlack = "#fc0xFF000000#"
var colorPink = "#fc0xFFFF3366#"
var colorLightPink = "#fc0xFFF781D8#"
// var enter is already defined

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
            cm.getPlayer().dropMessage(5, "คุณไม่สามารถใช้งานได้ในระหว่างการต่อสู้กับบอส");
            cm.dispose();
            return;
        }

        var msg = "";
        msg += "#fs12##b#e   [ภารกิจรายวัน]#k#n" + enter + enter;
        msg += "#L1##fs11##fUI/UIWindow.img/Quest/icon0#  (เลเวล 190)#d ค่ายทหารร้าง#l#n#k" + enter;
        msg += "#L2##fs11##fUI/UIWindow.img/Quest/icon0#  (เลเวล 190)#d เฮเว่น#l#n#k" + enter;
        msg += "#L3##fs11##fUI/UIWindow.img/Quest/icon0#  (เลเวล 200)#d อาร์เคนฟอร์ซ#l#n#k" + enter;
        msg += "#L4##fs11##fUI/UIWindow.img/Quest/icon0#  (เลเวล 265)#d ออเธนติกฟอร์ซ#l#n" + enter;
        msg += "#L5##fs11##fUI/UIWindow.img/Quest/icon0#  (เลเวล 265)#d สำรวจโลกกังลิม#l#n#k" + enter;

        msg += enter + enter;

        msg += "#fs12##b#e   [ภารกิจทำซ้ำ]#k#n" + enter + enter;
        msg += "#L9##fs11##fUI/UIWindow.img/Quest/icon0#  ดินสอสีที่หายไป#l" + enter;
        cm.sendSimple(msg);
    } else if (status == 1) {
        seld = sel;
        switch (sel) {
            case 1:
                cm.dispose();

                if (cm.getPlayer().getLevel() < 190) {
                    cm.sendOk("#fs11#" + colorPurple + "เลเวลไม่เพียงพอ");
                    return;
                }

                cm.openNpcCustom(cm.getClient(), 3004434, "AbandonedCamp");
                break;
            case 2:
                cm.dispose();

                if (cm.getPlayer().getLevel() < 190) {
                    cm.sendOk("#fs11#" + colorPurple + "เลเวลไม่เพียงพอ");
                    return;
                }

                cm.openNpcCustom(cm.getClient(), 3004434, "HavenDailyQuest");
                break;
            case 3:
                cm.dispose();

                if (cm.getPlayer().getLevel() < 200) {
                    cm.sendOk("#fs11#" + colorPurple + "เลเวลไม่เพียงพอ");
                    return;
                }

                cm.openNpcCustom(cm.getClient(), 3004434, "ArcaneDailyQuest");
                break;
            case 4:
                cm.dispose();

                if (cm.getPlayer().getLevel() < 265) {
                    cm.sendOk("#fs11#" + colorPurple + "เลเวลไม่เพียงพอ");
                    return;
                }

                cm.openNpcCustom(cm.getClient(), 3004434, "AuthenticDailyQuest");
                break;
            case 5:
                cm.dispose();

                if (cm.getPlayer().getLevel() < 265) {
                    cm.sendOk("#fs11#" + colorPurple + "เลเวลไม่เพียงพอ");
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
