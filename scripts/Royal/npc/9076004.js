importPackage(Packages.database);

importPackage(java.text);
var enter = "\r\n";
var seld = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

var nf = NumberFormat.getInstance();

Color = "#fc0xFF6600CC#"
Black = "#fc0xFF000000#"
Pink = "#fc0xFFFF3366#"
Gray = "#fc0xFFB2B2B2#"

var grade = [
    [0, "Normal"],
    [1, "Grade D"],
    [2, "Grade C"],
    [3, "Grade B"],
    [4, "Grade A"],
    [5, "Grade S"],
    [6, "Grade SS"],
    [7, "Grade SSS"],
    [8, "Grade SSS+"],
    [9, "MVP PLATINUM"],
    [10, "MVP LUXURY"],
    [11, "MVP NOBLE"],
    [12, "MVP CROWN"],
    [13, "MVP PRESTIGE"],
    [14, "MVP ROYAL"],
    [15, "MVP ROYAL+"]
]

var pgrade = [
    [0, "Lv.0 Normal"],
    [1, "Lv.1 Beginning"],
    [2, "Lv.2 Rising"],
    [3, "Lv.3 Flying"],
    [4, "Lv.4 Shining"],
    [5, "Lv.5 Idol"],
    [6, "Lv.6 Superstar"]
]

function action(mode, type, sel) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }
    if (status == 0) {
        if (cm.inBoss()) {
            cm.getPlayer().dropMessage(5, "You cannot use this while a boss battle is in progress.");
            cm.dispose();
            return;
        }

        var msg = "#fs11##fc0xFF990033##e[Ganglim World]#n#fc0xFF000000#'s Ranking Board.#b" + enter + enter;
        msg += "#L1#Level Ranking" + enter;
        msg += "#L8#Job Ranking" + enter;
        //msg += "#L6# Boss Hunter Ranking"+enter;
        //msg += "#L2# Meso Ranking"+enter;
        msg += "#L4#Guild Ranking" + enter;
        msg += "#L3#Fame Ranking" + enter;
        msg += "#L5#Mu Lung Dojo Ranking" + enter;
        //msg += "#L7# PVP Battle Ranking"+enter;
        msg += "#L9#Combat Power Ranking#l" + enter;
        msg += "#L10#Promotion Count Ranking#l" + enter;
        cm.sendSimple(msg);
    } else if (status == 1) {
        seld = sel;
        switch (seld) {
            case 1:
                cm.dispose();
                cm.openNpc(2093000);
                break;
            case 2:
                cm.dispose();
                cm.openNpc(2094000);
                break;
            case 3:
                cm.sendOk(getRank("SELECT id, name, fame FROM characters WHERE `fame` > 0 AND `gm` = 0 ORDER BY `fame` DESC LIMIT ", "fame", "Fame", 50));
                cm.dispose();
                break;
            case 4:
                cm.sendOk(getRankGuild("SELECT name, GP FROM guilds WHERE `GP` > 0 AND `GP` < 250000000 ORDER BY `GP` DESC LIMIT ", "GP", "GP Owned", 50));
                cm.dispose();
                break;
            case 5:
                cm.showDojangRanking();
                cm.getPlayer().dropMessage(5, "It may take some time for Mu Lung Dojo records to be reflected in the rankings. Points are distributed based on overall ranking after weekly settlement.");
                cm.dispose();
                break;
            case 6:
                cm.sendOk(getRank("SELECT * FROM characters WHERE `basebpoint` > 0 AND `gm` = 0 ORDER BY `basebpoint` DESC LIMIT ", "basebpoint", "Points", 10));
                cm.dispose();
                break;
            case 7:
                cm.sendOk(getRank3("SELECT * FROM characters WHERE `willPVPCount` > 0 AND `gm` = 0 ORDER BY `willPVPCount` DESC LIMIT ", "willPVPCount", "WIN", 10, "faillPVPCount"));
                cm.dispose();
                break;
            case 8:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 9076004, "JobRanking");
                break;
            case 9:
                var msg = "#fs11##fc0xFF990033##e[Ganglim World]#n#fc0xFF000000#'s Ranking Board.#b" + enter + enter;
                msg += "#L1#Overall Ranking" + enter;
                msg += "#L2#Job Ranking" + enter;
                cm.sendSimple(msg);
                //cm.sendOk(getRankDamage("SELECT `player_id`, `damage`, `name`, `job` FROM damage_measurement_rank ORDER BY damage DESC LIMIT ", "damage", "", 100));
                //cm.dispose();
                break;
            case 10:
                cm.sendOk(getRankPCount("SELECT `id`, `KEY`, `value` FROM acckeyvalue WHERE `value` > 0 AND `key` = 'PCount' ORDER BY CAST(`value` AS unsigned) DESC LIMIT ", "value", "PromoCount", 100));
                cm.dispose();
                break;
        }
    } else if (status == 2) {
        switch (seld) {
            case 9:
                if (sel == 1) {
                    cm.sendOk(getRankDamage("SELECT `player_id`, `damage`, `name`, `job` FROM damage_measurement_rank ORDER BY damage DESC LIMIT ", "damage", "", 100));
                    cm.dispose();
                    break;
                } else if (sel == 2) {
                    cm.dispose();
                    cm.openNpcCustom(cm.getClient(), 9000213, "JobDamageRanking");
                    break;
                }
                break;
        }
    }
}

