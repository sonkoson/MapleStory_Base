importPackage(Packages.constants);

var status = 0;
var invs = Array(1, 5, 6, 2, 3);
var invv;
var selected;
var slot_1 = Array();
var slot_2 = Array();
var slot_3 = Array();
var slot_4 = Array();
var slot_5 = Array();
var statsSel;
var 별 = "#fUI/FarmUI.img/objectStatus/star/whole#";

function start() {
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode != 1) {
		cm.dispose();
		return;
	}
	status++;
	if (status == 1) {
		// Changed to Item Drop
		cm.dispose();
		cm.openNpcCustom(cm.getClient(), 1012121, "아이템버리기");

		/*
				var txt = "[로얄메이플 아이템 버리기 시스템]";
				txt += "\r\n#d* 드롭에는 10 만 메소가 필요합니다.#k\r\n";
				//txt += "#L2##b장비 아이템#k 을 드롭하겠습니다.#k\r\n";
				txt += "#L3##b소비 아이템#k 을 드롭하겠습니다.#k\r\n";
				//txt += "#L5##b기타 아이템#k 을 드롭하겠습니다.#k\r\n";
				txt += "#L1##r캐쉬 아이템#k 을 드롭하겠습니다.#k\r\n";
				txt += "#L6##b캐시코디 아이템#k 을 드롭하겠습니다.#k\r\n";
				cm.sendSimple(txt);
		*/
	} else if (status == 2) {
		var ok = false;
		var selStr = "#fnNanumGothic Extrabold##dPlease select the item you want to drop.#k\r\n";
		for (var x = 0; x < invs.length; x++) {
			var inv = cm.getInventory(invs[x]);
			for (var i = 0; i <= inv.getSlotLimit(); i++) {
				if (x == 0) {
					slot_1.push(i);
				} else if (x == 1) {
					slot_2.push(i);
				} else if (x == 2) {
					slot_3.push(i);
				} else if (x == 3) {
					slot_4.push(i);
				} else if (x == 4) {
					slot_5.push(i);
				}
				var it = inv.getItem(i);
				if (it == null) {
					continue;
				}
				if (selection == 1) {
					var itemid = it.getItemId();
				} else if (selection == 2) {
					if (cm.isCash(it.getItemId())) {
						var itemid = 0;
					} else {
						var itemid = it.getItemId();
					}
				} else if (selection == 6) {
					var itemid = it.getItemId();
				} else if (selection == 3) {
					var itemid = it.getItemId();
				} else if (selection == 5) {
					var itemid = it.getItemId();
				}
				if (selection == 1) {
					if (!cm.isCash(itemid) || Packages.constants.GameConstants.isEquip(itemid)) {
						continue;
					}
				} else if (selection == 2) {
					if (!Packages.constants.GameConstants.isEquip(itemid)) {
						continue;
					}
				} else if (selection == 3) {
					if (!Packages.constants.GameConstants.isUse(itemid)) {
						continue;
					}
				} else if (selection == 5) {
					if (!Packages.constants.GameConstants.isEtc(itemid)) {
						continue;
					}
				} else if (selection == 6) {
					if (!cm.isCash(itemid) || !Packages.constants.GameConstants.isEquip(itemid)) {
						continue;
					}
				}
				ok = true;
				selStr += "#L" + (invs[x] * 1000 + i) + "##v" + itemid + "##t" + itemid + "##l\r\n";
			}
		}
		if (!ok) {
			cm.sendOk("#fnNanumGothic Extrabold##rIt seems you have no items in your inventory..#k");
			cm.dispose();
			return;
		}
		cm.sendSimple(selStr + "#k");
	} else if (status == 3) {
		invv = selection / 1000;
		invv = Math.floor(invv)
		selected = selection % 1000;
		var inzz = cm.getInventory(1);
		if (invv == 6) {
			inzz = cm.getInventory(6);
		} else {
			inzz = cm.getInventory(invv);
		}
		if (invv == invs[0]) {
			statsSel = inzz.getItem(slot_1[selected]);
		} else if (invv == invs[1]) {
			statsSel = inzz.getItem(slot_2[selected]);
		} else if (invv == invs[2]) {
			statsSel = inzz.getItem(slot_3[selected]);
		} else if (invv == invs[3]) {
			statsSel = inzz.getItem(slot_4[selected]);
		} else if (invv == invs[4]) {
			statsSel = inzz.getItem(slot_5[selected]);
		}
		if (statsSel == null) {
			cm.sendOk("#fnNanumGothic Extrabold##rError Information\r\n\r\nPlease try again.#k");
			cm.dispose();
			return;
		}
		cm.sendGetNumber("#fnNanumGothic Extrabold##v" + statsSel.getItemId() + "##t" + statsSel.getItemId() + "#\r\n\r\n#dPlease enter the quantity to drop.#k", 1, 1, statsSel.getQuantity());
	} else if (status == 4) {
		if (cm.getMeso() >= 100000) {
			if (statsSel.getItemId() !== 1143032 && statsSel.getItemId() !== 1142373 && statsSel.getItemId() !== 1112750 && statsSel.getItemId() !== 1182058 && statsSel.getItemId() !== 1142551 && statsSel.getItemId() !== 1182062 && statsSel.getItemId() !== 1182063 && statsSel.getItemId() !== 1182064 && statsSel.getItemId() !== 1182192) {
				cm.gainMeso(-100000);
				if (!cm.dropItem(selected, invv, selection)) {
					cm.sendOk("#fnNanumGothic Extrabold##r[Error Information]#k\r\n\r\nPlease try again.");
				}
			} else {
				cm.sendOk("#fnNanumGothic Extrabold##rYou cannot drop this item.#k");
				cm.dispose();
			}
		} else {
			cm.sendOk("#fnNanumGothic Extrabold##rNot enough mesos to drop.#k");
			cm.dispose();
		}
		cm.dispose();
	}
}
