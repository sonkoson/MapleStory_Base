importPackage(Packages.server);
importPackage(java.text);

var banitem = [1712001, 1712002, 1712003, 1712004, 1712005, 1712006, 1713000, 1713001, 1713002, 1713003, 1713004, 1713005, 1942004, 1952004, 1962004, 1972004, 1612004, 1622004, 1632003, 1642003, 1652004];
var 극악무도 = [1003142, 1009600, 1009601, 1009602, 1009603, 1009604, 1159600, 1159601, 1159602, 1159603, 1159604, 1109600, 1109601, 1109602, 1109603, 1109604, 1089600, 1089601, 1089602, 1089603, 1089604, 1079600, 1079601, 1079602, 1079603, 1079604];

var status = -1;
isReady = false;
var nf = java.text.NumberFormat.getInstance();
geti = null;
hm = 0;

var enhance = ["보스 공격시 데미지 +%", "몬스터 방어율 무시 +%", "데미지 +%", "올스탯 +%"]
var itemid = 4031227;
var qty = 30; //1%당 아이템 개수
var limit = [40, 50, 90, 10];
var selitem = 0;
var equipped = false;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        if (status == 3 && selection != 10) {
            if (selection == 0) {
                hw -= hm;
                hm = 0;
            } else if (selection == 1) {
                hw -= 10;
                hm -= 10;
            } else if (selection == 2) {
                hw -= 1;
                hm -= 1;
            } else if (selection == 3) {
                hw += 1;
                hm += 1;
            } else if (selection == 4) {
                hw += 10;
                hm += 10;
            } else {
                hw += limit[st2] - hw;
                hm = hw - parseInt(gE);
            }
        } else {
            status++;
        }
    } else {
        cm.dispose();
        return;
    }

    //Start
    if (status == 0) {
        말 = "#fs11##r#i4031227##z4031227##fc0xFF000000#ใช้\r\nเพื่อเพิ่ม #bSpecial Option#fc0xFF000000# ให้กับอุปกรณ์ที่ต้องการ\r\n\r\n";
        if (cm.getPlayer().getJob() == 10112)
            말 += "#r※ อาวุธ Zero อาจมีการรีเซ็ตออปชั่นเมื่อสืบทอดหรือเติบโต โปรดระวัง\r\n";

        말 += "#L0# #bเพิ่มออปชั่นเสริม#k#l";
        cm.sendSimple(말);
    } else if (status == 1) {
        var count = 0;
        말 = "#fs11##bเลือกไอเท็มที่จะเพิ่มความสามารถ\r\n\r\n#r#e※ ข้อควรระวัง ※\r\nจะแสดงรายการไอเท็มสวมใส่ - อุปกรณ์ตามลำดับ#n#k#b\r\n\r\n";

        for (i = 0; i <= 50; i++) {
            item = cm.getInventory(-1).getItem(i * -1);
            if ((item != null) && !cm.isCash(item.getItemId())) {
                말 += "#b#L" + (i + 100000) + "##i" + item.getItemId() + "# #z" + item.getItemId() + "# #r#e[สวมใส่อยู่]#n#k\r\n"
                count++;
            }
        }

        for (i = 0; i <= cm.getInventory(1).getSlotLimit(); i++) {
            item = cm.getInventory(1).getItem(i);
            if ((item != null) && !cm.isCash(item.getItemId())) {
                말 += "#b#L" + i + "##i" + item.getItemId() + "# #z" + item.getItemId() + "# #r[ช่อง " + i + "]#k\r\n"
                count++;
            }
        }

        if (count <= 0) {
            cm.sendOk("#fs11#ไม่มีอุปกรณ์ที่สามารถตีบวกได้");
            cm.dispose();
            return;
        }

        cm.sendSimple(말);
    } else if (status == 2) {
        st = selection;

        if (st > 10000) {
            st = -(st - 100000)
            equipped = true;
        }

        if (equipped) { // 장착중
            selitem = cm.getInventory(-1).getItem(st).getItemId();
        } else {
            selitem = cm.getInventory(1).getItem(st).getItemId();
        }


        if (banitem.indexOf(selitem) != -1) {
            cm.sendOk("#fs11#ไอเท็ม Symbol ไม่สามารถตีบวกได้");
            cm.dispose();
            return;
        }
        if (극악무도.indexOf(selitem) != -1) {
            limit = [40, 50, 110, 30];
        }

        말 = "#fs11##bไอเท็มที่เลือก :#k#n #i" + selitem + "# #b#z" + selitem + " #\r\n\r\n";
        말 += "#fs11##fc0xFF000000#เลือกออปชั่นเสริมที่ต้องการเพิ่ม\r\n\r\n";

        if (Packages.constants.GameConstants.isWeapon(selitem)) {
            말 += "อุปกรณ์ที่เลือกคือ #bWeapon#fc0xFF000000#\r\n";
        }
        if (Packages.constants.GameConstants.isRing(selitem)) {
            말 += "อุปกรณ์ที่เลือกคือ #bRing#fc0xFF000000#\r\n";
        }
        if (Packages.constants.GameConstants.isAndroidHeart(selitem)) {
            말 += "อุปกรณ์ที่เลือกคือ #bHeart#fc0xFF000000#\r\n";
        }

        말 += "#L0##fc0xFF6600CC#Damage to Boss +% #b(Max " + limit[0] + "%)#l\r\n";
        말 += "#L1##fc0xFF6600CC#Ignore Monster DEF +% #b(Max " + limit[1] + "%)#l\r\n";
        말 += "#L2##fc0xFF6600CC#Damage +% #b(Max " + limit[2] + "%)#l\r\n";
        //말 += "#fc0xFF6600CC##L3#All Stat +% #b(Max " + limit[3] + "%)#l\r\n"
        cm.sendSimple(말);
    } else if (status == 3) {
        if (!isReady) {
            if (equipped) { // 장착중
                gI = cm.getInventory(-1).getItem(st)
            } else {
                gI = cm.getInventory(1).getItem(st)
            }
            st2 = selection;
        }
        if (st2 == 0) {
            gE = gI.getBossDamage();
        } else if (st2 == 1) {
            gE = gI.getIgnorePDR();
        } else if (st2 == 2) {
            gE = gI.getTotalDamage();
        } else {
            gE = gI.getAllStat();
        }
        if (!isReady) {
            hw = parseInt(gE) + hm;
            isReady = true;
        }
        말 = "#fs11##bไอเท็มที่เลือก :#k#n #i" + selitem + "# #b#z" + selitem + " #\r\n\r\n";
        말 += "#fs11##bSpecial Option :#k#n " + enhance[st2] + "\r\n\r\n";
        말 += "#fs11##dระบุค่าที่ต้องการเพิ่ม\r\n#r(1% ใช้ #i4031227##z4031227# 30 ชิ้น)#k\r\n\r\n";

        말 += "#fs11#การเปลี่ยนแปลงค่า : #b" + gE + "%#k → #r" + hw + "% #fc0xFF0BCACC#(+" + hm + "%)#d#n\r\n\r\n"
        if (hm >= 10) {
            말 += "#L1# -10%#l "
        }
        if (hm >= 1) {
            말 += "#L2# -1%#l "
        }
        if (hw + 1 <= limit[st2]) {
            말 += "#L3# +1%#l ";
        }
        if (hw + 10 <= limit[st2]) {
            말 += "#L4# +10%#l ";
        }
        말 += "\r\n"
        말 += "#L10# #fs11##rตั้งค่าเสร็จสิ้น"
        cm.sendSimple(말);
    } else if (status == 4) {
        if (hm < 0) {
            cm.addCustomLog(50, "[붉은 구슬] " + cm.getPlayer().getID() + " hm :" + hm + "개 음수 적용");
            cm.dispose();
            return;
        }
        if (qty < 0) {
            cm.addCustomLog(50, "[붉은 구슬] " + cm.getPlayer().getID() + " qty :" + qty + "개 음수 적용");
            cm.dispose();
            return;
        }
        if (cm.itemQuantity(itemid) >= qty * hm) {
            cm.gainItem(itemid, -qty * hm);
            var type = "";
            if (st2 == 0) {
                type = "보공";
                gI.setBossDamage(hw);
            } else if (st2 == 1) {
                type = "방무";
                gI.setIgnorePDR(hw);
            } else if (st2 == 2) {
                type = "뎀퍼";
                gI.setTotalDamage(hw);
            } else {
                type = "올스탯";
                gI.setAllStat(hw);
            }


            cm.sendOk("#fs11#เพิ่มความสามารถเรียบร้อยแล้ว");
            cm.getPlayer().forceReAddItem(gI, Packages.objects.item.MapleInventoryType.EQUIP);
            cm.addEnchantLog(1, gI.getItemId(), gI.getSerialNumberEquip(), 12, 0, "Red Bead Enhance " + type + " " + hw + "% (Account : " + cm.getClient().getAccountName() + ", Character : " + cm.getPlayer().getName() + ", Equip Info [" + gI.toString() + "])");
            cm.dispose();
        } else {
            cm.sendOk("#fs11#ดูเหมือนวัสดุจะไม่เพียงพอ");
            cm.dispose();
        }
    }
}


function isWeapon(itemid) {
    if (Math.floor(itemid / 10000) >= 121 && Math.floor(itemid / 10000) <= 158) {
        return true;
    } else {
        return false;
    }
}