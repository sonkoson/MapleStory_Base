var enter = "\r\n";
var seld = -1;

var items = [
   {
      'itemid': 2048753, 'qty': 10, 'allstat': 0, 'atk': 0,
      'recipes': [[2048717, 100], [4009005, 100], [4001715, 10]], 'price': 10, 'chance': 100,
      'fail': [[4001715, 1]]
   },
   {
      'itemid': 2048753, 'qty': 100, 'allstat': 0, 'atk': 0,
      'recipes': [[2048717, 1000], [4009005, 1000], [4001715, 100]], 'price': 100, 'chance': 100,
      'fail': [[4001715, 1]]
   },
   {
      'itemid': 2002093, 'qty': 1, 'allstat': 0, 'atk': 0,
      'recipes': [[4000916, 1], [4021031, 200], [4001878, 30], [4009005, 200]], 'price': 10, 'chance': 100,
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
      var msg = "#fs11#ต้องการสร้างไอเท็มใช้งานหรือไม่?#k#n" + enter;
      msg += "";
      for (i = 0; i < items.length; i++)
         msg += "#fs11##b#L" + i + "##i" + items[i]['itemid'] + "##z" + items[i]['itemid'] + "# " + items[i]['qty'] + " ชิ้น#k" + enter;

      cm.sendSimple(msg);
   } else if (status == 1) {
      seld = sel;
      item = items[sel];
      isEquip = Math.floor(item['itemid'] / 1000000) == 1;

      canMake = checkItems(item);

      var msg = "#fs11#ไอเท็มที่เลือกมีดังนี้#fs11##b" + enter;
      msg += "#fs11#ไอเท็ม : #i" + item['itemid'] + "##z" + item['itemid'] + "# " + item['qty'] + " ชิ้น" + enter;

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
         msg += "#fs11#" + enter;
      msg += canMake ? "#rรวบรวมวัตถุดิบครบแล้ว" + enter + "กด 'ใช่' เพื่อเริ่มการสร้าง" : "#rวัตถุดิบไม่เพียงพอสำหรับการสร้างไอเท็มที่เลือก";

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
      payItems(item);
      if (Packages.objects.utils.Randomizer.rand(1, 100) <= item['chance']) {
         gainItem(item);
         cm.sendOk("#fs11#การสร้างสำเร็จ!");
      } else {
         cm.sendOk("#fs11#การสร้างล้มเหลว");
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
         //cm.getPlayer().dropMessage(6, "fas");
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