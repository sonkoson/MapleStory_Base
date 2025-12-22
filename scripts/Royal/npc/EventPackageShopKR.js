
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
var enter = "\r\n"
var enter2 = "\r\n\r\n"

importPackage(java.lang);
importPackage(Packages.server);

var a = "";
var b = "";

var status = -1;

var item = [
    ["#g[EVENT]#fc0xFF000000# Brilliant Spirit 3k Package", 4031227, 3000, 50000, "#r[HOT]", "Sale Period : ~2025.07.13"],
    ["#g[EVENT]#fc0xFF000000# Neo Core 4k Package", 4310308, 4000, 50000, "#r[HOT]", "Sale Period : ~2025.07.13"],
    ["#g[EVENT]#fc0xFF000000# Memory Fragment 4k Package", 4033172, 4000, 50000, "#b[NEW]", "Sale Period : ~2025.07.13"],
    ["#g[EVENT]#fc0xFF000000# Anti-Magic Stone 7k Package", 4009005, 7000, 50000, "#b[NEW]", "Sale Period : ~2025.07.13"],
    ["#g[EVENT]#fc0xFF000000# Upgrade Coin 8k Package", 4310266, 8000, 50000, "#b[NEW]", "Sale Period : ~2025.07.13"],
    ["#g[EVENT]#fc0xFF000000# Hunt Coin 15k Package", 4310237, 15000, 50000, "#b[NEW]", "Sale Period : ~2025.07.13"],
    //["#g[EVENT]#fc0xFF000000# Red Apple 120 Package", 5060048, 120, 100000, "#r[HOT]", "Sale Period : ~2025.06.08"],
    //["#g[EVENT]#fc0xFF000000# Roulette 40 Package", 4036660, 40, 100000, "#b[NEW]", "Sale Period : ~2025.06.08"],
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
        var cashPoint = comma(cm.getPlayer().getCashPoint());
        var currentGrade = cm.getPlayer().getHgrades();

        chat = star + "#fs11# Cash ปัจจุบันของ #h0# : #fc0xFFFF3366#" + cashPoint + "C#k\r\n";
        chat += star + " ระดับปัจจุบันของ #h0# : #fc0xFFFF3366#" + currentGrade + "\r\n";
        chat += "#Cgray##fs11#????????????????????????????????????????#fc0xFF000000#";
        chat += color + "#e<Event Package>#k#n" + enter;
        for (i = 0; i < item.length; i++) {
            price = item[i][3].toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
            chat += black + "#L" + i + "##i" + item[i][1] + "# " + item[i][0] + " #r#e" + item[i][4] + "#l#n\r\n              #fc0xFF000000#ราคา : #e#r" + price + "C#b#n " + item[i][5] + "#k#n\r\n";
        }
        cm.sendSimple(chat);
    } else if (status == 1) {
        a = selection;

        if (selection == -1) {
            cm.sendOk("#fs11#ไม่มีไอเทมที่สามารถซื้อได้จ้ะ");
            cm.dispose();
        } else {
            var suk1 = Math.floor((cm.getPlayer().getCashPoint() / item[selection][3]));
            stigmacoin = Math.min(suk1);
            cm.sendGetNumber("#fs11#" + black + "#i " + item[selection][1] + "# ต้องการซื้อ " + item[selection][0] + " จำนวนกี่ชิ้นจ๊ะ? \r\n#Cgray#(จำนวนที่ซื้อได้: " + stigmacoin + " ชิ้น)\r\n\r\n#r? เมื่อใส่จำนวนจะทำการซื้อทันที [ไม่สามารถยกเลิกได้]", 1, 1, 100);
        }
    } else if (status == 2) {
        b = selection;
        cost = b;

        if (selection <= 0) {
            cm.dispose();
            return;
        }

        if (selection > 100) {
            cm.sendOk("#fs11#ซื้อได้สูงสุดครั้งละ 100 ชิ้นจ้ะ");
            cm.dispose();
            return;
        }

        if (cm.getPlayer().getCashPoint() >= item[a][3] * cost) {
            var totalCount = item[a][2] * cost;
            var maxStack = 32000;
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
                " (" + cm.getClient().getAccID() + ")\r\nCharacter : " + cm.getPlayer().getName() +
                "\r\nItem Purchased : " + cm.getItemName(item[a][1]) + " [" + item[a][1] + "] (" + cost + " pcs)\r\n" +
                "Cash Spent : " + -item[a][3] * cost + "\r\nCash Remaining : " + cm.getPlayer().getCashPoint() + "\r\n\r\n",
                true
            );

            cm.sendOkS("#fs11#" + color + "ซื้อเรียบร้อยแล้วจ้ะ" + star, 2);
            cm.dispose();
        } else {
            cm.sendOk("#fs11#Cash ไม่เพียงพอจ้ะ");
            cm.dispose();
        }
    }
}

function comma(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}



