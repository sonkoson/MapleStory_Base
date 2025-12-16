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
            getPlayer().dropMessage(5, "ต้องมีเลเวล 215 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void out_450015170() {
        if (getPlayer().getLevel() >= 215) {
            registerTransferField(450015180, 1);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 215 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void pt_BackToArc1() {
        if (getPlayer().getLevel() >= 210) {
            registerTransferField(450001250, 1);
        } else {
            getPlayer().dropMessage(5, "ต้องมีเลเวล 210 ขึ้นไปเท่านั้นจึงจะเข้าได้");
        }
    }

    @Script
    public void go_deepForest() {
        getPlayer().dropMessage(5, "พอร์ทัลไม่สามารถใช้งานได้");
    }

    public void goToLehel() {
        if (getPlayer().getLevel() >= 220) {
            if (1 == self.askYesNo(
                    "#bMuto#k...อิ่มแล้วแฮะ... #bจะให้ขยับให้ไหมนะ#k...?\r\n\r\n(ถ้า Muto หลีกทางให้ ก็จะสามารถเดินทางไปตาม Arcane River สู่พื้นที่ถัดไปได้)",
                    ScriptMessageFlag.NpcReplacedByNpc)) {
                registerTransferField(450003000, 0);
            }
        } else {
            self.sayOk("Muto ยังหิวอยู่เลย... ถ้าอยากผ่านไป ก็กลับมาเมื่อเลเวล 220 นะ",
                    ScriptMessageFlag.NpcReplacedByNpc);
        }
    }

}
