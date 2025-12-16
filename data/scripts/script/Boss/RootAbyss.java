package script.Boss;

import constants.QuestExConstants;
import database.DBConfig;
import network.models.CField;
import network.models.CWvsContext;
import objects.effect.child.PlayMusicDown;
import objects.fields.Field;
import objects.fields.Portal;
import objects.fields.gameobject.lifes.ChangeableStats;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.fields.gameobject.lifes.MapleMonster;
import objects.users.MapleCharacter;
import scripting.EventInstanceManager;
import scripting.EventManager;
import scripting.newscripting.ScriptEngineNPC;

import java.awt.*;

public class RootAbyss extends ScriptEngineNPC {

    public void rootafirstDoor() { // Pierre
        // 30 min re-entry
        // 105200200 West Garden (Normal)
        // 105200210 Pierre Boss Map
        // 105200600 West Garden <Chaos>
        // 105200610 Pierre Boss Map <Chaos>
        initNPC(MapleLifeFactory.getNPC(1064012));
        EventManager em = getEventManager("RootAbyssPierre");
        if (em == null) {
            self.say("ไม่สามารถเข้าได้ในขณะนี้");
        } else {
            String text = "";
            if (DBConfig.isGanglim) {
                text = "#r#e<ทางเข้า Root Abyss West Garden>#n#k\r\nประตูสู่สวนที่ #rPierre#k ผู้พิทักษ์แห่งทิศตะวันตกเฝ้ารักษาอยู่ ประวัติการเคลียร์จะรีเซ็ตทุกเที่ยงคืน";
                text += "#L0#ใช้ #i4033611##t4033611# เพื่อเข้าสู่ Normal Mode (เลเวล 125 ขึ้นไป)";
                text += " #r[" + getPlayer().getOneInfoQuestInteger(QuestExConstants.Pierre.getQuestID(), "eNum") + "/"
                        + (getPlayer().getBossTier() + 1) + "]#k";
                getPlayer().getPartyMembers()
                        .forEach(chr -> chr.dropMessage(5,
                                "[Normal Pierre] วันนี้เข้าสู้บอสไปแล้ว "
                                        + chr.getOneInfoQuestInteger(QuestExConstants.Pierre.getQuestID(), "eNum")
                                        + " ครั้ง สามารถเข้าได้ทั้งหมด " + (chr.getBossTier() + 1) + " ครั้ง"));
                text += "\r\n#L1#ใช้ #i4033611##t4033611# เพื่อเข้าสู่ Chaos Mode (เลเวล 180 ขึ้นไป)";
                text += " #r[" + getPlayer().getOneInfoQuestInteger(QuestExConstants.ChaosPierre.getQuestID(), "eNum")
                        + "/" + (getPlayer().getBossTier() + 1) + "]#b";
                getPlayer().getPartyMembers()
                        .forEach(chr -> chr.dropMessage(5,
                                "[Chaos Pierre] วันนี้เข้าสู้บอสไปแล้ว "
                                        + chr.getOneInfoQuestInteger(QuestExConstants.ChaosPierre.getQuestID(), "eNum")
                                        + " ครั้ง สามารถเข้าได้ทั้งหมด " + (chr.getBossTier() + 1) + " ครั้ง"));
                text += "#l\r\n#L3#เข้าสู่ Chaos Practice Mode (เลเวล 180 ขึ้นไป)#l";
            } else {
                boolean single = getPlayer().getPartyMemberSize() == 1;
                text = "#r#e<ทางเข้า Root Abyss West Garden>#n#k\r\n";
                text += "ประตูสู่สวนที่ #rPierre#k ผู้พิทักษ์แห่งทิศตะวันตกเฝ้ารักษาอยู่ \r\n#rประวัติการเคลียร์ Normal จะรีเซ็ตทุกเที่ยงคืน, Chaos จะรีเซ็ตทุกวันพฤหัสบดี#b";
                text += "#L0#ใช้ #i4033611##t4033611# เพื่อเข้าสู่ Normal Mode " + (single ? "(Single)" : "(Multi)")
                        + " (เลเวล 125 ขึ้นไป)";
                text += "\r\n#L1#ใช้ #i4033611##t4033611# เพื่อเข้าสู่ Chaos Mode " + (single ? "(Single)" : "(Multi)")
                        + " (เลเวล 180 ขึ้นไป)";
                int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.DailyQuestResetCount.getQuestID(),
                        "NormalPierre" + (single ? "Single" : "Multi"));
                text += "\r\n#L4#เพิ่มรอบเข้าเล่น Normal Mode " + (single ? "(Single)" : "(Multi)") + " (เหลือ "
                        + ((single ? 2 : 1) - reset) + " ครั้ง) #l";
            }

            int v0 = self.askMenu(text);
            if (target.getParty() == null) {
                target.say("ดูเหมือนจะต้องมีปาร์ตี้ก่อนนะ ไปหาปาร์ตี้กันเถอะ");
                return;
            } else {
                if (v0 == 4 && !DBConfig.isGanglim) {
                    if (getPlayer().getTogetherPoint() < 150) {
                        self.sayOk("คะแนนความร่วมมือไม่เพียงพอ คะแนนปัจจุบัน : " + getPlayer().getTogetherPoint());
                        return;
                    }
                    boolean single = getPlayer().getPartyMemberSize() == 1;
                    int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.DailyQuestResetCount.getQuestID(),
                            "NormalPierre" + (single ? "Single" : "Multi"));
                    if ((reset > 0 && !single) || reset > 1 && single) { // Cannot reset
                        self.sayOk("คุณใช้จำนวนครั้งที่สามารถเพิ่มได้ในวันนี้หมดแล้ว");
                        return;
                    }
                    getPlayer().gainTogetherPoint(-150);
                    getPlayer().updateOneInfo(QuestExConstants.DailyQuestResetCount.getQuestID(),
                            "NormalPierre" + (single ? "Single" : "Multi"), String.valueOf(reset + 1));
                    self.sayOk("เพิ่มจำนวนครั้งที่เข้าเล่นเรียบร้อยแล้ว");
                    return;
                }
                if (v0 == 0) { // Normal Mode
                    if (target.getParty().isPartySameMap()) {
                        if (em.getProperty("status0").equals("0")) {
                            String overLap = checkEventNumber(getPlayer(), QuestExConstants.Pierre.getQuestID(),
                                    DBConfig.isGanglim);
                            if (overLap == null) {
                                String lastDate = checkEventLastDate(getPlayer(), QuestExConstants.Pierre.getQuestID());
                                if (lastDate == null || DBConfig.isGanglim) {
                                    String exMember = target.exchangeParty(4033611, -1);
                                    if (exMember == null) { // Try entry
                                        em.setProperty("status0", "1");
                                        EventInstanceManager eim = em.readyInstance();
                                        eim.setProperty("map", 105200200);
                                        eim.setProperty("mode", "normal");
                                        getClient().getChannelServer().getMapFactory().getMap(105200200)
                                                .setLastRespawnTime(0);
                                        getClient().getChannelServer().getMapFactory().getMap(105200200)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200210)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200200)
                                                .setLastRespawnTime(Long.MAX_VALUE);

                                        updateLastDate(getPlayer(), QuestExConstants.Pierre.getQuestID());
                                        if (DBConfig.isGanglim) {
                                            updateQuestEx(getPlayer(), QuestExConstants.Pierre.getQuestID());
                                        }
                                        if (!DBConfig.isGanglim && getPlayer().getPartyMemberSize() > 1) {
                                            for (MapleCharacter partyMember : getPlayer().getPartyMembers()) {
                                                if (partyMember.getMapId() == getPlayer().getMapId()) {
                                                    partyMember.setMultiMode(true);
                                                    partyMember.applyBMCurseJinMulti();
                                                }
                                            }
                                        }
                                        eim.registerParty(target.getParty(), getPlayer().getMap());
                                    } else {
                                        target.say("สมาชิกในปาร์ตี้ #b" + exMember
                                                + "#k ไม่มี #i4033611##t4033611# จึงไม่สามารถเข้าได้");
                                    }
                                } else {
                                    target.say("สมาชิกในปาร์ตี้จะสามารถเข้าได้อีกครั้งในอีก #b#e" + lastDate + " #n#k");
                                }
                            } else {
                                target.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                        + "#n#k ได้ทำการท้าทายไปแล้วในวันนี้ จึงไม่สามารถเข้าได้");
                            }
                        } else {
                            self.say("แผนที่เต็ม ไม่สามารถใช้บริการได้ กรุณาลองใหม่ในแชนแนลอื่น");
                        }
                    } else {
                        self.say("สมาชิกปาร์ตี้ทุกคนต้องอยู่ในแผนที่เดียวกัน");
                    }
                } else if (v0 == 1) { // Chaos Mode
                    if (target.getParty().isPartySameMap()) {
                        if (em.getProperty("Cstatus0").equals("0")) {
                            String overLap = checkEventNumber(getPlayer(), QuestExConstants.ChaosPierre.getQuestID(),
                                    DBConfig.isGanglim);
                            if (overLap == null) {
                                String lastDate = checkEventLastDate(getPlayer(),
                                        QuestExConstants.ChaosPierre.getQuestID());
                                if (lastDate == null || DBConfig.isGanglim) {
                                    String exMember = target.exchangeParty(4033611, -1);
                                    if (exMember == null) { // Try entry
                                        em.setProperty("Cstatus0", "1");
                                        EventInstanceManager eim = em.readyInstance();
                                        eim.setProperty("map", 105200600);
                                        eim.setProperty("mode", "chaos");
                                        getClient().getChannelServer().getMapFactory().getMap(105200600)
                                                .setLastRespawnTime(0);
                                        getClient().getChannelServer().getMapFactory().getMap(105200600)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200610)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200600)
                                                .setLastRespawnTime(Long.MAX_VALUE);
                                        updateLastDate(getPlayer(), QuestExConstants.ChaosPierre.getQuestID());
                                        if (DBConfig.isGanglim) {
                                            updateQuestEx(getPlayer(), QuestExConstants.ChaosPierre.getQuestID());
                                        }
                                        if (!DBConfig.isGanglim && getPlayer().getPartyMemberSize() > 1) {
                                            for (MapleCharacter partyMember : getPlayer().getPartyMembers()) {
                                                if (partyMember.getMapId() == getPlayer().getMapId()) {
                                                    partyMember.setMultiMode(true);
                                                    partyMember.applyBMCurseJinMulti();
                                                }
                                            }
                                        }
                                        eim.registerParty(target.getParty(), getPlayer().getMap());
                                    } else {
                                        target.say("สมาชิกในปาร์ตี้ #b" + exMember
                                                + "#k ไม่มี #i4033611##t4033611# จึงไม่สามารถเข้าได้");
                                    }
                                } else {
                                    target.say("สมาชิกในปาร์ตี้จะสามารถเข้าได้อีกครั้งในอีก #b#e" + lastDate + " #n#k");
                                }
                            } else {
                                if (DBConfig.isGanglim) {
                                    target.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                            + "#n#k ได้ทำการท้าทายไปแล้วในวันนี้ จึงไม่สามารถเข้าได้");
                                } else {
                                    target.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                            + "#n#k ได้ทำการท้าทายไปแล้วในสัปดาห์นี้ จึงไม่สามารถเข้าได้");
                                }
                            }
                        } else {
                            self.say("แผนที่เต็ม ไม่สามารถใช้บริการได้ กรุณาลองใหม่ในแชนแนลอื่น");
                        }
                    } else {
                        self.say("สมาชิกปาร์ตี้ทุกคนต้องอยู่ในแผนที่เดียวกัน");
                    }
                } else if (v0 == 2) { // Practice Mode
                    self.say("โหมดฝึกซ้อมกำลังอยู่ระหว่างการเตรียมการ");
                }
            }
        }
    }

    public void pierreEnter() { // Pierre
        if (!DBConfig.isGanglim) {
            rootafirstDoor();
            return;
        }
        EventManager em = getEventManager("RootAbyssPierre");
        if (em == null) {
            self.say("ไม่สามารถเข้าได้ในขณะนี้");
        } else {
            String text = "#r#e<ทางเข้า Root Abyss West Garden>#n#k\r\nประตูสู่สวนที่ #rPierre#k ผู้พิทักษ์แห่งทิศตะวันตกเฝ้ารักษาอยู่ "
                    + (DBConfig.isGanglim ? "ประวัติการเคลียร์จะรีเซ็ตทุกเที่ยงคืน"
                            : "#rประวัติการเคลียร์ Normal จะรีเซ็ตทุกเที่ยงคืน, Chaos จะรีเซ็ตทุกวันพฤหัสบดี#b");
            text += "#L0#ใช้ #i4033611##t4033611# เพื่อเข้าสู่ Normal Mode (เลเวล 125 ขึ้นไป)";
            if (DBConfig.isGanglim) {
                text += " #r[" + getPlayer().getOneInfoQuestInteger(QuestExConstants.Pierre.getQuestID(), "eNum") + "/"
                        + (getPlayer().getBossTier() + 1) + "]#k";
                getPlayer().getPartyMembers()
                        .forEach(chr -> chr.dropMessage(5,
                                "[Normal Pierre] วันนี้เข้าสู้บอสไปแล้ว "
                                        + chr.getOneInfoQuestInteger(QuestExConstants.Pierre.getQuestID(), "eNum")
                                        + " ครั้ง สามารถเข้าได้ทั้งหมด " + (getPlayer().getBossTier() + 1) + " ครั้ง"));
            }
            text += "\r\n#L1#ใช้ #i4033611##t4033611# เพื่อเข้าสู่ Chaos Mode (เลเวล 180 ขึ้นไป)";
            if (DBConfig.isGanglim) {
                text += " #r[" + getPlayer().getOneInfoQuestInteger(QuestExConstants.ChaosPierre.getQuestID(), "eNum")
                        + "/" + (getPlayer().getBossTier() + 1) + "]#b";
                getPlayer().getPartyMembers()
                        .forEach(chr -> chr.dropMessage(5,
                                "[Chaos Pierre] วันนี้เข้าสู้บอสไปแล้ว "
                                        + chr.getOneInfoQuestInteger(QuestExConstants.ChaosPierre.getQuestID(), "eNum")
                                        + " ครั้ง สามารถเข้าได้ทั้งหมด " + (getPlayer().getBossTier() + 1) + " ครั้ง"));
            }
            text += "#l\r\n#L3#เข้าสู่ Chaos Practice Mode (เลเวล 180 ขึ้นไป)#l";
            int v0 = self.askMenu(text);
            if (target.getParty() == null) {
                target.say("ดูเหมือนจะต้องมีปาร์ตี้ก่อนนะ ไปหาปาร์ตี้กันเถอะ");
            } else {
                if (v0 == 0) { // Normal Mode
                    if (target.getParty().isPartySameMap()) {
                        if (em.getProperty("status0").equals("0")) {
                            String overLap = checkEventNumber(getPlayer(), QuestExConstants.Pierre.getQuestID(),
                                    DBConfig.isGanglim);
                            if (overLap == null) {
                                String lastDate = checkEventLastDate(getPlayer(), QuestExConstants.Pierre.getQuestID());
                                if (lastDate == null || DBConfig.isGanglim) {
                                    String exMember = target.exchangeParty(4033611, -1);
                                    if (exMember == null) { // Try entry
                                        em.setProperty("status0", "1");
                                        EventInstanceManager eim = em.readyInstance();
                                        eim.setProperty("map", 105200200);
                                        eim.setProperty("mode", "normal");
                                        getClient().getChannelServer().getMapFactory().getMap(105200200)
                                                .setLastRespawnTime(0);
                                        getClient().getChannelServer().getMapFactory().getMap(105200200)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200210)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200200)
                                                .setLastRespawnTime(Long.MAX_VALUE);

                                        updateLastDate(getPlayer(), QuestExConstants.Pierre.getQuestID());
                                        if (DBConfig.isGanglim) {
                                            updateQuestEx(getPlayer(), QuestExConstants.Pierre.getQuestID());
                                        }
                                        eim.registerParty(target.getParty(), getPlayer().getMap());
                                    } else {
                                        target.say("สมาชิกในปาร์ตี้ #b" + exMember
                                                + "#k ไม่มี #i4033611##t4033611# จึงไม่สามารถเข้าได้");
                                    }
                                } else {
                                    target.say("สมาชิกในปาร์ตี้จะสามารถเข้าได้อีกครั้งในอีก #b#e" + lastDate + " #n#k");
                                }
                            } else {
                                target.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                        + "#n#k ได้ทำการท้าทายไปแล้วในวันนี้ จึงไม่สามารถเข้าได้");
                            }
                        } else {
                            self.say("แผนที่เต็ม ไม่สามารถใช้บริการได้ กรุณาลองใหม่ในแชนแนลอื่น");
                        }
                    } else {
                        self.say("สมาชิกปาร์ตี้ทุกคนต้องอยู่ในแผนที่เดียวกัน");
                    }
                } else if (v0 == 1) { // Chaos Mode
                    if (target.getParty().isPartySameMap()) {
                        if (em.getProperty("Cstatus0").equals("0")) {
                            String overLap = checkEventNumber(getPlayer(), QuestExConstants.ChaosPierre.getQuestID(),
                                    DBConfig.isGanglim);
                            if (overLap == null) {
                                String lastDate = checkEventLastDate(getPlayer(),
                                        QuestExConstants.ChaosPierre.getQuestID());
                                if (lastDate == null || DBConfig.isGanglim) {
                                    String exMember = target.exchangeParty(4033611, -1);
                                    if (exMember == null) { // Try entry
                                        em.setProperty("Cstatus0", "1");
                                        EventInstanceManager eim = em.readyInstance();
                                        eim.setProperty("map", 105200600);
                                        eim.setProperty("mode", "chaos");
                                        getClient().getChannelServer().getMapFactory().getMap(105200600)
                                                .setLastRespawnTime(0);
                                        getClient().getChannelServer().getMapFactory().getMap(105200600)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200610)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200600)
                                                .setLastRespawnTime(Long.MAX_VALUE);
                                        updateLastDate(getPlayer(), QuestExConstants.ChaosPierre.getQuestID());
                                        if (DBConfig.isGanglim) {
                                            updateQuestEx(getPlayer(), QuestExConstants.ChaosPierre.getQuestID());
                                        }
                                        eim.registerParty(target.getParty(), getPlayer().getMap());
                                    } else {
                                        target.say("สมาชิกในปาร์ตี้ #b" + exMember
                                                + "#k ไม่มี #i4033611##t4033611# จึงไม่สามารถเข้าได้");
                                    }
                                } else {
                                    target.say("สมาชิกในปาร์ตี้จะสามารถเข้าได้อีกครั้งในอีก #b#e" + lastDate + " #n#k");
                                }
                            } else {
                                if (DBConfig.isGanglim) {
                                    target.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                            + "#n#k ได้ทำการท้าทายไปแล้วในวันนี้ จึงไม่สามารถเข้าได้");
                                } else {
                                    target.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                            + "#n#k ได้ทำการท้าทายไปแล้วในสัปดาห์นี้ จึงไม่สามารถเข้าได้");
                                }
                            }
                        } else {
                            self.say("แผนที่เต็ม ไม่สามารถใช้บริการได้ กรุณาลองใหม่ในแชนแนลอื่น");
                        }
                    } else {
                        self.say("สมาชิกปาร์ตี้ทุกคนต้องอยู่ในแผนที่เดียวกัน");
                    }
                } else if (v0 == 2) { // Practice Mode
                    self.say("โหมดฝึกซ้อมกำลังอยู่ระหว่างการเตรียมการ");
                }
            }
        }
    }

    // Pierre Tea Party

    public void pierre_Summon() { // Normal Pierre
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            if (eim.getProperty("summonMOB") == null) {
                eim.setProperty("summonMOB", "1");
                Field field = getPlayer().getMap();
                field.broadcastMessage(CField.environmentChange("rootabyss/firework", 19));
                field.startMapEffect("ยินดีต้อนรับสู่ปาร์ตี้น้ำชาของ Pierre!", 5120098, 3000);
                PlayMusicDown e = new PlayMusicDown(getPlayer().getId(), 100, "Field.img/rootabyss/firework");
                field.broadcastMessage(e.encodeForLocal());
                if (DBConfig.isGanglim) {
                    field.spawnMonster(MapleLifeFactory.getMonster(8900100), new Point(1000, 551), 1);
                } else {
                    if (!getPlayer().isMultiMode()) {
                        field.spawnMonster(MapleLifeFactory.getMonster(8900100), new Point(1000, 551), 1);
                    } else {
                        final MapleMonster pierre = MapleLifeFactory.getMonster(8900100);
                        pierre.setPosition(new Point(1000, 551));
                        final long hp = pierre.getMobMaxHp();
                        long fixedhp = hp * 3L;
                        if (fixedhp < 0) {
                            fixedhp = Long.MAX_VALUE;
                        }
                        pierre.setHp(fixedhp);
                        pierre.setMaxHp(fixedhp);

                        field.spawnMonster(pierre, 1);
                    }
                }
            }
        }
    }

    public void pierre_Summon1() {
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            if (eim.getProperty("summonMOB") == null) {
                eim.setProperty("summonMOB", "1");
                Field field = getPlayer().getMap();
                field.broadcastMessage(CField.environmentChange("rootabyss/firework", 19));
                field.startMapEffect("ยินดีต้อนรับสู่ปาร์ตี้น้ำชาของ Pierre!", 5120098, 3000);
                PlayMusicDown e = new PlayMusicDown(getPlayer().getId(), 100, "Field.img/rootabyss/firework");
                field.broadcastMessage(e.encodeForLocal());
                field.spawnMonster(MapleLifeFactory.getMonster(8900000), new Point(1000, 551), 1);
            }
        }
    }

    public void rootasecondDoor() { // Von Bon
        // East Garden 105200100
        // Chaos East Garden 105200500
        // Summon Von Bon in the rift
        initNPC(MapleLifeFactory.getNPC(1064013));
        EventManager em = getEventManager("RootAbyssVonbon");
        if (em == null) {
            self.say("ไม่สามารถเข้าได้ในขณะนี้");
        } else {
            String text = "";
            if (DBConfig.isGanglim) {
                text = "#r#e<ทางเข้า Root Abyss East Garden>#n#k\r\nประตูสู่สวนที่ #rVon Bon#k ผู้พิทักษ์แห่งทิศตะวันออกเฝ้ารักษาอยู่ "
                        + (DBConfig.isGanglim ? "ประวัติการเคลียร์จะรีเซ็ตทุกเที่ยงคืน"
                                : "#rประวัติการเคลียร์ Normal จะรีเซ็ตทุกเที่ยงคืน, Chaos จะรีเซ็ตทุกวันพฤหัสบดี#b")
                        + "\r\n";
                text += "#L0#ใช้ #i4033611##t4033611# เพื่อเข้าสู่ Normal Mode (เลเวล 125 ขึ้นไป)";
                text += " #r[" + getPlayer().getOneInfoQuestInteger(QuestExConstants.VonBon.getQuestID(), "eNum") + "/"
                        + (getPlayer().getBossTier() + 1) + "]#k";
                getPlayer().getPartyMembers()
                        .forEach(chr -> chr.dropMessage(5,
                                "[Normal Von Bon] วันนี้เข้าสู้บอสไปแล้ว "
                                        + chr.getOneInfoQuestInteger(QuestExConstants.VonBon.getQuestID(), "eNum")
                                        + " ครั้ง สามารถเข้าได้ทั้งหมด " + (chr.getBossTier() + 1) + " ครั้ง"));
                text += "#l\r\n#L1#ใช้ #i4033611##t4033611# เพื่อเข้าสู่ Chaos Mode (เลเวล 180 ขึ้นไป)";
                text += " #r[" + getPlayer().getOneInfoQuestInteger(QuestExConstants.ChaosVonBon.getQuestID(), "eNum")
                        + "/" + (getPlayer().getBossTier() + 1) + "]#b";
                getPlayer().getPartyMembers()
                        .forEach(chr -> chr.dropMessage(5,
                                "[Chaos Von Bon] วันนี้เข้าสู้บอสไปแล้ว "
                                        + chr.getOneInfoQuestInteger(QuestExConstants.ChaosVonBon.getQuestID(), "eNum")
                                        + " ครั้ง สามารถเข้าได้ทั้งหมด " + (chr.getBossTier() + 1) + " ครั้ง"));
                text += "#l\r\n#L3#เข้าสู่ Chaos Practice Mode (เลเวล 180 ขึ้นไป)#l";
            } else {
                boolean single = getPlayer().getPartyMemberSize() == 1;
                text = "#r#e<ทางเข้า Root Abyss East Garden>#n#k\r\nประตูสู่สวนที่ #rVon Bon#k ผู้พิทักษ์แห่งทิศตะวันออกเฝ้ารักษาอยู่ "
                        + (DBConfig.isGanglim ? "ประวัติการเคลียร์จะรีเซ็ตทุกเที่ยงคืน"
                                : "#rประวัติการเคลียร์ Normal จะรีเซ็ตทุกเที่ยงคืน, Chaos จะรีเซ็ตทุกวันพฤหัสบดี#b")
                        + "\r\n";
                text += "#L0#ใช้ #i4033611##t4033611# เพื่อเข้าสู่ Normal Mode " + (single ? "(Single)" : "(Multi)")
                        + " (เลเวล 125 ขึ้นไป)";
                text += "#l\r\n#L1#ใช้ #i4033611##t4033611# เพื่อเข้าสู่ Chaos Mode "
                        + (single ? "(Single)" : "(Multi)")
                        + " (เลเวล 180 ขึ้นไป)";
                // text += "#l\r\n#L3#เข้าสู่ Chaos Practice Mode (เลเวล 180 ขึ้นไป)#l";
                int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.DailyQuestResetCount.getQuestID(),
                        "NormalVonBon" + (single ? "Single" : "Multi"));
                text += "\r\n#L4#เพิ่มรอบเข้าเล่น Normal Mode " + (single ? "(Single)" : "(Multi)") + " (เหลือ "
                        + ((single ? 2 : 1) - reset)
                        + " ครั้ง) #l";
            }
            int v0 = self.askMenu(text);
            if (target.getParty() == null) {
                target.say("ดูเหมือนจะต้องมีปาร์ตี้ก่อนนะ ไปหาปาร์ตี้กันเถอะ");
                return;
            } else {
                if (v0 == 4 && !DBConfig.isGanglim) {
                    if (getPlayer().getTogetherPoint() < 150) {
                        self.sayOk("คะแนนความร่วมมือไม่เพียงพอ คะแนนปัจจุบัน : " + getPlayer().getTogetherPoint());
                        return;
                    }
                    boolean single = getPlayer().getPartyMemberSize() == 1;
                    int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.DailyQuestResetCount.getQuestID(),
                            "NormalVonBon" + (single ? "Single" : "Multi"));
                    if ((reset > 0 && !single) || reset > 1 && single) { // Cannot reset
                        self.sayOk("คุณใช้จำนวนครั้งที่สามารถเพิ่มได้ในวันนี้หมดแล้ว");
                        return;
                    }
                    getPlayer().gainTogetherPoint(-150);
                    getPlayer().updateOneInfo(QuestExConstants.DailyQuestResetCount.getQuestID(),
                            "NormalVonBon" + (single ? "Single" : "Multi"), String.valueOf(reset + 1));
                    self.sayOk("เพิ่มจำนวนครั้งที่เข้าเล่นเรียบร้อยแล้ว");
                    return;
                }
                if (target.getParty().isPartySameMap()) {
                    if (v0 == 0) { // Normal Mode
                        if (em.getProperty("status0").equals("0")) {
                            String overLap = checkEventNumber(getPlayer(), QuestExConstants.VonBon.getQuestID(),
                                    DBConfig.isGanglim);
                            if (overLap == null) {
                                String lastDate = checkEventLastDate(getPlayer(), QuestExConstants.VonBon.getQuestID());
                                if (lastDate == null || DBConfig.isGanglim) {
                                    String exMember = target.exchangeParty(4033611, -1);
                                    if (exMember == null) { // Try entry
                                        em.setProperty("status0", "1");
                                        updateLastDate(getPlayer(), QuestExConstants.VonBon.getQuestID());
                                        if (DBConfig.isGanglim) {
                                            updateQuestEx(getPlayer(), QuestExConstants.VonBon.getQuestID());
                                        }
                                        if (!DBConfig.isGanglim && getPlayer().getPartyMemberSize() > 1) {
                                            for (MapleCharacter partyMember : getPlayer().getPartyMembers()) {
                                                if (partyMember.getMapId() == getPlayer().getMapId()) {
                                                    partyMember.setMultiMode(true);
                                                    partyMember.applyBMCurseJinMulti();
                                                }
                                            }
                                        }
                                        EventInstanceManager eim = em.readyInstance();
                                        eim.setProperty("map", 105200100);
                                        eim.setProperty("mode", "normal");
                                        getClient().getChannelServer().getMapFactory().getMap(105200100)
                                                .setLastRespawnTime(0);
                                        getClient().getChannelServer().getMapFactory().getMap(105200100)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200110)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200100)
                                                .setLastRespawnTime(Long.MAX_VALUE);
                                        eim.registerParty(target.getParty(), getPlayer().getMap());
                                    } else {
                                        target.say("สมาชิกในปาร์ตี้ #b" + exMember
                                                + "#k ไม่มี #i4033611##t4033611# จึงไม่สามารถเข้าได้");
                                    }
                                } else {
                                    target.say("สมาชิกในปาร์ตี้จะสามารถเข้าได้อีกครั้งในอีก #b#e" + lastDate + " #n#k");
                                }
                            } else {
                                target.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                        + "#n#k ได้ทำการท้าทายไปแล้วในวันนี้ จึงไม่สามารถเข้าได้");
                            }
                        } else {
                            self.say("แผนที่เต็ม ไม่สามารถใช้บริการได้ กรุณาลองใหม่ในแชนแนลอื่น");
                        }
                    } else if (v0 == 1) { // Chaos Mode
                        if (em.getProperty("Cstatus0").equals("0")) {
                            String overLap = checkEventNumber(getPlayer(), QuestExConstants.ChaosVonBon.getQuestID(),
                                    DBConfig.isGanglim);
                            if (overLap == null) {
                                String lastDate = checkEventLastDate(getPlayer(),
                                        QuestExConstants.ChaosVonBon.getQuestID());
                                if (lastDate == null || DBConfig.isGanglim) {
                                    String exMember = target.exchangeParty(4033611, -1);
                                    if (exMember == null) { // Try entry
                                        em.setProperty("Cstatus0", "1");
                                        updateLastDate(getPlayer(), QuestExConstants.ChaosVonBon.getQuestID());
                                        if (DBConfig.isGanglim) {
                                            updateQuestEx(getPlayer(), QuestExConstants.ChaosVonBon.getQuestID());
                                        }
                                        if (!DBConfig.isGanglim && getPlayer().getPartyMemberSize() > 1) {
                                            for (MapleCharacter partyMember : getPlayer().getPartyMembers()) {
                                                if (partyMember.getMapId() == getPlayer().getMapId()) {
                                                    partyMember.setMultiMode(true);
                                                    partyMember.applyBMCurseJinMulti();
                                                }
                                            }
                                        }
                                        EventInstanceManager eim = em.readyInstance();
                                        eim.setProperty("map", 105200500);
                                        eim.setProperty("mode", "chaos");
                                        getClient().getChannelServer().getMapFactory().getMap(105200500)
                                                .setLastRespawnTime(0);
                                        getClient().getChannelServer().getMapFactory().getMap(105200500)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200510)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200500)
                                                .setLastRespawnTime(Long.MAX_VALUE);
                                        eim.registerParty(target.getParty(), getPlayer().getMap());
                                    } else {
                                        target.say("สมาชิกในปาร์ตี้ #b" + exMember
                                                + "#k ไม่มี #i4033611##t4033611# จึงไม่สามารถเข้าได้");
                                    }
                                } else {
                                    target.say("สมาชิกในปาร์ตี้จะสามารถเข้าได้อีกครั้งในอีก #b#e" + lastDate + " #n#k");
                                }
                            } else {
                                if (DBConfig.isGanglim) {
                                    target.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                            + "#n#k ได้ทำการท้าทายไปแล้วในวันนี้ จึงไม่สามารถเข้าได้");
                                } else {
                                    target.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                            + "#n#k ได้ทำการท้าทายไปแล้วในสัปดาห์นี้ จึงไม่สามารถเข้าได้");
                                }
                            }
                        } else {
                            self.say("แผนที่เต็ม ไม่สามารถใช้บริการได้ กรุณาลองใหม่ในแชนแนลอื่น");
                        }
                    } else if (v0 == 2) { // Practice Mode
                        self.say("โหมดฝึกซ้อมกำลังอยู่ระหว่างการเตรียมการ");
                    }
                } else {
                    self.say("สมาชิกปาร์ตี้ทุกคนต้องอยู่ในแผนที่เดียวกัน");
                }
            }
        }
    }

    public void banbanEnter() { // Von Bon
        if (!DBConfig.isGanglim) {
            rootasecondDoor();
            return;
        }
        EventManager em = getEventManager("RootAbyssVonbon");
        if (em == null) {
            self.say("ไม่สามารถเข้าได้ในขณะนี้");
        } else {
            String text = "#r#e<ทางเข้า Root Abyss East Garden>#n#k\r\nประตูสู่สวนที่ #rVon Bon#k ผู้พิทักษ์แห่งทิศตะวันออกเฝ้ารักษาอยู่ "
                    + (DBConfig.isGanglim ? "ประวัติการเคลียร์จะรีเซ็ตทุกเที่ยงคืน"
                            : "#rประวัติการเคลียร์ Normal จะรีเซ็ตทุกเที่ยงคืน, Chaos จะรีเซ็ตทุกวันพฤหัสบดี#b")
                    + "\r\n'";
            text += "#L0#ใช้ #i4033611##t4033611# เพื่อเข้าสู่ Normal Mode (เลเวล 125 ขึ้นไป)";
            if (DBConfig.isGanglim) {
                text += " #r[" + getPlayer().getOneInfoQuestInteger(QuestExConstants.VonBon.getQuestID(), "eNum") + "/"
                        + (getPlayer().getBossTier() + 1) + "]#k";
                getPlayer().getPartyMembers()
                        .forEach(chr -> chr.dropMessage(5,
                                "[Normal Von Bon] วันนี้เข้าสู้บอสไปแล้ว "
                                        + chr.getOneInfoQuestInteger(QuestExConstants.VonBon.getQuestID(), "eNum")
                                        + " ครั้ง สามารถเข้าได้ทั้งหมด " + (chr.getBossTier() + 1) + " ครั้ง"));
            }
            text += "#l\r\n#L1#ใช้ #i4033611##t4033611# เพื่อเข้าสู่ Chaos Mode (เลเวล 180 ขึ้นไป)";
            if (DBConfig.isGanglim) {
                text += " #r[" + getPlayer().getOneInfoQuestInteger(QuestExConstants.ChaosVonBon.getQuestID(), "eNum")
                        + "/" + (getPlayer().getBossTier() + 1) + "]#b";
                getPlayer().getPartyMembers()
                        .forEach(chr -> chr.dropMessage(5,
                                "[Chaos Von Bon] วันนี้เข้าสู้บอสไปแล้ว "
                                        + chr.getOneInfoQuestInteger(QuestExConstants.ChaosVonBon.getQuestID(), "eNum")
                                        + " ครั้ง สามารถเข้าได้ทั้งหมด " + (chr.getBossTier() + 1) + " ครั้ง"));
            }
            text += "#l\r\n#L3#เข้าสู่ Chaos Practice Mode (เลเวล 180 ขึ้นไป)#l";
            int v0 = self.askMenu(text);
            if (target.getParty() == null) {
                target.say("ดูเหมือนจะต้องมีปาร์ตี้ก่อนนะ ไปหาปาร์ตี้กันเถอะ");
            } else {
                if (target.getParty().isPartySameMap()) {
                    if (v0 == 0) { // Normal Mode
                        if (em.getProperty("status0").equals("0")) {
                            String overLap = checkEventNumber(getPlayer(), QuestExConstants.VonBon.getQuestID(),
                                    DBConfig.isGanglim);
                            if (overLap == null) {
                                String lastDate = checkEventLastDate(getPlayer(), QuestExConstants.VonBon.getQuestID());
                                if (lastDate == null || DBConfig.isGanglim) {
                                    String exMember = target.exchangeParty(4033611, -1);
                                    if (exMember == null) { // Try entry
                                        em.setProperty("status0", "1");
                                        updateLastDate(getPlayer(), QuestExConstants.VonBon.getQuestID());
                                        if (DBConfig.isGanglim) {
                                            updateQuestEx(getPlayer(), QuestExConstants.VonBon.getQuestID());
                                        }
                                        EventInstanceManager eim = em.readyInstance();
                                        eim.setProperty("map", 105200100);
                                        eim.setProperty("mode", "normal");
                                        getClient().getChannelServer().getMapFactory().getMap(105200100)
                                                .setLastRespawnTime(0);
                                        getClient().getChannelServer().getMapFactory().getMap(105200100)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200110)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200100)
                                                .setLastRespawnTime(Long.MAX_VALUE);
                                        eim.registerParty(target.getParty(), getPlayer().getMap());
                                    } else {
                                        target.say("สมาชิกในปาร์ตี้ #b" + exMember
                                                + "#k ไม่มี #i4033611##t4033611# จึงไม่สามารถเข้าได้");
                                    }
                                } else {
                                    target.say("สมาชิกในปาร์ตี้จะสามารถเข้าได้อีกครั้งในอีก #b#e" + lastDate + " #n#k");
                                }
                            } else {
                                target.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                        + "#n#k ได้ทำการท้าทายไปแล้วในวันนี้ จึงไม่สามารถเข้าได้");
                            }
                        } else {
                            self.say("แผนที่เต็ม ไม่สามารถใช้บริการได้ กรุณาลองใหม่ในแชนแนลอื่น");
                        }
                    } else if (v0 == 1) { // Chaos Mode
                        if (em.getProperty("Cstatus0").equals("0")) {
                            String overLap = checkEventNumber(getPlayer(), QuestExConstants.ChaosVonBon.getQuestID(),
                                    DBConfig.isGanglim);
                            if (overLap == null) {
                                String lastDate = checkEventLastDate(getPlayer(),
                                        QuestExConstants.ChaosVonBon.getQuestID());
                                if (lastDate == null || DBConfig.isGanglim) {
                                    String exMember = target.exchangeParty(4033611, -1);
                                    if (exMember == null) { // Try entry
                                        em.setProperty("Cstatus0", "1");
                                        updateLastDate(getPlayer(), QuestExConstants.ChaosVonBon.getQuestID());
                                        if (DBConfig.isGanglim) {
                                            updateQuestEx(getPlayer(), QuestExConstants.ChaosVonBon.getQuestID());
                                        }
                                        EventInstanceManager eim = em.readyInstance();
                                        eim.setProperty("map", 105200500);
                                        eim.setProperty("mode", "chaos");
                                        getClient().getChannelServer().getMapFactory().getMap(105200500)
                                                .setLastRespawnTime(0);
                                        getClient().getChannelServer().getMapFactory().getMap(105200500)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200510)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200500)
                                                .setLastRespawnTime(Long.MAX_VALUE);
                                        eim.registerParty(target.getParty(), getPlayer().getMap());
                                    } else {
                                        target.say("สมาชิกในปาร์ตี้ #b" + exMember
                                                + "#k ไม่มี #i4033611##t4033611# จึงไม่สามารถเข้าได้");
                                    }
                                } else {
                                    target.say("สมาชิกในปาร์ตี้จะสามารถเข้าได้อีกครั้งในอีก #b#e" + lastDate + " #n#k");
                                }
                            } else {
                                if (DBConfig.isGanglim) {
                                    target.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                            + "#n#k ได้ทำการท้าทายไปแล้วในวันนี้ จึงไม่สามารถเข้าได้");
                                } else {
                                    target.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                            + "#n#k ได้ทำการท้าทายไปแล้วในสัปดาห์นี้ จึงไม่สามารถเข้าได้");
                                }
                            }
                        } else {
                            self.say("แผนที่เต็ม ไม่สามารถใช้บริการได้ กรุณาลองใหม่ในแชนแนลอื่น");
                        }
                    } else if (v0 == 2) { // Practice Mode
                        self.say("โหมดฝึกซ้อมกำลังอยู่ระหว่างการเตรียมการ");
                    }
                } else {
                    self.say("สมาชิกปาร์ตี้ทุกคนต้องอยู่ในแผนที่เดียวกัน");
                }
            }
        }
    }

    public void banban_Summon() {
        //
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            if (eim.getProperty("summonMOB") == null) {
                eim.setProperty("summonMOB", "1");
                Field field = getPlayer().getMap();
                field.broadcastMessage(CField.environmentChange("Bgm29.img/banbantime", 19));
                field.startMapEffect("จงออกมาจากรอยแยกมิติ Von Bon!", 5120025, 3000);
            }
        }
    }

    public void banbanInsideMob() {
        MapleMonster mob = MapleLifeFactory.getMonster(8910001);
        if (mob != null) {
            Field field = getPlayer().getMap();
            field.spawnMonsterOnGroundBelow(mob, new Point(-50, 245));
        }
    }

    public void banbanGoInside() {
        Portal portal = getPortal();
        Field field = getPlayer().getMap();
        if (portal.getId() == 2) {
            if (field.isObjectEnable("Pt01gate")) {
                registerTransferField(field.getId() + 10);
            }
        }
        if (portal.getId() == 3) {
            if (field.isObjectEnable("Pt02gate")) {
                registerTransferField(field.getId() + 10);
            }
        }
        if (portal.getId() == 4) {
            if (field.isObjectEnable("Pt03gate")) {
                registerTransferField(field.getId() + 10);
            }
        }
        if (portal.getId() == 5) {
            if (field.isObjectEnable("Pt04gate")) {
                registerTransferField(field.getId() + 10);
            }
        }
        if (portal.getId() == 6) {
            if (field.isObjectEnable("Pt05gate")) {
                registerTransferField(field.getId() + 10);
            }
        }
        if (portal.getId() == 7) {
            if (field.isObjectEnable("Pt06gate")) {
                registerTransferField(field.getId() + 10);
            }
        }
        if (portal.getId() == 8) {
            if (field.isObjectEnable("Pt07gate")) {
                registerTransferField(field.getId() + 10);
            }
        }
        if (portal.getId() == 9) {
            if (field.isObjectEnable("Pt08gate")) {
                registerTransferField(field.getId() + 10);
            }
        }
        if (portal.getId() == 10) {
            if (field.isObjectEnable("Pt09gate")) {
                registerTransferField(field.getId() + 10);
            }
        }
    }

    public void rootathirdDoor() { // Crimson Queen
        // South Garden, Queen's Castle Normal, Chaos
        // 105200300, 105200310
        // 105200700, 105200710
        // Let's talk to the sleeping Crimson Queen
        // Oh, cute
        // Insolent!
        // Giggle, here is
        // Sob, your death
        // Must break box to appear
        initNPC(MapleLifeFactory.getNPC(1064014));
        EventManager em = getEventManager("RootAbyssCrimsonQueen");
        if (em == null) {
            self.say("ไม่สามารถเข้าได้ในขณะนี้");
        } else {
            String text = "";
            if (DBConfig.isGanglim) {
                text = "#r#e<ทางเข้า Root Abyss South Garden>#n#k\r\nประตูสู่สวนที่ #rCrimson Queen#k ผู้พิทักษ์แห่งทิศใต้เฝ้ารักษาอยู่ "
                        + (DBConfig.isGanglim ? "ประวัติการเคลียร์จะรีเซ็ตทุกเที่ยงคืน"
                                : "#rประวัติการเคลียร์ Normal จะรีเซ็ตทุกเที่ยงคืน, Chaos จะรีเซ็ตทุกวันพฤหัสบดี#b")
                        + "\r\n";
                text += "#L0#ใช้ #i4033611##t4033611# เพื่อเข้าสู่ Normal Mode (เลเวล 125 ขึ้นไป)";
                text += " #r[" + getPlayer().getOneInfoQuestInteger(QuestExConstants.CrimsonQueen.getQuestID(), "eNum")
                        + "/" + (getPlayer().getBossTier() + 1) + "]#k";
                getPlayer().getPartyMembers()
                        .forEach(chr -> chr.dropMessage(5,
                                "[Normal Crimson Queen] วันนี้เข้าสู้บอสไปแล้ว "
                                        + chr.getOneInfoQuestInteger(QuestExConstants.CrimsonQueen.getQuestID(), "eNum")
                                        + " ครั้ง สามารถเข้าได้ทั้งหมด " + (chr.getBossTier() + 1) + " ครั้ง"));
                text += "#l\r\n#L1#ใช้ #i4033611##t4033611# เพื่อเข้าสู่ Chaos Mode (เลเวล 180 ขึ้นไป)";
                text += " #r["
                        + getPlayer().getOneInfoQuestInteger(QuestExConstants.ChaosCrimsonQueen.getQuestID(), "eNum")
                        + "/" + (getPlayer().getBossTier() + 1) + "]#b";
                getPlayer().getPartyMembers().forEach(chr -> chr.dropMessage(5,
                        "[Chaos Crimson Queen] วันนี้เข้าสู้บอสไปแล้ว "
                                + chr.getOneInfoQuestInteger(QuestExConstants.ChaosCrimsonQueen.getQuestID(), "eNum")
                                + " ครั้ง สามารถเข้าได้ทั้งหมด " + (chr.getBossTier() + 1) + " ครั้ง"));
                text += "#l\r\n#L3#เข้าสู่ Chaos Practice Mode (เลเวล 180 ขึ้นไป)#l";
            } else {
                boolean single = getPlayer().getPartyMemberSize() == 1;
                text = "#r#e<ทางเข้า Root Abyss South Garden>#n#k\r\nประตูสู่สวนที่ #rCrimson Queen#k ผู้พิทักษ์แห่งทิศใต้เฝ้ารักษาอยู่ "
                        + (DBConfig.isGanglim ? "ประวัติการเคลียร์จะรีเซ็ตทุกเที่ยงคืน"
                                : "#rประวัติการเคลียร์ Normal จะรีเซ็ตทุกเที่ยงคืน, Chaos จะรีเซ็ตทุกวันพฤหัสบดี#b")
                        + "\r\n";
                text += "#L0#ใช้ #i4033611##t4033611# เพื่อเข้าสู่ Normal Mode " + (single ? "(Single)" : "(Multi)")
                        + " (เลเวล 125 ขึ้นไป)";
                text += "#l\r\n#L1#ใช้ #i4033611##t4033611# เพื่อเข้าสู่ Chaos Mode "
                        + (single ? "(Single)" : "(Multi)")
                        + " (เลเวล 180 ขึ้นไป)";
                // text += "#l\r\n#L3#เข้าสู่ Chaos Practice Mode (เลเวล 180 ขึ้นไป)#l";
                int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.DailyQuestResetCount.getQuestID(),
                        "NormalCrimsonQueen" + (single ? "Single" : "Multi"));
                text += "\r\n#L4#เพิ่มรอบเข้าเล่น Normal Mode " + (single ? "(Single)" : "(Multi)") + " (เหลือ "
                        + ((single ? 2 : 1) - reset)
                        + " ครั้ง) #l";
            }

            int v0 = self.askMenu(text);
            if (target.getParty() == null) {
                target.say("ดูเหมือนจะต้องมีปาร์ตี้ก่อนนะ ไปหาปาร์ตี้กันเถอะ");
                return;
            } else {
                if (v0 == 4 && !DBConfig.isGanglim) {
                    if (getPlayer().getTogetherPoint() < 150) {
                        self.sayOk("คะแนนความร่วมมือไม่เพียงพอ คะแนนปัจจุบัน : " + getPlayer().getTogetherPoint());
                        return;
                    }
                    boolean single = getPlayer().getPartyMemberSize() == 1;
                    int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.DailyQuestResetCount.getQuestID(),
                            "NormalCrimsonQueen" + (single ? "Single" : "Multi"));
                    if ((reset > 0 && !single) || reset > 1 && single) { // Cannot reset
                        self.sayOk("คุณใช้จำนวนครั้งที่สามารถเพิ่มได้ในวันนี้หมดแล้ว");
                        return;
                    }
                    getPlayer().gainTogetherPoint(-150);
                    getPlayer().updateOneInfo(QuestExConstants.DailyQuestResetCount.getQuestID(),
                            "NormalCrimsonQueen" + (single ? "Single" : "Multi"), String.valueOf(reset + 1));
                    self.sayOk("เพิ่มจำนวนครั้งที่เข้าเล่นเรียบร้อยแล้ว");
                    return;
                }
                if (target.getParty().isPartySameMap()) {
                    if (v0 == 0) { // Normal Mode
                        if (em.getProperty("status0").equals("0")) {
                            String overLap = checkEventNumber(getPlayer(), QuestExConstants.CrimsonQueen.getQuestID(),
                                    DBConfig.isGanglim);
                            if (overLap == null) {
                                String lastDate = checkEventLastDate(getPlayer(),
                                        QuestExConstants.CrimsonQueen.getQuestID());
                                if (lastDate == null || DBConfig.isGanglim) {
                                    String exMember = target.exchangeParty(4033611, -1);
                                    if (exMember == null) { // Try entry
                                        em.setProperty("status0", "1");
                                        updateLastDate(getPlayer(), QuestExConstants.CrimsonQueen.getQuestID());
                                        if (DBConfig.isGanglim) {
                                            updateQuestEx(getPlayer(), QuestExConstants.CrimsonQueen.getQuestID());
                                        }
                                        if (!DBConfig.isGanglim && getPlayer().getPartyMemberSize() > 1) {
                                            for (MapleCharacter partyMember : getPlayer().getPartyMembers()) {
                                                if (partyMember.getMapId() == getPlayer().getMapId()) {
                                                    partyMember.setMultiMode(true);
                                                    partyMember.applyBMCurseJinMulti();
                                                }
                                            }
                                        }
                                        EventInstanceManager eim = em.readyInstance();
                                        eim.setProperty("map", 105200300);
                                        eim.setProperty("mode", "normal");
                                        getClient().getChannelServer().getMapFactory().getMap(105200300)
                                                .setLastRespawnTime(0);
                                        getClient().getChannelServer().getMapFactory().getMap(105200300)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200310)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200300)
                                                .setLastRespawnTime(Long.MAX_VALUE);
                                        eim.registerParty(target.getParty(), getPlayer().getMap());
                                    } else {
                                        target.say("สมาชิกในปาร์ตี้ #b" + exMember
                                                + "#k ไม่มี #i4033611##t4033611# จึงไม่สามารถเข้าได้");
                                    }
                                } else {
                                    target.say("สมาชิกในปาร์ตี้จะสามารถเข้าได้อีกครั้งในอีก #b#e" + lastDate + " #n#k");
                                }
                            } else {
                                target.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                        + "#n#k ได้ทำการท้าทายไปแล้วในวันนี้ จึงไม่สามารถเข้าได้");
                            }
                        } else {
                            self.say("แผนที่เต็ม ไม่สามารถใช้บริการได้ กรุณาลองใหม่ในแชนแนลอื่น");
                        }
                    } else if (v0 == 1) { // Chaos Mode
                        if (em.getProperty("Cstatus0").equals("0")) {
                            String overLap = checkEventNumber(getPlayer(),
                                    QuestExConstants.ChaosCrimsonQueen.getQuestID(), DBConfig.isGanglim);
                            if (overLap == null) {
                                String lastDate = checkEventLastDate(getPlayer(),
                                        QuestExConstants.ChaosCrimsonQueen.getQuestID());
                                if (lastDate == null || DBConfig.isGanglim) {
                                    String exMember = target.exchangeParty(4033611, -1);
                                    if (exMember == null) { // Try entry
                                        em.setProperty("Cstatus0", "1");
                                        updateLastDate(getPlayer(), QuestExConstants.ChaosCrimsonQueen.getQuestID());
                                        if (DBConfig.isGanglim) {
                                            updateQuestEx(getPlayer(), QuestExConstants.ChaosCrimsonQueen.getQuestID());
                                        }
                                        if (!DBConfig.isGanglim && getPlayer().getPartyMemberSize() > 1) {
                                            for (MapleCharacter partyMember : getPlayer().getPartyMembers()) {
                                                if (partyMember.getMapId() == getPlayer().getMapId()) {
                                                    partyMember.setMultiMode(true);
                                                    partyMember.applyBMCurseJinMulti();
                                                }
                                            }
                                        }
                                        EventInstanceManager eim = em.readyInstance();
                                        eim.setProperty("map", 105200700);
                                        eim.setProperty("mode", "chaos");
                                        getClient().getChannelServer().getMapFactory().getMap(105200700)
                                                .setLastRespawnTime(0);
                                        getClient().getChannelServer().getMapFactory().getMap(105200700)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200710)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200700)
                                                .setLastRespawnTime(Long.MAX_VALUE);
                                        eim.registerParty(target.getParty(), getPlayer().getMap());
                                    } else {
                                        target.say("สมาชิกในปาร์ตี้ #b" + exMember
                                                + "#k ไม่มี #i4033611##t4033611# จึงไม่สามารถเข้าได้");
                                    }
                                } else {
                                    target.say("สมาชิกในปาร์ตี้จะสามารถเข้าได้อีกครั้งในอีก #b#e" + lastDate + " #n#k");
                                }
                            } else {
                                if (DBConfig.isGanglim) {
                                    target.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                            + "#n#k ได้ทำการท้าทายไปแล้วในวันนี้ จึงไม่สามารถเข้าได้");
                                } else {
                                    target.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                            + "#n#k ได้ทำการท้าทายไปแล้วในสัปดาห์นี้ จึงไม่สามารถเข้าได้");
                                }
                            }
                        } else {
                            self.say("แผนที่เต็ม ไม่สามารถใช้บริการได้ กรุณาลองใหม่ในแชนแนลอื่น");
                        }
                    } else if (v0 == 2) { // Practice Mode
                        self.say("โหมดฝึกซ้อมกำลังอยู่ระหว่างการเตรียมการ");
                    }
                } else {
                    self.say("สมาชิกปาร์ตี้ทุกคนต้องอยู่ในแผนที่เดียวกัน");
                }
            }
        }
    }

    public void bloodyqueenEnter() { // Crimson Queen
        if (!DBConfig.isGanglim) {
            rootathirdDoor();
            return;
        }
        EventManager em = getEventManager("RootAbyssCrimsonQueen");
        if (em == null) {
            self.say("ไม่สามารถเข้าได้ในขณะนี้");
        } else {
            String text = "#r#e<ทางเข้า Root Abyss South Garden>#n#k\r\nประตูสู่สวนที่ #rCrimson Queen#k ผู้พิทักษ์แห่งทิศใต้เฝ้ารักษาอยู่ "
                    + (DBConfig.isGanglim ? "ประวัติการเคลียร์จะรีเซ็ตทุกเที่ยงคืน"
                            : "#rประวัติการเคลียร์ Normal จะรีเซ็ตทุกเที่ยงคืน, Chaos จะรีเซ็ตทุกวันพฤหัสบดี#b")
                    + "\r\n";
            text += "#L0#ใช้ #i4033611##t4033611# เพื่อเข้าสู่ Normal Mode (เลเวล 125 ขึ้นไป)";
            if (DBConfig.isGanglim) {
                text += " #r[" + getPlayer().getOneInfoQuestInteger(QuestExConstants.CrimsonQueen.getQuestID(), "eNum")
                        + "/" + (getPlayer().getBossTier() + 1) + "]#k";
                getPlayer().getPartyMembers()
                        .forEach(chr -> chr.dropMessage(5,
                                "[Normal Crimson Queen] วันนี้เข้าสู้บอสไปแล้ว "
                                        + chr.getOneInfoQuestInteger(QuestExConstants.CrimsonQueen.getQuestID(), "eNum")
                                        + " ครั้ง สามารถเข้าได้ทั้งหมด " + (chr.getBossTier() + 1) + " ครั้ง"));
            }
            text += "#l\r\n#L1#ใช้ #i4033611##t4033611# เพื่อเข้าสู่ Chaos Mode (เลเวล 180 ขึ้นไป)";
            if (DBConfig.isGanglim) {
                text += " #r["
                        + getPlayer().getOneInfoQuestInteger(QuestExConstants.ChaosCrimsonQueen.getQuestID(), "eNum")
                        + "/" + (getPlayer().getBossTier() + 1) + "]#b";
                getPlayer().getPartyMembers().forEach(chr -> chr.dropMessage(5,
                        "[Chaos Crimson Queen] วันนี้เข้าสู้บอสไปแล้ว "
                                + chr.getOneInfoQuestInteger(QuestExConstants.ChaosCrimsonQueen.getQuestID(), "eNum")
                                + " ครั้ง สามารถเข้าได้ทั้งหมด " + (chr.getBossTier() + 1) + " ครั้ง"));
            }
            text += "#l\r\n#L3#เข้าสู่ Chaos Practice Mode (เลเวล 180 ขึ้นไป)#l";
            int v0 = self.askMenu(text);
            if (target.getParty() == null) {
                target.say("ดูเหมือนจะต้องมีปาร์ตี้ก่อนนะ ไปหาปาร์ตี้กันเถอะ");
            } else {
                if (target.getParty().isPartySameMap()) {
                    if (v0 == 0) { // Normal Mode
                        if (em.getProperty("status0").equals("0")) {
                            String overLap = checkEventNumber(getPlayer(), QuestExConstants.CrimsonQueen.getQuestID(),
                                    DBConfig.isGanglim);
                            if (overLap == null) {
                                String lastDate = checkEventLastDate(getPlayer(),
                                        QuestExConstants.CrimsonQueen.getQuestID());
                                if (lastDate == null || DBConfig.isGanglim) {
                                    String exMember = target.exchangeParty(4033611, -1);
                                    if (exMember == null) { // Try entry
                                        em.setProperty("status0", "1");
                                        updateLastDate(getPlayer(), QuestExConstants.CrimsonQueen.getQuestID());
                                        if (DBConfig.isGanglim) {
                                            updateQuestEx(getPlayer(), QuestExConstants.CrimsonQueen.getQuestID());
                                        }
                                        EventInstanceManager eim = em.readyInstance();
                                        eim.setProperty("map", 105200300);
                                        eim.setProperty("mode", "normal");
                                        getClient().getChannelServer().getMapFactory().getMap(105200300)
                                                .setLastRespawnTime(0);
                                        getClient().getChannelServer().getMapFactory().getMap(105200300)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200310)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200300)
                                                .setLastRespawnTime(Long.MAX_VALUE);
                                        eim.registerParty(target.getParty(), getPlayer().getMap());
                                    } else {
                                        target.say("สมาชิกในปาร์ตี้ #b" + exMember
                                                + "#k ไม่มี #i4033611##t4033611# จึงไม่สามารถเข้าได้");
                                    }
                                } else {
                                    target.say("สมาชิกในปาร์ตี้จะสามารถเข้าได้อีกครั้งในอีก #b#e" + lastDate + " #n#k");
                                }
                            } else {
                                target.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                        + "#n#k ได้ทำการท้าทายไปแล้วในวันนี้ จึงไม่สามารถเข้าได้");
                            }
                        } else {
                            self.say("แผนที่เต็ม ไม่สามารถใช้บริการได้ กรุณาลองใหม่ในแชนแนลอื่น");
                        }
                    } else if (v0 == 1) { // Chaos Mode
                        if (em.getProperty("Cstatus0").equals("0")) {
                            String overLap = checkEventNumber(getPlayer(),
                                    QuestExConstants.ChaosCrimsonQueen.getQuestID(), DBConfig.isGanglim);
                            if (overLap == null) {
                                String lastDate = checkEventLastDate(getPlayer(),
                                        QuestExConstants.ChaosCrimsonQueen.getQuestID());
                                if (lastDate == null || DBConfig.isGanglim) {
                                    String exMember = target.exchangeParty(4033611, -1);
                                    if (exMember == null) { // Try entry
                                        em.setProperty("Cstatus0", "1");
                                        updateLastDate(getPlayer(), QuestExConstants.ChaosCrimsonQueen.getQuestID());
                                        if (DBConfig.isGanglim) {
                                            updateQuestEx(getPlayer(), QuestExConstants.ChaosCrimsonQueen.getQuestID());
                                        }
                                        EventInstanceManager eim = em.readyInstance();
                                        eim.setProperty("map", 105200700);
                                        eim.setProperty("mode", "chaos");
                                        getClient().getChannelServer().getMapFactory().getMap(105200700)
                                                .setLastRespawnTime(0);
                                        getClient().getChannelServer().getMapFactory().getMap(105200700)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200710)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200700)
                                                .setLastRespawnTime(Long.MAX_VALUE);
                                        eim.registerParty(target.getParty(), getPlayer().getMap());
                                    } else {
                                        target.say("สมาชิกในปาร์ตี้ #b" + exMember
                                                + "#k ไม่มี #i4033611##t4033611# จึงไม่สามารถเข้าได้");
                                    }
                                } else {
                                    target.say("สมาชิกในปาร์ตี้จะสามารถเข้าได้อีกครั้งในอีก #b#e" + lastDate + " #n#k");
                                }
                            } else {
                                if (DBConfig.isGanglim) {
                                    target.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                            + "#n#k ได้ทำการท้าทายไปแล้วในวันนี้ จึงไม่สามารถเข้าได้");
                                } else {
                                    target.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                            + "#n#k ได้ทำการท้าทายไปแล้วในสัปดาห์นี้ จึงไม่สามารถเข้าได้");
                                }
                            }
                        } else {
                            self.say("แผนที่เต็ม ไม่สามารถใช้บริการได้ กรุณาลองใหม่ในแชนแนลอื่น");
                        }
                    } else if (v0 == 2) { // Practice Mode
                        self.say("โหมดฝึกซ้อมกำลังอยู่ระหว่างการเตรียมการ");
                    }
                } else {
                    self.say("สมาชิกปาร์ตี้ทุกคนต้องอยู่ในแผนที่เดียวกัน");
                }
            }
        }
    }

    public void queen_summon0() {
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            if (eim.getProperty("summonMOB") == null) {
                eim.setProperty("summonMOB", "1");
                Field field = getPlayer().getMap();
                field.startMapEffect("ลองไปคุยกับ Crimson Queen ที่กำลังหลับใหลดูสิ", 5120025, 3000);
            }
        }
    }

    public void rootaforthDoor() { // Vellum
        // North Garden
        // 105200400, 105200800
        // Cannot see Vellum
        // My warning
        initNPC(MapleLifeFactory.getNPC(1064014));
        EventManager em = getEventManager("RootAbyssVellum");
        if (em == null) {
            self.say("ไม่สามารถเข้าได้ในขณะนี้");
        } else {
            String text = "";
            if (DBConfig.isGanglim) {
                text = "#r#e<ทางเข้า Root Abyss North Garden>#n#k\r\nประตูสู่สวนที่ #rVellum#k ผู้พิทักษ์แห่งทิศเหนือเฝ้ารักษาอยู่ "
                        + (DBConfig.isGanglim ? "ประวัติการเคลียร์จะรีเซ็ตทุกเที่ยงคืน"
                                : "#rประวัติการเคลียร์ Normal จะรีเซ็ตทุกเที่ยงคืน, Chaos จะรีเซ็ตทุกวันพฤหัสบดี#b")
                        + "\r\n";
                text += "#L0#ใช้ #i4033611##t4033611# เพื่อเข้าสู่ Normal Mode (เลเวล 125 ขึ้นไป)";
                text += " #r[" + getPlayer().getOneInfoQuestInteger(QuestExConstants.Vellum.getQuestID(), "eNum") + "/"
                        + (getPlayer().getBossTier() + 1) + "]#k";
                getPlayer().getPartyMembers()
                        .forEach(chr -> chr.dropMessage(5,
                                "[Normal Vellum] วันนี้เข้าสู้บอสไปแล้ว "
                                        + chr.getOneInfoQuestInteger(QuestExConstants.Vellum.getQuestID(), "eNum")
                                        + " ครั้ง สามารถเข้าได้ทั้งหมด " + (chr.getBossTier() + 1) + " ครั้ง"));
                text += "#l\r\n#L1#ใช้ #i4033611##t4033611# เพื่อเข้าสู่ Chaos Mode (เลเวล 180 ขึ้นไป)";
                text += " #r[" + getPlayer().getOneInfoQuestInteger(QuestExConstants.ChaosVellum.getQuestID(), "eNum")
                        + "/" + (getPlayer().getBossTier() + 1) + "]#b";
                getPlayer().getPartyMembers()
                        .forEach(chr -> chr.dropMessage(5,
                                "[Chaos Vellum] วันนี้เข้าสู้บอสไปแล้ว "
                                        + chr.getOneInfoQuestInteger(QuestExConstants.ChaosVellum.getQuestID(), "eNum")
                                        + " ครั้ง สามารถเข้าได้ทั้งหมด " + (chr.getBossTier() + 1) + " ครั้ง"));
                text += "#l\r\n#L3#เข้าสู่ Chaos Practice Mode (เลเวล 180 ขึ้นไป)#l";
            } else {
                boolean single = getPlayer().getPartyMemberSize() == 1;
                text = "#r#e<ทางเข้า Root Abyss North Garden>#n#k\r\nประตูสู่สวนที่ #rVellum#k ผู้พิทักษ์แห่งทิศเหนือเฝ้ารักษาอยู่ "
                        + (DBConfig.isGanglim ? "ประวัติการเคลียร์จะรีเซ็ตทุกเที่ยงคืน"
                                : "#rประวัติการเคลียร์ Normal จะรีเซ็ตทุกเที่ยงคืน, Chaos จะรีเซ็ตทุกวันพฤหัสบดี#b")
                        + "\r\n";
                text += "#L0#ใช้ #i4033611##t4033611# เพื่อเข้าสู่ Normal Mode " + (single ? "(Single)" : "(Multi)")
                        + " (เลเวล 125 ขึ้นไป)";
                text += "#l\r\n#L1#ใช้ #i4033611##t4033611# เพื่อเข้าสู่ Chaos Mode "
                        + (single ? "(Single)" : "(Multi)")
                        + " (เลเวล 180 ขึ้นไป)";
                // text += "#l\r\n#L3#เข้าสู่ Chaos Practice Mode (เลเวล 180 ขึ้นไป)#l";
                int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.DailyQuestResetCount.getQuestID(),
                        "NormalVellum" + (single ? "Single" : "Multi"));
                text += "\r\n#L4#เพิ่มรอบเข้าเล่น Normal Mode " + (single ? "(Single)" : "(Multi)") + " (เหลือ "
                        + ((single ? 2 : 1) - reset)
                        + " ครั้ง) #l";
            }

            int v0 = self.askMenu(text);
            if (target.getParty() == null) {
                target.say("ดูเหมือนจะต้องมีปาร์ตี้ก่อนนะ ไปหาปาร์ตี้กันเถอะ");
                return;
            } else {
                if (v0 == 4 && !DBConfig.isGanglim) {
                    if (getPlayer().getTogetherPoint() < 150) {
                        self.sayOk("คะแนนความร่วมมือไม่เพียงพอ คะแนนปัจจุบัน : " + getPlayer().getTogetherPoint());
                        return;
                    }
                    boolean single = getPlayer().getPartyMemberSize() == 1;
                    int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.DailyQuestResetCount.getQuestID(),
                            "NormalVellum" + (single ? "Single" : "Multi"));
                    if ((reset > 0 && !single) || reset > 1 && single) { // Cannot reset
                        self.sayOk("คุณใช้จำนวนครั้งที่สามารถเพิ่มได้ในวันนี้หมดแล้ว");
                        return;
                    }
                    getPlayer().gainTogetherPoint(-150);
                    getPlayer().updateOneInfo(QuestExConstants.DailyQuestResetCount.getQuestID(),
                            "NormalVellum" + (single ? "Single" : "Multi"), String.valueOf(reset + 1));
                    self.sayOk("เพิ่มจำนวนครั้งที่เข้าเล่นเรียบร้อยแล้ว");
                    return;
                }
                if (target.getParty().isPartySameMap()) {
                    if (v0 == 0) { // Normal Mode
                        if (em.getProperty("status0").equals("0")) {
                            String overLap = checkEventNumber(getPlayer(), QuestExConstants.Vellum.getQuestID(),
                                    DBConfig.isGanglim);
                            if (overLap == null) {
                                String lastDate = checkEventLastDate(getPlayer(), QuestExConstants.Vellum.getQuestID());
                                if (lastDate == null || DBConfig.isGanglim) {
                                    String exMember = target.exchangeParty(4033611, -1);
                                    if (exMember == null) { // Try entry
                                        em.setProperty("status0", "1");
                                        updateLastDate(getPlayer(), QuestExConstants.Vellum.getQuestID());
                                        if (DBConfig.isGanglim) {
                                            updateQuestEx(getPlayer(), QuestExConstants.Vellum.getQuestID());
                                        }
                                        if (!DBConfig.isGanglim && getPlayer().getPartyMemberSize() > 1) {
                                            for (MapleCharacter partyMember : getPlayer().getPartyMembers()) {
                                                if (partyMember.getMapId() == getPlayer().getMapId()) {
                                                    partyMember.setMultiMode(true);
                                                    partyMember.applyBMCurseJinMulti();
                                                }
                                            }
                                        }
                                        EventInstanceManager eim = em.readyInstance();
                                        eim.setProperty("map", 105200400);
                                        eim.setProperty("mode", "normal");
                                        getClient().getChannelServer().getMapFactory().getMap(105200400)
                                                .setLastRespawnTime(0);
                                        getClient().getChannelServer().getMapFactory().getMap(105200400)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200410)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200400)
                                                .setLastRespawnTime(Long.MAX_VALUE);
                                        eim.registerParty(target.getParty(), getPlayer().getMap());
                                    } else {
                                        target.say("สมาชิกในปาร์ตี้ #b" + exMember
                                                + "#k ไม่มี #i4033611##t4033611# จึงไม่สามารถเข้าได้");
                                    }
                                } else {
                                    target.say("สมาชิกในปาร์ตี้จะสามารถเข้าได้อีกครั้งในอีก #b#e" + lastDate + " #n#k");
                                }
                            } else {
                                target.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                        + "#n#k ได้ทำการท้าทายไปแล้วในวันนี้ จึงไม่สามารถเข้าได้");
                            }
                        } else {
                            self.say("แผนที่เต็ม ไม่สามารถใช้บริการได้ กรุณาลองใหม่ในแชนแนลอื่น");
                        }
                    } else if (v0 == 1) { // Chaos Mode
                        if (em.getProperty("Cstatus0").equals("0")) {
                            String overLap = checkEventNumber(getPlayer(), QuestExConstants.ChaosVellum.getQuestID(),
                                    DBConfig.isGanglim);
                            if (overLap == null) {
                                String lastDate = checkEventLastDate(getPlayer(),
                                        QuestExConstants.ChaosVellum.getQuestID());
                                if (lastDate == null || DBConfig.isGanglim) {
                                    String exMember = target.exchangeParty(4033611, -1);
                                    if (exMember == null) { // Try entry
                                        em.setProperty("Cstatus0", "1");
                                        updateLastDate(getPlayer(), QuestExConstants.ChaosVellum.getQuestID());
                                        if (DBConfig.isGanglim) {
                                            updateQuestEx(getPlayer(), QuestExConstants.ChaosVellum.getQuestID());
                                        }
                                        if (!DBConfig.isGanglim && getPlayer().getPartyMemberSize() > 1) {
                                            for (MapleCharacter partyMember : getPlayer().getPartyMembers()) {
                                                if (partyMember.getMapId() == getPlayer().getMapId()) {
                                                    partyMember.setMultiMode(true);
                                                    partyMember.applyBMCurseJinMulti();
                                                }
                                            }
                                        }
                                        EventInstanceManager eim = em.readyInstance();
                                        eim.setProperty("map", 105200800);
                                        eim.setProperty("mode", "chaos");
                                        getClient().getChannelServer().getMapFactory().getMap(105200800)
                                                .setLastRespawnTime(0);
                                        getClient().getChannelServer().getMapFactory().getMap(105200800)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200810)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200800)
                                                .setLastRespawnTime(Long.MAX_VALUE);
                                        eim.registerParty(target.getParty(), getPlayer().getMap());
                                    } else {
                                        target.say("สมาชิกในปาร์ตี้ #b" + exMember
                                                + "#k ไม่มี #i4033611##t4033611# จึงไม่สามารถเข้าได้");
                                    }
                                } else {
                                    target.say("สมาชิกในปาร์ตี้จะสามารถเข้าได้อีกครั้งในอีก #b#e" + lastDate + " #n#k");
                                }
                            } else {
                                if (DBConfig.isGanglim) {
                                    target.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                            + "#n#k ได้ทำการท้าทายไปแล้วในวันนี้ จึงไม่สามารถเข้าได้");
                                } else {
                                    target.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                            + "#n#k ได้ทำการท้าทายไปแล้วในสัปดาห์นี้ จึงไม่สามารถเข้าได้");
                                }
                            }
                        } else {
                            self.say("แผนที่เต็ม ไม่สามารถใช้บริการได้ กรุณาลองใหม่ในแชนแนลอื่น");
                        }
                    } else if (v0 == 2) { // Practice Mode
                        self.say("โหมดฝึกซ้อมกำลังอยู่ระหว่างการเตรียมการ");
                    }
                } else {
                    self.say("สมาชิกปาร์ตี้ทุกคนต้องอยู่ในแผนที่เดียวกัน");
                }
            }
        }
    }

    public void bellumEnter() { // Vellum
        if (!DBConfig.isGanglim) {
            rootaforthDoor();
            return;
        }
        EventManager em = getEventManager("RootAbyssVellum");
        if (em == null) {
            self.say("ไม่สามารถเข้าได้ในขณะนี้");
        } else {
            String text = "#r#e<ทางเข้า Root Abyss North Garden>#n#k\r\nประตูสู่สวนที่ #rVellum#k ผู้พิทักษ์แห่งทิศเหนือเฝ้ารักษาอยู่ "
                    + (DBConfig.isGanglim ? "ประวัติการเคลียร์จะรีเซ็ตทุกเที่ยงคืน"
                            : "#rประวัติการเคลียร์ Normal จะรีเซ็ตทุกเที่ยงคืน, Chaos จะรีเซ็ตทุกวันพฤหัสบดี#b")
                    + "\r\n";
            text += "#L0#ใช้ #i4033611##t4033611# เพื่อเข้าสู่ Normal Mode (เลเวล 125 ขึ้นไป)";
            if (DBConfig.isGanglim) {
                text += " #r[" + getPlayer().getOneInfoQuestInteger(QuestExConstants.Vellum.getQuestID(), "eNum") + "/"
                        + (getPlayer().getBossTier() + 1) + "]#k";
                getPlayer().getPartyMembers()
                        .forEach(chr -> chr.dropMessage(5,
                                "[Normal Vellum] วันนี้เข้าสู้บอสไปแล้ว "
                                        + chr.getOneInfoQuestInteger(QuestExConstants.Vellum.getQuestID(), "eNum")
                                        + " ครั้ง สามารถเข้าได้ทั้งหมด " + (chr.getBossTier() + 1) + " ครั้ง"));
            }
            text += "#l\r\n#L1#ใช้ #i4033611##t4033611# เพื่อเข้าสู่ Chaos Mode (เลเวล 180 ขึ้นไป)";
            if (DBConfig.isGanglim) {
                text += " #r[" + getPlayer().getOneInfoQuestInteger(QuestExConstants.ChaosVellum.getQuestID(), "eNum")
                        + "/" + (getPlayer().getBossTier() + 1) + "]#b";
                getPlayer().getPartyMembers()
                        .forEach(chr -> chr.dropMessage(5,
                                "[Chaos Vellum] วันนี้เข้าสู้บอสไปแล้ว "
                                        + chr.getOneInfoQuestInteger(QuestExConstants.ChaosVellum.getQuestID(), "eNum")
                                        + " ครั้ง สามารถเข้าได้ทั้งหมด " + (chr.getBossTier() + 1) + " ครั้ง"));
            }
            text += "#l\r\n#L3#เข้าสู่ Chaos Practice Mode (เลเวล 180 ขึ้นไป)#l";
            int v0 = self.askMenu(text);
            if (target.getParty() == null) {
                target.say("ดูเหมือนจะต้องมีปาร์ตี้ก่อนนะ ไปหาปาร์ตี้กันเถอะ");
            } else {
                if (target.getParty().isPartySameMap()) {
                    if (v0 == 0) { // Normal Mode
                        if (em.getProperty("status0").equals("0")) {
                            String overLap = checkEventNumber(getPlayer(), QuestExConstants.Vellum.getQuestID(),
                                    DBConfig.isGanglim);
                            if (overLap == null) {
                                String lastDate = checkEventLastDate(getPlayer(), QuestExConstants.Vellum.getQuestID());
                                if (lastDate == null || DBConfig.isGanglim) {
                                    String exMember = target.exchangeParty(4033611, -1);
                                    if (exMember == null) { // Try entry
                                        em.setProperty("status0", "1");
                                        updateLastDate(getPlayer(), QuestExConstants.Vellum.getQuestID());
                                        if (DBConfig.isGanglim) {
                                            updateQuestEx(getPlayer(), QuestExConstants.Vellum.getQuestID());
                                        }
                                        EventInstanceManager eim = em.readyInstance();
                                        eim.setProperty("map", 105200400);
                                        eim.setProperty("mode", "normal");
                                        getClient().getChannelServer().getMapFactory().getMap(105200400)
                                                .setLastRespawnTime(0);
                                        getClient().getChannelServer().getMapFactory().getMap(105200400)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200410)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200400)
                                                .setLastRespawnTime(Long.MAX_VALUE);
                                        eim.registerParty(target.getParty(), getPlayer().getMap());
                                    } else {
                                        target.say("สมาชิกในปาร์ตี้ #b" + exMember
                                                + "#k ไม่มี #i4033611##t4033611# จึงไม่สามารถเข้าได้");
                                    }
                                } else {
                                    target.say("สมาชิกในปาร์ตี้จะสามารถเข้าได้อีกครั้งในอีก #b#e" + lastDate + " #n#k");
                                }
                            } else {
                                target.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                        + "#n#k ได้ทำการท้าทายไปแล้วในวันนี้ จึงไม่สามารถเข้าได้");
                            }
                        } else {
                            self.say("แผนที่เต็ม ไม่สามารถใช้บริการได้ กรุณาลองใหม่ในแชนแนลอื่น");
                        }
                    } else if (v0 == 1) { // Chaos Mode
                        if (em.getProperty("Cstatus0").equals("0")) {
                            String overLap = checkEventNumber(getPlayer(), QuestExConstants.ChaosVellum.getQuestID(),
                                    DBConfig.isGanglim);
                            if (overLap == null) {
                                String lastDate = checkEventLastDate(getPlayer(),
                                        QuestExConstants.ChaosVellum.getQuestID());
                                if (lastDate == null || DBConfig.isGanglim) {
                                    String exMember = target.exchangeParty(4033611, -1);
                                    if (exMember == null) { // Try entry
                                        em.setProperty("Cstatus0", "1");
                                        updateLastDate(getPlayer(), QuestExConstants.ChaosVellum.getQuestID());
                                        if (DBConfig.isGanglim) {
                                            updateQuestEx(getPlayer(), QuestExConstants.ChaosVellum.getQuestID());
                                        }
                                        EventInstanceManager eim = em.readyInstance();
                                        eim.setProperty("map", 105200800);
                                        eim.setProperty("mode", "chaos");
                                        getClient().getChannelServer().getMapFactory().getMap(105200800)
                                                .setLastRespawnTime(0);
                                        getClient().getChannelServer().getMapFactory().getMap(105200800)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200810)
                                                .resetFully(true);
                                        getClient().getChannelServer().getMapFactory().getMap(105200800)
                                                .setLastRespawnTime(Long.MAX_VALUE);
                                        eim.registerParty(target.getParty(), getPlayer().getMap());
                                    } else {
                                        target.say("สมาชิกในปาร์ตี้ #b" + exMember
                                                + "#k ไม่มี #i4033611##t4033611# จึงไม่สามารถเข้าได้");
                                    }
                                } else {
                                    target.say("สมาชิกในปาร์ตี้จะสามารถเข้าได้อีกครั้งในอีก #b#e" + lastDate + " #n#k");
                                }
                            } else {
                                if (DBConfig.isGanglim) {
                                    target.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                            + "#n#k ได้ทำการท้าทายไปแล้วในวันนี้ จึงไม่สามารถเข้าได้");
                                } else {
                                    target.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                            + "#n#k ได้ทำการท้าทายไปแล้วในสัปดาห์นี้ จึงไม่สามารถเข้าได้");
                                }
                            }
                        } else {
                            self.say("แผนที่เต็ม ไม่สามารถใช้บริการได้ กรุณาลองใหม่ในแชนแนลอื่น");
                        }
                    } else if (v0 == 2) { // Practice Mode
                        self.say("โหมดฝึกซ้อมกำลังอยู่ระหว่างการเตรียมการ");
                    }
                } else {
                    self.say("สมาชิกปาร์ตี้ทุกคนต้องอยู่ในแผนที่เดียวกัน");
                }
            }
        }
    }

    public void abysscave_ent() {
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            if (eim.getProperty("summonMOB") == null) {
                eim.setProperty("summonMOB", "1");
                Field field = getPlayer().getMap();
                field.startMapEffect("ไม่เห็นตัว Vellum เลย ลองสำรวจแถวๆ แท่นบูชาดูสิ", 5120025, 3000);
            }
        }
    }

    public void rootabyssOut() {
        initNPC(MapleLifeFactory.getNPC(1064012));
        if (target.askYesNo(
                "ใช้กุญแจไปแล้ว ถ้าออกไปตอนนี้จะเสียกุญแจไปเปล่าๆ สู้บอสให้ชนะก่อนดีกว่ามั้ย... แต่ก็จะออกเลยเหรอ?") == 1) {
            if (getPlayer().getEventInstance() != null) {
                getPlayer().setRegisterTransferFieldTime(0);
                getPlayer().setRegisterTransferField(0);
            }
            registerTransferField(105200000);
        } else {
            target.sayOk("ไหนๆ ก็ใช้กุญแจเข้ามาแล้ว จัดการบอสให้ได้ก่อนละกัน");
        }
    }

    public void outrootaBoss() {
        initNPC(MapleLifeFactory.getNPC(1064012));
        if (target.askYesNo("จบการต่อสู้แล้ว จะออกไปข้างนอกไหม?") == 1) {
            if (getPlayer().getEventInstance() != null) {
                getPlayer().setRegisterTransferFieldTime(0);
                getPlayer().setRegisterTransferField(0);
            }
            registerTransferField(105200000);
        }
    }

    public void rootaBossOut() {
        initNPC(MapleLifeFactory.getNPC(1064012));
        if (target.askYesNo("จบการต่อสู้แล้ว จะออกไปข้างนอกไหม?") == 1) {
            if (getPlayer().getEventInstance() != null) {
                getPlayer().setRegisterTransferFieldTime(0);
                getPlayer().setRegisterTransferField(0);
            }
            registerTransferField(105200000);
        }
    }

    public void rootaNext() {
        initNPC(MapleLifeFactory.getNPC(1064012));
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            if (getPlayer().getMap().getAllMonster().size() == 0) {
                if (target.askYesNo("งั้นไปกันเลยมั้ย?") == 1) {
                    if (eim.getProperty("stage1") == null) {
                        eim.setProperty("stage1", "clear");
                        target.getParty().registerTransferField(target.getMapId() + 10);
                    }
                } else {
                    target.sayOk("ขอเตรียมตัวอีกหน่อยแล้วค่อยไปนะ");
                }
            } else {
                getPlayer().getMap()
                        .broadcastMessage(CWvsContext.getScriptProgressMessage("กำจัด Imp ในสวนให้หมดก่อน"));
                getPlayer().getMap().broadcastMessage(CWvsContext.serverNotice(5, "กำจัด Imp ในสวนให้หมดก่อน"));
            }
        }
    }
}
