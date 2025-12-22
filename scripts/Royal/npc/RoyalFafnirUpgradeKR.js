var lineBreak = "\r\n";
var selectedItemIndex = -1;

var itemList = [
   {
      'itemid': 1042392, 'qty': 1, 'allstat': 0, 'atk': 0,
      'recipes': [[4310065, 200], [4310237, 200], [4310266, 200], [4001715, 30]], 'price': 10, 'chance': 100,
      'fail': [[4001715, 10]]
   },
   {
      'itemid': 1042393, 'qty': 1, 'allstat': 0, 'atk': 0,
      'recipes': [[4310065, 200], [4310237, 200], [4310266, 200], [4001715, 30]], 'price': 10, 'chance': 100,
      'fail': [[4001715, 10]]
   },
   {
      'itemid': 1042394, 'qty': 1, 'allstat': 0, 'atk': 0,
      'recipes': [[4310065, 200], [4310237, 200], [4310266, 200], [4001715, 30]], 'price': 10, 'chance': 100,
      'fail': [[4001715, 10]]
   },
   {
      'itemid': 1042395, 'qty': 1, 'allstat': 0, 'atk': 0,
      'recipes': [[4310065, 200], [4310237, 200], [4310266, 200], [4001715, 30]], 'price': 10, 'chance': 100,
      'fail': [[4001715, 10]]
   },
   {
      'itemid': 1042396, 'qty': 1, 'allstat': 0, 'atk': 0,
      'recipes': [[4310065, 200], [4310237, 200], [4310266, 200], [4001715, 30]], 'price': 10, 'chance': 100,
      'fail': [[4001715, 10]]
   },
   {
      'itemid': 1062258, 'qty': 1, 'allstat': 0, 'atk': 0,
      'recipes': [[4310065, 200], [4310237, 200], [4310266, 200], [4001715, 30]], 'price': 10, 'chance': 100,
      'fail': [[4001715, 10]]
   },
   {
      'itemid': 1062259, 'qty': 1, 'allstat': 0, 'atk': 0,
      'recipes': [[4310065, 200], [4310237, 200], [4310266, 200], [4001715, 30]], 'price': 10, 'chance': 100,
      'fail': [[4001715, 10]]
   },
   {
      'itemid': 1062260, 'qty': 1, 'allstat': 0, 'atk': 0,
      'recipes': [[4310065, 200], [4310237, 200], [4310266, 200], [4001715, 30]], 'price': 10, 'chance': 100,
      'fail': [[4001715, 10]]
   },
   {
      'itemid': 1062261, 'qty': 1, 'allstat': 0, 'atk': 0,
      'recipes': [[4310065, 200], [4310237, 200], [4310266, 200], [4001715, 30]], 'price': 10, 'chance': 100,
      'fail': [[4001715, 10]]
   },
   {
      'itemid': 1062262, 'qty': 1, 'allstat': 0, 'atk': 0,
      'recipes': [[4310065, 200], [4310237, 200], [4310266, 200], [4001715, 30]], 'price': 10, 'chance': 100,
      'fail': [[4001715, 10]]
   },
] // All Stat and ATT/MATT apply only to equipment

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
   if (status == 0) {
      var message = "#fs11#กรุณาเลือกไอเทมที่เธอต้องการจะสร้างนะจ๊ะ" + lineBreak;
      message += "#fs11#ข้อมูลสูตรและไอเทมจะปรากฏขึ้นเมื่อเธอเลือกจ้ะ#fs12##b" + lineBreak;
      for (var i = 0; i < itemList.length; i++)
         message += "#fs11##L" + i + "##i" + itemList[i]['itemid'] + "# สร้าง #z" + itemList[i]['itemid'] + "#" + lineBreak;

      cm.sendSimple(message);
   } else if (status == 1) {
      selectedItemIndex = selection;
      targetItem = itemList[selection];
      isEquipment = Math.floor(targetItem['itemid'] / 1000000) == 1;

      isCraftable = checkRequiredItems(targetItem);

      var message = "#fs11#ไอเทมที่เธอเลือกคือรายการนี้จ้ะ#fs11##b" + lineBreak;
      message += "#fs11#ไอเทม : #i" + targetItem['itemid'] + "##z" + targetItem['itemid'] + "#" + lineBreak;

      if (isEquipment) {
         if (targetItem['allstat'] > 0)
            message += "All Stat : +" + targetItem['allstat'] + lineBreak;
         if (targetItem['atk'] > 0)
            message += "ATT / MATT : +" + targetItem['atk'] + lineBreak;
      }

      message += lineBreak;
      message += "#fs11##kนี่คือสูตรสำหรับสร้างไอเทมที่เธอเลือกนะ#fs11##d" + lineBreak + lineBreak;

      if (targetItem['recipes'].length > 0) {
         for (var i = 0; i < targetItem['recipes'].length; i++)
            message += "#b#i" + targetItem['recipes'][i][0] + "##z" + targetItem['recipes'][i][0] + "# " + targetItem['recipes'][i][1] + " ชิ้น #r/ #c" + targetItem['recipes'][i][0] + "# ชิ้น (ที่มีอยู่)#k" + lineBreak;
      }

      message += lineBreak + "#fs11##eโอกาสสร้างสำเร็จ : " + targetItem['chance'] + "%#n" + lineBreak + lineBreak;
      message += "#kเมื่อสร้างล้มเหลว เธอจะได้รับไอเทมเหล่านี้จ้ะ#fs11##d" + lineBreak + lineBreak;
      if (targetItem['fail'].length > 0) {
         for (var i = 0; i < targetItem['fail'].length; i++)
            message += "#i" + targetItem['fail'][i][0] + "##z" + targetItem['fail'][i][0] + "# " + targetItem['fail'][i][1] + " ชิ้น" + lineBreak;
      }
      message += "#fs11#" + lineBreak;
      message += isCraftable ? "#bวัตถุดิบในการสร้างไอเทมที่เลือกครบแล้วจ้ะ!." + lineBreak + "ถ้าเธอพร้อมจะสร้างแล้ว กด 'ตกลง' ได้เลยนะ" : "#rอ๊ะ ดูเหมือนวัตถุดิบของเธอจะยังไม่พอนะจ๊ะ เลยยังสร้างไอเทมไม่ได้";

      if (isCraftable) cm.sendYesNo(message);
      else {
         cm.sendOk(message);
         cm.dispose();
      }

   } else if (status == 2) {
      isCraftable = checkRequiredItems(targetItem);

      if (!isCraftable) {
         cm.sendOk("รบกวนเธอช่วยเช็ควัตถุดิบอีกรอบนะจ๊ะว่าครบไหม");
         cm.dispose();
         return;
      }
      payRequiredItems(targetItem);
      if (Packages.objects.utils.Randomizer.rand(1, 100) <= targetItem['chance']) {
         gainTargetItem(targetItem);
         cm.sendOk("#fs11#ยินดีด้วยนะ! เธอสร้างไอเทมสำเร็จแล้ว!");
         cm.worldGMMessage(21, "[Chaos Fafnir] คุณ " + cm.getPlayer().getName() + " ได้สร้าง [" + cm.getItemName(targetItem['itemid']) + "] สำเร็จแล้ว!");
      } else {
         cm.sendOk("#fs11#เสียใจด้วยนะจ๊ะ เธอสร้างไอเทมล้มเหลว...");
         gainFailureItem(targetItem);
      }
      cm.dispose();
   }
}

