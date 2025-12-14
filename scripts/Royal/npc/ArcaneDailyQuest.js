/*Daily Quest
 Vanishing Journey: Defeat 100 Joyful Erdas, 100 Raging Erdas	Joyful-8641000 Raging-8641001				
 Chu Chu Island: Defeat 100 Crilla, 100 Bershark			Crilla-8642012 Bershark-8642014				
 Lachelein: Defeat 100 Angry Masquerade Citizens, 100 Insane Masquerade Citizens		Angry-8643008 Insane-8643009				
 Arcana: Defeat 200 Mournful Spirits, 200 Discordant Spirits			Mournful-8644009 Discordant-8644008				
 Morass: Defeat 200 Blue Shadows, 200 Red Shadows			Blue-8644405 Red-8644406				
 Espera: Defeat 300 Aranya, 300 Aranea			Aranya-8644504 Aranea-8644505	*/

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
    ["Vanishing Journey", "River"],
    ["Chu Chu Island", "ChewChew"],
    ["Lachelein", "Lacheln"],
    ["Arcana", "Arcana"],
    ["Morass", "Morass"],
    ["Espera", "Espera"],
];

var check = [
    [[8641000, 100], [8641001, 100]],
    [[8642012, 100], [8642013, 100]],
    [[8643008, 100], [8643009, 100]],
    [[8644008, 200], [8644009, 200]],
    [[8644405, 200], [8644406, 200]],
    [[8644504, 300], [8644505, 300]]
];

var reward = [
    [2437760, 100],
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
        var say = "นี่คือเควสรายวัน กรุณาเลือกเควสที่คุณต้องการทำ\r\n";
        //say += "#L0##e#b레벨범위 몬스터 #r(-20~+10레벨) #b10000마리#d (무제한)#l\r\n\r\n";
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
            cm.sendOk("เควสนี้ทำเสร็จแล้ว");
            cm.dispose();
            return;
        } else if (cm.getPlayer().getKeyValue("Quest_" + quest[sel][1]) != 0) {
            cm.getPlayer().setKeyValue("Quest_" + quest[sel][1], 0);
            for (var a = 0; a < check[sel].length; a++) {
                cm.getPlayer().setKeyValue("Quest_" + check[sel][a][0], "0");
                cm.getPlayer().setKeyValue("QuestMax_" + check[sel][a][0], check[sel][a][1]);
            }
        }

        // 이게 null이면 다시 설정해줘야댐.
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
            cm.sendYesNo("เงื่อนไขในการทำเควสนี้ครบแล้ว ต้องการส่งเควสหรือไม่?\r\n\r\n" + say);
        } else {
            var say = "#e#b[เควส " + quest[sel][0] + "]#n#k\r\n";
            for (var i = 0; i < check[sel].length; i++) {
                count = cm.getPlayer().getKeyValue("Quest_" + check[sel][i][0]);
                say += "#o" + check[sel][i][0] + "# (" + count + " / " + check[sel][i][1] + ") ตัว\r\n";
            }
            say += "#e#bคุณสามารถส่งเควสได้หลังจากกำจัดมอนสเตอร์ครบแล้ว\r\n";

            /*            say += "#L0##e#r사냥터로 이동하기#l#k\r\n\r\n";
                        say += "[첫번째 맵에서 이동시 두번째맵으로 이동합니다]\r\n\r\n";*/
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
                cm.sendOk("กรุณาตรวจสอบว่าช่องเก็บของว่างหรือไม่");
                return;
            }
        }

        cm.getPlayer().setKeyValue("Quest_" + quest[sel][1], Today);

        for (var i = 0; i < reward.length; i++) {
            cm.gainItem(reward[i][0], reward[i][1]);
        }

        cm.sendOk("ทำเควสสำเร็จและได้รับรางวัลแล้ว กรุณาตรวจสอบช่องเก็บของ");
    }
}
