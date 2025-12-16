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

    public void Populatus01() {
        if (self.askYesNo(
                "บี๊บ บี๊บ~ สามารถกลับไปยังที่ปลอดภัยผ่านทางฉันได้นะ บี๊บ บี๊บ~ ต้องการออกไปข้างนอกเลยไหม?") == 1) {
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
            self.say("ขณะนี้ไม่สามารถเข้าร่วม Papulatus Raid ได้ครับ");
            return;
        }
        if (target.getParty() == null) {
            self.say("ต้องอยู่ในปาร์ตี้ตั้งแต่ 1 คนขึ้นไปถึงจะเข้าได้ครับ");
            return;
        }
        if (target.getParty().getLeader().getId() != target.getId()) {
            self.say("กรุณาให้หัวหน้าปาร์ตี้เป็นผู้ดำเนินการครับ");
            return;
        }
        if (!target.getParty().isPartySameMap()) {
            self.say("สมาชิกในปาร์ตี้ทั้งหมดต้องมารวมตัวกันที่นี่ครับ");
            return;
        }
        int[] startMaps = new int[] { 220080100, 220080200, 220080300, 220080300 };
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
                    self.say("วันนี้กำจัดไปแล้ว สามารถท้าทายได้ใหม่หลังจากรีเซ็ตจำนวนครั้งในเวลา 00.00 น.");
                    return;
                }
                if (!getPlayer().CountCheck(key, 1)) {
                    self.say("สามารถเข้าท้าทายได้วันละ 1 ครั้งครับ");
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
                    self.say(
                            "สัปดาห์นี้กำจัดไปแล้ว สามารถท้าทายได้ใหม่หลังจากรีเซ็ตจำนวนครั้งในวันพฤหัสบดีเวลา 00.00 น.");
                    return;
                }
                status = "ChaosStatus";
                deathCount = 5;
                minLev = 190;
                break;
            case 3:
                initNPC(MapleLifeFactory.getNPC(9010000));
                if (0 == self.askYesNo(
                        "คุณเลือกเข้าสู่โหมดฝึกซ้อม ในโหมดฝึกซ้อม #b#eจะไม่ได้รับ EXP และรางวัล#k#n สามารถเข้าได้ #b#eวันละ 20 ครั้ง#k#n โดยไม่แบ่งประเภทบอสมอนสเตอร์\r\n\r\nในโหมดฝึกซ้อมจะไม่ใช้ Buff Freezer เมื่อฟื้นคืนชีพหลังเสียชีวิต แต่ต้องมี #b#eBuff Freezer อย่างน้อย 1 ชิ้น#k#n ถึงจะสามารถใช้ได้\r\n\r\nต้องการตกลงเข้าสู่โหมดฝึกซ้อมหรือไม่?",
                        ScriptMessageFlag.NpcReplacedByNpc)) {
                    return;
                }
                key = "boss_practice";
                if (!getPlayer().CountCheck(key, 20)) {
                    self.say("สามารถเข้าท้าทายได้วันละ 20 ครั้งครับ", ScriptMessageFlag.NpcReplacedByNpc);
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
            self.sayOk(
                    "ไม่สามารถเข้าได้เนื่องจากจำนวนครั้งที่เข้าได้ไม่เพียงพอ หรือมีสมาชิกปาร์ตี้ที่เลเวลไม่ตรงตามเงื่อนไข");
            return;
        }
        if (!timePass) {
            self.sayOk("มีสมาชิกปาร์ตี้ที่ยังมีเวลาเข้าท้าทายเหลืออยู่ จึงไม่สามารถเข้าได้");
            return;
        } else {
            String canTimeKey = null;
            if (diff == 2) {
                canTimeKey = "papulatus_can_time";
            }
            setBossEnter(target.getParty(), ("Papulatus Difficulty : " + diff), key, canTimeKey, 3);
        }
        if (em.getProperty(status).equals("1")) {
            self.sayOk("ขณะนี้อินสแตนซ์ทั้งหมดเต็มแล้ว ไม่สามารถเข้าใช้งานได้ กรุณาใช้แชนแนลอื่น");
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
