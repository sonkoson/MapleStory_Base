var wList = [];
var St = -1;

function start() {
    St = -1;
    action(1, 0, 0);
}

function action(M, T, S) {
    if (M != 1) {
        cm.dispose();
        return;
    }

    if (M == 1)
        St++;

    if (St == 0) {
        getWeapon(cm.getPlayer().getJob());
        selStr = "#fs 11##fc0xFF990033#[Zenia]#fc0xFF000000# ยินดีต้อนรับสู่เซิร์ฟเวอร์ของเรา! นี่คืออุปกรณ์สนับสนุนเพื่อช่วยเหลือการผจญภัยของคุณ กรุณาเลือกอาวุธที่เหมาะกับอาชีพ\r\n";
        selStr += "#L0##r#eเลือกทีหลัง#b#n#l\r\n\r\n";
        for (i = 0; i < wList.length; i++) {
            selStr += "#L" + wList[i] + "##i" + wList[i] + ":# #t" + wList[i] + ":#l\r\n";
        }

        cm.sendSimpleS(selStr, 4, 9062294);

    }

    else if (St == 1) {
        if (S == 0 || S == 1213020) {
            cm.getPlayer().dropMessage(5, "ยกเลิกการใช้กล่อง");
            cm.dispose();
            return;
        }

        cm.sendOkS("#fs11##fc0xFF000000#ข้าอยากให้เป็นกำลังใจในการผจญภัยของเจ้า จึงได้ใส่ #fc0xFFFF3300##eออปชั่นที่ดี#fc0xFF000000##nไว้ให้แล้ว! ขอให้สนุกกับ #fc0xFF990033#[Zenia]#fc0xFF000000# นะ\r\n#b"
            + "  #i1003561:# #t1003561:#\r\n"
            + "  #i1052467:# #t1052467:#\r\n"
            + "  #i1032148:# #t1032148:#\r\n"
            + "  #i1132161:# #t1132161:#\r\n"
            + "  #i1152099:# #t1152099:#\r\n"
            + "  #i1102467:# #t1102467:#\r\n"
            + "  #i1072672:# #t1072672:#\r\n"
            + "  #i1082438:# #t1082438:#\r\n"
            + "  #i" + S + ":# #t" + S + ":# #e#k#n\r\n", 4, 9062294);

        if (wList.indexOf(S) == -1) {
            cm.sendOk("การเข้าถึงผิดปกติ");
            cm.dispose();
            return;
        }

        addOption(1003561, false);
        addOption(1052467, false);
        addOption(1032148, false);
        addOption(1132161, false);
        addOption(1152099, false);
        addOption(1102467, false);
        addOption(1072672, false);
        addOption(1082438, false);
        addOption(S, true);
        cm.gainItem(2430917, -1);

        item = Packages.objects.item.MapleItemInformationProvider.getInstance().getEquipById(1142085);
        item.setStr(10);
        item.setDex(10);
        item.setInt(10);
        item.setLuk(10);
        item.setWatk(5);
        item.setMatk(5);
        item.setHp(item.getHp() + 3000);
        Packages.objects.item.MapleInventoryManipulator.addbyItem(cm.getClient(), item, false);
        cm.dispose();
        return;

    }
}

function isMagician(i) {
    switch (Math.floor(i / 100)) {
        case 2:
        case 12:
        case 22:
        case 27:
        case 32:
        case 142:
        case 152:
        case 162:
            return true;
            break;

        default:
            return false;
            break;
    }
}

