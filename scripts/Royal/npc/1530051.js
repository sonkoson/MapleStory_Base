importPackage(Packages.database);
importPackage(java.lang);

var enter = "\r\n";
var status = -1;
var seld = -1;
var seldGM = -1;
var step = -1;
var targetName = "";
var amount = 0;

var DB_SCHEMA = "ganglim";

var grade = [
    [0, "Lv.0"],
    [1, "Lv.1"],
    [2, "Lv.2"],
    [3, "Lv.3"],
    [4, "Lv.4"],
    [5, "Lv.5"],
    [6, "Lv.6"]
];

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
        if (cm.inBoss()) {
            cm.getPlayer().dropMessage(5, "This feature cannot be used during boss battles.");
            cm.dispose();
            return;
        }

        if (cm.getClient().getKeyValue("pGrade") == null) {
            cm.getClient().setKeyValue("pGrade", "0");
        }
        if (cm.getClient().getKeyValue("PCount") == null) {
            cm.getClient().setKeyValue("PCount", "0");
        }

        var loadpGrade = parseInt(cm.getClient().getKeyValue("pGrade"));
        var loadPCount = parseInt(cm.getClient().getKeyValue("PCount"));

        if (loadPCount >= 3000 && loadpGrade < 6) {
            setpGrade(6);
            return;
        } else if (loadPCount >= 2000 && loadpGrade < 5) {
            setpGrade(5);
            return;
        } else if (loadPCount >= 1000 && loadpGrade < 4) {
            setpGrade(4);
            return;
        } else if (loadPCount >= 600 && loadpGrade < 3) {
            setpGrade(3);
            return;
        } else if (loadPCount >= 300 && loadpGrade < 2) {
            setpGrade(2);
            return;
        } else if (loadPCount >= 100 && loadpGrade < 1) {
            setpGrade(1);
            return;
        }

        // Display current Promotion Points, Grade, Cumulative Count
        var promoPoint = comma(cm.getPlayer().getHPoint());
        var promoGrade = cm.getPlayer().getPgrades();
        var promoCount = comma(cm.getClient().getKeyValue("PCount"));

        var msg = "#fs11#";
        msg += "#fc0xFFFF3366##h ##fc0xFF000000#'s Promotion Points : #fc0xFFFF3366#" + promoPoint + "P#fc0xFF000000##n" + enter;
        msg += "#fc0xFFFF3366##h ##fc0xFF000000#'s Promotion Grade : #fc0xFFFF3366#" + promoGrade + "#fc0xFF000000##n#b" + enter;
        msg += "#fc0xFFFF3366##h ##fc0xFF000000#'s Total Promotion Count : #fc0xFFFF3366#" + promoCount + "#k#b" + enter + enter;

        msg += "#L1##e[ Promotion Point Shop ]#n#l    " + enter + enter;
        msg += "#L4#[ Transcendent Arcane Symbol ]#l" + enter + enter;
        msg += "#L5#[ Enhance Cosmetic Items ]#l" + enter + enter;
        msg += "#L2#I want to know the benefits of Promotion Grade.#l" + enter;
        msg += "#L3#I want to receive Promotion Points.#l" + enter; // ← New option added

        if (cm.getPlayer().isGM()) {
            msg += enter + enter + "#r#h GM#, This is a GM-only menu!#fc0xFF000000#" + enter;
            msg += "#L99#GM: Open Promotion Point/Count Distribution Menu#l";
        }

        cm.sendSimple(msg);

    }
    else if (status == 1) {
        seld = sel;

        if (seld == 1) {
            cm.dispose();
            cm.openNpcCustom(cm.getClient(), 9000213, "PromotionShop");
            return;
        }
        else if (seld == 2) {
            cm.dispose();
            cm.openNpcCustom(cm.getClient(), 9000213, "pgradeinfo");
            return;
        }
        else if (seld == 3) {
            giveHongboReward();
            return;
        }
        else if (seld == 4) {
            cm.dispose();
            cm.openNpcCustom(cm.getClient(), 9000178, "TranscendentArcaneSymbol");
            return;
        }
        else if (seld == 5) {
            cm.dispose();
            cm.openNpcCustom(cm.getClient(), 9000331, "GanglimCashEnhancement");
            return;
        }
        // 99) GM Only
        else if (seld == 99 && cm.getPlayer().isGM()) {
            var gmMsg = "#fs12#[GM Menu] What would you like to do?#fc0xFF000000##fs11#" + enter;
            gmMsg += "#L100#1. Give Promotion Points to User#l" + enter;
            gmMsg += "#L101#2. Give Cumulative Promotion Count to User#l";

            cm.sendSimple(gmMsg);
            return;
        }

        cm.sendOk("Invalid access.");
        cm.dispose();
    }
    else if (status == 2) {
        if (seld == 99 && cm.getPlayer().isGM()) {
            seldGM = sel;
            if (seldGM == 100) {
                cm.sendGetText("Please enter the exact nickname of the user to give points to.");
                step = 1;
                return;
            } else if (seldGM == 101) {
                cm.sendGetText("Please enter the exact nickname of the user to give cumulative count to.");
                step = 1;
                return;
            } else {
                cm.sendOk("Invalid access.");
                cm.dispose();
                return;
            }
        }

        cm.sendOk("Invalid flow.");
        cm.dispose();
    }
    else if (status == 3) {
        if (step == 1) {
            targetName = cm.getText().trim();
            if (targetName.length < 1) {
                cm.sendOk("Please enter a valid nickname.");
                cm.dispose();
                return;
            }

            if (seldGM == 100) {
                cm.sendGetText("🔹 Enter Promotion Points (P) to give to [" + targetName + "].\r\n(Numbers only, e.g., 500)");
            } else if (seldGM == 101) {
                cm.sendGetText("🔹 Enter Cumulative Promotion Count to give to [" + targetName + "].\r\n(Numbers only, e.g., 10)");
            }
            step = 2;
            return;

        } else {
            cm.sendOk("An unexpected error occurred.");
            cm.dispose();
            return;
        }
    }
    else if (status == 4) {
        if (step == 2) {
            var txt = cm.getText().trim();
            if (!/^\d+$/.test(txt)) {
                cm.sendOk("Please enter numbers only.");
                cm.dispose();
                return;
            }
            amount = parseInt(txt);

            var target = cm.getClient().getChannelServer()
                .getPlayerStorage()
                .getCharacterByName(targetName);

            if (target == null) {
                cm.sendOk("The character with that name does not exist in the current channel.\r\nThey may have changed channels or disconnected.");
                cm.dispose();
                return;
            }

            if (!cm.getPlayer().isGM()) {
                cm.sendOk("You do not have permission.");
                cm.dispose();
                return;
            }

            if (seldGM == 100) {
                target.gainHPoint(amount);
                cm.sendOk("Given " + amount + "P Promotion Points to [" + targetName + "].");
                cm.dispose();
                return;

            } else if (seldGM == 101) {
                var oldCount = parseInt(target.getClient().getKeyValue("PCount"));
                var newCount = oldCount + amount;
                target.getClient().setKeyValue("PCount", "" + newCount);
                cm.sendOk("Increased [" + targetName + "]'s cumulative promotion count by " + amount + ", setting total to " + newCount + ".");
                cm.dispose();
                return;

            } else {
                cm.sendOk("Invalid access.");
                cm.dispose();
                return;
            }

        } else {
            cm.sendOk("An unexpected flow error occurred.");
            cm.dispose();
            return;
        }
    }
    else {
        cm.dispose();
    }
}

