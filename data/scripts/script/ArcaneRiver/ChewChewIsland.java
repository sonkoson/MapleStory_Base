package script.ArcaneRiver;

import scripting.ScriptMessageFlag;
import scripting.newscripting.Script;
import scripting.newscripting.ScriptEngineNPC;

public class ChewChewIsland extends ScriptEngineNPC {

    @Script
    public void out_450002025() {
        if (getPlayer().getLevel() >= 215) {
            registerTransferField(450015020, 7);
        } else {
            getPlayer().dropMessage(5, "215เลเวล 이상만 เข้า하실 수 있.");
        }
    }

    @Script
    public void out_450015170() {
        if (getPlayer().getLevel() >= 215) {
            registerTransferField(450015180, 1);
        } else {
            getPlayer().dropMessage(5, "215เลเวล 이상만 เข้า하실 수 있.");
        }
    }

    @Script
    public void pt_BackToArc1() {
        if (getPlayer().getLevel() >= 210) {
            registerTransferField(450001250, 1);
        } else {
            getPlayer().dropMessage(5, "210เลเวล 이상만 เข้า하실 수 있.");
        }
    }

    @Script
    public void go_deepForest() {
        getPlayer().dropMessage(5, "이용하실 수 없는 포탈.");
    }

    public void goToLehel() {
        if (getPlayer().getLevel() >= 220) {
            if (1 == self.askYesNo("#b무토#k...이제 배 부르다... #b움직여 줄까#k...?\r\n\r\n(무토가 비켜สัปดาห์면 아케인리버를 따라 ถัดไป พื้นที่으로 갈 수 있.)", ScriptMessageFlag.NpcReplacedByNpc)) {
                registerTransferField(450003000, 0);
            }
        } else {
            self.sayOk("무토 아직 배가 고프다.. 지ฉัน가고 싶으면 เลเวล 220 넘기고 และ라", ScriptMessageFlag.NpcReplacedByNpc);
        }
    }

}
