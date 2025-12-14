importPackage(Packages.constants);
var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
        talk = "#fs11#I am the #e#bClothing Bin#n#k that collects unused clothes.\r\nYou can drop Cash Equip items and Cash items. Please select a menu.\r\n"
        talk += "#r(Note: Dropped items will disappear and cannot be recovered!)#k\r\n\r\n"
        talk += "#L1# #bCash Item#l\r\n";
        talk += "#L0# Cash Coordi Item#l\r\n";
        talk += "#L2# Secondary Weapon Item#k#l\r\n";
        cm.sendSimple(talk);
    } else if (status == 1) {
        st2 = selection;
        if (selection == 0) {
            talk = "#fs11#Please select the Cash Equip item you want to drop.\r\n"
            talk += "#r(Note: Dropped items will disappear and #ecannot be recovered#n!)#k\r\n\r\n"
            for (i = 0; i <= cm.getInventory(6).getSlotLimit(); i++) {
                if (cm.getInventory(6).getItem(i) != null && cm.isCash(cm.getInventory(6).getItem(i).getItemId())) {
                    talk += "#L" + i + "# #i" + cm.getInventory(6).getItem(i).getItemId() + "# #b#z" + cm.getInventory(6).getItem(i).getItemId() + "##k#l\r\n"
                }
            }
        } else if (selection == 1) {
            talk = "#fs11#Please select the Cash Item you want to drop. Pets can only be removed if #runequipped#k.\r\n"
            talk += "#r(Note: Dropped items will disappear and #ecannot be recovered#n!)#k\r\n\r\n"
            for (i = 0; i <= cm.getInventory(5).getSlotLimit(); i++) {
                if (cm.getInventory(5).getItem(i) != null) {
                    talk += "#L" + i + "# #i" + cm.getInventory(5).getItem(i).getItemId() + "# #b#z" + cm.getInventory(5).getItem(i).getItemId() + "##k#l\r\n"
                }
            }
        } else if (selection == 2) {
            talk = "#fs11#Please select the Secondary Weapon item you want to drop.\r\n"
            talk += "#r(Note: Dropped items will disappear and #ecannot be recovered#n!)#k\r\n\r\n"
            for (i = 0; i <= cm.getInventory(1).getSlotLimit(); i++) {
                item = cm.getInventory(1).getItem(i);
                if (item != null && (Math.floor(item.getItemId() / 10000) == 109 || Math.floor(item.getItemId() / 10000) == 134 || Math.floor(item.getItemId() / 10000) == 135) && !cm.isCash(cm.getInventory(1).getItem(i).getItemId())) {
                    talk += "#L" + i + "# #i" + cm.getInventory(1).getItem(i).getItemId() + "# #b#z" + cm.getInventory(1).getItem(i).getItemId() + "##k#l\r\n"
                }
            }
        }
        if (selection == 3) {
            cm.getPlayer().SkillMasterJob(501);
            cm.sendOk("#fs11#Complete");
            cm.dispose();
        } else {
            cm.sendSimple(talk);
        }
    } else if (status == 2) {
        st = selection;
        if (st < 0) {
            cm.dispose();
            return;
        }
        talk = "#fs11#Are you sure you want to remove this item from your inventory?\r\n"
        talk += "#r(Note: Dropped items will disappear and cannot be recovered!)#k\r\n\r\n"
        iv = st2 == 0 ? 6 : st2 == 2 ? 1 : 5;
        talk += "#i" + cm.getInventory(iv).getItem(st).getItemId() + "# #b#z" + cm.getInventory(iv).getItem(st).getItemId() + "##k"
        cm.sendYesNo(talk);
    } else if (status == 3) {
        if (st2 == 0) {
            Packages.objects.item.MapleInventoryManipulator.removeFromSlot(cm.getClient(), Packages.objects.item.MapleInventoryType.CASH_EQUIP, st, cm.getInventory(6).getItem(st).copy().getQuantity(), true);
        } else if (st2 == 2) {
            Packages.objects.item.MapleInventoryManipulator.removeFromSlot(cm.getClient(), Packages.objects.item.MapleInventoryType.EQUIP, st, cm.getInventory(1).getItem(st).copy().getQuantity(), true);
        } else {
            if (Packages.constants.GameConstants.isPet(cm.getInventory(5).getItem(st).getItemId())) {
                for (i = 0; i < cm.getPlayer().getPets().length; i++) {
                    if (cm.getPlayer().getPets()[i] != null) {
                        if (cm.getPlayer().getPets()[i].getInventoryPosition() == st) {
                            cm.sendOk("#fs11#You cannot remove this pet because it is currently equipped.");
                            cm.dispose();
                            return;
                        }
                    }
                }
            }
            Packages.objects.item.MapleInventoryManipulator.removeFromSlot(cm.getClient(), Packages.objects.item.MapleInventoryType.CASH, st, cm.getInventory(5).getItem(st).copy().getQuantity(), true);
        }
        cm.sendYesNo("#fs11#Successfully removed the item from your inventory.\r\n#bDo you have more items to drop?");
    } else if (status == 4) {
        cm.dispose();
        cm.openNpcCustom(cm.getClient(), 1012121, "아이템버리기");
    }
}