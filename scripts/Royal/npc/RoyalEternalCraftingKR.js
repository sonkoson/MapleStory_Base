importPackage(Packages.constants);

var lineBreak = "\r\n";
var status = -1;
var selectedItemIndex = -1;

var icon1 = "#fUI/GuildMark.img/BackGround/00001026/1#";
var icon2 = "#fUI/GuildMark.img/BackGround/00001028/12#";
var icon3 = "#fUI/GuildMark.img/BackGround/00001028/9#";

var star1 = "#fUI/GuildMark.img/Mark/Pattern/00004014/11#";

var num0 = "#fUI/GuildMark.img/Mark/Letter/00005026/15#";
var num1 = "#fUI/GuildMark.img/Mark/Letter/00005027/15#";
var num2 = "#fUI/GuildMark.img/Mark/Letter/00005028/4#";

var rankingIcon = "#fUI/Basic.img/theblackcoin/24#";
var blackTag = "#fc0xFF191919#";
var redTag = "#fc0xFFF15F5F#";
var purpleTag = "#fc0xFF5F00FF#";

var enhancementIcon = "#fUI/Basic.img/theblackcoin/8#";

var itemList = [
   {
      'id': 1005980, 'quantity': 1, 'allStat': 0, 'attack': 0,
      'recipes': [[2634472, 10], [4310237, 20000], [4310229, 5000], [4310266, 5000], [4310308, 2000], [4031227, 1000]], 'price': 50000000000, 'successChance': 100,
      'failRewards': [[1005980, 1]]
   },
   {
      'id': 1042433, 'quantity': 1, 'allStat': 0, 'attack': 0,
      'recipes': [[2634472, 10], [4310237, 20000], [4310229, 5000], [4310266, 5000], [4310308, 2000], [4031227, 1000]], 'price': 50000000000, 'successChance': 100,
      'failRewards': [[1042433, 1]]
   },
   {
      'id': 1062285, 'quantity': 1, 'allStat': 0, 'attack': 0,
      'recipes': [[2634472, 10], [4310237, 20000], [4310229, 5000], [4310266, 5000], [4310308, 2000], [4031227, 1000]], 'price': 50000000000, 'successChance': 100,
      'failRewards': [[1062285, 1]]
   },
   {
      'id': 1005981, 'quantity': 1, 'allStat': 0, 'attack': 0,
      'recipes': [[2634472, 10], [4310237, 20000], [4310229, 5000], [4310266, 5000], [4310308, 2000], [4031227, 1000]], 'price': 50000000000, 'successChance': 100,
      'failRewards': [[1005981, 1]]
   },
   {
      'id': 1042434, 'quantity': 1, 'allStat': 0, 'attack': 0,
      'recipes': [[2634472, 10], [4310237, 20000], [4310229, 5000], [4310266, 5000], [4310308, 2000], [4031227, 1000]], 'price': 50000000000, 'successChance': 100,
      'failRewards': [[1042434, 1]]
   },
   {
      'id': 1062286, 'quantity': 1, 'allStat': 0, 'attack': 0,
      'recipes': [[2634472, 10], [4310237, 20000], [4310229, 5000], [4310266, 5000], [4310308, 2000], [4031227, 1000]], 'price': 50000000000, 'successChance': 100,
      'failRewards': [[1062286, 1]]
   },
   {
      'id': 1005982, 'quantity': 1, 'allStat': 0, 'attack': 0,
      'recipes': [[2634472, 10], [4310237, 20000], [4310229, 5000], [4310266, 5000], [4310308, 2000], [4031227, 1000]], 'price': 50000000000, 'successChance': 100,
      'failRewards': [[1005982, 1]]
   },
   {
      'id': 1042435, 'quantity': 1, 'allStat': 0, 'attack': 0,
      'recipes': [[2634472, 10], [4310237, 20000], [4310229, 5000], [4310266, 5000], [4310308, 2000], [4031227, 1000]], 'price': 50000000000, 'successChance': 100,
      'failRewards': [[1042435, 1]]
   },
   {
      'id': 1062287, 'quantity': 1, 'allStat': 0, 'attack': 0,
      'recipes': [[2634472, 10], [4310237, 20000], [4310229, 5000], [4310266, 5000], [4310308, 2000], [4031227, 1000]], 'price': 50000000000, 'successChance': 100,
      'failRewards': [[1062287, 1]]
   },
   {
      'id': 1005983, 'quantity': 1, 'allStat': 0, 'attack': 0,
      'recipes': [[2634472, 10], [4310237, 20000], [4310229, 5000], [4310266, 5000], [4310308, 2000], [4031227, 1000]], 'price': 50000000000, 'successChance': 100,
      'failRewards': [[1005983, 1]]
   },
   {
      'id': 1042436, 'quantity': 1, 'allStat': 0, 'attack': 0,
      'recipes': [[2634472, 10], [4310237, 20000], [4310229, 5000], [4310266, 5000], [4310308, 2000], [4031227, 1000]], 'price': 50000000000, 'successChance': 100,
      'failRewards': [[1042436, 1]]
   },
   {
      'id': 1062288, 'quantity': 1, 'allStat': 0, 'attack': 0,
      'recipes': [[2634472, 10], [4310237, 20000], [4310229, 5000], [4310266, 5000], [4310308, 2000], [4031227, 1000]], 'price': 50000000000, 'successChance': 100,
      'failRewards': [[1062288, 1]]
   },
   {
      'id': 1005984, 'quantity': 1, 'allStat': 0, 'attack': 0,
      'recipes': [[2634472, 10], [4310237, 20000], [4310229, 5000], [4310266, 5000], [4310308, 2000], [4031227, 1000]], 'price': 50000000000, 'successChance': 100,
      'failRewards': [[1005984, 1]]
   },
   {
      'id': 1042437, 'quantity': 1, 'allStat': 0, 'attack': 0,
      'recipes': [[2634472, 10], [4310237, 20000], [4310229, 5000], [4310266, 5000], [4310308, 2000], [4031227, 1000]], 'price': 50000000000, 'successChance': 100,
      'failRewards': [[1042437, 1]]
   },
   {
      'id': 1062289, 'quantity': 1, 'allStat': 0, 'attack': 0,
      'recipes': [[2634472, 10], [4310237, 20000], [4310229, 5000], [4310266, 5000], [4310308, 2000], [4031227, 1000]], 'price': 50000000000, 'successChance': 100,
      'failRewards': [[1062289, 1]]
   },
]

