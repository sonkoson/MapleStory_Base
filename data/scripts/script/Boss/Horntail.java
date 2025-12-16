package script.Boss;

import constants.QuestExConstants;
import database.DBConfig;
import objects.fields.Field;
import objects.fields.gameobject.Reactor;
import objects.fields.gameobject.lifes.ChangeableStats;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.fields.gameobject.lifes.MapleMonster;
import objects.users.MapleCharacter;
import scripting.EventInstanceManager;
import scripting.EventManager;
import scripting.newscripting.ScriptEngineNPC;

import java.awt.*;

public class Horntail extends ScriptEngineNPC {

    public void hontale_enterToE() { // Horntail really? ;;
        if (target.getParty() == null) {
            self.say("กรุณาให้หัวหน้าปาร์ตี้เป็นผู้ดำเนินการครับ");
        } else {
            if (target.getParty().getLeader().getId() != target.getId()) {
                self.say("กรุณาให้หัวหน้าปาร์ตี้เป็นผู้ดำเนินการครับ");
            } else {
                if (self.askYesNo(
                        "ตัวอักษรบนแผ่นหินเปล่งแสงออกมา และประตูเล็กๆ ด้านหลังแผ่นหินก็เปิดออก คุณต้องการใช้ทางลับนี้หรือไม่?") == 1) {
                    if (target.getParty().isPartySameMap()) {
                        target.getParty().registerTransferField(240050400); // All party members warp!
                    } else {
                        self.say(target.getParty().getPartyMemberList().size()
                                + "คน ทั้งหมดต้องอยู่ในแผนที่เดียวกันครับ");
                    }
                } else {
                    self.say("หากต้องการจะย้ายแผนที่ กรุณาคุยกับฉันใหม่อีกครั้งครับ");
                }
            }
        }
    }

