package script.DailyQuest;

import network.models.FontColorType;
import network.models.FontType;
import objects.quest.MapleQuest;
import objects.quest.MapleQuestStatus;
import objects.utils.Randomizer;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DarkWorldTree extends ScriptEngineNPC {

    @SuppressWarnings("deprecation")
    public void q39002s() {
        //เลือก하는 เควส
        List<Integer> quests = new ArrayList<>();
        int[] questArray = {39003, 39004, 39005, 39006, 39007, 39008, 39009, 39010, 39011, 39012};
        MapleQuestStatus quest = getPlayer().getQuestIfNullAdd(MapleQuest.getInstance(39002));
        if (quest.getCustomData() == null) {
            quest.setCustomData("0-0");
        }
        if (quest.getCustomData().split("-")[0].equals("0")) {
            //วินาที기 เควส 선정해줘야함
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
                if (quests.size() == 5) break;
            }
        }
        Date time = new Date();
        if (time.getDay() == 1) {
            time.setDate(time.getDate() + 7);
        } else {
            while (time.getDay() != 1) {
                time.setDate(time.getDate() + 1);
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String resetDay = sdf.format(time);
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
        askText.append("이곳 โลก #b최강의 전사#k여. 이번 สัปดาห์에 도울 วัน은 ล่างและ 같다.\r\n\r\n");
        for (Integer q : quests) {
            askText.append("#b#e#y")
                    .append(q)
                    .append("##k#n\r\n");
        }
        askText.append("\r\n#e지금 바로 타락한 โลก수의 정화를 돕지 않겠ฉัน?#n\r\n(마음에 들지 않는다면 교체하기 버튼을 눌러 อื่น ภารกิจ로 교체할 수 มี.)");
        if (1 == self.askYesNo(askText.toString(), ScriptMessageFlag.Change)) {
            askText = new StringBuilder();
            askText.append("รายการ에 있는 ภารกิจ가 마음에 들지 않는다면 อื่น ภารกิจ를 찾아볼 수도 있지. 교체 싶은 ภารกิจ를 เลือก해라.\r\n\r\n");
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
            askText.append("#L5# #r#e더 이상 교체 싶은 ภารกิจ는 ไม่มี.#k#n#l");
            int selection = self.askMenu(askText.toString());
            List<Integer> changeQuest = new ArrayList<>();
            while (selection != 5) {
                if (!changeQuest.contains(selection)) changeQuest.add(selection);
                askText = new StringBuilder();
                askText.append("รายการ에 있는 ภารกิจ가 마음에 들지 않는다면 อื่น ภารกิจ를 찾아볼 수도 있지. 교체 싶은 ภารกิจ를 เลือก해라.\r\n\r\n");
                for (int i = 0; i < 5; i++) {
                    if (changeQuest.contains(i)) {
                        askText.append("#e#L" + i + "# #y" + quests.get(i) + "##l#k#n\r\n");
                    } else {
                        askText.append("#b#e#L" + i + "# #y" + quests.get(i) + "##l#k#n\r\n");
                    }
                }
                askText.append("#L5# #r#e더 이상 교체 싶은 ภารกิจ는 ไม่มี.#k#n#l");
                if (changeQuest.size() == 5) break;
                if (getSc().isStop()) break;
                selection = self.askMenu(askText.toString());
            }
            String scriptText = "제외된 ภารกิจ 대신 ใหม่ ภารกิจ를 찾았다.\r\n\r\n";
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
            getPlayer().getQuestIfNullAdd(MapleQuest.getInstance(39002)).setCustomData("0-0-0-0-0-n-n-n-n-n-" + resetDay);
            self.say(scriptText);
        } else {
            getQuest().forceStart(getPlayer(), getNpc().getId(), quest.getCustomData());
            getQuest().forceComplete(getPlayer(), getNpc().getId());
            getPlayer().getQuestIfNullAdd(MapleQuest.getInstance(39002)).setCustomData("0-0-0-0-0-n-n-n-n-n-" + resetDay);
            for (Integer q : quests) {
                MapleQuest.getInstance(q).forceStart(getPlayer(), getNpc().getId(), "");
            }
            self.sayOk("ภารกิจ가 ทั้งหมด 끝ฉัน면 내게 돌아และ เสร็จสมบูรณ์ 된다. \r\nทั้งหมด ภารกิจ는 #e#rวัน요วัน 자정#k#nถึง 유효하니 내게서 รางวัล을 받아 가려면 เขา전에 돌아오도록 해라.");
        }
    }

    @SuppressWarnings("deprecation")
    public void q15708s() {
        self.say("혹시 วันสัปดาห์วัน ใน에 โลก수를 #b5회 이상 정화#k 해준다면, 자네의 ความสามารถ을 인정 #r보답#k #i4001868:# #b#t4001868##k 더 สัปดาห์겠네.", ScriptMessageFlag.NpcReplacedByNpc);
        if (self.askYesNo("ฉัน의 부하들이 ฉัน지르고 있는 만행을 멈추고 โลก수의 타락을 막는 데에 힘써สัปดาห์겠ฉัน?", ScriptMessageFlag.NpcReplacedByNpc) == 1) {
            //เดือน요วัน날 리셋된다.
            Date time = new Date();
            if (time.getDay() == 1) {
                time.setDate(time.getDate() + 7);
            } else {
                while (time.getDay() != 1) {
                    time.setDate(time.getDate() + 1);
                }
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String resetDay = sdf.format(time);
            getQuest().forceStart(getPlayer(), getNpc().getId(), resetDay);
            self.say("เขา럼 부탁하겠네.", ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
        } else {
            self.say("내가 คน을 잘못 봤ฉัน 보군...\r\n혹시라도 마음이 바뀌면 다시 말하게ฉัน.", ScriptMessageFlag.NpcReplacedByNpc);
        }
    }

    public void q15708e() {
        self.say("자네는 내 예상ดู 훨씬 더 훌륭하게 ภารกิจ를 수행해สัปดาห์었군.", ScriptMessageFlag.NpcReplacedByNpc);
        self.say("감사의 แสดง라고 하기에는 약소แต่ #r보답#k 선น้ำ을 นิดหน่อย 더 สัปดาห์겠네.", ScriptMessageFlag.NpcReplacedByNpc);

        if (target.exchange(4001868, (2 * 2)) > 0) {
            getQuest().forceComplete(getPlayer(), getNpc().getId());
            self.say("ที่นี่ #i4001868:# #b#t4001868##k 받게. 덕นาที에 내 부하들이 악에서 헤어ฉัน올 수 있었다. 정말 고맙다...", ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
        } else {
            self.say("자네 가ห้อง이 가득찬거 같구만. อื่นๆ창을 1칸이상 비우고 다시และสัปดาห์게.");
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
        self.say("#r타락한 수액#k들을 น้ำ리치고 돌아왔군...\r\n#r마검의 힘#k ผลกระทบ을 받은 녀석들을 처치하는 것은 쉽지 않았을 텐데...", ScriptMessageFlag.NpcReplacedByNpc);
        if (target.exchange(4001868, (2 * eventRate)) > 0) {
            int cq = getPlayer().getOneInfoQuestInteger(15708, "cq");
            cq = cq + 1;
            getPlayer().updateOneInfo(15708, "cq", String.valueOf(cq));
            getPlayer().gainExp(40000000, true, true, false);
            bigScriptProgressMessage("EXP 40000000 ได้รับแล้ว", FontType.NanumGothicBold, FontColorType.Green);
            getQuest().forceComplete(getPlayer(), getNpc().getId());
            self.sayOk("자, 이건 #b약속한 선น้ำ#k이네.\r\n#i4001868:# #b#t4001868##k\r\nเรา 종족이 뿌린 #r악의 씨앗#k 거두는 วัน을 도และ줘서 고맙다..", ScriptMessageFlag.NpcReplacedByNpc);
        } else {
            self.sayOk("자네 가ห้อง이 가득찬거 같구만. อื่นๆ창을 1칸이상 비우고 다시และสัปดาห์게.");
        }
    }

    public void normalQuestEnd() {
        self.say("자네 덕นาที에 내 부하들의 영혼은 이제야 #b영원한 평화#k 얻었다...", ScriptMessageFlag.NpcReplacedByNpc);
        if (target.exchange(4001868, (2 * eventRate)) > 0) {
            int cq = getPlayer().getOneInfoQuestInteger(15708, "cq");
            cq = cq + 1;
            getPlayer().updateOneInfo(15708, "cq", String.valueOf(cq));
            getPlayer().gainExp(40000000, true, true, false);
            bigScriptProgressMessage("EXP 40000000 ได้รับแล้ว", FontType.NanumGothicBold, FontColorType.Green);
            getQuest().forceComplete(getPlayer(), getNpc().getId());
            self.sayOk("자, 이건 #b약속한 선น้ำ#k이네.\r\n#i4001868:# #b#t4001868##k\r\nเรา 종족이 뿌린 #r악의 씨앗#k 거두는 วัน을 도และ줘서 고맙다..", ScriptMessageFlag.NpcReplacedByNpc);
        } else {
            self.sayOk("자네 가ห้อง이 가득찬거 같구만. อื่นๆ창을 1칸이상 비우고 다시และสัปดาห์게.");
        }
    }

    public void itemQuestEnd(int needItem, int needItemQty) {
        self.say("자네 덕นาที에 내 부하들의 영혼은 이제야 #b영원한 평화#k 얻었다...", ScriptMessageFlag.NpcReplacedByNpc);
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
            self.sayOk("자, 이건 #b약속한 선น้ำ#k이네.\r\n#i4001868:# #b#t4001868##k\r\nเรา 종족이 뿌린 #r악의 씨앗#k 거두는 วัน을 도และ줘서 고맙다..", ScriptMessageFlag.NpcReplacedByNpc);
        } else {
            self.sayOk("자네 가ห้อง이 가득찬거 같구만. อื่นๆ창을 1칸이상 비우고 다시และสัปดาห์게.");
        }
    }
}