function checkRequiredItems(item) {
   var recipeList = item['recipes'];
   var result = true;

   for (var j = 0; j < recipeList.length; j++) {
      if (!cm.haveItem(recipeList[j][0], recipeList[j][1])) {
         result = false;
         break;
      }
   }
   if (result) result = cm.getPlayer().getMeso() >= item['price'];

   return result;
}

function payRequiredItems(item) {
   var recipeList = item['recipes'];
   for (var j = 0; j < recipeList.length; j++) {
      if (Math.floor(recipeList[j][0] / 1000000) == 1)
         Packages.objects.item.MapleInventoryManipulator.removeById(cm.getClient(), Packages.objects.item.MapleInventoryType.EQUIP, recipeList[j][0], 1, false, false);
      else cm.gainItem(recipeList[j][0], -recipeList[j][1]);
      cm.gainMeso(-1000000);
   }
}

function gainTargetItem(item) {
   var isEquipItem = Math.floor(item['itemid'] / 1000000) == 1;
   if (isEquipItem) {
      var newEquipItem = Packages.objects.item.MapleItemInformationProvider.getInstance().getEquipById(item['itemid']);
      if (item['allstat'] > 0) {
         newEquipItem.setStr(item['allstat']);
         newEquipItem.setDex(item['allstat']);
         newEquipItem.setInt(item['allstat']);
         newEquipItem.setLuk(item['allstat']);
      }
      if (item['atk'] > 0) {
         newEquipItem.setWatk(item['atk']);
         newEquipItem.setMatk(item['atk']);
      }
      Packages.objects.item.MapleInventoryManipulator.addFromDrop(cm.getClient(), newEquipItem, false);
   } else {
      cm.gainItem(item['itemid'], item['qty']);
   }
}
function gainFailureItem(item) {
   var failRewardList = item['fail'];

   for (var j = 0; j < failRewardList.length; j++) {
      cm.gainItem(failRewardList[j][0], failRewardList[j][1]);
   }
}




