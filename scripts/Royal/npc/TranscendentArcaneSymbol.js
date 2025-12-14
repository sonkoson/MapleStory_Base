importPackage(Packages.client.inventory);
importPackage(Packages.packet.creators);
importPackage(Packages.constants);
importPackage(Packages.client);
importPackage(Packages.server);
importPackage(java.lang);
importPackage(Packages.network.models);
importPackage(Packages.objects.item);

var status = -1;
var SYMBOL_IDS = [1712001, 1712002, 1712003, 1712004, 1712005, 1712006];
var available = [];
var chosenId = -1;
var paySel = -1;

blueStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#";
yellowStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#";
whiteStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#";
brownStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#";
redStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#";
blackStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#";
purpleStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#";

var DONATION_COST = 30000;
var HPOINT_COST = 3000000;

var BONUS_STAT = 300;
var BONUS_WATK = 80;
var BONUS_MATK = 80;
var MAX_STAR = 10;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 0) {
        cm.dispose();
        return;
    }
    status++;
    var chr = cm.getPlayer();

    switch (status) {
        case 0:
            available = [];
            for (var i = 0; i < SYMBOL_IDS.length; i++) {
                var id = SYMBOL_IDS[i];
                var it = chr.getInventory(MapleInventoryType.EQUIPPED).findById(id);
                if (it != null) {
                    var own = it.getOwner();
                    if (own == "" || own.match(/Transcendent\s*[1-9]Star/)) {
                        available.push(id);
                    }
                }
            }
            if (available.length == 0) {
                cm.sendOk("ไม่มี Arcane Symbol ที่สามารถอัปเกรดได้\r\n(เหลือเพียงสัญลักษณ์ที่เป็น Transcendent ระดับ 10 ดาวแล้ว)");
                cm.dispose();
                return;
            }
            var msg = "#fs11#     #fUI/Basic.img/Zenia/SC/0#\r\n" +
                "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――\r\n" +
                "#fc0xFFFF3366##e                 < การอัปเกรด Transcendent Arcane Symbol >#n\r\n" +
                "#fc0xFFFF3366##e       < ต่อ 1 ดาว: All Stats +300, ATT/MATK +80 >#n\r\n\r\n" +
                "#e#r      เลือก Arcane Symbol ที่ต้องการทำ Transcendent (สูงสุด 10 ดาว)#k\r\n" +
                "#Cgray##fs11#――――――――――――――――――――――――――――――――――――#n\r\n";
            for (var i = 0; i < available.length; i++) {
                var id = available[i];
                var it = chr.getInventory(MapleInventoryType.EQUIPPED).findById(id);
                var own = it.getOwner() || "";
                var m = own.match(/Transcendent\s*([1-9])Star/);
                var curStar = m ? parseInt(m[1], 10) : 0;
                var starText = curStar > 0 ? "Transcendent " + curStar + " Star" : "Not Transocended";
                msg += "#L" + i + "##i" + id + "# #b#z" + id + "##k  #e#fc0xFFF781D8#(" + starText + ")#n#k#l\r\n";
            }
            cm.sendSimple(msg);
            break;

        case 1:
            chosenId = available[selection];
            var payMsg = "#fc0xFF000000##fs11#เลือกวิธีการชำระเงินสำหรับการทำ Transcendent\r\n\r\n" +
                "#fc0xFF000000##e#L0#" + blueStar + " Donation Point #r-30,000P#k#l\r\n\r\n" +
                "#fc0xFF000000##L1#" + purpleStar + " Promotion Point #r-3,000,000P#k#l";
            cm.sendSimple(payMsg);
            break;

        case 2:
            paySel = selection;
            if (paySel == 0) {
                if (chr.getDonationPoint() < DONATION_COST) {
                    cm.sendOk("Donation Points ไม่เพียงพอ");
                    cm.dispose();
                    return;
                }
                chr.gainDonationPoint(-DONATION_COST);
            } else {
                if (chr.getHPoint() < HPOINT_COST) {
                    cm.sendOk("Promotion Points ไม่เพียงพอ");
                    cm.dispose();
                    return;
                }
                chr.gainHPoint(-HPOINT_COST);
            }
            var equip = chr.getInventory(MapleInventoryType.EQUIPPED).findById(chosenId);
            var own = equip.getOwner();
            var curStar = 0;
            var m = own.match(/Transcendent\s*([1-9])Star/);
            if (m) curStar = parseInt(m[1], 10);
            // Block 10 stars or more
            if (curStar >= MAX_STAR) {
                cm.sendOk("ไม่สามารถอัปเกรดสัญลักษณ์ที่ถึงระดับสูงสุดแล้วได้");
                cm.dispose();
                return;
            }
            var nextStar = curStar + 1;
            equip.setStr(java.lang.Short.valueOf(equip.getStr() + BONUS_STAT + ""));
            equip.setDex(java.lang.Short.valueOf(equip.getDex() + BONUS_STAT + ""));
            equip.setInt(java.lang.Short.valueOf(equip.getInt() + BONUS_STAT + ""));
            equip.setLuk(java.lang.Short.valueOf(equip.getLuk() + BONUS_STAT + ""));
            equip.setWatk(java.lang.Short.valueOf(equip.getWatk() + BONUS_WATK + ""));
            equip.setMatk(java.lang.Short.valueOf(equip.getMatk() + BONUS_MATK + ""));
            equip.setOwner("Transcendent " + nextStar + "Star");
            chr.send(CWvsContext.InventoryPacket.updateEquipSlot(equip));
            cm.sendOk("#fs11#" +
                "#b#eทำ Transcendent " + nextStar + " ดาว สำเร็จ!#k\r\n" +
                "(All Stats +" + BONUS_STAT + " / ATT/MATK +" + BONUS_WATK + ")");
            cm.dispose();
            break;
    }
}
