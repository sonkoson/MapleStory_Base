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
		cm.effectText("#fnArial ExtraBold##fs20#<Moon Bunny Hill>", 100, 1000, 6, 0, -50, -100);
		cm.delay(3000);
	} else if (status == 2) {
		cm.effectPlay("Effect/OnUserEff.img/emotion/whatl", 0, 0, 0, 0, 0, false);
		cm.delay(3000);
	} else if (status == 3) {
		cm.sayNpc("ที่นี่ที่ไหนกัน..?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 4) {
		cm.sayNpc("เจ้าพวกกระต่ายพวกนั้นคืออะไรกัน.....", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
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
		cm.sayReplacedNpc("เฮ้! เรารออยู่เลย!..#fs30##rยินดีที่ได้รู้จัก! ดีใจไหม? ยินดีต้อนรับ!", false, true, 1, 9001116, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 9) {
		cm.sayReplacedNpc("รู้ไหมว่าเรารอนานแค่ไหน?!", false, true, 1, 9001116, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 10) {
		cm.sayReplacedNpc("ใจเย็นๆ ลูกแม่... อย่าไปทำให้แขกตกใจสิ", false, true, 1, 9001115, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 11) {
		cm.sayReplacedNpc("อ๊ะ... ขอโทษฮะ นานๆ ทีจะเจอผู้คน", false, true, 1, 9001116, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 12) {
		cm.sayReplacedNpc("หึหึ. #e#b#h0##k. \r\nในที่สุดก็มาสินะ พวกเราคือ #bMoon Bunny#k", false, true, 1, 9001114, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 13) {
		cm.sayNpc("Moon Bunny...?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 14) {
		cm.sayReplacedNpc("ที่นี่คือ #r<Moon Bunny Hill>#k.\r\nเป็น #b#eภาพมายา#n#k ชั่วพริบตาที่แสงจันทร์ซ้อนทับกัน", false, true, 1, 9001114, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 15) {
		cm.sayNpc("#bMoon Bunny Hill#k..? #bภาพมายา#k..?\r\nแล้วทำไมถึงรอผมล่ะครับ..?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 16) {
		cm.sayReplacedNpc("พวกเรามักจะทำขนมไหว้พระจันทร์ที่นี่เสมอ\r\nเพื่อต้อนรับเทศกาล Chuseok และ #bทักทาย#k เหล่า #bนักผจญภัย#k ที่แวะมาเยี่ยมเยียน", false, true, 1, 9001115, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 17) {
		cm.sayNpc("เอ่อ... ถ้าผมมีขนมไหว้พระจันทร์บ้างก็คงดีนะ", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 18) {
		cm.sayReplacedNpc("#i3994408# #i3994409# #i3994410#\r\nเมื่อตกปลาหรือล่ามอนสเตอร์ ท่านมีโอกาสได้รับขนมไหว้พระจันทร์ 3 ชนิดนี้!", false, true, 1, 9001115, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 19) {
		cm.sayNpc("แล้วขนมไหว้พระจันทร์เอาไปใช้ทำอะไรเหรอ?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 20) {
		cm.sayReplacedNpc("ถ้ารวบรวมขนมไหว้พระจันทร์ครบทั้ง 3 ชนิด แล้วมาคุยกับ #bBaby Moon Bunny#k\r\nจะสามารถแลกเปลี่ยนเป็น #i4310185# #b#z4310185##k ได้!", false, true, 1, 9001116, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 21) {
		cm.sayNpc("แล้วเหรียญ Chuseok Coin เอาไปใช้อะไรได้?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 22) {
		cm.sayReplacedNpc("สามารถซื้อไอเท็มหลากหลายได้ที่ #bร้านค้าเหรียญกิจกรรม#k ไงล่ะ\r\nแน่นอนว่าต้องคุยกับ #bBaby Moon Bunny#k เพื่อเข้าใช้ร้านค้าด้วยนะ", false, true, 1, 9001114, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 23) {
		cm.sayReplacedNpc("พวกเราต้องรีบไปส่งขนมไหว้พระจันทร์แล้ว\r\nขอให้สนุกกับการผจญภัยใน #b< Ganglim World >#k นะ", false, true, 1, 9001114, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 24) {
		cm.sayNpc("ข...ขอบคุณนะ\r\n#fs12#(เรื่องแค่นี้ทำไมต้องอธิบายซะยิ่งใหญ่เชียว...)", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 25) {
		cm.removeLocalNpc(9001114);
		cm.delay(1000);
	} else if (status == 26) {
		cm.sayReplacedNpc("#fs25#ทำอะไรอยู่ รีบมาเร็วเข้า!!", false, true, 1, 9001114, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 27) {
		cm.sayReplacedNpc("พวกเราขอตัวไปก่อนนะ ขอให้โชคดี!", false, true, 1, 9001115, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 28) {
		cm.sayReplacedNpc("#fs30##rลาก่อนฮะ ท่านนักผจญภัย!!!! อิอิ", false, true, 1, 9001116, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 29) {
		cm.delay(1000);
	} else if (status == 30) {
		cm.removeLocalNpc(9001115);
		cm.delay(1000);
	} else if (status == 31) {
		cm.removeLocalNpc(9001116);
		cm.delay(1000);
	} else if (status == 32) {
		cm.sayNpc("งั้นเราก็ไปสนุกกับกิจกรรมบ้างดีกว่า?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 33) {
		cm.showEffect(false, "rootabyss/firework");
		cm.delay(5000);
	} else if (status == 34) {
		cm.setStandAloneMode(false);
		cm.endIngameDirectionMode(1);
		cm.warp(ServerConstants.TownMap, 0);
		cm.dispose();
		cm.openNpcCustom(cm.getClient(), 9001102, "2023cntjrevent");
	}
}