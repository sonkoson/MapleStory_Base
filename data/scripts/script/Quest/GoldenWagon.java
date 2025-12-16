package script.Quest;

import constants.GameConstants;
import network.SendPacketOpcode;
import network.encode.PacketEncoder;
import objects.context.GoldenChariot;
import objects.item.MapleInventoryType;
import objects.quest.MapleQuest;
import objects.utils.HexTool;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;
import database.DBConfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

public class GoldenWagon extends ScriptEngineNPC {
    public void q100750s() {
        if (getPlayer().getQuestStatus(100748) == 0) {
            MapleQuest.getInstance(100748).forceComplete(getPlayer(), 2007);
        }

        PacketEncoder p = new PacketEncoder();
        p.writeShort(SendPacketOpcode.SHOW_STATUS_INFO.getValue());
        p.write(14);
        p.writeInt(252);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = sdf.format(new Date());
        String count = getPlayer().getOneInfoQuest(1234699, "count");
        String qex = "count=" + count + ";T=" + time;
        p.writeMapleAsciiString(qex);

        getPlayer().send(p.getPacket());

        p = new PacketEncoder();
        p.writeShort(SendPacketOpcode.SHOW_STATUS_INFO.getValue());
        p.write(14);
        p.writeInt(253);
        StringBuilder builder = new StringBuilder();
        builder.append("complete=")
                .append(getPlayer().getOneInfo(1234699, "complete"))
                .append(";")
                .append("day=")
                .append(getPlayer().getOneInfo(1234699, "day"))
                .append(";")
                .append("passCount=")
                .append(getPlayer().getOneInfo(1234699, "passCount"))
                .append(";")
                .append("bMaxDay=91;")
                .append("lastDate=")
                .append(getPlayer().getOneInfo(1234699, "lastDate"))
                .append(";")
                .append("cMaxDay=91");
        p.writeMapleAsciiString(builder.toString());
        getPlayer().send(p.getPacket());

        p = new PacketEncoder();
        p.writeShort(SendPacketOpcode.SHOW_STATUS_INFO.getValue());
        p.write(14);
        p.writeInt(254);
        p.writeMapleAsciiString("season=2021/02");
        getPlayer().send(p.getPacket());

        p = new PacketEncoder();
        p.writeShort(SendPacketOpcode.UI_EVENT_SET.getValue());
        p.writeInt(100748);
        p.writeInt(1254);
        getPlayer().send(p.getPacket());

        PacketEncoder s = new PacketEncoder();
        s.writeShort(SendPacketOpcode.UI_EVENT_INFO.getValue());

        s.writeInt(100748); // qID
        s.write(1);
        s.write(0);
        s.encodeBuffer(HexTool.getByteArrayFromHexString("00 C0 ED 28 09 0B D7 01")); // 2021-02-25
        s.encodeBuffer(HexTool.getByteArrayFromHexString("80 29 99 B6 0B 63 D7 01")); // 2021-06-16
        s.writeInt(126);
        s.writeInt(100748);
        s.writeInt(0);
        s.write(0);
        s.writeInt(0);
        s.writeInt(252); // WorldShareQID 1
        s.writeInt(253); // WorldShareQID 2
        s.writeInt(254); // WorldShareQID 3
        s.writeInt(3600); // Total seconds to fill
        s.writeMapleAsciiString("chariotInfo4");
        s.writeMapleAsciiString("");
        s.writeMapleAsciiString("chariotPass4");
        s.writeMapleAsciiString("chariotAttend4");
        s.writeInt(0);
        s.writeInt(GoldenChariot.goldenChariotList.size());

        for (GoldenChariot gc : GoldenChariot.goldenChariotList) {
            s.writeInt(gc.getDay());
            int itemId = gc.getItemID();
            if (itemId == 1672077) {
                itemId = 4000000;
            }
            s.writeInt(itemId);
            s.writeInt(0);
            s.write(0);
            s.writeInt(0);
            s.write(0);
            s.writeInt(0);
            s.writeInt(0);
            s.write(0);
        }
        s.writeInt(0);
        s.writeInt(1254);
        getPlayer().send(s.getPacket());
    }

