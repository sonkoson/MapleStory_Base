var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    var count = 1;
    setting = [
        ["Normal_Cygnus", count, 271040100, 170],
    ]
    name = ["Normal"]
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        if (cm.getPlayer().getMapId() == 271040100) {
            cm.sendYesNo("ต้องการออกจากสวนของ Cygnus หลังจากจบการต่อสู้หรือไม่?");
        } else {
            talk = "พร้อมที่จะเผชิญหน้ากับ Fallen Cygnus แล้วหรือยัง?\r\n\r\n"
            talk += "#L0# #bขอเข้าท้าทาย Cygnus (Normal) (Lv. 170+)";
            cm.sendSimple(talk);
        }
    } else if (status == 1) {
        if (cm.getPlayer().getMapId() == 271040100) {
            cm.warp(271040000);
            cm.dispose();
            return;
        }
        st = selection;
        if (cm.getPlayer().getParty() == null) {
            cm.sendOk("ต้องมีปาร์ตี้อย่างน้อย 1 คนถึงจะเข้าได้");
            cm.dispose();
            return;
        } else if (!cm.isLeader()) {
            cm.sendOk("เฉพาะหัวหน้าปาร์ตี้เท่านั้นที่สามารถขอเข้าได้");
            cm.dispose();
            return;
        } else if (!cm.allMembersHere()) {
            cm.sendOk("สมาชิกทุกคนต้องอยู่ที่เดียวกัน");
            cm.dispose();
            return;
        } else if (cm.getPlayerCount(setting[st][2]) >= 1) {
            cm.sendNext("มีปาร์ตี้อื่นกำลังท้าทาย Cygnus อยู่ข้างในแล้ว");
            cm.dispose();
            return;
        }
        if (!cm.isBossAvailable(setting[st][0], setting[st][1])) {
            talk = "มีสมาชิกในปาร์ตี้ที่เคลียร์ Cygnus ไปแล้วในวันนี้\r\nCygnus (Normal) สามารถเคลียร์ได้วันละ " + setting[st][1] + " ครั้งเท่านั้น\r\n"
            talk += "#r#e<บันทึกการเคลียร์จะรีเซ็ตทุกวันเวลาเที่ยงคืน>#k#n"
            cm.sendOk(talk);
            cm.dispose();
            return;
        } else if (!cm.isLevelAvailable(setting[st][3])) {
            talk = "สมาชิกในปาร์ตี้ #b#e"
            for (i = 0; i < cm.LevelNotAvailableChrList(setting[st][3]).length; i++) {
                if (i != 0) {
                    talk += ", "
                }
                talk += "#b#e" + cm.LevelNotAvailableChrList(setting[st][3])[i] + ""
            }
            talk += "#k#n เลเวลไม่เพียงพอ Cygnus เข้าได้เฉพาะเลเวล " + setting[st][3] + " ขึ้นไปเท่านั้น";
        } else {
            cm.addBoss(setting[st][0]);
            em = cm.getEventManager(setting[st][0]);
            if (em != null) {
                cm.getEventManager(setting[st][0]).startInstance_Party(setting[st][2] + "", cm.getPlayer());
            }
            cm.dispose();
        }
    }
}