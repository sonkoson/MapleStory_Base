function enter(pi) {
    if (pi.getQuestStatus(21011) == 2) {
        pi.playPortalSE();
        pi.warp(140090300, 1);
    } else {
        pi.playerMessage(5, "เจ้าต้องทำภารกิจให้เสร็จสิ้นก่อนที่จะไปยังแผนที่ถัดไป");
    }
}