importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);
importPackage(java.text);

importPackage(Packages.objects.item);
importPackage(Packages.objects.users);
importPackage(Packages.objects.utils);
importPackage(Packages.constants);
importPackage(Packages.network.models);
importPackage(Packages.objects.wz.provider);
importPackage(Packages.objects.fields);
importPackage(Packages.objects.fields.gameobject);
importPackage(Packages.objects.fields.gameobject.lifes);
importPackage(Packages.database);

var nf = NumberFormat.getInstance();
var point = "";
var point2 = "";
var realName = "";
var event = false;
var sel = -1;
var sel2 = -1;
var sel3 = -1;

var cashnumber = "";
var cancelname = "";
var cancelcash = 0;
var cashcount = 0;
var VIP = false;
var havecashnumber = false;

function start() {
    point = "";
    point2 = "";
    realName = "";
    event = "";
    status = -1;
    sel = -1;
    sel2 = -1;
    sel3 = -1;
    action(1, 0, 0);
}

function CheckVIP() {
    con = DBConnection.getConnection();
    ps = con.prepareStatement("SELECT * FROM donation_request WHERE status = 1 AND account_name = '" + cm.getClient().getAccountName() + "'");
    rs = ps.executeQuery();
    while (rs.next()) {
        cashcount++
    }
    rs.close();
    ps.close();
    con.close();

    // Total charge 500,000 or more OR 3+ successful charges = VIP
    if (cm.getClient().getKeyValue("DPointAll") >= 500000 || cashcount >= 3) {
        VIP = true;
    } else {
        VIP = false;
    }
}

function CheckCashnumber() {
    con = DBConnection.getConnection();
    ps = con.prepareStatement("SELECT cashnumber FROM accounts WHERE name = '" + cm.getClient().getAccountName() + "'");
    rs = ps.executeQuery();
    while (rs.next()) {
        cashnumber = rs.getString("cashnumber");
    }

    if (cashnumber != null && cashnumber != "") {
        havecashnumber = true;
    }
    rs.close();
    ps.close();
    con.close();
}

function CheckRepeat(name, cash) {
    Check = false;
    con = DBConnection.getConnection();
    ps = con.prepareStatement("SELECT real_name, point FROM donation_request WHERE status = 0 AND real_name = '" + name + "' AND point = " + cash);
    rs = ps.executeQuery();
    while (rs.next()) {
        Check = true;
    }
    rs.close();
    ps.close();
    con.close();
    return Check;
}

