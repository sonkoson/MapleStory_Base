importPackage(Packages.client);
importPackage(Packages.objects.item);

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
var BlackColor = "#fc0xFF000000#"
var PinkColor = "#fc0xFFFF3366#"
var LightPinkColor = "#fc0xFFF781D8#"
var Enter = "\r\n"
var Enter2 = "\r\n\r\n"

var enter = "\r\n";
var seld = -1, seld2 = -1;
var deamon = 0;
var n = 0;
var t = 0;

var jobs = [
    { 'jobid': 112, 'jobname': "Hero", 'job': "Explorer", 'stat': 1, 'sk': [80001152, 1281, 12, 73], 'uq': false },
    { 'jobid': 122, 'jobname': "Paladin", 'job': "Explorer", 'stat': 1, 'sk': [80001152, 1281, 12, 73], 'uq': false },
    { 'jobid': 132, 'jobname': "Dark Knight", 'job': "Explorer", 'stat': 1, 'sk': [80001152, 1281, 12, 73], 'uq': false },
    { 'jobid': 212, 'jobname': "F/P Archmage", 'job': "Explorer", 'stat': 3, 'sk': [80001152, 1281, 12, 73], 'uq': false },
    { 'jobid': 222, 'jobname': "I/L Archmage", 'job': "Explorer", 'stat': 3, 'sk': [80001152, 1281, 12, 73], 'uq': false },
    { 'jobid': 232, 'jobname': "Bishop", 'job': "Explorer", 'stat': 3, 'sk': [80001152, 1281, 12, 73], 'uq': false },
    { 'jobid': 312, 'jobname': "Bowmaster", 'job': "Explorer", 'stat': 2, 'sk': [80001152, 1281, 12, 73], 'uq': false },
    { 'jobid': 322, 'jobname': "Marksman", 'job': "Explorer", 'stat': 2, 'sk': [80001152, 1281, 12, 73], 'uq': false },
    { 'jobid': 332, 'jobname': "Pathfinder", 'job': "Explorer", 'stat': 2, 'sk': [80001152, 1281, 1297, 1298, 12, 73], 'uq': true },
    { 'jobid': 412, 'jobname': "Night Lord", 'job': "Explorer", 'stat': 4, 'sk': [80001152, 1281, 12, 73], 'uq': false },
    { 'jobid': 422, 'jobname': "Shadower", 'job': "Explorer", 'stat': 4, 'sk': [80001152, 1281, 12, 73], 'uq': false },
    { 'jobid': 434, 'jobname': "Dual Blade", 'job': "Explorer", 'stat': 4, 'sk': [80001152, 1281, 12, 73], 'uq': true },
    { 'jobid': 512, 'jobname': "Buccaneer", 'job': "Explorer", 'stat': 1, 'sk': [80001152, 1281, 12, 73], 'uq': false },
    { 'jobid': 522, 'jobname': "Corsair", 'job': "Explorer", 'stat': 2, 'sk': [80001152, 1281, 12, 73], 'uq': false },
    { 'jobid': 532, 'jobname': "Cannoneer", 'job': "Explorer", 'stat': 1, 'sk': [80001152, 1281, 110, 109, 111, 1283, 12, 73], 'uq': true },
    { 'jobid': 1112, 'jobname': "Soul Master", 'job': "Cygnus", 'stat': 1, 'sk': [10001244, 10000252, 80001152, 10001253, 10001254, 10001245, 10000250, 10000246, 10000012, 10000073], 'uq': false },
    { 'jobid': 1212, 'jobname': "Flame Wizard", 'job': "Cygnus", 'stat': 3, 'sk': [10001244, 10000252, 80001152, 10001253, 10001254, 10001245, 10000250, 10000248, 10000012, 10000073], 'uq': false },
    { 'jobid': 1312, 'jobname': "Wind Breaker", 'job': "Cygnus", 'stat': 2, 'sk': [10001244, 10000252, 80001152, 10001253, 10001254, 10001245, 10000250, 10000247, 10000012, 10000073], 'uq': false },
    { 'jobid': 1412, 'jobname': "Night Walker", 'job': "Cygnus", 'stat': 4, 'sk': [10001244, 10000252, 80001152, 10001253, 10001254, 10001245, 10000250, 10000249, 10000012, 10000073], 'uq': false },
    { 'jobid': 1512, 'jobname': "Striker", 'job': "Cygnus", 'stat': 1, 'sk': [10001244, 10000252, 80001152, 10001253, 10001254, 10001245, 10000250, 10000246, 10000012, 10000073], 'uq': false },
    { 'jobid': 5112, 'jobname': "Mihile", 'job': "Cygnus", 'stat': 1, 'sk': [50001214, 10000250, 10000074, 10000075, 50000012, 50000073], 'uq': false },
    { 'jobid': 2112, 'jobname': "Aran", 'job': "Hero", 'stat': 1, 'sk': [20000194, 20001295, 20001296, 20000012, 20000073], 'uq': false },
    { 'jobid': 2217, 'jobname': "Evan", 'job': "Hero", 'stat': 3, 'sk': [20010022, 20010194, 20011293, 20010012, 20010073], 'uq': true },
    { 'jobid': 2312, 'jobname': "Mercedes", 'job': "Hero", 'stat': 2, 'sk': [20020109, 20021110, 20020111, 20020112, 20020012, 20020073], 'uq': false },
    { 'jobid': 2412, 'jobname': "Phantom", 'job': "Hero", 'stat': 4, 'sk': [20031208, 20040190, 20031203, 20031205, 20030206, 20031207, 20031209, 20031251, 20031260, 20030012, 20030073], 'uq': false },
    { 'jobid': 2512, 'jobname': "Shade", 'job': "Hero", 'stat': 1, 'sk': [20051284, 20050285, 20050286, 20050074, 20050012, 20050073], 'uq': false },
    { 'jobid': 2712, 'jobname': "Luminous", 'job': "Hero", 'stat': 3, 'sk': [20040216, 20040217, 20040218, 20040219, 20040221, 20041222, 20040012, 20040073], 'uq': false },
    { 'jobid': 14212, 'jobname': "Kinesis", 'job': "Hero", 'stat': 3, 'sk': [140000291, 14200, 14210, 14211, 14212, 140001290, 140000012, 140000073], 'uq': false },
    { 'jobid': 3112, 'jobname': "Demon Slayer", 'job': "Resistance", 'stat': 1, 'sk': [30001281, 80001152, 30001061, 30010110, 30010185, 30010112, 30010111, 30010012, 30010073], 'uq': false },
    //{'jobid': 3122, 'jobname': "Demon Avenger", 'job': "Resistance", 'stat': 6, 'sk': [30001281, 80001152, 30001061, 30010110, 30010185, 30010242, 30010241, 30010230, 30010231, 30010232, 30010012, 30010073], 'uq': true},
    { 'jobid': 3212, 'jobname': "Battle Mage", 'job': "Resistance", 'stat': 3, 'sk': [30001281, 30000012, 30000073], 'uq': false },
    { 'jobid': 3312, 'jobname': "Wild Hunter", 'job': "Resistance", 'stat': 2, 'sk': [30001281, 30001062, 30001061, 30000012, 30000073], 'uq': false },
    { 'jobid': 3512, 'jobname': "Mechanic", 'job': "Resistance", 'stat': 2, 'sk': [30001281, 30001068, 30000227, 30000012, 30000073], 'uq': false },
    { 'jobid': 3712, 'jobname': "Blaster", 'job': "Resistance", 'stat': 1, 'sk': [30001281, 30020012, 30020073], 'uq': false },
    { 'jobid': 3612, 'jobname': "Xenon", 'job': "Resistance", 'stat': 5, 'sk': [30001281, 30020232, 30020233, 30020234, 30020240, 30021235, 30021236, 30021237, 30020012, 30020073], 'uq': false },
    { 'jobid': 6112, 'jobname': "Kaiser", 'job': "Nova", 'stat': 1, 'sk': [600000219, 60000222, 60001217, 60001216, 60001225, 60001005, 60001296, 60000012, 60000073, 60001218, 60000219], 'uq': false },
    { 'jobid': 6512, 'jobname': "Angelic Buster", 'job': "Nova", 'stat': 2, 'sk': [60011216, 60010217, 60011218, 60011219, 60011220, 60011221, 60011222, 60011005, 60010012, 60010073], 'uq': false },
    { 'jobid': 6412, 'jobname': "Cadena", 'job': "Nova", 'stat': 4, 'sk': [60020216, 60021217, 60021005, 60020218, 60020012, 60020073], 'uq': false },
    { 'jobid': 15112, 'jobname': "Adele", 'job': "Lef", 'stat': 1, 'sk': [150020041, 150021000, 150020079, 150020006, 150020241, 151001004], 'uq': false },
    { 'jobid': 15212, 'jobname': "Illium", 'job': "Lef", 'stat': 3, 'sk': [150001021, 150000017, 150000079, 150000012, 150000073], 'uq': false },
    { 'jobid': 15512, 'jobname': "Ark", 'job': "Lef", 'stat': 1, 'sk': [150010079, 150011005, 150011074, 150010241, 150010012, 150010073, 155101006], 'uq': false },
    { 'jobid': 16412, 'jobname': "Hoyoung", 'job': "Anima", 'stat': 4, 'sk': [160001074, 160001075, 160001005, 160000000, 160000076, 160000012, 160000073, 164001004], 'uq': false },
    { 'jobid': 6312, 'jobname': "Kain", 'job': "Nova", 'stat': 2, 'sk': [60031005, 60030012, 60030073], 'uq': false },
    { 'jobid': 16212, 'jobname': "Lara", 'job': "Anima", 'stat': 3, 'sk': [160011074, 160011075, 160011005, 160010000, 160010076, 160010012, 160010073], 'uq': false },
    { 'jobid': 15412, 'jobname': "Khali", 'job': "Lef", 'stat': 4, 'sk': [150031074, 150030079, 150031005], 'uq': false },
]
var level = -1;
var coin = -1;
var hpmp = -1;

