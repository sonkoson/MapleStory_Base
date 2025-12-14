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
            cm.getPlayer().dropMessage(5, "ไม่สามารถใช้งานได้ในขณะต่อสู้กับบอส");
            cm.dispose();
            return;
        }

        var msg = "#fs11#       #fUI/Basic.img/Zenia/SC/0#\r\n";
        msg += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――\r\n";
        msg += "#L1##fUI/Basic.img/Zenia/SCBtn/600##l";
        msg += "#L2##fUI/Basic.img/Zenia/SCBtn/601##l";
        msg += "#L3##fUI/Basic.img/Zenia/SCBtn/602##l";

        cm.sendSimple(msg);

    } else if (status == 1) {
        switch (sel) {
            case 1:
                var msg = "#fs11#";
                msg += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――\r\n#fc0xFFFF3300#";
                msg += Color + "              #L1##fUI/UIWindow4.img/pointShop/100658/iconShop# อัพเกรดอุปกรณ์ทั่วไป#l#L2##fUI/UIWindow4.img/pointShop/100658/iconShop# อัพเกรดอุปกรณ์ระดับสูง#l\r\n\r\n";
                msg += Color + "                         #L3##fUI/UIWindow4.img/pointShop/501372/iconShop# #rรีเซ็ตการอัพเกรดด้วย Meso#l\r\n\r\n";
                msg += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――\r\n#fc0xFFFF3300#";
                cm.sendSimple(msg);
                break;
            case 2:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 3004434, "BallEn");
                break;
            case 3:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 3004434, "CashEn");
                break;
        }
    } else if (status == 2) {
        switch (sel) {
            case 1:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 3004434, "MesoEn");
                break;
            case 2:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 3004434, "MesoEnHell");
                break;
            case 3:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 3004434, "MesoEnR");
                break;
        }
    }
}