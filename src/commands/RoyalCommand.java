package commands;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import objects.users.MapleCharacter;
import objects.users.MapleClient;
import scripting.NPCScriptManager;

public class RoyalCommand implements Command {
   @Override
   public void execute(MapleClient c, String[] splitted) throws Exception, IllegalCommandSyntaxException {
      String split = splitted[0];
      if (split.equals("!setquestkv")) {
         if (splitted.length < 5) {
            c.getPlayer().dropMessage(6, "Syntax: !setquestkv <player> <quest id> <key> <value>");
            return;
         }

         MapleCharacter victim = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
         if (victim != null) {
            victim.updateOneInfo(Integer.parseInt(splitted[2]), splitted[3], splitted[4]);
            c.getPlayer().dropMessage(6, "Quest KeyValue Set Complete.");
         } else {
            c.getPlayer().dropMessage(6, "The victim does not exist.");
         }
      } else if (split.equals("!removequestkv")) {
         if (splitted.length < 4) {
            c.getPlayer().dropMessage(6, "Syntax: !removequestkv <player> <quest id> <key>");
            return;
         }

         MapleCharacter chrx = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
         if (chrx == null) {
            c.getPlayer().dropMessage(6, "Player not found.");
         } else {
            int t = Integer.parseInt(splitted[2]);
            String key = splitted[3];
            chrx.removeOneInfo(t, key);
            c.getPlayer().dropMessage(6, "Quest KeyValue Delete Complete");
         }
      } else if (split.equals("!setcharkv")) {
         MapleCharacter chr = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
         if (chr == null) {
            c.getPlayer().dropMessage(6, "Player not found.");
         } else {
            String key = splitted[2];
            String value = splitted[3];
            chr.setKeyValue(key, value);
            c.getPlayer().dropMessage(6, "Character KeyValue Set Complete");
         }
      } else if (split.equals("!removecharkv")) {
         if (splitted.length < 3) {
            c.getPlayer().dropMessage(6, "!removecharkv <player> <key>");
            return;
         }

         MapleCharacter chr = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
         if (chr == null) {
            c.getPlayer().dropMessage(6, "Player not found.");
         } else {
            String key = splitted[2];
            chr.removeKeyValue(key);
            c.getPlayer().dropMessage(6, "Character KeyValue Delete Complete");
         }
      } else if (split.equals("!setaccountkv")) {
         if (splitted.length < 4) {
            c.getPlayer().dropMessage(6, "!setaccountkv <player> <key> <value>");
            return;
         }

         MapleCharacter chr = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
         if (chr == null) {
            c.getPlayer().dropMessage(6, "Player not found.");
         } else {
            String key = splitted[2];
            String value = splitted[3];
            chr.getClient().setKeyValue(key, value);
            c.getPlayer().dropMessage(6, "Account KeyValue Set Complete");
         }
      } else if (split.equals("!removeaccountkv")) {
         if (splitted.length < 3) {
            c.getPlayer().dropMessage(6, "!removeaccountkv <player> <key>");
            return;
         }

         MapleCharacter chr = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
         if (chr == null) {
            c.getPlayer().dropMessage(6, "Player not found.");
         } else {
            String key = splitted[2];
            chr.getClient().removeKeyValue(key);
            c.getPlayer().dropMessage(6, "Account KeyValue Delete Complete");
         }
      } else if (split.equals("!giveoffline")) {
         if (splitted.length < 2) {
            c.getPlayer().dropMessage(5, "!giveoffline <player> <itemid> <quantity>");
            return;
         }

         try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT `id` FROM characters WHERE `name` = ?");
            ps.setString(1, splitted[1]);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               PreparedStatement pse = con
                     .prepareStatement("INSERT INTO `offline` (chrid, item, qua) VALUES (?, ?, ?)");
               pse.setInt(1, rs.getInt("id"));
               pse.setInt(2, Integer.parseInt(splitted[2]));
               pse.setInt(3, Integer.parseInt(splitted[3]));
               pse.executeUpdate();
               pse.close();
               c.getPlayer().dropMessage(5,
                     "[Offline Gift] Given " + splitted[3] + " of item " + splitted[2] + " to " + splitted[1] + ".");
            } else {
               c.getPlayer().dropMessage(5, "Character not found.");
            }

            rs.close();
            ps.close();
         } catch (SQLException var15) {
            var15.printStackTrace();
         }
      } else if (split.equals("!clearoffline")) {
         if (splitted.length < 2) {
            c.getPlayer().dropMessage(5, "!clearoffline <player>");
            return;
         }

         try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT `id` FROM characters WHERE `name` = ?");
            ps.setString(1, splitted[1]);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               PreparedStatement pse = con.prepareStatement("DELETE FROM `offline` WHERE chrid = ?");
               pse.setInt(1, rs.getInt("id"));
               pse.execute();
               pse.close();
               c.getPlayer().dropMessage(5, "[Offline Gift] Cleared offline gifts for " + splitted[1] + ".");
            } else {
               c.getPlayer().dropMessage(5, "Character not found.");
            }

            rs.close();
            ps.close();
         } catch (SQLException var13) {
            var13.printStackTrace();
         }
      } else if (split.equals("!royal")) {
         c.removeClickedNPC();
         NPCScriptManager.getInstance().dispose(c);
         NPCScriptManager.getInstance().start(c, 9900000);
      }
   }

   @Override
   public CommandDefinition[] getDefinition() {
      return new CommandDefinition[] {
            new CommandDefinition("!setquestkv", "<player> <questid> <key> <value>", "Sets a quest keyvalue.", 6),
            new CommandDefinition("!removequestkv", "<player> <questid> <key>", "Removes a quest keyvalue.", 6),
            new CommandDefinition("!setcharkv", "<player> <key> <value>", "Sets a character keyvalue.", 6),
            new CommandDefinition("!removecharkv", "<player> <key>", "Removes a character keyvalue.", 6),
            new CommandDefinition("!setaccountkv", "<player> <key> <value>", "Sets an account keyvalue.", 6),
            new CommandDefinition("!removeaccountkv", "<player> <key>", "Removes an account keyvalue.", 6),
            new CommandDefinition("!giveoffline", "<player> <item> <quantity>", "Gives an item to an offline player.",
                  6),
            new CommandDefinition("!clearoffline", "<player>", "Clears offline items for a player.", 6),
            new CommandDefinition("!royal", "", "Opens the Royal NPC.", 6)
      };
   }
}
