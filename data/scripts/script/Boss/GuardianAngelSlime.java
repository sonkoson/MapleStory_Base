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
        String Message = "알 수 없는 기운이 느껴진다. 가디언 엔젤 슬라임과의 전투를 위해 ย้าย할까?\r\n\r\n";
        if (DBConfig.isGanglim) {
        	Message += "#L0#가디언 엔젤 슬라임(#b노말 โหมด#k) เข้า을 สมัคร한다. #r(เลเวล 210이상) #g[" + getPlayer().getOneInfoQuestInteger(1234570, "guardian_angel_slime_clear") + "/" + (getPlayer().getBossTier() + 1) + "]#k#l\r\n";
            Message += "#L1#가디언 엔젤 슬라임(#b카오스 โหมด#k) เข้า을 สมัคร한다. #r(เลเวล 210이상) #g[" + getPlayer().getOneInfoQuestInteger(1234569, "guardian_angel_slime_clear") + "/" + (getPlayer().getBossTier() + 1) + "]#k#l\r\n";
            Message += "#L2#가디언 엔젤 슬라임(#b노말 연습 โหมด#k) เข้า을 สมัคร한다. #r(เลเวล 210이상)#k#l\r\n";
            Message += "#L3#가디언 엔젤 슬라임(#b카오스 연습 โหมด#k) เข้า을 สมัคร한다. #r(เลเวล 210이상)#k#l\r\n\r\n";
            Message += "#L4#ย้าย하지 않는다.#l";
        }
        else {
        	boolean single = getPlayer().getPartyMemberSize() == 1;
        	Message += "#L0##b가디언 엔젤 슬라임과의 전투(노멀 โหมด)" + (single ? "(싱글)" : "(멀티)") + " 위해 ย้าย한다.#r(เลเวล 210이상)#k#l\r\n";
            Message += "#L1##b가디언 엔젤 슬라임과의 전투(카오스 โหมด)" + (single ? "(싱글)" : "(멀티)") + " 위해 ย้าย한다.#r(เลเวล 210이상)#k#l\r\n";
            Message += "#L2##b가디언 엔젤 슬라임과의 전투(노멀 연습 โหมด)" + (single ? "(싱글)" : "(멀티)") + " 위해 ย้าย한다.#r(เลเวล 210이상)#k#l\r\n";
            Message += "#L3##b가디언 엔젤 슬라임과의 전투(카오스 연습 โหมด)" + (single ? "(싱글)" : "(멀티)") + " 위해 ย้าย한다.#r(เลเวล 210이상)#k#l\r\n";
            int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.WeeklyQuestResetCount.getQuestID(), "GuardianSlime" + (single ? "Single" : "Multi"));
            Message += "#L5#เข้า횟수 เพิ่ม " + (single ? "(싱글)" : "(멀티)") + "(" + (1-reset) + "회 เป็นไปได้)#l\r\n\r\n";
            Message += "#L4#ย้าย하지 않는다.#l";
        }
        int Menu = target.askMenu(Message, ScriptMessageFlag.BigScenario);
        if (Menu == 4) return; //ย้าย하지 않는다
        if (Menu == 5 && !DBConfig.isGanglim) {
        	if (getPlayer().getTogetherPoint() < 150) {
        		self.sayOk("협동 คะแนน ไม่พอ. มี 협동คะแนน : " + getPlayer().getTogetherPoint(), ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
        		return;
        	}
        	boolean single = getPlayer().getPartyMemberSize() == 1;
        	int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.WeeklyQuestResetCount.getQuestID(), "GuardianSlime" + (single ? "Single" : "Multi"));
        	if (reset > 0) {
        		self.sayOk("이번 สัปดาห์에는 이미 เข้าเป็นไปได้ 횟수를 เพิ่ม시켰.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
        		return;
        	}
        	getPlayer().gainTogetherPoint(-150);
        	getPlayer().updateOneInfo(QuestExConstants.WeeklyQuestResetCount.getQuestID(), "GuardianSlime" + (single ? "Single" : "Multi"), String.valueOf(reset + 1));
        	self.sayOk("เข้าเป็นไปได้ 횟수가 เพิ่ม되었.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
        	return;
        }
//        if (DBConfig.isGanglim) {
//            self.sayOk("ปัจจุบัน 점검중인 บอส.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
//            return;
//        }
        if (target.getParty() == null) {
            self.sayOk("1인 이상 ปาร์ตี้ 맺어야만 เข้า할 수 있.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
        if (target.getParty().getLeader().getId() != target.getId()) {
            self.sayOk("ปาร์ตี้장을 통해 ดำเนินการ해 สัปดาห์십시오.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
        if (!target.getParty().isPartySameMap()) {
            self.sayOk("ปาร์ตี้원이 모두 같은แผนที่ 있어야 .", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
        switch (Menu) { //따로 จำกัด되는거 없으면 바로 เข้าเป็นไปได้함
            case 0: { //노멀โหมด
                NormalGuardianSlimeEnter fieldSet = (NormalGuardianSlimeEnter) fieldSet("NormalGuardianSlimeEnter");
                int enter = fieldSet.enter(target.getId(), false, 3);
                if (enter == 6) {
                    self.sayOk("이용 เป็นไปได้한 인스턴스가 없. 다른 แชนแนล 이용โปรด.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -5) {
                    self.sayOk("บอส 티어가 ไม่พอ한 ปาร์ตี้원이 있어 เข้า할 수 없.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -3) {
                    self.sayOk("최근 วันสัปดาห์วัน 이내 가디언 엔젤 슬라임을 쓰러뜨린 ปาร์ตี้원이 있. 가디언 엔젤 슬라임은 노멀 โหมด, 카오스 โหมด를 합쳐 วันสัปดาห์วัน에 1회만 클리어 เป็นไปได้\r\n#r<클리어 기록은 매สัปดาห์ 목요วัน에 วัน괄 วินาที기화.>", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if ((enter == -1 || enter == 4)) {
                    self.sayOk("เข้า จำกัด횟수가 ไม่พอ하거나 เลเวล จำกัด이 맞지 않는 ปาร์ตี้원이 있어 เข้า할 수 없.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -2) {
                    self.sayOk("เข้า จำกัดเวลา이 남은 ปาร์ตี้원이 있어 เข้า할 수 없.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                break;
            }
            case 2: { //노멀 연습 โหมด
                int practiceMode = self.askYesNo("연습 โหมด에 เข้า을 เลือก하였. 연습 โหมด에서는 #b#eEXP รางวัล을 얻을 수 ไม่มี#n#k บอส มอนสเตอร์ 종류와 상관없이 #b#e하루 20회#n#k 이용할 수 있. เข้าต้องการหรือไม่?");
                if (practiceMode == 0) {
                    return;
                }
                NormalGuardianSlimeEnter fieldSet = (NormalGuardianSlimeEnter) fieldSet("NormalGuardianSlimeEnter");
                int enter = fieldSet.enter(target.getId(), true, 3);
                if (enter == 6) {
                    self.sayOk("이용 เป็นไปได้한 인스턴스가 없. 다른 แชนแนล 이용โปรด.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -5) {
                    self.sayOk("บอส 티어가 ไม่พอ한 ปาร์ตี้원이 있어 เข้า할 수 없.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == 4) {
                    self.sayOk("เลเวล จำกัด이 맞지 않는 ปาร์ตี้원이 있어 เข้า할 수 없.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -1) {
                    self.sayOk("연습 โหมด는 하루 20회만 เป็นไปได้.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                break;
            }
            case 1: { //하드โหมด

                HardGuardianSlimeEnter fieldSet = (HardGuardianSlimeEnter) fieldSet("HardGuardianSlimeEnter");
                int enter = fieldSet.enter(target.getId(), false, 5);
                if (enter == 6) {
                    self.sayOk("이용 เป็นไปได้한 인스턴스가 없. 다른 แชนแนล 이용โปรด.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -5) {
                    self.sayOk("บอส 티어가 ไม่พอ한 ปาร์ตี้원이 있어 เข้า할 수 없.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -3) {
                    self.sayOk("최근 วันสัปดาห์วัน 이내 가디언 엔젤 슬라임을 쓰러뜨린 ปาร์ตี้원이 있. 가디언 엔젤 슬라임은 노멀 โหมด, 카오스 โหมด를 합쳐 วันสัปดาห์วัน에 1회만 클리어 เป็นไปได้\r\n#r<클리어 기록은 매สัปดาห์ 목요วัน에 วัน괄 วินาที기화.>", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if ((enter == -1 || enter == 4)) {
                    self.sayOk("เข้า จำกัด횟수가 ไม่พอ하거나 เลเวล จำกัด이 맞지 않는 ปาร์ตี้원이 있어 เข้า할 수 없.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -2) {
                    self.sayOk("เข้า จำกัดเวลา이 남은 ปาร์ตี้원이 있어 เข้า할 수 없.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                break;
            }
            case 3: { //하드 연습 โหมด
                int practiceMode = self.askYesNo("연습 โหมด에 เข้า을 เลือก하였. 연습 โหมด에서는 #b#eEXP รางวัล을 얻을 수 ไม่มี#n#k บอส มอนสเตอร์ 종류와 상관없이 #b#e하루 20회#n#k 이용할 수 있. เข้าต้องการหรือไม่?");
                if (practiceMode == 0) {
                    return;
                }
                HardGuardianSlimeEnter fieldSet = (HardGuardianSlimeEnter) fieldSet("HardGuardianSlimeEnter");
                int enter = fieldSet.enter(target.getId(), true, 5);
                if (enter == 6) {
                    self.sayOk("이용 เป็นไปได้한 인스턴스가 없. 다른 แชนแนล 이용โปรด.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -5) {
                    self.sayOk("บอส 티어가 ไม่พอ한 ปาร์ตี้원이 있어 เข้า할 수 없.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if ((enter == 4)) {
                    self.sayOk("เลเวล จำกัด이 맞지 않는 ปาร์ตี้원이 있어 เข้า할 수 없.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                if (enter == -1) {
                    self.sayOk("연습 โหมด는 하루 20회만 เป็นไปได้.", ScriptMessageFlag.Scenario, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
            }
            break;
        }
    }


    public void slimeOut() {
        initNPC(MapleLifeFactory.getNPC(9091025));
        if (self.askYesNo("전투를 หยุด 나가시겠습니까?") == 1) {
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
