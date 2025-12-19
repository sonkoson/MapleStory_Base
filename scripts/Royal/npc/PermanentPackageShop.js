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
enter = "\r\n"
enter2 = "\r\n\r\n"

var a = "";
var b = "";

var status = -1;

var item = [
    [2430017, 1, 5000, "", "10K Points"],
    [2430017, 3, 13500, "", "30K Points"],
    [2430017, 6, 27000, "", "60K Points"],
    [2430017, 10, 45000, "", "100K Points"],
    [2430017, 20, 90000, "", "200K Points"],
    [2430017, 40, 180000, "", "400K Points"],
    [2430017, 60, 260000, "", "600K Points"],
    [2430017, 100, 430000, "", "1M Points"],
    //[2430017, 200, 850000, "#r[HOT]", "2M Points"]
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

        chat = star + "#fs11# Cash ปัจจุบันของ #h0#: #r" + currentCash + "C#k\r\n";
        chat += star + " Grade ปัจจุบันของ #h0#: #fc0xFFFF3366#" + currentGrade + "\r\n";
        chat += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        chat += color + "#e<แพ็กเกจ>#k#n" + enter;
        for (i = 0; i < item.length; i++) {
            price = item[i][2].toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
            chat += black + "#L" + i + "##i" + item[i][0] + "# #z" + item[i][0] + "# #r#e" + item[i][3] + "#l#n\r\n              #fc0xFF000000#ราคา: #e#r" + price + "C#b#n " + item[i][4] + "#k#n\r\n";
        }
        cm.sendSimple(chat);

    } else if (status == 1) {
        a = selection;

        if (selection == -1) {
            cm.sendOk("การซื้อผิดปกติ");
            cm.dispose();
        } else {
            var suk1 = Math.floor((cm.getPlayer().getCashPoint() / item[selection][2]));
            stigmacoin = Math.min(suk1);
            cm.sendGetNumber("#fs11#" + black + "คุณต้องการซื้อ #i " + item[selection][0] + "# #z" + item[selection][0] + "# กี่ชิ้น? \r\n#Cgray#(จำนวน #z" + item[selection][0] + "# ที่สามารถซื้อได้: " + stigmacoin + " ชิ้น)\r\n\r\n#r※ การซื้อจะเกิดขึ้นทันทีเมื่อกรอกจำนวน [ไม่สามารถยกเลิกได้]", 1, 1, stigmacoin);
        }
    } else if (status == 2) {
        b = selection;
        cost = b;

        if (selection > 100) {
            cm.sendOk("สามารถซื้อได้สูงสุด 100 ชิ้นต่อครั้ง");
            cm.dispose();
            return;
        }

        if (!cm.canHold(item[a][0], item[a][1] * cost)) {
            cm.sendOk("#fs11#พื้นที่ในกระเป๋าไม่เพียงพอ");
            cm.dispose();
            return;
        }

        if (cm.getPlayer().getCashPoint() >= item[a][2] * cost) {
            cm.gainItem(item[a][0], item[a][1] * cost);
            cm.getPlayer().gainCashPoint(-item[a][2] * cost);
            Packages.scripting.NPCConversationManager.writeLog("TextLog/zenia/[MVPCashShop].log", "\r\nAccount: " + cm.getClient().getAccountName() + " (" + cm.getClient().getAccID() + ")\r\nCharacter: " + cm.getPlayer().getName() + "\r\nPurchased Item: " + cm.getItemName(item[a][0]) + "[" + item[a][0] + "] (" + cost + " pcs)\r\nCash Used: " + -item[a][2] * cost + "\r\nRemaining Cash: " + cm.getPlayer().getCashPoint() + "\r\n\r\n", true);
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
