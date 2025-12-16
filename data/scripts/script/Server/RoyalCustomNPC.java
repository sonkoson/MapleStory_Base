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
     * public static void main(String[] args) {
     * File dir = new File("wz/Character.wz/PetEquip");
     * File files[] = dir.listFiles();
     * int index = 243;
     * for (int i = 0; i < files.length; i++) {
     * System.out.println("\t" + index + " = {");
     * System.out.println("\t\titemID = " +
     * Integer.parseInt(files[i].getName().replace(".img.xml", "")));
     * System.out.println("\t\tQuantity = 1");
     * System.out.println("\t\tPrice = 1");
     * System.out.println("\t\tPosition = 0");
     * System.out.println("\t\tReqItem = 0");
     * System.out.println("\t\tReqItemQ = 0");
     * System.out.println("\t\tCategory = 1");
     * System.out.println("\t\tMinLevel = 0");
     * System.out.println("\t\tExpiration = 0");
     * System.out.println("\t\tPointQuestExID = 0");
     * System.out.println("\t\tBuyLimit = 0");
     * System.out.println("\t\tWorldBuyLimit = 0");
     * System.out.println("\t\tLimitQuestExID = 0");
     * System.out.println("\t\tLimitQuestExValue = 0");
     * System.out.println("\t}");
     * index++;
     * }
     * }
     */

    String[] grades = new String[] {
            "Scout", "Sergeant", "Guardian", "Master", "Commander", "Supreme"
    };

    ItemEntry[] itemList = new ItemEntry[] {
            new ItemEntry(2450134, new int[] { 2, 2, 2, 2, 2, 2 }, new int[] { 0, 0, 0, 0, 0, 0 }, 1, 0),
            new ItemEntry(2434554, new int[] { 0, 0, 0, 0, 1, 1 }, new int[] { 0, 0, 0, 0, 0, 0 }, 2, 4),
            new ItemEntry(2434555, new int[] { 0, 0, 0, 3, 3, 3 }, new int[] { 0, 0, 0, 0, 0, 0 }, 3, 3),
            new ItemEntry(2434556, new int[] { 0, 5, 5, 5, 5, 5 }, new int[] { 0, 0, 0, 0, 0, 0 }, 4, 1),
            new ItemEntry(2434557, new int[] { 10, 10, 10, 10, 10, 10 }, new int[] { 0, 0, 0, 0, 0, 0 }, 5, 0),
            new ItemEntry(2434558, new int[] { 30, 30, 30, 30, 30, 30 }, new int[] { 0, 0, 0, 0, 0, 0 }, 6, 0),
            new ItemEntry(2028263, new int[] { 0, 0, 0, 0, 10, 10 }, new int[] { 0, 0, 0, 0, 0, 0 }, 7, 4),
            new ItemEntry(2028264, new int[] { 0, 0, 0, 5, 5, 5 }, new int[] { 0, 0, 0, 0, 0, 0 }, 8, 3),
            new ItemEntry(2028265, new int[] { 0, 0, 3, 3, 3, 3 }, new int[] { 0, 0, 0, 0, 0, 0 }, 9, 2),
            new ItemEntry(2437092, new int[] { 0, 0, 0, 7, 7, 7 }, new int[] { 0, 0, 0, 0, 0, 0 }, 10, 3),
            new ItemEntry(1713000, new int[] { 0, 0, 3, 3, 3, 3 }, new int[] { 0, 0, 0, 0, 0, 0 }, 11, 2),
            new ItemEntry(1713001, new int[] { 0, 0, 0, 0, 3, 3 }, new int[] { 0, 0, 0, 0, 0, 0 }, 12, 4),
            new ItemEntry(5680222, new int[] { 0, 0, 0, 0, 5, 5 }, new int[] { 0, 0, 0, 0, 0, 0 }, 13, 4),
            new ItemEntry(1112916, new int[] { 0, 0, 0, 0, 0, 300 }, new int[] { 0, 0, 0, 0, 0, 0 }, 14, 5),
    };

    public void displayShop() {
        int grade = getPlayer().getOneInfoQuestInteger(100711, "grade");
        String gradeName = "#e<ร้านค้า Awakening ระดับ " + grades[grade] + ">#n";
        String v0 = gradeName + "\r\n#ePoint ที่มี : "
                + NumberFormat.getInstance().format(getPlayer().getOneInfoQuestInteger(100778, "point"))
                + "\r\n\r\n#e[รายการร้านค้า]#n#b\r\n";
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
            int remain = entry.getWorldLimit(grade - 1) - buyCount; // จำนวนที่ซื้อได้ในวันนี้
            if (entry.getWorldLimit(grade - 1) == 0) {
                remain = -1;
            }
            int gradeLimit = entry.getGradeLimit();
            int price = entry.getPrice(grade - 1);

            int check = getPlayer().getOneInfoQuestInteger(100711, "grade");
            if (gradeLimit > check) {
                // เมื่อระดับที่ซื้อได้ไม่ใช่ปัจจุบัน
                v0 += "     #i" + itemID + "#  #z" + itemID + "# #e(" + NumberFormat.getInstance().format(price)
                        + " P)#n#l\r\n";
                v0 += "#k#e           - ซื้อได้ในวันนี้ : 0 ครั้ง#b#n #r(ระดับไม่พอ)#b\r\n";
            } else {
                if (remain != -1 && remain <= 0) {
                    // เมื่อไม่มีจำนวนที่ซื้อได้
                    v0 += "     #i" + itemID + "#  #z" + itemID + "# #e(" + NumberFormat.getInstance().format(price)
                            + " P)#n#l\r\n";
                    v0 += "#k#e           - ซื้อได้ในวันนี้ : " + remain + " ครั้ง#b#n\r\n";
                } else {
                    v0 += "#L" + index + "##i" + itemID + "#  #z" + itemID + "# #e("
                            + NumberFormat.getInstance().format(price) + " P)#n#l\r\n";
                    if (remain == -1) {
                        v0 += "#k#e           - ซื้อได้ในวันนี้ : ไม่จำกัด#b#n\r\n";
                    } else {
                        v0 += "#k#e           - ซื้อได้ในวันนี้ : " + remain + " ครั้ง#b#n\r\n";
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
                if (0 == self.askMenu(
                        "#b#i" + item + "# #z" + item + "##k มอบให้แล้วครับ\r\n\r\n#b#L0#กลับสู่หน้ารายการไอเทม#l")) {
                    displayShop();
                }
            } else {
                self.say("กรุณาจัดช่องว่างในกระเป๋าแล้วลองใหม่ครับ");
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
            String v2 = "#e<ร้านค้า Awakening>#n\r\n#b#i" + pick.getItemID() + "# #z" + pick.getItemID() + "##k";
            if (pick.getWorldLimit(grade - 1) != 0) {
                v2 += "\r\n\r\n#eจำนวนที่ซื้อได้ในวันนี้ : " + remain + " ครั้ง\r\nPoint ที่มี : "
                        + NumberFormat.getInstance().format(getPlayer().getOneInfoQuestInteger(100778, "point"));
            } else {
                v2 += "\r\n\r\n#eจำนวนที่ซื้อได้ในวันนี้ : ไม่จำกัด\r\nPoint ที่มี : "
                        + NumberFormat.getInstance().format(getPlayer().getOneInfoQuestInteger(100778, "point"));
            }
            v2 += "\r\n\r\n#nเมื่อซื้อจะหัก #b#e" + pick.getPrice(grade - 1) + " Point#n#k  ต้องการซื้อหรือไม่?";
            if (1 == self.askYesNo(v2)) {
                int getPoint = getPlayer().getOneInfoQuestInteger(100778, "point");
                if (getPoint < pick.getPrice(grade - 1)) {
                    self.say("คะแนน Awakening ไม่พอ ไม่สามารถซื้อได้");
                    return;
                }
                if (1 == target.exchange(pick.getItemID(), 1)) {
                    getPlayer().updateOneInfo(100778, "point", String.valueOf(getPoint - pick.getPrice(grade - 1)));
                    getPlayer().updateOneInfo(100712, "sum",
                            String.valueOf(getPlayer().getOneInfoQuestInteger(100778, "point")));

                    // ประมวลผลการซื้อ
                    if (pick.getWorldLimit(grade - 1) != 0) {
                        getPlayer().updateOneInfo(100778, pick.getIndex() + "_buy_count", String.valueOf(buyCount + 1));
                    }
                    if (0 == self.askMenu("#b#i" + pick.getItemID() + "# #z" + pick.getItemID()
                            + "##k ซื้อเรียบร้อยแล้ว\r\n\r\n#b#L0#กลับสู่หน้ารายการไอเทม#l")) {
                        displayShop();
                    }
                } else {
                    self.say("กรุณาจัดช่องว่างในกระเป๋าแล้วลองใหม่ครับ");
                }
            }
        }
    }

    public void soulWeapon_Copy() {
    }

    public void Royal_Shop() {
        if (!DBConfig.isGanglim) {
            return;
        }
        initNPC(MapleLifeFactory.getNPC(9090000));
        int v = self.askMenu(
                "     #fUI/UIWindow2.img/Script/Title/2#\r\n\r\n#b#h0##k สวัสดีเหมียว\r\n\r\nฉันคือ #bMyo Myo#k ที่ดูแล #rร้านค้าจัตุรัส#k ในเซิร์ฟเวอร์ #r"
                        + ServerConstants.serverName + "#k เหมียว\r\nมีอะไรให้ช่วยเหมียว?\r\n#b" +
                        "#L0#ต้องการใช้ร้านค้าอุปกรณ์#l\r\n" +
                        "#L1#ต้องการใช้ร้านค้าไอเทมใช้งาน#l\r\n" +
                        "#L2#ต้องการใช้ร้านค้าอัปเกรด#l\r\n" +
                        "#L3#ต้องการใช้ร้านค้าติดตั้ง#l\r\n" +
                        "#L4#ต้องการใช้ร้านค้า Cash (Meso)#l\r\n" +
                        "#L5#ต้องการใช้ร้านค้า Boss Point#l\r\n" +
                        "#L6#ต้องการใช้ร้านค้า Rest Point#l",
                ScriptMessageFlag.NpcReplacedByNpc);
        switch (v) {
            case 0: { // อุปกรณ์상점
                int vv = self.askMenu(
                        "     #fUI/UIWindow2.img/Script/Title/2#\r\n\r\nที่นี่คือ #rร้านค้าอุปกรณ์#k เหมียว\r\n\r\nเลือกสิ่งที่ต้องการมาเลยเหมียว\r\n#b"
                                +
                                "#L0#ต้องการใช้ร้านค้า Anvil#r(มีแผนจะเพิ่มเรื่อยๆ)#b#l\r\n" +
                                "#L1#ต้องการใช้ร้านค้าแหวน#l\r\n" +
                                "#L2#ต้องการใช้ร้านค้าอาวุธรอง#l\r\n" +
                                "#L3#ต้องการใช้ร้านค้า Emblem#l\r\n" +
                                "#L4#ต้องการใช้ร้านค้า Absolab#r(Absolab Coin)#b#l\r\n" +
                                "#L5#ต้องการใช้ร้านค้า Absolab#r(Stigma Coin)#b#l\r\n" +
                                "#L6#ต้องการใช้ร้านค้า Arcane#r(Phantasma Coin)#b#l\r\n",
                        ScriptMessageFlag.NpcReplacedByNpc);
                switch (vv) {
                    case 0: // Anvil Shop
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
                        openShop(777781); // Absolab Coin
                        break;
                    case 5:
                        openShop(777782); // Stigma Coin
                        break;
                    case 6:
                        openShop(777783); // Phantasma Coin
                        break;
                }
                break;
            }
            case 1: { // Use Shop
                int vv = self.askMenu(
                        "     #fUI/UIWindow2.img/Script/Title/2#\r\n\r\nที่นี่คือ #rร้านค้าไอเทมใช้งาน#k เหมียว\r\n\r\nเลือกสิ่งที่ต้องการมาเลยเหมียว\r\n#b"
                                +
                                "#L0#ต้องการซื้อยา#l\r\n" +
                                "#L1#ต้องการซื้อยาบัฟ#l\r\n" +
                                "#L2#ต้องการซื้อลูกธนู, ดาวกระจาย, กระสุน#l",
                        ScriptMessageFlag.NpcReplacedByNpc);
                switch (vv) {
                    case 0: // Potion Buy
                        openShop(778777);
                        break;
                    case 1: // Buff Potion Buy
                        openShop(778778);
                        break;
                    case 2: // Arrow ThrowingStar Bullet Buy
                        openShop(778779);
                        break;
                }
                break;
            }
            case 2: { // Spec Up Shop
                int vv = self.askMenu(
                        "     #fUI/UIWindow2.img/Script/Title/2#\r\n\r\nที่นี่คือ #rร้านค้าอัปเกรด#k เหมียว\r\n\r\nเลือกสิ่งที่ต้องการมาเลยเหมียว\r\n#b"
                                +
                                "#L0#ต้องการซื้อ Cube#l\r\n" +
                                "#L1#ต้องการซื้อ Scroll#l\r\n" +
                                "#L2#ต้องการซื้อ Rebirth Flame#l\r\n" +
                                "#L3#ต้องการซื้อ Circulator#l\r\n" +
                                "#L4#ต้องการใช้ร้านค้า Soul#l\r\n",
                        ScriptMessageFlag.NpcReplacedByNpc);
                switch (vv) {
                    case 0: // Cube Buy
                        openShop(779777);
                        break;
                    case 1: // Scroll Buy
                        openShop(779778);
                        break;
                    case 2: // Rebirth Flame Buy
                        openShop(779779);
                        break;
                    case 3: // Circulator Buy
                        openShop(779780);
                        break;
                    case 4: // Soul
                        openShop(779781);
                        break;
                }
                break;
            }
            case 3: { // Setup Shop
                int vv = self.askMenu(
                        "     #fUI/UIWindow2.img/Script/Title/2#\r\n\r\nที่นี่คือ #rร้านค้าติดตั้ง#k เหมียว\r\n\r\nเลือกสิ่งที่ต้องการมาเลยเหมียว\r\n#b"
                                +
                                "#L0#ต้องการซื้อเก้าอี้#l\r\n" +
                                "#L1#ต้องการซื้อสัตว์ขี่#l\r\n" +
                                "#L2#ต้องการซื้อ Damage Skin#l\r\n" +
                                "#L3#ต้องการซื้อฉายา#l",
                        ScriptMessageFlag.NpcReplacedByNpc);
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
            case 4: { // Cash Shop
                int vv = self.askMenu(
                        "     #fUI/UIWindow2.img/Script/Title/2#\r\n\r\nที่นี่คือ #rร้านค้า Cash (Meso)#k เหมียว\r\n\r\nเลือกสิ่งที่ต้องการมาเลยเหมียว\r\n#b"
                                +
                                "#L0#ต้องการซื้อสัตว์เลี้ยง#l\r\n" +
                                "#L1#ต้องการซื้ออุปกรณ์สัตว์เลี้ยง (อุปกรณ์, สกิล)#l\r\n" +
                                "#L2#ต้องการซื้อดาวกระจาย Cash#l\r\n" +
                                "#L3#ต้องการซื้อโทรโข่ง#l\r\n" +
                                "#L4#ต้องการซื้อ Carta's Pearl (ลบหู Adele ฯลฯ)#l\r\n" +
                                "#L5#ต้องการซื้อ Maple Point#l\r\n",
                        ScriptMessageFlag.NpcReplacedByNpc);
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
            case 5: { // Boss Point Shop
                int vv = self.askMenu(
                        "     #fUI/UIWindow2.img/Script/Title/2#\r\n\r\nที่นี่คือ #rร้านค้า Boss Point#k เหมียว\r\n\r\nเลือกสิ่งที่ต้องการมาเลยเหมียว\r\n#b"
                                +
                                "#L0#ต้องการใช้ร้านค้า Boss Point#l\r\n" +
                                "#L1#Boss Point คืออะไรเหรอ?#l",
                        ScriptMessageFlag.NpcReplacedByNpc);
                switch (vv) {
                    case 0:
                        openShop(9073008);
                        break;
                    case 1:
                        self.say("Boss Point คือคะแนนที่ได้จากการกำจัดบอสเหมียว");
                        self.say("ยิ่งบอสโหดก็ยิ่งได้คะแนนเยอะ ถ้าบอสกากอาจจะไม่ได้เลยเหมียว");
                        self.say("คะแนนที่ได้จากแต่ละบอสเป็นแบบนี้เหมียว\r\n\r\nChaos Zakum : 10~15 สุ่ม\r\n" +
                                "Chaos Pierre : 10~15 สุ่ม\r\n" +
                                "Chaos Von Bon : 10~15 สุ่ม\r\n" +
                                "Chaos Crimson Queen : 10~15 สุ่ม\r\n" +
                                "Hard Magnus : 20~25 สุ่ม\r\n" +
                                "Chaos Vellum : 20~25 สุ่ม\r\n" +
                                "Chaos Papulatus : 30~35 สุ่ม\r\n" +
                                "Normal Lotus : 30~35 สุ่ม\r\n" +
                                "Normal Damien : 40~45 สุ่ม\r\n" +
                                "Normal Lucid : 40~45 สุ่ม\r\n" +
                                "Normal Will : 40~45 สุ่ม\r\n" +
                                "Normal Dusk : 40~45 สุ่ม\r\n" +
                                "Normal Dunkel : 40~45 สุ่ม\r\n" +
                                "Hard Damien : 60~65 สุ่ม\r\n" +
                                "Hard Lotus : 60~65 สุ่ม\r\n" +
                                "Hard Lucid : 60~65 สุ่ม\r\n" +
                                "Hard Will : 60~65 สุ่ม\r\n" +
                                "Chaos Dusk : 70~75 สุ่ม\r\n" +
                                "Hard Dunkel : 70~75 สุ่ม\r\n" +
                                "Verus Hilla : 70~75 สุ่ม\r\n" +
                                "Black Mage : 200~250 สุ่ม", ScriptMessageFlag.NpcReplacedByNpc);
                        break;
                }
                break;
            }
            case 6: { // Rest Point Shop
                int currentDP = getPlayer().getDancePoint();
                int[][] items = new int[][] {
                        { 2350004, 100 },
                        { 2350005, 100 },
                        { 2350007, 100 },
                        { 2435122, 100 },
                        { 2437092, 300 },
                        { 2434921, 300 },
                        { 2633597, 700 },
                        { 1672079, 700 },
                        { 1182200, 700 },
                        { 2439178, 1500 },
                        { 1190302, 2000 },

                };
                String shopItems = "#b";
                for (int i = 0; i < items.length; i++) {
                    if (items[i][0] == 2439178) {
                        shopItems += "\r\n #L" + i + "##i" + items[i][0] + "# #z" + items[i][0] + "# (" + items[i][1]
                                + "p) #rกล่องเสบียง Alchemy#b#l";
                    } else {
                        shopItems += "\r\n #L" + i + "##i" + items[i][0] + "# #z" + items[i][0] + "# (" + items[i][1]
                                + "p)#l";
                    }
                }
                int vv = self.askMenu(
                        "     #fUI/UIWindow2.img/Script/Title/2#\r\n\r\nที่นี่คือ #rร้านค้า Rest Point#k เหมียว\r\n\r\n#e [Rest Point ของฉันตอนนี้ : "
                                + currentDP + "p]#n\r\n" +
                                shopItems,
                        ScriptMessageFlag.NpcReplacedByNpc);
                if (vv >= 0) {
                    if (items[vv][1] > currentDP) {
                        self.sayOk("ลองเช็คดูว่า Rest Point ไม่พอรึเปล่าเหมียว", ScriptMessageFlag.NpcReplacedByNpc);
                        return;
                    }
                    if (1 == self.askYesNo(
                            "จะซื้อ #b#i" + items[vv][0] + "# #z" + items[vv][0] + "##k " + items[vv][1]
                                    + "point จริงๆ เหรอเหมียว?",
                            ScriptMessageFlag.NpcReplacedByNpc)) {
                        if (target.exchange(items[vv][0], 1) > 0) {
                            getPlayer().setDancePoint(currentDP - items[vv][1]);
                            self.sayOk("แลกเปลี่ยนเรียบร้อยแล้วเหมียว วันหลังมาอีกนะเหมียว");
                        } else {
                            self.sayOk("ลองเช็คดูว่ามีช่องว่างรึเปล่าเหมียว", ScriptMessageFlag.NpcReplacedByNpc);
                        }
                    } else {
                        self.sayOk("ลองคิดดูดีๆ แล้วค่อยมาใหม่นะเหมียว", ScriptMessageFlag.NpcReplacedByNpc);
                    }
                }
                break;
            }
        }
    }

    public void Royal_Pet_Shop() {
        initNPC(MapleLifeFactory.getNPC(9090000));
        int vv = self.askMenu(
                "     #fUI/UIWindow2.img/Script/Title/2#\r\n\r\nที่นี่คือ #ร้านค้าสัตว์เลี้ยง#k เหมียว\r\n\r\nเลือกสิ่งที่ต้องการมาเลยเหมียว\r\n#b"
                        +
                        "#L0#ต้องการซื้อสัตว์เลี้ยง#l\r\n" +
                        "#L1#ต้องการซื้ออุปกรณ์สัตว์เลี้ยง (อุปกรณ์, สกิล)#l\r\n" +
                        "#L2#ต้องการซื้อดาวกระจาย Cash#l\r\n" +
                        "#L3#ต้องการซื้อ Maple Point#l\r\n",
                ScriptMessageFlag.NpcReplacedByNpc);
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
        int vv = self.askMenu(
                "     #fUI/UIWindow2.img/Script/Title/2#\r\n\r\nที่นี่คือ #rร้านค้า Gacha#k เหมียว มีเตรียมไอเทมไว้หลากหลายเลย ไม่ว่าจะเป็น Peanut Machine หรือ Mermaid Mirror Incubator\r\n\r\nเลือกสิ่งที่ต้องการมาเลยเหมียว\r\n#b"
                        +
                        "#L0#ต้องการซื้อเก้าอี้#l\r\n" +
                        "#L1#ต้องการซื้อสัตว์ขี่#l\r\n" +
                        "#L2#ต้องการซื้อ Damage Skin#l\r\n" +
                        "#L3#ต้องการซื้อฉายา#l",
                ScriptMessageFlag.NpcReplacedByNpc);
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
    int[] banneditem = { 3018466, 3018209, 3016200, 3016206, 3018079, 3018080, 3018515, 3015761, 3015687, 3018467,
            3018465, 3018634, 3018116, 3018117, 3018118, 3018119, 3018120, 3018121 };

    public void Royal_Peanut() {
        int needItem = -1;
        int reward = -1;
        int[] arr;
        switch (itemID) {
            case 5060002: // Premium Incubator
            case 5060005: // Premium Incubator (Riding)
                arr = new int[] { 2430050, 2430056, 2430072, 2430243, 2430506, 2430518, 2430566, 2430610, 2430633,
                        2430660, 2430726, 2430939, 2430727, 2430794, 2430968, 2431044, 2431135, 2431362, 2431364,
                        2431366, 2431367, 2431368, 2431369, 2431370, 2431371, 2431474, 2431490, 2431491, 2431492,
                        2431494, 2431496, 2431497, 2431498, 2431499, 2431500, 2431501, 2431502, 2431503, 2431505,
                        2431506, 2431527, 2431530, 2431700, 2431745, 2431760, 2431764, 2431765, 2431797, 2431799,
                        2431898, 2431914, 2431915, 2432006, 2432008, 2432015, 2432029, 2432030, 2432078, 2432085,
                        2432135, 2432149, 2432151, 2432216, 2432218, 2432291, 2432293, 2432295, 2432309, 2432347,
                        2432348, 2432349, 2432350, 2432351, 2432359, 2432361, 2432418, 2432433, 2432449, 2432498,
                        2432527, 2432528, 2432582, 2432645, 2432653, 2432724, 2432733, 2432994, 2432997, 2432998,
                        2432999, 2433000, 2433001, 2433002, 2433003, 2433006, 2433272, 2433274, 2433276, 2433345,
                        2433347, 2433349, 2433405, 2433406, 2433460, 2433603, 2433735, 2433736, 2433809, 2433811,
                        2433946, 2433948, 2434025, 2434077, 2434079, 2434275, 2434277, 2434377, 2434379, 2434515,
                        2434517, 2434525, 2434527, 2434582, 2434649, 2434737, 2434761, 2435089, 2435091, 2435112,
                        2435113, 2435114, 2435203, 2435205, 2435298, 2435442, 2435476, 2435517, 2435720, 2435722,
                        2435842, 2435843, 2435844, 2435845, 2435965, 2435967, 2435986, 2436030, 2436031, 2436183,
                        2436185, 2436292, 2436294, 2436405, 2436407, 2436524, 2436525, 2436526, 2436597, 2436599,
                        2436610, 2436648, 2436715, 2436716, 2436728, 2436730, 2436778, 2436780, 2436837, 2436839,
                        2436957, 2437026, 2437040, 2437042, 2437123, 2437125, 2437259, 2437261, 2437497, 2437625,
                        2437737, 2437738, 2437794, 2437852, 2438136, 2438137, 2438138, 2438139, 2438340, 2438373,
                        2438408, 2438409, 2438493, 2438494, 2438657, 2438743, 2438882, 2438886, 2439266, 2439278,
                        2439295, 2439406, 2439443, 2439666, 2439667, 2630116, 2630240, 2630261, 2630279, 2630386,
                        2630387, 2630448, 2630570, 2631460, 2631561, 2631563, 2631710, 2432328, 2437623, 2437719,
                        2437721, 2437923, 2438486, 2438640, 2438715, 2439034, 2439127, 2439331, 2439486, 2439675,
                        2439694, 2439808, 2439911, 2439913, 2439915, 2630451, 2630476, 2630488, 2630563, 2630573,
                        2630575, 2630763, 2630764, 2630765, 2630913, 2630917, 2630918, 2630919, 2630971, 2631136,
                        2631140, 2631413, 2631448, 2631518, 2631520, 2435296, 2437809, 2438380, 2438382, 2438488,
                        2438638, 2438745, 2439036, 2439144, 2439329, 2439484, 2439677, 2439909, 2439933, 2631191,
                        2631914, 2631800, 2631890, 2632275, 2632283, 2633075, 2633309, 2632352, 2632353, 2632887,
                        2632885, 2632361, 2632445, 2632713, 2632834, 2632913, 2633061, 2633075, 2633310, 2633601,
                        2633602 };
                reward = arr[Randomizer.rand(0, arr.length - 1)];
                needItem = 4170000;
                break;
            case 5060003: // Peanut Machine (Chair)
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
                        if (itemPair.getLeft() >= 3010000
                                && itemPair.getLeft() <= 3018720 && (itemPair.getRight() != null
                                        && (!itemPair.getRight().contains("Honor")) && itemPair.getRight() != "")
                                && banned == false) {
                            itemArray.add(itemPair.getLeft());
                        }
                    }
                    reward = itemArray.get(Randomizer.rand(0, itemArray.size() - 1));
                } else {
                    reward = itemArray.get(Randomizer.rand(0, itemArray.size() - 1));
                }
                needItem = 4170023;
                break;
            case 5060004: // Microwave (Undecided)
                needItem = 4170024; // Ice Cube
                break;
            case 5060012: // Mermaid Mirror (Damage Skin)
                arr = new int[] { 2431966, 2431965, 2431967, 2432131, 2438163, 2438164, 2438165, 2438166, 2438167,
                        2438168, 2438169, 2438170, 2438171, 2438172, 2438173, 2438174, 2438175, 2438176, 2438177,
                        2438179, 2438178, 2438180, 2438181, 2438182, 2438184, 2438185, 2438186, 2438187, 2438188,
                        2438189, 2438190, 2438191, 2438192, 2438193, 2438195, 2438196, 2438197, 2438201, 2438198,
                        2438199, 2438200, 2438202, 2438203, 2438204, 2438205, 2438206, 2438207, 2438208, 2438209,
                        2438210, 2438211, 2438212, 2438213, 2438214, 2438215, 2438216, 2438217, 2438218, 2438219,
                        2438220, 2438221, 2438222, 2438223, 2438224, 2438225, 2438226, 2438227, 2438228, 2438229,
                        2438230, 2438231, 2438232, 2438233, 2438234, 2438235, 2438236, 2438237, 2438238, 2438239,
                        2438240, 2438241, 2438242, 2438243, 2438244, 2438245, 2438246, 2438247, 2438248, 2438249,
                        2438250, 2438251, 2438252, 2438253, 2438254, 2438255, 2438256, 2438257, 2438258, 2438259,
                        2438260, 2438261, 2438262, 2438263, 2438264, 2438265, 2438266, 2438267, 2438268, 2438269,
                        2438270, 2438271, 2438272, 2438273, 2438274, 2438275, 2438276, 2438277, 2438278, 2438279,
                        2438280, 2438281, 2438282, 2438283, 2438284, 2438285, 2438286, 2438287, 2438288, 2438289,
                        2438290, 2438291, 2438292, 2438293, 2438294, 2438295, 2438296, 2438297, 2438298, 2438299,
                        2438300, 2438301, 2438302, 2438303, 2438304, 2438305, 2438306, 2438307, 2438308, 2438309,
                        2438310, 2438311, 2438312, 2438313, 2438314, 2438315, 2438353, 2438378, 2438379, 2438413,
                        2438415, 2438417, 2438419, 2438485, 2438492, 2438530, 2438637, 2438672, 2438713, 2438871,
                        2438881, 2438885, 2439298, 2439336, 2439337, 2439338, 2439381, 2439393, 2439395, 2439408,
                        2439572, 2439617, 2439652, 2439684, 2439686, 2439769, 2439925, 2439927, 2630137, 2630222,
                        2630178, 2630214, 2630235, 2630224, 2630262, 2630264, 2630266, 2439397, 2439399, 2630268,
                        2439682, 2439401, 2438149, 2438151, 2630477, 2630479, 2630481, 2630483, 2630485, 2630552,
                        2630554, 2630558, 2630560, 2630652, 2630743, 2630745, 2630747, 2630749, 2630751, 2630766,
                        2630804, 2631094, 2631135, 2631189, 2631183, 2631401, 2631471, 2631492, 2631610, 2630380,
                        2438147, 2631893, 2631885, 2631815, 2631798, 2632124, 2632281, 2632288, 2632350, 2632430,
                        2632544, 2632498, 2632712, 2632816, 5680862, 2632281, 2632888, 2632976, 2633045, 2633047,
                        2633074, 2633218, 2633220, 2633306, 2633313, 2633573, 2633599 };
                reward = arr[Randomizer.rand(0, arr.length - 1)];
                needItem = 4170031;
                break;
            case 5060018: // Book of Infinity (Title)
                arr = new int[] { 3700000, 3700001, 3700005, 3700006, 3700007, 3700008, 3700009, 3700011, 3700012,
                        3700013, 3700014, 3700016, 3700468, 3700017, 3700018, 3700019, 3700020, 3700039, 3700040,
                        3700041, 3700042, 3700043, 3700044, 3700045, 3700046, 3700047, 3700048, 3700049, 3700057,
                        3700074, 3700075, 3700076, 3700077, 3700078, 3700079, 3700080, 3700081, 3700082, 3700084,
                        3700098, 3700099, 3700100, 3700101, 3700103, 3700106, 3700118, 3700119, 3700120, 3700135,
                        3700136, 3700141, 3700142, 3700143, 3700144, 3700147, 3700148, 3700149, 3700150, 3700156,
                        3700157, 3700158, 3700159, 3700164, 3700214, 3700215, 3700216, 3700217, 3700218, 3700219,
                        3700220, 3700228, 3700229, 3700230, 3700231, 3700242, 3700244, 3700245, 3700247, 3700248,
                        3700249, 3700250, 3700251, 3700252, 3700253, 3700254, 3700263, 3700268, 3700269, 3700270,
                        3700271, 3700272, 3700279, 3700280, 3700281, 3700284, 3700285, 3700286, 3700288, 3700321,
                        3700322, 3700334, 3700335, 3700336, 3700350, 3700351, 3700380, 3700385, 3700388, 3700389,
                        3700390, 3700402, 3700418, 3700419, 3700429, 3700442, 3700465, 3700466, 3700486, 3700000,
                        3700001, 3700005, 3700006, 3700007, 3700008, 3700009, 3700011, 3700012, 3700013, 3700014,
                        3700016, 3700017, 3700018, 3700019, 3700020, 3700039, 3700040, 3700041, 3700042, 3700043,
                        3700044, 3700045, 3700046, 3700047, 3700048, 3700049, 3700057, 3700074, 3700075, 3700076,
                        3700077, 3700078, 3700079, 3700080, 3700081, 3700082, 3700084, 3700085, 3700087, 3700098,
                        3700099, 3700100, 3700101, 3700103, 3700106, 3700118, 3700119, 3700120, 3700135, 3700136,
                        3700141, 3700142, 3700143, 3700144, 3700147, 3700148, 3700149, 3700150, 3700156, 3700157,
                        3700158, 3700159, 3700164, 3700214, 3700215, 3700216, 3700217, 3700218, 3700219, 3700220,
                        3700228, 3700229, 3700230, 3700231, 3700242, 3700244, 3700245, 3700247, 3700248, 3700249,
                        3700250, 3700251, 3700252, 3700253, 3700254, 3700263, 3700268, 3700269, 3700270, 3700271,
                        3700272, 3700279, 3700280, 3700281, 3700284, 3700285, 3700286, 3700288, 3700321, 3700322,
                        3700334, 3700335, 3700336, 3700350, 3700351, 3700380, 3700385, 3700388, 3700389, 3700390,
                        3700402, 3700418, 3700419, 3700429, 3700442, 3700465, 3700466, 3700486 };
                reward = arr[Randomizer.rand(0, arr.length - 1)];
                needItem = 4170038;
                break;
        }
        if (needItem == -1 || reward == -1) {
            self.sayOk("การเข้าถึงผิดพลาด");
            return;
        }
        if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 1 ||
                getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 1 ||
                getPlayer().getInventory(MapleInventoryType.SETUP).getNumFreeSlot() < 3) {
            self.sayOk("กรุณาเว้นว่างช่อง Equip 1 ช่อง, Use 3 ช่อง, Setup 1 ช่อง อย่างน้อยครับ");
            return;
        }
        if (target.exchange(itemID, -1, needItem, -1, reward, 1) > 0) {
            getPlayer().send(CWvsContext.getIncubatorResult(reward, (short) 1, 0, (short) 0, itemID));
        } else { // Should not happen
            self.sayOk("ช่องเก็บของไม่พอครับ");
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
        String L0 = "#L0#รับรางวัลรายวัน#l";
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
            L1 = "\r\n#fs11##L1#เมนูพิเศษสำหรับ Ranker (แก้ไขข้อความทักทาย)";

        // 1st Place
        if (rank == "1") {
            if (getPlayer().getName().equals(npc.getName()))
                L0 = "#L0#รับรางวัลรายวันพิเศษ (สำหรับอันดับ 1)#l";

            if (!customText1.equals("")) {
                v = self.askMenu("#fs11##b" + npc.getName() + "#k : " + customText1 + "\r\n\r\n#b" + L0 + L1);
            } else {
                v = self.askMenu("#fs11#สวัสดีครับ \r\nฉันคือ #b" + npc.getName()
                        + "#k ผู้ที่กำลังครองอันดับ " + rank + " ในการวัดพลังต่อสู้ครับ\r\n\r\n#b" + L0 + L1);
            }
            // 2nd Place
        } else if (rank == "2") {
            if (!customText2.equals("")) {
                v = self.askMenu("#fs11##b" + npc.getName() + "#k : " + customText2 + "\r\n\r\n#b#L3#อันดับ " + rank
                        + " สุดยอดไปเลย!#l" + L1);
            } else {
                v = self.askMenu("#fs11#สวัสดีครับ \r\nฉันคือ #b" + npc.getName()
                        + "#k ผู้ที่กำลังครองอันดับ " + rank + " ในการวัดพลังต่อสู้ครับ\r\n\r\n#b#L3#อันดับ " + rank
                        + " สุดยอดไปเลย!#l" + L1);
            }
            // 3rd Place
        } else if (rank == "3") {
            if (!customText3.equals("")) {
                v = self.askMenu("#fs11##b" + npc.getName() + "#k : " + customText3 + "\r\n\r\n#b#L3#อันดับ " + rank
                        + " สุดยอดไปเลย!#l" + L1);
            } else {
                v = self.askMenu("#fs11#สวัสดีครับ \r\nฉันคือ #b" + npc.getName()
                        + "#k ผู้ที่กำลังครองอันดับ " + rank + " ในการวัดพลังต่อสู้ครับ\r\n\r\n#b#L3#อันดับ " + rank
                        + " สุดยอดไปเลย!#l" + L1);
            }
        }
        switch (v) {
            case 0: {
                if (getPlayer().getOneInfoQuestInteger(1234699, "dailyGiftCT") < 1) {
                    if (rank == "1" && getPlayer().getName().equals(npc.getName())) { // 1st Place Reward
                        if (target.exchange(5062005, 20, 5062503, 20, 2430044, 1, 4001715, 50, 4031227, 500) > 0) {
                            getPlayer().updateOneInfo(1234699, "dailyGiftCT", "1");
                            self.sayOk("#fs11#รับรางวัลรายวันเรียบร้อยแล้วครับ\r\n\r\n\r\n#b" +
                                    "#i5062005# #z5062005# 20 ชิ้น\r\n" +
                                    "#i5062503# #z5062503# 20 ชิ้น\r\n" +
                                    "#i2430044# #z2430044# 1 ชิ้น\r\n" +
                                    "#i4001715# #z4001715# 50 ชิ้น\r\n" +
                                    "#i4031227# #z4031227# 500 ชิ้น\r\n");
                        } else {
                            self.say("#fs11#กรุณาทำช่องว่างในกระเป๋าด้วยครับ");
                        }
                    } else { // General Reward
                        if (target.exchange(5062005, 10, 5062503, 10, 2430043, 1, 4001715, 20, 4031227, 200) > 0) {
                            getPlayer().updateOneInfo(1234699, "dailyGiftCT", "1");
                            self.sayOk("#fs11#รับรางวัลรายวันเรียบร้อยแล้วครับ\r\n\r\n\r\n#b" +
                                    "#i5062005# #z5062005# 10 ชิ้น\r\n" +
                                    "#i5062503# #z5062503# 10 ชิ้น\r\n" +
                                    "#i2430043# #z2430043# 1 ชิ้น\r\n" +
                                    "#i4001715# #z4001715# 20 ชิ้น\r\n" +
                                    "#i4031227# #z4031227# 200 ชิ้น\r\n");
                        } else {
                            self.say("#fs11#กรุณาทำช่องว่างในกระเป๋าด้วยครับ");
                        }
                    }
                } else {
                    self.sayOk("#fs11#วันนี้รับรางวัลรายวันไปแล้วครับ");
                }
                break;
            }
            case 1: {
                if (getPlayer().getName().equals(npc.getName())) {
                    String text = self
                            .askText("#fs11#กรุณาใส่ข้อความทักทายที่ต้องการครับ! ข้อความจะคงอยู่จนกว่าจะมีการรีบูต");
                    if (!text.equals("") && !text.contains("#")) {
                        if (rank == "1")
                            Center.RankerCustomText = text;
                        if (rank == "2")
                            Center.Ranker2CustomText = text;
                        if (rank == "3")
                            Center.Ranker3CustomText = text;
                        self.sayOk("#fs11#เปลี่ยนข้อความทักทายเรียบร้อยแล้วครับ");
                    } else {
                        self.sayOk("#fs11#ไม่สามารถใช้ # หรือเว้นว่างได้ครับ");
                    }
                }
                break;
            }
            case 3: {
                self.sayOk("#fs11#ขอบคุณครับ");
                break;
            }
        }
    }

    public void Royal_QuickMove() {
        int[][] quickMove = new int[][] {
                { 993033200, 1, 200, 0 },
                { 450001013, 200, 210, 30 },
                { 450001216, 200, 210, 60 },
                { 450001260, 200, 210, 80 },
                { 450014160, 205, 210, 60 },
                { 450014310, 205, 210, 100 },
                { 450002002, 210, 220, 100 },
                { 450002004, 210, 220, 100 },
                { 450002006, 210, 220, 100 },
                { 450002008, 210, 220, 100 },
                { 450002010, 210, 220, 100 },
                { 450002012, 210, 220, 130 },
                { 450002014, 210, 220, 130 },
                { 450002019, 210, 220, 160 },
                { 450015050, 215, 220, 130 },
                { 450015240, 215, 220, 160 },
                { 450015290, 215, 220, 190 },
                { 450015300, 215, 220, 190 },
                { 450003220, 220, 225, 190 }, // Lawless 3
                { 450003310, 220, 225, 210 },
                { 450003320, 220, 225, 210 },
                { 450003510, 220, 225, 240 },
                { 450005121, 225, 230, 280 },
                { 450005130, 225, 230, 280 },
                { 450005222, 225, 230, 320 },
                { 450005241, 225, 230, 320 },
                { 450005430, 225, 230, 360 },
                { 450005412, 225, 230, 360 },
                { 450005500, 225, 230, 360 },
                { 450006210, 230, 235, 480 },
                { 450006220, 230, 235, 480 },
                { 450006230, 230, 235, 480 },
                { 450006300, 230, 235, 480 },
                { 450006410, 230, 235, 520 },
                { 450006430, 230, 235, 520 },
                { 450007110, 235, 240, 600 },
                { 450007210, 235, 240, 640 },
                { 450007220, 235, 240, 640 },
                { 450007230, 235, 240, 640 },
                { 450016020, 240, 245, 600 },
                { 450016030, 240, 245, 600 },
                { 450016060, 240, 245, 600 },
                { 450016090, 240, 245, 600 },
                { 450016110, 240, 245, 640 },
                { 450016120, 240, 245, 640 },
                { 450016130, 240, 245, 640 },
                { 450016140, 240, 245, 640 },
                { 450016160, 240, 245, 640 },
                { 450016220, 240, 245, 670 },
                { 450016230, 240, 245, 670 },
                { 450016260, 240, 245, 670 },
                { 450009110, 245, 250, 670 },
                { 450009120, 245, 250, 670 },
                { 450009130, 245, 250, 670 },
                { 450009140, 245, 250, 670 },
                { 450009150, 245, 250, 670 },
                { 450009160, 245, 250, 670 },
                { 450009210, 245, 250, 700 },
                { 450009220, 245, 250, 700 },
                { 450009230, 245, 250, 700 },
                { 450009240, 245, 250, 700 },
                { 450009250, 245, 250, 700 },
                { 450009260, 245, 250, 700 },
                { 450011420, 250, 255, 760 },
                { 450011410, 250, 255, 760 },
                { 450011400, 250, 255, 760 },
                { 450011430, 250, 255, 760 },
                { 450011440, 250, 255, 760 },
                { 450011450, 250, 255, 760 },
                { 450011510, 250, 255, 790 },
                { 450011520, 250, 255, 790 },
                { 450011530, 250, 255, 790 },
                { 450011540, 250, 255, 790 },
                { 450011550, 250, 255, 790 },
                { 450011560, 250, 255, 790 },
                { 450011570, 250, 255, 790 },
                { 450011600, 250, 255, 820 },
                { 450011610, 250, 255, 820 },
                { 450011620, 250, 255, 820 },
                { 450011630, 250, 255, 820 },
                { 450011640, 250, 255, 820 },
                { 450011650, 250, 255, 820 },
                { 450012020, 255, 260, 850 },
                { 450012030, 255, 260, 850 },
                { 450012040, 255, 260, 850 },
                { 450012100, 255, 260, 850 },
                { 450012110, 255, 260, 850 },
                { 450012120, 255, 260, 850 },
                { 450012130, 255, 260, 1000 },
                { 450012330, 255, 260, 880 },
                { 450012350, 255, 260, 880 },
                { 450012430, 255, 260, 880 },
                { 410000540, 260, 265, 50 },
                { 410000600, 260, 265, 50 },
                { 410000640, 260, 265, 50 },
                { 410000700, 260, 265, 50 },
                { 410000710, 260, 265, 50 },
                { 410000840, 265, 270, 100 },
                { 410000850, 265, 270, 100 },
                { 410000860, 265, 270, 100 },
                { 410000870, 265, 270, 100 },
                { 410000880, 265, 270, 100 },
                { 410000890, 265, 270, 100 },
                { 410000990, 265, 270, 100 },
                { 410003040, 270, 300, 130 },
                { 410003050, 270, 300, 130 },
                { 410003060, 270, 300, 130 },
                { 410003070, 270, 300, 130 },
                { 410003090, 270, 300, 160 },
                { 410003100, 270, 300, 160 },
                { 410003110, 270, 300, 160 },
                { 410003120, 270, 300, 160 },
                { 410003130, 270, 300, 160 },
                { 410003140, 270, 300, 160 },
                { 410003150, 270, 300, 200 },
                { 410003160, 270, 300, 200 },
                { 410003170, 270, 300, 200 },
                { 410003180, 270, 300, 200 },
                { 410003190, 270, 300, 200 },
                { 410003200, 270, 300, 200 },
        };
        int v = self.askMenu(
                "     #fUI/UIWindow2.img/Script/Title/0#\r\nกรุณาเลือกเมนูที่ต้องการครับ\r\n#b#L0#แผนที่เก็บเลเวลที่แนะนำ#l\r\n#L1#แผนที่ทั้งหมดที่เข้าได้ในช่วงเลเวลปัจจุบัน#l");
        switch (v) {
            case 0: { // Appropriate Level
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
                        mapString += "\r\n#L" + index + "# #k[" + quickMove[i][1] + "~" + quickMove[i][2] + "] #b#m"
                                + quickMove[i][0] + "#" + extra + "#l";
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
                        mapString += "\r\n#L" + index + "# #k[" + quickMove[i][1] + "~" + quickMove[i][2] + "] #b#m"
                                + quickMove[i][0] + "#" + extra + "#l";
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
        if (self.askYesNo(
                "ต้องการเรียนรู้วิชาเล่นแร่แปรธาตุไหมครับ?\r\n\r\n※ จะทำให้สามารถใช้ยาบัฟต่างชนิดกันได้ 2 ขวดพร้อมกัน ※") == 1) {
            if (target.exchange(2439178, -1) > 0) {
                getPlayer().changeSkillLevel(92040000, 1, 13);
                self.sayOk("เรียนรู้วิชาเล่นแร่แปรธาตุสำเร็จแล้วครับ");
            } else {
                self.say("การเข้าถึงผิดพลาด");
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
            List.of(new Pair<>(4001716, 50), new Pair<>(4310266, 800), new Pair<>(4310308, 5000)));

    // Zenia put 10 value.. maybe change or remove?

    List<Long> bossTierUpgradeMesoList = List.of(
            10L, 10L, 10L, 10L, 10L, 10L, 10L, 10L);

    public void bossRank() {
        if (getPlayer().getBossTier() >= 8) {
            self.sayOk("ถึงระดับสูงสุดแล้ว ไม่สามารถเลื่อนขั้นได้อีกครับ");
            return;
        }

        if (getPlayer().getBossTier() <= 0) {
            getPlayer().setBossTier(1);
        }

        StringBuilder str = new StringBuilder(
                "#fs11##fc0xFF990033##eระบบเลื่อนขั้น Boss Rank#n#fc0xFF000000# ครับ\r\n");
        str.append("#bการเลื่อนขั้น Boss Rank#fc0xFF000000# จะทำให้คุณแข็งแกร่งขึ้น สนใจไหมครับ!?\r\n\r\n");
        str.append("#L0##bต้องการเลื่อนขั้นระดับถัดไป#l\r\n");
        str.append("#L1##bBoss Rank คืออะไรเหรอ?#l");
        int select = self.askMenu(str.toString());
        if (select == 1) {
            str = new StringBuilder("#fs11##fc0xFF990033#[Buff การเลื่อนขั้นแต่ละระดับ]\r\n\r\n");
            str.append("[1] ระดับ\r\n#bพลังโจมตีบอส + 10%\r\n#bจำนวนครั้งที่เข้าบอสได้ + 1\r\n\r\n");
            str.append("#fc0xFF990033#[Boss Rank 2]\r\n#bHard Lotus, Damien, Lucid เข้าได้\r\n\r\n");
            str.append("#fc0xFF990033#[Boss Rank 3]\r\n#bGuardian Angel Slime, Normal Will เข้าได้\r\n\r\n");
            str.append("#fc0xFF990033#[Boss Rank 4]\r\n#bNormal Dusk เข้าได้\r\n\r\n");
            str.append("#fc0xFF990033#[Boss Rank 5]\r\n#bHard Will, Dunkel เข้าได้\r\n\r\n");
            str.append("#fc0xFF990033#[Boss Rank 6]\r\n#bChaos Dusk, Verus Hilla เข้าได้\r\n\r\n");
            str.append("#fc0xFF990033#[Boss Rank 7]\r\n#bBlack Mage, Seren เข้าได้");
            self.sayOk(str.toString());
        } else {
            str = new StringBuilder("#fs11#ในการเลื่อนขั้นระดับถัดไป จำเป็นต้องใช้วัตถุดิบดังต่อไปนี้ครับ\r\n\r\n");
            for (var pair : bossTierUpgradeItemList.get(getPlayer().getBossTier())) {
                str.append("#i").append(pair.left).append("# #b#z").append(pair.left).append("# #r").append(pair.right)
                        .append(" ชิ้น#k\r\n");
            }
            str.append("\r\n#fs11##e#bต้องการเลื่อนขั้นจริงๆ ใช่ไหม?#k#n");
            int askType = self.askYesNo(str.toString());

            if (askType == 1) {
                for (var pair : bossTierUpgradeItemList.get(getPlayer().getBossTier())) {
                    if (!getPlayer().haveItem(pair.left, pair.right)) {
                        self.sayOk("#eวัตถุดิบ#n สำหรับเลื่อนขั้นไม่เพียงพอครับ");
                        return;
                    }
                }

                if (getPlayer().getMeso() < bossTierUpgradeMesoList.get(getPlayer().getBossTier())) {
                    self.sayOk("#eMeso#n สำหรับเลื่อนขั้นไม่เพียงพอครับ");
                    return;
                }

                if (Math.floor(Math.random() * 100) < 100) {
                    int nextTier = getPlayer().getBossTier() + 1;
                    Center.Broadcast.broadcastMessage(CField.chatMsg(8,
                            "[Boss Rank] คุณ " + getPlayer().getName() + " เลื่อนขั้นเป็นระดับ " + nextTier
                                    + " สำเร็จแล้ว"));
                    getPlayer().setBossTier(nextTier);
                    str = new StringBuilder("ยินดีด้วยครับ! เลื่อนขั้นสำเร็จแล้ว\r\n");
                    getPlayer().send(CField.showEffect("tdAnbur/idea_hyperMagic"));
                    self.sayOk(str.toString());
                } else {
                    self.sayOk("เลื่อนขั้นล้มเหลวครับ");
                }
                for (var pair : bossTierUpgradeItemList.get(getPlayer().getBossTier())) {
                    getPlayer().removeItem(pair.left, pair.right);
                }
                getPlayer().gainMeso(-bossTierUpgradeMesoList.get(getPlayer().getBossTier()), true);
            }
        }
    }

    List<Pair<String, Integer>> jobs = new ArrayList<>(Arrays.asList(
            new Pair<>("Hero", 112),
            new Pair<>("Paladin", 122),
            new Pair<>("Dark Knight", 132),
            new Pair<>("Archmage (Fire, Poison)", 212),
            new Pair<>("Archmage (Ice, Lightning)", 222),
            new Pair<>("Bishop", 232),
            new Pair<>("Bowmaster", 312),
            new Pair<>("Marksman", 322),
            new Pair<>("Pathfinder", 332),
            new Pair<>("Night Lord", 412),
            new Pair<>("Shadower", 422),
            new Pair<>("Dual Blade", 434),
            new Pair<>("Viper", 512),
            new Pair<>("Captain", 522),
            new Pair<>("Cannon Master", 532),
            new Pair<>("Soul Master", 1112),
            new Pair<>("Flame Wizard", 1212),
            new Pair<>("Wind Breaker", 1312),
            new Pair<>("Night Walker", 1412),
            new Pair<>("Striker", 1512),
            new Pair<>("Mihile", 5112),
            new Pair<>("Aran", 2112),
            new Pair<>("Evan", 2218),
            new Pair<>("Mercedes", 2312),
            new Pair<>("Phantom", 2412),
            new Pair<>("Eunwol", 2512),
            new Pair<>("Luminous", 2712),
            new Pair<>("Demon Slayer", 3112),
            new Pair<>("Demon Avenger", 3122),
            new Pair<>("Battle Mage", 3212),
            new Pair<>("Wild Hunter", 3312),
            new Pair<>("Mechanic", 3512),
            new Pair<>("Xenon", 3612),
            new Pair<>("Blaster", 3712),
            new Pair<>("Kaiser", 6112),
            new Pair<>("Kain", 6312),
            new Pair<>("Cadena", 6412),
            new Pair<>("Angelic Buster", 6512),
            new Pair<>("Kinesis", 14212),
            new Pair<>("Adele", 15112),
            new Pair<>("Illium", 15212),
            new Pair<>("Ark", 15512),
            new Pair<>("Hoyoung", 16412)));

    public void freechange_job() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        int v = self.askMenu(
                "เมนูย้ายสายอาชีพ (Zero ไม่สามารถทำได้!)\r\n\r\n#L0##bย้ายสายอาชีพ Adventure (#r#i4310086# #z4310086# ใช้ 1 ชิ้น)#b#l\r\n#L1#ย้ายสายอาชีพทุกอาชีพ#l");
        switch (v) {
            case 0: {
                if (getPlayer().getItemQuantity(4310086, false) <= 0) {
                    self.sayOk("ต้องมีเหรียญย้ายสายอาชีพจึงจะสามารถย้ายได้ครับ");
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
                        self.sayOk("สามารถใช้ได้เฉพาะกลุ่มอาชีพ Adventure เท่านั้นครับ");
                        return;
                }
                sendUIJobChange();
                break;
            }
            case 1: {
                if (getPlayer().getJob() == 10112) {
                    self.sayOk("Zero ไม่สามารถใช้บริการนี้ได้ครับ");
                    return;
                }
                int vv = self.askMenu(
                        "กรุณาเลือกเมนูย้ายสายอาชีพที่ต้องการครับ\r\n#b#L0#ย้ายสายอาชีพเลเวล 300 (Rebirth 235)#l\r\n#L1#ย้ายสายอาชีพใช้ Ganglim Credit#l\r\n#L2#ย้ายสายอาชีพใช้ 3 หมื่นล้าน Meso#l");
                switch (vv) {
                    case 0: { // 300 level change
                        if (getPlayer().getLevel() < 300) {
                            self.sayOk("ต้องมีเลเวล 300 ขึ้นไปถึงจะใช้ได้ครับ");
                            return;
                        }
                        String menu = "กรุณาเลือกอาชีพที่ต้องการจะย้ายไปครับ#b";
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
                            int yesNo = self.askYesNo("อาชีพที่เลือกคือ #r" + selectJob
                                    + "#k ถูกต้องไหมครับ?\r\n\r\n(หากกดยืนยัน จะทำการย้ายสายอาชีพและรีเซตเลเวลเป็น 235)");
                            if (yesNo == 1) {
                                for (VCore core : getPlayer().getVCoreSkillsNoLock()) {
                                    if (core.getState() == 2) {
                                        getPlayer().dropMessage(1, "กรุณาถอด V Skill Core ออกทั้งหมดก่อนลองใหม่ครับ");
                                        return;
                                    }
                                }
                                Equip test2 = null;
                                if (getPlayer().getJob() / 100 == 4) {
                                    test2 = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                            .getItem((short) -10);
                                }
                                if (test2 != null) {
                                    getPlayer().dropMessage(1, "กรุณาถอดอาวุธรอง/โล่/Blade ออกก่อนครับ");
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
                            self.sayOk("ต้องการ 30,000 Ganglim Credit ขึ้นไปครับ ปัจจุบันมี : " + realcash);
                            return;
                        }
                        if (self.askYesNo("ปัจจุบันมี Ganglim Credit " + realcash
                                + " ครับ ต้องการใช้ 30,000 Ganglim Credit เพื่อย้ายสายอาชีพหรือไม่ครับ?") == 1) {
                            String menu = "กรุณาเลือกอาชีพที่ต้องการจะย้ายไปครับ#b";
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
                                int yesNo = self.askYesNo(
                                        "อาชีพที่เลือกคือ #r" + selectJob
                                                + "#k ถูกต้องไหมครับ?\r\n\r\n(หากกดยืนยัน จะทำการย้ายสายอาชีพ)");
                                if (yesNo == 1) {
                                    for (VCore core : getPlayer().getVCoreSkillsNoLock()) {
                                        if (core.getState() == 2) {
                                            getPlayer().dropMessage(1,
                                                    "กรุณาถอด V Skill Core ออกทั้งหมดก่อนลองใหม่ครับ");
                                            return;
                                        }
                                    }
                                    Equip test2 = null;
                                    if (getPlayer().getJob() / 100 == 4) {
                                        test2 = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                                .getItem((short) -10);
                                    }
                                    if (test2 != null) {
                                        getPlayer().dropMessage(1, "กรุณาถอดอาวุธรอง/โล่/Blade ออกก่อนครับ");
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
                            self.sayOk("ต้องการ 3 หมื่นล้าน Meso ขึ้นไปครับ ปัจจุบันมี : " + meso);
                            return;
                        }
                        if (self.askYesNo("ปัจจุบันมี Meso " + meso
                                + " ครับ ต้องการใช้ 3 หมื่นล้าน Meso เพื่อย้ายสายอาชีพหรือไม่ครับ?") == 1) {
                            String menu = "กรุณาเลือกอาชีพที่ต้องการจะย้ายไปครับ#b";
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
                                int yesNo = self.askYesNo(
                                        "อาชีพที่เลือกคือ #r" + selectJob
                                                + "#k ถูกต้องไหมครับ?\r\n\r\n(หากกดยืนยัน จะทำการย้ายสายอาชีพ)");
                                if (yesNo == 1) {
                                    for (VCore core : getPlayer().getVCoreSkillsNoLock()) {
                                        if (core.getState() == 2) {
                                            getPlayer().dropMessage(1,
                                                    "กรุณาถอด V Skill Core ออกทั้งหมดก่อนลองใหม่ครับ");
                                            return;
                                        }
                                    }
                                    Equip test2 = null;
                                    if (getPlayer().getJob() / 100 == 4) {
                                        test2 = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                                .getItem((short) -10);
                                    }
                                    if (test2 != null) {
                                        getPlayer().dropMessage(1, "กรุณาถอดอาวุธรอง/โล่/Blade ออกก่อนครับ");
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
            getPlayer().changeJob(vvv, true); // Job Change
            for (int i = 0; i < (getPlayer().getJob() % 10) + 1; i++) {
                int job = ((i + 1) == ((getPlayer().getJob() % 10) + 1))
                        ? getPlayer().getJob() - (getPlayer().getJob() % 100)
                        : getPlayer().getJob() - (i + 1);
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
                getPlayer().maxSkillByID(25001000); // Mega Punch
                getPlayer().maxSkillByID(25001002); // Fox Trot
            } else if (GameConstants.isArk(job)) {
                div = 15001;
                getPlayer().maxSkillByID(155101006); // Spell Control
            }
            getPlayer().maxskill((getPlayer().getJob() / div) * div);
            // Beginner Skill
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
                int skills[] = new int[] { 20031208, 20030190, 20031203, 20031205, 20030206, 20031207, 20031209,
                        20031251, 20031260 };
                for (int skill : skills) {
                    getPlayer().maxSkillByID(skill);
                }
            }
            if (GameConstants.isLuminous(getPlayer().getJob())) {
                int skills[] = new int[] { 20040216, 20040217, 20040218, 20040219, 20040221, 20041222 };
                for (int skill : skills) {
                    getPlayer().maxSkillByID(skill);
                }
            }
            if (GameConstants.isAngelicBuster(getPlayer().getJob())) {
                int skills[] = new int[] { 60011216, 60010217, 60011218, 60011219, 60011220, 60011222 };
                for (int skill : skills) {
                    getPlayer().maxSkillByID(skill);
                }
            }
            if (GameConstants.isXenon(getPlayer().getJob())) {
                int skills[] = new int[] { 30020232, 30020233, 30020234, 30020240 };
                for (int skill : skills) {
                    getPlayer().maxSkillByID(skill);
                }
            }
            if (GameConstants.isEunWol(getPlayer().getJob())) {
                int skills[] = new int[] { 20051284, 20050285, 20050286 };
                for (int skill : skills) {
                    getPlayer().maxSkillByID(skill);
                }
            }
            if (GameConstants.isKinesis(getPlayer().getJob())) {
                int skills[] = new int[] { 140000291 };
                for (int skill : skills) {
                    getPlayer().maxSkillByID(skill);
                }
            }
            if (GameConstants.isArk(getPlayer().getJob())) {
                int skills[] = new int[] { 150010079 };
                for (int skill : skills) {
                    getPlayer().maxSkillByID(skill);
                }
            }
            if (GameConstants.isHoyoung(getPlayer().getJob())) {
                int skills[] = new int[] { 160001075, 164001004 };
                for (int skill : skills) {
                    getPlayer().maxSkillByID(skill);
                }
            }
            if (GameConstants.isAdele(getPlayer().getJob())) {
                int skills[] = new int[] { 150020079 };
                for (int skill : skills) {
                    getPlayer().maxSkillByID(skill);
                }
            }
            if (GameConstants.isLara(getPlayer().getJob())) {
                int skills[] = new int[] { 160011075 };
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
            // Auto Job Advancement Prevention
            getPlayer().updateOneInfo(122870, "AutoJob", String.valueOf(getPlayer().getJob() / 10 * 10));
            getPlayer().maxskill(getPlayer().getJob());

            getPlayer().getStat().recalcLocalStats(getPlayer());

            // Union Change
            getPlayer().firstLoadMapleUnion();
            getPlayer().sendUnionPacket();
            getPlayer().dropMessage(1, "ย้ายสายอาชีพเสร็จสมบูรณ์ครับ");
        } catch (Exception e) {
            DiscordBotHandler.requestSendTelegram(e.getMessage());
            System.out.println("เกิดข้อผิดพลาดในการย้ายสายอาชีพ ส่งข้อความแล้ว");
        }
    }

    public void sendUIJobChange() {
        if (getPlayer().getJob() / 1000 != 0) {
            getPlayer().dropMessage(1, "การย้ายสายอาชีพทำได้เฉพาะกลุ่มอาชีพ Adventure เท่านั้นครับ");
            return;
        }
        for (VCore core : getPlayer().getVCoreSkillsNoLock()) {
            if (core.getState() == 2) {
                getPlayer().dropMessage(1, "กรุณาถอด V Skill Core ออกทั้งหมดก่อนลองใหม่ครับ");
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
            getPlayer().dropMessage(1, "สายอาชีพโจร กรุณาถอดอาวุธรอง/โล่/Blade ออกก่อนครับ");
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
        GradeRandomOption[] options = { GradeRandomOption.Red, GradeRandomOption.Black, GradeRandomOption.Additional };
        String cubeTotalInfo = "#fs11#ตรวจสอบ #bจำนวนครั้งการใช้ Cube ที่เหลือ#k เพื่อเลื่อนระดับ Cube ครับ\r\n"
                + "ค่าดังกล่าวจะถูกรีเซตเมื่อเลื่อนระดับ และ #bแชร์ภายในบัญชี#k ครับ\r\n\r\n";
        for (GradeRandomOption option : options) {
            String cubeString = "";
            switch (option) {
                case Red:
                    cubeString = "Red Cube";
                    break;
                case Black:
                    cubeString = "Black Cube";
                    break;
                case Additional:
                    cubeString = "Additional Cube";
                    break;
                default:
                    break;
            }
            cubeTotalInfo += "#e[" + cubeString + "]#n\r\n";
            String[] levelUps = { "RtoE", "EtoU", "UtoL" };
            for (String levelUp : levelUps) {
                int tryCount = getPlayer().getOneInfoQuestInteger(QuestExConstants.CubeLevelUp.getQuestID(),
                        option.toString() + levelUp);
                int grade = 1;
                String gradeString = "";
                if (levelUp.equals("RtoE")) {
                    grade = 1;
                    gradeString = "Rare -> Epic";
                } else if (levelUp.equals("EtoU")) {
                    grade = 2;
                    gradeString = "Epic -> Unique";
                } else if (levelUp.equals("UtoL")) {
                    grade = 3;
                    gradeString = "Unique -> Legendary";
                }
                int levelUpCount = GameConstants.getCubeLevelUpCount(option, grade);
                cubeTotalInfo += gradeString + " สะสม " + tryCount + " ครั้ง จาก " + levelUpCount + " ครั้ง\r\n";
            }
            if (option != GradeRandomOption.AmazingAdditional) {
                cubeTotalInfo += "\r\n";
            }
        }
        self.sayOk(cubeTotalInfo);
    }

}