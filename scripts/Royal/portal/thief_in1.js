function enter(pi) {
    if (pi.isQuestActive(3925)) {
        pi.forceCompleteQuest(3925);
        pi.playerMessage("ภารกิจเสร็จสิ้น");
    }
    pi.warp(260010402, 0);
}