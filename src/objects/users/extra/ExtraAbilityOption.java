package objects.users.extra;

import objects.users.stats.SecondaryStatFlag;

public enum ExtraAbilityOption {
   None(-1, "ไม่มี", false),
   DamageReduceR(0, "Arcane Force +%d", false, SecondaryStatFlag.indieArc),
   Str(1, "STR +%d", false, SecondaryStatFlag.indieSTR),
   Dex(2, "DEX +%d", false, SecondaryStatFlag.indieDEX),
   Int(3, "INT +%d", false, SecondaryStatFlag.indieINT),
   Luk(4, "LUK +%d", false, SecondaryStatFlag.indieLUK),
   AllStat(5, "All Stat +%d", false, SecondaryStatFlag.indieAllStat),
   MaxHp(6, "Max HP +%d", false, SecondaryStatFlag.indieMHP),
   Attack(7, "Attack Power/Magic Power +%d", false, SecondaryStatFlag.indieMAD, SecondaryStatFlag.indiePAD),
   MaxHpR(8, "Max HP +%d%s", false, SecondaryStatFlag.indieMHPR),
   AllStatR(9, "All Stat +%d%s (AP Stats)", false, SecondaryStatFlag.indieStatRBasic),
   AttackR(10, "Attack Power/Magic Power +%d%s", false, SecondaryStatFlag.indieMadR, SecondaryStatFlag.indiePadR),
   CriticalRate(11, "Critical Rate +%d%s", false, SecondaryStatFlag.indieCR),
   IgnoreMobPdpR(12, "Ignore Monster Defense +%d%s", false, SecondaryStatFlag.indieIgnoreMobPdpR),
   BossDamageR(13, "Boss Damage +%d%s", false, SecondaryStatFlag.indieBDR),
   ReduceCooltime(14, "Cooldown -%d sec", true, SecondaryStatFlag.indieCooltimeReduce),
   MesoRateR(15, "Meso Drop Rate +%d%s", true, SecondaryStatFlag.indieMesoAmountRate),
   DropRateR(16, "Item Drop Rate +%d%s", true, SecondaryStatFlag.indieDropPer),
   ExpRateR(17, "EXP Rate +%d%s", true, SecondaryStatFlag.indieEXP),
   CriticalDamage(18, "Critical Damage +%d%s", false, SecondaryStatFlag.indieCD),
   IncMobGen(19, "Max Mobs 1.5x", true, null),
   ReviveInvincible(20, "부활 시 무์  시간 %d초 증가", true, null),
   TerR(21, "Status Resistance +%d%s", true, SecondaryStatFlag.indieTerR, SecondaryStatFlag.indieAsrR),
   PMDR(22, "Final Damage +%d%s", true, SecondaryStatFlag.indiePMDR);

   private int option;
   private String desc;
   private SecondaryStatFlag[] flag;
   private boolean extraOption;

   private ExtraAbilityOption(int option, String desc, boolean extraOption, SecondaryStatFlag... flag) {
      this.option = option;
      this.desc = desc;
      this.flag = flag;
      this.setExtraOption(extraOption);
   }

   public ExtraAbilityOption getOption() {
      for (ExtraAbilityOption g : values()) {
         if (g.option == this.option) {
            return g;
         }
      }

      return null;
   }

   public ExtraAbilityOption getGrade(int option) {
      for (ExtraAbilityOption g : values()) {
         if (g.option == option) {
            return g;
         }
      }

      return null;
   }

   public String getDesc() {
      return this.desc;
   }

   public SecondaryStatFlag[] getFlag() {
      return this.flag;
   }

   public int getOptionID() {
      return this.option;
   }

   public SecondaryStatFlag[] getFlag(ExtraAbilityOption option) {
      for (ExtraAbilityOption g : values()) {
         if (g.option == option.option) {
            return g.flag;
         }
      }

      return null;
   }

   public static ExtraAbilityOption getByName(String name) {
      for (ExtraAbilityOption option : values()) {
         if (option.name().equals(name)) {
            return option;
         }
      }

      return null;
   }

   public static ExtraAbilityOption getByOption(int option) {
      for (ExtraAbilityOption o : values()) {
         if (o.option == option) {
            return o;
         }
      }

      return null;
   }

   public boolean isExtraOption() {
      return this.extraOption;
   }

   public void setExtraOption(boolean extraOption) {
      this.extraOption = extraOption;
   }
}
