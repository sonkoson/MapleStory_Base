importPackage(java.lang);

var equipped = false;

var banitem = [1712001, 1712002, 1712003, 1712004, 1712005, 1712006, 1713000, 1713001, 1713002, 1713003, 1713004, 1713005];

var canowner = ["", "1 Star", "2 Star", "3 Star", "4 Star", "5 Star", "6 Star", "7 Star", "8 Star", "9 Star", "10 Star", "11 Star", "12 Star", "13 Star", "14 Star", "15 Star", "16 Star", "17 Star", "18 Star", "19 Star", "20 Star"];

var cantowner = ["△", "★", "★△", "★★", "★★△", "★★★"];

function ConvertNumber(number) {
    var inputNumber = number < 0 ? false : number;
    var unitWords = ['', 'K ', 'M ', 'B ', 'T ', 'Q '];
    var splitUnit = 1000;
    var splitCount = unitWords.length;
    var resultArray = [];
    var resultString = '';
    if (inputNumber == false) {
        cm.sendOk("#fs11#Error occurred. Please try again.\r\n(Parsing Error)");
        cm.dispose();
        return;
    }
    for (var i = 0; i < splitCount; i++) {
        var unitResult = (inputNumber % Math.pow(splitUnit, i + 1)) / Math.pow(splitUnit, i);
        unitResult = Math.floor(unitResult);
        if (unitResult > 0) {
            resultArray[i] = unitResult;
        }
    }
    for (var i = 0; i < resultArray.length; i++) {
        if (!resultArray[i]) continue;
        resultString = String(resultArray[i]) + unitWords[i] + resultString;
    }
    return resultString;
}

var status = -1;

var items = [ //Enhance Count, Success Rate, Cost (Meso or Pouch), AllStat, Att/Mag
    [0, 0, 0, 0, 0],
    [1, 100, 15000000, 5, 5],
    [2, 100, 15000000, 5, 5],
    [3, 90, 29500000, 5, 5],
    [4, 90, 37000000, 7, 5],
    [5, 80, 45000000, 7, 5],
    [6, 60, 1, 9, 5],
    [7, 50, 2, 11, 6],
    [8, 40, 3, 13, 9],
    [9, 30, 3, 15, 10],
    [10, 30, 5, 15, 15],
    [11, 25, 7, 20, 20],
    [12, 20, 9, 20, 20],
    [13, 20, 11, 30, 25],
    [14, 15, 15, 35, 30],
    [15, 9, 17, 35, 30],
    [16, 7, 19, 60, 30],
    [17, 5, 25, 100, 50],
    [18, 5, 30, 150, 100],
    [19, 3, 40, 250, 150],
    [20, 2, 50, 300, 250],
];

function getAddEnhance(item) {
    var owner = item.getOwner();
    return owner == "1 Star" ? 1 :
        owner == "2 Star" ? 2 :
            owner == "3 Star" ? 3 :
                owner == "4 Star" ? 4 :
                    owner == "5 Star" ? 5 :
                        owner == "6 Star" ? 6 :
                            owner == "7 Star" ? 7 :
                                owner == "8 Star" ? 8 :
                                    owner == "9 Star" ? 9 :
                                        owner == "10 Star" ? 10 :
                                            owner == "11 Star" ? 11 :
                                                owner == "12 Star" ? 12 :
                                                    owner == "13 Star" ? 13 :
                                                        owner == "14 Star" ? 14 :
                                                            owner == "15 Star" ? 15 :
                                                                owner == "16 Star" ? 16 :
                                                                    owner == "17 Star" ? 17 :
                                                                        owner == "18 Star" ? 18 :
                                                                            owner == "19 Star" ? 19 :
                                                                                owner == "20 Star" ? 20 :
                                                                                    0;
}

