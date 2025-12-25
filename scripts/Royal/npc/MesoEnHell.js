importPackage(java.lang);

var equipped = false;

var banitem = [1712001, 1712002, 1712003, 1712004, 1712005, 1712006, 1713000, 1713001, 1713002, 1713003, 1713004, 1713005];

var canowner = ["20 Star", "△", "★", "★△", "★★", "★★△"];

function ConvertNumber(number) {
    var inputNumber = number < 0 ? false : number;
    var unitWords = ['', 'K ', 'M ', 'B ', 'T ', 'Q '];
    var splitUnit = 1000;
    var splitCount = unitWords.length;
    var resultArray = [];
    var resultString = '';
    if (inputNumber == false) {
        cm.sendOk("#fs11#เกิดข้อผิดพลาด กรุณาลองใหม่อีกครั้ง\r\n(Parsing Error)");
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

var items = [ // Level, Success Rate, Pouch Cost, All Stat, Att/Mag, Red Ball, Blue Ball, All Stat%
    [0, 0, 0, 0, 0, 0, 0, 0],
    ["△", 50, 100, 300, 200, 1000, 1, 0],
    ["★", 40, 100, 300, 200, 1500, 2, 1],
    ["★△", 30, 200, 400, 250, 2000, 3, 1],
    ["★★", 20, 200, 400, 250, 3000, 4, 2],
    ["★★△", 15, 300, 500, 300, 4000, 5, 2],
    ["★★★", 10, 300, 500, 300, 5000, 6, 4],
];

function getAddEnhance(item) {
    var owner = item.getOwner();
    return owner == "△" ? 1 :
        owner == "★" ? 2 :
            owner == "★△" ? 3 :
                owner == "★★" ? 4 :
                    owner == "★★△" ? 5 :
                        owner == "★★★" ? 6 :
                            0;
}

function getStar(star) {
    return star == 1 ? "△" :
        star == 2 ? "★" :
            star == 3 ? "★△" :
                star == 4 ? "★★" :
                    star == 5 ? "★★△" :
                        star == 6 ? "★★★" :
                            "20 Star";
}

var keep = 10000000000; //Protection Cost (Meso)
var keep2 = 100; //Pouch (Not used in logic?)
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
        var say = "#fs11##bกรุณาเลือกอุปกรณ์ที่จะตีบวก#k\r\n\r\n#rเฉพาะไอเท็มที่มีสถานะ +20 เท่านั้นที่สามารถเสริมพลังได้#n#k\r\n\r\n※ การเสริมพลัง Transcendence สามารถทำได้สูงสุด 6 ดาว\r\n\r\n#r#e※ หมายเหตุ ※\r\nไอเท็มที่สวมใส่อยู่จะแสดงก่อน#n#k#b\r\n\r\n";
        for (i = 0; i <= 50; i++) { // Equipped
            item = cm.getInventory(-1).getItem(i * -1)
            if (item != null) {
                if (canowner.indexOf(item.getOwner()) != -1) {
                    if (!cm.isCash(item.getItemId())) {
                        say += "#L" + (i + 100000) + "##e#b#i" + item.getItemId() + "# #z" + item.getItemId() + "# #r#e[สวมใส่]#n#k#l\r\n";
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
                        say += "#L" + i + "##e#b#i" + item.getItemId() + "# #z" + item.getItemId() + "# #r[ช่อง " + i + "]#k#l\r\n";
                        count++;
                    }
                }
            }
        }
        if (count <= 0) {
            cm.sendOk("#fs11#กรุณาตรวจสอบว่าคุณมีอุปกรณ์ที่จะตีบวกหรือไม่\r\nการเสริมพลัง Transcendence ใช้ได้เฉพาะไอเท็มที่ผ่านการตีบวก Meso +20 แล้วเท่านั้น");
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

        if (item.getOwner().equals("★★★")) {
            cm.sendOk("#fs11#ไอเท็มนี้ได้รับการเสริมพลัง Transcendence เต็มแล้ว");
            cm.dispose();
            return;
        }
        if (canowner.indexOf(item.getOwner()) == -1) {
            cm.sendOk("#fs11#การเสริมพลัง Transcendence ใช้ได้เฉพาะไอเท็มที่ผ่านการตีบวก Meso +20 แล้วเท่านั้น");
            cm.dispose();
            return;
        }
        itemid = item.getItemId();
        if (banitem.indexOf(itemid) != -1) {
            cm.sendOk("#fs11#ไอเท็มสัญลักษณ์ไม่สามารถเสริมพลังได้");
            cm.dispose();
            return;
        }

        //Stats
        var notice = "";
        say = "";
        say += "#fs 11#";
        say += "#rเลื่อนขั้น : " + getStar(getAddEnhance(item)) + " -> " + getStar(getAddEnhance(item) + 1) + "#k\r\n";
        say += "เมื่อสำเร็จ: #bAll Stats +" + items[getAddEnhance(item) + 1][3] + ", Att/Mag +" + items[getAddEnhance(item) + 1][4] + ", All Stat + " + items[getAddEnhance(item) + 1][7] + "%#k เพิ่มขึ้น\r\n";
        say += "จำนวนถุงที่ต้องการ :#b #z4001715# " + items[getAddEnhance(item) + 1][2] + " ชิ้น#k\r\n";
        say += "วัตถุดิบที่ต้องการ :#b #z4031227# " + items[getAddEnhance(item) + 1][5] + " ชิ้น, #z4031228# " + items[getAddEnhance(item) + 1][6] + " ชิ้น#k\r\n";
        say += "#bโอกาสสำเร็จ " + items[getAddEnhance(item) + 1][1] + "%#k, #rยศลดลงเมื่อล้มเหลว#k\r\n\r\n";
        say += "#r<ข้อมูลไอเท็ม>\r\n";
        say += "#fc0xFF9A9A9A##i" + itemid + "# #z" + itemid + "#\r\n";
        say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
        say += "Att : " + item.getWatk() + "  |  Mag : " + item.getMatk() + "  | All Stat% : " + item.getAllStat() + "% | StarForce : " + item.getEnhance() + "\r\n";
        cm.sendSimple(notice + say +
            "#L1##bใช้ถุง #z4001715# เพิ่ม 100 ชิ้นเพื่อป้องกันยศลด#l\r\n" +
            "#L2#เสริมพลังโดยไม่ป้องกันยศลด#k#l");
    } else if (status == 2) {
        if (re == 0) {
            choice = selection;
        }
        //Stats
        say = "";
        say += "#fs 11#";
        say += "#rเลื่อนขั้น : " + getStar(getAddEnhance(item)) + " -> " + getStar(getAddEnhance(item) + 1) + "#k\r\n";
        say += "เมื่อสำเร็จ: #bAll Stats +" + items[getAddEnhance(item) + 1][3] + ", Att/Mag +" + items[getAddEnhance(item) + 1][4] + ", All Stat + " + items[getAddEnhance(item) + 1][7] + "%#k เพิ่มขึ้น\r\n";
        say += "จำนวนถุงที่ต้องการ :#b #z4001715# " + items[getAddEnhance(item) + 1][2] + " ชิ้น#k\r\n";
        say += "วัตถุดิบที่ต้องการ :#b #z4031227# " + items[getAddEnhance(item) + 1][5] + " ชิ้น, #z4031228# " + items[getAddEnhance(item) + 1][6] + " ชิ้น#k\r\n";
        say += "#bโอกาสสำเร็จ " + items[getAddEnhance(item) + 1][1] + "%#k";
        if (selection == 1 || choice == 1) {
            say += ", #bป้องกันยศลด#k\r\n\r\n";
        } else if (selection == 2 || choice == 2) {
            say += ", #rยศลดลงเมื่อล้มเหลว#k\r\n\r\n";
        }
        say += "#r<ข้อมูลไอเท็ม>\r\n";
        say += "#fc0xFF9A9A9A##i" + itemid + "# #z" + itemid + "#\r\n";
        say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
        say += "Att : " + item.getWatk() + "  |  Mag : " + item.getMatk() + "  | All Stat% : " + item.getAllStat() + "% | StarForce : " + item.getEnhance() + "\r\n";

        if (selection == 1 || choice == 1) {
            cm.sendYesNo(say + "\r\nคุณต้องการใช้ถุง #z4001715# เพิ่ม 100 ชิ้น เพื่อป้องกันยศลดหรือไม่?\r\n" + "ค่าป้องกันจะถูกใช้ไม่ว่าจะสำเร็จหรือล้มเหลว");
        } else if (selection == 2 || choice == 2) {
            cm.sendYesNo(say + "\r\nคุณต้องการเสริมพลังโดยไม่ใช้การป้องกันถุง #z4001715# เพิ่ม 100 ชิ้น หรือไม่?\r\n" + "ยศจะลดลงหากการเสริมพลังล้มเหลว");
        }

    } else if (status == 3) {
        if (choice == 1) {
            if (!cm.haveItem(4001715, items[getAddEnhance(item) + 1][2] + keep2)) { // Pouch + keep2 (100)?
                cm.sendOk("#fs11#ถุง/Meso ไม่เพียงพอ");
                cm.dispose();
                return;
            }
            if (!cm.haveItem(4031227, items[getAddEnhance(item) + 1][5])) {
                cm.sendOk("#fs11#วัสดุไม่เพียงพอ");
                cm.dispose();
                return;
            }
            if (!cm.haveItem(4031228, items[getAddEnhance(item) + 1][6])) {
                cm.sendOk("#fs11#วัสดุไม่เพียงพอ");
                cm.dispose();
                return;
            }

            cm.gainItem(4001715, -(items[getAddEnhance(item) + 1][2] + keep2));
            cm.gainItem(4031227, -items[getAddEnhance(item) + 1][5]);
            cm.gainItem(4031228, -items[getAddEnhance(item) + 1][6]);

        } else if (choice == 2) {
            if (!cm.haveItem(4001715, items[getAddEnhance(item) + 1][2])) {
                cm.sendOk("#fs11#ถุงไม่เพียงพอ");
                cm.dispose();
                return;
            }
            // ...
            cm.gainItem(4001715, -items[getAddEnhance(item) + 1][2]);
            // ...
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
                item.setAllStat((item.getAllStat() + items[getAddEnhance(item) + 1][7]));
                item.setOwner("" + getStar(getAddEnhance(item) + 1) + "");
                cm.getPlayer().send(Packages.network.models.CWvsContext.InventoryPacket.updateInventoryItem(Packages.objects.item.MapleInventoryType.EQUIP, item, false, cm.getPlayer()));

                say = "#fs11#";
                say += "#r<Item Info>\r\n";
                say += "#fc0xFF9A9A9A##i" + itemid + "# #z" + itemid + "#\r\n";
                say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
                say += "Att : " + item.getWatk() + "  |  Mag : " + item.getMatk() + "  | All Stat% : " + item.getAllStat() + "% | StarForce : " + item.getEnhance() + "\r\n";

                if (item.getOwner().equals("★★★")) {
                    cm.sendOk("#fs11##bการเสริมพลังสำเร็จ#k.\r\nระดับสูงสุดแล้ว\r\n\r\n" + say);
                } else {
                    cm.sendYesNo("#fs11##bการเสริมพลังสำเร็จ#k.\r\nกด 'Yes' เพื่อดำเนินการต่อ\r\n\r\n" + say);
                }

                cm.addEnchantLog(1, item.getItemId(), item.getSerialNumberEquip(), 10, 0, "Transcendence Enhancement " + getStar((getAddEnhance(item) - 1)) + "->" + getStar(getAddEnhance(item)) + " Success (Acc : " + cm.getClient().getAccountName() + ", Char : " + cm.getPlayer().getName() + ", " + itemid + " [" + item.toString() + "])");

                re = 1;
                status = 1;
            } else {
                if (choice == 1 || getAddEnhance(item) == 0) {
                    say = "#fs11#";
                    say += "#r<Item Info>\r\n";
                    say += "#fc0xFF9A9A9A##i" + itemid + "# #z" + itemid + "#\r\n";
                    say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
                    say += "Att : " + item.getWatk() + "  |  Mag : " + item.getMatk() + "  | All Stat% : " + item.getAllStat() + "% | StarForce : " + item.getEnhance() + "\r\n";
                    cm.sendYesNo("#fs11##rการเสริมพลังล้มเหลว#k แต่ #rยศ#k ได้รับการป้องกันไว้\r\nกด 'Yes' เพื่อดำเนินการต่อ\r\n\r\n" + say);

                    cm.addEnchantLog(1, item.getItemId(), item.getSerialNumberEquip(), 10, 1, "Transcendence Enhancement " + getStar(getAddEnhance(item)) + "->" + getStar((getAddEnhance(item) + 1)) + " Failed Protected (Acc : " + cm.getClient().getAccountName() + ", Char : " + cm.getPlayer().getName() + ", " + itemid + " [" + item.toString() + "])");

                    re = 1;
                    status = 1;
                } else if (choice == 2) {
                    item.setStr(item.getStr() - items[getAddEnhance(item)][3]);
                    item.setDex(item.getDex() - items[getAddEnhance(item)][3]);
                    item.setInt(item.getInt() - items[getAddEnhance(item)][3]);
                    item.setLuk(item.getLuk() - items[getAddEnhance(item)][3]);
                    item.setWatk(item.getWatk() - items[getAddEnhance(item)][4]);
                    item.setMatk(item.getMatk() - items[getAddEnhance(item)][4]);

                    item.setAllStat((item.getAllStat() - items[getAddEnhance(item)][7]));
                    item.setOwner("" + getStar(getAddEnhance(item) - 1) + "");

                    cm.getPlayer().send(Packages.network.models.CWvsContext.InventoryPacket.updateInventoryItem(Packages.objects.item.MapleInventoryType.EQUIP, item, false, cm.getPlayer()));

                    say = "#fs11#";
                    say += "#r<Item Info>\r\n";
                    say += "#fc0xFF9A9A9A##i" + itemid + "# #z" + itemid + "#\r\n";
                    say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
                    say += "Att : " + item.getWatk() + "  |  Mag : " + item.getMatk() + "  | All Stat% : " + item.getAllStat() + "% | StarForce : " + item.getEnhance() + "\r\n";
                    cm.sendYesNo("#fs11##rการเสริมพลังล้มเหลว#k และ #rยศลดลง#k.\r\nกด 'Yes' เพื่อดำเนินการต่อ\r\n\r\n" + say);

                    cm.addEnchantLog(1, item.getItemId(), item.getSerialNumberEquip(), 10, 1, "Transcendence Enhancement " + getStar((getAddEnhance(item) - 1)) + "->" + getStar(getAddEnhance(item)) + " Failed Dropped (Acc : " + cm.getClient().getAccountName() + ", Char : " + cm.getPlayer().getName() + ", " + itemid + " [" + item.toString() + "])");

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