package objects.fields;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class FieldMonsterSpawner {
   private final int spawnMonster;
   private long lastSpawnTime;
   private long cycleTime;
   private List<Point> spawnPositions;
   private String spawnDesc;

   public static FieldMonsterSpawner getSpawner(int mapID) {
      switch (mapID) {
         case 100020101: {
            FieldMonsterSpawner spawner = new FieldMonsterSpawner(6130101);
            spawner.setCycleTime(600000L);
            spawner.addSpawnPosition(new Point(-311, 201));
            spawner.addSpawnPosition(new Point(-903, 197));
            spawner.addSpawnPosition(new Point(-568, 196));
            spawner.setSpawnDesc("์–ด๋””์ ๊ฐ€ ์ปค๋ค๋€ ๋ฒ์ฏ์ด ๋ํ€๋ฌ์ต๋๋ค.");
            return spawner;
         }
         case 100020301: {
            FieldMonsterSpawner spawner = new FieldMonsterSpawner(8220007);
            spawner.setCycleTime(600000L);
            spawner.addSpawnPosition(new Point(-188, -657));
            spawner.addSpawnPosition(new Point(625, -660));
            spawner.addSpawnPosition(new Point(508, -648));
            spawner.setSpawnDesc("์–ด๋””์ ๊ฐ€ ์ปค๋ค๋€ ํ๋€ ๋ฒ์ฏ์ด ๋ํ€๋ฌ์ต๋๋ค.");
            return spawner;
         }
         case 100020401: {
            FieldMonsterSpawner spawner = new FieldMonsterSpawner(6300005);
            spawner.setCycleTime(600000L);
            spawner.addSpawnPosition(new Point(-130, -773));
            spawner.addSpawnPosition(new Point(504, -760));
            spawner.addSpawnPosition(new Point(608, -641));
            spawner.setSpawnDesc("์–ด๋””์ ๊ฐ€ ์์ฐํ• ๊ธฐ์ด์ ํ’๊ธฐ๋” ์ปค๋ค๋€ ๋ฒ์ฏ์ด ๋ํ€๋ฌ์ต๋๋ค.");
            return spawner;
         }
         case 102020500: {
            FieldMonsterSpawner spawner = new FieldMonsterSpawner(3220000);
            spawner.setCycleTime(600000L);
            spawner.addSpawnPosition(new Point(1121, 2130));
            spawner.addSpawnPosition(new Point(483, 2171));
            spawner.addSpawnPosition(new Point(1474, 1706));
            spawner.setSpawnDesc("๋ฐ”์์ฐ์ ์ธ๋ฆฌ๋” ๋ฐ๊ฑธ์ ์๋ฆฌ์€ ํ•จ๊ป ์คํ…€ํ”ผ๊ฐ€ ๋ํ€๋ฌ์ต๋๋ค.");
            return spawner;
         }
         case 103030400: {
            FieldMonsterSpawner spawner = new FieldMonsterSpawner(6220000);
            spawner.setCycleTime(600000L);
            spawner.addSpawnPosition(new Point(-831, 109));
            spawner.addSpawnPosition(new Point(1525, -75));
            spawner.addSpawnPosition(new Point(-511, 107));
            spawner.setSpawnDesc("๋ช ์์—์ ๊ฑฐ๋€ํ• ์•…์–ด ๋ค์ผ์ด ์ฌ๋ผ์”์ต๋๋ค.");
            return spawner;
         }
         case 106030102: {
            FieldMonsterSpawner spawner = new FieldMonsterSpawner(3300109);
            spawner.setCycleTime(60000L);
            spawner.addSpawnPosition(new Point(479, 156));
            spawner.setSpawnDesc("๋ฐ”์ดํน ๊ตฐ๋จ์ด ๋ํ€๋ฌ์ต๋๋ค.");
            return spawner;
         }
         case 106030800: {
            FieldMonsterSpawner spawner = new FieldMonsterSpawner(3300110);
            spawner.setCycleTime(1800000L);
            spawner.addSpawnPosition(new Point(360, 290));
            spawner.setSpawnDesc("๊ฒ€์€ ๋ฐ”์ดํน์ด ๋ชจ์ต์ ๋“๋ฌ๋์ต๋๋ค.");
            return spawner;
         }
         case 250010300: {
            FieldMonsterSpawner spawner = new FieldMonsterSpawner(5120507);
            spawner.setCycleTime(600000L);
            spawner.addSpawnPosition(new Point(-536, -422));
            spawner.setSpawnDesc("์•ฝ์ด๋ฐญ ์ฌ์ด๋ก ๊ฑฐ๋€ ๋๋ผ์ง€๊ฐ€ ๋ํ€๋ฌ์ต๋๋ค.");
            return spawner;
         }
         case 250010504: {
            FieldMonsterSpawner spawner = new FieldMonsterSpawner(7220002);
            spawner.setCycleTime(600000L);
            spawner.addSpawnPosition(new Point(-303, 543));
            spawner.addSpawnPosition(new Point(227, 543));
            spawner.addSpawnPosition(new Point(719, 543));
            spawner.setSpawnDesc("์ฃผ๋ณ€์ ํ๋ฅด๋” ์”๊ธฐ๊ฐ€ ๊ฐ•ํ•ด์ก์ต๋๋ค. ๊ธฐ๋ถ๋์ ๊ณ ์–‘์ด ์ธ์์๋ฆฌ๊ฐ€ ๋“ค๋ฆฝ๋๋ค.");
            return spawner;
         }
         case 251010101: {
            FieldMonsterSpawner spawner = new FieldMonsterSpawner(6090002);
            spawner.setCycleTime(600000L);
            spawner.addSpawnPosition(new Point(660, 124));
            spawner.setSpawnDesc("๋‘๋ฃจ๋ง๊ธฐ์— ๋ชธ์ ์จ๊ฒผ๋ ๋€๋๋ฌด ๋ฌด์ฌ๊ฐ€ ๋ชจ์ต์ ๋“๋ฌ๋์ต๋๋ค.");
            return spawner;
         }
         case 251010102: {
            FieldMonsterSpawner spawner = new FieldMonsterSpawner(5220004);
            spawner.setCycleTime(600000L);
            spawner.addSpawnPosition(new Point(-41, 124));
            spawner.addSpawnPosition(new Point(-173, 126));
            spawner.addSpawnPosition(new Point(79, 118));
            spawner.setSpawnDesc("๋ฌผ๋ฐ‘์—์ ์ค๋ฉ€์ค๋ฉ€ ๋€์•์ง€๋ค๊ฐ€ ๋ํ€๋ฌ์ต๋๋ค.");
            return spawner;
         }
         case 270010500: {
            FieldMonsterSpawner spawner = new FieldMonsterSpawner(8220004);
            spawner.setCycleTime(600000L);
            spawner.addSpawnPosition(new Point(70, -876));
            spawner.addSpawnPosition(new Point(-124, -876));
            spawner.addSpawnPosition(new Point(-333, -876));
            spawner.setSpawnDesc("์ถ”์–ต์ ๊ธธ์— ๋๋๊ฐ€ ๋ํ€๋ฌ์ต๋๋ค.");
            return spawner;
         }
         case 270020500: {
            FieldMonsterSpawner spawner = new FieldMonsterSpawner(8220005);
            spawner.setCycleTime(600000L);
            spawner.addSpawnPosition(new Point(64, -528));
            spawner.addSpawnPosition(new Point(100, -528));
            spawner.addSpawnPosition(new Point(0, -528));
            spawner.setSpawnDesc("ํํ์ ๊ธธ์— ๋ฆด๋ฆฌ๋…ธํ๊ฐ€ ๋ํ€๋ฌ์ต๋๋ค.");
            return spawner;
         }
         case 270030500: {
            FieldMonsterSpawner spawner = new FieldMonsterSpawner(8220006);
            spawner.setCycleTime(600000L);
            spawner.addSpawnPosition(new Point(423, -569));
            spawner.setSpawnDesc("๋ง๊ฐ์ ๊ธธ์— ๋ผ์ด์นด๊ฐ€ ๋ํ€๋ฌ์ต๋๋ค.");
            return spawner;
         }
         default:
            return null;
      }
   }

   public boolean check(Field field) {
      return this.getLastSpawnTime() == 0L
         ? true
         : field.getMonsterById(this.getSpawnMonster()) == null && System.currentTimeMillis() >= this.getLastSpawnTime() + this.getCycleTime();
   }

   public FieldMonsterSpawner(int spawnMonster) {
      this.spawnMonster = spawnMonster;
      this.spawnPositions = new ArrayList<>();
   }

   public int getSpawnMonster() {
      return this.spawnMonster;
   }

   public long getLastSpawnTime() {
      return this.lastSpawnTime;
   }

   public void setLastSpawnTime(long lastSpawnTime) {
      this.lastSpawnTime = lastSpawnTime;
   }

   public long getCycleTime() {
      return this.cycleTime;
   }

   public void setCycleTime(long cycleTime) {
      this.cycleTime = cycleTime;
   }

   public List<Point> getSpawnPositions() {
      return this.spawnPositions;
   }

   public void addSpawnPosition(Point pos) {
      this.spawnPositions.add(pos);
   }

   public String getSpawnDesc() {
      return this.spawnDesc;
   }

   public void setSpawnDesc(String spawnDesc) {
      this.spawnDesc = spawnDesc;
   }
}
