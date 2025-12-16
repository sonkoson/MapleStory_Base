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

    public void q100865s() { // StepUp Start Quest
        self.say("\r\nสวัสดีจ้ะ!\r\nเธอคือ #bผู้กล้าหน้าใหม่#k ที่เพิ่งเดินทางมาถึง #bGanglim World#k ใช่ไหมเอ่ย?",
                ScriptMessageFlag.NpcReplacedByNpc);
        self.say(
                "\r\nยินดีที่ได้รู้จักนะ! ฉันชื่อ #bCassandra#k จ้ะ!\r\n\r\nฉันเป็นสุดยอด #bนักพยากรณ์#k แห่ง Maple World และยังเป็น #bผู้แนะนำ#k ให้กับเหล่าผู้กล้าที่มาเยือน Maple World แห่งนี้ด้วยนะ!",
                ScriptMessageFlag.NpcReplacedByNpc);
        self.say(
                "\r\nเพิ่งมาถึงได้ไม่นาน ก็เลยยังมีเรื่องที่ไม่รู้อีกเยอะเลยสินะ?\r\n\r\nวันนี้ควรจะไปล่า #bมอนสเตอร์#k ตัวไหนดี\r\nหรือจะปั้นตัวละครยังไงให้เก่งไวๆ ดีนะ?",
                ScriptMessageFlag.NpcReplacedByNpc);
        self.say(
                "\r\n#bไม่ต้องกังวลไปหรอก~!#k\r\n\r\nเพราะฉัน #bCassandra#k คนนี้ได้เตรียมกิจกรรม\r\n#b#e<Ganglim World Step Up>#n#k เพื่อช่วยให้ผู้กล้าหน้าใหม่เติบโตได้อย่างรวดเร็วไว้แล้วยังไงล่ะ~!",
                ScriptMessageFlag.NpcReplacedByNpc);
        self.say(
                "\r\nทุกๆ ช่วงเลเวลที่กำหนด จะมี #bภารกิจ Step Up~!#k มอบให้\r\nและเมื่อทำภารกิจสำเร็จ ก็จะได้รับ #bของรางวัล#k เพื่อ #bSpec Up~!#k ให้ตัวละครเก่งขึ้นด้วยนะ",
                ScriptMessageFlag.NpcReplacedByNpc);
        if (1 == self.askYesNo(
                "เป็นไงล่ะ? อยากเข้าร่วมกิจกรรม #b#e<Ganglim World Step Up>#n#k\r\nที่จะช่วยสอนพื้นฐานสำคัญของ Maple World ให้กับเธอ\r\nด้วยกันไหมล่ะ?",
                ScriptMessageFlag.NpcReplacedByNpc)) {
            for (int i = 501525; i <= 50155; i++) {
                getPlayer().updateInfoQuest(i, "");
            }
            getPlayer().updateInfoQuest(100865, "");
            SimpleDateFormat sdf = new SimpleDateFormat("YY/MM/dd/HH/mm;");
            String timeToString = sdf.format(new Date());
            getPlayer().updateInfoQuest(501524,
                    "reward=00000000000000000000000000000;step=0;sTime=" + timeToString + "state=3");
            getPlayer().updateInfoQuest(501527, "value=0");
            getPlayer().updateInfoQuest(501540, "value=0");
            getPlayer().updateInfoQuest(501541, "value=0");
            getPlayer().updateInfoQuest(501524,
                    "reward=00000000000000000000000000000;start=1;step=0;sTime=" + timeToString + "state=3");
            getQuest().forceComplete(getPlayer(), getNpc().getId());
            self.say("\r\nคิดถูกแล้วล่ะ~!\r\nรับรองว่าจะต้องเป็นการเลือกที่ไม่เสียใจทีหลังแน่นอน~!",
                    ScriptMessageFlag.NpcReplacedByNpc);
            self.say(
                    "\r\nเอาล่ะ ตอนนี้เธอสามารถเริ่มทำ #bภารกิจ Step Up#k ตามช่วงเลเวลที่กำหนดได้แล้ว!\r\n\r\nหากต้องการตรวจสอบสถานะภารกิจและการดำเนินการ ก็สามารถคลิกที่ #b#eไอคอน Step Up#n#k ทางด้านซ้ายของหน้าจอ (Event Notifier) ได้เลย!",
                    ScriptMessageFlag.NpcReplacedByNpc);
            // self.say("\r\n#b#e<Burning World Step Up>#n#k\r\nAvailable until
            // #b#eSeptember 9, 2021 (Thu) before server maintenance#n#k\r\n\r\nAfter that,
            // you cannot complete missions or claim rewards.\r\nBe careful with the
            // time!\r\nDo you understand?", ScriptMessageFlag.NpcReplacedByNpc);
            self.say(
                    "\r\nคำอธิบายก็มีเท่านี้แหละ ง่ายใช่ไหมล่ะ?\r\n\r\nงั้นก็ขอให้สนุกไปกับการผจญภัยใน #b#e<Ganglim World Step Up>#n#k นะ~!\r\nขออวยพรให้การเติบโตของเธอในวันข้างหน้ามีแต่เรื่องดีๆ #bCheers#k~#r♥#k",
                    ScriptMessageFlag.NpcReplacedByNpc);
            getSc().flushSay();
            getPlayer().send(CField.UIPacket.openUI(1160));

            NormalEffect e = new NormalEffect(getPlayer().getId(), EffectHeader.QuestClear);
            getPlayer().send(e.encodeForLocal());
        } else {
            self.say(
                    "\r\nเป็น #bStar One ที่มีความมุ่งมั่น#k ชอบบุกเบิกเส้นทางด้วยตัวเองสินะ!\r\n\r\nแต่บางครั้งการได้รับ #bความช่วยเหลือ#k และเรียนรู้ #bKnow-how#k จากคนอื่น\r\nก็อาจช่วยให้ #bเติบโตได้ไวยิ่งขึ้น#k นะ!",
                    ScriptMessageFlag.NpcReplacedByNpc);
            // self.say("\r\n#b<Burning World Step Up>#k Available until\r\n#bSeptember 9,
            // 2021 (Thu) before server maintenance#k\r\nIf you change your mind,\r\nplease
            // come back and talk to me anytime~!\r\n\r\nI'll be waiting right here!",
            // ScriptMessageFlag.NpcReplacedByNpc);
        }
    }

    public void StepUp_1() {
        /* (Comments removed for brevity, check original if needed) */
        // public void check_step_up(int sLevel, int questID, List<String> questSay, int
        // reward, int rewardqty, String rewardString, String mission) {
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add(
                "\r\nฉันคือ #b<Step Up Goal>#k\r\nที่จะมาคอยช่วยเหลือการดำเนินการ #bStep Up#k ของคุณ #b#h0##k ยังไงล่ะ\r\n\r\nตามชื่อเลย ฉันรับหน้าที่ดูแลช่อง #b<GOAL>#k\r\nที่สำคัญที่สุดใน Step Up นี้");
        questSay.add(
                "\r\n#bภารกิจของฉัน#k ก็คือการช่วยเหลือให้คุณ #b#h0##k\r\nได้พัฒนาตัวเองไป #bทีละขั้นๆ (Step by Step)#k\r\n\r\nฝากตัวตลอดกิจกรรม #bStep Up#k ด้วยนะ");
        questSay.add(
                "\r\nงั้นมาเริ่ม Step แรกกันเลยดีกว่า\r\n\r\nเนื้อหาที่จะเรียนรู้ใน Step แรกก็คือ\r\n#b#e<การใช้คำสั่ง @FreeMarket>#n#k");
        questSay.add(
                "\r\nพื้นฐานของ MMORPG ก็คือการพบปะกับผู้เล่นคนอื่นๆ ใช่ไหมล่ะ?\r\nสถานที่สื่อสารกับผู้เล่นคนอื่น (Free Market) และคำสั่งที่ช่วยให้ย้ายไปได้ง่ายๆ");
        questSay.add(
                "\r\nรู้วิธีใช้คำสั่ง #r@FreeMarket#k ไหม?\r\nแค่พิมพ์ #r@FreeMarket#k ในช่องแชท ก็จะสามารถย้ายไปยังพื้นที่พิเศษภายในเซิร์ฟเวอร์ได้ทันที!\r\nลองใช้คำสั่งดูสิ");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
        check_step_up(33, 501525, questSay, 2439580, 1, "10000000000000000000000000000", "<ใช้คำสั่ง @FreeMarket>");
    }

    public void StepUp_2() {
        int level = 35;
        int questID = 501526;
        int reward = 2450042;
        int rewardQTY = 2;
        String rewardString = "11000000000000000000000000000";
        String mission = "<เก็บเลเวลให้ถึง 40>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add("\r\nStep Up ขั้นที่สองคือ เก็บเลเวลให้ถึง Lv.40 นะจ๊ะ");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_3() {
        int level = 40;
        int questID = 501527;
        int reward = 2000019;
        int rewardQTY = 500;
        String rewardString = "11100000000000000000000000000";
        String mission = "<เก็บเลเวลให้ถึง 45>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add("\r\nStep Up ขั้นที่สามคือ เก็บเลเวลให้ถึง Lv.45 นะจ๊ะ");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_4() {
        int level = 45;
        int questID = 501528;
        int reward = 2439581;
        int rewardQTY = 1;
        String rewardString = "11110000000000000000000000000";
        String mission = "<การใช้ Rune>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add("\r\nStep Up ขั้นที่สี่คือ การใช้ Rune จ้ะ");
        questSay.add(
                "\r\nเวลาเดินผ่านแผนที่ล่ามอนสเตอร์ เธออาจจะพบกับ #r#eRune#n#k ได้ หากใช้ Rune จะได้รับเอฟเฟกต์ต่างๆ และบัฟ #bEXP 2 เท่า#k ด้วยนะ");
        questSay.add(
                "\r\nแต่ถ้าไม่ใช้ Rune ที่ปรากฏขึ้นมาเมื่อผ่านไปช่วงเวลาหนึ่ง จะทำให้เกิดผลเสีย (Penalty) ในพื้นที่ล่าด้วยนะ");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_5() {
        int level = 50;
        int questID = 501529;
        int reward = 5076100;
        int rewardQTY = 5;
        String rewardString = "11111000000000000000000000000";
        String mission = "<เก็บเลเวลให้ถึง 55>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add("\r\nStep Up ขั้นที่ห้าคือ เก็บเลเวลให้ถึง Lv.55 นะจ๊ะ");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_6() {
        // Reward as Spell Trace
        int level = 55;
        int questID = 501530;
        int reward = 4001832;
        int rewardQTY = 9000;
        String rewardString = "11111100000000000000000000000";
        String mission = "<เก็บเลเวลให้ถึง 60>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add("\r\nStep Up ขั้นที่หกคือ เก็บเลเวลให้ถึง Lv.60 นะจ๊ะ");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_7() {
        int level = 60;
        int questID = 501531;
        int reward = 2450042;
        int rewardQTY = 2;
        String rewardString = "11111110000000000000000000000";
        String mission = "<การตีบวกอัปเกรด>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add(
                "\r\nงั้นมาเริ่ม Step ที่เจ็ดกันเลย\r\n\r\nเนื้อหาที่จะเรียนรู้ใน Step ที่เจ็ดก็คือ\r\n#b#e<การตีบวกอัปเกรด>#n#k");
        questSay.add("\r\nเมื่อกดปุ่มกระเป๋า (I) เธอจะเห็น #rปุ่มค้อนสีแดง#k ใช่ไหมล่ะ");
        questSay.add("\r\nพอกดปุ่มสีแดงนั้น หน้าต่าง UI ตีบวกจะปรากฏขึ้น ซึ่งเธอสามารถทำการตีบวกผ่าน UI นี้ได้เลย");
        questSay.add(
                "\r\nวัตถุดิบที่จำเป็นในการตีบวกคือ #bSpell Trace (Juhon)#k ยิ่งเลเวลของไอเทมสูงเท่าไหร่ ก็ยิ่งต้องใช้ Spell Trace มากขึ้นเท่านั้น\r\nเธอสามารถหา #bSpell Trace#k ได้จากหลายช่องทาง (หลักๆ ก็คือการล่ามอนสเตอร์) นะ!");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_8() {
        // Reward: 2 General Meso Lucky Bags
        int level = 65;
        int questID = 501532;
        int reward = 2433019;
        int rewardQTY = 2;
        String rewardString = "11111111000000000000000000000";
        String mission = "<เก็บเลเวลให้ถึง 70>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add("\r\nStep Up ขั้นที่แปดคือ เก็บเลเวลให้ถึง Lv.70 นะจ๊ะ");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_9() {
        int level = 70;
        int questID = 501533;
        int reward = 2450042;
        int rewardQTY = 2;
        String rewardString = "11111111100000000000000000000";
        String mission = "<Star Force อัปเกรด>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add(
                "\r\nงั้นมาเริ่ม Step ที่เก้ากันเลย\r\n\r\nเนื้อหาที่จะเรียนรู้ใน Step ที่เก้าก็คือ\r\n#b#e<Star Force อัปเกรด>#n#k");
        questSay.add(
                "\r\nจำการอัปเกรดไอเทมที่เรียนไปใน Step ที่เจ็ดได้ไหม? Star Force อัปเกรดก็ทำผ่านปุ่มค้อนสีแดง (ปุ่มตีบวก) เหมือนกันจ้ะ");
        questSay.add(
                "\r\nเมื่อนำไอเทมที่ใช้ Spell Trace อัปเกรดจนครบจำนวนครั้งแล้วมาใส่ใน UI ตีบวก จะสามารถทำ Star Force อัปเกรดต่อได้ ซึ่งจำเป็นต้องใช้ Meso จำนวนหนึ่งในการดำเนินการ");
        questSay.add(
                "\r\nแน่นอนว่าเช่นเดียวกับการตีบวกปกติ ยิ่งไอเทมเลเวลสูงก็ยิ่งต้องใช้ Meso มากขึ้น และระวังด้วยนะเพราะมีโอกาสที่ไอเทมจะถูกทำลายได้ ขึ้นอยู่กับระดับการตีบวก!");
        questSay.add("\r\nStep Up ขั้นที่เก้านี้ ลองทำ Star Force อัปเกรด ดูสัก 1 ครั้งก็ผ่านแล้วจ้ะ!");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_10() {
        int level = 75;
        int questID = 501534;
        int reward = 2439582;
        int rewardQTY = 1;
        String rewardString = "11111111110000000000000000000";
        String mission = "<Elite Monster, Elite Champion>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add(
                "\r\nงั้นมาเริ่ม Step ที่สิบกันเลย\r\n\r\nเนื้อหาที่จะเรียนรู้ใน Step ที่สิบก็คือ\r\n#b#e<Elite Monster, Elite Champion>#n#k");
        questSay.add(
                "\r\nถ้าล่ามอนสเตอร์ในแผนที่ที่เลเวลเหมาะสมไปเรื่อยๆ จะมี #rElite Monster#k ที่ตัวใหญ่กว่ามอนสเตอร์ทั่วไปปรากฏตัวขึ้นมา");
        questSay.add(
                "\r\n#rElite Monster#k จะมี HP มากกว่ามอนสเตอร์ทั่วไป แต่ก็จะดรอปไอเทมรางวัลที่หลากหลายกว่าเช่นกัน");
        questSay.add("\r\nStep Up ขั้นที่สิบนี้ แค่กำจัด Elite Monster 1 ตัวก็สำเร็จแล้วจ้ะ!");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_11() {
        int level = 80;
        int questID = 501535;
        int reward = 5044008;
        int rewardQTY = 1;
        String rewardString = "11111111111000000000000000000";
        String mission = "<เก็บเลเวลให้ถึง 90>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add("\r\nStep Up ขั้นที่สิบเอ็ดคือ เก็บเลเวลให้ถึง Lv.90 นะจ๊ะ");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_12() {
        int level = 90;
        int questID = 501536;
        int reward = 2000019;
        int rewardQTY = 500;
        String rewardString = "11111111111100000000000000000";
        String mission = "<เก็บเลเวลให้ถึง 100>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add("\r\nStep Up ขั้นที่สิบสองคือ เก็บเลเวลให้ถึง Lv.100 นะจ๊ะ");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_13() {
        int level = 100;
        int questID = 501537;
        int reward = 2439609;
        int rewardQTY = 1;
        String rewardString = "11111111111110000000000000000";
        String mission = "<เก็บเลเวลให้ถึง 101>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add("\r\nStep Up ขั้นที่สิบสามคือ เก็บเลเวลให้ถึง Lv.101 นะจ๊ะ");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_14() {
        int level = 101;
        int questID = 501538;
        int reward = 2023836;
        int rewardQTY = 10;
        String rewardString = "11111111111111000000000000000";
        String mission = "<ตรวจสอบ UI กิจกรรม>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add(
                "\r\nงั้นมาเริ่ม Step ที่สิบสี่กันเลย\r\n\r\nเนื้อหาที่จะเรียนรู้ใน Step ที่สิบสี่ก็คือ\r\n#b#e<การตรวจสอบ UI กิจกรรม>#n#k");
        questSay.add("\r\nใน Ganglim Server เธอสามารถตรวจสอบรายการกิจกรรมที่กำลังดำเนินอยู่ได้ผ่านปุ่มกิจกรรม (V) นะ");
        questSay.add("\r\nเธอสามารถดูตารางกิจกรรมต่างๆ และไอเทมที่ได้รับจากกิจกรรมได้ที่นี่");
        questSay.add("\r\nStep Up ขั้นที่สิบสี่นี้ ลองเปิดหน้าต่าง UI กิจกรรมด้วยปุ่มกิจกรรม (V) ดูสิ!");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_15() {
        int level = 105;
        int questID = 501539;
        int reward = 2644017;
        int rewardQTY = 1;
        String rewardString = "11111111111111100000000000000";
        String mission = "<เก็บเลเวลให้ถึง 107>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add("\r\nStep Up ขั้นที่สิบห้าคือ เก็บเลเวลให้ถึง Lv.107 นะจ๊ะ");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_16() {
        int level = 107;
        int questID = 501540;
        int reward = 2437090;
        int rewardQTY = 1;
        String rewardString = "11111111111111110000000000000";
        String mission = "<เก็บเลเวลให้ถึง 110>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add("\r\nStep Up ขั้นที่สิบหกคือ เก็บเลเวลให้ถึง Lv.110 นะจ๊ะ");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_17() {
        int level = 110;
        int questID = 501541;
        int reward = 2433019;
        int rewardQTY = 2;
        String rewardString = "11111111111111111000000000000";
        String mission = "<Monster Park>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add(
                "\r\nงั้นมาเริ่ม Step ที่สิบเจ็ดกันเลย\r\n\r\nเนื้อหาที่จะเรียนรู้ใน Step ที่สิบเจ็ดก็คือ\r\n#b#e<Monster Park>#n#k");
        questSay.add(
                "\r\nMonster Park เป็นคอนเทนต์ที่ได้รับ EXP มหาศาล และยังได้รับของรางวัลที่แตกต่างกันไปในแต่ละวันด้วยนะ");
        questSay.add(
                "\r\nสามารถเข้าสู่ Monster Park ได้ผ่านระบบคอนเทนต์ โดยแนะนำสำหรับเลเวล 105 ขึ้นไปจนถึง 200+ เลยล่ะ");
        questSay.add(
                "\r\nขอให้สนุกกับ Monster Park นะ สำหรับ Step Up นี้สามารถกดปุ่มสำเร็จภารกิจได้เลยโดยไม่มีเงื่อนไขใดๆ จ้ะ!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_18() {
        int level = 119;
        int questID = 501542;
        int reward = 2000019;
        int rewardQTY = 500;
        String rewardString = "11111111111111111100000000000";
        String mission = "<เก็บเลเวลให้ถึง 130>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add("\r\nStep Up ขั้นที่สิบแปดคือ เก็บเลเวลให้ถึง Lv.130 นะจ๊ะ");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_19() {
        int level = 130;
        int questID = 501543;
        int reward = 2450042;
        int rewardQTY = 2;
        String rewardString = "11111111111111111110000000000";
        String mission = "<Star Force 45 ดาว>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add(
                "\r\nงั้นมาเริ่ม Step ที่สิบเก้ากันเลย\r\n\r\nเนื้อหาที่จะเรียนรู้ใน Step ที่สิบเก้าก็คือ\r\n#b#e<Star Force 45 ดาว>#n#k");
        questSay.add(
                "\r\nจำเรื่องการทำ Star Force ที่เรียนไปตอน Step เก้าได้ไหม? เพื่อที่จะไปล่าในแผนที่ที่หลากหลายและแข็งแกร่งขึ้น จำเป็นต้องมี Star Force ที่สูงขึ้นนะ");
        questSay.add("\r\nStep Up ขั้นที่สิบเก้านี้ ลองทำยอดรวม Star Force ของไอเทมที่สวมใส่อยู่ให้เกิน 45 ดาวดูสิ!");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
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
        String mission = "<Normal Boss>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add("\r\nในที่สุดก็มาถึง Step ที่ยี่สิบแล้ว! ยินดีด้วยนะ");
        questSay.add(
                "\r\nงั้นมาเริ่ม Step ที่ยี่สิบกันเลย\r\n\r\nเนื้อหาที่จะเรียนรู้ใน Step ที่ยี่สิบก็คือ\r\n#b#e<Normal Boss>#n#k");
        questSay.add(
                "\r\nใน MapleStory มีบอสอยู่มากมายหลายตัว ในบรรดานั้นรู้จักบอสที่เป็นสัญลักษณ์อย่าง #bZakum#k ไหม?");
        questSay.add("\r\nZakum มีทั้ง Normal Mode และ Chaos Mode... Step ยี่สิบนี้เราจะมาลองกำจัด Normal Zakum กัน!");
        questSay.add(
                "\r\nเธอสามารถเข้าสู่ห้องบอสได้โดยใช้คีย์ลัด Boss UI (T) และสามารถตรวจสอบสถานะการเคลียร์บอสได้ด้วย");
        questSay.add("\r\nStep Up ขั้นที่ยี่สิบคือการกำจัด Normal Zakum!");
        questSay.add(
                "\r\nอ๊ะ แล้วถ้าอยากลองท้าทาย Chaos Zakum ก็สามารถทำได้เช่นกันนะ ถ้ากำจัด Chaos Zakum ก็ถือว่าผ่านภารกิจเหมือนกัน ใครที่ไหวก็ลองไปท้าทายดูได้เลย!");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_21() {
        // Reward: Eternal Rebirth Flame
        int level = 150;
        int questID = 501545;
        int reward = 2048723;
        int rewardQTY = 10;
        String rewardString = "11111111111111111111100000000";
        String mission = "<Star Force 80 ดาว>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add(
                "\r\nงั้นมาเริ่ม Step ที่ยี่สิบเอ็ดกันเลย\r\n\r\nเนื้อหาที่จะเรียนรู้ใน Step ที่ยี่สิบเอ็ดก็คือ\r\n#b#e<Star Force 80 ดาว>#n#k");
        questSay.add(
                "\r\nStep Up ขั้นที่ยี่สิบเอ็ดนี้ ลองทำยอดรวม Star Force ของไอเทมที่สวมใส่อยู่ให้เกิน 80 ดาวดูสิ!");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
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
        // Reward: ability circulator
        int level = 160;
        int questID = 501546;
        int reward = 5062800;
        int rewardQTY = 10;
        String rewardString = "11111111111111111111110000000";
        String mission = "<ปรับแต่ง Option เพิ่มเติม>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add(
                "\r\nงั้นมาเริ่ม Step ที่ยี่สิบสองกันเลย\r\n\r\nเนื้อหาที่จะเรียนรู้ใน Step ที่ยี่สิบสองก็คือ\r\n#b#e<การปรับแต่ง Option เพิ่มเติม>#n#k");
        questSay.add(
                "\r\nหนึ่งในวิธีที่จะทำให้ตัวละครแข็งแกร่งขึ้นในเกม MMORPG ก็คือการสวมใส่ไอเทมที่ดียิ่งขึ้นใช่ไหมล่ะ!");
        questSay.add("\r\nเราสามารถเลือกสวมใส่ไอเทมที่มี Option ที่ดีกว่าได้");
        questSay.add(
                "\r\nOption เพิ่มเติม (Additional Option) สามารถรีเซ็ตใหม่ได้โดยใช้ไอเทม Rebirth Flame ประเภทต่างๆ เช่น Powerful, Eternal หรือ Black Rebirth Flame");
        questSay.add("\r\nStep Up ขั้นที่ยี่สิบสองนี้ ลองใช้ไอเทม Rebirth Flame เพื่อรีเซ็ต Option เพิ่มเติมดูสิ!");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_23() {
        int level = 170;
        int questID = 501547;
        int reward = 2439583;
        int rewardQTY = 1;
        String rewardString = "11111111111111111111111100000";
        String mission = "<Ability>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add(
                "\r\nงั้นมาเริ่ม Step ที่ยี่สิบสามกันเลย\r\n\r\nเนื้อหาที่จะเรียนรู้ใน Step ที่ยี่สิบสามก็คือ\r\n#b#e<Ability>#n#k");
        questSay.add(
                "\r\nเพื่อทำให้ตัวละครแข็งแกร่งขึ้น ยังมีอีกวิธีนึงคือการดึงพลังที่ซ่อนอยู่ภายใน หรือ Ability ออกมาใช้");
        questSay.add("\r\nสามารถปลดล็อก Ability ได้ผ่านเควสหลอดไฟทางด้านซ้ายของหน้าจอ");
        questSay.add(
                "\r\nเราสามารถรีเซ็ต Ability ได้โดยใช้ค่าชื่อเสียง (Honor EXP) ผ่านหน้าต่าง Stat (S) หรือใช้ไอเทม Circulator ก็ได้เช่นกัน");
        questSay.add("\r\nStep Up ขั้นที่ยี่สิบสามนี้ ลองรีเซ็ต Ability ดูสิ!");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_24() {
        int level = 175;
        int questID = 501548;
        int reward = 5680148;
        int rewardQTY = 1;
        String rewardString = "11111111111111111111111110000";
        String mission = "<Mu Lung Dojo>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add(
                "\r\nงั้นมาเริ่ม Step ที่ยี่สิบสี่กันเลย\r\n\r\nเนื้อหาที่จะเรียนรู้ใน Step ที่ยี่สิบสี่ก็คือ\r\n#b#e<Mu Lung Dojo>#n#k");
        questSay.add("\r\nMu Lung Dojo เป็นคอนเทนต์ที่ให้เราไต่หอคอยขึ้นไปทีละชั้น โดยต้องกำจัดบอสที่อยู่ในแต่ละชั้น!");
        questSay.add(
                "\r\nแน่นอนว่ายิ่งสูง บอสก็ยิ่งแข็งแกร่ง และจะมีการสรุปผลจัดอันดับทุกสัปดาห์เพื่อมอบของรางวัลต่างๆ ด้วยนะ");
        questSay.add(
                "\r\nขอให้สนุกกับ Mu Lung Dojo นะ สำหรับ Step Up นี้สามารถกดปุ่มสำเร็จภารกิจได้เลยโดยไม่มีเงื่อนไขใดๆ จ้ะ!");
        questSay.add("\r\nอ้อ! Mu Lung Dojo สามารถเข้าได้ผ่านระบบคอนเทนต์แบบเดียวกับ Monster Park เลยนะจ๊ะ");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_25() {
        int level = 179;
        int questID = 501549;
        int reward = 2439584;
        int rewardQTY = 1;
        String rewardString = "11111111111111111111111110000";
        String mission = "<เก็บเลเวลให้ถึง 190>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add("\r\nStep Up ขั้นที่ยี่สิบห้าคือ เก็บเลเวลให้ถึง Lv.190 นะจ๊ะ");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_26() {
        // Reward: Arcane Symbol Selector x100
        int level = 190;
        int questID = 501550;
        int reward = 2439585;
        int rewardQTY = 1;
        String rewardString = "11111111111111111111111111000";
        String mission = "<เก็บเลเวลให้ถึง 200>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add("\r\nStep Up ขั้นที่ยี่สิบหกคือ เก็บเลเวลให้ถึง Lv.200 นะจ๊ะ");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_27() {
        int level = 200;
        int questID = 501551;
        int reward = 2439586;
        int rewardQTY = 1;
        String rewardString = "11111111111111111111111111100";
        String mission = "<Vanishing Journey>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add(
                "\r\nงั้นมาเริ่ม Step ที่ยี่สิบเจ็ดกันเลย\r\n\r\nเนื้อหาที่จะเรียนรู้ใน Step ที่ยี่สิบเจ็ดก็คือ\r\n#b#e<Vanishing Journey>#n#k");
        questSay.add(
                "\r\nพื้นที่ล่าตั้งแต่ Vanishing Journey เป็นต้นไป จำเป็นต้องใช้ #bArcane Force#k ด้วยนะ แน่นอนว่ายิ่งมอนสเตอร์แข็งแกร่งมากเท่าไหร่ ก็ยิ่งต้องการค่า Arcane Force ที่สูงขึ้นเท่านั้น");
        questSay.add(
                "\r\nวิธีเพิ่มค่า Arcane Force สามารถทำได้โดยการรับ Arcane Symbol จาก Daily Quest, กิจกรรมต่างๆ, คอนเทนต์หมู่บ้านประจำวัน หรือใช้ Spiegelmann's Quick Pass ก็ได้เช่นกัน");
        questSay.add(
                "\r\nStep Up ขั้นที่ยี่สิบเจ็ดนี้ ให้ลองไปคุยกับ NPC #bSpiegelmann (Arcane River Quick Pass)#k ดูสิ");
        questSay.add(
                "\r\nNPC #bSpiegelmann (Arcane River Quick Pass)#k จะประจำอยู่ที่หมู่บ้านต่างๆ (Nameless Town, Chu Chu Island ฯลฯ) ลองหาดูนะ!");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void StepUp_28() {
        int level = 210;
        int questID = 501552;
        int reward = 2439587;
        int rewardQTY = 1;
        String rewardString = "11111111111111111111111111110";
        String mission = "<Star Force 120 ดาว>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add(
                "\r\nงั้นมาเริ่ม Step ที่ยี่สิบแปดกันเลย\r\n\r\nเนื้อหาที่จะเรียนรู้ใน Step ที่ยี่สิบแปดก็คือ\r\n#b#e<Star Force 120 ดาว>#n#k");
        questSay.add(
                "\r\nStep Up ขั้นที่ยี่สิบแปดนี้ ลองทำยอดรวม Star Force ของไอเทมที่สวมใส่อยู่ให้เกิน 120 ดาวดูสิ!");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
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
        String mission = "<Arcane Force>";
        List<String> questSay = new ArrayList<>();
        questSay.add("\r\nสวัสดีจ้ะ #b#h0##k\r\nยินดีต้อนรับสู่ #b<Step Up>#k นะ");
        questSay.add("\r\nใ.น.ที่.สุ.ด. Step Up สุดท้ายแล้ว! ลำบากมามากเลยนะเนี่ย");
        questSay.add(
                "\r\nตลอดกิจกรรม Step Up ที่ผ่านมา ทั้งล่า Elite Monster, ใช้ Rune, รีเซ็ต Ability คงเหนื่อยแย่เลยสินะ");
        questSay.add("\r\nงั้นมาเริ่ม Step สุดท้ายกันเลย");
        questSay.add("\r\nStep Up สุดท้ายคือ... <Arcane Force> ยังไงล่ะ!");
        questSay.add(
                "\r\nระดับผู้กล้าที่มาถึงเลเวล 220 แล้ว คงจะทำ Daily Quest และกิจกรรมต่างๆ จนสะสม Arcane Force มาเต็มเปี่ยมแล้วใช่ไหมล่ะ!?");
        questSay.add("\r\nภารกิจ Step Up สุดท้ายนี้ ก็คือการสะสมค่า Arcane Force ให้ได้ 3000!");
        questSay.add("\r\nแต่เดี๋ยวก่อน... เลเวล 220 จะเอา Arcane Force 3000 มาจากไหน ตั้งเยอะแยะขนาดนั้น?");
        questSay.add("\r\nไม่รู้สินะ...");
        questSay.add("\r\n\r\nงั้นไว้เจอกันใหม่หลังทำภารกิจสำเร็จนะ\r\nขอให้โชคดีจ้ะ!");
        questSay.add(
                "\r\nล้อเล่นน่า! แหย่เล่นนิดเดียวเอง จริงๆ แค่คุยกับฉันก็ผ่านภารกิจ Step Up นี้แล้วล่ะ\r\nขอบคุณที่เหนื่อยมาตลอดนะ พอกดปิดหน้าต่างสนทนานี้แล้ว ก็กดปุ่มสำเร็จภารกิจได้เลย เดี๋ยวฉันให้รางวัลนะ!");
        check_step_up(level, questID, questSay, reward, rewardQTY, rewardString, mission);
    }

    public void check_step_up(int sLevel, int questID, List<String> questSay, int reward, int rewardqty,
            String rewardString, String mission) {
        if (getPlayer().getLevel() < sLevel) {
            self.sayOk("ต้องมีเลเวล " + sLevel + " ขึ้นไปเท่านั้น จึงจะสามารถทำภารกิจนี้ได้",
                    ScriptMessageFlag.NpcReplacedByNpc);
            return;
        }
        if (getPlayer().getQuestStatus(questID) < 1) { // Quest Not Started
            for (String q : questSay) {
                self.say(q, ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
            }
            getSc().flushSay();
            MapleQuest.getInstance(questID).forceStart(getPlayer(), getNpc().getId(), null);
            getPlayer().updateOneInfo(501524, "state", "1");
            switch (questID) {
                case 501526: // Step 2 (Lv 40)
                    if (getPlayer().getLevel() >= 40) {
                        getPlayer().updateOneInfo(501524, "state", "2");
                        getPlayer().updateOneInfo(501526, "value", "1");
                    }
                    break;
                case 501527: // Step 3 (Lv 45)
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
            // if (!getPlayer().getQuest(questID).getQuest().canComplete(getPlayer(), null))
            // {
            if (getPlayer().getOneInfoQuestInteger(questID, "value") == 1 && (getPlayer().isQuestStarted(questID)/*
                                                                                                                  * ||
                                                                                                                  * ((
                                                                                                                  * questID
                                                                                                                  * -
                                                                                                                  * 501525
                                                                                                                  * + 1)
                                                                                                                  * ==
                                                                                                                  * getPlayer
                                                                                                                  * ().
                                                                                                                  * getOneInfoQuestInteger
                                                                                                                  * (
                                                                                                                  * 501524,
                                                                                                                  * "step"
                                                                                                                  * ))
                                                                                                                  */)) {
                if (1 == self.askYesNo("\r\n#b[ภารกิจ Step Up เลเวล " + sLevel
                        + "]#k สำเร็จแล้ว! ยินดีด้วยจ้ะ!\r\nจะรับ #bของรางวัลภารกิจ#k เลยไหมเอ่ย?\r\n\r\n\r\n#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#\r\n#b#i"
                        + reward + ":# #t" + reward + ":# " + rewardqty + " ชิ้น#k\r\n",
                        ScriptMessageFlag.NpcReplacedByNpc)) {
                    MapleItemInformationProvider mif = MapleItemInformationProvider.getInstance();
                    if (mif.getItemInformation(reward) != null) {
                        String itemName = mif.getName(reward);
                        if (target.exchange(reward, rewardqty) > 0) {
                            getPlayer().completeQuest(questID);
                            getPlayer().updateOneInfo(501524, "reward", rewardString);
                            getPlayer().updateOneInfo(501524, "state", "3");
                            getPlayer().send(CWvsContext.getScriptProgressMessage(reward,
                                    " ได้รับ " + itemName + " " + rewardqty + " ชิ้น เรียบร้อยแล้ว!"));
                        } else {
                            self.sayOk("ช่องเก็บของไม่เพียงพอจ้ะ กรุณาเคลียร์ช่องเก็บของแล้วลองใหม่อีกครั้งนะ!",
                                    ScriptMessageFlag.NpcReplacedByNpc);
                        }
                    } else {
                        self.sayOk("เกิดข้อผิดพลาดขึ้น");
                    }
                }
            } else {
                if (getPlayer().getQuestStatus(questID) == 2) {
                    self.say("เป็นภารกิจ Step Up ที่ทำสำเร็จไปแล้ว หรือยังไม่ถึงเลเวลที่กำหนด",
                            ScriptMessageFlag.NpcReplacedByNpc);
                } else {
                    int v = self.askMenu(
                            "#b#e[ภารกิจ Step Up เลเวล " + sLevel
                                    + "]#k กำลังดำเนินการอยู่!\r\n\r\n\r\n<เงื่อนไขการสำเร็จภารกิจ>#n\r\n"
                                    + mission + "\r\n\r\n#b#L0#ฟังคำอธิบายภารกิจอีกครั้ง#l#k",
                            ScriptMessageFlag.NpcReplacedByNpc);
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
