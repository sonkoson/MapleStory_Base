importPackage(java.lang);
importPackage(java.text);

importPackage(Packages.scripting);
importPackage(Packages.constants);

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
		cm.setIngameDirectionMode(false, false, false);
		cm.setBlind(1, 255, 0, 0, 0, 0, 0);
		cm.setStandAloneMode(true);
		cm.spawnLocalNpc(2001003, -208, -108, 0, 156, true);
		cm.spawnLocalNpc(2001002, -92, 8, 1, 156, true);
		cm.delay(1200);
	} else if (status == 1) {
		cm.setBlind(0, 0, 0, 0, 0, 1000, 0); //off
		cm.effectText("#fnNanumGothic ExtraBold##fs20#<Christmas Hill>", 100, 1000, 6, 0, -50, -100);
		cm.delay(3000);
	} else if (status == 2) {
		cm.effectPlay("Effect/OnUserEff.img/emotion/whatl", 0, 0, 0, 0, 0, false);
		cm.delay(3000);
	} else if (status == 3) {
		cm.sayNpc("Where am I...?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 4) {
		cm.sayNpc("What are those snowmen doing there.....", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 5) {
		cm.effectPlay("Effect/OnUserEff.img/emotion/oh", 0, 0, 0, 0, 2001004, false);
		cm.delay(1000);
	} else if (status == 6) {
		cm.effectPlay("Effect/OnUserEff.img/emotion/oh", 0, 0, 0, 0, 2001003, false);
		cm.delay(1000);
	} else if (status == 7) {
		cm.effectPlay("Effect/OnUserEff.img/emotion/oh", 0, 0, 0, 0, 2001002, false);
		cm.delay(2000);
	} else if (status == 8) {
		cm.sayReplacedNpc("Hehe! I've been waiting!..#fs30##rNice to meet you! You're welcome here!", false, true, 1, 2001004, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 9) {
		cm.sayReplacedNpc("Do you know how long I've waited?!", false, true, 1, 2001004, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 10) {
		cm.sayReplacedNpc("Calm down, child... You shouldn't startle our guest.", false, true, 1, 2001002, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 11) {
		cm.sayReplacedNpc("Ah... Sorry, it's been so long since I met a human.", false, true, 1, 2001004, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 12) {
		cm.sayReplacedNpc("Huhu. #e#b#h0##k. \r\nYou've finally arrived. We are the #bFancy Snowmen#k.", false, true, 1, 2001003, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 13) {
		cm.sayNpc("Fancy Snowmen...?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 14) {
		cm.sayReplacedNpc("This is #r<Christmas Hill>#k.\r\nIt's a #b#eParadise#n#k where the moonlight shines so close.", false, true, 1, 2001003, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 15) {
		cm.sayNpc("#bChristmas Hill#k..? #bParadise#k..?\r\nBut why were you waiting for me..?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 16) {
		cm.sayReplacedNpc("We are always making Songpyeon here.\r\nWe greet the #bAdventurers#k who visit here for Christmas.", false, true, 1, 2001002, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 17) {
		cm.sayNpc("Uh... I wish I had those candies too.", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 18) {
		cm.sayReplacedNpc("#i4317001# #i4317002# #i4317003#\r\nYou can get these three candies with a certain probability when fishing and hunting!", false, true, 1, 2001002, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 19) {
		cm.sayNpc("What are those candies used for?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 20) {
		cm.sayReplacedNpc("If you bring all three candies and talk to #bme#k,\r\nyou can exchange them for #i4310320# #b#z4310320##k!", false, true, 1, 2001004, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 21) {
		cm.sayNpc("What are those Christmas Coins used for?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 22) {
		cm.sayReplacedNpc("You can buy various items at the #bEvent Coin Shop#k.\r\nOf course, talk to the #bScarf Snowman#k for the Coin Shop.", false, true, 1, 2001003, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 23) {
		cm.sayReplacedNpc("We have to go deliver candies now.\r\nWe hope you always have a fun adventure in #b< Royal Maple >#k.", false, true, 1, 2001003, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 24) {
		cm.sayNpc("Th...Thank you.\r\n#fs12#(It's not much, but the explanation is grand...)", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 25) {
		cm.removeLocalNpc(2001003);
		cm.delay(1000);
	} else if (status == 26) {
		cm.sayReplacedNpc("#fs25#What are you doing? Come on!!", false, true, 1, 2001003, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 27) {
		cm.sayReplacedNpc("We'll be going now. Have a great adventure!", false, true, 1, 2001002, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 28) {
		cm.sayReplacedNpc("#fs30##rGoodbye, Adventurer!!!! Hehe", false, true, 1, 2001004, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 29) {
		cm.delay(1000);
	} else if (status == 30) {
		cm.removeLocalNpc(2001002);
		cm.delay(1000);
	} else if (status == 31) {
	} else if (status == 31) {
		cm.sayNpc("Shall I go enjoy the event now too?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 32) {
		cm.showEffect(false, "rootabyss/firework");
		cm.delay(5000);
	} else if (status == 33) {
		cm.setStandAloneMode(false);
		cm.endIngameDirectionMode(1);
		cm.warp(ServerConstants.TownMap, 0);
		cm.dispose();
		cm.openNpcCustom(cm.getClient(), 2001004, "12dnjfevent");
	}
}