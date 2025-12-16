package script.ArcaneRiver;

import scripting.ScriptMessageFlag;
import scripting.newscripting.Script;
import scripting.newscripting.ScriptEngineNPC;

public class Esfera extends ScriptEngineNPC {

    @Script
    public void _450006330_door() {
        if (getPlayer().getLevel() >= 235) {
            registerTransferField(450007000, 2);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 235 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void pt_450007030() {
        if (getPlayer().getLevel() >= 235) {
            registerTransferField(450007040, 3);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 235 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void east_450007160() {
        if (getPlayer().getLevel() >= 240) {
            registerTransferField(450007170, 1);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 240 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    public void npc_3003533() {
        if (getPlayer().getLevel() >= 235) {
            if (1 == target.askYesNo("ต้องเข้าไปข้างใน", ScriptMessageFlag.Scenario, ScriptMessageFlag.Self,
                    ScriptMessageFlag.FlipImage)) {
                registerTransferField(450007200, 0);
            }
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 235 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

}
