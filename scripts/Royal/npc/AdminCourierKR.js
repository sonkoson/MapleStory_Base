importPackage(Packages.objects.item);
importPackage(Packages.constants);
importPackage(Packages.client);
importPackage(Packages.network);
importPackage(Packages.network.models);

importPackage(Packages.objects.item);
importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);
importPackage(java.awt);

var purple = "#fMap/MapHelper.img/weather/starPlanet/7#";
var blue = "#fMap/MapHelper.img/weather/starPlanet/8#";
var starBlue = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
var starYellow = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
var starWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
var starBrown = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
var starRed = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
var starBlack = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
var starPurple = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
var star = "#fUI/FarmUI.img/objectStatus/star/whole#"
var S = "#fUI/CashShop.img/CSEffect/today/0#"
var reward = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
var obtain = "#fUI/UIWindow2.img/QuestIcon/4/0#"
var color = "#fc0xFF6600CC#"
var black = "#fc0xFF000000#"
var enter = "\r\n"
var enter2 = "\r\n\r\n"

var Time = new Date();
var Year = Time.getFullYear() + "";
var Month = Time.getMonth() + 1 + "";
var Date = Time.getDate() + "";
if (Month < 10) {
    Month = "0" + Month;
}
if (Date < 10) {
    Date = "0" + Date;
}
var Today = (Year + "-" + Month + "-" + Date);

var status = 0;
var operation = -1;
var select = -1;
var type;
var ty;
var dd = true;
var yes = 1;
var invs = Array(1, 5);
var invv;
var selected;
var slot_1 = Array();
var slot_2 = Array();
var statsSel;
var sel;
var name;
var isban = false;

var banitem = [2437659, 3994410, 2434583, 2430027, 2040727, 2439302, 2437121, 2430218, 2023072, 2430488, 2433977, 2432305, 3994351, 2437122, 2023287, 4033114, 4310261, 2630442,];

