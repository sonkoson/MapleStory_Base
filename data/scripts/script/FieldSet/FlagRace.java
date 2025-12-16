package script.FieldSet;

import network.models.CField;
import network.models.CWvsContext;
import objects.context.guild.GuildContentsType;
import objects.fields.Field;
import objects.fields.fieldset.FieldSet;
import objects.fields.fieldset.childs.FlagRaceN1Enter;
import objects.fields.fieldset.childs.FlagRaceN2Enter;
import objects.fields.fieldset.childs.FlagRaceN3Enter;
import objects.fields.fieldset.instance.FlagRaceN1;
import objects.fields.fieldset.instance.FlagRaceN2;
import objects.fields.fieldset.instance.FlagRaceN3;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.item.Item;
import scripting.ScriptMessageFlag;
import scripting.newscripting.Script;
import scripting.newscripting.ScriptEngineNPC;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FlagRace extends ScriptEngineNPC {


    public void flag_NPC() {
        String course = "한낮의 설원";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date1 = sdf.parse("20210802");
            Date date2 = new Date();
            long time = date2.getTime() - date1.getTime();
            long day = TimeUnit.MILLISECONDS.toDays(time);
            if ((day / 7) % 3 == 0) {
                course = "한낮의 설원";
            } else if ((day / 7) % 3 == 1) {
                course = "석양의 설원";
            } else if ((day / 7) % 3 == 2) {
                course = "한밤의 설원";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int v = self.askMenu("#fs11#\r\n먼 โลก에서 게임을 만들 땐 이렇게 인기가 좋지 않았는데... ฉัน의 아름다운 เดือน드에서 플래เขา 레이스를 즐기고 싶은거지?\r\n\r\n이번 สัปดาห์의 코스 : #r#e" + course + "#n#k\r\n\r\n#b#L0#플래เขา 레이스에 도전 싶어요.#l#k\r\n#b#L1#플래เขา 레이스 연습 รอ실로 ย้าย시켜 สัปดาห์세요.#l#k\r\n#b#L2#플래เขา 레이스에 대해서 알려สัปดาห์세요.#l#k", ScriptMessageFlag.NpcReplacedByNpc);
        switch (v) {
            case 0: //플래เขา 레이스에 도전 싶어요.
                if (getPlayer().getGuild() == null) {
                    self.say("#fs11#\r\n플래เขา 레이스는 #rสัปดาห์간 กิลด์ ภารกิจ คะแนน 1 이상 ได้รับ한 자#k만이 เข้าร่วม할 수 있어. เงื่อนไข을 갖춘 หลัง에 다시 시도해 줘.", ScriptMessageFlag.NpcReplacedByNpc);
                } else {
                    if (getPlayer().getGuild().getPointLogByType(GuildContentsType.WEEK_MISSIONS, getPlayer()) < 0) {
                        self.say("#fs11#\r\n플래เขา 레이스는 #rสัปดาห์간 กิลด์ ภารกิจ คะแนน 1 이상 ได้รับ한 자#k만이 เข้าร่วม할 수 있어. เงื่อนไข을 갖춘 หลัง에 다시 시도해 줘.", ScriptMessageFlag.NpcReplacedByNpc);
                    } else {
                        FieldSet fieldSet = fieldSet("FlagRaceEnter");
                        if (fieldSet == null) {
                            self.sayOk("#fs11#지금은 플래เขา 레이스을 이용할 수 없어.");
                            return;
                        }
                        int enter = fieldSet.enter(target.getId(), 0);
                        if (enter == -1) self.say("#fs11#알 수 없는 이유로 เข้า할 수 없. 잠시 후에 다시 시도해 สัปดาห์십시오.");
                        else if (enter == 1) self.sayOk("#fs11#<플래เขา레이스> คนเดียว서만 도전할 수 있어.\r\nปาร์ตี้ ปลดล็อก 다시 찾아오도록 해");
                        else if (enter == 2) self.say("#fs11#เลเวล ต่ำสุด " + fieldSet.minLv + " 이상이어야 플래เขา레이스를 할 수 있어.");
                        else if (enter == -2)
                            self.sayOk("#fs11#플래เขา레이스는 매시 30นาที에 개최 돼 เวลา을 ยืนยัน 다시 시도해 줘");
                        else if (enter == -3)
                            self.sayOk("#fs11#วัน요วัน 23시 ~ เดือน요วัน 01시는 노블레스 คะแนน 정산เวลา이라 연습โหมด만 เป็นไปได้해~! เวลา을 ยืนยัน 다시 시도해 줘");
                    }
                }
                break;
            case 1: //플래เขา 레이스 연습 รอ실로 ย้าย시켜 สัปดาห์세요.
                if (1 == self.askYesNo("#fs11##b플래เขา 레이스 연습 รอ실#k 지금 ย้าย할래?", ScriptMessageFlag.NpcReplacedByNpc)) {
                    registerTransferField(942003050); //플래เขา 레이스 : 연습 รอ실
                }
                break;
            case 2: //플래เขา 레이스에 대해서 알려สัปดาห์세요.
                int vv = self.askMenu("#fs11#쿠쿠쿠, 플래เขา 레이스에 대해서 뭐가 เขา렇게 알고 싶은데?\r\n\r\n#b#L0#플래เขา 레이스가 뭔가요?#l\r\n#b#L1#플래เขา 레이스를  뭐가 좋죠?#l\r\n#L4#플래เขา 레이스의 랭킹 คะแนน 기준을 알고 싶어요.#l\r\n#L3#ใคร 것도 아니에요.#l", ScriptMessageFlag.NpcReplacedByNpc);
                switch (vv) {
                    case 0: //플래เขา 레이스가 뭔가요?
                        self.say("#fs11#\r\n내 บน대한 창작น้ำ인 플래เขา 레이스에 대해 알고 싶은 거야?", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("#fs11#\r\n룰은 간단해. #r매시 30นาที마다#k 개최, #rสัปดาห์간 กิลด์ ภารกิจ คะแนน 1 이상인 กิลด์원#k만이 เข้าร่วม할 수 있어.", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("#fs11#\r\n빠르게 골인 지점ถึง 달리면 된다고. น้ำ론 장애น้ำ เหมือนกัน게 조----------------------------금은 있을 수도 있어.", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("#fs11#\r\n플래เขา 레이스 내에서는 오로지 ย้ายและ 점프만으로 정정당당하게 승부... 하는 건 아니고, 코스 내에서만 ใช้할 수 있는 플래เขา สกิล들이 있어.", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("#fs11#\r\n플래เขา สกิล들และ แผนที่ 배치된 장치들을 잘 조합 통상의 สถานการณ์에서는 갈 수 없는 ถนน을 가거ฉัน 더 빠르게 코스를 สัปดาห์파할 수 있을 거야.", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("#fs11#\r\n아, เขา리고 레이스 เข้า 시 ทั้งหมด 변신 สถานะ가 ปลดล็อก 레이스 중 변신 포션을 ใช้하더라도 변신이 되지 않을 거야. 소중한 น้ำ약을 날려버릴 수도 있으니 조심하라고.", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("#fs11#\r\n101เลเวล 이상만 เข้าร่วม할 수 있으니 ยืนยัน해 두라고. 이미 101เลเวล 넘은지 한참 됐다면야 뭐...", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("#fs11#\r\nเขา럼, 힘내라고.", ScriptMessageFlag.NpcReplacedByNpc);
                        break;
                    case 1: //플래เขา 레이스를  뭐가 좋죠?
                        self.say("#fs11#\r\n뭐가 좋으냐고? ถนน이 있으니까 달리고, 장애น้ำ이 있으니까 넘는 거지 꼭 좋을 게 있어야 하ฉัน?", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("#fs11#\r\n...농담이고, กิลด์ สกิล 중 노블레스 สกิล โดยตรง 투자할 수 있는 SP วันสัปดาห์วัน간의 플래เขา 레이스 กิลด์ 랭킹에 따라 받을 수 있지.", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("#fs11##b#e1บน : 25개의 SP\r\n2บน : 22개의 SP\r\n3บน : 20개의 SP\r\n4บน ~ 10บน : 18개의 SP\r\n상บน 10% : 16개의 SP\r\n상บน 20% : 14개의 SP\r\n상บน 30% : 12개의 SP\r\n상บน 40% : 10개의 SP\r\n상บน 50% : 9개의 SP\r\n상บน 60% : 8개의 SP\r\n상บน 70% : 7개의 SP\r\n상บน 80% : 6개의 SP\r\n1,000คะแนน 이상 : 5개의 SP#n#k\r\n\r\n#r#e※ 단, 1,000คะแนน 이상 ได้รับ하지 못 순บน ใน에 들었더라도 รางวัล을 받을 수 없.#k#n", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("#fs11#\r\n플래เขา 레이스 กิลด์ 랭킹은 แต่ละ กิลด์원의 플래เขา 레이스 점수를 합산한 점수로 매겨지지. 달리기 실력이 빼어난 동료가 많은 กิลด์ 더 สูง 순บน를 가져갈 수 있어.", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("#fs11#\r\n너의 플래เขา 레이스 점수는 สัปดาห์간 최고 기록으로 결정돼. 지금의 기록이 마음에 들지 않으면 เมื่อไหร่든 다시 도전하라고. น้ำ론 열려 있을 때 말이야.", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("#fs11#\r\n아, กิลด์ 탈퇴하거ฉัน 추ห้อง당 너의 สัปดาห์간 최고 기록이 วินาที기화 กิลด์ 합산 점수도 เขา만큼 หัก되니까 반드시 조심하라고.", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("#fs11#\r\nเขา리고 랭킹은 영원하지 않아. 매สัปดาห์ เดือน요วัน 0시에 랭킹และ 노블레스 สกิล ทั้งหมด วินาที기화 เขา때ถึง 합산된 랭킹을 반영해서 노블레스 สกิลSP สัปดาห์니까. 이번 สัปดาห์에 ล้มเหลว했다고 해서 너무 실망하지 말라구.", ScriptMessageFlag.NpcReplacedByNpc);
                        break;
                    case 4: //플래เขา 레이스의 랭킹 คะแนน 기준을 알고 싶어요.
                        self.say("#fs11#\r\n이제 플래เขา 레이스에서 얻을 수 있는 랭킹 คะแนน 세 바퀴를 ทั้งหมด 완สัปดาห์하는데 걸린 เวลา에 따라 결정돼. 남들และ 상관 없이 너만 잘 달리면 สูง 점수를 얻을 수 มี는 뜻이지.", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("#fs11#\r\n랭킹 คะแนน 기준은 วันสัปดาห์วัน 간 너의 최고 기록을 기준으로 결정돼. 더 สูง 기록을 얻어 기록을 갱신할 수 있으니까 기록이 아쉬우면 다시 도전하라고.", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("#fs11#\r\nครั้งสุดท้าย으로, 플래เขา 레이스의 코스는 매สัปดาห์ เดือน요วัน 0시마다 바뀌게 돼. 코스마다 점수 기준이 다르니까 꼭 참고해서 똑똑하게 달리라고.", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("#fs11#\r\n#e[한낮의 설원 랭킹 คะแนน 기준]#n\r\n\r\n2นาที 10วินาที 미만 : #b#e1,000 คะแนน#n#k\r\n2นาที 10วินาที ~ 2นาที 15วินาที 미만 : #b#e800 คะแนน#n#k\r\n2นาที 15วินาที ~ 2นาที 20วินาที 미만 : #b#e650 คะแนน#n#k\r\n2นาที 20วินาที ~ 2นาที 25วินาที 미만 : #b#e550 คะแนน#n#k\r\n2นาที 25วินาที ~ 2นาที 30วินาที 미만 : #b#e450 คะแนน#n#k\r\n2นาที 30วินาที ~ 2นาที 35วินาที 미만 : #b#e400 คะแนน#n#k\r\n2นาที 35วินาที ~ 2นาที 40วินาที 미만 : #b#e350 คะแนน#n#k\r\n2นาที 40วินาที ~ 2นาที 50วินาที 미만 : #b#e300 คะแนน#n#k\r\n2นาที 50วินาที ~ 3นาที 00วินาที 미만 : #b#e250 คะแนน#n#k\r\n3นาที 00วินาที ~ 3นาที 10วินาที 미만 : #b#e200 คะแนน#n#k\r\n3นาที 10วินาที 이상 : #b#e100 คะแนน#n#k", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("#fs11#\r\n#e[석양의 설원 랭킹 คะแนน 기준]#n\r\n\r\n1นาที 50วินาที 미만 : #b#e1,000 คะแนน#n#k\r\n1นาที 50วินาที ~ 1นาที 55วินาที 미만 : #b#e800 คะแนน#n#k\r\n1นาที 55วินาที ~ 2นาที 00วินาที 미만 : #b#e650 คะแนน#n#k\r\n2นาที 00วินาที ~ 2นาที 05วินาที 미만 : #b#e550 คะแนน#n#k\r\n2นาที 05วินาที ~ 2นาที 10วินาที 미만 : #b#e450 คะแนน#n#k\r\n2นาที 10วินาที ~ 2นาที 15วินาที 미만 : #b#e400 คะแนน#n#k\r\n2นาที 15วินาที ~ 2นาที 20วินาที 미만 : #b#e350 คะแนน#n#k\r\n2นาที 20วินาที ~ 2นาที 30วินาที 미만 : #b#e300 คะแนน#n#k\r\n2นาที 30วินาที ~ 2นาที 40วินาที 미만 : #b#e250 คะแนน#n#k\r\n2นาที 40วินาที ~ 2นาที 50วินาที 미만 : #b#e200 คะแนน#n#k\r\n2นาที 50วินาที 이상 : #b#e100 คะแนน#n#k", ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("#fs11#\r\n#e[한밤의 설원 랭킹 คะแนน 기준]#n\r\n\r\n2นาที 30วินาที 미만 : #b#e1,000 คะแนน#n#k\r\n2นาที 30วินาที ~ 2นาที 35วินาที 미만 : #b#e800 คะแนน#n#k\r\n2นาที 35วินาที ~ 2นาที 40วินาที 미만 : #b#e650 คะแนน#n#k\r\n2นาที 40วินาที ~ 2นาที 45วินาที 미만 : #b#e550 คะแนน#n#k\r\n2นาที 45วินาที ~ 2นาที 50วินาที 미만 : #b#e450 คะแนน#n#k\r\n2นาที 50วินาที ~ 2นาที 55วินาที 미만 : #b#e400 คะแนน#n#k\r\n2นาที 55วินาที ~ 3นาที 00วินาที 미만 : #b#e350 คะแนน#n#k\r\n3นาที 00วินาที ~ 3นาที 10วินาที 미만 : #b#e300 คะแนน#n#k\r\n3นาที 10วินาที ~ 3นาที 20วินาที 미만 : #b#e250 คะแนน#n#k\r\n3นาที 20วินาที ~ 3นาที 30วินาที 미만 : #b#e200 คะแนน#n#k\r\n3นาที 30วินาที 이상 : #b#e100 คะแนน#n#k", ScriptMessageFlag.NpcReplacedByNpc);
                        break;
                    case 3: //ใคร것도 아니에요
                        self.sayOk("#fs11#\r\nเขา래. อะไร을 하건 하지 않건 너의 자유지.", ScriptMessageFlag.NpcReplacedByNpc);
                        break;
                }
                break;
        }
    }

    public void mad_Designer() {
        switch (getPlayer().getMapId()) {
            case 942003000:
            case 942003001:
            case 942003002: {
                if (getPlayer().getOneInfoQuestInteger(32581, "mode") > 1) {
                    if (1 == self.askAccept("#fs11#쿠쿠쿠, 연습 코스는 어땠어? 다시 연습 รอ실로 돌려보내 줄까?")) {
                        registerTransferField(942003050); //플래เขา 레이스 : 연습 รอ실
                    }
                } else {
                    int v = self.askMenu("#fs11#플래เขา 레이스는 재미있게 즐겼어? 무슨 วัน이야?\r\n#b#L0# 곳에서 ฉัน가고 싶어요.#l\r\n#b#L1#플래เขา 레이스에 대해 알려สัปดาห์세요.#l");
                    switch (v) {
                        case 1: { //플래เขา 레이스에 대해서 알려สัปดาห์세요.
                            int vv = self.askMenu("#fs11#쿠쿠쿠, 플래เขา 레이스에 대해서 뭐가 เขา렇게 알고 싶은데?\r\n\r\n#b#L0#플래เขา 레이스가 뭔가요?#l\r\n#b#L1#플래เขา 레이스를  뭐가 좋죠?#l\r\n#L4#플래เขา 레이스의 랭킹 คะแนน 기준을 알고 싶어요.#l\r\n#L3#ใคร 것도 아니에요.#l");
                            switch (vv) {
                                case 0: //플래เขา 레이스가 뭔가요?
                                    self.say("#fs11#\r\n내 บน대한 창작น้ำ인 플래เขา 레이스에 대해 알고 싶은 거야?");
                                    self.say("#fs11#\r\n룰은 간단해. #r매시 30นาที마다#k 개최, #rสัปดาห์간 กิลด์ ภารกิจ คะแนน 1 이상인 กิลด์원#k만이 เข้าร่วม할 수 있어.");
                                    self.say("#fs11#\r\n빠르게 골인 지점ถึง 달리면 된다고. น้ำ론 장애น้ำ เหมือนกัน게 조----------------------------금은 있을 수도 있어.");
                                    self.say("#fs11#\r\n플래เขา 레이스 내에서는 오로지 ย้ายและ 점프만으로 정정당당하게 승부... 하는 건 아니고, 코스 내에서만 ใช้할 수 있는 플래เขา สกิล들이 있어.");
                                    self.say("#fs11#\r\n플래เขา สกิล들และ แผนที่ 배치된 장치들을 잘 조합 통상의 สถานการณ์에서는 갈 수 없는 ถนน을 가거ฉัน 더 빠르게 코스를 สัปดาห์파할 수 있을 거야.");
                                    self.say("#fs11#\r\n아, เขา리고 레이스 เข้า 시 ทั้งหมด 변신 สถานะ가 ปลดล็อก 레이스 중 변신 포션을 ใช้하더라도 변신이 되지 않을 거야. 소중한 น้ำ약을 날려버릴 수도 있으니 조심하라고.");
                                    self.say("#fs11#\r\n101เลเวล 이상만 เข้าร่วม할 수 있으니 ยืนยัน해 두라고. 이미 101เลเวล 넘은지 한참 됐다면야 뭐...");
                                    self.say("#fs11#\r\nเขา럼, 힘내라고.");
                                    break;
                                case 1: //플래เขา 레이스를  뭐가 좋죠?
                                    self.say("#fs11#\r\n뭐가 좋으냐고? ถนน이 있으니까 달리고, 장애น้ำ이 있으니까 넘는 거지 꼭 좋을 게 있어야 하ฉัน?");
                                    self.say("#fs11#\r\n...농담이고, กิลด์ สกิล 중 노블레스 สกิล โดยตรง 투자할 수 있는 SP วันสัปดาห์วัน간의 플래เขา 레이스 กิลด์ 랭킹에 따라 받을 수 있지.");
                                    self.say("#fs11##b#e1บน : 25개의 SP\r\n2บน : 22개의 SP\r\n3บน : 20개의 SP\r\n4บน ~ 10บน : 18개의 SP\r\n상บน 10% : 16개의 SP\r\n상บน 20% : 14개의 SP\r\n상บน 30% : 12개의 SP\r\n상บน 40% : 10개의 SP\r\n상บน 50% : 9개의 SP\r\n상บน 60% : 8개의 SP\r\n상บน 70% : 7개의 SP\r\n상บน 80% : 6개의 SP\r\n1,000คะแนน 이상 : 5개의 SP#n#k\r\n\r\n#r#e※ 단, 1,000คะแนน 이상 ได้รับ하지 못 순บน ใน에 들었더라도 รางวัล을 받을 수 없.#k#n");
                                    self.say("#fs11#\r\n플래เขา 레이스 กิลด์ 랭킹은 แต่ละ กิลด์원의 플래เขา 레이스 점수를 합산한 점수로 매겨지지. 달리기 실력이 빼어난 동료가 많은 กิลด์ 더 สูง 순บน를 가져갈 수 있어.");
                                    self.say("#fs11#\r\n너의 플래เขา 레이스 점수는 สัปดาห์간 최고 기록으로 결정돼. 지금의 기록이 마음에 들지 않으면 เมื่อไหร่든 다시 도전하라고. น้ำ론 열려 있을 때 말이야.");
                                    self.say("#fs11#\r\n아, กิลด์ 탈퇴하거ฉัน 추ห้อง당 너의 สัปดาห์간 최고 기록이 วินาที기화 กิลด์ 합산 점수도 เขา만큼 หัก되니까 반드시 조심하라고.");
                                    self.say("#fs11#\r\nเขา리고 랭킹은 영원하지 않아. 매สัปดาห์ เดือน요วัน 0시에 랭킹และ 노블레스 สกิล ทั้งหมด วินาที기화 เขา때ถึง 합산된 랭킹을 반영해서 노블레스 สกิลSP สัปดาห์니까. 이번 สัปดาห์에 ล้มเหลว했다고 해서 너무 실망하지 말라구.");
                                    break;
                                case 4: //플래เขา 레이스의 랭킹 คะแนน 기준을 알고 싶어요.
                                    self.say("#fs11#\r\n이제 플래เขา 레이스에서 얻을 수 있는 랭킹 คะแนน 세 바퀴를 ทั้งหมด 완สัปดาห์하는데 걸린 เวลา에 따라 결정돼. 남들และ 상관 없이 너만 잘 달리면 สูง 점수를 얻을 수 มี는 뜻이지.");
                                    self.say("#fs11#\r\n랭킹 คะแนน 기준은 วันสัปดาห์วัน 간 너의 최고 기록을 기준으로 결정돼. 더 สูง 기록을 얻어 기록을 갱신할 수 있으니까 기록이 아쉬우면 다시 도전하라고.");
                                    self.say("#fs11#\r\nครั้งสุดท้าย으로, 플래เขา 레이스의 코스는 매สัปดาห์ เดือน요วัน 0시마다 바뀌게 돼. 코스마다 점수 기준이 다르니까 꼭 참고해서 똑똑하게 달리라고.");
                                    self.say("#fs11#\r\n#e[한낮의 설원 랭킹 คะแนน 기준]#n\r\n\r\n2นาที 10วินาที 미만 : #b#e1,000 คะแนน#n#k\r\n2นาที 10วินาที ~ 2นาที 15วินาที 미만 : #b#e800 คะแนน#n#k\r\n2นาที 15วินาที ~ 2นาที 20วินาที 미만 : #b#e650 คะแนน#n#k\r\n2นาที 20วินาที ~ 2นาที 25วินาที 미만 : #b#e550 คะแนน#n#k\r\n2นาที 25วินาที ~ 2นาที 30วินาที 미만 : #b#e450 คะแนน#n#k\r\n2นาที 30วินาที ~ 2นาที 35วินาที 미만 : #b#e400 คะแนน#n#k\r\n2นาที 35วินาที ~ 2นาที 40วินาที 미만 : #b#e350 คะแนน#n#k\r\n2นาที 40วินาที ~ 2นาที 50วินาที 미만 : #b#e300 คะแนน#n#k\r\n2นาที 50วินาที ~ 3นาที 00วินาที 미만 : #b#e250 คะแนน#n#k\r\n3นาที 00วินาที ~ 3นาที 10วินาที 미만 : #b#e200 คะแนน#n#k\r\n3นาที 10วินาที 이상 : #b#e100 คะแนน#n#k");
                                    self.say("#fs11#\r\n#e[석양의 설원 랭킹 คะแนน 기준]#n\r\n\r\n1นาที 50วินาที 미만 : #b#e1,000 คะแนน#n#k\r\n1นาที 50วินาที ~ 1นาที 55วินาที 미만 : #b#e800 คะแนน#n#k\r\n1นาที 55วินาที ~ 2นาที 00วินาที 미만 : #b#e650 คะแนน#n#k\r\n2นาที 00วินาที ~ 2นาที 05วินาที 미만 : #b#e550 คะแนน#n#k\r\n2นาที 05วินาที ~ 2นาที 10วินาที 미만 : #b#e450 คะแนน#n#k\r\n2นาที 10วินาที ~ 2นาที 15วินาที 미만 : #b#e400 คะแนน#n#k\r\n2นาที 15วินาที ~ 2นาที 20วินาที 미만 : #b#e350 คะแนน#n#k\r\n2นาที 20วินาที ~ 2นาที 30วินาที 미만 : #b#e300 คะแนน#n#k\r\n2นาที 30วินาที ~ 2นาที 40วินาที 미만 : #b#e250 คะแนน#n#k\r\n2นาที 40วินาที ~ 2นาที 50วินาที 미만 : #b#e200 คะแนน#n#k\r\n2นาที 50วินาที 이상 : #b#e100 คะแนน#n#k");
                                    self.say("#fs11#\r\n#e[한밤의 설원 랭킹 คะแนน 기준]#n\r\n\r\n2นาที 30วินาที 미만 : #b#e1,000 คะแนน#n#k\r\n2นาที 30วินาที ~ 2นาที 35วินาที 미만 : #b#e800 คะแนน#n#k\r\n2นาที 35วินาที ~ 2นาที 40วินาที 미만 : #b#e650 คะแนน#n#k\r\n2นาที 40วินาที ~ 2นาที 45วินาที 미만 : #b#e550 คะแนน#n#k\r\n2นาที 45วินาที ~ 2นาที 50วินาที 미만 : #b#e450 คะแนน#n#k\r\n2นาที 50วินาที ~ 2นาที 55วินาที 미만 : #b#e400 คะแนน#n#k\r\n2นาที 55วินาที ~ 3นาที 00วินาที 미만 : #b#e350 คะแนน#n#k\r\n3นาที 00วินาที ~ 3นาที 10วินาที 미만 : #b#e300 คะแนน#n#k\r\n3นาที 10วินาที ~ 3นาที 20วินาที 미만 : #b#e250 คะแนน#n#k\r\n3นาที 20วินาที ~ 3นาที 30วินาที 미만 : #b#e200 คะแนน#n#k\r\n3นาที 30วินาที 이상 : #b#e100 คะแนน#n#k");
                                    break;
                                case 3: //ใคร것도 아니에요
                                    self.sayOk("#fs11#\r\nเขา래. อะไร을 하건 하지 않건 너의 자유지.");
                                    break;
                            }
                            break;
                        }
                        case 0:
                            self.say("#fs11# 곳에서 ฉัน가고 싶은 거야? ฉัน가기 전에 ข้าง에 있는 훌륭한 게임 머신에서 랭킹을 ยืนยัน할 수 있어. 한 번 보는 것도 괜찮을 거야.");
                            if (1 == self.askAccept("#fs11# 곳에 오기 전의 แผนที่ 돌려보내 줄까?")) {
                                registerTransferField(100000000);
                            }
                            break;
                    }
                }
                break;
            }
            case 942000000:
            case 942001000:
            case 942002000: {
                int v = self.askMenu("#fs11#\r\n여긴 플래เขา 레이스 게임을 하기 บน해 รอ자들이 기다리는 곳이야. 너무 보채지 말라고.\r\n#b#L0#플래เขา 레이스에 대해서 알려สัปดาห์세요.#l\r\n#b#L1# 곳에서 ฉัน가고 싶어요.#l");
                switch (v) {
                    case 0: { //플래เขา 레이스에 대해서 알려สัปดาห์세요.
                        int vv = self.askMenu("#fs11#쿠쿠쿠, 플래เขา 레이스에 대해서 뭐가 เขา렇게 알고 싶은데?\r\n\r\n#b#L0#플래เขา 레이스가 뭔가요?#l\r\n#b#L1#플래เขา 레이스를  뭐가 좋죠?#l\r\n#L4#플래เขา 레이스의 랭킹 คะแนน 기준을 알고 싶어요.#l\r\n#L3#ใคร 것도 아니에요.#l");
                        switch (vv) {
                            case 0: //플래เขา 레이스가 뭔가요?
                                self.say("#fs11#\r\n내 บน대한 창작น้ำ인 플래เขา 레이스에 대해 알고 싶은 거야?");
                                self.say("#fs11#\r\n룰은 간단해. #r매시 30นาที마다#k 개최, #rสัปดาห์간 กิลด์ ภารกิจ คะแนน 1 이상인 กิลด์원#k만이 เข้าร่วม할 수 있어.");
                                self.say("#fs11#\r\n빠르게 골인 지점ถึง 달리면 된다고. น้ำ론 장애น้ำ เหมือนกัน게 조----------------------------금은 있을 수도 있어.");
                                self.say("#fs11#\r\n플래เขา 레이스 내에서는 오로지 ย้ายและ 점프만으로 정정당당하게 승부... 하는 건 아니고, 코스 내에서만 ใช้할 수 있는 플래เขา สกิล들이 있어.");
                                self.say("#fs11#\r\n플래เขา สกิล들และ แผนที่ 배치된 장치들을 잘 조합 통상의 สถานการณ์에서는 갈 수 없는 ถนน을 가거ฉัน 더 빠르게 코스를 สัปดาห์파할 수 있을 거야.");
                                self.say("#fs11#\r\n아, เขา리고 레이스 เข้า 시 ทั้งหมด 변신 สถานะ가 ปลดล็อก 레이스 중 변신 포션을 ใช้하더라도 변신이 되지 않을 거야. 소중한 น้ำ약을 날려버릴 수도 있으니 조심하라고.");
                                self.say("#fs11#\r\n101เลเวล 이상만 เข้าร่วม할 수 있으니 ยืนยัน해 두라고. 이미 101เลเวล 넘은지 한참 됐다면야 뭐...");
                                self.say("#fs11#\r\nเขา럼, 힘내라고.");
                                break;
                            case 1: //플래เขา 레이스를  뭐가 좋죠?
                                self.say("#fs11#\r\n뭐가 좋으냐고? ถนน이 있으니까 달리고, 장애น้ำ이 있으니까 넘는 거지 꼭 좋을 게 있어야 하ฉัน?");
                                self.say("#fs11#\r\n...농담이고, กิลด์ สกิล 중 노블레스 สกิล โดยตรง 투자할 수 있는 SP วันสัปดาห์วัน간의 플래เขา 레이스 กิลด์ 랭킹에 따라 받을 수 있지.");
                                self.say("#fs11##b#e1บน : 25개의 SP\r\n2บน : 22개의 SP\r\n3บน : 20개의 SP\r\n4บน ~ 10บน : 18개의 SP\r\n상บน 10% : 16개의 SP\r\n상บน 20% : 14개의 SP\r\n상บน 30% : 12개의 SP\r\n상บน 40% : 10개의 SP\r\n상บน 50% : 9개의 SP\r\n상บน 60% : 8개의 SP\r\n상บน 70% : 7개의 SP\r\n상บน 80% : 6개의 SP\r\n1,000คะแนน 이상 : 5개의 SP#n#k\r\n\r\n#r#e※ 단, 1,000คะแนน 이상 ได้รับ하지 못 순บน ใน에 들었더라도 รางวัล을 받을 수 없.#k#n");
                                self.say("#fs11#\r\n플래เขา 레이스 กิลด์ 랭킹은 แต่ละ กิลด์원의 플래เขา 레이스 점수를 합산한 점수로 매겨지지. 달리기 실력이 빼어난 동료가 많은 กิลด์ 더 สูง 순บน를 가져갈 수 있어.");
                                self.say("#fs11#\r\n너의 플래เขา 레이스 점수는 สัปดาห์간 최고 기록으로 결정돼. 지금의 기록이 마음에 들지 않으면 เมื่อไหร่든 다시 도전하라고. น้ำ론 열려 있을 때 말이야.");
                                self.say("#fs11#\r\n아, กิลด์ 탈퇴하거ฉัน 추ห้อง당 너의 สัปดาห์간 최고 기록이 วินาที기화 กิลด์ 합산 점수도 เขา만큼 หัก되니까 반드시 조심하라고.");
                                self.say("#fs11#\r\nเขา리고 랭킹은 영원하지 않아. 매สัปดาห์ เดือน요วัน 0시에 랭킹และ 노블레스 สกิล ทั้งหมด วินาที기화 เขา때ถึง 합산된 랭킹을 반영해서 노블레스 สกิลSP สัปดาห์니까. 이번 สัปดาห์에 ล้มเหลว했다고 해서 너무 실망하지 말라구.");
                                break;
                            case 4: //플래เขา 레이스의 랭킹 คะแนน 기준을 알고 싶어요.
                                self.say("#fs11#\r\n이제 플래เขา 레이스에서 얻을 수 있는 랭킹 คะแนน 세 바퀴를 ทั้งหมด 완สัปดาห์하는데 걸린 เวลา에 따라 결정돼. 남들และ 상관 없이 너만 잘 달리면 สูง 점수를 얻을 수 มี는 뜻이지.");
                                self.say("#fs11#\r\n랭킹 คะแนน 기준은 วันสัปดาห์วัน 간 너의 최고 기록을 기준으로 결정돼. 더 สูง 기록을 얻어 기록을 갱신할 수 있으니까 기록이 아쉬우면 다시 도전하라고.");
                                self.say("#fs11#\r\nครั้งสุดท้าย으로, 플래เขา 레이스의 코스는 매สัปดาห์ เดือน요วัน 0시마다 바뀌게 돼. 코스마다 점수 기준이 다르니까 꼭 참고해서 똑똑하게 달리라고.");
                                self.say("#fs11#\r\n#e[한낮의 설원 랭킹 คะแนน 기준]#n\r\n\r\n2นาที 10วินาที 미만 : #b#e1,000 คะแนน#n#k\r\n2นาที 10วินาที ~ 2นาที 15วินาที 미만 : #b#e800 คะแนน#n#k\r\n2นาที 15วินาที ~ 2นาที 20วินาที 미만 : #b#e650 คะแนน#n#k\r\n2นาที 20วินาที ~ 2นาที 25วินาที 미만 : #b#e550 คะแนน#n#k\r\n2นาที 25วินาที ~ 2นาที 30วินาที 미만 : #b#e450 คะแนน#n#k\r\n2นาที 30วินาที ~ 2นาที 35วินาที 미만 : #b#e400 คะแนน#n#k\r\n2นาที 35วินาที ~ 2นาที 40วินาที 미만 : #b#e350 คะแนน#n#k\r\n2นาที 40วินาที ~ 2นาที 50วินาที 미만 : #b#e300 คะแนน#n#k\r\n2นาที 50วินาที ~ 3นาที 00วินาที 미만 : #b#e250 คะแนน#n#k\r\n3นาที 00วินาที ~ 3นาที 10วินาที 미만 : #b#e200 คะแนน#n#k\r\n3นาที 10วินาที 이상 : #b#e100 คะแนน#n#k");
                                self.say("#fs11#\r\n#e[석양의 설원 랭킹 คะแนน 기준]#n\r\n\r\n1นาที 50วินาที 미만 : #b#e1,000 คะแนน#n#k\r\n1นาที 50วินาที ~ 1นาที 55วินาที 미만 : #b#e800 คะแนน#n#k\r\n1นาที 55วินาที ~ 2นาที 00วินาที 미만 : #b#e650 คะแนน#n#k\r\n2นาที 00วินาที ~ 2นาที 05วินาที 미만 : #b#e550 คะแนน#n#k\r\n2นาที 05วินาที ~ 2นาที 10วินาที 미만 : #b#e450 คะแนน#n#k\r\n2นาที 10วินาที ~ 2นาที 15วินาที 미만 : #b#e400 คะแนน#n#k\r\n2นาที 15วินาที ~ 2นาที 20วินาที 미만 : #b#e350 คะแนน#n#k\r\n2นาที 20วินาที ~ 2นาที 30วินาที 미만 : #b#e300 คะแนน#n#k\r\n2นาที 30วินาที ~ 2นาที 40วินาที 미만 : #b#e250 คะแนน#n#k\r\n2นาที 40วินาที ~ 2นาที 50วินาที 미만 : #b#e200 คะแนน#n#k\r\n2นาที 50วินาที 이상 : #b#e100 คะแนน#n#k");
                                self.say("#fs11#\r\n#e[한밤의 설원 랭킹 คะแนน 기준]#n\r\n\r\n2นาที 30วินาที 미만 : #b#e1,000 คะแนน#n#k\r\n2นาที 30วินาที ~ 2นาที 35วินาที 미만 : #b#e800 คะแนน#n#k\r\n2นาที 35วินาที ~ 2นาที 40วินาที 미만 : #b#e650 คะแนน#n#k\r\n2นาที 40วินาที ~ 2นาที 45วินาที 미만 : #b#e550 คะแนน#n#k\r\n2นาที 45วินาที ~ 2นาที 50วินาที 미만 : #b#e450 คะแนน#n#k\r\n2นาที 50วินาที ~ 2นาที 55วินาที 미만 : #b#e400 คะแนน#n#k\r\n2นาที 55วินาที ~ 3นาที 00วินาที 미만 : #b#e350 คะแนน#n#k\r\n3นาที 00วินาที ~ 3นาที 10วินาที 미만 : #b#e300 คะแนน#n#k\r\n3นาที 10วินาที ~ 3นาที 20วินาที 미만 : #b#e250 คะแนน#n#k\r\n3นาที 20วินาที ~ 3นาที 30วินาที 미만 : #b#e200 คะแนน#n#k\r\n3นาที 30วินาที 이상 : #b#e100 คะแนน#n#k");
                                break;
                            case 3: //ใคร것도 아니에요
                                self.sayOk("#fs11#\r\nเขา래. อะไร을 하건 하지 않건 너의 자유지.");
                                break;
                        }
                        break;
                    }
                    case 1:
                        if (1 == self.askYesNo("#fs11#정말  곳에서 ฉัน가고 싶은거야?")) {
                            registerTransferField(getPlayer().getMapId() + 1);
                        }
                        break;
                }
                break;
            }
            default: {
                int v = self.askMenu("#fs11#\r\nที่นี่는 플래เขา 레이스 연습 รอ실이야~ 궁금한 거 있으면 น้ำ어봐도 돼.\r\n#b#L1#플래เขา 레이스 연습 코스로 가고 싶어요.#l\r\n#b#L0#플래เขา 레이스에 대해서 알려สัปดาห์세요.#l");
                switch (v) {
                    case 1: { //플래เขา 레이스 연습 코스로 가고 싶어요.
                        int vv = self.askMenu("#fs11#\r\n어느 코스를 연습 싶은데? 전부 쉽지는 않지만 말이야.\r\n#b#L1#한낮의 설원 연습 코스#l\r\n#b#L2#석양의 설원 연습 코스#l\r\n#b#L3#한밤의 설원 연습 코스#l");
                        switch (vv) {
                            case 1: //한낮의 설원 연습 코스
                                if (1 == self.askAccept("#fs11##b한낮의 설원 연습 코스#k ย้าย시켜 줄까?\r\n\r\n#r※ ปาร์ตี้장만 เริ่ม할 수  ทั้งหมด ปาร์ตี้원이 연습 รอ실에 있어야만 เข้า เป็นไปได้.")) {
                                    FieldSet fieldSet = fieldSet("FlagRaceN1Enter");
                                    if (fieldSet == null) {
                                        self.sayOk("#fs11#지금은 플래เขา 레이스(한낮의 설원) 이용할 수 없어.");
                                        return;
                                    }
                                    int enter = ((FlagRaceN1Enter) fieldSet).enter(target.getId(), true, 0);
                                    if (enter == -1)
                                        self.say("#fs11#알 수 없는 이유로 เข้า할 수 없. 잠시 후에 다시 시도해 สัปดาห์십시오.");
                                    else if (enter == 1)
                                        self.say("#fs11#1~6인 ปาร์ตี้ 맺어야만 เริ่ม할 수 있어.");
                                    else if (enter == 2)
                                        self.say("#fs11#...너는 ปาร์ตี้장이 아닌걸? ปาร์ตี้장만이 เข้า을 สมัคร할 수 มี고.");
                                    else if (enter == 3)
                                        self.say("#fs11#ต่ำสุด " + fieldSet.minMember + "인 이상의 ปาร์ตี้ เควส เริ่ม할 수 있답니다.");
                                    else if (enter == 4)
                                        self.say("#fs11#ปาร์ตี้원의 เลเวล ต่ำสุด " + fieldSet.minLv + " 이상이어야 .");
                                    else if (enter == 5) self.say("#fs11#ปาร์ตี้원이 ทั้งหมด 모여 있어야 เริ่ม할 수 있.");
                                    else if (enter == 6)
                                        self.say("#fs11#이미 อื่น ปาร์ตี้ ใน으로 들어가 เควส 클리어에 도전 있는 중이랍니다.");
                                } else {
                                    self.sayOk("#fs11#연습이 จำเป็น해지면 เมื่อไหร่든 말해...");
                                }
                                break;
                            case 2: //석양의 설원 연습 코스
                                if (1 == self.askAccept("#fs11##b석양의 설원 연습 코스#k ย้าย시켜 줄까?\r\n\r\n#r※ ปาร์ตี้장만 เริ่ม할 수  ทั้งหมด ปาร์ตี้원이 연습 รอ실에 있어야만 เข้า เป็นไปได้.")) {
                                    //942003200
                                    FieldSet fieldSet = fieldSet("FlagRaceN2Enter");
                                    if (fieldSet == null) {
                                        self.sayOk("#fs11#지금은 플래เขา 레이스(석양의 설원) 이용할 수 없어.");
                                        return;
                                    }
                                    int enter = ((FlagRaceN2Enter) fieldSet).enter(target.getId(), true, 0);
                                    if (enter == -1)
                                        self.say("#fs11#알 수 없는 이유로 เข้า할 수 없. 잠시 후에 다시 시도해 สัปดาห์십시오.");
                                    else if (enter == 1)
                                        self.say("#fs11#1~6인 ปาร์ตี้ 맺어야만 เริ่ม할 수 있어.");
                                    else if (enter == 2)
                                        self.say("#fs11#...너는 ปาร์ตี้장이 아닌걸? ปาร์ตี้장만이 เข้า을 สมัคร할 수 มี고.");
                                    else if (enter == 3)
                                        self.say("#fs11#ต่ำสุด " + fieldSet.minMember + "인 이상의 ปาร์ตี้ เควส เริ่ม할 수 있답니다.");
                                    else if (enter == 4)
                                        self.say("#fs11#ปาร์ตี้원의 เลเวล ต่ำสุด " + fieldSet.minLv + " 이상이어야 .");
                                    else if (enter == 5) self.say("#fs11#ปาร์ตี้원이 ทั้งหมด 모여 있어야 เริ่ม할 수 있.");
                                    else if (enter == 6)
                                        self.say("#fs11#이미 อื่น ปาร์ตี้ ใน으로 들어가 เควส 클리어에 도전 있는 중이랍니다.");
                                } else {
                                    self.sayOk("#fs11#연습이 จำเป็น해지면 เมื่อไหร่든 말해...");
                                }
                                break;
                            case 3: //한밤의 설원 연습 코스
                                if (1 == self.askAccept("#fs11##b한밤의 설원 연습 코스#k ย้าย시켜 줄까?\r\n\r\n#r※ ปาร์ตี้장만 เริ่ม할 수  ทั้งหมด ปาร์ตี้원이 연습 รอ실에 있어야만 เข้า เป็นไปได้.")) {
                                    //942003300
                                    FieldSet fieldSet = fieldSet("FlagRaceN3Enter");
                                    if (fieldSet == null) {
                                        self.sayOk("#fs11#지금은 플래เขา 레이스(석양의 설원) 이용할 수 없어.");
                                        return;
                                    }
                                    int enter = ((FlagRaceN3Enter) fieldSet).enter(target.getId(), true, 0);
                                    if (enter == -1)
                                        self.say("#fs11#알 수 없는 이유로 เข้า할 수 없. 잠시 후에 다시 시도해 สัปดาห์십시오.");
                                    else if (enter == 1)
                                        self.say("#fs11#1~6인 ปาร์ตี้ 맺어야만 เริ่ม할 수 있어.");
                                    else if (enter == 2)
                                        self.say("#fs11#...너는 ปาร์ตี้장이 아닌걸? ปาร์ตี้장만이 เข้า을 สมัคร할 수 มี고.");
                                    else if (enter == 3)
                                        self.say("#fs11#ต่ำสุด " + fieldSet.minMember + "인 이상의 ปาร์ตี้ เควส เริ่ม할 수 있답니다.");
                                    else if (enter == 4)
                                        self.say("#fs11#ปาร์ตี้원의 เลเวล ต่ำสุด " + fieldSet.minLv + " 이상이어야 .");
                                    else if (enter == 5) self.say("#fs11#ปาร์ตี้원이 ทั้งหมด 모여 있어야 เริ่ม할 수 있.");
                                    else if (enter == 6)
                                        self.say("#fs11#이미 อื่น ปาร์ตี้ ใน으로 들어가 เควส 클리어에 도전 있는 중이랍니다.");

                                } else {
                                    self.sayOk("#fs11#연습이 จำเป็น해지면 เมื่อไหร่든 말해...");
                                }
                                break;
                        }
                        break;
                    }
                }
                break;
            }
        }
    }

    public void flag_Exit_NPC() {
        if (1 == self.askYesNo("#fs11#\r\n벌써 포기 싶은 거야? นอก으로 내보내줄까?", ScriptMessageFlag.NpcReplacedByNpc)) {
            registerTransferField(942003000); //한낮 ออกแผนที่
            //942003001 석양 ออกแผนที่
            //942003002 한밤 ออกแผนที่
        }
    }

    public void flag_Exit_Map_NPC() {
        if (1 == self.askAccept("#fs11#쿠쿠쿠, 연습 코스는 어땠어? 다시 연습 รอ실로 돌려보내 줄까?")) {
            registerTransferField(942003050);
        } else {
            self.sayOk("#fs11#เขา래, 돌아가고 싶거든 내게 말해줘.");
        }
    }

    @Script
    public void flag_pexit() {
        registerTransferField(100000000);
    }

    @Script
    public void flag_Start() {
        switch (getPlayer().getMapId()) {
            case 942000500: { //실전แผนที่
                objects.fields.fieldset.instance.FlagRace fieldSet = (objects.fields.fieldset.instance.FlagRace) getPlayer().getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "지금은 포탈을 이용ไม่สามารถทำได้.");
                    return;
                }
                if (fieldSet.isStartGame()) {
                    Field field = getPlayer().getMap();
                    field.removeDropsIndividual(getPlayer());
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(94, 2297), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(1892, 1225), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(812, 1167), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(420, 1008), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-2023, 1244), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-555, 1273), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-600, 1043), false);
                    userLocalTeleport(16);
                }
                break;
            }
            case 942003100: {
                FlagRaceN1 fieldSet = (FlagRaceN1) getPlayer().getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "지금은 포탈을 이용ไม่สามารถทำได้.");
                    return;
                }
                if (fieldSet.isStartGame()) {
                    Field field = getPlayer().getMap();
                    field.removeDropsIndividual(getPlayer());
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(94, 2297), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(1892, 1225), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(812, 1167), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(420, 1008), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-2023, 1244), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-555, 1273), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-600, 1043), false);
                    userLocalTeleport(16);
                }
                break;
            }

            case 942001500: {
                objects.fields.fieldset.instance.FlagRace fieldSet = (objects.fields.fieldset.instance.FlagRace) getPlayer().getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "지금은 포탈을 이용ไม่สามารถทำได้.");
                    return;
                }
                if (fieldSet.isStartGame()) {
                    Field field = getPlayer().getMap();
                    field.removeDropsIndividual(getPlayer());
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-803, 1480), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(270, 1525), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(632, 1525), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(2007, 1103), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(1357, 1103), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(725, 975), false);
                    userLocalTeleport(1);
                }
                break;
            }
            case 942003200: {
                FlagRaceN2 fieldSet = (FlagRaceN2) getPlayer().getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "지금은 포탈을 이용ไม่สามารถทำได้.");
                    return;
                }
                if (fieldSet.isStartGame()) {
                    Field field = getPlayer().getMap();
                    field.removeDropsIndividual(getPlayer());
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-803, 1480), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(270, 1525), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(632, 1525), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(2007, 1103), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(1357, 1103), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(725, 975), false);
                    userLocalTeleport(1);
                }
                break;
            }
            //932200300
            case 942002500: {
                objects.fields.fieldset.instance.FlagRace fieldSet = (objects.fields.fieldset.instance.FlagRace) getPlayer().getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "지금은 포탈을 이용ไม่สามารถทำได้.");
                    return;
                }
                if (fieldSet.isStartGame()) {
                    Field field = getPlayer().getMap();
                    field.removeDropsIndividual(getPlayer());
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(689, 2328), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-1075, 2322), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-110, 2660), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(2073, 1644), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(2073, 1284), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(808, 1339), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-2050, 1081), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-2065, 1345), false);
                    userLocalTeleport(18);
                }
                break;
            }
            case 942003300: {
                FlagRaceN3 fieldSet = (FlagRaceN3) getPlayer().getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "지금은 포탈을 이용ไม่สามารถทำได้.");
                    return;
                }
                if (fieldSet.isStartGame()) {
                    Field field = getPlayer().getMap();
                    field.removeDropsIndividual(getPlayer());
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(689, 2328), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-1075, 2322), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-110, 2660), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(2073, 1644), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(2073, 1284), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(808, 1339), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-2050, 1081), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-2065, 1345), false);
                    userLocalTeleport(18);
                }
                break;
            }
        }
    }

    @Script
    public void flag_goal() {
        switch (getPlayer().getMapId()) {
            case 942000500: { //실전แผนที่
                objects.fields.fieldset.instance.FlagRace fieldSet = (objects.fields.fieldset.instance.FlagRace) getPlayer().getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "지금은 포탈을 이용ไม่สามารถทำได้.");
                    return;
                }
                int flagGoal = fieldSet.addFlagGoalNumber(getPlayer().getName());
                getPlayer().updateOneInfo(32581, "lap", String.valueOf(flagGoal));
                if (flagGoal == 3) {
                    SimpleDateFormat sdf = new SimpleDateFormat("YYMMddHHmmssSS");
                    getPlayer().updateOneInfo(32581, "finish", sdf.format(new Date()));
                    long record = 0;
                    try {
                        long start = sdf.parse(getPlayer().getOneInfo(32581, "start")).getTime();
                        long finish = sdf.parse(getPlayer().getOneInfo(32581, "finish")).getTime();
                        record = finish- start;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getPlayer().updateOneInfo(32581, "complete", "1");
                    getPlayer().updateOneInfo(32581, "record", String.valueOf(record));
                    getPlayer().getMap().broadcastMessage(CWvsContext.getScriptProgressMessage(getPlayer().getName() + " 님께서 완สัปดาห์에 สำเร็จ하였."));
                    getPlayer().send(CField.environmentChange("quest/party/clear", 19));
                    userLocalTeleport(18);
                } else {
                    getPlayer().getMap().broadcastMessage(CWvsContext.getScriptProgressMessage(getPlayer().getName() + "님이 골인 지점을 통และ하였. หน้า으로 " + (3 - flagGoal) + "바퀴 남았."));
                    userLocalTeleport(17);
                }
                break;
            }
            case 942003100: {
                FlagRaceN1 fieldSet = (FlagRaceN1) getPlayer().getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "지금은 포탈을 이용ไม่สามารถทำได้.");
                    return;
                }
                int flagGoal = fieldSet.addFlagGoalNumber(getPlayer().getName());
                getPlayer().updateOneInfo(32581, "lap", String.valueOf(flagGoal));
                if (flagGoal == 3) {
                    SimpleDateFormat sdf = new SimpleDateFormat("YYMMddHHmmssSS");
                    getPlayer().updateOneInfo(32581, "finish", sdf.format(new Date()));
                    long record = 0;
                    try {
                        long start = sdf.parse(getPlayer().getOneInfo(32581, "start")).getTime();
                        long finish = sdf.parse(getPlayer().getOneInfo(32581, "finish")).getTime();
                        record = finish- start;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getPlayer().updateOneInfo(32581, "complete", "1");
                    getPlayer().updateOneInfo(32581, "record", String.valueOf(record));
                    getPlayer().getMap().broadcastMessage(CWvsContext.getScriptProgressMessage(getPlayer().getName() + " 님께서 완สัปดาห์에 สำเร็จ하였."));
                    getPlayer().send(CField.environmentChange("quest/party/clear", 19));
                    userLocalTeleport(18);
                } else {
                    getPlayer().getMap().broadcastMessage(CWvsContext.getScriptProgressMessage(getPlayer().getName() + "님이 골인 지점을 통และ하였. หน้า으로 " + (3 - flagGoal) + "바퀴 남았."));
                    userLocalTeleport(17);
                }
                break;
            }
            case 942001500: {
                objects.fields.fieldset.instance.FlagRace fieldSet = (objects.fields.fieldset.instance.FlagRace) getPlayer().getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "지금은 포탈을 이용ไม่สามารถทำได้.");
                    return;
                }
                int flagGoal = fieldSet.addFlagGoalNumber(getPlayer().getName());
                getPlayer().updateOneInfo(32581, "lap", String.valueOf(flagGoal));
                if (flagGoal == 3) {
                    SimpleDateFormat sdf = new SimpleDateFormat("YYMMddHHmmssSS");
                    getPlayer().updateOneInfo(32581, "finish", sdf.format(new Date()));
                    long record = 0;
                    try {
                        long start = sdf.parse(getPlayer().getOneInfo(32581, "start")).getTime();
                        long finish = sdf.parse(getPlayer().getOneInfo(32581, "finish")).getTime();
                        record = finish- start;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getPlayer().updateOneInfo(32581, "complete", "1");
                    getPlayer().updateOneInfo(32581, "record", String.valueOf(record));
                    getPlayer().getMap().broadcastMessage(CWvsContext.getScriptProgressMessage(getPlayer().getName() + " 님께서 완สัปดาห์에 สำเร็จ하였."));
                    getPlayer().send(CField.environmentChange("quest/party/clear", 19));
                    userLocalTeleport(5);
                } else {
                    Field field = getPlayer().getMap();
                    field.removeDropsIndividual(getPlayer());
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-803, 1480), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(270, 1525), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(632, 1525), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(2007, 1103), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(1357, 1103), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(725, 975), false);
                    getPlayer().getMap().broadcastMessage(CWvsContext.getScriptProgressMessage(getPlayer().getName() + "님이 골인 지점을 통และ하였. หน้า으로 " + (3 - flagGoal) + "바퀴 남았."));
                    userLocalTeleport(1);
                }
                break;
            }

            case 942003200: {
                FlagRaceN2 fieldSet = (FlagRaceN2) getPlayer().getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "지금은 포탈을 이용ไม่สามารถทำได้.");
                    return;
                }
                int flagGoal = fieldSet.addFlagGoalNumber(getPlayer().getName());
                getPlayer().updateOneInfo(32581, "lap", String.valueOf(flagGoal));
                if (flagGoal == 3) {
                    SimpleDateFormat sdf = new SimpleDateFormat("YYMMddHHmmssSS");
                    getPlayer().updateOneInfo(32581, "finish", sdf.format(new Date()));
                    long record = 0;
                    try {
                        long start = sdf.parse(getPlayer().getOneInfo(32581, "start")).getTime();
                        long finish = sdf.parse(getPlayer().getOneInfo(32581, "finish")).getTime();
                        record = finish- start;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getPlayer().updateOneInfo(32581, "complete", "1");
                    getPlayer().updateOneInfo(32581, "record", String.valueOf(record));
                    getPlayer().getMap().broadcastMessage(CWvsContext.getScriptProgressMessage(getPlayer().getName() + " 님께서 완สัปดาห์에 สำเร็จ하였."));
                    getPlayer().send(CField.environmentChange("quest/party/clear", 19));
                    userLocalTeleport(5);
                } else {
                    Field field = getPlayer().getMap();
                    field.removeDropsIndividual(getPlayer());
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-803, 1480), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(270, 1525), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(632, 1525), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(2007, 1103), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(1357, 1103), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(), new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(725, 975), false);
                    getPlayer().getMap().broadcastMessage(CWvsContext.getScriptProgressMessage(getPlayer().getName() + "님이 골인 지점을 통และ하였. หน้า으로 " + (3 - flagGoal) + "바퀴 남았."));
                    userLocalTeleport(1);
                }
                break;
            }
            case 942002500: {
                objects.fields.fieldset.instance.FlagRace fieldSet = (objects.fields.fieldset.instance.FlagRace) getPlayer().getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "지금은 포탈을 이용ไม่สามารถทำได้.");
                    return;
                }
                int flagGoal = fieldSet.addFlagGoalNumber(getPlayer().getName());
                getPlayer().updateOneInfo(32581, "lap", String.valueOf(flagGoal));
                if (flagGoal == 3) {
                    SimpleDateFormat sdf = new SimpleDateFormat("YYMMddHHmmssSS");
                    getPlayer().updateOneInfo(32581, "finish", sdf.format(new Date()));
                    long record = 0;
                    try {
                        long start = sdf.parse(getPlayer().getOneInfo(32581, "start")).getTime();
                        long finish = sdf.parse(getPlayer().getOneInfo(32581, "finish")).getTime();
                        record = finish- start;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getPlayer().updateOneInfo(32581, "complete", "1");
                    getPlayer().updateOneInfo(32581, "record", String.valueOf(record));
                    getPlayer().getMap().broadcastMessage(CWvsContext.getScriptProgressMessage(getPlayer().getName() + " 님께서 완สัปดาห์에 สำเร็จ하였."));
                    getPlayer().send(CField.environmentChange("quest/party/clear", 19));
                    userLocalTeleport(3);
                } else {
                    getPlayer().getMap().broadcastMessage(CWvsContext.getScriptProgressMessage(getPlayer().getName() + "님이 골인 지점을 통และ하였. หน้า으로 " + (3 - flagGoal) + "바퀴 남았."));
                    userLocalTeleport(5);
                }
                break;
            }
            case 942003300: {
                FlagRaceN3 fieldSet = (FlagRaceN3) getPlayer().getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "지금은 포탈을 이용ไม่สามารถทำได้.");
                    return;
                }
                int flagGoal = fieldSet.addFlagGoalNumber(getPlayer().getName());
                getPlayer().updateOneInfo(32581, "lap", String.valueOf(flagGoal));
                if (flagGoal == 3) {
                    SimpleDateFormat sdf = new SimpleDateFormat("YYMMddHHmmssSS");
                    getPlayer().updateOneInfo(32581, "finish", sdf.format(new Date()));
                    long record = 0;
                    try {
                        long start = sdf.parse(getPlayer().getOneInfo(32581, "start")).getTime();
                        long finish = sdf.parse(getPlayer().getOneInfo(32581, "finish")).getTime();
                        record = finish- start;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getPlayer().updateOneInfo(32581, "complete", "1");
                    getPlayer().updateOneInfo(32581, "record", String.valueOf(record));
                    getPlayer().getMap().broadcastMessage(CWvsContext.getScriptProgressMessage(getPlayer().getName() + " 님께서 완สัปดาห์에 สำเร็จ하였."));
                    getPlayer().send(CField.environmentChange("quest/party/clear", 19));
                    userLocalTeleport(3);
                } else {
                    getPlayer().getMap().broadcastMessage(CWvsContext.getScriptProgressMessage(getPlayer().getName() + "님이 골인 지점을 통และ하였. หน้า으로 " + (3 - flagGoal) + "바퀴 남았."));
                    userLocalTeleport(5);
                }
                break;
            }
        }
    }

    public void flag_exit() {
        initNPC(MapleLifeFactory.getNPC(9000233));
        switch (getPlayer().getMapId()) {
            case 942000500:
            case 942001500:
            case 942002500: {
                objects.fields.fieldset.instance.FlagRace fieldSet = (objects.fields.fieldset.instance.FlagRace) getPlayer().getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "포탈을 이용ไม่สามารถทำได้.");
                    return;
                }
                int flagGoal = fieldSet.getFlagGoalNumber(getPlayer().getName());
                if (flagGoal == 3) {
                    String mapName = "한낮의 설원";
                    if (getPlayer().getMapId() == 932200200) {
                        mapName = "석양의 설원";
                    } else if (getPlayer().getMapId() == 932200300) {
                        mapName = "한밤의 설원";
                    }
                    self.say("#b" + mapName + "#k 완สัปดาห์에 สำเร็จ했구ฉัน! 생แต่ละ보단 꽤 하는걸?", ScriptMessageFlag.NpcReplacedByNpc);
                    if (1 == self.askYesNo("내가 โดยตรง 코스 นอก으로 แนะนำ해줄게. น้ำ론 원한다면 아직도 달리고 있는 동료들을 구경해도 . 쿠쿠쿠.", ScriptMessageFlag.NpcReplacedByNpc)) {
                        int returnMap = 942003000 + ((getPlayer().getMapId() - 942000500) / 1000);
                        registerTransferField(returnMap);
                    }
                }
                break;
            }
            case 942003100: {
                FlagRaceN1 fieldSet = (FlagRaceN1) getPlayer().getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "포탈을 이용ไม่สามารถทำได้.");
                    return;
                }
                int flagGoal = fieldSet.getFlagGoalNumber(getPlayer().getName());
                if (flagGoal == 3) {
                    self.say("#b한낮의 설원#k 완สัปดาห์에 สำเร็จ했구ฉัน! 생แต่ละ보단 꽤 하는걸?", ScriptMessageFlag.NpcReplacedByNpc);
                    if (1 == self.askYesNo("내가 โดยตรง 코스 นอก으로 แนะนำ해줄게. น้ำ론 원한다면 아직도 달리고 있는 동료들을 구경해도 . 쿠쿠쿠.", ScriptMessageFlag.NpcReplacedByNpc)) {
                        registerTransferField(942003000); //한낮 ออกแผนที่
                    }
                }
                break;
            }
            case 942003200: {
                FlagRaceN2 fieldSet = (FlagRaceN2) getPlayer().getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "포탈을 이용ไม่สามารถทำได้.");
                    return;
                }
                int flagGoal = fieldSet.getFlagGoalNumber(getPlayer().getName());
                if (flagGoal == 3) {
                    self.say("#b석양의 설원#k 완สัปดาห์에 สำเร็จ했구ฉัน! 생แต่ละ보단 꽤 하는걸?", ScriptMessageFlag.NpcReplacedByNpc);
                    if (1 == self.askYesNo("내가 โดยตรง 코스 นอก으로 แนะนำ해줄게. น้ำ론 원한다면 아직도 달리고 있는 동료들을 구경해도 . 쿠쿠쿠.", ScriptMessageFlag.NpcReplacedByNpc)) {
                        registerTransferField(942003001); //석양 ออกแผนที่
                    }
                }
                break;
            }
            case 942003300: {
                FlagRaceN3 fieldSet = (FlagRaceN3) getPlayer().getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "포탈을 이용ไม่สามารถทำได้.");
                    return;
                }
                int flagGoal = fieldSet.getFlagGoalNumber(getPlayer().getName());
                if (flagGoal == 3) {
                    self.say("#b한밤의 설원#k 완สัปดาห์에 สำเร็จ했구ฉัน! 생แต่ละ보단 꽤 하는걸?", ScriptMessageFlag.NpcReplacedByNpc);
                    if (1 == self.askYesNo("내가 โดยตรง 코스 นอก으로 แนะนำ해줄게. น้ำ론 원한다면 아직도 달리고 있는 동료들을 구경해도 . 쿠쿠쿠.", ScriptMessageFlag.NpcReplacedByNpc)) {
                        registerTransferField(942003002); //석양 ออกแผนที่
                    }
                }
                break;
            }
        }
    }

    public void flag_result() {
        if (getPlayer().getOneInfoQuestInteger(32581, "complete") > 0) {
            getPlayer().updateOneInfo(32581, "complete", "0");
            initNPC(MapleLifeFactory.getNPC(9000232));
            switch (getPlayer().getMapId()) {
                case 942003000: {
                    long record = getPlayer().getOneInfoQuestInteger(32581, "record");
                    long weeklyRecord = getPlayer().getOneInfoQuestInteger(32581, "weeklyRecord");
                    long minute = TimeUnit.MILLISECONDS.toMinutes(record);
                    long second = TimeUnit.MILLISECONDS.toSeconds(record % 60000);
                    String recordToString = minute + "นาที " + second + "วินาที";
                    int score = 0;
                    if (record < 130000) { // 2นาที 10วินาที
                        score = 1000;
                    } else if (record >= 130000 && record < 135000) {
                        score = 800;
                    } else if (record >= 135000 && record < 140000) {
                        score = 650;
                    } else if (record >= 140000 && record < 145000) {
                        score = 550;
                    } else if (record >= 145000 && record < 150000) {
                        score = 450;
                    } else if (record >= 150000 && record < 155000) {
                        score = 400;
                    } else if (record >= 155000 && record < 160000) {
                        score = 350;
                    } else if (record >= 160000 && record < 170000) {
                        score = 300;
                    } else if (record >= 170000 && record < 180000) {
                        score = 250;
                    } else if (record >= 180000 && record < 190000) {
                        score = 200;
                    } else if (record >= 190000) {
                        score = 100;
                    }
                    if (getPlayer().getOneInfoQuestInteger(32581, "mode") > 1) {
                        self.say("\r\n#e[플래เขา 레이스 연습 코스 ผลลัพธ์]\r\n\r\n#n- 기록 : #b" + recordToString + "#k\r\n- 예상 ได้รับ 랭킹 คะแนน : #b" + score + "점", ScriptMessageFlag.NpcReplacedByNpc);
                    } else {
                        if (getPlayer().getGuild() != null) {
                            getPlayer().getGuild().setPointLog(GuildContentsType.FLAG_RACE, getPlayer(), score);
                            int guildScore = getPlayer().getGuild().getPointLogByType(GuildContentsType.FLAG_RACE, getPlayer());
                            if (weeklyRecord > 0) {
                                if (record < weeklyRecord) {
                                    self.say("\r\n#e[플래เขา 레이스 ผลลัพธ์]\r\n\r\n#n- 기록 : #b" + recordToString + "(신기록 갱신!)#k\r\n- 이번 สัปดาห์ 최고 기록 : #r" + recordToString + " #k\r\n- 이번 สัปดาห์ ได้รับ한 กิลด์ 랭킹 คะแนน : " + guildScore + "점", ScriptMessageFlag.NpcReplacedByNpc);
                                    getPlayer().updateOneInfo(32581, "weeklyRecord", String.valueOf(record));
                                } else {
                                    self.say("\r\n#e[플래เขา 레이스 ผลลัพธ์]\r\n\r\n#n- 기록 : #b" + recordToString + "#k\r\n- 이번 สัปดาห์ ได้รับ한 กิลด์ 랭킹 คะแนน : #b" + guildScore + "점", ScriptMessageFlag.NpcReplacedByNpc);
                                }
                            } else if (weeklyRecord == 0) {
                                self.say("\r\n#e[플래เขา 레이스 ผลลัพธ์]\r\n\r\n#n- 기록 : #b" + recordToString + "#k\r\n- 이번 สัปดาห์ ได้รับ한 กิลด์ 랭킹 คะแนน : #b" + guildScore + "점", ScriptMessageFlag.NpcReplacedByNpc);
                                getPlayer().updateOneInfo(32581, "weeklyRecord", String.valueOf(record));
                            }
                        }
                    }
                    break;
                }
                case 942003001: {
                    long record = getPlayer().getOneInfoQuestInteger(32581, "record");
                    long weeklyRecord = getPlayer().getOneInfoQuestInteger(32581, "weeklyRecord");
                    long minute = TimeUnit.MILLISECONDS.toMinutes(record);
                    long second = TimeUnit.MILLISECONDS.toSeconds(record % 60000);
                    String recordToString = minute + "นาที " + second + "วินาที";
                    int score = 0;
                    if (record < 110000) {
                        score = 1000;
                    } else if (record >= 110000 && record < 115000) {
                        score = 800;
                    } else if (record >= 115000 && record < 120000) {
                        score = 650;
                    } else if (record >= 120000 && record < 125000) {
                        score = 550;
                    } else if (record >= 125000 && record < 130000) {
                        score = 450;
                    } else if (record >= 130000 && record < 135000) {
                        score = 400;
                    } else if (record >= 135000 && record < 140000) {
                        score = 350;
                    } else if (record >= 140000 && record < 150000) {
                        score = 300;
                    } else if (record >= 150000 && record < 160000) {
                        score = 250;
                    } else if (record >= 160000 && record < 170000) {
                        score = 200;
                    } else if (record >= 170000) {
                        score = 100;
                    }
                    if (getPlayer().getOneInfoQuestInteger(32581, "mode") > 1) {
                        self.say("\r\n#e[플래เขา 레이스 연습 코스 ผลลัพธ์]\r\n\r\n#n- 기록 : #b" + recordToString + "#k\r\n- 예상 ได้รับ 랭킹 คะแนน : #b" + score + "점", ScriptMessageFlag.NpcReplacedByNpc);
                    } else {
                        if (getPlayer().getGuild() != null) {
                            getPlayer().getGuild().setPointLog(GuildContentsType.FLAG_RACE, getPlayer(), score);
                            int guildScore = getPlayer().getGuild().getPointLogByType(GuildContentsType.FLAG_RACE, getPlayer());
                            if (weeklyRecord > 0) {
                                if (record < weeklyRecord) {
                                    self.say("\r\n#e[플래เขา 레이스 ผลลัพธ์]\r\n\r\n#n- 기록 : #b" + recordToString + "(신기록 갱신!)#k\r\n- 이번 สัปดาห์ 최고 기록 : #r" + recordToString + " #k\r\n- 이번 สัปดาห์ ได้รับ한 กิลด์ 랭킹 คะแนน : " + guildScore + "점", ScriptMessageFlag.NpcReplacedByNpc);
                                    getPlayer().updateOneInfo(32581, "weeklyRecord", String.valueOf(record));
                                } else {
                                    self.say("\r\n#e[플래เขา 레이스 ผลลัพธ์]\r\n\r\n#n- 기록 : #b" + recordToString + "#k\r\n- 이번 สัปดาห์ ได้รับ한 กิลด์ 랭킹 คะแนน : #b" + guildScore + "점", ScriptMessageFlag.NpcReplacedByNpc);
                                }
                            } else if (weeklyRecord == 0) {
                                self.say("\r\n#e[플래เขา 레이스 ผลลัพธ์]\r\n\r\n#n- 기록 : #b" + recordToString + "#k\r\n- 이번 สัปดาห์ ได้รับ한 กิลด์ 랭킹 คะแนน : #b" + guildScore + "점", ScriptMessageFlag.NpcReplacedByNpc);
                                getPlayer().updateOneInfo(32581, "weeklyRecord", String.valueOf(record));
                            }
                        }
                    }
                    break;
                }
                case 942003002: {
                    long record = getPlayer().getOneInfoQuestInteger(32581, "record");
                    long weeklyRecord = getPlayer().getOneInfoQuestInteger(32581, "weeklyRecord");
                    long minute = TimeUnit.MILLISECONDS.toMinutes(record);
                    long second = TimeUnit.MILLISECONDS.toSeconds(record % 60000);
                    String recordToString = minute + "นาที " + second + "วินาที";
                    int score = 0;
                    if (record < 150000) {
                        score = 1000;
                    } else if (record >= 150000 && record < 155000) {
                        score = 800;
                    } else if (record >= 155000 && record < 160000) {
                        score = 650;
                    } else if (record >= 160000 && record < 165000) {
                        score = 550;
                    } else if (record >= 165000 && record < 170000) {
                        score = 450;
                    } else if (record >= 170000 && record < 175000) {
                        score = 400;
                    } else if (record >= 175000 && record < 180000) {
                        score = 350;
                    } else if (record >= 180000 && record < 190000) {
                        score = 300;
                    } else if (record >= 190000 && record < 200000) {
                        score = 250;
                    } else if (record >= 200000 && record < 210000) {
                        score = 200;
                    } else if (record >= 210000) {
                        score = 100;
                    }
                    if (getPlayer().getOneInfoQuestInteger(32581, "mode") > 1) {
                        self.say("\r\n#e[플래เขา 레이스 연습 코스 ผลลัพธ์]\r\n\r\n#n- 기록 : #b" + recordToString + "#k\r\n- 예상 ได้รับ 랭킹 คะแนน : #b" + score + "점", ScriptMessageFlag.NpcReplacedByNpc);
                    } else {
                        if (getPlayer().getGuild() != null) {
                            getPlayer().getGuild().setPointLog(GuildContentsType.FLAG_RACE, getPlayer(), score);
                            int guildScore = getPlayer().getGuild().getPointLogByType(GuildContentsType.FLAG_RACE, getPlayer());
                            if (weeklyRecord > 0) {
                                if (record < weeklyRecord) {
                                    self.say("\r\n#e[플래เขา 레이스 ผลลัพธ์]\r\n\r\n#n- 기록 : #b" + recordToString + "(신기록 갱신!)#k\r\n- 이번 สัปดาห์ 최고 기록 : #r" + recordToString + " #k\r\n- 이번 สัปดาห์ ได้รับ한 กิลด์ 랭킹 คะแนน : " + guildScore + "점", ScriptMessageFlag.NpcReplacedByNpc);
                                    getPlayer().updateOneInfo(32581, "weeklyRecord", String.valueOf(record));
                                } else {
                                    self.say("\r\n#e[플래เขา 레이스 ผลลัพธ์]\r\n\r\n#n- 기록 : #b" + recordToString + "#k\r\n- 이번 สัปดาห์ ได้รับ한 กิลด์ 랭킹 คะแนน : #b" + guildScore + "점", ScriptMessageFlag.NpcReplacedByNpc);
                                }
                            } else if (weeklyRecord == 0) {
                                self.say("\r\n#e[플래เขา 레이스 ผลลัพธ์]\r\n\r\n#n- 기록 : #b" + recordToString + "#k\r\n- 이번 สัปดาห์ ได้รับ한 กิลด์ 랭킹 คะแนน : #b" + guildScore + "점", ScriptMessageFlag.NpcReplacedByNpc);
                                getPlayer().updateOneInfo(32581, "weeklyRecord", String.valueOf(record));
                            }
                        }
                    }
                    break;
                }
            }
        }
    }


}
