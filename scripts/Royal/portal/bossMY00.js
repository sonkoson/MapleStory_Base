function enter(pi) {
    if (!pi.haveItem(4032246)) {
        pi.playerMessage(5, "เจ้าไม่มี Spirit of Fantasy Theme park");
    } else {
        pi.openNpc(9270047);
    }
}