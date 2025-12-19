/*Daily Quest
 Path of Vanishing     Joyful Erdas 100, Angry Erdas 100    Joyful-8641000 Angry-8641001				
 Chu Chu Island        Krilla 100, Bashark 100			    Krilla-8642012 Bashark-8642014				
 Lachelein             Angry Ball Guest 100, Mad Ball Guest 100		Angry-8643008 Mad-8643009				
 Arcana                Sorrowful Spirit 200, Despairing Spirit 200		Sorrow-8644009 Despair-8644008				
 Morass                Blue Shadow 200, Red Shadow 200		        Blue-8644405 Red-8644406				
 Esfera                Aranya 300, Aranea 300			        Aranya-8644504 Aranea-8644505	*/

importPackage(java.lang);

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
rewardIcon = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
obtainIcon = "#fUI/UIWindow2.img/QuestIcon/4/0#"
color = "#fc0xFF6600CC#"
enter = "\r\n"
enter2 = "\r\n\r\n"

var Time = new Date();
var Year = Time.getFullYear() + "";
var Month = Time.getMonth() + 1 + "";
var Date = Time.getDate() + "";
if (Month < 10) {
    Month = "0" + Month;
}
if (Date < 10) {
    Date = "0" + Date;
}
var Today = parseInt(Year + Month + Date);

var quest = [
    ["", [0, 0], [0, 0], [0, 0], [0, 0]],
    [["Login Reward", "HotTime"], [0, 0], [0, 0]],
];

var reward = [[2630442, 1]];
var reward2 = [[2633336, 1]];
var choice = 0;

var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode != 1) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
        var say = "#fs11#" + star + color + " กรุณาเลือกรางวัลที่ต้องการรับ " + star + enter;
        // for (var i = 1; i < quest.length; i++) {
        say += "#L0##e#b[รางวัลเข้าเกม]#n#k";
        // }
        cm.sendSimple(say);

    } else if (status == 1) {
        if (cm.getClient().getKeyValue("hotTimeComplete") != Today) {
            var say = "#fs11#<รายการรางวัล>\r\n";
            for (var i = 0; i < reward.length; i++) {
                say += "#i" + reward[i][0] + "##z" + reward[i][0] + "# " + reward[i][1] + " ชิ้น\r\n";
            }
            cm.sendYesNo("#fs11#" + star + color + " คุณต้องการรับรางวัลหรือไม่? " + star + enter + enter + say);
        } else {
            cm.sendOkS("#fs11#" + color + "#eเดี๋ยวนะ เมื่อกี้เหมือนรับไปแล้ว..?", 2);
            cm.dispose();
            return;
        }
    } else if (status == 2) {
        cm.dispose();
        for (var i = 0; i < reward.length; i++) {
            if (!cm.canHold(reward[i][0], reward[i][1])) {
                cm.sendOk("#fs11#" + color + "กรุณาตรวจสอบว่าช่องเก็บของเต็มหรือไม่เพียงพอ");
                cm.dispose();
                return;
            }
        }

        for (var i = 0; i < reward.length; i++) {
            cm.gainItem(reward[i][0], reward[i][1]);
        }

        cm.getClient().setKeyValue("hotTimeComplete", Today);
        cm.sendOk("#fs11#" + color + "ได้รับรางวัลเข้าเกมรายวันแล้ว กรุณาตรวจสอบช่องเก็บของ");
    }
}
