package commands;

import objects.users.MapleClient;

public class HelpCommand implements Command {
   @Override
   public void execute(MapleClient c, String[] splittedLine) throws Exception, IllegalCommandSyntaxException {
      try {
         CommandProcessor.getInstance().dropHelp(c.getPlayer(), CommandProcessor.getOptionalIntArg(splittedLine, 1, 1));
      } catch (Exception var4) {
         var4.printStackTrace();
      }
   }

   @Override
   public CommandDefinition[] getDefinition() {
      return new CommandDefinition[] {
            new CommandDefinition("!help", "[page - default: 1]", "Shows the list of commands.", 1),
            new CommandDefinition("!commands", "[page - default: 1]", "Shows the list of commands.", 1)
      };
   }
}
