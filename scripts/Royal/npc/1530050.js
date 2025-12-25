// Donation System
importPackage(Packages.server);
importPackage(Packages.database);
importPackage(Packages.client);
importPackage(java.lang);
importPackage(Packages.network.game);

var Purple = "#fMap/MapHelper.img/weather/starPlanet/7#";
var Blue = "#fMap/MapHelper.img/weather/starPlanet/8#";
var StarBlue = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
var StarYellow = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
var StarWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
var StarBrown = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
var StarRed = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
var StarBlack = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
var StarPurple = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
var Star = "#fUI/FarmUI.img/objectStatus/star/whole#"
var S = "#fUI/CashShop.img/CSEffect/today/0#"
var Reward = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
var Obtain = "#fUI/UIWindow2.img/QuestIcon/4/0#"
var Color = "#fc0xFF6600CC#"
var Black = "#fc0xFF000000#"
var Pink = "#fc0xFFFF3366#"
var LightPink = "#fc0xFFF781D8#"
var Enter = "\r\n"
var Enter2 = "\r\n\r\n"
enter = "\r\n";

var seld = -1;
var seldreward = -1;
var seld2 = -1;
var seld3 = -1;

var reward = 0;
var seldgrade = 0;
var S1 = -1;

// Achievement Reward
var nreward = [
    { 'ngrade': 1, 'items': [[1143891, 1], [4310266, 2000], [4001715, 200]], 'select': false },
    { 'ngrade': 2, 'items': [[1143892, 1], [4310266, 3000], [4001715, 300]], 'select': false },
    { 'ngrade': 3, 'items': [[1143893, 1], [4310266, 4000], [4031227, 1000], [5060048, 10], [4001715, 500]], 'select': false },
    { 'ngrade': 4, 'items': [[1143894, 1], [4031227, 1500], [5060048, 30], [4001715, 1000]], 'select': false },
    { 'ngrade': 5, 'items': [[1143895, 1], [2439988, 1], [2439961, 3], [4031227, 1500], [4001715, 1500]], 'select': false },
    { 'ngrade': 6, 'items': [[1143896, 1], [5060048, 20], [2439961, 5], [4031227, 2500], [4001715, 3000]], 'select': false },
    { 'ngrade': 7, 'items': [[1143897, 1], [5060048, 30], [2439958, 2], [4031227, 3500], [4001715, 4000], [5068306, 2]], 'select': false },
    { 'ngrade': 8, 'items': [[1143898, 1], [5060048, 30], [2439958, 2], [4031227, 4000], [4001715, 5000], [5068306, 3]], 'select': false },
    { 'ngrade': 9, 'items': [[1143899, 1], [5060048, 30], [2439988, 2], [4031227, 5000], [4001715, 6000], [5068306, 5]], 'select': false },
    { 'ngrade': 10, 'items': [[1143900, 1], [1113316, 1], [1122443, 1], [1032330, 1], [1012757, 1], [4031227, 5000], [4310308, 5000], [5068306, 10]], 'select': false },
    { 'ngrade': 11, 'items': [[1143901, 1], [1113308, 1], [2439988, 1], [5068306, 10], [4031227, 5000], [4310308, 10000]], 'select': false },
    { 'ngrade': 12, 'items': [[1143902, 1], [1182294, 1], [2439988, 1], [5068306, 10], [4031227, 10000], [4310308, 15000]], 'select': false },
    { 'ngrade': 13, 'items': [[1143903, 1], [1162079, 1], [2439988, 1], [5068306, 15], [4031227, 15000], [4310308, 20000]], 'select': false },
    { 'ngrade': 14, 'items': [[1143904, 1], [1190565, 1], [2439988, 1], [5068306, 15], [4031227, 20000], [4310308, 25000]], 'select': false },
    { 'ngrade': 15, 'items': [[1143905, 1], [2439959, 1], [2439988, 1], [5068306, 20], [4031227, 25000], [4310308, 30000]], 'select': false }
] // ngrade matches grade below

var grade = [
    [0, "Normal"],
    [1, "VIP Bronze"],
    [2, "VIP Silver"],
    [3, "VIP Gold"],
    [4, "VIP Platinum"],
    [5, "VIP Diamond"],
    [6, "VVIP Classic"],
    [7, "VVIP Premium"],
    [8, "VVIP Luxury"],
    [9, "VVIP Noble"],
    [10, "VVIP Prestige"],
    [11, "MVP Elite"],
    [12, "MVP Prime"],
    [13, "MVP Signature"],
    [14, "MVP CROWN"],
    [15, "MVP Black"]
]

var daily;

// Weekly Reward
var daily_1 = [
    [4310237, 500],//헌트코인
    [4310266, 300],//승급주화
    [5060048, 1]//레드애플

] // Grade D

var daily_2 = [
    [4310237, 1000],//헌트코인
    [4310266, 500],//승급주화
    [5060048, 3]//레드애플
] // Grade C

var daily_3 = [
    [4310237, 1000],//헌트코인
    [4310266, 800],//승급주화
    [5060048, 5]//레드애플
] // Grade B

var daily_4 = [
    [4310237, 1500],//헌트코인
    [4310266, 1000],//승급주화
    [4031227, 50],//찬빛결
    [5060048, 10]//Golden Apple
] // Grade A

var daily_5 = [
    [4310237, 2000],//헌트코인
    [4310266, 1500],//승급주화
    [4031227, 200],//찬빛결
    [5060048, 10]//Golden Apple
] // Grade S

