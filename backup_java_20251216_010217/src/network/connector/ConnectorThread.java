package network.connector;

import java.util.ArrayList;
import java.util.List;
import network.center.Center;
import network.game.GameServer;
import objects.users.MapleCharacter;

public class ConnectorThread implements Runnable {
   @Override
   public void run() {
      try {
         List<MapleCharacter> removeChars = new ArrayList<>();

         for (GameServer cs : GameServer.getAllInstances()) {
            for (MapleCharacter chr : cs.getPlayerStorage().getAllCharacters()) {
               try {
                  if (!chr.getClient().isAllowedClient()) {
                     if (chr.getClient().getConnectorClient() != null) {
                        ConnectorClient cli = ConnectorServer.getInstance().getClientStorage().getLoginClient(chr.getClient().getConnectorClient().getId());
                        if (cli != null) {
                           if (cli.getAccountId() != chr.getClient().getAccID()) {
                              System.out
                                 .println("[Connector Error] " + chr.getName() + " ์บ๋ฆญํฐ์ AccountID๊ฐ€ ๋ค๋ฆ : " + cli.getAccountId() + " / " + chr.getClient().getAccID());
                              removeChars.add(chr);
                           }
                        } else {
                           System.out.println("[Connector Error] " + chr.getName() + " ์บ๋ฆญํฐ์ ์ €์ฅ๋ ์ปค๋ฅํฐ ํด๋ผ์ด์–ธํธ ์ •๋ณด๊ฐ€ Null");
                           removeChars.add(chr);
                        }
                     } else {
                        ConnectorClient cli = ConnectorServer.getInstance().getClientStorage().getLoginClient(chr.getClient().getAccountName());
                        if (cli != null) {
                           chr.getClient().setConnectorClient(cli);
                        } else {
                           System.out.println("[Connector Error] " + chr.getName() + " ์บ๋ฆญํฐ์ ๊ณ์ • ์ปค๋ฅํฐ ํด๋ผ์ด์–ธํธ ์ •๋ณด๊ฐ€ Null");
                           removeChars.add(chr);
                        }
                     }
                  }
               } catch (Exception var7) {
                  System.out.println("Connector Thread error");
                  var7.printStackTrace();
               }
            }
         }

         for (MapleCharacter chr : removeChars) {
            if (chr.getClient().getConnectorClient() != null) {
               chr.getClient().getConnectorClient().getSession().close();
               System.out.println("Disconnected dude");
            }

            chr.getClient().getSession().close();
            System.out.println("Disconnected dude");
            Center.Find.forceDeregister(chr.getId(), chr.getName(), chr.getAccountID());
         }
      } catch (Exception var8) {
         System.out.println("Connector Thread error");
         var8.printStackTrace();
      }
   }
}
