package script.Boss;

import constants.GameConstants;
import constants.QuestExConstants;
import constants.ServerConstants;
import database.DBConfig;
import objects.fields.fieldset.FieldSet;
import objects.fields.fieldset.childs.HardBlackHeavenBossEnter;
import objects.fields.fieldset.childs.HellBlackHeavenBossEnter;
import objects.fields.fieldset.childs.NormalBlackHeavenBossEnter;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import scripting.ScriptMessageFlag;
import scripting.newscripting.Script;
import scripting.newscripting.ScriptEngineNPC;

public class Swoo extends ScriptEngineNPC {

    public void blackHeaven_boss() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        String v0 = "ต้องการเดินทางไปยัง Black Heaven Core เพื่อกำจัด Lotus หรือไม่?\r\n\r\n";
        if (DBConfig.isGanglim) {
            v0 += "#L0#เดินทางไปยัง Black Heaven Core (#bNormal Mode#k) #r(เลเวล 230 ขึ้นไป)#k #g["
                    + getPlayer().getOneInfoQuestInteger(1234570, "swoo_clear") + "/" + (getPlayer().getBossTier() + 1)
                    + "]#k#l\r\n";
            v0 += "#L1#เดินทางไปยัง Black Heaven Core (#bHard Mode#k) #r(เลเวล 230 ขึ้นไป)#k #g["
                    + getPlayer().getOneInfoQuestInteger(1234569, "swoo_clear") + "/" + (getPlayer().getBossTier() + 1)
                    + "]#k#l\r\n";
            v0 += "#L2#เดินทางไปยัง Black Heaven Core (#bNormal Practice Mode#k) #r(เลเวล 230 ขึ้นไป)#k#l\r\n";
            v0 += "#L3#เดินทางไปยัง Black Heaven Core (#bHard Practice Mode#k) #r(เลเวล 230 ขึ้นไป)#k#l\r\n";
            v0 += "#L4#เดินทางไปยัง Black Heaven Core (#rHell Mode#k) #r(เลเวล 270 ขึ้นไป)#k#l\r\n";
            v0 += "#L5#ยกเลิก#l\r\n\r\n";
        } else {
            boolean single = getPlayer().getPartyMemberSize() == 1;
            v0 += "#L0#เดินทางไปยัง Black Heaven Core (#bNormal Mode#k)" + (single ? "(Single)" : "(Multi)")
                    + " (เลเวล 230 ขึ้นไป)#l\r\n";
            v0 += "#L1#เดินทางไปยัง Black Heaven Core (#bHard Mode#k)" + (single ? "(Single)" : "(Multi)")
                    + " (เลเวล 230 ขึ้นไป)#l\r\n";
            v0 += "#L2#เดินทางไปยัง Black Heaven Core (#bNormal Practice Mode#k)" + (single ? "(Single)" : "(Multi)")
                    + " (เลเวล 230 ขึ้นไป)#l\r\n";
            v0 += "#L3#เดินทางไปยัง Black Heaven Core (#bHard Practice Mode#k)" + (single ? "(Single)" : "(Multi)")
                    + " (เลเวล 230 ขึ้นไป)#l\r\n";
            v0 += "#L4#เดินทางไปยัง Black Heaven Core (#rHell Mode#k) (เลเวล 270 ขึ้นไป)#l\r\n";
            int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                    "Swoo" + (single ? "Single" : "Multi"));
            v0 += "#L6#เพิ่มจำนวนครั้งที่เข้าได้ " + (single ? "(Single)" : "(Multi)") + "(" + (1 - reset)
                    + " ครั้ง) #l\r\n\r\n";
            v0 += "#L5#ยกเลิก#l\r\n\r\n";
        }
        int Menu = target.askMenu(v0, ScriptMessageFlag.BigScenario);
        if (Menu == 5)
            return; // ยกเลิก
        if (Menu == 6 && !DBConfig.isGanglim) {
            if (getPlayer().getTogetherPoint() < 150) {
                self.sayOk("Together Point ไม่เพียงพอ คะแนนที่มี : " + getPlayer().getTogetherPoint(),
                        ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                return;
            }
            boolean single = getPlayer().getPartyMemberSize() == 1;
            int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                    "Swoo" + (single ? "Single" : "Multi"));
            if (reset > 0) {
                self.sayOk("สัปดาห์นี้ได้เพิ่มจำนวนครั้งที่เข้าได้ไปแล้ว", ScriptMessageFlag.Scenario,
                        ScriptMessageFlag.NpcReplacedByNpc);
                return;
            }
            getPlayer().gainTogetherPoint(-150);
            getPlayer().updateOneInfo(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                    "Swoo" + (single ? "Single" : "Multi"), String.valueOf(reset + 1));
            self.sayOk("เพิ่มจำนวนครั้งที่เข้าได้เรียบร้อยแล้ว", ScriptMessageFlag.Scenario,
                    ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
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
                NormalBlackHeavenBossEnter fieldSet = (NormalBlackHeavenBossEnter) fieldSet(
                        "NormalBlackHeavenBossEnter");
                int enter = fieldSet.enter(target.getId(), false, 0);
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
                            "มีสมาชิกในปาร์ตี้ที่กำจัด Lotus ไปแล้วในสัปดาห์นี้ Lotus สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง (Normal/Hard/Hell รวมกัน)\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>",
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
                NormalBlackHeavenBossEnter fieldSet = (NormalBlackHeavenBossEnter) fieldSet(
                        "NormalBlackHeavenBossEnter");
                int enter = fieldSet.enter(target.getId(), true, 2);
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
                if (getPlayer().getQuestStatus(2000022) == 1) {
                    int Genesis = target.askMenu(
                            "#e<Genesis Weapon>#n\r\nสามารถทำภารกิจเพื่อปลดล็อคความลับของ #bGenesis Weapon#k ที่มีพลังของ Black Mage ได้ จะทำอย่างไร?\r\n\r\n#e#r<เงื่อนไขภารกิจ>#n#k\r\n#b -กำจัดบอสคนเดียว\r\n -Final Damage ลดลง 20%\r\n#k#L0#รับภารกิจ#l\r\n#L1#ไม่รับภารกิจ#l");
                    if (Genesis == 0) { // รับภารกิจ
                        GenesisQuest = true;
                    } else if (Genesis == 1) { // ไม่รับภารกิจ
                        GenesisQuest = false;
                    }
                }
                HardBlackHeavenBossEnter fieldSet = (HardBlackHeavenBossEnter) fieldSet("HardBlackHeavenBossEnter");
                int enter = fieldSet.enter(target.getId(), false, GenesisQuest, 2);
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
                    target.sayOk("ภารกิจนี้ต้องทำคนเดียวเท่านั้น");
                    return;
                }
                if (enter == -3) {
                    self.sayOk(
                            "มีสมาชิกในปาร์ตี้ที่กำจัด Lotus ไปแล้วในสัปดาห์นี้ Lotus สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง (Normal/Hard/Hell รวมกัน)\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>",
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
                HardBlackHeavenBossEnter fieldSet = (HardBlackHeavenBossEnter) fieldSet("HardBlackHeavenBossEnter");
                int enter = fieldSet.enter(target.getId(), true, false, 2);
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
            case 4: { // Hell Mode
                String hellMenu = "";
                if (DBConfig.isGanglim) {
                    hellMenu = "#fs11##e<Lotus Hell Mode>#n\r\nHell Mode จะมีเงื่อนไขดังต่อไปนี้\r\n\r\n#e#r<เงื่อนไขการเข้า>#n#k\r\n#b -กำจัดได้สูงสุด 3 คน\r\n -Final Damage ลดลง 99%, พลังโจมตีของตัวละครลดลง 50%\r\n -HP เพิ่มขึ้น\r\n -รูปแบบการโจมตีรุนแรงขึ้น\r\n -Death Count 3 ครั้ง (แชร์ทั้งปาร์ตี้)\r\n#rเมื่อกำจัดสำเร็จ มีโอกาส 55% ที่จะได้รับ #b#i4031228# #z4031228##r 1 ชิ้นในช่องเก็บของ\r\n#k#L0#ตกลง#l\r\n#L1#ยกเลิก#l";
                } else {
                    hellMenu = "#e<Lotus Hell Mode>#n\r\nHell Mode จะมีเงื่อนไขดังต่อไปนี้\r\n\r\n#e#r<เงื่อนไขการเข้า>#n#k\r\n#b -กำจัดได้สูงสุด 3 คน\r\n -Final Damage ลดลง 95%\r\n -HP เพิ่มขึ้น\r\n -ประสิทธิภาพการฟื้นฟู HP 50% ของปกติ\r\n -รูปแบบการโจมตีรุนแรงขึ้น\r\n -Death Count 5 ครั้ง (แชร์ทั้งปาร์ตี้)\r\n#k#L0#ตกลง#l\r\n#L1#ยกเลิก#l";
                }
                int WelcomeToTheHell = target.askMenu(hellMenu);
                if (WelcomeToTheHell != 0) {
                    return;
                }
                HellBlackHeavenBossEnter fieldSet = (HellBlackHeavenBossEnter) fieldSet("HellBlackHeavenBossEnter");
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
                if (enter == -10) {
                    target.sayOk("ภารกิจนี้สามารถท้าทายได้ด้วยปาร์ตี้ไม่เกิน 3 คน");
                    return;
                }
                if (enter == -3) {
                    if (DBConfig.isGanglim) {
                        self.sayOk(
                                "มีสมาชิกในปาร์ตี้ที่กำจัด Hell Lotus ไปแล้วในสัปดาห์นี้\r\nHell Lotus สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันจันทร์>",
                                ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    } else {
                        self.sayOk(
                                "มีสมาชิกในปาร์ตี้ที่กำจัด Lotus ไปแล้วในสัปดาห์นี้ Lotus สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง (Normal/Hard/Hell รวมกัน)\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>",
                                ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
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
            }
                break;
        }
    }

    public void bh_bossOutN() {
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

    public void bh_bossOut() {
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
