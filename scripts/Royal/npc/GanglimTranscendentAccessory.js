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

bossa = Math.floor(Math.random() * 30) + 250 // Min 10 Max 35, Horntail
bossg = Math.floor(Math.random() * 30) + 250 // Min 5, Max 15, Horntail

function action(mode, type, selection) {
    ing = [
        [[1113149, 2], [4310237, 150], [4001715, 5]],// Silver Blossom Ring
        [[1113282, 2], [4310237, 150], [4001715, 5]],// Gophia Ring
        [[1012478, 2], [4310237, 150], [4001715, 5]],// Condensed
        [[1022231, 2], [4310237, 150], [4001715, 5]],// Aquatic
        [[1022232, 2], [4310237, 150], [4001715, 5]],// Black Bean


        [[1022277, 2], [4310237, 150], [4001715, 5]],// Papulatus
        [[1032241, 2], [4310237, 150], [4001715, 5]],// Dea Sidus
        [[1122000, 2], [4310237, 150], [4001715, 5]],// Horntail Necklace
        [[1122076, 2], [4310237, 150], [4001715, 5]],// Chaos HT Necklace


        [[1122254, 2], [4310237, 150], [4001715, 5]],// Meister
        [[1122150, 2], [4310237, 150], [4001715, 5]],// Dominator
        [[1132296, 2], [4310237, 150], [4001715, 5]],// Pink Bean Belt
        [[1132272, 2], [4310237, 150], [4001715, 5]],// Gold Clover Belt
        [[1152170, 2], [4310237, 150], [4001715, 5]],// Black Metal Shoulder


        [[1162025, 2], [4310237, 150], [4001715, 5]],// Holy Grail
        [[1182087, 2], [4310237, 150], [4001715, 5]],// Badge
    ]; // Materials
    epsol = [1113800, 1113801, 1012800, 1022800, 1022801, 1022802, 1032801, 1122800, 1122801, 1122802, 1122803, 1132800, 1132801, 1152800, 1162800, 1182800]; // Result
    chance = 100; // Success rate
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
        msg = "\r\n#fs11##fc0xFF000000#ต้องการคราฟ Transcendent Boss Accessory หรือไม่?#n\r\n\r\n";
        msg += "#L0##bฉันจะคราฟ";
        cm.sendSimple(msg);
    } else if (status == 1) {
        msg = "\r\n#fs11##fc0xFF000000#กรุณาเลือกไอเท็มที่ต้องการคราฟ#k\r\n"
        for (i = 0; i < epsol.length; i++) {
            msg += "#L" + i + "# #i" + epsol[i] + "# #b#z" + epsol[i] + "##k\r\n";
        }
        cm.sendSimple(msg);
    } else if (status == 2) {
        st = selection;
        msg = "#b#eไอเท็มที่เลือก: #k#n #i" + epsol[st] + "# #r#z" + epsol[st] + "##k\r\n\r\n"
        msg += "สำหรับการคราฟไอเท็มนี้ ต้องการวัสดุด้านล่าง:\r\n\r\n";
        for (i = 0; i < ing[st].length; i++) {
            msg += "#i" + ing[st][i][0] + "# #b#z" + ing[st][i][0] + "##k"
            if (cm.itemQuantity(ing[st][i][0]) >= ing[st][i][1]) {
                msg += "#fc0xFF41AF39##e (" + cm.itemQuantity(ing[st][i][0]) + "/" + ing[st][i][1] + ")#k#n\r\n";
            } else {
                msg += "#r#e (" + cm.itemQuantity(ing[st][i][0]) + "/" + ing[st][i][1] + ")#k#n\r\n";
                isOk = false;
            }
        }
        msg += "\r\n"
        if (isOk) {
            msg += "#L0# #bฉันจะคราฟไอเท็ม#k";
            cm.sendSimple(msg);
        } else {
            msg += "#rวัสดุไม่เพียงพอ ไม่สามารถคราฟไอเท็มได้#k";
            cm.sendOk(msg);
            cm.dispose();
        }
    } else if (status == 3) {
        if (Math.floor(Math.random() * 100) <= 100) {
            if (epsol[st] >= 2000000) {
                cm.gainItem(epsol[st], 1);
                for (i = 0; i < ing[st].length; i++) {
                    cm.gainItem(ing[st][i][0], -ing[st][i][1]);
                }
                cm.sendOk("คราฟสำเร็จ");
            }
            cm.gainItem(epsol[st], 1);
            for (i = 0; i < ing[st].length; i++) {
                cm.gainItem(ing[st][i][0], -ing[st][i][1]);
            }
            cm.sendOk("คราฟสำเร็จ");
            var itemName = cm.getItemName(epsol[st]);
            cm.worldGMMessage(21, "[Transcendent Accessory] " + cm.getPlayer().getName() + " crafted [" + itemName + "]");
        } else {
            for (i = 1; i < ing[st].length; i++) {
                cm.gainItem(ing[st][i][0], -ing[st][i][1]);
            }
            cm.sendOk("คราฟล้มเหลว");
        }
        cm.dispose();
        return;
    }
}
