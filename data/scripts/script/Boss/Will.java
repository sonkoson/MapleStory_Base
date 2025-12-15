package script.Boss;

import constants.QuestExConstants;
import database.DBConfig;
import objects.fields.fieldset.childs.*;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

public class Will extends ScriptEngineNPC {

    public void will_enterGate() {
        String Message = "ต้องการเดินทางไปยัง #bCorridor of Diffraction#k เพื่อหยุดยั้ง Will หรือไม่?\r\n\r\n";
        if (DBConfig.isGanglim) {
            Message += "#L0#เดินทางไปยัง Corridor of Diffraction (#bNormal Mode#k) #r(เลเวล 235 ขึ้นไป) #g["
                    + getPlayer().getOneInfoQuestInteger(1234570, "will_clear") + "/" + (getPlayer().getBossTier() + 1)
                    + "]#k#l\r\n";
            Message += "#L1#เดินทางไปยัง Corridor of Diffraction (#bHard Mode#k) #r(เลเวล 235 ขึ้นไป) #g["
                    + getPlayer().getOneInfoQuestInteger(1234569, "will_clear") + "/" + (getPlayer().getBossTier() + 1)
                    + "]#k#l\r\n";
            Message += "#L2#เดินทางไปยัง Corridor of Diffraction (#bNormal Practice Mode#k) #r(เลเวล 235 ขึ้นไป)#k#l\r\n";
            Message += "#L3#เดินทางไปยัง Corridor of Diffraction (#bHard Practice Mode#k) #r(เลเวล 235 ขึ้นไป)#k#l\r\n";
            Message += "#L7#เดินทางไปยัง Corridor of Diffraction (#rHell Mode#k) #r(เลเวล 270 ขึ้นไป)#k#l\r\n";
            Message += "#L4#ยกเลิก#l\r\n\r\n";
        } else {
            boolean single = getPlayer().getPartyMemberSize() == 1;
            Message += "#L5##bCorridor of Diffraction (Easy Mode)" + (single ? "(Single)" : "(Multi)")
                    + " #r(เลเวล 235 ขึ้นไป)#k#l\r\n";
            Message += "#L0##bCorridor of Diffraction (Normal Mode)" + (single ? "(Single)" : "(Multi)")
                    + " #r(เลเวล 235 ขึ้นไป)#k#l\r\n";
            Message += "#L1##bCorridor of Diffraction (Hard Mode)" + (single ? "(Single)" : "(Multi)")
                    + " #r(เลเวล 235 ขึ้นไป)#k#l\r\n";
            Message += "#L6##bCorridor of Diffraction (Easy Practice Mode)" + (single ? "(Single)" : "(Multi)")
                    + " #r(เลเวล 235 ขึ้นไป)#k#l\r\n";
            Message += "#L2##bCorridor of Diffraction (Normal Practice Mode)" + (single ? "(Single)" : "(Multi)")
                    + " #r(เลเวล 235 ขึ้นไป)#k#l\r\n";
            Message += "#L3##bCorridor of Diffraction (Hard Practice Mode)" + (single ? "(Single)" : "(Multi)")
                    + " #r(เลเวล 235 ขึ้นไป)#k#l\r\n";
            Message += "#L7##bCorridor of Diffraction (#rHell Mode#b) #r(เลเวล 270 ขึ้นไป)#k#l\r\n";
            int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                    "Will" + (single ? "Single" : "Multi"));
            Message += "#L8#เพิ่มจำนวนครั้งที่เข้าได้ " + (single ? "(Single)" : "(Multi)") + "(" + (1 - reset)
                    + " ครั้ง)#l\r\n\r\n\r\n";
            Message += "#L4#ยกเลิก#l\r\n\r\n";
        }
        int Menu = target.askMenu(Message, ScriptMessageFlag.BigScenario);
        if (Menu == 4)
            return; // ยกเลิก
        if (target.getParty() == null) {
            self.sayOk("ต้องมีปาร์ตี้อย่างน้อย 1 คนจึงจะเข้าได้", ScriptMessageFlag.Scenario,
                    ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
        if (Menu == 8 && !DBConfig.isGanglim) {
            if (getPlayer().getTogetherPoint() < 150) {
                self.sayOk("Together Point ไม่เพียงพอ คะแนนที่มี : " + getPlayer().getTogetherPoint(),
                        ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                return;
            }
            boolean single = getPlayer().getPartyMemberSize() == 1;
            int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                    "Will" + (single ? "Single" : "Multi"));
            if (reset > 0) {
                self.sayOk("สัปดาห์นี้ได้เพิ่มจำนวนครั้งที่เข้าได้ไปแล้ว", ScriptMessageFlag.Scenario,
                        ScriptMessageFlag.NpcReplacedByNpc);
                return;
            }
            getPlayer().gainTogetherPoint(-150);
            getPlayer().updateOneInfo(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                    "Will" + (single ? "Single" : "Multi"), String.valueOf(reset + 1));
            self.sayOk("เพิ่มจำนวนครั้งที่เข้าได้เรียบร้อยแล้ว", ScriptMessageFlag.Scenario,
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
                NormalWillEnter fieldSet = (NormalWillEnter) fieldSet("NormalWillEnter");
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
                            "มีสมาชิกในปาร์ตี้ที่กำจัด Will ไปแล้วในสัปดาห์นี้ Will สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง (Normal/Hard รวมกัน)\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>",
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
                NormalWillEnter fieldSet = (NormalWillEnter) fieldSet("NormalWillEnter");
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
                boolean GenesisQuest = false;
                if (getPlayer().getQuestStatus(2000024) == 1) {
                    int Genesis = target.askMenu(
                            "#e<Genesis Weapon>#n\r\nสามารถทำภารกิจเพื่อปลดล็อคความลับของ #bGenesis Weapon#k ที่มีพลังของ Black Mage ได้ จะทำอย่างไร?\r\n\r\n#e#r<เงื่อนไขภารกิจ>#n#b- กำจัดด้วยสมาชิกปาร์ตี้ไม่เกิน 2 คน ซึ่งทุกคนกำลังทำเควสต์ 'Traces of Will, the King of Spiders' และยังไม่ผ่านเงื่อนไขการกำจัด Will\r\n- ได้รับความเสียหายเมื่อถูกโจมตีเพิ่มขึ้น 10%\r\n- ในปาร์ตี้ 2 คน Final Damage จะลดลง 50%, หากมีคนใดคนหนึ่งล้มเหลว ทั้งปาร์ตี้จะล้มเหลว, หากสมาชิกที่มี Death Count เหลืออยู่ตาย จะไม่สามารถลด HP ของ Will ให้ต่ำกว่า 1 ได้#n\r\n#k#L0#รับภารกิจ#l\r\n#L1#ไม่รับภารกิจ#l");
                    if (Genesis == 0) { // รับภารกิจ
                        GenesisQuest = true;
                    } else if (Genesis == 1) { // ไม่รับภารกิจ
                        GenesisQuest = false;
                    }
                }
                HardWillEnter fieldSet = (HardWillEnter) fieldSet("HardWillEnter");
                int enter = fieldSet.enter(target.getId(), false, GenesisQuest, 5);
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
                            "มีสมาชิกในปาร์ตี้ที่กำจัด Will ไปแล้วในสัปดาห์นี้ Will สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง (Normal/Hard/Hell รวมกัน)\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>",
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
                HardWillEnter fieldSet = (HardWillEnter) fieldSet("HardWillEnter");
                int enter = fieldSet.enter(target.getId(), true, false, 5);
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
            case 5: { // Easy Mode
                EasyWillEnter fieldSet = (EasyWillEnter) fieldSet("EasyWillEnter");
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
                            "มีสมาชิกในปาร์ตี้ที่กำจัด Will ไปแล้วในสัปดาห์นี้ Will สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง (Normal/Hard/Hell รวมกัน)\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>",
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
            case 6: { // Easy Practice Mode
                int practiceMode = self.askYesNo(
                        "คุณได้เลือกเข้าสู่โหมดฝึกซ้อม ในโหมดฝึกซ้อม #b#eจะไม่ได้รับ EXP และรางวัล#n#k และสามารถเข้าได้ #b#eวันละ 20 ครั้ง#n#k เท่านั้น (รวมทุกบอส) ต้องการเข้าสู่โหมดฝึกซ้อมหรือไม่?");
                if (practiceMode == 0) {
                    return;
                }
                EasyWillEnter fieldSet = (EasyWillEnter) fieldSet("EasyWillEnter");
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
            case 7: { // Hell Mode
                String hellMenu = "";
                if (DBConfig.isGanglim) {
                    hellMenu = "#fs11##e<Will Hell Mode>#n\r\nHell Mode จะมีเงื่อนไขดังต่อไปนี้\r\n\r\n#e#r<เงื่อนไขการเข้า>#n#k\r\n#b -กำจัดได้สูงสุด 3 คน\r\n -Final Damage ลดลง 99%, พลังโจมตีของตัวละครลดลง 50%\r\n -HP เพิ่มขึ้น\r\n -Death Count 5 ครั้ง (แชร์ทั้งปาร์ตี้)\r\n#rเมื่อกำจัดสำเร็จ มีโอกาส 55% ที่จะได้รับ #b#i4031228# #z4031228##r 2 ชิ้นในช่องเก็บของ\r\n#k#L0#ตกลง#l\r\n#L1#ยกเลิก#l";
                } else {
                    hellMenu = "#e<Will Hell Mode>#n\r\nHell Mode จะมีเงื่อนไขดังต่อไปนี้\r\n\r\n#e#r<เงื่อนไขการเข้า>#n#k\r\n#b -กำจัดได้สูงสุด 3 คน\r\n -Final Damage ลดลง 95%\r\n -HP เพิ่มขึ้น\r\n -Death Count 5 ครั้ง (แชร์ทั้งปาร์ตี้)\r\n#k#L0#ตกลง#l\r\n#L1#ยกเลิก#l";
                }
                int WelcomeToTheHell = target.askMenu(hellMenu);
                if (WelcomeToTheHell == 1) {
                    return;
                }
                HellWillEnter fieldSet = (HellWillEnter) fieldSet("HellWillEnter");
                int enter = fieldSet.enter(target.getId(), DBConfig.isGanglim ? 8 : 2);
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
                    if (DBConfig.isGanglim) {
                        self.sayOk(
                                "มีสมาชิกในปาร์ตี้ที่กำจัด Hell Will ไปแล้วในสัปดาห์นี้\r\nHell Will สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันจันทร์>",
                                ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    } else {
                        self.sayOk(
                                "มีสมาชิกในปาร์ตี้ที่กำจัด Will ไปแล้วในสัปดาห์นี้ Will สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง (Normal/Hard/Hell รวมกัน)\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>",
                                ScriptMessageFlag.NpcReplacedByNpc);
                    }
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
                if (enter == 3) {
                    self.sayOk("Will (Hell Mode) สามารถเข้าได้สูงสุด 3 คน", ScriptMessageFlag.Scenario,
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                break;
            }
        }
    }

    public void will_out() {
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
