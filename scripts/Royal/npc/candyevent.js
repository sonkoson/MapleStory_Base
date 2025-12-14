보라 = "#fMap/MapHelper.img/weather/starPlanet/7#";
파랑 = "#fMap/MapHelper.img/weather/starPlanet/8#";
별파 = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
별노 = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
별흰 = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
별갈 = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
별빨 = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
별검 = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
별보 = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
별 = "#fUI/FarmUI.img/objectStatus/star/whole#"
S = "#fUI/CashShop.img/CSEffect/today/0#"
보상 = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
획득 = "#fUI/UIWindow2.img/QuestIcon/4/0#"
색 = "#fc0xFF6600CC#"
검은색 = "#fc0xFF000000#"
핑크색 = "#fc0xFFFF3366#"
분홍색 = "#fc0xFFF781D8#"
엔터 = "\r\n"
엔터2 = "\r\n\r\n"
enter = "\r\n";

importPackage(Packages.objects.item);

var randitem = [
    [1114001, "#fs11##i1114001# #fc0xFF000000#นักผจญภัยเหมาะกับ #fc0xFF000000#[Black]#fc0xFF000000# มากเลยนะ"],
    [1114002, "#fs11##i1114002# #fc0xFF000000#นักผจญภัยเหมาะกับ #fc0xFFBEBEBE#[White]#fc0xFF000000# มากเลยนะ"],
    [1114003, "#fs11##i1114003# #fc0xFF000000#นักผจญภัยเหมาะกับ #fc0xFFFF0000#[Red]#fc0xFF000000# มากเลยนะ"],
    [1114004, "#fs11##i1114004# #fc0xFF000000#นักผจญภัยเหมาะกับ #fc0xFF0600FF#[Blue]#fc0xFF000000# มากเลยนะ"],
    [1114005, "#fs11##i1114005# #fc0xFF000000#นักผจญภัยเหมาะกับ #fc0xFF00FF30#[Green]#fc0xFF000000# มากเลยนะ"],
    [1114006, "#fs11##i1114006# #fc0xFF000000#นักผจญภัยเหมาะกับ #fc0xFFCACC00#[Yellow]#fc0xFF000000# มากเลยนะ"],
    [1114007, "#fs11##i1114007# #fc0xFF000000#นักผจญภัยเหมาะกับ #fc0xFF00FFEA#[Sky Blue]#fc0xFF000000# มากเลยนะ"],
    [1114008, "#fs11##i1114008# #fc0xFF000000#นักผจญภัยเหมาะกับ #fc0xFFFF7BF7#[Pink]#fc0xFF000000# มากเลยนะ"],
    [1114009, "#fs11##i1114009# #fc0xFF000000#นักผจญภัยเหมาะกับ #fc0xFFFF9F16#[Orange]#fc0xFF000000# มากเลยนะ"],
];

var randpet = [
    [5002239, "#fs11##i5002239# #fc0xFF000000#นักผจญภัยเหมาะกับ #fc0xFF000000#[Sherbet]#fc0xFF000000# มากเลยนะ"],
    [5002396, "#fs11##i5002396# #fc0xFF000000#นักผจญภัยเหมาะกับ #fc0xFFFF7BF7#[Pink Bean]#fc0xFF000000# มากเลยนะ"],
];

function start() {
    status = -1;
    action(1, 0, 0);
}

