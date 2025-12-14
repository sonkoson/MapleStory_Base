/*후라이 까지말라 (kms_word@naver.com)*/


별파 = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
별노 = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
별흰 = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
별갈 = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
별빨 = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
별검 = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
별보 = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
별 = "#fUI/FarmUI.img/objectStatus/star/whole#";
S = "#fUI/CashShop.img/CSEffect/today/0#"
보상 = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
색 = "#fc0xFF6600CC#"
엔터 = "\r\n"
엔터2 = "\r\n\r\n"

var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.dispose();
        }
        status--;
    }
    if (status == 0) {
        say = 별 + 색 + "คุณต้องการตรวจสอบกล่องจดหมายรับของหรือไม่?" + 엔터2 + 별빨 + "#rกรุณาทำช่องว่างในกระเป๋าให้เพียงพอด้วยนะ"
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM `offline` WHERE `chrid` = " + cm.getPlayer().getId() + "");
            rs = ps.executeQuery();
            while (rs.next()) {
                say += "#z" + rs.getString("item") + "##i" + rs.getString("name") + "#" + rs.getInt("qua") + "ชิ้น\r\n";
            }
            rs.close();
            ps.close();
            con.close();
            cm.sendOk(say);
            cm.dispose();
            return;
        } catch (e) {
            cm.sendOk("เกิดข้อผิดพลาด\r\n\r\n" + e);
            cm.dispose();
            return;
        }
    } else if (status == 1) {
        if (cm.getPlayer().getInventory(Packages.objects.item.MapleInventoryType.EQUIP).getNumFreeSlot() < 10 || cm.getPlayer().getInventory(Packages.objects.item.MapleInventoryType.USE).getNumFreeSlot() < 10 || cm.getPlayer().getInventory(Packages.objects.item.MapleInventoryType.ETC).getNumFreeSlot() < 10) {
            cm.sendOk("#fs11#กรุณาทำช่องว่างในช่องสวมใส่, ใช้แล้วหมดไป, และอื่นๆ ให้ว่างอย่างน้อย 10 ช่อง");
            cm.dispose();
            return;
        }

        if (Offline.pickUpItemOff(cm.getPlayer())) {
            Offline.removeItemOff(cm.getPlayer().getId());
            cm.sendOk("#fs11#ได้รับไอเท็มจากกล่องจดหมายเรียบร้อยแล้ว\r\nกรุณาตรวจสอบในกระเป๋า");
        } else {
            cm.sendOk("#fs11#ไม่มีไอเท็มในกล่องจดหมาย");
        }
        cm.dispose();
    }
}