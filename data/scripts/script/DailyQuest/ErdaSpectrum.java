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
     * count -> Daily Entry Count
     * date -> 21/06/16 Last Played Date
     * ctype -> Message type? 0 is Failed
     * clear -> 1 if cleared, enables Reward Map and Map Effect
     */

    public void arcane1MO_Enter() {
        initNPC(MapleLifeFactory.getNPC(3003145));
        FieldSet fieldSet = fieldSet("ErdaSpectrumEnter");
        if (fieldSet == null) {
            self.sayOk("ขณะนี้ไม่สามารถดำเนินการ Erda Spectrum ได้ค่ะ");
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
        if (getPlayer().getMap().getId() == 450001550) { // Reward Map
            if (lastTime != null && lastTime.equals(now) && getPlayer().getOneInfoQuestInteger(34170, "clear") > 0) {
                int v = self.askMenu(
                        "#b#e<Erda Spectrum>#n#k\r\n\r\nร่างกายเป็นอย่างไรบ้างคะ? ไม่บาดเจ็บตรงไหนใช่ไหมคะ?\r\nรู้สึกทุกครั้งที่สำรวจเลยว่า ไม่มีวันไหนง่ายเลยจริงๆ ค่ะ\r\n#b#L0# รับรางวัล <Erda Spectrum>#l\r\n#b#L2# กลับไปโดยไม่รับรางวัล#l");
                switch (v) {
                    case 0: // Receive Rewards
                        String v0 = "ขอบคุณจริงๆ ค่ะที่ช่วยให้การสำรวจจบลงด้วยดี!\r\n" +
                                "ถึงแม้จะเป็นการสำรวจที่ยากกว่าที่คิด...\r\n" +
                                "#rสีสันของเออร์ดา#k สวยงามจริงๆ ใช่ไหมคะ?\r\n" +
                                "\r\n" +
                                "#i1712001##b#e#t1712001:##n#k #e2 ชิ้น#n\r\n" +
                                "#b#eEXP:#n#k#e87026100#n";
                        if (DBConfig.isGanglim) {
                            v0 = "ขอบคุณจริงๆ ค่ะที่ช่วยให้การสำรวจจบลงด้วยดี!\r\n" +
                                    "ถึงแม้จะเป็นการสำรวจที่ยากกว่าที่คิด...\r\n" +
                                    "#rสีสันของเออร์ดา#k สวยงามจริงๆ ใช่ไหมคะ?\r\n" +
                                    "\r\n" +
                                    "#i1712001##b#e#t1712001:##n#k #e2 ชิ้น#n\r\n" +
                                    "#b#eEXP:#n#k#e87026100000#n\r\n" +
                                    "#b#e100 Ganglim Point#n#k";
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
                            getPlayer().updateOneInfo(34170, "count",
                                    String.valueOf(getPlayer().getOneInfoQuestInteger(34170, "count") + 1));
                            getPlayer().updateOneInfo(34170, "clear", "0");
                        } else {
                            self.sayOk("ช่องเก็บของไม่พอค่ะ กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่อีกครั้งนะคะ",
                                    ScriptMessageFlag.NpcReplacedByNpc);
                        }
                        break;
                    case 2: // Give up and leave without rewards
                        self.say("เอ๊ะ? จะกลับหรอคะ?\r\nแต่ว่า #rยังไม่ได้รับของรางวัลเลยนะคะ#k...",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        if (1 == self.askYesNo(
                                "ต้องการจบการสำรวจและกลับหมู่บ้านจริงๆ หรอคะ?\r\n#r(แม้ออกจากที่นี่โดยไม่รับรางวัล จำนวนครั้งที่เข้าได้ก็จะถูกหัก 1 ครั้งค่ะ)",
                                ScriptMessageFlag.NpcReplacedByNpc)) {
                            getPlayer().updateOneInfo(34170, "count",
                                    String.valueOf(getPlayer().getOneInfoQuestInteger(34170, "count") + 1));
                            getPlayer().updateOneInfo(34170, "clear", "0");
                            target.registerTransferField(450001000);
                        } else {
                            self.sayOk("ใช่ไหมคะ! รับรางวัลจากการสำรวจก่อนกลับ ฉันถึงจะสบายใจค่ะ",
                                    ScriptMessageFlag.NpcReplacedByNpc);
                        }
                        break;
                }
            } else {
                int v = self.askMenu(
                        "#b#e<Erda Spectrum>#n#k\r\n\r\nร่างกายเป็นอย่างไรบ้างคะ? ไม่บาดเจ็บตรงไหนใช่ไหมคะ?\r\nรู้สึกทุกครั้งที่สำรวจเลยว่า ไม่มีวันไหนง่ายเลยจริงๆ ค่ะ\r\n#L0# กลับหมู่บ้าน#l");
                if (v == 0) {
                    self.say(
                            "น่าเสียดายที่การสำรวจครั้งนี้ยังไม่สำเร็จ แต่ครั้งหน้า #rต้องทำได้ดีแน่นอนค่ะ#k... ถ้าอย่างนั้น ขอให้โชคดีนะคะ!",
                            ScriptMessageFlag.NpcReplacedByNpc);
                    target.registerTransferField(100000000);
                }
            }

        } else {
            int v = -1;
            if (lastTime != null && lastTime.equals(now) && getPlayer().getOneInfoQuestInteger(34170, "count") > 0) {
                v = self.askMenu(
                        "#b#e<Erda Spectrum>#n#k\r\nคุณรู้ไหมคะว่า ภายใน #rเออร์ดา (Erda)#k เองก็มีสีสันที่เป็นเอกลักษณ์อยู่ด้วย?\r\nฉันค้นพบความจริงนี้ตอนที่สร้าง #bErda Condenser#k เพื่อสกัดเออร์ดาจากรอบๆ ค่ะ\r\nแต่ว่า... ฉันได้รับบาดเจ็บ เลยต้องการคนช่วย...\r\n#b#L0# เข้าสู่ <Erda Spectrum>#l\r\n#L1# ฟังคำอธิบาย#l\r\n#L2# ตรวจสอบจำนวนครั้งที่สำเร็จภารกิจวันนี้#l#k\r\n\r\n\r\n#e*สามารถเคลียร์ได้วันละ 1 ครั้ง\r\n*รางวัลสูงสุดของวันนี้:   \r\n#i1712001##b#e#t1712001:##n #e10 ชิ้น");
            } else {
                if (lastTime != null && !lastTime.equals(now)) {
                    getPlayer().updateOneInfo(34170, "count", "0");
                }
                v = self.askMenu(
                        "#b#e<Erda Spectrum>#n#k\r\nคุณรู้ไหมคะว่า ภายใน #rเออร์ดา (Erda)#k เองก็มีสีสันที่เป็นเอกลักษณ์อยู่ด้วย?\r\nฉันค้นพบความจริงนี้ตอนที่สร้าง #bErda Condenser#k เพื่อสกัดเออร์ดาจากรอบๆ ค่ะ\r\nแต่ว่า... ฉันได้รับบาดเจ็บ เลยต้องการคนช่วย...\r\n#b#L0# เข้าสู่ <Erda Spectrum>#l\r\n#L1# ฟังคำอธิบาย#l\r\n#L2# ตรวจสอบจำนวนครั้งที่สำเร็จภารกิจวันนี้#l#k\r\n\r\n\r\n#e*สามารถเคลียร์ได้วันละ 1 ครั้ง\r\n*รางวัลสูงสุดของวันนี้:   \r\n#i1712001##b#e#t1712001:##n #e0 ชิ้น");
            }
            switch (v) {

                case 0: { // Enter <Erda Spectrum>
                    if (getPlayer().getParty() != null
                            && getPlayer().getParty().getLeader().getId() == getPlayer().getId()) {
                        int dailyCount = getPlayer().getOneInfoQuestInteger(34170, "count");
                        int amount = 0;
                        if (getPlayer().getLevel() >= 210) {
                            amount++;
                        }
                        if (getPlayer().getLevel() >= 220) {
                            amount++;
                        }
                        if (dailyCount >= 1 && dailyCount < 3 && amount > 0) {
                            if (1 == self.askYesNo(
                                    "#b#ho##k #b#e<Erda Spectrum>#n#k สามารถสำเร็จภารกิจได้ทันทีค่ะ เมื่อสำเร็จภารกิจทันที จำนวนครั้งที่เข้าได้จะถูก #r#eหักออก 1 ครั้ง#n#k นะคะ!\r\n#r(หากยกเลิก จะดำเนินการเข้าสู่ภารกิจ)#k#e\r\n\r\n◆จำนวนครั้งสำเร็จทันทีที่เหลือวันนี้ : #b"
                                            + (amount - (dailyCount - 1))
                                            + " ครั้ง#k\r\n◆ของรางวัลสำเร็จทันที : #b#t1712001:# 10 ชิ้น")) {
                                if (target.exchange(1712001, 10) > 0) {
                                    getPlayer().updateOneInfo(34170, "count", String.valueOf((dailyCount + 1)));
                                    self.sayOk("มอบรางวัลสำเร็จภารกิจทันทีให้แล้วค่ะ กรุณาตรวจสอบในกระเป๋านะคะ",
                                            ScriptMessageFlag.NpcReplacedByNpc);
                                } else {
                                    self.sayOk("ช่องเก็บของไม่พอค่ะ กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่อีกครั้งนะคะ",
                                            ScriptMessageFlag.NpcReplacedByNpc);
                                }
                            } else {
                                int enter = fieldSet.enter(target.getId(), 0);
                                if (enter == -1)
                                    self.say(
                                            "ไม่สามารถเข้าได้เนื่องจากข้อผิดพลาดที่ไม่ทราบสาเหตุ กรุณาลองใหม่อีกครั้งในภายหลังค่ะ");
                                else if (enter == 1)
                                    self.say(
                                            "หากไม่ได้อยู่ในปาร์ตี้จะไม่สามารถเข้าได้ค่ะ กรุณาสร้างปาร์ตี้ #b1~3 คน#k แล้วลองใหม่อีกครั้งนะคะ");
                                else if (enter == 2)
                                    self.say("กรุณาให้หัวหน้าปาร์ตี้เป็นคนดำเนินการค่ะ");
                                else if (enter == 3)
                                    self.say("ต้องมีสมาชิกในปาร์ตี้อย่างน้อย " + fieldSet.minMember
                                            + " คนขึ้นไป ถึงจะเริ่มภารกิจได้ค่ะ");
                                else if (enter == 4)
                                    self.say("เลเวลของสมาชิกในปาร์ตี้ต้องไม่ต่ำกว่า " + fieldSet.minLv
                                            + " ถึงจะเข้าได้ค่ะ");
                                else if (enter == 5)
                                    self.say("สมาชิกปาร์ตี้ทุกคนต้องอยู่ที่นี่ถึงจะเริ่มภารกิจได้ค่ะ");
                                else if (enter == 6)
                                    self.say("มีปาร์ตี้อื่นกำลังทำภารกิจอยู่ค่ะ");
                                else if (enter == -2)
                                    self.say(
                                            "มีสมาชิกในปาร์ตี้ที่ทำครบจำนวนครั้งต่อวันแล้วค่ะ กรุณาตรวจสอบอีกครั้งนะคะ");
                            }
                        } else {
                            int enter = fieldSet.enter(target.getId(), 0);
                            if (enter == -1)
                                self.say(
                                        "ไม่สามารถเข้าได้เนื่องจากข้อผิดพลาดที่ไม่ทราบสาเหตุ กรุณาลองใหม่อีกครั้งในภายหลังค่ะ");
                            else if (enter == 1)
                                self.say(
                                        "หากไม่ได้อยู่ในปาร์ตี้จะไม่สามารถเข้าได้ค่ะ กรุณาสร้างปาร์ตี้ #b1~3 คน#k แล้วลองใหม่อีกครั้งนะคะ");
                            else if (enter == 2)
                                self.say("กรุณาให้หัวหน้าปาร์ตี้เป็นคนดำเนินการค่ะ");
                            else if (enter == 3)
                                self.say("ต้องมีสมาชิกในปาร์ตี้อย่างน้อย " + fieldSet.minMember
                                        + " คนขึ้นไป ถึงจะเริ่มภารกิจได้ค่ะ");
                            else if (enter == 4)
                                self.say(
                                        "เลเวลของสมาชิกในปาร์ตี้ต้องไม่ต่ำกว่า " + fieldSet.minLv + " ถึงจะเข้าได้ค่ะ");
                            else if (enter == 5)
                                self.say("สมาชิกปาร์ตี้ทุกคนต้องอยู่ที่นี่ถึงจะเริ่มภารกิจได้ค่ะ");
                            else if (enter == 6)
                                self.say("มีปาร์ตี้อื่นกำลังทำภารกิจอยู่ค่ะ");
                            else if (enter == -2)
                                self.say("มีสมาชิกในปาร์ตี้ที่ทำครบจำนวนครั้งต่อวันแล้วค่ะ กรุณาตรวจสอบอีกครั้งนะคะ");
                        }
                    } else {
                        int enter = fieldSet.enter(target.getId(), 0);
                        if (enter == -1)
                            self.say(
                                    "ไม่สามารถเข้าได้เนื่องจากข้อผิดพลาดที่ไม่ทราบสาเหตุ กรุณาลองใหม่อีกครั้งในภายหลังค่ะ");
                        else if (enter == 1)
                            self.say(
                                    "หากไม่ได้อยู่ในปาร์ตี้จะไม่สามารถเข้าได้ค่ะ กรุณาสร้างปาร์ตี้ #b1~3 คน#k แล้วลองใหม่อีกครั้งนะคะ");
                        else if (enter == 2)
                            self.say("กรุณาให้หัวหน้าปาร์ตี้เป็นคนดำเนินการค่ะ");
                        else if (enter == 3)
                            self.say("ต้องมีสมาชิกในปาร์ตี้อย่างน้อย " + fieldSet.minMember
                                    + " คนขึ้นไป ถึงจะเริ่มภารกิจได้ค่ะ");
                        else if (enter == 4)
                            self.say("เลเวลของสมาชิกในปาร์ตี้ต้องไม่ต่ำกว่า " + fieldSet.minLv + " ถึงจะเข้าได้ค่ะ");
                        else if (enter == 5)
                            self.say("สมาชิกปาร์ตี้ทุกคนต้องอยู่ที่นี่ถึงจะเริ่มภารกิจได้ค่ะ");
                        else if (enter == 6)
                            self.say("มีปาร์ตี้อื่นกำลังทำภารกิจอยู่ค่ะ");
                        else if (enter == -2)
                            self.say("มีสมาชิกในปาร์ตี้ที่ทำครบจำนวนครั้งต่อวันแล้วค่ะ กรุณาตรวจสอบอีกครั้งนะคะ");
                    }
                    break;
                }
                case 1: { // Listen to Nina's explanation
                    int vv = self.askMenu(
                            "ฉันคือ Nina นักวิจัยที่คอยสังเกตและบันทึกข้อมูลเออร์ดาในแถบ Vanishing Journey ค่ะ\r\nระหว่างการสำรวจครั้งก่อนเกิดเหตุฉุกเฉินขึ้น ฉันเลยได้รับบาดเจ็บเล็กน้อย เลยกำลังมองหาคนช่วยอยู่ค่ะ\r\n\r\n#e<Erda Spectrum>#n\r\n\r\n#e1. จำนวนผู้เข้าร่วม:#n 1~3 คน\r\n#e2. เวลาจำกัด:#n 10 นาที\r\n#e3. จำนวนครั้งที่สำเร็จได้ต่อวัน:#n 3 ครั้ง (นับเฉพาะเมื่อสำเร็จภารกิจ)\r\n#e4. ของรางวัล:#n #i1712001##b#e#t1712001:##n#k + EXP\r\n\r\n\r\n#L0#ฟังคำอธิบายเพิ่มเติม#l");
                    if (vv == 0) {
                        int vvv = -2;
                        while (vvv != 4 && vvv != 5 && vvv != -1 && !getSc().isStop()) {
                            vvv = self.askMenu(
                                    "อยากทราบเรื่องอะไรเป็นพิเศษไหมคะ?#b\r\n#L0#<ความสำคัญของการช่วยสำรวจ>#l\r\n#L1#<วิธีการสำรวจ>#l\r\n#L2#<วิธีรับและใช้ Erda>#l\r\n#L3#<ขั้นตอนและของรางวัล>#l\r\n#L5#<วิธีทำ Daily Quest อย่างง่าย>#l\r\n#L4#ไม่ต้องการฟังคำอธิบาย#l#k");
                            switch (vvv) {
                                case 0: // <Need for Investigation Support>
                                    self.say(
                                            "#e<ความสำคัญของการช่วยสำรวจ>#n\r\n\r\nบริเวณ Vanishing Journey เต็มไปด้วย #bErda#k จำนวนมาก\r\nคุณคงรู้อยู่แล้วใช่ไหมคะว่า #bErda#k สามารถเปลี่ยนรูปร่างได้หลากหลายตามสภาพแวดล้อม?\r\nฉันคิดว่า #rพลังงานที่ไม่สามารถระบุได้แน่ชัด#k นี่แหละคือเสน่ห์ของ Erda ค่ะ");
                                    self.say(
                                            "#e<ความสำคัญของการช่วยสำรวจ>#n\r\n\r\nฉันเลยประดิษฐ์ #bErda Condenser#k ขึ้นมาเพื่อการสำรวจ Erda\r\nเมื่อใช้ #bErda Condenser#k เราจะสามารถสกัด Erda จากรอบๆ ได้ง่ายขึ้น และสามารถ #rรวบรวมไว้ในที่เดียว#k ได้ค่ะ\r\n\r\nแต่ว่า ในระหว่างขั้นตอนการรวบรวม ฉันได้พบสิ่งที่น่าสนใจเข้าค่ะ");
                                    self.say(
                                            "#e<ความสำคัญของการช่วยสำรวจ>#n\r\n\r\nนั่นก็คือ #bสีของ Erda#k ค่ะ!\r\n\r\nฉันหลงใหลในสิ่งนี้เข้าอย่างจังเลยล่ะค่ะ\r\nสีสันที่งดงามและเปล่งประกาย มันกระตุ้นความอยากรู้อยากเห็นของฉันมาก!\r\n\r\nแต่ฉันยังไม่ค้นพบปัจจัยที่กำหนดสี หรือความแตกต่างของแต่ละสีเลย...\r\nดังนั้น เพื่อเก็บตัวอย่างให้มากขึ้น ฉันจึงต้องขอความช่วยเหลือจากเหล่านักรบแถวนี้ค่ะ");
                                    getSc().flushSay();
                                    break;
                                case 1: // <Investigation Method>
                                    self.say(
                                            "#e<วิธีการสำรวจ>#n\r\n\r\nเป้าหมายของการสำรวจคือการส่ง Erda ที่สกัดได้โดยใช้ #bErda Condenser#k ที่ติดตั้งอยู่ตรงกลางค่ะ\r\n\r\n- Erda Condenser จะทำงานด้วย Erda ที่ได้รับจากการ #rกำจัดมอนสเตอร์#k\r\n- เมื่อ #rโจมตี#k Erda Condenser ที่ทำงานอยู่ จะสกัด Erda ที่รวบรวมไว้ออกมา\r\n- ใช้ปุ่ม #bเก็บของ/สนทนา#k เพื่อผลัก Erda ที่สกัดได้ไปยังโซนส่งที่มีสีตรงกัน");
                                    break;
                                case 2: // <How to Obtain and Use Erda>
                                    self.say(
                                            "#e<วิธีรับและใช้ Erda>#n\r\n\r\n- #bการรับ#k Erda:\r\n#rกำจัดมอนสเตอร์#k รอบๆ Erda Condenser หรือได้รับจากจุดที่ปรากฏขึ้นจากผลของเครื่องรวบรวม\r\n\r\n- #bการใช้#k Erda:\r\nใช้เป็นสื่อกลางในการ #rเปิดใช้งาน Erda Condenser#k หรือ #rใช้ฟังก์ชันพิเศษของ Erda Condenser#k\r\n(※ Erda Condenser ใช้งานด้วยปุ่ม #bNPC/เก็บของ#k)");
                                    break;
                                case 3: // <Process and Rewards>
                                    self.say(
                                            "#e<ขั้นตอนและของรางวัล>#n\r\n\r\nเมื่อเคลียร์ภารกิจ ไม่ว่าจะมี Erda เหลืออยู่เท่าไหร่ หรือใช้เวลาไปเท่าไหร่\r\nจะได้รับ #r#i1712001##t1712001# 2 ชิ้น\r\n\r\n#b+ ค่าประสบการณ์จำนวนหนึ่ง#k ค่ะ");
                                    break;
                                case 5: // <Easier Daily Quest> -> Script needs editing
                                    self.sayOk(
                                            "เพื่อให้สามารถทำ Daily Quest ของพื้นที่ Arcane River ใหม่ได้ง่ายขึ้น จึงให้โอกาส #b#eสำเร็จ <Erda Spectrum> ทันที#n#k ได้ทุกวันค่ะ การ #eสำเร็จทันที#n จะคำนวณจากคะแนนสูงสุดของวันที่ทำไว้ แต่มันจะไม่ให้รางวัลค่าประสบการณ์และไม่นับในความสำเร็จนะคะ อย่าลืมข้อนี้ด้วยนะ!\r\n\r\n\r\n#e#bจำนวนการสำเร็จทันทีของ <Erda Spectrum> ที่ใช้ได้วันนี้ (0/2)#n#k\r\n ▶พื้นที่ Chu Chu Island:  #r#eสามารถทำ Daily Quest ได้#n#k\r\n ▶พื้นที่ Lachelein:  #r#eสามารถทำ Daily Quest ได้#n#k");
                                    break;
                                case 4: // No more explanation needed
                                    break;
                            }
                        }
                    }
                    break;
                }
                case 2: { // Check remaining possible completions for today
                    int aa = 3;
                    if (lastTime != null && lastTime.equals(now)
                            && getPlayer().getOneInfoQuestInteger(34170, "count") > 0) {
                        aa = 3 - getPlayer().getOneInfoQuestInteger(34170, "count");
                    }
                    self.say("จำนวนครั้งที่คุณ #h0# สามารถสำเร็จภารกิจได้ในวันนี้คือ #r" + aa
                            + " ครั้ง#k ค่ะ\r\n\r\n(※ จะหักจำนวนครั้งเมื่อทำการ #rสำรวจจนเสร็จสิ้นแล้วกลับออกมา#k เท่านั้นค่ะ)");
                    break;
                }
            }
        }
    }

    @Script
    public void arcane1MO_1() {
        getPlayer().send(
                CField.startMapEffect("   ล่ามอนสเตอร์รอบๆ เพื่อรวบรวม Erda และเปิดใช้งาน Erda Condenser กันเถอะ!   ",
                        5120025, true, 15));
        getPlayer().send(ErdaSpectrumErdaInfo(0, 0, "red"));
        getPlayer().send(ErdaSpectrumPhase(2));
        getPlayer().send(ErdaSpectrumTimer(600000, 10));
    }

    @Script
    public void arcane1MO_2_1() {
        objects.fields.fieldset.instance.ErdaSpectrum fieldSet = (objects.fields.fieldset.instance.ErdaSpectrum) getPlayer()
                .getMap().getFieldSetInstance();
        if (fieldSet != null) {
            fieldSet.setVar("Phase", "1");
            getPlayer().send(CField.startMapEffect(
                    "  ป้องกันไม่ให้มอนสเตอร์เพิ่มจำนวนขึ้น ก่อนที่ Erda Condenser จะทำงานหนักเกินไป!  ", 5120025, true,
                    15));
            getPlayer().send(
                    CField.UIPacket.sendBigScriptProgressMessage(
                            "ดูเหมือนจะถูกดึงดูดมาด้วย Erda รอบๆ มีแสงสีฟ้าสั่นไหวและหายไป",
                            FontType.NanumGothicBold, FontColorType.SkyBlue));
            fieldSet.startCrackMap();
        }
    }

    @Script
    public void arcane1MO_2_2() { // Landworm Map
        objects.fields.fieldset.instance.ErdaSpectrum fieldSet = (objects.fields.fieldset.instance.ErdaSpectrum) getPlayer()
                .getMap().getFieldSetInstance();
        if (fieldSet != null) {
            fieldSet.setVar("Phase", "1");
            getPlayer().send(
                    CField.startMapEffect("  ไล่ตาม Arma Junior ที่พยายามจะทำลาย Erda Condenser ให้ออกไป!  ", 5120025,
                            true, 15));
            getPlayer().send(
                    CField.UIPacket.sendBigScriptProgressMessage(
                            "ดูเหมือนจะถูกดึงดูดมาด้วย Erda รอบๆ มีแสงสีแดงสั่นไหวและหายไป",
                            FontType.NanumGothicBold, FontColorType.Pink));
            fieldSet.startWormMap();
        }
    }

    @Script
    public void enter_450001550() {
        // If cleared, show clear effect!
        if (getPlayer().getOneInfoQuestInteger(34170, "ctype") == 1) { // Cleared
            getPlayer().send(CField.MapEff("Map/Effect.img/killing/clear"));
            getPlayer().updateOneInfo(34170, "ctype", "-1");
        } else if (getPlayer().getOneInfoQuestInteger(34170, "ctype") == 0) {
            getPlayer().send(CField.MapEff("Map/Effect.img/killing/fail"));
            getPlayer().updateOneInfo(34170, "ctype", "-1");
        }
    }
}
