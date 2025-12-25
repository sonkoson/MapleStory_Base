function enter(pi) {
    var eim = pi.getPlayer().getEventInstance();

    // only let people through if the eim is ready
    if (eim.getProperty("stage7status") == null) { // do nothing; send message to player
        pi.playerMessage(5, "พอร์ทัลถูกบล็อก");
    } else {
        pi.warp(pi.getMapId() + 100, "st00");
    }
}