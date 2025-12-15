package script.Boss;

import constants.QuestExConstants;
import database.DBConfig;
import objects.fields.fieldset.childs.*;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;
import scripting.NPCScriptManager;

public class Lucid extends ScriptEngineNPC {

    public void lucid_accept() {
        String Message = "หากไม่หยุด Lucid ไว้ จะต้องเกิดเรื่องน่ากลัวขึ้นแน่ๆ\r\n\r\n";
        if (DBConfig.isGanglim) {
            Message += "#L0#ขอเข้าสู่ <Boss: Lucid (#bNormal Mode#k)> #r(เลเวล 220 ขึ้นไป) #g["
                    + getPlayer().getOneInfoQuestInteger(1234570, "lucid_clear") + "/" + (getPlayer().getBossTier() + 1)
                    + "]#k#l\r\n";
            Message += "#L1#ขอเข้าสู่ <Boss: Lucid (#bHard Mode#k)> #r(เลเวล 220 ขึ้นไป) #g["
                    + getPlayer().getOneInfoQuestInteger(1234569, "lucid_clear") + "/" + (getPlayer().getBossTier() + 1)
                    + "]#k#l\r\n";
            Message += "#L2#ขอเข้าสู่ <Boss: Lucid (#bNormal Practice Mode#k)> #r(เลเวล 220 ขึ้นไป)#l#k\r\n";
            Message += "#L3#ขอเข้าสู่ <Boss: Lucid (#bHard Practice Mode#k)> #r(เลเวล 220 ขึ้นไป)#l#k\r\n";
            Message += "#L4#ขอเข้าสู่ <Boss: Lucid (#rHell Mode#k)> #r(เลเวล 270 ขึ้นไป)#l#k\r\n";
            Message += "#L5#ยกเลิก#l";
        } else {
            boolean single = getPlayer().getPartyMemberSize() == 1;
            Message += "#L0#ขอเข้าสู่ <Boss: Lucid (#bNormal#k)" + (single ? "(Single)" : "(Multi)") + ">#l\r\n";
            Message += "#L1#ขอเข้าสู่ <Boss: Lucid (#bHard#k)" + (single ? "(Single)" : "(Multi)") + ">#l\r\n";
            Message += "#L2#ขอเข้าสู่ <Boss: Lucid (#bNormal#k)" + (single ? "(Single)" : "(Multi)")
                    + "> Practice Mode#l\r\n";
            Message += "#L3#ขอเข้าสู่ <Boss: Lucid (#bHard#k)" + (single ? "(Single)" : "(Multi)")
                    + "> Practice Mode#l\r\n";
            Message += "#L4#ขอเข้าสู่ <Boss: Lucid (#rHell#k)>#l\r\n";
            int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                    "Lucid" + (single ? "Single" : "Multi"));
            Message += "#L6#เพิ่มจำนวนครั้งที่เข้าได้" + (single ? "(Single)" : "(Multi)") + " (#r" + (1 - reset)
                    + " ครั้ง)#l\r\n";
        }
        int Menu = target.askMenu(Message, ScriptMessageFlag.BigScenario);
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
                    "Lucid" + (single ? "Single" : "Multi"));
            if (reset > 0) {
                self.sayOk("สัปดาห์นี้ได้เพิ่มจำนวนครั้งที่เข้าได้ไปแล้ว", ScriptMessageFlag.Scenario,
                        ScriptMessageFlag.NpcReplacedByNpc);
                return;
            }
            getPlayer().gainTogetherPoint(-150);
            getPlayer().updateOneInfo(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                    "Lucid" + (single ? "Single" : "Multi"), String.valueOf(reset + 1));
            self.sayOk("เพิ่มจำนวนครั้งที่เข้าได้เรียบร้อยแล้ว", ScriptMessageFlag.Scenario,
                    ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
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
            case 0: { // Normal Mode
                NormalLucidEnter fieldSet = (NormalLucidEnter) fieldSet("NormalLucidEnter");
                int enter = fieldSet.enter(target.getId(), false, 2);
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
                        self.sayOk("มีสมาชิกในปาร์ตี้ที่ใช้จำนวนครั้งการเข้าของวันนี้หมดแล้ว",
                                ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    } else {
                        self.sayOk(
                                "มีสมาชิกในปาร์ตี้ที่กำจัด Lucid ไปแล้วในสัปดาห์นี้ Lucid สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง (Normal/Hard รวมกัน)\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>",
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
                break;
            }
            case 2: { // Normal Practice Mode
                int practiceMode = self.askYesNo(
                        "คุณได้เลือกเข้าสู่โหมดฝึกซ้อม ในโหมดฝึกซ้อม #b#eจะไม่ได้รับ EXP และรางวัล#n#k และสามารถเข้าได้ #b#eวันละ 20 ครั้ง#n#k เท่านั้น (รวมทุกบอส) ต้องการเข้าสู่โหมดฝึกซ้อมหรือไม่?");
                if (practiceMode == 0) {
                    return;
                }
                NormalLucidEnter fieldSet = (NormalLucidEnter) fieldSet("NormalLucidEnter");
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
                if (getPlayer().getQuestStatus(2000025) == 1) {
                    int Genesis = target.askMenu(
                            "#e<Genesis Weapon>#n\r\nสามารถทำภารกิจเพื่อปลดล็อคความลับของ #bGenesis Weapon#k ที่มีพลังของ Black Mage ได้ จะทำอย่างไร?\r\n\r\n#e#r<เงื่อนไขภารกิจ>#n#b- สมาชิกทุกคนต้องกำลังทำเควสต์ 'Traces of Lucid, Master of Nightmares' และยังไม่ผ่านเงื่อนไขการกำจัด Lucid\r\n- ปาร์ตี้ต้องมีสมาชิกไม่เกิน 2 คน\r\n- ใช้ Challenger Elixir เพียง 50 ขวดในการกำจัด\r\n- ในปาร์ตี้ 2 คน Final Damage จะลดลง 50%, หากมีคนใดคนหนึ่งล้มเหลว ทั้งปาร์ตี้จะล้มเหลว, หากสมาชิกที่มี Death Count เหลืออยู่ตาย จะไม่สามารถลด HP ของ Lucid ให้ต่ำกว่า 1 ได้#n\r\n#k#L0#รับภารกิจ#l\r\n#L1#ไม่รับภารกิจ#l");
                    if (Genesis == 0) { // รับภารกิจ
                        GenesisQuest = true;
                    } else if (Genesis == 1) { // ไม่รับภารกิจ
                        GenesisQuest = false;
                    }
                }
                HardLucidEnter fieldSet = (HardLucidEnter) fieldSet("HardLucidEnter");
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
                    target.sayOk("ภารกิจนี้สามารถท้าทายได้ด้วยปาร์ตี้ไม่เกิน 2 คน");
                    return;
                }
                if (enter == -20) {
                    target.sayOk("สามารถดำเนินการได้เฉพาะสมาชิกที่กำลังทำเควสต์ Genesis Weapon อยู่เท่านั้น");
                    return;
                }
                if (enter == -30) {
                    target.sayOk("ไม่มีช่องว่างสำหรับรับ Challenger Elixir");
                    return;
                }
                if (enter == -3) {
                    if (DBConfig.isGanglim) {
                        self.sayOk("มีสมาชิกในปาร์ตี้ที่ใช้จำนวนครั้งการเข้าของวันนี้หมดแล้ว",
                                ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    } else {
                        self.sayOk(
                                "มีสมาชิกในปาร์ตี้ที่กำจัด Lucid ไปแล้วในสัปดาห์นี้ Lucid สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง (Normal/Hard รวมกัน)\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>",
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
                break;
            }
            case 3: { // Hard Practice Mode
                int practiceMode = self.askYesNo(
                        "คุณได้เลือกเข้าสู่โหมดฝึกซ้อม ในโหมดฝึกซ้อม #b#eจะไม่ได้รับ EXP และรางวัล#n#k และสามารถเข้าได้ #b#eวันละ 20 ครั้ง#n#k เท่านั้น (รวมทุกบอส) ต้องการเข้าสู่โหมดฝึกซ้อมหรือไม่?");
                if (practiceMode == 0) {
                    return;
                }
                HardLucidEnter fieldSet = (HardLucidEnter) fieldSet("HardLucidEnter");
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
                break;
            }
            case 4: { // Hell Mode
                String hellMenu = "";
                if (DBConfig.isGanglim) {
                    hellMenu = "#fs11##e<Lucid Hell Mode>#n\r\nHell Mode จะมีเงื่อนไขดังต่อไปนี้\r\n\r\n#e#r<เงื่อนไขการเข้า>#n#k\r\n#b -กำจัดได้สูงสุด 3 คน\r\n -Final Damage ลดลง 99%, พลังโจมตีของตัวละครลดลง 50%\r\n -HP เพิ่มขึ้น\r\n -รูปแบบการโจมตีรุนแรงขึ้น & ประสิทธิภาพการฟื้นฟู HP ของตัวลัครลดลง 50%\r\n -Death Count 5 ครั้ง (แชร์ทั้งปาร์ตี้)\r\n#rเมื่อกำจัดสำเร็จ มีโอกาส 55% ที่จะได้รับ #b#i4031228# #z4031228##r 1 ชิ้นในช่องเก็บของ\r\n#k#L0#ตกลง#l\r\n#L1#ยกเลิก#l";
                } else {
                    hellMenu = "#e<Lucid Hell Mode>#n\r\nHell Mode จะมีเงื่อนไขดังต่อไปนี้\r\n\r\n#e#r<เงื่อนไขการเข้า>#n#k\r\n#b -กำจัดได้สูงสุด 3 คน\r\n -Final Damage ลดลง 95%\r\n -HP เพิ่มขึ้น\r\n -ประสิทธิภาพการฟื้นฟู HP ลดลง 50%\r\n -รูปแบบการโจมตีรุนแรงขึ้น\r\n -Death Count 5 ครั้ง (แชร์ทั้งปาร์ตี้)\r\n#k#L0#ตกลง#l\r\n#L1#ยกเลิก#l";
                }
                int WelcomeToTheHell = target.askMenu(hellMenu);
                if (WelcomeToTheHell == 1) {
                    return;
                }
                if (getPlayer().getPartyMemberSize() >= 4) {
                    self.sayOk("Hell Mode สามารถเข้าได้สูงสุด 3 คน");
                    return;
                }
                HellLucidEnter fieldSet = (HellLucidEnter) fieldSet("HellLucidEnter");
                int enter = fieldSet.enter(target.getId(), false, DBConfig.isGanglim ? 8 : 2);
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
                if (enter == -30) {
                    target.sayOk("ไม่มีช่องว่างสำหรับรับ Challenger Elixir");
                    return;
                }
                if (enter == -3) {
                    if (DBConfig.isGanglim) {
                        self.sayOk(
                                "มีสมาชิกในปาร์ตี้ที่กำจัด Hell Lucid ไปแล้วในสัปดาห์นี้\r\nHell Lucid สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันจันทร์>",
                                ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    } else {
                        self.sayOk(
                                "มีสมาชิกในปาร์ตี้ที่กำจัด Lucid ไปแล้วในสัปดาห์นี้ Lucid สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง (Normal/Hard รวมกัน)\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>",
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
                break;
            }
        }
    }

    public void midnightChaser_NPC() {
        if (DBConfig.isGanglim) {
            getClient().removeClickedNPC();
            NPCScriptManager.getInstance().start(getClient(), 9010100, "dreamBreaker_NPC", true);
            return;
        }
        lucid_accept();
        return;
    }

    public void dreamBreaker_NPC() {
        if (DBConfig.isGanglim) {
            return;
        }
        lucid_accept();
        return;
    }

    public void west_450004150() {
        if (self.askYesNo("ต้องการหยุดการต่อสู้และออกไปหรือไม่?") == 1) {
            registerTransferField(getPlayer().getMap().getReturnMap().getId());
            if (getPlayer().getEventInstance() != null) {
                getPlayer().getEventInstance().unregisterPlayer(getPlayer());
            }
            if (getPlayer().getBossMode() == 1) {
                getPlayer().setBossMode(0);
            }
        }
    }

    public void out_450004250() {
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

    public void out_450004300() {
        getPlayer().changeMap(450004000, 0);
    }
}
