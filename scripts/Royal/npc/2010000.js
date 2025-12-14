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
        var john = "#fnArial#ชื่อปฏิบัติการ! ปฏิบัติการสำรวจเหมืองร้าง! วันนี้ก็ลุยกันเลย!\r\nว่าแต่ #bจ่าอีซี่#k หายไปไหนนะ?\r\n\r\n";
        if (!cm.getQuestStatus(504) == 1) {
            john += "#fs11##fUI/UIWindow2.img/UtilDlgEx/list1#\r\n#fnArial##L1##dปฏิบัติการสำรวจเหมืองร้างของจ่าชาร์ลี";
        } else {
            john += "#fs11##fUI/UIWindow2.img/UtilDlgEx/list3#\r\n#fnArial##L2##dปฏิบัติการสำรวจเหมืองร้างของจ่าชาร์ลี";
        }
        cm.sendSimple(john);
    } else if (status == 1) {
        if (selection == 2) {
            cm.sendOk("#fnArial##bจ่าอีซี่#k!!.. เจ้านั่น... อ่า! เอาเถอะ...\r\nนี่เป็นของตอบแทนเล็กน้อยจากข้า..\r\nรับไว้เถิด..!\r\n\\r\n#fUI/UIWindow2.img/QuestIcon/4/0#\r\n\r\n#i4021031# #bTwisted Time Essence#k #r500 ชิ้น#k");
            cm.gainItem(4021031, 500);
            cm.forfeitQuest(504);
            cm.forceStartQuest(503);
            cm.showEffect(false, "monsterPark/clear");
            cm.playSound(false, "Field.img/Party1/Clear");
            cm.dispose();
        } else {
            cm.sendNextS("#fnArial##bเฮ้อ.. ต้องมาลำบากเพราะตาลุงขี้สงสัยแท้ๆ..\r\nคนต่อไปคือ.. #fs14#จ่าชาร์ลี#fs12# งั้นหรือ..?", 2);
        }
    } else if (status == 2) {
        cm.sendNextS("#fnArial##d(อึ๋ย.. หน้าตาน่ากลัวชะมัด งานยากแน่ๆ..)#k\r\n\r\n#bสวัสดีครับ #eจ่าชาร์ลี#n! ข้าคือ.. นักผจญภัยที่.. มีค่าตัวสูงที่สุดในตอนนี้!\r\nข้าชื่อ #h # เป็นเกียรติที่ได้พบท่านครับ!#k", 2);
    } else if (status == 3) {
        cm.sendNextPrev("#fnArial#หือ!? ไม่เคยเห็นหน้าเจ้ามาก่อนเลยนี่!! หืมม..\r\nก็นะ.. ข้าไม่ค่อยชอบหน้าตาเจ้าเท่าไหร่ แต่จิตวิญญาณเจ้านี่ยอดเยี่ยมเลย!");
    } else if (status == 4) {
        cm.sendNextPrevS("#fnArial##d(หน้าตาข้าทำไม? ไม่สิ.. ใจเย็นไว้..)#k\r\n\r\n#bฮ่าฮ่า! ข้ามักจะได้ยินคนบอกว่าข้าหน้าตาตลกอยู่บ่อยๆ ครับ!\r\nว่าแต่ ได้ยินว่าท่าน #fs14#จ่าชาร์ลี#fs12# กำลังลำบากอยู่หรือครับ...?#k", 2);
    } else if (status == 5) {
        cm.sendNextPrev("#fnArial#หือ? เจ้ารู้ได้ยังไง? คือว่า.. #bจ่าอีซี่#k..\r\nเขาไปสำรวจเหมืองร้างแล้วยังไม่กลับมาเลย..\r\nในฐานะจ่า ข้าต้องตามหา #bจ่าอีซี่#k ให้เจอ! เป็นเรื่องใหญ่แล้ว...");
    } else if (status == 6) {
        cm.sendNextS("#fnArial##bถ้า.. อย่างนั้น... ข้าจะช่วยท่านเองครับ!!#k", 2);
    } else if (status == 7) {
        cm.sendYesNo("#fnArial#อืม.. ถึงหน้าตาจะดูไม่ได้เรื่อง.. มันอันตรายนะ แต่ถ้าเจ้าบอกว่าจะทำก็ช่วยไม่ได้..\r\nข้าจะอธิบายเกี่ยวกับ #bเหมืองร้าง#k ให้ฟัง.. มีทั้งหมด 2 ชั้น.. เจ้าแค่เข้าไปตามหา #bจ่าอีซี่#k ให้เจอแล้วกลับมาหาข้า..\r\nหลังจากนั้นข้าจะมอบรางวัลที่เหมาะสมให้\r\n\r\n#fUI/UIWindow2.img/QuestIcon/3/0#\r\n#i4021031# #bTwisted Time Essence#k #r500 ชิ้น#k\r\n\r\n#dงั้นเจ้าจะออกเดินทางไปเหมืองร้างตอนนี้เลยไหม??#k");
    } else if (status == 8) {
        cm.warp(280020000, 0);
        cm.sendOk("#fnArial#ตามหา #bจ่าอีซี่#k ให้เจอแล้วกลับมาหาข้านะ ฝากด้วยล่ะ #b#h ##k !!");
        cm.dispose();
    }
}
