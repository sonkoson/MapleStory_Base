package script.Boss;

import constants.QuestExConstants;
import database.DBConfig;
import network.models.CField;
import objects.fields.child.karrotte.Field_BossKalos;
import objects.fields.fieldset.childs.*;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

public class Kalos extends ScriptEngineNPC {
    public void kalos_enterGate() {
        /*
         * if (!(getPlayer().getClient().isGm() || getPlayer().isGM())) {
         * target.say("지금은 เข้า이 불เป็นไปได้.");
         * return;
         * }
         */
        String Message = "[เข้าได้สูงสุด 3 คน] ต้องการเดินทางไปต่อสู้กับ Kalos the Guardian หรือไม่? (#rเลเวล 500 ขึ้นไป#k สามารถเข้าได้)\r\n\r\n";
        Message += "#L1#เข้าสู่ <Boss: Kalos the Guardian (#bChaos#k)> #g["
                + getPlayer().getOneInfoQuestInteger(1234569, "kalos_clear") + "/" + (getPlayer().getBossTier() + 1)
                + "]#k#l\r\n";
        Message += "#L5#เข้าสู่ <Boss: Kalos the Guardian (#bChaos Practice Mode#k)>#l#k\r\n";
        /*
         * Scripts to use after 380
         * Message += "#L0#<Boss: Kalos the Guardian (#bEasy#k)> ลงทะเบียนการเข้า #g[" +
         * getPlayer().getOneInfoQuestInteger(1234570, "kalos_clear") + "/" +
         * (getPlayer().getBossTier() + 1) + "]#k#l\r\n";
         * Message += "#L1#<Boss: Kalos the Guardian (#bNormal#k)> ลงทะเบียนการเข้า #g["
         * +
         * getPlayer().getOneInfoQuestInteger(1234569, "kalos_clear") + "/" +
         * (getPlayer().getBossTier() + 1) + "]#k#l\r\n";
         * Message += "#L2#<Boss: Kalos the Guardian (#bChaos#k)> ลงทะเบียนการเข้า #g["
         * +
         * getPlayer().getOneInfoQuestInteger(1234569, "kalos_clear") + "/" +
         * (getPlayer().getBossTier() + 1) + "]#k#l\r\n";
         * Message +=
         * "#L3#<Boss: Kalos the Guardian (#bExtreme#k)> ลงทะเบียนการเข้า #g[" +
         * getPlayer().getOneInfoQuestInteger(1234569, "kalos_clear") + "/" +
         * (getPlayer().getBossTier() + 1) + "]#k#l\r\n";
         * Message +=
         * "#L4#<Boss: Kalos the Guardian (#bEasy Practice Mode#k)> ลงทะเบียนการเข้า #l#k\r\n"
         * ;
         * Message +=
         * "#L5#<Boss: Kalos the Guardian (#bNormal Practice Mode#k)> ลงทะเบียนการเข้า #l#k\r\n"
         * ;
         * Message +=
         * "#L6#<Boss: Kalos the Guardian (#bChaos Practice Mode#k)> ลงทะเบียนการเข้า #l#k\r\n"
         * ;
         * Message +=
         * "#L7#<Boss: Kalos the Guardian (#bExtreme Practice Mode#k)> ลงทะเบียนการเข้า #l#k\r\n"
         * ;
         */
        // Message += "#L8#<Boss: Kalos the Guardian (#rHell Mode#k)>
        // ลงทะเบียนการเข้า#l#k\r\n";
        Message += "#L9#ยกเลิก#l";
        int Menu = target.askMenu(Message, ScriptMessageFlag.BigScenario);
        if (Menu < 0 || Menu >= 9) {
            return;
        }
        if (target.getParty() == null) {
            int partyReq = target.askYesNo("ต้องมีปาร์ตี้จึงจะสามารถเข้าได้ ต้องการสร้างปาร์ตี้หรือไม่?");
            if (partyReq != 1) {
                return;
            } else {
                getPlayer().createParty();
            }
        }
        if (target.getParty().getLeader().getId() != target.getId()) {
            self.sayOk("กรุณาให้หัวหน้าปาร์ตี้เป็นผู้ดำเนินการ", ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
        if (!target.getParty().isPartySameMap()) {
            self.sayOk("สมาชิกปาร์ตี้ทุกคนต้องอยู่ในแผนที่เดียวกัน", ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
        if (target.getParty().getPartyMemberList().size() > 3) {
            self.sayOk("สามารถเข้าได้สูงสุด 3 คนต่อปาร์ตี้", ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }

        // Currently Chaos. At 380 Chaos -> Normal changes.
        if (Menu == 1 || Menu == 5) {
            NormalKalosEnter fieldSet = (NormalKalosEnter) fieldSet("NormalKalosEnter");
            int enter = fieldSet.enter(target.getId(), Menu == 5, 7);
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
                        "มีสมาชิกในปาร์ตี้ที่กำจัด Kalos ไปแล้วในสัปดาห์นี้ Kalos สามารถเคลียร์ได้สัปดาห์ละ 1 ครั้ง\r\n#r<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>",
                        ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                /*
                 * if (DBConfig.isGanglim) {
                 * self.sayOk("วันนี้ เข้า 횟수를 ทั้งหมด ใช้한 ปาร์ตี้원이 있.",
                 * ScriptMessageFlag.Scenario,
                 * ScriptMessageFlag.NpcReplacedByNpc);
                 * } else {
                 * self.
                 * sayOk("최근 วันสัปดาห์วัน 이내 칼로스를 쓰러뜨린 ปาร์ตี้원이 있. 칼로스는 วันสัปดาห์วัน에 1회만 클리어 เป็นไปได้\r\n#r<클리어 기록은 매สัปดาห์ 목요วัน에 วัน괄 วินาที기화.>"
                 * , ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                 * }
                 */
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
            // break;
        }
    }

    public void kalos_direction1() {
        setIngameDirectionMode(false, false, false);
        blind(1, 255, 0, 0, 0, 0, 0);
        spineEffect("Effect/Direction20.img/bossKalos/1phase_spine/skeleton", "animation", "intro", 0, 0, 1, 0);
        effectSound("Sound/SoundEff.img/kalos/1phase");
        getPlayer().setRegisterTransferField(getPlayer().getMapId() + 20);
        getPlayer().setRegisterTransferFieldTime(System.currentTimeMillis() + 7000);
        delay(7000);
        environmentChange(31, "intro", 100);
        getOnOffFade(100, "BlackOPut", 0);
    }

    public void kalos_direction1_easy() {
        kalos_direction1();
    }

    public void kalos_direction1_chaos() {
        kalos_direction1();
    }

    public void kalos_direction1_ex() {
        kalos_direction1();
    }

    public void kalos_direction2() {
        setIngameDirectionMode(false, false, false);
        blind(1, 255, 0, 0, 0, 0, 0);
        spineEffect("Effect/Direction20.img/bossKalos/2phase_spine/skeleton", "animation", "intro", 0, 0, 1, 0);
        effectSound("Sound/SoundEff.img/kalos/2phase");
        getPlayer().setRegisterTransferField(getPlayer().getMapId() + 20);
        getPlayer().setRegisterTransferFieldTime(System.currentTimeMillis() + 7000);
        delay(7000);
        environmentChange(31, "intro", 100);
        getOnOffFade(100, "BlackOPut", 0);
    }

    public void kalos_direction2_easy() {
        kalos_direction2();
    }

    public void kalos_direction2_chaos() {
        kalos_direction2();
    }

    public void kalos_direction2_ex() {
        kalos_direction2();
    }

    /*
     * public void ptKalosOut() {
     * if (getPlayer().getMap() instanceof Field_BossKalos) {
     * Field_BossKalos kalosmap = (Field_BossKalos) getPlayer().getMap();
     * if (kalosmap.findBoss() != null) {
     * getPlayer().dropMessage(5,
     * "ในระหว่างการต่อสู้ สามารถคลิกที่พอร์ทัลเพื่อออกได้");
     * return;
     * }
     * }
     * kalosOut();
     * }
     * 
     * public void kalosOut() {
     * initNPC(MapleLifeFactory.getNPC(9091028));
     * if (1 == target.askYesNo("ต้องการหยุดการต่อสู้และออกไปหรือไม่?")) {
     * getPlayer().warp(410005005);
     * }
     * }
     */

}
