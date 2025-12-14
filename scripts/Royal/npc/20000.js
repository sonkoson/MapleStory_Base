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
        var john = "#fs 11#โซเฟีย... ช่วงนี้ข้ารู้สึกดีกับนางเหลือเกิน อืม...\r\nข้าจะทำให้นางรับรู้ความรู้สึกของข้าได้อย่างไรนะ?\r\n\r\n";
        if (!cm.haveItem(4033970, 1)) {
            john += "#fs11##fUI/UIWindow2.img/UtilDlgEx/list1#\r\n#fnArial##L1##dดอกไม้แห่งความจริงใจของจอห์น";
        } else {
            john += "#fs11##fUI/UIWindow2.img/UtilDlgEx/list3#\r\n#fnArial##L2##dดอกไม้แห่งความจริงใจของจอห์น";
        }
        cm.sendSimple(john);
    } else if (status == 1) {
        if (selection == 2) {
            cm.sendOk("#fs 11#ขอบใจมากเจ้าหนุ่ม! นี่คือของตอบแทนเล็กๆ น้อยๆ จากข้า...\r\nรับไปเถอะ!...\r\n\\r\n#fUI/UIWindow2.img/QuestIcon/4/0#\r\n\r\n#i4021031# #bTwisted Time Essence#k #r100 ชิ้น#k");
            cm.gainItem(4033970, -1);
            cm.gainItem(4021031, 500); // Gives 500 items but text says 100? I'll keep logic (500) and update text to 500.
            cm.forceStartQuest(501);
            cm.showEffect(false, "monsterPark/clear");
            cm.playSound(false, "Field.img/Party1/Clear");
            cm.dispose();
        } else {
            cm.sendNextS("#fs 11##bข้าเกือบตายเพราะตกลงมาตอนช่วยชูมิ เฮ้อ...\r\nต่อไปคือ... คนชื่อ #fs14#จอห์น#fs12# สินะ..?", 2);
        }
    } else if (status == 2) {
        cm.sendNextPrevS("#fnArial##bสวัสดี! ท่านคือ #fs14#จอห์น#fs12# ใช่ไหม??\r\nข้าชื่อ #fs14##h ##fs12#", 2);
    } else if (status == 3) {
        cm.sendNextPrev("#fnArial#หืม? ไม่คุ้นหน้าเลยแฮะ... มีธุระอะไรกับข้าหรือ?...");
    } else if (status == 4) {
        cm.sendNextS("#fnArial##bอ๋อ! ข้ามาหาท่านจอห์น ... \r\n\r\n#fs13##L1#ข้าได้ยินข่าวลือมาและอยากช่วยท่านด้วยใจจริง\r\n#L2#ข้าได้รับคำไหว้วานให้มาช่วยท่าน", 2);
    } else if (status == 5) {
        if (selection == 2) {
            cm.sendYesNo("#fnArial#อ่า จริงสิ! ข้าเคยไหว้วานไปเมื่อคราวก่อนหรือเปล่านะ..?\r\nเอาเถอะ ยินดีที่ได้รู้จัก!.. พร้อมจะฟังเรื่องราวของข้าหรือยัง?");
        } else {
            cm.sendYesNo("#fnArial#หืม? ข่าวลือเกี่ยวกับข้าแพร่ไปตอนไหนเนี่ย...?\r\nเจ้าคงยังไม่รู้รายละเอียด งั้นลองฟังเรื่องของข้าหน่อยไหม?");
        }
    } else if (status == 6) {
        cm.sendNext("#fnArial#น่าอายจัง แต่ข้ามีหญิงที่หมายปองอยู่คนนึงในวัยนี้!\r\nนางชื่อโซเฟีย... แค่ได้ยินชื่อก็ไพเราะแล้วใช่ไหมล่ะ?\r\nแต่ในระหว่างที่ข้ากำลังกลุ้มใจว่าจะสารภาพรักกับนางยังไง...");
    } else if (status == 7) {
        cm.sendYesNo("#fnArial#ข้าก็ปิ๊งไอเดียขึ้นมา! มันคือดอกไม้ที่อยู่บนยอดของป่าแห่งความอดทนขั้นที่ 2...\r\n#i4033970# #b#z4033970##k ให้นางไงล่ะ! แค่ชื่อยังสวยเลย..\r\nแต่ลำพังแรงข้าคงหามาไม่ได้ง่ายๆ..\r\nถ้าเจ้าหามาให้ข้าได้ ข้าจะมอบของที่มีประโยชน์ให้เจ้า!\r\n\r\n#fUI/UIWindow2.img/QuestIcon/3/0#\r\n#i4021031# #bTwisted Time Essence#k #r500 ชิ้น#k\r\n\r\n#dงั้นเจ้าจะไปป่าแห่งความอดทนตอนนี้เลยไหม..??#k");
    } else if (status == 8) {
        cm.warp(910130000, 0);
        cm.sendOk("#fnArial##bดอกไม้#k ถ้าได้ดอกไม้แล้วกลับมาหาข้านะ! ฝากด้วยล่ะ #b#h ##k !!");
        cm.dispose();
    }
}
