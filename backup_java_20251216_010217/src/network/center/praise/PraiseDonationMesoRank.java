package network.center.praise;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import logging.LoggingManager;
import logging.entry.CustomLog;
import network.game.GameServer;
import objects.users.MapleCharacter;

public class PraiseDonationMesoRank {
   private static Map<Integer, PraiseDonationMesoRankEntry> ranks = new HashMap<>();

   public static synchronized void loadRanks() {
      ranks.clear();
      PreparedStatement ps = null;
      ResultSet rs = null;

      try (Connection con = DBConnection.getConnection()) {
         ps = con.prepareStatement("SELECT `total_meso`, `account_id` FROM `praise_donation_meso_rank` ORDER BY `total_meso` DESC");
         rs = ps.executeQuery();

         while (rs.next()) {
            String name = "";
            int accountID = rs.getInt("account_id");
            long totalMeso = rs.getLong("total_meso");
            PreparedStatement ps2 = con.prepareStatement("SELECT `name` FROM `characters` WHERE `accountid` = ? ORDER BY `level` DESC");
            ps2.setInt(1, accountID);
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
               name = rs2.getString("name");
            }

            rs2.close();
            ps2.close();
            if (!name.isEmpty()) {
               ranks.put(accountID, new PraiseDonationMesoRankEntry(name, accountID, totalMeso));
            }
         }
      } catch (SQLException var21) {
         var21.printStackTrace();
      } finally {
         try {
            if (ps != null) {
               ps.close();
               PreparedStatement var23 = null;
            }

            if (rs != null) {
               rs.close();
               ResultSet var24 = null;
            }
         } catch (SQLException var18) {
         }
      }

