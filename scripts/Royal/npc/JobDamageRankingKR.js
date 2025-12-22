
importPackage(Packages.database);

var Adv = [ // Only 4th job or higher
    // Adventure
    [112, "Hero"], [122, "Paladin"], [132, "Dark Knight"],
    [212, "Arch Mage (Fire/Poison)"], [222, "Arch Mage (Ice/Lightning)"], [232, "Bishop"],
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
    // Friend World
    [14212, "Kinesis"],
    // Transcendent
    [10112, "Zero"],
];

var color = "#fc0xFF6600CC#"
var black = "#fc0xFF000000#"
var pink = "#fc0xFFFF3366#"

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
        var say = "#fs11##bนี่คืออันดับ Battle Power ตามสายอาชีพจ้ะ\r\n#kโปรดเลือกอาชีพที่ต้องการดูอันดับ\r\n";
        for (var i = 0; i < Adv.length; i++) {
            say += "#L" + i + "#" + Adv[i][1] + "#l\r\n";
        }
        cm.sendSimple(say);
    } else if (status == 1) {
        var con = null;
        var ps = null;
        var rs = null;
        var count = 0;
        var say = "#fs11##fc0xFF000000#อันดับ Battle Power ของอาชีพ " + Adv[selection][1] + " จ้ะ\r\n#r? แสดงดาเมจในหน่วย 'ร้อยล้าน' (Eok) ขึ้นไป\r\n\r\n";
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("SELECT `name`, `damage`, `player_id` FROM `damage_measurement_rank` WHERE `job` = " + Adv[selection][0] + " ORDER BY `damage` DESC LIMIT 100"); //AND `gm` = 0 
            rs = ps.executeQuery();

            psmodify = con.prepareStatement("UPDATE damage_measurement_rank SET job = (SELECT job FROM characters Where id = player_id), name = (SELECT name FROM characters Where id = player_id)");
            rsmodify = psmodify.executeQuery();

            while (rs.next()) {
                count++;
                if (count < 10) {
                    aas = "#e00#fc0xFF09A17F#" + count + black + "#n | ";
                } else if (count < 100) {
                    aas = "#e0#fc0xFF09A17F#" + count + black + "#n | ";
                } else {
                    aas = "#e#fc0xFF09A17F#" + count + black + "#n | ";
                }

                var pssearchaccid = con.prepareStatement("SELECT banned FROM accounts Where id in (SELECT accountid FROM characters Where id = " + rs.getInt("player_id") + ")");
                var rssearchaccid = pssearchaccid.executeQuery();

                var banned = 0;
                while (rssearchaccid.next()) {
                    banned = rssearchaccid.getInt("banned");
                }

                var name = "";
                name = rs.getString("name");

                if (banned > 0) {
                    name = "#rโดนแบน";
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
            cm.sendOk("เกิดข้อผิดพลาดจ้ะ\r\n\r\n" + e);
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
    var unitWords = ['', '0,000 ', '00 ', '000 ', '0 ']; // Adapted for Korean units, but Thai usually reads full numbers or Millions. 
    // Since this is for display, let's keep it simple or try to match Korean 'Eok' (100 Million).
    // Original: Man (10^4), Eok (10^8), Jo (10^12), Gyeong (10^16)
    // Let's use English abbreviations or Thai equivalents if possible.
    // 1 Eok = 100 Million.
    // Let's stick to a custom format or just Million/Billion if appropriate, but the logic splits by 10000.
    // If I change the split logic it might break.
    // I will use suffixes: 'Man' -> '0k', 'Eok' -> '00M' is tricky.
    // Let's just use Korean unit sounds in Thai or english chars? 
    // Or just simple mapping:
    // array index 1 (10^4): 'Man' -> 'หมื่น' (Ten Thousand)
    // array index 2 (10^8): 'Eok' -> 'ร้อยล้าน' (Hundred Million)
    // array index 3 (10^12): 'Jo' -> 'ล้านล้าน' (Trillion)

    var unitWords = ['', 'หมื่น ', 'ร้อยล้าน ', 'ล้านล้าน ', 'Kyung '];
    var splitUnit = 10000;
    var splitCount = unitWords.length;
    var resultArray = [];
    var resultString = '';
    if (inputNumber == false) {
        cm.sendOk("#fs11#เกิดข้อผิดพลาด กรุณาลองใหม่จ้ะ\r\n(Parsing Error)");
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
    for (var i = 2; i < resultArray.length; i++) { // Starts loop at 2 -> Only shows Eok (10^8) and above as per original comment?
        if (!resultArray[i]) continue;
        resultString = String(resultArray[i]) + unitWords[i] + resultString;
    }
    return resultString;
}



