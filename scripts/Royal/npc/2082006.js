var enter = "\r\n";
var seld = -1;

var need = [
    { 'itemid': 4001326, 'qty': 1 },
    { 'itemid': 4001327, 'qty': 1 },
    { 'itemid': 4001328, 'qty': 1 },
    { 'itemid': 4001329, 'qty': 1 },
    { 'itemid': 4001330, 'qty': 1 },
    { 'itemid': 4001331, 'qty': 1 },
    { 'itemid': 4001332, 'qty': 1 }
];
var tocoin = 2433979, toqty = 5;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, sel) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }

    if (status == 0) {
        // Intro dialogue
        var msg = "#fs11##fc0xFF000000#สวัสดีค่ะ~ อยากวาดรูปสวยๆ แต่สีเทียนไม่พอ.. ช่วยหน่อยได้ไหม?" + enter;

        // Required items amount display
        for (var i = 0; i < need.length; i++) {
            var item = need[i];
            if (i != need.length - 1) {
                msg += "#b#i" + item.itemid + "##z" + item.itemid + "# " + item.qty + " pcs / #r#c "
                    + item.itemid + "# pcs owned" + "#b" + enter;
            } else {
                msg += "#i" + item.itemid + "##z" + item.itemid + "# " + item.qty + " pcs / #r#c "
                    + item.itemid + "# pcs owned#fc0xFF000000#\r\n\r\nช่วยเก็บสีเทียนให้ด้วย!" + enter;
            }
        }

        // Reward display
        msg += "#bReward: #i" + tocoin + "# #z" + tocoin + "# " + toqty + " pcs" + enter;

        if (haveNeed(1)) {
            cm.sendNext(msg);
        } else {
            msg += "\r\n#fc0xFF000000#สีเทียนไม่พอ! รีบหามาให้เร็ว~";
            cm.sendOk(msg);
            cm.dispose();
        }

    } else if (status == 1) {
        // Calculate max exchange count
        var counts = [];
        for (var i = 0; i < need.length; i++) {
            counts.push(Math.floor(cm.itemQuantity(need[i].itemid) / need[i].qty));
        }
        counts.sort();
        var max = counts[0];

        cm.sendGetNumber(
            "#fs11##fc0xFF000000#แลกได้สูงสุด #b" + max + " ครั้ง#k\r\nจะแลกกี่ครั้ง?",
            1, 1, max
        );

    } else if (status == 2) {
        // Remove items from inventory and give reward based on selected count
        if (!haveNeed(sel)) {
            cm.sendOk("#fc0xFF000000#สีเทียนไม่พอ!");
            cm.dispose();
            return;
        }
        for (var i = 0; i < need.length; i++) {
            cm.gainItem(need[i].itemid, -need[i].qty * sel);
        }
        cm.gainItem(tocoin, toqty * sel);
        cm.sendOk("#fs11##fc0xFF000000#ขอบคุณ! ตอนนี้วาดรูปได้แล้ว!");
        cm.dispose();
    }
}

function haveNeed(a) {
    for (var i = 0; i < need.length; i++) {
        if (!cm.haveItem(need[i].itemid, need[i].qty * a)) {
            return false;
        }
    }
    return true;
}
