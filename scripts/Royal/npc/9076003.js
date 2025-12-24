var star = "#fUI/GuildMark.img/Mark/Pattern/00004001/14#";

var sel = 0

var ringList = [{
    'item': 1112030,
    'price': 1
},
{
    'item': 1112031,
    'price': 1
},
{
    'item': 1112032,
    'price': 1
},
{
    'item': 1112033,
    'price': 1
},
{
    'item': 1112034,
    'price': 1
},
{
    'item': 1112035,
    'price': 1
},
{
    'item': 1112036,
    'price': 1
},
{

    'item': 1112037,
    'price': 1
},
{
    'item': 1112038,
    'price': 1
},
{
    'item': 1112039,
    'price': 1
},
{
    'item': 1112040,
    'price': 1
},
{
    'item': 1112041,
    'price': 1
},
{
    'item': 1112042,
    'price': 1
},
{
    'item': 1112043,
    'price': 1
},
{
    'item': 1112044,
    'price': 1
},
{
    'item': 1112045,
    'price': 1
},
{
    'item': 1112850,
    'price': 1
},
{
    'item': 1112851,
    'price': 1
},
{
    'item': 1112853,
    'price': 1
},
{
    'item': 1112854,
    'price': 1
},
{
    'item': 1112855,
    'price': 1
},
{
    'item': 1112856,
    'price': 1
},
{
    'item': 1112857,
    'price': 1
},
{
    'item': 1112858,
    'price': 1
},
{
    'item': 1112859,
    'price': 1
},
{
    'item': 1112860,
    'price': 1
},
{
    'item': 1112861,
    'price': 1
},
{
    'item': 1112862,
    'price': 1
}
]

var coin = 4001679; // Required Item Code

var status = -1;

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }
    if (status == 0) {
        var text = "                       #fs11##fc0xFF000000##fs17#" + star + " ร้านค้าแหวนมิตรภาพ Royal Maple " + star + "\r\n#fs12##Cgray#                มอบแหวนเพื่อยืนยันมิตรภาพของคุณ!#k\r\n\r\n#fs12#";
        text += "#fs12#กรุณาเลือกแหวนที่ต้องการมอบ\r\nคุณต้องอยู่ใน #bปาร์ตี้#k กับคนที่จะสวมใส่ด้วย\r\n#r(หมายเหตุ: ทั้งผู้ส่งและผู้รับต้องย้ายแชนแนลเพื่อให้มีผล)#k\r\n#fs11##fc0xFF000000#คุณต้องมี #i4001679# เพื่อมอบแหวนคู่รัก#b\r\n";
        for (var i = 0; i < ringList.length; ++i) {
            var itemID = ringList[i]['item'];
            var price = ringList[i]['price'];
            text += "#L" + i + "##i" + itemID + "# #z" + itemID + "\r\n\r\n";
        }
        cm.sendSimple(text);
    } else if (status == 1) {
        if (!cm.haveItem(coin, 1)) {
            cm.sendNext("คุณมี #z" + coin + "# ไม่เพียงพอ");
            cm.dispose();
            return;
        }
        sel = selection;
        cm.sendGetText("#fs11##fc0xFF000000# กรุณากรอกชื่อตัวละครของคนที่จะสวมใส่ด้วย");
    } else if (status == 2) {
        if (cm.getPlayer().getParty() == null) {
            cm.sendOk("#fs11##fc0xFF000000# กรุณาลองใหม่หลังจากสร้างปาร์ตี้กับคนที่จะสวมใส่ด้วย");
            cm.dispose();
            return;
        }
        if (!cm.allMembersHere()) {
            cm.sendOk("#fs11##fc0xFF000000# กรุณาลองใหม่ในแผนที่เดียวกับอีกฝ่าย");
            cm.dispose();
            return;
        }
        var target = cm.getText();
        var it = cm.getClient().getChannelServer().getPartyMembers(cm.getParty()).iterator();
        var chr = null;

        while (it.hasNext()) {
            var chr = it.next();
            if (chr.getName().equals(target)) {
                find = chr;
                break;
            }
        }

        if (chr == null) {
            cm.sendOk("#fs11##fc0xFF000000#ผู้รับไม่ได้ออนไลน์");
            cm.dispose();
            return;
        }

        cm.makeRing(ringList[sel]['item'], target);
        cm.gainItem(coin, -ringList[sel]['price']);
        cm.sendNext("#fs11##fc0xFF000000#ซื้อเรียบร้อยแล้ว แหวนคู่รักจะมีผลหลังจากล็อคอินใหม่");
        chr.chatMsg(10, cm.getPlayer().getName() + " ได้มอบแหวนคู่รักให้คุณ ตรวจสอบช่องเก็บของของคุณตอนนี้เลย!");
        chr.chatMsg(10, "แหวนคู่รักจะมีผลหลังจากล็อคอินใหม่");
        cm.dispose();
    }
}