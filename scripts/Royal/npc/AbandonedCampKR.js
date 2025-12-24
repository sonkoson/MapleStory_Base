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
    ["ㅸ擄앗》戾뛰》姑롯蓼", "trash"]
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
        var dialogue = "밌筽勒잃찼盜촤쪄箕③?첬′밝맹よ픈瑄勒÷櫓∇㉦戾들系⌒첨沓배睾밀\r\n";
        //dialogue += "#L0##e#bLevel Range Monster #r(-20~+10 Level) #b10000 Mobs#d (Unlimited)#l\r\n\r\n";
        for (var i = 0; i < questList.length; i++) {
            dialogue += "#L" + i + "##e#b[" + questList[i][0] + "]#n#k\r\n#e#d";
            for (var a = 0; a < completionCheck[i].length; a++) {
                dialogue += "   ?#o" + completionCheck[i][a][0] + "# " + completionCheck[i][a][1] + " 둘?r\n";
            }
            dialogue += "\r\n";
        }
        cm.sendSimple(dialogue);

    } else if (status == 1) {
        selectedQuestIndex = selection;

        if (cm.getPlayer().getKeyValue("Quest_" + questList[selectedQuestIndex][1]) == numericToday) {
            cm.sendOk("잃찼盜밌禹맹롭線춰ⓣ촹쨘췌睾笑茸③?);
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
            var rewardMessage = "<촤징櫓♨㎴廊한?\r\n";
            for (var i = 0; i < rewardList.length; i++) {
                rewardMessage += "#i" + rewardList[i][0] + "##z" + rewardList[i][0] + "# " + rewardList[i][1] + " ぴ埇\r\n";
            }
            cm.sendYesNo("錫庫岱㎓琓밥⇔系잃찼盜ㅓ볘茸밞톱퓜珪慰 들系⌒챔珪봉櫓∇ⓣ태齷?\r\n\r\n" + rewardMessage);
        } else {
            var questInfo = "#e#b[잃찼盜 " + questList[selectedQuestIndex][1] + "]#n#k\r\n";
            for (var i = 0; i < completionCheck[selectedQuestIndex].length; i++) {
                var currentKillCount = cm.getPlayer().getKeyValue("Quest_" + completionCheck[selectedQuestIndex][i][0]);
                questInfo += "#o" + completionCheck[selectedQuestIndex][i][0] + "# (" + currentKillCount + " / " + completionCheck[selectedQuestIndex][i][1] + ") 둘?r\n";
            }
            questInfo += "#e#b旋羸考脘죌믐碩稿隣닻ㅓ볕怒ⓖ므?錫繫芹怒櫓땍봉櫓∇ⓧ닻③?r\n";

            questInfo += "#L0##e#r西테琓뮤友쭝뼘欺街拈롱訛脘#l#k\r\n\r\n";
            questInfo += "[旋羸昆ㅕ羸攷쮸擄ⓕ■섰롱矮찼 ⓓ餓쫓㎭섰롱瓮戾姦㎤優]\r\n\r\n";
            questInfo += "#fc0xFF6600CC#<촤징櫓♨㎴廊한?\r\n";
            for (var i = 0; i < rewardList.length; i++) {
                questInfo += "#i" + rewardList[i][0] + "##z" + rewardList[i][0] + "# " + rewardList[i][1] + " ぴ埇\r\n";
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
                    cm.sendOk("供?다繕죽攷よ系胥瀛♨㎖系錫繫及든졸珪慰 첬′묫完쭌ょㅄ目蘿롱밀");
                    return;
                }
            }

            cm.getPlayer().setKeyValue("Quest_" + questList[selectedQuestIndex][1], numericToday);

            for (var i = 0; i < rewardList.length; i++) {
                cm.gainItem(rewardList[i][0], rewardList[i][1]);
            }

            cm.sendOk("롭잃찼盜柬煽葉笑茸③? ⒠뮐故♨㎴廊한톈鉅煽覽봤于쭐톱?첬′뭇쳬㉹故尸よ系胥瀛♨㏏珪慰");
        }
    }
}



