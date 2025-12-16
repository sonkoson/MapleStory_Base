package script.item;

import database.DBConfig;
import network.discordbot.DiscordBotHandler;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.item.MapleInventoryType;
import objects.utils.Randomizer;
import scripting.newscripting.ScriptEngineNPC;

import java.util.*;

public class SymbolVoucher extends ScriptEngineNPC {

    public void consume_2437750() {
        // 1 item
        changeArcaneSymbolVoucher(1);
    }

    public void consume_2437760() {
        // 5 items
        changeArcaneSymbolVoucher(5);
    }

    public void consume_2437880() {
        // 2 items
        changeArcaneSymbolVoucher(2);
    }

    public void consume_2438141() {
        // 1개
        changeArcaneSymbolVoucher(1);
    }

    public void consume_2439301() {
        // 20 items
        changeArcaneSymbolVoucher(20);
    }

    public void consume_2439305() {
        // 1개
        changeArcaneSymbolVoucher(1);
    }

    public void consume_2631878() {
        // 100 items
        changeArcaneSymbolVoucher(100);
    }

    public void consume_2631906() {
        // 10 items
        changeArcaneSymbolVoucher(10);
    }

    public void consume_2633385() {
        // 100 items
        changeArcaneSymbolVoucher(100);
    }

    public void consume_2630981() {
        // 5 items
        changeArcaneSymbolVoucher(5);
    }

    public void consume_2630983() {
        // Random
        /**
         * 1 pc (45%)
         * 2 pcs (45%)
         * 5 pcs (9%)
         * 10 pcs (0.8%)
         * 50 pcs (0.16%)
         * 100 pcs (0.04%)
         */
        HashMap<Integer, Double> itemQty = new HashMap();
        itemQty.put(1, 45.0d);
        itemQty.put(2, 45.0d);
        itemQty.put(5, 9.0d);
        itemQty.put(10, 0.8d);
        itemQty.put(50, 0.16d);
        itemQty.put(100, 0.04d);
        List<Integer> keys = new ArrayList<>(itemQty.keySet());
        Collections.shuffle(keys);

        double percent = 45.0d + 45.0d + 9.0d + 0.8d + 0.16d + 0.04d;
        double random = percent - (Randomizer.nextDouble() * 100);
        double stack = 0.0d;
        int quantity = 0;
        Iterator<Integer> ite = keys.iterator();
        while (ite.hasNext() && quantity == 0) {
            int a = ite.next();
            Double p = itemQty.get(a);
            stack += p;
            if (stack >= random) {
                quantity = a;
            }
        }
        changeArcaneSymbolVoucher(quantity);
    }

