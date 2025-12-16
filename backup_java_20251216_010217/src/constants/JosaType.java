package constants;

public enum JosaType {
   Type_0(0),
   Type_1(1),
   Type_2(2),
   Type_3(3);

   private int type;

   private JosaType(int type) {
      this.type = type;
   }

   public int getType() {
      return this.type;
   }
}
