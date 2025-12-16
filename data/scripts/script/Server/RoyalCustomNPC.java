package script.Server;

import constants.GameConstants;
import constants.ServerConstants;
import database.DBConfig;
import network.center.Center;
import network.discordbot.DiscordBotHandler;
import network.models.CField;
import network.models.CWvsContext;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.item.Equip;
import objects.item.MapleInventoryType;
import objects.item.MapleItemInformationProvider;
import objects.users.MapleStat;
import objects.users.skills.Skill;
import objects.users.skills.SkillEntry;
import objects.users.skills.VCore;
import objects.utils.Pair;
import objects.utils.Randomizer;
import scripting.NPCScriptManager;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;
import constants.PlayerNPCConstants;
import constants.QuestExConstants;
import objects.users.enchant.GradeRandomOption;

import java.text.NumberFormat;
import java.util.*;

public class RoyalCustomNPC extends ScriptEngineNPC {

    /*
    public static void main(String[] args) {
        File dir = new File("wz/Character.wz/PetEquip");
        File files[] = dir.listFiles();
        int index = 243;
        for (int i = 0; i < files.length; i++) {
            System.out.println("\t" + index + " = {");
            System.out.println("\t\titemID = " + Integer.parseInt(files[i].getName().replace(".img.xml", "")));
            System.out.println("\t\tQuantity = 1");
            System.out.println("\t\tPrice = 1");
            System.out.println("\t\tPosition = 0");
            System.out.println("\t\tReqItem = 0");
            System.out.println("\t\tReqItemQ = 0");
            System.out.println("\t\tCategory = 1");
            System.out.println("\t\tMinLevel = 0");
            System.out.println("\t\tExpiration = 0");
            System.out.println("\t\tPointQuestExID = 0");
            System.out.println("\t\tBuyLimit = 0");
            System.out.println("\t\tWorldBuyLimit = 0");
            System.out.println("\t\tLimitQuestExID = 0");
            System.out.println("\t\tLimitQuestExValue = 0");
            System.out.println("\t}");
            index++;
        }
    }
     */

    String[] grades = new String[] {
            "스카웃", "서전트", "가디언", "마스터", "커맨더", "슈프림"
    };

    ItemEntry[] itemList = new ItemEntry[] {
            new ItemEntry(2450134, new int[]{2, 2, 2, 2, 2, 2}, new int[]{0, 0, 0, 0, 0, 0}, 1, 0),
            new ItemEntry(2434554, new int[]{0, 0, 0, 0, 1, 1}, new int[]{0, 0, 0, 0, 0, 0}, 2, 4),
            new ItemEntry(2434555, new int[]{0, 0, 0, 3, 3, 3}, new int[]{0, 0, 0, 0, 0, 0}, 3, 3),
            new ItemEntry(2434556, new int[]{0, 5, 5, 5, 5, 5}, new int[]{0, 0, 0, 0, 0, 0}, 4, 1),
            new ItemEntry(2434557, new int[]{10, 10, 10, 10, 10, 10}, new int[]{0, 0, 0, 0, 0, 0}, 5, 0),
            new ItemEntry(2434558, new int[]{30, 30, 30, 30, 30, 30}, new int[]{0, 0, 0, 0, 0, 0}, 6, 0),
            new ItemEntry(2028263, new int[]{0, 0, 0, 0, 10, 10}, new int[]{0, 0, 0, 0, 0, 0}, 7, 4),
            new ItemEntry(2028264, new int[]{0, 0, 0, 5, 5, 5}, new int[]{0, 0, 0, 0, 0, 0}, 8, 3),
            new ItemEntry(2028265, new int[]{0, 0, 3, 3, 3, 3}, new int[]{0, 0, 0, 0, 0, 0}, 9, 2),
            new ItemEntry(2437092, new int[]{0, 0, 0, 7, 7, 7}, new int[]{0, 0, 0, 0, 0, 0}, 10, 3),
            new ItemEntry(1713000, new int[]{0, 0, 3, 3, 3, 3}, new int[]{0, 0, 0, 0, 0, 0}, 11, 2),
            new ItemEntry(1713001, new int[]{0, 0, 0, 0, 3, 3}, new int[]{0, 0, 0, 0, 0, 0}, 12, 4),
            new ItemEntry(5680222, new int[]{0, 0, 0, 0, 5, 5}, new int[]{0, 0, 0, 0, 0, 0}, 13, 4),
            new ItemEntry(1112916, new int[]{0, 0, 0, 0, 0, 300}, new int[]{0, 0, 0, 0, 0, 0}, 14, 5),
    };

    public void displayShop() {
        int grade = getPlayer().getOneInfoQuestInteger(100711, "grade");
        String gradeName = "#e<" + grades[grade] + "ระดับ แต่ละ성 상점>#n";
        String v0 = gradeName + "\r\n#eมี คะแนน : " + NumberFormat.getInstance().format(getPlayer().getOneInfoQuestInteger(100778, "point")) + "\r\n\r\n#e[상점 리스트]#n#b\r\n";
        int item = 2630688 + grade;

        if (!DBConfig.isGanglim) {
            if (grade == 0) {
                grade = 1;
            }
        } else {
            grade += 1;
        }
        for (ItemEntry entry : itemList) {
            int itemID = entry.getItemID();
            int index = entry.getIndex();
            int buyCount = getPlayer().getOneInfoQuestInteger(100778, index + "_buy_count");
            int remain = entry.getWorldLimit(grade - 1) - buyCount; // วันนี้ ซื้อ เป็นไปได้ 횟수
            if (entry.getWorldLimit(grade - 1) == 0) {
                remain = -1;
            }
            int gradeLimit = entry.getGradeLimit();
            int price = entry.getPrice(grade - 1);

            int check = getPlayer().getOneInfoQuestInteger(100711, "grade");
            if (gradeLimit > check) {
                // ซื้อ เป็นไปได้ ระดับ 아닐 때
                v0 += "     #i" + itemID + "#  #z" + itemID + "# #e(" + NumberFormat.getInstance().format(price) + " P)#n#l\r\n";
                v0 += "#k#e           - วันนี้ ซื้อ เป็นไปได้ : 0회#b#n #r(ระดับ ไม่พอ)#b\r\n";
            } else {
                if (remain != -1 && remain <= 0) {
                    // ซื้อ เป็นไปได้ 횟수가 없을 때
                    v0 += "     #i" + itemID + "#  #z" + itemID + "# #e(" + NumberFormat.getInstance().format(price) + " P)#n#l\r\n";
                    v0 += "#k#e           - วันนี้ ซื้อ เป็นไปได้ : " + remain + "회#b#n\r\n";
                } else {
                    v0 += "#L" + index + "##i" + itemID + "#  #z" + itemID + "# #e(" + NumberFormat.getInstance().format(price) + " P)#n#l\r\n";
                    if (remain == -1) {
                        v0 += "#k#e           - วันนี้ ซื้อ เป็นไปได้ : จำกัด 없음#b#n\r\n";
                    } else {
                        v0 += "#k#e           - วันนี้ ซื้อ เป็นไปได้ : " + remain + "회#b#n\r\n";
                    }
                }
            }
        }
        int v1 = self.askMenu(v0);
        if (v1 == 0) {
            if (getPlayer().getOneInfoQuestInteger(100778, "0_buy_count") != 0) {
                return;
            }
            if (1 == target.exchange(item, 1)) {
                getPlayer().updateOneInfo(100778, "0_buy_count", "1");
                if (0 == self.askMenu("#b#i" + item + "# #z" + item + "##k 보급해드렸.\r\n\r\n#b#L0#ไอเท็ม รายการ으로 돌아간다.#l")) {
                    displayShop();
                }
            } else {
                self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
            }
        } else {
            if (v1 > itemList.length) {
                return;
            }
            ItemEntry pick = itemList[v1 - 1];
            if (pick == null) {
                return;
            }
            if (pick.getGradeLimit() > grade) {
                return;
            }
            int buyCount = getPlayer().getOneInfoQuestInteger(100778, pick.getIndex() + "_buy_count");
            int remain = pick.getWorldLimit(grade - 1) - buyCount;
            if (pick.getWorldLimit(grade - 1) != 0 && 0 >= remain) {
                return;
            }
            String v2 = "#e<แต่ละ성 상점>#n\r\n#b#i" + pick.getItemID() + "# #z" + pick.getItemID() + "##k";
            if (pick.getWorldLimit(grade - 1) != 0) {
                v2 += "\r\n\r\n#eวันนี้ ซื้อ เป็นไปได้ 횟수 : " + remain + "회\r\nมี คะแนน : " + NumberFormat.getInstance().format(getPlayer().getOneInfoQuestInteger(100778, "point"));
            } else {
                v2 += "\r\n\r\n#eวันนี้ ซื้อ เป็นไปได้ 횟수 : จำกัด 없음\r\nมี คะแนน : " + NumberFormat.getInstance().format(getPlayer().getOneInfoQuestInteger(100778, "point"));
            }
            v2 += "\r\n\r\n#nซื้อ 시 #b#e" + pick.getPrice(grade - 1) + " คะแนน#n#k หัก. 정말 ซื้อต้องการหรือไม่?";
            if (1 == self.askYesNo(v2)) {
                int getPoint = getPlayer().getOneInfoQuestInteger(100778, "point");
                if (getPoint < pick.getPrice(grade - 1)) {
                    self.say("แต่ละ성 คะแนน ไม่พอ ซื้อ할 수 없.");
                    return;
                }
                if (1 == target.exchange(pick.getItemID(), 1)) {
                    getPlayer().updateOneInfo(100778, "point", String.valueOf(getPoint -  pick.getPrice(grade - 1)));
                    getPlayer().updateOneInfo(100712, "sum", String.valueOf(getPlayer().getOneInfoQuestInteger(100778, "point")));

                    // ซื้อ ประมวลผล
                    if (pick.getWorldLimit(grade - 1) != 0) {
                        getPlayer().updateOneInfo(100778, pick.getIndex() + "_buy_count", String.valueOf(buyCount + 1));
                    }
                    if (0 == self.askMenu("#b#i" + pick.getItemID() + "# #z" + pick.getItemID() + "##k ซื้อ เสร็จสมบูรณ์.\r\n\r\n#b#L0#ไอเท็ม รายการ으로 돌아간다.#l")) {
                        displayShop();
                    }
                } else {
                    self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                }
            }
        }
    }

