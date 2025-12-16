package script.Profession;

import constants.GameConstants;
import network.models.CField;
import objects.item.MapleInventoryManipulator;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.users.skills.Skill;
import objects.users.skills.SkillEntry;
import objects.users.skills.SkillFactory;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

import java.util.*;

public class Profession extends ScriptEngineNPC {

    public void himmel() {
        self.say(
                "สงสัยเกี่ยวกับทักษะวิชาชีพเหรอ... ข้าจะอธิบายให้ฟังคร่าวๆ นะ ในหมู่บ้านนี้มีผู้เชี่ยวชาญอยู่ทั้งหมด 5 คน ได้แก่ #bเก็บสมุนไพร, ขุดแร่, สร้างอุปกรณ์, สร้างเครื่องประดับ, และเล่นแร่แปรธาตุ#k");
        self.say(
                "การรวบรวมวัตถุดิบประกอบด้วย การเก็บสมุนไพร และ การขุดแร่ เจ้าสามารถเรียนทั้ง 2 อย่างได้ผ่าน Sachel และ Novum");
        self.say(
                "การสร้างประกอบด้วย การสร้างอุปกรณ์, การสร้างเครื่องประดับ, และการเล่นแร่แปรธาตุ เจ้าสามารถเลือกเรียนได้เพียง 1 อย่างจาก Eissen, Melts หรือ Karayen");
        self.sayOk(
                "แต่ว่า การสร้างอุปกรณ์และเครื่องประดับจำเป็นต้องมีทักษะขุดแร่ ส่วนการเล่นแร่แปรธาตุต้องมีทักษะเก็บสมุนไพรนะ");
    }

    public void gatherTuto() {
        initNPC(MapleLifeFactory.getNPC(9031008));
        int[] mapList = new int[] { 910001005, 910001006, 910001003, 910001004, 910001008, 910001007, 910001010,
                910001009 };
        StringBuilder text = new StringBuilder("จะย้ายไปที่เหมืองหรือฟาร์มดีล่ะ?\r\n");

        for (int i = 0; i < mapList.length; i++) {
            text.append("#b#L" + i + "##m" + mapList[i] + "##k ย้ายไป\r\n");
        }

        int v0 = self.askMenu(text.toString());

        if (v0 >= 0 && v0 < mapList.length) {
            /*
             * int itemID;
             * if (v0 > 3) {
             * itemID = 4001570 + v0 - 5;
             * } else {
             * itemID = 4001480 + v0;
             * }
             * if (getPlayer().haveItem(itemID, 1, false, true)) {
             * getPlayer().removeItem(itemID, -1);
             */
            getPlayer().timeMoveMap(910001000, mapList[v0], 10 * 60);
            // } else {
            // self.sayOk("#b#i" + itemID + "##z" + itemID + "##k จำเป็นต้องมีไอเท็มครับ");
            // }
        }
    }

