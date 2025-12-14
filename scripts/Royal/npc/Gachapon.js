importPackage(Packages.handling.world);
importPackage(Packages.tools.packet);

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
end = "#fc0xFF000000#"

var status = -1;
var enter = "\r\n";
var boxmsg = enter
var items0 = [
    [[2439988, 1], 0.1], // 0.1% Pitch Black Selection
    [[2439961, 1], 0.1], // 0.1% Arcane Selection
    [[2439960, 1], 0.1], // 0.1% Arcane Random
    [[2630782, 1], 4.6], // 4.8% Arcane Random

    [[0, 0], 0], // Enter

    [[2049380, 1], 0.2], // 0.2% Star Force 25 Star
    [[2430045, 1], 0.3], // 0.3% M-Upgrade +18
    [[2430044, 1], 1], // 1% M-Upgrade +17
    [[2430043, 1], 1], // 1% M-Upgrade +15
    [[2049377, 1], 1], // 1.0% Star Force 22 Star
    [[2049376, 1], 1.5], // 1.5% Star Force 20 Star
    [[2430042, 1], 7], // 7% M-Upgrade +13
    [[2430041, 1], 11], // 11% M-Upgrade +10

    [[0, 0], 0], // Enter

    [[2439944, 1], 2], // 2% Magnet Pet Gen 4
    [[2439943, 1], 2], // 2% Magnet Pet Gen 3
    [[2439942, 1], 3], // 3% Magnet Pet Gen 2
    [[2630127, 1], 4], // 3% Magnet Pet Gen 1

    [[0, 0], 0], // Enter

    [[4001715, 50], 11], // 11% 100 Million x50
    [[4031227, 50], 11], // 11% Cold Light x50
    [[4310308, 200], 11], // 11% Neo Core x200
    [[4310266, 200], 12], // 12% Promotion Coin x200
    [[4310237, 200], 16] // 16% Hunt Coin x200
]

var items = [
    //[[ItemCode, Count], Rate]
    [[2439958, 1], 1], //Destruction Selection
    [[2439988, 1], 1], //Pitch Black Selection
    [[2430047, 1], 1], //M-Upgrade +20
    [[2430046, 1], 1], //M-Upgrade +19
    [[5068306, 1], 1], //Rainbow Berry
    [[2439960, 1], 5], //Arcane Random
    [[2439961, 1], 5], // Arcane Selection
    [[2439962, 1], 5], // Arcane Weapon
    [[1113305, 1], 5], // Heart of Gllona
    [[1122439, 1], 5], // Legacy of Darkness
    [[1113070, 1], 5], // Scarlet Ring
    [[1012632, 1], 5], // Lucid's Earrings
    [[1022278, 1], 5], // Magic Eye Patch
    [[2430017, 20], 5], // Spon Point 200,000
    [[5062005, 20], 5], // Amazing Cube x20
    [[5062503, 20], 5], // White Additional Cube x20
    [[4031227, 1000], 5], // Cold Light x1000
    [[4310308, 2000], 5], // Neo Core x2000
    [[4310266, 2000], 5], // Promotion Coin x2000
    [[2049380, 1], 5], // Star Force 25 Star
    [[2049377, 1], 5], // Star Force 22 Star
    [[2430044, 1], 5], // M-Upgrade +17 
    [[4001715, 100], 10] // 10 Billion
]

