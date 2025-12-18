// NPC Name: Exchange NPC (ex: "Spirit of Exchange")
// Item list array
var exchangeItems = [
    1213018, 1214018, 1212120, 1222113, 1232113, 1242121, 1262039, 1302343,
    1322255, 1312203, 1332279, 1342104, 1362140, 1372228, 1382265, 1402259, 1412181, 1422189,
    1432218, 1442274, 1452257, 1462243, 1472265, 1482221, 1492235, 1522143, 1532150, 1582023,
    1272017, 1282017, 1292018, 1592020, 1404018, 1102940, 1102941, 1102942, 1102943, 1102944,
    1082695, 1082696, 1082697, 1082698, 1082699, 1073158, 1073159, 1073160, 1073161, 1073162,
    1152196, 1152197, 1152198, 1152199, 1152200, 1053063, 1053064, 1053065, 1053066, 1053067, 1004808, 1004809, 1004810, 1004811, 1004812
];

// Exchange target item ID
var targetItem = 4310218;
var availableItems = [];
var selectionIndex = -1; // Stores user's selected index

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 0) {
        cm.dispose();
        return;
    }
    status++;

    // === status == 0: Show menu with only exchangeable items in inventory ===
    if (status == 0) {
        availableItems = [];
        // Filter items that are in player's inventory
        for (var i = 0; i < exchangeItems.length; i++) {
            var id = exchangeItems[i];
            if (cm.haveItem(id, 1)) {
                availableItems.push(id);
            }
        }
        if (availableItems.length == 0) {
            cm.sendOk("ขอโทษนะ ตอนนี้ไม่มีไอเทมที่แลกได้ในกระเป๋าเลย");
            cm.dispose();
            return;
        }

        // Start conversation and create menu
        var msg = "สวัสดี! ถ้าให้ #e#bอุปกรณ์ Arcane Shade 1 ชิ้น#k#n มา\r\n"
            + "จะแลก #e#b#z4310218##k#n 1 ชิ้นให้นะ\r\n\r\n"
            + "#bเลือกไอเทมที่จะแลกจากรายการด้านล่าง#k\r\n\r\n";
        for (var i = 0; i < availableItems.length; i++) {
            var itemId = availableItems[i];
            msg += "#L" + i + "# #i" + itemId + "# [#z" + itemId + "#]#l\r\n";
        }
        cm.sendSimple(msg);

        // === status == 1: User selected item from menu ===
    } else if (status == 1) {
        selectionIndex = selection;
        var itemId = availableItems[selectionIndex];
        // Error handling if item disappeared from inventory
        if (!cm.haveItem(itemId, 1)) {
            cm.sendOk("น่าเสียดาย ไอเทมที่เลือกไม่อยู่ในกระเป๋าแล้ว");
            cm.dispose();
            return;
        }

        // Process exchange: remove item & give targetItem
        cm.gainItem(itemId, -1);
        cm.gainItem(targetItem, 1);

        // Success message
        cm.sendOk("แลก #i" + itemId + "# 1 ชิ้น เป็น #i" + targetItem + "# 1 ชิ้น สำเร็จแล้ว!");

        // === status == 2: Re-open menu after pressing confirm ===
    } else if (status == 2) {
        // Reset status and call action again
        status = -1;
        action(1, 0, 0);
    }
}
