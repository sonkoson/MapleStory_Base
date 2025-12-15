importPackage(java.lang);
importPackage(Packages.server);

req = [
    [0, [[4001715, 30], [4310266, 100], [4310308, 50]], 0],
    [0, [[4001715, 50], [4310266, 200], [4310308, 100]], 0],
    [0, [[4001715, 70], [4310266, 300], [4310308, 350]], 0],
    [0, [[4001715, 90], [4310266, 400], [4310308, 700]], 0],
    [0, [[4001715, 150], [4310266, 500], [4310308, 1500]], 0],
    [0, [[4001715, 250], [4310266, 600], [4310308, 2700]], 0],
    [0, [[4001715, 350], [4310266, 700], [4310308, 3000]], 0],
    [0, [[4001715, 500], [4310266, 800], [4310308, 5000]], 0]
]

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode <= 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
        say = "#fs11##fc0xFF990033##eBoss Rank Upgrade System#n#fc0xFF000000#\r\n#bสนใจที่จะแข็งแกร่งขึ้นผ่านการเลื่อนขั้น Boss Rank หรือไม่!?#fc0xFF000000#\r\n\r\n"
        if (gK() >= 8) {
            say += "#rBoss Rank ของคุณถึงระดับสูงสุดแล้ว#l\r\n";
        } else {
            say += "#L0##bขอเลื่อนขั้นเป็นเลเวล " + (gK() + 1) + "#l\r\n";
        }
        say += "#L1##bBoss Rank คืออะไร?#l"
        cm.sendSimple(say);
    } else if (status == 1) {
        if (selection == 0) {
            say = "ต้องใช้วัสดุดังต่อไปนี้เพื่อเลื่อนขั้นเป็นเลเวล " + (gK() + 1) + "#fs11#\r\n\r\n"

            for (i = 0; i < req[gK() - 0][1].length; i++) {
                say += "#i" + req[gK()][1][i][0] + "# #b#z" + req[gK() - 0][1][i][0] + "##r " + req[gK()][1][i][1] + " ชิ้น#k\r\n"; // Qty : cm.itemQuantity(req[gK()][1][i][0])
            }
            //Msg+= "#i4031138# #bMeso "+req[gK()][2]+"#k\r\n\r\n"
            say += " \r\n#fs11##e#bต้องการเลื่อนขั้นจริงๆ ใช่ไหม?#k#n"
            cm.sendYesNo(say);
        } else {
            cm.sendOk("#fs11##fc0xFF990033#[บัฟการเลื่อนขั้นตาม Rank]\r\n\r\nต่อ [1] Rank\r\n#bBoss Damage + 10%\r\n#bBoss Entry Count + 1\r\n\r\n#fc0xFF990033#[Boss Rank Level 2]\r\n#bเข้า Hard Lotus, Damien, Lucid ได้\r\n\r\n#fc0xFF990033#[Boss Rank Level 3]\r\n#bเข้า Normal Will, Normal Guardian Angel Slime ได้\r\n\r\n#fc0xFF990033#[Boss Rank Level 4]\r\n#bเข้า Normal Dunkel, Normal Dusk ได้\r\n\r\n#fc0xFF990033#[Boss Rank Level 5]\r\n#bเข้า Hard Will, Chaos Guardian Angel Slime ได้\r\n\r\n#fc0xFF990033#[Boss Rank Level 6]\r\n#bเข้า Hard Dunkel, Chaos Dusk, Jin Hilla ได้\r\n\r\n#fc0xFF990033#[Boss Rank Level 7]\r\n#bเข้า Black Mage, Seren ได้\r\n\r\n#fc0xFF990033#[Boss Rank Level 8]\r\n#bเข้า Hell Mode Boss ได้");
            cm.dispose();
        }
    } else if (status == 2) {
        /*if (cm.getPlayer().getBossPoint() < req[gK()][0]) {
            cm.sendOk("Not enough Boss Points.");
            cm.dispose();
            return;
        }*/
        for (i = 0; i < req[gK() - 0][1].length; i++) {
            if (cm.itemQuantity(req[gK()][1][i][0]) < req[gK()][1][i][1]) {
                cm.sendOk("#fs11#ดูเหมือนวัสดุสำหรับ #eเลื่อนขั้น#n จะไม่พอ");
                cm.dispose();
                return;
            }
        }

        ggK = gK();
        if (Math.floor(Math.random() * 100) < 100) {
            cm.dispose();
            try {
                if ((gK() + 1) >= 6) // World message for Rank 6+
                    cm.worldGMMessage(22, "[Boss Rank] คุณ " + cm.getPlayer().getName() + " ได้เลื่อนเป็น Rank " + (gK() + 1));
                //Packages.scripting.NPCConversationManager.writeLog("TextLog/zenia/RankUp/[BossRankUp].log", "\r\nAccount : " + cm.getClient().getAccountName() + " (" + cm.getClient().getAccID() + ")\r\nNickname : " + cm.getPlayer().getName() + "\r\nRank : " +Integer.parseInt(gK()+1) + "\r\n\r\n", true);
                cm.addCustomLog(2, "[Boss Rank] Rank Up Grade : " + Integer.parseInt(gK() + 1) + "");
                cm.effectText("#fnArial##fs20#[Boss Rank] เลื่อนขั้นเป็น Rank < " + (gK() + 1) + " >", 50, 1000, 6, 0, 330, -550);

                cm.getPlayer().setBossTier(gK() + 1);
                cm.getPlayer().saveToDB(false, false);
                cm.getPlayer().setBonusCTSStat();
                cm.showEffect(false, "Effect/EventEffect.img/SalonDebut/screenEff/1366");
                cm.showEffect(false, "Effect/CharacterEff.img/GradeUp");
            } catch (err) {
                cm.addCustomLog(50, "[BossRank.js] Error : " + err + "");
            }
        } else {
            cm.sendOk("#fs11#การเลื่อนขั้นล้มเหลว")
        }
        for (i = 0; i < req[ggK][1].length; i++) {
            cm.gainItem(req[ggK][1][i][0], -req[ggK][1][i][1]);
        }

    }
}

function gK() {
    return cm.getPlayer().getBossTier();
}