var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    var count = 1;
    setting = [
        ["Normal_Hillah", count, 262030100, 120],
        ["Hard_Hillah", count, 262031100, 170]
    ]
    name = ["Normal", "Hard"]
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        msg = "#e<Boss: Hilla>#n\r\n"
        msg += "พร้อมที่จะปราบ Hilla และปลดปล่อย Azwan อย่างแท้จริงหรือยัง? ถ้ามีสมาชิกปาร์ตี้อยู่แมพอื่น กรุณามารวมตัวกันก่อนนะ\r\n\r\n"
        msg += "#L0# #b<Boss: Hilla> ขอเข้าสู้บอส"
        cm.sendSimple(msg);
    } else if (status == 1) {
        msg = "#e<Boss: Hilla>#n\r\n"
        msg += "กรุณาเลือกโหมดที่ต้องการ\r\n\r\n"
        msg += "#L0# Normal Mode (Level 120 ขึ้นไป)\r\n"
        msg += "#L1# Hard Mode (Level 170 ขึ้นไป, เข้าคนเดียวอันตรายมาก)"
        cm.sendSimple(msg);
    } else if (status == 2) {
        st = selection;
        if (cm.getParty() == null) {
            cm.sendOk("ต้องมีปาร์ตี้อย่างน้อย 1 คนถึงจะเข้าได้");
            cm.dispose();
            return;
        } else if (cm.getPlayerCount(setting[st][2]) >= 1 || cm.getPlayerCount(Number(setting[st][2]) + 100) >= 1 || cm.getPlayerCount(Number(setting[st][2]) + 200) >= 1 || cm.getPlayerCount(Number(setting[st][2]) + 210) >= 1) {
            cm.sendOk("มีคนกำลังสู้ Hilla อยู่แล้ว\r\nกรุณาไปช่องอื่น");
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
        }
        if (!cm.isBossAvailable(setting[st][0], setting[st][1])) {
            talk = "สมาชิกปาร์ตี้ "
            for (i = 0; i < cm.BossNotAvailableChrList(setting[st][0], setting[st][1]).length; i++) {
                if (i != 0) {
                    talk += ", "
                }
                talk += "#b#e" + cm.BossNotAvailableChrList(setting[st][0], setting[st][1])[i] + ""
            }
            talk += "#k#n ได้เข้าสู้บอสนี้วันนี้แล้ว <Boss: Hilla> " + name[st] + " Mode ท้าทายได้วันละ " + setting[st][1] + " ครั้งเท่านั้น";
            cm.sendOk(talk);
            cm.dispose();
            return;
        } else if (!cm.isLevelAvailable(setting[st][3])) {
            talk = "สมาชิกปาร์ตี้ "
            for (i = 0; i < cm.LevelNotAvailableChrList(setting[st][3]).length; i++) {
                if (i != 0) {
                    talk += ", "
                }
                talk += "#b#e" + cm.LevelNotAvailableChrList(setting[st][3])[i] + ""
            }
            talk += "#k#n เลเวลไม่ถึง <Boss: Hilla> " + name[st] + " Mode ต้อง Level " + setting[st][3] + " ขึ้นไปถึงจะเข้าได้";
            cm.sendOk(talk);
            cm.dispose();
            return;
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