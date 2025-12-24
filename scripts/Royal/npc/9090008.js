var invtype = 0;
var St = -1;
var rotation = 0;
var S1 = 0, S2 = 0, S3 = 0, S4 = 0;
var inz = null;
var txt = "";

function start() {
    St = -1;
    rotation = 0;
    S1 = 0; S2 = 0; S3 = 0; S4 = 0;
    action(1, 0, 0);
}

function send(i, str) {
    cm.getPlayer().dropMessage(6, str);
}

function Comma(i) {
    var reg = /(^[+-]?\d+)(\d{3})/;
    i += '';
    while (reg.test(i)) i = i.replace(reg, '$1' + ',' + '$2');
    return i;
}

function action(M, T, S) {
    if (M != 1) {
        cm.dispose();
        return;
    }

    if (M == 1) {
        St++;
    } else {
        St--;
    }

    if (St == 0) {
        if (!cm.getPlayer().isGM()) {
            cm.sendOk("สวัสดีครับ? สนุกกับการเดินทางใน Maple World ไหมครับ?");
            cm.dispose();
            return;
        }
        cm.sendSimple("สวัสดีครับ? สนุกกับการเดินทางใน Maple World ไหมครับ?\r\n"
            + "#L0##rจบการสนทนา#l\r\n"
            + "#L1##bเปลี่ยนออพชั่นไอเทม (สวมใส่)#l\r\n"
            + "#L3##bเปลี่ยนออพชั่นไอเทม (แคช)#l\r\n"
            + "#L2##bดูรหัสศักยภาพ (Potential Codes)#l");
    }

    else if (St == 1) {
        S1 = S;
        switch (S1) {
            case 1:
                inz = cm.getInventory(1);
                invtype = 1;
                txt = "นี่คือรายการไอเทมสวมใส่ที่ #b#h ##k มีอยู่ในปัจจุบัน เรียงตามลำดับในช่องเก็บของ กรุณาเลือก #rไอเทมที่ต้องการเปลี่ยนออพชั่น#k\r\n#b#fs11#";
                for (w = 0; w <= inz.getSlotLimit(); w++) {
                    if (!inz.getItem(w)) continue;
                    txt += "#L" + w + "##i" + inz.getItem(w).getItemId() + ":# #t" + inz.getItem(w).getItemId() + "##l\r\n";
                }
                cm.sendSimple(txt);
                break;
            case 3:
                inz = cm.getInventory(6);
                invtype = 6;
                txt = "นี่คือรายการไอเทมแคชที่ #b#h ##k มีอยู่ในปัจจุบัน เรียงตามลำดับในช่องเก็บของ กรุณาเลือก #rไอเทมที่ต้องการเปลี่ยนออพชั่น#k\r\n#b#fs11#";
                for (w = 0; w <= inz.getSlotLimit(); w++) {
                    if (!inz.getItem(w)) continue;
                    txt += "#L" + w + "##i" + inz.getItem(w).getItemId() + ":# #t" + inz.getItem(w).getItemId() + "##l\r\n";
                }
                cm.sendSimple(txt);
                break;
            case 2:
                showPotentialCode();
                break;
            default:
                cm.dispose();
                break;
        }
    }

    else if (St > 1) {
        if (rotation != -1) {
            switch (St) {
                case 2: S2 = S; break;
                case 3: S3 = S; break;
                case 4: S4 = S; break;
            }
            if (St == 4 && rotation == 2) {
                inz = cm.getInventory(invtype).getItem(S2);
                inz.setArc(3000);
                switch (S3) {
                    case 0: inz.setStr(S4); break;
                    case 1: inz.setDex(S4); break;
                    case 2: inz.setInt(S4); break;
                    case 3: inz.setLuk(S4); break;
                    case 4: inz.setHp(S4); break;
                    case 5: inz.setMp(S4); break;
                    case 6: inz.setWatk(S4); break;
                    case 7: inz.setMatk(S4); break;
                    case 8: inz.setWdef(S4); break;
                    case 9: inz.setMdef(S4); break;
                    case 10: inz.setAcc(S4); break;
                    case 11: inz.setAvoid(S4); break;
                    case 12: inz.setSpeed(S4); break;
                    case 13: inz.setJump(S4); break;
                    case 14: inz.setFire(S4); break;
                    case 15: inz.setEnchantStr(S4); break;
                    case 16: inz.setEnchantDex(S4); break;
                    case 17: inz.setEnchantInt(S4); break;
                    case 18: inz.setEnchantLuk(S4); break;
                    case 19: inz.setEnchantHp(S4); break;
                    case 20: inz.setEnchantMp(S4); break;
                    case 21: inz.setEnchantWatk(S4); break;
                    case 22: inz.setEnchantMatk(S4); break;
                    case 23: inz.setEnchantWdef(S4); break;
                    case 24: inz.setEnchantMdef(S4); break;
                    case 25: inz.setEnchantAcc(S4); break;
                    case 26: inz.setEnchantAvoid(S4); break;
                    case 27: inz.setLevel(S4); break;
                    case 28: inz.setUpgradeSlots(S4); break;
                    case 29: inz.setEnhance(S4); break;
                    case 30: inz.setAmazingequipscroll(true); break;
                    case 31: inz.setBossDamage(S4); break;
                    case 32: inz.setIgnorePDR(S4); break;
                    case 33: inz.setTotalDamage(S4); break;
                    case 34: inz.setAllStat(S4); break;
                    case 35: inz.setReqLevel(S4); break;
                    case 36: inz.setState(S4); break;
                    case 37: inz.setPotential1(S4); break;
                    case 38: inz.setPotential2(S4); break;
                    case 39: inz.setPotential3(S4); break;
                    case 40: inz.setPotential4(S4); break;
                    case 41: inz.setPotential5(S4); break;
                    case 42: inz.setPotential6(S4); break;
                    case 43: inz.setArcLevel(S4); break;
                    case 44: inz.setArc(S4); break;
                    case 45: inz.setArcEXP(S4); break;
                }
                if (invtype == 1) {
                    cm.getPlayer().forceReAddItem(inz, Packages.client.inventory.MapleInventoryType.EQUIP);
                } else if (invtype == 6) {
                    cm.getPlayer().forceReAddItem(inz, Packages.client.inventory.MapleInventoryType.CASH);
                } else {
                    cm.sendOk("เกิดข้อผิดพลาด");
                    cm.dispose();
                    return;
                }

                rotation = 0;
                St = 2;
            }
        } else {
            S2 = S2;
            rotation++;
        }
        addItemInfo();
    }
}

