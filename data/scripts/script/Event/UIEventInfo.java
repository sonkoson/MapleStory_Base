package script.Event;

import network.SendPacketOpcode;
import network.models.*;
import network.encode.PacketEncoder;
import objects.utils.*;
import scripting.newscripting.ScriptEngineNPC;
import java.time.LocalDate;
import java.util.*;

public class UIEventInfo extends ScriptEngineNPC {

    public static String[] customData = {"", "", "", "", "count=0", "count=0", "RunAct=0", "suddenMK=0"};
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
            //sub_8F1C50
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
                o.write(0); //  // 351++

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
        self.say("#b#e자, 드디어! <헤이스트 히든 ภารกิจ>#n#k 열렸어!\r\n\r\n#b2021ปี 12เดือน 5วัน 오후 11시 59นาที#k까지\r\n#b#eเลเวล 범위 มอนสเตอร์ 44,444마리#n#k... 아니라..#b#e88,888마리#n#k 사냥 된다구!");
        self.say("#b#e<헤이스트 히든 ภารกิจ 상자>#n#k에서는..\r\n#b#e#i2631097:# #t2631097:#,\r\n#i1114317:# #t1114317:##n#k 받을 수 있으니 힘을 내!");
    }

    public void useHasteBooster() {
        if (1 == self.askYesNo("#r#e헤이스트 부스터#n#k ใช้할꺼야?\r\n#b#e100วินาที동안 มอนสเตอร์ เพิ่ม 소환된다구!#n#k\r\n진정한 사냥 가속을 할 수 있지!\r\n\r\n#e<ใช้ 할 수 없는 경우>#n\r\n 1. เลเวล 범위 มอนสเตอร์ 없는 필드 또는 마을.\r\n 2. 엘리트 บอส 소환 된 경우.\r\n 3. ปัจจุบัน 자신이 헤이스트 부스터를 ใช้ 중인 경우.\r\n 4. ปัจจุบัน 다른 플레이어가 헤이스트 부스터를 ใช้ 중인 경우.")) {
            getPlayer().getMap().startHasteBooster(getPlayer());
        }
    }
}
