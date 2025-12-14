// Shop System

importPackage(Packages.scripting);

var enter = "\r\n";
var seld = -1, seld2 = -1;

purple = "#fMap/MapHelper.img/weather/starPlanet/7#";
blue = "#fMap/MapHelper.img/weather/starPlanet/8#";
starBlue = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
starYellow = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
starWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
starBrown = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
starRed = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
starBlack = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
starPurple = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
star = "#fUI/FarmUI.img/objectStatus/star/whole#"
s = "#fUI/CashShop.img/CSEffect/today/0#"
reward = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
obtain = "#fUI/UIWindow2.img/QuestIcon/4/0#"
color = "#fc0xFF6600CC#"
enter = "\r\n"
enter2 = "\r\n\r\n"

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
            cm.getPlayer().dropMessage(5, "ไม่สามารถใช้ได้ในขณะต่อสู้กับบอส");
            cm.dispose();
            return;
        }

        //        var msg = "#fs11#     #fUI/UIWindow5.img/Disguise/backgrnd3#\r\n";
        var msg = "#fs11##fUI/Basic.img/Zenia/SC/2#\r\n";
        //      msg += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#\r\n";
        msg += "#L1##fUI/Basic.img/Zenia/SCBtn/201##l";//Consumable
        msg += "#L91##fUI/Basic.img/Zenia/SCBtn/200##l";//Equipment
        msg += "#L13##fUI/Basic.img/Zenia/SCBtn/202##l\r\n";//Crafting
        msg += "#L90##fUI/Basic.img/Zenia/SCBtn/203##l";//Coin
        msg += "#L92##fUI/Basic.img/Zenia/SCBtn/205##l";//Etc
        msg += "#L3##fUI/Basic.img/Zenia/SCBtn/204##l\r\n\r\n";//Crystal
        /*
                msg +="#L1##fs11##fUI/Basic.img/Zenia/SCBtn/10#" + "#l#L90##fUI/Basic.img/Zenia/SCBtn/6##l\r\n";
                msg +="#L91##fUI/Basic.img/Zenia/SCBtn/8#" + "#l#L92##fUI/Basic.img/Zenia/SCBtn/7##l\r\n";
                msg +="#L2##fUI/Basic.img/Zenia/SCBtn/11#" + "#l#L3##fUI/Basic.img/Zenia/SCBtn/9##l\r\n\r\n";
                msg +="　　　　　　　　　　#fc0xFFFF3366##L13##fUI/UIWindow4.img/pointShop/100658/iconShop# Craft System#l\r\n"
        */
        //msg += "　　　　#fc0xFFFF3366##L11##fUI/UIWindow4.img/pointShop/501053/iconShop# Donation#l #L12##fUI/UIWindow4.img/pointShop/501053/iconShop##fc0xFFFF3366# Supporters#l\r\n\r\n";
        //msg +="#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#k";
        cm.sendSimple(msg);
    } else if (status == 1) {
        seld = sel;
        switch (sel) {
            case 1: // Consumable Shop
                cm.dispose();
                cm.openShop(1);
                break;
            case 3: // Sell Boss Crystal
                cm.dispose();
                cm.openShop(9001212);
                break;
            case 11: // Donation Shop
                cm.dispose();
                cm.openNpc(1530050);
                break;
            case 12: // Promotion Shop
                cm.dispose();
                cm.openNpc(1530051);
                break;
            case 13: // Crafting System
                if (cm.getInvSlots(1) < 3 || cm.getInvSlots(2) < 3 || cm.getInvSlots(3) < 3 || cm.getInvSlots(4) < 3 || cm.getInvSlots(5) < 3) {
                    cm.sendOkS("#fs11##fc0xFF6600CC#กรุณาเคลียร์ช่องว่างในกระเป๋าอย่างน้อย 3 ช่องในแต่ละแท็บ", 2);
                    cm.dispose();
                    return;
                }
                var msg = "#fs11#    #fUI/Basic.img/Zenia/SC/2#\r\n";
                msg += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#k";
                msg += "#fc0xFF000099#　　#L1# [Craft] Basic Acc#l　　　#L124# [Craft] Transcendence Acc#l \r\n"
                msg += "#fc0xFF000099#　　#L2# [Craft] Eternal#l　　　　#L3# [Craft] Genesis#l\r\n\r\n"

                msg += "#fc0xFF990033##L4# [Upgrade] Chaos Root Abyss (Fafnir)#l #L50# [Upgrade] Chaos Arcane Shade#l\r\n"
                msg += "#fc0xFF990033##L60# [Upgrade] Chaos Eternal#l #L70# [Upgrade] Chaos Pitch Black#l\r\n\r\n"

                msg += "#fc0xFFFF3300##L301# [Upgrade] Ruin Arcane Shade#l\r\n"
                msg += "#fc0xFFFF3300##L302# #Cgray#[Coming Soon] Ruin Eternal#k#l #fc0xFFFF3300##L304# [Upgrade] Ruin Pitch Black#k#l\r\n\r\n"
                msg += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#k";
                cm.sendSimple(msg);
                break;
            case 90: // Coin Shop
                var msg = "#fs11#    #fUI/Basic.img/Zenia/SC/2#\r\n";
                msg += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#k";
                msg += "　　#fc0xFF000099##L10##fUI/UIWindow4.img/pointShop/4310237/iconShop# Hunt Coin#l";
                msg += "　#fc0xFF000099##L7##fUI/UIWindow4.img/pointShop/500629/iconShop# Union Shop#l";
                msg += "　#fc0xFF000099##L8##fUI/UIWindow4.img/pointShop/100508/iconShop# Attendance Coin#l\r\n\r\n";
                msg += "　　#fc0xFF6633FF##L5##fUI/UIWindow4.img/pointShop/100711/iconShop# Neo Stone#l";
                msg += "　#fc0xFF6633FF##L6##fUI/UIWindow4.img/pointShop/100712/iconShop# Rest Shop#l";
                msg += "　#fc0xFF6633FF##L22##fUI/UIWindow4.img/pointShop/501215/iconShop# Neo Core#l";
                //msg += "　　#fc0xFF990033##L23##fUI/UIWindow4.img/pointShop/16393/iconShop# Anti-Magic Stone#l";
                //msg += "　#fc0xFF6633FF##L6##fUI/UIWindow4.img/pointShop/100712/iconShop# 123123#l";
                //msg += "　#fc0xFF6633FF##L22##fUI/UIWindow4.img/pointShop/501215/iconShop# 123123#l\r\n\r\n";
                msg += "\r\n\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#k";
                cm.sendSimple(msg);
                break;
            case 91: // Equipment Shop
                var msg = "#fs11#    #fUI/Basic.img/Zenia/SC/2#\r\n";
                msg += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#k";
                msg += "　　#fc0xFF000099##L1##fUI/UIWindow4.img/pointShop/100658/iconShop##fc0xFFFF3300# Basic Acc Shop#l";
                msg += "　　　#fc0xFF000099##L2##fUI/UIWindow4.img/pointShop/100658/iconShop##fc0xFFFF3300# Secondary Weapon Shop#l\r\n\r\n";
                msg += "　　#L50##fUI/UIWindow4.img/pointShop/4310065/iconShop##fc0xFF0066CC# Fafnir Shop#fc0xFF000000##l\r\n\r\n";
                //msg += "#L51##fUI/UIWindow4.img/pointShop/4310065/iconShop##fc0xFF0066CC# Transcendence Fafnir Shop#fc0xFF000000# Use#l\r\n";
                msg += "　　#L52##fUI/UIWindow4.img/pointShop/4310218/iconShop##fc0xFF000099# Arcane Shade Shop#fc0xFF000000##l";
                msg += "  #L53##fUI/UIWindow4.img/pointShop/4310249/iconShop##fc0xFF000099# Arcane Shade Shop#fc0xFF000000##l\r\n";
                msg += "　　#L90##fUI/UIWindow4.img/pointShop/4310218/iconShop##fc0xFF000099# #z4310218##fc0xFF000000# Exchange#l";
                msg += " #L91##fUI/UIWindow4.img/pointShop/4310249/iconShop##fc0xFF000099# #z4310249##fc0xFF000000# Exchange#l\r\n";
                msg += "　　#L900##fUI/UIWindow4.img/pointShop/4310218/iconShop##fc0xFF000099# #z4310218##fc0xFF000000# Exchange 2#l";
                //msg += "#L54##fUI/UIWindow4.img/pointShop/4318000/iconShop##fc0xFF000099# Transcendence Arcane Shop #fc0xFF000000#Use#l#L92##fUI/UIWindow4.img/pointShop/4318000/iconShop##fc0xFF000099# #z4318000##fc0xFF000000# Exchange#l\r\n\r\n";

                cm.sendSimple(msg);
                break;
            case 92: // Etc Shop
                var msg = "#fs11#    #fUI/Basic.img/Zenia/SC/2#\r\n";
                msg += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#k";
                msg += "　　#fc0xFF000099##L3##fUI/Basic.img/BtCoin/normal/0##fc0xFFFF3300# Currency Exchange#l";
                msg += "　　　#fc0xFF000099##L4##fUI/Basic.img/BtCoin/normal/0##fc0xFFFF3300# Meso Currency Shop#l\r\n\r\n";
                msg += "　　#fc0xFF000099##L1##fUI/Basic.img/BtCoin/normal/0##fc0xFF000099# Anvil Shop#l";
                msg += "　　　#fc0xFF000099##L2##fUI/Basic.img/BtCoin/normal/0##fc0xFF000099# Cash Outfit Shop#l\r\n\r\n";
                //msg += "　　　　　　　　　#fc0xFF000099##L5##fUI/Basic.img/BtCoin/normal/0##fc0xFFFF3300# Red Cube Exchange#l\r\n";
                //msg += "　　　　　　　　　#fc0xFF000099##L6##fUI/Basic.img/BtCoin/normal/0##fc0xFFFF3300# Transcendence Item#l\r\n\r\n";
                msg += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#k";
                cm.sendSimple(msg);
                break;
        }
    } else if (status == 2) {
        seld2 = sel;
        switch (seld) {
            case 1:
                switch (sel) {
                    case 1:
                        cm.dispose();
                        cm.openShop(1);
                        break;
                }
                break;
            case 13: // Crafting System
                switch (sel) {
                    case 1:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 3006009, "BaseAcc");
                        break;
                    case 2:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 3006009, "GanglimEternalCrafting");
                        break;
                    case 3:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 3006009, "GanglimGenesisCrafting");
                        break;
                    case 4:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 3006009, "GanglimFafnirUpgrade");
                        break;
                    case 50:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 3006009, "GanglimArcaneUpgrade");
                        break;
                    case 60:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 3006009, "GanglimEternalUpgrade");
                        break;
                    case 70:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 3006009, "GanglimPitchBlackUpgrade");
                        break;
                    case 124:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 3006009, "GanglimTranscendenceAccessory");
                        break;
                    case 301:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 3006009, "GanglimArcaneDestruction");
                        break;
                    case 304:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 3006009, "GanglimPitchBlackDestruction");
                        break;
                }
            case 90:
                switch (sel) {
                    case 5:
                        cm.dispose();
                        cm.openShop(9062459);
                        break;
                    case 6:
                        cm.dispose();
                        cm.openShop(9001213);
                        break;
                    case 7:
                        cm.openShop(9010107);
                        break;
                    case 8:
                        cm.dispose();
                        cm.openShop(18);
                        break;
                    case 10:
                        cm.dispose();
                        cm.openShop(1302011);
                        break;
                    case 22:
                        cm.dispose();
                        cm.openShop(20);
                        break;
                    case 23:
                        cm.dispose();
                        cm.openShop(21);
                        break;
                }
                break;
            case 91:
                switch (sel) {
                    case 1:
                        cm.dispose();
                        cm.openShop(9031015);
                        break;
                    case 2:
                        cm.dispose();
                        cm.openShop(2);
                        break;
                    case 50:
                        cm.dispose();
                        cm.openShop(1064003);
                        break;
                    case 51:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 9000213, "royalPaf");
                        break;
                    case 52:
                        cm.dispose();
                        cm.openShop(4);
                        break;
                    case 53:
                        cm.dispose();
                        cm.openShop(5);
                        break;
                    case 54: // Transcendence Arcane
                        cm.dispose();
                        cm.openNpc(1052206, "제작초월아케인");
                        break;
                    // Material Exchange
                    case 90:
                        cm.dispose();
                        cm.openNpc(3003105);
                        break;
                    case 900:
                        cm.dispose();
                        cm.openNpc(3006264);
                        break;
                    case 91:
                        cm.dispose();
                        cm.openNpc(3003536);
                        break;
                    case 92: // Transcendence Arcane Material Exchange
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 9000213, "TranscendenceArcaneMaterialExchange");
                        break;
                }
                break;
            case 92:
                switch (sel) {
                    case 1:
                        cm.dispose();
                        cm.openShop(9031003);
                        break;
                    case 2:
                        cm.dispose();
                        cm.openShop(6); // Cash Shop
                        break;
                    case 3:
                        cm.dispose();
                        cm.openShop(17); // Meso Exchange
                    case 4:
                        cm.dispose();
                        cm.openShop(22); // Meso Shop
                        break;
                    case 5:
                        cm.dispose();
                        cm.openShop(23); // Red Cube Shop
                    case 6:
                        cm.dispose();
                        cm.openShop(24); // Transcendence Item
                }
                break;
        }
    } else if (status == 3) {
        if (seld == 4 && seld2 == 3) {
            switch (sel) {
                case 1:
                    cm.dispose();
                    cm.openShop(10);
                    break;
                case 2:
                    cm.dispose();
                    cm.openNpc(10);
                    break;
                case 3:
                    cm.dispose();
                    cm.openNpc(10);
                    break;
                case 4:
                    cm.dispose();
                    cm.openNpc(10);
                    break;
                case 5:
                    cm.dispose();
                    cm.openNpc(10);
                    break;
            }
        }
    }
}