var Purple = "#fMap/MapHelper.img/weather/starPlanet/7#";
var Blue = "#fMap/MapHelper.img/weather/starPlanet/8#";
var StarBlue = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
var StarYellow = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
var StarWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
var StarBrown = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
var StarRed = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
var StarBlack = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
var StarPurple = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
var Star = "#fUI/FarmUI.img/objectStatus/star/whole#"
var S = "#fUI/CashShop.img/CSEffect/today/0#"
var Reward = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
var Obtain = "#fUI/UIWindow2.img/QuestIcon/4/0#"
var Color = "#fc0xFF6600CC#"
var Black = "#fc0xFF000000#"
var Pink = "#fc0xFFFF3366#"
var LightPink = "#fc0xFFF781D8#"
var Enter = "\r\n"
var Enter2 = "\r\n\r\n"
var enter = "\r\n";

importPackage(Packages.objects.item);

var bosang = [
    [2634770, 1],
    [2633202, 1],
    [2633202, 1]
]

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, sel) {
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
        var msg = "　　#i1142275# #fs14##e[Royal Maple] - Event#n#fs11# #i1142275#\r\n#fs11##Cblue#              ขอบคุณที่ใช้บริการ Royal Maple ครับ#k\r\n";
        msg += Pink + "                               #L100#รายละเอียดกิจกรรม#fc0xFF000000##l\r\n\r\n";
        msg += "                      ระยะเวลากิจกรรม : #b10.03 ~ 10.24\r\n";
        msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        msg += "#L1##fc0xFF6600CC#รับ Event Pet#fc0xFF000000# #l\r\n";
        msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        cm.sendSimple(msg);
    } else if (status == 1) {
        seld = sel;
        switch (sel) {
            case 100:
                msg = "#fnNanumGothic Extrabold##dกรุณาตรวจสอบที่เว็บไซต์";
                cm.dispose();
                cm.sendOk(msg);
                break;

            case 1:
                msg = "#fs11#" + Pink + enter + "ต้องการรับ Event Pet หรือไม่?" + Black + enter + enter;
                msg += "#r#fs14#<ข้อควรระวังและคำแนะนำ>#fs11#" + enter + enter;
                msg += Black + "1. Event Pet รับได้ 1 ครั้งต่อบัญชีเท่านั้น" + enter;
                msg += Black + "2. สัตว์เลี้ยงจะได้รับในรูปแบบกล่องและจะสุ่มจาก 2 แบบ" + enter;
                msg += Black + "3. กล่องดังกล่าวสามารถย้ายในคลังและแลกเปลี่ยนได้" + enter;

                msg += Black + enter + "หากเข้าใจข้อความข้างต้นแล้ว กรุณาพิมพ์ #r'ยอมรับ'" + Black + " ด้านล่างนี้" + enter + " ";
                cm.sendGetText(msg);
                break;

        }

    } else if (status == 2) {
        switch (seld) {
            case 100:
                cm.sendOk("#fs11#" + Color + "สามารถซื้อไอเท็มหลากหลายได้ที่ร้านค้ากิจกรรมด้วย #i4310185# #z4310185# !");
                cm.dispose();
                break;

            case 1:
                if (cm.getText() != "ยอมรับ") {
                    cm.dispose();
                    cm.sendOk("#fs11#หากไม่ยอมรับก็จะไม่ได้รับของรางวัล\r\nถ้า#bยอมรับ#k กรุณาพิมพ์ '#r#eยอมรับ#k#n' อีกครั้ง");
                    return;
                }
                if (getClear() != null) {
                    cm.dispose();
                    cm.sendOk("#fs11#คุณได้รับ Event Pet ไปแล้ว\r\nกรุณาตรวจสอบ#bช่องเก็บของ#k");
                    return;
                }
                if (cm.getInvSlots(1) < 3 || cm.getInvSlots(2) < 3 || cm.getInvSlots(3) < 3 || cm.getInvSlots(4) < 3 || cm.getInvSlots(5) < 3) {
                    cm.sendOkS("#fs11##fc0xFF6600CC#กรุณาเคลียร์ช่องเก็บของแต่ละแท็บให้ว่างอย่างน้อย 3 ช่อง", 2);
                    cm.dispose();
                    return;
                }



                msg = "#fs11#" + Obtain + enter;

                i = Packages.objects.utils.Randomizer.rand(0, 2);
                msg += Color + "#i" + bosang[i][0] + "# #z" + bosang[i][0] + "##r " + bosang[i][1] + " ชิ้น#k" + enter;
                cm.gainItem(bosang[i][0], bosang[i][1]);

                msg += Pink + "\r\nได้รับไอเท็มเรียบร้อยแล้ว";
                cm.sendOk(msg);
                cm.getClient().setKeyValue("10dnjfevent", "1");
                cm.dispose();
                break;
        }

    }
}

function getClear() {
    return cm.getClient().getKeyValue("10dnjfevent");
}