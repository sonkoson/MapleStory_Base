importPackage(java.lang);

var banitem = [1712001, 1712002, 1712003, 1712004, 1712005, 1712006, 1713000, 1713001, 1713002, 1713003, 1713004, 1713005];

function ConvertNumber(number) {
    var inputNumber = number < 0 ? false : number;
    var unitWords = ['', 'K ', 'M ', 'B ', 'T '];
    var splitUnit = 1000;
    var splitCount = unitWords.length;
    var resultArray = [];
    var resultString = '';
    if (inputNumber == false) {
        cm.sendOk("เกิดข้อผิดพลาด กรุณาลองใหม่อีกครั้ง\r\n(Parsing Error)");
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

var items = [ //Enhance Count, Success Rate, Cost, AllStat, Att/Mag
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

var keep = 3000000000; //Prevention Cost
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
        var say = "#fs11##bโปรดเลือกอุปกรณ์ที่ต้องการอัพเกรด#k\r\n\r\n#rนี่เป็นระบบอัพเกรดที่แยกจาก Star Force#k\r\n#r#eอย่าอัพเกรด Symbol เนื่องจากค่าสถานะจะไม่ถูกนำไปใช้#n#k\r\n\r\n";
        for (i = 0; i <= cm.getInventory(1).getSlotLimit(); i++) {
            if (cm.getInventory(1).getItem(i) != null) {
                if (!Packages.objects.item.MapleItemInformationProvider.getInstance().isCash(cm.getInventory(1).getItem(i).getItemId())) {
                    say += "#L" + i + "##e#b#i" + cm.getInventory(1).getItem(i).getItemId() + "# #z" + cm.getInventory(1).getItem(i).getItemId() + "# #r[ช่อง " + i + "]#k#l\r\n";
                    count++;
                }
            }
        }
        if (count <= 0) {
            cm.sendOk("โปรดตรวจสอบว่ามีอุปกรณ์สำหรับอัพเกรดหรือไม่");
            cm.dispose();
            return;
        }
        cm.sendSimple(say);
    } else if (status == 1) {
        if (re == 0) {
            slot = selection;
            item = cm.getInventory(1).getItem(selection);
        }
        if (item.getOwner().equals("20 Star")) {
            cm.sendOk("ไอเท็มถูกอัพเกรดสูงสุดถึง +20 แล้ว");
            cm.dispose();
            return;
        }
        if (item.getOwner().equals("[Promotion]")) {
            cm.sendOk("ไอเท็มโปรโมชั่นไม่สามารถอัพเกรดได้");
            cm.dispose();
            return;
        }

        itemid = item.getItemId();
        if (banitem.indexOf(itemid) != -1) {
            cm.sendOk("#fs11#ไอเท็ม Symbol ไม่สามารถอัพเกรดได้");
            cm.dispose();
            return;
        }

        //Stats
        var notice = "";
        say = "";
        say += "#fs 11#";
        say += "#rอัพเกรด : +" + getAddEnhance(item) + " -> +" + (getAddEnhance(item) + 1) + "#k\r\n";
        say += "เมื่อสำเร็จ: #bAll Stats +" + items[getAddEnhance(item) + 1][3] + ", Att/Mag +" + items[getAddEnhance(item) + 1][4] + "#k เพิ่มขึ้น\r\n";
        say += "Meso ที่ต้องใช้ :#b " + items[getAddEnhance(item) + 1][2] + " Meso#k\r\n";
        say += "#bโอกาสสำเร็จ " + items[getAddEnhance(item) + 1][1] + "%#k, ค่าสถานะจะลดลงเมื่อล้มเหลว\r\n";
        say += "อย่างไรก็ตาม คุณสามารถใช้ #b" + ConvertNumber(keep) + " Meso#k เพื่อป้องกันค่าสถานะไอเท็มได้\r\n\r\n\r\n";
        say += "#r<ข้อมูลไอเท็ม>\r\n";
        say += "ไอเท็ม : #i" + itemid + "# #z" + itemid + "#\r\n";
        say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
        say += "Att : " + item.getWatk() + "  |  Mag : " + item.getMatk() + "  | StarForce : " + item.getEnhance() + "\r\n";
        say += "All Stat : " + item.getAllStat() + "%  |  Total Dmg : " + item.getTotalDamage() + "%  |  Boss Dmg : " + item.getBossDamage() + "%\r\n";
        say += "จำนวนการอัพเกรด : " + getAddEnhance(item) + "#k\r\n\r\n\r\n";
        cm.sendSimple(notice + say +
            "#L1##bใช้ " + ConvertNumber(keep) + " Meso เพื่อป้องกันการทำลาย/การลดระดับ#l\r\n" +
            "#L2#อัพเกรดโดยไม่ป้องกันการทำลาย/การลดระดับ#k#l");
    } else if (status == 2) {
        if (re == 0) {
            choice = selection;
        }
        if (item.getOwner().equals("20 Star")) {
            cm.sendOk("ไอเท็มถูกอัพเกรดสูงสุดถึง +20 แล้ว");
            cm.dispose();
            return;
        }

        say = "";
        say += "#fs 11#";
        say += "อัพเกรด : #b+" + getAddEnhance(item) + " -> +" + (getAddEnhance(item) + 1) + "#k\r\n";
        say += "เมื่อสำเร็จ: #bAll Stats +" + items[getAddEnhance(item) + 1][3] + ", Att/Mag +" + items[getAddEnhance(item) + 1][4] + "#k เพิ่มขึ้น\r\n";
        say += "Meso ที่ต้องใช้ : #b" + items[getAddEnhance(item) + 1][2] + " Meso#k\r\n";
        say += "#bโอกาสสำเร็จ " + items[getAddEnhance(item) + 1][1] + "%#k";
        if (selection == 1 || choice == 1) {
            say += "\r\n";
        } else if (selection == 2 || choice == 2) {
            say += ", ค่าสถานะจะลดลงหากล้มเหลว\r\n\r\n";
        }
        say += "#r<ข้อมูลไอเท็ม>\r\n";
        say += "ไอเท็ม : #i" + itemid + "# #z" + itemid + "#\r\n";
        say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
        say += "Att : " + item.getWatk() + "  |  Mag : " + item.getMatk() + "  | StarForce : " + item.getEnhance() + "\r\n";
        say += "All Stat : " + item.getAllStat() + "%  |  Total Dmg : " + item.getTotalDamage() + "%  |  Boss Dmg : " + item.getBossDamage() + "%\r\n";
        say += "จำนวนการอัพเกรด : " + getAddEnhance(item) + "#k\r\n\r\n\r\n";
        if (selection == 1 || choice == 1) {
            cm.sendYesNo(say + "คุณต้องการใช้ " + ConvertNumber(keep) + " Meso เพื่อป้องกันการลดระดับใช่หรือไม่?\r\n" +
                "ค่าป้องกันจะถูกใช้ไม่ว่าจะสำเร็จหรือล้มเหลว\r\n\r\n#bจำนวนที่ต้องการทั้งหมด : " + ConvertNumber(items[getAddEnhance(item) + 1][2] + keep) + " Meso#k#l");
        } else if (selection == 2 || choice == 2) {
            cm.sendYesNo(say + "คุณต้องการอัพเกรดโดยไม่ใช้ " + ConvertNumber(keep) + " Meso ในการป้องกันหรือไม่?\r\n" +
                "ระดับการอัพเกรดจะลดลงหากการอัพเกรดล้มเหลว");
        }
    } else if (status == 3) {
        if (choice == 1) {
            if (cm.getPlayer().getMeso() >= (items[getAddEnhance(item) + 1][2] + keep)) {
                cm.gainMeso(-(items[getAddEnhance(item) + 1][2] + keep));
            } else {
                cm.sendOk("Meso ไม่เพียงพอสำหรับการป้องกันการอัพเกรด\r\nต้องใช้ทั้งหมด : " + ConvertNumber(items[getAddEnhance(item) + 1][2] + keep) + " Meso");
                cm.dispose();
                return;
            }
        } else if (choice == 2) {
            if (cm.getPlayer().getMeso() >= items[getAddEnhance(item) + 1][2]) {
                cm.gainMeso(-items[getAddEnhance(item) + 1][2]);
            } else {
                cm.sendOk("Meso ไม่เพียงพอ");
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
                item.setOwner("" + (getAddEnhance(item) + 1) + " Star");
                cm.getPlayer().send(Packages.network.models.CWvsContext.InventoryPacket.updateInventoryItem(Packages.objects.item.MapleInventoryType.EQUIP, item, false, cm.getPlayer()));

                say = "";
                say += "#fs11##r<ข้อมูลไอเท็ม>\r\n";
                say += "ไอเท็ม : #i" + itemid + "# #z" + itemid + "#\r\n";
                say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
                say += "Att : " + item.getWatk() + "  |  Mag : " + item.getMatk() + "  | StarForce : " + item.getEnhance() + "\r\n";
                say += "All Stat : " + item.getAllStat() + "%  |  Total Dmg : " + item.getTotalDamage() + "%  |  Boss Dmg : " + item.getBossDamage() + "%\r\n";
                say += "จำนวนการอัพเกรด : " + getAddEnhance(item) + "#k\r\n\r\n\r\n";
                cm.sendYesNo("#bอัพเกรดสำเร็จ#k\r\nกด 'ใช่' เพื่ออัพเกรดต่อ\r\n\r\n" + say);

                cm.addEnchantLog(1, item.getItemId(), item.getSerialNumberEquip(), 10, 0, "Meso Enhancement " + (getAddEnhance(item) - 1) + "->" + getAddEnhance(item) + " Success (Acc : " + cm.getClient().getAccountName() + ", Char : " + cm.getPlayer().getName() + ", Item [" + item.toString() + "])");

                re = 1;
                status = 1;
            } else {
                if (choice == 1 || getAddEnhance(item) == 0) {
                    say = "";
                    say += "#fs11##r<ข้อมูลไอเท็ม>\r\n";
                    say += "ไอเท็ม : #i" + itemid + "# #z" + itemid + "#\r\n";
                    say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
                    say += "Att : " + item.getWatk() + "  |  Mag : " + item.getMatk() + "  | StarForce : " + item.getEnhance() + "\r\n";
                    say += "All Stat : " + item.getAllStat() + "%  |  Total Dmg : " + item.getTotalDamage() + "%  |  Boss Dmg : " + item.getBossDamage() + "%\r\n";
                    say += "จำนวนการอัพเกรด : " + getAddEnhance(item) + "#k\r\n\r\n\r\n";
                    cm.sendYesNo("#rการอัพเกรดล้มเหลว#k แต่ใช้ " + ConvertNumber(keep) + " Meso เพื่อ #rป้องกันระดับ#k เรียบร้อยแล้ว\r\nกด 'ใช่' เพื่ออัพเกรดต่อ\r\n\r\n" + say);

                    cm.addEnchantLog(1, item.getItemId(), item.getSerialNumberEquip(), 10, 1, "Meso Enhancement " + getAddEnhance(item) + "->" + (getAddEnhance(item) + 1) + " Failed Protected (Acc : " + cm.getClient().getAccountName() + ", Char : " + cm.getPlayer().getName() + ", Item [" + item.toString() + "])");

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

                    say = "";
                    say += "#r<ข้อมูลไอเท็ม>\r\n";
                    say += "ไอเท็ม : #i" + itemid + "# #z" + itemid + "#\r\n";
                    say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
                    say += "Att : " + item.getWatk() + "  |  Mag : " + item.getMatk() + "  | StarForce : " + item.getEnhance() + "\r\n";
                    say += "All Stat : " + item.getAllStat() + "%  |  Total Dmg : " + item.getTotalDamage() + "%  |  Boss Dmg : " + item.getBossDamage() + "%\r\n";
                    say += "จำนวนการอัพเกรด : " + getAddEnhance(item) + "#k\r\n\r\n\r\n";
                    cm.sendYesNo("#rการอัพเกรดล้มเหลว#k และ #rระดับลดลง#k\r\nกด 'ใช่' เพื่ออัพเกรดต่อ\r\n\r\n" + say);

                    cm.addEnchantLog(1, item.getItemId(), item.getSerialNumberEquip(), 10, 1, "Meso Enhancement " + (getAddEnhance(item) - 1) + "->" + getAddEnhance(item) + " Failed Dropped (Acc : " + cm.getClient().getAccountName() + ", Char : " + cm.getPlayer().getName() + ", Item [" + item.toString() + "])");

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