function action(mode, type, selection) {
    if (mode == 0 && type == 3 && selection == -1 && status >= 5) {
        display();
        return;
    }
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;


        if (status == 0) {
            // Set cumulative amount to 0 on first charge
            if (cm.getClient().getKeyValue("DPointAll") == null)
                cm.getClient().setKeyValue("DPointAll", "0");

            // Temporary charging disabled
            cantcharging = false;

            // VIP Check
            CheckVIP()

            // Virtual account check
            CheckCashnumber()

            /*
            if (!VIP) { // If not VIP
                cm.sendOk("#fs11##r#e[Cash Charging Info]#n\r\n\r\n#fc0xFF000000#Temporarily unavailable for non-#fc0xFFFF3366##eVIP#n#fc0xFF000000# users.\r\nPlease try again later.");
                cm.dispose();
                return;
            }
            */

            var msg = "#fs11#สวัสดีครับ ผมชื่อ #bSell#k รับผิดชอบเรื่องการเติม Cash ใน Gangrim World\r\n\r\n";
            msg += "#b#L0#ขอเติม Cash ด้วย [โอนเงินผ่านธนาคาร]#l\r\n";
            msg += "#b#L3#ขอเติม Cash ด้วย [Mobile Culture Voucher]#l\r\n";
            //msg += "#L1#อยากดูรายการเติมเงินที่ขอไว้#l\r\n";
            msg += "#L2#อยากดูจำนวนสะสมและประวัติการเติม#l";

            cm.sendSimple(msg);
        } else if (status == 1) {
            sel = selection;
            if (selection == 0) {
                if (cantcharging) {
                    cm.sendOk("#fs11##r#e[แจ้งการเติม Cash]#n\r\n\r\n#fc0xFF000000#ปิดให้บริการเติมเงินชั่วคราว\r\nกรุณาลองใหม่ภายหลัง\r\n\r\nเนื่องจากไม่สามารถแจ้งวันที่เปิดให้บริการได้\r\nกรุณาอย่าสอบถามผ่าน Customer Service ว่า #e'เมื่อไหร่จะเปิด?'#n, #e'ตอนนี้ไม่ได้เหรอ?'#n\r\n#rจะไม่ตอบคำถามเหล่านี้#k\r\n\r\n#fc0xFF000000##eจะพยามเปิดให้บริการโดยเร็วที่สุด#n\r\nขออภัยในความไม่สะดวก");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getAccountTotalLevel() < 8000) {
                    cm.sendOk("#fs11##fc0xFF000000#ต้องมี Union Level รวมในบัญชี 8000 ขึ้นไปถึงจะขอเติมได้");
                    cm.dispose();
                    return;
                }
                if (!cm.canDonationRequest()) {
                    cm.sendOk("#fs11##fc0xFF000000#มีรายการเติมเงินที่ยังไม่ดำเนินการอยู่ กรุณารอจน #eรายการนั้นเสร็จสิ้น#n หรือ #eยกเลิกรายการนั้น#n แล้วลองใหม่ #bสามารถยกเลิกได้ที่ 'อยากดูรายการเติมเงินที่ขอไว้'#k");
                    cm.dispose();
                    return;
                }
                cm.sendYesNo("#fs11##fc0xFF000000#จะเริ่มเติม Cash ด้วยการโอนเงินผ่านธนาคารไหม?\r\n#eถ้าขอแล้วไม่โอนเงินซ้ำๆ หรือขอเล่นๆ อาจถูกลงโทษได้#n");


            } else if (selection == 3) {
                if (cantcharging) {
                    cm.sendOk("#fs11##r#e[แจ้งการเติม Cash]#n\r\n\r\n#fc0xFF000000#ปิดให้บริการเติมเงินชั่วคราว\r\nกรุณาลองใหม่ภายหลัง\r\n\r\nเนื่องจากไม่สามารถแจ้งวันที่เปิดให้บริการได้\r\nกรุณาอย่าสอบถามผ่าน Customer Service ว่า #e'เมื่อไหร่จะเปิด?'#n, #e'ตอนนี้ไม่ได้เหรอ?'#n\r\n#rจะไม่ตอบคำถามเหล่านี้#k\r\n\r\n#fc0xFF000000##eจะพยามเปิดให้บริการโดยเร็วที่สุด#n\r\nขออภัยในความไม่สะดวก");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getAccountTotalLevel() < 8000) {
                    cm.sendOk("#fs11##fc0xFF000000#ต้องมี Union Level รวมในบัญชี 8000 ขึ้นไปถึงจะขอเติมได้");
                    cm.dispose();
                    return;
                }
                if (!cm.canDonationRequest()) {
                    cm.sendOk("#fs11##fc0xFF000000#มีรายการเติมเงินที่ยังไม่ดำเนินการอยู่ กรุณารอจน #eรายการนั้นเสร็จสิ้น#n หรือ #eยกเลิกรายการนั้น#n แล้วลองใหม่ #bสามารถยกเลิกได้ที่ 'อยากดูรายการเติมเงินที่ขอไว้'#k");
                    cm.dispose();
                    return;
                }
                cm.sendYesNo("#fs11##fc0xFF000000#จะเริ่มเติม Cash ด้วย [Mobile] Culture Voucher ไหม?\r\n#eถ้าขอแล้วไม่โอนเงินซ้ำๆ หรือขอเล่นๆ อาจถูกลงโทษได้#n\r\n#rเติมได้เฉพาะ Mobile Culture Voucher รหัส PIN 16 หลักเท่านั้น#k");


            } else if (selection == 1) {
                if (!cm.displayDonationRequest()) {
                    cm.dispose();
                    return;
                }
            } else if (selection == 2) {
                cm.displayDonationLog();
                cm.dispose();
            }

        } else if (status == 2) {
            if (sel == 0) {
                // Get hour and minute
                today = new Date();
                hours = today.getHours();
                minutes = today.getMinutes();

                // Maintenance notice
                if (hours >= 23 && minutes >= 30 || hours <= 00 && minutes <= 30) {
                    cm.sendOk("#fs11##r#e[แจ้งปิดปรับปรุง]#k#n\r\nทุกวัน 23:30 ~ 00:30\r\nเป็นช่วงเวลาปิดปรับปรุงระบบธนาคาร");
                    cm.dispose();
                    return;
                }

                var msg = "#fs11##fc0xFF000000#กรุณากรอกจำนวนเงินที่ต้องการเติมให้ถูกต้อง\r\n#r#e※ 10,000 วอนขึ้นไป โดยเติมทีละ 10,000 วอน #b#eตัวอย่าง) 50000";
                cm.sendGetNumber(msg, 0, 10000, 2000000);
            } else if (sel == 3) {
                today = new Date();
                hours = today.getHours();
                minutes = today.getMinutes();

                var msg = "#fs11##fc0xFF000000#กรุณากรอกจำนวนเงินรวมของ Mobile Culture Voucher ที่ต้องการเติมให้ถูกต้อง\r\n#r#e※ 10,000 วอนขึ้นไป โดยเติมทีละ 10,000 วอน #b#eตัวอย่าง) 50000";
                cm.sendGetNumber(msg, 0, 10000, 2000000);

            } else if (sel == 1) {
                // Cancel charging
                sel2 = selection / 12438;
                checkcancelcash(sel2);
                cm.askDeleteDonationRequest(sel2);
            }
        } else if (status == 3) {
            if (sel == 0) {
                point = selection;
                var msg = "#fs11##fc0xFF000000#กรุณากรอกชื่อผู้โอนเงินให้ถูกต้อง\r\n#r#e※ กรุณาใส่ชื่อในบัญชีธนาคารตามจริง";
                cm.sendGetText(msg);
            } else if (sel == 3) {
                point = selection;
                var msg = "#fs11##fc0xFF000000#กรุณากรอกอีเมลผู้ส่งให้ถูกต้อง\r\n#r#e※ กรุณาใส่อีเมลของตัวเองตามจริง";
                cm.sendGetText(msg);
            } else if (sel == 1) {
                // Cancel charging
                if (!cm.deleteDonationRequest(sel2)) {
                    cm.sendNext("#fs11##fc0xFF000000#รายการนี้ดำเนินการเสร็จสิ้นแล้ว ไม่สามารถยกเลิกได้");
                    cm.dispose();
                    return;
                }
                cm.addCustomLog(101, "[Cash Charge] Cancel Amount: " + nf.format(cancelcash) + ", Name: " + cancelname);
            }
        } else if (status == 4) {
            realName = cm.getText();


            if (sel == 0) {
                if (!havecashnumber) {
                    cm.sendOk("กรุณาเข้าเว็บไซต์ด้านล่างผ่าน Browser เพื่อยืนยันตัวตนและรับหมายเลขบัญชีเสมือน แล้วค่อยกลับมาขอเติมใหม่\r\n\r\n#bhttps://mapleroyal.cc/cash");
                    cm.dispose();
                    return;
                }
                if (!realName.matches("^[가-힝]*$")) {
                    cm.sendOk("#fs11#ชื่อผู้โอนเงินต้องเป็นภาษาเกาหลีเท่านั้น\r\n\r\nชื่อที่กรอก: " + realName + "");
                    cm.dispose();
                    return;
                }

                if (realName.length() < 2 || realName.length() > 3) {
                    cm.sendOk("#fs11#ชื่อผู้โอนเงินต้องเป็นภาษาเกาหลี 2~3 ตัวอักษร\r\n\r\nชื่อที่กรอก: " + realName + "");
                    cm.dispose();
                    return;
                }

                if (CheckRepeat(realName, point)) {
                    cm.sendOk("#fs11#มีรายการของคนอื่นที่ใช้ชื่อผู้โอนและจำนวนเงินเดียวกันอยู่\r\nกรุณาเปลี่ยนจำนวนเงิน หรือรอจนรายการของคนอื่นเสร็จสิ้นแล้วค่อยขอใหม่\r\n\r\n#rถ้าข้อความนี้แสดงนานเกินไป กรุณาติดต่อ Customer Service");
                    cm.dispose();
                    return;
                }

                if (point % 10000 != 0 || point > 2000000) {
                    cm.sendOk("#fs11##r※ เติม Cash ได้ 10,000 วอนขึ้นไป โดยเติมทีละ 10,000 วอน สูงสุด 2,000,000 วอนต่อครั้ง");
                    cm.dispose();
                    return;
                }

                var msg = "#fs11##fc0xFF000000##e<ข้อมูลที่กรอก>#n\r\n";
                msg += "#bจำนวนเงิน: " + point;
                msg += "\r\nชื่อผู้โอน: " + realName;
                msg += "\r\n\r\n#fs14##e<ข้อควรทราบ>#fs11#";
                msg += "\r\n#fc0xFF000000#1. ต้อง #rโอนเงินทันที#k หลังจากได้รับหมายเลขบัญชี";
                msg += "\r\n#fc0xFF000000#2. เมื่อชื่อผู้โอนและจำนวนเงินตรงกับที่ขอ จะเติมให้อัตโนมัติ (ต้องใช้ชื่อบัญชีจริง)";
                msg += "\r\n#fc0xFF000000#3. หากหมายเลขบัญชีไม่ตรง กรุณาติดต่อ Customer Service";
                msg += "\r\n\r\n#k#rหากฝ่าฝืนข้อข้างต้น อาจถูกแบนถาวรได้";
                msg += "\r\n#k#bหากเห็นด้วยและต้องการดำเนินการเติม Cash ต่อ\r\nกรุณาพิมพ์ #r'동의합니다'#b (ฉันยินยอม)";
                cm.sendGetText(msg);


            } else if (sel == 3) {
                if (CheckRepeat(realName, point)) {
                    cm.sendOk("#fs11#มีรายการของคนอื่นที่ใช้ชื่อผู้โอนและจำนวนเงินเดียวกันอยู่\r\nกรุณาเปลี่ยนจำนวนเงิน หรือรอจนรายการของคนอื่นเสร็จสิ้นแล้วค่อยขอใหม่\r\n\r\n#rถ้าข้อความนี้แสดงนานเกินไป กรุณาติดต่อ Customer Service");
                    cm.dispose();
                    return;
                }

                if (point % 10000 != 0 || point > 2000000) {
                    cm.sendOk("#fs11##r※ เติม Cash ด้วย Culture Voucher ได้ 10,000 วอนขึ้นไป โดยเติมทีละ 10,000 วอน สูงสุด 2,000,000 วอนต่อครั้ง และจะได้รับเพียง 90% ของจำนวนที่ขอ");
                    cm.dispose();
                    return;
                }

                var msg = "#fs11##fc0xFF000000##e<ข้อมูลที่กรอก>#n\r\n";
                msg += "#bจำนวนเงิน: " + point;
                msg += "\r\nอีเมล: " + realName;
                msg += "\r\n\r\n#fs14##e<ข้อควรทราบ>#fs11#";
                msg += "\r\n#fc0xFF000000#1. ต้อง #rส่งรหัส Voucher ทันที#k ไปยังอีเมลที่แจ้ง";
                msg += "\r\n#fc0xFF000000#2. เมื่ออีเมลและจำนวนเงินตรงกับ Voucher ที่ส่ง จะเติมให้อัตโนมัติ";
                msg += "\r\n#fc0xFF000000#3. Culture Voucher จะได้รับเพียง #r90%#k ของจำนวนที่ส่ง";
                msg += "\r\n\r\n#k#rหากฝ่าฝืนข้อข้างต้น อาจถูกแบนถาวรได้";
                msg += "\r\n#k#bหากเห็นด้วยและต้องการดำเนินการเติม Cash ต่อ\r\nกรุณาพิมพ์ #r'동의합니다'#b (ฉันยินยอม)";
                cm.sendGetText(msg);
            }
        } else if (status == 5) {
            if (cm.getText() != "동의합니다") {
                cm.dispose();
                cm.sendOk("#fs11#ถ้าไม่ยินยอม ก็ช่วยอะไรไม่ได้\r\nถ้า #bยินยอม#k กรุณาพิมพ์ '#r#e동의합니다#k#n' อีกครั้ง");
                return;
            }
            if (sel == 0) {
                display();
            } else if (sel == 3) {
                point2 = point;
                point = point * 0.9;
                display2();
            }
        }
    }
}

