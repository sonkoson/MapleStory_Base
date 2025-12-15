package constants;

public class JobConstants {
   public static final boolean enableJobs = true;
   public static final int jobOrder = 35;

   public static String getPlayerJobs(int i) {
      String job = "";
      switch (i) {
         case 100:
            job = "Warrior";
            break;
         case 110:
            job = "Fighter";
            break;
         case 111:
            job = "Crusader";
            break;
         case 112:
            job = "Hero";
            break;
         case 120:
            job = "Page";
            break;
         case 121:
            job = "White Knight";
            break;
         case 122:
            job = "Paladin";
            break;
         case 130:
            job = "Spearman";
            break;
         case 131:
            job = "Dragon Knight";
            break;
         case 132:
            job = "Dark Knight";
            break;
         case 200:
            job = "Magician";
            break;
         case 210:
            job = "Wizard (F/P)";
            break;
         case 211:
            job = "Mage (F/P)";
            break;
         case 212:
            job = "Arch Mage (F/P)";
            break;
         case 220:
            job = "Wizard (I/L)";
            break;
         case 221:
            job = "Mage (I/L)";
            break;
         case 222:
            job = "Arch Mage (I/L)";
            break;
         case 230:
            job = "Cleric";
            break;
         case 231:
            job = "Priest";
            break;
         case 232:
            job = "Bishop";
            break;
         case 300:
            job = "Bowman";
            break;
         case 301:
            job = "Archer";
            break;
         case 310:
            job = "Hunter";
            break;
         case 311:
            job = "Ranger";
            break;
         case 312:
            job = "Bowmaster";
            break;
         case 320:
            job = "Crossbowman";
            break;
         case 321:
            job = "Sniper";
            break;
         case 322:
            job = "Marksman";
            break;
         case 330:
         case 331:
         case 332:
            job = "Pathfinder";
            break;
         case 400:
            job = "Thief";
            break;
         case 410:
            job = "Assassin";
            break;
         case 411:
            job = "Hermit";
            break;
         case 412:
            job = "Night Lord";
            break;
         case 420:
            job = "Bandit";
            break;
         case 421:
            job = "Chief Bandit";
            break;
         case 422:
            job = "Shadower";
            break;
         case 430:
            job = "Blade Recruit";
            break;
         case 431:
            job = "Blade Acolyte";
            break;
         case 432:
            job = "Blade Specialist";
            break;
         case 433:
            job = "Blade Lord";
            break;
         case 434:
            job = "Blade Master";
            break;
         case 500:
            job = "Pirate";
            break;
         case 510:
            job = "Brawler";
            break;
         case 511:
            job = "Marauder";
            break;
         case 512:
            job = "Buccaneer";
            break;
         case 520:
            job = "Gunslinger";
            break;
         case 521:
            job = "Outlaw";
            break;
         case 522:
            job = "Corsair";
            break;
         case 530:
            job = "Cannoneer";
            break;
         case 531:
            job = "Cannon Trooper";
            break;
         case 532:
            job = "Cannon Master";
            break;
         case 1100:
         case 1110:
         case 1111:
         case 1112:
            job = "Soul Master";
            break;
         case 1200:
         case 1210:
         case 1211:
         case 1212:
            job = "Flame Wizard";
            break;
         case 1300:
         case 1310:
         case 1311:
         case 1312:
            job = "Wind Breaker";
            break;
         case 1400:
         case 1410:
         case 1411:
         case 1412:
            job = "Night Walker";
            break;
         case 1500:
         case 1510:
         case 1511:
         case 1512:
            job = "Striker";
            break;
         case 2100:
         case 2110:
         case 2111:
         case 2112:
            job = "Aran";
            break;
         case 2200:
         case 2210:
         case 2211:
         case 2212:
         case 2213:
         case 2214:
         case 2215:
         case 2216:
         case 2217:
         case 2218:
            job = "Evan";
            break;
         case 2300:
         case 2310:
         case 2311:
         case 2312:
            job = "Mercedes";
            break;
         case 2400:
         case 2410:
         case 2411:
         case 2412:
            job = "Phantom";
            break;
         case 2500:
         case 2510:
         case 2511:
         case 2512:
            job = "Shade";
            break;
         case 2700:
         case 2710:
         case 2711:
         case 2712:
            job = "Luminous";
            break;
         case 3100:
         case 3110:
         case 3111:
         case 3112:
            job = "Demon Slayer";
            break;
         case 3101:
         case 3120:
         case 3121:
         case 3122:
            job = "Demon Avenger";
            break;
         case 3200:
         case 3210:
         case 3211:
         case 3212:
            job = "Battle Mage";
            break;
         case 3300:
         case 3310:
         case 3311:
         case 3312:
            job = "Wild Hunter";
            break;
         case 3500:
         case 3510:
         case 3511:
         case 3512:
            job = "Mechanic";
            break;
         case 3600:
         case 3610:
         case 3611:
         case 3612:
            job = "Xenon";
            break;
         case 3700:
         case 3710:
         case 3711:
         case 3712:
            job = "Blaster";
            break;
         case 5100:
         case 5110:
         case 5111:
         case 5112:
            job = "Mihile";
            break;
         case 6100:
         case 6110:
         case 6111:
         case 6112:
            job = "Kaiser";
            break;
         case 6300:
         case 6310:
         case 6311:
         case 6312:
            job = "Angelic Buster";
            break;
         case 6400:
         case 6410:
         case 6411:
         case 6412:
            job = "Cadena";
            break;
         case 6500:
         case 6510:
         case 6511:
         case 6512:
            job = "Pink Bean";
            break;
         case 10112:
            job = "Zero";
            break;
         case 14200:
         case 14210:
         case 14211:
         case 14212:
            job = "Kinesis";
            break;
         case 15000:
         case 15200:
         case 15210:
         case 15211:
         case 15212:
            job = "Illium";
            break;
         case 15100:
         case 15110:
         case 15111:
         case 15112:
            job = "Adele";
            break;
         case 15400:
         case 15410:
         case 15411:
         case 15412:
            job = "Kain";
            break;
         case 15500:
         case 15510:
         case 15511:
         case 15512:
            job = "Ark";
            break;
         case 16200:
         case 16210:
         case 16211:
         case 16212:
            job = "Lara";
            break;
         case 16400:
         case 16410:
         case 16411:
         case 16412:
            job = "Hoyoung";
            break;
         default:
            job = "Citizen";
      }
      return job;
   }

