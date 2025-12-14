var status = -1;

purple = "#fMap/MapHelper.img/weather/starPlanet/7#";
blue = "#fMap/MapHelper.img/weather/starPlanet/8#";
blueStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
yellowStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
whiteStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
brownStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
redStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
blackStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
purpleStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
star = "#fUI/FarmUI.img/objectStatus/star/whole#"
S = "#fUI/CashShop.img/CSEffect/today/0#"
reward = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
gain = "#fUI/UIWindow2.img/QuestIcon/4/0#"
color = "#fc0xFF6600CC#"
enter = "\r\n"
enter2 = "\r\n\r\n"

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    var count = 1;
    setting = [
        ["Teng", 1, 813000000, 250],
    ]
    name = ["Tengu"]
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        if (cm.getPlayer().getMapId() == 813000000) {
            cm.sendYesNo("คุณต้องการออกจากลานต่อสู้และกลับสู่เมืองหรือไม่?");
        } else {
            talk = "#fs11#คุณพร้อมที่จะเผชิญหน้ากับ Phoenix Tengu แล้วหรือยัง?\r\n\r\n"
            talk += color + "#L0# [Event Boss] - ขอเข้าท้าทาย #rTengu";
            cm.sendSimple(talk);
        }
    } else if (status == 1) {
        if (cm.getPlayer().getMapId() == 813000000) {
            cm.warp(100000000);
            cm.dispose();
            return;
        }
        st = selection;
        if (cm.getPlayer().getParty() == null) {
            cm.sendOk("คุณต้องอยู่ในปาร์ตี้ที่มีสมาชิกอย่างน้อย 1 คนเพื่อเข้าสู่สนาม");
            cm.dispose();
            return;
        } else if (!cm.isLeader()) {
            cm.sendOk("เฉพาะหัวหน้าปาร์ตี้เท่านั้นที่สามารถขอเข้าสู่สนามได้");
            cm.dispose();
            return;
        } else if (!cm.allMembersHere()) {
            cm.sendOk("สมาชิกทุกคนต้องอยู่ในแผนที่เดียวกัน");
            cm.dispose();
            return;
        } else if (cm.getPlayerCount(setting[st][2]) >= 1) {
            cm.sendNext("มีคนกำลังท้าทายอยู่แล้ว\r\nโปรดลองใหม่อีกครั้งในแชแนลอื่น");
            cm.dispose();
            return;
        }
        if (!cm.isBossAvailable2(setting[st][0], setting[st][1])) {
            talk = "#fs11#สมาชิกปาร์ตี้ "
            for (i = 0; i < cm.BossNotAvailableChrList2(setting[st][0], setting[st][1]).length; i++) {
                if (i != 0) {
                    talk += ", "
                }
                talk += "#b#e" + cm.BossNotAvailableChrList2(setting[st][0], setting[st][1])[i] + ""
            }
            talk += "#k#n ใช้งานจำนวนครั้งเข้าสู่สนามครบแล้วสำหรับวันนี้";
            cm.sendOk(talk);
            cm.dispose();
            return;
        } else if (!cm.isLevelAvailable(setting[st][3])) {
            talk = "#fs11#สมาชิกปาร์ตี้ #b#e"
            for (i = 0; i < cm.LevelNotAvailableChrList(setting[st][3]).length; i++) {
                if (i != 0) {
                    talk += ", "
                }
                talk += "#b#e" + cm.LevelNotAvailableChrList(setting[st][3])[i] + ""
            }
            talk += "#k#n มีเลเวลไม่ถึงเกณฑ์\r\nการท้าทาย " + name[st] + " ต้องมีเลเวล " + setting[st][3] + " ขึ้นไปเท่านั้น";
            cm.sendOk(talk);
            cm.dispose();
            return;
        } else {
            cm.addBoss(setting[st][0]);
            em = cm.getEventManager(setting[st][0]);
            if (em != null) {
                cm.getEventManager(setting[st][0]).startInstance_Party(setting[st][2] + "", cm.getPlayer());
            }
            cm.dispose();
        }
    }
}
