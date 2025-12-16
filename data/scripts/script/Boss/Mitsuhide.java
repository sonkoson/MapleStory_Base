package script.Boss;

import constants.ServerConstants;
import objects.fields.fieldset.childs.*;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

public class Mitsuhide extends ScriptEngineNPC {


    public void mitsuhide_enter() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        String v0 = "미츠히데 쓰러뜨리기 บน해 동할까?\r\n\r\n";
        v0 += "#L0#미츠히데 แผนที่(#b노멀 โหมด#k)으 동 한다.(เลเวล 250상)#l\r\n\r\n\r\n";
        v0 += "#L1#미츠히데 แผนที่(#b하드 โหมด#k)으 동 한다.(เลเวล 250상)#l\r\n\r\n\r\n";
        v0 += "#L5#동하지 않다.#l\r\n\r\n";
        int Menu = target.askMenu(v0, ScriptMessageFlag.BigScenario);
        if (Menu == 5) return; //동하지 않다
        if (target.getParty() == null) {
            self.sayOk("1인 상 ปาร์ตี้ 맺어야만 เข้า할 수 있.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
        if (target.getParty().getLeader().getId() != target.getId()) {
            self.sayOk("ปาร์ตี้장 통해 ดำเนินการ해 สัปดาห์십시오.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
        if (!target.getParty().isPartySameMap()) {
            self.sayOk("ปาร์ตี้원 ทั้งหมด 같แผนที่ 있어야 .", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
        switch (Menu) { //따 จำกัด되거 없으면 바 เข้า능함
            case 0:
            case 1: { //노멀โหมด
                MitsuhideEnter fieldSet = (MitsuhideEnter) fieldSet("MitsuhideEnter");
                int enter = fieldSet.enter(target.getId(), false, Menu == 0, 2);
                if (enter == 6) {
                    self.sayOk("용 능한 인스턴스 없. อื่น แชนแนล 용โปรด.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -5) {
                    self.sayOk("บอส 티어 ไม่พอ한 ปาร์ตี้원 있어 เข้า할 수 없.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -3) {
                    self.sayOk("วันนี้ 미츠히데 쓰러뜨린 ปาร์ตี้원 있. \r\n#r<클리어 기록 ทุกวัน 자정 วัน괄 วินาที기화.>", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if ((enter == -1 || enter == 4)) {
                    self.sayOk("เข้า จำกัด횟수 ไม่พอ하거ฉัน เลเวล จำกัด 맞지 않 ปาร์ตี้원 있어 เข้า할 수 없.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -2) {
                    self.sayOk("เข้า จำกัดเวลา 남 ปาร์ตี้원 있어 เข้า할 수 없.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                break;
            }
        }
    }
}
