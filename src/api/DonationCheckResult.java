package api;

public class DonationCheckResult {
   private int totalPrice = 0;
   private boolean beginnerPackage = false;
   private boolean scrollPackage = false;
   private boolean event = false;
   private boolean event2 = false;

   public DonationCheckResult(int totalPrice, boolean beginnerPackage, boolean scrollPackage, boolean event, boolean event2) {
      this.totalPrice = totalPrice;
      this.beginnerPackage = beginnerPackage;
      this.scrollPackage = scrollPackage;
      this.event = event;
      this.event2 = event2;
   }

   public int getTotalPrice() {
      return this.totalPrice;
   }

   public boolean isBeginnerPackage() {
      return this.beginnerPackage;
   }

   public boolean isScrollPackage() {
      return this.scrollPackage;
   }

   public boolean isEvent() {
      return this.event;
   }

   public boolean isEvent2() {
      return this.event2;
   }
}
