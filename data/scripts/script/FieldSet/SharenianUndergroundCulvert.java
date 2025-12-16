package script.FieldSet;

import network.models.CField;
import objects.fields.fieldset.childs.CulvertEnter;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import scripting.ScriptMessageFlag;
import scripting.newscripting.Script;
import scripting.newscripting.ScriptEngineNPC;

import objects.users.stats.SecondaryStatFlag;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SharenianUndergroundCulvert extends ScriptEngineNPC {


    public void Culvert_NPC() {
        int v = self.askMenu("#fs11#샤레니안의 악마, 아르카누스에게 도전ต้องการหรือไม่?\r\n\r\n#L1# #b샤레니안의 지하 수로에 เข้า한다.#k#l\r\n#L2# #b샤레니안의 지하 수로는 어떤 공간인가요?#k#l", ScriptMessageFlag.NpcReplacedByNpc);
        switch (v) {
            case 1: //샤레니안의 지하 수로에 เข้า한다.

                registerTransferField(941000000);
                break;
            case 2: //샤레니안의 지하 수로는 어떤 공간인가요?
                self.say("#fs11##r#e샤레니안의 지하 수로#k#n 대해 궁금하신가요?", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("#fs11#아สัปดาห์ 먼 옛날... 샤레니안이라는 고대의 왕국이 있었. 비록 끔찍한 사건으로 인해 왕국이 통째로 멸망 말았지만요.", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("#fs11#샤레니안의 유적지를 탐험하려는 시도는 여러 번 있었지만 쉽지 않았고, 결국 그곳으로 향하는 길은 잊히고 말았.\r\n\r\n#r#e얼마 전까지만 해도 말이지요...", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("#fs11#전 ก่อนหน้า부터 샤레니안의 유적지를 조사 중이었는데 사고로 인해 아무도 가지 않았던 곳에서 #b샤레니안의 지하 수로로 통하는 길을 발견แล้ว.#k", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("#fs11#มาก 많은 입구들이 미로처럼 얽혀있지만 어느 길로 가도 불길한 기운이 잠들어 있는 제단이 있는 곳으로 갈 수 있었지요.", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("#fs11#กิลด์ 본부는 지하 수로 조사를 결정แล้ว. 지하 수로 입구에서 저에게 말을 걸어서 샤레니안의 지하 수로로 통하는 문을 열면, 불길한 기운의 제단으로 향하는 통로로 갈 수 있.", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("#fs11#조사는 혼자서만 ดำเนินการ할 수  중간에 กิลด์ 탈퇴하거나 추방 던전 คะแนน ได้รับ할 수 없. 또한, กิลด์ 탈퇴하거나 추방당 해당 กิลด์ 총 คะแนน หัก되니 สัปดาห์의하시길 바랍니다.", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("#fs11#조사는 จำกัด 없이 ดำเนินการ할 수 있. 단, 지하 수로의 불길한 기운으로 인해 เข้า 시 ทั้งหมด Buff เอฟเฟกต์가 ปลดล็อก되므로 신중하게 결정하시길 바랍니다.", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("#fs11#제단으로 향하는 통로에서는 잠시 동안 Buff สกิล ใช้ ไอเท็ม ใช้할 수 있으니 이후 올 시련에 대비하실 수 있.\r\n단, วัน정 เวลา 내 제단으로 เข้า하지 못 지하 수로에서 ออก하게 되니 สัปดาห์의하십시오.", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("#fs11#수로의 가장 깊은 곳에 자리한 불길한 기운의 제단에서 숨죽인 채 때를 기다리던 존재가 곧 깨어날 겁니다. 바로 \r\n\r\n                   #r샤레니안의 악마, 아르카누스#k\r\n\r\n.", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("#fs11#그는 불멸의 존재... โจมตี을 받을수록 점점 더 강한 존재가  태어나니 조심해야 . 가장 강력한 สถานะ로 재탄생 아무리 강한 โจมตี을 받더라도 그는 결코 쓰러지는 모습을 보이지 않을 것이니 สัปดาห์의를 기울이는 것이 좋.\r\n또한 아르카누스의 โจมตี에 의해 사망한다면 곧바로 제단 밖으로 ย้าย되니 สัปดาห์의하시길 바랍니다.", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("#fs11#지하 수로의 존재들에게 입힌 피해에 비례해 던전 คะแนน 얻을 수  เดือน요วัน 0시마다 เป้าหมาย 달성에 따라 กิลด์ 본부에서 포상으로 노블레스 SP 지급하니 힘내서 도전해보십시오. 다만 กิลด์ 총 던전 คะแนน วัน정 점수 이상이 야 받을 수 있을 겁니다.", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("#fs11#또, คะแนน 순위에 따라 เพิ่ม 노블레스 SP 얻을 수도  지하 수로에서 ออก วัน정 เวลา이 지나야 던전 คะแนน 정산되니 참고하십시오.\r\n#rแต่ละ กิลด์원의 던전 คะแนน 합산에 วัน정 เวลา이 소요 랭킹에 모두 반영 되기까지 어느정도 เวลา이 จำเป็น하오니 คะแนน น้อย 기록도 당황하지 말고 잠시 후 다시 ยืนยัน해 보십시오.", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("#fs11##b#e1위 : 25개의 SP\r\n2위 : 23개의 SP\r\n3위 : 21개의 SP\r\n4위 ~ 10위 : 20개의 SP\r\n상위 10% : 19개의 SP\r\n상위 30% : 17개의 SP\r\n상위 60% : 15개의 SP\r\n상위 80% : 10개의 SP\r\n500คะแนน 이상 : 5개의 SP\r\n\r\n#r#e※ 단, 500คะแนน 이상 ได้รับ하지 못 순위 안에 들었더라도 รางวัล을 받을 수 없.#k#n", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("#fs11#제가 함께 수로 속으로 갈 테니 길을 잃을 염려는 하지 않으셔도 . 어서 도전하시지요.", ScriptMessageFlag.NpcReplacedByNpc);
                break;
        }
    }

    public void npc_2012041() {
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd/HH/mm");
        Date lastTime = null;
        try {
            lastTime = sdf.parse(getPlayer().getOneInfo(100811, "date"));
        } catch (Exception e) {
            //e.printStackTrace();
            lastTime = null;
        }
        if (lastTime != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(lastTime);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            cal.set(Calendar.HOUR, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.DATE, cal.getTime().getDate() + 7);
            if (cal.getTime().getTime() <= new Date().getTime()) {
                getPlayer().updateOneInfo(100811, "point", "0");
                getPlayer().updateOneInfo(100811, "complete", "0");
                getPlayer().updateOneInfo(100811, "date", sdf.format(new Date()));
                getPlayer().updateOneInfo(100811, "guildPoint", "0");
                getPlayer().updateOneInfo(100811, "weeklyRecord", "0");
            }
        }

        switch (getPlayer().getMapId()) {
            case 941000100: {
                if (1 == self.askYesNo("#fs11#เตรียม가 เสร็จสมบูรณ์되셨다면 แนะนำ해드리겠.\r\n\r\n#b#e지금 제단으로 ย้ายต้องการหรือไม่?#n#k", ScriptMessageFlag.NpcReplacedByNpc)) {
                    registerTransferField(941000200);
                } else {
                    self.sayOk("#fs11#그렇다면 정비를 마친 후 다시 시도โปรด.", ScriptMessageFlag.NpcReplacedByNpc);
                }
                break;
            }
            case 941000001: {
                if (getPlayer().getOneInfoQuestInteger(100811, "complete") > 0) {
                    int point = getPlayer().getOneInfoQuestInteger(100811, "point");
                    int weeklyRecord = getPlayer().getOneInfoQuestInteger(100811, "weeklyRecord");
                    int v = -1;
                    if (point > weeklyRecord) {
                        v = self.askMenu("#fs11#제의 불길한 기운이 นิดหน่อย은 줄어든 것 같.\r\n다시 지하 수로 입구로 แนะนำ해드리겠.\r\n\r\n#e- ได้รับ 점수: #n#b#e" + point + "점#n#k#e#b (이번 สัปดาห์ 신기록!)#n#k\r\n#e- 이번 สัปดาห์ 최고 점수: #n#r#e" + point + "점#n#k\r\n#b#L1# 지하 수로 입구로 돌아간다.#k#l\r\n#b#L2# สนทนา를 สิ้นสุด한다.#k#l", ScriptMessageFlag.NpcReplacedByNpc);
                    } else {
                        v = self.askMenu("#fs11#제의 불길한 기운이 นิดหน่อย은 줄어든 것 같.\r\n다시 지하 수로 입구로 แนะนำ해드리겠.\r\n\r\n#e- ได้รับ 점수: #n#b#e" + point + "점#n#k\r\n#e- 이번 สัปดาห์ 최고 점수: #n#r#e" + weeklyRecord + "점#n#k\r\n#b#L1# 지하 수로 입구로 돌아간다.#k#l\r\n#b#L2# สนทนา를 สิ้นสุด한다.#k#l", ScriptMessageFlag.NpcReplacedByNpc);
                    }
                    switch (v) {
                        case 1:
                            if (point > weeklyRecord) {
                                getPlayer().updateOneInfo(100811, "weeklyRecord", String.valueOf(point));
                            }
                            registerTransferField(941000000);
                            break;
                    }
                } else {
                    int v = self.askMenu("#fs11#\r\n아르카누스의 힘이 전혀 줄어들지 않았군요. 다시 지하 수로 입구로 แนะนำ해드리겠.\r\n\r\n#b#L1# 지하 수로 입구로 돌아간다.#k#l\r\n#b#L2# สนทนา를 สิ้นสุด한다.#k#l", ScriptMessageFlag.NpcReplacedByNpc);
                    switch (v) {
                        case 1:
                            registerTransferField(941000000);
                            break;
                    }
                }
                break;
            }
            default: {
                int weeklyRecord = getPlayer().getOneInfoQuestInteger(100811, "weeklyRecord");
                int v = self.askMenu("#fs11#\r\n유적지가 불길한 기운을 내뿜고 있. 지금 지하 수로를 조사ต้องการหรือไม่?\r\n\r\n#e- 이번 สัปดาห์ 최고 점수: #n#r#e" + weeklyRecord + "점#n#k\r\n#L1# #b샤레니안의 지하 수로에 เข้า한다.#k#l\r\n#L2# #b샤레니안의 지하 수로는 어떤 공간인가요?#k#l", ScriptMessageFlag.NpcReplacedByNpc);
                switch (v) {
                    case 1: {
                        if (1 == self.askAccept("지하 수로에 เข้า 시 지금 ใช้งาน 있는\r\n#fs16##b#eทั้งหมด Buff เอฟเฟกต์가 ปลดล็อก#k#fs12##n.\r\n\r\n지금 도전ต้องการหรือไม่?")) {
                            CulvertEnter fieldSet = (CulvertEnter) fieldSet("CulvertEnter");
                            int enter = fieldSet.enter(target.getId(), 0);
                            if (enter == -1) self.say("#fs11#알 수 없는 이유로 เข้า할 수 없. 잠시 후에 다시 시도해 สัปดาห์십시오.");
                            else if (enter == 1) self.sayOk("#fs11#<샤레니안의 지하수로> 혼자서만 도전할 수 있.\r\nปาร์ตี้ ปลดล็อก 다시 찾아와 สัปดาห์길 바랍니다.");
                            else if (enter == 2) self.say("#fs11#เลเวล ต่ำสุด " + fieldSet.minLv + " 이상이어야 .");
                            else if (enter == 3) self.say("#fs11#ปัจจุบัน ทั้งหมด 인스턴스가 가득차 도전할 수 없. แชนแนล ย้ายโปรด.");
                            else if (enter == -2) self.say("#fs11#วัน요วัน 오후 23시 ~ เดือน요วัน 오전 01시 사이에는 노블레스 คะแนน 정산เวลา으로 이용ไม่สามารถทำได้.");
                        } else {
                            self.sayOk("#fs11#알겠. 조사할 เตรียม가 되시면 다시 시도โปรด.",   ScriptMessageFlag.NpcReplacedByNpc);
                        }
                        break;
                    }
                    case 2: { //샤레니안의 지하 수로는 어떤 공간인가요?
                        self.say("#fs11##r#e샤레니안의 지하 수로#k#n 대해 궁금하신가요?");
                        self.say("#fs11#아สัปดาห์ 먼 옛날... 샤레니안이라는 고대의 왕국이 있었. 비록 끔찍한 사건으로 인해 왕국이 통째로 멸망 말았지만요.");
                        self.say("#fs11#샤레니안의 유적지를 탐험하려는 시도는 여러 번 있었지만 쉽지 않았고, 결국 그곳으로 향하는 길은 잊히고 말았.\r\n\r\n#r#e얼마 전까지만 해도 말이지요...");
                        self.say("#fs11#전 ก่อนหน้า부터 샤레니안의 유적지를 조사 중이었는데 사고로 인해 아무도 가지 않았던 곳에서 #b샤레니안의 지하 수로로 통하는 길을 발견แล้ว.#k");
                        self.say("#fs11#มาก 많은 입구들이 미로처럼 얽혀있지만 어느 길로 가도 불길한 기운이 잠들어 있는 제단이 있는 곳으로 갈 수 있었지요.");
                        self.say("#fs11#กิลด์ 본부는 지하 수로 조사를 결정แล้ว. 지하 수로 입구에서 저에게 말을 걸어서 샤레니안의 지하 수로로 통하는 문을 열면, 불길한 기운의 제단으로 향하는 통로로 갈 수 있.");
                        self.say("#fs11#조사는 혼자서만 ดำเนินการ할 수  중간에 กิลด์ 탈퇴하거나 추방 던전 คะแนน ได้รับ할 수 없. 또한, กิลด์ 탈퇴하거나 추방당 해당 กิลด์ 총 คะแนน หัก되니 สัปดาห์의하시길 바랍니다.");
                        self.say("#fs11#조사는 จำกัด 없이 ดำเนินการ할 수 있. 단, 지하 수로의 불길한 기운으로 인해 เข้า 시 ทั้งหมด Buff เอฟเฟกต์가 ปลดล็อก되므로 신중하게 결정하시길 바랍니다.");
                        self.say("#fs11#제단으로 향하는 통로에서는 잠시 동안 Buff สกิล ใช้ ไอเท็ม ใช้할 수 있으니 이후 올 시련에 대비하실 수 있.\r\n단, วัน정 เวลา 내 제단으로 เข้า하지 못 지하 수로에서 ออก하게 되니 สัปดาห์의하십시오.");
                        self.say("#fs11#수로의 가장 깊은 곳에 자리한 불길한 기운의 제단에서 숨죽인 채 때를 기다리던 존재가 곧 깨어날 겁니다. 바로 \r\n\r\n                   #r샤레니안의 악마, 아르카누스#k\r\n\r\n.");
                        self.say("#fs11#그는 불멸의 존재... โจมตี을 받을수록 점점 더 강한 존재가  태어나니 조심해야 . 가장 강력한 สถานะ로 재탄생 아무리 강한 โจมตี을 받더라도 그는 결코 쓰러지는 모습을 보이지 않을 것이니 สัปดาห์의를 기울이는 것이 좋.\r\n또한 아르카누스의 โจมตี에 의해 사망한다면 곧바로 제단 밖으로 ย้าย되니 สัปดาห์의하시길 바랍니다.");
                        self.say("#fs11#지하 수로의 존재들에게 입힌 피해에 비례해 던전 คะแนน 얻을 수  เดือน요วัน 0시마다 เป้าหมาย 달성에 따라 กิลด์ 본부에서 포상으로 노블레스 SP 지급하니 힘내서 도전해보십시오. 다만 กิลด์ 총 던전 คะแนน วัน정 점수 이상이 야 받을 수 있을 겁니다.");
                        self.say("#fs11#또, คะแนน 순위에 따라 เพิ่ม 노블레스 SP 얻을 수도  지하 수로에서 ออก วัน정 เวลา이 지나야 던전 คะแนน 정산되니 참고하십시오.\r\n#rแต่ละ กิลด์원의 던전 คะแนน 합산에 วัน정 เวลา이 소요 랭킹에 모두 반영 되기까지 어느정도 เวลา이 จำเป็น하오니 คะแนน น้อย 기록도 당황하지 말고 잠시 후 다시 ยืนยัน해 보십시오.");
                        self.say("#fs11##b#e1위 : 25개의 SP\r\n2위 : 23개의 SP\r\n3위 : 21개의 SP\r\n4위 ~ 10위 : 20개의 SP\r\n상위 10% : 19개의 SP\r\n상위 30% : 17개의 SP\r\n상위 60% : 15개의 SP\r\n상위 80% : 10개의 SP\r\n500คะแนน 이상 : 5개의 SP\r\n\r\n#r#e※ 단, 500คะแนน 이상 ได้รับ하지 못 순위 안에 들었더라도 รางวัล을 받을 수 없.#k#n");
                        self.say("#fs11#제가 함께 수로 속으로 갈 테니 길을 잃을 염려는 하지 않으셔도 . 어서 도전하시지요.");
                        break;
                    }
                }
                break;
            }
        }
    }

    @Script
    public void gb_reset() {
        int duration = 0;
        if(getPlayer().getCooldownLimit(80002282) != 0L){ // 봉인된 룬의 힘 ปลดล็อก 악용 방지
            duration = (int) getPlayer().getRemainCooltime(80002282);
        }
        getPlayer().cancelAllBuffs();
        getPlayer().send(CField.addPopupSay(2012041, 1300, "제단의 불길한 기운으로 인해\r\n#rทั้งหมด Buff ปลดล็อก#k되었.", ""));
        getPlayer().send(CField.addPopupSay(2012041, 1300, "#r샤레니안의 악마#k와의 결전을 위해 정비하세요!", ""));
        if(duration != 0){
            getPlayer().temporaryStatSet(80002282, duration, SecondaryStatFlag.RuneBlocked, 1);
        }
    }

    public void gb_out() {
        initNPC(MapleLifeFactory.getNPC(2012041));
        if (1 == self.askYesNo("#fs11#도전을 포기 지하 수로 입구로 돌아가시겠습니까?", ScriptMessageFlag.NpcReplacedByNpc)) {
            registerTransferField(941000001);
        }
    }


}
