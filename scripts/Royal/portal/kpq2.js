/*
Kerning PQ: 2nd stage to 3rd stage portal
*/

function enter(pi) {
    var eim = pi.getEventManager("KerningPQ").getInstance("KerningPQ");

    // only let people through if the eim is ready
    if (eim.getProperty("2stageclear") == null) { // do nothing; send message to player
        pi.playerMessage(5, "พอร์ทัลถูกบล็อก");
    } else {
        pi.warp(910340300, "st00");
    }
}