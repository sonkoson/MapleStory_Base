function enter(pi) {
    pi.warp(302010400, 0);
    pi.forceCompleteQuest(300);
    pi.getPlayer().dropMessage(-1, "ถึงแล้ว... ลองพูดคุยกับ Hatsar ดู");
    pi.getPlayer().dropMessage(5, "ถึงแล้ว... ลองพูดคุยกับ Hatsar ดู");
    return true;
}