importPackage(java.lang);

var equipped = false;

var cantowner = ["△", "★", "★△", "★★", "★★△", "★★★"];

var canowner = ["1 Star", "2 Star", "3 Star", "4 Star", "5 Star", "6 Star", "7 Star", "8 Star", "9 Star", "10 Star", "11 Star", "12 Star", "13 Star", "14 Star", "15 Star", "16 Star", "17 Star", "18 Star", "19 Star", "20 Star"];

var banitem = [1712001, 1712002, 1712003, 1712004, 1712005, 1712006, 1713000, 1713001, 1713002, 1713003, 1713004, 1713005];

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

var items = [ //Level, Success Rate, Cost, All Stat, Att/Mag
    [0, 0, 0, 0, 0],
    [1, 100, 0, -5, -5],
    [2, 100, 0, -10, -10],
    [3, 100, 0, -15, -15],
    [4, 100, 0, -22, -20],
    [5, 100, 0, -29, -25],
    [6, 100, 0, -38, -30],
    [7, 100, 0, -49, -36],
    [8, 100, 0, -62, -45],
    [9, 100, 0, -77, -55],
    [10, 100, 0, -92, -70],
    [11, 100, 0, -112, -90],
    [12, 100, 0, -132, -110],
    [13, 100, 0, -162, -135],
    [14, 100, 0, -197, -165],
    [15, 100, 0, -232, -195],
    [16, 100, 0, -292, -225],
    [17, 100, 0, -392, -275],
    [18, 100, 0, -542, -375],
    [19, 100, 0, -792, -525],
    [20, 100, 0, -1092, -775],
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

var keep = 3000000000; //Protection Cost (Not really needed if 100% success?)
var item, itemid, slot, choice, say;
var re = 0;

function start() {
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
        var say = "#fs11##bPlease select equipment to reset.#k\r\n\r\n#rThis is the Meso Enhancement Reset System.#k \r\n#r#eEnhancement scrolls are NOT refunded upon reset.\r\n\r\n#r#e※ Note ※\r\nEquipped items are listed first.#n#k#b\r\n\r\n";

        for (i = 0; i <= 50; i++) { // Equipped
            item = cm.getInventory(-1).getItem(i * -1)
            if (item != null) {
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
                if (canowner.indexOf(item.getOwner()) != -1) {
                    if (!cm.isCash(item.getItemId())) {
                        say += "#L" + i + "##b#i" + item.getItemId() + "# #z" + item.getItemId() + "# #r[Slot " + i + "]#k#l\r\n";
                        count++;
                    }
                }
            }
        }

        if (count <= 0) {
            cm.sendOk("#fs11#No equipment available for reset.");
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
        if (item.getOwner().equals("")) {
            cm.sendOk("#fs11#This item is not Meso Enhanced.");
            cm.dispose();
            return;
        }
        itemid = item.getItemId();

        var notice = "";
        say = "";
        say += "#fs 11#";
        say += "#rReset #b: +" + getAddEnhance(item) + " -> +0#k\r\n";
        say += "On Reset: #bAll Stats " + (items[getAddEnhance(item)][3] > 0 ? "+" : "") + items[getAddEnhance(item)][3] + ", Att/Mag " + (items[getAddEnhance(item)][4] > 0 ? "+" : "") + items[getAddEnhance(item)][4] + "#k\r\n";
        say += "#r<Item Info>\r\n";
        say += "#fc0xFF9A9A9A#Item : #i" + itemid + "# #z" + itemid + "#\r\n";
        say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
        say += "Att : " + item.getWatk() + "  |  Mag : " + item.getMatk() + "  | StarForce : " + item.getEnhance() + "\r\n";
        say += "All Stat : " + item.getAllStat() + "%  |  Total Dmg : " + item.getTotalDamage() + "%  |  Boss Dmg : " + item.getBossDamage() + "%\r\n";

        cm.sendSimple(notice + say +
            "#L2#Reset it.#k#l");
    } else if (status == 2) {
        if (re == 0) {
            choice = selection;
        }
        if (item.getOwner().equals("")) {
            cm.sendOk("#fs11#This item is not Meso Enhanced.");
            cm.dispose();
            return;
        }

        say = "";
        say += "#fs 11#";
        say += "#rReset #b: +" + getAddEnhance(item) + " -> +0#k\r\n";
        say += "On Reset: #bAll Stats " + items[getAddEnhance(item)][3] + ", Att/Mag " + items[getAddEnhance(item)][4] + "#k\r\n";
        say += "Reset Cost : #b" + items[getAddEnhance(item)][2] + " Meso#k\r\n";
        say += "#bSuccess Rate 100%#k";
        if (selection == 1 || choice == 1) {
            say += "\r\n";
        } else if (selection == 2 || choice == 2) {
            say += "\r\n";
        }
        say += "#r<Item Info>\r\n";
        say += "#fc0xFF9A9A9A#Item : #i" + itemid + "# #z" + itemid + "#\r\n";
        say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
        say += "Att : " + item.getWatk() + "  |  Mag : " + item.getMatk() + "  | StarForce : " + item.getEnhance() + "\r\n";
        say += "All Stat : " + item.getAllStat() + "%  |  Total Dmg : " + item.getTotalDamage() + "%  |  Boss Dmg : " + item.getBossDamage() + "%\r\n";

        if (selection == 1 || choice == 1) {
            cm.sendYesNo(say + "Do you really want to use " + ConvertNumber(keep) + " Meso to prevent slipping?\r\n" +
                "Prevention cost is consumed regardless of success/failure.\r\n\r\n#bTotal Required : " + ConvertNumber(items[getAddEnhance(item) + 1][2] + keep) + " Meso#k#l");
        } else if (selection == 2 || choice == 2) {
            cm.sendYesNo(say + "Do you really want to reset?");
        }
    } else if (status == 3) {
        if (choice == 1) {
            if (cm.getPlayer().getMeso() >= 0) {
                cm.gainMeso(-(items[getAddEnhance(item) + 1][2] + keep));
            } else {
                cm.sendOk("#fs11#Not enough Meso for protected reset.\r\nTotal Required : " + ConvertNumber(items[getAddEnhance(item) + 1][2] + keep) + " Meso");
                cm.dispose();
                return;
            }
        } else if (choice == 2) {
            if (cm.getPlayer().getMeso() >= 0) {
                //cm.gainMeso(0);
            } else {
                cm.sendOk("#fs11#Not enough Meso.");
                cm.dispose();
                return;
            }
        } else {
            cm.dispose();
            return;
        }
        if (item != null) {
            var rand = Math.ceil(Math.random() * 100);
            if (rand >= 0 && rand <= items[getAddEnhance(item)][1]) {
                item.setStr(item.getStr() + items[getAddEnhance(item)][3]);
                item.setDex(item.getDex() + items[getAddEnhance(item)][3]);
                item.setInt(item.getInt() + items[getAddEnhance(item)][3]);
                item.setLuk(item.getLuk() + items[getAddEnhance(item)][3]);
                item.setWatk(item.getWatk() + items[getAddEnhance(item)][4]);
                item.setMatk(item.getMatk() + items[getAddEnhance(item)][4]);
                item.setOwner("");
                cm.getPlayer().send(Packages.network.models.CWvsContext.InventoryPacket.updateInventoryItem(Packages.objects.item.MapleInventoryType.EQUIP, item, false, cm.getPlayer()));
                say = "";
                say += "#fs11##r<Item Info>\r\n";
                say += "#fc0xFF9A9A9A#Item : #i" + itemid + "# #z" + itemid + "#\r\n";
                say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
                say += "Att : " + item.getWatk() + "  |  Mag : " + item.getMatk() + "  | StarForce : " + item.getEnhance() + "\r\n";
                say += "All Stat : " + item.getAllStat() + "%  |  Total Dmg : " + item.getTotalDamage() + "%  |  Boss Dmg : " + item.getBossDamage() + "%\r\n";
                say += "Enhance Count : " + getAddEnhance(item) + "#k\r\n\r\n\r\n";
                cm.sendOk("#fs11#Reset Successful.");
                cm.addEnchantLog(1, item.getItemId(), item.getSerialNumberEquip(), 11, 0, "Meso Enhancement Reset Success (Acc : " + cm.getClient().getAccountName() + ", Char : " + cm.getPlayer().getName() + ", Item [" + item.toString() + "])");
            } else {
                if (choice == 1 || getAddEnhance(item) == 0) {
                    say = "";
                    say += "#fs11##r<Item Info>\r\n";
                    say += "#fc0xFF9A9A9A#Item : #i" + itemid + "# #z" + itemid + "#\r\n";
                    say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
                    say += "Att : " + item.getWatk() + "  |  Mag : " + item.getMatk() + "  | StarForce : " + item.getEnhance() + "\r\n";
                    say += "All Stat : " + item.getAllStat() + "%  |  Total Dmg : " + item.getTotalDamage() + "%  |  Boss Dmg : " + item.getBossDamage() + "%\r\n";
                    say += "Enhance Count : " + getAddEnhance(item) + "#k\r\n\r\n\r\n";
                    cm.sendYesNo("#rReset Failed#k but #rRank was protected#k by using " + ConvertNumber(keep) + " Meso.\r\nPress 'Yes' to continue.\r\n\r\n" + say);
                    cm.addEnchantLog(1, item.getItemId(), item.getSerialNumberEquip(), 11, 1, "Meso Enhancement Reset Failed Protected (Acc : " + cm.getClient().getAccountName() + ", Char : " + cm.getPlayer().getName() + ", Item [" + item.toString() + "])");

                    re = 1;
                    status = 1;
                } else if (choice == 2) {
                    item.setStr(item.getStr() - items[getAddEnhance(item)][3]);
                    item.setDex(item.getDex() - items[getAddEnhance(item)][3]);
                    item.setInt(item.getInt() - items[getAddEnhance(item)][3]);
                    item.setLuk(item.getLuk() - items[getAddEnhance(item)][3]);
                    item.setWatk(item.getWatk() - items[getAddEnhance(item)][4]);
                    item.setMatk(item.getMatk() - items[getAddEnhance(item)][4]);
                    if (getAddEnhance(item) > 1 && getAddEnhance(item) < 15) {
                        item.setOwner("" + (getAddEnhance(item) - 1) + " Star");
                    } else if (getAddEnhance(item) == 1) {
                        item.setOwner("");
                    }
                    cm.getPlayer().send(Packages.network.models.CWvsContext.InventoryPacket.updateInventoryItem(Packages.objects.item.MapleInventoryType.EQUIP, item, false, cm.getPlayer()));
                    say = "";
                    say += "#r<Item Info>\r\n";
                    say += "#fc0xFF9A9A9A#Item : #i" + itemid + "# #z" + itemid + "#\r\n";
                    say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
                    say += "Att : " + item.getWatk() + "  |  Mag : " + item.getMatk() + "  | StarForce : " + item.getEnhance() + "\r\n";
                    say += "All Stat : " + item.getAllStat() + "%  |  Total Dmg : " + item.getTotalDamage() + "%  |  Boss Dmg : " + item.getBossDamage() + "%\r\n";
                    say += "Enhance Count : " + getAddEnhance(item) + "#k\r\n\r\n\r\n";
                    cm.sendYesNo("#rReset Failed#k and #rRank Dropped#k.\r\nPress 'Yes' to continue.\r\n\r\n" + say);
                    cm.addEnchantLog(1, item.getItemId(), item.getSerialNumberEquip(), 11, 1, "Meso Enhancement Reset Failed (Acc : " + cm.getClient().getAccountName() + ", Char : " + cm.getPlayer().getName() + ", Item [" + item.toString() + "])");

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