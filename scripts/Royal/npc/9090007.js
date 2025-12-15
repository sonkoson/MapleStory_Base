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
			cm.sendOk("Welcome GM #b#e#h ##k#n. How can I help you?\r\n#b"
				+ "#L0#Lookup Character#l\r\n"
				+ "#L1#Change Settings#l\r\n\r\n");
		} else {
			cm.sendOk("What are you looking at?");
			cm.dispose();
		}
	}

	else if (St == 1) {
		S1 = S;
		switch (S1) {

			//checkOnOff()
			case 1:
				selStr = "Here are the current settings. Select an option to toggle #bON#k/#rOFF#k.\r\n"
				selStr += "#L10#" + checkOnOff(SET_GIV, "color") + "Send notice when giving item #e(" + checkOnOff(SET_GIV, "string") + ")#n\r\n";
				selStr += "#L11#" + checkOnOff(SET_DEL, "color") + "Send notice when deleting item #e(" + checkOnOff(SET_DEL, "string") + ")#n\r\n";
				cm.sendOk(selStr);
				break;

			case 0:
				cm.sendGetText("Welcome GM #b#e#h ##k#n. Please enter the target character's nickname. Only currently online characters are allowed.");
				break;
		}
	}

	else if (St == 2) {
		S2 = S;

		switch (S1) {
			case 1: //설정 변경
				switch (S2) {
					case 10:
						if (SET_GIV == -1)
							cm.getClient().setKeyValue("GM_SETTING_GIV", 1);

						else
							cm.getClient().setKeyValue("GM_SETTING_GIV", -1);

						cm.getPlayer().dropMessage(5, "Send notice when giving item setting has been changed to " + checkOnOff(SET_GIV * -1, "string") + ".");
						break;

					case 11:
						if (SET_DEL == -1) //OFF
							cm.getClient().setKeyValue("GM_SETTING_DEL", 1);

						else
							cm.getClient().setKeyValue("GM_SETTING_DEL", -1);

						cm.getPlayer().dropMessage(5, "Send notice when deleting item setting has been changed to " + checkOnOff(SET_DEL * -1, "string") + ".");
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
					cm.sendOk("Accessed #b#e" + chr.getName() + "#k#n character. How can I help you?#b\r\n\r\n#fnArial##fs11#"
						+ "   - Mesos Held | #fnArial#" + Comma(chr.getMeso()) + " (1 Billion Pouches: " + Comma(chr.itemQuantity(4001716)) + "qty)#fnArial#\r\n"
						+ "   - Aqua Coins Held | " + Comma(chr.itemQuantity(4310237)) + "qty\r\n"
						+ "   - Neo Stones Held | " + Comma(chr.getKeyValue(100711, "point")) + "\r\n"
						+ "   - Neo Gems Held | " + Comma(chr.getKeyValue(100712, "point")) + "\r\n"
						+ "   - Neo Cores Held | " + Comma(chr.getKeyValue(501215, "point")) + "\r\n"
						+ "   - Promotion Points | " + Comma(chr.getHPoint()) + "\r\n"
						+ "   - Donation Points | " + Comma(chr.getDonationPoint()) + "\r\n#fs12##fnArial#"
						//+ "#L16#Give Promotion Points#l #L17#Give Donation Points#l\r\n"
						+ "#L13#Give Neo Stones#l#L14#Give Neo Gems#l#L15#Give Neo Cores#l\r\n\r\n"
						+ "#L0#Check Equipped Inventory#l\r\n"
						+ "#L1#Check Equip Inventory#l\r\n"
						+ "#L2#Check Use Inventory#l\r\n"
						+ "#L3#Check Setup Inventory#l\r\n"
						+ "#L4#Check Etc Inventory#l\r\n"
						+ "#L5#Check Cash Inventory#l\r\n"
						+ "#L6#Check Decoration Inventory#l\r\n\r\n"
						+ "#L7#Check Storage#l\r\n\r\n"
						+ "#L10#Send Message to Character#l\r\n"
						+ "#L11#Give Item to Character#l\r\n"
						+ "#L12#Check Macro#l\r\n");
				}
				else {
					cm.sendOk("Cannot access the character. They might be changing channels or logging off.");
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
					cm.sendOk("The character has disconnected or logged off.");
					//log("fail", "Fail	"+name+"	Disconnected during lookup\r\n");
					cm.dispose();
					return;
				}

				if (S3 == 999) {
					chr.addKV("Hard_Will", "0");
					chr.dropMessage(5, "Count has been restored.");
					cm.dispose();
					return;
				}

				if (S3 == 10) {
					cm.sendGetText("Please enter the message to send to " + chr.getName() + ". It may be cancelled if too long.");
					return;
				}

				if (S3 == 11) {
					cm.sendGetNumber("Please enter the item code to send to " + chr.getName() + ".", 0, 0, 5999999);
					return;
				}


				if (S3 == 12) {
					if (chr == null) {
						cm.sendOk("The character has disconnected or logged off.");
						//log("fail", "Fail	"+name+"	Disconnected during lookup\r\n");
						cm.dispose();
						return;
					}

					cm.dispose();
					cm.openNpcCustom2(chr.getClient(), 2007, "macro");
					cm.getPlayer().dropMessage(5, "Performed macro test on " + name + ".");
					return;
				}

				if (S3 >= 13 && S3 <= 15) {
					cm.sendGetNumber("Please enter the quantity to give to " + chr.getName() + ".", 1, 1, 9999);
					return;
				}

				if (S3 >= 16 && S3 <= 17) {
					cm.sendGetNumber("Please enter the points to give to " + chr.getName() + ".", 1, 1, 999999);
					return;
				}

				if (S3 == 7) {
					//log("access", ""+name+"("+chr.getId()+") Accessing Storage\r\n");

					selStr = "Accessed #b#e" + chr.getName() + "#k#n's #e#rStorage#k#n. Storage items are view-only.\r\n\r\n#b";

					for (w = 0; w < chr.getStorage().getItems().size(); w++)
						selStr += "#i" + chr.getStorage().getItems().get(w).getItemId() + "# #z" + chr.getStorage().getItems().get(w).getItemId() + "# #e" + chr.getStorage().getItems().get(w).getQuantity() + "qty#n\r\n";

					cm.sendOk(selStr);
					cm.dispose();
					return;
				}

				if (S3 < 7) // Inventory
				{
					invType = S3 == 0 ? "Equipped" : S3 == 1 ? "Equip" : S3 == 2 ? "Use" : S3 == 3 ? "Setup" : S3 == 4 ? "Etc" : S3 == 5 ? "Cash" : "Decoration";
					//log("access", ""+name+"("+chr.getId()+") Accessing "+invType+" Inventory\r\n");

					selStr = "Accessed #b#e" + chr.getName() + "#k#n's #e#r" + invType + "#k#n inventory. Which item would you like to check?\r\n#b";
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

							selStr += "#L" + z + "# #i" + inv.getItem(z).getItemId() + ":# #t" + inv.getItem(z).getItemId() + ":# (Qty : " + inv.getItem(z).getQuantity() + ")\r\n";
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
					cm.sendOk("This feature is not implemented.");
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
					cm.sendOk("해당 캐릭터가 접속 등을 종료하여 연결이 끊어졌습니다.");
					//log("fail", "조회 실패	"+name+"	인벤토리 조회 중 접속 종료\r\n");
					cm.dispose();
					return;
				}

				if (S3 < 10) // Inventory
				{
					//log("access", ""+name+"("+chr.getId()+") Accessing "+invType+" Inventory Item "+inv.getItem(S4).getItemId()+"\r\n");

					selStr = "Accessed #b#e" + chr.getName() + "#k#n's #e#r" + invType + "#k#n inventory.\r\n";
					selStr += "Selected Item: #e#b#t" + inv.getItem(S4).getItemId() + ":##k#n.\r\n#b";
					if (S3 < 2) //Equip
					{
						selStr += "#L20010#Copy item to my inventory#l\r\n";

						if (S3 == 1)
							selStr += "#L20000#Delete selected item from inventory#l";

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
						selStr += "\r\n#kCurrently holding #e#r" + inv.getItem(S4).getQuantity() + " qty#k#n in that slot.\r\n#e#rPlease enter quantity to delete.";
						cm.sendGetNumber(selStr, 0, 1, inv.getItem(S4).getQuantity());
					}
				}

				if (S3 == 10) {
					txt = cm.getText();
					if (txt.contains("cm") || txt.contains("Packages")) {
						cm.dispose();
						return;
					}
					selStr = "Do you really want to send the following message to #e" + name + "#n? Select message type.\r\n\r\n#d" + txt + "#r\r\n";
					selStr += "#L5#Pink Notice#l\r\n";
					selStr += "#L6#Blue Notice\r\n";

					if (txt.length < 40)
						selStr += "#L1#Popup Notice (Max 40 chars)#l\r\n";
					cm.sendOk(selStr);
				}

				if (S3 == 11) {
					number = S4;
					ii = Packages.server.MapleItemInformationProvider.getInstance();
					if (!ii.itemExists(number)) {
						cm.sendOk("Item does not exist.");
						cm.dispose();
						return;
					}
					cm.sendGetNumber("Please enter quantity to give to " + chr.getName() + ".", 1, 1, 32767);
					return;
				}
				if (S3 >= 13 && S3 <= 15) {
					number = S4;
					if (S3 == 13) {
						txt = "Given " + number + " Neo Stones to " + name + ".";
						chr.setKeyValue(100711, "point", chr.getKeyValue(100711, "point") + number);
					} else if (S3 == 14) {
						txt = "Given " + number + " Neo Gems to " + name + ".";
						chr.setKeyValue(100712, "point", chr.getKeyValue(100712, "point") + number);
					} else if (S3 == 15) {
						txt = "Given " + number + " Neo Cores to " + name + ".";
						chr.setKeyValue(501215, "point", chr.getKeyValue(501215, "point") + number);
					}
					cm.getPlayer().dropMessage(6, txt);
					cm.dispose();
					return;
				}
				if (S3 >= 16 && S3 <= 17) {
					number = S4;
					if (S3 == 13) {
						txt = "Given " + number + " Promotion Points to " + name + ".";
						chr.setKeyValue(100711, "point", chr.getKeyValue(100711, "point") + number);
					} else if (S3 == 14) {
						txt = "Given " + number + " Donation Points to " + name + ".";
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
					cm.sendOk("The character has disconnected or logged off.");
					//log("fail", "Fail	"+name+"	Disconnected during inventory lookup\r\n");
					cm.dispose();
					return;
				}

				if (S3 < 10) // Inventory
				{
					if (S5 != 20010) // Not Copy
					{
						selStr = "\r\nPlease enter #e#rdeletion reason#k#n. Enter number for auto-fill.\r\n\r\nFor other reasons, please type manually.\r\n";
						selStr += "#e1 : #nRetrieving wrongfully given item\r\n"
						selStr += "#e2 : #nRetrieving item obtained abnormally\r\n"
						selStr += "#e3 : #nRetrieving item from trade fraud\r\n\r\n";
					}
					else {
						selStr = "\r\nPlease enter #e#rcopy reason#k#n. Enter number for auto-fill.\r\n\r\nFor other reasons, please type manually.\r\n";
						selStr += "#e4 : #nRetrieving item from trade fraud\r\n"
						selStr += "#e5 : #nPreserving item obtained abnormally\r\n"
					}
					cm.sendGetText(selStr);
				}


				if (S3 == 10) // Notice Send
				{

					//log("notice", "Send Success	"+name+"	"+S5+"	"+txt+"\r\n");

					chr.dropMessage(S5, txt);
					cm.getPlayer().dropMessage(5, "Notice sent to " + name + ". Please proceed with next task.");
					cm.dispose();
					cm.openNpc(9010017);
					return;
				}
				if (S3 == 11) {
					count = S5;
					if (count > 32767 || count <= 0) {
						cm.sendOk("Invalid value. Please try again.");
						cm.dispose();
						return;
					}
					if (!Packages.server.MapleInventoryManipulator.checkSpace(chr.getClient(), number, count, "")) {
						cm.sendOk("Target inventory is full.");
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
					cm.getPlayer().dropMessage(5, "Sent item to " + name + ". Please proceed with next task.");
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
					cm.sendOk("해당 캐릭터가 접속 등을 종료하여 연결이 끊어졌습니다.");
					//log("fail", "조회 실패	"+name+"	인벤토리 수정 중 접속 종료\r\n");
					cm.dispose();
					return;
				}

				if (S3 < 10) // 인벤토리
				{
					REASON = function () {
						switch (cm.getText()) {
							case "1": return "Retrieving wrongfully given item";
							case "2": return "Retrieving item obtained abnormally";
							case "3": return "Retrieving item from trade fraud";
							case "4": return "Retrieving item from trade fraud";
							case "5": return "Preserving item obtained abnormally";
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
							chr.dropMessage(5, "[Notice] " + cm.getItemName(inv.getItem(S4).getItemId()) + " item has been deleted due to '" + REASON() + "'.");
							chr.dropMessage(5, " ");

							//log("success", "삭제	"+chr.getId()+"	"+name+"	"+invType+"	"+inv.getItem(S4).getItemId()+"	1개	"+REASON()+"\r\n");
							MapleInventoryManipulator.removeFromSlot(chr.getClient(), TYPES, S4, 1, false);
							cm.sendOk("Item deletion complete.");


							cm.dispose();
							break;

						case 20010: // Copy to my inventory
							//log("success", "Copy	"+chr.getId()+"	"+name+"	"+invType+"	"+inv.getItem(S4).getItemId()+"	1qty	"+REASON()+"\r\n");
							items = inv.getItem(S4).copy();
							MapleInventoryManipulator.addFromDrop(cm.getClient(), items, true);
							cm.sendOk("Item copy complete.");

							cm.dispose();
							break;

						default:

							if (SET_DEL == 1)
								chr.dropMessage(5, " ");
							chr.dropMessage(5, "[Notice] " + cm.getItemName(inv.getItem(S4).getItemId()) + " item " + S5 + "qty has been deleted due to '" + REASON() + "'.");
							chr.dropMessage(5, " ");

							//log("success", "Delete	"+chr.getId()+"	"+name+"	"+invType+"	"+inv.getItem(S4).getItemId()+"	"+S5+"qty	"+REASON()+"\r\n");
							MapleInventoryManipulator.removeFromSlot(chr.getClient(), TYPES, S4, S5, false);

							cm.sendOk("Item deletion complete.");
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

//내용 부분 입력
function log(type, i) {
	switch (type) {
		//추가 내용 필요 없음
		case "access": //접속 시
			fn = "1_Access.txt";
			break;

		//추가 내용 : interaction, fail reason
		case "fail": //실패 시
			fn = "2_Failure.txt";
			break;

		//추가 내용 : interaction, 공지 여부
		case "success": //성공 시
			fn = "3_Success.txt";
			break;

		case "notice": //공지사항
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
			return "#Cgray#Not Set#k";

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