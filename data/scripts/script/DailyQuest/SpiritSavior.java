package script.DailyQuest;

import network.models.CField;
import objects.fields.fieldset.childs.SpiritSaviorEnter;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SpiritSavior extends ScriptEngineNPC {

    /**
     * qexID : 16214
     * count -> วันวัน 몇번했는지 횟수
     * date -> 21/06/16 언제 시행했는지
     * coin -> วันนี้ 코인 몇개얻었는지
     * todayrecord -> วันนี้ 최고기록
     */

    /**
     * qexID : 16215
     * point -> 몇점인지
     * play -> 몇번시도했는지?
     * saved 몇마리 구하는중인가
     * life 100부터เริ่ม
     * chase 따라오는 괴물새끼 단계?
     * 8644301 : 따라오는 괴물새끼 1단계
     */


    public void spiritSavior_NPC() {
        initNPC(MapleLifeFactory.getNPC(3003381));
        SpiritSaviorEnter fieldSet = (SpiritSaviorEnter) fieldSet("SpiritSaviorEnter");
        if (fieldSet == null) {
            self.sayOk("지금은 스피릿 세이비어를 ดำเนินการ할 수 없.");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
        Date lastTime = null;
        Date now = null;
        try {
            lastTime = sdf.parse(getPlayer().getOneInfo(16214, "date"));
            now = sdf.parse(sdf.format(new Date()));
        } catch (Exception e) {
            lastTime = null;
        }
        if (lastTime == null) {
            getPlayer().updateOneInfo(16214, "date", new SimpleDateFormat("yy/MM/dd").format(new Date()));
            getPlayer().updateOneInfo(16214, "count", "0");
        }
        if (lastTime != null && !lastTime.equals(now)) {
            getPlayer().updateOneInfo(16214, "count", "0");
        }
        // self.say("내 친구들 มาก 구해줬남?\r\n구출 점수는 #b#e10500점#n#k 이구남!\r\n 정도면 #r#e스피릿 코인 10개#n#k 줄 수 있겠담!");
        // self.say("ถัดไป에 또 도와달람!");

        //만약에 코인받을만큼 구하지못했을경우
        //self.say("음... 내 친구들을 มาก 구하지 못했구남!");
        //self.say("ถัดไป에는 친구들을 มาก 구해달람!");

        //즉시เสร็จสมบูรณ์대사
        //self.askYesNo("#b#ho!##k #b#e<스피릿 세이비어>#n#k 즉시 เสร็จสมบูรณ์할 수 있담. 즉시 เสร็จสมบูรณ์ 시 เข้า 횟수가 #r#e1회 หัก#n#k되니까 잊지 마람!\r\n#r(ยกเลิก 시 เข้า이 ดำเนินการ.)#k#e\r\n\r\n◆วันนี้ 남은 즉시 เสร็จสมบูรณ์ 횟수 : #b1회#k\r\n◆즉시 เสร็จสมบูรณ์ รางวัล : #b#t4310235:# 10개");
        //self.sayOk("즉시 เสร็จสมบูรณ์ รางวัล을 สัปดาห์었담!");

        //다햇을경우
        //self.say("วันนี้은 더 이상 #b#e<스피릿 세이비어>#n#k 도전할 수 없담.\r\nพรุ่งนี้ 다시 찾아와 달람.\r\n\r\n#r#e(1วัน 3회 เข้า เป็นไปได้)#n#k");
        if (getPlayer().getMap().getId() == 921172400) { // รางวัลแผนที่
            if (lastTime != null && lastTime.equals(now)) {
                int point = getPlayer().getOneInfoQuestInteger(16215, "point");
                if (point >= 1000) {
                    int todayCoin = getPlayer().getOneInfoQuestInteger(16214, "coin");
                    self.say("내 친구들 มาก 구해줬남?\r\n구출 점수는 #b#e" + point + "점#n#k 이구남!\r\n 정도면 #r#e스피릿 코인 " + (point / 1000) + "개#n#k 줄 수 있겠담!\r\n(하루 สูงสุด 코인 ได้รับ량 " + todayCoin + " / 30)", ScriptMessageFlag.NoEsc);
                    self.say("ถัดไป에 또 도와달람!", ScriptMessageFlag.NoEsc);
                    int giveCoin = (point / 1000);
                    if (todayCoin + giveCoin > 30) {
                        int what = giveCoin - ((todayCoin + giveCoin) - 30);
                        giveCoin = what;
                    }
                    if (getPlayer().getOneInfoQuestInteger(16215, "point") > 0) {
                        if (target.exchange(4310235, giveCoin) > 0) {
                            getPlayer().updateOneInfo(16214, "coin", String.valueOf(getPlayer().getOneInfoQuestInteger(16214, "coin") + giveCoin));
                            getPlayer().updateOneInfo(16215, "point", "0");
                            target.registerTransferField(450005000);
                        } else {
                            self.sayOk("อื่นๆ창을 비워달람!", ScriptMessageFlag.NoEsc);
                        }
                    } else {
                        target.registerTransferField(450005000);
                    }
                } else {
                    self.say("음... 내 친구들을 มาก 구하지 못했구남!");
                    self.say("ถัดไป에는 친구들을 มาก 구해달람!");
                    target.registerTransferField(450005000);
                }
            } else {
                self.say("음... 내 친구들을 มาก 구하지 못했구남!");
                self.say("ถัดไป에는 친구들을 มาก 구해달람!");
                target.registerTransferField(450005000);
            }
        } else {
            int v = -1;
            int count = getPlayer().getOneInfoQuestInteger(16214, "count");
            int todayrecordCoin = getPlayer().getOneInfoQuestInteger(16214, "todayrecord") / 1000;
            int canD = 3;
            if (getPlayer().getLevel() >= 230) canD--;
            if (getPlayer().getLevel() >= 235) canD--;
            if (lastTime != null && lastTime.equals(now) && getPlayer().getOneInfoQuestInteger(16214, "count") > 0) {
                if (getPlayer().getLevel() >= 230) {
                    v = self.askMenu("#b#e<스피릿 세이비어>#n#k\r\n 내 친구들을 어서 구해줬음 좋겠담!\r\n\r\n#b#L0# <스피릿 세이비어> 도전한다.#l\r\n#L1# 스피릿 코인을 แลกเปลี่ยน한다.#l\r\n#L2# อธิบาย을 듣는다.#l#k\r\n\r\n\r\n#e*" + canD + "회 클리어 후 즉시 เสร็จสมบูรณ์ เป็นไปได้.\r\n*วันนี้의 최고 รางวัล 기록:   \r\n#i4310235##b#e#t4310235:##n #e" + todayrecordCoin + "개");
                } else {
                    v = self.askMenu("#b#e<스피릿 세이비어>#n#k\r\n 내 친구들을 어서 구해줬음 좋겠담!\r\n\r\n#b#L0# <스피릿 세이비어> 도전한다.#l\r\n#L1# 스피릿 코인을 แลกเปลี่ยน한다.#l\r\n#L2# อธิบาย을 듣는다.#l#k");
                }
            } else {
                v = self.askMenu("#b#e<스피릿 세이비어>#n#k\r\n 내 친구들을 어서 구해줬음 좋겠담!\r\n\r\n#b#L0# <스피릿 세이비어> 도전한다.#l\r\n#L1# 스피릿 코인을 แลกเปลี่ยน한다.#l\r\n#L2# อธิบาย을 듣는다.#l#k\r\n\r\n\r\n#e*2회 클리어 후 즉시 เสร็จสมบูรณ์ เป็นไปได้.\r\n*วันนี้의 최고 รางวัล 기록:   \r\n#i4310235##b#e#t4310235:##n #e0개");
            }
            switch (v) {

                case 0: { //<스피릿 세이비어> 도전한다.
                    if (count >= canD && count < 3) {
                        if (1 == self.askYesNo("#b#ho!##k #b#e<스피릿 세이비어>#n#k 즉시 เสร็จสมบูรณ์할 수 있담. 즉시 เสร็จสมบูรณ์ 시 เข้า 횟수가 #r#e1회 หัก#n#k되니까 잊지 마람!\r\n#r(ยกเลิก 시 เข้า이 ดำเนินการ.)#k#e\r\n\r\n◆วันนี้ 남은 즉시 เสร็จสมบูรณ์ 횟수 : #b" + (3 - count) + "회#k\r\n◆즉시 เสร็จสมบูรณ์ รางวัล : #b#t4310235:# " + todayrecordCoin + "개")) {
                            int todayCoin = getPlayer().getOneInfoQuestInteger(16214, "coin");
                            int giveCoin = todayrecordCoin;
                            boolean fuck = false;
                            if (todayCoin + giveCoin > 30) {
                                fuck = true;
                                int what = giveCoin - ((todayCoin + giveCoin) - 30);
                                giveCoin = what;
                            }
                            if (exchange(4310235, giveCoin) > 0) {
                                getPlayer().updateOneInfo(16214, "coin", String.valueOf(getPlayer().getOneInfoQuestInteger(16214, "coin") + giveCoin));
                                getPlayer().updateOneInfo(16214, "count", String.valueOf(count + 1));
                                if (fuck) {
                                    self.sayOk("즉시 เสร็จสมบูรณ์ รางวัล을 สัปดาห์었담!\r\nวันนี้은 #r#e30개 이상#n#k 코인을 받아 가서  만큼 밖에 못 줄것 같담...\r\n");
                                } else {
                                    self.sayOk("즉시 เสร็จสมบูรณ์ รางวัล을 สัปดาห์었담!");
                                }
                            } else {
                                self.sayOk("อื่นๆ창을 비워달람!", ScriptMessageFlag.NoEsc);
                            }
                        } else {
                            int enter = fieldSet.enter(target.getId(), 0);
                            if (enter == -1) self.say("알 수 없는 이유로 เข้า할 수 없. 잠시 후에 다시 시도해 สัปดาห์십시오.");
                            else if (enter == 1) self.sayOk("<스피릿 세이비어> 혼자서만 도전할 수 있담.\r\nปาร์ตี้ ปลดล็อก 다시 찾아와 줬음 좋겠담.");
                            else if (enter == 2) self.say("เลเวล ต่ำสุด " + fieldSet.minLv + " 이상이어야 내 친구들을 도와줄 수 있담.");
                            else if (enter == 3) self.say("ปัจจุบัน ทั้งหมด 인스턴스가 가득차 도전할 수 없담.");
                            else if (enter == -2)
                                self.sayOk("วันนี้은 더 이상 #b#e<스피릿 세이비어>#n#k 도전할 수 없담.\r\nพรุ่งนี้ 다시 찾아와 달람.\r\n\r\n#r#e(1วัน 3회 เข้า เป็นไปได้)#n#k");
                        }
                    } else {
                        if (1 == self.askYesNo("어서 내 친구들을 구해สัปดาห์면 좋겠담. 지금 도전 할 건감?\r\n\r\n#bวันนี้ 도전 횟수 " + count + " / 3#k")) {
                            int enter = fieldSet.enter(target.getId(), 0);
                            if (enter == -1) self.say("알 수 없는 이유로 เข้า할 수 없. 잠시 후에 다시 시도해 สัปดาห์십시오.");
                            else if (enter == 1) self.sayOk("<스피릿 세이비어> 혼자서만 도전할 수 있담.\r\nปาร์ตี้ ปลดล็อก 다시 찾아와 줬음 좋겠담.");
                            else if (enter == 2) self.say("เลเวล ต่ำสุด " + fieldSet.minLv + " 이상이어야 내 친구들을 도와줄 수 있담.");
                            else if (enter == 3) self.say("ปัจจุบัน ทั้งหมด 인스턴스가 가득차 도전할 수 없담.");
                            else if (enter == -2)
                                self.sayOk("วันนี้은 더 이상 #b#e<스피릿 세이비어>#n#k 도전할 수 없담.\r\nพรุ่งนี้ 다시 찾아와 달람.\r\n\r\n#r#e(1วัน 3회 เข้า เป็นไปได้)#n#k");
                        } else {
                            self.sayOk("너무 늦으면 내 친구들을 영영 못 볼 수도 있담...");
                        }
                    }
                    break;
                }
                case 1: { //스피릿 코인을 แลกเปลี่ยน한다. (แก้ไข해야해!!)
                    if (getPlayer().getItemQuantity(4310235, false) > 0) {
                        int number = self.askNumber("#b#i4310235:##t4310235##k #r#i1712004:##t1712004##k랑 바꿀램?\r\n(#b#t4310235# 1개#k = #r#t1712004# 1개#k)\r\n\r\nสูงสุด #r#e" + getPlayer().getItemQuantity(4310235, false) + "개#n#k แลกเปลี่ยน เป็นไปได้.", 1, 1, getPlayer().getItemQuantity(4310235, false));
                        if (exchange(4310235, -number, 1712004, number) > 0) {
                            self.sayOk("자! 여기 #b#i1712004:##t1712004#" + number + "개#k 줄겜!\r\nถัดไป에 또 도와달람!");
                        } else {
                            self.sayOk("อุปกรณ์창이 ไม่พอ하거나 스피릿 코인이 ไม่พอ하담! ยืนยัน해달람!");
                        }
                    } else {
                        self.sayOk("스피릿 코인이 없으면 아케인심볼을 แลกเปลี่ยน할수 없담!");
                    }
                    break;
                }
                case 2: { //อธิบาย을 듣는다
                    int vvv = -2;
                    while (vvv != 2 && vvv != 100 && vvv != -1 && !getSc().isStop()) {
                        vvv = self.askMenu("무엇을 알고 싶은감?\r\n#L0# #e스피릿 세이비어 규칙#n#l\r\n#L1# #e스피릿 세이비어 รางวัล#n#l\r\n#L2# #eวันวัน เควส 간편하게 하기#n#l"/*\r\n#L3# #e힘내라! 보너스 스피릿 코인이란?#n#l*/ + "\r\n#L100# #eอธิบาย을 듣지 않는다.#n#l");
                        switch (vvv) {
                            case 0: //스피릿 세이비어 규칙
                                self.say("#e<스피릿 세이비어 규칙>#n\r\n\r\n#eจำกัดเวลา이 끝나기 전에 / ป้องกัน도가 모두 깎이기 전에#n  สูงสุด한 많은 #b#e속박된 돌의 정령#n#k 구출해야 한담!\r\n#b#e속박된 돌의 정령#n#k 구출 #r#e채집/NPCสนทนา키를 눌러서#n#k 데리고 다닐 수 있담!");
                                self.say("#e<스피릿 세이비어 규칙>#n\r\n\r\n친구들은 #b#eสูงสุด 5명 까지#n#k 데리고 다닐 수 있담!\r\n친구들을 ครั้งแรก เริ่ม했던 #b#e구출지점까지 무사히 데려오면#n#k\r\n#e'구출점수'#n 얻을 수 있담!\r\n#b#e한 번에 많은 친구들을 구출할 수록#n#k 높은 점수를 얻는담!\r\n\r\n#e1명-200점\r\n2명-500점\r\n3명-1000점\r\n4명-1500점\r\n5명-2500점#n");
                                self.say("#e<스피릿 세이비어 규칙>#n\r\n\r\n하지만 #r#e나쁜 정령#n#k들이 친구들을 쉽게 데려가도록 내버려 두지 않을 거담.\r\nวัน정 เวลา이 지나면 แผนที่ 곳곳을 돌아다니는 #r#e정령의 파편#n#k 생겨날거담. 그 녀석들에게 맞으면 #b#eป้องกัน도#n#k 깎이게 된담.");
                                self.say("#e<스피릿 세이비어 규칙>#n\r\n\r\n또 우리 친구들을 구출 #r#e맹독의 정령#n#k 너를 추격하기 เริ่ม할거담!\r\n#r#e맹독의 정령#n#k 많은 친구들이 구출 될 수록 #e점점 커지고 빨라진담.#n 녀석에게 โจมตี받으면 많은 ป้องกัน도를 잃게 데리고 있던 친구들도 모두 사라지게 된담.. 녀석에게 부딪히지 않도록 조심하람!");
                                getSc().flushSay();
                                break;
                            case 1: //스피릿 세이비어 รางวัล
                                self.say("#e<스피릿 세이비어 รางวัล>#n\r\n\r\n친구들을 구출해서 #b#e구출 점수#n#k 얻으면 #e1000 คะแนน#n당 #b#i4310235:##t4310235##k 1개를 얻을 수 있담.");
                                self.say("#e<스피릿 세이비어 รางวัล>#n\r\n\r\n#b#i4310235:##t4310235# 3개#k 나에게 가져오면 #r#i1712004##t1712004# 1개#k แลกเปลี่ยน해 สัปดาห์겠담.");
                                self.say("#e<스피릿 세이비어 รางวัล>#n\r\n\r\n도전은 #b#e하루에 3번#n#k할 수 #r#e 하루에 สูงสุด 30개의 코인#n#k 얻을 수 있담. 그럼 내 친구들을 잘 부탁한담!");
                                getSc().flushSay();
                                break;
                            case 2: //วันวัน เควส 간편하게 하기
                                if (getPlayer().getLevel() < 230) {
                                    self.sayOk("새로운 아케인리버 พื้นที่의 วันวัน เควส 수행할 수 있게  #e<스피릿 세이비어>#n ภารกิจ를 더욱 손쉽게 완수 할 수 있도록 ทุกวัน #b#e<스피릿 세이비어> 즉시 เสร็จสมบูรณ์#n#k 기회를 준담. #e즉시 เสร็จสมบูรณ์#n#k 이용 วันนี้ 내가 기록한 최고 기록을 기준으로 즉시 ภารกิจ를 เสร็จสมบูรณ์할 수 있담. 단, EXP รางวัล 및 업적과 관련된 เนื้อหา은 기록되지 않으니  점 잊지마람!\r\n#r*즉시 เสร็จสมบูรณ์ 시 힘내라 보너스 코인은 ได้รับ 할 수 없.#k");
                                } else if (getPlayer().getLevel() >= 230 && getPlayer().getLevel() < 235) {
                                    self.sayOk("새로운 아케인리버 พื้นที่의 วันวัน เควส 수행할 수 있게  #e<스피릿 세이비어>#n ภารกิจ를 더욱 손쉽게 완수 할 수 있도록 ทุกวัน #b#e<스피릿 세이비어> 즉시 เสร็จสมบูรณ์#n#k 기회를 준담. #e즉시 เสร็จสมบูรณ์#n#k 이용 วันนี้ 내가 기록한 최고 기록을 기준으로 즉시 ภารกิจ를 เสร็จสมบูรณ์할 수 있담. 단, EXP รางวัล 및 업적과 관련된 เนื้อหา은 기록되지 않으니  점 잊지마람!\r\n#r*즉시 เสร็จสมบูรณ์ 시 힘내라 보너스 코인은 ได้รับ 할 수 없.#k\r\n\r\n\r\n#e#bวันนี้ เป็นไปได้한 <스피릿 세이비어> 즉시 เสร็จสมบูรณ์ 횟수 (0/1)#n#k\r\n ▶모라스 พื้นที่: #r#eวันวัน เควส 수행 เป็นไปได้#n#k\r\n ▶에스페라 พื้นที่: #e#kวันวัน เควส 수행 불가#n#k");
                                } else {
                                    self.sayOk("새로운 아케인리버 พื้นที่의 วันวัน เควส 수행할 수 있게  #e<스피릿 세이비어>#n ภารกิจ를 더욱 손쉽게 완수 할 수 있도록 ทุกวัน #b#e<스피릿 세이비어> 즉시 เสร็จสมบูรณ์#n#k 기회를 준담. #e즉시 เสร็จสมบูรณ์#n#k 이용 วันนี้ 내가 기록한 최고 기록을 기준으로 즉시 ภารกิจ를 เสร็จสมบูรณ์할 수 있담. 단, EXP รางวัล 및 업적과 관련된 เนื้อหา은 기록되지 않으니  점 잊지마람!\r\n#r*즉시 เสร็จสมบูรณ์ 시 힘내라 보너스 코인은 ได้รับ 할 수 없.#k\r\n\r\n\r\n#e#bวันนี้ เป็นไปได้한 <스피릿 세이비어> 즉시 เสร็จสมบูรณ์ 횟수 (0/2)#n#k\r\n ▶모라스 พื้นที่: #r#eวันวัน เควส 수행 เป็นไปได้#n#k\r\n ▶에스페라 พื้นที่: #e#rวันวัน เควส 수행 เป็นไปได้#n#k");
                                }
                                break;
                            case 3: //힘내라! 보너스 스피릿 코인이란?
                                //self.say("#e<스피릿 세이비어 รางวัล>#n\r\n\r\n하루에 3번을 모두 도전  얻은 스피릿 코인의 총 개수가 1개 이상 9개 미만วัน 경우에는 응원의 차원에서 #b#e힘내라! 보너스 스피릿 코인#n#k 총 ได้รับ 개수에 따라 차등으로 เพิ่ม 지급되니 포기하지 않길 바란담!");
                                break;
                            case 100: //อธิบาย을 듣지 않는다.
                                break;
                        }
                    }
                    break;
                }
            }
        }
    }

    public void enter_SavingSpirit() {
        if (getPlayer().getMap().getFieldSetInstance() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
            Date lastTime = null;
            Date now = null;
            try {
                lastTime = sdf.parse(getPlayer().getOneInfo(16214, "date"));
                now = sdf.parse(sdf.format(new Date()));
            } catch (Exception e) {
                lastTime = null;
            }
            if (lastTime != null && !lastTime.equals(now)) {
                getPlayer().updateOneInfo(16214, "count", "0");
                getPlayer().updateOneInfo(16214, "date", new SimpleDateFormat("yy/MM/dd").format(new Date()));
                getPlayer().updateOneInfo(16214, "coin", "0");
                getPlayer().updateOneInfo(16214, "todayrecord", "0");
            }
            getPlayer().updateOneInfo(16214, "date", new SimpleDateFormat("yy/MM/dd").format(new Date()));
            getPlayer().updateOneInfo(16214, "count", String.valueOf(getPlayer().getOneInfoQuestInteger(16214, "count") + 1)); //เริ่ม하자마자 카운트+1
            getPlayer().updateInfoQuest(16215, "point=0;play=1;saved=0;life=100;chase=0;");
            getPlayer().send(CField.environmentChange("event/start", 19, 0));
            getPlayer().send(CField.environmentChange("Dojang/clear", 5, 100));
            objects.fields.fieldset.instance.SpiritSavior spiritSavior = (objects.fields.fieldset.instance.SpiritSavior) getPlayer().getMap().getFieldSetInstance();
            spiritSavior.spawnRandomPrison();
            spiritSavior.spawnBomb();
        }
    }

    public void out_SavingSpirit() {
        if (getPlayer().getOneInfoQuestInteger(16215, "play") > 0) {
            int todayrecord = getPlayer().getOneInfoQuestInteger(16214, "todayrecord");
            int point = getPlayer().getOneInfoQuestInteger(16215, "point");
            if (todayrecord < point && point >= 1000) {
                getPlayer().updateOneInfo(16214, "todayrecord", String.valueOf(point));
            }
            getPlayer().send(CField.environmentChange("Map/Effect2.img/event/gameover", 16, 0));
            getPlayer().updateOneInfo(16215, "play", "0");
        }
    }
}
