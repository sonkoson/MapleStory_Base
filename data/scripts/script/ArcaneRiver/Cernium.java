package script.ArcaneRiver;

import objects.fields.gameobject.lifes.MapleLifeFactory;
import scripting.ScriptMessageFlag;
import scripting.newscripting.Script;
import scripting.newscripting.ScriptEngineNPC;

public class Cernium extends ScriptEngineNPC {

    @Script
    public void east_410000560() {
        if (getPlayer().getLevel() >= 260) {
            playPortalSE();
            registerTransferField(410000570, 1);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 260 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void south_410000500() {
        if (getPlayer().getLevel() >= 260) {
            playPortalSE();
            registerTransferField(410000760, 1);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 260 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void BPReturn_Seren() {
        if (getPlayer().getLevel() >= 260) {
            playPortalSE();
            registerTransferField(410000500, 3);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 260 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void BPReturn_Seren2() {
        if (getPlayer().getLevel() >= 260) {
            playPortalSE();
            registerTransferField(410000680, 1);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 260 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void BPReturn_Seren3() {
        if (getPlayer().getLevel() >= 260) {
            playPortalSE();
            registerTransferField(410000690, 3);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 260 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void north_410000620() {
        if (getPlayer().getLevel() >= 260) {
            playPortalSE();
            registerTransferField(410000630, 2);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 260 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void west_410000630() {
        if (getPlayer().getLevel() >= 260) {
            playPortalSE();
            registerTransferField(410000620, 3);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 260 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void east_410000620() {
        if (getPlayer().getLevel() >= 260) {
            playPortalSE();
            registerTransferField(410000770, 2);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 260 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void west_410000770() {
        if (getPlayer().getLevel() >= 260) {
            playPortalSE();
            registerTransferField(410000620, 2);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 260 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    public void east01_410000500() {
        initNPC(MapleLifeFactory.getNPC(9001000));
        if (getPlayer().getLevel() >= 265) {
            String text = "จะไปที่ไหนดี?\r\n\r\n#b#L0#Burning Cernium#l";
            int v = target.askMenu(text, ScriptMessageFlag.Scenario, ScriptMessageFlag.Self,
                    ScriptMessageFlag.FlipImage);
            switch (v) { // Burning Cernium
                case 0: {
                    registerTransferField(410000800, 4);
                    break;
                }
            }
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 265 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    public void south_410000800() {
        initNPC(MapleLifeFactory.getNPC(9001000));
        if (getPlayer().getLevel() >= 265) {
            String text = "จะไปที่ไหนดี?\r\n\r\n#b#L0#Cernium Square#l";
            int v = target.askMenu(text, ScriptMessageFlag.Scenario, ScriptMessageFlag.Self,
                    ScriptMessageFlag.FlipImage);
            switch (v) { // Cernium
                case 0: {
                    registerTransferField(410000500, 5);
                    break;
                }
            }
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 265 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

}
