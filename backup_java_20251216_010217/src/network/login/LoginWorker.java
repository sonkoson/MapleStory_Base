package network.login;

import java.util.Map;
import java.util.Map.Entry;
import network.game.GameServer;
import network.login.processors.CharLoginHandler;
import network.models.CWvsContext;
import network.models.LoginPacket;
import objects.users.MapleClient;

public class LoginWorker {
   private static long lastUpdate = 0L;

   public static void registerClient(MapleClient c, String id, String mac, String volume) {
      if (LoginServer.isAdminOnly() && !c.isGm() && !c.isLocalhost()) {
         c.getSession().writeAndFlush(
               CWvsContext.serverNotice(1, "เน€เธเธดเธฃเนเธเน€เธงเธญเธฃเนเธเธณเธฅเธฑเธเธเธดเธ”เธเธฃเธฑเธเธเธฃเธธเธ เธเธฃเธธเธ“เธฒเธ•เธฃเธงเธเธชเธญเธเธฃเธฒเธขเธฅเธฐเน€เธญเธตเธขเธ”เธ—เธตเนเธซเธเนเธฒเน€เธงเนเธเนเธเธ•เน"));
         c.getSession().writeAndFlush(LoginPacket.getLoginFailed(7));
      } else {
         if (System.currentTimeMillis() - lastUpdate > 600000L) {
            lastUpdate = System.currentTimeMillis();
            Map<Integer, Integer> load = GameServer.getChannelLoad();
            int usersOn = 0;
            if (load == null || load.size() <= 0) {
               lastUpdate = 0L;
               c.getSession().writeAndFlush(LoginPacket.getLoginFailed(7));
               return;
            }

            double loadFactor = 1200.0 / ((double) LoginServer.getUserLimit() / load.size());

            for (Entry<Integer, Integer> entry : load.entrySet()) {
               usersOn += entry.getValue();
               load.put(entry.getKey(), Math.min(1200, (int) (entry.getValue().intValue() * loadFactor)));
            }

            LoginServer.setLoad(load, usersOn);
            lastUpdate = System.currentTimeMillis();
         }

         if (c.finishLogin() == 0) {
            c.getSession().writeAndFlush(LoginPacket.getAuthSuccessRequest(c, id));
            if (mac != null && volume != null && !mac.equals("") && !volume.equals("")) {
               CharLoginHandler.ServerListRequest(c, false, mac, volume);
            }

            CharLoginHandler.ServerListRequest(c, false, "", "");
         } else {
            c.getSession().writeAndFlush(LoginPacket.getLoginFailed(7));
         }
      }
   }
}
