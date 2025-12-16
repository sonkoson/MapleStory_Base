package script.Util;

import constants.GameConstants;
import constants.ServerConstants;
import database.DBConfig;
import network.SendPacketOpcode;
import network.encode.PacketEncoder;
import objects.contents.ContentsManager;
import objects.contents.SpeedLadder;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.item.MapleItemInformationProvider;
import objects.users.MapleStat;
import objects.users.looks.zero.ZeroInfoFlag;
import objects.utils.Pair;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class Util extends ScriptEngineNPC {

    List<Integer> specialHair = new ArrayList<>(Arrays.asList(
            32700, 32710, 32720, 32730, 32740, 32750, 32760, 32770, 32780, 32790, 32800, 32810, 32820, 32830, 32860,
            32870, 32880, 32890, 32890, 32900, 32910,
            32920, 32930, 32940, 32950, 32960, 32970, 32980, 32990, 39100, 39110, 39120, 39130, 39140, 39150, 39160,
            39170, 45320, 45330, 45340, 45350, 45360,
            45370, 45380, 45390, 45400, 45410, 45420, 45430, 45440, 45450, 45460, 45470, 45480, 45490, 45500, 45510,
            45520, 45530, 45540, 45550, 45560, 45570,
            45580, 45590, 45600, 45610, 45620, 45630, 45640, 45650, 45660, 45670, 45680, 45690, 45700, 45710, 45720,
            45730, 45740, 45750, 45760, 45770, 45780,
            45790, 45800, 45810, 45820, 45830, 45840, 45850, 45860, 45870, 45880, 45890, 45900, 45910, 45920, 45930,
            45940, 45950, 45960, 45970, 45980, 45990,
            39180, 39270, 39280, 39290, 39470, 39480, 39490, 39790, 39800, 39810, 39820, 39830, 39840, 39850, 39860,
            39870, 39880, 39890, 39910, 47810, 47820,
            39000, 39010, 39020, 39030, 39450, 39460, 39300, 39310, 39320, 39330, 39340, 39350, 39360, 39370, 39380,
            39390, 39400, 39410, 39420, 39430, 39440,
            39500, 39510, 39520, 39530, 39540, 39550, 39560, 39570, 39580, 39590, 39600, 39610, 39620, 39630, 39640,
            39650, 39660, 39670, 39680, 39690, 39700,
            39710, 39720, 39730, 39740, 39750, 39760, 39770, 39780, 39900, 39000, 39010, 39020, 39030, 39040, 39050,
            39060, 39070, 39080, 39100, 39110, 39120, 39130, 39140, 39150, 39160, 39170, 39180, 39190, 39200, 39210,
            39500, 39510, 39520, 39530, 39540, 39550, 39560, 39570, 39580, 39590));

    DecimalFormat decFormat = new DecimalFormat("###,###");

    public void searchHairAndFace() {
        initNPC(MapleLifeFactory.getNPC(9000172));
        int vv = self.askMenu(
                "     #fUI/UIWindow2.img/Script/Title/1#\r\n#e<ค้นหาแฟชั่น>#n\r\n#b#h0##k ลองเปลี่ยนทรงผมและหน้าตาผ่านการ #rค้นหา#k ดูสิ\r\n#b#L0#ค้นหาทรงผม#l\r\n#L1#ค้นหาหน้าตา#l");
        String v = "";
        if (vv == 0) {
            v = self.askText("กรุณาใส่ชื่อทรงผมที่ต้องการค้นหา (ใส่บางส่วนก็ได้)", ScriptMessageFlag.NpcReplacedByNpc);
        } else {
            v = self.askText("กรุณาใส่ชื่อหน้าตาที่ต้องการค้นหา (ใส่บางส่วนก็ได้)", ScriptMessageFlag.NpcReplacedByNpc);
        }
        if (v.equals("")) {
            return;
        }
        List<Integer> items = new ArrayList<>();
        MapleItemInformationProvider mii = MapleItemInformationProvider.getInstance();
        switch (vv) {
            case 0: { // Hair Search

                String itemName = null;
                for (Pair<Integer, String> item : mii.getAllEquips()) {
                    int itemid = item.getLeft();
                    if (itemid / 10000 == 3 || itemid / 10000 == 4 || itemid / 10000 == 6) {
                        if (specialHair.contains(itemid) || itemid % 10 != 0) {
                            continue;
                        }
                        itemName = item.getRight();
                        if (itemName.replace(" ", "").contains(v) || itemName.contains(v)
                                || itemName.contains(v.replace(" ", "")) ||
                                itemName.replace(" ", "").contains(v.replace(" ", ""))) {
                            items.add(itemid);
                        }
                    }
                }
                break;
            }

            case 1: { // Face Search
                String itemName = null;
                for (Pair<Integer, String> item : mii.getAllEquips()) {
                    int itemid = item.getLeft();
                    if (itemid / 10000 == 2 || itemid / 10000 == 5) {
                        itemName = item.getRight();
                        if (String.valueOf(itemid).charAt(2) == '0') {
                            if (itemName.replace(" ", "").contains(v) || itemName.contains(v)
                                    || itemName.contains(v.replace(" ", "")) ||
                                    itemName.replace(" ", "").contains(v.replace(" ", ""))) {
                                items.add(itemid);
                            }
                        }
                    }
                }
                break;
            }
        }
        if (items.size() > 60) {
            self.sayOk(
                    "ข้อมูลการค้นหาเยอะเกินไปไม่สามารถแสดงได้ครบ กรุณาระบุคำค้นหาให้ชัดเจนกว่านี้\r\n[ตัวอย่าง : (Rariel Hair) Rari x Rariel o]");
        } else if (items.size() > 0) {
            String list = "ด้านล่างคือผลการค้นหา กรุณาเลือกรายการที่ต้องการ#b\r\n";
            /*
             * for (int i = 0; i < items.size(); i++) {
             * list += "#L" + i + "#" + "#z" + items.get(i) + "#\r\n";
             * }
             */
            for (int i = 0; i < items.size(); i++) {
                String type = "Hair";
                String type2 = "hair";
                if (vv == 1) {
                    type = "Face";
                    type2 = "face";
                }
                list += "#L" + i + "#" + "#fCharacter/" + type + "/000" + items.get(i) + ".img/default/" + type2
                        + "# #e#z" + items.get(i) + "##n#l\r\n";
            }
            int vvv = self.askMenu(list);
            if (vvv > -1) {
                int az = 0;
                if (GameConstants.isAngelicBuster(getPlayer().getJob())) {
                    if (1 == self.askYesNo("ต้องการใช้งานกับโหมด Dress-up หรือไม่?")) {
                        az = 1;
                    }
                }
                if (GameConstants.isZero(getPlayer().getJob())) {
                    if (1 == self.askYesNo("ต้องการใช้งานกับ Beta หรือไม่? (#r#eไม่ใช่#n#k เพื่อใช้งานกับ Alpha)")) {
                        az = 1;
                    }
                }
                String v0 = "ต้องการเปลี่ยนเป็น #r#z" + items.get(vvv) + "##k จริงหรือไม่?\r\n#e";
                if (vv == 0) {
                    v0 += "\r\nด้านหน้า -\r\n#fCharacter/Hair/000" + items.get(vvv)
                            + ".img/default/hair#\r\nด้านหลัง -\r\n#fCharacter/Hair/000" + items.get(vvv)
                            + ".img/backDefault/backHair#";
                } else {
                    v0 += "\r\n#fCharacter/Face/000" + items.get(vvv) + ".img/default/face#";
                }
                if (1 == self.askYesNo(v0)) {

                    if (items.get(vvv) < 30000 || items.get(vvv) >= 50000 && items.get(vvv) <= 59999) {
                        if (GameConstants.isAngelicBuster(getPlayer().getJob()) && az > 0) {
                            getPlayer().setSecondFace(items.get(vvv));
                            getPlayer().fakeRelog();
                        } else if (GameConstants.isZero(getPlayer().getJob()) && az > 0) {
                            getPlayer().getZeroInfo().setSubFace(items.get(vvv));
                            getPlayer().getZeroInfo().sendUpdateZeroInfo(getPlayer(), ZeroInfoFlag.SubFace);
                        } else {
                            getPlayer().setFace(items.get(vvv));
                            getPlayer().updateSingleStat(MapleStat.FACE, items.get(vvv));
                        }
                    } else {
                        // Reset Mix Dye
                        getPlayer().setBaseColor(-1);
                        getPlayer().setAddColor(0);
                        getPlayer().setBaseProb(0);
                        if (GameConstants.isAngelicBuster(getPlayer().getJob()) && az > 0) {
                            getPlayer().setSecondHair(items.get(vvv));
                            getPlayer().fakeRelog();
                        } else if (GameConstants.isZero(getPlayer().getJob()) && az > 0) {
                            getPlayer().getZeroInfo().setSubHair(items.get(vvv));
                            getPlayer().getZeroInfo().sendUpdateZeroInfo(getPlayer(), ZeroInfoFlag.SubHair);
                        } else {
                            getPlayer().setHair(items.get(vvv));
                            getPlayer().updateSingleStat(MapleStat.HAIR, items.get(vvv));
                        }
                    }
                    getPlayer().equipChanged();
                }
            } else {
                self.sayOk("ไม่พบผลการค้นหา");
            }
        }
    }

    public void go_pcmap() {
        long time = (ContentsManager.SpeedLadderGame.getCurrentGameStartTime() + 300000) - System.currentTimeMillis();
        long minute = TimeUnit.MILLISECONDS.toMinutes(time);
        long second = TimeUnit.MILLISECONDS.toSeconds(time % 60000);
        String recordToString = minute + "นาที " + second + "วินาที";
        int selection = -1;

        if (true) {
            self.say("#h0# สวัสดีจ้ะ\r\nฉันคือคอมพิวเตอร์ที่ดูแลระบบ #bเกมไต่บันได#k ในเซิร์ฟเวอร์ "
                    + ServerConstants.serverName
                    + "\r\n\r\nขณะนี้ไม่สามารถใช้งานฟังก์ชันดังกล่าวได้");
            return;
        }
        if (!canGame()) {
            String text = "#h0# สวัสดีจ้ะ\r\nฉันคือคอมพิวเตอร์ที่ดูแลระบบ #bเกมไต่บันได#k ในเซิร์ฟเวอร์ "
                    + ServerConstants.serverName + "\r\n\r\n#r#eผลลัพธ์รอบที่ "
                    + ContentsManager.SpeedLadderGame.getCurrentRound() + " จะออกในอีก " + recordToString + "#n#k" +
                    "\r\n\r\n";
            if (getPlayer().getOneInfoQuestInteger(777777, "round") == 0) {
                text += "#b#L0#ฉันต้องการลงเดิมพันเกมไต่บันได #r(เวลาน้อยกว่า 30 วินาที ไม่สามารถเดิมพันได้)#b#l\r\n";
            } else {
                int type = getPlayer().getOneInfoQuestInteger(777777, "type");
                int flag = getPlayer().getOneInfoQuestInteger(777777, "flag");
                text += "#bสถานะการเดิมพันของ #h0# : " + getBetString(type, flag) + "#k\r\n";
            }
            text += "#L10#ฉันต้องการรับรางวัลเดิมพัน#l\r\n" +
                    "#L11#ฉันต้องการฟังคำอธิบายเกี่ยวกับเกมไต่บันได#l";
            selection = self.askMenu(text);

        } else if (checkReward()) {
            selection = self.askMenu("#h0# สวัสดีจ้ะ\r\nฉันคือคอมพิวเตอร์ที่ดูแลระบบ #bเกมไต่บันได#k ในเซิร์ฟเวอร์ "
                    + ServerConstants.serverName
                    + "\r\n\r\n#r#eผลลัพธ์รอบที่ " + ContentsManager.SpeedLadderGame.getCurrentRound()
                    + " จะออกในอีก " + recordToString + "#n#k" +
                    "\r\n\r\n" +
                    "#L10##b#eฉันต้องการรับรางวัลเดิมพัน#k#n#l\r\n" +
                    "#L11#ฉันต้องการฟังคำอธิบายเกี่ยวกับเกมไต่บันได#l");
        } else {
            String text = "#h0# ยินดีต้อนรับครับ\r\nผมคือคอมพิวเตอร์ที่ดูแลระบบ #bเกมไต่บันได#k ประจำเซิร์ฟเวอร์ "
                    + ServerConstants.serverName + "\r\n\r\n#r#eผลลัพธ์เกมไต่บันไดรอบที่ "
                    + ContentsManager.SpeedLadderGame.getCurrentRound() + " จะออกในอีก " + recordToString + "#n#k" +
                    "\r\n\r\n";
            if (getPlayer().getOneInfoQuestInteger(777777, "round") == 0) {
                text += "#b#L0#ฉันต้องการลงเดิมพันเกมไต่บันได#l\r\n";
            } else {
                if (getPlayer().getOneInfoQuestInteger(777777, "round") == ContentsManager.SpeedLadderGame
                        .getCurrentRound()) {
                    int type = getPlayer().getOneInfoQuestInteger(777777, "type");
                    int flag = getPlayer().getOneInfoQuestInteger(777777, "flag");
                    text += "#bสถานะการเดิมพันของ #h0# : " + getBetString(type, flag) + "#k\r\n";
                } else {
                    text += "#b#L0#ฉันต้องการลงเดิมพันเกมไต่บันได#l\r\n";
                }
            }
            text += "#L10#ฉันต้องการรับรางวัลเดิมพัน#l\r\n" +
                    "#L11#ฉันต้องการฟังคำอธิบายเกี่ยวกับเกมไต่บันได#l";
            selection = self.askMenu(text);
        }
        String canBet = "\r\nเงินที่มีปัจจุบัน : " + decFormat.format(getPlayer().getMeso()) + " Meso";
        switch (selection) {
            case 0: // Start Left
                if (checkReward()) {
                    self.sayOk("ยังมีรางวัลที่ยังไม่ได้กดรับ กรุณากดรับรางวัลก่อน");
                    return;
                }
                if (getPlayer().getOneInfoQuestInteger(777777, "round") == ContentsManager.SpeedLadderGame
                        .getCurrentRound()) {
                    self.sayOk("คุณได้ลงเดิมพันในรอบนี้ไปแล้ว");
                    return;
                }
                if (!canGame()) {
                    self.sayOk("เหลือเวลาน้อยกว่า 30 วินาที ไม่สามารถลงเดิมพันได้");
                    return;
                }
                int v = self.askMenu("<เมนูการเดิมพัน>#k#n\r\n\r\n#r#eผลลัพธ์รอบที่ "
                        + ContentsManager.SpeedLadderGame.getCurrentRound()
                        + " จะออกในอีก " + recordToString + "#n#k" +
                        "\r\n\r\n" +
                        "#b#L0#บันได (เริ่ม [ซ้าย] x1.85)#l\r\n" +
                        "#L1#บันได (เริ่ม [ขวา] x1.85)#l\r\n" +
                        "#L2#บันได (ขีด [4 ขีด] x1.85)#l\r\n" +
                        "#L3#บันได (ขีด [3 ขีด] x1.85)#l\r\n" +
                        "#L4#บันได (ผลลัพธ์ [คี่] x1.85)#l\r\n" +
                        "#L5#บันได (ผลลัพธ์ [คู่] x1.85)#l\r\n" +
                        "#L6#บันได (เริ่ม+ขีด [ซ้าย 4 ขีด] x3.6)#l\r\n" +
                        "#L7#บันได (เริ่ม+ขีด [ซ้าย 3 ขีด] x3.6)#l\r\n" +
                        "#L8#บันได (เริ่ม+ขีด [ขวา 4 ขีด] x3.6)#l\r\n" +
                        "#L9#บันได (เริ่ม+ขีด [ขวา 3 ขีด] x3.6)#l");
                if (v == 0 || v == 1) {
                    String reward;
                    if (v == 0) {
                        reward = self.askText(
                                "#e#bเริ่ม [ซ้าย] x1.85#n#k ที่ท่านเลือก\r\nต้องการลงเดิมพันเท่าไหร่? [ปัจจุบันสามารถใช้ Meso ได้เท่านั้น]"
                                        + canBet);
                    } else {
                        reward = self.askText(
                                "#e#bเริ่ม [ขวา] x1.85#n#k ที่ท่านเลือก\r\nต้องการลงเดิมพันเท่าไหร่? [ปัจจุบันสามารถใช้ Meso ได้เท่านั้น]"
                                        + canBet);
                    }
                    String pattern = "^[0-9]*$"; // Numbers only
                    boolean regex = Pattern.matches(pattern, reward);
                    if (!regex) {
                        getPlayer().dropMessage(1, "กรุณาใส่เฉพาะตัวเลขเท่านั้น");
                        return;
                    }
                    if (Long.parseLong(reward) > getPlayer().getMeso()) {
                        self.sayOk("คำขอไม่ถูกต้อง");
                        return;
                    }
                    if (Long.parseLong(reward) <= 0) {
                        if (Long.parseLong(reward) == 0) {
                            self.sayOk("กรุณาใส่จำนวน 1 ขึ้นไป");
                        }
                        return;
                    }
                    // type : 1 : Start Only, 2 : Lines , 3 : Result , 4 : Start + Lines
                    // flag : 1 : Right, 3 lines, Odd
                    // flag : 2 : Left, 4 lines, Even
                    // flag : 4 : Left 4
                    // flag : 8 : Left 3
                    // flag : 16 : Right 4
                    // flag : 32 : Right 3
                    if (!canGame()) {
                        self.sayOk("เหลือเวลาไม่ถึง 30 วินาที ไม่สามารถลงเดิมพันได้");
                        return;
                    }
                    getPlayer().updateOneInfo(777777, "round",
                            String.valueOf(ContentsManager.SpeedLadderGame.getCurrentRound()));
                    getPlayer().updateOneInfo(777777, "type", "1");
                    if (v == 0) { // Left Start
                        ContentsManager.SpeedLadderGame.addBetMan(getPlayer(), (byte) 1, (byte) 2,
                                Long.parseLong(reward));
                        getPlayer().updateOneInfo(777777, "flag", "2");
                    } else { // Right Start
                        ContentsManager.SpeedLadderGame.addBetMan(getPlayer(), (byte) 1, (byte) 1,
                                Long.parseLong(reward));
                        getPlayer().updateOneInfo(777777, "flag", "1");
                    }
                    getPlayer().updateOneInfo(777777, "reward", reward);
                    getPlayer().gainMeso(-Long.parseLong(reward), false);
                } else if (v == 2 || v == 3) {
                    String reward;
                    if (v == 2) {
                        reward = self.askText(
                                "#e#bขีด [4] x1.85#n#k ที่ท่านเลือก\r\nต้องการลงเดิมพันเท่าไหร่? [ปัจจุบันสามารถใช้ Meso ได้เท่านั้น]"
                                        + canBet);
                    } else {
                        reward = self.askText(
                                "#e#bขีด [3] x1.85#n#k ที่ท่านเลือก\r\nต้องการลงเดิมพันเท่าไหร่? [ปัจจุบันสามารถใช้ Meso ได้เท่านั้น]"
                                        + canBet);
                    }
                    String pattern = "^[0-9]*$"; // Numbers only
                    boolean regex = Pattern.matches(pattern, reward);
                    if (!regex) {
                        getPlayer().dropMessage(1, "กรุณาระบุเฉพาะตัวเลขเท่านั้น");
                        return;
                    }
                    if (Long.parseLong(reward) > getPlayer().getMeso()) {
                        self.sayOk("คำขอไม่ถูกต้อง");
                        return;
                    }
                    if (Long.parseLong(reward) <= 0) {
                        if (Long.parseLong(reward) == 0) {
                            self.sayOk("กรุณาระบุจำนวน 1 ขึ้นไป");
                        }
                        return;
                    }
                    // type : 1 : Start Only, 2 : Lines , 3 : Result , 4 : Start + Lines
                    // flag : 1 : Right, 3 lines, Odd
                    // flag : 2 : Left, 4 lines, Even
                    // flag : 4 : Left 4
                    // flag : 8 : Left 3
                    // flag : 16 : Right 4
                    // flag : 32 : Right 3
                    if (!canGame()) {
                        self.sayOk("เหลือเวลาไม่ถึง 30 วินาที ไม่สามารถลงเดิมพันได้");
                        return;
                    }
                    getPlayer().updateOneInfo(777777, "round",
                            String.valueOf(ContentsManager.SpeedLadderGame.getCurrentRound()));
                    getPlayer().updateOneInfo(777777, "type", "2");
                    if (v == 2) { // 4 Lines
                        ContentsManager.SpeedLadderGame.addBetMan(getPlayer(), (byte) 2, (byte) 2,
                                Long.parseLong(reward));
                        getPlayer().updateOneInfo(777777, "flag", "2");
                    } else { // 3 Lines
                        ContentsManager.SpeedLadderGame.addBetMan(getPlayer(), (byte) 2, (byte) 1,
                                Long.parseLong(reward));
                        getPlayer().updateOneInfo(777777, "flag", "1");
                    }
                    getPlayer().updateOneInfo(777777, "reward", reward);
                    getPlayer().gainMeso(-Long.parseLong(reward), false);
                } else if (v == 4 || v == 5) {
                    String reward;
                    if (v == 4) {
                        reward = self.askText(
                                "#e#b[ผลลัพธ์ : คี่] x1.85#n#k ที่ท่านเลือก\r\nต้องการลงเดิมพันเท่าไหร่? [ปัจจุบันสามารถใช้ Meso ได้เท่านั้น]"
                                        + canBet);
                    } else {
                        reward = self.askText(
                                "#e#b[ผลลัพธ์ : คู่] x1.85#n#k ที่ท่านเลือก\r\nต้องการลงเดิมพันเท่าไหร่? [ปัจจุบันสามารถใช้ Meso ได้เท่านั้น]"
                                        + canBet);
                    }
                    String pattern = "^[0-9]*$"; // Numbers only
                    boolean regex = Pattern.matches(pattern, reward);
                    if (!regex) {
                        getPlayer().dropMessage(1, "กรุณาระบุเฉพาะตัวเลขเท่านั้น");
                        return;
                    }
                    if (Long.parseLong(reward) > getPlayer().getMeso()) {
                        self.sayOk("คำขอไม่ถูกต้อง");
                        return;
                    }
                    if (Long.parseLong(reward) <= 0) {
                        if (Long.parseLong(reward) == 0) {
                            self.sayOk("กรุณาระบุจำนวน 1 ขึ้นไป");
                        }
                        return;
                    }
                    // type : 1 : Start Only, 2 : Lines , 3 : Result , 4 : Start + Lines
                    // flag : 1 : Right, 3 lines, Odd
                    // flag : 2 : Left, 4 lines, Even
                    // flag : 4 : Left 4
                    // flag : 8 : Left 3
                    // flag : 16 : Right 4
                    // flag : 32 : Right 3
                    if (!canGame()) {
                        self.sayOk("เหลือเวลาไม่ถึง 30 วินาที ไม่สามารถลงเดิมพันได้");
                        return;
                    }
                    getPlayer().updateOneInfo(777777, "round",
                            String.valueOf(ContentsManager.SpeedLadderGame.getCurrentRound()));
                    getPlayer().updateOneInfo(777777, "type", "3");
                    if (v == 4) { // Odd
                        ContentsManager.SpeedLadderGame.addBetMan(getPlayer(), (byte) 3, (byte) 1,
                                Long.parseLong(reward));
                        getPlayer().updateOneInfo(777777, "flag", "1");
                    } else { // Even
                        ContentsManager.SpeedLadderGame.addBetMan(getPlayer(), (byte) 3, (byte) 2,
                                Long.parseLong(reward));
                        getPlayer().updateOneInfo(777777, "flag", "2");
                    }
                    getPlayer().updateOneInfo(777777, "reward", reward);
                    getPlayer().gainMeso(-Long.parseLong(reward), false);
                } else if (v == 6 || v == 7) {
                    String reward = "";
                    if (v == 6) {
                        reward = self.askText(
                                "#e#b[เริ่ม+ขีด ซ้าย4] x3.6#n#k ที่ท่านเลือก\r\nต้องการลงเดิมพันเท่าไหร่? [ปัจจุบันสามารถใช้ Meso ได้เท่านั้น]"
                                        + canBet);
                    } else {
                        reward = self.askText(
                                "#e#b[เริ่ม+ขีด ซ้าย3] x3.6#n#k ที่ท่านเลือก\r\nต้องการลงเดิมพันเท่าไหร่? [ปัจจุบันสามารถใช้ Meso ได้เท่านั้น]"
                                        + canBet);
                    }
                    String pattern = "^[0-9]*$"; // Numbers only
                    boolean regex = Pattern.matches(pattern, reward);
                    if (!regex) {
                        getPlayer().dropMessage(1, "กรุณาระบุเฉพาะตัวเลขเท่านั้น");
                        return;
                    }
                    if (Long.parseLong(reward) > getPlayer().getMeso()) {
                        self.sayOk("คำขอไม่ถูกต้อง");
                        return;
                    }
                    if (Long.parseLong(reward) <= 0) {
                        if (Long.parseLong(reward) == 0) {
                            self.sayOk("กรุณาระบุจำนวน 1 ขึ้นไป");
                        }
                        return;
                    }
                    // type : 1 : Start Only, 2 : Lines , 3 : Result , 4 : Start + Lines
                    // flag : 1 : Right, 3 lines, Odd
                    // flag : 2 : Left, 4 lines, Even
                    // flag : 4 : Left 4
                    // flag : 8 : Left 3
                    // flag : 16 : Right 4
                    // flag : 32 : Right 3
                    if (!canGame()) {
                        self.sayOk("เหลือเวลาไม่ถึง 30 วินาที ไม่สามารถลงเดิมพันได้");
                        return;
                    }
                    getPlayer().updateOneInfo(777777, "round",
                            String.valueOf(ContentsManager.SpeedLadderGame.getCurrentRound()));
                    getPlayer().updateOneInfo(777777, "type", "4");
                    if (v == 6) { // Left 4
                        ContentsManager.SpeedLadderGame.addBetMan(getPlayer(), (byte) 4, (byte) 4,
                                Long.parseLong(reward));
                        getPlayer().updateOneInfo(777777, "flag", "4");
                    } else { // Left 3
                        ContentsManager.SpeedLadderGame.addBetMan(getPlayer(), (byte) 4, (byte) 8,
                                Long.parseLong(reward));
                        getPlayer().updateOneInfo(777777, "flag", "8");
                    }
                    getPlayer().updateOneInfo(777777, "reward", reward);
                    getPlayer().gainMeso(-Long.parseLong(reward), false);
                } else if (v == 8 || v == 9) {
                    String reward;
                    if (v == 8) {
                        reward = self.askText(
                                "#e#b[เริ่ม+ขีด ขวา4] x3.6#n#k ที่ท่านเลือก\r\nต้องการลงเดิมพันเท่าไหร่? [ปัจจุบันสามารถใช้ Meso ได้เท่านั้น]"
                                        + canBet);
                    } else {
                        reward = self.askText(
                                "#e#b[เริ่ม+ขีด ขวา3] x3.6#n#k ที่ท่านเลือก\r\nต้องการลงเดิมพันเท่าไหร่? [ปัจจุบันสามารถใช้ Meso ได้เท่านั้น]"
                                        + canBet);
                    }
                    String pattern = "^[0-9]*$"; // Numbers only
                    boolean regex = Pattern.matches(pattern, reward);
                    if (!regex) {
                        getPlayer().dropMessage(1, "กรุณาระบุเฉพาะตัวเลขเท่านั้น");
                        return;
                    }
                    if (Long.parseLong(reward) > getPlayer().getMeso()) {
                        self.sayOk("คำขอไม่ถูกต้อง");
                        return;
                    }
                    if (Long.parseLong(reward) <= 0) {
                        if (Long.parseLong(reward) == 0) {
                            self.sayOk("กรุณาระบุจำนวน 1 ขึ้นไป");
                        }
                        return;
                    }
                    // type : 1 : Start Only, 2 : Lines , 3 : Result , 4 : Start + Lines
                    // flag : 1 : Right, 3 lines, Odd
                    // flag : 2 : Left, 4 lines, Even
                    // flag : 4 : Left 4
                    // flag : 8 : Left 3
                    // flag : 16 : Right 4
                    // flag : 32 : Right 3
                    if (!canGame()) {
                        self.sayOk("เหลือเวลาไม่ถึง 30 วินาที ไม่สามารถลงเดิมพันได้");
                        return;
                    }
                    getPlayer().updateOneInfo(777777, "round",
                            String.valueOf(ContentsManager.SpeedLadderGame.getCurrentRound()));
                    getPlayer().updateOneInfo(777777, "type", "4");
                    if (v == 8) { // Right 4
                        getPlayer().updateOneInfo(777777, "flag", "16");
                        ContentsManager.SpeedLadderGame.addBetMan(getPlayer(), (byte) 4, (byte) 16,
                                Long.parseLong(reward));
                    } else { // Right 3
                        ContentsManager.SpeedLadderGame.addBetMan(getPlayer(), (byte) 4, (byte) 32,
                                Long.parseLong(reward));
                        getPlayer().updateOneInfo(777777, "flag", "32");
                    }
                    getPlayer().updateOneInfo(777777, "reward", reward);
                    getPlayer().gainMeso(-Long.parseLong(reward), false);
                }
                self.sayOk("ลงเดิมพันเรียบร้อยแล้ว");
                break;
            case 10:
                if (checkReward()) {
                    int type = getPlayer().getOneInfoQuestInteger(777777, "type");
                    long reward = getPlayer().getOneInfoQuestLong(777777, "reward");
                    getPlayer().updateOneInfo(777777, "round", "0");
                    getPlayer().updateOneInfo(777777, "type", "0");
                    getPlayer().updateOneInfo(777777, "flag", "0");
                    if (type < 4) {
                        getPlayer().gainMeso((long) (reward * 1.85), false);
                        self.sayOk(decFormat.format((long) (reward * 1.85)) + " Meso ได้รับเรียบร้อยแล้ว");
                    } else {
                        getPlayer().gainMeso((long) (reward * 3.6), false);
                        self.sayOk(decFormat.format((long) (reward * 3.6)) + " Meso ได้รับเรียบร้อยแล้ว");
                    }
                    getPlayer().updateOneInfo(777777, "reward", "0");
                } else {
                    self.sayOk("ไม่มีรางวัลที่สามารถรับได้");
                }
                break;
            case 11:
                self.sayOk(
                        "#e#b<อัตราจ่าย>#n#k\r\nเริ่ม - เริ่มซ้าย, เริ่มขวา (1.85 เท่า)\r\nขีด - 3 ขีด, 4 ขีด (1.85 เท่า)\r\nผล - คี่, คู่ (1.85 เท่า)\r\nเริ่ม + ขีด - ซ้าย4, ซ้าย3, ขวา4, ขวา3 (3.6 เท่า)\r\n\r\n#r#e<ศัพท์เกมไต่บันได>#k#n\r\nเริ่มซ้าย : เริ่มจากทางซ้าย\r\n"
                                +
                                "เริ่มขวา : เริ่มจากทางขวา\r\n3 ขีด : บันไดมี 3 ขีด\r\n4 ขีด : บันไดมี 4 ขีด\r\nซ้าย4 : เริ่มซ้าย + 4 ขีด\r\nซ้าย3 : เริ่มซ้าย + 3 ขีด\r\nขวา4 : เริ่มขวา + 4 ขีด\r\nขวา3 : เริ่มขวา + 3 ขีด");
                break;
        }
    }

    public boolean checkReward() {
        int round = getPlayer().getOneInfoQuestInteger(777777, "round");
        boolean reward = false;
        if (round > 0) {
            SpeedLadder ladder = null;
            for (SpeedLadder lad : ContentsManager.SpeedLadderGame.getLadders()) {
                if (lad.getRound() == round) {
                    ladder = lad;
                    break;
                }
            }
            if (ladder == null) {
                return false;
            }
            // type : 1 : Start Only, 2 : Lines , 3 : Result , 4 : Start + Lines
            // flag : 1 : Right, 3 lines, Odd
            // flag : 2 : Left, 4 lines, Even
            // flag : 4 : Left 4
            // flag : 8 : Left 3
            // flag : 16 : Right 4
            // flag : 32 : Right 3
            int type = getPlayer().getOneInfoQuestInteger(777777, "type");
            int flag = getPlayer().getOneInfoQuestInteger(777777, "flag");
            if (type == 1) { // Start Only
                int right = ladder.getRight();
                if ((right == 1 && flag == 1) || (right == 0 && flag == 2)) {
                    reward = true;
                } else {
                    reward = false;
                }
            } else if (type == 2) { // Lines
                int line = ladder.getLine();
                if ((line == 3 && flag == 1) || (line == 4 && flag == 2)) {
                    reward = true;
                } else {
                    reward = false;
                }
            } else if (type == 3) {
                int odd = ladder.getOdd();
                if ((odd == 1 && flag == 1) || (odd == 0 && flag == 2)) {
                    reward = true;
                } else {
                    reward = false;
                }
            } else if (type == 4) { // Start + Lines
                int right = ladder.getRight();
                int line = ladder.getLine();
                if ((right == 0 && line == 4 && flag == 4) || (right == 0 && line == 3 && flag == 8)
                        || (right == 1 && line == 4 && flag == 16) || (right == 1 && line == 3 && flag == 32)) {
                    reward = true;
                } else {
                    reward = false;
                }
            }
        } else {
            reward = false;
        }
        return reward;
    }

    public boolean canGame() {
        long time = (ContentsManager.SpeedLadderGame.getCurrentGameStartTime() + 300000) - System.currentTimeMillis();
        long minute = TimeUnit.MILLISECONDS.toMinutes(time);
        long second = TimeUnit.MILLISECONDS.toSeconds(time % 60000);
        if (minute == 0 && second < 30) {
            return false;
        }
        return true;
    }

    public String getBetString(int type, int flag) {
        // type : 1 : Start only, 2 : Lines, 3 : Result, 4 : Start + Lines
        // flag : 1 : Right, 3 lines, Odd
        // flag : 2 : Left, 4 lines, Even
        // flag : 4 : Left 4 lines
        // flag : 8 : Left 3 lines
        // flag : 16 : Right 4 lines
        // flag : 32 : Right 3 lines
        switch (type) {
            case 1: // Start
                // Start [Left]
                // Start [Right]
                if (flag == 1) {
                    return "จุดเริ่ม [ขวา]";
                } else {
                    return "จุดเริ่ม [ซ้าย]";
                }
            case 2: // Lines
                // Lines [4]
                // Lines [3]
                if (flag == 1) {
                    return "จำนวนขีด [3]";
                } else {
                    return "จำนวนขีด [4]";
                }
            case 3: // Result
                // Result [Odd]
                // Result [Even]
                if (flag == 1) {
                    return "ผลลัพธ์ [คี่]";
                } else {
                    return "ผลลัพธ์ [คู่]";
                }
            case 4: // Start + Lines
                // [Start+Lines Left4]
                // [Start+Lines Left3]
                // [Start+Lines Right4]
                // [Start+Lines Right3]
                // flag : 4 : Left4
                // flag : 8 : Left3
                // flag : 16 : Right4
                // flag : 32 : Right3
                if (flag == 4) {
                    return "เริ่ม+ขีด [ซ้าย 4 ขีด]";
                } else if (flag == 8) {
                    return "เริ่ม+ขีด [ซ้าย 3 ขีด]";
                } else if (flag == 16) {
                    return "เริ่ม+ขีด [ขวา 4 ขีด]";
                } else if (flag == 32) {
                    return "เริ่ม+ขีด [ขวา 3 ขีด]";
                }
        }
        return "";
    }

    public void characterNameChange() {
        initNPC(MapleLifeFactory.getNPC(9062010));
        int v = self.askMenu(
                "สวัสดี! ฉันคือ #bMr. New Name#k ผู้สามารถเปลี่ยนชื่อตัวละครให้คุณได้ มีอะไรให้ฉันช่วยไหม?\r\n\r\n#b#L0#เปลี่ยนชื่อตัวละคร (ต้องใช้คูปองเปลี่ยนชื่อตัวละคร)#l\r\n#L1#จบการสนทนา#l");
        if (v != 0) {
            return;
        }
        getPlayer().send(NameChanger((byte) 9, 4034803));
    }

    public static byte[] NameChanger(byte status, int itemid) {
        PacketEncoder mplew = new PacketEncoder();
        mplew.writeShort(SendPacketOpcode.USER_RENAME_RESULT.getValue());
        mplew.write(status);
        if (status == 9) {
            mplew.writeInt(itemid);
        }
        return mplew.getPacket();
    }

    public void getUnionCoin() {
        initNPC(MapleLifeFactory.getNPC(9010108));
        int coin = getPlayer().getOneInfoQuestInteger(18098, "coin");
        if (coin > 0) {
            int pv = getPlayer().getOneInfoQuestInteger(500629, "point");
            if (DBConfig.isGanglim) {
                getPlayer().gainItem(4310229, coin);
            }
            int point = pv + coin;
            getPlayer().updateOneInfo(500629, "point", point + "");
            getPlayer().updateOneInfo(18098, "coin", "0");
            getPlayer().updateOneInfo(18790, "coin", "0");

            self.say("#b#i4310229:##t4310229##k #b" + coin + " เหรียญ#k ได้รับเรียบร้อยแล้ว");
        } else {
            self.say("ไม่มีเหรียญที่สามารถรับได้");
        }
    }
}
