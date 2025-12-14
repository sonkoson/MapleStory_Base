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

purple = "#fMap/MapHelper.img/weather/starPlanet/7#";
blue = "#fMap/MapHelper.img/weather/starPlanet/8#";
starBlue = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
starYellow = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
starWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
starBrown = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
starRed = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
starBlack = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
starPurple = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
star = "#fUI/FarmUI.img/objectStatus/star/whole#"
S = "#fUI/CashShop.img/CSEffect/today/0#"
reward = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
obtain = "#fUI/UIWindow2.img/QuestIcon/4/0#"
color = "#fc0xFF6600CC#"
black = "#fc0xFF000000#"
enter = "\r\n"
enter2 = "\r\n\r\n"

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
            ask += black + "หมวดหมู่ไอเท็มที่คุณต้องการส่งทาง #rพัสดุด่วน" + black + " คือ?\r\n\r\n";
            ask += color + "▶ กรุณาเลือกประเภทของไอเท็มที่ต้องการส่ง#k\r\n";
            ask += "#L1##b[Equip]" + black + " ส่งไอเท็มสวมใส่\r\n";
            ask += "#L2##b[Use]" + black + " ส่งไอเท็มใช้งาน\r\n";
            ask += "#L3##b[Setup]" + black + " ส่งไอเท็มติดตั้ง\r\n";
            ask += "#L4##b[Etc]" + black + " ส่งไอเท็มอื่นๆ\r\n";
            ask += "#L5##b[Cash]" + black + " ส่งไอเท็มแคช\r\n";
            //ask += "#L55##b[Cube]" + black + " ส่งไอเท็มคิวบ์\r\n";
            ask += "#L6##b[Decoration]" + black + " ส่งไอเท็มตกแต่ง\r\n";
            //ask += "\r\n#L999##b[Tradeable Items]" + black + " ดูรายการไอเท็ม\r\n";
            cm.sendSimple(ask);

        } else if (cm.getPlayer().getReborns() < 0) {
            cm.sendOk("\r\n#bใช้งานได้เฉพาะผู้เล่นที่จุติรวม 10 ครั้งขึ้นไปเท่านั้น\r\n");
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
                var msg = starBlue + "#fs11##fc0xFF000000# รายการไอเท็มที่สามารถส่งได้! #fs11#" + starBlue + enter + enter;
                msg += color + "#e<Cube>#n\r\n";
                for (i = 0; i < canitemCube.length; i++) {
                    msg += black + "#i" + canitemCube[i] + "##z" + canitemCube[i] + "##k" + enter;
                }
                cm.sendOk(msg);
                cm.dispose();
            }
            if (selection >= 1 && selection <= 6 || selection == 55) {
                cm.sendGetText("#fs11#" + black + "กรุณากรอกชื่อตัวละครของผู้รับพัสดุ#r\r\n\r\nผู้รับต้องออนไลน์อยู่ในแชนแนลเดียวกัน หากส่งไอเท็มที่มีเพียงชิ้นเดียว (Unique) แล้วผู้รับมีอยู่แล้ว ไอเท็มอาจหายไปได้ ทางทีมงานจะไม่รับผิดชอบปัญหาที่เกิดจากการใช้งานผิดพลาด,");
            } else if (selection == 7) {
                cm.sendOk("#fs11#ระบบรับส่งพัสดุ เป็นระบบที่สามารถส่งไอเท็มให้ใครก็ได้โดยไม่จำกัดชนิดหรือออปชั่น ในการส่งพัสดุต้องใช้เงิน 500 ล้านเมโซ และต้องอยู่ในแชนแนลเดียวกัน ทางทีมงานจะไม่รับผิดชอบปัญหาที่เกิดจากการใช้งานผิดพลาด");
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
            var item = cm.getChar().getInventory(type);
            var text = cm.getText();
            var conn = Packages.network.game.GameServer.getInstance(cm.getClient().getChannel()).getPlayerStorage().getCharacterByName(text);
            if (conn == null) {
                cm.sendOk("#fs11#ผู้เล่นไม่ออนไลน์หรืออยู่คนละแชนแนล หรือชื่อตัวละครไม่ถูกต้อง");
                cm.dispose();
            } else if (!cm.getPlayer().isGM() && !conn.isGM()) {
                cm.sendOk("#fs11#" + color + "ส่งพัสดุให้ GM เท่านั้น");
                cm.dispose();
            } else {
                var ok = false;
                var selStr = "#fs11##bต้องการส่งไอเท็มชิ้นไหนให้คุณ " + conn.getName() + "?" + black + "\r\n";
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
                        //Here is where the package item is decided
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
                    cm.sendOk("#fs11#ดูเหมือนคุณจะไม่มีไอเท็มที่จะส่งนะ?");
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
                cm.sendOk("#fs11#เกิดข้อผิดพลาด กรุณาแจ้งผู้ดูแลระบบ");
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

            if (conn == null) { // Prevent item loss if recipient logs out/changes channel after name entry
                cm.sendOk("#fs11#ผู้รับออกจากระบบหรือเปลี่ยนแชนแนล\r\nr\n#rยกเลิกการส่งพัสดุ");
                cm.dispose();
                return;
            }

            if (item.getQuantity() == 1) {
                if (cm.haveItem(5330000, 0)) {
                    if (Packages.constants.GameConstants.isPet(item.getItemId()) == false) {
                        if (cm.getPlayer().getName() != text) {
                            //if (!conn.canHold(item.getItemId())) {
                            //	cm.sendOk("#fs11#ช่องเก็บของของผู้รับไม่เพียงพอ");
                            //	cm.dispose();
                            //	return;
                            //}
                            if (isban) {
                                cm.sendOk("ไอเท็มนี้ถูกระงับการใช้งาน");
                                cm.dispose();
                                return;
                            }
                            Packages.objects.item.MapleInventoryManipulator.removeFromSlot(cm.getC(), type, selection % 1000, item.getQuantity(), true);
                            Packages.objects.item.MapleInventoryManipulator.addFromDrop(conn.getClient(), item, true);
                            //cm.gainItem(5330000, -1);
                            WriteLog(cm.getPlayer().getName(), text, item.getItemId(), item.getQuantity());
                            cm.sendOk("#fs11#ส่งพัสดุให้คุณ " + text + " เรียบร้อยแล้ว");
                            cm.dispose();
                        } else {
                            cm.sendOk("#fs11#คุณไม่สามารถส่งพัสดุให้ตัวเองได้");
                            cm.dispose();
                        }
                    } else {
                        cm.sendOk("#fs11#ไม่สามารถส่งสัตว์เลี้ยงทางพัสดุได้ หยุดทรมานสัตว์เถอะ");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("คุณไม่มีสิทธิ์ใช้งาน");
                    cm.dispose();
                }
            } else {
                if (!isban) {
                    cm.sendGetNumber("#fs11#ต้องการส่งกี่ชิ้น?\r\nจำนวน #i" + item.getItemId() + "# #b(#t" + item.getItemId() + "#)#k ที่มี: #b" + item.getQuantity() + "#k", 1, 1, item.getQuantity());
                } else {
                    cm.sendOk("ไอเท็มนี้ถูกระงับการใช้งาน");
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
                cm.sendOk("เกิดข้อผิดพลาด กรุณาแจ้งผู้ดูแลระบบ");
                cm.dispose();
                return;
            }

            var text = selection;
            var con = Packages.network.game.GameServer.getInstance(cm.getClient().getChannel()).isMyChannelConnected(name);
            var conn = Packages.network.game.GameServer.getInstance(cm.getClient().getChannel()).getPlayerStorage().getCharacterByName(name);

            if (conn == null) { // Prevent item loss if recipient logs out/changes channel after name entry
                cm.sendOk("#fs11#ผู้รับออกจากระบบหรือเปลี่ยนแชนแนล\r\nr\n#rยกเลิกการส่งพัสดุ");
                cm.dispose();
                return;
            }

            if (item.getQuantity() >= text) {
                if (cm.haveItem(5330000, 0)) {
                    if (cm.getPlayer().getName() != name) {
                        item.setQuantity(text);
                        Packages.objects.item.MapleInventoryManipulator.removeFromSlot(cm.getC(), type, sel % 1000, item.getQuantity(), true);
                        Packages.objects.item.MapleInventoryManipulator.addFromDrop(conn.getClient(), item, true);
                        //cm.gainItem(5330000,-1);
                        WriteLog(cm.getPlayer().getName(), name, item.getItemId(), item.getQuantity());
                        cm.sendOk("#fs11#ส่งพัสดุให้คุณ " + name + " เรียบร้อยแล้ว");
                        cm.dispose();
                    } else {
                        cm.sendOk("คุณไม่สามารถส่งพัสดุให้ตัวเองได้");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("คุณต้องมีตั๋วส่งพัสดุด่วนเพื่อส่งไอเท็ม");
                    cm.dispose();
                }
            } else {
                cm.sendOk("คุณใส่จำนวนมากกว่าที่คุณมี");
                cm.dispose();
            }
        }
    }
}

function WriteLog(cname, vname, itemid, qty) {
    Packages.scripting.NPCConversationManager.writeLog("TextLog/zenia/AdminCourier/" + Today + ".log", "\r\nSender Account : " + cm.getClient().getAccountName() + " (" + cm.getClient().getAccID() + ")\r\nSender Nickname : " + cm.getPlayer().getName() + "\r\nRecipient Nickname : " + vname + "\r\nGifted Item : " + cm.getItemName(itemid) + "[" + itemid + "] (" + qty + " pcs)" + "\r\n\r\n", true);
}
