var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    var count = 1;
    setting = [
        ["Hard_Magnus", count, 401060100, 175],
        ["Normal_Magnus", count, 401060200, 155]
    ]
    name = ["Hard", "Normal"]
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        talk = "ต้องการย้ายไปบัลลังก์ทรราชย์เพื่อปราบ Magnus หรือเปล่า?\r\n"
        talk += "#L1##bย้ายไปบัลลังก์ทรราชย์ (Normal) (Level 155 ขึ้นไป)#l\r\n"
        talk += "#L0#ย้ายไปบัลลังก์ทรราชย์ (Hard) (Level 175 ขึ้นไป)#l\r\n"
        talk += "#L3#ไม่ย้าย#l"
        cm.sendSimple(talk);
    } else if (status == 1) {
        st = selection;
        if (cm.getPlayer().getParty() == null) {
            cm.sendOk("ต้องมีปาร์ตี้อย่างน้อย 1 คนถึงจะเข้าได้");
            cm.dispose();
            return;
        } else if (!cm.isLeader()) {
            cm.sendOk("เฉพาะหัวหน้าปาร์ตี้เท่านั้นที่ขอเข้าได้");
            cm.dispose();
            return;
        } else if (cm.getPlayerCount(setting[st][2]) >= 1) {
            cm.sendOk("มีคนกำลังสู้ Magnus อยู่แล้ว");
            cm.dispose();
            return;
        } else if (!cm.allMembersHere()) {
            cm.sendOk("สมาชิกทุกคนต้องอยู่ที่เดียวกัน");
            cm.dispose();
            return;
        }
        if (!cm.isBossAvailable(setting[st][0], setting[st][1])) {
            c = 1;
            cm.sendNext("มีสมาชิกปาร์ตี้ที่ใช้จำนวนครั้งเข้าบัลลังก์ทรราชย์ (" + name[st] + " Mode) หมดแล้ว");
        } else if (!cm.isLevelAvailable(setting[st][3])) {
            c = 2;
            cm.sendNext("บัลลังก์ทรราชย์ (" + name[st] + " Mode) ต้อง Level " + setting[st][3] + " ขึ้นไปถึงจะเข้าได้");
        } else {
            cm.addBoss(setting[st][0]);
            em = cm.getEventManager(setting[st][0]);
            if (em != null) {
                cm.getEventManager(setting[st][0]).startInstance_Party(setting[st][2] + "", cm.getPlayer());
            }
            cm.dispose();
        }
    } else if (status == 2) {
        talk = "สมาชิกปาร์ตี้ #b#e"
        if (c == 1) {
            for (i = 0; i < cm.BossNotAvailableChrList(setting[st][0], setting[st][1]).length; i++) {
                if (i != 0) {
                    talk += ", "
                }
                talk += "#b#e" + cm.BossNotAvailableChrList(setting[st][0], setting[st][1])[i] + ""
            }
        } else {
            for (i = 0; i < cm.LevelNotAvailableChrList(setting[st][3]).length; i++) {
                if (i != 0) {
                    talk += ", "
                }
                talk += "#b#e" + cm.LevelNotAvailableChrList(setting[st][3])[i] + ""
            }
        }
        talk += "#k#n ไม่มีคุณสมบัติที่จะเข้าได้";
        cm.sendNext(talk);
        cm.dispose();
    }
}