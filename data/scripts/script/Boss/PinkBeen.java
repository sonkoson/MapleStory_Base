package script.Boss;

import constants.QuestExConstants;
import database.DBConfig;
import objects.context.party.Party;
import objects.context.party.PartyMemberEntry;
import objects.fields.Field;
import objects.fields.gameobject.lifes.ChangeableStats;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.fields.gameobject.lifes.MapleMonster;
import objects.users.MapleCharacter;
import scripting.EventInstanceManager;
import scripting.EventManager;
import scripting.newscripting.ScriptEngineNPC;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PinkBeen extends ScriptEngineNPC {

    public void PinkBeen_accept() {
        EventManager em = getEventManager("PinkBeen");
        int[] normalMaps = new int[] { 270050100 };
        int[] chaosMaps = new int[] { 270051100 };
        if (em == null) {
            self.say("ขณะนี้ไม่สามารถเข้าร่วม Pink Bean Raid ได้ครับ");
        } else {
            String v = "#e<บอส: Pink Bean>#n\r\nดูเหมือนผู้บุกรุกจะมุ่งหน้าไปยัง Altar of Goddess หากไม่รีบหยุดเขา เรื่องน่ากลัวต้องเกิดขึ้นแน่ๆ\r\n#b\r\n#L0# สมัครเข้าสู่ <บอส: Pink Bean>#l";
            int v0 = self.askMenu(v);
            if (v0 == 0) {
                if (target.getParty() == null) {
                    self.say("ต้องอยู่ในปาร์ตี้ตั้งแต่ 1 คนขึ้นไปถึงจะเข้าได้ครับ");
                } else if (DBConfig.isGanglim && getPlayer().getParty().getLeader().getId() != getPlayer().getId()) {
                    self.say("กรุณาให้หัวหน้าปาร์ตี้เป็นผู้ดำเนินการครับ");
                } else {
                    if (target.getParty().isPartySameMap()) {
                        String v2 = "";
                        if (DBConfig.isGanglim) {
                            v2 = "#e<บอส: Pink Bean>#n\r\nกรุณาเลือกโหมดที่ต้องการ\r\n\r\n#L0# Normal Mode (เลเวล 160 ขึ้นไป)#l\r\n";
                            // v2 += "#L1# Chaos Mode (เลเวล 170 ขึ้นไป)#l\r\n#L2# Chaos Practice Mode
                            // (เลเวล 170 ขึ้นไป)#l";
                        } else {
                            boolean single = getPlayer().getPartyMemberSize() == 1;
                            v2 = "#e<บอส: Pink Bean>#n\r\nกรุณาเลือกโหมดที่ต้องการ\r\n\r\n"
                                    + "#L0# Normal Mode " + (single ? "(Single)" : "(Multi)")
                                    + " (เลเวล 160 ขึ้นไป)#l\r\n"
                                    + "#L1# Chaos Mode " + (single ? "(Single)" : "(Multi)")
                                    + " (เลเวล 170 ขึ้นไป)#l\r\n"
                                    + "#L2# Chaos Practice Mode " + (single ? "(Single)" : "(Multi)")
                                    + "(เลเวล 170 ขึ้นไป)#l\r\n";
                            int nreset = getPlayer().getOneInfoQuestInteger(
                                    QuestExConstants.DailyQuestResetCount.getQuestID(),
                                    "NormalPinkBeen" + (single ? "Single" : "Multi"));
                            v2 += "#L3# Normal Mode " + (single ? "(Single)" : "(Multi)") + " เพิ่มจำนวนการเข้า ("
                                    + ((single ? 2 : 1) - nreset) + " ครั้ง)#l\r\n";
                            int creset = getPlayer().getOneInfoQuestInteger(
                                    QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                                    "ChaosPinkBeen" + (single ? "Single" : "Multi"));
                            v2 += "#L4# Chaos Mode " + (single ? "(Single)" : "(Multi)") + " เพิ่มจำนวนการเข้า ("
                                    + (1 - creset) + " ครั้ง)";
                        }
                        boolean canEnter = false;
                        int questID = QuestExConstants.PinkBeen.getQuestID();
                        int selection = self.askMenu(v2);
                        if (selection == 1) { // Normal Mode
                            questID = QuestExConstants.ChaosPinkBeen.getQuestID();
                        }
                        String overLap = null;
                        if (!DBConfig.isGanglim) {
                            if (selection == 3) {
                                boolean single = getPlayer().getPartyMemberSize() == 1;
                                int nreset = getPlayer().getOneInfoQuestInteger(
                                        QuestExConstants.DailyQuestResetCount.getQuestID(),
                                        "NormalPinkBeen" + (single ? "Single" : "Multi"));
                                if (getPlayer().getTogetherPoint() < 150) {
                                    self.sayOk(
                                            "คะแนนความร่วมมือไม่เพียงพอ มีคะแนน : " + getPlayer().getTogetherPoint());
                                    return;
                                }
                                if (nreset > (single ? 1 : 0)) {
                                    self.sayOk("วันนี้ไม่สามารถเพิ่มจำนวนการเข้าได้อีกแล้วครับ");
                                    return;
                                }
                                getPlayer().gainTogetherPoint(-150);
                                getPlayer().updateOneInfo(QuestExConstants.DailyQuestResetCount.getQuestID(),
                                        "NormalPinkBeen" + (single ? "Single" : "Multi"), String.valueOf(nreset + 1));
                                self.sayOk("เพิ่มจำนวนการเข้าเรียบร้อยแล้วครับ");
                                return;
                            }
                            if (selection == 4) {
                                boolean single = getPlayer().getPartyMemberSize() == 1;
                                int creset = getPlayer().getOneInfoQuestInteger(
                                        QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                                        "ChaosPinkBeen" + (single ? "Single" : "Multi"));
                                if (getPlayer().getTogetherPoint() < 150) {
                                    self.sayOk(
                                            "คะแนนความร่วมมือไม่เพียงพอ มีคะแนน : " + getPlayer().getTogetherPoint());
                                    return;
                                }
                                if (creset > 0) {
                                    self.sayOk("สัปดาห์นี้ไม่สามารถเพิ่มจำนวนการเข้าได้อีกแล้วครับ");
                                    return;
                                }
                                getPlayer().gainTogetherPoint(-150);
                                getPlayer().updateOneInfo(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                                        "ChaosPinkBeen" + (single ? "Single" : "Multi"), String.valueOf(creset + 1));
                                self.sayOk("เพิ่มจำนวนการเข้าเรียบร้อยแล้วครับ");
                                return;
                            }

                            if (selection == 0 || selection == 1 || selection == 2) { // ตรวจสอบการเข้าปาร์ตี้จริง
                                if (target.getParty().getLeader().getId() != getPlayer().getId()) {
                                    self.say("กรุณาให้หัวหน้าปาร์ตี้เป็นผู้ดำเนินการครับ");
                                    return;
                                }
                            }
                            overLap = checkEventNumber(getPlayer(), questID);
                        }
                        if (selection == 0 || selection == 1) {
                            if (overLap == null) {
                                if (selection == 0) { // Normal Mode
                                    if (em.getProperty("status0").equals("0")) {
                                        canEnter = true;
                                    }
                                } else if (selection == 1) { // Chaos Mode
                                    if (em.getProperty("Cstatus0").equals("0")) {
                                        canEnter = true;
                                    }
                                }
                                if (canEnter) {
                                    if (DBConfig.isGanglim) {
                                        Party party = getPlayer().getParty();
                                        for (PartyMemberEntry mpc : party.getPartyMemberList()) {
                                            MapleCharacter p = getPlayer().getMap().getCharacterById(mpc.getId());
                                            int key = 1234569;
                                            if (p != null) {
                                                int count = p.getOneInfoQuestInteger(key, "pinkbean_clear");
                                                if (count >= (1 + p.getBossTier())) {
                                                    self.say("สมาชิกปาร์ตี้ #b#e" + p.getName()
                                                            + "#n#k ไม่สามารถเข้าท้าทายได้อีกในวันนี้ครับ");
                                                    return;
                                                }
                                                p.updateOneInfo(key, "pinkbean_clear", String.valueOf(count + 1));
                                            }
                                        }
                                    }
                                    if (selection == 0)
                                        em.setProperty("status0", "1");
                                    else if (selection == 1)
                                        em.setProperty("Cstatus0", "1");
                                    EventInstanceManager eim = em.readyInstance();
                                    int map = selection == 0 ? normalMaps[0] : chaosMaps[0];
                                    eim.setProperty("map", map);
                                    eim.setProperty("mode", selection == 0 ? "normal" : "chaos");
                                    eim.getMapInstance(map).setLastRespawnTime(Long.MAX_VALUE); // ป้องกันการเกิดใหม่
                                    eim.getMapInstance(map).resetFully(false);
                                    if (selection == 0) {
                                        if (DBConfig.isGanglim) {
                                            updateEventNumber(getPlayer(), questID);
                                        }
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
                                    eim.registerParty(target.getParty(), getPlayer().getMap());
                                } else {
                                    self.say(
                                            "ขณะนี้อินสแตนซ์ทั้งหมดเต็มแล้ว ไม่สามารถเข้าใช้งานได้ กรุณาใช้แชนแนลอื่น");
                                }
                            } else {
                                self.say("สมาชิกปาร์ตี้ #b#e" + overLap
                                        + "#n#k ได้เข้าท้าทายไปแล้วในวันนี้ ถ้าอย่างนั้นวันนี้คงเข้าไม่ได้อีกแล้วล่ะครับ");
                            }
                        } else if (selection == 2) {
                            self.say("Pink Bean Raid Practice Mode is under construction!");
                        }
                    } else {
                        self.say(target.getParty().getPartyMemberList().size()
                                + "คน ทั้งหมดต้องอยู่ในแผนที่เดียวกันครับ");
                    }
                }
            }
        }
    }

    public void PinkBeen_before() {
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            if (eim.getProperty("summonNPC") == null) {
                eim.setProperty("summonNPC", "1");
                getPlayer().getMap().spawnMonster(MapleLifeFactory.getMonster(8820023), new Point(5, -42), -1);
                getPlayer().getMap().spawnMonster(MapleLifeFactory.getMonster(8820022), new Point(5, -42), -1);
                getPlayer().getMap().spawnMonster(MapleLifeFactory.getMonster(8820021), new Point(5, -42), -1);
                getPlayer().getMap().spawnMonster(MapleLifeFactory.getMonster(8820020), new Point(5, -42), -1);
                getPlayer().getMap().spawnMonster(MapleLifeFactory.getMonster(8820019), new Point(5, -42), -1);
                getPlayer().getMap().spawnNpc(2141000, new Point(-171, -48));
            }
        }
    }

    public void PinkBeen_Summon() {
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            if (target.getParty().getLeader().getId() == target.getId()) { // เฉพาะหัวหน้าปาร์ตี้เท่านั้นที่สามารถอัญเชิญได้
                                                                           // (ป้องกันการอัญเชิญซ้ำ)
                if (self.askAccept(
                        "ขอแค่มีกระจกแห่งเทพธิดา ก็จะสามารถอัญเชิญ Black Mage ได้อีกครั้ง!...\r\n, แปลกจัง... ทำไม Black Mage ถึงไม่ออกมานะ? พลังนี่มันอะไรกัน? มันต่างจากพลังของ Black Mage อย่างสิ้นเชิง... อ๊ากกก!\r\n\r\n#b(แตะที่ไหล่ของ Kirston)#k") == 1) {
                    if (eim.getProperty("summonMOB") == null) {
                        eim.setProperty("summonMOB", "1");
                        Field field = getPlayer().getMap();
                        List<Integer> spongeMob = null;
                        int spongeMobId = 8820014;

                        if (eim.getProperty("mode").equals("normal")) {
                            if (DBConfig.isGanglim || (!DBConfig.isGanglim && getPlayer().getPartyMemberSize() == 1)) {
                                field.spawnMonster(MapleLifeFactory.getMonster(8820008), new Point(5, -42), 1);
                                field.spawnMonster(MapleLifeFactory.getMonster(8820002), new Point(5, -42), -2);
                                field.spawnMonster(MapleLifeFactory.getMonster(8820003), new Point(5, -42), -2);
                                field.spawnMonster(MapleLifeFactory.getMonster(8820004), new Point(5, -42), -2);
                                field.spawnMonster(MapleLifeFactory.getMonster(8820005), new Point(5, -42), -2);
                                field.spawnMonster(MapleLifeFactory.getMonster(8820006), new Point(5, -42), -2);
                                field.spawnMonster(MapleLifeFactory.getMonster(8820014), new Point(5, -42), -2); // For
                                                                                                                 // Pink
                                                                                                                 // Bean
                                                                                                                 // Summon
                            } else {
                                int[] pinkbeanparts = { 8820008, 8820002, 8820003, 8820004, 8820005, 8820006, 8820014 };
                                for (int part : pinkbeanparts) {
                                    final MapleMonster pinkbeanpart = MapleLifeFactory.getMonster(part);
                                    pinkbeanpart.setPosition(new Point(5, -42));
                                    final long orghp = pinkbeanpart.getMobMaxHp();
                                    long fixedhp = orghp * 3L;
                                    if (fixedhp < 0) {
                                        fixedhp = Long.MAX_VALUE;
                                    }
                                    pinkbeanpart.setHp(fixedhp);
                                    pinkbeanpart.setMaxHp(fixedhp);
                                    if (part == 8820008) {
                                        field.spawnMonster(pinkbeanpart, 1);
                                    } else {
                                        field.spawnMonster(pinkbeanpart, -2);
                                    }
                                }
                            }
                            spongeMob = new ArrayList<>(Arrays.asList(8820002, 8820003, 8820004, 8820005, 8820006));
                        } else {
                            if (DBConfig.isGanglim || (!DBConfig.isGanglim && getPlayer().getPartyMemberSize() == 1)) {
                                field.spawnMonster(MapleLifeFactory.getMonster(8820108), new Point(5, -42), 1);
                                field.spawnMonster(MapleLifeFactory.getMonster(8820102), new Point(5, -42), -2);
                                field.spawnMonster(MapleLifeFactory.getMonster(8820103), new Point(5, -42), -2);
                                field.spawnMonster(MapleLifeFactory.getMonster(8820104), new Point(5, -42), -2);
                                field.spawnMonster(MapleLifeFactory.getMonster(8820105), new Point(5, -42), -2);
                                field.spawnMonster(MapleLifeFactory.getMonster(8820106), new Point(5, -42), -2);
                                field.spawnMonster(MapleLifeFactory.getMonster(8820114), new Point(5, -42), -2); // For
                                                                                                                 // Pink
                                                                                                                 // Bean
                                                                                                                 // Summon
                            } else {
                                int[] pinkbeanparts = { 8820108, 8820102, 8820103, 8820104, 8820105, 8820106, 8820114 };
                                for (int part : pinkbeanparts) {
                                    final MapleMonster pinkbeanpart = MapleLifeFactory.getMonster(part);
                                    pinkbeanpart.setPosition(new Point(5, -42));
                                    final long orghp = pinkbeanpart.getMobMaxHp();
                                    long fixedhp = orghp * 3L;
                                    if (fixedhp < 0) {
                                        fixedhp = Long.MAX_VALUE;
                                    }
                                    pinkbeanpart.setHp(fixedhp);
                                    pinkbeanpart.setMaxHp(fixedhp);
                                    if (part == 8820108) {
                                        field.spawnMonster(pinkbeanpart, 1);
                                    } else {
                                        field.spawnMonster(pinkbeanpart, -2);
                                    }
                                }
                            }
                            spongeMob = new ArrayList<>(Arrays.asList(8820102, 8820103, 8820104, 8820105, 8820106));
                            spongeMobId = 8820114;
                        }
                        for (MapleMonster mob : field.getAllMonstersThreadsafe()) {
                            if (spongeMob.contains(mob.getId())) {
                                mob.setSponge(getPlayer().getMap().getMonsterById(spongeMobId));
                            }
                        }

                        field.killMonster(field.getMonsterById(8820023)); // Dead Statues
                        field.killMonster(field.getMonsterById(8820022));
                        field.killMonster(field.getMonsterById(8820021));
                        field.killMonster(field.getMonsterById(8820020));
                        field.killMonster(field.getMonsterById(8820019));

                        field.removeNpc(2141000);
                    }
                }
            }
        }
    }

    public void PinkBeen_Out() {
        if (self.askYesNo(
                "ต้องการหยุดการต่อสู้และออกไปข้างนอกหรือไม่?\r\n#r#e※คำเตือน : หากออกไปแล้ว ท่านจะไม่สามารถเข้าท้าทาย Pink Bean ได้อีกในวันนี้#n#k") == 1) {
            List<Integer> normalMap = new ArrayList(Arrays.asList(270050100, 270050101, 270050102, 270050103, 270050104,
                    270050105, 270050106, 270050107, 270050108, 270050109));
            // 270050300(Normal Pink Bean Map Exit)
            // 270051300(Chaos Pink Bean Map Exit)
            getPlayer().setRegisterTransferFieldTime(0);
            getPlayer().setRegisterTransferField(0);
            if (normalMap.contains(target.getMapId())) { // Normal Map Exit
                registerTransferField(270050300);
            } else {
                registerTransferField(270051300);
            }
            if (getPlayer().getEventInstance() != null) {
                getPlayer().getEventInstance().unregisterPlayer(getPlayer());
                getPlayer().setEventInstance(null);
            }
        } else {
            self.say("กรุณาพยายามต่อไปนะครับ");
        }
    }
}
