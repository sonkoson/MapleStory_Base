function enter(pi) {
    pi.getPlayer().getStat().heal(pi.getPlayer())
    if (pi.getPlayer().getPosition().x > -830) {
        pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.CharReLocationPacket(-4129, -88));
        var m = (pi.getPlayer().getPosition().x + 830) / 100;
        pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.SendPacket(694, "05 00 00 00 D0 07 00 00 00"));
        pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070201, 3000, "บันทึกการกระโดดของคุณคือ " + m + " เมตร ลองใหม่อีกครั้งนะ!", ""));
        return;
    }
}
