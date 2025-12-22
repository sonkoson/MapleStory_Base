importPackage(java.lang);

var currentTime = new Date();
var year = currentTime.getFullYear() + "";
var month = currentTime.getMonth() + 1 + "";
var date = currentTime.getDate() + "";
if (month < 10) {
    month = "0" + month;
}
if (date < 10) {
    date = "0" + date;
}
var todayDate = parseInt(year + month + date);

var questList = [
    ["ภารกิจรายวันกิจกรรม 01", "3k"],
    ["ภารกิจรายวันกิจกรรม 02", "5k"],
    ["ภารกิจรายวันกิจกรรม 03", "7k"],
    ["ภารกิจรายวันกิจกรรม 04", "10k"],
];

var monsterRequirementList = [
    [[8643003, 3000], [8643004, 3000]],
    [[8644008, 5000], [8644009, 5000]],
    [[8644405, 7000], [8644406, 7000]],
    [[8644508, 10000], [8644509, 10000]]
];

var rewardList = [
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
        var message = "  #fs14#   #i4001779##fc0xFFF781D8##e [Royal World] เทศกาลล่าสัตว์! #i4001779#\r\n#fs11##Cblue#              เธอสามารถทำได้วันละ 1 ครั้งเท่านั้นนะ#k\r\n\r\n";
        for (var i = 0; i < questList.length; i++) {
            message += "#L" + i + "##e#b[" + questList[i][0] + "]#n#k\r\n#d";
            for (var j = 0; j < monsterRequirementList[i].length; j++) {
                message += "   ㄴ#o" + monsterRequirementList[i][j][0] + "# " + monsterRequirementList[i][j][1] + " ตัว";
            }
            message += "\r\n\r\n";
        }
        cm.sendSimple(message);

    } else if (status == 1) {
        selectedQuest = selection;
        if (cm.getPlayer().getKeyValue("Quest_" + questList[selectedQuest][1]) == todayDate) {
            cm.sendOk("ภารกิจนี้เธอทำเสร็จสมบูรณ์ไปแล้วนะจ๊ะ");
            cm.dispose();
            return;
        } else if (cm.getPlayer().getKeyValue("Quest_" + questList[selectedQuest][1]) != 0) {
            cm.getPlayer().setKeyValue("Quest_" + questList[selectedQuest][1], 0);
            for (var j = 0; j < monsterRequirementList[selectedQuest].length; j++) {
                cm.getPlayer().setKeyValue("Quest_" + monsterRequirementList[selectedQuest][j][0], "0");
                cm.getPlayer().setKeyValue("QuestMax_" + monsterRequirementList[selectedQuest][j][0], monsterRequirementList[selectedQuest][j][1]);
            }
        }

        // If this is null, we need to set it again.
        if (cm.getPlayer().getKeyValue("QuestMax_" + questList[selectedQuest][0][0]) == null) {
            for (var j = 0; j < monsterRequirementList[selectedQuest].length; j++) {
                cm.getPlayer().setKeyValue("QuestMax_" + monsterRequirementList[selectedQuest][j][0], monsterRequirementList[selectedQuest][j][1]);
            }
        }

        isCleared = true;

        for (var j = 0; j < monsterRequirementList[selectedQuest].length; j++) {
            if (Number(cm.getPlayer().getKeyValue("Quest_" + monsterRequirementList[selectedQuest][j][0])) < monsterRequirementList[selectedQuest][j][1]) {
                isCleared = false;
                break;
            }
        }

        if (isCleared) {
            var message = "<รายการของรางวัล>\r\n";
            for (var i = 0; i < rewardList.length; i++) {
                message += "#i" + rewardList[i][0] + "##z" + rewardList[i][0] + "# " + rewardList[i][1] + " ชิ้น\r\n";
            }
            cm.sendYesNo("เธอทำตามเงื่อนไขเพื่อสำเร็จภารกิจนี้ครบถ้วนแล้วนะ ต้องการจะรับรางวัลเลยไหม?\r\n\r\n" + message);
        } else {
            var message = "#e#b[ภารกิจ " + questList[selectedQuest][1] + "]#n#k\r\n\r\n";
            for (var i = 0; i < monsterRequirementList[selectedQuest].length; i++) {
                count = cm.getPlayer().getKeyValue("Quest_" + monsterRequirementList[selectedQuest][i][0]);
                message += "#o" + monsterRequirementList[selectedQuest][i][0] + "# (" + count + " / " + monsterRequirementList[selectedQuest][i][1] + ") ตัว\r\n";
            }
            message += "\r\n#e#bเมื่อเธอกำจัดมอนสเตอร์ได้ครบตามจำนวนที่กำหนด ก็จะสามารถสำเร็จภารกิจนี้ได้นะ\r\n\r\n";

            message += "#fc0xFF6600CC#<รายการของรางวัล>\r\n";
            for (var i = 0; i < rewardList.length; i++) {
                message += "#i" + rewardList[i][0] + "##z" + rewardList[i][0] + "# " + rewardList[i][1] + " ชิ้น\r\n";
            }
            cm.sendSimple(message);
        }

    } else if (status == 2) {
        cm.dispose();
        isCleared = true;

        for (var j = 0; j < monsterRequirementList[selectedQuest].length; j++) {
            if (Number(cm.getPlayer().getKeyValue("Quest_" + monsterRequirementList[selectedQuest][j][0])) < monsterRequirementList[selectedQuest][j][1]) {
                isCleared = false;
                break;
            }
        }
        if (!isCleared) {
            return;
        }
        for (var i = 0; i < rewardList.length; i++) {
            if (!cm.canHold(rewardList[i][0], rewardList[i][1])) {
                cm.sendOk("ดูเหมือนกระเป๋าสัมภาระของเธอจะเต็มนะจ๊ะ รบกวนช่วยตรวจสอบดูหน่อยนะ");
                return;
            }
        }

        cm.getPlayer().setKeyValue("Quest_" + questList[selectedQuest][1], todayDate);

        for (var i = 0; i < rewardList.length; i++) {
            cm.gainItem(rewardList[i][0], rewardList[i][1]);
        }

        cm.sendOk("ภารกิจสำเร็จแล้วจ้า! ฉันมอบของรางวัลให้แล้วนะ ลองเช็คดูในกระเป๋าของเธอได้เลย");
    }
}





