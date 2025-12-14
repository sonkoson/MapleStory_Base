importPackage(Packages.database);
importPackage(java.lang);

color = "#fc0xFF6600CC#"
black = "#fc0xFF000000#"

var couponid = "";
var itemid = 0;
var qty = 0;
var starttime = "";
var endtime = "";
var maxcount = 0;
var currentcount = 0;
var cancoupon = false;
var enter = "\r\n";

function start() {
    status = -1;
    action(1, 0, 0);
}


function action(mode, type, sel) {
    var today = new Date();
    var hours = today.getHours();
    var minutes = today.getMinutes();

    var todayFormatted = today.toISOString().split('T')[0];
    var StartDate = new Date("2023-12-23");
    var StartDate2 = new Date("2023-12-24");
    var startDateFormatted = StartDate.toISOString().split('T')[0];
    var startDate2Formatted = StartDate2.toISOString().split('T')[0];
    var todayDate = new Date(todayFormatted);
    var timecheck = hours >= 17 && minutes >= 00;

    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }
    if (todayDate > StartDate && timecheck || todayDate > StartDate2) {
        if (cm.getPlayer().getAccountTotalLevel() < 8075) {
            cm.sendOk("#fs11##fc0xFF000000#ใช้งานได้เฉพาะบัญชีที่มีเลเวลรวม 8075 ขึ้นไปเท่านั้น");
            cm.dispose();
            return;
        }
        if (cm.getPlayer().getLevel() < 275) {
            cm.sendOk("#fs11##fc0xFF000000#ใช้งานได้เฉพาะตัวละครเลเวล 275 ขึ้นไปเท่านั้น");
            cm.dispose();
            return;
        }
    }
    if (status == 0) {
        var msg = "#fs11##fc0xFF000000#กรุณากรอกรหัสคูปองเพื่อใช้งาน\r\nคูปองจะถูกสร้างขึ้นใน Discord เป็นครั้งคราว\r\n\r\n#r※ โปรดทราบว่าคูปองแต่ละใบสามารถใช้ได้ 1 ครั้งต่อบัญชีเท่านั้น!";
        cm.sendGetText(msg);
    } else if (status == 1) {
        couponid = cm.getText();

        // Already used coupon
        if (cm.getClient().getKeyValue(couponid) != null) {
            cm.sendOk("#fs11##fc0xFF000000#คูปองนี้ถูกใช้งานไปแล้ว");
            cm.dispose();
            return;
        }

        con = DBConnection.getConnection();
        ps = con.prepareStatement("SELECT * FROM coupon");
        rs = ps.executeQuery();
        while (rs.next()) {
            if (couponid == rs.getString("couponid")) {
                cancoupon = true;
                itemid = rs.getInt("item");
                qty = rs.getInt("qty");
                starttime = rs.getString("starttime");
                endtime = rs.getString("endtime");
                maxcount = rs.getInt("maxcount");
                currentcount = rs.getInt("currentcount");
                break;
            }
        }
        rs.close();
        ps.close();
        con.close();

        // Coupon does not exist
        if (!cancoupon) {
            cm.sendOk("#fs11##fc0xFF000000#รหัสคูปองนี้ไม่มีอยู่จริง");
            cm.dispose();
            return;
        }

        // Check coupon validity period
        var date = new Date();
        var year = date.getFullYear();
        var month = ("0" + (1 + date.getMonth())).slice(-2);
        var day = ("0" + date.getDate()).slice(-2);
        var hour = ("0" + date.getHours()).slice(-2);
        var minutes = ("0" + date.getMinutes()).slice(-2);
        var seconds = ("0" + date.getSeconds()).slice(-2);

        var today = year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;

        if (today > endtime) {
            cm.sendOk("#fs11##fc0xFF000000#คูปองนี้หมดอายุการใช้งานแล้ว");
            cm.dispose();
            return;
        }

        if (today < starttime) {
            cm.sendOk("#fs11##fc0xFF000000#คูปองนี้ยังไม่สามารถใช้งานได้");
            cm.dispose();
            return;
        }

        // If maxcount exists
        if (maxcount > 0) {
            // If current count equals maxcount
            if (currentcount >= maxcount) {
                cm.sendOk("#fs11##fc0xFF000000#คูปองนี้ถูกใช้งานจนหมดโควตาแล้ว");
                cm.dispose();
                return;
            }
        }

        // Increase currentcount
        con = DBConnection.getConnection();
        psmodify = con.prepareStatement("UPDATE coupon SET currentcount = " + (currentcount + 1) + " Where couponid = '" + couponid + "'");
        rsmodify = psmodify.executeQuery();
        rsmodify.close();
        psmodify.close();
        con.close();

        // No inventory space
        if (!cm.canHold(itemid, qty)) {
            cm.sendOk("#fs11##fc0xFF000000#พื้นที่ในกระเป๋าไม่เพียงพอ");
            cm.dispose();
            return;
        }

        // Valid coupon = Give item
        msg = "#fs11##fc0xFF000000#ใช้งานคูปองสำเร็จและได้รับไอเทมเรียบร้อยแล้ว\r\n\r\n#r※ รายละเอียดของรางวัลมีดังนี้\r\n\r\n";
        msg += color + "#i" + itemid + "# #z" + itemid + "# " + qty + " ชิ้น" + enter;
        cm.gainItem(itemid, qty);
        cm.getClient().setKeyValue(couponid, today);
        cm.sendOk(msg);
        cm.dispose();
    }
}