function giveHongboReward() {
    var con = null;
    var ps = null, rs = null;
    try {
        con = DBConnection.getConnection();

        var dbgPS = con.prepareStatement("SELECT DATABASE()");
        var dbgRS = dbgPS.executeQuery();
        dbgRS.close();
        dbgPS.close();

        ps = con.prepareStatement(
            "SELECT id, blog, etc " +
            "FROM " + DB_SCHEMA + ".hongbo " +
            "WHERE `name` = ? AND `check` = 0"
        );
        ps.setString(1, cm.getPlayer().getName());
        rs = ps.executeQuery();

        var totalBlog = 0;  // 지급할 홍보 횟수 합계
        var totalEtc = 0;  // 지급할 홍보 포인트 합계
        var idList = new java.util.ArrayList();

        while (rs.next()) {
            totalBlog += rs.getInt("blog");
            totalEtc += rs.getInt("etc");
            idList.add(rs.getInt("id"));
        }
        rs.close();
        ps.close();

        if (idList.size() == 0) {
            cm.sendOk(
                "#fs11#▶ No pending promotion reward data found.\r\n" +
                "   Total Promotion Points to distribute: 0P\r\n" +
                "   Total Promotion Count to distribute: 0"
            );
            cm.dispose();
            return;
        }

        cm.getPlayer().gainHPoint(totalEtc);

        var oldCount = parseInt(cm.getClient().getKeyValue("PCount"));
        if (isNaN(oldCount)) {
            oldCount = 0;
        }
        var newCount = oldCount + totalBlog;
        cm.getClient().setKeyValue("PCount", "" + newCount);

        var updatePS = con.prepareStatement(
            "UPDATE " + DB_SCHEMA + ".hongbo SET `check` = 1 WHERE id = ?"
        );
        for (var i = 0; i < idList.size(); i++) {
            updatePS.setInt(1, idList.get(i));
            updatePS.executeUpdate();
        }
        updatePS.close();

        cm.sendOk(
            "#fs11#▶ Promotion rewards have been successfully processed." + enter +
            "   - Points Distributed : " + totalEtc.toLocaleString() + "P" + enter +
            "   - Promotion Count Distributed : " + totalBlog + "" + enter + enter +
            "   - (Previous Cumulative: " + oldCount + " → Current: " + newCount + ")"
        );
        cm.dispose();
        return;

    } catch (e) {
        cm.sendOk("An error occurred while processing promotion rewards:\r\n" + e.toString());
        e.printStackTrace();
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
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

function setpGrade(gradeA) {
    var loadpGrade = parseInt(cm.getClient().getKeyValue("pGrade"));
    cm.getClient().setKeyValue("pGrade", "" + gradeA);
    cm.getPlayer().giveDonatorBuff();
    cm.sendOk(
        "#fs11#Congratulations! Your Promotion Grade has changed.\r\n\r\n" +
        "Previous Grade : #r" + grade[loadpGrade][1] + "#k\r\n" +
        "New Grade : #b" + grade[gradeA][1]
    );
    cm.dispose();
}

function comma(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}
