package script.Job;

import network.models.CWvsContext;
import objects.item.*;
import objects.utils.Randomizer;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

public class Zero extends ScriptEngineNPC {

        public void zero_inheritance() {
                int level = getPlayer().getLevel();
                int curLv = 0;
                int itemID = getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short) -10).getItemId();

                if (itemID == 1562007) {
                        curLv = 7;
                } else if (itemID == 1562008) {
                        curLv = 8;
                } else if (itemID == 1562009) {
                        curLv = 9;
                } else if (itemID == 1562010) {
                        curLv = 10;
                }

                if (curLv == 7 && level < 180) {
                        return;
                }
                if (curLv == 8 && level < 200) {
                        return;
                }

                if (curLv != 10) {
                        self.sayReplacedNpc("จากนี้ไป เพื่อทำให้พวกเราเติบโตขึ้น จำเป็นต้องใช้วัตถุดิบพิเศษนะ", 2400009,
                                        0,
                                        ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("#face1#ค่าตัวแพงจังนะ... พวกเราเนี่ย...", 2400010, 0,
                                        ScriptMessageFlag.Scenario);
                }

                int v0 = -1;
                if (getPlayer().getOneInfoQuestInteger(40981, "save_genesis") == 1) {
                        if (curLv != 10) {
                                v0 = self.askMenuReplacedNpc(
                                                "เอาล่ะ #h0# จะเอายังไงดี?\r\n\r\n#b#L3# รับ Genesis Lapis และ Lazuli#l\r\n#L999# ไว้ก่อน#l",
                                                2400009, 0, ScriptMessageFlag.NpcReplacedByNpc);
                        } else {
                                v0 = self.askMenuReplacedNpc(
                                                "เอาล่ะ #h0# จะเอายังไงดี?\r\n\r\n#b#L3# สวมใส่ Lapis และ Lazuli ระดับก่อนหน้า#l\r\n#L999# ไว้ก่อน#l",
                                                2400009, 0, ScriptMessageFlag.NpcReplacedByNpc);
                        }
                } else if (curLv == 7) {
                        if (level >= 200) {
                                v0 = self.askMenuReplacedNpc(
                                                "เอาล่ะ #h0# จะเอายังไงดี?\r\n\r\n#b#L0# พัฒนา Lapis และ Lazuli เป็นระดับ 8#l\r\n#L1# พัฒนา Lapis และ Lazuli เป็นระดับ 9#l\r\n#L999# ไว้ก่อน#l",
                                                2400009, 0, ScriptMessageFlag.Scenario);
                        } else {
                                v0 = self.askMenuReplacedNpc(
                                                "เอาล่ะ #h0# จะเอายังไงดี?\r\n\r\n#b#L0# พัฒนา Lapis และ Lazuli เป็นระดับ 8#l\r\n#L999# ไว้ก่อน#l",
                                                2400009, 0, ScriptMessageFlag.Scenario);
                        }
                } else if (curLv == 8) {
                        v0 = self.askMenuReplacedNpc(
                                        "เอาล่ะ #h0# จะเอายังไงดี?\r\n\r\n#b#L1# พัฒนา Lapis และ Lazuli เป็นระดับ 9#l\r\n#L999# ไว้ก่อน#l",
                                        2400009, 0, ScriptMessageFlag.Scenario);
                } else if (curLv == 9) {
                        v0 = self.askMenuReplacedNpc(
                                        "เอาล่ะ #h0# จะเอายังไงดี?\r\n\r\n#b#L2# พัฒนา Lapis และ Lazuli เป็นระดับ 10#l\r\n#L999# ไว้ก่อน#l",
                                        2400009, 0, ScriptMessageFlag.Scenario);
                } else if (curLv == 10) {
                        v0 = self.askMenuReplacedNpc(
                                        "เอาล่ะ #h0# จะเอายังไงดี?\r\n\r\n#b#L3# สวมใส่ Lapis และ Lazuli ระดับก่อนหน้า#l\r\n#L999# ไว้ก่อน#l",
                                        2400009, 0, ScriptMessageFlag.NpcReplacedByNpc);
                }

                if (v0 == 0) {
                        self.sayReplacedNpc("เพื่อที่จะเติบโต จำเป็นต้องมี #t4310216# 1 ชิ้น", 2400009, 0,
                                        ScriptMessageFlag.Scenario);
                        getPlayer().openInheritanceUpgrade(8, 4310216, 1);
                } else if (v0 == 1) {
                        self.sayReplacedNpc("เพื่อที่จะเติบโต จำเป็นต้องมี #t4310217# 1 ชิ้น", 2400009, 0,
                                        ScriptMessageFlag.Scenario);
                        getPlayer().openInheritanceUpgrade(9, 4310217, 1);
                } else if (v0 == 2) {
                        self.sayReplacedNpc("เพื่อที่จะได้รับอาวุธ Genesis จำเป็นต้องมี #t4310260# 1 ชิ้น", 2400009, 0,
                                        ScriptMessageFlag.Scenario);
                        if (!getPlayer().haveItem(4310260)) {
                                self.sayReplacedNpc("ถ้าตามรอยที่ Black Mage ทิ้งไว้ ก็จะได้รับ #b#t4310260##k ไงล่ะ",
                                                2400009, 0,
                                                ScriptMessageFlag.Scenario);
                                self.sayReplacedNpc("อีกอย่าง การเติบโตโดยใช้ #t4310260# มันต่างจากที่ผ่านมามากนะ!",
                                                2400009, 0,
                                                ScriptMessageFlag.Scenario);
                                self.sayReplacedNpc(
                                                "#bอาวุธ Genesis#k นั้น ศักยภาพ, ศักยภาพเพิ่มเติม, และออปชั่นเพิ่มเติมจากระดับก่อนหน้านั้นจะไม่ถูกสืบทอดมา และจะมีออปชั่นพิเศษติดมาด้วย\r\n#r- ใช้ใบอัปเกรด Spell Trace 15% พลังโจมตี(STR) จนครบจำนวนอัปเกรดทั้งหมด\r\n- Star Force 22 ดาว\r\n- มีศักยภาพระดับ Unique และศักยภาพเพิ่มเติมระดับ Epic (กรณีที่ไม่สืบทอด)\r\n- เมื่อสวมใส่จะได้รับสกิล <Yaldabaoth of Destruction>, <Aeon of Creation>\r\n- ไม่สามารถใช้ใบอัปเกรดและเสริมแกร่ง Star Force ได้",
                                                2400009, 0, ScriptMessageFlag.Scenario);
                                self.sayReplacedNpc(
                                                "และหลังจากทำอาวุธ Genesis สำเร็จแล้ว ก็สามารถใช้ปุ่มพัฒนาอาวุธเพื่อสลับไปมาระหว่างระดับก่อนหน้าและอาวุธ Genesis ได้ ดังนั้นไม่ต้องกังวลเรื่องการเสริมแกร่งอาวุธ Genesis หรอกนะ\r\nแต่ว่า การสลับอาวุธต้องใช้พลังมาก ดังนั้นหลังจากสลับครั้งนึงแล้ว จะไม่สามารถสลับได้อีกเป็นเวลา 10 นาที",
                                                2400009, 0, ScriptMessageFlag.Scenario);
                                self.sayReplacedNpc("พยายามเข้าล่ะ หวังว่าจะได้ #b#t4310260##k มานะ", 2400009, 0,
                                                ScriptMessageFlag.Scenario);
                                // getPlayer().openInheritanceUpgrade(10, 4310260, 1);
                        } else {
                                self.sayReplacedNpc(
                                                "#b#t4310260##k ที่มีพลังของ Black Mage อยู่ งั้นเหรอ!\r\nสุดยอดไปเลยนะ นายเนี่ย!",
                                                2400009, 0, ScriptMessageFlag.Scenario);
                                self.sayReplacedNpc(
                                                "ใช้ #b#t4310260##k เสริมแกร่งพวกเราให้เป็น #bอาวุธ Genesis#k ได้สินะ!\r\nแต่ว่า การเสริมแกร่งตอนนี้มันต่างจากที่ผ่านมามากนะ!",
                                                2400009, 0, ScriptMessageFlag.Scenario);
                                self.sayReplacedNpc(
                                                "#bอาวุธ Genesis#k นั้น ศักยภาพ, ศักยภาพเพิ่มเติม, และออปชั่นเพิ่มเติมจากระดับก่อนหน้านั้นจะไม่ถูกสืบทอดมา และจะมีออปชั่นพิเศษติดมาด้วย\r\n#r- ใช้ใบอัปเกรด Spell Trace 15% พลังโจมตี(STR) จนครบจำนวนอัปเกรดทั้งหมด\r\n- Star Force 22 ดาว\r\n- มีศักยภาพระดับ Unique และศักยภาพเพิ่มเติมระดับ Epic (กรณีที่ไม่สืบทอด)\r\n- เมื่อสวมใส่จะได้รับสกิล <Yaldabaoth of Destruction>, <Aeon of Creation>\r\n- ไม่สามารถใช้ใบอัปเกรดและเสริมแกร่ง Star Force ได้",
                                                2400009, 0, ScriptMessageFlag.Scenario);
                                self.sayReplacedNpc(
                                                "และหลังจากทำอาวุธ Genesis สำเร็จแล้ว ก็สามารถใช้ปุ่มพัฒนาอาวุธเพื่อสลับไปมาระหว่างระดับก่อนหน้าและอาวุธ Genesis ได้ ดังนั้นไม่ต้องกังวลเรื่องการเสริมแกร่งอาวุธ Genesis หรอกนะ\r\nแต่ว่า การสลับอาวุธต้องใช้พลังมาก ดังนั้นหลังจากสลับครั้งนึงแล้ว จะไม่สามารถสลับได้อีกเป็นเวลา 10 นาที",
                                                2400009, 0, ScriptMessageFlag.Scenario);
                                int v1 = self.askMenuReplacedNpc(
                                                "ในส่วนของ #rศักยภาพ#k ถ้าเจ้าต้องการ มันจะถูกเปลี่ยนเป็น #bศักยภาพระดับ Unique#k แบบสุ่ม จะเปลี่ยนเป็น #bศักยภาพระดับ Unique#k หรือไม่?\r\n\r\n#b#L0#เปลี่ยนเป็นศักยภาพระดับ Unique#l\r\n#L1#คงศักยภาพเดิมไว้ตามเดิม#l",
                                                2400009, 0, ScriptMessageFlag.NpcReplacedByNpc);
                                int v2 = self.askMenuReplacedNpc(
                                                "ในส่วนของ #rศักยภาพเพิ่มเติม#k ถ้าเจ้าต้องการ มันจะถูกเปลี่ยนเป็น #bออปชั่นศักยภาพเพิ่มเติมระดับ Epic#k แบบสุ่ม จะเปลี่ยนเป็น #bศักยภาพเพิ่มเติมระดับ Epic#k หรือไม่?\r\n\r\n#b#L0#เปลี่ยนเป็นศักยภาพเพิ่มเติมระดับ Epic#l\r\n#L1#คงศักยภาพเพิ่มเติมเดิมไว้ตามเดิม#l",
                                                2400009, 0, ScriptMessageFlag.NpcReplacedByNpc);
                                self.sayReplacedNpc("#bออปชั่นเพิ่มเติม#k จะถูกสืบทอดมาตามเดิม ไม่ต้องกังวลนะ!",
                                                2400009, 0,
                                                ScriptMessageFlag.Scenario);
                                String v4 = "เอาล่ะ! นี่เป็นครั้งสุดท้ายจริงๆ แล้ว!\r\nใช้ #t4310260# เพื่อรับ #bGenesis Lapis และ Lazuli#k ไหม?\r\n";
                                v4 += "\r\n#rสถานะการรีเซ็ตออปชั่นศักยภาพ Unique : #e"
                                                + (v1 == 0 ? "รีเซ็ต" : "ไม่รีเซ็ต") + "#n\r\n";
                                v4 += "#rสถานะการรีเซ็ตออปชั่นศักยภาพเพิ่มเติม Epic : #e"
                                                + (v2 == 0 ? "รีเซ็ต" : "ไม่รีเซ็ต")
                                                + "#n\r\n";
                                v4 += "\r\n#b#L0#รับ Genesis Lapis และ Lazuli#l\r\n#L1#ไว้ก่อน#l";
                                int v3 = self.askMenuReplacedNpc(v4, 2400009, 0, ScriptMessageFlag.NpcReplacedByNpc);
                                if (v3 == 0) {
                                        if (v1 == 0) {
                                                getPlayer().updateOneInfo(40981, "reset_potential", "1");
                                        }
                                        if (v2 == 0) {
                                                getPlayer().updateOneInfo(40981, "reset_e_potential", "1");
                                        }
                                        getPlayer().openInheritanceUpgrade(10, 4310260, 1);
                                }
                        }
                } else if (v0 == 3) {
                        boolean check = false;
                        long time = getPlayer().getOneInfoQuestLong(40981, "last_change_time");
                        if (time == 0 || System.currentTimeMillis() - time >= (1000 * 60 * 10)) {
                                // 10นาที
                                check = true;
                        }
                        if (check) {
                                Item alphaBefore = getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                                .getItem((short) -20000);
                                Item betaBefore = getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                                .getItem((short) -20001);
                                if (alphaBefore == null || betaBefore == null) {
                                        self.sayReplacedNpc(
                                                        "เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุ ลองติดต่อ Customer Center ดูไหม?",
                                                        2400009, 0,
                                                        ScriptMessageFlag.NpcReplacedByNpc);
                                        return;
                                }

                                // ปัจจุบัน อาวุธ บันทึก
                                Equip alpha_copy = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                                .getItem((short) -11)
                                                .copy();
                                Equip beta_copy = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                                                .getItem((short) -10)
                                                .copy();

                                getPlayer().getInventory(MapleInventoryType.EQUIPPED).removeItem((short) -11);
                                getPlayer().getInventory(MapleInventoryType.EQUIPPED).removeItem((short) -10);

                                alphaBefore.setPosition((short) -11);
                                betaBefore.setPosition((short) -10);

                                getPlayer().getInventory(MapleInventoryType.EQUIPPED).addFromDB(alphaBefore);
                                getPlayer().getInventory(MapleInventoryType.EQUIPPED).addFromDB(betaBefore);

                                getPlayer().send(CWvsContext.InventoryPacket.updateEquipSlot(alphaBefore));
                                getPlayer().send(CWvsContext.InventoryPacket.updateEquipSlot(betaBefore));

                                alpha_copy.setPosition((short) -20000);
                                beta_copy.setPosition((short) -20001);

                                getPlayer().getInventory(MapleInventoryType.EQUIPPED).addFromDB(alpha_copy);
                                getPlayer().getInventory(MapleInventoryType.EQUIPPED).addFromDB(beta_copy);

                                if (curLv == 10) {
                                        getPlayer().changeSkillLevel(80002632, -1, -1);
                                        getPlayer().changeSkillLevel(80002633, -1, -1);
                                } else {
                                        getPlayer().changeSkillLevel(80002632, 1, 1);
                                        getPlayer().changeSkillLevel(80002633, 1, 1);
                                }
                                getPlayer().updateOneInfo(40981, "save_genesis", "1");
                                getPlayer().updateOneInfo(40981, "last_change_time",
                                                String.valueOf(System.currentTimeMillis()));
                        } else {
                                self.sayReplacedNpc(
                                                "การกลับไปเป็นอาวุธก่อนหน้าต้องใช้พลังมหาศาล ยังต้องการเวลาอีกหน่อย ไว้ลองใหม่ทีหลังนะ?",
                                                2400009, 0, ScriptMessageFlag.NpcReplacedByNpc);
                        }
                }
        }

        public void zero_reinvoke_weapon() {
                int curLv = getPlayer().getOneInfoQuestInteger(40981, "lv");

                self.sayReplacedNpc(
                                "#face2#เหวอ, นี่มันกลายเป็นผงไปหมดแล้วไม่ใช่เหรอ? นายทำอะไรกับฉันเนี่ย? แล้วจะทำยังไงดียังงั้นเหรอ? ก็ไม่เห็นต้องทำยังไงเลย! อาวุธพังไปแล้ว ทุกอย่างก็จบ GAME OVER ไงล่ะ! ที่ผ่านมาสนุกมากนะ งั้นก็ ลาก่อน...",
                                2400009, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
                self.sayReplacedNpc(
                                "...ถ้าพูดแบบนั้นคงเสียใจแย่สินะ? ฮ่าๆ ตกใจไหมล่ะ? แต่ครั้งนี้ฉันเองก็ตกใจมากเหมือนกันนะ เห็นแบบนี้ฉันเป็นผู้ชายที่อ่อนไหวนะ ช่วยดูแลกันดีๆ หน่อยสิ~",
                                2400009, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
                self.sayReplacedNpc(
                                "ยังไงซะ อาวุธก็เป็นแค่ภาชนะที่บรรจุพวกเราไว้ ตราบใดที่พวกเรายังอยู่ ก็สามารถชุบชีวิตอาวุธขึ้นมาใหม่ได้ทุกเมื่อ เอาล่ะ งั้นรวบรวมพลังแห่งเวลาแบบนี้...",
                                2400009, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);

                getSc().flushSay();

                int alphaID = 1572000 + Math.min(7, curLv);
                int betaID = 1562000 + Math.min(7, curLv);

                MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
                Equip alpha_new = (Equip) ii.getEquipById(alphaID);
                Equip beta_new = (Equip) ii.getEquipById(betaID);

                alpha_new.setPosition((short) -11);
                beta_new.setPosition((short) -10);

                getPlayer().getInventory(MapleInventoryType.EQUIPPED).addFromDB(alpha_new);
                getPlayer().getInventory(MapleInventoryType.EQUIPPED).addFromDB(beta_new);

                getPlayer().send(CWvsContext.InventoryPacket.updateEquipSlot(alpha_new));
                getPlayer().send(CWvsContext.InventoryPacket.updateEquipSlot(beta_new));

                self.sayReplacedNpc(
                                "แท่นแท๊น! เป็นไงล่ะ? เหมือนใหม่เลยใช่มั้ย? หืม? บอกว่าในเมื่อชุบชีวิตได้ทุกเมื่อ ก็ตีบวกได้ไม่ต้องกังวลแล้วงั้นเหรอ? นั่นมัน...",
                                2400009, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
                self.sayReplacedNpc(
                                "#face2#...คิดแบบนั้นได้ยังไงกัน?! ตอนที่อาวุธพัง พวกเราเองก็ได้รับแรงกระแทกไม่น้อยเลยนะ! ตอนอาวุธแตกน่ะตกใจแทบแย่......",
                                2400010, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
                self.sayReplacedNpc(
                                "ที่ Lazuli พูดก็ถูกนะ แถมไอเทมที่ทรงพลังมากๆ แม้แต่พลังแห่งเวลาก็มีขีดจำกัดในการชุบชีวิตนะ ต่อไปก็ระวังหน่อยล่ะ!",
                                2400009, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
        }

        public void zero_egoequiptalk() {
                int r = Randomizer.rand(1, 34);
                if (r == 1) {
                        self.sayReplacedNpc(
                                        "สิ่งที่ฉันชอบที่สุดเหรอ...? อื้ม... ฉันชอบเค้กสตรอว์เบอร์รี่ที่สุดเลย ถ้าอยากกินด้วยกันล่ะก็... อืม ถ้านายเป็นคนกินด้วยกันก็โอนะ",
                                        2400010, 0, ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("ฉันด้วย! ฉันด้วย! ฉันก็ชอบเค้กสตรอว์เบอร์รี่เหมือนกัน!", 2400009, 0,
                                        ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
                        self.sayReplacedNpc("#face0#ไม่เอา! ฉันจะกินกับ Alpha สองคน... อ๊ะ ไม่สิ งั้นฉันไปดีกว่า",
                                        2400010, 0, ScriptMessageFlag.Scenario);
                } else if (r == 2) {
                        self.sayReplacedNpc("คิดยังไงกับ Lapis งั้นเหรอ? อืม... เจ้าบ้าตัวใหญ่แต่ตัว?", 2400010, 0,
                                        ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("#face2#ใจร้ายจัง Lazuli ฉันเสียใจนะเนี่ย", 2400009, 0,
                                        ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
                        self.sayReplacedNpc("อ๊ะ ตรงนั้นมีเนื้อที่ดูน่ากินสุดๆ อยู่ด้วย!", 2400010, 0,
                                        ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("เนื้อ?! ที่ไหน? ที่ไหน?", 2400009, 0, ScriptMessageFlag.Scenario,
                                        ScriptMessageFlag.FlipImage);
                        self.sayReplacedNpc("หึ ก็เป็นเจ้าบ้าจริงๆ นั่นแหละ", 2400010, 0, ScriptMessageFlag.Scenario);
                } else if (r == 3) {
                        self.sayReplacedNpc("#face1#จะ, จ้องมองกันแบบนั้นมันน่าอายนะ", 2400010, 0,
                                        ScriptMessageFlag.Scenario);
                } else if (r == 4) {
                        self.sayReplacedNpc(
                                        "ทำไมถึงเลือก Alpha งั้นเหรอ? เรื่องนั้น... ม, ไม่ได้มีเหตุผลอะไรเป็นพิเศษหรอก ก็แค่เลือกใครก็ได้เท่านั้นแหละ ห้ามคิดเข้าข้างตัวเองว่าฉันถูกใจนายเด็ดขาดเลยนะ!",
                                        2400010, 0, ScriptMessageFlag.Scenario);
                } else if (r == 5) {
                        self.sayReplacedNpc(
                                        "#face1#ฉันเกลียดตอนกลางคืน มันให้ความรู้สึกเหมือนอยู่คนเดียวยังไงไม่รู้ แต่ว่าตอนนี้ไม่ได้อยู่คนเดียวแล้ว ก็ไม่เป็นไรหรอก",
                                        2400010, 0, ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("ใช่แล้วๆ มีฉันอยู่ทั้งคน ไม่ต้องห่วงนะ", 2400009, 0,
                                        ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
                        self.sayReplacedNpc("#face0#ไม่ได้พูดกับนายย่ะ!", 2400010, 0, ScriptMessageFlag.Scenario);
                } else if (r == 6) {
                        self.sayReplacedNpc(
                                        "#face0#ไม่ได้อยากอยู่ด้วยเพราะชอบนายหรอกนะ ฉันก็แค่ทำตามหน้าที่ของฉันเท่านั้นแหละ อย่าใส่ใจเลย",
                                        2400010, 0, ScriptMessageFlag.Scenario);
                } else if (r == 7) {
                        self.sayReplacedNpc(
                                        "คิดยังไงกับ Alpha งั้นเหรอ? เอ๋ คิดยังไงอะไรกันเล่า? อ๊ะ ฉันไม่ได้ชอบคนอย่าง Alpha สักหน่อยนะ!",
                                        2400010, 0, ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("หืม?", 2400005, 0, ScriptMessageFlag.Scenario,
                                        ScriptMessageFlag.FlipImage);
                        self.sayReplacedNpc("#face1#ตะ, แต่ก็ไม่ได้หมายความว่าเกลียดหรอกนะ......", 2400010, 0,
                                        ScriptMessageFlag.Scenario);
                } else if (r == 8) {
                        self.sayReplacedNpc(
                                        "#face0#คิดว่าฉันโกรธอยู่เหรอ? ก็แค่อากาศมันไม่ค่อยดีเท่านั้นแหละ ไม่ใช่เพราะนายไม่ยอมชวนคุยตั้งนานหรอกนะ!",
                                        2400010, 0, ScriptMessageFlag.Scenario);
                } else if (r == 9) {
                        self.sayReplacedNpc(
                                        "สิ่งที่ฉันชอบที่สุด... แน่นอนว่าต้องเป็นเนื้อ! เนื้อที่ทั้งดีต่อสุขภาพและอร่อย!",
                                        2400009, 0, ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("ฉันก็ชอบเนื้อเหมือนกัน อร่อยดีนะ", 2400006, 0, ScriptMessageFlag.Scenario,
                                        ScriptMessageFlag.FlipImage);
                        self.sayReplacedNpc("งั้นไปกินเนื้อกับฉันเถอะ! เดี๋ยวฉันย่างให้กินอร่อยๆ เลย!", 2400009, 0,
                                        ScriptMessageFlag.Scenario);
                } else if (r == 10) {
                        self.sayReplacedNpc(
                                        "คิดยังไงกับ Lazuli งั้นเหรอ? Lazuli น่ะ... น่ากลัวจริงๆ นะ เวลาโกรธเหมือนแม่มดเลย ดูสิ ตอนนี้ก็... ฮึ้ย!",
                                        2400009, 0, ScriptMessageFlag.Scenario);
                } else if (r == 11) {
                        self.sayReplacedNpc(
                                        "ทำไมถึงเลือก Beta งั้นเหรอ? นั่นสินะ ก็เพราะ... เธอน่ารักไง! ไหนๆ จะได้รับใช้นายท่านทั้งที ผู้หญิงน่ารักๆ ก็ดีกว่าใช่มั้ยล่ะ? ฮ่าๆ",
                                        2400009, 0, ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("ฉันน่ารักเหรอ?", 2400006, 0, ScriptMessageFlag.Scenario,
                                        ScriptMessageFlag.FlipImage);
                        self.sayReplacedNpc("แน่นอนสิ! Lazuli เองก็เลือก Alpha เพราะเขาหล่อเหมือนกันไม่ใช่เหรอ",
                                        2400009, 0, ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("#face1#ม, ไม่ใช่ซะหน่อย!", 2400010, 0, ScriptMessageFlag.Scenario,
                                        ScriptMessageFlag.FlipImage);
                } else if (r == 12) {
                        self.sayReplacedNpc(
                                        "ยัย Lazuli น่ะ จริงๆ แล้วเหมือนจะชอบเธอพอสมควรเลยนะ รู้ได้ยังไงงั้นเหรอ? เดิมทีพวกเราเคยเป็นหนึ่งเดียวกันมาก่อน ถึงไม่พูดก็รู้หมดแหละ",
                                        2400009, 0, ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("#face1#อย่าพูดเรื่องไร้สาระนะ ไม่ใช่แบบนั้นสักหน่อย!", 2400006, 0,
                                        ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
                } else if (r == 13) {
                        self.sayReplacedNpc("ฉันก็ดูมีประโยชน์พอตัวไม่ใช่เหรอ? ...ถ้าคิดว่าไม่ใช่ก็ช่วยไม่ได้ล่ะนะ",
                                        2400009, 0, ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("Lapis สู้ๆ นะ...", 2400006, 0, ScriptMessageFlag.Scenario,
                                        ScriptMessageFlag.FlipImage);
                        self.sayReplacedNpc("#face2#อะ, อ่านะ คำพูดนั้นมันทำให้รู้สึกหมดแรงกว่าเดิมอีกแฮะ", 2400009, 0,
                                        ScriptMessageFlag.Scenario);
                } else if (r == 14) {
                        self.sayReplacedNpc("เวลาซึมเศร้า การกินนี่แหละสุดยอดที่สุด ง่ำๆ!", 2400009, 0,
                                        ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("แล้วเวลาอารมณ์ดีล่ะ?", 2400006, 0, ScriptMessageFlag.Scenario,
                                        ScriptMessageFlag.FlipImage);
                        self.sayReplacedNpc("เวลาอารมณ์ดี การกินก็สุดยอดที่สุดเหมือนกัน ง่ำๆ!", 2400009, 0,
                                        ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("แล้วเวลาเศร้าล่ะ?", 2400006, 0, ScriptMessageFlag.Scenario,
                                        ScriptMessageFlag.FlipImage);
                        self.sayReplacedNpc("เวลาเศร้า การกินก็......", 2400009, 0, ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("สรุปก็คือกินตลอดเลยไม่ใช่รึไง!", 2400010, 0, ScriptMessageFlag.Scenario,
                                        ScriptMessageFlag.FlipImage);
                } else if (r == 15) {
                        self.sayReplacedNpc(
                                        "กำลังคิดอะไรอยู่งั้นเหรอ? จริงๆ แล้วตอนนี้ฉันกำลังซีเรียส... ...ไม่ได้คิดอะไรอยู่เลยต่างหาก?",
                                        2400009, 0, ScriptMessageFlag.Scenario);
                } else if (r == 16) {
                        self.sayReplacedNpc(
                                        "เห็นทุกคนเงียบกันหมด ฉันเลยต้องทำลายบรรยากาศเงียบเหงาหน่อย ฉันมีภารกิจในฐานะผู้สร้างบรรยากาศอยู่ด้วยนะ! ไม่ใช่แค่เบื่อเลยแกล้งเล่นหรอกนะ ...จริงๆ นะ!",
                                        2400009, 0, ScriptMessageFlag.Scenario);
                } else if (r == 17) {
                        self.sayReplacedNpc(
                                        "ถ้ากำจัดบอสมอนสเตอร์ได้ จะได้รับ WP มากขึ้นนะ แน่นอนว่าต้องกำจัดให้ได้ก่อนถึงจะได้ ต้องกำจัดให้ได้นะ...",
                                        2400009, 0, ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("#face0#พูดรอบเดียวก็พอแล้ว รอบเดียว! ", 2400005, 0,
                                        ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
                } else if (r == 18) {
                        self.sayReplacedNpc(
                                        "นายต้องเติบโต พวกเราถึงจะวิวัฒนาการได้ เพราะงั้นต้องกินเนื้อที่ดีต่อสุขภาพและอร่อยเยอะๆ... อ๊ะ ไม่ใช่เหรอ?",
                                        2400009, 0, ScriptMessageFlag.Scenario);
                } else if (r == 19) {
                        self.sayReplacedNpc(
                                        "ถ้าใช้ใบอัปเกรดหรือ Cube จะมีผลกับดาบใหญ่และดาบยาวพร้อมกันเลย นี่แหละคือ 1+1!",
                                        2400009, 0, ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("#face5#โอ้ ก็เข้าท่าดีนี่?", 2400005, 0, ScriptMessageFlag.Scenario,
                                        ScriptMessageFlag.FlipImage);
                        self.sayReplacedNpc("แต่ตอนพังก็ 1+1 เหมือนกันนะ!", 2400009, 0, ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("#face6#……!!!", 2400005, 0, ScriptMessageFlag.Scenario,
                                        ScriptMessageFlag.FlipImage);
                } else if (r == 20) {
                        self.sayReplacedNpc(
                                        "ชอบอ่านหนังสือเหรอ? ฉันแค่เปิดหนังสือก็จะหลับแล้ว... ในชั้นหนังสือของวิหารจะมีหนังสือการ์ตูนบ้างมั้ยนะ?",
                                        2400009, 0, ScriptMessageFlag.Scenario);
                } else if (r == 21) {
                        self.sayReplacedNpc(
                                        "สามารถเพิ่มระดับอาวุธได้ตอนเลเวล 110, 120, 130, 140, 150, 170, 180 แน่นอนว่าถ้ายังไม่อยากทำ จะเลือกทำทีหลังก็ได้นะ",
                                        2400009, 0,
                                        ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("รู้สึกเหมือนมีอะไรต่างจากเมื่อก่อนนิดหน่อยแฮะ?", 2400010, 0,
                                        ScriptMessageFlag.Scenario,
                                        ScriptMessageFlag.FlipImage);
                        self.sayReplacedNpc("นายเองก็พูดเหมือนกำลังแนะนำอะไรอยู่เลยไม่ใช่เหรอ?", 2400009, 0,
                                        ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("...", 2400006, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
                } else if (r == 22) {
                        self.sayReplacedNpc(
                                        "ลองกดปุ่มเปลี่ยนศักยภาพดูรึยัง? หัวใจฉันเต้นตึกตักเลยว่าจะได้ศักยภาพแบบไหนออกมา!",
                                        2400009, 0, ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("แล้วถ้าได้ศักยภาพที่ไม่ดีออกมาจะทำไงล่ะ?", 2400010, 0,
                                        ScriptMessageFlag.Scenario,
                                        ScriptMessageFlag.FlipImage);
                        self.sayReplacedNpc("ไม่เป็นไร ฉันเป็นผู้ชายที่มองโลกในแง่ดี! ก็สุ่มไปจนกว่าจะได้ของดีไง!",
                                        2400009, 0,
                                        ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("#face7#สรุปก็คือปล่อยไปตามยถากรรมสินะ...", 2400005, 0,
                                        ScriptMessageFlag.Scenario,
                                        ScriptMessageFlag.FlipImage);
                } else if (r == 23) {
                        self.sayReplacedNpc(
                                        "ในบรรดาพวกนักบวช คนที่ชื่อ Dello ถึงจะยังเด็กแต่ก็ฉลาดเอาเรื่องเลยนะ? เหมือนจะมีความรู้กว้างขวางเกี่ยวกับทักษะวิชาชีพพอสมควรเลยนี่นา",
                                        2400009, 0,
                                        ScriptMessageFlag.Scenario);
                } else if (r == 24) {
                        self.sayReplacedNpc(
                                        "ใน WEAPON UI จะเสริมแกร่งได้แค่อาวุธเท่านั้นนะ เพราะที่นี่เป็นพื้นที่ของพวกเรา ส่วนเครื่องป้องกันหรือเครื่องประดับอื่นๆ ก็ไปเสริมแกร่งที่หน้าต่างอุปกรณ์ตามปกติเถอะ",
                                        2400010, 0, ScriptMessageFlag.Scenario);
                } else if (r == 25) {
                        self.sayReplacedNpc(
                                        "การใช้ Tag จะทำให้การต่อสู้มีประสิทธิภาพมากขึ้น ก, ก็ไม่ได้อยากจะร่วมสู้ไปกับพวกนายหรอกนะ อย่าเข้าใจผิดล่ะ",
                                        2400010, 0, ScriptMessageFlag.Scenario);
                } else if (r == 26) {
                        self.sayReplacedNpc(
                                        "เห็นว่าถ้าใช้คัมภีร์ Lucky Item จะได้รับเอฟเฟกต์เซ็ตไอเทมด้วยนี่นา แต่จริงๆ แล้วฉันไม่ค่อยชอบสังกัดอยู่ที่ไหนเท่าไหร่หรอกนะ",
                                        2400010, 0, ScriptMessageFlag.Scenario);
                } else if (r == 27) {
                        self.sayReplacedNpc(
                                        "ถ้าสะสม Coin แล้วเอาไปให้นักบวช พวกนั้นคงจะชอบน่าดู สำหรับพวกไส้แห้งแล้วคงมีค่ามากเลยล่ะมั้ง",
                                        2400010, 0, ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("#face11#นายพูดแรงไปมั้ย?", 2400005, 0, ScriptMessageFlag.Scenario,
                                        ScriptMessageFlag.FlipImage);
                        self.sayReplacedNpc("#face2#มะ, ไม่ใช่แบบนั้นซะหน่อย...... ", 2400010, 0,
                                        ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("#face10#ก็นะ ที่นายพูดก็ไม่ผิดหรอก ความจริงคือฉันก็ไส้แห้งจริงๆ นั่นแหละ",
                                        2400005, 0,
                                        ScriptMessageFlag.Scenario,
                                        ScriptMessageFlag.FlipImage);
                        self.sayReplacedNpc("#face2#ไม่ใช่ ไม่ใช่แบบนั้น...... ไม่รู้ด้วยแล้ว เจ้าบ้า!", 2400010, 0,
                                        ScriptMessageFlag.Scenario);
                } else if (r == 28) {
                        self.sayReplacedNpc(
                                        "รู้จักเซ็ตเครื่องป้องกัน Gold Label Knight รึเปล่า? เห็นว่าบอสใน Mirror World มี Recipe อยู่นะ ลองไปหามาสร้างดูมั้ยล่ะ? ถึงจะไม่มีทางก็เถอะ แต่ไม่แน่อาจจะเหมาะกับนายก็ได้นะ เชอะ",
                                        2400010, 0, ScriptMessageFlag.Scenario);
                } else if (r == 29) {
                        self.sayReplacedNpc("MP Potion น่ะไม่อร่อยเลย โชคดีจริงๆ ที่ไม่ต้องกิน MP Potion", 2400009, 0,
                                        ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("บนโลกนี้มีของกินที่นายเกลียดด้วยเหรอ?", 2400010, 0,
                                        ScriptMessageFlag.Scenario,
                                        ScriptMessageFlag.FlipImage);
                        self.sayReplacedNpc("#face2#แน่นอนสิ! นายเห็นฉันเป็นตัวอะไรกันเนี่ย?", 2400009, 0,
                                        ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("เจ้าบ้าจอมตะกละ", 2400010, 0, ScriptMessageFlag.Scenario,
                                        ScriptMessageFlag.FlipImage);
                        self.sayReplacedNpc("#face2#เธอ, ตรงไปตรงมาเกินไปแล้ว!", 2400009, 0,
                                        ScriptMessageFlag.Scenario);
                } else if (r == 30) {
                        self.sayReplacedNpc(
                                        "แน่นอนว่าพวกเราเป็นอาวุธสองมือ หมายความว่าจะต้องดูแลเทิดทูนด้วยสองมือไงล่ะ",
                                        2400010, 0,
                                        ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("#face7#อาวุธสองมือมีความหมายแบบนั้นเองเหรอ?!", 2400005, 0,
                                        ScriptMessageFlag.Scenario,
                                        ScriptMessageFlag.FlipImage);
                } else if (r == 31) {
                        self.sayReplacedNpc(
                                        "ถ้ารวบรวมน้ำตาของเทพธิดาได้ จะเรียนสกิลใหม่ได้ ...แต่ฉันไม่อยากจะไปไหนมาไหนกับพวกนั้นเท่าไหร่น่ะสิ",
                                        2400010, 0, ScriptMessageFlag.Scenario);
                } else if (r == 32) {
                        self.sayReplacedNpc(
                                        "พวกเธอน่ะ ถึงจะเหมือนกันแต่ก็ต่างกันมากนะ ทรงผมหรือหน้าตาจะใช้ร่วมกันก็ได้ แต่ก็สามารถแยกใช้ทีละคนได้เหมือนกัน ลองหาความเป็นตัวเองดูหน่อยก็น่าจะไม่เลวนะ?",
                                        2400010, 0, ScriptMessageFlag.Scenario);
                } else if (r == 33) {
                        self.sayReplacedNpc(
                                        "#face1#เอ๋? ความสัมพันธ์กับ Lazuli ไม่ดีเหรอ? ไม่นะ ฉันคิดว่าพวกเราสนิทกันดีออก ทำไมเหรอ?",
                                        2400009, 0, ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("ฉันไม่เล่นกับคนบ้าหรอกนะ", 2400010, 0, ScriptMessageFlag.Scenario,
                                        ScriptMessageFlag.FlipImage);
                        self.sayReplacedNpc("#face0#เห็นมั้ย สนิทกันจะตาย", 2400009, 0, ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("#face7#ไม่ได้หมายความแบบนั้นเลยสักนิด", 2400005, 0,
                                        ScriptMessageFlag.Scenario,
                                        ScriptMessageFlag.FlipImage);
                } else if (r == 34) {
                        self.sayReplacedNpc("#face1#จริงสิ ฉันเคยบอกหรือยังว่า WP หาได้จาก Mirror World เท่านั้นน่ะ?",
                                        2400009, 0,
                                        ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("#face7#อะไรนะ? ถึงจะเป็นอิสระจาก Mirror World แล้วก็หาไม่ได้งั้นเหรอ?",
                                        2400005, 0,
                                        ScriptMessageFlag.Scenario,
                                        ScriptMessageFlag.FlipImage);
                        self.sayReplacedNpc(
                                        "WP คือพลังแห่งเวลาที่กระจายอยู่ใน Mirror World แน่นอนว่าต้องหาได้แค่ใน Mirror World สิ? ใน Maple World มีแค่บอสระดับผู้บัญชาการกองพันเท่านั้นที่มี WP",
                                        2400010,
                                        0, ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("และน่าเสียดายที่ในเนื้อไม่มีส่วนผสม(?)ของ WP หรอกนะ", 2400009, 0,
                                        ScriptMessageFlag.Scenario,
                                        ScriptMessageFlag.FlipImage);
                        self.sayReplacedNpc("เสียดายจัง...", 2400006, 0, ScriptMessageFlag.Scenario);
                        self.sayReplacedNpc("#face7#เสียดายอะไรของนายเนี่ย?", 2400005, 0, ScriptMessageFlag.Scenario,
                                        ScriptMessageFlag.FlipImage);
                }
        }
}
