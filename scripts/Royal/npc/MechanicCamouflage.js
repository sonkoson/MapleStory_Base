
/*

    * This script was created via the Simple NPC Auto-Production Script.

    * (Guardian Project Development Source Script)

    Created by Mojiyo.

    NPC ID : 1105008

    NPC Name : Checky

    NPC Map : Henesys : Rina's House (100000003)

    NPC Description : MISSINGNO


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
        talk = "#fs11#มีอะไรให้ช่วยไหม?\r\n\r\n"
        talk += "#L0##bฉันอยากเปลี่ยนสีลายพรางของ Metal Armor";
        cm.sendNext(talk);
    } else if (status == 1) {
        cm.sendYesNo("#fs11#คุณอยากเปลี่ยน#bสีลายพรางของ Metal Armor#k เหรอ?\r\nแค่จ่ายมา #e#r1 ล้าน Meso#n#k ฉันจะทาสี#bที่คุณต้องการ#kให้!");

    } else if (status == 2) {
        if (cm.getPlayer().getJob() >= 3500 && cm.getPlayer().getJob() <= 3512) {
            if (cm.getPlayer().getMeso() > 1000000) {
                talk = "#fs11#คุณต้องการ#bสีอะไร#k?\r\n#r(Meso จะถูกหักแม้ว่าคุณจะเลือกสีเดิม)\r\n\r\n#b"
                talk += "#L0#สีพื้นฐาน\r\n"
                talk += "#L2881#สีฟ้า\r\n"
                talk += "#L5745#สีแดง\r\n"
                talk += "#L3601#สีน้ำเงิน\r\n"
                talk += "#L4913#สีชมพู\r\n"
                talk += "#L4273#สีม่วง\r\n"
                talk += "#L417#สีส้ม\r\n"
                talk += "#L2049#สีเขียว\r\n"
                talk += "#L1025#สีเขียวอ่อน\r\n"
                cm.sendNext(talk);
            } else {
                cm.sendOk("#fs11#ดูเหมือนคุณจะมี Meso ไม่พอนะ?\r\nต้องใช้ #e#r1 ล้าน Meso#n#k ในการเปลี่ยนสีลายพราง")
                cm.dispose();
            }
        } else {
            cm.sendOk("#fs11#เฉพาะอาชีพ Mechanic เท่านั้นที่สามารถใช้ได้");
            cm.dispose();
        }

    } else if (status == 3) {
        cm.sendOk("#fs11#ทาสีเสร็จเรียบร้อย เจ๋งไปเลย!");
        cm.gainMeso(-1000000);
        cm.getPlayer().dropMessage(5, "เปลี่ยนสีลายพราง Metal Armor ของ Mechanic แล้ว")
        cm.getPlayer().setKeyValue(19752, "hiddenpiece", selection);
        cm.dispose();
    }
}
