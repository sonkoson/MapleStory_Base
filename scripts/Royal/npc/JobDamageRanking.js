importPackage(Packages.database);

var jobList = [ // Only show 4th job or higher
    // Adventurers
    [112, "Hero"], [122, "Paladin"], [132, "Dark Knight"],
    [212, "Arch Mage (Fire, Poison)"], [222, "Arch Mage (Ice, Lightning)"], [232, "Bishop"],
    [312, "Bowmaster"], [322, "Marksman"], [332, "Pathfinder"],
    [412, "Night Lord"], [422, "Shadower"], [434, "Dual Blade"],
    [512, "Buccaneer"], [522, "Corsair"], [532, "Cannoneer"],
    // Cygnus Knights
    [1112, "Soul Master"], [1212, "Flame Wizard"], [1312, "Wind Breaker"], [1412, "Night Walker"], [1512, "Striker"], [5112, "Mihile"],
    // Heroes
    [2112, "Aran"], [2217, "Evan"], [2312, "Mercedes"], [2412, "Phantom"], [2512, "Shade"], [2712, "Luminous"],
    // Resistance, Demon
    [3112, "Demon Slayer"], [3122, "Demon Avenger"], [3212, "Battle Mage"], [3312, "Wild Hunter"], [3512, "Mechanic"], [3612, "Xenon"], [3712, "Blaster"],
    // Nova
    [6112, "Kaiser"], [6312, "Kain"], [6412, "Cadena"], [6512, "Angelic Buster"],
    // Lef
    [15112, "Adele"], [15212, "Illium"], [15512, "Ark"], [15412, "Khali"],
    // Anima
    [16412, "Hoyoung"], [16212, "Lara"],
    // Friends World
    [14212, "Kinesis"],
    // Transcendent
    [10112, "Zero"],
];

color = "#fc0xFF6600CC#"
black = "#fc0xFF000000#"
pink = "#fc0xFFFF3366#"

var choice = 0;

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
        var say = "#fs11##bนี่คือการจัดอันดับพลังต่อสู้แยกตามอาชีพ\r\n#kโปรดเลือกอาชีพที่คุณต้องการดูอันดับ\r\n";
        for (var i = 0; i < jobList.length; i++) {
            say += "#L" + i + "#" + jobList[i][1] + "#l\r\n";
        }
        cm.sendSimple(say);
    } else if (status == 1) {
        var con = null;
        var ps = null;
        var rs = null;
        var count = 0;
        var say = "#fs11##fc0xFF000000#การจัดอันดับพลังต่อสู้ของอาชีพ " + jobList[selection][1] + "\r\n#r※ ดาเมจจะแสดงตั้งแต่หลักร้อยล้าน (100M) ขึ้นไป\r\n\r\n";
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("SELECT `name`, `damage`, `player_id` FROM `damage_measurement_rank` WHERE `job` = " + jobList[selection][0] + " ORDER BY `damage` DESC LIMIT 100"); //AND `gm` = 0 
            rs = ps.executeQuery();

            psmodify = con.prepareStatement("UPDATE damage_measurement_rank SET job = (SELECT job FROM characters Where id = player_id), name = (SELECT name FROM characters Where id = player_id)");
            rsmodify = psmodify.executeQuery();

            while (rs.next()) {
                count++;
                if (count < 10) {
                    aas = "#e00#fc0xFF09A17F#" + count + black + "#n Rank | ";
                } else if (count < 100) {
                    aas = "#e0#fc0xFF09A17F#" + count + black + "#n Rank | ";
                } else {
                    aas = "#e#fc0xFF09A17F#" + count + black + "#n Rank | ";
                }

                var pssearchaccid = con.prepareStatement("SELECT banned FROM accounts Where id in (SELECT accountid FROM characters Where id = " + rs.getInt("player_id") + ")");
                var rssearchaccid = pssearchaccid.executeQuery();

                while (rssearchaccid.next()) {
                    var banned = rssearchaccid.getInt("banned");
                }

                var name = "";
                name = rs.getString("name");

                if (banned > 0) {
                    name = "#rBanned";
                }

                say += "#fc0xFFB2B2B2#" + aas + "#b" + name + "#k | #rDamage : #e#fc0xFF000000#" + ConvertNumber(rs.getString("damage")) + "#n\r\n";
            }
            rs.close();
            ps.close();
            rsmodify.close();
            psmodify.close();
            con.close();
            cm.sendOk(say);
            cm.dispose();
            return;
        } catch (e) {
            cm.sendOk("เกิดข้อผิดพลาด\r\n\r\n" + e);
            cm.dispose();
            return;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
    } else {
        cm.dispose();
        return;
    }
}

function ConvertNumber(number) {
    var inputNumber = number < 0 ? false : number;
    var unitWords = ['', 'K', 'M', 'B', 'T', 'Q'];
    var splitUnit = 1000;
    var splitCount = unitWords.length;
    var resultArray = [];
    var resultString = '';

    if (inputNumber == false) {
        cm.sendOk("#fs11#เกิดข้อผิดพลาด โปรดลองใหม่อีกครั้ง\r\n(Parsing Error)");
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

    // Logic to show only significantly large numbers similar to original intent (10^8+)
    // But here we just show readable format.
    for (var i = resultArray.length - 1; i >= 0; i--) {
        if (!resultArray[i]) continue;
        resultString += String(resultArray[i]) + unitWords[i] + " ";
    }

    return resultString.trim();
}
