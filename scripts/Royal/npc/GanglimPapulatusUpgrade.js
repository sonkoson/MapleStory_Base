var enter = "\r\n";
var seld = -1;

var items = [
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
] // All Stats and ATK/MATT only apply to equipment items

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
        var msg = "#fs11#กรุณาเลือกไอเท็มที่ต้องการคราฟ" + enter;
        msg += "#fs11#Recipe และข้อมูลไอเท็มจะแสดงเมื่อเลือก#fs12##b" + enter;
        for (i = 0; i < items.length; i++)
            msg += "#fs11##L" + i + "##i" + items[i]['itemid'] + "##z" + items[i]['itemid'] + "# Craft" + enter;

        cm.sendSimple(msg);
    } else if (status == 1) {
        seld = sel;
        item = items[sel];
        isEquip = Math.floor(item['itemid'] / 1000000) == 1;

        canMake = checkItems(item);

        var msg = "#fs11#ไอเท็มที่คุณเลือกมีดังนี้:#fs11##b" + enter;
        msg += "#fs11#ไอเท็ม: #i" + item['itemid'] + "##z" + item['itemid'] + "#" + enter;

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
        if (item['price'] > 0)

            msg += enter + "#fs11##eอัตราความสำเร็จ: " + item['chance'] + "%#n" + enter + enter;
        msg += "#kเมื่อคราฟล้มเหลว จะได้รับไอเท็มดังต่อไปนี้:#fs11##d" + enter + enter;
        if (item['fail'].length > 0) {
            for (i = 0; i < item['fail'].length; i++)
                msg += "#i" + item['fail'][i][0] + "##z" + item['fail'][i][0] + "# " + item['fail'][i][1] + " ชิ้น" + enter;
        }
        msg += "#fs11#" + enter;
        msg += canMake ? "#bวัสดุครบถ้วนแล้วสำหรับคราฟไอเท็มนี้" + enter + "กด 'Yes' เพื่อยืนยันการคราฟ" : "#rวัสดุไม่เพียงพอสำหรับคราฟไอเท็มนี้";

        if (canMake) cm.sendYesNo(msg);
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
            cm.worldGMMessage(21, "[Chaos Papulatus] " + cm.getPlayer().getName() + " crafted [" + cm.getItemName(item['itemid']) + "]");
        } else {
            cm.sendOk("#fs11#คราฟล้มเหลว");
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
