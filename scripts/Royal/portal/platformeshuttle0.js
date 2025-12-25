function enter(pi) {
    var lastshuttle = pi.getPlayer().getKeyValue(20190208, "lastshuttle");

    if (lastshuttle == 1 || lastshuttle == -1) {
        pi.getPlayer().setKeyValue(20190208, "lastshuttle", "0");
        pi.getPlayer().setKeyValue(20190208, "shuttlecount", (pi.getPlayer().getKeyValue(20190208, "shuttlecount") + 1) + "");
        var count = pi.getPlayer().getKeyValue(20190208, "shuttlecount");
        switch (count) {
            case 1:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "เอาล่ะ เริ่มได้! หนึ่ง!", ""));
                break;
            case 2:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "สอง!", ""));
                break;
            case 3:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "สาม!", ""));
                break;
            case 4:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "สี่!", ""));
                break;
            case 5:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "ห้า! เร็วกว่าแสง!", ""));
                break;
            case 6:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "หก!", ""));
                break;
            case 7:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "เจ็ด!", ""));
                break;
            case 8:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "แปด!", ""));
                break;
            case 9:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "เก้า!", ""));
                break;
            case 10:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "สิบ! ชื่อของข้าคือ ไลท์นิ่งโบลต์!", ""));
                break;
            case 11:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "สิบเอ็ด!", ""));
                break;
            case 12:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "สิบสอง!", ""));
                break;
            case 13:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "สิบสาม!", ""));
                break;
            case 14:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "สิบสี่!", ""));
                break;
            case 15:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "สิบห้า! อดทนไว้แม้จะเหนื่อย!", ""));
                break;
            case 16:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "สิบหก!", ""));
                break;
            case 17:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "สิบเจ็ด!", ""));
                break;
            case 18:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "สิบแปด!", ""));
                break;
            case 19:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "สิบเก้า!", ""));
                break;
            case 20:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "ยี่สิบ! คิดถึงแม่ใช่ไหมล่ะ?!", ""));
                break;
            case 21:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "ยี่สิบเอ็ด!", ""));
                break;
            case 22:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "ยี่สิบสอง!", ""));
                break;
            case 23:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "ยี่สิบสาม!", ""));
                break;
            case 24:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "ยี่สิบสี่!", ""));
                break;
            case 25:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "ยี่สิบห้า! เหลืออีกห้า!", ""));
                break;
            case 26:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "ยี่สิบหก!", ""));
                break;
            case 27:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "ยี่สิบเจ็ด!", ""));
                break;
            case 28:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "ยี่สิบแปด!", ""));
                break;
            case 29:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "ยี่สิบเก้า! อันสุดท้าย!", ""));
                break;
            case 29:
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.OnYellowDlg(9070203, 1000, "สามสิบ! เยี่ยม! ทำได้ดีมาก! สำเร็จแล้ว!", ""));
                break;
        }
        if (count == 30) {
            pi.getPlayer().setKeyValue(20190208, "lastshuttle", "-1");
            pi.getPlayer().setKeyValue(20190208, "shuttlecount", "0");
            pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.SendPacket(714, "01 01 01 00"));
            pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.CharReLocationPacket(-510, 92));
            pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.playSE("Sound/MiniGame.img/prize"));
            pi.getClient().getSession().writeAndFlush(Packages.network.models.CField.environmentChange("monsterPark/clearF", 0x13));
            var schedule = Packages.server.Timer.MapTimer.getInstance().schedule(function () {
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.playSE("Sound/MiniGame.img/Catch"));
            }, 1000)
            pi.getClient().getSession().writeAndFlush(Packages.network.models.CField.enforceMSG("ผ่านด่านแล้ว กำลังย้ายไปล็อบบี้", 212, 2000));
            pi.getPlayer().RegisterPlatformerRecord(18);
            pi.getPlayer().warpdelay(993001000, 2);
            var schedule = Packages.server.Timer.MapTimer.getInstance().schedule(function () {
                pi.getClient().getSession().writeAndFlush(Packages.tools.packet.MaplePacket.SendPacket(714, "00 01"));
            }, 2000)
        }
    }
}
