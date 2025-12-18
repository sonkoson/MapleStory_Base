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
            c.getPlayer().dropMessage(6, "รูปแบบคำสั่ง: !setquestkv <ชื่อผู้เล่น> <รหัสเควส> <คีย์> <ค่า>");
            return;
         }

         MapleCharacter victim = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
         if (victim != null) {
            victim.updateOneInfo(Integer.parseInt(splitted[2]), splitted[3], splitted[4]);
            c.getPlayer().dropMessage(6, "ตั้งค่า KeyValue ของเควสเรียบร้อยแล้ว");
         } else {
            c.getPlayer().dropMessage(6, "ไม่พบผู้เล่นดังกล่าว");
         }
      } else if (split.equals("!removequestkv")) {
         if (splitted.length < 4) {
            c.getPlayer().dropMessage(6, "รูปแบบคำสั่ง: !removequestkv <ชื่อผู้เล่น> <รหัสเควส> <คีย์>");
            return;
         }

         MapleCharacter chrx = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
         if (chrx == null) {
            c.getPlayer().dropMessage(6, "ไม่พบผู้เล่น");
         } else {
            int t = Integer.parseInt(splitted[2]);
            String key = splitted[3];
            chrx.removeOneInfo(t, key);
            c.getPlayer().dropMessage(6, "ลบ KeyValue ของเควสเรียบร้อยแล้ว");
         }
      } else if (split.equals("!setcharkv")) {
         MapleCharacter chr = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
         if (chr == null) {
            c.getPlayer().dropMessage(6, "ไม่พบผู้เล่น");
         } else {
            String key = splitted[2];
            String value = splitted[3];
            chr.setKeyValue(key, value);
            c.getPlayer().dropMessage(6, "ตั้งค่า KeyValue ของตัวละครเรียบร้อยแล้ว");
         }
      } else if (split.equals("!removecharkv")) {
         if (splitted.length < 3) {
            c.getPlayer().dropMessage(6, "!removecharkv <ชื่อผู้เล่น> <คีย์>");
            return;
         }

         MapleCharacter chr = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
         if (chr == null) {
            c.getPlayer().dropMessage(6, "ไม่พบผู้เล่น");
         } else {
            String key = splitted[2];
            chr.removeKeyValue(key);
            c.getPlayer().dropMessage(6, "ลบ KeyValue ของตัวละครเรียบร้อยแล้ว");
         }
      } else if (split.equals("!setaccountkv")) {
         if (splitted.length < 4) {
            c.getPlayer().dropMessage(6, "!setaccountkv <ชื่อผู้เล่น> <คีย์> <ค่า>");
            return;
         }

         MapleCharacter chr = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
         if (chr == null) {
            c.getPlayer().dropMessage(6, "ไม่พบผู้เล่น");
         } else {
            String key = splitted[2];
            String value = splitted[3];
            chr.getClient().setKeyValue(key, value);
            c.getPlayer().dropMessage(6, "ตั้งค่า KeyValue ของบัญชีเรียบร้อยแล้ว");
         }
      } else if (split.equals("!removeaccountkv")) {
         if (splitted.length < 3) {
            c.getPlayer().dropMessage(6, "!removeaccountkv <ชื่อผู้เล่น> <คีย์>");
            return;
         }

         MapleCharacter chr = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
         if (chr == null) {
            c.getPlayer().dropMessage(6, "ไม่พบผู้เล่น");
         } else {
            String key = splitted[2];
            chr.getClient().removeKeyValue(key);
            c.getPlayer().dropMessage(6, "ลบ KeyValue ของบัญชีเรียบร้อยแล้ว");
         }
      } else if (split.equals("!giveoffline")) {
         if (splitted.length < 2) {
            c.getPlayer().dropMessage(5, "!giveoffline <ชื่อผู้เล่น> <รหัสไอเทม> <จำนวน>");
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
                     "[ของขวัญออฟไลน์] มอบไอเทม " + splitted[2] + " จำนวน " + splitted[3] + " ชิ้น ให้กับ "
                           + splitted[1] + " เรียบร้อยแล้ว");
            } else {
               c.getPlayer().dropMessage(5, "ไม่พบตัวละคร");
            }

            rs.close();
            ps.close();
         } catch (SQLException var15) {
            var15.printStackTrace();
         }
      } else if (split.equals("!clearoffline")) {
         if (splitted.length < 2) {
            c.getPlayer().dropMessage(5, "!clearoffline <ชื่อผู้เล่น>");
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
               c.getPlayer().dropMessage(5, "[ของขวัญออฟไลน์] ลบของขวัญออฟไลน์ของ " + splitted[1] + " เรียบร้อยแล้ว");
            } else {
               c.getPlayer().dropMessage(5, "ไม่พบตัวละคร");
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
            new CommandDefinition("!setquestkv", "<player> <questid> <key> <value>", "ตั้งค่า KeyValue ของเควส", 6),
            new CommandDefinition("!removequestkv", "<player> <questid> <key>", "ลบ KeyValue ของเควส", 6),
            new CommandDefinition("!setcharkv", "<player> <key> <value>", "ตั้งค่า KeyValue ของตัวละคร", 6),
            new CommandDefinition("!removecharkv", "<player> <key>", "ลบ KeyValue ของตัวละคร", 6),
            new CommandDefinition("!setaccountkv", "<player> <key> <value>", "ตั้งค่า KeyValue ของบัญชี", 6),
            new CommandDefinition("!removeaccountkv", "<player> <key>", "ลบ KeyValue ของบัญชี", 6),
            new CommandDefinition("!giveoffline", "<player> <item> <quantity>", "มอบไอเทมให้ผู้เล่นที่ออฟไลน์",
                  6),
            new CommandDefinition("!clearoffline", "<player>", "ลบไอเทมออฟไลน์ของผู้เล่น", 6),
            new CommandDefinition("!royal", "", "เปิด NPC Royal", 6)
      };
   }
}
