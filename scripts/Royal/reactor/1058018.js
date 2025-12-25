function destroy() {
    rm.spawnMonster(8920102, 37, 135);
}

function act() {
    if (rm.getMap().getId() == 105200310) {
        state = rm.getReactor().getState();
        if (state == 1) {
            rm.startMapEffect("แหม มีแขกที่น่ารักมาเยี่ยมด้วย", 5120099, 3);
        } else if (state == 2) {
            rm.startMapEffect("บังอาจนัก! กล้าดียังไงถึงบุกเข้ามาในเขตหวงห้าม! ", 5120100, 3);
        } else if (state == 3) {
            rm.startMapEffect("คิกคิก ไม่รู้สินะว่าที่นี่คือจุดจบของเจ้า", 5120101, 3);
        } else if (state == 4) {
            rm.startMapEffect("ฮือฮือ ข้าจะไว้อาลัยล่วงหน้าให้กับความตายของเจ้า", 5120102, 5);
        }
    }
}
