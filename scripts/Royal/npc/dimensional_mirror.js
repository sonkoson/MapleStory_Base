importPackage(Packages.scripting);

var status = -1;
var sel = -1;
var sel2 = -1;
var f = -1;

function start(flag) {
    status = -1;
    sel = -1;
    sel2 = -1;
    f = flag;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
    var player = cm.getPlayer();
    if (status == 0) {
        if (f == 1) { // Sponsorship System
            cm.dispose();
            cm.openNpc(2003, "D후원시스템");
        } else if (f == 2) { // Promotion System
            cm.dispose();
            cm.openNpc(2003, "D홍보시스템");
        } else if (f == 3) { // Change Nickname
            cm.dispose();
            cm.openNpc(2003, "D닉네임변경");
        } else if (f == 4) { // Change Job
            cm.dispose();
            cm.openNpc(2003, "D자유전직");
        } else if (f == 5) { // V Matrix
            cm.dispose();
            cm.openNpc(2003, "D매트릭스");
        } else if (f == 6) { // Union
            cm.dispose();
            cm.openNpc(2003, "D유니온시스템");
        } else if (f == 7) { // Combat Power Measurement
            cm.dispose();
            cm.openNpc(2003, "D전투력측정");
        } else if (f == 8) { // Growth System
            cm.dispose();
            cm.openNpc(2003, "D성장시스템");
        } else if (f == 9) { // Marriage System
            cm.dispose();
            cm.openNpc(2003, "D결혼시스템");
        } else if (f == 10) { // Ranking System
            cm.dispose();
            cm.openNpc(2003, "D랭킹시스템");
        } else if (f == 11) { // Enhancement System
            cm.dispose();
            cm.openNpc(2003, "D강화시스템");
        }
    }
}