package script.Boss;

import constants.GameConstants;
import constants.QuestExConstants;
import database.DBConfig;
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
import java.util.Arrays;
import java.util.List;

public class Magnus extends ScriptEngineNPC {

    public void magnus_easy() {
        initNPC(MapleLifeFactory.getNPC(3001000));
        if (DBConfig.isGanglim) {
            self.say("ไม่สามารถเข้าสู่ Magnus Simulation Battle ได้ในขณะนี้");
        }
        self.say(
                "สามารถลองต่อสู้กับ Magnus ผ่านพอร์ทัลนั้นได้ แน่นอนว่าพลังอาจเทียบไม่ได้กับ Magnus ตัวจริง แต่นี่เป็นขีดจำกัดของเทคโนโลยี Nova ในปัจจุบัน",
                ScriptMessageFlag.NpcReplacedByNpc);
        if (self.askYesNo(
                "ต้องการเข้าสู่ Magnus Simulation Battle (Easy Mode) หรือไม่?\r\n#b<< Magnus Simulation Battle สามารถเคลียร์ได้วันละ 1 ครั้ง >>\r\n<< สามารถเข้าได้เฉพาะปาร์ตี้ที่มีเลเวล 115 ขึ้นไป >>",
                ScriptMessageFlag.NpcReplacedByNpc) == 1) {
            self.say(
                    "เราได้จำลองปราสาทของทรราชเพื่อให้สภาพแวดล้อมใกล้เคียงที่สุด สามารถเข้าสู่ห้องบัลลังก์ได้จากที่นั่น",
                    ScriptMessageFlag.NpcReplacedByNpc);
            target.registerTransferField(401060399); // Use target when moving after say
        }
    }

