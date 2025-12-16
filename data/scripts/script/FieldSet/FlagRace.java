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
        String course = "Daylight Snowfield";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date1 = sdf.parse("20210802");
            Date date2 = new Date();
            long time = date2.getTime() - date1.getTime();
            long day = TimeUnit.MILLISECONDS.toDays(time);
            if ((day / 7) % 3 == 0) {
                course = "Daylight Snowfield";
            } else if ((day / 7) % 3 == 1) {
                course = "Sunset Snowfield";
            } else if ((day / 7) % 3 == 2) {
                course = "Midnight Snowfield";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int v = self.askMenu(
                "#fs11#\r\nเมื่อก่อนสร้างเกมในโลกอันไกลโพ้นไม่ได้ฮิตขนาดนี้นะเนี่ย... อยากจะมาสนุกกับ Flag Race ในโลกอันสวยงามของฉันสินะ?\r\n\r\nสนามแข่งสัปดาห์นี้ : #r#e"
                        + course
                        + "#n#k\r\n\r\n#b#L0#อยากท้าทาย Flag Race ครับ#l#k\r\n#b#L1#ช่วยย้ายไปห้องรอฝึกซ้อม Flag Race หน่อยครับ#l#k\r\n#b#L2#ช่วยอธิบายเกี่ยวกับ Flag Race หน่อยครับ#l#k",
                ScriptMessageFlag.NpcReplacedByNpc);
        switch (v) {
            case 0: // อยากท้าทาย Flag Race
                if (getPlayer().getGuild() == null) {
                    self.say(
                            "#fs11#\r\nFlag Race สามารถเข้าร่วมได้เฉพาะ #rผู้ที่มีคะแนนภารกิจกิลด์รายสัปดาห์ 1 คะแนนขึ้นไป#k เท่านั้น ไปทำตามเงื่อนไขมาก่อนนะ",
                            ScriptMessageFlag.NpcReplacedByNpc);
                } else {
                    if (getPlayer().getGuild().getPointLogByType(GuildContentsType.WEEK_MISSIONS, getPlayer()) < 0) {
                        self.say(
                                "#fs11#\r\nFlag Race สามารถเข้าร่วมได้เฉพาะ #rผู้ที่มีคะแนนภารกิจกิลด์รายสัปดาห์ 1 คะแนนขึ้นไป#k เท่านั้น ไปทำตามเงื่อนไขมาก่อนนะ",
                                ScriptMessageFlag.NpcReplacedByNpc);
                    } else {
                        FieldSet fieldSet = fieldSet("FlagRaceEnter");
                        if (fieldSet == null) {
                            self.sayOk("#fs11#ตอนนี้ไม่สามารถเข้า Flag Race ได้");
                            return;
                        }
                        int enter = fieldSet.enter(target.getId(), 0);
                        if (enter == -1)
                            self.say("#fs11#ไม่สามารถเข้าได้ด้วยสาเหตุที่ไม่ทราบ กรุณาลองใหม่ภายหลัง");
                        else if (enter == 1)
                            self.sayOk("#fs11#<Flag Race> เข้าได้คนเดียวเท่านั้น\r\nกรุณาถอนปาร์ตี้แล้วมาใหม่นะ");
                        else if (enter == 2)
                            self.say("#fs11#ต้องมีเลเวลขั้นต่ำ " + fieldSet.minLv + " ขึ้นไปถึงจะเข้า Flag Race ได้");
                        else if (enter == -2)
                            self.sayOk("#fs11#Flag Race จัดขึ้นทุกชั่วโมง นาทีที่ 30 ตรวจสอบเวลาแล้วลองใหม่นะ");
                        else if (enter == -3)
                            self.sayOk(
                                    "#fs11#วันอาทิตย์ 23:00 ~ วันจันทร์ 01:00 เป็นเวลาสรุปคะแนน Noblesse เข้าได้แค่โหมดฝึกซ้อมเท่านั้น~! ตรวจสอบเวลาแล้วลองใหม่นะ");
                    }
                }
                break;
            case 1: // ย้ายไปห้องรอฝึก
                if (1 == self.askYesNo("#fs11##bห้องรอฝึกซ้อม Flag Race#k จะย้ายไปตอนนี้เลยไหม?",
                        ScriptMessageFlag.NpcReplacedByNpc)) {
                    registerTransferField(942003050); // ห้องรอฝึก
                }
                break;
            case 2: // อธิบาย
                int vv = self.askMenu(
                        "#fs11#คิคิคิ, อยากรู้อะไรเกี่ยวกับ Flag Race หรอ?\r\n\r\n#b#L0#Flag Race คืออะไร?#l\r\n#b#L1#Flag Race ดีตรงไหน?#l\r\n#L4#อยากรู้เกณฑ์คะแนนอันดับ Flag Race#l\r\n#L3#เปล่าครับ ไม่มีอะไร#l",
                        ScriptMessageFlag.NpcReplacedByNpc);
                switch (vv) {
                    case 0: // คืออะไร
                        self.say("#fs11#\r\nอยากรู้เกี่ยวกับ Flag Race ผลงานสร้างอันยิ่งใหญ่ของฉันหรอ?",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say(
                                "#fs11#\r\nกติกาง่ายมาก จัดขึ้น #rทุกชั่วโมง นาทีที่ 30#k, เฉพาะ #rสมาชิกกิลด์ที่มีคะแนนภารกิจรายสัปดาห์ 1 คะแนนขึ้นไป#k เท่านั้นที่เข้าร่วมได้",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("#fs11#\r\nแค่วิ่งไปให้ถึงเส้นชัยเร็วๆ ก็พอ แน่นอนว่าอาจจะมีอุปสรรคนิด---หน่อยนะ",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say(
                                "#fs11#\r\nใน Flag Race ไม่ได้มีแค่การเดินและกระโดดเพื่อแข่งขันอย่างยุติธรรมหรอกนะ แต่มี Flag Skill ให้ใช้ได้เฉพาะในสนามด้วย",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say(
                                "#fs11#\r\nลองผสมผสาน Flag Skill กับกลไกในแผนที่ดูสิ จะไปในเส้นทางที่สถานการณ์ปกติไปไม่ได้ หรือผ่านสนามได้เร็วขึ้นก็ได้นะ",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say(
                                "#fs11#\r\nอ้อ แล้วก็ตอนเข้า Race สถานะแปลงร่างทั้งหมดจะถูกยกเลิก และต่อให้ใช้น้ำยาแปลงร่างระหว่างแข่งก็ไม่แสดงผล ระวังจะเสียน้ำยาฟรีล่ะ",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say(
                                "#fs11#\r\nเลเวล 101 ขึ้นไปเท่านั้นถึงจะเข้าร่วมได้ ตรวจสอบให้ดีล่ะ แต่ถ้าเลเวลเกิน 101 มานานแล้วก็ช่างเถอะ...",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say("#fs11#\r\nงั้นก็ พยายามเข้านะ", ScriptMessageFlag.NpcReplacedByNpc);
                        break;
                    case 1: // ดีตรงไหน
                        self.say(
                                "#fs11#\r\nดีตรงไหนงั้นหรอ? มีทางก็ต้องวิ่ง มีอุปสรรคก็ต้องข้าม จำเป็นต้องมีข้อดีด้วยหรือไง?",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say(
                                "#fs11#\r\n...ล้อเล่นน่า SP ที่เอาไปอัป Noblesse Skill ของกิลด์ได้ จะได้รับตามอันดับกิลด์ใน Flag Race ประจำสัปดาห์ไงล่ะ",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say(
                                "#fs11##b#eอันดับ 1 : SP 25 แต้ม\r\nอันดับ 2 : SP 22 แต้ม\r\nอันดับ 3 : SP 20 แต้ม\r\nอันดับ 4 ~ 10 : SP 18 แต้ม\r\nท็อป 10% : SP 16 แต้ม\r\nท็อป 20% : SP 14 แต้ม\r\nท็อป 30% : SP 12 แต้ม\r\nท็อป 40% : SP 10 แต้ม\r\nท็อป 50% : SP 9 แต้ม\r\nท็อป 60% : SP 8 แต้ม\r\nท็อป 70% : SP 7 แต้ม\r\nท็อป 80% : SP 6 แต้ม\r\n1,000 คะแนนขึ้นไป : SP 5 แต้ม#n#k\r\n\r\n#r#e※ แต่ว่า, ถ้าทำได้ไม่ถึง 1,000 คะแนน ต่อให้ติดอันดับก็ไม่ได้รับรางวัลนะ#k#n",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say(
                                "#fs11#\r\nอันดับกิลด์ Flag Race คิดจากผลรวมคะแนน Flag Race ของสมาชิกแต่ละคน กิลด์ที่มีคนวิ่งเก่งๆ เยอะก็ยิ่งได้อันดับสูงนะ",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say(
                                "#fs11#\r\nคะแนน Flag Race ของเจ้าจะตัดสินจากสถิติที่ดีที่สุดในสัปดาห์ ถ้าไม่พอใจสถิติตอนนี้ก็มาท้าทายใหม่ได้ตลอด แน่นอนว่าต้องเป็นตอนที่เปิดนะ",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say(
                                "#fs11#\r\nอ้อ ถ้าออกจากกิลด์หรือถูกไล่ออก สถิติสูงสุดรายสัปดาห์จะถูกรีเซต คะแนนรวมกิลด์ก็จะถูกหักออกไปด้วย ระวังให้ดีล่ะ",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say(
                                "#fs11#\r\nแล้วก็อันดับไม่ได้อยู่ตลอดไป ทุกวันจันทร์เวลา 00:00 นาฬิกา อันดับและ Noblesse Skill จะถูกรีเซตทั้งหมด แล้วจะมอบ SP Noblesse Skill ให้ตามอันดับที่สรุปได้ตอนนั้น อย่าผิดหวังไปล่ะถ้าสัปดาห์นี้ล้มเหลว",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        break;
                    case 4: // เกณฑ์คะแนน
                        self.say(
                                "#fs11#\r\nคะแนนอันดับที่หาได้จาก Flag Race ตัดสินจากเวลาที่ใช้ในการวิ่งครบ 3 รอบ ไม่เกี่ยวกับคนอื่น แค่วิ่งให้ดียังไงก็ได้คะแนนสูง",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say(
                                "#fs11#\r\nเกณฑ์คะแนนอันดับตัดสินจากสถิติสูงสุดของเจ้าในสัปดาห์นั้น มาท้าทายใหม่เพื่อทำสถิติให้สูงขึ้นได้นะถ้ายังไม่พอใจ",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say(
                                "#fs11#\r\nสุดท้าย สนาม Flag Race จะเปลี่ยนทุกสัปดาห์วันจันทร์เวลา 00:00 นาฬิกา แต่ละสนามมีเกณฑ์คะแนนต่างกัน ศึกษาให้ดีแล้ววิ่งอย่างฉลาดล่ะ",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say(
                                "#fs11#\r\n#e[เกณฑ์คะแนนอันดับ Daylight Snowfield]#n\r\n\r\nต่ำกว่า 2 นาที 10 วินาที : #b#e1,000 คะแนน#n#k\r\n2 นาที 10 วินาที ~ ต่ำกว่า 2 นาที 15 วินาที : #b#e800 คะแนน#n#k\r\n2 นาที 15 วินาที ~ ต่ำกว่า 2 นาที 20 วินาที : #b#e650 คะแนน#n#k\r\n2 นาที 20 วินาที ~ ต่ำกว่า 2 นาที 25 วินาที : #b#e550 คะแนน#n#k\r\n2 นาที 25 วินาที ~ ต่ำกว่า 2 นาที 30 วินาที : #b#e450 คะแนน#n#k\r\n2 นาที 30 วินาที ~ ต่ำกว่า 2 นาที 35 วินาที : #b#e400 คะแนน#n#k\r\n2 นาที 35 วินาที ~ ต่ำกว่า 2 นาที 40 วินาที : #b#e350 คะแนน#n#k\r\n2 นาที 40 วินาที ~ ต่ำกว่า 2 นาที 50 วินาที : #b#e300 คะแนน#n#k\r\n2 นาที 50 วินาที ~ ต่ำกว่า 3 นาที 00 วินาที : #b#e250 คะแนน#n#k\r\n3 นาที 00 วินาที ~ ต่ำกว่า 3 นาที 10 วินาที : #b#e200 คะแนน#n#k\r\n3 นาที 10 วินาที ขึ้นไป : #b#e100 คะแนน#n#k",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say(
                                "#fs11#\r\n#e[เกณฑ์คะแนนอันดับ Sunset Snowfield]#n\r\n\r\nต่ำกว่า 1 นาที 50 วินาที : #b#e1,000 คะแนน#n#k\r\n1 นาที 50 วินาที ~ ต่ำกว่า 1 นาที 55 วินาที : #b#e800 คะแนน#n#k\r\n1 นาที 55 วินาที ~ ต่ำกว่า 2 นาที 00 วินาที : #b#e650 คะแนน#n#k\r\n2 นาที 00 วินาที ~ ต่ำกว่า 2 นาที 05 วินาที : #b#e550 คะแนน#n#k\r\n2 นาที 05 วินาที ~ ต่ำกว่า 2 นาที 10 วินาที : #b#e450 คะแนน#n#k\r\n2 นาที 10 วินาที ~ ต่ำกว่า 2 นาที 15 วินาที : #b#e400 คะแนน#n#k\r\n2 นาที 15 วินาที ~ ต่ำกว่า 2 นาที 20 วินาที : #b#e350 คะแนน#n#k\r\n2 นาที 20 วินาที ~ ต่ำกว่า 2 นาที 30 วินาที : #b#e300 คะแนน#n#k\r\n2 นาที 30 วินาที ~ ต่ำกว่า 2 นาที 40 วินาที : #b#e250 คะแนน#n#k\r\n2 นาที 40 วินาที ~ ต่ำกว่า 2 นาที 50 วินาที : #b#e200 คะแนน#n#k\r\n2 นาที 50 วินาที ขึ้นไป : #b#e100 คะแนน#n#k",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        self.say(
                                "#fs11#\r\n#e[เกณฑ์คะแนนอันดับ Midnight Snowfield]#n\r\n\r\nต่ำกว่า 2 นาที 30 วินาที : #b#e1,000 คะแนน#n#k\r\n2 นาที 30 วินาที ~ ต่ำกว่า 2 นาที 35 วินาที : #b#e800 คะแนน#n#k\r\n2 นาที 35 วินาที ~ ต่ำกว่า 2 นาที 40 วินาที : #b#e650 คะแนน#n#k\r\n2 นาที 40 วินาที ~ ต่ำกว่า 2 นาที 45 วินาที : #b#e550 คะแนน#n#k\r\n2 นาที 45 วินาที ~ ต่ำกว่า 2 นาที 50 วินาที : #b#e450 คะแนน#n#k\r\n2 นาที 50 วินาที ~ ต่ำกว่า 2 นาที 55 วินาที : #b#e400 คะแนน#n#k\r\n2 นาที 55 วินาที ~ ต่ำกว่า 3 นาที 00 วินาที : #b#e350 คะแนน#n#k\r\n3 นาที 00 วินาที ~ ต่ำกว่า 3 นาที 10 วินาที : #b#e300 คะแนน#n#k\r\n3 นาที 10 วินาที ~ ต่ำกว่า 3 นาที 20 วินาที : #b#e250 คะแนน#n#k\r\n3 นาที 20 วินาที ~ ต่ำกว่า 3 นาที 30 วินาที : #b#e200 คะแนน#n#k\r\n3 นาที 30 วินาที ขึ้นไป : #b#e100 คะแนน#n#k",
                                ScriptMessageFlag.NpcReplacedByNpc);
                        break;
                    case 3: // ไม่มีอะไร
                        self.sayOk("#fs11#\r\nงั้นหรอ จะทำหรือไม่ทำอะไรก็เรื่องของเจ้านะ",
                                ScriptMessageFlag.NpcReplacedByNpc);
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
                    if (1 == self
                            .askAccept("#fs11#คิคิคิ, สนามฝึกซ้อมเป็นยังไงบ้าง? จะให้ส่งกลับไปห้องรอฝึกซ้อมไหม?")) {
                        registerTransferField(942003050); // Flag Race : ห้องรอฝึก
                    }
                } else {
                    int v = self.askMenu(
                            "#fs11#สนุกกับ Flag Race ไหม? มีธุระอะไรหรือเปล่า?\r\n#b#L0#อยากออกไปจากที่นี่ครับ#l\r\n#b#L1#ช่วยอธิบายเกี่ยวกับ Flag Race หน่อยครับ#l");
                    switch (v) {
                        case 1: { // ช่วยอธิบายเกี่ยวกับ Flag Race หน่อยครับ
                            int vv = self.askMenu(
                                    "#fs11#คิคิคิ, อยากรู้อะไรเกี่ยวกับ Flag Race หรอ?\r\n\r\n#b#L0#Flag Race คืออะไร?#l\r\n#b#L1#Flag Race ดีตรงไหน?#l\r\n#L4#อยากรู้เกณฑ์คะแนนอันดับ Flag Race#l\r\n#L3#เปล่าครับ ไม่มีอะไร#l");
                            switch (vv) {
                                case 0: // Flag Race คืออะไร?
                                    self.say("#fs11#\r\nอยากรู้เกี่ยวกับ Flag Race ผลงานสร้างอันยิ่งใหญ่ของฉันหรอ?");
                                    self.say(
                                            "#fs11#\r\nกติกาง่ายมาก จัดขึ้น #rทุกชั่วโมง นาทีที่ 30#k, เฉพาะ #rสมาชิกกิลด์ที่มีคะแนนภารกิจรายสัปดาห์ 1 คะแนนขึ้นไป#k เท่านั้นที่เข้าร่วมได้");
                                    self.say(
                                            "#fs11#\r\nแค่วิ่งไปให้ถึงเส้นชัยเร็วๆ ก็พอ แน่นอนว่าอาจจะมีอุปสรรคนิด---หน่อยนะ");
                                    self.say(
                                            "#fs11#\r\nใน Flag Race ไม่ได้มีแค่การเดินและกระโดดเพื่อแข่งขันอย่างยุติธรรมหรอกนะ แต่มี Flag Skill ให้ใช้ได้เฉพาะในสนามด้วย");
                                    self.say(
                                            "#fs11#\r\nลองผสมผสาน Flag Skill กับกลไกในแผนที่ดูสิ จะไปในเส้นทางที่สถานการณ์ปกติไปไม่ได้ หรือผ่านสนามได้เร็วขึ้นก็ได้นะ");
                                    self.say(
                                            "#fs11#\r\nอ้อ แล้วก็ตอนเข้า Race สถานะแปลงร่างทั้งหมดจะถูกยกเลิก และต่อให้ใช้น้ำยาแปลงร่างระหว่างแข่งก็ไม่แสดงผล ระวังจะเสียน้ำยาฟรีล่ะ");
                                    self.say(
                                            "#fs11#\r\nเลเวล 101 ขึ้นไปเท่านั้นถึงจะเข้าร่วมได้ ตรวจสอบให้ดีล่ะ แต่ถ้าเลเวลเกิน 101 มานานแล้วก็ช่างเถอะ...");
                                    self.say("#fs11#\r\nงั้นก็ พยายามเข้านะ");
                                    break;
                                case 1: // Flag Race ดีตรงไหน?
                                    self.say(
                                            "#fs11#\r\nดีตรงไหนงั้นหรอ? มีทางก็ต้องวิ่ง มีอุปสรรคก็ต้องข้าม จำเป็นต้องมีข้อดีด้วยหรือไง?");
                                    self.say(
                                            "#fs11#\r\n...ล้อเล่นน่า SP ที่เอาไปอัป Noblesse Skill ของกิลด์ได้ จะได้รับตามอันดับกิลด์ใน Flag Race ประจำสัปดาห์ไงล่ะ");
                                    self.say(
                                            "#fs11##b#eอันดับ 1 : SP 25 แต้ม\r\nอันดับ 2 : SP 22 แต้ม\r\nอันดับ 3 : SP 20 แต้ม\r\nอันดับ 4 ~ 10 : SP 18 แต้ม\r\nท็อป 10% : SP 16 แต้ม\r\nท็อป 20% : SP 14 แต้ม\r\nท็อป 30% : SP 12 แต้ม\r\nท็อป 40% : SP 10 แต้ม\r\nท็อป 50% : SP 9 แต้ม\r\nท็อป 60% : SP 8 แต้ม\r\nท็อป 70% : SP 7 แต้ม\r\nท็อป 80% : SP 6 แต้ม\r\n1,000 คะแนนขึ้นไป : SP 5 แต้ม#n#k\r\n\r\n#r#e※ แต่ว่า, ถ้าทำได้ไม่ถึง 1,000 คะแนน ต่อให้ติดอันดับก็ไม่ได้รับรางวัลนะ#k#n");
                                    self.say(
                                            "#fs11#\r\nอันดับกิลด์ Flag Race คิดจากผลรวมคะแนน Flag Race ของสมาชิกแต่ละคน กิลด์ที่มีคนวิ่งเก่งๆ เยอะก็ยิ่งได้อันดับสูงนะ");
                                    self.say(
                                            "#fs11#\r\nคะแนน Flag Race ของเจ้าจะตัดสินจากสถิติที่ดีที่สุดในสัปดาห์ ถ้าไม่พอใจสถิติตอนนี้ก็มาท้าทายใหม่ได้ตลอด แน่นอนว่าต้องเป็นตอนที่เปิดนะ");
                                    self.say(
                                            "#fs11#\r\nอ้อ ถ้าออกจากกิลด์หรือถูกไล่ออก สถิติสูงสุดรายสัปดาห์จะถูกรีเซต คะแนนรวมกิลด์ก็จะถูกหักออกไปด้วย ระวังให้ดีล่ะ");
                                    self.say(
                                            "#fs11#\r\nแล้วก็อันดับไม่ได้อยู่ตลอดไป ทุกวันจันทร์เวลา 00:00 นาฬิกา อันดับและ Noblesse Skill จะถูกรีเซตทั้งหมด แล้วจะมอบ SP Noblesse Skill ให้ตามอันดับที่สรุปได้ตอนนั้น อย่าผิดหวังไปล่ะถ้าสัปดาห์นี้ล้มเหลว");
                                    break;
                                case 4: // เกณฑ์คะแนน
                                    self.say(
                                            "#fs11#\r\nคะแนนอันดับที่หาได้จาก Flag Race ตัดสินจากเวลาที่ใช้ในการวิ่งครบ 3 รอบ ไม่เกี่ยวกับคนอื่น แค่วิ่งให้ดียังไงก็ได้คะแนนสูง");
                                    self.say(
                                            "#fs11#\r\nเกณฑ์คะแนนอันดับตัดสินจากสถิติสูงสุดของเจ้าในสัปดาห์นั้น มาท้าทายใหม่เพื่อทำสถิติให้สูงขึ้นได้นะถ้ายังไม่พอใจ");
                                    self.say(
                                            "#fs11#\r\nสุดท้าย สนาม Flag Race จะเปลี่ยนทุกสัปดาห์วันจันทร์เวลา 00:00 นาฬิกา แต่ละสนามมีเกณฑ์คะแนนต่างกัน ศึกษาให้ดีแล้ววิ่งอย่างฉลาดล่ะ");
                                    self.say(
                                            "#fs11#\r\n#e[เกณฑ์คะแนนอันดับ Daylight Snowfield]#n\r\n\r\nต่ำกว่า 2 นาที 10 วินาที : #b#e1,000 คะแนน#n#k\r\n2 นาที 10 วินาที ~ ต่ำกว่า 2 นาที 15 วินาที : #b#e800 คะแนน#n#k\r\n2 นาที 15 วินาที ~ ต่ำกว่า 2 นาที 20 วินาที : #b#e650 คะแนน#n#k\r\n2 นาที 20 วินาที ~ ต่ำกว่า 2 นาที 25 วินาที : #b#e550 คะแนน#n#k\r\n2 นาที 25 วินาที ~ ต่ำกว่า 2 นาที 30 วินาที : #b#e450 คะแนน#n#k\r\n2 นาที 30 วินาที ~ ต่ำกว่า 2 นาที 35 วินาที : #b#e400 คะแนน#n#k\r\n2 นาที 35 วินาที ~ ต่ำกว่า 2 นาที 40 วินาที : #b#e350 คะแนน#n#k\r\n2 นาที 40 วินาที ~ ต่ำกว่า 2 นาที 50 วินาที : #b#e300 คะแนน#n#k\r\n2 นาที 50 วินาที ~ ต่ำกว่า 3 นาที 00 วินาที : #b#e250 คะแนน#n#k\r\n3 นาที 00 วินาที ~ ต่ำกว่า 3 นาที 10 วินาที : #b#e200 คะแนน#n#k\r\n3 นาที 10 วินาที ขึ้นไป : #b#e100 คะแนน#n#k");
                                    self.say(
                                            "#fs11#\r\n#e[เกณฑ์คะแนนอันดับ Sunset Snowfield]#n\r\n\r\nต่ำกว่า 1 นาที 50 วินาที : #b#e1,000 คะแนน#n#k\r\n1 นาที 50 วินาที ~ ต่ำกว่า 1 นาที 55 วินาที : #b#e800 คะแนน#n#k\r\n1 นาที 55 วินาที ~ ต่ำกว่า 2 นาที 00 วินาที : #b#e650 คะแนน#n#k\r\n2 นาที 00 วินาที ~ ต่ำกว่า 2 นาที 05 วินาที : #b#e550 คะแนน#n#k\r\n2 นาที 05 วินาที ~ ต่ำกว่า 2 นาที 10 วินาที : #b#e450 คะแนน#n#k\r\n2 นาที 10 วินาที ~ ต่ำกว่า 2 นาที 15 วินาที : #b#e400 คะแนน#n#k\r\n2 นาที 15 วินาที ~ ต่ำกว่า 2 นาที 20 วินาที : #b#e350 คะแนน#n#k\r\n2 นาที 20 วินาที ~ ต่ำกว่า 2 นาที 30 วินาที : #b#e300 คะแนน#n#k\r\n2 นาที 30 วินาที ~ ต่ำกว่า 2 นาที 40 วินาที : #b#e250 คะแนน#n#k\r\n2 นาที 40 วินาที ~ ต่ำกว่า 2 นาที 50 วินาที : #b#e200 คะแนน#n#k\r\n2 นาที 50 วินาที ขึ้นไป : #b#e100 คะแนน#n#k");
                                    self.say(
                                            "#fs11#\r\n#e[เกณฑ์คะแนนอันดับ Midnight Snowfield]#n\r\n\r\nต่ำกว่า 2 นาที 30 วินาที : #b#e1,000 คะแนน#n#k\r\n2 นาที 30 วินาที ~ ต่ำกว่า 2 นาที 35 วินาที : #b#e800 คะแนน#n#k\r\n2 นาที 35 วินาที ~ ต่ำกว่า 2 นาที 40 วินาที : #b#e650 คะแนน#n#k\r\n2 นาที 40 วินาที ~ ต่ำกว่า 2 นาที 45 วินาที : #b#e550 คะแนน#n#k\r\n2 นาที 45 วินาที ~ ต่ำกว่า 2 นาที 50 วินาที : #b#e450 คะแนน#n#k\r\n2 นาที 50 วินาที ~ ต่ำกว่า 2 นาที 55 วินาที : #b#e400 คะแนน#n#k\r\n2 นาที 55 วินาที ~ ต่ำกว่า 3 นาที 00 วินาที : #b#e350 คะแนน#n#k\r\n3 นาที 00 วินาที ~ ต่ำกว่า 3 นาที 10 วินาที : #b#e300 คะแนน#n#k\r\n3 นาที 10 วินาที ~ ต่ำกว่า 3 นาที 20 วินาที : #b#e250 คะแนน#n#k\r\n3 นาที 20 วินาที ~ ต่ำกว่า 3 นาที 30 วินาที : #b#e200 คะแนน#n#k\r\n3 นาที 30 วินาที ขึ้นไป : #b#e100 คะแนน#n#k");
                                    break;
                                case 3: // ไม่มีอะไร
                                    self.sayOk("#fs11#\r\nงั้นหรอ จะทำหรือไม่ทำอะไรก็เรื่องของเจ้านะ");
                                    break;
                            }
                            break;
                        }
                        case 0:
                            self.say(
                                    "#fs11#อยากออกไปจากที่นี่หรอ? ก่อนออกไปเช็คอันดับที่เครื่องเกมสุดยอดด้านข้างได้นะ ลองดูสักหน่อยก็ดี");
                            if (1 == self.askAccept("#fs11#จะให้ส่งกลับไปแผนที่ก่อนที่จะเข้ามาที่นี่ไหม?")) {
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
                int v = self.askMenu(
                        "#fs11#\r\nที่นี่คือที่ที่ผู้ท้าชิงรอเล่น Flag Race อย่าใจร้อนไปเลยน่า\r\n#b#L0#ช่วยอธิบายเกี่ยวกับ Flag Race หน่อยครับ#l\r\n#b#L1#อยากออกไปจากที่นี่ครับ#l");
                switch (v) {
                    case 0: { // ช่วยอธิบายเกี่ยวกับ Flag Race หน่อยครับ
                        int vv = self.askMenu(
                                "#fs11#คิคิคิ, อยากรู้อะไรเกี่ยวกับ Flag Race หรอ?\r\n\r\n#b#L0#Flag Race คืออะไร?#l\r\n#b#L1#Flag Race ดีตรงไหน?#l\r\n#L4#อยากรู้เกณฑ์คะแนนอันดับ Flag Race#l\r\n#L3#เปล่าครับ ไม่มีอะไร#l");
                        switch (vv) {
                            case 0: // Flag Race คืออะไร?
                                self.say("#fs11#\r\nอยากรู้เกี่ยวกับ Flag Race ผลงานสร้างอันยิ่งใหญ่ของฉันหรอ?");
                                self.say(
                                        "#fs11#\r\nกติกาง่ายมาก จัดขึ้น #rทุกชั่วโมง นาทีที่ 30#k, เฉพาะ #rสมาชิกกิลด์ที่มีคะแนนภารกิจรายสัปดาห์ 1 คะแนนขึ้นไป#k เท่านั้นที่เข้าร่วมได้");
                                self.say(
                                        "#fs11#\r\nแค่วิ่งไปให้ถึงเส้นชัยเร็วๆ ก็พอ แน่นอนว่าอาจจะมีอุปสรรคนิด---หน่อยนะ");
                                self.say(
                                        "#fs11#\r\nใน Flag Race ไม่ได้มีแค่การเดินและกระโดดเพื่อแข่งขันอย่างยุติธรรมหรอกนะ แต่มี Flag Skill ให้ใช้ได้เฉพาะในสนามด้วย");
                                self.say(
                                        "#fs11#\r\nลองผสมผสาน Flag Skill กับกลไกในแผนที่ดูสิ จะไปในเส้นทางที่สถานการณ์ปกติไปไม่ได้ หรือผ่านสนามได้เร็วขึ้นก็ได้นะ");
                                self.say(
                                        "#fs11#\r\nอ้อ แล้วก็ตอนเข้า Race สถานะแปลงร่างทั้งหมดจะถูกยกเลิก และต่อให้ใช้น้ำยาแปลงร่างระหว่างแข่งก็ไม่แสดงผล ระวังจะเสียน้ำยาฟรีล่ะ");
                                self.say(
                                        "#fs11#\r\nเลเวล 101 ขึ้นไปเท่านั้นถึงจะเข้าร่วมได้ ตรวจสอบให้ดีล่ะ แต่ถ้าเลเวลเกิน 101 มานานแล้วก็ช่างเถอะ...");
                                self.say("#fs11#\r\nงั้นก็ พยายามเข้านะ");
                                break;
                            case 1: // Flag Race ดีตรงไหน?
                                self.say(
                                        "#fs11#\r\nดีตรงไหนงั้นหรอ? มีทางก็ต้องวิ่ง มีอุปสรรคก็ต้องข้าม จำเป็นต้องมีข้อดีด้วยหรือไง?");
                                self.say(
                                        "#fs11#\r\n...ล้อเล่นน่า SP ที่เอาไปอัป Noblesse Skill ของกิลด์ได้ จะได้รับตามอันดับกิลด์ใน Flag Race ประจำสัปดาห์ไงล่ะ");
                                self.say(
                                        "#fs11##b#eอันดับ 1 : SP 25 แต้ม\r\nอันดับ 2 : SP 22 แต้ม\r\nอันดับ 3 : SP 20 แต้ม\r\nอันดับ 4 ~ 10 : SP 18 แต้ม\r\nท็อป 10% : SP 16 แต้ม\r\nท็อป 20% : SP 14 แต้ม\r\nท็อป 30% : SP 12 แต้ม\r\nท็อป 40% : SP 10 แต้ม\r\nท็อป 50% : SP 9 แต้ม\r\nท็อป 60% : SP 8 แต้ม\r\nท็อป 70% : SP 7 แต้ม\r\nท็อป 80% : SP 6 แต้ม\r\n1,000 คะแนนขึ้นไป : SP 5 แต้ม#n#k\r\n\r\n#r#e※ แต่ว่า, ถ้าทำได้ไม่ถึง 1,000 คะแนน ต่อให้ติดอันดับก็ไม่ได้รับรางวัลนะ#k#n");
                                self.say(
                                        "#fs11#\r\nอันดับกิลด์ Flag Race คิดจากผลรวมคะแนน Flag Race ของสมาชิกแต่ละคน กิลด์ที่มีคนวิ่งเก่งๆ เยอะก็ยิ่งได้อันดับสูงนะ");
                                self.say(
                                        "#fs11#\r\nคะแนน Flag Race ของเจ้าจะตัดสินจากสถิติที่ดีที่สุดในสัปดาห์ ถ้าไม่พอใจสถิติตอนนี้ก็มาท้าทายใหม่ได้ตลอด แน่นอนว่าต้องเป็นตอนที่เปิดนะ");
                                self.say(
                                        "#fs11#\r\nอ้อ ถ้าออกจากกิลด์หรือถูกไล่ออก สถิติสูงสุดรายสัปดาห์จะถูกรีเซต คะแนนรวมกิลด์ก็จะถูกหักออกไปด้วย ระวังให้ดีล่ะ");
                                self.say(
                                        "#fs11#\r\nแล้วก็อันดับไม่ได้อยู่ตลอดไป ทุกวันจันทร์เวลา 00:00 นาฬิกา อันดับและ Noblesse Skill จะถูกรีเซตทั้งหมด แล้วจะมอบ SP Noblesse Skill ให้ตามอันดับที่สรุปได้ตอนนั้น อย่าผิดหวังไปล่ะถ้าสัปดาห์นี้ล้มเหลว");
                                break;
                            case 4: // เกณฑ์คะแนน
                                self.say(
                                        "#fs11#\r\nคะแนนอันดับที่หาได้จาก Flag Race ตัดสินจากเวลาที่ใช้ในการวิ่งครบ 3 รอบ ไม่เกี่ยวกับคนอื่น แค่วิ่งให้ดียังไงก็ได้คะแนนสูง");
                                self.say(
                                        "#fs11#\r\nเกณฑ์คะแนนอันดับตัดสินจากสถิติสูงสุดของเจ้าในสัปดาห์นั้น มาท้าทายใหม่เพื่อทำสถิติให้สูงขึ้นได้นะถ้ายังไม่พอใจ");
                                self.say(
                                        "#fs11#\r\nสุดท้าย สนาม Flag Race จะเปลี่ยนทุกสัปดาห์วันจันทร์เวลา 00:00 นาฬิกา แต่ละสนามมีเกณฑ์คะแนนต่างกัน ศึกษาให้ดีแล้ววิ่งอย่างฉลาดล่ะ");
                                self.say(
                                        "#fs11#\r\n#e[เกณฑ์คะแนนอันดับ Daylight Snowfield]#n\r\n\r\nต่ำกว่า 2 นาที 10 วินาที : #b#e1,000 คะแนน#n#k\r\n2 นาที 10 วินาที ~ ต่ำกว่า 2 นาที 15 วินาที : #b#e800 คะแนน#n#k\r\n2 นาที 15 วินาที ~ ต่ำกว่า 2 นาที 20 วินาที : #b#e650 คะแนน#n#k\r\n2 นาที 20 วินาที ~ ต่ำกว่า 2 นาที 25 วินาที : #b#e550 คะแนน#n#k\r\n2 นาที 25 วินาที ~ ต่ำกว่า 2 นาที 30 วินาที : #b#e450 คะแนน#n#k\r\n2 นาที 30 วินาที ~ ต่ำกว่า 2 นาที 35 วินาที : #b#e400 คะแนน#n#k\r\n2 นาที 35 วินาที ~ ต่ำกว่า 2 นาที 40 วินาที : #b#e350 คะแนน#n#k\r\n2 นาที 40 วินาที ~ ต่ำกว่า 2 นาที 50 วินาที : #b#e300 คะแนน#n#k\r\n2 นาที 50 วินาที ~ ต่ำกว่า 3 นาที 00 วินาที : #b#e250 คะแนน#n#k\r\n3 นาที 00 วินาที ~ ต่ำกว่า 3 นาที 10 วินาที : #b#e200 คะแนน#n#k\r\n3 นาที 10 วินาที ขึ้นไป : #b#e100 คะแนน#n#k");
                                self.say(
                                        "#fs11#\r\n#e[เกณฑ์คะแนนอันดับ Sunset Snowfield]#n\r\n\r\nต่ำกว่า 1 นาที 50 วินาที : #b#e1,000 คะแนน#n#k\r\n1 นาที 50 วินาที ~ ต่ำกว่า 1 นาที 55 วินาที : #b#e800 คะแนน#n#k\r\n1 นาที 55 วินาที ~ ต่ำกว่า 2 นาที 00 วินาที : #b#e650 คะแนน#n#k\r\n2 นาที 00 วินาที ~ ต่ำกว่า 2 นาที 05 วินาที : #b#e550 คะแนน#n#k\r\n2 นาที 05 วินาที ~ ต่ำกว่า 2 นาที 10 วินาที : #b#e450 คะแนน#n#k\r\n2 นาที 10 วินาที ~ ต่ำกว่า 2 นาที 15 วินาที : #b#e400 คะแนน#n#k\r\n2 นาที 15 วินาที ~ ต่ำกว่า 2 นาที 20 วินาที : #b#e350 คะแนน#n#k\r\n2 นาที 20 วินาที ~ ต่ำกว่า 2 นาที 30 วินาที : #b#e300 คะแนน#n#k\r\n2 นาที 30 วินาที ~ ต่ำกว่า 2 นาที 40 วินาที : #b#e250 คะแนน#n#k\r\n2 นาที 40 วินาที ~ ต่ำกว่า 2 นาที 50 วินาที : #b#e200 คะแนน#n#k\r\n2 นาที 50 วินาที ขึ้นไป : #b#e100 คะแนน#n#k");
                                self.say(
                                        "#fs11#\r\n#e[เกณฑ์คะแนนอันดับ Midnight Snowfield]#n\r\n\r\nต่ำกว่า 2 นาที 30 วินาที : #b#e1,000 คะแนน#n#k\r\n2 นาที 30 วินาที ~ ต่ำกว่า 2 นาที 35 วินาที : #b#e800 คะแนน#n#k\r\n2 นาที 35 วินาที ~ ต่ำกว่า 2 นาที 40 วินาที : #b#e650 คะแนน#n#k\r\n2 นาที 40 วินาที ~ ต่ำกว่า 2 นาที 45 วินาที : #b#e550 คะแนน#n#k\r\n2 นาที 45 วินาที ~ ต่ำกว่า 2 นาที 50 วินาที : #b#e450 คะแนน#n#k\r\n2 นาที 50 วินาที ~ ต่ำกว่า 2 นาที 55 วินาที : #b#e400 คะแนน#n#k\r\n2 นาที 55 วินาที ~ ต่ำกว่า 3 นาที 00 วินาที : #b#e350 คะแนน#n#k\r\n3 นาที 00 วินาที ~ ต่ำกว่า 3 นาที 10 วินาที : #b#e300 คะแนน#n#k\r\n3 นาที 10 วินาที ~ ต่ำกว่า 3 นาที 20 วินาที : #b#e250 คะแนน#n#k\r\n3 นาที 20 วินาที ~ ต่ำกว่า 3 นาที 30 วินาที : #b#e200 คะแนน#n#k\r\n3 นาที 30 วินาที ขึ้นไป : #b#e100 คะแนน#n#k");
                                break;
                            case 3: // ไม่มีอะไร
                                self.sayOk("#fs11#\r\nงั้นหรอ จะทำหรือไม่ทำอะไรก็เรื่องของเจ้านะ");
                                break;
                        }
                        break;
                    }
                    case 1:
                        if (1 == self.askYesNo("#fs11#อยากออกไปจากที่นี่จริงๆ หรอ?")) {
                            registerTransferField(getPlayer().getMapId() + 1);
                        }
                        break;
                }
                break;
            }
            default: {
                int v = self.askMenu(
                        "#fs11#\r\nที่นี่คือห้องรอฝึกซ้อม Flag Race~ มีอะไรสงสัยถามได้นะ\r\n#b#L1#อยากไปสนามฝึกซ้อม Flag Race ครับ#l\r\n#b#L0#ช่วยอธิบายเกี่ยวกับ Flag Race หน่อยครับ#l");
                switch (v) {
                    case 1: { // อยากไปสนามฝึกซ้อม Flag Race ครับ
                        int vv = self.askMenu(
                                "#fs11#\r\nอยากฝึกสนามไหนล่ะ? ไม่ง่ายสักสนามหรอกนะจะบอกให้\r\n#b#L1#สนามซ้อม Daylight Snowfield#l\r\n#b#L2#สนามซ้อม Sunset Snowfield#l\r\n#b#L3#สนามซ้อม Midnight Snowfield#l");
                        switch (vv) {
                            case 1: // สนามซ้อม Daylight Snowfield
                                if (1 == self.askAccept(
                                        "#fs11##bสนามซ้อม Daylight Snowfield#k ไหม?\r\n\r\n#r※ หัวหน้าปาร์ตี้เท่านั้นที่กดเริ่มได้ และสมาชิกปาร์ตี้ทุกคนต้องอยู่ในห้องรอฝึกซ้อมถึงจะเข้าได้")) {
                                    FieldSet fieldSet = fieldSet("FlagRaceN1Enter");
                                    if (fieldSet == null) {
                                        self.sayOk("#fs11#ตอนนี้ไม่สามารถเข้า Flag Race (Daylight Snowfield) ได้");
                                        return;
                                    }
                                    int enter = ((FlagRaceN1Enter) fieldSet).enter(target.getId(), true, 0);
                                    if (enter == -1)
                                        self.say("#fs11#ไม่สามารถเข้าได้ด้วยสาเหตุที่ไม่ทราบ กรุณาลองใหม่ภายหลัง");
                                    else if (enter == 1)
                                        self.say("#fs11#ต้องมีปาร์ตี้ 1~6 คน ถึงจะเริ่มได้");
                                    else if (enter == 2)
                                        self.say(
                                                "#fs11#...เจ้าไม่ใช่หัวหน้าปาร์ตี้นี่? หัวหน้าปาร์ตี้เท่านั้นที่สมัครเข้าได้");
                                    else if (enter == 3)
                                        self.say("#fs11#ต้องมีสมาชิกปาร์ตี้อย่างน้อย " + fieldSet.minMember
                                                + " คนขึ้นไป ถึงจะเริ่มเควสต์ได้");
                                    else if (enter == 4)
                                        self.say("#fs11#เลเวลของสมาชิกในปาร์ตี้ต้องมีเลเวล " + fieldSet.minLv
                                                + " ขึ้นไป");
                                    else if (enter == 5)
                                        self.say("#fs11#สมาชิกปาร์ตี้ต้องมารวมตัวกันครบทุกคนถึงจะเริ่มได้");
                                    else if (enter == 6)
                                        self.say("#fs11#มีปาร์ตี้อื่นกำลังท้าทายเควสต์อยู่จ้า");
                                } else {
                                    self.sayOk("#fs11#ถ้าจำเป็นต้องฝึกซ้อมเมื่อไหร่ก็มาบอกนะ...");
                                }
                                break;
                            case 2: // สนามซ้อม Sunset Snowfield
                                if (1 == self.askAccept(
                                        "#fs11##bสนามซ้อม Sunset Snowfield#k ไหม?\r\n\r\n#r※ หัวหน้าปาร์ตี้เท่านั้นที่กดเริ่มได้ และสมาชิกปาร์ตี้ทุกคนต้องอยู่ในห้องรอฝึกซ้อมถึงจะเข้าได้")) {
                                    // 942003200
                                    FieldSet fieldSet = fieldSet("FlagRaceN2Enter");
                                    if (fieldSet == null) {
                                        self.sayOk("#fs11#ตอนนี้ไม่สามารถเข้า Flag Race (Sunset Snowfield) ได้");
                                        return;
                                    }
                                    int enter = ((FlagRaceN2Enter) fieldSet).enter(target.getId(), true, 0);
                                    if (enter == -1)
                                        self.say("#fs11#ไม่สามารถเข้าได้ด้วยสาเหตุที่ไม่ทราบ กรุณาลองใหม่ภายหลัง");
                                    else if (enter == 1)
                                        self.say("#fs11#ต้องมีปาร์ตี้ 1~6 คน ถึงจะเริ่มได้");
                                    else if (enter == 2)
                                        self.say(
                                                "#fs11#...เจ้าไม่ใช่หัวหน้าปาร์ตี้นี่? หัวหน้าปาร์ตี้เท่านั้นที่สมัครเข้าได้");
                                    else if (enter == 3)
                                        self.say("#fs11#ต้องมีสมาชิกปาร์ตี้อย่างน้อย " + fieldSet.minMember
                                                + " คนขึ้นไป ถึงจะเริ่มเควสต์ได้");
                                    else if (enter == 4)
                                        self.say("#fs11#เลเวลของสมาชิกในปาร์ตี้ต้องมีเลเวล " + fieldSet.minLv
                                                + " ขึ้นไป");
                                    else if (enter == 5)
                                        self.say("#fs11#สมาชิกปาร์ตี้ต้องมารวมตัวกันครบทุกคนถึงจะเริ่มได้");
                                    else if (enter == 6)
                                        self.say("#fs11#มีปาร์ตี้อื่นกำลังท้าทายเควสต์อยู่จ้า");
                                } else {
                                    self.sayOk("#fs11#ถ้าจำเป็นต้องฝึกซ้อมเมื่อไหร่ก็มาบอกนะ...");
                                }
                                break;
                            case 3: // สนามซ้อม Midnight Snowfield
                                if (1 == self.askAccept(
                                        "#fs11##bสนามซ้อม Midnight Snowfield#k ไหม?\r\n\r\n#r※ หัวหน้าปาร์ตี้เท่านั้นที่กดเริ่มได้ และสมาชิกปาร์ตี้ทุกคนต้องอยู่ในห้องรอฝึกซ้อมถึงจะเข้าได้")) {
                                    // 942003300
                                    FieldSet fieldSet = fieldSet("FlagRaceN3Enter");
                                    if (fieldSet == null) {
                                        self.sayOk("#fs11#ตอนนี้ไม่สามารถเข้า Flag Race (Midnight Snowfield) ได้");
                                        return;
                                    }
                                    int enter = ((FlagRaceN3Enter) fieldSet).enter(target.getId(), true, 0);
                                    if (enter == -1)
                                        self.say("#fs11#ไม่สามารถเข้าได้ด้วยสาเหตุที่ไม่ทราบ กรุณาลองใหม่ภายหลัง");
                                    else if (enter == 1)
                                        self.say("#fs11#ต้องมีปาร์ตี้ 1~6 คน ถึงจะเริ่มได้");
                                    else if (enter == 2)
                                        self.say(
                                                "#fs11#...เจ้าไม่ใช่หัวหน้าปาร์ตี้นี่? หัวหน้าปาร์ตี้เท่านั้นที่สมัครเข้าได้");
                                    else if (enter == 3)
                                        self.say("#fs11#ต้องมีสมาชิกปาร์ตี้อย่างน้อย " + fieldSet.minMember
                                                + " คนขึ้นไป ถึงจะเริ่มเควสต์ได้");
                                    else if (enter == 4)
                                        self.say("#fs11#เลเวลของสมาชิกในปาร์ตี้ต้องมีเลเวล " + fieldSet.minLv
                                                + " ขึ้นไป");
                                    else if (enter == 5)
                                        self.say("#fs11#สมาชิกปาร์ตี้ต้องมารวมตัวกันครบทุกคนถึงจะเริ่มได้");
                                    else if (enter == 6)
                                        self.say("#fs11#มีปาร์ตี้อื่นกำลังท้าทายเควสต์อยู่จ้า");

                                } else {
                                    self.sayOk("#fs11#ถ้าจำเป็นต้องฝึกซ้อมเมื่อไหร่ก็มาบอกนะ...");
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
        if (1 == self.askYesNo("#fs11#\r\nถอดใจแล้วงั้นหรอ? จะให้ออกไปข้างนอกไหม?",
                ScriptMessageFlag.NpcReplacedByNpc)) {
            registerTransferField(942003000); // Daylight Exit Map
            // 942003001 Sunset Exit Map
            // 942003002 Midnight Exit Map
        }
    }

    public void flag_Exit_Map_NPC() {
        if (1 == self.askAccept("#fs11#คิคิคิ, สนามซ้อมเป็นยังไงบ้าง? จะให้ส่งกลับห้องรอฝึกซ้อมไหม?")) {
            registerTransferField(942003050);
        } else {
            self.sayOk("#fs11#เข้าใจแล้ว, ถ้าอยากกลับเมื่อไหร่ก็บอกข้านะ");
        }
    }

    @Script
    public void flag_pexit() {
        registerTransferField(100000000);
    }

    @Script
    public void flag_Start() {
        switch (getPlayer().getMapId()) {
            case 942000500: { // 실전แผนที่
                objects.fields.fieldset.instance.FlagRace fieldSet = (objects.fields.fieldset.instance.FlagRace) getPlayer()
                        .getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "ตอนนี้ไม่สามารถใช้พอร์ทัลได้");
                    return;
                }
                if (fieldSet.isStartGame()) {
                    Field field = getPlayer().getMap();
                    field.removeDropsIndividual(getPlayer());
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(94, 2297), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(1892, 1225), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(812, 1167), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(420, 1008), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-2023, 1244), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-555, 1273), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-600, 1043), false);
                    userLocalTeleport(16);
                }
                break;
            }
            case 942003100: {
                FlagRaceN1 fieldSet = (FlagRaceN1) getPlayer().getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "ตอนนี้ไม่สามารถใช้พอร์ทัลได้");
                    return;
                }
                if (fieldSet.isStartGame()) {
                    Field field = getPlayer().getMap();
                    field.removeDropsIndividual(getPlayer());
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(94, 2297), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(1892, 1225), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(812, 1167), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(420, 1008), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-2023, 1244), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-555, 1273), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-600, 1043), false);
                    userLocalTeleport(16);
                }
                break;
            }

            case 942001500: {
                objects.fields.fieldset.instance.FlagRace fieldSet = (objects.fields.fieldset.instance.FlagRace) getPlayer()
                        .getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "ตอนนี้ไม่สามารถใช้พอร์ทัลได้");
                    return;
                }
                if (fieldSet.isStartGame()) {
                    Field field = getPlayer().getMap();
                    field.removeDropsIndividual(getPlayer());
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-803, 1480), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(270, 1525), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(632, 1525), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(2007, 1103), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(1357, 1103), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(725, 975), false);
                    userLocalTeleport(1);
                }
                break;
            }
            case 942003200: {
                FlagRaceN2 fieldSet = (FlagRaceN2) getPlayer().getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "ตอนนี้ไม่สามารถใช้พอร์ทัลได้");
                    return;
                }
                if (fieldSet.isStartGame()) {
                    Field field = getPlayer().getMap();
                    field.removeDropsIndividual(getPlayer());
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-803, 1480), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(270, 1525), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(632, 1525), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(2007, 1103), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(1357, 1103), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(725, 975), false);
                    userLocalTeleport(1);
                }
                break;
            }
            // 932200300
            case 942002500: {
                objects.fields.fieldset.instance.FlagRace fieldSet = (objects.fields.fieldset.instance.FlagRace) getPlayer()
                        .getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "ตอนนี้ไม่สามารถใช้พอร์ทัลได้");
                    return;
                }
                if (fieldSet.isStartGame()) {
                    Field field = getPlayer().getMap();
                    field.removeDropsIndividual(getPlayer());
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(689, 2328), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-1075, 2322), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-110, 2660), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(2073, 1644), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(2073, 1284), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(808, 1339), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-2050, 1081), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-2065, 1345), false);
                    userLocalTeleport(18);
                }
                break;
            }
            case 942003300: {
                FlagRaceN3 fieldSet = (FlagRaceN3) getPlayer().getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "ตอนนี้ไม่สามารถใช้พอร์ทัลได้");
                    return;
                }
                if (fieldSet.isStartGame()) {
                    Field field = getPlayer().getMap();
                    field.removeDropsIndividual(getPlayer());
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(689, 2328), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-1075, 2322), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-110, 2660), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(2073, 1644), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(2073, 1284), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(808, 1339), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-2050, 1081), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-2065, 1345), false);
                    userLocalTeleport(18);
                }
                break;
            }
        }
    }

    @Script
    public void flag_goal() {
        switch (getPlayer().getMapId()) {
            case 942000500: { // 실전แผนที่
                objects.fields.fieldset.instance.FlagRace fieldSet = (objects.fields.fieldset.instance.FlagRace) getPlayer()
                        .getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "ตอนนี้ไม่สามารถใช้พอร์ทัลได้");
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
                        record = finish - start;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getPlayer().updateOneInfo(32581, "complete", "1");
                    getPlayer().updateOneInfo(32581, "record", String.valueOf(record));
                    getPlayer().getMap().broadcastMessage(
                            CWvsContext.getScriptProgressMessage(getPlayer().getName() + " วิ่งเข้าเส้นชัยสำเร็จแล้ว"));
                    getPlayer().send(CField.environmentChange("quest/party/clear", 19));
                    userLocalTeleport(18);
                } else {
                    getPlayer().getMap().broadcastMessage(CWvsContext.getScriptProgressMessage(
                            getPlayer().getName() + " ผ่านจุดเข้าเส้นชัยแล้ว เหลืออีก " + (3 - flagGoal) + " รอบ"));
                    userLocalTeleport(17);
                }
                break;
            }
            case 942003100: {
                FlagRaceN1 fieldSet = (FlagRaceN1) getPlayer().getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "ตอนนี้ไม่สามารถใช้พอร์ทัลได้");
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
                        record = finish - start;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getPlayer().updateOneInfo(32581, "complete", "1");
                    getPlayer().updateOneInfo(32581, "record", String.valueOf(record));
                    getPlayer().getMap().broadcastMessage(
                            CWvsContext.getScriptProgressMessage(getPlayer().getName() + " วิ่งเข้าเส้นชัยสำเร็จแล้ว"));
                    getPlayer().send(CField.environmentChange("quest/party/clear", 19));
                    userLocalTeleport(18);
                } else {
                    getPlayer().getMap().broadcastMessage(CWvsContext.getScriptProgressMessage(
                            getPlayer().getName() + " ผ่านจุดเข้าเส้นชัยแล้ว เหลืออีก " + (3 - flagGoal) + " รอบ"));
                    userLocalTeleport(17);
                }
                break;
            }
            case 942001500: {
                objects.fields.fieldset.instance.FlagRace fieldSet = (objects.fields.fieldset.instance.FlagRace) getPlayer()
                        .getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "ตอนนี้ไม่สามารถใช้พอร์ทัลได้");
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
                        record = finish - start;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getPlayer().updateOneInfo(32581, "complete", "1");
                    getPlayer().updateOneInfo(32581, "record", String.valueOf(record));
                    getPlayer().getMap().broadcastMessage(
                            CWvsContext.getScriptProgressMessage(getPlayer().getName() + " วิ่งเข้าเส้นชัยสำเร็จแล้ว"));
                    getPlayer().send(CField.environmentChange("quest/party/clear", 19));
                    userLocalTeleport(5);
                } else {
                    Field field = getPlayer().getMap();
                    field.removeDropsIndividual(getPlayer());
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-803, 1480), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(270, 1525), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(632, 1525), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(2007, 1103), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(1357, 1103), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(725, 975), false);
                    getPlayer().getMap().broadcastMessage(CWvsContext.getScriptProgressMessage(
                            getPlayer().getName() + " ผ่านจุดเข้าเส้นชัยแล้ว เหลืออีก " + (3 - flagGoal) + " รอบ"));
                    userLocalTeleport(1);
                }
                break;
            }

            case 942003200: {
                FlagRaceN2 fieldSet = (FlagRaceN2) getPlayer().getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "ตอนนี้ไม่สามารถใช้พอร์ทัลได้");
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
                        record = finish - start;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getPlayer().updateOneInfo(32581, "complete", "1");
                    getPlayer().updateOneInfo(32581, "record", String.valueOf(record));
                    getPlayer().getMap().broadcastMessage(
                            CWvsContext.getScriptProgressMessage(getPlayer().getName() + " วิ่งเข้าเส้นชัยสำเร็จแล้ว"));
                    getPlayer().send(CField.environmentChange("quest/party/clear", 19));
                    userLocalTeleport(5);
                } else {
                    Field field = getPlayer().getMap();
                    field.removeDropsIndividual(getPlayer());
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(-803, 1480), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(270, 1525), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(632, 1525), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(2007, 1103), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(1357, 1103), false);
                    field.spawnIndividualItemDropPos(getPlayer(), getPlayer(),
                            new Item(2633626, (byte) 0, (short) 1, (byte) 0), new Point(725, 975), false);
                    getPlayer().getMap().broadcastMessage(CWvsContext.getScriptProgressMessage(
                            getPlayer().getName() + " ผ่านจุดเข้าเส้นชัยแล้ว เหลืออีก " + (3 - flagGoal) + " รอบ"));
                    userLocalTeleport(1);
                }
                break;
            }
            case 942002500: {
                objects.fields.fieldset.instance.FlagRace fieldSet = (objects.fields.fieldset.instance.FlagRace) getPlayer()
                        .getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "ตอนนี้ไม่สามารถใช้พอร์ทัลได้");
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
                        record = finish - start;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getPlayer().updateOneInfo(32581, "complete", "1");
                    getPlayer().updateOneInfo(32581, "record", String.valueOf(record));
                    getPlayer().getMap().broadcastMessage(
                            CWvsContext.getScriptProgressMessage(getPlayer().getName() + " วิ่งเข้าเส้นชัยสำเร็จแล้ว"));
                    getPlayer().send(CField.environmentChange("quest/party/clear", 19));
                    userLocalTeleport(3);
                } else {
                    getPlayer().getMap().broadcastMessage(CWvsContext.getScriptProgressMessage(
                            getPlayer().getName() + " ผ่านจุดเข้าเส้นชัยแล้ว เหลืออีก " + (3 - flagGoal) + " รอบ"));
                    userLocalTeleport(5);
                }
                break;
            }
            case 942003300: {
                FlagRaceN3 fieldSet = (FlagRaceN3) getPlayer().getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "ตอนนี้ไม่สามารถใช้พอร์ทัลได้");
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
                        record = finish - start;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getPlayer().updateOneInfo(32581, "complete", "1");
                    getPlayer().updateOneInfo(32581, "record", String.valueOf(record));
                    getPlayer().getMap().broadcastMessage(
                            CWvsContext.getScriptProgressMessage(getPlayer().getName() + " วิ่งเข้าเส้นชัยสำเร็จแล้ว"));
                    getPlayer().send(CField.environmentChange("quest/party/clear", 19));
                    userLocalTeleport(3);
                } else {
                    getPlayer().getMap().broadcastMessage(CWvsContext.getScriptProgressMessage(
                            getPlayer().getName() + " ผ่านจุดเข้าเส้นชัยแล้ว เหลืออีก " + (3 - flagGoal) + " รอบ"));
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
                objects.fields.fieldset.instance.FlagRace fieldSet = (objects.fields.fieldset.instance.FlagRace) getPlayer()
                        .getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "ไม่สามารถใช้พอร์ทัลได้");
                    return;
                }
                int flagGoal = fieldSet.getFlagGoalNumber(getPlayer().getName());
                if (flagGoal == 3) {
                    String mapName = "Daylight Snowfield";
                    if (getPlayer().getMapId() == 932200200) {
                        mapName = "Sunset Snowfield";
                    } else if (getPlayer().getMapId() == 932200300) {
                        mapName = "Midnight Snowfield";
                    }
                    self.say("#b" + mapName + "#k วิ่งเข้าเส้นชัยสำเร็จสินะ! ทำได้ดีกว่าที่คิดนี่?",
                            ScriptMessageFlag.NpcReplacedByNpc);
                    if (1 == self.askYesNo(
                            "เดี๋ยวข้าจะพาออกไปข้างนอกเอง แน่นอนว่าถ้าอยากดูเพื่อนที่กำลังวิ่งอยู่ต่อก็ได้นะ คิคิคิ",
                            ScriptMessageFlag.NpcReplacedByNpc)) {
                        int returnMap = 942003000 + ((getPlayer().getMapId() - 942000500) / 1000);
                        registerTransferField(returnMap);
                    }
                }
                break;
            }
            case 942003100: {
                FlagRaceN1 fieldSet = (FlagRaceN1) getPlayer().getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "ไม่สามารถใช้พอร์ทัลได้");
                    return;
                }
                int flagGoal = fieldSet.getFlagGoalNumber(getPlayer().getName());
                if (flagGoal == 3) {
                    self.say("#bDaylight Snowfield#k วิ่งเข้าเส้นชัยสำเร็จสินะ! ทำได้ดีกว่าที่คิดนี่?",
                            ScriptMessageFlag.NpcReplacedByNpc);
                    if (1 == self.askYesNo(
                            "เดี๋ยวข้าจะพาออกไปข้างนอกเอง แน่นอนว่าถ้าอยากดูเพื่อนที่กำลังวิ่งอยู่ต่อก็ได้นะ คิคิคิ",
                            ScriptMessageFlag.NpcReplacedByNpc)) {
                        registerTransferField(942003000); // 한낮 ออกแผนที่
                    }
                }
                break;
            }
            case 942003200: {
                FlagRaceN2 fieldSet = (FlagRaceN2) getPlayer().getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "ไม่สามารถใช้พอร์ทัลได้");
                    return;
                }
                int flagGoal = fieldSet.getFlagGoalNumber(getPlayer().getName());
                if (flagGoal == 3) {
                    self.say("#bSunset Snowfield#k วิ่งเข้าเส้นชัยสำเร็จสินะ! ทำได้ดีกว่าที่คิดนี่?",
                            ScriptMessageFlag.NpcReplacedByNpc);
                    if (1 == self.askYesNo(
                            "เดี๋ยวข้าจะพาออกไปข้างนอกเอง แน่นอนว่าถ้าอยากดูเพื่อนที่กำลังวิ่งอยู่ต่อก็ได้นะ คิคิคิ",
                            ScriptMessageFlag.NpcReplacedByNpc)) {
                        registerTransferField(942003001); // 석양 ออกแผนที่
                    }
                }
                break;
            }
            case 942003300: {
                FlagRaceN3 fieldSet = (FlagRaceN3) getPlayer().getMap().getFieldSetInstance();
                if (fieldSet == null) {
                    getPlayer().dropMessage(5, "ไม่สามารถใช้พอร์ทัลได้");
                    return;
                }
                int flagGoal = fieldSet.getFlagGoalNumber(getPlayer().getName());
                if (flagGoal == 3) {
                    self.say("#bMidnight Snowfield#k วิ่งเข้าเส้นชัยสำเร็จสินะ! ทำได้ดีกว่าที่คิดนี่?",
                            ScriptMessageFlag.NpcReplacedByNpc);
                    if (1 == self.askYesNo(
                            "เดี๋ยวข้าจะพาออกไปข้างนอกเอง แน่นอนว่าถ้าอยากดูเพื่อนที่กำลังวิ่งอยู่ต่อก็ได้นะ คิคิคิ",
                            ScriptMessageFlag.NpcReplacedByNpc)) {
                        registerTransferField(942003002); // 석양 ออกแผนที่
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
                        self.say(
                                "\r\n#e[ผลการฝึกซ้อม Flag Race]\r\n\r\n#n- สถิติ : #b" + recordToString
                                        + "#k\r\n- คะแนนอันดับที่คาดว่าจะได้รับ : #b" + score + " คะแนน",
                                ScriptMessageFlag.NpcReplacedByNpc);
                    } else {
                        if (getPlayer().getGuild() != null) {
                            getPlayer().getGuild().setPointLog(GuildContentsType.FLAG_RACE, getPlayer(), score);
                            int guildScore = getPlayer().getGuild().getPointLogByType(GuildContentsType.FLAG_RACE,
                                    getPlayer());
                            if (weeklyRecord > 0) {
                                if (record < weeklyRecord) {
                                    self.say("\r\n#e[ผลการแข่ง Flag Race]\r\n\r\n#n- สถิติ : #b" + recordToString
                                            + "(ทำลายสถิติใหม่!)#k\r\n- สถิติสูงสุดในสัปดาห์นี้ : #r" + recordToString
                                            + " #k\r\n- คะแนนอันดับกิลด์ที่ได้รับในสัปดาห์นี้ : " + guildScore
                                            + " คะแนน",
                                            ScriptMessageFlag.NpcReplacedByNpc);
                                    getPlayer().updateOneInfo(32581, "weeklyRecord", String.valueOf(record));
                                } else {
                                    self.say("\r\n#e[ผลการแข่ง Flag Race]\r\n\r\n#n- สถิติ : #b" + recordToString
                                            + "#k\r\n- คะแนนอันดับกิลด์ที่ได้รับในสัปดาห์นี้ : #b" + guildScore
                                            + " คะแนน",
                                            ScriptMessageFlag.NpcReplacedByNpc);
                                }
                            } else if (weeklyRecord == 0) {
                                self.say(
                                        "\r\n#e[ผลการแข่ง Flag Race]\r\n\r\n#n- สถิติ : #b" + recordToString
                                                + "#k\r\n- คะแนนอันดับกิลด์ที่ได้รับในสัปดาห์นี้ : #b" + guildScore
                                                + " คะแนน",
                                        ScriptMessageFlag.NpcReplacedByNpc);
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
                        self.say(
                                "\r\n#e[ผลการฝึกซ้อม Flag Race]\r\n\r\n#n- สถิติ : #b" + recordToString
                                        + "#k\r\n- คะแนนอันดับที่คาดว่าจะได้รับ : #b" + score + " คะแนน",
                                ScriptMessageFlag.NpcReplacedByNpc);
                    } else {
                        if (getPlayer().getGuild() != null) {
                            getPlayer().getGuild().setPointLog(GuildContentsType.FLAG_RACE, getPlayer(), score);
                            int guildScore = getPlayer().getGuild().getPointLogByType(GuildContentsType.FLAG_RACE,
                                    getPlayer());
                            if (weeklyRecord > 0) {
                                if (record < weeklyRecord) {
                                    self.say("\r\n#e[ผลการแข่ง Flag Race]\r\n\r\n#n- สถิติ : #b" + recordToString
                                            + "(ทำลายสถิติใหม่!)#k\r\n- สถิติสูงสุดในสัปดาห์นี้ : #r" + recordToString
                                            + " #k\r\n- คะแนนอันดับกิลด์ที่ได้รับในสัปดาห์นี้ : " + guildScore
                                            + " คะแนน",
                                            ScriptMessageFlag.NpcReplacedByNpc);
                                    getPlayer().updateOneInfo(32581, "weeklyRecord", String.valueOf(record));
                                } else {
                                    self.say("\r\n#e[ผลการแข่ง Flag Race]\r\n\r\n#n- สถิติ : #b" + recordToString
                                            + "#k\r\n- คะแนนอันดับกิลด์ที่ได้รับในสัปดาห์นี้ : #b" + guildScore
                                            + " คะแนน",
                                            ScriptMessageFlag.NpcReplacedByNpc);
                                }
                            } else if (weeklyRecord == 0) {
                                self.say(
                                        "\r\n#e[ผลการแข่ง Flag Race]\r\n\r\n#n- สถิติ : #b" + recordToString
                                                + "#k\r\n- คะแนนอันดับกิลด์ที่ได้รับในสัปดาห์นี้ : #b" + guildScore
                                                + " คะแนน",
                                        ScriptMessageFlag.NpcReplacedByNpc);
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
                        self.say(
                                "\r\n#e[ผลการฝึกซ้อม Flag Race]\r\n\r\n#n- สถิติ : #b" + recordToString
                                        + "#k\r\n- คะแนนอันดับที่คาดว่าจะได้รับ : #b" + score + " คะแนน",
                                ScriptMessageFlag.NpcReplacedByNpc);
                    } else {
                        if (getPlayer().getGuild() != null) {
                            getPlayer().getGuild().setPointLog(GuildContentsType.FLAG_RACE, getPlayer(), score);
                            int guildScore = getPlayer().getGuild().getPointLogByType(GuildContentsType.FLAG_RACE,
                                    getPlayer());
                            if (weeklyRecord > 0) {
                                if (record < weeklyRecord) {
                                    self.say("\r\n#e[ผลการแข่ง Flag Race]\r\n\r\n#n- สถิติ : #b" + recordToString
                                            + "(ทำลายสถิติใหม่!)#k\r\n- สถิติสูงสุดในสัปดาห์นี้ : #r" + recordToString
                                            + " #k\r\n- คะแนนอันดับกิลด์ที่ได้รับในสัปดาห์นี้ : " + guildScore
                                            + " คะแนน",
                                            ScriptMessageFlag.NpcReplacedByNpc);
                                    getPlayer().updateOneInfo(32581, "weeklyRecord", String.valueOf(record));
                                } else {
                                    self.say("\r\n#e[ผลการแข่ง Flag Race]\r\n\r\n#n- สถิติ : #b" + recordToString
                                            + "#k\r\n- คะแนนอันดับกิลด์ที่ได้รับในสัปดาห์นี้ : #b" + guildScore
                                            + " คะแนน",
                                            ScriptMessageFlag.NpcReplacedByNpc);
                                }
                            } else if (weeklyRecord == 0) {
                                self.say(
                                        "\r\n#e[ผลการแข่ง Flag Race]\r\n\r\n#n- สถิติ : #b" + recordToString
                                                + "#k\r\n- คะแนนอันดับกิลด์ที่ได้รับในสัปดาห์นี้ : #b" + guildScore
                                                + " คะแนน",
                                        ScriptMessageFlag.NpcReplacedByNpc);
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