    public void hontale_accept() {
        // Easy, Normal Horntail Start Map (240060000, 240060010, 240060020, 240060030,
        // 240060040, 240060050, 240060060, 240060070)
        // Chaos Horntail Start Map (240060001, 240060011, 240060021, 240060031,
        // 240060041, 240060051, 240060061, 240060071)
        int[] normalHortailMaps = new int[] { 240060000, 240060010, 240060020, 240060030, 240060040, 240060050,
                240060060, 240060070 };
        int[] chaosHortailMaps = new int[] { 240060001, 240060011, 240060021, 240060031, 240060041, 240060051,
                240060061, 240060071 };
        EventManager em = getEventManager("Horntail");
        if (em == null) {
            self.say("ขณะนี้ไม่สามารถเข้าร่วม Horntail Boss Raid ได้ครับ");
        } else {
            int v = self.askMenu(
                    "#e<บอส: Horntail>#n\r\nHorntail ฟื้นคืนชีพขึ้นมาแล้ว หากปล่อยไว้แบบนี้ มันจะทำให้ภูเขาไฟระเบิดและเปลี่ยน Minar Forest ให้กลายเป็นนรก\r\n#b\r\n#L0# สมัครเข้าสู่ <บอส: Horntail>#l");
            if (v == 0) {
                String menu = "";

                if (DBConfig.isGanglim) {
                    menu = "#e<บอส: Horntail>#n\r\nกรุณาเลือกโหมดที่ต้องการ\r\n\r\n#L0# Easy Mode (เลเวล 130 ขึ้นไป)#l\r\n#L1# Normal Mode (เลเวล 130 ขึ้นไป)#l\r\n#L2# Chaos Mode (เลเวล 135 ขึ้นไป)#l";
                } else {
                    boolean single = getPlayer().getPartyMemberSize() == 1;
                    menu = "#e<บอส: Horntail>#n\r\nกรุณาเลือกโหมดที่ต้องการ\r\n\r\n"
                            + "#L0# Easy Mode " + (single ? "(Single)" : "(Multi)") + " (เลเวล 130 ขึ้นไป)#l\r\n"
                            + "#L1# Normal Mode " + (single ? "(Single)" : "(Multi)") + "(เลเวล 130 ขึ้นไป)#l\r\n"
                            + "#L2# Chaos Mode " + (single ? "(Single)" : "(Multi)") + " (เลเวล 135 ขึ้นไป)#l\r\n";
                    int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.DailyQuestResetCount.getQuestID(),
                            "Horntail" + (single ? "Single" : "Multi"));
                    menu += "#L3#เพิ่มจำนวนการเข้า (" + ((single ? 2 : 1) - reset) + " ครั้ง)#l";
                }
                int v2 = self.askMenu(menu);

                if (target.getParty() == null) {
                    self.say("กรุณาให้หัวหน้าปาร์ตี้เป็นผู้ดำเนินการครับ");
                    return;
                } else {
                    if (v2 == 3 && !DBConfig.isGanglim) {
                        boolean single = getPlayer().getPartyMemberSize() == 1;
                        if (getPlayer().getTogetherPoint() < 150) {
                            self.sayOk("คะแนนความร่วมมือไม่เพียงพอ มีคะแนน : " + getPlayer().getTogetherPoint());
                            return;
                        }
                        int reset = getPlayer().getOneInfoQuestInteger(
                                QuestExConstants.DailyQuestResetCount.getQuestID(),
                                "Horntail" + (single ? "Single" : "Multi"));
                        if (reset > (single ? 1 : 0)) {
                            self.sayOk("วันนี้ไม่สามารถเพิ่มจำนวนการเข้าได้อีกแล้วครับ");
                            return;
                        }
                        getPlayer().gainTogetherPoint(-150);
                        getPlayer().updateOneInfo(QuestExConstants.DailyQuestResetCount.getQuestID(),
                                "Horntail" + (single ? "Single" : "Multi"), String.valueOf(reset + 1));
                        self.sayOk("เพิ่มจำนวนการเข้าเรียบร้อยแล้วครับ");
                        return;
                    }

                    if (target.getParty().getLeader().getId() != target.getId()) {
                        self.say("กรุณาให้หัวหน้าปาร์ตี้เป็นผู้ดำเนินการครับ");
                    } else {
                        if (target.getParty().isPartySameMap()) {
                            // ตรวจสอบว่ามีใครอยู่ข้างในหรือไม่
                            int[] fields = new int[] {};
                            int map = normalHortailMaps[0];
                            if (v2 == 2) {
                                map = chaosHortailMaps[0];
                            }
                            if (v2 == 0 || v2 == 1) {
                                fields = new int[] { map, map + 100, map + 200, map + 300 };
                            } else if (v2 == 2) {
                                fields = new int[] { map, map + 100, map + 200 };
                            }
                            boolean findUser = false;
                            for (int field : fields) {
                                Field fie = getClient().getChannelServer().getMapFactory().getMap(field);
                                if (fie.getCharactersSize() > 0) {
                                    findUser = true;
                                }
                            }
                            if (findUser) {
                                if (v2 == 0 || v2 == 1) {
                                    self.say("ขณะนี้แผนที่ Easy, Normal เต็มแล้วครับ กรุณาใช้แชนแนลอื่น");
                                } else if (v2 == 2) {
                                    self.say("ขณะนี้แผนที่ Chaos เต็มแล้วครับ กรุณาใช้แชนแนลอื่น");
                                }
                                return;
                            }

                            int canEnter = -1;
                            String overLap = checkEventNumber(getPlayer(), QuestExConstants.Horntail.getQuestID(),
                                    DBConfig.isGanglim);
                            if (overLap != null) {
                                v2 = 3;
                                canEnter = -2;
                            }
                            String mode = "easy";
                            if (v2 == 0 || v2 == 1) { // Easy, Normal Mode
                                if (em.getProperty("status0").equals("0")) {
                                    em.setProperty("status0", "1");
                                    canEnter = 0;
                                    if (v2 == 1) {
                                        mode = "normal";
                                    }
                                }
                            } else if (v2 == 2) { // Chaos Mode
                                em.setProperty("Cstatus0", "0");
                                if (em.getProperty("Cstatus0").equals("0")) {
                                    em.setProperty("Cstatus0", "1");
                                    canEnter = 0;
                                    mode = "chaos";
                                }
                            }

                            if (canEnter == -1 || canEnter == -2) {
                                if (canEnter == -2) {
                                    self.say("สมาชิกปาร์ตี้ #b#e" + overLap
                                            + "#n#k ได้เข้าท้าทายไปแล้วในวันนี้ ถ้าอย่างนั้นวันนี้คงเข้าไม่ได้อีกแล้วล่ะครับ");
                                } else {
                                    self.say("ขณะนี้แผนที่ทั้งหมดเต็มแล้วครับ กรุณาใช้แชนแนลอื่น");
                                }
                            } else if (canEnter == 0) {
                                EventInstanceManager eim = em.readyInstance();
                                eim.setProperty("map", map);
                                eim.setProperty("mode", mode);
                                for (int field : fields) {
                                    Field fie = getClient().getChannelServer().getMapFactory().getMap(field);
                                    fie.resetFully(false);
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
                                updateEventNumber(getPlayer(), QuestExConstants.Horntail.getQuestID());
                                eim.registerParty(target.getParty(), getPlayer().getMap());
                            }
                        } else { // กรณีที่สมาชิกปาร์ตี้ไม่ได้อยู่ในแผนที่เดียวกัน
                            self.say(target.getParty().getPartyMemberList().size()
                                    + "คน ทั้งหมดต้องอยู่ในแผนที่เดียวกันครับ");
                        }
                    }
                }
            }
        }
    }

