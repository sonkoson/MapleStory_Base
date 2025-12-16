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
            self.say("지금은 힐라 레이드를 이용하실 수 없.");
            return;
        }
        if (target.getParty() == null) {
            self.sayOk("1인 이상 ปาร์ตี้ 맺어야만 เข้า할 수 있.");
            return;
        }
        if (target.getParty().getLeader().getId() != target.getId()) {
            self.sayOk("ปาร์ตี้장을 통해 ดำเนินการ해 สัปดาห์십시오.");
            return;
        }
        int v0 = self.askMenu("#e<บอส:힐라>#n\r\n힐라를 처치, 아스완의 진정한 해ห้อง을 이뤄낼 เตรียม는 되셨습니까? อื่น พื้นที่에 있는 ปาร์ตี้원이 มี면, ทั้งหมด 모여 สัปดาห์세요.\r\n#b\r\n#L0# <บอส:힐라> เข้า을 สมัคร한다.#l");
        if (v0 == 0) {
            String v1Menu = "\r\n#L1# 하드 โหมด ( เลเวล 170 이상 )#l\r\n#L2# 하드 연습 โหมด ( เลเวล 170 이상 )#l"; //ฉัน중에 เพิ่ม 될 하드โหมดและ 연습โหมด
            int v1 = self.askMenu("#e<บอส:힐라>#n\r\n원하시는 โหมด를 เลือกโปรด.\r\n\r\n#L0# 노멀 โหมด ( เลเวล 120 이상 )#l");
            if (v1 != -1) {
                if (target.getParty().isPartySameMap()) {
                    if (v1 == 0) { //노말힐라
                        String overLap = checkEventNumber(getPlayer(), QuestExConstants.Hillah.getQuestID(), DBConfig.isGanglim);
                        if (overLap == null) {
                            boolean canEnter = false;
                            if (em.getProperty("status0").equals("0")) {
                                canEnter = true;
                            }
                            if (canEnter) {
                                em.setProperty("status0", "1");
                                EventInstanceManager eim = em.readyInstance();
                                eim.setProperty("map", 262030100); //복도1
                                MapleMapFactory mFactory = getClient().getChannelServer().getMapFactory();
                                mFactory.getMap(262030100).setLastRespawnTime(0);
                                mFactory.getMap(262030100).resetFully(true);
                                mFactory.getMap(262030100).setLastRespawnTime(Long.MAX_VALUE);

                                mFactory.getMap(262030200).setLastRespawnTime(0);
                                mFactory.getMap(262030200).resetFully(true);
                                mFactory.getMap(262030200).setLastRespawnTime(Long.MAX_VALUE);

                                mFactory.getMap(262030300).resetFully(false);
                                mFactory.getMap(262030300).spawnMonster(MapleLifeFactory.getMonster(8870000), new Point(165, 196), 0);
                                updateEventNumber(getPlayer(), QuestExConstants.Hillah.getQuestID()); //힐라는
                                eim.registerParty(target.getParty(), getPlayer().getMap());
                            } else {
                                self.sayOk("ปัจจุบัน ทั้งหมดแผนที่ 가득ชา 이용하실 수 없. อื่น แชนแนล 이용โปรด.");
                            }
                        } else {
                            self.sayOk("ปาร์ตี้원 중 #b#e" + overLap + "#n#k วันนี้ เข้า했. <บอส:힐라> 노멀 โหมด는 하루에 1번만 도전하실 수 있.");
                        }
                    }
                } else {
                    self.sayOk(target.getParty().getPartyMemberList().size() + "명 ทั้งหมด เหมือนกันแผนที่ 있어야 .");
                }
            }
        }
    }

    public void in_hillah() {
        initNPC(MapleLifeFactory.getNPC(2184001));
        EventManager em = getEventManager("Hillah");
        if (em == null) {
            self.say("지금은 힐라 레이드를 이용하실 수 없.");
            return;
        }
        if (target.getParty() == null) {
            self.sayOk("1인 이상 ปาร์ตี้ 맺어야만 เข้า할 수 있.");
            return;
        }
        if (target.getParty().getLeader().getId() != target.getId()) {
            self.sayOk("ปาร์ตี้장을 통해 ดำเนินการ해 สัปดาห์십시오.");
            return;
        }
        int v0 = self.askMenu("#e<บอส:힐라>#n\r\n힐라를 처치, 아스완의 진정한 해ห้อง을 이뤄낼 เตรียม는 되셨습니까? อื่น พื้นที่에 있는 ปาร์ตี้원이 มี면, ทั้งหมด 모여 สัปดาห์세요.\r\n#b\r\n#L0# <บอส:힐라> เข้า을 สมัคร한다.#l");
        if (v0 == 0) {
            String v1Menu = "\r\n#L1# 하드 โหมด ( เลเวล 170 이상 )#l\r\n#L2# 하드 연습 โหมด ( เลเวล 170 이상 )#l"; //ฉัน중에 เพิ่ม 될 하드โหมดและ 연습โหมด
            int v1 = self.askMenu("#e<บอส:힐라>#n\r\n원하시는 โหมด를 เลือกโปรด.\r\n\r\n#L0# 노멀 โหมด ( เลเวล 120 이상 )#l");
            if (v1 != -1) {
                if (target.getParty().isPartySameMap()) {
                    if (v1 == 0) { //노말힐라
                        String overLap = checkEventNumber(getPlayer(), QuestExConstants.Hillah.getQuestID());
                        if (overLap == null) {
                            boolean canEnter = false;
                            if (em.getProperty("status0").equals("0")) {
                                canEnter = true;
                            }
                            if (canEnter) {
                                em.setProperty("status0", "1");
                                EventInstanceManager eim = em.readyInstance();
                                eim.setProperty("map", 262030100); //복도1
                                MapleMapFactory mFactory = getClient().getChannelServer().getMapFactory();
                                mFactory.getMap(262030100).setLastRespawnTime(0);
                                mFactory.getMap(262030100).resetFully(true);
                                mFactory.getMap(262030100).setLastRespawnTime(Long.MAX_VALUE);

                                mFactory.getMap(262030200).setLastRespawnTime(0);
                                mFactory.getMap(262030200).resetFully(true);
                                mFactory.getMap(262030200).setLastRespawnTime(Long.MAX_VALUE);

                                mFactory.getMap(262030300).resetFully(false);
                                mFactory.getMap(262030300).spawnMonster(MapleLifeFactory.getMonster(8870000), new Point(165, 196), 0);

                                updateEventNumber(getPlayer(), QuestExConstants.Hillah.getQuestID()); //힐라는
                                eim.registerParty(target.getParty(), getPlayer().getMap());
                            }
                        } else {
                            self.sayOk("ปาร์ตี้원 중 #b#e" + overLap + "#n#k วันนี้ เข้า했. <บอส:힐라> 노멀 โหมด는 하루에 1번만 도전하실 수 있.");
                        }
                    }
                } else {
                    self.sayOk(target.getParty().getPartyMemberList().size() + "명 ทั้งหมด เหมือนกันแผนที่ 있어야 .");
                }
            }
        }
    }

    @Script
    public void hillah_next() {
        EventInstanceManager eim = getEventInstance();
        if (eim == null) {
            getPlayer().dropMessage(5, "이벤트 인스턴스가 없.");
            return;
        }
        Field field = getPlayer().getMap();
        if (field.getAllMonster().size() == 0) {
            switch (field.getId()) {
                case 262030100:
                    if (eim.getProperty("stage1_bloodTooth") == null) {
                        eim.setProperty("stage1_bloodTooth", "1");
                        field.broadcastMessage(CWvsContext.getScriptProgressMessage("블러드투스가 เรา의 침입을 눈치챘!!! 블러드투스를 น้ำ리치세요."));
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
                        field.broadcastMessage(CWvsContext.getScriptProgressMessage("블러드투스가 เรา의 침입을 눈치챘!!! 블러드투스를 น้ำ리치세요."));
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
            getPlayer().dropMessage(5, "아직은 포탈을 이용하실 수 없.");
        }
    }

    public void out_hillah() {
        initNPC(MapleLifeFactory.getNPC(2184001));
        if (1 == self.askYesNo("이대로 포기ต้องการหรือไม่?")) {
            self.say("어쩔 수 없군요. ที่นี่ถึง 도และสัปดาห์셔서 감사했어요.");
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
