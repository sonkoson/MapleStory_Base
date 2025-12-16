package script.Quest;

import network.models.CField;
import network.models.CWvsContext;
import objects.effect.EffectHeader;
import objects.effect.NormalEffect;
import objects.item.Equip;
import objects.item.Item;
import objects.item.MapleInventoryType;
import objects.item.MapleItemInformationProvider;
import objects.quest.MapleQuest;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StepUp extends ScriptEngineNPC {

    public void q100865s() { //스텝업 เริ่ม퀘
        self.say("\r\nใน녕!\r\n넌 #b강림 เดือน드#k 도착한 지 เท่าไหร่ ใน 된 #bใหม่ 용사#k구ฉัน?", ScriptMessageFlag.NpcReplacedByNpc);
        self.say("\r\n반가워! 내 이름은 #b카산드라#k야!\r\n\r\n메이플 เดือน드 최고의 #b점성술사#k이자 너และ ด้วยกัน 메이플 เดือน드를 새롭게 찾은 용사들을 บน한 #bแนะนำ자#k야!", ScriptMessageFlag.NpcReplacedByNpc);
        self.say("\r\n아직 온 지 เท่าไหร่ ใน 돼서 นี่โน่น 모르겠는게 많다고?\r\n\r\nวันนี้은 และ 어떤 มอนสเตอร์ 사냥해야 할지\r\n육성은 และ อย่างไร 해야 좋을지?", ScriptMessageFlag.NpcReplacedByNpc);
        self.say("\r\n#b고민고민하지 마~!#k\r\n\r\nฉัน #b카산드라#k님이 새내기 용사들의 성장을 도และ줄\r\n#b#e<강림 เดือน드 스텝업>#n#k 이벤트를 เตรียม했으니까 말이야~!", ScriptMessageFlag.NpcReplacedByNpc);
        self.say("\r\n특정 เลเวล마다 สัปดาห์어지는 ภารกิจ으로 #b스텝업~!#k\r\nภารกิจ เสร็จสมบูรณ์마다 สัปดาห์어지는 รางวัล으로 #b스펙업~!#k", ScriptMessageFlag.NpcReplacedByNpc);
        if (1 == self.askYesNo("어때? 메이플 เดือน드의 핵심 요소를 체계적으로 알려줄\r\n#b#e<강림 เดือน드 스텝업>#n#k 이벤트, 너도 เข้าร่วม해 보지 않을래?", ScriptMessageFlag.NpcReplacedByNpc)) {
            for (int i = 501525; i <= 50155; i++) {
                getPlayer().updateInfoQuest(i, "");
            }
            getPlayer().updateInfoQuest(100865, "");
            SimpleDateFormat sdf = new SimpleDateFormat("YY/MM/dd/HH/mm;");
            String timeToString = sdf.format(new Date());
            getPlayer().updateInfoQuest(501524, "reward=00000000000000000000000000000;step=0;sTime=" + timeToString + "state=3");
            getPlayer().updateInfoQuest(501527, "value=0");
            getPlayer().updateInfoQuest(501540, "value=0");
            getPlayer().updateInfoQuest(501541, "value=0");
            getPlayer().updateInfoQuest(501524, "reward=00000000000000000000000000000;start=1;step=0;sTime=" + timeToString + "state=3");
            getQuest().forceComplete(getPlayer(), getNpc().getId());
            self.say("\r\n잘 생แต่ละ했어~!\r\n후회 없는 เลือก 될 거야~!", ScriptMessageFlag.NpcReplacedByNpc);
            self.say("\r\n이제 특정 เลเวล마다 #b스텝업 ภารกิจ#k ดำเนินการ할 수 있어!\r\n\r\nภารกิจและ ดำเนินการ สถานะ를 ยืนยัน 싶을 때는 왼쪽 이벤트 알림이에서 #b#e스텝업 아이콘#n#k 클릭 돼!", ScriptMessageFlag.NpcReplacedByNpc);
            //self.say("\r\n#b#e<버닝 เดือน드 스텝업>#n#k\r\n#b#e2021ปี 9เดือน 9วัน(목) 점검 전#n#kถึง ดำเนินการ할 수 있어.\r\n\r\nเขา 이후에는 ภารกิจ을 달성하거ฉัน รางวัล을 받을 수 없으니\r\nสัปดาห์의하도록 해! 알았지~?", ScriptMessageFlag.NpcReplacedByNpc);
            self.say("\r\nอธิบาย은 ที่นี่ถึง야, 간단하지?\r\n\r\nเขา럼 #b#e<강림 เดือน드 스텝업>#n#k 신ฉัน는 모험되ถนน 바랄게~!\r\nหน้า으로 이루어질 너의 멋진 성장에 #b치얼스#k~#r♥#k", ScriptMessageFlag.NpcReplacedByNpc);
            getSc().flushSay();
            getPlayer().send(CField.UIPacket.openUI(1160));

            NormalEffect e = new NormalEffect(getPlayer().getId(), EffectHeader.QuestClear);
            getPlayer().send(e.encodeForLocal());
        } else {
            self.say("\r\n스스로 개척하ถนน 좋아하는 #b진취적인 스타วัน#k이구ฉัน!\r\n\r\nแต่ 때로는 อื่น คน의 #b노하우#k #bช่วยเหลือ#k 통해\r\n#b더 ใหญ่ 성장#k 이루어 낼 때도 있어!", ScriptMessageFlag.NpcReplacedByNpc);
            //self.say("\r\n#b<버닝 เดือน드 스텝업>#k #b2021ปี 9เดือน 9วัน(목) 점검 전#kถึง\r\nเข้าร่วม할 수 있으니까 혹시 마음이 바뀌면 เมื่อไหร่든지 말을 걸어줘~!\r\n\r\n난 เมื่อไหร่ฉัน  자리에서 널 기다리고 있을게!", ScriptMessageFlag.NpcReplacedByNpc);
        }
    }

    public void StepUp_1() {
        /*
            self.say("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.", ScriptMessageFlag.NpcReplacedByNpc);
            self.say("\r\nฉัน는 #b#h0##k님의 #b스텝업#k ดำเนินการ을 돕게 될\r\n#b<스텝업 골>#k이라고 .\r\n\r\n이름에서 알 수 있듯이 스텝업에서 가장 สำคัญ\r\n#b<GOAL>칸#k 담당 있.", ScriptMessageFlag.NpcReplacedByNpc);
            self.say("\r\n#b#h0##k님을 #b한 스텝 한 스텝#k 발전하실 수 있도록 돕는 것이 #bฉัน의 ภารกิจ#k.\r\n\r\n#b스텝업#k 기간 동ใน 잘 부탁드리겠.", ScriptMessageFlag.NpcReplacedByNpc);
            self.say("\r\nเขา럼 첫 번째 스텝을 เริ่ม하겠.\r\n\r\n첫 번째 스텝에서 알아볼 เนื้อหา은\r\n#b#e<เลเวล 범บน มอนสเตอร์>#n#k.", ScriptMessageFlag.NpcReplacedByNpc);
            self.say("\r\n성장의 พื้นฐาน은 역시 #bมอนสเตอร์ 사냥#k이지요.\r\n\r\nเขา런데 EXP 높다고 무เงื่อนไข #rสูง เลเวล มอนสเตอร์#k\r\n사냥하는 건 ดี ห้อง법이 아닙니다.", ScriptMessageFlag.NpcReplacedByNpc);
            self.say("\r\nเลเวล ชา이가 #b10เลเวล 미만#k인 มอนสเตอร์ 사냥\r\nเลเวลชา에 따라 #bEXP 보너스#k 얻으실 수 있.\r\n\r\n반대로 #r10เลเวล วินาทีและ#k인 มอนสเตอร์ 사냥\r\nเลเวลชา에 따라 นิดหน่อย씩 #rEXP 패널티#k 받게 .", ScriptMessageFlag.NpcReplacedByNpc);
            self.say("\r\nถ้า เลเวล ชา이가 #r20เลเวล 이상#k이면 사냥하기 몹시 힘들 뿐만 아니라 #rใหญ่ 폭의 EXP 패널티#kถึง 받게 .\r\n\r\n오히려 #b사냥 효율#k 떨어질 수 있지요.", ScriptMessageFlag.NpcReplacedByNpc);
            self.say("\r\nดังนั้น 사냥하기 พอดี한 มอนสเตอร์ เลเวล\r\n자신และ เลเวล ชา이 #b20เลเวล 이하#k มอนสเตอร์라고 볼 수 있.\r\n\r\nเขา리고 이런 มอนสเตอร์들을\r\n바로 #b<เลเวล 범บน มอนสเตอร์>#k라고 부릅니다.", ScriptMessageFlag.NpcReplacedByNpc);
            self.say("\r\n#b[첫 번째 스텝업 ภารกิจ]#k\r\n#b#e<เลเวล 범บน มอนสเตอร์ 300마리 사냥>#n#k.\r\n\r\nเลเวล ชา이 20เลเวล 이하의 มอนสเตอร์ 있는\r\nพอดี한 사냥터를 찾아 300마리 사냥해 보시기 โปรด.", ScriptMessageFlag.NpcReplacedByNpc);
            self.say("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!", ScriptMessageFlag.NpcReplacedByNpc);
         */
        //public void check_step_up(int sLevel, int questID, List<String> questSay, int reward, int rewardqty, String rewardString, String mission) {
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\nฉัน는 #b#h0##k님의 #b스텝업#k ดำเนินการ을 돕게 될\r\n#b<스텝업 골>#k이라고 .\r\n\r\n이름에서 알 수 있듯이 스텝업에서 가장 สำคัญ\r\n#b<GOAL>칸#k 담당 있.");
        questSay.add("\r\n#b#h0##k님을 #b한 스텝 한 스텝#k 발전하실 수 있도록 돕는 것이 #bฉัน의 ภารกิจ#k.\r\n\r\n#b스텝업#k 기간 동ใน 잘 부탁드리겠.");
        questSay.add("\r\nเขา럼 첫 번째 스텝을 เริ่ม하겠.\r\n\r\n첫 번째 스텝에서 알아볼 เนื้อหา은\r\n#b#e<광장 명령어 ใช้>#n#k.");
        questSay.add("\r\nMMORPG พื้นฐาน은 유ฉัน들간의 만남이겠죠. อื่น 유ฉัน들และ 쉽게 소통할 수 있는 공간(광장) 쉽게 ย้าย할 수 있는 명령어");
        questSay.add("\r\n#r@광장#k ใช้ ห้อง법을 아시ฉัน요? 채팅을통해 #r@광장#k 명령어를 ใช้하게  서버내 พิเศษ 공간! 쉽게 ย้าย할 수 있게 !\r\n광장 명령어를 ใช้해 보시기 โปรด.");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        check_step_up(33, 501525, questSay, 2439580, 1, "10000000000000000000000000000", "<광장 명령어 ใช้>");
    }

    public void StepUp_2() {
        int level = 35;
        int questID = 501526;
        int reward = 2450042;
        int rewardQTY = 2;
        String rewardString = "11000000000000000000000000000";
        String mission = "<40 เลเวล 달성>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\n두 번째 스텝업은 40Lv 달성하기 .");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_3() {
        int level = 40;
        int questID = 501527;
        int reward = 2000019;
        int rewardQTY = 500;
        String rewardString = "11100000000000000000000000000";
        String mission = "<45 เลเวล 달성>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\n세 번째 스텝업은 45Lv 달성하기 .");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_4() {
        int level = 45;
        int questID = 501528;
        int reward = 2439581;
        int rewardQTY = 1;
        String rewardString = "11110000000000000000000000000";
        String mission = "<룬 ใช้>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\n네 번째 스텝업은 룬 ใช้하기.");
        questSay.add("\r\n사냥필드를 돌아다니다 보면 #r#e룬#n#k 발견하실 수 있으실겁니다. 룬을 ใช้하게  룬의 แต่ละ종 เอฟเฟกต์ และ EXP 2배 Buff 걸리게 ");
        questSay.add("\r\n필드에 소환된 룬을 ใช้하지 않고 วัน정เวลา이 지ฉัน게  사냥터에 ในดี 패널티가 부여되게 .");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_5() {
        int level = 50;
        int questID = 501529;
        int reward = 5076100;
        int rewardQTY = 5;
        String rewardString = "11111000000000000000000000000";
        String mission = "<55Lv 달성>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\n다섯 번째 스텝업은 55Lv 달성하기 .");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_6() {
        //รางวัล สัปดาห์ประตู의흔적으로
        int level = 55;
        int questID = 501530;
        int reward = 4001832;
        int rewardQTY = 9000;
        String rewardString = "11111100000000000000000000000";
        String mission = "<60Lv 달성>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\n여섯 번째 스텝업은 60Lv 달성하기 .");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_7() {
        int level = 60;
        int questID = 501531;
        int reward = 2450042;
        int rewardQTY = 2;
        String rewardString = "11111110000000000000000000000";
        String mission = "<อัพเกรด เสริมแรง>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\nเขา럼 วัน곱 번째 스텝을 เริ่ม하겠.\r\n\r\nวัน곱 번째 스텝에서 알아볼 เนื้อหา은\r\n#b#e<อัพเกรด เสริมแรง>#n#k.");
        questSay.add("\r\nกระเป๋า(I) 버튼을 누르게  #r빨간색 망치버튼#k 발견하실 수 있으실 겁니다.");
        questSay.add("\r\n빨간색 버튼을 누르게  เสริมแรงUI 등장하게 되는데  UI 통해 เสริมแรง를 ดำเนินการ할 수 있.");
        questSay.add("\r\nเสริมแรง에 จำเป็น한 재료는 #bสัปดาห์ประตู의 흔적#k ไอเท็ม เลเวลจำกัด이 높을수록 จำเป็น한 สัปดาห์ประตู의 흔적이 늘어ฉัน게  #bสัปดาห์ประตู의 흔적#k 여러 경로(대표적으로 사냥) 통해 ได้รับ 하실 수 있으실 겁니다!");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_8() {
        //รางวัล วัน반 Meso 럭키백 2개
        int level = 65;
        int questID = 501532;
        int reward = 2433019;
        int rewardQTY = 2;
        String rewardString = "11111111000000000000000000000";
        String mission = "<70Lv 달성>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\n여덟 번째 스텝업은 70Lv 달성하기 .");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_9() {
        int level = 70;
        int questID = 501533;
        int reward = 2450042;
        int rewardQTY = 2;
        String rewardString = "11111111100000000000000000000";
        String mission = "<스타포스 เสริมแรง>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\nเขา럼 아홉 번째 스텝을 เริ่ม하겠.\r\n\r\n아홉 번째 스텝에서 알아볼 เนื้อหา은\r\n#b#e<스타포스 เสริมแรง>#n#k.");
        questSay.add("\r\nวัน곱 번째 스텝업에서 배운 ไอเท็ม เสริมแรง하기를 기억 하시ฉัน요? 스타포스 เสริมแรง 역시 빨간망치버튼(เสริมแรง하기버튼) 통해 ดำเนินการ할 수 있.");
        questSay.add("\r\nสัปดาห์ประตู의 흔적으로 อัพเกรด เสริมแรง가 전부다된 ไอเท็ม เสริมแรง UI 올리게  스타포스 เสริมแรง를 시도할 수 있게되는데 이를 ดำเนินการ하기 บน해서는 วัน정량의 Meso จำเป็น.");
        questSay.add("\r\n 역시 อัพเกรด เสริมแรงและ 마찬가지로 เลเวลจำกัด이 สูง ไอเท็มวัน수록 더 많은 Meso จำเป็น로 ต้องการ하게  เสริมแรง สถานะ에따라 파괴โอกาส 존재하게 되니 สัปดาห์의하시기 โปรด!");
        questSay.add("\r\n아홉 번째 스텝업은 스타포스 เสริมแรง를 1회 시도하시면 !");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_10() {
        int level = 75;
        int questID = 501534;
        int reward = 2439582;
        int rewardQTY = 1;
        String rewardString = "11111111110000000000000000000";
        String mission = "<엘리트 มอนสเตอร์, 엘리트 챔피언>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\nเขา럼 열 번째 스텝을 เริ่ม하겠.\r\n\r\n열 번째 스텝에서 알아볼 เนื้อหา은\r\n#b#e<엘리트 มอนสเตอร์, 엘리트 챔피언>#n#k.");
        questSay.add("\r\nเลเวล 맞는 사냥 필드에서 오랜เวลา 사냥ทำ보면 วัน반 มอนสเตอร์ดู 크기가 더 ใหญ่ #r엘리트 มอนสเตอร์#k 등장하게 .");
        questSay.add("\r\n#r엘리트 มอนสเตอร์#k วัน반 มอนสเตอร์ดู HP이 많은대신 다양한 รางวัลไอเท็ม들이 드랍되게 .");
        questSay.add("\r\n열 번째 스텝업은 엘리트 มอนสเตอร์ 1회 사냥하시면 .");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_11() {
        int level = 80;
        int questID = 501535;
        int reward = 5044008;
        int rewardQTY = 1;
        String rewardString = "11111111111000000000000000000";
        String mission = "<90Lv 달성>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\n열한 번째 스텝업은 90Lv 달성하기 .");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_12() {
        int level = 90;
        int questID = 501536;
        int reward = 2000019;
        int rewardQTY = 500;
        String rewardString = "11111111111100000000000000000";
        String mission = "<100Lv 달성>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\n열두 번째 스텝업은 100Lv 달성하기 .");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_13() {
        int level = 100;
        int questID = 501537;
        int reward = 2439609;
        int rewardQTY = 1;
        String rewardString = "11111111111110000000000000000";
        String mission = "<101Lv 달성>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\n열세 번째 스텝업은 101Lv 달성하기 .");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_14() {
        int level = 101;
        int questID = 501538;
        int reward = 2023836;
        int rewardQTY = 10;
        String rewardString = "11111111111111000000000000000";
        String mission = "<이벤트로 스텝업>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\nเขา럼 열네 번째 스텝을 เริ่ม하겠.\r\n\r\n열네 번째 스텝에서 알아볼 เนื้อหา은\r\n#b#e<이벤트 UIยืนยัน하기>#n#k.");
        questSay.add("\r\n강림 서버는 이벤트(V)키를 통해 ดำเนินการ중인 이벤트 리스트를 ยืนยัน할 수 있던거 알고 계셨ฉัน요?");
        questSay.add("\r\n다양한 이벤트 วัน정 และ 이벤트로 얻을 수 있는 ไอเท็ม들을 ยืนยัน할 수 있.");
        questSay.add("\r\n열네 번째 스텝업은 이벤트(V)키를 이용해 이벤트 UI ยืนยัน해 ดู.");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_15() {
        int level = 105;
        int questID = 501539;
        int reward = 2644017;
        int rewardQTY = 1;
        String rewardString = "11111111111111100000000000000";
        String mission = "<107Lv 달성>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\nเปิด섯 번째 스텝업은 107Lv 달성하기 .");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_16() {
        int level = 107;
        int questID = 501540;
        int reward = 2437090;
        int rewardQTY = 1;
        String rewardString = "11111111111111110000000000000";
        String mission = "<110Lv 달성>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\n열여섯 번째 스텝업은 110Lv 달성하기 .");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_17() {
        int level = 110;
        int questID = 501541;
        int reward = 2433019;
        int rewardQTY = 2;
        String rewardString = "11111111111111111000000000000";
        String mission = "<มอนสเตอร์ 파크>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\nเขา럼 열วัน곱 번째 스텝을 เริ่ม하겠.\r\n\r\n열วัน곱 번째 스텝에서 알아볼 เนื้อหา은\r\n#b#e<มอนสเตอร์ 파크>#n#k.");
        questSay.add("\r\nมอนสเตอร์ 파크는 막대한 EXP และ 요วัน별 다양한 รางวัล을 얻을 수 있는 컨텐츠 .");
        questSay.add("\r\nมอนสเตอร์ 파크는 컨텐츠 ระบบ을 통해 이용하실 수  권장เลเวล 200เลเวล 이상.");
        questSay.add("\r\nมอนสเตอร์ 파크를 많은 이용 바라며 이번 스텝업은 클리어 เงื่อนไข없이 ภารกิจ เสร็จสมบูรณ์버튼을 누르시면 .");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_18() {
        int level = 119;
        int questID = 501542;
        int reward = 2000019;
        int rewardQTY = 500;
        String rewardString = "11111111111111111100000000000";
        String mission = "<130Lv 달성>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\n열여덟 번째 스텝업은 130Lv 달성하기 .");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_19() {
        int level = 130;
        int questID = 501543;
        int reward = 2450042;
        int rewardQTY = 2;
        String rewardString = "11111111111111111110000000000";
        String mission = "<스타포스 45성>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\nเขา럼 열아홉 번째 스텝을 เริ่ม하겠.\r\n\r\n열아홉 번째 스텝에서 알아볼 เนื้อหา은\r\n#b#e<스타포스 45성>#n#k.");
        questSay.add("\r\n아홉 번째 스텝업에서 배웠던 스타포스 เสริมแรง를 기억하시ฉัน요? 다양한 사냥터를 이용하기 บน해, 더 강해지기 บน해서는 더 สูง 스타포스를 ต้องการ .");
        questSay.add("\r\n열아홉 번째 스텝업은 장착 있는 อุปกรณ์의 스타포스 총 수치가 45 넘어보자 .");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        int chuk = 0;
        for (Item item : getPlayer().getInventory(MapleInventoryType.EQUIPPED).list()) {
            Equip equip = (Equip) item;
            if (equip != null) {
                chuk += equip.getCHUC();
            }
        }
        if (chuk >= 45) {
            if (getPlayer().isQuestStarted(501543)) {
                if (getPlayer().getOneInfoQuestInteger(501543, "value") < 1) {
                    getPlayer().updateOneInfo(501543, "value", "1");
                }
                if (getPlayer().getOneInfoQuestInteger(501524, "state") < 2) {
                    getPlayer().updateOneInfo(501524, "state", "2");
                }
            }
        }
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_20() {
        int level = 140;
        int questID = 501544;
        int reward = 2631822;
        int rewardQTY = 1;
        String rewardString = "11111111111111111111000000000";
        String mission = "<노멀 บอส>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\n드디어 스무 번째 스텝업 ! 축하.");
        questSay.add("\r\nเขา럼 스무 번째 스텝을 เริ่ม하겠.\r\n\r\n스무 번째 스텝에서 알아볼 เนื้อหา은\r\n#b#e<노멀 บอส>#n#k.");
        questSay.add("\r\n메이플스토리에는 다양한 บอส 존재 . เขา 중에서도 대표되는 บอส #b자쿰#k 아시ฉัน요?");
        questSay.add("\r\n자쿰은 노말 โหมด, 카오스 โหมด가 존재 이번 스무 번째 스텝업에서 잡아볼 บอส มอนสเตอร์ 노말 자쿰!");
        questSay.add("\r\nบอสUI(T) 단축키를 이용해 บอส เข้า할 수  บอส 클리어 여부และ한 ยืนยัน เป็นไปได้.");
        questSay.add("\r\n스무 번째 스텝업은 노말 자쿰 처치하기 !");
        questSay.add("\r\n아참 혹시라도 카오스 자쿰을 격파해보고 싶다면 카오스 자쿰을 잡아도 เควส เสร็จสมบูรณ์ เป็นไปได้하니 카오스 자쿰에 도전하실 นาที은 도전하셔도 !");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_21() {
        //รางวัล 영환불로
        int level = 150;
        int questID = 501545;
        int reward = 2048723;
        int rewardQTY = 10;
        String rewardString = "11111111111111111111100000000";
        String mission = "<스타포스 80성>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\nเขา럼 스น้ำ한 번째 스텝을 เริ่ม하겠.\r\n\r\n스น้ำ한 번째 스텝에서 알아볼 เนื้อหา은\r\n#b#e<스타포스 80성>#n#k.");
        questSay.add("\r\n스น้ำ한 번째 스텝업은 장착 있는 อุปกรณ์의 스타포스 총 수치가 80 넘어보자 .");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        int chuk = 0;
        for (Item item : getPlayer().getInventory(MapleInventoryType.EQUIPPED).list()) {
            Equip equip = (Equip) item;
            if (equip != null) {
                chuk += equip.getCHUC();
            }
        }
        if (chuk >= 80) {
            if (getPlayer().isQuestStarted(501545)) {
                if (getPlayer().getOneInfoQuestInteger(501545, "value") < 1) {
                    getPlayer().updateOneInfo(501545, "value", "1");
                }
                if (getPlayer().getOneInfoQuestInteger(501524, "state") < 2) {
                    getPlayer().updateOneInfo(501524, "state", "2");
                }
            }
        }
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_22() {
        //어빌리티 서큘레이터 รางวัล으로
        int level = 160;
        int questID = 501546;
        int reward = 5062800;
        int rewardQTY = 10;
        String rewardString = "11111111111111111111110000000";
        String mission = "<เพิ่มตัวเลือก>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\nเขา럼 스น้ำ두 번째 스텝을 เริ่ม하겠.\r\n\r\n스น้ำ두 번째 스텝에서 알아볼 เนื้อหา은\r\n#b#e<เพิ่มตัวเลือก>#n#k.");
        questSay.add("\r\nMMORPG 강해지는 ห้อง법 중 하ฉัน 바로 더 แข็งแรง ไอเท็ม 장착이지요!");
        questSay.add("\r\n더 แข็งแรง ไอเท็ม 장착하는 ห้อง법중 하ฉัน 자신에게 더 ดี เพิ่มตัวเลือก 있는 ไอเท็ม 장착하기!");
        questSay.add("\r\nเพิ่มตัวเลือก 강력한 환생의 불꽃, 영원한 환생의 불꽃, 검은 환생의 불꽃 ไอเท็ม 통해 รีเซ็ต เป็นไปได้.");
        questSay.add("\r\n스น้ำ두 번째 스텝업은 환생의 불꽃 ไอเท็ม 통해 เพิ่มตัวเลือก รีเซ็ต하기 .");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_23() {
        int level = 170;
        int questID = 501547;
        int reward = 2439583;
        int rewardQTY = 1;
        String rewardString = "11111111111111111111111000000";
        String mission = "<어빌리티>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\nเขา럼 스น้ำ세 번째 스텝을 เริ่ม하겠.\r\n\r\n스น้ำ세 번째 스텝에서 알아볼 เนื้อหา은\r\n#b#e<어빌리티>#n#k.");
        questSay.add("\r\nตัวละคร 강하게 만들기 บน해서는 ตัวละคร 내면에 숨겨진 힘 어빌리티를 좋게 만드는 ห้อง법도 있.");
        questSay.add("\r\n최วินาที에 어빌리티는 왼쪽 전구 어빌리티 เควส 통해 부여받을 수 있게");
        questSay.add("\r\n어빌리티의 รีเซ็ต ระดับ별 จำเป็น 명성치를 이용해 Stat창(S) และ 서큘레이터 ไอเท็ม 통해 รีเซ็ต 할 수 있.");
        questSay.add("\r\n스น้ำ세 번째 스텝업은 어빌리티 รีเซ็ต하기 .");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_24() {
        int level = 175;
        int questID = 501548;
        int reward = 5680148;
        int rewardQTY = 1;
        String rewardString = "11111111111111111111111100000";
        String mission = "<무릉도장>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\nเขา럼 스น้ำ네 번째 스텝을 เริ่ม하겠.\r\n\r\n스น้ำ네 번째 스텝에서 알아볼 เนื้อหา은\r\n#b#e<무릉도장>#n#k.");
        questSay.add("\r\n무릉도장은 แต่ละ 층에 배치있는 บอส들을 한층한층 격파해ฉัน가며 더 บน로 올라가야하는 컨텐츠!");
        questSay.add("\r\nน้ำ론 บน로 올라갈수록 더 แข็งแรง บอส들이 존재 더 สูง층을 올라갈수록 สัปดาห์마다 ดำเนินการ되는 무릉도장 정산을 통해 다양한 ไอเท็ม ได้รับ할 수 있.");
        questSay.add("\r\n무릉도장을 많은 이용 바라며 이번 스텝업은 클리어 เงื่อนไข없이 ภารกิจ เสร็จสมบูรณ์버튼을 누르시면 .");
        questSay.add("\r\n아! 참고로 무릉도장 역시 มอนสเตอร์파크และ ด้วยกัน 컨텐츠 ระบบ을 이용해 이용하실 수 있. 감사.");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_25() {
        int level = 179;
        int questID = 501549;
        int reward = 2439584;
        int rewardQTY = 1;
        String rewardString = "11111111111111111111111110000";
        String mission = "<190Lv 달성>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\n스น้ำ다섯 번째 스텝업은 190Lv 달성하기 .");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_26() {
        //รางวัล으로 เลือก형 아케인심볼 100개
        int level = 190;
        int questID = 501550;
        int reward = 2439585;
        int rewardQTY = 1;
        String rewardString = "11111111111111111111111111000";
        String mission = "<200Lv 달성>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\n스น้ำ여섯 번째 스텝업은 200Lv 달성하기 .");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_27() {
        int level = 200;
        int questID = 501551;
        int reward = 2439586;
        int rewardQTY = 1;
        String rewardString = "11111111111111111111111111100";
        String mission = "<소멸의 여로>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\nเขา럼 스น้ำวัน곱 번째 스텝을 เริ่ม하겠.\r\n\r\n스น้ำวัน곱 번째 스텝에서 알아볼 เนื้อหา은\r\n#b#e<소멸의 여로>#n#k.");
        questSay.add("\r\n소멸의 여로 이후 사냥터จาก는 아케인 포스를 ต้องการ하게 . น้ำ론  아케인 포스는 มอนสเตอร์ 강해지면 강해질수록 더 สูง 아케인 포스를 ต้องการ하게 ");
        questSay.add("\r\n아케인 포스를 높이는 ห้อง법으로는 วันวัน เควส, แต่ละ종 이벤트, วันวัน หมู่บ้าน별 컨텐츠, 슈피겔만의 퀵패스를 이용해 아케인 심볼을 ได้รับ해 아케인 포스를 늘릴 수 있.");
        questSay.add("\r\n스น้ำวัน곱 번째 스텝업은 슈피겔만(아케인리버 퀵패스) 엔피시에게 말을 걸어보자 .");
        questSay.add("\r\n슈피겔만(아케인리버 퀵패스)엔피시는 แต่ละ종 หมู่บ้าน(이름 없는 หมู่บ้าน, 츄츄 아วัน랜드 등) ตำแหน่ง 있.");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_28() {
        int level = 210;
        int questID = 501552;
        int reward = 2439587;
        int rewardQTY = 1;
        String rewardString = "11111111111111111111111111110";
        String mission = "<스타포스 120성>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\nเขา럼 스น้ำ여덟 번째 스텝을 เริ่ม하겠.\r\n\r\n스น้ำ여덟 번째 스텝에서 알아볼 เนื้อหา은\r\n#b#e<스타포스 120성>#n#k.");
        questSay.add("\r\n스น้ำ여덟 번째 스텝업은 장착 있는 อุปกรณ์의 스타포스 총 수치가 120 넘어보자 .");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        int chuk = 0;
        for (Item item : getPlayer().getInventory(MapleInventoryType.EQUIPPED).list()) {
            Equip equip = (Equip) item;
            if (equip != null) {
                chuk += equip.getCHUC();
            }
        }
        if (chuk >= 120) {
            if (getPlayer().isQuestStarted(501552)) {
                if (getPlayer().getOneInfoQuestInteger(501552, "value") < 1) {
                    getPlayer().updateOneInfo(501552, "value", "1");
                }
                if (getPlayer().getOneInfoQuestInteger(501524, "state") < 2) {
                    getPlayer().updateOneInfo(501524, "state", "2");
                }
            }
        }
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_29() {
        int level = 220;
        int questID = 501553;
        int reward = 2439588;
        int rewardQTY = 1;
        String rewardString = "11111111111111111111111111111";
        String mission = "<아케인포스>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nใน녕하십니까, #b#h0##k.\r\n#b<스텝업>#k 오신 걸 환영.");
        questSay.add("\r\n드.디.어 ครั้งสุดท้าย 스텝업. 고생 많으셨.");
        questSay.add("\r\n스텝업 기간동ใน 엘리트 มอนสเตอร์ 사냥하랴 룬도 ใช้하랴 어빌리티도 รีเซ็ต하랴 고생 많으셨.");
        questSay.add("\r\nเขา럼 ครั้งสุดท้าย 스텝업을 เริ่ม하겠.");
        questSay.add("\r\nครั้งสุดท้าย 스텝업은 <아케인포스> 스텝업! .");
        questSay.add("\r\n220เลเวล 달성한 용사님 정도면 วันวัน เควส และ แต่ละ종 이벤트로 아케인포스를 가득 채웠겠지요!??");
        questSay.add("\r\nครั้งสุดท้าย 스텝업의 เนื้อหา은 아케인포스 3000채우기 .");
        questSay.add("\r\n220인데 3000 너무 많은거 아.니.냐.구.요?");
        questSay.add("\r\nฉัน는 모르겠.");
        questSay.add("\r\n\r\nเขา럼 ภารกิจ เสร็จสมบูรณ์ 후 다시 뵙도록 하겠.\r\n건투를 빕니다!");
        questSay.add("\r\n라고 할뻔! 지금까진 장난이였구 스텝업 เควส 하느라 고생 많으셨. 엔피시 สนทนา가 สิ้นสุด된หลัง에 ภารกิจ เสร็จสมบูรณ์ 누르시면 . เขา럼 수고많으셨. 감사.");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void check_step_up(int sLevel, int questID, List<String> questSay, int reward, int rewardqty, String rewardString, String mission) {
        if (getPlayer().getLevel() < sLevel) {
            self.sayOk("เลเวล" + sLevel + "이상만 ภารกิจ을 수행할 수 있.", ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
        if (getPlayer().getQuestStatus(questID) < 1) { //เควส ใน받아져있는 สถานะ
            for (String q : questSay) {
                self.say(q, ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
            }
            getSc().flushSay();
            MapleQuest.getInstance(questID).forceStart(getPlayer(), getNpc().getId(), null);
            getPlayer().updateOneInfo(501524, "state", "1");
            switch (questID) {
                case 501526: //두번째 스텝업
                    if (getPlayer().getLevel() >= 40) {
                        getPlayer().updateOneInfo(501524, "state", "2");
                        getPlayer().updateOneInfo(501526, "value", "1");
                    }
                    break;
                case 501527: //세번째 스텝업
                    if (getPlayer().getLevel() >= 45) {
                        getPlayer().updateOneInfo(501524, "state", "2");
                        getPlayer().updateOneInfo(501527, "value", "1");
                    }
                    break;
                case 501529:
                    if (getPlayer().getLevel() >= 55) {
                        getPlayer().updateOneInfo(501524, "state", "2");
                        getPlayer().updateOneInfo(501529, "value", "1");
                    }
                    break;
                case 501530:
                    if (getPlayer().getLevel() >= 60) {
                        getPlayer().updateOneInfo(501524, "state", "2");
                        getPlayer().updateOneInfo(501530, "value", "1");
                    }
                    break;
                case 501532:
                    if (getPlayer().getLevel() >= 70) {
                        getPlayer().updateOneInfo(501524, "state", "2");
                        getPlayer().updateOneInfo(501532, "value", "1");
                    }
                    break;
                case 501535:
                    if (getPlayer().getLevel() >= 90) {
                        getPlayer().updateOneInfo(501524, "state", "2");
                        getPlayer().updateOneInfo(501535, "value", "1");
                    }
                    break;
                case 501536:
                    if (getPlayer().getLevel() >= 100) {
                        getPlayer().updateOneInfo(501524, "state", "2");
                        getPlayer().updateOneInfo(501536, "value", "1");
                    }
                    break;
                case 501537:
                    if (getPlayer().getLevel() >= 101) {
                        getPlayer().updateOneInfo(501524, "state", "2");
                        getPlayer().updateOneInfo(501537, "value", "1");
                    }
                    break;
                case 501539:
                    if (getPlayer().getLevel() >= 107) {
                        getPlayer().updateOneInfo(501524, "state", "2");
                        getPlayer().updateOneInfo(501539, "value", "1");
                    }
                    break;
                case 501540:
                    if (getPlayer().getLevel() >= 110) {
                        getPlayer().updateOneInfo(501524, "state", "2");
                        getPlayer().updateOneInfo(501540, "value", "1");
                    }
                    break;
                case 501541:
                    getPlayer().updateOneInfo(501524, "state", "2");
                    getPlayer().updateOneInfo(501541, "value", "1");
                    break;
                case 501548:
                    getPlayer().updateOneInfo(501524, "state", "2");
                    getPlayer().updateOneInfo(501548, "value", "1");
                    break;
                case 501553:
                    getPlayer().updateOneInfo(501524, "state", "2");
                    getPlayer().updateOneInfo(501553, "value", "1");
                    break;
                case 501542:
                    if (getPlayer().getLevel() >= 130) {
                        getPlayer().updateOneInfo(501524, "state", "2");
                        getPlayer().updateOneInfo(501542, "value", "1");
                    }
                    break;
                case 501543:
                    int chuk = 0;
                    for (Item item : getPlayer().getInventory(MapleInventoryType.EQUIPPED).list()) {
                        Equip equip = (Equip) item;
                        if (equip != null) {
                            chuk += equip.getCHUC();
                        }
                    }
                    if (chuk >= 45) {
                        if (getPlayer().isQuestStarted(501543)) {
                            if (getPlayer().getOneInfoQuestInteger(501543, "value") < 1) {
                                getPlayer().updateOneInfo(501543, "value", "1");
                            }
                            if (getPlayer().getOneInfoQuestInteger(501524, "state") < 2) {
                                getPlayer().updateOneInfo(501524, "state", "2");
                            }
                        }
                    }
                    break;
                case 501545:
                    chuk = 0;
                    for (Item item : getPlayer().getInventory(MapleInventoryType.EQUIPPED).list()) {
                        Equip equip = (Equip) item;
                        if (equip != null) {
                            chuk += equip.getCHUC();
                        }
                    }
                    if (chuk >= 80) {
                        if (getPlayer().isQuestStarted(501545)) {
                            if (getPlayer().getOneInfoQuestInteger(501545, "value") < 1) {
                                getPlayer().updateOneInfo(501545, "value", "1");
                            }
                            if (getPlayer().getOneInfoQuestInteger(501524, "state") < 2) {
                                getPlayer().updateOneInfo(501524, "state", "2");
                            }
                        }
                    }
                    break;
                case 501552:
                    chuk = 0;
                    for (Item item : getPlayer().getInventory(MapleInventoryType.EQUIPPED).list()) {
                        Equip equip = (Equip) item;
                        if (equip != null) {
                            chuk += equip.getCHUC();
                        }
                    }
                    if (chuk >= 120) {
                        if (getPlayer().isQuestStarted(501552)) {
                            if (getPlayer().getOneInfoQuestInteger(501552, "value") < 1) {
                                getPlayer().updateOneInfo(501552, "value", "1");
                            }
                            if (getPlayer().getOneInfoQuestInteger(501524, "state") < 2) {
                                getPlayer().updateOneInfo(501524, "state", "2");
                            }
                        }
                    }
                    break;
                case 501549:
                    if (getPlayer().getLevel() >= 190) {
                        getPlayer().updateOneInfo(501524, "state", "2");
                        getPlayer().updateOneInfo(501549, "value", "1");
                    }
                    break;
                case 501550:
                    if (getPlayer().getLevel() >= 200) {
                        getPlayer().updateOneInfo(501524, "state", "2");
                        getPlayer().updateOneInfo(501550, "value", "1");
                    }
                    break;
            }
        } else {
            //if (!getPlayer().getQuest(questID).getQuest().canComplete(getPlayer(), null)) {
            if (getPlayer().getOneInfoQuestInteger(questID, "value") == 1 && (getPlayer().isQuestStarted(questID)/* || ((questID - 501525 + 1) == getPlayer().getOneInfoQuestInteger(501524, "step"))*/)) {
                if (1 == self.askYesNo("\r\n#b[" + sLevel + "เลเวล 스텝업 ภารกิจ]#k 달성을 축하!\r\n지금 바로 #bภารกิจ รางวัล#k 받으시หรือไม่?\r\n\r\n\r\n#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#\r\n#b#i" + reward + ":# #t" + reward + ":# " + rewardqty + "개#k\r\n", ScriptMessageFlag.NpcReplacedByNpc)) {
                    MapleItemInformationProvider mif = MapleItemInformationProvider.getInstance();
                    if (mif.getItemInformation(reward) != null) {
                        String itemName = mif.getName(reward);
                        if (target.exchange(reward, rewardqty) > 0) {
                            getPlayer().completeQuest(questID);
                            getPlayer().updateOneInfo(501524, "reward", rewardString);
                            getPlayer().updateOneInfo(501524, "state", "3");
                            getPlayer().send(CWvsContext.getScriptProgressMessage(reward, " " + itemName + " " + rewardqty + "개를 ได้รับ하였!"));
                        } else {
                            self.sayOk("กระเป๋า 공간이 ไม่พอ. 공간을 넉넉히 비운หลัง 다시 시도โปรด!", ScriptMessageFlag.NpcReplacedByNpc);
                        }
                    } else {
                        self.sayOk("오류가 발생แล้ว.");
                    }
                }
            } else {
                if (getPlayer().getQuestStatus(questID) == 2) {
                    self.say("이미 깬 스텝업 ภารกิจ이거ฉัน จำเป็น한 เลเวล 달성하지 못แล้ว.");
                } else {
                    int v = self.askMenu("#b#e[" + sLevel + "เลเวล 스텝업 ภารกิจ]#k ดำเนินการ 중!\r\n\r\n\r\n<ภารกิจ เสร็จสมบูรณ์ เงื่อนไข>#n\r\n" + mission + "\r\n\r\n#b#L0#ภารกิจ อธิบาย 다시 듣기#l#k", ScriptMessageFlag.NpcReplacedByNpc);
                    if (v == 0) {
                        for (String q : questSay) {
                            self.say(q, ScriptMessageFlag.NpcReplacedByNpc);
                        }
                    }
                }
            }
        }
    }


}
