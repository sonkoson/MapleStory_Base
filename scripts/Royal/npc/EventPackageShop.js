purple = "#fMap/MapHelper.img/weather/starPlanet/7#";
blue = "#fMap/MapHelper.img/weather/starPlanet/8#";
starBlue = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
starYellow = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
starWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
starBrown = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
starRed = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
starBlack = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
starPurple = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
star = "#fUI/FarmUI.img/objectStatus/star/whole#"
S = "#fUI/CashShop.img/CSEffect/today/0#"
reward = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
obtain = "#fUI/UIWindow2.img/QuestIcon/4/0#"
color = "#fc0xFF6600CC#"
black = "#fc0xFF000000#"
pink = "#fc0xFFFF3366#"
lightPink = "#fc0xFFF781D8#"
enter = "\r\n"
enter2 = "\r\n\r\n"
enter = "\r\n";

importPackage(java.lang);
importPackage(Packages.server);

var a = "";
var b = "";

var status = -1;

var item = [
    ["#g[EVENT]#fc0xFF000000# Radiant Light Crystal 3k Package", 4031227, 3000, 50000, "#r[HOT]", "Sale Period : ~2025.07.13"],
    ["#g[EVENT]#fc0xFF000000# Neo Core 4k Package", 4310308, 4000, 50000, "#r[HOT]", "Sale Period : ~2025.07.13"],
    ["#g[EVENT]#fc0xFF000000# Memory Fragment 4k Package", 4033172, 4000, 50000, "#b[NEW]", "Sale Period : ~2025.07.13"],
    ["#g[EVENT]#fc0xFF000000# Anti-Magic Stone 7k Package", 4009005, 7000, 50000, "#b[NEW]", "Sale Period : ~2025.07.13"],
    ["#g[EVENT]#fc0xFF000000# Promotion Coin 8k Package", 4310266, 8000, 50000, "#b[NEW]", "Sale Period : ~2025.07.13"],
    ["#g[EVENT]#fc0xFF000000# Hunt Coin 15k Package", 4310237, 15000, 50000, "#b[NEW]", "Sale Period : ~2025.07.13"],
    //["#g[EVENT]#fc0xFF000000# Red Apple 120 Package", 5060048, 120, 100000, "#r[HOT]", "Sale Period : ~2025.06.08"],
    //["#g[EVENT]#fc0xFF000000# Wheel 40 Package", 4036660, 40, 100000, "#b[NEW]", "Sale Period : ~2025.06.08"],
];

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
    }
    if (status == 0) {
        var currentCash = comma(cm.getPlayer().getCashPoint());
        var currentGrade = cm.getPlayer().getHgrades();

        chat = star + "#fs11# Current #h0#'s Cash : #fc0xFFFF3366#" + currentCash + "C#k\r\n";
        chat += star + " Current #h0#'s Rank : #fc0xFFFF3366#" + currentGrade + "\r\n";
        chat += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        chat += color + "#e<Event Package>#k#n" + enter;
        for (i = 0; i < item.length; i++) {
            // Event package format is different below, be careful
            price = item[i][3].toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
            chat += black + "#L" + i + "##i" + item[i][1] + "# " + item[i][0] + " #r#e" + item[i][4] + "#l#n\r\n              #fc0xFF000000#Price : #e#r" + price + "C#b#n " + item[i][5] + "#k#n\r\n";
        }
        cm.sendSimple(chat);
    } else if (status == 1) {
        a = selection;

        if (selection == -1) {
            cm.sendOk("#fs11#No items available for purchase.");
            cm.dispose();
        } else {
            var suk1 = Math.floor((cm.getPlayer().getCashPoint() / item[selection][3]));
            stigmacoin = Math.min(suk1);
            // Event package format is different below, be careful
            cm.sendGetNumber("#fs11#" + black + "#i " + item[selection][1] + "# How many " + item[selection][0] + " would you like to buy? \r\n#Cgray#(Current purchasable " + item[selection][0] + " Quantity : " + stigmacoin + ")\r\n\r\n#r※ Purchase is immediate upon input [Cannot be undone]", 1, 1, 100);
        }
    } else if (status == 2) {
        b = selection;
        cost = b;

        if (selection <= 0) {
            cm.dispose();
            return;
        }

        if (selection > 100) {
            cm.sendOk("#fs11#You can only purchase up to 100 at a time.");
            cm.dispose();
            return;
        }

        if (cm.getPlayer().getCashPoint() >= item[a][3] * cost) {
            var totalCount = item[a][2] * cost; // Total quantity to give
            var maxStack = 32000; // Split limit (safely set to 32000)
            var givenCount = 0;

            while (totalCount > 0) {
                var give = Math.min(totalCount, maxStack);
                cm.gainItem(item[a][1], give);
                totalCount -= give;
                givenCount += give;
            }

            cm.getPlayer().gainCashPoint(-item[a][3] * cost);

            Packages.scripting.NPCConversationManager.writeLog(
                "TextLog/zenia/[MVP_CashShop].log",
                "\r\nAccount : " + cm.getClient().getAccountName() +
                " (" + cm.getClient().getAccID() + ")\r\nNickname : " + cm.getPlayer().getName() +
                "\r\nPurchased Item : " + cm.getItemName(item[a][1]) + " [" + item[a][1] + "] (" + cost + ")\r\n" +
                "Used Cash : " + -item[a][3] * cost + "\r\nHeld Cash : " + cm.getPlayer().getCashPoint() + "\r\n\r\n",
                true
            );

            cm.sendOkS("#fs11#" + color + "Purchase complete." + star, 2);
            cm.dispose();
        } else {
            cm.sendOk("#fs11#Insufficient Cash.");
            cm.dispose();
        }
    }
}

function comma(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}
