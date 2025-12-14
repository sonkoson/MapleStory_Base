var enter = "\r\n";
var seld = -1;

var items = [
   {
      'itemid': 1032206, 'qty': 1, 'allstat': 10, 'atk': 10,
      'recipes': [[4310237, 500], [4009005, 100], [4001715, 10]], 'price': 10, 'chance': 70,
      'fail': [[4001715, 1]]
   },
   {
      'itemid': 1032207, 'qty': 1, 'allstat': 15, 'atk': 15,
      'recipes': [[1032206, 1], [4310237, 1000], [4009005, 500], [4001715, 50]], 'price': 10, 'chance': 60,
      'fail': [[4001715, 1]]
   },
   {
      'itemid': 1032208, 'qty': 1, 'allstat': 20, 'atk': 20,
      'recipes': [[1032207, 1], [4310237, 2000], [4001715, 100], [4009005, 900], [2450064, 3]], 'price': 10, 'chance': 50,
      'fail': [[4001715, 1]]
   },
   {
      'itemid': 1032209, 'qty': 1, 'allstat': 35, 'atk': 35,
      'recipes': [[1032208, 1], [4310237, 7000], [4001715, 300], [4009005, 1500], [2450064, 4]], 'price': 10, 'chance': 40,
      'fail': [[4001715, 1]]
   },
] // All Stat and Attack/Magic are only applied to equipment items

var item;
var isEquip = false;
var canMake = false;

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
   if (status == 0) {
      var msg = "#fs11#โปรดเลือกไอเท็มที่ต้องการสร้าง" + enter;
      msg += "#fs11#ข้อมูลสูตรและไอเท็มจะปรากฏเมื่อเลือก#fs12##b" + enter;
      msg += "#fs11##kผลลัพธ์ขั้นที่ 1 : #bได้รับ EXP เพิ่มขึ้น 20% เมื่อสวมใส่" + enter;
      msg += "#kผลลัพธ์ขั้นที่ 2 : #bได้รับ EXP เพิ่มขึ้น 30% เมื่อสวมใส่" + enter;
      msg += "#kผลลัพธ์ขั้นที่ 3 : #bได้รับ EXP เพิ่มขึ้น 40% เมื่อสวมใส่" + enter;
      msg += "#kผลลัพธ์ขั้นที่ 4 : #bได้รับ EXP เพิ่มขึ้น 50% เมื่อสวมใส่#k" + enter;
      for (i = 0; i < items.length; i++)
         msg += "#fs11##L" + i + "##i" + items[i]['itemid'] + "##z" + items[i]['itemid'] + "# สร้าง" + enter;

      cm.sendSimple(msg);
   } else if (status == 1) {
      seld = sel;
      item = items[sel];
      isEquip = Math.floor(item['itemid'] / 1000000) == 1;

      canMake = checkItems(item);

      var msg = "#fs11#ไอเท็มที่เลือกมีดังนี้#fs11##b" + enter;
      msg += "#fs11#ไอเท็ม : #i" + item['itemid'] + "##z" + item['itemid'] + "#" + enter;

      if (isEquip) {
         if (item['allstat'] > 0)
            msg += "All Stat : +" + item['allstat'] + enter;
         if (item['atk'] > 0)
            msg += "Attack, Magic : +" + item['atk'] + enter;
      }

      msg += enter;
      msg += "#fs11##kนี่คือสูตรสำหรับการสร้างไอเท็มที่เลือก#fs11##d" + enter + enter;

      if (item['recipes'].length > 0) {
         for (i = 0; i < item['recipes'].length; i++)
            msg += "#b#i" + item['recipes'][i][0] + "##z" + item['recipes'][i][0] + "# " + item['recipes'][i][1] + " ชิ้น #r/ #c" + item['recipes'][i][0] + "# ชิ้น ที่มีอยู่#k" + enter;
      }

      if (item['price'] > 0)
         // msg += "#i5200002#"+item['price']+" Meso"+enter;

         msg += enter + "#fs11##eโอกาสสำเร็จ : " + item['chance'] + "%#n" + enter + enter;
      msg += "#kหากล้มเหลว จะได้รับไอเท็มดังต่อไปนี้#fs11##d" + enter + enter;
      if (item['fail'].length > 0) {
         for (i = 0; i < item['fail'].length; i++)
            msg += "#i" + item['fail'][i][0] + "##z" + item['fail'][i][0] + "# " + item['fail'][i][1] + " ชิ้น" + enter;
      }
      msg += "#fs11#" + enter;
      msg += canMake ? "#bรวบรวมวัตถุดิบครบแล้ว" + enter + "กด 'ใช่' เพื่อเริ่มการสร้าง" : "#rวัตถุดิบไม่เพียงพอสำหรับการสร้างไอเท็มที่เลือก";

      if (canMake) cm.sendYesNo(msg);
      else {
         cm.sendOk(msg);
         cm.dispose();
      }

   } else if (status == 2) {
      canMake = checkItems(item);

      if (!canMake) {
         cm.sendOk("กรุณาตรวจสอบว่ามีวัตถุดิบเพียงพอหรือไม่");
         cm.dispose();
         return;
      }

      if (cm.getInvSlots(6) < 5) {
         cm.sendOkS("#fs11##fc0xFF6600CC#กรุณาทำช่องว่างในกระเป๋าช่อง Cash อย่างน้อย 5 ช่อง", 2);
         cm.dispose();
         return;
      }

      payItems(item);
      if (Packages.objects.utils.Randomizer.rand(1, 100) <= item['chance']) {
         gainItem(item);
         cm.sendOk("#fs11#ยินดีด้วย! การสร้างสำเร็จ");
         cm.worldGMMessage(21, "[Exp Earring] คุณ " + cm.getPlayer().getName() + " ได้สร้าง [" + cm.getItemName(item['itemid']) + "]");
      } else {
         cm.sendOk("#fs11#การสร้างล้มเหลว ได้รับ #bItem Crystal#k ชดเชย");
         gainFail(item);
      }
      cm.dispose();
   }
}

