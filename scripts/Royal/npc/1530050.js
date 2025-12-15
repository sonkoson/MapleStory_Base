// 후원시스템
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

// 주간보상
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
            cm.getPlayer().dropMessage(5, "This feature cannot be used during boss battles.");
            cm.dispose();
            return;
        }

        var ownedCash = comma(cm.getPlayer().getCashPoint());
        var ownedPoint = comma(cm.getPlayer().getDonationPoint());
        var currentGrade = cm.getPlayer().getHgrades();
        var totalCash = comma(cm.getClient().getKeyValue("DPointAll"));

        var msg = "#fs11#" + Black;
        msg += "         " + StarWhite + " Current #fc0xFFFF3366##h ##fc0xFF000000#'s Grade : #fc0xFFFF3366#" + currentGrade + Black + Enter;
        msg += "         " + StarWhite + " Current #fc0xFFFF3366##h ##fc0xFF000000#'s Total Cash : #fc0xFFFF3366#" + totalCash + "C#b" + Enter + Enter;
        msg += " #fc0xFF000000#　　- Owned Cash : #fc0xFFFF3366#" + ownedCash + "C#k" + Black;
        msg += " #fc0xFF000000#   - Owned Points : #fc0xFFFF3366#" + ownedPoint + "P#k" + Black + Enter;
        msg += "#l#Cgray#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";
        msg += "　　　　#b#L22##e[ Cash Shop ]#n#l";
        msg += "      #b#L2##e[ Point Shop ]#n#l" + Enter + Enter;
        msg += "　　　#b#L3#[ Transcendent Arcane Symbol ]#l";
        msg += "  #b#L4#[ Enhance Cosmetic Items ]#l" + Enter;
        //msg += "      #b#L2#- Point Shop -#l" +Enter;

        msg += "#l\r\n#Cgray#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#";

        msg += Color + "　　　　#L5#- Receive Grade Achievement Reward -#l" + Enter;
        msg += Color + "　　　　#L50#- Receive Cumulative Cash Reward -#l #Cgray##L61#(View Rewards)#l#k" + Enter;
        msg += Color + "　　　　#L6#- Receive Grade Weekly Reward -#l #Cgray##L60#(View Rewards)#l#k" + Enter;
        cm.sendSimple(msg);
    } else if (status == 1) {
        seld = sel;
        switch (sel) {
            case 1:
                cm.sendSimple(getList());
                break;
            case 2:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 1530050, "포인트상점");
                break;
            case 3:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 9000178, "초월아케인심볼");
                break;
            case 4:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 9000331, "강림캐시강화");
                break;
            case 5:
                var msg = "#fs11#Current #b#h ##k's grade is #b" + cm.getPlayer().getHgrades() + "#k." + Enter;
                var a = "";
                a += "#L999##rAchievement Reward Preview#k" + Enter + Enter;
                for (i = 0; i < nreward.length; i++) {
                    if (cm.getClient().getKeyValue("nd_" + nreward[i]['ngrade']) == null && cm.getPlayer().getHgrade() >= nreward[i]['ngrade']) {
                        a += "#L" + i + "##b" + grade[nreward[i]['ngrade']][1] + " Cumulative Reward (Available)#k" + Enter;
                    }
                }
                if (a.equals("")) {
                    msg += "There are no rewards available to collect.";
                    cm.sendOk(msg);
                    cm.dispose();
                    return;
                }
                cm.sendSimple(msg + a);
                break;

            case 6: // 주간보상
                if (cm.getPlayer().getHgrade() < 1) {
                    cm.sendOk("#fs11#MVP 브론즈 이상만 사용할 수 있는 기능입니다.");
                    cm.dispose();
                    return;
                }
                if (cm.getClient().getKeyValue("HgradeWeek") != null) {
                    cm.sendOk("#fs11#이번 주는 이미 수령하셨습니다.");
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
                cm.sendYesNo("#fs11##b " + cm.getPlayer().getHgrades() + " Reward Available!#k#r (Reset every Monday)#k\r\n\r\n" + rlist + "\r\n\r\n #rDo you want to receive it?");
                break;

            case 22:
                var 보유캐시 = comma(cm.getPlayer().getCashPoint());
                var 보유포인트 = comma(cm.getPlayer().getDonationPoint());
                var 현재등급 = cm.getPlayer().getHgrades();
                var 누적캐시 = comma(cm.getClient().getKeyValue("DPointAll"));

                var msg = "#fs11#" + 검은색;
                msg += 별 + " 현재 #fc0xFFFF3366##h ##fc0xFF000000# 님의 캐시 : #fc0xFFFF3366#" + 보유캐시 + "C#k" + 검은색 + enter;
                msg += "#l\r\n#Cgray#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#" + enter;
                msg += "#L1#- 상시 패키지 -#l" + enter;
                msg += "#L2#- 이벤트 패키지 - #r[Hot !]#k#l" + enter;
                msg += "#L3#- MVP 등급 - #r[Hot !]#l" + enter;

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

                cm.sendOkS("Reset Complete", 2);
                cm.dispose();
                break;

            case 50: // Cumulative Reward
                var msg = Star + " #fs11##fc0xFF000000#Current #fc0xFFFF3366##h ##fc0xFF000000#'s Cumulative Cash : #fc0xFFFF3366#" + cm.getClient().getKeyValue("DPointAll") + "C#fc0xFF000000##b" + Enter;
                msg += Color + "Please select the cumulative reward to receive#b" + Enter;
                msg += "#L1998#<All Chat Title> Character" + Enter;
                msg += "#L1999#<All Chat Title> Account Shared" + Enter + Enter;
                msg += "#L10#100k Cumulative Reward  " + checkDPointAll(10) + Enter;
                msg += "#L20#200k Cumulative Reward  " + checkDPointAll(20) + Enter;
                msg += "#L30#300k Cumulative Reward  " + checkDPointAll(30) + Enter;
                msg += "#L50#500k Cumulative Reward  " + checkDPointAll(50) + Enter;
                msg += "#L75#750k Cumulative Reward  " + checkDPointAll(75) + Enter;
                msg += "#L100#1m Cumulative Reward  " + checkDPointAll(100) + Enter;
                msg += "#L125#1.25m Cumulative Reward  " + checkDPointAll(125) + Enter;
                msg += "#L150#1.5m Cumulative Reward  " + checkDPointAll(150) + Enter;
                msg += "#L200#2m Cumulative Reward  " + checkDPointAll(200) + Enter;
                msg += "#L250#2.5m Cumulative Reward  " + checkDPointAll(250) + Enter;
                msg += "#L300#3m Cumulative Reward  " + checkDPointAll(300) + Enter;
                msg += "#L350#3.5m Cumulative Reward  " + checkDPointAll(350) + Enter;
                msg += "#L400#4m Cumulative Reward  " + checkDPointAll(400) + Enter;
                msg += "#L450#4.5m Cumulative Reward  " + checkDPointAll(450) + Enter;
                msg += "#L500#5m Cumulative Reward  " + checkDPointAll(500) + Enter;
                msg += "#L550#5.5m Cumulative Reward  " + checkDPointAll(550) + Enter;
                msg += "#L600#6m Cumulative Reward  " + checkDPointAll(600) + Enter;
                msg += "#L650#6.5m Cumulative Reward  " + checkDPointAll(650) + Enter;
                msg += "#L700#7m Cumulative Reward  " + checkDPointAll(700) + Enter;
                msg += "#L750#7.5m Cumulative Reward  " + checkDPointAll(750) + Enter;
                msg += "#L800#8m Cumulative Reward  " + checkDPointAll(800) + Enter;
                msg += "#L850#8.5m Cumulative Reward  " + checkDPointAll(850) + Enter;
                msg += "#L900#9m Cumulative Reward  " + checkDPointAll(900) + Enter;
                msg += "#L950#9.5m Cumulative Reward  " + checkDPointAll(950) + Enter;
                msg += "#L1000#10m Cumulative Reward  " + checkDPointAll(1000) + Enter;
                msg += "#L1050#10.5m Cumulative Reward  " + checkDPointAll(1050) + Enter;
                msg += "#L1100#11m Cumulative Reward  " + checkDPointAll(1100) + Enter;
                msg += "#L1150#11.5m Cumulative Reward  " + checkDPointAll(1150) + Enter;
                msg += "#L1200#12m Cumulative Reward  " + checkDPointAll(1200) + Enter;
                msg += "#L1250#12.5m Cumulative Reward  " + checkDPointAll(1250) + Enter;
                msg += "#L1300#13m Cumulative Reward  " + checkDPointAll(1300) + Enter;
                msg += "#L1350#13.5m Cumulative Reward  " + checkDPointAll(1350) + Enter;
                msg += "#L1400#14m Cumulative Reward  " + checkDPointAll(1400) + Enter;
                msg += "#L1450#14.5m Cumulative Reward  " + checkDPointAll(1450) + Enter;
                msg += "#L1500#15m Cumulative Reward  " + checkDPointAll(1500) + Enter;
                msg += "#L1550#15.5m Cumulative Reward  " + checkDPointAll(1550) + Enter;
                msg += "#L1600#16m Cumulative Reward  " + checkDPointAll(1600) + Enter;
                msg += "#L1650#16.5m Cumulative Reward  " + checkDPointAll(1650) + Enter;
                msg += "#L1700#17m Cumulative Reward  " + checkDPointAll(1700) + Enter;
                msg += "#L1750#17.5m Cumulative Reward  " + checkDPointAll(1750) + Enter;
                msg += "#L1800#18m Cumulative Reward  " + checkDPointAll(1800) + Enter;
                msg += "#L1850#18.5m Cumulative Reward  " + checkDPointAll(1850) + Enter;
                msg += "#L1900#19m Cumulative Reward  " + checkDPointAll(1900) + Enter;
                msg += "#L1950#19.5m Cumulative Reward  " + checkDPointAll(1950) + Enter;
                msg += "#L2000#20m Cumulative Reward  " + checkDPointAll(2000) + Enter;
                msg += "#L2100#21m Cumulative Reward  " + checkDPointAll(2100) + Enter;
                msg += "#L2200#22m Cumulative Reward  " + checkDPointAll(2200) + Enter;
                msg += "#L2300#23m Cumulative Reward  " + checkDPointAll(2300) + Enter;
                msg += "#L2400#24m Cumulative Reward  " + checkDPointAll(2400) + Enter;
                msg += "#L2500#25m Cumulative Reward  " + checkDPointAll(2500) + Enter;
                msg += "#L2600#26m Cumulative Reward  " + checkDPointAll(2600) + Enter;
                msg += "#L2700#27m Cumulative Reward  " + checkDPointAll(2700) + Enter;
                msg += "#L2800#28m Cumulative Reward  " + checkDPointAll(2800) + Enter;
                msg += "#L2900#29m Cumulative Reward  " + checkDPointAll(2900) + Enter;
                msg += "#L3000#30m Cumulative Reward  " + checkDPointAll(3000) + Enter;
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

                var previewMsg = "#fs11##eWeekly Reward Full Preview (Resets every Monday)#n\r\n\r\n";

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
                var msg = "#fs11##e누적 캐시보상 미리보기#n\r\n\r\n";

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
                    msg += "#fs11##b- " + tier + "만 누적#k :  #i4036661# " + cfg.spins + "개";
                    if (cfg.berries > 0) msg += ",  #i5068306# " + cfg.berries + "개";
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
                    var msg = "#fs11#" + Color + "Rewards available when achieving MVP Grade" + Enter + Enter;
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
                    cm.sendOkS("#fs11##fc0xFF6600CC#Please keep at least 3 inventory slots empty per tab.", 2);
                    cm.dispose();
                    return;
                }

                if (cm.getClient().getKeyValue("nd_" + nreward[seld2]['ngrade']) != null || cm.getPlayer().getHgrade() < nreward[seld2]['ngrade']) {
                    cm.sendOk("r");
                    cm.dispose();
                    return;
                }

                if (!nreward[seld2]['select']) {
                    var msg = "#b#e[ Achievement Reward ] #k#n\r\nDo you want to receive the following #bRewards#k?\r\n" + Enter + Enter;
                    for (i = 0; i < nreward[seld2]['items'].length; i++) {
                        //cm.gainItem(nreward[seld2]['items'][i][0], nreward[seld2]['items'][i][1]);
                        msg += "#b#e#i" + nreward[seld2]['items'][i][0] + "# #z" + nreward[seld2]['items'][i][0] + "# " + nreward[seld2]['items'][i][1] + " (Qty) \r\n";
                    }
                    //msg += "#L1#I will receive it.";
                    //msg += "#L2#Next time";
                    cm.sendYesNo(msg);
                } else {
                    var msg = "This reward is a selectable reward. Please choose the reward you want to receive.#b" + Enter;
                    for (i = 0; i < nreward[seld2]['items'].length; i++)
                        msg += "#L" + i + "##i" + nreward[seld2]['items'][i][0] + "##z" + nreward[seld2]['items'][i][0] + "# " + nreward[seld2]['items'][i][1] + " (Qty)" + Enter;
                    cm.sendSimple(msg);
                }
                break;

            case 6:
                if (cm.getClient().getKeyValue("HgradeWeek") == null) {
                    for (a = 0; a < daily.length; a++) {
                        if (!cm.canHold(daily[a][0], daily[a][1])) {
                            cm.sendOk("Not enough inventory space.");
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
                    cm.sendOk("#fs11#Reward distribution complete.");
                    cm.dispose();
                }
                break;

            case 22:
                seld22 = sel;
                switch (seld22) {
                    case 1:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 1530050, "상시패키지상점");
                        break;
                    case 2:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 1530050, "이벤트패키지상점");
                        break;
                    case 3:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 1530050, "등급상점");
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
                            1998: { required: 5000000, scope: 'Character', note: '※ Can only set once if cumulative is under 5 million' },
                            1999: { required: 10000000, scope: 'Account', note: '' }
                        };
                        var cc = chatConfig[seld50];
                        if (cm.getPlayer().getDPointAll() >= cc.required) {
                            var msg = "#fs11#";
                            msg += Color + "< All Chat Title Priority >\r\n";
                            msg += Black + "Character Title → Account Title → Main Rank.\r\n";
                            if (seld50 === 1998) msg += "#r" + cc.note + "#k\r\n\r\n";
                            msg += "Please enter the " + cc.scope + " title to apply.";
                            cm.sendGetText(msg);
                        } else {
                            cm.sendOk("#fs11#Insufficient cumulative amount.");
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
                            cm.sendOk("#fs11#Invalid reward item.");
                            cm.dispose();
                            break;
                        }

                        var already = Number(cm.getClient().getKeyValue(key) || "0") >= 1;
                        if (userTotal >= requiredCash && !already) {
                            cm.getClient().setKeyValue(key, "1");
                            cm.gainItem(4036661, cfg.spins);
                            if (cfg.berries > 0) cm.gainItem(5068306, cfg.berries);
                            cm.sendOk("#fs11#Reward has been distributed.");
                        } else {
                            cm.sendOk("#fs11#Already collected or insufficient cumulative amount.");
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
                cm.sendOk("Account point charging is possible up to 3 million.");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getCashPoint() < sel) {
                cm.sendOk("You entered a value larger than your cash balance.");
                cm.dispose();
                return;
            }
            if (0 > sel) {
                cm.sendOk("Error Occurrence Error Occurrence");
                cm.dispose();
                return;
            }
            cm.getPlayer().gainCashPoint(-sel);
            cm.getPlayer().gainDonationPoint(sel);

            cm.getClient().setKeyValue("tradepoint", "" + (tradePoint + sel));
            cm.sendOk(comma(sel) + "Exchange complete.");
            cm.dispose();
            return;
        }
    } else if (status == 3) {
        switch (seld) {
            case 1:
                seldreward = sel;
                reward = getQ(seldreward);
                cm.sendYesNo("Do you really want to receive this reward?\r\nYou will get #b" + reward + " Donation Points#k.");
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
                    cm.sendOkS("#fs11##b#eThank you~!", 2);
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
                            cm.sendOkS("#fs11##b#eThank you~!", 2);
                            cm.dispose();
                            return;
                        } else if (SetMedalString != null && Get500MedalString == null) {
                            cm.getClient().setKeyValue("500MedalString", "1");
                            cm.getClient().getPlayer().setKeyValue("MedalString", SetMedalString);
                            cm.sendOkS("#fs11##b#eThank you~!", 2);
                            cm.dispose();
                            return;
                        } else {
                            cm.sendOkS("#fs11##b#eOh right, I already set it..", 2);
                            cm.dispose();
                            return;
                        }
                        break;

                    case 1999:
                        SetMedalString = cm.getText();
                        if (SetMedalString != null) {
                            cm.getClient().setKeyValue("MedalString", SetMedalString);
                            cm.sendOkS("#fs11##b#eThank you~!", 2);
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
                cm.sendOk("Collection complete.");
                cm.dispose();
                break;

            case 4:
                switch (seld3) {
                    case 1:
                        DeleteReward(sel);
                        cm.sendOk("Deletion complete.");
                        cm.dispose();
                        break;
                }
                break;

            case 5:
                cm.gainItem(nreward[seld2]['items'][seld3][0], nreward[seld2]['items'][seld3][1]);

                cm.getClient().setKeyValue("nd_" + nreward[seld2]['ngrade'], "1");
                cm.sendOk("#fs11#Distribution complete.");
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
                        cm.sendOk("Change complete.");
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
            ret += "#L" + rs.getInt("id") + "##b" + rs.getString("name") + " (Eligible)\r\n";
            break;
        }
    }
    rs.close();
    ps.close();
    con.close();

    if (ret.equals("")) return "#fs11#There are no donation rewards available to collect.";
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
    if (ret.equals("")) return "No donation rewards found.";
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
            return "#b(Available)#b";
        }
        return "#fc0xFF9A9A9A#(Collected)#b";
    } else {
        return "#r(Insufficient Amount)#b";
    }
}