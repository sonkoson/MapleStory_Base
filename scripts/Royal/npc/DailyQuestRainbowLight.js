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
    ["Ganglim World Exploration-1", "eq1"],
    ["Ganglim World Exploration-2", "eq2"],
];

var check = [
    [[8645009, 1], [8645129, 300]],
    [[8644655, 1], [8645130, 300]]
];

var reward = [
    [4310266, 100]
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
        var say = "#fs11 \r\n   ต้องการสำรวจ Ganglim World หรือไม่?\r\n";
        for (var i = 0; i < quest.length; i++) {
            say += "#L" + i + "##e#b[" + quest[i][0] + "]#n#k\r\n#e#d";
            for (var a = 0; a < check[i].length; a++) {
                say += "   ㄴ#o" + check[i][a][0] + "# " + check[i][a][1] + " ตัว\r\n";
            }
            say += "\r\n";
        }
        cm.sendSimple(say);

    } else if (status == 1) {
        sel = selection;
        if (cm.getPlayer().getKeyValue("Quest_" + quest[sel][1]) == Today) {
            cm.sendOk("#fs11#ภารกิจนี้เสร็จสมบูรณ์แล้ว");
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
            var say = "#fs11#<รายการรางวัล>\r\n";
            for (var i = 0; i < reward.length; i++) {
                say += "#i" + reward[i][0] + "##z" + reward[i][0] + "# " + reward[i][1] + " ชิ้น\r\n";
            }
            cm.sendYesNo("#fs11#เงื่อนไขการทำภารกิจนี้ครบถ้วนแล้ว ต้องการรับรางวัลหรือไม่?\r\n\r\n" + say);
        } else {
            var say = "#e#b[ภารกิจ " + quest[sel][1] + "]#n#k\r\n";
            for (var i = 0; i < check[sel].length; i++) {
                count = cm.getPlayer().getKeyValue("Quest_" + check[sel][i][0]);
                say += "#o" + check[sel][i][0] + "# (" + count + " / " + check[sel][i][1] + ") ตัว\r\n";
            }
            say += "#e#bกรุณากำจัดมอนสเตอร์ให้ครบก่อนจึงจะรับรางวัลได้\r\n";

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
                cm.sendOk("#fs11#กรุณาตรวจสอบว่าช่องเก็บของเต็มหรือไม่เพียงพอ");
                return;
            }
        }

        cm.getPlayer().setKeyValue("Quest_" + quest[sel][1], Today);

        for (var i = 0; i < reward.length; i++) {
            cm.gainItem(reward[i][0], reward[i][1]);
        }

        cm.sendOk("#fs11#ภารกิจเสร็จสมบูรณ์และได้รับรางวัลแล้ว กรุณาตรวจสอบช่องเก็บของ");

    }
}
