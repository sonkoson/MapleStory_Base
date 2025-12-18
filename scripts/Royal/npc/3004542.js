var status = -1;


function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    var count = 1;
    setting = [
        ["Hard_Seren", count, 410002000, 280, 7]
    ]
    name = ["Hard"]
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        talk = "ต้องการย้ายไป #bPalace Main Hall#k เพื่อเผชิญหน้า Seren หรือเปล่า?\r\n\r\n"
        talk += "#L0##rย้ายไปที่ใกล้จุดจบ (LV.280 ขึ้นไป)#k\r\n"
        talk += "#L1#ไม่ย้าย\r\n"
        cm.sendSimpleS(talk, 0x26);
    } else if (status == 1) {
        if (selection == 1) {
            cm.dispose();
            return;
        }
        st = selection;
        if (cm.getParty() == null) {
            cm.sendOkS("ต้องมีปาร์ตี้อย่างน้อย 1 คนถึงจะเข้าได้", 0x26);
            cm.dispose();
            return;
        } else if (cm.getPlayerCount(setting[st][2]) >= 1 || cm.getPlayerCount(setting[st][2] + 20) >= 1 || cm.getPlayerCount(setting[st][2] + 40) >= 1 || cm.getPlayerCount(setting[st][2] + 60) >= 1 || cm.getPlayerCount(setting[st][2] + 80) >= 1) {
            cm.sendOkS("มีคนกำลังสู้ Seren อยู่แล้ว\r\nกรุณาไปช่องอื่น", 0x26);
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
            cm.sendOkS(cm.isBossString(setting[st][0]), 0x04, 9010061);
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
            talk += "#k#n เลเวลไม่ถึง\r\nSeren " + name[st] + " Mode ต้อง Level " + setting[st][3] + " ขึ้นไปถึงจะเข้าได้";
            cm.sendOkS(talk, 0x24);
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
            talk += "#k#n Boss Rank ไม่ถึง\r\nSeren " + name[st] + " Mode ต้อง Rank " + setting[st][4] + " ขึ้นไปถึงจะเข้าได้";
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
    } else if (status == 2) {
        cm.dispose();
        em = cm.getEventManager(setting[st][0]);
        if (em != null) {
            cm.getEventManager(setting[st][0]).startInstance_Party(setting[st][2] + "", cm.getPlayer());
        }
    }
}