importPackage(Packages.provider);
importPackage(Packages.tools);
importPackage(Packages.client.skills);
importPackage(Packages.client);
importPackage(java.lang);
importPackage(java.io);


var status = -1;

function start() {
	status = -1;
	action(1, 0, 0);
}

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
		if (cm.getClient().getUnionLevel() < 500 && !cm.getPlayer().isGM()) {
			cm.sendOkS("อืม~ ดูเหมือนจะยังเร็วไปหน่อยที่จะมาเจอฉันนะ?\r\n#b#eUnion Level ตั้งแต่ 500 ขึ้นไป#k#n, #r#eมีตัวละครตั้งแต่ 3 ตัวขึ้นไป#k#n ถึงจะใช้ Maple Union ได้\r\n\r\n#e※ Union Level คืออะไร?#n\r\nค่ารวมของ #bเลเวลทั้งหมด#k ของตัวละครที่มีเลเวล 60 ขึ้นไปและเปลี่ยนอาชีพครั้งที่ 2 แล้วใน World หากมีตัวละครมากกว่า 40 ตัว จะนับรวมเฉพาะเลเวลของตัวละครที่มีเลเวลสูงสุด 40 ตัวเท่านั้น สำหรับ #rZero#k จะนับเฉพาะตัวที่มีเลเวลสูงสุดเพียง 1 ตัว", 4, 9010106);
			cm.dispose();
			return;
		}

		UNION_GRADE = cm.getPlayer().getKeyValue(1, "UNION_GRADE");

		if (UNION_GRADE == -1) {
			cm.getPlayer().setKeyValue(1, "UNION_GRADE", 0);
		}


		cm.getPlayer().setKeyValue(1, "UNION_GRADE", 5);


		cm.sendSimpleS("ผู้กล้า! อยากลองสร้าง Union ที่แข็งแกร่งกว่าเดิมไหม?\r\n\r\n"
			+ "#e#r[ข้อมูล Maple Union]#b#n\r\n"
			+ "#L0#ตรวจสอบข้อมูล Maple Union ของฉัน#l\r\n"
			+ "#L1#ตรวจสอบ Union Stat ของฉัน#l\r\n"
			+ "#L2#ฟังคำอธิบายเกี่ยวกับ Maple Union#l\r\n"
			+ "\r\n\r\n#e#r[การเติบโตของ Maple Union]#b#n\r\n"
			+ "#L10#อัพเกรดระดับ Maple Union#l\r\n"
			+ "#L11#เพิ่มค่า Union Stat#l\r\n"
			+ "#L12#รับของรางวัล Union ประจำวัน (รับได้วันละ 1 ครั้ง)#l\r\n", 4, 9010106);
	}

	else if (status == 1) {
		sel_00 = selection;
		switch (sel_00) {
			case 0:
				cm.sendOkS("ต้องการดูข้อมูล #eMaple Union#n ของท่านหรือไม่?\r\n\r\n"
					+ "  #e #b-#k ระดับ Union | " + getUnionFullString() + "#b #n(ระดับ " + UNION_GRADE + ")#k\r\n"
					+ "  #e #b-#k Union Level | เลเวล #b#n" + cm.getClient().getUnionLevel() + "\r\n\r\n", 4, 9010106);
				cm.dispose();
				break;

			case 1:
				txt = "สถานะ Union Stat ที่ท่านได้ลงทุนไป\r\n\r\n"
					+ " #r-#k #eUnion Stat Point ที่ใช้ไป : #r#e" + cm.getPlayer().getKeyValue(1, "UNION_STAT_USED") + " AP#k#n\r\n"
					+ " #r-#k #eUnion Stat Point ที่ใช้ได้ : #r" + cm.getPlayer().getKeyValue(1, "UNION_STAT") + " AP#k#n\r\n\r\n";

				SKILL_LIST = 71004000;
				PER_LEVEL = function (skillid) {
					switch (skillid - 71004000) {
						//STR, DEX, INT, LUK
						case 0:
						case 1:
						case 2:
						case 3:
							return 5;
							break;

						//Att(4), Matt(5), Status Resist(9) CritRate(11), BossDmg(12), Stance(13), BuffDur(14), IED(15)
						case 4:
						case 5:
						case 9:
						case 11:
						case 12:
						case 13:
						case 14:
						case 15:
							return 1;
							break;

						//MaxHP(6), MaxMP(7)
						case 6:
						case 7:
							return 250;
							break;

						//CritDmg(8), ExpRate(10)
						case 8:
						case 10:
							return 0.5;
							break;
					}
				}

				for (i = 0; i < 16; i++) {
					txt += " #r-#k #e#q" + (SKILL_LIST + i) + "##k#n : #b#e＋" + cm.getPlayer().getSkillLevel((SKILL_LIST + i)) * PER_LEVEL(SKILL_LIST + i) + "#k#n / " + PER_LEVEL(SKILL_LIST + i) * SkillFactory.getSkill(SKILL_LIST + i).getMaxLevel() + "\r\n";
				}
				cm.sendOkS(txt, 4, 9010106);
		}
	}
}

function getUnionFullString() {
	UNION_GRADE_STRING = ["#fc0xFFCC723D#Novice", "#fc0xFF81929A#Veteran", "#fc0xFFD9B084#Master", "#fc0xFF8741B5#Grand Master"];
	if (UNION_GRADE > -1)
		i = 0;

	if (UNION_GRADE > 5)
		i = 1;

	if (UNION_GRADE > 10)
		i = 2;

	if (UNION_GRADE > 15)
		i = 3;

	switch (Math.floor(UNION_GRADE % 5)) {
		case 0:
			ii = 5; break;
		default:
			ii = UNION_GRADE % 5;
	}

	return "" + UNION_GRADE_STRING[i] + " Union ขั้น " + ii + "";
}