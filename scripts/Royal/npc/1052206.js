var status = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1)
        status++;
    else
        status--;
    if (status == 0) {
        var text = "#fUI/Basic.img/RoyalBtn/StartImg/0#";

        text += "\r\n#fn나눔고딕 Extrabold##d							ยินดีต้อนรับสู่ Royal Maple#k\r\n#r								โปรดอ่านประกาศใน Discord#k\r\n#b									ขอให้สนุก";
        cm.sendOk(text);
        cm.dispose();
    }
}