package script.Boss;

import constants.QuestExConstants;
import database.DBConfig;
import objects.fields.Field;
import objects.fields.gameobject.lifes.ChangeableStats;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.fields.gameobject.lifes.MapleMonster;
import objects.users.MapleCharacter;
import scripting.EventInstanceManager;
import scripting.EventManager;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

import java.awt.*;

public class Cygnus extends ScriptEngineNPC {

    public void in_cygnus() {
        // Instance Maps 271040300, 271041300 (Small Garden)
        initNPC(MapleLifeFactory.getNPC(2143004));
        if (DBConfig.isGanglim) {
            getPlayer().dropMessage(5, "ไม่สามารถเข้าสู่ Corrupted Cygnus ได้ในขณะนี้");
            return;
        }
        EventManager em = getEventManager("Cygnus");
        if (em == null) {
            self.say("ขณะนี้ไม่สามารถเข้าร่วม Cygnus Raid ได้ครับ");
        } else {
            if (target.getMapId() >= 271040100 && target.getMapId() <= 271040199) { // Normal Cygnus Battle Map
                int v0 = self.askYesNo("ต้องการจบการต่อสู้และออกไปข้างนอกหรือไม่?");
                if (v0 == 1) {
                    registerTransferField(271040200); // Normal Cygnus Exit Map
                }
            } else if (target.getMapId() >= 271041100 && target.getMapId() <= 271041109) { // Easy Cygnus
                int v0 = self.askYesNo("ต้องการจบการต่อสู้และออกไปข้างนอกหรือไม่?");
                if (v0 == 1) {
                    registerTransferField(271041200); // Easy Cygnus Exit Map
                }
            } else if (target.getMapId() == 271040000 || target.getMapId() == 271041000) { // Entry Map
                if (target.getParty() == null) {
                    self.say("ต้องอยู่ในปาร์ตี้ตั้งแต่ 1 คนขึ้นไปถึงจะเข้าได้ครับ");
                } else {
                    if (DBConfig.isGanglim && getPlayer().getParty().getLeader().getId() != target.getId()) {
                        self.say("กรุณาให้หัวหน้าปาร์ตี้เป็นผู้ดำเนินการครับ");
                    } else {
                        boolean normalCygnus = target.getMapId() == 271040000;
                        if (!normalCygnus && DBConfig.isGanglim) {
                            getPlayer().dropMessage(5, "ไม่สามารถเข้าสู่ Corrupted Cygnus (Easy) ได้ในขณะนี้");
                            return;
                        }
                        String v = "";
                        if (DBConfig.isGanglim) {
                            v = "พร้อมที่จะเผชิญหน้ากับ Corrupted Cygnus (Easy) หรือยัง?\r\n#b\r\n#L0# สมัครเข้าสู่ Cygnus (Easy)#l\r\n#L1# สมัครเข้าสู่ Cygnus Practice Mode (Easy)#l";
                            if (normalCygnus) {
                                v = "พร้อมที่จะเผชิญหน้ากับ Corrupted Cygnus หรือยัง?\r\n#b\r\n#L0# สมัครเข้าสู่ Cygnus (Normal)#l\r\n#L1# สมัครเข้าสู่ Cygnus Practice Mode (Normal)#l";
                            }
                        } else {
                            boolean single = getPlayer().getPartyMemberSize() == 1;
                            int reset = getPlayer().getOneInfoQuestInteger(
                                    QuestExConstants.DailyQuestResetCount.getQuestID(),
                                    "Cygnus" + (single ? "Single" : "Multi"));
                            v = "พร้อมที่จะเผชิญหน้ากับ Corrupted Cygnus (Easy) หรือยัง?\r\n#b\r\n"
                                    + "#L0# สมัครเข้าสู่ Cygnus (Easy) " + (single ? "(Single)" : "(Multi)") + "#l\r\n"
                                    + "#L1# สมัครเข้าสู่ Cygnus Practice Mode (Easy) "
                                    + (single ? "(Single)" : "(Multi)") + "#l\r\n";
                            if (normalCygnus) {
                                v = "พร้อมที่จะเผชิญหน้ากับ Corrupted Cygnus หรือยัง?\r\n#b\r\n"
                                        + "#L0# สมัครเข้าสู่ Cygnus (Normal) " + (single ? "(Single)" : "(Multi)")
                                        + "#l\r\n"
                                        + "#L1# สมัครเข้าสู่ Cygnus Practice Mode (Normal) "
                                        + (single ? "(Single)" : "(Multi)") + "#l\r\n";
                            }
                        }
                        int v0 = self.askMenu(v);
                        if (target.getParty().isPartySameMap()) {
                            boolean canEnter = false;
                            String overLap = checkEventNumber(getPlayer(), QuestExConstants.Cygnus.getQuestID());
                            // Cygnus is cleared on defeat!!
                            if (overLap == null && v0 != 1) {
                                String lastDate = checkEventLastDate(getPlayer(), QuestExConstants.Cygnus.getQuestID());
                                if (lastDate == null) {
                                    if (v0 == 0) {
                                        // 271040000 ~ 271040199 (Why use 199 maps??)
                                        // 271041100 ~ 271041109 (Normal Cygnus Map)
                                        int instanceMapID = 271041100; // Easy Cygnus Battle Map
                                        if (target.getMapId() == 271040000) { // Normal Cygnus Map
                                            instanceMapID = 271040100;
                                        }
                                        String mode = "easy";
                                        if (instanceMapID == 271040100) {
                                            if (em.getProperty("status0").equals("0")) {
                                                canEnter = true;
                                            }
                                        } else {
                                            if (em.getProperty("Nstatus0").equals("0")) {
                                                mode = "normal";
                                                canEnter = true;
                                            }
                                        }
                                        if (!canEnter) { // If entry not possible (check if map empty then reset
                                                         // instance)
                                            if (getClient().getChannelServer().getMapFactory().getMap(instanceMapID)
                                                    .getCharacters().size() == 0) {
                                                String rt = em.getProperty("ResetTime");
                                                long curTime = System.currentTimeMillis();
                                                long time = rt == null ? 0 : Long.parseLong(rt);
                                                if (time == 0) {
                                                    em.setProperty("ResetTime", String.valueOf(curTime));
                                                } else if (time - curTime >= 10000) { // If map empty for 10+ seconds
                                                                                      // Make entry possible
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
                                            eim.setProperty("map", instanceMapID);
                                            eim.setProperty("mode", mode);
                                            getClient().getChannelServer().getMapFactory().getMap(instanceMapID)
                                                    .resetFully(false);
                                            boolean single = getPlayer().getPartyMemberSize() == 1;
                                            if (!DBConfig.isGanglim && !single) {
                                                for (MapleCharacter partyMember : getPlayer().getPartyMembers()) {
                                                    if (partyMember.getMapId() == getPlayer().getMapId()) {
                                                        partyMember.setMultiMode(true);
                                                        partyMember.applyBMCurseJinMulti();
                                                    }
                                                }
                                            }
                                            updateLastDate(getPlayer(), QuestExConstants.Cygnus.getQuestID());
                                            eim.registerParty(target.getParty(), getPlayer().getMap());
                                        } else {
                                            self.sayOk(
                                                    "ขณะนี้อินสแตนซ์ทั้งหมดเต็มแล้ว ไม่สามารถเข้าใช้งานได้ กรุณาใช้แชนแนลอื่น");
                                        }
                                    }
                                } else {
                                    self.say("สมาชิกปาร์ตี้ #b#e" + lastDate
                                            + " #n#kจะสามารถเข้าได้อีกครั้งในภายหลังครับ");// Official: Party member
                                                                                           // entered within 30 mins.
                                                                                           // Re-entry disabled for 30
                                                                                           // mins.
                                }
                            } else {
                                if (v0 == 1) {
                                    self.say("โหมดฝึกซ้อมกำลังอยู่ระหว่างการเตรียมการ");
                                } else {
                                    self.say(
                                            "มีสมาชิกปาร์ตี้ที่เคลียร์ Cygnus ไปแล้วในสัปดาห์นี้ Cygnus (Easy) และ Cygnus (Normal) สามารถเคลียร์ได้รวมกันสัปดาห์ละ 1 ครั้ง\r\n#r#e<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>#k#n");
                                }
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

    public void cygnus_accept() {
        if (!DBConfig.isGanglim) {
            in_cygnus();
            return;
        }
        // 인스턴스แผนที่ 271040300, 271041300(볼품없는정원)
        if (DBConfig.isGanglim) {
            getPlayer().dropMessage(5, "ไม่สามารถเข้าสู่ Corrupted Cygnus ได้ในขณะนี้");
            return;
        }
        EventManager em = getEventManager("Cygnus");
        if (em == null) {
            self.say("ขณะนี้ไม่สามารถเข้าร่วม Cygnus Raid ได้ครับ");
        } else {
            if (target.getMapId() >= 271040100 && target.getMapId() <= 271040199) { // Normal Cygnus Battle Map
                int v0 = self.askYesNo("ต้องการจบการต่อสู้และออกไปข้างนอกหรือไม่?");
                if (v0 == 1) {
                    registerTransferField(271040200); // Normal Cygnus Exit Map
                }
            } else if (target.getMapId() >= 271041100 && target.getMapId() <= 271041109) { // Easy Cygnus
                int v0 = self.askYesNo("ต้องการจบการต่อสู้และออกไปข้างนอกหรือไม่?");
                if (v0 == 1) {
                    registerTransferField(271041200); // Easy Cygnus Exit Map
                }
            } else if (target.getMapId() == 271040000 || target.getMapId() == 271041000) { // Entry Map
                if (target.getParty() == null) {
                    self.say("ต้องอยู่ในปาร์ตี้ตั้งแต่ 1 คนขึ้นไปถึงจะเข้าได้ครับ");
                } else {
                    if (target.getParty().getLeader().getId() != target.getId()) {
                        self.say("กรุณาให้หัวหน้าปาร์ตี้เป็นผู้ดำเนินการครับ");
                    } else {
                        boolean normalCygnus = target.getMapId() == 271040000;
                        if (!normalCygnus && DBConfig.isGanglim) {
                            getPlayer().dropMessage(5, "ไม่สามารถเข้าสู่ Corrupted Cygnus (Easy) ได้ในขณะนี้");
                            return;
                        }
                        String v = "พร้อมที่จะเผชิญหน้ากับ Corrupted Cygnus (Easy) หรือยัง?\r\n#b\r\n#L0# สมัครเข้าสู่ Cygnus (Easy)#l\r\n#L1# สมัครเข้าสู่ Cygnus Practice Mode (Easy)#l";
                        if (normalCygnus) {
                            v = "พร้อมที่จะเผชิญหน้ากับ Corrupted Cygnus หรือยัง?\r\n#b\r\n#L0# สมัครเข้าสู่ Cygnus (Normal)#l\r\n#L1# สมัครเข้าสู่ Cygnus Practice Mode (Normal)#l";
                        }
                        int v0 = self.askMenu(v);
                        if (target.getParty().isPartySameMap()) {
                            boolean canEnter = false;
                            String overLap = checkEventNumber(getPlayer(), QuestExConstants.Cygnus.getQuestID()); // 시เขา너스는
                                                                                                                  // 격파시
                                                                                                                  // 클리어ประมวลผล됨!!
                            if (overLap == null && v0 != 1) {
                                String lastDate = checkEventLastDate(getPlayer(), QuestExConstants.Cygnus.getQuestID());
                                if (lastDate == null) {
                                    if (v0 == 0) {
                                        // 271040000 ~ 271040199 (무슨 แผนที่ 199개ฉัน쓰냐;;)
                                        // 271041100 ~ 271041109 (노말시เขา แผนที่)
                                        int instanceMapID = 271041100; // 이지시เขา 전투แผนที่
                                        if (target.getMapId() == 271040000) { // 노말시เขาแผนที่
                                            instanceMapID = 271040100;
                                        }
                                        String mode = "easy";
                                        if (instanceMapID == 271040100) {
                                            if (em.getProperty("status0").equals("0")) {
                                                canEnter = true;
                                            }
                                        } else {
                                            if (em.getProperty("Nstatus0").equals("0")) {
                                                mode = "normal";
                                                canEnter = true;
                                            }
                                        }
                                        if (!canEnter) { // เข้า이 불เป็นไปได้한 경우 แผนที่ 유ฉัน가 없는지 체크 후 인스턴스 วินาที기화
                                            if (getClient().getChannelServer().getMapFactory().getMap(instanceMapID)
                                                    .getCharacters().size() == 0) {
                                                String rt = em.getProperty("ResetTime");
                                                long curTime = System.currentTimeMillis();
                                                long time = rt == null ? 0 : Long.parseLong(rt);
                                                if (time == 0) {
                                                    em.setProperty("ResetTime", String.valueOf(curTime));
                                                } else if (time - curTime >= 10000) { // 10วินาที이상 แผนที่ 빈경우
                                                                                      // เข้าเป็นไปได้하게 เปลี่ยน
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
                                            eim.setProperty("map", instanceMapID);
                                            eim.setProperty("mode", mode);
                                            getClient().getChannelServer().getMapFactory().getMap(instanceMapID)
                                                    .resetFully(false);
                                            updateLastDate(getPlayer(), QuestExConstants.Cygnus.getQuestID());
                                            eim.registerParty(target.getParty(), getPlayer().getMap());
                                        } else {
                                            self.sayOk(
                                                    "ขณะนี้อินสแตนซ์ทั้งหมดเต็มแล้ว ไม่สามารถเข้าใช้งานได้ กรุณาใช้แชนแนลอื่น");
                                        }
                                    }
                                } else {
                                    self.say("สมาชิกปาร์ตี้ #b#e" + lastDate
                                            + " #n#kจะสามารถเข้าได้อีกครั้งในภายหลังครับ");// Official: Party member
                                                                                           // entered within 30 mins.
                                                                                           // Re-entry disabled for 30
                                                                                           // mins.
                                }
                            } else {
                                if (v0 == 1) {
                                    self.say("โหมดฝึกซ้อมกำลังอยู่ระหว่างการเตรียมการ");
                                } else {
                                    self.say(
                                            "มีสมาชิกปาร์ตี้ที่เคลียร์ Cygnus ไปแล้วในสัปดาห์นี้ Cygnus (Easy) และ Cygnus (Normal) สามารถเคลียร์ได้รวมกันสัปดาห์ละ 1 ครั้ง\r\n#r#e<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>#k#n");
                                }
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

    public void in_cygnusGarden() {
        initNPC(MapleLifeFactory.getNPC(2143004));
        int v0 = target.askMenu(
                "#r#eต้องการเข้าไปยัง Cygnus Garden หรือไม่?#b\r\n#L0#ย้ายไปเพื่อกำจัด Cygnus (Normal)#l",
                ScriptMessageFlag.Self);
        if (v0 == 0) { // TODO Check Cygnus Garden key
            getPlayer().dropMessage(5, "ย้ายไปยัง Cygnus Garden");
            registerTransferField(271040000);
        }
    }

    public void cygnus_Summon_Easy() {
        // 8850011 - 노말시เขา (8850012) 소환용 더미
        // 8850111 - 이지시เขา (8850112) 소환용 더미
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            if (eim.getProperty("summonMOB") == null) {
                eim.setProperty("summonMOB", "1");
                Field field = getPlayer().getMap();
                if (DBConfig.isGanglim || (!DBConfig.isGanglim && !getPlayer().isMultiMode())) {
                    field.spawnMonster(MapleLifeFactory.getMonster(8850112), new Point(-160, -65), 1);
                } else {
                    final MapleMonster cygnus = MapleLifeFactory.getMonster(8850112);
                    cygnus.setPosition(new Point(-160, -65));
                    final long hp = cygnus.getMobMaxHp();
                    long fixedhp = hp * 3L;
                    if (fixedhp < 0) {
                        fixedhp = Long.MAX_VALUE;
                    }
                    cygnus.setHp(fixedhp);
                    cygnus.setMaxHp(fixedhp);
                    field.spawnMonster(cygnus, new Point(-160, -65), 1);
                }
                eim.getMapInstance(getPlayer().getMapId())
                        .startMapEffect("ไม่ได้เห็นคนที่มาที่นี่นานแล้วนะ... แต่ก็ไม่มีใครรอดกลับไปได้หรอก", 5120043);
            }
        }
    }

    public void cygnus_Summon() {
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            if (eim.getProperty("summonMOB") == null) {
                eim.setProperty("summonMOB", "1");
                Field field = getPlayer().getMap();
                if (DBConfig.isGanglim || (!DBConfig.isGanglim && !getPlayer().isMultiMode())) {
                    field.spawnMonster(MapleLifeFactory.getMonster(8850012), new Point(-160, -65), 1);
                } else {
                    final MapleMonster cygnus = MapleLifeFactory.getMonster(8850012);
                    cygnus.setPosition(new Point(-160, -65));
                    final long hp = cygnus.getMobMaxHp();
                    long fixedhp = hp * 3L;
                    if (fixedhp < 0) {
                        fixedhp = Long.MAX_VALUE;
                    }
                    cygnus.setHp(fixedhp);
                    cygnus.setMaxHp(fixedhp);
                    field.spawnMonster(cygnus, new Point(-160, -65), 1);
                }
                eim.getMapInstance(getPlayer().getMapId())
                        .startMapEffect("ไม่ได้เห็นคนที่มาที่นี่นานแล้วนะ... แต่ก็ไม่มีใครรอดกลับไปได้หรอก", 5120043);
            }
        }
    }

    public void knights_Summon() {
        for (int i = 8610023; i <= 8610027; ++i) {
            if (DBConfig.isGanglim || (!DBConfig.isGanglim && !getPlayer().isMultiMode())) {
                MapleMonster mob = MapleLifeFactory.getMonster(i);
                getPlayer().getMap().spawnMonsterOnGroundBelow(mob, new Point(-551, 113));

                mob = MapleLifeFactory.getMonster(i);
                getPlayer().getMap().spawnMonsterOnGroundBelow(mob, new Point(-551, 113));
            } else {
                MapleMonster mob = MapleLifeFactory.getMonster(i);
                final long hp = mob.getMobMaxHp();
                long fixedhp = hp * 3L;
                if (fixedhp < 0) {
                    fixedhp = Long.MAX_VALUE;
                }
                mob.setHp(fixedhp);
                mob.setMaxHp(fixedhp);
                getPlayer().getMap().spawnMonsterOnGroundBelow(mob, new Point(-551, 113));

                mob = MapleLifeFactory.getMonster(i);
                mob.setHp(fixedhp);
                mob.setMaxHp(fixedhp);
                getPlayer().getMap().spawnMonsterOnGroundBelow(mob, new Point(-551, 113));
            }
        }
    }

    public void knights_Summon_Easy() {
        for (int i = 8610028; i <= 8610032; ++i) {
            if (DBConfig.isGanglim || (DBConfig.isGanglim && !getPlayer().isMultiMode())) {
                MapleMonster mob = MapleLifeFactory.getMonster(i);
                getPlayer().getMap().spawnMonsterOnGroundBelow(mob, new Point(-551, 113));

                mob = MapleLifeFactory.getMonster(i);
                getPlayer().getMap().spawnMonsterOnGroundBelow(mob, new Point(-551, 113));
            } else {
                MapleMonster mob = MapleLifeFactory.getMonster(i);
                final long hp = mob.getMobMaxHp();
                long fixedhp = hp * 3L;
                if (fixedhp < 0) {
                    fixedhp = Long.MAX_VALUE;
                }
                mob.setHp(fixedhp);
                mob.setMaxHp(fixedhp);
                getPlayer().getMap().spawnMonsterOnGroundBelow(mob, new Point(-551, 113));

                mob = MapleLifeFactory.getMonster(i);
                mob.setHp(fixedhp);
                mob.setMaxHp(fixedhp);
                getPlayer().getMap().spawnMonsterOnGroundBelow(mob, new Point(-551, 113));
            }
        }
    }

    public void back_cygnus_Easy() {
        // 이벤트 인스턴스 ยืนยัน해서 สถานการณ์에 맞게끔 แผนที่ย้าย시킬 것
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            if (getPlayer().getMap().getAllMonster().size() == 0) {
                registerTransferField(Integer.parseInt(eim.getProperty("map")));
            } else {
                getPlayer().dropMessage(5, "ต้องกำจัดอัศวินทั้งหมดก่อน จึงจะสามารถกลับไปยัง Cygnus Garden ได้");
            }
        }
    }

    public void back_cygnus() {
        // 이벤트 인스턴스 ยืนยัน해서 สถานการณ์에 맞게끔 แผนที่ย้าย시킬 것
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            if (getPlayer().getMap().getAllMonster().size() == 0) {
                registerTransferField(Integer.parseInt(eim.getProperty("map")));
            } else {
                getPlayer().dropMessage(5, "ต้องกำจัดอัศวินทั้งหมดก่อน จึงจะสามารถกลับไปยัง Cygnus Garden ได้");
            }
        }
    }

    public void out_cygnusBackGarden() {
        // 이벤트 인스턴스 ยืนยัน해서 สถานการณ์에 맞게끔 แผนที่ย้าย시킬 것
        registerTransferField(100000000);
    }

    public void out_cygnusBackGardenEasy() {
        // 이벤트 인스턴스 ยืนยัน해서 สถานการณ์에 맞게끔 แผนที่ย้าย시킬 것(이지โหมด)
        registerTransferField(100000000);
    }
}
