package script.DailyQuest;

import network.models.CField;
import objects.fields.fieldset.childs.SpiritSaviorEnter;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SpiritSavior extends ScriptEngineNPC {

    /**
     * qexID : 16214
     * count -> Daily Entry Count
     * date -> 21/06/16 Last Played Date
     * coin -> Coins obtained today
     * todayrecord -> Today's Best Record
     */

    /**
     * qexID : 16215
     * point -> Current Score
     * play -> Attempt Count?
     * saved -> Number of spirits currently saving
     * life -> 100 on start
     * chase -> Chasing Monster Stage?
     * 8644301 : Chasing Monster Stage 1
     */

    public void spiritSavior_NPC() {
        initNPC(MapleLifeFactory.getNPC(3003381));
        SpiritSaviorEnter fieldSet = (SpiritSaviorEnter) fieldSet("SpiritSaviorEnter");
        if (fieldSet == null) {
            self.sayOk("ขณะนี้ไม่สามารถดำเนินการ Spirit Savior ได้งับ");
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
        LocalDate lastTime = null;
        LocalDate now = LocalDate.now();
        try {
            lastTime = LocalDate.parse(getPlayer().getOneInfo(16214, "date"), formatter);
        } catch (Exception e) {
            lastTime = null;
        }
        if (lastTime == null) {
            getPlayer().updateOneInfo(16214, "date", now.format(formatter));
            getPlayer().updateOneInfo(16214, "count", "0");
        }
        if (lastTime != null && !lastTime.isEqual(now)) {
            getPlayer().updateOneInfo(16214, "count", "0");
        }
        // self.say("Save many of my friends?\r\nRescue score is #b#e10500
        // points#n#k!\r\nIf so, I can give #r#eSpirit Coin 10#n#k!");
        // self.say("Please help next time too!");

        // If not enough saved to get coins
        // self.say("Umm... You couldn't save many of my friends!");
        // self.say("Please save more friends next time!");

        // Instant completion dialogue
        // self.askYesNo("#b#ho!##k You can complete #b#e<Spirit Savior>#n#k
        // immediately. Immediate
        // completion will deduct #r#e1 entry count#n#k so don't forget!\r\n#r(Entry
        // proceeds upon cancellation.)#k#e\r\n\r\n◆Today's remaining instant
        // completions: #b1#k\r\n◆Instant
        // Completion Reward: #b#t4310235:# 10");
        // self.sayOk("Given instant completion reward!");

        // If done
        // self.say("You cannot challenge #b#e<Spirit Savior>#n#k anymore
        // today.\r\nPlease come back tomorrow.\r\n\r\n#r#e(3 entries per day)#n#k");
        if (getPlayer().getMap().getId() == 921172400) { // รางวัลแผนที่
            if (lastTime != null && lastTime.isEqual(now)) {
                int point = getPlayer().getOneInfoQuestInteger(16215, "point");
                if (point >= 1000) {
                    int todayCoin = getPlayer().getOneInfoQuestInteger(16214, "coin");
                    self.say(
                            "ช่วยเพื่อนๆ ของฉันมาได้เยอะเลยเหรองับ?\r\nคะแนนช่วยเหลือคือ #b#e" + point
                                    + " คะแนน#n#k สินะงับ!\r\nถ้าขนาดนี้ ฉันให้ #r#eSpirit Coin " + (point / 1000)
                                    + " เหรียญ#n#k ได้เลยงับ!\r\n(ได้รับเหรียญสูงสุดต่อวัน " + todayCoin + " / 30)",
                            ScriptMessageFlag.NoEsc);
                    self.say("ครั้งหน้าก็ฝากด้วยนะงับ!", ScriptMessageFlag.NoEsc);
                    int giveCoin = (point / 1000);
                    if (todayCoin + giveCoin > 30) {
                        int what = giveCoin - ((todayCoin + giveCoin) - 30);
                        giveCoin = what;
                    }
                    if (getPlayer().getOneInfoQuestInteger(16215, "point") > 0) {
                        if (target.exchange(4310235, giveCoin) > 0) {
                            getPlayer().updateOneInfo(16214, "coin",
                                    String.valueOf(getPlayer().getOneInfoQuestInteger(16214, "coin") + giveCoin));
                            getPlayer().updateOneInfo(16215, "point", "0");
                            target.registerTransferField(450005000);
                        } else {
                            self.sayOk("ช่องเก็บของอื่นๆ เต็มงับ!", ScriptMessageFlag.NoEsc);
                        }
                    } else {
                        target.registerTransferField(450005000);
                    }
                } else {
                    self.say("งือ... ช่วยเพื่อนๆ ของฉันมาได้ไม่เยอะเลยงับ!");
                    self.say("ครั้งหน้าช่วยเพื่อนๆ มาให้เยอะกว่านี้นะงับ!");
                    target.registerTransferField(450005000);
                }
            } else {
                self.say("งือ... ช่วยเพื่อนๆ ของฉันมาได้ไม่เยอะเลยงับ!");
                self.say("ครั้งหน้าช่วยเพื่อนๆ มาให้เยอะกว่านี้นะงับ!");
                target.registerTransferField(450005000);
            }
        } else {
            int v = -1;
            int count = getPlayer().getOneInfoQuestInteger(16214, "count");
            int todayrecordCoin = getPlayer().getOneInfoQuestInteger(16214, "todayrecord") / 1000;
            int canD = 3;
            if (getPlayer().getLevel() >= 230)
                canD--;
            if (getPlayer().getLevel() >= 235)
                canD--;
            if (lastTime != null && lastTime.isEqual(now) && getPlayer().getOneInfoQuestInteger(16214, "count") > 0) {
                if (getPlayer().getLevel() >= 230) {
                    v = self.askMenu(
                            "#b#e<Spirit Savior>#n#k\r\n รีบไปช่วยเพื่อนๆ ของฉันกันเถอะงับ!\r\n\r\n#b#L0# ท้าทาย <Spirit Savior>#l\r\n#L1# แลกเปลี่ยน Spirit Coin#l\r\n#L2# ฟังคำอธิบาย#l#k\r\n\r\n\r\n#e*เคลียร์ "
                                    + canD
                                    + " ครั้งแล้วจะสามารถสำเร็จทันทีได้\r\n*บันทึกรางวัลสูงสุดของวันนี้:   \r\n#i4310235##b#e#t4310235:##n #e"
                                    + todayrecordCoin + " เหรียญ");
                } else {
                    v = self.askMenu(
                            "#b#e<Spirit Savior>#n#k\r\n รีบไปช่วยเพื่อนๆ ของฉันกันเถอะงับ!\r\n\r\n#b#L0# ท้าทาย <Spirit Savior>#l\r\n#L1# แลกเปลี่ยน Spirit Coin#l\r\n#L2# ฟังคำอธิบาย#l#k");
                }
            } else {
                v = self.askMenu(
                        "#b#e<Spirit Savior>#n#k\r\n รีบไปช่วยเพื่อนๆ ของฉันกันเถอะงับ!\r\n\r\n#b#L0# ท้าทาย <Spirit Savior>#l\r\n#L1# แลกเปลี่ยน Spirit Coin#l\r\n#L2# ฟังคำอธิบาย#l#k\r\n\r\n\r\n#e*เคลียร์ 2 ครั้งแล้วจะสามารถสำเร็จทันทีได้\r\n*บันทึกรางวัลสูงสุดของวันนี้:   \r\n#i4310235##b#e#t4310235:##n #e0 เหรียญ");
            }
            switch (v) {

                case 0: { // Challenge <Spirit Savior>
                    if (count >= canD && count < 3) {
                        if (1 == self.askYesNo(
                                "#b#ho!##k #b#e<Spirit Savior>#n#k สามารถสำเร็จภารกิจได้ทันทีงับ เมื่อสำเร็จภารกิจทันที จำนวนครั้งที่เข้าได้จะถูก #r#eหักออก 1 ครั้ง#n#k อย่าลืมนะงับ!\r\n#r(หากยกเลิก จะดำเนินการเข้าสู่ภารกิจ)#k#e\r\n\r\n◆จำนวนครั้งสำเร็จทันทีที่เหลือวันนี้ : #b"
                                        + (3 - count) + " ครั้ง#k\r\n◆ของรางวัลสำเร็จทันที : #b#t4310235:# "
                                        + todayrecordCoin + " เหรียญ")) {
                            int todayCoin = getPlayer().getOneInfoQuestInteger(16214, "coin");
                            int giveCoin = todayrecordCoin;
                            boolean fuck = false;
                            if (todayCoin + giveCoin > 30) {
                                fuck = true;
                                int what = giveCoin - ((todayCoin + giveCoin) - 30);
                                giveCoin = what;
                            }
                            if (exchange(4310235, giveCoin) > 0) {
                                getPlayer().updateOneInfo(16214, "coin",
                                        String.valueOf(getPlayer().getOneInfoQuestInteger(16214, "coin") + giveCoin));
                                getPlayer().updateOneInfo(16214, "count", String.valueOf(count + 1));
                                if (fuck) {
                                    self.sayOk(
                                            "มอบรางวัลสำเร็จภารกิจแเล้วงับ!\r\nวันนี้ได้รับเหรียญไป #r#e30 เหรียญขึ้นไป#n#k แล้ว คงให้เพิ่มไม่ได้แล้วงับ...\r\n");
                                } else {
                                    self.sayOk("มอบรางวัลสำเร็จภารกิจแล้วงับ!");
                                }
                            } else {
                                self.sayOk("ช่องเก็บของอื่นๆ เต็มงับ!", ScriptMessageFlag.NoEsc);
                            }
                        } else {
                            int enter = fieldSet.enter(target.getId(), 0);
                            if (enter == -1)
                                self.say(
                                        "ไม่สามารถเข้าได้เนื่องจากข้อผิดพลาดที่ไม่ทราบสาเหตุ กรุณาลองใหม่อีกครั้งในภายหลังงับ");
                            else if (enter == 1)
                                self.sayOk(
                                        "<Spirit Savior> สามารถเข้าท้าทายได้เพียงคนเดียวเท่านั้นงับ\r\nกรุณาออกจากปาร์ตี้แล้วลองใหม่อีกครั้งนะงับ");
                            else if (enter == 2)
                                self.say("ต้องมีเลเวลอย่างน้อย " + fieldSet.minLv
                                        + " ขึ้นไป ถึงจะช่วยเพื่อนๆ ของฉันได้นะงับ");
                            else if (enter == 3)
                                self.say("ขณะนี้ห้องภารกิจเต็มทุกห้องแล้ว ไม่สามารถเข้าได้งับ");
                            else if (enter == -2)
                                self.sayOk(
                                        "วันนี้ไม่สามารถท้าทาย #b#e<Spirit Savior>#n#k ได้อีกแล้วงับ\r\nแวะมาใหม่พรุ่งนี้นะงับ\r\n\r\n#r#e(เข้าได้ 3 ครั้งต่อวัน)#n#k");
                        }
                    } else {
                        if (1 == self.askYesNo(
                                "รีบไปช่วยเพื่อนๆ ของฉันกันเถอะงับ จะเริ่มท้าทายเลยไหมงับ?\r\n\r\n#bจำนวนการท้าทายวันนี้ "
                                        + count + " / 3#k")) {
                            int enter = fieldSet.enter(target.getId(), 0);
                            if (enter == -1)
                                self.say(
                                        "ไม่สามารถเข้าได้เนื่องจากข้อผิดพลาดที่ไม่ทราบสาเหตุ กรุณาลองใหม่อีกครั้งในภายหลังงับ");
                            else if (enter == 1)
                                self.sayOk(
                                        "<Spirit Savior> สามารถเข้าท้าทายได้เพียงคนเดียวเท่านั้นงับ\r\nกรุณาออกจากปาร์ตี้แล้วลองใหม่อีกครั้งนะงับ");
                            else if (enter == 2)
                                self.say("ต้องมีเลเวลอย่างน้อย " + fieldSet.minLv
                                        + " ขึ้นไป ถึงจะช่วยเพื่อนๆ ของฉันได้นะงับ");
                            else if (enter == 3)
                                self.say("ขณะนี้ห้องภารกิจเต็มทุกห้องแล้ว ไม่สามารถเข้าได้งับ");
                            else if (enter == -2)
                                self.sayOk(
                                        "วันนี้ไม่สามารถท้าทาย #b#e<Spirit Savior>#n#k ได้อีกแล้วงับ\r\nแวะมาใหม่พรุ่งนี้นะงับ\r\n\r\n#r#e(เข้าได้ 3 ครั้งต่อวัน)#n#k");
                        } else {
                            self.sayOk("ถ้ามาช้าไป เพื่อนๆ ของฉันอาจจะไม่รอดก็ได้นะงับ...");
                        }
                    }
                    break;
                }
                case 1: { // Exchange Spirit Coins (Needs fix!!)
                    if (getPlayer().getItemQuantity(4310235, false) > 0) {
                        int number = self.askNumber(
                                "#b#i4310235:##t4310235##k แลกกับ #r#i1712004:##t1712004##k ไหมงับ?\r\n(#b#t4310235# 3 เหรียญ#k = #r#t1712004# 1 ชิ้น#k)\r\n\r\nสามารถแลกได้สูงสุด #r#e"
                                        + getPlayer().getItemQuantity(4310235, false) / 3 + " ชิ้น#n#k งับ",
                                1, 1, getPlayer().getItemQuantity(4310235, false) / 3);
                        if (exchange(4310235, -number, 1712004, number) > 0) {
                            self.sayOk("นี่งับ! ฉันให้ #b#i1712004:##t1712004#" + number
                                    + " ชิ้น#k งับ!\r\nครั้งหน้าก็ฝากด้วยนะงับ!");
                        } else {
                            self.sayOk("ช่องเก็บของไม่พอ หรือ Spirit Coin ไม่พอนะงับ! ตรวจสอบดูหน่อยงับ!");
                        }
                    } else {
                        self.sayOk("ถ้าไม่มี Spirit Coin ก็แลก Arcane Symbol ไม่ได้นะงับ!");
                    }
                    break;
                }
                case 2: { // Listen to explanation
                    int vvv = -2;
                    while (vvv != 2 && vvv != 100 && vvv != -1 && !getSc().isStop()) {
                        vvv = self.askMenu(
                                "อยากรู้อะไรเหรองับ?\r\n#L0# #eกฎของ Spirit Savior#n#l\r\n#L1# #eรางวัล Spirit Savior#n#l\r\n#L2# #eทำ Daily Quest อย่างง่าย#n#l"
                                        + "\r\n#L100# #eไม่ฟังคำอธิบาย#n#l");
                        switch (vvv) {
                            case 0: // Spirit Savior Rules
                                self.say(
                                        "#e<กฎของ Spirit Savior>#n\r\n\r\n#eก่อนเวลาจำกัดจะหมด / ก่อนค่าป้องกันจะลดจนหมด#n  ต้องช่วยเหลือ #b#eRock Spirit ที่ถูกขัง#n#k ให้ได้มากที่สุดงับ!\r\nสามารถพา #b#eRock Spirit ที่ถูกขัง#n#k ไปด้วยได้โดยการ #r#eกดปุ่มเก็บของ/สนทนา#n#k งับ!");
                                self.say(
                                        "#e<กฎของ Spirit Savior>#n\r\n\r\nสามารถพาเพื่อนๆ ไปด้วยได้ #b#eสูงสุด 5 ตัว#n#k นะงับ!\r\nหากพาเพื่อนๆ กลับมาส่งที่ #b#eจุดเริ่มต้น#n#k ได้อย่างปลอดภัย\r\nก็จะได้รับ #e'คะแนนช่วยเหลือ'#n งับ!\r\n#b#eยิ่งช่วยเพื่อนๆ ได้ครั้งละมากเท่าไหร่#n#k ก็จะยิ่งได้คะแนนสูงขึ้นนะงับ!\r\n\r\n#e1 ตัว - 200 คะแนน\r\n2 ตัว - 500 คะแนน\r\n3 ตัว - 1,000 คะแนน\r\n4 ตัว - 1,500 คะแนน\r\n5 ตัว - 2,500 คะแนน#n");
                                self.say(
                                        "#e<กฎของ Spirit Savior>#n\r\n\r\nแต่พวก #r#eSpirit ตัวร้าย#n#k คงไม่ยอมให้พาเพื่อนๆ ไปง่ายๆ หรอกงับ\r\nเมื่อผ่านไปสักพัก จะมี #r#eSpirit Fragment#n#k ปรากฏขึ้นทั่วแผนที่ หากโดนพวกมัน #b#eค่าป้องกัน#n#k จะลดลงนะงับ");
                                self.say(
                                        "#e<กฎของ Spirit Savior>#n\r\n\r\nและเมื่อเริ่มช่วยเพื่อนๆ #r#eToxic Spirit#n#k ก็จะเริ่มตามล่าเธองับ!\r\n#r#eToxic Spirit#n#k จะเริ่ม #eตัวใหญ่ขึ้นและเร็วขึ้น#n ยิ่งช่วยเพื่อนๆ ได้มากเท่าไหร่ ถ้าถูกมันโจมตีค่าป้องกันจะลดลงเยอะมาก และเพื่อนๆ ที่พามาจะหายไปทั้งหมดเลย... ระวังอย่าไปชนมันเข้านะงับ!");
                                getSc().flushSay();
                                break;
                            case 1: // Spirit Savior Rewards
                                self.say(
                                        "#e<รางวัล Spirit Savior>#n\r\n\r\nเมื่อช่วยเพื่อนๆ และได้รับ #b#eคะแนนช่วยเหลือ#n#k ทุกๆ #e1000 คะแนน#n จะได้รับ #b#i4310235:##t4310235##k 1 เหรียญงับ");
                                self.say(
                                        "#e<รางวัล Spirit Savior>#n\r\n\r\nนำ #b#i4310235:##t4310235# 3 เหรียญ#k มาแลกกับฉัน จะเปลี่ยนเป็น #r#i1712004##t1712004# 1 ชิ้น#k ให้งับ");
                                self.say(
                                        "#e<รางวัล Spirit Savior>#n\r\n\r\nสามารถท้าทายได้ #b#eวันละ 3 ครั้ง#n#k และได้รับ #r#eสูงสุด 30 เหรียญต่อวัน#n#k ฝากดูแลเพื่อนๆ ของฉันด้วยนะงับ!");
                                getSc().flushSay();
                                break;
                            case 2: // Easy Daily Quest
                                if (getPlayer().getLevel() < 230) {
                                    self.sayOk(
                                            "เพื่อให้สามารถทำ Daily Quest ของพื้นที่ Arcane River ใหม่ได้ง่ายขึ้น จึงให้โอกาส #b#eสำเร็จ Spirit Savior ทันที#n#k ได้ทุกวันงับ การ #eสำเร็จทันที#n จะคำนวณจากคะแนนสูงสุดของวันที่ทำไว้ แต่มันจะไม่ให้รางวัลค่าประสบการณ์และไม่นับในความสำเร็จนะงับ อย่าลืมข้อนี้ด้วยนะ!\r\n#r*การสำเร็จทันทีจะไม่ได้รับโบนัสเหรียญพยายามเข้านะงับ#k");
                                } else if (getPlayer().getLevel() >= 230 && getPlayer().getLevel() < 235) {
                                    self.sayOk(
                                            "เพื่อให้สามารถทำ Daily Quest ของพื้นที่ Arcane River ใหม่ได้ง่ายขึ้น จึงให้โอกาส #b#eสำเร็จ Spirit Savior ทันที#n#k ได้ทุกวันงับ การ #eสำเร็จทันที#n จะคำนวณจากคะแนนสูงสุดของวันที่ทำไว้ แต่มันจะไม่ให้รางวัลค่าประสบการณ์และไม่นับในความสำเร็จนะงับ อย่าลืมข้อนี้ด้วยนะ!\r\n#r*การสำเร็จทันทีจะไม่ได้รับโบนัสเหรียญพยายามเข้านะงับ#k\r\n\r\n\r\n#e#bจำนวนการสำเร็จทันทีของ <Spirit Savior> ที่ใช้ได้วันนี้ (0/1)#n#k\r\n ▶พื้นที่ Morass: #r#eสามารถทำ Daily Quest ได้#n#k\r\n ▶พื้นที่ Esfera: #e#kไม่สามารถทำ Daily Quest ได้#n#k");
                                } else {
                                    self.sayOk(
                                            "เพื่อให้สามารถทำ Daily Quest ของพื้นที่ Arcane River ใหม่ได้ง่ายขึ้น จึงให้โอกาส #b#eสำเร็จ Spirit Savior ทันที#n#k ได้ทุกวันงับ การ #eสำเร็จทันที#n จะคำนวณจากคะแนนสูงสุดของวันที่ทำไว้ แต่มันจะไม่ให้รางวัลค่าประสบการณ์และไม่นับในความสำเร็จนะงับ อย่าลืมข้อนี้ด้วยนะ!\r\n#r*การสำเร็จทันทีจะไม่ได้รับโบนัสเหรียญพยายามเข้านะงับ#k\r\n\r\n\r\n#e#bจำนวนการสำเร็จทันทีของ <Spirit Savior> ที่ใช้ได้วันนี้ (0/2)#n#k\r\n ▶พื้นที่ Morass: #r#eสามารถทำ Daily Quest ได้#n#k\r\n ▶พื้นที่ Esfera: #e#rสามารถทำ Daily Quest ได้#n#k");
                                }
                                break;
                            case 3: // What is Cheer Up! Bonus Spirit Coin?
                                // Content removed as it was commented out Korean
                                break;
                            case 100: // Stop listening to explanation
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
            LocalDate lastTime = null;
            LocalDate now = LocalDate.now();
            try {
                lastTime = LocalDate.parse(getPlayer().getOneInfo(16214, "date"), formatter);
            } catch (Exception e) {
                lastTime = null;
            }
            if (lastTime != null && !lastTime.isEqual(now)) {
                getPlayer().updateOneInfo(16214, "count", "0");
                getPlayer().updateOneInfo(16214, "date", now.format(formatter));
                getPlayer().updateOneInfo(16214, "coin", "0");
                getPlayer().updateOneInfo(16214, "todayrecord", "0");
            }
            getPlayer().updateOneInfo(16214, "date", now.format(formatter));
            getPlayer().updateOneInfo(16214, "count",
                    String.valueOf(getPlayer().getOneInfoQuestInteger(16214, "count") + 1)); // Count +1 immediately
                                                                                             // upon start
            getPlayer().updateInfoQuest(16215, "point=0;play=1;saved=0;life=100;chase=0;");
            getPlayer().send(CField.environmentChange("event/start", 19, 0));
            getPlayer().send(CField.environmentChange("Dojang/clear", 5, 100));
            objects.fields.fieldset.instance.SpiritSavior spiritSavior = (objects.fields.fieldset.instance.SpiritSavior) getPlayer()
                    .getMap().getFieldSetInstance();
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
