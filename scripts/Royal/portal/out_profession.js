/*
Return from Free Market Script
*/

function enter(pi) {
    var returnMap = pi.getSavedLocation("ARDENTMILL");
    pi.clearSavedLocation("ARDENTMILL");

    if (returnMap < 0) {
        returnMap = 100000000; // เพื่อแก้ไขผู้ที่เข้าสู่ Ardentmill ด้วยวิธีที่ไม่ปกติ
    }
    var target = pi.getMap(returnMap);
    var portal = target.getPortal("profession");
    if (portal == null) {
        portal = target.getPortal(0);
    }
    if (pi.getMapId() != target) {
        pi.getPlayer().changeMap(target, portal);
    }
}