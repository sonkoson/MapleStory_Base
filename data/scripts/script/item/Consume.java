package script.item;

import constants.GameConstants;
import constants.JosaType;
import constants.Locales;
import constants.QuestExConstants;
import database.DBConfig;
import database.DBConnection;
import database.loader.CharacterSaveFlag;
import logging.LoggingManager;
import logging.entry.ConsumeLog;
import network.auction.AuctionServer;
import network.center.Center;
import network.game.GameServer;
import network.models.CWvsContext;
import network.shop.CashShopServer;
import objects.context.MonsterCollection;
import objects.fields.Field;
import objects.fields.gameobject.lifes.ChangeableStats;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.fields.gameobject.lifes.MapleMonster;
import objects.fields.gameobject.lifes.OverrideMonsterStats;
import objects.item.*;
import objects.quest.QuestEx;
import objects.users.MapleCharacter;
import objects.users.MapleTrait;
import objects.users.enchant.*;
import objects.utils.*;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

import java.nio.file.Paths;
import java.sql.*;
import java.text.NumberFormat;
import java.util.*;
import java.time.LocalDateTime;

public class Consume extends ScriptEngineNPC {

    public void decreaseBossCount(List<Triple<Integer, String, String>> bossList, int itemID) {

        if (getPlayer().getMap().getFieldSetInstance() != null) {
            getPlayer().dropMessage(5, "ไม่สามารถใช้งานได้ขณะดำเนินการบอส");
            return;
        }

        if (DBConfig.isGanglim) {
            LocalDateTime now = LocalDateTime.now();
            int hours = now.getHour();
            int minutes = now.getMinute();

            if (hours >= 23 && minutes >= 50) {
                self.sayOk("#fs11#ตั้งแต่เวลา 11:50 ถึง 12:00 ไม่สามารถใช้ตั๋วลบจำนวนครั้งได้ครับ",
                        ScriptMessageFlag.NpcReplacedByUser);
                return;
            }

            StringBuilder bossComment = new StringBuilder("#fs11#ต้องการลด #bจำนวนการเข้า#k บอสไหน 1 ครั้งดีครับ?\r\n")
                    .append("ตั๋วลบสามารถใช้ได้ #r20 ครั้ง#k ต่อวัน เลือกให้ดีนะครับ\r\n\r\n");

            int i = 0;
            for (var triple : bossList) {
                bossComment.append("#L").append(i).append("##b#fUI/UIWindow2.img/UserList/Main/Boss/BossList/")
                        .append(triple.left).append("/Icon/normal/0##k").append(triple.mid).append("\r\n");
                i++;
            }

            int select = self.askMenu(bossComment.toString(), ScriptMessageFlag.NpcReplacedByUser);
            var triple = bossList.get(select);

            int cancount = 20 - getPlayer().getOneInfoQuestInteger(1234569, "OffsetCount");

            int usecount = self.askNumber(
                    "#fs11##fUI/UIWindow2.img/UserList/Main/Boss/BossList/" + triple.left + "/Icon/normal/0#\r\n#r#e"
                            + triple.mid + " #k#n จะใช้ตั๋วลบจำนวนกี่ใบดีครับ?\r\nจำนวนที่สามารถใช้ได้ : " + cancount,
                    1, 1, cancount, ScriptMessageFlag.NpcReplacedByUser);
            if (usecount < 1)
                return;
            if (usecount > 20)
                return;

            if (1 == self.askYesNo(String.format("#r#e#fs11##fUI/UIWindow2.img/UserList/Main/Boss/BossList/" +
                    "%d" +
                    "/Icon/normal/0#\r\n" +
                    "%s" +
                    "#fs11##n#k ต้องการลดจำนวนการเคลียร์ " + usecount + " ครั้งหรือไม่ครับ?\r\n\r\n" +
                    "#fs11##r(※ เมื่อใช้แล้วไม่สามารถย้อนคืนได้)", triple.left, triple.mid),
                    ScriptMessageFlag.NpcReplacedByUser)) {
                int count = getPlayer().getOneInfoQuestInteger(1234569, triple.right);
                int count2 = getPlayer().getOneInfoQuestInteger(1234570, triple.right);
                int count3 = getPlayer().getOneInfoQuestInteger(1234589, triple.right);
                int count4 = getPlayer().getOneInfoQuestInteger(1234590, triple.right);

                if (count < usecount && count2 < usecount && count3 < usecount && count4 < usecount) {
                    self.sayOk("#fs11#จำนวนการเข้าบอสน้อยกว่าจำนวนตั๋วที่จะใช้ไม่ใช่เหรอครับ?",
                            ScriptMessageFlag.NpcReplacedByUser);
                    return;
                }

                if (target.exchange(itemID, -usecount) == 1) {
                    if (count > 0) {
                        getPlayer().updateOneInfo(1234569, triple.right, String.valueOf(count - usecount));
                    }
                    if (count2 > 0) {
                        getPlayer().updateOneInfo(1234570, triple.right, String.valueOf(count2 - usecount));
                    }
                    if (count3 > 0) {
                        getPlayer().updateOneInfo(1234589, triple.right, String.valueOf(count3 - usecount));
                    }
                    if (count4 > 0) {
                        getPlayer().updateOneInfo(1234590, triple.right, String.valueOf(count4 - usecount));
                    }

                    int offsetCount = getPlayer().getOneInfoQuestInteger(1234569, "OffsetCount");
                    int currentCount = offsetCount + usecount;
                    getPlayer().updateOneInfo(1234569, "OffsetCount", String.valueOf(currentCount));

                    String str = "#r#e#fs11##fUI/UIWindow2.img/UserList/Main/Boss/BossList/" + triple.left
                            + "/Icon/normal/0#\r\n" +
                            triple.mid +
                            "#fs11##n#k ลดจำนวนการเคลียร์ " + usecount + " ครั้งเรียบร้อยแล้วครับ\r\n"
                            + "#fs11##n#kจำนวนครั้งที่เหลือที่ใช้ได้: "
                            + (20 - currentCount);

                    getPlayer().setSaveFlag(getPlayer().getSaveFlag() | CharacterSaveFlag.QUEST_INFO.getFlag()); // questinfo
                                                                                                                 // บันทึก
                    getPlayer().saveToDB(false, false);
                    self.sayOk(str, ScriptMessageFlag.NpcReplacedByUser);
                }

            }
        }
    }

    public void clearBossCount(List<Triple<Integer, String, String>> bossList, int itemID) {
        if (DBConfig.isGanglim) {
            LocalDateTime now = LocalDateTime.now();
            int hours = now.getHour();
            int minutes = now.getMinute();

            if (hours >= 23 && minutes >= 50) {
                self.sayOk("ตั้งแต่เวลา 11:50 ถึง 12:00 ไม่สามารถใช้ตั๋วรีเซ็ตได้ครับ",
                        ScriptMessageFlag.NpcReplacedByUser);
                return;
            }

            StringBuilder bossComment = new StringBuilder("#fs11#ต้องการรีเซ็ต #bจำนวนการเข้า#k บอสไหนดีครับ?\r\n")
                    .append("ตั๋วรีเซ็ตสามารถใช้ได้ #r3 ครั้ง#k ต่อวัน\r\n\r\n");

            int i = 0;
            for (var triple : bossList) {
                bossComment.append("#L").append(i).append("##b#fUI/UIWindow2.img/UserList/Main/Boss/BossList/")
                        .append(triple.left).append("/Icon/normal/0##k").append(triple.mid).append("\r\n");
                i++;
            }

            int select = self.askMenu(bossComment.toString(), ScriptMessageFlag.NpcReplacedByUser);
            var triple = bossList.get(select);
            if (1 == self.askYesNo(String.format("#r#e#fs11##fUI/UIWindow2.img/UserList/Main/Boss/BossList/" +
                    "%d" +
                    "/Icon/normal/0#\r\n" +
                    "%s" +
                    "#fs11##n#k ต้องการรีเซ็ตจำนวนการเคลียร์ใช่ไหมครับ?\r\n\r\n" +
                    "#fs11##r(※ เมื่อใช้แล้วไม่สามารถย้อนคืนได้)", triple.left, triple.mid),
                    ScriptMessageFlag.NpcReplacedByUser)) {
                String date = String.valueOf(GameConstants.getCurrentDate_NoTime());

                int used = getPlayer().getOneInfoQuestInteger(1234569, "ResetBoss");
                if (used >= 3) {
                    self.sayOk("วันนี้ใช้ตั๋วรีเซ็ตครบ 3 ครั้งแล้วครับ พรุ่งนี้ค่อยลองใหม่นะครับ",
                            ScriptMessageFlag.NpcReplacedByUser);
                    return;
                }

                if (target.exchange(itemID, -1) == 1) {
                    getPlayer().updateOneInfo(1234569, "ResetBoss", String.valueOf(used + 1));

                    getPlayer().updateOneInfo(1234569, triple.right, "0");
                    getPlayer().updateOneInfo(1234570, triple.right, "0");
                    getPlayer().updateOneInfo(1234589, triple.right, "0");
                    getPlayer().updateOneInfo(1234590, triple.right, "0");

                    String str = "#r#e#fs11##fUI/UIWindow2.img/UserList/Main/Boss/BossList/" + triple.left
                            + "/Icon/normal/0#\r\n" +
                            triple.mid +
                            "#fs11##n#k รีเซ็ตจำนวนการเคลียร์เรียบร้อยแล้วครับ";

                    self.sayOk("รีเซ็ตเรียบร้อยแล้วครับ จำนวนครั้งที่เหลือ: " + (3 - (used + 1)) + " ครั้ง",
                            ScriptMessageFlag.NpcReplacedByUser);
                }
            }
        }
    }

    public void compassUse_cash() {
        List<Triple<Integer, String, String>> bossList = List.of(
                new Triple<>(13, "สวู", "swoo_clear"),
                new Triple<>(15, "เดเมียน", "demian_clear"),
                new Triple<>(19, "ลูซิด", "lucid_clear"),
                new Triple<>(23, "วิลล์", "will_clear"));

        clearBossCount(bossList, 2430030);
    }

    public void snapShot() {
        List<Triple<Integer, String, String>> bossList = List.of(
                new Triple<>(27, "ดุงเคล", "dunkel_clear"),
                new Triple<>(26, "ดัสก์", "dusk_clear"),
                new Triple<>(24, "จิน ฮิลล่า", "jinhillah_clear"),
                new Triple<>(29, "การ์เดียน แองเจิล สไลม์", "guardian_angel_slime_clear"));

        clearBossCount(bossList, 2430031);
    }

    public void blackBag() {
        List<Triple<Integer, String, String>> bossList = List.of(
                new Triple<>(13, "สวู", "swoo_clear"),
                new Triple<>(15, "เดเมียน", "demian_clear"),
                new Triple<>(19, "ลูซิด", "lucid_clear"),
                new Triple<>(23, "วิลล์", "will_clear"));

        decreaseBossCount(bossList, 2430032);
    }

    public void xmas_present00() {
        List<Triple<Integer, String, String>> bossList = List.of(
                new Triple<>(27, "ดุงเคล", "dunkel_clear"),
                new Triple<>(26, "ดัสก์", "dusk_clear"),
                new Triple<>(24, "จิน ฮิลล่า", "jinhillah_clear"),
                new Triple<>(29, "การ์เดียน แองเจิล สไลม์", "guardian_angel_slime_clear"));

        decreaseBossCount(bossList, 2430033);
    }

