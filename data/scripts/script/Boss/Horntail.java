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

    public void hontale_enterToE() { //hontale 실화냐 ;;
        if (target.getParty() == null) {
            self.say("ปาร์ตี้장을 통해 ดำเนินการ해 สัปดาห์십시오.");
        } else {
            if (target.getParty().getLeader().getId() != target.getId()) {
                self.say("ปาร์ตี้장을 통해 ดำเนินการ해 สัปดาห์십시오.");
            } else {
                if (self.askYesNo("석판에 쓰여진 글씨가 빛ฉัน더니 석판 หลัง로 เล็ก ประตู이 열렸다. 비밀통로를 이용ต้องการหรือไม่?") == 1) {
                    if (target.getParty().isPartySameMap()) {
                        target.getParty().registerTransferField(240050400); //ปาร์ตี้원 ทั้งหมดย้าย!
                    } else {
                        self.say(target.getParty().getPartyMemberList().size() + "명 ทั้งหมด เหมือนกันแผนที่ 있어야 .");
                    }
                } else {
                    self.say("ย้าย하시려면 다시 말을 걸어 สัปดาห์세요.");
                }
            }
        }
    }

    public void hontale_accept() {
        //이지, 노말혼테วัน 첫เริ่มแผนที่(240060000, 240060010, 240060020, 240060030, 240060040, 240060050, 240060060, 240060070)
        //카오스혼테วัน 첫เริ่มแผนที่(240060001, 240060011, 240060021, 240060031, 240060041, 240060051, 240060061, 240060071)
        int[] normalHortailMaps = new int[]{240060000, 240060010, 240060020, 240060030, 240060040, 240060050, 240060060, 240060070};
        int[] chaosHortailMaps = new int[]{240060001, 240060011, 240060021, 240060031, 240060041, 240060051, 240060061, 240060071};
        EventManager em = getEventManager("Horntail");
        if (em == null) {
            self.say("ปัจจุบัน 혼테วัน บอส레이드를 이용할 수 없.");
        } else {
            int v = self.askMenu("#e<บอส: 혼테วัน>#n\r\n혼테วัน이 부활했다. 이대로 둔다면 화산폭발을 วัน으켜서 미ฉัน르 วัน대를 지옥으로 만들어 버릴거야.\r\n#b\r\n#L0# <บอส: 혼테วัน> เข้า을 สมัคร한다.#l");
            if (v == 0) {
            	String menu = "";

            	if (DBConfig.isGanglim) {
            		menu = "#e<บอส: 혼테วัน>#n\r\n원하는 โหมด를 เลือก하라.\r\n\r\n#L0# 이지 โหมด ( เลเวล 130 이상 )#l\r\n#L1# 노멀 โหมด ( เลเวล 130 이상 )#l\r\n#L2# 카오스 โหมด ( เลเวล 135 이상 )#l";
            	} else {
            		boolean single = getPlayer().getPartyMemberSize() == 1;
            		menu = "#e<บอส: 혼테วัน>#n\r\n원하는 โหมด를 เลือก하라.\r\n\r\n"
            				+ "#L0# 이지 โหมด " + (single ? "(싱글)" : "(멀티)") + " ( เลเวล 130 이상 )#l\r\n"
            				+ "#L1# 노멀 โหมด " + (single ? "(싱글)" : "(멀티)") + "( เลเวล 130 이상 )#l\r\n"
            				+ "#L2# 카오스 โหมด "  + (single ? "(싱글)" : "(멀티)") + " ( เลเวล 135 이상 )#l\r\n";
            		int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.DailyQuestResetCount.getQuestID(), "Horntail" + (single ? "Single" : "Multi"));
            		menu += "#L3#เข้า횟수 เพิ่ม (" + ((single ? 2 : 1) - reset) + "회 เป็นไปได้)#l";
            	}
                int v2 = self.askMenu(menu);

                if (target.getParty() == null) {
                    self.say("ปาร์ตี้장을 통해 ดำเนินการ해 สัปดาห์십시오.");
                    return;
                } else {
                	if (v2 == 3 && !DBConfig.isGanglim) {
                		boolean single = getPlayer().getPartyMemberSize() == 1;
                		if (getPlayer().getTogetherPoint() < 150) {
                			self.sayOk("협동คะแนน ไม่พอ. มี คะแนน : " + getPlayer().getTogetherPoint());
                			return;
                		}
                		int reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.DailyQuestResetCount.getQuestID(), "Horntail" + (single ? "Single" : "Multi"));
                		if (reset > (single ? 1 : 0)) {
                			self.sayOk("วันนี้은 เข้าเป็นไปได้ 횟수 เพิ่ม가 불เป็นไปได้.");
                			return;
                		}
                		getPlayer().gainTogetherPoint(-150);
                		getPlayer().updateOneInfo(QuestExConstants.DailyQuestResetCount.getQuestID(), "Horntail" + (single ? "Single" : "Multi"), String.valueOf(reset + 1));
                		self.sayOk("เข้า 횟수가 เพิ่ม되었.");
                		return;
                	}

                    if (target.getParty().getLeader().getId() != target.getId()) {
                        self.say("ปาร์ตี้장을 통해 ดำเนินการ해 สัปดาห์십시오.");
                    } else {
                        if (target.getParty().isPartySameMap()) {
                            // 이미 누가 ใน에 있는지 จาก 알아보자
                            int[] fields = new int[]{};
                            int map = normalHortailMaps[0];
                            if (v2 == 2) {
                                map = chaosHortailMaps[0];
                            }
                            if (v2 == 0 || v2 == 1) {
                                fields = new int[]{map, map + 100, map + 200, map + 300};
                            } else if (v2 == 2) {
                                fields = new int[]{map, map + 100, map + 200};
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
                                    self.say("ปัจจุบัน 이지,노말 แผนที่ 가득ชา 이용하실 수 없. อื่น แชนแนล 이용โปรด.");
                                } else if (v2 == 2) {
                                    self.say("ปัจจุบัน 카오스 แผนที่ 가득ชา 이용하실 수 없. อื่น แชนแนล 이용โปรด.");
                                }
                                return;
                            }

                            int canEnter = -1;
                            String overLap = checkEventNumber(getPlayer(), QuestExConstants.Horntail.getQuestID(), DBConfig.isGanglim);
                            if (overLap != null) {
                                v2 = 3;
                                canEnter = -2;
                            }
                            String mode = "easy";
                            if (v2 == 0 || v2 == 1) { //이지, 노말โหมด
                                if (em.getProperty("status0").equals("0")) {
                                    em.setProperty("status0", "1");
                                    canEnter = 0;
                                    if (v2 == 1) {
                                        mode = "normal";
                                    }
                                }
                            } else if (v2 == 2) { //카오스โหมด
                                em.setProperty("Cstatus0", "0");
                                if (em.getProperty("Cstatus0").equals("0")) {
                                    em.setProperty("Cstatus0", "1");
                                    canEnter = 0;
                                    mode = "chaos";
                                }
                            }

                            if (canEnter == -1 || canEnter == -2) {
                                if (canEnter == -2) {
                                    self.say("ปาร์ตี้원 중#b#e" + overLap + "#n#k วันนี้ เข้า했군. เขา렇다면 วันนี้은 더 이상 들어갈 수 ไม่มี.");
                                } else {
                                    self.say("ปัจจุบัน ทั้งหมดแผนที่ 가득ชา 이용하실 수 없. อื่น แชนแนล 이용โปรด.");
                                }
                            } else if (canEnter == 0){
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
                        } else { //ปาร์ตี้원 ทั้งหมด เหมือนกันแผนที่ 없을 때
                            self.say(target.getParty().getPartyMemberList().size() + "명 ทั้งหมด เหมือนกันแผนที่ 있어야 .");
                        }
                    }
                }
            }
        }
    }

    public void hontale_out() {
        if (target.getMapId() == 240050400) {
            if (self.askYesNo("#m240050000# 돌아가시หรือไม่?") == 1) {
                registerTransferField(240050000);
            } else {
                self.say("다시 생แต่ละ해 보시고 말을 걸어 สัปดาห์세요.");
            }
        } else {
            if (self.askYesNo("전투를 เขา만두고 นอก으로 ฉัน가시หรือไม่? ออก 시 วันนี้은 더 이상 เข้า할 수 없.") == 1) {
                //횟수 หัก!
                registerTransferField(240050400);
            } else {
                self.say("다시 생แต่ละ해 보시고 말을 걸어 สัปดาห์세요.");
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
                        } else if (eim.getProperty("mode").equals("chaos")) { //카오스โหมด
                            mobId = 8810100;
                        }
                        tremble.getMap().spawnMonster(MapleLifeFactory.getMonster(mobId), new Point(tremble.getPosition().x - 90, tremble.getPosition().y + 5), -2); //이거 본섭화 맞음ㅋㅋ
                        mapMessage(6, "동굴 깊은 곳에서 무시무시한 생명체가 ฉัน타납니다.");
                        tremble.forceHitReactor((byte)0);
                        tremble.forceHitReactor((byte)1);
                	}
                	else {
                		if (getPlayer().getPartyMemberSize() == 1) {
                			int mobId = 8810200;
                            if (eim.getProperty("mode").equals("normal")) {
                                mobId = 8810000;
                            } else if (eim.getProperty("mode").equals("chaos")) { //카오스โหมด
                                mobId = 8810100;
                            }
                            tremble.getMap().spawnMonster(MapleLifeFactory.getMonster(mobId), new Point(tremble.getPosition().x - 90, tremble.getPosition().y + 5), -2); //이거 본섭화 맞음ㅋㅋ
                            mapMessage(6, "동굴 깊은 곳에서 무시무시한 생명체가 ฉัน타납니다.");
                            tremble.forceHitReactor((byte)0);
                            tremble.forceHitReactor((byte)1);
                		}
                		else {
                			int mobId = 8810200;
                            if (eim.getProperty("mode").equals("normal")) {
                                mobId = 8810000;
                            } else if (eim.getProperty("mode").equals("chaos")) { //카오스โหมด
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
                            mapMessage(6, "동굴 깊은 곳에서 무시무시한 생명체가 ฉัน타납니다.");
                            tremble.forceHitReactor((byte)0);
                            tremble.forceHitReactor((byte)1);
                		}
                	}
                }
            }
        }
    }

    public void hontale_boss2() {
        EventInstanceManager eim = getPlayer().getEventInstance();
        if (eim != null) {
            if (eim.getProperty("boss2") == null) {
                eim.setProperty("boss2", "1");
                Reactor tremble = getPlayer().getMap().getReactorByName("tremble2");
                if (tremble != null) {
                	if (DBConfig.isGanglim) {
                		int mobId = 8810201;
                        if (eim.getProperty("mode").equals("normal")) {
                            mobId = 8810001;
                        } else if (eim.getProperty("mode").equals("chaos")) { //카오스โหมด
                            mobId = 8810101;
                        }
                        mapMessage(6, "동굴 깊은 곳에서 무시무시한 생명체가 ฉัน타납니다.");
                        tremble.getMap().spawnMonster(MapleLifeFactory.getMonster(mobId), new Point(tremble.getPosition().x + 89, tremble.getPosition().y - 21), -2);
                        tremble.forceHitReactor((byte)0);
                        tremble.forceHitReactor((byte)1);
                	}
                	else {
                		if (getPlayer().getPartyMemberSize() == 1) {
                			int mobId = 8810201;
                            if (eim.getProperty("mode").equals("normal")) {
                                mobId = 8810001;
                            } else if (eim.getProperty("mode").equals("chaos")) { //카오스โหมด
                                mobId = 8810101;
                            }
                            mapMessage(6, "동굴 깊은 곳에서 무시무시한 생명체가 ฉัน타납니다.");
                            tremble.getMap().spawnMonster(MapleLifeFactory.getMonster(mobId), new Point(tremble.getPosition().x + 89, tremble.getPosition().y - 21), -2);
                            tremble.forceHitReactor((byte)0);
                            tremble.forceHitReactor((byte)1);
                		}
                		else {
                			int mobId = 8810201;
                            if (eim.getProperty("mode").equals("normal")) {
                                mobId = 8810001;
                            } else if (eim.getProperty("mode").equals("chaos")) { //카오스โหมด
                                mobId = 8810101;
                            }
                            final MapleMonster horntail = MapleLifeFactory.getMonster(mobId);
                            horntail.setPosition(new Point(tremble.getPosition().x + 89, tremble.getPosition().y - 21));
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
                            mapMessage(6, "동굴 깊은 곳에서 무시무시한 생명체가 ฉัน타납니다.");
                            tremble.forceHitReactor((byte)0);
                            tremble.forceHitReactor((byte)1);
                		}
                	}
                }
            }
        }
    }

    public void hontale_BR() {
        EventInstanceManager eim = getPlayer().getEventInstance();
        if (eim != null) {
            int enMap = 240060000;
            int chaosMap = 240060001;
            if (eim.getProperty("stage1") != null && (target.getMapId() == enMap || target.getMapId() == chaosMap)) { //1단계
                playPortalSE();
                getPlayer().changeMap(target.getMapId() + 100);
            } else if (eim.getProperty("stage2") != null && (target.getMapId() == enMap+100 || target.getMapId() == chaosMap + 100)) {
                playPortalSE();
                if (eim.getProperty("mode").equals("easy")) {
                    getPlayer().changeMap(target.getMapId() + 200);
                } else {
                    getPlayer().changeMap(target.getMapId() + 100);
                }
            } else {
                getPlayer().dropMessage(5, "아직  포탈을 ใช้할 수 없.");
            }
        }
    }
}