function getRank(v1, v2, v3, v4) {
    var ret = "#fs11#Rankings are shown up to #r" + v4 + "th#k place.\r\n\r\n";
    var as = 0;
    var names = [];
    var name = "";

    var con = DBConnection.getConnection();
    var ps = con.prepareStatement(v1 + v4);
    var rs = ps.executeQuery();

    while (rs.next()) {
        as += 1;
        if (as < 10) {
            aas = "#e00#fc0xFF09A17F#" + as + Black + "#nRank | ";
        } else if (as < 100) {
            aas = "#e0#fc0xFF09A17F#" + as + Black + "#nRank | ";
        } else {
            aas = "#e#fc0xFF09A17F#" + as + Black + "#nRank | ";
        }
        var pssearchaccid = con.prepareStatement("SELECT banned FROM accounts Where id in (SELECT accountid FROM characters Where id = " + rs.getInt('id') + ")");
        var rssearchaccid = pssearchaccid.executeQuery();

        while (rssearchaccid.next()) {
            var banned = rssearchaccid.getInt("banned");
        }

        name = rs.getString('name');

        if (name == null) {
            name = "#rDeleted Character";
        }

        if (banned > 0) {
            name = "#rBanned";
        }

        ret += Gray + aas + "#b" + name + "#fc0xFF000000# | " + v3 + " : " + Pink + rs.getInt(v2) + "#k\r\n";
    }
    rs.close();
    ps.close();
    con.close();

    if (ret.equals("")) return "No rankings available.";
    return ret;
}

function getRankGuild(v1, v2, v3, v4) {
    var ret = "#fs11#Rankings are shown up to #r" + v4 + "th#k place.\r\n\r\n";
    var as = 0;
    var names = [];
    var name = "";

    var con = DBConnection.getConnection();
    var ps = con.prepareStatement(v1 + v4);
    var rs = ps.executeQuery();

    while (rs.next()) {
        as += 1;
        if (as < 10) {
            aas = "#e00#fc0xFF09A17F#" + as + Black + "#nRank | ";
        } else if (as < 100) {
            aas = "#e0#fc0xFF09A17F#" + as + Black + "#nRank | ";
        } else {
            aas = "#e#fc0xFF09A17F#" + as + Black + "#nRank | ";
        }

        name = rs.getString('name');

        ret += Gray + aas + "#b" + name + "#fc0xFF000000# | " + v3 + " : " + Pink + rs.getInt(v2) + "#k\r\n";
    }
    rs.close();
    ps.close();
    con.close();

    if (ret.equals("")) return "No rankings available.";
    return ret;
}

