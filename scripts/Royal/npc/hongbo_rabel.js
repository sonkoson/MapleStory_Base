star = "#fUI/FarmUI.img/objectStatus/star/whole#";
starWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/10#"


function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == -1) {
		cm.dispose();
	} else {
		if (mode == 0) {
			cm.dispose();
			return;
		}
		if (mode == 1)
			status++;
		else
			status--;
		if (status == 0) {
			if (cm.getPlayer().getLevel() < 10) {
				cm.sendOk("   #bAvailable for Level 10 or higher.");
				cm.dispose();
				return;
			}
			var chat = "Hello? #eJin:True#n's #i5680157##z5680157# gives #bSpecial Label, Red Label, Black Label, Master Label#k. Using #i5069001##z5069001#, you can fuse #rSpecial Labels without effects or Special Label + Red Label#k to try for a #eMaster Label#n.\r\n#r*Black Labels cannot be fused.\r\n#r*Search Cash items have no effect.#k\r\n" + star + " Current #b#e#h0##n#k's #b#i4430000##ePromotion Points#n#k : #r" + cm.getHongboPoint() + " P#k\r\n";
			chat += "――――――――――――――――――――――――――――――――――――――――";
			chat += "#L0##e#dExplain Master Label set effects and options.#k#n#l\r\n\r\n";
			chat += "#L1##e#i5680157# #b#z5680157##k 1 pc #d#e#r(2000 Point)#k#n#l\r\n";
			chat += "#L2##e#i5680157# #b#z5680157##k 5 pcs #d#e#r(10000 Point)#k#n#l\r\n";
			chat += "#L3##e#i5680157# #b#z5680157##k #r10+1#k pcs #d#e#r(20000 Point)#k#n#l\r\n";
			chat += "#L4##e#i5069001# #b#z5069001##k 1 pc #d#e#r(1500 Point)#k#n#l\r\n";
			chat += "#L5##e#i5069001# #b#z5069001##k 5 pcs #d#e#r(7500 Point)#k#n#l\r\n";
			chat += "#L6##e#i5069001# #b#z5069001##k #r10+1#k pcs #d#e#r(15000 Point)#k#n#l\r\n";
			cm.sendSimple(chat);
		} else if (selection == 0) {
			cm.sendOk("#b* Special Label : No Options\r\n* Red Label : Random Stat + 10 / Att, Mag + 10\r\n* Black Label : Random Stat + 20 / Att, Mag + 20\r\n* Master Label : Random Stat + 40 / Att, Mag + 40#k\r\n#r#e\r\n<Master Label Set Effects>#n#k\r\n#d- 3 Set : All Stat 10%\r\n- 4 Set : All Stat 15%\r\n- 5 Set : All Stat 20%\r\n- 6 Set : All Stat 25% + Att/Mag 10%#k\r\n");
			cm.dispose();
		} else if (selection == 1) {
			if (cm.getHongboPoint() >= 2000) {
				if (cm.canHold(5680157)) {
					cm.gainHongboPoint(-2000);
					cm.gainItem(5680157, 1);
					cm.sendOk("Purchased #i5680157# #r1 pc#k with #bPromotion Points#k.");
					cm.dispose();
				} else {
					cm.sendOk("#rNo empty space in Cash Inventory.#k");
					cm.dispose();
				}
			} else {
				cm.sendOk("   Not enough #bPromotion Points#k.");
				cm.dispose();
			}
		} else if (selection == 2) {
			if (cm.getHongboPoint() >= 10000) {
				if (cm.canHold(5680157)) {
					cm.gainHongboPoint(-10000);
					cm.gainItem(5680157, 5);
					cm.sendOk("Purchased #i5680157# #r5 pcs#k with #bPromotion Points#k.");
					cm.dispose();
				} else {
					cm.sendOk("#rNo empty space in Cash Inventory.#k");
					cm.dispose();
				}
			} else {
				cm.sendOk("   Not enough #bPromotion Points#k.");
				cm.dispose();
			}
		} else if (selection == 3) {
			if (cm.getHongboPoint() >= 20000) {
				if (cm.canHold(5680157)) {
					cm.gainHongboPoint(-20000);
					cm.gainItem(5680157, 11);
					cm.sendOk("Purchased #i5680157# #r11 pcs#k with #bPromotion Points#k.");
					cm.dispose();
				} else {
					cm.sendOk("#rNo empty space in Cash Inventory.#k");
					cm.dispose();
				}
			} else {
				cm.sendOk("   Not enough #bPromotion Points#k.");
				cm.dispose();
			}
		} else if (selection == 4) {
			if (cm.getHongboPoint() >= 1500) {
				if (cm.canHold(5069001)) {
					cm.gainHongboPoint(-1500);
					cm.gainItem(5069001, 1);
					cm.sendOk("Purchased #i5069001# #r1 pc#k with #bPromotion Points#k.");
					cm.dispose();
				} else {
					cm.sendOk("#rNo empty space in Cash Inventory.#k");
					cm.dispose();
				}
			} else {
				cm.sendOk("   Not enough #bPromotion Points#k.");
				cm.dispose();
			}
		} else if (selection == 5) {
			if (cm.getHongboPoint() >= 7500) {
				if (cm.canHold(5069001)) {
					cm.gainHongboPoint(-7500);
					cm.gainItem(5069001, 5);
					cm.sendOk("Purchased #i5069001# #r5 pcs#k with #bPromotion Points#k.");
					cm.dispose();
				} else {
					cm.sendOk("#rNo empty space in Cash Inventory.#k");
					cm.dispose();
				}
			} else {
				cm.sendOk("   Not enough #bPromotion Points#k.");
				cm.dispose();
			}
		} else if (selection == 6) {
			if (cm.getHongboPoint() >= 15000) {
				if (cm.canHold(5069001)) {
					cm.gainHongboPoint(-15000);
					cm.gainItem(5069001, 11);
					cm.sendOk("Purchased #i5069001# #r11 pcs#k with #bPromotion Points#k.");
					cm.dispose();
				} else {
					cm.sendOk("#rNo empty space in Cash Inventory.#k");
					cm.dispose();
				}
			} else {
				cm.sendOk("   Not enough #bPromotion Points#k.");
				cm.dispose();
			}
		}
	}
}