var canitemCube = [
    5062009,
    5062010,
    5062500,
    5062005,
    5062503,
];

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            var ask = "#fs11#";
            ask += black + "ต้องการส่งไอเทมประเภทไหนทาง #rPost" + black + "?\r\n\r\n";
            ask += color + "? เลือกประเภทไอเทมที่จะส่งจ้ะ#k\r\n";
            ask += "#L1##b[Equip]" + black + " ส่งไอเทมสวมใส่\r\n";
            ask += "#L2##b[Use]" + black + " ส่งไอเทมใช้งาน\r\n";
            ask += "#L3##b[Setup]" + black + " ส่งไอเทมติดตั้ง\r\n";
            ask += "#L4##b[Etc]" + black + " ส่งไอเทมอื่นๆ\r\n";
            ask += "#L5##b[Cash]" + black + " ส่งไอเทม Cash\r\n";
            //ask += "#L55##b[Cube]" + black + " ส่งไอเทม Cube\r\n";
            ask += "#L6##b[Decoration]" + black + " ส่งไอเทมตกแต่ง\r\n";
            //ask += "\r\n#L999##b[Tradeable Items]" + black + " ดูรายการไอเทมที่แลกเปลี่ยนได้\r\n";
            cm.sendSimple(ask);

        } else if (cm.getPlayer().getReborns() < 0) {
            cm.sendOk("\r\n#bต้องจุติอย่างน้อย 10 ครั้งขึ้นไปถึงจะใช้งานได้จ้ะ\r\n");
            cm.dispose();

        } else if (status == 1) {
            operation = selection;
            if (operation == 1) {
                type = Packages.objects.item.MapleInventoryType.EQUIP;
                yes = 1;
            } else if (operation == 2) {
                type = Packages.objects.item.MapleInventoryType.USE;
                yes = 2;
            } else if (operation == 4) {
                type = Packages.objects.item.MapleInventoryType.SETUP;
                yes = 4;
            } else if (operation == 3) {
                type = Packages.objects.item.MapleInventoryType.ETC;
                yes = 3;
            } else if (operation == 5) {
                type = Packages.objects.item.MapleInventoryType.CASH;
                yes = 5;
            } else if (operation == 6) {
                type = Packages.objects.item.MapleInventoryType.EQUIP;
                yes = 6;
            } else if (operation == 55) {
                type = Packages.objects.item.MapleInventoryType.CASH;
                yes = 5;
            } else if (operation == 999) {
                var msg = starBlue + "#fs11##fc0xFF000000# รายการไอเทมที่สามารถส่งได้จ้ะ! #fs11#" + starBlue + enter + enter;
                msg += color + "#e<Cube>#n\r\n";
                for (i = 0; i < canitemCube.length; i++) {
                    msg += black + "#i" + canitemCube[i] + "##z" + canitemCube[i] + "##k" + enter;
                }
                cm.sendOk(msg);
                cm.dispose();
            }
            if (selection >= 1 && selection <= 6 || selection == 55) {
                cm.sendGetText("#fs11#" + black + "กรุณากรอกชื่อตัวละครของผู้รับจ้ะ#r\r\n\r\nผู้รับต้องออนไลน์อยู่ในชาแนลเดียวกัน\nหากส่งไอเทมที่มีอยู่แล้ว ไอเทมอาจสูญหายได้ และทางทีมงานจะไม่รับผิดชอบหากเกิดข้อผิดพลาดจากการใช้งาน");
            } else if (selection == 7) {
                cm.sendOk("#fs11#ระบบส่งของสามารถส่งให้ใครก็ได้ไม่จำกัดชนิดหรือออพชั่น โดยต้องใช้เงิน 500 ล้าน Meso และผู้รับต้องออนไลน์ในชาแนลเดียวกัน\nทางทีมงานจะไม่รับผิดชอบความเสียหายที่เกิดจากการใช้งานผิดพลาด");
                cm.dispose();
            }
        } else if (status == 2) {
            if (operation == 1) {
                type = Packages.objects.item.MapleInventoryType.EQUIP;
            } else if (operation == 2) {
                type = Packages.objects.item.MapleInventoryType.USE;
            } else if (operation == 3) {
                type = Packages.objects.item.MapleInventoryType.SETUP;
            } else if (operation == 4) {
                type = Packages.objects.item.MapleInventoryType.ETC;
            } else if (operation == 5) {
                type = Packages.objects.item.MapleInventoryType.CASH;
            } else if (operation == 6) {
                type = Packages.objects.item.MapleInventoryType.EQUIP;
            } else if (operation == 55) {
                type = Packages.objects.item.MapleInventoryType.CASH;
            }
            // Logic issue: "item" declared but unused here?
            var item = cm.getChar().getInventory(type);
            var text = cm.getText();
            var conn = Packages.network.game.GameServer.getInstance(cm.getClient().getChannel()).getPlayerStorage().getCharacterByName(text);
            if (conn == null) {
                cm.sendOk("#fs11#ผู้เล่นไม่ได้ออนไลน์ หรืออยู่คนละชาแนล หรือชื่อตัวละครไม่ถูกต้องจ้ะ");
                cm.dispose();
            } else if (!cm.getPlayer().isGM() && !conn.isGM()) {
                cm.sendOk("#fs11#" + color + "สามารถส่งของให้ GM ได้เท่านั้นจ้ะ");
                cm.dispose();
            } else {
                var ok = false;
                var selStr = "#fs11##b" + conn.getName() + black + " ต้องการส่งไอเทมชิ้นไหนให้ทางนั้นจ๊ะ?\r\n";
                for (var x = 1; x < 2; x++) {
                    var inv = cm.getInventory(yes);
                    for (var i = 0; i <= cm.getInventory(yes).getSlotLimit(); i++) {
                        if (x == 0) {
                            slot_1.push(i);
                        } else {
                            slot_2.push(i);
                        }
                        var it = inv.getItem(i);
                        if (it == null) {
                            continue;
                        }
                        var itemid = it.getItemId();
                        ok = true;
                        if ((itemid >= 1140000 && itemid <= 1143999) && !cm.getPlayer().isGM()) {
                            continue;
                        }
                        // Selecting item to send
                        if (operation == 55) {
                            for (ii = 0; ii < canitemCube.length; ii++) {
                                if (cm.getInventory(yes).getItem(i).getItemId() == canitemCube[ii]) {
                                    selStr += black + "#L" + (yes * 1000 + i) + "##i" + itemid + "##z" + itemid + "# #r[ช่อง " + i + "]\#l\r\n";
                                }
                            }
                        } else {
                            selStr += black + "#L" + (yes * 1000 + i) + "##i" + itemid + "##z" + itemid + "# #r[ช่อง " + i + "]#l\r\n";
                        }
                    }
                }
                if (!ok) {
                    cm.sendOk("#fs11#ดูเหมือนไม่มีไอเทมที่จะส่งได้เลยนะ?");
                    cm.dispose();
                    return;
                }
                cm.sendSimple(selStr + "#k");
            }
        } else if (status == 3) {
            sel = selection;
            if (operation == 1) {
                type = Packages.objects.item.MapleInventoryType.EQUIP;
            } else if (operation == 2) {
                type = Packages.objects.item.MapleInventoryType.USE;
            } else if (operation == 3) {
                type = Packages.objects.item.MapleInventoryType.SETUP;
            } else if (operation == 4) {
                type = Packages.objects.item.MapleInventoryType.ETC;
            } else if (operation == 5) {
                type = Packages.objects.item.MapleInventoryType.CASH;
            } else if (operation == 6) {
                type = Packages.objects.item.MapleInventoryType.CASH_EQUIP;
            } else if (operation == 55) {
                type = Packages.objects.item.MapleInventoryType.CASH;
            }
            var item = cm.getChar().getInventory(type).getItem(selection % 1000).copy();
            var text = cm.getText();
            invv = selection / 1000;
            var inzz = cm.getInventory(invv);
            selected = selection % 1000;
            if (invv == invs[0]) {
                statsSel = inzz.getItem(slot_1[selected]);
            } else {
                statsSel = inzz.getItem(slot_2[selected]);
            }
            if (statsSel == null) {
                cm.sendOk("#fs11#เกิดข้อผิดพลาด กรุณาแจ้ง GM จ้ะ");
                cm.dispose();
                return;
            }
            var text = cm.getText();
            var con = Packages.network.game.GameServer.getInstance(cm.getClient().getChannel()).isMyChannelConnected(text);
            var conn = Packages.network.game.GameServer.getInstance(cm.getClient().getChannel()).getPlayerStorage().getCharacterByName(text);

            for (a = 0; a < banitem.length; a++) {
                if (banitem[a] == item.getItemId()) {
                    isban = true;
                    continue;
                }
            }

            if (conn == null) {
                cm.sendOk("#fs11#ผู้เล่นปลายทางล็อกออฟหรือเปลี่ยนชาแนลไปแล้ว\r\n\r\n#rยกเลิกการส่งจ้ะ");
                cm.dispose();
                return;
            }

            if (item.getQuantity() == 1) { // Single item logic
                if (cm.haveItem(5330000, 0)) { // Check for "Quick Delivery Ticket" (5330000) or similar? 
                    // Original checked: cm.haveItem(5330000, 0) -> Returns true if quantity >= 0? 
                    // Or maybe check if exists? 
                    // Usually haveItem(id, count) checks if user has >= count.
                    // If count is 0, it might always return true? Or check if item exists in data?
                    // Original code: if(cm.haveItem(5330000, 0)) ...
                    // If this item is required to use the system, the prompt for it is missing in translation?
                    // Actually, status 4 checks for it again with specific rejection message: "Quick Delivery Ticket is required."
                    // But here, if quantity 1, it processes immediately.

                    if (Packages.constants.GameConstants.isPet(item.getItemId()) == false) {
                        if (cm.getPlayer().getName() != text) {
                            if (isban) {
                                cm.sendOk("ไอเทมนี้ถูกระงับการส่งจ้ะ");
                                cm.dispose();
                                return;
                            }
                            Packages.objects.item.MapleInventoryManipulator.removeFromSlot(cm.getC(), type, selection % 1000, item.getQuantity(), true);
                            Packages.objects.item.MapleInventoryManipulator.addFromDrop(conn.getClient(), item, true);
                            //cm.gainItem(5330000, -1); // Commented out in original
                            WriteLog(cm.getPlayer().getName(), text, item.getItemId(), item.getQuantity());
                            cm.sendOk("#fs11#ส่งไอเทมให้คุณ " + text + " เรียบร้อยแล้วจ้ะ");
                            cm.dispose();
                        } else {
                            cm.sendOk("#fs11#ส่งของให้ตัวเองไม่ได้นะ");
                            cm.dispose();
                        }
                    } else {
                        cm.sendOk("#fs11#สัตว์เลี้ยงส่งทางนี้ไม่ได้จ้ะ สงสารน้อง");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("คุณไม่มีสิทธิ์ใช้งานจ้ะ");
                    cm.dispose();
                }
            } else { // Multiple items logic
                if (!isban) {
                    cm.sendGetNumber("#fs11#ต้องการส่งกี่ชิ้นจ๊ะ?\r\nจำนวน #i" + item.getItemId() + "# #b(#t" + item.getItemId() + "#)#k ที่มี: #b" + item.getQuantity() + "#k", 1, 1, item.getQuantity());
                } else {
                    cm.sendOk("ไอเทมนี้ถูกระงับการส่งจ้ะ");
                    cm.dispose();
                }
            }
            name = text;
        } else if (status == 4) {

            if (isban) {
                cm.sendOk("เกิดข้อผิดพลาด");
                cm.dispose();
                return;
            }
            var sele = selection % 1000;
            var quan = cm.getText();
            if (operation == 1) {
                type = Packages.objects.item.MapleInventoryType.EQUIP;
            } else if (operation == 2) {
                type = Packages.objects.item.MapleInventoryType.USE;
            } else if (operation == 3) {
                type = Packages.objects.item.MapleInventoryType.SETUP;
            } else if (operation == 4) {
                type = Packages.objects.item.MapleInventoryType.ETC;
            } else if (operation == 5) {
                type = Packages.objects.item.MapleInventoryType.CASH;
            } else if (operation == 6) {
                type = Packages.objects.item.MapleInventoryType.CASH_EQUIP;
            } else if (operation == 55) {
                type = Packages.objects.item.MapleInventoryType.CASH;
            }
            var item = cm.getChar().getInventory(type).getItem(sel % 1000).copy();
            var text = cm.getText();
            invv = sel / 1000;
            var inzz = cm.getInventory(invv);
            selected = sel % 1000;
            if (invv == invs[0]) {
                statsSel = inzz.getItem(slot_1[selected]);
            } else {
                statsSel = inzz.getItem(slot_2[selected]);
            }
            if (statsSel == null) {
                cm.sendOk("เกิดข้อผิดพลาด กรุณาแจ้ง GM จ้ะ");
                cm.dispose();
                return;
            }

            var text = selection; // This holds the quantity entered in previous step (sendGetNumber)
            var con = Packages.network.game.GameServer.getInstance(cm.getClient().getChannel()).isMyChannelConnected(name);
            var conn = Packages.network.game.GameServer.getInstance(cm.getClient().getChannel()).getPlayerStorage().getCharacterByName(name);

            if (conn == null) {
                cm.sendOk("#fs11#ผู้เล่นปลายทางล็อกออฟหรือเปลี่ยนชาแนลไปแล้ว\r\n\r\n#rยกเลิกการส่งจ้ะ");
                cm.dispose();
                return;
            }

            if (item.getQuantity() >= text) {
                if (cm.haveItem(5330000, 0)) { // Security check again
                    if (cm.getPlayer().getName() != name) {
                        item.setQuantity(text);
                        Packages.objects.item.MapleInventoryManipulator.removeFromSlot(cm.getC(), type, sel % 1000, item.getQuantity(), true);
                        Packages.objects.item.MapleInventoryManipulator.addFromDrop(conn.getClient(), item, true);
                        //cm.gainItem(5330000,-1);
                        WriteLog(cm.getPlayer().getName(), name, item.getItemId(), item.getQuantity());
                        cm.sendOk("#fs11#ส่งไอเทมให้คุณ " + name + " เรียบร้อยแล้วจ้ะ");
                        cm.dispose();
                    } else {
                        cm.sendOk("ส่งของให้ตัวเองไม่ได้นะ");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("ต้องมี Quick Delivery Ticket ถึงจะส่งของได้จ้ะ");
                    cm.dispose();
                }
            } else {
                cm.sendOk("ใส่จำนวนมากกว่าที่มีนะจ๊ะ");
                cm.dispose();
            }
        }
    }
}

function WriteLog(cname, vname, itemid, qty) {
    Packages.scripting.NPCConversationManager.writeLog("TextLog/zenia/GMParcel/" + Today + ".log", "\r\nSender Account : " + cm.getClient().getAccountName() + " (" + cm.getClient().getAccID() + ")\r\nSender Name : " + cm.getPlayer().getName() + "\r\nReceiver Name : " + vname + "\r\nItem Sent : " + cm.getItemName(itemid) + "[" + itemid + "] (" + qty + " pcs)" + "\r\n\r\n", true);
}




