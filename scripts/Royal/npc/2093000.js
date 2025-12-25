importPackage(java.sql);

importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);

importPackage(Packages.database);

Green = "#fc0xFF0FA400#"
Black = "#fc0xFF000000#"
Pink = "#fc0xFFFF3366#"

function start() {
    var i = 0;
    var a = 0;
    var b = 0;
    var print = new StringBuilder();
    var con = DBConnection.getConnection();
    var ps = con.prepareStatement("SELECT name, level, job, levelUpTime FROM characters WHERE gm = 0 ORDER BY level DESC, exp DESC, levelUpTime ASC LIMIT 100");
    var rs = ps.executeQuery();

    while (rs.next()) {
        i++;
        chrname = rs.getString("name");
        job = Packages.constants.GameConstants.getJobNameById(rs.getInt("job"));
        level = rs.getInt("level");
        date = rs.getDate("levelUpTime");

        if (i < 10) {
            aas = "#e00#fc0xFF09A17F#" + i + Black + "#n Place | ";
        } else if (i < 100) {
            aas = "#e0#fc0xFF09A17F#" + i + Black + "#n Place | ";
        } else {
            aas = "#e#fc0xFF09A17F#" + i + Black + "#n Place | ";
        }


        if (rs.getInt("level") == 777) {
            print.append("#fc0xFFb2b2b2#" + aas).append("#b").append(chrname).append("#fc0xFF000000# | #rLevel : ").append("#fc0xFFFF3366##e").append(level).append("#n");
            print.append("#fc0xFF000000# | #fc0xFF6600CC#").append(job).append("#fc0xFF000000# | ").append(date).append("\r\n");
        } else {
            print.append("#fc0xFFb2b2b2#" + aas).append("#b").append(chrname).append("#fc0xFF000000# | #rLv: ").append("#fc0xFF000000#").append(level);
            print.append("#fc0xFF000000# | #fc0xFF6600CC#").append(job).append("\r\n");
        }
        //print.append("#fc0xFF000000# | #fc0xFF6600CC#Job : #fc0xFF000000#").append(job).append("\r\n");
    }

    //cm.sendOk("#fs11#When you reach max level (300), your #rTranscendence Level#fc0xFF000000# increases.\r\nThe #bblue#fc0xFF000000# number next to the level is your #rTranscendence Level#fc0xFF000000#.\r\n※ Transcendence Level may be #rreset#fc0xFF000000#.\r\n\r\n"+print.toString());
    cm.sendOk("#fs11##fc0xFF000000#การจัดอันดับเรียงตาม:\r\n#bLevel -> EXP -> วันที่ถึงเลเวลนั้น\r\n\r\n" + print.toString());
    rs.close();
    ps.close();
    con.close();
    cm.dispose();
}