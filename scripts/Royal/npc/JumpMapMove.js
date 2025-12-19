h = "#fUI/UIMiniGame.img/starPlanetRPS/heart#";
g = "#fMap/MapHelper.img/minimap/anothertrader#";
a = "#i3801317#"
b = "#i3801313#"
c = "#i3801314#"
d = "#i3801315#"
p = "#fc0xFFF781D8#"

mTown = ["SixPath", "Henesys", "Ellinia", "Perion", "KerningCity",
    "Rith", "Dungeon", "Nautilus", "Ereb", "Rien",
    "Orbis", "ElNath", "Ludibrium", "Folkvillige", "AquaRoad",
    "Leafre", "Murueng", "WhiteHerb", "Ariant", "Magatia",
    "Edelstein", "Eurel", "critias", "Haven", "Road of Vanishing", "ChewChew", "Lacheln", "Arcana", "Morass", "esfera", "aliance", "moonBridge", "TheLabyrinthOfSuffering", "Limen"];

cTown = [104020000, 120040000, 101000000, 102000000, 103000000,
    104000000, 105000000, 120000000, 130000000, 140000000,
    200000000, 211000000, 220000000, 224000000, 230000000,
    240000000, 250000000, 251000000, 260000000, 261000000,
    310000000, 101050000, 241000000, 310070000, 450001000, 450002000, 450003000, 450005000, 450006130, 450007040, 450009050, 450009100, 450011500, 450012300];

