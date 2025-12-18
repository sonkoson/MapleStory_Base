package commands;

import java.util.Collection;
import network.game.GameServer;
import objects.users.MapleClient;
import objects.utils.StringUtil;

public class ServerMessageCommand implements Command {
   @Override
   public void execute(MapleClient c, String[] splittedLine) throws Exception, IllegalCommandSyntaxException {
      Collection<GameServer> cservs = GameServer.getAllInstances();
      String outputMessage = StringUtil.joinStringFrom(splittedLine, 1);

      for (GameServer cserv : cservs) {
         cserv.setServerMessage(outputMessage);
      }
   }

   @Override
   public CommandDefinition[] getDefinition() {
      return new CommandDefinition[] { new CommandDefinition("!servermessage", "<message>",
            "ตั้งค่าข้อความเซิร์ฟเวอร์ที่แสดงด้านบนของหน้าจอ", 2) };
   }
}