function getRankPCount(v1, v2, v3, v4) {
    var ret = "#fs11#Rankings are shown up to #r" + v4 + "th#k place.\r\n";
    var as = 0;
    var names = [];

    var con = DBConnection.getConnection();
    var ps = con.prepareStatement(v1 + v4);
    var rs = ps.executeQuery();


    while (rs.next()) {
        as += 1;
        if (as < 10) {
            aas = "#e00#fc0xFF09A17F#" + as + Black + "#nRank | ";
        } else if (as < 100) {
            aas = "#e0#fc0xFF09A17F#" + as + Black + "#nRank | ";
        } else {
            aas = "#e#fc0xFF09A17F#" + as + Black + "#nRank | ";
        }

        var ps2 = con.prepareStatement("SELECT `name` FROM characters WHERE accountid = " + rs.getString("id") + " ORDER BY `mainchr` DESC, `level` DESC LIMIT 1");
        var rs2 = ps2.executeQuery();
        var ps3 = con.prepareStatement("SELECT `value` FROM acckeyvalue WHERE id = " + rs.getString("id") + " AND `key` = 'pGrade'");
        var rs3 = ps3.executeQuery();

        // Load character nickname
        var findchr = false;



        while (rs2.next()) {
            var pssearchaccid = con.prepareStatement("SELECT banned FROM accounts Where id = " + rs.getString("id"));
            var rssearchaccid = pssearchaccid.executeQuery();

            while (rssearchaccid.next()) {
                var banned = rssearchaccid.getInt("banned");
            }

            if (banned > 0) {
                ret += Gray + aas + "#rBanned";
            } else {
                ret += Gray + aas + "#b" + rs2.getString("name");
            }
            findchr = true;
        }

        // If character does not exist
        if (!findchr)
            ret += Gray + aas + "#rBanned";


        // Load Promotion Grade & Write
        while (rs3.next()) {
            ret += Black + " | " + v3 + " : " + Color + nf.format(rs.getInt(v2)) + Black + " | " + Pink + "[" + pgrade[rs3.getInt("value")][1] + "]#k\r\n";
        }
    }

    rs.close();
    ps.close();
    rs2.close();
    ps2.close();
    rs3.close();
    ps3.close();
    con.close();

    if (ret.equals("")) return "No rankings available.";
    return ret;
}

function getRankDPoint(v1, v2, v3, v4) {
    var ret = "#fs11#Rankings are shown up to #r" + v4 + "th#k place.\r\n";
    ret += Pink + "※ Representative Character Selection Method\r\n";
    ret += Pink + "- Can be set in Discord [Bot Commands] channel\r\n";
    ret += Pink + "- If not set, order by account level (creation order if level is same)\r\n\r\n";
    var as = 0;
    var names = [];

    var con = DBConnection.getConnection();
    var ps = con.prepareStatement(v1 + v4);
    var rs = ps.executeQuery();


    while (rs.next()) {
        as += 1;
        if (as < 10) {
            aas = "#e00#fc0xFF09A17F#" + as + Black + "#nRank | ";
        } else if (as < 100) {
            aas = "#e0#fc0xFF09A17F#" + as + Black + "#nRank | ";
        } else {
            aas = "#e#fc0xFF09A17F#" + as + Black + "#nRank | ";
        }

        var ps2 = con.prepareStatement("SELECT `name` FROM characters WHERE accountid = " + rs.getString("id") + " ORDER BY `mainchr` DESC, `level` DESC LIMIT 1");
        var rs2 = ps2.executeQuery();
        var ps3 = con.prepareStatement("SELECT `value` FROM acckeyvalue WHERE id = " + rs.getString("id") + " AND `key` = 'hGrade'");
        var rs3 = ps3.executeQuery();

        // Load character nickname
        var findchr = false;
        var findgrade = false;

        while (rs2.next()) {
            ret += Gray + aas + "#b" + rs2.getString("name");
            findchr = true;
        }

        // If character does not exist
        if (!findchr)
            ret += Gray + aas + "#rBanned";


        // Load MVP Grade & Write
        while (rs3.next()) {
            ret += Black + " | " + v3 + " : " + Color + nf.format(rs.getInt(v2)) + Black + " | " + Pink + "[" + grade[rs3.getInt("value")][1] + "]#k\r\n";
            findgrade = true;
        }

        // If no MVP Grade
        if (!findgrade)
            ret += Black + " | " + v3 + " : " + Color + nf.format(rs.getInt(v2)) + "#k\r\n";

    }

    rs.close();
    ps.close();
    rs2.close();
    ps2.close();
    rs3.close();
    ps3.close();
    con.close();

    if (ret.equals("")) return "No rankings available.";
    return ret;
}

function getRankCashEn(v1, v2, v3, v4) {
    var ret = "#fs11#Rankings are shown up to #r" + v4 + "th#k place.\r\n\r\n";
    var as = 0;
    var names = [];

    var con = DBConnection.getConnection();
    var ps = con.prepareStatement(v1 + v4);
    var rs = ps.executeQuery();

    while (rs.next()) {
        as += 1;
        if (as < 10) {
            aas = "#e00#fc0xFF09A17F#" + as + Black + "#nRank | ";
        } else if (as < 100) {
            aas = "#e0#fc0xFF09A17F#" + as + Black + "#nRank | ";
        } else {
            aas = "#e#fc0xFF09A17F#" + as + Black + "#nRank | ";
        }

        var ps2 = con.prepareStatement("SELECT `name` FROM characters WHERE id = " + rs.getString("id") + " ORDER BY `mainchr` DESC, `level` DESC LIMIT 1");
        var rs2 = ps2.executeQuery();

        var ps3 = con.prepareStatement("SELECT `value` FROM keyvalue WHERE id = " + rs.getString("id") + " AND `key` = 'cashatk'");
        var rs3 = ps3.executeQuery();

        // Load character nickname
        var findchr = false;

        while (rs2.next()) {
            ret += Gray + aas + "#b" + rs2.getString("name");
            findchr = true;
        }

        // If character does not exist
        if (!findchr)
            ret += Gray + aas + "#rBanned";
        while (rs3.next()) {
            ret += Black + " | " + v3 + " : " + Color + nf.format(rs.getInt(v2)) + Black + " | Atk : " + Color + nf.format(rs3.getInt(v2)) + "#k\r\n";
        }

    }

    rs.close();
    ps.close();
    rs2.close();
    ps2.close();
    con.close();

    if (ret.equals("")) return "No rankings available.";
    return ret;
}

