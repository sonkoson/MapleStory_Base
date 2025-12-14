importPackage(java.lang);

var equipped = false;

var banitem = [1712001, 1712002, 1712003, 1712004, 1712005, 1712006, 1713000, 1713001];

var cantowner = ["△", "★", "★△", "★★", "★★△", "★★★"];

var ItemCode = 2430041;
var TargetStar = 10;
var AllStat = 92;
var AttMatk = 70;

function ConvertNumber(number) { // Reference from a blog, copyright belongs neither to Kinesis (kinesis8@nate.com) or Lapis#2519
    var inputNumber = number < 0 ? false : number;
    var unitWords = ['', 'K ', 'M ', 'B ', 'T ', 'Q '];
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

var items = [ // Enhance Level, Success Rate, Cost, All Stat, Att/Matt
    [0, 0, 0, 0, 0],
    [1, 100, 0, 87, 65],
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

var keep = 3000000000; // Protection Cost
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
        var say = "#fs11##bเลือกไอเท็มที่ต้องการใช้ใบเสริมแกร่ง +" + TargetStar + "#k\r\n\r\n";

        for (i = 0; i <= 50; i++) { // Equipped
            item = cm.getInventory(-1).getItem(i * -1)
            if (item != null) {
                if (!cm.isCash(item.getItemId())) {
                    say += "#L" + (i + 100000) + "##b#i" + item.getItemId() + "# #z" + item.getItemId() + "# #r#e[สวมใส่]#k#n#l\r\n";
                    count++;
                }
            }
        }

        for (i = 0; i <= cm.getInventory(1).getSlotLimit(); i++) {
            item = cm.getInventory(1).getItem(i)
            if (item != null) {
                if (!cm.isCash(item.getItemId())) {
                    say += "#L" + i + "##b#i" + item.getItemId() + "# #z" + item.getItemId() + "# #r[ช่อง " + i + "]#k#l\r\n";
                    count++;
                }
            }
        }

        if (count <= 0) {
            cm.sendOk("กรุณาตรวจสอบว่ามีไอเท็มที่จะเสริมแกร่งหรือไม่");
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
            cm.sendOk("#fs11#ไอเท็มนี้เสริมแกร่งถึงระดับ 20 แล้ว");
            cm.dispose();
            return;
        }
        if (cantowner.indexOf(item.getOwner()) != -1) {
            cm.sendOk("#fs11#ไอเท็มนี้ผ่านการ Transcendence Enhancement แล้ว");
            cm.dispose();
            return;
        }
        if (getAddEnhance(item) >= 1) {
            cm.sendOk("#fs11#ไม่สามารถใช้กับไอเท็มที่เสริมแกร่งไปแล้วอย่างน้อย 1 ครั้ง");
            cm.dispose();
            return;
        }

        itemid = item.getItemId();

        if (banitem.indexOf(itemid) != -1) {
            cm.sendOk("#fs11#ไม่สามารถเสริมแกร่งไอเท็มประเภท Symbol ได้");
            cm.dispose();
            return;
        }


        // Enhance Level, Success Rate, Cost, All Stat, Att/Matt
        var notice = "";
        say = "";
        say += "#fs 11#ต้องการใช้ใบเสริมแกร่ง +" + TargetStar + " หรือไม่?\r\n\r\n\r\n ";
        cm.sendSimple(notice + say +
            //  "#L1##bใช้ " + ConvertNumber(keep) + " Meso เพื่อป้องกันไอเท็มถูกทำลายหรือระดับลดลง#l\r\n" +
            "#L2##bตกลง#k#k#l");
    } else if (status == 2) {
        if (re == 0) {
            choice = selection;
        }

        // Enhance Level, Success Rate, Cost, All Stat, Att/Matt
        say = "";
        say += "#fs 11#";
        say += "เสริมแกร่ง : #b+0 -> +" + TargetStar + "#k\r\n";
        say += "เมื่อสำเร็จ #bAll Stat + " + AllStat + " Att/Matt + " + AttMatk + "#k เพิ่มขึ้น\r\n";
        if (selection == 1 || choice == 1) {
            say += "\r\n";
        } else if (selection == 2 || choice == 2) {
            say += "";
        }
        say += "#r<ข้อมูลไอเท็ม>\r\n";
        say += "ไอเท็มที่จะเสริมแกร่ง : #i" + itemid + "# #z" + itemid + "#\r\n";
        say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
        say += "Att : " + item.getWatk() + "  |  Matt : " + item.getMatk() + "  | Star Force : " + item.getEnhance() + " ดาว\r\n";
        say += "All Stat : " + item.getAllStat() + "%  |  Total Damage : " + item.getTotalDamage() + "%  |  Boss Damage : " + item.getBossDamage() + "%\r\n";
        say += "ระดับการเสริมแกร่ง : " + getAddEnhance(item) + " ครั้ง#k\r\n\r\n\r\n";
        if (selection == 1 || choice == 1) {
            cm.sendYesNo(say + "ต้องการใช้ " + ConvertNumber(keep) + " Meso เพื่อป้องกันระดับลดลงหรือไม่?\r\n" +
                "ค่าป้องกันจะถูกหักไม่ว่าจะสำเร็จหรือล้มเหลว\r\n\r\n#bรวม Meso ที่ต้องใช้ : " + ConvertNumber(items[getAddEnhance(item) + 1][2] + keep) + " Meso#k#l");
        } else if (selection == 2 || choice == 2) {
            cm.sendYesNo(say + "กด #bใช่#k เพื่อใช้ใบเสริมแกร่ง");
        }
    } else if (status == 3) {
        if (choice == 1) {
            if (cm.getPlayer().getMeso() >= (items[getAddEnhance(item) + 1][2] + keep)) {
                cm.gainMeso(-(items[getAddEnhance(item) + 1][2] + keep));
            } else {
                cm.sendOk("Meso ไม่เพียงพอสำหรับค่าธรรมเนียม\r\nค่าธรรมเนียมที่ต้องใช้ : 100 ล้าน Meso");
                cm.dispose();
                return;
            }
        } else if (choice == 2) {
            if (cm.getPlayer().getMeso() >= items[getAddEnhance(item) + 1][2]) {
                //cm.gainMeso(-items[getAddEnhance(item) + 1][2]);
            } else {
                cm.sendOk("Meso ไม่เพียงพอสำหรับการเสริมแกร่ง");
                cm.dispose();
                return;
            }
        } else {
            cm.dispose();
            return;
        }
        if (item != null) {
            var rand = Math.ceil(Math.random() * 100);
            if (rand >= 0 && rand <= items[getAddEnhance(item) + 1][1]) {
                item.addStr(AllStat);
                item.addDex(AllStat);
                item.addInt(AllStat);
                item.addLuk(AllStat);
                item.addWatk(AttMatk);
                item.addMatk(AttMatk);
                item.setOwner(TargetStar + " Star");
                cm.getPlayer().forceReAddItem(item, Packages.objects.item.MapleInventoryType.EQUIP);
                cm.gainItem(ItemCode, -1);
                say = "";
                say += "#fs11##r<ข้อมูลไอเท็ม>\r\n";
                say += "ไอเท็มที่จะเสริมแกร่ง : #i" + itemid + "# #z" + itemid + "#\r\n";
                say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
                say += "Att : " + item.getWatk() + "  |  Matt : " + item.getMatk() + "  | Star Force : " + item.getEnhance() + " ดาว\r\n";
                say += "All Stat : " + item.getAllStat() + "%  |  Total Damage : " + item.getTotalDamage() + "%  |  Boss Damage : " + item.getBossDamage() + "%\r\n";
                say += "ระดับการเสริมแกร่ง : " + getAddEnhance(item) + " ครั้ง#k\r\n\r\n\r\n";

                // Log
                Packages.scripting.NPCConversationManager.writeLog("TextLog/zenia/Scroll/MesoEnhance20Star.log", "\r\nAccount : " + cm.getClient().getAccountName() + " (" + cm.getClient().getAccID() + ")\r\nName : " + cm.getPlayer().getName() + "\r\nUsed Item : Meso Enhance +20 Scroll (2430047)" + "\r\n\r\n", true);
                cm.addEnchantLog(1, item.getItemId(), item.getSerialNumberEquip(), 10, 0, "Meso Enhance Scroll " + getAddEnhance(item) + " Enhance (Account : " + cm.getClient().getAccountName() + ", Character : " + cm.getPlayer().getName() + ", Equip Info [" + item.toString() + "])");

                cm.sendOk("#fs11#การเสริมแกร่งสำเร็จ");
                cm.dispose();
                return;
            } else {
                if (choice == 1 || getAddEnhance(item) == 0) {
                    say = "";
                    say += "#fs11##r<ข้อมูลไอเท็ม>\r\n";
                    say += "ไอเท็มที่จะเสริมแกร่ง : #i" + itemid + "# #z" + itemid + "#\r\n";
                    say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
                    say += "Att : " + item.getWatk() + "  |  Matt : " + item.getMatk() + "  | Star Force : " + item.getEnhance() + " ดาว\r\n";
                    say += "All Stat : " + item.getAllStat() + "%  |  Total Damage : " + item.getTotalDamage() + "%  |  Boss Damage : " + item.getBossDamage() + "%\r\n";
                    say += "ระดับการเสริมแกร่ง : " + getAddEnhance(item) + " ครั้ง#k\r\n\r\n\r\n";
                    cm.sendYesNo("#rเสริมแกร่งล้มเหลว#k แต่ใช้ " + ConvertNumber(keep) + " Meso เพื่อป้องกัน #rระดับการเสริมแกร่งลดลง#k ไว้แล้ว\r\nหากต้องการเสริมแกร่งต่อ กด 'ใช่'\r\n\r\n" + say);
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
                    cm.getPlayer().forceReAddItem(item, Packages.objects.item.MapleInventoryType.EQUIP);
                    say = "";
                    say += "#r<ข้อมูลไอเท็ม>\r\n";
                    say += "ไอเท็มที่จะเสริมแกร่ง : #i" + itemid + "# #z" + itemid + "#\r\n";
                    say += "STR : " + item.getStr() + "  |  DEX : " + item.getDex() + "  |  INT : " + item.getInt() + "  |  LUK " + item.getLuk() + "\r\n";
                    say += "Att : " + item.getWatk() + "  |  Matt : " + item.getMatk() + "  | Star Force : " + item.getEnhance() + " ดาว\r\n";
                    say += "All Stat : " + item.getAllStat() + "%  |  Total Damage : " + item.getTotalDamage() + "%  |  Boss Damage : " + item.getBossDamage() + "%\r\n";
                    say += "ระดับการเสริมแกร่ง : " + getAddEnhance(item) + " ครั้ง#k\r\n\r\n\r\n";
                    cm.sendYesNo("#rเสริมแกร่งล้มเหลว#k ทำให้ #rระดับการเสริมแกร่งลดลง#k\r\nหากต้องการเสริมแกร่งต่อ กด 'ใช่'\r\n\r\n" + say);
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