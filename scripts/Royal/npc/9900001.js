importPackage(Packages.database);
importPackage(java.lang);

var enter = "\r\n";
var status = -1;
var step = -1;

var targetName = "";
var promoCount = 0;

// ※ Please modify according to your environment. (Actual DB schema name viewed in HeidiSQL)
var DB_SCHEMA = "ganglim";  // e.g.: "ganglim", "mydb", etc.

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

    // ───────────────────────────────────────────────────────────────────────────
    // 0) First Dialogue: "Receive User Nickname"
    // ───────────────────────────────────────────────────────────────────────────
    if (status == 0) {
        // Check GM Permission
        if (!cm.getPlayer().isGM()) {
            cm.sendOk("คุณไม่มีสิทธิ์ใช้งาน");
            cm.dispose();
            return;
        }
        cm.sendGetText(
            "#fs12#<บันทึกโปรโมท (สำหรับ GM)>#fs11#" + enter +
            "1) กรุณากรอกชื่อตัวละครที่ต้องการบันทึกจำนวนการโปรโมท:\n\n" +
            "(ตัวอย่าง: ชื่อตัวละคร)"
        );
        step = 1;
    }
    // ───────────────────────────────────────────────────────────────────────────
    // 1) After Nickname Input → "Receive Promotion Count"
    // ───────────────────────────────────────────────────────────────────────────
    else if (status == 1) {
        if (step != 1) {
            cm.sendOk("An unexpected error occurred.");
            cm.dispose();
            return;
        }
        targetName = cm.getText().trim();
        if (targetName.length < 1) {
            cm.sendOk("กรุณากรอกชื่อตัวละครให้ถูกต้อง");
            cm.dispose();
            return;
        }
        cm.sendGetText(
            "🔹 กรอกจำนวนการโปรโมทที่จะบันทึกให้ [" + targetName + "]:\n\n" +
            "(ตัวอย่าง: 3)"
        );
        step = 2;
    }
    // ───────────────────────────────────────────────────────────────────────────
    // 2) After Promotion Count Input → Actual INSERT Processing
    // ───────────────────────────────────────────────────────────────────────────
    else if (status == 2) {
        if (step != 2) {
            cm.sendOk("An unexpected flow error occurred.");
            cm.dispose();
            return;
        }
        var txt = cm.getText().trim();
        if (!/^\d+$/.test(txt)) {
            cm.sendOk("กรุณากรอกเฉพาะตัวเลขเท่านั้น");
            cm.dispose();
            return;
        }
        promoCount = parseInt(txt);
        if (promoCount <= 0) {
            cm.sendOk("จำนวนการโปรโมทต้องเป็น 1 หรือมากกว่า");
            cm.dispose();
            return;
        }

        // Re-check GM Permission
        if (!cm.getPlayer().isGM()) {
            cm.sendOk("คุณไม่มีสิทธิ์ใช้งาน");
            cm.dispose();
            return;
        }

        // Now INSERT into DB
        insertHongboRecord(targetName, promoCount);
        return;
    }
    // ───────────────────────────────────────────────────────────────────────────
    // Terminate if other status values
    // ───────────────────────────────────────────────────────────────────────────
    else {
        cm.dispose();
    }
}


// ───────────────────────────────────────────────────────────────────────────
// Function: Actual DB processing to leave a new "Promotion Record" in the hongbo table
// ───────────────────────────────────────────────────────────────────────────
function insertHongboRecord(nickname, count) {
    var con = null;
    var ps = null, rs = null;
    var psIns = null;
    try {
        con = DBConnection.getConnection();

        // 1) Find character ID (cId) for INSERT from characters table
        ps = con.prepareStatement(
            "SELECT id FROM " + DB_SCHEMA + ".characters WHERE name = ?"
        );
        ps.setString(1, nickname);
        rs = ps.executeQuery();
        if (!rs.next()) {
            // Character with that nickname does not exist
            rs.close();
            ps.close();
            cm.sendOk("ไม่พบข้อมูล [" + nickname + "] ในฐานข้อมูล");
            cm.dispose();
            return;
        }
        var charId = rs.getInt("id");
        rs.close();
        ps.close();

        // 2) Calculate Promotion Point (etc) (e.g.: 50,000P per count)
        var pointValue = count * 50000;

        // 3) INSERT into hongbo table
        //    -> Use MySQL NOW() function to automatically insert server time into date column
        psIns = con.prepareStatement(
            "INSERT INTO " + DB_SCHEMA + ".hongbo " +
            "(`name`, `check`, `youtube`, `blog`, `etc`, `comment`, `date`, `cid`) " +
            "VALUES (?, 0, 0, ?, ?, '', NOW(), ?)"
        );
        psIns.setString(1, nickname);        // name
        psIns.setInt(2, count);              // blog = Promotion Count
        psIns.setInt(3, pointValue);         // etc  = Promotion Point
        psIns.setInt(4, charId);             // cid  = characterId

        var inserted = psIns.executeUpdate();
        psIns.close();

        if (inserted > 0) {
            cm.sendOk(
                "#fs11#▶ บันทึกข้อมูลการโปรโมทลงในฐานข้อมูลเรียบร้อยแล้ว\r\n\r\n" +
                "   ชื่อตัวละคร : " + nickname + enter +
                "   จำนวนโปรโมท : " + count + " ครั้ง" + enter +
                "   แต้มโปรโมทตามแผน : " + pointValue.toLocaleString() + "P" + enter +
                "   characterId (cid) : " + charId + enter +
                "   เวลาลงทะเบียน (DB Server Time) : NOW()"
            );
        } else {
            cm.sendOk("Error: No records were inserted.");
        }
        cm.dispose();
        return;

    } catch (e) {
        cm.sendOk("Error occurred while recording promotion record to DB:\r\n" + e.toString());
        e.printStackTrace();
        try {
            if (con != null && !con.isClosed()) con.close();
        } catch (ex) {
            ex.printStackTrace();
        }
        cm.dispose();
        return;
    } finally {
        try { if (rs != null && !rs.isClosed()) rs.close(); } catch (e2) { }
        try { if (ps != null && !ps.isClosed()) ps.close(); } catch (e2) { }
        try { if (con != null && !con.isClosed()) con.close(); } catch (e2) { }
    }
}
