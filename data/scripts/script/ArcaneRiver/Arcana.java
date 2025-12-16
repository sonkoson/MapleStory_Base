package script.ArcaneRiver;

import scripting.ScriptMessageFlag;
import scripting.newscripting.Script;
import scripting.newscripting.ScriptEngineNPC;

public class Arcana extends ScriptEngineNPC {

    @Script
    public void _450005010_pt00() {
        if (getPlayer().getLevel() >= 225) {
            registerTransferField(450005015, 1);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 225 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void _450005015_east00() {
        if (getPlayer().getLevel() >= 225) {
            registerTransferField(450005100, 2);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 225 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void _450005130_east00() {
        if (getPlayer().getLevel() >= 225) {
            registerTransferField(450005000, 1);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 225 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void _450005000_out10() {
        if (getPlayer().getLevel() >= 225) {
            registerTransferField(450005200, 1);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 225 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void _450005300_col00() {
        if (getPlayer().getLevel() >= 225) {
            registerTransferField(450005400, 1);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 225 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void _450005400_pt00() {
        if (getPlayer().getLevel() >= 225) {
            registerTransferField(450005500, 2);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 225 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    public void morass_fish() {
        if (getPlayer().getLevel() >= 230) {
            if (1 == target.askAccept(
                    "เอาล่ะ งั้นขี่ปลานกกระจอกแล้วไปยังพื้นที่ถัดไปกันเลยไหม?\r\n#b(หากตอบตกลงจะย้ายแผนที่อัตโนมัติ)",
                    ScriptMessageFlag.Scenario, ScriptMessageFlag.Self, ScriptMessageFlag.FlipImage)) {
                registerTransferField(450006000, 0);
            }
        } else {
            self.sayOk("ต้องมีเลเวล 230 ขึ้นไปเท่านั้นจึงจะย้ายไป Morass ได้", ScriptMessageFlag.NpcReplacedByNpc);
        }
    }

}
