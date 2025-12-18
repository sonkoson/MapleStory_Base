var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    var count = 1;
    setting = [
        ["Normal_Horntail", count, 240060000, 130],
        ["Chaos_Horntail", count, 240060001, 135]
    ]
    name = ["Normal", "Chaos"]
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
            cm.sendOk("เฉพาะหัวหน้าปาร์ตี้เท่านั้นที่ขอเข้าได้");
            cm.dispose();
            return;
        }
        talk = "#e<Boss: Horntail>#n\r\n"
        talk += "Horntail ฟื้นคืนชีพแล้ว ถ้าปล่อยไว้มันจะทำให้ภูเขาไฟระเบิดและเปลี่ยน Minar ให้กลายเป็นนรก\r\n\r\n"
        talk += "#L0##b <Boss: Horntail> ขอเข้าสู้บอส"
        cm.sendSimple(talk);
    } else if (status == 1) {
        if (cm.getPlayer().getParty() == null) {
            cm.sendOk("ต้องมีปาร์ตี้จึงจะเข้าได้");
            cm.dispose();
            return;
        }
        if (!cm.isLeader()) {
            cm.sendOk("เฉพาะหัวหน้าปาร์ตี้เท่านั้นที่ขอเข้าได้");
            cm.dispose();
            return;
        }
        talk = "#e<Boss: Horntail>#n\r\n"
        talk += "เลือกโหมดที่ต้องการ\r\n\r\n"
        talk += "#L0# Normal Mode (Level 130 ขึ้นไป)#l\r\n"
        talk += "#L1# Chaos Mode (Level 135 ขึ้นไป)#l"
        cm.sendSimple(talk);
    } else if (status == 2) {
        st = selection;
        if (cm.getPlayer().getParty() == null) {
            cm.sendOk("ต้องมีปาร์ตี้จึงจะเข้าได้");
            cm.dispose();
            return;
        } else if (!cm.isLeader()) {
            cm.sendOk("เฉพาะหัวหน้าปาร์ตี้เท่านั้นที่ขอเข้าได้");
            cm.dispose();
            return;
        } else if (!cm.allMembersHere()) {
            cm.sendOk("สมาชิกทุกคนต้องอยู่ที่เดียวกัน");
            cm.dispose();
            return;
        } else if (cm.getPlayerCount(setting[st][2]) >= 1 || cm.getPlayerCount(setting[st][2] + 100) >= 1 || cm.getPlayerCount(setting[st][2] + 200) >= 1) {
            cm.sendOk("มีคนกำลังสู้ Horntail อยู่แล้ว");
            cm.dispose();
            return;
        }
        if (!cm.isBossAvailable(setting[st][0], setting[st][1])) {
            talk = "สมาชิกปาร์ตี้ #b#e"
            for (i = 0; i < cm.BossNotAvailableChrList(setting[st][0], setting[st][1]).length; i++) {
                if (i != 0) {
                    talk += ", "
                }
                talk += "#b#e" + cm.BossNotAvailableChrList(setting[st][0], setting[st][1])[i] + ""
            }
            talk += "#k#n ได้เข้าวันนี้แล้ว งั้นวันนี้เข้าอีกไม่ได้แล้ว";
            cm.sendOk(talk);
            cm.dispose();
            return;
        } else if (!cm.isLevelAvailable(setting[st][3])) {
            talk = "สมาชิกปาร์ตี้ #b#e"
            for (i = 0; i < cm.LevelNotAvailableChrList(setting[st][3]).length; i++) {
                if (i != 0) {
                    talk += ", "
                }
                talk += "#b#e" + cm.LevelNotAvailableChrList(setting[st][3])[i] + ""
            }
            talk += "#k#n เลเวลไม่ถึง งั้นเข้าไม่ได้";
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