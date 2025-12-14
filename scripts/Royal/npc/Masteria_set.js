var enter = "\r\n";
var seld = -1;

var items = [
   {
      'itemid': 1113305, 'qty': 1, 'allstat': 0, 'atk': 0,
      'recipes': [[4310237, 20000], [4021040, 10], [4021031, 9000], [4001715, 300], [4009005, 6000]], 'price': 0, 'chance': 100,
      'fail': [[4001715, 10]]
   },
   {
      'itemid': 1122439, 'qty': 1, 'allstat': 0, 'atk': 0,
      'recipes': [[4310237, 20000], [4031466, 15], [4001715, 300], [4009005, 3000]], 'price': 0, 'chance': 100,
      'fail': [[4021015, 10]]
   },
] // All stat and ATK/MATK apply only to equipment

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
      var msg = "#fs11#กรุณาเลือกไอเท็มที่ต้องการสร้าง" + enter;
      msg += "#fs11#ข้อมูลสูตรและไอเท็มจะแสดงเมื่อเลือก#fs12##b" + enter;
      for (i = 0; i < items.length; i++)
         msg += "#fs11##L" + i + "##i" + items[i]['itemid'] + "##z" + items[i]['itemid'] + "# สร้าง" + enter;

      cm.sendSimple(msg);
   } else if (status == 1) {
      seld = sel;
      item = items[sel];
      isEquip = Math.floor(item['itemid'] / 1000000) == 1;

      canMake = checkItems(item);

      var msg = "#fs11#ไอเท็มที่ท่านเลือกมีดังนี้#fs11##b" + enter;
      msg += "#fs11#ไอเท็ม : #i" + item['itemid'] + "##z" + item['itemid'] + "#" + enter;

      if (isEquip) {
         if (item['allstat'] > 0)
            msg += "All Stat : +" + item['allstat'] + enter;
         if (item['atk'] > 0)
            msg += "Att, Matt : +" + item['atk'] + enter;
      }

      msg += enter;
      msg += "#fs11##kนี่คือสูตรสำหรับสร้างไอเท็มที่ท่านเลือก#fs11##d" + enter + enter;

      if (item['recipes'].length > 0) {
         for (i = 0; i < item['recipes'].length; i++)
            msg += "#b#i" + item['recipes'][i][0] + "##z" + item['recipes'][i][0] + "# " + item['recipes'][i][1] + " ชิ้น #r/ #c" + item['recipes'][i][0] + "# ชิ้น (มีอยู่)#k" + enter;
      }

      msg += "#fs11#" + enter;
      msg += canMake ? "#bรวบรวมวัสดุสำหรับสร้างไอเท็มที่เลือกครบแล้ว" + enter + "หากต้องการสร้างจริงๆ กรุณากด 'ใช่'" : "#rวัสดุสำหรับสร้างไอเท็มที่เลือกยังไม่พอ";

      if (canMake) cm.sendYesNo(msg);
      else {
         cm.sendOk(msg);
         cm.dispose();
      }

   } else if (status == 2) {
      canMake = checkItems(item);

      if (!canMake) {
         cm.sendOk("กรุณาตรวจสอบอีกครั้งว่ามีวัสดุเพียงพอหรือไม่");
         cm.dispose();
         return;
      }
      payItems(item);
      if (Packages.objects.utils.Randomizer.rand(1, 100) <= item['chance']) {
         gainItem(item);
         cm.sendOk("#fs11#ยินดีด้วย การสร้างสำเร็จแล้ว");
         cm.worldGMMessage(21, "[Alchemy] คุณ " + cm.getPlayer().getName() + " ได้สร้าง [" + cm.getItemName(item['itemid']) + "]");
      } else {
         cm.sendOk("#fs11#การสร้างล้มเหลว ได้รับ #bItem Crystal#k เป็นการตอบแทน");
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