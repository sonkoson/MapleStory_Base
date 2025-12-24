importPackage(Packages.scripting);

Purple = "#fMap/MapHelper.img/weather/starPlanet/7#";
Blue = "#fMap/MapHelper.img/weather/starPlanet/8#";
StarBlue = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
StarYellow = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
StarWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
StarBrown = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
StarRed = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
StarBlack = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
StarPurple = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
Star = "#fUI/FarmUI.img/objectStatus/star/whole#"
S = "#fUI/CashShop.img/CSEffect/today/0#"
Reward = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
Obtain = "#fUI/UIWindow2.img/QuestIcon/4/0#"
Color = "#fc0xFF6600CC#"
PurpleColor = "#fc0xFF6600CC#"
Black = "#fc0xFF000000#"
Pink = "#fc0xFFFF3366#"
Pink = "#fc0xFFF781D8#"
Enter = "\r\n"
Enter2 = "\r\n\r\n"
enter = "\r\n";

var seld = -1;

var a = "";

var prevallstat = 0;
var prevatk = 0;
var nextallstat = 0;
var nextatk = 0;

var usedCurrency = 0;
var currencyPrice = 0;
var currencyName = "";
var maxcount = 0;
var price = 30;
var allstat = 2, atk = 2; // 1회당 올스텟, 공마 증가치

function start() {
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
        if (cm.getPlayer().getKeyValue("cashallstat") == null)
            cm.getPlayer().setKeyValue("cashallstat", "0")

        if (cm.getPlayer().getKeyValue("cashatk") == null)
            cm.getPlayer().setKeyValue("cashatk", "0")

        prevallstat = parseInt(cm.getPlayer().getKeyValue("cashallstat"));
        prevatk = parseInt(cm.getPlayer().getKeyValue("cashatk"));

        txt = "#fs11#       #fUI/Basic.img/Zenia/SC/0#\r\n";
        txt += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――\r\n";
        txt += "#fc0xFF000000#     ต้องการใช้ไอเท็มชิ้นไหนเพื่อเสริมแกร่งตัวละคร?#l\r\n\r\n";
        txt += "#fc0xFF6542D7##L1##i4310308# #z4310308##l";
        txt += "#fc0xFF6542D7##L2##i4031227# #z4031227##l";

        cm.sendSimple(txt);
    } else if (status == 1) {
        seld = selection;
        if (seld == 1) { // Neo Core
            usedCurrency = 4310308;
            currencyPrice = 30;
            currencyName = "Neo Core";
            maxcount = 1000;
        } else if (seld == 2) { // Red Bead
            usedCurrency = 4031227;
            currencyPrice = 100;
            currencyName = "Red Bead";
            maxcount = 300;
        } else {
            cm.sendOk("#fs11##fc0xFF000000#กรุณาเลือกไอเท็มที่จะใช้");
            cm.dispose();
            return;
        }

        txt = "#fs11#       #fUI/Basic.img/Zenia/SC/0#\r\n";
        txt += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――\r\n";
        txt += "#fs11##fc0xFF000000#       ใช้ #b#i" + usedCurrency + "##z" + usedCurrency + "# " + currencyPrice + " ชิ้น#fc0xFF000000# เพื่อเสริมแกร่งตัวละครได้#b \r\n#r                      (All Stat +2 / WA MA +2 ต่อครั้ง)#k" + enter + enter;
        txt += "#fs13#" + PurpleColor + "                         < สเตตัสปัจจุบัน >" + enter;
        txt += "#fs11#" + Black + "                                 All Stat #b+ " + prevallstat + enter;
        txt += Black + "                                 WA MA #b+ " + prevatk + enter + enter;
        txt += Pink + "                     ต้องการเสริมแกร่งตัวละครหรือไม่?";

        cm.sendSimple(txt);
    } else if (status == 2) {
        var suk1 = Math.floor((cm.itemQuantity(usedCurrency) / currencyPrice));
        stigmacoin = Math.min(suk1);
        stigmacoin = Math.min(1000, stigmacoin);

        if (stigmacoin > maxcount)
            //stigmacoin = maxcount

            cm.askNumber("#fs11##fc0xFF000000#ต้องการเสริมแกร่งกี่ครั้ง? \r\n#Cgray#(จำนวนที่เสริมแกร่งได้ : " + stigmacoin + " ครั้ง)", GameObjectType.User, 1, 1, stigmacoin, ScriptMessageFlag.NpcReplacedByUser);
    } else if (status == 3) {
        a = selection;
        nextallstat = prevallstat + (allstat * a);
        nextatk = prevatk + (atk * a);

        if (a < 0 | currencyPrice * a > 30000) {
            cm.dispose();
            return;
        }

        txt = "#fs11#       #fUI/Basic.img/Zenia/SC/0#\r\n";
        txt += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――\r\n";
        txt += "#fs13#" + PurpleColor + "                         < สเตตัสปัจจุบัน >" + enter;
        txt += "#fs11#" + Black + "                                 All Stat #b+ " + prevallstat + enter;
        txt += Black + "                                 WA MA #b+ " + prevatk + enter + enter;
        txt += "#fs13#" + PurpleColor + "                       < สเตตัสหลังเสริมแกร่ง >" + enter;
        txt += "#fs11#" + Black + "                                 All Stat #b+ " + nextallstat + enter;
        txt += Black + "                                 WA MA #b+ " + nextatk + enter + enter;
        txt += Black + "#r                    จำนวน #i" + usedCurrency + "##z" + usedCurrency + "# ที่จะใช้ : " + (currencyPrice * a) + " ชิ้น" + enter + enter;
        txt += Black + " ถ้าต้องการเสริมแกร่งจริงๆ กรุณาพิมพ์ #r'ยอมรับ'" + Black + " ด้านล่าง";


        cm.sendGetText(txt);
    } else if (status == 4) {
        var text = cm.getText();
        if (text != "ยอมรับ") {
            cm.dispose();
            cm.sendOk("#fs11#ถ้าไม่ยอมรับก็ช่วยไม่ได้...\r\nถ้า #bยอมรับ#k กรุณาพิมพ์ '#r#eยอมรับ#k#n' อีกครั้ง");
            return;
        }

        if (!cm.haveItem(usedCurrency, currencyPrice * a)) {
            cm.sendOk("#fs11##fc0xFF000000#เพื่อเสริมแกร่ง " + a + " ครั้ง จำเป็นต้องมี #b" + currencyPrice * a + " #z" + usedCurrency + "##fc0xFF000000#");
            cm.dispose();
            return;
        }

        cm.dispose();

        try {
            cm.addCustomLog(10, "[CharacterEnhancement] | CumulativeStat : " + nextallstat + " | CumulativeAtk : " + nextatk + " |\r\nL | EnhanceCount : " + a + " | CurrencyUsed :  " + currencyName + " " + (currencyPrice * a) + "pcs |");
            cm.effectText("#fnArial##fs20#[Character Enhancement] Character enhanced < " + a + " > times", 50, 1000, 6, 0, 330, -550);
            cm.getPlayer().setKeyValue("cashallstat", nextallstat);
            cm.getPlayer().setKeyValue("cashatk", nextatk);
            cm.getPlayer().setBonusCTSStat();
            cm.gainItem(usedCurrency, -currencyPrice * a);

            prevflag = cm.getPlayer().getSaveFlag();
            cm.getPlayer().setSaveFlag(4096); // KeyValue
            cm.getPlayer().saveToDB(false, false);
            cm.getPlayer().setSaveFlag(prevflag)
        } catch (err) {
            cm.addCustomLog(50, "[CashEn.js] 에러 발생 : " + err + "");
        }
    }
}