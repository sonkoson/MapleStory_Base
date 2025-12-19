var enter = "\r\n";
var seld = -1;
importPackage(Packages.server);
importPackage(Packages.scripting);
importPackage(Packages.objects.item);

heart = "#fs11##fMap/MapHelper.img/minimap/match#"
blue = "#fc0xFF6B66FF#"
square = "#fMap/MapHelper.img/minimap/rune#"
black = "#fc0xFF191919#";
var sssss = 0;
var coin = 4033213;    // Currency
var price = 1;        // Price
var allstat = 4, atk = 2;
var failChance = 50;          // Failure rate
var maxEnhance = 100;   // Max enhancement level

function getAddEnhance(item) {
    var owner = item.getOwner();
    // Allow 1-3 digit numbers
    var m = owner.match(/^(\d{1,3})Lv$/);
    if (!m) return 0;
    var lvl = parseInt(m[1], 10);
    return (lvl >= 1 && lvl <= 100) ? lvl : 0;
}

function start() {
    status = -1;
    action(1, 0, 0);
}
function action(mode, type, selection) {
    if (mode == 1) {
        if (status == 1) {
            if (sssss == 1) {
                status++;
            }
        }
        status++;
        if (status == 4 && selection == 0 && sssss == 0) {
            status = 1;
        }
    } else {
        if (status == 2) {
            cm.sendOkS("เข้าใจแล้ว หากเปลี่ยนใจให้กลับมาหาฉันอีกนะ", 4, 9401232);
        }
        cm.dispose();
        return;
    }
    if (status == 0) {
        msg = "#fs11#" + black + "สวัสดี #b#h ##k! \r\n";
        msg += "#fs11#" + black + "รู้ไหมว่าไอเทมแคชก็สามารถเพิ่มพลังได้เช่นกัน?  \r\n";
        msg += "#fs11#" + black + "ระบบเพิ่มพลังแคชใช้ #i4033213##b#t4033213##d\r\nเพื่อเพิ่มพลังให้กับไอเทมตกแต่ง";
        msg += "#fs11#" + black + " \r\n\r\n";
        msg += "#fs11#" + black + "แต่ละครั้งจะเพิ่ม #rAll Stats 4 และ ATT/MATT 2#d \r\n";
        msg += "#fs11#" + black + "#b#z4033213##k 1 ชิ้นสามารถเพิ่มพลังได้สูงสุด 100 ขั้น#r\r\n\r\nไม่สามารถใช้กับอุปกรณ์สัตว์เลี้ยงและบางไอเทม#d\r\n"
        msg += "#fc0xFFD5D5D5#─────────────────────────────#k\r\n";
        msg += "#L0##fc0xFF6B66FF#ไอเทมแฟชั่น#k" + black + " เพิ่มพลัง #r[อัตราสำเร็จ 50%]#k\r\n"
        //msg += "#L1#ฉันต้องการคำอธิบาย"
        cm.sendSimple(msg);
    } else if (status == 1) {
        sssss = selection;
        if (selection == 0) {
            var txt = "#fs11#" + black + "ต้องการเพิ่มพลังไอเทมใด?\r\nอัตราสำเร็จ #b50%#k\r\n";
            txt += "#fc0xFFD5D5D5#─────────────────────────────#k\r\n";
            for (i = 0; i < cm.getInventory(6).getSlotLimit(); i++) {
                if (cm.getInventory(6).getItem(i) != null) {
                    if (cm.isCash(cm.getInventory(6).getItem(i).getItemId())) {
                        txt += "#L" + i + "# #i" + cm.getInventory(6).getItem(i).getItemId() + "# #fc0xFF6B66FF##z" + cm.getInventory(6).getItem(i).getItemId() + "#\r\n";
                    }
                }
            }
            cm.sendSimple(txt);
        } else if (selection == 1) {
            cm.Entertuto(false, false);
        }
    } else if (status == 2) {
        items = selection;
        item = cm.getInventory(6).getItem(items);
        if (item == null) {
            cm.sendOk("เกิดข้อผิดพลาด กรุณาลองใหม่อีกครั้ง");
            cm.dispose();
            return;
        }
        cm.sendNext("#fs11#คุณแน่ใจหรือไม่ว่าต้องการเพิ่มพลัง #r#i" + item.getItemId() + ":# #z" + item.getItemId() + ":##k? ไม่สามารถย้อนกลับได้ โปรดเลือกอย่างระมัดระวัง");
    } else if (status == 3) {
        if (sssss == 1) {
            cm.sendScreenText("#fs27##fnNanumGothic Extrabold##fc0xFF6B66FF#เพิ่มพลังไอเทมแฟชั่น#k ให้ฉันอธิบายให้ฟัง", false);
        } else {
            if (!cm.haveItem(coin, price)) {
                cm.sendOk("#fs11#" + black + "วัสดุไม่เพียงพอ! ต้องใช้ #i4033213##r#t4033213# 1 ชิ้น#d");
                cm.dispose();
                return;
            }

            if (item == null) {
                cm.dispose();
                return;
            }
            if (item.getItemId() >= 1112000 && item.getItemId() <= 1112099 || item.getItemId() >= 1112800 && item.getItemId() <= 1112899) {
                cm.sendOk("#fs11#" + black + "แหวนคู่รักและแหวนมิตรภาพไม่สามารถเพิ่มพลังได้");
                cm.dispose();
                return;
            }
            var cur = getAddEnhance(item);
            if (cur >= maxEnhance) {
                cm.sendOk("#fs11#ไม่สามารถเพิ่มพลังได้อีก! ไอเทมหนึ่งชิ้นสามารถเพิ่มพลังได้สูงสุด #r"
                    + maxEnhance + " ขั้น#d เท่านั้น");
                cm.dispose();
                return;
            }
            if (item.getItemId() >= 1802000 && item.getItemId() <= 1802999) {
                cm.sendOk("#fs11#" + black + "อุปกรณ์สัตว์เลี้ยงไม่สามารถเพิ่มพลังได้");
                cm.dispose();
                return;
            }
            if (!cm.isCash(item.getItemId())) {
                cm.sendOk("#fs11#" + black + "ดูเหมือนจะเป็นไอเทมผิดปกติ");
                cm.dispose();
                return;
            }

            var suc = (Math.random() * 100) < (100 - failChance);

            if (!suc) {
                item.setStr(item.getStr() - 0);
                item.setDex(item.getDex() - 0);
                item.setInt(item.getInt() - 0);
                item.setLuk(item.getLuk() - 0);
                item.setWatk(item.getWatk() - 0);
                item.setMatk(item.getMatk() - 0);
                item.setHp(item.getHp() - 0);
                cm.gainItem(coin, -price);
                cm.getPlayer().send(Packages.network.models.CWvsContext.InventoryPacket.updateInventoryItem(Packages.objects.item.MapleInventoryType.CASH_EQUIP, item, false, cm.getPlayer()));
                msg = "#fs11#" + black + "การเพิ่มพลังล้มเหลว\r\n"
                msg += "#fc0xFFD5D5D5#───────────────────────────#k#l\r\n";
                msg += "#i" + item.getItemId() + "# #fc0xFF4641D9##z" + item.getItemId() + "#\r\n";
                msg += "#fc0xFF6799FF#STR : + " + item.getStr() + "\r\n";
                msg += "DEX : + " + item.getDex() + "\r\n";
                msg += "INT : + " + item.getInt() + "\r\n";
                msg += "LUK : + " + item.getLuk() + "\r\n";
                msg += "MAXHP : + " + item.getHp() + "\r\n";
                msg += "ATT : + " + item.getWatk() + "\r\n";
                msg += "MATT : + " + item.getMatk() + "\r\n";
                msg += "#L0##bเพิ่มพลังอีกครั้ง\r\n"
                msg += "#L1##rหยุดเพิ่มพลัง\r\n"
                cm.sendSimple(msg);
            } else {
                item.setStr(item.getStr() + allstat);
                item.setDex(item.getDex() + allstat);
                item.setInt(item.getInt() + allstat);
                item.setLuk(item.getLuk() + allstat);
                item.setWatk(item.getWatk() + atk);
                item.setMatk(item.getMatk() + atk);
                item.setHp(item.getHp() + 10);
                item.setOwner("" + (getAddEnhance(item) + 1) + "Lv");
                cm.gainItem(coin, -price);
                cm.getPlayer().send(Packages.network.models.CWvsContext.InventoryPacket.updateInventoryItem(Packages.objects.item.MapleInventoryType.CASH_EQUIP, item, false, cm.getPlayer()));
                msg = "#fs11#" + black + "#bการเพิ่มพลังสำเร็จ!#k นี่คือ option ของไอเทมที่เพิ่มพลังแล้ว\r\n";
                msg += "#fc0xFFD5D5D5#───────────────────────────#k#l\r\n";
                msg += "#i" + item.getItemId() + "# #fc0xFF4641D9##z" + item.getItemId() + "#\r\n";
                msg += "#fc0xFF6799FF#STR : + " + item.getStr() + "\r\n";
                msg += "DEX : + " + item.getDex() + "\r\n";
                msg += "INT : + " + item.getInt() + "\r\n";
                msg += "LUK : + " + item.getLuk() + "\r\n";
                msg += "MAXHP : + " + item.getHp() + "\r\n";
                msg += "ATT : + " + item.getWatk() + "\r\n";
                msg += "MATT : + " + item.getMatk() + "\r\n";
                msg += "#L0##bเพิ่มพลังอีกครั้ง\r\n";
                msg += "#L1##rหยุดเพิ่มพลัง\r\n";
                cm.sendSimple(msg);
            }
        }
    } else if (status == 4) {
        if (sssss == 1) {
            cm.sendScreenText("#fs18#ไอเทมตกแต่งใดก็ได้สามารถใช้ได้                              #fs15##rยกเว้นแหวนคู่รัก แหวนมิตรภาพ และอุปกรณ์สัตว์เลี้ยง#k", false);
        } else {
            if (selection == 0) {
            } else {
                cm.dispose();
            }
        }
    } else if (status == 5) {
        cm.sendScreenText("#fs18#การเพิ่มพลังแต่ละครั้งต้องใช้ #i4310012# #fc0xFFFFBB00##z4310012##k #r30 ชิ้น#k", false);
    } else if (status == 6) {
        cm.sendScreenText("Stats สูงสุดคือ #fc0xFFCCA63D#'50'#k", false);
    } else if (status == 7) {
        cm.sendScreenText("#fs15##fc0xFF4641D9#เพิ่มพลังสำเร็จ#k จะได้รับ #fc0xFF6799FF#All Stats + 1, ATT/MATT + 1, HP + 10#k", false);
    } else if (status == 8) {
        cm.sendScreenText("#fs15##fc0xFFCC3D3D#เพิ่มพลังล้มเหลว#k จะลด #fc0xFFF15F5F#All Stats - 1, ATT/MATT - 1, HP - 10#k", false);
    } else if (status == 9) {
        cm.sendScreenText("#fs18#อัตราความสำเร็จคือ #r60%#k", true);
    } else if (status == 10) {
        cm.Endtuto(false);
        cm.dispose();
    }
}
