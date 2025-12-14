var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    var count = 1;
    setting = [
        ["Normal_Pierre", count, 105200200, 100],
        ["Chaos_Pierre", count, 105200600, 210]
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
        talk = "#r#e<ทางเข้าสวนแห่ง Root Abyss (South)>#k#n\r\n"
        talk += "ประตูสู่สวนที่ #rPierre#k ผู้พิทักษ์ผนึกทางใต้ของ Root Abyss เฝ้าอยู่ #rบันทึกการเคลียร์จะถูกรีเซ็ตตอนเที่ยงคืน#k#n\r\n\r\n"
        talk += "#L0##i4033611##bใช้ #z4033611# เพื่อเข้าสู่โหมด Normal#l\r\n"
        talk += "#L1##i4033611##bใช้ #z4033611# เพื่อเข้าสู่โหมด Chaos#l\r\n"
        cm.sendSimple(talk);
    } else if (status == 1) {
        st = selection;
        if (cm.getPlayer().getParty() == null) {
            cm.sendOk("ต้องมีปาร์ตี้อย่างน้อย 1 คนถึงจะเข้าได้");
            cm.dispose();
            return;
        } else if (!cm.isLeader()) {
            cm.sendOk("เฉพาะหัวหน้าปาร์ตี้เท่านั้นที่สามารถขอเข้าดันเจี้ยนได้");
            cm.dispose();
            return;
        } else if (cm.getPlayerCount(setting[st][2] + 10) >= 1 || cm.getPlayerCount(setting[st][2]) >= 1) {
            cm.sendNext("มีปาร์ตี้อื่นกำลังท้าทาย Pierre อยู่ข้างในแล้ว");
            cm.dispose();
            return;
        } else if (!cm.allMembersHere()) {
            cm.sendOk("สมาชิกปาร์ตี้ทุกคนต้องอยู่ในแผนที่เดียวกัน");
            cm.dispose();
            return;
        }
        if (!cm.partyhaveItem(4033611, 1)) {
            talk = "มีสมาชิกในปาร์ตี้ที่ไม่มีไอเท็ม #i4033611# #b#z4033611##k";
            cm.sendOk(talk);
            cm.dispose();
            return;
        }
        if (!cm.isBossAvailable(setting[st][0], setting[st][1])) {
            talk = "สมาชิกในปาร์ตี้ #b#e"
            for (i = 0; i < cm.BossNotAvailableChrList(setting[st][0], setting[st][1]).length; i++) {
                if (i != 0) {
                    talk += ", "
                }
                talk += "#b#e" + cm.BossNotAvailableChrList(setting[st][0], setting[st][1])[i] + ""
            }
            talk += "#k#n เข้าดันเจี้ยนวันนี้ไปแล้ว Pierre สามารถท้าทายได้วันละ " + setting[st][1] + " ครั้งเท่านั้น";
            cm.sendOk(talk);
            cm.dispose();
            return;
        } else if (!cm.isLevelAvailable(setting[st][3])) {
            talk = "สมาชิกในปาร์ตี้ #b#e"
            for (i = 0; i < cm.LevelNotAvailableChrList(setting[st][3]).length; i++) {
                if (i != 0) {
                    talk += ", "
                }
                talk += "#b#e" + cm.LevelNotAvailableChrList(setting[st][3])[i] + ""
            }
            talk += "#k#n เลเวลไม่ถึงเกณฑ์ Pierre สามารถท้าทายได้ตั้งแต่เลเวล " + setting[st][3] + " ขึ้นไป";
        } else {
            cm.givePartyItems(4033611, -1);
            cm.addBoss(setting[st][0]);
            em = cm.getEventManager(setting[st][0]);
            if (em != null) {
                cm.getEventManager(setting[st][0]).startInstance_Party(setting[st][2] + "", cm.getPlayer());
            }
            cm.dispose();
        }
    }
}