package constants;

import database.DBConfig;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import objects.item.MapleItemInformationProvider;

public class DailySettingConstants {
   public static List<Integer> dailyGifts = new ArrayList<>();
   public static List<Integer> dailyCounts = new ArrayList<>();

   protected static String toUni(String kor) throws UnsupportedEncodingException {
      return new String(kor.getBytes("KSC5601"), "8859_1");
   }

   public static String getItemName(int id) {
      return MapleItemInformationProvider.getInstance().getName(id);
   }

   public static void main(String[] args) {
      if (ServerConstants.useDailyGift) {
         System.out.println("Daily Gift System check successfully started.");
         Check();
      } else {
         System.out.println("Daily Gift System check skipped (System disabled).");
      }
   }

   private static void Check() {
      try {
         System.out.println("Checking Daily Gift System properties...");
         FileInputStream setting = new FileInputStream(
               "data/" + (DBConfig.isGanglim ? "Ganglim" : "Jin") + "/dailyGift.cfg");
         Properties setting_ = new Properties();
         setting_.load(setting);
         setting.close();
         String dailycode = setting_.getProperty(toUni("dailyCode"));
         if (!dailycode.isEmpty()) {
            String[] dailyCodes = dailycode.split(",");

            for (int i = 0; i < dailyCodes.length; i++) {
               dailyGifts.add(Integer.parseInt(dailyCodes[i]));
            }
         }

         String dailycount = setting_.getProperty(toUni("dailyCount"));
         if (!dailycount.isEmpty()) {
            String[] counts = dailycount.split(",");

            for (int i = 0; i < counts.length; i++) {
               dailyCounts.add(Integer.parseInt(counts[i]));
            }
         }
      } catch (Exception var6) {
         System.err.println("[Error] Failed to load Daily Gift System settings.");
         var6.printStackTrace();
      }
   }

   public static final void println(String text, int color) {
      System.out.println(text);
   }
}
