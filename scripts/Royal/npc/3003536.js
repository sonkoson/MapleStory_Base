var enter = "\r\n";
var seld = -1;

var need = [
	{ 'itemid': 4001889, 'qty': 10 },
	{ 'itemid': 4001890, 'qty': 1 }
]
var tocoin = 4310249, toqty = 1;

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
		var msg = "เหล่านักบวชแห่งกาลเวลากำลังสำรวจพื้นที่ Arcane River อยู่ มี Erda Droplet อยู่ไหม? ได้ยินว่ามอนสเตอร์ที่ Esfera มีอยู่นะ" + enter;

		for (i = 0; i < need.length; i++) {
			if (i != need.length - 1) msg += "#i" + need[i]['itemid'] + "##z" + need[i]['itemid'] + "# " + need[i]['qty'] + " pcs and" + enter;
			else msg += "#i" + need[i]['itemid'] + "##z" + need[i]['itemid'] + "# " + need[i]['qty'] + " pcs จะช่วยการสำรวจได้มาก จะให้ #b#z" + tocoin + "##k เป็นการตอบแทน" + enter;
		}


		if (haveNeed(1))
			cm.sendNext(msg);
		else {
			msg += enter + enter + "แต่... คุณไม่มีไอเทมที่จะแลก..";
			cm.sendOk(msg);
			cm.dispose();
		}
	} else if (status == 1) {
		temp = [];
		for (i = 0; i < need.length; i++) {
			temp.push(Math.floor(cm.itemQuantity(need[i]['itemid']) / need[i]['qty']));
		}
		temp.sort();
		max = temp[0];
		cm.sendGetNumber("แลกได้สูงสุด #b" + max + " ชิ้น#k..\r\nจะแลกกี่ชิ้น?", 1, 1, max);
	} else if (status == 2) {
		if (!haveNeed(sel)) {
			cm.sendOk("ไอเทมไม่พอ");
			cm.dispose();
			return;
		}
		for (i = 0; i < need.length; i++) {
			cm.gainItem(need[i]['itemid'], -(need[i]['qty'] * sel));
		}
		cm.gainItem(tocoin, (toqty * sel));
		cm.sendOk("ได้รับ #z4310249# แล้ว");
		cm.dispose();
	}
}

function haveNeed(a) {
	var ret = true;
	for (i = 0; i < need.length; i++) {
		if (!cm.haveItem(need[i]['itemid'], (need[i]['qty'] * a)))
			ret = false;
	}
	return ret;
}
