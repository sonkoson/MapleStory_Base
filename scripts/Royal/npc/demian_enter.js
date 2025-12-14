function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        status--;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        if (cm.getPlayer().getParty() == null) {
            cm.sendOkS("โปรดเข้าร่วมปาร์ตี้ก่อนเข้า", 2);
            cm.dispose();
        } else if (!cm.isLeader()) {
            cm.sendOkS("หัวหน้าปาร์ตี้เท่านั้นที่สามารถขอเข้าได้", 2);
            cm.dispose();
        } else {
            cm.sendSimpleS("กรุณาเลือกระดับความยาก\r\n#b#L0#Normal Mode\r\n#L1#Hard Mode", 2);
        }
    } else if (status == 1) {

    }
}