function showPotentialCode() {
    var list = [
        "　< รหัสศักยภาพ Main Stat% >",
        "　STR : +3%(10041)　STR : +6%(20041)　STR : +9%(30041)　STR : +12%(40041)",
        "　DEX : +3%(10042)　DEX : +6%(20042)　DEX : +9%(30042)　DEX : +12%(40042)",
        "　INT : +3%(10043)　INT : +6%(20043)　INT : +9%(30043)　INT : +12%(40043)",
        "　LUK : +3%(10044)　LUK : +6%(20044)　LUK : +9%(30044)　LUK : +12%(40044)",
        "　All Stat: +9%(40086)　　 All Stat: +12%(40081)　　 All Stat: +20%(60002)",
        "　< รหัสศักยภาพ Stat% อื่นๆ >",
        "　MaxHP: +3%(10045)　MaxHP: +6%(20045)　MaxHP: +9%(30045)　MaxHP: +12%(40045)",
        "　MaxMP: +3%(10046)　MaxMP: +6%(20046)　MaxMP: +9%(30046)　MaxMP: +12%(40046)",
        "　Avoid: +3%(10048)　Avoid: +6%(20048)　Avoid: +9%(30048)　Avoid: +12%(40048)",
        "　< รหัสศักยภาพอาวุธ >",
        "　Damage: +6%(20070)　Damage: +9%(30070)　Damage: +12%(40070)",
        "　Attack: +6%(20051)　Attack: +9%(30051)　Attack: +12%(40051)",
        "　Magic Attack: +6%(20052)　Magic Attack: +9%(30052)　Magic Attack: +12%(40052)",
        "　< รหัสศักยภาพเจาะเกราะ (IED) >",
        "　+15%(10291)　+20%(20291)　+30%(30291)　+35%(40291)　+40%(40292)",
        "　< รหัสศักยภาพบอสแดเมจ >",
        "　+20%(30601)　+25%(40601)　+30%(30602)　+35%(40602)　+40%(40603)",
        "　< รหัสศักยภาพคริติคอล >",
        "　Critical Rate: +8%(20055)　+10%(30055)　+12%(40055)",
        "　Crit Min Dmg: +15%(40056)　Crit Max Dmg: +15%(40057)",
        "　< รหัสศักยภาพเครื่องประดับ · ชุดเกราะ >",
        "　Meso Obtain: +20%(40650)　Item Drop Rate: +20%(40656)",
        "　Invincibility time after hit: 1 sec(20366)　2 sec(30366)　3 sec(40366)",
        "　< รหัสศักยภาพสกิล Decent >",
        "　(Unique)　Haste(31001)　Mystic Door(31002)　Sharp Eyes(31003)　Hyper Body(31004)",
        "　(Legendary)　Combat Orders(41005)　Advanced Bless(41006)　Wind Booster(41007)"
    ];
    for (var i = 0; i < list.length; i++) {
        send(20, list[i]);
    }
    cm.getPlayer().dropMessage(1, "ขยายหน้าต่างแชทเพื่อดูเนื้อหาทั้งหมด");
    cm.dispose();
}

// Use the remaining addItemInfo function as is.
