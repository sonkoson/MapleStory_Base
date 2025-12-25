importPackage(Packages.database);
importPackage(java.lang);

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, sel) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }


    if (status == 0) {
        cm.effectText("#fnNanumGothic ExtraBold##fs20#< ยินดีต้อนรับสู่ Royal World >", 50, 1000, 6, 0, 380, -550);

        cm.getPlayer().send(Packages.network.models.CField.addPopupSay(1052206, 10000, "ยินดีต้อนรับสู่\r\n#r[Royal World]!!!#k\r\n\r\nกรุณา #bยอมรับข้อตกลงการใช้งาน#k\r\nผ่าน NPC Sugar จากนั้น\r\nลอง #rเปลี่ยนอาชีพ#k เป็นอาชีพที่ต้องการดูสิ!", ""));

        cm.showEffect(false, "Effect/EventEffect.img/SalonDebut/screenEff/1366");
        cm.dispose();
    }
}