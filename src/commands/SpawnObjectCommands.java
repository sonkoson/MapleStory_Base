package commands;

import java.awt.Point;
import java.util.List;
import network.models.CField;
import objects.fields.gameobject.RuneStone;
import objects.fields.gameobject.RuneStoneType;
import objects.users.MapleClient;

public class SpawnObjectCommands implements Command {
   @Override
   public void execute(MapleClient c, String[] splitted) throws Exception, IllegalCommandSyntaxException {
      if (splitted[0].equals("!rune")) {
         Point pos = c.getPlayer().getPosition();
         int type = Integer.parseInt(splitted[1]);
         if (type >= 0 && type <= 9) {
            RuneStone rune = new RuneStone(RuneStoneType.get(Integer.parseInt(splitted[1])), 0, pos.x - 50, pos.y,
                  c.getPlayer().getMap());
            List<RuneStone> runes = c.getPlayer().getMap().getAllRune();

            for (int i = 0; i < runes.size(); i++) {
               c.getPlayer().getMap().removeMapObject(runes.get(i));
            }

            c.getPlayer().getMap().addMapObject(rune);
            c.getPlayer().getMap().broadcastMessage(CField.spawnRune(rune, false));
            c.getPlayer().getMap().broadcastMessage(CField.spawnRune(rune, true));
         } else {
            c.getPlayer().dropMessage(5, "Values 0-9 are possible, and only one rune can spawn at a time.");
         }
      } else if (splitted[0].equals("!objectcount")) {
         c.getPlayer().dropMessage(5,
               "There are " + c.getPlayer().getMap().getMapObjectSize() + " objects in the current map.");
      }
   }

   @Override
   public CommandDefinition[] getDefinition() {
      return new CommandDefinition[] {
            new CommandDefinition("!rune", "<type>", "Spawns a rune at your position.", 6),
            new CommandDefinition("!objectcount", "", "Displays the number of objects in the current map.", 6)
      };
   }
}
