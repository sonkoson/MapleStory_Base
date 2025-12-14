var status = -1;
var selectedSlot = -1;
var itemList = [];

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 1) {
            status++;
        } else {
            cm.dispose();
            return;
        }

        if (status == 0) {
            var inv = cm.getPlayer().getInventory(Packages.objects.item.MapleInventoryType.EQUIP);
            var items = inv.list().toArray();

            // Sort by slot number
            java.util.Arrays.sort(items, function (a, b) {
                return a.getPosition() - b.getPosition();
            });

            var txt = "เลือกไอเท็มที่จะส่ง\r\n";
            itemList = [];

            for (var i = 0; i < items.length; i++) {
                var item = items[i];
                txt += "#L" + i + "##i" + item.getItemId() + ":# #t" + item.getItemId() + "# (Slot: " + item.getPosition() + ")\r\n";
                itemList.push(item);
            }

            if (itemList.length == 0) {
                cm.sendOk("ไม่มีไอเท็มสวมใส่ในครอบครอง");
                cm.dispose();
                return;
            }

            cm.sendSimple(txt);

        } else if (status == 1) {
            selectedSlot = selection;

            cm.sendGetText("กรุณากรอกชื่อตัวละครของผู้ที่จะรับไอเท็ม\r\n(เฉพาะผู้เล่นในแชแนลเดียวกันเท่านั้น)");

        } else if (status == 2) {
            var targetName = cm.getText();
            var target = cm.getClient().getChannelServer().getPlayerStorage().getCharacterByName(targetName);

            if (target == null) {
                cm.sendOk("ไม่พบผู้เล่นดังกล่าว\r\n(ต้องออนไลน์ในแชแนลเดียวกัน)");
                cm.dispose();
                return;
            }

            var selectedItem = itemList[selectedSlot];

            if (selectedItem == null) {
                cm.sendOk("ไม่พบไอเท็มที่เลือก");
                cm.dispose();
                return;
            }

            var newItem = selectedItem.copy();

            target.getInventory(Packages.objects.item.MapleInventoryType.EQUIP).addItem(newItem);
            target.send(Packages.network.models.CWvsContext.InventoryPacket.addInventorySlot(Packages.objects.item.MapleInventoryType.EQUIP, newItem));

            cm.getPlayer().getInventory(Packages.objects.item.MapleInventoryType.EQUIP).removeItem(selectedItem.getPosition());
            cm.getPlayer().send(Packages.network.models.CWvsContext.InventoryPacket.clearInventoryItem(Packages.objects.item.MapleInventoryType.EQUIP, selectedItem.getPosition(), false));

            cm.sendOk("ส่งไอเท็มให้คุณ " + targetName + " เรียบร้อยแล้ว");
            cm.dispose();
        }
    }
}
