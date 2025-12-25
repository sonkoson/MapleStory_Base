function enter(pi) {
    if (pi.isQuestActive(3164)) {
        pi.forceCompleteQuest(3164);
        pi.playerMessage("ภารกิจเสร็จสิ้น");
    }
    pi.warp(211060201, 0);
}