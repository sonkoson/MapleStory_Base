package script.ArcaneRiver;

import scripting.ScriptMessageFlag;
import scripting.newscripting.Script;
import scripting.newscripting.ScriptEngineNPC;
import objects.fields.gameobject.lifes.MapleLifeFactory;

public class Morass extends ScriptEngineNPC {

    @Script
    public void center_450006240() {
        if (getPlayer().getLevel() >= 230) {
            registerTransferField(450006400, 1);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 230 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void east_450006400() {
        if (getPlayer().getLevel() >= 230) {
            registerTransferField(450006410, 1);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 230 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void morassDQ_34285() {
        if (getPlayer().getLevel() >= 230) {
            if (getPlayer().getQuestStatus(34285) == 1) {
                /*
                 * 940204309 - pt_940204309
                 * 940204410 - pt_940204410
                 * 940204430 - pt_940204430
                 * 940204450 - pt_940204450
                 * 940204470 - pt_940204470
                 */
                registerTransferField(940204309, 1);
            } else {
                getPlayer().dropMessage(5, "Erda ในบริเวณนี้ดูเหมือนจะยังคงสงบอยู่");
            }
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 230 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void morassDQ_MP() {
        if (getPlayer().getLevel() >= 230) {
            getPlayer().dropMessage(5, "ที่แห่งนี้ดูเหมือนจะไม่มีปัญหาอะไรอื่น");
            // Erda seems stable here.
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 230 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    public void pt_940204309() {
        initNPC(MapleLifeFactory.getNPC(2007));
        if (getPlayer().getLevel() >= 230) {
            if (getPlayer().getItemQuantity(4036328, false) >= 50) {
                int v0 = target.askMenu(
                        "ดูเหมือนจะรวบรวม #i4036328# #b#t4036328##k ได้เพียงพอแล้ว\r\nเอากลับไปให้ Jean ที่ Trueffet Square กันเถอะ\r\n#L0#ไปยัง Trueffet Square#l\r\n#L1#ไปยังห้องวิจัย#l",
                        ScriptMessageFlag.FlipImage, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario);
                if (v0 == 0) {
                    registerTransferField(450006130);
                } else if (v0 == 1) {
                    registerTransferField(450006240);
                }
            } else {
                int v0 = target.askMenu(
                        "ยังรวบรวม #i4036328# #b#t4036328##k ได้ไม่ครบเลย\r\nจะทำอย่างไรดี?\r\n#L0#ไปยัง Trueffet Square#l\r\n#L1#ไปยังห้องวิจัย#l",
                        ScriptMessageFlag.FlipImage, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario);
                if (v0 == 0) {
                    registerTransferField(450006130);
                } else if (v0 == 1) {
                    registerTransferField(450006240);
                }
            }
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 230 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void east00_450006040() {
        if (getPlayer().getLevel() >= 230) {
            registerTransferField(450006130, 4);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 230 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void east_450006320() {
        if (getPlayer().getLevel() >= 235) {
            registerTransferField(450006330, 3);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 235 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

}
