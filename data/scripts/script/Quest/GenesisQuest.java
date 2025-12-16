package script.Quest;

import constants.GameConstants;
import network.SendPacketOpcode;
import network.center.Center;
import network.encode.PacketEncoder;
import network.models.CWvsContext;
import objects.context.GoldenChariot;
import objects.item.*;
import objects.quest.MapleQuest;
import objects.users.enchant.*;
import objects.utils.HexTool;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GenesisQuest extends ScriptEngineNPC {
    public void q2000019s() {
        if (self.askAccept(
                "#e#r[Jin:眞]#k#n ปลดล็อคอาวุธ Genesis #bChapter.2#k\r\n\r\n#e<ร่องรอยของ Lion King, Von Leon>#n\r\nกำจัด Hard Von Leon คนเดียวภายใต้เงื่อนไขดังนี้\r\n#b  - สวมใส่เฉพาะอาวุธ Genesis ที่ถูกผนึกและอาวุธรองเท่านั้น\r\n  - ใช้เฉพาะค่าสถานะบริสุทธิ์ของอุปกรณ์ที่สวมใส่อยู่\r\n  - Final Damage ลดลง 90%\r\n\r\n#kจะท้าทายหรือไม่?",
                ScriptMessageFlag.Self) == 1) {
            self.say("ไปลองกำจัด Hard Von Leon คนเดียวดูเถอะ!", ScriptMessageFlag.Self);
            getQuest().forceStart(getPlayer(), getNpc().getId(), "");
        }
    }

    public void q2000019e() {
        self.say("กำจัด Von Leon คนเดียวสำเร็จแล้ว แต่ดูเหมือนพลังของ #bอาวุธ Genesis#k จะยังจมอยู่ในความมืดอยู่เลย",
                ScriptMessageFlag.Self);
        getQuest().forceComplete(getPlayer(), getNpc().getId());
    }

    public void q2000020s() {
        if (self.askAccept(
                "#e#r[Jin:眞]#k#n ปลดล็อคอาวุธ Genesis #bChapter.3#k\r\n\r\n#e<ร่องรอยของ High Priest of Time, Arkarium>#n\r\nกำจัด Normal Arkarium คนเดียวภายใต้เงื่อนไขดังนี้\r\n#b  - สวมใส่เฉพาะอาวุธ Genesis ที่ถูกผนึกและอาวุธรองเท่านั้น\r\n  - ใช้เฉพาะค่าสถานะบริสุทธิ์ของอุปกรณ์ที่สวมใส่อยู่\r\n  - Final Damage ลดลง 75%\r\n\r\n#kจะท้าทายหรือไม่?",
                ScriptMessageFlag.Self) == 1) {
            self.say("ไปลองกำจัด Normal Arkarium คนเดียวดูเถอะ!", ScriptMessageFlag.Self);
            getQuest().forceStart(getPlayer(), getNpc().getId(), "");
        }
    }

    public void q2000020e() {
        self.say("กำจัด Arkarium คนเดียวสำเร็จแล้ว แต่ดูเหมือนพลังของ #bอาวุธ Genesis#k จะยังจมอยู่ในความมืดอยู่เลย",
                ScriptMessageFlag.Self);
        getQuest().forceComplete(getPlayer(), getNpc().getId());
    }

    public void q2000021s() {
        if (self.askAccept(
                "#e#r[Jin:眞]#k#n ปลดล็อคอาวุธ Genesis #bChapter.4#k\r\n\r\n#e<ร่องรอยของ Tyrant Magnus>#n\r\nกำจัด Hard Magnus คนเดียวภายใต้เงื่อนไขดังนี้\r\n#b  - สวมใส่เฉพาะอาวุธ Genesis ที่ถูกผนึกและอาวุธรองเท่านั้น\r\n  - ใช้เฉพาะค่าสถานะบริสุทธิ์ของอุปกรณ์ที่สวมใส่อยู่\r\n  - Final Damage ลดลง 50%\r\n\r\nต้องการ #b#i4036460##z4036460# 1 ชิ้น#k\r\n  #b- หลังจากรับเควสนี้ สามารถหาได้จากการกำจัด Black Mage#k\r\n\r\n#kจะท้าทายหรือไม่?",
                ScriptMessageFlag.Self) == 1) {
            self.say("ไปลองกำจัด Hard Magnus คนเดียวดูเถอะ!", ScriptMessageFlag.Self);
            getQuest().forceStart(getPlayer(), getNpc().getId(), "");
        }
    }

    public void q2000021e() {
        if (!getPlayer().haveItem(4036460, 1)) {
            self.say("จำเป็นต้องมี #b#i4036460# #z4036460# 1 ชิ้น#k สามารถหาได้จากการกำจัด Black Mage",
                    ScriptMessageFlag.Self);
            return;
        }
        if (target.exchange(4036460, -1) > 0) {
            self.say("กำจัด Magnus คนเดียวสำเร็จแล้ว แต่ดูเหมือนพลังของ #bอาวุธ Genesis#k จะยังจมอยู่ในความมืดอยู่เลย",
                    ScriptMessageFlag.Self);
            getQuest().forceComplete(getPlayer(), getNpc().getId());
        }
    }

    public void q2000022s() {
        String v0 = "#e#r[Jin:眞]#k#n ปลดล็อคอาวุธ Genesis #bChapter.5#k\r\n\r\n#e<ร่องรอยของ Wing Master Lotus>#n\r\nกำจัด Lotus คนเดียวภายใต้เงื่อนไขดังนี้\r\n#b  - Final Damage ลดลง 20%\r\n\r\nต้องการ #b#i4036461##z4036461# 1 ชิ้น#k\r\n  #b- หลังจากรับเควสนี้ สามารถหาได้จากการกำจัด Black Mage\r\n\r\nเมื่อเคลียร์จะได้รับสกิล [Tanadian Ruin]\r\nและปลดปล่อยพลังส่วนแรกของอาวุธ Genesis\r\n\r\n#kจะท้าทายหรือไม่?";
        if (GameConstants.isZero(getPlayer().getJob())) {
            v0 = "#e#r[Jin:眞]#k#n ปลดล็อคอาวุธ Genesis #bChapter.5#k\r\n\r\n#e<ร่องรอยของ Wing Master Lotus>#n\r\nกำจัด Reborn Lotus คนเดียวภายใต้เงื่อนไขดังนี้\r\n#b  - Final Damage ลดลง 20%\r\n\r\nต้องการ #b#i4036461##z4036461# 1 ชิ้น#k\r\n  #b- หลังจากรับเควสนี้ สามารถหาได้จากการกำจัด Black Mage\r\n\r\n#kจะท้าทายหรือไม่?";
        }
        if (self.askAccept(v0, ScriptMessageFlag.Self) == 1) {
            self.say("ไปลองกำจัด Hard Lotus คนเดียวดูเถอะ!", ScriptMessageFlag.Self);
            getQuest().forceStart(getPlayer(), getNpc().getId(), "");
        }
    }

    public void q2000022e() {
        if (!getPlayer().haveItem(4036461, 1)) {
            self.say("จำเป็นต้องมี #b#i4036461# #z4036461# 1 ชิ้น#k สามารถหาได้จากการกำจัด Black Mage",
                    ScriptMessageFlag.Self);
            return;
        }
        if (target.exchange(4036461, -1) > 0) {
            if (!GameConstants.isZero(getPlayer().getJob())) {
                int result = doGenesisWeaponFirstUpgrade();
                if (result == -1) {
                    return;
                }
                self.say(
                        "พลังส่วนแรกที่ซ่อนอยู่ใน #bอาวุธ Genesis#k ตื่นขึ้นแล้ว\r\n\r\n#r- ได้รับสกิล <Tanadian Ruin>\r\n- ไม่สามารถตีบวก Scroll/Star Force ได้\r\n- Additional Options/Soul จะถูกรีเซ็ตเมื่อปลดล็อคสมบูรณ์\r\n\r\n#k#i"
                                + result + "# #z" + result + "#",
                        ScriptMessageFlag.Self);
            }
            getQuest().forceComplete(getPlayer(), getNpc().getId());
        }
    }

    public void q2000023s() {
        if (self.askAccept(
                "#e#r[Jin:眞]#k#n ปลดล็อคอาวุธ Genesis #bChapter.6#k\r\n\r\n#e<ร่องรอยของ Sword of Destruction Damien>#n\r\nกำจัด Hard Damien คนเดียวภายใต้เงื่อนไขดังนี้\r\n#b  - Death Count ลดเหลือ 5\r\n\r\nต้องการ #b#i4036462##z4036462# 1 ชิ้น#k\r\n  #b- หลังจากรับเควสนี้ สามารถหาได้จากการกำจัด Black Mage#k\r\n\r\n#kจะท้าทายหรือไม่?",
                ScriptMessageFlag.Self) == 1) {
            self.say("ไปลองกำจัด Hard Damien คนเดียวดูเถอะ!", ScriptMessageFlag.Self);
            getQuest().forceStart(getPlayer(), getNpc().getId(), "");
        }
    }

    public void q2000023e() {
        if (!getPlayer().haveItem(4036462, 1)) {
            self.say("จำเป็นต้องมี #b#i4036462# #z4036462# 1 ชิ้น#k สามารถหาได้จากการกำจัด Black Mage",
                    ScriptMessageFlag.Self);
            return;
        }
        if (target.exchange(4036462, -1) > 0) {
            self.say("กำจัด Damien คนเดียวสำเร็จแล้ว แต่ดูเหมือนพลังของ #bอาวุธ Genesis#k จะยังจมอยู่ในความมืดอยู่เลย",
                    ScriptMessageFlag.Self);
            getQuest().forceComplete(getPlayer(), getNpc().getId());
        }
    }

    public void q2000024s() {
        if (self.askAccept(
                "#e#r[Jin:眞]#k#n ปลดล็อคอาวุธ Genesis #bChapter.7#k\r\n\r\n#e<ร่องรอยของ Spider King Will>#n\r\nกำจัด Hard Will ภายใต้เงื่อนไขดังนี้\r\n#b  - กำจัดคนเดียว\r\n\r\nต้องการ #b#i4036463##z4036463# 1 ชิ้น#k\r\n  #b- หลังจากรับเควสนี้ สามารถหาได้จากการกำจัด Black Mage#k\r\n\r\n#kจะท้าทายหรือไม่?",
                ScriptMessageFlag.Self) == 1) {
            self.say("ไปลองกำจัด Hard Will คนเดียวดูเถอะ!", ScriptMessageFlag.Self);
            getQuest().forceStart(getPlayer(), getNpc().getId(), "");
        }
    }

    public void q2000024e() {
        if (!getPlayer().haveItem(4036463, 1)) {
            self.say("จำเป็นต้องมี #b#i4036463# #z4036463# 1 ชิ้น#k สามารถหาได้จากการกำจัด Black Mage",
                    ScriptMessageFlag.Self);
            return;
        }
        if (target.exchange(4036463, -1) > 0) {
            self.say("กำจัด Will คนเดียวสำเร็จแล้ว แต่ดูเหมือนพลังของ #bอาวุธ Genesis#k จะยังจมอยู่ในความมืดอยู่เลย",
                    ScriptMessageFlag.Self);
            getQuest().forceComplete(getPlayer(), getNpc().getId());
        }
    }

    public void q2000025s() {
        if (self.askAccept(
                "#e#r[Jin:眞]#k#n ปลดล็อคอาวุธ Genesis #bChapter.8#k\r\n\r\n#e<ร่องรอยของ Master of Nightmares, Lucid>#n\r\nกำจัด Lucid คนเดียวภายใต้เงื่อนไขดังนี้\r\n#b  - ให้ #i2000047# #z2000047# 50 ชิ้น\r\n  - #eไม่สามารถใช้งานไอเทมอื่นๆ ได้เลย#n ยกเว้น #z2000047#\r\n\r\nต้องการ #b#i4036464##z4036464# 1 ชิ้น#k\r\n  #b- หลังจากรับเควสนี้ สามารถหาได้จากการกำจัด Black Mage#k\r\n\r\n#kจะท้าทายหรือไม่?",
                ScriptMessageFlag.Self) == 1) {
            self.say("ไปลองกำจัด Hard Lucid คนเดียวดูเถอะ!", ScriptMessageFlag.Self);
            getQuest().forceStart(getPlayer(), getNpc().getId(), "");
        }
    }

    public void q2000025e() {
        if (!getPlayer().haveItem(4036464, 1)) {
            self.say("จำเป็นต้องมี #b#i4036464# #z4036464# 1 ชิ้น#k สามารถหาได้จากการกำจัด Black Mage",
                    ScriptMessageFlag.Self);
            return;
        }
        if (target.exchange(4036464, -1) > 0) {
            self.say("กำจัด Lucid คนเดียวสำเร็จแล้ว อีกไม่นานคงจะปลดปล่อยจากพลังด้านมืดได้", ScriptMessageFlag.Self);
            getQuest().forceComplete(getPlayer(), getNpc().getId());
        }
    }

    public void q2000026s() {
        if (self.askAccept(
                "#e#r[Jin:眞]#k#n ปลดล็อคอาวุธ Genesis #bChapter.9#k\r\n\r\n#e<ร่องรอยของ Red Witch, Verus Hilla>#n\r\nกำจัด Verus Hilla คนเดียวภายใต้เงื่อนไขดังนี้\r\n#b  - HP ของ Verus Hilla ลดลง 25%#n\r\n\r\nต้องการ #b#i4036465##z4036465# 1 ชิ้น#k\r\n  #b- สามารถหาได้จากการกำจัด Black Mage\r\n\r\nเมื่อสำเร็จจะได้รับสกิล Aeonian Rise#k\r\n\r\n#kจะท้าทายหรือไม่?",
                ScriptMessageFlag.Self) == 1) {
            self.say("ไปลองกำจัด Hard Verus Hilla คนเดียวดูเถอะ!", ScriptMessageFlag.Self);
            getQuest().forceStart(getPlayer(), getNpc().getId(), "");
        }
    }

    public void q2000026e() {
        if (!getPlayer().haveItem(4036465, 1)) {
            self.say("จำเป็นต้องมี #b#i4036465# #z4036465# 1 ชิ้น#k สามารถหาได้จากการกำจัด Black Mage",
                    ScriptMessageFlag.Self);
            return;
        }
        if (target.exchange(4036465, -1) > 0) {
            self.say(
                    "กำจัด Verus Hilla คนเดียวสำเร็จแล้ว ในที่สุดก็น่าจะปลดปล่อยจากพลังด้านมืดได้อย่างสมบูรณ์\r\n\r\n#e<สามารถใช้สกิลได้>#n\r\n#b - สกิล Aeonian Rise สามารถใช้ได้แล้ว (หากสวมใส่อาวุธ Genesis อยู่ กรุณาถอดแล้วใส่ใหม่)",
                    ScriptMessageFlag.Self);
            getQuest().forceComplete(getPlayer(), getNpc().getId());
        }
    }

    public void q2000027s() {
        final AtomicInteger weapon = new AtomicInteger(0);
        getPlayer().getInventory(MapleInventoryType.EQUIPPED).list().stream().forEach(item -> {
            for (int i : bmWeapons) {
                if (item.getItemId() == i + 1) {
                    weapon.set(i);
                    break;
                }
            }
        });
        if (weapon.get() == 0) {
            getPlayer().getInventory(MapleInventoryType.EQUIP).list().stream().forEach(item -> {
                for (int i : bmWeapons) {
                    if (item.getItemId() == i + 1) {
                        weapon.set(i);
                        break;
                    }
                }
            });
        }
        if (weapon.get() == 0) {
            if (GameConstants.isZero(getPlayer().getJob())) {
                if (target.exchange(4310260, 1) > 0) {
                    self.say(
                            "ได้รับ #b#i4310260# #z4310260##k แล้ว สามารถเติบโตเป็น Type 10 ผ่านการ #eGrowth#n ของอาวุธได้",
                            ScriptMessageFlag.Self);
                    getQuest().forceComplete(getPlayer(), getNpc().getId());
                } else {
                    self.say("กรุณาทำช่องว่างใน #bBag อื่นๆ#k 1 ช่อง แล้วลองใหม่อีกครั้ง", ScriptMessageFlag.Self);
                }
                return;
            }
            self.say("หากไม่มีอาวุธ Genesis จะไม่สามารถดำเนินเควสนี้ได้", ScriptMessageFlag.Self);
            return;
        }
        if (self.askMenu(
                "#e#r[Jin:眞]#k#n ปลดล็อคอาวุธ Genesis #bChapter.X#k\r\n\r\n#e<อาวุธ Genesis>#n\r\nอาวุธ Genesis เต็มเปี่ยมไปด้วยพลังอันแข็งแกร่ง\r\nดูเหมือนจะสามารถปลุกพลังที่ซ่อนอยู่ของอาวุธ Genesis ได้อย่างสมบูรณ์แล้ว จะเริ่มการปลดปล่อยเลยมั้ย?\r\n\r\n#r- ตีบวกด้วย Scroll 15% สำเร็จทั้งหมด\r\n- Star Force 22 ดาว\r\n- มี Potential ระดับ Unique\r\n- มี Additional Potential ระดับ Epic\r\n- ไม่สามารถตีบวก Scroll/Star Force เพิ่มเติมได้\r\n- Additional Options/Soul จะถูกรีเซ็ตเมื่อปลดล็อคสมบูรณ์\r\n#b#L0##i"
                        + (weapon.get() + 1) + "# #z" + (weapon.get() + 1) + "##l",
                ScriptMessageFlag.Self) == 0) {
            doGenesisWeaponUpgrade();
            self.say(
                    "อาวุธ Genesis ที่พลังตื่นขึ้นอย่างสมบูรณ์นั้นแข็งแกร่งยิ่งขึ้น\r\nจงสวมใส่อาวุธ แล้วลองทดสอบพลังดูสิ",
                    ScriptMessageFlag.Self);

            getQuest().forceComplete(getPlayer(), getNpc().getId());
        }
    }

    int[] bmWeapons = new int[] {
            1212128, 1213021, 1222121, 1232121, 1242138, 1242140, 1262050, 1272039, 1282039, 1292021, 1302354, 1312212,
            1322263, 1332288, 1362148, 1372236, 1382273, 1402267, 1412188, 1422196, 1432226, 1442284, 1452265, 1462251,
            1472274, 1482231, 1492244, 1522151, 1532156, 1582043, 1592021, 1562010, 1214021
    };

    // Unseal Genesis Weapon 1st Stage
    public int doGenesisWeaponFirstUpgrade() {
        MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();

        if (getPlayer().getInventory(MapleInventoryType.EQUIP).getNextFreeSlot() < 0) {
            self.say("กรุณาทำช่องว่างในช่องเก็บอุปกรณ์ 1 ช่องขึ้นไป", ScriptMessageFlag.Self);
            return -1;
        }

        Equip equip = null;
        for (Item item : new ArrayList<>(getPlayer().getInventory(MapleInventoryType.EQUIPPED).list())) {
            for (int i : bmWeapons) {
                if (item.getItemId() == i) {
                    equip = (Equip) item;
                    break;
                }
            }
        }
        if (equip == null) {
            for (Item item : new ArrayList<>(getPlayer().getInventory(MapleInventoryType.EQUIP).list())) {
                for (int i : bmWeapons) {
                    if (item.getItemId() == i) {
                        equip = (Equip) item;
                        break;
                    }
                }
            }
        }
        if (equip == null) {
            self.say("เกิดข้อผิดพลาดไม่ทราบสาเหตุ", ScriptMessageFlag.Self);
            return -1;
        }
        int weaponID = equip.getItemId() + 1;
        Equip genesis = (Equip) ii.getEquipById(weaponID);

        if (genesis == null) {
            self.say("เกิดข้อผิดพลาดไม่ทราบสาเหตุ", ScriptMessageFlag.Self);
            return -1;
        }

        // Grant Additional Options
        if (BonusStat.resetBonusStat(genesis, BonusStatPlaceType.LevelledRebirthFlame)) {
        }

        MapleInventoryType type = MapleInventoryType.EQUIP;
        if (equip.getPosition() < 0) {
            type = MapleInventoryType.EQUIPPED;
        }
        getPlayer().send(CWvsContext.InventoryPacket.deleteItem(equip));

        MapleInventoryManipulator.removeFromSlot(getClient(), type, equip.getPosition(), equip.getQuantity(), false,
                false);
        MapleInventoryManipulator.addbyItem(getClient(), genesis);
        return genesis.getItemId();
    }

    // Unseal Genesis Weapon Final Stage
    public void doGenesisWeaponUpgrade() {
        MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        Equip equip = null;

        if (getPlayer().getInventory(MapleInventoryType.EQUIP).getNextFreeSlot() < 0) {
            self.say("กรุณาทำช่องว่างในช่องเก็บอุปกรณ์ 1 ช่องขึ้นไป", ScriptMessageFlag.Self);
            return;
        }

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
            self.say("เกิดข้อผิดพลาดไม่ทราบสาเหตุ", ScriptMessageFlag.Self);
            return;
        }
        /*
         * int weaponID = equip.getItemId() + 1;
         * Equip genesis = (Equip) ii.getEquipById(weaponID);
         * 
         * if (genesis == null) {
         * sendNext("알 수 없는 오류가 발생แล้ว.");
         * dispose();
         * return;
         * }
         */
        int weaponID = equip.getItemId();
        Equip genesis = (Equip) ii.getEquipById(weaponID);

        if (genesis == null) {
            self.say("เกิดข้อผิดพลาดไม่ทราบสาเหตุ", ScriptMessageFlag.Self);
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

        // Exception processing
        if (equip.getItemId() == 1242140) { // Xenon DEX, LUK
            source.clear();
            EquipEnchantOption option = new EquipEnchantOption();
            option.setOption(ItemUpgradeFlag.INC_PAD.getValue(),
                    EquipEnchantMan.getIncATTWeapon(ii.getReqLevel(weaponID), 3));
            option.setOption(ItemUpgradeFlag.INC_LUK.getValue(),
                    EquipEnchantMan.getIncPrimaryStatWeapon(ii.getReqLevel(weaponID), 3));

            source.add(new EquipEnchantScroll(weaponID, 3, option, ScrollType.UPGRADE, 0, false));
        }
        if (equip.getItemId() == 1232121) { // Demon Avenger
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
        if (equip.getItemId() == 1362148) { // Night Lord/Thief
            source.clear();
            EquipEnchantOption option = new EquipEnchantOption();
            option.setOption(ItemUpgradeFlag.INC_PAD.getValue(),
                    EquipEnchantMan.getIncATTWeapon(ii.getReqLevel(weaponID), 3));
            option.setOption(ItemUpgradeFlag.INC_LUK.getValue(),
                    EquipEnchantMan.getIncPrimaryStatWeapon(ii.getReqLevel(weaponID), 3));

            source.add(new EquipEnchantScroll(weaponID, 3, option, ScrollType.UPGRADE, 0, false));
        }
        if (source.size() <= 0) {
            self.say("เกิดข้อผิดพลาดไม่ทราบสาเหตุ", ScriptMessageFlag.Self);
            return;
        }
        EquipEnchantScroll scroll = source.get(0); // Scroll ที่ตรงกับอาชีพ
        if (scroll == null) {
            self.say("เกิดข้อผิดพลาดไม่ทราบสาเหตุ", ScriptMessageFlag.Self);
            return;
        }
        // Success 8 times

        Equip zeroEquip = null;
        if (GameConstants.isZero(getPlayer().getJob())) {
            zeroEquip = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                    .getItem(equip.getPosition() == -11 ? (short) -10 : -11);
        }
        for (int i = 0; i < 8; ++i) {
            scroll.upgrade(genesis, 0, true, zeroEquip);
        }

        // Grant 22 Stars
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

        // Grant Additional Options
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

        Center.Broadcast.broadcastMessage(CWvsContext.serverNotice(6, getPlayer().getName()
                + " ได้ปลดปล่อยพลังที่ถูกผนึก และกลายเป็นเจ้าของอาวุธ Genesis ที่มีพลังของ Black Mage สถิตอยู่"));
    }
}
