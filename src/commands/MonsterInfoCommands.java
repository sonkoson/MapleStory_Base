package commands;

import java.util.Arrays;
import objects.fields.Field;
import objects.fields.MapleMapObject;
import objects.fields.MapleMapObjectType;
import objects.fields.gameobject.lifes.MapleMonster;
import objects.users.MapleClient;

public class MonsterInfoCommands implements Command {
   @Override
   public void execute(MapleClient c, String[] splitted) throws Exception {
      Field map = c.getPlayer().getMap();
      double range = Double.POSITIVE_INFINITY;
      if (splitted.length > 1) {
         int irange = Integer.parseInt(splitted[1]);
         if (splitted.length <= 2) {
            range = irange * irange;
         } else {
            map = c.getChannelServer().getMapFactory().getMap(Integer.parseInt(splitted[2]));
         }
      }

      if (splitted[0].equals("!killall")) {
         for (MapleMapObject monstermo : map.getMapObjectsInRange(c.getPlayer().getPosition(), range,
               Arrays.asList(MapleMapObjectType.MONSTER))) {
            MapleMonster mob = (MapleMonster) monstermo;
            map.killMonster(mob, c.getPlayer(), false, false, (byte) 1);
         }
      } else if (splitted[0].equals("!killalldrop")) {
         for (MapleMapObject monstermo : map.getMapObjectsInRange(c.getPlayer().getPosition(), range,
               Arrays.asList(MapleMapObjectType.MONSTER))) {
            MapleMonster mob = (MapleMonster) monstermo;
            map.killMonster(mob, c.getPlayer(), true, false, (byte) 1);
         }
      } else if (splitted[0].equals("!killallmsg")) {
         map.killAllMonsters(false);
      } else if (splitted[0].equals("!mobinfo")) {
         for (MapleMapObject monstermo : map.getMapObjectsInRange(c.getPlayer().getPosition(), range,
               Arrays.asList(MapleMapObjectType.MONSTER))) {
            MapleMonster mob = (MapleMonster) monstermo;
            c.getPlayer().dropMessage(6, "มอนสเตอร์: " + mob.toString());
         }
      } else if (splitted[0].equals("!lowhp")) {
         for (MapleMapObject monstermo : map.getMapObjectsInRange(c.getPlayer().getPosition(), range,
               Arrays.asList(MapleMapObjectType.MONSTER))) {
            MapleMonster mob = (MapleMonster) monstermo;
            mob.setHp(10L);
         }
      }
   }

   @Override
   public CommandDefinition[] getDefinition() {
      return new CommandDefinition[] {
            new CommandDefinition("!killall", "(<range>) (<mapid>)", "ฆ่ามอนสเตอร์ทั้งหมดในแผนที่", 5),
            new CommandDefinition("!killallmsg", "(<range>) (<mapid>)",
                  "ฆ่ามอนสเตอร์ทั้งหมดในแผนที่ (ดรอปปกติ)", 5),
            new CommandDefinition("!killalldrop", "(<range>) (<mapid>)", "ฆ่ามอนสเตอร์ทั้งหมดในแผนที่ (ดรอปทุกอย่าง)",
                  5),
            new CommandDefinition("!mobinfo", "", "แสดงข้อมูลมอนสเตอร์ในแผนที่", 5),
            new CommandDefinition("!lowhp", "", "ตั้งค่า HP ของมอนสเตอร์ในระยะเป็น 10", 5)
      };
   }
}
