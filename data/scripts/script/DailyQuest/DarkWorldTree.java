package script.DailyQuest;

import network.models.FontColorType;
import network.models.FontType;
import objects.quest.MapleQuest;
import objects.quest.MapleQuestStatus;
import objects.utils.Randomizer;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class DarkWorldTree extends ScriptEngineNPC {

    @SuppressWarnings("deprecation")
    public void q39002s() {
        // Selected Quest
        List<Integer> quests = new ArrayList<>();
        int[] questArray = { 39003, 39004, 39005, 39006, 39007, 39008, 39009, 39010, 39011, 39012 };
        MapleQuestStatus quest = getPlayer().getQuestIfNullAdd(MapleQuest.getInstance(39002));
        if (quest.getCustomData() == null) {
            quest.setCustomData("0-0");
        }
        if (quest.getCustomData().split("-")[0].equals("0")) {
            // Need to select waiting quest
            while (quests.size() < 5) {
                int selectedQuest = questArray[Randomizer.nextInt(questArray.length)];
                while (quests.contains(selectedQuest)) {
                    selectedQuest = questArray[Randomizer.nextInt(questArray.length)];
                }
                quests.add(selectedQuest);
            }
        } else {
            for (String s : quest.getCustomData().split("-")) {
                Integer q = Integer.parseInt(s);
                quests.add(q);
                if (quests.size() == 5)
                    break;
            }
        }
        LocalDate nextMonday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String resetDay = nextMonday.format(formatter);
        StringBuilder customData = new StringBuilder();
        customData.append(quests.get(0))
                .append("-")
                .append(quests.get(1))
                .append("-")
                .append(quests.get(2))
                .append("-")
                .append(quests.get(3))
                .append("-")
                .append(quests.get(4))
                .append("-")
                .append("n-n-n-n-n-")
                .append(resetDay);
        quest.setCustomData(customData.toString());
        StringBuilder askText = new StringBuilder();
        askText.append("โอ #bนักรบผู้แข็งแกร่งที่สุด#k ในโลกนี้ งานที่เจ้าต้องช่วยในสัปดาห์นี้มีดังต่อไปนี้\r\n\r\n");
        for (Integer q : quests) {
            askText.append("#b#e#y")
                    .append(q)
                    .append("##k#n\r\n");
        }
        askText.append(
                "\r\n#eจะเริ่มช่วยชำระล้าง World Tree ที่แปดเปื้อนเลยไหม?#n\r\n(หากไม่ถูกใจ สามารถกดปุ่มเปลี่ยนเพื่อเลือกภารกิจอื่นได้)");
        if (1 == self.askYesNo(askText.toString(), ScriptMessageFlag.Change)) {
            askText = new StringBuilder();
            askText.append(
                    "หากเจ้าไม่ถูกใจภารกิจในรายการ ก็สามารถลองหาภารกิจอื่นดูได้ จงเลือกภารกิจที่อยากเปลี่ยนเถิด\r\n\r\n");
            int index = 0;
            for (Integer q : quests) {
                askText.append("#b#e")
                        .append("#L")
                        .append(index)
                        .append("#")
                        .append(" #y")
                        .append(q)
                        .append("##l#k#n\r\n");
                index++;
            }
            askText.append("#L5# #r#eไม่มีภารกิจที่อยากเปลี่ยนแล้ว#k#n#l");
            int selection = self.askMenu(askText.toString());
            List<Integer> changeQuest = new ArrayList<>();
            while (selection != 5) {
                if (!changeQuest.contains(selection))
                    changeQuest.add(selection);
                askText = new StringBuilder();
                askText.append(
                        "หากเจ้าไม่ถูกใจภารกิจในรายการ ก็สามารถลองหาภารกิจอื่นดูได้ จงเลือกภารกิจที่อยากเปลี่ยนเถิด\r\n\r\n");
                for (int i = 0; i < 5; i++) {
                    if (changeQuest.contains(i)) {
                        askText.append("#e#L" + i + "# #y" + quests.get(i) + "##l#k#n\r\n");
                    } else {
                        askText.append("#b#e#L" + i + "# #y" + quests.get(i) + "##l#k#n\r\n");
                    }
                }
                askText.append("#L5# #r#eไม่มีภารกิจที่อยากเปลี่ยนแล้ว#k#n#l");
                if (changeQuest.size() == 5)
                    break;
                if (getSc().isStop())
                    break;
                selection = self.askMenu(askText.toString());
            }
            String scriptText = "หาภารกิจใหม่แทนภารกิจเดิมได้แล้ว\r\n\r\n";
            List<Integer> tempQuests = quests;
            for (Integer c : changeQuest) {
                int selectedQuest = questArray[Randomizer.nextInt(questArray.length)];
                while (tempQuests.contains(selectedQuest)) {
                    selectedQuest = questArray[Randomizer.nextInt(questArray.length)];
                }
                quests.remove((int) c);
                quests.add(c, selectedQuest);
            }
            for (int i = 0; i < quests.size(); i++) {
                scriptText += "#b#e" + " #y" + quests.get(i) + "##l#k#n\r\n";
            }
            for (Integer q : quests) {
                MapleQuest.getInstance(q).forceStart(getPlayer(), getNpc().getId(), "");
            }
            getQuest().forceStart(getPlayer(), getNpc().getId(), quest.getCustomData());
            getQuest().forceComplete(getPlayer(), getNpc().getId());
            getPlayer().getQuestIfNullAdd(MapleQuest.getInstance(39002))
                    .setCustomData("0-0-0-0-0-n-n-n-n-n-" + resetDay);
            self.say(scriptText);
        } else {
            getQuest().forceStart(getPlayer(), getNpc().getId(), quest.getCustomData());
            getQuest().forceComplete(getPlayer(), getNpc().getId());
            getPlayer().getQuestIfNullAdd(MapleQuest.getInstance(39002))
                    .setCustomData("0-0-0-0-0-n-n-n-n-n-" + resetDay);
            for (Integer q : quests) {
                MapleQuest.getInstance(q).forceStart(getPlayer(), getNpc().getId(), "");
            }
            self.sayOk(
                    "เมื่อทำภารกิจเสร็จทั้งหมดแล้ว ให้กลับมาหาข้าเพื่อจบงาน \r\nภารกิจทั้งหมดมีผลถึง #e#rเที่ยงคืนวันอาทิตย์#k#n เท่านั้น หากจะรับรางวัลก็จงกลับมาก่อนเวลานั้น");
        }
    }

    @SuppressWarnings("deprecation")
    public void q15708s() {
        self.say(
                "หากเจ้าช่วย #bชำระล้าง World Tree 5 ครั้งขึ้นไป#k ภายในสัปดาห์นี้ ข้าจะมอบ #rของตอบแทน#k #i4001868:# #b#t4001868##k เพิ่มให้ เพื่อเป็นการยอมรับในความสามารถของเจ้า",
                ScriptMessageFlag.NpcReplacedByNpc);
        if (self.askYesNo(
                "เจ้าจะช่วยหยุดยั้งการกระทำอันป่าเถื่อนของเหล่าลูกน้องข้า และช่วยยับยั้งการแปดเปื้อนของ World Tree หรือไม่?",
                ScriptMessageFlag.NpcReplacedByNpc) == 1) {
            // Resets on Monday.
            LocalDate nextMonday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String resetDay = nextMonday.format(formatter);
            getQuest().forceStart(getPlayer(), getNpc().getId(), resetDay);
            self.say("ถ้าเช่นนั้นก็ฝากด้วยนะ", ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
        } else {
            self.say("ดูเหมือนข้าจะมองคนผิดไปสินะ...\r\nหากเปลี่ยนใจเมื่อไหร่ก็กลับมาบอกข้าได้",
                    ScriptMessageFlag.NpcReplacedByNpc);
        }
    }

    public void q15708e() {
        self.say("เจ้าปฏิบัติภารกิจได้ยอดเยี่ยมกว่าที่ข้าคาดไว้มาก", ScriptMessageFlag.NpcReplacedByNpc);
        self.say("อาจจะเป็นของเล็กน้อยเกินกว่าจะแทนคำขอบคุณได้ แต่ข้าจะมอบของขวัญ #rตอบแทน#k ให้อีกนิดหน่อยนะ",
                ScriptMessageFlag.NpcReplacedByNpc);

        if (target.exchange(4001868, (2 * 2)) > 0) {
            getQuest().forceComplete(getPlayer(), getNpc().getId());
            self.say(
                    "รับนี่ไปสิ #i4001868:# #b#t4001868##k ด้วยความช่วยเหลือของเจ้า เหล่าลูกน้องของข้าจึงหลุดพ้นจากความชั่วร้ายได้ ขอบใจจริงๆ...",
                    ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
        } else {
            self.say(
                    "ดูเหมือนช่องเก็บของเจ้าจะเต็มนะ ไปเคลียร์ช่องเก็บของช่อง 'อื่นๆ' ให้ว่างอย่างน้อย 1 ช่อง แล้วค่อยกลับมาคุยใหม่นะ");
        }
    }

    public void q39003e() {
        slimeQuestEnd();
    }

    public void q39004e() {
        slimeQuestEnd();
    }

    public void q39005e() {
        itemQuestEnd(4034875, 40);
    }

    public void q39006e() {
        itemQuestEnd(4034876, 40);
    }

    public void q39007e() {
        normalQuestEnd();
    }

    public void q39008e() {
        normalQuestEnd();
    }

    public void q39009e() {
        itemQuestEnd(4034877, 20);
    }

    public void q39010e() {
        itemQuestEnd(4034878, 20);
    }

    public void q39011e() {
        normalQuestEnd();
    }

    public void q39012e() {
        normalQuestEnd();
    }

    int eventRate = 2;

    public void slimeQuestEnd() {
        self.say(
                "เจ้ากำจัด #rCorrupted Sap#k และกลับมาแล้วสินะ...\r\nการกำจัดพวกที่ได้รับผลกระทบจาก #rพลังของดาบมาร#k คงไม่ใช่เรื่องง่ายแน่ๆ...",
                ScriptMessageFlag.NpcReplacedByNpc);
        if (target.exchange(4001868, (2 * eventRate)) > 0) {
            int cq = getPlayer().getOneInfoQuestInteger(15708, "cq");
            cq = cq + 1;
            getPlayer().updateOneInfo(15708, "cq", String.valueOf(cq));
            getPlayer().gainExp(40000000, true, true, false);
            bigScriptProgressMessage("EXP 40000000 ได้รับแล้ว", FontType.NanumGothicBold, FontColorType.Green);
            getQuest().forceComplete(getPlayer(), getNpc().getId());
            self.sayOk(
                    "อะ นี่คือ #bของขวัญที่สัญญาไว้#k\r\n#i4001868:# #b#t4001868##k\r\nขอบใจที่ช่วยเก็บกวาด #rเมล็ดพันธุ์แห่งความชั่วร้าย#k ที่เผ่าพันธุ์ของพวกเราหว่านไว้..",
                    ScriptMessageFlag.NpcReplacedByNpc);
        } else {
            self.sayOk(
                    "ดูเหมือนช่องเก็บของเจ้าจะเต็มนะ ไปเคลียร์ช่องเก็บของช่อง 'อื่นๆ' ให้ว่างอย่างน้อย 1 ช่อง แล้วค่อยกลับมาคุยใหม่นะ");
        }
    }

    public void normalQuestEnd() {
        self.say("เพราะเจ้าช่วย วิญญาณลูกน้องของข้าจึงได้รับ #bความสงบสุขชั่วนิรันดร์#k เสียที...",
                ScriptMessageFlag.NpcReplacedByNpc);
        if (target.exchange(4001868, (2 * eventRate)) > 0) {
            int cq = getPlayer().getOneInfoQuestInteger(15708, "cq");
            cq = cq + 1;
            getPlayer().updateOneInfo(15708, "cq", String.valueOf(cq));
            getPlayer().gainExp(40000000, true, true, false);
            bigScriptProgressMessage("EXP 40000000 ได้รับแล้ว", FontType.NanumGothicBold, FontColorType.Green);
            getQuest().forceComplete(getPlayer(), getNpc().getId());
            self.sayOk(
                    "อะ นี่คือ #bของขวัญที่สัญญาไว้#k\r\n#i4001868:# #b#t4001868##k\r\nขอบใจที่ช่วยเก็บกวาด #rเมล็ดพันธุ์แห่งความชั่วร้าย#k ที่เผ่าพันธุ์ของพวกเราหว่านไว้..",
                    ScriptMessageFlag.NpcReplacedByNpc);
        } else {
            self.sayOk(
                    "ดูเหมือนช่องเก็บของเจ้าจะเต็มนะ ไปเคลียร์ช่องเก็บของช่อง 'อื่นๆ' ให้ว่างอย่างน้อย 1 ช่อง แล้วค่อยกลับมาคุยใหม่นะ");
        }
    }

    public void itemQuestEnd(int needItem, int needItemQty) {
        self.say("เพราะเจ้าช่วย วิญญาณลูกน้องของข้าจึงได้รับ #bความสงบสุขชั่วนิรันดร์#k เสียที...",
                ScriptMessageFlag.NpcReplacedByNpc);
        if (target.exchange(needItem, -needItemQty, 4001868, (2 * eventRate)) > 0) {
            if (getPlayer().getItemQuantity(needItem, false) > 0) {
                target.exchange(needItem, -getPlayer().getItemQuantity(needItem, false));
            }
            int cq = getPlayer().getOneInfoQuestInteger(15708, "cq");
            cq = cq + 1;
            getPlayer().updateOneInfo(15708, "cq", String.valueOf(cq));
            getPlayer().gainExp(40000000, true, true, false);
            bigScriptProgressMessage("EXP 40000000 ได้รับแล้ว", FontType.NanumGothicBold, FontColorType.Green);
            getQuest().forceComplete(getPlayer(), getNpc().getId());
            self.sayOk(
                    "อะ นี่คือ #bของขวัญที่สัญญาไว้#k\r\n#i4001868:# #b#t4001868##k\r\nขอบใจที่ช่วยเก็บกวาด #rเมล็ดพันธุ์แห่งความชั่วร้าย#k ที่เผ่าพันธุ์ของพวกเราหว่านไว้..",
                    ScriptMessageFlag.NpcReplacedByNpc);
        } else {
            self.sayOk(
                    "ดูเหมือนช่องเก็บของเจ้าจะเต็มนะ ไปเคลียร์ช่องเก็บของช่อง 'อื่นๆ' ให้ว่างอย่างน้อย 1 ช่อง แล้วค่อยกลับมาคุยใหม่นะ");
        }
    }
}
