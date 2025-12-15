package script.Boss;

import constants.QuestExConstants;
import database.DBConfig;
import objects.fields.fieldset.childs.ChaosDuskEnter;
import objects.fields.fieldset.childs.HardGuardianSlimeEnter;
import objects.fields.fieldset.childs.NormalDuskEnter;
import objects.fields.fieldset.childs.NormalGuardianSlimeEnter;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.users.MapleCharacter;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

public class Dusk extends ScriptEngineNPC {

    public void dusk_enter() {
        initNPC(MapleLifeFactory.getNPC(2007));
        String Message = "เราจะปล่อยให้ Gloom สัตว์ประหลาดขนาดยักษ์ที่เกิดจากความคิดของ Black Mage ลอยนวลต่อไปไม่ได้\r\n\r\n";
        if (DBConfig.isGanglim) {
            Message += "#L0#เดินทางไปยัง Eye of the Void (#bNormal Mode#k) #r(เลเวล 245 ขึ้นไป) #g["
                    + getPlayer().getOneInfoQuestInteger(1234590, "dusk_clear") + "/" + (getPlayer().getBossTier() + 1)
                    + "]#k#l\r\n";
            Message += "#L1#เดินทางไปยัง Eye of the Void (#bChaos Mode#k) #r(เลเวล 245 ขึ้นไป) #g["
                    + getPlayer().getOneInfoQuestInteger(1234589, "dusk_clear") + "/" + (getPlayer().getBossTier() + 1)
                    + "]#k#l\r\n";
            Message += "#L2#เดินทางไปยัง Eye of the Void (#bNormal Practice Mode#k) #r(เลเวล 245 ขึ้นไป)#k#l\r\n";
            Message += "#L3#เดินทางไปยัง Eye of the Void (#bChaos Practice Mode#k) #r(เลเวล 245 ขึ้นไป)#k#l\r\n";
            // Message += "#L4#เดินทางไปยัง Eye of the Void (#rHell Mode#k) #r(เลเวล 270
            // ขึ้นไป)#k#l\r\n\r\n\r\n";
            Message += "#L5#ยกเลิก#l\r\n\r\n";
        } else {
            boolean single = getPlayer().getPartyMemberSize() == 1;
            Message += "#L0##bEye of the Void (Normal Mode)" + (single ? "(Single)" : "(Multi)")
                    + " #r(เลเวล 245 ขึ้นไป)#k#l\r\n";
            Message += "#L1##bEye of the Void (Chaos Mode)" + (single ? "(Single)" : "(Multi)")
                    + " #r(เลเวล 245 ขึ้นไป)#k#l\r\n";
            Message += "#L2##bEye of the Void (Normal Practice Mode)" + (single ? "(Single)" : "(Multi)")
                    + " #r(เลเวล 245 ขึ้นไป)#k#l\r\n";
            Message += "#L3##bEye of the Void (Chaos Practice Mode)" + (single ? "(Single)" : "(Multi)")
                    + " #r(เลเวล 245 ขึ้นไป)#k#l\r\n";
            // Message += "#L4##bEye of the Void (#rHell Mode#b) #r(เลเวล 270
            // ขึ้นไป)#k#l\r\n\r\n\r\n";
            int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                    "Dusk" + (single ? "Single" : "Multi"));
            Message += "#L8#เพิ่มจำนวนครั้งที่เข้าได้ " + (single ? "(Single)" : "(Multi)") + "(" + (1 - reset)
                    + " ครั้ง)#l\r\n\r\n\r\n";
            Message += "#L5#ยกเลิก#l\r\n\r\n";
        }

