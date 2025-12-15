var status = -1;

var data;
var day;

function start() {
	status = -1;
	action(1, 0, 0);
}

function String(date) {
	switch (date) {
		case 1:
		case 1:
			return "Creation Monday";
		case 2:
			return "Enhancement Tuesday";
		case 3:
			return "Trait Wednesday";
		case 4:
			return "Honor Thursday";
		case 5:
			return "Golden Friday";
		case 6:
			return "Festive Saturday";
		case 0:
			return "Growth Sunday";
	}
}

var map = new Array(953020000, 953030000, 953040000, 953050000, 953060000, 953070000, 953080000, 953090000, 954000000, 954010000, 954020000, 954030000, 954040000, 954050000, 954060000);
var mapname = new Array("Auto Security Area(Lv.105~114)", "Mossy Tree Forest(Lv.115~124)", "Sky Forest Training Center(Lv.120~129)", "Pirate's Secret Base(Lv.125~134)", "Otherworld Battleground(Lv.135~144)", "Remote Forest Danger Zone(Lv.140~149)", "Forbidden Time(Lv.145~154)", "Hidden Ruins(Lv.150~159)", "Ruined City(Lv.160~169)", "Dead Tree Forest(Lv.170~179)", "Watchman's Tower(Lv.175~184)", "Dragon Nest(Lv.180~189)", "Temple of Oblivion(Lv.185~194)", "Knight's Stronghold(Lv.190~199)", "Spirit Canyon(Lv.200~209)");
var exp = new Array(3517411, 5989675, 7311630, 8129820, 11524015, 11953470, 13978390, 15311670, 19790735, 26950030, 27953565, 33576980, 35340485, 39775800, 40650435);
var select;

function action(mode, type, selection) {
	if (mode == -1) {
		cm.dispose();
		return;
	}
	if (mode == 0) {
		status--;
	}
	if (mode == 1) {
		status++;
	}

	if (status == 0) {
		cm.sendOk("#fs11# The Monster Park entrance is firmly closed right now.");
		cm.dispose();
		return;
		/*
			if (!cm.getPlayer().isGM()) {
				cm.dispose();
				return;
			}
		*/

		getData();
		if (cm.getClient().getKeyValue(201820, "mc_" + data) == -1) {
			cm.getClient().setKeyValue(201820, "mc_" + data, "0");
		}

		var text = "#e<Today is #b" + String(Packages.tools.CurrentTime.getDayOfWeek()) + "#k.>\r\n<Today's Clears: #b" + cm.getClient().getKeyValue(201820, "mc_" + data) + " / 7#k (Per World Account)>\r\n#eFree Clears Remaining: #b" + (7 - cm.getClient().getKeyValue(201820, "mc_" + data)) + "#k#n#b\r\n";
		for (i = 0; i < map.length; i++) {
			text += "#L" + i + "#" + mapname[i] + "\r\n";
		}
		cm.sendSimple(text);
	} else if (status == 1) {
		select = selection;
		cm.sendYesNo("#e<Today is #b" + String(Packages.objects.utils.CurrentTime.getDayOfWeek()) + "#k.>\r\n\r\nSelected Dungeon : #b" + mapname[select] + "#k\r\n\r\n#kDo you want to enter the dungeon?#n");
	} else if (status == 2) {
		cm.dispose();
		if (cm.getClient().getKeyValue(201820, "mc_" + data) >= 7) {
			cm.sendOk("You have used all of today's clear counts.");
		} else {
			for (i = 0; i < 6; i++) {
				if (Packages.network.game.GameServer.getInstance(cm.getClient().getChannel()).getMapFactory().getMap(map[select] + (i * 100)).getCharactersSize() > 0) {
					cm.sendOk("Someone has already entered.");
					return;
				}
			}
			cm.warp(map[select], 0);
			cm.resetMap(map[select]);
			cm.getPlayer().setMparkexp(exp[select]);
			//cm.writeLog("Log/MonsterParkEntry.log", cm.getPlayer().getName()+" entered Monster Park.\r\n", true);
		}
	}
}


function getData() {
	time = new Date();
	year = time.getFullYear();
	month = time.getMonth() + 1;
	if (month < 10) {
		month = "0" + month;
	}
	date = time.getDate() < 10 ? "0" + time.getDate() : time.getDate();
	data = year + "" + month + "" + date;
	day = time.getDay();
}