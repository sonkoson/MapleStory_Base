package script.ArcaneRiver;

import scripting.ScriptMessageFlag;
import scripting.newscripting.Script;
import scripting.newscripting.ScriptEngineNPC;

public class Lacheln extends ScriptEngineNPC {

    @Script
    public void out_450003000() {
        if (getPlayer().getLevel() >= 220) {
            registerTransferField(450002021, 1);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 220 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void _450003000_in04() {
        if (getPlayer().getLevel() >= 220) {
            registerTransferField(450003010, 2);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 220 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    public void arcana_fish() {
        if (getPlayer().getLevel() >= 225) {
            if (1 == self.askYesNo("#b(ต้องการขี่ปลานกกระจอกไป Arcana เดี๋ยวนี้เลยหรือไม่?)",
                    ScriptMessageFlag.NpcReplacedByNpc)) {
                registerTransferField(450005010, 1);
            }
        } else {
            self.sayOk("ต้องมีเลเวล 225 ขึ้นไปเท่านั้นจึงจะย้ายไป Arcana ได้", ScriptMessageFlag.NpcReplacedByNpc);
        }
    }

    @Script
    public void go_ballroom() {
        if (getPlayer().getLevel() >= 220) {
            registerTransferField(450003400, 4);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 220 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void go_clockTower() {
        if (getPlayer().getLevel() >= 220) {
            registerTransferField(450003500, 1);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 220 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void top_450003500() {
        if (getPlayer().getLevel() >= 220) {
            registerTransferField(450003510, 1);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 220 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void top_450003510() {
        if (getPlayer().getLevel() >= 220) {
            registerTransferField(450003520, 1);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 220 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void top_450003520() {
        if (getPlayer().getLevel() >= 220) {
            registerTransferField(450003530, 2);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 220 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void top_450003540() {
        if (getPlayer().getLevel() >= 220) {
            registerTransferField(450003600, 1);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 220 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }
}
