package script.Boss;

import constants.QuestExConstants;
import database.DBConfig;
import network.models.CWvsContext;
import objects.fields.Field;
import objects.fields.MapleMapFactory;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import scripting.EventInstanceManager;
import scripting.EventManager;
import scripting.newscripting.Script;
import scripting.newscripting.ScriptEngineNPC;

import java.awt.*;

public class Hillah extends ScriptEngineNPC {

    public void hillah_accept() {
        EventManager em = getEventManager("Hillah");
        if (em == null) {
            self.say("ขณะนี้ไม่สามารถเข้าร่วม Hilla Raid ได้ครับ");
            return;
        }
        if (target.getParty() == null) {
            self.sayOk("ต้องสร้างปาร์ตี้ตั้งแต่ 1 คนขึ้นไปถึงจะเข้าได้ครับ");
            return;
        }
        if (target.getParty().getLeader().getId() != target.getId()) {
            self.sayOk("กรุณาให้หัวหน้าปาร์ตี้เป็นผู้ดำเนินการครับ");
            return;
        }
        int v0 = self.askMenu(
                "#e<บอส: Hilla>#n\r\nท่านพร้อมที่จะกำจัด Hilla และปลดปล่อย Azwan ให้เป็นอิสระอย่างแท้จริงแล้วหรือยัง? หากมีสมาชิกปาร์ตี้อยู่ในพื้นที่อื่น กรุณารวมตัวกันให้ครบด้วยครับ\r\n#b\r\n#L0# สมัครเข้าสู่ <บอส: Hilla>#l");
        if (v0 == 0) {
            String v1Menu = "\r\n#L1# Hard Mode (เลเวล 170 ขึ้นไป)#l\r\n#L2# Hard Practice Mode (เลเวล 170 ขึ้นไป)#l"; // ในอนาคตจะเพิ่ม
                                                                                                                       // Hard
                                                                                                                       // Mode
                                                                                                                       // และ
                                                                                                                       // Practice
                                                                                                                       // Mode
            int v1 = self.askMenu(
                    "#e<บอส: Hilla>#n\r\nกรุณาเลือกโหมดที่ต้องการ\r\n\r\n#L0# Normal Mode (เลเวล 120 ขึ้นไป)#l");
            if (v1 != -1) {
                if (target.getParty().isPartySameMap()) {
                    if (v1 == 0) { // Normal Hilla
                        String overLap = checkEventNumber(getPlayer(), QuestExConstants.Hillah.getQuestID(),
                                DBConfig.isGanglim);
                        if (overLap == null) {
                            boolean canEnter = false;
                            if (em.getProperty("status0").equals("0")) {
                                canEnter = true;
                            }
                            if (canEnter) {
                                em.setProperty("status0", "1");
                                EventInstanceManager eim = em.readyInstance();
                                eim.setProperty("map", 262030100); // Corridor 1
                                MapleMapFactory mFactory = getClient().getChannelServer().getMapFactory();
                                mFactory.getMap(262030100).setLastRespawnTime(0);
                                mFactory.getMap(262030100).resetFully(true);
                                mFactory.getMap(262030100).setLastRespawnTime(Long.MAX_VALUE);

                                mFactory.getMap(262030200).setLastRespawnTime(0);
                                mFactory.getMap(262030200).resetFully(true);
                                mFactory.getMap(262030200).setLastRespawnTime(Long.MAX_VALUE);

                                mFactory.getMap(262030300).resetFully(false);
                                mFactory.getMap(262030300).spawnMonster(MapleLifeFactory.getMonster(8870000),
                                        new Point(165, 196), 0);
                                updateEventNumber(getPlayer(), QuestExConstants.Hillah.getQuestID()); // Hilla
                                eim.registerParty(target.getParty(), getPlayer().getMap());
                            } else {
                                self.sayOk("ขณะนี้แผนที่ทั้งหมดเต็มแล้วครับ กรุณาใช้แชนแนลอื่น");
                            }
                        } else {
                            self.sayOk("สมาชิกในปาร์ตี้ #b#e" + overLap
                                    + "#n#k เข้าไปแล้วในวันนี้ <บอส: Hilla> Normal Mode สามารถเข้าได้วันละ 1 ครั้งครับ");
                        }
                    }
                } else {
                    self.sayOk(
                            target.getParty().getPartyMemberList().size() + "คน ทั้งหมดต้องอยู่ในแผนที่เดียวกันครับ");
                }
            }
        }
    }

