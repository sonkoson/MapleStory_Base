/*
Daily Quest
Abandoned Camp: Kill 200 of certain monsters
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
    ["พื้นที่ตั้งแคมป์ร้าง", "trash"]
];

var completionCheck = [
    [[3503001, 200], [3503004, 200]]
];

var rewardList = [
    [2435719, 200],
];

var status = -1;
var selectedQuestIndex;

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
        var dialogue = "นี่คือภารกิจรายวัน กรุณาเลือกภารกิจที่ต้องการทำ\r\n";
        //dialogue += "#L0##e#bLevel Range Monster #r(-20~+10 Level) #b10000 Mobs#d (Unlimited)#l\r\n\r\n";
        for (var i = 0; i < questList.length; i++) {
            dialogue += "#L" + i + "##e#b[" + questList[i][0] + "]#n#k\r\n#e#d";
            for (var a = 0; a < completionCheck[i].length; a++) {
                dialogue += "   ㄴ#o" + completionCheck[i][a][0] + "# " + completionCheck[i][a][1] + " ตัว\r\n";
            }
            dialogue += "\r\n";
        }
        cm.sendSimple(dialogue);

    } else if (status == 1) {
        selectedQuestIndex = selection;

        if (cm.getPlayer().getKeyValue("Quest_" + questList[selectedQuestIndex][1]) == numericToday) {
            cm.sendOk("ภารกิจนี้เสร็จสมบูรณ์แล้ว");
            cm.dispose();
            return;
        } else if (cm.getPlayer().getKeyValue("Quest_" + questList[selectedQuestIndex][1]) != 0) {
            cm.getPlayer().setKeyValue("Quest_" + questList[selectedQuestIndex][1], "0");
            for (var a = 0; a < completionCheck[selectedQuestIndex].length; a++) {
                cm.getPlayer().setKeyValue("Quest_" + completionCheck[selectedQuestIndex][a][0], "0");
                cm.getPlayer().setKeyValue("QuestMax_" + completionCheck[selectedQuestIndex][a][0], completionCheck[selectedQuestIndex][a][1]);
            }
        }

        // Reset if null
        if (cm.getPlayer().getKeyValue("QuestMax_" + completionCheck[selectedQuestIndex][0][0]) == null) {
            for (var a = 0; a < completionCheck[selectedQuestIndex].length; a++) {
                cm.getPlayer().setKeyValue("QuestMax_" + completionCheck[selectedQuestIndex][a][0], completionCheck[selectedQuestIndex][a][1]);
            }
        }

        var isQuestCleared = true;

        for (var a = 0; a < completionCheck[selectedQuestIndex].length; a++) {
            if (Number(cm.getPlayer().getKeyValue("Quest_" + completionCheck[selectedQuestIndex][a][0])) < completionCheck[selectedQuestIndex][a][1]) {
                isQuestCleared = false;
                break;
            }
        }

        if (isQuestCleared) {
            var rewardMessage = "<รายการรางวัล>\r\n";
            for (var i = 0; i < rewardList.length; i++) {
                rewardMessage += "#i" + rewardList[i][0] + "##z" + rewardList[i][0] + "# " + rewardList[i][1] + " ชิ้น\r\n";
            }
            cm.sendYesNo("เงื่อนไขการทำภารกิจนี้ครบถ้วนแล้ว ต้องการรับรางวัลหรือไม่?\r\n\r\n" + rewardMessage);
        } else {
            var questInfo = "#e#b[ภารกิจ " + questList[selectedQuestIndex][1] + "]#n#k\r\n";
            for (var i = 0; i < completionCheck[selectedQuestIndex].length; i++) {
                var currentKillCount = cm.getPlayer().getKeyValue("Quest_" + completionCheck[selectedQuestIndex][i][0]);
                questInfo += "#o" + completionCheck[selectedQuestIndex][i][0] + "# (" + currentKillCount + " / " + completionCheck[selectedQuestIndex][i][1] + ") ตัว\r\n";
            }
            questInfo += "#e#bกรุณากำจัดมอนสเตอร์ให้ครบก่อนจึงจะรับรางวัลได้\r\n";

            questInfo += "#L0##e#rย้ายไปยังพื้นที่ล่า#l#k\r\n\r\n";
            questInfo += "[เมื่อย้ายจากแผนที่แรก จะไปยังแผนที่ที่สอง]\r\n\r\n";
            questInfo += "#fc0xFF6600CC#<รายการรางวัล>\r\n";
            for (var i = 0; i < rewardList.length; i++) {
                questInfo += "#i" + rewardList[i][0] + "##z" + rewardList[i][0] + "# " + rewardList[i][1] + " ชิ้น\r\n";
            }
            cm.sendSimple(questInfo);
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

            for (var i = 0; i < rewardList.length; i++) {
                if (!cm.canHold(rewardList[i][0], rewardList[i][1])) {
                    cm.sendOk("กรุณาตรวจสอบว่าช่องเก็บของเต็มหรือไม่เพียงพอ");
                    return;
                }
            }

            cm.getPlayer().setKeyValue("Quest_" + questList[selectedQuestIndex][1], numericToday);

            for (var i = 0; i < rewardList.length; i++) {
                cm.gainItem(rewardList[i][0], rewardList[i][1]);
            }

            cm.sendOk("ภารกิจเสร็จสมบูรณ์และได้รับรางวัลแล้ว กรุณาตรวจสอบช่องเก็บของ");
        }
    }
}
