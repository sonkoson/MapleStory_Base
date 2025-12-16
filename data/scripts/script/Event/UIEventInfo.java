package script.Event;

import network.SendPacketOpcode;
import network.models.*;
import network.encode.PacketEncoder;
import objects.utils.*;
import scripting.newscripting.ScriptEngineNPC;
import java.time.LocalDate;
import java.util.*;

public class UIEventInfo extends ScriptEngineNPC {

    public static String[] customData = { "", "", "", "", "count=0", "count=0", "RunAct=0", "suddenMK=0" };
    public int objectId = 100208;
    public int windowId = 1254;
    public int maxDate = 135;
    public int maxTime = 3600;
    public int questEx1 = 252;
    public int questEx2 = 253;
    public int questEx3 = 254;
    public String info = "chariotInfo";
    public String pass = "chariotPass";
    public String attend = "chariotAttend";
    public LocalDate startDate = LocalDate.of(2021, 12, 1);
    public LocalDate finishDate = LocalDate.of(2022, 5, 31);

    public List<Pair<Integer, Integer>> itemList = List.of(new Pair<>(1003142, 9), new Pair<>(2000005, 18));

    public void q100906s() {
        if (getPlayer().isGM()) {
            PacketEncoder o = new PacketEncoder();
            o.writeShort(SendPacketOpcode.UI_EVENT_INFO.getValue());
            o.writeInt(objectId);
            o.write(true); // 348++
            o.write(false); // 348++
            // sub_8F1C50
            o.writeLong(PacketHelper.getTime(finishDate.toEpochDay()));
            o.writeLong(PacketHelper.getTime(startDate.toEpochDay()));
            o.writeInt(maxDate);
            o.writeInt(objectId);
            o.writeInt(0);
            o.write(0);
            o.writeInt(0);
            o.writeInt(questEx1); // 351++
            o.writeInt(questEx2); // 351++
            o.writeInt(questEx3); // 351++
            o.writeInt(maxTime); // 351++
            o.writeMapleAsciiString(info);
            o.writeMapleAsciiString("");
            o.writeMapleAsciiString(pass); // 351++
            o.writeMapleAsciiString(attend); // 351++
            o.writeInt(0); // size1

            o.writeInt(itemList.size()); // size2

            for (Pair<Integer, Integer> item : itemList) {
                o.writeInt(item.left); // itemID
                o.writeInt(item.right); // dateID
                o.writeInt(0);
                o.write(0); // // 351++

                o.writeInt(0);
                o.write(0);

                o.writeInt(0);
                o.writeInt(0);
                o.write(0);
            }

            o.writeInt(0); // size3

            o.writeInt(windowId);

            getPlayer().send(o.getPacket());

            o = new PacketEncoder();
            o.writeShort(SendPacketOpcode.UI_EVENT_SET.getValue());
            o.writeInt(objectId);
            o.writeInt(windowId);

            getPlayer().send(o.getPacket());
        }
    }

    public void weekHQuest() {
        self.say(
                "#b#eเอาล่ะ ในที่สุด! <ภารกิจลับ Haste> ก็เปิดแล้ว!#n#k\r\n\r\nจนถึง #bวันที่ 5 ธันวาคม 2021 เวลา 23:59 น.#k\r\nต้องล่ามอนสเตอร์เลเวลใกล้เคียง #b#e44,444 ตัว#n#k... ไม่สิ.. #b#e88,888 ตัว#n#k ถึงจะสำเร็จ!");
        self.say(
                "ใน #b#e<กล่องภารกิจลับ Haste>#n#k..\r\nสามารถรับ #b#e#i2631097:# #t2631097:#,\r\n#i1114317:# #t1114317:##n#k ได้ พยายามเข้านะ!");
    }

    public void useHasteBooster() {
        if (1 == self.askYesNo(
                "#r#eHaste Booster#n#k จะใช้ไหม?\r\n#b#eมอนสเตอร์จะเกิดเพิ่มขึ้นเป็นเวลา 100 วินาทีเชียวนะ!#n#k\r\nเร่งความเร็วในการล่าได้อย่างแท้จริง!\r\n\r\n#e<กรณีที่ไม่สามารถใช้ได้>#n\r\n 1. แผนที่ที่ไม่มีมอนสเตอร์เลเวลใกล้เคียง หรือในหมู่บ้าน\r\n 2. เมื่อ Elite Boss ปรากฏตัว\r\n 3. เมื่อตนเองกำลังใช้ Haste Booster อยู่\r\n 4. เมื่อผู้เล่นอื่นกำลังใช้ Haste Booster อยู่")) {
            getPlayer().getMap().startHasteBooster(getPlayer());
        }
    }
}
