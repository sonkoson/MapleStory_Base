package commands;

import database.DBConnection;
import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import network.models.CField;
import objects.fields.child.dreambreaker.DreamBreakerRank;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.fields.gameobject.lifes.MapleMonster;
import objects.fields.gameobject.lifes.MapleNPC;
import objects.fields.gameobject.lifes.PlayerNPC;
import objects.users.MapleCharacter;
import objects.users.MapleClient;
import scripting.NPCScriptManager;

public class NPCSpawningCommands implements Command {
   @Override
   public void execute(MapleClient c, String[] splitted) throws Exception, IllegalCommandSyntaxException {
      if (splitted[0].equals("!npc")) {
         int npcId = Integer.parseInt(splitted[1]);
         MapleNPC npc = MapleLifeFactory.getNPC(npcId);
         if (npc != null && !npc.getName().equals("MISSINGNO")) {
            npc.setPosition(c.getPlayer().getPosition());
            npc.setCy(c.getPlayer().getPosition().y);
            npc.setRx0(c.getPlayer().getPosition().x - 50);
            npc.setRx1(c.getPlayer().getPosition().x + 50);
            npc.setFh(c.getPlayer().getMap().getFootholds().findBelow(c.getPlayer().getPosition()).getId());
            npc.setCustom(true);
            c.getPlayer().getMap().addMapObject(npc);
            c.getPlayer().getMap().broadcastMessage(CField.NPCPacket.spawnNPC(npc, true));
         } else {
            c.getPlayer().dropMessage(6, "ไม่พบ NPC ที่ใช้ ID นี้");
         }
      } else if (splitted[0].equals("!shop")) {
         if (DBConfig.isGanglim) {
            c.removeClickedNPC();
            NPCScriptManager.getInstance().dispose(c);
            NPCScriptManager.getInstance().start(c, 9062462);
         }
      } else if (splitted[0].equals("!clearnpc")) {
         c.getPlayer().getMap().resetNPCs();
      } else if (splitted[0].equals("!pos")) {
         Point pos = c.getPlayer().getPosition();
         c.getPlayer()
               .dropMessage(
                     6,
                     "CY: "
                           + (pos.y + 2)
                           + " | RX0: "
                           + (pos.x - 50)
                           + " | RX1: "
                           + (pos.x + 50)
                           + " | FH: "
                           + c.getPlayer().getMap().getFootholds().findBelow(pos).getId());
      } else if (splitted[0].equals("!ranknpc")) {
         try {
            c.getPlayer().dropMessage(6, "กำลังรีโหลด NPC อันดับ Dream Breaker...");
            List<String> rankers = new ArrayList<>();

            for (int i = 1; i <= 5; i++) {
               String name = DreamBreakerRank.getRanker(i);
               if (name.isEmpty()) {
                  break;
               }

               rankers.add(name);
            }

            c.getPlayer().dropMessage(6, "เสร็จสิ้น!");
         } catch (Exception var18) {
            c.getPlayer().dropMessage(6, "เกิดข้อผิดพลาดในการรีโหลด NPC อันดับ Dream Breaker: " + var18.getMessage());
            var18.printStackTrace();
         }
      } else if (splitted[0].equals("!playernpc")) {
         try {
            c.getPlayer().dropMessage(6, "กำลังสร้าง NPC ผู้เล่น...");
            MapleCharacter chhr = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
            if (chhr == null) {
               c.getPlayer().dropMessage(6, splitted[1] + " ไม่ได้ออนไลน์หรือไม่มีอยู่จริง");
            } else {
               PlayerNPC npc = new PlayerNPC(chhr, Integer.parseInt(splitted[2]), c.getPlayer().getMap(),
                     c.getPlayer());
               npc.addToServer();
               c.getPlayer().dropMessage(6, "เสร็จสิ้น!");
            }
         } catch (Exception var12) {
            c.getPlayer().dropMessage(6, "เกิดข้อผิดพลาดในการสร้าง NPC ผู้เล่น: " + var12.getMessage());
            var12.printStackTrace();
         }
      } else if (splitted[0].equals("!pnpc")) {
         int npcId = Integer.parseInt(splitted[1]);
         MapleNPC npc = MapleLifeFactory.getNPC(npcId);
         if (npc == null || npc.getName().equals("MISSINGNO")) {
            c.getPlayer().dropMessage(6, "ไม่พบ NPC ในข้อมูล WZ");
            return;
         }

         npc.setPosition(c.getPlayer().getPosition());
         npc.setCy(c.getPlayer().getPosition().y);
         npc.setRx0(c.getPlayer().getPosition().x - 50);
         npc.setRx1(c.getPlayer().getPosition().x + 50);
         npc.setFh(c.getPlayer().getMap().getFootholds().findBelow(c.getPlayer().getPosition()).getId());
         c.getPlayer().getMap().addMapObject(npc);
         c.getPlayer().getMap().broadcastMessage(CField.NPCPacket.spawnNPC(npc, true));

         try (
               Connection con = DBConnection.getConnection();
               PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO `spawn`(`lifeid`, `rx0`, `rx1`, `cy`, `fh`, `type`, `dir`, `mapid`, `mobTime`) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,?)");) {
            ps.setInt(1, npcId);
            ps.setInt(2, c.getPlayer().getPosition().x);
            ps.setInt(3, c.getPlayer().getPosition().x + 100);
            ps.setInt(4, c.getPlayer().getPosition().y);
            ps.setInt(5, c.getPlayer().getMap().getFootholds().findBelow(c.getPlayer().getPosition()).getId());
            ps.setString(6, "n");
            ps.setInt(7, c.getPlayer().getFacingDirection() == 1 ? 0 : 1);
            ps.setInt(8, c.getPlayer().getMapId());
            ps.setInt(9, 0);
            ps.executeUpdate();
         } catch (Exception var17) {
            System.err.println("[Error] Failed to save NPC spawn to DB.");
            var17.printStackTrace();
         }
      } else if (splitted[0].equals("!pmob")) {
         int mobId = Integer.parseInt(splitted[1]);
         MapleMonster mob = MapleLifeFactory.getMonster(mobId);
         c.getPlayer().getMap().spawnMonsterOnGroundBelow(mob, c.getPlayer().getPosition());

         try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO `spawn`(`lifeid`, `rx0`, `rx1`, `cy`, `fh`, `type`, `dir`, `mapid`, `mobTime`) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, mobId);
            ps.setInt(2, c.getPlayer().getPosition().x - 50);
            ps.setInt(3, c.getPlayer().getPosition().x + 50);
            ps.setInt(4, c.getPlayer().getPosition().y);
            ps.setInt(5, c.getPlayer().getMap().getFootholds().findBelow(c.getPlayer().getPosition()).getId());
            ps.setString(6, "m");
            ps.setInt(7, c.getPlayer().getFacingDirection() == 1 ? 0 : 1);
            ps.setInt(8, c.getPlayer().getMapId());
            ps.setInt(9, 0);
            ps.executeUpdate();
            ps.close();
         } catch (Exception var14) {
            System.err.println("[Error] Failed to save Mob spawn to DB.");
            var14.printStackTrace();
         }
      }
   }

   @Override
   public CommandDefinition[] getDefinition() {
      return new CommandDefinition[] {
            new CommandDefinition("!shop", "", "เปิดร้านค้าสำหรับ GM", 5),
            new CommandDefinition("!npc", "<npc id>", "เรียก NPC ออกมาที่ตำแหน่งของคุณ", 5),
            new CommandDefinition("!clearnpc", "", "ลบ NPC ทั้งหมดออกจากแผนที่", 5),
            new CommandDefinition("!playernpc", "<player name> <script id>", "สร้าง NPC เลียนแบบผู้เล่น", 5),
            new CommandDefinition("!ranknpc", "", "รีโหลด NPC อันดับ Dream Breaker", 5),
            new CommandDefinition("!pnpc", "<npc id>", "เรียก NPC ถาวรและบันทึกลงฐานข้อมูล", 5),
            new CommandDefinition("!pmob", "<mob id>", "เรียกมอนสเตอร์ถาวรและบันทึกลงฐานข้อมูล", 5),
            new CommandDefinition("!pos", "", "แสดงข้อมูลพิกัดปัจจุบันของคุณ", 2)
      };
   }
}