    public void herbalism() {
        int skillID = 92000000;
        int v0 = -1;
        if (getPlayer().getProfessionLevel(skillID) <= 0) {
            v0 = self.askMenu(
                    "สวัสดีครับ ให้ช่วยอะไรไหมครับ?\r\n#L0#ฟังคำอธิบายเกี่ยวกับ #b#eการเก็บสมุนไพร#n#l\r\n#L1#เรียน #eการเก็บสมุนไพร#n#k#l");
        } else {
            v0 = self.askMenu(
                    "สวัสดีครับ ให้ช่วยอะไรไหมครับ?\r\n#L2#เพิ่มเลเวล #b#eการเก็บสมุนไพร#n#l\r\n#L3#แลกเปลี่ยน #b#t4022023##k#l");
        }

        switch (v0) {
            case 0: {
                self.sayOk(
                        "การเก็บสมุนไพรคือทักษะที่ใช้เก็บสมุนไพรตามแผนที่ต่างๆ สมุนไพรที่เก็บได้สามารถนำไปใส่ในขวดสมุนไพรที่ #p9031007# ขาย เพื่อนำไปสกัดเป็นวัตถุดิบที่จำเป็นสำหรับ การสร้างอุปกรณ์, การสร้างเครื่องประดับ, และการเล่นแร่แปรธาตุ ครับ");
                break;
            }
            case 1: {
                if (getPlayer().getQuestStatus(3194) != 2) {
                    self.sayOk(
                            "หากต้องการเรียนการเก็บสมุนไพร ท่านต้องไปหา #bHimmel#k เพื่อฟังบรรยายเกี่ยวกับวิชาชีพก่อนครับ ท่านสามารถพบ Himmel ได้ที่ทางเข้าหมู่บ้าน");
                } else if (getPlayer().getProfessionLevel(skillID) <= 0) {
                    if (1 == self.askYesNo(
                            "ต้องการเรียน #bการเก็บสมุนไพร#k ค่าใช้จ่ายคือ #b5000 Mesos#k ยืนยันที่จะเรียนหรือไม่?\r\n#b")) {
                        getPlayer().changeProfessionLevelExp(skillID, 1, 0, (byte) 10);
                        self.sayOk(
                                "ดีเลย ท่านได้เรียนรู้การเก็บสมุนไพรเรียบร้อยแล้ว ท่านต้องสะสมความชำนาญให้เต็มก่อนถึงจะอัพเลเวลได้ แล้วกลับมาหาข้าอีกครั้งนะ");
                    } else {
                        self.sayOk("ท่านเป็นคนรอบคอบสินะ ดีครับ ลองคิดดูให้ดีแล้วค่อยกลับมาใหม่นะครับ");
                    }
                }
                break;
            }
            case 2: {
                if (getPlayer().addProfessionLevel(skillID)) {
                    self.sayOk("เลเวลการเก็บสมุนไพรกลายเป็น " + getPlayer().getProfessionLevel(skillID)
                            + " แล้วครับ เพิ่ม Empathy ให้ "
                            + ((int) Math.pow(2, getPlayer().getProfessionLevel(skillID))) + " ลองตรวจสอบดูนะครับ");
                } else {
                    self.sayOk("ท่านยังสะสมความชำนาญไม่ครบเลยครับ หากความชำนาญไม่เต็มจะไม่สามารถอัพเลเวลได้นะครับ");
                }
                break;
            }
            case 3: {
                int itemQty = (int) (getPlayer().getItemQuantity(4022023, false) / 100);
                int number = self.askNumber(
                        "ต้องการ #t2028066# กี่ชิ้นครับ? \r\nใช้ #b#t4022023# 100 ชิ้น#k เพื่อแลก #i2028066:# #b#t2028066# 1 ชิ้น#k",
                        itemQty, 1, itemQty);

                if (MapleInventoryManipulator.checkSpace(getPlayer().getClient(), 2028066, number, "")) {
                    getPlayer().removeItem(4022023, itemQty * -100);
                    getPlayer().gainItem(2028066, number);
                    self.sayOk("แลกเปลี่ยน #t4022023# เรียบร้อยแล้วครับ");
                } else {
                    self.sayOk(
                            "ของในตัวเยอะเกินไปนะครับ? กรุณาทำช่องช่อง Use ให้ว่างอย่างน้อย 1 ช่อง แล้วลองใหม่นะครับ");
                }
                break;
            }
        }
    }

