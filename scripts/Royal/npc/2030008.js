function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }
    if (status == 0) {
        cm.sendSimple("ก็..... ได้. ดูเหมือนพวกเจ้าจะมีคุณสมบัติเพียงพอ. จะทำอะไรดีล่ะ?\r\n#b" +
            "#L0#ออกเดินทางไปสำรวจถ้ำเหมืองร้าง#l\r\n" +
            "#L1#สำรวจดันเจี้ยน Zakum#l\r\n" +
            "#L2#รับเครื่องบรรณาการเพื่อถวายแด่ Zakum#l\r\n" +
            "#L3#เดินทางไปยัง El Nath#l");
    } else if (status == 1) {
        sT = selection;
        if (selection == 0) {
            if (cm.getPlayerCount(280010000) >= 1) {
                cm.sendNext("มีปาร์ตี้อื่นกำลังท้าทายดันเจี้ยนนั้นอยู่ โปรดลองใหม่อีกครั้งในภายหลัง");
            } else {
                cm.warp(280010000);
            }
            cm.dispose();
            return;
        } else if (selection == 1) {
            cm.sendNext("ดี! จากนี้ไปเจ้าจะถูกส่งไปยังแผนที่ที่มีอุปสรรคมากมาย ขอให้โชคดี!");
        } else if (selection == 2) {
            cm.sendSimple("ต้องการรับเครื่องบรรณาการเพื่อถวายแด่ Zakum ตัวไหน?\r\n#b"
                //+ "#L0#Easy Zakum#l\r\n"
                + "#L1#Normal/Chaos Zakum#l\r\n");
        } else {
            cm.sendNext("งั้นข้าจะส่งเจ้าไปที่ El Nath");
        }
    } else if (status == 2) {
        sT2 = selection;
        if (sT == 1) {
            cm.warp(280020000);
            cm.dispose();
            return;
        } else if (sT == 2) {
            if (selection == 0) {
                if (cm.itemQuantity(4001796) >= 1) {
                    cm.sendOk("เจ้ามี #b#t4001796##k เครื่องบรรณาการของ Easy Zakum อยู่แล้วนี่.. ใช้หมดแล้วค่อยมาคุยกับข้าใหม่");
                    cm.dispose();
                    return;
                } else {
                    cm.sendNext("ต้องการเครื่องบรรณาการเพื่อถวายแด่ Easy Zakum สินะ..");
                }
            } else {
                if (cm.itemQuantity(4001017) >= 1) {
                    cm.sendOk("เจ้ามี #b#t4001017##k เครื่องบรรณาการของ Zakum อยู่แล้วนี่.. ใช้หมดแล้วค่อยมาคุยกับข้าใหม่");
                    cm.dispose();
                    return;
                } else {
                    cm.sendNext("ต้องการเครื่องบรรณาการเพื่อถวายแด่ Zakum สินะ..");
                }
            }
        } else {
            cm.warp(211000000);
            cm.dispose();
        }
    } else if (status == 3) {
        if (sT2 == 0) {
            cm.sendNextPrev("ทว่า #b#t4001796##k ที่จำเป็นสำหรับการเรียก Zakum นั้นข้ามีอยู่เยอะ งั้นข้าจะให้เจ้าเลยละกัน");
            if (!cm.haveItem(4001796)) {
                cm.gainItem(4001796, 1);
            }
        } else {
            cm.sendNextPrev("ทว่า #b#t4001017##k ที่จำเป็นสำหรับการเรียก Zakum นั้นข้ามีอยู่เยอะ งั้นข้าจะให้เจ้าเลยละกัน");
            if (!cm.haveItem(4001017)) {
                cm.gainItem(4001017, 10);
            }
        }
    } else if (status == 4) {
        if (sT2 == 0) {
            cm.sendNextPrev("ทิ้งเจ้านี่ลงที่แท่นบูชาของ Easy Zakum ก็พอ");
        } else {
            cm.sendNextPrev("ทิ้งเจ้านี่ลงที่แท่นบูชาของ Zakum ก็พอ");
        }
        cm.dispose();
        return;
    }
}