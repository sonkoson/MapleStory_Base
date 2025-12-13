package commands;

import constants.devtempConstants.MapleClientCRC;
import constants.devtempConstants.MapleDailyGift;
import constants.devtempConstants.MapleDimensionalMirror;
import constants.devtempConstants.MapleEventList;
import constants.devtempConstants.MapleFishing;
import constants.devtempConstants.MapleGoldenChariot;
import constants.devtempConstants.MapleMonsterCustomHP;
import network.RecvPacketOpcode;
import network.SendPacketOpcode;
import network.center.WeeklyItemManager;
import network.game.GameServer;
import objects.fields.child.etc.DamageMeasurementRank;
import objects.fields.gameobject.lifes.MapleMonsterInformationProvider;
import objects.item.rewards.RoyalStyle;
import objects.shop.MapleShopFactory;
import objects.users.MapleClient;
import objects.users.skills.SkillFactory;
import scripting.PortalScriptManager;
import scripting.ReactorScriptManager;

public class ReloadingCommands implements Command {
   @Override
   public void execute(MapleClient c, String[] splitted) throws Exception, IllegalCommandSyntaxException {
      if (splitted[0].equals("!reloadops")) {
         SendPacketOpcode.reloadValues();
         RecvPacketOpcode.reloadValues();
         c.getPlayer().dropMessage(5, "[System] Ops reloading completed.");
      } else if (splitted[0].equals("!setop")) {
         for (SendPacketOpcode send : SendPacketOpcode.values()) {
            if (send.name().equals(splitted[1])) {
               send.setValue(Short.parseShort(splitted[2]));
               c.getPlayer().dropMessage(5, "[Set Op] " + send.name() + " : " + send.getValue());
               break;
            }
         }
      } else if (splitted[0].equals("!reloaddrops")) {
         MapleMonsterInformationProvider.getInstance().clearDrops();
         ReactorScriptManager.getInstance().clearDrops();
         c.getPlayer().dropMessage(5, "[System] Drops reloading completed.");
      } else if (splitted[0].equals("!reloadportals")) {
         PortalScriptManager.getInstance().clearScripts();
         c.getPlayer().dropMessage(5, "[System] Portal scripts reloading completed.");
      } else if (splitted[0].equals("!reloadshops")) {
         MapleShopFactory.getInstance().clear();
         c.getPlayer().dropMessage(5, "[System] Shops reloading completed.");
      } else if (splitted[0].equals("!reloadevents")) {
         for (GameServer instance : GameServer.getAllInstances()) {
            instance.reloadEvents();
         }
         c.getPlayer().dropMessage(5, "[System] Events reloading completed.");
      } else if (splitted[0].equals("!reloadskills")) {
         SkillFactory.load();
         c.getPlayer().dropMessage(5, "[System] Skills reloading completed.");
      } else if (splitted[0].equals("!reloadweekly")) {
         WeeklyItemManager.loadWeeklyItems();
         c.getPlayer().dropMessage(5, "[System] Weekly items reloading completed.");
      } else if (splitted[0].equals("!saveweekly")) {
         WeeklyItemManager.saveWeeklyItems();
         c.getPlayer().dropMessage(5, "[System] Weekly items saved to DB.");
      } else if (splitted[0].equals("!resetgoldapple")) {
         RoyalStyle.resetGoldApple();
         c.getPlayer().dropMessage(5, "[System] Gold Apple reset completed.");
      } else if (splitted[0].equals("!reloadmobhp")) {
         MapleMonsterCustomHP.Load();
         c.getPlayer().dropMessage(5, "[System] Excel Custom Mob HP loading completed.");
      } else if (splitted[0].equals("!reloaddailygift")) {
         MapleDailyGift.Load();
         c.getPlayer().dropMessage(5, "[System] Excel Daily Gift loading completed.");
      } else if (splitted[0].equals("!reloaddimen")) {
         MapleDimensionalMirror.Load();
         c.getPlayer().dropMessage(5, "[System] Excel Dimensional Mirror loading completed.");
      } else if (splitted[0].equals("!reloadeventlist")) {
         MapleEventList.Load();
         c.getPlayer().dropMessage(5, "[System] Excel Event List loading completed.");
      } else if (splitted[0].equals("!reloadfishing")) {
         MapleFishing.Load();
         c.getPlayer().dropMessage(5, "[System] Excel Fishing loading completed.");
      } else if (splitted[0].equals("!reloadgoldenchariot")) {
         MapleGoldenChariot.Load();
         c.getPlayer().dropMessage(5, "[System] Excel Golden Chariot loading completed.");
      } else if (splitted[0].equals("!reloadcrc")) {
         MapleClientCRC.Load();
         c.getPlayer().dropMessage(5, "[System] Excel WZ CRC loading completed.");
      } else if (splitted[0].equals("!reloadrank")) {
         DamageMeasurementRank.loadRank();
         c.getPlayer().dropMessage(5, "[System] Damage Measurement Rank loading completed.");
      }
   }

   @Override
   public CommandDefinition[] getDefinition() {
      return new CommandDefinition[] {
            new CommandDefinition("!reloadops", "", "Reloads Send/Recv Packet Opcodes from properties.", 6),
            new CommandDefinition("!setop", "<name> <value>", "Sets a specific opcode value.", 6),
            new CommandDefinition("!reloadportals", "", "Clears portal scripts from memory.", 6),
            new CommandDefinition("!reloaddrops", "", "Clears drops from memory.", 6),
            new CommandDefinition("!reloadshops", "", "Clears shops from memory.", 6),
            new CommandDefinition("!reloadevents", "", "Reloads events on all channel servers.", 6),
            new CommandDefinition("!reloadskills", "", "Reloads skills from WZ.", 6),
            new CommandDefinition("!reloadweekly", "", "Reloads weekly items from DB.", 6),
            new CommandDefinition("!saveweekly", "", "Saves weekly items to DB.", 6),
            new CommandDefinition("!resetgoldapple", "", "Resets Gold Apple items.", 6),
            new CommandDefinition("!reloadmobhp", "", "Reloads custom mob HP.", 6),
            new CommandDefinition("!reloaddailygift", "", "Reloads daily gift info.", 6),
            new CommandDefinition("!reloaddimen", "", "Reloads dimensional mirror info.", 6),
            new CommandDefinition("!reloadeventlist", "", "Reloads event list.", 6),
            new CommandDefinition("!reloadfishing", "", "Reloads fishing info.", 6),
            new CommandDefinition("!reloadgoldenchariot", "", "Reloads golden chariot info.", 6),
            new CommandDefinition("!reloadcrc", "", "Reloads CRC info.", 6),
            new CommandDefinition("!reloadrank", "", "Reloads damage measurement rank.", 6)
      };
   }
}
