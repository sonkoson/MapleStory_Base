/*
    Mechanic Camouflage Color Change Script
    (Guardian Project Development Source Script)
    NPC ID: 1105008 (Checky)
*/

var status = -1;

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
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        var dialogue = "#fs11#มีธุระอะไรหรือเปล่า?\r\n\r\n";
        dialogue += "#L0##bฉันต้องการเปลี่ยนสี Mechanic จ้ะ";
        cm.sendNext(dialogue);
    } else if (status == 1) {
        cm.sendYesNo("#fs11##bต้องการเปลี่ยนสี Mechanic#k งั้นเหรอ?\r\nถ้ามี #e#r1,000,000 Meso#n#k ฉันสามารถเปลี่ยนให้ได้นะ จะเอาไหม?");

    } else if (status == 2) {
        if (cm.getPlayer().getJob() >= 3500 && cm.getPlayer().getJob() <= 3512) {
            if (cm.getPlayer().getMeso() >= 1000000) {
                var dialogue = "#fs11##bต้องการเปลี่ยนเป็นสีไหนล่ะ?\r\n#r(เมื่อเปลี่ยนแล้ว เงินจะถูกหักทันทีนะ)\r\n\r\n#b";
                dialogue += "#L0#สีพื้นฐาน\r\n";
                dialogue += "#L2881#สีฟ้า\r\n";
                dialogue += "#L5745#สีแดง\r\n";
                dialogue += "#L3601#สีน้ำเงิน\r\n";
                dialogue += "#L4913#สีดำ\r\n";
                dialogue += "#L4273#สีขาว\r\n"; // Guessing White/Special
                dialogue += "#L417#สีเหลือง\r\n";
                dialogue += "#L2049#สีเขียว\r\n";
                dialogue += "#L1025#สีม่วง\r\n";
                cm.sendNext(dialogue);
            } else {
                cm.sendOk("#fs11#เงินไม่พอนะ?\r\nการเปลี่ยนสีต้องใช้ #e#r1,000,000 Meso#n#k น่ะ");
                cm.dispose();
            }
        } else {
            cm.sendOk("#fs11#เฉพาะ Mechanic เท่านั้นที่สามารถใช้งานได้นะจ๊ะ");
            cm.dispose();
        }

    } else if (status == 3) {
        cm.sendOk("#fs11#เปลี่ยนสีเรียบร้อยแล้วจ้ะ ขอบคุณนะ!");
        cm.gainMeso(-1000000);
        // cm.getPlayer().dropMessage(5,"Mechanic Camouflage Color changed.");
        cm.getPlayer().setKeyValue(19752, "hiddenpiece", selection + ""); // Ensure string if needed, or int. Original was selection.
        cm.dispose();
    }
}



