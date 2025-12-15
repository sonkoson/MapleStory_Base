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
            //cm.sayNpc("그러면 게임에 참여하고 싶을 때 다시 찾아와줘~", GameObjectType.Npc, false, false, ScriptMessageFlag.NpcReplacedByNpc);
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;

        if (status == 0) {
            var success = cm.getPlayer().getOneInfoQuestInteger(1234569, "miniGame3_success");
            var neoGem = cm.getPlayer().getOneInfoQuestInteger(1234569, "miniGame3_coin");
            var canGain = cm.getPlayer().canGainStackEventGauge(1); // Core Gem Daily Obtainable Amount
            var v0 = "Did you have fun? This time you sent #e#b" + success + "#n#k cards!\r\n\r\n";
            if (canGain <= 0) {
                v0 += "You have already reached the #eDaily Limit#n, so I can't give you more rewards~\r\n\r\n";
            } else {
                v0 += "I'll give you #e#b" + neoGem + " Neo Gems#k#n!";
            }
            v0 += "#r(Rewards are given upon exit)#k";

            cm.sayNpc(v0, GameObjectType.Npc, ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
        } else if (status == 1) {
            var neoGem = cm.getPlayer().getOneInfoQuestInteger(1234569, "miniGame3_coin");
            var prevneoGem = cm.getPlayer().getKeyValue(100712, "point");

            //cm.getPlayer().gainStackEventGauge(1, neoGem, false);
            cm.getPlayer().setKeyValue(100712, "point", prevneoGem + neoGem);
            cm.getPlayer().updateOneInfo(1234569, "miniGame3_coin", "0");
            cm.warp(ServerConstants.TownMap, 0);
            cm.dispose();
        }
    }
}