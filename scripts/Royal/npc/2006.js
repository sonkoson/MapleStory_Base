importPackage(Packages.database);
importPackage(java.lang);

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
        var TwoMonthsAgo = System.currentTimeMillis() - 24 * 3600 * 1000 * 60 + "\r\n";
        var OneMonthAgo = System.currentTimeMillis() - 24 * 3600 * 1000 * 30 + "\r\n";
        var Yesterday = System.currentTimeMillis() - 24 * 3600 * 1000 * 1 + "\r\n";
        var CurrentTime = System.currentTimeMillis() + "\r\n";
        var Tomorrow = System.currentTimeMillis() + 24 * 3600 * 1000 * 1 + "\r\n";
        var OneMonthLater = System.currentTimeMillis() + 24 * 3600 * 1000 * 30 + "\r\n";
        cm.sendOk("#i61500##z61500#2 เดือนก่อน : " + TwoMonthsAgo + "1 เดือนก่อน : " + OneMonthAgo + "เมื่อวาน : " + Yesterday + "เวลาปัจจุบัน : " + CurrentTime + "พรุ่งนี้ : " + Tomorrow + "1 เดือนข้างหน้า : " + OneMonthLater);
        cm.dispose();
    }
}
