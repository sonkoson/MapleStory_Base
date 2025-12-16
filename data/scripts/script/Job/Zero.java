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
            self.sayReplacedNpc("이제จาก เรา를 더 성장 시키기 บน해서는 พิเศษ 재료가 จำเป็น해.", 2400009, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("#face1#비싼 몸이구ฉัน…… เรา……", 2400010, 0, ScriptMessageFlag.Scenario);
        }

        int v0 = -1;
        if (getPlayer().getOneInfoQuestInteger(40981, "save_genesis") == 1) {
            if (curLv != 10) {
                v0 = self.askMenuReplacedNpc("자, #h0#. อย่างไร 하겠어?\r\n\r\n#b#L3# 제네시스 라피스และ 라즐리를 얻는다.#l\r\n#L999# เขา만둔다.#l", 2400009, 0,  ScriptMessageFlag.NpcReplacedByNpc);
            } else {
                v0 = self.askMenuReplacedNpc("자, #h0#. อย่างไร 하겠어?\r\n\r\n#b#L3# ก่อนหน้า 단계의 라피스และ 라즐리를 장착한다.#l\r\n#L999# เขา만둔다.#l", 2400009, 0,  ScriptMessageFlag.NpcReplacedByNpc);
            }
        } else if (curLv == 7) {
            if (level >= 200) {
                v0 = self.askMenuReplacedNpc("자, #h0#. อย่างไร 하겠어?\r\n\r\n#b#L0# 라피스และ 라즐리를 8형으로 성장시킨다.#l\r\n#L1# 라피스และ 라즐리를 9형으로 성장시킨다.#l\r\n#L999# เขา만둔다.#l", 2400009, 0,  ScriptMessageFlag.Scenario);
            } else {
                v0 = self.askMenuReplacedNpc("자, #h0#. อย่างไร 하겠어?\r\n\r\n#b#L0# 라피스และ 라즐리를 8형으로 성장시킨다.#l\r\n#L999# เขา만둔다.#l", 2400009, 0,  ScriptMessageFlag.Scenario);
            }
        } else if (curLv == 8) {
            v0 = self.askMenuReplacedNpc("자, #h0#. อย่างไร 하겠어?\r\n\r\n#b#L1# 라피스และ 라즐리를 9형으로 성장시킨다.#l\r\n#L999# เขา만둔다.#l", 2400009, 0,  ScriptMessageFlag.Scenario);
        } else if (curLv == 9) {
            v0 = self.askMenuReplacedNpc("자, #h0#. อย่างไร 하겠어?\r\n\r\n#b#L2# 라피스และ 라즐리를 10형으로 성장시킨다.#l\r\n#L999# เขา만둔다.#l", 2400009, 0,  ScriptMessageFlag.Scenario);
        } else if (curLv == 10) {
            v0 = self.askMenuReplacedNpc("자, #h0#. อย่างไร 하겠어?\r\n\r\n#b#L3# ก่อนหน้า 단계의 라피스และ 라즐리를 장착한다.#l\r\n#L999# เขา만둔다.#l", 2400009, 0,  ScriptMessageFlag.NpcReplacedByNpc);
        }

        if (v0 == 0) {
            self.sayReplacedNpc("성장하기 บน해서는 #t4310216# 1개 จำเป็น해.", 2400009, 0, ScriptMessageFlag.Scenario);
            getPlayer().openInheritanceUpgrade(8, 4310216, 1);
        } else if (v0 == 1) {
            self.sayReplacedNpc("성장하기 บน해서는 #t4310217# 1개 จำเป็น해.", 2400009, 0, ScriptMessageFlag.Scenario);
            getPlayer().openInheritanceUpgrade(9, 4310217, 1);
        } else if (v0 == 2) {
            self.sayReplacedNpc("제네시스 อาวุธ를 얻기 บน해서는 #t4310260# 1개 จำเป็น해.", 2400009, 0, ScriptMessageFlag.Scenario);
            if (!getPlayer().haveItem(4310260)) {
                self.sayReplacedNpc("검은 마법사가 남긴 흔적을 따라가면 #b#t4310260##k 습득할 수 있을거야.", 2400009, 0, ScriptMessageFlag.Scenario);
                self.sayReplacedNpc("참고로 #t4310260# ใช้한 성장은 지금ถึงและ는 อื่น 점이 많아!", 2400009, 0, ScriptMessageFlag.Scenario);
                self.sayReplacedNpc("#b제네시스 อาวุธ#k ก่อนหน้า 단계의 อาวุธ에서 잠재ความสามารถ, 에디셔널 잠재ความสามารถ, เพิ่มตัวเลือก 제외는 전수되지 않고 พิเศษ ตัวเลือก 가지고 있어.\r\n#r- สัปดาห์ประตู의 흔적 15% โจมตี력(힘) สัปดาห์ประตู서로 อัพเกรด เป็นไปได้ 횟수 ทั้งหมด ใช้\r\n- 스타포스 22성\r\n- Unique 잠재ความสามารถ และ Epic 에디셔널 잠재ความสามารถ มี(전수하지 않을 경우)\r\n-장착 시 <파괴의 얄다바오트>, <창조의 아이온> สกิล ได้รับ\r\n- สัปดาห์ประตู서 และ 스타포스 เสริมแรง 불가", 2400009, 0, ScriptMessageFlag.Scenario);
                self.sayReplacedNpc("และ 제네시스 อาวุธ를 완성한 후에는 อาวุธ 성장 버튼으로 ก่อนหน้า 단계และ 제네시스 อาวุธ를 오갈 수 있으니 제네시스 อาวุธ로 เสริมแรง하는 것을 고민하지 않아도 될거야.\r\n단, 교체하는데 힘이 มาก จำเป็น해서 한번 교체한 후 10นาที 동ใน은 교체할 수 없어.", 2400009, 0, ScriptMessageFlag.Scenario);
                self.sayReplacedNpc("열심히 노력해서 #b#t4310260##k 얻기를 바랄게.", 2400009, 0, ScriptMessageFlag.Scenario);
                //getPlayer().openInheritanceUpgrade(10, 4310260, 1);
            } else {
                self.sayReplacedNpc("검은 마법사의 힘이 담긴 #b#t4310260##k 구ทำ니!\r\n대단하구ฉัน 너!", 2400009, 0, ScriptMessageFlag.Scenario);
                self.sayReplacedNpc("#b#t4310260##k 이용 เรา를 #b제네시스 อาวุธ#k เสริมแรง할 수 있어!\r\nแต่ 지금ถึง의 เสริมแรงและ는 อื่น 점이 많아!", 2400009, 0, ScriptMessageFlag.Scenario);
                self.sayReplacedNpc("#b제네시스 อาวุธ#k ก่อนหน้า 단계의 อาวุธ에서 잠재ความสามารถ, 에디셔널 잠재ความสามารถ, เพิ่มตัวเลือก 제외는 전수되지 않고 พิเศษ ตัวเลือก 가지고 있어.\r\n#r- สัปดาห์ประตู의 흔적 15% โจมตี력(힘) สัปดาห์ประตู서로 อัพเกรด เป็นไปได้ 횟수 ทั้งหมด ใช้\r\n- 스타포스 22성\r\n- Unique 잠재ความสามารถ และ Epic 에디셔널 잠재ความสามารถ มี(전수하지 않을 경우)\r\n-장착 시 <파괴의 얄다바오트>, <창조의 아이온> สกิล ได้รับ\r\n- สัปดาห์ประตู서 และ 스타포스 เสริมแรง 불가", 2400009, 0, ScriptMessageFlag.Scenario);
                self.sayReplacedNpc("และ 제네시스 อาวุธ를 완성한 후에는 อาวุธ 성장 버튼으로 ก่อนหน้า 단계และ 제네시스 อาวุธ를 오갈 수 있으니 제네시스 อาวุธ로 เสริมแรง하는 것을 고민하지 않아도 될거야.\r\n단, 교체하는데 힘이 มาก จำเป็น해서 한번 교체한 후 10นาที 동ใน은 교체할 수 없어.", 2400009, 0, ScriptMessageFlag.Scenario);
                int v1 = self.askMenuReplacedNpc("#r잠재ความสามารถ#k 경우 네가 원한다면 สุ่ม한 #bUnique 잠재ความสามารถ#k 변화할거야. #bUnique 잠재ความสามารถ#k 변화하겠어?\r\n\r\n#b#L0#Unique 잠재ความสามารถ으로 เปลี่ยน한다.#l\r\n#L1#기존 잠재ความสามารถ을 เขา대로 유지한다.#l", 2400009, 0, ScriptMessageFlag.NpcReplacedByNpc);
                int v2 = self.askMenuReplacedNpc("#r에디셔널 잠재ความสามารถ#k 경우 네가 원한다면 สุ่ม한 #bEpic 에디셔널 잠재 ตัวเลือก#k 변화할거야. #bEpic 에디셔널 잠재ความสามารถ#k 변화하겠어?\r\n\r\n#b#L0#Epic 에디셔널 잠재ความสามารถ으로 เปลี่ยน한다.#l\r\n#L1#기존 에디셔널 잠재ความสามารถ을 เขา대로 유지한다.#l", 2400009, 0, ScriptMessageFlag.NpcReplacedByNpc);
                self.sayReplacedNpc("#bเพิ่มตัวเลือก#k เขา대로 전수되니 걱정하지마!", 2400009, 0, ScriptMessageFlag.Scenario);
                String v4 = "자! 이제 정말 ครั้งสุดท้าย이야!\r\n#t4310260# ใช้해 #b제네시스 라피스และ 라즐리#k 얻겠어?\r\n";
                v4 += "\r\n#r잠재ความสามารถ ตัวเลือก Unique รีเซ็ต 여부 : #e" + (v1 == 0 ? "รีเซ็ต" : "รีเซ็ต하지 않음") + "#n\r\n";
                v4 += "#r에디셔널 잠재ความสามารถ ตัวเลือก Epic รีเซ็ต 여부 : #e" + (v2 == 0 ? "รีเซ็ต" : "รีเซ็ต하지 않음") + "#n\r\n";
                v4 += "\r\n#b#L0#제네시스 라피스และ 라즐리를 얻는다.#l\r\n#L1#เขา만둔다.#l";
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
                Item alphaBefore = getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short) -20000);
                Item betaBefore = getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short) -20001);
                if (alphaBefore == null || betaBefore == null) {
                    self.sayReplacedNpc("알 수 없는 오류가 발생했어. 고객센터에 ประตู의해สัปดาห์겠어?", 2400009, 0, ScriptMessageFlag.NpcReplacedByNpc);
                    return;
                }

                // ปัจจุบัน อาวุธ บันทึก
                Equip alpha_copy = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short) -11).copy();
                Equip beta_copy = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short) -10).copy();


                getPlayer().getInventory(MapleInventoryType.EQUIPPED).removeItem((short ) -11);
                getPlayer().getInventory(MapleInventoryType.EQUIPPED).removeItem((short ) -10);

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
                getPlayer().updateOneInfo(40981, "last_change_time", String.valueOf(System.currentTimeMillis()));
            } else {
                self.sayReplacedNpc("ก่อนหน้า อาวุธ로 돌아가는데 많은 힘이 จำเป็น해. 아직 เวลา이 더 จำเป็น하니 ฉัน중에 다시 시도해줄래?", 2400009, 0, ScriptMessageFlag.NpcReplacedByNpc);
            }
        }
    }

    public void zero_reinvoke_weapon() {
        int curLv = getPlayer().getOneInfoQuestInteger(40981, "lv");

        self.sayReplacedNpc("#face2#우왓, 이거 완전히 가루가 버렸잖아? 너, 대체 ฉัน한테 무슨 짓을 한 거야? 이제 어떡하냐고? 어떡하긴 뭘 어떡해? อาวุธ가 깨졌으니 ทั้งหมด 게 끝, GAME OVER지! เขา동ใน 즐거웠어. เขา럼 ใน녕……", 2400009, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
        self.sayReplacedNpc("…라고  서운하겠지? 하하, 놀랐어? แต่ ฉัน도 이번엔 มาก 놀랐다고. ฉัน, 알고 보면 예민한 남자야. 소중하게 다뤄줘~", 2400009, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
        self.sayReplacedNpc("어ชา피 อาวุธ는 เรา를 담고 있는 เขา릇วัน 뿐, เรา가 เขา대로 남아มี면 อาวุธ는 เมื่อไหร่든지 되살릴 수 있어. 자자, เขา럼 เวลา의 힘을 모아서 이렇게……", 2400009, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);

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

        self.sayReplacedNpc("쨘! 어때. 감쪽같지? 응? เมื่อไหร่든지 재생이 เป็นไปได้하니까 마음놓고 เสริมแรง를 해도 되겠다고? เขา건…", 2400009, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
        self.sayReplacedNpc("#face2#…อย่างไร เขา런 생แต่ละ을 할 수 있어?! อาวุธ가 깨질 때 เรา도 만만찮게 충격을 받는단 말야! อาวุธ가 깨지는 바람에 얼MP 놀랐는데……", 2400010, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
        self.sayReplacedNpc("เขา건 라즐리 말이 맞아. 게다가 너무 강력한 ไอเท็ม เวลา의 힘으로도 되살리는데에 한계가 있어. หน้า으로는 조심해!", 2400009, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.NoEsc);
    }

    public void zero_egoequiptalk() {
        int r = Randomizer.rand(1, 34);
        if (r == 1) {
            self.sayReplacedNpc("내가 제วัน 좋아하는 거…? 으응… ฉัน는 딸기 케이크가 제วัน 좋아. ฉัน랑 ด้วยกัน 먹고 싶다면... 응, 너라면 ด้วยกัน 먹어도 좋아.",  2400010, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("ฉัน도, ฉัน도! 딸기 케이크 ฉัน도 좋아해!",  2400009, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
            self.sayReplacedNpc("#face0#싫어! ฉัน는 알파랑 둘이… 아, 아니 เขา럼 난 ใน 갈래.",  2400010, 0, ScriptMessageFlag.Scenario);
        } else if (r == 2) {
            self.sayReplacedNpc("라피스에 대해서 อย่างไร 생แต่ละ하냐고? 음… 덩치만 커다란 바보?",  2400010, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("#face2#너무해, 라즐리. ฉัน 상처받았다고.",  2400009, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
            self.sayReplacedNpc("앗, ฉัน기에 엄청 맛있어보이는 เนื้อ가!",  2400010, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("เนื้อ?! ที่ไหน? ที่ไหน?",  2400009, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
            self.sayReplacedNpc("흥, 역시 바보라니까.",  2400010, 0, ScriptMessageFlag.Scenario);
        } else if (r == 3) {
            self.sayReplacedNpc("#face1#เขา, เขา렇게 빤히 쳐다보면 어쩐지 창피하단 말야.",  2400010, 0, ScriptMessageFlag.Scenario);
        } else if (r == 4) {
            self.sayReplacedNpc("알파를 เลือก한 이유? เขา건… 벼, 별อื่น 이유 없어. เขา냥 ใครฉัน 골랐을 뿐이라구. 혹시 네가 마음에 들어서 เขา렇다는 착แต่ละ은 하지 말아줬으면 좋겠어!",  2400010, 0, ScriptMessageFlag.Scenario);
        } else if (r == 5) {
            self.sayReplacedNpc("#face1#ฉัน는 밤이 싫어. คนเดียว 있는 것만 เหมือนกัน 기นาที이 든단 말야. เขา래도 이젠 คนเดียว가 아니니까 괜찮아.",  2400010, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("เขา래เขา래, 내가 있으니까 걱정하지 말라구.",  2400009, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
            self.sayReplacedNpc("#face0#너한테 한 말 아니거든!",  2400010, 0, ScriptMessageFlag.Scenario);
        } else if (r == 6) {
            self.sayReplacedNpc("#face0#딱히 네가 좋아서 ด้วยกัน 있어สัปดาห์는 건 아니야. เขา냥 ฉัน는 내 의무를 다 있는 것 뿐이니까 신경쓰지 말아줬음 좋겠어.",  2400010, 0, ScriptMessageFlag.Scenario);
        } else if (r == 7) {
            self.sayReplacedNpc("알파에 대해서 อย่างไร 생แต่ละ하냐고? 어, อย่างไร 생แต่ละ하긴 뭘 อย่างไร 생แต่ละ해? 아, 알파 เหมือนกัน 건 นิดหน่อย도 좋아하지 않는다구!",  2400010, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("흐음?",  2400005, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
            self.sayReplacedNpc("#face1#เขา, เขา렇다고 싫어한다는 뜻은 아니니까……",  2400010, 0, ScriptMessageFlag.Scenario);
        } else if (r == 8) {
            self.sayReplacedNpc("#face0#내가 화난 것 같다고? เขา냥 날씨가 별로라서 เขา래. 굳이 네가 ฉัน에게 오랫동ใน 말을 걸어สัปดาห์지 않아서 เขา런 게 아니라구!",  2400010, 0, ScriptMessageFlag.Scenario);
        } else if (r == 9) {
            self.sayReplacedNpc("내가 제วัน 좋아하는 건… 당연히 เนื้อ지! 몸에 좋고 맛도 ดี เนื้อ!",  2400009, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("ฉัน도 เนื้อ 좋아해. 맛있어.",  2400006, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
            self.sayReplacedNpc("เขา럼 ฉัน랑 เนื้อ 먹으러 가자! 내가 완전 맛있게 구워줄게!",  2400009, 0, ScriptMessageFlag.Scenario);
        } else if (r == 10) {
            self.sayReplacedNpc("라즐리에 대해서 อย่างไร 생แต่ละ하냐고? 라즐리는… 정말 무서워. 화내면 마녀같다고. 봐, 지금도… 히익!",  2400009, 0, ScriptMessageFlag.Scenario);
        } else if (r == 11) {
            self.sayReplacedNpc("베타를 เลือก한 이유? เขา야 당연히… 예쁘니까! 이왕 สัปดาห์인으로 모실 거면 예쁜 여자가 좋잖아? 하하.",  2400009, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("내가 예뻐?",  2400006, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
            self.sayReplacedNpc("당연하지! 라즐리도 알파가 잘생겨서 เลือก한 거잖아.",  2400009, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("#face1#เขา, เขา런 거 아니거든!",  2400010, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
        } else if (r == 12) {
            self.sayReplacedNpc("라즐리 녀석, 사실 너를 꽤ฉัน 좋아하는 거 같아. อย่างไร 아느냐고? 원래 เรา는 하ฉัน였으니까 말하지 않아도 다 알지.",  2400009, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("#face1#쓸데없는 소리 하지마. เขา런 거 아니야!",  2400006, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
        } else if (r == 13) {
            self.sayReplacedNpc("ฉัน 제법 쓸만한 것 같지 않아? …아니라고 생แต่ละ 어쩔 수 ไม่มี.",  2400009, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("라피스, 힘내…",  2400006, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
            self.sayReplacedNpc("#face2#어, 어쩐지 เขา 말이 더 기운빠지게 만드는 것 เหมือนกัน데.",  2400009, 0, ScriptMessageFlag.Scenario);
        } else if (r == 14) {
            self.sayReplacedNpc("우울한 땐 먹는 게 최고지. และ구และ구!",  2400009, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("เขา럼 기นาที이 좋을 땐?",  2400006, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
            self.sayReplacedNpc("기นาที 좋을 때도 먹는 게 최고지. และ구และ구!",  2400009, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("เขา럼 슬픈 땐?",  2400006, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
            self.sayReplacedNpc("슬플 때도 먹는 게……",  2400009, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("결국 ต่อไป 먹는다는 거잖아!",  2400010, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
        } else if (r == 15) {
            self.sayReplacedNpc("무슨 생แต่ละ을  있냐고? 사실 ฉัน는 지금 아สัปดาห์ 심แต่ละ하게… …ใคร 생แต่ละ도 없는데?",  2400009, 0, ScriptMessageFlag.Scenario);
        } else if (r == 16) {
            self.sayReplacedNpc("다들 조용하니까 ฉัน라도 นาทีบน기를 살려야지. ฉัน는 นาทีบน기 메이커로써 ฉัน름의 사명감을 가지고 มี고! 단순히 심심해서 장난치는 게 아니야. …정말로!",  2400009, 0, ScriptMessageFlag.Scenario);
        } else if (r == 17) {
            self.sayReplacedNpc("บอส มอนสเตอร์ 잡으면 더 많은 wp ได้รับ할 수 있어. น้ำ론 잡아야 ได้รับ할 수 있지, 잡아야…",  2400009, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("#face0#1절만 하라고, 1절만! ",  2400005, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
        } else if (r == 18) {
            self.sayReplacedNpc("네가 성장해야 เรา도 진화할 수 있어. เขา러니까 몸에 좋고 맛도 ดี เนื้อ를 มาก 먹어야… 아, 이게 아닌가?",  2400009, 0, ScriptMessageFlag.Scenario);
        } else if (r == 19) {
            self.sayReplacedNpc("สัปดาห์ประตู서ฉัน 큐브를 ใช้ 대검และ 태도에 동시에 ใช้งาน된다고. 이게 바로 1+1!",  2400009, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("#face5#오, เขา거 괜찮은데?",  2400005, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
            self.sayReplacedNpc("대신 파괴도 1+1이지!",  2400009, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("#face6#……!!!",  2400005, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
        } else if (r == 20) {
            self.sayReplacedNpc("หนังสือ 읽는 거 좋아해? ฉัน는 หนังสือ만 펼치면 잠이 오던데… 혹시 신전 หนังสือ장에 만화หนังสือ은 없을까?",  2400009, 0, ScriptMessageFlag.Scenario);
        } else if (r == 21) {
            self.sayReplacedNpc("110, 120, 130, 140, 150, 170, 180เลเวล  อาวุธ ระดับ ขึ้น시킬 수 있어. น้ำ론 네가 원하지 않는다면 ฉัน중에 เลือก해도 좋아.",  2400009, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("뭔가 예전และ 미묘하게 อื่น 것 เหมือนกัน데?",  2400010, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
            self.sayReplacedNpc("너야말로 แนะนำ를 유도하는 말투인 것 เหมือนกัน데?",  2400009, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("...",  2400006, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
        } else if (r == 22) {
            self.sayReplacedNpc("잠재ความสามารถเปลี่ยน 버튼을 눌러봤어? 어떤 잠재ความสามารถ이 ใช้งาน될 지 내 심장이 두근거린다고!",  2400009, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("เขา러다가 ใน ดี 잠재ความสามารถ이 ฉัน오면 어떡해?",  2400010, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
            self.sayReplacedNpc("괜찮아, ฉัน는 매사에 긍정적인 남자니까! ดี 게 ฉัน올 때ถึง 돌리면 되지!",  2400009, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("#face7#결국 될대로 되라는 거잖아…",  2400005, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
        } else if (r == 23) {
            self.sayReplacedNpc("신관 중에 델로라는 녀석, 어린데 제법 똑똑하던걸? 전ประตู기술에 관해서 꽤ฉัน 해박한 지식을 갖고 있더라고.",  2400009, 0, ScriptMessageFlag.Scenario);
        } else if (r == 24) {
            self.sayReplacedNpc("WEAPON UI에서는 อาวุธ만 เสริมแรง할 수 있어. 여긴 เรา만의 공간이니까. ฉัน머지 ป้องกัน구ฉัน 액세서리는 원래처럼 อุปกรณ์창에서 เสริมแรง하라구. ",  2400010, 0, ScriptMessageFlag.Scenario);
        } else if (r == 25) {
            self.sayReplacedNpc("태เขา를 이용 좀 더 효율적인 전투가 เป็นไปได้해. 구, 굳이 ฉัน도 너희และ ด้วยกัน 싸우고 싶어서 เขา러는 건 아니니까 오해하진 말아줘.",  2400010, 0, ScriptMessageFlag.Scenario);
        } else if (r == 26) {
            self.sayReplacedNpc("럭키ไอเท็ม สัปดาห์ประตู서를 ใช้ 세트ไอเท็ม เอฟเฟกต์를 낼 수 มี던데. 사실 난 ที่ไหน에 소속되는 걸 เขา리 좋아하진 않지만 말야.",  2400010, 0, ScriptMessageFlag.Scenario);
        } else if (r == 27) {
            self.sayReplacedNpc("코인을 모아서 신관들에게 가져가면 좋아할 거야. 가난뱅이들에게는 꽤 가치가 있을 테니까.",  2400010, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("#face11#너 말이 너무 심ทำ?",  2400005, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
            self.sayReplacedNpc("#face2#ฉัน, 난 เขา런 게 아니라…… ",  2400010, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("#face10#하긴, 네 말도 틀린 건 아니지. 내가 가난뱅이인 건 사실이니까.",  2400005, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
            self.sayReplacedNpc("#face2#아냐, เขา런 게 아니라…… 몰라, 바보!",  2400010, 0, ScriptMessageFlag.Scenario);
        } else if (r == 28) {
            self.sayReplacedNpc("골드라벨 ฉัน이트 ป้องกัน구 세트라고 알아? 거울โลก บอส들이 레시피를 가지고 มี던데 구해서 만들어 보는 게 어때? เขา럴 리는 없지만 의외로 너한테 어울릴지도 모르니까, 흥.",  2400010, 0, ScriptMessageFlag.Scenario);
        } else if (r == 29) {
            self.sayReplacedNpc("MP포션은 맛없어. MP포션을 먹지 않아도 되는 건 정말 다행이야. ",  2400009, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("세상에 네가 싫어하는 อาหาร도 있었어?",  2400010, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
            self.sayReplacedNpc("#face2#당연하지! 날 대체 뭘로 생แต่ละ 있는 거야?",  2400009, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("먹을 거 좋아하는 바보.",  2400010, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
            self.sayReplacedNpc("#face2#너, 너무 솔직하잖아!",  2400009, 0, ScriptMessageFlag.Scenario);
        } else if (r == 30) {
            self.sayReplacedNpc("เรา는 당연히 두손อาวุธ지. 두손으로 공손히 모시라는 뜻이야.",  2400010, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("#face7#두손อาวุธ가 เขา런 의미였어?!",  2400005, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
        } else if (r == 31) {
            self.sayReplacedNpc("여신의 눈น้ำ을 모으면 ใหม่ สกิล 배울 수 있어. …แต่ 난 걔네랑 별로 ด้วยกัน 다니고 싶진 않단 말야.",  2400010, 0, ScriptMessageFlag.Scenario);
        } else if (r == 32) {
            self.sayReplacedNpc("너희, 닮긴 했지만 เขา래도 มาก อื่น걸. 헤어ฉัน 성형은 ด้วยกัน 쓸 수도 있지만 한 명씩도 ใช้ เป็นไปได้하니까 좀 더 개성을 추구하는 것도 ฉัน쁘지 않을지도?",  2400010, 0, ScriptMessageFlag.Scenario);
        } else if (r == 33) {
            self.sayReplacedNpc("#face1#엥? 라즐리랑 ระหว่าง가 ฉัน쁘냐고? 아니, เรา 제법 친한 ระหว่าง라고 생แต่ละ하는데. ใน เขา래?",  2400009, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("난 바보랑은 ใน 놀아.",  2400010, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
            self.sayReplacedNpc("#face0#봐, ฉัน랑 친ทำ잖아.",  2400009, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("#face7#전혀 เขา런 말이 아니잖아.",  2400005, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
        } else if (r == 34) {
            self.sayReplacedNpc("#face1#근데, wp 거울โลก에서만 얻을 수 มี고 내가 말했었ฉัน?",  2400009, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("#face7#뭐? 거울โลก에서 자유로워져도 얻을 수 없는거야?",  2400005, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
            self.sayReplacedNpc("wp 거울โลก에 흩어진 เวลา의 힘. 거울โลก에서만 얻을 수 있는게 당연한 거 아니야? 메이플 เดือน드에서는 군단장급의 บอส들만 wp 가지고 있어.",  2400010, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("เขา리고 아쉽게도 เนื้อ에는 wp 함유(?) 있지 않아.",  2400009, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
            self.sayReplacedNpc("아쉽다...",  2400006, 0, ScriptMessageFlag.Scenario);
            self.sayReplacedNpc("#face7#뭐가 아쉽다는 거야?",  2400005, 0, ScriptMessageFlag.Scenario, ScriptMessageFlag.FlipImage);
        }
    }
}
