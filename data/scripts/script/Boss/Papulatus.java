package script.Boss;

import objects.fields.MapleMapFactory;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.users.MapleCharacter;
import scripting.EventInstanceManager;
import scripting.EventManager;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

import java.util.Iterator;

public class Papulatus extends ScriptEngineNPC {

    /*
    public void Populatus00() {
        initNPC(MapleLifeFactory.getNPC(2041021));
        int menu = self.askMenu("#e<บอส: 파풀라투스>#n\r\n사고뭉치 파풀라투스가 ชา원을 ต่อไป 부수는 것을 막아야 . 도และสัปดาห์시겠어요?\r\n\r\n\r\n#L0# 이지 โหมด ( เลเวล 115 이상 )#l\r\n#L1# 노멀 โหมด ( เลเวล 155 이상 )#l\r\n#L2# 카오스 โหมด ( เลเวล 190 이상 )#l\r\n#L3# 카오스 연습 โหมด( เลเวล 190 이상 )#l");
        if (!getPlayer().haveItem(4031179)) {
            if (target.exchange(4031179, 1) > 0) {
                self.say("#r#eปาร์ตี้원 ทั้งหมด#n#k #b#eชา원 균열의 조แต่ละ#k#n 없으시군요. 파풀라투스를 만ฉัน기 บน해서 꼭 จำเป็น. 제가 마침 갖고 있는 것을 드리겠.");
                self.say("#b#eชา원 균열의 조แต่ละ#k#n 드렸으니, 파풀라투스가 ชา원을 부수는 것을 꼭 막아 สัปดาห์세요!");
            } else {
                self.say("อื่นๆ กระเป๋า 공간이 ไม่พอ. อื่นๆ กระเป๋า 공간을 충นาที히 확보โปรด.");
                return;
            }
        }
        enter(menu);
    }
     */

    public void Populatus01() {
        if (self.askYesNo("삐리 삐리~ ฉัน를 통해 ใน전한 곳으로 ฉัน가실 수 있. 삐리 삐리~ 이대로 นอก으로 ฉัน가시หรือไม่?") == 1) {
            if (getPlayer().getEventInstance() != null) {
                getPlayer().getEventInstance().unregisterPlayer(getPlayer());
                getPlayer().setEventInstance(null);
                getPlayer().setRegisterTransferFieldTime(0);
                getPlayer().setRegisterTransferField(0);
                registerTransferField(220080000);
            }
        }
    }

