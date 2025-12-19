/*
Warp NPC
*/

h = "#fUI/UIMiniGame.img/starPlanetRPS/heart#";
g = "#fMap/MapHelper.img/minimap/anothertrader#";
a = "#i3801317#"
b = "#i3801313#"
c = "#i3801314#"
d = "#i3801315#"
p = "#fc0xFFF781D8#"

randnumber = Packages.objects.utils.Randomizer.rand(100, 200);

pocket = "#fUI/Basic.img/RoyalBtn/theblackcoin/23#";
blackMage = "#fUI/Basic.img/RoyalBtn/theblackcoin/42#";

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
S = "#fUI/CashShop.img/CSEffect/today/0#"
reward = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
obtain = "#fUI/UIWindow2.img/QuestIcon/4/0#"
color = "#fc0xFF6600CC#"
black = "#fc0xFF000000#"
pink = "#fc0xFFFF3366#"
lightPink = "#fc0xFFF781D8#"
enter = "\r\n"
enter2 = "\r\n\r\n"

mTown = ["SixPath", "Henesys", "Ellinia", "Perion", "KerningCity",
    "Rith", "Dungeon", "Nautilus", "Ereb", "Rien",
    "Orbis", "ElNath", "Ludibrium", "Folkvillige", "AquaRoad",
    "Leafre", "Murueng", "WhiteHerb", "Ariant", "Magatia",
    "Edelstein", "Eurel", "critias", "Haven", "Road of Vanishing", "ChewChew", "Lacheln", "Arcana", "Morass", "esfera", "aliance", "moonBridge", "TheLabyrinthOfSuffering", "Limen"
];

cTown = [104020000, 120040000, 101000000, 102000000, 103000000,
    104000000, 105000000, 120000000, 130000000, 140000000,
    200000000, 211000000, 220000000, 224000000, 230000000,
    240000000, 250000000, 251000000, 260000000, 261000000,
    310000000, 101050000, 241000000, 310070000, 450001000, 450002000, 450003000, 450005000, 450006130, 450007040, 450009050, 450009100, 450011500, 450012300
];

