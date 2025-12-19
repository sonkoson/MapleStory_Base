importPackage(Packages.database);

var jobList = [ // Only show 4th job and above
    // Adventurer
    [112, "Hero"], [122, "Paladin"], [132, "Dark Knight"],
    [212, "Arch Mage (F/P)"], [222, "Arch Mage (I/L)"], [232, "Bishop"],
    [312, "Bow Master"], [322, "Marksman"], [332, "Pathfinder"],
    [412, "Night Lord"], [422, "Shadower"], [434, "Dual Blade"],
    [512, "Buccaneer"], [522, "Corsair"], [532, "Cannoneer"],
    // Cygnus
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
    [16412, "Ho Young"], [16212, "Lara"],
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
        var say = "#fs11#นี่คือ Level Ranking แยกตามอาชีพ\r\nกรุณาเลือกอาชีพที่ต้องการดู Ranking\r\n\r\n";
        for (var i = 0; i < jobList.length; i++) {
            say += "#L" + i + "#" + jobList[i][1] + "#l\r\n";
        }
        cm.sendSimple(say);
    } else if (status == 1) {
        var con = null;
        var ps = null;
        var rs = null;
        var count = 0;
        var say = "#fs11##fc0xFF000000#Level Ranking ของอาชีพ " + jobList[selection][1] + "\r\n\r\nลำดับ Ranking เรียงตาม:\r\n#bLevel -> EXP -> วันที่ถึงเลเวลนั้น\r\n\r\n";
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("SELECT name, level FROM `characters` WHERE `job` = " + jobList[selection][0] + " ORDER BY `level` DESC, `exp` DESC, `levelUpTime` ASC LIMIT 100"); //AND `gm` = 0 
            rs = ps.executeQuery();
            while (rs.next()) {
                count++;
                if (count < 10) {
                    aas = "#e00#fc0xFF09A17F#" + count + black + "#n | ";
                } else if (count < 100) {
                    aas = "#e0#fc0xFF09A17F#" + count + black + "#n | ";
                } else {
                    aas = "#e#fc0xFF09A17F#" + count + black + "#n | ";
                }

                say += "#fc0xFFB2B2B2#" + aas + "#b" + rs.getString("name") + "#k | #rLevel: #fc0xFF000000#" + rs.getInt("level") + "\r\n";
            }
            rs.close();
            ps.close();
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
