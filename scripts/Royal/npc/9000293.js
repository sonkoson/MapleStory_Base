var enter = "\r\n";
var seld = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}
function action(mode, type, sel) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }
    if (status == 0) {
        var msg = "#fs11#เตรียมใจให้พร้อมไว้นะ~ #k\r\n\r\n";
        msg += "#L2# #dดอกไม้ของ John #b(รางวัล #i4021031# 500 ชิ้น)#l#k#n\r\n";
        msg += "#L3# #dเหรียญที่หายไปของ Shumi #b(รางวัล #i4021031# 500 ชิ้น)#l#k#n\r\n";
        msg += "#L4# #dMandragora ของ Krisharama #b(รางวัล #i4021031# 500 ชิ้น)#l#k#n\r\n";
        msg += "#L5# #dภารกิจสำรวจเหมืองร้าง #b(รางวัล #i4021031# 500 ชิ้น)#l#k#n\r\n";
        cm.sendSimple(msg);
    } else if (status == 1) {
        seld = sel;
        switch (sel) {
            case 2:
                cm.dispose();
                cm.openNpc(20000);
                break;
            case 3:
                cm.dispose();
                cm.openNpc(1052102);
                break;
            case 4:

                cm.dispose();
                cm.openNpc(1061000);
                break;
            case 5:
                cm.dispose();
                cm.openNpc(2010000);
                break;
        }
    }
}