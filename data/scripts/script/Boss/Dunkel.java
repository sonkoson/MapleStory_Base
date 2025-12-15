package script.Boss;

import constants.QuestExConstants;
import database.DBConfig;
import objects.fields.fieldset.childs.*;
import objects.fields.fieldset.instance.HellDunkelBoss;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

public class Dunkel extends ScriptEngineNPC {

    public void dunkel_enter() {
        initNPC(MapleLifeFactory.getNPC(2007));
        String Message = "เราต้องหยุดยั้ง Guard Captain Darknell และกองกำลัง Elite Guard ของเขา\r\n\r\n";
        if (DBConfig.isGanglim) {
            Message += "#L0#เดินทางไปยัง Near the End (#bNormal Mode#k) #r(เลเวล 255 ขึ้นไป) #g["
                    + getPlayer().getOneInfoQuestInteger(1234589, "dunkel_clear") + "/"
                    + (getPlayer().getBossTier() + 1) + "]#k#l\r\n";
            Message += "#L1#เดินทางไปยัง Near the End (#bHard Mode#k) #r(เลเวล 255 ขึ้นไป) #g["
                    + getPlayer().getOneInfoQuestInteger(1234569, "dunkel_clear") + "/"
                    + (getPlayer().getBossTier() + 1) + "]#k#l\r\n";
            Message += "#L2#เดินทางไปยัง Near the End (#bNormal Practice Mode#k) #r(เลเวล 255 ขึ้นไป)#k#l\r\n";
            Message += "#L3#เดินทางไปยัง Near the End (#bHard Practice Mode#k) #r(เลเวล 255 ขึ้นไป)#k#l\r\n";
            Message += "#L4#เดินทางไปยัง Near the End (#rHell Mode#k) #r(เลเวล 280 ขึ้นไป)#k#l\r\n";
            Message += "#L5#ยกเลิก#l";
        } else {
            boolean single = getPlayer().getPartyMemberSize() == 1;
            Message += "#L0##bNear the End (Normal Mode)" + (single ? "(Single)" : "(Multi)")
                    + " #r(เลเวล 255 ขึ้นไป)#k#l\r\n";
            Message += "#L1##bNear the End (Hard Mode)" + (single ? "(Single)" : "(Multi)")
                    + " #r(เลเวล 255 ขึ้นไป)#k#l\r\n";
            Message += "#L2##bNear the End (Normal Practice Mode)" + (single ? "(Single)" : "(Multi)")
                    + " #r(เลเวล 255 ขึ้นไป)#k#l\r\n";
            Message += "#L3##bNear the End (Hard Practice Mode)" + (single ? "(Single)" : "(Multi)")
                    + " #r(เลเวล 255 ขึ้นไป)#k#l\r\n\r\n";
            int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                    "Dunkel" + (single ? "Single" : "Multi"));
            Message += "#L8#เพิ่มจำนวนครั้งที่เข้าได้ " + (single ? "(Single)" : "(Multi)") + "(" + (1 - reset)
                    + " ครั้ง)#l\r\n\r\n\r\n";
            Message += "#L5#ยกเลิก#l";
        }

        int Menu = target.askMenu(Message, ScriptMessageFlag.BigScenario);
        if (Menu == 5)
            return; // ยกเลิก
        if (Menu == 8 && !DBConfig.isGanglim) {
            if (getPlayer().getTogetherPoint() < 150) {
                self.sayOk("Together Point ไม่เพียงพอ คะแนนที่มี : " + getPlayer().getTogetherPoint(),
                        ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                return;
            }
            boolean single = getPlayer().getPartyMemberSize() == 1;
            int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                    "Dunkel" + (single ? "Single" : "Multi"));
            if (reset > 0) {
                self.sayOk("สัปดาห์นี้ได้เพิ่มจำนวนครั้งที่เข้าได้ไปแล้ว", ScriptMessageFlag.Scenario,
                        ScriptMessageFlag.NpcReplacedByNpc);
                return;
            }
            getPlayer().gainTogetherPoint(-150);
            getPlayer().updateOneInfo(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                    "Dunkel" + (single ? "Single" : "Multi"), String.valueOf(reset + 1));
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
                NormalDunkelEnter fieldSet = (NormalDunkelEnter) fieldSet("NormalDunkelEnter");
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
                            "มีสมาชิกในปาร์ตี้ที่กำจัด Darknell ไปแล้วในสัปดาห์นี้ Darknell สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง (Normal/Hard รวมกัน)\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>",
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
                NormalDunkelEnter fieldSet = (NormalDunkelEnter) fieldSet("NormalDunkelEnter");
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
            case 1: { // Hard Mode

                HardDunkelEnter fieldSet = (HardDunkelEnter) fieldSet("HardDunkelEnter");
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
                            "มีสมาชิกในปาร์ตี้ที่กำจัด Darknell ไปแล้วในสัปดาห์นี้ Darknell สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง (Normal/Hard รวมกัน)\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>",
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
                HardDunkelEnter fieldSet = (HardDunkelEnter) fieldSet("HardDunkelEnter");
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
                String hellMenu = "";
                if (DBConfig.isGanglim) {
                    hellMenu = "#fs11##e<Darknell Hell Mode>#n\r\nHell Mode จะมีเงื่อนไขดังต่อไปนี้\r\n\r\n#e#r<เงื่อนไขการเข้า>#n#k\r\n#b -กำจัดได้สูงสุด 3 คน\r\n -Final Damage ลดลง 99% , พลังโจมตีของตัวละครลดลง 50%\r\n -HP เพิ่มขึ้น\r\n -รูปแบบการโจมตีโหดขึ้น\r\n -Death Count 5 ครั้ง (แชร์ทั้งปาร์ตี้)\r\n#rเมื่อกำจัดสำเร็จ มีโอกาส 55% ที่จะได้รับ #b#i4031228# #z4031228##r 2 ชิ้นในช่องเก็บของ\r\n#k#L0#เข้าสู่ดันเจี้ยน#l\r\n#L1#ยกเลิก#l";
                } else {
                    hellMenu = "#e<Darknell Hell Mode>#n\r\nHell Mode จะมีเงื่อนไขดังต่อไปนี้\r\n\r\n#e#r<เงื่อนไขการเข้า>#n#k\r\n#b -กำจัดได้สูงสุด 3 คน\r\n -Final Damage ลดลง 95%\r\n -HP เพิ่มขึ้น\r\n -ผลการฟื้นฟู HP 50% มีผล\r\n -รูปแบบการโจมตีโหดขึ้น\r\n -Death Count 5 ครั้ง (แชร์ทั้งปาร์ตี้)\r\n#k#L0#เข้าสู่ดันเจี้ยน#l\r\n#L1#ยกเลิก#l";
                }
                int WelcomeToTheHell = target.askMenu(hellMenu);
                if (WelcomeToTheHell == 1) {
                    return;
                }
                if (getPlayer().getPartyMemberSize() >= 4) {
                    self.sayOk("Hell Mode สามารถเข้าได้สูงสุด 3 คน");
                    return;
                }
                HellDunkelEnter fieldSet = (HellDunkelEnter) fieldSet("HellDunkelEnter");
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
                                "มีสมาชิกในปาร์ตี้ที่กำจัด Hell Darknell ไปแล้วในสัปดาห์นี้\r\nHell Darknell สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันจันทร์>",
                                ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    } else {
                        self.sayOk(
                                "มีสมาชิกในปาร์ตี้ที่กำจัด Hell Darknell ไปแล้วในสัปดาห์นี้ Darknell สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง (Normal/Hard รวมกัน)\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>",
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

    public void GC_out() {
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
