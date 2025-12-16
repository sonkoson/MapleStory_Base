package script.Boss;

import constants.ServerConstants;
import objects.fields.fieldset.childs.HardBlackHeavenBossEnter;
import objects.fields.fieldset.childs.HellBlackHeavenBossEnter;
import objects.fields.fieldset.childs.NormalBlackHeavenBossEnter;
import objects.fields.fieldset.childs.TenguEnter;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

public class Tengu extends ScriptEngineNPC {

    public void tengu_enter() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        String v0 = "ต้องการย้ายเพื่อไปจัดการ Tengu หรือไม่?\r\n\r\n";
        v0 += "#L0#ย้ายไปยังแผนที่ Tengu (#bโหมด Normal#k) (เลเวล 250 ขึ้นไป)#l\r\n\r\n\r\n";
        v0 += "#L5#ไม่ย้าย#l\r\n\r\n";
        int Menu = target.askMenu(v0, ScriptMessageFlag.BigScenario);
        if (Menu == 5)
            return; // 동하지 않다
        if (target.getParty() == null) {
            self.sayOk("ต้องสร้างปาร์ตี้ 1 คนขึ้นไปจึงจะเข้าได้", ScriptMessageFlag.Scenario,
                    ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
        if (target.getParty().getLeader().getId() != target.getId()) {
            self.sayOk("กรุณาให้หัวหน้าปาร์ตี้เป็นผู้ดำเนินการ", ScriptMessageFlag.Scenario,
                    ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
        if (!target.getParty().isPartySameMap()) {
            self.sayOk("สมาชิกปาร์ตี้ทั้งหมดต้องอยู่ในแผนที่เดียวกัน", ScriptMessageFlag.Scenario,
                    ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
        switch (Menu) { // 따 จำกัด되거 없으면 바 เข้า능함
            case 0: { // 노멀โหมด
                TenguEnter fieldSet = (TenguEnter) fieldSet("TenguEnter");
                int enter = fieldSet.enter(target.getId(), 2);
                if (enter == 6) {
                    self.sayOk("ไม่มี Instance ที่ว่าง กรุณาใช้แชนแนลอื่น", ScriptMessageFlag.Scenario,
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -5) {
                    self.sayOk("มีสมาชิกปาร์ตี้ที่มีระดับ Boss Tier ไม่เพียงพอ จึงไม่สามารถเข้าได้",
                            ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -3) {
                    self.sayOk(
                            "มีสมาชิกปาร์ตี้ที่กำจัด Tengu ไปแล้วในวันนี้ \r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันเวลาเที่ยงคืน>",
                            ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if ((enter == -1 || enter == 4)) {
                    self.sayOk("มีสมาชิกปาร์ตี้ที่จำนวนครั้งการเข้าไม่เพียงพอหรือเลเวลไม่ถึงกำหนด จึงไม่สามารถเข้าได้",
                            ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -2) {
                    self.sayOk("มีสมาชิกปาร์ตี้ที่ยังติดเวลารอเข้าดันเจี้ยนอยู่ จึงไม่สามารถเข้าได้",
                            ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                break;
            }
        }
    }
}
