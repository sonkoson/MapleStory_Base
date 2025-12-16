package script.Boss;

import constants.GameConstants;
import constants.QuestExConstants;
import database.DBConfig;
import network.models.CField;
import objects.context.party.Party;
import objects.context.party.PartyMemberEntry;
import objects.fields.Field;
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
import java.util.List;

public class VonLeon extends ScriptEngineNPC {

    public void portalNPC() {
        initNPC(MapleLifeFactory.getNPC(2161005)); // 포탈스크립트 엔피시
        EventManager em = getEventManager("VonLeon");
        if (em == null) {
            self.say("ยังไม่สามารถเข้าร่วม Von Leon Raid ได้ในขณะนี้");
        } else {
            if (target.getMapId() == 211070000) { // เข้าแผนที่(알현실 หน้า 복도)
                if (target.getParty() == null) {
                    self.say("ต้องมีปาร์ตี้อย่างน้อย 1 คนจึงจะเข้าได้");
                } else {
                    if (target.getParty().getLeader().getId() != target.getId() && DBConfig.isGanglim) {
                        self.say("กรุณาให้หัวหน้าปาร์ตี้เป็นผู้ดำเนินการ");
                    } else {
                        boolean single = getPlayer().getPartyMemberSize() == 1;
                        int v0 = self.askMenu(
                                "#e<Boss: Von Leon>#n\r\nผู้กล้าผู้ยิ่งใหญ่ ท่านพร้อมที่จะเผชิญหน้ากับราชสีห์ผู้ล่วงลับแล้วหรือยัง?\r\n#b\r\n#L0# ขอเข้าร่วมกองกำลัง Von Leon#l");
                        if (v0 == 0) {
                            String menu = "";
                            if (DBConfig.isGanglim) {
                                menu = "#e<Boss: Von Leon>#n\r\nกรุณาเลือกโหมดที่ต้องการ\r\n\r\n"
                                        + "#L0# โหมด Easy ( เลเวล 125 ขึ้นไป )#l\r\n"
                                        + "#L1# โหมด Normal ( เลเวล 125 ขึ้นไป )#l\r\n"
                                        + "#L2# โหมด Hard ( เลเวล 125 ขึ้นไป )#l";
                            } else {
                                menu = "#e<Boss: Von Leon>#n\r\nกรุณาเลือกโหมดที่ต้องการ\r\n\r\n"
                                        + "#L0# โหมด Easy " + (single ? "(Single)" : "(Multi)")
                                        + " ( เลเวล 125 ขึ้นไป )#l\r\n"
                                        + "#L1# โหมด Normal " + (single ? "(Single)" : "(Multi)")
                                        + " ( เลเวล 125 ขึ้นไป )#l\r\n"
                                        + "#L2# โหมด Hard " + (single ? "(Single)" : "(Multi)")
                                        + " ( เลเวล 125 ขึ้นไป )#l\r\n";
                                int reset = getPlayer().getOneInfoQuestInteger(
                                        QuestExConstants.DailyQuestResetCount.getQuestID(),
                                        "VonLeon" + (single ? "Single" : "Multi"));
                                menu += "#L3# จำนวนครั้งเข้าเล่น" + (single ? "(Single)" : "(Multi)") + " เพิ่ม "
                                        + ((single ? 2 : 1) - reset) + "ครั้งที่เพิ่มได้";
                            }
                            int v1 = self.askMenu(menu);
                            if (v1 == 3 && !DBConfig.isGanglim) {
                                if (getPlayer().getTogetherPoint() < 150) {
                                    self.sayOk("คะแนนความร่วมมือไม่เพียงพอ คะแนนปัจจุบัน : "
                                            + getPlayer().getTogetherPoint());
                                    return;
                                }
                                int reset = getPlayer().getOneInfoQuestInteger(
                                        QuestExConstants.DailyQuestResetCount.getQuestID(),
                                        "VonLeon" + (single ? "Single" : "Multi"));
                                if ((reset > 0 && !single) || (reset > 1 && single)) {
                                    self.sayOk("คุณใช้จำนวนครั้งที่สามารถเพิ่มได้ในวันนี้หมดแล้ว");
                                    return;
                                }
                                getPlayer().gainTogetherPoint(-150);
                                getPlayer().updateOneInfo(QuestExConstants.DailyQuestResetCount.getQuestID(),
                                        "VonLeon" + (single ? "Single" : "Multi"), String.valueOf(reset + 1));
                                self.sayOk("จำนวนครั้งที่เข้าเล่นได้เพิ่มขึ้นแล้ว");
                                return;
                            }
                            if (!DBConfig.isGanglim) {
                                if (target.getParty().getLeader().getId() != target.getId()) {
                                    self.say("ปาร์ตี้장 통해 ดำเนินการ해 สัปดาห์십시오.");
                                    return;
                                }
                            }
                            if (target.getParty().isPartySameMap()) {
                                boolean canEnter = false;
                                String overLap = null;
                                if (!DBConfig.isGanglim) {
                                    overLap = checkEventNumber(getPlayer(), QuestExConstants.VonLeon.getQuestID());
                                }
                                if (overLap == null) {
                                    if (em.getProperty("status0").equals("0")) {
                                        canEnter = true;
                                    }

                                    int v2 = -1;
                                    if (v1 == 2) {
                                        if (getPlayer().getQuestStatus(2000019) == 1) {
                                            if (GameConstants.isZero(getPlayer().getJob())) {
                                                v2 = self.askMenu(
                                                        "#e<อาวุธ Genesis>#n\r\nสามารถทำภารกิจเพื่อปลดปล่อยความลับของ #bอาวุธ Genesis#k ที่มีพลังของจอมเวทดำได้ จะทำอย่างไร?\r\n\r\n#e#r<เงื่อนไขภารกิจ>#n#k\r\n#b -กำจัดคนเดียว\r\n -ลด Final Damage 90%\r\n -ใช้ค่าพลังจากอุปกรณ์ที่สวมใส่เท่านั้น\r\n#k#L0#ทำภารกิจ#l\r\n#L1#ไม่ทำภารกิจ#l",
                                                        ScriptMessageFlag.Self);
                                            } else {
                                                v2 = self.askMenu(
                                                        "#e<อาวุธ Genesis>#n\r\nสามารถทำภารกิจเพื่อปลดปล่อยความลับของ #bอาวุธ Genesis#k ที่มีพลังของจอมเวทดำได้ จะทำอย่างไร?\r\n\r\n#e#r<เงื่อนไขภารกิจ>#n#k\r\n#b -กำจัดคนเดียว\r\n -สวมใส่เฉพาะอาวุธ Genesis ที่ถูกผนึกและอาวุธรองเท่านั้น\r\n -ลด Final Damage 90%\r\n -ใช้ค่าพลังจากอุปกรณ์ที่สวมใส่เท่านั้น\r\n#k#L0#ทำภารกิจ#l\r\n#L1#ไม่ทำภารกิจ#l",
                                                        ScriptMessageFlag.Self);
                                            }
                                            if (v2 == 0) {
                                                if (!checkBMQuestEquip()) {
                                                    return;
                                                }
                                                if (getPlayer().getParty().getPartyMemberList().size() > 1) {
                                                    self.say("ภารกิจนี้ต้องดำเนินการเพียงคนเดียว",
                                                            ScriptMessageFlag.Self);
                                                    return;
                                                }
                                            }
                                        }
                                    }

                                    if (!canEnter) { // เข้า 불능한 경우 แผนที่ 유ฉัน 없지 체크 후 인스턴스 วินาที기화
                                        if (getClient().getChannelServer().getMapFactory().getMap(211070100)
                                                .getCharactersSize() == 0) {
                                            String rt = em.getProperty("ResetTime");
                                            long curTime = System.currentTimeMillis();
                                            long time = rt == null ? 0 : Long.parseLong(rt);
                                            if (time == 0) {
                                                em.setProperty("ResetTime", String.valueOf(curTime));
                                            } else if (time - curTime >= 10000) { // 10วินาที상 แผนที่ 빈경우 เข้า능하게 เปลี่ยน
                                                canEnter = true;
                                                em.setProperty("ResetTime", "0");
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
                                                    int count = p.getOneInfoQuestInteger(key, "vonleon_clear");
                                                    if (count >= (1 + p.getBossTier())) {
                                                        self.say("สมาชิกในปาร์ตี้ #b#e" + p.getName()
                                                                + "#n#k ไม่สามารถท้าทายได้อีกในวันนี้");
                                                        return;
                                                    }
                                                    p.updateOneInfo(key, "vonleon_clear", String.valueOf(count + 1));
                                                }
                                            }
                                        }

                                        em.setProperty("status0", "1");
                                        EventInstanceManager eim = em.readyInstance();
                                        eim.setProperty("map", 211070100);
                                        if (v1 == 0) {
                                            eim.setProperty("mode", "easy");
                                        } else if (v1 == 1) {
                                            eim.setProperty("mode", "normal");
                                        } else if (v1 == 2) {
                                            eim.setProperty("mode", "hard");
                                        }
                                        getClient().getChannelServer().getMapFactory().getMap(211070100)
                                                .resetFully(false);
                                        if (v2 == 0) {
                                            getPlayer().applyBMCurse1(1);
                                        }
                                        if (!DBConfig.isGanglim && !single) {
                                            for (MapleCharacter partyMember : getPlayer().getPartyMembers()) {
                                                if (partyMember.getMapId() == getPlayer().getMapId()) {
                                                    partyMember.setMultiMode(true);
                                                    partyMember.applyBMCurseJinMulti();
                                                }
                                            }
                                        }
                                        updateEventNumber(getPlayer(), QuestExConstants.VonLeon.getQuestID());
                                        eim.registerParty(target.getParty(), getPlayer().getMap());
                                    } else {
                                        self.sayOk("แผนที่เต็ม ไม่สามารถใช้บริการได้ กรุณาลองใหม่ในแชนแนลอื่น");
                                    }
                                } else {
                                    self.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                            + "#n#k ได้เข้าเล่นแล้วในวันนี้ ไม่สามารถท้าทายได้อีก");
                                }
                            } else {
                                self.say("สมาชิกทั้ง " + target.getParty().getPartyMemberList().size()
                                        + " คนต้องอยู่ในแผนที่เดียวกัน");
                            }
                        }
                    }
                }
            } else {
                if (self.askYesNo("ต้องการจบการท้าทายและออกจากห้องโถงหรือไม่?") == 1) {
                    // 수락시 ออก (네 번째 탑루 짐)
                    getPlayer().setRegisterTransferFieldTime(0);
                    getPlayer().setRegisterTransferField(0);
                    registerTransferField(211060801);

                    if (getPlayer().getEventInstance() != null) {
                        getPlayer().getEventInstance().unregisterPlayer(getPlayer());
                        getPlayer().setEventInstance(null);
                    }
                }
                // 거절시 ใคร것도ไม่มี
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
            String v0 = "#rอาวุธ#k และ #bอาวุธรอง#k ต้องสวมใส่เท่านั้นจึงจะท้าทายได้\r\n\r\n#r<ไอเทมที่ต้องถอดออก>#k\r\n";
            for (int i = 0; i < blockedList.size(); ++i) {
                int bid = blockedList.get(i);
                v0 += "#i" + bid + "# #z" + bid + "#\r\n";
            }
            self.say(v0, ScriptMessageFlag.Self);
            return false;
        }
        return true;
    }

    public void VanLeon_Before() {
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            if (eim.getProperty("summonNPC") == null) {
                eim.setProperty("summonNPC", "1");
                Field field = getPlayer().getMap();
                field.spawnNpc(2161000, new Point(-6, -188));
                field.broadcastMessage(
                        CField.NPCPacket.npcSpecialAction(field.getNPCById(2161000).getObjectId(), "summon", 0, 0));
            }
        }
    }

    public void VanLeon_Summon() {
        /*
         * 8840013 - 지소환
         * 8840010 - 노말소환
         * 8840018 - 하드소환
         */
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            if (target.getParty().getLeader().getId() == target.getId()) { // ปาร์ตี้장만 소환능(중복소환 ห้อง지)
                if (self.askAccept(
                        "ผู้กล้าที่มาปราบข้า... หรือจะเป็นศัตรูของจอมเวทดำ... จะฝ่ายไหนก็ไม่สำคัญ หากเป้าหมายชัดเจนก็ไม่ต้องพูดพร่ำทำเพลง...  \r\nเข้ามาเลย เจ้าพวกโง่เขลา...#k",
                        ScriptMessageFlag.NoEsc) == 1) {
                    // 수락 반레온 소환된다
                    if (eim.getProperty("summonMOB") == null) {
                        eim.setProperty("summonMOB", "1");
                        Field field = getPlayer().getMap();
                        field.removeNpc(2161000);
                        if (eim.getProperty("mode").equals("hard")) {
                            if (DBConfig.isGanglim) {
                                field.spawnMonster(MapleLifeFactory.getMonster(8840018), new Point(-6, -188), 32);
                            } else {
                                if (!getPlayer().isMultiMode()) { // 싱글โหมด
                                    field.spawnMonster(MapleLifeFactory.getMonster(8840018), new Point(-6, -188), 32);
                                    if (getPlayer().getQuestStatus(2000019) == 1) {
                                        getPlayer().applyBMCurse1(2);
                                    }
                                } else {
                                    final MapleMonster vonleon = MapleLifeFactory.getMonster(8840018);
                                    vonleon.setPosition(new Point(-6, -188));

                                    final long hp = vonleon.getMobMaxHp();
                                    long fixedhp = hp * 3L;
                                    if (fixedhp < 0) {
                                        fixedhp = Long.MAX_VALUE;
                                    }
                                    vonleon.setHp(fixedhp);
                                    vonleon.setMaxHp(fixedhp);

                                    field.spawnMonster(vonleon, 32);
                                }
                            }
                        } else if (eim.getProperty("mode").equals("normal")) {
                            if (DBConfig.isGanglim) {
                                field.spawnMonster(MapleLifeFactory.getMonster(8840010), new Point(-6, -188), 32);
                            } else {
                                if (getPlayer().getPartyMemberSize() == 1) {
                                    field.spawnMonster(MapleLifeFactory.getMonster(8840010), new Point(-6, -188), 32);
                                } else {
                                    final MapleMonster vonleon = MapleLifeFactory.getMonster(8840010);
                                    vonleon.setPosition(new Point(-6, -188));
                                    final long hp = vonleon.getMobMaxHp();
                                    long fixedhp = hp * 3L;
                                    if (fixedhp < 0) {
                                        fixedhp = Long.MAX_VALUE;
                                    }
                                    vonleon.setHp(fixedhp);
                                    vonleon.setMaxHp(fixedhp);

                                    field.spawnMonster(vonleon, 32);
                                }
                            }
                        } else if (eim.getProperty("mode").equals("easy")) {
                            if (DBConfig.isGanglim) {
                                field.spawnMonster(MapleLifeFactory.getMonster(8840013), new Point(-6, -188), 32);
                            } else {
                                if (getPlayer().getPartyMemberSize() == 1) {
                                    field.spawnMonster(MapleLifeFactory.getMonster(8840013), new Point(-6, -188), 32);
                                } else {
                                    final MapleMonster vonleon = MapleLifeFactory.getMonster(8840013);
                                    vonleon.setPosition(new Point(-6, -188));
                                    final long hp = vonleon.getMobMaxHp();
                                    long fixedhp = hp * 3L;
                                    if (fixedhp < 0) {
                                        fixedhp = Long.MAX_VALUE;
                                    }
                                    vonleon.setHp(fixedhp);
                                    vonleon.setMaxHp(fixedhp);

                                    field.spawnMonster(vonleon, 32);
                                }
                            }
                        }
                    }
                }
            } else {
                self.say(
                        "ผู้กล้าที่มาปราบข้า... หรือจะเป็นศัตรูของจอมเวทดำ... จะฝ่ายไหนก็ไม่สำคัญ หากเป้าหมายชัดเจนก็ไม่ต้องพูดพร่ำทำเพลง...");
            }
        }
        // 거절시 ใคร것도ไม่มี
    }

    public void outVanLeonPrison() {
        // 감옥열쇠 있어야 탈출능
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            if (getPlayer().haveItem(4032860, 1, false, true)) {
                registerTransferField(Integer.parseInt(eim.getProperty("map")));
                getPlayer().removeItem(4032860, -1);
            } else {
                getPlayer().dropMessage(5, "ไม่สามารถออกจากคุกได้หากไม่มีกุญแจ กรุณาค้นหากุญแจจากกล่อง");
            }
        }
    }
}
