package objects.fields.gameobject.lifes;

public enum ElitePrefix {
   Prefix_0("ํ ์ผ", 100, 110),
   Prefix_1("ํผํผํ•", 102, 112),
   Prefix_2("๋ง๋ฒ•์ €ํ•ญ์", 103, 113),
   Prefix_3("์ฌ์ํ•๋”", 114),
   Prefix_4("์ฌ๋น ๋ฅธ", 115),
   Prefix_5("๋ด์ธ์", 120),
   Prefix_6("ํํ”ผํ•๋”", 121),
   Prefix_7("ํ—์•ฝ์", 122),
   Prefix_8("๊ธฐ์ ์ํค๋”", 123),
   Prefix_9("์ €์ฃผ์", 124),
   Prefix_10("๋งน๋…์", 125),
   Prefix_11("๋๋ํ•", 126),
   Prefix_12("๋งคํน์", 128),
   Prefix_13("๋…์ ๋ฟ๋ฆฌ๋”", 131),
   Prefix_14("ํผ๋€์", 132),
   Prefix_15("์–ธ๋ฐ๋“", 133),
   Prefix_16("ํฌ์…์ ์ซ์–ดํ•๋”", 134),
   Prefix_17("๋ฉ์ถ”์ง€ ์•๋”", 135),
   Prefix_18("์•”ํ‘์", 136),
   Prefix_19("๋จ๋จํ•", 142),
   Prefix_20("๋ฐ์ฌ์", 145),
   Prefix_21("๋ฌด์ ์", 146),
   Prefix_22("๋ณ€์ ์ ์ฌ", 172),
   Prefix_23("์ํ”์", 174),
   Prefix_24("์์์", 181),
   Prefix_25("์—ญ๋ณ‘์", 211),
   Prefix_26("์ง€ํ๊ด€", 212),
   Prefix_27("๊ฒ€์€ ์ฌ์ฌ์", 213);

   private String prefix;
   private int[] skills;

   private ElitePrefix(String prefix, int... skills) {
      this.prefix = prefix;
      this.skills = skills;
   }

   public static String getElitePrefixBySkills(int skill) {
      for (ElitePrefix pre : values()) {
         for (int skills : pre.skills) {
            if (skill == skills) {
               return pre.prefix;
            }
         }
      }

      return null;
   }
}
