importPackage(Packages.scripting);

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
var PurpleColor = "#fc0xFF6600CC#"
var BlackColor = "#fc0xFF000000#"
var PinkColor = "#fc0xFFFF3366#"
var LightPinkColor = "#fc0xFFF781D8#"
var Enter = "\r\n"
var Enter2 = "\r\n\r\n"
enter = "\r\n";

var seld = -1;
var a = "";
var prevallstat = 0, prevatk = 0, nextallstat = 0, nextatk = 0;
var useCurrency = 0, currencyPrice = 0, useCurrencyName = "";
var logtype = 0, maxcount = 0;
var allstat = 2, atk = 2; // stats/att/magic att increase per time
var maxstat = 80000;

var currencyOptions = [
    { id: 4310308, price: 30, name: "Neo Core", maxCount: 1000, logType: 10 },
    { id: 4031227, price: 50, name: "Bright Light", maxCount: 600, logType: 11 },
    { id: 2437760, price: 300, name: "Arcane Symbol", maxCount: 100, logType: 12 }, // seld=3
    { id: 2633336, price: 300, name: "Authentic Symbol", maxCount: 100, logType: 13 }, // seld=4
    { id: 4009547, price: 1000, name: "Erda Fragment", maxCount: 30, logType: 14 }  // seld=5
];

