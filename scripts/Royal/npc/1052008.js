var status = -1;

importPackage(Packages.constants);

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
        cm.sendNextS("#fnArial##d(ดูเหมือนจะมีอะไรบางอย่างอยู่ในหีบสมบัติ)#k\r\nนี่อาจจะเป็น #bเหรียญ#k ที่ Shumi ทำหายไปหรือเปล่านะ?", 2);
    } else if (status == 1) {
        cm.sendOk("#fnArial#คุณได้รับไอเท็มจากหีบสมบัติ\r\n\r\n#fUI/UIWindow2.img/QuestIcon/4/0#\r\n\r\n#i4031039# #b#z4031039##k");
    } else if (status == 2) {
        cm.warp(ServerConstants.TownMap, 0);
        cm.gainItem(4031039, 1);
        cm.dispose();
    }
}
