// Event NPC Castor
var starBo = "#fMap/MapHelper.img/weather/starPlanet/7#";
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
var rewardIcon = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
var obtainIcon = "#fUI/UIWindow2.img/QuestIcon/4/0#"
var color = "#fc0xFF6600CC#"
var black = "#fc0xFF000000#"
var pink = "#fc0xFFFF3366#"
var lightPink = "#fc0xFFF781D8#"
var skyBlue = "#fc0xFF58D4D3#"
var enter = "\r\n"
var enter2 = "\r\n\r\n"

var seld = -1;
var status = -1;

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

    // GM만 가능하게 임시로 막기
    /*
    if (cm.getPlayer().getGMLevel() < 5) {
    cm.dispose();
    cm.sendOkS("현재 이용하실수 없습니다", 0x24);
    }
    */
    if (status == 0) {
        var msg = "#fs11##fUI/Basic.img/Zenia/SC/1#\r\n";
        msg += "#Cgray#――――――――――――――――――――――――――――――――――――――――\r\n";

        msg += "#L110#" + starBlue + skyBlue + " [EVENT]#b [" + color + "ร้านค้าเหรียญกิจกรรมฉลองเปิดเซิร์ฟ#b]" + pink + " ไปทันที#l\r\n";
        msg += "#L10#" + starBlue + skyBlue + " [EVENT]#b [" + color + "เทศกาลล่าสัตว์ Advent World#b]" + pink + " ไปทันที#l\r\n";
        //msg += "#L100#" + starPurple + skyBlue + "[EVENT]#b [" + color + "Mini Game#b] " + black + "เล่นเกม#l\r\n";

        msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――";

        //msg += "#L16#" + starPurple + "#r[ตลอดเวลา]#b [" + color + "Advent Coupon#b] " + black + "กรอกโค้ดกิจกรรม\r\n";

        msg += "#L14#" + starPurple + "#r [EVENT]#b [" + color + "รางวัลล็อกอินรายวัน#b] " + black + "รับรางวัล#l\r\n";
        msg += "#L11#" + starPurple + "#r [EVENT]#b [" + color + "Union 8000#b] " + black + "รับรางวัล (" + checkunion() + ")#l\r\n";

        //msg += "#L12#" + starPurple + "#r[ตลอดเวลา]#b [" + color + "Watermelon Punch#b] " + black + "ทำ#l　";
        //msg += "#L13#" + starPurple + "#r[ตลอดเวลา]#b [" + color + "Roulette#b] " + black + "หมุนวงล้อ#l\r\n";

        //msg += "#L15#" + starPurple + "#r[ตลอดเวลา]#b [" + color + "Mailbox#b] " + black + "ตรวจสอบ#l\r\n";
        cm.sendSimple(msg);

    } else if (status == 1) {
        seld = sel;
        switch (seld) {
            /* 
        case 122:
              cm.dispose();
             cm.getClient().setKeyValue("PCount", "1");
                  cm.getClient().removeKeyValue("unionevent");
        cm.getPlayer().dropMessage(6, "초기화 했다~");
            return;
*/
            case 10:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 9000213, "강림사냥페스티벌");
                return;

            case 11:
                var v = cm.getPlayer().getOneInfoQuest(18771, "rank");
                if (v == null | v.isEmpty()) {
                    cm.sendOk("#fs11#คุณไม่มีข้อมูล Union ในขณะนี้!\r\n\r\nกรุณาสร้าง Union โดยใช้ระบบ Union ก่อน\r\n#b[ระบบคอนเทรนต์ - Union] #rOR #b[@Union] #kใช้คำสั่ง");
                    return;
                }

                if (cm.getClient().getKeyValue("unionevent") == null) {
                    if (cm.getInvSlots(1) < 5 || cm.getInvSlots(2) < 5 || cm.getInvSlots(3) < 5 || cm.getInvSlots(4) < 5 || cm.getInvSlots(5) < 5) {
                        cm.sendOkS("#fs11##fc0xFF6600CC#กรุณาเคลียร์ช่องเก็บของให้ว่างอย่างน้อย 5 ช่องในแต่ละแท็บ", 2);
                        cm.dispose();
                        return;
                    }
                    if (cm.getPlayer().getUnionLevel() >= 8000) {
                        var msg = "#fs11#";
                        msg += "ยินดีด้วยที่บรรลุ Union 8000" + enter;
                        msg += "เราได้มอบไอเทมเล็กๆ น้อยๆ เพื่อช่วยในการเติบโตของคุณแล้ว" + enter;
                        msg += "#i4310229##z4310229# 2000 ชิ้น" + enter;
                        msg += "#i4310266##z4310266# 1000 ชิ้น" + enter;
                        msg += "#i4310237##z4310237# 2000 ชิ้น" + enter;
                        msg += "#i4001715##z4001715# 200 ชิ้น" + enter;
                        msg += "#i5060048##z5060048# 5 ชิ้น" + enter;
                        msg += "#i2630009##z2630009# 3 ชิ้น" + enter;
                        cm.sendOk(msg);
                        cm.gainItem(4310229, 2000);
                        cm.gainItem(4310266, 1000);
                        cm.gainItem(4310237, 2000);
                        cm.gainItem(4001715, 100);
                        cm.gainItem(5060048, 5);
                        cm.gainItem(2630009, 1);
                        cm.getClient().setKeyValue("unionevent", "1");
                        cm.dispose();
                    } else {
                        cm.sendOk("#fs11#ดูเหมือนว่าเลเวล Union ของคุณยังไม่ถึงนะ?\r\nเลเวล Union ปัจจุบัน :#r" + cm.getPlayer().getUnionLevel());
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("#fs11#คุณได้เข้าร่วมกิจกรรม Union ไปแล้ว");
                    cm.dispose();
                }
                return;

            case 12:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 9000213, "수박");
                return;

            case 13:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 9000213, "뽑기");
                return;

            case 14:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 9000213, "핫타임일퀘");
                return;

            case 15:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 9000213, "우편함");
                return;

            case 16:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 9000213, "쿠폰");
                return;



            // Mini Game
            case 100:
                var msg = "#fs11#    #fUI/Basic.img/Zenia/SC/1#\r\n";
                msg += "#Cgray#――――――――――――――――――――――――――――――――――――――――\r\n";
                msg += "                  #L10#" + pink + blue + "[Erda Spectrum]" + color + " เล่น" + black + " เกม#l" + enter;
                msg += "                    #L11#" + pink + blue + "[Hungry Muto]" + color + " เล่น" + black + " เกม#l" + enter;
                msg += "                   #L12#" + pink + blue + "[Dream Breaker]" + color + " เล่น" + black + " เกม#l" + enter;
                //msg += "                   #L13#" + pink + blue + "[Spirit Savior]" + color + " เล่น" + black + " เกม#l" + enter;

                msg += enter;

                msg += "         #L1#" + color + blue + "#r[Solo]" + color + " เล่น" + black + " เกม#l";
                msg += "#L2#" + color + blue + "#r[Multi]" + color + " เล่น" + black + " เกม#l" + enter;

                msg += enter;

                msg += "                        #L3#" + color + blue + "#b[Classic]" + color + " เล่น" + black + " เกม#l";
                msg += "\r\n\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――";
                cm.sendSimple(msg);
                return;

            case 123:
                if (cm.haveItem(3010432, 1)) {
                    cm.sendOk("#fs11##dดูเหมือนว่าคุณจะมีอยู่แล้วนะ?");
                    cm.dispose();
                    return;
                }
                cm.gainItem(3010432, 1);
                cm.sendOk("#fs11##i3010432##b#z3010432# #d มอบไอเทมเรียบร้อยแล้ว");
                cm.dispose();
                return;

            case 110:
                cm.dispose();
                cm.openShop(3334);
                break;

        }
    } else if (status == 2) {
        switch (seld) {
            // Mini Game
            case 100:
                switch (sel) {
                    case 1:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 9000213, "minigamesolo");
                        return;
                    case 2:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 9000213, "minigamemulti");
                        return;
                    case 3:
                        cm.dispose();
                        cm.openShop(11111);
                        return;
                    case 10:
                        cm.dispose();
                        Packages.scripting.newscripting.ScriptManager.runScript(cm.getPlayer().getClient(), "arcane1MO_Enter", null);
                        return;
                    case 11:
                        cm.dispose();
                        cm.getPlayer().warp(450002023);
                        return;
                    case 12:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 9010100, "dreamBreaker_NPC");
                        return;
                    case 13:
                        cm.dispose();
                        Packages.scripting.newscripting.ScriptManager.runScript(cm.getPlayer().getClient(), "spiritSavior_NPC", null);
                        return;
                }
        }
    }
}

function checkunion() {
    if (cm.getClient().getKeyValue("unionevent") == null && cm.getPlayer().getUnionLevel() >= 8000) {
        return "#bรับได้";
    } else if (cm.getClient().getKeyValue("unionevent") == null && cm.getPlayer().getUnionLevel() < 8000) {
        return "#rเลเวลไม่พอ";
    } else if (cm.getClient().getKeyValue("unionevent") != null) {
        return "#rรับไปแล้ว";
    }
}