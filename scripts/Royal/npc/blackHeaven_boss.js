var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    var count = 1;
    setting = [
        ["Hard_Lotus", count, 350060700, 230, 2],
        ["Normal_Lotus", count, 350060400, 220, 0],
        ["Extreme_Lotus", 1, 350060700, 290, 0],
    ]
    name = ["Hard", "Normal", "Extreme"]
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        talk = "จะย้ายไปยัง Black Heaven Core เพื่อกำจัด Lotus หรือไม่?\r\n\r\n";
        talk += "#L1#ย้ายไปยัง Black Heaven Core (Normal Mode) (Lv. 220+)#l\r\n";
        talk += "#L0#ย้ายไปยัง Black Heaven Core (Hard Mode) (Lv. 230+)#l\r\n";
        //  talk += "#L2#ย้ายไปยัง Black Heaven Core (Extreme Mode) (Lv. 190+)#l\r\n";
        cm.sendSimpleS(talk, 0x26);
    } else if (status == 1) {
        st = selection;
        if (cm.getParty() == null) {
            cm.sendOkS("ต้องมีปาร์ตี้อย่างน้อย 1 คนจึงจะเข้าได้", 0x24);
            cm.dispose();
            return;
        } else if (cm.getPlayerCount(setting[st][2]) >= 1 || cm.getPlayerCount(Number(setting[st][2] + 100)) >= 1 || cm.getPlayerCount(Number(setting[st][2] + 200)) >= 1 || cm.getPlayerCount(Number(setting[st][2] + 250)) >= 1) {
            cm.sendOkS("มีคนกำลังท้าทาย Lotus อยู่แล้ว\r\nกรุณาใช้แชแนลอื่น", 0x24);
            cm.dispose();
            return;
        } else if (!cm.isLeader()) {
            cm.sendOkS("เฉพาะหัวหน้าปาร์ตี้เท่านั้นที่สามารถขอเข้าได้", 0x24);
            cm.dispose();
            return;
        } else if (!cm.allMembersHere()) {
            cm.sendOk("สมาชิกทุกคนต้องอยู่ที่เดียวกัน");
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
            talk += "#k#n ได้ใช้จำนวนครั้งเข้าของวันนี้หมดแล้ว";
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
            talk += "#k#n เลเวลของคุณไม่เพียงพอ\r\nLotus " + name[st] + " Mode เข้าได้เฉพาะเลเวล " + setting[st][3] + " ขึ้นไปเท่านั้น";
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
            talk += "#k#n Boss Rank ของคุณไม่เพียงพอ\r\nLotus " + name[st] + " Mode เข้าได้เฉพาะ Rank " + setting[st][4] + " ขึ้นไปเท่านั้น";
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