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
         * 도전เป็นไปได้한 횟수จำกัด은 없으ฉัน EXP 하루에 2번ถึง 얻을 수 있음
         * เข้า시 DispelItemOptionByField Buff 걸림
         */
        FieldSet fieldSet = fieldSet("TangyoonKitchenEnter");
        if (fieldSet == null) {
            self.sayOk("ขณะนี้ไม่สามารถเข้าใช้งานร้านอาหารทังยุนได้ครับ~! กรุณากลับมาใหม่ภายหลังนะครับ!",
                    ScriptMessageFlag.NpcReplacedByNpc);
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
        int v = self.askMenu(
                "#b#eMaple Variety#n#k เอพิโซด 1~! #r#eร้าน.อาหาร.ทัง.ยุน!#l#k\r\n#L2# #b#e<ร้านอาหารทังยุน>#n ปาร์ตี้เพลย์ (ท้าทาย 1~3 คน)#l\r\n#L5# #b#e<Puzzle Master>#n ปาร์ตี้เพลย์ (ท้าทาย 1~3 คน)#l#k\r\n\r\n#L3# #b#e<ร้านอาหารทังยุน>#n ฟังคำอธิบาย#l\r\n#L6# #b#e<Puzzle Master>#n ฟังคำอธิบาย#l#k\r\n#L4# ตรวจสอบจำนวนครั้งที่รับรางวัล EXP ได้วันนี้ <ร้านอาหารทังยุน>#l\r\n#L7# ตรวจสอบจำนวนครั้งที่รับรางวัล EXP ได้วันนี้ <Puzzle>\r\n\r\n#L100# ไม่มีข้อสงสัยแล้ว#l",
                ScriptMessageFlag.NpcReplacedByNpc);
        switch (v) {
            case 2: { // ท้าทายแบบปาร์ตี้ (ร้านอาหารทังยุน)
                if (1 == self.askYesNo(
                        "\r\nต้องการเข้าร่วม #b#e<ร้านอาหารทังยุน>#n#k ตอนนี้เลยไหมครับ~?\r\n\r\n(ระหว่างเล่นเกม ความละเอียดจะเปลี่ยนเป็น 1366x768)",
                        ScriptMessageFlag.NpcReplacedByNpc)) {
                    if (1 == self.askYesNo(
                            "\r\nหากเข้าร่วม #b#e<ร้านอาหารทังยุน>#n#k \r\n#fs16##r#eBuff Effect ทั้งหมดที่ใช้งานอยู่จะถูกลบล้าง#n#k#fs12# นะครับ\r\n\r\nต้องการเข้าร่วมจริงๆ หรือไม่ครับ?",
                            ScriptMessageFlag.NpcReplacedByNpc)) {
                        int enter = fieldSet.enter(target.getId(), 0);
                        if (enter == -1)
                            self.say(
                                    "ไม่สามารถเข้าได้เนื่องจากข้อผิดพลาดที่ไม่ทราบสาเหตุ กรุณาลองใหม่อีกครั้งในภายหลังครับ",
                                    ScriptMessageFlag.NpcReplacedByNpc);
                        else if (enter == 1)
                            self.say("ต้องสร้างปาร์ตี้ 1~3 คน จึงจะสามารถทำ #b#eปาร์ตี้เพลย์#n#k ได้ครับ",
                                    ScriptMessageFlag.NpcReplacedByNpc);
                        else if (enter == 2)
                            self.say("สามารถเข้าได้ผ่านหัวหน้าปาร์ตี้เท่านั้นครับ! กรุณาเข้าผ่านหัวหน้าปาร์ตี้นะครับ~!",
                                    ScriptMessageFlag.NpcReplacedByNpc);
                        else if (enter == 3)
                            self.say("ปาร์ตี้เควสต์เริ่มได้เมื่อมีสมาชิกอย่างน้อย " + fieldSet.minMember + " คนครับ",
                                    ScriptMessageFlag.NpcReplacedByNpc);
                        else if (enter == 4)
                            self.say("สมาชิกปาร์ตี้ต้องมีเลเวล " + fieldSet.minLv + " ขึ้นไปครับ",
                                    ScriptMessageFlag.NpcReplacedByNpc);
                        else if (enter == 5)
                            self.say("สมาชิกปาร์ตี้ต้องอยู่รวมกันครบทุกคนจึงจะเริ่มได้ครับ",
                                    ScriptMessageFlag.NpcReplacedByNpc);
                        else if (enter == 6)
                            self.say("มีปาร์ตี้อื่นเข้าไปท้าทายในเควสต์อยู่แล้วครับ",
                                    ScriptMessageFlag.NpcReplacedByNpc);
                    } else {
                        self.say("\r\nถ้าอย่างนั้น ไว้ต้องการเข้าร่วมเมื่อไหร่ก็กลับมาใหม่นะครับ~",
                                ScriptMessageFlag.NpcReplacedByNpc);
                    }
                } else {
                    self.say("\r\nถ้าอย่างนั้น ไว้ต้องการเข้าร่วมเมื่อไหร่ก็กลับมาใหม่นะครับ~",
                            ScriptMessageFlag.NpcReplacedByNpc);
                }
                break;
            }
            case 5: { // ท้าทายแบบปาร์ตี้ (Puzzle Master)
                if (1 == self.askYesNo("\r\nต้องการเข้าร่วม #b#e<Puzzle Master>#n#k ตอนนี้เลยไหมครับ~?#k",
                        ScriptMessageFlag.NpcReplacedByNpc)) {
                    if (1 == self.askYesNo(
                            "\r\nหากเข้าร่วม #b#e<Puzzle Master>#n#k \r\n#fs16##r#eBuff Effect ทั้งหมดที่ใช้งานอยู่จะถูกลบล้าง#n#k#fs12# นะครับ\r\n\r\nต้องการเข้าร่วมจริงๆ หรือไม่ครับ?",
                            ScriptMessageFlag.NpcReplacedByNpc)) {
                        FieldSet puzzleMaster = fieldSet("PuzzleMasterEnter");
                        if (puzzleMaster == null) {
                            self.sayOk(
                                    "ขณะนี้ไม่สามารถเข้าใช้งาน Puzzle Master ได้ครับ~! กรุณากลับมาใหม่ภายหลังนะครับ!",
                                    ScriptMessageFlag.NpcReplacedByNpc);
                            return;
                        }
                        int enter = puzzleMaster.enter(target.getId(), 0);
                        if (enter == -1)
                            self.say(
                                    "ไม่สามารถเข้าได้เนื่องจากข้อผิดพลาดที่ไม่ทราบสาเหตุ กรุณาลองใหม่อีกครั้งในภายหลังครับ",
                                    ScriptMessageFlag.NpcReplacedByNpc);
                        else if (enter == 1)
                            self.say("1~3인 ปาร์ตี้ 맺어야만 #b#eปาร์ตี้ 플레이#n#k 할 수 있답니다.",
                                    ScriptMessageFlag.NpcReplacedByNpc);
                        else if (enter == 2)
                            self.say("สามารถเข้าได้ผ่านหัวหน้าปาร์ตี้เท่านั้นครับ! กรุณาเข้าผ่านหัวหน้าปาร์ตี้นะครับ~!",
                                    ScriptMessageFlag.NpcReplacedByNpc);
                        else if (enter == 3)
                            self.say("ปาร์ตี้เควสต์เริ่มได้เมื่อมีสมาชิกอย่างน้อย " + fieldSet.minMember + " คนครับ",
                                    ScriptMessageFlag.NpcReplacedByNpc);
                        else if (enter == 4)
                            self.say("สมาชิกปาร์ตี้ต้องมีเลเวล " + fieldSet.minLv + " ขึ้นไปครับ",
                                    ScriptMessageFlag.NpcReplacedByNpc);
                        else if (enter == 5)
                            self.say("สมาชิกปาร์ตี้ต้องอยู่รวมกันครบทุกคนจึงจะเริ่มได้ครับ",
                                    ScriptMessageFlag.NpcReplacedByNpc);
                        else if (enter == 6)
                            self.say("มีปาร์ตี้อื่นเข้าไปท้าทายในเควสต์อยู่แล้วครับ",
                                    ScriptMessageFlag.NpcReplacedByNpc);
                    } else {
                        self.say("\r\nถ้าอย่างนั้น ไว้ต้องการเข้าร่วมเมื่อไหร่ก็กลับมาใหม่นะครับ~",
                                ScriptMessageFlag.NpcReplacedByNpc);
                    }
                } else {
                    self.say("\r\nถ้าอย่างนั้น ไว้ต้องการเข้าร่วมเมื่อไหร่ก็กลับมาใหม่นะครับ~",
                            ScriptMessageFlag.NpcReplacedByNpc);
                }
                break;
            }
            case 3: { // ฟังคำอธิบาย
                self.say("#e[ระยะเวลากิจกรรม]\r\n\r\n5 กรกฎาคม 2021 (จันทร์) หลังปิดปรับปรุง ~ \r\nยังไม่กำหนด",
                        ScriptMessageFlag.NpcReplacedByNpc);
                self.say(
                        "\r\n#b#e<ร้านอาหารทังยุน>#n#k เป็นคอนเทนต์ที่ให้สมาชิกอย่างน้อย 1 คน ~ สูงสุด 3 คน ช่วยกันทำอาหารและส่งให้เสร็จสมบูรณ์\r\nเป้าหมายคือการทำคะแนนให้ได้ #b#e50,000#n#k คะแนน ภายในเวลาจำกัด #e30 นาที#n ครับ!",
                        ScriptMessageFlag.NpcReplacedByNpc);
                self.say(
                        "\r\nตรวจสอบเมนูที่เข้ามาได้ที่ #b#e<กระดานเมนู>#n#k ทางด้านซ้าย\r\nแล้วลองทำอาหารตามสูตรและนำไปส่งดูนะครับ~!",
                        ScriptMessageFlag.NpcReplacedByNpc);
                self.say(
                        "\r\n#e[การรวบรวมวัตถุดิบ]#n\r\n\r\nสามารถรับวัตถุดิบที่ต้องการได้โดยการกดค้างที่ #r#eปุ่ม Space#k#n\r\nที่หน้าวัตถุดิบ #e5 ชนิด#n ซึ่งตั้งอยู่ทาง #b#eด้านซ้ายของห้องครัว#n#k ครับ\r\n\r\n",
                        ScriptMessageFlag.NpcReplacedByNpc);
                self.say(
                        "\r\n#e[การวางวัตถุดิบ]#n\r\n\r\nสามารถวางวัตถุดิบที่ถืออยู่ได้โดยการกดค้างที่ #r#eปุ่ม Space#k#n\r\nที่หน้า #eโต๊ะทำครัว#n ซึ่งตั้งอยู่ทาง #b#eตรงกลางของห้องครัว#n#k ครับ\r\n\r\nอย่าลืมนะครับว่าต้องทำอาหารให้ตรงกับ #eโต๊ะทำครัวที่มีหมายเลขเมนู#n\r\nที่ตรงกันเท่านั้น!",
                        ScriptMessageFlag.NpcReplacedByNpc);
                self.say(
                        "\r\n#e[การถือเครื่องมือแปรรูป]#n\r\n\r\nสามารถรับเครื่องมือได้โดยการกดค้างที่ #r#eปุ่ม Space#k#n\r\nที่หน้าเครื่องมือแปรรูป #e3 ชนิด#n ซึ่งตั้งอยู่ทาง #b#eด้านขวาของห้องครัว#n#k ครับ\r\n\r\nจำไว้ว่าต้องอยู่ในสถานะที่ถือ #b#eเครื่องมือแปรรูป#n#k อยู่เท่านั้น\r\nถึงจะสามารถแปรรูปวัตถุดิบได้นะครับ!",
                        ScriptMessageFlag.NpcReplacedByNpc);
                self.say(
                        "\r\n#e[การแปรรูปวัตถุดิบ]#n\r\n\r\nสามารถแปรรูปวัตถุดิบที่วางอยู่บนโต๊ะทำครัวได้โดยการกดค้างที่ #r#eปุ่ม Space#n#k\r\nที่หน้า #eโต๊ะทำครัว#n ซึ่งตั้งอยู่ทาง #b#eตรงกลางของห้องครัว#n#k ครับ\r\n\r\nอย่าลืมนะครับว่าต้องทำอาหารให้ตรงกับ #eโต๊ะทำครัวที่มีหมายเลขเมนู#n\r\nที่ตรงกันเท่านั้น!",
                        ScriptMessageFlag.NpcReplacedByNpc);
                self.say(
                        "\r\n#e[การส่งอาหาร]#n\r\n\r\nในสถานะที่สามารถถืออาหารได้ จาก #b#eโต๊ะทำครัวที่ทำอาหารเสร็จแล้ว#n#k \r\nสามารถดำเนินการส่งอาหารได้โดยการกดค้างที่ #r#eปุ่ม Space#k#n ที่หน้า #eโต๊ะทำครัว#n นั้นครับ\r\n\r\nหลบหลีกการขัดขวางของเหล่า #eมอนสเตอร์#n ในพื้นที่ส่งอาหารทางด้านขวา\r\nและกดค้างที่ #r#eปุ่ม Space#k#n ต่อหน้าลูกค้าที่สั่งอาหาร ก็จะส่งอาหารเสร็จสมบูรณ์!",
                        ScriptMessageFlag.NpcReplacedByNpc);
                self.say(
                        "\r\nถ้าเป็น #b#eCreator ตัวจริง#n#k ก็ต้องลงมือทำจริงสินะครับ!\r\n\r\nลองไปสัมผัสประสบการณ์ด้วยตัวเองดูไหมครับ?",
                        ScriptMessageFlag.NpcReplacedByNpc);
                break;
            }
            case 6: { // ฟังคำอธิบาย Puzzle Master
                self.say("#e[ระยะเวลากิจกรรม]\r\n\r\nกรกฎาคม 2021 ~ \r\nยังไม่กำหนด",
                        ScriptMessageFlag.NpcReplacedByNpc);
                self.say(
                        "\r\n#e#b<Puzzle Master>#k#n เป็นคอนเทนต์ที่ให้สมาชิกอย่างน้อย 1 คน ~ สูงสุด 3 คน\r\nช่วยกันต่อ #e#bจิ๊กซอว์ทั้งหมด 3 ภาพ#n#k ให้เสร็จสมบูรณ์ภายในเวลาจำกัด #b#e30 นาที#n#k ครับ!",
                        ScriptMessageFlag.NpcReplacedByNpc);
                self.say(
                        "\r\nนำ #b#eชิ้นส่วนจิ๊กซอว์#n#k ที่ปรากฏขึ้นทั่วแผนที่\r\nมาวางให้ตรงตำแหน่งใน #b#eกรอบรูปที่อยู่กลางหน้าจอ!#n#k",
                        ScriptMessageFlag.NpcReplacedByNpc);
                self.say(
                        "\r\nอ๊ะ! คิดว่ายากเกินไปงั้นเหรอครับ? ไม่ต้องกังวลไปนะครับ!\r\n#b#eถ้าช่วยกันกับเหล่า Creator ที่มาร่วมด้วยช่วยกันล่ะก็ ทำได้แน่นอนครับ!#n#k",
                        ScriptMessageFlag.NpcReplacedByNpc);
                break;
            }
            case 4: { // จำนวนครั้งรับ EXP วันนี้
                int today = getPlayer().getOneInfoQuestInteger(501555, "today");
                if (2 - today > 0) {
                    self.say("จำนวนครั้งที่รับรางวัล EXP ได้วันนี้คือ #b#e" + (2 - today) + " ครั้ง#n#k ครับ",
                            ScriptMessageFlag.NpcReplacedByNpc);
                } else {
                    self.say("วันนี้ไม่สามารถรับรางวัล EXP ได้อีกแล้วครับ", ScriptMessageFlag.NpcReplacedByNpc);
                }
                break;
            }
            case 7: { // จำนวนครั้งรับ EXP วันนี้ (Puzzle Master)
                int today = getPlayer().getOneInfoQuestInteger(501600, "today");
                if (2 - today > 0) {
                    self.say("จำนวนครั้งที่รับรางวัล EXP ได้วันนี้คือ #b#e" + (2 - today) + " ครั้ง#n#k ครับ",
                            ScriptMessageFlag.NpcReplacedByNpc);
                } else {
                    self.say("วันนี้ไม่สามารถรับรางวัล EXP ได้อีกแล้วครับ", ScriptMessageFlag.NpcReplacedByNpc);
                }
                break;
            }
        }
    }

    public void TyoonKitchen_npc1() {
        if (getPlayer().getMap().getId() == 993194401) {
            if (getPlayer().getOneInfoQuestInteger(501555, "today") >= 2) {
                self.say(
                        "วันนี้ไม่สามารถรับรางวัล EXP ได้อีกแล้วครับ จึงไม่สามารถมอบ EXP ให้ได้ จะส่งกลับไปยังที่เดิมนะครับ!",
                        ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                getPlayer().temporaryStatReset(SecondaryStatFlag.DispelItemOptionByField);
                target.registerTransferField(ServerConstants.TownMap);
            } else if (getPlayer().getOneInfoQuestInteger(501555, "complete") >= 1) {
                Calendar CAL = new GregorianCalendar(Locale.KOREA);
                int day = CAL.getTime().getDay();
                boolean doubleEXP = false;
                if (day == 6 || day == 0) { // สุดสัปดาห์
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
                        self.say(
                                "\r\n#e#bEXP#k #r" + expString
                                        + "#k#n และ #r100 Ganglim Point#k#n ครับ!\r\n(มอบรางวัลเมื่อออกจากแผนที่)",
                                ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                    } else {
                        self.say("\r\n#e#bEXP#k #r" + expString
                                + " * 2 (กิจกรรม EXP 2 เท่า ร้านอาหารทังยุนสุดสัปดาห์!)#k#n และ #r100 Ganglim Point#k#n ครับ!\r\n(มอบรางวัลเมื่อออกจากแผนที่)",
                                ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                    }
                    getPlayer().gainHongboPoint(100);
                } else {
                    if (!doubleEXP) {
                        self.say(
                                "\r\nมอบ #e#bEXP#k #r" + expString
                                        + "#k#n ให้เรียบร้อยครับ!\r\n(มอบรางวัลเมื่อออกจากแผนที่)",
                                ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                    } else {
                        self.say(
                                "\r\nมอบ #e#bEXP#k #r" + expString
                                        + " * 2 (กิจกรรม EXP 2 เท่า ร้านอาหารทังยุนสุดสัปดาห์!)#k#n ให้เรียบร้อยครับ!\r\n(มอบรางวัลเมื่อออกจากแผนที่)",
                                ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                    }
                }
                getPlayer().temporaryStatReset(SecondaryStatFlag.DispelItemOptionByField);
                target.registerTransferField(ServerConstants.TownMap);
                getPlayer().updateOneInfo(501555, "start", "0");
                getPlayer().updateOneInfo(501555, "today",
                        String.valueOf(getPlayer().getOneInfoQuestInteger(501555, "today") + 1));
                getPlayer().updateOneInfo(501555, "complete", "0");
                if (!doubleEXP) {
                    getPlayer().gainExpLong(exp);
                } else {
                    getPlayer().gainExpLong(exp * 2);
                }
            } else {
                self.say(
                        "\r\n\r\nถ้าทำคะแนนไม่ถึง #e#r50,000 คะแนน#n#k\r\nจะไม่สามารถมอบรางวัลให้ได้นะครับ\r\n\r\nครั้งหน้าพยายามเข้านะครับ!",
                        ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                getPlayer().temporaryStatReset(SecondaryStatFlag.DispelItemOptionByField);
                target.registerTransferField(ServerConstants.TownMap);
            }
        } else {
            int v = -2;
            boolean exit = false;
            while (v != -1 && v != 0 && !exit && !getSc().isStop()) {
                v = self.askMenu(
                        "มีอะไรสงสัยไหมครับ?\r\n\r\n#L1# สอบถามเกี่ยวกับ #b#e<ร้านอาหารทังยุน>#b#e#l\r\n#L2# สอบถามเกี่ยวกับ #b#e<วิธีเล่นร้านอาหารทังยุน>#n#k#l\r\n#L3# สอบถามเกี่ยวกับ #b#e<รางวัลเกม>#n#k#l\r\n#L4# อยากกลับไปยังที่เดิม#l\r\n\r\n#L0# ไม่มีข้อสงสัยแล้ว#l",
                        ScriptMessageFlag.NpcReplacedByNpc);
                switch (v) {
                    case 1: { // เกี่ยวกับร้านอาหารทังยุน
                        self.say("\r\n#b#eMaple Variety#n#k เอพิโซด 1~! #r#eร้าน.อาหาร.ทัง.ยุน!#n#k",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say(
                                "\r\nโอกาสที่จะได้สัมผัสประสบการณ์การบริหารร้านอาหารของสุดยอดเชฟในตำนาน #b#e'Tangyoon'#n#k!",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("\r\nถ้าเป็น #b#eCreator ตัวจริง#n#k ก็ห้ามพลาดโอกาสนี้เชียวนะครับ?!",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        break;
                    }
                    case 2: { // วิธีเล่นร้านอาหารทังยุน
                        self.say(
                                "\r\n#b#e<ร้านอาหารทังยุน>#n#k เป็นคอนเทนต์ที่ให้สมาชิกอย่างน้อย 1 คน ~ สูงสุด 3 คน ช่วยกันทำอาหารและส่งให้เสร็จสมบูรณ์\r\nเป้าหมายคือการทำคะแนนให้ได้ #b#e50,000#n#k คะแนน ภายในเวลาจำกัด #e30 นาที#n ครับ!",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say(
                                "\r\nตรวจสอบเมนูที่เข้ามาได้ที่ #b#e<กระดานเมนู>#n#k ทางด้านซ้าย\r\nแล้วลองทำอาหารตามสูตรและนำไปส่งดูนะครับ~!",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say(
                                "\r\n#e[การรวบรวมวัตถุดิบ]#n\r\n\r\nสามารถรับวัตถุดิบที่ต้องการได้โดยการกดค้างที่ #r#eปุ่ม Space#k#n\r\nที่หน้าวัตถุดิบ #e5 ชนิด#n ซึ่งตั้งอยู่ทาง #b#eด้านซ้ายของห้องครัว#n#k ครับ\r\n\r\n",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say(
                                "\r\n#e[การวางวัตถุดิบ]#n\r\n\r\nสามารถวางวัตถุดิบที่ถืออยู่ได้โดยการกดค้างที่ #r#eปุ่ม Space#k#n\r\nที่หน้า #eโต๊ะทำครัว#n ซึ่งตั้งอยู่ทาง #b#eตรงกลางของห้องครัว#n#k ครับ\r\n\r\nอย่าลืมนะครับว่าต้องทำอาหารให้ตรงกับ #eโต๊ะทำครัวที่มีหมายเลขเมนู#n\r\nที่ตรงกันเท่านั้น!",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say(
                                "\r\n#e[การถือเครื่องมือแปรรูป]#n\r\n\r\nสามารถรับเครื่องมือได้โดยการกดค้างที่ #r#eปุ่ม Space#k#n\r\nที่หน้าเครื่องมือแปรรูป #e3 ชนิด#n ซึ่งตั้งอยู่ทาง #b#eด้านขวาของห้องครัว#n#k ครับ\r\n\r\nจำไว้ว่าต้องอยู่ในสถานะที่ถือ #b#eเครื่องมือแปรรูป#n#k อยู่เท่านั้น\r\nถึงจะสามารถแปรรูปวัตถุดิบได้นะครับ!",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say(
                                "\r\n#e[การแปรรูปวัตถุดิบ]#n\r\n\r\nสามารถแปรรูปวัตถุดิบที่วางอยู่บนโต๊ะทำครัวได้โดยการกดค้างที่ #r#eปุ่ม Space#n#k\r\nที่หน้า #eโต๊ะทำครัว#n ซึ่งตั้งอยู่ทาง #b#eตรงกลางของห้องครัว#n#k ครับ\r\n\r\nอย่าลืมนะครับว่าต้องทำอาหารให้ตรงกับ #eโต๊ะทำครัวที่มีหมายเลขเมนู#n\r\nที่ตรงกันเท่านั้น!",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say(
                                "\r\n#e[การส่งอาหาร]#n\r\n\r\nในสถานะที่สามารถถืออาหารได้ จาก #b#eโต๊ะทำครัวที่ทำอาหารเสร็จแล้ว#n#k \r\nสามารถดำเนินการส่งอาหารได้โดยการกดค้างที่ #r#eปุ่ม Space#k#n ที่หน้า #eโต๊ะทำครัว#n นั้นครับ\r\n\r\nหลบหลีกการขัดขวางของเหล่า #eมอนสเตอร์#n ในพื้นที่ส่งอาหารทางด้านขวา\r\nและกดค้างที่ #r#eปุ่ม Space#k#n ต่อหน้าลูกค้าที่สั่งอาหาร ก็จะส่งอาหารเสร็จสมบูรณ์!",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        break;
                    }
                    case 3: { // รางวัลเกม
                        self.say(
                                "\r\nหากทำได้ #b#e50,000#n#k คะแนน จะได้รับ #eEXP ตามที่กำหนด#n\r\nเป็นรางวัลครับ!\r\n\r\n#r#eโอกาสดีๆ แบบนี้#n#k พลาดไม่ได้แล้วใช่ไหมล่ะครับ?",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        break;
                    }
                    case 4: {
                        getPlayer().temporaryStatReset(SecondaryStatFlag.DispelItemOptionByField);
                        if (1 == self.askYesNo("\r\nต้องการกลับไปที่ #e#b<จัตุรัส>#n#k หรือไม่ครับ?",
                                ScriptMessageFlag.NpcReplacedByNpc)) {
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
        if (getPlayer().getMap().getId() == 993194801) { // ออกจากแผนที่
            if (getPlayer().getOneInfoQuestInteger(501600, "today") >= 2) {
                self.say(
                        "วันนี้ไม่สามารถรับรางวัล EXP ได้อีกแล้วครับ จึงไม่สามารถมอบ EXP ให้ได้ จะส่งกลับไปยังที่เดิมนะครับ!",
                        ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                if (DBConfig.isHosting) {
                    target.registerTransferField(ServerConstants.TownMap);
                } else {
                    target.registerTransferField(100000000);
                }
            } else if (getPlayer().getOneInfoQuestInteger(501600, "complete") >= 1) {
                Calendar CAL = new GregorianCalendar(Locale.KOREA);
                int day = CAL.getTime().getDay();
                boolean doubleEXP = false;
                if (day == 6 || day == 0) { // สุดสัปดาห์
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
                        self.say(
                                "\r\n#e#bEXP#k #r" + expString
                                        + "#k#n และ #r100 Ganglim Point#k#n ครับ!\r\n(มอบรางวัลเมื่อออกจากแผนที่)",
                                ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                    } else {
                        self.say("\r\n#e#bEXP#k #r" + expString
                                + " * 2 (กิจกรรม EXP 2 เท่า Puzzle Master สุดสัปดาห์!)#k#n และ #r100 Ganglim Point#k#n ครับ!\r\n(มอบรางวัลเมื่อออกจากแผนที่)",
                                ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                    }
                    getPlayer().gainHongboPoint(100);
                } else {
                    if (!doubleEXP) {
                        self.say(
                                "\r\nมอบ #e#bEXP#k #r" + expString
                                        + "#k#n ให้เรียบร้อยครับ!\r\n(มอบรางวัลเมื่อออกจากแผนที่)",
                                ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                    } else {
                        self.say(
                                "\r\nมอบ #e#bEXP#k #r" + expString
                                        + " * 2 (กิจกรรม EXP 2 เท่า Puzzle Master สุดสัปดาห์!)#k#n ให้เรียบร้อยครับ!\r\n(มอบรางวัลเมื่อออกจากแผนที่)",
                                ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                    }
                }
                getPlayer().temporaryStatReset(SecondaryStatFlag.DispelItemOptionByField);
                if (DBConfig.isHosting) {
                    target.registerTransferField(ServerConstants.TownMap);
                } else {
                    target.registerTransferField(100000000);
                }
                getPlayer().updateOneInfo(501600, "start", "0");
                getPlayer().updateOneInfo(501600, "today",
                        String.valueOf(getPlayer().getOneInfoQuestInteger(501600, "today") + 1));
                getPlayer().updateOneInfo(501600, "complete", "0");
                if (!doubleEXP) {
                    getPlayer().gainExpLong(exp);
                } else {
                    getPlayer().gainExpLong(exp * 2);
                }
            } else {
                getPlayer().temporaryStatReset(SecondaryStatFlag.DispelItemOptionByField);
                self.say(
                        "\r\n#e#rถ้าต่อจิ๊กซอว์ 3 ภาพไม่ครบ\r\nจะไม่สามารถมอบรางวัลให้ได้นะครับ#k\r\n\r\nครั้งหน้าพยายามเข้านะครับ!",
                        ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
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
                v = self.askMenu(
                        "มีอะไรสงสัยไหมครับ?\r\n\r\n#L1# สอบถามเกี่ยวกับ #b#e<Puzzle Master>#b#e#l\r\n#L2# สอบถามเกี่ยวกับ #b#e<วิธีเล่น Puzzle Master>#n#k#l\r\n#L3# สอบถามเกี่ยวกับ #b#e<รางวัลเกม>#n#k#l\r\n#L4# อยากกลับไปยังที่เดิม#l\r\n\r\n#L0# ไม่มีข้อสงสัยแล้ว#l",
                        ScriptMessageFlag.NpcReplacedByNpc);
                switch (v) {
                    case 1: { // เกี่ยวกับ Puzzle Master
                        self.say("\r\n#b#eMaple Variety#n#k เอพิโซด 2! #r#ePuzzle Master!#n#k",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("\r\nถ้าเป็น #b#eCreator ตัวจริง#n#k ก็ห้ามพลาดโอกาสนี้เชียวนะครับ?!",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        break;
                    }
                    case 2: { // วิธีเล่น
                        self.say(
                                "\r\n#e#b<Puzzle Master>#k#n เป็นคอนเทนต์ที่ให้สมาชิกอย่างน้อย 1 คน ~ สูงสุด 3 คน\r\nช่วยกันต่อ #e#bจิ๊กซอว์ทั้งหมด 3 ภาพ#n#k ให้เสร็จสมบูรณ์ภายในเวลาจำกัด #b#e30 นาที#n#k ครับ!",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say(
                                "\r\nนำ #b#eชิ้นส่วนจิ๊กซอว์#n#k ที่ปรากฏขึ้นทั่วแผนที่\r\nมาวางให้ตรงตำแหน่งใน #b#eกรอบรูปที่อยู่กลางหน้าจอ!#n#k",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say(
                                "\r\nอ๊ะ! คิดว่ายากเกินไปงั้นเหรอครับ? ไม่ต้องกังวลไปนะครับ!\r\n#b#eถ้าช่วยกันกับเหล่า Creator ที่มาร่วมด้วยช่วยกันล่ะก็ ทำได้แน่นอนครับ!#n#k",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        break;
                    }
                    case 3: { // รางวัลเกม
                        self.say(
                                "\r\nจะได้รับ #eEXP ตามที่กำหนด#n ตามเลเวลที่ต่อ #b#eจิ๊กซอว์ 3 ภาพ#n#k สำเร็จ\r\nเป็นรางวัลครับ!\r\n\r\n#r#eโอกาสดีๆ แบบนี้#n#k พลาดไม่ได้แล้วใช่ไหมล่ะครับ?",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        break;
                    }
                    case 4: {
                        if (1 == self.askYesNo("\r\nต้องการกลับไปที่ #e#b<จัตุรัส>#n#k หรือไม่ครับ?",
                                ScriptMessageFlag.NpcReplacedByNpc)) {
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
            self.sayOk("\r\nการทำอาหารไม่ใช่แค่งานหรอก แต่คือความหลงใหลตังหากล่ะ", ScriptMessageFlag.NpcReplacedByNpc);
    }

    public void enter_993194400() {
        getPlayer().send(CField.addPopupSay(9062561, 5000, "ยินดีต้อนรับสู่\r\nร้านอาหารทังยุน~!", ""));
        getPlayer().send(
                CField.addPopupSay(9062563, 4000, "ประสบการณ์ชีวิตอันน่าทึ่งที่เปิดเผยในร้านอาหารของเชฟในตำนาน!", ""));
        getPlayer().send(CField.addPopupSay(9062561, 5000, "เริ่มกันเลย~! ดีกว่า~!", ""));
    }

    public void enter_993194900() {

    }

    public void TyoonKitchen_setting() {
        Field field = getPlayer().getMap();
        if (field.getFieldSetInstance() != null) {
            objects.fields.fieldset.instance.TangyoonKitchen f = (objects.fields.fieldset.instance.TangyoonKitchen) field
                    .getFieldSetInstance();
            getPlayer().send(objects.fields.fieldset.instance.TangyoonKitchen.TangyoonOrder(1));
            getPlayer().temporaryStatSet(993194500, Integer.MAX_VALUE, SecondaryStatFlag.DispelItemOptionByField, 1);
            getPlayer().send(CWvsContext.InfoPacket
                    .brownMessage(
                            "ในแผนที่ปัจจุบัน ความสามารถแฝง (Potential) และความสามารถแฝงเพิ่มเติม (Additional Potential) จะไม่ถูกนำมาใช้"));
            final MapleCharacter user = getPlayer();
            Timer.EventTimer.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    if (user.getMap().getId() == 993194500) {
                        user.send(objects.fields.fieldset.instance.TangyoonKitchen.TangyoonOrder(2));
                        user.send(objects.fields.fieldset.instance.TangyoonKitchen.TangyoonObjectPacket_1());
                        user.send(CField.environmentChange("UI/TyoonKitchen_UI.img/TyoonKitchen_UI/effect/countdown",
                                16));
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
                        user.send(
                                CField.environmentChange("UI/UIMiniGame.img/mapleOneCard/Effect/screeneff/start", 16));
                        user.send(CField.environmentChange("Sound/MiniGame.img/oneCard/start", 5, 100));
                    }
                }
            }, 6000);
        }
    }
}
