var enter = "\r\n";
var seld = -1;

var a = "";

var price = 30;
var allstat = 2, atk = 2; // All stat, Attack/Magic Attack increase per time

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
        var txt = "#fs11##fc0xFF000000#Did you know you can upgrade Cash Items with #b#i4310308##z4310308# 30 pieces#fc0xFF000000#? #bPlease select the Cash Item you want to upgrade.#fc0xFF000000#\r\n#r(Per upgrade: All Stat 2 / WA & MA 2 increase, Stackable)#k\r\n";
        for (i = 0; i <= cm.getInventory(6).getSlotLimit(); i++) {
            if (cm.getInventory(6).getItem(i) != null) {
                if (cm.isCash(cm.getInventory(6).getItem(i).getItemId())) {
                    txt += "#b#L" + i + "# #e#i" + cm.getInventory(6).getItem(i).getItemId() + "# #b#z" + cm.getInventory(6).getItem(i).getItemId() + "# #r[" + i + "슬롯]#k\r\n";
                }
            }
        }
        cm.sendSimple(txt);
    } else if (status == 1) {
        tem = selection;
        var suk1 = Math.floor((cm.itemQuantity(4310308) / price));
        stigmacoin = Math.min(suk1);

        item = cm.getInventory(6).getItem(tem);

        for (var i = 0; i < stigmacoin; i++) {
            if (item.getStr() + (allstat * i) >= 32000 || item.getDex() + (allstat * i) >= 32000 || item.getInt() + (allstat * i) >= 32000
                || item.getLuk() + (allstat * i) >= 32000 || item.getWatk() + (atk * i) >= 32000 || item.getMatk() + (atk * i) >= 32000) {
                stigmacoin = i;
                break;
            }
        }

        stigmacoin = Math.min(1000, stigmacoin);

        cm.sendGetNumber("\r\n#fnArial#How many times would you like to upgrade? \r\n#Cgray#(Current max upgrades possible: " + stigmacoin + " times)", 1, 1, stigmacoin);
    } else if (status == 2) {
        a = selection;

        if (!cm.haveItem(4310308, price * a)) {
            cm.sendOk("#fs11##fc0xFF000000#To upgrade " + a + " times, you need #b" + price * a + " #z4310308##fc0xFF000000#.");
            cm.dispose();
            return;
        }

        if (item == null) {
            return;
        }
        if (!cm.isCash(item.getItemId())) {
            cm.sendOk("Hack usage detected.");
            cm.dispose();
            return;
        }

        item.addStr(allstat * a);
        item.addDex(allstat * a);
        item.addInt(allstat * a);
        item.addLuk(allstat * a);
        item.addWatk(atk * a);
        item.addMatk(atk * a);
        cm.gainItem(4310308, -price * a);
        cm.sendOk("#fs11#Stats have been applied to the item. Thank you for using our service.");
        cm.dispose();

        cm.addEnchantLog(1, item.getItemId(), item.getSerialNumberEquip(), 13, 0, "Cash Option Applied " + a + " times (Account : " + cm.getClient().getAccountName() + ", Character : " + cm.getPlayer().getName() + ", Equipment Info [" + item.toString() + "])");

        cm.getPlayer().send(Packages.network.models.CWvsContext.InventoryPacket.updateInventoryItem(Packages.objects.item.MapleInventoryType.CASH_EQUIP, item, false, cm.getPlayer()));
    }
}