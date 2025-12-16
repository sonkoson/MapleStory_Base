package script.Event;

import constants.QuestExConstants;
import network.models.CField;
import objects.quest.MapleQuest;
import scripting.newscripting.ScriptEngineNPC;

public class HasteEvent extends ScriptEngineNPC {

    public static String[] customData = { "", "", "", "", "count=0", "count=0", "RunAct=0", "suddenMK=0" };

    public void q16401s() {
        if (getPlayer().getQuestStatus(QuestExConstants.HasteEventInit.getQuestID()) < 1) {
            self.say("สวัสดี! #b#h0##k!\\r\\nกิจกรรม #eเร่งการล่า #b<Haste>#k#n กลับมาแล้ว!");
            self.say(
                    "กิจกรรม #b#e<Haste>#n#k เริ่มตั้งแต่ #b4 พฤศจิกายน 2021 เวลา 00:00 น.#k ถึง #b5 ธันวาคม เวลา 23:59 น.#k ตามชื่อเลย เป็นกิจกรรมที่ช่วย #bเร่งการล่า#k ให้เร็วขึ้น พร้อม #bการเปลี่ยนแปลงหลากหลายอย่าง#k!");
            self.say("มีการเปลี่ยนแปลงอะไรบ้างน่ะเหรอ~!");
            self.say(
                    "#b- #eElite Monster#n ปรากฏตัว #eบ่อยขึ้น#n!\\r\\n- #eRune#n ปรากฏตัว #eบ่อยขึ้น#n และใช้ได้ #eบ่อยขึ้น#n!\\r\\n- #eRune#n เพิ่มเอฟเฟกต์ EXP #eมากยิ่งขึ้น#n เป็น 2 เท่า!\\r\\n- #eSurprise Mission#n สามารถเคลียร์ได้ #eมากขึ้น#n 2 เท่าต่อวัน!\\r\\n- #ePollo & Fritto#n ปรากฏตัว #eบ่อยขึ้น#n!\\r\\n- #eFlame Wolf#n ให้ EXP #eมากขึ้น#n 1.5 เท่า!");
            self.say(
                    "เป็นไงล่ะ? แค่ฟังก็รู้สึกว่าการล่าจะเร็วขึ้นทันตาเห็นเลยใช่มั้ยล่ะ?\\r\\n\\r\\nไม่ใช่แค่นั้นนะ!\\r\\nระหว่างระยะเวลากิจกรรม สามารถทำ #b#e6 ภารกิจรายวัน#n#k ได้,\\r\\nทุกครั้งที่ทำภารกิจสำเร็จ จะได้รับ #b#e<Haste Box>#n#k ด้วย!");
            self.say(
                    "ใน #b<Haste Box>#k ทั้ง 6 กล่อง มี #bของรางวัล#k มากมายและ #bHaste Booster#k รออยู่ ดังนั้นห้ามพลาดเด็ดขาดในทุกๆ วัน~!\\r\\nเมื่อใช้ #bHaste Booster#k จะทำให้ #bMonster#k #bถูกอัญเชิญ#k #bเพิ่มขึ้น#k เป็นเวลา 100 วินาที ช่วยเร่งทั้งการล่าและการเติบโตให้เร็วยิ่งขึ้นไปอีก!");
            self.say(
                    "อีกอย่างหนึ่ง!\\r\\n\\r\\nหากทำ #e#b<ภารกิจรายวัน Haste> ทั้ง 6 อย่าง#n#k สำเร็จทั้งหมดในหนึ่งวัน...\\r\\nหึหึ... อันนั้นลองไปตรวจสอบด้วยตัวเองดูสิ!");

            for (int i = QuestExConstants.HasteEventInit.getQuestID(); i <= QuestExConstants.HasteEventSuddenMK
                    .getQuestID(); i++) {
                if (i != QuestExConstants.HasteEventEliteBoss.getQuestID()) {
                    MapleQuest.getInstance(i).forceStart(getPlayer(), 9010010, "");
                    getPlayer().updateInfoQuest(i, customData[i - QuestExConstants.HasteEventInit.getQuestID()]);
                }
            }

            getPlayer().updateInfoQuest(QuestExConstants.HasteEvent.getQuestID(),
                    "M1=0;M2=0;M3=0;M4=0;M5=0;M6=0;date=21/11/03;booster=0;openBox=0;unlockBox=0;str=กำลังท้าทายกล่องขั้นที่ 1! ทำภารกิจรายวันสำเร็จ 1 อย่าง!");
        } else {
            getPlayer().send(CField.UIPacket.openUI(1251));
        }
    }

    public void weekHQuest() {
        self.say(
                "#b#eเอาล่ะ ในที่สุด! <Haste Hidden Mission>#n#k ก็เปิดแล้ว!\r\n\r\nตั้งแต่นี้จนถึง #b5 ธันวาคม 2021 เวลา 23:59 น.#k\r\nต้องล่า #b#eมอนสเตอร์ระดับเลเวล 44,444 ตัว#n#k... ไม่สิ.. #b#e88,888 ตัว#n#k ถึงจะเสร็จนะ!");
        self.say(
                "ใน #b#e<Haste Hidden Mission Box>#n#k นั้น..\r\nสามารถรับ #b#e#i2631097:# #t2631097:#,\r\n#i1114317:# #t1114317:##n#k ได้ พยายามเข้านะ!");
    }

    public void useHasteBooster() {
        if (1 == self.askYesNo(
                "#r#eHaste Booster#n#k จะใช้งานมั้ย?\r\n#b#eมอนสเตอร์จะถูกอัญเชิญเพิ่มขึ้นเป็นเวลา 100 วินาที!#n#k\r\nช่วยเร่งการล่าได้อย่างแท้จริงเลยล่ะ!\r\n\r\n#e<กรณีที่ไม่สามารถใช้งานได้>#n\r\n 1. ในฟิลด์ที่ไม่มีมอนสเตอร์ระดับเลเวล หรือในหมู่บ้าน\r\n 2. กรณีที่ Elite Boss ถูกอัญเชิญออกมา\r\n 3. กรณีที่ตนเองกำลังใช้งาน Haste Booster อยู่\r\n 4. กรณีที่ผู้เล่นคนอื่นกำลังใช้งาน Haste Booster อยู่")) {
            getPlayer().getMap().startHasteBooster(getPlayer());
        }
    }

}
