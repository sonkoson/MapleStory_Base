package commands;

import network.game.GameServer;
import network.models.CWvsContext;
import objects.users.MapleClient;
import objects.utils.StringUtil;

public class NoticeCommand implements Command {
   private int getNoticeType(String typestring) {
      if (typestring.equals("notice")) {
         return 0;
      } else if (typestring.equals("popup")) {
         return 1;
      } else if (typestring.equals("megaphone")) {
         return 2;
      } else if (typestring.equals("pink")) {
         return 5;
      } else if (typestring.equals("pinknotice")) {
         return 5;
      } else {
         return typestring.equals("blue") ? 6 : -1;
      }
   }

   @Override
   public void execute(MapleClient c, String[] splitted) throws Exception, IllegalCommandSyntaxException {
      if (splitted[0].equals("!notice")) {
         int joinmod = 1;
         int range = -1;
         if (splitted[1].equals("m")) {
            range = 0;
         } else if (splitted[1].equals("c")) {
            range = 1;
         } else if (splitted[1].equals("w")) {
            range = 2;
         }

         int tfrom = 2;
         if (range == -1) {
            range = 2;
            tfrom = 1;
         }

         int type = this.getNoticeType(splitted[tfrom]);
         if (type == -1) {
            type = 0;
            joinmod = 0;
         }

         StringBuilder sb = new StringBuilder();
         if (splitted[tfrom].equals("pinknotice")) {
            sb.append("[Notice]");
         } else {
            sb.append("");
         }

         joinmod += tfrom;
         sb.append(StringUtil.joinStringFrom(splitted, joinmod));
         byte[] packet = CWvsContext.serverNotice(type, sb.toString());
         if (range == 0) {
            c.getPlayer().getMap().broadcastMessage(packet);
         } else if (range == 1) {
            GameServer.getInstance(c.getChannel()).broadcastPacket(packet);
         } else if (range == 2) {
            for (GameServer cs : GameServer.getAllInstances()) {
               cs.broadcastPacket(packet);
            }
         }
      }
   }

   @Override
   public CommandDefinition[] getDefinition() {
      return new CommandDefinition[] {
            new CommandDefinition("!notice", "[m/c/w] [notice/popup/megaphone/pink/pinknotice/blue] <message>",
                  "Sends a server notice with the specified type and range.", 2) };
   }
}
