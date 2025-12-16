package script.ArcaneRiver;

import scripting.newscripting.Script;
import scripting.newscripting.ScriptEngineNPC;

public class HotelArcs extends ScriptEngineNPC {

    @Script
    public void west_410003020() {
        if (getPlayer().getLevel() >= 270) {
            playPortalSE();
            registerTransferField(410003150, 1);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 270 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void west_410003080() {
        if (getPlayer().getLevel() >= 270) {
            playPortalSE();
            registerTransferField(410003000, 2);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 270 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }
}
