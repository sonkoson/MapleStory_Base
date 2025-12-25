function enter(pi) {
    if (pi.isQuestFinished(20407)) {
        pi.warp(924010200, 0);
    } else if (pi.isQuestFinished(20406)) {
        pi.warp(924010100, 0);
    } else if (pi.isQuestFinished(20404)) {
        pi.warp(924010000, 0);
    } else {
        pi.playerMessage(5, "ฉันไม่ควรไปที่นั่นเลย.. มันน่าขนลุก!");
    }
}