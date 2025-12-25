function enter(pi) {
    if (!pi.haveMonster(9300216)) {
        pi.playerMessage("ยังมีมอนสเตอร์เหลืออยู่");
    } else {
        pi.dojoAgent_NextMap(true, false);
    }
}