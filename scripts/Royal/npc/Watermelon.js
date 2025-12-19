importPackage(java.lang);
var enter = "\r\n";
var seld = -1;

var limit = 5;

var needs = [[4034939, 30], [4034941, 30]];
var special_needs = [[4031701, 50]];


var reward = [[4009005, 100, 200], [4310266, 50, 120], [2430041, 1, 1], [2435719, 10, 50], [2049360, 2, 3], [4031701, 5, 7], [2048717, 30, 50], [5060048, 1, 3], [4001715, 30, 100], [2431940, 1, 2], [2049153, 3, 5]];

var special_reward = 1112164;
var special_allstat = 200;
var special_atk = 100;

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
        var msg = "#fs 11##bต้องการวัตถุดิบเพื่อคลายร้อนฤดูร้อน!#k\r\nนำ #b#i4034939#30 ชิ้น, #i4034941#30 ชิ้น#k ที่ดรอปจากทุกพื้นที่ล่ามาให้ แล้วจะให้รางวัลพิเศษ! แลกได้วันละ 3 ครั้งต่อบัญชีเท่านั้น!#b" + enter;
        msg += "#L1#แลกไอเท็ม" + enter;
        msg += "#L2#แลกไอเท็มอีเว้นท์" + enter;

        cm.sendSimple(msg);
    } else if (status == 1) {

        if (cm.getClient().getKeyValue("day_qitem") == null)
            cm.getClient().setKeyValue("day_qitem", 0);

        if (Integer.parseInt(cm.getClient().getKeyValue("day_qitem")) >= limit) {
            cm.sendOk("วันนี้แลกไม่ได้แล้ว! กลับมาใหม่พรุ่งนี้นะ");
            cm.dispose();
            return;
        }
        seld = sel;

        switch (seld) {
            case 1:
                var msg = "#fs11#นำ #b#i4034939#30 ชิ้น, #i4034941#30 ชิ้น#k มาให้ แล้วจะให้ของขวัญพิเศษ~#b" + enter + enter;
                msg += "#i4034939#  #c4034939# / 30\r\n#i4034941#  #c4034941# / 30" + enter;

                cm.sendNext(msg);
                break;
            case 2:
                var msg = "#fs11#นำไอเท็มด้านล่างมาให้ แล้วจะให้ไอเท็มพิเศษ!#b" + enter + enter;
                for (i = 0; i < special_needs.length; i++) {
                    msg += "#i4031701#  #c4031701# / 50\r\n" + enter;
                    msg += "\r\n#bรางวัลอีเว้นท์#k\r\n#b#i" + special_reward + "##z" + special_reward + "# All Stats " + special_allstat + " | ATT/MATT " + special_atk + enter;
                    msg += "#i4001715##z4001715# 30~100 ชิ้น#k" + enter;
                }

                cm.sendNext(msg);
                break;
        }
    } else if (status == 2) {
        var pass = true;
        switch (seld) {
            case 1:
                for (i = 0; i < needs.length; i++) {
                    if (!cm.haveItem(needs[i][0], needs[i][1])) {
                        pass = false;
                        break;
                    }
                }

                if (!pass) {
                    cm.sendOk("#fs11#ดูเหมือนจะยังเก็บไอเท็มไม่ครบนะ?");
                    cm.dispose();
                    return;
                }

                var msg = "#fs11#เก็บวัตถุดิบครบแล้วเหรอ!?";
                cm.sendNext(msg);
                break;
            case 2:
                for (i = 0; i < special_needs.length; i++) {
                    if (!cm.haveItem(special_needs[i][0], special_needs[i][1])) {
                        pass = false;
                        break;
                    }
                }

                if (!pass) {
                    cm.sendOk("#fs11#ดูเหมือนจะยังเก็บไอเท็มไม่ครบนะ?");
                    cm.dispose();
                    return;
                }

                var msg = "#fs11#นี่คือไอเท็มพิเศษของอีเว้นท์นี้! เป็นยังไงบ้าง!?#b" + enter + enter;

                msg += "#L1##i" + special_reward + "##z" + special_reward + "# All Stats " + special_allstat + " | ATT/MATT " + special_atk + enter;
                msg += "#L2##i4001715##z4001715# 30 ~ 100 ชิ้น" + enter;
                cm.sendSimple(msg);
                break;
        }
    } else if (status == 3) {
        switch (seld) {
            case 1:
                var msg = "#fs11#ถ้าให้วัตถุดิบที่เก็บมา จะให้ไอเท็มสุ่มจากรายการด้านล่าง เอาไหม?#b" + enter + enter;

                for (i = 0; i < reward.length; i++) {
                    if (reward[i][0] == -5)
                        msg += "#i4001715##z4001715# " + reward[i][1] + " ~ " + reward[i][2] + " ชิ้น" + enter;
                    else
                        msg += "#i" + reward[i][0] + "##z" + reward[i][0] + "# " + reward[i][1] + " ~ " + reward[i][2] + " ชิ้น" + enter;
                }
                msg += enter + "#kจะแลกจริงๆ เหรอ? วันนี้แลกได้อีก #b" + (limit - Integer.parseInt(cm.getClient().getKeyValue("day_qitem"))) + " ครั้ง#k";
                cm.sendYesNo(msg);
                break;
            case 2:
                seld2 = sel;
                var msg = "#fs11#เป็นยังไง ไอเท็มสุดเท่ใช่ไหม? หลังจบอีเว้นท์นี้จะหาไม่ได้อีกแล้วนะ โชคดีมากเลยล่ะ!#b" + enter + enter;
                if (sel == 1)
                    msg += "#i" + special_reward + "##z" + special_reward + "# All Stats " + special_allstat + " | ATT/MATT " + special_atk;

                msg += enter + "#kจะแลกจริงๆ เหรอ? วันนี้แลกได้อีก #b" + (limit - Integer.parseInt(cm.getClient().getKeyValue("day_qitem"))) + " ครั้ง#k";
                cm.sendYesNo(msg);
                break;
        }

    } else if (status == 4) {
        switch (seld) {
            case 1:
                a = Packages.objects.utils.Randomizer.rand(0, reward.length - 1);
                b = Packages.objects.utils.Randomizer.rand(reward[a][1], reward[a][2]);

                if (reward[a][0] != -5 && !cm.canHold(reward[a][0], b)) {
                    cm.sendOk("ตรวจสอบว่ามีที่ว่างในกระเป๋าหรือไม่");
                    cm.dispose();
                    return;
                }
                for (i = 0; i < needs.length; i++) {
                    cm.gainItem(needs[i][0], -needs[i][1]);
                }

                var msg = "แลกเสร็จแล้ว";
                if (reward[a][0] == -5) {
                    msg = "#i4001715##z4001715# ได้ #b" + b + " ชิ้น#k!";
                } else
                    cm.gainItem(reward[a][0], b);
                break;
            case 2:
                if (!cm.canHold(special_reward, 1) && seld == 1) {
                    cm.sendOk("ตรวจสอบว่ามีที่ว่างในกระเป๋าหรือไม่");
                    cm.dispose();
                    return;
                }
                for (i = 0; i < special_needs.length; i++) {
                    cm.gainItem(special_needs[i][0], -special_needs[i][1]);
                }
                if (seld2 == 1) {
                    var msg = "แลกเสร็จแล้ว";
                    gainItemS(special_reward, special_allstat, special_atk);
                } else if (seld2 == 2) {
                    b = Packages.objects.utils.Randomizer.rand(30, 50);
                    cm.gainItem(4001715, b);
                    msg = "#i4001715##z4001715# ได้ #b" + b + " ชิ้น#k!";
                }

                break;
        }
        cm.getClient().setKeyValue("day_qitem", (Integer.parseInt(cm.getClient().getKeyValue("day_qitem")) + 1));
        cm.sendOk(msg);
        cm.dispose();
    }
}

function gainItemS(id, as, atk) {
    item = Packages.objects.item.MapleItemInformationProvider.getInstance().getEquipById(id);
    if (as > -1) {
        item.setStr(as);
        item.setDex(as);
        item.setInt(as);
        item.setLuk(as);
    }
    if (atk > -1) {
        item.setWatk(atk);
        item.setMatk(atk);
    }
    Packages.objects.item.MapleInventoryManipulator.addFromDrop(cm.getClient(), item, false);
}
