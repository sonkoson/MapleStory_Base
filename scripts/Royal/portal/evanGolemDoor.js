function enter(pi) {
    pi.warp(100040000, 0);
    pi.playPortalSE();
    if (pi.isQuestActive(22557)) {
        pi.forceCompleteQuest(22557);
        pi.playerMessage(5, "ช่วยเหลือ Camilla สำเร็จ!");
        pi.getPlayer().gainSP(1);
    }
}