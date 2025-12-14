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
		cm.spawnLocalNpc(9001114, 708, 608, 0, 53, true);
		cm.spawnLocalNpc(9001115, 929, 608, 1, 53, true);
		cm.spawnLocalNpc(9001116, 1099, 608, 2, 53, true);
		cm.delay(1200);
	} else if (status == 1) {
		cm.setBlind(0, 0, 0, 0, 0, 1000, 0); //off
		cm.effectText("#fnArial##fs20#<Moon Bunny Hill>", 100, 1000, 6, 0, -50, -100);
		cm.delay(3000);
	} else if (status == 2) {
		cm.effectPlay("Effect/OnUserEff.img/emotion/whatl", 0, 0, 0, 0, 0, false);
		cm.delay(3000);
	} else if (status == 3) {
		cm.sayNpc("ที่นี่คือที่ไหนกัน..?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 4) {
		cm.sayNpc("เจ้ากระต่ายพวกนั้นมันอะไรกัน.....", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 5) {
		cm.effectPlay("Effect/OnUserEff.img/emotion/oh", 0, 0, 0, 0, 9001114, false);
		cm.delay(1000);
	} else if (status == 6) {
		cm.effectPlay("Effect/OnUserEff.img/emotion/oh", 0, 0, 0, 0, 9001115, false);
		cm.delay(1000);
	} else if (status == 7) {
		cm.effectPlay("Effect/OnUserEff.img/emotion/oh", 0, 0, 0, 0, 9001116, false);
		cm.delay(2000);
	} else if (status == 8) {
		cm.sayReplacedNpc("เฮ้! กำลังรออยู่เลย!..#fs30##rยินดีที่ได้พบ! ยินดีใช่ไหมล่ะ? ยินดีต้อนรับ!", false, true, 1, 9001116, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 9) {
		cm.sayReplacedNpc("รู้ไหมว่ารอนานแค่ไหน?!", false, true, 1, 9001116, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 10) {
		cm.sayReplacedNpc("ใจเย็นๆ สิเจ้าหนู...อย่าทำให้คนที่อุตส่าห์มาหาต้องตกใจสิ", false, true, 1, 9001115, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 11) {
		cm.sayReplacedNpc("อ๊ะ... ขอโทษที พอดีไม่ได้เจอคนมานาน", false, true, 1, 9001116, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 12) {
		cm.sayReplacedNpc("ฮุฮุ. #e#b#h0##k. \r\nในที่สุดก็มาสินะ พวกเราคือ #bMoon Bunny#k", false, true, 1, 9001114, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 13) {
		cm.sayNpc("Moon Bunny...?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 14) {
		cm.sayReplacedNpc("ที่นี่คือ #r<Moon Bunny Hill>#k.\r\nภาพลวงตาอันไร้แก่นสารที่สะท้อนในสถานที่ที่แสงจันทร์ทับซ้อนกัน", false, true, 1, 9001114, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 15) {
		cm.sayNpc("#bMoon Bunny Hill#k..? #bภาพลวงตา#k..?\r\nว่าแต่ทำไมถึงรอฉันล่ะ..?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 16) {
		cm.sayReplacedNpc("พวกเราจะทำขนมไหว้พระจันทร์อยู่ที่นี่เสมอ\r\nเลยถือโอกาสทักทายเหล่านักผจญภัยที่แวะมาเที่ยวในช่วงเทศกาลชูซอกน่ะ", false, true, 1, 9001115, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 17) {
		cm.sayNpc("เอ่อ... ขนมไหว้พระจันทร์นั่น ฉันก็อยากได้บ้างจัง", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 18) {
		cm.sayReplacedNpc("#i3994408# #i3994409# #i3994410#\r\nสามารถหาขนมไหว้พระจันทร์ทั้ง 3 ชนิดนี้ได้จากการตกปลาและล่ามอนสเตอร์!", false, true, 1, 9001115, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 19) {
		cm.sayNpc("แล้วขนมไหว้พระจันทร์พวกนี้เอาไปใช้ทำอะไรเหรอ?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 20) {
		cm.sayReplacedNpc("ถ้ารวบรวมขนมไหว้พระจันทร์ครบ 3 ชนิด แล้วไปคุยกับ #bBaby Moon Bunny#k\r\nจะสามารถแลกเป็น #i4310185# #b#z4310185##k ได้!", false, true, 1, 9001116, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 21) {
		cm.sayNpc("แล้วเหรียญชูซอกนั่นเอาไปใช้อะไรได้ล่ะ?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 22) {
		cm.sayReplacedNpc("สามารถนำไปซื้อไอเท็มต่างๆ ได้ที่ #bEvent Coin Shop#k\r\nแน่นอนว่าต้องไปคุยกับ #bBaby Moon Bunny#k เพื่อเข้า Coin Shop ด้วยนะ", false, true, 1, 9001114, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 23) {
		cm.sayReplacedNpc("พวกเราต้องไปส่งขนมไหว้พระจันทร์แล้วล่ะ\r\nขอให้สนุกกับการผจญภัยใน #b< Royal World >#k เสมอนะ", false, true, 1, 9001114, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 24) {
		cm.sayNpc("ข...ขอบใจนะ\r\n#fs12#(เรื่องแค่นี้เอง อธิบายซะยิ่งใหญ่เชียว...)", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 25) {
		cm.removeLocalNpc(9001114);
		cm.delay(1000);
	} else if (status == 26) {
		cm.sayReplacedNpc("#fs25#มัวทำอะไรอยู่ รีบมาเร็วเข้า!!", false, true, 1, 9001114, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 27) {
		cm.sayReplacedNpc("พวกเราขอตัวก่อนนะ ขอให้เป็นการผจญภัยที่ดี !", false, true, 1, 9001115, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 28) {
		cm.sayReplacedNpc("#fs30##rลาก่อนนะท่านนักผจญภัย!!!! อิอิ", false, true, 1, 9001116, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 29) {
		cm.delay(1000);
	} else if (status == 30) {
		cm.removeLocalNpc(9001115);
		cm.delay(1000);
	} else if (status == 31) {
		cm.removeLocalNpc(9001116);
		cm.delay(1000);
	} else if (status == 32) {
		cm.sayNpc("ฉันเองก็ไปสนุกกับกิจกรรมบ้างดีกว่าไหมนะ?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 33) {
		cm.showEffect(false, "rootabyss/firework");
		cm.delay(5000);
	} else if (status == 34) {
		cm.setStandAloneMode(false);
		cm.endIngameDirectionMode(1);
		cm.warp(ServerConstants.TownMap, 0);
		cm.dispose();
		cm.openNpcCustom(cm.getClient(), 9001102, "cntjrevent");
	}
}