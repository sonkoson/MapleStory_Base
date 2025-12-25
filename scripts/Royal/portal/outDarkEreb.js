function enter(pi) {
    if (pi.isQuestFinished(20407)) {
        pi.warp(130000000, 0);
    } else {
        pi.playerMessage(5, "ฉันต้องจัดการแม่มดดำก่อน!");
    }
}