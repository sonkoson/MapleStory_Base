var enter = "\r\n";
var seld = -1;

var items = [{
    'itemid': 2439958,
    'qty': 1,
    'allstat': 0,
    'atk': 0,
    'recipes': [[2439961, 3], [4310237, 8000], [4310308, 5000], [4031227, 1000], [4001715, 1000]],
    'price': 0,
    'chance': 100,
    'fail': [[2439958, 1]]
},
]

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
        if (cm.getInvSlots(1) < 3 || cm.getInvSlots(2) < 3 || cm.getInvSlots(3) < 3 || cm.getInvSlots(4) < 3 || cm.getInvSlots(5) < 3) {
            cm.sendOkS("#fs11##fc0xFF6600CC#กรุณาเคลียร์ช่องเก็บของอย่างน้อย 3 ช่องในแต่ละแท็บ", 2);
            cm.dispose();
            return;
        }
        var msg = "#fs11#กรุณาเลือกไอเท็มที่ต้องการคราฟ" + enter + enter;
        msg += "#fs11##b#e[Destruction Arcane Shade]#k จะได้รับออปชั่นเพิ่มเติม\r\n#r[ All Stats + 15% Damage + 15% Boss ATT + 15% Upgrade Slots 19 ]#n#k#fs12##b" + enter;
        for (i = 0; i < items.length; i++)
            msg += "#fs11##L" + i + "##i" + items[i]['itemid'] + "##z" + items[i]['itemid'] + "# " + items[i]['qty'] + " ชิ้น" + enter;

        cm.sendSimple(msg);
    } else if (status == 1) {
        seld = sel;
        item = items[sel];
        isEquip = Math.floor(item['itemid'] / 1000000) == 1;

        canMake = checkItems(item);

        var msg = "#fs11#ไอเท็มที่คุณเลือกมีดังนี้:#fs11##b" + enter;
        msg += "#fs11#ไอเท็ม: #i" + item['itemid'] + "##z" + item['itemid'] + "# " + item['qty'] + " ชิ้น" + enter;

        if (isEquip) {
            if (item['allstat'] > 0)
                msg += "All Stats: +" + item['allstat'] + enter;
            if (item['atk'] > 0)
                msg += "ATT, MATT: +" + item['atk'] + enter;
        }

        msg += enter;
        msg += "#fs11##kนี่คือ Recipe สำหรับคราฟไอเท็มที่เลือก:#fs11##d" + enter + enter;

        if (item['recipes'].length > 0) {
            for (i = 0; i < item['recipes'].length; i++)
                msg += "#b#i" + item['recipes'][i][0] + "##z" + item['recipes'][i][0] + "# " + item['recipes'][i][1] + " ชิ้น #r/ #c" + item['recipes'][i][0] + "# ที่มีอยู่#k" + enter;
        }

        if (item['price'] == 0)

            msg += enter + "#fs11##eอัตราความสำเร็จ: " + item['chance'] + "%#n" + enter + enter;
        msg += "#kเมื่อคราฟสำเร็จ จะได้รับไอเท็มดังต่อไปนี้:#fs11##d" + enter + enter;
        if (item['fail'].length > 0) {
            for (i = 0; i < item['fail'].length; i++)
                msg += "#i" + item['fail'][i][0] + "##z" + item['fail'][i][0] + "# " + item['fail'][i][1] + " ชิ้น" + enter;
        }
        msg += "#fs11#" + enter;
        msg += canMake ? "#bวัสดุครบถ้วนแล้วสำหรับคราฟไอเท็มนี้" + enter + "กด 'Yes' เพื่อยืนยันการคราฟ" : "#rวัสดุไม่เพียงพอสำหรับคราฟไอเท็มนี้";

        if (canMake)
            cm.sendYesNo(msg);
        else {
            cm.sendOk(msg);
            cm.dispose();
        }

    } else if (status == 2) {
        canMake = checkItems(item);

        if (!canMake) {
            cm.sendOk("กรุณาตรวจสอบว่าวัสดุเพียงพอหรือไม่");
            cm.dispose();
            return;
        }
        payItems(item);
        if (Packages.objects.utils.Randomizer.rand(1, 100) <= item['chance']) {
            gainItem(item);
            cm.sendOk("#fs11#ยินดีด้วย! คราฟสำเร็จ");
            cm.worldGMMessage(21, "[Destruction Arcane] " + cm.getPlayer().getName() + " crafted [" + cm.getItemName(item['itemid']) + "] successfully!");
        } else {
            cm.sendOk("#fs11#คราฟล้มเหลว");
            cm.worldGMMessage(21, "[Destruction Arcane] " + cm.getPlayer().getName() + " failed to craft [" + cm.getItemName(item['itemid']) + "]");
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
    if (ret)
        ret = cm.getPlayer().getMeso() >= i['price'];

    return ret;
}

function payItems(i) {
    recipe = i['recipes'];
    for (j = 0; j < recipe.length; j++) {
        if (Math.floor(recipe[j][0] / 1000000) == 1)
            Packages.objects.item.MapleInventoryManipulator.removeById(cm.getClient(), Packages.objects.item.MapleInventoryType.EQUIP, recipe[j][0], 1, false, false);
        else
            cm.gainItem(recipe[j][0], -recipe[j][1]);
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