var daily_6 = [
    [4310237, 2500],//헌트코인
    [4310266, 2000],//승급주화
    [4031227, 500],//찬빛결
    [5060048, 10]//Golden Apple
] // Grade SS

var daily_7 = [
    [4310237, 3000],//헌트코인
    [4310266, 2500],//승급주화
    [4031227, 1000],//찬빛결
    [5060048, 10]//Golden Apple
] // Grade SSS

var daily_8 = [
    [4310237, 4000],//헌트코인
    [4310266, 3000],//승급주화
    [4031227, 1000],//찬빛결
    [5060048, 15]//레드애플
] // Grade SSS+

var daily_9 = [
    [4310237, 5000],//헌트코인
    [4310266, 4000],//승급주화
    [4031227, 1500],//찬빛결
    [5060048, 15]//레드애플
] // MVP PLATINUM

var daily_10 = [
    [4310237, 6000],//헌트코인
    [4310266, 5000],//승급주화
    [4031227, 2000],//찬빛결
    [5060048, 20]//Golden Apple
] // MVP LUXURY

var daily_11 = [
    [4310237, 7000],//헌트코인
    [4310266, 6000],//승급주화
    [4031227, 2500],//찬빛결
    [5060048, 20]//Golden Apple
] // MVP NOBLE

var daily_12 = [
    [4310237, 8000],//헌트코인
    [4310266, 7000],//승급주화
    [4031227, 3000],//찬빛결
    [5060048, 20]//Golden Apple
] // MVP CROWN

var daily_13 = [
    [4310237, 10000],//헌트코인
    [4310266, 8000],//승급주화
    [4031227, 4000],//찬빛결
    [5060048, 20]//Golden Apple
] // MVP PRESTIGE

var daily_14 = [
    [4310237, 10000],//헌트코인
    [4310266, 10000],//승급주화
    [4031227, 5000],//찬빛결
    [5060048, 50]//레드애플
] // MVP ROYAL

