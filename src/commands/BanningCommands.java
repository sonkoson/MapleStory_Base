package commands;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import network.auction.AuctionServer;
import network.game.GameServer;
import network.shop.CashShopServer;
import objects.users.MapleCharacter;
import objects.users.MapleClient;
import objects.utils.StringUtil;

public class BanningCommands implements Command {
   @Override
   public void execute(MapleClient c, String[] splitted) throws Exception {
      GameServer cserv = c.getChannelServer();
      if (!splitted[0].equals("!ban") && !splitted[0].equals("!hellban")) {
         if (splitted[0].equals("!tempban")) {
            String targetName = splitted[1];
            String reason = splitted[2];
            int numDay = Integer.parseInt(splitted[3]);
            MapleCharacter target = null;
            int accountID = -1;
            String accountName = "";
            String banLogs = "";

            try (Connection con = DBConnection.getConnection()) {
               PreparedStatement ps = con.prepareStatement("SELECT `accountid` FROM characters WHERE `name` = ?");
               ps.setString(1, targetName);
               ResultSet rs = ps.executeQuery();

               while (rs.next()) {
                  accountID = rs.getInt("accountid");
               }

               rs.close();
               ps.close();
               ps = con.prepareStatement("SELECT `name`, `banreason` FROM accounts WHERE `id` = ?");
               ps.setInt(1, accountID);
               rs = ps.executeQuery();

               while (rs.next()) {
                  banLogs = rs.getString("banreason");
                  accountName = rs.getString("name");
               }

               rs.close();
               ps.close();
            } catch (SQLException var27) {
            }

            if (banLogs != null && !banLogs.isEmpty()) {
               banLogs = banLogs + "," + reason + "(TempBan)";
            } else {
               banLogs = reason + "(TempBan)";
            }

            for (MapleCharacter p : new ArrayList<>(AuctionServer.getPlayerStorage().getAllCharacters())) {
               if (p != null && p.getName().equals(targetName)) {
                  target = p;
                  break;
               }
            }

            for (MapleCharacter px : new ArrayList<>(CashShopServer.getPlayerStorage().getAllCharacters())) {
               if (px != null && px.getName().equals(targetName)) {
                  target = px;
                  break;
               }
            }

            for (GameServer cs : GameServer.getAllInstances()) {
               for (MapleCharacter chr : cs.getPlayerStorage().getAllCharacters()) {
                  if (chr != null && chr.getName().equals(targetName)) {
                     target = chr;
                     break;
                  }
               }
            }

            if (target == null) {
               try (Connection con = DBConnection.getConnection()) {
                  for (MapleCharacter pxx : new ArrayList<>(AuctionServer.getPlayerStorage().getAllCharacters())) {
                     if (pxx != null && pxx.getAccountID() == accountID) {
                        target = pxx;
                        break;
                     }
                  }

                  for (MapleCharacter pxxx : new ArrayList<>(CashShopServer.getPlayerStorage().getAllCharacters())) {
                     if (pxxx != null && pxxx.getAccountID() == accountID) {
                        target = pxxx;
                        break;
                     }
                  }

                  for (GameServer cs : GameServer.getAllInstances()) {
                     for (MapleCharacter chrx : cs.getPlayerStorage().getAllCharacters()) {
                        if (chrx != null && chrx.getAccountID() == accountID) {
                           target = chrx;
                           break;
                        }
                     }
                  }

                  if (target != null) {
                     Calendar cal = Calendar.getInstance();
                     cal.add(5, numDay);
                     DateFormat df = DateFormat.getInstance();
                     target.tempban(banLogs, cal, false);
                     c.getPlayer().dropMessage(6,
                           splitted[1] + " has been banned until " + df.format(cal.getTime()) + ".");
                  } else {
                     Calendar cal = Calendar.getInstance();
                     cal.add(5, numDay);
                     DateFormat df = DateFormat.getInstance();
                     PreparedStatement ps = con
                           .prepareStatement("UPDATE accounts SET tempban = ?, banreason = ? WHERE id = ?");
                     Timestamp TS = new Timestamp(cal.getTimeInMillis());
                     ps.setTimestamp(1, TS);
                     ps.setString(2, banLogs);
                     ps.setInt(3, accountID);
                     ps.execute();
                     ps.close();
                     c.getPlayer().dropMessage(6,
                           splitted[1] + " (Offline) has been banned until " + df.format(cal.getTime()) + ".");
                  }
               } catch (SQLException var25) {
               }
            } else {
               Calendar cal = Calendar.getInstance();
               cal.add(5, numDay);
               DateFormat df = DateFormat.getInstance();
               target.tempban(banLogs, cal, false);
               c.getPlayer().dropMessage(6, splitted[1] + " has been banned until " + df.format(cal.getTime()) + ".");
            }
         } else if (splitted[0].equals("!unban")) {
            if (splitted.length < 2) {
               c.getPlayer().dropMessage(6, "!unban <character name>");
            } else {
               byte result = MapleClient.unban(splitted[1]);
               if (result == -1) {
                  c.getPlayer().dropMessage(6, splitted[1] + " not found.");
               } else if (result == -2) {
                  c.getPlayer().dropMessage(6, splitted[1] + " found, but error occurred during unban.");
               } else {
                  c.getPlayer().dropMessage(6, splitted[1] + " successfully unbanned.");
               }
            }
         } else if (splitted[0].equals("!dc")) {
            int level = 0;
            MapleCharacter victim;
            if (splitted[1].charAt(0) == '-') {
               level = StringUtil.countCharacters(splitted[1], 'f');
               victim = cserv.getPlayerStorage().getCharacterByName(splitted[2]);
            } else {
               victim = cserv.getPlayerStorage().getCharacterByName(splitted[1]);
            }

            try (Connection con = DBConnection.getConnection()) {
               PreparedStatement ps = con.prepareStatement("UPDATE accounts SET loggedin = 0 WHERE id = ?");
               ps.setInt(1, victim.getId());
               ps.executeUpdate();
               ps.close();
            } catch (Exception var23) {
               var23.printStackTrace();
            }

            if (level < 2) {
               victim.getClient().getSession().close();
               System.out.println("Forced disconnection initiated.");
               if (level >= 1) {
                  victim.getClient().disconnect(false);
               }
            } else {
               c.getPlayer().dropMessage(6, "Please use dc -f instead.");
            }
         }
      } else {
         if (splitted.length < 3) {
            return;
         }

         boolean byAdminClient = false;
         boolean hellban = splitted[0].equals("!hellban");
         String targetName = splitted[1];
         StringBuilder sb = new StringBuilder(c.getPlayer().getName());
         sb.append(" banned ").append(splitted[1]).append(": ").append(StringUtil.joinStringFrom(splitted, 2));
         int accountID = -1;
         String banLogs = "";

         try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT `accountid` FROM characters WHERE `name` = ?");
            ps.setString(1, targetName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
               accountID = rs.getInt("accountid");
            }

            rs.close();
            ps.close();
            ps = con.prepareStatement("SELECT `banreason` FROM accounts WHERE `id` = ?");
            ps.setInt(1, accountID);
            rs = ps.executeQuery();

            while (rs.next()) {
               banLogs = rs.getString("banreason");
            }

            rs.close();
            ps.close();
         } catch (SQLException var21) {
         }

         if (banLogs != null && !banLogs.isEmpty()) {
            banLogs = banLogs + "," + sb.toString();
         } else {
            banLogs = sb.toString();
         }

         MapleCharacter target = null;

         for (MapleCharacter pxxxx : new ArrayList<>(AuctionServer.getPlayerStorage().getAllCharacters())) {
            if (pxxxx != null && pxxxx.getName().equals(targetName)) {
               target = pxxxx;
               break;
            }
         }

         for (MapleCharacter pxxxxx : new ArrayList<>(CashShopServer.getPlayerStorage().getAllCharacters())) {
            if (pxxxxx != null && pxxxxx.getName().equals(targetName)) {
               target = pxxxxx;
               break;
            }
         }

         for (GameServer cs : GameServer.getAllInstances()) {
            for (MapleCharacter chrxx : cs.getPlayerStorage().getAllCharacters()) {
               if (chrxx != null && chrxx.getName().equals(targetName)) {
                  target = chrxx;
                  break;
               }
            }
         }

         if (target == null) {
            for (MapleCharacter pxxxxxx : new ArrayList<>(AuctionServer.getPlayerStorage().getAllCharacters())) {
               if (pxxxxxx != null && pxxxxxx.getAccountID() == accountID) {
                  target = pxxxxxx;
                  break;
               }
            }

            for (MapleCharacter pxxxxxxx : new ArrayList<>(CashShopServer.getPlayerStorage().getAllCharacters())) {
               if (pxxxxxxx != null && pxxxxxxx.getAccountID() == accountID) {
                  target = pxxxxxxx;
                  break;
               }
            }

            for (GameServer cs : GameServer.getAllInstances()) {
               for (MapleCharacter chrxxx : cs.getPlayerStorage().getAllCharacters()) {
                  if (chrxxx != null && chrxxx.getAccountID() == accountID) {
                     target = chrxxx;
                     break;
                  }
               }
            }

            if (target != null) {
               if (target.ban(banLogs, true, false, hellban)) {
                  c.getPlayer().dropMessage(6, targetName + " has been banned.");
               } else {
                  c.getPlayer().dropMessage(6, "Error occurred while banning.");
               }

               target.serialBan(byAdminClient);
            } else if (MapleCharacter.ban(targetName, banLogs, false,
                  c.getPlayer().isAdmin() ? 250 : c.getPlayer().getGMLevel(), false)) {
               c.getPlayer().dropMessage(6, targetName + " (Offline) has been banned.");
            } else {
               c.getPlayer().dropMessage(6, targetName + " failed to ban.");
            }
         } else {
            if (target.ban(banLogs, true, false, hellban)) {
               c.getPlayer().dropMessage(6, targetName + " has been banned.");
            } else {
               c.getPlayer().dropMessage(6, "Error while banning.");
            }

            target.serialBan(byAdminClient);
         }
      }
   }

   @Override
   public CommandDefinition[] getDefinition() {
      return new CommandDefinition[] {
            new CommandDefinition("!ban", "<character name> <reason>", "Permanently bans the IP, MAC, and Account.", 3),
            new CommandDefinition("!unban", "<character name>", "Unbans the IP, MAC, and Account.", 3),
            new CommandDefinition("!tempban", "<character name> <reason> <days>",
                  "Temporarily bans the account for the specified number of days.", 3),
            new CommandDefinition("!dc", "[-f] <character name>", "Disconnects the player. Use -f to force disconnect.",
                  3)
      };
   }
}
