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
    ["Haven", "heaven1"]
];

var check = [
    [[8250003, 200], [8250011, 200]]
];

var reward = [
    [2435719, 300],
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
        var say = "นี่คือเควสประจำวัน โปรดเลือกเควสที่ต้องการทำ\r\n";
        //say += "#L0##e#bLevel Range Monsters #r(-20~+10 Level) #b10000#d (Unlimited)#l\r\n\r\n";
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
            cm.sendOk("เควสนี้ทำเสร็จเรียบร้อยแล้ว");
            cm.dispose();
            return;
        } else if (cm.getPlayer().getKeyValue("Quest_" + quest[sel][1]) != 0) {
            cm.getPlayer().setKeyValue("Quest_" + quest[sel][1], 0);
            for (var a = 0; a < check[sel].length; a++) {
                cm.getPlayer().setKeyValue("Quest_" + check[sel][a][0], "0");
                cm.getPlayer().setKeyValue("QuestMax_" + check[sel][a][0], check[sel][a][1]);
            }
        }

        // If null, reset
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
            var say = "< รายการของรางวัล >\r\n";
            for (var i = 0; i < reward.length; i++) {
                say += "#i" + reward[i][0] + "##z" + reward[i][0] + "# " + reward[i][1] + " ชิ้น\r\n";
            }
            cm.sendYesNo("เงื่อนไขสำหรับเควสนี้ครบแล้ว คุณต้องการส่งเควสหรือไม่?\r\n\r\n" + say);
        } else {
            var say = "#e#b[เควส " + quest[sel][0] + "]#n#k\r\n";
            for (var i = 0; i < check[sel].length; i++) {
                count = cm.getPlayer().getKeyValue("Quest_" + check[sel][i][0]);
                say += "#o" + check[sel][i][0] + "# (" + count + " / " + check[sel][i][1] + ") ตัว\r\n";
            }
            say += "#e#bสามารถส่งเควสได้หลังจากกำจัดมอนสเตอร์ครบแล้ว\r\n";

            say += "#L0##e#rย้ายไปยังแผนที่ล่า#l#k\r\n\r\n";
            say += "[หากกดจากแผนที่แรก จะย้ายไปแผนที่ที่สอง]\r\n\r\n";
            say += "#fc0xFF6600CC#< รายการของรางวัล >\r\n";
            for (var i = 0; i < reward.length; i++) {
                say += "#i" + reward[i][0] + "##z" + reward[i][0] + "# " + reward[i][1] + " ชิ้น\r\n";
            }
            cm.sendSimple(say);
        }

    } else if (status == 2) {
        cm.dispose();
        if (selection == 0) {
            if (cm.getPlayer().getMapId() != 310070130) {
                cm.warp(310070130, 0);
            } else {
                cm.warp(310070400, 0);
            }
        } else {
            for (var i = 0; i < reward.length; i++) {
                if (!cm.canHold(reward[i][0], reward[i][1])) {
                    cm.sendOk("โปรดตรวจสอบว่าช่องเก็บของในกระเป๋าของคุณเต็มหรือไม่");
                    return;
                }
            }

            cm.getPlayer().setKeyValue("Quest_" + quest[sel][1], Today);

            for (var i = 0; i < reward.length; i++) {
                cm.gainItem(reward[i][0], reward[i][1]);
            }

            cm.sendOk("เควสเสร็จสมบูรณ์และได้รับของรางวัลแล้ว โปรดตรวจสอบในกระเป๋าของคุณ");
        }
    }
}