var daily_15 = [
    [4310237, 10000],//헌트코인
    [4310266, 10000],//승급주화
    [4031227, 6000],//찬빛결
    [5060048, 100]//레드애플
] // MVP ROYAL+

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
        getData();
        if (cm.getClient().getKeyValue("DPointAll") == null)
            cm.getClient().setKeyValue("DPointAll", "0");

        if (cm.inBoss()) {
            cm.getPlayer().dropMessage(5, "ฟังก์ชันนี้ไม่สามารถใช้งานได้ในระหว่างการต่อสู้กับบอส");
            cm.dispose();
            return;
        }

        var ownedCash = comma(cm.getPlayer().getCashPoint());
        var ownedPoint = comma(cm.getPlayer().getDonationPoint());
        var currentGrade = cm.getPlayer().getHgrades();
        var totalCash = comma(cm.getClient().getKeyValue("DPointAll"));

        var msg = "#fs11#" + Black;
        msg += "         " + StarWhite + " ยศปัจจุบันของ #fc0xFFFF3366##h ##fc0xFF000000# : #fc0xFFFF3366#" + currentGrade + Black + Enter;
        msg += "         " + StarWhite + " ยอดเติมเงินสะสม #fc0xFFFF3366##h ##fc0xFF000000# : #fc0xFFFF3366#" + totalCash + "C#b" + Enter + Enter;
        msg += " #fc0xFF000000#　　- Cash ที่มี : #fc0xFFFF3366#" + ownedCash + "C#k" + Black;
        msg += " #fc0xFF000000#   - Points ที่มี : #fc0xFFFF3366#" + ownedPoint + "P#k" + Black + Enter;
        msg += "#l#Cgray#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        msg += "　　　　#b#L22##e[ Cash Shop ]#n#l";
        msg += "      #b#L2##e[ Point Shop ]#n#l" + Enter + Enter;
        msg += "　　　#b#L3#[ Transcendent Arcane Symbol ]#l";
        msg += "  #b#L4#[ Enhance Cosmetic Items ]#l" + Enter;
        //msg += "      #b#L2#- Point Shop -#l" +Enter;

        msg += "#l\r\n#Cgray#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";

        msg += Color + "　　　　#L5#- รับรางวัลระดับยศ -#l" + Enter;
        msg += Color + "　　　　#L50#- รับรางวัลสะสมการเติม Cash -#l #Cgray##L61#(ดูของรางวัล)#l#k" + Enter;
        msg += Color + "　　　　#L6#- รับรางวัลประจำสัปดาห์ -#l #Cgray##L60#(ดูของรางวัล)#l#k" + Enter;
        cm.sendSimple(msg);
    } else if (status == 1) {
        seld = sel;
        switch (sel) {
            case 1:
                cm.sendSimple(getList());
                break;
            case 2:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 1530050, "PointShop");
                break;
            case 3:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 9000178, "TranscendentArcaneSymbol");
                break;
            case 4:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 9000331, "GanglimCashEnhancement");
                break;
            case 5:
                var msg = "#fs11#ระดับยศปัจจุบันของ #b#h ##k คือ #b" + cm.getPlayer().getHgrades() + "#k." + Enter;
                var a = "";
                a += "#L999##rAchievement Reward Preview#k" + Enter + Enter;
                for (i = 0; i < nreward.length; i++) {
                    if (cm.getClient().getKeyValue("nd_" + nreward[i]['ngrade']) == null && cm.getPlayer().getHgrade() >= nreward[i]['ngrade']) {
                        a += "#L" + i + "##b" + grade[nreward[i]['ngrade']][1] + " รางวัลสะสมระดับยศ (รับได้)#k" + Enter;
                    }
                }
                if (a.equals("")) {
                    msg += "ไม่มีรางวัลให้รับในขณะนี้";
                    cm.sendOk(msg);
                    cm.dispose();
                    return;
                }
                cm.sendSimple(msg + a);
                break;

            case 6: // 주간보상
                if (cm.getPlayer().getHgrade() < 1) {
                    cm.sendOk("#fs11#ฟังก์ชันนี้ใช้ได้เฉพาะ MVP Bronze ขึ้นไปเท่านั้น");
                    cm.dispose();
                    return;
                }
                if (cm.getClient().getKeyValue("HgradeWeek") != null) {
                    cm.sendOk("#fs11#สัปดาห์นี้คุณได้รับไปแล้ว");
                    cm.dispose();
                    return;
                }

                time = new Date();
                year = time.getFullYear();
                month = time.getMonth() + 1;
                date2 = time.getDate() < 10 ? "0" + time.getDate() : time.getDate();
                date = year + "" + month + "" + date2;
                daily = cm.getPlayer().getHgrade() == 1 ? daily_1 : cm.getPlayer().getHgrade() == 2 ? daily_2 : cm.getPlayer().getHgrade() == 3 ? daily_3 : cm.getPlayer().getHgrade() == 4 ? daily_4 : cm.getPlayer().getHgrade() == 5 ? daily_5 : cm.getPlayer().getHgrade() == 6 ? daily_6 : cm.getPlayer().getHgrade() == 7 ? daily_7 : cm.getPlayer().getHgrade() == 8 ? daily_8 : cm.getPlayer().getHgrade() == 9 ? daily_9 : cm.getPlayer().getHgrade() == 10 ? daily_10 : cm.getPlayer().getHgrade() == 11 ? daily_11 : cm.getPlayer().getHgrade() == 12 ? daily_12 : cm.getPlayer().getHgrade() == 13 ? daily_13 : cm.getPlayer().getHgrade() == 14 ? daily_14 : cm.getPlayer().getHgrade() == 15 ? daily_15 : [];


                var rlist = "";
                for (a = 0; a < daily.length; a++) {
                    rlist += "#b#i" + daily[a][0] + "##z" + daily[a][0] + "# " + daily[a][1] + " (Qty)" + Enter;
                }
                cm.sendYesNo("#fs11##b รางวัลของระดับ " + cm.getPlayer().getHgrades() + " พร้อมรับแล้ว!#k#r (รีเซ็ตทุกวันจันทร์)#k\r\n\r\n" + rlist + "\r\n\r\n #rคุณต้องการรับรางวัลหรือไม่?");
                break;

            case 22:
                var ownedCashMenu = comma(cm.getPlayer().getCashPoint());
                var ownedPointMenu = comma(cm.getPlayer().getDonationPoint());
                var currentGradeMenu = cm.getPlayer().getHgrades();
                var totalCashMenu = comma(cm.getClient().getKeyValue("DPointAll"));

                var msg = "#fs11#" + Black;
                msg += Star + " Current #fc0xFFFF3366##h ##fc0xFF000000#'s Cash : #fc0xFFFF3366#" + ownedCashMenu + "C#k" + Black + enter;
                msg += "#l\r\n#Cgray#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#" + enter;
                msg += "#L1#- Regular Package -#l" + enter;
                msg += "#L2#- Event Package - #r[Hot !]#k#l" + enter;
                msg += "#L3#- MVP Grade - #r[Hot !]#l" + enter;

                msg += "#l\r\n#Cgray#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
                cm.sendSimple(msg);
                break;

                var con = DBConnection.getConnection();
                var ps = con.prepareStatement("DELETE FROM acckeyvalue WHERE `key` = 'HgradeWeek'");
                ps.executeUpdate();
                ps.close();
                con.close();

                for (var i = 0; i < GameServer.getAllInstances().size(); i++) {
                    var chrs = GameServer.getAllInstances().get(i)
                        .getPlayerStorage().getAllCharacters();
                    for (var a = 0; a < chrs.size(); a++) {
                        chrs.get(a).getClient().removeKeyValue("HgradeWeek");
                    }
                }

                cm.sendOkS("รีเซ็ตเรียบร้อยแล้ว", 2);
                cm.dispose();
                break;

            case 50: // Cumulative Reward
                var msg = Star + " #fs11##fc0xFF000000#Current #fc0xFFFF3366##h ##fc0xFF000000#'s Cumulative Cash : #fc0xFFFF3366#" + cm.getClient().getKeyValue("DPointAll") + "C#fc0xFF000000##b" + Enter;
                msg += Color + "Please select the cumulative reward to receive#b" + Enter;
                msg += "#L1998#<ฉายา All Chat> ตัวละคร" + Enter;
                msg += "#L1999#<ฉายา All Chat> แชร์ทั้งบัญชี" + Enter + Enter;
                msg += "#L10#รางวัลสะสม 100k " + checkDPointAll(10) + Enter;
                msg += "#L20#รางวัลสะสม 200k " + checkDPointAll(20) + Enter;
                msg += "#L30#รางวัลสะสม 300k " + checkDPointAll(30) + Enter;
                msg += "#L50#รางวัลสะสม 500k " + checkDPointAll(50) + Enter;
                msg += "#L75#รางวัลสะสม 750k " + checkDPointAll(75) + Enter;
                msg += "#L100#รางวัลสะสม 1m " + checkDPointAll(100) + Enter;
                msg += "#L125#รางวัลสะสม 1.25m " + checkDPointAll(125) + Enter;
                msg += "#L150#รางวัลสะสม 1.5m " + checkDPointAll(150) + Enter;
                msg += "#L200#รางวัลสะสม 2m " + checkDPointAll(200) + Enter;
                msg += "#L250#รางวัลสะสม 2.5m " + checkDPointAll(250) + Enter;
                msg += "#L300#รางวัลสะสม 3m " + checkDPointAll(300) + Enter;
                msg += "#L350#รางวัลสะสม 3.5m " + checkDPointAll(350) + Enter;
                msg += "#L400#รางวัลสะสม 4m " + checkDPointAll(400) + Enter;
                msg += "#L450#รางวัลสะสม 4.5m " + checkDPointAll(450) + Enter;
                msg += "#L500#รางวัลสะสม 5m " + checkDPointAll(500) + Enter;
                msg += "#L550#รางวัลสะสม 5.5m " + checkDPointAll(550) + Enter;
                msg += "#L600#รางวัลสะสม 6m " + checkDPointAll(600) + Enter;
                msg += "#L650#รางวัลสะสม 6.5m " + checkDPointAll(650) + Enter;
                msg += "#L700#รางวัลสะสม 7m " + checkDPointAll(700) + Enter;
                msg += "#L750#รางวัลสะสม 7.5m " + checkDPointAll(750) + Enter;
                msg += "#L800#รางวัลสะสม 8m " + checkDPointAll(800) + Enter;
                msg += "#L850#รางวัลสะสม 8.5m " + checkDPointAll(850) + Enter;
                msg += "#L900#รางวัลสะสม 9m " + checkDPointAll(900) + Enter;
                msg += "#L950#รางวัลสะสม 9.5m " + checkDPointAll(950) + Enter;
                msg += "#L1000#รางวัลสะสม 10m " + checkDPointAll(1000) + Enter;
                msg += "#L1050#รางวัลสะสม 10.5m " + checkDPointAll(1050) + Enter;
                msg += "#L1100#รางวัลสะสม 11m " + checkDPointAll(1100) + Enter;
                msg += "#L1150#รางวัลสะสม 11.5m " + checkDPointAll(1150) + Enter;
                msg += "#L1200#รางวัลสะสม 12m " + checkDPointAll(1200) + Enter;
                msg += "#L1250#รางวัลสะสม 12.5m " + checkDPointAll(1250) + Enter;
                msg += "#L1300#รางวัลสะสม 13m " + checkDPointAll(1300) + Enter;
                msg += "#L1350#รางวัลสะสม 13.5m " + checkDPointAll(1350) + Enter;
                msg += "#L1400#รางวัลสะสม 14m " + checkDPointAll(1400) + Enter;
                msg += "#L1450#รางวัลสะสม 14.5m " + checkDPointAll(1450) + Enter;
                msg += "#L1500#รางวัลสะสม 15m " + checkDPointAll(1500) + Enter;
                msg += "#L1550#รางวัลสะสม 15.5m " + checkDPointAll(1550) + Enter;
                msg += "#L1600#รางวัลสะสม 16m " + checkDPointAll(1600) + Enter;
                msg += "#L1650#รางวัลสะสม 16.5m " + checkDPointAll(1650) + Enter;
                msg += "#L1700#รางวัลสะสม 17m " + checkDPointAll(1700) + Enter;
                msg += "#L1750#รางวัลสะสม 17.5m " + checkDPointAll(1750) + Enter;
                msg += "#L1800#รางวัลสะสม 18m " + checkDPointAll(1800) + Enter;
                msg += "#L1850#รางวัลสะสม 18.5m " + checkDPointAll(1850) + Enter;
                msg += "#L1900#รางวัลสะสม 19m " + checkDPointAll(1900) + Enter;
                msg += "#L1950#รางวัลสะสม 19.5m " + checkDPointAll(1950) + Enter;
                msg += "#L2000#รางวัลสะสม 20m " + checkDPointAll(2000) + Enter;
                msg += "#L2100#รางวัลสะสม 21m " + checkDPointAll(2100) + Enter;
                msg += "#L2200#รางวัลสะสม 22m " + checkDPointAll(2200) + Enter;
                msg += "#L2300#รางวัลสะสม 23m " + checkDPointAll(2300) + Enter;
                msg += "#L2400#รางวัลสะสม 24m " + checkDPointAll(2400) + Enter;
                msg += "#L2500#รางวัลสะสม 25m " + checkDPointAll(2500) + Enter;
                msg += "#L2600#รางวัลสะสม 26m " + checkDPointAll(2600) + Enter;
                msg += "#L2700#รางวัลสะสม 27m " + checkDPointAll(2700) + Enter;
                msg += "#L2800#รางวัลสะสม 28m " + checkDPointAll(2800) + Enter;
                msg += "#L2900#รางวัลสะสม 29m " + checkDPointAll(2900) + Enter;
                msg += "#L3000#รางวัลสะสม 30m " + checkDPointAll(3000) + Enter;
                cm.sendSimple(msg);
                break;

            case 60:
                var allDaily = [
                    [],          // 인덱스 0 패스
                    daily_1,     // VIP Bronze
                    daily_2,     // VIP Silver
                    daily_3,     // VIP Gold
                    daily_4,     // VIP Platinum
                    daily_5,     // VIP Diamond
                    daily_6,     // VVIP Classic
                    daily_7,     // VVIP Premium
                    daily_8,     // VVIP Luxury
                    daily_9,     // VVIP Noble
                    daily_10,    // VVIP Prestige
                    daily_11,    // MVP Elite
                    daily_12,    // MVP Prime
                    daily_13,    // MVP Signature
                    daily_14,    // MVP CROWN
                    daily_15     // MVP Black
                ];

                var previewMsg = "#fs11##eดูของรางวัลประจำสัปดาห์ทั้งหมด (รีเซ็ตทุกวันจันทร์)#n\r\n\r\n";

                for (var i = 1; i < allDaily.length; i++) {
                    var list = allDaily[i];
                    previewMsg += "#fs11##e#r- " + grade[i][1] + " -#k#n#b#e\r\n";
                    for (var j = 0; j < list.length; j++) {
                        previewMsg += "#i" + list[j][0] + "# #z" + list[j][0] + "# " + list[j][1] + " (Qty)\r\n";
                    }
                    previewMsg += "\r\n";
                }

                cm.sendOk(previewMsg);
                cm.dispose();
                break;

            case 61:
                var msg = "#fs11##eดูของรางวัลสะสม Cash#n\r\n\r\n";

                var previewConfig = {
                    10: { spins: 2, berries: 0 },
                    20: { spins: 2, berries: 0 },
                    30: { spins: 3, berries: 0 },
                    50: { spins: 4, berries: 0 },
                    75: { spins: 4, berries: 0 },
                    100: { spins: 5, berries: 1 },
                    125: { spins: 5, berries: 1 },
                    150: { spins: 5, berries: 1 },
                    200: { spins: 7, berries: 2 },
                    250: { spins: 7, berries: 2 },
                    300: { spins: 10, berries: 3 },
                    350: { spins: 10, berries: 3 },
                    400: { spins: 10, berries: 3 },
                    450: { spins: 10, berries: 3 },
                    500: { spins: 10, berries: 3 },
                    550: { spins: 10, berries: 3 },
                    600: { spins: 15, berries: 3 },
                    650: { spins: 15, berries: 3 },
                    700: { spins: 15, berries: 3 },
                    750: { spins: 15, berries: 3 },
                    800: { spins: 15, berries: 3 },
                    850: { spins: 15, berries: 3 },
                    900: { spins: 15, berries: 3 },
                    950: { spins: 15, berries: 3 },
                    1000: { spins: 17, berries: 4 },
                    1050: { spins: 17, berries: 4 },
                    1100: { spins: 17, berries: 4 },
                    1150: { spins: 17, berries: 4 },
                    1200: { spins: 17, berries: 4 },
                    1250: { spins: 17, berries: 4 },
                    1300: { spins: 17, berries: 4 },
                    1350: { spins: 17, berries: 4 },
                    1400: { spins: 17, berries: 4 },
                    1450: { spins: 17, berries: 4 },
                    1500: { spins: 20, berries: 5 },
                    1550: { spins: 20, berries: 5 },
                    1600: { spins: 20, berries: 5 },
                    1650: { spins: 20, berries: 5 },
                    1700: { spins: 20, berries: 5 },
                    1750: { spins: 20, berries: 5 },
                    1800: { spins: 20, berries: 5 },
                    1850: { spins: 20, berries: 5 },
                    1900: { spins: 20, berries: 5 },
                    1950: { spins: 20, berries: 5 },
                    2000: { spins: 25, berries: 7 },
                    2100: { spins: 25, berries: 7 },
                    2200: { spins: 25, berries: 7 },
                    2300: { spins: 25, berries: 7 },
                    2400: { spins: 25, berries: 7 },
                    2500: { spins: 25, berries: 8 },
                    2600: { spins: 30, berries: 8 },
                    2700: { spins: 30, berries: 8 },
                    2800: { spins: 30, berries: 8 },
                    2900: { spins: 30, berries: 8 },
                    3000: { spins: 50, berries: 10 }
                };
                // 출력
                for (var tier in previewConfig) {
                    var cfg = previewConfig[tier];
                    msg += "#fs11##b- " + tier + "0k สะสม#k :  #i4036661# " + cfg.spins + " ชิ้น";
                    if (cfg.berries > 0) msg += ",  #i5068306# " + cfg.berries + " pcs";
                    msg += "\r\n";
                }
                cm.sendOk(msg);
                cm.dispose();
                break;

            case 222:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 2460021, "2460021");
                break;
        }
    } else if (status == 2) {
        switch (seld) {
            case 1:
                getList2(getsex(sel));
                break;

            case 5:
                seld2 = sel;

                if (seld2 == 999) { // Achievement Reward Preview
                    var msg = "#fs11#" + Color + "รางวัลที่ได้รับเมื่อถึงระดับ MVP" + Enter + Enter;
                    for (i = 0; i < nreward.length; i++) {
                        msg += "#r#e- " + grade[1 + i][1] + " -" + Enter;
                        for (ii = 0; ii < nreward[i]['items'].length; ii++) {
                            msg += "#b#e#i" + nreward[i]['items'][ii][0] + "# #z" + nreward[i]['items'][ii][0] + "# " + nreward[i]['items'][ii][1] + " (Qty) \r\n";
                        }
                        msg += Enter;
                    }
                    cm.sendOk(msg);
                    cm.dispose();
                    return;
                }

                if (cm.getInvSlots(1) < 3 || cm.getInvSlots(2) < 3 || cm.getInvSlots(3) < 3 || cm.getInvSlots(4) < 3 || cm.getInvSlots(5) < 3) {
                    cm.sendOkS("#fs11##fc0xFF6600CC#กรุณาเว้นช่องว่างในช่องเก็บของอย่างน้อย 3 ช่อง", 2);
                    cm.dispose();
                    return;
                }

                if (cm.getClient().getKeyValue("nd_" + nreward[seld2]['ngrade']) != null || cm.getPlayer().getHgrade() < nreward[seld2]['ngrade']) {
                    cm.sendOk("r");
                    cm.dispose();
                    return;
                }

                if (!nreward[seld2]['select']) {
                    var msg = "#b#e[ รางวัลความสำเร็จ ] #k#n\r\nคุณต้องการรับ #bรางวัล#k ต่อไปนี้หรือไม่?\r\n" + Enter + Enter;
                    for (i = 0; i < nreward[seld2]['items'].length; i++) {
                        //cm.gainItem(nreward[seld2]['items'][i][0], nreward[seld2]['items'][i][1]);
                        msg += "#b#e#i" + nreward[seld2]['items'][i][0] + "# #z" + nreward[seld2]['items'][i][0] + "# " + nreward[seld2]['items'][i][1] + " (Qty) \r\n";
                    }
                    //msg += "#L1#I will receive it.";
                    //msg += "#L2#Next time";
                    cm.sendYesNo(msg);
                } else {
                    var msg = "รางวัลนี้เป็นรางวัลที่เลือกได้ กรุณาเลือกรางวัลที่คุณต้องการ#b" + Enter;
                    for (i = 0; i < nreward[seld2]['items'].length; i++)
                        msg += "#L" + i + "##i" + nreward[seld2]['items'][i][0] + "##z" + nreward[seld2]['items'][i][0] + "# " + nreward[seld2]['items'][i][1] + " (Qty)" + Enter;
                    cm.sendSimple(msg);
                }
                break;

            case 6:
                if (cm.getClient().getKeyValue("HgradeWeek") == null) {
                    for (a = 0; a < daily.length; a++) {
                        if (!cm.canHold(daily[a][0], daily[a][1])) {
                            cm.sendOk("ช่องเก็บของไม่เพียงพอ");
                            cm.dispose();
                            return;
                        }
                    }
                    for (a = 0; a < daily.length; a++) {
                        cm.gainItem(daily[a][0], daily[a][1]);
                    }
                    cm.getClient().setKeyValue("HgradeWeek", "1");
                    //Packages.scripting.NPCConversationManager.writeLog("TextLog/zenia/[MVP주간보상].log", "\r\n계정 : " + cm.getClient().getAccountName() + " (" + cm.getClient().getAccID() + ")\r\n닉네임 : " + cm.getPlayer().getName() + "\r\n등급 : " + cm.getPlayer().getHgrades() + "\r\n\r\n", true);
                    cm.addCustomLog(99, "[Weekly Reward] Grade : " + cm.getPlayer().getHgrades() + "");
                    cm.sendOk("#fs11#แจกจ่ายรางวัลเสร็จสมบูรณ์");
                    cm.dispose();
                }
                break;

            case 22:
                seld22 = sel;
                switch (seld22) {
                    case 1:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 1530050, "PermanentPackageShop");
                        break;
                    case 2:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 1530050, "EventPackageShop");
                        break;
                    case 3:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 9000213, "GradeShop");
                        break;
                }
                break;

            case 50:  // 누적 캐시보상
                seld50 = sel;
                // 보상 설정: 기준(만 단위) -> {spins: 스핀 수, berries: 베리 수}
                var rewardConfig = {
                    10: { spins: 2, berries: 0 },
                    20: { spins: 2, berries: 0 },
                    30: { spins: 3, berries: 0 },
                    50: { spins: 4, berries: 0 },
                    75: { spins: 4, berries: 0 },
                    100: { spins: 5, berries: 1 },
                    125: { spins: 5, berries: 1 },
                    150: { spins: 5, berries: 1 },
                    200: { spins: 7, berries: 2 },
                    250: { spins: 7, berries: 2 },
                    300: { spins: 10, berries: 3 },
                    350: { spins: 10, berries: 3 },
                    400: { spins: 10, berries: 3 },
                    450: { spins: 10, berries: 3 },
                    500: { spins: 10, berries: 3 },
                    550: { spins: 10, berries: 3 },
                    600: { spins: 15, berries: 3 },
                    650: { spins: 15, berries: 3 },
                    700: { spins: 15, berries: 3 },
                    750: { spins: 15, berries: 3 },
                    800: { spins: 15, berries: 3 },
                    850: { spins: 15, berries: 3 },
                    900: { spins: 15, berries: 3 },
                    950: { spins: 15, berries: 3 },
                    1000: { spins: 17, berries: 4 },
                    1050: { spins: 17, berries: 4 },
                    1100: { spins: 17, berries: 4 },
                    1150: { spins: 17, berries: 4 },
                    1200: { spins: 17, berries: 4 },
                    1250: { spins: 17, berries: 4 },
                    1300: { spins: 17, berries: 4 },
                    1350: { spins: 17, berries: 4 },
                    1400: { spins: 17, berries: 4 },
                    1450: { spins: 17, berries: 4 },
                    1500: { spins: 20, berries: 5 },
                    1550: { spins: 20, berries: 5 },
                    1600: { spins: 20, berries: 5 },
                    1650: { spins: 20, berries: 5 },
                    1700: { spins: 20, berries: 5 },
                    1750: { spins: 20, berries: 5 },
                    1800: { spins: 20, berries: 5 },
                    1850: { spins: 20, berries: 5 },
                    1900: { spins: 20, berries: 5 },
                    1950: { spins: 20, berries: 5 },
                    2000: { spins: 25, berries: 7 },
                    2100: { spins: 25, berries: 7 },
                    2200: { spins: 25, berries: 7 },
                    2300: { spins: 25, berries: 7 },
                    2400: { spins: 25, berries: 7 },
                    2500: { spins: 25, berries: 8 },
                    2600: { spins: 30, berries: 8 },
                    2700: { spins: 30, berries: 8 },
                    2800: { spins: 30, berries: 8 },
                    2900: { spins: 30, berries: 8 },
                    3000: { spins: 50, berries: 10 }
                };

                // 1998/1999는 전체채팅 칭호 설정, 나머지는 누적 보상 로직으로
                switch (seld50) {
                    case 1998:
                    case 1999:
                        var chatConfig = {
                            1998: { required: 5000000, scope: 'ตัวละคร', note: '※ สามารถตั้งค่าได้เพียงครั้งเดียวหากยอดสะสมต่ำกว่า 5 ล้าน' },
                            1999: { required: 10000000, scope: 'บัญชี', note: '' }
                        };
                        var cc = chatConfig[seld50];
                        if (cm.getPlayer().getDPointAll() >= cc.required) {
                            var msg = "#fs11#";
                            msg += Color + "< ลำดับการแสดงฉายา All Chat >\r\n";
                            msg += Black + "ฉายาตัวละคร → ฉายาบัญชี → ยศหลัก\r\n";
                            if (seld50 === 1998) msg += "#r" + cc.note + "#k\r\n\r\n";
                            msg += "กรุณาใส่ฉายา " + cc.scope + " เพื่อดำเนินการ";
                            cm.sendGetText(msg);
                        } else {
                            cm.sendOk("#fs11#ยอดสะสมไม่เพียงพอ");
                            cm.dispose();
                        }
                        break;

                    default:
                        var threshold = seld50;
                        var requiredCash = threshold * 10000;
                        var key = "DPointAll_" + threshold;
                        var userTotal = cm.getPlayer().getDPointAll();
                        var cfg = rewardConfig[threshold];

                        if (!cfg) {
                            cm.sendOk("#fs11#ไอเท็มรางวัลไม่ถูกต้อง");
                            cm.dispose();
                            break;
                        }

                        var already = Number(cm.getClient().getKeyValue(key) || "0") >= 1;
                        if (userTotal >= requiredCash && !already) {
                            cm.getClient().setKeyValue(key, "1");
                            cm.gainItem(4036661, cfg.spins);
                            if (cfg.berries > 0) cm.gainItem(5068306, cfg.berries);
                            cm.sendOk("#fs11#แจกจ่ายรางวัลเรียบร้อยแล้ว");
                        } else {
                            cm.sendOk("#fs11#รับไปแล้ว หรือยอดสะสมไม่เพียงพอ");
                        }
                        cm.dispose();
                        break;
                }
                break;
        }
        if (S1 == 223) {
            if (cm.getClient().getKeyValue("tradepoint") != null) {
                tradePoint = Number(cm.getClient().getKeyValue("tradepoint"));
            } else {
                tradePoint = 0
            }
            if (tradePoint >= tradePoint + sel) {
                cm.sendOk("สามารถเติมพอยท์เข้าบัญชีได้สูงสุด 3 ล้าน");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getCashPoint() < sel) {
                cm.sendOk("คุณใส่จำนวนมากกว่า Cash ที่คุณมี");
                cm.dispose();
                return;
            }
            if (0 > sel) {
                cm.sendOk("เกิดข้อผิดพลาด");
                cm.dispose();
                return;
            }
            cm.getPlayer().gainCashPoint(-sel);
            cm.getPlayer().gainDonationPoint(sel);

            cm.getClient().setKeyValue("tradepoint", "" + (tradePoint + sel));
            cm.sendOk(comma(sel) + "แลกเปลี่ยนสำเร็จ");
            cm.dispose();
            return;
        }
    } else if (status == 3) {
        switch (seld) {
            case 1:
                seldreward = sel;
                reward = getQ(seldreward);
                cm.sendYesNo("คุณต้องการรับรางวัลนี้จริงๆ หรือ?\r\nคุณจะได้รับ #b" + reward + " Donation Points#k.");
                break;

            case 4:
                seld2 = sel;
                switch (seld3) {
                    case 1:
                        getList2Admin(seld2);
                        break;
                }
                break;

            case 5:
                if (!nreward[seld2]['select']) {
                    for (i = 0; i < nreward[seld2]['items'].length; i++) {
                        cm.gainItem(nreward[seld2]['items'][i][0], nreward[seld2]['items'][i][1]);
                    }
                    cm.getClient().setKeyValue("nd_" + nreward[seld2]['ngrade'], "1");
                    cm.sendOkS("#fs11##b#eขอบคุณมาก~!", 2);
                    cm.dispose();
                    return;
                }
                break;

            case 50:
                switch (seld50) {
                    case 1998:
                        SetMedalString = cm.getText();
                        Get500MedalString = cm.getClient().getKeyValue("500MedalString");
                        if (cm.getClient().getKeyValue("DPointAll") >= 10000000) {
                            cm.getClient().getPlayer().setKeyValue("MedalString", SetMedalString);
                            cm.sendOkS("#fs11##b#eขอบคุณมาก~!", 2);
                            cm.dispose();
                            return;
                        } else if (SetMedalString != null && Get500MedalString == null) {
                            cm.getClient().setKeyValue("500MedalString", "1");
                            cm.getClient().getPlayer().setKeyValue("MedalString", SetMedalString);
                            cm.sendOkS("#fs11##b#eขอบคุณมาก~!", 2);
                            cm.dispose();
                            return;
                        } else {
                            cm.sendOkS("#fs11##b#eอ้อ ใช่ ฉันตั้งค่าไปแล้วนี่นา..", 2);
                            cm.dispose();
                            return;
                        }
                        break;

                    case 1999:
                        SetMedalString = cm.getText();
                        if (SetMedalString != null) {
                            cm.getClient().setKeyValue("MedalString", SetMedalString);
                            cm.sendOkS("#fs11##b#eขอบคุณมาก~!", 2);
                            cm.dispose();
                            return;
                        }
                        break;
                }
        }
    } else if (status == 4) {
        switch (seld) {
            case 1:
                getReward(seldreward);
                cm.getPlayer().gainDonationPoint(reward);
                cm.sendOk("รับรางวัลเรียบร้อยแล้ว");
                cm.dispose();
                break;

            case 4:
                switch (seld3) {
                    case 1:
                        DeleteReward(sel);
                        cm.sendOk("ลบเรียบร้อยแล้ว");
                        cm.dispose();
                        break;
                }
                break;

            case 5:
                cm.gainItem(nreward[seld2]['items'][seld3][0], nreward[seld2]['items'][seld3][1]);

                cm.getClient().setKeyValue("nd_" + nreward[seld2]['ngrade'], "1");
                cm.sendOk("#fs11#แจกจ่ายเรียบร้อยแล้ว");
                cm.dispose();
                break;
        }
    } else if (status == 5) {
        switch (seld) {
            case 4:
                switch (seld3) {
                    case 2:
                        if (modifychr != null) {
                            modifychr.setHgrade(seldgrade);
                        } else {
                            var con = DBConnection.getConnection();
                            asdid = getAccId(modify);
                            var ps = con.prepareStatement("UPDATE acckeyvalue SET `value` = ? WHERE `id` = ? and `key` = ?");
                            ps.setString(1, seldgrade + "");
                            ps.setInt(2, asdid);
                            ps.setString(3, "hGrade");
                            ps.executeUpdate();
                            ps.close();
                            con.close();
                        }
                        cm.sendOk("เปลี่ยนแปลงเรียบร้อยแล้ว");
                        cm.dispose();
                        break;
                }
                break;
        }
    }
}