    // Jin: True Upgrade Support Box
    public void consume_2439600() {
        int[][] rewards = new int[][] {
                { 2439602, 1 }, // Jin: True Weapon Support Box
                /*
                 * {1003243, 1}, // Maple Platinum Beret
                 * {1102295, 1},// Maple Platinum Cloak
                 * {1052358, 1}, // Maple Platinum Lisene
                 * {1072522, 1}, // Maple Platinum Shoes
                 */
                { 1004492, 1 }, // Maple Treasure Cap
                { 1102828, 1 }, // Maple Treasure Cape
                { 1052929, 1 }, // Maple Treasure Suit
                { 1132287, 1 }, // Maple Treasure Belt
                { 1152187, 1 }, // Maple Treasure Shoulder
                { 1073057, 1 }, // Maple Treasure Shoes
                { 1082647, 1 }, // Maple Treasure Gloves
        };
        int[] weapons = new int[] {
                1212098, 1213009, 1214009, 1222092, 1232092, 1242099, 1272020, 1282020, 1302312, 1312182, 1322233,
                1332257, 1342097, 1362118, 1372204, 1382242, 1402233, 1412161, 1422168, 1432197, 1442251, 1452235,
                1462222, 1472244, 1482199, 1492209, 1522121, 1532127, 1592010, 1292009, 1262012, 1582012, 1404009
        };

        initNPC(MapleLifeFactory.getNPC(9062474));
        String v0 = "หากเปิดกล่องจะได้รับไอเทมดังต่อไปนี้ ต้องการเปิดไหมครับ?\r\n\r\n";
        v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
        for (int[] reward : rewards) {
            if (reward[1] != -1) {
                v0 += "#b#i" + reward[0] + "# #z" + reward[0] + "##k " + reward[1] + "개\r\n";
            }
        }
        v0 += "\r\n#bได้รับ Zakum's Poisonic Weapon 1 ชิ้น (เลือกได้)\r\n";

        if (DBConfig.isGanglim) {
            v0 += "  - ใช้งาน Star Force 10 ดาว, มอบออปชั่นศักยภาพ Epic, All Stat +30, Atk/M.Atk +15\r\n";
        } else {
            v0 += "  - ใช้งาน Star Force 10 ดาว, มอบออปชั่นศักยภาพ Unique, All Stat 30\r\n";
        }
        if (DBConfig.isGanglim) {
            v0 += "\r\nอุปกรณ์ Maple Treasure ทั้งหมดมี All Stat +30, Atk/M.Atk +15\r\n";
        }
        v0 += "\r\n#k#e[สกิลที่จะได้รับ]#n#b\r\n";
        v0 += "#s80001825# Wan Seom\r\n";
        v0 += "#s80001829# Biyeon\r\n";
        int v1 = self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByUser);
        if (v1 > 0) {
            // Originally exchange is possible, but logic needed to check items
            if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 1 ||
                    getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 8) {

                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Equip#k และ #bกระเป๋า Use#k แล้วลองใหม่นะครับ",
                        ScriptMessageFlag.NpcReplacedByUser);
            } else {
                boolean isZero = false;
                int v3 = -1;
                if (GameConstants.isZero(getPlayer().getJob())) {
                    isZero = true;
                } else {
                    String v2 = "สามารถเลือกอาวุธหนึ่งชิ้นจากรายการต่อไปนี้ครับ เลือกชิ้นไหนดีครับ?#b\r\n\r\n";
                    if (DBConfig.isGanglim) {
                        v2 = "สามารถเลือกอาวุธหนึ่งชิ้นจากรายการต่อไปนี้ครับ เลือกชิ้นไหนดีครับ?#b\r\n\r\n";
                    }
                    for (int i = 0; i < weapons.length; ++i) {
                        int weapon = weapons[i];
                        v2 += "#L" + i + "##i" + weapon + "# #z" + weapon + "##l\r\n";
                    }
                    v3 = self.askMenu(v2, ScriptMessageFlag.NpcReplacedByUser);
                }
                if (v3 >= 0 || isZero) {
                    int itemID = 0;
                    String v4 = "คุณจะได้รับไอเทมตามรายการต่อไปนี้ ต้องการดำเนินการต่อไหมครับ?\r\n\r\n";
                    v4 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
                    if (!isZero) {
                        itemID = weapons[v3];
                        v4 += "#e#i" + itemID + "# #z" + itemID + "# (เลือก)#n\r\n";
                    }
                    for (int[] reward : rewards) {
                        if (reward[1] != -1) {
                            v4 += "#b#i" + reward[0] + "# #z" + reward[0] + "##k " + reward[1] + "개\r\n";
                        }
                    }
                    if (self.askYesNo(v4, ScriptMessageFlag.NpcReplacedByUser) == 1) {

                        if (target.exchange(2439600, -1, 2439602, 1) == 1) {
                            getPlayer().changeSkillLevel(80001825, 30, 30);
                            getPlayer().changeSkillLevel(80001829, 5, 5);

                            if (getPlayer().getOneInfoQuestInteger(1234569, "get_treasure_set") == 0) {
                                exchangeSupportEquip(1004492, 30, 15, 100);
                                exchangeSupportEquip(1102828, 30, 15, 100);
                                exchangeSupportEquip(1052929, 30, 15, 100);
                                exchangeSupportEquip(1132287, 30, 15, 100);
                                exchangeSupportEquip(1152187, 30, 15, 100);
                                exchangeSupportEquip(1073057, 30, 15, 100);
                                exchangeSupportEquip(1082647, 30, 15, 100);
                                exchangeSupportEquip(itemID, 30, 15, 100);
                                self.say("เปิดกล่องได้รับไอเทมเรียบร้อยแล้วครับ", ScriptMessageFlag.NpcReplacedByUser);
                                getPlayer().updateOneInfo(1234569, "get_treasure_set", "1");
                            } else {
                                self.say(
                                        "ได้รับเซ็ต Maple Treasure ไปแล้ว จึงไม่ได้รับซ้ำครับ ลองตรวจสอบไอเทมที่เหลือดูนะครับ",
                                        ScriptMessageFlag.NpcReplacedByUser);
                            }

                        }
                    } else {
                        if (DBConfig.isGanglim) {
                            self.say("ลองคิดดูอีกทีนะครับ", ScriptMessageFlag.NpcReplacedByUser);
                        } else {
                            self.say("ดูเหมือนต้องลองคิดดูอีกทีนะครับ", ScriptMessageFlag.NpcReplacedByUser);
                        }
                    }

                }
            }
        }
    }

    // Jin: True Nice to Meet You! Box
    public void consume_2439601() {
        initNPC(MapleLifeFactory.getNPC(9062474));
        if (getPlayer().getOneInfoQuestInteger(1234567, "use_first_support") == 1) {
            if (DBConfig.isGanglim) {
                self.say("เหมือนว่าจะเคยรับรางวัลไปแล้วนะครับ", ScriptMessageFlag.NpcReplacedByUser);
            } else {
                self.say("เหมือนว่าจะเคยรับรางวัลไปแล้วนะครับ?", ScriptMessageFlag.NpcReplacedByUser);
            }

            if (target.exchange(2439601, -1) == 1) {
            }
            return;
        }
        int[][] rewards = new int[][] {
                { 2439605, 1 }, // Jin: True Special Cody Box (S)
                { 2439604, 5 }, // Jin: True Special Cody Box (R)
                { 2436018, 1 }, // Jin: True Special Hair Coupon
                { 2439601, -1 }, // Box Use
        };
        String v0 = "หากเปิดกล่องจะได้รับไอเทมดังต่อไปนี้ ต้องการเปิดไหมครับ?\r\n\r\n";
        if (DBConfig.isGanglim) {
            v0 = "เขาบอกว่าถ้าเปิดกล่องจะได้ไอเทมดังต่อไปนี้ครับ จะเปิดเลยไหมครับ?\r\n\r\n";
        }
        v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
        for (int[] reward : rewards) {
            if (reward[1] != -1) {
                v0 += "#b#i" + reward[0] + "# #z" + reward[0] + "##k " + reward[1] + "개\r\n";
            }
        }
        if (!DBConfig.isGanglim) {
            v0 += "\r\n#b#i4310306# #t4310306# #k100개";
        }
        int v1 = self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByUser);
        if (v1 > 0) {
            if (target.exchange(rewards) == 1) {
                if (!DBConfig.isGanglim) {
                    getPlayer().gainStackEventGauge(0, 100, true);
                }
                getPlayer().updateOneInfo(1234567, "use_first_support", "1");
                self.say("เปิดกล่องได้รับไอเทมเรียบร้อยแล้วครับ", ScriptMessageFlag.NpcReplacedByUser);
            } else {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Use#k แล้วลองใหม่นะครับ", ScriptMessageFlag.NpcReplacedByUser);
            }
        }
    }

    // Jin: True Weapon Support Box
    public void consume_2439602() {
        int[] rewards = new int[] {
                1212127,
                1213039,
                1214031,
                1222120,
                1232120,
                1242061,
                1262048,
                1272037,
                1282037,
                1292039,
                1302353,
                1312210,
                1322265,
                1332287,
                1362147,
                1372235,
                1382272,
                1402266,
                1412187,
                1422195,
                1432225,
                1442283,
                1452264,
                1462250,
                1472273,
                1482230,
                1492243,
                1522150,
                1532155,
                1582042,
                1592029,
                1404016
        };

        if (DBConfig.isGanglim) {
            initNPC(MapleLifeFactory.getNPC(2008));
        } else {
            initNPC(MapleLifeFactory.getNPC(9062474));
        }
        if (GameConstants.isZero(getPlayer().getJob())) {
            self.say("Zero ไม่สามารถใช้บริการนี้ได้ครับ");
            if (target.exchange(2439602, -1) == 1) {
            }
            return;
        }
        String v0 = "เลือกรับ #bอาวุธ Fafnir 1 ชิ้น#k จากรายการต่อไปนี้ครับ\r\nอาวุธที่เลือกสามารถใช้ได้ #e10 วัน#n มีออปชั่น #bAll Stat +200, Atk/M.Atk +200#k\r\n\r\nเลือกอาวุธที่ต้องการได้เลยครับ#b\r\n\r\n";
        if (DBConfig.isGanglim) {
            v0 = "เลือกรับ #bอาวุธ Fafnir 1 ชิ้น#k จากรายการต่อไปนี้ครับ\r\nอาวุธที่เลือกสามารถใช้ได้ #e14 วัน#n มีออปชั่น #bAll Stat +250, Atk/M.Atk +250#k\r\n\r\nเลือกอาวุธที่ต้องการได้เลยครับ#b\r\n\r\n";
        }
        for (int i = 0; i < rewards.length; ++i) {
            int itemID = rewards[i];
            v0 += "#L" + i + "##i" + itemID + "# #z" + itemID + "##l\r\n";
        }

        int v1 = self.askMenu(v0, ScriptMessageFlag.NpcReplacedByNpc);
        if (v1 >= rewards.length) {
            return; // TODO: Hack
        }
        int itemID = rewards[v1];
        String v2 = "อาวุธที่เลือกคือ #b#i" + itemID + "# #z" + itemID
                + "##k ครับ\r\nต้องการเลือกอาวุธนี้ใช่ไหมครับ?\r\n\r\n#b(หากกด #eใช่#n จะได้รับไอเทมทันที)";
        if (DBConfig.isGanglim) {
            v2 = "อาวุธที่เลือกคือ #b#i" + itemID + "# #z" + itemID
                    + "##k ครับ\r\nต้องการเลือกอาวุธนี้ใช่ไหมครับ?\r\n\r\n#b(หากกด #eใช่#n จะได้รับไอเทมทันที)";
        }
        if (self.askYesNo(v2, ScriptMessageFlag.NpcReplacedByNpc) > 0) {
            if (getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 1) {
                if (DBConfig.isGanglim) {
                    self.say("กรุณาทำช่องว่างใน #bกระเป๋า Equip#k แล้วลองใหม่นะครับ");
                } else {
                    self.say("กรุณาทำช่องว่างใน #bกระเป๋า Equip#k แล้วลองใหม่นะครับ");
                }
            } else {
                if (target.exchange(2439602, -1) == 1) {
                    if (DBConfig.isGanglim) {
                        exchangeSupportEquipBonusStatPeriod(itemID, 250, 250, 14);
                        self.say("มอบให้เรียบร้อยแล้วครับ ลองเช็คในกระเป๋าดูนะครับ!");
                    } else {
                        exchangeSupportEquipBonusStatPeriod(itemID, 200, 200, 10);
                        self.say("มอบให้เรียบร้อยแล้วครับ ลองเช็คในกระเป๋าดูนะครับ!");
                    }
                }
            }
        }
    }

    // Newbie Package Box
    public void consume_2439603() {
        initNPC(MapleLifeFactory.getNPC(9062000));
        int[][] rewards = new int[][] {
                { 5068300, 4 }, // Wondrous Berry x4
                { 5069100, 1 }, // Luna Crystal x1
                { 2049360, 3 }, // Amazing Enhancement Scroll x3
                { 2450153, 10 }, // EXP 2x Coupon x10
                { 2436605, 5 }, // Master Craftsman's Cube Lucky Bag x5
                { 2435719, 100 }, // Core Gemstone x100
                { 2439292, 3 }, // Labyrinth Arcane Symbol Box x3
                { 5680410, 1 }, // 1 Billion Meso Exchange Ticket
                { 2439603, -1 }, // Box Use
        };

        String v0 = "หากเปิดกล่องจะได้รับไอเทมดังต่อไปนี้ ต้องการเปิดไหมครับ?\r\n\r\n";
        v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
        for (int[] reward : rewards) {
            if (reward[1] != -1) {
                v0 += "#b#i" + reward[0] + "# #z" + reward[0] + "##k " + reward[1] + "개\r\n";
            }
        }
        v0 += "\r\n#b1,000,000,000 Meso (1 พันล้าน Meso)";
        int v1 = self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByUser);
        if (v1 > 0) {
            if (target.exchange(rewards) == 1) {
                getPlayer().gainMeso(1000000000, true);
                self.say("เปิดกล่องได้รับไอเทมเรียบร้อยแล้วครับ", ScriptMessageFlag.NpcReplacedByUser);
            } else {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Use#k และ #bกระเป๋า Cash#k แล้วลองใหม่นะครับ",
                        ScriptMessageFlag.NpcReplacedByUser);
            }
        }
    }

    // Jin: True Special Cody Box (S)
    static int[] extremeList = new int[] { 1109000, 1009017, 1009018, 1009019, 1009020, 1009021, 1009022, 1009023,
            1009024, 1009025, 1009026, 1009027, 1009028, 1009029, 1009030, 1009031, 1009032, 1009033, 1009034, 1009035,
            1009036, 1009037, 1009038, 1009039, 1009040, 1009041, 1009042, 1009043, 1009044, 1009045, 1009046, 1009047,
            1009048, 1009049, 1009050, 1009051, 1009052, 1009053, 1009054, 1009055, 1009056, 1009057, 1009058, 1009059,
            1009060, 1009061, 1009062, 1009063, 1009064, 1009065, 1009066, 1009067, 1009068, 1009069, 1009070, 1009071,
            1009072, 1009073, 1009074, 1009078, 1009079, 1009080, 1009081, 1009082, 1709000, 1709001, 1709002, 1709003,
            1709004, 1709005, 1709006, 1709007, 1709008, 1709009, 1709010, 1709011, 1709012, 1709013, 1709014, 1709015,
            1709016, 1709017, 1709018, 1079000, 1079001, 1079002, 1079003, 1079004, 1079005, 1059000, 1059001, 1059002,
            1059003, 1059004, 1059005, 1059006, 1059007, 1059008, 1059009, 1059010, 1059011, 1059012, 1059013, 1059014,
            1059015, 1059016, 1059017, 1059018, 1059019, 1059020, 1059021, 1059022, 1059023, 1059024, 1059027, 1059028,
            1059029, 1059030, 1059031, 1059032, 1059033, 1059034, 1059035, 1059036, 1059037, 1059038, 1059039, 1059040,
            1059041, 1059042, 1059043, 1059044, 1059045, 1059046, 1059047, 1059048, 1059049, 1009000, 1009001, 1009002,
            1009003, 1009004, 1009005, 1009006, 1009007, 1009008, 1009009, 1009010, 1009011, 1009012, 1009013, 1009014,
            1009015, 1009016 };
    static int[][] jinList = new int[][] {
            // Hat
            { 1007000, 1007001, 1007002, 1007003, 1007004, 1007005, 1007006, 1007007, 1007008, 1007009, 1007010,
                    1007011, 1007012, 1007013, 1007014, 1007015, 1007016, 1007017, 1007018, 1007019, 1007020, 1007021,
                    1007022, 1007023, 1007024, 1007025, 1007026, 1007027, 1007028, 1007029, 1007030, 1007031, 1007032,
                    1007033, 1007034, 1007035, 1007036, 1007037, 1007038, 1007039, 1007040, 1007041, 1007042, 1007043,
                    1007044, 1007045, 1007046, 1007047, 1007048, 1007049, 1007050, 1007051, 1007052, 1007053, 1007054,
                    1007055, 1007056, 1007057, 1007058, 1007059, 1007060, 1007061, 1007062, 1007063, 1007064, 1007065,
                    1007066, 1007067, 1007068, 1007069, 1007070, 1007071, 1007072, 1007073, 1007074, 1007075, 1007076,
                    1007077, 1007078, 1007079, 1007080, 1007081, 1007082, 1007083, 1007084, 1007085, 1007086, 1007087,
                    1007088, 1007089, 1007090, 1007091, 1007092, 1007093, 1007094, 1007095, 1007096, 1007097, 1007098,
                    1007099, 1007100, 1007101, 1007102, 1007103, 1007104, 1007105, 1007106, 1007107, 1007108, 1007109,
                    1007110, 1007111, 1007112, 1007113, 1007114, 1007115, 1007116, 1007117, 1007118, 1007119, 1007120,
                    1007121, 1007122, 1007123, 1007124, 1007125, 1007126, 1007127, 1007128, 1007129, 1007130, 1007131,
                    1007132, 1007133, 1007134, 1007135, 1007136, 1007137, 1007138, 1007139, 1007140, 1007141, 1007142,
                    1007143, 1007144, 1007145, 1007146, 1007147, 1007148, 1007149, 1007150, 1007151, 1007152, 1007153,
                    1007154, 1007155, 1007156, 1007157, 1007158, 1007159, 1007160, 1007161, 1007162, 1007163, 1007164,
                    1007165, 1007166, 1007167, 1007168, 1007169, 1007170, 1007171, 1007172, 1007173, 1007174, 1007175,
                    1007176, 1007177, 1007178, 1007179, 1007180, 1007181, 1007182, 1007183, 1007184, 1007185, 1007186,
                    1007187, 1007188, 1007189, 1007190, 1007191, 1007192, 1007193, 1007194, 1007195, 1007196, 1007197,
                    1007198, 1007199, 1007200, 1007201, 1007202, 1007203, 1007204, 1007205, 1007206, 1007207, 1007208,
                    1007209, 1007210, 1007211, 1007212, 1007213, 1007214, 1007215, 1007216, 1007217, 1007218, 1007219,
                    1007220, 1007221, 1007222, 1007223, 1007224, 1007225, 1007226, 1007227, 1007228, 1007229 },
            // Overall
            { 1056000, 1056001, 1056002, 1056003, 1056004, 1056005, 1056006, 1056007, 1056008, 1056009, 1056010,
                    1056011, 1056012, 1056013, 1056014, 1056015, 1056016, 1056017, 1056018, 1056019, 1056020, 1056021,
                    1056022, 1056023, 1056024, 1056025, 1056026, 1056027, 1056028, 1056029, 1056030, 1056031, 1056032,
                    1056033, 1056034, 1056035, 1056036, 1056037, 1056038, 1056039, 1056040, 1056041, 1056042, 1056043,
                    1056044, 1056045, 1056046, 1056047, 1056048, 1056049, 1056050, 1056051, 1056052, 1056053, 1056054,
                    1056055, 1056056, 1056057, 1056058, 1056059, 1056060, 1056061, 1056062, 1056063, 1056064, 1056065,
                    1056066, 1056067, 1056068, 1056069, 1056070, 1056071, 1056072, 1056073, 1056074, 1056075, 1056076,
                    1056077, 1056078, 1056079, 1056080, 1056081, 1056082, 1056083, 1056084,
                    1057000, 1057001, 1057002, 1057003, 1057004, 1057005, 1057006, 1057007, 1057008, 1057009, 1057010,
                    1057011, 1057012, 1057013, 1057014, 1057015, 1057016, 1057017, 1057018, 1057019,
                    1058000, 1058001, 1058002, 1058003, 1058004, 1058005, 1058006, 1058007, 1058008, 1058009, 1058010,
                    1058011, 1058012, 1058013, 1058014, 1058015, 1058016, 1058017, 1058018, 1058019, 1058020, 1058021,
                    1058022, 1058023, 1058024, 1058025, 1058026, 1058027, 1058028, 1058029, 1058030, 1058031, 1058032,
                    1058033, 1058034, 1058035, 1058036, 1058037, 1058038, 1058039, 1058040, 1058041, 1058042, 1058043,
                    1058044, 1058045, 1058046, 1058047, 1058048, 1058049, 1058050, 1058051, 1058052, 1058053, 1058054,
                    1058055, 1058056, 1058057, 1058058, 1058059, 1058060, 1058061, 1058062, 1058063, 1058064, 1058065,
                    1058066, 1058067, 1058068, 1058069, 1058070, 1058071, 1058072, 1058073, 1058074, 1058075, 1058076,
                    1058077, 1058078, 1058079, 1058080, 1058081, 1058082, 1058083, 1058084 },
            // Top
            { 1045000, 1045001, 1045002, 1045003, 1045004 },
            // Weapon
            { 1704000, 1704001, 1704002, 1704003, 1704004, 1704005, 1704006, 1704007, 1704008, 1704009, 1704010,
                    1704011, 1704012, 1704013, 1704014,
                    1705000, 1705001, 1705002, 1705003, 1705004, 1705005, 1705006, 1705007, 1705008, 1705009, 1705010,
                    1705011, 1705012, 1705013, 1705014, 1705015, 1705016, 1705017, 1705018, 1705019, 1705020, 1705021,
                    1705022, 1705023, 1705024, 1705025, 1705026, 1705027, 1705028, 1705029, 1705030, 1705031, 1705032,
                    1705033, 1705034, 1705035, 1705036, 1705037, 1705038, 1705039, 1705040, 1705041, 1705042, 1705043,
                    1705044, 1705045, 1705046, 1705047, 1705048, 1705049, 1705050, 1705051, 1705052, 1705053, 1705054,
                    1705055, 1705056, 1705057, 1705058, 1705059, 1705060, 1705061, 1705062, 1705063, 1705064, 1705065,
                    1705066, 1705067, 1705068, 1705069, 1705070, 1705071, 1705072, 1705073, 1705074, 1705075, 1705076,
                    1705077, 1705078, 1705079, 1705080, 1705081, 1705082, 1705083, 1705084, 1705085, 1705086, 1705087,
                    1705088, 1705089 },
            // Cape
            { 1104000, 1104001, 1104002, 1104003, 1104004, 1104005, 1104006, 1104007, 1104008, 1104009, 1104010,
                    1104011, 1104012, 1104013, 1104014, 1104015, 1104016, 1104017, 1104018, 1104019, 1104020, 1104021,
                    1104022, 1104023, 1104024, 1104035, 1104036, 1104037, 1104038, 1104039 },
            // Gloves
            { 1084000, 1084001, 1084002, 1084003, 1084004, 1084005, 1084006, 1084007, 1084008 },
            // Shoes
            { 1075000, 1075001, 1075002, 1075003, 1075004, 1075005, 1075006, 1075007, 1075008, 1075009, 1075010,
                    1075011, 1075012, 1075013, 1075014 },
            // Face Accessory
            { 1012850, 1012851, 1012852, 1012853, 1012854, 1012855, 1012856, 1012857, 1012858, 1012859, 1012860,
                    1012861, 1012862, 1012863, 1012864 }
    };
    static int[][] royalList = new int[][] {
            // Hat
            { 1007000, 1007001, 1007002, 1007003, 1007004, 1007005, 1007006, 1007007, 1007008, 1007009, 1007010,
                    1007011, 1007012, 1007013, 1007014, 1007015, 1007016, 1007017, 1007018, 1007019, 1007020, 1007021,
                    1007022, 1007023, 1007024, 1007025, 1007026, 1007027, 1007028, 1007029, 1007030, 1007031, 1007032,
                    1007033, 1007034, 1007035, 1007036, 1007037, 1007038, 1007039, 1007040, 1007041, 1007042, 1007043,
                    1007044, 1007045, 1007046, 1007047, 1007048, 1007049, 1007050, 1007051, 1007052, 1007053, 1007054,
                    1007055, 1007056, 1007057, 1007058, 1007059, 1007060, 1007061, 1007062, 1007063, 1007064, 1007065,
                    1007066, 1007067, 1007068, 1007069, 1007070, 1007071, 1007072, 1007073, 1007074, 1007075, 1007076,
                    1007077, 1007078, 1007079, 1007080, 1007081, 1007082, 1007083, 1007084, 1007085, 1007086, 1007087,
                    1007088, 1007089, 1007090, 1007091, 1007092, 1007093, 1007094, 1007095, 1007096, 1007097, 1007098,
                    1007099, 1007100, 1007101, 1007102, 1007103, 1007104, 1007105, 1007106, 1007107, 1007108, 1007109,
                    1007110, 1007111, 1007112, 1007113, 1007114, 1007115, 1007116, 1007117, 1007118, 1007119, 1007120,
                    1007121, 1007122, 1007123, 1007124, 1007125, 1007126, 1007127, 1007128, 1007129, 1007130, 1007131,
                    1007132, 1007133, 1007134, 1007135, 1007136, 1007137, 1007138, 1007139, 1007140, 1007141, 1007142,
                    1007143, 1007144, 1007145, 1007146, 1007147, 1007148, 1007149, 1007150, 1007151, 1007152, 1007153,
                    1007154, 1007155, 1007156, 1007157, 1007158, 1007159, 1007160, 1007161, 1007162, 1007163, 1007164,
                    1007165, 1007166, 1007167, 1007168, 1007169, 1007170, 1007171, 1007172, 1007173, 1007174, 1007175,
                    1007176, 1007177, 1007178, 1007179, 1007180, 1007181, 1007182, 1007183, 1007184, 1007185, 1007186,
                    1007187, 1007188, 1007189, 1007190, 1007191, 1007192, 1007193, 1007194, 1007195, 1007196, 1007197,
                    1007198, 1007199, 1007200, 1007201, 1007202, 1007203, 1007204, 1007205, 1007206, 1007207, 1007208,
                    1007209, 1007210, 1007211, 1007212, 1007213, 1007214, 1007215, 1007216 },
            // Overall
            { 1056000, 1056001, 1056002, 1056003, 1056004, 1056005, 1056006, 1056007, 1056008, 1056009, 1056010,
                    1056011, 1056012, 1056013, 1056014, 1056015, 1056016, 1056017, 1056018, 1056019, 1056020, 1056021,
                    1056022, 1056023, 1056024, 1056025, 1056026, 1056027, 1056028, 1056029, 1056030, 1056031, 1056032,
                    1056033, 1056034, 1056035, 1056036, 1056037, 1056038, 1056039, 1056040, 1056041, 1056042, 1056043,
                    1056044, 1056045, 1056046, 1056047, 1056048, 1056049, 1056050, 1056051, 1056052, 1056053, 1056054,
                    1056055, 1056056, 1056057, 1056058, 1056059, 1056060, 1056061, 1056062, 1056063, 1056064, 1056065,
                    1056066, 1056067, 1056068, 1056069, 1056070, 1056071, 1056072, 1056073, 1056074, 1056075, 1056076,
                    1056077, 1056078, 1056079, 1056080, 1056081, 1056082, 1056083, 1056084, 1056085, 1056086, 1056087,
                    1056088, 1056089, 1056090, 1056091, 1056092, 1056093, 1056094, 1056095, 1056096, 1056097, 1057000,
                    1057001, 1057002, 1057003, 1057004, 1057005, 1057006, 1057007, 1057008, 1057009, 1057010, 1057011,
                    1057012, 1057013, 1057014, 1057015, 1057016, 1057017, 1057018, 1057019, 1057020, 1058000, 1058001,
                    1058002, 1058003, 1058004, 1058005, 1058006, 1058007, 1058008, 1058009, 1058010, 1058011, 1058012,
                    1058013, 1058014, 1058015, 1058016, 1058017, 1058018, 1058019, 1058020, 1058021, 1058022, 1058023,
                    1058024, 1058025, 1058026, 1058027, 1058028, 1058029, 1058030, 1058031, 1058032, 1058033, 1058034,
                    1058035, 1058036, 1058037, 1058038, 1058039, 1058040, 1058041, 1058042, 1058043, 1058044, 1058045,
                    1058046, 1058047, 1058048, 1058049, 1058050, 1058051, 1058052, 1058053, 1058054, 1058055, 1058056,
                    1058057, 1058058, 1058059, 1058060, 1058061, 1058062, 1058063, 1058064, 1058065, 1058066, 1058067,
                    1058068, 1058069, 1058070, 1058071, 1058072, 1058073, 1058074, 1058075, 1058076, 1058077, 1058078,
                    1058079, 1058080, 1058081, 1058082, 1058083, 1058084, 1058085, 1058086, 1058087, 1058088, 1058089,
                    1058090, 1058091, 1058092, 1058093, 1058094, 1058095, 1058096, 1058097 },
            // 상의
            {},
            // Weapon
            { 1705000, 1705001, 1705002, 1705003, 1705004, 1705005, 1705006, 1705007, 1705008, 1705009, 1705010,
                    1705011, 1705012, 1705013, 1705014, 1705015, 1705016, 1705017, 1705018, 1705019, 1705020, 1705021,
                    1705022, 1705023, 1705024, 1705025, 1705026, 1705027, 1705028, 1705029, 1705030, 1705031, 1705032,
                    1705033, 1705034, 1705035, 1705036, 1705037, 1705038, 1705039, 1705040, 1705041, 1705042, 1705043,
                    1705044, 1705045, 1705046, 1705047, 1705048, 1705049, 1705050, 1705051, 1705052, 1705053, 1705054,
                    1705055, 1705056, 1705057, 1705058, 1705059, 1705060, 1705061, 1705062, 1705063, 1705064, 1705065,
                    1705066, 1705067, 1705068, 1705069, 1705070, 1705071, 1705072, 1705073, 1705074, 1705075, 1705076,
                    1705077, 1705078, 1705079, 1705080, 1705081, 1705082, 1705083, 1705084, 1705085, 1705086, 1705087,
                    1705088, 1705089, 1705090, 1705091, 1705092, 1705093, 1705094, 1705095, 1705096, 1705097, 1705098,
                    1705099, 1705100, 1705101, 1705102, 1705103, 1705104, 1705105, 1705106, 1705107, 1705108, 1705109,
                    1705110, 1705111, 1705112, 1705113, 1705114, 1705115, 1705116, 1705117, 1705118, 1705119, 1705120,
                    1705121, 1705122, 1705123, 1705124, 1705125, 1705126, 1705127, 1705128, 1705129, 1705130, 1705131,
                    1705132, 1705133, 1705134, 1705135, 1705136, 1705137, 1705138, 1705139, 1705140, 1705141, 1705142,
                    1705143, 1705144, 1705145, 1705146, 1705147, 1705148, 1705149, 1705150, 1705151, 1705152, 1705153 },
            // Cape
            { 1104000, 1104001, 1104002, 1104003, 1104004, 1104005, 1104006, 1104007, 1104008, 1104009, 1104010,
                    1104011, 1104012, 1104013, 1104014, 1104015, 1104016, 1104017, 1104018, 1104019, 1104020, 1104021,
                    1104022, 1104023, 1104024, 1104025, 1104026, 1104027, 1104028, 1104029, 1104030, 1104031, 1104032,
                    1104033, 1104034, 1104035, 1104036, 1104037, 1104038, 1104039, 1104040, 1104041 },
            // Glove
            {},
            // Shoes
            { 1075000, 1075001, 1075002, 1075003, 1075004, 1075005, 1075006, 1075007, 1075008, 1075009, 1075010,
                    1075011, 1075012, 1075013 },
            // Accessory
            { 1022500, 1022501, 1022502, 1022503, 1022504, 1022505, 1022506, 1012900, 1012901, 1012902, 1012903,
                    1012904, 1012905, 1012906, 1012907, 1012908, 1012909, 1012910, 1012911, 1012912, 1012913, 1012914,
                    1012915, 1012916, 1012917, 1012918, 1012919, 1012920 }
    };
    static String[] label = new String[] {
            "หมวก", "ชุดคลุม", "เสื้อ", "อาวุธ", "ผ้าคลุม", "ถุงมือ", "รองเท้า", "ประดับหน้า"
    };

    public void consume_2439605() {
        if (DBConfig.isGanglim) {
            initNPC(MapleLifeFactory.getNPC(3003225));
        } else {
            initNPC(MapleLifeFactory.getNPC(9062475));
        }
        if (DBConfig.isGanglim) {
            int v0 = self.askMenu(
                    "#b#i2439605# #z2439605##k สามารถเลือกรับไอเทม Special Cody ที่ต้องการได้ครับ#b\r\n\r\n" +
                            "#L0#เลือกตอนนี้เลย#l\r\n" +
                            "#L1#ขอดูรายการ Special Cody รายการที่ได้รับได้หน่อยครับ#k#l\r\n",
                    ScriptMessageFlag.NpcReplacedByNpc);
            switch (v0) {
                case 0: { // Open
                    String v1 = "ต้องการเลือก Special Cody แบบไหนดีครับ?#b\r\n\r\n";
                    v1 += "#L0#Ganglim Maple Special Cody Season 1#l\r\n";
                    int v2 = self.askMenu(v1, ScriptMessageFlag.NpcReplacedByNpc);

                    if (v2 == 0) { // Ganglim
                        String v3 = "กรุณาเลือกประเภท Special Cody ครับ#b\r\n\r\n";
                        v3 += "#L0#หมวก#l\r\n#L1#ชุดคลุม#l\r\n#L2#เสื้อ#l\r\n#L3#อาวุธ#l\r\n#L4#ผ้าคลุม#l\r\n#L5#ถุงมือ#l\r\n#L6#รองเท้า#l\r\n#L7#ประดับหน้า#l";
                        int v4 = self.askMenu(v3, ScriptMessageFlag.NpcReplacedByNpc);
                        String v5 = "เลือก Special Cody ที่ต้องการได้เลยครับ! #e(" + label[v4] + ")#n #b\r\n\r\n";
                        int index = 0;
                        for (int itemID : royalList[v4]) {
                            v5 += "#L" + index++ + "##i" + itemID + "# #z" + itemID + "##l\r\n";
                        }
                        int v6 = self.askMenu(v5, ScriptMessageFlag.NpcReplacedByNpc);
                        if (self.askYesNo("คุณเลือก Special Cody #b#i" + royalList[v4][v6] + "# #z" + royalList[v4][v6]
                                + "##k ต้องการรับชิ้นนี้ใช่ไหมครับ?\r\n\r\n#bหากกด #eใช่#n จะได้รับไอเทมทันที") > 0) {
                            if (target.exchange(2439605, -1, royalList[v4][v6], 1) == 1) {
                                self.say(
                                        "ได้รับ Special Cody เรียบร้อยแล้วครับ\r\nลองเช็คใน #bกระเป๋าแต่งตัว#k ดูนะครับ~!");
                            } else {
                                self.say("กรุณาทำช่องว่างใน #bกระเป๋าแต่งตัว#k แล้วลองใหม่นะครับ!");
                            }
                        } else {
                            self.say("ลองคิดดูอีกทีแล้วค่อยมาใหม่นะครับ!", ScriptMessageFlag.NpcReplacedByNpc);
                        }
                    }
                }
                    break;
                case 1: {// List
                    int v1 = self.askMenu(
                            "กรุณาเลือกรายการ Special Cody ที่ต้องการดูครับ#b\r\n\r\n#L0#Ganglim Maple Special Cody Season 1#l",
                            ScriptMessageFlag.NpcReplacedByNpc);
                    if (v1 == 0) { // Ganglim Special Cody List
                        String v2 = "#e<Ganglim Maple Special Cody Season 1>#n\r\nกรุณาเลือกประเภท Special Cody ครับ\r\n\r\n#b";
                        v2 += "#L0#หมวก#l\r\n#L1#ชุดคลุม#l\r\n#L2#เสื้อ#l\r\n#L3#อาวุธ#l\r\n#L4#ผ้าคลุม#l\r\n#L5#ถุงมือ#l\r\n#L6#รองเท้า#l\r\n#L7#ประดับหน้า#l";
                        int v3 = self.askMenu(v2, ScriptMessageFlag.NpcReplacedByNpc);

                        String v4 = "#e<Ganglim Maple Special Cody Season 1 (" + label[v3] + ")>#n#b\r\n\r\n";
                        for (int itemID : royalList[v3]) {
                            v4 += "#i" + itemID + "# #z" + itemID + "#\r\n";
                        }
                        self.say(v4, ScriptMessageFlag.NpcReplacedByNpc);
                    }
                }
                    break;
            }
        } else {
            int v0 = self.askMenu(
                    "#b#i2439605# #z2439605##k สามารถเลือกรับไอเทม Special Cody ที่ต้องการได้ครับ#b\r\n\r\n" +
                            "#L0#เลือกตอนนี้เลย#l\r\n" +
                            "#L1#ขอดูรายการ Special Cody รายการที่ได้รับได้หน่อยครับ#k#l\r\n",
                    ScriptMessageFlag.NpcReplacedByNpc);
            switch (v0) {
                case 0: { // Open
                    String v1 = "ต้องการเลือก Special Cody แบบไหนดีครับ?#b\r\n\r\n";
                    v1 += "#L0#Extreme [E] Special Cody#l\r\n";
                    v1 += "#L1#Jin [J] Special Cody#l";
                    int v2 = self.askMenu(v1, ScriptMessageFlag.NpcReplacedByNpc);
                    if (v2 == 0) { // Extreme
                        String v3 = "เลือก Special Cody ที่ต้องการได้เลยครับ!#b\r\n\r\n";
                        for (int i = 0; i < extremeList.length; ++i) {
                            int itemID = extremeList[i];
                            v3 += "#L" + i + "##i" + itemID + "# #z" + itemID + "##l\r\n";
                        }
                        int v4 = self.askMenu(v3, ScriptMessageFlag.NpcReplacedByNpc);
                        if (self.askYesNo("คุณเลือก Special Cody #b#i" + extremeList[v4] + "# #z" + extremeList[v4]
                                + "##k ต้องการรับชิ้นนี้ใช่ไหมครับ?\r\n\r\n#bหากกด #eใช่#n จะได้รับไอเทมทันที") > 0) {
                            if (target.exchange(2439605, -1, extremeList[v4], 1) == 1) {
                                self.say(
                                        "ได้รับ Special Cody เรียบร้อยแล้วครับ\r\nลองเช็คใน #bกระเป๋าแต่งตัว#k ดูนะครับ~!");
                            } else {
                                self.say("กรุณาทำช่องว่างใน #bกระเป๋าแต่งตัว#k แล้วลองใหม่นะครับ!");
                            }
                        } else {
                            self.say("ลองคิดดูอีกทีแล้วค่อยมาใหม่นะครับ!", ScriptMessageFlag.NpcReplacedByNpc);
                        }
                    } else if (v2 == 1) { // Jin
                        String v3 = "กรุณาเลือกประเภท Special Cody ครับ#b\r\n\r\n";
                        v3 += "#L0#หมวก#l\r\n#L1#ชุดคลุม#l\r\n#L2#เสื้อ#l\r\n#L3#อาวุธ#l\r\n#L4#ผ้าคลุม#l\r\n#L5#ถุงมือ#l\r\n#L6#รองเท้า#l\r\n#L7#ประดับหน้า#l";
                        int v4 = self.askMenu(v3, ScriptMessageFlag.NpcReplacedByNpc);
                        String v5 = "เลือก Special Cody ที่ต้องการได้เลยครับ! #e(" + label[v4] + ")#n #b\r\n\r\n";
                        int index = 0;
                        for (int itemID : jinList[v4]) {
                            v5 += "#L" + index++ + "##i" + itemID + "# #z" + itemID + "##l\r\n";
                        }
                        int v6 = self.askMenu(v5, ScriptMessageFlag.NpcReplacedByNpc);
                        if (self.askYesNo("คุณเลือก Special Cody #b#i" + jinList[v4][v6] + "# #z" + jinList[v4][v6]
                                + "##k ต้องการรับชิ้นนี้ใช่ไหมครับ?\r\n\r\n#bหากกด #eใช่#n จะได้รับไอเทมทันที") > 0) {
                            if (target.exchange(2439605, -1, jinList[v4][v6], 1) == 1) {
                                self.say(
                                        "ได้รับ Special Cody เรียบร้อยแล้วครับ\r\nลองเช็คใน #bกระเป๋าแต่งตัว#k ดูนะครับ~!");
                            } else {
                                self.say("กรุณาทำช่องว่างใน #bกระเป๋าแต่งตัว#k แล้วลองใหม่นะครับ!");
                            }
                        } else {
                            self.say("ลองคิดดูอีกทีแล้วค่อยมาใหม่นะครับ!", ScriptMessageFlag.NpcReplacedByNpc);
                        }
                    }
                }
                    break;
                case 1: {// List
                    int v1 = self.askMenu(
                            "ต้องการดูรายการ Special Cody อันไหนครับ?#b\r\n\r\n#L0#Extreme [E] Special Cody#l\r\n#L1#Jin [J] Special Cody#l",
                            ScriptMessageFlag.NpcReplacedByNpc);
                    if (v1 == 0) { // Extreme Special Cody List
                        String v2 = "#e[Extreme [E] Special Cody List]#n\r\n\r\n#b";
                        for (int itemID : extremeList) {
                            v2 += "#i" + itemID + "# #z" + itemID + "#\r\n";
                        }
                        self.say(v2, ScriptMessageFlag.NpcReplacedByNpc);
                    } else if (v1 == 1) { // Jin Special Cody List
                        String v2 = "#e[Jin [J] Special Cody List]#n\r\nกรุณาเลือกประเภท Special Cody ครับ\r\n\r\n#b";
                        v2 += "#L0#หมวก#l\r\n#L1#ชุดคลุม#l\r\n#L2#เสื้อ#l\r\n#L3#อาวุธ#l\r\n#L4#ผ้าคลุม#l\r\n#L5#ถุงมือ#l\r\n#L6#รองเท้า#l\r\n#L7#ประดับหน้า#l";
                        int v3 = self.askMenu(v2, ScriptMessageFlag.NpcReplacedByNpc);

                        String v4 = "#e[Jin [J] Special Cody List (" + label[v3] + ")]#n#b\r\n\r\n";
                        for (int itemID : jinList[v3]) {
                            v4 += "#i" + itemID + "# #z" + itemID + "#\r\n";
                        }
                        self.say(v4, ScriptMessageFlag.NpcReplacedByNpc);
                    }
                }
                    break;
            }
        }
    }

    // Jin: True Special Cody Box (R)
    public void consume_2439604() {
        if (DBConfig.isGanglim) {
            initNPC(MapleLifeFactory.getNPC(3003225));
        } else {
            initNPC(MapleLifeFactory.getNPC(9062475));
        }

        if (DBConfig.isGanglim) {
            int v0 = self.askMenu("#b#i2439604# #z2439604##k สามารถสุ่มรับไอเทม Special Cody 1 ชิ้นได้ครับ#b\r\n\r\n" +
                    "#L0#เปิดตอนนี้เลย#l\r\n" +
                    "#L1#ขอดูรายการ Special Cody รายการที่ได้รับได้หน่อยครับ#k#l\r\n",
                    ScriptMessageFlag.NpcReplacedByNpc);
            switch (v0) {
                case 0: { // 개봉
                    if (self.askYesNo("ต้องการเปิดตอนนี้เลยใช่ไหมครับ?\r\n\r\n#bหากกด #eใช่#n จะได้รับไอเทมทันที",
                            ScriptMessageFlag.NpcReplacedByNpc) == 1) {
                        List<Integer> list = new ArrayList<>();
                        for (int i = 0; i < jinList.length; ++i) {
                            Arrays.stream(royalList[i]).forEach(list::add);
                        }
                        Collections.shuffle(list);
                        Integer pick = list.stream().findAny().orElse(null);
                        if (pick == null) {
                            self.say("เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุ กรุณาลองใหม่อีกครั้งในภายหลังครับ",
                                    ScriptMessageFlag.NpcReplacedByNpc);
                            return;
                        }
                        if (target.exchange(2439604, -1, pick, 1) == 1) {
                            self.say("#b#i2439604# #z2439604##k ได้รับไอเทมดังต่อไปนี้ครับ!\r\n\r\n#b#i" + pick
                                    + "# #z" + pick
                                    + "# #k1 ชิ้น ได้รับเรียบร้อยแล้ว!", ScriptMessageFlag.NpcReplacedByNpc);
                        } else {

                        }
                    } else {
                        self.say("ลองคิดดูอีกทีแล้วค่อยมาใหม่นะครับ!", ScriptMessageFlag.NpcReplacedByNpc);
                    }
                }
                    break;
                case 1: {// List
                    int v1 = self.askMenu(
                            "กรุณาเลือกรายการ Special Cody ที่ต้องการดูครับ#b\r\n\r\n#L0#Ganglim Maple Special Cody Season 1#l",
                            ScriptMessageFlag.NpcReplacedByNpc);
                    if (v1 == 0) { // Ganglim Special Cody List
                        String v2 = "#e<Ganglim Maple Special Cody Season 1 List>#n\r\nกรุณาเลือกประเภท Special Cody ครับ\r\n\r\n#b";
                        v2 += "#L0#หมวก#l\r\n#L1#ชุดคลุม#l\r\n#L2#เสื้อ#l\r\n#L3#อาวุธ#l\r\n#L4#ผ้าคลุม#l\r\n#L5#ถุงมือ#l\r\n#L6#รองเท้า#l\r\n#L7#ประดับหน้า#l";
                        int v3 = self.askMenu(v2, ScriptMessageFlag.NpcReplacedByNpc);

                        String v4 = "#e<Ganglim Maple Special Cody Season 1 List (" + label[v3] + ")>#n#b\r\n\r\n";
                        for (int itemID : royalList[v3]) {
                            v4 += "#i" + itemID + "# #z" + itemID + "#\r\n";
                        }
                        self.say(v4, ScriptMessageFlag.NpcReplacedByNpc);
                    }
                }
                    break;
            }
        } else {
            int v0 = self.askMenu("#b#i2439604# #z2439604##k สามารถสุ่มรับไอเทม Special Cody 1 ชิ้นได้ครับ#b\r\n\r\n" +
                    "#L0#เปิดตอนนี้เลย#l\r\n" +
                    "#L1#ขอดูรายการ Special Cody ที่ได้รับได้หน่อยครับ#k#l\r\n", ScriptMessageFlag.NpcReplacedByNpc);
            switch (v0) {
                case 0: { // 개봉
                    if (self.askYesNo("ต้องการเปิดตอนนี้เลยใช่ไหมครับ?\r\n\r\n#bหากกด #eใช่#n จะได้รับไอเทมทันที",
                            ScriptMessageFlag.NpcReplacedByNpc) == 1) {
                        List<Integer> list = new ArrayList<>();
                        Arrays.stream(extremeList).forEach(list::add);
                        for (int i = 0; i < jinList.length; ++i) {
                            Arrays.stream(jinList[i]).forEach(list::add);
                        }
                        Collections.shuffle(list);
                        Integer pick = list.stream().findAny().orElse(null);
                        if (pick == null) {
                            self.say("เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุ กรุณาลองใหม่อีกครั้งในภายหลังครับ",
                                    ScriptMessageFlag.NpcReplacedByNpc);
                            return;
                        }
                        if (target.exchange(2439604, -1, pick, 1) == 1) {
                            self.say("#b#i2439604# #z2439604##k ได้รับไอเทมดังต่อไปนี้ครับ!\r\n\r\n#b#i" + pick
                                    + "# #z" + pick
                                    + "# #k1 ชิ้น ได้รับเรียบร้อยแล้ว!", ScriptMessageFlag.NpcReplacedByNpc);
                        } else {

                        }
                    } else {
                        self.say("ลองคิดดูอีกทีแล้วค่อยมาใหม่นะครับ!", ScriptMessageFlag.NpcReplacedByNpc);
                    }
                }
                    break;
                case 1: {// List
                    int v1 = self.askMenu(
                            "กรุณาเลือกรายการ Special Cody ที่ต้องการดูครับ#b\r\n\r\n#L0#Extreme [E] Special Cody#l\r\n#L1#Jin [J] Special Cody#l",
                            ScriptMessageFlag.NpcReplacedByNpc);
                    if (v1 == 0) { // Extreme Special Cody List
                        String v2 = "#e[Extreme [E] Special Cody List]#n\r\n\r\n#b";
                        for (int itemID : extremeList) {
                            v2 += "#i" + itemID + "# #z" + itemID + "#\r\n";
                        }
                        self.say(v2, ScriptMessageFlag.NpcReplacedByNpc);
                    } else if (v1 == 1) { // Jin Special Cody List
                        String v2 = "#e[Jin [J] Special Cody List]#n\r\nกรุณาเลือกประเภท Special Cody ครับ\r\n\r\n#b";
                        v2 += "#L0#หมวก#l\r\n#L1#ชุดคลุม#l\r\n#L2#เสื้อ#l\r\n#L3#อาวุธ#l\r\n#L4#ผ้าคลุม#l\r\n#L5#ถุงมือ#l\r\n#L6#รองเท้า#l\r\n#L7#ประดับหน้า#l";
                        int v3 = self.askMenu(v2, ScriptMessageFlag.NpcReplacedByNpc);

                        String v4 = "#e[Jin [J] Special Cody List (" + label[v3] + ")]#n#b\r\n\r\n";
                        for (int itemID : jinList[v3]) {
                            v4 += "#i" + itemID + "# #z" + itemID + "#\r\n";
                        }
                        self.say(v4, ScriptMessageFlag.NpcReplacedByNpc);
                    }
                }
                    break;
            }
        }
    }

    // Jin: True Growth Support Box 1
    public void consume_2439580() {
        initNPC(MapleLifeFactory.getNPC(9062475));

        String v0 = "หากเปิดกล่องจะได้รับไอเทมดังต่อไปนี้ ต้องการเปิดไหมครับ?\r\n\r\n";
        if (DBConfig.isGanglim) {
            v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
            v0 += "#b#i5002239# #z5002239# (ใช้งาน 30 วัน)#k 1 ชิ้น\r\n";
            v0 += "#b#i2630437# #z2630437# #k 100 ชิ้น\r\n";
            // 2630437
        } else {
            v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
            v0 += "#b#i5000930# #z5000930# (ใช้งาน 5 วัน)#k 1 ชิ้น\r\n";
        }
        int v1 = self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByUser);
        if (v1 > 0) {
            // Originally exchange is possible, but logic needed to check items
            if (DBConfig.isGanglim ? (getPlayer().getInventory(MapleInventoryType.CASH).getNumFreeSlot() < 1 ||
                    getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 1)
                    : getPlayer().getInventory(MapleInventoryType.CASH).getNumFreeSlot() < 1) {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Cash#k แล้วลองใหม่นะครับ", ScriptMessageFlag.NpcReplacedByUser);
            } else {
                if (target.exchange(2439580, -1) == 1) {
                    if (DBConfig.isGanglim) {
                        exchangePetPeriod(5002239, 30);
                        target.exchange(2630437, 100);
                    } else {
                        exchangePetPeriod(5000930, 5);
                    }
                    self.say("เปิดกล่องได้รับไอเทมเรียบร้อยแล้วครับ", ScriptMessageFlag.NpcReplacedByUser);
                }
            }
        }
    }

    // Jin: True Growth Support Box 2
    public void consume_2439581() {
        initNPC(MapleLifeFactory.getNPC(9062475));

        String v0 = "หากเปิดกล่องจะได้รับไอเทมดังต่อไปนี้ ต้องการเปิดไหมครับ?\r\n\r\n";
        if (DBConfig.isGanglim) {
            v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
            v0 += "#b#i1112401# #z1112401# (ใช้งาน 7 วัน)#k 1 ชิ้น\r\n";
            v0 += "  - All Stat +70, Atk/M.Atk +40\r\n";
        } else {
            v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
            v0 += "#b#i1112405# #z1112405# (ใช้งาน 7 วัน)#k 1 ชิ้น\r\n";
            v0 += "  - All Stat +50, Atk/M.Atk +25\r\n";
            v0 += "#b#i1112431# #z1112431# (ใช้งาน 7 วัน)#k 1 ชิ้น\r\n";
            v0 += "  - All Stat +50, Atk/M.Atk +25\r\n";
        }
        int v1 = self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByUser);
        if (v1 > 0) {
            // Originally exchange works, but doing this due to time-limited items.
            if (DBConfig.isGanglim ? getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 1
                    : getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 2) {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Equip#k แล้วลองใหม่นะครับ", ScriptMessageFlag.NpcReplacedByUser);
            } else {
                if (target.exchange(2439581, -1) == 1) {
                    if (DBConfig.isGanglim) {
                        exchangeSupportEquipPeriod(1112401, 70, 40, 7, 50);
                    } else {
                        exchangeSupportEquipPeriod(1112405, 50, 25, 7);
                        exchangeSupportEquipPeriod(1112431, 50, 25, 7);
                    }
                    self.say("เปิดกล่องได้รับไอเทมเรียบร้อยแล้วครับ", ScriptMessageFlag.NpcReplacedByUser);
                }
            }
        }
    }

    // Jin: True Growth Support Box 3
    public void consume_2439582() {
        initNPC(MapleLifeFactory.getNPC(9062475));

        int rewards[][] = new int[][] {
                // ItemID, Quantity, Period, AllStat, Attack
                { 2433509, 1, 0, 0, 0 }, // Jin: True Black Equipment Box
                { 1032259, 1, 7, 50, 25 }, // Halloween Earrings
                { 2432643, 5, 0, 0, 0 }, // Mastery Book 20
                { 2434589, 6, 0, 0, 0 } // Piece of Black Guardian
        };
        if (DBConfig.isGanglim) {
            if (getPlayer().getGender() == 0) {
                rewards = new int[][] {
                        { 1122074, 1, 7, 100, 50 },
                        { 1005781, 1, 7, 30, 10 }, // Chu Chu Set
                        { 1050583, 1, 7, 30, 10 }, // Chu Chu Set
                        { 1103332, 1, 7, 30, 10 }, // Chu Chu Set
                        { 1073534, 1, 7, 30, 10 }, // Chu Chu Set
                        { 1703084, 1, 7, 30, 10 }, // Chu Chu Set
                };
            } else {
                rewards = new int[][] {
                        { 1122074, 1, 7, 100, 50 },
                        { 1005781, 1, 7, 30, 10 }, // Chu Chu Set
                        { 1051656, 1, 7, 30, 10 }, // Chu Chu Set
                        { 1103332, 1, 7, 30, 10 }, // Chu Chu Set
                        { 1073534, 1, 7, 30, 10 }, // Chu Chu Set
                        { 1703084, 1, 7, 30, 10 }, // Chu Chu Set
                };
            }
        }

        String v0 = "หากเปิดกล่องจะได้รับไอเทมดังต่อไปนี้ ต้องการเปิดไหมครับ?\r\n\r\n";
        v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
        for (int[] reward : rewards) {
            v0 += "#b#i" + reward[0] + "# #z" + reward[0] + "#";
            if (reward[2] > 0) {
                v0 += " (ใช้งาน " + reward[2] + " วัน) ";
            }
            v0 += "#k " + reward[1] + " ชิ้น";
            if (reward[3] > 0 || reward[4] > 0) {
                v0 += "\r\n  - All Stat +" + reward[3] + ", Atk/M.Atk +" + reward[4] + "\r\n";
            }
            v0 += "\r\n";
        }
        int v1 = self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByUser);
        if (v1 > 0) {
            // Originally exchange is possible, but logic needed to check items
            if (DBConfig.isGanglim ? (getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 5 ||
                    getPlayer().getInventory(MapleInventoryType.CASH_EQUIP).getNumFreeSlot() < 5)
                    : (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 5 ||
                            getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 1)) {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Equip#k และ #bกระเป๋า Use#k แล้วลองใหม่นะครับ",
                        ScriptMessageFlag.NpcReplacedByUser);
            } else {
                if (target.exchange(2439582, -1) == 1) {
                    for (int[] reward : rewards) {
                        if (reward[3] > 0 || reward[4] > 0) {
                            if (DBConfig.isGanglim) {
                                exchangeSupportEquipPeriod(reward[0], reward[3], reward[4], reward[2], 70);
                            } else {
                                exchangeSupportEquipPeriod(reward[0], reward[3], reward[4], reward[2]);
                            }
                        } else {
                            target.exchange(reward[0], reward[1]);
                        }
                    }
                    self.say("เปิดกล่องได้รับไอเทมเรียบร้อยแล้วครับ", ScriptMessageFlag.NpcReplacedByUser);
                }
            }
        }
    }

    // Jin: True Growth Support Box 4
    public void consume_2439583() {
        initNPC(MapleLifeFactory.getNPC(9062475));

        int rewards[][] = new int[][] {
                // ItemID, Quantity, Period, AllStat, Attack
                { 1122330, 1, 7, 70, 30 }, // Halloween Pendant
                { 1022252, 1, 7, 70, 30 }, // Halloween Earrings
                { 2436605, 1, 0, 0, 0 }, // Master Craftsman's Cube Lucky Bag
        };

        if (DBConfig.isGanglim) {
            rewards = new int[][] {
                    { 1022251, 1, 7, 100, 50 }
            };
        }

        String v0 = "หากเปิดกล่องจะได้รับไอเทมดังต่อไปนี้ ต้องการเปิดไหมครับ?\r\n\r\n";
        v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
        for (int[] reward : rewards) {
            v0 += "#b#i" + reward[0] + "# #z" + reward[0] + "#";
            if (reward[2] > 0) {
                v0 += " (ใช้งาน " + reward[2] + " วัน) ";
            }
            v0 += "#k " + reward[1] + " ชิ้น";
            if (reward[3] > 0 || reward[4] > 0) {
                v0 += "\r\n  - All Stat +" + reward[3] + ", Atk/M.Atk +" + reward[4] + "\r\n";
            }
            v0 += "\r\n";
        }
        int v1 = self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByUser);
        if (v1 > 0) {
            // Originally exchange works, but doing this due to time-limited items.
            if (DBConfig.isGanglim ? getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 1
                    : (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 1 ||
                            getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 2)) {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Equip#k และ #bกระเป๋า Use#k แล้วลองใหม่นะครับ",
                        ScriptMessageFlag.NpcReplacedByUser);
            } else {
                if (target.exchange(2439583, -1) == 1) {
                    for (int[] reward : rewards) {
                        if (reward[3] > 0 || reward[4] > 0) {
                            if (DBConfig.isGanglim) {
                                exchangeSupportEquipPeriod(reward[0], reward[3], reward[4], reward[2], 70);
                            } else {
                                exchangeSupportEquipPeriod(reward[0], reward[3], reward[4], reward[2]);
                            }
                        } else {
                            target.exchange(reward[0], reward[1]);
                        }
                    }
                    self.say("เปิดกล่องได้รับไอเทมเรียบร้อยแล้วครับ", ScriptMessageFlag.NpcReplacedByUser);
                }
            }
        }
    }

    // Jin: True Growth Support Box 5
    public void consume_2439584() {
        initNPC(MapleLifeFactory.getNPC(9062475));

        int rewards[][] = new int[][] {
                // ItemID, Quantity, Period, AllStat, Attack
                { 1152052, 1, 7, 100, 50 }, // Absolab Shoulder
                { 1712001, 10, 0, 0, 0 }, // Arcane Symbol: Vanishing Journey 10
                { 2436078, 30, 0, 0, 0 }, // Core Gemstone 30
        };

        if (DBConfig.isGanglim) {
            rewards = new int[][] {
                    // Reboot Ring
            };
        }

        String v0 = "หากเปิดกล่องจะได้รับไอเทมดังต่อไปนี้ ต้องการเปิดไหมครับ?\r\n\r\n";
        v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
        v0 += "#b#i1113227# #z1113227# (อายุการใช้งาน 30 วัน) #k 1 ชิ้น\r\n";
        v0 += "  - อัตราดรอป Meso 40%, อัตราดรอปไอเท็ม 40%\r\n";
        for (int[] reward : rewards) {
            v0 += "#b#i" + reward[0] + "# #z" + reward[0] + "#";
            if (reward[2] > 0) {
                v0 += " (ใช้งาน " + reward[2] + " วัน) ";
            }
            v0 += "#k " + reward[1] + " ชิ้น";
            if (reward[3] > 0 || reward[4] > 0) {
                v0 += "\r\n  - All Stat +" + reward[3] + ", Atk/M.Atk +" + reward[4] + "\r\n";
            }
            v0 += "\r\n";
        }
        int v1 = self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByUser);
        if (v1 > 0) {
            // Originally exchange works, but doing this due to time-limited items.
            if (DBConfig.isGanglim ? getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 1
                    : getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 1 ||
                            getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 12) {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Equip#k และ #bกระเป๋า Use#k แล้วลองใหม่นะครับ",
                        ScriptMessageFlag.NpcReplacedByUser);
            } else {
                if (target.exchange(2439584, -1) == 1) {
                    for (int[] reward : rewards) {
                        if (reward[3] > 0 || reward[4] > 0) {
                            exchangeSupportEquipPeriod(reward[0], reward[3], reward[4], reward[2]);
                        } else {
                            target.exchange(reward[0], reward[1]);
                        }
                    }
                    if (DBConfig.isGanglim) {
                        Equip item = (Equip) MapleItemInformationProvider.getInstance().getEquipById(1113227);
                        item.setState((byte) 20);
                        item.setPotential1(40650);
                        item.setPotential2(40650);
                        item.setPotential4(40656);
                        item.setPotential5(40656);
                        item.setWatk((short) 100);
                        item.setMatk((short) 100);
                        item.setDownLevel((byte) 70);
                        item.setExpiration(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 20));
                        MapleInventoryManipulator.addFromDrop(getClient(), item, false);
                    } else {
                        if (getPlayer().getOneInfoQuestInteger(1234566, "reboot_ring") <= 0) {
                            Equip item = (Equip) MapleItemInformationProvider.getInstance().getEquipById(1113227);
                            item.setState((byte) 20);
                            item.setPotential1(40650);
                            item.setPotential2(40650);
                            item.setPotential4(40656);
                            item.setPotential5(40656);
                            item.setExpiration(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 20));
                            MapleInventoryManipulator.addFromDrop(getClient(), item, false);
                            getPlayer().updateOneInfo(1234566, "reboot_ring", "1");
                        }
                    }

                    self.say("เปิดกล่องได้รับไอเทมเรียบร้อยแล้วครับ", ScriptMessageFlag.NpcReplacedByUser);
                }
            }
        }
    }

    // Jin: True Growth Support Box 6
    public void consume_2439585() {
        initNPC(MapleLifeFactory.getNPC(9062475));

        int rewards[][] = new int[][] {
                // ItemID, Quantity, Period, AllStat, Attack
                { 2439292, 5, 0, 0, 0 }, // Labyrinth Arcane Symbol Box
                { 2436605, 3, 0, 0, 0 }, // Master Craftsman's Cube Lucky Bag
                { 1114305, 1, 0, 100, 50 }, // Chaos Ring
        };

        if (DBConfig.isGanglim) {
            rewards = new int[][] {
                    { 1152118, 1, 7, 100, 50 } // Stella Shoulder
            };
        }

        String v0 = "หากเปิดกล่องจะได้รับไอเทมดังต่อไปนี้ ต้องการเปิดไหมครับ?\r\n\r\n";
        v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
        for (int[] reward : rewards) {
            v0 += "#b#i" + reward[0] + "# #z" + reward[0] + "#";
            if (reward[2] > 0) {
                v0 += " (ใช้งาน " + reward[2] + " วัน) ";
            }
            v0 += "#k " + reward[1] + " ชิ้น";
            if (reward[3] > 0 || reward[4] > 0) {
                v0 += "\r\n  - All Stat +" + reward[3] + ", Atk/M.Atk +" + reward[4] + "\r\n";
            }
            v0 += "\r\n";
        }
        int v1 = self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByUser);
        if (v1 > 0) {
            // Originally exchange works, but doing this due to time-limited items.
            if (DBConfig.isGanglim ? getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 1
                    : getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 7 ||
                            getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 1) {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Equip#k และ #bกระเป๋า Use#k แล้วลองใหม่นะครับ",
                        ScriptMessageFlag.NpcReplacedByUser);
            } else {
                if (target.exchange(2439585, -1) == 1) {
                    for (int[] reward : rewards) {
                        if (reward[3] > 0 || reward[4] > 0) {
                            exchangeSupportEquipPeriod(reward[0], reward[3], reward[4], reward[2]);
                        } else {
                            target.exchange(reward[0], reward[1]);
                        }
                    }
                    if (!DBConfig.isGanglim) {
                        getPlayer().gainMeso(50000000, true);
                    }
                    self.say("เปิดกล่องได้รับไอเทมเรียบร้อยแล้วครับ", ScriptMessageFlag.NpcReplacedByUser);
                }
            }
        }
    }

    // Jin: True Growth Support Box 7
    public void consume_2439586() {
        initNPC(MapleLifeFactory.getNPC(9062475));

        int rewards[][] = new int[][] {
                // ItemID, Quantity, Period, AllStat, Attack
                { 2436605, 1, 0, 0, 0 }, // Master Craftsman's Cube Lucky Bag
                { 1162013, 1, 7, 50, 30 }, // ES Square
        };

        if (DBConfig.isGanglim) {
            rewards = new int[][] {
                    { 1114305, 1, 7, 100, 50 } // Chaos Ring
            };
        }

        String v0 = "หากเปิดกล่องจะได้รับไอเทมดังต่อไปนี้ ต้องการเปิดไหมครับ?\r\n\r\n";
        v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
        for (int[] reward : rewards) {
            v0 += "#b#i" + reward[0] + "# #z" + reward[0] + "#";
            if (reward[2] > 0) {
                v0 += " (ใช้งาน " + reward[2] + " วัน) ";
            }
            v0 += "#k " + reward[1] + " ชิ้น";
            if (reward[3] > 0 || reward[4] > 0) {
                v0 += "\r\n  - All Stat +" + reward[3] + ", Atk/M.Atk +" + reward[4] + "\r\n";
            }
            v0 += "\r\n";
        }
        v0 += "#b  - ปลดล็อคช่อง Pocket";
        int v1 = self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByUser);
        if (v1 > 0) {
            // Originally exchange works, but doing this due to time-limited items.
            if (DBConfig.isGanglim ? getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 1
                    : getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 1 ||
                            getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 1) {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Equip#k และ #bกระเป๋า Use#k แล้วลองใหม่นะครับ",
                        ScriptMessageFlag.NpcReplacedByUser);
            } else {
                if (target.exchange(2439586, -1) == 1) {
                    for (int[] reward : rewards) {
                        if (reward[3] > 0 || reward[4] > 0) {
                            exchangeSupportEquipPeriod(reward[0], reward[3], reward[4], reward[2]);
                        } else {
                            target.exchange(reward[0], reward[1]);
                        }
                    }
                    if (!DBConfig.isGanglim) {
                        getPlayer().forceCompleteQuest(6500); // Pocket Unlock
                        getPlayer().gainMeso(50000000, true);
                        getPlayer().gainExp(15000000000L, true, true, true);
                    }
                    self.say("เปิดกล่องได้รับไอเทมเรียบร้อยแล้วครับ", ScriptMessageFlag.NpcReplacedByUser);
                }
            }
        }
    }

    // Jin: True Growth Support Box 8
    public void consume_2439587() {
        initNPC(MapleLifeFactory.getNPC(9062475));

        int rewards[][] = new int[][] {
                // ItemID, Quantity, Period, AllStat, Attack
                { 5062010, 10, 0, 0, 0 }, // Black Cube
                { 4001832, 5000, 0, 0, 0 }, // Spell Trace
                { 1032227, 1, 0, 40, 25 }, // Ifia's Earrings
        };

        if (DBConfig.isGanglim) {
            rewards = new int[][] {
                    { 1132296, 1, 0, 100, 30 } // Enraged Zakum's Belt
            };
        }

        String v0 = "หากเปิดกล่องจะได้รับไอเทมดังต่อไปนี้ ต้องการเปิดไหมครับ?\r\n\r\n";
        v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
        for (int[] reward : rewards) {
            v0 += "#b#i" + reward[0] + "# #z" + reward[0] + "#";
            if (reward[2] > 0) {
                v0 += " (ใช้งาน " + reward[2] + " วัน) ";
            }
            v0 += "#k " + reward[1] + " ชิ้น";
            if (reward[3] > 0 || reward[4] > 0) {
                v0 += "\r\n  - All Stat +" + reward[3] + ", Atk/M.Atk +" + reward[4] + "\r\n";
            }
            v0 += "\r\n";
        }
        int v1 = self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByUser);
        if (v1 > 0) {
            // Originally exchange works, but doing this due to time-limited items.
            if (DBConfig.isGanglim ? getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 1
                    : (getPlayer().getInventory(MapleInventoryType.ETC).getNumFreeSlot() < 1 ||
                            getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 1 ||
                            getPlayer().getInventory(MapleInventoryType.CASH).getNumFreeSlot() < 1)) {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Equip#k, #bกระเป๋า Use#k และ #bกระเป๋า Cash#k แล้วลองใหม่นะครับ",
                        ScriptMessageFlag.NpcReplacedByUser);
            } else {
                if (target.exchange(2439587, -1) == 1) {
                    for (int[] reward : rewards) {
                        if (reward[3] > 0 || reward[4] > 0) {
                            exchangeSupportEquipPeriod(reward[0], reward[3], reward[4], reward[2]);
                        } else {
                            target.exchange(reward[0], reward[1]);
                        }
                    }
                    if (!DBConfig.isGanglim) {
                        getPlayer().gainMeso(50000000, true);
                        getPlayer().gainExp(20000000000L, true, true, true);
                    }
                    self.say("เปิดกล่องได้รับไอเทมเรียบร้อยแล้วครับ", ScriptMessageFlag.NpcReplacedByUser);
                }
            }
        }
    }

    // Jin: True Growth Support Box 9
    public void consume_2439588() {
        initNPC(MapleLifeFactory.getNPC(9062475));
        int rewards[][] = new int[][] {
                // ItemID, Quantity, Period, AllStat, Attack
                { 1162025, 1, 0, 30, 20 }, // Pink Holy Cup
                { 2436605, 3, 0, 0, 0 }, // Master Craftsman's Cube Lucky Bag
        };
        if (DBConfig.isGanglim) {
            rewards = new int[][] {
                    { 1113282, 1, 0, 100, 30 }, // Noble Ifia's Ring
                    { 1182200, 1, 0, 30, 20 }, // Seven Days Badge
                    { 1162025, 1, 0, 50, 30 }, // Pink Holy Cup
            };
        }

        String v0 = "หากเปิดกล่องจะได้รับไอเทมดังต่อไปนี้ ต้องการเปิดไหมครับ?\r\n\r\n";
        v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
        for (int[] reward : rewards) {
            v0 += "#b#i" + reward[0] + "# #z" + reward[0] + "#";
            if (reward[2] > 0) {
                v0 += " (ใช้งาน " + reward[2] + " วัน) ";
            }
            v0 += "#k " + reward[1] + " ชิ้น";
            if (reward[3] > 0 || reward[4] > 0) {
                v0 += "\r\n  - All Stat +" + reward[3] + ", Atk/M.Atk +" + reward[4] + "\r\n";
            }
            v0 += "\r\n";
        }
        int v1 = self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByUser);
        if (v1 > 0) {
            // Originally exchange works, but doing this due to time-limited items.
            if (DBConfig.isGanglim ? (getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 3)
                    : (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 1 ||
                            getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 1)) {
                if (DBConfig.isGanglim) {
                    self.say("กรุณาทำช่องว่างใน #bกระเป๋า Equip#k และ #bกระเป๋า Cash Equip#k แล้วลองใหม่นะครับ",
                            ScriptMessageFlag.NpcReplacedByUser);
                } else {
                    self.say("กรุณาทำช่องว่างใน #bกระเป๋า Equip#k และ #bกระเป๋า Use#k แล้วลองใหม่นะครับ",
                            ScriptMessageFlag.NpcReplacedByUser);
                }
            } else {
                if (target.exchange(2439588, -1) == 1) {
                    for (int[] reward : rewards) {
                        if (reward[3] > 0 || reward[4] > 0) {
                            exchangeSupportEquipPeriod(reward[0], reward[3], reward[4], reward[2]);
                        } else {
                            target.exchange(reward[0], reward[1]);
                        }
                    }
                    if (DBConfig.isGanglim) {
                        getPlayer().forceCompleteQuest(6500); // Pocket Unlock
                    } else {
                        getPlayer().gainMeso(50000000, true);
                        getPlayer().gainExp(50000000000L, true, true, true);
                    }
                    self.say("เปิดกล่องได้รับไอเทมเรียบร้อยแล้วครับ", ScriptMessageFlag.NpcReplacedByUser);
                }
            }
        }
    }

    // Jin: True Growth Support Box 10
    public void consume_2439589() {
        initNPC(MapleLifeFactory.getNPC(9062475));

        int rewards[][] = new int[][] {
                // ItemID, Quantity, Period, AllStat, Attack
                { 2436078, 20, 0, 0, 0 }, // Core Gemstone x20
                { 2450064, 3, 0, 0, 0 }, // EXP 2x Coupon x3 (Untradable)
                { 5062010, 20, 0, 0, 0 }, // Black Cube x20
                { 2439292, 5, 0, 0, 0 }, // Labyrinth Arcane Symbol Box
        };

        String v0 = "หากเปิดกล่องจะได้รับไอเทมดังต่อไปนี้ ต้องการเปิดไหมครับ?\r\n\r\n";
        v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
        for (int[] reward : rewards) {
            v0 += "#b#i" + reward[0] + "# #z" + reward[0] + "#";
            if (reward[2] > 0) {
                v0 += " (ใช้งาน " + reward[2] + " วัน) ";
            }
            v0 += "#k " + reward[1] + "개";
            if (reward[3] > 0 || reward[4] > 0) {
                v0 += "\r\n  - All Stat +" + reward[3] + ", Atk/M.Atk +" + reward[4] + "\r\n";
            }
            v0 += "\r\n";
        }
        int v1 = self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByUser);
        if (v1 > 0) {
            // Originally exchange works, but doing this due to time-limited items.
            if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 9 ||
                    getPlayer().getInventory(MapleInventoryType.CASH).getNumFreeSlot() < 1) {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Use#k และ #bกระเป๋า Cash#k แล้วลองใหม่นะครับ",
                        ScriptMessageFlag.NpcReplacedByUser);
            } else {
                if (target.exchange(2439589, -1) == 1) {
                    for (int[] reward : rewards) {
                        if (reward[3] > 0 || reward[4] > 0) {
                            exchangeSupportEquipPeriod(reward[0], reward[3], reward[4], reward[2]);
                        } else {
                            target.exchange(reward[0], reward[1]);
                        }
                    }
                    getPlayer().gainMeso(50000000, true);
                    getPlayer().gainExp(100000000000L, true, true, true);
                    self.say("เปิดกล่องได้รับไอเทมเรียบร้อยแล้วครับ", ScriptMessageFlag.NpcReplacedByUser);
                }
            }
        }
    }

    // Jin: True Growth Support Box 11
    public void consume_2439590() {
        initNPC(MapleLifeFactory.getNPC(9062475));

        int rewards[][] = new int[][] {
                // ItemID, Quantity, Period, AllStat, Attack
                { 1182200, 1, 0, 30, 20 }, // Seven Days Badge
                { 5062010, 20, 0, 0, 0 }, // Black Cube x20
                { 5680150, 1, 0, 0, 0 }, // 30,000 Maple Points Exchange Ticket
                { 2434290, 5, 0, 0, 0 }, // Medal of Honor guaranteed by Mu Gong x5
        };

        String v0 = "หากเปิดกล่องจะได้รับไอเทมดังต่อไปนี้ ต้องการเปิดไหมครับ?\r\n\r\n";
        v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
        for (int[] reward : rewards) {
            v0 += "#b#i" + reward[0] + "# #z" + reward[0] + "#";
            if (reward[2] > 0) {
                v0 += " (ใช้งาน " + reward[2] + " วัน) ";
            }
            v0 += "#k " + reward[1] + "개";
            if (reward[3] > 0 || reward[4] > 0) {
                v0 += "\r\n  - All Stat +" + reward[3] + ", Atk/M.Atk +" + reward[4] + "\r\n";
            }
            v0 += "\r\n";
        }
        int v1 = self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByUser);
        if (v1 > 0) {
            // Originally exchange works, but doing this due to time-limited items.
            if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 2 ||
                    getPlayer().getInventory(MapleInventoryType.CASH).getNumFreeSlot() < 3 ||
                    getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 1) {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Use#k, #bกระเป๋า Cash#k และ #bกระเป๋า Equip#k แล้วลองใหม่นะครับ",
                        ScriptMessageFlag.NpcReplacedByUser);
            } else {
                if (target.exchange(2439590, -1) == 1) {
                    for (int[] reward : rewards) {
                        if (reward[3] > 0 || reward[4] > 0) {
                            exchangeSupportEquipPeriod(reward[0], reward[3], reward[4], reward[2]);
                        } else {
                            target.exchange(reward[0], reward[1]);
                        }
                    }
                    getPlayer().gainMeso(100000000, true);
                    getPlayer().gainExp(100000000000L, true, true, true);
                    self.say("เปิดกล่องได้รับไอเทมเรียบร้อยแล้วครับ", ScriptMessageFlag.NpcReplacedByUser);
                }
            }
        }
    }

    // Jin: True Growth Support Box 12
    public void consume_2439591() {
        initNPC(MapleLifeFactory.getNPC(9062475));

        int rewards[][] = new int[][] {
                // ItemID, Quantity, Period, AllStat, Attack
                { 4001832, 5000, 0, 0, 0 }, // Spell Trace
                { 2436078, 20, 0, 0, 0 }, // Core Gemstone x20
                { 2450064, 3, 0, 0, 0 }, // EXP 2x Coupon
                { 3014005, 1, 0, 0, 0 }, // Honor Roll of Glory
                { 2434891, 1, 0, 0, 0 }, // Damage Skin Selection Box
                { 2439239, 1, 0, 0, 0 }, // Magical Scroll Exchange Ticket
        };

        String v0 = "หากเปิดกล่องจะได้รับไอเทมดังต่อไปนี้ ต้องการเปิดไหมครับ?\r\n\r\n";
        v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
        for (int[] reward : rewards) {
            v0 += "#b#i" + reward[0] + "# #z" + reward[0] + "#";
            if (reward[2] > 0) {
                v0 += " (ใช้งาน " + reward[2] + " วัน) ";
            }
            v0 += "#k " + reward[1] + "개";
            if (reward[3] > 0 || reward[4] > 0) {
                v0 += "\r\n  - All Stat +" + reward[3] + ", Atk/M.Atk +" + reward[4] + "\r\n";
            }
            v0 += "\r\n";
        }
        int v1 = self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByUser);
        if (v1 > 0) {
            // Originally exchange works, but doing this due to time-limited items.
            if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 6 ||
                    getPlayer().getInventory(MapleInventoryType.SETUP).getNumFreeSlot() < 1 ||
                    getPlayer().getInventory(MapleInventoryType.ETC).getNumFreeSlot() < 2) {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Use#k, #bกระเป๋า Etc#k และ #bกระเป๋า Setup#k แล้วลองใหม่นะครับ",
                        ScriptMessageFlag.NpcReplacedByUser);
            } else {
                if (target.exchange(2439591, -1) == 1) {
                    for (int[] reward : rewards) {
                        if (reward[3] > 0 || reward[4] > 0) {
                            exchangeSupportEquipPeriod(reward[0], reward[3], reward[4], reward[2]);
                        } else {
                            target.exchange(reward[0], reward[1]);
                        }
                    }
                    getPlayer().gainMeso(100000000, true);
                    getPlayer().gainExp(300000000000L, true, true, true);
                    self.say("เปิดกล่องได้รับไอเทมเรียบร้อยแล้วครับ", ScriptMessageFlag.NpcReplacedByUser);
                }
            }
        }
    }

    // Jin: True Growth Support Box 13
    public void consume_2439592() {
        initNPC(MapleLifeFactory.getNPC(9062475));

        int rewards[][] = new int[][] {
                // ItemID, Quantity, Period, AllStat, Attack
                { 2439239, 2, 0, 0, 0 }, // Magical Scroll Exchange Ticket x2
                { 5680150, 2, 0, 0, 0 }, // 30,000 Maple Points Exchange Ticket x2
                { 2439292, 5, 0, 0, 0 }, // Labyrinth Arcane Symbol Box x5
                { 2439604, 1, 0, 0, 0 }, // Jin: True Special Cody Box (R)
        };

        String v0 = "หากเปิดกล่องจะได้รับไอเทมดังต่อไปนี้ ต้องการเปิดไหมครับ?\r\n\r\n";
        v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
        for (int[] reward : rewards) {
            v0 += "#b#i" + reward[0] + "# #z" + reward[0] + "#";
            if (reward[2] > 0) {
                v0 += " (ใช้งาน " + reward[2] + " วัน) ";
            }
            v0 += "#k " + reward[1] + "개";
            if (reward[3] > 0 || reward[4] > 0) {
                v0 += "\r\n  - All Stat +" + reward[3] + ", Atk/M.Atk +" + reward[4] + "\r\n";
            }
            v0 += "\r\n";
        }
        int v1 = self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByUser);
        if (v1 > 0) {
            // Originally exchange works, but doing this due to time-limited items.
            if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 8 ||
                    getPlayer().getInventory(MapleInventoryType.CASH).getNumFreeSlot() < 2) {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Use#k และ #bกระเป๋า Cash#k แล้วลองใหม่นะครับ",
                        ScriptMessageFlag.NpcReplacedByUser);
            } else {
                if (target.exchange(2439592, -1) == 1) {
                    for (int[] reward : rewards) {
                        if (reward[3] > 0 || reward[4] > 0) {
                            exchangeSupportEquipPeriod(reward[0], reward[3], reward[4], reward[2]);
                        } else {
                            target.exchange(reward[0], reward[1]);
                        }
                    }
                    getPlayer().gainMeso(100000000, true);
                    getPlayer().gainExp(350000000000L, true, true, true);
                    self.say("เปิดกล่องได้รับไอเทมเรียบร้อยแล้วครับ", ScriptMessageFlag.NpcReplacedByUser);
                }
            }
        }
    }

    // Jin: True Growth Support Box 14
    public void consume_2439593() {
        initNPC(MapleLifeFactory.getNPC(9062475));

        int rewards[][] = new int[][] {
                // ItemID, Quantity, Period, AllStat, Attack
                { 4001832, 5000, 0, 0, 0 }, // Spell Trace
                { 2436078, 20, 0, 0, 0 }, // Core Gemstone
                { 5062010, 50, 0, 0, 0 }, // Black Cube
                { 2434891, 1, 0, 0, 0 }, // Damage Skin Selection Box
                { 2439604, 1, 0, 0, 0 }, // Jin: True Special Cody Box (R)
        };

        String v0 = "หากเปิดกล่องจะได้รับไอเทมดังต่อไปนี้ ต้องการเปิดไหมครับ?\r\n\r\n";
        v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
        for (int[] reward : rewards) {
            v0 += "#b#i" + reward[0] + "# #z" + reward[0] + "#";
            if (reward[2] > 0) {
                v0 += " (ใช้งาน " + reward[2] + " วัน) ";
            }
            v0 += "#k " + reward[1] + "개";
            if (reward[3] > 0 || reward[4] > 0) {
                v0 += "\r\n  - All Stat +" + reward[3] + ", Atk/M.Atk +" + reward[4] + "\r\n";
            }
            v0 += "\r\n";
        }
        int v1 = self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByUser);
        if (v1 > 0) {
            // Originally exchange works, but doing this due to time-limited items.
            if (getPlayer().getInventory(MapleInventoryType.ETC).getNumFreeSlot() < 2 ||
                    getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 4 ||
                    getPlayer().getInventory(MapleInventoryType.CASH).getNumFreeSlot() < 2) {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Use#k, #bกระเป๋า Etc#k และ #bกระเป๋า Cash#k แล้วลองใหม่นะครับ",
                        ScriptMessageFlag.NpcReplacedByUser);
            } else {
                if (target.exchange(2439593, -1) == 1) {
                    for (int[] reward : rewards) {
                        if (reward[3] > 0 || reward[4] > 0) {
                            exchangeSupportEquipPeriod(reward[0], reward[3], reward[4], reward[2]);
                        } else {
                            target.exchange(reward[0], reward[1]);
                        }
                    }
                    getPlayer().gainMeso(200000000, true);
                    getPlayer().gainExp(400000000000L, true, true, true);
                    self.say("เปิดกล่องได้รับไอเทมเรียบร้อยแล้วครับ", ScriptMessageFlag.NpcReplacedByUser);
                }
            }
        }
    }

    // Jin: True Growth Support Box 15
    public void consume_2439594() {
        initNPC(MapleLifeFactory.getNPC(9062475));

        int rewards[][] = new int[][] {
                // ItemID, Quantity, Period, AllStat, Attack
                { 2439292, 5, 0, 0, 0 }, // Labyrinth Arcane Symbol Box
                { 5062503, 10, 0, 0, 0 }, // White Additional Cube
                { 2436604, 2, 0, 0, 0 }, // Eternal Rebirth Flame Lucky Bag
                { 2450064, 2, 0, 0, 0 }, // EXP 2x Coupon
        };

        String v0 = "หากเปิดกล่องจะได้รับไอเทมดังต่อไปนี้ ต้องการเปิดไหมครับ?\r\n\r\n";
        v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
        for (int[] reward : rewards) {
            v0 += "#b#i" + reward[0] + "# #z" + reward[0] + "#";
            if (reward[2] > 0) {
                v0 += " (ใช้งาน " + reward[2] + " วัน) ";
            }
            v0 += "#k " + reward[1] + "개";
            if (reward[3] > 0 || reward[4] > 0) {
                v0 += "\r\n  - All Stat +" + reward[3] + ", Atk/M.Atk +" + reward[4] + "\r\n";
            }
            v0 += "\r\n";
        }
        int v1 = self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByUser);
        if (v1 > 0) {
            // Originally exchange works, but doing this due to time-limited items.
            if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 9 ||
                    getPlayer().getInventory(MapleInventoryType.CASH).getNumFreeSlot() < 2) {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Use#k และ #bกระเป๋า Cash#k แล้วลองใหม่นะครับ",
                        ScriptMessageFlag.NpcReplacedByUser);
            } else {
                if (target.exchange(2439594, -1) == 1) {
                    for (int[] reward : rewards) {
                        if (reward[3] > 0 || reward[4] > 0) {
                            exchangeSupportEquipPeriod(reward[0], reward[3], reward[4], reward[2]);
                        } else {
                            target.exchange(reward[0], reward[1]);
                        }
                    }
                    getPlayer().gainMeso(500000000, true);
                    getPlayer().gainExp(450000000000L, true, true, true);
                    self.say("เปิดกล่องได้รับไอเทมเรียบร้อยแล้วครับ", ScriptMessageFlag.NpcReplacedByUser);
                }
            }
        }
    }

    // Jin: True Growth Support Box 16
    public void consume_2439595() {
        initNPC(MapleLifeFactory.getNPC(9062475));

        int rewards[][] = new int[][] {
                // ItemID, Quantity, Period, AllStat, Attack
                { 2434891, 1, 0, 0, 0 }, // Damage Skin Selection Box x1
                { 2439605, 1, 0, 0, 0 }, // Jin: True Special Cody Box (S)
                { 2450064, 3, 0, 0, 0 }, // EXP 2x Coupon
                { 5062010, 50, 0, 0, 0 }, // Black Cube x50
        };

        String v0 = "หากเปิดกล่องจะได้รับไอเทมดังต่อไปนี้ ต้องการเปิดไหมครับ?\r\n\r\n";
        v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
        for (int[] reward : rewards) {
            v0 += "#b#i" + reward[0] + "# #z" + reward[0] + "#";
            if (reward[2] > 0) {
                v0 += " (ใช้งาน " + reward[2] + " วัน) ";
            }
            v0 += "#k " + reward[1] + "개";
            if (reward[3] > 0 || reward[4] > 0) {
                v0 += "\r\n  - All Stat +" + reward[3] + ", Atk/M.Atk +" + reward[4] + "\r\n";
            }
            v0 += "\r\n";
        }
        int v1 = self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByUser);
        if (v1 > 0) {
            // Originally exchange works, but doing this due to time-limited items.
            if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 5 ||
                    getPlayer().getInventory(MapleInventoryType.CASH).getNumFreeSlot() < 2) {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Use#k และ #bกระเป๋า Cash#k แล้วลองใหม่นะครับ",
                        ScriptMessageFlag.NpcReplacedByUser);
            } else {
                if (target.exchange(2439595, -1) == 1) {
                    for (int[] reward : rewards) {
                        if (reward[3] > 0 || reward[4] > 0) {
                            exchangeSupportEquipPeriod(reward[0], reward[3], reward[4], reward[2]);
                        } else {
                            target.exchange(reward[0], reward[1]);
                        }
                    }
                    getPlayer().gainMeso(1000000000, true);
                    getPlayer().gainExp(500000000000L, true, true, true);
                    self.say("เปิดกล่องได้รับไอเทมเรียบร้อยแล้วครับ", ScriptMessageFlag.NpcReplacedByUser);
                }
            }
        }
    }

    // Jin: True Growth Support Box 17
    public void consume_2439596() {
        initNPC(MapleLifeFactory.getNPC(9062475));

        int rewards[][] = new int[][] {
                // ItemID, Quantity, Period, AllStat, Attack
                { 5680150, 5, 0, 0, 0 }, // 30,000 Maple Points Exchange Ticket
                { 2439241, 3, 0, 0, 0 }, // Wondrous Berry x3
                { 2450064, 3, 0, 0, 0 }, // EXP 2x Coupon
                { 2439239, 3, 0, 0, 0 }, // Magical Scroll Exchange Ticket x3
                { 2435764, 3, 0, 0, 0 }, // Gold Apple Exchange Ticket
        };

        String v0 = "หากเปิดกล่องจะได้รับไอเทมดังต่อไปนี้ ต้องการเปิดไหมครับ?\r\n\r\n";
        v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
        for (int[] reward : rewards) {
            v0 += "#b#i" + reward[0] + "# #z" + reward[0] + "#";
            if (reward[2] > 0) {
                v0 += " (ใช้งาน " + reward[2] + " วัน) ";
            }
            v0 += "#k " + reward[1] + "개";
            if (reward[3] > 0 || reward[4] > 0) {
                v0 += "\r\n  - All Stat +" + reward[3] + ", Atk/M.Atk +" + reward[4] + "\r\n";
            }
            v0 += "\r\n";
        }
        int v1 = self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByUser);
        if (v1 > 0) {
            // Originally exchange works, but doing this due to time-limited items.
            if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 9 ||
                    getPlayer().getInventory(MapleInventoryType.CASH).getNumFreeSlot() < 2) {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Use#k และ #bกระเป๋า Cash#k แล้วลองใหม่นะครับ",
                        ScriptMessageFlag.NpcReplacedByUser);
            } else {
                if (target.exchange(2439596, -1) == 1) {
                    for (int[] reward : rewards) {
                        if (reward[3] > 0 || reward[4] > 0) {
                            exchangeSupportEquipPeriod(reward[0], reward[3], reward[4], reward[2]);
                        } else {
                            target.exchange(reward[0], reward[1]);
                        }
                    }
                    getPlayer().gainMeso(1000000000, true);
                    getPlayer().gainExp(500000000000L, true, true, true);
                    self.say("เปิดกล่องได้รับไอเทมเรียบร้อยแล้วครับ", ScriptMessageFlag.NpcReplacedByUser);
                }
            }
        }
    }

    // Jin: True Growth Support Box 18
    public void consume_2439597() {
        initNPC(MapleLifeFactory.getNPC(9062475));

        int rewards[][] = new int[][] {
                // ItemID, Quantity, Period, AllStat, Attack
                { 3014028, 1, 0, 0, 0 }, // Brilliant Symbol of Honor
                { 2049360, 3, 0, 0, 0 }, // Amazing Enhancement Scroll
                { 2439605, 1, 0, 0, 0 }, // Jin: True Special Cody Box (S)
                { 2439241, 5, 0, 0, 0 }, // Wondrous Berry Exchange Ticket
                { 2435764, 5, 0, 0, 0 }, // Gold Apple Exchange Ticket
        };

        String v0 = "หากเปิดกล่องจะได้รับไอเทมดังต่อไปนี้ ต้องการเปิดไหมครับ?\r\n\r\n";
        v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
        for (int[] reward : rewards) {
            v0 += "#b#i" + reward[0] + "# #z" + reward[0] + "#";
            if (reward[2] > 0) {
                v0 += " (ใช้งาน " + reward[2] + " วัน) ";
            }
            v0 += "#k " + reward[1] + "개";
            if (reward[3] > 0 || reward[4] > 0) {
                v0 += "\r\n  - All Stat +" + reward[3] + ", Atk/M.Atk +" + reward[4] + "\r\n";
            }
            v0 += "\r\n";
        }
        int v1 = self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByUser);
        if (v1 > 0) {
            // Originally exchange works, but doing this due to time-limited items.
            if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 13 ||
                    getPlayer().getInventory(MapleInventoryType.SETUP).getNumFreeSlot() < 1) {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Use#k และ #bกระเป๋า Setup#k แล้วลองใหม่นะครับ",
                        ScriptMessageFlag.NpcReplacedByUser);
            } else {
                if (target.exchange(2439597, -1) == 1) {
                    for (int[] reward : rewards) {
                        if (reward[3] > 0 || reward[4] > 0) {
                            exchangeSupportEquipPeriod(reward[0], reward[3], reward[4], reward[2]);
                        } else {
                            target.exchange(reward[0], reward[1]);
                        }
                    }
                    getPlayer().gainMeso(5000000000L, true);
                    getPlayer().gainExp(1000000000000L, true, true, true);
                    self.say("เปิดกล่องได้รับไอเทมเรียบร้อยแล้วครับ", ScriptMessageFlag.NpcReplacedByUser);
                }
            }
        }
    }

    // Jin: True Black Equipment Box
    public void consume_2433509() {
        int[] rewards = new int[] {
                1212116, 1213023, 1214023, 1222110, 1232110, 1242117, 1262047, 1272031, 1282036, 1292023, 1302334,
                1312200, 1322251, 1332275, 1362136, 1372223, 1382260, 1402252, 1412178, 1422185, 1432215, 1442269,
                1452253, 1462240, 1472262, 1482217, 1492232, 1522139, 1532145, 1582041, 1592030
        };

        initNPC(MapleLifeFactory.getNPC(9062474));
        String v0 = "คุณสามารถเลือกรับ #bอาวุธ Black 1 ชิ้น#k จากรายการต่อไปนี้ครับ\r\nอาวุธที่เลือกสามารถใช้ได้ #e14 วัน#n มีออปชั่น #bAll Stat +200, Atk/M.Atk +200#k\r\n\r\nเลือกอาวุธที่ต้องการได้เลยครับ#b\r\n\r\n";
        for (int i = 0; i < rewards.length; ++i) {
            int itemID = rewards[i];
            v0 += "#L" + i + "##i" + itemID + "# #z" + itemID + "##l\r\n";
        }

        int v1 = self.askMenu(v0, ScriptMessageFlag.NpcReplacedByNpc);
        if (v1 >= rewards.length) {
            return; // TODO: Hack
        }
        int itemID = rewards[v1];
        if (self.askYesNo(
                "อาวุธที่เลือกคือ #b#i" + itemID + "# #z" + itemID
                        + "##k ครับ\r\nต้องการเลือกอาวุธนี้ใช่ไหมครับ?\r\n\r\n#b(หากกด #eใช่#n จะได้รับไอเทมทันที)",
                ScriptMessageFlag.NpcReplacedByNpc) > 0) {
            if (getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 1) {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Equip#k แล้วลองใหม่นะครับ");
            } else {
                if (target.exchange(2433509, -1) == 1) {
                    exchangeSupportEquipBonusStatPeriod(itemID, 200, 200, 14);
                    self.say("มอบให้เรียบร้อยแล้วครับ ลองเช็คในกระเป๋าดูนะครับ!");
                }
            }
        }
    }

    // Jin: True Legendary Arcane Shade Weapon Box
    public void consume_2439609() {
        int[] rewards = new int[] {
                1212131, 1213030, 1214030, 1222124, 1232124, 1242144, 1242145, 1262053, 1272043, 1282043, 1292030,
                1302359, 1312215, 1322266,
                1332291, 1362151, 1372239, 1382276, 1402271, 1412191, 1422199, 1432229, 1442287, 1452269, 1462254,
                1472277, 1482234, 1492247, 1522154, 1532159, 1582046, 1592037
        };

        initNPC(MapleLifeFactory.getNPC(9062474));
        String v0 = "คุณสามารถเลือกรับ #bอาวุธ Arcane Shade 1 ชิ้น#k จากรายการต่อไปนี้ครับ\r\nอาวุธที่เลือกจะได้รับ #b15 ดาว และระดับตำนาน#k\r\n\r\nเลือกอาวุธที่ต้องการได้เลยครับ#b\r\n\r\n";
        for (int i = 0; i < rewards.length; ++i) {
            int itemID = rewards[i];
            v0 += "#L" + i + "##i" + itemID + "# #z" + itemID + "##l\r\n";
        }

        int v1 = self.askMenu(v0, ScriptMessageFlag.NpcReplacedByNpc);
        if (v1 >= rewards.length) {
            return; // TODO: Hack
        }
        int itemID = rewards[v1];
        if (self.askYesNo(
                "อาวุธที่เลือกคือ #b#i" + itemID + "# #z" + itemID
                        + "##k ครับ\r\nต้องการเลือกอาวุธนี้ใช่ไหมครับ?\r\n\r\n#b(หากกด #eใช่#n จะได้รับไอเทมทันที)",
                ScriptMessageFlag.NpcReplacedByNpc) > 0) {
            if (getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 1) {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Equip#k แล้วลองใหม่นะครับ");
            } else {
                if (target.exchange(2439609, -1) == 1) {
                    exchangeEquipCHUCWithScroll(itemID, 15, 2);
                    self.say("มอบให้เรียบร้อยแล้วครับ ลองเช็คในกระเป๋าดูนะครับ!");
                }
            }
        }
    }

    // Jin: True Unique Absolab Weapon Box
    public void consume_2439610() {
        int[] rewards = new int[] {
                1212121, 1213028, 1214028, 1222114, 1232114, 1242123, 1242124, 1262040, 1272021, 1282022,
                1292028, 1302344, 1312204, 1322256, 1332280, 1342105, 1362141, 1372229, 1382266, 1402260, 1412182,
                1422190, 1432219,
                1442276, 1452258, 1462244, 1472266, 1482222, 1492236, 1522144, 1532148, 1582027, 1592028
        };

        initNPC(MapleLifeFactory.getNPC(9062474));
        String v0 = "คุณสามารถเลือกรับ #bอาวุธ Absolab 1 ชิ้น#k จากรายการต่อไปนี้ครับ\r\nอาวุธที่เลือกจะได้รับ #b15 ดาว และระดับ Unique#k\r\n\r\nเลือกอาวุธที่ต้องการได้เลยครับ#b\r\n\r\n";
        for (int i = 0; i < rewards.length; ++i) {
            int itemID = rewards[i];
            v0 += "#L" + i + "##i" + itemID + "# #z" + itemID + "##l\r\n";
        }

        int v1 = self.askMenu(v0, ScriptMessageFlag.NpcReplacedByNpc);
        if (v1 >= rewards.length) {
            return; // TODO: Hack
        }
        int itemID = rewards[v1];
        if (self.askYesNo("อาวุธที่เลือกคือ #b#i" + itemID + "# #z" + itemID
                + "##k ครับ\r\nต้องการเลือกอาวุธนี้ใช่ไหมครับ?\r\n\r\n#b(หากกด #eใช่#n จะได้รับไอเทมทันที)",
                ScriptMessageFlag.NpcReplacedByNpc) == 1) {
            if (getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 1) {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Equip#k แล้วลองใหม่นะครับ");
            } else {
                if (target.exchange(2439610, -1) == 1) {
                    exchangeEquipCHUCWithScroll(itemID, 15, 2);
                    self.say("มอบให้เรียบร้อยแล้วครับ ลองเช็คในกระเป๋าดูนะครับ!");
                }
            }
        }
    }

    // Good Bye Extreme
    public void consume_2439611() {
        initNPC(MapleLifeFactory.getNPC(9062000));
        int[][] rewards = new int[][] {
                { 2630512, 100 }, // Select Arcane Symbol Exchange Ticket x100
                { 2439605, 3 }, // Jin: True Special Cody Box (S)
                { 2439660, 5 }, // Typhon Growth Potion x5
                { 5060048, 2 }, // Gold Apple x2
                { 5068300, 2 }, // Wondrous Berry x2
                { 5062010, 50 }, // Black Cube x50
                { 2436078, 100 }, // Core Gemstone x100
                { 2439611, -1 }, // Box Usage
        };

        String v0 = "หากเปิดกล่องจะได้รับไอเทมดังต่อไปนี้ ต้องการเปิดไหมครับ?\r\n\r\n";
        v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
        for (int[] reward : rewards) {
            if (reward[1] != -1) {
                v0 += "#b#i" + reward[0] + "# #z" + reward[0] + "##k " + reward[1] + " ชิ้น\r\n";
            }
        }
        int v1 = self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByUser);
        if (v1 > 0) {
            if (target.exchange(rewards) == 1) {
                getPlayer().gainMeso(1000000000, true);
                self.say("เปิดกล่องได้รับไอเทมเรียบร้อยแล้วครับ", ScriptMessageFlag.NpcReplacedByUser);
            } else {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Use#k และ #bกระเป๋า Cash#k แล้วลองใหม่นะครับ",
                        ScriptMessageFlag.NpcReplacedByUser);
            }
        }
    }

    // Cygnus Clear Count Reset Ticket
    public void consume_2431968() {
        bossResetCoupon(QuestExConstants.Cygnus.getQuestID(), "cygnus_clear", 2431968);
    }

    // Hard Magnus Clear Count Reset Ticket
    public void consume_2431969() {
        bossResetCoupon(QuestExConstants.HardMagnus.getQuestID(), "hard_magnus_clear", 2431969);
    }

    // Chaos Von Bon Clear Count Reset Ticket
    public void consume_2431970() {
        bossResetCoupon(QuestExConstants.ChaosVonBon.getQuestID(), "chaos_banban_clear", 2431970);
    }

    // Chaos Pierre Clear Count Reset Ticket
    public void consume_2431971() {
        bossResetCoupon(QuestExConstants.ChaosPierre.getQuestID(), "chaos_pierre_clear", 2431971);
    }

    // Chaos Blood Queen Clear Count Reset Ticket
    public void consume_2431972() {
        bossResetCoupon(QuestExConstants.ChaosCrimsonQueen.getQuestID(), "chaos_b_queen_clear", 2431972);
    }

    // Chaos Vellum Clear Count Reset Ticket
    public void consume_2431973() {
        bossResetCoupon(QuestExConstants.ChaosVellum.getQuestID(), "chaos_velum_clear", 2431973);
    }

    public void bossResetCoupon(int questID, String key, int itemID) {
        initNPC(MapleLifeFactory.getNPC(2007));
        MapleCharacter user = getPlayer();
        if (user.getOneInfo(questID, "eNum") != null) {
            if (1 == self.askYesNo("#eต้องการใช้ตั๋วรีเซ็ตหรือไม่?")) {
                if (DBConfig.isGanglim) {
                    user.dropMessage(5, user.getOneInfoQuestInteger(1234569, "use_" + itemID) + "");
                }
                int resetCan = 2;
                if (!DBConfig.isGanglim) {
                    resetCan = 8;
                }
                if (DBConfig.isGanglim) {
                    if (resetCan <= user.getOneInfoQuestInteger(1234569, "use_" + itemID)) {
                        self.sayOk("ไม่สามารถรีเซ็ตบันทึกการเข้าบอสนี้ได้อีกในสัปดาห์นี้");
                        return;
                    }
                    if (user.getOneInfo(1234569, key) != null) {
                        if (user.getOneInfo(1234569, key).equals("1")) {
                            if (target.exchange(itemID, -1) == 1) {
                                user.updateOneInfo(1234569, key, "0");
                                user.updateOneInfo(1234569, "use_" + itemID,
                                        String.valueOf(user.getOneInfoQuestInteger(1234569, "use_" + itemID) + 1));
                                user.updateOneInfo(questID, "eNum", "0");
                                user.updateOneInfo(questID, "lastDate", "2000/01/01/01/01/01");
                                self.sayOk("รีเซ็ตเรียบร้อยแล้ว");
                            } else {
                                self.sayOk("เกิดข้อผิดพลาดไม่ทราบสาเหตุในการใช้");
                            }
                        } else {
                            self.sayOk("ไม่พบบันทึกการเคลียร์ในสัปดาห์นี้ หรือไม่สามารถดำเนินการได้ตามปกติ");
                        }
                    } else {
                        self.sayOk("ไม่พบบันทึกการเคลียร์ในสัปดาห์นี้ หรือไม่สามารถดำเนินการได้ตามปกติ");
                    }
                } else { // 진
                    int nSingleClear = user.getOneInfoQuestInteger(questID, "eNum_single");
                    int nMultiClear = user.getOneInfoQuestInteger(questID, "eNum_multi");
                    user.dropMessage(5,
                            "ใช้ Single : " + user.getOneInfoQuestInteger(1234569, "use_" + itemID + "_single")
                                    + " / ใช้ Multi : "
                                    + user.getOneInfoQuestInteger(1234569, "use_" + itemID + "_multi"));
                    if (resetCan <= user.getOneInfoQuestInteger(1234569, "use_" + itemID + "_single")
                            && resetCan <= user.getOneInfoQuestInteger(1234569, "use_" + itemID + "_multi")) {
                        self.sayOk("ไม่สามารถรีเซ็ตบันทึกการเข้าบอสนี้ได้อีกในสัปดาห์นี้");
                        return;
                    }
                    if (nSingleClear == 0 && nMultiClear == 0) {
                        self.sayOk("ไม่พบบันทึกการเคลียร์บอสนี้ในสัปดาห์นี้ จึงไม่สามารถดำเนินการได้ตามปกติ");
                        return;
                    }
                    if (nSingleClear < 0 || nMultiClear < 0) {
                        self.sayOk("เกิดข้อผิดพลาดในบันทึกรีเซ็ต ไม่สามารถดำเนินการได้");
                        return;
                    }
                    String askClear = "กรุณาเลือกโหมดที่ต้องการลดจำนวนครั้งการเข้า";
                    if (nSingleClear > 0) {
                        askClear += "\r\n#L0#โหมด Single (เคลียร์ " + nSingleClear + " ครั้ง)#l";
                    }
                    if (nMultiClear > 0) {
                        askClear += "\r\n#L1#โหมด Multi (เคลียร์ " + nMultiClear + " ครั้ง)#l";
                    }
                    int clearSel = self.askMenu(askClear);
                    if (clearSel != 0 && clearSel != 1)
                        return;
                    if (clearSel == 0 && (nSingleClear <= 0
                            || resetCan <= user.getOneInfoQuestInteger(1234569, "use_" + itemID + "_single"))) {
                        self.sayOk("ไม่สามารถรีเซ็ตบันทึกการเข้าบอสนี้ได้อีกในสัปดาห์นี้");
                        return;
                    }
                    if (clearSel == 1 && (nMultiClear <= 0
                            || resetCan <= user.getOneInfoQuestInteger(1234569, "use_" + itemID + "_multi"))) {
                        self.sayOk("ไม่สามารถรีเซ็ตบันทึกการเข้าบอสนี้ได้อีกในสัปดาห์นี้");
                        return;
                    }
                    if (target.exchange(itemID, -1) == 1) {
                        user.updateOneInfo(1234569, key, "0");
                        user.updateOneInfo(questID, "eNum", "0");
                        if (clearSel == 0) {
                            user.updateOneInfo(1234569, "use_" + itemID + "_single", String
                                    .valueOf(user.getOneInfoQuestInteger(1234569, "use_" + itemID + "_single") + 1));
                            user.updateOneInfo(questID, "eNum_single", String.valueOf(nSingleClear - 1));
                            user.updateOneInfo(questID, "lastDate", "2000/01/01/01/01/01");
                            self.sayOk("หักจำนวนครั้ง Single 1 ครั้งเรียบร้อยแล้ว");
                        } else { // clearSel == 1
                            user.updateOneInfo(1234569, "use_" + itemID + "_multi", String
                                    .valueOf(user.getOneInfoQuestInteger(1234569, "use_" + itemID + "_multi") + 1));
                            user.updateOneInfo(questID, "eNum_multi", String.valueOf(nMultiClear - 1));
                            user.updateOneInfo(questID, "lastDate", "2000/01/01/01/01/01");
                            self.sayOk("หักจำนวนครั้ง Multi 1 ครั้งเรียบร้อยแล้ว");
                        }
                    } else {
                        self.sayOk("เกิดข้อผิดพลาดในการใช้งาน เนื่องจากสาเหตุที่ไม่ทราบครับ");
                    }
                }
            }
        } else {
            self.sayOk("ไม่พบประวัติการเคลียร์ในสัปดาห์นี้ จึงไม่สามารถดำเนินการได้ตามปกติครับ");
        }
    }

    // Jin: True Newbie Support Box
    public void consume_2437121() {
        initNPC(MapleLifeFactory.getNPC(9062474));
        if (getPlayer().getOneInfoQuestInteger(1234567, "use_newbie_support") == 1) {
            self.say("เหมือนว่าจะเคยรับรางวัลไปแล้วนะครับ", ScriptMessageFlag.NpcReplacedByUser);

            if (target.exchange(2437121, -1) == 1) {
            }
            return;
        }
        int[][] rewards = new int[][] {
                { 2049361, 3 },
                { 5062009, 100 },
                { 5068300, 1 },
                { 5060048, 1 },
                { 5680150, 1 },
                { 2435890, 1 },
                { 2437121, -1 }, // Box Usage
        };
        String v0 = "หากเปิดกล่องจะได้รับไอเทมดังต่อไปนี้ ต้องการเปิดไหมครับ?\r\n\r\n";
        v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
        // v0 += "#b#i5000931# #z5000931# (Period 30 Days)#k 1 Piece\r\n";
        for (int[] reward : rewards) {
            if (reward[1] != -1) {
                v0 += "#b#i" + reward[0] + "# #z" + reward[0] + "##k " + reward[1] + " ชิ้น\r\n";
            }
        }
        int v1 = self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByUser);
        if (v1 > 0) {
            if (getPlayer().getInventory(MapleInventoryType.CASH).getNumFreeSlot() < 7) {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Cash#k แล้วลองใหม่นะครับ", ScriptMessageFlag.NpcReplacedByUser);
                return;
            }
            if (target.exchange(rewards) == 1) {
                getPlayer().updateOneInfo(1234567, "use_newbie_support", "1");
                // exchangePetPeriod(5000931, 30);
                self.say("เปิดกล่องได้รับไอเทมเรียบร้อยแล้วครับ", ScriptMessageFlag.NpcReplacedByUser);
            } else {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Use#k และ #bกระเป๋า Cash#k แล้วลองใหม่นะครับ",
                        ScriptMessageFlag.NpcReplacedByUser);
            }
        }
    }

    public void consume_2437122() {
        initNPC(MapleLifeFactory.getNPC(9062474));
        int[][] rewards = new int[][] {
                { 2049360, 1 },
                { 2435890, 1 },
                { 2436078, 50 },
                { 2439292, 20 },
                { 2437122, -1 }, // Box Usage
        };
        String v0 = "หากเปิดกล่องจะได้รับไอเทมดังต่อไปนี้ ต้องการเปิดไหมครับ?\r\n\r\n";
        v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
        v0 += "#b#i5060048# #z5060048# (โอกาสได้รับ 20%)#k 1 ชิ้น\r\n";
        v0 += "#b#i5068300# #z5068300# (โอกาสได้รับ 20%)#k 1 ชิ้น\r\n";
        for (int[] reward : rewards) {
            if (reward[1] != -1) {
                v0 += "#b#i" + reward[0] + "# #z" + reward[0] + "##k " + reward[1] + " ชิ้น\r\n";
            }
        }
        int v1 = self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByUser);
        if (v1 > 0) {
            if (getPlayer().getInventory(MapleInventoryType.CASH).getNumFreeSlot() < 2) {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Cash#k แล้วลองใหม่นะครับ", ScriptMessageFlag.NpcReplacedByUser);
                return;
            }
            if (target.exchange(rewards) == 1) {
                if (Randomizer.isSuccess(20)) {
                    getPlayer().dropMessage(5, "ได้รับ Gold Apple 1 ชิ้นแล้ว");
                    target.exchange(5060048, 1);
                }
                if (Randomizer.isSuccess(20)) {
                    getPlayer().dropMessage(5, "ได้รับ Wonder Berry 1 ชิ้นแล้ว");
                    target.exchange(5068300, 1);
                }
                self.say("เปิดกล่องได้รับไอเทมเรียบร้อยแล้วครับ", ScriptMessageFlag.NpcReplacedByUser);
            } else {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Use#k และ #bกระเป๋า Cash#k แล้วลองใหม่นะครับ",
                        ScriptMessageFlag.NpcReplacedByUser);
            }
        }
    }

    // Random Plastic Surgery Growth Potion
    public void consume_2430909() {
        randomTraitSecretPotion(2430909);
    }

    // PC Cafe Random Plastic Surgery Growth Potion
    public void consume_2436786() {
        randomTraitSecretPotion(2436786);
    }

    // Tradeable Trait Potion within Account
    public void consume_2433604() {
        traitSecretPotion(2433604);
    }

    // Tradeable Trait Potion within Account
    public void consume_2633242() {
        traitSecretPotion(2633242);
    }

    // Untradeable Trait Potion
    public void consume_2434921() {
        traitSecretPotion(2434921);
    }

    // Untradeable Trait Potion
    public void consume_2439429() {
        traitSecretPotion(2439429);
    }

    // Tradeable Trait Potion
    public void consume_2436595() {
        traitSecretPotion(2436595);
    }

    // Trait Growth Potion (Gives less EXP than Trait Growth Potion)
    public void consume_2438644() {
        traitSecretPotion(2438644);
    }

    public void randomTraitSecretPotion(int itemId) {
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (self.askYesNo("ต้องการใช้น้ำยาสุ่มค่าอุปนิสัยไหม?", ScriptMessageFlag.NpcReplacedByNpc) == 1) {
            MapleTrait.MapleTraitType traitType = null;
            MapleTrait.MapleTraitType[] t = { MapleTrait.MapleTraitType.charisma,
                    MapleTrait.MapleTraitType.sense,
                    MapleTrait.MapleTraitType.insight,
                    MapleTrait.MapleTraitType.will,
                    MapleTrait.MapleTraitType.craft,
                    MapleTrait.MapleTraitType.charm };
            boolean canUse = false;
            for (MapleTrait.MapleTraitType tT : t) {
                if (getPlayer().getTrait(tT).getLevel() < 100) {
                    canUse = true;
                    break;
                }
            }
            if (!canUse) {
                self.sayOk("ค่าอุปนิสัยทั้งหมดเลเวลสูงสุดแล้ว");
                return;
            }
            while (traitType == null) {
                int r = Randomizer.nextInt(t.length);
                if (getPlayer().getTrait(t[r]).getLevel() < 100) {
                    traitType = t[r];
                }
            }
            if (target.exchange(itemId, -1) > 0) {
                getPlayer().getTrait(traitType).addTrueExp(11040, getPlayer());
                getPlayer().dropMessage(5, traitType.getName() + " เติบโตขึ้นอย่างน่าประหลาด");
            }
        }
    }

    public void traitSecretPotion(int itemId) {
        initNPC(MapleLifeFactory.getNPC(9010000));
        StringBuilder s = new StringBuilder();
        s.append("#L0#Charisma   #kIgnore DEF Increase / Death Penalty Duration Decrease#l\r\n")
                .append("#b#L1#Sense   #kMax MP / Buff Duration Increase#l\r\n")
                .append("#b#L2#Insight   #kIgnore Elemental Resist / Reveal Potential Level Increase#l\r\n")
                .append("#b#L3#Will   #kMax HP / DEF / Abnormal Status Resistance Increase#l\r\n")
                .append("#b#L4#Craft   #kScroll Success Rate / Double Mastery Chance Increase#l\r\n")
                .append("#b#L5#Charm   #kPocket Slot / Facial Expression#l\r\n");
        int v0 = self.askMenu("กรุณาเลือกค่าอุปนิสัยที่ต้องการเพิ่มครับ!#b\r\n" + s.toString(),
                ScriptMessageFlag.NpcReplacedByNpc);

        MapleTrait.MapleTraitType traitType = null;
        switch (v0) {
            case 0:
                traitType = MapleTrait.MapleTraitType.charisma;
                break;
            case 1:
                traitType = MapleTrait.MapleTraitType.sense;
                break;
            case 2:
                traitType = MapleTrait.MapleTraitType.insight;
                break;
            case 3:
                traitType = MapleTrait.MapleTraitType.will;
                break;
            case 4:
                traitType = MapleTrait.MapleTraitType.craft;
                break;
            case 5:
                traitType = MapleTrait.MapleTraitType.charm;
                break;
        }
        if (traitType != null) {
            int level = getPlayer().getTrait(traitType).getLevel();
            if (level < 100) {
                if (target.exchange(itemId, -1) > 0) {
                    int traitEXP = 11040;
                    if (itemId == 2438644) {
                        traitEXP = 3680;
                    }
                    getPlayer().getTrait(traitType).addTrueExp(traitEXP, getPlayer());
                    getPlayer().dropMessage(5, traitType.getName() + " เติบโตขึ้นอย่างน่าประหลาด");
                }
            } else {
                self.sayOk("ค่าอุปนิสัยที่เลือกเลเวลสูงสุดแล้ว", ScriptMessageFlag.NpcReplacedByNpc);
            }
        }
    }

    public void consume_2633201() {
        int menu = self.askMenu(
                "กรุณาเลือก #r#eเพศของไอเทม#k#n ที่ต้องการครับ\r\nตัวละครเพศอื่นจะไม่สามารถสวมใส่ได้\r\n#b#L0#เซ็ต Chu Chu Island (ชาย)#l\r\n#L1#เซ็ต Chu Chu Island (หญิง)#l");
        String cGender = "";
        int[] items = new int[5];
        switch (menu) {
            case 0: // Chu Chu Island Set (Male)
                cGender = "Chu Chu Island Set (Male)";
                items = new int[] { 1005781, 1050583, 1103332, 1073534, 1703084 };
                break;
            case 1: // Chu Chu Island Set (Female)
                cGender = "Chu Chu Island Set (Female)";
                items = new int[] { 1005781, 1051656, 1103332, 1073534, 1703084 };
                break;
        }
        String itemString = "";
        for (int i : items) {
            if (GameConstants.isCap(i)) {
                itemString += "[หมวก] #i" + i + "# #z" + i + "#\r\n";
            }
            if (GameConstants.isOverall(i)) {
                itemString += "[ชุดคลุม] #i" + i + "# #z" + i + "#\r\n";
            }
            if (GameConstants.isCape(i)) {
                itemString += "[ผ้าคลุม] #i" + i + "# #z" + i + "#\r\n";
            }
            if (GameConstants.isShoes(i)) {
                itemString += "[รองเท้า] #i" + i + "# #z" + i + "#\r\n";
            }
            if (GameConstants.isWeapon(i)) {
                itemString += "[อาวุธ] #i" + i + "# #z" + i + "#";
            }
        }
        if (1 == self.askYesNo(
                "กรุณายืนยันชุดที่เลือกอีกครั้งครับ\r\n#b- เพศที่เลือก: " + cGender
                        + "\r\n- ตัวละครที่รับ: #h0##k\r\n\r\n"
                        + itemString)) {
            if (getPlayer().getInventory(MapleInventoryType.CASH_EQUIP).getNumFreeSlot() >= 5) {
                if (target.exchange(2633201, -1) > 0) {
                    MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
                    for (int i : items) {
                        Item rewardItem = ii.getEquipById(i);
                        Equip rewardEquip = null;
                        if (rewardItem != null) {
                            rewardEquip = (Equip) rewardItem;
                        }
                        if (rewardEquip != null) {
                            rewardEquip.setUniqueId(MapleInventoryIdentifier.getInstance());
                            rewardEquip.setExpiration(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 180L);
                            if (GameConstants.isCap(i) || GameConstants.isCape(i)) {
                                rewardEquip.setStr((short) 30);
                                rewardEquip.setDex((short) 30);
                                rewardEquip.setInt((short) 30);
                                rewardEquip.setLuk((short) 30);
                            }
                            if (GameConstants.isOverall(i)) {
                                rewardEquip.setWdef((short) 300);
                            }
                            if (GameConstants.isShoes(i)) {
                                rewardEquip.setSpeed((short) 50);
                                rewardEquip.setJump((short) 50);
                            }
                            if (GameConstants.isWeapon(i)) {
                                rewardEquip.setWatk((short) 30);
                                rewardEquip.setMatk((short) 30);
                            }
                            rewardEquip.setFlag((short) 32);
                            short TI = MapleInventoryManipulator.addbyItem(getClient(), rewardEquip, false);
                        }
                    }
                }
            } else {
                self.say("กรุณาทำช่องในกระเป๋าแฟชั่นให้ว่างอย่างน้อย 5 ช่องครับ");
            }
        }
    }

    public void consume_2632860() {
        if (getPlayer().getInventory(MapleInventoryType.CASH).getNumFreeSlot() >= 1) {
            if (target.exchange(2632860, -1) > 0) {
                if (!DBConfig.isGanglim) {
                    exchangePetPeriod(5002081, 60);
                } else {
                    exchangePetPeriod(5002197, 90);
                }
                self.sayOk("แลกเปลี่ยนเรียบร้อยแล้วครับ");
            }
        } else {
            self.say("กรุณาทำช่องในกระเป๋า Cash ให้ว่างอย่างน้อย 1 ช่องครับ");
        }
    }

    public void consume_2630782() {
        int[] rewards = new int[] {
                1212131, 1213030, 1214030, 1222124, 1232124, 1242144, 1242145, 1262053, 1272043, 1282043, 1292030,
                1302359, 1312215, 1322266,
                1332291, 1362151, 1372239, 1382276, 1402271, 1412191, 1422199, 1432229, 1442287, 1452269, 1462254,
                1472277, 1482234, 1492247, 1522154, 1532159, 1582046, 1592037
        };
        String v0 = "สามารถเลือกรับ #bอาวุธ Arcane Shade 1 ชิ้น#k จากรายการต่อไปนี้ครับ\r\nอาวุธที่เลือกจะได้รับแบบ #b15 Star, Legendary Potential, และ Bonus Stat#k\r\n\r\nเลือกอาวุธที่ต้องการได้เลยครับ#b\r\n\r\n";
        for (int i = 0; i < rewards.length; ++i) {
            int itemID = rewards[i];
            v0 += "#L" + i + "##i" + itemID + "# #z" + itemID + "##l\r\n";
        }

        int v1 = self.askMenu(v0, ScriptMessageFlag.NpcReplacedByNpc);
        if (v1 >= rewards.length) {
            return; // TODO: Hack
        }
        int itemID = rewards[v1];
        if (self.askYesNo(
                "อาวุธที่เลือกคือ #b#i" + itemID + "# #z" + itemID
                        + "##k ครับ\r\nต้องการเลือกอาวุธนี้ใช่ไหมครับ?\r\n\r\n#b(หากกด #eใช่#n จะได้รับไอเทมทันที)",
                ScriptMessageFlag.NpcReplacedByNpc) > 0) {
            if (getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 1) {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Equip#k แล้วลองใหม่นะครับ");
            } else {
                if (target.exchange(2630782, -1) == 1) {
                    exchangeEquipCHUCWithScroll(itemID, 15, 2);
                    self.say("มอบให้เรียบร้อยแล้วครับ ลองเช็คในกระเป๋าดูนะครับ!");
                }
            }
        }
    }

    public void consume_2632861() {
        int[] rewards = new int[] {
                1004808, 1004809, 1004810, 1004811, 1004812,
                1102940, 1102941, 1102942, 1102943, 1102944,
                1082695, 1082696, 1082697, 1082698, 1082699,
                1053063, 1053064, 1053065, 1053066, 1053067,
                1073158, 1073159, 1073160, 1073161, 1073162,
                1152196, 1152197, 1152198, 1152199, 1152200
        };
        String v0 = "สามารถเลือกรับ #bชุดป้องกัน Arcane Shade 1 ชิ้น#k จากรายการต่อไปนี้ครับ\r\nกรุณาเลือกชุดที่ต้องการได้เลยครับ#b\r\n\r\n";
        for (int i = 0; i < rewards.length; ++i) {
            int itemID = rewards[i];
            v0 += "#L" + i + "##i" + itemID + "# #z" + itemID + "##l\r\n";
        }

        int v1 = self.askMenu(v0, ScriptMessageFlag.NpcReplacedByNpc);
        if (v1 >= rewards.length) {
            return; // TODO: Hack
        }
        int itemID = rewards[v1];
        if (self.askYesNo(
                "ชุดป้องกันที่เลือกคือ #b#i" + itemID + "# #z" + itemID
                        + "##k ครับ\r\nต้องการเลือกชุดนี้ใช่ไหมครับ?\r\n\r\n#b(หากกด #eใช่#n จะได้รับไอเทมทันที)",
                ScriptMessageFlag.NpcReplacedByNpc) > 0) {
            if (target.exchange(2632861, -1, itemID, 1) == 1) {
                self.say("มอบให้เรียบร้อยแล้วครับ ลองเช็คในกระเป๋าดูนะครับ!");
            } else {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Equip#k แล้วลองใหม่นะครับ");
            }
        }
    }

    public void consume_2630133() {
        int[] rewards = new int[] {
                1212120, 1213018, 1214018, 1222113, 1232113, 1242121, 1242122, 1262039, 1272017, 1282017,
                1292018, 1302343, 1312203, 1322255, 1332279, 1342104, 1362140, 1372228, 1382265, 1402259,
                1412181, 1422189, 1432218, 1442274, 1452257, 1462243, 1472265, 1482221, 1492235, 1522143, 1532150,
                1582023, 4310217
        };
        String v0 = "สามารถสุ่มรับ #bอาวุธ Arcane Shade 1 ชิ้น#k ได้ครับ\r\nต้องการเปิดกล่องเลยไหมครับ?#b\r\n\r\n";
        for (int i = 0; i < rewards.length; ++i) {
            int itemID = rewards[i];
            v0 += "#i" + itemID + "# #z" + itemID + "##l\r\n";
        }

        if (self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByNpc) > 0) {
            int v1 = Randomizer.rand(0, rewards.length - 1);
            if (v1 >= rewards.length) {
                return; // TODO: Hack
            }
            int itemID = rewards[v1];
            if (target.exchange(2630133, -1, itemID, 1) == 1) {
                self.say("ได้รับ #b#i" + itemID + "# #z" + itemID + "##k() จากกล่องแล้วครับ ลองเช็คในกระเป๋าดูนะครับ");
            } else {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Equip#k แล้วลองใหม่นะครับ");
            }
        }
    }

    public void consume_2630291() {
        int[] rewards = new int[] {
                1212115, 1213017, 1214017, 1222109, 1232109, 1242116, 1242123, 1262017, 1272016, 1282016, 1292017,
                1302333, 1312199, 1322250, 1332274, 1342101, 1362135, 1372222, 1382259, 1402251, 1412177, 1422184,
                1432214, 1442268, 1452252, 1462239, 1472261, 1482216, 1492231, 1522138, 1532144, 1582017, 1592019,
                4310216
        };
        String v0 = "สามารถเลือกรับ #bอาวุธ Absolab 1 ชิ้น#k จากรายการต่อไปนี้ครับ\r\nกรุณาเลือกอาวุธที่ต้องการได้เลยครับ#b\r\n\r\n";
        for (int i = 0; i < rewards.length; ++i) {
            int itemID = rewards[i];
            v0 += "#L" + i + "##i" + itemID + "# #z" + itemID + "##l\r\n";
        }

        int v1 = self.askMenu(v0, ScriptMessageFlag.NpcReplacedByNpc);
        if (v1 >= rewards.length) {
            return; // TODO: Hack
        }
        int itemID = rewards[v1];
        if (self.askYesNo(
                "อาวุธที่เลือกคือ #b#i" + itemID + "# #z" + itemID
                        + "##k ครับ\r\nต้องการเลือกอาวุธนี้ใช่ไหมครับ?\r\n\r\n#b(หากกด #eใช#n จะได้รับไอเทมทันที)",
                ScriptMessageFlag.NpcReplacedByNpc) > 0) {
            if (target.exchange(2630291, -1, itemID, 1) == 1) {
                self.say("มอบให้เรียบร้อยแล้วครับ ลองเช็คในกระเป๋าดูนะครับ!");
            } else {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Equip#k แล้วลองใหม่นะครับ");
            }
        }
    }

    public void consume_2630704() {
        int[] rewards = new int[] {
                1004422, 1004423, 1004424, 1004425, 1004426,
                1102775, 1102794, 1102795, 1102796, 1102797,
                1082636, 1082637, 1082638, 1082639, 1082640,
                1052882, 1052887, 1052888, 1052889, 1052890,
                1073030, 1073032, 1073033, 1073034, 1073035,
                1152174, 1152176, 1152177, 1152178, 1152179
        };
        String v0 = "สามารถเลือกรับ #bชุดป้องกัน Absolab 1 ชิ้น#k จากรายการต่อไปนี้ครับ\r\nกรุณาเลือกชุดที่ต้องการได้เลยครับ#b\r\n\r\n";
        for (int i = 0; i < rewards.length; ++i) {
            int itemID = rewards[i];
            v0 += "#L" + i + "##i" + itemID + "# #z" + itemID + "##l\r\n";
        }

        int v1 = self.askMenu(v0, ScriptMessageFlag.NpcReplacedByNpc);
        if (v1 >= rewards.length) {
            return; // TODO: Hack
        }
        int itemID = rewards[v1];
        if (self.askYesNo(
                "ชุดป้องกันที่เลือกคือ #b#i" + itemID + "# #z" + itemID
                        + "##k ครับ\r\nต้องการเลือกชุดนี้ใช่ไหมครับ?\r\n\r\n#b(หากกด #eใช่#n จะได้รับไอเทมทันที)",
                ScriptMessageFlag.NpcReplacedByNpc) > 0) {
            if (target.exchange(2630704, -1, itemID, 1) == 1) {
                self.say("มอบให้เรียบร้อยแล้วครับ ลองเช็คในกระเป๋าดูนะครับ!");
            } else {
                self.say("กรุณาทำช่องว่างใน #bกระเป๋า Equip#k แล้วลองใหม่นะครับ");
            }
        }
    }

    public void consume_2632789() { // Eternal Flame Ring Exchange Coupon
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 1) {
            self.sayOk("ช่องเก็บอุปกรณ์ไม่เพียงพอ");
            return;
        }
        if (target.exchange(2632789, -1) > 0) {
            exchangeUniqueItem(1114324);
            self.sayOk("ได้รับ Eternal Flame Ring (Unique) แล้วครับ");
        }
    }

    public void consume_2435484() { // Coupon
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (target.exchange(2435484, -1) > 0) {
            int addPoint = 10000;
            int point = getPlayer().getEnchantPoint();
            getPlayer().setEnchantPoint(point + addPoint);
            self.sayOk(
                    "ได้รับคะแนนเสริมพลัง #e#r" + addPoint + "#n#k คะแนนเรียบร้อยแล้วครับ\r\nคะแนน : " + point + " → "
                            + (point + addPoint));
        }
    }

    public void consume_2437090() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() >= 1) {
            if (target.exchange(2437090, -1) > 0) {
                exchangeSupportEquipPeriod(1122017, 0, 0, 14);
                self.sayOk("แลกเปลี่ยนเรียบร้อยแล้วครับ");
            }
        } else {
            self.sayOk("ช่องเก็บอุปกรณ์ไม่เพียงพอ");
        }
    }

    public void consume_2633590() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (getPlayer().getLevel() < 200) {
            self.sayOk("ต้องมีเลเวล 200 ขึ้นไปจึงจะใช้ได้ครับ");
            return;
        }
        List<Item> symbols = new ArrayList<>();
        for (Item item : getPlayer().getInventory(MapleInventoryType.EQUIPPED).list()) {
            if (GameConstants.isArcaneSymbol(item.getItemId())) {
                symbols.add(item);
            }
        }
        if (symbols.isEmpty()) {
            self.sayOk("ไม่มี Arcane Symbol ที่สวมใส่อยู่ครับ");
            return;
        }
        Equip selected = null;
        String string = "สวัสดีครับ\r\n\r\nผมคือ NPC รับผิดชอบ Arcane Symbol Quick Pass\r\nกรุณาเลือก Arcane Symbol ที่ต้องการอัปเลเวลสูงสุดครับ!\r\n\r\n #r※ หมายเหตุ ※ \r\nSymbol ที่เลือกจะถูกอัปเป็นเลเวลสูงสุดทันทีครับ#k#b";

        for (int i = 0; i < symbols.size(); i++) {
            string += "\r\n#L" + i + "# #i" + symbols.get(i).getItemId() + ":# #z" + symbols.get(i).getItemId()
                    + ":# #l";
        }
        int v = self.askMenu(string);
        if (v >= 0) {
            selected = (Equip) symbols.get(v);
        }
        if (selected != null) {
            if (selected.getArcLevel() >= 20) {
                self.sayOk("Symbol ที่เลือกเลเวลเต็มแล้วครับ");
                return;
            }
            // updateArcaneSymbol
            if (target.exchange(2633590, -1) > 0) {
                int level = selected.getArcLevel();
                int up = 20 - level;
                selected.setArcEXP(0);
                selected.setArcLevel(20);
                selected.setArc((short) (10 * (selected.getArcLevel() + 2)));
                if (getPlayer().getJob() >= 100 && getPlayer().getJob() < 200 || getPlayer().getJob() == 512
                        || getPlayer().getJob() == 1512 || getPlayer().getJob() == 2512
                        || getPlayer().getJob() >= 1100 && getPlayer().getJob() < 1200
                        || GameConstants.isAran(getPlayer().getJob()) || GameConstants.isBlaster(getPlayer().getJob())
                        || GameConstants.isDemonSlayer(getPlayer().getJob())
                        || GameConstants.isMichael(getPlayer().getJob()) || GameConstants.isKaiser(getPlayer().getJob())
                        || GameConstants.isZero(getPlayer().getJob()) || GameConstants.isArk(getPlayer().getJob())
                        || GameConstants.isAdele(getPlayer().getJob())) {
                    selected.setStr((short) (selected.getStr() + (100 * up)));
                } else if (getPlayer().getJob() >= 200 && getPlayer().getJob() < 300
                        || GameConstants.isFlameWizard(getPlayer().getJob())
                        || GameConstants.isEvan(getPlayer().getJob()) || GameConstants.isLuminous(getPlayer().getJob())
                        || getPlayer().getJob() >= 3200 && getPlayer().getJob() < 3300
                        || GameConstants.isKinesis(getPlayer().getJob()) || GameConstants.isIllium(getPlayer().getJob())
                        || GameConstants.isLara(getPlayer().getJob())) {
                    selected.setInt((short) (selected.getInt() + (100 * up)));
                } else if (GameConstants.isKain(getPlayer().getJob())
                        || getPlayer().getJob() >= 300 && getPlayer().getJob() < 400 || getPlayer().getJob() == 522
                        || getPlayer().getJob() == 532 || GameConstants.isMechanic(getPlayer().getJob())
                        || GameConstants.isAngelicBuster(getPlayer().getJob())
                        || getPlayer().getJob() >= 1300 && getPlayer().getJob() < 1400
                        || GameConstants.isMercedes(getPlayer().getJob())
                        || getPlayer().getJob() >= 3300 && getPlayer().getJob() < 3400) {
                    selected.setDex((short) (selected.getDex() + (100 * up)));
                } else if (getPlayer().getJob() >= 400 && getPlayer().getJob() < 500
                        || getPlayer().getJob() >= 1400 && getPlayer().getJob() < 1500
                        || GameConstants.isPhantom(getPlayer().getJob()) || GameConstants.isKadena(getPlayer().getJob())
                        || GameConstants.isHoyoung(getPlayer().getJob())) {
                    selected.setLuk((short) (selected.getLuk() + (100 * up)));
                } else if (GameConstants.isDemonAvenger(getPlayer().getJob())) {
                    selected.setHp((short) (selected.getHp() + (140 * up)));
                } else if (GameConstants.isXenon(getPlayer().getJob())) {
                    selected.setStr((short) (selected.getStr() + (39 * up)));
                    selected.setDex((short) (selected.getDex() + (39 * up)));
                    selected.setLuk((short) (selected.getLuk() + (39 * up)));
                }
                getPlayer().send(CWvsContext.InventoryPacket.updateArcaneSymbol(selected));
                self.sayOk("Arcane Symbol ที่เลือกถูกอัปเป็นเลเวลสูงสุดเรียบร้อยแล้วครับ");
            } else {
                self.sayOk("เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุครับ");
            }
        }
    }

    public void consume_2430503() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (getPlayer().getLevel() < 200) {
            self.sayOk("ต้องมีเลเวล 200 ขึ้นไปจึงจะใช้ได้ครับ");
            return;
        }
        List<Item> symbols = new ArrayList<>();
        for (Item item : getPlayer().getInventory(MapleInventoryType.EQUIPPED).list()) {
            if (GameConstants.isArcaneSymbol(item.getItemId())) {
                symbols.add(item);
            }
        }
        self.say(
                "สวัสดีครับ! ผมคือผู้ดูแล Arcane ที่จะช่วยอัปเลเวล Arcane Symbol ของคุณให้เต็มทันที! คุณสามารถตรวจสอบไอเทมที่มีผลเหมือนกันได้ที่ #eร้านค้า Ganglim Credit และ Point#n ขอให้ใช้บริการเยอะๆ นะครับ");
        if (symbols.isEmpty()) {
            self.sayOk("ไม่มี Arcane Symbol ที่สวมใส่อยู่ครับ");
            return;
        }
        Equip selected = null;
        String string = "กรุณาเลือก Arcane Symbol ที่ต้องการอัปเป็นเลเวลสูงสุดครับ!\r\n\r\n #r※ หมายเหตุ ※ \r\nเลเวลของ Symbol ที่เลือกจะถูกอัปเป็นเลเวลสูงสุดทันทีครับ#k#b";

        for (int i = 0; i < symbols.size(); i++) {
            string += "\r\n#L" + i + "# #i" + symbols.get(i).getItemId() + ":# #z" + symbols.get(i).getItemId()
                    + ":# #l";
        }
        int v = self.askMenu(string);
        if (v >= 0) {
            selected = (Equip) symbols.get(v);
        }
        if (selected != null) {
            if (selected.getArcLevel() >= 20) {
                self.sayOk("Symbol ที่เลือกเลเวลเต็มแล้วครับ");
                return;
            }
            // updateArcaneSymbol
            if (target.exchange(2430503, -1) > 0) {
                int level = selected.getArcLevel();
                int up = 20 - level;
                selected.setArcEXP(0);
                selected.setArcLevel(20);
                selected.setArc((short) (10 * (selected.getArcLevel() + 2)));
                if (getPlayer().getJob() >= 100 && getPlayer().getJob() < 200 || getPlayer().getJob() == 512
                        || getPlayer().getJob() == 1512 || getPlayer().getJob() == 2512
                        || getPlayer().getJob() >= 1100 && getPlayer().getJob() < 1200
                        || GameConstants.isAran(getPlayer().getJob()) || GameConstants.isBlaster(getPlayer().getJob())
                        || GameConstants.isDemonSlayer(getPlayer().getJob())
                        || GameConstants.isMichael(getPlayer().getJob()) || GameConstants.isKaiser(getPlayer().getJob())
                        || GameConstants.isZero(getPlayer().getJob()) || GameConstants.isArk(getPlayer().getJob())
                        || GameConstants.isAdele(getPlayer().getJob())) {
                    selected.setStr((short) (selected.getStr() + (100 * up)));
                } else if (getPlayer().getJob() >= 200 && getPlayer().getJob() < 300
                        || GameConstants.isFlameWizard(getPlayer().getJob())
                        || GameConstants.isEvan(getPlayer().getJob()) || GameConstants.isLuminous(getPlayer().getJob())
                        || getPlayer().getJob() >= 3200 && getPlayer().getJob() < 3300
                        || GameConstants.isKinesis(getPlayer().getJob()) || GameConstants.isIllium(getPlayer().getJob())
                        || GameConstants.isLara(getPlayer().getJob())) {
                    selected.setInt((short) (selected.getInt() + (100 * up)));
                } else if (GameConstants.isKain(getPlayer().getJob())
                        || getPlayer().getJob() >= 300 && getPlayer().getJob() < 400 || getPlayer().getJob() == 522
                        || getPlayer().getJob() == 532 || GameConstants.isMechanic(getPlayer().getJob())
                        || GameConstants.isAngelicBuster(getPlayer().getJob())
                        || getPlayer().getJob() >= 1300 && getPlayer().getJob() < 1400
                        || GameConstants.isMercedes(getPlayer().getJob())
                        || getPlayer().getJob() >= 3300 && getPlayer().getJob() < 3400) {
                    selected.setDex((short) (selected.getDex() + (100 * up)));
                } else if (getPlayer().getJob() >= 400 && getPlayer().getJob() < 500
                        || getPlayer().getJob() >= 1400 && getPlayer().getJob() < 1500
                        || GameConstants.isPhantom(getPlayer().getJob()) || GameConstants.isKadena(getPlayer().getJob())
                        || GameConstants.isHoyoung(getPlayer().getJob())) {
                    selected.setLuk((short) (selected.getLuk() + (100 * up)));
                } else if (GameConstants.isDemonAvenger(getPlayer().getJob())) {
                    selected.setHp((short) (selected.getHp() + (210 * up)));
                } else if (GameConstants.isXenon(getPlayer().getJob())) {
                    selected.setStr((short) (selected.getStr() + (48 * up)));
                    selected.setDex((short) (selected.getDex() + (48 * up)));
                    selected.setLuk((short) (selected.getLuk() + (48 * up)));
                }
                getPlayer().send(CWvsContext.InventoryPacket.updateArcaneSymbol(selected));
                self.sayOk("Arcane Symbol ที่เลือกถูกอัปเป็นเลเวลสูงสุดเรียบร้อยแล้วครับ");
            } else {
                self.sayOk("เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุครับ");
            }
        }
    }

    public void consume_2430504() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (getPlayer().getLevel() < 225) {
            self.sayOk("ต้องมีเลเวล 225 ขึ้นไปจึงจะใช้ได้ครับ");
            return;
        }
        if (target.exchange(2430504, -1, 1712004, 1, 1712005, 1, 1712006, 1) > 0) {
            getPlayer().gainMeso(50000000, false);
            self.say(
                    "[Arcane Symbol : Arcana] [Arcane Symbol : Morass] [Arcane Symbol : Esfera] [50,000,000 Meso] ได้รับไอเทมเรียบร้อยแล้วครับ");
        } else {
            self.say("ช่องเก็บอุปกรณ์ไม่เพียงพอ ต้องการช่องว่าง 3 ช่อง");
        }
    }

    public void consume_2439527() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        int[] armor = new int[] { 1004808, 1102940, 1082695, 1053063, 1073158, 1152196 };
        int[][] weapon = new int[][] {
                { 1213018, 1232113, 1302343, 1312203, 1322255, 1402259, 1412181, 1422189, 1432218, 1442274, 1582023 },
                { 1212120, 1262039, 1282017, 1372228, 1382265 },
                { 1214018, 1452257, 1462243, 1522143, 1592020 },
                { 1242122, 1272017, 1292018, 1332279, 1342104, 1362140, 1472265 },
                { 1222113, 1242121, 1482221, 1492235, 1532150 } };
        int v = self.askMenu(
                "สวัสดีครับ~! Ganglim Maple ครับ\r\nขอมอบ #rอาวุธและชุดป้องกัน#k ให้ท่านผู้กล้าที่ยอดเยี่ยม เพื่อให้แข็งแกร่งยิ่งขึ้น~! นอกจากชุดป้องกันที่เตรียมไว้แล้ว ยังมีไอเทมโบนัสเพิ่มเติมด้วย ห้ามพลาดนะครับ!\r\n\r\n"
                        +
                        "#b#L0#รับไอเทม Warriors\r\n" +
                        "#L1#รับไอเทม Magicians\r\n" +
                        "#L2#รับไอเทม Bowmen\r\n" +
                        "#L3#รับไอเทม Thieves\r\n" +
                        "#L4#รับไอเทม Pirates\r\n");
        if (v > 4) {
            self.sayOk("คำขอไม่ถูกต้อง");
            return;
        }
        String test = "ชุดป้องกันที่จะได้รับครับ\r\n#b";
        for (int a : armor) {
            int itemID = (a + v);
            test += "#i" + itemID + "# #z" + itemID + "#\r\n";
        }
        test += "#L0# #r#eเลือกอาวุธ#l";
        int vv = self.askMenu(test);
        if (vv == 0) {
            String wTest = "รายการอาวุธ#b\r\n";
            int index = 0;
            for (int a : weapon[v]) {
                wTest += "#L" + index + "#" + "#i" + (a) + "# #z" + (a) + "#\r\n";
                index++;
            }
            int vvv = self.askMenu(wTest);
            if (vvv >= 0 && weapon[v].length >= vvv) {
                String all = "กรุณายืนยันไอเทมที่เลือกครับ!#b\r\n";
                for (int a : armor) {
                    all += "#i" + (a + v) + "# #z" + (a + v) + "#\r\n";
                }
                all += "#i" + weapon[v][vvv] + "# #z" + weapon[v][vvv] + "#\r\n";
                if (1 == self.askYesNo(all)) {
                    if (1 == self.askYesNo("ยืนยันครั้งสุดท้ายอีกครั้งครับ!\r\n" + all)) {
                        if (getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 6
                                || getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 4
                                || getPlayer().getInventory(MapleInventoryType.CASH).getNumFreeSlot() < 1) {
                            self.sayOk("ช่องว่างในกระเป๋า Equip, Use, Cash ไม่เพียงพอครับ");
                            return;
                        }
                        if (target.exchange(2439527, -1, 2630437, 300, 2434290, 100, 2048757, 100, 2000054, 1, 5044006,
                                1) > 0) {
                            if (weapon[v][vvv] == 1232113) {
                                for (int a : armor) {
                                    int itemID = (a + v);
                                    exchangeEquipCHUCADDSTATBONUSEDDITIONALArmor(itemID, 25, 2, 14, 1);
                                }
                            } else {
                                for (int a : armor) {
                                    int itemID = (a + v);
                                    exchangeEquipCHUCADDSTATBONUSEDDITIONALArmor(itemID, 25, 2, 14, 0);
                                }
                            }
                            if (v != 1) {
                                exchangeEquipCHUCADDSTATBONUSEDDITIONALArmor(weapon[v][vvv], 25, 2, 14, 3);
                            } else {
                                exchangeEquipCHUCADDSTATBONUSEDDITIONALArmor(weapon[v][vvv], 25, 2, 14, 4);
                            }
                            getPlayer().forceCompleteQuest(6500); // Pocket Unlock
                        }
                        self.sayOk("เปิดช่อง Pocket และมอบไอเทมต่างๆ ให้เรียบร้อยแล้วครับ");
                    } else {
                        self.sayOk("โปรดตัดสินใจให้ดีก่อนเลือกนะครับ~!");
                    }
                } else {
                    self.sayOk("โปรดตัดสินใจให้ดีก่อนเลือกนะครับ~!");
                }
            } else {
                self.sayOk("คำขอไม่ถูกต้อง");
            }
        }
    }

    public void consume_2430497() { // Cute Newbie Support Box
        initNPC(MapleLifeFactory.getNPC(9010000));
        int[] armor = new int[] { 1004229, 1102718, 1082608, 1052799, 1072967, 1152108 };
        int[][] weapon = new int[][] {
                { 1213014, 1232095, 1302315, 1312185, 1322236, 1402236, 1412164, 1422171, 1432200, 1442254, 1582011 },
                { 1212101, 1262011, 1282013, 1372207, 1382245 },
                { 1214014, 1452238, 1462225, 1522124, 1592016 },
                { 1242102, 1272013, 1292014, 1332260, 1342100, 1362121, 1472247 },
                { 1222095, 1242133, 1482202, 1492212, 1532130 } };
        int v = self.askMenu(
                "สวัสดีครับ~! Ganglim Maple ครับ\r\nขอมอบ #rอาวุธและชุดป้องกัน#k ให้ท่านผู้กล้าที่ยอดเยี่ยม เพื่อให้แข็งแกร่งยิ่งขึ้น~! นอกจากชุดป้องกันที่เตรียมไว้แล้ว ยังมีไอเทมโบนัสเพิ่มเติมด้วย ห้ามพลาดนะครับ!\r\n\r\n"
                        +
                        "#b#L0#รับไอเทม Warriors\r\n" +
                        "#L1#รับไอเทม Magicians\r\n" +
                        "#L2#รับไอเทม Bowmen\r\n" +
                        "#L3#รับไอเทม Thieves\r\n" +
                        "#L4#รับไอเทม Pirates\r\n");
        if (v > 4) {
            self.sayOk("คำขอไม่ถูกต้อง");
            return;
        }
        String test = "ชุดป้องกันที่จะได้รับครับ\r\n#b";
        for (int a : armor) {
            int itemID = (a + v);
            if (a + v > 1152108) {
                itemID += 1;
            }
            test += "#i" + itemID + "# #z" + itemID + "#\r\n";
        }
        test += "#L0# #r#eเลือกอาวุธ#l";
        int vv = self.askMenu(test);
        if (vv == 0) {
            String wTest = "รายการอาวุธ#b\r\n";
            int index = 0;
            for (int a : weapon[v]) {
                wTest += "#L" + index + "#" + "#i" + (a) + "# #z" + (a) + "#\r\n";
                index++;
            }
            int vvv = self.askMenu(wTest);
            if (vvv >= 0 && weapon[v].length >= vvv) {
                String all = "กรุณายืนยันไอเทมที่เลือกครับ!#b\r\n";
                for (int a : armor) {
                    int itemID = (a + v);
                    if (a + v > 1152108) {
                        itemID += 1;
                    }
                    all += "#i" + itemID + "# #z" + itemID + "#\r\n";
                }
                all += "#i" + weapon[v][vvv] + "# #z" + weapon[v][vvv] + "#\r\n";
                if (1 == self.askYesNo(all)) {
                    if (1 == self.askYesNo("ยืนยันครั้งสุดท้ายอีกครั้งครับ!\r\n" + all)) {
                        if (getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 11 ||
                                getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 13 ||
                                getPlayer().getInventory(MapleInventoryType.CASH).getNumFreeSlot() < 1 ||
                                getPlayer().getInventory(MapleInventoryType.SETUP).getNumFreeSlot() < 2) {
                            self.sayOk("ช่องว่างในกระเป๋า Equip, Use, Setup, Cash ไม่เพียงพอครับ");
                            return;
                        }
                        if (target.exchange(2430497, -1, 2630437, 100, 2048757, 50, 2000054, 1, 5044006, 1, 1712001, 1,
                                1712002, 1, 1712003, 1, 3014005, 1, 3014028, 1, 2439580, 1, 2439581, 1, 2439582, 1,
                                2439583, 1, 2439584, 1, 2435122, 3, 2430503, 1, 2430504, 1) > 0) {
                            if (weapon[v][vvv] == 1232095) { // Desperado
                                for (int a : armor) {
                                    int itemID = (a + v);
                                    if (a + v > 1152108) {
                                        itemID += 1;
                                    }
                                    exchangeEquipCHUCADDSTATBONUSEDDITIONALArmor(itemID, 17, 2, 14, 1);
                                }
                            } else {
                                for (int a : armor) {
                                    int itemID = (a + v);
                                    if (a + v > 1152108) {
                                        itemID += 1;
                                    }
                                    exchangeEquipCHUCADDSTATBONUSEDDITIONALArmor(itemID, 17, 2, 14, 0);
                                }
                            }
                            if (v != 1) {
                                exchangeEquipCHUCADDSTATBONUSEDDITIONALArmor(weapon[v][vvv], 17, 2, 14, 3);
                            } else {
                                exchangeEquipCHUCADDSTATBONUSEDDITIONALArmor(weapon[v][vvv], 17, 2, 14, 4);
                            }
                            getPlayer().gainMeso(5000000, true);
                            getPlayer().forceCompleteQuest(6500); // Pocket Unlock
                            getPlayer().changeSkillLevel(80001825, 30, 30);
                            getPlayer().changeSkillLevel(80001829, 5, 5);
                        }
                        self.sayOk("[เปิดช่อง Pocket] [มอบสกิล] [มอบไอเทม] [5,000,000 Meso] มอบให้เรียบร้อยแล้วครับ");
                    } else {
                        self.sayOk("โปรดตัดสินใจให้ดีก่อนเลือกนะครับ~!");
                    }
                } else {
                    self.sayOk("โปรดตัดสินใจให้ดีก่อนเลือกนะครับ~!");
                }
            } else {
                self.sayOk("คำขอไม่ถูกต้อง");
            }
        }
    }

    public void consume_2435122() {
        incDamageSkinSlot();
    }

    public void consume_2435513() {
        incDamageSkinSlot();
    }

    public void consume_2436784() {
        incDamageSkinSlot();
    }

    public void consume_2439631() {
        incDamageSkinSlot();
    }

    public void incDamageSkinSlot() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (increaseDamageSkinSlotCount(1)) {
            target.exchange(itemID, -1);
            getPlayer().dropMessage(5, "ขยายช่องบันทึก Damage Skin เรียบร้อยแล้วครับ");
        } else {
            getPlayer().dropMessage(5, "ไม่สามารถขยายช่องบันทึก Damage Skin ได้อีกแล้วครับ");
        }
    }

    public boolean increaseDamageSkinSlotCount(int add) {
        if (getPlayer().getDamageSkinSaveInfo().getSlotCount() >= 48) {
            return false;
        }
        getPlayer().getDamageSkinSaveInfo().addSlotCount(add);
        getPlayer().setSaveFlag(getPlayer().getSaveFlag() | CharacterSaveFlag.DAMAGE_SKIN_SAVE.getFlag());

        getPlayer().updateOneInfo(13190, "slotCount",
                String.valueOf(getPlayer().getDamageSkinSaveInfo().getSlotCount()));
        return true;
    }

    public void consume_2633597() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (target.exchange(itemID, -1, 1662175, 1, 1672085, 1) > 0) {
            self.sayOk("แลกเปลี่ยนเรียบร้อยแล้วครับ ลองเช็คในช่องอุปกรณ์ดูนะครับ");
        } else {
            self.sayOk("ช่องเก็บของไม่เพียงพอ");
        }
    }

    public void createRecoveryQex() {
        Table mainTable = new Table(getPlayer().getName() + "_qex");

        // Create data file with sql file at restore time
        for (Map.Entry<Integer, QuestEx> entry : getPlayer().getQuestInfos().entrySet()) {
            Table table = new Table(String.valueOf(entry.getKey()));
            table.put("questID", String.valueOf(entry.getKey()));
            table.put("data", entry.getValue().getData());
            table.put("date", entry.getValue().getTime());

            mainTable.putChild(table);
        }
        try {
            mainTable.save(Paths.get(getPlayer().getName() + "_qex.data"));
        } catch (Exception e) {
        }
    }

    public void recoveryQex() {
        // Restore using restore data

        try {
            Table table = objects.utils.Properties.loadTable("./", "qex.data");
            for (Table child : table.list()) {
                for (Table c : child.list()) {
                    int questID = Integer.parseInt(c.getProperty("questID"));
                    String data = c.getProperty("data");
                    String time = c.getProperty("date");

                    getPlayer().getInfoQuest_Map().put(questID, new QuestEx(questID, data, time));
                    getPlayer().setSaveFlag(getPlayer().getSaveFlag() | CharacterSaveFlag.QUEST_INFO.getFlag());
                    getPlayer().send(CWvsContext.InfoPacket.updateInfoQuest(questID, data));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        getPlayer().dropMessage(5, "กู้คืนข้อมูลเควสต์ทั้งหมดเรียบร้อยแล้ว กรุณาเข้าเกมใหม่");
        getPlayer().dropMessage(1, "กู้คืนข้อมูลเควสต์ทั้งหมดเรียบร้อยแล้ว กรุณาเข้าเกมใหม่");
    }

    public void consume_2433482() {
        if (DBConfig.isGanglim) {
            if (getPlayer().getClient().isGm()) {
                /*
                 * try (Connection con = DBConnection.getConnection()) {
                 * List<Pair<Item, MapleInventoryType>> itemlist = new ArrayList<>();
                 * List<AuctionItemPackage> items = new ArrayList<>();
                 * for (int i = 0; i < 100000; ++i) {
                 * AuctionItemPackage aip = Center.Auction.getItem(i);
                 * if (aip != null) {
                 * items.add(aip);
                 * }
                 * }
                 * getPlayer().dropMessage(5, "Number of auction house data to save : " +
                 * items.size());
                 * for (AuctionItemPackage aitem : new ArrayList<>(items)) {
                 * itemlist.add(new Pair<>(aitem.getItem(),
                 * GameConstants.getInventoryType(aitem.getItem().getItemId())));
                 * }
                 * saveItems(itemlist, con, -1, items);
                 * getPlayer().dropMessage(5, "Phew.. Save complete");
                 * } catch (SQLException e) {
                 * e.printStackTrace();
                 * }
                 */
            }
            /*
             * if (getPlayer().getClient().isGm()) {
             * for (GameServer cs : GameServer.getAllInstances()) {
             * for (Field map : cs.getMapFactory().getAllMaps()) {
             * for (MapleCharacter chr : new ArrayList<>(map.getCharacters())) {
             * if (chr != null) {
             * chr.getClient().removeKeyValue("HgradeWeek");
             * }
             * }
             * }
             * }
             * 
             * for (MapleCharacter chr :
             * AuctionServer.getPlayerStorage().getAllCharacters()) {
             * if (chr != null) {
             * chr.getClient().removeKeyValue("HgradeWeek");
             * }
             * }
             * 
             * for (GameServer cs : GameServer.getAllInstances()) {
             * for (Field map : cs.getMapFactory().getAllMaps()) {
             * for (MapleCharacter chr : new ArrayList<>(map.getCharacters())) {
             * if (chr != null) {
             * chr.getClient().removeKeyValue("HgradeWeek");
             * }
             * }
             * }
             * }
             * 
             * for (MapleCharacter chr :
             * CashShopServer.getPlayerStorage().getAllCharacters()) {
             * if (chr != null) {
             * chr.getClient().removeKeyValue("HgradeWeek");
             * }
             * }
             * }
             */
            return;
        } else {
            /*
             * if (getPlayer().getClient().isGm()) {
             * SimpleDateFormat lastDate = new SimpleDateFormat("yy/MM/dd");
             * for (GameServer cs : GameServer.getAllInstances()) {
             * for (Field map : cs.getMapFactory().getAllMaps()) {
             * for (MapleCharacter chr : new ArrayList<>(map.getCharacters())) {
             * if (chr != null) {
             * chr.updateOneInfo(1234699, "count", "0");
             * chr.updateOneInfo(1234699, "complete", "0");
             * chr.updateOneInfo(1234699, "day", "0");
             * chr.updateOneInfo(1234699, "dailyGiftCT", "0");
             * chr.updateOneInfo(1234699, "passCount", "0");
             * chr.updateOneInfo(1234699, "bMaxDay", "126");
             * chr.updateOneInfo(1234699, "lastDate", lastDate.format(new Date()));
             * chr.updateOneInfo(1234699, "cMaxDay", "126");
             * }
             * }
             * }
             * }
             * 
             * for (MapleCharacter chr :
             * AuctionServer.getPlayerStorage().getAllCharacters()) {
             * if (chr != null) {
             * chr.updateOneInfo(1234699, "count", "0");
             * chr.updateOneInfo(1234699, "complete", "0");
             * chr.updateOneInfo(1234699, "day", "0");
             * chr.updateOneInfo(1234699, "dailyGiftCT", "0");
             * chr.updateOneInfo(1234699, "passCount", "0");
             * chr.updateOneInfo(1234699, "bMaxDay", "126");
             * chr.updateOneInfo(1234699, "lastDate", lastDate.format(new Date()));
             * chr.updateOneInfo(1234699, "cMaxDay", "126");
             * }
             * }
             * 
             * for (MapleCharacter chr :
             * CashShopServer.getPlayerStorage().getAllCharacters()) {
             * if (chr != null) {
             * chr.updateOneInfo(1234699, "count", "0");
             * chr.updateOneInfo(1234699, "complete", "0");
             * chr.updateOneInfo(1234699, "day", "0");
             * chr.updateOneInfo(1234699, "dailyGiftCT", "0");
             * chr.updateOneInfo(1234699, "passCount", "0");
             * chr.updateOneInfo(1234699, "bMaxDay", "126");
             * chr.updateOneInfo(1234699, "lastDate", lastDate.format(new Date()));
             * chr.updateOneInfo(1234699, "cMaxDay", "126");
             * }
             * }
             * 
             * PreparedStatement ps = null;
             * ResultSet rs = null;
             * DBConnection db = new DBConnection();
             * try (Connection con = db.getConnection()) {
             * ps = con.
             * prepareStatement("SELECT `customData`, `account_id` FROM questinfo_account WHERE quest = ?"
             * );
             * ps.setInt(1, 1234699);
             * rs = ps.executeQuery();
             * boolean f = false;
             * while (rs.next()) {
             * f = true;
             * String customData = rs.getString("customData");
             * int accountID = rs.getInt("account_id");
             * 
             * String[] v = customData.split(";");
             * 
             * StringBuilder sb = new StringBuilder();
             * int i = 1;
             * int count = 0;
             * boolean a = false;
             * for (String v_ : v) {
             * String[] cd = v_.split("=");
             * if (cd[0].equals("get_sp_item") || cd[0].equals("get_sp_item2")) {
             * sb.append(cd[0]);
             * sb.append("=");
             * //System.out.println("CD1 : " + cd[1]);
             * sb.append(cd[1]);
             * if (count <= 0) {
             * sb.append(";");
             * }
             * count++;
             * }
             * }
             * PreparedStatement ps2 = con.
             * prepareStatement("UPDATE questinfo_account SET customData = ? WHERE account_id = ? and quest = ?"
             * );
             * ps2.setString(1, sb.toString());
             * ps2.setInt(2, accountID);
             * ps2.setInt(3, 1234699);
             * ps2.executeUpdate();
             * ps2.close();
             * }
             * } catch (SQLException e) {
             * e.printStackTrace();
             * } finally {
             * try {
             * if (ps != null) {
             * ps.close();
             * ps = null;
             * }
             * if (rs != null) {
             * rs.close();
             * rs = null;
             * }
             * } catch (SQLException e) {
             * e.printStackTrace();
             * }
             * }
             * }
             * getPlayer().dropMessage(5, "Gold Wagon has been reset for all Jin users.");
             * return;
             * }
             */
        }
        initNPC(MapleLifeFactory.getNPC(9010000));

        if (getPlayer().getName().equals("이유")) {
            if (target.exchange(2433482, -1) > 0) {
                recoveryQex();
                return;
            }
        }
        if (target.exchange(2433482, -1) > 0) {
            doGenesisWeaponUpgrade();
        }

        /*
         * if (target.exchange(2433482, -1, 2439614, 1, 4036460, 1, 4036461, 1, 4036462,
         * 1, 4036463, 1, 4036464, 1) > 0) {
         * // Reset quest when discarding Genesis Weapon
         * for (int i = 2000018; i <= 2000027; ++i) {
         * getPlayer().updateOneInfo(i, "clear", "0");
         * 
         * final MapleQuestStatus newStatus = new
         * MapleQuestStatus(MapleQuest.getInstance(i), (byte) 0, 2003);
         * getPlayer().updateQuest(newStatus);
         * }
         * 
         * for (int i = 2000018; i <= 2000025; ++i) {
         * getPlayer().updateOneInfo(i, "clear", "1");
         * }
         * 
         * getPlayer().fakeRelog();
         * } else {
         * self.
         * say("กรุณาทำช่องว่างใน #bกระเป๋า Use#k และ #bกระเป๋า Etc#k แล้วลองใหม่นะครับ"
         * );
         * }
         */
    }

    int[] bmWeapons = GameConstants.bmWeapons;

    // Unseal Genesis Weapon Final Release
    public void doGenesisWeaponUpgrade() {
        MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        Equip equip = null;
        for (Item item : new ArrayList<>(getPlayer().getInventory(MapleInventoryType.EQUIPPED).list())) {
            for (int i : bmWeapons) {
                if (item.getItemId() == i + 1) {
                    equip = (Equip) item;
                    break;
                }
            }
        }
        if (equip == null) {
            for (Item item : new ArrayList<>(getPlayer().getInventory(MapleInventoryType.EQUIP).list())) {
                for (int i : bmWeapons) {
                    if (item.getItemId() == i + 1) {
                        equip = (Equip) item;
                        break;
                    }
                }
            }
        }
        if (equip == null) {
            self.say("เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุ", ScriptMessageFlag.Self);
            return;
        }
        /*
         * int weaponID = equip.getItemId() + 1;
         * Equip genesis = (Equip) ii.getEquipById(weaponID);
         * 
         * if (genesis == null) {
         * sendNext("เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุ");
         * dispose();
         * return;
         * }
         */
        int weaponID = equip.getItemId();
        Equip genesis = (Equip) ii.getEquipById(weaponID);

        if (genesis == null) {
            self.say("เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุ", ScriptMessageFlag.Self);
            return;
        }

        int flag = EquipEnchantMan.filterForJobWeapon(weaponID);
        ItemUpgradeFlag[] flagArray = new ItemUpgradeFlag[] {
                ItemUpgradeFlag.INC_PAD,
                ItemUpgradeFlag.INC_MAD
        };
        ItemUpgradeFlag[] flagArray2 = new ItemUpgradeFlag[] {
                ItemUpgradeFlag.INC_STR,
                ItemUpgradeFlag.INC_DEX,
                ItemUpgradeFlag.INC_LUK,
                ItemUpgradeFlag.INC_MHP
        };
        ItemUpgradeFlag[] flagArray3 = new ItemUpgradeFlag[] {
                ItemUpgradeFlag.INC_INT
        };
        List<EquipEnchantScroll> source = new ArrayList<>();
        for (ItemUpgradeFlag f : flagArray) {
            for (ItemUpgradeFlag f2 : f == ItemUpgradeFlag.INC_PAD ? flagArray2 : flagArray3) {
                int index = 3; // 15%
                EquipEnchantOption option = new EquipEnchantOption();
                option.setOption(f.getValue(), EquipEnchantMan.getIncATTWeapon(ii.getReqLevel(weaponID), 3));
                if ((f2.check(flag))) {
                    option.setOption(f2.getValue(), EquipEnchantMan.getIncPrimaryStatWeapon(ii.getReqLevel(weaponID), 3)
                            * (f2 == ItemUpgradeFlag.INC_MHP ? 50 : 1));
                    if (option.flag > 0) {
                        source.add(new EquipEnchantScroll(weaponID, 3, option, ScrollType.UPGRADE, 0, false));
                    }
                }
            }
        }

        // Exception handling
        if (equip.getItemId() == 1242140) { // Xenon DEX, LUK
            source.clear();
            EquipEnchantOption option = new EquipEnchantOption();
            option.setOption(ItemUpgradeFlag.INC_PAD.getValue(),
                    EquipEnchantMan.getIncATTWeapon(ii.getReqLevel(weaponID), 3));
            option.setOption(ItemUpgradeFlag.INC_LUK.getValue(),
                    EquipEnchantMan.getIncPrimaryStatWeapon(ii.getReqLevel(weaponID), 3));

            source.add(new EquipEnchantScroll(weaponID, 3, option, ScrollType.UPGRADE, 0, false));
        }
        if (equip.getItemId() == 1232121) { // DA
            source.clear();
            EquipEnchantOption option = new EquipEnchantOption();
            option.setOption(ItemUpgradeFlag.INC_PAD.getValue(),
                    EquipEnchantMan.getIncATTWeapon(ii.getReqLevel(weaponID), 3));
            option.setOption(ItemUpgradeFlag.INC_MHP.getValue(),
                    EquipEnchantMan.getIncPrimaryStatWeapon(ii.getReqLevel(weaponID), 3) * 50);

            source.add(new EquipEnchantScroll(weaponID, 3, option, ScrollType.UPGRADE, 0, false));
        }
        if (equip.getItemId() == 1292021) { // Hoyoung
            source.clear();
            EquipEnchantOption option = new EquipEnchantOption();
            option.setOption(ItemUpgradeFlag.INC_PAD.getValue(),
                    EquipEnchantMan.getIncATTWeapon(ii.getReqLevel(weaponID), 3));
            option.setOption(ItemUpgradeFlag.INC_LUK.getValue(),
                    EquipEnchantMan.getIncPrimaryStatWeapon(ii.getReqLevel(weaponID), 3));

            source.add(new EquipEnchantScroll(weaponID, 3, option, ScrollType.UPGRADE, 0, false));
        }
        if (equip.getItemId() == 1362148) { // Phantom
            source.clear();
            EquipEnchantOption option = new EquipEnchantOption();
            option.setOption(ItemUpgradeFlag.INC_PAD.getValue(),
                    EquipEnchantMan.getIncATTWeapon(ii.getReqLevel(weaponID), 3));
            option.setOption(ItemUpgradeFlag.INC_LUK.getValue(),
                    EquipEnchantMan.getIncPrimaryStatWeapon(ii.getReqLevel(weaponID), 3));

            source.add(new EquipEnchantScroll(weaponID, 3, option, ScrollType.UPGRADE, 0, false));
        }
        if (equip.getItemId() == 1362148) { // NL/NW
            source.clear();
            EquipEnchantOption option = new EquipEnchantOption();
            option.setOption(ItemUpgradeFlag.INC_PAD.getValue(),
                    EquipEnchantMan.getIncATTWeapon(ii.getReqLevel(weaponID), 3));
            option.setOption(ItemUpgradeFlag.INC_LUK.getValue(),
                    EquipEnchantMan.getIncPrimaryStatWeapon(ii.getReqLevel(weaponID), 3));

            source.add(new EquipEnchantScroll(weaponID, 3, option, ScrollType.UPGRADE, 0, false));
        }
        if (source.size() <= 0) {
            self.say("เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุ", ScriptMessageFlag.Self);
            return;
        }
        EquipEnchantScroll scroll = source.get(0); // The first one is the correct scroll for the job
        if (scroll == null) {
            self.say("เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุ", ScriptMessageFlag.Self);
            return;
        }
        // Succeed 8 times

        Equip zeroEquip = null;
        if (GameConstants.isZero(getPlayer().getJob())) {
            zeroEquip = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                    .getItem(equip.getPosition() == -11 ? (short) -10 : -11);
        }
        for (int i = 0; i < 8; ++i) {
            scroll.upgrade(genesis, 0, true, zeroEquip);
        }

        // Apply 22 Star Force
        genesis.setCHUC(22);
        genesis.setItemState(equip.getItemState() | ItemStateFlag.AMAZING_HYPER_UPGRADE_CHECKED.getValue());

        byte grade = genesis.getAdditionalGrade();
        if (grade == 0) {
            grade = 1;
        }

        // Unique Potential 3 lines
        genesis.setLines((byte) 3); // 3 lines
        genesis.setState((byte) 19); // Unique
        for (int i = 0; i < 3; ++i) {
            int optionGrade = 3; // Unique
            int option = ItemOptionInfo.getItemOption(equip.getItemId(), optionGrade, genesis.getPotentials(false, i),
                    GradeRandomOption.Black);
            genesis.setPotentialOption(i, option);
        }

        // Epic Additional Potential 3 lines
        for (int i = 0; i < 3; ++i) {
            int optionGrade = 2; // Epic
            int option = ItemOptionInfo.getItemOption(equip.getItemId(), optionGrade, genesis.getPotentials(true, i),
                    GradeRandomOption.Additional);
            genesis.setPotentialOption(i + 3, option);
        }

        // Apply Bonus Stats
        if (BonusStat.resetBonusStat(genesis, BonusStatPlaceType.LevelledRebirthFlame)) {
        }

        if (zeroEquip != null) {
            zeroEquip.setCHUC(genesis.getCHUC());
            zeroEquip.setItemState(genesis.getItemState());
            zeroEquip.setExGradeOption(genesis.getExGradeOption());
            zeroEquip.setLines(genesis.getLines());
            zeroEquip.setState(genesis.getState());
            zeroEquip.setPotential1(genesis.getPotential1());
            zeroEquip.setPotential2(genesis.getPotential2());
            zeroEquip.setPotential3(genesis.getPotential3());
            zeroEquip.setPotential4(genesis.getPotential4());
            zeroEquip.setPotential5(genesis.getPotential5());
            zeroEquip.setPotential6(genesis.getPotential6());
        }
        MapleInventoryType type = MapleInventoryType.EQUIP;
        if (equip.getPosition() < 0) {
            type = MapleInventoryType.EQUIPPED;
        }
        getPlayer().send(CWvsContext.InventoryPacket.deleteItem(equip));

        MapleInventoryManipulator.removeFromSlot(getClient(), type, equip.getPosition(), equip.getQuantity(), false,
                false);
        MapleInventoryManipulator.addbyItem(getClient(), genesis);

        for (int i = 2000018; i <= 2000027; ++i) {
            getPlayer().forceCompleteQuest(i);
        }

        Center.Broadcast.broadcastMessage(CWvsContext.serverNotice(6,
                "ผู้เล่น " + getPlayer().getName()
                        + " ได้ปลดปล่อยพลังที่ถูกผนึก และกลายเป็นเจ้าของอาวุธ Genesis ที่มีพลังของ Black Mage!"));
    }

    public void consume_2633927() {
        initNPC(MapleLifeFactory.getNPC(9010000));

        String v0 = "กรุณาเลือกไอเทมที่ต้องการรับครับ\r\n#b";
        int baseItem = 1190555;
        for (int i = 0; i < 5; ++i) {
            v0 += "#L" + i + "##i" + (baseItem + i) + "# #z" + (baseItem + i) + "#\r\n";
        }
        v0 += "\r\n#L6#ยกเลิกการใช้#l";
        int v1 = self.askMenu(v0);
        if (v1 >= 0 && v1 <= 4) {
            int itemID = baseItem + v1;
            if (target.exchange(2633927, -1, itemID, 1) > 0) {
                self.say("แลกเปลี่ยนเรียบร้อยแล้วครับ");
            } else {
                self.say("กรุณาทำช่องในกระเป๋า Equip ให้ว่างแล้วลองใหม่ครับ");
            }
        }
    }

    // Jin: True 11th Month Heartfelt Box
    public void consume_2439630() {
        initNPC(MapleLifeFactory.getNPC(9062000));
        int[][] rewards = new int[][] {
                { 2434557, 3 }, // 1 เสริมแรง คะแนน แลกเปลี่ยน권 3개
                { 5680409, 1 }, // 5 แคช แลกเปลี่ยน권 1개
                { 5060048, 5 }, // 골드애플 5개
                { 5068300, 5 }, // บน습의 원더베리 2개
                { 5680157, 1 }, // 진:眞 강림 스타วัน 1개
                { 2436018, 1 }, // 진:眞 스페셜 헤어 쿠폰 1개
                { 2439605, 1 }, // 진:眞 스페셜 코디 상자 (S) 1개
                { 2439630, -1 }, // 상자 ใช้
        };

        String v0 = "หากเปิดกล่องจะได้รับไอเทมดังต่อไปนี้ ต้องการเปิดไหมครับ?\r\n\r\n";
        v0 += "#e[ไอเทมที่จะได้รับ]#n\r\n";
        for (int[] reward : rewards) {
            if (reward[1] != -1) {
                v0 += "#b#i" + reward[0] + "# #z" + reward[0] + "##k " + reward[1] + "개\r\n";
            }
        }

        v0 += "#b#i5002239# #z5002239##k (기간제 30วัน)\r\n";
        int v1 = self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByUser);
        if (v1 > 0) {

            // Originally should use exchange, but need to do this because of timed pets.
            if (getPlayer().getInventory(MapleInventoryType.CASH).getNumFreeSlot() < 1) {
                self.say("กรุณาทำช่องใน #bกระเป๋า Cash#k ให้ว่างแล้วลองใหม่นะครับ",
                        ScriptMessageFlag.NpcReplacedByUser);
                return;
            }
            if (target.exchange(rewards) == 1) {
                exchangePetPeriod(5002239, 30);
                self.say("เปิดกล่องและได้รับไอเทมแล้ว", ScriptMessageFlag.NpcReplacedByUser);
            } else {
                self.say("กรุณาทำช่องใน #bกระเป๋า Use#k และ #bกระเป๋า Cash#k ให้ว่างแล้วลองใหม่นะครับ",
                        ScriptMessageFlag.NpcReplacedByUser);
            }
        }
    }

    // Arcane Symbol: Vanishing Journey Upgrade Coupon
    public void consume_2431470() {
        initNPC(MapleLifeFactory.getNPC(9062000));

        // Check if Arcane Symbol: Vanishing Journey is equipped
        Item item = getPlayer().getInventory(MapleInventoryType.EQUIPPED).findById(1712001);

        if (item == null) {
            self.say("#b#i1712001# #z1712001##k() กรุณาสวมใส่ก่อนใช้งานครับ");
            return;
        }
        String v0 = "#b#i1712001# #z1712001##k ใช้ #b#z2431470##k เพื่อรับเอฟเฟกต์ #eAll Stat +1,000, ATT/MATT +300#n หรือไม่?\r\n\r\nไอเทมนี้ใช้งานได้เพียง 1 ครั้งเท่านั้น, #rไม่สามารถใช้กับ Symbol ที่ใช้งานไปแล้วได้#k.";
        if (self.askYesNo(v0) == 1) {
            String owner = item.getOwner();
            if (!owner.isEmpty()) {
                self.say("ไม่สามารถใช้กับ Symbol ที่ถูกเสริมพลังไปแล้วได้");
                return;
            }
            if (target.exchange(2431470, -1) > 0) {
                Equip equip = (Equip) item;
                equip.setOwner("Symbol ที่ถูกเสริมพลัง");
                equip.setWatk((short) (equip.getWatk() + 300));
                equip.setMatk((short) (equip.getMatk() + 300));
                equip.setStr((short) (equip.getStr() + 1000));
                equip.setDex((short) (equip.getDex() + 1000));
                equip.setInt((short) (equip.getInt() + 1000));
                equip.setLuk((short) (equip.getLuk() + 1000));

                getPlayer().send(CWvsContext.InventoryPacket.updateEquipSlot(equip));
                self.say("ใช้งานใบเสริมพลังเรียบร้อยแล้ว กรุณาตรวจสอบในช่องอุปกรณ์");
            }
        }
    }

    // Arcane Symbol: Chu Chu Island Upgrade Coupon
    public void consume_2431471() {
        initNPC(MapleLifeFactory.getNPC(9062000));

        // Check if Arcane Symbol: Chu Chu Island is equipped
        Item item = getPlayer().getInventory(MapleInventoryType.EQUIPPED).findById(1712002);

        if (item == null) {
            self.say("#b#i1712002# #z1712002##k() กรุณาสวมใส่ก่อนใช้งานครับ");
            return;
        }
        String v0 = "#b#i1712002# #z1712002##k ใช้ #b#z2431471##k เพื่อรับเอฟเฟกต์ #eAll Stat +750, ATT/MATT +250#n หรือไม่?\r\n\r\nไอเทมนี้ใช้งานได้เพียง 1 ครั้งเท่านั้น, #rไม่สามารถใช้กับ Symbol ที่ใช้งานไปแล้วได้#k.";
        if (self.askYesNo(v0) == 1) {
            String owner = item.getOwner();
            if (!owner.isEmpty()) {
                self.say("ไม่สามารถใช้กับ Symbol ที่ถูกเสริมพลังไปแล้วได้");
                return;
            }
            if (target.exchange(2431471, -1) > 0) {
                Equip equip = (Equip) item;
                equip.setOwner("Symbol ที่ถูกเสริมพลัง");
                equip.setWatk((short) (equip.getWatk() + 750));
                equip.setMatk((short) (equip.getMatk() + 750));
                equip.setStr((short) (equip.getStr() + 1500));
                equip.setDex((short) (equip.getDex() + 1500));
                equip.setInt((short) (equip.getInt() + 1500));
                equip.setLuk((short) (equip.getLuk() + 1500));

                getPlayer().send(CWvsContext.InventoryPacket.updateEquipSlot(equip));
                self.say("ใช้งานใบเสริมพลังเรียบร้อยแล้ว กรุณาตรวจสอบในช่องอุปกรณ์");
            }
        }
    }

    // Arcane Symbol: Lachelein Upgrade Coupon
    public void consume_2431472() {
        initNPC(MapleLifeFactory.getNPC(9062000));

        // Check if Arcane Symbol: Lachelein is equipped
        Item item = getPlayer().getInventory(MapleInventoryType.EQUIPPED).findById(1712003);

        if (item == null) {
            self.say("#b#i1712003# #z1712003##k() กรุณาสวมใส่ก่อนใช้งานครับ");
            return;
        }
        String v0 = "#b#i1712003# #z1712003##k ใช้ #b#z2431472##k เพื่อรับเอฟเฟกต์ #eAll Stat +750, ATT/MATT +250#n หรือไม่?\r\n\r\nไอเทมนี้ใช้งานได้เพียง 1 ครั้งเท่านั้น, #rไม่สามารถใช้กับ Symbol ที่ใช้งานไปแล้วได้#k.";
        if (self.askYesNo(v0) == 1) {
            String owner = item.getOwner();
            if (!owner.isEmpty()) {
                self.say("ไม่สามารถใช้กับ Symbol ที่ถูกเสริมพลังไปแล้วได้");
                return;
            }
            if (target.exchange(2431472, -1) > 0) {
                Equip equip = (Equip) item;
                equip.setOwner("Symbol ที่ถูกเสริมพลัง");
                equip.setWatk((short) (equip.getWatk() + 750));
                equip.setMatk((short) (equip.getMatk() + 750));
                equip.setStr((short) (equip.getStr() + 1500));
                equip.setDex((short) (equip.getDex() + 1500));
                equip.setInt((short) (equip.getInt() + 1500));
                equip.setLuk((short) (equip.getLuk() + 1500));

                getPlayer().send(CWvsContext.InventoryPacket.updateEquipSlot(equip));
                self.say("ใช้งานใบเสริมพลังเรียบร้อยแล้ว กรุณาตรวจสอบในช่องอุปกรณ์");
            }
        }
    }

    // Arcane Symbol: Arcana Upgrade Coupon
    public void consume_2431475() {
        initNPC(MapleLifeFactory.getNPC(9062000));

        // Check if Arcane Symbol: Arcana is equipped
        Item item = getPlayer().getInventory(MapleInventoryType.EQUIPPED).findById(1712004);

        if (item == null) {
            self.say("#b#i1712004# #z1712004##k() กรุณาสวมใส่ก่อนใช้งานครับ");
            return;
        }
        String v0 = "#b#i1712004# #z1712004##k ใช้ #b#z2431475##k เพื่อรับเอฟเฟกต์ #eAll Stat +750, ATT/MATT +250#n หรือไม่?\r\n\r\nไอเทมนี้ใช้งานได้เพียง 1 ครั้งเท่านั้น, #rไม่สามารถใช้กับ Symbol ที่ใช้งานไปแล้วได้#k.";
        if (self.askYesNo(v0) == 1) {
            String owner = item.getOwner();
            if (!owner.isEmpty()) {
                self.say("ไม่สามารถใช้กับ Symbol ที่ถูกเสริมพลังไปแล้วได้");
                return;
            }
            if (target.exchange(2431475, -1) > 0) {
                Equip equip = (Equip) item;
                equip.setOwner("Symbol ที่ถูกเสริมพลัง");
                equip.setWatk((short) (equip.getWatk() + 750));
                equip.setMatk((short) (equip.getMatk() + 750));
                equip.setStr((short) (equip.getStr() + 1500));
                equip.setDex((short) (equip.getDex() + 1500));
                equip.setInt((short) (equip.getInt() + 1500));
                equip.setLuk((short) (equip.getLuk() + 1500));

                getPlayer().send(CWvsContext.InventoryPacket.updateEquipSlot(equip));
                self.say("ใช้งานใบเสริมพลังเรียบร้อยแล้ว กรุณาตรวจสอบในช่องอุปกรณ์");
            }
        }
    }

    // Arcane Symbol: Morass Upgrade Coupon
    public void consume_2431483() {
        initNPC(MapleLifeFactory.getNPC(9062000));

        // Check if Arcane Symbol: Morass is equipped
        Item item = getPlayer().getInventory(MapleInventoryType.EQUIPPED).findById(1712005);

        if (item == null) {
            self.say("#b#i1712005# #z1712005##k() กรุณาสวมใส่ก่อนใช้งานครับ");
            return;
        }
        String v0 = "#b#i1712005# #z1712005##k ใช้ #b#z2431483##k เพื่อรับเอฟเฟกต์ #eAll Stat +750, ATT/MATT +250#n หรือไม่?\r\n\r\nไอเทมนี้ใช้งานได้เพียง 1 ครั้งเท่านั้น, #rไม่สามารถใช้กับ Symbol ที่ใช้งานไปแล้วได้#k.";
        if (self.askYesNo(v0) == 1) {
            String owner = item.getOwner();
            if (!owner.isEmpty()) {
                self.say("ไม่สามารถใช้กับ Symbol ที่ถูกเสริมพลังไปแล้วได้");
                return;
            }
            if (target.exchange(2431483, -1) > 0) {
                Equip equip = (Equip) item;
                equip.setOwner("Symbol ที่ถูกเสริมพลัง");
                equip.setWatk((short) (equip.getWatk() + 750));
                equip.setMatk((short) (equip.getMatk() + 750));
                equip.setStr((short) (equip.getStr() + 1500));
                equip.setDex((short) (equip.getDex() + 1500));
                equip.setInt((short) (equip.getInt() + 1500));
                equip.setLuk((short) (equip.getLuk() + 1500));

                getPlayer().send(CWvsContext.InventoryPacket.updateEquipSlot(equip));
                self.say("ใช้งานใบเสริมพลังเรียบร้อยแล้ว กรุณาตรวจสอบในช่องอุปกรณ์");
            }
        }
    }

    // Arcane Symbol: Esfera Upgrade Coupon
    public void consume_2431540() {
        initNPC(MapleLifeFactory.getNPC(9062000));

        // Check if Arcane Symbol: Esfera is equipped
        Item item = getPlayer().getInventory(MapleInventoryType.EQUIPPED).findById(1712006);

        if (item == null) {
            self.say("#b#i1712006# #z1712006##k() กรุณาสวมใส่ก่อนใช้งานครับ");
            return;
        }
        String v0 = "#b#i1712006# #z1712006##k ใช้ #b#z2431540##k เพื่อรับเอฟเฟกต์ #eAll Stat +750, ATT/MATT +250#n หรือไม่?\r\n\r\nไอเทมนี้ใช้งานได้เพียง 1 ครั้งเท่านั้น, #rไม่สามารถใช้กับ Symbol ที่ใช้งานไปแล้วได้#k.";
        if (self.askYesNo(v0) == 1) {
            String owner = item.getOwner();
            if (!owner.isEmpty()) {
                self.say("ไม่สามารถใช้กับ Symbol ที่ถูกเสริมพลังไปแล้วได้");
                return;
            }
            if (target.exchange(2431540, -1) > 0) {
                Equip equip = (Equip) item;
                equip.setOwner("Symbol ที่ถูกเสริมพลัง");
                equip.setWatk((short) (equip.getWatk() + 750));
                equip.setMatk((short) (equip.getMatk() + 750));
                equip.setStr((short) (equip.getStr() + 1500));
                equip.setDex((short) (equip.getDex() + 1500));
                equip.setInt((short) (equip.getInt() + 1500));
                equip.setLuk((short) (equip.getLuk() + 1500));

                getPlayer().send(CWvsContext.InventoryPacket.updateEquipSlot(equip));
                self.say("ใช้งานใบเสริมพลังเรียบร้อยแล้ว กรุณาตรวจสอบในช่องอุปกรณ์");
            }
        }
    }

    public void consume_2434325() {
        initNPC(MapleLifeFactory.getNPC(9000159));
        int level = self.askNumber("กรุณาระบุเลเวลของหุ่นฟางที่จะเรียกออกมา (เลเวล 100 ~ 250)", 200, 100, 250);
        if (level < 100)
            return;
        if (level > 250)
            return;
        MapleMonster mob = MapleLifeFactory.getMonster(9305650);
        if (!mob.getStats().isChangeable()) {
            mob.getStats().setChange(true);
        }
        ChangeableStats stat = new ChangeableStats(mob.getStats(),
                new OverrideMonsterStats(21000000000000L, mob.getStats().getMp(), 0));
        mob.changeCustomStat(new ChangeableStats(mob.getStats(), stat, level));
        getPlayer().getMap().spawnMonsterOnGroundBelow(mob, getPlayer().getPosition());
        getPlayer().removeItem(2434325, -1);
    }

    public void consume_2434330() {
        initNPC(MapleLifeFactory.getNPC(9000159));
        int level = self.askNumber("กรุณาระบุเลเวลของหุ่นฟางที่จะเรียกออกมา (เลเวล 100 ~ 250)", 200, 100, 250);
        if (level < 100)
            return;
        if (level > 250)
            return;
        MapleMonster mob = MapleLifeFactory.getMonster(9305652);
        if (!mob.getStats().isChangeable()) {
            mob.getStats().setChange(true);
        }
        ChangeableStats stat = new ChangeableStats(mob.getStats(),
                new OverrideMonsterStats(21000000000000L, mob.getStats().getMp(), 0));
        mob.changeCustomStat(new ChangeableStats(mob.getStats(), stat, level));
        getPlayer().getMap().spawnMonsterOnGroundBelow(mob, getPlayer().getPosition());
        getPlayer().removeItem(2434330, -1);
    }

    public void consume_2432098() {
        if (DBConfig.isGanglim) { // Item not used in Ganglim
            return;
        }
        initNPC(MapleLifeFactory.getNPC(9062000));
        String v0 = "#b#i2432098# #z2432098##k ใช้สำหรับรีเซ็ตจำนวนการเข้าและเคลียร์บอสประจำสัปดาห์ได้\r\n\r\n#rโหมด Hell#k ของบอสทั้งหมด สามารถรีเซ็ตการเคลียร์ได้ #r5 ครั้งต่อวัน#k เท่านั้น, ส่วนจำนวนการเข้าสามารถรีเซ็ตได้ไม่จำกัด\r\n\r\n";
        int count = getPlayer().getOneInfoQuestInteger(1234569, "hell_boss_count");
        v0 += "#eจำนวนครั้งที่รีเซ็ตการเคลียร์โหมด Hell วันนี้ : (" + count + "/5)#n\r\n\r\n";
        v0 += "กรุณาเลือกบอสที่จะรีเซ็ต#b\r\n#L7#รีเซ็ต Chaos Papulatus#l\r\n#L0#รีเซ็ต Lotus#l\r\n#L1#รีเซ็ต Damien#l\r\n#L2#รีเซ็ต Lucid#l\r\n#L3#รีเซ็ต Will#l\r\n#L4#รีเซ็ต Jin Hilla#l\r\n#L5#รีเซ็ต Gloom#l\r\n#L6#รีเซ็ต Darknell#l\r\n#L9#รีเซ็ต Guardian Angel Slime\r\n#L8#รีเซ็ต Seren#l\r\n#L10##rHell Lotus#b รีเซ็ต#l\r\n#L11##rHell Damien#b รีเซ็ต#l\r\n#L12##rHell Lucid#b รีเซ็ต#l\r\n#L13##rHell Will#b รีเซ็ต#l\r\n";
        int v1 = self.askMenu(v0);
        String bossName = "";
        String clearKeyValue = "";
        String canTimeKeyValue = "";
        List<String> countList = new ArrayList<>();
        switch (v1) {
            case 0:
                bossName = "Suu";
                clearKeyValue = "swoo_clear";
                canTimeKeyValue = "swoo_can_time";
                countList.add("노말 스우c");
                countList.add("하드 스우c");
                countList.add("헬 스우c");
                break;
            case 1:
                bossName = "Demian";
                clearKeyValue = "demian_clear";
                canTimeKeyValue = "demian_can_time";
                countList.add("노말 데미ในc");
                countList.add("하드 데미ในc");
                countList.add("헬 데미ในc");
                break;
            case 2:
                bossName = "Lucid";
                clearKeyValue = "lucid_clear";
                canTimeKeyValue = "lucid_can_time";
                countList.add("노말 루시드c");
                countList.add("하드 루시드c");
                countList.add("헬 루시드c");
                break;
            case 3:
                bossName = "Will";
                clearKeyValue = "will_clear";
                canTimeKeyValue = "will_can_time";
                countList.add("노말 윌c");
                countList.add("하드 윌c");
                countList.add("헬 윌c");
                break;
            case 4:
                bossName = "Jin Hilla";
                clearKeyValue = "jinhillah_clear";
                canTimeKeyValue = "jinhillah_can_time";
                countList.add("노말 진힐라c");
                countList.add("하드 진힐라c");
                countList.add("헬 진힐라c");
                break;
            case 5:
                bossName = "Gloom";
                clearKeyValue = "dusk_clear";
                canTimeKeyValue = "dusk_can_time";
                countList.add("노말 더스크c");
                countList.add("카오스 더스크c");
                countList.add("헬 더스크c");
                break;
            case 6:
                bossName = "Darknell";
                clearKeyValue = "dunkel_clear";
                canTimeKeyValue = "dunkel_can_time";
                countList.add("노말 듄켈c");
                countList.add("하드 듄켈c");
                countList.add("헬 듄켈c");
                break;
            case 7:
                bossName = "Papulatus";
                clearKeyValue = "chaos_papulatus_clear";
                canTimeKeyValue = "papulatus_can_time";
                countList.add("노말 파풀라투스c");
                countList.add("하드 파풀라투스c");
                break;
            case 8:
                bossName = "Seren";
                clearKeyValue = "seren_clear";
                canTimeKeyValue = "seren_can_time";
                countList.add("노말 세렌c");
                countList.add("하드 세렌c");
                countList.add("헬 세렌c");
                break;
            case 9:
                bossName = "Guardian Angel Slime";
                clearKeyValue = "guardian_angel_slime_clear";
                canTimeKeyValue = "guardian_angel_slime_can_time";
                countList.add("노말 가디언 엔젤 슬라임c");
                countList.add("하드 가디언 엔젤 슬라임c");
                countList.add("헬 가디언 엔젤 슬라임c");
                break;
            case 10:
                bossName = "Hell Suu";
                clearKeyValue = "swoo_clear";
                canTimeKeyValue = "swoo_can_time";
                countList.add("노말 스우c");
                countList.add("하드 스우c");
                countList.add("헬 스우c");
                break;
            case 11:
                bossName = "Hell Demian";
                clearKeyValue = "demian_clear";
                canTimeKeyValue = "demian_can_time";
                countList.add("노말 데미ในc");
                countList.add("하드 데미ในc");
                countList.add("헬 데미ในc");
                break;
            case 12:
                bossName = "Hell Lucid";
                clearKeyValue = "lucid_clear";
                canTimeKeyValue = "lucid_can_time";
                countList.add("노말 루시드c");
                countList.add("하드 루시드c");
                countList.add("헬 루시드c");
                break;
            case 13:
                bossName = "Hell Will";
                clearKeyValue = "will_clear";
                canTimeKeyValue = "will_can_time";
                countList.add("노말 윌c");
                countList.add("하드 윌c");
                countList.add("헬 윌c");
                break;
        }
        int qid = 1234569;
        if (v1 < 10) {
            /*
             * if (v1 == 5 || v1 == 6) {
             * qid = 1234589;
             * }
             * if (v1 == 8) {
             * qid = 39932;
             * boolean check = getPlayer().getOneInfoQuestInteger(qid, "clear") == 1;
             * if (!check) {
             * self.say("Since there is no record of defeating " + bossName +
             * ", it cannot be used.");
             * return;
             * }
             * } else {
             * boolean check = getPlayer().getOneInfoQuestInteger(qid, clearKeyValue) == 1;
             * if (!check) {
             * self.say("Since there is no record of defeating " + bossName +
             * ", it cannot be used.");
             * return;
             * }
             * }
             */
            if (self.askYesNo("ต้องการรีเซ็ตจำนวนการเข้าและเคลียร์บอส " + bossName
                    + " หรือไม่?\r\n\r\n#e#rจำนวนการเข้าและเคลียร์โหมด Hell จะถูกรีเซ็ต") == 1) {
                if (target.exchange(2432098, -1) == 1) {
                    boolean downSingle = false;
                    boolean downMulti = false;
                    if (v1 == 8) {
                        getPlayer().updateOneInfo(39932, "clear", "");
                        if (!DBConfig.isGanglim) {
                            int trycounts = getPlayer().getOneInfoQuestInteger(39932, "clear_single");
                            if (trycounts > 0) {
                                downSingle = true;
                                getPlayer().updateOneInfo(39932, "clear_single", String.valueOf(trycounts - 1));
                            }
                            int trycountm = getPlayer().getOneInfoQuestInteger(39932, "clear_multi");
                            if (trycountm > 0) {
                                downMulti = true;
                                getPlayer().updateOneInfo(39932, "clear_multi", String.valueOf(trycountm - 1));
                            }
                        }
                        getPlayer().updateOneInfo(39932, "enter", "");
                        getPlayer().updateOneInfo(1234569, canTimeKeyValue, "");
                    }
                    if (v1 == 5 || v1 == 6) {
                        getPlayer().updateOneInfo(1234589, clearKeyValue, "");
                        if (!DBConfig.isGanglim) {
                            int trycounts = getPlayer().getOneInfoQuestInteger(1234589, clearKeyValue + "_single");
                            if (trycounts > 0) {
                                downSingle = true;
                                getPlayer().updateOneInfo(1234589, clearKeyValue + "_single",
                                        String.valueOf(trycounts - 1));
                            }
                            int trycountm = getPlayer().getOneInfoQuestInteger(1234589, clearKeyValue + "_multi");
                            if (trycountm > 0) {
                                downMulti = true;
                                getPlayer().updateOneInfo(1234589, clearKeyValue + "_multi",
                                        String.valueOf(trycountm - 1));
                            }
                        }
                        getPlayer().updateOneInfo(1234569, canTimeKeyValue, "");
                    } else {
                        if (DBConfig.isGanglim) {
                            getPlayer().updateOneInfo(qid, clearKeyValue, "");
                        } else {
                            getPlayer().updateOneInfo(qid, clearKeyValue, "");
                            int trycounts = getPlayer().getOneInfoQuestInteger(qid, clearKeyValue + "_single");
                            if (trycounts > 0) {
                                downSingle = true;
                                getPlayer().updateOneInfo(qid, clearKeyValue + "_single",
                                        String.valueOf(trycounts - 1));
                            }
                            int trycountm = getPlayer().getOneInfoQuestInteger(qid, clearKeyValue + "_multi");
                            if (trycountm > 0) {
                                downMulti = true;
                                getPlayer().updateOneInfo(qid, clearKeyValue + "_multi",
                                        String.valueOf(trycountm - 1));
                            }
                        }
                        if (!canTimeKeyValue.isEmpty()) {
                            getPlayer().updateOneInfo(qid, canTimeKeyValue, "");
                        }
                    }
                    for (String c : countList) {
                        getPlayer().CountClear(c);
                    }
                    String infoSingle = downSingle ? "Single 1x " : "";
                    String infoMulti = (downSingle ? ", " : "") + (downMulti ? "Multi 1x" : "");
                    self.say("รีเซ็ตจำนวนการเข้าและเคลียร์บอส " + bossName + infoSingle + infoMulti
                            + " เรียบร้อยแล้วครับ");

                    StringBuilder sb = new StringBuilder("Boss Entry Reset (Item : ");
                    sb.append(2432098);
                    sb.append(", Reset Boss : ");
                    sb.append(bossName);
                    sb.append(")");
                    LoggingManager.putLog(new ConsumeLog(getPlayer(), 2432098, sb));
                }
            }
        } else {
            boolean check = getPlayer().getOneInfoQuestInteger(1234569, "hell_" + clearKeyValue) == 1;
            /*
             * if (!check) {
             * self.say("Since there is no record of defeating " + bossName +
             * ", it cannot be used.");
             * return;
             * }
             */

            int hbc = getPlayer().getOneInfoQuestInteger(1234569, "hell_boss_count");
            if (hbc >= 5 && !getPlayer().isGM()) {
                self.say(
                        "วันนี้รีเซ็ตการเคลียร์โหมด Hell ไปแล้ว 5 ครั้ง ไม่สามารถรีเซ็ตเพิ่มได้อีกครับ จำนวนครั้งจะถูกรีเซ็ตตอนเที่ยงคืน");
                return;
            }
            if (self.askYesNo("ต้องการรีเซ็ตจำนวนการเข้าและเคลียร์บอส " + bossName
                    + " หรือไม่?\r\n\r\n#e#rจำนวนการเข้าและเคลียร์โหมด Hell จะถูกรีเซ็ต") == 1) {
                if (target.exchange(2432098, -1) == 1) {
                    getPlayer().updateOneInfo(qid, clearKeyValue, "");
                    getPlayer().updateOneInfo(qid, "hell_" + clearKeyValue, "");
                    getPlayer().updateOneInfo(1234569, "hell_boss_count",
                            String.valueOf(getPlayer().getOneInfoQuestInteger(1234569, "hell_boss_count") + 1));
                    if (!canTimeKeyValue.isEmpty()) {
                        getPlayer().updateOneInfo(qid, canTimeKeyValue, "");
                    }
                    for (String c : countList) {
                        getPlayer().CountClear(c);
                    }
                    self.say("รีเซ็ตจำนวนการเข้าและเคลียร์บอส " + bossName + " เรียบร้อยแล้วครับ");

                    StringBuilder sb = new StringBuilder("Boss Entry Reset [Hell Mode] (Item : ");
                    sb.append(2432098);
                    sb.append(", Reset Boss : ");
                    sb.append(bossName);
                    sb.append(")");
                    LoggingManager.putLog(new ConsumeLog(getPlayer(), 2432098, sb));
                }
            }
        }
    }

    public void cash_5680520() {
        initNPC(MapleLifeFactory.getNPC(9062000));
        if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 1) {
            self.say("กรุณาทำช่อง Use ให้ว่าง 1 ช่องแล้วลองใหม่ครับ");
            return;
        }
        if (target.exchange(5680520, -1, 2436577, 1) > 0) {
            self.sayOk("แลกเปลี่ยนเรียบร้อยแล้วครับ");
        }
    }

    public void consume_2436577() {
        initNPC(MapleLifeFactory.getNPC(9062000));
        if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 1) {
            self.say("กรุณาทำช่อง Use ให้ว่าง 1 ช่องแล้วลองใหม่ครับ");
            return;
        }
        Set keys = MonsterCollection.mobByName.keySet();
        String key = (String) Randomizer.next(Arrays.asList(keys.toArray()));
        int type = MonsterCollection.mobByName.get(key).getType();
        while (type == 2 || type == 6 || type >= 7) {
            key = (String) Randomizer.next(Arrays.asList(keys.toArray()));
            type = MonsterCollection.mobByName.get(key).getType();
        }
        MonsterCollection.CollectionMobData data = MonsterCollection.mobByName.get(key);
        if (target.exchange(itemID, -1) > 0) {
            if (!MonsterCollection.checkIfMobOnCollection(getPlayer(), data)) {
                MonsterCollection.setMobOnCollection(getPlayer(), data);
            } else {
                if (target.exchange(2048746, 1) > 0) {
                    self.sayOk(
                            "มอบรางวัลพิเศษให้คุณ #h0# เรียบร้อยแล้ว!\r\nลองเช็คในกระเป๋า Use ดูนะครับ!\r\n\r\n#fUI/UIWindow2.img/QuestIcon/4/0#\r\n#e#b#i2048746# #t2048746#");
                }
            }
        }
    }

    public void consume_2434941() {
        initNPC(MapleLifeFactory.getNPC(9062000));
        if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 1) {
            self.say("กรุณาทำช่องในกระเป๋า Use ให้ว่างอย่างน้อย 1 ช่องแล้วลองใหม่ครับ");
            return;
        }
        Set keys = MonsterCollection.mobByName.keySet();
        String key = (String) Randomizer.next(Arrays.asList(keys.toArray()));
        int starRank = MonsterCollection.mobByName.get(key).getStarRank();
        while (starRank < 3) {
            key = (String) Randomizer.next(Arrays.asList(keys.toArray()));
            starRank = MonsterCollection.mobByName.get(key).getStarRank();
        }
        MonsterCollection.CollectionMobData data = MonsterCollection.mobByName.get(key);
        if (target.exchange(itemID, -1) > 0) {
            if (!MonsterCollection.checkIfMobOnCollection(getPlayer(), data)) {
                MonsterCollection.setMobOnCollection(getPlayer(), data);
            } else {
                if (target.exchange(2048746, 1) > 0) {
                    self.sayOk(
                            "ได้มอบรางวัลพิเศษให้คุณ #h0# เรียบร้อยแล้ว!\r\nลองเช็คในกระเป๋า Use ดูนะครับ!\r\n\r\n#fUI/UIWindow2.img/QuestIcon/4/0#\r\n#e#b#i2048746# #t2048746#");
                }
            }
        }
    }

    public void consume_2434942() {
        initNPC(MapleLifeFactory.getNPC(9062000));
        if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 1) {
            self.say("กรุณาทำช่องในกระเป๋า Use ให้ว่างอย่างน้อย 1 ช่องแล้วลองใหม่ครับ");
            return;
        }
        Set keys = MonsterCollection.mobByName.keySet();
        String key = (String) Randomizer.next(Arrays.asList(keys.toArray()));
        int type = MonsterCollection.mobByName.get(key).getType();
        while (type != 0) {
            key = (String) Randomizer.next(Arrays.asList(keys.toArray()));
            type = MonsterCollection.mobByName.get(key).getType();
        }
        MonsterCollection.CollectionMobData data = MonsterCollection.mobByName.get(key);
        if (target.exchange(itemID, -1) > 0) {
            if (!MonsterCollection.checkIfMobOnCollection(getPlayer(), data)) {
                MonsterCollection.setMobOnCollection(getPlayer(), data);
            } else {
                self.sayOk(String.format("ได้มอนสเตอร์ %s แต่มีในคอลเลกชันอยู่แล้ว จึงไม่สามารถบันทึกได้", key));
            }
        }
    }

    public void consume_2434943() {
        initNPC(MapleLifeFactory.getNPC(9062000));
        if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 1) {
            self.say("กรุณาทำช่องในกระเป๋า Use ให้ว่างอย่างน้อย 1 ช่องแล้วลองใหม่ครับ");
            return;
        }

        var csi = Randomizer.next(MonsterCollection.mobCollections.get(0).getSubIndexList());
        var randg = Randomizer.next(csi.getGroup());
        Integer randm = Randomizer.next(randg.getMobs());
        String key = MapleLifeFactory.getMonster(randm).getStats().getName();
        int type = MonsterCollection.mobByName.get(key).getType();
        while (type == 2 || type == 6 || type >= 7) {
            csi = Randomizer.next(MonsterCollection.mobCollections.get(0).getSubIndexList());
            randg = Randomizer.next(csi.getGroup());
            randm = Randomizer.next(randg.getMobs());
            key = MapleLifeFactory.getMonster(randm).getStats().getName();
            type = MonsterCollection.mobByName.get(key).getType();
        }
        MonsterCollection.CollectionMobData data = MonsterCollection.mobByName.get(key);
        if (target.exchange(itemID, -1) > 0) {
            if (!MonsterCollection.checkIfMobOnCollection(getPlayer(), data)) {
                MonsterCollection.setMobOnCollection(getPlayer(), data);
            } else {
                self.sayOk(String.format("ได้มอนสเตอร์ %s แต่มีในคอลเลกชันอยู่แล้ว จึงไม่สามารถบันทึกได้", key));
            }
        }
    }

    public void consume_2434958() {
        monsterMoMong("Ice Golem");
    }

    public void consume_2434959() {
        monsterMoMong("Prime Minister");
    }

    public void consume_2434971() {
        monsterMoMong("Poison Flower");
    }

    public void consume_2435366() {
        monsterMoMong("Horntail");
    }

    public void consume_2435367() {
        monsterMoMong("Chaos Horntail");
    }

    public void consume_2435368() {
        List<String> names = Arrays.asList("Jar", "Tri-Jar", "Bellflower Root", "Old Bellflower Root",
                "Giant Bellflower Root");
        monsterMoMong(Randomizer.next(names));
    }

    public void consume_2437618() {
        monsterMoMong("Cheap Amplifier");
    }

    public void consume_2437619() {
        monsterMoMong("High Quality Amplifier");
    }

    private void monsterMoMong(String name) {
        initNPC(MapleLifeFactory.getNPC(9062000));
        if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 1) {
            self.say("กรุณาทำช่อง Use ให้ว่าง 1 ช่องแล้วลองใหม่ครับ");
            return;
        }
        var data = MonsterCollection.mobByName.getOrDefault(name, null);
        if (data == null) {
            self.sayOk("ล้มเหลวเนื่องจากข้อผิดพลาดที่ไม่ทราบสาเหตุ");
            return;
        }
        if (target.exchange(itemID, -1) > 0) {
            if (!MonsterCollection.checkIfMobOnCollection(getPlayer(), data)) {
                MonsterCollection.setMobOnCollection(getPlayer(), data);
            } else {
                if (target.exchange(2048745, 1) > 0) {
                    self.sayOk(
                            "ได้มอบรางวัลพิเศษให้คุณ #h0# เรียบร้อยแล้ว!\r\nลองเช็คในกระเป๋า Use ดูนะครับ!\r\n\r\n#fUI/UIWindow2.img/QuestIcon/4/0#\r\n#e#b#i2048745# #t2048745#");
                }
            }
        }
    }

    public void consume_2434929() {
        List<Pair<Integer, Integer>> rewards = Arrays.asList(new Pair<>(4001832, 3000));
        adventure_box_reward(Randomizer.next(rewards));
    }

    public void consume_2434930() {
        List<Pair<Integer, Integer>> rewards = Arrays.asList(new Pair<>(4001832, 6000));
        adventure_box_reward(Randomizer.next(rewards));
    }

    public void consume_2434931() {
        List<Pair<Integer, Integer>> rewards = Arrays.asList(new Pair<>(2048745, 3));
        adventure_box_reward(Randomizer.next(rewards));
    }

    public void consume_2434932() {
        List<Pair<Integer, Integer>> rewards = Arrays.asList(new Pair<>(2048746, 3));
        adventure_box_reward(Randomizer.next(rewards));
    }

    private void adventure_box_reward(Pair<Integer, Integer> reward) {
        initNPC(MapleLifeFactory.getNPC(9062000));
        if (getPlayer().getInventory(MapleInventoryType.ETC).getNumFreeSlot() < 1
                || getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 3) {
            self.say("กรุณาทำช่อง Etc ให้ว่าง 1 ช่อง และช่อง Use ให้ว่าง 3 ช่องขึ้นไป แล้วลองใหม่ครับ");
            return;
        }
        if (target.exchange(itemID, -1, reward.left, reward.right) > 0) {
            self.sayOk(
                    String.format(
                            "มอบรางวัลให้เรียบร้อยแล้วครับ!\r\n\r\n#fUI/UIWindow2.img/QuestIcon/4/0#\r\n#e#b#i%d# #t%d# %d ชิ้น",
                            reward.left, reward.left, reward.right));
        }
    }

    public void consume_2632808() {
        initNPC(MapleLifeFactory.getNPC(9062000));
        int qty = getPlayer().getItemQuantity(2632808, false);
        qty = Math.min(30000, qty);
        if (target.exchange(2632808, -qty) > 0) {
            if (DBConfig.isGanglim) {
                int currentstone = getPlayer().getOneInfoQuestInteger(100711, "point");
                getPlayer().updateOneInfo(100711, "point", String.valueOf(currentstone + qty));
            } else {
                getPlayer().gainStackEventGauge(0, qty, false);
            }
            self.sayOk(
                    String.format(
                            "มอบรางวัลให้เรียบร้อยแล้วครับ!\r\n\r\n#fUI/UIWindow2.img/QuestIcon/4/0#\r\n#e#b#i%d# #t%d# %d ชิ้น",
                            2632905, 2632905, qty));
        }
    }

    public void ep1Reset() {
        initNPC(MapleLifeFactory.getNPC(9062000));
        final StringBuilder v0 = new StringBuilder(
                "คุณต้องการใช้ใบเสริมพลังกับเหรียญตราอันไหน?\r\nเมื่อใช้แล้วจะไม่สามารถย้อนกลับได้ กรุณาเลือกอย่างระมัดระวัง\r\n\r\n#b※ เมื่อใช้ใบเสริมพลัง จะเพิ่ม #eAll Stat 350, ATT/MATT 250#n, สามารถใช้ได้สูงสุด 10 ครั้งต่อเหรียญตรา\r\n\r\n");
        List<Item> itemList = new ArrayList<>();
        getPlayer().getInventory(MapleInventoryType.EQUIP).list().forEach(item -> {
            if (!DBConfig.isGanglim) {
                if (item.getItemId() / 10000 == 114) {
                    if (!GameConstants.isJinEndlessMedal(item.getItemId())) {
                        itemList.add(item);
                    }
                }
            } else {
                if (item.getItemId() / 10000 == 114) {
                    itemList.add(item);
                }
            }
        });
        if (itemList.isEmpty()) {
            v0.append("ไม่มีเหรียญตราอยู่ในช่องอุปกรณ์");
            self.say(v0.toString());
            return;
        } else {
            itemList.forEach(item -> {
                v0.append("#L" + item.getPosition() + "##i" + item.getItemId() + "# #z" + item.getItemId() + "##l\r\n");
            });
        }
        int v1 = self.askMenu(v0.toString());
        Item pick = getPlayer().getInventory(MapleInventoryType.EQUIP).getItem((short) v1);
        if (pick == null) {
            self.say("ไม่พบไอเทมดังกล่าว");
            return;
        }
        String owner = pick.getOwner();
        int level = 0;
        if (owner != null && !owner.isEmpty()) {
            level = Integer.parseInt(owner.split(" Star")[0]);
        }

        String v2 = "#b#i" + pick.getItemId() + "# #z" + pick.getItemId()
                + "##k\r\n\r\nต้องการใช้ใบเสริมพลังกับไอเทมนี้หรือไม่? เมื่อใช้จะเพิ่ม #eAll Stat 350, ATT/MATT 250#n\r\n\r\n";
        v2 += "#eจำนวนครั้งที่เสริมพลังไปแล้ว : +" + level;
        if (1 == self.askYesNo(v2)) {
            if (level >= 10) { // 10 stars reached
                self.say("ไม่สามารถใช้กับเหรียญตรานี้ได้อีกแล้ว");
                return;
            }
            if (exchange(2432096, -1) > 0) {
                Equip equip = (Equip) pick;
                equip.setOwner(++level + " Star");
                equip.setStr((short) (equip.getStr() + 350));
                equip.setDex((short) (equip.getDex() + 350));
                equip.setInt((short) (equip.getInt() + 350));
                equip.setLuk((short) (equip.getLuk() + 350));
                equip.setMatk((short) (equip.getMatk() + 250));
                equip.setWatk((short) (equip.getWatk() + 250));

                getPlayer().send(CWvsContext.InventoryPacket.updateInventoryItem(MapleInventoryType.EQUIP, equip, false,
                        getPlayer()));

                objects.utils.FileoutputUtil.log("./TextLog/MedalEnchant.txt",
                        "Medal Enhancement Use (ItemID : " + equip.getItemId()
                                + ", Level : " + level + ", User : " + getPlayer().getName() + ")\r\n");
                self.say("ใช้งานใบเสริมพลังเรียบร้อยแล้วครับ");
            }
        }
    }

    public void consume_2434560() {
        if (!DBConfig.isGanglim) {
            final int tradeitem = 2434560;
            if (!getPlayer().haveItem(tradeitem))
                return;
            if (getPlayer().getInventory(MapleInventoryType.CASH).getNumFreeSlot() < 1) {
                self.sayOk("ไม่มีพื้นที่ว่างในกระเป๋า Cash");
                return;
            } else {
                int qty = getPlayer().getItemQuantity(tradeitem, false);
                int tradeQty = self.askNumber("ต้องการแลกกี่ชิ้นครับ?", 1, 1, Math.min(100, qty),
                        ScriptMessageFlag.NpcReplacedByUser);
                if (tradeQty > qty || tradeQty <= 0)
                    return; // 패킷핵
                if (target.exchange(tradeitem, -tradeQty) > 0) {
                    target.exchange(5062010, tradeQty);
                    self.sayOk("แลกเปลี่ยนเรียบร้อยแล้วครับ");
                }
            }
        }
    }

    public void consume_2631879() {
        if (!DBConfig.isGanglim) {
            final int tradeitem = 2631879;
            if (!getPlayer().haveItem(tradeitem))
                return;
            if (getPlayer().getInventory(MapleInventoryType.CASH).getNumFreeSlot() < 1) {
                self.sayOk("ไม่มีพื้นที่ว่างในกระเป๋า Cash");
                return;
            } else {
                int qty = getPlayer().getItemQuantity(tradeitem, false);
                int tradeQty = self.askNumber("ต้องการแลกกี่ชิ้นครับ?", 1, 1, Math.min(100, qty),
                        ScriptMessageFlag.NpcReplacedByUser);
                if (tradeQty > qty || tradeQty <= 0)
                    return; // 패킷핵
                if (target.exchange(tradeitem, -tradeQty) > 0) {
                    target.exchange(5062500, tradeQty);
                    self.sayOk("แลกเปลี่ยนเรียบร้อยแล้วครับ");
                }
            }
        }
    }

    public void consume_2439259() {
        if (!DBConfig.isGanglim) {
            final int tradeitem = 2439259;
            if (!getPlayer().haveItem(tradeitem))
                return;
            if (getPlayer().getInventory(MapleInventoryType.CASH).getNumFreeSlot() < 1) {
                self.sayOk("ไม่มีพื้นที่ว่างในกระเป๋า Cash");
                return;
            } else {
                int qty = getPlayer().getItemQuantity(tradeitem, false);
                int tradeQty = self.askNumber("ต้องการแลกกี่ชิ้นครับ?", 1, 1, Math.min(100, qty),
                        ScriptMessageFlag.NpcReplacedByUser);
                if (tradeQty > qty || tradeQty <= 0)
                    return; // 패킷핵
                if (target.exchange(tradeitem, -tradeQty) > 0) {
                    target.exchange(5062503, tradeQty);
                    self.sayOk("แลกเปลี่ยนเรียบร้อยแล้วครับ");
                }
            }
        }
    }

    public void consume_2432122() {
        unboxingStat(2432122);
    }

    public void consume_2432123() {
        unboxingStat(2432123);
    }

    public void consume_2432124() {
        unboxingStat(2432124);
    }

    public void consume_2432125() {
        unboxingStat(2432125);
    }

    public void consume_2432160() {
        unboxingStat(2432160);
    }

    public void consume_2432161() {
        unboxingStat(2432161);
    }

    public void consume_2432162() {
        unboxingStat(2432162);
    }

    public void consume_2432163() {
        unboxingStat(2432163);
    }

    public void unboxingStat(int itemID) {
        initNPC(MapleLifeFactory.getNPC(2500000));

        if (itemID >= 2432160 && itemID <= 2432163) {
            self.say("ดูเหมือนว่าจะสามารถเปิดกล่องที่ถูกผนึกได้ หากใช้กุญแจที่มีระดับเหมาะสม", ScriptMessageFlag.Self);
            return;
        }
        int needItem = 0;
        if (itemID == 2432122) { // Grade.1
            needItem = 2432160;
            if (0 >= getPlayer().getItemQuantity(2432160, false)) {
                self.say("#b#i2432160# #z2432160##k() หากไม่มีสิ่งนี้ก็ไม่มีประโยชน์อะไรเลย", ScriptMessageFlag.Self);
                return;
            }
        } else if (itemID == 2432123) { // Grade.2
            needItem = 2432161;
            if (0 >= getPlayer().getItemQuantity(2432161, false)) {
                self.say("#b#i2432161# #z2432161##k() หากไม่มีสิ่งนี้ก็ไม่มีประโยชน์อะไรเลย", ScriptMessageFlag.Self);
                return;
            }
        } else if (itemID == 2432124) { // Grade.3
            needItem = 2432162;
            if (0 >= getPlayer().getItemQuantity(2432162, false)) {
                self.say("#b#i2432162# #z2432162##k() หากไม่มีสิ่งนี้ก็ไม่มีประโยชน์อะไรเลย", ScriptMessageFlag.Self);
                return;
            }
        } else if (itemID == 2432125) { // Grade.4
            needItem = 2432163;
            if (0 >= getPlayer().getItemQuantity(2432163, false)) {
                self.say("#b#i2432163# #z2432163##k() หากไม่มีสิ่งนี้ก็ไม่มีประโยชน์อะไรเลย", ScriptMessageFlag.Self);
                return;
            }
        }
        if (needItem == 0) {
            return;
        }
        String v0 = "เมื่อเสียบกุญแจเข้าไปในกล่องที่ล็อคแน่น ก็เริ่มสัมผัสได้ถึงพลังที่แปลกประหลาด\r\nพลังที่ซ่อนอยู่ซึ่งไม่เคยรู้มาก่อนม\r\n\r\nนี่มัน……\r\n\r\n";
        if (itemID == 2432122) { // Grade.1
            v0 += "#b#L0##eSTR +2#n น่ะสิ#l\r\n";
            v0 += "#b#L1##eDEX +2#n น่ะสิ#l\r\n";
            v0 += "#b#L2##eINT +2#n น่ะสิ#l\r\n";
            v0 += "#b#L3##eLUK +2#n น่ะสิ#l\r\n";
            v0 += "\r\n\r\n#r※ เพิ่มค่าสถานะตามที่เลือก";
            int v1 = self.askMenu(v0, ScriptMessageFlag.Self);
            if (target.exchange(itemID, -1, needItem, -1) > 0) {
                if (v1 == 0) {
                    getPlayer().updateOneInfo(1237777, "str",
                            String.valueOf(getPlayer().getOneInfoQuestInteger(1237777, "str") + 2));
                } else if (v1 == 1) {
                    getPlayer().updateOneInfo(1237777, "dex",
                            String.valueOf(getPlayer().getOneInfoQuestInteger(1237777, "dex") + 2));
                } else if (v1 == 2) {
                    getPlayer().updateOneInfo(1237777, "int_",
                            String.valueOf(getPlayer().getOneInfoQuestInteger(1237777, "int_") + 2));
                } else if (v1 == 3) {
                    getPlayer().updateOneInfo(1237777, "luk",
                            String.valueOf(getPlayer().getOneInfoQuestInteger(1237777, "luk") + 2));
                }
                String v2 = "#e[พลังที่ถูกปลดปล่อยจนถึงปัจจุบัน]#n\r\n\r\n#e#r";
                v2 += displayLiberationStats();
                self.say(v2, ScriptMessageFlag.Self);
                // objects.utils.FileoutputUtil.log("./TextLog/LiberationStat.txt", "Unseal
                // (Grade.1, rand : " + v1 + ", User : " + getPlayer().getName() + ")\r\n");

                StringBuilder sb = new StringBuilder(
                        "Unseal (Grade.1, rand : " + v1 + ", User : " + getPlayer().getName() + ")");
                LoggingManager.putLog(new ConsumeLog(getPlayer(), itemID, sb));
            }
        } else if (itemID == 2432123) { // Grade.2
            v0 += "#b#L0##eSTR +20#n น่ะสิ#l\r\n";
            v0 += "#b#L1##eDEX +20#n น่ะสิ#l\r\n";
            v0 += "#b#L2##eINT +20#n น่ะสิ#l\r\n";
            v0 += "#b#L3##eLUK +20#n น่ะสิ#l\r\n";
            v0 += "\r\n\r\n#r※ เพิ่มค่าสถานะตามที่เลือก";
            int v1 = self.askMenu(v0, ScriptMessageFlag.Self);
            if (target.exchange(itemID, -1, needItem, -1) > 0) {
                if (v1 == 0) {
                    getPlayer().updateOneInfo(1237777, "str",
                            String.valueOf(getPlayer().getOneInfoQuestInteger(1237777, "str") + 20));
                } else if (v1 == 1) {
                    getPlayer().updateOneInfo(1237777, "dex",
                            String.valueOf(getPlayer().getOneInfoQuestInteger(1237777, "dex") + 20));
                } else if (v1 == 2) {
                    getPlayer().updateOneInfo(1237777, "int_",
                            String.valueOf(getPlayer().getOneInfoQuestInteger(1237777, "int_") + 20));
                } else if (v1 == 3) {
                    getPlayer().updateOneInfo(1237777, "luk",
                            String.valueOf(getPlayer().getOneInfoQuestInteger(1237777, "luk") + 20));
                }
                String v2 = "#e[พลังที่ถูกปลดปล่อยจนถึงปัจจุบัน]#n\r\n\r\n#e#r";
                v2 += displayLiberationStats();
                self.say(v2, ScriptMessageFlag.Self);
                // objects.utils.FileoutputUtil.log("./TextLog/LiberationStat.txt", "Unseal
                // (Grade.2, rand : " + v1 + ", User : " + getPlayer().getName() + ")\r\n");

                StringBuilder sb = new StringBuilder(
                        "Unseal (Grade.2, rand : " + v1 + ", User : " + getPlayer().getName() + ")");
                LoggingManager.putLog(new ConsumeLog(getPlayer(), itemID, sb));
            }
        } else if (itemID == 2432124) { // Grade.3
            if (target.exchange(itemID, -1, needItem, -1) > 0) {
                int rand = Randomizer.rand(0, 3);

                if (rand == 0) {
                    getPlayer().updateOneInfo(1237777, "str",
                            String.valueOf(getPlayer().getOneInfoQuestInteger(1237777, "str") + 50));
                } else if (rand == 1) {
                    getPlayer().updateOneInfo(1237777, "dex",
                            String.valueOf(getPlayer().getOneInfoQuestInteger(1237777, "dex") + 50));
                } else if (rand == 2) {
                    getPlayer().updateOneInfo(1237777, "int_",
                            String.valueOf(getPlayer().getOneInfoQuestInteger(1237777, "int_") + 50));
                } else if (rand == 3) {
                    getPlayer().updateOneInfo(1237777, "luk",
                            String.valueOf(getPlayer().getOneInfoQuestInteger(1237777, "luk") + 50));
                }
                String[] stats = new String[] {
                        "STR", "DEX", "INT", "LUK"
                };
                String v2 = String.format(
                        "ดูสิ นี่คือ #b#e" + stats[rand] + "#n ไงล่ะ\r\nรู้สึกว่าเพิ่มขึ้นมา %s เชียวนะ", "+50");
                self.say(v2, ScriptMessageFlag.Self);
                // objects.utils.FileoutputUtil.log("./TextLog/LiberationStat.txt", "Unseal
                // (Grade.3, rand : " + rand + ", User : " + getPlayer().getName() + ")\r\n");

                StringBuilder sb = new StringBuilder(
                        "Unseal (Grade.3, rand : " + rand + ", User : " + getPlayer().getName() + ")");
                LoggingManager.putLog(new ConsumeLog(getPlayer(), itemID, sb));
            }
        } else if (itemID == 2432125) { // Grade.4
            if (target.exchange(itemID, -1, needItem, -1) > 0) {
                int rand = Randomizer.rand(0, 4);

                if (rand == 0) {
                    getPlayer().updateOneInfo(1237777, "pad",
                            String.valueOf(getPlayer().getOneInfoQuestInteger(1237777, "pad") + 2));
                } else if (rand == 1) {
                    getPlayer().updateOneInfo(1237777, "mad",
                            String.valueOf(getPlayer().getOneInfoQuestInteger(1237777, "mad") + 2));
                } else if (rand == 2) {
                    getPlayer().updateOneInfo(1237777, "bdr",
                            String.valueOf(getPlayer().getOneInfoQuestInteger(1237777, "bdr") + 2));
                } else if (rand == 3) {
                    getPlayer().updateOneInfo(1237777, "imdr",
                            String.valueOf(getPlayer().getOneInfoQuestInteger(1237777, "imdr") + 2));
                } else if (rand == 4) {
                    getPlayer().updateOneInfo(1237777, "all_stat_r",
                            String.valueOf(getPlayer().getOneInfoQuestInteger(1237777, "all_stat_r") + 5));
                }
                String[] stats = new String[] {
                        "ATT", "MATT", "Boss Damage", "Ignore Enemy Defense", "All Stat"
                };
                String v2 = String.format(
                        "ดูสิ นี่คือ #b#e" + stats[rand] + "#n ไงล่ะ\r\nรู้สึกว่าเพิ่มขึ้นมา %s เชียวนะ",
                        (rand == 4 ? "5%" : "2%"));
                self.say(v2, ScriptMessageFlag.Self);
                // objects.utils.FileoutputUtil.log("./TextLog/LiberationStat.txt", "Unseal
                // (Grade.4, rand : " + rand + ", User : " + getPlayer().getName() + ")\r\n");

                StringBuilder sb = new StringBuilder(
                        "Unseal (Grade.4, rand : " + rand + ", User : " + getPlayer().getName() + ")");
                LoggingManager.putLog(new ConsumeLog(getPlayer(), itemID, sb));
            }
        }
        getPlayer().checkLiberationStats();
    }

    public String displayLiberationStats() {
        String ret = "";

        int str = getPlayer().getOneInfoQuestInteger(1237777, "str");
        int dex = getPlayer().getOneInfoQuestInteger(1237777, "dex");
        int int_ = getPlayer().getOneInfoQuestInteger(1237777, "int_");
        int luk = getPlayer().getOneInfoQuestInteger(1237777, "luk");
        int pad = getPlayer().getOneInfoQuestInteger(1237777, "pad");
        int mad = getPlayer().getOneInfoQuestInteger(1237777, "mad");
        int bdr = getPlayer().getOneInfoQuestInteger(1237777, "bdr");
        int imdr = getPlayer().getOneInfoQuestInteger(1237777, "imdr");
        int allStatR = getPlayer().getOneInfoQuestInteger(1237777, "all_stat_r");
        int totalTE = getPlayer().getTotalTranscendenceEnchant();
        double scale = 1.0;
        if (totalTE >= 72 && totalTE < 80) {
            scale = 1.2;
        } else if (totalTE >= 80) {
            scale = 1.5;
        }
        if (scale > 1.0) {
            str *= scale;
            dex *= scale;
            int_ *= scale;
            luk *= scale;
        }
        ret += String.format("STR +%d\r\n", str);
        ret += String.format("DEX +%d\r\n", dex);
        ret += String.format("INT +%d\r\n", int_);
        ret += String.format("LUK +%d\r\n", luk);
        ret += String.format("All Stat +%s\r\n", allStatR + "%");
        ret += String.format("ATT +%s\r\n", pad + "%");
        ret += String.format("MATT +%s\r\n", mad + "%");
        ret += String.format("Boss Damage +%s\r\n", bdr + "%");
        ret += String.format("Ignore Enemy Defense +%s\r\n", imdr + "%");
        if (scale > 1.0) {
            ret += "\r\n#nตัวเลือกข้างต้นถูกคูณด้วย " + scale + " เท่า (โบนัส Transcendence Enhancement)";
        }

        return ret;
    }

    public void pickStatItem(boolean hongbo) {
        NumberFormat nf = NumberFormat.getInstance();
        if (hongbo) {
            if (getPlayer().getHongboPoint() < 6000) {
                self.say("ดูเหมือนว่า Promotion Point จะไม่พอสินะ");
                return;
            }
        } else {
            if (getPlayer().getRealCash() < 2000) {
                self.say("ดูเหมือนว่า Jin Points จะไม่พอสินะ");
                return;
            }
        }
        if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 3) {
            self.say("ช่องใน #bกระเป๋า Use#k ไม่เพียงพอ");
            return;
        }

        if (1 == self.askYesNo(String.format(
                "ต้องการใช้ #b" + (hongbo ? "6,000" : "2,000")
                        + " %s Point#k เพื่อสุ่มรับ #b'Sealed Box' หรือ 'Unsealing Key'#k จริงๆ งั้นเหรอ?\r\n\r\n#e#kแต้มที่มี : %s",
                hongbo ? "Promotion" : "Jin",
                hongbo ? nf.format(getPlayer().getHongboPoint()) : nf.format(getPlayer().getRealCash())))) {
            int rand = Randomizer.rand(0, 100);
            int reward = 0;
            if (rand < 60) {
                if (Randomizer.isSuccess(50)) {
                    reward = 2432122;
                } else {
                    reward = 2432160;
                }
            } else if (rand >= 60 && rand < 90) {
                if (Randomizer.isSuccess(50)) {
                    reward = 2432123;
                } else {
                    reward = 2432161;
                }
            } else if (rand >= 90 && rand < 98) {
                if (Randomizer.isSuccess(50)) {
                    reward = 2432124;
                } else {
                    reward = 2432162;
                }
            } else if (rand >= 98) {
                if (Randomizer.isSuccess(50)) {
                    reward = 2432125;
                } else {
                    reward = 2432163;
                }
            }

            if (reward != 0) {
                if (target.exchange(reward, 1) > 0) {
                    if (hongbo) {
                        getPlayer().gainHongboPoint(-6000, true);
                    } else {
                        getPlayer().gainRealCash(-2000, true);
                    }

                    StringBuilder sb = new StringBuilder(
                            "Sealed Stat Draw Result (Draw ItemID : " + reward + ", Currency Used : "
                                    + (hongbo ? "Promotion Point" : "Jin: True Point") + ", User : "
                                    + getPlayer().getName() + ")");
                    LoggingManager.putLog(new ConsumeLog(getPlayer(), 1, sb));

                    String v0 = "#bได้รับ #i" + reward + "# #z" + reward + "# 1 ชิ้น\r\n\r\n#k#eแต้มคงเหลือ : "
                            + (hongbo ? nf.format(getPlayer().getHongboPoint()) : nf.format(getPlayer().getRealCash()))
                            + "\r\n#n#b#L0#สุ่มอีกครั้ง#l\r\n#L1#จบการสนทนา#l";
                    int v1 = self.askMenu(v0);
                    if (v1 == 0) {
                        pickStatItem(hongbo);
                    }
                }
            }
        }
    }

    public void rita_library() {
        if (DBConfig.isGanglim) {
            return;
        }
        initNPC(MapleLifeFactory.getNPC(2500000));
        String v0 = "สวัสดี #h0#!\r\nได้ยินข่าวเรื่องกล่องที่มีพลังประหลาดที่ถูกค้นพบใน Maple World บ้างไหม?\r\n\r\n";
        v0 += "เขาว่ากันว่าไม่ว่าจะใช้วิธีไหนก็ไม่สามารถเปิดกล่องนี้ได้ สงสัยต้องหากุญแจที่เข้ากับรูได้พอดีซะแล้วสิ……\r\n\r\n#b";
        v0 += "#L0#ตรวจสอบพลังที่ปลดปล่อยอยู่ในปัจจุบัน#l\r\n";
        v0 += "#L2#ใช้แต้มเพื่อสุ่มรับกล่องหรือกุญแจ#l\r\n";
        v0 += "#L1#ฟังรายละเอียดเกี่ยวกับกล่องเพิ่มเติม#l";
        int v1 = self.askMenu(v0);
        if (v1 == 0) {
            String v2 = "#e[พลังที่ปลดปล่อยอยู่ในปัจจุบัน]#n\r\n\r\n#e#r";
            v2 += displayLiberationStats();
            v2 += "#n\r\n#b#L0#กลับสู่เมนูหลัก#l\r\n#L1#จบการสนทนา#l";
            int v3 = self.askMenu(v2);
            if (v3 == 0) {
                rita_library();
            }
        } else if (v1 == 1) {
            String v2 = "หาก #bUnsealing Key#k และ #bSealed Box#k มี Grade เดียวกัน จะสามารถปลดปล่อยพลังตามระดับนั้นได้\r\n";
            v2 += "Grade มีทั้งหมด #e4 ระดับ คือ 1, 2, 3, 4#n ยิ่งตัวเลขสูง พลังที่ถูกผนึกก็ยิ่งแข็งแกร่ง\r\n\r\n";
            v2 += "สามารถรับ #bSealed Box#k และ #bUnsealing Key#k ได้จากการกำจัดบอสที่มีความยากระดับหนึ่งขึ้นไป,\r\n";
            v2 += "หรือใช้ #rJin Point, Promotion Point#k เพื่อ #eสุ่มระดับ 1~4#n ได้\r\n\r\n#b";
            v2 += "#L0#อยากรู้เกี่ยวกับโอกาสดรอปจากบอส#l\r\n";
            v2 += "#L1#อยากรู้เกี่ยวกับแต้มที่ใช้และพลังที่ปลดปล่อยได้#l\r\n";
            int v3 = self.askMenu(v2);
            if (v3 == 0) {
                String v4 = "#e- Hard Black Mage\r\n";
                v4 += "- Hard Seren\r\n";
                v4 += "- Chaos Kalos\r\n\r\n#n#k";
                v4 += "บอสเหล่านี้มีโอกาสดรอปทั้งหมด สำหรับข้อมูลโอกาสโดยละเอียด กรุณาตรวจสอบตารางโอกาสที่หน้าเว็บไซต์";
                v4 += "#n\r\n#b#L0#กลับสู่เมนูหลัก#l\r\n#L1#จบการสนทนา#l";
                int v5 = self.askMenu(v4);
                if (v5 == 0) {
                    rita_library();
                }
            } else if (v3 == 1) {
                String v4 = "- ใช้ #rJin Point 2,000#k เพื่อรับ #b'Sealed Box' หรือ 'Unsealing Key' ระดับ Grade.1~Grade.4#k\r\n";
                v4 += "- ใช้ #rPromotion Point 6,000#k เพื่อรับ #b'Sealed Box' หรือ 'Unsealing Key' ระดับ Grade.1~Grade.4#k\r\n\r\n";
                v4 += "Sealed Box และ Unsealing Key มีโอกาสได้รับแตกต่างกันตามระดับ และเมื่อปลดปล่อยแล้ว Stat ที่เพิ่มขึ้นก็จะแตกต่างกันด้วย";
                self.say(v4);
                String v5 = "#eGrade.1 (60%) - กล่อง 30%, กุญแจ 30%#n\r\n";
                v5 += "#bเพิ่ม Stat ที่เลือก 2 หน่วย (จาก STR, DEX, INT, LUK)#k\r\n";
                v5 += "#eGrade.2 (30%) - กล่อง 15%, กุญแจ 15%#n\r\n";
                v5 += "#bเพิ่ม Stat ที่เลือก 20 หน่วย (จาก STR, DEX, INT, LUK)#k\r\n";
                v5 += "#eGrade.3 (8%) - กล่อง 4%, กุญแจ 4%#n\r\n";
                v5 += "#bเพิ่ม Stat แบบสุ่ม 50 หน่วย (จาก STR, DEX, INT, LUK)#k\r\n";
                v5 += "#eGrade.4 (2%) - กล่อง 1%, กุญแจ 1%#n\r\n";
                v5 += "#bเพิ่ม Stat แบบสุ่ม 2% (ATT, MATT, Boss Dmg, IED, All Stat%) (All Stat คือ 5%)\r\n";
                v5 += "#n\r\n#b#L0#กลับสู่เมนูหลัก#l\r\n#L1#จบการสนทนา#l";
                int v6 = self.askMenu(v5);
                if (v6 == 0) {
                    rita_library();
                }
            }
        } else if (v1 == 2) {
            String v2 = "ต้องการใช้แต้มอะไรเพื่อสุ่มรับ #b'Sealed Box' หรือ 'Unsealing Key'#k?\r\n\r\n#b";
            v2 += "#L0#ใช้ #eJin Point#n (2,000 Point)#l\r\n";
            v2 += "#L1#ใช้ #ePromotion Point#n (6,000 Point)#l\r\n";
            v2 += "#L2#จบการสนทนา#l\r\n";
            int v3 = self.askMenu(v2);
            if (v3 == 0) {
                pickStatItem(false);
            } else if (v3 == 1) {
                pickStatItem(true);
            }
        }
    }

    public void consume_2435873() {
        if (DBConfig.isGanglim) {
            return;
        }
        int[] itemList = new int[] {
                1012632, 1022278, 1132308, 1162080, 1162081, 1162082, 1162083
        };
        unboxingItem(2435873, itemList);
    }

    public void consume_2435874() {
        if (DBConfig.isGanglim) {
            return;
        }
        int[] itemList = new int[] {
                1113306, 1032316, 1122430
        };
        unboxingItem(2435874, itemList);
    }

    public void consume_2435875() {
        if (DBConfig.isGanglim) {
            return;
        }
        int[] itemList = new int[] {
                1182285, 1190555, 1190556, 1190557, 1190558, 1190559
        };
        unboxingItem(2435875, itemList);
    }

    public void consume_2435876() {
        if (DBConfig.isGanglim) {
            return;
        }
        int[] itemList = new int[] {
                1012632, 1022278, 1132308, 1162080, 1162081, 1162082, 1162083, 1113306, 1032316, 1122430, 1182285,
                1190555, 1190556, 1190557, 1190558, 1190559
        };
        String v0 = "สามารถเลือกรับไอเทมด้านล่างได้ 1 อย่าง คุณต้องการเลือกไอเทมไหน?\r\n\r\n#b";
        for (int i = 0; i < itemList.length; ++i) {
            v0 += "#L" + i + "##i" + itemList[i] + "# #z" + itemList[i] + "##l\r\n";
        }
        int v1 = self.askMenu(v0);
        if (target.exchange(2435876, -1, itemList[v1], 1) > 0) {
            self.sayOk("ได้รับ #b#i" + itemList[v1] + "# #z" + itemList[v1] + "##k 1 ชิ้นแล้ว");
        } else {
            self.sayOk("กรุณาทำช่องในกระเป๋า Equip ให้ว่างแล้วลองใหม่ครับ");
        }
    }

    public void unboxingItem(int consumeID, int[] itemList) {
        initNPC(MapleLifeFactory.getNPC(9010000));
        String v0 = "เมื่อเปิดกล่องจะสุ่มรับไอเทมด้านล่าง 1 อย่าง ต้องการเปิดเลยหรือไม่?\r\n\r\n#b";
        for (int itemID : itemList) {
            v0 += "#i" + itemID + "# #z" + itemID + "#\r\n";
        }
        if (1 == self.askYesNo(v0)) {
            if (target.exchange(consumeID, -1) > 0) {
                int rand = Randomizer.rand(0, itemList.length - 1);
                int pick = itemList[rand];

                if (target.exchange(pick, 1) > 0) {
                    self.sayOk("ได้รับ #b#i" + pick + "# #z" + pick + "##k 1 ชิ้นแล้ว");
                } else {
                    self.sayOk("กรุณาทำช่องในกระเป๋า Equip ให้ว่างแล้วลองใหม่ครับ");
                }
            } else {
                self.sayOk("เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุ");
            }
        }
    }

    // Precise Carved Stone
    public void consume_2432126() {
        equipStone(2432126);
    }

    // Normal Carved Stone
    public void consume_2432127() {
        equipStone(2432127);
    }

    public void equipStone(int itemID) {
        initNPC(MapleLifeFactory.getNPC(9010000));
        String v0 = "";
        if (DBConfig.isGanglim) {
            v0 = "#fs11#กรุณาเลือกช่องที่ต้องการสวมใส่ #r#eหินแกะสลักที่สวมใส่ในช่องที่เลือกจะถูกทำลาย#n#k โปรดเลือกอย่างระมัดระวัง\r\n\r\n";
        } else {
            v0 = "กรุณาเลือกช่องที่ต้องการสวมใส่ #r#eหินแกะสลักที่สวมใส่ในช่องที่เลือกจะถูกทำลาย#n#k โปรดเลือกอย่างระมัดระวัง\r\n\r\n";
        }

        String empty = "#fc0xFF6600CC##fUI/UIWindow.img/IconBase/0#";
        String icon = "#fs11##fc0xFF6600CC##i";
        int item1 = getPlayer().getOneInfoQuestInteger(133333, "equip1");
        int item2 = getPlayer().getOneInfoQuestInteger(133333, "equip2");
        int item3 = getPlayer().getOneInfoQuestInteger(133333, "equip3");
        int item4 = getPlayer().getOneInfoQuestInteger(133333, "equip4");
        int item5 = getPlayer().getOneInfoQuestInteger(133333, "equip5");

        String lock1 = getPlayer().getOneInfoQuestInteger(133333, "lock1") > 0 ? "#r(ล็อค)" : "#b(เปิด)";
        String lock2 = getPlayer().getOneInfoQuestInteger(133333, "lock2") > 0 ? "#r(ล็อค)" : "#b(เปิด)";
        String lock3 = getPlayer().getOneInfoQuestInteger(133333, "lock3") > 0 ? "#r(ล็อค)" : "#b(เปิด)";
        String lock4 = getPlayer().getOneInfoQuestInteger(133333, "lock4") > 0 ? "#r(ล็อค)" : "#b(เปิด)";
        String lock5 = getPlayer().getOneInfoQuestInteger(133333, "lock5") > 0 ? "#r(ล็อค)" : "#b(เปิด)";

        v0 += "#e[หินแกะสลักที่สวมใส่บนแผ่นหิน]#n\r\n";
        v0 += item1 > 0 ? (icon + item1 + "# ") : (empty + " ");
        v0 += item2 > 0 ? (icon + item2 + "# ") : (empty + " ");
        v0 += item3 > 0 ? (icon + item3 + "# ") : (empty + " ");
        v0 += item4 > 0 ? (icon + item4 + "# ") : (empty + " ");
        v0 += item5 > 0 ? (icon + item5 + "# ") : (empty + " ");

        v0 += "\r\n" + lock1 + " " + lock2 + " " + lock3 + " " + lock4 + " " + lock5;

        // int unlock1 = getPlayer().getOneInfoQuestInteger(133333, "unlock1");
        // int unlock2 = getPlayer().getOneInfoQuestInteger(133333, "unlock2");

        v0 += "\r\n\r\n#b#L0#สวมใส่ในช่องที่ 1#l\r\n";
        v0 += "#b#L1#สวมใส่ในช่องที่ 2#l\r\n";
        v0 += "#b#L2#สวมใส่ในช่องที่ 3#l\r\n";
        v0 += "#b#L3#สวมใส่ในช่องที่ 4#l\r\n";
        v0 += "#b#L4#สวมใส่ในช่องที่ 5#l\r\n";

        int v1 = self.askMenu(v0);
        int itemID_ = 0;
        if (v1 == 0) {
            itemID_ = item1;
        } else if (v1 == 1) {
            itemID_ = item2;
        } else if (v1 == 2) {
            itemID_ = item3;
        } else if (v1 == 3) {
            itemID_ = item4;
        } else if (v1 == 4) {
            itemID_ = item5;
        }
        String v2 = "";
        if (DBConfig.isGanglim) {
            v2 = "#fs11#ต้องการสวมใส่ใน #eช่องที่ " + (v1 + 1) + "#n จริงหรือไม่?\r\n\r\n";
        } else {
            v2 = "ต้องการสวมใส่ใน #eช่องที่ " + (v1 + 1) + "#n จริงหรือไม่?\r\n\r\n";
        }

        if (itemID_ == 0) {
            v2 += "#bปัจจุบันช่องดังกล่าวไม่มีหินแกะสลักสวมใส่อยู่";
        } else {
            v2 += "#b#i" + itemID_ + "# #z" + itemID_
                    + "##k ถูกสวมใส่อยู่\r\n#e#rหากสวมใส่ หินแกะสลักดังกล่าวจะถูกทำลาย";
        }
        if (1 == self.askYesNo(v2)) {
            if (getPlayer().getOneInfoQuestInteger(133333, "lock" + (v1 + 1)) > 0) {
                if (DBConfig.isGanglim) {
                    self.say("#fs11##rช่องดังกล่าวถูกล็อคอยู่ ไม่สามารถสวมใส่ได้");
                } else {
                    self.say("#rช่องดังกล่าวถูกล็อคอยู่ ไม่สามารถสวมใส่ได้");
                }
                return;
            }
            if (target.exchange(itemID, -1) > 0) {
                int index = v1 + 1;
                getPlayer().updateOneInfo(133333, "equip" + index, String.valueOf(itemID));

                getPlayer().updateOneInfo(133333, "craftPlus" + index, "0");
                getPlayer().updateOneInfo(133333, "craftMinus" + index, "0");
                getPlayer().updateOneInfo(133333, "plusOption" + index, "0");
                getPlayer().updateOneInfo(133333, "minusOption" + index, "0");
                getPlayer().updateOneInfo(133333, "plusValue" + index, "0");
                getPlayer().updateOneInfo(133333, "minusValue" + index, "0");
                getPlayer().checkImprintedStone();
                if (DBConfig.isGanglim) {
                    self.say("#fs11#สวมใส่เรียบร้อยแล้วครับ");
                } else {
                    self.say("สวมใส่เรียบร้อยแล้วครับ");
                }

                StringBuilder sb = new StringBuilder(
                        "Carved Stone Equip (Slot : " + index + ", ItemID : " + itemID + ", User : "
                                + getPlayer().getName()
                                + ")");
                LoggingManager.putLog(new ConsumeLog(getPlayer(), itemID, sb));

            }
        }
    }

    public void HonorTransmission() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        String v0 = "#e[ถ่ายโอนออปชั่นเหรียญตรา]#n\r\n";
        v0 += "กรุณาเลือกเหรียญตราที่จะใช้ถ่ายโอนออปชั่น ออปชั่นเสริมพลังของเหรียญตราดังกล่าวจะถูกถ่ายโอน เฉพาะออปชั่นการเสริมพลังเท่านั้นที่จะถูกถ่ายโอน\r\n\r\n#b";
        boolean find = false;
        for (Item item : getPlayer().getInventory(MapleInventoryType.EQUIP).list()) {
            if (item.getItemId() / 10000 == 114) {
                if (!DBConfig.isGanglim) {
                    if (GameConstants.isJinEndlessMedal(item.getItemId()))
                        continue;
                }
                v0 += "#L" + item.getPosition() + "##i" + item.getItemId() + "# #z" + item.getItemId() + "##l\r\n";
                if (!find)
                    find = true;
            }
        }
        if (!find) {
            v0 += "ไม่มีเหรียญตราในครอบครอง";
            self.say(v0);
            return;
        }
        int v1 = self.askMenu(v0);
        Item item = getPlayer().getInventory(MapleInventoryType.EQUIP).getItem((short) v1);
        if (item == null || item.getItemId() / 10000 != 114) {
            self.say("การเข้าถึงผิดพลาด");
            return;
        }
        Equip baseEquip = (Equip) item;

        String v2 = "#e[ถ่ายโอนออปชั่นเหรียญตรา]#n\r\n";
        v2 += "กรุณาเลือกเหรียญตราที่จะรับการถ่ายโอนออปชั่น\r\n\r\n";
        v2 += "#eเหรียญตราต้นทาง : #i" + baseEquip.getItemId() + "# #z" + baseEquip.getItemId() + "##n#b\r\n\r\n";
        find = false;
        for (Item it : getPlayer().getInventory(MapleInventoryType.EQUIP).list()) {
            if (it.getItemId() / 10000 == 114 && it.getPosition() != v1) {
                if (!DBConfig.isGanglim) {
                    if (GameConstants.isJinEndlessMedal(it.getItemId()))
                        continue;
                }
                v2 += "#L" + it.getPosition() + "##i" + it.getItemId() + "# #z" + it.getItemId() + "##l\r\n";
                if (!find)
                    find = true;
            }
        }
        if (!find) {
            v2 += "ไม่มีเหรียญตราในครอบครอง";
            self.say(v2);
            return;
        }
        int v3 = self.askMenu(v2);
        Item item2 = getPlayer().getInventory(MapleInventoryType.EQUIP).getItem((short) v3);
        if (item2 == null || item2.getItemId() / 10000 != 114) {
            self.say("การเข้าถึงผิดพลาด");
            return;
        }
        Equip targetEquip = (Equip) item2;

        if (1 == self.askYesNo(
                "#e[ถ่ายโอนออปชั่นเหรียญตรา]#n\r\nต้องการถ่ายโอนไปยังเหรียญตรานี้หรือไม่? ไม่สามารถใช้กับเหรียญตราที่มีการเสริมพลังอยู่แล้วได้\r\n\r\n#eเหรียญตราต้นทาง : #i"
                        + baseEquip.getItemId() + "# #z" + baseEquip.getItemId() + "#\r\nเหรียญตราปลายทาง : #i"
                        + targetEquip.getItemId() + "# #z" + targetEquip.getItemId() + "#")) {
            if (baseEquip.getOwner() == null || baseEquip.getOwner().isEmpty()
                    || targetEquip.getOwner() != null && !targetEquip.getOwner().isEmpty()) {
                self.say(
                        "การถ่ายโอนล้มเหลวเนื่องจากเหรียญตราต้นทางไม่มีการเสริมพลัง หรือเหรียญตราปลายทางมีการเสริมพลังอยู่แล้ว จึงไม่สามารถถ่ายโอนได้");
                return;
            }
            int baseLevel = Integer.parseInt(baseEquip.getOwner().replaceAll("[^0-9]", ""));
            baseEquip.setStr((short) (baseEquip.getStr() - (350 * baseLevel)));
            baseEquip.setDex((short) (baseEquip.getDex() - (350 * baseLevel)));
            baseEquip.setInt((short) (baseEquip.getInt() - (350 * baseLevel)));
            baseEquip.setLuk((short) (baseEquip.getLuk() - (350 * baseLevel)));
            baseEquip.setWatk((short) (baseEquip.getWatk() - (250 * baseLevel)));
            baseEquip.setMatk((short) (baseEquip.getMatk() - (250 * baseLevel)));
            baseEquip.setOwner("");

            targetEquip.setStr((short) (targetEquip.getStr() + (350 * baseLevel)));
            targetEquip.setDex((short) (targetEquip.getDex() + (350 * baseLevel)));
            targetEquip.setInt((short) (targetEquip.getInt() + (350 * baseLevel)));
            targetEquip.setLuk((short) (targetEquip.getLuk() + (350 * baseLevel)));
            targetEquip.setWatk((short) (targetEquip.getWatk() + (250 * baseLevel)));
            targetEquip.setMatk((short) (targetEquip.getMatk() + (250 * baseLevel)));
            targetEquip.setOwner(baseLevel + " Star");

            getPlayer().send(CWvsContext.InventoryPacket.updateInventoryItem(MapleInventoryType.EQUIP, baseEquip, false,
                    getPlayer()));
            getPlayer().send(CWvsContext.InventoryPacket.updateInventoryItem(MapleInventoryType.EQUIP, targetEquip,
                    false, getPlayer()));
            self.say("การถ่ายโอนเรียบร้อยแล้ว");
        }
    }

    public void consume_2434287() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (1 == self.askYesNo("ต้องการใช้ Honor Exp 10,000 เพื่อรับ #b#i2434290# #z2434290# 1 ชิ้น#k หรือไม่?")) {
            if (getPlayer().getInnerExp() < 10000) {
                self.say("Honor Exp ไม่พอ ไม่สามารถใช้งานได้");
                return;
            }
            if (target.exchange(2434287, -1, 2434290, 1) == 1) {
                getPlayer().addHonorExp(-10000);
                self.say("แลกเปลี่ยนเรียบร้อยแล้วครับ");
            }

        }
    }

    public void consume_2438116() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (1 == self.askYesNo(
                "ต้องการเปิด #b#i2438116# #z2438116##k เพื่อรับไอเทมด้านล่างนี้หรือไม่?\r\n\r\n#b#i5060048# #z5060048# 5 ชิ้น\r\n#i2434558# #z2434558# 1 ชิ้น\r\n#i5680157# #z5680157# 3 ชิ้น\r\n#i5068300# #z5068300# 5 ชิ้น\r\n#i2028273# #z2028273# 5 ชิ้น\r\n#i5680409# #z5680409# 1 ชิ้น")) {
            if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 3 ||
                    getPlayer().getInventory(MapleInventoryType.CASH).getNumFreeSlot() < 4) {
                self.say(
                        "กรุณาทำช่องใน #bกระเป๋า Use#k ให้ว่าง 3 ช่อง และ #bกระเป๋า Cash#k ให้ว่าง 4 ช่อง แล้วลองใหม่ครับ");
                return;
            }
            if (target.exchange(2438116, -1, 5060048, 5, 2434558, 1, 5680157, 3, 2028273, 5, 5680409, 1) == 1) {
                Item wonderBerry = new Item(5068300, (short) 1, (short) 5, (short) ItemFlag.KARMA_USE.getValue());
                MapleInventoryManipulator.addFromDrop(getClient(), wonderBerry, true);
                self.say("ได้รับไอเทมเรียบร้อยแล้วครับ");
            }
        }
    }

    public void consume_2630009() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (1 == self.askYesNo(
                "ต้องการเปิด #b#i2630009# #z2630009##k เพื่อรับไอเทมด้านล่างนี้หรือไม่?\r\n\r\n#b#i4034803# #z4034803# 1 ชิ้น")) {
            if (getPlayer().getInventory(MapleInventoryType.ETC).getNumFreeSlot() < 1) {
                self.say("กรุณาทำช่องใน #bกระเป๋า Etc#k ให้ว่าง 1 ช่อง แล้วลองใหม่ครับ");
                return;
            }
            if (target.exchange(2630009, -1, 4034803, 1) == 1) {
                self.say("ได้รับไอเทมเรียบร้อยแล้วครับ");
            }
        }
    }

}