    public void mining() {
        int skillID = 92010000;
        int v0 = -1;
        if (getPlayer().getProfessionLevel(skillID) <= 0) {
            v0 = self.askMenu(
                    "ว่าไง. ต้องการอะไรจาก #bNovum#k ผู้เชี่ยวชาญด้านการขุดแร่คนนี้?\r\n#L0#ฟังคำอธิบายเกี่ยวกับ #b#eการขุดแร่#n#l\r\n#L1#เรียน #eการขุดแร่#n#k#l");
        } else {
            v0 = self.askMenu(
                    "ว่าไง. ต้องการอะไรจาก #bNovum#k ผู้เชี่ยวชาญด้านการขุดแร่คนนี้?\r\n#L2#เพิ่มเลเวล #b#eการขุดแร่#n#l\r\n#L3#แลกเปลี่ยน #b#t4011010##k#l");
        }

        switch (v0) {
            case 0: {
                self.sayOk(
                        "การขุดแร่คือทักษะที่ใช้ขุดแร่ต่างๆ ตามแผนที่ แร่ที่ขุดได้สามารถนำไปใส่ในเบ้าหลอมที่ #p9031006# ขาย เพื่อสกัดเป็นวัตถุดิบที่จำเป็นสำหรับ การสร้างอุปกรณ์, การสร้างเครื่องประดับ, และการเล่นแร่แปรธาตุ ไงล่ะ");
                break;
            }
            case 1: {
                if (getPlayer().getQuestStatus(3194) != 2) {
                    self.sayOk("ถ้าจะเรียนขุดแร่ ต้องไปทักทาย #bHimmel#k ก่อนนะ~ เขาอยู่ที่ทางเข้าหมู่บ้านน่ะ");
                } else if (getPlayer().getProfessionLevel(skillID) <= 0) {
                    if (1 == self.askYesNo(
                            "#bการขุดแร่#k สินะ? คิดจะเรียนจริงๆ ใช่ไหม? มีค่าใช้จ่ายนิดหน่อย #b5000 Mesos#k น่ะ มีเงินพอใช่ไหม?\r\n#b")) {
                        getPlayer().changeProfessionLevelExp(skillID, 1, 0, (byte) 10);
                        self.sayOk(
                                "เอาล่ะ ข้าถ่ายทอดพื้นฐานการขุดแร่ให้เจ้าแล้ว ถ้าสะสมความชำนาญครบจะเรียนรู้ขั้นต่อไปได้ ถ้าสะสมครบแล้วก็กลับมาหาข้าสิ");
                    } else {
                        self.sayOk("เป็นคนรอบคอบดีนี่ เอาสิ ลองคิดดูให้ดีแล้วค่อยกลับมาใหม่");
                    }
                }
                break;
            }
            case 2: {
                if (getPlayer().addProfessionLevel(skillID)) {
                    self.sayOk("เลเวลการขุดแร่กลายเป็น " + getPlayer().getProfessionLevel(skillID)
                            + " แล้ว เพิ่ม Willpower ให้ "
                            + ((int) Math.pow(2, getPlayer().getProfessionLevel(skillID))) + " ลองตรวจสอบดูสิ");
                } else {
                    self.sayOk(
                            "ยังสะสมความชำนาญไม่ครบเลยนี่นา ไปสะสมความชำนาญของเลเวลปัจจุบันให้เต็มก่อนแล้วค่อยกลับมา");
                }
                break;
            }
            case 3: {
                int itemQty = (int) (getPlayer().getItemQuantity(4011010, false) / 100);
                int number = self.askNumber(
                        "ต้องการ #t2028067# กี่ชิ้น? \r\nใช้ #b#t4011010# 100 ชิ้น#k เพื่อแลก #i2028067:# #b#t2028067# 1 ชิ้น#k",
                        itemQty, 1, itemQty);

                if (MapleInventoryManipulator.checkSpace(getPlayer().getClient(), 2028067, number, "")) {
                    getPlayer().removeItem(4011010, itemQty * -100);
                    getPlayer().gainItem(2028067, number);
                    self.sayOk("แลกเปลี่ยน #t4011010# เรียบร้อยแล้ว");
                } else {
                    self.sayOk("ของเยอะเกินไปมั้ง? ไปเคลียร์ช่อง Use ให้ว่างอย่างน้อย 1 ช่องก่อนค่อยมาคุย");
                }
                break;
            }
        }
    }

