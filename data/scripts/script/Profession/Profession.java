package script.Profession;

import constants.GameConstants;
import network.models.CField;
import objects.item.MapleInventoryManipulator;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.users.skills.Skill;
import objects.users.skills.SkillEntry;
import objects.users.skills.SkillFactory;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

import java.util.*;

public class Profession extends ScriptEngineNPC {

    public void himmel() {
        self.say("전ประตู기술에 대해 궁금ทำ.. 내가 간단하게 알려สัปดาห์도록 함세. .지금  หมู่บ้าน에는 #b약วินาที채บ้าน, 채광, อุปกรณ์ สร้าง, 장신구สร้าง, 연금술#k 이렇게 총 5명의 장인이 있네.");
        self.say("채บ้าน은 약วินาที 채บ้านและ 채광 2가지가  스타첼และ 노붐을 통해 2가지 채บ้าน 기술을 ทั้งหมด 배울 수 있네.");
        self.say("สร้าง은 อุปกรณ์ สร้าง, 장신구 สร้าง, 연금술 3가지가  에이센, 멜츠, 카리엔을 통해 3가지 중 하ฉัน만 เลือก해서 배울 수 있네.");
        self.sayOk("단, อุปกรณ์ สร้างและ 장신구 สร้าง을 บน해서는 채광 기술이 จำเป็น, 연금술을 배우기 บน해서는 채บ้าน 기술이 있어야 하지.");
    }

    public void gatherTuto() {
        initNPC(MapleLifeFactory.getNPC(9031008));
        int[] mapList = new int[]{910001005, 910001006, 910001003, 910001004, 910001008, 910001007, 910001010, 910001009};
        StringBuilder text = new StringBuilder("ถัดไป 광산และ 농장 중에서 어느 곳으로 ย้าย하겠ฉัน?\r\n");

        for (int i = 0; i < mapList.length; i++) {
            text.append("#b#L" + i + "##m" + mapList[i] + "##k ย้าย하겠.\r\n");
        }

        int v0 = self.askMenu(text.toString());

        if (v0 >= 0 && v0 < mapList.length) {
/*            int itemID;
            if (v0 > 3) {
                itemID = 4001570 + v0 - 5;
            } else {
                itemID = 4001480 + v0;
            }
            if (getPlayer().haveItem(itemID, 1, false, true)) {
                getPlayer().removeItem(itemID, -1);*/
            getPlayer().timeMoveMap(910001000, mapList[v0], 10 * 60);
//            } else {
            //              self.sayOk("#b#i" + itemID + "##z" + itemID + "##k ไอเท็ม จำเป็นทำ네.");
            //        }
        }
    }

