/*
 * 퓨어온라인 소스 스크립트 입니다.
 * 
 * 포탈위치 : 
 * 포탈설명 : 
 * 
 * 제작 : 주크블랙
 * 
 */

function enter(pi) {
    var eim = pi.getPlayer().getEventInstance();
    if (pi.getPlayer().getMapId() == 240050101) { //첫번째 미로방
        if (eim.getProperty("Maze02open") == null) {
            pi.getPlayer().message(5, "พอร์ทัลถูกบล็อกโดยพลังลึกลับบางอย่าง");
            return false;
        } else {
            pi.warp(240050102);
            return true;
        }
    }
    if (pi.getPlayer().getMapId() == 240050102) { //두번째 미로방
        if (eim.getProperty("Maze03open") == null) {
            pi.getPlayer().message(5, "พอร์ทัลถูกบล็อกโดยพลังลึกลับบางอย่าง");
            return false;
        } else {
            pi.warp(240050103);
            return true;
        }
    }
    if (pi.getPlayer().getMapId() == 240050103) { //세번째 미로방
        if (eim.getProperty("Maze04open") == null) {
            pi.getPlayer().message(5, "พอร์ทัลถูกบล็อกโดยพลังลึกลับบางอย่าง");
            return false;
        } else {
            pi.warp(240050104);
            return true;
        }
    }
    if (pi.getPlayer().getMapId() == 240050104) { //네번째 미로방
        if (eim.getProperty("Maze05open") == null) {
            pi.getPlayer().message(5, "พอร์ทัลถูกบล็อกโดยพลังลึกลับบางอย่าง");
            return false;
        } else {
            pi.warp(240050105);
            return true;
        }
    }

    if (pi.getPlayer().getMapId() == 240050105) { //두번째 미로방
        if (pi.haveItem(4001092, 1)) {
            pi.gainItem(4001092, -1);
            eim.broadcastPlayerMsg(5, "เคลื่อนย้ายด้วยพลังของกุญแจสีแดง");
            pi.allPartyWarp(240050100, false);
            eim.addAchievementRatio(5);
            return true;
        } else {
            pi.getPlayer().message(5, "พอร์ทัลถูกบล็อกโดยพลังลึกลับบางอย่าง");
            return false;
        }
    }
}