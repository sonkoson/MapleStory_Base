package script.Boss;

import constants.QuestExConstants;
import database.DBConfig;
import objects.fields.fieldset.FieldSet;
import objects.fields.fieldset.childs.*;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import scripting.ScriptMessageFlag;
import scripting.newscripting.Script;
import scripting.newscripting.ScriptEngineNPC;

public class GuardianAngelSlime extends ScriptEngineNPC {

    public void slime_enterGate() {
        String Message = "สัมผัสได้ถึงพลังงานที่ไม่รู้จัก ต้องการย้ายไปต่อสู้กับ Guardian Angel Slime หรือไม่?\r\n\r\n";
        if (DBConfig.isGanglim) {
            Message += "#L0#ขอเข้าสู่ Guardian Angel Slime (#bNormal Mode#k) #r(เลเวล 210 ขึ้นไป) #g["
                    + getPlayer().getOneInfoQuestInteger(1234570, "guardian_angel_slime_clear") + "/"
                    + (getPlayer().getBossTier() + 1) + "]#k#l\r\n";
            Message += "#L1#ขอเข้าสู่ Guardian Angel Slime (#bChaos Mode#k) #r(เลเวล 210 ขึ้นไป) #g["
                    + getPlayer().getOneInfoQuestInteger(1234569, "guardian_angel_slime_clear") + "/"
                    + (getPlayer().getBossTier() + 1) + "]#k#l\r\n";
            Message += "#L2#ขอเข้าสู่ Guardian Angel Slime (#bNormal Practice Mode#k) #r(เลเวล 210 ขึ้นไป)#k#l\r\n";
            Message += "#L3#ขอเข้าสู่ Guardian Angel Slime (#bChaos Practice Mode#k) #r(เลเวล 210 ขึ้นไป)#k#l\r\n\r\n";
            Message += "#L4#ไม่ย้ายไป#l";
        } else {
            boolean single = getPlayer().getPartyMemberSize() == 1;
            Message += "#L0##bGuardian Angel Slime (Normal Mode)" + (single ? "(Single)" : "(Multi)")
                    + " #r(เลเวล 210 ขึ้นไป)#k#l\r\n";
            Message += "#L1##bGuardian Angel Slime (Chaos Mode)" + (single ? "(Single)" : "(Multi)")
                    + " #r(เลเวล 210 ขึ้นไป)#k#l\r\n";
            Message += "#L2##bGuardian Angel Slime (Normal Practice Mode)" + (single ? "(Single)" : "(Multi)")
                    + " #r(เลเวล 210 ขึ้นไป)#k#l\r\n";
            Message += "#L3##bGuardian Angel Slime (Chaos Practice Mode)" + (single ? "(Single)" : "(Multi)")
                    + " #r(เลเวล 210 ขึ้นไป)#k#l\r\n";
            int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                    "GuardianSlime" + (single ? "Single" : "Multi"));
            Message += "#L5#เพิ่มจำนวนครั้งที่เข้าได้ " + (single ? "(Single)" : "(Multi)") + "(" + (1 - reset)
                    + " ครั้ง)#l\r\n\r\n";
            Message += "#L4#ไม่ย้ายไป#l";
        }
        int Menu = target.askMenu(Message, ScriptMessageFlag.BigScenario);
        if (Menu == 4)
            return; // ไม่ย้ายไป
        if (Menu == 5 && !DBConfig.isGanglim) {
            if (getPlayer().getTogetherPoint() < 150) {
                self.sayOk("Together Point ไม่เพียงพอ คะแนนที่มี : " + getPlayer().getTogetherPoint(),
                        ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                return;
            }
            boolean single = getPlayer().getPartyMemberSize() == 1;
            int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                    "GuardianSlime" + (single ? "Single" : "Multi"));
            if (reset > 0) {
                self.sayOk("สัปดาห์นี้ได้เพิ่มจำนวนครั้งที่เข้าได้ไปแล้ว", ScriptMessageFlag.Scenario,
                        ScriptMessageFlag.NpcReplacedByNpc);
                return;
            }
            getPlayer().gainTogetherPoint(-150);
            getPlayer().updateOneInfo(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                    "GuardianSlime" + (single ? "Single" : "Multi"), String.valueOf(reset + 1));
            self.sayOk("เพิ่มจำนวนครั้งที่เข้าได้เรียบร้อยแล้ว", ScriptMessageFlag.Scenario,
                    ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
        // if (DBConfig.isGanglim) {
        // self.sayOk("บอสที่กำลังปิดปรับปรุงอยู่ในปัจจุบัน",
        // ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
        // return;
        // }
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
                NormalGuardianSlimeEnter fieldSet = (NormalGuardianSlimeEnter) fieldSet("NormalGuardianSlimeEnter");
                int enter = fieldSet.enter(target.getId(), false, 3);
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
                            "มีสมาชิกในปาร์ตี้ที่กำจัด Guardian Angel Slime ไปแล้วในสัปดาห์นี้ Guardian Angel Slime สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง (Normal/Chaos รวมกัน)\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>",
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
                NormalGuardianSlimeEnter fieldSet = (NormalGuardianSlimeEnter) fieldSet("NormalGuardianSlimeEnter");
                int enter = fieldSet.enter(target.getId(), true, 3);
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
            case 1: { // Hard Mode

                HardGuardianSlimeEnter fieldSet = (HardGuardianSlimeEnter) fieldSet("HardGuardianSlimeEnter");
                int enter = fieldSet.enter(target.getId(), false, 5);
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
                            "มีสมาชิกในปาร์ตี้ที่กำจัด Guardian Angel Slime ไปแล้วในสัปดาห์นี้ Guardian Angel Slime สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง (Normal/Chaos รวมกัน)\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>",
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
            case 3: { // Hard Practice Mode
                int practiceMode = self.askYesNo(
                        "คุณได้เลือกเข้าสู่โหมดฝึกซ้อม ในโหมดฝึกซ้อม #b#eจะไม่ได้รับ EXP และรางวัล#n#k และสามารถเข้าได้ #b#eวันละ 20 ครั้ง#n#k เท่านั้น (รวมทุกบอส) ต้องการเข้าสู่โหมดฝึกซ้อมหรือไม่?");
                if (practiceMode == 0) {
                    return;
                }
                HardGuardianSlimeEnter fieldSet = (HardGuardianSlimeEnter) fieldSet("HardGuardianSlimeEnter");
                int enter = fieldSet.enter(target.getId(), true, 5);
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
            }
                break;
        }
    }

    public void slimeOut() {
        initNPC(MapleLifeFactory.getNPC(9091025));
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