function checkItems(i) {
   recipe = i['recipes'];
   ret = true;

   for (j = 0; j < recipe.length; j++) {
      if (!cm.haveItem(recipe[j][0], recipe[j][1])) {
         ret = false;
         break;
      }
   }
   if (ret) ret = cm.getPlayer().getMeso() >= i['price'];

   return ret;
}

function payItems(i) {
   recipe = i['recipes'];
   for (j = 0; j < recipe.length; j++) {
      if (Math.floor(recipe[j][0] / 1000000) == 1)
         Packages.objects.item.MapleInventoryManipulator.removeById(cm.getClient(), Packages.objects.item.MapleInventoryType.EQUIP, recipe[j][0], 1, false, false);
      else cm.gainItem(recipe[j][0], -recipe[j][1]);
      cm.gainMeso(-1000000);
   }
}

function gainItem(i) {
   ise = Math.floor(i['itemid'] / 1000000) == 1;
   if (ise) {
      vitem = Packages.objects.item.MapleItemInformationProvider.getInstance().getEquipById(i['itemid']);
      if (i['allstat'] > 0) {
         vitem.setStr(i['allstat']);
         vitem.setDex(i['allstat']);
         vitem.setInt(i['allstat']);
         vitem.setLuk(i['allstat']);
      }
      if (i['atk'] > 0) {
         vitem.setWatk(i['atk']);
         vitem.setMatk(i['atk']);
      }
      Packages.objects.item.MapleInventoryManipulator.addFromDrop(cm.getClient(), vitem, false);
   } else {
      cm.gainItem(i['itemid'], i['qty']);
   }
}
function gainFail(i) {
   fail = i['fail'];

   for (j = 0; j < fail.length; j++) {
      cm.gainItem(fail[j][0], fail[j][1]);
   }
}