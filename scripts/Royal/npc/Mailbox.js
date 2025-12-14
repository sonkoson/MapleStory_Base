/*
Xenia Mailbox
*/
importPackage(Packages.objects.item);
importPackage(Packages.objects.users);

importPackage(Packages.database);

purple = "#fMap/MapHelper.img/weather/starPlanet/7#";
blue = "#fMap/MapHelper.img/weather/starPlanet/8#";
starBlue = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
starYellow = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
starWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
starBrown = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
starRed = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
starBlack = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
starPurple = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
star = "#fUI/FarmUI.img/objectStatus/star/whole#"
S = "#fUI/CashShop.img/CSEffect/today/0#"
reward = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
obtain = "#fUI/UIWindow2.img/QuestIcon/4/0#"
color = "#fc0xFF6600CC#"
black = "#fc0xFF000000#"
enter = "\r\n"
enter2 = "\r\n\r\n"

var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status--;
    } else {
        if (status == 0) {
            cm.dispose();
        }
        status--;
    }
    if (status == 0) {
        say = "#fs11#" + star + color + "คุณต้องการรับไอเท็มจากกล่องจดหมายหรือไม่?" + enter2 + starRed + "#r กรุณาทำช่องว่างในช่องเก็บของให้เพียงพอ\r\n\r\n#e< รายการในกล่องจดหมาย >#n\r\n"
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM `offline` WHERE `chrid` = " + cm.getPlayer().getId() + " AND status = 0");
            rs = ps.executeQuery();
            while (rs.next()) {
                say += black + "#i" + rs.getString("item") + "#" + color + " #z" + rs.getString("item") + "# " + rs.getInt("qua") + " ชิ้น\r\n";
            }
            rs.close();
            ps.close();
            con.close();
            cm.sendYesNo(say);
            return;
        } catch (e) {
            cm.sendOk("เกิดข้อผิดพลาด\r\n\r\n" + e);
            cm.dispose();
            return;
        }
    } else if (status == 1) {
        if (cm.getPlayer().getInventory(Packages.objects.item.MapleInventoryType.EQUIP).getNumFreeSlot() < 10 || cm.getPlayer().getInventory(Packages.objects.item.MapleInventoryType.USE).getNumFreeSlot() < 10 || cm.getPlayer().getInventory(Packages.objects.item.MapleInventoryType.ETC).getNumFreeSlot() < 10) {
            cm.sendOk("#fs11#กรุณาทำช่องว่างในช่อง Equip, Use, และ Etc อย่างน้อย 10 ช่อง");
            cm.dispose();
            return;
        }

        if (MailBox.pickUpItemOff(cm.getPlayer())) {
            MailBox.removeItemOff(cm.getPlayer().getId());
            cm.sendOk("#fs11#คุณได้รับไอเท็มจากกล่องจดหมายเรียบร้อยแล้ว\r\nกรุณาตรวจสอบช่องเก็บของ");
        } else {
            cm.sendOk("#fs11#ไม่มีไอเท็มในกล่องจดหมาย");
        }
        cm.dispose();
    }
}
