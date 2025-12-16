package script.DailyQuest;

import database.DBConfig;
import network.models.CField;
import network.models.FontColorType;
import network.models.FontType;
import objects.fields.fieldset.FieldSet;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import scripting.ScriptMessageFlag;
import scripting.newscripting.Script;
import scripting.newscripting.ScriptEngineNPC;

import java.text.SimpleDateFormat;
import java.util.Date;

import static objects.fields.fieldset.instance.ErdaSpectrum.*;

public class ErdaSpectrum extends ScriptEngineNPC {

    /**
     * qexID : 34170
     * count -> วันวัน กี่번했는지 횟수
     * date -> 21/06/16 เมื่อไหร่ 시행했는지
     * ctype -> 메세지뜨는용인듯 0이면 Failed
     * clear -> 1이면 450001550แผนที่ รางวัลและ แผนที่이펙트
     */


    public void arcane1MO_Enter() {
        initNPC(MapleLifeFactory.getNPC(3003145));
        FieldSet fieldSet = fieldSet("ErdaSpectrumEnter");
        if (fieldSet == null) {
            self.sayOk("지금은 에르다 스펙트럼을 ดำเนินการ할 수 없.");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
        Date lastTime = null;
        Date now = null;
        try {
            lastTime = sdf.parse(getPlayer().getOneInfo(34170, "date"));
            now = sdf.parse(sdf.format(new Date()));
        } catch (Exception e) {
        }
        if (getPlayer().getMap().getId() == 450001550) { //รางวัลแผนที่
            if (lastTime != null && lastTime.equals(now) && getPlayer().getOneInfoQuestInteger(34170, "clear") > 0) {
                int v = self.askMenu("#b#e<에르다 스펙트럼>#n#k\r\n\r\n몸은 좀 어떠신가요? มาก 다치진 않으셨죠?\r\n조사 때마다 느끼는 거지만 정말 ง่าย 날이 없네요.\r\n#b#L0# <에르다 스펙트럼> รางวัล을 받는다.#l\r\n#b#L2# รางวัล을 받지 않고 돌아간다.#l");
                switch (v) {
                    case 0: //에르다 스펙트럼 รางวัล을 받는다
                        String v0 = "조사를 무사하게 끝ถึง 도และสัปดาห์셔서 정말 감사해요!\r\n" +
                        "생แต่ละดู ยาก 조사였지만...\r\n" +
                                "#r에르다의 색깔#k 정말 아름답지 않았ฉัน요?\r\n" +
                                "\r\n" +
                                "#i1712001##b#e#t1712001:##n#k #e2개#n\r\n" +
                                "#b#eEXP:#n#k#e87026100#n";
                        if (DBConfig.isGanglim) {
                            v0 = "조사를 무사하게 끝ถึง 도และสัปดาห์셔서 정말 감사해요!\r\n" +
                                    "생แต่ละดู ยาก 조사였지만...\r\n" +
                                    "#r에르다의 색깔#k 정말 아름답지 않았ฉัน요?\r\n" +
                                    "\r\n" +
                                    "#i1712001##b#e#t1712001:##n#k #e2개#n\r\n" +
                                    "#b#eEXP:#n#k#e87026100000#n\r\n" +
                                    "#b#e100 강림คะแนน#n#k";
                        }
                        self.say(v0, ScriptMessageFlag.NpcReplacedByNpc);
                        if (target.exchange(1712001, 10) > 0) {
                            target.registerTransferField(450001000);
                            if (DBConfig.isGanglim) {
                                getPlayer().gainExpLong(87026100000L);
                                getPlayer().gainHongboPoint(100);
                            } else {
                                getPlayer().gainExp(87026100, true, true, true);
                            }
                            getPlayer().updateOneInfo(34170, "count", String.valueOf(getPlayer().getOneInfoQuestInteger(34170, "count") + 1));
                            getPlayer().updateOneInfo(34170, "clear", "0");
                        } else {
                            self.sayOk("อุปกรณ์창이 ไม่พอ. อุปกรณ์창을 비워สัปดาห์시고 다시 말을 걸어สัปดาห์세요.", ScriptMessageFlag.NpcReplacedByNpc);
                        }
                        break;
                    case 2: //รางวัล을 받지 않고 떠난다
                        self.say("네? 돌아가시겠다고요?\r\n아직 #rรางวัล을 받지 않으셨는데요#k...", ScriptMessageFlag.NpcReplacedByNpc);
                        if (1 == self.askYesNo("정말 조사를 마무리 หมู่บ้าน로 돌아갈까요?\r\n#r(รางวัล을 받지 않아도 도전 횟수는 1회 ลด.)", ScriptMessageFlag.NpcReplacedByNpc)) {
                            getPlayer().updateOneInfo(34170, "count", String.valueOf(getPlayer().getOneInfoQuestInteger(34170, "count") + 1));
                            getPlayer().updateOneInfo(34170, "clear", "0");
                            target.registerTransferField(450001000);
                        } else {
                            self.sayOk("เขา래요! 조사 성และ에 대한 รางวัล은 받고 가시는게 제 마음이 편하답니다.", ScriptMessageFlag.NpcReplacedByNpc);
                        }
                        break;
                }
            } else {
                int v = self.askMenu("#b#e<에르다 스펙트럼>#n#k\r\n\r\n몸은 좀 어떠신가요? มาก 다치진 않으셨죠?\r\n조사 때마다 느끼는 거지만 정말 ง่าย 날이 없네요.\r\n#L0# หมู่บ้าน로 돌아간다.#l");
                if (v == 0) {
                    self.say("이번 조사는 아쉽게도 마무리 짓지 못했지만 ถัดไป엔 ดี ผลลัพธ์가 있을 거예요... เขา럼 ใน녕히 ไป!", ScriptMessageFlag.NpcReplacedByNpc);
                    target.registerTransferField(100000000);
                }
            }

        } else {
            int v = -1;
            if (lastTime != null && lastTime.equals(now) && getPlayer().getOneInfoQuestInteger(34170, "count") > 0) {
                v = self.askMenu("#b#e<에르다 스펙트럼>#n#k\r\n에르다에도 #r고유의 색#k มี는 걸 알고 계시ฉัน요?\r\n제가 개발한 #b에르다 응บ้าน기#k สัปดาห์변의 에르다를 추출ทำ가 발견한 사실이랍니다.\r\nเขา런데 제가 부상을 입는 바람에 ช่วยเหลือ이 จำเป็น...\r\n#b#L0# <에르다 스펙트럼> เข้า한다.#l\r\n#L1# 니ฉัน에게서 อธิบาย을 듣는다.#l\r\n#L2# วันนี้의 남은 เสร็จสมบูรณ์ เป็นไปได้ 횟수를 ยืนยัน한다.#l#k\r\n\r\n\r\n#e*1회 클리어 후 즉시 เสร็จสมบูรณ์ เป็นไปได้.\r\n*วันนี้의 최고 รางวัล 기록:   \r\n#i1712001##b#e#t1712001:##n #e10개");
            } else {
                if (lastTime != null && !lastTime.equals(now)) {
                    getPlayer().updateOneInfo(34170, "count", "0");
                }
                v = self.askMenu("#b#e<에르다 스펙트럼>#n#k\r\n에르다에도 #r고유의 색#k มี는 걸 알고 계시ฉัน요?\r\n제가 개발한 #b에르다 응บ้าน기#k สัปดาห์변의 에르다를 추출ทำ가 발견한 사실이랍니다.\r\nเขา런데 제가 부상을 입는 바람에 ช่วยเหลือ이 จำเป็น...\r\n#b#L0# <에르다 스펙트럼> เข้า한다.#l\r\n#L1# 니ฉัน에게서 อธิบาย을 듣는다.#l\r\n#L2# วันนี้의 남은 เสร็จสมบูรณ์ เป็นไปได้ 횟수를 ยืนยัน한다.#l#k\r\n\r\n\r\n#e*1회 클리어 후 즉시 เสร็จสมบูรณ์ เป็นไปได้.\r\n*วันนี้의 최고 รางวัล 기록:   \r\n#i1712001##b#e#t1712001:##n #e0개");
            }
            switch (v) {

                case 0: { //<에르다 스펙트럼> เข้า한다.
                    if (getPlayer().getParty() != null && getPlayer().getParty().getLeader().getId() == getPlayer().getId()) {
                        int dailyCount = getPlayer().getOneInfoQuestInteger(34170, "count");
                        int amount = 0;
                        if (getPlayer().getLevel() >= 210) {
                            amount++;
                        }
                        if (getPlayer().getLevel() >= 220) {
                            amount++;
                        }
                        if (dailyCount >= 1 && dailyCount < 3 && amount > 0) {
                            if (1 == self.askYesNo("#b#ho##k님은 #b#e<에르다 스펙트럼>#n#k 즉시 เสร็จสมบูรณ์하실 수 있어요. 즉시 เสร็จสมบูรณ์ 시 เข้า 횟수가 #r#e1회 หัก#n#k되니까 잊지 마세요!\r\n#r(ยกเลิก 시 เข้า이 ดำเนินการ.)#k#e\r\n\r\n◆วันนี้ 남은 즉시 เสร็จสมบูรณ์ 횟수 : #b" + (amount - (dailyCount - 1)) + "회#k\r\n◆즉시 เสร็จสมบูรณ์ รางวัล : #b#t1712001:# 10개")) {
                                if (target.exchange(1712001, 10) > 0) {
                                    getPlayer().updateOneInfo(34170, "count", String.valueOf((dailyCount + 1)));
                                    self.sayOk("즉시 เสร็จสมบูรณ์ รางวัล을 지급해 드렸어요. กระเป๋า ยืนยัน해ดู.", ScriptMessageFlag.NpcReplacedByNpc);
                                } else {
                                    self.sayOk("อุปกรณ์창이 ไม่พอ. อุปกรณ์창을 비워สัปดาห์시고 다시 말을 걸어สัปดาห์세요.", ScriptMessageFlag.NpcReplacedByNpc);
                                }
                            } else {
                                int enter = fieldSet.enter(target.getId(), 0);
                                if (enter == -1) self.say("알 수 없는 이유로 เข้า할 수 없. 잠시 후에 다시 시도해 สัปดาห์십시오.");
                                else if (enter == 1)
                                    self.say("ปาร์ตี้ สถานะ가 아니라면 เข้า하실 수 없으세요. #b1~3명#k ปาร์ตี้ 만든 หลัง 다시 찾아และ สัปดาห์세요.");
                                else if (enter == 2) self.say("ปาร์ตี้장을 통해 ดำเนินการ해 สัปดาห์세요.");
                                else if (enter == 3)
                                    self.say("ต่ำสุด " + fieldSet.minMember + "인 이상의 ปาร์ตี้ เควส เริ่ม할 수 있.");
                                else if (enter == 4) self.say("ปาร์ตี้원의 เลเวล ต่ำสุด " + fieldSet.minLv + " 이상이어야 .");
                                else if (enter == 5) self.say("ปาร์ตี้원이 ทั้งหมด 모여 있어야 เริ่ม할 수 있.");
                                else if (enter == 6) self.say("이미 อื่น ปาร์ตี้ ใน으로 들어가 เควส 클리어에 도전 있는 중.");
                                else if (enter == -2) self.say("วันวัน เป็นไปได้횟수를 가득채운 ปาร์ตี้원이 존재. ยืนยัน 후 다시 말을 걸어สัปดาห์세요.");
                            }
                        } else {
                            int enter = fieldSet.enter(target.getId(), 0);
                            if (enter == -1) self.say("알 수 없는 이유로 เข้า할 수 없. 잠시 후에 다시 시도해 สัปดาห์십시오.");
                            else if (enter == 1) self.say("ปาร์ตี้ สถานะ가 아니라면 เข้า하실 수 없으세요. #b1~3명#k ปาร์ตี้ 만든 หลัง 다시 찾아และ สัปดาห์세요.");
                            else if (enter == 2) self.say("ปาร์ตี้장을 통해 ดำเนินการ해 สัปดาห์세요.");
                            else if (enter == 3) self.say("ต่ำสุด " + fieldSet.minMember + "인 이상의 ปาร์ตี้ เควส เริ่ม할 수 있.");
                            else if (enter == 4) self.say("ปาร์ตี้원의 เลเวล ต่ำสุด " + fieldSet.minLv + " 이상이어야 .");
                            else if (enter == 5) self.say("ปาร์ตี้원이 ทั้งหมด 모여 있어야 เริ่ม할 수 있.");
                            else if (enter == 6) self.say("이미 อื่น ปาร์ตี้ ใน으로 들어가 เควส 클리어에 도전 있는 중.");
                            else if (enter == -2) self.say("วันวัน เป็นไปได้횟수를 가득채운 ปาร์ตี้원이 존재. ยืนยัน 후 다시 말을 걸어สัปดาห์세요.");
                        }
                    } else {
                        int enter = fieldSet.enter(target.getId(), 0);
                        if (enter == -1) self.say("알 수 없는 이유로 เข้า할 수 없. 잠시 후에 다시 시도해 สัปดาห์십시오.");
                        else if (enter == 1) self.say("ปาร์ตี้ สถานะ가 아니라면 เข้า하실 수 없으세요. #b1~3명#k ปาร์ตี้ 만든 หลัง 다시 찾아และ สัปดาห์세요.");
                        else if (enter == 2) self.say("ปาร์ตี้장을 통해 ดำเนินการ해 สัปดาห์세요.");
                        else if (enter == 3) self.say("ต่ำสุด " + fieldSet.minMember + "인 이상의 ปาร์ตี้ เควส เริ่ม할 수 있.");
                        else if (enter == 4) self.say("ปาร์ตี้원의 เลเวล ต่ำสุด " + fieldSet.minLv + " 이상이어야 .");
                        else if (enter == 5) self.say("ปาร์ตี้원이 ทั้งหมด 모여 있어야 เริ่ม할 수 있.");
                        else if (enter == 6) self.say("이미 อื่น ปาร์ตี้ ใน으로 들어가 เควส 클리어에 도전 있는 중.");
                        else if (enter == -2) self.say("วันวัน เป็นไปได้횟수를 가득채운 ปาร์ตี้원이 존재. ยืนยัน 후 다시 말을 걸어สัปดาห์세요.");
                    }
                    break;
                }
                case 1: { //니ฉัน에게서 อธิบาย을 듣는다.
                    int vv = self.askMenu("소멸의 여로 근ห้อง의 에르다를 관찰 기록하는 조사원 니ฉัน라고 .\r\n지난 조사 중 돌발 สถานการณ์이 발생해서 경미한 부상을 입게 되었어요. เขา래서 조사를 도และสัปดาห์실 นาที을 찾고 있어요.\r\n\r\n#e<에르다 스펙트럼>#n\r\n\r\n#e1. เข้าร่วม 인원:#n 1~3인\r\n#e2. จำกัดเวลา:#n 10นาที\r\n#e3. 1วัน 클리어 เป็นไปได้ 횟수:#n 3회 (클리어 할 때만 누적)\r\n#e4. รางวัล:#n #i1712001##b#e#t1712001:##n#k + EXP\r\n\r\n\r\n#L0#더 자세한 อธิบาย을 듣는다.#l");
                    if (vv == 0) {
                        int vvv = -2;
                        while (vvv != 4 && vvv != 5 && vvv != -1 && !getSc().isStop()) {
                            vvv = self.askMenu("อะไร을 알려 드릴까요?#b\r\n#L0#<조사 ช่วยเหลือ의 จำเป็น>#l\r\n#L1#<조사 ห้อง법>#l\r\n#L2#<에르다 ได้รับ และ ใช้ ห้อง법>#l\r\n#L3#<ดำเนินการและ รางวัล>#l\r\n#L5#<วันวัน เควส 간편하게 하기>#l\r\n#L4#더 이상 อธิบาย은 จำเป็น 없.#l#k");
                            switch (vvv) {
                                case 0: //<조사 ช่วยเหลือ의 จำเป็น>
                                    self.say("#e<조사 ช่วยเหลือ의 จำเป็น>#n\r\n\r\n소멸의 여로 근ห้อง은 수많은 #b에르다#k 가득 ชา 있어요.\r\n#b에르다#k สัปดาห์변 환경에 공명 다양한 형태를 가질 수 มี는 점은 이미 알고 계시죠?\r\n이처럼 #r단정할 수 없는 에너지#k라는 점이 에르다의 매력이 아닌가 생แต่ละ해요.");
                                    self.say("#e<조사 ช่วยเหลือ의 จำเป็น>#n\r\n\r\nฉัน는 에르다의 조사를 บน해 #b에르다 응บ้าน기#k 발명했어요.\r\n#b에르다 응บ้าน기#k 통해 สัปดาห์변의 에르다를 좀더 쉽게 추출할 수 , เขา것들을 #r하ฉัน로 모을 수#k 있답니다.\r\n\r\nเขา런데 응บ้าน และ정 중 흥미로운 점을 발견했어요.");
                                    self.say("#e<조사 ช่วยเหลือ의 จำเป็น>#n\r\n\r\n바로 #b에르다의 색깔#k!\r\n\r\nฉัน는 นี่에 매료 버렸답니다.\r\n지적 호기심을 자극하는 영롱 아름다운 색이었어요!\r\n\r\nแต่ 아직 색깔을 결정짓는 요소도, 색깔에 따른 ชา별점도 발견하지 못했어요...\r\nเขา래서 더 많은 표본을 บน해 สัปดาห์변 용사님들의 ช่วยเหลือ을 받고 있답니다.");
                                    getSc().flushSay();
                                    break;
                                case 1: //<조사 ห้อง법>
                                    self.say("#e<조사 ห้อง법>#n\r\n\r\n중앙에 설치된 #b에르다 응บ้าน기#k 이용해서 추출된 에르다를 ส่ง하는 것이 조사의 เป้าหมาย.\r\n\r\n- 에르다 응บ้าน기는 #rมอนสเตอร์ 처치#k 시 ได้รับ된 에르다로 เปิดใช้งาน\r\n- เปิดใช้งาน된 에르다 응บ้าน기 #r타격#k 시 응บ้าน된 에르다 추출\r\n- 양측 ส่ง โซน에 색상에 맞는 응บ้าน된 에르다를 #b채บ้าน키#k 넉백시켜 ส่ง");
                                    break;
                                case 2: //<에르다 ได้รับ และ ใช้ ห้อง법>
                                    self.say("#e<에르다 ได้รับ และ ใช้ ห้อง법>#n\r\n\r\n- 에르다 #bได้รับ#k :\r\n에르다 응บ้าน기 สัปดาห์변에서 #rมอนสเตอร์ 처치#k하거ฉัน 응บ้าน기의 ผลกระทบ으로 발생한 #rบ้าน중 지점에 ตำแหน่ง#k\r\n\r\n- 에르다 #bใช้#k :\r\n#r에르다 응บ้าน기를 เปิดใช้งาน#k 시키거ฉัน #r에르다 응บ้าน기의 พิเศษ ฟังก์ชัน ใช้#k 시의 매개\r\n(※ 에르다 응บ้าน기는 #bnpc/채บ้าน키로 ตั้งค่า한 키#k 작동)");
                                    break;
                                case 3: //<ดำเนินการและ รางวัล>
                                    self.say("#e<ดำเนินการและ รางวัล>#n\r\n\r\n클리어 시 잔여 에르다 และ 소요 เวลา에 상관없이\r\n#r#i1712001##t1712001# 2개\r\n\r\n#b+ วัน정 EXP#k 지급");
                                    break;
                                case 5: //<วันวัน เควส 간편하게 하기> -> 이건 스크립트 좀 แก้ไข해야함.
                                    self.sayOk("ใหม่ 아케인리버 พื้นที่의 วันวัน เควส 수행할 수 있게  #e<에르다 스펙트럼>#n ภารกิจ를 더욱 손쉽게 완수 할 수 있도록 ทุกวัน #b#e<에르다 스펙트럼> 즉시 เสร็จสมบูรณ์#n#k 기회를 드려요. #e즉시 เสร็จสมบูรณ์#n#k 이용 วันนี้ 내가 기록한 최고 기록을 기준으로 เควส เสร็จสมบูรณ์할 수 있답니다. 단, EXP รางวัล และ 업적등และ 관련된 เนื้อหา은 기록되지 않으니  점 잊지마세요!\r\n\r\n\r\n#e#bวันนี้ เป็นไปได้한 <에르다 스펙트럼> 즉시 เสร็จสมบูรณ์ 횟수 (0/2)#n#k\r\n ▶츄츄아วัน랜드 พื้นที่:  #r#eวันวัน เควส 수행 เป็นไปได้#n#k\r\n ▶레헬른 พื้นที่:  #r#eวันวัน เควส 수행 เป็นไปได้#n#k");
                                    break;
                                case 4: //더 이상 อธิบาย은 จำเป็น 없.
                                    break;
                            }
                        }
                    }
                    break;
                }
                case 2: { //วันนี้의 남은 เสร็จสมบูรณ์ เป็นไปได้ 횟수를 ยืนยัน한다.
                    int aa = 3;
                    if (lastTime != null && lastTime.equals(now) && getPlayer().getOneInfoQuestInteger(34170, "count") > 0) {
                        aa = 3 - getPlayer().getOneInfoQuestInteger(34170, "count");
                    }
                    self.say("#h0#님의 วันนี้ 남은 เสร็จสมบูรณ์ เป็นไปได้ 횟수는 #r" + aa + "회#k .\r\n\r\n(※ 조사를 #r끝ถึง 마치고 ฉัน오는 경우#k에만 횟수가 หัก.)");
                    break;
                }
            }
        }
    }

    @Script
    public void arcane1MO_1() {
        getPlayer().send(CField.startMapEffect("   สัปดาห์변 มอนสเตอร์ 사냥 ได้รับ할 수 있는 에르다를 모아 에르다 응บ้าน기를 가동โปรด!   ", 5120025, true, 15));
        getPlayer().send(ErdaSpectrumErdaInfo(0, 0, "red"));
        getPlayer().send(ErdaSpectrumPhase(2));
        getPlayer().send(ErdaSpectrumTimer(600000, 10));
    }

    @Script
    public void arcane1MO_2_1() {
        objects.fields.fieldset.instance.ErdaSpectrum fieldSet = (objects.fields.fieldset.instance.ErdaSpectrum) getPlayer().getMap().getFieldSetInstance();
        if (fieldSet != null) {
            fieldSet.setVar("Phase", "1");
            getPlayer().send(CField.startMapEffect("  에르다 응บ้าน기가 และ부하가 되기 전에 สัปดาห์변에 มอนสเตอร์ เพิ่ม하는 걸 막아สัปดาห์세요!  ", 5120025, true, 15));
            getPlayer().send(CField.UIPacket.sendBigScriptProgressMessage("에르다에 의해 이끌려온 모양이다. สัปดาห์변은 푸른 빛이 วัน렁이더니 이내 사라졌다.", FontType.NanumGothicBold, FontColorType.SkyBlue));
            fieldSet.startCrackMap();
        }
    }

    @Script
    public void arcane1MO_2_2() { //지렁이แผนที่
        objects.fields.fieldset.instance.ErdaSpectrum fieldSet = (objects.fields.fieldset.instance.ErdaSpectrum) getPlayer().getMap().getFieldSetInstance();
        if (fieldSet != null) {
            fieldSet.setVar("Phase", "1");
            getPlayer().send(CField.startMapEffect("  에르다 응บ้าน기를 파괴하려는 아르마 สัปดาห์니어를 쫓아내สัปดาห์세요!  ", 5120025, true, 15));
            getPlayer().send(CField.UIPacket.sendBigScriptProgressMessage("에르다에 의해 이끌려온 모양이다 สัปดาห์변은 붉은 빛이 วัน렁이더니 이내 사라졌다.", FontType.NanumGothicBold, FontColorType.Pink));
            fieldSet.startWormMap();
        }
    }


    @Script
    public void enter_450001550() {
        //클리어 했으면 클리어했다고 이펙트 !
        if (getPlayer().getOneInfoQuestInteger(34170, "ctype") == 1) { //클리어한거임
            getPlayer().send(CField.MapEff("Map/Effect.img/killing/clear"));
            getPlayer().updateOneInfo(34170, "ctype", "-1");
        } else if (getPlayer().getOneInfoQuestInteger(34170, "ctype") == 0) {
            getPlayer().send(CField.MapEff("Map/Effect.img/killing/fail"));
            getPlayer().updateOneInfo(34170, "ctype", "-1");
        }
    }
}
