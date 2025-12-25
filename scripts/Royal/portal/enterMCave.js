function enter(pi) {
    if (pi.isQuestActive(21201)) { //aran first job
        pi.forceCompleteQuest(21201);
        pi.playerMessage(5, "เจ้ากู้คืนความทรงจำกลับมาแล้ว!");
    }
    pi.warp(914021000, 0);
    //what does this even do
}