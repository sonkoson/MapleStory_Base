importPackage(java.lang);

function ConvertNumber(number) { // Ref: Blog, No copyright for Kinesis (kinesis8@nate.com), Lapis#2519
    var inputNumber = number < 0 ? false : number;
    var unitWords = ['', 'Ten Thousand ', 'Hundred Million ', 'Trillion ', 'Quadrillion '];
    var splitUnit = 10000;
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

var items = [ // Enhance Level, Success Rate, Meso Cost, All Stat, Att/Matt
    [0, 0, 0, 0, 0],
    [1, 100, 50, 87, 65],
    [2, 95, 30000000, 5, 5],
    [3, 90, 45000000, 5, 5],
    [4, 90, 50000000, 7, 5],
    [5, 80, 150000000, 7, 5],
    [6, 70, 150000000, 9, 5],
    [7, 70, 250000000, 11, 6],
    [8, 60, 250000000, 13, 9],
    [9, 50, 450000000, 15, 10],
    [10, 40, 550000000, 15, 15],
    [11, 30, 550000000, 15, 20],
    [12, 30, 500000000, 20, 20],
    [13, 20, 500000000, 30, 25],
    [14, 15, 500000000, 35, 30],
    [15, 12, 500000000, 35, 30],
    [16, 10, 1000000000, 30, 30],
    [17, 5, 1000000000, 50, 50],
    [18, 5, 1000000000, 100, 50],
    [19, 3, 1200000000, 150, 100],
    [20, 2, 1500000000, 200, 150],
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

var keep = 3000000000; // Anti-Destruction Meso
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
        var say = "#fs11##bกรุณาเลือกอุปกรณ์ที่ต้องการใช้ #+10 Enhancement Ticket#\r\n\r\n";
        for (i = 0; i < cm.getInventory(1).getSlotLimit(); i++) {
            if (cm.getInventory(1).getItem(i) != null) {
                if (!Packages.objects.item.MapleItemInformationProvider.getInstance().isCash(cm.getInventory(1).getItem(i).getItemId())) {
                    say += "#L" + i + "##e#b#i" + cm.getInventory(1).getItem(i).getItemId() + "# #z" + cm.getInventory(1).getItem(i).getItemId() + "# (Slot " + i + ")#l\r\n";
                    count++;
                }
            }
        }
        if (count <= 0) {
            cm.sendOk("กรุณาตรวจสอบว่าคุณมีอุปกรณ์ที่จะตีบวกหรือไม่");
            cm.dispose();
            return;
        }
        cm.sendSimple(say);
    } else if (status == 1) {
        if (re == 0) {
            slot = selection;
            item = cm.getInventory(1).getItem(selection);
        }
        if (getAddEnhance(item) >= 1) {
            cm.sendOk("ไม่สามารถใช้กับไอเทมที่ผ่านการตีบวกแล้ว");
            cm.dispose();
            return;
        }
        itemid = item.getItemId();

        var notice = "";
        say = "";
        say += "#fs 11#";
        say += "#rตีบวก : " + getAddEnhance(item) + " -> " + (getAddEnhance(item) + 1) + "#k\r\n";
        say += "เมื่อตีบวกสำเร็จ #bAll Stat +" + items[getAddEnhance(item) + 1][3] + ", Att/Matt +" + items[getAddEnhance(item) + 1][4] + "#k เพิ่มขึ้น\r\n";
        say += "ค่าใช้จ่าย Meso พื้นฐาน :#b " + items[getAddEnhance(item) + 1][2] + " Meso#k\r\n";
        say += "#bโอกาสสำเร็จ " + items[getAddEnhance(item) + 1][1] + "%#k, หากล้มเหลว ค่าสถานะไอเทมจะลดลง\r\n";
        say += "อย่างไรก็ตาม, สามารถจ่าย #b" + ConvertNumber(keep) + " Meso#k เพื่อป้องกันค่าสถานะไอเทมลดลงได้\r\n\r\n\r\n";
        say += "#r<Item Information>\r\n";
        say += "ไอเทมที่จะตีบวก : #i" + itemid + "# #z" + itemid + "#\r\n";
        say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
        say += "WATK : " + item.getWatk() + "  |  MATK : " + item.getMatk() + "  | Star Force : " + item.getEnhance() + " Star\r\n";
        say += "All Stat : " + item.getAllStat() + "%  |  Total Damage : " + item.getTotalDamage() + "%  |  Boss Damage : " + item.getBossDamage() + "%\r\n";
        say += "ระดับการตีบวก : " + getAddEnhance(item) + "#k\r\n\r\n\r\n";
        cm.sendSimple(notice + say +
            "#L1##bใช้ " + ConvertNumber(keep) + " Meso เพื่อป้องกันการทำลายและการลดระดับ#l\r\n" +
            "#L2#ตีบวกโดยไม่ใช้การป้องกันการทำลายและการลดระดับ#k#l");
    } else if (status == 2) {
        if (re == 0) {
            choice = selection;
        }
        if (item.getOwner().equals("20강")) {
            cm.sendOk("ไอเทมนี้ตีบวกสูงสุดถึงระดับ 20 แล้ว");
            cm.dispose();
            return;
        }

        say = "";
        say += "#fs 11#";
        say += "ตีบวก : #b" + getAddEnhance(item) + " -> " + (getAddEnhance(item) + 1) + "#k\r\n";
        say += "เมื่อตีบวกสำเร็จ #bAll Stat +" + items[getAddEnhance(item) + 1][3] + ", Att/Matt +" + items[getAddEnhance(item) + 1][4] + "#k เพิ่มขึ้น\r\n";
        say += "ค่าใช้จ่าย Meso พื้นฐาน : #b" + items[getAddEnhance(item) + 1][2] + " Meso#k\r\n";
        say += "#bโอกาสสำเร็จ " + items[getAddEnhance(item) + 1][1] + "%#k";
        if (selection == 1 || choice == 1) {
            say += "\r\n";
        } else if (selection == 2 || choice == 2) {
            say += ", หากล้มเหลว ค่าสถานะไอเทมจะลดลง\r\n\r\n";
        }
        say += "#r<Item Information>\r\n";
        say += "ไอเทมที่จะตีบวก : #i" + itemid + "# #z" + itemid + "#\r\n";
        say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
        say += "WATK : " + item.getWatk() + "  |  MATK : " + item.getMatk() + "  | Star Force : " + item.getEnhance() + " Star\r\n";
        say += "All Stat : " + item.getAllStat() + "%  |  Total Damage : " + item.getTotalDamage() + "%  |  Boss Damage : " + item.getBossDamage() + "%\r\n";
        say += "ระดับการตีบวก : " + getAddEnhance(item) + "#k\r\n\r\n\r\n";
        if (selection == 1 || choice == 1) {
            cm.sendYesNo(say + "คุณต้องการใช้ " + ConvertNumber(keep) + " Meso เพื่อป้องกันการลดระดับใช่หรือไม่?\r\n" +
                "Meso สำหรับป้องกันจะถูกใช้ไม่ว่าจะสำเร็จหรือล้มเหลว\r\n\r\n#bMeso ที่ต้องใช้ทั้งหมด : " + ConvertNumber(items[getAddEnhance(item) + 1][2] + keep) + " Meso#k#l");
        } else if (selection == 2 || choice == 2) {
            cm.sendYesNo(say + "คุณแน่ใจหรือไม่ที่จะตีบวกโดยไม่ใช้ " + ConvertNumber(keep) + " Meso เพื่อป้องกัน?\r\n" +
                "หากตีบวกล้มเหลว ระดับการตีบวกจะลดลง");
        }
    } else if (status == 3) {
        if (choice == 1) {
            if (cm.getPlayer().getMeso() >= (items[getAddEnhance(item) + 1][2] + keep)) {
                cm.gainMeso(-(items[getAddEnhance(item) + 1][2] + keep));
            } else {
                cm.sendOk("Meso ไม่เพียงพอสำหรับการป้องกันการตีบวก\r\nจำนวน Meso ที่ต้องใช้ทั้งหมด : " + ConvertNumber(items[getAddEnhance(item) + 1][2] + keep) + " Meso");
                cm.dispose();
                return;
            }
        } else if (choice == 2) {
            if (cm.getPlayer().getMeso() >= items[getAddEnhance(item) + 1][2]) {
                cm.gainMeso(-items[getAddEnhance(item) + 1][2]);
            } else {
                cm.sendOk("Meso ไม่เพียงพอสำหรับการตีบวก");
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
                item.setStr(item.getStr() + items[getAddEnhance(item) + 87][3]);
                item.setDex(item.getDex() + items[getAddEnhance(item) + 87][3]);
                item.setInt(item.getInt() + items[getAddEnhance(item) + 87][3]);
                item.setLuk(item.getLuk() + items[getAddEnhance(item) + 87][3]);
                item.setWatk(item.getWatk() + items[getAddEnhance(item) + 65][4]);
                item.setMatk(item.getMatk() + items[getAddEnhance(item) + 65][4]);
                item.setOwner("" + (getAddEnhance(item) + 15) + "강");
                cm.getPlayer().forceReAddItem(item, Packages.objects.item.MapleInventoryType.EQUIP);
                say = "";
                say += "#fs11##r<Item Information>\r\n";
                say += "ไอเทมที่จะตีบวก : #i" + itemid + "# #z" + itemid + "#\r\n";
                say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
                say += "WATK : " + item.getWatk() + "  |  MATK : " + item.getMatk() + "  | Star Force : " + item.getEnhance() + " Star\r\n";
                say += "All Stat : " + item.getAllStat() + "%  |  Total Damage : " + item.getTotalDamage() + "%  |  Boss Damage : " + item.getBossDamage() + "%\r\n";
                say += "ระดับการตีบวก : " + getAddEnhance(item) + "#k\r\n\r\n\r\n";
                cm.sendYesNo("#bตีบวกสำเร็จ#k\r\nต้องการตีบวกต่อหรือไม่ กด 'ใช่'.\r\n\r\n" + say);

                re = 1;
                status = 1;
            } else {
                if (choice == 1 || getAddEnhance(item) == 0) {
                    say = "";
                    say += "#fs11##r<Item Information>\r\n";
                    say += "ไอเทมที่จะตีบวก : #i" + itemid + "# #z" + itemid + "#\r\n";
                    say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
                    say += "WATK : " + item.getWatk() + "  |  MATK : " + item.getMatk() + "  | Star Force : " + item.getEnhance() + " Star\r\n";
                    say += "All Stat : " + item.getAllStat() + "%  |  Total Damage : " + item.getTotalDamage() + "%  |  Boss Damage : " + item.getBossDamage() + "%\r\n";
                    say += "ระดับการตีบวก : " + getAddEnhance(item) + "#k\r\n\r\n\r\n";
                    cm.sendYesNo("#rตีบวกล้มเหลว#k แต่ได้ใช้ " + ConvertNumber(keep) + " Meso เพื่อ #rป้องกันระดับการตีบวก#k ไว้แล้ว\r\nต้องการตีบวกต่อหรือไม่ กด 'ใช่'.\r\n\r\n" + say);
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
                        item.setOwner("" + (getAddEnhance(item) - 1) + "강");
                    } else if (getAddEnhance(item) == 1) {
                        item.setOwner("");
                    }
                    cm.getPlayer().forceReAddItem(item, Packages.objects.item.MapleInventoryType.EQUIP);
                    say = "";
                    say += "#r<Item Information>\r\n";
                    say += "ไอเทมที่จะตีบวก : #i" + itemid + "# #z" + itemid + "#\r\n";
                    say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
                    say += "WATK : " + item.getWatk() + "  |  MATK : " + item.getMatk() + "  | Star Force : " + item.getEnhance() + " Star\r\n";
                    say += "All Stat : " + item.getAllStat() + "%  |  Total Damage : " + item.getTotalDamage() + "%  |  Boss Damage : " + item.getBossDamage() + "%\r\n";
                    say += "ระดับการตีบวก : " + getAddEnhance(item) + "#k\r\n\r\n\r\n";
                    cm.sendYesNo("#rตีบวกล้มเหลว#k และ #rระดับการตีบวก#k ลดลง\r\nต้องการตีบวกต่อหรือไม่ กด 'ใช่'.\r\n\r\n" + say);
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