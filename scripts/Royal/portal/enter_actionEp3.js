function enter(pi) {
    if (!pi.haveItem(4031217, 1)) {
        pi.getPlayer().dropMessage(-1, "ไม่มีกุญแจเปิดทางเดิน ไม่สามารถผ่านไปได้");
        pi.getPlayer().dropMessage(5, "ไม่มีกุญแจเปิดทางเดิน ไม่สามารถผ่านไปได้");
        return false;
    } else {
        pi.gainItem(4031217, -1);
        pi.warp(304010300, 0);
        pi.getPlayer().dropMessage(-1, "ที่นี่ดูอันตราย.. ระวังตัวแล้วเดินหน้าต่อไป..");
        pi.getPlayer().dropMessage(5, "ที่นี่ดูอันตราย.. ระวังตัวแล้วเดินหน้าต่อไป..");
        return true;
    }
}