function getData() {
    time = new Date();
    year = time.getFullYear();
    month = time.getMonth() + 1;
    if (month < 10) {
        month = "0" + month;
    }
    date2 = time.getDate() < 10 ? "0" + time.getDate() : time.getDate();
    date = year + "." + month + "." + date2;
    day = time.getDay();
}

function getList() {
    var ret = "";
    var names = [];

    var con = DBConnection.getConnection();
    var ps = con.prepareStatement("SELECT * FROM donation WHERE `check` = 0");
    var rs = ps.executeQuery();

    while (rs.next()) {
        if (rs.getInt("cid") == cm.getPlayer().getId() || rs.getString("name").equals(cm.getPlayer().getName())) {
            ret += "#L" + rs.getInt("id") + "##b" + rs.getString("name") + " (รับได้)\r\n";
            break;
        }
    }
    rs.close();
    ps.close();
    con.close();

    if (ret.equals("")) return "#fs11#ไม่มีรางวัลโดเนทให้รับในขณะนี้";
    return ret;
}
function getQ(id) {
    var ret = 0;
    var con = DBConnection.getConnection();
    var ps = con.prepareStatement("SELECT * FROM donation WHERE `check` = 0 AND `id` = ? AND (`name` = ? or `cid` = ?)");
    ps.setInt(1, id);
    ps.setString(2, cm.getPlayer().getName());
    ps.setInt(3, cm.getPlayer().getId());
    var rs = ps.executeQuery();
    while (rs.next()) {
        ret += rs.getInt("sum");
    }
    rs.close();
    ps.close();
    con.close();
    if (ret.equals("")) return "ไม่พบรางวัลโดเนท";
    return ret;
}

function getReward(id) {
    var con = DBConnection.getConnection();
    var ps = con.prepareStatement("UPDATE donation SET `check` = 1 WHERE `id` = ? AND (`name` = ? or `cid` = ?)");
    ps.setInt(1, id);
    ps.setString(2, cm.getPlayer().getName());
    ps.setInt(3, cm.getPlayer().getId());
    ps.executeUpdate();
    ps.close();
    con.close();
}

function comma(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}

function checkDPointAll(cash) {
    price = cash * 10000;
    keyvalue = "DPointAll_" + cash + ""
    if (cm.getPlayer().getDPointAll() >= price) {
        if (cm.getClient().getKeyValue(keyvalue) < 1) {
            return "#b(รับได้)#b";
        }
        return "#fc0xFF9A9A9A#(รับแล้ว)#b";
    } else {
        return "#r(ยอดสะสมไม่พอ)#b";
    }
}