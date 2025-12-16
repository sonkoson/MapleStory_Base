package script.DailyQuest;

import network.SendPacketOpcode;
import network.encode.PacketEncoder;
import objects.quest.MapleQuest;
import objects.quest.MapleQuestStatus;
import scripting.ScriptMessageFlag;
import scripting.newscripting.Script;
import scripting.newscripting.ScriptEngineNPC;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ArcaneRiver extends ScriptEngineNPC {

    // Spiegelmann Arcane River Quick Pass
    @Script
    public void npc_3003146() {
        if (getPlayer().isQuestStarted(501551)) { // <Vanishing Journey> Step Up!
            if (getPlayer().getOneInfoQuestInteger(501551, "value") < 1) {
                getPlayer().updateOneInfo(501551, "value", "1");
            }
            if (getPlayer().getOneInfoQuestInteger(501524, "state") < 2) {
                getPlayer().updateOneInfo(501524, "state", "2");
            }
        }
        // getPlayer().send(HexTool.getByteArrayFromHexString("64 00 0D 8B 98 00 00 00
        // 00"));
        // getPlayer().send(HexTool.getByteArrayFromHexString("64 00 0D 8C 98 00 00 00
        // 00"));
        int level = getPlayer().getLevel();
        StringBuilder sb = new StringBuilder();
        int t0 = 5; // Vanishing Journey
        for (int i = 34164; i <= 34167; i++) {
            if (getPlayer().getQuestStatus(i) == 2) {
                t0--;
            }
        }
        sb.append("t0=").append(t0).append(";");
        int t1 = 0; // Chu Chu
        if (level >= 210) {
            t1 = 3;
        }
        for (int i = 34227; i <= 34228; i++) {
            if (getPlayer().getQuestStatus(i) == 2) {
                t1--;
            }
        }
        sb.append("t1=").append(t1).append(";");
        int t2 = 0; // Lachelein
        if (level >= 220) {
            t2 = 3;
        }
        for (int i = 34333; i <= 34334; i++) {
            if (getPlayer().getQuestStatus(i) == 2) {
                t2--;
            }
        }
        sb.append("t2=").append(t2).append(";");
        int t3 = 0; // Arcana
        if (level >= 225) {
            t3 = 3;
        }
        for (int i = 34491; i <= 34492; i++) {
            if (getPlayer().getQuestStatus(i) == 2) {
                t3--;
            }
        }
        sb.append("t3=").append(t3).append(";");
        int t4 = 0; // Morass
        if (level >= 230) {
            t4 = 3;
        }
        if (getPlayer().getLevel() >= 245) {
            t4--;
        }
        sb.append("t4=").append(t4).append(";");
        int t5 = 0; // Esfera
        if (level >= 235) {
            t5 = 3;
        }
        if (getPlayer().getLevel() >= 245) {
            t5--;
        }
        if (getPlayer().getLevel() >= 250) {
            t5--;
        }
        sb.append("t5=").append(t5).append(";");
        int c0 = 0; // Vanishing Journey
        for (int i = 34130; i <= 34150; i++) {
            if (getPlayer().getQuestStatus(i) == 2) {
                c0++;
            }
        }
        for (int i = 39055; i <= 39063; i++) {
            if (getPlayer().getQuestStatus(i) == 2) {
                c0++;
            }
        }
        if (getPlayer().getQuestStatus(34129) == 2) { // If Vanishing Journey main quest is cleared
            c0 = t0;
        }
        sb.append("c0=").append(c0).append(";");
        int c1 = 0; // Chu Chu
        for (int i = 39017; i <= 39033; i++) {
            if (getPlayer().getQuestStatus(i) == 2) {
                c1++;
            }
        }
        for (int i = 39064; i <= 39070; i++) {
            if (getPlayer().getQuestStatus(i) == 2) {
                c1++;
            }
        }
        if (getPlayer().getQuestStatus(39014) == 2) { // If Chu Chu main quest is cleared
            c1 = t1;
        }
        sb.append("c1=").append(c1).append(";");
        int c2 = 0; // Lachelein
        for (int i = 34381; i <= 34394; i++) {
            if (getPlayer().getQuestStatus(i) == 2) {
                c2++;
            }
        }
        if (getPlayer().getQuestStatus(34378) == 2) { // If Lachelein main quest is cleared
            c2 = t2;
        }
        sb.append("c2=").append(c2).append(";");
        int c3 = 0; // Arcana
        for (int i = 39038; i <= 39050; i++) {
            if (getPlayer().getQuestStatus(i) == 2) {
                c3++;
            }
        }
        if (getPlayer().getQuestStatus(39035) == 2) { // If Arcana main quest is cleared
            c3 = t3;
        }
        sb.append("c3=").append(c3).append(";");
        int c4 = 0; // Morass
        for (int i = 34276; i <= 34296; i++) {
            if (getPlayer().getQuestStatus(i) == 2) {
                c4++;
            }
        }
        if (getPlayer().getQuestStatus(34275) == 2) { // If Morass main quest is cleared
            c4 = t4;
        }
        sb.append("c4=").append(c4).append(";");
        int c5 = 0; // Esfera
        for (int i = 34780; i <= 34799; i++) {
            if (getPlayer().getQuestStatus(i) == 2) {
                c5++;
            }
        }
        if (getPlayer().getQuestStatus(34773) == 2) { // If Esfera main quest is cleared
            c5 = t5;
        }
        sb.append("c5=").append(c5).append(";");
        // TODO: Check this logic

        getPlayer().updateInfoQuest(39051, sb.toString());
        t0 = 3;
        if (getPlayer().getLevel() >= 210)
            t0--;
        if (getPlayer().getLevel() >= 220)
            t0--;
        t1 = 3; // Hungry Muto
        if (getPlayer().getLevel() >= 220)
            t1--;
        if (getPlayer().getLevel() >= 225)
            t1--;
        t2 = 3; // Dream Breaker
        if (getPlayer().getLevel() >= 225)
            t2--;
        if (getPlayer().getLevel() >= 230)
            t2--;
        t3 = 3;
        if (getPlayer().getLevel() >= 230)
            t3--;
        if (getPlayer().getLevel() >= 235)
            t3--;
        c0 = getPlayer().getOneInfoQuestInteger(34170, "count");
        c1 = getPlayer().GetCount("hungry_muto");
        c2 = getPlayer().GetCount("dream_breaker");
        c3 = getPlayer().getOneInfoQuestInteger(16214, "count");
        c3 = getPlayer().getOneInfoQuestInteger(16214, "count");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
        LocalDate lastTimeSpectrum = null;
        LocalDate lastTimeSavior = null;
        LocalDate now = LocalDate.now();
        try {
            lastTimeSpectrum = LocalDate.parse(getPlayer().getOneInfo(34170, "date"), formatter);
        } catch (Exception e) {
        }
        try {
            lastTimeSavior = LocalDate.parse(getPlayer().getOneInfo(16214, "date"), formatter);
        } catch (Exception e) {
        }
        if (lastTimeSpectrum != null) {
            if (!lastTimeSpectrum.isEqual(now)) {
                c0 = 0;
            }
        }
        if (lastTimeSavior != null) {
            if (!lastTimeSavior.isEqual(now)) {
                c3 = 0;
            }
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("t0=").append(t0).append(";").append("t1=").append(t1).append(";").append("t2=").append(t2)
                .append(";").append("t3=").append(t3).append(";")
                .append("c0=").append(c0).append(";").append("c1=").append(c1).append(";").append("c2=").append(c2)
                .append(";").append("c3=").append(c3).append(";");
        getPlayer().updateInfoQuest(39052, sb2.toString());
        getPlayer().send(arcaneRiverQuickPath(level >= 200 ? true : false));
    }

    public void arcaneRiverQuickPath0() {
        int t0 = 5;
        // Edit here
        if (getPlayer().getQuestStatus(i) == 2) {
            t0--;
        }
    }

    int c0 = 0;
    List<Integer> quests = new ArrayList<>();for(
    int i = 34130;i<=34150;i++)
    { // ที่นี่แก้ไข
        quests.add(i);
        if (getPlayer().getQuestStatus(i) == 2) {
            c0++;
        }
    }for(
    int i = 39055;i<=39063;i++)
    { // ที่นี่แก้ไข
        quests.add(i);
        if (getPlayer().getQuestStatus(i) == 2) {
            c0++;
        }
    }
    int basicPay = 1 * getClient().getChannelServer().getVanishingJourneySymbolBonusRate(); // ที่นี่แก้ไข
    int totalPay = (8 * getClient().getChannelServer().getVanishingJourneySymbolBonusRate()) - basicPay; // ที่นี่แก้ไข
    String cityName = "Vanishing Journey"; // ที่นี่แก้ไข
    String key = "c0"; // ที่นี่แก้ไข
    int itemId = 1712001; // ที่นี่แก้ไข
    int questId = 34129;totalPay-=basicPay*Integer.parseInt(getPlayer().getOneInfo(39051, key));
        arcaneRiverQuickPath(cityName, quests, key, t0, c0, basicPay, totalPay, itemId, questId);
    }

    public void arcaneRiverQuickPath1() {
        int t0 = 3;
        for (int i = 34227; i <= 34228; i++) { // ที่นี่แก้ไข
            if (getPlayer().getQuestStatus(i) == 2) {
                t0--;
            }
        }
        int c0 = 0;
        List<Integer> quests = new ArrayList<>();
        for (int i = 39017; i <= 39033; i++) { // ที่นี่แก้ไข
            quests.add(i);
            if (getPlayer().getQuestStatus(i) == 2) {
                c0++;
            }
        }
        for (int i = 39064; i <= 39070; i++) { // ที่นี่แก้ไข
            quests.add(i);
            if (getPlayer().getQuestStatus(i) == 2) {
                c0++;
            }
        }
        int basicPay = 2 * getClient().getChannelServer().getChewChewSymbolBonusRate(); // ที่นี่แก้ไข
        int totalPay = (8 * getClient().getChannelServer().getChewChewSymbolBonusRate()) - basicPay; // ที่นี่แก้ไข
        String cityName = "Chu Chu Island"; // ที่นี่แก้ไข
        String key = "c1"; // ที่นี่แก้ไข
        int itemId = 1712002; // ที่นี่แก้ไข
        int questId = 39014;
        totalPay -= basicPay * Integer.parseInt(getPlayer().getOneInfo(39051, key));
        arcaneRiverQuickPath(cityName, quests, key, t0, c0, basicPay, totalPay, itemId, questId);
    }

    public void arcaneRiverQuickPath2() {
        int t0 = 3;
        for (int i = 34333; i <= 34334; i++) { // ที่นี่แก้ไข
            if (getPlayer().getQuestStatus(i) == 2) {
                t0--;
            }
        }
        int c0 = 0;
        List<Integer> quests = new ArrayList<>();
        for (int i = 34381; i <= 34394; i++) { // ที่นี่แก้ไข
            quests.add(i);
            if (getPlayer().getQuestStatus(i) == 2) {
                c0++;
            }
        }
        int basicPay = 2 * getClient().getChannelServer().getLachelnSymbolBonusRate(); // ที่นี่แก้ไข
        int totalPay = (8 * getClient().getChannelServer().getLachelnSymbolBonusRate()) - basicPay; // ที่นี่แก้ไข
        String cityName = "Lachelein"; // ที่นี่แก้ไข
        String key = "c2"; // ที่นี่แก้ไข
        int itemId = 1712003; // ที่นี่แก้ไข
        int questId = 34378;
        totalPay -= basicPay * Integer.parseInt(getPlayer().getOneInfo(39051, key));
        arcaneRiverQuickPath(cityName, quests, key, t0, c0, basicPay, totalPay, itemId, questId);
    }

    public void arcaneRiverQuickPath3() {
        int t0 = 3;
        for (int i = 34491; i <= 34492; i++) { // ที่นี่แก้ไข
            if (getPlayer().getQuestStatus(i) == 2) {
                t0--;
            }
        }
        int c0 = 0;
        List<Integer> quests = new ArrayList<>();
        for (int i = 39038; i <= 39050; i++) { // ที่นี่แก้ไข
            quests.add(i);
            if (getPlayer().getQuestStatus(i) == 2) {
                c0++;
            }
        }
        int basicPay = 2 * getClient().getChannelServer().getArcanaSymbolBonusRate(); // ที่นี่แก้ไข
        int totalPay = (8 * getClient().getChannelServer().getArcanaSymbolBonusRate()) - basicPay; // ที่นี่แก้ไข
        String cityName = "Arcana"; // ที่นี่แก้ไข
        String key = "c3"; // ที่นี่แก้ไข
        int itemId = 1712004; // ที่นี่แก้ไข
        int questId = 39035;
        totalPay -= basicPay * Integer.parseInt(getPlayer().getOneInfo(39051, key));
        arcaneRiverQuickPath(cityName, quests, key, t0, c0, basicPay, totalPay, itemId, questId);
    }

    public void arcaneRiverQuickPath4() {
        int t0 = 3;
        if (getPlayer().getLevel() >= 245) {
            t0--;
        }
        int c0 = 0;
        List<Integer> quests = new ArrayList<>();
        for (int i = 34276; i <= 34296; i++) { // ที่นี่แก้ไข
            quests.add(i);
            if (getPlayer().getQuestStatus(i) == 2) {
                c0++;
            }
        }
        int basicPay = 2 * getClient().getChannelServer().getMorassSymbolBonusRate(); // ที่นี่แก้ไข
        int totalPay = (8 * getClient().getChannelServer().getMorassSymbolBonusRate()) - basicPay; // ที่นี่แก้ไข
        String cityName = "Morass"; // ที่นี่แก้ไข
        String key = "c4"; // ที่นี่แก้ไข
        int itemId = 1712005;
        int questId = 34275;
        totalPay -= basicPay * Integer.parseInt(getPlayer().getOneInfo(39051, key));
        arcaneRiverQuickPath(cityName, quests, key, t0, c0, basicPay, totalPay, itemId, questId);
    }

    public void arcaneRiverQuickPath5() {
        int t0 = 3; // Esfera
        if (getPlayer().getLevel() >= 245) {
            t0--;
        }
        if (getPlayer().getLevel() >= 250) {
            t0--;
        }
        int c0 = 0; // Esfera
        List<Integer> quests = new ArrayList<>();
        for (int i = 34780; i <= 34799; i++) { // ที่นี่แก้ไข
            quests.add(i);
            if (getPlayer().getQuestStatus(i) == 2) {
                c0++;
            }
        }
        int basicPay = 2 * getClient().getChannelServer().getArcanaSymbolBonusRate(); // ที่นี่แก้ไข
        int totalPay = (8 * getClient().getChannelServer().getArcanaSymbolBonusRate()) - basicPay; // ที่นี่แก้ไข
        String cityName = "Esfera";
        String key = "c5"; // ที่นี่แก้ไข
        int itemId = 1712006;
        int questId = 34773;
        totalPay -= basicPay * Integer.parseInt(getPlayer().getOneInfo(39051, key));
        arcaneRiverQuickPath(cityName, quests, key, t0, c0, basicPay, totalPay, itemId, questId);
    }

    @SuppressWarnings("deprecation")
    public void arcaneRiverQuickPath(String cityName, List<Integer> quests, String key, int t, int c, int basicPay,
            int totalPay, int itemId, int questID) {
        int commission = 500 * (t - c);
        if (1 == self.askYesNo("ให้ฉันช่วยจัดการเควสรายวันของ #b" + cityName + "#k ให้เอาไหม?\r\n\r\nค่าจ้างคือ #b"
                + commission + " Maple Point#k นะ\r\nแถมฉันจะให้ #b#i" + itemId + ":# #t" + itemId + ":# " + basicPay
                + "+" + totalPay + " ชิ้น#k กับเจ้าด้วยเป็นไง?", ScriptMessageFlag.Scenario)) {
            if (getPlayer().getMaplePoints() >= commission) {
                if (target.exchange(itemId, (basicPay + totalPay)) > 0) {
                    MapleQuestStatus qStatus = new MapleQuestStatus(MapleQuest.getInstance(questID), 2);
                    String saveDate = LocalDate.now().plusDays(1).toString(); // default ISO-8601 yyyy-MM-dd
                    qStatus.setCustomData("0-0-0-0-0-n-n-n-n-n-" + saveDate);
                    getPlayer().updateQuest(qStatus);
                    for (Integer quest : quests) {
                        if (getPlayer().getQuestStatus(quest) == 1) {
                            getPlayer().forceCompleteQuest(quest);
                        }
                    }
                    getPlayer().updateOneInfo(39051, key, String.valueOf(t));
                    getPlayer().setMaplePoint(getPlayer().getMaplePoints() - commission);
                    self.say("เรียบร้อยแล้วล่ะ\r\nคิคิ.. ไว้มาใช้บริการใหม่นะ", ScriptMessageFlag.Scenario);
                } else {
                    self.say("ช่องเก็บของไม่พอนี่นา\r\nไปเคลียร์ช่องเก็บของแล้วค่อยกลับมาใหม่นะ");
                }
            } else {
                self.say("ดูเหมือน Maple Point ของเจ้าจะไม่พอนะ ลองตรวจสอบดูใหม่สิ");
            }
        }
    }

    public void arcaneRiverQuickPath10() { // Erda Spectrum

        int t = 3;
        if (getPlayer().getLevel() >= 210)
            t--;
        if (getPlayer().getLevel() >= 220)
            t--;
        int c = getPlayer().getOneInfoQuestInteger(34170, "count");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
        LocalDate lastTime = null;
        LocalDate now = LocalDate.now();
        try {
            lastTime = LocalDate.parse(getPlayer().getOneInfo(34170, "date"), formatter);
        } catch (Exception e) {
        }
        if (lastTime != null) {
            if (!lastTime.isEqual(now)) {
                c = 0;
            }
        }
        int basicPay = 10;

        if (c > t) {
            c = t;
        }
        int commission = 500 * (t - c);
        int totalItem = (basicPay * 3) - (c * basicPay);
        int itemId = 1712001;
        if (1 == self.askAccept("ให้ฉันช่วยจัดการ #rErda Spectrum#k ให้เอาไหม?\r\n\r\nค่าจ้างคือ #b" + commission
                + " Maple Point#k นะ\r\nแถมฉันจะให้ #b#i1712001:# #t1712001:# " + totalItem + " ชิ้น#k กับเจ้าด้วย",
                ScriptMessageFlag.Scenario)) {
            if (getPlayer().getMaplePoints() >= commission) {
                if (target.exchange(itemId, totalItem) > 0) {
                    getPlayer().updateOneInfo(34170, "count", "3");
                    getPlayer().updateOneInfo(34170, "date", LocalDate.now().format(formatter));
                    getPlayer().updateOneInfo(39052, "c0", String.valueOf(t));
                    getPlayer().setMaplePoint(getPlayer().getMaplePoints() - commission);
                    self.say("เรียบร้อยแล้วล่ะ\r\nคิคิ.. ไว้มาใช้บริการใหม่นะ", ScriptMessageFlag.Scenario);
                } else {
                    self.say("ช่องเก็บของไม่พอนี่นา\r\nไปเคลียร์ช่องเก็บของแล้วค่อยกลับมาใหม่นะ");
                }
            } else {
                self.say("ดูเหมือน Maple Point ของเจ้าจะไม่พอนะ ลองตรวจสอบดูใหม่สิ");
            }
        }
    }

    public void arcaneRiverQuickPath11() { // Hungry Muto
        if (getPlayer().getMutoHighRank() == -1) {
            self.sayOk(
                    "#rHungry Muto#k ดูเหมือนจะไม่พบคะแนนสูงสุดของเจ้านะ\r\nฉันต้องใช้คะแนนนั้นเป็นเกณฑ์ในการจัดการ ถึงจะยุติธรรมจริงไหมล่ะ? คิคิ...\r\n\r\n(#bArcane River Quick Pass#k จำเป็นต้องเคยเล่นคอนเทนต์นั้นๆ มาก่อนอย่างน้อย 1 ครั้ง)",
                    ScriptMessageFlag.Scenario);
            return;
        }

        // #r배고픈 무토#k ประมวลผล해สัปดาห์면 되겠ฉัน?\r\n\r\n의뢰금은 #b500 메이플คะแนน#k라네.\r\n부가적으로
        // 얻게될 #b#i1712002:# #t1712002:# 15개#k 자네에게 สัปดาห์겠네.
        int t = 3;
        for (int i = 34227; i <= 34228; i++) { // ที่นี่แก้ไข
            if (getPlayer().getQuestStatus(i) == 2) {
                t--;
            }
        }
        int c = getPlayer().GetCount("hungry_muto");

        int basicPay = 5;
        int highDiff = getPlayer().getMutoHighDifficultly();
        int highRank = getPlayer().getMutoHighRank();
        if (highDiff == 2) { // Hard
            if (highRank == 1) { // Rank A
                basicPay = 4;
            } else if (highRank == 2) { // Rank B
                basicPay = 3;
            }
        } else if (highDiff == 1) {
            if (highRank == 0) {
                basicPay = 3;
            } else if (highRank == 1) {
                basicPay = 2;
            } else if (highRank == 2) {
                basicPay = 1;
            }
        } else if (highDiff == 0) {
            basicPay = 1;
        }
        if (c > t) {
            c = t;
        }
        int commission = 500 * (t - c);
        int totalItem = (basicPay * 3) - (c * basicPay);
        // #r배고픈 무토#k ประมวลผล해สัปดาห์면 되겠ฉัน?\r\n\r\n의뢰금은 #b500 메이플คะแนน#k라네.\r\n부가적으로
        // 얻게될 #b#i1712002:# #t1712002:# 15개#k 자네에게 สัปดาห์겠네.
        if (1 == self.askAccept("ให้ฉันช่วยจัดการ #rHungry Muto#k ให้เอาไหม?\r\n\r\nค่าจ้างคือ #b" + commission
                + " Maple Point#k นะ\r\nแถมฉันจะให้ #b#i1712002:# #t1712002:# " + totalItem + " ชิ้น#k กับเจ้าด้วย",
                ScriptMessageFlag.Scenario)) {
            if (getPlayer().getMaplePoints() >= commission) {
                if (target.exchange(1712002, totalItem) > 0) {
                    while (getPlayer().GetCount("hungry_muto") < 3) {
                        getPlayer().CountAdd("hungry_muto");
                    }
                    getPlayer().updateOneInfo(39052, "c1", String.valueOf(t));
                    getPlayer().setMaplePoint(getPlayer().getMaplePoints() - commission);
                    self.say("เรียบร้อยแล้วล่ะ\r\nคิคิ.. ไว้มาใช้บริการใหม่นะ", ScriptMessageFlag.Scenario);
                } else {
                    self.say("ช่องเก็บของไม่พอนี่นา\r\nไปเคลียร์ช่องเก็บของแล้วค่อยกลับมาใหม่นะ");
                }
            } else {
                self.say("ดูเหมือน Maple Point ของเจ้าจะไม่พอนะ ลองตรวจสอบดูใหม่สิ");
            }
        }
    }

    public void arcaneRiverQuickPath12() { // Dream Breaker
        int best = getPlayer().getOneInfoQuestInteger(15901, "best");
        if (best <= 0) {
            self.sayOk(
                    "#rDream Breaker#k ดูเหมือนจะไม่พบคะแนนสูงสุดของเจ้านะ\r\nฉันต้องใช้คะแนนนั้นเป็นเกณฑ์ในการจัดการ ถึงจะยุติธรรมจริงไหมล่ะ? คิคิ...\r\n\r\n(#bArcane River Quick Pass#k จำเป็นต้องเคยเล่นคอนเทนต์นั้นๆ มาก่อนอย่างน้อย 1 ครั้ง)",
                    ScriptMessageFlag.Scenario);
            return;
        }
        int t = 3;
        if (getPlayer().getLevel() >= 225)
            t--;
        if (getPlayer().getLevel() >= 230)
            t--;
        int c = getPlayer().GetCount("dream_breaker");

        int basicPay = getPlayer().getOneInfoQuestInteger(15901, "best");

        if (c > t) {
            c = t;
        }
        int commission = 500 * (t - c);
        int totalItem = (basicPay * 3) - (c * basicPay);
        int itemId = 4310227;
        if (1 == self.askAccept("#rDream Breaker#k ให้ฉันช่วยจัดการให้เอาไหม?\r\n\r\nค่าจ้างคือ #b" + commission
                + " Maple Point#k นะ\r\nแถมฉันจะให้ #b#i4310227:# #t4310227:# " + totalItem
                + " ชิ้น#k กับเจ้าด้วย\r\n\r\n(※ #rหมายเหตุ#k : หากสำเร็จผ่าน Quick Pass จะไม่ได้รับเหรียญโบนัส 'สู้เขานะ!' และไม่ถูกบันทึกในอันดับ)",
                ScriptMessageFlag.Scenario)) {
            if (getPlayer().getMaplePoints() >= commission) {
                if (target.exchange(itemId, totalItem) > 0) {
                    while (getPlayer().GetCount("dream_breaker") < 3) {
                        getPlayer().CountAdd("dream_breaker");
                    }
                    getPlayer().updateOneInfo(39052, "c2", String.valueOf(t));
                    getPlayer().setMaplePoint(getPlayer().getMaplePoints() - commission);
                    self.say("เรียบร้อยแล้วล่ะ\r\nคิคิ.. ไว้มาใช้บริการใหม่นะ", ScriptMessageFlag.Scenario);
                } else {
                    self.say("ช่องเก็บของไม่พอนี่นา\r\nไปเคลียร์ช่องเก็บของแล้วค่อยกลับมาใหม่นะ");
                }
            } else {
                self.say("ดูเหมือน Maple Point ของเจ้าจะไม่พอนะ ลองตรวจสอบดูใหม่สิ");
            }
        }
    }

    public void arcaneRiverQuickPath13() { // Spirit Savior

        int t = 3;
        if (getPlayer().getLevel() >= 230)
            t--;
        if (getPlayer().getLevel() >= 235)
            t--;
        int c = getPlayer().getOneInfoQuestInteger(16214, "count");

        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
        LocalDate lastTime = null;
        LocalDate now = LocalDate.now();
        try {
            lastTime = LocalDate.parse(getPlayer().getOneInfo(16214, "date"), formatter);
        } catch (Exception e) {
        }
        if (lastTime != null) {
            if (!lastTime.isEqual(now)) {
                c = 0;
            }
        }
        int basicPay = 10;

        if (c > t) {
            c = t;
        }
        int commission = 500 * (t - c);
        int totalItem = (basicPay * 3) - (c * basicPay);
        int itemId = 4310235;
        if (1 == self.askAccept("#rSpirit Savior#k ให้ฉันช่วยจัดการให้เอาไหม?\r\n\r\nค่าจ้างคือ #b" + commission
                + " Maple Point#k นะ\r\nแถมฉันจะให้ #b#i4310235:# #t4310235:# " + totalItem
                + " ชิ้น#k กับเจ้าด้วย\r\nได้ข่าวว่าวันนี้เจ้าได้รับ Spirit Coin มา #r#e30 เหรียญขึ้นไป#n#k แล้วนี่? เท่านี้ก็ดีที่สุดแล้วล่ะมั้ง? คิคิ..\r\n\r\n\r\n(※ #rหมายเหตุ#k : หากสำเร็จผ่าน Quick Pass จะไม่ได้รับเหรียญโบนัส)",
                ScriptMessageFlag.Scenario)) {
            if (getPlayer().getMaplePoints() >= commission) {
                if (target.exchange(itemId, totalItem) > 0) {
                    getPlayer().updateOneInfo(16214, "date", LocalDate.now().format(formatter));
                    getPlayer().updateOneInfo(16214, "count", "3");
                    getPlayer().updateOneInfo(39052, "c3", String.valueOf(t));
                    getPlayer().setMaplePoint(getPlayer().getMaplePoints() - commission);
                    self.say("เรียบร้อยแล้วล่ะ\r\nคิคิ.. ไว้มาใช้บริการใหม่นะ", ScriptMessageFlag.Scenario);
                } else {
                    self.say("ช่องเก็บของไม่พอนี่นา\r\nไปเคลียร์ช่องเก็บของแล้วค่อยกลับมาใหม่นะ");
                }
            } else {
                self.say("ดูเหมือน Maple Point ของเจ้าจะไม่พอนะ ลองตรวจสอบดูใหม่สิ");
            }
        }
    }

    public byte[] arcaneRiverQuickPath(boolean available) {
        final PacketEncoder p = new PacketEncoder();
        p.writeShort(SendPacketOpcode.ARCANERIVER_QUICK_PATH.getValue());
        p.writeInt(available ? 1 : 0);
        return p.getPacket();
    }
}
