importPackage(Packages.constants);
importPackage(Packages.database);
importPackage(java.lang);

function ConvertNumber(number) { // Number formatting function
    var inputNumber = number < 0 ? false : number;
    var unitWords = ['', 'Ten Thousand ', 'Hundred Million ', 'Trillion ', 'Quadrillion '];
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
            say += "#fc0xFF6600CC#\r\n\r\n\r\n   <Admin Menu>\r\n#L4#Reset Ranking#l\r\n\r\n"//#L5#랭커 보상 지급하기#l;
        }
        cm.sendSimple("#fs11##fc0xFF000000#   데미지 측정을 시작하시겠습니까?\r\n" +
            "   Previous Damage Record: " + cm.getPlayer().DamageMeter + "\r\n   #r※ Ranking saves your latest record, not highest record#k\r\n" +
            "#L1##rStart Damage Test (2 mins)#l\r\n#L2##bView Damage Ranking#l" + say);
    } else if (status == 1) {
        if (selection == 1) {
            var em = cm.getEventManager("DamageMeter");
            if (em == null) {
                cm.sendOk("An error occurred. Please try again.");
                cm.dispose();
                return;
            } else if (em.getProperty("entry").equals("false")) {
                cm.sendOk("Another user is currently recording damage. Please try again in 1 minute.");
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
                var say = "#fs11##fc0xFF000000#Damage Meter Ranking\r\n※ Only 100 trillion+ displayed\r\n\r\n";
                while (rs.next()) {
                    count++;
                    say += count + " place - #b" + rs.getString("name") + "   #rDamage#fc0xFF000000#: " + ConvertNumber(rs.getLong("damage")) + "\r\n";
                }
                rs.close();
                ps.close();
                con.close();
                cm.sendOk(say);
                cm.dispose();
                return;
            } catch (e) {
                cm.sendOk("No records found or an error occurred.\r\n" + e);
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
            cm.sendGetNumber("Which date's ranking would you like to see?\r\nEnter date as follows:\r\nExample) 20200101", 0, 20200101, 99999999);
        } else if (selection == 4 && cm.getPlayer().getGMLevel() > 5) {
            admin = 1;
            cm.sendYesNo("Are you sure you want to reset all Damage Meter rankings?\r\nAll date records will be deleted!");
        } else if (selection == 5 && cm.getPlayer().getGMLevel() > 5) {
            admin = 2;
            cm.sendGetNumber("Which date's ranker rewards would you like to distribute?\r\nEnter date as follows:\r\nExample) 20200101", 0, 20200101, 99999999);
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
                var say = selection.toString().substring(0, 4) + "/" + selection.toString().substring(4, 6) + "/" + selection.toString().substring(6, 8) + " Damage Meter Ranking\r\n\r\n";
                while (rs.next()) {
                    count++;
                    say += count + " place - " + rs.getString("name") + "   Damage: " + ConvertNumber(rs.getLong("damage")) + "\r\n";
                }
                rs.close();
                ps.close();
                con.close();
                cm.sendOk(say);
                cm.dispose();
                return;
            } catch (e) {
                cm.sendOk("No records found or an error occurred.\r\n" + e);
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
                cm.sendOk("Damage Meter ranking reset complete.");
                cm.dispose();
                return;
            } catch (e) {
                cm.sendOk("An error occurred.\r\n" + e);
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
                var say = rewarddate.toString().substring(0, 4) + "/" + rewarddate.toString().substring(4, 6) + "/" + rewarddate.toString().substring(6, 8) + " Damage Meter Ranking\r\n" +
                    "Click nickname to distribute ranking rewards.\r\n";
                while (rs.next()) {
                    count++;
                    say += "#L" + rs.getInt("id") + "#" + count + " place - " + rs.getString("name") + "   Damage: " + ConvertNumber(rs.getLong("damage")) + "\r\n";
                }
                rs.close();
                ps.close();
                con.close();
                cm.sendSimple(say);
            } catch (e) {
                cm.sendOk("No records found or an error occurred.\r\n" + e);
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
                    say += rs.getString("name") + "'s Damage Meter Ranking\r\n\r\n";
                    say += count + " place - Damage: " + ConvertNumber(rs.getLong("damage"));
                    say += "\r\n\r\nDo you want to give rank " + count + " reward?";
                }
            }
            rs.close();
            ps.close();
            con.close();
            cm.sendYesNo(say);
        } catch (e) {
            cm.sendOk("No user selected or an error occurred.\r\n" + e);
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
        //1등 2022424 20개 + 4001126 500개 + 4310024 5개
        //2등 2022424 10개 + 4001126 300개 + 4310024 3개
        //3등 2022424 5개 + 4001126 100개 + 4310024 1개
        var channel = Packages.handling.world.World.Find.findChannel(characterid);
        if (rank == 1) {
            Packages.handling.channel.handler.DueyHandler.addNewItemToDb(2022424, 20, characterid, "[데미지미터]", "데미지미터 " + rank + "등 보상", channel >= 0);
            Packages.handling.channel.handler.DueyHandler.addNewItemToDb(4001126, 500, characterid, "[데미지미터]", "데미지미터 " + rank + "등 보상", channel >= 0);
            Packages.handling.channel.handler.DueyHandler.addNewItemToDb(4310024, 5, characterid, "[데미지미터]", "데미지미터 " + rank + "등 보상", channel >= 0);
        } else if (rank == 2) {
            Packages.handling.channel.handler.DueyHandler.addNewItemToDb(2022424, 10, characterid, "[데미지미터]", "데미지미터 " + rank + "등 보상", channel >= 0);
            Packages.handling.channel.handler.DueyHandler.addNewItemToDb(4001126, 300, characterid, "[데미지미터]", "데미지미터 " + rank + "등 보상", channel >= 0);
            Packages.handling.channel.handler.DueyHandler.addNewItemToDb(4310024, 3, characterid, "[데미지미터]", "데미지미터 " + rank + "등 보상", channel >= 0);
        } else if (rank == 3) {
            Packages.handling.channel.handler.DueyHandler.addNewItemToDb(2022424, 5, characterid, "[데미지미터]", "데미지미터 " + rank + "등 보상", channel >= 0);
            Packages.handling.channel.handler.DueyHandler.addNewItemToDb(4001126, 100, characterid, "[데미지미터]", "데미지미터 " + rank + "등 보상", channel >= 0);
            Packages.handling.channel.handler.DueyHandler.addNewItemToDb(4310024, 1, characterid, "[데미지미터]", "데미지미터 " + rank + "등 보상", channel >= 0);
        } else {
            cm.sendOk("This user is outside the top 3, so there is no reward.");
            cm.dispose();
            return;
        }
        if (channel >= 0) {
            Packages.network.center.Center.Broadcast.sendPacket(characterid, Packages.tools.MaplePacketCreator.sendDuey(28, null, null));
            Packages.network.center.Center.Broadcast.sendPacket(characterid, Packages.tools.MaplePacketCreator.serverNotice(2, "[System]: Damage Meter rank " + rank + " reward has been sent via Parcel."));
        }
        cm.sendOk("Distributed rank " + rank + " reward to " + name + ".");
        cm.dispose();
        return;
    } else {
        cm.dispose();
        return;
    }
}