   public static enum LoginJob {
      Resistance(0, JobFlag.ENABLED),
      Adventurer(1, JobFlag.ENABLED),
      Cygnus(2, JobFlag.ENABLED),
      Aran(3, JobFlag.ENABLED),
      Evan(4, JobFlag.ENABLED),
      Mercedes(5, JobFlag.ENABLED),
      Demon(6, JobFlag.ENABLED),
      Phantom(7, JobFlag.ENABLED),
      DualBlade(8, JobFlag.ENABLED),
      Mihile(9, JobFlag.ENABLED),
      Luminous(10, JobFlag.ENABLED),
      Kaiser(11, JobFlag.ENABLED),
      AngelicBuster(12, JobFlag.ENABLED),
      Cannoneer(13, JobFlag.ENABLED),
      Xenon(14, JobFlag.ENABLED),
      Zero(15, JobFlag.ENABLED),
      EunWol(16, JobFlag.ENABLED),
      PinkBean(17, JobFlag.ENABLED),
      Kinesis(18, JobFlag.ENABLED),
      Kadena(19, JobFlag.ENABLED),
      Illium(20, JobFlag.ENABLED),
      Ark(21, JobFlag.ENABLED),
      PathFinder(22, JobFlag.ENABLED),
      Hoyoung(23, JobFlag.ENABLED),
      Adele(24, JobFlag.ENABLED),
      Kain(25, JobFlag.ENABLED),
      Yeti(26, JobFlag.ENABLED),
      Lara(27, JobFlag.ENABLED),
      Khali(28, JobFlag.ENABLED);

      private final int jobType;
      private final int flag;

      private LoginJob(int jobType, JobFlag flag) {
         this.jobType = jobType;
         this.flag = flag.getFlag();
      }

      public int getJobType() {
         return this.jobType;
      }

      public int getFlag() {
         return this.flag;
      }

      public static enum JobFlag {
         DISABLED(0),
         ENABLED(1),
         LEVEL_DISABLED(2);

         private final int flag;

         private JobFlag(int flag) {
            this.flag = flag;
         }

         public int getFlag() {
            return this.flag;
         }
      }
   }
}
