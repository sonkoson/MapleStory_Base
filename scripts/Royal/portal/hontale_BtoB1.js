/*
 * 퓨어온라인 소스 스크립트 입니다.
 * 
 * 포탈위치 : 생명의동굴 : 미로방
 * 포탈설명 : 첫번째 미로방 입장
 * 
 * 제작 : 주크블랙
 * 
 */

function enter(pi) {
    if (pi.haveItem(4001087, 1)) {
        pi.warp(240050101);
        pi.getPlayer().message(5, "เคลื่อนย้ายไปที่ไหนสักแห่งด้วยพลังของคริสตัลในห้องเขาวงกตแรก");
        pi.gainItem(4001087, -1);
        return true;
    } else {
        pi.getPlayer().message(5, "เจ้าไม่มีกุญแจที่จำเป็นในการเข้าสู่ห้องเขาวงกต");
        return false;
    }
}