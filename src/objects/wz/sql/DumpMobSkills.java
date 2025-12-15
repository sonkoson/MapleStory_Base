package objects.wz.sql;

import database.DBConnection;
import java.awt.Point;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import objects.wz.provider.MapleData;
import objects.wz.provider.MapleDataDirectoryEntry;
import objects.wz.provider.MapleDataFileEntry;
import objects.wz.provider.MapleDataProvider;
import objects.wz.provider.MapleDataProviderFactory;
import objects.wz.provider.MapleDataTool;

public class DumpMobSkills {
   private MapleDataProvider skill;
   protected boolean hadError = false;
   protected boolean update = false;
   protected int id = 0;
   private Connection con = DBConnection.getConnection();

   public DumpMobSkills(boolean update) throws Exception {
      this.update = update;
      this.skill = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("net.sf.odinms.wzpath") + "/Skill.wz"));
      if (this.skill == null) {
         this.hadError = true;
      }
   }

   public boolean isHadError() {
      return this.hadError;
   }

   public void dumpMobSkills() throws Exception {
      if (!this.hadError) {
         PreparedStatement ps = this.con
            .prepareStatement(
               "INSERT INTO wz_mobskilldata(skillid, `level`, hp, mpcon, x, y, time, prop, `limit`, spawneffect,`interval`, summons, ltx, lty, rbx, rby, once) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );

         try {
            this.dumpMobSkills(ps);
         } catch (Exception var6) {
            System.out.println(this.id + " skill.");
            var6.printStackTrace();
            this.hadError = true;
         } finally {
            ps.executeBatch();
            ps.close();
         }
      }
   }

   public void delete(String sql) throws Exception {
      PreparedStatement ps = this.con.prepareStatement(sql);
      ps.executeUpdate();
      ps.close();
   }

   public boolean doesExist(String sql) throws Exception {
      PreparedStatement ps = this.con.prepareStatement(sql);
      ResultSet rs = ps.executeQuery();
      boolean ret = rs.next();
      rs.close();
      ps.close();
      return ret;
   }

   public void dumpMobSkills(PreparedStatement ps) throws Exception {
      if (!this.update) {
         this.delete("DELETE FROM wz_mobskilldata");
         System.out.println("Deleted wz_mobskilldata successfully.");
      }

      System.out.println("Adding into wz_mobskilldata.....");

      for (MapleDataDirectoryEntry root : new ArrayList<MapleDataDirectoryEntry>() {
         {
            this.add(DumpMobSkills.this.skill.getRoot("MobSkill_000.wz"));
            this.add(DumpMobSkills.this.skill.getRoot("MobSkill_001.wz"));
         }
      }) {
         for (MapleDataFileEntry topDir : root.getFiles()) {
            for (MapleData data : this.skill.getData(topDir.getName())) {
               if (data.getName().equals("level")) {
                  this.id = Integer.parseInt(topDir.getName().split(".img")[0]);

                  for (MapleData level : data.getChildren()) {
                     int lvl = Integer.parseInt(level.getName());
                     if (!this.update || !this.doesExist("SELECT * FROM wz_mobskilldata WHERE skillid = " + this.id + " AND level = " + lvl)) {
                        ps.setInt(1, this.id);
                        ps.setInt(2, lvl);
                        ps.setInt(3, MapleDataTool.getInt("hp", level, 100));
                        ps.setInt(4, MapleDataTool.getInt("mpCon", level, 0));
                        ps.setInt(5, MapleDataTool.getInt("x", level, 1));
                        ps.setInt(6, MapleDataTool.getInt("y", level, 1));
                        ps.setInt(7, MapleDataTool.getInt("time", level, 0));
                        ps.setInt(8, MapleDataTool.getInt("prop", level, 100));
                        ps.setInt(9, MapleDataTool.getInt("limit", level, 0));
                        ps.setInt(10, MapleDataTool.getInt("summonEffect", level, 0));
                        ps.setInt(11, MapleDataTool.getInt("interval", level, 0));
                        StringBuilder summ = new StringBuilder();
                        List<Integer> toSummon = new ArrayList<>();

                        for (int i = 0; i > -1 && level.getChildByPath(String.valueOf(i)) != null; i++) {
                           toSummon.add(MapleDataTool.getInt(level.getChildByPath(String.valueOf(i)), 0));
                        }

                        for (Integer summon : toSummon) {
                           if (summ.length() > 0) {
                              summ.append(", ");
                           }

                           summ.append(String.valueOf(summon));
                        }

                        ps.setString(12, summ.toString());
                        if (level.getChildByPath("lt") != null) {
                           Point lt = (Point)level.getChildByPath("lt").getData();
                           ps.setInt(13, lt.x);
                           ps.setInt(14, lt.y);
                        } else {
                           ps.setInt(13, 0);
                           ps.setInt(14, 0);
                        }

                        if (level.getChildByPath("rb") != null) {
                           Point rb = (Point)level.getChildByPath("rb").getData();
                           ps.setInt(15, rb.x);
                           ps.setInt(16, rb.y);
                        } else {
                           ps.setInt(15, 0);
                           ps.setInt(16, 0);
                        }

                        ps.setByte(17, (byte)(MapleDataTool.getInt("summonOnce", level, 0) > 0 ? 1 : 0));
                        System.out.println("Added skill: " + this.id + " level " + lvl);
                        ps.addBatch();
                     }
                  }
               }
            }
         }
      }

      System.out.println("Done wz_mobskilldata...");
   }

   public int currentId() {
      return this.id;
   }

   public static void main(String[] args) {
      DBConnection.init();
      boolean hadError = false;
      boolean update = false;
      long startTime = System.currentTimeMillis();

      for (String file : args) {
         if (file.equalsIgnoreCase("-update")) {
            update = true;
         }
      }

      int currentQuest = 0;

      try {
         DumpMobSkills dq = new DumpMobSkills(update);
         System.out.println("Dumping mobskills");
         dq.dumpMobSkills();
         hadError |= dq.isHadError();
         currentQuest = dq.currentId();
      } catch (Exception var13) {
         hadError = true;
         var13.printStackTrace();
         System.out.println(currentQuest + " skill.");
      }

      long endTime = System.currentTimeMillis();
      double elapsedSeconds = (endTime - startTime) / 1000.0;
      int elapsedSecs = (int)elapsedSeconds % 60;
      int elapsedMinutes = (int)(elapsedSeconds / 60.0);
      String withErrors = "";
      if (hadError) {
         withErrors = " with errors";
      }

      System.out.println("Finished" + withErrors + " in " + elapsedMinutes + " minutes " + elapsedSecs + " seconds");
   }
}
