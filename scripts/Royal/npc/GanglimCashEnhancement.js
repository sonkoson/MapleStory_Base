var enter = "\r\n";
var seld = -1;
importPackage(Packages.server);
importPackage(Packages.scripting);
importPackage(Packages.objects.item);

var heart = "#fs11##fMap/MapHelper.img/minimap/match#"
var blue = "#fc0xFF6B66FF#"
var square = "#fMap/MapHelper.img/minimap/rune#"
var black = "#fc0xFF191919#";
var selectionDetails = 0;
var coin = 4033213;    // Currency
var price = 1;        // Price
var allStat = 4, atk = 2;
var failRate = 50;          // Failure Rate Variable (Success = 100 - failRate)
var maxEnhance = 100;   // Max Enhance Level

function getAddEnhance(item) {
    var owner = item.getOwner();
    // 1-3 digits allowed
    var m = owner.match(/^(\d{1,3})강$/);
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
            if (selectionDetails == 1) {
                status++;
            }
        }
        status++;
        if (status == 4 && selection == 0 && selectionDetails == 0) {
            status = 1;
        }
    } else {
        if (status == 2) {
            cm.sendOkS("เข้าใจแล้ว หากเปลี่ยนใจก็กลับมาใหม่นะ", 4, 9401232);
        }
        cm.dispose();
        return;
    }
    if (status == 0) {
        talk = "#fs11#" + black + "สวัสดีคุณ #b#h ##k! \r\n";
        talk += "#fs11#" + black + "คุณรู้หรือไม่ว่าไอเท็ม Cash ก็สามารถตีบวกได้?  \r\n";
        talk += "#fs11#" + black + "ระบบตีบวก Cash คือการใช้ #i4033213##b#t4033213##d\r\nเพื่อตีบวกไอเท็มแฟชั่น";
        talk += "#fs11#" + black + " \r\n\r\n";
        talk += "#fs11#" + black + "เพิ่ม #rAll Stat 4 และพลังโจมตี/เวทย์ 2#d ต่อครั้ง \r\n";
        talk += "#fs11#" + black + "#b#z4033213##k 1 ชิ้น สามารถตีบวกได้สูงสุดถึง +100#r\r\n\r\nไม่สามารถใช้กับอุปกรณ์สัตว์เลี้ยงและไอเท็มบางชนิด#d ได้\r\n"
        talk += "#fc0xFFD5D5D5#─────────────────────────────#k\r\n";
        talk += "#L0##fc0xFF6B66FF#ตีบวกไอเท็มแฟชั่น#k" + black + " #r[โอกาสสำเร็จ 50%]#k\r\n"
        //talk += "#L1#ฉันต้องการฟังคำอธิบาย"
        cm.sendSimple(talk);
    } else if (status == 1) {
        selectionDetails = selection;
        if (selection == 0) {
            var txt = "#fs11#" + black + "ต้องการตีบวกไอเท็มชิ้นไหน?\r\nโอกาสสำเร็จคือ #b50%#k\r\n";
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
        cm.sendNext("#fs11#คุณแน่ใจหรือไม่ที่จะตีบวก #r#i" + item.getItemId() + ":# #z" + item.getItemId() + ":##k? การตัดสินใจนี้ไม่สามารถย้อนกลับได้");
    } else if (status == 3) {
        if (selectionDetails == 1) {
            cm.sendScreenText("#fs27##fn나눔고딕 Extrabold##fc0xFF6B66FF#เกี่ยวกับระบบตีบวกไอเท็มแฟชั่น#k", false);
        } else {
            if (!cm.haveItem(coin, price)) {
                cm.sendOk("#fs11#" + black + "วัตถุดิบไม่เพียงพอ! ต้องการ #i4033213##r#t4033213# 1 ชิ้น#d");
                cm.dispose();
                return;
            }

            if (item == null) {
                cm.dispose();
                return;
            }
            if (item.getItemId() >= 1112000 && item.getItemId() <= 1112099 || item.getItemId() >= 1112800 && item.getItemId() <= 1112899) {
                cm.sendOk("#fs11#" + black + "แหวนคู่รักและแหวนมิตรภาพไม่สามารถตีบวกได้");
                cm.dispose();
                return;
            }
            var cur = getAddEnhance(item);
            if (cur >= maxEnhance) {
                cm.sendOk("#fs11#ไม่สามารถตีบวกได้อีกแล้ว! สามารถตีบวกได้สูงสุดถึง #r"
                    + maxEnhance + " ขั้น#d เท่านั้น");
                cm.dispose();
                return;
            }
            if (item.getItemId() >= 1802000 && item.getItemId() <= 1802999) {
                cm.sendOk("#fs11#" + black + "อุปกรณ์สัตว์เลี้ยงไม่สามารถตีบวกได้");
                cm.dispose();
                return;
            }
            if (!cm.isCash(item.getItemId())) {
                cm.sendOk("#fs11#" + black + "ดูเหมือนจะเป็นไอเท็มที่ผิดปกติ");
                cm.dispose();
                return;
            }

            var suc = (Math.random() * 100) < (100 - failRate);

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
                talk = "#fs11#" + black + "การตีบวกล้มเหลว\r\n"
                talk += "#fc0xFFD5D5D5#───────────────────────────#k#l\r\n";
                talk += "#i" + item.getItemId() + "# #fc0xFF4641D9##z" + item.getItemId() + "#\r\n";
                talk += "#fc0xFF6799FF#STR : + " + item.getStr() + "\r\n";
                talk += "DEX : + " + item.getDex() + "\r\n";
                talk += "INT : + " + item.getInt() + "\r\n";
                talk += "LUK : + " + item.getLuk() + "\r\n";
                talk += "MAXHP : + " + item.getHp() + "\r\n";
                talk += "ATT : + " + item.getWatk() + "\r\n";
                talk += "MATT : + " + item.getMatk() + "\r\n";
                talk += "#L0##bตีบวกอีกครั้ง\r\n"
                talk += "#L1##rหยุดตีบวก\r\n"
                cm.sendSimple(talk);
            } else {
                item.setStr(item.getStr() + allStat);
                item.setDex(item.getDex() + allStat);
                item.setInt(item.getInt() + allStat);
                item.setLuk(item.getLuk() + allStat);
                item.setWatk(item.getWatk() + atk);
                item.setMatk(item.getMatk() + atk);
                item.setHp(item.getHp() + 10);
                item.setOwner("" + (getAddEnhance(item) + 1) + "강");
                cm.gainItem(coin, -price);
                cm.getPlayer().send(Packages.network.models.CWvsContext.InventoryPacket.updateInventoryItem(Packages.objects.item.MapleInventoryType.CASH_EQUIP, item, false, cm.getPlayer()));
                talk = "#fs11#" + black + "#bตีบวกสำเร็จ!#k นี่คือออพชั่นของไอเท็มที่คุณตีบวก\r\n";
                talk += "#fc0xFFD5D5D5#───────────────────────────#k#l\r\n";
                talk += "#i" + item.getItemId() + "# #fc0xFF4641D9##z" + item.getItemId() + "#\r\n";
                talk += "#fc0xFF6799FF#STR : + " + item.getStr() + "\r\n";
                talk += "DEX : + " + item.getDex() + "\r\n";
                talk += "INT : + " + item.getInt() + "\r\n";
                talk += "LUK : + " + item.getLuk() + "\r\n";
                talk += "MAXHP : + " + item.getHp() + "\r\n";
                talk += "ATT : + " + item.getWatk() + "\r\n";
                talk += "MATT : + " + item.getMatk() + "\r\n";
                talk += "#L0##bตีบวกอีกครั้ง\r\n";
                talk += "#L1##rหยุดตีบวก\r\n";
                cm.sendSimple(talk);
            }
        }
    } else if (status == 4) {
        if (selectionDetails == 1) {
            cm.sendScreenText("#fs18#ไอเท็มแฟชั่นใดๆ ก็สามารถทำได้　　　　　　　　　　　 　#fs15##rยกเว้น แหวนคู่รัก, แหวนมิตรภาพ, และอุปกรณ์สัตว์เลี้ยงที่ไม่สามารถตีบวกได้#k", false);
        } else {
            if (selection == 0) {
            } else {
                cm.dispose();
            }
        }
    } else if (status == 5) {
        cm.sendScreenText("#fs18#ต้องใช้ #i4310012# #fc0xFFFFBB00##z4310012##k #r30 ชิ้น#k ต่อการตีบวก,", false);
    } else if (status == 6) {
        cm.sendScreenText("สเตตัสสูงสุดคือ #fc0xFFCCA63D#'50'#k", false);
    } else if (status == 7) {
        cm.sendScreenText("#fs15##fc0xFF4641D9#เมื่อสำเร็จ#k, จะได้รับ #fc0xFF6799FF#All Stat + 1 ATT/MATT + 1 HP + 10#k", false);
    } else if (status == 8) {
        cm.sendScreenText("#fs15##fc0xFFCC3D3D#เมื่อล้มเหลว#k, จะได้รับ #fc0xFFF15F5F#All Stat - 1 ATT/MATT - 1 HP - 10#k", false);
    } else if (status == 9) {
        cm.sendScreenText("#fs18#โอกาสสำเร็จคือ #r60%#k", true);
    } else if (status == 10) {
        cm.Endtuto(false);
        cm.dispose();
    }
}