function display2() {
    var result = cm.putDonationRequest(point, realName, event);
    if (result == -1) {
        cm.dispose();
        return;
    }

    var msg = "#fs11##fc0xFF000000##e<ข้อมูลอีเมลสำหรับ Culture Voucher>#n\r\n";

    var cashurl = "https://mapleroyal.cc/cash";

    msg += "\r\n#bอีเมล: hiticket@etlgr.com#k\r\n\r\n";

    msg += "#fc0xFF000000#จำนวน Voucher ที่ต้องส่ง: #b" + nf.format(point2) + "#k\r\n";
    msg += "#fc0xFF000000#จำนวน Cash ที่จะได้รับ: #b" + nf.format(point) + "#k\r\n";
    msg += "#fc0xFF000000#อีเมลของคุณ: #e" + realName + "#n\r\n\r\n";
    msg += "#b※ ส่งรหัส PIN ของ Culture Voucher ไปที่อีเมลด้านบน\r\n";
    msg += "#r※ หากส่งรหัส PIN ผิด อาจถูกจำกัดการใช้บริการเติมเงิน กรุณาตรวจสอบก่อนส่ง";

    cm.addCustomLog(100, "[Cash Charge] Request Amount: " + nf.format(point2) + ", Name: " + realName);

    cm.getPlayer().dropMessage(-22, "ขอเติม Cash สำเร็จแล้ว กรุณาส่ง Culture Voucher " + nf.format(point2) + " วอน ไปที่อีเมลด้านล่าง");
    cm.getPlayer().dropMessage(-22, "[Email] hiticket@etlgr.com");

    cm.sendOk(msg);
    cm.dispose();
}

