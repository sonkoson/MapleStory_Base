var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    var count = 1;
    setting = [
        ["Normal_Pinkbean", count, 270050100, 160],
        ["Chaos_Pinkbean", count, 270051100, 170]
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
        if (cm.getPlayer().getMapId() == 270050000) {
            if (cm.getPlayer().getParty() == null) {
                cm.sendOk("ต้องมีปาร์ตี้ถึงจะเข้าได้");
                cm.dispose();
                return;
            }
            if (!cm.isLeader()) {
                cm.sendOk("เฉพาะหัวหน้าปาร์ตี้เท่านั้นที่ขอเข้าได้");
                cm.dispose();
                return;
            }
            talk = "#e<Boss: Pink Bean>#n\r\n"
            talk += "ผู้บุกรุกดูเหมือนจะมุ่งหน้าไปยังแท่นบูชาของเทพธิดาแล้ว ถ้าไม่หยุดเขาเร็วๆ นี้ จะเกิดเรื่องน่ากลัวขึ้น\r\n\r\n"
            //talk += "#L0# #b<Boss: Pink Bean> ขอเข้าสู้บอส";
            cm.sendSimple(talk);
        } else {
            cm.sendYesNo("ต้องการออกจากแท่นบูชา Pink Bean หลังจบการต่อสู้หรือเปล่า?");
        }
    } else if (status == 1) {
        if (cm.getPlayer().getMapId() == 270050000) {
            talk = "#e<Boss: Pink Bean>#n\r\n"
            talk += "กรุณาเลือกโหมดที่ต้องการ\r\n\r\n"
            talk += "#L0#Normal Mode (Level 160 ขึ้นไป)#l\r\n";
            //talk += "#L1#Chaos Mode (Level 170 ขึ้นไป)#l\r\n";
            cm.sendSimple(talk);
        } else {
            cm.warp(270050000);
            cm.dispose();
        }
    } else if (status == 2) {
        st = selection;
        if (cm.getPlayer().getParty() == null) {
            cm.sendOk("ต้องมีปาร์ตี้ถึงจะเข้าได้");
            cm.dispose();
            return;
        } else if (!cm.isLeader()) {
            cm.sendOk("เฉพาะหัวหน้าปาร์ตี้เท่านั้นที่ขอเข้าได้");
            cm.dispose();
            return;
        } else if (cm.getPlayerCount(setting[st][2]) >= 1) {
            cm.sendNext("มีปาร์ตี้อื่นกำลังท้าทาย Pink Bean อยู่แล้ว");
            cm.dispose();
            return;
        } else if (!cm.allMembersHere()) {
            cm.sendOk("สมาชิกทุกคนต้องอยู่ที่เดียวกัน");
            cm.dispose();
            return;
        }
        if (!cm.isBossAvailable(setting[st][0], setting[st][1])) {
            talk = "#fs11#สมาชิกปาร์ตี้ #b#e"
            for (i = 0; i < cm.BossNotAvailableChrList(setting[st][0], setting[st][1]).length; i++) {
                if (i != 0) {
                    talk += ", "
                }
                talk += "#b#e" + cm.BossNotAvailableChrList(setting[st][0], setting[st][1])[i] + ""
            }
            talk += "#k#n ใช้จำนวนครั้งเข้าวันนี้หมดแล้ว";
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
            talk += "#k#n เลเวลไม่ถึง <Boss: Pink Bean> ต้อง Level " + setting[st][3] + " ขึ้นไปถึงจะท้าทายได้";
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