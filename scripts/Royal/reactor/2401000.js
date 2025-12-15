function act() {
    if (rm.getMap().getId() == 240060200) {
        rm.changeMusic("Bgm14/HonTale");
        rm.spawnMonster(8810026, 71, 260);
        //rm.getMap().killMonster(rm.getMap().getMonsterById(8810026));
        rm.mapMessage(6, "Horntail ปรากฏตัวขึ้นจากส่วนลึกของถ้ำ!");
    }
}