package objects.utils;

import database.DBConnection;
import database.DBEventManager;
import database.DBProcessor;
import database.DBSelectionKey;
import database.callback.DBCallback;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemEquipmentTool {
   public static void main(String[] args) {
      DBConnection.init();
      DBEventManager.init(6);
      PreparedStatement ps = null;
      ResultSet rs = null;

      try (Connection con = DBConnection.getConnection()) {
         System.out.println("[Notice] inventoryequipment task started.");
         ps = con.prepareStatement("SELECT * FROM inventoryequipment");
         System.out.println("[Notice] Starting inventoryequipment data collection. This may take some time.");
         rs = ps.executeQuery();
         System.out.println("[Notice] inventoryequipment data collection completed. Task started.");
         int count = 0;

         while (rs.next()) {
            PreparedStatement ps2 = con.prepareStatement("SELECT * FROM inventoryitems WHERE `inventoryitemid` = ?");
            ps2.setInt(1, rs.getInt("inventoryitemid"));
            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()) {
               final int characterID = rs2.getInt("characterid");
               final int accountID = rs2.getInt("accountid");
               final int itemID = rs2.getInt("itemid");
               final int inventoryID = rs.getInt("inventoryitemid");
               DBProcessor db = DBEventManager.getNextProcessor();
               db.addQuery(
                     DBSelectionKey.INSERT_OR_UPDATE,
                     "UPDATE inventoryequipment SET `player_id` = ?, `account_id` = ?, `item_id` = ? WHERE `inventoryitemid` = ?",
                     new DBCallback() {
                        @Override
                        public void execute(PreparedStatement ps) throws SQLException {
                           ps.setInt(1, characterID);
                           ps.setInt(2, accountID);
                           ps.setInt(3, itemID);
                           ps.setInt(4, inventoryID);
                           ps.addBatch();
                        }
                     });
            }

            if (++count % 10000 == 0) {
               System.out.println("[Notice] inventoryequipment " + count + " rows completed.");
            }

            rs2.close();
            ps2.close();
         }

         System.out.println("[Notice] auctionequipment task started.");
         ps = con.prepareStatement("SELECT * FROM auctionequipment");
         System.out.println("[Notice] Starting auctionequipment data collection. This may take some time.");
         rs = ps.executeQuery();
         System.out.println("[Notice] auctionequipment data collection completed. Task started.");
         count = 0;

         while (rs.next()) {
            PreparedStatement ps2 = con.prepareStatement("SELECT * FROM auctionitems WHERE `inventoryitemid` = ?");
            ps2.setInt(1, rs.getInt("inventoryitemid"));
            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()) {
               final int characterID = rs2.getInt("characterid");
               final int accountID = rs2.getInt("accountid");
               final int itemID = rs2.getInt("itemid");
               final int inventoryID = rs.getInt("inventoryitemid");
               DBProcessor db = DBEventManager.getNextProcessor();
               db.addQuery(
                     DBSelectionKey.INSERT_OR_UPDATE,
                     "UPDATE auctionequipment SET `player_id` = ?, `account_id` = ?, `item_id` = ? WHERE `inventoryitemid` = ?",
                     new DBCallback() {
                        @Override
                        public void execute(PreparedStatement ps) throws SQLException {
                           ps.setInt(1, characterID);
                           ps.setInt(2, accountID);
                           ps.setInt(3, itemID);
                           ps.setInt(4, inventoryID);
                           ps.addBatch();
                        }
                     });
            }

            if (++count % 10000 == 0) {
               System.out.println("[Notice] auctionequipment " + count + " rows completed.");
            }

            rs2.close();
            ps2.close();
         }

         System.out.println("[Notice] cabinet_equipment task started.");
         ps = con.prepareStatement("SELECT * FROM cabinet_equipment");
         System.out.println("[Notice] Starting cabinet_equipment data collection. This may take some time.");
         rs = ps.executeQuery();
         System.out.println("[Notice] cabinet_equipment data collection completed. Task started.");
         count = 0;

         while (rs.next()) {
            PreparedStatement ps2 = con.prepareStatement("SELECT * FROM cabinet_items WHERE `inventoryitemid` = ?");
            ps2.setInt(1, rs.getInt("inventoryitemid"));
            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()) {
               final int characterID = rs2.getInt("characterid");
               final int accountID = rs2.getInt("accountid");
               final int itemID = rs2.getInt("itemid");
               final int inventoryID = rs.getInt("inventoryitemid");
               DBProcessor db = DBEventManager.getNextProcessor();
               db.addQuery(
                     DBSelectionKey.INSERT_OR_UPDATE,
                     "UPDATE cabinet_equipment SET `player_id` = ?, `account_id` = ?, `item_id` = ? WHERE `inventoryitemid` = ?",
                     new DBCallback() {
                        @Override
                        public void execute(PreparedStatement ps) throws SQLException {
                           ps.setInt(1, characterID);
                           ps.setInt(2, accountID);
                           ps.setInt(3, itemID);
                           ps.setInt(4, inventoryID);
                           ps.addBatch();
                        }
                     });
            }

            if (++count % 10000 == 0) {
               System.out.println("[Notice] cabinet_equipment " + count + " rows completed.");
            }

            rs2.close();
            ps2.close();
         }

         System.out.println("[Notice] csequipment task started.");
         ps = con.prepareStatement("SELECT * FROM csequipment");
         System.out.println("[Notice] Starting csequipment data collection. This may take some time.");
         rs = ps.executeQuery();
         System.out.println("[Notice] csequipment data collection completed. Task started.");
         count = 0;

         while (rs.next()) {
            PreparedStatement ps2 = con.prepareStatement("SELECT * FROM csitems WHERE `inventoryitemid` = ?");
            ps2.setInt(1, rs.getInt("inventoryitemid"));
            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()) {
               final int characterID = rs2.getInt("characterid");
               final int accountID = rs2.getInt("accountid");
               final int itemID = rs2.getInt("itemid");
               final int inventoryID = rs.getInt("inventoryitemid");
               DBProcessor db = DBEventManager.getNextProcessor();
               db.addQuery(
                     DBSelectionKey.INSERT_OR_UPDATE,
                     "UPDATE csequipment SET `player_id` = ?, `account_id` = ?, `item_id` = ? WHERE `inventoryitemid` = ?",
                     new DBCallback() {
                        @Override
                        public void execute(PreparedStatement ps) throws SQLException {
                           ps.setInt(1, characterID);
                           ps.setInt(2, accountID);
                           ps.setInt(3, itemID);
                           ps.setInt(4, inventoryID);
                           ps.addBatch();
                        }
                     });
            }

            if (++count % 10000 == 0) {
               System.out.println("[Notice] csequipment " + count + " rows completed.");
            }

            rs2.close();
            ps2.close();
         }

         System.out.println("[Notice] Task completed.");
      } catch (SQLException var15) {
         try {
            if (ps != null) {
               ps.close();
               PreparedStatement var16 = null;
            }

            if (rs != null) {
               rs.close();
               ResultSet var17 = null;
            }
         } catch (SQLException var12) {
         }
      }
   }
}