    public void soulWeapon_Copy() {
/*        if (!DBConfig.isGanglim) {
            return;
        }
        initNPC(MapleLifeFactory.getNPC(9000178));
        NumberFormat nf = NumberFormat.getInstance();
        int rc = getPlayer().getRebirthCount();
        int src = getPlayer().getSuperRebirthCount();
        String rGrade = getPlayer().getRebirthGrade();
        String v0 = "#e<환생 및 แต่ละ성 ระบบ>#n#k\r\n\r\n안녕하신가? 나는 #b환생#k 및 #bแต่ละ성#k 담당 있는 #b환생 마법사#k라고 하네. 무엇을 원하는가?\r\n\r\n";
        v0 += "#e누적 환생 횟수 : #b" + nf.format(rc) + "회#k\r\n";
        v0 += "누적 แต่ละ성 횟수 : #b" + nf.format(src) + "회#k\r\n";
        v0 += "แต่ละ성 ระดับ : #b" + rGrade + "ระดับ#k#n\r\n";
        v0 += "#b#L0#환생에 대해 알려สัปดาห์세요.#l\r\n";
        v0 += "#b#L1#แต่ละ성에 대해 알려สัปดาห์세요.#l\r\n";
        v0 += "#b#L2#환생 싶.#l\r\n";
        v0 += "#b#L3#แต่ละ성 싶.#l\r\n";
        v0 += "#b#L4#แต่ละ성 승급을  싶.#l\r\n";
        v0 += "#b#L5#환생 상점을 이용 싶.#l\r\n";
        v0 += "#b#L6#แต่ละ성 상점을 이용 싶.#l\r\n";
        int v = self.askMenu(v0, ScriptMessageFlag.NpcReplacedByNpc);
        switch (v) {
            case 0: //환생อธิบาย
                self.say("#b환생 ระบบ#k #e250 เลเวล#n 달성 시 ดำเนินการ เป็นไปได้, 환생 ดำเนินการ 시 #e235 เลเวล#n 돌아가지 크크크, ทั้งหมด Stat วินาที기화 (올Stat 4, 4, 4, 4) 후 ถัดไป과 같은 공식으로 새롭게 AP 지급된다네.\r\n\r\n#b999 + (환생 횟수 + แต่ละ성 횟수) * 5 ", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("เพิ่ม #b누적 환생 횟수#k 1회 เพิ่ม, 환생 คะแนน เลเวล별로 상이하게 지급 된다네.", ScriptMessageFlag.NpcReplacedByNpc);
                break;
            case 1: //แต่ละ성อธิบาย
                self.say("#bแต่ละ성 ระบบ#k #e275 เลเวล#n 달성 시 ดำเนินการ เป็นไปได้, แต่ละ성 ดำเนินการ 시 #e235 เลเวล#n 돌아가지 크크크, ทั้งหมด Stat วินาที기화 (올Stat 4, 4, 4, 4) 후 ถัดไป과 같은 공식으로 새롭게 AP 지급된다네.\r\n\r\n#b999 + (환생 횟수 + แต่ละ성 횟수) * 5 ", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("เพิ่ม #b누적 แต่ละ성 횟수#k 1회 เพิ่ม, เลเวล 따른 환생 คะแนน แต่ละ성 คะแนน #b5~10 คะแนน#k 사이로 สุ่ม ได้รับ 하게 된다네.\r\n또한, แต่ละ성은 환생과 다르게, แต่ละ성 ระดับ 존재하지.", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("แต่ละ성 ระดับ #b서전트 ระดับ 부터 슈프림 ระดับ#k까지 존재한다네.\r\nแต่ละ성 ระดับ ระดับ 따라 혜택이 존재하지.\r\n\r\nแต่ละ성 ระดับ 따라 이용 เป็นไปได้한 엘리트 แชนแนล 상이, 엘리트 แชนแนล 별 มอนสเตอร์ HP 및 รางวัล이 달라진다네.", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("#e<엘리트 แชนแนล이란?>#n\r\n\r\n10~17 แชนแนล 엘리트 แชนแนล로써, แชนแนล별 ต้องการ แต่ละ성 ระดับ 다르지.\r\n\r\n#b10~11 แชนแนล (엘리트 '가디언 ระดับ')#k : #eแต่ละ성 가디언 ระดับ ต้องการ#n, 출현하는 ทั้งหมด วัน반 มอนสเตอร์ 크기 2배로 เพิ่ม, HP 10배 เพิ่ม, 해당 มอนสเตอร์에게서 얻는 EXP 1.2배 ใช้งาน\r\n#b12~13แชนแนล (엘리트 '마스터 ระดับ')#k #eแต่ละ성 마스터 ระดับ ต้องการ#n, 출현하는 ทั้งหมด วัน반 มอนสเตอร์ 크기 2배로 เพิ่ม, HP 17배 เพิ่ม, 해당 มอนสเตอร์에게서 얻는 EXP 1.4배 ใช้งาน\r\n#b14~15แชนแนล (엘리트 '커맨더 ระดับ') : #k#eแต่ละ성 커맨더 ระดับ ต้องการ#n, 출현하는 ทั้งหมด วัน반 มอนสเตอร์ 크기 2배로 เพิ่ม, HP 20배 เพิ่ม, 해당 มอนสเตอร์에게서 얻는 EXP 1.7배 ใช้งาน\r\n#b16~17แชนแนล (엘리트 '슈프림 ระดับ') : #k#eแต่ละ성 슈프림 ระดับ ต้องการ#n, 출현하는 ทั้งหมด วัน반 มอนสเตอร์ 크기 2배로 เพิ่ม, HP 40배 เพิ่ม, 해당 มอนสเตอร์에게서 얻는 EXP 2배 ใช้งาน", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("เพิ่ม แต่ละ성 ระดับ 따라 แต่ละ성 상점에서 ระดับ별로 ซื้อ เป็นไปได้한 품목이 다르며, ระดับ 높을 수록 더 좋은 혜택을 얻을 수 있다네.", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("#e<แต่ละ성 승급 ต้องการ เงื่อนไข>#n\r\n\r\n#b서전트 ระดับ#k : 누적 แต่ละ성 คะแนน 20점\r\n#b가디언 ระดับ#k : 누적 แต่ละ성 คะแนน 40점\r\n#b마스터ระดับ#k : 누적 แต่ละ성 คะแนน 80점\r\n#b커맨더 ระดับ#k : 누적 แต่ละ성 คะแนน 200점\r\n#b슈프림 ระดับ#k : 누적 แต่ละ성 คะแนน 350점", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("ครั้งสุดท้าย으로 แต่ละ성 ดำเนินการ 시 #b강림 리버스 멤버십#k SP 1개 ได้รับ할 수 있지. 왼쪽 별모양 아이콘을 통해 #b강림 리버스 멤버십#k 혜택을 이용할 수 있다네.", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("누적 แต่ละ성 횟수에 따라 #b강림 리버스 멤버십#k ระดับ 올릴 수 , ระดับ 따라 สูงสุด 투자 เป็นไปได้한 สกิล เลเวล เพิ่ม된다네.\r\n\r\nแต่ละ성을 통해 다양한 สกิล 배우고 ตัวละคร 더욱 성장시켜보게나.", ScriptMessageFlag.NpcReplacedByNpc);
                break;
            case 2: { //환생 싶.
                if (getPlayer().getLevel() < 250) {
                    self.say("250 เลเวล 이상만 환생할 수 있다네.\r\n자네는 ความสามารถ이 ไม่พอ해보이는군.");
                    return;
                }
                v0 = "환생을 할 수 있는 เงื่อนไข에 달성했군. 지금 바로 환생을 해보겠는가?\r\n\r\n#e누적 환생 횟수 : #b" + nf.format(rc) + "회#k#n\r\n\r\n";
                v0 += "#b#L0#환생을 하겠.#l\r\n#L2#วินาทีเดือน 환생을 하겠.\r\n#L1#นิดหน่อย 더 생แต่ละ하겠.#l";
                int vv = self.askMenu(v0, ScriptMessageFlag.NpcReplacedByNpc);
                if (vv == 0) {
                    int ap = 1179;
                    rc = getPlayer().getRebirthCount() + getPlayer().getSuperRebirthCount() + 1;
                    ap = Math.min(32767, ap + (rc * 5));
                    if (1 == self.askYesNo("#b예#k 누르면 환생, #b235 เลเวล#k 돌아가게 된다네.\r\n  #b- 환생 시 AP : " + ap + "#k\r\n\r\n지금 바로 환생하겠나?", ScriptMessageFlag.NpcReplacedByNpc)) {
                        try {
                            getPlayer().doRebirth();
                            short Tap = 1179;
                            int rcc = getPlayer().getRebirthCount() + getPlayer().getSuperRebirthCount();
                            ap = (short) Math.min(Short.MAX_VALUE, Tap + (rcc * 5));
                            getPlayer().setRemainingAp((short) (ap));
                            getPlayer().getStat().setStr((short) 4, getPlayer());
                            getPlayer().getStat().setDex((short) 4, getPlayer());
                            getPlayer().getStat().setInt((short) 4, getPlayer());
                            getPlayer().getStat().setLuk((short) 4, getPlayer());
                            Map<MapleStat, Long> statups = new EnumMap<MapleStat, Long>(MapleStat.class);
                            statups.put(MapleStat.STR, (long) 4);
                            statups.put(MapleStat.DEX, (long) 4);
                            statups.put(MapleStat.INT, (long) 4);
                            statups.put(MapleStat.LUK, (long) 4);
                            statups.put(MapleStat.MAXHP, (long) getPlayer().getStat().getMaxHp());
                            statups.put(MapleStat.MAXMP, (long) getPlayer().getStat().getMaxMp());
                            statups.put(MapleStat.LEVEL, (long) 235);
                            statups.put(MapleStat.AVAILABLEAP, (long) ap);
                            getPlayer().send(CWvsContext.updatePlayerStats(statups, true, getPlayer()));
                        } catch (Exception e) {
                            DiscordBotHandler.requestSendTelegram(e.getMessage());
                            System.out.println("환생오류 발생 메세지가 ส่ง.");
                        }
                    }
                } else if (vv == 2) {
                    int ap = 1179;
                    rc = getPlayer().getRebirthCount() + getPlayer().getSuperRebirthCount() + 2;
                    ap = Math.min(32767, ap + (rc * 5));
                    if (1 == self.askYesNo("#b예#k 누르면 환생, #b235 เลเวล#k 돌아가게 된다네.\r\nวินาทีเดือน 환생은 #r2,000 강림크레딧#k จำเป็น 2회의 환생 เอฟเฟกต์를 얻을 수 있다네.\r\n  #b- 환생 시 AP : " + ap + "#k\r\n\r\n지금 바로 วินาทีเดือน 환생하겠나?", ScriptMessageFlag.NpcReplacedByNpc)) {
                        if (getPlayer().getRealCash() < 2000) {
                            self.say("강림 크레딧이 ไม่พอ해 보이는군. #r2,000 강림크레딧#k จำเป็น하다 말하지 않았소?");
                            return;
                        }
                        try {
                            getPlayer().doSpecialRebirth();
                            short Tap = 1179;
                            int rcc = getPlayer().getRebirthCount() + getPlayer().getSuperRebirthCount();
                            ap = (short) Math.min(Short.MAX_VALUE, Tap + (rcc * 5));
                            getPlayer().setRemainingAp((short) (ap));
                            getPlayer().getStat().setStr((short) 4, getPlayer());
                            getPlayer().getStat().setDex((short) 4, getPlayer());
                            getPlayer().getStat().setInt((short) 4, getPlayer());
                            getPlayer().getStat().setLuk((short) 4, getPlayer());
                            Map<MapleStat, Long> statups = new EnumMap<MapleStat, Long>(MapleStat.class);
                            statups.put(MapleStat.STR, (long) 4);
                            statups.put(MapleStat.DEX, (long) 4);
                            statups.put(MapleStat.INT, (long) 4);
                            statups.put(MapleStat.LUK, (long) 4);
                            statups.put(MapleStat.MAXHP, (long) getPlayer().getStat().getMaxHp());
                            statups.put(MapleStat.MAXMP, (long) getPlayer().getStat().getMaxMp());
                            statups.put(MapleStat.LEVEL, (long) 235);
                            statups.put(MapleStat.AVAILABLEAP, (long) ap);
                            getPlayer().send(CWvsContext.updatePlayerStats(statups, true, getPlayer()));
                            getPlayer().gainRealCash(-2000);
                        } catch (Exception e) {
                            DiscordBotHandler.requestSendTelegram(e.getMessage());
                            System.out.println("환생오류 발생 메세지가 ส่ง.");
                        }
                    }
                }
                break;
            }
            case 3: { //แต่ละ성을 하겠.
                if (getPlayer().getLevel() < 275) {
                    self.say("275 เลเวล 이상만 แต่ละ성할 수 있다네.\r\n자네는 ความสามารถ이 ไม่พอ해보이는군.");
                    return;
                }
                v0 = "แต่ละ성을 할 수 있는 เงื่อนไข에 달성했군. 지금 바로 แต่ละ성을 해보겠는가?\r\n\r\n#e누적 แต่ละ성 횟수 : #b" + nf.format(src) + "회#k#n\r\n\r\n";
                v0 += "#b#L0#แต่ละ성을 하겠.#l\r\n#L1#นิดหน่อย 더 생แต่ละ하겠.#l";
                int vv = self.askMenu(v0, ScriptMessageFlag.NpcReplacedByNpc);
                if (vv == 0) {
                    int ap = 1179;
                    rc = getPlayer().getRebirthCount() + getPlayer().getSuperRebirthCount() + 1;
                    ap = Math.min(32767, ap + (rc * 5));
                    if (1 == self.askYesNo("#b예#k 누르면 แต่ละ성, #b235 เลเวล#k 돌아가게 된다네.\r\n  #b- 환생 시 AP : " + ap + "#k\r\n\r\n지금 바로 แต่ละ성하겠나?", ScriptMessageFlag.NpcReplacedByNpc)) {
                        try {
                            getPlayer().doSuperRebirth();
                            short Tap = 1179;
                            int rcc = getPlayer().getRebirthCount() + getPlayer().getSuperRebirthCount();
                            ap = (short) Math.min(Short.MAX_VALUE, Tap + (rcc * 5));
                            getPlayer().setRemainingAp((short) (ap));
                            getPlayer().getStat().setStr((short) 4, getPlayer());
                            getPlayer().getStat().setDex((short) 4, getPlayer());
                            getPlayer().getStat().setInt((short) 4, getPlayer());
                            getPlayer().getStat().setLuk((short) 4, getPlayer());
                            Map<MapleStat, Long> statups = new EnumMap<MapleStat, Long>(MapleStat.class);
                            statups.put(MapleStat.STR, (long) 4);
                            statups.put(MapleStat.DEX, (long) 4);
                            statups.put(MapleStat.INT, (long) 4);
                            statups.put(MapleStat.LUK, (long) 4);
                            statups.put(MapleStat.MAXHP, (long) getPlayer().getStat().getMaxHp());
                            statups.put(MapleStat.MAXMP, (long) getPlayer().getStat().getMaxMp());
                            statups.put(MapleStat.LEVEL, (long) 235);
                            statups.put(MapleStat.AVAILABLEAP, (long) ap);
                            getPlayer().send(CWvsContext.updatePlayerStats(statups, true, getPlayer()));
                        } catch (Exception e) {
                            DiscordBotHandler.requestSendTelegram(e.getMessage());
                            System.out.println("แต่ละ성오류 발생 메세지가 ส่ง.");
                        }
                    }
                }
                break;
            }
            case 4: { //แต่ละ성 승급
                int total = getPlayer().getOneInfoQuestInteger(100712, "total");

                v0 = "ปัจจุบัน #b#h0##k 자네의 แต่ละ성 ข้อมูล는 아래와 같다네.\r\n\r\n";
                v0 += "#e누적 환생 횟수 : #b" + nf.format(rc) + "회#k\r\n";
                v0 += "누적 แต่ละ성 횟수 : #b" + nf.format(src) + "회#k\r\n";
                v0 += "누적 แต่ละ성 คะแนน : #b" + nf.format(total) + "#k\r\n";
                v0 += "แต่ละ성 ระดับ : #b" + rGrade + "ระดับ#k#n\r\n\r\n";
                v0 += "#b#L0#แต่ละ성 승급을 ดำเนินการ 싶.#l\r\n";
                v0 += "#L1#그만 สนทนา하겠.#l\r\n";
                int vv = self.askMenu(v0, ScriptMessageFlag.NpcReplacedByNpc);
                if (vv == 0) {
                    int grade = getPlayer().getOneInfoQuestInteger(100711, "grade");

                    String[] gradeName = {"스카웃", "서전트", "가디언", "마스터", "커맨더", "슈프림"};
                    v0 = "#e<แต่ละ성 승급 สมัคร>#n\r\n\r\n#eปัจจุบัน ระดับ : #b" + gradeName[grade] + "#k#n\r\n";
                    v0 += "#eถัดไป ระดับ : #b" + gradeName[grade + 1] + "#k#n\r\n\r\nถัดไป ระดับ 승급하겠나?";
                    if (grade == 5) {
                        v0 = "#e<แต่ละ성 승급 สมัคร>#n\r\n\r\n#eปัจจุบัน ระดับ : #b" + gradeName[grade] + "#k#n\r\n";
                        v0 += "\r\n자네는 더 이상 승급할 수 없다네.";
                        self.say(v0, ScriptMessageFlag.NpcReplacedByNpc);
                        return;
                    }
                    if (1 == self.askYesNo(v0, ScriptMessageFlag.NpcReplacedByNpc)) {
                        int need = 0;
                        if (grade == 0) {
                            need = 20;
                        } else if (grade == 1) {
                            need = 40;
                        } else if (grade == 2) {
                            need = 80;
                        } else if (grade == 3) {
                            need = 200;
                        } else if (grade == 4) {
                            need = 350;
                        }
                        if (total < need) {
                            self.say("승급 เงื่อนไข에 달성하지 못 승급이 불เป็นไปได้ 하다네.\r\n\r\nต้องการ คะแนน : #b" + need + "#k", ScriptMessageFlag.NpcReplacedByNpc);
                            return;
                        }
                        getPlayer().updateOneInfo(100711, "grade", (grade + 1) + "");
                        self.say("승급이 เสร็จสมบูรณ์ #e" + gradeName[(grade + 1)] + " ระดับ#n 되었다네.", ScriptMessageFlag.NpcReplacedByNpc);
                    }
                }
                break;
            }
            case 5: { //환생상점
                openShop(9062194);
                break;
            }
            case 6: { //แต่ละ성상점
                displayShop();
                //openShop(9062195);
                break;
            }
        }*/
    }