var final = [];
var finaljob;
var jrand = -1;
var ast = -1;

var price = 0;

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

    if (cm.inBoss()) {
        cm.getPlayer().dropMessage(5, "ไม่สามารถใช้งานได้ในขณะบอสกำลังดำเนินอยู่");
        cm.dispose();
        return;
    }

    if (!cm.getPlayer().isGM() && cm.getPlayer().getDonationPoint() < price && status > 1 && seld != 2) {
        cm.sendOk("#fs11##fc0xFFFF3300#" + price + "P#fc0xFF000000# Royal Point เป็นสิ่งที่จำเป็น");
        cm.dispose();
        return;
    }
    /*
        if (!cm.getPlayer().isGM()) {
            cm.sendOk("Under maintenance.");
            cm.dispose();
            return;
        }
    */
    if (status == 0) {
        hpmp = 1000 * n;
        var msg = "#fs11##e#fc0xFFFF3300#[Job Change]#n#fc0xFF000000#\r\n\r\nต้องการเปลี่ยนไปเล่นอาชีพอื่นไหม?#b" + enter;
        msg += "#L1#ต้องการเปลี่ยนอาชีพ" + enter;
        msg += "#r#L2#[ควรอ่าน] สงสัยเกี่ยวกับระบบเปลี่ยนอาชีพ" + enter;
        cm.sendSimple(msg);
    } else {
        seld = seld == -1 ? sel : seld;

        switch (seld) {
            case 1:
                if (status == 1) {
                    if ((Math.floor(cm.getPlayer().getJob() % 10) != 2 && Math.floor(cm.getPlayer().getJob() % 10) != 4 && Math.floor(cm.getPlayer().getJob() % 10) != 7) || cm.getPlayer().getLevel() < 200) {
                        cm.sendOk("#fs11#ต้องผ่านการเปลี่ยนอาชีพคลาส 4 (หรือ 5) และมีเลเวล 200 ขึ้นไปจึงจะสามารถเปลี่ยนอาชีพได้");
                        cm.dispose();
                        return;
                    }

                    if (cm.getPlayer().getJob() == 10112) {
                        cm.sendOk("#fs11#อาชีพ Zero ไม่สามารถใช้บริการเปลี่ยนอาชีพได้ในขณะนี้");
                        cm.dispose();
                        return;
                    }

                    if (cm.getPlayer().getJob() == 3122) {
                        deamon = 1;
                        cm.sendOk("#fs11#อาชีพ Demon Avenger ไม่สามารถใช้บริการเปลี่ยนอาชีพได้ในขณะนี้");
                        cm.dispose();
                        return;
                    }
                    /*
                                        if (!cm.getPlayer().vCoreEquipCheck()) {
                                            cm.sendOk("#fs11#กรุณาถอด V Skill Core ทั้งหมดออกก่อนลองใหม่อีกครั้ง");
                                            cm.dispose();
                                            return;
                                        }
                    
                                        var 주무기 = cm.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem(-11);
                                        var 보조무기 = cm.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem(-10);
                                        if (주무기 || 보조무기) {
                                            cm.sendOk("#fs11#กรุณาถอดอาวุธหลักและอาวุธรองออกก่อนลองใหม่อีกครั้ง");
                                            cm.dispose();
                                            return;
                                        }
                    */
                    var msg = "#fs11#";
                    msg += Star + " #fc0xFF000000#กรุณาเลือกอาชีพที่ต้องการ" + enter;
                    msg += Star + " #fc0xFF000000##bPrice for 1 Job Change#fc0xFF000000# คือ #fc0xFFFF3366#" + price + "P#fc0xFF000000# Royal Point" + enter;
                    msg += Star + " ปัจจุบัน #fc0xFFFF3366##h ##fc0xFF000000# Your Points : #fc0xFFFF3366#" + cm.getPlayer().getDonationPoint() + "P#k" + BlackColor + enter;
                    msg += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#" + enter;
                    for (i = 0; i < jobs.length; i++)
                        msg += "#L" + i + "##fc0xFF6600CC#" + jobs[i]['jobname'] + "#fc0xFF000000# Change Job to " + jobs[i]['jobname'] + enter;

                    cm.sendSimple(msg);
                } else if (status == 2) {
                    seld2 = sel;

                    var msg = "#fs11##fc0xFF000000#Please read the following precautions before changing jobs." + enter;
                    msg += "#fc0xFFFF3366#" + price + " Royal Point#fc0xFF000000# แต้มจะถูกหัก และจะไม่รับแจ้งปัญหาหรือกู้คืนใดๆ เกี่ยวกับระบบนี้" + enter;
                    msg += "หากไม่ได้ตั้งใจจะเปลี่ยนเป็นอาชีพนี้จริงๆ ให้กด #b'No'#fc0xFF000000# แล้วเลือกใหม่" + enter;
                    msg += enter + "#fs12##bหากยอมรับ#fc0xFF000000#ให้กด #b'Yes'#fc0xFF000000#";

                    cm.sendYesNo(msg);
                } else if (status == 3) {
                    cm.dispose();

                    if (!cm.getPlayer().isGM() && cm.getPlayer().getDonationPoint() < price) {
                        cm.sendOk("#fs11##bRoyal Point#fc0xFF000000# ดูเหมือนจะไม่พอนะ");
                        return;
                    }
                    prevjob = cm.getPlayer().getJob();
                    tempjob = jobs[seld2];
                    cm.getPlayer().gainDonationPoint(-price);
                    changeJobscript(tempjob, 1);
                    if (deamon == 1) {
                        cm.sendOk("#fs11#เปลี่ยนอาชีพเรียบร้อยแล้ว\r\n#r※ เกจ MP จะแสดงผลเมื่อย้ายแชแนลหรือล็อกอินใหม่");
                    } else {
                        cm.effectText("#fnArial##fs20#[Job Change] เปลี่ยนเป็น < " + tempjob['jobname'] + " > เรียบร้อยแล้ว", 50, 1000, 6, 0, 330, -550);
                    }
                    prevflag = cm.getPlayer().getSaveFlag();
                    cm.getPlayer().setSaveFlag(1024); // VMatrix
                    cm.getPlayer().saveToDB(false, false);
                    cm.getPlayer().setSaveFlag(prevflag);
                    cm.addCustomLog(20, "[Job Change] Change : " + tempjob['jobname'] + "(" + tempjob['jobid'] + ") Previous : " + Packages.constants.GameConstants.getJobNameById(prevjob) + "(" + prevjob + ")");
                }
                break;

            case 2:
                if (status == 1) {
                    var msg = "#fs11#ระบบเปลี่ยนอาชีพคืออะไร?" + enter + enter;
                    msg += "จะเปลี่ยนเป็นอาชีพที่เลือกทันที" + enter + enter;
                    msg += "Arcane Symbol จะถูกเปลี่ยนเป็นค่าสเตตัสหลักของอาชีพใหม่โดยอัตโนมัติ" + enter;
                    msg += "Authentic Symbol จะถูกเปลี่ยนเป็นค่าสเตตัสหลักของอาชีพใหม่โดยอัตโนมัติ" + enter;
                    msg += "- ※ มีผลกับ Symbol ที่สวมใส่อยู่และในช่องเก็บของทั้งหมด" + enter + enter;
                    msg += "#r#fs11#※ หากไม่ตรวจสอบข้อมูลข้างต้นแล้วเปลี่ยนอาชีพ ทางเราไม่สามารถกู้คืนแก้ไขให้ได้"
                    cm.sendOk(msg);
                    cm.dispose();
                }
                break;
        }
    }
}