    public void equip_product() {
        int skillID = 92020000;
        int parentSkillID = 92010000;
        int v0 = -1;
        if (getPlayer().getProfessionLevel(skillID) <= 0) {
            v0 = self.askMenu(
                    "ว่าไง ต้องการอะไรจาก #bEissen#k ผู้เชี่ยวชาญด้านการสร้างอุปกรณ์คนนี้?\r\n#L0#ฟังคำอธิบายเกี่ยวกับ #b#eการสร้างอุปกรณ์#n#l\r\n#L1#เรียน #eการสร้างอุปกรณ์#n#k#l");
        } else {
            v0 = self.askMenu(
                    "ว่าไง ต้องการอะไรจาก #bEissen#k ผู้เชี่ยวชาญด้านการสร้างอุปกรณ์คนนี้?\r\n#L2#เพิ่มเลเวล #b#eการสร้างอุปกรณ์#n#l\r\n#L3#รีเซ็ตทักษะการสร้างอุปกรณ์#k#l");
        }

        switch (v0) {
            case 0: {
                self.sayOk(
                        "การสร้างอุปกรณ์คือการนำแร่และอัญมณีที่ผ่านการถลุงด้วยการขุดแร่มาหลอมในเตาหลอมขนาดยักษ์ เพื่อสร้างเป็นอุปกรณ์ป้องกันและอาวุธที่มีประโยชน์ ถ้าเรียนกับ Eissen คนนี้ เจ้าจะสามารถสร้างอาวุธและชุดเกราะที่ไม่เคยเห็นที่ไหนมาก่อนได้ด้วยนะ");
                break;
            }
            case 1: {
                if (getPlayer().getQuestStatus(3194) != 2) {
                    self.sayOk(
                            "ดูเหมือนจะสนใจการสร้างอุปกรณ์สินะ ถ้าอยากเรียนต้องไปทักทาย #bHimmel#k ก่อนนะ~ เขาอยู่ที่ทางเข้าหมู่บ้านน่ะ");
                } else if (getPlayer().getProfessionLevel(parentSkillID) <= 0) {
                    self.sayOk(
                            "ข้าไม่สอนการสร้างอุปกรณ์ให้กับคนที่ไม่รู้จักการขุดแร่หรอกนะ เพราะถ้าไม่มีวัตถุดิบก็คงบากบั่นทำไม่ได้... ลองไปเรียนการขุดแร่จาก #bNovum#k ปรมาจารย์ด้านการขุดแร่ที่อยู่ข้างๆ ก่อนสิ");
                } else if (getPlayer().getProfessionLevel(skillID) <= 0) {
                    if (1 == self.askYesNo(
                            "อยากเรียน #bการสร้างอุปกรณ์#k งั้นรึ? ถ้าอยากเรียนต้องจ่ายค่าเรียนนะ... #b5000 Mesos#k น่ะ... จะเรียนจริงๆ รึเปล่า?\r\n#b")) {
                        getPlayer().changeProfessionLevelExp(skillID, 1, 0, (byte) 10);
                        self.sayOk(
                                "ดีมาก เรียนรู้การสร้างอุปกรณ์เรียบร้อยแล้ว ถ้าสะสมความชำนาญจนเต็มจะสามารถเพิ่มเลเวลได้ แล้วอย่าลืมกลับมาเพิ่มเลเวลล่ะ");
                    } else {
                        self.sayOk("เป็นคนรอบคอบดีนี่ เอาสิ ลองคิดดูให้ดีแล้วค่อยกลับมาใหม่");
                    }
                }
                break;
            }
            case 2: {
                if (getPlayer().addProfessionLevel(skillID)) {
                    if (getPlayer().getProfessionLevel(skillID) == 11) {
                        self.sayOk("ดีมาก เลเวลการสร้างอุปกรณ์เป็นระดับช่างฝีมือแล้ว ข้าเพิ่มค่า Diligence ให้ "
                                + ((int) Math.pow(2, getPlayer().getProfessionLevel(skillID))) + " ลองตรวจสอบดูสิ");
                    } else if (getPlayer().getProfessionLevel(skillID) == 12) {
                        self.sayOk("ดีมาก เลเวลการสร้างอุปกรณ์เป็นระดับปรมาจารย์แล้ว ข้าเพิ่มค่า Diligence ให้ "
                                + ((int) Math.pow(2, getPlayer().getProfessionLevel(skillID))) + " ลองตรวจสอบดูสิ");
                    } else {
                        self.sayOk("ดีมาก เลเวลการสร้างอุปกรณ์เป็นระดับ " + getPlayer().getProfessionLevel(skillID)
                                + " แล้ว ข้าเพิ่มค่า Diligence ให้ "
                                + ((int) Math.pow(2, getPlayer().getProfessionLevel(skillID))) + " ลองตรวจสอบดูสิ");
                    }
                } else {
                    self.sayOk(
                            "ยังสะสมความชำนาญไม่ครบเลยนี่นา ไปสะสมความชำนาญของเลเวลปัจจุบันให้เต็มก่อนแล้วค่อยกลับมา");
                }
                break;
            }
            case 3: {
                if (1 == self.askYesNo(
                        "จะรีเซ็ตทักษะการสร้างอุปกรณ์กลับไปเป็นสถานะที่ยังไม่ได้เรียนงั้นรึ? เลเวลและความชำนาญที่สั่งสมมาจะหายไปหมดเลยนะ จะรีเซ็ตจริงๆ เหรอ?")) {
                    getPlayer().changeProfessionLevelExp(skillID, 0, 0, (byte) 10);
                    self.sayOk("ข้ารีเซ็ตทักษะการสร้างอุปกรณ์ให้หมดแล้ว ถ้าอยากเรียนใหม่เมื่อไหร่ก็กลับมาได้เสมอ");
                } else {
                    self.sayOk("นั่นสินะ เรื่องรีเซ็ตควรคิดให้รอบคอบก่อนตัดสินใจนั่นแหละดีแล้ว");
                }
                break;
            }
        }
    }