    public void enter_magnusDoor() {
        initNPC(MapleLifeFactory.getNPC(3001020));
        EventManager em = getEventManager("Magnus");
        if (em == null) {
            self.say("ไม่สามารถเข้าสู่ Magnus Raid ได้ในขณะนี้");
        } else {
            if (target.getParty() == null) {
                self.say("ต้องอยู่ในปาร์ตี้อย่างน้อย 1 คนจึงจะสามารถเข้าได้");
            } else {
                if (target.getParty().getLeader().getId() != target.getId() && DBConfig.isGanglim) {
                    self.say("กรุณาให้หัวหน้าปาร์ตี้เป็นผู้ดำเนินการ");
                } else {
                    if (target.getMapId() == 401060399) { // Easy Magnus entry map
                        if (DBConfig.isGanglim) {
                            self.say("ไม่สามารถเข้าสู่ Magnus Simulation Battle ได้ในขณะนี้");
                        }
                        if (self.askYesNo(
                                "ต้องการเดินทางไปยัง Tyrant's Throne เพื่อกำจัด Magnus หรือไม่?\r\n#b<< Magnus Simulation Battle สามารถเคลียร์ได้วันละ 1 ครั้ง >>\r\n<< สามารถเข้าได้เฉพาะปาร์ตี้ที่มีเลเวล 115 ขึ้นไป >>") == 1) {
                            // 401060200 ~ 401060209
                            if (target.getParty().isPartySameMap()) {
                                boolean canEnter = false;
                                String overLap = checkEventNumber(getPlayer(), QuestExConstants.Magnus.getQuestID());
                                if (overLap == null) {
                                    if (em.getProperty("status0").equals("0")) {
                                        canEnter = true;
                                    }
                                    if (canEnter) {
                                        em.setProperty("status0", "1");
                                        EventInstanceManager eim = em.readyInstance();
                                        eim.setProperty("map", 401060300);
                                        eim.setProperty("mode", "easy");
                                        getClient().getChannelServer().getMapFactory().getMap(401060300)
                                                .resetFully(false);
                                        updateLastDate(getPlayer(), QuestExConstants.Magnus.getQuestID()); // Easy and
                                                                                                           // Normal
                                                                                                           // Magnus
                                                                                                           // share time
                                        eim.registerParty(target.getParty(), getPlayer().getMap());
                                    } else {
                                        self.sayOk("มีการต่อสู้กับ Magnus ในแชนแนลนี้อยู่แล้ว");
                                    }
                                } else {
                                    self.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                            + "#n#k ได้เข้าสู่ดันเจี้ยนไปแล้วในวันนี้ ไม่สามารถเข้าได้อีก");
                                }
                            } else {
                                self.say("สมาชิกปาร์ตี้ทุกคนต้องอยู่ในแผนที่เดียวกัน");
                            }
                        }
                    } else {
                        boolean single = false;
                        if (!DBConfig.isGanglim) {
                            single = getPlayer().getParty().getMembers().size() == 1;
                        }
                        String text = "ต้องการเดินทางไปยัง Tyrant's Throne เพื่อกำจัด Magnus หรือไม่?#b\r\n";
                        if (DBConfig.isGanglim) {
                            text += "#L0#เข้าสู่ Tyrant's Throne (Hard) (เลเวล 175 ขึ้นไป)";
                            text += " #r[" + getPlayer()
                                    .getOneInfoQuestInteger(QuestExConstants.HardMagnus.getQuestID(), "eNum") + "/"
                                    + (getPlayer().getBossTier() + 1) + "]#b";
                            getPlayer().getPartyMembers()
                                    .forEach(chr -> chr.dropMessage(5, "[Hard Magnus] วันนี้เข้าสู้บอสไปแล้ว "
                                            + chr.getOneInfoQuestInteger(QuestExConstants.HardMagnus.getQuestID(),
                                                    "eNum")
                                            + " ครั้ง สามารถเข้าได้ทั้งหมด " + (chr.getBossTier() + 1) + " ครั้ง"));
                            text += "\r\n#L1#เข้าสู่ Tyrant's Throne (Normal) (เลเวล 155 ขึ้นไป)";
                            text += " #r["
                                    + getPlayer().getOneInfoQuestInteger(QuestExConstants.Magnus.getQuestID(), "eNum")
                                    + "/" + (getPlayer().getBossTier() + 1) + "]#b";
                            getPlayer().getPartyMembers().forEach(chr -> chr.dropMessage(5,
                                    "[Normal Magnus] วันนี้เข้าสู้บอสไปแล้ว "
                                            + chr.getOneInfoQuestInteger(QuestExConstants.Magnus.getQuestID(), "eNum")
                                            + " ครั้ง สามารถเข้าได้ทั้งหมด " + (chr.getBossTier() + 1) + " ครั้ง"));
                            text += "\r\n#L3#เข้าสู่ Tyrant's Throne (Hard) Practice Mode (เลเวล 175 ขึ้นไป)#l\r\n#L2#ยกเลิก#l";
                        } else {
                            text += "#L0#เข้าสู่ Tyrant's Throne (Hard)" + (single ? "(Single)" : "(Multi)")
                                    + " (เลเวล 175 ขึ้นไป)";
                            text += "\r\n#L1#เข้าสู่ Tyrant's Throne (Normal)" + (single ? "(Single)" : "(Multi)")
                                    + " (เลเวล 155 ขึ้นไป)";
                            // text +="\r\n#L3#เข้าสู่ Tyrant's Throne (Hard) Practice Mode (เลเวล 175
                            // ขึ้นไป)#l\r\n#L2#ยกเลิก#l";
                            /*
                             * int hreset =
                             * getPlayer().getOneInfoQuestInteger(QuestExConstants.WeeklyQuestResetCount.
                             * getQuestID(), "HardMagnus" + (single ? "Single" : "Multi"));
                             * text += "\r\n#L5#เพิ่มรอบเข้าเล่น Tyrant's Throne (Hard)" + (single ?
                             * "(Single)" : "(Multi)") + " (" + ((single ? 2 : 1) - hreset) + " ครั้ง)#l";
                             */
                            int nreset = getPlayer().getOneInfoQuestInteger(
                                    QuestExConstants.DailyQuestResetCount.getQuestID(),
                                    "NormalMagnus" + (single ? "Single" : "Multi"));
                            text += "\r\n#L6#เพิ่มรอบเข้าเล่น Tyrant's Throne (Normal)"
                                    + (single ? "(Single)" : "(Multi)") + " (" + ((single ? 2 : 1) - nreset)
                                    + " ครั้ง)#l";
                        }

                        int v0 = self.askMenu(text);
                        if (!DBConfig.isGanglim) {
                            if (v0 == 6) { // (v0 == 5 || v0 == 6) {
                                int togetherPoint = getPlayer().getTogetherPoint();
                                if (togetherPoint < 150) {
                                    self.sayOk("คะแนนความร่วมมือไม่เพียงพอ คะแนนปัจจุบัน : " + togetherPoint);
                                    return;
                                }
                                if (v0 == 5) { // Hard Magnus reset
                                    int hreset = getPlayer().getOneInfoQuestInteger(
                                            QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                                            "HardMagnus" + (single ? "Single" : "Multi"));
                                    if (hreset > (single ? 1 : 0)) { // Single can buy 2 times
                                        self.sayOk("ไม่สามารถเพิ่มจำนวนครั้งที่เข้าได้อีกในสัปดาห์นี้");
                                        return;
                                    }
                                    getPlayer().gainTogetherPoint(-150);
                                    getPlayer().updateOneInfo(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
                                            "HardMagnus" + (single ? "Single" : "Multi"), String.valueOf(hreset + 1));
                                    self.sayOk("เพิ่มจำนวนครั้งที่เข้าเล่นเรียบร้อยแล้ว");
                                    return;
                                }
                                if (v0 == 6) { // Normal Magnus reset
                                    int nreset = getPlayer().getOneInfoQuestInteger(
                                            QuestExConstants.DailyQuestResetCount.getQuestID(),
                                            "NormalMagnus" + (single ? "Single" : "Multi"));
                                    if (nreset > (single ? 1 : 0)) { // Single can buy 2 times
                                        self.sayOk("ไม่สามารถเพิ่มจำนวนครั้งที่เข้าได้อีกในสัปดาห์นี้");
                                        return;
                                    }
                                    getPlayer().gainTogetherPoint(-150);
                                    getPlayer().updateOneInfo(QuestExConstants.DailyQuestResetCount.getQuestID(),
                                            "NormalMagnus" + (single ? "Single" : "Multi"), String.valueOf(nreset + 1));
                                    self.sayOk("เพิ่มจำนวนครั้งที่เข้าเล่นเรียบร้อยแล้ว");
                                    return;
                                }
                            }
                        }

                        if (!DBConfig.isGanglim && target.getParty().getLeader().getId() != target.getId()) {
                            self.say("กรุณาให้หัวหน้าปาร์ตี้เป็นผู้ดำเนินการ");
                            return;
                        }

                        if (target.getParty().isPartySameMap()) {
                            boolean canEnter = false;
                            if (v0 != 3) {
                                int v2 = -1;
                                if (v0 == 0) {
                                    if (getPlayer().getQuestStatus(2000021) == 1) {
                                        if (GameConstants.isZero(getPlayer().getJob())) {
                                            v2 = self.askMenu(
                                                    "#e<Genesis Weapon>#n\r\nสามารถทำภารกิจเพื่อปลดล็อคความลับของ #bGenesis Weapon#k ที่มีพลังของ Black Mage ได้ จะทำอย่างไร?\r\n\r\n#e#r<เงื่อนไขภารกิจ>#n#k\r\n#b -กำจัดบอสคนเดียว\r\n -Final Damage ลดลง 50%\r\n -ใช้เฉพาะค่าสถานะบริสุทธิ์ของอุปกรณ์ที่สวมใส่เท่านั้น\r\n#k#L0#รับภารกิจ#l\r\n#L1#ไม่รับภารกิจ#l",
                                                    ScriptMessageFlag.Self);
                                        } else {
                                            v2 = self.askMenu(
                                                    "#e<Genesis Weapon>#n\r\nสามารถทำภารกิจเพื่อปลดล็อคความลับของ #bGenesis Weapon#k ที่มีพลังของ Black Mage ได้ จะทำอย่างไร?\r\n\r\n#e#r<เงื่อนไขภารกิจ>#n#k\r\n#b -กำจัดบอสคนเดียว\r\n -สวมใส่ได้เฉพาะ Genesis Weapon ที่ถูกผนึกและอาวุธรองเท่านั้น\r\n -Final Damage ลดลง 50%\r\n -ใช้เฉพาะค่าสถานะบริสุทธิ์ของอุปกรณ์ที่สวมใส่เท่านั้น\r\n#k#L0#รับภารกิจ#l\r\n#L1#ไม่รับภารกิจ#l",
                                                    ScriptMessageFlag.Self);
                                        }
                                        if (v2 == 0) {
                                            if (!getPlayer().haveItem(4036460)) {
                                                self.say(
                                                        "จำเป็นต้องมี #b#i4036460# #z4036460# 1 ชิ้น#k สามารถหาได้จากการกำจัด Black Mage",
                                                        ScriptMessageFlag.Self);
                                                return;
                                            }
                                            if (!checkBMQuestEquip()) {
                                                return;
                                            }
                                            if (getPlayer().getParty().getPartyMemberList().size() > 1) {
                                                self.say("ภารกิจนี้ต้องทำคนเดียวเท่านั้น", ScriptMessageFlag.Self);
                                                return;
                                            }
                                        }
                                    }
                                }

                                String overLap = checkEventNumber(getPlayer(), QuestExConstants.Magnus.getQuestID(),
                                        DBConfig.isGanglim);
                                if (v0 == 0) { // Hard Magnus
                                    overLap = checkEventNumber(getPlayer(), QuestExConstants.HardMagnus.getQuestID(),
                                            DBConfig.isGanglim);
                                }
                                // getPlayer().dropMessage(5, (overLap == null ? "Null" : overLap));
                                if (overLap == null) {
                                    if (v0 == 0) { // Hard Magnus
                                        String lastDate = checkEventLastDate(getPlayer(),
                                                QuestExConstants.HardMagnus.getQuestID());
                                        if (lastDate == null || DBConfig.isGanglim) { // Ganglim removed 30min re-entry
                                            if (em.getProperty("Hstatus0").equals("0")) {
                                                canEnter = true;
                                            }
                                            if (canEnter) {
                                                em.setProperty("Hstatus0", "1");
                                                EventInstanceManager eim = em.readyInstance();
                                                eim.setProperty("map", 401060100);
                                                eim.setProperty("mode", "hard");
                                                getClient().getChannelServer().getMapFactory().getMap(401060100)
                                                        .resetFully(false);
                                                updateLastDate(getPlayer(), QuestExConstants.HardMagnus.getQuestID());
                                                if (DBConfig.isGanglim) {
                                                    updateQuestEx(getPlayer(),
                                                            QuestExConstants.HardMagnus.getQuestID());
                                                }
                                                if (v2 == 0) {
                                                    getPlayer().applyBMCurse1(3);
                                                }
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
                                                self.sayOk("แผนที่เต็ม ไม่สามารถใช้บริการได้ กรุณาลองใหม่ในแชนแนลอื่น");
                                            }
                                        } else {
                                            self.say("สมาชิกในปาร์ตี้จะสามารถเข้าได้อีกครั้งในอีก #b#e" + lastDate
                                                    + " #n#k"); // Official: Party member entered within 30 mins. Cannot
                                                                // re-enter within 30 mins after entry.
                                        }
                                    } else if (v0 == 1) { // Normal Magnus
                                        if (em.getProperty("Nstatus0").equals("0")) {
                                            canEnter = true;
                                        }
                                        if (canEnter) {
                                            em.setProperty("Nstatus0", "1");
                                            EventInstanceManager eim = em.readyInstance();
                                            eim.setProperty("map", 401060200);
                                            eim.setProperty("mode", "normal");
                                            getClient().getChannelServer().getMapFactory().getMap(401060200)
                                                    .resetFully(false);
                                            updateLastDate(getPlayer(), QuestExConstants.Magnus.getQuestID());
                                            if (DBConfig.isGanglim) {
                                                updateQuestEx(getPlayer(), QuestExConstants.Magnus.getQuestID());
                                            }
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
                                            self.sayOk("แผนที่เต็ม ไม่สามารถใช้บริการได้ กรุณาลองใหม่ในแชนแนลอื่น");
                                        }
                                    }
                                } else {
                                    String text_ = "สมาชิกในปาร์ตี้ #b#e" + overLap
                                            + "#n#k ได้เข้าสู่ดันเจี้ยนไปแล้วในวันนี้ ไม่สามารถเข้าได้อีก";
                                    if (!DBConfig.isGanglim) {
                                        text_ += "\r\n(Hard Magnus จะรีเซ็ตทุกวันพฤหัสบดี)";
                                    }
                                    self.say(text_);
                                }
                            } else {
                                self.say("โหมดฝึกซ้อมกำลังอยู่ระหว่างการเตรียมการ");
                            }
                        } else {
                            self.say("สมาชิกปาร์ตี้ทุกคนต้องอยู่ในแผนที่เดียวกัน");
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
            String v0 = "#r#e<Genesis Weapon>#n#k\r\nต้องสวมใส่เพียง #rอาวุธ#k และ #bอาวุธรอง#k เท่านั้น\r\n\r\n#r<ไอเทมที่ต้องถอดออก>#k\r\n";
            for (int i = 0; i < blockedList.size(); ++i) {
                int bid = blockedList.get(i);
                v0 += "#i" + bid + "# #z" + bid + "#\r\n";
            }
            self.say(v0, ScriptMessageFlag.Self);
            return false;
        }
        return true;
    }

    public void magnus_boss() {
        if (!DBConfig.isGanglim) {
            enter_magnusDoor();
            return;
        }
        initNPC(MapleLifeFactory.getNPC(3001020));
        EventManager em = getEventManager("Magnus");
        if (em == null) {
            self.say("ไม่สามารถเข้าสู่ Magnus Raid ได้ในขณะนี้");
        } else {
            if (target.getParty() == null) {
                self.say("ต้องอยู่ในปาร์ตี้อย่างน้อย 1 คนจึงจะสามารถเข้าได้");
            } else {
                if (target.getParty().getLeader().getId() != target.getId()) {
                    self.say("กรุณาให้หัวหน้าปาร์ตี้เป็นผู้ดำเนินการ");
                } else {
                    if (target.getMapId() == 401060399) { // Easy Magnus entry map
                        if (DBConfig.isGanglim) {
                            self.say("ไม่สามารถเข้าสู่ Magnus Simulation Battle ได้ในขณะนี้");
                        }
                        if (self.askYesNo(
                                "ต้องการเดินทางไปยัง Tyrant's Throne เพื่อกำจัด Magnus หรือไม่?\r\n#b<< Magnus Simulation Battle สามารถเคลียร์ได้วันละ 1 ครั้ง >>\r\n<< สามารถเข้าได้เฉพาะปาร์ตี้ที่มีเลเวล 115 ขึ้นไป >>") == 1) {
                            // 401060200 ~ 401060209
                            if (target.getParty().isPartySameMap()) {
                                boolean canEnter = false;
                                String overLap = checkEventNumber(getPlayer(), QuestExConstants.Magnus.getQuestID());
                                if (overLap == null) {
                                    if (em.getProperty("status0").equals("0")) {
                                        canEnter = true;
                                    }
                                    if (canEnter) {
                                        em.setProperty("status0", "1");
                                        EventInstanceManager eim = em.readyInstance();
                                        eim.setProperty("map", 401060300);
                                        eim.setProperty("mode", "easy");
                                        getClient().getChannelServer().getMapFactory().getMap(401060300)
                                                .resetFully(false);
                                        updateLastDate(getPlayer(), QuestExConstants.Magnus.getQuestID()); // Easy and
                                                                                                           // Normal
                                                                                                           // Magnus
                                                                                                           // share time
                                        eim.registerParty(target.getParty(), getPlayer().getMap());
                                    } else {
                                        self.sayOk("มีการต่อสู้กับ Magnus ในแชนแนลนี้อยู่แล้ว");
                                    }
                                } else {
                                    self.say("สมาชิกในปาร์ตี้ #b#e" + overLap
                                            + "#n#k ได้เข้าสู่ดันเจี้ยนไปแล้วในวันนี้ ไม่สามารถเข้าได้อีก");
                                }
                            } else {
                                self.say("สมาชิกปาร์ตี้ทุกคนต้องอยู่ในแผนที่เดียวกัน");
                            }
                        }
                    } else {
                        String text = "ต้องการเดินทางไปยัง Tyrant's Throne เพื่อกำจัด Magnus หรือไม่?#b\r\n";
                        text += "#L0#เข้าสู่ Tyrant's Throne (Hard) (เลเวล 175 ขึ้นไป)";
                        if (DBConfig.isGanglim) {
                            text += " #r[" + getPlayer()
                                    .getOneInfoQuestInteger(QuestExConstants.HardMagnus.getQuestID(), "eNum") + "/"
                                    + (getPlayer().getBossTier() + 1) + "]#b";

                            getPlayer().getPartyMembers()
                                    .forEach(chr -> chr.dropMessage(5, "[Hard Magnus] วันนี้เข้าสู้บอสไปแล้ว "
                                            + chr.getOneInfoQuestInteger(QuestExConstants.HardMagnus.getQuestID(),
                                                    "eNum")
                                            + " ครั้ง สามารถเข้าได้ทั้งหมด " + (chr.getBossTier() + 1) + " ครั้ง"));
                        }
                        text += "\r\n#L1#เข้าสู่ Tyrant's Throne (Normal) (เลเวล 155 ขึ้นไป)";
                        if (DBConfig.isGanglim) {
                            text += " #r["
                                    + getPlayer().getOneInfoQuestInteger(QuestExConstants.Magnus.getQuestID(), "eNum")
                                    + "/" + (getPlayer().getBossTier() + 1) + "]#b";
                            getPlayer().getPartyMembers().forEach(chr -> chr.dropMessage(5,
                                    "[Normal Magnus] วันนี้เข้าสู้บอสไปแล้ว "
                                            + chr.getOneInfoQuestInteger(QuestExConstants.Magnus.getQuestID(), "eNum")
                                            + " ครั้ง สามารถเข้าได้ทั้งหมด " + (chr.getBossTier() + 1) + " ครั้ง"));
                        }
                        text += "\r\n#L3#เข้าสู่ Tyrant's Throne (Hard) Practice Mode (เลเวล 175 ขึ้นไป)#l\r\n#L2#ยกเลิก#l";

                        int v0 = self.askMenu(text);
                        if (target.getParty().isPartySameMap()) {
                            boolean canEnter = false;
                            if (v0 != 3) {
                                int v2 = -1;
                                if (v0 == 0) {
                                    if (getPlayer().getQuestStatus(2000021) == 1) {
                                        if (GameConstants.isZero(getPlayer().getJob())) {
                                            v2 = self.askMenu(
                                                    "#e<Genesis Weapon>#n\r\nสามารถทำภารกิจเพื่อปลดล็อคความลับของ #bGenesis Weapon#k ที่มีพลังของ Black Mage ได้ จะทำอย่างไร?\r\n\r\n#e#r<เงื่อนไขภารกิจ>#n#k\r\n#b -กำจัดบอสคนเดียว\r\n -Final Damage ลดลง 50%\r\n -ใช้เฉพาะค่าสถานะบริสุทธิ์ของอุปกรณ์ที่สวมใส่เท่านั้น\r\n#k#L0#รับภารกิจ#l\r\n#L1#ไม่รับภารกิจ#l",
                                                    ScriptMessageFlag.Self);
                                        } else {
                                            v2 = self.askMenu(
                                                    "#e<Genesis Weapon>#n\r\nสามารถทำภารกิจเพื่อปลดล็อคความลับของ #bGenesis Weapon#k ที่มีพลังของ Black Mage ได้ จะทำอย่างไร?\r\n\r\n#e#r<เงื่อนไขภารกิจ>#n#k\r\n#b -กำจัดบอสคนเดียว\r\n -สวมใส่ได้เฉพาะ Genesis Weapon ที่ถูกผนึกและอาวุธรองเท่านั้น\r\n -Final Damage ลดลง 50%\r\n -ใช้เฉพาะค่าสถานะบริสุทธิ์ของอุปกรณ์ที่สวมใส่เท่านั้น\r\n#k#L0#รับภารกิจ#l\r\n#L1#ไม่รับภารกิจ#l",
                                                    ScriptMessageFlag.Self);
                                        }
                                        if (v2 == 0) {
                                            if (!checkBMQuestEquip()) {
                                                return;
                                            }
                                            if (getPlayer().getParty().getPartyMemberList().size() > 1) {
                                                self.say("ภารกิจนี้ต้องทำคนเดียวเท่านั้น", ScriptMessageFlag.Self);
                                                return;
                                            }
                                        }
                                    }
                                }

                                String overLap = checkEventNumber(getPlayer(), QuestExConstants.Magnus.getQuestID(),
                                        DBConfig.isGanglim);
                                if (v0 == 0) { // Hard Magnus
                                    overLap = checkEventNumber(getPlayer(), QuestExConstants.HardMagnus.getQuestID(),
                                            DBConfig.isGanglim);
                                }
                                if (overLap == null) {
                                    if (v0 == 0) { // Hard Magnus
                                        String lastDate = checkEventLastDate(getPlayer(),
                                                QuestExConstants.HardMagnus.getQuestID());
                                        if (lastDate == null || DBConfig.isGanglim) { // Ganglim removed 30min re-entry
                                            if (em.getProperty("Hstatus0").equals("0")) {
                                                canEnter = true;
                                            }
                                            if (canEnter) {
                                                em.setProperty("Hstatus0", "1");
                                                EventInstanceManager eim = em.readyInstance();
                                                eim.setProperty("map", 401060100);
                                                eim.setProperty("mode", "hard");
                                                getClient().getChannelServer().getMapFactory().getMap(401060100)
                                                        .resetFully(false);
                                                updateLastDate(getPlayer(), QuestExConstants.HardMagnus.getQuestID());
                                                if (DBConfig.isGanglim) {
                                                    updateQuestEx(getPlayer(),
                                                            QuestExConstants.HardMagnus.getQuestID());
                                                }
                                                if (v2 == 0) {
                                                    getPlayer().applyBMCurse1(3);
                                                }
                                                eim.registerParty(target.getParty(), getPlayer().getMap());
                                            } else {
                                                self.sayOk("แผนที่เต็ม ไม่สามารถใช้บริการได้ กรุณาลองใหม่ในแชนแนลอื่น");
                                            }
                                        } else {
                                            self.say("สมาชิกในปาร์ตี้จะสามารถเข้าได้อีกครั้งในอีก #b#e" + lastDate
                                                    + " #n#k"); // Official: Party member entered within 30 mins. Cannot
                                                                // re-enter within 30 mins after entry.
                                        }
                                    } else if (v0 == 1) { // Normal Magnus
                                        if (em.getProperty("Nstatus0").equals("0")) {
                                            canEnter = true;
                                        }
                                        if (canEnter) {
                                            em.setProperty("Nstatus0", "1");
                                            EventInstanceManager eim = em.readyInstance();
                                            eim.setProperty("map", 401060200);
                                            eim.setProperty("mode", "normal");
                                            getClient().getChannelServer().getMapFactory().getMap(401060200)
                                                    .resetFully(false);
                                            updateLastDate(getPlayer(), QuestExConstants.Magnus.getQuestID());
                                            if (DBConfig.isGanglim) {
                                                updateQuestEx(getPlayer(), QuestExConstants.Magnus.getQuestID());
                                            }
                                            eim.registerParty(target.getParty(), getPlayer().getMap());
                                        } else {
                                            self.sayOk("แผนที่เต็ม ไม่สามารถใช้บริการได้ กรุณาลองใหม่ในแชนแนลอื่น");
                                        }
                                    }
                                } else {
                                    String text_ = "สมาชิกในปาร์ตี้ #b#e" + overLap
                                            + "#n#k ได้เข้าสู่ดันเจี้ยนไปแล้วในวันนี้ ไม่สามารถเข้าได้อีก";
                                    if (!DBConfig.isGanglim) {
                                        text_ += "\r\n(Hard Magnus จะรีเซ็ตทุกวันพฤหัสบดี)";
                                    }
                                    self.say(text_);
                                }
                            } else {
                                self.say("โหมดฝึกซ้อมกำลังอยู่ระหว่างการเตรียมการ");
                            }
                        } else {
                            self.say("สมาชิกปาร์ตี้ทุกคนต้องอยู่ในแผนที่เดียวกัน");
                        }
                    }
                }
            }
        }
    }

    public void magnus_summon() { // Hard Magnus Summon
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            if (eim.getProperty("summonMOB") == null) {
                eim.setProperty("summonMOB", "1");
                Field field = getPlayer().getMap();
                if (DBConfig.isGanglim) {
                    field.spawnMonster(MapleLifeFactory.getMonster(8880000), new Point(1860, -1450), 32);
                } else {
                    if (getPlayer().getPartyMembers().size() == 1) {
                        field.spawnMonster(MapleLifeFactory.getMonster(8880000), new Point(1860, -1450), 32);
                    } else {
                        final MapleMonster magnus = MapleLifeFactory.getMonster(8880000);
                        magnus.setPosition(new Point(1860, -1450));
                        final long hp = magnus.getMobMaxHp();
                        ChangeableStats cs = new ChangeableStats(magnus.getStats());
                        cs.hp = hp * 3L;
                        if (cs.hp < 0) {
                            cs.hp = Long.MAX_VALUE;
                        }
                        magnus.getStats().setHp(cs.hp);
                        magnus.getStats().setMaxHp(cs.hp);
                        magnus.setOverrideStats(cs);

                        field.spawnMonster(magnus, 32);
                    }
                }
            }
        }
    }

    public void magnus_summon_N() { // Normal Magnus Summon
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            if (eim.getProperty("summonMOB") == null) {
                eim.setProperty("summonMOB", "1");
                Field field = getPlayer().getMap();
                if (DBConfig.isGanglim) {
                    field.spawnMonster(MapleLifeFactory.getMonster(8880002), new Point(1860, -1450), 32);
                } else {
                    if (getPlayer().getPartyMembers().size() == 1) {
                        field.spawnMonster(MapleLifeFactory.getMonster(8880002), new Point(1860, -1450), 32);
                    } else {
                        final MapleMonster magnus = MapleLifeFactory.getMonster(8880002);
                        magnus.setPosition(new Point(1860, -1450));
                        final long hp = magnus.getMobMaxHp();
                        ChangeableStats cs = new ChangeableStats(magnus.getStats());
                        cs.hp = hp * 3L;
                        ;
                        if (cs.hp < 0) {
                            cs.hp = Long.MAX_VALUE;
                        }
                        magnus.getStats().setHp(cs.hp);
                        magnus.getStats().setMaxHp(cs.hp);
                        magnus.setOverrideStats(cs);

                        field.spawnMonster(magnus, 32);
                    }
                }
            }
        }
    }

    public void magnus_summon_E() { // Easy Magnus Summon
        EventInstanceManager eim = getEventInstance();
        if (eim != null) {
            if (eim.getProperty("summonMOB") == null) {
                eim.setProperty("summonMOB", "1");
                Field field = getPlayer().getMap();
                field.spawnMonster(MapleLifeFactory.getMonster(8880010), new Point(1860, -1450), 32);
            }
        }
    }

    public void out_magnusDoor() {
        initNPC(MapleLifeFactory.getNPC(3001020));
        if (self.askYesNo("ต้องการออกจากพื้นที่ต่อสู้หรือไม่?") == 1) {
            getPlayer().setRegisterTransferFieldTime(0);
            getPlayer().setRegisterTransferField(0);
            List<Integer> normalMap = new ArrayList(Arrays.asList(401060200, 401060201, 401060202, 401060203, 401060204,
                    401060205, 401060206, 401060207, 401060208, 401060209));
            if (normalMap.contains(target.getMapId())) {
                registerTransferField(401060399); // Easy Magnus
            } else {
                registerTransferField(401060000);
            }
            if (getPlayer().getEventInstance() != null) {
                getPlayer().getEventInstance().unregisterPlayer(getPlayer());
                getPlayer().setEventInstance(null);
            }
        }
    }

    public void magnus_out() {
        if (self.askYesNo("ต้องการออกจากพื้นที่ต่อสู้หรือไม่?") == 1) {
            getPlayer().setRegisterTransferFieldTime(0);
            getPlayer().setRegisterTransferField(0);
            List<Integer> normalMap = Arrays.asList(401060200, 401060201, 401060202, 401060203, 401060204, 401060205,
                    401060206, 401060207, 401060208, 401060209);
            if (!DBConfig.isGanglim && normalMap.contains(target.getMapId())) {
                registerTransferField(401060399); // Easy Magnus
            } else {
                registerTransferField(401060000);
            }
            if (getPlayer().getEventInstance() != null) {
                getPlayer().getEventInstance().unregisterPlayer(getPlayer());
                getPlayer().setEventInstance(null);
            }
        }
    }

    /*
     * FieldSet fieldSet = fieldSet("EasyMagnusEnter");
     * if (target.getMapId() != 401060399) {
     * fieldSet = fieldSet("MagnusEnter");
     * }
     * if (fieldSet == null) {
     * self.sayOk("지금은 매그너스 레이드를 이용하실 수 없습니다.");
     * return;
     * }
     * boolean enterField = false;
     * if (target.getMapId() == 401060399) { //이지매그입장맵
     * if (self.
     * askYesNo("매그너스 퇴치를 위해 폭군의 왕좌로 이동 하시겠습니까??\r\n#b<< 매그너스 모의전은 1일에 1회 클리어 가능합니다. >>\r\n<<115 레벨 이상 유저 간의 파티로 입장하실 수 있습니다.>>"
     * ) == 1) {
     * enterField = true;
     * }
     * } else { //노말, 하드매그
     * //TODO
     * }
     * if (enterField) {
     * int enter = fieldSet.enter(target.getId());
     * if (enter == -1) self.say("알 수 없는 이유로 입장할 수 없습니다. 잠시 후에 다시 시도해 주십시오.");
     * else if (enter == 1) self.say("파티를 맺어야만 도전할 수 있습니다.");
     * else if (enter == 2) self.say("파티장을 통해 진행해 주십시오.");
     * else if (enter == 3) self.say("최소 " + fieldSet.minMember +
     * "인 이상의 파티가 퀘스트를 시작할 수 있습니다.");
     * else if (enter == 4) self.say("파티원의 레벨은 최소 " + fieldSet.minLv +
     * " 이상이어야 합니다.");
     * else if (enter == 5) self.say("파티원이 모두 모여 있어야 시작할 수 있습니다.");
     * else if (enter == 6) self.say("이미 다른 원정대가 안으로 들어가 퀘스트 클리어에 도전하고 있는 중입니다.");
     * else if (enter == 7) { //30분 대기시간이 발생한경우
     * self.say("30분 이내에 입장한 파티원이 있습니다. 입장 후 30분 이내에 재입장이 불가능합니다.");
     * } else if (enter < -1) {
     * MapleCharacter user =
     * getClient().getChannelServer().getPlayerStorage().getCharacterById(enter *
     * -1);
     * String name = "";
     * if (user != null) {
     * name = user.getName();
     * }
     * if (target.getMapId() != 401060399) {
     * self.
     * sayOk("최근 일주일 이내 <보스:매그너스> 하드 모드를 클리어한 파티원이 있습니다. <보스:매그너스> 하드 모드는 일주일에 1회만 클리어 가능합니다.\r\n#r#e<클리어 기록은 매주 목요일에 일괄 초기화 됩니다.>"
     * );
     * } else {
     * self.say("파티원 중 #b#e" + name + "#k#n 님이 오늘 매그너스에 입장하셔서 들어갈 수 없습니다.");
     * }
     * }
     * }
     */
    // TODO mag_GateWayOut(이지매그퇴장), BPReturn_Magnus2(노말매그,하드매그퇴장)
}