var targetItem;
var isEquipment = false;
var isCraftable = false;

function start() {
   status = -1;
   action(1, 0, 0);
}

function action(mode, type, selection) {
   if (mode == 1) {
      status++;
   } else {
      cm.dispose();
      return;
   }
   var dialogue = "#fs11#";
   if (status == 0) {
      dialogue += "สวัสดีจ้ะ! เธอต้องการสร้างอุปกรณ์ Eternal สินะจ๊ะ? รบกวนช่วยบอกอาชีพของเธอหน่อยนะ เพื่อใช้ในการกรองรายการไอเท็มจ้ะ\r\n\r\n";
      dialogue += "#L0#ฉันต้องการสร้างไอเท็มสำหรับสาย #eWarrior#n จ้ะ\r\n";
      dialogue += "#L1#ฉันต้องการสร้างไอเท็มสำหรับสาย #eMagician#n จ้ะ\r\n";
      dialogue += "#L2#ฉันต้องการสร้างไอเท็มสำหรับสาย #eBowman#n จ้ะ\r\n";
      dialogue += "#L3#ฉันต้องการสร้างไอเท็มสำหรับสาย #eThief#n จ้ะ\r\n";
      dialogue += "#L4#ฉันต้องการสร้างไอเท็มสำหรับสาย #ePirate#n จ้ะ\r\n";
      cm.sendSimple(dialogue);
   } else if (status == 1) {
      dialogue += blackTag + "คุณ #h0# จ๊ะ รบกวนเธอช่วยเลือกไอเท็มที่ต้องการจะสร้างหน่อยนะจ๊ะ\r\r\n\n\r\n";
      switch (selection) {
         case 0:
            for (var i = 0; i < 3; i++) {
               dialogue += "#fs11##L" + i + "##i" + itemList[i]['id'] + "# #z" + itemList[i]['id'] + "# " + itemList[i]['quantity'] + " ชิ้น" + lineBreak;
            }
            break;
         case 1:
            for (var i = 3; i < 6; i++) {
               dialogue += "#fs11##L" + i + "##i" + itemList[i]['id'] + "# #z" + itemList[i]['id'] + "# " + itemList[i]['quantity'] + " ชิ้น" + lineBreak;
            }
            break;
         case 2:
            for (var i = 6; i < 9; i++) {
               dialogue += "#fs11##L" + i + "##i" + itemList[i]['id'] + "# #z" + itemList[i]['id'] + "# " + itemList[i]['quantity'] + " ชิ้น" + lineBreak;
            }
            break;
         case 3:
            for (var i = 9; i < 12; i++) {
               dialogue += "#fs11##L" + i + "##i" + itemList[i]['id'] + "# #z" + itemList[i]['id'] + "# " + itemList[i]['quantity'] + " ชิ้น" + lineBreak;
            }
            break;
         case 4:
            for (var i = 12; i < 15; i++) {
               dialogue += "#fs11##L" + i + "##i" + itemList[i]['id'] + "# #z" + itemList[i]['id'] + "# " + itemList[i]['quantity'] + " ชิ้น" + lineBreak;
            }
            break;
      }
      cm.sendSimple(dialogue);
   } else if (status == 2) {
      selectedItemIndex = selection;
      targetItem = itemList[selection];
      isEquipment = Math.floor(targetItem['id'] / 1000000) == 1;

      isCraftable = checkRequiredItems(targetItem);

      var displayInfo = "ไอเท็มที่เธอเลือกคือรายการนี้จ้ะ#fs11##b" + lineBreak;
      displayInfo += "ไอเท็มเป้าหมาย : #i" + targetItem['id'] + "##z" + targetItem['id'] + "# " + targetItem['quantity'] + " ชิ้น" + lineBreak;

      if (isEquipment) {
         if (targetItem['allStat'] > 0)
            displayInfo += "All Stat : +" + targetItem['allStat'] + lineBreak;
         if (targetItem['attack'] > 0)
            displayInfo += "ATT / MATT : +" + targetItem['attack'] + lineBreak;
      }

      displayInfo += lineBreak;
      displayInfo += "#fs12##kนี่คือวัตถุดิบที่ต้องใช้ในการสร้างไอเท็มที่เธอเลือกนะจ๊ะ#fs11##d" + lineBreak + lineBreak;

      if (targetItem['recipes'].length > 0) {
         for (var i = 0; i < targetItem['recipes'].length; i++) {
            displayInfo += "#i" + targetItem['recipes'][i][0] + "##z" + targetItem['recipes'][i][0] + "# " + targetItem['recipes'][i][1] + " ชิ้น" + lineBreak;
         }
      }

      if (targetItem['price'] > 0)
         displayInfo += "#i5200002#" + (targetItem['price'] / 1000000) + " ล้าน Meso" + lineBreak;

      displayInfo += lineBreak + "#fs12##eโอกาสสำเร็จ : " + targetItem['successChance'] + "%#n" + lineBreak + lineBreak;
      displayInfo += "#kเมื่อสร้างสำเร็จ เธอจะได้รับไอเท็มเหล่านี้จ้ะ#fs11##d" + lineBreak + lineBreak;
      if (targetItem['failRewards'].length > 0) {
         for (var i = 0; i < targetItem['failRewards'].length; i++) {
            displayInfo += "#i" + targetItem['failRewards'][i][0] + "##z" + targetItem['failRewards'][i][0] + "# " + targetItem['failRewards'][i][1] + " ชิ้น" + lineBreak;
         }
      }
      displayInfo += "#fs12#" + lineBreak;
      displayInfo += isCraftable ? "#bวัตถุดิบในการสร้างอุปกรณ์ครบแล้วจ้ะ!." + lineBreak + "ถ้าเธอพร้อมแล้ว กด 'ตกลง' ได้เลยนะจ๊ะ" : "#rอ๊ะ ดูเหมือนอุปกรณ์หรือวัตถุดิบของเธอจะยังไม่พอสำหรับสร้างนะจ๊ะ?";

      if (isCraftable) cm.sendYesNo(displayInfo);
      else {
         cm.sendOk(displayInfo);
         cm.dispose();
      }

   } else if (status == 3) {
      isCraftable = checkRequiredItems(targetItem);

      if (!isCraftable) {
         cm.sendOk("อ๊ะ รบกวนเธอช่วยเช็ควัตถุดิบอีกรอบนะจ๊ะว่าครบไหม");
         cm.dispose();
         return;
      }
      consumeRequiredItems(targetItem);
      if (Packages.server.Randomizer.rand(1, 100) <= targetItem['successChance']) {
         obtainCraftedItem(targetItem);
         cm.sendOk("สร้างอุปกรณ์เรียบร้อยแล้วจ้า! ยินดีด้วยนะจ๊ะ");
         Packages.handling.world.World.Broadcast.broadcastMessage(Packages.tools.packet.CWvsContext.serverNotice(11, "", "[Royal Eternal] เธอคนเก่ง " + cm.getPlayer().getName() + " ได้สร้างชุด [" + cm.getItemName(targetItem['id']) + "] เรียบร้อยแล้วจ้า!"));
      } else {
         cm.sendOk("อุ๊ย... ล้มเหลวซะแล้วจ้ะ... ไม่เป็นไรนะจ๊ะ ลองใหม่อีกครั้งนะ");
         obtainFailureRewards(targetItem);
      }
      cm.dispose();
   }
}

