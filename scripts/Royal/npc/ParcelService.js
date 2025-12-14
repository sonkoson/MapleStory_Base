importPackage(Packages.objects.item);
importPackage(Packages.server);
importPackage(Packages.constants);
importPackage(Packages.client);
importPackage(Packages.network);
importPackage(Packages.network.models);
importPackage(Packages.tools);

importPackage(Packages.objects.item);
importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);
importPackage(java.awt);

purple = "#fMap/MapHelper.img/weather/starPlanet/7#";
blue = "#fMap/MapHelper.img/weather/starPlanet/8#";
blueStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
yellowStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
whiteStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
brownStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
redStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
blackStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
purpleStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
star = "#fUI/FarmUI.img/objectStatus/star/whole#"
S = "#fUI/CashShop.img/CSEffect/today/0#"
reward = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
gain = "#fUI/UIWindow2.img/QuestIcon/4/0#"
color = "#fc0xFF6600CC#"
black = "#fc0xFF000000#"
enter = "\r\n"
enter2 = "\r\n\r\n"

var Time = new Date();
var Year = Time.getFullYear() + "";
var Month = Time.getMonth() + 1 + "";
var Dates = Time.getDate() + "";
if (Month < 10) {
    Month = "0" + Month;
}
if (Dates < 10) {
    Dates = "0" + Dates;
}
var Today = (Year + "-" + Month + "-" + Dates);

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
            var today = new Date();
            var todayFormatted = today.toISOString().split('T')[0];
            var todayDate = new Date(todayFormatted);

            var timestamp = cm.getClient().getCreated();
            var timecheck = todayDate - timestamp.getTime();

            if (timecheck <= (1000 * 60 * 60 * 24 * 2)) {
                cm.sendOk("#fs11#ใช้งานได้เฉพาะบัญชีที่สร้างมาแล้วเกิน 3 วันเท่านั้น");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getAccountTotalLevel() < 8075) {
                //     cm.sendOk("#fs11##fc0xFF000000#계정 내 통합 레벨 8075 이상만 이용할 수 있습니다.");
                //   cm.dispose();
                // return;
            }
            var ask = "#fs11#";
            ask += black + "คุณต้องการส่งไอเทมชนิดใดทาง #rParcel" + black + "?\r\n\r\n";
            ask += color + "▶ โปรดเลือกชนิดของไอเทมที่ต้องการส่ง#k\r\n";
            ask += "#L55##b[Cube]" + black + " ส่งไอเทม\r\n";
            ask += "\r\n#L999##b[Tradeable Item]" + black + " ดูรายการ\r\n";
            cm.sendSimple(ask);

        } else if (cm.getPlayer().getReborns() < 0) {
            cm.sendOk("\r\n#bใช้งานได้เฉพาะผู้เล่นที่ Rebirth 10 ครั้งขึ้นไปเท่านั้น\r\n");
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
                type = Packages.objects.item.MapleInventoryType.CASH_EQUIP;
                yes = 6;
            } else if (operation == 55) {
                type = Packages.objects.item.MapleInventoryType.CASH;
                yes = 5;
            } else if (operation == 999) {
                var msg = blueStar + "#fs11##fc0xFF000000# นี่คือรายการไอเทมที่คุณสามารถส่งได้! #fs11#" + blueStar + enter + enter;
                msg += color + "#e< Cube >#n\r\n";
                for (i = 0; i < canitemCube.length; i++) {
                    msg += black + "#i" + canitemCube[i] + "##z" + canitemCube[i] + "##k" + enter;
                }
                cm.sendOk(msg);
                cm.dispose();
            }
            if (selection >= 1 && selection <= 6 || selection == 55) {
                cm.sendGetText("#fs11#" + black + "กรุณากรอกชื่อตัวละครของผู้รับพัสดุ#r\r\n\r\nผู้รับจะต้องออนไลน์อยู่ในแชแนลเดียวกัน\r\nไอเทมแบบ Unique จะหายไปหากผู้รับมีไอเทมนั้นอยู่แล้ว ทางทีมงานจะไม่รับผิดชอบต่อปัญหาที่เกิดจากการใช้งานผิดพลาด,");
            } else if (selection == 7) {
                cm.sendOk("#fs11#ระบบ Parcel ช่วยให้คุณส่งไอเทมชนิดใดก็ได้โดยไม่จำกัด Option ในการส่งต้องใช้ 500 ล้าน Meso และผู้รับต้องออนไลน์ในแชแนลเดียวกัน ทางทีมงานจะไม่รับผิดชอบต่อปัญหาที่เกิดจากการใช้งานผิดพลาด");
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
                type = Packages.objects.item.MapleInventoryType.CASH_EQUIP;
            } else if (operation == 55) {
                type = Packages.objects.item.MapleInventoryType.CASH;
            }
            var item = cm.getChar().getInventory(type);
            var text = cm.getText();
            var conn = Packages.network.game.GameServer.getInstance(cm.getClient().getChannel()).getPlayerStorage().getCharacterByName(text);
            if (conn == null) {
                cm.sendOk("#fs11#ผู้เล่นไม่ได้ออนไลน์หรืออยู่คนละแชแนล หรือชื่อตัวละครอาจไม่มีอยู่จริง");
                cm.dispose();
            } else {
                var ok = false;
                var selStr = "#fs11##b" + conn.getName() + black + " คุณต้องการส่งไอเทมใดให้กับคุณ?\r\n";
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
                        // Where to choose parcel items
                        if (operation == 55) {
                            for (ii = 0; ii < canitemCube.length; ii++) {
                                if (cm.getInventory(yes).getItem(i).getItemId() == canitemCube[ii]) {
                                    selStr += black + "#L" + (yes * 1000 + i) + "##i" + itemid + "##z" + itemid + "# #r[" + i + "Slot]\#l\r\n";
                                }
                            }
                        } else {
                            selStr += black + "#L" + (yes * 1000 + i) + "##i" + itemid + "##z" + itemid + "##l\r\n";
                        }
                    }
                }
                if (!ok) {
                    cm.sendOk("#fs11#ดูเหมือนไม่มีไอเทมที่จะส่ง?");
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
                type = Packages.objects.item.MapleInventoryType.DECORATION;
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
                cm.sendOk("#fs11#เกิดข้อผิดพลาด โปรดแจ้ง GM");
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
                cm.sendOk("#fs11#ผู้รับออกจากเกมหรือย้ายแชแนล\r\nr\n#rยกเลิกการส่งพัสดุ");
                cm.dispose();
                return;
            }
            if (item.getQuantity() == 1) {

                if (cm.haveItem(5330000, 0)) {
                    if (Packages.constants.GameConstants.isPet(item.getItemId()) == false) {
                        if (cm.getPlayer().getName() != text) {
                            //if (!conn.canHold(item.getItemId())) {
                            //	cm.sendOk("#fs11#Recipient inventory is full.");
                            //	cm.dispose();
                            //	return;
                            //}
                            if (isban) {
                                cm.sendOk("ไอเทมนี้ถูกระงับการใช้งาน");
                                cm.dispose();
                                return;
                            }
                            Packages.objects.item.MapleInventoryManipulator.removeFromSlot(cm.getC(), type, selection % 1000, item.getQuantity(), true);
                            Packages.objects.item.MapleInventoryManipulator.addFromDrop(conn.getClient(), item, true);
                            //cm.gainItem(5330000, -1);
                            WriteLog(cm.getPlayer().getName(), text, item.getItemId(), item.getQuantity());
                            cm.sendOk("#fs11#ส่งพัสดุให้กับคุณ " + text + " เรียบร้อยแล้ว");
                            cm.dispose();
                        } else {
                            cm.sendOk("#fs11#ไม่สามารถส่งพัสดุให้ตัวเองได้");
                            cm.dispose();
                        }
                    } else {
                        cm.sendOk("#fs11#ไม่สามารถส่งสัตว์เลี้ยงทางพัสดุได้ โปรดหยุดทารุณกรรมสัตว์");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("คุณไม่มีสิทธิ์ใช้งาน");
                    cm.dispose();
                }
            } else {
                if (!isban) {
                    cm.sendGetNumber("#fs11#คุณต้องการส่งจำนวนเท่าไหร่?\r\nจำนวน #i" + item.getItemId() + "# #b(#t" + item.getItemId() + "#)#k ที่มี: #b" + item.getQuantity() + "#k", 1, 1, item.getQuantity());
                } else {
                    cm.sendOk("ไอเทมนี้ถูกระงับการใช้งาน");
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
                type = Packages.objects.item.MapleInventoryType.DECORATION;
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
                cm.sendOk("เกิดข้อผิดพลาด โปรดแจ้ง GM");
                cm.dispose();
                return;
            }

            var text = selection;
            var con = Packages.network.game.GameServer.getInstance(cm.getClient().getChannel()).isMyChannelConnected(name);
            var conn = Packages.network.game.GameServer.getInstance(cm.getClient().getChannel()).getPlayerStorage().getCharacterByName(name);

            if (conn == null) { // Prevent item loss if recipient logs out/changes channel after name entry
                cm.sendOk("#fs11#ผู้รับออกจากเกมหรือย้ายแชแนล\r\nr\n#rยกเลิกการส่งพัสดุ");
                cm.dispose();
                return;
            }
            if (item.getQuantity() >= text && text > 0) {
                if (cm.haveItem(5330000, 0)) {
                    if (cm.getPlayer().getName() != name) {
                        item.setQuantity(text);
                        Packages.objects.item.MapleInventoryManipulator.removeFromSlot(cm.getC(), type, sel % 1000, item.getQuantity(), true);
                        Packages.objects.item.MapleInventoryManipulator.addFromDrop(conn.getClient(), item, true);
                        var itemName = MapleItemInformationProvider.getInstance()
                            .getName(item.getItemId());
                        conn.dropMessage(6,
                            cm.getPlayer().getName()
                            + " has sent you "
                            + itemName
                            + " (" + item.getQuantity() + ") items!");
                        //cm.gainItem(5330000,-1);
                        WriteLog(cm.getPlayer().getName(), name, item.getItemId(), item.getQuantity());
                        cm.sendOk("#fs11#ส่งพัสดุให้กับคุณ " + name + " เรียบร้อยแล้ว");
                        cm.dispose();
                    } else {
                        cm.sendOk("ไม่สามารถส่งพัสดุให้ตัวเองได้");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("คุณต้องมีตั๋วส่งด่วนในการส่งไอเทม");
                    cm.dispose();
                }
            } else {
                cm.sendOk("คุณกรอกจำนวนมากกว่าที่มีอยู่");
                cm.dispose();
            }
        }
    }
}

function WriteLog(cname, vname, itemid, qty) {
    Packages.scripting.NPCConversationManager.writeLog("TextLog/zenia/Parcel/" + Today + ".log", "\r\nSenderAccount : " + cm.getClient().getAccountName() + " (" + cm.getClient().getAccID() + ")\r\nSenderName : " + cm.getPlayer().getName() + "\r\nRecipientName : " + vname + "\r\nGiftedItem : " + cm.getItemName(itemid) + "[" + itemid + "] (" + qty + "qty)" + "\r\n\r\n", true);
}
