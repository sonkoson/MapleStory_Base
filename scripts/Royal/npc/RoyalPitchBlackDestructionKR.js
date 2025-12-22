importPackage(Packages.constants);

var lineBreak = "\r\n";

var icon1 = "#fUI/GuildMark.img/BackGround/00001026/1#";
var icon2 = "#fUI/GuildMark.img/BackGround/00001028/12#";
var icon3 = "#fUI/GuildMark.img/BackGround/00001028/9#";

var star1 = "#fUI/GuildMark.img/Mark/Pattern/00004014/11#";

var num0 = "#fUI/GuildMark.img/Mark/Letter/00005026/15#";
var num1 = "#fUI/GuildMark.img/Mark/Letter/00005027/15#";
var num2 = "#fUI/GuildMark.img/Mark/Letter/00005028/4#";

var t2 = "#fUI/GuildMark.img/Mark/Letter/00005019/4#";
var o2 = "#fUI/GuildMark.img/Mark/Letter/00005014/4#";
var p2 = "#fUI/GuildMark.img/Mark/Letter/00005015/4#";

var t10 = "#fUI/GuildMark.img/Mark/Letter/00005019/15#";
var o10 = "#fUI/GuildMark.img/Mark/Letter/00005014/15#";
var p10 = "#fUI/GuildMark.img/Mark/Letter/00005015/15#";

var rankingIcon = "#fUI/Basic.img/theblackcoin/24#";
var blackColor = "#fc0xFF191919#";
var redColor = "#fc0xFFF15F5F#";
var purpleColor = "#fc0xFF5F00FF#";

var enhancementIcon = "#fUI/Basic.img/theblackcoin/8#";

var selectedItemIndex = -1;

var itemList = [
   {
      'itemid': 1012655, 'qty': 1, 'allstat': 0, 'atk': 0, // Item ID, quantity, bonus stats, bonus attack (stats apply only to equipment)
      'recipes': [[1012643, 1], [4310237, 30000], [4001878, 10000], [4310308, 10000], [4310266, 10000], [4031227, 5000], [4001715, 3000]], 'price': 0, 'chance': 100, // Ingredients, cost, probability
      'fail': [[1012655, 1]] // Item given on failure
   },
   {
      'itemid': 1022293, 'qty': 1, 'allstat': 0, 'atk': 0,
      'recipes': [[1022289, 1], [4310237, 30000], [4001878, 10000], [4310308, 10000], [4310266, 10000], [4031227, 5000], [4001715, 3000]], 'price': 0, 'chance': 100,
      'fail': [[1022293, 1]]
   },
   {
      'itemid': 1032319, 'qty': 1, 'allstat': 0, 'atk': 0,
      'recipes': [[1032318, 1], [4310237, 30000], [4001878, 10000], [4310308, 10000], [4310266, 10000], [4031227, 5000], [4001715, 3000]], 'price': 0, 'chance': 100,
      'fail': [[1032319, 1]]
   },
   {
      'itemid': 1122432, 'qty': 1, 'allstat': 0, 'atk': 0,
      'recipes': [[1122431, 1], [4310237, 30000], [4001878, 10000], [4310308, 10000], [4310266, 10000], [4031227, 5000], [4001715, 3000]], 'price': 0, 'chance': 100,
      'fail': [[1122432, 1]]
   },
   {
      'itemid': 1132314, 'qty': 1, 'allstat': 0, 'atk': 0,
      'recipes': [[1132311, 1], [4310237, 30000], [4001878, 10000], [4310308, 10000], [4310266, 10000], [4031227, 5000], [4001715, 3000]], 'price': 0, 'chance': 100,
      'fail': [[1132314, 1]]
   },
   {
      'itemid': 1672079, 'qty': 1, 'allstat': 0, 'atk': 0,
      'recipes': [[1672078, 1], [4310237, 30000], [4001878, 10000], [4310308, 10000], [4310266, 10000], [4031227, 5000], [4001715, 3000]], 'price': 0, 'chance': 100,
      'fail': [[1672079, 1]]
   },
   {
      'itemid': 1113308, 'qty': 1, 'allstat': 0, 'atk': 0,
      'recipes': [[1113307, 1], [4310237, 30000], [4001878, 10000], [4310308, 10000], [4310266, 10000], [4031227, 5000], [4001715, 3000]], 'price': 0, 'chance': 100,
      'fail': [[1113308, 1]]
   },
   {
      'itemid': 1182294, 'qty': 1, 'allstat': 0, 'atk': 0,
      'recipes': [[1182286, 1], [4310237, 30000], [4001878, 10000], [4310308, 10000], [4310266, 10000], [4031227, 5000], [4001715, 3000]], 'price': 0, 'chance': 100,
      'fail': [[1182294, 1]]
   },
   {
      'itemid': 1162079, 'qty': 1, 'allstat': 0, 'atk': 0,
      'recipes': [[1162078, 1], [4310237, 30000], [4001878, 10000], [4310308, 10000], [4310266, 10000], [4031227, 5000], [4001715, 3000]], 'price': 0, 'chance': 100,
      'fail': [[1162079, 1]]
   },
   {
      'itemid': 1190565, 'qty': 1, 'allstat': 0, 'atk': 0,
      'recipes': [[1190564, 1], [4310237, 30000], [4001878, 10000], [4310308, 10000], [4310266, 10000], [4031227, 5000], [4001715, 3000]], 'price': 0, 'chance': 100,
      'fail': [[1190565, 1]]
   },
]

