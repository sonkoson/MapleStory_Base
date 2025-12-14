function start() {
    status = -1;
    action(1, 0, 0);
}

var serverName = "Royal Maple World"

function action(mode, type, selection) {
    dialogue = [
        [""],
        ["#fc0xFF54ff00#[Rank Upgrade Buff]#k"],
        ["#fs16##bเมื่อ Rank เพิ่มขึ้น บัฟด้านล่างจะทับซ้อนกัน"],
        ["#rMeso Drop Rate + 10%\r\nItem Drop Rate +10%\r\nCritical Damage + 5%\r\nBoss Damage +5%"],
        ["#fs20##fc0xFF54ff00#[Rank Types]#k"],
        ["#fc0xFF330000##fEtc/ZodiacEvent.img/0/icon/1/0#[Bronze] #fc0xFF999999##fEtc/ZodiacEvent.img/0/icon/2/0#[Silver] #fc0xFFCCCC33##fEtc/ZodiacEvent.img/0/icon/3/0#[Gold] #fc0xFF339966##fEtc/ZodiacEvent.img/0/icon/4/0#[Platinum] #fc0xFF00CCCC##fEtc/ZodiacEvent.img/0/icon/5/0#[Diamond]\r\n#fc0xFF990066##fEtc/ZodiacEvent.img/0/icon/6/0#[Master] #fc0xFFCC0066##fEtc/ZodiacEvent.img/0/icon/7/0#[Grand Master] #fc0xFFFF6600##fEtc/ZodiacEvent.img/0/icon/8/0#[Challenger]\r\n\r\n#fc0xFFFFFFFF##fEtc/ZodiacEvent.img/0/icon/9/0#[Noble] #fEtc/ZodiacEvent.img/0/icon/10/0#[Imperial] #fEtc/ZodiacEvent.img/0/icon/11/0#[Specialist]\r\n\r\nขอบคุณที่ใช้บริการ #fc0xFFFF3366#Royal Maple World#fc0xFFFFFFFF# ในวันนี้"],
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