package script.FieldSet;

import java.util.Date;
import java.text.SimpleDateFormat;

import database.DBConfig;
import objects.fields.child.etc.DamageMeasurementRank;
import objects.fields.child.etc.Field_DamageMeasurement;
import objects.fields.fieldset.childs.MulungForestEnter;
import objects.fields.fieldset.childs.NormalDemianEnter;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.users.stats.SecondaryStatFlag;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DamageMeasurement extends ScriptEngineNPC {


    public void Npc_9062025() {
        if (getPlayer().getMapId() != 993026900) {
            self.sayOk("นิดหน่อย만 더 힘을 내세요!!");
            return;
        }
        int v = 0;
        /*if(!getPlayer().checkCharacterUse(1, 300, 7)) {
            return;
        }*/
        if (DBConfig.isGanglim) {
            v = self.askMenu("#fs11#전투력 측정을 담당 있는 #bMr.보체#k .\r\n#b#h ##k 님의 최고 전투력은 : #r" + DamageMeasurementRank.getUnit(DamageMeasurementRank.getDamage(getPlayer().getId())) + "#k .\r\n전투력은 원하시는 เมนู를 เลือก해보시겠어요?\r\n#L0#전투력 측정 อธิบาย을 듣는다.\r\n#L2#전투력 측정 รางวัล อธิบาย을 듣는다.\r\n\r\n#r#L1#전투력을 측정할게요.");
        } else {
            v = self.askMenu("전투력 측정을 담당 있는 #bMr.보체#k .\r\n#b#h ##k 님의 최고 전투력은 : #fs11##r" + DamageMeasurementRank.getUnit(DamageMeasurementRank.getDamage(getPlayer().getId())) + "#k#fs12# .\r\n전투력은 원하시는 เมนู를 เลือก해보시겠어요?\r\n#L0#전투력 측정 อธิบาย을 듣는다.\r\n#r#L1#전투력을 측정할게요.");
        }
        switch (v) {
            case 0: {
                if (DBConfig.isGanglim) {
                    self.sayOk("#fs11#전투력 측정은 2นาที간 ดำเนินการ 최고 Damage를 달성 อัตโนมัติ으로 랭킹에 반영.\r\n전투력 측정을 도와줄 허수아비는 บอส มอนสเตอร์ 취급 บอส เพิ่ม Damage ตัวเลือก ใช้งาน.\r\n전투력 랭킹은 편의ระบบ → 유저랭킹에서 ยืนยันโปรด.");
                } else {
                    self.sayOk("전투력 측정은 1นาที간 ดำเนินการ 최고 Damage를 달성 อัตโนมัติ으로 랭킹에 반영.\r\n전투력 측정을 도와줄 허수아비는 บอส มอนสเตอร์ 취급 บอส เพิ่ม Damage ตัวเลือก ใช้งาน.\r\n전투력 랭킹은 편의ระบบ → 유저랭킹에서 ยืนยันโปรด.");
                }
                break;
            }
            case 1: {
                Date date = new Date();
                if ((date.getDay() == 0 && date.getHours() == 23 && date.getMinutes() >= 50) || (date.getDay() == 1 && date.getHours() == 0 && date.getMinutes() <= 10)) {
                    self.sayOk("วัน요วัน 23시 50นาที ~ เดือน요วัน 00시 10นาที까지는 랭킹 집계เวลา이므로 도전할 수 없.");
                    return;
                }
                if (getClient().getChannelServer().getMapFactory().getMap(993026800).getCharacters().size() > 0) {
                    self.sayOk("이미 누군가가 도전중.\r\n#b다른 แชนแนล 이용해 สัปดาห์세요.#k");
                    return;
                }

                int vv = 0;
                if (DBConfig.isGanglim) {
                    vv = self.askMenu("#fs11#전투력을 측정할 허수아비 ตำแหน่ง를 เลือกโปรด.\r\n\r\n#e#r※ 지난สัปดาห์ 측정기록이 있을 시 이번สัปดาห์는 랭킹에 기록되지 않. เพิ่ม บัญชี 내 다른 ตัวละคร 기록은 ลบ.#b#n\r\n#b #L0#좌측#l\r\n #L1#중앙#l\r\n #L2#우측#l");
                } else {
                    vv = self.askMenu("전투력을 측정할 허수아비 ตำแหน่ง를 เลือกโปรด.\r\n\r\n#e#r※ 지난สัปดาห์ 측정기록이 있을 시 이번สัปดาห์는 랭킹에 기록되지 않. เพิ่ม บัญชี 내 다른 ตัวละคร 기록은 ลบ.#b#n\r\n#b #L0#좌측#l\r\n #L1#중앙#l\r\n #L2#우측#l");
                }
                if (getClient().getChannelServer().getMapFactory().getMap(993026800).getCharacters().size() > 0) {
                    self.sayOk("이미 누군가가 도전중.\r\n#b다른 แชนแนล 이용해 สัปดาห์세요.#k");
                    return;
                }
                if (1 == self.askYesNo("#fs11#전투력 측정을 공정하게 ดำเนินการ하기 위해\r\n\r\n#fs16#เข้า 시 #r#eทั้งหมด Buff ปลดล็อก#n#k.#n#fs11#\r\n\r\nดำเนินการต้องการหรือไม่?")) {
                    Field_DamageMeasurement map = (Field_DamageMeasurement) getClient().getChannelServer().getMapFactory().getMap(993026800);
                    map.spawnPoint = vv;
                    if (getClient().getChannelServer().getMapFactory().getMap(993026800).getCharacters().size() > 0) {
                        self.sayOk("이미 누군가가 도전중.\r\n#b다른 แชนแนล 이용해 สัปดาห์세요.#k");
                        return;
                    } else {
                        int duration = 0;
                        if(getPlayer().getCooldownLimit(80002282) != 0L){ // 봉인된 룬의 힘 ปลดล็อก 악용 방지
                            duration = (int) getPlayer().getRemainCooltime(80002282);
                        }
                        getPlayer().cancelAllBuffs();
                        target.registerTransferField(993026800);
                        if(duration != 0){
                            getPlayer().temporaryStatSet(80002282, duration, SecondaryStatFlag.RuneBlocked, 1);
                        }
                    }
                }
                break;
            }
            case 2: {
                if (DBConfig.isGanglim) {
                    self.sayOk("#fs11#매สัปดาห์ เดือน요วัน 자정에 랭킹이 วินาที기화 วินาที기화ก่อนหน้า의 랭킹 기준으로 รางวัล이 지급\r\n\r\n#r랭킹 1~3위#k : 올Stat 1000, โจมตี력/마력 500\r\n#r랭킹 상위 30%#k : 올Stat 500, โจมตี력/마력 200\r\n#r랭킹 상위 70%#k : 올Stat 300, โจมตี력/마력 100\r\n#r그 외#k : 올Stat 150, โจมตี력/마력 50");
                }
                break;
            }
        }
    }

    public void DamageRanking() {
        initNPC(MapleLifeFactory.getNPC(9076004));
        String menu = "원하시는 เมนู를 เลือกโปรด.\r\n#b#L0#ทั้งหมด 랭킹ดู#l\r\n";
        menu += "#L1#รางวัล 랭킹ดู#l";
        int v = self.askMenu(menu);
        switch (v) {
            case 0: //ทั้งหมด랭킹ดู
                self.sayOk(DamageMeasurementRank.getRanks(50));
                break;
            case 1: //รางวัล랭킹ดู
                self.sayOk(DamageMeasurementRank.getRewardRanks(50));
                break;
        }
    }

    public void mulung_forest() {
        //100936 count, date
        initNPC(MapleLifeFactory.getNPC(2091011));
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
        Date lastTime = null;
        Date now = null;
        try {
            lastTime = sdf.parse(getPlayer().getOneInfo(100936, "date"));
        } catch (Exception e) {
            lastTime = null;
        }
        try {
            now = sdf.parse(sdf.format(new Date()));
        } catch (Exception e) {
            lastTime = null;
        }
        if ((lastTime != null && !lastTime.equals(now)) || lastTime == null) {
            getPlayer().updateOneInfo(100936, "count", "0");
        }
        int remainCount = 5 - getPlayer().getOneInfoQuestInteger(100936, "count");

        int menu = self.askMenu(String.format("무릉 깊숙한 곳엔 우리 선인들만의 비밀 수련장이 있지.\r\n\r\n (วันนี้ 남은 เข้า 횟수 : #r#e%d회#k#n)\r\n#b#L0# 안개 숲 수련장에 เข้า 싶어.#l\r\n#L1# 안개 숲 수련장에 대해 알고 싶어.#l\r\n", remainCount));
        switch (menu) {
            case 0: { //เข้า
                MulungForestEnter fieldSet = (MulungForestEnter) fieldSet("MulungForestEnter");
                if (fieldSet == null) {
                    self.sayOk("지금은 안개숲 수련장을 이용할 수 없어!");
                    return;
                }
                int enter = fieldSet.enter(target.getId(), 0);
                if (enter == -1) self.say("วันวัน 도전횟수가 ไม่พอ한 ปาร์ตี้원이 존재.");
                else if (enter == 1) self.say("ปาร์ตี้ 맺어야만 도전할 수 있.");
                else if (enter == 2) self.say("ปาร์ตี้장을 통해 ดำเนินการ해 สัปดาห์십시오.");
                else if (enter == 3) self.say( "ต่ำสุด " + fieldSet.minMember + "인 이상의 ปาร์ตี้ เควส เริ่ม할 수 있.");
                else if (enter == 4) self.say( "ปาร์ตี้원의 เลเวล ต่ำสุด " + fieldSet.minLv + " 이상이어야 .");
                else if (enter == 5) self.say("ปาร์ตี้원이 모두 모여 있어야 เริ่ม할 수 있.");
                else if (enter == 6) self.say( "이미 다른 ปาร์ตี้ 안으로 들어가 เควส 클리어에 도전 있는 중.");

                break;
            }
            case 1: { //อธิบาย듣기
                self.say("\r\n#b#e<안개 숲 수련장>#n#k 자신의 한계를 넘어서고 싶은 \r\n#b선인들만의 비밀 수련장#k이지.\r\n\r\n 우리 선인들만 쓰는 비밀 공간인데 #r#e특별히#k#n 공개할 테니 \r\n감사하라고.", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("\r\n#b#e<안개 숲 수련장>#n#k에서는 원하는 전투 สถานการณ์을 만들 수 있어.\r\n HP, ป้องกัน율 등의 #bความสามารถ치를 조절#k해서 #b선인 바위 มอนสเตอร์#k \r\n소환할 수 있어.\r\n\r\n 필드의 #b포스 종류를 เปลี่ยน#k #b포스 수치#k 원하는 대로 \r\n바꿀 수도 있지.", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("\r\n단, มอนสเตอร์ ตั้งค่า 필드 ตั้งค่า 권한은 #r#eปาร์ตี้장에게만#k#n 있어.", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("\r\n ทั้งหมด ปาร์ตี้원들이 원할 때마다 #bHP MP 모두 สูงสุด치로 \r\n1회 회복#k할 수도 있어.\r\n\r\n #b#e안개 숲 수련장이니까#n#k เป็นไปได้한 거라고?", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("\r\n단, 특별한 공간인 만큼 아무나 들여보낼 수는 없지.\r\n#r#e200 เลเวล#k#n #r#e무릉 30층#k#n 도달해야 เข้า할 수 있어.", ScriptMessageFlag.NpcReplacedByNpc);
                self.say("\r\n수련장에는 #r#eปาร์ตี้ สถานะ로만#k#n เข้า이 เป็นไปได้해.\r\n그리고 #r#e60นาที만#k#n 이용할 수 있으니까  점 유의하도록.", ScriptMessageFlag.NpcReplacedByNpc);
                break;
            }
        }
    }

    public void Training_exit() {
        initNPC(MapleLifeFactory.getNPC(2091011));
        if (self.askYesNo("수련을 그만두고 나갈꺼야?") == 1) {
            if (getPlayer().getEventInstance() != null) {
                getPlayer().getEventInstance().unregisterPlayer(getPlayer());
            }
            registerTransferField(925020001);
        }
    }
}
