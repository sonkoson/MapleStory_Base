importPackage(Packages.scripting);
importPackage(Packages.constants);

var status = -1;
var sel = -1;
var sel2 = -1;

function start() {
    status = -1;
    sel = -1;
    sel2 = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0 && type == 3 && selection == -1) {
            //cm.sayNpc("Business is not easy...", GameObjectType.Npc, false, false, ScriptMessageFlag.NpcReplacedByNpc);
            cm.dispose();
            return;
        } else if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;

        if (status == 0) {
            var result = cm.getPlayer().getOneInfoQuestInteger(1234569, "miniGame1_result");
            var canGain = cm.getPlayer().canGainStackEventGauge(1); // Core Gem Daily Limit

            if (result == 2) { // Surrender
                var v0 = "ยอมแพ้กลางคันแบบนี้ น่าผิดหวังจริงๆ!\r\nฉันให้รางวัลกับคนที่ไม่ความอดทนไม่ได้หรอกนะ";
                cm.sayNpc(v0, GameObjectType.Npc, ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
            } else if (result == 1) { // Victory

                var v0 = "ผู้ชนะในเกมนี้คือ #b#h0##k! เก่งใช้ได้เลยนี่?\r\n\r\n";
                if (canGain <= 0) {
                    v0 += "วันนี้คุณได้รับรางวัลครบ #e#rDaily Limit#k#n แล้ว ไม่สามารถรับเพิ่มได้อีก";
                } else {
                    v0 += "ฉันจะให้ #e#bNeo Gem 60 อัน#k#n นะ!\r\n\r\n#r(รางวัลจะได้รับเมื่อออกจากแผนที่)#k";
                }
                cm.sayNpc(v0, GameObjectType.Npc, ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
            } else if (result == 4) { // Defeat
                var v0 = "น่าเสียดายจัง ไว้มาตัดสินกันคราวหน้านะ!\r\n\r\n";
                if (canGain <= 0) {
                    v0 += "วันนี้คุณได้รับรางวัลครบ #e#rDaily Limit#k#n แล้ว ไม่สามารถรับเพิ่มได้อีก";
                } else {
                    v0 += "ฉันจะให้ #e#bNeo Gem 20 อัน#k#n นะ!\r\n\r\n#r(รางวัลจะได้รับเมื่อออกจากแผนที่)#k";
                }
                cm.sayNpc(v0, GameObjectType.Npc, ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
            } else if (result == 3) { // Draw
                var v0 = "เสมอเหรอเนี่ย เป็นการต่อสู้ที่ดุเดือดจริงๆ!\r\n\r\n";
                if (canGain <= 0) {
                    v0 += "วันนี้คุณได้รับรางวัลครบ #e#rDaily Limit#k#n แล้ว ไม่สามารถรับเพิ่มได้อีก";
                } else {
                    v0 += "ฉันจะให้ #e#bNeo Gem 30 อัน#k#n นะ!\r\n\r\n#r(รางวัลจะได้รับเมื่อออกจากแผนที่)#k";
                }
                cm.sayNpc(v0, GameObjectType.Npc, ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
            } else {
                // Forced Exit
                var v0 = "ออกกลางคันแบบนี้ น่าผิดหวังจริงๆ!\r\nฉันให้รางวัลกับคนที่ไม่ความอดทนไม่ได้หรอกนะ";
                cm.sayNpc(v0, GameObjectType.Npc, ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
            }
        } else if (status == 1) {
            var result = cm.getPlayer().getOneInfoQuestInteger(1234569, "miniGame1_result");
            var canGain = cm.getPlayer().canGainStackEventGauge(1); // Core Gem daily obtain amount
            var prevneoGem = cm.getPlayer().getKeyValue(100712, "point");

            if (canGain > 0) {

                if (result == 1) {
                    cm.getPlayer().setKeyValue(100712, "point", prevneoGem + 60);
                    //cm.getPlayer().gainStackEventGauge(1, 60, false);
                } else if (result == 4) {
                    cm.getPlayer().setKeyValue(100712, "point", prevneoGem + 20);
                    //cm.getPlayer().gainStackEventGauge(1, 20, false);
                } else if (result == 3) {
                    cm.getPlayer().setKeyValue(100712, "point", prevneoGem + 30);
                    //cm.getPlayer().gainStackEventGauge(1, 30, false);
                }
            }
            cm.warp(ServerConstants.TownMap, 0);
            cm.dispose();
        }
    }
}