function getRankDamage(v1, v2, v3, v4) {
    var ret = "#fs11#Rankings are shown up to #r100th#k place.\r\n";
    ret += Pink + "※ Damage is shown from units of 100 million\r\n\r\n";
    var as = 0;
    var names = [];

    var con = DBConnection.getConnection();
    var ps = con.prepareStatement(v1 + v4);
    var rs = ps.executeQuery();

    var psmodify = con.prepareStatement("UPDATE damage_measurement_rank SET job = (SELECT job FROM characters Where id = player_id), name = (SELECT name FROM characters Where id = player_id)");
    var rsmodify = psmodify.executeQuery();

    while (rs.next()) {
        as += 1;
        if (as < 10) {
            aas = "#e00#fc0xFF09A17F#" + as + Black + "#nRank | ";
        } else if (as < 100) {
            aas = "#e0#fc0xFF09A17F#" + as + Black + "#nRank | ";
        } else {
            aas = "#e#fc0xFF09A17F#" + as + Black + "#nRank | ";
        }

        name = rs.getString("name");
        job = Packages.constants.GameConstants.getJobNameById(rs.getInt("job"));
        damage = ConvertNumber(rs.getString(v2));
        playerid = rs.getInt("player_id");

        var pssearchaccid = con.prepareStatement("SELECT banned FROM accounts Where id in (SELECT accountid FROM characters Where id = " + playerid + ")");
        var rssearchaccid = pssearchaccid.executeQuery();

        while (rssearchaccid.next()) {
            var banned = rssearchaccid.getInt("banned");
        }

        if (name == null) {
            name = "#rDeleted Character";
        }

        /* Hide banned characters
                if (banned > 0) { // Banned characters not shown
                    name = "#rBanned";
                    as -= 1;
                } else {
                    ret += Gray + aas + "#b" + name;
                    ret += Black + " | Job : "  + Color + job + " | #e" + Black + damage + Black + "#k#n\r\n";
                }
        
                if (as == 100) {
                    break;
                }
        */

        if (banned > 0) { // Banned characters not shown
            name = "#rBanned";
        }

        ret += Gray + aas + "#b" + name;
        ret += Black + " | Job : " + Color + job + " | #e" + Black + damage + Black + "#k#n\r\n";



    }

    rs.close();
    ps.close();
    rsmodify.close();
    psmodify.close();
    con.close();

    if (ret.equals("")) return "No rankings available.";
    return ret;
}

function getRank3(v1, v2, v3, v4, v5) {
    var ret = "#fs11#Rankings are shown up to #r" + v4 + "th#r place.\r\n\r\n";
    var as = 0;
    var names = [];

    var con = DBConnection.getConnection();
    var ps = con.prepareStatement(v1 + v4);
    var rs = ps.executeQuery();

    while (rs.next()) {
        as += 1;
        ret += as + ". #b" + rs.getString("name") + "#k " + v3 + " : #d" + rs.getInt(v2) + " / #kLOSE :#d " + rs.getInt(v5) + " #k\\r\n";
    }
    rs.close();
    ps.close();
    con.close();

    if (ret.equals("")) return "No rankings available.";
    return ret;
}

function ConvertNumber(number) { // Derived from a blog
    var inputNumber = number < 0 ? false : number;
    var unitWords = ['', 'K', 'M', 'B', 'T', 'Q'];
    var splitUnit = 1000;
    var splitCount = unitWords.length;
    var resultArray = [];
    var resultString = '';
    if (inputNumber == false) {
        cm.sendOk("#fs11#An error occurred. Please try again.\r\n(Parsing Error)");
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
        resultString = String(resultArray[i]) + unitWords[i] + " " + resultString;
    }
    return resultString;
}