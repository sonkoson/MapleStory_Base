function enter(pi) {
    pi.getPlayer().getStat().heal(pi.getPlayer())
    if (pi.getPlayer().getPosition().x > -830) {
        pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.CharReLocationPacket(-4129, -88));
        var m = (pi.getPlayer().getPosition().x + 830) / 100;
        if (m >= 15) {
            pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.SendPacket(714, "01 01 01 00"));
            pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.playSE("Sound/MiniGame.img/prize"));
            pi.getClient().getSession().writeAndFlush(Packages.network.models.CField.environmentChange("monsterPark/clearF", 0x13));
            var schedule = Packages.server.Timer.MapTimer.getInstance().schedule(function () {
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.playSE("Sound/MiniGame.img/Catch"));
            }, 1000)
            pi.getClient().getSession().writeAndFlush(Packages.network.models.CField.enforceMSG("ผ่านด่านแล้ว กำลังย้ายไปล็อบบี้", 212, 2000));
            pi.getPlayer().RegisterPlatformerRecord(12);
            pi.getPlayer().warpdelay(993001000, 2);
            var schedule = Packages.server.Timer.MapTimer.getInstance().schedule(function () {
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.SendPacket(714, "00 01"));
            }, 2000)
        }
        else {
            pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.SendPacket(694, "05 00 00 00 D0 07 00 00 00"));
            pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070201, 3000, "สถิติการกระโดดของเจ้าคือ " + m + " เมตร ลองใหม่อีกครั้ง!", ""));
        }
        return;
    }
}
