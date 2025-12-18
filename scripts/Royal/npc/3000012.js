var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    }
    if (selection == -1) {
        cm.dispose();
        return;
    }
    if (status == 0) {
        cm.sendDimensionGate("#0# Six Path Crossway#1# Henesys#2# Ellinia#3# Perion#4# Kerning City#5# Lith Harbor#6# Sleepywood#7# Nautilus#8# Ereve#9# Rien#10# Orbis#11# El Nath#12# Ludibrium#13# Omega Sector#14# Korean Folk Town#15# Aquarium#16# Leafre#17# Mu Lung#18# Herb Town#19# Ariant#20# Magatia#21# Edelstein#22# Eurel#23# Kritias#24# Haven#25# Pantheon Grand Temple#26# Abandoned Camp");
    } else if (status == 1) {
        switch (selection) {
            case 0:
                cm.warp(104020000);
                break;
            case 1:
                cm.warp(100000000);
                break;
            case 2:
                cm.warp(101000000);
                break;
            case 3:
                cm.warp(102000000);
                break;
            case 4:
                cm.warp(103000000);
                break;
            case 5:
                cm.warp(104000000);
                break;
            case 6:
                cm.warp(105000000);
                break;
            case 7:
                cm.warp(120000000);
                break;
            case 8:
                cm.warp(130000000);
                break;
            case 9:
                cm.warp(140000000);
                break;
            case 10:
                cm.warp(200000000);
                break;
            case 11:
                cm.warp(211000000);
                break;
            case 12:
                cm.warp(220000000);
                break;
            case 13:
                cm.warp(221000000);
                break;
            case 14:
                cm.warp(224000000);
                break;
            case 15:
                cm.warp(230000000);
                break;
            case 16:
                cm.warp(240000000);
                break;
            case 17:
                cm.warp(250000000);
                break;
            case 18:
                cm.warp(251000000);
                break;
            case 19:
                cm.warp(260000000);
                break;
            case 20:
                cm.warp(261000000);
                break;
            case 21:
                cm.warp(310000000);
                break;
            case 22:
                cm.warp(101050000);
                break;
            case 23:
                cm.warp(241000100);
                break;
            case 24:
                cm.warp(310070000);
                break;
            case 25:
                cm.warp(400000001);
                break;
            case 26:
                cm.warp(105300000);
                break;
            case 27:
                cm.warp(940200502);
                break;
            case 28:
                cm.warp(450001000);
                break;
            case 29:
                cm.warp(450002000);
                break;
            case 30:
                cm.warp(450003000);
                break;
            case 31:
                cm.warp(940200420);
                break;
            case 32:
                cm.warp(450006130);
                break;
            case 33:
                cm.warp(450007040);
                break;
            case 34:
                cm.warp(993060020);
                break;
            case 35:
                cm.warp(450009300);
                break;
            case 36:
                cm.warp(450011220);
                break;
            case 37:
                cm.warp(450012300);
                break;
        }
        cm.dispose();
    }
}