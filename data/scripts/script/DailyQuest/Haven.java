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

public class Haven extends ScriptEngineNPC {

    @SuppressWarnings("deprecation")
    public void q39165s() {
        //เลือก하는 เควส
        List<Integer> quests = new ArrayList<>();
        int[] questArray = {39101, 39102, 39103, 39104, 39105, 39106, 39107, 39108, 39111, 39112, 39113, 39114, 39115, 39116, 39117, 39118, 39119, 39121, 39122, 39123, 39124, 39125/*, 39126*/, 39127, 39131, 39132, 39133, 39134, 39135, 39136, 39141, 39142, 39143, 39144, 39145, 39146, 39147, 39148, 39149, 39150, 39151, 39152, 39153, 39154, 39155};
        MapleQuestStatus quest = getPlayer().getQuestIfNullAdd(MapleQuest.getInstance(39165));
        if (quest.getCustomData() == null) {
            quest.setCustomData("0-0");
        }
        if (quest.getCustomData().split("-")[0].equals("0")) {
            //วินาที기 เควส 선정해줘야함
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
                if (quests.size() == 4) break;
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
                .append("0-")
                .append("n-n-n-n-n-")
                .append(resetDay);
        quest.setCustomData(customData.toString());
        StringBuilder askText = new StringBuilder();
        askText.append("이번 สัปดาห์ 자네에게 부탁할 วัน은 아래와 같다네.\r\n\r\n");
        for (Integer q : quests) {
            askText.append("#b#e#y")
                    .append(q)
                    .append("##k#n\r\n");
        }
        askText.append("\r\n#e지금 바로 수행하시겠나?#n\r\n(마음에 들지 않는다면 교체하기 버튼을 눌러 다른 ภารกิจ로 교체할 수도 있다네.)");
        if (1 == self.askYesNo(askText.toString(), ScriptMessageFlag.Change)) {
            askText = new StringBuilder();
            askText.append("รายการ에 있는 ภารกิจ가 마음에 들지 않는가 보군? 그렇다면 다른 ภารกิจ를 찾아볼 수도 있지. 교체 싶은 ภารกิจ를 골라สัปดาห์게나.\r\n\r\n");
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
            askText.append("#L4# #r#e더 이상 교체 싶은 ภารกิจ는 없다.#k#n#l");
            int selection = self.askMenu(askText.toString());
            List<Integer> changeQuest = new ArrayList<>();
            while (selection != 4) {
                if (!changeQuest.contains(selection)) changeQuest.add(selection);
                askText = new StringBuilder();
                askText.append("รายการ에 있는 ภารกิจ가 마음에 들지 않는가 보군? 그렇다면 다른 ภารกิจ를 찾아볼 수도 있지. 교체 싶은 ภารกิจ를 골라สัปดาห์게나.\r\n\r\n");
                for (int i = 0; i < 4; i++) {
                    if (changeQuest.contains(i)) {
                        askText.append("#e#L" + i + "# #y" + quests.get(i) + "##l#k#n\r\n");
                    } else {
                        askText.append("#b#e#L" + i + "# #y" + quests.get(i) + "##l#k#n\r\n");
                    }
                }
                askText.append("#L4# #r#e더 이상 교체 싶은 ภารกิจ는 없다.#k#n#l");
                if (changeQuest.size() == 4) break;
                if (getSc().isStop()) break;
                selection = self.askMenu(askText.toString());
            }
            String scriptText = "새로운 ภารกิจ를 찾았네.\r\n부탁할 วัน은 아래와 같다네.\r\n\r\n";
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
            getPlayer().getQuestIfNullAdd(MapleQuest.getInstance(39165)).setCustomData("0-0-0-0-0-n-n-n-n-n-" + resetDay);
            self.say(scriptText);
        } else {
            getQuest().forceStart(getPlayer(), getNpc().getId(), quest.getCustomData());
            getQuest().forceComplete(getPlayer(), getNpc().getId());
            getPlayer().getQuestIfNullAdd(MapleQuest.getInstance(39165)).setCustomData("0-0-0-0-0-n-n-n-n-n-" + resetDay);
            for (Integer q : quests) {
                MapleQuest.getInstance(q).forceStart(getPlayer(), getNpc().getId(), "");
            }
            self.sayOk("꼭 #e#rวัน요วัน 자정#k#n까지 ภารกิจ를 완수해야 한다는 점 잊지 말게. 그럼 안녕히 다녀오게나.");
        }
    }

    @SuppressWarnings("deprecation")
    public void q39160s() {
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
        self.say("말랑이, 이번에도 왔군.\r\n자네가 도와줘서 얼MP 고마운지 모르네.\r\n#b저번에 รางวัล을 받은 뒤로 의뢰를 4번 เสร็จสมบูรณ์했다면 나에게 알려สัปดาห์게, 핫핫핫.#k", ScriptMessageFlag.NoEsc);
    }

    public void q39160e() {
        if (target.exchange(4001842, (7 * 2)) > 0) {
            getQuest().forceComplete(getPlayer(), getNpc().getId());
            int level = getPlayer().getLevel();
            //39164 - [สัปดาห์간 เควส] 블랙헤븐 내부 พิเศษ 의뢰 : 적 로봇 처치
            if (level >= 216) {
                getPlayer().updateInfoQuest(39164, "start=1");
            } else if (level >= 211) {
                getPlayer().updateInfoQuest(39163, "start=1");
            } else if (level >= 206) {
                getPlayer().updateInfoQuest(39162, "start=1");
            } else {
                getPlayer().updateInfoQuest(39161, "start=1");
            }
            self.say("말랑이, 자네가 꾸준히 도와줘서 정찰이 훨씬 편해졌다네.\r\n여기, 이건 그에 대한 รางวัลวัน세.\r\n#i4001842# #b#t4001842##k\r\n\r\n앞으로도 매สัปดาห์ 의뢰를 4번 เสร็จสมบูรณ์ 나에게 알려สัปดาห์게. 핫핫핫.");
        } else {
            self.say("말랑이 자네 가방에든게 많나보구만 อื่นๆ창을 1칸이상 비우고 다시 말을걸어สัปดาห์게나.");
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
        self.say("말랑이, 긴급 의뢰가 들어왔네.\r\n#b적 로봇 처치#k 의뢰야.\r\n무서운 로봇들을 처치해สัปดาห์면 된다네. 다행히도 그 숫자가 많지는 않은 것 같네.");
        if (self.askAccept("처치해야 할 로봇은 아래와 같네.\r\n\r\n#r[의뢰 : 적 로봇 처치]#k\r\n처치 대상 : #b#o" + mobId + "# " + mobCount + "#k\r\n\r\n어때? 의뢰를 수락할 텐가?") == 1) {
            getQuest().forceStart(getPlayer(), getNpc().getId(), "");
            self.say("고맙네. 갑자기 무서운 로봇들이 늘어나서 정찰이 힘들어...\r\n다 처치 나면 여기로 돌아오게나.", ScriptMessageFlag.NoEsc);
        }
    }

    public void bonusQuestEnd(int mobId) {
        if (target.exchange(4001842, (5 * 2)) > 0) {
            getPlayer().updateInfoQuest(getQuest().getId(), "start=0");
            getQuest().forceComplete(getPlayer(), getNpc().getId());
            self.say("말랑이, #o" + mobId + "#들을 처치해줘서 고맙네.\r\n이제 정찰이 좀 수เดือน해 지겠구만..\r\n여기, 이건 그에 대한 รางวัลวัน세.\r\n#i4001842# #b#t4001842##k\r\n\r\n앞으로도 잘 부탁하네.", ScriptMessageFlag.NoEsc);
        } else {
            self.sayOk("말랑이 자네 가방에든게 많나보구만 อื่นๆ창을 1칸이상 비우고 다시 말을걸어สัปดาห์게나.");
        }
    }

    public void npc_2155000() {
        self.say("말랑이 내게 볼วัน이 있는가?");
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
        self.say("기다리고 있었네!  물건들이 꼭 จำเป็น했거든.\r\n음...ขอ한 그대로군. #b물건은 전부 가져가도록 하지.#k\r\n앞으로도 잘 부탁하네, 말랑이.", ScriptMessageFlag.NoEsc);
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
        self.say("증거가 확실하군. 증거용 재료는 모두 가져가겠네.\r\n수고 많았군. 자네 덕นาที에 부서진 감시탑을 다시 쓸 수 있게 됐어.\r\n정말 하기 힘든 วัน인데...\r\n앞으로도 잘 부탁하네, 말랑이.", ScriptMessageFlag.NoEsc);
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
        self.say("로봇들을 해방시켜줘서 고맙네.\r\n자네에게 인사하려는 로봇들이 너무 많아 전부 말리느라 힘들었다고. 자네니까 할 수 있는 วัน이야.\r\n앞으로도 잘 부탁하네, 말랑이.", ScriptMessageFlag.NoEsc);
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
        self.say("ช่วยเหลือ을 ขอ했더니, 말랑이, 자네가 오다니!\r\n자네라면 정말 큰 ช่วยเหลือ이 될걸세.\r\n저쪽에 고맙다고 말이라도 해야겠군.", ScriptMessageFlag.NoEsc);
        self.say("자, 그럼 วัน 얘기를 해볼까.\r\n 근처의 #b#o" + mobId + "##k들이 갑자기 너무 많아졌어.\r\n도저히 정찰을 할 수가 없다네.\r\n#b#o" + mobId + "#" + mobCount + "마리#k 처치해 สัปดาห์게.\r\n다 처치한 후엔 나에게 알려สัปดาห์면 되지, 잘 부탁하네.", ScriptMessageFlag.NoEsc);
    }

    public void NpcSpeechQuestEnd(int mobId) {
        self.say("#o" + mobId + "#들을 처치해줘서 고맙네.\r\n이제 정찰이 좀 수เดือน해 지겠구만.\r\n아무나 못하는 의뢰인데, 대단해!\r\n앞으로도 잘 부탁하네, 말랑이.", ScriptMessageFlag.NoEsc);
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
        self.say("ช่วยเหลือ은 잘 받았네.\r\n자네의 활약에 대해 내가 잘 전달해 두지.\r\n자네가 없었으면 큰วัน났을 거야.\r\n앞으로도 잘 부탁하네, 말랑이.", ScriptMessageFlag.NoEsc);
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
