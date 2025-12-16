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
                int v = self.askMenu(
                                "#fs11#ต้องการท้าทาย #rArcanus#k ปีศาจแห่ง Sharenian หรือไม่?\r\n\r\n#L1# #bเข้าสู่ Sharenian Underground Culvert#k#l\r\n#L2# #bSharenian Underground Culvert คืออะไร?#k#l",
                                ScriptMessageFlag.NpcReplacedByNpc);
                switch (v) {
                        case 1: // เข้าสู่ Sharenian Underground Culvert

                                registerTransferField(941000000);
                                break;
                        case 2: // Sharenian Underground Culvert คืออะไร?
                                self.say("#fs11#สงสัยเกี่ยวกับ #r#eSharenian Underground Culvert#k#n งั้นหรือครับ?",
                                                ScriptMessageFlag.NpcReplacedByNpc);
                                self.say(
                                                "#fs11#นานมาแล้ว... มีอาณาจักรโบราณที่ชื่อว่า #bSharenian#k อยู่ครับ แม้ว่าอาณาจักรนั้นจะล่มสลายไปทั้งเมืองจากเหตุการณ์อันน่าสยดสยองก็ตาม",
                                                ScriptMessageFlag.NpcReplacedByNpc);
                                self.say(
                                                "#fs11#มีความพยายามที่จะสำรวจซากปรักหักพังของ Sharenian อยู่หลายครั้ง แต่ก็ไม่ง่ายเลย และในที่สุดเส้นทางที่มุ่งไปสู่ที่นั่นก็ถูกลืมเลือนไป\r\n\r\n#r#eจนกระทั่งเมื่อไม่นานมานี้นี่เอง...#n",
                                                ScriptMessageFlag.NpcReplacedByNpc);
                                self.say(
                                                "#fs11#ก่อนหน้านี้ ผมกำลังสำรวจโบราณสถาน Sharenian อยู่ และด้วยอุบัติเหตุบางอย่าง ทำให้ผมค้นพบ #bเส้นทางสู่ Sharenian Underground Culvert#k ในที่ที่ไม่มีใครเคยไปถึงครับ",
                                                ScriptMessageFlag.NpcReplacedByNpc);
                                self.say(
                                                "#fs11#ทางเข้ามากมายพันกันยุ่งเหยิงเหมือนเขาวงกต แต่ไม่ว่าจะไปทางไหน ก็สามารถไปถึงแท่นบูชาที่มีพลังงานชั่วร้ายหลับใหลอยู่ได้ครับ",
                                                ScriptMessageFlag.NpcReplacedByNpc);
                                self.say(
                                                "#fs11#ทางกองบัญชาการ Guild ตัดสินใจที่จะสำรวจท่อระบายน้ำใต้ดินนี้ครับ หากคุยกับผมที่หน้าทางเข้าเพื่อเปิดประตูสู่ Sharenian Underground Culvert คุณก็จะสามารถเข้าไปยังทางเดินที่มุ่งสู่แท่นบูชาแห่งความชั่วร้ายได้",
                                                ScriptMessageFlag.NpcReplacedByNpc);
                                self.say(
                                                "#fs11#การสำรวจสามารถทำได้ #bเพียงคนเดียว#k เท่านั้น หากออกจาก Guild หรือถูกไล่ออกกลางคัน จะไม่ได้รับคะแนนดันเจี้ยน นอกจากนี้ คะแนนสะสมของ Guild นั้นจะถูกหักออกด้วย โปรดระวังด้วยนะครับ",
                                                ScriptMessageFlag.NpcReplacedByNpc);
                                self.say(
                                                "#fs11#การสำรวจสามารถทำได้โดยไม่มีข้อจำกัด แต่เนื่องจากพลังงานชั่วร้ายในท่อระบายน้ำ เมื่อเข้าไปแล้ว #bBuff ทั้งหมดจะถูกลบล้าง#k โปรดตัดสินใจให้ดีนะครับ",
                                                ScriptMessageFlag.NpcReplacedByNpc);
                                self.say(
                                                "#fs11#ในทางเดินสู่แท่นบูชา คุณสามารถใช้ Buff สกิลร่วมกันและไอเท็มต่างๆ ได้ชั่วคราว เพื่อเตรียมรับมือกับบททดสอบที่จะมาถึง\r\nแต่ว่า ถ้าไม่เข้าไปยังแท่นบูชาภายในเวลาที่กำหนด คุณจะถูกส่งออกจากท่อระบายน้ำ โปรดระวังด้วยครับ",
                                                ScriptMessageFlag.NpcReplacedByNpc);
                                self.say(
                                                "#fs11#ณ ส่วนลึกที่สุดของท่อระบายน้ำ ที่แท่นบูชาแห่งพลังงานชั่วร้าย มีบางสิ่งที่ซ่อนตัวรอเวลาตื่นขึ้นมา นั่นก็คือ... \r\n\r\n                   #rArcanus ปีศาจแห่ง Sharenian#k\r\n\r\n.",
                                                ScriptMessageFlag.NpcReplacedByNpc);
                                self.say(
                                                "#fs11#มันคือตัวตนที่เป็นอมตะ... ยิ่งถูกโจมตี มันก็จะยิ่งแข็งแกร่งขึ้นและกำเนิดใหม่เรื่อยๆ แม้จะถูกโจมตีรุนแรงแค่ไหน มันก็จะไม่แสดงท่าทีว่าจะล้มลงเลย ดังนั้นควรระวังตัวไว้นะครับ\r\nและถ้าหากเสียชีวิตจากการโจมตีของ Arcanus คุณจะถูกย้ายออกมานอกแท่นบูชาทันที ระวังด้วยนะครับ",
                                                ScriptMessageFlag.NpcReplacedByNpc);
                                self.say(
                                                "#fs11#คุณจะได้รับคะแนนดันเจี้ยนตามความเสียหายที่ทำต่อสิ่งมีชีวิตในท่อระบายน้ำ ทุกวันจันทร์เวลา 00:00 น. ทางกองบัญชาการ Guild จะมอบรางวัลเป็น #bNoblesse SP#k ให้ตามเป้าหมายที่ทำได้ครับ แต่ Guild จะต้องมีคะแนนดันเจี้ยนรวมถึงเกณฑ์ที่กำหนดก่อนนะครับ ถึงจะได้รับรางวัล",
                                                ScriptMessageFlag.NpcReplacedByNpc);
                                self.say(
                                                "#fs11#นอกจากนี้ ยังสามารถรับ Noblesse SP เพิ่มเติมได้ตามอันดับคะแนนด้วย หลังจากออกจากท่อระบายน้ำ ต้องรอสักพักคะแนนถึงจะคำนวณเสร็จครับ\r\n#rต้องใช้เวลาสักพักในการรวมคะแนนของสมาชิก Guild แต่ละคนและแสดงผลในอันดับ หากคะแนนยังไม่ขึ้นทันทีก็ไม่ต้องตกใจนะครับ ลองตรวจสอบใหม่อีกครั้งในภายหลัง",
                                                ScriptMessageFlag.NpcReplacedByNpc);
                                self.say(
                                                "#fs11##b#eอันดับ 1 : 25 SP\r\nอันดับ 2 : 23 SP\r\nอันดับ 3 : 21 SP\r\nอันดับ 4 ~ 10 : 20 SP\r\nTop 10% : 19 SP\r\nTop 30% : 17 SP\r\nTop 60% : 15 SP\r\nTop 80% : 10 SP\r\n500 คะแนนขึ้นไป : 5 SP\r\n\r\n#r#e※ แต่ถ้าได้คะแนนต่ำกว่า 500 คะแนน แม้จะติดอันดับ ก็จะไม่ได้รับรางวัลนะครับ#k#n",
                                                ScriptMessageFlag.NpcReplacedByNpc);
                                self.say("#fs11#ผมจะเข้าไปในท่อระบายน้ำด้วย ไม่ต้องห่วงว่าจะหลงทางนะครับ รีบไปท้าทายกันเถอะ",
                                                ScriptMessageFlag.NpcReplacedByNpc);
                                break;
                }
        }

        public void npc_2012041() {
                SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd/HH/mm");
                Date lastTime = null;
                try {
                        lastTime = sdf.parse(getPlayer().getOneInfo(100811, "date"));
                } catch (Exception e) {
                        // e.printStackTrace();
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
                                if (1 == self.askYesNo(
                                                "#fs11#ถ้าเตรียมตัวพร้อมแล้ว ผมจะนำทางไปครับ\r\n\r\n#b#eต้องการไปยังแท่นบูชาตอนนี้เลยไหมครับ?#n#k",
                                                ScriptMessageFlag.NpcReplacedByNpc)) {
                                        registerTransferField(941000200);
                                } else {
                                        self.sayOk("#fs11#เข้าใจแล้วครับ ถ้าเตรียมตัวเสร็จแล้วค่อยลองใหม่นะครับ",
                                                        ScriptMessageFlag.NpcReplacedByNpc);
                                }
                                break;
                        }
                        case 941000001: {
                                if (getPlayer().getOneInfoQuestInteger(100811, "complete") > 0) {
                                        int point = getPlayer().getOneInfoQuestInteger(100811, "point");
                                        int weeklyRecord = getPlayer().getOneInfoQuestInteger(100811, "weeklyRecord");
                                        int v = -1;
                                        if (point > weeklyRecord) {
                                                v = self.askMenu(
                                                                "#fs11#ดูเหมือนพลังงานชั่วร้ายจะลดลงนิดหน่อยนะครับ\r\nผมจะนำทางกลับไปยังปากทางเข้าท่อระบายน้ำครับ\r\n\r\n#e- คะแนนที่ได้รับ: #n#b#e"
                                                                                + point
                                                                                + " คะแนน#n#k#e#b (สถิติใหม่ประจำสัปดาห์นี้!)#n#k\r\n#e- คะแนนสูงสุดประจำสัปดาห์นี้: #n#r#e"
                                                                                + point
                                                                                + " คะแนน#n#k\r\n#b#L1# กลับไปยังปากทางเข้าท่อระบายน้ำ#k#l\r\n#b#L2# จบการสนทนา#k#l",
                                                                ScriptMessageFlag.NpcReplacedByNpc);
                                        } else {
                                                v = self.askMenu(
                                                                "#fs11#ดูเหมือนพลังงานชั่วร้ายจะลดลงนิดหน่อยนะครับ\r\nผมจะนำทางกลับไปยังปากทางเข้าท่อระบายน้ำครับ\r\n\r\n#e- คะแนนที่ได้รับ: #n#b#e"
                                                                                + point
                                                                                + " คะแนน#n#k\r\n#e- คะแนนสูงสุดประจำสัปดาห์นี้: #n#r#e"
                                                                                + weeklyRecord
                                                                                + " คะแนน#n#k\r\n#b#L1# กลับไปยังปากทางเข้าท่อระบายน้ำ#k#l\r\n#b#L2# จบการสนทนา#k#l",
                                                                ScriptMessageFlag.NpcReplacedByNpc);
                                        }
                                        switch (v) {
                                                case 1:
                                                        if (point > weeklyRecord) {
                                                                getPlayer().updateOneInfo(100811, "weeklyRecord",
                                                                                String.valueOf(point));
                                                        }
                                                        registerTransferField(941000000);
                                                        break;
                                        }
                                } else {
                                        int v = self.askMenu(
                                                        "#fs11#\r\nพลังของ Arcanus ไม่ลดลงเลยสินะครับ ผมจะนำทางกลับไปยังปากทางเข้าท่อระบายน้ำครับ\r\n\r\n#b#L1# กลับไปยังปากทางเข้าท่อระบายน้ำ#k#l\r\n#b#L2# จบการสนทนา#k#l",
                                                        ScriptMessageFlag.NpcReplacedByNpc);
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
                                int v = self.askMenu(
                                                "#fs11#\r\nโบราณสถานกำลังแผ่พลังงานชั่วร้ายออกมาครับ ต้องการสำรวจท่อระบายน้ำใต้ดินตอนนี้เลยไหมครับ?\r\n\r\n#e- คะแนนสูงสุดประจำสัปดาห์นี้: #n#r#e"
                                                                + weeklyRecord
                                                                + " คะแนน#n#k\r\n#L1# #bเข้าสู่ Sharenian Underground Culvert#k#l\r\n#L2# #bSharenian Underground Culvert คืออะไร?#k#l",
                                                ScriptMessageFlag.NpcReplacedByNpc);
                                switch (v) {
                                        case 1: {
                                                if (1 == self.askAccept(
                                                                "เมื่อเข้าสู่ท่อระบายน้ำใต้ดิน #fs16##b#eBuff Effect ทั้งหมดจะถูกลบล้าง#k#fs12##n ครับ\r\n\r\nต้องการท้าทายตอนนี้เลยไหมครับ?")) {
                                                        CulvertEnter fieldSet = (CulvertEnter) fieldSet("CulvertEnter");
                                                        int enter = fieldSet.enter(target.getId(), 0);
                                                        if (enter == -1)
                                                                self.say(
                                                                                "#fs11#ไม่สามารถเข้าได้เนื่องจากข้อผิดพลาดที่ไม่ทราบสาเหตุ กรุณาลองใหม่อีกครั้งในภายหลังครับ");
                                                        else if (enter == 1)
                                                                self.sayOk(
                                                                                "#fs11#<Sharenian Underground Culvert> สามารถเข้าได้ #bเพียงคนเดียว#k เท่านั้นครับ\r\nกรุณาออกจากปาร์ตี้แล้วลองใหม่อีกครั้งนะครับ");
                                                        else if (enter == 2)
                                                                self.say("#fs11#ต้องมีเลเวล " + fieldSet.minLv
                                                                                + " ขึ้นไปจึงจะเข้าได้ครับ");
                                                        else if (enter == 3)
                                                                self.say(
                                                                                "#fs11#ขณะนี้ดันเจี้ยนเต็มทุกห้อง ไม่สามารถเข้าได้ กรุณาลองย้ายแชนแนลดูนะครับ");
                                                        else if (enter == -2)
                                                                self.say(
                                                                                "#fs11#ช่วงเวลาวันอาทิตย์ 23:00 น. ~ วันจันทร์ 01:00 น. เป็นช่วงเวลาคำนวณคะแนน Noblesse ไม่สามารถเข้าใช้งานได้ครับ");
                                                } else {
                                                        self.sayOk("#fs11#เข้าใจแล้วครับ ถ้าเตรียมตัวเสร็จแล้วค่อยมาลองใหม่นะครับ",
                                                                        ScriptMessageFlag.NpcReplacedByNpc);
                                                }
                                                break;
                                        }
                                        case 2: // Sharenian Underground Culvert คืออะไร?
                                                self.say("#fs11#สงสัยเกี่ยวกับ #r#eSharenian Underground Culvert#k#n งั้นหรือครับ?",
                                                                ScriptMessageFlag.NpcReplacedByNpc);
                                                self.say(
                                                                "#fs11#นานมาแล้ว... มีอาณาจักรโบราณที่ชื่อว่า #bSharenian#k อยู่ครับ แม้ว่าอาณาจักรนั้นจะล่มสลายไปทั้งเมืองจากเหตุการณ์อันน่าสยดสยองก็ตาม",
                                                                ScriptMessageFlag.NpcReplacedByNpc);
                                                self.say(
                                                                "#fs11#มีความพยายามที่จะสำรวจซากปรักหักพังของ Sharenian อยู่หลายครั้ง แต่ก็ไม่ง่ายเลย และในที่สุดเส้นทางที่มุ่งไปสู่ที่นั่นก็ถูกลืมเลือนไป\r\n\r\n#r#eจนกระทั่งเมื่อไม่นานมานี้นี่เอง...#n",
                                                                ScriptMessageFlag.NpcReplacedByNpc);
                                                self.say(
                                                                "#fs11#ก่อนหน้านี้ ผมกำลังสำรวจโบราณสถาน Sharenian อยู่ และด้วยอุบัติเหตุบางอย่าง ทำให้ผมค้นพบ #bเส้นทางสู่ Sharenian Underground Culvert#k ในที่ที่ไม่มีใครเคยไปถึงครับ",
                                                                ScriptMessageFlag.NpcReplacedByNpc);
                                                self.say(
                                                                "#fs11#ทางเข้ามากมายพันกันยุ่งเหยิงเหมือนเขาวงกต แต่ไม่ว่าจะไปทางไหน ก็สามารถไปถึงแท่นบูชาที่มีพลังงานชั่วร้ายหลับใหลอยู่ได้ครับ",
                                                                ScriptMessageFlag.NpcReplacedByNpc);
                                                self.say(
                                                                "#fs11#ทางกองบัญชาการ Guild ตัดสินใจที่จะสำรวจท่อระบายน้ำใต้ดินนี้ครับ หากคุยกับผมที่หน้าทางเข้าเพื่อเปิดประตูสู่ Sharenian Underground Culvert คุณก็จะสามารถเข้าไปยังทางเดินที่มุ่งสู่แท่นบูชาแห่งความชั่วร้ายได้",
                                                                ScriptMessageFlag.NpcReplacedByNpc);
                                                self.say(
                                                                "#fs11#การสำรวจสามารถทำได้ #bเพียงคนเดียว#k เท่านั้น หากออกจาก Guild หรือถูกไล่ออกกลางคัน จะไม่ได้รับคะแนนดันเจี้ยน นอกจากนี้ คะแนนสะสมของ Guild นั้นจะถูกหักออกด้วย โปรดระวังด้วยนะครับ",
                                                                ScriptMessageFlag.NpcReplacedByNpc);
                                                self.say(
                                                                "#fs11#การสำรวจสามารถทำได้โดยไม่มีข้อจำกัด แต่เนื่องจากพลังงานชั่วร้ายในท่อระบายน้ำ เมื่อเข้าไปแล้ว #bBuff ทั้งหมดจะถูกลบล้าง#k โปรดตัดสินใจให้ดีนะครับ",
                                                                ScriptMessageFlag.NpcReplacedByNpc);
                                                self.say(
                                                                "#fs11#ในทางเดินสู่แท่นบูชา คุณสามารถใช้ Buff สกิลร่วมกันและไอเท็มต่างๆ ได้ชั่วคราว เพื่อเตรียมรับมือกับบททดสอบที่จะมาถึง\r\nแต่ว่า ถ้าไม่เข้าไปยังแท่นบูชาภายในเวลาที่กำหนด คุณจะถูกส่งออกจากท่อระบายน้ำ โปรดระวังด้วยครับ",
                                                                ScriptMessageFlag.NpcReplacedByNpc);
                                                self.say(
                                                                "#fs11#ณ ส่วนลึกที่สุดของท่อระบายน้ำ ที่แท่นบูชาแห่งพลังงานชั่วร้าย มีบางสิ่งที่ซ่อนตัวรอเวลาตื่นขึ้นมา นั่นก็คือ... \r\n\r\n                   #rArcanus ปีศาจแห่ง Sharenian#k\r\n\r\n.",
                                                                ScriptMessageFlag.NpcReplacedByNpc);
                                                self.say(
                                                                "#fs11#มันคือตัวตนที่เป็นอมตะ... ยิ่งถูกโจมตี มันก็จะยิ่งแข็งแกร่งขึ้นและกำเนิดใหม่เรื่อยๆ แม้จะถูกโจมตีรุนแรงแค่ไหน มันก็จะไม่แสดงท่าทีว่าจะล้มลงเลย ดังนั้นควรระวังตัวไว้นะครับ\r\nและถ้าหากเสียชีวิตจากการโจมตีของ Arcanus คุณจะถูกย้ายออกมานอกแท่นบูชาทันที ระวังด้วยนะครับ",
                                                                ScriptMessageFlag.NpcReplacedByNpc);
                                                self.say(
                                                                "#fs11#คุณจะได้รับคะแนนดันเจี้ยนตามความเสียหายที่ทำต่อสิ่งมีชีวิตในท่อระบายน้ำ ทุกวันจันทร์เวลา 00:00 น. ทางกองบัญชาการ Guild จะมอบรางวัลเป็น #bNoblesse SP#k ให้ตามเป้าหมายที่ทำได้ครับ แต่ Guild จะต้องมีคะแนนดันเจี้ยนรวมถึงเกณฑ์ที่กำหนดก่อนนะครับ ถึงจะได้รับรางวัล",
                                                                ScriptMessageFlag.NpcReplacedByNpc);
                                                self.say(
                                                                "#fs11#นอกจากนี้ ยังสามารถรับ Noblesse SP เพิ่มเติมได้ตามอันดับคะแนนด้วย หลังจากออกจากท่อระบายน้ำ ต้องรอสักพักคะแนนถึงจะคำนวณเสร็จครับ\r\n#rต้องใช้เวลาสักพักในการรวมคะแนนของสมาชิก Guild แต่ละคนและแสดงผลในอันดับ หากคะแนนยังไม่ขึ้นทันทีก็ไม่ต้องตกใจนะครับ ลองตรวจสอบใหม่อีกครั้งในภายหลัง",
                                                                ScriptMessageFlag.NpcReplacedByNpc);
                                                self.say(
                                                                "#fs11##b#eอันดับ 1 : 25 SP\r\nอันดับ 2 : 23 SP\r\nอันดับ 3 : 21 SP\r\nอันดับ 4 ~ 10 : 20 SP\r\nTop 10% : 19 SP\r\nTop 30% : 17 SP\r\nTop 60% : 15 SP\r\nTop 80% : 10 SP\r\n500 คะแนนขึ้นไป : 5 SP\r\n\r\n#r#e※ แต่ถ้าได้คะแนนต่ำกว่า 500 คะแนน แม้จะติดอันดับ ก็จะไม่ได้รับรางวัลนะครับ#k#n",
                                                                ScriptMessageFlag.NpcReplacedByNpc);
                                                self.say("#fs11#ผมจะเข้าไปในท่อระบายน้ำด้วย ไม่ต้องห่วงว่าจะหลงทางนะครับ รีบไปท้าทายกันเถอะ",
                                                                ScriptMessageFlag.NpcReplacedByNpc);
                                                break;
                                }
                        }
                                break;
                }
        }

        @Script
        public void gb_reset() {
                int duration = 0;
                if (getPlayer().getCooldownLimit(80002282) != 0L) { // ป้องกันการใช้ประโยชน์จากการปลดล็อก Rune of Might
                                                                    // ที่ถูกผนึก
                        duration = (int) getPlayer().getRemainCooltime(80002282);
                }
                getPlayer().cancelAllBuffs();
                getPlayer().send(CField.addPopupSay(2012041, 1300,
                                "เนื่องจากพลังงานชั่วร้ายของแท่นบูชา\r\n#rBuff ทั้งหมดจึงถูกลบล้าง#kครับ", ""));
                getPlayer().send(CField.addPopupSay(2012041, 1300,
                                "กรุณาเตรียมตัวให้พร้อมสำหรับการตัดสินกับ #rปีศาจแห่ง Sharenian#k ครับ!", ""));
                if (duration != 0) {
                        getPlayer().temporaryStatSet(80002282, duration, SecondaryStatFlag.RuneBlocked, 1);
                }
        }

        public void gb_out() {
                initNPC(MapleLifeFactory.getNPC(2012041));
                if (1 == self.askYesNo("#fs11#ต้องการล้มเลิกการท้าทายและกลับไปยังปากทางเข้าท่อระบายน้ำไหมครับ?",
                                ScriptMessageFlag.NpcReplacedByNpc)) {
                        registerTransferField(941000001);
                }
        }

}
