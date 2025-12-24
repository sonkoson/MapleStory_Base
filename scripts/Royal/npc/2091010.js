importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);
importPackage(java.text);
importPackage(java.awt);


var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;

        if (status == 0) {
            cm.showDojangRanking();
            cm.getPlayer().dropMessage(5, "ต้องใช้เวลาก่อนที่ข้อมูล Mu Lung Dojo จะแสดงในตารางอันดับ แต้มรางวัลจะถูกแจกจ่ายตามอันดับรวมหลังจากสรุปผลแร้งกิ้งประจำสัปดาห์");
            cm.dispose();
        }
    }
}