      System.out.println("Praise Newbie Donation ranking updated.");
   }

   public static void saveRank() {
      PreparedStatement ps = null;

      try (Connection con = DBConnection.getConnection()) {
         ps = con.prepareStatement("DELETE FROM `praise_donation_meso_rank`");
         ps.executeQuery();
         ps.close();

         for (Entry<Integer, PraiseDonationMesoRankEntry> entry : ranks.entrySet()) {
            PraiseDonationMesoRankEntry e = entry.getValue();
            ps = con.prepareStatement("INSERT INTO `praise_donation_meso_rank` (`account_id`, `total_meso`) VALUES (?, ?)");
            ps.setInt(1, e.getAccountID());
            ps.setLong(2, e.getTotalMeso());
            ps.executeQuery();
            ps.close();
         }
      } catch (SQLException var17) {
         var17.printStackTrace();
      } finally {
         try {
            if (ps != null) {
               ps.close();
               PreparedStatement var19 = null;
            }
         } catch (SQLException var14) {
            var14.printStackTrace();
         }
      }

      System.out.println("Praise Newbie Donation ranking saved.");
   }

   public static List<PraiseDonationMesoRankEntry> getTopRanker() {
      List<PraiseDonationMesoRankEntry> ret = new ArrayList<>();
      List<PraiseDonationMesoRankEntry> list = new ArrayList<>(ranks.values());
      Collections.sort(list, (o1, o2) -> Long.compare(o2.getTotalMeso(), o1.getTotalMeso()));
      int count = 0;

      for (PraiseDonationMesoRankEntry e : list) {
         if (e.getTotalMeso() > 0L) {
            ret.add(e);
         }

         if (++count >= 50) {
            break;
         }
      }

      return ret;
   }

   public static void recalculateRanks() {
      PreparedStatement ps = null;
      ResultSet rs = null;

      try (Connection con = DBConnection.getConnection()) {
         int rank = 1;
         ps = con.prepareStatement("DELETE FROM `questinfo_account` WHERE `quest` = ?");
         ps.setInt(1, 1234599);
         ps.executeUpdate();
         ps.close();

         for (GameServer gs : GameServer.getAllInstances()) {
            for (MapleCharacter player : gs.getPlayerStorage().getAllCharacters()) {
               if (player != null) {
                  player.updateOneInfo(1234599, "praise_reward", "");
                  player.updateOneInfo(1234599, "praise_reward_get", "");
               }
            }
         }

         List<PraiseDonationMesoRankEntry> rankList = new ArrayList<>(ranks.values());
         Collections.sort(rankList, (o1, o2) -> Long.compare(o2.getTotalMeso(), o1.getTotalMeso()));
         List<Integer> list = new ArrayList<>();

         for (PraiseDonationMesoRankEntry entry : rankList) {
            if (entry.getTotalMeso() >= 3000000000L) {
               list.add(entry.getAccountID());
            }

            int toAccountID = entry.getAccountID();
            MapleCharacter playerx = null;

            for (GameServer cs : GameServer.getAllInstances()) {
               for (MapleCharacter chr : cs.getPlayerStorage().getAllCharacters()) {
                  if (chr.getAccountID() == toAccountID) {
                     playerx = chr;
                     break;
                  }
               }
            }

            if (playerx != null) {
               playerx.updateOneInfo(1234599, "praise_reward", String.valueOf(rank));
               playerx.updateOneInfo(1234599, "praise_reward_get", "");
               playerx.dropMessage(5, "[์•๋ฆผ] ์นญ์ฐฌ ํฌ์ธํธ ๋ญํน " + rank + "เธเธฃเธธเธ“เธฒเธฃเธฑเธเธฃเธฒเธเธงเธฑเธฅเธ”เนเธฒเธเธเธ");
            } else {
               PreparedStatement ps3 = con.prepareStatement("INSERT INTO `questinfo_account` (`account_id`, `quest`, `customData`, `date`) VALUES (?, ?, ?, ?)");
               ps3.setInt(1, toAccountID);
               ps3.setInt(2, 1234599);
               ps3.setString(3, "praise_reward=" + rank);
               SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
               String time = sdf.format(Calendar.getInstance().getTime());
               ps3.setString(4, time);
               ps3.executeUpdate();
               ps3.close();
            }

            System.out.println(entry.getPlayerName() + " ์บ๋ฆญํฐ๊ฐ€ ์นญ์ฐฌ ํฌ์ธํธ ๋ญํน " + rank + "์๋ก ๋ณด์์ด ์ •์ฐ๋์—์ต๋๋ค.");
            LoggingManager.putLog(
               new CustomLog(
                  entry.getPlayerName(),
                  playerx != null ? playerx.getClient().getAccountName() : "",
                  playerx != null ? playerx.getId() : 0,
                  toAccountID,
                  1,
                  new StringBuilder("์นญ์ฐฌ ํฌ์ธํธ ๋ญํน " + rank + "์ ๋ณด์ ๋€์์ (name : " + entry.getPlayerName() + ", accountID : " + toAccountID + ")")
               )
            );
            if (rank++ >= 10) {
               break;
            }
         }

         Collections.shuffle(list);
         long meso = Math.min(150000000000L, (long)(PraiseDonationMeso.getTotalMeso() * 0.3));
         int count = 0;

         for (int id : list) {
            MapleCharacter playerx = null;

            for (GameServer cs : GameServer.getAllInstances()) {
               for (MapleCharacter chrx : cs.getPlayerStorage().getAllCharacters()) {
                  if (chrx.getAccountID() == id) {
                     playerx = chrx;
                     break;
                  }
               }
            }

            if (playerx != null) {
               playerx.updateOneInfo(1234599, "praise_reward2", String.valueOf(meso));
               playerx.updateOneInfo(1234599, "praise_reward2_get", "");
               playerx.dropMessage(5, "[Notice] เธเธเธฐเธเธดเธเธเธฃเธฃเธก Praise Point Settlement เธฃเธฑเธ Meso เนเธ”เนเธ—เธตเน NPC Donation Box");
               LoggingManager.putLog(
                  new CustomLog(
                     playerx.getName(),
                     playerx.getClient().getAccountName(),
                     playerx.getId(),
                     playerx.getAccountID(),
                     2,
                     new StringBuilder(playerx.getName() + ", ์ํผ๋ณผ ๋น์ฒจ์")
                  )
               );
            } else {
               PreparedStatement ps3 = con.prepareStatement("INSERT INTO `questinfo_account` (`account_id`, `quest`, `customData`, `date`) VALUES (?, ?, ?, ?)");
               ps3.setInt(1, id);
               ps3.setInt(2, 1234599);
               ps3.setString(3, "praise_reward2=" + meso);
               SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
               String time = sdf.format(Calendar.getInstance().getTime());
               ps3.setString(4, time);
               ps3.executeUpdate();
               ps3.close();
               LoggingManager.putLog(new CustomLog("", "", 0, id, 2, new StringBuilder("์ํผ๋ณผ ๋น์ฒจ์ (accountID : " + id + ")")));
            }

            if (++count >= 3) {
               break;
            }
         }

         ps = con.prepareStatement("DELETE FROM `praise_donation_meso_rank`");
         ps.executeUpdate();
      } catch (SQLException var28) {
         var28.printStackTrace();
      } finally {
         try {
            if (ps != null) {
               ps.close();
               PreparedStatement var30 = null;
            }

            if (rs != null) {
               rs.close();
               ResultSet var31 = null;
            }
         } catch (SQLException var25) {
            var25.printStackTrace();
         }
      }
   }

   public static PraiseDonationMesoRankEntry getRank(int accountID) {
      return !ranks.containsKey(accountID) ? new PraiseDonationMesoRankEntry("", accountID, 0L) : ranks.get(accountID);
   }

   public static void putRank(String name, int accountID, long meso) {
      ranks.put(accountID, new PraiseDonationMesoRankEntry(name, accountID, meso));
   }
}
