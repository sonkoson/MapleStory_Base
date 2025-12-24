var status = -1;
var sel = 0;

var limit = 10; // Charge limit
var ccoin = 4310261; // Coin to use
var quantity = 15; // Required coin amount

var promotion = false;

mobid = [9010172, 9010173, 9010174, 9010175, 9010176, 9010178, 8644005, 8644006, 8644424]

function action(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
    if (status == 0) {
        if (cm.getPlayer().getFrozenLinkMobCount() <= 0) {
            if (!cm.CountCheck("frozen_link", limit)) {
                cm.sendNext("วันนี้ใช้โอกาสเติมหมดแล้ว!");
                cm.dispose();
                return;
            }
        }
        var msg = "สนามล่ามอนที่สดชื่นที่สุดใน Maple World!\r\n"
        msg += "ยินดีต้อนรับสู่ #b<Frozen Link>#k นะ~!\r\n\r\n";
        msg += "#b#L0# อยากเปลี่ยนประเภทมอนสเตอร์#k ที่ถูกเรียก\r\n"
        msg += "#b#L1# อยากเติมจำนวนมอนสเตอร์ที่ล่าได้#k\r\n"
        cm.sendSimple(msg);
    } else if (status == 1) {
        sel = selection;
        if (selection == 0) {
            var msg = "#bมอนสเตอร์ Frozen Link#k ที่เลือกได้มีดังนี้!\r\nกรุณาเลือกมอนสเตอร์ที่ต้องการ~!\r\n\r\n";
            for (var i = 0; i < mobid.length; i++) {
                msg += "#L" + i + "# #b[Frozen Link]: #o" + mobid[i] + "#\r\n";
            }
            cm.sendSimple(msg);
        } else if (selection == 1) {


            if (cm.haveItem(4001884)) {
                cm.sendYesNo("มี #b#v" + 4001884 + "##t" + 4001884 + "##k อยู่นะ จะเติม #b3000 ตัว#k เลยไหม?");
                promotion = true;
                return;
            }
            var coinCount = parseInt(cm.getPlayer().getStackEventGauge(0));

            if (Math.floor(coinCount / quantity) < (limit - cm.GetCount("frozen_link"))) {
                limit = Math.floor(coinCount / quantity)
            } else {
                limit = (limit - cm.GetCount("frozen_link"));
            }
            cm.sendGetNumber("จะเติมกี่ครั้ง?\r\n\r\n#r#e※ 1 ครั้งจะใช้ Coin " + quantity + " ชิ้น และเติมมอนสเตอร์ 500 ตัว\r\n\r\n#b( Neo Stone ที่มี: " + coinCount + " ชิ้น)", 1, 1, limit);
        }
    } else if (status == 2) {
        if (sel == 0) {
            cm.getPlayer().setFrozenLinkMobID(mobid[selection]);
            cm.getPlayer().getMap().killAllMonstersFL(true, cm.getPlayer().getFrozenLinkSerialNumber());
            cm.getPlayer().cancelFrozenLinkTask();
            cm.getPlayer().changeMap(cm.getPlayer().getWarpMap(993014200));
            if (cm.getPlayer().getFrozenLinkMobCount() > 0) {
                cm.getPlayer().startFrozenLinkTask();
            }
            cm.sendOk("เปลี่ยนเป็นมอนสเตอร์ที่เลือกแล้วนะ~!");
            cm.dispose();
        } else if (sel == 1) {
            if (promotion) {
                cm.getPlayer().addFrozenLinkMobCount(3000);
                cm.gainItem(4001884, -1);
                cm.getPlayer().dropMessage(6, "เติม 3000 ตัว เสร็จเรียบร้อย");
                if (cm.getPlayer().getFrozenLinkMobID() == 0) {
                    var a = Math.floor(cm.getPlayer().getLevel() / 10) - 1;
                    cm.getPlayer().setFrozenLinkMobID(mobid[selection]);
                }
                cm.getPlayer().startFrozenLinkTask();
                cm.dispose();
                return;
            }

            var coinCount = parseInt(cm.getPlayer().getStackEventGauge(0));
            if (selection < 0) {
                return;
            }
            if (/*!cm.haveItem(ccoin, selection * quantity)*/coinCount < selection * quantity) {
                cm.sendNext("ดูเหมือน Neo Stone จะไม่พอนะ~?");
                cm.dispose();
                return;
            }
            cm.getPlayer().addFrozenLinkMobCount(500 * selection);
            //cm.gainItem(ccoin , -selection * quantity)
            cm.getPlayer().gainStackEventGauge(0, -(selection * quantity), true);
            //cm.getPlayer().gainKillPoint(-selection * quantity);
            cm.getPlayer().dropMessage(6, "เติม " + selection * 500 + " ตัว เสร็จเรียบร้อย");
            for (var i = 0; i < selection; ++i) {
                cm.getPlayer().CountAdd("frozen_link");
            }
            if (cm.getPlayer().getFrozenLinkMobID() == 0) {
                var a = Math.floor(cm.getPlayer().getLevel() / 10) - 1;
                cm.getPlayer().setFrozenLinkMobID(mobid[selection]);
            }
            cm.getPlayer().startFrozenLinkTask();
            cm.dispose();
        }
    }
}