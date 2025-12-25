importPackage(Packages.client.inventory);
importPackage(Packages.packet.creators);
importPackage(Packages.constants);
importPackage(Packages.client);
importPackage(Packages.server);
importPackage(java.lang);
importPackage(Packages.objects.item);

function start() {
	St = -1;
	action(1, 0, 0);
}

function action(M, T, S) {
	if (M != 1)
		cm.dispose();

	else
		St++;

	if (St == 0) {
		SET_GIV = cm.getClient().getKeyValue("GM_SETTING_GIV");
		SET_DEL = cm.getClient().getKeyValue("GM_SETTING_DEL");

		if (!cm.getPlayer().isGM()) {
			cm.dispose();
			return;
		}
		if (cm.getPlayer().isGM()) {
			//log("access", "NPC Access \r\n");
			cm.sendOk("ยินดีต้อนรับ GM #b#e#h ##k#n มีอะไรให้ช่วยไหมครับ?\r\n#b"
				+ "#L0#ค้นหาตัวละคร (Lookup Character)#l\r\n"
				+ "#L1#เปลี่ยนการตั้งค่า (Change Settings)#l\r\n\r\n");
		} else {
			cm.sendOk("มองอะไรครับ?");
			cm.dispose();
		}
	}

	else if (St == 1) {
		S1 = S;
		switch (S1) {

			//checkOnOff()
			case 1:
				selStr = "นี่คือการตั้งค่าปัจจุบัน เลือกหัวข้อเพื่อเปิดปิด #bON#k/#rOFF#k\r\n"
				selStr += "#L10#" + checkOnOff(SET_GIV, "color") + "ส่งประกาศเมื่อมอบไอเทม #e(" + checkOnOff(SET_GIV, "string") + ")#n\r\n";
				selStr += "#L11#" + checkOnOff(SET_DEL, "color") + "ส่งประกาศเมื่อลบไอเทม #e(" + checkOnOff(SET_DEL, "string") + ")#n\r\n";
				cm.sendOk(selStr);
				break;

			case 0:
				cm.sendGetText("ยินดีต้อนรับ GM #b#e#h ##k#n กรุณากรอกชื่อตัวละครเป้าหมาย (ต้องออนไลน์อยู่เท่านั้น)");
				break;
		}
	}

	else if (St == 2) {
		S2 = S;

		switch (S1) {
			case 1: //Change Settings
				switch (S2) {
					case 10:
						if (SET_GIV == -1)
							cm.getClient().setKeyValue("GM_SETTING_GIV", 1);

						else
							cm.getClient().setKeyValue("GM_SETTING_GIV", -1);

						cm.getPlayer().dropMessage(5, "เปลี่ยนการตั้งค่าส่งประกาศเมื่อมอบไอเท็มเป็น " + checkOnOff(SET_GIV * -1, "string") + " แล้ว");
						break;

					case 11:
						if (SET_DEL == -1) //OFF
							cm.getClient().setKeyValue("GM_SETTING_DEL", 1);

						else
							cm.getClient().setKeyValue("GM_SETTING_DEL", -1);

						cm.getPlayer().dropMessage(5, "เปลี่ยนการตั้งค่าส่งประกาศเมื่อลบไอเท็มเป็น " + checkOnOff(SET_DEL * -1, "string") + " แล้ว");
						1
						break;
				}
				cm.dispose();
				cm.openNpc(9010017);
				break;

			case 0:
				name = cm.getText();

				chr = findPlayerByName(name);
				if (chr != null) {
					//log("access", ""+name+"("+chr.getId()+") Config Accessed\r\n");
					cm.sendOk("เข้าถึงตัวละคร #b#e" + chr.getName() + "#k#n แล้ว มีอะไรให้ช่วยไหมครับ?#b\r\n\r\n#fnArial##fs11#"
						+ "   - เมโซที่มี | #fnArial#" + Comma(chr.getMeso()) + " (ถุงพันล้าน: " + Comma(chr.itemQuantity(4001716)) + "ชิ้น)#fnArial#\r\n"
						+ "   - Aqua Coins ที่มี | " + Comma(chr.itemQuantity(4310237)) + "ชิ้น\r\n"
						+ "   - Neo Stones ที่มี | " + Comma(chr.getKeyValue(100711, "point")) + "\r\n"
						+ "   - Neo Gems ที่มี | " + Comma(chr.getKeyValue(100712, "point")) + "\r\n"
						+ "   - Neo Cores ที่มี | " + Comma(chr.getKeyValue(501215, "point")) + "\r\n"
						+ "   - แต้มโปรโมท (Promotion Points) | " + Comma(chr.getHPoint()) + "\r\n"
						+ "   - แต้มโดเนท (Donation Points) | " + Comma(chr.getDonationPoint()) + "\r\n#fs12##fnArial#"
						//+ "#L16#Give Promotion Points#l #L17#Give Donation Points#l\r\n"
						+ "#L13#มอบ Neo Stones#l#L14#มอบ Neo Gems#l#L15#มอบ Neo Cores#l\r\n\r\n"
						+ "#L0#ตรวจสอบช่องสวมใส่ (Equipped Inventory)#l\r\n"
						+ "#L1#ตรวจสอบช่อง Equip#l\r\n"
						+ "#L2#ตรวจสอบช่อง Use#l\r\n"
						+ "#L3#ตรวจสอบช่อง Setup#l\r\n"
						+ "#L4#ตรวจสอบช่อง Etc#l\r\n"
						+ "#L5#ตรวจสอบช่อง Cash#l\r\n"
						+ "#L6#ตรวจสอบช่อง Decoration#l\r\n\r\n"
						+ "#L7#ตรวจสอบคลังเก็บของ (Storage)#l\r\n\r\n"
						+ "#L10#ส่งข้อความหาตัวละคร#l\r\n"
						+ "#L11#มอบไอเทมให้ตัวละคร#l\r\n"
						+ "#L12#ตรวจสอบ Macro#l\r\n");
				}
				else {
					cm.sendOk("ไม่สามารถเข้าถึงตัวละครได้ อาจกำลังย้ายแชนแนลหรือออฟไลน์");
					cm.dispose();
					return;
				}

		}
	}

	else if (St == 3) {
		S3 = S;
		switch (S1) {

			case 0:
				if (chr == null) {
					cm.sendOk("ตัวละครได้ตัดการเชื่อมต่อหรือออฟไลน์ไปแล้ว");
					//log("fail", "Fail	"+name+"	Disconnected during lookup\r\n");
					cm.dispose();
					return;
				}

				if (S3 == 999) {
					chr.addKV("Hard_Will", "0");
					chr.dropMessage(5, "คืนค่าจำนวนนับเรียบร้อยแล้ว");
					cm.dispose();
					return;
				}

				if (S3 == 10) {
					cm.sendGetText("กรุณากรอกข้อความที่จะส่งถึง " + chr.getName() + " (อาจถูกยกเลิกหากข้อความยาวเกินไป)");
					return;
				}

				if (S3 == 11) {
					cm.sendGetNumber("กรุณากรอกรหัสไอเทมที่จะมอบให้ " + chr.getName() + ".", 0, 0, 5999999);
					return;
				}


				if (S3 == 12) {
					if (chr == null) {
						cm.sendOk("ตัวละครได้ตัดการเชื่อมต่อหรือออฟไลน์ไปแล้ว");
						//log("fail", "Fail	"+name+"	Disconnected during lookup\r\n");
						cm.dispose();
						return;
					}

					cm.dispose();
					cm.openNpcCustom2(chr.getClient(), 2007, "macro");
					cm.getPlayer().dropMessage(5, "เริ่มการทดสอบมาโครกับตัวละคร " + name + " แล้ว");
					return;
				}

				if (S3 >= 13 && S3 <= 15) {
					cm.sendGetNumber("กรุณากรอกจำนวนที่จะมอบให้ " + chr.getName() + ".", 1, 1, 9999);
					return;
				}

				if (S3 >= 16 && S3 <= 17) {
					cm.sendGetNumber("กรุณากรอกแต้มที่จะมอบให้ " + chr.getName() + ".", 1, 1, 999999);
					return;
				}

				if (S3 == 7) {
					//log("access", ""+name+"("+chr.getId()+") Accessing Storage\r\n");

					selStr = "เข้าถึง #b#e" + chr.getName() + "#k#n's #e#rคลังเก็บของ (Storage)#k#n. ไอเทมในคลังแสดงผลเท่านั้น (View-only)\r\n\r\n#b";

					for (w = 0; w < chr.getStorage().getItems().size(); w++)
						selStr += "#i" + chr.getStorage().getItems().get(w).getItemId() + "# #z" + chr.getStorage().getItems().get(w).getItemId() + "# #e" + chr.getStorage().getItems().get(w).getQuantity() + "ชิ้น#n\r\n";

					cm.sendOk(selStr);
					cm.dispose();
					return;
				}

				if (S3 < 7) // Inventory
				{
					invType = S3 == 0 ? "Equipped" : S3 == 1 ? "Equip" : S3 == 2 ? "Use" : S3 == 3 ? "Setup" : S3 == 4 ? "Etc" : S3 == 5 ? "Cash" : "Decoration";
					//log("access", ""+name+"("+chr.getId()+") Accessing "+invType+" Inventory\r\n");

					selStr = "เข้าถึงช่องเก็บของ #e#r" + invType + "#k#n ของ #b#e" + chr.getName() + "#k#n ต้องการตรวจสอบไอเทมชิ้นไหน?\r\n#b";
					//	inv = (S3 != 0) ? chr.getInventory(S3) : chr.getInventory(MapleInventoryType.EQUIPPED);
					switch (S3) {
						case 0:
							inv = chr.getInventory(MapleInventoryType.EQUIPPED)
							break;
						case 1:
							inv = chr.getInventory(MapleInventoryType.EQUIP);
							break;
						case 2:
							inv = chr.getInventory(MapleInventoryType.USE);
							break;
						case 3:
							inv = chr.getInventory(MapleInventoryType.SETUP);
							break;
						case 4:
							inv = chr.getInventory(MapleInventoryType.ETC);
							break;
						case 5:
							inv = chr.getInventory(MapleInventoryType.CASH);
							break;


					}

					if (S3 != 0) {
						for (z = 0; z < inv.getSlotLimit(); z++) {
							if (inv.getItem(z) == null)
								continue;

							selStr += "#L" + z + "# #i" + inv.getItem(z).getItemId() + ":# #t" + inv.getItem(z).getItemId() + ":# (จำนวน : " + inv.getItem(z).getQuantity() + ")\r\n";
						}
					}
					else {
						for (z = 0; z < 100; z++) {
							a = -1 * z;
							if (inv.getItem(a) == null)
								continue;

							selStr += "#L" + z + "# " + z + ". #i" + inv.getItem(a).getItemId() + ":# #t" + inv.getItem(a).getItemId() + ":#\r\n";

							if (z > 100)
								break;
						}

					}
					cm.sendOk(selStr);
				}
				else {
					cm.sendOk("ฟีเจอร์นี้ยังไม่เปิดใช้งาน");
					cm.dispose();
				}
				break;
		}
	}

	else if (St == 4) {
		S4 = (S3 != 0) ? S : -1 * S;
		switch (S1) {
			case 0:
				if (chr == null) {
					cm.sendOk("ตัวละครได้ตัดการเชื่อมต่อหรือออฟไลน์ระหว่างการค้นหา");
					//log("fail", "Lookup Fail	"+name+"	Disconnected during lookup\r\n");
					cm.dispose();
					return;
				}

				if (S3 < 10) // Inventory
				{
					//log("access", ""+name+"("+chr.getId()+") Accessing "+invType+" Inventory Item "+inv.getItem(S4).getItemId()+"\r\n");

					selStr = "กำลังดูช่องเก็บของ #e#r" + invType + "#k#n ของ #b#e" + chr.getName() + "#k#n\r\n";
					selStr += "ไอเทมที่เลือก: #e#b#t" + inv.getItem(S4).getItemId() + ":##k#n.\r\n#b";
					if (S3 < 2) //Equip
					{
						selStr += "#L20010#คัดลอกไอเทมมายังกระเป๋าของฉัน#l\r\n";

						if (S3 == 1)
							selStr += "#L20000#ลบไอเทมที่เลือกจากช่องเก็บของ#l";

						selStr += "\r\n\r\n\r\n#e[Detailed Stats]#k#n\r\n#fs11#";
						selStr += "  - STR #e+" + inv.getItem(S4).getStr() + "#n, DEX #e+" + inv.getItem(S4).getDex() + "#n, INT #e+" + inv.getItem(S4).getInt() + "#n, LUK #e+" + inv.getItem(S4).getLuk() + "#n\r\n"
						selStr += "  - ATT #e+" + inv.getItem(S4).getWatk() + "#n, MATK #e+" + inv.getItem(S4).getMatk() + "#n\r\n";
						selStr += "  - Special Option Status : #e" + inv.getItem(S4).getOwner() + "#n\r\n";

						selStr += "\r\n\r\n#e#fs12##b[Potential]#k#n\r\n#fs11#";
						selStr += "  - Line 1 : #e" + toString(inv.getItem(S4).getPotential1()) + "#n\r\n";
						selStr += "  - Line 2 : #e" + toString(inv.getItem(S4).getPotential2()) + "#n\r\n";
						selStr += "  - Line 3 : #e" + toString(inv.getItem(S4).getPotential3()) + "#n\r\n";
						selStr += "  - Line 4 : #e" + toString(inv.getItem(S4).getPotential4()) + "#n\r\n";
						selStr += "  - Line 5 : #e" + toString(inv.getItem(S4).getPotential5()) + "#n\r\n";
						selStr += "  - Line 6 : #e" + toString(inv.getItem(S4).getPotential6()) + "#n\r\n";


						cm.sendOk(selStr);
					}
					else {
						selStr += "\r\n#kปัจจุบันมี #e#r" + inv.getItem(S4).getQuantity() + " ชิ้น#k#n ในช่องนั้น\r\n#e#rกรุณากรอกจำนวนที่ต้องการลบ";
						cm.sendGetNumber(selStr, 0, 1, inv.getItem(S4).getQuantity());
					}
				}

				if (S3 == 10) {
					txt = cm.getText();
					if (txt.contains("cm") || txt.contains("Packages")) {
						cm.dispose();
						return;
					}
					selStr = "คุณต้องการส่งข้อความต่อไปนี้หา #e" + name + "#n ใช่ไหม? เลือกประเภทข้อความ\r\n\r\n#d" + txt + "#r\r\n";
					selStr += "#L5#ประกาศสีชมพู (Pink Notice)#l\r\n";
					selStr += "#L6#ประกาศสีฟ้า (Blue Notice)\r\n";

					if (txt.length < 40)
						selStr += "#L1#ประกาศแบบเด้ง (Popup Notice) (สูงสุด 40 ตัวอักษร)#l\r\n";
					cm.sendOk(selStr);
				}

				if (S3 == 11) {
					number = S4;
					ii = Packages.server.MapleItemInformationProvider.getInstance();
					if (!ii.itemExists(number)) {
						cm.sendOk("ไม่มีไอเทมนี้");
						cm.dispose();
						return;
					}
					cm.sendGetNumber("กรุณากรอกจำนวนที่จะมอบให้ " + chr.getName() + ".", 1, 1, 32767);
					return;
				}
				if (S3 >= 13 && S3 <= 15) {
					number = S4;
					if (S3 == 13) {
						txt = "มอบ " + number + " Neo Stones ให้ " + name + " แล้ว";
						chr.setKeyValue(100711, "point", chr.getKeyValue(100711, "point") + number);
					} else if (S3 == 14) {
						txt = "มอบ " + number + " Neo Gems ให้ " + name + " แล้ว";
						chr.setKeyValue(100712, "point", chr.getKeyValue(100712, "point") + number);
					} else if (S3 == 15) {
						txt = "มอบ " + number + " Neo Cores ให้ " + name + " แล้ว";
						chr.setKeyValue(501215, "point", chr.getKeyValue(501215, "point") + number);
					}
					cm.getPlayer().dropMessage(6, txt);
					cm.dispose();
					return;
				}
				if (S3 >= 16 && S3 <= 17) {
					number = S4;
					if (S3 == 13) {
						txt = "มอบ " + number + " แต้มโปรโมท ให้ " + name + " แล้ว";
						chr.setKeyValue(100711, "point", chr.getKeyValue(100711, "point") + number);
					} else if (S3 == 14) {
						txt = "มอบ " + number + " แต้มโดเนท ให้ " + name + " แล้ว";
						chr.setKeyValue(100712, "point", chr.getKeyValue(100712, "point") + number);
					}
					cm.getPlayer().dropMessage(6, txt);
					cm.dispose();
					return;
				}
				break;
		}
	}

	else if (St == 5) {
		S5 = S;
		switch (S1) {
			case 0:
				if (chr == null) {
					cm.sendOk("ตัวละครได้ตัดการเชื่อมต่อหรือออฟไลน์");
					//log("fail", "Fail	"+name+"	Disconnected during inventory lookup\r\n");
					cm.dispose();
					return;
				}

				if (S3 < 10) // Inventory
				{
					if (S5 != 20010) // Not Copy
					{
						selStr = "\r\nกรุณากรอก #e#rเหตุผลการลบ#k#n. ใส่ตัวเลขเพื่อเลือกข้อความอัตโนมัติ\r\n\r\nสำหรับเหตุผลอื่น กรุณาพิมพ์เอง\r\n";
						selStr += "#e1 : #nเรียกคืนไอเทมที่ให้ผิด\r\n"
						selStr += "#e2 : #nเรียกคืนไอเทมที่ได้ผิดปกติ\r\n"
						selStr += "#e3 : #nเรียกคืนไอเทมจากการโกง\r\n\r\n";
					}
					else {
						selStr = "\r\nกรุณากรอก #e#rเหตุผลการคัดลอก#k#n. ใส่ตัวเลขเพื่อเลือกข้อความอัตโนมัติ\r\n\r\nสำหรับเหตุผลอื่น กรุณาพิมพ์เอง\r\n";
						selStr += "#e4 : #nเรียกคืนไอเทมจากการโกง\r\n"
						selStr += "#e5 : #nเก็บรักษาไอเทมที่ได้ผิดปกติ\r\n"
					}
					cm.sendGetText(selStr);
				}


				if (S3 == 10) // Notice Send
				{

					//log("notice", "Send Success	"+name+"	"+S5+"	"+txt+"\r\n");

					chr.dropMessage(S5, txt);
					cm.getPlayer().dropMessage(5, "ส่งประกาศถึง " + name + " แล้ว ดำเนินการต่อ");
					cm.dispose();
					cm.openNpc(9010017);
					return;
				}
				if (S3 == 11) {
					count = S5;
					if (count > 32767 || count <= 0) {
						cm.sendOk("ค่าไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง");
						cm.dispose();
						return;
					}
					if (!Packages.server.MapleInventoryManipulator.checkSpace(chr.getClient(), number, count, "")) {
						cm.sendOk("ช่องเก็บของเป้าหมายเต็ม");
						cm.dispose();
						return;
					}
					if (count > 1 && Math.floor(number / 1000000) == 1) {
						for (i = 0; i < count; ++i) {
							chr.gainItem(number, 1);
						}
					} else if (Packages.constants.GameConstants.isPet(number)) {
						Packages.server.MapleInventoryManipulator.addId(chr.getClient(), number, 1, "", Packages.client.inventory.MaplePet.createPet(number, -1), 1, "", false);
					} else {
						chr.gainItem(number, count);
					}
					cm.getPlayer().dropMessage(5, "ส่งไอเท็มให้ " + name + " แล้ว ดำเนินการต่อ");
					cm.dispose();
					cm.openNpc(9010017);
					return;
				}
		}
	}
	else if (St == 6) {
		S6 = S;
		switch (S1) {
			case 0:

				if (chr == null) {
					cm.sendOk("ตัวละครได้ตัดการเชื่อมต่อหรือออฟไลน์ระหว่างการแก้ไข");
					//log("fail", "Lookup Fail	"+name+"	Disconnected during modification\r\n");
					cm.dispose();
					return;
				}

				if (S3 < 10) // 인벤토리
				{
					REASON = function () {
						switch (cm.getText()) {
							case "1": return "เรียกคืนไอเทมที่ให้ผิด";
							case "2": return "เรียกคืนไอเทมที่ได้ผิดปกติ";
							case "3": return "เรียกคืนไอเทมจากการโกง";
							case "4": return "เรียกคืนไอเทมจากการโกง";
							case "5": return "เก็บรักษาไอเทมที่ได้ผิดปกติ";
							default: return cm.getText();
						}
					}

					TYPES = S3 == 0 ? MapleInventoryType.EQUIPPED :
						S3 == 1 ? MapleInventoryType.EQUIP :
							S3 == 2 ? MapleInventoryType.USE :
								S3 == 3 ? MapleInventoryType.SETUP :
									S3 == 4 ? MapleInventoryType.ETC :
										MapleInventoryType.CASH;


					switch (S5) {
						case 20000: // Delete
							reason = S5 == 20000 ? "Item Wrongly Given" : S5 == 20001 ? "Item Retrieval" : "Other";

							if (SET_DEL == 1)
								chr.dropMessage(5, " ");
							chr.dropMessage(5, "[ประกาศ] ไอเท็ม " + cm.getItemName(inv.getItem(S4).getItemId()) + " ถูกลบเนื่องจาก '" + REASON() + "'");
							chr.dropMessage(5, " ");

							//log("success", "Delete	"+chr.getId()+"	"+name+"	"+invType+"	"+inv.getItem(S4).getItemId()+"	1qty	"+REASON()+"\r\n");
							MapleInventoryManipulator.removeFromSlot(chr.getClient(), TYPES, S4, 1, false);
							cm.sendOk("ลบไอเทมเรียบร้อยแล้ว");


							cm.dispose();
							break;

						case 20010: // Copy to my inventory
							//log("success", "Copy	"+chr.getId()+"	"+name+"	"+invType+"	"+inv.getItem(S4).getItemId()+"	1qty	"+REASON()+"\r\n");
							items = inv.getItem(S4).copy();
							MapleInventoryManipulator.addFromDrop(cm.getClient(), items, true);
							cm.sendOk("คัดลอกไอเทมเรียบร้อยแล้ว");

							cm.dispose();
							break;

						default:

							if (SET_DEL == 1)
								chr.dropMessage(5, " ");
							chr.dropMessage(5, "[ประกาศ] ไอเท็ม " + cm.getItemName(inv.getItem(S4).getItemId()) + " x" + S5 + " ชิ้น ถูกลบเนื่องจาก '" + REASON() + "'");
							chr.dropMessage(5, " ");

							//log("success", "Delete	"+chr.getId()+"	"+name+"	"+invType+"	"+inv.getItem(S4).getItemId()+"	"+S5+"qty	"+REASON()+"\r\n");
							MapleInventoryManipulator.removeFromSlot(chr.getClient(), TYPES, S4, S5, false);

							cm.sendOk("ลบไอเทมเรียบร้อยแล้ว");
							cm.dispose();

					}
				}
		}
	}
}

