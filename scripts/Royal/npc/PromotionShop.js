var status = -1;

purple = "#fMap/MapHelper.img/weather/starPlanet/7#";
blue = "#fMap/MapHelper.img/weather/starPlanet/8#";
blueStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
yellowStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
whiteStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
brownStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
redStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
blackStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
purpleStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
star = "#fUI/FarmUI.img/objectStatus/star/whole#";
S = "#fUI/CashShop.img/CSEffect/today/0#"
color = "#fc0xFF6600CC#"
end = "#k"

var a = "";
var b = "";

var item = [
    [4033213, 1, 100000, "", ""], // Shining Jewel
    [5060048, 1, 300000, "", ""], // Red Apple
    [4036660, 1, 900000, "", ""], // Roulette Ticket
    //[5068306, 1, 1500000, "", ""], // Rainbow Berry
    [5062005, 1, 300000, "", ""], // Miracle Cube
    [5062503, 1, 300000, "", ""], // White Additional Cube
    [2631527, 10, 500000, "10 ชิ้น", ""], // Exp Node Gem
    [4009547, 500, 500000, "500 ชิ้น", ""], // Sol Erda Fragment
    [2046076, 1, 500000, "", ""], // One-Handed Weapon Att
    [2046077, 1, 500000, "", ""], // One-Handed Weapon Mag
    [2046150, 1, 500000, "", ""], // Two-Handed Weapon Att
    [2046340, 1, 500000, "", ""], // Accessory Att
    [2046341, 1, 500000, "", ""], // Accessory Mag
    [2048047, 1, 500000, "", ""], // Pet Equip Att
    [2048048, 1, 500000, "", ""], // Pet Equip Mag
    [2046251, 1, 500000, "", ""], // Shield Scroll
    [2630127, 1, 800000, "", ""], // Gen 1 Pet
    [2439942, 1, 800000, "", ""], // Gen 2 Pet
    [2439943, 1, 800000, "", ""], // Gen 3 Pet
    [2439944, 1, 800000, "", ""], // Gen 4 Pet
    [2439932, 1, 800000, "", ""], // Gen 5 Pet
    [2430218, 1, 1000000, "", ""], // Extreme Growth Potion
    [2538000, 1, 3000000, "", ""], // Additional Option Transfer
    [4034803, 1, 10000000, "", ""], // Name Change
    [2633590, 1, 500000, "", ""], // Max Level Arcane Symbol
    [2633591, 1, 1000000, "", ""]// Max Level Authentic Symbol

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
        var PromoPoint = comma(cm.getPlayer().getHPoint());

        chat = star + "#fs11# Promotion Point ปัจจุบันของคุณ #h0# : #r" + PromoPoint + "P#k\r\n";
        for (i = 0; i < item.length; i++) {
            price = item[i][2].toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
            chat += color + "#L" + i + "##i" + item[i][0] + "# #z" + item[i][0] + "# #r#e" + item[i][3] + "#l#n\r\n              #fc0xFF000000#ราคา : #e#r" + price + "P#b#n " + item[i][4] + "#k#n\r\n";
        }
        cm.sendSimple(chat);

    } else if (status == 1) {
        a = selection;

        if (selection == 1000) {
            cm.dispose();
        } else {
            var suk1 = Math.floor((cm.getPlayer().getHPoint() / item[selection][2]));
            stigmacoin = Math.min(suk1);
            cm.sendGetNumber("\r\n#fnArial#คุณต้องการซื้อ #i " + item[selection][0] + "##z" + item[selection][0] + " # จำนวน #fc0xFF7401DF#" + item[selection][1] + "#k ชิ้น จริงหรือไม่? \r\n#Cgray#(จำนวน #z" + item[selection][0] + "# ที่สามารถซื้อได้ : " + stigmacoin + " ชิ้น)", 1, 1, stigmacoin);
        }
    } else if (status == 2) {
        b = selection;
        cost = b;

        if (selection == 1000) {
            cm.dispose();
        }

        if (!cm.canHold(item[a][0], item[a][1] * cost)) {
            cm.sendOk("#fs11#พื้นที่ในกระเป๋าไม่เพียงพอ");
            cm.dispose();
            return;
        }

        if (cm.getPlayer().getHPoint() >= item[a][2] * cost) {
            cm.gainItem(item[a][0], item[a][1] * cost);
            cm.getPlayer().gainHPoint(-item[a][2] * cost);
            cm.sendOkS(color + "ขอบคุณครับ~" + star, 2);
            cm.dispose();
        } else {
            cm.sendOk("Promotion Point ไม่เพียงพอ");
            cm.dispose();
        }
    }
}

function comma(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}
