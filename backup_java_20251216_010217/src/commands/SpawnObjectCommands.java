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
            c.getPlayer().dropMessage(5, "เนเธชเนเธเนเธฒเนเธ”เนเน€เธเธตเธขเธ 0-9 เนเธฅเธฐเธชเธฒเธกเธฒเธฃเธ–เธชเธฃเนเธฒเธเธฃเธนเธเนเธ”เนเธเธฃเธฑเนเธเธฅเธฐ 1 เธญเธฑเธเน€เธ—เนเธฒเธเธฑเนเธ");
         }
      } else if (splitted[0].equals("!objectcount")) {
         c.getPlayer().dropMessage(5,
               "เธกเธตเธญเธญเธเน€เธเธเธ•เนเธเธณเธเธงเธ " + c.getPlayer().getMap().getMapObjectSize() + " เธเธดเนเธเนเธเนเธเธเธ—เธตเนเธเธฑเธเธเธธเธเธฑเธ");
      }
   }

   @Override
   public CommandDefinition[] getDefinition() {
      return new CommandDefinition[] {
            new CommandDefinition("!rune", "<type>", "เน€เธฃเธตเธขเธเธฃเธนเธเธญเธญเธเธกเธฒเธ—เธตเนเธ•เธณเนเธซเธเนเธเธเธญเธเธเธธเธ“", 6),
            new CommandDefinition("!objectcount", "", "เนเธชเธ”เธเธเธณเธเธงเธเธญเธญเธเน€เธเธเธ•เนเนเธเนเธเธเธ—เธตเนเธเธฑเธเธเธธเธเธฑเธ", 6)
      };
   }
}
