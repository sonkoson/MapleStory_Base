importPackage(Packages.constants);
importPackage(Packages.database);
importPackage(java.lang);

function ConvertNumber(number) { // Number formatting function
    var inputNumber = number < 0 ? false : number;
    var unitWords = ['', 'หมื่น ', 'ร้อยล้าน ', 'ล้านล้าน ', 'พันล้านล้าน '];
    var splitUnit = 10000;
    var splitCount = unitWords.length;
    var resultArray = [];
    var resultString = '';
    if (inputNumber == false) {
        cm.sendOk("เกิดข้อผิดพลาด กรุณาลองใหม่อีกครั้ง\r\n(Parsing Error)");
        cm.dispose();
        return;
    }
    for (var i = 0; i < splitCount; i++) {
        var unitResult = (inputNumber % Math.pow(splitUnit, i + 1)) / Math.pow(splitUnit, i);
        unitResult = Math.floor(unitResult);
        if (unitResult > 0) {
            resultArray[i] = unitResult;
        }
    }
    for (var i = 0; i < resultArray.length; i++) {
        if (!resultArray[i]) continue;
        resultString = String(resultArray[i]) + unitWords[i] + resultString;
    }
    return resultString;
}

var Time = new Date();
var Year = Time.getFullYear() + "";
var Month = Time.getMonth() + 1 + "";
var Date = Time.getDate() + "";
if (Month < 10) {
    Month = "0" + Month;
}
if (Date < 10) {
    Date = "0" + Date;
}
var Today = parseInt(Year + Month + Date);

