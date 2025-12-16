package constants;

import database.DBConfig;
import java.util.HashMap;
import java.util.Map;
import objects.utils.Properties;
import objects.utils.Table;

public class MobSettingConstants {
   public static Map<Integer, Long> maxHP = new HashMap<>();
   public static Map<Integer, Integer> killPoint = new HashMap<>();

   public static void init() {
      Table table = Properties.loadTable(DBConfig.isGanglim ? "data/Ganglim" : "data/Jin", "CustomMobSetting.data");
      int count = 0;

      for (Table children : table.getChild("MobMaxHP").list()) {
         String mHP = children.getProperty("MaxHP");
         int mobID = Integer.parseInt(children.getName());
         maxHP.put(mobID, Long.valueOf(mHP));
         count++;
      }

      System.out.println("[DEBUG] Loaded " + count + " Custom Mob MaxHP Settings.");
   }

   public static long getMaxHP(int mobID) {
      Long ret = maxHP.get(mobID);
      return ret != null ? ret : 0L;
   }

   public static int getKillPoint(int mobID) {
      Integer ret = killPoint.get(mobID);
      return ret != null ? ret : 0;
   }
}
