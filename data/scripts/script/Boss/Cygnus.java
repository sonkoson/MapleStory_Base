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
        //인스턴스แผนที่ 271040300, 271041300(볼품없는정원)
        initNPC(MapleLifeFactory.getNPC(2143004));
        if (DBConfig.isGanglim) {
            getPlayer().dropMessage(5, "ปัจจุบัน 타락한 시그너스에 เข้าร่วม할 수 없.");
            return;
        }
        EventManager em = getEventManager("Cygnus");
        if (em == null) {
            self.say("ปัจจุบัน 시그너스 레이드를 이용하실 수 없.");
        } else {
            if (target.getMapId() >= 271040100 && target.getMapId() <= 271040199) { //노말시그 전투แผนที่
                int v0 = self.askYesNo("전투를 마치고 시그너스의 후원으로 ออกต้องการหรือไม่?");
                if (v0 == 1) {
                    registerTransferField(271040200); //노말시그너스 ออกแผนที่
                }
            } else if (target.getMapId() >= 271041100 && target.getMapId() <= 271041109) { //이지시그
                int v0 = self.askYesNo("전투를 마치고 시그너스의 후원으로 ออกต้องการหรือไม่?");
                if (v0 == 1) {
                    registerTransferField(271041200); //이지시그너스 ออกแผนที่
                }
            } else if (target.getMapId() == 271040000 || target.getMapId() == 271041000) { //노말시그, 이지시그너스 เข้าแผนที่
                if (target.getParty() == null) {
                    self.say("1인 이상 ปาร์ตี้ 맺어야만 เข้า할 수 있.");
                } else {
                    if (DBConfig.isGanglim && getPlayer().getParty().getLeader().getId() != target.getId()) {
                        self.say("ปาร์ตี้장을 통해 ดำเนินการ해 สัปดาห์십시오.");
                    } else {
                        boolean normalCygnus = target.getMapId() == 271040000;
                        if (!normalCygnus && DBConfig.isGanglim) {
                            getPlayer().dropMessage(5, "ปัจจุบัน 타락한 시그너스(이지) เข้าร่วม할 수 없.");
                            return;
                        }
                        String v = "";
                        if (DBConfig.isGanglim) {
                        	v = "타락한 시그너스(이지) เข้าร่วม할 เตรียม는 되셨습니까?\r\n#b\r\n#L0# 시그너스(이지) เข้า을 สมัคร한다.#l\r\n#L1# 시그너스(이지) 연습 โหมด เข้า을 สมัคร한다.#l";
                            if (normalCygnus) {
                                v = "타락한 시그너스에게 맞설 เตรียม는 되셨습니까?\r\n#b\r\n#L0# 시그너스(노멀) เข้า을 สมัคร한다.#l\r\n#L1# 시그너스(노멀) 연습 โหมด เข้า을 สมัคร한다.#l";
                            }
                        }
                        else {
                        	boolean single = getPlayer().getPartyMemberSize() == 1;
                        	int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.DailyQuestResetCount.getQuestID(), "Cygnus" + (single ? "Single" : "Multi"));
                        	v = "타락한 시그너스(이지) เข้าร่วม할 เตรียม는 되셨습니까?\r\n#b\r\n"
                        			+ "#L0# 시그너스(이지)" + (single ? "(싱글)" : "(멀티)") + " เข้า을 สมัคร한다.#l\r\n"
                        			+ "#L1# 시그너스(이지)" + (single ? "(싱글)" : "(멀티)") + " 연습 โหมด เข้า을 สมัคร한다.#l\r\n";
                            if (normalCygnus) {
                                v = "타락한 시그너스에게 맞설 เตรียม는 되셨습니까?\r\n#b\r\n"
                                	+ "#L0# 시그너스(노멀)" + (single ? "(싱글)" : "(멀티)") + " เข้า을 สมัคร한다.#l\r\n"
                                	+ "#L1# 시그너스(노멀)" + (single ? "(싱글)" : "(멀티)") + " 연습 โหมด เข้า을 สมัคร한다.#l\r\n";
                            }
                        }
                        int v0 = self.askMenu(v);
                        if (target.getParty().isPartySameMap()) {
                            boolean canEnter = false;
                            String overLap = checkEventNumber(getPlayer(), QuestExConstants.Cygnus.getQuestID()); //시그너스는 격파시 클리어ประมวลผล됨!!
                            if (overLap == null && v0 != 1) {
                                String lastDate = checkEventLastDate(getPlayer(), QuestExConstants.Cygnus.getQuestID());
                                if (lastDate == null) {
                                    if (v0 == 0) {
                                        //271040000 ~ 271040199 (무슨 แผนที่ 199개나쓰냐;;)
                                        //271041100 ~ 271041109 (노말시그 แผนที่)
                                        int instanceMapID = 271041100; //이지시그 전투แผนที่
                                        if (target.getMapId() == 271040000) { //노말시그แผนที่
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
                                        if (!canEnter) { //เข้า이 불เป็นไปได้한 경우 แผนที่ 유저가 없는지 체크 후 인스턴스 วินาที기화
                                        	if (getClient().getChannelServer().getMapFactory().getMap(instanceMapID).getCharacters().size() == 0) {
                                        		String rt = em.getProperty("ResetTime");
                                        		long curTime = System.currentTimeMillis();
                                        		long time = rt == null ? 0 : Long.parseLong(rt);
                                        		if (time == 0) {
                                        			em.setProperty("ResetTime", String.valueOf(curTime));
                                        		}
												else if (time - curTime >= 10000) { // 10วินาที이상 แผนที่ 빈경우 เข้าเป็นไปได้하게 เปลี่ยน
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
                                            getClient().getChannelServer().getMapFactory().getMap(instanceMapID).resetFully(false);
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
                                            self.sayOk("ปัจจุบัน ทั้งหมดแผนที่ 가득차 이용하실 수 없. 다른 แชนแนล 이용โปรด.");
                                        }
                                    }
                                } else {
                                    self.say("ปาร์ตี้원 중 #b#e" + lastDate + " #n#k뒤 재 เข้า เป็นไปได้.");//본메 : 30นาที 이내에 เข้า한 ปาร์ตี้원이 있. 이지 및 노멀 โหมด를 통합 เข้า 후 30นาที 이내에 재เข้า이 불เป็นไปได้.
                                }
                            } else {
                                if (v0 == 1) {
                                    self.say("ปัจจุบัน 연습โหมด는 เตรียม 중 .");
                                } else {
                                    self.say("최근 วันสัปดาห์วัน 이내 시그너스를 클리어한 ปาร์ตี้원이 있. 시그너스(이지), 시그너스(노멀) 모두 합쳐 วันสัปดาห์วัน에 1회만 클리어 เป็นไปได้.\r\n#r#e<클리어 기록은 매สัปดาห์ 목요วัน에 วัน괄 วินาที기화.>#k#n");
                                }
                            }
                        } else {
                            self.say(target.getParty().getPartyMemberList().size() + "명 모두 같은แผนที่ 있어야 .");
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
        //인스턴스แผนที่ 271040300, 271041300(볼품없는정원)
        if (DBConfig.isGanglim) {
            getPlayer().dropMessage(5, "ปัจจุบัน 타락한 시그너스에 เข้าร่วม할 수 없.");
            return;
        }
        EventManager em = getEventManager("Cygnus");
        if (em == null) {
            self.say("ปัจจุบัน 시그너스 레이드를 이용하실 수 없.");
        } else {
            if (target.getMapId() >= 271040100 && target.getMapId() <= 271040199) { //노말시그 전투แผนที่
                int v0 = self.askYesNo("전투를 마치고 시그너스의 후원으로 ออกต้องการหรือไม่?");
                if (v0 == 1) {
                    registerTransferField(271040200); //노말시그너스 ออกแผนที่
                }
            } else if (target.getMapId() >= 271041100 && target.getMapId() <= 271041109) { //이지시그
                int v0 = self.askYesNo("전투를 마치고 시그너스의 후원으로 ออกต้องการหรือไม่?");
                if (v0 == 1) {
                    registerTransferField(271041200); //이지시그너스 ออกแผนที่
                }
            } else if (target.getMapId() == 271040000 || target.getMapId() == 271041000) { //노말시그, 이지시그너스 เข้าแผนที่
                if (target.getParty() == null) {
                    self.say("1인 이상 ปาร์ตี้ 맺어야만 เข้า할 수 있.");
                } else {
                    if (target.getParty().getLeader().getId() != target.getId()) {
                        self.say("ปาร์ตี้장을 통해 ดำเนินการ해 สัปดาห์십시오.");
                    } else {
                        boolean normalCygnus = target.getMapId() == 271040000;
                        if (!normalCygnus && DBConfig.isGanglim) {
                            getPlayer().dropMessage(5, "ปัจจุบัน 타락한 시그너스(이지) เข้าร่วม할 수 없.");
                            return;
                        }
                        String v = "타락한 시그너스(이지) เข้าร่วม할 เตรียม는 되셨습니까?\r\n#b\r\n#L0# 시그너스(이지) เข้า을 สมัคร한다.#l\r\n#L1# 시그너스(이지) 연습 โหมด เข้า을 สมัคร한다.#l";
                        if (normalCygnus) {
                            v = "타락한 시그너스에게 맞설 เตรียม는 되셨습니까?\r\n#b\r\n#L0# 시그너스(노멀) เข้า을 สมัคร한다.#l\r\n#L1# 시그너스(노멀) 연습 โหมด เข้า을 สมัคร한다.#l";
                        }
                        int v0 = self.askMenu(v);
                        if (target.getParty().isPartySameMap()) {
                            boolean canEnter = false;
                            String overLap = checkEventNumber(getPlayer(), QuestExConstants.Cygnus.getQuestID()); //시그너스는 격파시 클리어ประมวลผล됨!!
                            if (overLap == null && v0 != 1) {
                                String lastDate = checkEventLastDate(getPlayer(), QuestExConstants.Cygnus.getQuestID());
                                if (lastDate == null) {
                                    if (v0 == 0) {
                                        //271040000 ~ 271040199 (무슨 แผนที่ 199개나쓰냐;;)
                                        //271041100 ~ 271041109 (노말시그 แผนที่)
                                        int instanceMapID = 271041100; //이지시그 전투แผนที่
                                        if (target.getMapId() == 271040000) { //노말시그แผนที่
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
                                        if (!canEnter) { //เข้า이 불เป็นไปได้한 경우 แผนที่ 유저가 없는지 체크 후 인스턴스 วินาที기화
                                        	if (getClient().getChannelServer().getMapFactory().getMap(instanceMapID).getCharacters().size() == 0) {
                                        		String rt = em.getProperty("ResetTime");
                                        		long curTime = System.currentTimeMillis();
                                        		long time = rt == null ? 0 : Long.parseLong(rt);
                                        		if (time == 0) {
                                        			em.setProperty("ResetTime", String.valueOf(curTime));
                                        		}
												else if (time - curTime >= 10000) { // 10วินาที이상 แผนที่ 빈경우 เข้าเป็นไปได้하게 เปลี่ยน
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
                                            getClient().getChannelServer().getMapFactory().getMap(instanceMapID).resetFully(false);
                                            updateLastDate(getPlayer(), QuestExConstants.Cygnus.getQuestID());
                                            eim.registerParty(target.getParty(), getPlayer().getMap());
                                        } else {
                                            self.sayOk("ปัจจุบัน ทั้งหมดแผนที่ 가득차 이용하실 수 없. 다른 แชนแนล 이용โปรด.");
                                        }
                                    }
                                } else {
                                    self.say("ปาร์ตี้원 중 #b#e" + lastDate + " #n#k뒤 재 เข้า เป็นไปได้.");//본메 : 30นาที 이내에 เข้า한 ปาร์ตี้원이 있. 이지 및 노멀 โหมด를 통합 เข้า 후 30นาที 이내에 재เข้า이 불เป็นไปได้.
                                }
                            } else {
                                if (v0 == 1) {
                                    self.say("ปัจจุบัน 연습โหมด는 เตรียม 중 .");
                                } else {
                                    self.say("최근 วันสัปดาห์วัน 이내 시그너스를 클리어한 ปาร์ตี้원이 있. 시그너스(이지), 시그너스(노멀) 모두 합쳐 วันสัปดาห์วัน에 1회만 클리어 เป็นไปได้.\r\n#r#e<클리어 기록은 매สัปดาห์ 목요วัน에 วัน괄 วินาที기화.>#k#n");
                                }
                            }
                        } else {
                            self.say(target.getParty().getPartyMemberList().size() + "명 모두 같은แผนที่ 있어야 .");
                        }
                    }
                }
            }
        }
    }

    public void in_cygnusGarden() {
        initNPC(MapleLifeFactory.getNPC(2143004));
        int v0 = target.askMenu("#r#e시그너스의 정원에 เข้า할까?#b\r\n#L0#시그너스(노멀) 물리치기 위해 ย้าย한다.#l", ScriptMessageFlag.Self);
        if (v0 == 0) { //TODO 시그너스 정원의 열쇠 체크
            getPlayer().dropMessage(5, "시그너스의 정원으로 ย้าย.");
            registerTransferField(271040000);
        }
    }

    public void cygnus_Summon_Easy() {
        //8850011 - 노말시그 (8850012) 소환용 더미
        //8850111 - 이지시그 (8850112) 소환용 더미
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            if (eim.getProperty("summonMOB") == null) {
                eim.setProperty("summonMOB", "1");
                Field field = getPlayer().getMap();
                if (DBConfig.isGanglim || (!DBConfig.isGanglim && !getPlayer().isMultiMode())) {
                	field.spawnMonster(MapleLifeFactory.getMonster(8850112), new Point(-160, -65), 1);
                }
                else {
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
                eim.getMapInstance(getPlayer().getMapId()).startMapEffect("이곳을 찾아 온 사람을 보는 것은 정말 오랜만이에요. 하지만 무사히 돌아간 นาที도 없었답니다.", 5120043);
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
                }
                else {
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
                eim.getMapInstance(getPlayer().getMapId()).startMapEffect("이곳을 찾아 온 사람을 보는 것은 정말 오랜만이에요. 하지만 무사히 돌아간 นาที도 없었답니다.", 5120043);
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
        	}
        	else {
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
        	}
        	else {
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
        //이벤트 인스턴스 ยืนยัน해서 สถานการณ์에 맞게끔 แผนที่ย้าย시킬 것
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            if (getPlayer().getMap().getAllMonster().size() == 0) {
                registerTransferField(Integer.parseInt(eim.getProperty("map")));
            } else {
                getPlayer().dropMessage(5, "시그너스의 정원으로 되돌아가기 전에 먼저 기사단을 모두 처치해야 .");
            }
        }
    }

    public void back_cygnus() {
        //이벤트 인스턴스 ยืนยัน해서 สถานการณ์에 맞게끔 แผนที่ย้าย시킬 것
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            if (getPlayer().getMap().getAllMonster().size() == 0) {
                registerTransferField(Integer.parseInt(eim.getProperty("map")));
            } else {
                getPlayer().dropMessage(5, "시그너스의 정원으로 되돌아가기 전에 먼저 기사단을 모두 처치해야 .");
            }
        }
    }

    public void out_cygnusBackGarden() {
        //이벤트 인스턴스 ยืนยัน해서 สถานการณ์에 맞게끔 แผนที่ย้าย시킬 것
        registerTransferField(100000000);
    }

    public void out_cygnusBackGardenEasy() {
        //이벤트 인스턴스 ยืนยัน해서 สถานการณ์에 맞게끔 แผนที่ย้าย시킬 것(이지โหมด)
        registerTransferField(100000000);
    }
}
