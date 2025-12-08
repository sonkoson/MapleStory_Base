package script.Boss;

import objects.fields.fieldset.FieldSet;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.item.MapleInventoryType;
import objects.users.MapleCharacter;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

public class BossOutPortal extends ScriptEngineNPC {
    public void pt_hillah() { // Èú¶ó
        allBossOut();
    }

    public void pt_outrootaBoss() { // ·çÅ¸ºñ½º
        allBossOut();
    }

    public void pt_portalNPC1() { // ¾ÆÄ«ÀÌ·³
        allBossOut();
    }

    public void will_out() { // Àª
        allBossOut();
    }

    public void pt_bh_bossOutN() { // ³ë¸»½º¿ì Æ÷Å»
        allBossOutPosition(350060300, 1);
    }

    public void pt_bh_bossOut() { // ÇÏµå½º¿ì Æ÷Å»
        allBossOutPosition(350060300, 1);
    }

    public void pt_bh_bossOutN_R() { // ³ë¸Ö½º¿ì º¸»ó¸ÊÆ÷Å»
        allBossOutPosition(350060300, 1);
    }

    public void pt_bh_bossOut_R() { // ÇÏµå½º¿ì º¸»ó¸ÊÆ÷Å»
        allBossOutPosition(350060300, 1);
    }

    public void ptDemianOut() { // µ¥¹Ì¾È Æ÷Å»
        allBossOutPosition(105300303, 1);
    }

    public void ptDemianOut_R() { // µ¥¹Ì¾È º¸»ó¸ÊÆ÷Å»
        allBossOutPosition(105300303, 1);
    }

    public void pt_GC_out() { // µáÄÌ Æ÷Å» & º¸»ó¸Ê Æ÷Å»
        allBossOutPosition(450012200, 2);
    }

    public void pt_BM1_bossOut() { // ´õ½ºÅ© Æ÷Å» & º¸»ó¸Ê Æ÷Å»
        allBossOutPosition(450009301, 1);
    }

    public void pt_jinHillah_out() { // ÁøÈú¶ó Æ÷Å» & º¸»ó¸Ê Æ÷Å»
        allBossOutPosition(450011990, 2);
    }

    public void serenOut() { // ¼¼·» °ÔÀÌÆ® & º¸»ó¸ÊÆ÷Å»
        allBossOutPosition(410000670, 4);
    }

    public void allBossOut() {
        initNPC(MapleLifeFactory.getNPC(1530723));
        if (self.askYesNo("#fs11#ÀüÅõ¸¦ ÁßÁöÇÏ°í ³ª°¥±î?", ScriptMessageFlag.NpcReplacedByUser) == 1) {
            if (getPlayer().getEventInstance() != null) {
                getPlayer().getEventInstance().unregisterPlayer(getPlayer());
                getPlayer().setEventInstance(null);
                getPlayer().setRegisterTransferFieldTime(0);
                getPlayer().setRegisterTransferField(0);
            }
            if (getPlayer().getBossMode() == 1) {
                getPlayer().setBossMode(0);
            }
            registerTransferField(getPlayer().getMap().getReturnMap().getId());
        }
    }

    public void allBossOutPosition(int Map, int Portal) {
        initNPC(MapleLifeFactory.getNPC(1530723));
        if (self.askYesNo("#fs11#ÀüÅõ¸¦ ÁßÁöÇÏ°í ³ª°¥±î?", ScriptMessageFlag.NpcReplacedByUser) == 1) {
            if (getPlayer().getEventInstance() != null) {
                getPlayer().getEventInstance().unregisterPlayer(getPlayer());
                getPlayer().setEventInstance(null);
                getPlayer().setRegisterTransferFieldTime(0);
                getPlayer().setRegisterTransferField(0);
            }
            if (getPlayer().getBossMode() == 1) {
                getPlayer().setBossMode(0);
            }
            registerTransferField(Map, Portal);
        }
    }

}