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
    if (pi.getPlayer().getLevel() < 170) {
        pi.getPlayer().message("ผู้ที่มีเลเวล 170 ขึ้นไปเท่านั้นที่สามารถเข้าได้");
        return false;
    }
    pi.warp(271040000);
    return false;
}