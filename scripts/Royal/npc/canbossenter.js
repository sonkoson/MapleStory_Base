importPackage(Packages.scripting.newscripting)
importPackage(Packages.constants)

enter = "\r\n"
검은색 = "#fc0xFF000000#"

var status = -1;
var maxcount = 1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        if (cm.inBoss() && !cm.isLeader) {
            cm.getPlayer().dropMessage(5, "ไม่สามารถใช้งานได้ในขณะบอสกำลังดำเนินอยู่");
            cm.dispose();
            return;
        }

        // Max count
        maxcount += cm.getPlayer().getBossTier();

        var msg = "#fs11#";
        msg += 검은색 + "※ จำนวนครั้งที่เข้าได้สูงสุดต่อวัน : #b#e" + maxcount + 검은색 + "#n ( 1 + Boss Tier )" + enter;
        msg += 검은색 + "#r※ ด้านล่างคือจำนวนครั้งที่สามารถเข้าได้" + enter + enter;
        msg += 검은색;
        msg += "#e";
        msg += "Lotus　 | #fc0xFF13b817#NORMAL : #b" + getCount(1234570, "swoo_clear") + 검은색 + " | #fc0xFFdc28bc#HARD : #b" + getCount(1234569, "swoo_clear") + 검은색 + " | #rHELL : #b" + getCountHell(1234569, "hell_swoo_clear") + 검은색 + enter;
        msg += "Damien | #fc0xFF13b817#NORMAL : #b" + getCount(1234570, "demian_clear") + 검은색 + " | #fc0xFFdc28bc#HARD : #b" + getCount(1234569, "demian_clear") + 검은색 + " | #rHELL : #b" + getCountHell(1234569, "hell_demian_clear") + 검은색 + enter;
        msg += "Lucid | #fc0xFF13b817#NORMAL : #b" + getCount(1234570, "lucid_clear") + 검은색 + " | #fc0xFFdc28bc#HARD : #b" + getCount(1234569, "lucid_clear") + 검은색 + " | #rHELL : #b" + getCountHell(1234569, "hell_lucid_clear") + 검은색 + enter;
        msg += "Will　　 | #fc0xFF13b817#NORMAL : #b" + getCount(1234570, "will_clear") + 검은색 + " | #fc0xFFdc28bc#HARD : #b" + getCount(1234569, "will_clear") + 검은색 + " | #rHELL : #b" + getCountHell(1234569, "hell_will_clear") + 검은색 + enter;
        msg += "Dunkel　 | #fc0xFF13b817#NORMAL : #b" + getCount(1234589, "dunkel_clear") + 검은색 + " | #fc0xFFdc28bc#HARD : #b" + getCount(1234569, "dunkel_clear") + 검은색 + " | #rHELL : #b" + getCountHell(1234569, "hell_dunkel_clear") + 검은색 + enter;
        msg += "Dusk | #fc0xFF13b817#NORMAL : #b" + getCount(1234590, "dusk_clear") + 검은색 + " | #fc0xFFdc28bc#CHAOS : #b" + getCount(1234589, "dusk_clear") + 검은색 + enter;
        msg += "PA Slime | #fc0xFF13b817#NORMAL : #b" + getCount(1234570, "guardian_angel_slime_clear") + 검은색 + " | #fc0xFFdc28bc#CHAOS : #b" + getCount(1234569, "guardian_angel_slime_clear") + 검은색 + enter;
        msg += "Jin Hilla | #fc0xFFdc28bc#HARD : #b" + getCount(1234569, "jinhillah_clear") + 검은색 + " |" + enter;
        msg += "Black Mage　 | #fc0xFFdc28bc#HARD : #b" + getCount(1234570, "blackmage_clear") + 검은색 + " |" + enter;
        msg += "Seren　 | #fc0xFFdc28bc#HARD : #b" + getCount(QuestExConstants.SerniumSeren.getQuestID(), "clear") + 검은색 + " |" + enter;
        cm.sendOkS(msg, 2);
        cm.dispose();
    }
}

function getCount(keyvalue, data) {
    return maxcount - cm.getPlayer().getOneInfoQuestInteger(keyvalue, data)
}

function getCountHell(keyvalue, data) {
    if (1 - cm.getPlayer().getOneInfoQuestInteger(keyvalue, data) < 0) {
        return 0
    } else {
        return 1 - cm.getPlayer().getOneInfoQuestInteger(keyvalue, data)
    }
}