function buildCurrencyMenu() {
    var txt = "";
    for (var i = 0; i < currencyOptions.length; i++) {
        var opt = currencyOptions[i];
        var idx = i + 1;
        txt += "#fc0xFF6542D7##L" + idx + "##i" + opt.id + "# #z" + opt.id + "##l #Cgray#(สูงสุดต่อครั้ง : " + opt.maxCount + " ครั้ง)#k\r\n";
    }
    return txt;
}

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode != 1) {
        cm.dispose();
        return;
    }
    status++;

    if (status == 0) {
        if (cm.getPlayer().getKeyValue("cashallstat") == null)
            cm.getPlayer().setKeyValue("cashallstat", "0");
        if (cm.getPlayer().getKeyValue("cashatk") == null)
            cm.getPlayer().setKeyValue("cashatk", "0");

        if (cm.getPlayer().getKeyValue("cashallstat") > maxstat - 2 ||
            cm.getPlayer().getKeyValue("cashatk") > maxstat - 2) {
            cm.getPlayer().setKeyValue("cashallstat", maxstat);
            cm.getPlayer().setKeyValue("cashatk", maxstat);
        }

        prevallstat = parseInt(cm.getPlayer().getKeyValue("cashallstat"));
        prevatk = parseInt(cm.getPlayer().getKeyValue("cashatk"));

        var txt = "#fs11#       #fUI/Basic.img/Zenia/SC/0#\r\n";
        txt += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――\r\n";
        txt += "#fc0xFF000000#      ต้องการใช้ไอเท็มใดเพื่ออัปเกรดตัวละคร?#l\r\n\r\n";
        txt += "#fc0xFF6542D7#";
        txt += buildCurrencyMenu();  // ← Automatically generated based on array
        cm.sendSimple(txt);

    } else if (status == 1) {
        seld = selection;
        var opt = currencyOptions[seld - 1];
        if (!opt) {
            cm.sendOk("#fs11##fc0xFF000000#ตัวเลือกไม่ถูกต้อง");
            cm.dispose();
            return;
        }
        txt += "#fs11##fc0xFF000000#       #b#i" + useCurrency + "##z" + useCurrency + "# " + currencyPrice + " ชิ้น#fc0xFF000000# สามารถอัปเกรดตัวละครได้#b \r\n#r                      (เพิ่ม All Stat 2 / Att & Matt 2 ต่อครั้ง)#k\r\n\r\n";
        txt += "#fs13#" + PurpleColor + "                         < สเตตัสที่ใช้อยู่ปัจจุบัน >\r\n";
        txt += "#fs11#" + BlackColor + "                                 All Stat #b+ " + prevallstat + "\r\n";
        txt += BlackColor + "                                 Att & Matt #b+ " + prevatk + "\r\n\r\n";
        txt += PinkColor + "                     ต้องการดำเนินการอัปเกรดตัวละครหรือไม่?";
        cm.sendSimple(txt);

    } else if (status == 2) {
        var possible = Math.floor(cm.itemQuantity(useCurrency) / currencyPrice);
        possible = Math.min(possible, maxcount);
        cm.askNumber(
            "#fs11##fc0xFF000000#ต้องการอัปเกรดตัวละครกี่ครั้ง? \r\n#Cgray#(จำนวนครั้งที่สามารถอัปเกรดได้ตอนนี้ : " + possible + " ครั้ง)",
            GameObjectType.User, 1, 1, possible, ScriptMessageFlag.NpcReplacedByUser
        );

    } else if (status == 3) {
        a = selection;
        nextallstat = prevallstat + allstat * a;
        nextatk = prevatk + atk * a;

        if (nextallstat > maxstat || nextatk > maxstat) {
            cm.sendOk("#fs11#ค่าการอัปเกรดตัวละครปัจจุบันไม่สามารถเกิน 80,000 ได้");
            cm.dispose();
            return;
        }

        var txt = "#fs11#       #fUI/Basic.img/Zenia/SC/0#\r\n";
        txt += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――\r\n";
        txt += "#fs13#" + PurpleColor + "                         < สเตตัสที่ใช้อยู่ปัจจุบัน >\r\n";
        txt += "#fs11#" + BlackColor + "                                 All Stat #b+ " + prevallstat + "\r\n";
        txt += BlackColor + "                                 Att & Matt #b+ " + prevatk + "\r\n\r\n";
        txt += "#fs13#" + PurpleColor + "                       < สเตตัสที่จะได้รับหลังอัปเกรด >\r\n";
        txt += "#fs11#" + BlackColor + "                                 All Stat #b+ " + nextallstat + "\r\n";
        txt += BlackColor + "                                 Att & Matt #b+ " + nextatk + "\r\n\r\n";
        txt += BlackColor + "#r                    จำนวน #i" + useCurrency + "##z" + useCurrency + "# ที่ต้องใช้ : " + (currencyPrice * a) + " ชิ้น\r\n\r\n";
        txt += BlackColor + " หากต้องการดำเนินการอัปเกรดจริงๆ กรุณาพิมพ์ #r'Y'" + BlackColor + " ด้านล่าง";
        cm.sendGetText(txt);

    } else if (status == 4) {
        if (cm.getText() != "Y" && cm.getText() != "y") {
            cm.sendOk("#fs11#หากไม่พิมพ์ Y ก็ช่วยอะไรไม่ได้นะ\r\n#bหากตกลง#k ให้พิมพ์ '#r#eY#k#n' อีกครั้ง");
            cm.dispose();
            return;
        }
        if (!cm.haveItem(useCurrency, currencyPrice * a)) {
            cm.sendOk("#fs11##fc0xFF000000#ต้องมี #b" + (currencyPrice * a) + " #z" + useCurrency + "##fc0xFF000000# เพื่อการอัปเกรด " + a + " ครั้ง");
            cm.dispose();
            return;
        }
        cm.dispose();
        try {
            cm.addCustomLog(logtype, "[Character Upgrade] | Total Stat : " + nextallstat + " | Total Att/Matt : " + nextatk + " |\r\nㄴ | Upgrade Count : " + a + " | Currency :  " + useCurrencyName + " " + (currencyPrice * a) + " EA |");
            cm.effectText("#fnArial##fs20#[Character Upgrade] ตัวละครได้รับการอัปเกรด < " + a + " > ครั้ง", 50, 1000, 6, 0, 330, -550);
            cm.getPlayer().setKeyValue("cashallstat", nextallstat);
            cm.getPlayer().setKeyValue("cashatk", nextatk);
            cm.getPlayer().setBonusCTSStat();
            cm.gainItem(useCurrency, -currencyPrice * a);
        } catch (err) {
            cm.addCustomLog(50, "[CashEn.js] Error : " + err);
        }
    }
}
