importPackage(Packages.objects.users);

function enter(pi) {
    pi.warp(141030000, 0);
    pi.getPlayer().cancelEffectFromBuffStat(SecondaryStatFlag.RIDING_VEHICLE);
    pi.getPlayer().dropMessage(-1, "[การเดินทางสำเร็จ] ไปหา Ddabo ทางขวาและช่วยทำตามคำขอของ Ddabo");
    pi.getPlayer().dropMessage(5, "[การเดินทางสำเร็จ] ไปหา Ddabo ทางขวาและช่วยทำตามคำขอของ Ddabo");
    return true;
}

