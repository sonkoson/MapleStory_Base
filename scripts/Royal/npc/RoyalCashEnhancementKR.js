var enter = "\r\n";
var selectedItemIndex = -1;
importPackage(Packages.server);
importPackage(Packages.scripting);
importPackage(Packages.objects.item);

var heartIcon = "#fs11##fMap/MapHelper.img/minimap/match#";
var blueColor = "#fc0xFF6B66FF#";
var runeIcon = "#fMap/MapHelper.img/minimap/rune#";
var blackColor = "#fc0xFF191919#";
var selectionType = 0;
var enhancementCoinId = 4033213; // Resource
var requiredCoinQuantity = 1; // Price
var allStatBonus = 4, attackBonus = 2;
var failureChance = 50; // Failure probability
var maxReinforcementLevel = 100; // Max reinforce level

function getAddEnhance(item) {
    var owner = item.getOwner();
    // Allow 1-3 digits reinforcement level with '+' suffix or previous Korean '?'
    var m = owner.match(/^(\d{1,3})(\+|\uAC15)$/);
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
            if (selectionType == 1) {
                status++;
            }
        }
        status++;
        if (status == 4 && selection == 0 && selectionType == 0) {
            status = 1;
        }
    } else {
        if (status == 2) {
            cm.sendOkS("จ้ะ ถ้าเธอเปลี่ยนใจเมื่อไหร่ก็กลับมาหาฉันได้เสมอนะ", 4, 9401232);
        }
        cm.dispose();
        return;
    }
    if (status == 0) {
        var message = "#fs11#" + blackColor + "สวัสดีจ๊ะคุณ #b#h ##k! \r\n";
        message += "#fs11#" + blackColor + "เธอรู้ไหมว่าไอเทม Cash ก็สามารถเสริมพลังได้เหมือนกันนะ?  \r\n";
        message += "#fs11#" + blackColor + "ระบบเสริมพลัง Cash คือระบบที่ใช้ #i4033213##b#t4033213##d\r\nในการเสริมพลังให้กับไอเทมแฟชั่นของเธอจ้ะ";
        message += "#fs11#" + blackColor + " เสริมพลังเพิ่มความเท่และเก่งไปพร้อมกัน! \r\n\r\n";
        message += "#fs11#" + blackColor + "ในการเสริมพลังแต่ละครั้ง จะเพิ่ม #rAll Stat 4 และ ATT/MATT 2#d จ้ะ \r\n";
        message += "#fs11#" + blackColor + "ใช้ #b#z4033213##k 1 ชิ้น สามารถเสริมพลังได้สูงสุดถึง " + maxReinforcementLevel + "+ เลยนะ#r\r\n\r\nอุปกรณ์สัตว์เลี้ยงและไอเทมบางอย่างจะไม่สามารถใช้ได้นะจ๊ะ\r\n";
        message += "#fc0xFFD5D5D5#ฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤ#k\r\n";
        message += "#L0##fc0xFF6B66FF#เสริมพลังไอเทมแฟชั่น#k" + blackColor + " [โอกาสสำเร็จ 50%]#k\r\n";
        //message += "#L1#ต้องการคำอธิบายเพิ่มเติมจ้ะ"
        cm.sendSimple(message);
    } else if (status == 1) {
        selectionType = selection;
        if (selection == 0) {
            var inventoryText = "#fs11#" + blackColor + "เธอต้องการเสริมพลังไอเทมชิ้นไหนดีจ๊ะ?\r\nโอกาสสำเร็จคือ #b50%#k นะ\r\n";
            inventoryText += "#fc0xFFD5D5D5#ฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤ#k\r\n";
            for (var i = 0; i < cm.getInventory(6).getSlotLimit(); i++) {
                if (cm.getInventory(6).getItem(i) != null) {
                    if (cm.isCash(cm.getInventory(6).getItem(i).getItemId())) {
                        inventoryText += "#L" + i + "# #i" + cm.getInventory(6).getItem(i).getItemId() + "# #fc0xFF6B66FF##z" + cm.getInventory(6).getItem(i).getItemId() + "#\r\n";
                    }
                }
            }
            cm.sendSimple(inventoryText);
        } else if (selection == 1) {
            cm.Entertuto(false, false);
        }
    } else if (status == 2) {
        var itemsSelection = selection;
        item = cm.getInventory(6).getItem(itemsSelection);
        if (item == null) {
            cm.sendOk("อ๊ะ เกิดข้อผิดพลาดบางอย่าง รบกวนเธอช่วยลองใหม่อีกครั้งนะจ๊ะ");
            cm.dispose();
            return;
        }
        cm.sendNext("#fs11#เธอต้องการเสริมพลัง #r#i" + item.getItemId() + "# #z" + item.getItemId() + "##k จริงๆ ใช่ไหมจ๊ะ? เมื่อทำแล้วจะไม่สามารถย้อนกลับได้นะ รบกวนตัดสินใจให้ดีๆ ก่อนนะจ๊ะ");
    } else if (status == 3) {
        if (selectionType == 1) {
            cm.sendScreenText("#fs27##fc0xFF6B66FF#เดี๋ยวฉันจะอธิบายเรื่องการเสริมพลังแฟชั่นให้ฟังจ้ะ#k", false);
        } else {
            if (!cm.haveItem(enhancementCoinId, requiredCoinQuantity)) {
                cm.sendOk("#fs11#" + blackColor + "อ๊ะ ดูเหมือนวัตถุดิบเสริมพลังของเธอจะไม่พอนะ! ต้องมี #i4033213##r#t4033213# 1 ชิ้น#d จ้ะ");
                cm.dispose();
                return;
            }

            if (item == null) {
                cm.dispose();
                return;
            }
            if ((item.getItemId() >= 1112000 && item.getItemId() <= 1112099) || (item.getItemId() >= 1112800 && item.getItemId() <= 1112899)) {
                cm.sendOk("#fs11#" + blackColor + "ไอเทมประเภท Couple Ring หรือ Friendship Ring ไม่สามารถเสริมพลังได้นะจ๊ะ");
                cm.dispose();
                return;
            }
            var currentLevel = getAddEnhance(item);
            if (currentLevel >= maxReinforcementLevel) {
                cm.sendOk("#fs11#ไม่สามารถเสริมพลังต่อได้แล้วจ้ะ! ไอเทมหนึ่งชิ้นสามารถเสริมพลังได้สูงสุดแค่ #r"
                    + maxReinforcementLevel + "+#d เท่านั้นนะ");
                cm.dispose();
                return;
            }
            if (item.getItemId() >= 1802000 && item.getItemId() <= 1802999) {
                cm.sendOk("#fs11#" + blackColor + "อุปกรณ์สัตว์เลี้ยงไม่สามารถเสริมพลังได้นะจ๊ะ");
                cm.dispose();
                return;
            }
            if (!cm.isCash(item.getItemId())) {
                cm.sendOk("#fs11#" + blackColor + "ดูเหมือนจะเป็นไอเทมที่ผิดปกตินะจ๊ะ");
                cm.dispose();
                return;
            }

            var isSuccess = (Math.random() * 100) < (100 - failureChance);

            if (!isSuccess) {
                // Failure logic (no stat change but consume resource)
                cm.gainItem(enhancementCoinId, -requiredCoinQuantity);
                cm.getPlayer().send(Packages.network.models.CWvsContext.InventoryPacket.updateInventoryItem(Packages.objects.item.MapleInventoryType.CASH_EQUIP, item, false, cm.getPlayer()));
                var message = "#fs11#" + blackColor + "อุ๊ย... เสริมพลังล้มเหลวซะแล้วจ้ะ...\r\n";
                message += "#fc0xFFD5D5D5#ฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤ#k#l\r\n";
                message += "#i" + item.getItemId() + "# #fc0xFF4641D9##z" + item.getItemId() + "#\r\n";
                message += "#fc0xFF6799FF#STR : + " + item.getStr() + "\r\n";
                message += "DEX : + " + item.getDex() + "\r\n";
                message += "INT : + " + item.getInt() + "\r\n";
                message += "LUK : + " + item.getLuk() + "\r\n";
                message += "MAXHP : + " + item.getHp() + "\r\n";
                message += "ATTACK : + " + item.getWatk() + "\r\n";
                message += "MAGIC ATTACK : + " + item.getMatk() + "\r\n";
                message += "#L0##bฉันจะลองเสริมพลังอีกรอบจ้ะ\r\n";
                message += "#L1##rฉันพอแค่นี้ก่อนดีกว่าจ้ะ\r\n";
                cm.sendSimple(message);
            } else {
                item.setStr(item.getStr() + allStatBonus);
                item.setDex(item.getDex() + allStatBonus);
                item.setInt(item.getInt() + allStatBonus);
                item.setLuk(item.getLuk() + allStatBonus);
                item.setWatk(item.getWatk() + attackBonus);
                item.setMatk(item.getMatk() + attackBonus);
                item.setHp(item.getHp() + 10);
                item.setOwner("" + (currentLevel + 1) + "+");
                cm.gainItem(enhancementCoinId, -requiredCoinQuantity);
                cm.getPlayer().send(Packages.network.models.CWvsContext.InventoryPacket.updateInventoryItem(Packages.objects.item.MapleInventoryType.CASH_EQUIP, item, false, cm.getPlayer()));
                var message = "#fs11#" + blackColor + "#bยินดีด้วยนะ! เสริมพลังสำเร็จแล้วจ้า!#k นี่คือออฟชั่นของไอเทมที่เสริมพลังแล้วนะ\r\n";
                message += "#fc0xFFD5D5D5#ฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤ#k#l\r\n";
                message += "#i" + item.getItemId() + "# #fc0xFF4641D9##z" + item.getItemId() + "#\r\n";
                message += "#fc0xFF6799FF#STR : + " + item.getStr() + "\r\n";
                message += "DEX : + " + item.getDex() + "\r\n";
                message += "INT : + " + item.getInt() + "\r\n";
                message += "LUK : + " + item.getLuk() + "\r\n";
                message += "MAXHP : + " + item.getHp() + "\r\n";
                message += "ATTACK : + " + item.getWatk() + "\r\n";
                message += "MAGIC ATTACK : + " + item.getMatk() + "\r\n";
                message += "#L0##bฉันจะลองเสริมพลังอีกรอบจ้ะ\r\n";
                message += "#L1##rฉันพอแค่นี้ก่อนดีกว่าจ้ะ\r\n";
                cm.sendSimple(message);
            }
        }
    } else if (status == 4) {
        if (selectionType == 1) {
            cm.sendScreenText("#fs18#ถ้าเป็นไอเทมแฟชั่นล่ะก็ ชิ้นไหนก็เสริมพลังได้จ้ะ??????????? ?#fs15##rแต่ Couple Ring, Friendship Ring และอุปกรณ์สัตว์เลี้ยงจะทำไม่ได้นะ#k", false);
        } else {
            if (selection == 0) {
            } else {
                cm.dispose();
            }
        }
    } else if (status == 5) {
        cm.sendScreenText("#fs18#การเสริมพลังแต่ละครั้งต้องใช้ #i4033213# #fc0xFFFFBB00##z4033213##k #r1 ชิ้น#k จ้ะ,", false);
    } else if (status == 6) {
        cm.sendScreenText("สามารถเสริมพลังได้สูงสุดถึง #fc0xFFCCA63D#100+#k เลยนะจ๊ะ", false);
    } else if (status == 7) {
        cm.sendScreenText("#fs15##fc0xFF4641D9#เมื่อเสริมพลังสำเร็จ#k จะได้รับ #fc0xFF6799FF#All Stat +4, ATT/MATT +2, HP +10#k จ้ะ", false);
    } else if (status == 8) {
        cm.sendScreenText("#fs15##fc0xFFCC3D3D#หากเสริมพลังล้มเหลว#k เราจะคืนไอเทมของเธอให้ แต่จะสูญเสียวัตถุดิบไปนะ", false);
    } else if (status == 9) {
        cm.sendScreenText("#fs18#โอกาสเสริมพลังสำเร็จคือ #r50%#k จ้ะ", true);
    } else if (status == 10) {
        cm.Endtuto(false);
        cm.dispose();
    }
}




