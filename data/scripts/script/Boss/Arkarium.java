package script.Boss;

import constants.GameConstants;
import constants.QuestExConstants;
import database.DBConfig;
import objects.context.party.Party;
import objects.context.party.PartyMemberEntry;
import objects.effect.child.PlayMusicDown;
import objects.fields.Field;
import objects.fields.gameobject.Reactor;
import objects.fields.gameobject.lifes.ChangeableStats;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.fields.gameobject.lifes.MapleMonster;
import objects.item.Item;
import objects.item.MapleInventory;
import objects.item.MapleInventoryType;
import objects.item.MapleItemInformationProvider;
import objects.users.MapleCharacter;
import scripting.EventInstanceManager;
import scripting.EventManager;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Arkarium extends ScriptEngineNPC {

    public void timeCrack() {
        initNPC(MapleLifeFactory.getNPC(2144017));
        String v = "#e<รอยแยกแห่งกาลเวลา>#n\r\nอดีตและอนาคต, และที่ไหนสักแห่งระหว่างนั้น... ท่านต้องการจะไปที่ไหน?\r\n#b#L0# Leafre ในอดีต#l\r\n#L1# รอยแยกของมิติ#l";
        int v0 = self.askMenu(v, ScriptMessageFlag.NpcReplacedByNpc);
        if (v0 == 0) {
            self.say("ขณะนี้กำลังเตรียมการอยู่ครับ");
        } else if (v0 == 1) {
            registerTransferField(272020000);
        }
    }

    public void check_eNum() {
        registerTransferField(272020110);
    }

    public void portalNPC1() {
        initNPC(MapleLifeFactory.getNPC(2144017));
        EventManager em = getEventManager("Arkarium");
        List<Integer> arkMap = new ArrayList(Arrays.asList(272020200, 272020201, 272020202, 272020203, 272020204,
                272020205, 272020206, 272020207, 272020208, 272020209, 272020210, 272020211, 272020212, 272020213,
                272020214, 272020215, 272020216, 272020217, 272020218, 272020219));
        if (arkMap.contains(target.getMapId())) { // สถานะอยู่ในแผนที่ต่อสู้
            if (self.askYesNo("ต้องการออกจากแท่นบูชาของ Arkarium และสิ้นสุดการต่อสู้หรือไม่?") == 1) {
                registerTransferField(272020110);
                if (getPlayer().getEventInstance() != null) {
                    getPlayer().getEventInstance().unregisterPlayer(getPlayer());
                    getPlayer().setEventInstance(null);
                    getPlayer().setRegisterTransferFieldTime(0);
                    getPlayer().setRegisterTransferField(0);
                }
            }
        } else {
            if (target.getParty() == null) {
                self.say("ต้องสร้างปาร์ตี้ตั้งแต่ 1 คนขึ้นไปถึงจะเข้าได้ครับ");
            } else {
                if (target.getParty().getLeader().getId() != target.getId() && DBConfig.isGanglim) {
                    self.say("กรุณาให้หัวหน้าปาร์ตี้เป็นผู้ดำเนินการครับ");
                } else {
                    int v0 = self.askMenu(
                            "#e<บอส: Arkarium>#n\r\nผู้กล้าผู้ยิ่งใหญ่ ท่านพร้อมที่จะเผชิญหน้ากับผู้บัญชาการกองพันอันชั่วร้ายของ Black Mage แล้วหรือยัง?\r\n#b\r\n#L0# สมัครเข้าสู่ <บอส: Arkarium>#l");
                    if (v0 == 0) {
                        String menu = "";
                        if (DBConfig.isGanglim) {
                            menu = "#e<บอส: Arkarium>#n\r\nกรุณาเลือกโหมดที่ต้องการ\r\n\r\n#L0# Easy Mode (เลเวล 140 ขึ้นไป)#l\r\n#L1# Normal Mode (เลเวล 140 ขึ้นไป)#l";
                        } else {
                            boolean single = getPlayer().getPartyMemberSize() == 1;
                            int reset = getPlayer().getOneInfoQuestInteger(
                                    QuestExConstants.DailyQuestResetCount.getQuestID(),
                                    "Arkarium" + (single ? "Single" : "Multi"));
                            menu = "#e<บอส: Arkarium>#n\r\nกรุณาเลือกโหมดที่ต้องการ\r\n\r\n"
                                    + "#L0# Easy Mode " + (single ? "(Single)" : "(Multi)")
                                    + " (เลเวล 140 ขึ้นไป)#l\r\n"
                                    + "#L1# Normal Mode " + (single ? "(Single)" : "(Multi)")
                                    + " (เลเวล 140 ขึ้นไป)#l\r\n";
                            if (((single ? 2 : 1) - reset) >= 0) {
                                menu += "#L2# เพิ่มจำนวนการเข้า " + (single ? "(Single)" : "(Multi)")
                                        + ((single ? 2 : 1) - reset) + " ครั้ง)#l";
                            }
                        }
                        int v1 = self.askMenu(menu);
                        if (target.getParty().isPartySameMap()) {
                            if (v1 == 2 && !DBConfig.isGanglim) {
                                boolean single = getPlayer().getPartyMemberSize() == 1;
                                int reset = getPlayer().getOneInfoQuestInteger(
                                        QuestExConstants.DailyQuestResetCount.getQuestID(),
                                        "Arkarium" + (single ? "Single" : "Multi"));
                                if (getPlayer().getTogetherPoint() < 150) {
                                    self.sayOk(
                                            "คะแนนความร่วมมือไม่เพียงพอ มีคะแนน : " + getPlayer().getTogetherPoint());
                                    return;
                                }
                                if ((single ? 1 : 0) < reset) {
                                    self.sayOk("วันนี้ไม่สามารถเพิ่มจำนวนการเข้าได้อีกแล้วครับ");
                                    return;
                                }
                                getPlayer().gainTogetherPoint(-150);
                                getPlayer().updateOneInfo(QuestExConstants.DailyQuestResetCount.getQuestID(),
                                        "Arkarium" + (single ? "Single" : "Multi"), String.valueOf(reset + 1));
                                self.sayOk("เพิ่มจำนวนการเข้าเรียบร้อยแล้วครับ");
                                return;
                            }
                            if (!DBConfig.isGanglim) {
                                if (target.getParty().getLeader().getId() != target.getId()) {
                                    self.say("กรุณาให้หัวหน้าปาร์ตี้เป็นผู้ดำเนินการครับ");
                                    return;
                                }
                            }
                            String overLap = null;
                            if (!DBConfig.isGanglim) {
                                overLap = checkEventNumber(getPlayer(), QuestExConstants.Arkarium.getQuestID());
                            }
                            if (overLap == null) {
                                boolean canEnter = false;
                                String mode = "easy";
                                if (v1 == 0) { // Easy Mode
                                    if (em.getProperty("status0").equals("0")) {
                                        canEnter = true;
                                    }
                                } else if (v1 == 1) { // Normal Mode
                                    if (em.getProperty("Nstatus0").equals("0")) {
                                        mode = "normal";
                                        canEnter = true;
                                    }
                                }

                                int v2 = -1;
                                if (v1 == 1) {
                                    if (getPlayer().getQuestStatus(2000020) == 1) {
                                        if (GameConstants.isZero(getPlayer().getJob())) {
                                            v2 = self.askMenu(
                                                    "#e<Genesis Weapon>#n\r\nเจ้าสามารถทำภารกิจเพื่อปลดปล่อยความลับของ #bGenesis Weapon#k ที่มีพลังของ Black Mage ได้ จะทำอย่างไรดี?\r\n\r\n#e#r<เงื่อนไขการทำภารกิจ>#n#k\r\n#b - กำจัดด้วยตัวคนเดียว\r\n - ลด Final Damage 70%\r\n - ใช้ได้เฉพาะค่าสถานะบริสุทธิ์ของอุปกรณ์ที่สวมใส่\r\n#k#L0#ทำภารกิจ#l\r\n#L1#ไม่ทำภารกิจ#l",
                                                    ScriptMessageFlag.Self);
                                        } else {
                                            v2 = self.askMenu(
                                                    "#e<Genesis Weapon>#n\r\nเจ้าสามารถทำภารกิจเพื่อปลดปล่อยความลับของ #bGenesis Weapon#k ที่มีพลังของ Black Mage ได้ จะทำอย่างไรดี?\r\n\r\n#e#r<เงื่อนไขการทำภารกิจ>#n#k\r\n#b - กำจัดด้วยตัวคนเดียว\r\n - สวมใส่ได้เฉพาะ Genesis Weapon ที่ถูกผนึกและอาวุธรองเท่านั้น\r\n - ลด Final Damage 70%\r\n - ใช้ได้เฉพาะค่าสถานะบริสุทธิ์ของอุปกรณ์ที่สวมใส่\r\n#k#L0#ทำภารกิจ#l\r\n#L1#ไม่ทำภารกิจ#l",
                                                    ScriptMessageFlag.Self);
                                        }
                                        if (v2 == 0) {
                                            if (!checkBMQuestEquip()) {
                                                return;
                                            }
                                            if (getPlayer().getParty().getPartyMemberList().size() > 1) {
                                                self.say("เควสต์ดังกล่าวต้องดำเนินการเพียงคนเดียว",
                                                        ScriptMessageFlag.Self);
                                                return;
                                            }
                                        }
                                    }
                                }
                                if (canEnter) {
                                    if (DBConfig.isGanglim) {
                                        Party party = getPlayer().getParty();
                                        for (PartyMemberEntry mpc : party.getPartyMemberList()) {
                                            MapleCharacter p = getPlayer().getMap().getCharacterById(mpc.getId());
                                            int key = 1234569 + v1;
                                            if (p != null) {
                                                int count = p.getOneInfoQuestInteger(key, "akairum_clear");
                                                if (count >= (1 + p.getBossTier())) {
                                                    self.say("สมาชิกปาร์ตี้ #b#e" + p.getName()
                                                            + "#n#k ไม่สามารถท้าทายได้อีกแล้วในวันนี้");
                                                    return;
                                                }
                                                p.updateOneInfo(key, "akairum_clear", String.valueOf(count + 1));
                                            }
                                        }
                                    }
                                    if (mode.equals("easy")) {
                                        em.setProperty("status0", "1");
                                    } else {
                                        em.setProperty("Nstatus0", "1");
                                    }
                                    EventInstanceManager eim = em.readyInstance();
                                    int map = 272020210;
                                    if (v1 == 1) {
                                        map = 272020200;
                                    }
                                    boolean single = getPlayer().getPartyMemberSize() == 1;
                                    if (!DBConfig.isGanglim && !single) {
                                        for (MapleCharacter partyMember : getPlayer().getPartyMembers()) {
                                            if (partyMember.getMapId() == getPlayer().getMapId()) {
                                                partyMember.setMultiMode(true);
                                                partyMember.applyBMCurseJinMulti();
                                            }
                                        }
                                    }
                                    eim.setProperty("map", map);
                                    eim.setProperty("mode", mode);
                                    getClient().getChannelServer().getMapFactory().getMap(map).resetFully(false);
                                    getClient().getChannelServer().getMapFactory().getMap(map + 100).resetFully(false); // พื้นที่ภายในอันชั่วร้าย
                                    if (v2 == 0) {
                                        getPlayer().applyBMCurse1(2);
                                    }
                                    updateEventNumber(getPlayer(), QuestExConstants.Arkarium.getQuestID());
                                    eim.registerParty(target.getParty(), getPlayer().getMap());
                                } else {
                                    self.sayOk("ขณะนี้แผนที่ทั้งหมดเต็มแล้วครับ กรุณาใช้แชนแนลอื่น");
                                }
                            } else {
                                self.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                        + "#n#k เข้าไปแล้วในวันนี้ ถ้าอย่างนั้นวันนี้คงเข้าไม่ได้แล้วล่ะ");
                            }
                        } else {
                            self.say(target.getParty().getPartyMemberList().size()
                                    + "คน ทั้งหมดต้องอยู่ในแผนที่เดียวกันครับ");
                        }
                    }
                }
            }
        }
    }

    int[] bmWeapons = GameConstants.bmWeapons;

    public boolean checkBMQuestEquip() {
        MapleInventory inv = getPlayer().getInventory(MapleInventoryType.EQUIPPED);
        MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        List<Integer> blockedList = new ArrayList<>();
        for (int next = 0; next > -3999; --next) {
            Item item = inv.getItem((short) next);
            if (item == null) {
                continue;
            }
            if (!ii.isCash(item.getItemId())) {
                if (next == -11 || next == -10 || next <= -1600 && next >= -1700 || next == -117 || next == -122
                        || next == -131) {
                    if (next == -11) {
                        boolean find = false;
                        for (int i = 0; i < bmWeapons.length; ++i) {
                            int weapon = bmWeapons[i];
                            if (item.getItemId() == weapon) {
                                find = true;
                                break;
                            }
                        }
                        if (!find) {
                            if (!(item.getItemId() >= 1572000 && item.getItemId() <= 1572010)) {
                                blockedList.add(item.getItemId());
                            }
                        }
                    }
                } else {
                    blockedList.add(item.getItemId());
                }
            }
        }
        if (!blockedList.isEmpty()) {
            String v0 = "ต้องสวมใส่ #rอาวุธ#k และ #bอาวุธรอง#k เพื่อท้าทาย\r\n\r\n#r<ไอเท็มที่ต้องปลดล็อกและสวมใส่>#k\r\n";
            for (int i = 0; i < blockedList.size(); ++i) {
                int bid = blockedList.get(i);
                v0 += "#i" + bid + "# #z" + bid + "#\r\n";
            }
            self.say(v0, ScriptMessageFlag.Self);
            return false;
        }
        return true;
    }

    public void Akayrum_accept() {
        if (!DBConfig.isGanglim) {
            portalNPC1();
            return;
        }
        EventManager em = getEventManager("Arkarium");
        List<Integer> arkMap = new ArrayList(Arrays.asList(272020200, 272020201, 272020202, 272020203, 272020204,
                272020205, 272020206, 272020207, 272020208, 272020209, 272020210, 272020211, 272020212, 272020213,
                272020214, 272020215, 272020216, 272020217, 272020218, 272020219));
        if (arkMap.contains(target.getMapId())) { // สถานะอยู่ในแผนที่ต่อสู้
            if (self.askYesNo("ต้องการออกจากแท่นบูชาของ Arkarium และสิ้นสุดการต่อสู้หรือไม่?") == 1) {
                registerTransferField(272020110);
            }
        } else {
            if (target.getParty() == null) {
                self.say("ต้องสร้างปาร์ตี้ตั้งแต่ 1 คนขึ้นไปถึงจะเข้าได้ครับ");
            } else {
                if (target.getParty().getLeader().getId() != target.getId()) {
                    self.say("กรุณาให้หัวหน้าปาร์ตี้เป็นผู้ดำเนินการครับ");
                } else {
                    int v0 = self.askMenu(
                            "#e<บอส: Arkarium>#n\r\nผู้กล้าผู้ยิ่งใหญ่ ท่านพร้อมที่จะเผชิญหน้ากับผู้บัญชาการกองพันอันชั่วร้ายของ Black Mage แล้วหรือยัง?\r\n#b\r\n#L0# สมัครเข้าสู่ <บอส: Arkarium>#l");
                    if (v0 == 0) {
                        int v1 = self.askMenu(
                                "#e<บอส: Arkarium>#n\r\nกรุณาเลือกโหมดที่ต้องการ\r\n\r\n#L0# Easy Mode (เลเวล 140 ขึ้นไป)#l\r\n#L1# Normal Mode (เลเวล 140 ขึ้นไป)#l");
                        if (target.getParty().isPartySameMap()) {
                            String overLap = checkEventNumber(getPlayer(), QuestExConstants.Arkarium.getQuestID());
                            if (overLap == null) {
                                boolean canEnter = false;
                                String mode = "easy";
                                if (v1 == 0) { // Easy Mode
                                    if (em.getProperty("status0").equals("0")) {
                                        canEnter = true;
                                    }
                                } else if (v1 == 1) { // Normal Mode
                                    if (em.getProperty("Nstatus0").equals("0")) {
                                        mode = "normal";
                                        canEnter = true;
                                    }
                                }
                                if (!canEnter) { // กรณที่ไม่สามารถเข้าได้ เช็คว่ามีผู้เล่นในแผนที่หรือไม่
                                                 // แล้วรีเซ็ตอินสแตนซ์
                                    int map = 272020210;
                                    if (v1 == 1) {
                                        map = 272020200;
                                    }
                                    if (getClient().getChannelServer().getMapFactory().getMap(map).getCharacters()
                                            .size() == 0) {
                                        String rt = em.getProperty("ResetTime");
                                        long curTime = System.currentTimeMillis();
                                        long time = rt == null ? 0 : Long.parseLong(rt);
                                        if (time == 0) {
                                            em.setProperty("ResetTime", String.valueOf(curTime));
                                        } else if (time - curTime >= 10000) { // ถ้าแผนที่ว่างเกิน 10 วินาที
                                                                              // เปลี่ยนให้เข้าได้
                                            canEnter = true;
                                            em.setProperty("ResetTime", "0");
                                        }
                                    }
                                }
                                if (canEnter) {
                                    if (mode.equals("easy")) {
                                        em.setProperty("status0", "1");
                                    } else {
                                        em.setProperty("Nstatus0", "1");
                                    }
                                    EventInstanceManager eim = em.readyInstance();
                                    int map = 272020210;
                                    if (v1 == 1) {
                                        map = 272020200;
                                    }
                                    updateEventNumber(getPlayer(), QuestExConstants.Arkarium.getQuestID());
                                    eim.setProperty("map", map);
                                    eim.setProperty("mode", mode);
                                    getClient().getChannelServer().getMapFactory().getMap(map).resetFully(false);
                                    getClient().getChannelServer().getMapFactory().getMap(map + 100).resetFully(false); // พื้นที่ภายในอันชั่วร้าย
                                    eim.registerParty(target.getParty(), getPlayer().getMap());
                                } else {
                                    self.sayOk("ขณะนี้แผนที่ทั้งหมดเต็มแล้วครับ กรุณาใช้แชนแนลอื่น");
                                }
                            } else {
                                self.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                        + "#n#k เข้าไปแล้วในวันนี้ ถ้าอย่างนั้นวันนี้คงเข้าไม่ได้แล้วล่ะ");
                            }
                        } else {
                            self.say(target.getParty().getPartyMemberList().size()
                                    + "คน ทั้งหมดต้องอยู่ในแผนที่เดียวกันครับ");
                        }
                    }
                }
            }
        }
    }

    public void Akayrum_Before() {
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            if (eim.getProperty("summmonMOB") == null) {
                eim.setProperty("summmonMOB", "1");
                Field field = getPlayer().getMap();
                field.startMapEffect(
                        "เหล่าผู้ที่ไม่สามารถแยกแยะระหว่างความกล้าหาญและความบ้าบิ่น... ถ้าไม่เสียดายชีวิตก็เข้ามา ข้าจะจัดการให้ ฮึฮึ",
                        5120056);
                PlayMusicDown e = new PlayMusicDown(getPlayer().getId(), 100, "Voice.img/akayrum/2");
                field.broadcastMessage(e.encodeForLocal());
                field.removeNpc(2144016); // Rhinne ออกไป!
                field.spawnNpc(2144010, new Point(320, -190));
            }
        }
    }

    public void Akayrum_Before2() { // Easy Arkarium
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            if (eim.getProperty("summmonMOB") == null) {
                eim.setProperty("summmonMOB", "1");
                Field field = getPlayer().getMap();
                field.startMapEffect(
                        "เหล่าผู้ที่ไม่สามารถแยกแยะระหว่างความกล้าหาญและความบ้าบิ่น... ถ้าไม่เสียดายชีวิตก็เข้ามา ข้าจะจัดการให้ ฮึฮึ",
                        5120056);
                PlayMusicDown e = new PlayMusicDown(getPlayer().getId(), 100, "Voice.img/akayrum/2");
                field.broadcastMessage(e.encodeForLocal());
                field.removeNpc(2144016); // Rhinne Leave!
                field.spawnNpc(2144021, new Point(320, -190));
            }
        }
    }

    public void Akayrum_Summon() { // Arkarium Summon!!!!!!!!(NPC ID : 2144010)
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            if (target.getParty().getLeader().getId() == target.getId()) { // Only Party Leader can summon
                                                                           // (Prevent double summon)
                if (self.askAccept(
                        "พวกที่ทำให้แผนการอันยาวนานของข้ากลายเป็นฟองอากาศ กลับเดินเข้ามาหาที่ตายถึงที่ น่าดีใจเสียจริงๆ\r\n\r\n#rเพื่อเป็นการตอบแทน ข้าจะมอบความตายที่ทรมานที่สุดในโลกให้พวกเจ้า#k") == 1) {
                    // When accepted, Arkarium disappears and monsters are summoned!
                    Field field = getPlayer().getMap();
                    field.removeNpc(2144010);
                    if (DBConfig.isGanglim) {
                        field.spawnMonster(MapleLifeFactory.getMonster(8860010), new Point(320, -190), 32);
                    } else {
                        if (getPlayer().getPartyMemberSize() == 1) {
                            field.spawnMonster(MapleLifeFactory.getMonster(8860010), new Point(320, -190), 32);
                        } else {
                            final MapleMonster arkarium = MapleLifeFactory.getMonster(8860010);
                            arkarium.setPosition(new Point(320, -190));
                            final long orghp = arkarium.getMobMaxHp();
                            ChangeableStats cs = new ChangeableStats(arkarium.getStats());
                            cs.hp = orghp * 3L;
                            if (cs.hp < 0) {
                                cs.hp = Long.MAX_VALUE;
                            }
                            arkarium.getStats().setHp(cs.hp);
                            arkarium.getStats().setMaxHp(cs.hp);
                            arkarium.setOverrideStats(cs);
                            field.spawnMonster(arkarium, -2);
                        }
                    }
                }
            }
        }
    }

    public void Akayrum_Summon2() { // Easy Arkarium Summon!!!!!!!!(NPC ID : 2144021)
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            if (target.getParty().getLeader().getId() == target.getId()) { // Only Party Leader can summon
                                                                           // (Prevent double summon)
                if (self.askAccept(
                        "พวกที่ทำให้แผนการอันยาวนานของข้ากลายเป็นฟองอากาศ กลับเดินเข้ามาหาที่ตายถึงที่ น่าดีใจเสียจริงๆ\r\n\r\n#rเพื่อเป็นการตอบแทน ข้าจะมอบความตายที่ทรมานที่สุดในโลกให้พวกเจ้า#k") == 1) {
                    // When accepted, Arkarium disappears and monsters are summoned!
                    Field field = getPlayer().getMap();
                    field.removeNpc(2144021);
                    if (DBConfig.isGanglim) {
                        field.spawnMonster(MapleLifeFactory.getMonster(8860007), new Point(320, -190), 32);
                    } else {
                        if (getPlayer().getPartyMemberSize() == 1) {
                            field.spawnMonster(MapleLifeFactory.getMonster(8860007), new Point(320, -190), 32);
                        } else {
                            final MapleMonster arkarium = MapleLifeFactory.getMonster(8860007);
                            arkarium.setPosition(new Point(320, -190));
                            final long orghp = arkarium.getMobMaxHp();
                            ChangeableStats cs = new ChangeableStats(arkarium.getStats());
                            cs.hp = orghp * 3L;
                            if (cs.hp < 0) {
                                cs.hp = Long.MAX_VALUE;
                            }
                            arkarium.getStats().setHp(cs.hp);
                            arkarium.getStats().setMaxHp(cs.hp);
                            arkarium.setOverrideStats(cs);
                            field.spawnMonster(arkarium, -2);
                        }
                    }
                }
            }
        }
    }

    public void Akayrum_retry() {
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            registerTransferField(Integer.parseInt(eim.getProperty("map")));
        }
    }

    public void akayrum_saveTheGoddess() {
        getPlayer().getMap().hideNpc(2144020);
        self.say("เจ้ากำจัดผู้บัญชาการกองพันผู้ชั่วร้าย #p2144010# ได้แล้วสินะ");
        self.say("ในที่สุดข้าก็ถูกปลดปล่อยจากการถูกผนึกมาเป็นเวลานาน ขอบคุณมากนะ #h0#");
    }

    // Normal
    public void inAkayrumPrison() {
        for (MapleMonster mob : getPlayer().getMap().getAllMonstersThreadsafe()) {
            getPlayer().getMap().removeMonster(mob, 1);
        }
        getPlayer().getMap().startMapEffect("รู้สึกอย่างไรที่ได้เผชิญหน้ากับรูปลักษณ์ที่น่าเกลียดในจิตใจตนเอง?",
                5120057, false, 5);

        MapleMonster mob = MapleLifeFactory.getMonster(8860003);
        getPlayer().getMap().spawnMonsterOnGroundBelow(mob, new Point(88, 95));
    }

    // Easy
    public void inAkayrumPrison2() {
        for (MapleMonster mob : getPlayer().getMap().getAllMonstersThreadsafe()) {
            getPlayer().getMap().removeMonster(mob, 1);
        }
        getPlayer().getMap().startMapEffect("รู้สึกอย่างไรที่ได้เผชิญหน้ากับรูปลักษณ์ที่น่าเกลียดในจิตใจตนเอง?",
                5120057, false, 5);

        MapleMonster mob = MapleLifeFactory.getMonster(8860003);
        getPlayer().getMap().spawnMonsterOnGroundBelow(mob, new Point(88, 95));
    }

    public void outAkayrumPrison() {
        if (getPlayer().getMap().getMobsSize() == 0) {
            getPlayer().setRegisterTransferField(272020200);
            getPlayer().setRegisterTransferFieldTime(System.currentTimeMillis());
        } else {
            getPlayer().dropMessage(5,
                    "ต้องกำจัดร่างแยกของตัวเองที่บิดเบี้ยวให้ได้ก่อน จึงจะหนีออกจากจิตใจที่ชั่วร้ายได้");
        }
    }

    public void outAkayrumP2() {
        if (getPlayer().getMap().getMobsSize() == 0) {
            getPlayer().setRegisterTransferField(272020210);
            getPlayer().setRegisterTransferFieldTime(System.currentTimeMillis());
        } else {
            getPlayer().dropMessage(5,
                    "ต้องกำจัดร่างแยกของตัวเองที่บิดเบี้ยวให้ได้ก่อน จึงจะหนีออกจากจิตใจที่ชั่วร้ายได้");
        }
    }

    // Arkarium Monitor Break lua script
    public void Akayrum_lastHit1() {
        Reactor reactor = getPlayer().getMap().getReactorByName("marble1");
        if (reactor != null) {
            reactor.forceHitReactor((byte) 1);
        }
    }

    public void Akayrum_lastHit2() {
        Reactor reactor = getPlayer().getMap().getReactorByName("marble2");
        if (reactor != null) {
            reactor.forceHitReactor((byte) 1);
        }
    }

    public void Akayrum_lastHit3() {
        Reactor reactor = getPlayer().getMap().getReactorByName("marble3");
        if (reactor != null) {
            reactor.forceHitReactor((byte) 1);
        }
    }

    public void Akayrum_lastHit4() {
        Reactor reactor = getPlayer().getMap().getReactorByName("marble4");
        if (reactor != null) {
            reactor.forceHitReactor((byte) 1);
            getPlayer().getMap().startMapEffect(
                    "กล้าต้อนให้ข้ามาจนถึงที่นี่ได้เชียวรึ... คราวนี้ข้าจะรับมือพวกเจ้าให้เต็มที่หน่อยก็แล้วกัน",
                    5120057, false, 5);
        }
    }
}