    public void Royal_Shop() {
        if (!DBConfig.isGanglim) {
            return;
        }
        initNPC(MapleLifeFactory.getNPC(9090000));
        int v = self.askMenu("     #fUI/UIWindow2.img/Script/Title/2#\r\n\r\n#b#h0##k 안녕하다냥\r\n\r\n나는 #r" + ServerConstants.serverName + "서버#k 광장 상점을 맡고있는 #b묘묘#k다냥\r\nจำเป็น한것이 있냥?\r\n#b" +
                "#L0#อุปกรณ์ 상점을 이용 싶어요.#l\r\n" +
                "#L1#ใช้ 상점을 이용 싶어요.#l\r\n" +
                "#L2#스펙업 상점을 이용 싶어요.#l\r\n" +
                "#L3#설치 상점을 이용 싶어요.#l\r\n" +
                "#L4#แคช(Meso) 상점을 이용 싶어요.#l\r\n" +
                "#L5#บอส คะแนน 상점을 이용 싶어요.#l\r\n" +
                "#L6#휴식 คะแนน 상점을 이용 싶어요.#l", ScriptMessageFlag.NpcReplacedByNpc);
        switch (v) {
            case 0: { //อุปกรณ์상점
                int vv = self.askMenu("     #fUI/UIWindow2.img/Script/Title/2#\r\n\r\n이곳은 #rอุปกรณ์ 상점#k이다냥\r\n\r\nจำเป็น한것이 무엇인지 골라라냥\r\n#b" +
                        "#L0#모루 상점을 이용 싶어요.#r(지속해서 เพิ่ม예정)#b#l\r\n" +
                        "#L1#반지 상점을 이용 싶어요.#l\r\n" +
                        "#L2#보조อาวุธ 상점을 이용 싶어요.#l\r\n" +
                        "#L3#엠블렘 상점을 이용 싶어요.#l\r\n" +
                        "#L4#앱솔랩스 상점을 이용 싶어요.#r(앱솔랩스 코인)#b#l\r\n" +
                        "#L5#앱솔랩스 상점을 이용 싶어요.#r(스티그마 코인)#b#l\r\n" +
                        "#L6#아케인 상점을 이용 싶어요.#r(판타즈마 코인)#b#l\r\n", ScriptMessageFlag.NpcReplacedByNpc);
                switch (vv) {
                    case 0: //모루상점
                        openShop(777777);
                        break;
                    case 1:
                        openShop(777778);
                        break;
                    case 2:
                        openShop(777779);
                        break;
                    case 3:
                        openShop(777780);
                        break;
                    case 4:
                        openShop(777781); //앱솔랩스 코인
                        break;
                    case 5:
                        openShop(777782); //스티그마 코인
                        break;
                    case 6:
                        openShop(777783); //판타즈마 코인
                        break;
                }
                break;
            }
            case 1: { //ใช้상점
                int vv = self.askMenu("     #fUI/UIWindow2.img/Script/Title/2#\r\n\r\n이곳은 #rใช้ 상점#k이다냥\r\n\r\nจำเป็น한것이 무엇인지 골라라냥\r\n#b" +
                        "#L0#물약을 ซื้อ 싶어요.#l\r\n" +
                        "#L1#도핑물약을 ซื้อ 싶어요.#l\r\n" +
                        "#L2#화살, 표창, 불릿을 ซื้อ 싶어요.#l", ScriptMessageFlag.NpcReplacedByNpc);
                switch (vv) {
                    case 0: //물약ซื้อ
                        openShop(778777);
                        break;
                    case 1: //도핑물약ซื้อ
                        openShop(778778);
                        break;
                    case 2: //화살 표창 불릿 ซื้อ
                        openShop(778779);
                        break;
                }
                break;
            }
            case 2: { //스펙업상점
                int vv = self.askMenu("     #fUI/UIWindow2.img/Script/Title/2#\r\n\r\n이곳은 #r스펙업 상점#k이다냥\r\n\r\nจำเป็น한것이 무엇인지 골라라냥\r\n#b" +
                        "#L0#큐브를 ซื้อ 싶어요.#l\r\n" +
                        "#L1#สัปดาห์문서를 ซื้อ 싶어요.#l\r\n" +
                        "#L2#환생의 불꽃을 ซื้อ 싶어요.#l\r\n" +
                        "#L3#서큘레이터를 ซื้อ 싶어요.#l\r\n" +
                        "#L4#소울상점을 이용 싶어요.#l\r\n", ScriptMessageFlag.NpcReplacedByNpc);
                switch (vv) {
                    case 0: //큐브ซื้อ
                        openShop(779777);
                        break;
                    case 1: //สัปดาห์문서 ซื้อ
                        openShop(779778);
                        break;
                    case 2: //환생의 불꽃 ซื้อ
                        openShop(779779);
                        break;
                    case 3: //서큘레이터 ซื้อ
                        openShop(779780);
                        break;
                    case 4: //소울
                        openShop(779781);
                        break;
                }
                break;
            }
            case 3: { //설치상점
                int vv = self.askMenu("     #fUI/UIWindow2.img/Script/Title/2#\r\n\r\n이곳은 #r설치 상점#k이다냥\r\n\r\nจำเป็น한것이 무엇인지 골라라냥\r\n#b" +
                        "#L0#의자를 ซื้อ 싶어요.#l\r\n" +
                        "#L1#라이딩을 ซื้อ 싶어요.#l\r\n" +
                        "#L2#Damage 스킨을 ซื้อ 싶어요.#l\r\n" +
                        "#L3#칭호를 ซื้อ 싶어요.#l", ScriptMessageFlag.NpcReplacedByNpc);
                switch (vv) {
                    case 0: {
                        openShop(780777);
                        break;
                    }
                    case 1: {
                        openShop(780778);
                        break;
                    }
                    case 2: {
                        openShop(780779);
                        break;
                    }
                    case 3: {
                        openShop(780780);
                        break;
                    }
                }
                break;
            }
            case 4: { //แคช상점
                int vv = self.askMenu("     #fUI/UIWindow2.img/Script/Title/2#\r\n\r\n이곳은 #rแคช(Meso) 상점#k이다냥\r\n\r\nจำเป็น한것이 무엇인지 골라라냥\r\n#b" +
                        "#L0#펫을 ซื้อ 싶어요.#l\r\n" +
                        "#L1#펫อุปกรณ์(อุปกรณ์, สกิล) ซื้อ 싶어요.#l\r\n" +
                        "#L2#แคช표창을 ซื้อ 싶어요.#l\r\n" +
                        "#L3#확성기를 ซื้อ 싶어요.#l\r\n" +
                        "#L4#카르타의 진สัปดาห์를 ซื้อ 싶어요(아델 등 귀제거).#l\r\n" +
                        "#L5#메이플 คะแนน ซื้อ 싶어요.#l\r\n", ScriptMessageFlag.NpcReplacedByNpc);
                switch (vv) {
                    case 0: {
                        openShop(781777);
                        break;
                    }
                    case 1: {
                        openShop(781778);
                        break;
                    }
                    case 2: {
                        openShop(781779);
                        break;
                    }
                    case 3: {
                        openShop(781781);
                        break;
                    }
                    case 4: {
                        openShop(781782);
                        break;
                    }
                    case 5: {
                        openShop(781780);
                        break;
                    }
                }
                break;
            }
            case 5: { //บอสคะแนน 상점
                int vv = self.askMenu("     #fUI/UIWindow2.img/Script/Title/2#\r\n\r\n이곳은 #rบอส คะแนน 상점#k이다냥\r\n\r\nจำเป็น한것이 무엇인지 골라라냥\r\n#b" +
                        "#L0#บอส คะแนน 상점을 이용 싶어요.#l\r\n" +
                        "#L1#บอส คะแนน 무엇인가요?#l", ScriptMessageFlag.NpcReplacedByNpc);
                switch (vv) {
                    case 0:
                        openShop(9073008);
                        break;
                    case 1:
                        self.say("บอส คะแนน란 บอส 잡으면 얻을 수 있는 คะแนน다 냥");
                        self.say("บอส 강할수록 คะแนน มาก얻고 약 คะแนน 아예 벌 수 없는 บอส 있다 냥");
                        self.say("บอส별로 벌 수 있는 คะแนน 이렇다 냥\r\n\r\n카오스 자쿰 : 10~15 สุ่ม\r\n" +
                                "카오스 피에르 : 10~15 สุ่ม\r\n" +
                                "카오스 반반 : 10~15 สุ่ม\r\n" +
                                "카오스 블러디퀸 : 10~15 สุ่ม\r\n" +
                                "하드 매그너스 : 20~25 สุ่ม\r\n" +
                                "카오스 벨룸 : 20~25 สุ่ม\r\n" +
                                "카오스 파풀라투스 : 30~35 สุ่ม\r\n" +
                                "노말 스우 : 30~35 สุ่ม\r\n" +
                                "노말 데미안 : 40~45 สุ่ม\r\n" +
                                "노말 루시드 : 40~45 สุ่ม\r\n" +
                                "노말 윌 : 40~45 สุ่ม\r\n" +
                                "노말 더스크 : 40~45 สุ่ม\r\n" +
                                "노말 듄켈 : 40~45 สุ่ม\r\n" +
                                "하드 데미안 : 60~65 สุ่ม\r\n" +
                                "하드 스우 : 60~65 สุ่ม\r\n" +
                                "하드 루시드 : 60~65 สุ่ม\r\n" +
                                "하드 윌 : 60~65 สุ่ม\r\n" +
                                "카오스 더스크 : 70~75 สุ่ม\r\n" +
                                "하드 듄켈 : 70~75 สุ่ม\r\n" +
                                "진 힐라 : 70~75 สุ่ม\r\n" +
                                "검은 마법사 : 200~250 สุ่ม", ScriptMessageFlag.NpcReplacedByNpc);
                        break;
                }
                break;
            }
            case 6: { //휴식คะแนน 상점
                int currentDP = getPlayer().getDancePoint();
                int[][] items = new int[][]{
                        {2350004, 100},
                        {2350005, 100},
                        {2350007, 100},
                        {2435122, 100},
                        {2437092, 300},
                        {2434921, 300},
                        {2633597, 700},
                        {1672079, 700},
                        {1182200, 700},
                        {2439178, 1500},
                        {1190302, 2000},

                };
                String shopItems = "#b";
                for (int i = 0; i < items.length; i++) {
                    if (items[i][0] == 2439178) {
                        shopItems += "\r\n #L" + i + "##i" + items[i][0] + "# #z" + items[i][0] + "# (" + items[i][1] + "p) #r연금술 ได้รับ상자#b#l";
                    } else {
                        shopItems += "\r\n #L" + i + "##i" + items[i][0] + "# #z" + items[i][0] + "# (" + items[i][1] + "p)#l";
                    }
                }
                int vv = self.askMenu("     #fUI/UIWindow2.img/Script/Title/2#\r\n\r\n이곳은 #r휴식 คะแนน 상점#k이다냥\r\n\r\n#e [ปัจจุบัน 나의 휴식 คะแนน : " + currentDP + "p]#n\r\n" +
                        shopItems, ScriptMessageFlag.NpcReplacedByNpc);
                if (vv >= 0) {
                    if (items[vv][1] > currentDP) {
                        self.sayOk("휴식 คะแนน ไม่พอ한게 아닌지 ยืนยัน해봐라 냥", ScriptMessageFlag.NpcReplacedByNpc);
                        return;
                    }
                    if (1 == self.askYesNo("정말로 #b#i" + items[vv][0] + "# #z" + items[vv][0] + "##k " + items[vv][1] + "point 구입할거 냥?", ScriptMessageFlag.NpcReplacedByNpc)) {
                        if (target.exchange(items[vv][0], 1) > 0) {
                            getPlayer().setDancePoint(currentDP - items[vv][1]);
                            self.sayOk("แลกเปลี่ยน เสร็จสมบูรณ์됐다 냥 ถัดไป에 또와라 냥");
                        } else {
                            self.sayOk("여유ช่อง 있는지 ยืนยัน해봐라 냥", ScriptMessageFlag.NpcReplacedByNpc);
                        }
                    } else {
                        self.sayOk("신중히 생แต่ละ해보고 다시와라 냥", ScriptMessageFlag.NpcReplacedByNpc);
                    }
                }
                break;
            }
        }
    }