function baseSkill() {
    switch (cm.getJob()) {

        case 2100:
            cm.teachSkill(20001295, 1, 1);
            break;

        case 6100:
            cm.teachSkill(60000219, 1, 1);
            cm.teachSkill(60001217, 1, 1);
            cm.teachSkill(60001216, 1, 1);
            cm.teachSkill(60001218, 1, 1);
            cm.teachSkill(60001219, 1, 1);
            cm.teachSkill(60001225, 1, 1);
            break;

        case 6500:
            break;

        case 2700:
            cm.teachSkill(27000106, 5, 5);
            cm.teachSkill(27000207, 5, 5);
            cm.teachSkill(27001201, 20, 20);
            cm.teachSkill(27001100, 20, 20);
            break;
        case 2500:
            cm.teachSkill(20051284, 30, 30);
            cm.teachSkill(20050285, 30, 30);
            cm.teachSkill(20050286, 30, 30);
            cm.teachSkill(25001000, 30, 30);
            cm.teachSkill(25001002, 30, 30);
            cm.teachSkill(25000003, 30, 30);
            break;

        case 1100:
        case 1200:
        case 1300:
        case 1400:
        case 1500:
            cm.teachSkill(10001251, 1, 1);
            cm.teachSkill(10001252, 1, 1);
            cm.teachSkill(10001253, 1, 1);
            cm.teachSkill(10001254, 1, 1);
            cm.teachSkill(10001255, 1, 1);
            break;
        case 14200:
            cm.teachSkill(140000291, 6, 6);
            break;
        case 15200:
            cm.teachSkill(150000079, 1, 1);
            cm.teachSkill(150011005, 1, 1);
            break;

        case 16400:
            cm.teachSkill(160000001, 1, 1);

            break;
        case 3500:
            cm.teachSkill(30001068, 1, 1);
            break;
    }
}