    public void hontale_out() {
        if (target.getMapId() == 240050400) {
            if (self.askYesNo("ต้องการกลับไปยัง #m240050000# หรือไม่?") == 1) {
                registerTransferField(240050000);
            } else {
                self.say("กรุณาลองคิดดูใหม่แล้วค่อยมาคุยกันครับ");
            }
        } else {
            if (self.askYesNo(
                    "ต้องการเลิกต่อสู้และออกไปข้างนอกหรือไม่? หากออกไปแล้ว วันนี้จะไม่สามารถเข้าได้อีก") == 1) {
                // Deduct count!
                registerTransferField(240050400);
            } else {
                self.say("กรุณาลองคิดดูใหม่แล้วค่อยมาคุยกันครับ");
            }
        }
    }

    public void hontale_boss1() {
        EventInstanceManager eim = getPlayer().getEventInstance();
        if (eim != null) {
            if (eim.getProperty("boss1") == null) {
                eim.setProperty("boss1", "1");
                Reactor tremble = getPlayer().getMap().getReactorByName("tremble1");
                if (tremble != null) {
                    if (DBConfig.isGanglim) {
                        int mobId = 8810200;
                        if (eim.getProperty("mode").equals("normal")) {
                            mobId = 8810000;
                        } else if (eim.getProperty("mode").equals("chaos")) { // Chaos Mode
                            mobId = 8810100;
                        }
                        tremble.getMap().spawnMonster(MapleLifeFactory.getMonster(mobId),
                                new Point(tremble.getPosition().x - 90, tremble.getPosition().y + 5), -2); // Like
                                                                                                           // official
                                                                                                           // server
                                                                                                           // haha

                        mapMessage(6, "สิ่งมีชีวิตที่น่าสะพรึงกลัวปรากฏตัวขึ้นจากส่วนลึกของถ้ำ");
                        tremble.forceHitReactor((byte) 0);
                        tremble.forceHitReactor((byte) 1);
                    } else {
                        if (getPlayer().getPartyMemberSize() == 1) {
                            int mobId = 8810200;
                            if (eim.getProperty("mode").equals("normal")) {
                                mobId = 8810000;
                            } else if (eim.getProperty("mode").equals("chaos")) { // Chaos Mode
                                mobId = 8810100;
                            }
                            tremble.getMap().spawnMonster(MapleLifeFactory.getMonster(mobId),
                                    new Point(tremble.getPosition().x - 90, tremble.getPosition().y + 5), -2); // Like
                                                                                                               // official
                                                                                                               // server
                                                                                                               // haha

                            mapMessage(6, "สิ่งมีชีวิตที่น่าสะพรึงกลัวปรากฏตัวขึ้นจากส่วนลึกของถ้ำ");
                            tremble.forceHitReactor((byte) 0);
                            tremble.forceHitReactor((byte) 1);
                        } else {
                            int mobId = 8810200;
                            if (eim.getProperty("mode").equals("normal")) {
                                mobId = 8810000;
                            } else if (eim.getProperty("mode").equals("chaos")) { // Chaos Mode
                                mobId = 8810100;
                            }
                            final MapleMonster horntail = MapleLifeFactory.getMonster(mobId);
                            horntail.setPosition(new Point(tremble.getPosition().x - 90, tremble.getPosition().y + 5));
                            final long orghp = horntail.getMobMaxHp();
                            ChangeableStats cs = new ChangeableStats(horntail.getStats());
                            cs.hp = orghp * 3L;
                            if (cs.hp < 0) {
                                cs.hp = Long.MAX_VALUE;
                            }
                            horntail.getStats().setHp(cs.hp);
                            horntail.getStats().setMaxHp(cs.hp);
                            horntail.setOverrideStats(cs);
                            tremble.getMap().spawnMonster(horntail, -2);
                            mapMessage(6, "สิ่งมีชีวิตที่น่าสะพรึงกลัวปรากฏตัวขึ้นจากส่วนลึกของถ้ำ");
                            tremble.forceHitReactor((byte) 0);
                            tremble.forceHitReactor((byte) 1);
                        }
                    }
                }
            }
        }
    }

    // ...

    public void hontale_BR() {
        EventInstanceManager eim = getPlayer().getEventInstance();
        if (eim != null) {
            int enMap = 240060000;
            int chaosMap = 240060001;
            if (eim.getProperty("stage1") != null && (target.getMapId() == enMap || target.getMapId() == chaosMap)) { // Stage
                                                                                                                      // 1
                playPortalSE();
                getPlayer().changeMap(target.getMapId() + 100);
            } else if (eim.getProperty("stage2") != null
                    && (target.getMapId() == enMap + 100 || target.getMapId() == chaosMap + 100)) {
                playPortalSE();
                if (eim.getProperty("mode").equals("easy")) {
                    getPlayer().changeMap(target.getMapId() + 200);
                } else {
                    getPlayer().changeMap(target.getMapId() + 100);
                }
            } else {
                getPlayer().dropMessage(5, "ยังไม่สามารถใช้พอร์ทัลได้");
            }
        }
    }
}