    public void Royal_Pet_Shop() {
        initNPC(MapleLifeFactory.getNPC(9090000));
        int vv = self.askMenu("     #fUI/UIWindow2.img/Script/Title/2#\r\n\r\n이곳은 #애완 상점#k이다냥\r\n\r\nจำเป็น한것이 무엇인지 골라라냥\r\n#b" +
                "#L0#펫을 ซื้อ 싶어요.#l\r\n" +
                "#L1#펫อุปกรณ์(อุปกรณ์, สกิล) ซื้อ 싶어요.#l\r\n" +
                "#L2#แคช표창을 ซื้อ 싶어요.#l\r\n" +
                "#L3#메이플 คะแนน ซื้อ 싶어요.#l\r\n", ScriptMessageFlag.NpcReplacedByNpc);
        switch (vv) {
            case 0: {
                openShop(781777);
                break;
            }
            case 1: {
                openShop(781778);
                break;
            }
            case 2: {
                openShop(781779);
                break;
            }
            case 3: {
                openShop(781780);
                break;
            }
        }
    }

    public void Royal_Gacha_Shop() {
        initNPC(MapleLifeFactory.getNPC(9090000));
        int vv = self.askMenu("     #fUI/UIWindow2.img/Script/Title/2#\r\n\r\n이곳은 #r가챠 상점#k이다냥 피넛 머신이나 머메이드 거울 부화기 등 다양한 ไอเท็ม เตรียม있다 냥\r\n\r\nจำเป็น한것이 무엇인지 골라라냥\r\n#b" +
                "#L0#의자를 ซื้อ 싶어요.#l\r\n" +
                "#L1#라이딩을 ซื้อ 싶어요.#l\r\n" +
                "#L2#Damage 스킨을 ซื้อ 싶어요.#l\r\n" +
                "#L3#칭호를 ซื้อ 싶어요.#l", ScriptMessageFlag.NpcReplacedByNpc);
        switch (vv) {
            case 0: {
                openShop(780777);
                break;
            }
            case 1: {
                openShop(780778);
                break;
            }
            case 2: {
                openShop(780779);
                break;
            }
            case 3: {
                openShop(780780);
                break;
            }
        }
    }

    static List<Integer> itemArray = new ArrayList<>();
    int[] banneditem = {3018466, 3018209, 3016200, 3016206, 3018079, 3018080, 3018515, 3015761, 3015687, 3018467, 3018465, 3018634, 3018116, 3018117, 3018118, 3018119, 3018120, 3018121};

