var status = 0;

function start() {
    status = -1;
    action(1, 1, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }
    if (status == 0) {
        cm.sendSimple("#fs11#ผู้กล้าเอ๋ย เจ้าต้องการทำสิ่งใด?\r\n\r\n#L0##bข้าต้องการเสริมพลังหรือสร้าง V Core#l\r\n#L1#เปล่า ไม่มีอะไร อากาศดีนะวันนี้");
    } else if (status == 1) {
        if (selection == 0) {
            cm.sendOk("#fs11#กรุณาเปิดระบบ V Matrix ผ่านหน้าต่างสกิล");
            cm.dispose();
        } else if (selection == 1) {
            cm.sendOk("#fs11#พื้นที่แถวนี้สภาพอากาศแปรปรวนตามกระแสของ Elda รักษาสุขภาพด้วยล่ะ");
            cm.dispose();
        }
    }
}