    public void acc_product() {
        int skillID = 92030000;
        int parentSkillID = 92010000;
        int v0 = -1;
        if (getPlayer().getProfessionLevel(skillID) <= 0) {
            v0 = self.askMenu(
                    "งานอดิเรกอันสูงส่งของ #bMelts#k ผู้สง่างามคือการชื่นชมอัญมณี... พอได้มองดูอัญมณีที่เปล่งประกายระยิบระยับแล้วก็ลืมเวลาไปเลย... อะแฮ่ม~ เจ้าก็สนใจงั้นเหรอ? ดูไม่เหมือนแบบนั้นเลยนะเนี่ย?\r\n#L0#ฟังคำอธิบายเกี่ยวกับ #b#eการสร้างเครื่องประดับ#n#l\r\n#L1#เรียน #eการสร้างเครื่องประดับ#n#k#l");
        } else {
            v0 = self.askMenu(
                    "งานอดิเรกอันสูงส่งของ #bMelts#k ผู้สง่างามคือการชื่นชมอัญมณี... พอได้มองดูอัญมณีที่เปล่งประกายระยิบระยับแล้วก็ลืมเวลาไปเลย... อะแฮ่ม~ เจ้าก็สนใจงั้นเหรอ? ดูไม่เหมือนแบบนั้นเลยนะเนี่ย?\r\n#L2#เพิ่มเลเวล #b#eการสร้างเครื่องประดับ#n#l\r\n#L3#รีเซ็ตทักษะการสร้างเครื่องประดับ#k#l");
        }

        switch (v0) {
            case 0: {
                self.sayOk(
                        "ถ้าจะให้สอนเรื่องการสร้างเครื่องประดับ ก็ควรจะเริ่มจากรากฐานความงามของอัญมณีก่อน แต่จะเล่าสั้นๆ ก็แล้วกันนะ เพราะต่อให้เล่าทั้งคืนก็คงไม่จบ... \r\nการสร้างเครื่องประดับนั้นง่ายมาก แค่นำอัญมณีและแร่ที่ยังไม่ได้เจียระไนมาขัดเกลาให้งดงามแล้วทำเป็นเครื่องประดับเพื่อให้มันเปล่งประกายดั้งเดิมออกมา ในกระบวนการนั้นพลังที่ซ่อนอยู่จะถูกปลุกออกมา ทำให้เจ้าแข็งแกร่งและงดงามยิ่งขึ้นไงล่ะ");
                break;
            }
            case 1: {
                if (getPlayer().getQuestStatus(3194) != 2) {
                    self.sayOk(
                            "อยากเรียนการสร้างเครื่องประดับงั้นเหรอ?? อะแฮ่ม~? ถ้าอยากเรียนทักษะวิชาชีพต้องไปทักทาย #bHimmel#k ก่อนนะ~ เขาอยู่ที่ทางเข้าหมู่บ้าน ลองไปหาเขาดูสิ?");
                } else if (getPlayer().getProfessionLevel(parentSkillID) <= 0) {
                    self.sayOk(
                            "แหม~ ทำยังไงดีล่ะ? ถ้าอยากสร้างเครื่องประดับ ต้องเรียนการขุดแร่ก่อนนะ เพราะการสร้างเครื่องประดับต้องใช้โลหะและอัญมณีต่างๆ ไงล่ะ~ ถ้าไปทางซ้ายจะมีปรมาจารย์ด้านการขุดแร่ที่ชื่อ #bNovum#k หน้าตาเหมือนเห็ดอ้วนๆ อยู่ ลองไปหาเขาดูไหม?");
                } else if (getPlayer().getProfessionLevel(skillID) <= 0) {
                    if (1 == self.askYesNo(
                            "จะเรียน #bการสร้างเครื่องประดับ#k จริงๆ เหรอ?..ค่าเรียน #b5000 Mesos#k นะ\r\n#b")) {
                        getPlayer().changeProfessionLevelExp(skillID, 1, 0, (byte) 10);
                        self.sayOk(
                                "ตกลง! เยี่ยม! ยอดเยี่ยม!! เอาล่ะ~ ข้าถ่ายทอดความรู้เรื่องการสร้างเครื่องประดับให้เจ้าหมดแล้ว ถ้าสะสมความชำนาญจนเต็มจะเพิ่มเลเวลได้ อย่าลืมกลับมาเพิ่มเลเวลล่ะ");
                    } else {
                        self.sayOk(
                                "อะไรกัน!!! การสร้างเครื่องประดับเป็นทักษะที่ดีจริงๆ นะ บอกว่าจะขอคิดดูก่อนเนี่ย รอบคอบเกินไปหรือเปล่า?");
                    }
                }
                break;
            }
            case 2: {
                if (getPlayer().addProfessionLevel(skillID)) {
                    if (getPlayer().getProfessionLevel(skillID) == 11) {
                        self.sayOk(
                                "ยอดเยี่ยม! เลเวลการสร้างเครื่องประดับเป็นระดับช่างฝีมือแล้ว! ข้าเพิ่มค่า Diligence ให้ "
                                        + ((int) Math.pow(2, getPlayer().getProfessionLevel(skillID)))
                                        + " ลองตรวจสอบดูสิ?");
                    } else if (getPlayer().getProfessionLevel(skillID) == 12) {
                        self.sayOk(
                                "ยอดเยี่ยม! เลเวลการสร้างเครื่องประดับเป็นระดับปรมาจารย์แล้ว! ข้าเพิ่มค่า Diligence ให้ "
                                        + ((int) Math.pow(2, getPlayer().getProfessionLevel(skillID)))
                                        + " ลองตรวจสอบดูสิ?");
                    } else {
                        self.sayOk("ยอดเยี่ยม! เลเวลการสร้างเครื่องประดับเป็นระดับ "
                                + getPlayer().getProfessionLevel(skillID) + " แล้ว! ข้าเพิ่มค่า Diligence ให้ "
                                + ((int) Math.pow(2, getPlayer().getProfessionLevel(skillID))) + " ลองตรวจสอบดูสิ?");
                    }
                } else {
                    self.sayOk(
                            "ยังสะสมความชำนาญไม่ครบเลยนี่นา ไปสะสมความชำนาญของเลเวลปัจจุบันให้เต็มก่อนแล้วค่อยกลับมานะ");
                }
                break;
            }
            case 3: {
                if (1 == self.askYesNo(
                        "อยากจะลบการสร้างเครื่องประดับงั้นเหรอ? เบื่อแล้วเหรอ? ความชำนาญและเลเวลที่อุตส่าห์สะสมมาอย่างยากลำบาก... เงินและความพยายาม... จะกลายเป็นฟองอากาศไปในพริบตานะ... จะรีเซ็ตจริงๆ เหรอ?")) {
                    getPlayer().changeProfessionLevelExp(skillID, 0, 0, (byte) 10);
                    self.sayOk("รีเซ็ตให้หมดแล้ว... ใจร้ายจัง ถ้าอยากเรียนใหม่อีกเมื่อไหร่ก็กลับมานะ");
                } else {
                    self.sayOk("นั่นสินะ จะรีเซ็ตทั้งทีต้องคิดให้รอบคอบก่อนตัดสินใจสิ");
                }
                break;
            }
        }
    }

