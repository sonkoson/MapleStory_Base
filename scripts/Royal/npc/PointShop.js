purple = "#fMap/MapHelper.img/weather/starPlanet/7#";
blue = "#fMap/MapHelper.img/weather/starPlanet/8#";
blueStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
yellowStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
whiteStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
brownStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
redStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
blackStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
purpleStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
star = "#fUI/FarmUI.img/objectStatus/star/whole#"
S = "#fUI/CashShop.img/CSEffect/today/0#"
reward = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
gain = "#fUI/UIWindow2.img/QuestIcon/4/0#"
color = "#fc0xFF6600CC#"
black = "#fc0xFF000000#"
enter = "\r\n"
enter2 = "\r\n\r\n"
end = "#k"

var a = "";
var b = "";

var status = -1;

var item = [
    [4033213, 1, 1000, "", ""], // Shining Jewel
    [5060048, 1, 3000, "", ""], // Red Apple
    [4036660, 1, 9000, "", ""], // Roulette Ticket
    [5062005, 1, 3000, "", ""], // Miracle Cube
    [5062503, 1, 3000, "", ""], // White Additional Cube
    [2631527, 10, 5000, "10 ชิ้น", ""], // Exp Node Gem
    [4009547, 500, 5000, "500 ชิ้น", ""], // Sol Erda Fragment
    [2046076, 1, 5000, "", ""], // One-Handed Weapon Att
    [2046077, 1, 5000, "", ""], // One-Handed Weapon Mag
    [2046150, 1, 5000, "", ""], // Two-Handed Weapon Att
    [2046340, 1, 5000, "", ""], // Accessory Att
    [2046341, 1, 5000, "", ""], // Accessory Mag
    [2048047, 1, 5000, "", ""], // Pet Equip Att
    [2048048, 1, 5000, "", ""], // Pet Equip Mag
    [2046251, 1, 5000, "", ""], // Shield Scroll
    [2630127, 1, 8000, "", ""], // Gen 1 Pet
    [2439942, 1, 8000, "", ""], // Gen 2 Pet
    [2439943, 1, 8000, "", ""], // Gen 3 Pet
    [2439944, 1, 8000, "", ""], // Gen 4 Pet
    [2439932, 1, 8000, "", ""], // Gen 5 Pet
    [2430218, 1, 10000, "", ""], // Extreme Growth Potion
    [2538000, 1, 30000, "", ""], // Additional Option Transfer
    //[2430007, 1, 25000, "", ""], // Scotty
    //[2437781, 1, 100000, "", ""], // Transparent Set
    //[2431394, 1, 150000, "", ""], // Ring Box
    [4034803, 1, 100000, "", ""], // Name Change
    //[2350002, 1, 1000000, "", ""], // 1 Character Name
    [2633590, 1, 20000, "", ""], // Max Level Arcane Symbol
    [2633591, 1, 40000, "", ""]// Max Level Authentic Symbol
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
        var currentPoints = comma(cm.getPlayer().getDonationPoint());
        var currentGrade = cm.getPlayer().getHgrades();

        chat = star + "#fs11# แต้มปัจจุบันของคุณ #h0# : #r" + currentPoints + "P#k\r\n";
        chat += star + " ระดับปัจจุบันของคุณ #h0# : #fc0xFFFF3366#" + currentGrade + "\r\n";
        chat += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        for (i = 0; i < item.length; i++) {
            price = item[i][2].toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
            chat += color + "#L" + i + "##i" + item[i][0] + "# #z" + item[i][0] + "# #r#e" + item[i][3] + "#l#n\r\n              #fc0xFF000000#ราคา : #e#r" + price + "P#b#n " + item[i][4] + "#k#n\r\n";
        }
        cm.sendSimple(chat);


    } else if (status == 1) {
        a = selection;

        if (selection == -1) {
            cm.sendOk("การพยายามซื้อที่ผิดปกติ");
            cm.dispose();
        } else {
            var suk1 = Math.floor((cm.getPlayer().getDonationPoint() / item[selection][2]));
            stigmacoin = Math.min(suk1);
            cm.sendGetNumber("#fs11#" + black + "คุณต้องการซื้อ #i " + item[selection][0] + "# #z" + item[selection][0] + "# จำนวนเท่าไหร่? \r\n#Cgray#(จำนวน #z" + item[selection][0] + "# ที่สามารถซื้อได้ : " + stigmacoin + " ชิ้น)\r\n\r\n#r※ การซื้อจะเสร็จสมบูรณ์ทันทีเมื่อใส่จำนวน [ไม่สามารถยกเลิกได้]", 1, 1, stigmacoin);
        }
    } else if (status == 2) {
        b = selection;
        cost = b;

        if (selection > 1000) {
            cm.sendOk("สามารถซื้อได้สูงสุด 1,000 ชิ้นเท่านั้น");
            cm.dispose();
            return;
        }

        if (!cm.canHold(item[a][0], item[a][1] * cost)) {
            cm.sendOk("#fs11#พื้นที่ในกระเป๋าไม่เพียงพอ");
            cm.dispose();
            return;
        }

        if (cm.getPlayer().getDonationPoint() >= item[a][2] * cost) {
            cm.gainItem(item[a][0], item[a][1] * cost);
            cm.getPlayer().gainDonationPoint(-item[a][2] * cost);
            Packages.scripting.NPCConversationManager.writeLog("TextLog/zenia/[MVPPointShop].log", "\r\nAccount : " + cm.getClient().getAccountName() + " (" + cm.getClient().getAccID() + ")\r\nNickname : " + cm.getPlayer().getName() + "\r\nPurchased Item : " + cm.getItemName(item[a][0]) + "[" + item[a][0] + "] (" + cost + "qty)\r\nPoints Used : " + -item[a][2] * cost + "\r\nPoints Held : " + cm.getPlayer().getDonationPoint() + "\r\n\r\n", true);
            cm.sendOkS(color + "การซื้อเสร็จสมบูรณ์" + star, 2);
            cm.dispose();
        } else {
            cm.sendOk("#fs11#แต้มไม่เพียงพอ");
            cm.dispose();
        }
    }
}

function comma(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}
