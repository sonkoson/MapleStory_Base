package script.Util;

import constants.GameConstants;
import database.DBConfig;
import network.game.processors.inventory.InventoryHandler;
import network.models.CWvsContext;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.item.*;
import objects.item.Equip;
import objects.item.Item;
import objects.item.MapleInventoryType;
import objects.item.MapleItemInformationProvider;
import objects.users.MapleStat;
import objects.users.enchant.*;
import objects.users.looks.zero.ZeroInfoFlag;
import objects.utils.Pair;
import objects.utils.Randomizer;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;
import logging.LoggingManager;
import logging.entry.EnchantLog;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class OneClickSet extends ScriptEngineNPC {

    List<Integer> specialHair = new ArrayList<>(Arrays.asList(
            32700, 32710, 32720, 32730, 32740, 32750, 32760, 32770, 32780, 32790, 32800, 32810, 32820, 32830, 32860,
            32870, 32880, 32890, 32890, 32900, 32910,
            32920, 32930, 32940, 32950, 32960, 32970, 32980, 32990, 39100, 39110, 39120, 39130, 39140, 39150, 39160,
            39170, 45320, 45330, 45340, 45350, 45360,
            45370, 45380, 45390, 45400, 45410, 45420, 45430, 45440, 45450, 45460, 45470, 45480, 45490, 45500, 45510,
            45520, 45530, 45540, 45550, 45560, 45570,
            45580, 45590, 45600, 45610, 45620, 45630, 45640, 45650, 45660, 45670, 45680, 45690, 45700, 45710, 45720,
            45730, 45740, 45750, 45760, 45770, 45780,
            45790, 45800, 45810, 45820, 45830, 45840, 45850, 45860, 45870, 45880, 45890, 45900, 45910, 45920, 45930,
            45940, 45950, 45960, 45970, 45980, 45990,
            39180, 39270, 39280, 39290, 39470, 39480, 39490, 39790, 39800, 39810, 39820, 39830, 39840, 39850, 39860,
            39870, 39880, 39890, 39910, 47810, 47820,
            39000, 39010, 39020, 39030, 39450, 39460, 39300, 39310, 39320, 39330, 39340, 39350, 39360, 39370, 39380,
            39390, 39400, 39410, 39420, 39430, 39440,
            39500, 39510, 39520, 39530, 39540, 39550, 39560, 39570, 39580, 39590, 39600, 39610, 39620, 39630, 39640,
            39650, 39660, 39670, 39680, 39690, 39700,
            39710, 39720, 39730, 39740, 39750, 39760, 39770, 39780, 39900, 39000, 39010, 39020, 39030, 39040, 39050,
            39060, 39070, 39080, 39100, 39110, 39120, 39130, 39140, 39150, 39160, 39170, 39180, 39190, 39200, 39210,
            39500, 39510, 39520, 39530, 39540, 39550, 39560, 39570, 39580, 39590));

    DecimalFormat decFormat = new DecimalFormat("###,###");

    public void levelUP() {
        initNPC(MapleLifeFactory.getNPC(1530055));
        int vv = self.askMenu(
                "#fs11#กรุณาเลือกเมนูที่ต้องการ\r\n\r\n#b#L0#One Click Cube#l\r\n#L1#One Click Rebirth Flame#l");
        if (vv == 0) {
            int vvv = self.askMenu(
                    "#fs11#กรุณาเลือกประเภท Cube\r\n#b#L100##i2711006:# #z2711006:#\r\n#L0##i5062009:# #z5062009:#\r\n#L1##i5062010:# #z5062010:##l\r\n#L2##i5062500:# #z5062500:##l");
            switch (vvv) {
                case 0: // Red Cube
                    oneClickCubeZenia(5062009, GradeRandomOption.Red);
                    break;
                case 1: // Black Cube
                    oneClickCubeZenia(5062010, GradeRandomOption.Black);
                    break;
                case 2: // Additional Cube
                    oneClickCubeZenia(5062500, GradeRandomOption.Additional);
                    break;
                case 100:
                    if (getPlayer().getItemQuantity(2711004, false) > 0) {
                        oneClickCubeZenia(2711004, GradeRandomOption.Meister);
                    } else if (getPlayer().getItemQuantity(2711006, false) > 0) {
                        oneClickCubeZenia(2711006, GradeRandomOption.Meister);
                    } else if (getPlayer().getItemQuantity(2711013, false) > 0) {
                        oneClickCubeZenia(2711013, GradeRandomOption.Meister);
                    } else if (getPlayer().getItemQuantity(2711017, false) > 0) {
                        oneClickCubeZenia(2711017, GradeRandomOption.Meister);
                    } else {
                        self.sayOk("หากไม่มี Meister's Cube จะไม่สามารถใช้งานได้");
                    }
                    break;
            }
        } else if (vv == 1) {
            String rebirthFlame = "\r\n#b";
            Collection<Item> itemCollection = getPlayer().getInventory(MapleInventoryType.USE).list();

            List<Integer> flames = new ArrayList<>();
            for (Item item : itemCollection) {
                if (GameConstants.isRebirhFireScroll(item.getItemId())) {
                    if (!GameConstants.IsBlackRebirthFlame(item.getItemId())) {
                        if (!flames.contains(item.getItemId())) {
                            flames.add(item.getItemId());
                            rebirthFlame += String.format("#L%d##i%d:# #z%d:#\r\n", item.getItemId(), item.getItemId(),
                                    item.getItemId());
                        }
                    }
                }
            }

            rebirthFlame += "\r\n#r#L0#ไม่เลือก (ย้อนกลับ)#l";

            int vvv = self.askMenu(
                    "#fs11#กรุณาเลือก Rebirth Flame (แสดงเฉพาะที่มีในช่องเก็บของ, รองรับเฉพาะ Red/Rainbow Rebirth Flame)"
                            + rebirthFlame);
            if (vvv > 0) {
                BonusStatPlaceType placeType = BonusStatPlaceType.PowerfulRebirthFlame;
                if (GameConstants.IsPowerfulRebirthFlame(vvv)) {
                    oneClickFlame(vvv, placeType);
                } else if (GameConstants.IsEternalRebirthFlame(vvv)) {
                    placeType = BonusStatPlaceType.EternalRebirthFlame;
                    oneClickFlame(vvv, placeType);
                }
            }
        }
    }

    public void oneClickFlame(int flameID, BonusStatPlaceType placeType) {
        List<Short> items = new ArrayList<>();
        for (Item item : getPlayer().getInventory(MapleInventoryType.EQUIPPED).list()) {
            if (item instanceof Equip) {
                if (((Equip) item).getExGradeOption() > 0) {
                    // Unlocked only
                    if ((((Equip) item).getItemState() & ItemStateFlag.LOCK.getValue()) == 0) {
                        items.add((short) ((item.getPosition() * -1) + 10000));
                    }
                }
            }
        }
        for (Item item : getPlayer().getInventory(MapleInventoryType.EQUIP).list()) {
            if (item instanceof Equip) {
                if (((Equip) item).getExGradeOption() > 0) {
                    // Unlocked only
                    if ((((Equip) item).getItemState() & ItemStateFlag.LOCK.getValue()) == 0) {
                        items.add(item.getPosition());
                    }
                }
            }
        }
        if (items.isEmpty()) {
            self.say(
                    "ไม่พบอุปกรณ์ที่เคยใช้ Rebirth Flame ในช่องสวมใส่และช่องเก็บของ\r\n#e#r[เงื่อนไข: อุปกรณ์ที่เคยใช้ Rebirth Flame มาก่อน]");
            return;
        }
        String menu = "#fs11#กรุณาเลือกอุปกรณ์ที่จะรีเซ็ตออปชั่น\r\n\r\n#r#e※ ข้อควรระวัง ※\r\nเรียงลำดับจาก ช่องสวมใส่ -> ช่องเก็บของ\r\nอุปกรณ์ที่ล็อคอยู่จะไม่แสดง#n#b\r\n";
        for (short i : items) {
            Item toItem;
            var a = i;
            if (i > 1000) {
                toItem = getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short) ((i - 10000) * -1));
                a = 0;
            } else {
                toItem = getPlayer().getInventory(MapleInventoryType.EQUIP).getItem(i);
            }
            int itemid = toItem.getItemId();
            if (a == 0) {
                menu += "\r\n#L" + i + "# #i" + itemid + "# #z" + itemid + "# #r#e[สวมใส่อยู่]#n#b";
            } else {
                menu += "\r\n#L" + i + "# #i" + itemid + "# #z" + itemid + "# #r[" + a + "ช่อง]#b";
            }
        }
        int selection = self.askMenu(menu);
        if (selection > -1) {
            Equip selectedItem;
            if (selection > 1000) {
                selectedItem = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                        .getItem((short) ((selection - 10000) * -1));
            } else {
                selectedItem = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIP).getItem((short) selection);
            }
            if (self.askYesNo("#fs11#คุณต้องการเลือกไอเท็ม #i" + selectedItem.getItemId() + ":# #z"
                    + selectedItem.getItemId() + ":# ใช่หรือไม่?") != 1) {
                return;
            }
            Equip nZeroEquip = null;
            if (GameConstants.isZeroWeapon(selectedItem.getItemId())) {
                nZeroEquip = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                        .getItem(selectedItem.getPosition() == -11 ? (short) -10 : -11);
            }
            MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            int itemCount = 0;
            int useCount = 0;
            while (getSc() != null) {
                if (target.exchange(flameID, -1) > 0) {
                    itemCount = getPlayer().getItemQuantity(flameID, false);
                    useCount++;
                    if (Randomizer.isSuccess(ii.getSuccess(flameID, getPlayer(), selectedItem))) {
                        if (BonusStat.resetBonusStat(selectedItem, placeType)) {
                            // สำเร็จ
                            if (selection > 1000) {
                                getPlayer().forceReAddItem(selectedItem, MapleInventoryType.EQUIPPED);
                            } else {
                                getPlayer().forceReAddItem(selectedItem, MapleInventoryType.EQUIP);
                            }
                            if (nZeroEquip != null) {
                                nZeroEquip.setExGradeOption(selectedItem.getExGradeOption());
                                getPlayer().forceReAddItem(nZeroEquip, MapleInventoryType.EQUIPPED);
                            }

                            Map<ExItemType, Integer> bonusStat = BonusStat.getExItemOptions(selectedItem);
                            String t = "";
                            TreeMap<ExItemType, Integer> bs = new TreeMap<>(); // 시발것!!!!!!!!!!!!!!!!!!!!!
                            for (Map.Entry<ExItemType, Integer> entry : bonusStat.entrySet()) {
                                int bonusStatValue = BonusStat.getBonusStat(selectedItem, entry.getKey(),
                                        entry.getValue());
                                if (bonusStatValue == 0)
                                    continue;

                                int type = entry.getKey().getType();
                                if (type >= 4 && type <= 9) {
                                    if (type == 4) {
                                        bs.putIfAbsent(ExItemType.Str, 0);
                                        bs.putIfAbsent(ExItemType.Dex, 0);
                                        bs.put(ExItemType.Str, bs.get(ExItemType.Str) + bonusStatValue);
                                        bs.put(ExItemType.Dex, bs.get(ExItemType.Dex) + bonusStatValue);
                                    }
                                    if (type == 5) {
                                        bs.putIfAbsent(ExItemType.Str, 0);
                                        bs.putIfAbsent(ExItemType.Int, 0);
                                        bs.put(ExItemType.Str, bs.get(ExItemType.Str) + bonusStatValue);
                                        bs.put(ExItemType.Int, bs.get(ExItemType.Int) + bonusStatValue);
                                    }
                                    if (type == 6) {
                                        bs.putIfAbsent(ExItemType.Str, 0);
                                        bs.putIfAbsent(ExItemType.Luk, 0);
                                        bs.put(ExItemType.Str, bs.get(ExItemType.Str) + bonusStatValue);
                                        bs.put(ExItemType.Luk, bs.get(ExItemType.Luk) + bonusStatValue);
                                    }
                                    if (type == 7) {
                                        bs.putIfAbsent(ExItemType.Dex, 0);
                                        bs.putIfAbsent(ExItemType.Int, 0);
                                        bs.put(ExItemType.Dex, bs.get(ExItemType.Dex) + bonusStatValue);
                                        bs.put(ExItemType.Int, bs.get(ExItemType.Int) + bonusStatValue);
                                    }
                                    if (type == 8) {
                                        bs.putIfAbsent(ExItemType.Dex, 0);
                                        bs.putIfAbsent(ExItemType.Luk, 0);
                                        bs.put(ExItemType.Dex, bs.get(ExItemType.Dex) + bonusStatValue);
                                        bs.put(ExItemType.Luk, bs.get(ExItemType.Luk) + bonusStatValue);
                                    }
                                    if (type == 9) {
                                        bs.putIfAbsent(ExItemType.Int, 0);
                                        bs.putIfAbsent(ExItemType.Luk, 0);
                                        bs.put(ExItemType.Int, bs.get(ExItemType.Int) + bonusStatValue);
                                        bs.put(ExItemType.Luk, bs.get(ExItemType.Luk) + bonusStatValue);
                                    }
                                } else {
                                    bs.putIfAbsent(entry.getKey(), 0);
                                    bs.put(entry.getKey(), bs.get(entry.getKey()) + bonusStatValue);
                                }
                            }
                            int line = 0;
                            for (ExItemType entry : bs.keySet()) {
                                if (entry == ExItemType.ReqLevel) {
                                    t += "ลดเลเวลสวมใส่ : " + "-" + bs.get(entry) + "\r\n";
                                } else if (entry == ExItemType.Pad) {
                                    t += "พลังโจมตี : " + "+" + bs.get(entry) + "\r\n";
                                } else if (entry == ExItemType.Mad) {
                                    t += "พลังเวท : " + "+" + bs.get(entry) + "\r\n";
                                } else if (entry == ExItemType.Pdd || entry == ExItemType.Mdd) {
                                    t += "พลังป้องกัน : " + "+" + bs.get(entry) + "\r\n";
                                } else if (entry == ExItemType.Acc) { // Obsolete option?
                                    t += "ความแม่นยำ : " + "+" + bs.get(entry) + "\r\n";
                                } else if (entry == ExItemType.Eva) { // Obsolete option?
                                    t += "อัตราหลบหลีก : " + "+" + bs.get(entry) + "\r\n";
                                } else if (entry == ExItemType.Speed) {
                                    t += "ความเร็วเคลื่อนที่ : " + "+" + bs.get(entry) + "\r\n";
                                } else if (entry == ExItemType.Jump) {
                                    t += "พลังกระโดด : " + "+" + bs.get(entry) + "\r\n";
                                } else if (entry == ExItemType.BdR) {
                                    t += "Damage เมื่อโจมตีบอส : " + "+" + bs.get(entry) + "%\r\n";
                                } else if (entry == ExItemType.IMdR) {
                                    t += "Ignore DEF : " + "+" + bs.get(entry) + "%\r\n";
                                } else if (entry == ExItemType.DamR) {
                                    t += "Damage : " + "+" + bs.get(entry) + "%\r\n";
                                } else if (entry == ExItemType.StatR) {
                                    t += "All Stat : " + "+" + bs.get(entry) + "%\r\n";
                                } else if (entry == ExItemType.Str) {
                                    t += "STR : " + "+" + bs.get(entry) + "\r\n";
                                } else if (entry == ExItemType.Dex) {
                                    t += "DEX : " + "+" + bs.get(entry) + "\r\n";
                                } else if (entry == ExItemType.Int) {
                                    t += "INT : " + "+" + bs.get(entry) + "\r\n";
                                } else if (entry == ExItemType.Luk) {
                                    t += "LUK : " + "+" + bs.get(entry) + "\r\n";
                                } else {
                                    t += entry + " : +" + bs.get(entry) + "\r\n";
                                }
                                ++line;
                            }
                            if (line == 5) {
                                if (1 != self.askMenu(String.format(
                                        "#eจำนวน Rebirth Flame ที่เหลือ : #r%d ชิ้น#k\r\nRebirth Flame ที่ใช้ไปจนถึงตอนนี้ : #r%d ชิ้น#k#n\r\n\r\n#b#e#L0#\r\n%s#l\r\n#L1##rสุ่มอีกครั้ง#l",
                                        itemCount, useCount, t), ScriptMessageFlag.NoEsc)) {
                                    break;
                                }
                            } else if (line == 4) {
                                if (1 != self.askMenu(String.format(
                                        "#eจำนวน Rebirth Flame ที่เหลือ : #r%d ชิ้น#k\r\nRebirth Flame ที่ใช้ไปจนถึงตอนนี้ : #r%d ชิ้น#k#n\r\n\r\n#b#e#L0#\r\n%s#l\r\n\r\n#L1##rสุ่มอีกครั้ง#l",
                                        itemCount, useCount, t), ScriptMessageFlag.NoEsc)) {
                                    break;
                                }
                            } else if (line == 3) {
                                if (1 != self.askMenu(String.format(
                                        "#eจำนวน Rebirth Flame ที่เหลือ : #r%d ชิ้น#k\r\nRebirth Flame ที่ใช้ไปจนถึงตอนนี้ : #r%d ชิ้น#k#n\r\n\r\n#b#e#L0#\r\n%s#l\r\n\r\n\r\n#L1##rสุ่มอีกครั้ง#l",
                                        itemCount, useCount, t), ScriptMessageFlag.NoEsc)) {
                                    break;
                                }
                            }
                            if (useCount > 500) {
                                StringBuilder sb = new StringBuilder("One Click Rebirth Flame used: " + useCount);
                                LoggingManager.putLog(new EnchantLog(getPlayer(), flameID, selectedItem.getItemId(),
                                        selectedItem.getSerialNumberEquip(), 99, 0, sb));
                            }
                        } else {
                            self.sayOk("การรีเซ็ตออปชั่นล้มเหลว (Error)");
                            break;
                        }
                    }
                } else {
                    self.sayOk("ไม่มี Rebirth Flame เหลืออยู่ การรีเซ็ตจึงล้มเหลว");
                    break;
                }
            }
        }
    }

    public void oneClickSet() {
        int vv = -1;
        if (!DBConfig.isGanglim) {
            vv = self.askMenu(
                    "#b#h0##k  สวัสดีจ้ะ\r\n\r\nฉันคือ #bCredit Union#k ที่จะมาช่วยปรับแต่งตัวละครของทุกคน ลองเลือกเมนูที่ต้องการสิ#b\r\n#L0#ค้นหาทรงผม#l\r\n#L1#ค้นหาหน้าตา#l");
        } else {
            vv = self.askMenu(
                    "#b#h0##k  สวัสดีจ้ะ\r\n\r\nฉันคือ #bLily#k ที่จะมาช่วยปรับแต่งตัวละครของทุกคน ลองเลือกเมนูที่ต้องการสิ#b\r\n#L0#ค้นหาทรงผม#l\r\n#L1#ค้นหาหน้าตา#l");
        }
        switch (vv) {
            case 0:
            case 1: {
                String v = "";
                if (vv == 0) {
                    v = self.askText("กรุณาใส่ชื่อทรงผมที่ต้องการค้นหา (ใส่บางส่วนก็ได้)",
                            ScriptMessageFlag.NpcReplacedByNpc);
                } else {
                    v = self.askText("กรุณาใส่ชื่อหน้าตาที่ต้องการค้นหา (ใส่บางส่วนก็ได้)",
                            ScriptMessageFlag.NpcReplacedByNpc);
                }
                if (v.equals("")) {
                    return;
                }
                List<Integer> items = new ArrayList<>();
                MapleItemInformationProvider mii = MapleItemInformationProvider.getInstance();
                switch (vv) {
                    case 0: { // Hair Search
                        String itemName = null;
                        for (Pair<Integer, String> item : mii.getAllEquips()) {
                            int itemid = item.getLeft();
                            if (itemid / 10000 == 3 || itemid / 10000 == 4 || itemid / 10000 == 6) {
                                if (specialHair.contains(itemid) || itemid % 10 != 0) {
                                    continue;
                                }
                                itemName = item.getRight();
                                if (itemName.replace(" ", "").contains(v) || itemName.contains(v)
                                        || itemName.contains(v.replace(" ", "")) ||
                                        itemName.replace(" ", "").contains(v.replace(" ", ""))) {
                                    items.add(itemid);
                                }
                            }
                        }
                        break;
                    }

                    case 1: { // Face Search
                        String itemName = null;
                        for (Pair<Integer, String> item : mii.getAllEquips()) {
                            int itemid = item.getLeft();
                            if (itemid / 10000 == 2 || itemid / 10000 == 5) {
                                itemName = item.getRight();
                                if (String.valueOf(itemid).charAt(2) == '0') {
                                    if (itemName.replace(" ", "").contains(v) || itemName.contains(v)
                                            || itemName.contains(v.replace(" ", "")) ||
                                            itemName.replace(" ", "").contains(v.replace(" ", ""))) {
                                        items.add(itemid);
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
                if (items.size() > 60) {
                    self.sayOk(
                            "ข้อมูลการค้นหาเยอะเกินไปไม่สามารถแสดงได้ครบ กรุณาระบุคำค้นหาให้ชัดเจนกว่านี้\r\n[ตัวอย่าง : (Rariel Hair) Rari x Rariel o]");
                } else if (items.size() > 0) {
                    String list = "ด้านล่างคือผลการค้นหา กรุณาเลือกรายการที่ต้องการ#b\r\n";
                    /*
                     * for (int i = 0; i < items.size(); i++) {
                     * list += "#L" + i + "#" + "#z" + items.get(i) + "#\r\n";
                     * }
                     */
                    for (int i = 0; i < items.size(); i++) {
                        String type = "Hair";
                        String type2 = "hair";
                        if (vv == 1) {
                            type = "Face";
                            type2 = "face";
                        }
                        list += "#L" + i + "#" + "#fCharacter/" + type + "/000" + items.get(i) + ".img/default/" + type2
                                + "# #e#z" + items.get(i) + "##n#l\r\n";
                    }
                    int vvv = self.askMenu(list);
                    if (vvv > -1) {
                        int az = 0;
                        if (GameConstants.isAngelicBuster(getPlayer().getJob())) {
                            if (1 == self.askYesNo("ต้องการใช้งานกับโหมด Dress-up หรือไม่?")) {
                                az = 1;
                            }
                        }
                        if (GameConstants.isZero(getPlayer().getJob())) {
                            if (1 == self
                                    .askYesNo("ต้องการใช้งานกับ Beta หรือไม่? (#r#eไม่ใช่#n#k เพื่อใช้งานกับ Alpha)")) {
                                az = 1;
                            }
                        }
                        String v0 = "ต้องการเปลี่ยนเป็น #r#z" + items.get(vvv) + "##k จริงหรือไม่?\r\n#e";
                        if (vv == 0) {
                            v0 += "\r\nด้านหน้า -\r\n#fCharacter/Hair/000" + items.get(vvv)
                                    + ".img/default/hair#\r\nด้านหลัง -\r\n#fCharacter/Hair/000" + items.get(vvv)
                                    + ".img/backDefault/backHair#";
                        } else {
                            v0 += "\r\n#fCharacter/Face/000" + items.get(vvv) + ".img/default/face#";
                        }
                        if (1 == self.askYesNo(v0)) {

                            if (items.get(vvv) < 30000 || items.get(vvv) >= 50000 && items.get(vvv) <= 59999) {
                                if (GameConstants.isAngelicBuster(getPlayer().getJob()) && az > 0) {
                                    getPlayer().setSecondFace(items.get(vvv));
                                    getPlayer().fakeRelog();
                                } else if (GameConstants.isZero(getPlayer().getJob()) && az > 0) {
                                    getPlayer().getZeroInfo().setSubFace(items.get(vvv));
                                    getPlayer().getZeroInfo().sendUpdateZeroInfo(getPlayer(), ZeroInfoFlag.SubFace);
                                } else {
                                    getPlayer().setFace(items.get(vvv));
                                    getPlayer().updateSingleStat(MapleStat.FACE, items.get(vvv));
                                }
                            } else {
                                // Reset Mix Dye
                                getPlayer().setBaseColor(-1);
                                getPlayer().setAddColor(0);
                                getPlayer().setBaseProb(0);
                                if (GameConstants.isAngelicBuster(getPlayer().getJob()) && az > 0) {
                                    getPlayer().setSecondHair(items.get(vvv));
                                    getPlayer().fakeRelog();
                                } else if (GameConstants.isZero(getPlayer().getJob()) && az > 0) {
                                    getPlayer().getZeroInfo().setSubHair(items.get(vvv));
                                    getPlayer().getZeroInfo().sendUpdateZeroInfo(getPlayer(), ZeroInfoFlag.SubHair);
                                } else {
                                    getPlayer().setHair(items.get(vvv));
                                    getPlayer().updateSingleStat(MapleStat.HAIR, items.get(vvv));
                                }
                            }
                            getPlayer().equipChanged();
                        }
                    } else {
                        self.sayOk("검색ผลลัพธ์가 없.");
                    }
                }
                break;
            }
        }
    }

    private int getCubePiece(int cubeid) {
        switch (cubeid) {
            case 5062009:
                return 2431893;
            case 5062010:
                return 2431894;
            case 5062500:
                return 2430915;
            case 2711004:
            case 2711006:
                return -1;
        }
        return 2431893;
    }

    private void oneClickCubeZenia(int cubeId, GradeRandomOption option) {
        boolean additional = option == GradeRandomOption.Additional || option == GradeRandomOption.OccultAdditional;
        if (getPlayer().getItemQuantity(cubeId, false) < 1) {
            self.sayOk("ไม่พบ #b#z" + cubeId + "##k");
            return;
        }
        if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 2) {
            self.sayOk("กรุณาทำช่องเก็บของ (Use) ให้ว่างอย่างน้อย 2 ช่อง");
            return;
        }
        List<Short> items = new ArrayList<>();
        for (Item item : getPlayer().getInventory(MapleInventoryType.EQUIPPED).list()) {
            if (item instanceof Equip) {
                if (!additional) {
                    if (((Equip) item).getPotential1() > 0 && ((Equip) item).getPotential2() > 0
                            && ((Equip) item).getPotential3() > 0) {
                        // Unlocked only
                        if ((((Equip) item).getItemState() & ItemStateFlag.LOCK.getValue()) == 0) {
                            items.add((short) ((item.getPosition() * -1) + 10000));
                        }
                    }
                } else {
                    if (((Equip) item).getPotential4() > 0 && ((Equip) item).getPotential5() > 0
                            && ((Equip) item).getPotential6() > 0) {
                        // Unlocked only
                        if ((((Equip) item).getItemState() & ItemStateFlag.LOCK.getValue()) == 0) {
                            items.add((short) ((item.getPosition() * -1) + 10000));
                        }
                    }
                }
            }
        }
        for (Item item : getPlayer().getInventory(MapleInventoryType.EQUIP).list()) {
            if (item instanceof Equip) {
                if (!additional) {
                    if (((Equip) item).getPotential1() > 0 && ((Equip) item).getPotential2() > 0
                            && ((Equip) item).getPotential3() > 0) {
                        // Unlocked only
                        if ((((Equip) item).getItemState() & ItemStateFlag.LOCK.getValue()) == 0) {
                            items.add(item.getPosition());
                        }
                    }
                } else {
                    if (((Equip) item).getPotential4() > 0 && ((Equip) item).getPotential5() > 0
                            && ((Equip) item).getPotential6() > 0) {
                        // Unlocked only
                        if ((((Equip) item).getItemState() & ItemStateFlag.LOCK.getValue()) == 0) {
                            items.add(item.getPosition());
                        }
                    }
                }
            }
        }
        if (items.isEmpty()) {
            self.say(
                    "ไม่พบอุปกรณ์ที่มี Potential ที่สามารถรีเซ็ตได้\r\n#e#r[เงื่อนไข: อุปกรณ์ที่มี Potential 3 แถว]");
            return;
        }
        String menu = "#fs11#กรุณาเลือกไอเท็มที่จะรีเซ็ต Potential\r\n\r\n#r#e※ ข้อควรระวัง ※\r\nเรียงลำดับจาก ช่องสวมใส่ -> ช่องเก็บของ\r\nอุปกรณ์ที่ล็อคอยู่จะไม่แสดง#n#b\r\n";
        for (short i : items) {
            Item CubetoItem;
            var a = i;
            if (i > 1000) {
                CubetoItem = getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short) ((i - 10000) * -1));
                a = 0;
            } else {
                CubetoItem = getPlayer().getInventory(MapleInventoryType.EQUIP).getItem(i);
            }
            int itemid = CubetoItem.getItemId();
            if (a == 0) {
                menu += "\r\n#L" + i + "# #i" + itemid + "# #z" + itemid + "# #r#e[장착중]#n#b";
            } else {
                menu += "\r\n#L" + i + "# #i" + itemid + "# #z" + itemid + "# #r[" + a + "ช่อง]#b";
            }
        }
        int selection = self.askMenu(menu);
        if (selection > -1) {
            Equip selectedItem;
            if (selection > 1000) {
                selectedItem = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                        .getItem((short) ((selection - 10000) * -1));
            } else {
                selectedItem = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIP).getItem((short) selection);
            }
            if (self.askYesNo("#fs11#เลือก하신 ไอเท็ม #i" + selectedItem.getItemId() + ":# #z" + selectedItem.getItemId()
                    + ":#  맞습니까?") != 1) {
                return;
            }

            int cubePiece = getCubePiece(cubeId);
            Equip reItem;
            final long price = GameConstants.getItemReleaseCost(selectedItem.getItemId());
            /*
             * if (getPlayer().getMeso() < price) {
             * self.sayOk("Meso ไม่พอ 잠재ความสามารถ รีเซ็ต 할 수 없.\r\n" + "[จำเป็น Meso : " +
             * decFormat.format(price) + "]");
             * return;
             * }
             */
            if (option == GradeRandomOption.Black) { // 블랙큐브면 메모리얼큐브에 ลงทะเบียน
                Equip neq = (Equip) selectedItem.copy();
                getPlayer().memorialCube = neq;
            }

            long stackMeso = 0;
            int stackCube = 0;
            MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            int itemCount = 0;
            while (getSc() != null) {
                if (cubePiece > 0 ? (target.exchange(cubeId, -1, cubePiece, 1) > 0)
                        : (target.exchange(cubeId, -1) > 0)) { // 최วินาที에 한개 돌림
                    // getPlayer().gainMeso(-price, true);
                    // stackMeso += price;
                    stackCube++;
                    InventoryHandler.setPotentialReturnInt(option, true, selectedItem);
                    if (selection > 1000) {
                        reItem = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                .getItem(selectedItem.getPosition());
                        ((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                .getItem(selectedItem.getPosition())).set(reItem);
                        getPlayer().forceReAddItem(reItem, MapleInventoryType.EQUIPPED);
                    } else {
                        reItem = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIP)
                                .getItem(selectedItem.getPosition());
                        ((Equip) getPlayer().getInventory(MapleInventoryType.EQUIP).getItem(selectedItem.getPosition()))
                                .set(reItem);
                        getPlayer().forceReAddItem(reItem, MapleInventoryType.EQUIP);
                    }
                    Equip zeroEquip;
                    if (GameConstants.isZeroWeapon(reItem.getItemId())) {
                        zeroEquip = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                .getItem(reItem.getPosition() == -11 ? (short) -10 : -11);
                        zeroEquip.setState(reItem.getState());
                        zeroEquip.setLines(reItem.getLines());
                        if (!additional) {
                            zeroEquip.setPotential1(reItem.getPotential1());
                            zeroEquip.setPotential2(reItem.getPotential2());
                            zeroEquip.setPotential3(reItem.getPotential3());
                        } else {
                            zeroEquip.setPotential4(reItem.getPotential4());
                            zeroEquip.setPotential5(reItem.getPotential5());
                            zeroEquip.setPotential6(reItem.getPotential6());
                        }
                        getPlayer().forceReAddItem(
                                getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem(zeroEquip.getPosition()),
                                MapleInventoryType.EQUIPPED);
                    }
                    itemCount = getPlayer().getItemQuantity(cubeId, false);

                    if (stackCube > 500) {
                        StringBuilder sb = new StringBuilder("원클릭 큐브 ใช้ : " + stackCube);
                        LoggingManager.putLog(new EnchantLog(getPlayer(), cubeId, selectedItem.getItemId(),
                                selectedItem.getSerialNumberEquip(), 98, 0, sb));
                    }

                    String text = String.format(
                            "#eจำนวน Cube ที่เหลือ : #r%d ชิ้น#k\r\nCube ที่ใช้ไปจนถึงตอนนี้ : #r%d ชิ้น#k#n#k\r\n\r\n",
                            itemCount, stackCube, decFormat.format(stackMeso));
                    String text2 = "";
                    if (additional) {
                        if (reItem.getPotential4() > 0) {
                            text += "#L3#\r\n#n#k#b#e" + ii.getPotentialString().get(reItem.getPotential4())
                                    .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                        }
                        if (reItem.getPotential5() > 0) {
                            text += "\r\n#n#k#b#e" + ii.getPotentialString().get(reItem.getPotential5())
                                    .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                        }
                        if (reItem.getPotential6() > 0) {
                            text += "\r\n#n#k#b#e" + ii.getPotentialString().get(reItem.getPotential6())
                                    .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                        }
                        text += "#l\r\n\r\n#L4#หมุนอีกครั้ง#l";
                    } else {
                        if (option == GradeRandomOption.Black) {
                            text = "#L0#เลือก Before\r\n#b#e";
                            if (reItem.getPotential1() > 0) {
                                text += ii.getPotentialString().get(getPlayer().memorialCube.getPotential1())
                                        .get(Math.max(1, Math.min(20,
                                                ii.getReqLevel(getPlayer().memorialCube.getItemId()) / 10)));
                                text2 += ii.getPotentialString().get(reItem.getPotential1())
                                        .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()) / 10)));
                            }
                            if (reItem.getPotential2() > 0) {
                                text += "\r\n" + ii.getPotentialString().get(getPlayer().memorialCube.getPotential2())
                                        .get(Math.max(1, Math.min(20,
                                                ii.getReqLevel(getPlayer().memorialCube.getItemId()) / 10)));
                                text2 += "\r\n" + ii.getPotentialString().get(reItem.getPotential2())
                                        .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()) / 10)));
                            }
                            if (reItem.getPotential3() > 0) {
                                text += "\r\n" + ii.getPotentialString().get(getPlayer().memorialCube.getPotential3())
                                        .get(Math.max(1, Math.min(20,
                                                ii.getReqLevel(getPlayer().memorialCube.getItemId()) / 10)));
                                text2 += "\r\n" + ii.getPotentialString().get(reItem.getPotential3())
                                        .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()) / 10)));
                            }
                            text += "#l#k#n\r\n\r\n#L2#เลือก After\r\n#b#e";
                            text += text2;
                        } else {
                            if (reItem.getPotential1() > 0) {
                                text += "\r\n#L3#\r\n#n#k#b#e"
                                        + ii.getPotentialString().get(reItem.getPotential1()).get(Math.max(1,
                                                Math.min(20, (ii.getReqLevel(selectedItem.getItemId()) / 10))));
                            }
                            if (reItem.getPotential2() > 0) {
                                text += "\r\n#n#k#b#e" + ii.getPotentialString().get(reItem.getPotential2()).get(
                                        Math.max(1, Math.min(20, (ii.getReqLevel(selectedItem.getItemId()) / 10))));
                            }
                            if (reItem.getPotential3() > 0) {
                                text += "\r\n#n#k#b#e" + ii.getPotentialString().get(reItem.getPotential3()).get(
                                        Math.max(1, Math.min(20, (ii.getReqLevel(selectedItem.getItemId()) / 10))));
                            }
                            text += "#l\r\n\r\n#L4#หมุนอีกครั้ง#l";
                        }
                    }
                    if (option == GradeRandomOption.Black) {
                        text += "\r\n\r\n#l#n#r#L0#หมุนอีกครั้ง#l\r\n#L3#จบการทำงาน#l";
                        int res = self.askMenu(text, ScriptMessageFlag.NoEsc);
                        if (res == 0)
                            continue;
                        else if (res == 1 || res == 3) {
                            if (selection > 1000) {
                                ((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                        .getItem(selectedItem.getPosition())).set(getPlayer().memorialCube);
                                getPlayer().forceReAddItem(getPlayer().memorialCube, MapleInventoryType.EQUIPPED);
                            } else {
                                ((Equip) getPlayer().getInventory(MapleInventoryType.EQUIP)
                                        .getItem(selectedItem.getPosition())).set(getPlayer().memorialCube);
                                getPlayer().forceReAddItem(getPlayer().memorialCube, MapleInventoryType.EQUIP);
                            }
                            getPlayer().memorialCube = null;
                            break;
                        } else if (res == 2) {
                            if (selection > 1000) {
                                ((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                        .getItem(selectedItem.getPosition())).set(reItem);
                                getPlayer().forceReAddItem(reItem, MapleInventoryType.EQUIPPED);
                            } else {
                                ((Equip) getPlayer().getInventory(MapleInventoryType.EQUIP)
                                        .getItem(selectedItem.getPosition())).set(reItem);
                                getPlayer().forceReAddItem(reItem, MapleInventoryType.EQUIP);
                            }
                            getPlayer().memorialCube = null;
                            break;
                        }
                    } else {
                        if (4 != self.askMenu(text, ScriptMessageFlag.NoEsc)) {
                            break;
                        }
                        continue;
                    }
                } else {
                    self.sayOk("ไม่มี Cube หรือช่องเก็บของ (Use) ไม่พอ การรีเซ็ตจึงล้มเหลว");
                    break;
                }
            }
        }
    }

    private void oneClickCube(int cubeId, GradeRandomOption option) {
        boolean additional = option == GradeRandomOption.Additional || option == GradeRandomOption.OccultAdditional;
        if (getPlayer().getItemQuantity(cubeId, false) < 1) {
            self.sayOk("ไม่พบ #b#z" + cubeId + "##k");
            return;
        }
        if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 2) {
            self.sayOk("กรุณาทำช่องเก็บของ (Use) ให้ว่างอย่างน้อย 2 ช่อง");
            return;
        }

        List<Short> items = new ArrayList<>();
        for (Item item : getPlayer().getInventory(MapleInventoryType.EQUIPPED).list()) {
            if (item instanceof Equip) {
                if (!additional) {
                    if (((Equip) item).getPotential1() > 0 && ((Equip) item).getPotential2() > 0
                            && ((Equip) item).getPotential3() > 0) {
                        // Unlocked only
                        if ((((Equip) item).getItemState() & ItemStateFlag.LOCK.getValue()) == 0) {
                            items.add((short) ((item.getPosition() * -1) + 10000));
                        }
                    }
                } else {
                    if (((Equip) item).getPotential4() > 0 && ((Equip) item).getPotential5() > 0
                            && ((Equip) item).getPotential6() > 0) {
                        // Unlocked only
                        if ((((Equip) item).getItemState() & ItemStateFlag.LOCK.getValue()) == 0) {
                            items.add((short) ((item.getPosition() * -1) + 10000));
                        }
                    }
                }
            }
        }
        for (Item item : getPlayer().getInventory(MapleInventoryType.EQUIP).list()) {
            if (item instanceof Equip) {
                if (!additional) {
                    if (((Equip) item).getPotential1() > 0 && ((Equip) item).getPotential2() > 0
                            && ((Equip) item).getPotential3() > 0) {
                        // Unlocked only
                        if ((((Equip) item).getItemState() & ItemStateFlag.LOCK.getValue()) == 0) {
                            items.add(item.getPosition());
                        }
                    }
                } else {
                    if (((Equip) item).getPotential4() > 0 && ((Equip) item).getPotential5() > 0
                            && ((Equip) item).getPotential6() > 0) {
                        // Unlocked only
                        if ((((Equip) item).getItemState() & ItemStateFlag.LOCK.getValue()) == 0) {
                            items.add(item.getPosition());
                        }
                    }
                }
            }
        }
        if (items.isEmpty()) {
            self.say(
                    "ไม่พบอุปกรณ์ที่มี Potential ที่สามารถรีเซ็ตได้ [เงื่อนไข: อุปกรณ์ที่มี Potential 3 แถว]");
            return;
        }
        String menu = "#fs11#กรุณาเลือกไอเท็มที่จะรีเซ็ต Potential\r\n\r\n#r#e※ ข้อควรระวัง ※\r\nเรียงลำดับจาก ช่องสวมใส่ -> ช่องเก็บของ\r\nอุปกรณ์ที่ล็อคอยู่จะไม่แสดง#n#b\r\n";
        for (short i : items) {
            Item CubetoItem;
            var a = i;
            if (i > 1000) {
                CubetoItem = getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short) ((i - 10000) * -1));
                a = 0;
            } else {
                CubetoItem = getPlayer().getInventory(MapleInventoryType.EQUIP).getItem(i);
            }
            int itemid = CubetoItem.getItemId();
            if (a == 0) {
                menu += "\r\n#L" + i + "# #i" + itemid + "# #z" + itemid + "# #r#e[สวมใส่อยู่]#n#b";
            } else {
                menu += "\r\n#L" + i + "# #i" + itemid + "# #z" + itemid + "# #r[" + a + "ช่อง]#b";
            }
        }
        int selection = self.askMenu(menu);
        if (selection > -1) {
            Equip selectedItem;
            if (selection > 1000) {
                selectedItem = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                        .getItem((short) ((selection - 10000) * -1));
            } else {
                selectedItem = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIP).getItem((short) selection);
            }
            List<ItemOption> allPoptions = getItemOption(selectedItem, option);
            String see = "กรุณาเลือกออปชั่นแถวแรกที่ต้องการ\r\n#r#e※ ข้อควรระวัง ※\r\nจะทำการรีเซ็ตไปเรื่อยๆ จนกว่าจะได้แถวแรกที่ต้องการ โดยจะใช้ Cube 1~x ชิ้น (ยิ่งออกเร็ว ยิ่งประหยัด Cube)#n#b\r\n\r\n";
            int L = 0;
            MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            for (ItemOption opt : allPoptions) {
                see += "#L" + L + "#" + ii.getPotentialString().get(opt.id)
                        .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId())))) + "#l " + "\r\n";
                L++;
            }
            int cubePiece = getCubePiece(cubeId);
            int cubeOption = self.askMenu(see);
            Equip reItem;
            long totalMeso = 0;
            int totalCube = 0;
            int stackCube = 0;
            final long price = GameConstants.getItemReleaseCost(selectedItem.getItemId());
            long stackMeso = price;
            if (getPlayer().getMeso() < price) {
                self.sayOk("Meso ไม่เพียงพอ ไม่สามารถรีเซ็ตได้\r\n" + "[จำเป็น Meso : " + decFormat.format(price)
                        + "]");
                return;
            }
            if (option == GradeRandomOption.Black) { // 블랙큐브면 메모리얼큐브에 ลงทะเบียน
                Equip neq = (Equip) selectedItem.copy();
                getPlayer().memorialCube = neq;
            }
            if (cubePiece > 0) {
                if (target.exchange(cubeId, -1, cubePiece, 1) > 0) { // First spin
                    stackMeso += price;
                    totalCube += 1;
                    totalMeso += price;
                } else {
                    self.sayOk("ช่องเก็บของ (Use) ไม่เพียงพอ");
                    return;
                }
            } else {
                if (target.exchange(cubeId, -1) > 0) { // First spin
                    stackMeso += price;
                    totalCube += 1;
                    totalMeso += price;
                } else {
                    self.sayOk("ช่องเก็บของ (Use) ไม่เพียงพอ");
                    return;
                }
            }
            // String check = "[큐브 1회당 จำเป็น Meso : " + decFormat.format(price)
            // +"Meso]\r\n" + "[누적 ใช้ 큐브 : " + totalCube + "개]\r\n" + "[누적 ใช้ Meso : " +
            // decFormat.format(totalMeso) + "]\r\n";
            int canCubeNumber = getPlayer().getItemQuantity(cubeId, false);
            int potentialLineOne = InventoryHandler.setPotentialReturnInt(option, true, selectedItem);
            while (allPoptions.get(cubeOption).id != potentialLineOne) { // Spin until desired first line
                if (getSc().isStop()) { // Script closed, stop loop
                    break;
                }
                boolean canMeso = getPlayer().getMeso() >= (stackMeso + price);
                if (canCubeNumber > 0 && canMeso) { // If sufficient Cube and Meso, spin again
                    potentialLineOne = InventoryHandler.setPotentialReturnInt(option, true, selectedItem);
                    stackMeso += price;
                    stackCube += 1;
                    totalCube += 1;
                    totalMeso += price;
                    canCubeNumber--;
                } else { // Insufficient Meso
                    if (option == GradeRandomOption.Black) { // Black Cube
                        exchange(cubeId, -stackCube, cubePiece, stackCube);
                        getPlayer().gainMeso(-stackMeso, true);
                        Equip zeroEquip = null;
                        int after = self.askYesNo(
                                "[Meso ที่ต้องใช้ต่อครั้ง : " + decFormat.format(price) + " Meso]\r\n"
                                        + "[Cube สะสมที่ใช้ : "
                                        + totalCube + " ชิ้น]\r\n" + "[Meso สะสมที่ใช้ : " + decFormat.format(totalMeso)
                                        + "]\r\n" + getMemorialCubeString(additional, (short) selection, selectedItem)); // No
                                                                                                                         // =
                                                                                                                         // Before,
                                                                                                                         // Yes
                                                                                                                         // =
                                                                                                                         // Changed
                        if (0 == after) { // No
                            if (selection > 1000) {
                                ((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                        .getItem(selectedItem.getPosition())).set(getPlayer().memorialCube);
                            } else {
                                ((Equip) getPlayer().getInventory(MapleInventoryType.EQUIP)
                                        .getItem(selectedItem.getPosition())).set(getPlayer().memorialCube);
                            }
                        }
                        if (GameConstants.isZeroWeapon(selectedItem.getItemId())) {
                            zeroEquip = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                    .getItem(selectedItem.getPosition() == -11 ? (short) -10 : -11);
                            zeroEquip.setState(((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                    .getItem(selectedItem.getPosition())).getState());
                            zeroEquip.setLines(((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                    .getItem(selectedItem.getPosition())).getLines());
                            if (!additional) {
                                zeroEquip.setPotential1(((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                        .getItem(selectedItem.getPosition())).getPotential1());
                                zeroEquip.setPotential2(((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                        .getItem(selectedItem.getPosition())).getPotential2());
                                zeroEquip.setPotential3(((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                        .getItem(selectedItem.getPosition())).getPotential3());
                            } else {
                                zeroEquip.setPotential4(((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                        .getItem(selectedItem.getPosition())).getPotential4());
                                zeroEquip.setPotential5(((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                        .getItem(selectedItem.getPosition())).getPotential5());
                                zeroEquip.setPotential6(((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                        .getItem(selectedItem.getPosition())).getPotential6());
                            }
                            getPlayer().forceReAddItem(getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                    .getItem(zeroEquip.getPosition()), MapleInventoryType.EQUIPPED);
                        }
                        if (selection > 1000) {
                            getPlayer().forceReAddItem(getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                    .getItem(selectedItem.getPosition()), MapleInventoryType.EQUIPPED);
                        } else {
                            getPlayer().forceReAddItem(getPlayer().getInventory(MapleInventoryType.EQUIP)
                                    .getItem(selectedItem.getPosition()), MapleInventoryType.EQUIP);
                        }
                        getPlayer().memorialCube = null;
                    } else {
                        if (selection > 1000) {
                            getPlayer().forceReAddItem(getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                    .getItem(selectedItem.getPosition()), MapleInventoryType.EQUIPPED);
                        } else {
                            getPlayer().forceReAddItem(getPlayer().getInventory(MapleInventoryType.EQUIP)
                                    .getItem(selectedItem.getPosition()), MapleInventoryType.EQUIP);
                        }
                        if (cubePiece > 0) {
                            exchange(cubeId, -stackCube, cubePiece, stackCube);
                        } else {
                            exchange(cubeId, -stackCube);
                        }
                        getPlayer().gainMeso(-stackMeso, true);
                        self.sayOk("[Meso ที่ต้องใช้ต่อครั้ง : " + decFormat.format(price) + " Meso]\r\n"
                                + "[Cube สะสมที่ใช้ : "
                                + totalCube + " ชิ้น]\r\n" + "[Meso สะสมที่ใช้ : " + decFormat.format(totalMeso)
                                + "]\r\n"
                                + "ไม่มี Cube/Meso หรือช่องเก็บของไม่พอ ไม่สามารถรีเซ็ตต่อได้");
                    }
                    return;
                }
            }
            if (cubePiece > 0) {
                exchange(cubeId, -stackCube, cubePiece, stackCube);
            } else {
                exchange(cubeId, -stackCube);
            }
            getPlayer().gainMeso(-stackMeso, true); // Deduct initial Meso
            stackMeso = 0;
            stackCube = 0;
            if (selection > 1000) {
                reItem = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                        .getItem(selectedItem.getPosition());
            } else {
                reItem = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIP).getItem(selectedItem.getPosition());
            }
            Equip zeroEquip;
            if (GameConstants.isZeroWeapon(reItem.getItemId())) {
                zeroEquip = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                        .getItem(reItem.getPosition() == -11 ? (short) -10 : -11);
                zeroEquip.setState(reItem.getState());
                zeroEquip.setLines(reItem.getLines());
                if (!additional) {
                    zeroEquip.setPotential1(reItem.getPotential1());
                    zeroEquip.setPotential2(reItem.getPotential2());
                    zeroEquip.setPotential3(reItem.getPotential3());
                } else {
                    zeroEquip.setPotential4(reItem.getPotential4());
                    zeroEquip.setPotential5(reItem.getPotential5());
                    zeroEquip.setPotential6(reItem.getPotential6());
                }
                getPlayer().forceReAddItem(
                        getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem(zeroEquip.getPosition()),
                        MapleInventoryType.EQUIPPED);
            }
            if (allPoptions.get(cubeOption).id != potentialLineOne) {
                return;
            }
            if (reItem == null) {
                return;
            }
            String text = "";
            if (option == GradeRandomOption.Black) {
                if (!additional) {
                    if (getPlayer().memorialCube.getPotential1() > 0) {
                        text += "첫번째 줄 ตัวเลือก : "
                                + ii.getPotentialString().get(getPlayer().memorialCube.getPotential1())
                                        .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                    }
                } else {
                    if (getPlayer().memorialCube.getPotential4() > 0) {
                        text += "에디셔널 첫번째 줄 ตัวเลือก : "
                                + ii.getPotentialString().get(getPlayer().memorialCube.getPotential4())
                                        .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                    }
                }
                if (!additional) {
                    if (getPlayer().memorialCube.getPotential2() > 0) {
                        text += "\r\n두번째 줄 ตัวเลือก : "
                                + ii.getPotentialString().get(getPlayer().memorialCube.getPotential2())
                                        .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                    }
                } else {
                    if (getPlayer().memorialCube.getPotential5() > 0) {
                        text += "\r\n에디셔널 두번째 줄 ตัวเลือก : "
                                + ii.getPotentialString().get(getPlayer().memorialCube.getPotential5())
                                        .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                    }
                }
                if (!additional) {
                    if (getPlayer().memorialCube.getPotential3() > 0) {
                        text += "\r\n세번째 줄 ตัวเลือก : "
                                + ii.getPotentialString().get(getPlayer().memorialCube.getPotential3())
                                        .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                    }
                } else {
                    if (getPlayer().memorialCube.getPotential6() > 0) {
                        text += "\r\n에디셔널 세번째 줄 ตัวเลือก : "
                                + ii.getPotentialString().get(getPlayer().memorialCube.getPotential6())
                                        .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                    }
                }
                if (selection > 1000) {
                    reItem = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                            .getItem(selectedItem.getPosition());
                } else {
                    reItem = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIP)
                            .getItem(selectedItem.getPosition());
                }
                text += "\r\n\r\n#k[After ตัวเลือก]#b\r\n";
                if (!additional) {
                    if (reItem.getPotential1() > 0) {
                        text += "첫번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential1())
                                .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                    }
                } else {
                    if (reItem.getPotential4() > 0) {
                        text += "에디셔널 첫번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential4())
                                .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                    }
                }
                if (!additional) {
                    if (reItem.getPotential2() > 0) {
                        text += "\r\n두번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential2())
                                .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                    }
                } else {
                    if (reItem.getPotential5() > 0) {
                        text += "\r\n에디셔널 두번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential5())
                                .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                    }
                }
                if (!additional) {
                    if (reItem.getPotential3() > 0) {
                        text += "\r\n세번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential3())
                                .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                    }
                } else {
                    if (reItem.getPotential6() > 0) {
                        text += "\r\n에디셔널 세번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential6())
                                .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                    }
                }
            } else {
                if (additional) {
                    if (reItem.getPotential4() > 0) {
                        text += "에디셔널 첫번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential4())
                                .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                    }
                    if (reItem.getPotential5() > 0) {
                        text += "\r\n에디셔널 두번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential5())
                                .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                    }
                    if (reItem.getPotential6() > 0) {
                        text += "\r\n에디셔널 세번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential6())
                                .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                    }
                } else {
                    if (reItem.getPotential1() > 0) {
                        text += "첫번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential1())
                                .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                    }
                    if (reItem.getPotential2() > 0) {
                        text += "\r\n두번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential2())
                                .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                    }
                    if (reItem.getPotential3() > 0) {
                        text += "\r\n세번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential3())
                                .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                    }
                }
            }

            int yesNo = -1;
            if (option == GradeRandomOption.Black) { // 블랙큐브วัน때
                yesNo = self.askMenu("[큐브 1회당 จำเป็น Meso : " + decFormat.format(price) + "Meso]\r\n" + "[누적 ใช้ 큐브 : "
                        + totalCube + "개]\r\n" + "[누적 ใช้ Meso : " + decFormat.format(totalMeso) + "]\r\n"
                        + "첫번째 줄 ตัวเลือก ได้รับ에 สำเร็จแล้ว.\r\n\r\n [Before ตัวเลือก] \r\n#b" + text
                        + "\r\n\r\n#e#r[남은 큐브 : " + getPlayer().getItemQuantity(cubeId, false) + "개]"
                        + "\r\n[남은 Meso : " + decFormat.format(getPlayer().getMeso()) + "Meso]"
                        + "\r\n#n#b#L0#[Before ตัวเลือก] เลือก하기#l\r\n#L1#[After ตัวเลือก] เลือก하기#l\r\n#L2#[다시 돌리기] เลือก하기");
                if (yesNo == 0) { // beforeเลือก
                    if (selection > 1000) {
                        ((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                .getItem(selectedItem.getPosition())).set(getPlayer().memorialCube);
                    } else {
                        ((Equip) getPlayer().getInventory(MapleInventoryType.EQUIP).getItem(selectedItem.getPosition()))
                                .set(getPlayer().memorialCube);
                    }
                    if (GameConstants.isZeroWeapon(reItem.getItemId())) {
                        zeroEquip = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                .getItem(reItem.getPosition() == -11 ? (short) -10 : -11);
                        zeroEquip.setState(reItem.getState());
                        zeroEquip.setLines(reItem.getLines());
                        if (!additional) {
                            zeroEquip.setPotential1(reItem.getPotential1());
                            zeroEquip.setPotential2(reItem.getPotential2());
                            zeroEquip.setPotential3(reItem.getPotential3());
                        } else {
                            zeroEquip.setPotential4(reItem.getPotential4());
                            zeroEquip.setPotential5(reItem.getPotential5());
                            zeroEquip.setPotential6(reItem.getPotential6());
                        }
                        getPlayer().forceReAddItem(
                                getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem(zeroEquip.getPosition()),
                                MapleInventoryType.EQUIPPED);
                    }
                    if (selection > 1000) {
                        getPlayer().forceReAddItem(reItem, MapleInventoryType.EQUIPPED);
                    } else {
                        getPlayer().forceReAddItem(reItem, MapleInventoryType.EQUIP);
                    }
                    getPlayer().memorialCube = null;
                    return;
                } else if (yesNo == 1) { // afterเลือก
                    if (selection > 1000) {
                        reItem = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                .getItem(selectedItem.getPosition());
                    } else {
                        reItem = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIP)
                                .getItem(selectedItem.getPosition());
                    }
                    if (GameConstants.isZeroWeapon(reItem.getItemId())) {
                        zeroEquip = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                .getItem(reItem.getPosition() == -11 ? (short) -10 : -11);
                        zeroEquip.setState(reItem.getState());
                        zeroEquip.setLines(reItem.getLines());
                        if (!additional) {
                            zeroEquip.setPotential1(reItem.getPotential1());
                            zeroEquip.setPotential2(reItem.getPotential2());
                            zeroEquip.setPotential3(reItem.getPotential3());
                        } else {
                            zeroEquip.setPotential4(reItem.getPotential4());
                            zeroEquip.setPotential5(reItem.getPotential5());
                            zeroEquip.setPotential6(reItem.getPotential6());
                        }
                        getPlayer().forceReAddItem(
                                getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem(zeroEquip.getPosition()),
                                MapleInventoryType.EQUIPPED);
                    }
                    if (selection > 1000) {
                        getPlayer().forceReAddItem(reItem, MapleInventoryType.EQUIPPED);
                    } else {
                        getPlayer().forceReAddItem(reItem, MapleInventoryType.EQUIP);
                    }
                    getPlayer().memorialCube = null;
                    return;
                }
            } else { // 블큐아닐때
                if (selection > 1000) {
                    getPlayer().forceReAddItem(
                            getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem(selectedItem.getPosition()),
                            MapleInventoryType.EQUIPPED);
                } else {
                    getPlayer().forceReAddItem(
                            getPlayer().getInventory(MapleInventoryType.EQUIP).getItem(selectedItem.getPosition()),
                            MapleInventoryType.EQUIP);
                }
                yesNo = self.askYesNo("[큐브 1회당 จำเป็น Meso : " + decFormat.format(price) + "Meso]\r\n" + "[누적 ใช้ 큐브 : "
                        + totalCube + "개]\r\n" + "[누적 ใช้ Meso : " + decFormat.format(totalMeso) + "]\r\n"
                        + "첫번째 줄 ตัวเลือก ได้รับ에 สำเร็จแล้ว.\r\n\r\n [큐브 ตัวเลือก] \r\n#b" + text
                        + "\r\n\r\n#e#r다시 돌리시หรือไม่?\r\n#b[남은 큐브 : " + getPlayer().getItemQuantity(cubeId, false)
                        + "개]\r\n[남은 Meso : " + decFormat.format(getPlayer().getMeso()) + "Meso]");
            }
            while (yesNo != 0) {
                if (getSc().isStop()) {
                    break;
                }

                boolean canMeso = getPlayer().getMeso() >= (stackMeso + price);
                if (canCubeNumber > 0 && canMeso) {
                    potentialLineOne = InventoryHandler.setPotentialReturnInt(option, true, selectedItem);
                    stackMeso += price;
                    canCubeNumber -= 1;
                    stackCube += 1;
                    totalCube += 1;
                    totalMeso += price;
                } else { // Meso여유없어
                    if (option == GradeRandomOption.Black) { // 블랙큐브วัน경우
                        exchange(cubeId, -stackCube, cubePiece, stackCube);
                        getPlayer().gainMeso(-stackMeso, true);
                        int after = self.askYesNo(
                                "[큐브 1회당 จำเป็น Meso : " + decFormat.format(price) + "Meso]\r\n" + "[누적 ใช้ 큐브 : "
                                        + totalCube + "개]\r\n" + "[누적 ใช้ Meso : " + decFormat.format(totalMeso)
                                        + "]\r\n" + getMemorialCubeString(additional, (short) selection, selectedItem)); // 아니오
                                                                                                                         // 누르면
                                                                                                                         // before
                                                                                                                         // เปลี่ยน
                        if (0 == after) { // 아니오
                            if (selection > 1000) {
                                ((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                        .getItem(selectedItem.getPosition())).set(getPlayer().memorialCube);
                            } else {
                                ((Equip) getPlayer().getInventory(MapleInventoryType.EQUIP)
                                        .getItem(selectedItem.getPosition())).set(getPlayer().memorialCube);
                            }
                        }
                        if (GameConstants.isZeroWeapon(selectedItem.getItemId())) {
                            zeroEquip = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                    .getItem(selectedItem.getPosition() == -11 ? (short) -10 : -11);
                            zeroEquip.setState(((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                    .getItem(selectedItem.getPosition())).getState());
                            zeroEquip.setLines(((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                    .getItem(selectedItem.getPosition())).getLines());
                            if (!additional) {
                                zeroEquip.setPotential1(((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                        .getItem(selectedItem.getPosition())).getPotential1());
                                zeroEquip.setPotential2(((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                        .getItem(selectedItem.getPosition())).getPotential2());
                                zeroEquip.setPotential3(((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                        .getItem(selectedItem.getPosition())).getPotential3());
                            } else {
                                zeroEquip.setPotential4(((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                        .getItem(selectedItem.getPosition())).getPotential4());
                                zeroEquip.setPotential5(((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                        .getItem(selectedItem.getPosition())).getPotential5());
                                zeroEquip.setPotential6(((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                        .getItem(selectedItem.getPosition())).getPotential6());
                            }
                            getPlayer().forceReAddItem(getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                    .getItem(zeroEquip.getPosition()), MapleInventoryType.EQUIPPED);
                        }
                        if (selection > 1000) {
                            getPlayer().forceReAddItem(getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                    .getItem(selectedItem.getPosition()), MapleInventoryType.EQUIPPED);
                        } else {
                            getPlayer().forceReAddItem(getPlayer().getInventory(MapleInventoryType.EQUIP)
                                    .getItem(selectedItem.getPosition()), MapleInventoryType.EQUIP);
                        }
                        getPlayer().memorialCube = null;
                    } else {
                        if (selection > 1000) {
                            getPlayer().forceReAddItem(getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                    .getItem(selectedItem.getPosition()), MapleInventoryType.EQUIPPED);
                        } else {
                            getPlayer().forceReAddItem(getPlayer().getInventory(MapleInventoryType.EQUIP)
                                    .getItem(selectedItem.getPosition()), MapleInventoryType.EQUIP);
                        }
                        if (cubePiece > 0) {
                            exchange(cubeId, -stackCube, cubePiece, stackCube);
                        } else {
                            exchange(cubeId, -stackCube);
                        }
                        getPlayer().gainMeso(-stackMeso, true);
                        self.sayOk("[큐브 1회당 จำเป็น Meso : " + decFormat.format(price) + "Meso]\r\n" + "[누적 ใช้ 큐브 : "
                                + totalCube + "개]\r\n" + "[누적 ใช้ Meso : " + decFormat.format(totalMeso) + "]\r\n"
                                + "더이상 큐브, Meso 없거ฉัน ใช้창에 여유공간이 없어 รีเซ็ต 할 수 없.");
                    }
                    return;
                }
                while (allPoptions.get(cubeOption).id != potentialLineOne) {
                    if (getSc().isStop()) {
                        break;
                    }
                    canMeso = getPlayer().getMeso() >= (stackMeso + price);
                    if (canCubeNumber > 0 && canMeso) {
                        potentialLineOne = InventoryHandler.setPotentialReturnInt(option, true, selectedItem);
                        canCubeNumber -= 1;
                        stackCube += 1;
                        stackMeso += price;
                        totalCube += 1;
                        totalMeso += price;
                    } else {
                        if (option == GradeRandomOption.Black) { // 블랙큐브วัน경우
                            exchange(cubeId, -stackCube, cubePiece, stackCube);
                            getPlayer().gainMeso(-stackMeso, true);
                            int after = self.askYesNo("[큐브 1회당 จำเป็น Meso : " + decFormat.format(price) + "Meso]\r\n"
                                    + "[누적 ใช้ 큐브 : " + totalCube + "개]\r\n" + "[누적 ใช้ Meso : "
                                    + decFormat.format(totalMeso) + "]\r\n"
                                    + getMemorialCubeString(additional, (short) selection, selectedItem)); // 아니오 누르면
                                                                                                           // before
                                                                                                           // เปลี่ยน
                            if (0 == after) { // 아니오
                                if (selection > 1000) {
                                    ((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                            .getItem(selectedItem.getPosition())).set(getPlayer().memorialCube);
                                } else {
                                    ((Equip) getPlayer().getInventory(MapleInventoryType.EQUIP)
                                            .getItem(selectedItem.getPosition())).set(getPlayer().memorialCube);
                                }
                            }
                            if (GameConstants.isZeroWeapon(selectedItem.getItemId())) {
                                zeroEquip = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                        .getItem(selectedItem.getPosition() == -11 ? (short) -10 : -11);
                                zeroEquip.setState(((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                        .getItem(selectedItem.getPosition())).getState());
                                zeroEquip.setLines(((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                        .getItem(selectedItem.getPosition())).getLines());
                                if (!additional) {
                                    zeroEquip.setPotential1(
                                            ((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                                    .getItem(selectedItem.getPosition())).getPotential1());
                                    zeroEquip.setPotential2(
                                            ((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                                    .getItem(selectedItem.getPosition())).getPotential2());
                                    zeroEquip.setPotential3(
                                            ((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                                    .getItem(selectedItem.getPosition())).getPotential3());
                                } else {
                                    zeroEquip.setPotential4(
                                            ((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                                    .getItem(selectedItem.getPosition())).getPotential4());
                                    zeroEquip.setPotential5(
                                            ((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                                    .getItem(selectedItem.getPosition())).getPotential5());
                                    zeroEquip.setPotential6(
                                            ((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                                    .getItem(selectedItem.getPosition())).getPotential6());
                                }
                                getPlayer().forceReAddItem(getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                        .getItem(zeroEquip.getPosition()), MapleInventoryType.EQUIPPED);
                            }
                            if (selection > 1000) {
                                getPlayer().forceReAddItem(getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                        .getItem(selectedItem.getPosition()), MapleInventoryType.EQUIPPED);
                            } else {
                                getPlayer().forceReAddItem(getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                        .getItem(selectedItem.getPosition()), MapleInventoryType.EQUIP);
                            }
                            getPlayer().memorialCube = null;
                        } else {
                            if (selection > 1000) {
                                getPlayer().forceReAddItem(reItem, MapleInventoryType.EQUIPPED);
                            } else {
                                getPlayer().forceReAddItem(reItem, MapleInventoryType.EQUIP);
                            }
                            if (cubePiece > 0) {
                                exchange(cubeId, -stackCube, cubePiece, stackCube);
                            } else {
                                exchange(cubeId, -stackCube);
                            }
                            getPlayer().gainMeso(-stackMeso, true);
                            self.sayOk(
                                    "[큐브 1회당 จำเป็น Meso : " + decFormat.format(price) + "Meso]\r\n" + "[누적 ใช้ 큐브 : "
                                            + totalCube + "개]\r\n" + "[누적 ใช้ Meso : " + decFormat.format(totalMeso)
                                            + "]\r\n" + "더이상 큐브, Meso 없거ฉัน ใช้창에 여유공간이 없어 รีเซ็ต 할 수 없.");
                        }
                        return;
                    }
                }
                if (selection > 1000) {
                    getPlayer().forceReAddItem(
                            getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem(selectedItem.getPosition()),
                            MapleInventoryType.EQUIPPED);
                } else {
                    getPlayer().forceReAddItem(
                            getPlayer().getInventory(MapleInventoryType.EQUIP).getItem(selectedItem.getPosition()),
                            MapleInventoryType.EQUIP);
                }
                if (cubePiece > 0) {
                    exchange(cubeId, -stackCube, cubePiece, stackCube);
                } else {
                    exchange(cubeId, -stackCube);
                }
                getPlayer().gainMeso(-stackMeso, true);
                stackCube = 0;
                stackMeso = 0;
                text = "";
                if (option == GradeRandomOption.Black) {
                    if (!additional) {
                        if (getPlayer().memorialCube.getPotential1() > 0) {
                            text += "첫번째 줄 ตัวเลือก : "
                                    + ii.getPotentialString().get(getPlayer().memorialCube.getPotential1())
                                            .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                        }
                    } else {
                        if (getPlayer().memorialCube.getPotential4() > 0) {
                            text += "에디셔널 첫번째 줄 ตัวเลือก : "
                                    + ii.getPotentialString().get(getPlayer().memorialCube.getPotential4())
                                            .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                        }
                    }
                    if (!additional) {
                        if (getPlayer().memorialCube.getPotential2() > 0) {
                            text += "\r\n두번째 줄 ตัวเลือก : "
                                    + ii.getPotentialString().get(getPlayer().memorialCube.getPotential2())
                                            .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                        }
                    } else {
                        if (getPlayer().memorialCube.getPotential5() > 0) {
                            text += "\r\n에디셔널 두번째 줄 ตัวเลือก : "
                                    + ii.getPotentialString().get(getPlayer().memorialCube.getPotential5())
                                            .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                        }
                    }
                    if (!additional) {
                        if (getPlayer().memorialCube.getPotential3() > 0) {
                            text += "\r\n세번째 줄 ตัวเลือก : "
                                    + ii.getPotentialString().get(getPlayer().memorialCube.getPotential3())
                                            .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                        }
                    } else {
                        if (getPlayer().memorialCube.getPotential6() > 0) {
                            text += "\r\n에디셔널 세번째 줄 ตัวเลือก : "
                                    + ii.getPotentialString().get(getPlayer().memorialCube.getPotential6())
                                            .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                        }
                    }
                    if (selection > 1000) {
                        reItem = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                .getItem(selectedItem.getPosition());
                    } else {
                        reItem = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIP)
                                .getItem(selectedItem.getPosition());
                    }
                    text += "\r\n\r\n#k[After ตัวเลือก]#b\r\n";
                    if (!additional) {
                        if (reItem.getPotential1() > 0) {
                            text += "첫번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential1())
                                    .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                        }
                    } else {
                        if (reItem.getPotential4() > 0) {
                            text += "에디셔널 첫번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential4())
                                    .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                        }
                    }
                    if (!additional) {
                        if (reItem.getPotential2() > 0) {
                            text += "\r\n두번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential2())
                                    .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                        }
                    } else {
                        if (reItem.getPotential5() > 0) {
                            text += "\r\n에디셔널 두번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential5())
                                    .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                        }
                    }
                    if (!additional) {
                        if (reItem.getPotential3() > 0) {
                            text += "\r\n세번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential3())
                                    .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                        }
                    } else {
                        if (reItem.getPotential6() > 0) {
                            text += "\r\n에디셔널 세번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential6())
                                    .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                        }
                    }
                } else {
                    if (additional) {
                        if (reItem.getPotential4() > 0) {
                            text += "에디셔널 첫번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential4())
                                    .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                        }
                        if (reItem.getPotential5() > 0) {
                            text += "\r\n에디셔널 두번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential5())
                                    .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                        }
                        if (reItem.getPotential6() > 0) {
                            text += "\r\n에디셔널 세번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential6())
                                    .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                        }
                    } else {
                        if (reItem.getPotential1() > 0) {
                            text += "첫번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential1())
                                    .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                        }
                        if (reItem.getPotential2() > 0) {
                            text += "\r\n두번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential2())
                                    .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                        }
                        if (reItem.getPotential3() > 0) {
                            text += "\r\n세번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential3())
                                    .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
                        }
                    }
                }
                if (option == GradeRandomOption.Black) { // 블랙큐브วัน때
                    if (selection > 1000) {
                        getPlayer().forceReAddItem(getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                .getItem(selectedItem.getPosition()), MapleInventoryType.EQUIPPED);
                    } else {
                        getPlayer().forceReAddItem(
                                getPlayer().getInventory(MapleInventoryType.EQUIP).getItem(selectedItem.getPosition()),
                                MapleInventoryType.EQUIP);
                    }
                    yesNo = self.askMenu("[큐브 1회당 จำเป็น Meso : " + decFormat.format(price) + "Meso]\r\n"
                            + "[누적 ใช้ 큐브 : " + totalCube + "개]\r\n" + "[누적 ใช้ Meso : " + decFormat.format(totalMeso)
                            + "]\r\n" + "첫번째 줄 ตัวเลือก ได้รับ에 สำเร็จแล้ว.\r\n\r\n [Before ตัวเลือก] \r\n#b" + text
                            + "\r\n\r\n#e#r\r\n[남은 큐브 : " + getPlayer().getItemQuantity(cubeId, false) + "개]"
                            + "\r\n[남은 Meso : " + decFormat.format(getPlayer().getMeso()) + "Meso]"
                            + "\r\n#n#b#L0#[Before ตัวเลือก] เลือก하기#l\r\n#L1#[After ตัวเลือก] เลือก하기#l\r\n#L2#[다시 돌리기] เลือก하기");
                    if (yesNo == 0) { // beforeเลือก
                        if (selection > 1000) {
                            ((Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                    .getItem(selectedItem.getPosition())).set(getPlayer().memorialCube);
                        } else {
                            ((Equip) getPlayer().getInventory(MapleInventoryType.EQUIP)
                                    .getItem(selectedItem.getPosition())).set(getPlayer().memorialCube);
                        }
                        if (GameConstants.isZeroWeapon(reItem.getItemId())) {
                            zeroEquip = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                    .getItem(reItem.getPosition() == -11 ? (short) -10 : -11);
                            zeroEquip.setState(reItem.getState());
                            zeroEquip.setLines(reItem.getLines());
                            if (!additional) {
                                zeroEquip.setPotential1(reItem.getPotential1());
                                zeroEquip.setPotential2(reItem.getPotential2());
                                zeroEquip.setPotential3(reItem.getPotential3());
                            } else {
                                zeroEquip.setPotential4(reItem.getPotential4());
                                zeroEquip.setPotential5(reItem.getPotential5());
                                zeroEquip.setPotential6(reItem.getPotential6());
                            }
                            getPlayer().forceReAddItem(getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                    .getItem(zeroEquip.getPosition()), MapleInventoryType.EQUIPPED);
                        }
                        if (selection > 1000) {
                            getPlayer().forceReAddItem(reItem, MapleInventoryType.EQUIPPED);
                        } else {
                            getPlayer().forceReAddItem(reItem, MapleInventoryType.EQUIP);
                        }
                        getPlayer().memorialCube = null;
                        return;
                    } else if (yesNo == 1) { // afterเลือก
                        if (selection > 1000) {
                            reItem = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                    .getItem(selectedItem.getPosition());
                        } else {
                            reItem = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIP)
                                    .getItem(selectedItem.getPosition());
                        }
                        if (GameConstants.isZeroWeapon(reItem.getItemId())) {
                            zeroEquip = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                    .getItem(reItem.getPosition() == -11 ? (short) -10 : -11);
                            zeroEquip.setState(reItem.getState());
                            zeroEquip.setLines(reItem.getLines());
                            if (!additional) {
                                zeroEquip.setPotential1(reItem.getPotential1());
                                zeroEquip.setPotential2(reItem.getPotential2());
                                zeroEquip.setPotential3(reItem.getPotential3());
                            } else {
                                zeroEquip.setPotential4(reItem.getPotential4());
                                zeroEquip.setPotential5(reItem.getPotential5());
                                zeroEquip.setPotential6(reItem.getPotential6());
                            }
                            getPlayer().forceReAddItem(getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                    .getItem(zeroEquip.getPosition()), MapleInventoryType.EQUIPPED);
                        }
                        if (selection > 1000) {
                            getPlayer().forceReAddItem(reItem, MapleInventoryType.EQUIPPED);
                        } else {
                            getPlayer().forceReAddItem(reItem, MapleInventoryType.EQUIP);
                        }
                        getPlayer().memorialCube = null;
                        return;
                    }
                } else { // 블큐아닐때
                    if (GameConstants.isZeroWeapon(reItem.getItemId())) {
                        zeroEquip = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                .getItem(reItem.getPosition() == -11 ? (short) -10 : -11);
                        zeroEquip.setState(reItem.getState());
                        zeroEquip.setLines(reItem.getLines());
                        if (!additional) {
                            zeroEquip.setPotential1(reItem.getPotential1());
                            zeroEquip.setPotential2(reItem.getPotential2());
                            zeroEquip.setPotential3(reItem.getPotential3());
                        } else {
                            zeroEquip.setPotential4(reItem.getPotential4());
                            zeroEquip.setPotential5(reItem.getPotential5());
                            zeroEquip.setPotential6(reItem.getPotential6());
                        }
                        getPlayer().forceReAddItem(
                                getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem(zeroEquip.getPosition()),
                                MapleInventoryType.EQUIPPED);
                    }
                    if (selection > 1000) {
                        getPlayer().forceReAddItem(reItem, MapleInventoryType.EQUIPPED);
                    } else {
                        getPlayer().forceReAddItem(reItem, MapleInventoryType.EQUIP);
                    }
                    yesNo = self.askYesNo("[큐브 1회당 จำเป็น Meso : " + decFormat.format(price) + "Meso]\r\n"
                            + "[누적 ใช้ 큐브 : " + totalCube + "개]\r\n" + "[누적 ใช้ Meso : " + decFormat.format(totalMeso)
                            + "]\r\n" + "첫번째 줄 ตัวเลือก ได้รับ에 สำเร็จแล้ว.\r\n\r\n [큐브 ตัวเลือก] \r\n#b" + text
                            + "\r\n\r\n#e#r다시 돌리시หรือไม่?\r\n[남은 큐브 : " + getPlayer().getItemQuantity(cubeId, false)
                            + "개]" + "\r\n[남은 Meso : " + decFormat.format(getPlayer().getMeso()) + "Meso]");
                }
            }
        }
    }

    private List<ItemOption> getItemOption(Item item, GradeRandomOption option) {
        List<Integer> optionTypes = ItemOptionInfo.getOptionTypes(item.getItemId());
        Map<Integer, List<ItemOption>> options;
        List<ItemOption> list;
        MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        options = ItemOptionInfo.optionsSorted.get(4);
        List<ItemOption> allPoptions = new ArrayList<>();
        for (int opt : optionTypes) {
            list = options.get(opt);
            if (list == null || list.isEmpty()) {
                continue;
            }
            for (ItemOption op : list) {
                if (option == GradeRandomOption.Additional || option == GradeRandomOption.OccultAdditional) {
                    if (ItemOptionInfo.isAdditional(op.id)) {
                        allPoptions.add(op);
                    }
                } else {
                    if (!ItemOptionInfo.isAdditional(op.id)) {
                        allPoptions.add(op);
                    }
                }
            }
        }
        allPoptions = allPoptions.stream()
                .filter(a -> ItemOptionInfo.getCustomMaxLine(itemID, a, optionTypes) > 0)
                .filter(a -> a.reqLevel <= ii.getReqLevel(itemID))
                .filter(a -> ItemOptionPercentageInfo.getItemOptionPercentageInfo(option, a.id) > 0)
                .collect(Collectors.toList());
        return allPoptions;
    }

    private String getMemorialCubeString(boolean additional, short selection, Item selectedItem) {
        MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        String memorialText = "큐브가 ไม่พอ하거ฉัน Meso ไม่พอ. #r[After ตัวเลือก]#k เปลี่ยนต้องการหรือไม่?\r\n\r\n#b[Before ตัวเลือก]#k\r\n";
        if (!additional) {
            if (getPlayer().memorialCube.getPotential1() > 0) {
                memorialText += "첫번째 줄 ตัวเลือก : "
                        + ii.getPotentialString().get(getPlayer().memorialCube.getPotential1())
                                .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
            }
        } else {
            if (getPlayer().memorialCube.getPotential4() > 0) {
                memorialText += "에디셔널 첫번째 줄 ตัวเลือก : "
                        + ii.getPotentialString().get(getPlayer().memorialCube.getPotential4())
                                .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
            }
        }
        if (!additional) {
            if (getPlayer().memorialCube.getPotential2() > 0) {
                memorialText += "\r\n두번째 줄 ตัวเลือก : "
                        + ii.getPotentialString().get(getPlayer().memorialCube.getPotential2())
                                .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
            }
        } else {
            if (getPlayer().memorialCube.getPotential5() > 0) {
                memorialText += "\r\n에디셔널 두번째 줄 ตัวเลือก : "
                        + ii.getPotentialString().get(getPlayer().memorialCube.getPotential5())
                                .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
            }
        }
        if (!additional) {
            if (getPlayer().memorialCube.getPotential3() > 0) {
                memorialText += "\r\n세번째 줄 ตัวเลือก : "
                        + ii.getPotentialString().get(getPlayer().memorialCube.getPotential3())
                                .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
            }
        } else {
            if (getPlayer().memorialCube.getPotential6() > 0) {
                memorialText += "\r\n에디셔널 세번째 줄 ตัวเลือก : "
                        + ii.getPotentialString().get(getPlayer().memorialCube.getPotential6())
                                .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
            }
        }
        Equip reItem = null;
        if (selection > 1000) {
            reItem = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem(selectedItem.getPosition());
        } else {
            reItem = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIP).getItem(selectedItem.getPosition());
        }
        memorialText += "\r\n#k[After ตัวเลือก]#b\r\n";
        if (!additional) {
            if (reItem.getPotential1() > 0) {
                memorialText += "첫번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential1())
                        .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
            }
        } else {
            if (reItem.getPotential4() > 0) {
                memorialText += "에디셔널 첫번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential4())
                        .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
            }
        }
        if (!additional) {
            if (reItem.getPotential2() > 0) {
                memorialText += "\r\n두번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential2())
                        .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
            }
        } else {
            if (reItem.getPotential5() > 0) {
                memorialText += "\r\n에디셔널 두번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential5())
                        .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
            }
        }
        if (!additional) {
            if (reItem.getPotential3() > 0) {
                memorialText += "\r\n세번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential3())
                        .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
            }
        } else {
            if (reItem.getPotential6() > 0) {
                memorialText += "\r\n에디셔널 세번째 줄 ตัวเลือก : " + ii.getPotentialString().get(reItem.getPotential6())
                        .get(Math.max(1, Math.min(20, ii.getReqLevel(selectedItem.getItemId()))));
            }
        }
        memorialText += "\r\n #r#e예#k 누르면 [After ตัวเลือก] เปลี่ยน.";
        return memorialText;
    }
}
