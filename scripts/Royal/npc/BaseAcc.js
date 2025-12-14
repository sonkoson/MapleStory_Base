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
      [[4310237, 30], [4036491, 5]],
      [[1113067, 1], [4310237, 150]],
      [[1032241, 1], [4310237, 150]],
      [[1152170, 1], [4310237, 150]],
      [[1113089, 1], [4310237, 150]],
      [[1112665, 1], [4310237, 50]],
      [[1112664, 1], [4310237, 50]],
      [[1112666, 1], [4310237, 50]],
      [[1112667, 1], [4310237, 50]],
   ]; // Ingredients
   rewardItems = [1190302, 1113055, 1032200, 1152154, 1113282, 1113064, 1113063, 1113065, 1113066]; // Finished Product
   chance = 100; // Success Rate
   if (mode == -1 || mode == 0) {
      cm.dispose();
      return;
   }
   if (mode == 1) {
      status++;
   }
   if (status == 0) {
      talk = "\r\n#fs11##fc0xFF000000#ต้องการสร้าง Basic Accessory หรือไม่?#n\r\n\r\n";
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
         //cm.broadcastSmega(8,"[Crafting] "+ cm.getPlayer().getName()+" crafted Scarlet Armor")
      } else {
         for (i = 1; i < requiredItems[st].length; i++) {
            cm.gainItem(requiredItems[st][i][0], -requiredItems[st][i][1]);
         }
         cm.sendOk("สร้างล้มเหลว");
         //cm.broadcastSmega(8,"[Crafting] "+ cm.getPlayer().getName()+" failed to craft Scarlet Armor")
      }
      cm.dispose();
      return;
   }
}