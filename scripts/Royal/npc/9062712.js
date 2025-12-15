importPackage(Packages.server);
importPackage(Packages.database);
importPackage(Packages.objects.utils);
importPackage(Packages.objects.item);
importPackage(Packages.scripting);
importPackage(Packages.network.game);
importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);
importPackage(java.text);

var status = -1;
var tempsel = 0;
var seld = 0;

function start() {
    status = -1;
    action(1, 1, 0);
}

function action(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
        var send = "#fs11#Please select a function.\r\n";
        send += "#L0#Check BGM Queue (Current Map/Channel)#l\r\n";
        send += "#L1#Register BGM Queue (Current Map/Channel) (2 Billion Mesos)#l\r\n";

        /*
        if (cm.getPlayer().isGM()) {
        send += "\r\n\r\n";
        send += "#r#e   <GM Menu>#k#n\r\n";
        send += "#L2#Register BGM Queue (All Channels)#l\r\n";
        send += "#L3#Reset BGM Queue (All Channels)#l";
        }
        */
        cm.sendSimple(send);
    }
    if (status == 1) {
        seld = selection;
        if (seld == 0) {
            var mapmusic = cm.getMapMusicList();
            if (mapmusic.size() == 0) {
                cm.sendOk("#fs11#There are currently no songs in the queue.");
                cm.dispose();
                return;
            }
            var musiclist = "#fs11#Here is the list of BGM currently in the queue.\r\n#r※ Plays for about 5 minutes then skips to the next song.#k\r\n\r\n";
            for (var i = 0; i < mapmusic.size(); i++) {
                musiclist += mapmusic.get(i) + "\r\n";
            }
            cm.sendOk(musiclist);
            cm.dispose();
            return;
        }
        if (seld == 1) {
            var mapmusic = cm.getMapMusicList();
            if (mapmusic.size() > 8) {
                cm.sendOk("#fs11#You can register up to 8 songs in the queue.");
                cm.dispose();
                return;
            }
            if (!cm.haveItem(4001715, 20)) {
                cm.sendOk("#fs11#You are missing 20 #i4001715# #z4001715#.");
                cm.dispose();
                return;
            }
            var musiclist = cm.getAllSound();
            var musicask = "#fs11#Please select the BGM you want.\r\n\r\n";
            musicask += "#b< Latale Music >#k\r\n";
            for (var i = 0; i < musiclist.size(); i++) {
                if (musiclist.get(i) == "[테일즈위버] Reminiscence")
                    musicask += "\r\n\r\n#b< Other Music >#k\r\n";
                musicask += "#L" + i + "#" + musiclist.get(i) + "#l\r\n";
            }
            cm.sendSimple(musicask);
        }

        if (seld == 2) { // GM menu: Register BGM Queue (All Channels)
            var mapmusic = cm.getMapMusicList();
            if (mapmusic.size() > 8) {
                cm.sendOk("#fs11#You can register up to 8 songs in the queue.");
                cm.dispose();
                return;
            }
            // No item check for GM option
            var musiclist = cm.getAllSound();
            var musicask = "#fs11#Please select the BGM you want.\r\n\r\n";
            musicask += "#b< Latale Music >#k\r\n";
            for (var i = 0; i < musiclist.size(); i++) {
                if (musiclist.get(i) == "[테일즈위버] Reminiscence")
                    musicask += "\r\n\r\n#b< Other Music >#k\r\n";
                musicask += "#L" + i + "#" + musiclist.get(i) + "#l\r\n";
            }
            cm.sendSimple(musicask);
        }
        if (seld == 3) { // GM menu: Reset BGM Queue (All Channels)
            cm.askYesNo("#fs11#Are you sure you want to reset the BGM queue for the current map (all channels)?", Packages.scripting.GameObjectType.Npc, Packages.scripting.ScriptMessageFlag.None);
        }

    }
    if (status == 2) {
        if (seld == 1) {
            var musiclist = cm.getAllSound();
            if (selection >= 0 && selection < musiclist.size()) {
                tempsel = selection;
                cm.askYesNo("#fs11#Is " + musiclist.get(selection) + " the song you selected?\r\n\r\nIt requires 20 #r#i4001715# #z4001715##b. Do you want to proceed?", Packages.scripting.GameObjectType.Npc, Packages.scripting.ScriptMessageFlag.None);
            }
        }
        if (seld == 2) { // GM menu: Register BGM Queue (All Channels) - confirmation
            var musiclist = cm.getAllSound();
            if (selection >= 0 && selection < musiclist.size()) {
                tempsel = selection;
                cm.askYesNo("#fs11#Is " + musiclist.get(selection) + " the song you selected?", Packages.scripting.GameObjectType.Npc, Packages.scripting.ScriptMessageFlag.None);
            }
        }
        if (seld == 3) { // GM menu: Reset BGM Queue (All Channels) - confirmation
            for (i = 0; i < Packages.server.GameServer.getAllInstances().size(); i++) {
                channel = Packages.server.GameServer.getAllInstances().get(i)
                channel.getMapFactory().getMap(cm.getPlayer().getMapId()).clearMusicList();
            }
            cm.dispose();
            cm.sendOk("#fs11#Current map (all channels) list reset complete.");
        }
    }
    if (status == 3) {
        if (seld == 1) {
            if (!cm.haveItem(4001715, 20)) {
                cm.sendOk("#fs11#You are missing 20 #i4001715# #z4001715#.");
                cm.dispose();
                return;
            }
            cm.gainItem(4001715, -20);
            cm.addMapMusic(tempsel);
            var musiclist = cm.getAllSound();
            cm.sendOk("#fs11#" + musiclist.get(tempsel) + " \r\nhas been registered to the current map (current channel) queue.");
            cm.dispose();
        }
        if (seld == 2) {
            cm.addServerMapMusicServer(tempsel);
            var musiclist = cm.getAllSound();
            cm.sendOk("#fs11#" + musiclist.get(tempsel) + " \r\nhas been registered to the current map (all channels) queue.");
            cm.dispose();
        }
    }
}