    public void herbalism() {
        int skillID = 92000000;
        int v0 = -1;
        if (getPlayer().getProfessionLevel(skillID) <= 0) {
            v0 = self.askMenu("ใน녕ทำ. อะไร을 도และ드릴까요?\r\n#L0##b#e약วินาที 채บ้าน#n 대한 อธิบาย을 듣는다.#l\r\n#L1##e약วินาที 채บ้าน#n 배운다.#k#l");
        } else {
            v0 = self.askMenu("ใน녕ทำ. อะไร을 도และ드릴까요?\r\n#L2##b#e약วินาที 채บ้าน#n เลเวล 올린다.#l\r\n#L3##b#t4022023# แลกเปลี่ยน한다.#k#l");
        }

        switch (v0) {
            case 0: {
                self.sayOk("약วินาที 채บ้าน은 필드 곳곳에 있는 약วินาที를 채บ้าน 하는 สกิล. 이렇게 채บ้าน한 약วินาที를 #p9031007# ขาย하는 오วัน병에 담아 제련하게  อุปกรณ์,장신구,연금술 등에 จำเป็น한 재료가 .");
                break;
            }
            case 1: {
                if (getPlayer().getQuestStatus(3194) != 2) {
                    self.sayOk("약วินาที채บ้าน을 배우려면 #b힘멜#k님을 찾아가서 전ประตู기술에 대한 강의를 들으셔야 해요. หมู่บ้าน 입구에서 힘멜님을 만날 수 있답니다.");
                } else if (getPlayer().getProfessionLevel(skillID) <= 0) {
                    if (1 == self.askYesNo("#b약วินาที 채บ้าน#k 배웁니다. 비용은 #b5000 Meso#k. 정말 배우시หรือไม่?\r\n#b")) {
                        getPlayer().changeProfessionLevelExp(skillID, 1, 0, (byte) 10);
                        self.sayOk("좋아. 약วินาที채บ้าน을 สำเร็จ적으로 익혔.  숙련도가 다 채워야지만 เลเวล 올릴 수 있으니 다시 찾아มา.");
                    } else {
                        self.sayOk("신중하신 นาที이군요. 좋아요. 충นาที히 더 생แต่ละ해 보시고 다시และ สัปดาห์세요.");
                    }
                }
                break;
            }
            case 2: {
                if (getPlayer().addProfessionLevel(skillID)) {
                    self.sayOk("채บ้าน เลเวล " + getPlayer().getProfessionLevel(skillID) + " 되었어요. 감성도 เพิ่ม " + ((int) Math.pow(2, getPlayer().getProfessionLevel(skillID))) + "만큼 올려드렸으니 ยืนยัน해 ดู.");
                } else {
                    self.sayOk("아직 숙련도를 ทั้งหมด 채우진 못하셨네요. 숙련도를 ทั้งหมด 채우지 않으면 เลเวล업을 할 수 없답니다.");
                }
                break;
            }
            case 3: {
                int itemQty = (int) (getPlayer().getItemQuantity(4022023, false) / 100);
                int number = self.askNumber("#t2028066# กี่ 개를 원하시ฉัน요? \r\n#b#t4022023# 100개#k #i2028066:##b#t2028066# 1개#k แลกเปลี่ยน해 .", itemQty, 1, itemQty);

                if (MapleInventoryManipulator.checkSpace(getPlayer().getClient(), 2028066, number, "")) {
                    getPlayer().removeItem(4022023, itemQty * -100);
                    getPlayer().gainItem(2028066, number);
                    self.sayOk("#t4022023# เมื่อไหร่든지 แลกเปลี่ยน해 .");
                } else {
                    self.sayOk("들고 있는 짐이 너무 많군요? ใช้창을 1칸 이상 비운 후 시도해 สัปดาห์세요.");
                }
                break;
            }
        }
    }

