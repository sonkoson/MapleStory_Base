package script.Boss;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import constants.QuestExConstants;
import database.DBConfig;
import objects.fields.fieldset.FieldSet;
import objects.item.MapleInventoryType;
import objects.quest.MapleQuest;
import objects.quest.MapleQuestStatus;
import objects.users.MapleCharacter;
import scripting.newscripting.ScriptEngineNPC;

public class Zakum extends ScriptEngineNPC {

	public void zakum_accept() {
		FieldSet fieldSet = fieldSet("ZakumEnter");
		if (fieldSet == null) {
			self.sayOk("ยังไม่สามารถเข้าร่วม Zakum Raid ได้ในขณะนี้");
			return;
		}
		int v0 = -1;
		if (DBConfig.isGanglim) {
			if (target.getMapId() == 211042401) {
				fieldSet = fieldSet("ChaosZakumEnter");
				v0 = self.askMenu(
						"#e<Zakum: Chaos Mode>#n\r\nZakum ฟื้นคืนชีพแล้ว หากปล่อยไว้ ภูเขาไฟจะระเบิดและทำให้ El Nath กลายเป็นนรก\r\n#b#e(สมาชิกปาร์ตี้จะถูกย้ายพร้อมกัน)#n#k\r\n#r(Chaos Zakum สามารถเคลียร์ได้ #e1 ครั้งต่อสัปดาห์#n และรีเซ็ตทุก #eวันพฤหัสบดี#n)\r\n#b\r\n#L0# ขอเข้าร่วม Chaos Zakum#l");
			} else {
				v0 = self.askMenu(
						"#e<Zakum: Normal Mode>#n\r\nZakum ฟื้นคืนชีพแล้ว หากปล่อยไว้ ภูเขาไฟจะระเบิดและทำให้ El Nath กลายเป็นนรก\r\n#r(Easy, Normal Zakum สามารถเข้าได้รวมกัน #e1 ครั้งต่อวัน#n และรีเซ็ต #eทุกเที่ยงคืน#n)\r\n#b\r\n#L0# ขอเข้าร่วม Zakum (สมาชิกปาร์ตี้จะถูกย้ายพร้อมกัน)#l");
			}
		} else { // 진서버
			if (target.getMapId() == 211042401) {
				fieldSet = fieldSet("ChaosZakumEnter");
				boolean isSingle = false;
				if (getPlayer().getParty() == null || // ปาร์ตี้ 없거나
						getPlayer().getParty() != null && getPlayer().getParty().getMembers().size() == 1) { // ปาร์ตี้원
																												// 1명인경우
					isSingle = true;
				}
				String askString = "#e<Zakum: Chaos Mode>#n\r\n"
						+ "Zakum ฟื้นคืนชีพแล้ว หากปล่อยไว้ ภูเขาไฟจะระเบิดและทำให้ El Nath กลายเป็นนรก\r\n"
						+ "#b#e(สมาชิกปาร์ตี้จะถูกย้ายพร้อมกัน)#n#k\r\n"
						+ "#r(Chaos Zakum สามารถเคลียร์ได้ #e1 ครั้งต่อสัปดาห์#n และรีเซ็ตทุก #eวันพฤหัสบดี#n)\r\n";
				if (!isSingle) { // 멀티โหมด
					askString += "ในโหมด Multi เลือดบอสจะเพิ่มขึ้น 3 เท่า, \r\n"
							+ "ลด Final Damage 50%,\r\n"
							+ "และแชร์ Death Count กับปาร์ตี้";
				}
				askString += "#b\r\n";
				if (isSingle) {
					askString += "#L0# ขอเข้าร่วม Chaos Zakum (Single)#l\r\n";
				} else {
					askString += "#L0# ขอเข้าร่วม Chaos Zakum (Multi)#l\r\n";
				}
				boolean canReset = false;
				int reset = 0;
				if (isSingle) {
					reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
							"ChaosZakumSingle");
					int count = getPlayer().getOneInfoQuestInteger(15166, "mobDeadSingle");
					if (count > 0)
						canReset = true;
				} else {
					reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
							"ChaosZakumMulti");
					int count = getPlayer().getOneInfoQuestInteger(15166, "mobDeadMulti");
					if (count > 0)
						canReset = true;
				}
				if (canReset) {
					askString += " #L1#รีเซ็ตจำนวนครั้งเข้าเล่น เพิ่มได้อีก " + (1 - reset) + " ครั้ง";
				}
				v0 = self.askMenu(askString);
			} else {
				boolean isSingle = false;
				if (getPlayer().getParty() == null || // ปาร์ตี้ 없거나
						getPlayer().getParty() != null && getPlayer().getParty().getMembers().size() == 1) { // ปาร์ตี้원
																												// 1명인경우
					isSingle = true;
				}
				String askString = "#e<Zakum: Normal Mode>#n\r\n"
						+ "Zakum ฟื้นคืนชีพแล้ว หากปล่อยไว้ ภูเขาไฟจะระเบิดและทำให้ El Nath กลายเป็นนรก\r\n"
						+ "#r(Easy, Normal Zakum สามารถเข้าได้รวมกัน #e1 ครั้งต่อวัน#n"
						+ "และรีเซ็ต #eทุกเที่ยงคืน#n)\r\n";
				if (!isSingle) { // 멀티โหมด
					askString += "ในโหมด Multi เลือดบอสจะเพิ่มขึ้น 3 เท่า, \r\n"
							+ "ลด Final Damage 50%,\r\n"
							+ "และแชร์ Death Count กับปาร์ตี้";
				}
				askString += "#b\r\n";
				if (isSingle) {
					askString += "#L0# ขอเข้าร่วม Zakum (Single)#l\r\n";
				} else {
					askString += "#L0# ขอเข้าร่วม Zakum (Multi)#l\r\n";
				}
				boolean canReset = false;
				int reset = 0;
				if (isSingle) {
					reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.DailyQuestResetCount.getQuestID(),
							"NormalZakumSingle");
					int count = getPlayer().getOneInfoQuestInteger(7003, "Single");
					if (count > 0)
						canReset = true;
				} else {
					reset = getPlayer().getOneInfoQuestInteger(QuestExConstants.DailyQuestResetCount.getQuestID(),
							"NormalZakumMulti");
					int count = getPlayer().getOneInfoQuestInteger(7003, "Multi");
					if (count > 1)
						canReset = true;
				}

