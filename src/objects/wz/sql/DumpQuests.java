package objects.wz.sql;

import database.DBConfig;
import database.DBConnection;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import objects.quest.MapleQuestActionType;
import objects.quest.MapleQuestRequirementType;
import objects.utils.Pair;
import objects.wz.provider.MapleData;
import objects.wz.provider.MapleDataProvider;
import objects.wz.provider.MapleDataProviderFactory;
import objects.wz.provider.MapleDataTool;

public class DumpQuests {
   private MapleDataProvider quest;
   protected boolean hadError = false;
   protected boolean update = false;
   protected int id = 0;
   private Connection con = DBConnection.getConnection();

   public DumpQuests(boolean update) throws Exception {
      this.update = update;
      System.setProperty("net.sf.odinms.wzpath", "wz");
      this.quest = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("net.sf.odinms.wzpath") + "/Quest.wz"));
      if (this.quest == null) {
         this.hadError = true;
      }
   }

   public boolean isHadError() {
      return this.hadError;
   }

   public void dumpQuests() throws Exception {
      if (!this.hadError) {
         PreparedStatement psai = this.con
            .prepareStatement("INSERT INTO wz_questactitemdata(uniqueid, itemid, count, period, gender, job, jobEx, prop) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
         PreparedStatement psas = this.con.prepareStatement("INSERT INTO wz_questactskilldata(uniqueid, skillid, skillLevel, masterLevel) VALUES (?, ?, ?, ?)");
         PreparedStatement psaq = this.con.prepareStatement("INSERT INTO wz_questactquestdata(uniqueid, quest, state) VALUES (?, ?, ?)");
         PreparedStatement ps = this.con
            .prepareStatement(
               "INSERT INTO wz_questdata(questid, name, autoStart, autoPreComplete, viewMedalItem, selectedSkillID, blocked, autoAccept, autoComplete, selfStart, selfComplete, startNavi) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
         PreparedStatement psr = this.con
            .prepareStatement("INSERT INTO wz_questreqdata(questid, type, name, stringStore, intStoresFirst, intStoresSecond) VALUES (?, ?, ?, ?, ?, ?)");
         PreparedStatement psq = this.con.prepareStatement("INSERT INTO wz_questpartydata(questid, rank, mode, property, value) VALUES(?,?,?,?,?)");
         PreparedStatement psa = this.con
            .prepareStatement("INSERT INTO wz_questactdata(questid, type, name, intStore, applicableJobs, uniqueid) VALUES (?, ?, ?, ?, ?, ?)");

         try {
            this.dumpQuests(psai, psas, psaq, ps, psr, psq, psa);
         } catch (Exception var12) {
            System.out.println(this.id + " quest.");
            var12.printStackTrace();
            this.hadError = true;
         } finally {
            psai.executeBatch();
            psai.close();
            psas.executeBatch();
            psas.close();
            psaq.executeBatch();
            psaq.close();
            psa.executeBatch();
            psa.close();
            psr.executeBatch();
            psr.close();
            psq.executeBatch();
            psq.close();
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

   public void dumpQuests(
      PreparedStatement psai,
      PreparedStatement psas,
      PreparedStatement psaq,
      PreparedStatement ps,
      PreparedStatement psr,
      PreparedStatement psq,
      PreparedStatement psa
   ) throws Exception {
      if (!this.update) {
         this.delete("DELETE FROM wz_questdata");
         this.delete("DELETE FROM wz_questactdata");
         this.delete("DELETE FROM wz_questactitemdata");
         this.delete("DELETE FROM wz_questactskilldata");
         this.delete("DELETE FROM wz_questactquestdata");
         this.delete("DELETE FROM wz_questreqdata");
         this.delete("DELETE FROM wz_questpartydata");
         System.out.println("Deleted wz_questdata successfully.");
      }

      MapleData checkz = this.quest.getData("Check.img");
      MapleData actz = this.quest.getData("Act.img");
      MapleData infoz = this.quest.getData("QuestInfo.img");
      MapleData pinfoz = this.quest.getData("PQuest.img");
      System.out.println("Adding into wz_questdata.....");
      int uniqueid = 0;

      for (MapleData qz : checkz.getChildren()) {
         this.id = Integer.parseInt(qz.getName());
         if (!this.update || !this.doesExist("SELECT * FROM wz_questdata WHERE questid = " + this.id)) {
            ps.setInt(1, this.id);

            for (int i = 0; i < 2; i++) {
               MapleData reqData = qz.getChildByPath(String.valueOf(i));
               if (reqData != null) {
                  psr.setInt(1, this.id);
                  psr.setInt(2, i);

                  for (MapleData req : reqData.getChildren()) {
                     if (MapleQuestRequirementType.getByWZName(req.getName()) != MapleQuestRequirementType.UNDEFINED) {
                        psr.setString(3, req.getName());
                        if (req.getName().equals("fieldEnter")) {
                           psr.setString(4, String.valueOf(MapleDataTool.getIntConvert("0", req, 0)));
                        } else if (!req.getName().equals("end") && !req.getName().equals("startscript") && !req.getName().equals("endscript")) {
                           psr.setString(4, String.valueOf(MapleDataTool.getInt(req, 0)));
                        } else {
                           psr.setString(4, MapleDataTool.getString(req, ""));
                        }

                        StringBuilder intStore1 = new StringBuilder();
                        StringBuilder intStore2 = new StringBuilder();
                        List<Pair<String, Integer>> dataStore = new ArrayList<>();
                        if (req.getName().equals("infoex")) {
                           List<MapleData> child = req.getChildren();

                           for (int x = 0; x < child.size(); x++) {
                              String key = MapleDataTool.getString(child.get(x).getChildByPath("exVariable"), "");
                              int value = MapleDataTool.getInt(child.get(x).getChildByPath("value"), -1);
                              if (!key.isEmpty() && value >= 0) {
                                 dataStore.add(new Pair<>(key, value));
                              }
                           }
                        } else if (req.getName().equals("job")) {
                           List<MapleData> child = req.getChildren();

                           for (int xx = 0; xx < child.size(); xx++) {
                              dataStore.add(new Pair<>(String.valueOf(i), MapleDataTool.getInt(child.get(xx), -1)));
                           }
                        } else if (req.getName().equals("skill")) {
                           List<MapleData> child = req.getChildren();

                           for (int xx = 0; xx < child.size(); xx++) {
                              MapleData childdata = child.get(xx);
                              if (childdata != null) {
                                 dataStore.add(
                                    new Pair<>(
                                       String.valueOf(MapleDataTool.getInt(childdata.getChildByPath("id"), 0)),
                                       MapleDataTool.getInt(childdata.getChildByPath("acquire"), 0)
                                    )
                                 );
                              }
                           }
                        } else if (req.getName().equals("quest")) {
                           List<MapleData> child = req.getChildren();

                           for (int xxx = 0; xxx < child.size(); xxx++) {
                              MapleData childdata = child.get(xxx);
                              if (childdata != null) {
                                 dataStore.add(
                                    new Pair<>(
                                       String.valueOf(MapleDataTool.getInt(childdata.getChildByPath("id"), 0)),
                                       MapleDataTool.getInt(childdata.getChildByPath("state"), 0)
                                    )
                                 );
                              }
                           }
                        } else if (!req.getName().equals("item") && !req.getName().equals("mob")) {
                           if (req.getName().equals("mbcard")) {
                              for (MapleData childdata : req.getChildren()) {
                                 if (childdata != null) {
                                    dataStore.add(
                                       new Pair<>(
                                          String.valueOf(MapleDataTool.getInt(childdata.getChildByPath("id"), 0)),
                                          MapleDataTool.getInt(childdata.getChildByPath("min"), 0)
                                       )
                                    );
                                 }
                              }
                           } else if (req.getName().equals("pet")) {
                              for (MapleData childdatax : req.getChildren()) {
                                 if (childdatax != null) {
                                    dataStore.add(new Pair<>(String.valueOf(i), MapleDataTool.getInt(childdatax.getChildByPath("id"), 0)));
                                 }
                              }
                           }
                        } else {
                           List<MapleData> child = req.getChildren();

                           for (int xxxx = 0; xxxx < child.size(); xxxx++) {
                              MapleData childdataxx = child.get(xxxx);
                              if (childdataxx != null) {
                                 dataStore.add(
                                    new Pair<>(
                                       String.valueOf(MapleDataTool.getInt(childdataxx.getChildByPath("id"), 0)),
                                       MapleDataTool.getInt(childdataxx.getChildByPath("count"), 0)
                                    )
                                 );
                              }
                           }
                        }

                        for (Pair<String, Integer> data : dataStore) {
                           if (intStore1.length() > 0) {
                              intStore1.append(", ");
                              intStore2.append(", ");
                           }

                           intStore1.append(data.getLeft());
                           intStore2.append(data.getRight());
                        }

                        psr.setString(5, intStore1.toString());
                        psr.setString(6, intStore2.toString());
                        psr.addBatch();
                     }
                  }
               }

               MapleData actData = actz.getChildByPath(this.id + "/" + i);
               if (actData != null) {
                  psa.setInt(1, this.id);
                  psa.setInt(2, i);

                  for (MapleData act : actData.getChildren()) {
                     if (MapleQuestActionType.getByWZName(act.getName()) != MapleQuestActionType.UNDEFINED) {
                        psa.setString(3, act.getName());
                        if (act.getName().equals("sp")) {
                           psa.setInt(4, MapleDataTool.getIntConvert("0/sp_value", act, 0));
                        } else {
                           psa.setInt(4, MapleDataTool.getInt(act, 0));
                        }

                        StringBuilder applicableJobs = new StringBuilder();
                        if (!act.getName().equals("sp") && !act.getName().equals("skill")) {
                           if (act.getChildByPath("job") != null) {
                              for (MapleData d : act.getChildByPath("job")) {
                                 if (applicableJobs.length() > 0) {
                                    applicableJobs.append(", ");
                                 }

                                 applicableJobs.append(MapleDataTool.getInt(d, 0));
                              }
                           }
                        } else {
                           for (int index = 0; act.getChildByPath(index + "/job") != null; index++) {
                              for (MapleData d : act.getChildByPath(index + "/job")) {
                                 if (applicableJobs.length() > 0) {
                                    applicableJobs.append(", ");
                                 }

                                 applicableJobs.append(MapleDataTool.getInt(d, 0));
                              }
                           }
                        }

                        psa.setString(5, applicableJobs.toString());
                        psa.setInt(6, -1);
                        if (act.getName().equals("item")) {
                           psa.setInt(6, ++uniqueid);
                           psai.setInt(1, uniqueid);

                           for (MapleData iEntry : act.getChildren()) {
                              psai.setInt(2, MapleDataTool.getInt("id", iEntry, 0));
                              psai.setInt(3, MapleDataTool.getInt("count", iEntry, 0));
                              psai.setInt(4, MapleDataTool.getInt("period", iEntry, 0));
                              psai.setInt(5, MapleDataTool.getInt("gender", iEntry, 2));
                              psai.setInt(6, MapleDataTool.getInt("job", iEntry, -1));
                              psai.setInt(7, MapleDataTool.getInt("jobEx", iEntry, -1));
                              if (iEntry.getChildByPath("prop") == null) {
                                 psai.setInt(8, -2);
                              } else {
                                 psai.setInt(8, MapleDataTool.getInt("prop", iEntry, -1));
                              }

                              psai.addBatch();
                           }
                        } else if (act.getName().equals("skill")) {
                           psa.setInt(6, ++uniqueid);
                           psas.setInt(1, uniqueid);

                           for (MapleData sEntry : act) {
                              psas.setInt(2, MapleDataTool.getInt("id", sEntry, 0));
                              psas.setInt(3, MapleDataTool.getInt("skillLevel", sEntry, 0));
                              psas.setInt(4, MapleDataTool.getInt("masterLevel", sEntry, 0));
                              psas.addBatch();
                           }
                        } else if (act.getName().equals("quest")) {
                           psa.setInt(6, ++uniqueid);
                           psaq.setInt(1, uniqueid);

                           for (MapleData sEntry : act) {
                              psaq.setInt(2, MapleDataTool.getInt("id", sEntry, 0));
                              psaq.setInt(3, MapleDataTool.getInt("state", sEntry, 0));
                              psaq.addBatch();
                           }
                        }

                        psa.addBatch();
                     }
                  }
               }
            }

            MapleData infoData = infoz.getChildByPath(String.valueOf(this.id));
            if (infoData != null) {
               ps.setString(2, MapleDataTool.getString("name", infoData, ""));
               ps.setInt(3, MapleDataTool.getInt("autoStart", infoData, 0) > 0 ? 1 : 0);
               ps.setInt(4, MapleDataTool.getInt("autoPreComplete", infoData, 0) > 0 ? 1 : 0);
               ps.setInt(5, MapleDataTool.getInt("viewMedalItem", infoData, 0));
               ps.setInt(6, MapleDataTool.getInt("selectedSkillID", infoData, 0));
               ps.setInt(7, MapleDataTool.getInt("blocked", infoData, 0));
               ps.setInt(8, MapleDataTool.getInt("autoAccept", infoData, 0));
               ps.setInt(9, MapleDataTool.getInt("autoComplete", infoData, 0));
               ps.setInt(10, MapleDataTool.getInt("selfStart", infoData, 0));
               ps.setInt(11, MapleDataTool.getInt("selfComplete", infoData, 0));
               ps.setInt(12, MapleDataTool.getInt("startNavi", infoData, 0));
            } else {
               ps.setString(2, "");
               ps.setInt(3, 0);
               ps.setInt(4, 0);
               ps.setInt(5, 0);
               ps.setInt(6, 0);
               ps.setInt(7, 0);
               ps.setInt(8, 0);
               ps.setInt(9, 0);
               ps.setInt(10, 0);
               ps.setInt(11, 0);
               ps.setInt(12, 0);
            }

            ps.addBatch();
            MapleData pinfoData = pinfoz.getChildByPath(String.valueOf(this.id));
            if (pinfoData != null && pinfoData.getChildByPath("rank") != null) {
               psq.setInt(1, this.id);

               for (MapleData d : pinfoData.getChildByPath("rank")) {
                  psq.setString(2, d.getName());

                  for (MapleData c : d) {
                     psq.setString(3, c.getName());

                     for (MapleData b : c) {
                        psq.setString(4, b.getName());
                        psq.setInt(5, MapleDataTool.getInt(b, 0));
                        psq.addBatch();
                     }
                  }
               }
            }
         }
      }

      System.out.println("Done wz_questdata...");
   }

   public int currentId() {
      return this.id;
   }

   public static void main(String[] args) {
      if (!DBConfig.DB_PASSWORD.equals("J2vs@efh6@K6!2")) {
         System.setProperty("net.sf.odinms.wzpath", "J:\\Choi\\MapleStory\\Git\\wz");
      }

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
         DumpQuests dq = new DumpQuests(update);
         System.out.println("Dumping quests");
         dq.dumpQuests();
         hadError |= dq.isHadError();
         currentQuest = dq.currentId();
      } catch (Exception var13) {
         hadError = true;
         var13.printStackTrace();
         System.out.println(currentQuest + " quest.");
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