    public void mining() {
        int skillID = 92010000;
        int v0 = -1;
        if (getPlayer().getProfessionLevel(skillID) <= 0) {
            v0 = self.askMenu("เขา래. 채광의 달인인  #b노붐#k님에게 원하는것이 อะไร인가?\r\n#L0##b#e채광#n 대한 อธิบาย을 듣는다.#l\r\n#L1##e채광#n 배운다.#k#l");
        } else {
            v0 = self.askMenu("เขา래. 채광의 달인인  #b노붐#k님에게 원하는것이 อะไร인가?\r\n#L2##b#e채광#n เลเวล 올린다.#l\r\n#L3##b#t4011010# แลกเปลี่ยน한다.#k#l");
        }

        switch (v0) {
            case 0: {
                self.sayOk("채광은 필드 곳곳에 있는  광석을 채บ้าน 하는 สกิล이야. 이렇게 채บ้าน한 원석을 #p9031006# ขาย하는 거푸บ้าน에 담아 제련하게  อุปกรณ์, 장신구, 연금술 등에 จำเป็น한 재료가 되지.");
                break;
            }
            case 1: {
                if (getPlayer().getQuestStatus(3194) != 2) {
                    self.sayOk("채광을 배우려면 #b힘멜#k님을 찾아가서 인사를 드려야하지~ หมู่บ้าน 입구에서 힘멜님을 만날 수 있어.");
                } else if (getPlayer().getProfessionLevel(skillID) <= 0) {
                    if (1 == self.askYesNo("#b채광#k 배우게 된다. 정말 이대로 배울거지? เล็กน้อย의 비용이 드는데 #b5000 Meso#k야. เขา 정도 돈은 있는거지?.\r\n#b")) {
                        getPlayer().changeProfessionLevelExp(skillID, 1, 0, (byte) 10);
                        self.sayOk("좋아. 채광의 기วินาที 지식을 너에게 전수했어. 숙련도를 채우면 ถัดไป เลเวล  ใหม่ 걸 배울 수 있으니 숙련도를 채우면 다시 찾아และ 줘.");
                    } else {
                        self.sayOk("신중한 녀석이군. 좋아. 충นาที히 더 생แต่ละ해 보시고 다시오도록 해.");
                    }
                }
                break;
            }
            case 2: {
                if (getPlayer().addProfessionLevel(skillID)) {
                    self.sayOk("채광 เลเวล " + getPlayer().getProfessionLevel(skillID) + " 되었어. 선น้ำ로 의지를 " + ((int) Math.pow(2, getPlayer().getProfessionLevel(skillID))) + "만큼 올려สัปดาห์었으니 ยืนยัน해 보는게 어때?");
                } else {
                    self.sayOk("아직 숙련도를 ทั้งหมด 채우진 못했군. ปัจจุบัน เลเวล에서의 숙련도를 끝ถึง 올리고 다시 오도록 해.");
                }
                break;
            }
            case 3: {
                int itemQty = (int) (getPlayer().getItemQuantity(4011010, false) / 100);
                int number = self.askNumber("#t2028067# กี่ 개를 원하시ฉัน요? \r\n#b#t4011010# 100개#k #i2028067:##b#t2028067# 1개#k แลกเปลี่ยน해 .", itemQty, 1, itemQty);

                if (MapleInventoryManipulator.checkSpace(getPlayer().getClient(), 2028067, number, "")) {
                    getPlayer().removeItem(4011010, itemQty * -100);
                    getPlayer().gainItem(2028067, number);
                    self.sayOk("#t4011010# เมื่อไหร่든지 แลกเปลี่ยน해 .");
                } else {
                    self.sayOk("들고 있는 짐이 너무 많군요? ใช้창을 1칸 이상 비운 후 시도해 สัปดาห์세요.");
                }
                break;
            }
        }
    }

