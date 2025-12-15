package objects.utils;

public class BossEnterTest {
   public static void main(String[] args) {
      String canTime = "1604511608330";
      long can = Long.valueOf(canTime);
      long now = System.currentTimeMillis();
      int delta = (int)(can - now);
      if (can > 0L && delta > 0) {
         int minute = delta / 1000 / 60;
         System.out.println(minute + "minutes until entry allowed.");
      }
   }
}
