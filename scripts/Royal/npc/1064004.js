importPackage(Packages.constants);
importPackage(Packages.objects.item);

var status = -1;

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        var chat = "รู้ใช่ไหมว่าต้องใช้ #bOld Tree Key#k เพื่อไปพบผู้พิทักษ์แห่งการผนึก? ถ้าต้องการล่ะก็ บอกข้าได้เลย.#b\r\n\r\n";
        chat += "#L1# ซื้อ #i4033611# #z4033611# ในราคา 400,000 meso\r\n";
        chat += "#L2# ซื้อ #i4033611# #z4033611# 10 ชิ้น ในราคา 3,600,000 meso\r\n";
        //chat += "#L2# #i4310064# #z4310064#을 교환하기";
        cm.sendSimple(chat);

    } else if (status == 1) {
        var leftslot = cm.getPlayer().getInventory(Packages.objects.item.MapleInventoryType.ETC).getNumFreeSlot();
        if (leftslot < 3) {
            cm.sendOk("ต้องการช่องว่างในช่องเก็บของอย่างน้อย 3 ช่อง กรุณาทำช่องว่างในแท็บ Etc แล้วลองใหม่อีกครั้ง");
            cm.dispose();
            return;
        }
        if (selection == 1) {
            if (cm.getMeso() >= 400000) {
                cm.gainItem(4033611, 1);
                cm.gainMeso(-400000);
                cm.sendOk("โอกาสหน้าเชิญใหม่นะ");
                cm.dispose();
            } else {
                cm.sendOk("ไม่มี meso แล้วจะมาซื้อกุญแจต้นไม้เก่าแก่อย่างนั้นรึ?");
                cm.dispose();
            }
        } else if (selection == 2) {
            if (cm.getMeso() >= 3600000) {
                cm.gainItem(4033611, 10);
                cm.gainMeso(-3600000);
                cm.sendOk("โอกาสหน้าเชิญใหม่นะ");
                cm.dispose();
            } else {
                cm.sendOk("ไม่มี meso แล้วจะมาซื้อกุญแจต้นไม้เก่าแก่อย่างนั้นรึ?");
                cm.dispose();
            }

        }
    }
}