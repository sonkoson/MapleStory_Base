importPackage(Packages.database);
importPackage(java.lang);

var color = "#fc0xFF6600CC#";
var black = "#fc0xFF000000#";

var months = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"];
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
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }

    if (status == 0) {
        Packages.network.center.Center.Guild.getGuild(3210).setAllianceId(0);
        var date = new Date();
        var year = date.getFullYear();
        var month = ("0" + (1 + date.getMonth())).slice(-2);
        var day = ("0" + date.getDate()).slice(-2);
        var hour = ("0" + date.getHours()).slice(-2);
        var minutes = ("0" + date.getMinutes()).slice(-2);
        var seconds = ("0" + date.getSeconds()).slice(-2);

        var today = year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;

        cm.sendGetText("#fs11#เวลาปัจจุบัน: " + today + "\r\n\r\n#bกรุณากรอกรหัสคูปองของคุณ:#k");
    } else if (status == 1) {
        couponid = cm.getText();
        var con = DBConnection.getConnection();
        var ps = con.prepareStatement("SELECT * FROM coupon");
        var rs = ps.executeQuery();
        while (rs.next()) {
            if (couponid == rs.getString("id")) {
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

        // Check if coupon exists
        if (!cancoupon) {
            cm.sendOk("#fs11##fc0xFF000000#ไม่พบรหัสคูปองนี้");
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
            cm.sendOk("#fs11##fc0xFF000000#คูปองหมดอายุแล้ว");
            cm.dispose();
            return;
        }

        if (today < starttime) {
            cm.sendOk("#fs11##fc0xFF000000#คูปองนี้ยังไม่เปิดให้ใช้งาน");
            cm.dispose();
            return;
        }

        // Check if already used
        if (cm.getClient().getKeyValue(couponid) != null) {
            cm.sendOk("#fs11##fc0xFF000000#คุณใช้คูปองนี้ไปแล้ว");
            cm.dispose();
            return;
        }

        // Check max count
        if (maxcount > 0) {
            if (currentcount >= maxcount) {
                cm.sendOk("#fs11##fc0xFF000000#คูปองนี้ถูกใช้จนหมดโควต้าแล้ว");
                cm.dispose();
                return;
            } else {
                var con = DBConnection.getConnection();
                var psmodify = con.prepareStatement("UPDATE coupon SET currentcount = " + (currentcount + 1) + " Where id = '" + couponid + "'");
                psmodify.executeUpdate();
                psmodify.close();
                con.close();
            }
        }

        // Give item
        var msg = "#fs11##fc0xFF000000#แลกคูปองสำเร็จ! คุณได้รับไอเท็มเรียบร้อยแล้ว\r\n\r\n#rรายละเอียดของขวัญ:\r\n\r\n";
        msg += color + "#i" + itemid + "# #z" + itemid + "# " + qty + " ชิ้น" + enter;
        cm.gainItem(itemid, qty);
        cm.getClient().setKeyValue(couponid, today);
        cm.sendOk(msg);
        cm.dispose();
    }
}
