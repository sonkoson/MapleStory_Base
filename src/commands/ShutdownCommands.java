package commands;

import network.game.GameServer;
import network.shop.CashShopServer;
import objects.users.MapleClient;

public class ShutdownCommands implements Command {
   @Override
   public void execute(MapleClient c, String[] splitted) throws Exception, IllegalCommandSyntaxException {
      if (splitted[0].equals("!shutdown")) {
         CommandProcessor.forcePersisting();
         c.getChannelServer().shutdown();
      } else if (splitted[0].equals("!shutdowncs")) {
         CashShopServer.shutdown();
      } else if (splitted[0].equals("!shutdownall")) {
         int time = 60000;
         if (splitted.length > 1) {
            time = Integer.parseInt(splitted[1]) * 60000;
         }

         CommandProcessor.forcePersisting();

         for (GameServer cs : GameServer.getAllInstances()) {
            cs.shutdown();
         }
      } else if (splitted[0].equals("!closemerchants")) {
         c.getChannelServer().closeAllMerchant();
      }
   }

   @Override
   public CommandDefinition[] getDefinition() {
      return new CommandDefinition[] {
            new CommandDefinition("!closemerchants", "", "Closes all merchants in the channel.", 6),
            new CommandDefinition("!shutdowncs", "", "Shuts down the Cash Shop server.", 6),
            new CommandDefinition("!shutdown", "(<time in minutes>)", "Shuts down the current channel.", 6),
            new CommandDefinition("!shutdownall", "(<time in minutes>)", "Shuts down all channels.", 6)
      };
   }
}
