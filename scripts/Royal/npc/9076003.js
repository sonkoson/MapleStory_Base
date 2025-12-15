var 별 = "#fUI/GuildMark.img/Mark/Pattern/00004001/14#";

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

var coin = 4001679; // 필요한 아이템 코드

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
        var text = "                       #fs11##fc0xFF000000##fs17#" + 별 + " Royal Maple Friendship Ring Shop " + 별 + "\r\n#fs12##Cgray#                Gift a ring to verify your friendship!#k\r\n\r\n#fs12#";
        text += "#fs12#Please select the ring you want to gift.\r\nYou must be in a #bparty#k with the person you will equip it with.\r\n#r(Note: Both sender and receiver must change channels for it to apply.)#k\r\n#fs11##fc0xFF000000#You need #i4001679# to gift a couple ring#b\r\n";
        for (var i = 0; i < ringList.length; ++i) {
            var itemID = ringList[i]['item'];
            var price = ringList[i]['price'];
            text += "#L" + i + "##i" + itemID + "# #z" + itemID + "\r\n\r\n";
        }
        cm.sendSimple(text);
    } else if (status == 1) {
        if (!cm.haveItem(coin, 1)) {
            cm.sendNext("You don't have enough #z" + coin + "#.");
            cm.dispose();
            return;
        }
        sel = selection;
        cm.sendGetText("#fs11##fc0xFF000000# Please enter the character name of the person who will equip it with you.");
    } else if (status == 2) {
        if (cm.getPlayer().getParty() == null) {
            cm.sendOk("#fs11##fc0xFF000000# Please try again after forming a party with the person you will equip it with.");
            cm.dispose();
            return;
        }
        if (!cm.allMembersHere()) {
            cm.sendOk("#fs11##fc0xFF000000# Please try again in the same map as the other person.");
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
            cm.sendOk("#fs11##fc0xFF000000#The recipient is not online.");
            cm.dispose();
            return;
        }

        cm.makeRing(ringList[sel]['item'], target);
        cm.gainItem(coin, -ringList[sel]['price']);
        cm.sendNext("#fs11##fc0xFF000000#Purchase complete. The couple ring will be applied after relogging.");
        chr.chatMsg(10, cm.getPlayer().getName() + " has gifted you a couple ring. Check your inventory right now!");
        chr.chatMsg(10, "The couple ring will be applied after relogging.");
        cm.dispose();
    }
}