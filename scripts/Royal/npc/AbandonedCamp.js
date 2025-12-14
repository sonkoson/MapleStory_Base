/* Daily Quest
 Vanishing Journey     Joyous Erdas 100, Raging Erdas 100			Joyous-8641000 Raging-8641001				
 Chu Chu Island    Crilla 100, Patriarch 100			Crilla-8642012 Patriarch-8642014				
 Lachelein          Angry Masquerade Citizen 100, Insane Masquerade Citizen 100		Angry-8643008 Insane-8643009				
 Arcana        Spirit of Grief 200, Spirit of Despair 200			Grief-8644009 Despair-8644008				
 Morass          Blue Shadow 200, Red Shadow 200			Blue-8644405 Red-8644406				
 Esfera        Aranya 300, Aranea 300			Aranya-8644504 Aranea-8644505	*/

importPackage(java.lang);

var time = new Date();
var year = time.getFullYear() + "";
var month = time.getMonth() + 1 + "";
var date = time.getDate() + "";
if (month < 10) {
    month = "0" + month;
}
if (date < 10) {
    date = "0" + date;
}
var today = parseInt(year + month + date);

var quest = [
    ["Abandoned Camp", "trash"]
];

var check = [
    [[3503001, 200], [3503004, 200]]
];

var reward = [
    [2435719, 200],
];

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
        var talk = "นี่คือเควสรายวัน กรุณาเลือกเควสที่ต้องการทำ\r\n";
        //talk += "#L0##e#bLevel Range Monsters #r(-20~+10 Level) #b10000 Monsters#d (Unlimited)#l\r\n\r\n";
        for (var i = 0; i < quest.length; i++) {
            talk += "#L" + i + "##e#b[" + quest[i][0] + "]#n#k\r\n#e#d";
            for (var a = 0; a < check[i].length; a++) {
                talk += "   ㄴ#o" + check[i][a][0] + "# " + check[i][a][1] + " ตัว\r\n";
            }
            talk += "\r\n";
        }
        cm.sendSimple(talk);

    } else if (status == 1) {
        sel = selection;

        if (cm.getPlayer().getKeyValue("Quest_" + quest[sel][1]) == today) {
            cm.sendOk("เควสนี้เสร็จสิ้นแล้ว");
            cm.dispose();
            return;
        } else if (cm.getPlayer().getKeyValue("Quest_" + quest[sel][1]) != 0) {
            cm.getPlayer().setKeyValue("Quest_" + quest[sel][1], "0");
            for (var a = 0; a < check[sel].length; a++) {
                cm.getPlayer().setKeyValue("Quest_" + check[sel][a][0], "0");
                cm.getPlayer().setKeyValue("QuestMax_" + check[sel][a][0], check[sel][a][1]);
            }
        }

        // If null, reset.
        if (cm.getPlayer().getKeyValue("QuestMax_" + quest[sel][0][0]) == null) {
            for (var a = 0; a < check[sel].length; a++) {
                cm.getPlayer().setKeyValue("QuestMax_" + check[sel][a][0], check[sel][a][1]);
            }
        }

        bClear = true;

        for (var a = 0; a < check[sel].length; a++) {
            if (Number(cm.getPlayer().getKeyValue("Quest_" + check[sel][a][0])) < check[sel][a][1]) {
                bClear = false;
                break;
            }
        }

        if (bClear) {
            var talk = "<รายการของรางวัล>\r\n";
            for (var i = 0; i < reward.length; i++) {
                talk += "#i" + reward[i][0] + "##z" + reward[i][0] + "# " + reward[i][1] + " ชิ้น\r\n";
            }
            cm.sendYesNo("เงื่อนไขครบถ้วนแล้ว ต้องการจบเควสหรือไม่?\r\n\r\n" + talk);
        } else {
            var talk = "#e#b[" + quest[sel][0] + " Quest]#n#k\r\n";
            for (var i = 0; i < check[sel].length; i++) {
                count = cm.getPlayer().getKeyValue("Quest_" + check[sel][i][0]);
                talk += "#o" + check[sel][i][0] + "# (" + count + " / " + check[sel][i][1] + ") ตัว\r\n";
            }
            talk += "#e#bสามารถจบเควสได้หลังจากล่ามอนสเตอร์ครบแล้ว\r\n";

            talk += "#L0##e#rย้ายไปยังพื้นที่ล่า#l#k\r\n\r\n";
            talk += "[หากย้ายจากแผนที่แรกจะไปยังแผนที่ที่สอง]\r\n\r\n";
            talk += "#fc0xFF6600CC#<รายการของรางวัล>\r\n";
            for (var i = 0; i < reward.length; i++) {
                talk += "#i" + reward[i][0] + "##z" + reward[i][0] + "# " + reward[i][1] + " ชิ้น\r\n";
            }
            cm.sendSimple(talk);
        }

    } else if (status == 2) {
        cm.dispose();
        if (selection == 0) {
            if (cm.getPlayer().getMapId() != 105300103) {
                cm.warp(105300103, 0);
            } else {
                cm.warp(105300209, 0);
            }
        } else {

            for (var i = 0; i < reward.length; i++) {
                if (!cm.canHold(reward[i][0], reward[i][1])) {
                    cm.sendOk("กรุณาตรวจสอบว่าช่องเก็บของว่างหรือไม่");
                    return;
                }
            }

            cm.getPlayer().setKeyValue("Quest_" + quest[sel][1], today);

            for (var i = 0; i < reward.length; i++) {
                cm.gainItem(reward[i][0], reward[i][1]);
            }

            cm.sendOk("เควสเสร็จสิ้นและได้รับของรางวัลแล้ว กรุณาตรวจสอบช่องเก็บของ");
        }
    }
}