var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }

    if (status == 0) {
        if (cm.inBoss()) {
            cm.getPlayer().dropMessage(5, "ไม่สามารถใช้งานได้ในขณะบอสกำลังดำเนินอยู่");
            cm.dispose();
            return;
        }

        var msg = "#fs11##fUI/Basic.img/Zenia/SC/3#\r\n";
        msg += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#\r\n";

        msg += "       #L1##fUI/Basic.img/Zenia/SCBtn/300##l       ";//Warp to Hunting Ground
        //msg += "#L999##fUI/Basic.img/Zenia/SCBtn/301##l";//Auto Hunting
        msg += "#L2##fUI/Basic.img/Zenia/SCBtn/302##l\r\n";//Warp to Boss
        msg += "       #L3##fUI/Basic.img/Zenia/SCBtn/303##l       ";//Warp to Town
        //msg += "#L4##fUI/Basic.img/Zenia/SCBtn/304##l";//Event Map
        msg += "#L5##fUI/Basic.img/Zenia/SCBtn/305##l\r\n\r\n";//Warp to Other Areas

        cm.sendSimple(msg);
    } else if (status == 1) {
        ans_01 = selection;
        selStr = "";
        if (ans_01 == 999 && cm.getPlayer().getLevel() < 300) {
            cm.sendSimple("ใช้งานได้ตั้งแต่เลเวล 300 ขึ้นไป");
            cm.dispose();
            return;
        }


        switch (ans_01) {
            case 1:
                selStr += "#L1# #fMap/MapHelper.img/minimap/party##r Level 10 ~ Level 200#k #fc0xFF000000#Warp to Hunting Ground#l\r\n\r\n";
                selStr += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#k";
                selStr += "#L3# #fUI/UIWindow.img/ToolTip/WorldMap/ArcaneForce##fc0xFF0066CC# Arcane River#fc0xFF000000# Warp to Hunting Ground#l\r\n";
                selStr += "#L5# #fUI/UIWindow.img/ToolTip/WorldMap/ArcaneForce##fc0xFF0066CC# Sellas#fc0xFF000000# Warp to Hunting Ground#l\r\n";
                selStr += "#L6# #fUI/UIWindow.img/ToolTip/WorldMap/AuthenticForce##fc0xFF3366FF# Cernium#fc0xFF000000# Warp to Hunting Ground#l\r\n";
                selStr += "#L7# #fUI/UIWindow.img/ToolTip/WorldMap/AuthenticForce##fc0xFF3366FF# Hotel Arcus#fc0xFF000000# Warp to Hunting Ground#l\r\n";
                selStr += "#L8# #fUI/UIWindow.img/ToolTip/WorldMap/AuthenticForce##fc0xFF3366FF# Odium#fc0xFF000000# Warp to Hunting Ground#l\r\n";
                selStr += "#L9# #fUI/UIWindow.img/ToolTip/WorldMap/AuthenticForce##fc0xFF3366FF# Shangri-La#fc0xFF000000# Warp to Hunting Ground#l\r\n";
                selStr += "#L10# #fUI/UIWindow.img/ToolTip/WorldMap/AuthenticForce##fc0xFF3366FF# Arteria#fc0xFF000000# Warp to Hunting Ground#l\r\n\r\n";
                selStr += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#k";
                selStr += "#L2# #fUI/Basic.img/BtCoin/normal/0##fc0xFF339933# Warp to Fatigue Hunting Ground#l#fc0xFF000000#\r\n";
                cm.sendSimpleS(selStr, 4);
                break;

            case 2:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 9000213, "BossMove");
                break;

            case 3:
                cm.dispose();
                cm.openNpc(3000012);
                break;

            case 4:
                var selStr = "#fs11#";
                selStr += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――\r\n#fc0xFFFF3300#";
                selStr += color + "            #L1#" + blackMage + " Coming Soon#l#L2#" + pocket + pink + " [Coordi Map]" + color + " Warp#l\r\n\r\n";
                selStr += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――\r\n#fc0xFFFF3300#";
                cm.sendSimple(selStr);
                break;

            case 5:
                selStr += "#fs11#";
                //selStr += "#L5# #fs11# Adventure Drill #b(Earn currency with your strength!)#k\r\n";
                selStr += "#L2#  Warp to Mu Lung Dojo\r\n\r\n";
                selStr += "#L3#  Reward Jump Map\r\n";
                selStr += "#L6#  Normal Jump Map\r\n\r\n";
                //selStr += "#L4# Fishing Spot #b(Catch big fish!)#k\r\n";
                cm.sendSimpleS(selStr, 4);
                break;

            case 999:
                starImg = "#fUI/UIWindow.img/ToolTip/WorldMap/StarForce#";
                selStr += "#fs11##fc0xFF000000#The Fatigue Hunting Ground consumes #e#fc0xFFFF9436Fatigue#k#n after recovery via Fatigue Relief Potion. Unlike #b#enormal hunting grounds#fc0xFF000000##n, you can obtain #b#emany items #r[#z4031227#, #z4310266#, #z4310237#]#b and significant EXP here.\r\n\r\n#fc0xFF000000#You cannot use it if you exhaust all your fatigue.\r\n#rNote: Fatigue resets to 0 after midnight.\r\n\r\n";
                selStr += "#b#L" + 150 + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.300 #fc0xFFFF9436#Fatigue Hunting Ground #b(A - 1)#l\r\n";
                selStr += "#b#L" + 151 + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.300 #fc0xFFFF9436#Fatigue Hunting Ground #b(A - 2)#l\r\n";
                selStr += "#b#L" + 152 + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.300 #fc0xFFFF9436#Fatigue Hunting Ground #b(A - 3)#l\r\n\r\n";
                selStr += "#b#L" + 153 + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.300 #fc0xFFFF9436#Fatigue Hunting Ground #b(B - 1)#l\r\n";
                selStr += "#b#L" + 154 + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.300 #fc0xFFFF9436#Fatigue Hunting Ground #b(B - 2)#l\r\n";
                selStr += "#b#L" + 155 + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.300 #fc0xFFFF9436#Fatigue Hunting Ground #b(B - 3)#l\r\n\r\n";
                selStr += "#b#L" + 156 + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.300 #fc0xFFFF9436#Fatigue Hunting Ground #b(C - 1)#l\r\n";
                selStr += "#b#L" + 157 + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.300 #fc0xFFFF9436#Fatigue Hunting Ground #b(C - 2)#l\r\n";
                selStr += "#b#L" + 158 + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.300 #fc0xFFFF9436#Fatigue Hunting Ground #b(C - 3)#l\r\n";
                cm.sendSimpleS(selStr, 4);
                break;

        }

    } else if (status == 2) {
        ans_02 = selection;
        if (ans_01 == 5) {
            switch (ans_02) {
                case 6:
                    cm.dispose();
                    cm.openNpcCustom(cm.getClient(), 9062294, "JumpMapMove");
                    return;
                    break;
                case 2:
                    cm.dispose();
                    cm.getPlayer().send(Packages.network.models.CField.UIPacket.closeUI(62));
                    cm.warp(925020000, 0);
                    return;
                    break;
                case 3:
                    cm.dispose();
                    cm.openNpc(9000293);
                    return;
                    break;
                case 5:
                    cm.dispose();
                    cm.openNpc(9000219);
                    return;
                    break;
                case 4:
                    cm.dispose();
                    cm.warp(993174800, 0);
                    return;
                    break;
            }
        } else if (ans_01 == 1) {
            selStr = "";
            switch (ans_02) {
                case 1:
                    selStr += "#fs11#Check the #bamazing average monster level#n#k in the hunting ground and warp.#fs11#\r\n";
                    selStr += "#b#L" + (931000500 + randnumber) + "##b#fMap/MapHelper.img/minimap/anothertrader# Jaguar Habitat (Wild Hunter only)#l\r\n\r\n";
                    selStr += "#b#L" + (100010000 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.10  #dHenesys - North Hill#l\r\n";
                    selStr += "#b#L" + (103050340 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.30  #dVictoria Road - Training Center 2#l\r\n";
                    selStr += "#b#L" + (101030500 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.30  #dNorth Forest - End of Forest#l\r\n";
                    selStr += "#b#L" + (103020320 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.50  #dKerning City Subway- Line 1 Area 3#l\r\n";
                    selStr += "#b#L" + (102030000 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.55  #dBurnt Land - Wild Boar Land#l\r\n";
                    selStr += "#b#L" + (102040600 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.60  #dExcavation Site - Unapproachable Area#l\r\n";
                    selStr += "#b#L" + (105010000 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.70  #dSleepywood - Silent Swamp#l\r\n";
                    selStr += "#b#L" + (230040000 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.100  #dAqua Road - Deep Sea Gorge 1#l\r\n";
                    selStr += "#b#L" + (220020600 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.120  #dLudibrium Castle - Toy Factory<Machine Room>#l\r\n";
                    selStr += "#b#L" + (250020200 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.130  #dMu Lung Temple - Intermediate Training Center#l\r\n";
                    selStr += "#b#L" + (251010402 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.150  #dMu Lung Garden - Red Nose Pirate Den 2#l\r\n";
                    selStr += "#b#L" + (221030800 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.180  #dInside UFO - Cockpit 1#l\r\n";
                    selStr += "#b#L" + (273040300 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.195  #dTwilight Perion - Abandoned Excavation Area 4#l\r\n";
                    selStr += "#b#L" + (241000216 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.200  #dKingdom Road - Corrupted Magic Forest 1#l\r\n";
                    selStr += "#b#L" + (241000206 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.200  #dKingdom Road - Corrupted Magic Forest 2#l\r\n";
                    selStr += "#b#L" + (310070140 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.205  #dScrapyard - Scrapyard Hill 4#l\r\n";
                    selStr += "#b#L" + (241000226 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.210  #dKingdom Road - Corrupted Magic Forest 3#l\r\n";
                    selStr += "#b#L" + (310070210 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.220  #dSkyline - Skyline 1#l\r\n";
                    break;

                case 2:
                    starImg = "#fUI/UIWindow.img/ToolTip/WorldMap/StarForce#";
                    selStr += "#fs11##fc0xFF000000#The Fatigue Hunting Ground consumes #e#fc0xFFFF9436Fatigue#k#n after recovery via Fatigue Relief Potion. Unlike #b#enormal hunting grounds#fc0xFF000000##n, you can obtain #b#emany items #r[#z4031227#, #z4310266#, #z4001715#]#b and significant EXP here.\r\n\r\n#fc0xFF000000#You cannot use it if you exhaust all your fatigue.\r\n#rNote: Fatigue resets to 0 after midnight.\r\n\r\n";
                    selStr += "#b#L" + (261020700 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.220 #fc0xFFFF9436#Fatigue Hunting Ground 1 #b#l\r\n";
                    selStr += "#b#L" + (261010103 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.260 #fc0xFFFF9436#Fatigue Hunting Ground 2 #b#l\r\n";
                    //selStr += "#b#L" + (261010104 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.260 #fc0xFFFF9436#Fatigue Hunting Ground #b(Medium - 2)#l\r\n";
                    //selStr += "#b#L" + (261010105 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.260 #fc0xFFFF9436#Fatigue Hunting Ground #b(Medium - 3)#l\r\n";
                    break;

                case 3:
                    starImg = "#fUI/UIWindow.img/ToolTip/WorldMap/ArcaneForce#";
                    if (cm.getPlayer().getLevel() < 200) {
                        selStr += "#fs11##fc0xFF000000#This hunting ground is available from #bLevel 200#fc0xFF000000#.";
                        cm.sendNext(selStr);
                        cm.dispose();
                        return;
                    }
                    if (cm.getPlayer().getLevel() >= 200) {
                        selStr += "#fs11#Check #e#fc0xFF6799FF#Arcane Force#k#n and #bmonster average level#k#n before moving.\r\n";
                        selStr += "\r\n#e#r[Vanishing Journey]#d#n\r\n";
                        selStr += "#b#L" + (450001010 + randnumber) + "##fc0xFF6799FF#Lv.202#d | " + starImg + " 30 | Weathered Land of Joy#l\r\n";
                        selStr += "#b#L" + (450001012 + randnumber) + "##fc0xFF6799FF#Lv.202#d | " + starImg + " 30 | Weathered Land of Rage#l\r\n";
                        selStr += "#b#L" + (450001014 + randnumber) + "##fc0xFF6799FF#Lv.202#d | " + starImg + " 30 | Weathered Land of Sorrow#l\r\n";
                        selStr += "#b#L" + (450001214 + randnumber) + "##fc0xFF6799FF#Lv.207#d | " + starImg + " 60 | Eastern Cave Path 2#l\r\n";
                        selStr += "#b#L" + (450001216 + randnumber) + "##fc0xFF6799FF#Lv.207#d | " + starImg + " 60 | Below the Cave#l\r\n";
                        selStr += "#b#L" + (450001230 + randnumber) + "##fc0xFF6799FF#Lv.207#d | " + starImg + " 60 | Arma's Hideout#l\r\n";
                        selStr += "#b#L" + (450001260 + randnumber) + "##fc0xFF6799FF#Lv.209#d | " + starImg + " 80 | Hidden Lakeshore#l\r\n";
                        selStr += "#b#L" + (450001262 + randnumber) + "##fc0xFF6799FF#Lv.209#d | " + starImg + " 80 | Hidden Cave#l\r\n";
                        selStr += "\r\n\r\n#e#r[Reverse City]#d#n\r\n";
                        selStr += "#b#L" + (450014140 + randnumber) + "##fc0xFF6799FF#Lv.207#d | " + starImg + " 60 | Subway Tunnel 1#l\r\n";
                        selStr += "#b#L" + (450014310 + randnumber) + "##fc0xFF6799FF#Lv.209#d | " + starImg + " 100 | Hidden Subway Tunnel#l\r\n";
                        selStr += "#b#L" + (450014320 + randnumber) + "##fc0xFF6799FF#Lv.209#d | " + starImg + " 100 | Hidden M Tower#l\r\n";
                        selStr += "\r\n\r\n#e#r[Chu Chu & Yum Yum Island]#d#n\r\n";
                        selStr += "#b#L" + (450002001 + randnumber) + "##fc0xFF6799FF#Lv.210#d | " + starImg + " 100 | Five-Colored Garden#l\r\n";
                        selStr += "#b#L" + (450002006 + randnumber) + "##fc0xFF6799FF#Lv.212#d | " + starImg + " 100 | Slurpy Forest#l\r\n";
                        selStr += "#b#L" + (450002010 + randnumber) + "##fc0xFF6799FF#Lv.212#d | " + starImg + " 100 | Slurpy Forest Depths #l\r\n";
                        selStr += "#b#L" + (450002012 + randnumber) + "##fc0xFF6799FF#Lv.214#d | " + starImg + " 130 | Torrent Zone#l\r\n";
                        selStr += "#b#L" + (450002016 + randnumber) + "##fc0xFF6799FF#Lv.217#d | " + starImg + " 160 | Sky Whale Mountain#l\r\n";
                        selStr += "#b#L" + (450015290 + randnumber) + "##fc0xFF6799FF#Lv.225#d | " + starImg + " 190 | Yum Yum Island - Hidden Illiard Field#l\r\n";
                        selStr += "\r\n\r\n#e#r[Lachelein the Dreaming City]#d#n\r\n";
                        selStr += "#b#L" + (450003200 + randnumber) + "##fc0xFF6799FF#Lv.220#d | " + starImg + " 190 | Lachelein Back Alley - Outlaw's Street#l\r\n";
                        selStr += "#b#L" + (450003300 + randnumber) + "##fc0xFF6799FF#Lv.221#d | " + starImg + " 210 | Lachelein Night Market#l\r\n";
                        selStr += "#b#L" + (450003400 + randnumber) + "##fc0xFF6799FF#Lv.223#d | " + starImg + " 210 | Lachelein Ballroom#l\r\n";
                        selStr += "#b#L" + (450003440 + randnumber) + "##fc0xFF6799FF#Lv.225#d | " + starImg + " 210 | Lachelein Occupied Dance Floor#l\r\n";
                        selStr += "\r\n\r\n#e#r[Arcana the Spirit Forest]#d#n\r\n";
                        selStr += "#b#L" + (450005131 + randnumber) + "##fc0xFF6799FF#Lv.230#d | " + starImg + " 280 | Forest of Sunlight and Soil#l\r\n";
                        selStr += "#b#L" + (450005221 + randnumber) + "##fc0xFF6799FF#Lv.233#d | " + starImg + " 320 | Forest of Frost and Lightning 1#l\r\n";
                        selStr += "#b#L" + (450005230 + randnumber) + "##fc0xFF6799FF#Lv.235#d | " + starImg + " 320 | Forest of Toxins#l\r\n";
                        selStr += "#b#L" + (450005500 + randnumber) + "##fc0xFF6799FF#Lv.237#d | " + starImg + " 360 | Five-Forked Cave#l\r\n ";
                        selStr += "\r\n\r\n#e#r[Morass]#d#n\r\n";
                        selStr += "#b#L" + (450006010 + randnumber) + "##fc0xFF6799FF#Lv.236#d | " + starImg + " 400 | Path to Coral Forest#l\r\n";
                        selStr += "#b#L" + (450006140 + randnumber) + "##fc0xFF6799FF#Lv.238#d | " + starImg + " 440 | Bully Blvd#l\r\n";
                        selStr += "#b#L" + (450006210 + randnumber) + "##fc0xFF6799FF#Lv.239#d | " + starImg + " 480 | Shadow Dance Hall#l\r\n";
                        selStr += "#b#L" + (450006300 + randnumber) + "##fc0xFF6799FF#Lv.241#d | " + starImg + " 480 | Closed Area#l\r\n ";
                        selStr += "#b#L" + (450006410 + randnumber) + "##fc0xFF6799FF#Lv.245#d | " + starImg + " 480 | That Day in Trueffet#l\r\n ";
                        selStr += "\r\n\r\n#e#r[Esfera]#d#n\r\n";
                        selStr += "#b#L" + (450007010 + randnumber) + "##fc0xFF6799FF#Lv.240#d | " + starImg + " 560 | Where Life Begins 2#l\r\n";
                        selStr += "#b#L" + (450007050 + randnumber) + "##fc0xFF6799FF#Lv.242#d | " + starImg + " 560 | Where Life Begins 5#l\r\n";
                        selStr += "#b#L" + (450007110 + randnumber) + "##fc0xFF6799FF#Lv.244#d | " + starImg + " 600 | Mirror-touched Sea 2#l\r\n";
                        selStr += "#b#L" + (450007120 + randnumber) + "##fc0xFF6799FF#Lv.244#d | " + starImg + " 600 | Mirror-touched Sea 3#l\r\n";
                        selStr += "#b#L" + (450007210 + randnumber) + "##fc0xFF6799FF#Lv.248#d | " + starImg + " 640 | Radiant Temple#l\r\n";
                        selStr += "\r\n\r\n#e#r[Moonbridge]#d#n\r\n";
                        selStr += "#b#L" + (450009110 + randnumber) + "##fc0xFF6799FF#Lv.250#d | " + starImg + " 670 | Last Horizon#l\r\n";
                        selStr += "#b#L" + (450009210 + randnumber) + "##fc0xFF6799FF#Lv.252#d | " + starImg + " 700 | Mysterious Fog#l\r\n";
                        selStr += "#b#L" + (450009310 + randnumber) + "##fc0xFF6799FF#Lv.254#d | " + starImg + " 730 | Void Current#l\r\n";
                        selStr += "\r\n\r\n#e#r[Labyrinth of Suffering]#d#n\r\n";
                        selStr += "#b#L" + (450011420 + randnumber) + "##fc0xFF6799FF#Lv.255#d | " + starImg + " 760 | Labyrinth of Suffering Interior 1#l\r\n";
                        selStr += "#b#L" + (450011510 + randnumber) + "##fc0xFF6799FF#Lv.257#d | " + starImg + " 790 | Labyrinth of Suffering Core#l\r\n";
                        selStr += "#b#L" + (450011600 + randnumber) + "##fc0xFF6799FF#Lv.259#d | " + starImg + " 820 | Labyrinth of Suffering Deepest Part#l\r\n";
                        selStr += "\r\n\r\n#e#r[Limina]#d#n\r\n";
                        selStr += "#b#L" + (450012030 + randnumber) + "##fc0xFF6799FF#Lv.260#d | " + starImg + " 850 | Tears of the World Lower#l\r\n";
                        selStr += "#b#L" + (450012100 + randnumber) + "##fc0xFF6799FF#Lv.261#d | " + starImg + " 850 | Tears of the World Middle#l\r\n";
                        selStr += "#b#L" + (450012310 + randnumber) + "##fc0xFF6799FF#Lv.262#d | " + starImg + " 880 | End of the World 1-2#l\r\n";
                        //selStr += "#b#L" + (450012410 + randnumber) + "##fc0xFF6799FF#Lv.262#d "+starImg+" 880 End of the World 2-2#l\r\n";
                        //selStr += "#b#L" + (993072000 + randnumber) + "##fc0xFF6799FF#Lv.263#d "+starImg+" 880 Resistance Ship Deck#l\r\n";
                    }

                    break;

                case 4:
                    starImg = "#e#fUI/UIWindow.img/ToolTip/WorldMap/ArcaneForce#";
                    selStr += "#fs11# Only #bHunting Grounds#k#n matching your #fc0xFF6799FF#Level#k#n are shown.\r\nWhere would you like to go?\r\n";
                    selStr += "#fs11##bPromotion Coins drop int the hunting grounds below.#k\r\n\r\n";
                    if (cm.getPlayer().getLevel() >= 300) {
                        selStr += "#b#L" + (993163100 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.300  #dHidden Street - Tragic Wall 3#l\r\n";
                    }
                    if (cm.getPlayer().getLevel() >= 340) {
                        selStr += "#b#L" + (993162600 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.340  #dHidden Street - Residential Area Near Wall 2#l\r\n";
                    }
                    if (cm.getPlayer().getLevel() >= 400) {
                        selStr += "#b#L" + (940204490 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.400  #dMorass - Liberated Closed Area 1#l\r\n";
                    }
                    if (cm.getPlayer().getLevel() >= 440) {
                        selStr += "#b#L" + (940204510 + randnumber) + "##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.440  #dMorass - Liberated Closed Area 2#l\r\n";
                    }
                    break;

                case 5:
                    starImg = "#fUI/UIWindow.img/ToolTip/WorldMap/ArcaneForce#";
                    if (cm.getPlayer().getLevel() < 230) {
                        selStr += "#fs11##fc0xFF000000#This hunting ground is available from #bLevel 230#fc0xFF000000#.";
                        cm.sendNext(selStr);
                        cm.dispose();
                        return;
                    }
                    if (cm.getPlayer().getLevel() >= 230) {
                        selStr += "#fs11#Check #e#fc0xFF6799FF#Arcane Force#k#n and #bmonster average level#k#n before moving.\r\n";
                        selStr += "\r\n#e#r[Sellas Arcane Force 600]#d#n\r\n";
                        selStr += "#b#L" + (450016010 + randnumber) + "##fc0xFF6799FF#Lv.245#d | " + starImg + " 600  | Where Light Last Touches 1#l\r\n";
                        selStr += "#b#L" + (450016060 + randnumber) + "##fc0xFF6799FF#Lv.245#d | " + starImg + " 600  | Where Light Last Touches 6#l\r\n";
                        selStr += "\r\n#e#r[Sellas Arcane Force 640]#d#n\r\n";
                        selStr += "#b#L" + (450016130 + randnumber) + "##fc0xFF6799FF#Lv.250#d | " + starImg + " 640  | Endlessly Plunging Abyss 3#l\r\n";
                        selStr += "#b#L" + (450016140 + randnumber) + "##fc0xFF6799FF#Lv.250#d | " + starImg + " 640  | Endlessly Plunging Abyss 4#l\r\n";
                        selStr += "#b#L" + (450016150 + randnumber) + "##fc0xFF6799FF#Lv.250#d | " + starImg + " 640  | Endlessly Plunging Abyss 5#l\r\n";
                        selStr += "#b#L" + (450016160 + randnumber) + "##fc0xFF6799FF#Lv.250#d | " + starImg + " 640  | Endlessly Plunging Abyss 6#l\r\n";
                        selStr += "\r\n\r\n#e#r[Sellas Arcane Force 670]#d#n\r\n";
                        selStr += "#b#L" + (450016210 + randnumber) + "##fc0xFF6799FF#Lv.250#d | " + starImg + " 670  | Star-Swallowed Abyss 1#l\r\n";
                        selStr += "#b#L" + (450016220 + randnumber) + "##fc0xFF6799FF#Lv.250#d | " + starImg + " 670  | Star-Swallowed Abyss 2#l\r\n";
                        selStr += "#b#L" + (450016230 + randnumber) + "##fc0xFF6799FF#Lv.250#d | " + starImg + " 670  | Star-Swallowed Abyss 3#l\r\n";
                        selStr += "#b#L" + (450016240 + randnumber) + "##fc0xFF6799FF#Lv.250#d | " + starImg + " 670  | Star-Swallowed Abyss 4#l\r\n";
                        selStr += "#b#L" + (450016250 + randnumber) + "##fc0xFF6799FF#Lv.250#d | " + starImg + " 670  | Star-Swallowed Abyss 5#l\r\n";
                        selStr += "#b#L" + (450016260 + randnumber) + "##fc0xFF6799FF#Lv.250#d | " + starImg + " 670  | Star-Swallowed Abyss 6#l\r\n";
                    }
                    break;

                case 6:
                    starImg = "#fUI/UIWindow.img/ToolTip/WorldMap/AuthenticForce#";
                    if (cm.getPlayer().getLevel() < 260) {
                        selStr += "#fs11##fc0xFF000000#This hunting ground is available from #bLevel 260#fc0xFF000000#.";
                        cm.sendNext(selStr);
                        cm.dispose();
                        return;
                    }
                    if (cm.getPlayer().getLevel() >= 260) {
                        selStr += "#fs11#Check #e#fc0xFF3366FF#Authentic Force#k#n and #bmonster average level#k#n before moving.\r\n";
                        selStr += "\r\n#e#r[Cernium Authentic Force 30]#d#n\r\n";
                        selStr += "#b#L" + (450013160 + randnumber) + "##fc0xFF6799FF#Lv.261#d | " + starImg + " 30 | Beach Rocky Area 2#l\r\n";
                        selStr += "#b#L" + (450013030 + randnumber) + "##fc0xFF6799FF#Lv.261#d | " + starImg + " 30 | Western City Ramparts 2#l\r\n";
                        selStr += "#b#L" + (450013090 + randnumber) + "##fc0xFF6799FF#Lv.262#d | " + starImg + " 30 | Eastern City Ramparts 2#l\r\n";
                        selStr += "#b#L" + (450013120 + randnumber) + "##fc0xFF6799FF#Lv.264#d | " + starImg + " 30 | Royal Library Section 1#l\r\n";
                        selStr += "\r\n#e#r[Burning Cernium Authentic Force 50]#d#n\r\n";
                        selStr += "#b#L" + (450013330 + randnumber) + "##fc0xFF6799FF#Lv.266#d | " + starImg + " 50 | Battle-Ridden Western City Ramparts 2#l\r\n";
                        selStr += "#b#L" + (450013390 + randnumber) + "##fc0xFF6799FF#Lv.267#d | " + starImg + " 50 | Battle-Ridden Eastern City Ramparts 2#l\r\n";
                        selStr += "#b#L" + (450013440 + randnumber) + "##fc0xFF6799FF#Lv.269#d | " + starImg + " 50 | Burning Royal Library Section 1#l\r\n";
                        //selStr += "#b#L" + (450013470 + randnumber) + "##fc0xFF6799FF#Lv.269#d "+starImg+" 50 Burning Royal Library Section 4#l\r\n";
                    }
                    break;

                case 7:
                    starImg = "#fUI/UIWindow.img/ToolTip/WorldMap/AuthenticForce#";
                    if (cm.getPlayer().getLevel() < 270) {
                        selStr += "#fs11##fc0xFF000000#This hunting ground is available from #bLevel 270#fc0xFF000000#.";
                        cm.sendNext(selStr);
                        cm.dispose();
                        return;
                    }
                    if (cm.getPlayer().getLevel() >= 270) {
                        selStr += "#fs11#Check #e#fc0xFF3366FF#Authentic Force#k#n and #bmonster average level#k#n before moving.\r\n";
                        selStr += "\r\n#e#r[Arcus Authentic Force 130]#d#n\r\n";
                        selStr += "#b#L" + (450016420 + randnumber) + "##fc0xFF6799FF#Lv.271#d | " + starImg + " 130 | Outlaw-Infested Wastes 2#l\r\n";
                        selStr += "#b#L" + (450016440 + randnumber) + "##fc0xFF6799FF#Lv.271#d | " + starImg + " 130 | Outlaw-Infested Wastes 4#l\r\n";
                        selStr += "\r\n#e#r[Arcus Authentic Force 160]#d#n\r\n";
                        selStr += "#b#L" + (450016510 + randnumber) + "##fc0xFF6799FF#Lv.273#d | " + starImg + " 160 | Romantic Drive-in Theater 2#l\r\n";
                        selStr += "#b#L" + (450016550 + randnumber) + "##fc0xFF6799FF#Lv.273#d | " + starImg + " 160 | Romantic Drive-in Theater 6#l\r\n";
                        selStr += "\r\n#e#r[Arcus Authentic Force 200]#d#n\r\n";
                        selStr += "#b#L" + (450016660 + randnumber) + "##fc0xFF6799FF#Lv.279#d | " + starImg + " 200 | Train with No Destination 6#l\r\n";
                        //selStr += "#b#L" + (410000670 + randnumber) + "##fc0xFF6799FF#Lv.279#d "+starImg+" 200 Train with No Destination 7#l\r\n";
                    }
                    break;

                case 8:
                    starImg = "#fUI/UIWindow.img/ToolTip/WorldMap/AuthenticForce#";
                    if (cm.getPlayer().getLevel() < 275) {
                        selStr += "#fs11##fc0xFF000000#This hunting ground is available from #bLevel 275#fc0xFF000000#.";
                        cm.sendNext(selStr);
                        cm.dispose();
                        return;
                    }
                    if (cm.getPlayer().getLevel() >= 260) {
                        selStr += "#fs11#Check #e#fc0xFF3366FF#Authentic Force#k#n and #bmonster average level#k#n before moving.\r\n";
                        selStr += "\r\n#e#r[Odium Authentic Force 230]#d#n\r\n";
                        selStr += "#b#L" + (450017006 + randnumber) + "##fc0xFF6799FF#Lv.276#d | " + starImg + " 230 | Path to the City Gates 1#l\r\n";
                        selStr += "#b#L" + (450017010 + randnumber) + "##fc0xFF6799FF#Lv.276#d | " + starImg + " 230 | Path to the City Gates 3#l\r\n";
                        selStr += "\r\n#e#r[Odium Authentic Force 260]#d#n\r\n";
                        selStr += "#b#L" + (450017120 + randnumber) + "##fc0xFF6799FF#Lv.277#d | " + starImg + " 260 | Occupied Alley 2#l\r\n";
                        selStr += "#b#L" + (450017160 + randnumber) + "##fc0xFF6799FF#Lv.277#d | " + starImg + " 260 | Occupied Alley 4#l\r\n";
                        selStr += "\r\n#e#r[Odium Authentic Force 280]#d#n\r\n";
                        selStr += "#b#L" + (450017210 + randnumber) + "##fc0xFF6799FF#Lv.278#d | " + starImg + " 280 | Sunny Laboratory 1#l\r\n";
                        selStr += "#b#L" + (450017230 + randnumber) + "##fc0xFF6799FF#Lv.278#d | " + starImg + " 280 | Sunny Laboratory 2#l\r\n";
                        selStr += "\r\n#e#r[Odium Authentic Force 300]#d#n\r\n";
                        selStr += "#b#L" + (450017260 + randnumber) + "##fc0xFF6799FF#Lv.279#d | " + starImg + " 300 | Laboratory Behind the Locked Door 1#l\r\n";
                        selStr += "#b#L" + (450017280 + randnumber) + "##fc0xFF6799FF#Lv.279#d | " + starImg + " 300 | Laboratory Behind the Locked Door 2#l\r\n";
                    }
                    break;

                case 9:
                    starImg = "#fUI/UIWindow.img/ToolTip/WorldMap/AuthenticForce#";
                    if (cm.getPlayer().getLevel() < 280) {
                        selStr += "#fs11##fc0xFF000000#This hunting ground is available from #bLevel 280#fc0xFF000000#.";
                        cm.sendNext(selStr);
                        cm.dispose();
                        return;
                    }
                    if (cm.getPlayer().getLevel() >= 260) {
                        selStr += "#fs11#Check #e#fc0xFF3366FF#Authentic Force#k#n and #bmonster average level#k#n before moving.\r\n";
                        selStr += "\r\n#e#r[Shangri-La Authentic Force 330]#d#n\r\n";
                        selStr += "#b#L" + (450018110 + randnumber) + "##fc0xFF6799FF#Lv.281#d | " + starImg + " 330 | Spring of Returning Vitality 1#l\r\n";
                        selStr += "#b#L" + (450018140 + randnumber) + "##fc0xFF6799FF#Lv.282#d | " + starImg + " 330 | Spring of Returning Vitality 4#l\r\n";
                        selStr += "\r\n#e#r[Shangri-La Authentic Force 360]#d#n\r\n";
                        selStr += "#b#L" + (450018220 + randnumber) + "##fc0xFF6799FF#Lv.283#d | " + starImg + " 360 | Summer of Dim Light 2#l\r\n";
                        selStr += "#b#L" + (450018240 + randnumber) + "##fc0xFF6799FF#Lv.284#d | " + starImg + " 360 | Summer of Dim Light 4#l\r\n";
                        selStr += "\r\n#e#r[Shangri-La Authentic Force 390]#d#n\r\n";
                        selStr += "#b#L" + (450018320 + randnumber) + "##fc0xFF6799FF#Lv.285#d | " + starImg + " 390 | Autumn of Pale Colors 2#l\r\n";
                        selStr += "#b#L" + (450018330 + randnumber) + "##fc0xFF6799FF#Lv.286#d | " + starImg + " 390 | Autumn of Pale Colors 3#l\r\n";
                        selStr += "\r\n#e#r[Shangri-La Authentic Force 420]#d#n\r\n";
                        selStr += "#b#L" + (450018440 + randnumber) + "##fc0xFF6799FF#Lv.288#d | " + starImg + " 420 | Winter of Cruel Traces 4#l\r\n";
                        selStr += "#b#L" + (450018450 + randnumber) + "##fc0xFF6799FF#Lv.289#d | " + starImg + " 420 | Winter of Cruel Traces 5#l\r\n";
                    }
                    break;


                case 10:
                    starImg = "#fUI/UIWindow.img/ToolTip/WorldMap/AuthenticForce#";
                    if (cm.getPlayer().getLevel() < 280) {
                        selStr += "#fs11##fc0xFF000000#This hunting ground is available from #bLevel 280#fc0xFF000000#.";
                        cm.sendNext(selStr);
                        cm.dispose();
                        return;
                    }
                    if (cm.getPlayer().getLevel() >= 280) {
                        selStr += "#fs11#Check #e#fc0xFF3366FF#Authentic Force#k#n and #bmonster average level#k#n before moving.\r\n";
                        selStr += "\r\n#e#r[Arteria Authentic Force 360]#d#n\r\n";
                        selStr += "#b#L" + (410007527 + randnumber) + "##fc0xFF6799FF#Lv.275#d | " + starImg + " 360 | Lowest Floor Passage 1#l\r\n";
                        selStr += "#b#L" + (410007528 + randnumber) + "##fc0xFF6799FF#Lv.275#d | " + starImg + " 360 | Lowest Floor Passage 2#l\r\n";
                        selStr += "#b#L" + (410007529 + randnumber) + "##fc0xFF6799FF#Lv.275#d | " + starImg + " 360 | Lowest Floor Passage 3#l\r\n";
                        selStr += "#b#L" + (410007548 + randnumber) + "##fc0xFF6799FF#Lv.275#d | " + starImg + " 360 | Lowest Floor Passage 4#l\r\n";
                        selStr += "#b#L" + (410007549 + randnumber) + "##fc0xFF6799FF#Lv.275#d | " + starImg + " 360 | Lowest Floor Passage 5#l\r\n";
                        selStr += "#b#L" + (410007530 + randnumber) + "##fc0xFF6799FF#Lv.275#d | " + starImg + " 360 | Lowest Floor Passage 6#l\r\n";
                        selStr += "\r\n#e#r[Arteria Authentic Force 400]#d#n\r\n";
                        selStr += "#b#L" + (410007537 + randnumber) + "##fc0xFF6799FF#Lv.276#d | " + starImg + " 400 | Highest Floor Passage 1#l\r\n";
                        selStr += "#b#L" + (410007538 + randnumber) + "##fc0xFF6799FF#Lv.276#d | " + starImg + " 400 | Highest Floor Passage 2#l\r\n";
                        selStr += "#b#L" + (410007539 + randnumber) + "##fc0xFF6799FF#Lv.276#d | " + starImg + " 400 | Highest Floor Passage 3#l\r\n";
                        selStr += "#b#L" + (410007550 + randnumber) + "##fc0xFF6799FF#Lv.276#d | " + starImg + " 400 | Highest Floor Passage 4#l\r\n";
                        selStr += "#b#L" + (410007551 + randnumber) + "##fc0xFF6799FF#Lv.276#d | " + starImg + " 400 | Highest Floor Passage 5#l\r\n";
                        selStr += "#b#L" + (410007552 + randnumber) + "##fc0xFF6799FF#Lv.276#d | " + starImg + " 400 | Highest Floor Passage 6#l\r\n";
                        selStr += "#b#L" + (410007553 + randnumber) + "##fc0xFF6799FF#Lv.276#d | " + starImg + " 400 | Highest Floor Passage 7#l\r\n";
                        selStr += "#b#L" + (410007540 + randnumber) + "##fc0xFF6799FF#Lv.276#d | " + starImg + " 400 | Highest Floor Passage 8#l\r\n";
                    }
                    break;


                case 11:
                    starImg = "#fUI/UIWindow.img/ToolTip/WorldMap/AuthenticForce#";
                    selStr += "#fs11#Check #e#fc0xFF6799FF#Party Dungeon Hot Time#k#n and #bParty Dungeon#k#n before moving.\r\n";
                    selStr += "\r\n#e#r[Party Dungeon]#d#n\r\n";
                    selStr += "#b#L" + (993072000 + randnumber) + "##fc0xFF6799FF#Lv.200#d | " + starImg + " 0  | Party Beginner Dungeon 1#l\r\n";
                    selStr += "#b#L" + (993072100 + randnumber) + "##fc0xFF6799FF#Lv.200#d | " + starImg + " 0  | Party Intermediate Dungeon #l\r\n";
                    selStr += "#b#L" + (993072200 + randnumber) + "##fc0xFF6799FF#Lv.200#d | " + starImg + " 0  | Party Advanced Dungeon #l\r\n";
                    //selStr += "#L" + (940204350 + randnumber) + "##fc0xFF6799FF#Lv.200#d | "+starImg+" 0  | 핫타임던전 4 #l\r\n";
                    //selStr += "#L" + (940204530 + randnumber) + "##fc0xFF6799FF#Lv.200#d | "+starImg+" 0  | 핫타임던전 5 #l\r\n";
                    //selStr += "#L" + (993134200 + randnumber) + "##fc0xFF6799FF#Lv.200#d | "+starImg+" 0  | 핫타임던전 6 #l\r\n";

                    break;
            }
            cm.sendSimpleS(selStr, 4);
        } else if (ans_01 == 4) { // Event Map
            switch (ans_02) {
                case 1:
                    cm.dispose();
                    cm.sendOkS("#fs11#Seems to be coming soon..", 2);
                    break;

                case 2:
                    cm.warp(102, 0);
                    cm.dispose();
                    break;

            }
        } else if (ans_01 == 999) {
            cm.getPlayer().dropMessage(5, "EXP is not shared in party play in this Fatigue Hunting Ground.");
            // Get hours and minutes
            today = new Date();
            hours = today.getHours();
            minutes = today.getMinutes();

            // Maintenance Notice
            if (hours <= 0 && minutes <= 5) {
                cm.sendOk("#fs11##r#e[Entry Not Allowed]#k#n\r\nDaily 00:00 ~ 00:05\r\nEntry to Fatigue Hunting Grounds is not possible during this time.");
                cm.dispose();
                return;
            }

            if (cm.getParty() != null) {
                if (cm.getPartyMembers().size() < 3) {
                    cm.warp(ans_02, "sp");
                    cm.dispose();
                } else {
                    cm.dispose();
                    cm.sendOk("#fs11#Parties of 3 or more cannot enter.");
                }
            } else {
                cm.warp(ans_02, "sp");
                cm.dispose();
            }
            return;
        }
    } else if (status == 3) {
        mapcode = selection - randnumber;
        switch (ans_02) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                cm.warp(mapcode, "sp");
                cm.dispose();
                break;

            // Fatigue
            case 2:
                // Get hours and minutes
                today = new Date();
                hours = today.getHours();
                minutes = today.getMinutes();

                // Maintenance Notice
                if (hours <= 0 && minutes <= 5) {
                    cm.sendOk("#fs11##r#e[Entry Not Allowed]#k#n\r\nDaily 00:00 ~ 00:05\r\nEntry to Fatigue Hunting Grounds is not possible during this time.");
                    cm.dispose();
                    return;
                }

                if (cm.getParty() != null) {
                    if (cm.getPartyMembers().size() < 3) {
                        cm.warp(mapcode, "sp");
                        cm.dispose();
                    } else {
                        cm.dispose();
                        cm.sendOk("#fs11#Parties of 3 or more cannot enter.");
                    }
                } else {
                    cm.warp(mapcode, "sp");
                    cm.dispose();
                }
                break;
        }
    }
}