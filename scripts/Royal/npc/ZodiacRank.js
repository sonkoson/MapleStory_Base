importPackage(java.lang);
importPackage(Packages.tools.packet);
importPackage(Packages.handling.world);

req = [
    [0, [[4310266, 300], [4001715, 10]], 100000000, "[Bronze]"],
    [0, [[4310266, 700], [4001715, 20]], 2000000000, "[Silver]"],
    [0, [[4310266, 1300], [4001715, 50]], 5000000000, "[Gold]"],
    [0, [[4310266, 2300], [4001715, 150]], 15000000000, "[Platinum]"],
    [0, [[4310266, 4500], [4001715, 300]], 30000000000, "[Diamond]"],
    [0, [[4310266, 6000], [4001715, 500]], 50000000000, "[Master]"],
    [0, [[4310266, 9500], [4001715, 750]], 75000000000, "[Grand Master]"],
    [0, [[4310266, 15000], [4001715, 900]], 90000000000, "[Challenger]"],
    [0, [[4310266, 30000], [4001715, 900]], 90000000000, "[Noble]"],
    [0, [[4310266, 100000], [4001715, 2000]], 200000000000, "[Imperial]"],
    [0, [[4310266, 200000], [4001715, 4000]], 400000000000, "[Specialist]"]
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
        /*
                if (gK() - 1 >= 10) {
                    cm.sendOk("#fs11#ท่านเลื่อนขั้นถึงระดับสูงสุดแล้ว ไม่สามารถเลื่อนขั้นได้อีก");
                    cm.dispose();
                    return;
                }
        */
        if (gK() >= 11) {
            talk = "#fs11#ท่านเลื่อนขั้นถึงระดับสูงสุดแล้ว ไม่สามารถเลื่อนขั้นได้อีก\r\n\r\n";
        }
        if (gK() < 11) {
            talk = "#fs11##fc0xFF990033##eระบบ Main Rank Upgrade#n#fc0xFF000000#\r\n#bMain Rank Upgrade#fc0xFF000000# จะทำให้ท่านแข็งแกร่งยิ่งขึ้น สนใจไหม!?\r\n\r\n"
            talk += "#L0##bต้องการเลื่อนขั้นเป็นระดับถัดไป#l#k#n\r\n";
        }
        talk += "#L1##bสงสัยเกี่ยวกับสิทธิประโยชน์ของแต่ละ Rank#l#k#n";
        cm.sendSimple(talk);

    } else if (status == 1) {
        if (selection == 0) {
            talk = "#fs11##bRank ถัดไป : " + req[gK()][3] + "\r\n#kในการเลื่อนขั้นเป็นระดับถัดไป, จำเป็นต้องใช้ไอเท็มดังนี้\r\n\r\n"
            talk += "#k\r\n";
            for (i = 0; i < req[gK()][1].length; i++) {
                talk += "#i" + req[gK()][1][i][0] + "# #b#z" + req[gK()][1][i][0] + "##r " + req[gK()][1][i][1] + " ชิ้น#k\r\n"; // Quantity : cm.itemQuantity(req[gK()][1][i][0])
            }
            //talk += "#i4031138# #bMeso #r" + req[gK()][2] + "#k\r\n\r\n"
            //talk += "#r#eระวัง : กรุณาทำช่องในกระเป๋า Equip และ Consume ให้ว่างอย่างน้อย 5 ช่อง.#k#n\r\n"
            talk += " #rต้องการเลื่อนขั้นจริงๆ หรือไม่?#k"
            cm.sendYesNo(talk);
        } else {
            cm.dispose();
            cm.openNpcCustom(cm.getClient(), 9062294, "mainrankinfo");
        }
    } else if (status == 2) {
        for (i = 0; i < req[gK()][1].length; i++) {
            if (cm.itemQuantity(req[gK()][1][i][0]) < req[gK()][1][i][1]) {
                cm.sendOk("#fs11#ดูเหมือนว่าท่านจะมี #eวัตถุดิบ#n ที่จำเป็นสำหรับการเลื่อนขั้นไม่พอนะ?");
                cm.dispose();
                return;
            }
        }
        before = gK();
        after = gK() + 1;
        if (Math.floor(Math.random() * 100) < 100) {

            cm.dispose();
            try {
                if (after >= 7 && after <= 9) { // GrandMaster(7) ~ Overlord(9)
                    cm.worldGMMessage(22, "[Main Rank] " + cm.getPlayer().getName() + " เลื่อนขั้นเป็นระดับ " + req[gK()][3]);
                } else if (after >= 10) { // Verga, Sirius
                    cm.worldGMMessage(5, "[Main Rank] " + cm.getPlayer().getName() + " เลื่อนขั้นเป็นระดับ " + req[gK()][3]);
                }

                //Packages.scripting.NPCConversationManager.writeLog("TextLog/zenia/UpgradeRank/[MainRankUpgrade].log", "\r\nAccount : " + cm.getClient().getAccountName() + " (" + cm.getClient().getAccID() + ")\r\nName : " + cm.getPlayer().getName() + "\r\nUpgrade Rank : " + req[gK()][3] + " - " + Integer.parseInt(gK()+1) + "\r\n\r\n", true);
                cm.addCustomLog(1, "[Main Rank] Upgrade Rank : " + req[gK()][3] + " - " + Integer.parseInt(gK() + 1) + "");
                cm.effectText("#fnArial##fs20#[Main Rank] เลื่อนขั้นเป็นระดับ < " + req[gK()][3] + " > แล้ว", 50, 1000, 6, 0, 330, -550);

                cm.getPlayer().setKeyValue(190823, "grade", "" + (gK() + 1) + "");
                cm.getPlayer().setBonusCTSStat();

                prevflag = cm.getPlayer().getSaveFlag();
                cm.getPlayer().setSaveFlag(64); // QuestInfo
                cm.getPlayer().saveToDB(false, false);
                cm.getPlayer().setSaveFlag(prevflag)

                cm.showEffect(false, "Effect/EventEffect.img/SalonDebut/screenEff/1366");
                cm.showEffect(false, "Effect/CharacterEff.img/GradeUp");
            } catch (err) {
                cm.addCustomLog(50, "[ZodiacRank.js] Error : " + err + "");
            }
        } else {
            cm.sendOk("#fs11#การเลื่อนขั้นล้มเหลว")
        }

        if (after < 10) {
            cm.gainItem(req[before][1][0][0], -req[before][1][0][1]);
            cm.gainItem(req[before][1][1][0], -req[before][1][1][1]);
        } else if (after == 10) { // Vega
            cm.gainItem(4310266, -30000);
            cm.gainItem(4310266, -30000);
            cm.gainItem(4310266, -30000);
            cm.gainItem(4310266, -10000);
            cm.gainItem(req[before][1][1][0], -req[before][1][1][1]);
        } else if (after == 11) { // Sirius
            cm.gainItem(4310266, -30000);
            cm.gainItem(4310266, -30000);
            cm.gainItem(4310266, -30000);
            cm.gainItem(4310266, -30000);
            cm.gainItem(4310266, -30000);
            cm.gainItem(4310266, -30000);
            cm.gainItem(4310266, -20000);
            cm.gainItem(req[before][1][1][0], -req[before][1][1][1]);
        }

    }
}

function gK() {
    return cm.getPlayer().getKeyValue(190823, "grade");
}
