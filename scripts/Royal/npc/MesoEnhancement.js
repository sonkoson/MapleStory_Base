importPackage(java.lang);

var banitem = [1712001, 1712002, 1712003, 1712004, 1712005, 1712006, 1713000, 1713001, 1713002, 1713003, 1713004, 1713005];

function ConvertNumber(number) {
    var inputNumber = number < 0 ? false : number;
    var unitWords = ['', '0,000 ', '00 Million ', 'Trillion ', 'Quad '];
    var splitUnit = 10000;
    var splitCount = unitWords.length;
    var resultArray = [];
    var resultString = '';
    if (inputNumber == false) {
        cm.sendOk("Error occurred. Please try again.\r\n(Parsing Error)");
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

var items = [ //Enhancement Level, Success Rate, Cost, All Stat, Att/Matk
    [0, 0, 0, 0, 0],
    [1, 95, 15000000, 5, 5],
    [2, 95, 15000000, 5, 5],
    [3, 90, 29500000, 5, 5],
    [4, 90, 37000000, 7, 5],
    [5, 80, 45000000, 7, 5],
    [6, 60, 120000000, 9, 5],
    [7, 50, 200000000, 11, 6],
    [8, 40, 250000000, 13, 9],
    [9, 30, 270000000, 15, 10],
    [10, 30, 550000000, 15, 15],
    [11, 25, 700000000, 20, 20],
    [12, 20, 900000000, 20, 20],
    [13, 20, 1100000000, 30, 25],
    [14, 15, 1500000000, 35, 30],
    [15, 9, 1700000000, 35, 30],
    [16, 7, 1900000000, 60, 30],
    [17, 5, 2100000000, 100, 50],
    [18, 5, 2400000000, 150, 100],
    [19, 3, 2700000000, 250, 150],
    [20, 2, 3000000000, 300, 250],
];

function getAddEnhance(item) {
    var owner = item.getOwner();
    return owner == "1강" ? 1 :
        owner == "2강" ? 2 :
            owner == "3강" ? 3 :
                owner == "4강" ? 4 :
                    owner == "5강" ? 5 :
                        owner == "6강" ? 6 :
                            owner == "7강" ? 7 :
                                owner == "8강" ? 8 :
                                    owner == "9강" ? 9 :
                                        owner == "10강" ? 10 :
                                            owner == "11강" ? 11 :
                                                owner == "12강" ? 12 :
                                                    owner == "13강" ? 13 :
                                                        owner == "14강" ? 14 :
                                                            owner == "15강" ? 15 :
                                                                owner == "16강" ? 16 :
                                                                    owner == "17강" ? 17 :
                                                                        owner == "18강" ? 18 :
                                                                            owner == "19강" ? 19 :
                                                                                owner == "20강" ? 20 :
                                                                                    0;
}

var keep = 3000000000; // Protection Cost
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
        var say = "#fs11#Please select the #bequipment to enhance#k.\r\n\r\n#rThis is a separate enhancement system from Star Force.#k \r\n#r#eDo not enhance Symbols as stats will not apply.#n#k\r\n\r\n";
        for (i = 0; i <= cm.getInventory(1).getSlotLimit(); i++) {
            if (cm.getInventory(1).getItem(i) != null) {
                if (!Packages.objects.item.MapleItemInformationProvider.getInstance().isCash(cm.getInventory(1).getItem(i).getItemId())) {
                    say += "#L" + i + "##e#b#i" + cm.getInventory(1).getItem(i).getItemId() + "# #z" + cm.getInventory(1).getItem(i).getItemId() + "# #r[" + i + "Slot]#k#l\r\n";
                    count++;
                }
            }
        }
        if (count <= 0) {
            cm.sendOk("Please check if you have equipment to enhance.");
            cm.dispose();
            return;
        }
        cm.sendSimple(say);
    } else if (status == 1) {
        if (re == 0) {
            slot = selection;
            item = cm.getInventory(1).getItem(selection);
        }
        if (item.getOwner().equals("20강")) {
            cm.sendOk("This item is already enhanced to +20.");
            cm.dispose();
            return;
        }
        if (item.getOwner().equals("[홍보]")) { // [Promotion]
            cm.sendOk("Promotion items cannot be enhanced.");
            cm.dispose();
            return;
        }

        itemid = item.getItemId();
        if (banitem.indexOf(itemid) != -1) {
            cm.sendOk("#fs11#Symbol items cannot be enhanced.");
            cm.dispose();
            return;
        }

        //Enhancement Level, Success Rate, Cost, All Stat, Att/Matk
        var notice = "";
        say = "";
        say += "#fs 11#";
        say += "#rEnhance : +" + getAddEnhance(item) + " -> +" + (getAddEnhance(item) + 1) + "#k\r\n";
        say += "On success, #bAll Stats +" + items[getAddEnhance(item) + 1][3] + ", Att/Matk +" + items[getAddEnhance(item) + 1][4] + "#k increase\r\n";
        say += "Mesos required for enhancement :#b " + items[getAddEnhance(item) + 1][2] + " Mesos#k\r\n";
        say += "#bSuccess Rate " + items[getAddEnhance(item) + 1][1] + "%#k, Item stats decrease on failure\r\n";
        say += "However, you can protect item stats by spending #b" + ConvertNumber(keep) + "Mesos#k.\r\n\r\n\r\n";
        say += "#r<Item Info>\r\n";
        say += "Item to Enhance : #i" + itemid + "# #z" + itemid + "#\r\n";
        say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
        say += "Attack : " + item.getWatk() + "  |  Magic Attack : " + item.getMatk() + "  | Star Force : " + item.getEnhance() + "Star\r\n";
        say += "All Stats : " + item.getAllStat() + "%  |  Total Damage : " + item.getTotalDamage() + "%  |  Boss Damage : " + item.getBossDamage() + "%\r\n";
        say += "Item Enhancement Level : " + getAddEnhance(item) + "#k\r\n\r\n\r\n";
        cm.sendSimple(notice + say +
            "#L1##bUse " + ConvertNumber(keep) + "Mesos to prevent destruction/degradation.#l\r\n" +
            "#L2#Enhance without creating a prevent destruction/degradation.#k#l");
    } else if (status == 2) {
        if (re == 0) {
            choice = selection;
        }
        if (item.getOwner().equals("20강")) {
            cm.sendOk("This item is already enhanced to +20.");
            cm.dispose();
            return;
        }
        //Enhancement Level, Success Rate, Cost, All Stat, Att/Matk
        say = "";
        say += "#fs 11#";
        say += "Enhance : #b+" + getAddEnhance(item) + " -> +" + (getAddEnhance(item) + 1) + "#k\r\n";
        say += "On success, #bAll Stats +" + items[getAddEnhance(item) + 1][3] + ", Att/Matk +" + items[getAddEnhance(item) + 1][4] + "#k increase\r\n";
        say += "Mesos required for enhancement : #b" + items[getAddEnhance(item) + 1][2] + "Mesos#k\r\n";
        say += "#bSuccess Rate " + items[getAddEnhance(item) + 1][1] + "%#k";
        if (selection == 1 || choice == 1) {
            say += "\r\n";
        } else if (selection == 2 || choice == 2) {
            say += ", Item stats decrease on failure\r\n\r\n";
        }
        say += "#r<Item Info>\r\n";
        say += "Item to Enhance : #i" + itemid + "# #z" + itemid + "#\r\n";
        say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
        say += "Attack : " + item.getWatk() + "  |  Magic Attack : " + item.getMatk() + "  | Star Force : " + item.getEnhance() + "Star\r\n";
        say += "All Stats : " + item.getAllStat() + "%  |  Total Damage : " + item.getTotalDamage() + "%  |  Boss Damage : " + item.getBossDamage() + "%\r\n";
        say += "Item Enhancement Level : " + getAddEnhance(item) + "#k\r\n\r\n\r\n";
        if (selection == 1 || choice == 1) {
            cm.sendYesNo(say + "Do you really want to spend " + ConvertNumber(keep) + "Mesos to prevent degradation?\r\n" +
                "Protection Mesos are consumed regardless of success/failure.\r\n\r\n#bTotal Mesos Required : " + ConvertNumber(items[getAddEnhance(item) + 1][2] + keep) + "Mesos#k#l");
        } else if (selection == 2 || choice == 2) {
            cm.sendYesNo(say + "Do you really want to attempt enhancement without spending " + ConvertNumber(keep) + "Mesos?\r\n" +
                "Enhancement level will drop upon failure.");
        }
    } else if (status == 3) {
        if (choice == 1) {
            if (cm.getPlayer().getMeso() >= (items[getAddEnhance(item) + 1][2] + keep)) {
                cm.gainMeso(-(items[getAddEnhance(item) + 1][2] + keep));
            } else {
                cm.sendOk("Insufficient Mesos for protected enhancement.\r\nTotal Required Mesos : " + ConvertNumber(items[getAddEnhance(item) + 1][2] + keep) + "Mesos");
                cm.dispose();
                return;
            }
        } else if (choice == 2) {
            if (cm.getPlayer().getMeso() >= items[getAddEnhance(item) + 1][2]) {
                cm.gainMeso(-items[getAddEnhance(item) + 1][2]);
            } else {
                cm.sendOk("Insufficient Mesos for enhancement.");
                cm.dispose();
                return;
            }
        } else {
            cm.dispose();
            return;
        }
        if (cm.getInventory(1).getItem(slot) != null) {
            var rand = Math.ceil(Math.random() * 100);
            if (rand >= 0 && rand <= items[getAddEnhance(item) + 1][1]) {
                item.addStr(items[getAddEnhance(item) + 1][3]);
                item.addDex(items[getAddEnhance(item) + 1][3]);
                item.addInt(items[getAddEnhance(item) + 1][3]);
                item.addLuk(items[getAddEnhance(item) + 1][3]);
                item.addWatk(items[getAddEnhance(item) + 1][4]);
                item.addMatk(items[getAddEnhance(item) + 1][4]);
                item.setOwner("" + (getAddEnhance(item) + 1) + "강");
                cm.getPlayer().send(Packages.network.models.CWvsContext.InventoryPacket.updateInventoryItem(Packages.objects.item.MapleInventoryType.EQUIP, item, false, cm.getPlayer()));

                say = "";
                say += "#fs11##r<Item Info>\r\n";
                say += "Item to Enhance : #i" + itemid + "# #z" + itemid + "#\r\n";
                say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
                say += "Attack : " + item.getWatk() + "  |  Magic Attack : " + item.getMatk() + "  | Star Force : " + item.getEnhance() + "Star\r\n";
                say += "All Stats : " + item.getAllStat() + "%  |  Total Damage : " + item.getTotalDamage() + "%  |  Boss Damage : " + item.getBossDamage() + "%\r\n";
                say += "Item Enhancement Level : " + getAddEnhance(item) + "#k\r\n\r\n\r\n";
                cm.sendYesNo("#bEnhancement Successful.#k\r\nPress 'Yes' to continue enhancing.\r\n\r\n" + say);

                cm.addEnchantLog(1, item.getItemId(), item.getSerialNumberEquip(), 10, 0, "Meso Enhancement " + (getAddEnhance(item) - 1) + "->" + getAddEnhance(item) + " Success (Account : " + cm.getClient().getAccountName() + ", Character : " + cm.getPlayer().getName() + ", Equipment Info [" + item.toString() + "])");

                re = 1;
                status = 1;
            } else {
                if (choice == 1 || getAddEnhance(item) == 0) {
                    say = "";
                    say += "#fs11##r<Item Info>\r\n";
                    say += "Item to Enhance : #i" + itemid + "# #z" + itemid + "#\r\n";
                    say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
                    say += "Attack : " + item.getWatk() + "  |  Magic Attack : " + item.getMatk() + "  | Star Force : " + item.getEnhance() + "Star\r\n";
                    say += "All Stats : " + item.getAllStat() + "%  |  Total Damage : " + item.getTotalDamage() + "%  |  Boss Damage : " + item.getBossDamage() + "%\r\n";
                    say += "Item Enhancement Level : " + getAddEnhance(item) + "#k\r\n\r\n\r\n";
                    cm.sendYesNo("#rEnhancement failed#k but enhancement level was protected by spending " + ConvertNumber(keep) + " Mesos.\r\nPress 'Yes' to continue enhancing.\r\n\r\n" + say);

                    cm.addEnchantLog(1, item.getItemId(), item.getSerialNumberEquip(), 10, 1, "Meso Enhancement " + getAddEnhance(item) + "->" + (getAddEnhance(item) + 1) + " Failure Protected (Account : " + cm.getClient().getAccountName() + ", Character : " + cm.getPlayer().getName() + ", Equipment Info [" + item.toString() + "])");

                    re = 1;
                    status = 1;
                } else if (choice == 2) {
                    item.setStr(item.getStr() - items[getAddEnhance(item)][3]);
                    item.setDex(item.getDex() - items[getAddEnhance(item)][3]);
                    item.setInt(item.getInt() - items[getAddEnhance(item)][3]);
                    item.setLuk(item.getLuk() - items[getAddEnhance(item)][3]);
                    item.setWatk(item.getWatk() - items[getAddEnhance(item)][4]);
                    item.setMatk(item.getMatk() - items[getAddEnhance(item)][4]);

                    item.setOwner("" + (getAddEnhance(item) - 1) + "강");

                    cm.getPlayer().send(Packages.network.models.CWvsContext.InventoryPacket.updateInventoryItem(Packages.objects.item.MapleInventoryType.EQUIP, item, false, cm.getPlayer()));

                    say = "";
                    say += "#r<Item Info>\r\n";
                    say += "Item to Enhance : #i" + itemid + "# #z" + itemid + "#\r\n";
                    say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
                    say += "Attack : " + item.getWatk() + "  |  Magic Attack : " + item.getMatk() + "  | Star Force : " + item.getEnhance() + "Star\r\n";
                    say += "All Stats : " + item.getAllStat() + "%  |  Total Damage : " + item.getTotalDamage() + "%  |  Boss Damage : " + item.getBossDamage() + "%\r\n";
                    say += "Item Enhancement Level : " + getAddEnhance(item) + "#k\r\n\r\n\r\n";
                    cm.sendYesNo("#rEnhancement failed#k and #renhancement level#k dropped.\r\nPress 'Yes' to continue enhancing.\r\n\r\n" + say);

                    cm.addEnchantLog(1, item.getItemId(), item.getSerialNumberEquip(), 10, 1, "Meso Enhancement " + (getAddEnhance(item) - 1) + "->" + getAddEnhance(item) + " Failure Drop (Account : " + cm.getClient().getAccountName() + ", Character : " + cm.getPlayer().getName() + ", Equipment Info [" + item.toString() + "])");

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
