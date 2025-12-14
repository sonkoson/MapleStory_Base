importPackage(Packages.constants);

var enter = "\r\n";

var icon = "#fUI/GuildMark.img/BackGround/00001026/1#"
var icon2 = "#fUI/GuildMark.img/BackGround/00001028/12#"
var icon3 = "#fUI/GuildMark.img/BackGround/00001028/9#"

var star1 = "#fUI/GuildMark.img/Mark/Pattern/00004014/11#"

var num0 = "#fUI/GuildMark.img/Mark/Letter/00005026/15#"
var num1 = "#fUI/GuildMark.img/Mark/Letter/00005027/15#"
var num2 = "#fUI/GuildMark.img/Mark/Letter/00005028/4#"

var t2 = "#fUI/GuildMark.img/Mark/Letter/00005019/4#"
var o2 = "#fUI/GuildMark.img/Mark/Letter/00005014/4#"
var p2 = "#fUI/GuildMark.img/Mark/Letter/00005015/4#"

var t10 = "#fUI/GuildMark.img/Mark/Letter/00005019/15#"
var o10 = "#fUI/GuildMark.img/Mark/Letter/00005014/15#"
var p10 = "#fUI/GuildMark.img/Mark/Letter/00005015/15#"

var ranking = "#fUI/Basic.img/theblackcoin/24#";
var black = "#fc0xFF191919#";
var red = "#fc0xFFF15F5F#";
var purple = "#fc0xFF5F00FF#";

var enhance = "#fUI/Basic.img/theblackcoin/8#";

var seld = -1;

var items = [
    {
        'itemid': 1012655, 'qty': 1, 'allstat': 0, 'atk': 0, // Item ID, Qty, All Stat, Attack (Stat applies to equip only)
        'recipes': [[1012643, 1], [4310237, 30000], [4001878, 10000], [4310308, 10000], [4310266, 10000], [4031227, 5000], [4001715, 3000]], 'price': 0, 'chance': 100, // Recipe, Price, Chance
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
        msg += black + "คุณ #h0#, กรุณาเลือกไอเท็มที่ต้องการสร้าง#r\r\n\r\n"
        for (i = 0; i < items.length; i++) {
            msg += "#L" + i + "##i" + items[i]['itemid'] + "# #z" + items[i]['itemid'] + "# " + items[i]['qty'] + " ชิ้น" + enter;
        }
        cm.sendSimple(msg);
    } else if (status == 1) {
        seld = sel;
        item = items[sel];
        isEquip = Math.floor(item['itemid'] / 1000000) == 1;

        canMake = checkItems(item);

        var msg = "ไอเท็มที่เลือกมีดังนี้#fs11##b" + enter;
        msg += "ไอเท็ม : #i" + item['itemid'] + "##z" + item['itemid'] + "# " + item['qty'] + " ชิ้น" + enter;

        if (isEquip) {
            if (item['allstat'] > 0)
                msg += "สเตตัสทั้งหมด : +" + item['allstat'] + enter;
            if (item['atk'] > 0)
                msg += "พลังโจมตี, พลังเวทย์ : +" + item['atk'] + enter;
        }

        msg += enter;
        msg += "#fs12##kนี่คือวัตถุดิบสำหรับการสร้างไอเท็ม#fs11##d" + enter + enter;

        if (item['recipes'].length > 0) {
            for (i = 0; i < item['recipes'].length; i++)
                msg += "#i" + item['recipes'][i][0] + "##z" + item['recipes'][i][0] + "# " + item['recipes'][i][1] + " ชิ้น" + enter;
        }

        if (item['price'] > 0)
            msg += "#i5200002#" + item['price'] + " Meso (1 หมื่น 5 พันล้าน)" + enter;

        msg += enter + "#fs12##eอัตราสำเร็จ : " + item['chance'] + "%#n" + enter + enter;
        msg += "#kหากสำเร็จจะได้รับไอเท็มดังนี้#fs11##d" + enter + enter;
        if (item['fail'].length > 0) {
            for (i = 0; i < item['fail'].length; i++)
                msg += "#i" + item['fail'][i][0] + "##z" + item['fail'][i][0] + "# " + item['fail'][i][1] + " ชิ้น" + enter;
        }
        msg += "#fs12#" + enter;
        msg += canMake ? "#bวัตถุดิบครบแล้ว! กด 'ใช่' เพื่อสร้าง" + enter + "ต้องการดำเนินการสร้างหรือไม่?" : "#rวัตถุดิบไม่เพียงพอ";
        if (canMake) { cm.sendYesNo(msg); }
        else {
            cm.sendOk(msg);
            cm.dispose();
        }

    } else if (status == 2) {
        canMake = checkItems(item);

        if (!canMake) {
            cm.sendOk("กรุณาตรวจสอบวัตถุดิบอีกครั้ง");
            cm.dispose();
            return;
        }
        payItems(item);
        if (Packages.objects.utils.Randomizer.rand(1, 100) <= item['chance']) {
            gainItem(item);
            cm.sendOk("แลกเปลี่ยนสำเร็จ");
            cm.worldGMMessage(21, "[Ruin Pitch Black] " + cm.getPlayer().getName() + " ได้รับ [" + cm.getItemName(item['itemid']) + "] จากการอัพเกรดไอเท็ม");
        } else {
            cm.sendOk("ล้มเหลว");
            cm.worldGMMessage(21, "[Ruin Pitch Black] " + cm.getPlayer().getName() + " failed to craft [" + cm.getItemName(item['itemid']) + "].");
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
