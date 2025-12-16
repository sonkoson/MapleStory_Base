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
        self.say("안녕하세요, #b#h0##k.벌써 เลเวล 50 달성하셨군요! #bเลเวล 50#k 달성하게  특별한 힘, #b어빌리티#k 얻을 수 있게 된답니다. 지금 제가 그 힘을 개방시켜 드릴게요.");
        self.say("자~! 당신의 새로운 힘, 어빌리티를 개방해 드렸. ตัวละคร Stat창을 통해서 ยืนยัน해보세요~!");
        getPlayer().innerLevelUp();
        getPlayer().innerLevelUp();
        getPlayer().innerLevelUp();
        getQuest().forceComplete(getPlayer(), getNpc().getId());
    }

    //여러가지 테스트를 하기위한 엔피시 แผนที่ : 180000001  있는 다미
    public void npc_9010057() {
        if (getPlayer().isGM()) {
            String testMenu = "\r\n#L2#이너어빌리티 ตัวเลือก테스트#l\r\n#L3#สุ่ม 이너 어빌리티 테스트하기#l\r\n#L4#핑크빈테스트#l\r\n#L5#블라썸테스트#l\r\n#L6#패킷테스트#l\r\n\r\n#L7#깸디 피해자#l";
            int v0 = self.askMenu("안녕하세요 저는 운영자님들에게 ช่วยเหลือ을 드리는 다미라고 ~! 무엇을 도와드릴까요?\r\n#b#L0#직업에 맞는 สกิล코어 지급받기#l\r\n#L1#멍멍이 레이싱 테스트#l" + testMenu);
            switch (v0) {
                case 0:
                    getPlayer().giveDefaultVMatrixSkill();
                    self.sayOk("지급 เสร็จสมบูรณ์. V매트릭스 UI ยืนยัน해보세요~!");
                    break;
                case 1:
                    registerTransferField(926010100);
                    break;
                case 2:
                    String abs = "테스트...\r\n#b";
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
                    self.sayOk(InnerAbility.innerAbilityInfos.get(test).getMaxLevel() + " เลือก한거 맥스เลเวล임");
                    //self.sayOk(options + "");
                    break;
                case 3: {
                    self.sayOk(("테스트끝난ฟังก์ชัน임임"));
                    break;
                }
                case 4: {


                    PacketEncoder pr = new PacketEncoder();
                    pr.writeShort(100);
                    pr.write(0x0D);
                    pr.writeInt(3996);
                    pr.writeMapleAsciiString("count=1;mobid=8880010;lasttime=2021/05/02 12:09:04;mobDead=0");
                    //pr.write(HexTool.getByteArrayFromHexString("0D 35 1C 00 00 18 00 65 4E 75 6D 3D 37 3B 6C 61 73 74 44 61 74 65 3D 32 31 2F 30 34 2F 32 35"));
                    getPlayer().send(pr.getPacket());
                    /*
                    for (int i=3000; i<=8000; i++) {
                        PacketEncoder pr = new PacketEncoder();
                        pr.writeShort(100);
                        pr.write(0x0D);
                        pr.writeInt(i);
                        pr.writeMapleAsciiString("lastDate" + "N" + "=21/04/25;");
                        getPlayer().send(pr.getPacket());
                    }
                     */
                    //카오스โหมด의경우 6C 61 73 74 44 61 74 65 43 3D 32 31 2F 30 34 2F 32 35 3B lastDateC=21/04/25;
                    break;
                }
                case 5: {
                    switch (self.askMenu("#L0#빨간색#l\r\n#L1#파란색#l\r\n#L2#노란색#l")) {
                        case 0: {
                            MapleNPC npc = getPlayer().getMap().getNPCById(9062530);
                            final Field map = getPlayer().getMap();
                            if (!npc.isBlossom()) {
                                npc.setBlossom(true);
                                map.broadcastMessage((CField.NPCPacket.npcSpecialAction(npc.getObjectId(), "special", 3000, 1)));
                                Timer.MapTimer.getInstance().schedule(new Runnable() {
                                    @Override
                                    public void run() {
                                        map.broadcastMessage(CField.NPCPacket.npcSpecialAction(npc.getObjectId(), "special2", 210000000, 1));
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
                                map.broadcastMessage((CField.NPCPacket.npcSpecialAction(npc.getObjectId(), "special", 3000, 1)));
                                Timer.MapTimer.getInstance().schedule(new Runnable() {
                                    @Override
                                    public void run() {
                                        map.broadcastMessage(CField.NPCPacket.npcSpecialAction(npc.getObjectId(), "special2", 210000000, 1));
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
                                map.broadcastMessage((CField.NPCPacket.npcSpecialAction(npc.getObjectId(), "special", 3000, 1)));
                                Timer.MapTimer.getInstance().schedule(new Runnable() {
                                    @Override
                                    public void run() {
                                        map.broadcastMessage(CField.NPCPacket.npcSpecialAction(npc.getObjectId(), "special2", 210000000, 1));
                                    }
                                }, 3000);
                            }
                            break;
                        }
                    }
                    break;
                }

                case 7: { //실เวลา 쿼리แก้ไข
                    HashMap<Integer, Integer> originalCoin = new HashMap<>();
                    int line = 1;
                    try {
                        //원래있던 코인 ข้อมูล담음
                        for (String ori : Files.readAllLines(Paths.get("questinfo_account.sql"), Charset.forName("UTF-8"))) {
                            if (ori.isEmpty()) continue;
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
                    try (Connection con = DBConnection.getConnection()) {
                        //깸디 피해자들 ข้อมูล싹다긁음
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
                    //접속중인 사람 원상복구
                    for (GameServer cs : GameServer.getAllInstances()) {
                        for (Field map : cs.getMapFactory().getAllMaps()) {
                            Iterator<MapleCharacter> iterator = map.getCharacters().listIterator();
                            while (iterator.hasNext()) {
                                MapleCharacter chr = iterator.next();
                                if (chr != null) {
                                    if (ggamdee.remove(chr.getAccountID()) != null) {
                                        //온라인 이용자 updateOneOnfo
                                        Integer oriCoin = originalCoin.remove(chr.getAccountID());
                                        if (oriCoin != null) {
                                            chr.updateOneInfo(500629, "point", String.valueOf(oriCoin));
                                        } else {
                                            //ข้อมูล없는사람들은 0개임
                                            chr.updateOneInfo(500629, "point", "0");
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //오프라인사람들 쿼리 업뎃
                    for (Integer a : ggamdee.keySet()) {
                        try (Connection con = DBConnection.getConnection()) {
                            ps = con.prepareStatement("UPDATE questinfo_account SET customData = ? WHERE `id` = ? AND `quest` = ?");
                            Integer oriCoin = originalCoin.remove(a);
                            if (oriCoin != null) {
                                ps.setString(1, "point=" + oriCoin);
                            } else {
                                ps.setString(1, "point=0");
                            }
                            ps.setLong(2, ggamdee.get(a));
                            ps.setInt(3, 500629); //เควส아이디가 500629วัน경우만 ประมวลผล해줌
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
                    self.sayOk("เสร็จสมบูรณ์.");
                    break;
                }
            }
        } else {
            self.say("아 운영자 데려오라고 ㅋㅋ");
        }
    }

    public void npc_9062508() {
        String gmMenu = "#L1#[GMเมนู] 따사로운 햇살 ดรอป률 แก้ไข#l\r\n#L2#[GMเมนู] 블라썸 ด้วยตนเอง เพิ่ม#l\r\n#L3#[GMเมนู] 꽃피우기 끄고 켜기#l";
        int v = self.askMenu("#e<강림 : 블라썸>#n\r\nปัจจุบัน 블라썸 게이지가 아래 만큼 모였!\r\n\r\n#B" + (int) ((((double) Center.sunShineStorage.getSunShineGuage() / 1000000.0d)) * 100) + "# " + (((double) Center.sunShineStorage.getSunShineGuage() / 1000000.0d)) * 100 + "%\r\n\r\n#b#L0#블라썸 게이지에 대해 알고 싶어요.#l\r\n" + (getPlayer().isGM() ? gmMenu : ""));
        switch (v) {
            case 0: //블라썸 게이지에 대해 알고 싶어요
                self.say("#b블라썸 게이지#k 꽃을 피우기 위한 게이지에요.\r\n게이지를 올리기 위해선 사냥을 통해 ได้รับ할 수 있는 #r#i2633343# #z2633343##k 통해 #e1คะแนน#n씩 올리는 방법과\r\n#b강림 คะแนน#k 충전하게  #e충전 금액에 20%#n씩 อัตโนมัติ으로 게이지가 충전된답니다.");
                self.say("꽃이 한 개씩 필 때 마다 ทั้งหมด แผนที่ #b봄 햇살, 봄 바람 Buff#k เริ่ม , 꽃이 다 피게 될 경우 #r#eEXP 1.5배, 드랍률 1.5배, Meso ได้รับ량 1.5배, 심볼 드랍률 2배 중 สุ่ม Buff#n#k 이벤트가 서버 ทั้งหมด에 ใช้งาน 되니 참고 좋겠지요?");
                break;
            case 1: //따사로운 햇살 ดรอป률 แก้ไข
                if (getPlayer().isGM()) {
                    MonsterGlobalDropEntry d = null;
                    for (MonsterGlobalDropEntry de : MapleMonsterInformationProvider.getInstance().getGlobalDrop()) {
                        if (de.itemId == 2633343) {
                            d = de;
                            break;
                        }
                    }
                    int dropRate = self.askNumber("ปัจจุบัน 따사로운 햇살 ดรอป률은 " + ((double) (d.chance / 10000.0d)) + "%. 얼마로 แก้ไข할까요?\r\n 1000 = 0.1%", 0, 0, 1000000);
                    if (dropRate > 0) {
                        for (MonsterGlobalDropEntry de : MapleMonsterInformationProvider.getInstance().getGlobalDrop()) {
                            if (de.itemId == 2633343) {
                                de.chance = dropRate;
                                break;
                            }
                        }
                    }

                }
                break;
            case 2: //블라썸 게이지 ด้วยตนเอง 조작
                if (getPlayer().isGM()) {
                    int blossom = self.askNumber("얼MP 충전할까요? ปัจจุบัน : " + Center.sunShineStorage.getSunShineGuage() + " / 1000000", 0, 0, 1000000);
                    if (blossom > 0) {
                        Center.sunShineStorage.addSunShineGuage(blossom);
                        self.sayOk("블라썸이 " + blossom + "만큼 충전되었.\r\nปัจจุบัน : " + Center.sunShineStorage.getSunShineGuage() + " / 1000000");
                    }
                }
                break;
            case 3:
                if (getPlayer().isGM()) {
                    int vv = self.askMenu("#b#L0#켜기#l\r\n#L1#끄기#l");
                    if (vv == 0) {
                        Center.sunShineStorage.setBloomFlower(true);
                        self.sayOk("켜기เสร็จสมบูรณ์");
                    } else if (vv == 1) {
                        Center.sunShineStorage.setBloomFlower(false);
                        self.sayOk("끄기เสร็จสมบูรณ์");
                    }
                }
                break;
        }
    }


    //멍멍레이스 테스트용
    public void dooat() {
        if (getPlayer().getMap() instanceof Field_MMRace) {
            int v0 = self.askMenu("#e<Meso 레이스>\r\n#nMeso 걸고하는 레이싱 게임\r\n#b#L0#Meso 레이스에 เข้าร่วม한다(배당ยืนยัน)#l\r\n#L1#누적 금액을 정산받고 싶어요.#l\r\n#L2#Meso 레이스에 대해 อธิบาย을 듣는다.#l\r\n#L3#มอนสเตอร์별 ความสามารถ치를 ยืนยัน 싶어요.#l\r\n#L4#나가고 싶어요#l");
            Field_MMRace field = (Field_MMRace) getPlayer().getMap();
            if (v0 == 0) {
                if (field.getParticipateUsers().get(getPlayer().getId()) != null) {
                    self.sayOk("자네는 이미 게임에 เข้าร่วม한 สถานะ로구먼... 이미 한번 걸린 배팅은 무를 수 없지 하하");
                    return;
                }
                if (field.eventRace != null) {
                    self.sayOk("이미 게임이 ดำเนินการ 중 วัน때는 배팅에 เข้าร่วม할 수 없다네.");
                    return;
                }
                Integer a = field.winningRate.get("쿤두라");
                Integer b = field.winningRate.get("그로돈");
                Integer c = field.winningRate.get("얼루");
                Integer d = field.winningRate.get("버기");
                if (a == null) a = 0;
                if (b == null) b = 0;
                if (c == null) c = 0;
                if (d == null) d = 0;
                int e = a + b + c + d;
                if (e == 0) {
                    e = 1;
                }
                String text = "";
                text += "#b#L0#쿤두라( " + (a / e) * 100 + "% ) 배당률 : 3.0#l\r\n";
                text += "#b#L1#그로돈( " + (b / e) * 100 + "% ) 배당률 : 3.0#l\r\n";
                text += "#b#L2#얼루( " + (c / e) * 100 + "% ) 배당률 : 3.0#l\r\n";
                text += "#b#L3#버기( " + (d / e) * 100 + "% ) 배당률 : 3.0#l\r\n";
                int mungMung = self.askMenu("누구한테 배팅할텐가?\r\n" + text);
                long mesos = self.askNumber("얼마를 배팅할텐가? สูงสุด 200,000,000 Meso까지 배팅할 수 있다네.", 0, 1, 200000000);
                if (getPlayer().getMeso() >= mesos) {
                    String btMonster = "";
                    switch (mungMung) {
                        case 0:
                            btMonster = "쿤두라";
                            break;
                        case 1:
                            btMonster = "그로돈";
                            break;
                        case 2:
                            btMonster = "얼루";
                            break;
                        case 3:
                            btMonster = "버기";
                            break;
                    }
                    if (field.eventRace != null) {
                        self.sayOk("이미 게임이 ดำเนินการ 중 วัน때는 배팅에 เข้าร่วม할 수 없다네.");
                        return;
                    }
                    field.participateUsers.put(getPlayer().getId(), new Pair(btMonster, mesos));
                    getPlayer().gainMeso(-mesos, true);
                    self.sayOk("เข้าร่วมสมัคร이 เสร็จสมบูรณ์되었네.\r\n< เข้าร่วม ข้อมูล >\r\n배팅 มอนสเตอร์ : " + btMonster + "\r\n" + "배팅 금액 :" + String.format("%,d", mesos));
                } else {
                    self.sayOk("자네가 입력한 금액이 자네가 갖고있는 Meso보다 큰 것 같구려...?");
                }
            } else if (v0 == 1) {
                //누적금액 정산
                if (field.accReward.get(getPlayer().getId()) == null || field.accReward.get(getPlayer().getId()) == 0) {
                    self.sayOk("정산받을 Meso 없.");
                    return;
                }
                if (1 == self.askYesNo("ปัจจุบัน 정산받을 수 있는 Meso\r\n" + String.format("%,d", field.accReward.get(getPlayer().getId())) + "Meso . 정산 받으시겠습니까?")) {
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
                    self.sayOk(String.format("%,d", rewardMeso) + "Meso 지급되었.\r\nMeso 지급량이 0인경우 กระเป๋า Meso 비운 뒤 정산받아 สัปดาห์세요.");

                }
            } else if (v0 == 2) {
                self.sayOk("Meso 레이스는 Meso 걸고하는 มอนสเตอร์ 레이싱 게임이라네. แต่ละ มอนสเตอร์들은 서로 다른 ความสามารถ치를 갖고  게임이 하나하나 누적됨에 따라 그날그날 정해지는 승률로 배당금액이 다르지 원하는 มอนสเตอร์에게 걸기만 기다리면 되는걸세");
            } else if (v0 == 3) {
                //มอนสเตอร์별 ความสามารถ치 ยืนยัน
                self.sayOk("-------------------------------\r\n#e< 쿤두라 >#n\r\n- 스피드 : 2.5 / 5\r\n- #r슬로우 저항 : 5 / 5#k\r\n- 스턴 저항 3 / 5\r\n#b속도는 느리지만 디Buff 저항이 무난한 편\r\n\r\n#k#e< 그로돈 >#n\r\n- 스피드 : 1 / 5\r\n- #r슬로우 저항 : 5 / 5#k" +
                        "\r\n- #r스턴 저항 : 5 / 5#k\r\n#b속도는 네 마리 มอนสเตอร์ 중 제วัน 느리지만 디Buff 저항 ความสามารถ이 제วัน 좋다\r\n" +
                        "\r\n#k#e< 얼루 >#n\r\n- 스피드 : 3 / 5\r\n- 슬로우 저항 : 3 / 5\r\n- 스턴 저항 : 1 / 5\r\n#b스피드와 슬로우 저항이 밸런스형 이지만 스턴 저항이 아쉬운 밸런스형 มอนสเตอร์\r\n" +
                        "\r\n#k#e< 버기 >#n\r\n- #r스피드 : 5 / 5#k\r\n- 슬로우 저항 : 1 / 5\r\n- 스턴 저항 : 1 / 5\r\n#b스피드가 제วัน 빠른 มอนสเตอร์지만 디Buff 저항이 약한 มอนสเตอร์\r\n#k-------------------------------");
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

        String v0 = "안녕하세요? #e강림 크레딧 누적 รางวัล 지급#n 담당 있는 #b코-크베어 운영자#k.\r\n\r\n받으 실 รางวัล을 เลือกโปรด.\r\n\r\n";
        boolean find = false;

        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = DBConnection.getConnection()) {
            ps = con.prepareStatement("SELECT `type` FROM `extreme_point_log` WHERE `accountid` = ? and `status` = 0");
            ps.setInt(1, getClient().getAccID());
            rs = ps.executeQuery();

            while (rs.next()) {
                String type = rs.getString("type");
                v0 += "#L" + type + "##e" + type + "만원#n 누적 รางวัล을 받겠.#l\r\n";
                if (!find) find = true;
            }

            if (!find) {
                v0 += "#b받을 수 있는 รางวัล이 없.";
                self.say(v0);
                return;
            }

            int v1 = self.askMenu(v0);

            // 받을 수 있는 สถานะ인지 한 번 더 체크 체크
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
                self.say("잘못된 접근.");
                return;
            }

            // 지급
            switch (v1) {
                case 10: {
                    if (exchange(1142085, 1, 2450042, 5) > 0) {
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 30: {
                    if (exchange(1142086, 1, 2450042, 5, 2439604, 3) > 0) {
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 50: {
                    if (exchange(1142087, 1, 2450042, 5, 2439604, 3, 5060048, 5) > 0) {
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 75: {
                    if (exchange(2450042, 5, 2049360, 3, 2439604, 3, 2436018, 2) > 0) {
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 100: {
                    if (exchange(1142088, 1, 2450163, 5, 2049360, 3, 2439604, 3, 5060048, 5) > 0) {
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 150: {
                    if (exchange(2450163, 10, 2049360, 5, 2434558, 3) > 0) {
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 200: {
                    if (exchange(1142089, 1, 2450163, 10, 2049360, 5, 2434558, 3, 5060048, 5) > 0) {
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 250: {
                    if (exchange(2450163, 10, 2049360, 5, 2434558, 5) > 0) {
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 300: {
                    if (exchange(1142090, 1, 2434558, 5, 2436018, 2, 5060048, 10) > 0) {
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 350: {
                    if (exchange(2434558, 5, 5680159, 10, 5060048, 5) > 0) {
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 400: {
                    if (exchange(1142091, 1, 2434558, 5, 5060048, 10) > 0) {
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 450: {
                    if (exchange(2434558, 5, 5680159, 15, 5060048, 5) > 0) {
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 500: {
                    if (exchange(1142092, 1, 2434558, 5, 5680159, 15, 5060048, 5) > 0) {
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 600: {
                    if (exchange(2450163, 10, 5680159, 15) > 0) {
                        getPlayer().gainRealCash(200000, true);
                        getPlayer().dropMessage(5, "200,000강림 크레딧을 ได้รับแล้ว.");
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 750: {
                    if (exchange(1142093, 1, 2434558, 5, 5060048, 10) > 0) {
                        getPlayer().gainRealCash(200000, true);
                        getPlayer().dropMessage(5, "200,000강림 크레딧을 ได้รับแล้ว.");
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 900: {
                    if (exchange(5680159, 15) > 0) {
                        getPlayer().gainRealCash(400000, true);
                        getPlayer().dropMessage(5, "400,000강림 크레딧을 ได้รับแล้ว.");
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 1000: {
                    if (exchange(1142094, 1, 2434558, 5, 5060048, 20) > 0) {
                        getPlayer().gainRealCash(200000, true);
                        getPlayer().dropMessage(5, "200,000강림 크레딧을 ได้รับแล้ว.");
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 1100: {
                    if (exchange(5062503, 500) > 0) {
                        getPlayer().gainRealCash(400000, true);
                        getPlayer().dropMessage(5, "400,000강림 크레딧을 ได้รับแล้ว.");
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 1250: {
                    if (exchange(1142095, 1, 5060048, 20) > 0) {
                        getPlayer().gainRealCash(500000, true);
                        getPlayer().dropMessage(5, "500,000강림 크레딧을 ได้รับแล้ว.");
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 1400: {
                    if (exchange(2049376, 1) > 0) {
                        getPlayer().gainRealCash(500000, true);
                        getPlayer().dropMessage(5, "500,000강림 크레딧을 ได้รับแล้ว.");
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 1500: {
                    if (exchange(1142096, 1, 2434558, 10, 5060048, 20) > 0) {
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 1600: {
                    if (exchange(2434558, 10, 2049376, 1) > 0) {
                        getPlayer().gainRealCash(200000, true);
                        getPlayer().dropMessage(5, "200,000강림 크레딧을 ได้รับแล้ว.");
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 1750: {
                    if (exchange(1142097, 1, 5060048, 20) > 0) {
                        getPlayer().gainRealCash(600000, true);
                        getPlayer().dropMessage(5, "600,000강림 크레딧을 ได้รับแล้ว.");
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 1900: {
                    if (exchange(5680159, 30) > 0) {
                        getPlayer().gainRealCash(800000, true);
                        getPlayer().dropMessage(5, "800,000강림 크레딧을 ได้รับแล้ว.");
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 2000: {
                    if (exchange(1142098, 1, 5060048, 30) > 0) {
                        getPlayer().gainRealCash(1000000, true);
                        getPlayer().dropMessage(5, "1,000,000강림 크레딧을 ได้รับแล้ว.");
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 2100: {
                    if (exchange(2434558, 10) > 0) {
                        getPlayer().gainRealCash(1000000, true);
                        getPlayer().dropMessage(5, "1,000,000강림 크레딧을 ได้รับแล้ว.");
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 2200:
                case 2300:
                case 2400: {
                    if (exchange(2434558, 10, 2049376, 1) > 0) {
                        getPlayer().gainRealCash(1000000, true);
                        getPlayer().dropMessage(5, "1,000,000강림 크레딧을 ได้รับแล้ว.");
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
                    }
                    break;
                }
                case 2500: {
                    if (exchange(1142099, 1, 2049376, 3, 5060048, 50) > 0) {
                        getPlayer().gainRealCash(1000000, true);
                        getPlayer().dropMessage(5, "1,000,000강림 크레딧을 ได้รับแล้ว.");
                        self.say("지급이 เสร็จสมบูรณ์. 감사.");
                        PreparedStatement ps2 = con.prepareStatement("UPDATE `extreme_point_log` SET `status` = ? WHERE `accountid` = ? AND `type` = ?");
                        ps2.setInt(1, 1);
                        ps2.setInt(2, getClient().getAccID());
                        ps2.setInt(3, v1);
                        ps2.executeUpdate();
                        ps2.close();
                    } else {
                        self.say("กระเป๋า ช่อง 확보 다시 시도โปรด.");
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