function getWeapon(i) {
    switch (Math.floor(cm.getPlayer().getJob())) {
        /* Warrior */
        case 100:
            wList.push(1302334); // One-Handed Sword
            wList.push(1312200); // One-Handed Axe
            wList.push(1322251); // One-Handed Blunt Weapon
            wList.push(1402252); // Two-Handed Sword
            wList.push(1412178); // Two-Handed Axe
            wList.push(1422185); // Two-Handed Blunt Weapon
            wList.push(1432215); // Spear
            wList.push(1442269); // Polearm
            break;

        case 110:
            wList.push(1302334); // One-Handed Sword
            wList.push(1402252); // Two-Handed Sword
            break;

        case 120:
            wList.push(1302334); // One-Handed Sword
            wList.push(1312200); // One-Handed Axe
            wList.push(1322251); // One-Handed Blunt Weapon
            wList.push(1402252); // Two-Handed Sword

            wList.push(1412178); // Two-Handed Axe
            wList.push(1422185); // Two-Handed Blunt Weapon
            break;

        case 130:
            wList.push(1432215); // Spear
            wList.push(1442269); // Polearm
            break;

        case 1100:
        case 1110:
            wList.push(1402252); // Two-Handed Sword
            break;

        case 2100:
        case 2110:
            wList.push(1442269); // Polearm
            break;

        case 3100:
        case 3101:
            wList.push(1312200); // One-Handed Axe
            wList.push(1322251); // One-Handed Blunt Weapon
            wList.push(1232110); // Desperado
            break;

        case 3110:
            wList.push(1312200); // One-Handed Axe
            wList.push(1322251); // One-Handed Blunt Weapon
            break;

        case 3120:
        case 3101:
            wList.push(1232110); // Desperado
            break;

        case 3700:
        case 3710:
            wList.push(1582021); // Gauntlet Revolver
            break;

        case 5100:
        case 5110: // Mihile
            wList.push(1302334); // One-Handed Sword
            break;

        case 6100:
        case 6110:
            wList.push(1402252); // Two-Handed Sword
            break;

        case 15002:
            wList.push(1213020); // Tuner
            break;

        /* Magician */
        case 200:
        case 210:
        case 220:
        case 1200:
        case 1210:
        case 2200:
        case 2210:
            wList.push(1372223); // Wand
            wList.push(1382260); // Staff
            break;

        case 2700:
        case 2710:
            wList.push(1212116); // Shining Rod
            break;

        case 3200:
        case 3210:
            wList.push(1382260); // Staff
            break;

        case 14200:
        case 14210:
            wList.push(1262027); // ESP Limiter
            break;

        case 15200:
        case 15210:
            wList.push(1282019); // Magic Gauntlet
            break;

        /* Bowman */
        case 300:
            wList.push(1452253); // Bow
            wList.push(1462240); // Crossbow
            break;

        case 310:
        case 1300:
        case 1310:
            wList.push(1452253); // Bow
            break;

        case 320:
        case 3300:
        case 3310:
            wList.push(1462240); // Crossbow
            break;

        case 301:
            wList.push(1592008); // Ancient Bow
            break;

        case 2300:
        case 2310:
            wList.push(1522139); // Dual Bowgun
            break;

        case 400:
            wList.push(1332275); // Dagger
            wList.push(1472262); // Claw
            break;

        case 410:
        case 1400:
        case 1410:
            wList.push(1472262); // Claw
            break;

        case 420:
        case 430:
            wList.push(1332275); // Dagger
            break;

        case 2400:
        case 2410:
            wList.push(1362136); // Cane
            break;

        case 3600:
        case 3610:
            wList.push(1242117); // Energy Sword
            break;

        case 6400:
        case 6410:
            wList.push(1272031); // Chain
            break;

        case 6300:
            wList.push(1214020); // Breath Shooter
            break;

        // Pirate
        case 500:
            wList.push(1482217); // Knuckle
            wList.push(1492232); // Gun
            wList.push(1532145); // Hand Cannon
            break;

        case 510:
        case 1500:
        case 1510:
        case 2500:
        case 2510:
        case 15500:
        case 15510:
            wList.push(1482217); // Knuckle
            break;

        case 520:
        case 3500:
        case 3510:
            wList.push(1492232); // Gun
            break;

        case 530:
        case 501:
            wList.push(1532145); // Hand Cannon
            break;

        case 16400:
            wList.push(1292023); // Fan
            break;

        case 16200: // Lara
            wList.push(1372223); // Wand
            break;

        case 6500:
        case 6510:
            wList.push(1222110); // Soul Shooter
            break;
        case 10110:
            break;
        default:
            wList.push(1213023);
            break;
    }
}
function hpJobCheck(i) {
    if (Math.floor(i / 10) === 312)
        return true;
}

function addOption(i, isWeapon) {
    item = Packages.objects.item.MapleItemInformationProvider.getInstance().getEquipById(i);
    if (!isWeapon) {
        if (hpJobCheck(cm.getPlayer().getJob())) {
            item.setHp(item.getHp() + 2500);
            item.setState(20);
            item.setPotential1(40086);
            item.setPotential2(40086);
            item.setPotential3(40086);
            item.setWatk(item.getWatk() + 40);
        }
        else {
            item.setStr(item.getStr() + 50);
            item.setDex(item.getDex() + 50);
            item.setInt(item.getInt() + 50);
            item.setLuk(item.getLuk() + 50);

            if (isMagician(cm.getPlayer().getJob()))
                item.setMatk(item.getMatk() + 40);

            else
                item.setWatk(item.getWatk() + 40);

            item.setState(19);
            item.setPotential1(40086);
            item.setPotential2(40086);
            item.setPotential3(40086);
        }
    }
    else {

        if (hpJobCheck(cm.getPlayer().getJob())) {
            item.setHp(item.getHp() + 5000);
            item.setWatk(item.getWatk() + 40);
            item.setState(19);
            item.setPotential1(30051);
            item.setPotential2(30051);
            item.setPotential3(30051);
        }
        else {
            item.setStr(item.getStr() + 30);
            item.setDex(item.getDex() + 30);
            item.setInt(item.getInt() + 30);
            item.setLuk(item.getLuk() + 30);
            item.setState(19);
            if (isMagician(cm.getPlayer().getJob())) {
                item.setMatk(item.getMatk() + 70);
                item.setPotential1(30052);
                item.setPotential2(30052);
                item.setPotential3(30052);
            }
            else {
                item.setWatk(item.getWatk() + 70);
                item.setPotential1(30051);
                item.setPotential2(30051);
                item.setPotential3(30051);
            }
        }
    }
    item.setReqLevel(-90);
    item.setLevel(item.getUpgradeSlots());
    item.setUpgradeSlots(0);
    Packages.objects.item.MapleInventoryManipulator.addbyItem(cm.getClient(), item, false);
}
