package script.Event;

import constants.QuestExConstants;
import network.models.CField;
import objects.quest.MapleQuest;
import scripting.newscripting.ScriptEngineNPC;

public class HasteEvent extends ScriptEngineNPC {

    public static String[] customData = {"", "", "", "", "count=0", "count=0", "RunAct=0", "suddenMK=0"};

    public void q16401s() {
        if (getPlayer().getQuestStatus(QuestExConstants.HasteEventInit.getQuestID()) < 1) {
            self.say("ใน녕! #b#h0##k!\\r\\n#e사냥 가속 #b<헤이스트>#k 이벤트#n 돌아왔어!");
            self.say("#b#e<헤이스트>#n#k #b2021ปี 11เดือน 4วัน 오전 0시#kจาก #b12เดือน 5วัน 오후 11시 59นาที#kถึง #b여러 가지 변화#k 생겨ฉัน 말 เขา대로 #b사냥을 가속#k시킬 수 있는 이벤트야!");
            self.say("어떤 변화가 생기냐면~!");
            self.say("#b- #e엘리트 มอนสเตอร์#n #e더#n 자สัปดาห์ ฉันและ!\\r\\n- #e룬#n #e더#n 자สัปดาห์ ฉัน오고 #e더#n 자สัปดาห์ 쓸 수 있어!\\r\\n- #e룬#n EXP เอฟเฟกต์가 두 배로 #e더#n 강력해져!\\r\\n- #e돌발ภารกิจ#n 하루에 두 배로 #e더#n 클리어할 수 있어!\\r\\n- #e폴로 & 프리토#n #e더#n 자สัปดาห์ ฉันและ!\\r\\n- #e불꽃늑대#n EXP 1.5배로 #e더#n 강력해져!");
            self.say("어때? 듣기만 해도 사냥이 팍팍 가속될 것 같지 않아?\\r\\n\\r\\n이뿐만이 아니야!\\r\\n이벤트 기간 동ใน #b#e6가지 วันวัน ภารกิจ#n#k 수행할 수 ,\\r\\nภารกิจ을 달성할 때 마다 #b#e<헤이스트 상자>#n#k 받을 수 있어!");
            self.say("6개의 #b<헤이스트 상자>#k에서는 푸짐한 #bรางวัล#k #b헤이스트 부스터#k 얻을 수 있으니까 ทุกวันทุกวัน 놓치지 말라구~!\\r\\n#b헤이스트 부스터#k ใช้ 100วินาที동ใน #bมอนสเตอร์#k #bเพิ่ม#k #b소환#k되니 사냥도 성장도 더 빠르게 가속시킬 수 있지!");
            self.say("하ฉัน 더!\\r\\n\\r\\n하루에 #e#b6가지 <헤이스트 วันวัน ภารกิจ>#n#k 전부 달성...\\r\\n흐흐... เขา건 네가 โดยตรง ยืนยัน해 봐!");


            for (int i = QuestExConstants.HasteEventInit.getQuestID(); i <= QuestExConstants.HasteEventSuddenMK.getQuestID(); i++) {
                if (i != QuestExConstants.HasteEventEliteBoss.getQuestID()) {
                    MapleQuest.getInstance(i).forceStart(getPlayer(), 9010010, "");
                    getPlayer().updateInfoQuest(i, customData[i - QuestExConstants.HasteEventInit.getQuestID()]);
                }
            }


            getPlayer().updateInfoQuest(QuestExConstants.HasteEvent.getQuestID(),
                    "M1=0;M2=0;M3=0;M4=0;M5=0;M6=0;date=21/11/03;booster=0;openBox=0;unlockBox=0;str=1단계 상자 도전 중! วันวัน ภารกิจ 1개를 เสร็จสมบูรณ์ทำ!");
        } else {
            getPlayer().send(CField.UIPacket.openUI(1251));
        }
    }

    public void weekHQuest() {
        self.say("#b#e자, 드디어! <헤이스트 히든 ภารกิจ>#n#k 열렸어!\r\n\r\n#b2021ปี 12เดือน 5วัน 오후 11시 59นาที#kถึง\r\n#b#eเลเวล 범บน มอนสเตอร์ 44,444마리#n#k... 아니라..#b#e88,888마리#n#k 사냥 된다구!");
        self.say("#b#e<헤이스트 히든 ภารกิจ 상자>#n#k에서는..\r\n#b#e#i2631097:# #t2631097:#,\r\n#i1114317:# #t1114317:##n#k 받을 수 있으니 힘을 내!");
    }

    public void useHasteBooster() {
        if (1 == self.askYesNo("#r#e헤이스트 부스터#n#k ใช้할꺼야?\r\n#b#e100วินาที동ใน มอนสเตอร์ เพิ่ม 소환된다구!#n#k\r\n진정한 사냥 가속을 할 수 있지!\r\n\r\n#e<ใช้ 할 수 없는 경우>#n\r\n 1. เลเวล 범บน มอนสเตอร์ 없는 필드 หรือ หมู่บ้าน.\r\n 2. 엘리트 บอส 소환 된 경우.\r\n 3. ปัจจุบัน 자신이 헤이스트 부스터를 ใช้ 중인 경우.\r\n 4. ปัจจุบัน อื่น 플레이어가 헤이스트 부스터를 ใช้ 중인 경우.")) {
            getPlayer().getMap().startHasteBooster(getPlayer());
        }
    }

}