var keep = 300000000; //Prevention Cost (Meso)
var keep2 = 3; //Prevention Cost (Pouch)
var item, itemid, slot, choice, say;
var re = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode != 1) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
        var count = 0;
        var say = "#fs11##bPlease select the equipment to enhance.#k\r\n\r\n#rThis is a separate enhancement system from Star Force.#k\r\n#r#eDo not enhance Symbols as stats will not apply.#n#k\r\n\r\n※ Normal enhancement up to +20.\r\nTranscendence enhancement available from +20.\r\n\r\n#r#e※ Note ※\r\nEquipped items are listed first.#n#k#b\r\n\r\n";
        for (i = 0; i <= 50; i++) { // Equipped
            item = cm.getInventory(-1).getItem(i * -1)
            if (item != null) {
                // Only enhanceable items
                if (canowner.indexOf(item.getOwner()) != -1) {
                    if (!cm.isCash(item.getItemId())) {
                        say += "#L" + (i + 100000) + "##b#i" + item.getItemId() + "# #z" + item.getItemId() + "# #r#e[Equipped]#k#n#l\r\n";
                        count++;
                    }
                }
            }
        }

        for (i = 0; i <= cm.getInventory(1).getSlotLimit(); i++) {
            item = cm.getInventory(1).getItem(i)
            if (item != null) {
                // Only enhanceable items
                if (canowner.indexOf(item.getOwner()) != -1) {
                    if (!cm.isCash(item.getItemId())) {
                        say += "#L" + i + "##b#i" + item.getItemId() + "# #z" + item.getItemId() + "# #r[Slot " + i + "]#k#l\r\n";
                        count++;
                    }
                }
            }
        }
        if (count <= 0) {
            cm.sendOk("#fs11#There is no equipment to enhance.");
            cm.dispose();
            return;
        }
        cm.sendSimple(say);
    } else if (status == 1) {
        if (re == 0) {
            slot = selection;
            if (slot > 10000) { // Equipped
                slot = -(slot - 100000)
                equipped = true;
            }
            if (equipped) { // Equipped
                item = cm.getInventory(-1).getItem(slot);
            } else {
                item = cm.getInventory(1).getItem(slot);
            }
        }

        if (item.getOwner().equals("20 Star")) {
            cm.sendOk("#fs11#Item is already fully enhanced to +20.");
            cm.dispose();
            return;
        }
        if (cantowner.indexOf(item.getOwner()) != -1) {
            cm.sendOk("#fs11#This item has already undergone Transcendence Enhancement.");
            cm.dispose();
            return;
        }
        if (item.getOwner().equals("[Promotion]")) {
            cm.sendOk("#fs11#Promotion items cannot be enhanced.");
            cm.dispose();
            return;
        }

        itemid = item.getItemId();

        if (banitem.indexOf(itemid) != -1) {
            cm.sendOk("#fs11#Symbol items cannot be enhanced.");
            cm.dispose();
            return;
        }

        //Stats
        var notice = "";
        say = "";
        say += "#fs 11#";
        say += "#rEnhance : +" + getAddEnhance(item) + " -> +" + (getAddEnhance(item) + 1) + "#k\r\n";
        say += "On Success: #bAll Stats +" + items[getAddEnhance(item) + 1][3] + ", Att/Mag +" + items[getAddEnhance(item) + 1][4] + "#k increase\r\n";
        if (items[getAddEnhance(item) + 1][2] > 100) // Meso
            say += "Meso Required :#b " + ConvertNumber(items[getAddEnhance(item) + 1][2]) + " Meso#k\r\n";
        if (items[getAddEnhance(item) + 1][2] < 100)  // Pouch
            say += "Requirement :#b #z4001715# " + items[getAddEnhance(item) + 1][2] + " pcs#k\r\n";
        say += "#bSuccess Rate " + items[getAddEnhance(item) + 1][1] + "%#k, #rRank drops on failure#k\r\n\r\n";
        say += "#r<Item Info>\r\n";
        say += "#fc0xFF9A9A9A##i" + itemid + "# #z" + itemid + "#\r\n";
        say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
        say += "Att : " + item.getWatk() + "  |  Mag : " + item.getMatk() + "  | StarForce : " + item.getEnhance() + "\r\n";
        cm.sendSimple(notice + say +
            "#L1##bUse " + ConvertNumber(keep) + " Meso to protect rank drop.#l\r\n" +
            "#L2#Enhance without rank drop protection.#k#l");
    } else if (status == 2) {
        if (re == 0) {
            choice = selection;
        }
        if (item.getOwner().equals("20 Star")) {
            cm.sendOk("#fs11#Item is already fully enhanced to +20.");
            cm.dispose();
            return;
        }
        //Stats
        say = "";
        say += "#fs 11#";
        say += "Enhance : #b+" + getAddEnhance(item) + " -> +" + (getAddEnhance(item) + 1) + "#k\r\n";
        say += "On Success: #bAll Stats +" + items[getAddEnhance(item) + 1][3] + ", Att/Mag +" + items[getAddEnhance(item) + 1][4] + "#k increase\r\n";
        if (items[getAddEnhance(item) + 1][2] > 100) // Meso
            say += "Meso Required :#b " + ConvertNumber(items[getAddEnhance(item) + 1][2]) + " Meso#k\r\n";
        if (items[getAddEnhance(item) + 1][2] < 100) // Pouch
            say += "Requirement :#b #z4001715# " + items[getAddEnhance(item) + 1][2] + " pcs#k\r\n";
        say += "#bSuccess Rate " + items[getAddEnhance(item) + 1][1] + "%#k";
        if (selection == 1 || choice == 1) {
            say += ", #bRank Drop Protected#k\r\n\r\n";
        } else if (selection == 2 || choice == 2) {
            say += ", #rRank drops on failure#k\r\n\r\n";
        }
        say += "#r<Item Info>\r\n";
        say += "#fc0xFF9A9A9A##i" + itemid + "# #z" + itemid + "#\r\n";
        say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
        say += "Att : " + item.getWatk() + "  |  Mag : " + item.getMatk() + "  | StarForce : " + item.getEnhance() + "\r\n";
        if (selection == 1 || choice == 1) {
            if (items[getAddEnhance(item) + 1][2] > 100) // Meso
                cm.sendYesNo(say + "\r\nDo you really want to use " + ConvertNumber(keep) + " Meso to protect rank drop?\r\n" + "Prevention cost is consumed regardless of success/failure.");
            if (items[getAddEnhance(item) + 1][2] < 100) // Pouch
                cm.sendYesNo(say + "\r\nDo you really want to use " + ConvertNumber(keep) + " Meso to protect rank drop?\r\n" + "Prevention cost is consumed regardless of success/failure.");
        } else if (selection == 2 || choice == 2) {
            cm.sendYesNo(say + "\r\nDo you really want to attempt enhancement without using " + ConvertNumber(keep) + " Meso protection?\r\n" + "Rank will drop if enhancement fails.");
        }
    } else if (status == 3) {
        if (choice == 1) {
            if (items[getAddEnhance(item) + 1][2] > 100) { // Meso
                if (cm.getPlayer().getMeso() >= (items[getAddEnhance(item) + 1][2] + keep)) {
                    cm.gainMeso(-(items[getAddEnhance(item) + 1][2] + keep));
                } else {
                    cm.sendOk("#fs11#Not enough Meso for protected enhancement.\r\nTotal Required : " + ConvertNumber(items[getAddEnhance(item) + 1][2] + keep) + " Meso");
                    cm.dispose();
                    return;
                }
            } else { // Pouch
                if (cm.haveItem(4001715, items[getAddEnhance(item) + 1][2] + keep2)) {
                    cm.gainItem(4001715, -(items[getAddEnhance(item) + 1][2] + keep2));
                } else {
                    cm.sendOk("#fs11#Not enough Pouch/Meso for protected enhancement.\r\nTotal Required : #z4001715# " + (items[getAddEnhance(item) + 1][2] + keep2) + " pcs");
                    cm.dispose();
                    return;
                }
            }
        } else if (choice == 2) {
            if (items[getAddEnhance(item) + 1][2] > 100) { // Meso
                if (cm.getPlayer().getMeso() >= items[getAddEnhance(item) + 1][2]) {
                    cm.gainMeso(-items[getAddEnhance(item) + 1][2]);
                } else {
                    cm.sendOk("#fs11#Not enough Meso.");
                    cm.dispose();
                    return;
                }
            } else { // Pouch
                if (cm.haveItem(4001715, items[getAddEnhance(item) + 1][2])) {
                    cm.gainItem(4001715, -items[getAddEnhance(item) + 1][2]);
                } else {
                    cm.sendOk("#fs11#Not enough Pouch.");
                    cm.dispose();
                    return;
                }
            }
        } else {
            cm.dispose();
            return;
        }
        if (item != null) {
            var rand = Math.ceil(Math.random() * 100);
            if (rand >= 0 && rand <= items[getAddEnhance(item) + 1][1]) {
                item.addStr(items[getAddEnhance(item) + 1][3]);
                item.addDex(items[getAddEnhance(item) + 1][3]);
                item.addInt(items[getAddEnhance(item) + 1][3]);
                item.addLuk(items[getAddEnhance(item) + 1][3]);
                item.addWatk(items[getAddEnhance(item) + 1][4]);
                item.addMatk(items[getAddEnhance(item) + 1][4]);
                item.setOwner("" + (getAddEnhance(item) + 1) + " Star");
                cm.getPlayer().send(Packages.network.models.CWvsContext.InventoryPacket.updateInventoryItem(Packages.objects.item.MapleInventoryType.EQUIP, item, false, cm.getPlayer()));

                say = "#fs11#";
                say += "#r<Item Info>\r\n";
                say += "#fc0xFF9A9A9A##i" + itemid + "# #z" + itemid + "#\r\n";
                say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
                say += "Att : " + item.getWatk() + "  |  Mag : " + item.getMatk() + "  | StarForce : " + item.getEnhance() + "\r\n";
                cm.sendYesNo("#fs11##bEnhancement Successful#k.\r\nPress 'Yes' to continue enhancing.\r\n\r\n" + say);

                cm.addEnchantLog(1, item.getItemId(), item.getSerialNumberEquip(), 10, 0, "Integrated Meso Enhancement " + (getAddEnhance(item) - 1) + "->" + getAddEnhance(item) + " Success (Acc : " + cm.getClient().getAccountName() + ", Char : " + cm.getPlayer().getName() + ", " + itemid + " [" + item.toString() + "])");

                re = 1;
                status = 1;
            } else {
                if (choice == 1 || getAddEnhance(item) == 0) {
                    say = "#fs11#";
                    say += "#r<Item Info>\r\n";
                    say += "#fc0xFF9A9A9A##i" + itemid + "# #z" + itemid + "#\r\n";
                    say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
                    say += "Att : " + item.getWatk() + "  |  Mag : " + item.getMatk() + "  | StarForce : " + item.getEnhance() + "\r\n";
                    cm.sendYesNo("#fs11##rEnhancement failed#k but using " + ConvertNumber(keep) + " Meso protected the #rrank from dropping#k.\r\nPress 'Yes' to continue enhancing.\r\n\r\n" + say);

                    cm.addEnchantLog(1, item.getItemId(), item.getSerialNumberEquip(), 10, 1, "Integrated Meso Enhancement " + getAddEnhance(item) + "->" + (getAddEnhance(item) + 1) + " Failure Protected (Acc : " + cm.getClient().getAccountName() + ", Char : " + cm.getPlayer().getName() + ", " + itemid + " [" + item.toString() + "])");

                    re = 1;
                    status = 1;
                } else if (choice == 2) {
                    item.setStr(item.getStr() - items[getAddEnhance(item)][3]);
                    item.setDex(item.getDex() - items[getAddEnhance(item)][3]);
                    item.setInt(item.getInt() - items[getAddEnhance(item)][3]);
                    item.setLuk(item.getLuk() - items[getAddEnhance(item)][3]);
                    item.setWatk(item.getWatk() - items[getAddEnhance(item)][4]);
                    item.setMatk(item.getMatk() - items[getAddEnhance(item)][4]);

                    item.setOwner("" + (getAddEnhance(item) - 1) + " Star");

                    cm.getPlayer().send(Packages.network.models.CWvsContext.InventoryPacket.updateInventoryItem(Packages.objects.item.MapleInventoryType.EQUIP, item, false, cm.getPlayer()));

                    say = "#fs11#";
                    say += "#r<Item Info>\r\n";
                    say += "#fc0xFF9A9A9A##i" + itemid + "# #z" + itemid + "#\r\n";
                    say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
                    say += "Att : " + item.getWatk() + "  |  Mag : " + item.getMatk() + "  | StarForce : " + item.getEnhance() + "\r\n";
                    cm.sendYesNo("#fs11##rEnhancement failed#k and #rrank dropped#k.\r\nPress 'Yes' to continue enhancing.\r\n\r\n" + say);

                    cm.addEnchantLog(1, item.getItemId(), item.getSerialNumberEquip(), 10, 1, "Integrated Meso Enhancement " + (getAddEnhance(item) - 1) + "->" + getAddEnhance(item) + " Failure Drop (Acc : " + cm.getClient().getAccountName() + ", Char : " + cm.getPlayer().getName() + ", " + itemid + " [" + item.toString() + "])");

                    re = 1;
                    status = 1;
                } else {
                    cm.dispose();
                    return;
                }
            }
        } else {
            cm.dispose();
            return;
        }
    } else {
        cm.dispose();
        return;
    }
}