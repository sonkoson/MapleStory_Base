var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    var count = 1;
    setting = [
        ["JINH", count, 450010500, 130],
    ]
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        if (cm.getPlayer().getParty() == null) {
            cm.sendOk("ต้องมีปาร์ตี้จึงจะเข้าได้");
            cm.dispose();
            return;
        }
        if (!cm.isLeader()) {
            cm.sendOk("เฉพาะหัวหน้าปาร์ตี้เท่านั้นที่สามารถขอเข้าได้");
            cm.dispose();
            return;
        }
        talk = "#e<Boss: Jin Hilla>#n\r\n"
        talk += "#L0##b ขอเข้าท้าทาย <Boss: Jin Hilla>"
        cm.sendSimple(talk);
    } else if (status == 1) {
        if (cm.getPlayer().getParty() == null) {
            cm.sendOk("ต้องมีปาร์ตี้จึงจะเข้าได้");
            cm.dispose();
            return;
        }
        if (!cm.isLeader()) {
            cm.sendOk("เฉพาะหัวหน้าปาร์ตี้เท่านั้นที่สามารถขอเข้าได้");
            cm.dispose();
            return;
        }
        talk = "#e<Boss: Jin Hilla>#n\r\n"
        talk += "#L0# Normal Mode"
        cm.sendSimple(talk);
    } else if (status == 2) {
        st = selection;
        if (cm.getPlayer().getParty() == null) {
            cm.sendOk("ต้องมีปาร์ตี้จึงจะเข้าได้");
            cm.dispose();
            return;
        } else if (!cm.isLeader()) {
            cm.sendOk("เฉพาะหัวหน้าปาร์ตี้เท่านั้นที่สามารถขอเข้าได้");
            cm.dispose();
            return;
        } else if (!cm.allMembersHere()) {
            cm.sendOk("สมาชิกทุกคนต้องอยู่ในแผนที่เดียวกัน");
            cm.dispose();
            return;
        } else if (cm.getPlayerCount(setting[st][2]) >= 1) {
            cm.sendOk("มีคนกำลังท้าทาย Jin Hilla อยู่แล้ว");
            cm.dispose();
            return;
        }
        if (!cm.isBossAvailable(setting[st][0], setting[st][1])) {
            talk = "สมาชิกในปาร์ตี้ #b#e"
            for (i = 0; i < cm.BossNotAvailableChrList(setting[st][0], setting[st][1]).length; i++) {
                if (i != 0) {
                    talk += ", "
                }
                talk += "#b#e" + cm.BossNotAvailableChrList(setting[st][0], setting[st][1])[i] + ""
            }
            talk += "#k#n ได้เข้าดันเจี้ยนไปแล้วในวันนี้ ไม่สามารถเข้าได้อีก";
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
            talk += "#k#n เลเวลไม่ถึงกำหนด ไม่สามารถเข้าได้";
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