function arcsymbol(j) {
    var inv = cm.getInventory(-1);
    for (i = -1600; i >= -1605; i--) {
        item = cm.getInventory(-1).getItem(i);
        if (item == null) continue;
        if (Math.floor(item.getItemId() / 1000) != 1712) continue;
        ial = item.getArcLevel();
        var normal = 100 * (ial + 2);
        var zen = 48 * (ial + 2);
        var dev = 2100 * (ial + 2);
        // 1 = s, 2 = d, 3 = i, 4 = l, 5 = z, 6 = h
        var stat = (j >= 1 && j <= 4) ? normal : j == 5 ? zen : dev;
        item.setStr(0);
        item.setDex(0);
        item.setInt(0);
        item.setLuk(0);
        item.setHp(0);

        switch (j) {
            case 1:
                item.setStr(stat);
                break;
            case 2:
                item.setDex(stat);
                break;
            case 3:
                item.setInt(stat);
                break;
            case 4:
                item.setLuk(stat);
                break;
            case 5:
                item.setStr(stat);
                item.setDex(stat);
                item.setLuk(stat);
                break;
            case 6:
                item.setHp(stat);
                break;
        }
        cm.getPlayer().forceReAddItem(item, Packages.objects.item.MapleInventoryType.EQUIPPED);
    }
    var inv = cm.getInventory(1);
    for (i = 0; i <= inv.getSlotLimit(); i++) {
        item = cm.getInventory(1).getItem(i);
        if (item == null) continue;
        if (Math.floor(item.getItemId() / 1000) != 1712) continue;
        ial = item.getArcLevel();
        var normal = 100 * (ial + 2);
        var zen = 48 * (ial + 2);
        var dev = 2100 * (ial + 2);
        // 1 = s, 2 = d, 3 = i, 4 = l, 5 = z, 6 = h
        var stat = (j >= 1 && j <= 4) ? normal : j == 5 ? zen : dev;
        item.setStr(0);
        item.setDex(0);
        item.setInt(0);
        item.setLuk(0);
        item.setHp(0);

        switch (j) {
            case 1:
                item.setStr(stat);
                break;
            case 2:
                item.setDex(stat);
                break;
            case 3:
                item.setInt(stat);
                break;
            case 4:
                item.setLuk(stat);
                break;
            case 5:
                item.setStr(stat);
                item.setDex(stat);
                item.setLuk(stat);
                break;
            case 6:
                item.setHp(stat);
                break;
        }
        cm.getPlayer().forceReAddItem(item, Packages.objects.item.MapleInventoryType.EQUIPPED);
    }
}


