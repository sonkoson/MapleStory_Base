/* 
Daily Quest
Kill 100,000 Monsters
*/

importPackage(java.lang);

var currentTime = new Date();
var currentYear = currentTime.getFullYear() + "";
var currentMonth = currentTime.getMonth() + 1 + "";
var currentDate = currentTime.getDate() + "";
if (currentMonth < 10) {
    currentMonth = "0" + currentMonth;
}
if (currentDate < 10) {
    currentDate = "0" + currentDate;
}
var numericToday = parseInt(currentYear + currentMonth + currentDate);

var questList = [
    ["", [0, 0], [0, 0], [0, 0], [0, 0]],
];

var rewardList = [[0, 1], [2430658, 20]];
var secondaryRewardList = [[0, 0], [2430658, 20]];
var selectedChoice = 0;

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
        var dialogue = "  #fs14##i4001779##e[Maple Royal] รางวัลกำจัดมอนสเตอร์ 10,000 ตัว #i4001779#\r\n#fs11##Cblue#          ขอบคุณที่แวะมาล่ามอนสเตอร์ที่ Maple Royal เสมอมานะจ๊ะ#k\r\n";
        dialogue += "#fs12##L0##e#bมอนสเตอร์ในช่วงเลเวลของเธอ #r10,000 ตัว#l#l\r\n\r\n#fs11##Cgray#ชาว Maple Royal บอกว่าล่า 1 หมื่นตัวนี่แป๊บเดียวเองนะ!!! พยายามเข้าล่ะ!!.#k";
        for (var i = 1; i < questList.length; i++) {
            dialogue += "#L" + i + "##e#b[" + questList[i][0][0] + "]#n#k\r\n    #e#d?#o" + questList[i][1][0] + "# " + questList[i][1][1] + " ตัว\r\n   ?#o" + questList[i][2][0] + "# " + questList[i][2][1] + " ตัว#l\r\n\r\n";
        }
        cm.sendSimple(dialogue);

    } else if (status == 1) {
        selectedChoice = selection;
        if (selection != 0) {
            if (cm.getPlayer().getKeyValue(numericToday, "Quest_" + questList[selection][1][0]) == -1) {
                cm.getPlayer().setKeyValue(numericToday, "Quest_" + questList[selection][1][0], "0");
            }
            if (cm.getPlayer().getKeyValue(numericToday, "Quest_" + questList[selection][2][0]) == -1) {
                cm.getPlayer().setKeyValue(numericToday, "Quest_" + questList[selection][2][0], "0");
            }
            if (cm.getPlayer().getKeyValue(numericToday, "Quest_" + questList[selection][0][1]) == -1) {
                cm.getPlayer().setKeyValue(numericToday, "Quest_" + questList[selection][0][1], "0");
            }
            if (cm.getPlayer().getKeyValue(numericToday, "Quest_" + questList[selection][0][1]) == 1) {
                cm.sendOk("ภารกิจนี้เธอทำเสร็จเรียบร้อยแล้วจ้ะ");
                cm.dispose();
                return;
            } else if (cm.getPlayer().getKeyValue(numericToday, "Quest_" + questList[selection][1][0]) >= questList[selection][1][1] &&
                cm.getPlayer().getKeyValue(numericToday, "Quest_" + questList[selection][2][0]) >= questList[selection][2][1]) {
                if (selection >= 7 && selection <= 9) {
                    var rewardMessage = "<รายการของรางวัล>\r\n";
                    rewardMessage += secondaryRewardList[0][0] + " Meso\r\n";
                    for (var i = 1; i < secondaryRewardList.length; i++) {
                        rewardMessage += "#i" + secondaryRewardList[i][0] + "##z" + secondaryRewardList[i][0] + "# " + secondaryRewardList[i][1] + " ชิ้น\r\n";
                    }
                    cm.sendYesNo("เธอทำเงื่อนไขของภารกิจครบถ้วนแล้วนะจ๊ะ ต้องการจะจบภารกิจเลยไหม?\r\n\r\n" + rewardMessage);
                } else {
                    var rewardMessage = "<รายการของรางวัล>\r\n";
                    for (var i = 1; i < rewardList.length; i++) {
                        rewardMessage += "#i" + rewardList[i][0] + "##z" + rewardList[i][0] + "# " + rewardList[i][1] + " ชิ้น\r\n";
                    }
                    cm.sendYesNo("เธอทำเงื่อนไขของภารกิจครบถ้วนแล้วนะจ๊ะ ต้องการจะจบภารกิจเลยไหม?\r\n\r\n" + rewardMessage);
                }
            } else {
                var questInfo = "#e#b[ภารกิจ " + questList[selection][0][0] + "]#n#k\r\n" +
                    "#e#d?#o" + questList[selection][1][0] + "# " + questList[selection][1][1] + " ตัว\r\n" +
                    "?#o" + questList[selection][2][0] + "# " + questList[selection][2][1] + " ตัว\r\n\r\n";
                questInfo += "#e#b[ความคืบหน้า]#n#k\r\n" +
                    "#e#d?#o" + questList[selection][1][0] + "# : #b" + cm.getPlayer().getKeyValue(numericToday, "Quest_" + questList[selection][1][0]) + " ตัว#d/#r" + questList[selection][1][1] + " ตัว\r\n" +
                    "#e#d?#o" + questList[selection][2][0] + "# : #b" + cm.getPlayer().getKeyValue(numericToday, "Quest_" + questList[selection][2][0]) + " ตัว#d/#r" + questList[selection][2][1] + " ตัว\r\n\r\n";
                questInfo += "#e#bเมื่อล่ามอนสเตอร์ได้ครบตามจำนวน เธอจะสามารถจบภารกิจได้จ้ะ";
                cm.sendOk(questInfo);
                cm.dispose();
                return;
            }
        } else {
            if (cm.getPlayer().getKeyValue(2021, "mobcount") == -1) {
                cm.getPlayer().setKeyValue(2021, "mobcount", "0");
            }
            if (cm.getPlayer().getKeyValue(2021, "mobcount") >= 10000) {
                var progressInfo = "#e#b[กำจัดมอนสเตอร์ในช่วงเลเวล 10,000 ตัว]#n#k\r\n\r\n";
                progressInfo += "#e#d[ความคืบหน้า]#n#k\r\n" +
                    "#e#b" + cm.getPlayer().getKeyValue("2021", "mobcount") + " ตัว#d/#r10000 ตัว\r\n\r\n";
                progressInfo += "#n#kเมื่อล่ามอนสเตอร์ได้ครบ 10,000 ตัว เธอจะสามารถจบภารกิจได้จ้ะ";
                cm.sendYesNo("เธอทำเงื่อนไขของภารกิจครบถ้วนแล้วนะจ๊ะ ต้องการจะจบภารกิจเลยไหม?\r\n\r\n" + progressInfo);
            } else {
                var progressInfo = "#e#b[กำจัดมอนสเตอร์ในช่วงเลเวล 10,000 ตัว]#n#k\r\n\r\n";
                progressInfo += "#e#d[ความคืบหน้า]#n#k\r\n" +
                    "#e#b" + cm.getPlayer().getKeyValue(2021, "mobcount") + " ตัว#d/#r10000 ตัว\r\n\r\n";
                progressInfo += "#n#kเมื่อล่ามอนสเตอร์ได้ครบ 10,000 ตัว เธอจะสามารถจบภารกิจได้จ้ะ";
                cm.sendOk(progressInfo);
                cm.dispose();
                return;
            }
        }

    } else if (status == 2) {
        if (selectedChoice != 0) {

            if (selectedChoice >= 7 && selectedChoice <= 9) {
                if (cm.getInvSlots(1) >= 10) {
                    cm.getPlayer().setKeyValue(numericToday, "Quest_" + questList[selectedChoice][0][1], "1");
                    cm.gainItem(secondaryRewardList[1][0], secondaryRewardList[1][1]);
                    cm.gainItem(secondaryRewardList[2][0], secondaryRewardList[2][1]);
                    cm.gainItem(secondaryRewardList[3][0], secondaryRewardList[3][1]);
                    cm.gainItem(secondaryRewardList[4][0], secondaryRewardList[4][1]);
                    cm.gainItem(secondaryRewardList[5][0], secondaryRewardList[5][1]);
                    cm.gainItem(secondaryRewardList[6][0], secondaryRewardList[6][1]);
                    cm.gainItem(secondaryRewardList[7][0], secondaryRewardList[7][1]);
                    cm.gainItem(secondaryRewardList[8][0], secondaryRewardList[8][1]);
                    cm.gainItem(secondaryRewardList[9][0], secondaryRewardList[9][1]);
                    cm.gainItem(secondaryRewardList[10][0], secondaryRewardList[10][1]);
                    cm.sendOk("ทำภารกิจสำเร็จแล้วจ้ะ! ฉันมอบของรางวัลให้เรียบร้อยแล้ว รบกวนตรวจสอบในช่องเก็บของนะจ๊ะ");
                    cm.dispose();
                    return;
                } else {
                    cm.sendOk("อ๊ะ ดูเหมือนช่องเก็บของของเธอจะเต็มนะจ๊ะ รบกวนช่วยเช็คดูอีกทีนะ");
                    cm.dispose();
                    return;
                }
            } else {
                if (cm.getInvSlots(2) >= 3 && cm.getInvSlots(4) >= 1) {
                    cm.getPlayer().setKeyValue(numericToday, "Quest_" + questList[selectedChoice][0][1], "1");
                    cm.gainItem(rewardList[1][0], rewardList[1][1]);
                    cm.gainItem(rewardList[2][0], rewardList[2][1]);
                    cm.gainItem(rewardList[3][0], rewardList[3][1]);
                    cm.gainItem(rewardList[4][0], rewardList[4][1]);
                    cm.gainItem(rewardList[5][0], rewardList[5][1]);
                    cm.gainItem(rewardList[6][0], rewardList[6][1]);
                    cm.sendOk("ทำภารกิจสำเร็จแล้วจ้ะ! ฉันมอบของรางวัลให้เรียบร้อยแล้ว รบกวนตรวจสอบในช่องเก็บของนะจ๊ะ");
                    cm.dispose();
                    return;
                } else {
                    cm.sendOk("อ๊ะ ดูเหมือนช่องเก็บของของเธอจะเต็มนะจ๊ะ รบกวนช่วยเช็คดูอีกทีนะ");
                    cm.dispose();
                    return;
                }
            }
        } else if (selectedChoice == 0) {
            if (cm.getInvSlots(2) >= 3) {
                cm.getPlayer().setKeyValue(2021, "mobcount", "0");
                cm.gainMeso(rewardList[0][0]);
                cm.gainItem(rewardList[1][0], rewardList[1][1]);
                cm.sendOk("ทำภารกิจสำเร็จแล้วจ้ะ! ฉันมอบของรางวัลให้เรียบร้อยแล้ว รบกวนตรวจสอบในช่องเก็บของนะจ๊ะ");
                cm.dispose();
                return;
            } else {
                cm.sendOk("อ๊ะ ดูเหมือนช่องเก็บของของเธอจะเต็มนะจ๊ะ รบกวนช่วยเช็คดูอีกทีนะ");
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




