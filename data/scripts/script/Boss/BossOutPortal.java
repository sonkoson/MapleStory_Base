package script.Boss;

import objects.fields.fieldset.FieldSet;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.item.MapleInventoryType;
import objects.users.MapleCharacter;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

public class BossOutPortal extends ScriptEngineNPC {
    public void pt_hillah() { // Hilla
        allBossOut();
    }

    public void pt_outrootaBoss() { // Root Abyss
        allBossOut();
    }

    public void pt_portalNPC1() { // Arkarium
        allBossOut();
    }

    public void will_out() { // Will
        allBossOut();
    }

    public void pt_bh_bossOutN() { // Normal Lotus Portal
        allBossOutPosition(350060300, 1);
    }

    public void pt_bh_bossOut() { // Hard Lotus Portal
        allBossOutPosition(350060300, 1);
    }

    public void pt_bh_bossOutN_R() { // Normal Lotus Reward Portal
        allBossOutPosition(350060300, 1);
    }

    public void pt_bh_bossOut_R() { // Hard Lotus Reward Portal
        allBossOutPosition(350060300, 1);
    }

    public void ptDemianOut() { // Damien Portal
        allBossOutPosition(105300303, 1);
    }

    public void ptDemianOut_R() { // Damien Reward Portal
        allBossOutPosition(105300303, 1);
    }

    public void pt_GC_out() { // Dunkel Portal & Reward Portal
        allBossOutPosition(450012200, 2);
    }

    public void pt_BM1_bossOut() { // Dusk Portal & Reward Portal
        allBossOutPosition(450009301, 1);
    }

    public void pt_jinHillah_out() { // Jin Hilla Portal & Reward Portal
        allBossOutPosition(450011990, 2);
    }

    public void serenOut() { // Seren Check & Reward Portal
        allBossOutPosition(410000670, 4);
    }

    public void allBossOut() {
        initNPC(MapleLifeFactory.getNPC(1530723));
        if (self.askYesNo("#fs11#ต้องการออกจากพื้นที่ต่อสู้หรือไม่?", ScriptMessageFlag.NpcReplacedByUser) == 1) {
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
        if (self.askYesNo("#fs11#ต้องการออกจากพื้นที่ต่อสู้หรือไม่?", ScriptMessageFlag.NpcReplacedByUser) == 1) {
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