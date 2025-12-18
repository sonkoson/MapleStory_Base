importPackage(java.lang);
importPackage(Packages.database);

var status = -1;
var s = -1;

function start() {
    s = -1;
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, sel) {
    if (mode == 0 && sel == -1 && type == 6) {
        cm.dispose();
        return;
    }
    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        status--;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
        con = DBConnection.getConnection();
        ps = con.prepareStatement("SELECT phonenumber FROM accounts WHERE id = '" + cm.getPlayer().getAccountID() + "'");
        rs = ps.executeQuery();
        while (rs.next()) {
            phonenumber = rs.getString("phonenumber");
        }
        rs.close();
        ps.close();
        con.close();

        if (phonenumber == "010-7777-7777") {
            cm.dispose();
            cm.getPlayer().setKeyValue(19770, "elapsed", 179);
            cm.getPlayer().setKeyValue(19770, "count", 1);
            return;
        }
        var text = "#fs11##e#h0##n มีจำนวนครั้งที่เข้า #bห้องแห่งความจริง#k สะสมทั้งหมด " + cm.getPlayer().getOneInfoQuestInteger(19770, "count") + " ครั้ง\r\n\r\n";
        text += "#L0#ฟังคำอธิบายเกี่ยวกับ #bห้องแห่งความจริง#k#l\r\n#L1#ทำไมฉันถึงมาอยู่ที่ #bห้องแห่งความจริง#k?#l";
        cm.sendSimple(text);
    } else if (status == 1) {
        if (s == -1) {
            s = sel;
        }
        if (s == 0) {
            cm.sendNext("#fs11##bห้องแห่งความจริง#k คือสถานที่ที่จะถูกย้ายมาเมื่อ #rตอบคำถามเครื่องจับโกหกผิด#k และถูกตรวจพบว่าสงสัยว่าใช้ Macro");
        } else if (s == 1) {
            cm.sendSimple("#fs11#" + cm.getPlayer().getOneInfoQuest(19770, "reason") + "\r\n\r\n#L0#ฟังคำอธิบายเกี่ยวกับ #rเครื่องจับโกหก#k เพิ่มเติม#l");
        }
    } else if (status == 2) {
        if (s == 0) {
            cm.sendNextPrev("#fs11#ตัวละครที่เข้ามาในห้องนี้ต้องออนไลน์ต่อเนื่องตาม #rเวลาที่เหลือ#k ที่แสดงบนหน้าจอด้านบน จึงจะออกไปได้");
        } else if (s == 1) {
            cm.sendNext("#fs11##rเครื่องจับโกหก#k คือระบบตรวจจับ Macro ที่จะถูกเปิดใช้งานเมื่อผู้เล่นคนอื่นใช้ไอเทมกับตัวละครที่สงสัยว่าใช้ Macro หรือเปิดใช้งานอัตโนมัติ");
        }
    } else if (status == 3) {
        if (s == 0) {
            cm.sendNextPrev("#fs11#ภายใน #bห้องแห่งความจริง#k จะถูกจำกัดสิ่งต่อไปนี้:\r\n\r\n#r  - การเคลื่อนที่และใช้สกิล\r\n  - การแชท\r\n  - การใช้ไอเทม\r\n  - Cash Shop/Maple Auction\r\n  - การเข้าร่วมคอนเทนท์และอีเวนท์ในเกม");
        } else if (s == 1) {
            cm.sendNextPrev("#fs11#เมื่อ #rเครื่องจับโกหก#k เปิดใช้งาน ต้องพิมพ์ข้อความที่แสดงบนหน้าจอให้ถูกต้องภายในเวลาที่กำหนด");
        }
    } else if (status == 4) {
        if (s == 0) {
            cm.sendNextPrev("#fs11#เวลาที่ต้องอยู่ใน #bห้องแห่งความจริง#k จะเพิ่มขึ้นตามจำนวนครั้งที่เข้าสะสม และจะ #eรีเซ็ต#n ทุก #bเที่ยงคืนวันจันทร์#k\r\n#r(จำนวนครั้งที่เข้าห้องแห่งความจริงจะนับรวมทุกตัวละครในบัญชี)");
        } else if (s == 1) {
            cm.sendNextPrev("#fs11#มีโอกาสตอบทั้งหมด #e4 ครั้ง#n ดังนั้นต้องตั้งใจดูให้ดีเพื่อตอบให้ถูกภายในเวลาที่กำหนดนะ");
        }
    } else if (status == 5) {
        cm.dispose();
        if (s == 0) {
            cm.sendPrev("#fs11#ต่อไปกรุณาระวัง #rเครื่องจับโกหก#k เพื่อที่จะได้ไม่ต้องมา #bห้องแห่งความจริง#k อีกนะ");
        } else if (s == 1) {
            cm.sendPrev("#fs11#หลังจากทำความเข้าใจเรื่องข้างต้นแล้ว กรุณาระวังไม่ให้ต้องเข้ามา #bห้องแห่งความจริง#k อีกนะ");
        }
    }
}