function display() {
    var result = cm.putDonationRequest(point, realName, event);
    /*
    con = DBConnection.getConnection();
    ps = con.prepareStatement("SELECT * FROM donation_number WHERE accountid = '" + cm.getPlayer().getAccountID() + "'");
    rs = ps.executeQuery();
    while (rs.next()) {
        cashnumber = rs.getString("number");
    }
    rs.close();
    ps.close();
    con.close();
    */

    if (result == -1) {
        cm.dispose();
        return;
    }

    var msg = "#fs11##fc0xFF000000##e<ข้อมูลบัญชีสำหรับเติม Cash>#n\r\n";

    var cashurl = "https://mapleroyal.cc/cash";

    chatnumber = cashnumber;
    cashnumber = "\r\n#bบัญชีโอนเงิน: Gwangju Bank " + cashnumber + "#k\r\n";

    if (VIP) { // If VIP
        msg += "\r\n#fUI/FarmUI.img/objectStatus/star/whole# #fc0xFFFF3366#[ VIP ] #fc0xFF6600CC#หมายเลขบัญชีเฉพาะสมาชิก VIP\r\n" + cashnumber + "#k#n#k\r\n";
    } else { // If not VIP
        msg += "\r\n#fUI/FarmUI.img/objectStatus/star/whole# #fc0xFFFF3366#[ VIP ] #fc0xFF6600CC#หมายเลขบัญชีเฉพาะสมาชิก VIP\r\n" + cashnumber + "#k#n#k\r\n";
        //msg += "#rกรุณาตรวจสอบบัญชีก่อนโอนเสมอ#fc0xFF000000# หมายเลขอาจเปลี่ยนบ่อย\r\n\r\nบัญชีโอนเงิน: ติดต่อ #bCustomer Service#k\r\n";
    }

    msg += "#fc0xFF000000#จำนวนเงิน: #b" + nf.format(point) + "#k\r\n";
    msg += "#fc0xFF000000#ชื่อผู้โอน: #e" + realName + "#n\r\n\r\n";
    msg += "#r※ ไม่สามารถโอนผ่าน Toss, โอนด่วน, Open Banking ได้\r\n";
    msg += "#r※ ต้องโอนผ่านแอปธนาคารที่ยืนยันตัวตนแล้วเท่านั้น\r\n";
    msg += "#b※ ตัวอย่าง) Woori Bank -> Woori WON Banking, Shinhan Bank -> Shinhan Sol";

    cm.addCustomLog(100, "[Cash Charge] Request Amount: " + nf.format(point) + ", Name: " + realName);

    if (havecashnumber) {
        cm.getPlayer().dropMessage(-22, "ขอเติม Cash สำเร็จแล้ว กรุณาโอนเงิน " + nf.format(point) + " วอน");
        cm.getPlayer().dropMessage(-22, "[บัญชีโอนเงิน] " + chatnumber + "");
    } else {
        cm.getPlayer().dropMessage(-22, "ขอเติม Cash สำเร็จแล้ว กรุณาโอนเงิน " + nf.format(point) + " วอน");
        cm.getPlayer().dropMessage(-22, "[บัญชีโอนเงิน] " + chatnumber + "");
    }

    cm.sendOk(msg);
    cm.dispose();
}

function checkcancelcash(requestid) {
    con = DBConnection.getConnection();
    ps = con.prepareStatement("SELECT point, real_name FROM donation_request Where id = " + requestid + "");
    rs = ps.executeQuery();
    while (rs.next()) {
        cancelcash = rs.getInt("point");
        cancelname = rs.getString("real_name");
    }
    rs.close();
    ps.close();
    con.close();
}