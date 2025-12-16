package script.Quest;

import database.DBConnection;
import network.center.Center;
import network.encode.PacketEncoder;
import network.game.GameServer;
import network.models.CField;
import objects.fields.Field;
import objects.fields.child.etc.Field_MMRace;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.fields.gameobject.lifes.MapleMonsterInformationProvider;
import objects.fields.gameobject.lifes.MapleNPC;
import objects.fields.gameobject.lifes.MonsterGlobalDropEntry;
import objects.users.MapleCharacter;
import objects.users.enchant.InnerAbility;
import objects.utils.Pair;
import objects.utils.Timer;
import scripting.newscripting.ScriptEngineNPC;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MapleGM extends ScriptEngineNPC {

    public void q12396s() {
        self.say(
                "สวัสดีครับ #b#h0##k เลเวล 50 แล้วสินะครับ! เมื่อเลเวลถึง 50 จะได้รับพลังพิเศษ #bAbility#k ครับ เดี๋ยวผมจะช่วยปลดล็อคพลังนั้นให้เอง");
        self.say("ทาดา~! พลังใหม่ของคุณ Ability ถูกปลดล็อคแล้ว ลองตรวจสอบที่หน้าต่าง Stat ดูนะครับ~!");
        getPlayer().innerLevelUp();
        getPlayer().innerLevelUp();
        getPlayer().innerLevelUp();
        getQuest().forceComplete(getPlayer(), getNpc().getId());
    }

    // NPC Dami for various tests Map: 180000001
    public void npc_9010057() {
        if (getPlayer().isGM()) {
            String testMenu = "\r\n#L2#ทดสอบ Inner Ability Option#l\r\n#L3#ทดสอบสุ่ม Inner Ability#l\r\n#L4#ทดสอบ Pink Bean#l\r\n#L5#ทดสอบ Blossom#l\r\n#L6#ทดสอบ Packet#l\r\n\r\n#L7#ผู้เสียหาย Gamdi#l";
            int v0 = self.askMenu(
                    "สวัสดีค่ะ ฉันคือ Dami ผู้ช่วยของเหล่า GM ค่ะ~! มีอะไรให้ช่วยไหมคะ?\r\n#b#L0#รับ V Matrix Skill ตามอาชีพ#l\r\n#L1#ทดสอบ Dog Racing#l"
                            + testMenu);
            switch (v0) {
                case 0:
                    getPlayer().giveDefaultVMatrixSkill();
                    self.sayOk("มอบให้เรียบร้อยแล้วค่ะ ลองตรวจสอบ V Matrix UI ดูนะคะ~!");
                    break;
                case 1:
                    registerTransferField(926010100);
                    break;
                case 2:
                    String abs = "ทดสอบ...\r\n#b";
                    int innerSize = InnerAbility.innerAbilityInfos.size();
                    SortedSet<Integer> keys = new TreeSet<>(InnerAbility.innerAbilityInfos.keySet());
                    for (Integer key : keys) {
                        System.out.println(key);
                        abs += "#L" + key + "#" + key + "#l\r\n";
                    }
                    int test = self.askMenu(abs);
                    String options = "";
                    for (String tt : InnerAbility.innerAbilityInfos.get(test).getOptions()) {
                        options += tt + "\r\n";
                    }
                    self.sayOk(InnerAbility.innerAbilityInfos.get(test).getMaxLevel() + " เลเวลสูงสุดที่เลือก");
                    // self.sayOk(options + "");
                    break;
                case 3: {
                    self.sayOk(("ฟังก์ชันที่ทดสอบเสร็จแล้ว"));
                    break;
                }
                case 4: {

                    PacketEncoder pr = new PacketEncoder();
                    pr.writeShort(100);
                    pr.write(0x0D);
                    pr.writeInt(3996);
                    pr.writeMapleAsciiString("count=1;mobid=8880010;lasttime=2021/05/02 12:09:04;mobDead=0");
                    // pr.write(HexTool.getByteArrayFromHexString("0D 35 1C 00 00 18 00 65 4E 75 6D
                    // 3D 37 3B 6C 61 73 74 44 61 74 65 3D 32 31 2F 30 34 2F 32 35"));
                    getPlayer().send(pr.getPacket());
                    /*
                     * for (int i=3000; i<=8000; i++) {
                     * PacketEncoder pr = new PacketEncoder();
                     * pr.writeShort(100);
                     * pr.write(0x0D);
                     * pr.writeInt(i);
                     * pr.writeMapleAsciiString("lastDate" + "N" + "=21/04/25;");
                     * getPlayer().send(pr.getPacket());
                     * }
                     */
                    // In case of Chaos Mode 6C 61 73 74 44 61 74 65 43 3D 32 31 2F 30 34 2F 32 35
                    // 3B
                    // lastDateC=21/04/25;
                    break;
                }
                case 5: {
                    switch (self.askMenu("#L0#สีแดง#l\r\n#L1#สีน้ำเงิน#l\r\n#L2#สีเหลือง#l")) {
                        case 0: {
                            MapleNPC npc = getPlayer().getMap().getNPCById(9062530);
                            final Field map = getPlayer().getMap();
                            if (!npc.isBlossom()) {
                                npc.setBlossom(true);
                                map.broadcastMessage(
                                        (CField.NPCPacket.npcSpecialAction(npc.getObjectId(), "special", 3000, 1)));
                                Timer.MapTimer.getInstance().schedule(new Runnable() {
                                    @Override
                                    public void run() {
                                        map.broadcastMessage(CField.NPCPacket.npcSpecialAction(npc.getObjectId(),
                                                "special2", 210000000, 1));
                                    }
                                }, 3000);
                            }
                            break;
                        }
                        case 1: {
                            MapleNPC npc = getPlayer().getMap().getNPCById(9062531);
                            final Field map = getPlayer().getMap();
                            if (!npc.isBlossom()) {
                                npc.setBlossom(true);
                                map.broadcastMessage(
                                        (CField.NPCPacket.npcSpecialAction(npc.getObjectId(), "special", 3000, 1)));
                                Timer.MapTimer.getInstance().schedule(new Runnable() {

                                    @Override
                                    public void run() {
                                        map.broadcastMessage(CField.NPCPacket.npcSpecialAction(npc.getObjectId(),
                                                "special2", 210000000, 1));
                                    }
                                }, 3000);
                            }
                            break;
                        }
                        case 2: {

                            final MapleNPC npc = getPlayer().getMap().getNPCById(9062532);
                            final Field map = getPlayer().getMap();
                            if (!npc.isBlossom()) {
                                npc.setBlossom(true);
                                map.broadcastMessage(
                                        (CField.NPCPacket.npcSpecialAction(npc.getObjectId(), "special", 3000, 1)));
                                Timer.MapTimer.getInstance().schedule(new Runnable() {
                                    @Override
                                    public void run() {
                                        map.broadcastMessage(CField.NPCPacket.npcSpecialAction(npc.getObjectId(),
                                                "special2", 210000000, 1));
                                    }
                                }, 3000);
                            }
                            break;
                        }
                    }
                    break;
                }

                case 7: { // Real-time query fix
                    HashMap<Integer, Integer> originalCoin = new HashMap<>();
                    int line = 1;
                    try {
                        // Store original coin data
                        for (String ori : Files.readAllLines(Paths.get("questinfo_account.sql"),
                                Charset.forName("UTF-8"))) {
                            if (ori.isEmpty())
                                continue;
                            String[] oriQEX = ori.split(",");
                            int questID = Integer.parseInt(oriQEX[2].trim());
                            if (questID == 500629) {
                                int accountid = Integer.parseInt(oriQEX[1].trim());
                                int point = Integer.parseInt(oriQEX[3].trim().replace("'", "").replace("point=", ""));
                                originalCoin.put(accountid, point);
                            }
                            line++;
                        }
                    } catch (Exception e) {
                        System.out.println(line);
                        e.printStackTrace();
                    }

                    PreparedStatement ps = null;
                    ResultSet rs = null;
                    HashMap<Integer, Long> ggamdee = new HashMap<>();
                    try (
                            Connection con = DBConnection.getConnection()) {
                        // Scrape all Gamdi victim data
                        ps = con.prepareStatement("SELECT * FROM `questinfo_account` WHERE `customData` = ?");
                        ps.setString(1, "point=function String() { [native code] }");
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            long id = rs.getLong("id");
                            int account_id = rs.getInt("account_id");
                            ggamdee.put(account_id, id);
                        }
                    } catch (Throwable t) {
                        t.printStackTrace();
                    } finally {
                        try {
                            if (ps != null) {
                                ps.close();
                            }
                            if (rs != null) {
                                rs.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    // Restore online users
                    for (GameServer cs : GameServer.getAllInstances()) {
                        for (Field map : cs.getMapFactory().getAllMaps()) {
                            Iterator<MapleCharacter> iterator = map.getCharacters().listIterator();
                            while (iterator.hasNext()) {
                                MapleCharacter chr = iterator.next();
                                if (chr != null) {
                                    if (ggamdee.remove(chr.getAccountID()) != null) {
                                        // Online user updateOneInfo
                                        Integer oriCoin = originalCoin.remove(chr.getAccountID());
                                        if (oriCoin != null) {
                                            chr.updateOneInfo(500629, "point", String.valueOf(oriCoin));
                                        } else {
                                            // Users with no data are 0
                                            chr.updateOneInfo(500629, "point", "0");
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // Offline users query update
                    for (Integer a : ggamdee.keySet()) {
                        try (Connection con = DBConnection.getConnection()) {
                            ps = con.prepareStatement(
                                    "UPDATE questinfo_account SET customData = ? WHERE `id` = ? AND `quest` = ?");
                            Integer oriCoin = originalCoin.remove(a);
                            if (oriCoin != null) {
                                ps.setString(1, "point=" + oriCoin);
                            } else {
                                ps.setString(1, "point=0");
                            }
                            ps.setLong(2, ggamdee.get(a));
                            ps.setInt(3, 500629); // Process only if Quest ID is 500629
                            ps.executeUpdate();
                        } catch (Throwable t) {
                            t.printStackTrace();
                        } finally {
                            try {
                                if (ps != null) {
                                    ps.close();
                                }
                                if (rs != null) {
                                    rs.close();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    self.sayOk("เสร็จสิ้น");
                    break;
                }
            }
        } else {
            self.say("อ้าว ไปตาม GM มาสิ 555");
        }
    }

    public void npc_9062508() {
        String gmMenu = "#L1#[GM Menu] แก้ไขอัตราดรอป Sunshine#l\r\n#L2#[GM Menu] เพิ่ม Blossom ด้วยตัวเอง#l\r\n#L3#[GM Menu] เปิด/ปิด การบานของดอกไม้#l";
        int v = self.askMenu("#e<Ganglim : Blossom>#n\r\nปัจจุบัน Blossom Gauge รวบรวมได้เท่านี้แล้วครับ!\r\n\r\n#B"
                + (int) ((((double) Center.sunShineStorage.getSunShineGuage() / 1000000.0d)) * 100) + "# "
                + (((double) Center.sunShineStorage.getSunShineGuage() / 1000000.0d)) * 100
                + "%\r\n\r\n#b#L0#อยากรู้เกี่ยวกับ Blossom Gauge#l\r\n" + (getPlayer().isGM() ? gmMenu : ""));
        switch (v) {
            case 0: // I want to know about Blossom Gauge
                self.say(
                        "#bBlossom Gauge#k คือเกจสำหรับการทำให้ดอกไม้บานครับ\r\nวิธีการเพิ่มเกจคือการล่ามอนสเตอร์เพื่อรับ #r#i2633343# #z2633343##k ซึ่งจะเพิ่มทีละ #e1 Point#n และ\r\nหากเติม #bGanglim Point#k เกจจะเพิ่มขึ้นอัตโนมัติ #e20% ของยอดเงินที่เติม#n ครับ");
                self.say(
                        "ทุกครั้งที่ดอกไม้บาน Map ทั้งหมดจะได้รับ #bSpring Sunshine, Spring Breeze Buff#k และเมื่อดอกไม้บานครบทั้งหมด จะมีกิจกรรม #r#eEXP 1.5 เท่า, Drop Rate 1.5 เท่า, Meso 1.5 เท่า, Symbol Drop Rate 2 เท่า (สุ่ม)#n#k มีผลทั้งเซิร์ฟเวอร์ ทราบไว้ก็ดีนะครับ!");
                break;
            case 1: // Edit Warm Sunshine Drop Rate
                if (getPlayer().isGM()) {
                    MonsterGlobalDropEntry d = null;
                    for (MonsterGlobalDropEntry de : MapleMonsterInformationProvider.getInstance().getGlobalDrop()) {
                        if (de.itemId == 2633343) {
                            d = de;
                            break;
                        }
                    }
                    int dropRate = self.askNumber("ปัจจุบันอัตราดรอป Sunshine คือ " + ((double) (d.chance / 10000.0d))
                            + "%. ต้องการแก้เป็นเท่าไหร่?\r\n 1000 = 0.1%", 0, 0, 1000000);
                    if (dropRate > 0) {
                        for (MonsterGlobalDropEntry de : MapleMonsterInformationProvider.getInstance()
                                .getGlobalDrop()) {
                            if (de.itemId == 2633343) {
                                de.chance = dropRate;
                                break;
                            }
                        }
                    }

                }
                break;
            case 2: // Manually manipulate Blossom Gauge
                if (getPlayer().isGM()) {
                    int blossom = self.askNumber("ต้องการเติมเท่าไหร่? ปัจจุบัน : "
                            + Center.sunShineStorage.getSunShineGuage() + " / 1000000", 0, 0, 1000000);
                    if (blossom > 0) {
                        Center.sunShineStorage.addSunShineGuage(blossom);
                        self.sayOk("เติม Blossom จำนวน " + blossom + " เรียบร้อยแล้ว\r\nปัจจุบัน : "
                                + Center.sunShineStorage.getSunShineGuage() + " / 1000000");
                    }
                }
                break;
            case 3:
                if (getPlayer().isGM()) {
                    int vv = self.askMenu("#b#L0#เปิด#l\r\n#L1#ปิด#l");
                    if (vv == 0) {
                        Center.sunShineStorage.setBloomFlower(true);
                        self.sayOk("เปิดเรียบร้อยแล้ว");
                    } else if (vv == 1) {
                        Center.sunShineStorage.setBloomFlower(false);
                        self.sayOk("ปิดเรียบร้อยแล้ว");
                    }
                }
                break;
        }
    }

    // Meso Race Test
    public void dooat() {
        if (getPlayer().getMap() instanceof Field_MMRace) {
            int v0 = self.askMenu(
                    "#e<Meso Race>\r\n#nเกมแข่งวิ่งเดิมพันด้วย Meso\r\n#b#L0#เข้าร่วม Meso Race (ดูอัตราต่อรอง)#l\r\n#L1#รับเงินรางวัลสะสม#l\r\n#L2#ฟังคำอธิบายเกี่ยวกับ Meso Race#l\r\n#L3#ตรวจสอบความสามารถของมอนสเตอร์#l\r\n#L4#ออกจากการสนทนา#l");
            Field_MMRace field = (Field_MMRace) getPlayer().getMap();
            if (v0 == 0) {
                if (field.getParticipateUsers().get(getPlayer().getId()) != null) {
                    self.sayOk("คุณได้เข้าร่วมการเดิมพันไปแล้วหนิ... การเดิมพันที่ลงไปแล้วไม่สามารถยกเลิกได้นะ ฮ่าๆ");
                    return;
                }
                if (field.eventRace != null) {
                    self.sayOk("เกมเริ่มไปแล้ว ไม่สามารถร่วมเดิมพันได้");
                    return;
                }
                Integer a = field.winningRate.get("Kundura");
                Integer b = field.winningRate.get("Croco");
                Integer c = field.winningRate.get("Allu");
                Integer d = field.winningRate.get("Buggy");
                if (a == null)
                    a = 0;
                if (b == null)
                    b = 0;
                if (c == null)
                    c = 0;
                if (d == null)
                    d = 0;
                int e = a + b + c + d;
                if (e == 0) {
                    e = 1;
                }
                String text = "";
                text += "#b#L0#Kundura( " + (a / e) * 100 + "% ) อัตราจ่าย : 3.0#l\r\n";
                text += "#b#L1#Croco( " + (b / e) * 100 + "% ) อัตราจ่าย : 3.0#l\r\n";
                text += "#b#L2#Allu( " + (c / e) * 100 + "% ) อัตราจ่าย : 3.0#l\r\n";
                text += "#b#L3#Buggy( " + (d / e) * 100 + "% ) อัตราจ่าย : 3.0#l\r\n";
                int mungMung = self.askMenu("จะเดิมพันตัวไหนดีล่ะ?\r\n" + text);
                long mesos = self.askNumber("จะเดิมพันเท่าไหร่? เดิมพันได้สูงสุด 200,000,000 Meso นะ", 0, 1, 200000000);
                if (getPlayer().getMeso() >= mesos) {
                    String btMonster = "";
                    switch (mungMung) {
                        case 0:
                            btMonster = "Kundura";
                            break;
                        case 1:
                            btMonster = "Croco";
                            break;
                        case 2:
                            btMonster = "Allu";
                            break;
                        case 3:
                            btMonster = "Buggy";
                            break;
                    }
                    if (field.eventRace != null) {
                        self.sayOk("เกมเริ่มไปแล้ว ไม่สามารถร่วมเดิมพันได้");
                        return;
                    }
                    field.participateUsers.put(getPlayer().getId(), new Pair(btMonster, mesos));
                    getPlayer().gainMeso(-mesos, true);
                    self.sayOk(
                            "สมัครเข้าร่วมเรียบร้อยแล้ว\r\n< ข้อมูลการเข้าร่วม >\r\nมอนสเตอร์ที่เดิมพัน : " + btMonster
                                    + "\r\n" + "ยอดเดิมพัน :" + String.format("%,d", mesos));
                } else {
                    self.sayOk("ยอดเงินที่ระบุ ดูเหมือนจะมากกว่า Meso ที่คุณมีนะ...?");
                }
            } else if (v0 == 1) {
                // Settlement of accumulated amount
                if (field.accReward.get(getPlayer().getId()) == null || field.accReward.get(getPlayer().getId()) == 0) {
                    self.sayOk("ไม่มี Meso ให้รับรางวัล");
                    return;
                }
                if (1 == self.askYesNo("Meso ที่สามารถรับได้ในปัจจุบัน\r\n"
                        + String.format("%,d", field.accReward.get(getPlayer().getId()))
                        + " Meso ต้องการรับเลยหรือไม่?")) {
                    long rewardMeso = 0;
                    if (30000000000L - (getPlayer().getMeso() + field.accReward.get(getPlayer().getId())) < 0) {
                        rewardMeso = 30000000000L - getPlayer().getMeso();
                    } else {
                        rewardMeso = field.accReward.get(getPlayer().getId());
                    }
                    field.accReward.put(getPlayer().getId(), field.accReward.get(getPlayer().getId()) - rewardMeso);
                    if (field.accReward.get(getPlayer()) != null && field.accReward.get(getPlayer()) == 0) {
                        field.accReward.remove(getPlayer().getId());
                    }
                    getPlayer().gainMeso(rewardMeso, true);
                    self.sayOk("ได้รับ " + String.format("%,d", rewardMeso)
                            + " Meso แล้ว\r\nหากยอด Meso ที่ได้รับเป็น 0 กรุณาเคลียร์ช่อง Meso แล้วมารับรางวัลใหม่");

                }
            } else if (v0 == 2) {
                self.sayOk(
                        "Meso Race คือเกมแข่งขันมอนสเตอร์ที่ใช้ Meso ในการเดิมพัน มอนสเตอร์แต่ละตัวมีความสามารถแตกต่างกัน และอัตราจ่ายจะเปลี่ยนไปตามสถิติชัยชนะ แค่เลือกเดิมพันมอนสเตอร์ที่คุณต้องการแล้วรอผลก็พอ");
            } else if (v0 == 3) {
                // Check capabilities by monster
                self.sayOk(
                        "-------------------------------\r\n#e< Kundura >#n\r\n- Speed : 2.5 / 5\r\n- #rSlow Res : 5 / 5#k\r\n- Stun Res 3 / 5\r\n#bความเร็วช้า แต่ต้านทานสถานะได้ดี\r\n\r\n#k#e< Croco >#n\r\n- Speed : 1 / 5\r\n- #rSlow Res : 5 / 5#k"
                                +
                                "\r\n- #rStun Res : 5 / 5#k\r\n#bความเร็วช้าที่สุดใน 4 ตัว แต่ความสามารถต้านทานสถานะดีที่สุด\r\n"
                                +
                                "\r\n#k#e< Allu >#n\r\n- Speed : 3 / 5\r\n- Slow Res : 3 / 5\r\n- Stun Res : 1 / 5\r\n#bSpeed และ Slow Res สมดุล แต่ไม่ค่อยทนต่อ Stun\r\n"
                                +
                                "\r\n#k#e< Buggy >#n\r\n- #rSpeed : 5 / 5#k\r\n- Slow Res : 1 / 5\r\n- Stun Res : 1 / 5\r\n#bมีความเร็วสูงสุด แต่ต้านทานสถานะได้อ่อนแอที่สุด\r\n#k-------------------------------");
            } else if (v0 == 4) {
                registerTransferField(100000000);
            }
        }
    }

    public void q100602s() {
        getPlayer().send(CField.UIPacket.openUI(1269));
    }

    public void credit_rewards() {
        initNPC(MapleLifeFactory.getNPC(9001000));

        String v0 = "สวัสดีครับ? ผมคือ #bCoke Bear Operator#k ผู้ดูแล #eรางวัลสะสม Ganglim Credit#n ครับ\r\n\r\nกรุณาเลือกรางวัลที่จะรับ\r\n\r\n";
        boolean find = false;

        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = DBConnection.getConnection()) {
            ps = con.prepareStatement("SELECT `type` FROM `extreme_point_log` WHERE `accountid` = ? and `status` = 0");
            ps.setInt(1, getClient().getAccID());
            rs = ps.executeQuery();

            while (rs.next()) {
                String type = rs.getString("type");
                v0 += "#L" + type + "##eรางวัลสะสมยอด " + type + "0,000 W#n\r\n";
                if (!find)
                    find = true;
            }

            if (!find) {
                v0 += "#bไม่มีรางวัลที่สามารถรับได้ครับ";
                self.say(v0);
                return;
            }

            int v1 = self.askMenu(v0);

            // Double check if it is a receivable status
            rs.close();
            ps.close();

            ps = con.prepareStatement("SELECT `status` FROM `extreme_point_log` WHERE `accountid` = ? and `type` = ?");
            ps.setInt(1, getClient().getAccID());
            ps.setInt(2, v1);
            rs = ps.executeQuery();

            boolean check = false;
            while (rs.next()) {
                int status = rs.getInt("status");
                if (status == 0) {
                    check = true;
                    break;
                }
            }

            if (!check) {
                self.say("การเข้าถึงผิดพลาด");
                return;
            }

            // Give reward
            switch (v1) {
                case 10: {
                    if (exchange(1142085, 1, 2450042, 5) > 0) {
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 30: {
                    if (exchange(1142086, 1, 2450042, 5, 2439604, 3) > 0) {
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 50: {
                    if (exchange(1142087, 1, 2450042, 5, 2439604, 3, 5060048, 5) > 0) {
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 75: {
                    if (exchange(2450042, 5, 2049360, 3, 2439604, 3, 2436018, 2) > 0) {
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 100: {
                    if (exchange(1142088, 1, 2450163, 5, 2049360, 3, 2439604, 3, 5060048, 5) > 0) {
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 150: {
                    if (exchange(2450163, 10, 2049360, 5, 2434558, 3) > 0) {
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 200: {
                    if (exchange(1142089, 1, 2450163, 10, 2049360, 5, 2434558, 3, 5060048, 5) > 0) {
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 250: {
                    if (exchange(2450163, 10, 2049360, 5, 2434558, 5) > 0) {
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 300: {
                    if (exchange(1142090, 1, 2434558, 5, 2436018, 2, 5060048, 10) > 0) {
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 350: {
                    if (exchange(2434558, 5, 5680159, 10, 5060048, 5) > 0) {
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 400: {
                    if (exchange(1142091, 1, 2434558, 5, 5060048, 10) > 0) {
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 450: {
                    if (exchange(2434558, 5, 5680159, 15, 5060048, 5) > 0) {
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 500: {
                    if (exchange(1142092, 1, 2434558, 5, 5680159, 15, 5060048, 5) > 0) {
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 600: {
                    if (exchange(2450163, 10, 5680159, 15) > 0) {
                        getPlayer().gainRealCash(200000, true);
                        getPlayer().dropMessage(5, "ได้รับ 200,000 Ganglim Credit แล้ว");
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 750: {
                    if (exchange(1142093, 1, 2434558, 5, 5060048, 10) > 0) {
                        getPlayer().gainRealCash(200000, true);
                        getPlayer().dropMessage(5, "ได้รับ 200,000 Ganglim Credit แล้ว");
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 900: {
                    if (exchange(5680159, 15) > 0) {
                        getPlayer().gainRealCash(400000, true);
                        getPlayer().dropMessage(5, "ได้รับ 400,000 Ganglim Credit แล้ว");
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 1000: {
                    if (exchange(1142094, 1, 2434558, 5, 5060048, 20) > 0) {
                        getPlayer().gainRealCash(200000, true);
                        getPlayer().dropMessage(5, "ได้รับ 200,000 Ganglim Credit แล้ว");
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 1100: {
                    if (exchange(5062503, 500) > 0) {
                        getPlayer().gainRealCash(400000, true);
                        getPlayer().dropMessage(5, "ได้รับ 400,000 Ganglim Credit แล้ว");
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 1250: {
                    if (exchange(1142095, 1, 5060048, 20) > 0) {
                        getPlayer().gainRealCash(500000, true);
                        getPlayer().dropMessage(5, "ได้รับ 500,000 Ganglim Credit แล้ว");
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 1400: {
                    if (exchange(2049376, 1) > 0) {
                        getPlayer().gainRealCash(500000, true);
                        getPlayer().dropMessage(5, "ได้รับ 500,000 Ganglim Credit แล้ว");
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 1500: {
                    if (exchange(1142096, 1, 2434558, 10, 5060048, 20) > 0) {
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 1600: {
                    if (exchange(2434558, 10, 2049376, 1) > 0) {
                        getPlayer().gainRealCash(200000, true);
                        getPlayer().dropMessage(5, "ได้รับ 200,000 Ganglim Credit แล้ว");
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 1750: {
                    if (exchange(1142097, 1, 5060048, 20) > 0) {
                        getPlayer().gainRealCash(600000, true);
                        getPlayer().dropMessage(5, "ได้รับ 600,000 Ganglim Credit แล้ว");
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 1900: {
                    if (exchange(5680159, 30) > 0) {
                        getPlayer().gainRealCash(800000, true);
                        getPlayer().dropMessage(5, "ได้รับ 800,000 Ganglim Credit แล้ว");
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 2000: {
                    if (exchange(1142098, 1, 5060048, 30) > 0) {
                        getPlayer().gainRealCash(1000000, true);
                        getPlayer().dropMessage(5, "ได้รับ 1,000,000 Ganglim Credit แล้ว");
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 2100: {
                    if (exchange(2434558, 10) > 0) {
                        getPlayer().gainRealCash(1000000, true);
                        getPlayer().dropMessage(5, "ได้รับ 1,000,000 Ganglim Credit แล้ว");
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 2200:
                case 2300:
                case 2400: {
                    if (exchange(2434558, 10, 2049376, 1) > 0) {
                        getPlayer().gainRealCash(1000000, true);
                        getPlayer().dropMessage(5, "ได้รับ 1,000,000 Ganglim Credit แล้ว");
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
                case 2500: {
                    if (exchange(1142099, 1, 2049376, 3, 5060048, 50) > 0) {
                        getPlayer().gainRealCash(1000000, true);
                        getPlayer().dropMessage(5, "ได้รับ 1,000,000 Ganglim Credit แล้ว");
                        self.say("มอบรางวัลเรียบร้อยแล้วครับ ขอบคุณครับ");
                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กรุณาทำช่องในกระเป๋าให้ว่างแล้วลองใหม่ครับ");
                    }
                    break;
                }
            }
        } catch (SQLException e) {

        } finally {
            try {
                if (ps != null) {
                    ps.close();
                    ps = null;
                }
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            } catch (SQLException e) {
            }
        }
    }
}
