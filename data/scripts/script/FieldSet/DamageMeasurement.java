package script.FieldSet;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import database.DBConfig;
import objects.fields.child.etc.DamageMeasurementRank;
import objects.fields.child.etc.Field_DamageMeasurement;
import objects.fields.fieldset.childs.MulungForestEnter;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.users.stats.SecondaryStatFlag;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

public class DamageMeasurement extends ScriptEngineNPC {

    public void Npc_9062025() {
        if (getPlayer().getMapId() != 993026900) {
            self.sayOk("พยายามอีกนิดนะครับ!!");
            return;
        }
        int v = 0;
        /*
         * if(!getPlayer().checkCharacterUse(1, 300, 7)) {
         * return;
         * }
         */
        if (DBConfig.isGanglim) {
            v = self.askMenu(
                    "#fs11#ผมคือ #bMr. Boche#k ผู้รับผิดชอบการวัดพลังต่อสู้ครับ\r\n#bพลังต่อสู้สูงสุดของท่าน #h ##k คือ : #r"
                            + DamageMeasurementRank.getUnit(DamageMeasurementRank.getDamage(getPlayer().getId()))
                            + "#k ครับ\r\nกรุณาเลือกเมนูที่ต้องการเกี่ยวกับพลังต่อสู้\r\n#L0#ฟังคำอธิบายเกี่ยวกับการวัดพลังต่อสู้\r\n#L2#ฟังคำอธิบายเกี่ยวกับรางวัลการวัดพลังต่อสู้\r\n\r\n#r#L1#ต้องการวัดพลังต่อสู้");
        } else {
            v = self.askMenu(
                    "ผมคือ #bMr. Boche#k ผู้รับผิดชอบการวัดพลังต่อสู้ครับ\r\n#bพลังต่อสู้สูงสุดของท่าน #h ##k คือ : #fs11##r"
                            + DamageMeasurementRank.getUnit(DamageMeasurementRank.getDamage(getPlayer().getId()))
                            + "#k#fs12# ครับ\r\nกรุณาเลือกเมนูที่ต้องการเกี่ยวกับพลังต่อสู้\r\n#L0#ฟังคำอธิบายเกี่ยวกับการวัดพลังต่อสู้\r\n#r#L1#ต้องการวัดพลังต่อสู้");
        }
        switch (v) {
            case 0: {
                if (DBConfig.isGanglim) {
                    self.sayOk(
                            "#fs11#การวัดพลังต่อสู้จะดำเนินไปเป็นเวลา 2 นาที ความเสียหายสูงสุดที่ทำได้จะถูกบันทึกลงในอันดับโดยอัตโนมัติ\r\nหุ่นฟางที่ช่วยวัดพลังจะถือว่าเป็นบอส และสามารถใช้ออปชั่นเพิ่มดาเมจบอสได้\r\nสามารถตรวจสอบอันดับพลังต่อสู้ได้ที่ ระบบอำนวยความสะดวก -> อันดับผู้เล่น");
                } else {
                    self.sayOk(
                            "การวัดพลังต่อสู้จะดำเนินไปเป็นเวลา 1 นาที ความเสียหายสูงสุดที่ทำได้จะถูกบันทึกลงในอันดับโดยอัตโนมัติ\r\nหุ่นฟางที่ช่วยวัดพลังจะถือว่าเป็นบอส และสามารถใช้ออปชั่นเพิ่มดาเมจบอสได้\r\nสามารถตรวจสอบอันดับพลังต่อสู้ได้ที่ ระบบอำนวยความสะดวก -> อันดับผู้เล่น");
                }
                break;
            }
            case 1: {
                LocalDateTime now = LocalDateTime.now();
                if ((now.getDayOfWeek() == DayOfWeek.SUNDAY && now.getHour() == 23 && now.getMinute() >= 50)
                        || (now.getDayOfWeek() == DayOfWeek.MONDAY && now.getHour() == 0 && now.getMinute() <= 10)) {
                    self.sayOk(
                            "วันอาทิตย์ เวลา 23:50 น. ~ วันจันทร์ เวลา 00:10 น. เป็นเวลาประมวลผลอันดับ ไม่สามารถท้าทายได้");
                    return;
                }
                if (getClient().getChannelServer().getMapFactory().getMap(993026800).getCharacters().size() > 0) {
                    self.sayOk("มีคนกำลังท้าทายอยู่\r\n#bกรุณาใช้แชนแนลอื่น#k");
                    return;
                }

                int vv = 0;
                if (DBConfig.isGanglim) {
                    vv = self.askMenu(
                            "#fs11#เลือกตำแหน่งหุ่นฟางที่จะวัดพลัง\r\n\r\n#e#r※ หากมีบันทึกของสัปดาห์ที่แล้ว บันทึกของสัปดาห์นี้จะไม่ถูกนับ และหากวัดพลังด้วยตัวละครอื่นในบัญชีเดียวกัน บันทึกเก่าจะถูกลบ#b#n\r\n#b #L0#ซ้าย#l\r\n #L1#กลาง#l\r\n #L2#ขวา#l");
                } else {
                    vv = self.askMenu(
                            "เลือกตำแหน่งหุ่นฟางที่จะวัดพลัง\r\n\r\n#e#r※ หากมีบันทึกของสัปดาห์ที่แล้ว บันทึกของสัปดาห์นี้จะไม่ถูกนับ และหากวัดพลังด้วยตัวละครอื่นในบัญชีเดียวกัน บันทึกเก่าจะถูกลบ#b#n\r\n#b #L0#ซ้าย#l\r\n #L1#กลาง#l\r\n #L2#ขวา#l");
                }
                if (getClient().getChannelServer().getMapFactory().getMap(993026800).getCharacters().size() > 0) {
                    self.sayOk("มีคนกำลังท้าทายอยู่\r\n#bกรุณาใช้แชนแนลอื่น#k");
                    return;
                }
                if (1 == self.askYesNo(
                        "#fs11#เพื่อความยุติธรรมในการวัดพลังต่อสู้\r\n\r\n#fs16#เมื่อเข้าสู่ลานประลอง #r#eบัฟทั้งหมดจะถูกยกเลิก#n#k#fs11#\r\n\r\nต้องการดำเนินการต่อหรือไม่?")) {
                    Field_DamageMeasurement map = (Field_DamageMeasurement) getClient().getChannelServer()
                            .getMapFactory().getMap(993026800);
                    map.spawnPoint = vv;
                    if (getClient().getChannelServer().getMapFactory().getMap(993026800).getCharacters().size() > 0) {
                        self.sayOk("มีคนกำลังท้าทายอยู่\r\n#bกรุณาใช้แชนแนลอื่น#k");
                        return;
                    } else {
                        int duration = 0;
                        if (getPlayer().getCooldownLimit(80002282) != 0L) { // 봉인된 룬의 힘 ปลดล็อก 악용 ห้อง지
                            duration = (int) getPlayer().getRemainCooltime(80002282);
                        }
                        getPlayer().cancelAllBuffs();
                        target.registerTransferField(993026800);
                        if (duration != 0) {
                            getPlayer().temporaryStatSet(80002282, duration, SecondaryStatFlag.RuneBlocked, 1);
                        }
                    }
                }
                break;
            }
            case 2: {
                if (DBConfig.isGanglim) {
                    self.sayOk(
                            "#fs11#ทุกวันจันทร์ เวลาเที่ยงคืน อันดับจะถูกรีเซ็ตและแจกรางวัลตามอันดับก่อนรีเซ็ต\r\n\r\n#rอันดับ 1~3#k : All Stat 1000, Attack/Magic 500\r\n#rอันดับสูงสุด 30%#k : All Stat 500, Attack/Magic 200\r\n#rอันดับสูงสุด 70%#k : All Stat 300, Attack/Magic 100\r\n#rอื่นๆ#k : All Stat 150, Attack/Magic 50");
                }
                break;
            }
        }
    }