var targetItem;
var isEquipment = false;
var isCraftable = false;

function start() {
   status = -1;
   action(1, 0, 0);
}
function action(mode, type, sel) {
   if (mode == 1) {
      status++;
   } else {
      cm.dispose();
      return;
   }
   var message = "#fs11#";
   if (status == 0) {
      message += blackColor + "คุณ #h0# จ๊ะ กรุณาเลือกไอเทมที่เธอต้องการจะสร้างหน่อยนะ#r\r\n\r\n";
      for (var i = 0; i < itemList.length; i++) {
         message += "#L" + i + "##i" + itemList[i]['itemid'] + "# #z" + itemList[i]['itemid'] + "# " + itemList[i]['qty'] + " ชิ้น" + lineBreak;
      }
      cm.sendSimple(message);
   } else if (status == 1) {
      selectedItemIndex = sel;
      targetItem = itemList[sel];
      isEquipment = Math.floor(targetItem['itemid'] / 1000000) == 1;

      isCraftable = checkRequiredItems(targetItem);

      var message = "ไอเทมที่เธอเลือกคือรายการนี้จ้ะ#fs11##b" + lineBreak;
      message += "ไอเทม : #i" + targetItem['itemid'] + "##z" + targetItem['itemid'] + "# " + targetItem['qty'] + " ชิ้น" + lineBreak;

      if (isEquipment) {
         if (targetItem['allstat'] > 0)
            message += "All Stat : +" + targetItem['allstat'] + lineBreak;
         if (targetItem['atk'] > 0)
            message += "ATT / MATT : +" + targetItem['atk'] + lineBreak;
      }

      message += lineBreak;
      message += "#fs12##kนี่คือวัตถุดิบที่ต้องใช้ในการสร้างไอเทมที่เธอเลือกนะ#fs11##d" + lineBreak + lineBreak;

      if (targetItem['recipes'].length > 0) {
         for (var i = 0; i < targetItem['recipes'].length; i++)
            message += "#i" + targetItem['recipes'][i][0] + "##z" + targetItem['recipes'][i][0] + "# " + targetItem['recipes'][i][1] + " ชิ้น" + lineBreak;
      }

      if (targetItem['price'] > 0)
         message += "#i5200002#" + targetItem['price'] + " Meso (150 แสนล้าน)" + lineBreak;

      message += lineBreak + "#fs12##eโอกาสสร้างสำเร็จ : " + targetItem['chance'] + "%#n" + lineBreak + lineBreak;
      message += "#kเมื่อสร้างสำเร็จ เธอจะได้รับไอเทมเหล่านี้จ้ะ#fs11##d" + lineBreak + lineBreak;
      if (targetItem['fail'].length > 0) {
         for (var i = 0; i < targetItem['fail'].length; i++)
            message += "#i" + targetItem['fail'][i][0] + "##z" + targetItem['fail'][i][0] + "# " + targetItem['fail'][i][1] + " ชิ้น" + lineBreak;
      }
      message += "#fs12#" + lineBreak;
      message += isCraftable ? "#bวัตถุดิบในการสร้างไอเทมที่เลือกครบแล้วจ้ะ!." + lineBreak + "ถ้าเธอพร้อมจะสร้างแล้ว กด 'ตกลง' ได้เลยนะ" : "#rอ๊ะ อุปกรณ์หรือวัตถุดิบของเธอยังไม่พอสำหรับสร้างนะจ๊ะ?";
      if (isCraftable) { cm.sendYesNo(message); }
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
         cm.sendOk("แลกเปลี่ยนเรียบร้อยแล้วจ้า!");
         cm.worldGMMessage(21, "[Pitch Black Destruction] คุณ " + cm.getPlayer().getName() + " ได้สร้าง [" + cm.getItemName(targetItem['itemid']) + "] สำเร็จแล้ว!");
      } else {
         cm.sendOk("อุ๊ย... เสริมพลังล้มเหลวซะแล้วจ้ะ...");
         cm.worldGMMessage(21, "[Pitch Black Destruction] คุณ " + cm.getPlayer().getName() + " สร้าง [" + cm.getItemName(targetItem['itemid']) + "] ล้มเหลว...");
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







