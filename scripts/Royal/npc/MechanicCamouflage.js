
/*

    * 단문엔피시 자동제작 스크립트를 통해 만들어진 스크립트 입니다.

    * (Guardian Project Development Source Script)

    뭐지요 에 의해 만들어 졌습니다.

    엔피시아이디 : 1105008

    엔피시 이름 : 체키

    엔피시가 있는 맵 : 헤네시스 : 리나의 집 (100000003)

    엔피시 설명 : MISSINGNO


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