function checkOnOff(i, type) {
	if (i == -1) // off
	{
		switch (type) {
			case "color":
				return "#r";

			case "string":
				return "OFF";
		}
	}
	else {
		switch (type) {
			case "color":
				return "#b";

			case "string":
				return "ON";
		}
	}
}

//Input Content Part
function log(type, i) {
	switch (type) {
		//No additional content needed
		case "access": //On Access
			fn = "1_Access.txt";
			break;

		//Additional content: interaction, fail reason
		case "fail": //On Failure
			fn = "2_Failure.txt";
			break;

		//Additional content: interaction, notice status
		case "success": //On Success
			fn = "3_Success.txt";
			break;

		case "notice": //Notice
			fn = "4_notice.txt";
			break;
	}

	fw = new java.io.FileWriter("SearchingLog/" + fn, true);
	fw.write(new Date() + "	" + cm.getPlayer().getId() + "	" + cm.getPlayer().getName() + "	" + i);
	fw.close();
}

function toString(i) {
	switch (i) {
		case 0:
			return "#Cgray#ไม่ได้ตั้งค่า#k";

		case 10041:
		case 20041:
		case 30041:
		case 40041:
			return "STR%";

		case 10042:
		case 20042:
		case 30042:
		case 40042:
			return "DEX%";

		case 10043:
		case 20043:
		case 30043:
		case 40043:
			return "INT%";

		case 10044:
		case 20044:
		case 30044:
		case 40044:
			return "LUK%";

		case 20086:
		case 30086:
		case 40086:
			return "All Stat%";

		case 10045:
		case 20045:
		case 30045:
		case 40045:
			return "MaxHP%";

		case 10046:
		case 20046:
		case 30046:
		case 40046:
			return "MaxMP%";

		case 10070:
		case 20070:
		case 30070:
		case 40070:
			return "Damage%";

		case 10051:
		case 20051:
		case 30051:
		case 40051:
			return "Attack%";

		case 10052:
		case 20052:
		case 30052:
		case 40052:
			return "Magic Attack%";

		case 30601:
		case 40601:
		case 30602:
		case 40602:
		case 40603:
			return "Boss Damage%";

		case 10291:
		case 20291:
		case 30291:
		case 40291:
		case 40292:
			return "Ignore Defense%";

		case 10055:
		case 20055:
		case 30055:
		case 40055:
			return "Critical Rate%";

		case 40056:
			return "Critical Damage%";

		case 40650:
			return "Meso Obtain%";

		case 40656:
			return "Item Drop Rate%";

		case 20366:
		case 30366:
		case 40366:
			return "Invincibility on hit+";

		case 40556:
			return "Click reduction -1s";

		case 40557:
			return "Click reduction -2s";

		default:
			return i + "";
	}
}

function Comma(i) {
	var reg = /(^[+-]?\d+)(\d{3})/;
	i += '';
	while (reg.test(i))
		i = i.replace(reg, '$1' + ',' + '$2');
	return i;
}

function findPlayerByName(userName) {
	var World = Packages.network.center.Center;
	var ChannelServer = Packages.network.game.GameServer
	if (World.Find.findChannel(userName) >= 0) {
		var player = ChannelServer.getInstance(World.Find.findChannel(userName)).getPlayerStorage().getCharacterByName(userName);
		if (player != null) {
			return player;
		}
	}
	return null;
}