// by MelonK
importPackage(Packages.network.models);

importPackage(Packages.objects.item);
importPackage(Packages.network.world);
importPackage(Packages.main.world);
importPackage(Packages.database.hikari);
importPackage(java.lang);
importPackage(Packages.server);
importPackage(Packages.network.models);

importPackage(Packages.objects.item);
importPackage(Packages.network.world);
importPackage(Packages.main.world);
importPackage(Packages.database);
importPackage(java.lang);

var status = -1;
isOk = true;
function start() {
    status = -1;
    action(1, 0, 0);
}

bossa = Math.floor(Math.random() * 30) + 250 // Min 10 Max 35 , Horntail
bossg = Math.floor(Math.random() * 30) + 250 // Min 5, Max 15, Horntail

function action(mode, type, selection) {
    requiredItems = [
        [[1113149, 2], [4310237, 150], [4001715, 5]],//Silver Blossom Ring
        [[1113282, 2], [4310237, 150], [4001715, 5]],//Ifia's Ring
        [[1012478, 2], [4310237, 150], [4001715, 5]],//Condensed Power Crystal
        [[1022231, 2], [4310237, 150], [4001715, 5]],//Aquatic Letter Eye Accessory
        [[1022232, 2], [4310237, 150], [4001715, 5]],//Black Bean Mark


        [[1022277, 2], [4310237, 150], [4001715, 5]],//Papulatus Mark
        [[1032241, 2], [4310237, 150], [4001715, 5]],//Dea Sidus Earring
        [[1122000, 2], [4310237, 150], [4001715, 5]],//Horntail Necklace
        [[1122076, 2], [4310237, 150], [4001715, 5]],//Chaos Horntail Necklace


        [[1122254, 2], [4310237, 150], [4001715, 5]],//Mechanator Pendant
        [[1122150, 2], [4310237, 150], [4001715, 5]],//Dominator Pendant
        [[1132296, 2], [4310237, 150], [4001715, 5]],//Golden Clover Belt
        [[1132272, 2], [4310237, 150], [4001715, 5]],//Angry Zakum Belt
        [[1152170, 2], [4310237, 150], [4001715, 5]],//Royal Black Metal Shoulder


        [[1162025, 2], [4310237, 150], [4001715, 5]],//Pink Holy Cup
        [[1182087, 2], [4310237, 150], [4001715, 5]],//Crystal Ventus Badge
    ]; // Ingredients
    rewardItems = [1113800, 1113801, 1012800, 1022800, 1022801, 1022802, 1032801, 1122800, 1122801, 1122802, 1122803, 1132800, 1132801, 1152800, 1162800, 1182800]; // Finished Product
    chance = 100; // Success Rate
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
        talk = "\r\n#fs11##fc0xFF000000#ต้องการสร้าง Transcendence Boss Accessory หรือไม่?#n\r\n\r\n";
        talk += "#L0##bต้องการสร้าง";
        cm.sendSimple(talk);
    } else if (status == 1) {
        talk = "\r\n#fs11##fc0xFF000000#กรุณาเลือกไอเท็มที่จะสร้าง#k\r\n"
        for (i = 0; i < rewardItems.length; i++) {
            talk += "#L" + i + "# #i" + rewardItems[i] + "# #b#z" + rewardItems[i] + "##k\r\n";
        }
        cm.sendSimple(talk);
    } else if (status == 2) {
        st = selection;
        talk = "#b#eไอเท็มที่เลือก : #k#n #i" + rewardItems[st] + "# #r#z" + rewardItems[st] + "##k\r\n\r\n"
        talk += "ต้องใช้วัตถุดิบดังนี้ในการสร้าง\r\n\r\n";
        for (i = 0; i < requiredItems[st].length; i++) {
            talk += "#i" + requiredItems[st][i][0] + "# #b#z" + requiredItems[st][i][0] + "##k"
            if (cm.itemQuantity(requiredItems[st][i][0]) >= requiredItems[st][i][1]) {
                talk += "#fc0xFF41AF39##e (" + cm.itemQuantity(requiredItems[st][i][0]) + "/" + requiredItems[st][i][1] + ")#k#n\r\n";
            } else {
                talk += "#r#e (" + cm.itemQuantity(requiredItems[st][i][0]) + "/" + requiredItems[st][i][1] + ")#k#n\r\n";
                isOk = false;
            }
        }
        talk += "\r\n"
        if (isOk) {
            talk += "#L0# #bยืนยันการสร้างไอเท็ม#k";
            cm.sendSimple(talk);
        } else {
            talk += "#rวัตถุดิบไม่เพียงพอ#k";
            cm.sendOk(talk);
            cm.dispose();
        }
    } else if (status == 3) {
        if (Math.floor(Math.random() * 100) <= 100) {
            if (rewardItems[st] >= 2000000) {
                cm.gainItem(rewardItems[st], 1);
                for (i = 0; i < requiredItems[st].length; i++) {
                    cm.gainItem(requiredItems[st][i][0], -requiredItems[st][i][1]);
                }
                cm.sendOk("สร้างสำเร็จแล้ว");
            }
            cm.gainItem(rewardItems[st], 1);
            for (i = 0; i < requiredItems[st].length; i++) {
                cm.gainItem(requiredItems[st][i][0], -requiredItems[st][i][1]);
            }
            cm.sendOk("สร้างสำเร็จแล้ว");
            var itemName = cm.getItemName(rewardItems[st]);
            cm.worldGMMessage(21, "[Transcendence Accessory] " + cm.getPlayer().getName() + " ได้สร้าง [" + itemName + "] สำเร็จ");
        } else {
            for (i = 1; i < requiredItems[st].length; i++) {
                cm.gainItem(requiredItems[st][i][0], -requiredItems[st][i][1]);
            }
            cm.sendOk("สร้างล้มเหลว");
        }
        cm.dispose();
        return;
    }
}




