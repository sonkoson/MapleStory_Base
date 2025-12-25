importPackage(Packages.database);
importPackage(java.lang);

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

function init() {
    em.setProperty("entry", "true");
}

function monsterValue(eim, mobId) {
    return 1;
}

function setup(eim) {
    em.getProperties().clear();
    var eim = em.newInstance("DamageMeter");
    var map = eim.setInstanceMap(450002250); // Measurement Map
    map.resetFully();
    map.killMonster(9300800); // Monster Code
    var mob = em.getMonster(9300800); // Monster Code
    //mob.setHp(9000000000000000000);
    eim.registerMonster(mob);
    map.spawnMonsterOnGroundBelow(mob, new java.awt.Point(-100, 100)); // Coordinates
    eim.startEventTimer(120000); // Time (1s = 1000)
    return eim;
}

function playerEntry(eim, player) {
    var map = eim.getMapFactory().getMap(450002250); // Measurement Map
    player.changeMap(map, map.getPortal(0));
    em.setProperty("entry", "false");
}

function changedMap(eim, player, mapid) {
    if (mapid != 450002250) { // Measurement Map
        em.setProperty("entry", "true");
        eim.unregisterPlayer(player);
        eim.dispose();
    }
}

function playerRevive(eim, player) { }

function end(eim) {
    try {
        em.setProperty("entry", "true");
        var player = eim.getPlayers().get(0);
        var map = eim.getMapFactory().getMap(100000000); // Exit Map
        player.changeMap(map, map.getPortal(0));
        //if (player.getGMLevel() > 5) {
        var con = null;
        var ps = null;
        con = DatabaseConnection.getConnection();
        ps = con.prepareStatement("DELETE FROM `DamageMeter` WHERE `characterid` = " + player.getId());
        ps.executeUpdate();
        ps.close();
        ps = con.prepareStatement("INSERT INTO `DamageMeter` (`characterid`, `name`, `damage`) VALUES (?, ?, ?)");
        ps.setInt(1, player.getId());
        ps.setString(2, player.getName());
        ps.setLong(3, player.DamageMeter);
        ps.executeUpdate();
        ps.close();
        con.close();
        //}
        eim.unregisterPlayer(player);
        eim.dispose();
        return;
    } catch (e) {
        cm.sendOk("เกิดข้อผิดพลาด\r\nกรุณาลองวัด Damage Meter ใหม่อีกครั้ง\r\n\r\n" + e);
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
}

function scheduledTimeout(eim) {
    var player = eim.getPlayers().get(0);
    eim.broadcastPlayerMsg(2, "[System] : หมดเวลาแล้ว บันทึกข้อมูลดาเมจเรียบร้อย ดาเมจสะสมทั้งหมด : " + player.DamageMeter);
    end(eim);
}

function playerDead(eim, player) { }
function playerDisconnected(eim, player) {
    end(eim);
    return 0;
}
function allMonstersDead(eim) { }
function cancelSchedule() { }
function leftParty(eim, player) { }
function disbandParty(eim, player) { }