    public void alchemy() {
        int skillID = 92040000;
        int parentSkillID = 92000000;
        int v0 = -1;
        if (getPlayer().getProfessionLevel(skillID) <= 0) {
            v0 = self.askMenu(
                    "สวัสดีค่ะ สนใจการเล่นแร่แปรธาตุไหมคะ?\r\n#L0#ฟังคำอธิบายเกี่ยวกับ #b#eการเล่นแร่แปรธาตุ#n#l\r\n#L1#เรียน #eการเล่นแร่แปรธาตุ#n#k#l");
        } else {
            v0 = self.askMenu(
                    "สวัสดีค่ะ สนใจการเล่นแร่แปรธาตุไหมคะ?\r\n#L2#เพิ่มเลเวล #b#eการเล่นแร่แปรธาตุ#n#l\r\n#L3#รีเซ็ตทักษะการเล่นแร่แปรธาตุ#k#l");
        }

        switch (v0) {
            case 0: {
                self.sayOk(
                        "การเล่นแร่แปรธาตุคือทักษะการใช้น้ำมันสมุนไพรมาปรุงยาต่างๆ ค่ะ สามารถทำยาฟื้นฟู HP MP และยังทำยาเพิ่มความแข็งแกร่งได้ด้วยนะ แถมยังทำยาแปลกๆ ที่ไม่เคยเห็นมาก่อนได้ด้วยค่ะ");
                break;
            }
            case 1: {
                if (getPlayer().getQuestStatus(3194) != 2) {
                    self.sayOk(
                            "ขอโทษด้วยนะคะ แต่ท่านต้องไปฟังบรรยายเกี่ยวกับทักษะวิชาชีพจาก Himmel ก่อนถึงจะสอนให้ได้ค่ะ ลองไปหา Himmel ดูไหมคะ?");
                } else if (getPlayer().getProfessionLevel(parentSkillID) <= 0) {
                    self.sayOk(
                            "การเล่นแร่แปรธาตุต้องเรียนหลังจากเรียนการเก็บสมุนไพรแล้วค่ะ ถ้าไปทางขวาจะเจอ #bSachel#k ปรมาจารย์ด้านการเก็บสมุนไพรที่กำลังต้มสมุนไพรอยู่นะคะ");
                } else if (getPlayer().getProfessionLevel(skillID) <= 0) {
                    if (1 == self.askYesNo(
                            "จะเรียน #bการเล่นแร่แปรธาตุ#k จริงๆ เหรอคะ? ต้องใช้ค่าเรียน #b5000 Mesos#k นะคะ \r\n#b")) {
                        getPlayer().changeProfessionLevelExp(skillID, 1, 0, (byte) 10);
                        self.sayOk(
                                "เอาล่ะ~ สอนพื้นฐานการเล่นแร่แปรธาตุให้แล้วนะคะ ถ้าสะสมความชำนาญจนเต็มจะเพิ่มเลเวลได้ อย่าลืมกลับมาหาฉันเพื่อเรียนรู้ความรู้ใหม่ๆ นะคะ");
                    } else {
                        self.sayOk(
                                "การจะเรียนทักษะวิชาชีพต้องรอบคอบสินะคะ เพราะต้องใช้เวลาและความพยายามมากนี่นา ลองคิดดูให้ดีแล้วถ้าตัดสินใจได้แล้วค่อยกลับมานะคะ");
                    }
                }
                break;
            }
            case 2: {
                if (getPlayer().addProfessionLevel(skillID)) {
                    if (getPlayer().getProfessionLevel(skillID) == 11) {
                        self.sayOk("ดีเลยค่ะ เลเวลการเล่นแร่แปรธาตุเป็นระดับช่างฝีมือแล้ว เพิ่มค่า Insight ให้ "
                                + ((int) Math.pow(2, getPlayer().getProfessionLevel(skillID))) + " ลองตรวจสอบดูนะคะ");
                    } else if (getPlayer().getProfessionLevel(skillID) == 12) {
                        self.sayOk("ดีเลยค่ะ เลเวลการเล่นแร่แปรธาตุเป็นระดับปรมาจารย์แล้ว เพิ่มค่า Insight ให้ "
                                + ((int) Math.pow(2, getPlayer().getProfessionLevel(skillID))) + " ลองตรวจสอบดูนะคะ");
                    } else {
                        self.sayOk("ดีเลยค่ะ เลเวลการเล่นแร่แปรธาตุเป็นระดับ " + getPlayer().getProfessionLevel(skillID)
                                + " แล้ว เพิ่มค่า Insight ให้ "
                                + ((int) Math.pow(2, getPlayer().getProfessionLevel(skillID))) + " ลองตรวจสอบดูนะคะ");
                    }
                } else {
                    self.sayOk(
                            "ดูเหมือนยังสะสมความชำนาญไม่ครบนะคะ ไปสะสมความชำนาญของเลเวลปัจจุบันให้เต็มก่อนแล้วค่อยกลับมานะคะ");
                }
                break;
            }
            case 3: {
                if (1 == self.askYesNo(
                        "จะรีเซ็ตทักษะการเล่นแร่แปรธาตุกลับไปเป็นสถานะที่ยังไม่ได้เรียนงั้นเหรอคะ? ถ้ารีเซ็ต เลเวลและความชำนาญที่สั่งสมมาจะหายไปหมดเลยนะคะ... จะรีเซ็ตจริงๆ เหรอคะ?")) {
                    getPlayer().changeProfessionLevelExp(skillID, 0, 0, (byte) 10);
                    self.sayOk(
                            "รีเซ็ตทักษะการเล่นแร่แปรธาตุให้หมดแล้วค่ะ ถ้าอยากเรียนใหม่เมื่อไหร่ก็กลับมาได้เสมอนะคะ");
                } else {
                    self.sayOk("ค่ะ เรื่องรีเซ็ตควรคิดให้รอบคอบก่อนตัดสินใจนะคะ");
                }
                break;
            }
        }
    }

