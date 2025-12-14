var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    var count = 1;
    setting = [
        ["Normal_JinHillah", count, 450010400, 265, 6],
        ["Normal_JinHillah", 5, 450010600, 265, 6]
    ]
    name = ["Normal"];
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        talk = "#fs11#ตอนนี้ได้เวลาเข้าสู่ Altar of Desire เพื่อต่อสู้กับ Verus Hilla แล้ว\r\n\r\n";
        talk += "#L0##kเข้าสู้กับ Verus Hilla#k#b (Lv. 265+)#k#l\r\n";
        //talk += "#L1##bVerus Hilla #Cgray#(Practice Mode) #k#r(Lv. 265+)#k#l\r\n";
        cm.sendSimpleS(talk, 2);
    } else if (status == 1) {
        st = selection;
        if (cm.getParty() == null) {
            cm.sendOkS("ต้องมีปาร์ตี้ 1 คนขึ้นไปจึงจะเข้าได้", 0x26);
            cm.dispose();
            return;
        } else if (cm.getPlayerCount(450010400) >= 1 || cm.getPlayerCount(450010500) >= 1 || cm.getPlayerCount(450010600) >= 1 || cm.getPlayerCount(450010550) >= 1) {
            cm.sendOkS("มีคนกำลังท้าทาย Verus Hilla อยู่แล้ว\r\nกรุณาใช้แชแนลอื่น", 0x26);
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
            talk = "#fs11#สมาชิกในปาร์ตี้ "
            for (i = 0; i < cm.BossNotAvailableChrList(setting[st][0], setting[st][1]).length; i++) {
                if (i != 0) {
                    talk += ", "
                }
                talk += "#b#e" + cm.BossNotAvailableChrList(setting[st][0], setting[st][1])[i] + ""
            }
            talk += "#k#n ใช้จำนวนครั้งเข้าหมดแล้ว";
            cm.sendOkS(talk, 0x26);
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
            talk += "#k#n เลเวลไม่เพียงพอ\r\nVerus Hilla " + name[st] + " Mode ต้องมีเลเวล " + setting[st][3] + " ขึ้นไป";
            cm.sendOkS(talk, 0x26);
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
            talk += "#k#n Boss Rank ไม่เพียงพอ\r\nVerus Hilla " + name[st] + " Mode ต้องมี Rank " + setting[st][4] + " ขึ้นไป";
            cm.sendOkS(talk, 0x26);
            cm.dispose();
            return;
        } else {
            if (st == 0) {
                cm.warpParty(setting[st][2]);
                cm.dispose();
                cm.openNpc(3003771);
            } else if (st == 1) {
                cm.sendYesNoS("เลือกเข้าสู่โหมดฝึกซ้อม ในโหมดฝึกซ้อม #b#eจะไม่ได้รับ EXP และของรางวัล#n#k และเข้าได้เพียง #b#eวันละ 5 ครั้ง#k#n เท่านั้น โดยไม่คำนึงถึงชนิดบอส ต้องการเข้าหรือไม่?", 4, 2007);
            } else {
                cm.sendYesNoS("เลือกเข้าสู่โหมด Extreme ในโหมด Extreme ความเสียหายจะลดลง 70% แต่ #b#eไอเท็มอุปกรณ์รางวัลที่ดรอปจะมี Option พิเศษที่แข็งแกร่งกว่า#n#k Option พิเศษของอุปกรณ์ดังกล่าว #b#eจะหายไปเมื่อใช้ Innocent Scroll และไม่สามารถกู้คืนได้#k#n ต้องการเข้าหรือไม่?", 4, 2007);
            }

        }
    } else if (status == 2) {
        cm.dispose();
        if (st == 1) {
            cm.warpParty(setting[st][2]);
            cm.dispose();
            cm.openNpc(3003771);
        }
    }
}