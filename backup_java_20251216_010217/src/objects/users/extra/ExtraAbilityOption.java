package objects.users.extra;

import objects.users.stats.SecondaryStatFlag;

public enum ExtraAbilityOption {
   None(-1, "์—์", false),
   DamageReduceR(0, "์•์ผ€์ธ ํฌ์ค +%d ์ฆ๊ฐ€", false, SecondaryStatFlag.indieArc),
   Str(1, "STR +%d ์ฆ๊ฐ€", false, SecondaryStatFlag.indieSTR),
   Dex(2, "DEX +%d ์ฆ๊ฐ€", false, SecondaryStatFlag.indieDEX),
   Int(3, "INT +%d ์ฆ๊ฐ€", false, SecondaryStatFlag.indieINT),
   Luk(4, "LUK +%d ์ฆ๊ฐ€", false, SecondaryStatFlag.indieLUK),
   AllStat(5, "์ฌ์คํฏ +%d ์ฆ๊ฐ€", false, SecondaryStatFlag.indieAllStat),
   MaxHp(6, "์ต๋€ HP +%d ์ฆ๊ฐ€", false, SecondaryStatFlag.indieMHP),
   Attack(7, "๊ณต๊ฒฉ๋ ฅ/๋ง๋ ฅ +%d ์ฆ๊ฐ€", false, SecondaryStatFlag.indieMAD, SecondaryStatFlag.indiePAD),
   MaxHpR(8, "์ต๋€ HP +%d%s ์ฆ๊ฐ€", false, SecondaryStatFlag.indieMHPR),
   AllStatR(9, "์ฌ์คํฏ +%d%s ์ฆ๊ฐ€ (์ง์ ‘ ํฌ์ํ• ์คํฏ)", false, SecondaryStatFlag.indieStatRBasic),
   AttackR(10, "๊ณต๊ฒฉ๋ ฅ/๋ง๋ ฅ +%d%s ์ฆ๊ฐ€", false, SecondaryStatFlag.indieMadR, SecondaryStatFlag.indiePadR),
   CriticalRate(11, "ํฌ๋ฆฌํฐ์ปฌ ํ•๋ฅ  +%d%s ์ฆ๊ฐ€", false, SecondaryStatFlag.indieCR),
   IgnoreMobPdpR(12, "๋ชฌ์คํฐ ๋ฐฉ์–ด๋ ฅ ๋ฌด์ +%d%s", false, SecondaryStatFlag.indieIgnoreMobPdpR),
   BossDamageR(13, "๋ณด์ค ๊ณต๊ฒฉ ์ ๋ฐ๋ฏธ์ง€ +%d%s ์ฆ๊ฐ€", false, SecondaryStatFlag.indieBDR),
   ReduceCooltime(14, "์ฌ์ฌ์ฉ ๋€๊ธฐ์๊ฐ %d์ด ๊ฐ์", true, SecondaryStatFlag.indieCooltimeReduce),
   MesoRateR(15, "๋ฉ”์ ํ๋“๋ +%d%s ์ฆ๊ฐ€", true, SecondaryStatFlag.indieMesoAmountRate),
   DropRateR(16, "์•์ดํ… ๋“๋กญ๋ฅ  +%d%s ์ฆ๊ฐ€", true, SecondaryStatFlag.indieDropPer),
   ExpRateR(17, "๊ฒฝํ—์น ํ๋“๋ +%d%s ์ฆ๊ฐ€", true, SecondaryStatFlag.indieEXP),
   CriticalDamage(18, "ํฌ๋ฆฌํฐ์ปฌ ๋ฐ๋ฏธ์ง€ +%d%s ์ฆ๊ฐ€", false, SecondaryStatFlag.indieCD),
   IncMobGen(19, "๋ชฌ์คํฐ ๋ฆฌ์   ๊ฐ์ฒด์ 1.5๋ฐฐ ์ฆ๊ฐ€", true, null),
   ReviveInvincible(20, "๋ถ€ํ ์ ๋ฌด์  ์๊ฐ %d์ด ์ฆ๊ฐ€", true, null),
   TerR(21, "์ํ ์ด์ ๋ด์ฑ +%d%s ์ฆ๊ฐ€", true, SecondaryStatFlag.indieTerR, SecondaryStatFlag.indieAsrR),
   PMDR(22, "์ต์ข… ๋ฐ๋ฏธ์ง€ +%d%s ์ฆ๊ฐ€", true, SecondaryStatFlag.indiePMDR);

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
