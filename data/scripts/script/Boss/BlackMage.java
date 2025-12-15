package script.Boss;

import database.DBConfig;
import objects.fields.fieldset.childs.HardBlackMageEnter;
import objects.fields.fieldset.childs.HardJinHillahEnter;
import objects.fields.fieldset.childs.NormalJinHillahEnter;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;
import objects.context.party.Party;
import objects.context.party.PartyMemberEntry;
import objects.users.MapleCharacter;

public class BlackMage extends ScriptEngineNPC {

    public void bossBlackMage_pt() {
        initNPC(MapleLifeFactory.getNPC(2007));
        String Message = "ต้องการเดินทางไปยัง #bTemple of Darkness#k เพื่อต่อสู้กับ Black Mage หรือไม่?\r\n\r\n";
        if (DBConfig.isGanglim) {
            Message += "#L0#เดินทางไปยัง Temple of Darkness (#bHard Mode#k) #r(เลเวล 255 ขึ้นไป) #g["
                    + getPlayer().getOneInfoQuestInteger(1234570, "blackmage_clear") + "/"
                    + (getPlayer().getBossTier() + 1) + "]#k#l\r\n";
            Message += "#L1#เดินทางไปยัง Temple of Darkness (#bHard Practice Mode#k) #r(เลเวล 255 ขึ้นไป)#k#l\r\n\r\n";
            Message += "#L2#ยกเลิก#l";
        } else {
            Message += "#L0##bเดินทางไปยัง Temple of Darkness#k#l\r\n";
            Message += "#L1##bเดินทางไปยัง Temple of Darkness (Practice Mode)#k#l\r\n\r\n";
            Message += "#L2#ยกเลิก#l";
        }
        int Menu = target.askMenu(Message, ScriptMessageFlag.BigScenario);
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
                HardBlackMageEnter fieldSet = (HardBlackMageEnter) fieldSet("HardBlackMageEnter");
                int enter = fieldSet.enter(target.getId(), false, 7);
                if (enter == 6) {
                    self.sayOk("ไม่มี Instance ว่าง กรุณาลองใหม่ในแชนแนลอื่น", ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -3) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่มีจำนวนครั้งที่เข้าได้ไม่เพียงพอ",
                            ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -4) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่มีไอเทมสำหรับเข้าไม่เพียงพอ", ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -5) {
                    self.sayOk("มีสมาชิกในปาร์ตี้ที่มีระดับ Boss Tier ไม่ถึงเกณฑ์", ScriptMessageFlag.Scenario,
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
                HardBlackMageEnter fieldSet = (HardBlackMageEnter) fieldSet("HardBlackMageEnter");
                int enter = fieldSet.enter(target.getId(), true, 7);
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
        }
    }

    public void bmbossfield_out() {
        if (this.npc == null) {
            initNPC(MapleLifeFactory.getNPC(3005411));
        }
        if (self.askYesNo("ต้องการหยุดการต่อสู้และออกไปหรือไม่?") == 1) {
            registerTransferField(450012500, 1);
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
