package objects.users.stone;

import objects.users.stats.SecondaryStatFlag;

public enum ImprintedStoneOption {
   None(0, "เนเธกเนเธกเธต", false),
   AllStatP(1, "All Stat %s%d%s%s", false, SecondaryStatFlag.indieStatRBasic),
   PadR(2, "เธเธฅเธฑเธเนเธเธกเธ•เธต %s%d%s%s", true, SecondaryStatFlag.indiePadR),
   MadR(3, "เธเธฅเธฑเธเน€เธงเธ— %s%d%s%s", true, SecondaryStatFlag.indieMadR),
   CriticalDamageR(4, "เธเธฃเธดเธ•เธดเธเธญเธฅเธ”เธฒเน€เธกเธ %s%d%s%s", true, SecondaryStatFlag.indieCD),
   BossDamageR(5, "เธ”เธฒเน€เธกเธเธ—เธณเธ•เนเธญเธเธญเธช %s%d%s%s", true, SecondaryStatFlag.indieBDR),
   IgnoreMobPdpR(6, "เน€เธเธดเธเน€เธเธขเธ•เนเธญเธเธฅเธฑเธเธเนเธญเธเธเธฑเธ %s%d%s", false, SecondaryStatFlag.indieIgnoreMobPdpR),
   CriticalRate(7, "เธญเธฑเธ•เธฃเธฒเธเธฃเธดเธ•เธดเธเธญเธฅ %s%d%s%s", false, SecondaryStatFlag.indieCR),
   PMDR(8, "เนเธเธเธญเธฅเธ”เธฒเน€เธกเธ %s%d%s%s", true, SecondaryStatFlag.indiePMDR),
   DamR(9, "เธ”เธฒเน€เธกเธ %s%d%s%s", false, SecondaryStatFlag.indieDamR);

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