        int Menu = target.askMenu(Message, ScriptMessageFlag.BigScenario);
        if (Menu == 8 && !DBConfig.isGanglim) {
            if (getPlayer().getTogetherPoint() < 150) {
                self.sayOk("Together Point ไม่เพียงพอ คะแนนที่มี : " + getPlayer().getTogetherPoint(),
                        ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                return;
            }
            boolean single = getPlayer().getPartyMemberSize() == 1;
            int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                    "Dusk" + (single ? "Single" : "Multi"));
            if (reset > 0) {
                self.sayOk("สัปดาห์นี้ได้เพิ่มจำนวนครั้งที่เข้าได้ไปแล้ว", ScriptMessageFlag.Scenario,
                        ScriptMessageFlag.NpcReplacedByNpc);
                return;
            }
            getPlayer().gainTogetherPoint(-150);
            getPlayer().updateOneInfo(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                    "Dusk" + (single ? "Single" : "Multi"), String.valueOf(reset + 1));
            self.sayOk("เพิ่มจำนวนครั้งที่เข้าได้เรียบร้อยแล้ว", ScriptMessageFlag.Scenario,
                    ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
        if (Menu == 5)
            return; // ยกเลิก
        if (target.getParty() == null) {
            self.sayOk("ต้องมีปาร์ตี้อย่างน้อย 1 คนจึงจะเข้าได้", ScriptMessageFlag.Scenario,
                    ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
        if (target.getParty().getLeader().getId() != target.getId()) {
            self.sayOk("กรุณาให้หัวหน้าปาร์ตี้เป็นผู้ดำเนินการ", ScriptMessageFlag.Scenario,
                    ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
        if (!target.getParty().isPartySameMap()) {
            self.sayOk("สมาชิกปาร์ตี้ทุกคนต้องอยู่ในแผนที่เดียวกัน", ScriptMessageFlag.Scenario,
                    ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
        switch (Menu) { // ถ้าไม่มีข้อจำกัดอื่นๆ ก็เข้าได้เลย
            case 0: { // Normal Mode
                NormalDuskEnter fieldSet = (NormalDuskEnter) fieldSet("NormalDuskEnter");
                int enter = fieldSet.enter(target.getId(), false, 4);
                if (enter == 6) {
                    self.sayOk("ไม่มี Instance ว่าง กรุณาลองใหม่ในแชนแนลอื่น", ScriptMessageFlag.Scenario,
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -5) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่มีระดับ Boss Tier ไม่ถึงเกณฑ์", ScriptMessageFlag.Scenario,
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -3) {
                    self.sayOk(
                            "มีสมาชิกในปาร์ตี้ที่กำจัด Gloom ไปแล้วในสัปดาห์นี้ Gloom สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง (Normal/Chaos รวมกัน)\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>",
                            ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if ((enter == -1 || enter == 4)) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่มีจำนวนครั้งการเข้าไม่เพียงพอหรือเลเวลไม่ถึงเกณฑ์",
                            ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -2) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่ยังติดเวลารอเข้าดันเจี้ยน", ScriptMessageFlag.Scenario,
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                break;
            }
            case 2: { // Normal Practice Mode
                int practiceMode = self.askYesNo(
                        "คุณได้เลือกเข้าสู่โหมดฝึกซ้อม ในโหมดฝึกซ้อม #b#eจะไม่ได้รับ EXP และรางวัล#n#k และสามารถเข้าได้ #b#eวันละ 20 ครั้ง#n#k เท่านั้น (รวมทุกบอส) ต้องการเข้าสู่โหมดฝึกซ้อมหรือไม่?");
                if (practiceMode == 0) {
                    return;
                }
                NormalDuskEnter fieldSet = (NormalDuskEnter) fieldSet("NormalDuskEnter");
                int enter = fieldSet.enter(target.getId(), true, 4);
                if (enter == 6) {
                    self.sayOk("ไม่มี Instance ว่าง กรุณาลองใหม่ในแชนแนลอื่น", ScriptMessageFlag.Scenario,
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -5) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่มีระดับ Boss Tier ไม่ถึงเกณฑ์", ScriptMessageFlag.Scenario,
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == 4) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่มีเลเวลไม่ถึงเกณฑ์", ScriptMessageFlag.Scenario,
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -1) {
                    self.sayOk("โหมดฝึกซ้อมเข้าได้วันละ 20 ครั้งเท่านั้น", ScriptMessageFlag.Scenario,
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                break;
            }
            case 1: { // Chaos Mode
                ChaosDuskEnter fieldSet = (ChaosDuskEnter) fieldSet("ChaosDuskEnter");
                int enter = fieldSet.enter(target.getId(), false, 6);
                if (enter == 6) {
                    self.sayOk("ไม่มี Instance ว่าง กรุณาลองใหม่ในแชนแนลอื่น", ScriptMessageFlag.Scenario,
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -5) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่มีระดับ Boss Tier ไม่ถึงเกณฑ์", ScriptMessageFlag.Scenario,
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -3) {
                    self.sayOk(
                            "มีสมาชิกในปาร์ตี้ที่กำจัด Gloom ไปแล้วในสัปดาห์นี้ Gloom สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง (Normal/Chaos รวมกัน)\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>",
                            ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if ((enter == -1 || enter == 4)) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่มีจำนวนครั้งการเข้าไม่เพียงพอหรือเลเวลไม่ถึงเกณฑ์",
                            ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -2) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่ยังติดเวลารอเข้าดันเจี้ยน", ScriptMessageFlag.Scenario,
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                break;
            }
            case 3: { // Chaos Practice Mode
                int practiceMode = self.askYesNo(
                        "คุณได้เลือกเข้าสู่โหมดฝึกซ้อม ในโหมดฝึกซ้อม #b#eจะไม่ได้รับ EXP และรางวัล#n#k และสามารถเข้าได้ #b#eวันละ 20 ครั้ง#n#k เท่านั้น (รวมทุกบอส) ต้องการเข้าสู่โหมดฝึกซ้อมหรือไม่?");
                if (practiceMode == 0) {
                    return;
                }
                ChaosDuskEnter fieldSet = (ChaosDuskEnter) fieldSet("ChaosDuskEnter");
                int enter = fieldSet.enter(target.getId(), true, 6);
                if (enter == 6) {
                    self.sayOk("ไม่มี Instance ว่าง กรุณาลองใหม่ในแชนแนลอื่น", ScriptMessageFlag.Scenario,
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -5) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่มีระดับ Boss Tier ไม่ถึงเกณฑ์", ScriptMessageFlag.Scenario,
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if ((enter == 4)) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่มีเลเวลไม่ถึงเกณฑ์", ScriptMessageFlag.Scenario,
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -1) {
                    self.sayOk("โหมดฝึกซ้อมเข้าได้วันละ 20 ครั้งเท่านั้น", ScriptMessageFlag.Scenario,
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                break;
            }
            case 4: { // Hell Mode
                // Reserved
                break;
            }
        }
    }

    public void BM1_bossOut() {
        if (self.askYesNo("ต้องการหยุดการต่อสู้และออกไปหรือไม่?") == 1) {
            registerTransferField(getPlayer().getMap().getReturnMap().getId());
            if (getPlayer().getEventInstance() != null) {
                getPlayer().getEventInstance().unregisterPlayer(getPlayer());
                getPlayer().setEventInstance(null);
                getPlayer().setRegisterTransferFieldTime(0);
                getPlayer().setRegisterTransferField(0);
            }
            if (getPlayer().getBossMode() == 1) {
                getPlayer().setBossMode(0);
            }
        }
    }
}