				if (canReset) {
					if (isSingle) {
						askString += " #L1#รีเซ็ตจำนวนครั้งเข้าเล่น เพิ่มได้อีก " + (2 - reset) + " ครั้ง";
					} else {
						askString += " #L1#รีเซ็ตจำนวนครั้งเข้าเล่น เพิ่มได้อีก " + (1 - reset) + " ครั้ง";
					}
				}
				v0 = self.askMenu(askString);
			}
		}

		if (v0 == 0) { // เข้า시도
			if (getPlayer().getItemQuantity(4001017, false) < 1) {
				if (getPlayer().getInventory(MapleInventoryType.ETC).getNumFreeSlot() < 1) {
					self.say("ดูเหมือนช่องเก็บของ ETC จะไม่พอ กรุณาเคลียร์ช่องว่างให้เพียงพอ");
					return;
				}
				target.exchange(4001017, 1);
				self.say("ดูเหมือนเจ้าจะไม่มี Eye of Fire ซึ่งจำเป็นในการพบ Zakum ข้าจะมอบให้เจ้า ขอให้เจ้าโชคดี");
			}
			int enter = fieldSet.enter(target.getId(), 0);
			if (enter == -1)
				self.say("ไม่สามารถเข้าได้ด้วยเหตุผลบางประการ กรุณาลองใหม่ภายหลัง");
			else if (enter == 1)
				self.say("ต้องมีปาร์ตี้จึงจะท้าทายได้");
			else if (enter == 2)
				self.say("กรุณาให้หัวหน้าปาร์ตี้เป็นผู้ดำเนินการ");
			else if (enter == 3)
				self.say("ตี้ต้องมีสมาชิกอย่างน้อย " + fieldSet.minMember + " คน");
			else if (enter == 4)
				self.say("เลเวลของสมาชิกปาร์ตี้ต้องมีอย่างน้อย " + fieldSet.minLv + " ขึ้นไป");
			else if (enter == 5)
				self.say("สมาชิกปาร์ตี้ทุกคนต้องมารวมตัวกัน");
			else if (enter == 6)
				self.say("มีกองกำลังอื่นกำลังท้าทายอยู่");
			else if (enter == 7) { // 30นาที รอเวลา 발생한경우 (카오스 자쿰만 해당된다.
				self.say("มีสมาชิกปาร์ตี้ที่เพิ่งเข้าเล่นภายใน 30 นาที ไม่สามารถเข้าซ้ำได้ภายใน 30 นาที");
			} else if (enter < -1) {
				MapleCharacter user = getClient().getChannelServer().getPlayerStorage().getCharacterById(enter * -1);
				String name = "";
				if (user != null) {
					name = user.getName();
				}
				if (target.getMapId() == 211042401) {
					self.sayOk(
							"มีสมาชิกปาร์ตี้ที่เคลียร์ <Boss: Zakum> Chaos Mode ไปแล้วในสัปดาห์นี้\r\n#r#e<ประวัติการเคลียร์จะรีเซ็ตทุกวันพฤหัสบดี>");
				} else {
					self.say("สมาชิกปาร์ตี้ #b#e" + name
							+ "#k#n ได้เข้าสู่แท่นบูชา Zakum ไปแล้ววันนี้ ไม่สามารถเข้าซ้ำได้");
				}
			}
		} else if (v0 == 1) { // 진 เข้า횟수 วินาที기화
			if (!DBConfig.isGanglim) {
				int togetherPoint = getPlayer().getTogetherPoint();
				if (togetherPoint < 150) {
					self.sayOk("คะแนนความร่วมมือไม่เพียงพอ คะแนนปัจจุบัน : " + togetherPoint);
					return;
				}
				if (target.getMapId() == 211042401) {
					boolean isSingle = false;
					if (getPlayer().getParty() == null || // ปาร์ตี้ 없거나
							getPlayer().getParty() != null && getPlayer().getParty().getMembers().size() == 1) { // ปาร์ตี้원
																													// 1명인경우
						isSingle = true;
					}
					int resetCount = 0;
					if (isSingle) {
						resetCount = getPlayer().getOneInfoQuestInteger(
								QuestExConstants.WeeklyQuestResetCount.getQuestID(), "ChaosZakumSingle");
						if (resetCount > 0) {
							self.sayOk("สัปดาห์นี้ไม่สามารถรีเซ็ตได้อีกแล้ว");
							return;
						}
						getPlayer().updateOneInfo(15166, "mobDeadSingle", "0");
						getPlayer().updateOneInfo(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
								"ChaosZakumSingle", String.valueOf(resetCount + 1));
					} else {
						resetCount = getPlayer().getOneInfoQuestInteger(
								QuestExConstants.WeeklyQuestResetCount.getQuestID(), "ChaosZakumMulti");
						if (resetCount > 0) {
							self.sayOk("สัปดาห์นี้ไม่สามารถรีเซ็ตได้อีกแล้ว");
							return;
						}
						getPlayer().updateOneInfo(15166, "mobDeadMulti", "0");
						getPlayer().updateOneInfo(QuestExConstants.WeeklyQuestResetCount.getQuestID(),
								"ChaosZakumMulti", String.valueOf(resetCount + 1));
					}
					getPlayer().gainTogetherPoint(-150);
					self.sayOk("รีเซ็ตจำนวนครั้ง Chaos Zakum เรียบร้อยแล้ว");
				} else {
					boolean isSingle = false;
					if (getPlayer().getParty() == null || // ปาร์ตี้ 없거나
							getPlayer().getParty() != null && getPlayer().getParty().getMembers().size() == 1) { // ปาร์ตี้원
																													// 1명인경우
						isSingle = true;
					}
					int resetCount = 0;
					if (isSingle) {
						resetCount = getPlayer().getOneInfoQuestInteger(
								QuestExConstants.DailyQuestResetCount.getQuestID(), "NormalZakumSingle");
						if (resetCount > 1) {
							self.sayOk("วันนี้ไม่สามารถรีเซ็ตได้อีกแล้ว");
							return;
						}
					} else {
						resetCount = getPlayer().getOneInfoQuestInteger(
								QuestExConstants.DailyQuestResetCount.getQuestID(), "NormalZakumMulti");
						if (resetCount > 0) {
							self.sayOk("วันนี้ไม่สามารถรีเซ็ตได้อีกแล้ว");
							return;
						}
					}
					if (isSingle) {
						getPlayer().updateOneInfo(7003, "Single", "");
						getPlayer().updateOneInfo(QuestExConstants.DailyQuestResetCount.getQuestID(),
								"NormalZakumSingle", String.valueOf(resetCount + 1));
					} else {
						getPlayer().updateOneInfo(7003, "Multi", "1"); // 멀티 1회추เข้า으 พื้นฐาน 1회 내림
						getPlayer().updateOneInfo(QuestExConstants.DailyQuestResetCount.getQuestID(),
								"NormalZakumMulti", String.valueOf(resetCount + 1));
					}
					getPlayer().gainTogetherPoint(-150);
					self.sayOk("รีเซ็ตจำนวนครั้ง Normal Zakum เรียบร้อยแล้ว");
				}
			}
		}
	}
}
