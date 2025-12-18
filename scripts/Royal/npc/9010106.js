importPackage(java.lang);

var status = -1;
var s = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        status--;
        cm.dispose();
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        if (cm.inBoss()) {
            cm.getPlayer().dropMessage(5, "ไม่สามารถใช้งานได้ระหว่างบอส");
            cm.dispose();
            return;
        }

        var v = cm.getPlayer().getOneInfoQuest(18771, "rank");
        if (v != null && !v.isEmpty()) {
            s = 0;
            var v0 = "#fs11#วันนี้เป็นวันดีสำหรับการไปล่ามังกรเลยนะ!\r\nให้ช่วยเรื่อง #bMaple Union#k อะไรดีคะ?\r\n\r\n#L0##b<ดูข้อมูล Maple Union ของฉัน>#l\r\n#L1##b<อัพเกรดระดับ Maple Union>#l\r\n#L2##b<ฟังคำอธิบายเกี่ยวกับ Maple Union>#k#l";
            cm.sendSimple(v0);
        } else {
            if (cm.getPlayer().canCreateMapleUnion()) {
                s = 1;
                cm.sendNext("#fs11#ได้เวลาสร้าง Maple Union แล้วนะ มาสร้างกันเลย!");
            } else {
                cm.sendNext("#fs11#Maple Union สามารถสร้างได้เมื่อ #b#eUnion Level 500 ขึ้นไป#n#k และ #r#eมีตัวละคร 3 ตัวขึ้นไป#n#k ตอนนี้ยังสร้างไม่ได้ค่ะ");
                cm.dispose();
            }
        }
    } else if (status == 1) {
        if (s == 1) {
            if (cm.getPlayer().firstLoadMapleUnion(true)) {
                cm.getPlayer().updateOneInfo(500629, "point", "0");
                cm.getPlayer().updateOneInfo(18771, "rank", "101");
                cm.sendNext("#fs11#สร้าง Maple Union สำเร็จแล้ว! สามารถแก้ไขสมาชิกกองโจมตีได้โดยเลือก #e#b<Maple Union>#k#n จาก #e#r<Menu>#k#n ค่ะ");
                cm.dispose();
            } else {
                cm.sendNext("#fs11#เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุ สร้าง Union ไม่สำเร็จ ลองใหม่อีกทีภายหลังนะคะ");
                cm.dispose();
            }
        } else if (s == 0) {
            if (selection == 0) {
                cm.sendNext("#fs11#มาบอกข้อมูล #eMaple Union#n ของคุณให้ฟังนะ\r\n\r\n#eระดับ Maple Union: #n#b#e<" + cm.getPlayer().getUnionLevelName() + ">#n#k\r\n#eUnion Level: #n#b#e<" + cm.getPlayer().getUnionLevel() + ">#n#k\r\n#eจำนวนตัวละคร Union: #n#b#e<" + cm.getPlayer().getUnionCharacterCount() + ">#n#k\r\n#eสมาชิกกองโจมตี:#n#b#e<" + cm.getPlayer().getUnionActive() + " / " + cm.getPlayer().getUnionActiveMax() + " คน>#n#k");
                cm.dispose();
            } else if (selection == 1) {
                if (cm.getPlayer().getMapleUnion().rank == 505) {
                    cm.sendNext("#fs11#ระดับ Union ของคุณสูงสุดแล้วค่ะ");
                    cm.dispose();
                    return;
                }
                var nextName = cm.getPlayer().getNextUnionLevelName();
                var nextMax = cm.getPlayer().getNextUnionActiveMax();
                if (nextMax != -1) {
                    cm.sendYesNo("#fs11#อยาก #eอัพเกรด Maple Union#n ใช่ไหมคะ?\r\n\r\n#eระดับปัจจุบัน: #n#b#e<" + cm.getPlayer().getUnionLevelName() + ">#n#k\r\n#eระดับถัดไป: #n#b#e<" + nextName + ">#n#k\r\n#eสมาชิกกองโจมตีเพิ่มเมื่ออัพเกรด:#n #b#e<" + cm.getPlayer().getUnionActiveMax() + "→" + nextMax + " คน>#n#k\r\n\r\nต้องมีคุณสมบัติตามนี้ถึงจะอัพเกรดได้:\r\n\r\n#e<Union Level> #r#e" + cm.getPlayer().getNextUnionNeedLevel() + " ขึ้นไป#n#k #n\r\n#e<ค่าใช้จ่าย Coin> #b#e#t4310229# " + cm.getPlayer().getNextUnionNeedCoin() + " ชิ้น#n#k\r\n\r\n จะ #eอัพเกรด#n Maple Union ตอนนี้เลยไหมคะ?");
                } else {
                    cm.sendNext("#fs11#ไม่สามารถอัพเกรดระดับ Union ได้แล้วค่ะ");
                    cm.dispose();
                }
            } else if (selection == 2) {
                cm.dispose();
                cm.openNpc(9010106, "mapleunion_help");
            }
        }
    } else if (status == 2) {
        if (s == 0) {
            var ret = cm.getPlayer().levelUpUnion();
            if (ret == -1) {
                cm.sendNext("#fs11#เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุ อัพเกรดไม่สำเร็จ ลองใหม่อีกทีภายหลังนะคะ");
                cm.dispose();
            } else if (ret == 0) {

                cm.addCustomLog(4, "[Union] Rank Up: " + cm.getPlayer().getUnionLevelName() + "(" + cm.getPlayer().getMapleUnion().rank + ") - " + cm.getPlayer().getUnionLevel() + "");
                cm.getPlayer().setSaveFlag(cm.getPlayer().getSaveFlag() | 1048576 | 2097152 | 4194304); // Maple_union_data, Maple_Union_Group, Maple_Union_Raiders
                cm.getPlayer().saveToDB(false, false);
                cm.sendNext("#fs11#ปรบมือ~!\r\n#eระดับ Maple Union#n เพิ่มขึ้นแล้ว! ตอนนี้สามารถใช้สมาชิกกองโจมตีเพิ่มขึ้นเพื่อเติบโตเร็วขึ้นได้แล้ว!\r\n\r\n#eระดับใหม่: #n#b#e<" + cm.getPlayer().getUnionLevelName() + ">#n#k\r\n#eสมาชิกกองโจมตีที่ใช้ได้:#n #b#e" + cm.getPlayer().getUnionActiveMax() + "#n#k\r\n\r\nงั้นก็เติบโตต่อไปจนถึงระดับถัดไปนะคะ!");
                cm.dispose();
            } else if (ret == 1) {
                cm.sendNext("#fs11#ไม่สามารถอัพเกรดระดับ Union ได้แล้วค่ะ");
                cm.dispose();
            } else if (ret == 2) {
                cm.sendNext("#fs11#Coin ไม่พอสำหรับอัพเกรดค่ะ ตรวจสอบ Coin ที่มีอีกทีนะ");
                cm.dispose();
            } else if (ret == 3) {
                cm.sendNext("#fs11#Union Level ไม่ถึงสำหรับระดับถัดไป ต้องเพิ่ม Union Level อีกนะคะ");
                cm.dispose();
            }
        }
    }
}