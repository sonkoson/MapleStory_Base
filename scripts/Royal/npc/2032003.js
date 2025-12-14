var status = -1;

importPackage(Packages.constants);

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
        cm.sendNext("#fs 11#โชคดีจริงๆ ที่ท่านหาทางมาถึงที่นี่ได้..\r\nมีธุระอะไรใน #bDead Mine#k อันตรายแห่งนี้หรือ?");
    } else if (status == 1) {
        cm.sendNextPrevS("#fnArial##bบังเอิญว่า.. แถวนี้ท่านเห็น #fs14#Sergeant Charlie#fs12# บ้างไหม?#k", 2);
    } else if (status == 2) {
        cm.sendNextPrev("#fnArial#อ้อ! เจ้า.. ทึ่ม.. นั่น..\r\nเอ่อ.. ไม่สิ.. ท่านคนนั้นได้รับความช่วยเหลือจากข้า แล้วกลับไปก่อนแล้วล่ะ");
    } else if (status == 3) {
        cm.sendNextS("#fnArial##d(ให้ตายสิ.. ลำบากเปล่าแท้ๆ..)#k\r\n\r\nอ่า! ขอบคุณมาก งั้นข้าขอตัวกลับก่อนล่ะ", 2);
    } else if (status == 4) {
        cm.sendNextPrev("#fnArial#เดี๋ยวก่อน!! ที่นี่มันอันตราย ให้ข้าส่งท่านกลับไปดีกว่า!");
    } else if (status == 5) {
        cm.sendNextS("#fnArial##d(รีบกลับไปแจ้งข่าวดีกว่า..)#k\r\n\r\n#bเอ๊ะ!! ขอบคุณมากเลยที่ช่วย..!#k", 2);
    } else if (status == 6) {
        cm.sendYesNo("#fnArial#งั้น.. ขอให้พระเจ้าคุ้มครอง\r\nจะออกไปข้างนอกตอนนี้เลยไหม?");
    } else if (status == 7) {
        cm.warp(ServerConstants.TownMap, 0);
        cm.forceStartQuest(504);
        cm.dispose();
    }
}
