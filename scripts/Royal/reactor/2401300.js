function act() {
    if (rm.getMap().getId() == 240060300) {
        rm.changeMusic("Bgm14/HonTale");
        rm.spawnMonster(8810215, 71, 260);
        rm.mapMessage(6, "Horntail ปรากฏตัวออกมาจากส่วนลึกของถ้ำ!");
    }
}