package script.Boss;

import constants.QuestExConstants;
import database.DBConfig;
import objects.fields.fieldset.childs.*;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;
import scripting.NPCScriptManager;

public class Karing extends ScriptEngineNPC {

    public void karing_enterGate() {
        if (!(getPlayer().getClient().isGm() || getPlayer().isGM())) {
            target.say("ไม่สามารถเข้าได้ในขณะนี้");
            return;
        }
        String Message = "ต้องการเดินทางไปต่อสู้กับ Kaling หรือไม่? (#rเลเวล 275 ขึ้นไป#k สามารถเข้าได้).\r\n\r\n";
        if (DBConfig.isGanglim) {
            Message += "#L0#เข้าสู่ <Boss: Kaling (#bNormal Mode#k)> #r(เลเวล 220 ขึ้นไป) #g["
                    + getPlayer().getOneInfoQuestInteger(1234570, "Karing_clear") + "/"
                    + (getPlayer().getBossTier() + 1) + "]#k#l\r\n";
            Message += "#L1#เข้าสู่ <Boss: Kaling (#bHard Mode#k)> #r(เลเวล 220 ขึ้นไป) #g["
                    + getPlayer().getOneInfoQuestInteger(1234569, "Karing_clear") + "/"
                    + (getPlayer().getBossTier() + 1) + "]#k#l\r\n";
            Message += "#L2#เข้าสู่ <Boss: Kaling (#bNormal Practice Mode#k)> #r(เลเวล 220 ขึ้นไป)#l#k\r\n";
            Message += "#L3#เข้าสู่ <Boss: Kaling (#bHard Practice Mode#k)> #r(เลเวล 220 ขึ้นไป)#l#k\r\n";
            Message += "#L4#เข้าสู่ <Boss: Kaling (#rHell Mode#k)> #r(เลเวล 270 ขึ้นไป)#l#k\r\n";
            Message += "#L5#ยกเลิก#l";
        } else {
            boolean single = getPlayer().getPartyMemberSize() == 1;
            Message += "#L0#เข้าสู่ <Boss: Kaling (#bNormal#k)" + (single ? "(Single)" : "(Multi)") + ">#l\r\n";
            Message += "#L1#เข้าสู่ <Boss: Kaling (#bHard#k)" + (single ? "(Single)" : "(Multi)") + ">#l\r\n";
            Message += "#L2#เข้าสู่ <Boss: Kaling (#bNormal#k)" + (single ? "(Single)" : "(Multi)")
                    + "> (Practice Mode)#l\r\n";
            Message += "#L3#เข้าสู่ <Boss: Kaling (#bHard#k)" + (single ? "(Single)" : "(Multi)")
                    + "> (Practice Mode)#l\r\n";
            Message += "#L4#เข้าสู่ <Boss: Kaling (#rHell#k)>#l\r\n";
            int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                    "Karing" + (single ? "Single" : "Multi"));
            Message += "#L6#เพิ่มจำนวนครั้งที่เข้าได้ " + (single ? "(Single)" : "(Multi)") + " (#r" + (1 - reset)
                    + " ครั้ง#k) #l\r\n";
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
                    "Karing" + (single ? "Single" : "Multi"));
            if (reset > 0) {
                self.sayOk("สัปดาห์นี้ได้เพิ่มจำนวนครั้งที่เข้าได้ไปแล้ว", ScriptMessageFlag.Scenario,
                        ScriptMessageFlag.NpcReplacedByNpc);
                return;
            }
            getPlayer().gainTogetherPoint(-150);
            getPlayer().updateOneInfo(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                    "Karing" + (single ? "Single" : "Multi"), String.valueOf(reset + 1));
            self.sayOk("เพิ่มจำนวนครั้งที่เข้าได้เรียบร้อยแล้ว", ScriptMessageFlag.Scenario,
                    ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
        if (target.getParty() == null) {
            int partyReq = target.askYesNo("ต้องมีปาร์ตี้จึงจะสามารถเข้าได้ ต้องการสร้างปาร์ตี้หรือไม่?");
            if (partyReq != 1) {
                return;
            } else {
                getPlayer().createParty();
            }
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
                NormalKaringEnter fieldSet = (NormalKaringEnter) fieldSet("NormalKaringEnter");
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
                        self.sayOk("มีสมาชิกในปาร์ตี้ที่ใช้จำนวนครั้งการเข้าหมดแล้วในวันนี้",
                                ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    } else {
                        self.sayOk(
                                "มีสมาชิกในปาร์ตี้ที่กำจัด Kaling ไปแล้วในสัปดาห์นี้ Kaling สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง (Normal/Hard รวมกัน)\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>",
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
                NormalKaringEnter fieldSet = (NormalKaringEnter) fieldSet("NormalKaringEnter");
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
                HardKaringEnter fieldSet = (HardKaringEnter) fieldSet("HardKaringEnter");
                int enter = fieldSet.enter(target.getId(), 2);
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
                    target.sayOk("ไม่มีพื้นที่ว่างสำหรับรับ Challenger Elixir");
                    return;
                }
                if (enter == -3) {
                    if (DBConfig.isGanglim) {
                        self.sayOk("มีสมาชิกในปาร์ตี้ที่ใช้จำนวนครั้งการเข้าหมดแล้วในวันนี้",
                                ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    } else {
                        self.sayOk(
                                "มีสมาชิกในปาร์ตี้ที่กำจัด Kaling ไปแล้วในสัปดาห์นี้ Kaling สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง (Normal/Hard รวมกัน)\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>",
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
                HardKaringEnter fieldSet = (HardKaringEnter) fieldSet("HardKaringEnter");
                int enter = fieldSet.enter(target.getId(), 2);
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
                    hellMenu = "#fs11##e<Kaling Hell Mode>#n\r\nHell Mode จะดำเนินการภายใต้เงื่อนไขดังต่อไปนี้\r\n\r\n#e#r<เงื่อนไขการเข้า>#n#k\r\n#b - กำจัดด้วยสมาชิกปาร์ตี้สูงสุด 3 คน\r\n - Final Damage ลดลง 99%, ค่าสถานะตัวละครจะถูกปรับเป็น 50%\r\n - HP เพิ่มขึ้น\r\n - รูปแบบการโจมตีโหดขึ้น & ผลการฟื้นฟู HP ตัวละคร 50%\r\n - Death Count 5 ครั้ง (แชร์ทั้งปาร์ตี้)\r\n#rเมื่อกำจัดสำเร็จ มีโอกาส 50% ที่จะได้รับ #b#i4031228# #z4031228##r 1 ชิ้นในช่องเก็บของ\r\n#k#L0#เข้าสู่ดันเจี้ยน#l\r\n#L1#ไม่เข้าสู่ดันเจี้ยน#l";
                } else {
                    hellMenu = "#e<Kaling Hell Mode>#n\r\nHell Mode จะดำเนินการภายใต้เงื่อนไขดังต่อไปนี้\r\n\r\n#e#r<เงื่อนไขการเข้า>#n#k\r\n#b - กำจัดด้วยสมาชิกปาร์ตี้สูงสุด 3 คน\r\n - Final Damage ลดลง 95%\r\n - HP เพิ่มขึ้น\r\n - ผลการฟื้นฟู HP 50% ถูกปรับใช้\r\n - รูปแบบการโจมตีโหดขึ้น\r\n - Death Count 5 ครั้ง (แชร์ทั้งปาร์ตี้)\r\n#k#L0#เข้าสู่ดันเจี้ยน#l\r\n#L1#ไม่เข้าสู่ดันเจี้ยน#l";
                }
                int WelcomeToTheHell = target.askMenu(hellMenu);
                if (WelcomeToTheHell == 1) {
                    return;
                }
                if (getPlayer().getPartyMemberSize() >= 4) {
                    self.sayOk("Hell Mode สามารถเข้าได้สูงสุด 3 คน");
                    return;
                }
                ExtremeKaringEnter fieldSet = (ExtremeKaringEnter) fieldSet("ExtremeKaringEnter");
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
                    target.sayOk("ไม่มีพื้นที่ว่างสำหรับรับ Challenger Elixir");
                    return;
                }
                if (enter == -3) {
                    if (DBConfig.isGanglim) {
                        self.sayOk(
                                "มีสมาชิกในปาร์ตี้ที่กำจัด Hell Kaling ไปแล้วในสัปดาห์นี้\r\nHell Kaling สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันจันทร์>",
                                ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    } else {
                        self.sayOk(
                                "มีสมาชิกในปาร์ตี้ที่กำจัด Kaling ไปแล้วในสัปดาห์นี้ Kaling สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง (Normal/Hard รวมกัน)\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>",
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
        karing_enterGate();
        return;
    }

    public void dreamBreaker_NPC() {
        if (DBConfig.isGanglim) {
            return;
        }
        karing_enterGate();
        return;
    }

    public void west_450004150() {
        if (self.askYesNo("ต้องการออกจากการต่อสู้ใช่หรือไม่?") == 1) {
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
        if (self.askYesNo("ต้องการออกจากการต่อสู้ใช่หรือไม่?") == 1) {
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