    public void goldenchariot() {
        if (DBConfig.isGanglim) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date StartDate = dateFormat.parse("2025-05-31");
                Date EndDate = dateFormat.parse("2025-12-31");
                String Today = dateFormat.format(new Date());
                String Start = dateFormat.format(StartDate);
                String End = dateFormat.format(EndDate);
                Date TodateDate = dateFormat.parse(Today);

                if (TodateDate.before(StartDate) || TodateDate.after(EndDate)) {
                    self.sayOk("#fs11#ไม่อยู่ในระยะเวลากิจกรรม Golden Chariot\r\n\r\nระยะเวลากิจกรรม : " + Start + " ~ "
                            + End, ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                    return;
                }
            } catch (ParseException ex) {
            }
        }

        boolean isWeekend = (new Date().getDay() == 0 || new Date().getDay() == 6);
        if (!getPlayer().getOneInfoQuest(1234699, "complete").equals("1")) {
            if (!isWeekend) {
                if ((getPlayer().getOneInfoQuestInteger(1234699, "day") + 1) % 7 != 0) {
                    getPlayer().updateOneInfo(1234699, "complete", "1");
                    getPlayer().updateOneInfo(1234699, "day",
                            "" + (getPlayer().getOneInfoQuestInteger(1234699, "day") + 1));
                    PacketEncoder p = new PacketEncoder();
                    p.writeShort(SendPacketOpcode.SHOW_STATUS_INFO.getValue());
                    p.write(14);
                    p.writeInt(253);
                    String qex = "complete=1;day=" + getPlayer().getOneInfo(1234699, "day") + ";"
                            + "passCount=0;bMaxDay=91;lastDate=" + getPlayer().getOneInfo(1234699, "lastDate")
                            + ";cMaxDay=91";
                    p.writeMapleAsciiString(qex);
                    getPlayer().send(p.getPacket());
                    self.sayOk("เช็คชื่อเรียบร้อย! ประทับตราให้ #b1 อัน#k แล้วนะ!", ScriptMessageFlag.NpcReplacedByNpc,
                            ScriptMessageFlag.NoEsc);
                } else { // Gift receiving date
                    GoldenChariot gc = null;
                    for (GoldenChariot g : GoldenChariot.goldenChariotList) {
                        if (g.getDay() == getPlayer().getOneInfoQuestInteger(1234699, "day") + 1) {
                            gc = g;
                            break;
                        }
                    }
                    if (self.askYesNo(
                            "จะรับของรางวัลหลังจากเช็คชื่อเสร็จเลยไหม?\r\n\r\n#e#b#i" + gc.getItemID() + "# #z"
                                    + gc.getItemID() + "#",
                            ScriptMessageFlag.NpcReplacedByNpc) == 1) {
                        if (GameConstants.isPet(gc.getItemID())) {
                            if (getPlayer().getInventory(MapleInventoryType.CASH).getNumFreeSlot() >= 1) {
                                exchangePetPeriod(gc.getItemID(), 90);
                                getPlayer().updateOneInfo(1234699, "complete", "1");
                                getPlayer().updateOneInfo(1234699, "day",
                                        "" + (getPlayer().getOneInfoQuestInteger(1234699, "day") + 1));
                                PacketEncoder p = new PacketEncoder();
                                p.writeShort(SendPacketOpcode.SHOW_STATUS_INFO.getValue());
                                p.write(14);
                                p.writeInt(253);
                                String qex = "complete=1;day=" + getPlayer().getOneInfo(1234699, "day") + ";"
                                        + "passCount=0;bMaxDay=91;lastDate="
                                        + getPlayer().getOneInfo(1234699, "lastDate") + ";cMaxDay=91";
                                p.writeMapleAsciiString(qex);
                                getPlayer().send(p.getPacket());
                                self.sayOk("เช็คชื่อเรียบร้อย! ประทับตราให้ #b1 อัน#k แล้วนะ!",
                                        ScriptMessageFlag.NpcReplacedByNpc,
                                        ScriptMessageFlag.NoEsc);
                            } else {
                                self.sayOk("ช่องเก็บของไม่พอ! กรุณาทำช่องว่างแล้วลองใหม่อีกครั้ง",
                                        ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                            }
                        } else {
                            if (target.exchange(gc.getItemID(), gc.getItemQty()) > 0) {
                                getPlayer().updateOneInfo(1234699, "complete", "1");
                                getPlayer().updateOneInfo(1234699, "day",
                                        "" + (getPlayer().getOneInfoQuestInteger(1234699, "day") + 1));
                                PacketEncoder p = new PacketEncoder();
                                p.writeShort(SendPacketOpcode.SHOW_STATUS_INFO.getValue());
                                p.write(14);
                                p.writeInt(253);
                                String qex = "complete=1;day=" + getPlayer().getOneInfo(1234699, "day") + ";"
                                        + "passCount=0;bMaxDay=91;lastDate="
                                        + getPlayer().getOneInfo(1234699, "lastDate") + ";cMaxDay=91";
                                p.writeMapleAsciiString(qex);
                                getPlayer().send(p.getPacket());
                                self.sayOk("เช็คชื่อเรียบร้อย! ประทับตราให้ #b1 อัน#k แล้วนะ!",
                                        ScriptMessageFlag.NpcReplacedByNpc,
                                        ScriptMessageFlag.NoEsc);
                            } else {
                                self.sayOk("ช่องเก็บของไม่พอ! กรุณาทำช่องว่างแล้วลองใหม่อีกครั้ง",
                                        ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                            }
                        }
                    }
                }
            } else { // Weekend
                boolean isGiftDay = false;
                int gcDay = 0;
                for (int i = 1; i <= 2; i++) {
                    if ((getPlayer().getOneInfoQuestInteger(1234699, "day") + i) % 7 == 0) {
                        gcDay = getPlayer().getOneInfoQuestInteger(1234699, "day") + i;
                        isGiftDay = true;
                        break;
                    }
                }
                if (!isGiftDay) {
                    getPlayer().updateOneInfo(1234699, "complete", "1");
                    getPlayer().updateOneInfo(1234699, "day",
                            "" + (getPlayer().getOneInfoQuestInteger(1234699, "day") + 2));
                    PacketEncoder p = new PacketEncoder();
                    p.writeShort(SendPacketOpcode.SHOW_STATUS_INFO.getValue());
                    p.write(14);
                    p.writeInt(253);
                    String qex = "complete=1;day=" + getPlayer().getOneInfo(1234699, "day") + ";"
                            + "passCount=0;bMaxDay=91;lastDate=" + getPlayer().getOneInfo(1234699, "lastDate")
                            + ";cMaxDay=91";
                    p.writeMapleAsciiString(qex);
                    getPlayer().send(p.getPacket());
                    self.sayOk("เช็คชื่อเรียบร้อย! เพราะเป็นวันหยุดสุดสัปดาห์เลยประทับตราให้ #b2 อัน#k เลยนะ!",
                            ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                } else { // Gift receiving date
                    GoldenChariot gc = null;
                    for (GoldenChariot g : GoldenChariot.goldenChariotList) {
                        if (g.getDay() == gcDay) {
                            gc = g;
                            break;
                        }
                    }
                    if (self.askYesNo("จะรับของรางวัลหลังจากเช็คชื่อเสร็จเลยไหม?\r\n\r\n#e#b#i" + gc.getItemID()
                            + "# #z" + gc.getItemID() + "#", ScriptMessageFlag.NpcReplacedByNpc) == 1) {
                        if (GameConstants.isPet(gc.getItemID())) {
                            if (getPlayer().getInventory(MapleInventoryType.CASH).getNumFreeSlot() >= 1) {
                                exchangePetPeriod(gc.getItemID(), 90);
                                getPlayer().updateOneInfo(1234699, "complete", "1");
                                getPlayer().updateOneInfo(1234699, "day",
                                        "" + (getPlayer().getOneInfoQuestInteger(1234699, "day") + 2));
                                PacketEncoder p = new PacketEncoder();
                                p.writeShort(SendPacketOpcode.SHOW_STATUS_INFO.getValue());
                                p.write(14);
                                p.writeInt(253);
                                String qex = "complete=1;day=" + getPlayer().getOneInfo(1234699, "day") + ";"
                                        + "passCount=0;bMaxDay=91;lastDate="
                                        + getPlayer().getOneInfo(1234699, "lastDate") + ";cMaxDay=91";
                                p.writeMapleAsciiString(qex);
                                getPlayer().send(p.getPacket());
                                self.sayOk(
                                        "เช็คชื่อเรียบร้อย! เพราะเป็นวันหยุดสุดสัปดาห์เลยประทับตราให้ #b2 อัน#k เลยนะ!",
                                        ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                            } else {
                                self.sayOk("ช่องเก็บของไม่พอ! กรุณาทำช่องว่างแล้วลองใหม่อีกครั้ง",
                                        ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                            }
                        } else {
                            if (target.exchange(gc.getItemID(), gc.getItemQty()) > 0) {
                                getPlayer().updateOneInfo(1234699, "complete", "1");
                                getPlayer().updateOneInfo(1234699, "day",
                                        "" + (getPlayer().getOneInfoQuestInteger(1234699, "day") + 2));
                                PacketEncoder p = new PacketEncoder();
                                p.writeShort(SendPacketOpcode.SHOW_STATUS_INFO.getValue());
                                p.write(14);
                                p.writeInt(253);
                                String qex = "complete=1;day=" + getPlayer().getOneInfo(1234699, "day") + ";"
                                        + "passCount=0;bMaxDay=91;lastDate="
                                        + getPlayer().getOneInfo(1234699, "lastDate") + ";cMaxDay=91";
                                p.writeMapleAsciiString(qex);
                                getPlayer().send(p.getPacket());
                                self.sayOk("เช็คชื่อเรียบร้อย! สัปดาห์말이라서 도장을 #b2개#k 찍어 줬어!",
                                        ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                            } else {
                                self.sayOk("ช่องเก็บของไม่พอ! กรุณาทำช่องว่างแล้วลองใหม่อีกครั้ง",
                                        ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
                            }
                        }
                    }
                }
            }
        }
    }

    public void goldenchariotWIG() {
        self.say(
                "#e#b<Fairy Bros' Golden Chariot>#k#n\r\n\r\n\r\n#bFairy Bros' Golden Chariot#k คือกิจกรรมที่ให้คุณเช็คชื่อทุกวันเพื่อรับของรางวัลมากมาย!",
                ScriptMessageFlag.NpcReplacedByNpc);
        self.say(
                "#e#b<Fairy Bros' Golden Chariot>#k#n\r\n\r\n\r\nในระยะเวลากิจกรรม สามารถกดปุ่มแจ้งเตือนกิจกรรมในเกมเพื่อเปิดกระดานเช็คชื่อ #b#e<Fairy Bros' Golden Chariot>#k#n ได้เลย!",
                ScriptMessageFlag.NpcReplacedByNpc);
        self.say(
                "#e#b<Fairy Bros' Golden Chariot>#k#n\r\n\r\n\r\nในระยะเวลากิจกรรม เมื่อสะสมเวลาออนไลน์ครบ #b60 นาที#k ในแต่ละวัน จะสามารถประทับตราเช็คชื่อได้!",
                ScriptMessageFlag.NpcReplacedByNpc);
        self.say(
                "#e#b<Fairy Bros' Golden Chariot>#k#n\r\n\r\n\r\nและที่สำคัญ #bในวันหยุดสุดสัปดาห์จะได้รับตราประทับทีเดียว 2 อัน#k อย่าพลาดเชียวนะ!",
                ScriptMessageFlag.NpcReplacedByNpc);
        // self.say("#e#b<Fairy Bros' Golden Chariot>#k#n\r\n\r\n\r\nRewards based on
        // attendance days are as follows!", ScriptMessageFlag.NpcReplacedByNpc);
        // self.say("#e<9 Attendances Completed>\r\n#b#i2633063:#
        // #t2633063:##k#n\r\n\r\n\r\n#e<18 Attendances Completed>\r\n#b#i2633064:#
        // #t2633064:##k#n\r\n\r\n\r\n#e<27 Attendances Completed>\r\n#b#i2631708:#
        // #t2631708:##k#n\r\n\r\n\r\n#e<36 Attendances Completed>\r\n#b#i2633065:#
        // #t2633065:##k#n\r\n\r\n\r\n#e<45 Attendances Completed>\r\n#b#i2633066:#
        // #t2633066:##k#n\r\n\r\n\r\n#e<54 Attendances Completed>\r\n#b#i2633067:#
        // #t2633067:##k#n\r\n\r\n\r\n#e<63 Attendances Completed>\r\n#b#i2631139:#
        // #t2631139:##k#n\r\n\r\n\r\n#e<72 Attendances Completed>\r\n#b#i2633071:#
        // #t2633071:##k#n\r\n\r\n\r\n#e<81 Attendances Completed>\r\n#b#i2633069:#
        // #t2633069:##k#n\r\n\r\n\r\n#e<90 Attendances Completed>\r\n#b#i2633070:#
        // #t2633070:##k#n\r\n\r\n\r\n#e<99 Attendances Completed>\r\n#b#i2633068:#
        // #t2633068:##k#n\r\n\r\n\r\n#e<108 Attendances Completed>\r\n#b#i2633072:#
        // #t2633072:##k#n\r\n\r\n\r\n#e<117 Attendances Completed>\r\n#b#i2633073:#
        // #t2633073:##k#n\r\n\r\n\r\n#e<126 Attendances Completed>\r\n#b#i2631717:#
        // #t2631717:##k#n", ScriptMessageFlag.NpcReplacedByNpc);
        // self.say("#e#b<Fairy Bros' Golden Chariot>#k#n\r\n\r\n\r\nSpecial chance just
        // in case you missed counting attendance!\r\nLet me tell you about #b#e<Golden
        // Pass>#k#n!", ScriptMessageFlag.NpcReplacedByNpc);
        // self.say("#e#b<Fairy Bros' Golden Chariot>#k#n\r\n\r\n\r\nYou can use
        // #b#e<Golden Pass>#k#n on the attendance board with #b3000 Maple Points#k to
        // stamp a missied attendance.",
        // ScriptMessageFlag.NpcReplacedByNpc);
        // self.say("#e#b<Fairy Bros' Golden Chariot>#k#n\r\n\r\n\r\nHowever, note that
        // #bAvailable Golden Pass Usage#k is limited to the number of #rmissed
        // attendance days as of yesterday#k!",
        // ScriptMessageFlag.NpcReplacedByNpc);
        // self.say("#e#b<Fairy Bros' Golden Chariot>#k#n\r\n\r\n\r\nDon't forget that
        // Golden Pass stamps #rone stamp#k regardless of the day used!",
        // ScriptMessageFlag.NpcReplacedByNpc);
        self.say(
                "#e#b<Fairy Bros' Golden Chariot>#k#n\r\n\r\n\r\nจำไว้ว่า #eการสะสมเวลาออนไลน์#n และ #eประวัติการรับรางวัล#n จะนับรวม #rต่อ Maple ID#k และ #rตัวละครเลเวล 101 ขึ้นไป#k เท่านั้นที่จะสะสมเวลาออนไลน์ได้!",
                ScriptMessageFlag.NpcReplacedByNpc);
        self.say(
                "#e#b<Fairy Bros' Golden Chariot>#k#n\r\n\r\n\r\nและถ้าทำกิจกรรมอื่นในช่วงที่วันเปลี่ยน เวลาออนไลน์อาจจะไม่สะสมตามปกติ ดังนั้น #eหลังจากเปลี่ยนวันแล้ว ให้เปิดกระดานเช็คชื่อเพื่อตรวจสอบว่าเวลาออนไลน์สะสมหรือไม่#n ด้วยนะ!",
                ScriptMessageFlag.NpcReplacedByNpc);
        self.say(
                "#e#b<Fairy Bros' Golden Chariot>#k#n\r\n\r\n\r\nสำหรับรายละเอียดเพิ่มเติม สามารถดูได้ที่เว็บไซต์หลัก!",
                ScriptMessageFlag.NpcReplacedByNpc);
        // self.say("#e#b<Fairy Bros' Golden Chariot>#k#n\r\n\r\n\r\n#e※ Event
        // Period#n\r\n - Until 2021\r\n#rJune 16th (Wed) 11:59 PM#k#k",
        // ScriptMessageFlag.NpcReplacedByNpc);
    }

    public void goldenchariotPass() {
        // self.sayOk("หากต้องการใช้ #bGolden Pass#k ต้องใช้ #r3,000 Maple Points#k!",
        // ScriptMessageFlag.NpcReplacedByNpc);
        self.sayOk("#fs11#ตอนนี้ไม่สามารถใช้งานฟังก์ชัน Pass ได้", ScriptMessageFlag.NpcReplacedByNpc);
    }
}