    public void DamageRanking() {
        initNPC(MapleLifeFactory.getNPC(9076004));
        String menu = "กรุณาเลือกเมนูที่ต้องการ\r\n#b#L0#ดูอันดับทั้งหมด#l\r\n";
        menu += "#L1#ดูอันดับรางวัล#l";
        int v = self.askMenu(menu);
        switch (v) {
            case 0: // Ranking Check
                self.sayOk(DamageMeasurementRank.getRanks(50));
                break;
            case 1: // Reward Ranking Check
                self.sayOk(DamageMeasurementRank.getRewardRanks(50));
                break;
        }
    }

    public void mulung_forest() {
        // 100936 count, date
        initNPC(MapleLifeFactory.getNPC(2091011));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
        LocalDate lastDate = null;
        LocalDate nowDate = LocalDate.now();
        try {
            lastDate = LocalDate.parse(getPlayer().getOneInfo(100936, "date"), formatter);
        } catch (Exception e) {
            lastDate = null;
        }

        if ((lastDate != null && !lastDate.isEqual(nowDate)) || lastDate == null) {
            getPlayer().updateOneInfo(100936, "count", "0");
        }
        int remainCount = 5 - getPlayer().getOneInfoQuestInteger(100936, "count");

        int menu = self.askMenu(String.format(
                "ลึกเข้าไปในเมือง Mu Lung มีโรงฝึกลับของเหล่าเซียนอยู่\r\n\r\n (จำนวนครั้งที่เข้าได้วันนี้ : #r#e%d ครั้ง#k#n)\r\n#b#L0# ต้องการเข้าสู่โรงฝึกป่าหมอก#l\r\n#L1# อยากรู้เกี่ยวกับโรงฝึกป่าหมอก#l\r\n",
                remainCount));
        switch (menu) {
            case 0: { // Enter
                MulungForestEnter fieldSet = (MulungForestEnter) fieldSet("MulungForestEnter");
                if (fieldSet == null) {
                    self.sayOk("ตอนนี้ยังไม่สามารถใช้โรงฝึกป่าหมอกได้!");
                    return;
                }
                int enter = fieldSet.enter(target.getId(), 0);
                if (enter == -1)
                    self.say("มีสมาชิกปาร์ตี้ที่จำนวนครั้งท้าทายไม่เพียงพอ");
                else if (enter == 1)
                    self.say("ต้องสร้างปาร์ตี้ก่อนเพื่อเข้าท้าทาย");
                else if (enter == 2)
                    self.say("กรุณาให้หัวหน้าปาร์ตี้เป็นคนดำเนินการ");
                else if (enter == 3)
                    self.say("จำนวนสมาชิกปาร์ตี้ต้องมีอย่างน้อย " + fieldSet.minMember + " คน ขึ้นไป");
                else if (enter == 4)
                    self.say("เลเวลของสมาชิกปาร์ตี้ต้อง " + fieldSet.minLv + " ขึ้นไป");
                else if (enter == 5)
                    self.say("สมาชิกปาร์ตี้ต้องอยู่ที่นี่ครบทุกคน");
                else if (enter == 6)
                    self.say("มีปาร์ตี้อื่นกำลังท้าทายอยู่ข้างใน");

                break;
            }
            case 1: { // Explanation
                self.say(
                        "\r\n#b#e<โรงฝึกป่าหมอก>#n#k คือสถานที่ฝึกลับของเหล่าเซียน\r\nสำหรับผู้ที่ต้องการก้าวข้ามขีดจำกัดของตนเอง\r\n\r\nเป็นพื้นที่ลับที่ใช้เฉพาะเราเหล่าเซียนเท่านั้น #r#eแต่จะเปิดเผยให้เป็นพิเศษ#k#n \r\nจงขอบคุณซะล่ะ",
                        ScriptMessageFlag.NpcReplacedByNpc);
                self.say(
                        "\r\nใน #b#e<โรงฝึกป่าหมอก>#n#k คุณสามารถจำลองสถานการณ์การต่อสู้ได้ตามต้องการ\r\nสามารถปรับค่า #bHP, ป้องกัน#k และเรียก #bมอนสเตอร์ระดับเซียน#k \r\nออกมาได้\r\n\r\nนอกจากนี้ยังสามารถ #bเปลี่ยนชนิดและค่า Force#k ของแผนที่ได้ตามต้องการอีกด้วย",
                        ScriptMessageFlag.NpcReplacedByNpc);
                self.say("\r\nแต่สิทธิ์ในการตั้งค่ามอนสเตอร์และแผนที่ #r#eอยู่ที่หัวหน้าปาร์ตี้เท่านั้น#k#n",
                        ScriptMessageFlag.NpcReplacedByNpc);
                self.say(
                        "\r\nสมาชิกปาร์ตี้ทุกคนสามารถ #bฟื้นฟู HP MP ทั้งหมดได้ 1 ครั้ง#k เมื่อต้องการ\r\n\r\nก็เพราะว่าเป็น #b#eโรงฝึกป่าหมอก#n#k ยังไงล่ะ ถึงทำได้",
                        ScriptMessageFlag.NpcReplacedByNpc);
                self.say(
                        "\r\nแต่เนื่องจากเป็นพื้นที่พิเศษ จะให้ใครเข้าก็ได้ไม่ได้หรอกนะ\r\nต้องมีเลเวล #r#e200#k#n และผ่าน #r#eMu Lung ชั้น 30#k#n ถึงจะเข้าได้",
                        ScriptMessageFlag.NpcReplacedByNpc);
                self.say(
                        "\r\nสามารถเข้าได้เฉพาะ #r#eสถานะปาร์ตี้#k#n เท่านั้น\r\nและสามารถใช้งานได้ #r#e60 นาที#k#n เท่านั้น โปรดระวังดว้ย",
                        ScriptMessageFlag.NpcReplacedByNpc);
                break;
            }
        }
    }

    public void Training_exit() {
        initNPC(MapleLifeFactory.getNPC(2091011));
        if (self.askYesNo("จะเลิกฝึกแล้วออกไปเหรอ?") == 1) {
            if (getPlayer().getEventInstance() != null) {
                getPlayer().getEventInstance().unregisterPlayer(getPlayer());
            }
            registerTransferField(925020001);
        }
    }
}
