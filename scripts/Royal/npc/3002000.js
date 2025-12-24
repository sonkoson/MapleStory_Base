var enter = "\r\n";

var itemid = 4001326;
var size = 7;

var first = 0;
var second = 0;

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
		var msg = "#fs11##fc0xFF000000#นี่คือรายการสีเทียนที่เจ้ามี! อยากแลกเปลี่ยนเป็นอะไรล่ะ?#b" + enter;

		for (i = 0; i < size; i++) {
			var id = itemid + i;
			if (cm.haveItem(id, 1))
				msg += "#b#L" + i + " ##i" + id + "##z" + id + "##l #r(มีอยู่ #c" + id + "# ชิ้น)#fc0xFF000000#" + enter;
		}

		cm.sendSimple(msg);
	} else if (status == 1) {

		first = sel;

		var msg = "#fs11##fc0xFF000000#จะแลกเป็นสีเทียนสีอะไร?" + enter;

		for (i = 0; i < size; i++) {
			if (first != i) {
				var id = itemid + i;
				msg += "#b#L" + i + "##i" + id + "##z" + id + "##k" + enter;
			}
		}

		cm.sendSimple(msg);
	} else if (status == 2) {
		second = sel;
		var id = itemid + first;
		var max = cm.itemQuantity(id);
		cm.sendGetNumber("#fs11##fc0xFF000000#เจ้าสามารถแลกได้สูงสุด #b" + max + " ชิ้น#k \r\nจะแลกเท่าไหร่?", 1, 1, max);
	} else if (status == 3) {
		var id = itemid + first;
		if (!cm.haveItem(id, 1)) {
			cm.sendOk("#fs11##fc0xFF000000#ไอเท็มไม่พอนี่นา?");
			cm.dispose();
			return;
		}
		cm.gainItem(id, -sel)
		cm.gainItem(itemid + second, sel);
		cm.sendOk("#fs11##fc0xFF000000#แลกเปลี่ยนเรียบร้อยแล้ว!");
		cm.dispose();
	}
}
