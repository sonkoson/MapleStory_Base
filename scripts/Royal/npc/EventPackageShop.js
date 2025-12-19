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

importPackage(java.lang);
importPackage(Packages.server);

var a = "";
var b = "";

var status = -1;

var item = [
    ["#g[EVENT]#fc0xFF000000# Glowing Crystal 3k Package", 4031227, 3000, 50000, "#r[HOT]", "Sale Period: ~2025.07.13"],
    ["#g[EVENT]#fc0xFF000000# Neo Core 4k Package", 4310308, 4000, 50000, "#r[HOT]", "Sale Period: ~2025.07.13"],
    ["#g[EVENT]#fc0xFF000000# Memory Fragment 4k Package", 4033172, 4000, 50000, "#b[NEW]", "Sale Period: ~2025.07.13"],
    ["#g[EVENT]#fc0xFF000000# Half Magic Stone 7k Package", 4009005, 7000, 50000, "#b[NEW]", "Sale Period: ~2025.07.13"],
    ["#g[EVENT]#fc0xFF000000# Upgrade Coin 8k Package", 4310266, 8000, 50000, "#b[NEW]", "Sale Period: ~2025.07.13"],
    ["#g[EVENT]#fc0xFF000000# Hunt Coin 15k Package", 4310237, 15000, 50000, "#b[NEW]", "Sale Period: ~2025.07.13"],
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

        chat = star + "#fs11# Cash ปัจจุบันของ #h0#: #fc0xFFFF3366#" + currentCash + "C#k\r\n";
        chat += star + " Grade ปัจจุบันของ #h0#: #fc0xFFFF3366#" + currentGrade + "\r\n";
        chat += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        chat += color + "#e<Event Package>#k#n" + enter;
        for (i = 0; i < item.length; i++) {
            price = item[i][3].toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
            chat += black + "#L" + i + "##i" + item[i][1] + "# " + item[i][0] + " #r#e" + item[i][4] + "#l#n\r\n              #fc0xFF000000#ราคา: #e#r" + price + "C#b#n " + item[i][5] + "#k#n\r\n";
        }
        cm.sendSimple(chat);
    } else if (status == 1) {
        a = selection;

        if (selection == -1) {
            cm.sendOk("#fs11#ไม่มีไอเท็มที่สามารถซื้อได้");
            cm.dispose();
        } else {
            var suk1 = Math.floor((cm.getPlayer().getCashPoint() / item[selection][3]));
            stigmacoin = Math.min(suk1);
            cm.sendGetNumber("#fs11#" + black + "คุณต้องการซื้อ #i " + item[selection][1] + "# " + item[selection][0] + " กี่ชิ้น? \r\n#Cgray#(จำนวน " + item[selection][0] + " ที่สามารถซื้อได้: " + stigmacoin + " ชิ้น)\r\n\r\n#r※ การซื้อจะเกิดขึ้นทันทีเมื่อกรอกจำนวน [ไม่สามารถยกเลิกได้]", 1, 1, 100);
        }
    } else if (status == 2) {
        b = selection;
        cost = b;

        if (selection <= 0) {
            cm.dispose();
            return;
        }

        if (selection > 100) {
            cm.sendOk("#fs11#สามารถซื้อได้สูงสุด 100 ชิ้นต่อครั้ง");
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
                "TextLog/zenia/[MVPCashShop].log",
                "\r\nAccount: " + cm.getClient().getAccountName() +
                " (" + cm.getClient().getAccID() + ")\r\nCharacter: " + cm.getPlayer().getName() +
                "\r\nPurchased Item: " + cm.getItemName(item[a][1]) + " [" + item[a][1] + "] (" + cost + " pcs)\r\n" +
                "Cash Used: " + -item[a][3] * cost + "\r\nRemaining Cash: " + cm.getPlayer().getCashPoint() + "\r\n\r\n",
                true
            );

            cm.sendOkS("#fs11#" + color + "ซื้อสำเร็จแล้ว" + star, 2);
            cm.dispose();
        } else {
            cm.sendOk("#fs11#Cash ไม่เพียงพอ");
            cm.dispose();
        }
    }
}

function comma(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}
