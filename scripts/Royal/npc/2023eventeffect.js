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
		cm.delay(1200);
	} else if (status == 1) {
		cm.setBlind(0, 0, 0, 0, 0, 1000, 0); //off
		cm.effectText("#fnArial ExtraBold##fs20#<Sunflower Ridge>", 100, 1000, 6, 0, -50, -100);
		cm.delay(3000);
	} else if (status == 2) {
		cm.effectPlay("Effect/OnUserEff.img/emotion/whatl", 0, 0, 0, 0, 0, false);
		cm.delay(3000);
	} else if (status == 3) {
		cm.sayNpc("ที่นี่ที่ไหนกัน..?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 4) {
		cm.sayNpc("ทำไมจู่ๆ ถึงถูกดึงมาที่นี่.....", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 5) {
		cm.delay(1000);
	} else if (status == 6) {
		cm.delay(1000);
	} else if (status == 7) {
		cm.delay(1000);
	} else if (status == 8) {
		cm.sayReplacedNpc("เฮ้! เรารออยู่เลย!..#fs30##rยินดีที่ได้รู้จัก! ดีใจไหม? ยินดีต้อนรับ!", false, true, 1, 2074118, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 9) {
		cm.sayReplacedNpc("รู้ไหมว่าเรารอนานแค่ไหน?!", false, true, 1, 2074118, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 10) {
		cm.sayReplacedNpc("ใจเย็นๆ สิ... อย่าไปทำให้แขกตกใจเก้อ", false, true, 1, 2071005, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 11) {
		cm.sayReplacedNpc("อ๊ะ... ขอโทษฮะ นานๆ ทีจะเจอคนนอก", false, true, 1, 2074118, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 12) {
		cm.sayReplacedNpc("หึหึ. #e#b#h0##k#n \r\nในที่สุดก็มาสินะ #e#bนักผจญภัย#k แห่ง Ganglim World ใช่ไหม?", false, true, 1, 2071002, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 13) {
		cm.sayNpc("ใช่ครับ ถูกต้องแล้ว", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 14) {
		cm.sayReplacedNpc("ที่นี่คือ #r<Sunflower Ridge>#k.\r\nดูนั่นสิ #b#eMoon Bunny#n#k ที่กำลังเล่นว่าว น่ารักไหมล่ะ?", false, true, 1, 2071002, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 15) {
		cm.sayNpc("#bMoon Bunny#k..? ก็น่ารักดีนะครับ..?\r\nแล้วทำไมถึงรอผมล่ะครับ..?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 16) {
		cm.sayReplacedNpc("พวกเรามักจะอยู่ที่นี่เพื่อต้อนรับเทศกาลปีใหม่\r\nและ #bทักทาย#k เหล่า #bนักผจญภัย#k ที่แวะมาเยี่ยมเยียน", false, true, 1, 2071005, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 17) {
		cm.sayNpc("เอ่อ... ถ้าผมมีลูกกวาดบ้างก็คงดีนะ", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 18) {
		cm.sayReplacedNpc("#i4317001# #i4317002# #i4317003#\r\nเมื่อตกปลาหรือล่ามอนสเตอร์ ท่านมีโอกาสได้รับลูกกวาด 3 ชนิดนี้!", false, true, 1, 2071005, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 19) {
		cm.sayNpc("แล้วลูกกวาดเอาไปใช้ทำอะไรเหรอ?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 20) {
		cm.sayReplacedNpc("ถ้ารวบรวมลูกกวาดครบทั้ง 3 ชนิด แล้วมาคุยกับ #bNolbu#k\r\nจะสามารถแลกเปลี่ยนเป็น #i4310198# #b#z4310198##k ได้!", false, true, 1, 2074118, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 21) {
		cm.sayNpc("แล้วเหรียญปีใหม่เอาไปใช้อะไรได้?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 22) {
		cm.sayReplacedNpc("สามารถซื้อไอเท็มหลากหลายได้ที่ #bร้านค้าเหรียญกิจกรรม#k ไงล่ะ\r\nแน่นอนว่าต้องคุยกับ #bฉัน#k เพื่อเข้าใช้ร้านค้าด้วยนะ", false, true, 1, 2071002, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 23) {
		cm.sayReplacedNpc("พวกเราต้องรีบไปส่งลูกกวาดแล้ว\r\nขอให้สนุกกับการผจญภัยใน #b< Ganglim World >#k นะ", false, true, 1, 2071002, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 24) {
		cm.sayNpc("ข...ขอบคุณนะ\r\n#fs12#(เรื่องแค่นี้ทำไมต้องอธิบายซะยิ่งใหญ่เชียว...)", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 25) {
		cm.delay(1000);
	} else if (status == 26) {
		cm.sayReplacedNpc("#fs25#ทำอะไรอยู่ รีบมาเร็วเข้า!!", false, true, 1, 2071002, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 27) {
		cm.sayReplacedNpc("พวกเราขอตัวไปก่อนนะ ขอให้โชคดี!", false, true, 1, 2071005, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 28) {
		cm.sayReplacedNpc("#fs30##rลาก่อนฮะ ท่านนักผจญภัย!!!! อิอิ", false, true, 1, 2074118, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 29) {
		cm.delay(1000);
	} else if (status == 30) {
		cm.delay(1000);
	} else if (status == 31) {
		cm.sayNpc("งั้นเราก็ไปสนุกกับกิจกรรมบ้างดีกว่า?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 32) {
		cm.showEffect(false, "rootabyss/firework");
		cm.delay(5000);
	} else if (status == 33) {
		cm.setStandAloneMode(false);
		cm.endIngameDirectionMode(1);
		cm.warp(ServerConstants.TownMap, 0);
		cm.dispose();
		cm.openNpcCustom(cm.getClient(), 2071002, "2023event");
	}
}