var status = -1;

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
        status--;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        var msg = "#fs11##fc0xFF000000#คุณต้องการย้ายไปยังแผนที่กระโดดไหน?#k\r\n\r\n";
        msg += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#k";
        msg += "#L500# #fMap/MapHelper.img/minimap/party##fc0xFF0066CC# Henesys - Pet Walkway#fc0xFF000000# ย้ายไปยังพื้นที่ล่า#l\r\n";
        //msg += "#L501# #fMap/MapHelper.img/minimap/party##fc0xFF0066CC# Henesys - Pet Walkway#fc0xFF000000# ย้ายไปยังพื้นที่ล่า#l\r\n";
        //msg += "#L502# #fMap/MapHelper.img/minimap/party##fc0xFF0066CC# Henesys - Pet Walkway#fc0xFF000000# ย้ายไปยังพื้นที่ล่า#l\r\n";
        //msg += "#L503# #fMap/MapHelper.img/minimap/party##fc0xFF0066CC# Henesys - Pet Walkway#fc0xFF000000# ย้ายไปยังพื้นที่ล่า#l\r\n";
        msg += "\r\n#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#k";
        cm.sendSimple(msg);
    } else if (status == 1) {
        ans_01 = selection;
        selStr = "";
        switch (ans_01) {
            // Jump Map here
            case 500:
                cm.dispose();
                cm.warp(100000202, 0);
                break;

            case 4:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 9000213, "BossMove");
                break;

            case 10:
                cm.dispose();
                cm.warp(3000400, 0);
                return;

            case 11:
                cm.dispose();
                cm.getPlayer().send(Packages.network.models.CField.UIPacket.closeUI(62));
                cm.warp(925020000, 0);
                return;

            case 12:
                cm.dispose();
                cm.openNpc(9071003);
                return;

            case 13:
                cm.dispose();
                cm.openNpc(9020011);
                return;

        }
        if (ans_01 == 2 || ans_01 == 3)
            cm.sendSimpleS(selStr, 4);
    }

    else if (status == 2) {
        ans_02 = selection;
        if (ans_01 == 3) {
            switch (ans_02) {
                case 6:
                    cm.dispose();
                    cm.warp(106030800, 0);
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
        } else {
            selStr = "";
            switch (ans_02) {
                case 1:
                    selStr += "#fs11#\r\nโปรดตรวจสอบ #bเลเวลเฉลี่ยของมอนสเตอร์#n#k ที่ปรากฏในพื้นที่ล่าก่อนย้าย#fs11#\r\n\r\n";
                    selStr += "#b#L931000500##b#fMap/MapHelper.img/minimap/anothertrader# Jaguar Habitat (สำหรับ Wild Hunter เท่านั้น)#l\r\n\r\n";
                    //selStr += "#r#L993134100#Lv.15~270  │ Union 0 Tier Beginner Hunting Ground          │ Union 0 Tier Hunting Ground#l\r\n";
                    //selStr += "#r#L993134150#Lv.15~270  │ Union 0 Tier Beginner Hunting Ground          │ Union 0 Tier Hunting Ground#l\r\n";
                    selStr += "#b#L100010000##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.10  #dHenesys - North Hill#l\r\n";
                    selStr += "#b#L103050340##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.30  #dVictoria Road - Training Center 2#l\r\n";
                    selStr += "#b#L101030500##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.30  #dNorth Forest - End of the Forest#l\r\n";
                    selStr += "#b#L103020320##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.50  #dKerning City Subway- Line 1 Area 3#l\r\n";
                    selStr += "#b#L102030000##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.55  #dBurnt Land - Wild Boar Land#l\r\n";
                    selStr += "#b#L102040600##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.60  #dExcavation Site - Unapproachable Area#l\r\n";
                    selStr += "#b#L105010000##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.70  #dSleepywood - Silent Swamp#l\r\n";
                    selStr += "#b#L230040000##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.100  #dAqua Road - Deep Sea Gorge 1#l\r\n";
                    selStr += "#b#L220020600##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.120  #dLudibrium Castle - Toy Factory<Machine Room>#l\r\n";
                    selStr += "#b#L250020200##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.130  #dMu Lung Temple - Intermediate Training Ground#l\r\n";
                    selStr += "#b#L251010402##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.150  #dMu Lung Garden - Red Nose Pirate Den 2#l\r\n";
                    selStr += "#b#L221030800##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.180  #dInside UFO - Cockpit 1#l\r\n";
                    selStr += "#b#L273040300##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.195  #dTwilight Perion - Abandoned Excavation Area 4#l\r\n";
                    selStr += "#b#L241000216##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.200  #dKingdom Road - Corrupted Magic Forest 1#l\r\n";
                    selStr += "#b#L241000206##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.200  #dKingdom Road - Corrupted Magic Forest 2#l\r\n";
                    selStr += "#b#L310070140##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.205  #dScrapyard - Scrapyard Hill 4#l\r\n";
                    selStr += "#b#L241000226##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.210  #dKingdom Road - Corrupted Magic Forest 3#l\r\n";
                    selStr += "#b#L310070210##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.220  #dSkyline - Skyline 1#l\r\n";
                    //selStr += "#d#L310070300#Lv.218 │ Black Heaven Deck     │ Black Heaven#l\r\n";
                    //selStr += "#r#L105300301#Lv.226 │ Corrupted World Tree     │ Upper Stem Fork#l\r\n ";
                    break;

                case 2:
                    starImg = "#fUI/UIWindow.img/ToolTip/WorldMap/StarForce#";
                    selStr += "#fs11#\r\n#fc0xFF000000#พื้นที่ล่า Fatigue จะใช้ #e#fc0xFFFF9436ค่าความเหนื่อยหลังจากฟื้นฟูด้วยยาฟื้นฟูความเหนื่อย#k#n และแตกต่างจาก #b#eพื้นที่ล่าทั่วไป#fc0xFF000000##n โดยสามารถ #b#eรับไอเทมจำนวนมาก #r[#z4031227#, #z4000916#, #z4310266#, #z4001209#]#b#fc0xFF000000##nได้\r\n\r\n#fc0xFF000000#นอกจากนี้ คุณจะไม่สามารถเข้าใช้งานได้หากค่าความเหนื่อยหมด\r\n#rคำเตือน : ค่าความเหนื่อยจะรีเซ็ตเป็น 0 หลังเที่ยงคืน\r\n\r\n";
                    selStr += "#b#L261020700##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.220 #fc0xFFFF9436#Alcadno Research Institute - Lab Area A-3 #b(Low Grade)#l\r\n";
                    selStr += "#b#L261010103##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.260 #fc0xFFFF9436#Zenumist Research Institute - Lab 203 #b(Mid Grade)#l\r\n";
                    break;
                // Jump Map here
                case 4:
                    cm.dispose();
                    cm.warp(100000202, 0);
                    break;

                case 5:
                    starImg = "#fUI/UIWindow.img/ToolTip/WorldMap/ArcaneForce#";
                    if (cm.getPlayer().getLevel() < 230) {
                        selStr += "#fs11##fc0xFF000000#พื้นที่ล่านี้สามารถเข้าได้ตั้งแต่ #bเลเวล 230#fc0xFF000000# ขึ้นไป";
                        cm.dispose();
                    }
                    if (cm.getPlayer().getLevel() >= 230) {
                        selStr += "#fs11#\r\nโปรดตรวจสอบ #e#fc0xFF6799FF#Arcane Force#k#n และ #bเลเวลเฉลี่ยของมอนสเตอร์#k#n ในพื้นที่ล่า\r\nก่อนทำการย้าย\r\n#fs11#";
                        selStr += "\r\n#e#r[Sellas Arcane Force 600]#d#n\r\n";
                        selStr += "#L450016010##fc0xFF6799FF#Lv.245#d | " + starImg + " 600  | Where Light Last Touches 1#l\r\n";
                        selStr += "#L450016060##fc0xFF6799FF#Lv.245#d | " + starImg + " 600  | Where Light Last Touches 6#l\r\n";
                        selStr += "\r\n#e#r[Sellas Arcane Force 640]#d#n\r\n";
                        selStr += "#L450016130##fc0xFF6799FF#Lv.250#d | " + starImg + " 640  | Endlessly Plunging Deep Sea 3#l\r\n";
                        selStr += "#L450016140##fc0xFF6799FF#Lv.250#d | " + starImg + " 640  | Endlessly Plunging Deep Sea 4#l\r\n";
                        selStr += "#L450016150##fc0xFF6799FF#Lv.250#d | " + starImg + " 640  | Endlessly Plunging Deep Sea 5#l\r\n";
                        selStr += "#L450016160##fc0xFF6799FF#Lv.250#d | " + starImg + " 640  | Endlessly Plunging Deep Sea 6#l\r\n";
                        selStr += "\r\n\r\n#e#r[Sellas Arcane Force 670]#d#n\r\n";
                        //selStr += "#L100030301##fc0xFF6799FF#Lv.0#d | "+starImg+" 0  | Item Exchange Map#l\r\n";
                        selStr += "#L450016210##fc0xFF6799FF#Lv.250#d | " + starImg + " 670  | Star-Swallowed Deep Sea 1#l\r\n";
                        selStr += "#L450016220##fc0xFF6799FF#Lv.250#d | " + starImg + " 670  | Star-Swallowed Deep Sea 2#l\r\n";
                        selStr += "#L450016230##fc0xFF6799FF#Lv.250#d | " + starImg + " 670  | Star-Swallowed Deep Sea 3#l\r\n";
                        selStr += "#L450016240##fc0xFF6799FF#Lv.250#d | " + starImg + " 670  | Star-Swallowed Deep Sea 4#l\r\n";
                        selStr += "#L450016250##fc0xFF6799FF#Lv.250#d | " + starImg + " 670  | Star-Swallowed Deep Sea 5#l\r\n";
                        selStr += "#L450016260##fc0xFF6799FF#Lv.250#d | " + starImg + " 670  | Star-Swallowed Deep Sea 6#l\r\n";
                    }
                    break;

                case 6:
                    starImg = "#fUI/UIWindow.img/ToolTip/WorldMap/AuthenticForce#";
                    if (cm.getPlayer().getLevel() < 260) {
                        selStr += "#fs11##fc0xFF000000#พื้นที่ล่านี้สามารถเข้าได้ตั้งแต่ #bเลเวล 260#fc0xFF000000# ขึ้นไป";
                        cm.dispose();
                    }
                    if (cm.getPlayer().getLevel() >= 260) {
                        selStr += "#fs11#\r\nโปรดตรวจสอบ #e#fc0xFF6799FF#Authentic Force#k#n และ #bเลเวลเฉลี่ยของมอนสเตอร์#k#n ในพื้นที่ล่า\r\nก่อนทำการย้าย\r\n#fs11#";
                        selStr += "\r\n#e#r[Cernium Authentic Force 50]#d#n\r\n";
                        selStr += "#L410000520##fc0xFF6799FF#Lv.260#d | " + starImg + " 50  | Beach Rocky Area 1#l\r\n";
                        selStr += "#L410000530##fc0xFF6799FF#Lv.260#d | " + starImg + " 50  | Beach Rocky Area 2#l\r\n";
                        selStr += "#L410000540##fc0xFF6799FF#Lv.260#d | " + starImg + " 50  | Beach Rocky Area 3#l\r\n";
                        selStr += "#L410000550##fc0xFF6799FF#Lv.260#d | " + starImg + " 50  | Beach Rocky Area 4#l\r\n";
                        selStr += "\r\n\r\n#e#r[Cernium Authentic Force 50]#d#n\r\n";
                        selStr += "#L410000590##fc0xFF6799FF#Lv.261#d | " + starImg + " 50  | Western City Wall 1#l\r\n";
                        selStr += "#L410000600##fc0xFF6799FF#Lv.261#d | " + starImg + " 50  | Western City Wall 2#l\r\n";
                        selStr += "#L410000610##fc0xFF6799FF#Lv.261#d | " + starImg + " 50  | Western City Wall 3#l\r\n";
                        selStr += "\r\n\r\n#e#r[Cernium Authentic Force 50]#d#n\r\n";
                        selStr += "#L410000640##fc0xFF6799FF#Lv.262#d | " + starImg + " 50  | Eastern City Wall 1#l\r\n";
                        selStr += "#L410000650##fc0xFF6799FF#Lv.262#d | " + starImg + " 50  | Eastern City Wall 2#l\r\n";
                        selStr += "#L410000660##fc0xFF6799FF#Lv.262#d | " + starImg + " 50  | Eastern City Wall 3#l\r\n";
                        selStr += "\r\n\r\n#e#r[Cernium Authentic Force 50]#d#n\r\n";
                        selStr += "#L410000700##fc0xFF6799FF#Lv.263#d | " + starImg + " 50  | Royal Library Section 1#l\r\n";
                        selStr += "#L410000710##fc0xFF6799FF#Lv.263#d | " + starImg + " 50  | Royal Library Section 2#l\r\n";
                        selStr += "#L410000720##fc0xFF6799FF#Lv.263#d | " + starImg + " 50  | Royal Library Section 3#l\r\n";
                        selStr += "#L410000730##fc0xFF6799FF#Lv.263#d | " + starImg + " 50  | Royal Library Section 4#l\r\n";
                        selStr += "#L410000740##fc0xFF6799FF#Lv.263#d | " + starImg + " 50  | Royal Library Section 5#l\r\n";
                        selStr += "#L410000750##fc0xFF6799FF#Lv.263#d | " + starImg + " 50  | Royal Library Section 6#l\r\n";
                        selStr += "\r\n\r\n#e#r[Burning Cernium Authentic Force 70]#d#n\r\n";
                        selStr += "#L410000920##fc0xFF6799FF#Lv.265#d | " + starImg + " 70  | Battle-Hardened Western City Wall 1#l\r\n";
                        selStr += "#L410000930##fc0xFF6799FF#Lv.265#d | " + starImg + " 70  | Battle-Hardened Western City Wall 2#l\r\n";
                        selStr += "#L410000940##fc0xFF6799FF#Lv.265#d | " + starImg + " 70  | Battle-Hardened Western City Wall 3#l\r\n";
                        selStr += "#L410000950##fc0xFF6799FF#Lv.265#d | " + starImg + " 70  | Battle-Hardened Western City Wall 4#l\r\n";
                        selStr += "\r\n\r\n#e#r[Burning Cernium Authentic Force 100]#d#n\r\n";
                        selStr += "#L410000980##fc0xFF6799FF#Lv.266#d | " + starImg + " 100  | Battle-Hardened Eastern City Wall 1#l\r\n";
                        selStr += "#L410000990##fc0xFF6799FF#Lv.266#d | " + starImg + " 100  | Battle-Hardened Eastern City Wall 2#l\r\n";
                        selStr += "#L410001000##fc0xFF6799FF#Lv.266#d | " + starImg + " 100  | Battle-Hardened Eastern City Wall 3#l\r\n";
                        selStr += "#L410001010##fc0xFF6799FF#Lv.266#d | " + starImg + " 100  | Battle-Hardened Eastern City Wall 4#l\r\n";
                        selStr += "#L410001020##fc0xFF6799FF#Lv.266#d | " + starImg + " 100  | Battle-Hardened Eastern City Wall 5#l\r\n";
                        selStr += "#L410001030##fc0xFF6799FF#Lv.266#d | " + starImg + " 100  | Battle-Hardened Eastern City Wall 6#l\r\n";
                        selStr += "\r\n\r\n#e#r[Burning Cernium Authentic Force 100]#d#n\r\n";
                        selStr += "#L410000840##fc0xFF6799FF#Lv.268#d | " + starImg + " 100  | Burning Royal Library Section 1#l\r\n";
                        selStr += "#L410000850##fc0xFF6799FF#Lv.268#d | " + starImg + " 100  | Burning Royal Library Section 2#l\r\n";
                        selStr += "#L410000860##fc0xFF6799FF#Lv.268#d | " + starImg + " 100  | Burning Royal Library Section 3#l\r\n";
                        selStr += "#L410000870##fc0xFF6799FF#Lv.268#d | " + starImg + " 100  | Burning Royal Library Section 4#l\r\n";
                        selStr += "#L410000880##fc0xFF6799FF#Lv.268#d | " + starImg + " 100  | Burning Royal Library Section 5#l\r\n";
                        selStr += "#L410000890##fc0xFF6799FF#Lv.268#d | " + starImg + " 100  | Burning Royal Library Section 6#l\r\n";
                    }
                    break;

                case 8:
                    starImg = "#fUI/UIWindow.img/ToolTip/WorldMap/AuthenticForce#";
                    selStr += "#fs11#\r\nบอสระดับกลาง #e#fc0xFF6799FF#บอสระดับกลาง#k#n และ #e#bต้องมีดาเมจสูง#k#n \r\nโปรดตรวจสอบก่อนย้าย\r\n#fs11#";
                    selStr += "\r\n#e#r[ดาเมจเพียงพอหรือไม่?]#d#n\r\n";
                    selStr += "#L940202049##fc0xFF6799FF#Lv.200#d | " + starImg + " 0  | บอสระดับกลาง (Beginner) 1#l\r\n";
                    selStr += "#L940202050##fc0xFF6799FF#Lv.200#d | " + starImg + " 0  | บอสระดับกลาง (Intermediate)#l\r\n";
                    selStr += "#L940200210##fc0xFF6799FF#Lv.200#d | " + starImg + " 0  | บอสระดับสูง (Advanced) #l\r\n";

                    break;

                case 9:
                    starImg = "#fUI/UIWindow.img/ToolTip/WorldMap/AuthenticForce#";
                    selStr += "#fs11#\r\nHot Time Dungeon #e#fc0xFF6799FF#Hot Time Dungeon#k#n และ #e#bHot Time Dungeon#k#n \r\nโปรดตรวจสอบก่อนย้าย\r\n#fs11#";
                    selStr += "\r\n#e#r[Hot Time Dungeon]#d#n\r\n";
                    selStr += "#L993185110##fc0xFF6799FF#Lv.200#d | " + starImg + " 0  | Hot Time Dungeon 1#l\r\n";
                    selStr += "#L993185120##fc0xFF6799FF#Lv.200#d | " + starImg + " 0  | Hot Time Dungeon 2#l\r\n";
                    selStr += "#L993185130##fc0xFF6799FF#Lv.200#d | " + starImg + " 0  | Hot Time Dungeon 3 #l\r\n";
                    selStr += "#L940204350##fc0xFF6799FF#Lv.200#d | " + starImg + " 0  | Hot Time Dungeon 4 #l\r\n";
                    selStr += "#L940204530##fc0xFF6799FF#Lv.200#d | " + starImg + " 0  | Hot Time Dungeon 5 #l\r\n";
                    selStr += "#L993134200##fc0xFF6799FF#Lv.200#d | " + starImg + " 0  | Hot Time Dungeon 6 #l\r\n";

                    break;

                case 11:
                    starImg = "#fUI/UIWindow.img/ToolTip/WorldMap/AuthenticForce#";
                    selStr += "#fs11#\r\nParty Dungeon #e#fc0xFF6799FF#Hot Time Dungeon#k#n และ #e#bParty Dungeon#k#n \r\nตรวจตรวจสอบก่อนย้าย\r\n#fs11#";
                    selStr += "\r\n#e#r[Party Dungeon]#d#n\r\n";
                    selStr += "#L993072000##fc0xFF6799FF#Lv.200#d | " + starImg + " 0  | Party Beginner Dungeon 1#l\r\n";
                    selStr += "#L993072100##fc0xFF6799FF#Lv.200#d | " + starImg + " 0  | Party Intermediate #l\r\n";
                    selStr += "#L993072200##fc0xFF6799FF#Lv.200#d | " + starImg + " 0  | Party Advanced #l\r\n";
                    //selStr += "#L940204350##fc0xFF6799FF#Lv.200#d | "+starImg+" 0  | Hot Time Dungeon 4 #l\r\n";
                    //selStr += "#L940204530##fc0xFF6799FF#Lv.200#d | "+starImg+" 0  | Hot Time Dungeon 5 #l\r\n";
                    //selStr += "#L993134200##fc0xFF6799FF#Lv.200#d | "+starImg+" 0  | Hot Time Dungeon 6 #l\r\n";

                    break;

                case 7:
                    starImg = "#fUI/UIWindow.img/ToolTip/WorldMap/AuthenticForce#";
                    if (cm.getPlayer().getLevel() < 260) {
                        selStr += "#fs11##fc0xFF000000#พื้นที่ล่านี้สามารถเข้าได้ตั้งแต่ #bเลเวล 260#fc0xFF000000# ขึ้นไป\r\n";
                        cm.dispose();
                    }
                    if (cm.getPlayer().getLevel() >= 260) {
                        selStr += "#fs11#\r\nโปรดตรวจสอบ #e#fc0xFF6799FF#Authentic Force#k#n และ #e#bเลเวลเฉลี่ยของมอนสเตอร์#k#n ในพื้นที่ล่า\r\nก่อนทำการย้าย\r\n#fs11#";
                        selStr += "\r\n#e#r[Arcus Authentic Force 130]#d#n\r\n";
                        selStr += "#L410003040##fc0xFF6799FF#Lv.270#d | " + starImg + " 130  | Outlaw-Ruled Wilderness 1#l\r\n";
                        selStr += "#L410003050##fc0xFF6799FF#Lv.270#d | " + starImg + " 130  | Outlaw-Ruled Wilderness 2#l\r\n";
                        selStr += "#L410003060##fc0xFF6799FF#Lv.270#d | " + starImg + " 130  | Outlaw-Ruled Wilderness 3#l\r\n";
                        selStr += "#L410003070##fc0xFF6799FF#Lv.270#d | " + starImg + " 130  | Outlaw-Ruled Wilderness 4#l\r\n";
                        selStr += "\r\n#e#r[Arcus Authentic Force 160]#d#n\r\n";
                        selStr += "#L410003090##fc0xFF6799FF#Lv.271#d | " + starImg + " 160  | Romantic Drive-in Theater 1#l\r\n";
                        selStr += "#L410003100##fc0xFF6799FF#Lv.271#d | " + starImg + " 160  | Romantic Drive-in Theater 2#l\r\n";
                        selStr += "#L410003110##fc0xFF6799FF#Lv.271#d | " + starImg + " 160  | Romantic Drive-in Theater 3#l\r\n";
                        selStr += "#L410003120##fc0xFF6799FF#Lv.271#d | " + starImg + " 160  | Romantic Drive-in Theater 4#l\r\n";
                        selStr += "#L410003130##fc0xFF6799FF#Lv.271#d | " + starImg + " 160  | Romantic Drive-in Theater 5#l\r\n";
                        selStr += "#L410003140##fc0xFF6799FF#Lv.271#d | " + starImg + " 160  | Romantic Drive-in Theater 6#l\r\n";
                        selStr += "\r\n#e#r[Arcus Authentic Force 200]#d#n\r\n";
                        selStr += "#L410003150##fc0xFF6799FF#Lv.273#d | " + starImg + " 200  | Destination-less Crossing Train 1#l\r\n";
                        selStr += "#L410003160##fc0xFF6799FF#Lv.273#d | " + starImg + " 200  | Destination-less Crossing Train 2#l\r\n";
                        selStr += "#L410003170##fc0xFF6799FF#Lv.273#d | " + starImg + " 200  | Destination-less Crossing Train 3#l\r\n";
                        selStr += "#L410003180##fc0xFF6799FF#Lv.273#d | " + starImg + " 200  | Destination-less Crossing Train 4#l\r\n";
                        selStr += "#L410003190##fc0xFF6799FF#Lv.273#d | " + starImg + " 200  | Destination-less Crossing Train 5#l\r\n";
                        selStr += "#L410003200##fc0xFF6799FF#Lv.273#d | " + starImg + " 200  | Destination-less Crossing Train 6#l\r\n";
                    }
                    break;

                case 10:
                    starImg = "#fUI/UIWindow.img/ToolTip/WorldMap/AuthenticForce#";
                    selStr += "#fs11#\r\nโปรดตรวจสอบ #e#fc0xFF6799FF#NPC#k#n และ #e#bNPC#k#n \r\nก่อนทำการย้าย\r\n#fs11#";
                    selStr += "\r\n#e#r[NPC ทั้งหมดอยู่ที่นี่]#d#n\r\n";
                    selStr += "#L100030300##fc0xFF6799FF#Lv.0#d | " + starImg + " 0  | แผนที่ NPC#l\r\n";

                    break;

                case 4:
                    starImg = "#e#fUI/UIWindow.img/ToolTip/WorldMap/ArcaneForce#";
                    selStr += "#fs11#\r\nคุณจะเห็นเฉพาะรายการ #bพื้นที่ล่า#k#n ที่เหมาะสมกับ #fc0xFF6799FF#เลเวล#k#n เท่านั้น\r\nคุณต้องการย้ายไปที่ไหน?\r\n#fs11#";
                    selStr += "#fs11#\r\n#bเหรียญเลื่อนขั้นดรอปในพื้นที่ล่าด้านล่างนี้#k\r\n\r\n";
                    if (cm.getPlayer().getLevel() >= 300) {
                        selStr += "#L993163100##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.300  #dHidden Street - Tragic City Wall 3#l\r\n";
                    }
                    if (cm.getPlayer().getLevel() >= 340) {
                        selStr += "#L993162600##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.340  #dHidden Street - Residential Area Near Wall 2#l\r\n";
                    }
                    if (cm.getPlayer().getLevel() >= 400) {
                        selStr += "#L940204490##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.400  #dMorass - Liberated Closed Area 1#l\r\n";
                    }
                    if (cm.getPlayer().getLevel() >= 440) {
                        selStr += "#L940204510##fMap/MapHelper.img/minimap/anothertrader# #fc0xFF6799FF#Lv.440  #dMorass - Liberated Closed Area 2#l\r\n";
                    }
                    break;
            }
            cm.sendSimpleS(selStr, 4);
        }
    } else if (status == 3) {
        ans_03 = selection;
        switch (ans_02) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                cm.warp(ans_03, "sp");
                cm.dispose();
                break;
        }
    }
}
