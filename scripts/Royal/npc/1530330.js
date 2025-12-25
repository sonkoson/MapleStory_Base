importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);

importPackage(Packages.objects.item);
importPackage(Packages.objects.users);
importPackage(Packages.objects.utils);
importPackage(Packages.constants);
importPackage(Packages.network.models);
importPackage(Packages.objects.wz.provider);
var status = -1;
itemarray = [];

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    ccoin = 4310237;
    banneditem = [3015314, 1802598, 1802599, 5000930, 5000931, 5000932, 5000484, 5000485, 5000486, 5000490, 5000754, 5000727, 5000952];
    if (mode == -1 || mode == 0) {
        itemarray = null;
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
        var msg = "#fs11#สวัสดี? เรามี #rสัตว์เลี้ยง#k แจกฟรี\r\nมีสัตว์เลี้ยงน่ารักมากมายเตรียมไว้ให้ เลือกได้เลย~\r\n\r\n"
        msg += "#L0##dเลือกสัตว์เลี้ยง#k#l"
        cm.sendSimple(msg);
    } else if (status == 1) {
        var msg = "#fs11#กรุณาเลือกสัตว์เลี้ยงที่ต้องการ\r\n\r\n";
        var it = Packages.objects.item.MapleItemInformationProvider.getInstance().getAllItems().iterator();
        while (it.hasNext()) {
            var itemPair = it.next();
            banned = false;
            for (i = 0; i < banneditem.length; i++) {
                if (itemPair.getLeft() == banneditem[i]) {
                    banned = true;
                }
            }
            if (itemPair.getLeft() >= 5000000 && itemPair.getLeft() <= 5000999 && (itemPair.getRight() != null && itemPair.getRight() != "") && banned == false && itemPair.getRight() != " " && PetDataFactory.getPetData(itemPair.getLeft()).getWonderGrade() == -1) {
                itemarray.push(itemPair.getLeft());
            }
        }
        it = null;
        for (i = 0; i < itemarray.length; i++) {
            msg += "#L" + i + "# #i" + itemarray[i] + "#"
            if (i % 5 == 4) {
                msg += "\r\n"
            }
        }
        cm.sendSimple(msg);
    } else if (status == 2) {
        if (!cm.isCash(itemarray[selection]) || itemarray[selection] >= 5001000) {
            a = new Date();
            temp = Packages.objects.utils.Randomizer.rand(0, 9999999);
            cn = cm.getPlayer().getName();
            fFile1 = new File("TextLog/scriptLog/" + temp + "_" + cn + ".log");
            if (!fFile1.exists()) {
                fFile1.createNewFile();
            }
            out1 = new FileOutputStream("TextLog/scriptLog/" + temp + "_" + cn + ".log", false);
            var msg = "'" + cm.getPlayer().getName() + "' attempted duping (suspicious)\r\n";
            msg += "'" + a.getFullYear() + "Year " + Number(a.getMonth() + 1) + "Month " + a.getDate() + "Day'\r\n";
            msg += "Attempted Dupe Item Code : " + selection + "\r\n";
            msg += "Character ID : " + cm.getPlayer().getId() + "\r\n";
            msg += "Account ID : " + cm.getPlayer().getAccountID() + "\r\n";
            out1.write(msg.getBytes());
            out1.close();
            cm.sendOk("การเข้าถึงไม่ถูกต้อง");
            cm.dispose();
        } else {
            //cm.BuyPET(itemarray[selection]);

            var item = new Item(itemarray[selection], 1, 1, 0);
            item.setExpiration((new Date()).getTime() + (1000 * 60 * 60 * 24 * 365));
            var pet = MaplePet.createPet(itemarray[selection], MapleInventoryIdentifier.getInstance());
            item.setPet(pet);
            item.setUniqueId(pet.getUniqueId());
            Packages.objects.item.MapleInventoryManipulator.addFromDrop(cm.getClient(), item, false);

            cm.sendOk("#fs11#ได้รับสัตว์เลี้ยงเรียบร้อยแล้ว ขอให้มีความสุข!");
            cm.dispose();
            itemarray = null;
            return;
        }
    }
}