    public void in_hillah() {
        initNPC(MapleLifeFactory.getNPC(2184001));
        EventManager em = getEventManager("Hillah");
        if (em == null) {
            self.say("ขณะนี้ไม่สามารถเข้าร่วม Hilla Raid ได้ครับ");
            return;
        }
        if (target.getParty() == null) {
            self.sayOk("ต้องสร้างปาร์ตี้ตั้งแต่ 1 คนขึ้นไปถึงจะเข้าได้ครับ");
            return;
        }
        if (target.getParty().getLeader().getId() != target.getId()) {
            self.sayOk("กรุณาให้หัวหน้าปาร์ตี้เป็นผู้ดำเนินการครับ");
            return;
        }
        int v0 = self.askMenu(
                "#e<บอส: Hilla>#n\r\nท่านพร้อมที่จะกำจัด Hilla และปลดปล่อย Azwan ให้เป็นอิสระอย่างแท้จริงแล้วหรือยัง? หากมีสมาชิกปาร์ตี้อยู่ในพื้นที่อื่น กรุณารวมตัวกันให้ครบด้วยครับ\r\n#b\r\n#L0# สมัครเข้าสู่ <บอส: Hilla>#l");
        if (v0 == 0) {
            String v1Menu = "\r\n#L1# Hard Mode (เลเวล 170 ขึ้นไป)#l\r\n#L2# Hard Practice Mode (เลเวล 170 ขึ้นไป)#l"; // ในอนาคตจะเพิ่ม
                                                                                                                       // Hard
                                                                                                                       // Mode
                                                                                                                       // และ
                                                                                                                       // Practice
                                                                                                                       // Mode
            int v1 = self.askMenu(
                    "#e<บอส: Hilla>#n\r\nกรุณาเลือกโหมดที่ต้องการ\r\n\r\n#L0# Normal Mode (เลเวล 120 ขึ้นไป)#l");
            if (v1 != -1) {
                if (target.getParty().isPartySameMap()) {
                    if (v1 == 0) { // Normal Hilla
                        String overLap = checkEventNumber(getPlayer(), QuestExConstants.Hillah.getQuestID());
                        if (overLap == null) {
                            boolean canEnter = false;
                            if (em.getProperty("status0").equals("0")) {
                                canEnter = true;
                            }
                            if (canEnter) {
                                em.setProperty("status0", "1");
                                EventInstanceManager eim = em.readyInstance();
                                eim.setProperty("map", 262030100); // Corridor 1
                                MapleMapFactory mFactory = getClient().getChannelServer().getMapFactory();
                                mFactory.getMap(262030100).setLastRespawnTime(0);
                                mFactory.getMap(262030100).resetFully(true);
                                mFactory.getMap(262030100).setLastRespawnTime(Long.MAX_VALUE);

                                mFactory.getMap(262030200).setLastRespawnTime(0);
                                mFactory.getMap(262030200).resetFully(true);
                                mFactory.getMap(262030200).setLastRespawnTime(Long.MAX_VALUE);

                                mFactory.getMap(262030300).resetFully(false);
                                mFactory.getMap(262030300).spawnMonster(MapleLifeFactory.getMonster(8870000),
                                        new Point(165, 196), 0);

                                updateEventNumber(getPlayer(), QuestExConstants.Hillah.getQuestID()); // Hilla
                                eim.registerParty(target.getParty(), getPlayer().getMap());
                            }
                        } else {
                            self.sayOk("สมาชิกในปาร์ตี้ #b#e" + overLap
                                    + "#n#k เข้าไปแล้วในวันนี้ <บอส: Hilla> Normal Mode สามารถเข้าได้วันละ 1 ครั้งครับ");
                        }
                    }
                } else {
                    self.sayOk(
                            target.getParty().getPartyMemberList().size() + "คน ทั้งหมดต้องอยู่ในแผนที่เดียวกันครับ");
                }
            }
        }
    }

    @Script
    public void hillah_next() {
        EventInstanceManager eim = getEventInstance();
        if (eim == null) {
            getPlayer().dropMessage(5, "ไม่พบอินสแตนซ์ของกิจกรรม");
            return;
        }
        Field field = getPlayer().getMap();
        if (field.getAllMonster().size() == 0) {
            switch (field.getId()) {
                case 262030100:
                    if (eim.getProperty("stage1_bloodTooth") == null) {
                        eim.setProperty("stage1_bloodTooth", "1");
                        field.broadcastMessage(CWvsContext.getScriptProgressMessage(
                                "Blood Tooth จับได้ว่าเราบุกรุกเข้ามา!!! จงกำจัด Blood Tooth ซะ"));
                        field.spawnMonster(MapleLifeFactory.getMonster(8870003), new Point(777, 196), 43);
                        field.spawnMonster(MapleLifeFactory.getMonster(8870003), new Point(777, 196), 43);
                        field.spawnMonster(MapleLifeFactory.getMonster(8870003), new Point(777, 196), 43);
                        field.spawnMonster(MapleLifeFactory.getMonster(8870003), new Point(777, 196), 43);
                    } else {
                        if (field.getAllMonster().size() == 0) {
                            registerTransferField(field.getId() + 100);
                        }
                    }
                    break;
                case 262030200:
                    if (eim.getProperty("stage2_bloodTooth") == null) {
                        eim.setProperty("stage2_bloodTooth", "1");
                        field.broadcastMessage(CWvsContext.getScriptProgressMessage(
                                "Blood Tooth จับได้ว่าเราบุกรุกเข้ามา!!! จงกำจัด Blood Tooth ซะ"));
                        field.spawnMonster(MapleLifeFactory.getMonster(8870003), new Point(777, 196), 43);
                        field.spawnMonster(MapleLifeFactory.getMonster(8870003), new Point(777, 196), 43);
                        field.spawnMonster(MapleLifeFactory.getMonster(8870003), new Point(777, 196), 43);
                        field.spawnMonster(MapleLifeFactory.getMonster(8870003), new Point(777, 196), 43);
                    } else {
                        if (field.getAllMonster().size() == 0) {
                            registerTransferField(field.getId() + 100);
                        }
                    }
                    break;
            }
        } else {
            getPlayer().dropMessage(5, "ยังไม่สามารถใช้พอร์ทัลได้");
        }
    }

    public void out_hillah() {
        initNPC(MapleLifeFactory.getNPC(2184001));
        if (1 == self.askYesNo("ต้องการยอมแพ้แค่นี้หรือ?")) {
            self.say("ช่วยไม่ได้สินะ ขอบคุณที่อุตส่าห์ท้าทายมาถึงที่นี่");
            getPlayer().setRegisterTransferFieldTime(0);
            getPlayer().setRegisterTransferField(0);
            target.registerTransferField(262030000);
            if (getPlayer().getEventInstance() != null) {
                getPlayer().getEventInstance().unregisterPlayer(getPlayer());
                getPlayer().setEventInstance(null);
            }
        }
    }

    @Script
    public void UIOpen() {
        registerTransferField(262030000);
    }
}
