importPackage(java.lang);
importPackage(java.util);
importPackage(java.awt);
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
        status--;
    }
    if (mode == 1) {
        status++;
    }


    if (status == 0) {
        if (cm.getPlayer().getGuild() != null) {
            if (cm.getPlayer().getInventory(Packages.objects.item.MapleInventoryType.USE).getNumFreeSlot() < 2) {
                cm.sendOk("#fs11#โปรดเว้นช่องว่างในช่องเก็บของใช้ (Use) อย่างน้อย 2 ช่อง แล้วค่อยมาคุยกับข้าใหม่");
                cm.dispose();
                return;
            }
            cm.sendYesNo("#fs11##bของสนับสนุนกิลด์#k ประจำสัปดาห์มาถึงแล้ว (รับได้ #rสัปดาห์ละครั้ง#k) เจ้าต้องการรับเลยหรือไม่?", 2010010);
        } else {
            cm.sendOk("#fs11#เจ้าไม่สามารถรับได้เพราะไม่ได้สังกัดกิลด์")
            cm.dispose();
            return;
        }
    } else if (status == 1) {

        if (cm.getPlayer().getGuild().getSkillLevel(91000031) != 0 ||
            cm.getPlayer().getGuild().getSkillLevel(91000032) != 0 ||
            cm.getPlayer().getGuild().getSkillLevel(91000009) != 0) {

            var itemid = 2432290;
            var pootioncount = cm.getPlayer().getGuild().getSkillLevel(91000009) * 25;
            var itemcount = cm.getPlayer().getGuild().getSkillLevel(91000009) * 5;
            if (cm.getPlayer().getGuild().getSkillLevel(91000031) >= 1) {
                itemid = 2631501;
                pootioncount += 100;
                itemcount += 5;
            }
            if (cm.getPlayer().getGuild().getSkillLevel(91000032) >= 1) {
                itemid = 2631501;
                pootioncount += 100;
                itemcount += 5;
            }
            time = new Date();
            var day2 = time.getDay();
            var check = day2 == 1 ? 7 : day2 == 2 ? 6 : day2 == 3 ? 5 : day2 == 4 ? 4 : day2 == 5 ? 3 : day2 == 6 ? 2 : day2 == 0 ? 1 : 0;
            var inz = new Packages.objects.item.Item(itemid, 0, 1);

            beforedate = cm.getPlayer().getKeyValue("GuildBless");
            afterdate = getMonday(new Date());

            if (beforedate >= afterdate) {
                cm.sendOk("#fs11#เจ้าได้รับของสนับสนุนประจำสัปดาห์นี้ไปแล้ว");
                cm.dispose();
                return;
            }

            inz.setExpiration((new Date(time.getFullYear(), time.getMonth(), time.getDate(), 0, 0, 0, 0)).getTime() + (1000 * 60 * 60 * 24 * check));
            inz.setQuantity(itemcount);
            Packages.objects.item.MapleInventoryManipulator.addbyItem(cm.getClient(), inz);
            inz = new Packages.objects.item.Item(2002037, 0, 1);
            inz.setExpiration((new Date(time.getFullYear(), time.getMonth(), time.getDate(), 0, 0, 0, 0)).getTime() + (1000 * 60 * 60 * 24 * check));
            inz.setQuantity(pootioncount);
            Packages.objects.item.MapleInventoryManipulator.addbyItem(cm.getClient(), inz);
            cm.sendOk("#fs11#ไอเท็มที่ได้รับสามารถใช้ได้ถึงวันอาทิตย์นี้เท่านั้น\r\nเมื่อเลเวลกิลด์เพิ่มขึ้น เจ้าจะได้รับของสนับสนุนมากขึ้น พยายามเข้านะ!\r\nขอบคุณสำหรับความเหนื่อยยากในสัปดาห์นี้ แล้วเจอกันใหม่สัปดาห์หน้า", 2010010);

            cm.getPlayer().setKeyValue("GuildBless", TodayDate(new Date()));
            cm.dispose();
        } else {
            cm.sendOk("#fs11#กิลด์ของเจ้ายังไม่ได้เรียนรู้สกิลสนับสนุน");
            cm.dispose();
        }
    }
}

function getMonday(date) {
    // Format = yyyyddmm

    var year = date.getFullYear();

    // Move to Monday
    var day = date.getDay() || 7;
    if (day !== 1)
        date.setHours(-24 * (day - 1));
    // Moved to Monday

    var month = date.getMonth() + 1;
    if (month < 10) {
        month = '0' + month;
    }

    var date = date.getDate();
    if (date < 10) {
        date = '0' + date;
    }

    return year + '' + month + '' + date;
}

function TodayDate(date) {
    // Format = yyyyddmm

    var year = date.getFullYear();

    var month = date.getMonth() + 1;
    if (month < 10) {
        month = '0' + month;
    }

    var date = date.getDate();
    if (date < 10) {
        date = '0' + date;
    }

    return year + '' + month + '' + date;
}