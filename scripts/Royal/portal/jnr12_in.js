function enter(pi) {
    if ((pi.getMap().getCharactersSize() >= 2 && pi.getMap().getCharactersSize() <= 4) || pi.getMap(926110401).getCharactersSize() > 0) {
        pi.warpParty(926110401, 0);
        pi.partyMessage(6, "ระหว่างที่ Yurete กำลังจัดการกับอุปกรณ์ ปีศาจยักษ์ก็ปรากฏตัวขึ้น Yurete หัวเราะอย่างน่ารังเกียจและหายตัวไป")
    } else {
        pi.playerMessage(5, "สมาชิกในปาร์ตี้ยังมาไม่ครบ");
    }
}