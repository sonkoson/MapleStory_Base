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
        var john = "#fnArial##bMandragora#k... เป็นสิ่งมีชีวิตที่น่าหลงใหลจริงๆ..\r\n\r\n";
        if (!cm.haveItem(4034685, 1)) {
            john += "#fs11##fUI/UIWindow2.img/UtilDlgEx/list1#\r\n#fnArial##L1##dKrishrama's Mandragora";
        } else {
            john += "#fs11##fUI/UIWindow2.img/UtilDlgEx/list3#\r\n#fnArial##L2##dKrishrama's Mandragora";
        }
        cm.sendSimple(john);
    } else if (status == 1) {
        if (selection == 2) {
            cm.sendOk("#fnArial#อย่างที่คิดเลย #bMandragora#k ไม่มีอยู่จริงสินะ..\r\nน่าเสียดายจัง แต่ก็ช่วยไม่ได้นี่นะ\r\nนี่คือของตอบแทนจากฉัน รับไปสิ\r\n\\r\n#fUI/UIWindow2.img/QuestIcon/4/0#\r\n\r\n#i4021031# #bTwisted Time Essence#k #r100 ชิ้น#k");
            cm.gainItem(4034685, -1);
            cm.gainItem(4021031, 500);
            cm.forceStartQuest(502);
            cm.showEffect(false, "monsterPark/clear");
            cm.playSound(false, "Field.img/Party1/Clear");
            cm.dispose();
        } else {
            cm.sendNextS("#fnArial##bกล้วยของ Lupin... อึ๋ย.. แย่ที่สุด!\r\nต่อไปก็... คนที่ชื่อ #fs14#Krishrama#fs12# สินะ..?#k", 2);
        }
    } else if (status == 2) {
        cm.sendNextPrev("#fnArial#อืม.. อยากรู้จริงๆ เลยว่า.. #bMandragora#k มีอยู่จริงหรือเปล่านะ?..");
    } else if (status == 3) {
        cm.sendNextPrevS("#fnArial##bเอ่อ, คุณคือ #fs14#Krishrama#fs12# ใช่ไหม?#k", 2);
    } else if (status == 4) {
        cm.sendNextPrev("#fnArial#โอ้, มาแล้วสินะ! อืม.. เรื่องที่ฉันจะวานก็คือ อยากให้ช่วยยืนยันการมีตัวตนของพืชที่ชื่อ #bMandragora#k หน่อยน่ะ..\r\nมีข่าวลือหนาหูว่ามันเติบโตอยู่ที่ยอดของ #bForest of Endurance#k (ป่าแห่งความอดทน)..\r\nถ้าเธอ.. ช่วยไขความจริงนี้ให้กระจ่าง.. ก็คงจะดีไม่น้อย..");
    } else if (status == 5) {
        cm.sendYesNoS("#fnArial##d(ถึงจะกะทันหันไปหน่อย.. แต่ไหนๆ ก็ตั้งใจจะมาช่วยอยู่แล้ว ช่วยเขาหน่อยดีกว่า..?)#k", 2);
    } else if (status == 6) {
        cm.sendYesNo("#fnArial#งั้นก็รีบไปกันเลย!.. รีบไปที่ปลายทางของ #bForest of Endurance#k..\r\nช่วยยืนยันให้ทีว่ามี #bMandragora#k อยู่บนยอดจริงๆ หรือไม่\r\nถ้าตรวจสอบแล้วกลับมา ฉันจะมีรางวัลให้#k\r\n\r\n#fUI/UIWindow2.img/QuestIcon/3/0#\r\n#i4021031# #bTwisted Time Essence#k #r100 ชิ้น#k\r\n\r\n#dงั้นจะมุ่งหน้าไป Forest of Endurance เลยไหม..??#k");
    } else if (status == 7) {
        cm.warp(910530200, 0);
        cm.sendOk("#fnArial#ตรวจสอบยอดเขาแล้วกลับมาหาฉันนะ ฝากด้วยล่ะ..");
        cm.dispose();
    }
}
