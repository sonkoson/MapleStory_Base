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

public class Haven extends ScriptEngineNPC {

    @SuppressWarnings("deprecation")
    public void q39165s() {
        // Selected Quest
        List<Integer> quests = new ArrayList<>();
        int[] questArray = { 39101, 39102, 39103, 39104, 39105, 39106, 39107, 39108, 39111, 39112, 39113, 39114, 39115,
                39116, 39117, 39118, 39119, 39121, 39122, 39123, 39124, 39125/* , 39126 */, 39127, 39131, 39132, 39133,
                39134, 39135, 39136, 39141, 39142, 39143, 39144, 39145, 39146, 39147, 39148, 39149, 39150, 39151, 39152,
                39153, 39154, 39155 };
        MapleQuestStatus quest = getPlayer().getQuestIfNullAdd(MapleQuest.getInstance(39165));
        if (quest.getCustomData() == null) {
            quest.setCustomData("0-0");
        }
        if (quest.getCustomData().split("-")[0].equals("0")) {
            // Need to select waiting quest
            while (quests.size() < 4) {
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
                if (quests.size() == 4)
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
                .append("0-")
                .append("n-n-n-n-n-")
                .append(resetDay);
        quest.setCustomData(customData.toString());
        StringBuilder askText = new StringBuilder();
        askText.append("งานที่จะวานเจ้าในสัปดาห์นี้มีดังต่อไปนี้นะ\r\n\r\n");
        for (Integer q : quests) {
            askText.append("#b#e#y")
                    .append(q)
                    .append("##k#n\r\n");
        }
        askText.append("\r\n#eจะเริ่มทำเลยไหม?#n\r\n(หากไม่ถูกใจ สามารถกดปุ่มเปลี่ยนเพื่อเลือกภารกิจอื่นได้นะ)");
        if (1 == self.askYesNo(askText.toString(), ScriptMessageFlag.Change)) {
            askText = new StringBuilder();
            askText.append(
                    "ดูเหมือนจะไม่ถูกใจภารกิจในรายการสินะ? ถ้าอย่างนั้นลองหาภารกิจอื่นดูก็ได้ เลือกภารกิจที่อยากเปลี่ยนมาสิ\r\n\r\n");
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
            askText.append("#L4# #r#eไม่มีภารกิจที่อยากเปลี่ยนแล้ว#k#n#l");
            int selection = self.askMenu(askText.toString());
            List<Integer> changeQuest = new ArrayList<>();
            while (selection != 4) {
                if (!changeQuest.contains(selection))
                    changeQuest.add(selection);
                askText = new StringBuilder();
                askText.append(
                        "ดูเหมือนจะไม่ถูกใจภารกิจในรายการสินะ? ถ้าอย่างนั้นลองหาภารกิจอื่นดูก็ได้ เลือกภารกิจที่อยากเปลี่ยนมาสิ\r\n\r\n");
                for (int i = 0; i < 4; i++) {
                    if (changeQuest.contains(i)) {
                        askText.append("#e#L" + i + "# #y" + quests.get(i) + "##l#k#n\r\n");
                    } else {
                        askText.append("#b#e#L" + i + "# #y" + quests.get(i) + "##l#k#n\r\n");
                    }
                }
                askText.append("#L4# #r#eไม่มีภารกิจที่อยากเปลี่ยนแล้ว#k#n#l");
                if (changeQuest.size() == 4)
                    break;
                if (getSc().isStop())
                    break;
                selection = self.askMenu(askText.toString());
            }
            String scriptText = "หาภารกิจใหม่มาให้แล้ว\r\nงานที่จะวานมีดังนี้นะ\r\n\r\n";
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
            getPlayer().getQuestIfNullAdd(MapleQuest.getInstance(39165))
                    .setCustomData("0-0-0-0-0-n-n-n-n-n-" + resetDay);
            self.say(scriptText);
        } else {
            getQuest().forceStart(getPlayer(), getNpc().getId(), quest.getCustomData());
            getQuest().forceComplete(getPlayer(), getNpc().getId());
            getPlayer().getQuestIfNullAdd(MapleQuest.getInstance(39165))
                    .setCustomData("0-0-0-0-0-n-n-n-n-n-" + resetDay);
            for (Integer q : quests) {
                MapleQuest.getInstance(q).forceStart(getPlayer(), getNpc().getId(), "");
            }
            self.sayOk("อย่าลืมว่าต้องทำภารกิจให้เสร็จภายใน #e#rเที่ยงคืนวันอาทิตย์#k#n นะ ถ้าอย่างนั้นก็ขอให้โชคดี");
        }
    }

    @SuppressWarnings("deprecation")
    public void q39160s() {
        // Resets on Monday.
        LocalDate nextMonday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String resetDay = nextMonday.format(formatter);
        getQuest().forceStart(getPlayer(), getNpc().getId(), resetDay);
        self.say(
                "เจ้าตัวนุ่ม มาอีกแล้วสินะ\r\nข้าไม่รู้จะขอบคุณเจ้ายังไงดีที่ช่วยมาตลอด\r\n#bหลังจากรับรางวัลครั้งก่อนแล้ว ถ้าทำภารกิจครบ 4 ครั้ง ก็มาบอกข้าได้เลย ฮ่าๆๆ#k",
                ScriptMessageFlag.NoEsc);
    }

    public void q39160e() {
        if (target.exchange(4001842, (7 * 2)) > 0) {
            getQuest().forceComplete(getPlayer(), getNpc().getId());
            int level = getPlayer().getLevel();
            // 39164 - [Weekly Quest] Black Heaven Interior Special Request: Defeat Enemy
            // Robots
            if (level >= 216) {
                getPlayer().updateInfoQuest(39164, "start=1");
            } else if (level >= 211) {
                getPlayer().updateInfoQuest(39163, "start=1");
            } else if (level >= 206) {
                getPlayer().updateInfoQuest(39162, "start=1");
            } else {
                getPlayer().updateInfoQuest(39161, "start=1");
            }
            self.say(
                    "เจ้าตัวนุ่ม เพราะเจ้าช่วยมาตลอด การลาดตระเวนเลยสะดวกขึ้นเยอะเลย\r\nเอานี่... นี่คือรางวัลสำหรับเรื่องนั้น\r\n#i4001842# #b#t4001842##k\r\n\r\nต่อไปก็เหมือนเดิมนะ ถ้าทำภารกิจรายสัปดาห์ครบ 4 ครั้ง ก็มาบอกข้าได้เลย ฮ่าๆๆ");
        } else {
            self.say(
                    "เจ้าตัวนุ่ม ดูเหมือนช่องเก็บของเจ้าจะเต็มนะ ไปเคลียร์ช่องเก็บของช่อง 'อื่นๆ' ให้ว่างอย่างน้อย 1 ช่อง แล้วค่อยมาคุยใหม่นะ");
        }
    }

    public void q39161s() {
        int mobId = 8250003;
        int mobCount = 200;
        bonusQuestStart(mobId, mobCount);
    }

    public void q39162s() {
        int mobId = 8250008;
        int mobCount = 100;
        bonusQuestStart(mobId, mobCount);
    }

    public void q39163s() {
        int mobId = 8250013;
        int mobCount = 200;
        bonusQuestStart(mobId, mobCount);
    }

    public void q39164s() {
        int mobId = 8250017;
        int mobCount = 200;
        bonusQuestStart(mobId, mobCount);
    }

    public void q39161e() {
        int mobId = 8250003;
        bonusQuestEnd(mobId);
    }

    public void q39162e() {
        int mobId = 8250008;
        bonusQuestEnd(mobId);
    }

    public void q39163e() {
        int mobId = 8250013;
        bonusQuestEnd(mobId);
    }

    public void q39164e() {
        int mobId = 8250017;
        bonusQuestEnd(mobId);
    }

    public void bonusQuestStart(int mobId, int mobCount) {
        self.say(
                "เจ้าตัวนุ่ม มีคำขอด่วนเข้ามาน่ะ\r\nเป็นคำขอ #bกำจัดหุ่นยนต์ศัตรู#k\r\nช่วยไปกำจัดหุ่นยนต์น่ากลัวพวกนั้นให้หน่อยนะ โชคดีที่จำนวนดูเหมือนจะไม่เยอะเท่าไหร่");
        if (self.askAccept("หุ่นยนต์ที่ต้องกำจัดมีดังนี้นะ\r\n\r\n#r[คำขอ : กำจัดหุ่นยนต์ศัตรู]#k\r\nเป้าหมาย : #b#o"
                + mobId + "# " + mobCount + "#k\r\n\r\nว่าไง? จะรับคำขอไหม?") == 1) {
            getQuest().forceStart(getPlayer(), getNpc().getId(), "");
            self.say(
                    "ขอบใจนะ อยู่ๆ หุ่นยนต์น่ากลัวพวกนั้นก็เพิ่มจำนวนขึ้นมา จนลาดตระเวนลำบากเลย...\r\nถ้ากำจัดหมดแล้วก็กลับมาที่นี่นะ",
                    ScriptMessageFlag.NoEsc);
        }
    }

    public void bonusQuestEnd(int mobId) {
        if (target.exchange(4001842, (5 * 2)) > 0) {
            getPlayer().updateInfoQuest(getQuest().getId(), "start=0");
            getQuest().forceComplete(getPlayer(), getNpc().getId());
            self.say("เจ้าตัวนุ่ม ขอบใจที่ช่วยกำจัด #o" + mobId
                    + "# ให้นะ\r\nเดี๋ยวการลาดตระเวนคงง่ายขึ้นหน่อย..\r\nเอานี่... รางวัลสำหรับเรื่องนั้น\r\n#i4001842# #b#t4001842##k\r\n\r\nต่อไปก็ฝากด้วยนะ",
                    ScriptMessageFlag.NoEsc);
        } else {
            self.sayOk(
                    "เจ้าตัวนุ่ม ดูเหมือนช่องเก็บของเจ้าจะเต็มนะ ไปเคลียร์ช่องเก็บของช่อง 'อื่นๆ' ให้ว่างอย่างน้อย 1 ช่อง แล้วค่อยมาคุยใหม่นะ");
        }
    }

    public void npc_2155000() {
        self.say("เจ้าตัวนุ่ม มีธุระอะไรกับข้าหรือ?");
    }

    public void q39101e() {
        itemQuest(4034286, 30);
    }

    public void q39102e() {
        itemQuest(4034281, 50);
    }

    public void q39103e() {
        itemQuest(4034282, 50);
    }

    public void q39104e() {
        itemQuest(4034283, 20);
    }

    public void q39105s() {
        NpcSpeechQuestStart(8250005, 200, 2155106);
    }

    public void q39106s() {
        NpcSpeechQuestStart(8250013, 300, 2155107);
    }

    public void q39107s() {
        NpcSpeechQuestStart(8250010, 300, 2155108);
    }

    public void q39108s() {
        NpcSpeechQuestStart(8250011, 200, 2155109);
    }

    public void q39105e() {
        NpcSpeechQuestEnd(8250005);
    }

    public void q39106e() {
        NpcSpeechQuestEnd(8250013);
    }

    public void q39107e() {
        NpcSpeechQuestEnd(8250010);
    }

    public void q39108e() {
        NpcSpeechQuestEnd(8250011);
    }

    public void q39111e() {
        NpcSpeechQuestEnd(8250003);
    }

    public void q39112e() {
        NpcSpeechQuestEnd(8250004);
    }

    public void q39113e() {
        NpcSpeechQuestEnd(8250006);
    }

    public void q39114s() {
        itemQuest(4034287, 20);
    }

    public void q39115s() {
        itemQuest(4034285, 30);
    }

    public void q39116e() {
        repairQuest(4034284, 50);
    }

    public void q39117e() {
        liberationQuest();
    }

    public void q39118e() {
        liberationQuest();
    }

    public void q39119e() {
        liberationQuest();
    }

    public void q39121e() {
        liberationQuest();
    }

    public void q39122e() {
        liberationQuest();
    }

    public void q39123e() {
        itemQuest(4034294, 50);
    }

    public void q39124e() {
        liberationQuest();
    }

    public void q39125e() {
        repairQuest(4034289, 10);
    }

    public void q39126s() {
        itemQuest(4034290, 2);
    }

    public void q39127s() {
        itemQuest(4034288, 10);
    }

    public void q39131e() {
        normalQuestEnd();
    }

    public void q39132e() {
        normalQuestEnd();
    }

    public void q39133e() {
        normalQuestEnd();
    }

    public void q39134e() {
        itemQuest(4034293, 2);
    }

    public void q39135s() {
        itemQuest(4034291, 50);
    }

    public void q39136s() {
        itemQuest(4034302, 2);
    }

    public void q39141e() {
        normalQuestEnd();
    }

    public void q39142e() {
        normalQuestEnd();
    }

    public void q39143e() {
        normalQuestEnd();
    }

    public void q39144e() {
        normalQuestEnd();
    }

    public void q39145e() {
        normalQuestEnd();
    }

    public void q39146e() {
        normalQuestEnd();
    }

    public void q39147e() {
        normalQuestEnd();
    }

    public void q39148e() {
        itemQuest(4034292, 30);
    }

    public void q39149e() {
        itemQuest(4034298, 50);
    }

    public void q39150e() {
        itemQuest(4034299, 15);
    }

    public void q39151e() {
        itemQuest(4034300, 30);
    }

    public void q39152e() {
        repairQuest(4034301, 2);
    }

    public void q39153s() {
        itemQuest(4034295, 50);
    }

    public void q39154s() {
        itemQuest(4034296, 50);
    }

    public void q39155s() {
        itemQuest(4034297, 20);
    }

    public void itemQuest(int itemId, int needQty) {
        self.say(
                "รออยู่เลย! กำลังจำเป็นต้องใช้ของพวกนั้นอยู่พอดี\r\nอืม... ครบตามที่ขอเลย #bงั้นข้าขอรับของไปทั้งหมดเลยนะ#k\r\nวันหน้าก็ฝากด้วยนะ เจ้าตัวนุ่ม",
                ScriptMessageFlag.NoEsc);
        if (target.exchange(itemId, -needQty) > 0) {
            if (getPlayer().getItemQuantity(itemId, false) > 0) {
                target.exchange(itemId, getPlayer().getItemQuantity(itemId, false));
            }
            int FC = getPlayer().getOneInfoQuestInteger(39100, "FC");
            FC = FC + 1;
            getPlayer().updateOneInfo(39100, "FC", String.valueOf(FC));
            getPlayer().gainExp(50000000, true, true, false);
            bigScriptProgressMessage("EXP 50000000 ได้รับแล้ว", FontType.NanumGothicBold, FontColorType.Green);
            getQuest().forceComplete(getPlayer(), getNpc().getId());
        }
    }

    public void repairQuest(int itemId, int needQty) {
        self.say(
                "หลักฐานชัดเจน ข้าขอเก็บวัสดุหลักฐานไปทั้งหมดนะ\r\nลำบากหน่อยนะ แต่ต้องขอบคุณเจ้าจริงๆ ที่ทำให้หอสังเกตการณ์ที่พังกลับมาใช้ได้อีกครั้ง\r\nเป็นงานที่ยากจริงๆ...\r\nวันหน้าก็ฝากด้วยนะ เจ้าตัวนุ่ม",
                ScriptMessageFlag.NoEsc);
        if (target.exchange(itemId, -needQty) > 0) {
            if (getPlayer().getItemQuantity(itemId, false) > 0) {
                target.exchange(itemId, getPlayer().getItemQuantity(itemId, false));
            }
            int FC = getPlayer().getOneInfoQuestInteger(39100, "FC");
            FC = FC + 1;
            getPlayer().updateOneInfo(39100, "FC", String.valueOf(FC));
            getPlayer().gainExp(50000000, true, true, false);
            bigScriptProgressMessage("EXP 50000000 ได้รับแล้ว", FontType.NanumGothicBold, FontColorType.Green);
            getQuest().forceComplete(getPlayer(), getNpc().getId());
        }
    }

    public void liberationQuest() {
        self.say(
                "ขอบใจที่ช่วยปลดปล่อยพวกหุ่นยนต์นะ\r\nมีหุ่นยนต์มากมายอยากจะขอบคุณเจ้า ข้าต้องคอยห้ามไว้แทบแย่ เพราะเป็นเจ้าถึงทำเรื่องแบบนี้ได้\r\nวันหน้าก็ฝากด้วยนะ เจ้าตัวนุ่ม",
                ScriptMessageFlag.NoEsc);
        getSc().flushSay();
        int FC = getPlayer().getOneInfoQuestInteger(39100, "FC");
        FC = FC + 1;
        getPlayer().updateOneInfo(39100, "FC", String.valueOf(FC));
        getPlayer().gainExp(50000000, true, true, false);
        bigScriptProgressMessage("EXP 50000000 ได้รับแล้ว", FontType.NanumGothicBold, FontColorType.Green);
        getQuest().forceComplete(getPlayer(), getNpc().getId());
    }

    public void NpcSpeechQuestStart(int mobId, int mobCount, int NpcSpeech) {
        getPlayer().updateInfoQuest(getQuest().getId(), "start=1;NpcSpeech=" + NpcSpeech + "1");
        self.say(
                "พอขอความช่วยเหลือไป เจ้าตัวนุ่ม เจ้าก็มาเลยสินะ!\r\nถ้าเป็นเจ้าต้องช่วยได้มากแน่ๆ\r\nต้องไปขอบคุณทางนั้นสักหน่อยแล้ว",
                ScriptMessageFlag.NoEsc);
        self.say(
                "เอาล่ะ งั้นมาคุยเรื่องงานกันดีกว่า\r\nแถวๆ นี้ #b#o" + mobId
                        + "##k จู่ๆ ก็เยอะขึ้นมาน่ะ\r\nจนออกไปลาดตระเวนไม่ได้เลย\r\nฝากกำจัด #b#o" + mobId + "# "
                        + mobCount + " ตัว#k ให้หน่อยนะ\r\nถ้ากำจัดหมดแล้วก็มาบอกข้าได้เลย ฝากด้วยนะ",
                ScriptMessageFlag.NoEsc);
    }

    public void NpcSpeechQuestEnd(int mobId) {
        self.say("ขอบใจที่ช่วยกำจัด #o" + mobId
                + "# ให้นะ\r\nเดี๋ยวการลาดตระเวนคงง่ายขึ้นหน่อย\r\nเป็นคำขอที่ไม่มีใครทำได้ แต่เจ้าทำได้ สุดยอดไปเลย!\r\nวันหน้าก็ฝากด้วยนะ เจ้าตัวนุ่ม",
                ScriptMessageFlag.NoEsc);
        getSc().flushSay();
        int FC = getPlayer().getOneInfoQuestInteger(39100, "FC");
        FC = FC + 1;
        getPlayer().updateOneInfo(39100, "FC", String.valueOf(FC));
        getPlayer().updateInfoQuest(getQuest().getId(), "");
        getPlayer().gainExp(50000000, true, true, false);
        bigScriptProgressMessage("EXP 50000000 ได้รับแล้ว", FontType.NanumGothicBold, FontColorType.Green);
        getQuest().forceComplete(getPlayer(), getNpc().getId());
    }

    public void normalQuestEnd() {
        self.say(
                "ได้รับความช่วยเหลือแล้วนะ\r\nข้าจะถ่ายทอดวีรกรรมของเจ้าให้เอง\r\nถ้าไม่มีเจ้าคงแย่แน่ๆ\r\nวันหน้าก็ฝากด้วยนะ เจ้าตัวนุ่ม",
                ScriptMessageFlag.NoEsc);
        getSc().flushSay();
        int FC = getPlayer().getOneInfoQuestInteger(39100, "FC");
        FC = FC + 1;
        getPlayer().updateOneInfo(39100, "FC", String.valueOf(FC));
        getPlayer().updateInfoQuest(getQuest().getId(), "");
        getPlayer().gainExp(50000000, true, true, false);
        bigScriptProgressMessage("EXP 50000000 ได้รับแล้ว", FontType.NanumGothicBold, FontColorType.Green);
        getQuest().forceComplete(getPlayer(), getNpc().getId());
    }
}
