importPackage(Packages.database);
importPackage(java.lang);

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
        if (mailboxcheck()) {
            cm.getPlayer().dropMessage(-22, "[Mailbox] You have unreceived items. Please collect them from Event NPC - Mailbox!");
        }
        cm.effectText("#fnNanumGothic ExtraBold##fs16#< Royal World > - สวน Royal", 100, 1000, 6, 0, 430, -550);

        cm.showEffect(false, "Effect/EventEffect.img/SalonDebut/screenEff/1366");
        cm.dispose();
    }
}

function mailboxcheck() {
    Check = false;
    try {
        con = DBConnection.getConnection();
        ps = con.prepareStatement("SELECT * FROM `offline` WHERE `chrid` = " + cm.getPlayer().getId() + " AND status = 0");
        rs = ps.executeQuery();
        while (rs.next()) {
            Check = true;
        }
        rs.close();
        ps.close();
        con.close();
        return Check;
    } catch (e) {
        return false;
    }
}