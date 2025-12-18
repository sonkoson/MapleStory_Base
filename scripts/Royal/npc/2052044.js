var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    var count = 1;
    setting = [
        ["Normal_Kawoong", count, 221030910, 180]
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
        msg = "#e<Kawoong Normal Mode>#n\r\n\r\n"
        msg += "กล้าดีที่มาถึงที่นี่!! อยากลิ้มรส Kawoong รุ่นต่อสู้ที่อัพเกรดใหม่หรือเปล่า!\r\n"
        msg += "#r(Kawoong (Normal) สามารถเข้าได้ #eวันละ " + setting[0][1] + " ครั้ง#n และบันทึกการเข้าจะ #eรีเซ็ตทุกเที่ยงคืน#n)#k\r\n\r\n"
        msg += "#L0# #bขอเข้าสู้ Kawoong (ปาร์ตี้จะย้ายพร้อมกัน)#k#l"
        cm.sendSimple(msg);
    } else if (status == 1) {
        st = selection;
        if (cm.getParty() == null) {
            cm.sendOk("ต้องมีปาร์ตี้อย่างน้อย 1 คนถึงจะเข้าได้");
            cm.dispose();
            return;
        } else if (cm.getPlayerCount(setting[st][2]) >= 1) {
            cm.sendOk("มีคนกำลังสู้ Kawoong อยู่แล้ว\r\nกรุณาไปช่องอื่น");
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
            talk = "มีสมาชิกปาร์ตี้ที่เข้าแล้ววันนี้:\r\n\r\n"
            for (i = 0; i < cm.BossNotAvailableChrList(setting[st][0], setting[st][1]).length; i++) {
                talk += "#b#e-" + cm.BossNotAvailableChrList(setting[st][0], setting[st][1])[i] + "\r\n"
            }
            cm.sendOk(talk);
            cm.dispose();
            return;
        } else if (!cm.isLevelAvailable(setting[st][3])) {
            talk = "มีสมาชิกปาร์ตี้ที่เลเวลไม่ถึงสำหรับท้าทาย Kawoong:\r\n"
            for (i = 0; i < cm.LevelNotAvailableChrList(setting[st][3]).length; i++) {
                talk += "#b#e-" + cm.LevelNotAvailableChrList(setting[st][3])[i] + "\r\n"
            }
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