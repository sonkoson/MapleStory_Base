importPackage(java.lang);

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
    ["Event Daily Quest.01", "3k"],
    ["Event Daily Quest.02", "5k"],
    ["Event Daily Quest.03", "7k"],
    ["Event Daily Quest.04", "10k"],
];

var check = [
    [[8643003, 3000], [8643004, 3000]],
    [[8644008, 5000], [8644009, 5000]],
    [[8644405, 7000], [8644406, 7000]],
    [[8644508, 10000], [8644509, 10000]]
];

var reward = [
    [4310266, 500],
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
        var say = "  #fs14#   #i4001779##fc0xFFF781D8##e [Ganglim World] Hunting Festival! #i4001779#\r\n#fs11##Cblue#              สามารถทำได้วันละ 1 ครั้งเท่านั้น#k\r\n\r\n";
        for (var i = 0; i < quest.length; i++) {
            say += "#L" + i + "##e#b[" + quest[i][0] + "]#n#k\r\n#d";
            for (var a = 0; a < check[i].length; a++) {
                say += "   ㄴ#o" + check[i][a][0] + "# " + check[i][a][1] + " ตัว";
            }
            say += "\r\n\r\n";
        }
        cm.sendSimple(say);

    } else if (status == 1) {
        sel = selection;
        if (cm.getPlayer().getKeyValue("Quest_" + quest[sel][1]) == Today) {
            cm.sendOk("ภารกิจนี้เสร็จสมบูรณ์แล้ว");
            cm.dispose();
            return;
        } else if (cm.getPlayer().getKeyValue("Quest_" + quest[sel][1]) != 0) {
            cm.getPlayer().setKeyValue("Quest_" + quest[sel][1], 0);
            for (var a = 0; a < check[sel].length; a++) {
                cm.getPlayer().setKeyValue("Quest_" + check[sel][a][0], "0");
                cm.getPlayer().setKeyValue("QuestMax_" + check[sel][a][0], check[sel][a][1]);
            }
        }

        // If null, need to set again
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
            var say = "<รายการรางวัล>\r\n";
            for (var i = 0; i < reward.length; i++) {
                say += "#i" + reward[i][0] + "##z" + reward[i][0] + "# " + reward[i][1] + " ชิ้น\r\n";
            }
            cm.sendYesNo("เงื่อนไขการทำภารกิจนี้ครบถ้วนแล้ว ต้องการรับรางวัลหรือไม่?\r\n\r\n" + say);
        } else {
            var say = "#e#b[ภารกิจ " + quest[sel][1] + "]#n#k\r\n\r\n";
            for (var i = 0; i < check[sel].length; i++) {
                count = cm.getPlayer().getKeyValue("Quest_" + check[sel][i][0]);
                say += "#o" + check[sel][i][0] + "# (" + count + " / " + check[sel][i][1] + ") ตัว\r\n";
            }
            say += "\r\n#e#bเมื่อกำจัดมอนสเตอร์ครบตามจำนวนจึงจะรับรางวัลได้\r\n\r\n";

            say += "#fc0xFF6600CC#<รายการรางวัล>\r\n";
            for (var i = 0; i < reward.length; i++) {
                say += "#i" + reward[i][0] + "##z" + reward[i][0] + "# " + reward[i][1] + " ชิ้น\r\n";
            }
            cm.sendSimple(say);
        }

    } else if (status == 2) {
        cm.dispose();
        bClear = true;

        for (var a = 0; a < check[sel].length; a++) {
            if (Number(cm.getPlayer().getKeyValue("Quest_" + check[sel][a][0])) < check[sel][a][1]) {
                bClear = false;
                break;
            }
        }
        if (!bClear) {
            return;
        }
        for (var i = 0; i < reward.length; i++) {
            if (!cm.canHold(reward[i][0], reward[i][1])) {
                cm.sendOk("กรุณาตรวจสอบว่าช่องเก็บของเต็มหรือไม่เพียงพอ");
                return;
            }
        }

        cm.getPlayer().setKeyValue("Quest_" + quest[sel][1], Today);

        for (var i = 0; i < reward.length; i++) {
            cm.gainItem(reward[i][0], reward[i][1]);
        }

        cm.sendOk("ภารกิจเสร็จสมบูรณ์และได้รับรางวัลแล้ว กรุณาตรวจสอบช่องเก็บของ");
    }
}
