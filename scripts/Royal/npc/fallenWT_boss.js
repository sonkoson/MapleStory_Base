var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    var count = 1;
    setting = [
        ["Normal_Demian", count, 350160100, 220, 0],
        ["Hard_Demian", count, 350160200, 230, 2],
        ["Extreme_Demian", 1, 350160200, 290, 0]
    ]
    name = ["Normal", "Hard", "Extreme"];
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        talk = "จะไปยัง 'Summit of the Fallen World Tree' เพื่อจัดการกับ Damien หรือไม่?\r\n\r\n"
        talk += "#L0#ไปยัง Summit of the Fallen World Tree (#bNormal Mode#k) (Lv. 220+)#l\r\n";
        talk += "#L1#ไปยัง Summit of the Fallen World Tree (#bHard Mode#k) (Lv. 230+)#l\r\n";
        //if (cm.getPlayer().getGMLevel() > 5) {
        //talk+= "#L2#ไปยัง Summit of the Fallen World Tree (#bExtreme Mode#k) (Lv. 290+)#l\r\n";
        //}
        cm.sendSimpleS(talk, 0x26);
    } else if (status == 1) {
        st = selection;
        if (cm.getParty() == null) {
            cm.sendOkS("ต้องมีปาร์ตี้อย่างน้อย 1 คนถึงจะเข้าได้", 0x24);
            cm.dispose();
            return;
        } else if (cm.getPlayerCount(setting[st][2]) >= 1 || cm.getPlayerCount(Number(setting[st][2]) + 40) >= 1 || cm.getPlayerCount(Number(setting[st][2]) + 80) >= 1) {
            cm.sendOkS("มีคนกำลังท้าทาย Damien อยู่แล้ว\r\nกรุณาใช้แชแนลอื่น", 0x24);
            cm.dispose();
            return;
        } else if (!cm.isLeader()) {
            cm.sendOkS("เฉพาะหัวหน้าปาร์ตี้เท่านั้นที่สามารถขอเข้าได้", 0x24);
            cm.dispose();
            return;
        } else if (!cm.allMembersHere()) {
            cm.sendOk("สมาชิกปาร์ตี้ทุกคนต้องมารวมตัวกันที่นี่");
            cm.dispose();
            return;
        }
        if (!cm.isBossAvailable(setting[st][0], setting[st][1])) {
            talk = "#fs11#สมาชิกในปาร์ตี้ "
            for (i = 0; i < cm.BossNotAvailableChrList(setting[st][0], setting[st][1]).length; i++) {
                if (i != 0) {
                    talk += ", "
                }
                talk += "#b#e" + cm.BossNotAvailableChrList(setting[st][0], setting[st][1])[i] + ""
            }
            talk += "#k#n ใช้จำนวนครั้งเข้าเล่นวันนี้หมดแล้ว";
            cm.sendOkS(talk, 0x24);
            cm.dispose();
            return;
        } else if (!cm.isLevelAvailable(setting[st][3])) {
            talk = "สมาชิกในปาร์ตี้ "
            for (i = 0; i < cm.LevelNotAvailableChrList(setting[st][3]).length; i++) {
                if (i != 0) {
                    talk += ", "
                }
                talk += "#b#e" + cm.LevelNotAvailableChrList(setting[st][3])[i] + ""
            }
            talk += "#k#n เลเวลไม่เพียงพอ\r\nDemian Mode " + name[st] + " ต้องมีเลเวล " + setting[st][3] + " ขึ้นไปเท่านั้น";
            cm.sendOkS(talk, 0x24);
            cm.dispose();
            return;
        } else if (!cm.isBossTier(setting[st][4])) {
            talk = "สมาชิกในปาร์ตี้ "
            for (i = 0; i < cm.BossTierChrList(setting[st][4]).length; i++) {
                if (i != 0) {
                    talk += ", "
                }
                talk += "#b#e" + cm.BossTierChrList(setting[st][4])[i] + ""
            }
            talk += "#k#n Boss Rank ไม่เพียงพอ\r\nDemian Mode " + name[st] + " ต้องมี Rank " + setting[st][4] + " ขึ้นไปเท่านั้น";
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