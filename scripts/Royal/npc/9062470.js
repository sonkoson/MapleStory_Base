importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);

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
            //cm.sayNpc("If you want to play again, come back anytime~", GameObjectType.Npc, false, false, ScriptMessageFlag.NpcReplacedByNpc);
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;

        if (status == 0) {
            var neoGem = cm.getPlayer().getOneInfoQuestInteger(1234569, "miniGame4_coin");
            var canGain = cm.getPlayer().canGainStackEventGauge(1); // Core Gem Daily Limit
            var v0 = "สนุกไหมล่ะ~?\r\n\r\n";
            if (canGain <= 0) {
                v0 += "คุณได้รับรางวัลครบ #eDaily Limit#n ของวันนี้แล้ว ไม่สามารถรับเพิ่มได้อีกนะ~\r\n\r\n";
            } else {
                v0 += "ฉันจะมอบ #e#bNeo Gem " + parseInt(Math.round(neoGem / 100)) + " อัน#k#n ให้คุณนะ!\r\n\r\n";
            }
            v0 += "#r(รางวัลจะได้รับเมื่อออกจากแผนที่)#k";

            cm.sayNpc(v0, GameObjectType.Npc, ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
        } else if (status == 1) {
            var neoGem = cm.getPlayer().getOneInfoQuestInteger(1234569, "miniGame4_coin");
            var prevneoGem = cm.getPlayer().getKeyValue(100712, "point");
            var plusneoGem = parseInt(Math.round(neoGem / 100));

            //cm.getPlayer().gainStackEventGauge(1, parseInt(Math.round(neoGem/100)), false);
            cm.getPlayer().setKeyValue(100712, "point", prevneoGem + plusneoGem);
            cm.getPlayer().updateOneInfo(1234569, "miniGame4_coin", "0");
            cm.warp(ServerConstants.TownMap, 0);
            cm.dispose();
        }
    }
}