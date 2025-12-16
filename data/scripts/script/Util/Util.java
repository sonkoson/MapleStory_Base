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
            32700, 32710, 32720, 32730, 32740, 32750, 32760, 32770, 32780, 32790, 32800, 32810, 32820, 32830, 32860, 32870, 32880, 32890, 32890, 32900, 32910,
            32920, 32930, 32940, 32950, 32960, 32970, 32980, 32990, 39100, 39110, 39120, 39130, 39140, 39150, 39160, 39170, 45320, 45330, 45340, 45350, 45360,
            45370, 45380, 45390, 45400, 45410, 45420, 45430, 45440, 45450, 45460, 45470, 45480, 45490, 45500, 45510, 45520, 45530, 45540, 45550, 45560, 45570,
            45580, 45590, 45600, 45610, 45620, 45630, 45640, 45650, 45660, 45670, 45680, 45690, 45700, 45710, 45720, 45730, 45740, 45750, 45760, 45770, 45780,
            45790, 45800, 45810, 45820, 45830, 45840, 45850, 45860, 45870, 45880, 45890, 45900, 45910, 45920, 45930, 45940, 45950, 45960, 45970, 45980, 45990,
            39180, 39270, 39280, 39290, 39470, 39480, 39490, 39790, 39800, 39810, 39820, 39830, 39840, 39850, 39860, 39870, 39880, 39890, 39910, 47810, 47820,
            39000, 39010, 39020, 39030, 39450, 39460, 39300, 39310, 39320, 39330, 39340, 39350, 39360, 39370, 39380, 39390, 39400, 39410, 39420, 39430, 39440,
            39500, 39510, 39520, 39530, 39540, 39550, 39560, 39570, 39580, 39590, 39600, 39610, 39620, 39630, 39640, 39650, 39660, 39670, 39680, 39690, 39700,
            39710, 39720, 39730, 39740, 39750, 39760, 39770, 39780, 39900, 39000, 39010, 39020, 39030, 39040, 39050, 39060, 39070, 39080, 39100, 39110, 39120, 39130, 39140, 39150, 39160, 39170, 39180, 39190, 39200, 39210,
            39500, 39510, 39520, 39530, 39540, 39550, 39560, 39570, 39580, 39590));

    DecimalFormat decFormat = new DecimalFormat("###,###");

    public void searchHairAndFace() {
        initNPC(MapleLifeFactory.getNPC(9000172));
        int vv = self.askMenu("     #fUI/UIWindow2.img/Script/Title/1#\r\n#e<검색 코디>#n\r\n#b#h0##k , #r검색#k 통해 헤어와 성형을 바꿔보세요.\r\n#b#L0#헤어 검색#l\r\n#L1#성형 검색#l");
        String v = "";
        if (vv == 0) {
            v = self.askText("원하시는 헤어의 이름 บางส่วน를 검색โปรด.", ScriptMessageFlag.NpcReplacedByNpc);
        } else {
            v = self.askText("원하시는 성형의 이름 บางส่วน를 검색โปรด.", ScriptMessageFlag.NpcReplacedByNpc);
        }
        if (v.equals("")) {
            return;
        }
        List<Integer> items = new ArrayList<>();
        MapleItemInformationProvider mii = MapleItemInformationProvider.getInstance();
        switch (vv) {
            case 0: { //헤어검색

                String itemName = null;
                for (Pair<Integer, String> item : mii.getAllEquips()) {
                    int itemid = item.getLeft();
                    if (itemid / 10000 == 3 || itemid / 10000 == 4 || itemid / 10000 == 6) {
                        if (specialHair.contains(itemid) || itemid % 10 != 0) {
                            continue;
                        }
                        itemName = item.getRight();
                        if (itemName.replace(" ", "").contains(v) || itemName.contains(v) || itemName.contains(v.replace(" ", "")) ||
                                itemName.replace(" ", "").contains(v.replace(" ", ""))) {
                            items.add(itemid);
                        }
                    }
                }
                break;
            }

            case 1: { //성형검색
                String itemName = null;
                for (Pair<Integer, String> item : mii.getAllEquips()) {
                    int itemid = item.getLeft();
                    if (itemid / 10000 == 2 || itemid / 10000 == 5) {
                        itemName = item.getRight();
                        if (String.valueOf(itemid).charAt(2) == '0') {
                            if (itemName.replace(" ", "").contains(v) || itemName.contains(v) || itemName.contains(v.replace(" ", "")) ||
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
            self.sayOk("찾으시려는 검색어에 데이터량이 너무 많아 แสดง하지 못แล้ว. 좀 더 정확한 검색을 โปรด.\r\n[예 : (라리엘 헤어) 라리x 라리엘o]");
        } else if (items.size() > 0) {
            String list = "아래는 검색 ผลลัพธ์. 원하시는 코디가 있다면 เลือกโปรด.#b\r\n";
            /*for (int i = 0; i < items.size(); i++) {
                list += "#L" + i + "#" + "#z" + items.get(i) + "#\r\n";
            }*/
            for (int i = 0; i < items.size(); i++) {
                String type = "Hair";
                String type2 = "hair";
                if (vv == 1) {
                    type = "Face";
                    type2 = "face";
                }
                list += "#L" + i + "#" + "#fCharacter/" + type + "/000" + items.get(i) + ".img/default/" + type2 + "# #e#z" + items.get(i) + "##n#l\r\n";
            }
            int vvv = self.askMenu(list);
            if (vvv > -1) {
                int az = 0;
                if (GameConstants.isAngelicBuster(getPlayer().getJob())) {
                    if (1 == self.askYesNo("드레스업 โหมด로 ใช้งาน ต้องการหรือไม่?")) {
                        az = 1;
                    }
                }
                if (GameConstants.isZero(getPlayer().getJob())) {
                    if (1 == self.askYesNo("베타에 ใช้งานต้องการหรือไม่? (#r#e아니오#n#k 누를 경우 알파에 ใช้งาน.)")) {
                        az = 1;
                    }
                }
                String v0 = "정말 #r#z" + items.get(vvv) + "##k เปลี่ยนต้องการหรือไม่?\r\n#e";
                if (vv == 0) {
                    v0 += "\r\n앞 모습 -\r\n#fCharacter/Hair/000" + items.get(vvv) + ".img/default/hair#\r\n뒷 모습 -\r\n#fCharacter/Hair/000" + items.get(vvv) + ".img/backDefault/backHair#";
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
                        // 믹염 วินาที기화
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
                self.sayOk("검색ผลลัพธ์가 없.");
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
            self.say("#h0# 반갑.\r\n저는 " + ServerConstants.serverName + "서버의 사다리를 담당있는 컴퓨터.\r\n\r\nปัจจุบัน는 해당 ฟังก์ชัน을 이용할 수 없.");
            return;
        }
        if (!canGame()) {
            String text = "#h0# 반갑.\r\n저는 " + ServerConstants.serverName + "서버의 사다리를 담당있는 컴퓨터.\r\n\r\n#r#e사다리" + ContentsManager.SpeedLadderGame.getCurrentRound() + "회차 ผลลัพธ์ 까지 " + recordToString + "#n#k" +
                    "\r\n\r\n";
            if (getPlayer().getOneInfoQuestInteger(777777, "round") == 0) {
                text += "#b#L0#사다리 게임에 배팅을  싶.#r(남은เวลา 30วินาที 미만 배팅 불가)#b#l\r\n";
            } else {
                int type = getPlayer().getOneInfoQuestInteger(777777, "type");
                int flag = getPlayer().getOneInfoQuestInteger(777777, "flag");
                text += "#b#h0#님의 ปัจจุบัน 배팅 สถานะ : " + getBetString(type, flag) + "#k\r\n";
            }
            text += "#L10#배팅 รางวัล을 수령 싶.#l\r\n" +
                    "#L11#사다리 게임에 대해서 อธิบาย을 듣고 싶.#l";
            selection = self.askMenu(text);

        } else if (checkReward()) {
            selection = self.askMenu("#h0# 반갑\r\n저는 " + ServerConstants.serverName + "서버의 사다리를 담당있는 컴퓨터.\r\n\r\n#r#e사다리" + ContentsManager.SpeedLadderGame.getCurrentRound() + "회차 ผลลัพธ์ 까지 " + recordToString + "#n#k" +
                    "\r\n\r\n" +
                    "#L10##b#e배팅 รางวัล을 수령 싶.#k#n#l\r\n" +
                    "#L11#사다리 게임에 대해서 อธิบาย을 듣고 싶.#l");
        } else {
            String text = "#h0# 반갑.\r\n저는 " + ServerConstants.serverName + "서버의 사다리를 담당있는 컴퓨터.\r\n\r\n#r#e사다리" + ContentsManager.SpeedLadderGame.getCurrentRound() + "회차 ผลลัพธ์ 까지 " + recordToString + "#n#k" +
                    "\r\n\r\n";
            if (getPlayer().getOneInfoQuestInteger(777777, "round") == 0) {
                text += "#b#L0#사다리 게임에 배팅을  싶.#l\r\n";
            } else {
                if (getPlayer().getOneInfoQuestInteger(777777, "round") == ContentsManager.SpeedLadderGame.getCurrentRound()) {
                    int type = getPlayer().getOneInfoQuestInteger(777777, "type");
                    int flag = getPlayer().getOneInfoQuestInteger(777777, "flag");
                    text += "#b#h0#님의 ปัจจุบัน 배팅 สถานะ : " + getBetString(type, flag) + "#k\r\n";
                } else {
                    text += "#b#L0#사다리 게임에 배팅을  싶.#l\r\n";
                }
            }
            text += "#L10#배팅 รางวัล을 수령 싶.#l\r\n" +
                    "#L11#사다리 게임에 대해서 อธิบาย을 듣고 싶.#l";
            selection = self.askMenu(text);
        }
        String canBet = "\r\nปัจจุบัน มี Meso : " + decFormat.format(getPlayer().getMeso()) + "Meso";
        switch (selection) {
            case 0: //출발 좌
                if (checkReward()) {
                    self.sayOk("수령하지 않은 รางวัล이 있. 먼저 รางวัล을 수령โปรด.");
                    return;
                }
                if (getPlayer().getOneInfoQuestInteger(777777, "round") == ContentsManager.SpeedLadderGame.getCurrentRound()) {
                    self.sayOk("이미 배팅이 ดำเนินการ된สถานะ.");
                    return;
                }
                if (!canGame()) {
                    self.sayOk("남은เวลา이 30วินาที 미만이라 배팅할 수 없.");
                    return;
                }
                int v = self.askMenu("<배팅เมนู>#k#n\r\n\r\n#r#e사다리" + ContentsManager.SpeedLadderGame.getCurrentRound() + "회차 ผลลัพธ์ 까지 " + recordToString + "#n#k" +
                        "\r\n\r\n" +
                        "#b#L0#사다리 (출발 [좌] x1.85)#l\r\n" +
                        "#L1#사다리 (출발 [우] x1.85)#l\r\n" +
                        "#L2#사다리 (줄수 [4줄] x1.85)#l\r\n" +
                        "#L3#사다리 (줄수 [3줄] x1.85)#l\r\n" +
                        "#L4#사다리 (ผลลัพธ์ [홀] x1.85)#l\r\n" +
                        "#L5#사다리 (ผลลัพธ์ [짝] x1.85)#l\r\n" +
                        "#L6#사다리 (출발+줄수 [좌출4줄] x3.6)#l\r\n" +
                        "#L7#사다리 (출발+줄수 [좌출3줄] x3.6)#l\r\n" +
                        "#L8#사다리 (출발+줄수 [우출4줄] x3.6)#l\r\n" +
                        "#L9#사다리 (출발+줄수 [우출3줄] x3.6)#l");
                if (v == 0 || v == 1) {
                    String reward;
                    if (v == 0) {
                        reward = self.askText("#e#b출발 [좌] x1.85#n#k เลือก하셨.\r\n얼마를 배팅ต้องการหรือไม่. [ปัจจุบัน는 Meso 이용เป็นไปได้.]" + canBet);
                    } else {
                        reward = self.askText("#e#b출발 [우] x1.85#n#k เลือก하셨.\r\n얼마를 배팅ต้องการหรือไม่. [ปัจจุบัน는 Meso 이용เป็นไปได้.]" + canBet);
                    }
                    String pattern = "^[0-9]*$"; //숫자만
                    boolean regex = Pattern.matches(pattern, reward);
                    if (!regex) {
                        getPlayer().dropMessage(1, "숫자만 입력하실 수 있.");
                        return;
                    }
                    if (Long.parseLong(reward) > getPlayer().getMeso()) {
                        self.sayOk("잘못된 ขอ.");
                        return;
                    }
                    if (Long.parseLong(reward) <= 0) {
                        if (Long.parseLong(reward) == 0) {
                            self.sayOk("1이상 입력เป็นไปได้.");
                        }
                        return;
                    }
                    //type : 1 : 출발만, 2 : 줄수 , 3 : ผลลัพธ์ , 4 : 출발 + 줄수
                    //flag : 1 : 우출, 3줄, 홀
                    //flag : 2 : 좌출, 4줄, 짝
                    //flag : 4 : 좌4
                    //flag : 8 : 좌3
                    //flag : 16 : 우4
                    //flag : 32 : 우3
                    if (!canGame()) {
                        self.sayOk("남은เวลา이 30วินาที 미만이라 배팅할 수 없.");
                        return;
                    }
                    getPlayer().updateOneInfo(777777, "round", String.valueOf(ContentsManager.SpeedLadderGame.getCurrentRound()));
                    getPlayer().updateOneInfo(777777, "type", "1");
                    if (v == 0) { //좌출
                        ContentsManager.SpeedLadderGame.addBetMan(getPlayer(), (byte) 1, (byte) 2, Long.parseLong(reward));
                        getPlayer().updateOneInfo(777777, "flag", "2");
                    } else { //우출
                        ContentsManager.SpeedLadderGame.addBetMan(getPlayer(), (byte) 1, (byte) 1, Long.parseLong(reward));
                        getPlayer().updateOneInfo(777777, "flag", "1");
                    }
                    getPlayer().updateOneInfo(777777, "reward", reward);
                    getPlayer().gainMeso(-Long.parseLong(reward), false);
                } else if (v == 2 || v == 3) {
                    String reward;
                    if (v == 2) {
                        reward = self.askText("#e#b줄수 [4] x1.85#n#k เลือก하셨.\r\n얼마를 배팅ต้องการหรือไม่. [ปัจจุบัน는 Meso 이용เป็นไปได้.]" + canBet);
                    } else {
                        reward = self.askText("#e#b줄수 [3] x1.85#n#k เลือก하셨.\r\n얼마를 배팅ต้องการหรือไม่. [ปัจจุบัน는 Meso 이용เป็นไปได้.]" + canBet);
                    }
                    String pattern = "^[0-9]*$"; //숫자만
                    boolean regex = Pattern.matches(pattern, reward);
                    if (!regex) {
                        getPlayer().dropMessage(1, "숫자만 입력하실 수 있.");
                        return;
                    }
                    if (Long.parseLong(reward) > getPlayer().getMeso()) {
                        self.sayOk("잘못된 ขอ.");
                        return;
                    }
                    if (Long.parseLong(reward) <= 0) {
                        if (Long.parseLong(reward) == 0) {
                            self.sayOk("1이상 입력เป็นไปได้.");
                        }
                        return;
                    }
                    //type : 1 : 출발만, 2 : 줄수 , 3 : ผลลัพธ์ , 4 : 출발 + 줄수
                    //flag : 1 : 우출, 3줄, 홀
                    //flag : 2 : 좌출, 4줄, 짝
                    //flag : 4 : 좌4
                    //flag : 8 : 좌3
                    //flag : 16 : 우4
                    //flag : 32 : 우3
                    if (!canGame()) {
                        self.sayOk("남은เวลา이 30วินาที 미만이라 배팅할 수 없.");
                        return;
                    }
                    getPlayer().updateOneInfo(777777, "round", String.valueOf(ContentsManager.SpeedLadderGame.getCurrentRound()));
                    getPlayer().updateOneInfo(777777, "type", "2");
                    if (v == 2) { // 4줄
                        ContentsManager.SpeedLadderGame.addBetMan(getPlayer(), (byte) 2, (byte) 2, Long.parseLong(reward));
                        getPlayer().updateOneInfo(777777, "flag", "2");
                    } else { //3줄
                        ContentsManager.SpeedLadderGame.addBetMan(getPlayer(), (byte) 2, (byte) 1, Long.parseLong(reward));
                        getPlayer().updateOneInfo(777777, "flag", "1");
                    }
                    getPlayer().updateOneInfo(777777, "reward", reward);
                    getPlayer().gainMeso(-Long.parseLong(reward), false);
                } else if (v == 4 || v == 5) {
                    String reward;
                    if (v == 4) {
                        reward = self.askText("#e#b[ผลลัพธ์ : 홀] x1.85#n#k เลือก하셨.\r\n얼마를 배팅ต้องการหรือไม่. [ปัจจุบัน는 Meso 이용เป็นไปได้.]" + canBet);
                    } else {
                        reward = self.askText("#e#b[ผลลัพธ์ : 짝] x1.85#n#k เลือก하셨.\r\n얼마를 배팅ต้องการหรือไม่. [ปัจจุบัน는 Meso 이용เป็นไปได้.]" + canBet);
                    }
                    String pattern = "^[0-9]*$"; //숫자만
                    boolean regex = Pattern.matches(pattern, reward);
                    if (!regex) {
                        getPlayer().dropMessage(1, "숫자만 입력하실 수 있.");
                        return;
                    }
                    if (Long.parseLong(reward) > getPlayer().getMeso()) {
                        self.sayOk("잘못된 ขอ.");
                        return;
                    }
                    if (Long.parseLong(reward) <= 0) {
                        if (Long.parseLong(reward) == 0) {
                            self.sayOk("1이상 입력เป็นไปได้.");
                        }
                        return;
                    }
                    //type : 1 : 출발만, 2 : 줄수 , 3 : ผลลัพธ์ , 4 : 출발 + 줄수
                    //flag : 1 : 우출, 3줄, 홀
                    //flag : 2 : 좌출, 4줄, 짝
                    //flag : 4 : 좌4
                    //flag : 8 : 좌3
                    //flag : 16 : 우4
                    //flag : 32 : 우3
                    if (!canGame()) {
                        self.sayOk("남은เวลา이 30วินาที 미만이라 배팅할 수 없.");
                        return;
                    }
                    getPlayer().updateOneInfo(777777, "round", String.valueOf(ContentsManager.SpeedLadderGame.getCurrentRound()));
                    getPlayer().updateOneInfo(777777, "type", "3");
                    if (v == 4) { // 홀
                        ContentsManager.SpeedLadderGame.addBetMan(getPlayer(), (byte) 3, (byte) 1, Long.parseLong(reward));
                        getPlayer().updateOneInfo(777777, "flag", "1");
                    } else { //3줄
                        ContentsManager.SpeedLadderGame.addBetMan(getPlayer(), (byte) 3, (byte) 2, Long.parseLong(reward));
                        getPlayer().updateOneInfo(777777, "flag", "2");
                    }
                    getPlayer().updateOneInfo(777777, "reward", reward);
                    getPlayer().gainMeso(-Long.parseLong(reward), false);
                } else if (v == 6 || v == 7) {
                    String reward = "";
                    if (v == 6) {
                        reward = self.askText("#e#b[출발+줄수 좌4] x3.6#n#k เลือก하셨.\r\n얼마를 배팅ต้องการหรือไม่. [ปัจจุบัน는 Meso 이용เป็นไปได้.]" + canBet);
                    } else {
                        reward = self.askText("#e#b[출발+줄수 좌3] x3.6#n#k เลือก하셨.\r\n얼마를 배팅ต้องการหรือไม่. [ปัจจุบัน는 Meso 이용เป็นไปได้.]" + canBet);
                    }
                    String pattern = "^[0-9]*$"; //숫자만
                    boolean regex = Pattern.matches(pattern, reward);
                    if (!regex) {
                        getPlayer().dropMessage(1, "숫자만 입력하실 수 있.");
                        return;
                    }
                    if (Long.parseLong(reward) > getPlayer().getMeso()) {
                        self.sayOk("잘못된 ขอ.");
                        return;
                    }
                    if (Long.parseLong(reward) <= 0) {
                        if (Long.parseLong(reward) == 0) {
                            self.sayOk("1이상 입력เป็นไปได้.");
                        }
                        return;
                    }
                    //type : 1 : 출발만, 2 : 줄수 , 3 : ผลลัพธ์ , 4 : 출발 + 줄수
                    //flag : 1 : 우출, 3줄, 홀
                    //flag : 2 : 좌출, 4줄, 짝
                    //flag : 4 : 좌4
                    //flag : 8 : 좌3
                    //flag : 16 : 우4
                    //flag : 32 : 우3
                    if (!canGame()) {
                        self.sayOk("남은เวลา이 30วินาที 미만이라 배팅할 수 없.");
                        return;
                    }
                    getPlayer().updateOneInfo(777777, "round", String.valueOf(ContentsManager.SpeedLadderGame.getCurrentRound()));
                    getPlayer().updateOneInfo(777777, "type", "4");
                    if (v == 6) { // 좌4
                        ContentsManager.SpeedLadderGame.addBetMan(getPlayer(), (byte) 4, (byte) 4, Long.parseLong(reward));
                        getPlayer().updateOneInfo(777777, "flag", "4");
                    } else { //좌3
                        ContentsManager.SpeedLadderGame.addBetMan(getPlayer(), (byte) 4, (byte) 8, Long.parseLong(reward));
                        getPlayer().updateOneInfo(777777, "flag", "8");
                    }
                    getPlayer().updateOneInfo(777777, "reward", reward);
                    getPlayer().gainMeso(-Long.parseLong(reward), false);
                } else if (v == 8 || v == 9) {
                    String reward;
                    if (v == 8) {
                        reward = self.askText("#e#b[출발+줄수 우4] x3.6#n#k เลือก하셨.\r\n얼마를 배팅ต้องการหรือไม่. [ปัจจุบัน는 Meso 이용เป็นไปได้.]" + canBet);
                    } else {
                        reward = self.askText("#e#b[출발+줄수 우3] x3.6#n#k เลือก하셨.\r\n얼마를 배팅ต้องการหรือไม่. [ปัจจุบัน는 Meso 이용เป็นไปได้.]" + canBet);
                    }
                    String pattern = "^[0-9]*$"; //숫자만
                    boolean regex = Pattern.matches(pattern, reward);
                    if (!regex) {
                        getPlayer().dropMessage(1, "숫자만 입력하실 수 있.");
                        return;
                    }
                    if (Long.parseLong(reward) > getPlayer().getMeso()) {
                        self.sayOk("잘못된 ขอ.");
                        return;
                    }
                    if (Long.parseLong(reward) <= 0) {
                        if (Long.parseLong(reward) == 0) {
                            self.sayOk("1이상 입력เป็นไปได้.");
                        }
                        return;
                    }
                    //type : 1 : 출발만, 2 : 줄수 , 3 : ผลลัพธ์ , 4 : 출발 + 줄수
                    //flag : 1 : 우출, 3줄, 홀
                    //flag : 2 : 좌출, 4줄, 짝
                    //flag : 4 : 좌4
                    //flag : 8 : 좌3
                    //flag : 16 : 우4
                    //flag : 32 : 우3
                    if (!canGame()) {
                        self.sayOk("남은เวลา이 30วินาที 미만이라 배팅할 수 없.");
                        return;
                    }
                    getPlayer().updateOneInfo(777777, "round", String.valueOf(ContentsManager.SpeedLadderGame.getCurrentRound()));
                    getPlayer().updateOneInfo(777777, "type", "4");
                    if (v == 8) { // 우4
                        getPlayer().updateOneInfo(777777, "flag", "16");
                        ContentsManager.SpeedLadderGame.addBetMan(getPlayer(), (byte) 4, (byte) 16, Long.parseLong(reward));
                    } else { //우3
                        ContentsManager.SpeedLadderGame.addBetMan(getPlayer(), (byte) 4, (byte) 32, Long.parseLong(reward));
                        getPlayer().updateOneInfo(777777, "flag", "32");
                    }
                    getPlayer().updateOneInfo(777777, "reward", reward);
                    getPlayer().gainMeso(-Long.parseLong(reward), false);
                }
                self.sayOk("สำเร็จ적으로 배팅되었.");
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
                        self.sayOk(decFormat.format((long) (reward * 1.85)) + "Meso 지급되었.");
                    } else {
                        getPlayer().gainMeso((long) (reward * 3.6), false);
                        self.sayOk(decFormat.format((long) (reward * 3.6)) + "Meso 지급되었.");
                    }
                    getPlayer().updateOneInfo(777777, "reward", "0");
                } else {
                    self.sayOk("수령할 수 있는 รางวัล이 없.");
                }
                break;
            case 11:
                self.sayOk("#e#b<배당률>#n#k\r\n출발 - 좌출발, 우출발 (1.85배)\r\n줄수 - 3줄, 4줄 (1.85배)\r\nผลลัพธ์ - 홀, 짝 (1.85배)\r\n출발 + 줄수 - 좌4, 좌3, 우4, 우3 (3.6배)\r\n\r\n#r#e<사다리 용어>#k#n\r\n좌출 : 왼쪽에서 출발\r\n" +
                        "우출 : 오른쪽에서 출발\r\n3줄 : 사다리 세줄\r\n4줄 : 사다리 네줄\r\n좌4 : 왼쪽에서 출발 + 사다리 네줄\r\n좌3 : 왼쪽에서 출발 + 사다리 세줄\r\n우4 : 오른쪽에서 출발 + 사다리 네줄\r\n우3 : 오른쪽에서 출발 + 사다리 세줄");
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
            //type : 1 : 출발만, 2 : 줄수 , 3 : ผลลัพธ์ , 4 : 출발 + 줄수
            //flag : 1 : 우출, 3줄, 홀
            //flag : 2 : 좌출, 4줄, 짝
            //flag : 4 : 좌4
            //flag : 8 : 좌3
            //flag : 16 : 우4
            //flag : 32 : 우3
            int type = getPlayer().getOneInfoQuestInteger(777777, "type");
            int flag = getPlayer().getOneInfoQuestInteger(777777, "flag");
            if (type == 1) { //출발만
                int right = ladder.getRight();
                if ((right == 1 && flag == 1) || (right == 0 && flag == 2)) {
                    reward = true;
                } else {
                    reward = false;
                }
            } else if (type == 2) { //줄수
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
            } else if (type == 4) { //출발 + 줄수
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
        //type : 1 : 출발만, 2 : 줄수 , 3 : ผลลัพธ์ , 4 : 출발 + 줄수
        //flag : 1 : 우출, 3줄, 홀
        //flag : 2 : 좌출, 4줄, 짝
        //flag : 4 : 좌4
        //flag : 8 : 좌3
        //flag : 16 : 우4
        //flag : 32 : 우3
        switch (type) {
            case 1: //출발
                //출발 [좌]
                //출발 [우]
                if (flag == 1) {
                    return "출발 [우]";
                } else {
                    return "출발 [좌]";
                }
            case 2: //줄수
                //줄수 [4]
                //줄수 [3]
                if (flag == 1) {
                    return "줄수 [3]";
                } else {
                    return "줄수 [4]";
                }
            case 3: //ผลลัพธ์
                //ผลลัพธ์ [홀]
                //ผลลัพธ์ [짝]
                if (flag == 1) {
                    return "ผลลัพธ์ [홀]";
                } else {
                    return "ผลลัพธ์ [짝]";
                }
            case 4: //출발+줄수
                //[출발+줄수 좌4]
                //[출발+줄수 좌3]
                //[출발+줄수 우4]
                //[출발+줄수 우3]
                //flag : 4 : 좌4
                //flag : 8 : 좌3
                //flag : 16 : 우4
                //flag : 32 : 우3
                if (flag == 4) {
                    return "출발+줄수 [좌4]";
                } else if (flag == 8) {
                    return "출발+줄수 [좌3]";
                } else if (flag == 16) {
                    return "출발+줄수 [우4]";
                } else if (flag == 32) {
                    return "출발+줄수 [우3]";
                }
        }
        return "";
    }
    
    public void characterNameChange() {
        initNPC(MapleLifeFactory.getNPC(9062010));
        int v = self.askMenu("안녕하세요! 당신의 이름을 바꿔드릴 수 있는 #b미스터 뉴네임#k. 무엇을 도와드릴까요?\r\n\r\n#b#L0#ตัวละคร 이름 เปลี่ยน하기 (ตัวละคร 이름 เปลี่ยน 쿠폰จำเป็น)#l\r\n#L1#สนทนา를 สิ้นสุด한다.#l");
        if (v != 0) {
            return;
        }
        getPlayer().send(NameChanger((byte)9, 4034803));
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

            self.say("#b#i4310229:##t4310229##k #b" + coin + "개#k 코인을 지급받았.");
        } else {
            self.say("수령 할 수 있는 코인이 없.");
        }
    }
}