function checkRequiredItems(target) {
   var recipeList = target['recipes'];
   var canCraft = true;

   for (var j = 0; j < recipeList.length; j++) {
      if (!cm.haveItem(recipeList[j][0], recipeList[j][1])) {
         canCraft = false;
         break;
      }
   }
   if (canCraft) canCraft = cm.getPlayer().getMeso() >= target['price'];

   return canCraft;
}

function consumeRequiredItems(target) {
   var recipeList = target['recipes'];
   for (var j = 0; j < recipeList.length; j++) {
      if (Math.floor(recipeList[j][0] / 1000000) == 1) {
         Packages.server.MapleInventoryManipulator.removeById(cm.getClient(), Packages.client.inventory.MapleInventoryType.EQUIP, recipeList[j][0], 1, false, false);
      } else {
         if (recipeList[j][1] > 30000) {
            cm.gainItem(recipeList[j][0], -recipeList[j][1] / 2);
            cm.gainItem(recipeList[j][0], -recipeList[j][1] / 2);
         } else {
            cm.gainItem(recipeList[j][0], -recipeList[j][1]);
         }
      }
   }
   cm.gainMeso(-target['price']);
}

function obtainCraftedItem(target) {
   var isEquipFlag = Math.floor(target['id'] / 1000000) == 1;
   if (isEquipFlag) {
      var newItem = Packages.server.MapleItemInformationProvider.getInstance().getEquipById(target['id']);
      if (target['allStat'] > 0) {
         newItem.setStr(target['allStat']);
         newItem.setDex(target['allStat']);
         newItem.setInt(target['allStat']);
         newItem.setLuk(target['allStat']);
      }
      if (target['attack'] > 0) {
         newItem.setWatk(target['attack']);
         newItem.setMatk(target['attack']);
      }
      Packages.server.MapleInventoryManipulator.addFromDrop(cm.getClient(), newItem, false);
   } else {
      cm.gainItem(target['id'], target['quantity']);
   }
}

function obtainFailureRewards(target) {
   var failRewardList = target['failRewards'];

   for (var j = 0; j < failRewardList.length; j++) {
      cm.gainItem(failRewardList[j][0], failRewardList[j][1]);
   }
}




