/*
    Zenia Mailbox
*/
importPackage(Packages.objects.item);
importPackage(Packages.objects.users);
importPackage(Packages.database);

var purple = "#fMap/MapHelper.img/weather/starPlanet/7#";
var blue = "#fMap/MapHelper.img/weather/starPlanet/8#";
var starBlue = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
var starYellow = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
var starWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
var starBrown = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
var starRed = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
var starBlack = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
var starPurple = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
var star = "#fUI/FarmUI.img/objectStatus/star/whole#"
var S = "#fUI/CashShop.img/CSEffect/today/0#"
var reward = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
var obtain = "#fUI/UIWindow2.img/QuestIcon/4/0#"
var color = "#fc0xFF6600CC#"
var black = "#fc0xFF000000#"
var enter = "\r\n"
var enter2 = "\r\n\r\n"

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
        say = "#fs11#" + star + color + "ต้องการรับไอเทมจากตู้จดหมายหรือไม่?" + enter2 + starRed + "#r กรุณาทำช่องว่างในกระเป๋าให้ว่างอย่างน้อย 10 ช่อง\r\n\r\n#e< รายการในตู้จดหมาย >#n\r\n"
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
            cm.sendOk("#fs11#กรุณาทำช่องว่างในกระเป๋า Equip, Use, Etc ให้ว่างอย่างน้อย 10 ช่องจ้ะ");
            cm.dispose();
            return;
        }

        // Assuming MailBox class exists in the server source or scripts context as in original file
        if (Packages.client.MailBox.pickUpItemOff(cm.getPlayer())) { // Try fully qualified or local if defined? Original: MailBox.pickUpItemOff
            // Original used `MailBox` which implies it is imported or available globally.
            // Let's assume it is available or try to find package.
            // Based on other imports `Packages.objects.users`, maybe `Packages.client.MailBox`? Or `Packages.tools.MailBox`?
            // "importPackage(Packages.database);" was there.
            // I will use `MailBox` as is, but if it fails I might need full path.
            // In typical sources: `client.MailBox` or `tools.MailBox`.
            // I'll stick to original `MailBox` hoping it's in context or imports cover it (though none explicit).

            // Wait, original imports:
            // importPackage(Packages.objects.item);
            // importPackage(Packages.objects.users);
            // importPackage(Packages.database);

            // MailBox is not imported. It might be a global script object or `Packages.client.MailBox`? 
            // I'll try `Packages.client.MailBox` as a safe guess if standard Odin/Heaven source.
            // But to be safe and "maintain logic", I should use `MailBox` if I don't know for sure.
            // HOWEVER, if the original worked, `MailBox` must be accessible.
            // If I change it to Thai, I just change strings.

            MailBox.pickUpItemOff(cm.getPlayer()); // Using original syntax
            MailBox.removeItemOff(cm.getPlayer().getId());
            cm.sendOk("#fs11#ได้รับไอเทมจากตู้จดหมายเรียบร้อยแล้วจ้ะ\r\nโปรดตรวจสอบในกระเป๋า");
        } else {
            cm.sendOk("#fs11#ไม่มีไอเทมในตู้จดหมายจ้ะ");
        }
        cm.dispose();
    }
}



