package script.FieldSet;

import constants.GameConstants;
import constants.ServerConstants;
import database.DBConfig;
import network.models.CField;
import network.models.CWvsContext;
import objects.fields.Field;
import objects.fields.fieldset.FieldSet;
import objects.users.MapleCharacter;
import objects.users.stats.SecondaryStatFlag;
import objects.utils.Timer;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class TangyoonKitchen extends ScriptEngineNPC {


    /**
     * qexID : 501555
     * start=1;date=21/07/04;today=0;dodge=1
     * time=210704175444105;start=1;date=21/07/04;today=0;dodge=1
     */

    public void npc_9062550() {
        /**
         * 도전เป็นไปได้한 횟수จำกัด은 없으나 EXP 하루에 2번까지 얻을 수 있음
         * เข้า시 DispelItemOptionByField Buff 걸림
         * */
        FieldSet fieldSet = fieldSet("TangyoonKitchenEnter");
        if (fieldSet == null) {
            self.sayOk("지금은 탕윤 식당을 이용ไม่สามารถทำได้~! ถัดไป에 찾아와สัปดาห์세요!", ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
        Date lastTime = null;
        Date lastTimePuzzleMaster = null;
        Date now = null;
        try {
            lastTime = sdf.parse(getPlayer().getOneInfo(501555, "date"));
        } catch (Exception e) {
            lastTime = null;
        }
        try {
            lastTimePuzzleMaster = sdf.parse(getPlayer().getOneInfo(501600, "date"));
        } catch (Exception e) {
            lastTimePuzzleMaster = null;
        }
        try {
            now = sdf.parse(sdf.format(new Date()));
        } catch (Exception e) {
            now = null;
        }
        if ((lastTime != null && !lastTime.equals(now)) || lastTime == null) {
            getPlayer().updateOneInfo(501555, "start", "0");
            getPlayer().updateOneInfo(501555, "date", sdf.format(new Date()));
            getPlayer().updateOneInfo(501555, "today", "0");
        }
        if ((lastTimePuzzleMaster != null && !lastTimePuzzleMaster.equals(now)) || lastTimePuzzleMaster == null) {
            getPlayer().updateOneInfo(501600, "start", "0");
            getPlayer().updateOneInfo(501600, "date", sdf.format(new Date()));
            getPlayer().updateOneInfo(501600, "today", "0");
        }
        getPlayer().updateOneInfo(501555, "complete", "0");
        getPlayer().updateOneInfo(501600, "complete", "0");
        int v = self.askMenu("#b#e메이플 버라이어티#n#k 제 1탄~! #r#e탕.윤.식.당!#l#k\r\n#L2# #b#e<탕윤 식당>#n ปาร์ตี้플레이(1~3인으로 도전)#l\r\n#L5# #b#e<퍼즐 마스터>#n ปาร์ตี้ 플레이(1~3인으로 도전)#l#k\r\n\r\n#L3# #b#e<탕윤 식당>#n อธิบาย 듣기#l\r\n#L6# #b#e<퍼즐 마스터>#n อธิบาย 듣기#l#k\r\n#L4# วันนี้ 남은 EXP รางวัล ได้รับ เป็นไปได้ 횟수 ยืนยัน<탕윤식당>#l\r\n#L7# วันนี้ 남은 EXP รางวัล ได้รับ เป็นไปได้ 횟수 ยืนยัน<퍼즐>\r\n\r\n#L100# 더 이상 궁금한 것이 없어.#l", ScriptMessageFlag.NpcReplacedByNpc);
        switch (v) {
            case 2: { //ปาร์ตี้로도전(탕윤식당)
                if (1 == self.askYesNo("\r\n지금 #b#e<탕윤 식당>#n#k เข้าร่วมต้องการหรือไม่~?#k\r\n\r\n(게임 중에는 해상도가 1366x768 เปลี่ยน.)", ScriptMessageFlag.NpcReplacedByNpc)) {
                    if (1 == self.askYesNo("\r\n#b#e<탕윤 식당>#n#k เข้าร่วม하시면 ใช้งาน 있는\r\n#fs16##r#eทั้งหมด Buff เอฟเฟกต์가 ปลดล็อก#n#k#fs12#된답니다.\r\n\r\n정말 เข้าร่วมต้องการหรือไม่?", ScriptMessageFlag.NpcReplacedByNpc)) {
                        int enter = fieldSet.enter(target.getId(), 0);
                        if (enter == -1)
                            self.say("알 수 없는 이유로 เข้า할 수 없. 잠시 후에 다시 시도해 สัปดาห์십시오.", ScriptMessageFlag.NpcReplacedByNpc);
                        else if (enter == 1)
                            self.say("1~3인 ปาร์ตี้ 맺어야만 #b#eปาร์ตี้ 플레이#n#k 할 수 있답니다.", ScriptMessageFlag.NpcReplacedByNpc);
                        else if (enter == 2)
                            self.say("ปาร์ตี้장을 통해서만 เข้า할 수 있답니다! ปาร์ตี้장을 통해 เข้าโปรด~!", ScriptMessageFlag.NpcReplacedByNpc);
                        else if (enter == 3)
                            self.say("ต่ำสุด " + fieldSet.minMember + "인 이상의 ปาร์ตี้ เควส เริ่ม할 수 있답니다.", ScriptMessageFlag.NpcReplacedByNpc);
                        else if (enter == 4)
                            self.say("ปาร์ตี้원의 เลเวล ต่ำสุด " + fieldSet.minLv + " 이상이어야 .", ScriptMessageFlag.NpcReplacedByNpc);
                        else if (enter == 5) self.say("ปาร์ตี้원이 모두 모여 있어야 เริ่ม할 수 있.", ScriptMessageFlag.NpcReplacedByNpc);
                        else if (enter == 6)
                            self.say("이미 다른 ปาร์ตี้ 안으로 들어가 เควส 클리어에 도전 있는 중이랍니다.", ScriptMessageFlag.NpcReplacedByNpc);
                    } else {
                        self.say("\r\n그러면 방송에 เข้าร่วม 싶을 때 다시 찾아와สัปดาห์세요~", ScriptMessageFlag.NpcReplacedByNpc);
                    }
                } else {
                    self.say("\r\n그러면 방송에 เข้าร่วม 싶을 때 다시 찾아와สัปดาห์세요~", ScriptMessageFlag.NpcReplacedByNpc);
                }
                break;
            }
            case 5: { //ปาร์ตี้로도전(퍼즐마스터)
                if (1 == self.askYesNo("\r\n지금 #b#e<퍼즐 마스터>#n#k เข้าร่วมต้องการหรือไม่~?#k", ScriptMessageFlag.NpcReplacedByNpc)) {
                    if (1 == self.askYesNo("\r\n#b#e<퍼즐 마스터>#n#k เข้าร่วม하시면 ใช้งาน 있는\r\n#fs16##r#eทั้งหมด Buff เอฟเฟกต์가 ปลดล็อก#n#k#fs12#된답니다.\r\n\r\n정말 เข้าร่วมต้องการหรือไม่?", ScriptMessageFlag.NpcReplacedByNpc)) {
                        FieldSet puzzleMaster = fieldSet("PuzzleMasterEnter");
                        if (puzzleMaster == null) {
                            self.sayOk("지금은 퍼즐 마스터를 이용ไม่สามารถทำได้~! ถัดไป에 찾아와สัปดาห์세요!", ScriptMessageFlag.NpcReplacedByNpc);
                            return;
                        }
                        int enter = puzzleMaster.enter(target.getId(), 0);
                        if (enter == -1)
                            self.say("알 수 없는 이유로 เข้า할 수 없. 잠시 후에 다시 시도해 สัปดาห์십시오.", ScriptMessageFlag.NpcReplacedByNpc);
                        else if (enter == 1)
                            self.say("1~3인 ปาร์ตี้ 맺어야만 #b#eปาร์ตี้ 플레이#n#k 할 수 있답니다.", ScriptMessageFlag.NpcReplacedByNpc);
                        else if (enter == 2)
                            self.say("ปาร์ตี้장을 통해서만 เข้า할 수 있답니다! ปาร์ตี้장을 통해 เข้าโปรด~!", ScriptMessageFlag.NpcReplacedByNpc);
                        else if (enter == 3)
                            self.say("ต่ำสุด " + fieldSet.minMember + "인 이상의 ปาร์ตี้ เควส เริ่ม할 수 있답니다.", ScriptMessageFlag.NpcReplacedByNpc);
                        else if (enter == 4)
                            self.say("ปาร์ตี้원의 เลเวล ต่ำสุด " + fieldSet.minLv + " 이상이어야 .", ScriptMessageFlag.NpcReplacedByNpc);
                        else if (enter == 5) self.say("ปาร์ตี้원이 모두 모여 있어야 เริ่ม할 수 있.", ScriptMessageFlag.NpcReplacedByNpc);
                        else if (enter == 6)
                            self.say("이미 다른 ปาร์ตี้ 안으로 들어가 เควส 클리어에 도전 있는 중이랍니다.", ScriptMessageFlag.NpcReplacedByNpc);
                    } else {
                        self.say("\r\n그러면 방송에 เข้าร่วม 싶을 때 다시 찾아와สัปดาห์세요~", ScriptMessageFlag.NpcReplacedByNpc);
                    }
                } else {
                    self.say("\r\n그러면 방송에 เข้าร่วม 싶을 때 다시 찾아와สัปดาห์세요~", ScriptMessageFlag.NpcReplacedByNpc);
                }
                break;
            }
            case 3: { //อธิบาย 듣기
                self.say("#e[이벤트 기간]\r\n\r\n2021ปี 07เดือน 05วัน (เดือน) 점검 후 ~ \r\n미정", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("\r\n#b#e<탕윤 식당>#n#k ต่ำสุด 1명 ~ สูงสุด 3명의 인원이 요리를 만들고 배달까지 เสร็จสมบูรณ์해서 จำกัด เวลา #e30นาที#n 동안 #b#e50,000#n#k คะแนน ได้รับ하는 것이 เป้าหมาย랍니다!", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("\r\n좌측에 ตำแหน่ง한 #b#e<สัปดาห์문판>#n#k 들어오는 สัปดาห์문을 ยืนยัน\r\n레시피대로 음식을 만들어서 배달해보세요~!", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("\r\n#e[재료 수집]#n\r\n\r\n#b#eสัปดาห์방 공간 좌측#n#k ตำแหน่ง한 #e5종#n 재료 앞에서 #r#eSpace키#k#n\r\n키다운으로 해당하는 재료를 ได้รับ할 수 있답니다.\r\n\r\n", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("\r\n#e[재료 놓기]#n\r\n\r\n#b#eสัปดาห์방 공간 중앙#n#k ตำแหน่ง한 #e조리대#n 앞에서 #r#eSpace키#k#n\r\n키다운으로 ปัจจุบัน ได้รับ한 재료를 놓을 수 있답니다.\r\n\r\n#eแต่ละ สัปดาห์문 번호에 해당하는 조리대#n에서만 해당 음식을\r\n조리할 수 있다는 점을 잊지 마세요!", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("\r\n#e[가공 도구 들기]#n\r\n\r\n#b#eสัปดาห์방 공간 우측#n#k ตำแหน่ง한 #e3종#n 가공 도구 앞에서\r\n#r#eSpace키#k#n 키다운으로 도구를 ได้รับ할 수 있답니다.\r\n\r\n#b#e가공 도구#n#k ได้รับ한 สถานะ에서만 재료 가공을 할 수 있다는 점을 잊지 마세요!", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("\r\n#e[재료 가공]#n\r\n\r\n#b#eสัปดาห์방 공간 중앙#n#k ตำแหน่ง한 #e조리대#n 앞에서 #r#eSpace키#n#k\r\n키다운으로 해당 조리대에 놓인 재료를 가공할 수 있답니다.\r\n\r\n#eแต่ละ สัปดาห์문 번호에 해당하는 조리대#n에서만 해당 음식을\r\n조리할 수 있다는 점을 잊지 마세요!", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("\r\n#e[음식 배달]#n\r\n\r\n#b#e음식이 완성된 조리대#n#k 음식을 들 수 있는 สถานะ가 \r\n해당 #e조리대#n 앞에서 #r#eSpace키#k#n 키다운으로 배달을 ดำเนินการ할 수 있답니다.\r\n\r\n우측 배달 공간에서 #eมอนสเตอร์#n들의 방해를 피해 สัปดาห์문한 손님\r\n앞에서 #r#eSpace키#k#n 키다운으로 배달 เสร็จสมบูรณ์!", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("\r\n#b#e진정한 크리에이터#n#k라면 역시 실전이죠!\r\n\r\n어서 직접 체험해보시는 것은 어떠신가요?", ScriptMessageFlag.NpcReplacedByNpc);
                break;
            }
            case 6: { //퍼즐마스터 อธิบาย 듣기
                self.say("#e[이벤트 기간]\r\n\r\n2021ปี 07เดือน ~ \r\n미정", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("\r\n#e#b<퍼즐 마스터>#k#n ต่ำสุด 1명 ~ สูงสุด 3명의 인원이 #e#b총 3가지의 퍼즐#n#k จำกัด เวลา #b#e30นาที#n#k 내에 완성하는 것을 เป้าหมาย로 하는 컨텐츠랍니다!", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("\r\n#b#e화면 중앙에 배치된 액자 속에#n#k แผนที่ 이곳저곳에서 등장하는 #b#e퍼즐 조แต่ละ을 정확한 ตำแหน่ง에 배치 !#n#k", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("\r\n앗! 너무 어려우실 것 같나요? 걱정하지 마세요!\r\n#b#e함께하는 크리에이터들과 힘을 합친다면 충นาที히 เป็นไปได้할 거예요!#n#k", ScriptMessageFlag.NpcReplacedByNpc);
                break;
            }
            case 4: { //วันนี้ 남은 EXP ได้รับ 횟수
                int today = getPlayer().getOneInfoQuestInteger(501555, "today");
                if (2 - today > 0) {
                    self.say("วันนี้ 남은 EXP รางวัล ได้รับ เป็นไปได้ 횟수는 #b#e" + (2 - today) + "회#n#k .", ScriptMessageFlag.NpcReplacedByNpc);
                } else {
                    self.say("วันนี้은 더 이상 EXP รางวัล을 ได้รับ하실 수 없답니다.", ScriptMessageFlag.NpcReplacedByNpc);
                }
                break;
            }
            case 7: { //วันนี้ 남은 EXP ได้รับ 횟수(퍼즐마스터)
                int today = getPlayer().getOneInfoQuestInteger(501600, "today");
                if (2 - today > 0) {
                    self.say("วันนี้ 남은 EXP รางวัล ได้รับ เป็นไปได้ 횟수는 #b#e" + (2 - today) + "회#n#k .", ScriptMessageFlag.NpcReplacedByNpc);
                } else {
                    self.say("วันนี้은 더 이상 EXP รางวัล을 ได้รับ하실 수 없답니다.", ScriptMessageFlag.NpcReplacedByNpc);
                }
                break;
            }
        }
    }

    public void TyoonKitchen_npc1() {
        if (getPlayer().getMap().getId() == 993194401) {
            if (getPlayer().getOneInfoQuestInteger(501555, "today") >= 2) {
                self.say("วันนี้ 남은 EXP ได้รับ 횟수가 없어서 EXP 드릴 수가 없어요 원래 있던 곳으로 돌려 보내드릴게요!", ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                getPlayer().temporaryStatReset(SecondaryStatFlag.DispelItemOptionByField);
                target.registerTransferField(ServerConstants.TownMap);
            } else if (getPlayer().getOneInfoQuestInteger(501555, "complete") >= 1) {
                Calendar CAL = new GregorianCalendar(Locale.KOREA);
                int day = CAL.getTime().getDay();
                boolean doubleEXP = false;
                if (day == 6 || day == 0) { //สัปดาห์말
                    doubleEXP = true;
                }
                long exp = GameConstants.tangyoonExp[getPlayer().getLevel() - 200];
                if (getPlayer().getLevel() < 280) {
                    exp *= 5;
                    if (DBConfig.isGanglim) {
                        exp *= 30;
                    }
                } else if (getPlayer().getLevel() < 285) {
                    exp *= 10;
                    if (DBConfig.isGanglim) {
                        exp *= 25;
                    }
                } else if (getPlayer().getLevel() < 290) {
                    exp *= 30;
                    if (DBConfig.isGanglim) {
                        exp *= 12;
                    }
                } else if (getPlayer().getLevel() < 300) {
                    exp *= 100;
                    if (DBConfig.isGanglim) {
                        exp *= 5;
                    }
                }

                String expString = String.format("%,d", exp);
                if (DBConfig.isGanglim) {
                    if (!doubleEXP) {
                        self.say("\r\n#e#bEXP#k #r" + expString + "#k#n #r100 강림คะแนน#k#n 를드릴게요!\r\n(รางวัล은 ออก 시 지급)", ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                    } else {
                        self.say("\r\n#e#bEXP#k #r" + expString + " * 2 (สัปดาห์말 탕윤 식당 EXP 2배 이벤트!)#k#n #r100 강림คะแนน#k#n 드릴게요!\r\n(รางวัล은 ออก 시 지급)", ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                    }
                    getPlayer().gainHongboPoint(100);
                } else {
                    if (!doubleEXP) {
                        self.say("\r\n#e#bEXP#k #r" + expString + "#k#n  드릴게요!\r\n(รางวัล은 ออก 시 지급)", ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                    } else {
                        self.say("\r\n#e#bEXP#k #r" + expString + " * 2 (สัปดาห์말 탕윤 식당 EXP 2배 이벤트!)#k#n  드릴게요!\r\n(รางวัล은 ออก 시 지급)", ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                    }
                }
                getPlayer().temporaryStatReset(SecondaryStatFlag.DispelItemOptionByField);
                target.registerTransferField(ServerConstants.TownMap);
                getPlayer().updateOneInfo(501555, "start", "0");
                getPlayer().updateOneInfo(501555, "today", String.valueOf(getPlayer().getOneInfoQuestInteger(501555, "today") + 1));
                getPlayer().updateOneInfo(501555, "complete", "0");
                if (!doubleEXP) {
                    getPlayer().gainExpLong(exp);
                } else {
                    getPlayer().gainExpLong(exp * 2);
                }
            } else {
                self.say("\r\n\r\n#e#r50,000 คะแนน#n#k 벌지 못 나오셨다면\r\nรางวัล을 지급해드릴 수 없답니다.\r\n\r\nถัดไป엔 더 노력โปรด!", ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                getPlayer().temporaryStatReset(SecondaryStatFlag.DispelItemOptionByField);
                target.registerTransferField(ServerConstants.TownMap);
            }
        } else {
            int v = -2;
            boolean exit = false;
            while (v != -1 && v != 0 && !exit && !getSc().isStop()) {
                v = self.askMenu("궁금한 게 있으신가요?\r\n\r\n#L1# #b#e<탕윤 식당>#b#e 대해 물어본다.#l\r\n#L2# #b#e<탕윤 식당 ดำเนินการ방법>#n#k 대해 물어본다.#l\r\n#L3# #b#e<게임 รางวัล>#n#k 대해 물어본다.#l\r\n#L4# 원래 있던 สถานที่로 돌아가고 싶다.#l\r\n\r\n#L0# 궁금한 것이 없다.#l", ScriptMessageFlag.NpcReplacedByNpc);
                switch (v) {
                    case 1: { //탕윤 식당에 대해 물어본다.
                        self.say("\r\n#b#e메이플 버라이어티#n#k 첫 번째 특집! #r#e탕.윤.식.당!#n#k", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("\r\n전설의 셰프 #b#e'탕윤'#n#k님과 식당 경영을 체험할 수 있는 기회!", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("\r\n#b#e진정한 크리에이터#n#k라면  기회를 놓칠 수 없겠죠?!", ScriptMessageFlag.NpcReplacedByNpc);
                        break;
                    }
                    case 2: { //탕윤 식당 ดำเนินการ방법에 대해 물어본다.
                        self.say("\r\n#b#e<탕윤 식당>#n#k ต่ำสุด 1명 ~ สูงสุด 3명의 인원이 요리를 만들고 배달까지 เสร็จสมบูรณ์해서 จำกัด เวลา #e30นาที#n 동안 #b#e50,000#n#k คะแนน ได้รับ하는 것이 เป้าหมาย랍니다!", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("\r\n좌측에 ตำแหน่ง한 #b#e<สัปดาห์문판>#n#k 들어오는 สัปดาห์문을 ยืนยัน\r\n레시피대로 음식을 만들어서 배달해보세요~!", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("\r\n#e[재료 수집]#n\r\n\r\n#b#eสัปดาห์방 공간 좌측#n#k ตำแหน่ง한 #e5종#n 재료 앞에서 #r#eSpace키#k#n\r\n키다운으로 해당하는 재료를 ได้รับ할 수 있답니다.\r\n\r\n", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("\r\n#e[재료 놓기]#n\r\n\r\n#b#eสัปดาห์방 공간 중앙#n#k ตำแหน่ง한 #e조리대#n 앞에서 #r#eSpace키#k#n\r\n키다운으로 ปัจจุบัน ได้รับ한 재료를 놓을 수 있답니다.\r\n\r\n#eแต่ละ สัปดาห์문 번호에 해당하는 조리대#n에서만 해당 음식을\r\n조리할 수 있다는 점을 잊지 마세요!", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("\r\n#e[가공 도구 들기]#n\r\n\r\n#b#eสัปดาห์방 공간 우측#n#k ตำแหน่ง한 #e3종#n 가공 도구 앞에서\r\n#r#eSpace키#k#n 키다운으로 도구를 ได้รับ할 수 있답니다.\r\n\r\n#b#e가공 도구#n#k ได้รับ한 สถานะ에서만 재료 가공을 할 수 있다는 점을 잊지 마세요!", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("\r\n#e[재료 가공]#n\r\n\r\n#b#eสัปดาห์방 공간 중앙#n#k ตำแหน่ง한 #e조리대#n 앞에서 #r#eSpace키#n#k\r\n키다운으로 해당 조리대에 놓인 재료를 가공할 수 있답니다.\r\n\r\n#eแต่ละ สัปดาห์문 번호에 해당하는 조리대#n에서만 해당 음식을\r\n조리할 수 있다는 점을 잊지 마세요!", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("\r\n#e[음식 배달]#n\r\n\r\n#b#e음식이 완성된 조리대#n#k 음식을 들 수 있는 สถานะ가 \r\n해당 #e조리대#n 앞에서 #r#eSpace키#k#n 키다운으로 배달을 ดำเนินการ할 수 있답니다.\r\n\r\n우측 배달 공간에서 #eมอนสเตอร์#n들의 방해를 피해 สัปดาห์문한 손님\r\n앞에서 #r#eSpace키#k#n 키다운으로 배달 เสร็จสมบูรณ์!", ScriptMessageFlag.NpcReplacedByNpc);
                        break;
                    }
                    case 3: { //게임 รางวัล에 대해 물어본다.
                        self.say("\r\n#b#e50,000#n#k คะแนน ได้รับ하시면 #eวัน정량의 EXP#n\r\nรางวัล으로 드린답니다!\r\n\r\n이런 #r#e엄청난 기회#n#k 놓칠 수는 없겠죠?", ScriptMessageFlag.NpcReplacedByNpc);
                        break;
                    }
                    case 4: {
                        getPlayer().temporaryStatReset(SecondaryStatFlag.DispelItemOptionByField);
                        if (1 == self.askYesNo("\r\n#e#b<광장>#n#k 돌아가시겠습니까?", ScriptMessageFlag.NpcReplacedByNpc)) {
                            target.registerTransferField(ServerConstants.TownMap);
                            exit = true;
                        }
                        break;
                    }
                }
            }
        }
    }

    public void npc_9062573() {
        if (getPlayer().getMap().getId() == 993194801) { //ออกแผนที่
            if (getPlayer().getOneInfoQuestInteger(501600, "today") >= 2) {
                self.say("วันนี้ 남은 EXP ได้รับ 횟수가 없어서 EXP 드릴 수가 없어요 원래 있던 곳으로 돌려 보내드릴게요!", ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                if (DBConfig.isHosting) {
                    target.registerTransferField(ServerConstants.TownMap);
                } else {
                    target.registerTransferField(100000000);
                }
            } else if (getPlayer().getOneInfoQuestInteger(501600, "complete") >= 1) {
                Calendar CAL = new GregorianCalendar(Locale.KOREA);
                int day = CAL.getTime().getDay();
                boolean doubleEXP = false;
                if (day == 6 || day == 0) { //สัปดาห์말
                    doubleEXP = true;
                }
                long exp = GameConstants.tangyoonExp[getPlayer().getLevel() - 200];
                if (getPlayer().getLevel() < 280) {
                    exp *= 5;
                    if (DBConfig.isGanglim) {
                        exp *= 30;
                    }
                } else if (getPlayer().getLevel() < 285) {
                    exp *= 10;
                    if (DBConfig.isGanglim) {
                        exp *= 25;
                    }
                } else if (getPlayer().getLevel() < 290) {
                    exp *= 30;
                    if (DBConfig.isGanglim) {
                        exp *= 12;
                    }
                } else if (getPlayer().getLevel() < 300) {
                    exp *= 100;
                    if (DBConfig.isGanglim) {
                        exp *= 5;
                    }
                }
                String expString = String.format("%,d", exp);
                if (DBConfig.isGanglim) {
                    if (!doubleEXP) {
                        self.say("\r\n#e#bEXP#k #r" + expString + "#k#n #r100 강림คะแนน#k#n 를드릴게요!\r\n(รางวัล은 ออก 시 지급)", ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                    } else {
                        self.say("\r\n#e#bEXP#k #r" + expString + " * 2 (สัปดาห์말 탕윤 식당 EXP 2배 이벤트!)#k#n #r100 강림คะแนน#k#n 드릴게요!\r\n(รางวัล은 ออก 시 지급)", ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                    }
                    getPlayer().gainHongboPoint(100);
                } else {
                    if (!doubleEXP) {
                        self.say("\r\n#e#bEXP#k #r" + expString + "#k#n  드릴게요!\r\n(รางวัล은 ออก 시 지급)", ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                    } else {
                        self.say("\r\n#e#bEXP#k #r" + expString + " * 2 (สัปดาห์말 퍼즐 마스터 EXP 2배 이벤트!)#k#n  드릴게요!\r\n(รางวัล은 ออก 시 지급)", ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                    }
                }
                getPlayer().temporaryStatReset(SecondaryStatFlag.DispelItemOptionByField);
                if (DBConfig.isHosting) {
                    target.registerTransferField(ServerConstants.TownMap);
                } else {
                    target.registerTransferField(100000000);
                }
                getPlayer().updateOneInfo(501600, "start", "0");
                getPlayer().updateOneInfo(501600, "today", String.valueOf(getPlayer().getOneInfoQuestInteger(501600, "today") + 1));
                getPlayer().updateOneInfo(501600, "complete", "0");
                if (!doubleEXP) {
                    getPlayer().gainExpLong(exp);
                } else {
                    getPlayer().gainExpLong(exp * 2);
                }
            } else {
                getPlayer().temporaryStatReset(SecondaryStatFlag.DispelItemOptionByField);
                self.say("\r\n#e#r3개의 퍼즐을 완성하지 못 나오셨다면\r\nรางวัล을 지급해드릴 수 없답니다.#k\r\n\r\nถัดไป엔 더 노력해 สัปดาห์세요!", ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                if (DBConfig.isHosting) {
                    target.registerTransferField(ServerConstants.TownMap);
                } else {
                    target.registerTransferField(100000000);
                }
            }
        } else {
            int v = -2;
            boolean exit = false;
            while (v != -1 && v != 0 && !exit && !getSc().isStop()) {
                v = self.askMenu("궁금한 게 있으신가요?\r\n\r\n#L1# #b#e<퍼즐 마스터>#b#e 대해 물어본다.#l\r\n#L2# #b#e<퍼즐 마스터 ดำเนินการ 방법>#n#k 대해 물어본다.#l\r\n#L3# #b#e<게임 รางวัล>#n#k 대해 물어본다.#l\r\n#L4# 원래 있던 สถานที่로 돌아가고 싶다.#l\r\n\r\n#L0# 궁금한 것이 없다.#l", ScriptMessageFlag.NpcReplacedByNpc);
                switch (v) {
                    case 1: { //퍼즐 마스터에 대해 물어본다.
                        self.say("\r\n#b#e메이플 버라이어티#n#k 두 번째 특집! #r#e퍼즐 마스터!#n#k", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("\r\n#b#e진정한 크리에이터#n#k라면  기회를 놓칠 수 없겠죠?!", ScriptMessageFlag.NpcReplacedByNpc);
                        break;
                    }
                    case 2: { //ดำเนินการ방법
                        self.say("\r\n#e#b<퍼즐 마스터>#k#n ต่ำสุด 1명 ~ สูงสุด 3명의 인원이 #e#b총 3가지의 퍼즐#n#k จำกัด เวลา #b#e30นาที#n#k 내에 완성하는 것을 เป้าหมาย로 하는 컨텐츠랍니다!", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("\r\n#b#e화면 중앙에 배치된 액자 속에#n#k แผนที่ 이곳저곳에서 등장하는 #b#e퍼즐 조แต่ละ을 정확한 ตำแหน่ง에 배치 !#n#k", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("\r\n앗! 너무 어려우실 것 같나요? 걱정하지 마세요!\r\n#b#e함께하는 크리에이터들과 힘을 합친다면 충นาที히 เป็นไปได้할 거예요!#n#k", ScriptMessageFlag.NpcReplacedByNpc);
                        break;
                    }
                    case 3: { //게임 รางวัล에 대해 물어본다.
                        self.say("\r\n#b#e총 3가지의 퍼즐#n#k 완성 เลเวล 따라 #eวัน정량의 EXP#n\r\nรางวัล으로 드리고 있어요!\r\n\r\n이런 #r#e엄청난 기회#n#k 놓칠 수는 없겠죠?", ScriptMessageFlag.NpcReplacedByNpc);
                        break;
                    }
                    case 4: {
                        if (1 == self.askYesNo("\r\n#e#b<광장>#n#k 돌아가시겠습니까?", ScriptMessageFlag.NpcReplacedByNpc)) {
                            getPlayer().temporaryStatReset(SecondaryStatFlag.DispelItemOptionByField);
                            if (DBConfig.isHosting) {
                                target.registerTransferField(ServerConstants.TownMap);
                            } else {
                                target.registerTransferField(100000000);
                            }
                            exit = true;
                        }
                        break;
                    }
                }
            }
        }
    }

    public void TyoonKitchen_npc2() {
        if (getPlayer().getMap().getId() != 993194500)
            self.sayOk("\r\n요리는 단순한 วัน이 아니라 열정이네.", ScriptMessageFlag.NpcReplacedByNpc);
    }

    public void enter_993194400() {
        getPlayer().send(CField.addPopupSay(9062561, 5000, "탕윤 식당에 오신 것을\r\n환영~!", ""));
        getPlayer().send(CField.addPopupSay(9062563, 4000, "전설의 셰프의 식당에서 펼쳐지는 숨막히는 삶의 체험!", ""));
        getPlayer().send(CField.addPopupSay(9062561, 5000, "เริ่ม~! 하겠~!", ""));
    }

    public void enter_993194900() {

    }

    public void TyoonKitchen_setting() {
        Field field = getPlayer().getMap();
        if (field.getFieldSetInstance() != null) {
            objects.fields.fieldset.instance.TangyoonKitchen f = (objects.fields.fieldset.instance.TangyoonKitchen) field.getFieldSetInstance();
            getPlayer().send(objects.fields.fieldset.instance.TangyoonKitchen.TangyoonOrder(1));
            getPlayer().temporaryStatSet(993194500, Integer.MAX_VALUE, SecondaryStatFlag.DispelItemOptionByField, 1);
            getPlayer().send(CWvsContext.InfoPacket.brownMessage("ปัจจุบัน แผนที่에서는 잠재 ความสามารถ과 에디셔널 잠재ความสามารถ이 ใช้งาน되지 않."));
            final MapleCharacter user = getPlayer();
            Timer.EventTimer.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    if (user.getMap().getId() == 993194500) {
                        user.send(objects.fields.fieldset.instance.TangyoonKitchen.TangyoonOrder(2));
                        user.send(objects.fields.fieldset.instance.TangyoonKitchen.TangyoonObjectPacket_1());
                        user.send(CField.environmentChange("UI/TyoonKitchen_UI.img/TyoonKitchen_UI/effect/countdown", 16));
                    }
                }
            }, 3000);

            Timer.EventTimer.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    if (user.getMap().getId() == 993194500) {
                        user.send(objects.fields.fieldset.instance.TangyoonKitchen.TangyoonOrder(3));
                        user.send(CField.tangyoonClock(1800000));
                    }
                }
            }, 6000);

            Timer.EventTimer.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    if (user.getMap().getId() == 993194500) {
                        user.send(CField.environmentChange("UI/UIMiniGame.img/mapleOneCard/Effect/screeneff/start", 16));
                        user.send(CField.environmentChange("Sound/MiniGame.img/oneCard/start", 5, 100));
                    }
                }
            }, 6000);
        }
    }
}