var amount = 1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
        var chat = "#fs11#"
        chat += "สวัสดี #h0#? นี่คือวงล้อแห่ง #b#e[Ganglim World]#k#n!" + enter
        chat += "ลองเสี่ยงโชคด้วยตั๋ววงล้อหรือตั๋ววงล้อพิเศษดูสิ!" + enter + enter
        chat += "#L0#" + color + "[วงล้อทั่วไป]" + end + " สุ่ม#l　"
        chat += "#L1#" + color + "[วงล้อพิเศษ]" + end + " สุ่ม#l"
        chat += "\r\n"
        chat += "#L98#" + color + "[วงล้อทั่วไป]" + end + " รายการ#l"
        chat += "#L99#" + color + "[วงล้อพิเศษ]" + end + " รายการ#l"
        cm.sendSimple(chat);
    } else if (status == 1) {
        switch (selection) {
            case 0:
                if (!cm.haveItem(4036660, 1)) {
                    cm.sendOkS("#i4036660# #z4036660# ดูเหมือนคุณจะไม่มีไอเท็มนะ?..", 700);
                    cm.dispose();
                    return;
                }
                if (cm.getInvSlots(1) < 3 || cm.getInvSlots(2) < 3 || cm.getInvSlots(3) < 3 || cm.getInvSlots(4) < 3 || cm.getInvSlots(5) < 3) {
                    cm.sendOkS("#fs11##fc0xFF6600CC#กรุณาเว้นช่องว่างในช่องเก็บของอย่างน้อย 3 ช่องในแต่ละแท็บ", 2);
                    cm.dispose();
                    return;
                }

                cm.gainItem(4036660, -1);
                //NormalUnboxing();
                // Normal Draw
                for (var x = 0; x < amount; x++) {
                    var percentage = 0;
                    var chance = Math.random() * 100;
                    for (var i = 0; i < items0.length; i++) {
                        percentage += items0[i][1];
                        if (percentage > chance) {
                            boxmsg = "#fs11#" + color + "ยินดีด้วย คุณได้รับ #i" + items0[i][0][0] + "##z" + items0[i][0][0] + "# #r" + items0[i][0][1] + " ชิ้น#b เป็นรางวัล#k#n" + enter + enter;
                            cm.gainItem(items0[i][0][0], items0[i][0][1]);
                            break;
                        }
                    }
                }
                boxmsg += pink + star + "หวังว่าคุณจะชอบนะ ต้องการสุ่มอีกครั้งไหม?";
                retry = "NORMAL";
                cm.sendYesNo(boxmsg);

                break;
            case 1:
                if (!cm.haveItem(4036661, 1)) {
                    cm.sendOkS("#i4036661# #z4036661# ดูเหมือนคุณจะไม่มีไอเท็มนะ?..", 700);
                    cm.dispose();
                    return;
                }
                if (cm.getInvSlots(1) < 1 || cm.getInvSlots(2) < 1 || cm.getInvSlots(3) < 1 || cm.getInvSlots(4) < 1 || cm.getInvSlots(5) < 1) {
                    cm.sendOkS("#fs11##fc0xFF6600CC#กรุณาเว้นช่องว่างในช่องเก็บของอย่างน้อย 1 ช่องในแต่ละแท็บ", 2);
                    cm.dispose();
                    return;
                }

                cm.gainItem(4036661, -1);
                //AdvancedUnboxing();
                // M.V.P Draw
                for (var x = 0; x < amount; x++) {
                    var percentage = 0;
                    var chance = Math.random() * 100;
                    for (var i = 0; i < items.length; i++) {
                        percentage += items[i][1];
                        if (percentage > chance) {
                            boxmsg = color + "#fs11#" + color + "ยินดีด้วย คุณได้รับ #i" + items[i][0][0] + "##z" + items[i][0][0] + "# #r" + items[i][0][1] + " ชิ้น#b เป็นรางวัล#k#n" + enter + enter;
                            cm.gainItem(items[i][0][0], items[i][0][1]);
                            break;
                        }
                    }
                }
                boxmsg += pink + star + "หวังว่าคุณจะชอบนะ ต้องการสุ่มอีกครั้งไหม?";
                retry = "MVP";
                cm.sendYesNo(boxmsg);

                break;
            case 98:
                var msg = starBlue + "#fs11##fc0xFF000000# นี่คือรายการไอเท็มที่คุณสามารถสุ่มได้! #fs11#" + starBlue + enter + enter;
                for (i = 0; i < items0.length; i++) {
                    if (items0[i][0][0] == 0) {
                        msg += enter + enter;
                    } else {
                        msg += color + "#i" + items0[i][0][0] + "##z" + items0[i][0][0] + "##r " + items0[i][0][1] + " ชิ้น#k" + enter;
                    }
                }
                cm.sendOk(msg);
                cm.dispose();

            case 99:
                var msg = starBlue + "#fs11##fc0xFF000000# นี่คือรายการไอเท็มที่คุณสามารถสุ่มได้! #fs11#" + starBlue + enter + enter;
                for (i = 0; i < items.length; i++) {
                    msg += color + "#i" + items[i][0][0] + "##z" + items[i][0][0] + "##r " + items[i][0][1] + " ชิ้น #b" + items[i][1] + "%#k" + enter;
                }
                cm.sendOk(msg);
                cm.dispose();
        }
    } else if (status == 2) {
        if (retry == "NORMAL") {
            status = 0;
            action(1, 0, 0);
        }
        if (retry == "MVP") {
            status = 0;
            action(1, 0, 1);
        }
    }
}