var admin = 0;
var rewarddate = 0;
var rank = 0;
var characterid = 0;
var name = "";
var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode != 1) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
        /*cm.getPlayer().DamageMeterMap = 450002250;
        cm.getPlayer().DamageMeterMonster = 9300800;
        cm.getPlayer().DamageMeterTime = 30;
        cm.getPlayer().DamageMobX = 200;
        cm.getPlayer().DamageMobY = 100;
        cm.getPlayer().DamageMeterExitMap = 180000000;*/
        var say = "";
        if (cm.getPlayer().getGMLevel() > 5) {
            say += "#fc0xFF6600CC#\r\n\r\n\r\n   <เมนูแอดมิน>\r\n#L4#รีเซ็ตอันดับ#l\r\n\r\n"//#L5#มอบรางวัลประจำวัน#l;
        }
        cm.sendSimple("#fs11##fc0xFF000000#   ต้องการเริ่มทดสอบ Damage หรือไม่?\r\n" +
            "   สถิติ Damage ก่อนหน้า: " + cm.getPlayer().DamageMeter + "\r\n   #r※ การจัดอันดับจะบันทึกสถิติล่าสุดของคุณ ไม่ใช่สถิติสูงสุด#k\r\n" +
            "#L1##rเริ่มทดสอบ Damage (2 นาที)#l\r\n#L2##bดูอันดับ Damage#l" + say);
    } else if (status == 1) {
        if (selection == 1) {
            var em = cm.getEventManager("DamageMeter");
            if (em == null) {
                cm.sendOk("เกิดข้อผิดพลาด กรุณาลองใหม่อีกครั้ง");
                cm.dispose();
                return;
            } else if (em.getProperty("entry").equals("false")) {
                cm.sendOk("มีผู้ใช้งานอยู่ กรุณาลองใหม่ในอีก 1 นาที");
                cm.dispose();
                return;
            } else {
                cm.getPlayer().DamageMeter = 0;
                em.startInstance(cm.getPlayer());
                cm.dispose();
            }
        } else if (selection == 2) {
            var con = null;
            var ps = null;
            var rs = null;
            try {
                con = DBConnection.getConnection();
                ps = con.prepareStatement("SELECT * FROM `DamageMeter` WHERE `damage` >= 100000000000000 ORDER BY `damage` DESC");
                rs = ps.executeQuery();
                var count = 0;
                var say = "#fs11##fc0xFF000000#อันดับ Damage Meter\r\n※ แสดงเฉพาะ 100 trillion ขึ้นไป\r\n\r\n";
                while (rs.next()) {
                    count++;
                    say += "อันดับ " + count + " - #b" + rs.getString("name") + "   #rDamage#fc0xFF000000#: " + ConvertNumber(rs.getLong("damage")) + "\r\n";
                }
                rs.close();
                ps.close();
                con.close();
                cm.sendOk(say);
                cm.dispose();
                return;
            } catch (e) {
                cm.sendOk("ไม่พบข้อมูลหรือเกิดข้อผิดพลาด\r\n" + e);
                cm.dispose();
                return;
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (e) {

                    }
                }
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (e) {

                    }
                }
                if (con != null) {
                    try {
                        con.close();
                    } catch (e) {

                    }
                }
            }
        } else if (selection == 3) {
            admin = 0;
            cm.sendGetNumber("ต้องการดูอันดับของวันที่เท่าไหร่?\r\nกรุณาใส่วันที่ดังนี้:\r\nตัวอย่าง) 20200101", 0, 20200101, 99999999);
        } else if (selection == 4 && cm.getPlayer().getGMLevel() > 5) {
            admin = 1;
            cm.sendYesNo("คุณแน่ใจหรือไม่ว่าต้องการรีเซ็ตอันดับ Damage Meter ทั้งหมด?\r\nบันทึกวันที่ทั้งหมดจะถูกลบ!");
        } else if (selection == 5 && cm.getPlayer().getGMLevel() > 5) {
            admin = 2;
            cm.sendGetNumber("ต้องการแจกรางวัลอันดับของวันที่เท่าไหร่?\r\nกรุณาใส่วันที่ดังนี้:\r\nตัวอย่าง) 20200101", 0, 20200101, 99999999);
        } else {
            cm.dispose();
            return;
        }
    } else if (status == 2) {
        if (admin == 0) {
            var con = null;
            var ps = null;
            var rs = null;
            try {
                con = DBConnection.getConnection();
                ps = con.prepareStatement("SELECT * FROM `DamageMeter` WHERE `date` = " + selection + " ORDER BY `damage` DESC");
                rs = ps.executeQuery();
                var count = 0;
                var say = selection.toString().substring(0, 4) + "/" + selection.toString().substring(4, 6) + "/" + selection.toString().substring(6, 8) + " อันดับ Damage Meter\r\n\r\n";
                while (rs.next()) {
                    count++;
                    say += "อันดับ " + count + " - " + rs.getString("name") + "   Damage: " + ConvertNumber(rs.getLong("damage")) + "\r\n";
                }
                rs.close();
                ps.close();
                con.close();
                cm.sendOk(say);
                cm.dispose();
                return;
            } catch (e) {
                cm.sendOk("ไม่พบข้อมูลหรือเกิดข้อผิดพลาด\r\n" + e);
                cm.dispose();
                return;
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (e) {

                    }
                }
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (e) {

                    }
                }
                if (con != null) {
                    try {
                        con.close();
                    } catch (e) {

                    }
                }
            }
        } else if (admin == 1 && cm.getPlayer().getGMLevel() > 5) {
            var con = null;
            var ps = null;
            try {
                con = DBConnection.getConnection();
                ps = con.prepareStatement("DELETE FROM `DamageMeter`");
                ps.executeUpdate();
                ps.close();
                con.close();
                cm.sendOk("รีเซ็ตอันดับ Damage Meter เรียบร้อยแล้ว");
                cm.dispose();
                return;
            } catch (e) {
                cm.sendOk("เกิดข้อผิดพลาด\r\n" + e);
                cm.dispose();
                return;
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (e) {

                    }
                }
                if (con != null) {
                    try {
                        con.close();
                    } catch (e) {

                    }
                }
            }
        } else if (admin == 2 && cm.getPlayer().getGMLevel() > 5) {
            var con = null;
            var ps = null;
            var rs = null;
            try {
                con = DBConnection.getConnection();
                ps = con.prepareStatement("SELECT * FROM `DamageMeter` WHERE `date` = " + selection + " ORDER BY `damage` DESC");
                rs = ps.executeQuery();
                var count = 0;
                rewarddate = selection;
                var say = rewarddate.toString().substring(0, 4) + "/" + rewarddate.toString().substring(4, 6) + "/" + rewarddate.toString().substring(6, 8) + " อันดับ Damage Meter\r\n" +
                    "คลิกชื่อเพื่อแจกของรางวัลอันดับ\r\n";
                while (rs.next()) {
                    count++;
                    say += "#L" + rs.getInt("id") + "#" + count + " place - " + rs.getString("name") + "   Damage: " + ConvertNumber(rs.getLong("damage")) + "\r\n";
                }
                rs.close();
                ps.close();
                con.close();
                cm.sendSimple(say);
            } catch (e) {
                cm.sendOk("ไม่พบข้อมูลหรือเกิดข้อผิดพลาด\r\n" + e);
                cm.dispose();
                return;
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (e) {

                    }
                }
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (e) {

                    }
                }
                if (con != null) {
                    try {
                        con.close();
                    } catch (e) {

                    }
                }
            }
        } else {
            cm.dispose();
            return;
        }
    } else if (status == 3 && cm.getPlayer().getGMLevel() > 5) {
        var con = null;
        var ps = null;
        var rs = null;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM `DamageMeter` WHERE `date` = " + rewarddate + " ORDER BY `damage` DESC");
            rs = ps.executeQuery();
            var count = 0;
            var say = rewarddate.toString().substring(0, 4) + "/" + rewarddate.toString().substring(4, 6) + "/" + rewarddate.toString().substring(6, 8) + " ";
            while (rs.next()) {
                count++;
                if (rs.getInt("id") == selection) {
                    rank = count;
                    characterid = rs.getInt("characterid");
                    name = rs.getString("name");
                    say += " อันดับ Damage Meter ของ " + rs.getString("name") + "\r\n\r\n";
                    say += "อันดับ " + count + " - Damage: " + ConvertNumber(rs.getLong("damage"));
                    say += "\r\n\r\nคุณต้องการมอบรางวัลอันดับ " + count + " หรือไม่?";
                }
            }
            rs.close();
            ps.close();
            con.close();
            cm.sendYesNo(say);
        } catch (e) {
            cm.sendOk("ไม่ได้เลือกผู้ใช้หรือเกิดข้อผิดพลาด\r\n" + e);
            cm.dispose();
            return;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (e) {

                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (e) {

                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (e) {

                }
            }
        }
    } else if (status == 4 && cm.getPlayer().getGMLevel() > 5) {
        //1st: 2022424 20 items + 4001126 500 items + 4310024 5 items
        //2nd: 2022424 10 items + 4001126 300 items + 4310024 3 items
        //3rd: 2022424 5 items + 4001126 100 items + 4310024 1 items
        var channel = Packages.handling.world.World.Find.findChannel(characterid);
        if (rank == 1) {
            Packages.handling.channel.handler.DueyHandler.addNewItemToDb(2022424, 20, characterid, "[Damage Meter]", "Damage Meter Rank " + rank + " Reward", channel >= 0);
            Packages.handling.channel.handler.DueyHandler.addNewItemToDb(4001126, 500, characterid, "[Damage Meter]", "Damage Meter Rank " + rank + " Reward", channel >= 0);
            Packages.handling.channel.handler.DueyHandler.addNewItemToDb(4310024, 5, characterid, "[Damage Meter]", "Damage Meter Rank " + rank + " Reward", channel >= 0);
        } else if (rank == 2) {
            Packages.handling.channel.handler.DueyHandler.addNewItemToDb(2022424, 10, characterid, "[Damage Meter]", "Damage Meter Rank " + rank + " Reward", channel >= 0);
            Packages.handling.channel.handler.DueyHandler.addNewItemToDb(4001126, 300, characterid, "[Damage Meter]", "Damage Meter Rank " + rank + " Reward", channel >= 0);
            Packages.handling.channel.handler.DueyHandler.addNewItemToDb(4310024, 3, characterid, "[Damage Meter]", "Damage Meter Rank " + rank + " Reward", channel >= 0);
        } else if (rank == 3) {
            Packages.handling.channel.handler.DueyHandler.addNewItemToDb(2022424, 5, characterid, "[Damage Meter]", "Damage Meter Rank " + rank + " Reward", channel >= 0);
            Packages.handling.channel.handler.DueyHandler.addNewItemToDb(4001126, 100, characterid, "[Damage Meter]", "Damage Meter Rank " + rank + " Reward", channel >= 0);
            Packages.handling.channel.handler.DueyHandler.addNewItemToDb(4310024, 1, characterid, "[Damage Meter]", "Damage Meter Rank " + rank + " Reward", channel >= 0);
        } else {
            cm.sendOk("ผู้เล่นนี้อยู่นอก 3 อันดับแรก จึงไม่มีรางวัล");
            cm.dispose();
            return;
        }
        if (channel >= 0) {
            Packages.network.center.Center.Broadcast.sendPacket(characterid, Packages.tools.MaplePacketCreator.sendDuey(28, null, null));
            Packages.network.center.Center.Broadcast.sendPacket(characterid, Packages.tools.MaplePacketCreator.serverNotice(2, "[ระบบ]: ของรางวัลอันดับ Damage Meter " + rank + " ถูกส่งทางพัสดุแล้ว"));
        }
        cm.sendOk("มอบรางวัลอันดับ " + rank + " ให้ " + name + " เรียบร้อยแล้ว");
        cm.dispose();
        return;
    } else {
        cm.dispose();
        return;
    }
}