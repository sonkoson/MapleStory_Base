package objects.quest;

public class MobQuest {
   private int questID;
   private int mobID;
   private int needCount;
   private int mobCount;

   public MobQuest(int questID, int mobID, int needCount, int mobCount) {
      this.questID = questID;
      this.mobID = mobID;
      this.needCount = needCount;
      this.mobCount = mobCount;
   }

   public int getQuestID() {
      return this.questID;
   }

   public void setQuestID(int questID) {
      this.questID = questID;
   }

   public int getMobID() {
      return this.mobID;
   }

   public void setMobID(int mobID) {
      this.mobID = mobID;
   }

   public int getNeedCount() {
      return this.needCount;
   }

   public void setNeedCount(int needCount) {
      this.needCount = needCount;
   }

   public int getMobCount() {
      return this.mobCount;
   }

   public void setMobCount(int mobCount) {
      this.mobCount = mobCount;
   }

   public static enum QuestName {
      QUEST_1("์ฃผํฉ๋ฒ์ฏ์ ๊ฐ“ 30๊ฐ ๋ชจ์•์ค๊ธฐ", 1234),
      QUEST_2("์€์ผ๋“ ๋ณด์–ด์ ์ก๊ณณ๋ 50๊ฐ ๋ชจ์•์ค๊ธฐ", 1235),
      QUEST_3("๋ฃจ ๊ด‘์ 50๊ฐ ๋ชจ์•์ค๊ธฐ", 1236),
      QUEST_4("์ถ”์–ต์ ์ฌ์  200๋ง๋ฆฌ ์ฒ์นํ•๊ธฐ", 1237),
      QUEST_5("๋…ธ๋ง ํผํ…์ผ ๊ฒฉํํ•๊ธฐ", 1238),
      QUEST_6("์๊ธ๊ธฐ์ฌ A~D ๊ฐ 50๋ง๋ฆฌ ์ฒ์นํ•๊ธฐ", 1239),
      QUEST_7("๋…ธ๋ง ๋ธ”๋ฌ๋”” ํ€ธ ๊ฒฉํํ•๊ธฐ", 1240),
      QUEST_8("200๋ ๋ฒจ ๋ฌ์ฑํ•๊ธฐ", 1241),
      QUEST_9("๊ธฐ์จ์ ์—๋ฅด๋ค์ค 200๋ง๋ฆฌ ์ฒ์นํ•๊ธฐ", 1242),
      QUEST_10("์•๋ฅด๋ง์ ๋ถ€ํ• 200๋ง๋ฆฌ ์ฒ์นํ•๊ธฐ", 1243),
      QUEST_11("๋ ๋ฒจ 210 ๋ฌ์ฑํ•๊ธฐ", 1244),
      QUEST_12("๋ ๋ฒจ 220 ๋ฌ์ฑํ•๊ธฐ", 1245),
      QUEST_13("๋ผ์ดํฐํ€, ๋ฒ์คํฌ 200๋ง๋ฆฌ ์ฒ์นํ•๊ธฐ", 1246),
      QUEST_14("๋ ๋ฒจ 230 ๋ฌ์ฑํ•๊ธฐ", 1247),
      QUEST_15("๋…ธ๋ง ์ค์ฐ ๊ฒฉํํ•๊ธฐ", 1248),
      QUEST_16("์•ํฌ์ค 200๋ง๋ฆฌ ์ฒ์นํ•๊ธฐ", 1249),
      QUEST_17("๋ ๋ฒจ 240 ๋ฌ์ฑํ•๊ธฐ", 1250),
      QUEST_18("์–ด๋‘ ์ ์ง‘ํ–์, ๋น์ ์ง‘ํ–์ 300๋ง๋ฆฌ ์ฒ์นํ•๊ธฐ", 1251),
      QUEST_19("๋ ๋ฒจ 250 ๋ฌ์ฑํ•๊ธฐ", 1252),
      QUEST_20("ํผ๋์ ํ”ผ์กฐ๋ฌผ 500๋ง๋ฆฌ ์ฒ์นํ•๊ธฐ", 1253),
      QUEST_21("์ ๋ง์ ๋ ๊ฐ 500๋ง๋ฆฌ ์ฒ์นํ•๊ธฐ", 1254),
      QUEST_22("๋ ๋ฒจ 260 ๋ฌ์ฑํ•๊ธฐ", 1255),
      QUEST_23("์•์ธ์คํฐ์จ 1000๋ง๋ฆฌ ์ฒ์นํ•๊ธฐ", 1256),
      QUEST_24("๋ ๋ฒจ 265 ๋ฌ์ฑํ•๊ธฐ", 1257),
      QUEST_25("ํฌ์–ด๋ฒ ๋ฆฌ์จ 1000๋ง๋ฆฌ ์ฒ์นํ•๊ธฐ", 1258),
      QUEST_26("๋ ๋ฒจ 270 ๋ฌ์ฑํ•๊ธฐ", 1259),
      QUEST_27("์— ๋ธ๋ฆฌ์จ 1000๋ง๋ฆฌ ์ฒ์นํ•๊ธฐ", 1260),
      QUEST_28("๋ ๋ฒจ 275 ๋ฌ์ฑํ•๊ธฐ", 1261),
      QUEST_29("๊ฒ๋ฆด๋ผ ์คํํฐ 200๋ง๋ฆฌ ์ฒ์นํ•๊ธฐ", 1263),
      QUEST_30("์ธํ”๋ฃป 200๋ง๋ฆฌ ์ฒ์นํ•๊ธฐ", 1264),
      QUEST_31("๊ฒ€์€ ๋ง๋ฒ•์ฌ ์ฒ์นํ•๊ธฐ", 1265),
      QUEST_32("์ฌ์์• ๋ฐ ๋ ์จ์ ํ”์ ", 1266),
      QUEST_33("์๊ฐ์ ๋€์ ๊ด€ ์•์นด์ด๋ผ์ ํ”์ ", 1267),
      QUEST_1000("์ ๋์จ - ๊ณจ๋“ ์€์ด๋ฒ 50๋ง๋ฆฌ ์ฒ์นํ•๊ธฐ", 1400),
      QUEST_1001("์ ๋์จ - ๋ฏธ๋ ์€์ด๋ฒ 100๋ง๋ฆฌ ์ฒ์นํ•๊ธฐ", 1401);

      final String name;
      final int questID;

      private QuestName(String name, int questID) {
         this.name = name;
         this.questID = questID;
      }

      public static String getQuestName(int questID) {
         for (MobQuest.QuestName name : values()) {
            if (name.questID == questID) {
               return name.name;
            }
         }

         return "";
      }
   }
}
