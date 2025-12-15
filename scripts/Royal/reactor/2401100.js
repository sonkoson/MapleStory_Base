function act() {
    if (rm.getMap().getId() == 240060201) {
        rm.changeMusic("Bgm14/HonTale");
        rm.spawnMonster(8810130, 117, 230);
        rm.mapMessage(6, "Chaos Horntail ปรากฏตัวขึ้นจากส่วนลึกของถ้ำ!");
    }
}