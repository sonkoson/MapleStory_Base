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
        var talk = "#fs11#ฉันคือ #e#bถังขยะเสื้อผ้า#n#k ที่คอยเก็บรวบรวมเสื้อผ้าที่ไม่ใช้แล้ว\r\nคุณสามารถทิ้งไอเท็มสวมใส่แคชและไอเท็มแคชได้ กรุณาเลือกเมนู\r\n"
        talk += "#r(หมายเหตุ: ไอเท็มที่ทิ้งแล้วจะหายไปและไม่สามารถกู้คืนได้!)#k\r\n\r\n"
        talk += "#L1# #bไอเท็มแคช#l\r\n";
        talk += "#L0# ไอเท็มแฟชั่นแคช#l\r\n";
        talk += "#L2# ไอเท็มอาวุธรอง#k#l\r\n";
        cm.sendSimple(talk);
    } else if (status == 1) {
        st2 = selection;
        if (selection == 0) {
            talk = "#fs11#กรุณาเลือกไอเท็มสวมใส่แคชที่ต้องการทิ้ง\r\n"
            talk += "#r(หมายเหตุ: ไอเท็มที่ทิ้งแล้วจะหายไปและ #eไม่สามารถกู้คืนได้#n!)#k\r\n\r\n"
            for (i = 0; i <= cm.getInventory(6).getSlotLimit(); i++) {
                if (cm.getInventory(6).getItem(i) != null && cm.isCash(cm.getInventory(6).getItem(i).getItemId())) {
                    talk += "#L" + i + "# #i" + cm.getInventory(6).getItem(i).getItemId() + "# #b#z" + cm.getInventory(6).getItem(i).getItemId() + "##k#l\r\n"
                }
            }
        } else if (selection == 1) {
            talk = "#fs11#กรุณาเลือกไอเท็มแคชที่ต้องการทิ้ง สัตว์เลี้ยงต้องถอดอุปกรณ์ก่อนจึงจะลบได้\r\n"
            talk += "#r(หมายเหตุ: ไอเท็มที่ทิ้งแล้วจะหายไปและ #eไม่สามารถกู้คืนได้#n!)#k\r\n\r\n"
            for (i = 0; i <= cm.getInventory(5).getSlotLimit(); i++) {
                if (cm.getInventory(5).getItem(i) != null) {
                    talk += "#L" + i + "# #i" + cm.getInventory(5).getItem(i).getItemId() + "# #b#z" + cm.getInventory(5).getItem(i).getItemId() + "##k#l\r\n"
                }
            }
        } else if (selection == 2) {
            talk = "#fs11#กรุณาเลือกไอเท็มอาวุธรองที่ต้องการทิ้ง\r\n"
            talk += "#r(หมายเหตุ: ไอเท็มที่ทิ้งแล้วจะหายไปและ #eไม่สามารถกู้คืนได้#n!)#k\r\n\r\n"
            for (i = 0; i <= cm.getInventory(1).getSlotLimit(); i++) {
                item = cm.getInventory(1).getItem(i);
                if (item != null && (Math.floor(item.getItemId() / 10000) == 109 || Math.floor(item.getItemId() / 10000) == 134 || Math.floor(item.getItemId() / 10000) == 135) && !cm.isCash(cm.getInventory(1).getItem(i).getItemId())) {
                    talk += "#L" + i + "# #i" + cm.getInventory(1).getItem(i).getItemId() + "# #b#z" + cm.getInventory(1).getItem(i).getItemId() + "##k#l\r\n"
                }
            }
        }
        if (selection == 3) {
            cm.getPlayer().SkillMasterJob(501);
            cm.sendOk("#fs11#ดำเนินการเรียบร้อยแล้ว");
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
        talk = "#fs11#คุณแน่ใจหรือไม่ว่าต้องการลบไอเท็มนี้ออกจากกระเป๋า?\r\n"
        talk += "#r(หมายเหตุ: ไอเท็มที่ทิ้งแล้วจะหายไปและไม่สามารถกู้คืนได้!)#k\r\n\r\n"
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
                            cm.sendOk("#fs11#คุณไม่สามารถลบสัตว์เลี้ยงนี้ได้เนื่องจากกำลังสวมใส่อยู่");
                            cm.dispose();
                            return;
                        }
                    }
                }
            }
            Packages.objects.item.MapleInventoryManipulator.removeFromSlot(cm.getClient(), Packages.objects.item.MapleInventoryType.CASH, st, cm.getInventory(5).getItem(st).copy().getQuantity(), true);
        }
        cm.sendYesNo("#fs11#ลบไอเท็มออกจากกระเป๋าเรียบร้อยแล้ว\r\n#bคุณมีไอเท็มอื่นที่ต้องการทิ้งอีกหรือไม่?");
    } else if (status == 4) {
        cm.dispose();
        cm.openNpcCustom(cm.getClient(), 1012121, "ItemDiscard");
    }
}
