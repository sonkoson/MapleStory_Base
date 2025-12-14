/* guild creation npc */
var status = -1;
var sel;

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 0 && status == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1)
        status++;
    else
        status--;

    if (status == 0)
        cm.sendSimple("เจ้าต้องการสร้างกิลด์ใช่หรือไม่? หรือมาทำธุระเกี่ยวกับกิลด์? บอกข้ามาสิว่าเจ้าต้องการอะไร\r\n\r\n#b#L0#ข้าต้องการสร้างกิลด์#l\r\n#L1#ข้าต้องการยุบกิลด์#l\r\n#L2#ข้าต้องการเพิ่มจำนวนสมาชิกสูงสุด (สูงสุด 100 คน) [500,000 เมโซ]#l\r\n#L3#ข้าต้องการเพิ่มจำนวนสมาชิกสูงสุด (สูงสุด 200 คน) [2,000 GP]#l#k");
    else if (status == 1) {
        sel = selection;
        if (selection == 0) {
            if (cm.getPlayerStat("GID") > 0) {
                cm.sendOk("อืม... ดูเหมือนเจ้าจะมีกิลด์อยู่แล้วนะ?");
                cm.dispose();
            } else
                cm.sendYesNo("ค่าธรรมเนียมในการสร้างกิลด์คือ #b5,000,000 เมโซ#k เจ้าต้องการสร้างจริงๆ ใช่ไหม?")
        } else if (selection == 1) {
            if (cm.getPlayerStat("GID") <= 0 || cm.getPlayerStat("GRANK") != 1) {
                cm.sendOk("เฉพาะหัวหน้ากิลด์เท่านั้นที่สามารถยุบกิลด์ได้");
                cm.dispose();
            } else {
                cm.sendYesNo("เจ้าต้องการยุบกิลด์หรือ...? หากยุบตอนนี้จะไม่สามารถแก้ไขได้อีก... และ GP ที่สะสมมาทั้งหมดจะหายไป โปรดตัดสินใจอย่างรอบคอบ คิดดูอีกครั้งเถิด เจ้าต้องการยุบกิลด์จริงๆ ใช่ไหม?");
            }
        } else if (selection == 2) {
            if (cm.getPlayerStat("GID") <= 0 || cm.getPlayerStat("GRANK") != 1) {
                cm.sendOk("เฉพาะหัวหน้ากิลด์เท่านั้นที่สามารถเพิ่มจำนวนสมาชิกสูงสุดได้");
                cm.dispose();
            } else
                cm.sendYesNo("ค่าธรรมเนียมเพิ่มจำนวนสมาชิกคือ #b500,000#k เมโซ จะเพิ่มได้ทีละ 5 คน เจ้าต้องการเพิ่มจริงๆ ใช่ไหม?");
        } else if (selection == 3) {
            if (cm.getPlayerStat("GID") <= 0 || cm.getPlayerStat("GRANK") != 1) {
                cm.sendOk("เฉพาะหัวหน้ากิลด์เท่านั้นที่สามารถเพิ่มจำนวนสมาชิกสูงสุดได้");
                cm.dispose();
            } else
                cm.sendYesNo("ค่าธรรมเนียมเพิ่มจำนวนสมาชิกคือ #b2,000#k กิลด์พอยต์ (GP) จะเพิ่มได้ทีละ 5 คน เจ้าต้องการเพิ่มจริงๆ ใช่ไหม?");
        }
    } else if (status == 2) {
        if (sel == 0 && cm.getPlayerStat("GID") <= 0) {
            cm.createGuild();
            cm.dispose();
        } else if (sel == 1 && cm.getPlayerStat("GID") > 0 && cm.getPlayerStat("GRANK") == 1) {
            cm.disbandGuild();
            cm.dispose();
        } else if (sel == 2 && cm.getPlayerStat("GID") > 0 && cm.getPlayerStat("GRANK") == 1) {
            cm.increaseGuildCapacity(false);
            cm.dispose();
        } else if (sel == 3 && cm.getPlayerStat("GID") > 0 && cm.getPlayerStat("GRANK") == 1) {
            cm.increaseGuildCapacity(true);
            cm.dispose();
        }
    }
}