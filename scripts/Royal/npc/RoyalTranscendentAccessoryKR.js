// by MelonK
importPackage(Packages.network.models);
importPackage(Packages.objects.item);
importPackage(Packages.network.world);
importPackage(Packages.main.world);
importPackage(Packages.database.hikari);
importPackage(java.lang);
importPackage(Packages.server);
importPackage(Packages.database);

var status = -1;
var isCraftable = true;
var selectedIndex;

function start() {
   status = -1;
   action(1, 0, 0);
}

var bossATK = Math.floor(Math.random() * 30) + 250; // min 250 max 280
var bossGrade = Math.floor(Math.random() * 30) + 250; // min 250 max 280

function action(mode, type, selection) {
   var ingredientList = [
      [[1113149, 2], [4310237, 150], [4001715, 5]], // Silver Blossom Ring
      [[1113282, 2], [4310237, 150], [4001715, 5]], // Noble Ifia's Ring
      [[1012478, 2], [4310237, 150], [4001715, 5]], // Condensed Power Crystal
      [[1022231, 2], [4310237, 150], [4001715, 5]], // Aquatic Letter Eye Ornament
      [[1022232, 2], [4310237, 150], [4001715, 5]], // Black Bean Mark


      [[1022277, 2], [4310237, 150], [4001715, 5]], // Papulatus Mark
      [[1032241, 2], [4310237, 150], [4001715, 5]], // Dea Sidus Earrings
      [[1122000, 2], [4310237, 150], [4001715, 5]], // Horntail Necklace
      [[1122076, 2], [4310237, 150], [4001715, 5]], // Chaos Horntail Necklace


      [[1122254, 2], [4310237, 150], [4001715, 5]], // Mechanator Pendant
      [[1122150, 2], [4310237, 150], [4001715, 5]], // Dominator Pendant
      [[1132296, 2], [4310237, 150], [4001715, 5]], // Pink Bean Belt
      [[1132272, 2], [4310237, 150], [4001715, 5]], // Gold Clover Belt
      [[1152170, 2], [4310237, 150], [4001715, 5]], // Royal Black Metal Shoulder


      [[1162025, 2], [4310237, 150], [4001715, 5]], // Crystal Ventus Badge
      [[1182087, 2], [4310237, 150], [4001715, 5]], // Ghost Ship Exorcist Badge
   ]; // Ingredients
   var resultItemList = [1113800, 1113801, 1012800, 1022800, 1022801, 1022802, 1032801, 1122800, 1122801, 1122802, 1122803, 1132800, 1132801, 1152800, 1162800, 1182800]; // Final Items
   var craftChance = 100; // Probability
   if (mode == -1 || mode == 0) {
      cm.dispose();
      return;
   }
   if (mode == 1) {
      status++;
   }
   if (status == 0) {
      message = "\r\n#fs11##fc0xFF000000#เธอต้องการสร้างเครื่องประดับบอสระดับ Transcendence ไหมจ๊ะ?#n\r\n\r\n";
      message += "#L0##bฉันต้องการสร้างจ้ะ";
      cm.sendSimple(message);
   } else if (status == 1) {
      message = "\r\n#fs11##fc0xFF000000#กรุณาเลือกไอเทมที่เธอต้องการจะสร้างนะ#k\r\n";
      for (var i = 0; i < resultItemList.length; i++) {
         message += "#L" + i + "# #i" + resultItemList[i] + "# #b#z" + resultItemList[i] + "##k\r\n";
      }
      cm.sendSimple(message);
   } else if (status == 2) {
      selectedIndex = selection;
      message = "#b#eไอเทมที่เลือก : #k#n #i" + resultItemList[selectedIndex] + "# #r#z" + resultItemList[selectedIndex] + "##k\r\n\r\n";
      message += "การจะสร้างไอเทมนี้ เธอจำเป็นต้องมีวัตถุดิบดังนี้จ้ะ\r\n\r\n";
      for (var i = 0; i < ingredientList[selectedIndex].length; i++) {
         message += "#i" + ingredientList[selectedIndex][i][0] + "# #b#z" + ingredientList[selectedIndex][i][0] + "##k";
         if (cm.itemQuantity(ingredientList[selectedIndex][i][0]) >= ingredientList[selectedIndex][i][1]) {
            message += "#fc0xFF41AF39##e (" + cm.itemQuantity(ingredientList[selectedIndex][i][0]) + "/" + ingredientList[selectedIndex][i][1] + ")#k#n\r\n";
         } else {
            message += "#r#e (" + cm.itemQuantity(ingredientList[selectedIndex][i][0]) + "/" + ingredientList[selectedIndex][i][1] + ")#k#n\r\n";
            isCraftable = false;
         }
      }
      message += "\r\n";
      if (isCraftable) {
         message += "#L0# #bตกลง ฉันจะสร้างไอเทมนี้จ้ะ#k";
         cm.sendSimple(message);
      } else {
         message += "#rอ๊ะ ดูเหมือนวัตถุดิบของเธอจะยังไม่พอนะจ๊ะ เลยยังสร้างไอเทมไม่ได้#k";
         cm.sendOk(message);
         cm.dispose();
      }
   } else if (status == 3) {
      if (Math.floor(Math.random() * 100) <= 100) {
         if (resultItemList[selectedIndex] >= 2000000) {
            cm.gainItem(resultItemList[selectedIndex], 1);
            for (var i = 0; i < ingredientList[selectedIndex].length; i++) {
               cm.gainItem(ingredientList[selectedIndex][i][0], -ingredientList[selectedIndex][i][1]);
            }
            cm.sendOk("ยินดีด้วยนะ! เธอสร้างไอเทมสำเร็จแล้ว!");
         } else {
            cm.gainItem(resultItemList[selectedIndex], 1);
            for (var i = 0; i < ingredientList[selectedIndex].length; i++) {
               cm.gainItem(ingredientList[selectedIndex][i][0], -ingredientList[selectedIndex][i][1]);
            }
            cm.sendOk("ยินดีด้วยนะ! เธอสร้างไอเทมสำเร็จแล้ว!");
         }
         var craftedItemName = cm.getItemName(resultItemList[selectedIndex]);
         cm.worldGMMessage(21, "[Transcendence Acc] คุณ " + cm.getPlayer().getName() + " ได้สร้าง [" + craftedItemName + "] สำเร็จแล้ว!");
      } else {
         for (var i = 1; i < ingredientList[selectedIndex].length; i++) {
            cm.gainItem(ingredientList[selectedIndex][i][0], -ingredientList[selectedIndex][i][1]);
         }
         cm.sendOk("เสียใจด้วยนะจ๊ะ เธอสร้างไอเทมล้มเหลว...");
      }
      cm.dispose();
      return;
   }
}