function MakeItem(itemid) {
    var ii = Packages.objects.item.MapleItemInformationProvider.getInstance();
    var it = ii.getEquipById(itemid);
    it.setStr(400);
    it.setDex(400);
    it.setInt(400);
    it.setLuk(400);
    it.setWatk(200);
    it.setMatk(200);
    it.setCHUC(30);
    it.setAllStat(10);
    it.setOwner("Royal Maple");
    it.setExpiration((new Date()).getTime() + (1000 * 60 * 60 * 24 * 14));
    Packages.objects.item.MapleInventoryManipulator.addFromDrop(cm.getPlayer().getClient(), it, false);
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
        var msg = "　　　#i4317002# #fs14##e[Royal Maple] - Candy Event#n#fs11# #i4317002#\r\n#fs11##Cblue#                  ขอบคุณที่ใช้บริการ Royal Maple เสมอมา#k\r\n";
        msg += 핑크색 + "                               #L100#Event Info#fc0xFF000000##l\r\n\r\n";
        msg += "                      Event Period : #b08.15 ~ 08.31\r\n";
        msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        msg += "#L1##fc0xFFFF3366#รับ Event Ring#fc0xFF000000##l\r\n";
        msg += "#L2##fc0xFFFF3366#รับ Event Magnet Pet#fc0xFF000000##l\r\n";
        msg += "#L3##fc0xFF6600CC#ร้านค้า Event Item#fc0xFF000000##l\r\n";
        msg += "#L4##fc0xFF6600CC#ตรวจสอบแหล่งหา Event Item#fc0xFF000000##l\r\n";
        msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        cm.sendSimple(msg);
    } else if (status == 1) {
        seld = sel;
        switch (sel) {
            case 100:
                var msg = "#fn나눔고딕 Extrabold##d(เห็นว่าถ้าล่ามอนสเตอร์จะมีลูกอมดรอปสินะ..)#k\r\n\r\n#bรีบไปล่ามอนสเตอร์แล้วสะสมลูกอมดีกว่า!\r\n\r\n";
                msg += "#L96##rว่าแต่ Event Item เอาไว้ใช้ทำอะไรนะ..?#l\r\n";
                cm.sendNextS(msg, 2);
                break;

            case 1:
                if (cm.getClient().getKeyValue("candyeventitem") == null) {
                    말 = "#fs11#" + 검은색;
                    말 += "สามารถสุ่มรับ Effect Ring ที่มีค่าสเตตัสได้หลากหลายเลยนะ" + enter;
                    말 += 핑크색 + "※ 1 ครั้งต่อบัญชี เป็นเวลา 15 วัน" + enter + enter;
                    말 += 핑크색 + "ต้องการรับจริงๆ ไหม?"
                    cm.sendYesNo(말);
                } else {
                    말 = "#fs11#" + 검은색 + "ได้รับ #fc0xFFFF3366#[Event Ring]" + 검은색 + " ไปแล้ว";
                    cm.dispose();
                    cm.sendOk(말);
                }
                break;

            case 2:
                if (cm.getClient().getKeyValue("candyeventpet") == null) {
                    말 = "#fs11#" + 검은색;
                    말 += "สามารถสุ่มรับสัตว์เลี้ยงแม่เหล็กสุดน่ารักได้" + enter;
                    말 += 핑크색 + "※ 1 ครั้งต่อบัญชี เป็นเวลา 15 วัน" + enter + enter;
                    말 += 핑크색 + "ต้องการรับจริงๆ ไหม?"
                    cm.sendYesNo(말);
                } else {
                    말 = "#fs11#" + 검은색 + "ได้รับ #fc0xFFFF3366#[Event Magnet Pet]" + 검은색 + " ไปแล้ว";
                    cm.dispose();
                    cm.sendOk(말);
                }
                break;

            case 3:
                cm.dispose();
                cm.openShop(7777);
                break;

            case 4:
                var msg = "#fs11#" + 색 + "คู่มือแหล่งหา Event Item\r\n\r\n";
                msg += "#fs11##b#i4317001##z4317001#\r\n#i4317002##z4317002#\r\n#i4317003##z4317003#\r\n\r\n";
                msg += "#r#e[ตกปลา]#b มีโอกาสได้รับไอเท็ม 3 อย่างข้างต้นจากการ\r\n";
                msg += "#r#e[ล่ามอนสเตอร์]#b มีโอกาสได้รับไอเท็ม 3 อย่างข้างต้นจากการ";
                cm.sendOk(msg);
                cm.dispose();
                break;
        }

    } else if (status == 2) {
        switch (seld) {
            case 100:
                cm.sendOk("#fs11#" + 색 + "สามารถใช้ลูกอมซื้อไอเท็มต่างๆ ได้ที่ร้านค้ากิจกรรม!");
                cm.dispose();
                break;

            case 1:
                a = Packages.objects.utils.Randomizer.rand(0, 8);

                if (!cm.canHold(randitem[a][0], 1)) {
                    cm.sendOk("#fs11#ช่องเก็บของไม่พอ");
                    cm.dispose();
                    return;
                }

                cm.getClient().setKeyValue("candyeventitem", "1");
                MakeItem(randitem[a][0]);
                cm.sendOk(randitem[a][1]);
                cm.dispose();
                break;

            case 2:
                b = Packages.objects.utils.Randomizer.rand(0, 1);

                if (!cm.canHold(randpet[b][0], 1)) {
                    cm.sendOk("#fs11#ช่องเก็บของไม่พอ");
                    cm.dispose();
                    return;
                }

                cm.getClient().setKeyValue("candyeventpet", "1");
                var item = new Item(randpet[b][0], 1, 1, 0);
                item.setExpiration((new Date()).getTime() + (1000 * 60 * 60 * 24 * 15));
                var pet = MaplePet.createPet(randpet[b][0], MapleInventoryIdentifier.getInstance());
                item.setPet(pet);
                item.setUniqueId(pet.getUniqueId());
                Packages.objects.item.MapleInventoryManipulator.addFromDrop(cm.getClient(), item, false);
                cm.sendOk(randpet[b][1]);
                cm.dispose();
                break;
        }
    }
}
