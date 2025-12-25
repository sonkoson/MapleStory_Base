importPackage(java.lang);
importPackage(java.text);
importPackage(Packages.scripting);

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, sel) {
	if (mode == 1) {
		status++;
	} else {
		if (mode != 3) {
			cm.dispose();
		}
		return;
	}
	if (status == 0) {
		cm.setIngameDirectionMode(false, false, false);
		cm.setBlind(1, 255, 0, 0, 0, 0, 0);
		cm.setStandAloneMode(true);
		cm.spawnLocalNpc(9062474, -1000, 30, 0, 53, true);
		cm.spawnLocalNpc(9062475, -1080, 30, 1, 53, true);
		cm.setBlind(1, 255, 0, 0, 0, 250, 2);
		cm.delay(250);
	} else if (status == 1) {
		cm.overlapDetail(0, 1000, 3000, 1);
		cm.cameraZoom(0, 1000, 2147483647, 2147483647, 2147483647);
		cm.cameraMoveBack(0, 0);
		cm.delay(300);
	} else if (status == 2) {
		cm.removeOverlapDetail(1000);
		cm.delay(100);
	} else if (status == 3) {
		cm.cameraZoom(0, 2000, 0, -1500, 100);
		cm.setBlind(1, 255, 0, 0, 0, 0, 2);
		cm.delay(100);
	} else if (status == 4) {
		cm.setBlind(0, 0, 0, 0, 0, 250, 0);
		cm.delay(2500);
	} else if (status == 5) {
		cm.effectText("#fnNanumGothic ExtraBold##fs18#<Neo Castle>", 100, 1000, 6, 0, -50, -50);
		cm.delay(2000);
	} else if (status == 6) {
		cm.sayNpc("ว้าว...", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 7) {
		cm.sayNpc("พอดูใกล้ๆ แล้วเป็น #r#eปราสาทที่ใหญ่โต#n#kจริงๆ สินะ...?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 8) {
		cm.delay(1000);
	} else if (status == 9) {
		cm.forcedMove(2, 400);
		cm.cameraZoom(4000, 2000, 4000, -1100, 100);
		cm.delay(4000);
	} else if (status == 10) {
		cm.effectPlay("Effect/OnUserEff.img/emotion/whatl", 0, 0, 0, 0, 0, false);
		cm.delay(2000);
	} else if (status == 11) {
		cm.effectPlay("Effect/OnUserEff.img/emotion/oh", 0, 0, 0, 0, 9062474, false);
		cm.delay(500);
	} else if (status == 12) {
		cm.npcFlipForcely(9062475, -1);
		cm.delay(500);
	} else if (status == 13) {
		cm.effectPlay("Effect/OnUserEff.img/emotion/oh", 0, 0, 0, 0, 9062475, false);
		cm.delay(2000);
	} else if (status == 14) {
		cm.sayReplacedNpc("ว้าว! #e#b#h0##k!!!", false, true, 1, 9062475, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 15) {
		cm.sayReplacedNpc("เฮ้! รอนานแล้ว!..#fs30##rยินดีที่ได้เจอ! ดีใจใช่ไหม? ยินดีต้อนรับ!", false, true, 1, 9062474, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 16) {
		cm.sayReplacedNpc("นั่ง #bกวางเรนเดียร์#k ที่ฉันส่งไปสบายดีไหมคะ?", false, true, 1, 9062475, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 17) {
		cm.npcMoveForcely(9062474, -1, 10, 100);
		cm.delay(1000);
	} else if (status == 18) {
		cm.sayReplacedNpc("ใจเย็นๆ ริโอ... อย่าทำให้แขกตกใจสิ", false, true, 1, 9062474, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 19) {
		cm.sayReplacedNpc("อ๊ะ... ขอโทษค่ะ!\r\nหนูชื่อ #bRio#k ค่ะ!", false, true, 1, 9062475, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 20) {
		cm.sayReplacedNpc("หึหึ. #e#b#h0##k. \r\nในที่สุดก็มาสินะ ฉันชื่อ #bRene#k", false, true, 1, 9062474, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 21) {
		cm.sayReplacedNpc("ที่นี่คือ #r<Neo Castle>#k.\r\nเป็น #b#eภาพมายา#n#k ที่สะท้อนในจุดที่มิติทับซ้อนกัน", false, true, 1, 9062474, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 22) {
		cm.sayNpc("พวกเธอคือภูตประจำ Neo Castle สินะ.\r\nแต่ว่า... #bภาพมายา#k งั้นเหรอ?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 23) {
		cm.sayReplacedNpc("ที่นี่ยังเป็นพื้นที่ที่เกิดจาก #bสองมิติที่แตกต่างกัน#k มารวมตัวกันค่ะ!\r\nหากมิติกลับสู่สภาวะปกติ Neo Castle ก็จะหายไปค่ะ!", false, true, 1, 9062475, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 24) {
		cm.sayReplacedNpc("หึหึ. ก็เลยเชิญทุกคนมาไงล่ะ.\r\nหลายคนได้รับคำเชิญและมาถึง Neo Castle แล้วนะ!", false, true, 1, 9062474, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 25) {
		cm.sayReplacedNpc("งั้นจะแนะนำคนที่มาก่อนแล้วให้รู้จักนะ", false, true, 1, 9062474, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 26) {
		cm.setBlind(1, 255, 0, 0, 0, 250, 2);
		cm.delay(250);
	} else if (status == 27) {
		cm.overlapDetail(0, 1000, 3000, 1);
		cm.cameraZoom(0, 1000, 2147483647, 2147483647, 2147483647);
		cm.cameraMoveBack(0, 0);
		cm.delay(300);
	} else if (status == 28) {
		cm.removeOverlapDetail(1000);
		cm.removeLocalNpc(9062474);
		cm.removeLocalNpc(9062475);
		cm.cameraZoom(0, 2000, 0, -500, -100);
		cm.delay(1000);
	} else if (status == 29) {
		cm.setBlind(1, 255, 0, 0, 0, 0, 2);
		cm.delay(100);
	} else if (status == 30) {
		cm.setBlind(0, 0, 0, 0, 0, 250, 0);
		cm.delay(300);
	} else if (status == 31) {
		cm.sayReplacedNpc("ใครชอบเล่นคนเดียว! มารวมกันตรงนี้~", false, true, 1, 9062461, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 32) {
		cm.sayReplacedNpc("ไม่มีใครเล่นเกมเก่งไปกว่าฉันหรอก?!", false, true, 1, 9062462, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 33) {
		cm.sayReplacedNpc("ทุกคนกำลัง #bรวมตัวกันเล่นเกม#k อยู่ค่ะ!", false, true, 1, 9062475, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 34) {
		cm.sayReplacedNpc("เกมงั้นเหรอ... เหมือนเด็กๆ เลยนะ. \r\nฉันจะเป็น #rพ่อค้ายอดฝีมือ#k ให้ได้เลย!", false, true, 1, 9062463, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 35) {
		cm.sayReplacedNpc("ถ้าสนุกกับ #bเกม#k จะได้รับ #i4310307:##b#t4310307##k ค่ะ!", false, true, 1, 9062475, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 36) {
		cm.sayReplacedNpc("ใช้ #i4310307:##b#t4310307##k แลกของ #rเจ๋งๆ#k จากเพื่อนพ่อค้าฝึกหัดคนนั้นได้นะ", false, true, 1, 9062474, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 37) {
		cm.cameraZoom(1500, 2000, 1500, -250, -100);
		cm.delay(1500);
	} else if (status == 38) {
		cm.sayReplacedNpc("หืม... สัมผัสได้ถึงพลังที่แข็งแกร่ง น่าพอใจมาก", false, true, 1, 9062459, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 39) {
		cm.sayReplacedNpc("คนคนนั้นกำลังตามหาอัญมณีที่มีพลังมหาศาล \r\n#i4310308:##r#t4310308##k อยู่", false, true, 1, 9062474, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 40) {
		cm.sayReplacedNpc("ถ้ากำจัด #bบอสที่แข็งแกร่ง#k จะสามารถสร้าง #i4310308:##r#t4310308##k ได้ค่ะ!", false, true, 1, 9062475, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 41) {
		cm.cameraZoom(3000, 2000, 3000, 400, -100);
		cm.delay(3000);
	} else if (status == 42) {
		cm.sayReplacedNpc("โอ้... คุณมีของดีนี่นา?", false, true, 1, 9062457, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 43) {
		cm.sayReplacedNpc("หึหึ. ใน Maple World มีของล้ำค่ามากมาย\r\nใน Grandis ก็มีของแปลกๆ เยอะเหมือนกันสินะ", false, true, 1, 9062455, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 44) {
		cm.sayReplacedNpc("ที่นี่มี #rพ่อค้าหลากหลายคน#k เลยค่ะ!", false, true, 1, 9062475, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 45) {
		cm.sayReplacedNpc("ถ้าเอา #i4310306:##b#t4310306##k แหล่งพลังงานของ Neo Castle \r\nไปให้พ่อค้าพวกนั้น จะได้ #rของล้ำค่า#k กลับมานะ", false, true, 1, 9062474, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 46) {
		cm.cameraZoom(1500, 2000, 1500, 700, -100);
		cm.delay(1500);
	} else if (status == 47) {
		cm.spawnLocalNpc(9062453, -230, -320, 0, 9, false);
		cm.spawnLocalNpc(9062454, 230, -320, 0, 9, false);
		cm.sayReplacedNpc("การฝึกฝนอย่างสม่ำเสมอเป็นหนทางเดียวที่จะปกป้องสันติภาพได้", false, true, 1, 9062451, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 48) {
		cm.sayReplacedNpc("บางคนแม้ได้รับเชิญมาก็ยังขยันฝึกซ้อมนะ", false, true, 1, 9062474, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 49) {
		cm.cameraZoom(2000, 2000, 2000, 0, -300);
		cm.delay(2000);
	} else if (status == 50) {
		cm.sayNpc("มีผู้คนหลากหลายจาก #bMaple World#k และ #rGrandis#k มารวมตัวกันจริงๆ ด้วย", GameObjectType.User, false, true, 1, ScriptMessageFlag.Self, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage, ScriptMessageFlag.NoEsc);
	} else if (status == 51) {
		cm.sayReplacedNpc("แน่นอนค่ะ! คุณมาช้าเกินไปแล้วนะคะ!!", false, true, 1, 9062475, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 52) {
		cm.cameraZoom(4000, 800, 4000, 0, -360);
		cm.delay(4000);
	} else if (status == 53) {
		cm.sayReplacedNpc("หึหึ ขอให้ #rNeo Castle#k เป็นความทรงจำที่ดีของคุณเหมือนกันนะ", false, true, 1, 9062474, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 54) {
		cm.sayReplacedNpc("มาสนุกกับช่วงเวลาดีๆ ที่ #rNeo Castle#k กับพวกเราเถอะค่ะ!", false, true, 1, 9062475, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
	} else if (status == 55) {
		cm.setBlind(1, 255, 0, 0, 0, 250, 2);
		cm.delay(250);
	} else if (status == 56) {
		cm.overlapDetail(0, 1000, 3000, 1);
		cm.cameraZoom(0, 1000, 2147483647, 2147483647, 2147483647);
		cm.cameraMoveBack(0, 0);
		cm.delay(300);
	} else if (status == 57) {
		cm.removeOverlapDetail(1000);
		cm.cameraMoveBack(0, 0);
		cm.delay(300);
	} else if (status == 58) {
		cm.setStandAloneMode(false);
		cm.delay(700);
	} else if (status == 59) {
		cm.endIngameDirectionMode(1);
		cm.removeLocalNpc(9062453);
		cm.removeLocalNpc(9062454);
		cm.getPlayer().setRegisterTransferFieldTime(System.currentTimeMillis() + 1000);
		cm.getPlayer().setRegisterTransferField(993189103);
		cm.dispose();
	}
}