    public void changeArcaneSymbolVoucher(int quantity) {
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 1) {
            self.sayOk("ช่องเก็บของ USE ไม่เพียงพอ กรุณาทำช่องว่างแล้วลองใหม่อีกครั้ง");
            return;
        }
        if (target.exchange(itemID, -1, 2630437, quantity) > 0) {
            self.sayOk("แลกเปลี่ยน Arcane Symbol Selector Coupon " + quantity
                    + " ใบเรียบร้อยแล้ว กรุณาตรวจสอบช่องเก็บของ USE");
        } else {
            self.sayOk("ช่องเก็บของ USE ไม่เพียงพอ กรุณาทำช่องว่างแล้วลองใหม่อีกครั้ง");
        }
    }

    public void consume_2630437() {
        // Untradable Arcane Symbol Selector Coupon
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (getPlayer().getLevel() < 200) {
            self.sayOk("#fs11#เลเวล 200 ขึ้นไปเท่านั้นจึงจะสามารถใช้งานได้");
            return;
        }
        String changeSymbol = "#fs11#";
        if (getPlayer().getLevel() >= 200) { // Vanishing Journey
            changeSymbol += "#L5# #b#i1712001:# #t1712001:# #k#l\r\n";
        }
        if (getPlayer().getLevel() >= 210) { // Chu Chu Island
            changeSymbol += "#L4# #b#i1712002:# #t1712002:# #k#l\r\n";
        }
        if (getPlayer().getLevel() >= 220) { // Lachelein
            changeSymbol += "#L3# #b#i1712003:# #t1712003:# #k#l\r\n";
        }
        if (getPlayer().getLevel() >= 225) { // Arcana
            changeSymbol += "#L2# #b#i1712004:# #t1712004:# #k#l\r\n";
        }
        if (getPlayer().getLevel() >= 230) { // Morass
            changeSymbol += "#L1# #b#i1712005:# #t1712005:# #k#l\r\n";
        }
        if (getPlayer().getLevel() >= 235) { // Esfera
            changeSymbol += "#L0# #b#i1712006:# #t1712006:# #k#l";
        }

        int v = self.askMenu("#fs11#กรุณาเลือก #bArcane Symbol#k ที่ต้องการ!\r\n\r\n" + changeSymbol);
        int itemQty = getPlayer().getItemQuantity(itemID, false);
        String letter = "[Arcane Symbol Selector Coupon Cheat Attempt]\r\n Name : " + getPlayer().getName()
                + "\r\n Level : " + getPlayer().getLevel() + "\r\n Attempted Symbol : ";
        int number = self.askNumber("\r\nในกระเป๋ามี #b#i" + itemID + ":# #t" + itemID + ":##k จำนวน #b" + itemQty
                + " ชิ้น#k \r\nต้องการใช้งาน #b#eกี่ชิ้น?#n#k\r\n ", itemQty, 1, itemQty);
        int symbolID = 1712001;
        switch (v) {
            case 0: {
                symbolID = 1712006;
                if (getPlayer().getLevel() < 235) {
                    self.sayOk("#fs11#เลเวลไม่เพียงพอ ไม่สามารถแลกเปลี่ยนได้");
                    letter += "Esfera";
                    // DiscordBotHandler.requestSendTelegram(letter, -460314003);
                    return;
                }
                break;
            }
            case 1: {
                symbolID = 1712005;
                if (getPlayer().getLevel() < 230) {
                    self.sayOk("#fs11#เลเวลไม่เพียงพอ ไม่สามารถแลกเปลี่ยนได้");
                    letter += "Morass";
                    // DiscordBotHandler.requestSendTelegram(letter, -460314003);
                    return;
                }
                break;
            }
            case 2: {
                symbolID = 1712004;
                if (getPlayer().getLevel() < 225) {
                    self.sayOk("#fs11#เลเวลไม่เพียงพอ ไม่สามารถแลกเปลี่ยนได้");
                    letter += "Arcana";
                    // DiscordBotHandler.requestSendTelegram(letter, -460314003);
                    return;
                }
                break;
            }
            case 3: {
                symbolID = 1712003;
                if (getPlayer().getLevel() < 220) {
                    self.sayOk("#fs11#เลเวลไม่เพียงพอ ไม่สามารถแลกเปลี่ยนได้");
                    letter += "Lachelein";
                    // DiscordBotHandler.requestSendTelegram(letter, -460314003);
                    return;
                }
                break;
            }
            case 4: {
                symbolID = 1712002;
                if (getPlayer().getLevel() < 210) {
                    self.sayOk("#fs11#เลเวลไม่เพียงพอ ไม่สามารถแลกเปลี่ยนได้");
                    letter += "Chu Chu Island";
                    // DiscordBotHandler.requestSendTelegram(letter, -460314003);
                    return;
                }
                break;
            }
            case 5: {
                symbolID = 1712001;
                if (getPlayer().getLevel() < 200) {
                    self.sayOk("#fs11#เลเวลไม่เพียงพอ ไม่สามารถแลกเปลี่ยนได้");
                    letter += "Vanishing Journey";
                    // DiscordBotHandler.requestSendTelegram(letter, -460314003);
                    return;
                }
                break;
            }
        }
        if (number < 0) {
            return;
        }
        if (getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < number) {
            self.say("#fs11#กรุณาทำช่องว่างในช่องเก็บอุปกรณ์อย่างน้อย " + number + " ช่อง แล้วใช้งานใหม่อีกครั้ง");
        } else {
            if (1 == self.askYesNo(
                    "#fs11#ต้องการรับ #b#i" + symbolID + ":# #t" + symbolID + ":# " + number + " ชิ้น#k หรือไม่?")) {
                if (target.exchange(itemID, -number, symbolID, number) <= 0) {
                    self.sayOk("#fs11#กรุณาทำช่องว่างในช่องเก็บอุปกรณ์อย่างน้อย " + number
                            + " ช่อง แล้วใช้งานใหม่อีกครั้ง");
                } else {
                    self.sayOk("#fs11#\r\nได้รับ #b#t" + symbolID + ":# " + number
                            + " ชิ้น#k เรียบร้อยแล้ว!\r\n\r\n\r\n#fUI/UIWindow2.img/QuestIcon/4/0##b#e\r\n#i" + symbolID
                            + ":# #t" + symbolID + ":# " + number + " ชิ้น#n#k");
                }
            }
        }
    }

    public void consume_2630512() {
        // Untradable within Account Arcane Symbol Selector Coupon
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (getPlayer().getLevel() < 200) {
            self.sayOk("เลเวล 200 ขึ้นไปเท่านั้นจึงจะสามารถใช้งานได้");
            return;
        }
        String changeSymbol = "#fs11#";
        if (getPlayer().getLevel() >= 200) { // Vanishing Journey
            changeSymbol += "#L5# #b#i1712001:# #t1712001:# #k#l\r\n";
        }
        if (getPlayer().getLevel() >= 210) { // Chu Chu Island
            changeSymbol += "#L4# #b#i1712002:# #t1712002:# #k#l\r\n";
        }
        if (getPlayer().getLevel() >= 220) { // Lachelein
            changeSymbol += "#L3# #b#i1712003:# #t1712003:# #k#l\r\n";
        }
        if (getPlayer().getLevel() >= 225) { // Arcana
            changeSymbol += "#L2# #b#i1712004:# #t1712004:# #k#l\r\n";
        }
        if (getPlayer().getLevel() >= 230) { // Morass
            changeSymbol += "#L1# #b#i1712005:# #t1712005:# #k#l\r\n";
        }
        if (getPlayer().getLevel() >= 235) { // Esfera
            changeSymbol += "#L0# #b#i1712006:# #t1712006:# #k#l";
        }

        int v = self.askMenu("#fs11#กรุณาเลือก #bArcane Symbol#k ที่ต้องการ!\r\n\r\n" + changeSymbol);
        int itemQty = getPlayer().getItemQuantity(itemID, false);
        String letter = "[Arcane Symbol Selector Coupon Cheat Attempt]\r\n Name : " + getPlayer().getName()
                + "\r\n Level : " + getPlayer().getLevel() + "\r\n Attempted Symbol : ";
        int number = self.askNumber("\r\nในกระเป๋ามี #b#i" + itemID + ":# #t" + itemID + ":##k จำนวน #b" + itemQty
                + " ชิ้น#k \r\nต้องการใช้งาน #b#eกี่ชิ้น?#n#k\r\n ", itemQty, 1, itemQty);
        int symbolID = 1712001;
        switch (v) {
            case 0: {
                symbolID = 1712006;
                if (getPlayer().getLevel() < 235) {
                    self.sayOk("#fs11#เลเวลไม่เพียงพอ ไม่สามารถแลกเปลี่ยนได้");
                    letter += "Esfera";
                    // DiscordBotHandler.requestSendTelegram(letter, -460314003);
                    return;
                }
                break;
            }
            case 1: {
                symbolID = 1712005;
                if (getPlayer().getLevel() < 230) {
                    self.sayOk("#fs11#เลเวลไม่เพียงพอ ไม่สามารถแลกเปลี่ยนได้");
                    letter += "Morass";
                    // DiscordBotHandler.requestSendTelegram(letter, -460314003);
                    return;
                }
                break;
            }
            case 2: {
                symbolID = 1712004;
                if (getPlayer().getLevel() < 225) {
                    self.sayOk("#fs11#เลเวลไม่เพียงพอ ไม่สามารถแลกเปลี่ยนได้");
                    letter += "Arcana";
                    // DiscordBotHandler.requestSendTelegram(letter, -460314003);
                    return;
                }
                break;
            }
            case 3: {
                symbolID = 1712003;
                if (getPlayer().getLevel() < 220) {
                    self.sayOk("#fs11#เลเวลไม่เพียงพอ ไม่สามารถแลกเปลี่ยนได้");
                    letter += "Lachelein";
                    // DiscordBotHandler.requestSendTelegram(letter, -460314003);
                    return;
                }
                break;
            }
            case 4: {
                symbolID = 1712002;
                if (getPlayer().getLevel() < 210) {
                    self.sayOk("#fs11#เลเวลไม่เพียงพอ ไม่สามารถแลกเปลี่ยนได้");
                    letter += "Chu Chu Island";
                    // DiscordBotHandler.requestSendTelegram(letter, -460314003);
                    return;
                }
                break;
            }
            case 5: {
                symbolID = 1712001;
                if (getPlayer().getLevel() < 200) {
                    self.sayOk("#fs11#เลเวลไม่เพียงพอ ไม่สามารถแลกเปลี่ยนได้");
                    letter += "Vanishing Journey";
                    // DiscordBotHandler.requestSendTelegram(letter, -460314003);
                    return;
                }
                break;
            }
        }
        if (number < 0) {
            return;
        }
        if (getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < number) {
            self.say("#fs11#กรุณาทำช่องว่างในช่องเก็บอุปกรณ์อย่างน้อย " + number + " ช่อง แล้วใช้งานใหม่อีกครั้ง");
        } else {
            if (1 == self.askYesNo(
                    "#fs11#ต้องการรับ #b#i" + symbolID + ":# #t" + symbolID + ":# " + number + " ชิ้น#k หรือไม่?")) {
                if (target.exchange(itemID, -number, symbolID, number) <= 0) {
                    self.sayOk("#fs11#กรุณาทำช่องว่างในช่องเก็บอุปกรณ์อย่างน้อย " + number
                            + " ช่อง แล้วใช้งานใหม่อีกครั้ง");
                } else {
                    self.sayOk("#fs11#\r\nได้รับ #b#t" + symbolID + ":# " + number
                            + " ชิ้น#k เรียบร้อยแล้ว!\r\n\r\n\r\n#fUI/UIWindow2.img/QuestIcon/4/0##b#e\r\n#i" + symbolID
                            + ":# #t" + symbolID + ":# " + number + " ชิ้น#n#k");
                }
            }
        }
    }

    public void consume_2633336() {
        changeAuthenticSymbolVoucher(DBConfig.isGanglim ? 5 : 1);
    }

    public void consume_2633616() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (getPlayer().getLevel() < 260) {
            self.sayOk("#fs11#เลเวล 260 ขึ้นไปเท่านั้นจึงจะสามารถใช้งานได้");
            return;
        }
        String changeSymbol = "#fs11#";
        if (getPlayer().getLevel() >= 260) { // Cernium
            changeSymbol += "#L2# #b#i1713000:# #t1713000:# #k#l\r\n";
        }
        if (getPlayer().getLevel() >= 270) { // Hotel Arcs
            changeSymbol += "#L1# #b#i1713001:# #t1713001:# #k#l\r\n";
        }
        if (getPlayer().getLevel() >= 275) { // Odium
            changeSymbol += "#L3# #b#i1713002:# #t1713002:# #k#l\r\n";
        }
        if (getPlayer().getLevel() >= 280) { // Shangri-La
            changeSymbol += "#L4# #b#i1713003:# #t1713003:# #k#l\r\n";
        }
        if (getPlayer().getLevel() >= 280) { // Arteria
            changeSymbol += "#L5# #b#i1713004:# #t1713004:# #k#l";
        }
        int v = self.askMenu("#fs11#กรุณาเลือก #bAuthentic Symbol#k ที่ต้องการ!\r\n\r\n" + changeSymbol);
        int itemQty = getPlayer().getItemQuantity(itemID, false);
        String letter = "[Authentic Symbol Selector Coupon Cheat Attempt]\r\n Name : " + getPlayer().getName()
                + "\r\n Level : " + getPlayer().getLevel() + "\r\n Attempted Symbol : ";
        int number = self.askNumber("\r\nในกระเป๋ามี #b#i" + itemID + ":# #t" + itemID + ":##k จำนวน #b" + itemQty
                + " ชิ้น#k \r\n ใช้งาน #b#eกี่ชิ้น?#n#k\r\n ", itemQty, 1, itemQty);
        int symbolID = 1713000;
        switch (v) {
            case 1: {
                symbolID = 1713001;
                if (getPlayer().getLevel() < 270) {
                    self.sayOk("#fs11#เลเวลไม่เพียงพอ ไม่สามารถแลกเปลี่ยนได้");
                    letter += "Hotel Arcs";
                    // DiscordBotHandler.requestSendTelegram(letter, -460314003);
                    return;
                }
                break;
            }
            case 2: {
                symbolID = 1713000;
                if (getPlayer().getLevel() < 260) {
                    self.sayOk("#fs11#เลเวลไม่เพียงพอ ไม่สามารถแลกเปลี่ยนได้");
                    letter += "Cernium";
                    // DiscordBotHandler.requestSendTelegram(letter, -460314003);
                    return;
                }
                break;
            }
            case 3: {
                symbolID = 1713002;
                if (getPlayer().getLevel() < 275) {
                    self.sayOk("#fs11#เลเวลไม่เพียงพอ ไม่สามารถแลกเปลี่ยนได้");
                    letter += "Odium";
                    // DiscordBotHandler.requestSendTelegram(letter, -460314003);
                    return;
                }
                break;
            }
            case 4: {
                symbolID = 1713003;
                if (getPlayer().getLevel() < 280) {
                    self.sayOk("#fs11#เลเวลไม่เพียงพอ ไม่สามารถแลกเปลี่ยนได้");
                    letter += "Shangri-La";
                    // DiscordBotHandler.requestSendTelegram(letter, -460314003);
                    return;
                }
                break;
            }
            case 5: {
                symbolID = 1713004;
                if (getPlayer().getLevel() < 280) {
                    self.sayOk("#fs11#เลเวลไม่เพียงพอ ไม่สามารถแลกเปลี่ยนได้");
                    letter += "Arteria";
                    // DiscordBotHandler.requestSendTelegram(letter, -460314003);
                    return;
                }
                break;
            }
        }
        if (number < 0) {
            return;
        }
        if (getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < number) {
            self.say("#fs11#กรุณาทำช่องว่างในช่องเก็บอุปกรณ์อย่างน้อย " + number + " ช่อง แล้วใช้งานใหม่อีกครั้ง");
        } else {
            if (1 == self.askYesNo(
                    "#fs11#ต้องการรับ #b#i" + symbolID + ":# #t" + symbolID + ":# " + number + " ชิ้น#k หรือไม่?")) {
                if (target.exchange(itemID, -number, symbolID, number) <= 0) {
                    self.sayOk("#fs11#กรุณาทำช่องว่างในช่องเก็บอุปกรณ์อย่างน้อย " + number
                            + " ช่อง แล้วใช้งานใหม่อีกครั้ง");
                } else {
                    self.sayOk("#fs11#\r\nได้รับ #b#t" + symbolID + ":# " + number
                            + " ชิ้น#k เรียบร้อยแล้ว!\r\n\r\n\r\n#fUI/UIWindow2.img/QuestIcon/4/0##b#e\r\n#i" + symbolID
                            + ":# #t" + symbolID + ":# " + number + " ชิ้น#n#k");
                }
            }
        }
    }

    public void changeAuthenticSymbolVoucher(int quantity) {
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 1) {
            self.sayOk("ช่องเก็บของ USE ไม่เพียงพอ กรุณาทำช่องว่างแล้วลองใหม่อีกครั้ง");
            return;
        }
        if (target.exchange(itemID, -1, 2633616, quantity) > 0) {
            self.sayOk("แลกเปลี่ยน Authentic Symbol Selector Coupon " + quantity
                    + " ใบเรียบร้อยแล้ว กรุณาตรวจสอบช่องเก็บของ USE");
        } else {
            self.sayOk("ช่องเก็บของ USE ไม่เพียงพอ กรุณาทำช่องว่างแล้วลองใหม่อีกครั้ง");
        }
    }
}