    public void equip_product() {
        int skillID = 92020000;
        int parentSkillID = 92010000;
        int v0 = -1;
        if (getPlayer().getProfessionLevel(skillID) <= 0) {
            v0 = self.askMenu("เขา래. อุปกรณ์ สร้าง의 달인,  #b에이센#k님에게 원하는게 뭐지?\r\n#L0##b#eอุปกรณ์ สร้าง#n 대한 อธิบาย을 듣는다.#l\r\n#L1##eอุปกรณ์ สร้าง#n기술을 배운다.#k#l");
        } else {
            v0 = self.askMenu("เขา래. อุปกรณ์ สร้าง의 달인,  #b에이센#k님에게 원하는게 뭐지?\r\n#L2##b#eอุปกรณ์ สร้าง#n เลเวล 올린다.#l\r\n#L3#อุปกรณ์ สร้าง 기술을 วินาที기화한다.#k#l");
        }

        switch (v0) {
            case 0: {
                self.sayOk("อุปกรณ์ สร้าง은 채광으로 제련한 광น้ำและ 보석을  거대한 용광로에 녹여 너에게 จำเป็น한 유용한 ป้องกัน구และ อาวุธ를 만드는 기술이야. 지금ถึง 볼 수 없었던 อาวุธและ ป้องกัน구도  에이센님께 배우면 만들 수 있지.");
                break;
            }
            case 1: {
                if (getPlayer().getQuestStatus(3194) != 2) {
                    self.sayOk("อุปกรณ์ สร้าง에 관심이 있ฉัน보군. 배우고 싶다면 먼ฉัน #b힘멜#k님을 찾아가서 인사를 드려야하지~ หมู่บ้าน 입구에서 힘멜님을 만날 수 있을걸세.");
                } else if (getPlayer().getProfessionLevel(parentSkillID) <= 0) {
                    self.sayOk("채광을 배우지 않는 คน에게 อุปกรณ์ สร้าง을 가르쳐สัปดาห์지 않고 있어. 재료가 ไม่มี고 끈기 있게 하지 못할테니...  ข้าง에 있는 채광의 마스터 #b노붐#k님께 먼ฉัน 채광을 배워오게.");
                } else if (getPlayer().getProfessionLevel(skillID) <= 0) {
                    if (1 == self.askYesNo("#bอุปกรณ์ สร้าง#k 배우고 싶다고? 배우고 싶다면 수강료를 내야지...#b 5000Meso#k 인데... 정말 배울건가?\r\n#b")) {
                        getPlayer().changeProfessionLevelExp(skillID, 1, 0, (byte) 10);
                        self.sayOk("좋아. อุปกรณ์ สร้าง을 สำเร็จ적으로 익혔어.  숙련도가 다 ชา게 อุปกรณ์ สร้าง의 เลเวล 올릴 수 있으니 다시 찾아และ เลเวล 올리는 것 잊지말고.");
                    } else {
                        self.sayOk("신중한 녀석이군. 좋아. 충นาที히 더 생แต่ละ해 보시고 다시오도록 해.");
                    }
                }
                break;
            }
            case 2: {
                if (getPlayer().addProfessionLevel(skillID)) {
                    if (getPlayer().getProfessionLevel(skillID) == 11) {
                        self.sayOk("좋아. อุปกรณ์สร้าง เลเวล 장인이 되었다네. 선น้ำ로 손재สัปดาห์를 " + ((int) Math.pow(2, getPlayer().getProfessionLevel(skillID))) + "만큼 올려สัปดาห์었으니 한번 ยืนยัน해 보라고.");
                    } else if (getPlayer().getProfessionLevel(skillID) == 12) {
                        self.sayOk("좋아. อุปกรณ์สร้าง เลเวล 명장이 되었다네. 선น้ำ로 손재สัปดาห์를 " + ((int) Math.pow(2, getPlayer().getProfessionLevel(skillID))) + "만큼 올려สัปดาห์었으니 한번 ยืนยัน해 보라고.");
                    } else {
                        self.sayOk("좋아. อุปกรณ์สร้าง เลเวล " + getPlayer().getProfessionLevel(skillID) + " 되었다네. 선น้ำ로 손재สัปดาห์를 " + ((int) Math.pow(2, getPlayer().getProfessionLevel(skillID))) + "만큼 올려สัปดาห์었으니 한번 ยืนยัน해 보라고.");
                    }
                } else {
                    self.sayOk("아직 숙련도를 ทั้งหมด 채우진 못했군. ปัจจุบัน เลเวล에서의 숙련도를 끝ถึง 올리고 다시 오도록 해.");
                }
                break;
            }
            case 3: {
                if (1 == self.askYesNo("อุปกรณ์ สร้าง을 배우지 않은 สถานะ로 วินาที기화 한다네. 지금 ถึง 쌓은 เลเวล 숙련도가 ทั้งหมด 사라 질텐데, 정말 วินาที기화를 할건가?")) {
                    getPlayer().changeProfessionLevelExp(skillID, 0, 0, (byte) 10);
                    self.sayOk("อุปกรณ์ สร้าง 기술을 ทั้งหมด วินาที기화 했다네. 다시 배우고 싶으면 เมื่อไหร่든 다시 오라고.");
                } else {
                    self.sayOk("เขา래. วินาที기화는 신중하게 충นาที히 생แต่ละ해 보고 결정하는 것이 좋지.");
                }
                break;
            }
        }
    }

