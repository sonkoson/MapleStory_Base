package script.Boss;

import constants.QuestExConstants;
import database.DBConfig;
import objects.fields.fieldset.childs.*;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

public class JinHillah extends ScriptEngineNPC {

    public void JinHillah_enter() {
        Maze3_dungeon();
    }

    // Maze3_dungeon
    // Maze3_dungeon
    public void Maze3_dungeon() {
        initNPC(MapleLifeFactory.getNPC(1402400));
        String Message = "ต้องการเดินทางไปยัง #bAltar of Desire#k เพื่อหยุดยั้ง Verus Hilla หรือไม่?\r\n\r\n";
        if (DBConfig.isGanglim) {
            Message += "#L0#เดินทางไปยัง Altar of Desire (#bHard Mode#k) #r(เลเวล 250 ขึ้นไป) #g["
                    + getPlayer().getOneInfoQuestInteger(1234569, "jinhillah_clear") + "/"
                    + (getPlayer().getBossTier() + 1) + "]#k#l\r\n";
            Message += "#L1#เดินทางไปยัง Altar of Desire (#bHard Practice Mode#k) #r(เลเวล 250 ขึ้นไป)#k#l\r\n\r\n";
            // Message += "#L5#เดินทางไปยัง Altar of Desire (#rHell Mode#k) #r(เลเวล 250
            // ขึ้นไป)#k#l\r\n";
            Message += "#L2#ยกเลิก#l";
        } else {
            boolean single = getPlayer().getPartyMemberSize() == 1;
            Message += "#L3##bAltar of Desire (Normal Mode)" + (single ? "(Single)" : "(Multi)")
                    + " #r(เลเวล 250 ขึ้นไป)#k#l\r\n";
            Message += "#L0##bAltar of Desire (Hard Mode)" + (single ? "(Single)" : "(Multi)")
                    + " #r(เลเวล 250 ขึ้นไป)#k#l\r\n";
            // Message += "#L5##bAltar of Desire (#rHell Mode#b) #r(เลเวล 250
            // ขึ้นไป)#k#l\r\n";
            Message += "#L4##bAltar of Desire (Normal Practice Mode)" + (single ? "(Single)" : "(Multi)")
                    + " #r(เลเวล 250 ขึ้นไป)#k#l\r\n";
            Message += "#L1##bAltar of Desire (Hard Practice Mode)" + (single ? "(Single)" : "(Multi)")
                    + " #r(เลเวล 250 ขึ้นไป)#k#l\r\n";
            int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                    "JinHillah" + (single ? "Single" : "Multi"));
            Message += "#L8#เพิ่มจำนวนครั้งที่เข้าได้ " + (single ? "(Single)" : "(Multi)") + "(" + (1 - reset)
                    + " ครั้ง)#l\r\n\r\n\r\n";
            Message += "#L2#ยกเลิก#l";
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
                    "JinHillah" + (single ? "Single" : "Multi"));
            if (reset > 0) {
                self.sayOk("สัปดาห์นี้ได้เพิ่มจำนวนครั้งที่เข้าได้ไปแล้ว", ScriptMessageFlag.Scenario,
                        ScriptMessageFlag.NpcReplacedByNpc);
                return;
            }
            getPlayer().gainTogetherPoint(-150);
            getPlayer().updateOneInfo(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                    "JinHillah" + (single ? "Single" : "Multi"), String.valueOf(reset + 1));
            self.sayOk("เพิ่มจำนวนครั้งที่เข้าได้เรียบร้อยแล้ว", ScriptMessageFlag.Scenario,
                    ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
        if (Menu == 2)
            return; // ยกเลิก
        if (target.getParty() == null) {
            self.sayOk("ต้องมีปาร์ตี้อย่างน้อย 1 คนจึงจะเข้าได้", ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
        if (target.getParty().getLeader().getId() != target.getId()) {
            self.sayOk("กรุณาให้หัวหน้าปาร์ตี้เป็นผู้ดำเนินการ", ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
        if (!target.getParty().isPartySameMap()) {
            self.sayOk("สมาชิกปาร์ตี้ทุกคนต้องอยู่ในแผนที่เดียวกัน", ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
        switch (Menu) { // ถ้าไม่มีข้อจำกัดอื่นๆ ก็เข้าได้เลย
            case 0: { // Hard Mode
                boolean GenesisQuest = false;
                if (getPlayer().getQuestStatus(2000026) == 1) {
                    int Genesis = target.askMenu(
                            "#e<Genesis Weapon>#n\r\nสามารถทำภารกิจเพื่อปลดล็อคความลับของ #bGenesis Weapon#k ที่มีพลังของ Black Mage ได้ จะทำอย่างไร?\r\n\r\n#e#r<เงื่อนไขภารกิจ>#n#b- กำจัดด้วยสมาชิกปาร์ตี้ไม่เกิน 2 คน ซึ่งทุกคนกำลังทำเควสต์ 'Traces of Verus Hilla, the Red Witch' และยังไม่ผ่านเงื่อนไขการกำจัด Verus Hilla\r\n- ในปาร์ตี้ 2 คน Final Damage จะลดลง 50%, หากมีคนใดคนหนึ่งล้มเหลว ทั้งปาร์ตี้จะล้มเหลว, หากสมาชิกที่มี Death Count เหลืออยู่ตาย จะไม่สามารถลด HP ของ Verus Hilla ให้ต่ำกว่า 1 ได้#n\r\n#k#L0#รับภารกิจ#l\r\n#L1#ไม่รับภารกิจ#l");
                    if (Genesis == 0) { // รับภารกิจ
                        GenesisQuest = true;
                    } else if (Genesis == 1) { // ไม่รับภารกิจ
                        GenesisQuest = false;
                    }
                }
                HardJinHillahEnter fieldSet = (HardJinHillahEnter) fieldSet("HardJinHillahEnter");
                int enter = fieldSet.enter(target.getId(), false, GenesisQuest, 6);
                if (enter == 6) {
                    self.sayOk("ไม่มี Instance ว่าง กรุณาลองใหม่ในแชนแนลอื่น", ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -5) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่มีระดับ Boss Tier ไม่ถึงเกณฑ์", ScriptMessageFlag.Scenario,
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -10) {
                    target.sayOk("ภารกิจนี้สามารถท้าทายได้ด้วยปาร์ตี้ไม่เกิน 2 คน");
                    return;
                }
                if (enter == -20) {
                    target.sayOk("สามารถดำเนินการได้เฉพาะสมาชิกที่กำลังทำเควสต์ Genesis Weapon อยู่เท่านั้น");
                    return;
                }
                if (enter == -3) {
                    self.sayOk(
                            "มีสมาชิกในปาร์ตี้ที่กำจัด Verus Hilla ไปแล้วในสัปดาห์นี้ Verus Hilla สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>",
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if ((enter == -1 || enter == 4)) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่มีจำนวนครั้งการเข้าไม่เพียงพอหรือเลเวลไม่ถึงเกณฑ์",
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -2) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่ยังติดเวลารอเข้าดันเจี้ยน", ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                break;
            }
            case 1: { // Hard Practice Mode
                int practiceMode = self.askYesNo(
                        "คุณได้เลือกเข้าสู่โหมดฝึกซ้อม ในโหมดฝึกซ้อม #b#eจะไม่ได้รับ EXP และรางวัล#n#k และสามารถเข้าได้ #b#eวันละ 20 ครั้ง#n#k เท่านั้น (รวมทุกบอส) ต้องการเข้าสู่โหมดฝึกซ้อมหรือไม่?");
                if (practiceMode == 0) {
                    return;
                }
                HardJinHillahEnter fieldSet = (HardJinHillahEnter) fieldSet("HardJinHillahEnter");
                int enter = fieldSet.enter(target.getId(), true, false, 6);
                if (enter == 6) {
                    self.sayOk("ไม่มี Instance ว่าง กรุณาลองใหม่ในแชนแนลอื่น", ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -5) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่มีระดับ Boss Tier ไม่ถึงเกณฑ์", ScriptMessageFlag.Scenario,
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if ((enter == 4)) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่มีเลเวลไม่ถึงเกณฑ์", ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -1) {
                    self.sayOk("โหมดฝึกซ้อมเข้าได้วันละ 20 ครั้งเท่านั้น", ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                break;
            }

            case 3: { // Normal Mode
                NormalJinHillahEnter fieldSet = (NormalJinHillahEnter) fieldSet("NormalJinHillahEnter");
                int enter = fieldSet.enter(target.getId(), false, 6);
                if (enter == 6) {
                    self.sayOk("ไม่มี Instance ว่าง กรุณาลองใหม่ในแชนแนลอื่น", ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -5) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่มีระดับ Boss Tier ไม่ถึงเกณฑ์", ScriptMessageFlag.Scenario,
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -3) {
                    self.sayOk(
                            "มีสมาชิกในปาร์ตี้ที่กำจัด Verus Hilla ไปแล้วในสัปดาห์นี้ Verus Hilla สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>",
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if ((enter == -1 || enter == 4)) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่มีจำนวนครั้งการเข้าไม่เพียงพอหรือเลเวลไม่ถึงเกณฑ์",
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -2) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่ยังติดเวลารอเข้าดันเจี้ยน", ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                break;
            }
            case 4: { // Normal Practice Mode
                int practiceMode = self.askYesNo(
                        "คุณได้เลือกเข้าสู่โหมดฝึกซ้อม ในโหมดฝึกซ้อม #b#eจะไม่ได้รับ EXP และรางวัล#n#k และสามารถเข้าได้ #b#eวันละ 20 ครั้ง#n#k เท่านั้น (รวมทุกบอส) ต้องการเข้าสู่โหมดฝึกซ้อมหรือไม่?");
                if (practiceMode == 0) {
                    return;
                }
                NormalJinHillahEnter fieldSet = (NormalJinHillahEnter) fieldSet("NormalJinHillahEnter");
                int enter = fieldSet.enter(target.getId(), true, 6);
                if (enter == 6) {
                    self.sayOk("ไม่มี Instance ว่าง กรุณาลองใหม่ในแชนแนลอื่น", ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -5) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่มีระดับ Boss Tier ไม่ถึงเกณฑ์", ScriptMessageFlag.Scenario,
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if ((enter == 4)) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่มีเลเวลไม่ถึงเกณฑ์", ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -1) {
                    self.sayOk("โหมดฝึกซ้อมเข้าได้วันละ 20 ครั้งเท่านั้น", ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
            }
                break;
            case 5: { // Hell Mode
                HellJinHillahEnter fieldSet = (HellJinHillahEnter) fieldSet("HellJinHillahEnter");
                int enter = fieldSet.enter(target.getId(), 6);
                if (enter == 6) {
                    self.sayOk("ไม่มี Instance ว่าง กรุณาลองใหม่ในแชนแนลอื่น", ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -5) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่มีระดับ Boss Tier ไม่ถึงเกณฑ์", ScriptMessageFlag.Scenario,
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -3) {
                    self.sayOk(
                            "มีสมาชิกในปาร์ตี้ที่กำจัด Verus Hilla ไปแล้วในสัปดาห์นี้ Verus Hilla สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>",
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if ((enter == -1 || enter == 4)) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่มีจำนวนครั้งการเข้าไม่เพียงพอหรือเลเวลไม่ถึงเกณฑ์",
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -2) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่ยังติดเวลารอเข้าดันเจี้ยน", ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                break;
            }
        }
    }

    public void jinHillah_out() {
        if (self.askYesNo("ต้องการหยุดการต่อสู้และออกไปหรือไม่?") == 1) {
            getPlayer().setRegisterTransferFieldTime(0);
            getPlayer().setRegisterTransferField(0);
            registerTransferField(getPlayer().getMap().getReturnMap().getId());
            if (getPlayer().getEventInstance() != null) {
                getPlayer().getEventInstance().unregisterPlayer(getPlayer());
                getPlayer().setEventInstance(null);
            }
            if (getPlayer().getBossMode() == 1) {
                getPlayer().setBossMode(0);
            }
        }
    }
}
