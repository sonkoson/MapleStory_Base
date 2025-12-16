package script.Util;

import database.DBConnection;
import network.center.Center;
import network.game.GameServer;
import objects.quest.QuestEx;
import objects.users.MapleCharacter;
import scripting.newscripting.ScriptEngineNPC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class GMUtil extends ScriptEngineNPC {

    public void test1() {
        if (!getPlayer().isGM()) {
            return;
        }
        var type = self.askMenu(
                "สวัสดี #h 0# นี่คือ NPC สำหรับแก้ไข QuestInfo ผู้เล่น\r\nกรุณาเลือกประเภทที่ต้องการแก้ไข\r\n#L0#แก้ไข QuestInfo ตัวละคร\r\n#L1#แก้ไข QuestInfo บัญชี");
        var name = self.askText(
                "กรุณากรอกชื่อผู้เล่นที่ต้องการแก้ไข\r\nกรณีบัญชี สามารถกรอก Account ID ได้\r\n(ไม่จำเป็นต้องอออนไลน์)");
        int userID = findUserID(type, name);
        MapleCharacter user = null;
        if (userID >= 0) {
            for (GameServer srv : GameServer.getAllInstances()) {
                if ((user = srv.getPlayerStorage().getCharacterById(userID)) != null) {
                    break;
                }
            }
        } else {
            self.sayOk("ไม่พบตัวละครหรือบัญชีดังกล่าว กรุณาลองใหม่อีกครั้ง");
            return;
        }

        int questID = self.askNumber("กรุณากรอก QuestEx ID ที่ต้องการเปลี่ยน", 0, 0, Integer.MAX_VALUE);
        String questKey = self.askText("กรุณากรอก QuestEx Key ที่ต้องการเปลี่ยน");

        if (user != null) {
            // In-game character exists
            String questValue = user.getOneInfo(questID, questKey);
            String newValue = self
                    .askText("ค่าปัจจุบันของ QuestEx คือ " + questValue + "\r\nกรุณากรอกค่าใหม่ที่ต้องการ");
            if (newValue != null) {
                user.updateOneInfo(questID, questKey, newValue);
                self.sayOk("เปลี่ยนแปลงเรียบร้อยแล้ว");
            } else {
                self.sayOk("เกิดปัญหาในการเปลี่ยนแปลง กรุณาลองใหม่อีกครั้ง");
            }
        } else {
            String newValue = self.askText("กรุณากรอกค่า QuestEx Value ที่ต้องการ");
            if (newValue != null) {
                if (updateUserQuestInfo(userID, type, questID, questKey, newValue)) {
                    self.sayOk("เปลี่ยนแปลงเรียบร้อยแล้ว");
                } else {
                    self.sayOk("เกิดปัญหา ไม่สามารถอัปเดตฐานข้อมูลได้");
                }
            } else {
                self.sayOk("เกิดปัญหาในการเปลี่ยนแปลง กรุณาลองใหม่อีกครั้ง");
            }
        }
    }

    public boolean updateUserQuestInfo(int userID, int type, int questID, String key, String value) {
        Map<Integer, QuestEx> questInfo = new HashMap<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String dbName = type == 1 ? "questinfo_account" : "questinfo";
        String idType = type == 1 ? "account_id" : "characterid";
        try (Connection con = DBConnection.getConnection()) {
            ps = con.prepareStatement(
                    "SELECT `quest`, `customData`, `date` FROM " + dbName + " WHERE " + idType + " = ?");
            ps.setInt(1, userID);
            rs = ps.executeQuery();

            while (rs.next()) {
                questInfo.put(rs.getInt("quest"),
                        new QuestEx(rs.getInt("quest"), rs.getString("customData"), rs.getString("date")));
            }

            if (key == null) {
                return false;
            }

            QuestEx ex = questInfo.get(questID);
            if (ex == null) {
                ps = con.prepareStatement("INSERT INTO " + dbName + " VALUES (DEFAULT, ?, ?, ?, ?)");
                ps.setInt(1, userID);
                ps.setInt(2, questID);
                ps.setString(3, value);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String time = sdf.format(Calendar.getInstance().getTime());
                ps.setString(4, time);
                ps.executeUpdate();
                ps.close();

                return true;
            }

            final String[] split = ex.getData().split(";");
            String oldData = null;
            for (String x : split) {
                final String[] split2 = x.split("="); // should be only 2
                if (split2.length == 2 && split2[0].equals(key)) {
                    oldData = split2[1];
                    break;
                }
            }

            rs.close();
            ps.close();

            if (oldData != null) {
                String newData = ex.getData().replaceAll(oldData, value);
                ex.setData(newData);

                ps = con.prepareStatement(
                        "UPDATE " + dbName + " SET customData = ? WHERE " + idType + " = ? AND quest = ?");
                ps.setString(1, newData);
                ps.setInt(2, userID);
                ps.setInt(3, questID);
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    public int findUserID(int type, String name) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = DBConnection.getConnection()) {
            ps = con.prepareStatement("SELECT * FROM characters WHERE `name` = ?");
            ps.setString(1, name);

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }

            ps.close();
            rs.close();

            if (type == 1) {
                ps = con.prepareStatement("SELECT * FROM accounts WHERE `name` = ?");
                ps.setString(1, name);

                rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return -1;
    }
}