function autsymbol(j) {
    var inv = cm.getInventory(-1);
    for (i = -1700; i >= -1704; i--) {
        item = cm.getInventory(-1).getItem(i);
        if (item == null) continue;
        if (Math.floor(item.getItemId() / 1000) != 1713) continue;
        ial = item.getArcLevel();
        var normal = 100 * ((ial * 2) + 3);
        var zen = 48 * ((ial * 2) + 3);
        var dev = 2100 * ((ial * 2) + 3);
        // 1 = s, 2 = d, 3 = i, 4 = l, 5 = z, 6 = h
        var stat = (j >= 1 && j <= 4) ? normal : j == 5 ? zen : dev;
        item.setStr(0);
        item.setDex(0);
        item.setInt(0);
        item.setLuk(0);
        item.setHp(0);

        switch (j) {
            case 1:
                item.setStr(stat);
                break;
            case 2:
                item.setDex(stat);
                break;
            case 3:
                item.setInt(stat);
                break;
            case 4:
                item.setLuk(stat);
                break;
            case 5:
                item.setStr(stat);
                item.setDex(stat);
                item.setLuk(stat);
                break;
            case 6:
                item.setHp(stat);
                break;
        }
        cm.getPlayer().forceReAddItem(item, Packages.objects.item.MapleInventoryType.EQUIPPED);
    }
    var inv = cm.getInventory(1);
    for (i = 0; i <= inv.getSlotLimit(); i++) {
        item = cm.getInventory(1).getItem(i);
        if (item == null) continue;
        if (Math.floor(item.getItemId() / 1000) != 1713) continue;
        ial = item.getArcLevel();
        var normal = 100 * ((ial * 2) + 3);
        var zen = 48 * ((ial * 2) + 3);
        var dev = 2100 * ((ial * 2) + 3);
        // 1 = s, 2 = d, 3 = i, 4 = l, 5 = z, 6 = h
        var stat = (j >= 1 && j <= 4) ? normal : j == 5 ? zen : dev;
        item.setStr(0);
        item.setDex(0);
        item.setInt(0);
        item.setLuk(0);
        item.setHp(0);

        switch (j) {
            case 1:
                item.setStr(stat);
                break;
            case 2:
                item.setDex(stat);
                break;
            case 3:
                item.setInt(stat);
                break;
            case 4:
                item.setLuk(stat);
                break;
            case 5:
                item.setStr(stat);
                item.setDex(stat);
                item.setLuk(stat);
                break;
            case 6:
                item.setHp(stat);
                break;
        }
        cm.getPlayer().forceReAddItem(item, Packages.objects.item.MapleInventoryType.EQUIPPED);
    }
}


function changeJobscript(tjob, jt) {
    jid = tjob['jobid'];

    cm.getPlayer().changeJob(jid, true);

    cm.getPlayer().getLinkSkill().updateLinkSkillByFreeJobChange(cm.getPlayer());

    cm.clearSkills();

    cm.getPlayer().getVCoreSkillsNoLock().clear();

    cm.getPlayer().getStolenSkills().clear();

    cm.getPlayer().setKeyValue(1477, "count", "0");

    baseSkill();

    cm.getPlayer().statReset();

    arcsymbol(tjob['stat']);

    autsymbol(tjob['stat']);

    for (i = 0; i < tjob['sk'].length; i++) {
        cm.teachSkill(tjob['sk'][i], 30, 30);
    }

    if (jid == 6500) {
        cm.getPlayer().setGender(1);
    }

    cm.getPlayer().updateOneInfo(122870, "AutoJob", jid + "");

    cm.autoSkillMaster();

    cm.getPlayer().fakeRelog();


    /*
        if (!cm.getPlayer().isGM()) {
            cm.getClient().disconnect(true, false);
            cm.getClient().getSession().close();
        }
    */
}