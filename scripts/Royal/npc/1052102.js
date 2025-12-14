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
        var sumi = "#fnArial#แย่แล้ว... ทำยังไงดีนะ...? ฉันทำเงินของเพื่อนหายไป... \r\nเอาไงดี...? อยากให้ใครสักคนช่วยจัง... แต่ว่าฉันคง.. ไม่ไหวแน่ๆ...\r\n\r\n";
        if (!cm.haveItem(4031039, 1)) {
            sumi += "#fs11##fUI/UIWindow2.img/UtilDlgEx/list1#\r\n#fnArial##L1##dเหรียญที่ Shumi ทำหาย";
        } else {
            sumi += "#fs11##fUI/UIWindow2.img/UtilDlgEx/list3#\r\n#fnArial##L2##dเหรียญที่ Shumi ทำหาย";
        }
        cm.sendSimple(sumi);
    } else if (status == 1) {
        if (selection == 2) {
            cm.sendOk("#fnArial#ขอบคุณจริงๆ ที่ช่วยฉัน! นี่เป็นของตอบแทนเล็กๆ น้อยๆ จากฉันนะ!\r\n\\r\n#fUI/UIWindow2.img/QuestIcon/4/0#\r\n#i4021031# #bTwisted Time Essence#k #r100 ชิ้น#k");
            cm.gainItem(4031039, -1);
            cm.gainItem(4021031, 500);
            cm.forceStartQuest(500);
            cm.showEffect(false, "monsterPark/clear");
            cm.playSound(false, "Field.img/Party1/Clear");
            cm.dispose();
        } else {
            cm.sendNextS("#fnArial##d(เด็กผู้หญิงคนนั้นดูท่าทางลำบากจัง ลองเข้าไปถามดูดีกว่า..)", 2);
        }
    } else if (status == 2) {
        cm.sendNextPrevS("#fnArial##bนี่...? มีเรื่องอะไรหรือเปล่า..?#k", 2);
    } else if (status == 3) {
        cm.sendNextPrev("#fnArial#ฉันแย่แน่ๆ เลย.. ฮือๆ ㅠ_ㅠ ทำเงินของเพื่อนหายไปง่ายๆ แบบนี้..\r\nดันไปทำหายที่.. #bสถานีรถไฟใต้ดิน#k อันแสนอันตรายนั่นซะด้วย...ㅠㅠ\r\nทำไม.. ทำไมกันนะ! ㅠㅠ… เฮ้อ.. จะมีใครช่วยฉันไหมเนี่ย..?");
    } else if (status == 4) {
        cm.sendNextS("#fnArial##d(ถึงไม่ได้ฟังเรื่องราวทั้งหมด ก็พอจะเดาได้ว่าเกิดอะไรขึ้น จะทำยังไงดี?)#k\r\n\r\n#fs13##L1##bช่วย Shumi ตามหาเงิน\r\n#L2#ไม่สนหรอก ไปตามทางของฉันดีกว่า#k", 2);
    } else if (status == 5) {
        if (selection == 2) {
            cm.sendOkS("#fnArial##bShumi จะเป็นยังไงก็ช่างสิ! ทำเรื่องของตัวเองดีกว่า..#k", 2);
            cm.dispose();
        } else {
            cm.sendYesNo("#fnArial##fs15#จริงเหรอ..!? #fs12#จะช่วยฉันจริงๆ เหรอ #b#h ##k !?\r\nดีใจจัง!! ขอบใจนะ..!! จริงๆ.. ขอบใจจริงๆ นะ!! ㅠㅠ..\r\nถ้าหาเงินมาคืนได้ ฉันจะให้ของที่มีประโยชน์กับเธอเป็นการตอบแทน!\r\n\r\n#fUI/UIWindow2.img/QuestIcon/3/0#\r\n#i4021031# #bTwisted Time Essence#k #r100 ชิ้น#k\r\n\r\n#dงั้นจะไปตามหาเงินที่หายไปตอนนี้เลยไหม..??#k");
        }
    } else if (status == 6) {
        cm.warp(910360000, 0);
        cm.sendOk("#fnArial#ถ้าเจอ #bเหรียญ#k ของฉันแล้ว กลับมาหาฉันนะ! ฝากด้วยล่ะ..!!");
    }
}

