package script.ArcaneRiver;

import objects.fields.gameobject.lifes.MapleLifeFactory;
import scripting.ScriptMessageFlag;
import scripting.newscripting.Script;
import scripting.newscripting.ScriptEngineNPC;

public class MoonBridge extends ScriptEngineNPC {

    @Script
    public void _450009100_up00() {
        if (getPlayer().getLevel() >= 245) {
            registerTransferField(450009200, 5);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 245 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void _450009200_down00() {
        if (getPlayer().getLevel() >= 245) {
            registerTransferField(450009100, 4);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 245 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void _450009200_up00() {
        if (getPlayer().getLevel() >= 245) {
            registerTransferField(450009300, 4);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 245 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void _450009300_down00() {
        if (getPlayer().getLevel() >= 245) {
            registerTransferField(450009200, 4);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 245 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void _450009300_up00() {
        if (getPlayer().getLevel() >= 245) {
            registerTransferField(450009301, 2);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 245 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    public void BM1_Ship() {
        if (getPlayer().getLevel() >= 245) {
            if (getPlayer().getMap().getId() == 450009300 && getPlayer().getLevel() >= 250) {
                int v = target.askMenu(
                        "สามารถใช้เรือเหาะขนาดเล็กเดินทางไปยังดาดฟ้าที่ต้องการได้\r\nจะไปที่ไหนดี?\r\n\r\n #b#L0#Crash Point#l\r\n\r\n #k#L2#ไม่ใช้เรือเหาะขนาดเล็ก",
                        ScriptMessageFlag.Self, ScriptMessageFlag.FlipImage);
                switch (v) {
                    case 0: { // Crash Point
                        registerTransferField(450011120, 3);
                        break;
                    }
                    case 2: // Do not use small airship
                        break;
                }
            } else if (getPlayer().getLevel() >= 245) {
                int v = target.askMenu(
                        "สามารถใช้เรือเหาะขนาดเล็กเดินทางไปยังดาดฟ้าที่ต้องการได้\r\nจะไปที่ไหนดี?\r\n\r\n #b#L0#Forward Base#l\r\n\r\n #k#L2#ไม่ใช้เรือเหาะขนาดเล็ก",
                        ScriptMessageFlag.Self, ScriptMessageFlag.FlipImage);
                switch (v) {
                    case 0: { // Crash Point
                        registerTransferField(450009050, 5);
                        break;
                    }
                    case 2: // Do not use small airship
                        break;
                }
            }
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 245 ขึ้นไปเท่านั้นจึงจะใช้งานได้");
        }
    }

    public void _450009050_out00() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (getPlayer().getLevel() >= 245) {
            if (1 == target.askYesNo("ต้องการย้ายไป Moon Bridge หรือไม่?", ScriptMessageFlag.Scenario,
                    ScriptMessageFlag.Self, ScriptMessageFlag.FlipImage)) {
                registerTransferField(450009100, 1);
            }
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 245 ขึ้นไปเท่านั้นจึงจะใช้งานได้");
        }
    }

}
