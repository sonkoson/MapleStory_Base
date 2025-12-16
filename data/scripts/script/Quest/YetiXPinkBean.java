package script.Quest;

import constants.GameConstants;
import network.SendPacketOpcode;
import network.encode.PacketEncoder;
import network.models.CField;
import network.models.CWvsContext;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.item.Equip;
import objects.item.Item;
import objects.item.MapleInventoryType;
import objects.quest.MapleQuest;
import objects.users.MapleStat;
import objects.utils.Randomizer;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class YetiXPinkBean extends ScriptEngineNPC {

    public void q100564s() {
        int v = self.askMenu(
                "#b#e< Yeti x Pink Bean World >#k#n เปิดแล้ว!\r\n\r\n#L0#อยากสร้างตัวละคร #eYeti#n#l\r\n#L1#อยากสร้างตัวละคร #ePink Bean#n#l\r\n#L2#ขอรับ #ePrepared Spirit Pendant#n อีกครั้ง#l\r\n#L3#ขอรับ #eรางวัลปิดปรับปรุง maintenance#n#l\r\n#L4#อยากเปลี่ยนฉายากลับเป็นคูปองฉายา (Yeti, Pink Bean)#l");
        if (v == 0) {
            self.say("ปัจจุบันสามารถสร้างตัวละคร Yeti ได้ที่เมนูสร้างตัวละคร");
        } else if (v == 1) {
            self.say("ปัจจุบันสามารถสร้างตัวละคร Pink Bean ได้ที่เมนูสร้างตัวละคร");
        } else if (v == 2) {
            if (getPlayer().getQuestStatus(100568) == 2 && getPlayer().getOneInfoQuestInteger(100568, "fairy") == 0) {
                if (target.exchange(2633422, 2) > 0) {
                    getPlayer().updateOneInfo(100568, "fairy", "1");
                    self.sayOk("Prepared Spirit Pendant Coupon (14 วัน) x 2 ถูกออกให้ใหม่เรียบร้อยแล้ว");
                }
            } else {
                self.sayOk("คุณได้รับไปแล้วหรือไม่ได้อยู่ในเงื่อนไขที่จะรับใหม่ได้");
            }
        } else if (v == 3) {
            if (getPlayer().getOneInfoQuestInteger(1234699, "pReward") == 0) {
                if (target.exchange(2633201, 2) > 0) {
                    getPlayer().updateOneInfo(1234699, "pReward", "1");
                } else {
                    self.say("กรุณาทำช่องว่างในช่องเก็บของอย่างน้อย 2 ช่อง");
                }
            } else {
                self.say("คุณได้รับไปแล้ว ไม่สามารถรับซ้ำได้");
            }
        } else if (v == 4) {
            int vv = self.askMenu(
                    "#b#L0#ฉายา Pink Bean#k เปลี่ยนเป็น #rPink Bean Title Coupon#k#l\r\n#b#L1#ฉายา Yeti#k เปลี่ยนเป็น #rYeti Title Coupon#k#l");
            if (vv == 0) {
                if (target.exchange(3700287, -1, 2434030, 1) > 0) {
                    self.sayOk("เรียบร้อยแล้ว");
                } else {
                    self.sayOk("กรุณาตรวจสอบว่าคุณมีฉายา Pink Bean หรือไม่ และมีช่องว่างในช่องเก็บของเพียงพอหรือไม่");
                }
            } else if (vv == 1) {
                if (target.exchange(3700682, -1, 2633232, 1) > 0) {
                    self.sayOk("เรียบร้อยแล้ว");
                } else {
                    self.sayOk("กรุณาตรวจสอบว่าคุณมีฉายา Yeti หรือไม่ และมีช่องว่างในช่องเก็บของเพียงพอหรือไม่");
                }
            }
        }
    }

    public void q100565s() {
        if (getPlayer().getJob() == 13100) { // Pink Bean Script
            target.say("ที่นี่ที่ไหนเนี่ย?! น่าตื่นเต้นจัง?!");
            target.say("...ใจเย็นๆ ก่อน ต้องเรียกพลังกลับคืนมาก่อนที่จะไปเจอกับ Yeti อีกครั้ง");
            target.say("เผื่อไว้สำหรับเวลานี้ ฉันเตรียมรายการภารกิจ #e#b'Step Up'#k#n ไว้แล้ว!");
            target.say(
                    "ไม่ต้องตกใจที่มาอยู่โลกใหม่ ลองดูไอคอน #e#b'Yeti x Pink Bean Step Up'#k#n ที่แจ้งเตือนกิจกรรมทางด้านซ้ายของหน้าจอสิ! มาลองทำเป้าหมายที่เขียนไว้ทีละอย่างกันเถอะ #e#rถ้าทำสำเร็จพลังก็น่าจะกลับมานะ");
            target.say("จะแพ้เจ้า Yeti ไม่ได้หรอกนะ โชคดีที่เอา 'Power Elixir' มาด้วย มาพยายามกันเถอะ! เฮะๆ");
            target.say("เอาล่ะ ของที่จำเป็นก็เตรียมพร้อมแล้ว!\r\n#e#bเป้าหมายแรกคือ เลเวล 30!");
            getSc().flushSay();
            getQuest().forceComplete(getPlayer(), getNpc().getId());
            MapleQuest.getInstance(100566).forceStart(getPlayer(), getNpc().getId(), null);
            if (getPlayer().getLevel() >= 30) {
                getPlayer().updateInfoQuest(100565, "questNum=100565");
            }
            getPlayer().send(CField.UIPacket.openUI(1267));
        } else if (getPlayer().getJob() == 13500) { // Yeti Script
            target.say("Yeti, ต้องเรียกพลังกลับคืนมา\r\nจะแพ้ Pink Bean ไม่ได้...");
            target.say("เผื่อไว้สำหรับเวลานี้ เตรียมรายการภารกิจ #e#b'Step Up'#k#n ไว้แล้ว");
            target.say(
                    "Yeti, ไม่ตกใจ\r\nตรวจสอบเป้าหมาย #e#b'Yeti x Pink Bean Step Up'#k#n ที่แจ้งเตือนกิจกรรมทางด้านซ้ายของหน้าจอ แล้วทำไปทีละอย่าง\r\n#e#rแล้วจะเรียกพลังกลับคืนมา");
            target.say("จะแพ้เจ้า Yeti ไม่ได้หรอกนะ โชคดีที่เอา 'Power Elixir' มาด้วย มาพยายามกันเถอะ! เฮะๆ");
            target.say("จะแพ้ Pink Bean ไม่ได้\r\nYeti, เอาของจากที่บ้านมาด้วย ทำเต็มที่");
            target.say("Yeti, เตรียมของที่จำเป็นแล้ว\r\n#e#bเป้าหมายแรก เลเวล 30");
            getSc().flushSay();
            getQuest().forceComplete(getPlayer(), getNpc().getId());
            MapleQuest.getInstance(100566).forceStart(getPlayer(), getNpc().getId(), null);
            if (getPlayer().getLevel() >= 30) {
                getPlayer().updateInfoQuest(100565, "questNum=100565");
            }
            getPlayer().send(CField.UIPacket.openUI(1267));
        }
    }

    public void q100588s() {
        if (GameConstants.isYetiPinkBean(getPlayer().getJob())) {
            if (getPlayer().getOneInfo(100565, "questNum") != null
                    && getPlayer().getOneInfo(100565, "questNum").equals("finish")) {
                PacketEncoder p = new PacketEncoder();
                p.writeShort(SendPacketOpcode.SHOW_STATUS_INFO.getValue());
                p.write(14);
                p.writeInt(268);
                if (getPlayer().getJob() == 13100) {
                    p.writeMapleAsciiString("r4=1;");
                } else {
                    p.writeMapleAsciiString("r3=1;");
                }
                getPlayer().send(p.getPacket());
            }
            getPlayer().send(CField.UIPacket.openUI(1267));
        }
    }

    public void q100566e() {
        if (getPlayer().getLevel() >= 30) {
            if (target.exchange(2350000, 1) > 0) {
                int questId = 100566;
                clearStepUp(questId);
                sayStepUp(1, 2350000, "คอมโบคิล 300 สำเร็จ");
            } else {
                target.say("ดูเหมือนว่าช่องเก็บของจะไม่พอสำหรับรับรางวัล");
            }
        }
    }

    public void q100567e() {
        if (getPlayer().getOneInfo(100565, "questNum").equals("100566")) {
            if (target.exchange(4001832, 9000) > 0) {
                int questId = 100567;
                clearStepUp(questId);
                sayStepUp(2, 4001832, "เลเวล 50 สำเร็จ");
                if (getPlayer().getLevel() >= 50) {
                    getPlayer().updateOneInfo(100565, "questNum", "100567");
                }
            } else {
                target.say("ช่องเก็บของไม่พอสำหรับรับรางวัล");
            }
        }
    }

    public void q100568e() {
        if (getPlayer().getLevel() >= 50) {
            if (target.exchange(2437095, 1, 2633422, 2) > 0) {
                getPlayer().updateOneInfo(100568, "fairy", "1");
                int questId = 100568;
                clearStepUp(questId);
                sayStepUp(3, 2437095, "กำจัดมอนสเตอร์ในระดับเลเวล 999 ตัว");
            } else {
                target.say("ดูเหมือนว่าช่องเก็บของจะไม่พอสำหรับรับรางวัล จำเป็นต้องมีช่องว่างในช่องเก็บของใช้ 3 ช่อง");
            }
        }
    }

    public void q100569e() {
        if (getPlayer().getQuest(100569).getQuest().canComplete(getPlayer(), null)) {
            if (target.exchange(4001211, 1) > 0) {
                int questId = 100569;
                clearStepUp(questId);
                if (getPlayer().getLevel() >= 70) {
                    getPlayer().updateOneInfo(100565, "questNum", "100569");
                }
                sayStepUp(4, 4001211, "เลเวล 70 สำเร็จ");
            } else {
                target.say("ดูเหมือนว่าช่องเก็บของจะไม่พอสำหรับรับรางวัล");
            }
        }
    }

    public void q100570e() {
        if (getPlayer().getLevel() >= 70) {
            if (target.exchange(2048759, 200) > 0) {
                int questId = 100570;
                clearStepUp(questId);
                sayStepUp(5, 2048759, "ใช้ Rune 2 ครั้ง");
            } else {
                target.say("ดูเหมือนว่าช่องเก็บของจะไม่พอสำหรับรับรางวัล");
            }
        }
    }

    public void q100571e() {
        if (getPlayer().getOneInfoQuestInteger(100571, "RunAct") >= 2) {
            if (target.exchange(2048758, 100) > 0) {
                int questId = 100571;
                clearStepUp(questId);
                if (getPlayer().getLevel() >= 100) {
                    getPlayer().updateOneInfo(100565, "questNum", "100571");
                }
                sayStepUp(6, 2048758, "เลเวล 100 สำเร็จ");
            } else {
                target.say("ดูเหมือนว่าช่องเก็บของจะไม่พอสำหรับรับรางวัล");
            }
        }
    }

    public void q100572e() {
        if (getPlayer().getLevel() >= 100) {
            if (target.exchange(2633349, 20) > 0) {
                int questId = 100572;
                clearStepUp(questId);
                sayStepUp(7, 2633349, "เคลียร์ Monster Park 2 ครั้ง");
            } else {
                target.say("ดูเหมือนว่าช่องเก็บของจะไม่พอสำหรับรับรางวัล");
            }
        }
    }

    public void q100573e() {
        if (getPlayer().getOneInfoQuestInteger(100573, "monsterPark") >= 2) {
            if (target.exchange(2631822, 1) > 0) {
                int questId = 100573;
                clearStepUp(questId);
                if (getPlayer().getLevel() >= 150) {
                    getPlayer().updateOneInfo(100565, "questNum", "100573");
                }
                sayStepUp(8, 2631822, "เลเวล 150 สำเร็จ");
            } else {
                target.say("ดูเหมือนว่าช่องเก็บของจะไม่พอสำหรับรับรางวัล");
            }
        }
    }

    public void q100574e() {
        if (getPlayer().getLevel() >= 150) {
            if (target.exchange(2048766, 30) > 0) {
                int questId = 100574;
                clearStepUp(questId);
                sayStepUp(9, 2048766, "กำจัด Elite Monster/Champion 5 ตัว");
            } else {
                target.say("ดูเหมือนว่าช่องเก็บของจะไม่พอสำหรับรับรางวัล");
            }
        }
    }

    public void q100575e() {
        if (getPlayer().getQuest(100575).getQuest().canComplete(getPlayer(), null)) {
            if (target.exchange(2631528, 1) > 0) {
                int questId = 100575;
                clearStepUp(questId);
                int chuk = 0;
                for (Item item : getPlayer().getInventory(MapleInventoryType.EQUIPPED).list()) {
                    Equip equip = (Equip) item;
                    if (equip != null) {
                        chuk += equip.getCHUC();
                    }
                }
                if (chuk >= 50) {
                    getPlayer().updateOneInfo(100565, "questNum", "100575");
                }
                sayStepUp(10, 2434007, "ทำ Star Force 50 ขึ้นไป");
            } else {
                target.say("ดูเหมือนว่าช่องเก็บของจะไม่พอสำหรับรับรางวัล");
            }
        }
    }

    public void q100576e() {
        int chuk = 0;
        for (Item item : getPlayer().getInventory(MapleInventoryType.EQUIPPED).list()) {
            Equip equip = (Equip) item;
            if (equip != null) {
                chuk += equip.getCHUC();
            }
        }
        if (chuk >= 50) {
            if (target.exchange(5062010, 100) > 0) {
                int questId = 100576;
                clearStepUp(questId);
                sayStepUp(11, 5062010, "กำจัด Chaos Horntail");
            } else {
                target.say("ดูเหมือนว่าช่องเก็บของจะไม่พอสำหรับรับรางวัล");
            }
        }
    }

    public void q100577e() {
        if (getPlayer().getQuest(100577).getQuest().canComplete(getPlayer(), null)
                || (getPlayer().getOneInfo(3790, "mobid").equals("8810122")
                        && getPlayer().getOneInfoQuestInteger(3790, "mobDead") > 0)) {
            if (target.exchange(2434011, 1) > 0) {
                int questId = 100577;
                clearStepUp(questId);
                if (getPlayer().getLevel() >= 200) {
                    getPlayer().updateOneInfo(100565, "questNum", "100577");
                }
                sayStepUp(12, 2434011, "เลเวล 200 สำเร็จ");
            } else {
                target.say("ดูเหมือนว่าช่องเก็บของจะไม่พอสำหรับรับรางวัล");
            }
        }
    }

    public void q100578e() {
        if (getPlayer().getLevel() >= 200) {
            if (target.exchange(2633195, 1) > 0) {
                String start = "";
                String end = "";
                if (getPlayer().getJob() == 13100) {
                    start = "ยอดเยี่ยม! เคลียร์ #eภารกิจระดับ " + 13 + "#n สำเร็จแล้ว!";
                    end = "ทำภารกิจทั้งหมดสำเร็จแล้วสินะ! ดูเหมือนว่าพลังจะกลับมาพอสมควรแล้ว!\r\nเจ้า Yeti เดี๋ยวจะโดนสั่งสอนแน่! เฮะๆ!";
                } else if (getPlayer().getJob() == 13500) {
                    start = "Yeti! เคลียร์ #eภารกิจระดับ " + 13 + "#n สำเร็จแล้ว!";
                    end = "Yeti, ทำภารกิจทั้งหมดสำเร็จแล้ว!\r\nYeti, มีทั้งความน่ารักและความแข็งแกร่งครบถ้วน\r\nตอนนี้สามารถจัดการ Pink Bean ได้สบายๆ แล้ว";
                }
                target.say(start + "\r\n\r\n\r\n#e#b<รางวัลภารกิจ Step Up ระดับ " + 13 + " เสร็จสมบูรณ์>\r\nรางวัล");
                target.say(end);
                int questId = 100578;
                MapleQuest.getInstance(questId).forceComplete(getPlayer(), 9010000);
                getPlayer().updateOneInfo(100565, "stepNum", "finish");
                getPlayer().updateOneInfo(100565, "questNum", "100578");
            } else {
                target.say("ช่องเก็บของไม่พอสำหรับรับรางวัล");
            }
        }
    }

    public void yetiXpinkHeadTitle() {
        if (getPlayer().getOneInfo(100565, "questNum").equals("100578")) {
            int exItem = 2434030;
            if (getPlayer().getJob() == 13500)
                exItem = 2633232;
            if (target.exchange(exItem, 1) > 0) {
                PacketEncoder p = new PacketEncoder();
                p.writeShort(SendPacketOpcode.SHOW_STATUS_INFO.getValue());
                p.write(14);
                p.writeInt(268);
                if (getPlayer().getJob() == 13100) {
                    p.writeMapleAsciiString("r4=1;");
                } else {
                    p.writeMapleAsciiString("r3=1;");
                }
                getPlayer().send(p.getPacket());
                self.say(
                        "#bคุณทำภารกิจ Step Up ทั้งหมดเสร็จสิ้นแล้ว!#k\r\nเราจะมอบคูปองแลกเปลี่ยนฉายาที่สามารถย้ายภายใน World ได้เป็นรางวัล\r\n\r\n#e#b#i"
                                + exItem + "# #z" + exItem + "#",
                        ScriptMessageFlag.NpcReplacedByNpc);
                getPlayer().updateOneInfo(100565, "questNum", "finish");
            } else {
                target.say("ดูเหมือนว่าช่องเก็บของจะไม่พอสำหรับรับรางวัล");
            }
        }
    }

    private void sayStepUp(int d, int reward, String nextMission) {
        String start = "";
        String end = "";
        if (getPlayer().getJob() == 13100) {
            start = "ยอดเยี่ยม! เคลียร์ #eภารกิจระดับ " + d + "#n สำเร็จแล้ว!";
            end = " อาศัยจังหวะนี้ไปต่อยังระดับถัดไปเลยดีไหม?\r\nต่อไปก็คืออันนี้นี่เอง!\r\n\r\n";
        } else if (getPlayer().getJob() == 13500) {
            start = "Yeti! เคลียร์ #eภารกิจระดับ " + d + "#n สำเร็จแล้ว!\r\nมีคุณสมบัติของผู้มาก่อนกาล";
            end = "Yeti, กำลังเรียกพลังกลับคืนมา ไม่หยุดแค่นี้\r\nทำภารกิจถัดไปต่อเลยทันที\r\n\r\n";
        }
        target.say(start + "\r\n\r\n\r\n#e#b<รางวัลภารกิจ Step Up ระดับ " + d + " เสร็จสมบูรณ์>\r\n\r\n" + "#i" + reward
                + "# #z" + reward + "#");
        target.say(end + "#b#e<ภารกิจ Step Up ระดับ " + (d + 1) + ">\r\n#k- " + nextMission);
    }

    private void clearStepUp(int questId) {
        MapleQuest.getInstance(questId).forceComplete(getPlayer(), 9010000);
        MapleQuest.getInstance(questId + 1).forceStart(getPlayer(), 9010000, "");
        getPlayer().updateOneInfo(100565, "stepNum", String.valueOf(questId - 100565));
        getPlayer().updateOneInfo(100565, "questNum", " ");
    }

    ////////// Pink Bean Reward Item Script
    public void consume_2631822() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (target.exchange(1012478, 1, 1032241, 1, 1113149, 1, 1122150, 1, 1182087, 1, 2631822, -1) > 0) {

        } else {
            target.say("ดูเหมือนว่าช่องเก็บของจะไม่พอสำหรับรับรางวัล");
        }
    }

    public void consume_2631528() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() > 1) {
            int[] rewards00 = new int[] { 1302361, 1312218, 1322270, 1402273, 1412193, 1422201, 1432231, 1442289 };
            int[] rewards01 = new int[] { 1482236, 1532162 };
            String r = "กรุณาเลือกอาวุธ#b\r\n\r\n";
            if (getPlayer().getJob() == 13100) {
                for (int i = 0; i < rewards00.length; i++) {
                    r += "#L" + i + "# #i" + rewards00[i] + "# #z" + rewards00[i] + "# #l\r\n";
                }
                int a = self.askMenu(r);
                if (a >= 0) {
                    exchangePinkBeanSupportEquip(rewards00[a]);
                }
            } else if (getPlayer().getJob() == 13500) {
                for (int i = 0; i < rewards01.length; i++) {
                    r += "#L" + i + "# #i" + rewards01[i] + "# #z" + rewards01[i] + "#\r\n";
                }
                int a = self.askMenu(r);
                if (a >= 0) {
                    exchangePinkBeanSupportEquip(rewards01[a]);
                }
            }
            target.exchange(2631528, -1);
        } else {
            target.say("ดูเหมือนว่าช่องเก็บของจะไม่พอสำหรับรับรางวัล");
        }
    }

    public void consume_2434011() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() > 1) {
            if (target.exchange(2434011, -1) > 0) {
                exchangePinkBeanSupportEquip(1022144);
            }
        } else {
            target.say("ดูเหมือนว่าช่องเก็บของจะไม่พอสำหรับรับรางวัล");
        }
    }

    public void consume_2633195() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        int[][] reward = new int[][] { { 2633222, 20 }, { 2633223, 10 } };
        int v = self.askMenu("กรุณาเลือกอย่างใดอย่างหนึ่ง\r\n\r\n#b#L0# #i" + reward[0][0] + "# #z" + reward[0][0]
                + "# " + reward[0][1] + " ชิ้น\r\n#L1# #i" + reward[1][0] + "# #z" + reward[1][0] + "# " + reward[1][1]
                + " ชิ้น");
        if (v >= 0 && target.exchange(reward[v][0], reward[v][1], 2633195, -1) > 0) {

        }
    }

    public void consume_2434030() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (target.exchange(2434030, -1, 3700287, 1) > 0) {

        }
    }

    public void consume_2633232() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (target.exchange(2633232, -1, 3700682, 1) > 0) {

        }
    }

    public void consume_2633349() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (GameConstants.isYetiPinkBean(getPlayer().getJob())) {
            self.say("Pink Bean และ Yeti ไม่สามารถใช้ Extreme Growth Potion ได้");
            return;
        }
        String percent = "";
        if (getPlayer().getLevel() >= 141 && getPlayer().getLevel() < 200) {
            List<Integer> levelUp = new ArrayList<>();
            for (int i = 1; i <= GameConstants.extreamPotion()[getPlayer().getLevel() - 141].length; i++) {
                for (int a = 0; a < GameConstants.extreamPotion()[getPlayer().getLevel() - 141][i - 1]; a++) {
                    levelUp.add(i);
                }
                percent += "- เลเวล " + i + " ขึ้นไป : "
                        + GameConstants.extreamPotion()[getPlayer().getLevel() - 141][i - 1] + "%\r\n";
            }
            if (1 == self.askYesNo(
                    "#r#eExtreme Growth Potion#k#n ต้องการใช้ตอนนี้หรือไม่?\r\n\r\n※ เมื่อใช้จะมีโอกาส #e#bสุ่ม#n#k #b#eเลเวลอัป 1 ~ 10 เลเวล#k#n,　 ยิ่งเลเวลตัวละครสูง โอกาสก็จะยิ่งลดลง\r\n\r\n<เลเวลตัวละคร #b"
                            + getPlayer().getLevel() + "#k ใช้>\r\n" + percent)) {
                Collections.shuffle(levelUp);
                int plus = levelUp.get(Randomizer.nextInt(levelUp.size()));
                for (int i = 0; i < plus; i++) {
                    getPlayer().levelUp();
                }
                self.sayOk("เติบโต #e#r" + plus + " เลเวล#k#n ด้วย Extreme Growth Potion!", ScriptMessageFlag.NoEsc);
                target.exchange(2633349, -1);
            }
        } else {
            getPlayer().gainExp(571115568.0d, true, false, false);
            self.sayOk("ได้รับ EXP 571115568 จาก Extreme Growth Potion", ScriptMessageFlag.NoEsc);
            target.exchange(2633349, -1);
        }
    }

    public void consume_2633222() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (getPlayer().getLevel() >= 200 && getPlayer().getLevel() < 210) {
            if (1 == self.askYesNo("#r#e< Yeti x Pink Bean Growth Potion 1 > #k#n ต้องการใช้ตอนนี้หรือไม่?")) {
                if (target.exchange(2633222, -1) > 0) {
                    getPlayer().levelUp();
                    getPlayer().setExp(0);
                    HashMap<MapleStat, Long> eee = new HashMap<>();
                    eee.put(MapleStat.EXP, 0L);
                    getPlayer().send(CWvsContext.updatePlayerStats(eee, getPlayer()));
                }
            }
        } else if (getPlayer().getLevel() >= 210) {
            if (1 == self.askYesNo("#r#e< Yeti x Pink Bean Growth Potion 1 > #k#n ต้องการใช้ตอนนี้หรือไม่?")) {
                if (target.exchange(2633222, -1) > 0) {
                    getPlayer().gainExp(6120258214.0d, true, false, false);
                }
            }
        } else {
            self.say("เลเวลต่ำเกินไป ไม่สามารถใช้ได้");
        }
    }

    public void consume_2633223() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (getPlayer().getLevel() >= 210 && getPlayer().getLevel() < 220) {
            if (1 == self.askYesNo("#r#e< Yeti x Pink Bean Growth Potion 2 > #k#n ต้องการใช้ตอนนี้หรือไม่?")) {
                if (target.exchange(2633223, -1) > 0) {
                    getPlayer().levelUp();
                    getPlayer().setExp(0);
                    HashMap<MapleStat, Long> eee = new HashMap<>();
                    eee.put(MapleStat.EXP, 0L);
                    getPlayer().send(CWvsContext.updatePlayerStats(eee, getPlayer()));
                }
            }
        } else if (getPlayer().getLevel() >= 220) {
            if (1 == self.askYesNo("#r#e< Yeti x Pink Bean Growth Potion 2 > #k#n ต้องการใช้ตอนนี้หรือไม่?")) {
                if (target.exchange(2633223, -1) > 0) {
                    getPlayer().gainExp(27279159629.0d, true, false, false);
                }
            }
        } else {
            self.say("เลเวลต่ำเกินไป ไม่สามารถใช้ได้");
        }
    }

    public void q100785s() {
        self.say(
                "สวัสดี! #b#e#h0##k#n.\r\n\r\nฉันสามารถแลกเปลี่ยน #b #i3700287# #z3700287##k #b#i3700682# #z3700682##k ฉายาทั้ง 2 อย่าง\r\nเป็น #rYeti x Pink Bean Title Coupon#k ให้ได้นะ\r\n\r\n#e [ฉายาที่จะได้รับ]\r\n#b#n #i2633243# #z2633243#",
                ScriptMessageFlag.NpcReplacedByNpc);
        if (target.exchange(3700287, -1, 3700682, -1, 2633243, 1) > 0) {
            self.sayOk(
                    "มอบคูปองแลกเปลี่ยนฉายาให้เรียบร้อยแล้ว กรุณาตรวจสอบช่องเก็บของ\r\n\r\n#fUI/UIWindow.img/Quest/reward#\r\n\r\n#b#e #i2633243# #z2633243#",
                    ScriptMessageFlag.NpcReplacedByNpc);
        } else {
            self.sayOk("ต้องมีฉายาทั้ง 2 อย่างจึงจะสามารถแลกเปลี่ยนได้", ScriptMessageFlag.NpcReplacedByNpc);
        }
    }

    public void consume_2633243() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (target.exchange(2633243, -1, 3700683, 1) > 0) {
            self.sayOk("แลกเปลี่ยนเรียบร้อยแล้ว");
        }
    }

    public void consume_2633422() {
        initNPC(MapleLifeFactory.getNPC(9010000));
        if (getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() >= 1) {
            if (target.exchange(2633422, -1) > 0) {
                exchangeSupportEquipPeriod(1122334, 0, 0, 14);
                self.sayOk("แลกเปลี่ยนเรียบร้อยแล้ว");
            }
        }
    }
}
