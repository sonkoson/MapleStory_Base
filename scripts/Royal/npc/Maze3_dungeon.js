var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    var count = 1;
    setting = [
        ["Normal_JinHillah", count, 450010400, 250],
        ["Normal_JinHillah", count, 450010400, 250]
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
        talk = "ตอนนี้ต้องเข้าไปยัง Altar of Desire เพื่อต่อสู้กับ Verus Hilla\r\n\r\n";
        talk += "#L0##bเข้าสู่ Verus Hilla#k#r (เลเวล 250 ขึ้นไป)#k#l\r\n";
        //talk += "#L1##bVerus Hilla #Cgray#(Practice Mode) #k#r(Level 250+)#k#l\r\n";
        cm.sendSimpleS(talk, 2);
    } else if (status == 1) {
        st = selection;
        if (cm.getParty() == null) {
            cm.sendOkS("ต้องมีปาร์ตี้ 1 คนขึ้นไปจึงจะเข้าได้", 0x26);
            cm.dispose();
            return;
        } else if (cm.getPlayerCount(450010400) >= 1 || cm.getPlayerCount(450010500) >= 1 || cm.getPlayerCount(450010600) >= 1) {
            cm.sendOkS("มีคนกำลังท้าทาย Verus Hilla อยู่แล้ว\r\nกรุณาใช้แชแนลอื่น", 0x26);
            cm.dispose();
            return;
        } else if (!cm.isLeader()) {
            cm.sendOkS("หัวหน้าปาร์ตี้เท่านั้นที่สามารถขอเข้าได้", 0x26);
            cm.dispose();
            return;
        } else if (!cm.allMembersHere()) {
            cm.sendOk("สมาชิกทุกคนต้องอยู่ที่เดียวกัน");
            cm.dispose();
            return;
        }
        if (!cm.isBossAvailable(setting[st][0], setting[st][1])) {
            talk = "#fs11#สมาชิกปาร์ตี้ "
            for (i = 0; i < cm.BossNotAvailableChrList(setting[st][0], setting[st][1]).length; i++) {
                if (i != 0) {
                    talk += ", "
                }
                talk += "#b#e" + cm.BossNotAvailableChrList(setting[st][0], setting[st][1])[i] + ""
            }
            talk += "#k#n ใช้จำนวนการเข้าของวันนี้หมดแล้ว";
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
            talk += "#k#n เลเวลไม่เพียงพอ Verus Hilla เข้าได้เฉพาะเลเวล " + setting[st][3] + " ขึ้นไปเท่านั้น";
            cm.sendOkS(talk, 0x26);
            cm.dispose();
            return;
        } else {
            if (st == 0) {
                cm.warpParty(setting[st][2]);
                cm.dispose();
                cm.openNpc(3003771);
            } else if (st == 1) {
                cm.sendYesNoS("คุณเลือกเข้าสู่โหมดฝึกซ้อม ในโหมดฝึกซ้อม #b#eจะไม่ได้รับ EXP และรางวัล#n#k และสามารถเข้าได้ #b#eวันละ 5 ครั้ง#k#n เท่านั้น เข้าหรือไม่?", 4, 2007);
            } else {
                cm.sendYesNoS("คุณเลือกเข้าสู่โหมด Extreme ในโหมด Extreme ดาเมจจะลดลง 70% แต่ #b#eรางวัลอุปกรณ์ที่แข็งแกร่งกว่าจะดรอปพร้อมออปชั่นเพิ่มเติม#n#k ออปชั่นเพิ่มเติมของอุปกรณ์นั้น #b#eจะหายไปเมื่อใช้ Innocent Scroll และไม่สามารถกู้คืนได้#k#n เข้าหรือไม่?", 4, 2007);
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