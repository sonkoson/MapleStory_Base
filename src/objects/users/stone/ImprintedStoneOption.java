package objects.users.stone;

import objects.users.stats.SecondaryStatFlag;

public enum ImprintedStoneOption {
   None(0, "ไม่มี", false),
   AllStatP(1, "All Stat %s%d%s%s", false, SecondaryStatFlag.indieStatRBasic),
   PadR(2, "พลังโจมตี%s%d%s%s", true, SecondaryStatFlag.indiePadR),
   MadR(3, "พลังเǷ %s%d%s%s", true, SecondaryStatFlag.indieMadR),
   CriticalDamageR(4, "ครԵิคอŴาเมจ %s%d%s%s", true, SecondaryStatFlag.indieCD),
   BossDamageR(5, "ดาเมจทӵ่อบอʁ%s%d%s%s", true, SecondaryStatFlag.indieBDR),
   IgnoreMobPdpR(6, "เพิกเฉµ่อพลังป้องกัน %s%d%s", false, SecondaryStatFlag.indieIgnoreMobPdpR),
   CriticalRate(7, "อѵราครԵิคอล%s%d%s%s", false, SecondaryStatFlag.indieCR),
   PMDR(8, "ไฟนอŴาเมจ %s%d%s%s", true, SecondaryStatFlag.indiePMDR),
   DamR(9, "ดาเมจ %s%d%s%s", false, SecondaryStatFlag.indieDamR);

   private int option;
   private String desc;
   private boolean special;
   private SecondaryStatFlag[] flag;

   private ImprintedStoneOption(int option, String desc, boolean special, SecondaryStatFlag... flag) {
      this.option = option;
      this.desc = desc;
      this.special = special;
      this.flag = flag;
   }

   public int getOption() {
      return this.option;
   }

   public String getDesc() {
      return this.desc;
   }

   public static ImprintedStoneOption getByOption(int option) {
      for (ImprintedStoneOption o : values()) {
         if (o.option == option) {
            return o;
         }
      }

      return null;
   }

   public SecondaryStatFlag[] getFlag() {
      return this.flag;
   }
}
