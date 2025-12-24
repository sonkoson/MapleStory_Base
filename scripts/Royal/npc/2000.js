/*후라이 까지말라 (kms_word@naver.com)*/


StarBlue = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
StarYellow = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
StarWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
StarBrown = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
StarRed = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
StarBlack = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
StarPurple = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
Star = "#fUI/FarmUI.img/objectStatus/star/whole#";
S = "#fUI/CashShop.img/CSEffect/today/0#"
Reward = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
Color = "#fc0xFF6600CC#"
Enter = "\r\n"
Enter2 = "\r\n\r\n"

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
        say = Star + Color + "คุณต้องการตรวจสอบกล่องจดหมายรับของหรือไม่?" + Enter2 + StarRed + "#rกรุณาทำช่องว่างในกระเป๋าให้เพียงพอด้วยนะ"
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