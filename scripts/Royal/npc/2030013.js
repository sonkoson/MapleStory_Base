var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    var count = 1;
    setting = [
        ["Normal_Zakum", count, 280030100],
        ["Chaos_Zakum", count, 280030000]
    ]
    name = ["Normal", "Chaos"]
    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
        st = cm.getPlayer().getMapId() - 211042400;
        if (st > 1 || st < 0) {
            cm.sendOk("NPC ถูกเรียกออกมาในสถานที่ที่ไม่ใช่แท่นบูชาของ Zakum โปรดติดต่อผู้ดูแล");
            cm.dispose();
            return;
        }
        if (cm.getPlayer().getParty() == null) {
            cm.sendNext("ดูเหมือนเจ้าจะยังไม่ได้สร้างปาร์ตี้นะ ต้องมีปาร์ตี้ถึงจะท้าทายได้");
            cm.dispose();
            return;
        }
        talk = "#e<Zakum : โหมด " + name[st] + ">#k#n\r\n"
        talk += "Zakum ฟื้นคืนชีพขึ้นมาแล้ว หากปล่อยไว้แบบนี้ มันตะทำให้ภูเขาไฟระเบิดและเปลี่ยนเทือกเขา El Nath ทั้งหมดให้กลายเป็นนรก\r\n"
        talk += "#r(แท่นบูชาแห่ง " + name[st] + " Zakum สามารถเข้าได้ #eวันละ 3 ครั้ง#n, และบันทึกการเข้าจะ #eรีเซ็ตทุกเที่ยงคืน#n)\r\n#kนอกจากนี้ จำนวนครั้งที่เข้าได้จะเพิ่มขึ้นตามระดับ #bBoss Hunter#k\r\n\r\n";
        talk += "#L0# #bขอเข้าต่อสู้กับ Zakum (สมาชิกปาร์ตี้จะถูกย้ายไปพร้อมกัน)#k";
        cm.sendSimple(talk);
    } else if (status == 1) {
        if (cm.getPlayer().getParty() == null) {
            cm.sendNext("ดูเหมือนเจ้าจะยังไม่ได้สร้างปาร์ตี้นะ ต้องมีปาร์ตี้ถึงจะท้าทายได้");
            cm.dispose();
            return;
        } else if (!cm.allMembersHere()) {
            cm.sendOk("สมาชิกปาร์ตี้ทุกคนต้องอยู่ในแผนที่เดียวกัน");
            cm.dispose();
            return;
        }
        if (!cm.isBossAvailable(setting[st][0], setting[st][1])) {
            talk = "สมาชิกในปาร์ตี้ "
            for (i = 0; i < cm.BossNotAvailableChrList(setting[st][0], setting[st][1]).length; i++) {
                if (i != 0) {
                    talk += ", "
                }
                talk += "#b#e" + cm.BossNotAvailableChrList(setting[st][0], setting[st][1])[i] + ""
            }
            talk += "#k#n วันนี้ได้เข้าสู่แท่นบูชา Zakum ไปแล้ว จึงไม่สามารถเข้าได้อีก";
            cm.sendOk(talk);
            cm.dispose();
            return;
        }
        if (cm.getPlayerCount(setting[st][2]) >= 1) {
            talk = "มีคนกำลังท้าทาย Zakum อยู่แล้ว ลองเปลี่ยนไปแชแนลอื่นดูสิ";
            cm.sendOk(talk);
            cm.dispose();
            return;
        }
        var em = cm.getEventManager(setting[st][0]);
        cm.addBoss(setting[st][0]);
        if (em != null) {
            cm.getEventManager(setting[st][0]).startInstance_Party(setting[st][2] + "", cm.getPlayer());
        }
        cm.dispose();
    }
}