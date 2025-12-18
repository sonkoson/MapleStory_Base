var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    var count = 1;
    setting = [
        ["Normal_Will", count, 450008700, 235, 3],
        ["Hard_Will", count, 450008100, 255, 5]
    ]
    name = ["Normal", "Hard"];
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        talk = "ต้องการย้ายไป #b'Corridor of Diffraction'#k เพื่อหยุด Will หรือเปล่า?\r\n\r\n"
        talk += "#L0##bย้ายไป Corridor of Diffraction (Normal Mode)#k#r (Level 235 ขึ้นไป)#k#l\r\n";
        talk += "#L1##bย้ายไป Corridor of Diffraction (Hard Mode)#k#r (Level 255 ขึ้นไป)#k#l\r\n";
        cm.sendSimpleS(talk, 0x26);
    } else if (status == 1) {
        st = selection;
        if (cm.getParty() == null) {
            cm.sendOkS("ต้องมีปาร์ตี้อย่างน้อย 1 คนถึงจะเข้าได้", 0x26);
            cm.dispose();
            return;
        } else if (cm.getPlayerCount(setting[st][2]) >= 1 || cm.getPlayerCount(setting[st][2] + 50) >= 1 || cm.getPlayerCount(setting[st][2] + 100) >= 1 || cm.getPlayerCount(setting[st][2] + 150) >= 1 || cm.getPlayerCount(setting[st][2] + 200) >= 1 || cm.getPlayerCount(setting[st][2] + 250) >= 1 || cm.getPlayerCount(setting[st][2] + 280) >= 1) {
            cm.sendOkS("มีคนกำลังสู้ Will อยู่แล้ว\r\nกรุณาไปช่องอื่น", 0x26);
            cm.dispose();
            return;
        } else if (!cm.isLeader()) {
            cm.sendOkS("เฉพาะหัวหน้าปาร์ตี้เท่านั้นที่ขอเข้าได้", 0x26);
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
            talk += "#k#n ได้เข้าวันนี้แล้ว Will " + name[st] + " Mode ท้าทายได้วันละ " + setting[st][1] + " ครั้งเท่านั้น";
            cm.sendOkS(talk, 0x26);
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
            talk += "#k#n เลเวลไม่ถึง\r\nWill " + name[st] + " Mode ต้อง Level " + setting[st][3] + " ขึ้นไปถึงจะเข้าได้";
            cm.sendOkS(talk, 0x26);
            cm.dispose();
            return;
        } else if (!cm.isBossTier(setting[st][4])) {
            talk = "สมาชิกปาร์ตี้ "
            for (i = 0; i < cm.BossTierChrList(setting[st][4]).length; i++) {
                if (i != 0) {
                    talk += ", "
                }
                talk += "#b#e" + cm.BossTierChrList(setting[st][4])[i] + ""
            }
            talk += "#k#n Boss Rank ไม่ถึง\r\nWill " + name[st] + " Mode ต้อง Rank " + setting[st][4] + " ขึ้นไปถึงจะเข้าได้";
            cm.sendOkS(talk, 0x26);
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