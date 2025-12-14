function start() {
    status = -1;
    action(1, 0, 0);
}

var serverName = "Royal"

function action(mode, type, selection) {
    dialogue = [
        [""],
        ["#fc0xFF54ff00#[Ganglim World Support Tier]#k"],
        ["#bจะได้รับ Buff ดังต่อไปนี้ ตามระดับ Support Tier#k"],
        ["#fs18##rLv.1#k #fc0xFFFF3366# #k -[สะสม 100 ครั้ง] \r\nㄴ Damage, Boss Damage 5%, Critical Damage 1%\r\n#rLv.2#k #fc0xFFFF3366# #k - [สะสม 300 ครั้ง] \r\nㄴ Damage, Boss Damage 10%, Critical Damage 3%\r\n#rLv.3#k #fc0xFFFF3366# #k - [สะสม 600 ครั้ง] \r\nㄴ Damage, Boss Damage 20%, Critical Damage 5%\r\n#rLv.4#k #fc0xFFFF3366# #k - [สะสม 1000 ครั้ง] \r\nㄴ Damage 40%, Boss Damage 30%, Critical Damage 7%\r\n#rLv.5#k #fc0xFFFF3366# #k - [สะสม 2000 ครั้ง] \r\nㄴ Damage 60%, Boss Damage 40%, Critical Damage 10%\r\n#rLv.6#k #fc0xFFFF3366# #k - [สะสม 3000 ครั้ง] \r\nㄴ Damage 80%, Boss Damage 50%, Critical Damage 15%"],
    ];
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
        cm.getClient().getSession().writeAndFlush(Packages.network.models.CField.blind(0x01, 200, 0, 0, 0, 800, 0));
        cm.getPlayer().setInGameDirectionMode(true, true, false, false);
        cm.getPlayer().InGameDirectionEvent("", 0x01, 1000);
    } else if (status > 0) {
        if (status == (dialogue.length - 1)) {
            cm.getPlayer().InGameDirectionEvent(dialogue[status], 0x0C, 1);
        } else if (status == dialogue.length) {
            cm.getPlayer().InGameDirectionEvent("", 0x01, 1000);
            cm.getPlayer().InGameDirectionEvent("", 0x16, 700);
        } else if (status == (dialogue.length + 1)) {
            cm.getPlayer().removeInGameDirectionMode();
            cm.getClient().getSession().writeAndFlush(Packages.network.models.CField.blind(0, 0, 0, 0, 0, 800, 0));
            cm.dispose();
        } else {
            cm.getPlayer().InGameDirectionEvent(dialogue[status], 0x0C, 0);
        }
    }
}