    public void acc_product() {
        int skillID = 92030000;
        int parentSkillID = 92010000;
        int v0 = -1;
        if (getPlayer().getProfessionLevel(skillID) <= 0) {
            v0 = self.askMenu("우아한 #b멜츠#k님의 고상한 취미 생활은 보석 감상이지. 반짝반짝ถนน는 보석들을 보고 있노라면 เวลา 가는 줄 모르겠어. 으흠~ 너도 관심있는거야? เขา래보이지 않는걸?\r\n#L0##b#e장신구สร้าง#n 대한 อธิบาย을 듣는다.#l\r\n#L1##e장신구สร้าง#n기술을 배운다.#k#l");
        } else {
            v0 = self.askMenu("우아한 #b멜츠#k님의 고상한 취미 생활은 보석 감상이지. 반짝반짝ถนน는 보석들을 보고 있노라면 เวลา 가는 줄 모르겠어. 으흠~ 너도 관심있는거야? เขา래보이지 않는걸?\r\n#L2##b#e장신구สร้าง#n เลเวล 올린다.#l\r\n#L3#장신구สร้าง 기술을 วินาที기화한다.#k#l");
        }

        switch (v0) {
            case 0: {
                self.sayOk("장신구สร้าง에 대해서 알려줄려면 우선 보석의 아름다움에 대한 근본적인 것에서จาก 출발해야แต่ 짧게 이야기하지. 밤새도록 이야기해도 หมวก르니... \r\n장신구สร้าง은 간단해. เขา냥 다듬어지지 않는 보석และ 광น้ำ을 아름답게 다듬고 장신구로 만들어 원래의 빛을 발하게 해สัปดาห์는거야. เขา และ정에서 숨겨진 힘이 발휘 ฉัน를 더 아름답고 강하게 만들 수 있지.");
                break;
            }
            case 1: {
                if (getPlayer().getQuestStatus(3194) != 2) {
                    self.sayOk("장신구สร้าง을 배우고 싶다구?? 으흠~? 전ประตู기술을 배우고 싶다면 먼ฉัน #b힘멜#k님을 찾아가서 인사를 드려야하지~ หมู่บ้าน 입구쪽에 계시니까 찾아가보겠어?");
                } else if (getPlayer().getProfessionLevel(parentSkillID) <= 0) {
                    self.sayOk("아유~ 어쩌ฉัน? 장신구สร้าง을  싶다면 먼ฉัน 채광을 배워야 해. 장신구를 สร้าง하기 บน해선 แต่ละ종 금속และ 보석이 จำเป็น하니까~ 왼쪽으로 가면 오동통한 버섯ด้วยกัน 생긴 #b노붐#k이란 채광의 마스터가 있으니까 가보는게 어때?");
                } else if (getPlayer().getProfessionLevel(skillID) <= 0) {
                    if (1 == self.askYesNo("정말 #b장신구สร้าง#k 배울거지?..수강료는 #b5000Meso#k야.\r\n#b")) {
                        getPlayer().changeProfessionLevelExp(skillID, 1, 0, (byte) 10);
                        self.sayOk("좋아!구우웃!เขา레이트!!자~ 너에게 ฉัน의 장신구สร้าง 지식을 ทั้งหมด 전수했어. 숙련도가 다 ชา게 장신구สร้าง의 เลเวล 올릴 수 있으니 다시 찾아และ เลเวล 올리는 것 잊지마.");
                    } else {
                        self.sayOk("아닛!!! 장신구สร้าง은 정말 ดี 기술이란 말이야. 더 생แต่ละ해보겠다니 너무 신중한 것 아냐?");
                    }
                }
                break;
            }
            case 2: {
                if (getPlayer().addProfessionLevel(skillID)) {
                    if (getPlayer().getProfessionLevel(skillID) == 11) {
                        self.sayOk("เขา레이트! 장신구สร้าง เลเวล 장인이 되었다구! เพิ่ม 손재สัปดาห์를 " + ((int) Math.pow(2, getPlayer().getProfessionLevel(skillID))) + "만큼 올려สัปดาห์었으니 ยืนยัน해 보는게 어때?");
                    } else if (getPlayer().getProfessionLevel(skillID) == 12) {
                        self.sayOk("เขา레이트! 장신구สร้าง เลเวล 명장이 되었다구! เพิ่ม 손재สัปดาห์를 " + ((int) Math.pow(2, getPlayer().getProfessionLevel(skillID))) + "만큼 올려สัปดาห์었으니 ยืนยัน해 보는게 어때?");
                    } else {
                        self.sayOk("เขา레이트! 장신구สร้าง เลเวล " + getPlayer().getProfessionLevel(skillID) + " 되었다구! เพิ่ม 손재สัปดาห์를 " + ((int) Math.pow(2, getPlayer().getProfessionLevel(skillID))) + "만큼 올려สัปดาห์었으니 ยืนยัน해 보는게 어때?");
                    }
                } else {
                    self.sayOk("아직 숙련도를 ทั้งหมด 채우진 못했군. ปัจจุบัน เลเวล에서의 숙련도를 끝ถึง 올리고 다시 오도록 해.");
                }
                break;
            }
            case 3: {
                if (1 == self.askYesNo("장신구สร้าง을 지우고 싶은거야? 질린거야? 지금ถึง 네가 힘들게 쌓은 เลเวล 숙련도.. 노력และ 돈이.. ทั้งหมด 한순간의 น้ำ거품이 될텐데... 정말 วินาที기화 할거야?")) {
                    getPlayer().changeProfessionLevelExp(skillID, 0, 0, (byte) 10);
                    self.sayOk("ทั้งหมด วินาที기화했어... 냉정하기는. 다시 배우고 싶으면 เมื่อไหร่든 찾아และ.");
                } else {
                    self.sayOk("เขา래. วินาที기화는 신중하게 충นาที히 생แต่ละ해 보고 결정해야지.");
                }
                break;
            }
        }
    }

