var enter = "\r\n";
var seld = -1;

black = "#fc0xFF000000#"

var items = [
    {
        'itemid': 1032317, 'qty': 1, 'allstat': 0, 'atk': 0,
        'recipes': [[1032316, 1], [4310237, 5000], [4009005, 2000], [4033172, 500], [4001878, 2000], [4031227, 1000], [4001715, 600]], 'price': 0, 'chance': 50,
        'fail': [[1032316, 1]]
    },
    {
        'itemid': 1122431, 'qty': 1, 'allstat': 0, 'atk': 0,
        'recipes': [[1122430, 1], [4310237, 5000], [4009005, 2000], [4033172, 500], [4001878, 2000], [4031227, 1000], [4001715, 600]], 'price': 0, 'chance': 50,
        'fail': [[1122430, 1]]
    },
    {
        'itemid': 1132311, 'qty': 1, 'allstat': 0, 'atk': 0,
        'recipes': [[1132308, 1], [4310237, 5000], [4009005, 2000], [4033172, 500], [4001878, 2000], [4031227, 1000], [4001715, 600]], 'price': 0, 'chance': 50,
        'fail': [[1132308, 1]]
    },
    {
        'itemid': 1672078, 'qty': 1, 'allstat': 0, 'atk': 0,
        'recipes': [[1672082, 1], [4310237, 5000], [4009005, 2000], [4033172, 500], [4001878, 2000], [4031227, 1000], [4001715, 600]], 'price': 0, 'chance': 50,
        'fail': [[1672082, 1]]
    },
    {
        'itemid': 1113307, 'qty': 1, 'allstat': 0, 'atk': 0,
        'recipes': [[1113306, 1], [4310237, 5000], [4009005, 2000], [4033172, 500], [4001878, 2000], [4031227, 1000], [4001715, 600]], 'price': 0, 'chance': 50,
        'fail': [[1113306, 1]]
    },
    {
        'itemid': 1182286, 'qty': 1, 'allstat': 0, 'atk': 0,
        'recipes': [[1182285, 1], [4310237, 5000], [4009005, 2000], [4033172, 500], [4001878, 2000], [4031227, 1000], [4001715, 600]], 'price': 0, 'chance': 50,
        'fail': [[1182285, 1]]
    },
    {
        'itemid': 1162078, 'qty': 1, 'allstat': 0, 'atk': 0,
        'recipes': [[1162080, 1], [1162081, 1], [1162082, 1], [1162083, 1], [4310237, 5000], [4009005, 2000], [4033172, 500], [4001878, 2000], [4031227, 1000], [4001715, 600]], 'price': 0, 'chance': 100,
        'fail': [[1162078, 1]]
    },
    {
        'itemid': 1190564, 'qty': 1, 'allstat': 0, 'atk': 0,
        'recipes': [[1190555, 1], [1190556, 1], [1190557, 1], [1190558, 1], [1190559, 1], [4310237, 5000], [4009005, 2000], [4033172, 500], [4001878, 2000], [4031227, 1000], [4001715, 600]], 'price': 0, 'chance': 100,
        'fail': [[1190564, 1]]
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
    var msg = "#fs11#"
    if (status == 0) {
        itemsId = 1;
        msg += black + "#h0# กรุณาเลือกไอเท็มที่ต้องการคราฟ#fc0xFF990033#\r\n\r\n"
        for (i = 0; i < items.length; i++) {
            msg += "#L" + i + "##i" + items[i]['itemid'] + "# #z" + items[i]['itemid'] + "# " + items[i]['qty'] + " ชิ้น" + enter;
        }
        cm.sendSimple(msg);
    } else if (status == 1) {
        seld = sel;
        item = items[sel];
        isEquip = Math.floor(item['itemid'] / 1000000) == 1;

        canMake = checkItems(item);

        var msg = "ไอเท็มที่เลือกมีดังนี้:#fs11##b" + enter;
        msg += "ไอเท็ม: #i" + item['itemid'] + "##z" + item['itemid'] + "# " + item['qty'] + " ชิ้น" + enter;

        if (isEquip) {
            if (item['allstat'] > 0)
                msg += "All Stats: +" + item['allstat'] + enter;
            if (item['atk'] > 0)
                msg += "ATT, MATT: +" + item['atk'] + enter;
        }

        msg += enter;
        msg += "#fs12##kนี่คือวัสดุสำหรับคราฟไอเท็มที่เลือก:#fs11##d" + enter + enter;

        if (item['recipes'].length > 0) {
            for (i = 0; i < item['recipes'].length; i++)
                msg += "#i" + item['recipes'][i][0] + "##z" + item['recipes'][i][0] + "# " + item['recipes'][i][1] + " ชิ้น" + enter;
        }

        if (item['price'] > 0)
            msg += "#i5200002#" + item['price'] + " Meso (15B)" + enter;

        msg += enter + "#fs12##eอัตราความสำเร็จ: " + item['chance'] + "%#n" + enter + enter;
        msg += "#kเมื่อคราฟล้มเหลว จะได้รับไอเท็มดังต่อไปนี้:#fs11##d" + enter + enter;
        if (item['fail'].length > 0) {
            for (i = 0; i < item['fail'].length; i++)
                msg += "#i" + item['fail'][i][0] + "##z" + item['fail'][i][0] + "# " + item['fail'][i][1] + " ชิ้น" + enter;
        }
        msg += "#fs12#" + enter;
        msg += canMake ? "#bวัสดุครบถ้วนแล้วสำหรับคราฟไอเท็มนี้" + enter + "กด 'Yes' เพื่อยืนยันการคราฟ" : "#rวัสดุไม่เพียงพอสำหรับคราฟไอเท็มนี้";
        if (canMake) { cm.sendYesNo(msg); }
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
            cm.sendOk("แลกเปลี่ยนสำเร็จ");
            cm.worldGMMessage(21, "[Chaos Pitch Black] " + cm.getPlayer().getName() + " crafted [" + cm.getItemName(item['itemid']) + "] successfully!");
        } else {
            cm.sendOk("อัพเกรดล้มเหลว");
            cm.worldGMMessage(21, "[Chaos Pitch Black] " + cm.getPlayer().getName() + " failed to craft [" + cm.getItemName(item['itemid']) + "]");
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




