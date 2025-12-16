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
            c.getPlayer().dropMessage(6, "เนเธกเนเธเธ NPC เธ—เธตเนเนเธเน ID เธเธตเน");
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
            c.getPlayer().dropMessage(6, "เธเธณเธฅเธฑเธเธฃเธตเนเธซเธฅเธ” NPC เธญเธฑเธเธ”เธฑเธ Dream Breaker...");
            List<String> rankers = new ArrayList<>();

            for (int i = 1; i <= 5; i++) {
               String name = DreamBreakerRank.getRanker(i);
               if (name.isEmpty()) {
                  break;
               }

               rankers.add(name);
            }

            c.getPlayer().dropMessage(6, "เน€เธชเธฃเนเธเธชเธดเนเธ!");
         } catch (Exception var18) {
            c.getPlayer().dropMessage(6, "เน€เธเธดเธ”เธเนเธญเธเธดเธ”เธเธฅเธฒเธ”เนเธเธเธฒเธฃเธฃเธตเนเธซเธฅเธ” NPC เธญเธฑเธเธ”เธฑเธ Dream Breaker: " + var18.getMessage());
            var18.printStackTrace();
         }
      } else if (splitted[0].equals("!playernpc")) {
         try {
            c.getPlayer().dropMessage(6, "เธเธณเธฅเธฑเธเธชเธฃเนเธฒเธ NPC เธเธนเนเน€เธฅเนเธ...");
            MapleCharacter chhr = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
            if (chhr == null) {
               c.getPlayer().dropMessage(6, splitted[1] + " เนเธกเนเนเธ”เนเธญเธญเธเนเธฅเธเนเธซเธฃเธทเธญเนเธกเนเธกเธตเธญเธขเธนเนเธเธฃเธดเธ");
            } else {
               PlayerNPC npc = new PlayerNPC(chhr, Integer.parseInt(splitted[2]), c.getPlayer().getMap(),
                     c.getPlayer());
               npc.addToServer();
               c.getPlayer().dropMessage(6, "เน€เธชเธฃเนเธเธชเธดเนเธ!");
            }
         } catch (Exception var12) {
            c.getPlayer().dropMessage(6, "เน€เธเธดเธ”เธเนเธญเธเธดเธ”เธเธฅเธฒเธ”เนเธเธเธฒเธฃเธชเธฃเนเธฒเธ NPC เธเธนเนเน€เธฅเนเธ: " + var12.getMessage());
            var12.printStackTrace();
         }
      } else if (splitted[0].equals("!pnpc")) {
         int npcId = Integer.parseInt(splitted[1]);
         MapleNPC npc = MapleLifeFactory.getNPC(npcId);
         if (npc == null || npc.getName().equals("MISSINGNO")) {
            c.getPlayer().dropMessage(6, "เนเธกเนเธเธ NPC เนเธเธเนเธญเธกเธนเธฅ WZ");
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
            new CommandDefinition("!shop", "", "เน€เธเธดเธ”เธฃเนเธฒเธเธเนเธฒเธชเธณเธซเธฃเธฑเธ GM", 5),
            new CommandDefinition("!npc", "<npc id>", "เน€เธฃเธตเธขเธ NPC เธญเธญเธเธกเธฒเธ—เธตเนเธ•เธณเนเธซเธเนเธเธเธญเธเธเธธเธ“", 5),
            new CommandDefinition("!clearnpc", "", "เธฅเธ NPC เธ—เธฑเนเธเธซเธกเธ”เธญเธญเธเธเธฒเธเนเธเธเธ—เธตเน", 5),
            new CommandDefinition("!playernpc", "<player name> <script id>", "เธชเธฃเนเธฒเธ NPC เน€เธฅเธตเธขเธเนเธเธเธเธนเนเน€เธฅเนเธ", 5),
            new CommandDefinition("!ranknpc", "", "เธฃเธตเนเธซเธฅเธ” NPC เธญเธฑเธเธ”เธฑเธ Dream Breaker", 5),
            new CommandDefinition("!pnpc", "<npc id>", "เน€เธฃเธตเธขเธ NPC เธ–เธฒเธงเธฃเนเธฅเธฐเธเธฑเธเธ—เธถเธเธฅเธเธเธฒเธเธเนเธญเธกเธนเธฅ", 5),
            new CommandDefinition("!pmob", "<mob id>", "เน€เธฃเธตเธขเธเธกเธญเธเธชเน€เธ•เธญเธฃเนเธ–เธฒเธงเธฃเนเธฅเธฐเธเธฑเธเธ—เธถเธเธฅเธเธเธฒเธเธเนเธญเธกเธนเธฅ", 5),
            new CommandDefinition("!pos", "", "เนเธชเธ”เธเธเนเธญเธกเธนเธฅเธเธดเธเธฑเธ”เธเธฑเธเธเธธเธเธฑเธเธเธญเธเธเธธเธ“", 2)
      };
   }
}