    public void alchemy() {
        int skillID = 92040000;
        int parentSkillID = 92000000;
        int v0 = -1;
        if (getPlayer().getProfessionLevel(skillID) <= 0) {
            v0 = self.askMenu("ใน녕ทำ. 혹시 연금술에 관심있으세요?\r\n#L0##b#e연금술#n 대한 อธิบาย을 듣는다.#l\r\n#L1##e연금술#n 배운다.#k#l");
        } else {
            v0 = self.askMenu("ใน녕ทำ. 혹시 연금술에 관심있으세요?\r\n#L2##b#e연금술#n เลเวล 올린다.#l\r\n#L3#연금술을 วินาที기화한다.#k#l");
        }

        switch (v0) {
            case 0: {
                self.sayOk("연금술은 허브의 오วัน을 이용 다양한 น้ำ약을 만드는 기술이랍니다. HP MP 회복하는 น้ำ약จาก คุณ을 강하게 할 수 있는 다양한 น้ำ약도 만들 수 있어요. 지금ถึง 체험하지 못했던 신기한 น้ำ약도 당연히 만들 수 있구요.");
                break;
            }
            case 1: {
                if (getPlayer().getQuestStatus(3194) != 2) {
                    self.sayOk("죄송แต่, 우선 힘멜님께 전ประตู기술에 대한 강의를 들으셔야 알려드릴 수 있답니다. 힘멜님께 가보시겠어요?");
                } else if (getPlayer().getProfessionLevel(parentSkillID) <= 0) {
                    self.sayOk("연금술은 약วินาที채บ้าน을 배운 후에 가르쳐 드릴 수 있답니다. 약วินาที채บ้าน은 오른쪽으로 가시면 솥에 열심히 약วินาที를 넣고 있는 약วินาที채บ้าน의 마스터 #b스타첼#k에게 배울 수 있어요.");
                } else if (getPlayer().getProfessionLevel(skillID) <= 0) {
                    if (1 == self.askYesNo("정말 #b연금술#k 배우시겠어요? #b5000Meso#k 수강료가 จำเป็น하답니다. \r\n#b")) {
                        getPlayer().changeProfessionLevelExp(skillID, 1, 0, (byte) 10);
                        self.sayOk("자아~ 연금술에 대한 พื้นฐาน적인 지식을 알려드렸어요. เขา리고 숙련도가 다 ชา게 연금술의 เลเวล 올릴 수 있으니 꼭 ฉัน에게 다시 찾아และสัปดาห์세요. ใหม่ 지식을 알려드릴게요.");
                    } else {
                        self.sayOk("전ประตู기술을 배울 때는 원래 신중해야하죠. เขา만큼 많은 เวลาและ 노력이 จำเป็น하니까요. 충นาที히 생แต่ละ해보시고 마음이 결정 다시 찾아มา.");
                    }
                }
                break;
            }
            case 2: {
                if (getPlayer().addProfessionLevel(skillID)) {
                    if (getPlayer().getProfessionLevel(skillID) == 11) {
                        self.sayOk("좋아요. 연금술 เลเวล 장인이 되었. เพิ่ม 손재สัปดาห์를 " + ((int) Math.pow(2, getPlayer().getProfessionLevel(skillID))) + "만큼 올려드렸으니 한 번 ยืนยัน해 ดู.");
                    } else if (getPlayer().getProfessionLevel(skillID) == 12) {
                        self.sayOk("좋아요. 연금술 เลเวล 명장이 되었. เพิ่ม 손재สัปดาห์를 " + ((int) Math.pow(2, getPlayer().getProfessionLevel(skillID))) + "만큼 올려드렸으니 한 번 ยืนยัน해 ดู.");
                    } else {
                        self.sayOk("좋아요. 연금술 เลเวล " + getPlayer().getProfessionLevel(skillID) + " 되었. เพิ่ม 손재สัปดาห์를 " + ((int) Math.pow(2, getPlayer().getProfessionLevel(skillID))) + "만큼 올려드렸으니 한 번 ยืนยัน해 ดู.");
                    }
                } else {
                    self.sayOk("아직 숙련도를 ทั้งหมด 채우지 못하신 것 같. ปัจจุบัน เลเวล에서의 숙련도를 끝ถึง 올리고 다시  สัปดาห์세요.");
                }
                break;
            }
            case 3: {
                if (1 == self.askYesNo("연금술을 배우지 않은 สถานะ로 วินาที기화 . วินาที기화를 하게  지금 ถึง 쌓은 เลเวล 숙련도가 ทั้งหมด 사라지게 돼요. 정말 วินาที기화를 하실건가요?")) {
                    getPlayer().changeProfessionLevelExp(skillID, 0, 0, (byte) 10);
                    self.sayOk("연금술 기술을 ทั้งหมด วินาที기화 แล้ว. 다시 배우고 싶으면 เมื่อไหร่든 다시  สัปดาห์세요.");
                } else {
                    self.sayOk("네. วินาที기화는 신중하게 충นาที히 생แต่ละ해 보고 결정 โปรด.");
                }
                break;
            }
        }
    }

    public void open_herb() {
        int skillID = 92000000;
        open_meister_object(skillID, "약วินาที채บ้าน을 배우지 않아 ใช้할 수 없.");
    }

    public void open_mining() {
        int skillID = 92010000;
        open_meister_object(skillID, "채광을 배우지 않아 ใช้할 수 없.");
    }

    public void open_equipP() {
        int skillID = 92020000;
        open_meister_object(skillID, "อุปกรณ์ สร้าง을 배우지 않아 ใช้할 수 없.");
    }

    public void open_accP() {
        int skillID = 92030000;
        open_meister_object(skillID, "장신구สร้าง을 배우지 않아 ใช้할 수 없.");
    }

    public void open_alchemy() {
        int skillID = 92040000;
        open_meister_object(skillID, "연금술을 배우지 않아 ใช้할 수 없.");
    }

    private void open_meister_object(int skillID, String context) {
        if (getPlayer().getProfessionLevel(skillID) >= 0) {
            getPlayer().send(CField.UIPacket.openUIOption(0x2A, 0, 0, 0));
        } else {
            getPlayer().dropMessage(5, context);
        }
    }
}