    public void open_herb() {
        int skillID = 92000000;
        open_meister_object(skillID, "ไม่สามารถใช้งานได้เนื่องจากยังไม่ได้เรียนการเก็บสมุนไพร");
    }

    public void open_mining() {
        int skillID = 92010000;
        open_meister_object(skillID, "ไม่สามารถใช้งานได้เนื่องจากยังไม่ได้เรียนการขุดแร่");
    }

    public void open_equipP() {
        int skillID = 92020000;
        open_meister_object(skillID, "ไม่สามารถใช้งานได้เนื่องจากยังไม่ได้เรียนการสร้างอุปกรณ์");
    }

    public void open_accP() {
        int skillID = 92030000;
        open_meister_object(skillID, "ไม่สามารถใช้งานได้เนื่องจากยังไม่ได้เรียนการสร้างเครื่องประดับ");
    }

    public void open_alchemy() {
        int skillID = 92040000;
        open_meister_object(skillID, "ไม่สามารถใช้งานได้เนื่องจากยังไม่ได้เรียนการเล่นแร่แปรธาตุ");
    }

    private void open_meister_object(int skillID, String context) {
        if (getPlayer().getProfessionLevel(skillID) >= 0) {
            getPlayer().send(CField.UIPacket.openUIOption(0x2A, 0, 0, 0));
        } else {
            getPlayer().dropMessage(5, context);
        }
    }
}