    private void enter(int diff) {
        EventManager em = getEventManager("Papulatus");
        if (em == null) {
            self.say("지금은 파풀라투스 레이드를 이용하실 수 없.");
            return;
        }
        if (target.getParty() == null) {
            self.say("1인 이상의 ปาร์ตี้ 속해야만 เข้า할 수 있.");
            return;
        }
        if (target.getParty().getLeader().getId() != target.getId()) {
            self.say("ปาร์ตี้장을 통해 ดำเนินการ해 สัปดาห์십시오.");
            return;
        }
        if (!target.getParty().isPartySameMap()) {
            self.say("ปาร์ตี้원이 전원 이곳에 모여있어야 .");
            return;
        }
        int[] startMaps = new int[]{220080100, 220080200, 220080300, 220080300};
        String status = "EasyStatus";
        int deathCount = 50;
        int startMap = startMaps[diff];
        int minLev = 115;
        boolean countPass = true;
        boolean timePass = true;
        String key = "papulatus_c";
        Iterator it = getClient().getChannelServer().getPartyMembers(target.getParty()).iterator();
        switch (diff) {
            case 0:
            case 1:
                String q = getPlayer().getOneInfoQuest(1234569, "papulatus_clear");
                if (q != null && !q.isEmpty() && q.equals("1")) {
                    self.say("วันนี้에 이미 격파 00시에 횟수 วินาที기화 이후 다시 도전 เป็นไปได้.");
                    return;
                }
                if (!getPlayer().CountCheck(key, 1)) {
                    self.say("하루에 1번만 시도 할 수 있.");
                    return;
                }
                if (diff == 1) {
                    status = "NormalStatus";
                    deathCount = 5;
                    minLev = 155;
                }
                break;
            case 2:
                q = getPlayer().getOneInfoQuest(1234569, "chaos_papulatus_clear");
                if (q != null && !q.isEmpty() && q.equals("1")) {
                    self.say("금สัปดาห์에 이미 격파 목요วัน 00시에 횟수 วินาที기화 이후 다시 도전 เป็นไปได้.");
                    return;
                }
                status = "ChaosStatus";
                deathCount = 5;
                minLev = 190;
                break;
            case 3:
                initNPC(MapleLifeFactory.getNPC(9010000));
                if (0 == self.askYesNo("연습 โหมด에 เข้า을 เลือก하셨. 연습 โหมด에서는 #b#eEXP รางวัล을 얻을 수 ไม่มี#k#n บอส มอนสเตอร์ 종류และ 상관없이 #b#e하루 20회#k#n 이용할 수 있.\r\n\r\n연습 โหมด에서는 사망 후 부활할 때 Buff 프리ฉัน를 ใช้해도 ใช้되지 않. 단, #b#eBuff 프리ฉัน가 1개 이상#k#n 있어야 ใช้할 수 있.\r\n\r\nเข้าต้องการหรือไม่?", ScriptMessageFlag.NpcReplacedByNpc)) {
                    return;
                }
                key = "boss_practice";
                if (!getPlayer().CountCheck(key, 20)) {
                    self.say("하루에 20번만 시도 เป็นไปได้.", ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }
                status = "ChaosStatus";
                deathCount = 5;
                minLev = 190;
                break;
        }
        while (it.hasNext()) {
            MapleCharacter chr = (MapleCharacter) it.next();
            if (chr.getLevel() < minLev) {
                countPass = false;
                break;
            }
            if (diff != 2 && diff != 3) {
                String q = chr.getOneInfoQuest(1234569, "papulatus_clear");
                if (q != null && !q.isEmpty() && q.equals("1")) {
                    countPass = false;
                    break;
                }
                if (!chr.CountCheck("papulatus_c", 1)) {
                    countPass = false;
                    break;
                }
            }
            if (diff == 2) {
                if (!chr.canEnterBoss("papulatus_can_time")) {
                    timePass = false;
                    break;
                }
            }
        }
        if (!countPass) {
            self.sayOk("เข้า จำกัด횟수가 ไม่พอ하거ฉัน เลเวล จำกัด이 맞지 않는 ปาร์ตี้원이 있어 เข้า할 수 없.");
            return;
        }
        if (!timePass) {
            self.sayOk("เข้า จำกัดเวลา이 남은 ปาร์ตี้원이 있어 เข้า할 수 없.");
            return;
        } else {
            String canTimeKey = null;
            if (diff == 2) {
                canTimeKey = "papulatus_can_time";
            }
            setBossEnter(target.getParty(), ("파풀라투스 ความยาก : " + diff), key, canTimeKey, 3);
        }
        if (em.getProperty(status).equals("1")) {
            self.sayOk("ปัจจุบัน ทั้งหมด 인스턴스가 가득ชา 이용하실 수 없. อื่น แชนแนล 이용โปรด.");
            return;
        }
        em.setProperty(status, "1");
        EventInstanceManager eim = em.readyInstance();
        eim.setProperty("mode", status.replace("Status", ""));
        eim.setProperty("map", startMap);
        eim.setProperty("deathCount", deathCount);
        eim.setProperty("practice", diff == 3 ? 1 : 0);
        MapleMapFactory mFactory = getClient().getChannelServer().getMapFactory();
        mFactory.getMap(startMap).resetFully(false);
        eim.registerParty(target.getParty(), getPlayer().getMap());
    }
}
