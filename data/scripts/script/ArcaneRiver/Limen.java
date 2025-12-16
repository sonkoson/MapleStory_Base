package script.ArcaneRiver;

import constants.ServerConstants;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import scripting.newscripting.Script;
import scripting.newscripting.ScriptEngineNPC;

public class Limen extends ScriptEngineNPC {

    public void BPReturn_dunkel() {
        initNPC(MapleLifeFactory.getNPC(9001000));
        if (getPlayer().getLevel() >= 255) {
            int v = target.askMenu("จะไปที่ไหนดี?\r\n#b#L0#กลับไปที่จัตุรัส#l\r\n#L1#ใจกลางน้ำตาแห่งโลก 3#l");
            switch (v) {
                case 0: { // Square
                    registerTransferField(ServerConstants.TownMap);
                    break;
                }
                case 1: { // Tears of the World Middle 3
                    registerTransferField(450012120, 3);
                    break;
                }
            }
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 255 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    public void BPReturn_BM() {
        initNPC(MapleLifeFactory.getNPC(9001000));
        if (getPlayer().getLevel() >= 255) {
            int v = target.askMenu("จะไปที่ไหนดี?\r\n#b#L0#กลับไปที่จัตุรัส#l\r\n#L1#สุดขอบโลก 1-3#l");
            switch (v) {
                case 0: { // Square
                    registerTransferField(ServerConstants.TownMap);
                    break;
                }
                case 1: { // End of the World 1-3
                    registerTransferField(450012320, 2);
                    break;
                }
            }
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 255 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    public void east_450012320() {
        initNPC(MapleLifeFactory.getNPC(9001000));
        if (getPlayer().getLevel() >= 255) {
            int v = target.askMenu("จะไปที่ไหนดี?\r\n#b#L0#หัวใจยักษ์#l\r\n#L1#สุดขอบโลก 2-1#l");
            switch (v) {
                case 0: { // Giant's Heart
                    registerTransferField(450012500, 2);
                    break;
                }
                case 1: { // End of the World 2-1
                    registerTransferField(450012400, 3);
                    break;
                }
            }
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 255 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void east_450012000() {
        if (getPlayer().getLevel() >= 255) {
            registerTransferField(450012010, 1);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 255 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void east_450012010() {
        if (getPlayer().getLevel() >= 255) {
            registerTransferField(450012100, 1);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 255 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void east_450012100() {
        if (getPlayer().getLevel() >= 255) {
            registerTransferField(450012110, 1);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 255 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void east_450012110() {
        if (getPlayer().getLevel() >= 255) {
            registerTransferField(450012120, 1);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 255 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void east_450012120() {
        if (getPlayer().getLevel() >= 255) {
            registerTransferField(450012200, 3);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 255 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }
}
