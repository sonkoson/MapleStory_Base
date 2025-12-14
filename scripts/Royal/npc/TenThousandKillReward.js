/* Daily Quest
100,000 kill */

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
    ["", [0, 0], [0, 0], [0, 0], [0, 0]],
];

var reward = [[0, 1], [2430658, 20],];
var reward2 = [[0, 0], [2430658, 20],];
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
        var say = "  #fs14##i4001779##e[Maple Royal] รางวัล 10,000 Kill #i4001779#\r\n#fs11##Cblue#          ขอบคุณที่ล่ามอนสเตอร์ใน Maple Royal เสมอมา#k\r\n";
        say += "#fs12##L0##e#bมอนสเตอร์ในระดับเลเวล #r10,000 ตัว#l#l\r\n\r\n#fs11##Cgray#ผู้เล่น Maple Royal ล่า 10,000 ตัวได้สบายๆ!!! สู้ๆ!!.#k";
        for (var i = 1; i < quest.length; i++) {
            say += "#L" + i + "##e#b[" + quest[i][0][0] + "]#n#k\r\n    #e#dㄴ#o" + quest[i][1][0] + "# " + quest[i][1][1] + " ตัว\r\n   ㄴ#o" + quest[i][2][0] + "# " + quest[i][2][1] + " ตัว#l\r\n\r\n";
        }
        cm.sendSimple(say);

    } else if (status == 1) {
        choice = selection;
        if (selection != 0) {
            if (cm.getPlayer().getKeyValue(today, "Quest_" + quest[selection][1][0]) == -1) {
                cm.getPlayer().setKeyValue(today, "Quest_" + quest[selection][1][0], "0");
            }
            if (cm.getPlayer().getKeyValue(today, "Quest_" + quest[selection][2][0]) == -1) {
                cm.getPlayer().setKeyValue(today, "Quest_" + quest[selection][2][0], "0");
            }
            if (cm.getPlayer().getKeyValue(today, "Quest_" + quest[selection][0][1]) == -1) {
                cm.getPlayer().setKeyValue(today, "Quest_" + quest[selection][0][1], "0");
            }
            if (cm.getPlayer().getKeyValue(today, "Quest_" + quest[selection][0][1]) == 1) {
                cm.sendOk("เควสนี้เสร็จสิ้นแล้ว");
                cm.dispose();
                return;
            } else if (cm.getPlayer().getKeyValue(today, "Quest_" + quest[selection][1][0]) >= quest[selection][1][1] &&
                cm.getPlayer().getKeyValue(today, "Quest_" + quest[selection][2][0]) >= quest[selection][2][1]) {
                if (selection >= 7 && selection <= 9) {
                    var say = "<รายการของรางวัล>\r\n";
                    say += reward2[0][0] + " Meso\r\n";
                    for (var i = 1; i < reward2.length; i++) {
                        say += "#i" + reward2[i][0] + "##z" + reward2[i][0] + "# " + reward2[i][1] + " ชิ้น\r\n";
                    }
                    cm.sendYesNo("เงื่อนไขครบถ้วนแล้ว ต้องการจบเควสหรือไม่?\r\n\r\n" + say);
                } else {
                    var say = "<รายการของรางวัล>\r\n";
                    for (var i = 1; i < reward.length; i++) {
                        say += "#i" + reward[i][0] + "##z" + reward[i][0] + "# " + reward[i][1] + " ชิ้น\r\n";
                    }
                    cm.sendYesNo("เงื่อนไขครบถ้วนแล้ว ต้องการจบเควสหรือไม่?\r\n\r\n" + say);
                }
            } else {
                var say = "#e#b[" + quest[selection][0][0] + " Quest]#n#k\r\n" +
                    "#e#dㄴ#o" + quest[selection][1][0] + "# " + quest[selection][1][1] + " ตัว\r\n" +
                    "ㄴ#o" + quest[selection][2][0] + "# " + quest[selection][2][1] + " ตัว\r\n\r\n";
                say += "#e#b[ความคืบหน้า]#n#k\r\n" +
                    "#e#dㄴ#o" + quest[selection][1][0] + "# : #b" + cm.getPlayer().getKeyValue(today, "Quest_" + quest[selection][1][0]) + " ตัว#d/#r" + quest[selection][1][1] + " ตัว\r\n" +
                    "#e#dㄴ#o" + quest[selection][2][0] + "# : #b" + cm.getPlayer().getKeyValue(today, "Quest_" + quest[selection][2][0]) + " ตัว#d/#r" + quest[selection][2][1] + " ตัว\r\n\r\n";
                say += "#e#bสามารถจบเควสได้หลังจากล่ามอนสเตอร์ครบแล้ว";
                cm.sendOk(say);
                cm.dispose();
                return;
            }
        } else {
            if (cm.getPlayer().getKeyValue(2021, "mobcount") == -1) {
                cm.getPlayer().setKeyValue(2021, "mobcount", "0");
            }
            if (cm.getPlayer().getKeyValue(2021, "mobcount") >= 10000) {
                var say = "#e#b[ล่ามอนสเตอร์ในระดับเลเวล 10,000 ตัว]#n#k\r\n\r\n";
                say += "#e#d[ความคืบหน้า]#n#k\r\n" +
                    "#e#b" + cm.getPlayer().getKeyValue("2021", "mobcount") + " ตัว#d/#r10000 ตัว\r\n\r\n";
                say += "#n#kสามารถจบเควสได้หลังจากล่ามอนสเตอร์ในระดับเลเวลครบ 10,000 ตัว";
                cm.sendYesNo("เงื่อนไขครบถ้วนแล้ว ต้องการจบเควสหรือไม่?\r\n\r\n" + say);
            } else {
                var say = "#e#b[ล่ามอนสเตอร์ในระดับเลเวล 10,000 ตัว]#n#k\r\n\r\n";
                say += "#e#d[ความคืบหน้า]#n#k\r\n" +
                    "#e#b" + cm.getPlayer().getKeyValue(2021, "mobcount") + " ตัว#d/#r10000 ตัว\r\n\r\n";
                say += "#n#kสามารถจบเควสได้หลังจากล่ามอนสเตอร์ในระดับเลเวลครบ 10,000 ตัว";
                cm.sendOk(say);
                cm.dispose();
                return;
            }
        }

    } else if (status == 2) {
        if (choice != 0) {

            if (choice >= 7 && choice <= 9) {
                if (cm.getInvSlots(1) >= 10) {
                    cm.getPlayer().setKeyValue(today, "Quest_" + quest[choice][0][1], "1");
                    cm.gainItem(reward2[1][0], reward2[1][1]);
                    cm.gainItem(reward2[2][0], reward2[2][1]);
                    cm.gainItem(reward2[3][0], reward2[3][1]);
                    cm.gainItem(reward2[4][0], reward2[4][1]);
                    cm.gainItem(reward2[5][0], reward2[5][1]);
                    cm.gainItem(reward2[6][0], reward2[6][1]);
                    cm.gainItem(reward2[7][0], reward2[7][1]);
                    cm.gainItem(reward2[8][0], reward2[8][1]);
                    cm.gainItem(reward2[9][0], reward2[9][1]);
                    cm.gainItem(reward2[10][0], reward2[10][1]);
                    cm.sendOk("เควสเสร็จสิ้นและได้รับของรางวัลแล้ว กรุณาตรวจสอบช่องเก็บของ");
                    cm.dispose();
                    return;
                } else {
                    cm.sendOk("กรุณาตรวจสอบว่าช่องเก็บของว่างหรือไม่");
                    cm.dispose();
                    return;
                }
            } else {
                if (cm.getInvSlots(2) >= 3 && cm.getInvSlots(4) >= 1) {
                    cm.getPlayer().setKeyValue(today, "Quest_" + quest[choice][0][1], "1");
                    cm.gainItem(reward[1][0], reward[1][1]);
                    cm.gainItem(reward[2][0], reward[2][1]);
                    cm.gainItem(reward[3][0], reward[3][1]);
                    cm.gainItem(reward[4][0], reward[4][1]);
                    cm.gainItem(reward[5][0], reward[5][1]);
                    cm.gainItem(reward[6][0], reward[6][1]);
                    cm.sendOk("เควสเสร็จสิ้นและได้รับของรางวัลแล้ว กรุณาตรวจสอบช่องเก็บของ");
                    cm.dispose();
                    return;
                } else {
                    cm.sendOk("กรุณาตรวจสอบว่าช่องเก็บของว่างหรือไม่");
                    cm.dispose();
                    return;
                }
            }
        } else if (choice == 0) {
            if (cm.getInvSlots(2) >= 3) {
                cm.getPlayer().setKeyValue(2021, "mobcount", "0");
                cm.gainMeso(reward[0][0]);
                cm.gainItem(reward[1][0], reward[1][1]);
                cm.sendOk("เควสเสร็จสิ้นและได้รับของรางวัลแล้ว กรุณาตรวจสอบช่องเก็บของ");
                cm.dispose();
                return;
            } else {
                cm.sendOk("กรุณาตรวจสอบว่าช่องเก็บของว่างหรือไม่");
                cm.dispose();
                return;
            }
        } else {
            cm.dispose();
            return;
        }
    } else {
        cm.dispose();
        return;
    }
}
