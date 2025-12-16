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
            getPlayer().dropMessage(5, "225เลเวล 이상만 เข้า하실 수 있.");
        }
    }

    @Script
    public void _450005015_east00() {
        if (getPlayer().getLevel() >= 225) {
            registerTransferField(450005100, 2);
        } else {
            getPlayer().dropMessage(5, "225เลเวล 이상만 เข้า하실 수 있.");
        }
    }

    @Script
    public void _450005130_east00() {
        if (getPlayer().getLevel() >= 225) {
            registerTransferField(450005000, 1);
        } else {
            getPlayer().dropMessage(5, "225เลเวล 이상만 เข้า하실 수 있.");
        }
    }

    @Script
    public void _450005000_out10() {
        if (getPlayer().getLevel() >= 225) {
            registerTransferField(450005200, 1);
        } else {
            getPlayer().dropMessage(5, "225เลเวล 이상만 เข้า하실 수 있.");
        }
    }

    @Script
    public void _450005300_col00() {
        if (getPlayer().getLevel() >= 225) {
            registerTransferField(450005400, 1);
        } else {
            getPlayer().dropMessage(5, "225เลเวล 이상만 เข้า하실 수 있.");
        }
    }

    @Script
    public void _450005400_pt00() {
        if (getPlayer().getLevel() >= 225) {
            registerTransferField(450005500, 2);
        } else {
            getPlayer().dropMessage(5, "225เลเวล 이상만 เข้า하실 수 있.");
        }
    }

    public void morass_fish() {
        if (getPlayer().getLevel() >= 230) {
            if (1 == target.askAccept("자, 그럼 어서 날치를 타고 ถัดไป พื้นที่으로 ย้าย할까?\r\n#b(수락 시 อัตโนมัติ ย้าย.)", ScriptMessageFlag.Scenario, ScriptMessageFlag.Self, ScriptMessageFlag.FlipImage)) {
                registerTransferField(450006000, 0);
            }
        } else {
            self.sayOk("230เลเวล 이상만 모라스로 ย้าย하실 수 있.", ScriptMessageFlag.NpcReplacedByNpc);
        }
    }

}
