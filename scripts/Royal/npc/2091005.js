importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);
importPackage(java.text);
importPackage(java.awt);


var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 0 && type == 3) {
        cm.sendNext("อะไรกัน เดี๋ยวก็ไปเดี๋ยวก็ไม่ไป! แต่เดี๋ยวก็คงร้องไห้อยากกลับบ้านแน่ๆ");
        cm.dispose();
    }
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;

        if (status == 0) {
            if (cm.getPlayer().getMap().checkDojangClear()) {
                cm.sendYesNo("อะไรนะ? ล้มคู่ต่อสู้แล้วจะไปไหน? แค่ผ่านไปด่านต่อไปก็จบแล้วนี่? นึกธุระด่วนขึ้นมาได้รึไง?");
            } else {
                cm.sendYesNo("จะยอมแพ้แค่นี้เหรอ? กะแล้วเชียวว่าต้องเสียใจทีหลัง!");
            }
        } else if (status == 1) {
            cm.warp(925020002);
            cm.dispose();
        }
    }
}