    public void Royal_Peanut() {
        int needItem = -1;
        int reward = -1;
        int[] arr;
        switch (itemID) {
            case 5060002: //프리미엄 부화기
            case 5060005: //프리미엄 부화기 (라이딩)
                arr = new int[]{2430050, 2430056, 2430072, 2430243, 2430506, 2430518, 2430566, 2430610, 2430633, 2430660, 2430726, 2430939, 2430727, 2430794, 2430968, 2431044, 2431135, 2431362, 2431364, 2431366, 2431367, 2431368, 2431369, 2431370, 2431371, 2431474, 2431490, 2431491, 2431492, 2431494, 2431496, 2431497, 2431498, 2431499, 2431500, 2431501, 2431502, 2431503, 2431505, 2431506, 2431527, 2431530, 2431700, 2431745, 2431760, 2431764, 2431765, 2431797, 2431799, 2431898, 2431914, 2431915, 2432006, 2432008, 2432015, 2432029, 2432030, 2432078, 2432085, 2432135, 2432149, 2432151, 2432216, 2432218, 2432291, 2432293, 2432295, 2432309, 2432347, 2432348, 2432349, 2432350, 2432351, 2432359, 2432361, 2432418, 2432433, 2432449, 2432498, 2432527, 2432528, 2432582, 2432645, 2432653, 2432724, 2432733, 2432994, 2432997, 2432998, 2432999, 2433000, 2433001, 2433002, 2433003, 2433006, 2433272, 2433274, 2433276, 2433345, 2433347, 2433349, 2433405, 2433406, 2433460, 2433603, 2433735, 2433736, 2433809, 2433811, 2433946, 2433948, 2434025, 2434077, 2434079, 2434275, 2434277, 2434377, 2434379, 2434515, 2434517, 2434525, 2434527, 2434582, 2434649, 2434737, 2434761, 2435089, 2435091, 2435112, 2435113, 2435114, 2435203, 2435205, 2435298, 2435442, 2435476, 2435517, 2435720, 2435722, 2435842, 2435843, 2435844, 2435845, 2435965, 2435967, 2435986, 2436030, 2436031, 2436183, 2436185, 2436292, 2436294, 2436405, 2436407, 2436524, 2436525, 2436526, 2436597, 2436599, 2436610, 2436648, 2436715, 2436716, 2436728, 2436730, 2436778, 2436780, 2436837, 2436839, 2436957, 2437026, 2437040, 2437042, 2437123, 2437125, 2437259, 2437261, 2437497, 2437625, 2437737, 2437738, 2437794, 2437852, 2438136, 2438137, 2438138, 2438139, 2438340, 2438373, 2438408, 2438409, 2438493, 2438494, 2438657, 2438743, 2438882, 2438886, 2439266, 2439278, 2439295, 2439406, 2439443, 2439666, 2439667, 2630116, 2630240, 2630261, 2630279, 2630386, 2630387, 2630448, 2630570, 2631460, 2631561, 2631563, 2631710, 2432328, 2437623, 2437719, 2437721, 2437923, 2438486, 2438640, 2438715, 2439034, 2439127, 2439331, 2439486, 2439675, 2439694, 2439808, 2439911, 2439913, 2439915, 2630451, 2630476, 2630488, 2630563, 2630573, 2630575, 2630763, 2630764, 2630765, 2630913, 2630917, 2630918, 2630919, 2630971, 2631136, 2631140, 2631413, 2631448, 2631518, 2631520, 2435296, 2437809, 2438380, 2438382, 2438488, 2438638, 2438745, 2439036, 2439144, 2439329, 2439484, 2439677, 2439909, 2439933, 2631191, 2631914, 2631800, 2631890, 2632275, 2632283, 2633075, 2633309, 2632352, 2632353, 2632887, 2632885, 2632361, 2632445, 2632713, 2632834, 2632913, 2633061, 2633075, 2633310, 2633601, 2633602};
                reward = arr[Randomizer.rand(0, arr.length - 1)];
                needItem = 4170000;
                break;
            case 5060003: //피넛머신 (의자)
                if (itemArray.size() == 0) {
                    Iterator it = MapleItemInformationProvider.getInstance().getAllItems().iterator();
                    boolean banned = false;
                    while (it.hasNext()) {
                        Pair<Integer, String> itemPair = (Pair<Integer, String>) it.next();
                        banned = false;
                        for (int i = 0; i < banneditem.length; i++) {
                            if (itemPair.getLeft() == banneditem[i]) {
                                banned = true;
                            }
                        }
                        if (itemPair.getLeft() >= 3010000 && itemPair.getLeft() <= 3018720 && (itemPair.getRight() != null && (!itemPair.getRight().contains("명예의")) && itemPair.getRight() != "") && banned == false) {
                            itemArray.add(itemPair.getLeft());
                        }
                    }
                    reward = itemArray.get(Randomizer.rand(0, itemArray.size() - 1));
                } else {
                    reward = itemArray.get(Randomizer.rand(0, itemArray.size() - 1));
                }
                needItem = 4170023;
                break;
            case 5060004: //전자레인지 (미정)
                needItem = 4170024; //아이스큐브
                break;
            case 5060012: //머메이드 미러 (Damage스킨)
                arr = new int[]{2431966, 2431965, 2431967, 2432131, 2438163, 2438164, 2438165, 2438166, 2438167, 2438168, 2438169, 2438170, 2438171, 2438172, 2438173, 2438174, 2438175, 2438176, 2438177, 2438179, 2438178, 2438180, 2438181, 2438182, 2438184, 2438185, 2438186, 2438187, 2438188, 2438189, 2438190, 2438191, 2438192, 2438193, 2438195, 2438196, 2438197, 2438201, 2438198, 2438199, 2438200, 2438202, 2438203, 2438204, 2438205, 2438206, 2438207, 2438208, 2438209, 2438210, 2438211, 2438212, 2438213, 2438214, 2438215, 2438216, 2438217, 2438218, 2438219, 2438220, 2438221, 2438222, 2438223, 2438224, 2438225, 2438226, 2438227, 2438228, 2438229, 2438230, 2438231, 2438232, 2438233, 2438234, 2438235, 2438236, 2438237, 2438238, 2438239, 2438240, 2438241, 2438242, 2438243, 2438244, 2438245, 2438246, 2438247, 2438248, 2438249, 2438250, 2438251, 2438252, 2438253, 2438254, 2438255, 2438256, 2438257, 2438258, 2438259, 2438260, 2438261, 2438262, 2438263, 2438264, 2438265, 2438266, 2438267, 2438268, 2438269, 2438270, 2438271, 2438272, 2438273, 2438274, 2438275, 2438276, 2438277, 2438278, 2438279, 2438280, 2438281, 2438282, 2438283, 2438284, 2438285, 2438286, 2438287, 2438288, 2438289, 2438290, 2438291, 2438292, 2438293, 2438294, 2438295, 2438296, 2438297, 2438298, 2438299, 2438300, 2438301, 2438302, 2438303, 2438304, 2438305, 2438306, 2438307, 2438308, 2438309, 2438310, 2438311, 2438312, 2438313, 2438314, 2438315, 2438353, 2438378, 2438379, 2438413, 2438415, 2438417, 2438419, 2438485, 2438492, 2438530, 2438637, 2438672, 2438713, 2438871, 2438881, 2438885, 2439298, 2439336, 2439337, 2439338, 2439381, 2439393, 2439395, 2439408, 2439572, 2439617, 2439652, 2439684, 2439686, 2439769, 2439925, 2439927, 2630137, 2630222, 2630178, 2630214, 2630235, 2630224, 2630262, 2630264, 2630266, 2439397, 2439399, 2630268, 2439682, 2439401, 2438149, 2438151, 2630477, 2630479, 2630481, 2630483, 2630485, 2630552, 2630554, 2630558, 2630560, 2630652, 2630743, 2630745, 2630747, 2630749, 2630751, 2630766, 2630804, 2631094, 2631135, 2631189, 2631183, 2631401, 2631471, 2631492, 2631610, 2630380, 2438147, 2631893, 2631885, 2631815, 2631798, 2632124, 2632281, 2632288, 2632350, 2632430, 2632544, 2632498, 2632712, 2632816, 5680862, 2632281, 2632888, 2632976, 2633045, 2633047, 2633074, 2633218, 2633220, 2633306, 2633313, 2633573, 2633599};
                reward = arr[Randomizer.rand(0, arr.length - 1)];
                needItem = 4170031;
                break;
            case 5060018: //무한의 서 (칭호)
                arr = new int[]{3700000, 3700001, 3700005, 3700006, 3700007, 3700008, 3700009, 3700011, 3700012, 3700013, 3700014, 3700016, 3700468, 3700017, 3700018, 3700019, 3700020, 3700039, 3700040, 3700041, 3700042, 3700043, 3700044, 3700045, 3700046, 3700047, 3700048, 3700049, 3700057, 3700074, 3700075, 3700076, 3700077, 3700078, 3700079, 3700080, 3700081, 3700082, 3700084, 3700098, 3700099, 3700100, 3700101, 3700103, 3700106, 3700118, 3700119, 3700120, 3700135, 3700136, 3700141, 3700142, 3700143, 3700144, 3700147, 3700148, 3700149, 3700150, 3700156, 3700157, 3700158, 3700159, 3700164, 3700214, 3700215, 3700216, 3700217, 3700218, 3700219, 3700220, 3700228, 3700229, 3700230, 3700231, 3700242, 3700244, 3700245, 3700247, 3700248, 3700249, 3700250, 3700251, 3700252, 3700253, 3700254, 3700263, 3700268, 3700269, 3700270, 3700271, 3700272, 3700279, 3700280, 3700281, 3700284, 3700285, 3700286, 3700288, 3700321, 3700322, 3700334, 3700335, 3700336, 3700350, 3700351, 3700380, 3700385, 3700388, 3700389, 3700390, 3700402, 3700418, 3700419, 3700429, 3700442, 3700465, 3700466, 3700486, 3700000, 3700001, 3700005, 3700006, 3700007, 3700008, 3700009, 3700011, 3700012, 3700013, 3700014, 3700016, 3700017, 3700018, 3700019, 3700020, 3700039, 3700040, 3700041, 3700042, 3700043, 3700044, 3700045, 3700046, 3700047, 3700048, 3700049, 3700057, 3700074, 3700075, 3700076, 3700077, 3700078, 3700079, 3700080, 3700081, 3700082, 3700084, 3700085, 3700087, 3700098, 3700099, 3700100, 3700101, 3700103, 3700106, 3700118, 3700119, 3700120, 3700135, 3700136, 3700141, 3700142, 3700143, 3700144, 3700147, 3700148, 3700149, 3700150, 3700156, 3700157, 3700158, 3700159, 3700164, 3700214, 3700215, 3700216, 3700217, 3700218, 3700219, 3700220, 3700228, 3700229, 3700230, 3700231, 3700242, 3700244, 3700245, 3700247, 3700248, 3700249, 3700250, 3700251, 3700252, 3700253, 3700254, 3700263, 3700268, 3700269, 3700270, 3700271, 3700272, 3700279, 3700280, 3700281, 3700284, 3700285, 3700286, 3700288, 3700321, 3700322, 3700334, 3700335, 3700336, 3700350, 3700351, 3700380, 3700385, 3700388, 3700389, 3700390, 3700402, 3700418, 3700419, 3700429, 3700442, 3700465, 3700466, 3700486};
                reward = arr[Randomizer.rand(0, arr.length - 1)];
                needItem = 4170038;
                break;
        }
        if (needItem == -1 || reward == -1) {
            self.sayOk("잘못된 접근.");
            return;
        }
        if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 1 ||
                getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 1 ||
                getPlayer().getInventory(MapleInventoryType.SETUP).getNumFreeSlot() < 3) {
            self.sayOk("อุปกรณ์1칸, ใช้3칸, 설치1칸 이상 비워สัปดาห์세요.");
            return;
        }
        if (target.exchange(itemID, -1, needItem, -1, reward, 1) > 0) {
            getPlayer().send(CWvsContext.getIncubatorResult(reward, (short) 1, 0, (short) 0, itemID));
        } else { //여기로 올경우 없긴함
            self.sayOk("ช่อง ไม่พอ.");
        }
    }

    public void enter_993165543() {
        if (getPlayer().getOneInfoQuestInteger(121212, "job_select") == 0) {
            initNPC(MapleLifeFactory.getNPC(9010000));
            NPCScriptManager.getInstance().start(getClient(), 1540208);
        }
    }

    public void rank_user() {
        int v = -1;
        String L0 = "#L0#วันวัน รางวัล 받아가기#l";
        String L1 = "";
        String customText1 = Center.RankerCustomText;
        String customText2 = Center.Ranker2CustomText;
        String customText3 = Center.Ranker3CustomText;
        
        String rank = "";
        if (getNpc().getId() == PlayerNPCConstants.DamageRank1NPC)
            rank = "1";
        if (getNpc().getId() == PlayerNPCConstants.DamageRank2NPC)
            rank = "2";
        if (getNpc().getId() == PlayerNPCConstants.DamageRank3NPC)
            rank = "3";

        if (getPlayer().getName().equals(npc.getName()))
                L1 = "\r\n#fs11##L1#랭커를 위한 특별เมนู(문구เปลี่ยน)";

        // 1위
        if (rank == "1") {
            if (getPlayer().getName().equals(npc.getName()))
                L0 = "#L0#특별한 วันวัน รางวัล 받아가기(1위전용)#l";

            if (!customText1.equals("")) {
                v = self.askMenu("#fs11##b" + npc.getName() + "#k : " + customText1 + "\r\n\r\n#b" + L0 + L1);
            } else {
                v = self.askMenu("#fs11#안녕하세요 \r\n저는 전투력 측정 랭킹 " + rank + "위를 달성중인 #b" + npc.getName() + "#k .\r\n\r\n#b" + L0 + L1);
            }
        // 2위
        } else if (rank == "2") {
            if (!customText2.equals("")) {
                v = self.askMenu("#fs11##b" + npc.getName() + "#k : " + customText2 + "\r\n\r\n#b#L3#" + rank + "위 라니 대단하세요 !#l" + L1);
            } else {
                v = self.askMenu("#fs11#안녕하세요 \r\n저는 전투력 측정 랭킹 " + rank + "위를 달성중인 #b" + npc.getName() + "#k .\r\n\r\n#b#L3#" + rank + "위 라니 대단하세요 !#l" + L1);
            }
        // 3위
        } else if (rank == "3") {
            if (!customText3.equals("")) {
                v = self.askMenu("#fs11##b" + npc.getName() + "#k : " + customText3 + "\r\n\r\n#b#L3#" + rank + "위 라니 대단하세요 !#l" + L1);
            } else {
               v = self.askMenu("#fs11#안녕하세요 \r\n저는 전투력 측정 랭킹 " + rank + "위를 달성중인 #b" + npc.getName() + "#k .\r\n\r\n#b#L3#" + rank + "위 라니 대단하세요 !#l" + L1);
            }
        }
        switch (v) {
            case 0: {
                if (getPlayer().getOneInfoQuestInteger(1234699, "dailyGiftCT") < 1) {
                    if (rank == "1" && getPlayer().getName().equals(npc.getName())) {  // 1위 본인 รางวัล
                        if (target.exchange(5062005, 20, 5062503, 20, 2430044, 1, 4001715, 50, 4031227, 500) > 0) {
                            getPlayer().updateOneInfo(1234699, "dailyGiftCT", "1");
                            self.sayOk("#fs11#วันวัน รางวัล을 สำเร็จ적으로 수령แล้ว.\r\n\r\n\r\n#b" +
                                    "#i5062005# #z5062005# 20개\r\n" +
                                    "#i5062503# #z5062503# 20개\r\n" +
                                    "#i2430044# #z2430044# 1개\r\n" +
                                    "#i4001715# #z4001715# 50개\r\n" +
                                    "#i4031227# #z4031227# 500개\r\n");
                        } else {
                            self.say("#fs11#กระเป๋า 여유공간이 จำเป็น.");
                        }
                    } else { // ทั้งหมด รางวัล
                        if (target.exchange(5062005, 10, 5062503, 10, 2430043, 1, 4001715, 20, 4031227, 200) > 0) {
                            getPlayer().updateOneInfo(1234699, "dailyGiftCT", "1");
                            self.sayOk("#fs11#วันวัน รางวัล을 สำเร็จ적으로 수령แล้ว.\r\n\r\n\r\n#b" +
                                    "#i5062005# #z5062005# 10개\r\n" +
                                    "#i5062503# #z5062503# 10개\r\n" +
                                    "#i2430043# #z2430043# 1개\r\n" +
                                    "#i4001715# #z4001715# 20개\r\n" +
                                    "#i4031227# #z4031227# 200개\r\n");
                        } else {
                            self.say("#fs11#กระเป๋า 여유공간이 จำเป็น.");
                        }
                    }
                } else {
                    self.sayOk("#fs11#วันนี้은 이미 วันวันรางวัล을 받아가셨.");
                }
                break;
            }
            case 1: {
                if (getPlayer().getName().equals(npc.getName())) {
                    String text = self.askText("#fs11#커스텀 문구를 넣어สัปดาห์세요! 커스텀 문구는 리붓동안 유지.");
                    if (!text.equals("") && !text.contains("#")) {
                        if (rank == "1")
                            Center.RankerCustomText = text;
                        if (rank == "2")
                            Center.Ranker2CustomText = text;
                        if (rank == "3")
                            Center.Ranker3CustomText = text;
                        self.sayOk("#fs11#커스텀 문구가 สำเร็จ적으로 เปลี่ยน되었.");
                    } else {
                        self.sayOk("#fs11## 및 공백은 ใช้할 수 없.");
                    }
                }
                break;
            }
            case 3: {
                self.sayOk("#fs11#감사.");
                break;
            }
        }
    }

    public void Royal_QuickMove() {
        int[][] quickMove = new int[][]{
                {993033200, 1, 200, 0},
                {450001013, 200, 210, 30},
                {450001216, 200, 210, 60},
                {450001260, 200, 210, 80},
                {450014160, 205, 210, 60},
                {450014310, 205, 210, 100},
                {450002002, 210, 220, 100},
                {450002004, 210, 220, 100},
                {450002006, 210, 220, 100},
                {450002008, 210, 220, 100},
                {450002010, 210, 220, 100},
                {450002012, 210, 220, 130},
                {450002014, 210, 220, 130},
                {450002019, 210, 220, 160},
                {450015050, 215, 220, 130},
                {450015240, 215, 220, 160},
                {450015290, 215, 220, 190},
                {450015300, 215, 220, 190},
                {450003220, 220, 225, 190}, //무법3
                {450003310, 220, 225, 210},
                {450003320, 220, 225, 210},
                {450003510, 220, 225, 240},
                {450005121, 225, 230, 280},
                {450005130, 225, 230, 280},
                {450005222, 225, 230, 320},
                {450005241, 225, 230, 320},
                {450005430, 225, 230, 360},
                {450005412, 225, 230, 360},
                {450005500, 225, 230, 360},
                {450006210, 230, 235, 480},
                {450006220, 230, 235, 480},
                {450006230, 230, 235, 480},
                {450006300, 230, 235, 480},
                {450006410, 230, 235, 520},
                {450006430, 230, 235, 520},
                {450007110, 235, 240, 600},
                {450007210, 235, 240, 640},
                {450007220, 235, 240, 640},
                {450007230, 235, 240, 640},
                {450016020, 240, 245, 600},
                {450016030, 240, 245, 600},
                {450016060, 240, 245, 600},
                {450016090, 240, 245, 600},
                {450016110, 240, 245, 640},
                {450016120, 240, 245, 640},
                {450016130, 240, 245, 640},
                {450016140, 240, 245, 640},
                {450016160, 240, 245, 640},
                {450016220, 240, 245, 670},
                {450016230, 240, 245, 670},
                {450016260, 240, 245, 670},
                {450009110, 245, 250, 670},
                {450009120, 245, 250, 670},
                {450009130, 245, 250, 670},
                {450009140, 245, 250, 670},
                {450009150, 245, 250, 670},
                {450009160, 245, 250, 670},
                {450009210, 245, 250, 700},
                {450009220, 245, 250, 700},
                {450009230, 245, 250, 700},
                {450009240, 245, 250, 700},
                {450009250, 245, 250, 700},
                {450009260, 245, 250, 700},
                {450011420, 250, 255, 760},
                {450011410, 250, 255, 760},
                {450011400, 250, 255, 760},
                {450011430, 250, 255, 760},
                {450011440, 250, 255, 760},
                {450011450, 250, 255, 760},
                {450011510, 250, 255, 790},
                {450011520, 250, 255, 790},
                {450011530, 250, 255, 790},
                {450011540, 250, 255, 790},
                {450011550, 250, 255, 790},
                {450011560, 250, 255, 790},
                {450011570, 250, 255, 790},
                {450011600, 250, 255, 820},
                {450011610, 250, 255, 820},
                {450011620, 250, 255, 820},
                {450011630, 250, 255, 820},
                {450011640, 250, 255, 820},
                {450011650, 250, 255, 820},
                {450012020, 255, 260, 850},
                {450012030, 255, 260, 850},
                {450012040, 255, 260, 850},
                {450012100, 255, 260, 850},
                {450012110, 255, 260, 850},
                {450012120, 255, 260, 850},
                {450012130, 255, 260, 1000},
                {450012330, 255, 260, 880},
                {450012350, 255, 260, 880},
                {450012430, 255, 260, 880},
                {410000540, 260, 265, 50},
                {410000600, 260, 265, 50},
                {410000640, 260, 265, 50},
                {410000700, 260, 265, 50},
                {410000710, 260, 265, 50},
                {410000840, 265, 270, 100},
                {410000850, 265, 270, 100},
                {410000860, 265, 270, 100},
                {410000870, 265, 270, 100},
                {410000880, 265, 270, 100},
                {410000890, 265, 270, 100},
                {410000990, 265, 270, 100},
                {410003040, 270, 300, 130},
                {410003050, 270, 300, 130},
                {410003060, 270, 300, 130},
                {410003070, 270, 300, 130},
                {410003090, 270, 300, 160},
                {410003100, 270, 300, 160},
                {410003110, 270, 300, 160},
                {410003120, 270, 300, 160},
                {410003130, 270, 300, 160},
                {410003140, 270, 300, 160},
                {410003150, 270, 300, 200},
                {410003160, 270, 300, 200},
                {410003170, 270, 300, 200},
                {410003180, 270, 300, 200},
                {410003190, 270, 300, 200},
                {410003200, 270, 300, 200},
        };
        int v = self.askMenu("     #fUI/UIWindow2.img/Script/Title/0#\r\n원하시는 เมนู를 เลือก하세요.\r\n#b#L0#적정 เลเวล 사냥터#l\r\n#L1#ปัจจุบัน เลเวล 이용เป็นไปได้한 ทั้งหมด 사냥터#l");
        switch (v) {
            case 0: { //적정เลเวล
                List<Integer> maps = new ArrayList<>();
                int index = 0;
                String mapString = "     #fUI/UIWindow2.img/Script/Title/0#\r\n";
                for (int i = 0; i < quickMove.length; i++) {
                    if (getPlayer().getLevel() >= quickMove[i][1] && getPlayer().getLevel() <= quickMove[i][2]) {
                        maps.add(quickMove[i][0]);
                        String extra = "";
                        if (quickMove[i][3] > 0) {
                            if (quickMove[i][1] < 260) {
                                extra = " #fUI/UIWindow.img/ToolTip/WorldMap/ArcaneForce# " + quickMove[i][3];
                            } else {
                                extra = " #fUI/UIWindow.img/ToolTip/WorldMap/AuthenticForce# " + quickMove[i][3];
                            }
                        }
                        mapString += "\r\n#L" + index + "# #k[" + quickMove[i][1] + "~" + quickMove[i][2] + "] #b#m" + quickMove[i][0] + "#" + extra + "#l";
                        index++;
                    }
                }
                int vv = self.askMenu(mapString);
                if (vv >= 0) {
                    registerTransferField(maps.get(vv));
                }
                break;
            }
            case 1: {
                List<Integer> maps = new ArrayList<>();
                int index = 0;
                String mapString = "     #fUI/UIWindow2.img/Script/Title/0#\r\n";
                for (int i = 0; i < quickMove.length; i++) {
                    if (getPlayer().getLevel() >= quickMove[i][1] && quickMove[i][1] > 1) {
                        maps.add(quickMove[i][0]);
                        String extra = "";
                        if (quickMove[i][3] > 0) {
                            if (quickMove[i][1] < 260) {
                                extra = " #fUI/UIWindow.img/ToolTip/WorldMap/ArcaneForce# " + quickMove[i][3];
                            } else {
                                extra = " #fUI/UIWindow.img/ToolTip/WorldMap/AuthenticForce# " + quickMove[i][3];
                            }
                        }
                        mapString += "\r\n#L" + index + "# #k[" + quickMove[i][1] + "~" + quickMove[i][2] + "] #b#m" + quickMove[i][0] + "#" + extra + "#l";
                        index++;
                    }
                }
                int vv = self.askMenu(mapString);
                if (vv >= 0) {
                    registerTransferField(maps.get(vv));
                }
                break;
            }
        }
    }

    public void consume_2439178() {
        initNPC(MapleLifeFactory.getNPC(9031005));
        if (self.askYesNo("연금술을 배우시겠습니까?\r\n\r\n※ 다른 종류의 도핑물약을 2개 ใช้ เป็นไปได้하게 . ※") == 1) {
            if (target.exchange(2439178, -1) > 0) {
                getPlayer().changeSkillLevel(92040000, 1, 13);
                self.sayOk("연금술 สกิล ได้รับ에 สำเร็จแล้ว.");
            } else {
                self.say("잘못된 접근.");
            }
        }
    }

    List<List<Pair<Integer, Integer>>> bossTierUpgradeItemList = List.of(
            List.of(new Pair<>(4001716, 3), new Pair<>(4310266, 100), new Pair<>(4310308, 50)),
            List.of(new Pair<>(4001716, 5), new Pair<>(4310266, 200), new Pair<>(4310308, 100)),
            List.of(new Pair<>(4001716, 6), new Pair<>(4310266, 300), new Pair<>(4310308, 350)),
            List.of(new Pair<>(4001716, 7), new Pair<>(4310266, 400), new Pair<>(4310308, 700)),
            List.of(new Pair<>(4001716, 15), new Pair<>(4310266, 500), new Pair<>(4310308, 1500)),
            List.of(new Pair<>(4001716, 25), new Pair<>(4310266, 600), new Pair<>(4310308, 2700)),
            List.of(new Pair<>(4001716, 35), new Pair<>(4310266, 700), new Pair<>(4310308, 3000)),
            List.of(new Pair<>(4001716, 50), new Pair<>(4310266, 800), new Pair<>(4310308, 5000))
    );

    //제니아가 10 값 넣어놈.. 바꾸던가 빼던가 해야할듯?

    List<Long> bossTierUpgradeMesoList = List.of(
            10L, 10L, 10L, 10L, 10L, 10L, 10L, 10L
    );

    public void bossRank() {
        if (getPlayer().getBossTier() >= 8) {
            self.sayOk("최고단계까지 승급을  더 이상 승급을 ไม่สามารถทำได้.");
            return;
        }

        if (getPlayer().getBossTier() <= 0) {
            getPlayer().setBossTier(1);
        }

        StringBuilder str = new StringBuilder("#fs11##fc0xFF990033##eบอส 랭크 승급 ระบบ#n#fc0xFF000000#이라네.\r\n");
        str.append("#bบอส 랭크 승급#fc0xFF000000# 통해 더욱 더 강해져보지 않겠나!?\r\n\r\n");
        str.append("#L0##bถัดไป เลเวล 승급하겠.#l\r\n");
        str.append("#L1##bบอส 랭크란 무엇인가요?#l");
        int select = self.askMenu(str.toString());
        if (select == 1) {
            str = new StringBuilder("#fs11##fc0xFF990033#[랭크별 승급Buff]\r\n\r\n");
            str.append("[1] 랭크 당\r\n#bบอส โจมตี력 + 10%\r\n#bบอส เข้าเป็นไปได้횟수 + 1\r\n\r\n");
            str.append("#fc0xFF990033#[บอส 랭크 2เลเวล]\r\n#b하드 스우, 데미안, 루시드 เข้า เป็นไปได้\r\n\r\n");
            str.append("#fc0xFF990033#[บอส 랭크 3เลเวล]\r\n#b가디언 엔젤 슬라임, 노멀 윌 เข้า เป็นไปได้\r\n\r\n");
            str.append("#fc0xFF990033#[บอส 랭크 4เลเวล]\r\n#b노멀 더스크 เข้า เป็นไปได้\r\n\r\n");
            str.append("#fc0xFF990033#[บอส 랭크 5เลเวล]\r\n#b하드 윌, 듄켈 เข้า เป็นไปได้\r\n\r\n");
            str.append("#fc0xFF990033#[บอส 랭크 6เลเวล]\r\n#b카오스 더스크, 진 힐라 เข้า เป็นไปได้\r\n\r\n");
            str.append("#fc0xFF990033#[บอส 랭크 7เลเวล]\r\n#b검은마법사, 세렌 เข้า เป็นไปได้");
            self.sayOk(str.toString());
        } else {
            str = new StringBuilder("#fs11#ถัดไป เลเวล เลเวล업을 하기 위해선 아래와 같은 재료가 จำเป็น하다네.\r\n\r\n");
            for (var pair : bossTierUpgradeItemList.get(getPlayer().getBossTier())) {
                str.append("#i").append(pair.left).append("# #b#z").append(pair.left).append("# #r").append(pair.right).append("개#k\r\n");
            }
            str.append("\r\n#fs11##e#b정말 승급을 할텐가?#k#n");
            int askType = self.askYesNo(str.toString());

            if (askType == 1) {
                for (var pair : bossTierUpgradeItemList.get(getPlayer().getBossTier())) {
                    if (!getPlayer().haveItem(pair.left, pair.right)) {
                        self.sayOk("승급에 จำเป็น한 #e재료#n 모자란 것 같군.");
                        return;
                    }
                }

                if (getPlayer().getMeso() < bossTierUpgradeMesoList.get(getPlayer().getBossTier())) {
                    self.sayOk("승급에 จำเป็น한 #eMeso#n 모자란 것 같군.");
                    return;
                }

                if (Math.floor(Math.random() * 100) < 100) {
                    int nextTier = getPlayer().getBossTier() + 1;
                    Center.Broadcast.broadcastMessage(CField.chatMsg(8, "[บอส랭크] " + getPlayer().getName() + " 님이 " + nextTier + " เลเวล เลเวล업에 สำเร็จ하였."));
                    getPlayer().setBossTier(nextTier);
                    str = new StringBuilder("축하한다네! 승급에 สำเร็จ했군\r\n");
                    getPlayer().send(CField.showEffect("tdAnbur/idea_hyperMagic"));
                    self.sayOk(str.toString());
                } else {
                    self.sayOk("เลเวล업에 ล้มเหลว하였.");
                }
                for (var pair : bossTierUpgradeItemList.get(getPlayer().getBossTier())) {
                    getPlayer().removeItem(pair.left, pair.right);
                }
                getPlayer().gainMeso(-bossTierUpgradeMesoList.get(getPlayer().getBossTier()), true);
            }
        }
    }

    List<Pair<String, Integer>> jobs = new ArrayList<>(Arrays.asList(
            new Pair<>("히어로", 112),
            new Pair<>("팔라딘", 122),
            new Pair<>("다크나이트", 132),
            new Pair<>("아크메이지(불,독)", 212),
            new Pair<>("아크메이지(썬,콜)", 222),
            new Pair<>("비숍", 232),
            new Pair<>("보우마스터", 312),
            new Pair<>("신궁", 322),
            new Pair<>("패스파인더", 332),
            new Pair<>("나이트로드", 412),
            new Pair<>("섀도어", 422),
            new Pair<>("듀얼블레이더", 434),
            new Pair<>("바이퍼", 512),
            new Pair<>("캡틴", 522),
            new Pair<>("캐논마스터", 532),
            new Pair<>("소울마스터", 1112),
            new Pair<>("플레임위자드", 1212),
            new Pair<>("윈드브레이커", 1312),
            new Pair<>("나이트워커", 1412),
            new Pair<>("스트라이커", 1512),
            new Pair<>("미하วัน", 5112),
            new Pair<>("아란", 2112),
            new Pair<>("에반", 2218),
            new Pair<>("메르세데스", 2312),
            new Pair<>("팬텀", 2412),
            new Pair<>("은เดือน", 2512),
            new Pair<>("루미너스", 2712),
            new Pair<>("데몬슬레이어", 3112),
            new Pair<>("데몬어벤져", 3122),
            new Pair<>("배틀메이지", 3212),
            new Pair<>("와วัน드헌터", 3312),
            new Pair<>("메카닉", 3512),
            new Pair<>("제논", 3612),
            new Pair<>("블래스터", 3712),
            new Pair<>("카이저", 6112),
            new Pair<>("카인", 6312),
            new Pair<>("카데나", 6412),
            new Pair<>("엔젤릭버스터", 6512),
            new Pair<>("키네시스", 14212),
            new Pair<>("아델", 15112),
            new Pair<>("วัน리움", 15212),
            new Pair<>("아크", 15512),
            new Pair<>("호영", 16412)
    ));

    public void freechange_job() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        int v = self.askMenu("자유전직 เมนู(제로 불เป็นไปได้!)\r\n\r\n#L0##b모험가 자유전직(#r#i4310086# #z4310086# 1개จำเป็น)#b#l\r\n#L1#ทั้งหมด직업 자유전직#l");
        switch (v) {
            case 0: {
                if (getPlayer().getItemQuantity(4310086, false) <= 0) {
                    self.sayOk("자유전직 코인이 있어야 자유전직을 할 수 있.");
                    break;
                }
                switch (getPlayer().getJob()) {
                    case 112:
                    case 122:
                    case 132:
                    case 212:
                    case 222:
                    case 232:
                    case 312:
                    case 322:
                    case 332:
                    case 412:
                    case 422:
                    case 434:
                    case 512:
                    case 522:
                    case 532:
                        break;
                    default:
                        self.sayOk("모험가 직업군만 ใช้할 수 있.");
                        return;
                }
                sendUIJobChange();
                break;
            }
            case 1: {
                if (getPlayer().getJob() == 10112) {
                    self.sayOk("제로는 이용이 불เป็นไปได้.");
                    return;
                }
                int vv = self.askMenu("원하시는 자유전직 เมนู를 เลือกโปรด.\r\n#b#L0#300เลเวล 자유전직(235 환생ดำเนินการ)#l\r\n#L1#강림크레딧 자유전직#l\r\n#L2#300억 Meso 자유전직#l");
                switch (vv) {
                    case 0: { //300เลเวล 자전
                        if (getPlayer().getLevel() < 300) {
                            self.sayOk("300เลเวล 이상만 ใช้할 수 있.");
                            return;
                        }
                        String menu = "자유전직을 원하시는 직업을 เลือกโปรด.#b";
                        for (Pair<String, Integer> job : jobs) {
                            if (job.getRight() == getPlayer().getJob()) {
                                continue;
                            }
                            menu += "\r\n#L" + job.getRight() + "#" + job.getLeft() + "#l";
                        }
                        int vvv = self.askMenu(menu);
                        String selectJob = "";
                        for (Pair<String, Integer> job : jobs) {
                            if (job.getRight() == vvv) {
                                selectJob = job.getLeft();
                            }
                        }

                        if (vvv > 0 && !selectJob.equals("")) {
                            int yesNo = self.askYesNo("เลือก하신 직업이 #r" + selectJob + "#k 맞습니까?\r\n\r\n(예를 누를시 자유전직 및 เลเวล 235 วินาที기화가 이루어집니다.)");
                            if (yesNo == 1) {
                                for (VCore core : getPlayer().getVCoreSkillsNoLock()) {
                                    if (core.getState() == 2) {
                                        getPlayer().dropMessage(1, "Vสกิล 코어를 전부 장착ปลดล็อก 후 시도โปรด.");
                                        return;
                                    }
                                }
                                Equip test2 = null;
                                if (getPlayer().getJob() / 100 == 4) {
                                    test2 = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short) -10);
                                }
                                if (test2 != null) {
                                    getPlayer().dropMessage(1, "보조อาวุธ/방패/블레이드를 ปลดล็อก하셔야 .");
                                    return;
                                }
                                changeFreeJob(vvv);

                                int ap = 1179;
                                getPlayer().doRebirth();
                                short Tap = 1179;
                                int rcc = getPlayer().getRebirthCount() + getPlayer().getSuperRebirthCount();
                                ap = (short) Math.min(Short.MAX_VALUE, Tap + (rcc * 5));
                                getPlayer().setRemainingAp((short) (ap));
                                getPlayer().getStat().setStr((short) 4, getPlayer());
                                getPlayer().getStat().setDex((short) 4, getPlayer());
                                getPlayer().getStat().setInt((short) 4, getPlayer());
                                getPlayer().getStat().setLuk((short) 4, getPlayer());
                                Map<MapleStat, Long> statups = new EnumMap<MapleStat, Long>(MapleStat.class);
                                statups.put(MapleStat.STR, (long) 4);
                                statups.put(MapleStat.DEX, (long) 4);
                                statups.put(MapleStat.INT, (long) 4);
                                statups.put(MapleStat.LUK, (long) 4);
                                statups.put(MapleStat.MAXHP, (long) getPlayer().getStat().getMaxHp());
                                statups.put(MapleStat.MAXMP, (long) getPlayer().getStat().getMaxMp());
                                statups.put(MapleStat.LEVEL, (long) 235);
                                statups.put(MapleStat.AVAILABLEAP, (long) ap);
                                getPlayer().send(CWvsContext.updatePlayerStats(statups, true, getPlayer()));
                            }
                        }
                        break;
                    }
                    case 1: {
                        int realcash = getPlayer().getRealCash();
                        if (realcash < 30000) {
                            self.sayOk("강림크레딧 3 이상 จำเป็น. ปัจจุบัน 강림크레딧 : " + realcash);
                            return;
                        }
                        if (self.askYesNo("ปัจจุบัน 강림크레딧이 " + realcash + "만큼 있. 자유전직 1회 30,000강림크레딧 자유전직을 ดำเนินการต้องการหรือไม่?") == 1) {
                            String menu = "자유전직을 원하시는 직업을 เลือกโปรด.#b";
                            for (Pair<String, Integer> job : jobs) {
                                if (job.getRight() == getPlayer().getJob()) {
                                    continue;
                                }
                                menu += "\r\n#L" + job.getRight() + "#" + job.getLeft() + "#l";
                            }
                            int vvv = self.askMenu(menu);
                            String selectJob = "";
                            for (Pair<String, Integer> job : jobs) {
                                if (job.getRight() == vvv) {
                                    selectJob = job.getLeft();
                                }
                            }

                            if (vvv > 0 && !selectJob.equals("")) {
                                int yesNo = self.askYesNo("เลือก하신 직업이 #r" + selectJob + "#k 맞습니까?\r\n\r\n(예를 누를시 자유전직이 이루어집니다.)");
                                if (yesNo == 1) {
                                    for (VCore core : getPlayer().getVCoreSkillsNoLock()) {
                                        if (core.getState() == 2) {
                                            getPlayer().dropMessage(1, "Vสกิล 코어를 전부 장착ปลดล็อก 후 시도โปรด.");
                                            return;
                                        }
                                    }
                                    Equip test2 = null;
                                    if (getPlayer().getJob() / 100 == 4) {
                                        test2 = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short) -10);
                                    }
                                    if (test2 != null) {
                                        getPlayer().dropMessage(1, "보조อาวุธ/방패/블레이드를 ปลดล็อก하셔야 .");
                                        return;
                                    }
                                    getPlayer().setRealCash(realcash - 30000);
                                    changeFreeJob(vvv);
                                }
                            }
                        }
                        break;
                    }
                    case 2: {
                        long meso = getPlayer().getMeso();
                        if (meso < 30000000000L) {
                            self.sayOk("Meso 300억 이상 จำเป็น. ปัจจุบัน Meso : " + meso);
                            return;
                        }
                        if (self.askYesNo("ปัจจุบัน Meso " + meso + "만큼 있. 자유전직 1회 300억 Meso 자유전직을 ดำเนินการต้องการหรือไม่?") == 1) {
                            String menu = "자유전직을 원하시는 직업을 เลือกโปรด.#b";
                            for (Pair<String, Integer> job : jobs) {
                                if (job.getRight() == getPlayer().getJob()) {
                                    continue;
                                }
                                menu += "\r\n#L" + job.getRight() + "#" + job.getLeft() + "#l";
                            }
                            int vvv = self.askMenu(menu);
                            String selectJob = "";
                            for (Pair<String, Integer> job : jobs) {
                                if (job.getRight() == vvv) {
                                    selectJob = job.getLeft();
                                }
                            }

                            if (vvv > 0 && !selectJob.equals("")) {
                                int yesNo = self.askYesNo("เลือก하신 직업이 #r" + selectJob + "#k 맞습니까?\r\n\r\n(예를 누를시 자유전직이 이루어집니다.)");
                                if (yesNo == 1) {
                                    for (VCore core : getPlayer().getVCoreSkillsNoLock()) {
                                        if (core.getState() == 2) {
                                            getPlayer().dropMessage(1, "Vสกิล 코어를 전부 장착ปลดล็อก 후 시도โปรด.");
                                            return;
                                        }
                                    }
                                    Equip test2 = null;
                                    if (getPlayer().getJob() / 100 == 4) {
                                        test2 = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short) -10);
                                    }
                                    if (test2 != null) {
                                        getPlayer().dropMessage(1, "보조อาวุธ/방패/블레이드를 ปลดล็อก하셔야 .");
                                        return;
                                    }
                                    getPlayer().gainMeso(-30000000000L, true);
                                    changeFreeJob(vvv);
                                }
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
    }



    public void changeFreeJob(int vvv) {
        try {
            getPlayer().dispel();
            for (Map.Entry<Skill, SkillEntry> entry : new HashMap<>(getPlayer().getSkills()).entrySet()) {
                if (GameConstants.getSkillRootFromSkill(entry.getKey().getId()) / 100 == getPlayer().getJob() / 100) {
                    getPlayer().changeSkillLevel(entry.getKey().getId(), (byte) 0, (byte) 0);
                }
            }
            getPlayer().changeJob(vvv, true); // 직업 เปลี่ยน
            for (int i = 0; i < (getPlayer().getJob() % 10) + 1; i++) {
                int job = ((i + 1) == ((getPlayer().getJob() % 10) + 1)) ? getPlayer().getJob() - (getPlayer().getJob() % 100) : getPlayer().getJob() - (i + 1);
                if (getPlayer().getJob() >= 330 && getPlayer().getJob() <= 332) {
                    if (job == 300) {
                        job = 301;
                    }
                }
                getPlayer().maxskill(job);
            }

            int div = getPlayer().getJob() < 1000 ? 100 : 1000;
            int job = getPlayer().getJob();
            if (GameConstants.isKadena(job)) {
                div = 6002;
            } else if (GameConstants.isAngelicBuster(job)) {
                div = 6001;
            } else if (GameConstants.isEvan(job)) {
                div = 2001;
            } else if (GameConstants.isMercedes(job)) {
                div = 2002;
            } else if (GameConstants.isDemonSlayer(job) || GameConstants.isDemonAvenger(job)) {
                div = 3001;
            } else if (GameConstants.isPhantom(job)) {
                div = 2003;
            } else if (GameConstants.isLuminous(job)) {
                div = 2004;
            } else if (GameConstants.isXenon(job)) {
                div = 3002;
            } else if (GameConstants.isEunWol(job)) {
                div = 2005;
                getPlayer().maxSkillByID(25001000); // 메가 펀치
                getPlayer().maxSkillByID(25001002); // 섬권
            } else if (GameConstants.isArk(job)) {
                div = 15001;
                getPlayer().maxSkillByID(155101006); // 잠식 제어
            }
            getPlayer().maxskill((getPlayer().getJob() / div) * div);
            // วินาที보자 สกิล
            if (GameConstants.isMechanic(getPlayer().getJob())) {
                getPlayer().maxSkillByID(30001068);
            }
            if (GameConstants.isResistance(getPlayer().getJob())) {
                getPlayer().maxSkillByID(80001152);
                getPlayer().maxSkillByID(30001061);
            }
            if (GameConstants.isCygnus(getPlayer().getJob())) {
                getPlayer().maxSkillByID(80001152);
                getPlayer().maxSkillByID(10001244);
                getPlayer().maxSkillByID(10000252);
            }
            if (GameConstants.isAran(getPlayer().getJob())) {
                getPlayer().maxSkillByID(20000194);
            }
            if (GameConstants.isEvan(getPlayer().getJob())) {
                getPlayer().maxSkillByID(20010022);
                getPlayer().maxSkillByID(20010194);
            }
            if (GameConstants.isMercedes(getPlayer().getJob())) {
                getPlayer().maxSkillByID(20020109);
                getPlayer().maxSkillByID(20021110);
                getPlayer().maxSkillByID(20020111);
                getPlayer().maxSkillByID(20020112);
            }
            if (GameConstants.isDemon(getPlayer().getJob())) {
                getPlayer().maxSkillByID(30010110);
                getPlayer().maxSkillByID(30011109);
            }
            if (GameConstants.isPhantom(getPlayer().getJob())) {
                int skills[] = new int[]{20031208, 20030190, 20031203, 20031205, 20030206, 20031207, 20031209, 20031251, 20031260};
                for (int skill : skills) {
                    getPlayer().maxSkillByID(skill);
                }
            }
            if (GameConstants.isLuminous(getPlayer().getJob())) {
                int skills[] = new int[]{20040216, 20040217, 20040218, 20040219, 20040221, 20041222};
                for (int skill : skills) {
                    getPlayer().maxSkillByID(skill);
                }
            }
            if (GameConstants.isAngelicBuster(getPlayer().getJob())) {
                int skills[] = new int[]{60011216, 60010217, 60011218, 60011219, 60011220, 60011222};
                for (int skill : skills) {
                    getPlayer().maxSkillByID(skill);
                }
            }
            if (GameConstants.isXenon(getPlayer().getJob())) {
                int skills[] = new int[]{30020232, 30020233, 30020234, 30020240};
                for (int skill : skills) {
                    getPlayer().maxSkillByID(skill);
                }
            }
            if (GameConstants.isEunWol(getPlayer().getJob())) {
                int skills[] = new int[]{20051284, 20050285, 20050286};
                for (int skill : skills) {
                    getPlayer().maxSkillByID(skill);
                }
            }
            if (GameConstants.isKinesis(getPlayer().getJob())) {
                int skills[] = new int[]{140000291};
                for (int skill : skills) {
                    getPlayer().maxSkillByID(skill);
                }
            }
            if (GameConstants.isArk(getPlayer().getJob())) {
                int skills[] = new int[]{150010079};
                for (int skill : skills) {
                    getPlayer().maxSkillByID(skill);
                }
            }
            if (GameConstants.isHoyoung(getPlayer().getJob())) {
                int skills[] = new int[]{160001075, 164001004};
                for (int skill : skills) {
                    getPlayer().maxSkillByID(skill);
                }
            }
            if (GameConstants.isAdele(getPlayer().getJob())) {
                int skills[] = new int[]{150020079};
                for (int skill : skills) {
                    getPlayer().maxSkillByID(skill);
                }
            }
            if (GameConstants.isLara(getPlayer().getJob())) {
                int skills[] = new int[]{160011075};
                for (int skill : skills) {
                    getPlayer().maxSkillByID(skill);
                }
            }
            switch (getPlayer().getJob()) {
                case 112:
                case 122:
                case 132:
                case 212:
                case 222:
                case 232:
                case 312:
                case 322:
                case 332:
                case 412:
                case 422:
                case 434:
                case 512:
                case 522:
                case 532:
                    getPlayer().maxSkillByID(80001152);
                    getPlayer().maxSkillByID(1281);
                    break;
            }
            // อัตโนมัติ전직 방지
            getPlayer().updateOneInfo(122870, "AutoJob", String.valueOf(getPlayer().getJob() / 10 * 10));
            getPlayer().maxskill(getPlayer().getJob());

            getPlayer().getStat().recalcLocalStats(getPlayer());

            // 유니온 เปลี่ยน
            getPlayer().firstLoadMapleUnion();
            getPlayer().sendUnionPacket();
            getPlayer().dropMessage(1, "자유전직이 เสร็จสมบูรณ์.");
        } catch (Exception e) {
            DiscordBotHandler.requestSendTelegram(e.getMessage());
            System.out.println("자유전직오류가 발생 메세지가 ส่ง.");
        }
    }

    public void sendUIJobChange() {
        if (getPlayer().getJob() / 1000 != 0) {
            getPlayer().dropMessage(1, "모험가 직업군만 자유전직이 เป็นไปได้.");
            return;
        }
        for (VCore core : getPlayer().getVCoreSkillsNoLock()) {
            if (core.getState() == 2) {
                getPlayer().dropMessage(1, "Vสกิล 코어를 전부 장착ปลดล็อก 후 시도โปรด.");
                return;
            }
        }
        Equip test2 = null;
        if (getPlayer().getJob() / 100 == 4) {
            test2 = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short) -10);
        }
        if (test2 == null) {
            getPlayer().send(CField.UIPacket.openUI(164));
        } else {
            getPlayer().dropMessage(1, "도적 직업군은 보조อาวุธ/방패/블레이드를 ปลดล็อก하셔야 .");
        }
    }

    public static class ItemEntry {
        private int itemID;
        private int[] price;
        private int[] worldLimit;
        private int index;
        private int gradeLimit;

        public ItemEntry(int itemID, int[] price, int[] worldLimit, int index, int gradeLimit) {
            this.setItemID(itemID);
            this.setPrice(price);
            this.setWorldLimit(worldLimit);
            this.setIndex(index);
            this.setGradeLimit(gradeLimit);
        }

        public int getItemID() {
            return itemID;
        }

        public void setItemID(int itemID) {
            this.itemID = itemID;
        }

        public int getPrice(int index) {
            return price[index];
        }

        public void setPrice(int[] price) {
            this.price = price;
        }

        public int getWorldLimit(int index) {
            return worldLimit[index];
        }

        public void setWorldLimit(int[] worldLimit) {
            this.worldLimit = worldLimit;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getGradeLimit() {
            return gradeLimit;
        }

        public void setGradeLimit(int gradeLimit) {
            this.gradeLimit = gradeLimit;
        }
    }

    public void sendCubeLevelUpInfo() {
        GradeRandomOption[] options = {GradeRandomOption.Red, GradeRandomOption.Black, GradeRandomOption.Additional};
        String cubeTotalInfo = "#fs11#큐브 ระดับ ขึ้น까지 #b남은 큐브 ใช้ 횟수#k ยืนยัน해 보세요.\r\n"
                + "해당 수치는 ระดับ ขึ้น 시 วินาที기화, #bบัญชี 내 공유#k.\r\n\r\n";
        for (GradeRandomOption option : options) {
            String cubeString = "";
            switch (option) {
                case Red:
                    cubeString = "레드 큐브";
                    break;
                case Black:
                    cubeString = "블랙 큐브";
                    break;
                case Additional:
                    cubeString = "에디셔널 큐브";
                    break;
            }
            cubeTotalInfo += "#e[" + cubeString + "]#n\r\n";
            String[] levelUps = {"RtoE", "EtoU", "UtoL"};
            for (String levelUp : levelUps) {
                int tryCount = getPlayer().getOneInfoQuestInteger(QuestExConstants.CubeLevelUp.getQuestID(), option.toString() + levelUp);
                int grade = 1;
                String gradeString = "";
                if (levelUp.equals("RtoE")) {
                    grade = 1;
                    gradeString = "Rare Epic";
                }
                else if (levelUp.equals("EtoU")) {
                    grade = 2;
                    gradeString = "Epic Unique";
                }
                else if (levelUp.equals("UtoL")) {
                    grade = 3;
                    gradeString = "Unique 레던더리";
                }
                int levelUpCount = GameConstants.getCubeLevelUpCount(option, grade);
                cubeTotalInfo += gradeString + " " + levelUpCount + "회 중 " + tryCount + "회 누적\r\n"; 
            }
            if (option != GradeRandomOption.AmazingAdditional) {
                cubeTotalInfo += "\r\n";
            }
        }
        self.sayOk(cubeTotalInfo);
    }

}