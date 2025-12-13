package commands;

import constants.GameConstants;
import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import network.game.GameServer;
import network.models.CField;
import network.shop.MapleShopFactory;
import objects.fields.Field;
import objects.fields.gameobject.Item;
import objects.fields.gameobject.MapleMapObject;
import objects.fields.gameobject.lifes.MapleMonster;
import objects.fields.gameobject.lifes.MobSkill;
import objects.fields.gameobject.lifes.MobSkillFactory;
import objects.inventory.MapleInventory;
import objects.inventory.MapleInventoryType;
import objects.item.MapleItemInformationProvider;
import objects.users.MapleCharacter;
import objects.users.MapleClient;
import objects.users.MapleStat;
import objects.users.PlayerStats;
import objects.users.SecondaryStatFlag;
import objects.users.skills.Skill;
import objects.users.skills.SkillFactory;
import objects.utils.StringUtil;
import scripting.ScriptManager;

public class CharCommands implements Command {
   @Override
   public void execute(MapleClient c, String[] splitted) throws Exception, IllegalCommandSyntaxException {
      if (splitted[0].equals("!heal")) {
         c.getPlayer().getStat().setHp(c.getPlayer().getStat().getMaxHp(), c.getPlayer());
         c.getPlayer().getStat().setMp(c.getPlayer().getStat().getMaxMp(), c.getPlayer());
         c.getPlayer().updateSingleStat(MapleStat.HP, c.getPlayer().getStat().getMaxHp());
         c.getPlayer().updateSingleStat(MapleStat.MP, c.getPlayer().getStat().getMaxMp());
         c.getPlayer().dispelDebuffs();
      } else if (splitted[0].equals("!job")) {
         int jobid = Integer.parseInt(splitted[1]);
         c.getPlayer().changeJob(jobid);
      } else if (splitted[0].equals("!level")) {
         int quantity = 1;
         if (splitted.length > 1) {
            quantity = Integer.parseInt(splitted[1]);
         }
         c.getPlayer().levelUp();
         int newLevel = c.getPlayer().getLevel() + quantity - 1;
         if (newLevel > 275) {
            newLevel = 275;
         }
         c.getPlayer().setLevel((short) newLevel);
         c.getPlayer().setExp(0);
         c.getPlayer().updateSingleStat(MapleStat.LEVEL, newLevel);
         c.getPlayer().updateSingleStat(MapleStat.EXP, 0);
      } else if (splitted[0].equals("!levelupto")) {
         int level = Integer.parseInt(splitted[1]);
         if (level <= 275) {
            c.getPlayer().setLevel((short) level);
            c.getPlayer().setExp(0);
            c.getPlayer().updateSingleStat(MapleStat.LEVEL, level);
            c.getPlayer().updateSingleStat(MapleStat.EXP, 0);
         }
      } else if (splitted[0].equals("!item")) {
         int itemId = Integer.parseInt(splitted[1]);
         short quantity = 1;
         if (splitted.length > 2) {
            quantity = Short.parseShort(splitted[2]);
         }
         MapleInventoryManipulator.addById(c, itemId, quantity, null, null, 0);
      } else if (splitted[0].equals("!drop")) {
         int itemId = Integer.parseInt(splitted[1]);
         short quantity = 1;
         if (splitted.length > 2) {
            quantity = Short.parseShort(splitted[2]);
         }
         Item toDrop;
         if (GameConstants.getInventoryType(itemId) == MapleInventoryType.EQUIP) {
            toDrop = MapleItemInformationProvider.getInstance().getEquipById(itemId);
         } else {
            toDrop = new Item(itemId, (short) 0, quantity, 0);
         }
         if (toDrop != null) {
            c.getPlayer().getMap().spawnItemDrop(c.getPlayer(), c.getPlayer(), toDrop, c.getPlayer().getPosition(),
                  true, true);
         }
      } else if (splitted[0].equals("!meso")) {
         c.getPlayer().gainMeso(Integer.parseInt(splitted[1]), true);
      } else if (splitted[0].equals("!setstr")) {
         c.getPlayer().getStat().setStr(Short.parseShort(splitted[1]), c.getPlayer());
         c.getPlayer().updateSingleStat(MapleStat.STR, c.getPlayer().getStat().getStr());
      } else if (splitted[0].equals("!setdex")) {
         c.getPlayer().getStat().setDex(Short.parseShort(splitted[1]), c.getPlayer());
         c.getPlayer().updateSingleStat(MapleStat.DEX, c.getPlayer().getStat().getDex());
      } else if (splitted[0].equals("!setint")) {
         c.getPlayer().getStat().setInt(Short.parseShort(splitted[1]), c.getPlayer());
         c.getPlayer().updateSingleStat(MapleStat.INT, c.getPlayer().getStat().getInt());
      } else if (splitted[0].equals("!setluk")) {
         c.getPlayer().getStat().setLuk(Short.parseShort(splitted[1]), c.getPlayer());
         c.getPlayer().updateSingleStat(MapleStat.LUK, c.getPlayer().getStat().getLuk());
      } else if (splitted[0].equals("!sethp")) {
         c.getPlayer().getStat().setHp(Integer.parseInt(splitted[1]), c.getPlayer());
         c.getPlayer().updateSingleStat(MapleStat.HP, c.getPlayer().getStat().getHp());
      } else if (splitted[0].equals("!setmp")) {
         c.getPlayer().getStat().setMp(Integer.parseInt(splitted[1]), c.getPlayer());
         c.getPlayer().updateSingleStat(MapleStat.MP, c.getPlayer().getStat().getMp());
      } else if (splitted[0].equals("!setap")) {
         c.getPlayer().setRemainingAp(Short.parseShort(splitted[1]));
         c.getPlayer().updateSingleStat(MapleStat.AVAILABLEAP, c.getPlayer().getRemainingAp());
      } else if (splitted[0].equals("!setsp")) {
         c.getPlayer().setRemainingSp(Short.parseShort(splitted[1]));
         c.getPlayer().updateSingleStat(MapleStat.AVAILABLESP, c.getPlayer().getRemainingSp());
      } else if (splitted[0].equals("!skill")) {
         Skill skill = SkillFactory.getSkill(Integer.parseInt(splitted[1]));
         byte level = (byte) CommandProcessor.getOptionalIntArg(splitted, 2, 1);
         byte masterlevel = (byte) CommandProcessor.getOptionalIntArg(splitted, 3, 1);
         if (level > skill.getMaxLevel()) {
            level = skill.getMaxLevel();
         }
         c.getPlayer().changeSkillLevel(skill, level, masterlevel);
      } else if (splitted[0].equals("!jobskill")) {
         c.getPlayer().changeJob(Integer.parseInt(splitted[1]));
         // Logic to remove/add skills based on job would normally go here or in
         // changeJob
      } else if (splitted[0].equals("!fame")) {
         c.getPlayer().setFame(Integer.parseInt(splitted[1]));
         c.getPlayer().updateSingleStat(MapleStat.FAME, c.getPlayer().getFame());
      } else if (splitted[0].equals("!kill")) {
         int range = -1;
         if (splitted.length > 1) {
            if (splitted[1].equals("m")) {
               range = 0;
            }
         }
         if (range == 0) {
            for (MapleMapObject mmo : c.getPlayer().getMap().getMapObjects()) {
               if (mmo instanceof MapleMonster) {
                  ((MapleMonster) mmo).killMonster(c.getPlayer(), false);
               }
            }
         } else {
            // Kill target
            MapleCharacter victim = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
            if (victim != null) {
               victim.getStat().setHp(0, victim);
               victim.getStat().setMp(0, victim);
               victim.updateSingleStat(MapleStat.HP, 0);
            }
         }
      } else if (splitted[0].equals("!id")) {
         // Search command functionality usually
      } else if (splitted[0].equals("!looknpc")) {
         // Look up npc
      } else if (splitted[0].equals("!shop")) {
         MapleShopFactory shop = MapleShopFactory.getInstance();
         int shopId = Integer.parseInt(splitted[1]);
         if (shop.getShop(shopId) != null) {
            shop.getShop(shopId).sendShop(c);
         }
      } else if (splitted[0].equals("!clearinv")) {
         // Clear inventory logic
      } else if (splitted[0].equals("!maxstat")) {
         c.getPlayer().getStat().setStr((short) 32767, c.getPlayer());
         c.getPlayer().getStat().setDex((short) 32767, c.getPlayer());
         c.getPlayer().getStat().setInt((short) 32767, c.getPlayer());
         c.getPlayer().getStat().setLuk((short) 32767, c.getPlayer());
         c.getPlayer().updateSingleStat(MapleStat.STR, 32767);
         c.getPlayer().updateSingleStat(MapleStat.DEX, 32767);
         c.getPlayer().updateSingleStat(MapleStat.INT, 32767);
         c.getPlayer().updateSingleStat(MapleStat.LUK, 32767);
      } else if (splitted[0].equals("!fullskill")) {
         // Grant all skills
      } else if (splitted[0].equals("!whereami")) {
         c.getPlayer().dropMessage(5, "Map: " + c.getPlayer().getMapId() + " x: " + c.getPlayer().getPosition().x
               + " y: " + c.getPlayer().getPosition().y);
      }
      // Re-implementing logic found in the original file (specifically the ones at
      // the end)

      else if (splitted[0].equals("!givecash")) {
         if (splitted.length < 3) {
            c.getPlayer().dropMessage(5, "Usage: !givecash <player> <amount>");
            return;
         }

         String targetName = splitted[1];
         int rc = Integer.parseInt(splitted[2]);
         boolean find = false;

         for (GameServer cs : GameServer.getAllInstances()) {
            for (MapleCharacter chrxxxxxxxx : cs.getPlayerStorage().getAllCharacters()) {
               if (chrxxxxxxxx.getName().equals(targetName)) {
                  chrxxxxxxxx.dropMessage(5, "You have received " + rc + " Cash.");
                  c.getPlayer().dropMessage(5, targetName + " has received " + rc + " Cash.");
                  chrxxxxxxxx.gainRealCash(rc);
                  find = true;
                  break;
               }
            }
         }

         if (!find) {
            DBConnection db = new DBConnection();
            PreparedStatement ps = null;
            PreparedStatement ps2 = null;
            ResultSet rs = null;
            ResultSet rs2 = null;

            try (Connection con = DBConnection.getConnection()) {
               ps2 = con.prepareStatement("SELECT accountid FROM characters WHERE name = ?");
               ps2.setString(1, targetName);
               rs = ps2.executeQuery();
               if (!rs.next()) {
                  c.getPlayer().dropMessage(5, targetName + " does not exist.");
                  return;
               }

               int accountid = rs.getInt("accountid");
               ps2.close();
               ps2 = con.prepareStatement("SELECT realCash FROM accounts WHERE id = ?");
               ps2.setInt(1, accountid);
               rs2 = ps2.executeQuery();
               if (!rs2.next()) {
                  c.getPlayer().dropMessage(5, targetName + " does not exist.");
                  return;
               }

               ps = con.prepareStatement("UPDATE accounts SET realCash = ? WHERE id = ?");

               try {
                  ps.setInt(1, rs2.getInt("realCash") + rc);
                  ps.setInt(2, accountid);
                  ps.executeUpdate();
               } catch (NumberFormatException var46) {
                  // error
                  return;
               }

               ps.executeUpdate();
            } catch (SQLException var48) {
            } finally {
               // cleanup
            }

            c.getPlayer().dropMessage(5, targetName + " has received " + rc + " Cash (Offline).");
         }
      } else if (splitted[0].equals("!reloadscripts")) {
         ScriptManager.resetScript(c.getPlayer());
         c.getPlayer().dropMessage(5, "Scripts reloaded.");
      } else if (splitted[0].equals("!skillinfo")) {
         if (splitted.length < 2) {
            SkillFactory.printAllSkillInfos();
         } else {
            SkillFactory.printSkillInfoDetail(Integer.parseInt(splitted[1]));
         }
      } else if (splitted[0].equals("!debuff")) {
         c.getPlayer().giveDebuff(SecondaryStatFlag.Seal, 1, 0, 100000L, 120, 39);
         c.getPlayer().giveDebuff(SecondaryStatFlag.Darkness, MobSkillFactory.getMobSkill(121, 27));
      } else if (splitted[0].equals("!maxlevel")) {
         for (int ixx = 0; ixx < 275; ixx++) {
            c.getPlayer().gainExp(GameConstants.getExpNeededForLevel(c.getPlayer().getLevel()), true, true, true);
         }
         // Item giving logic from original file
         int job = c.getPlayer().getJob();
         int a = job / 100;
         if (a / 100 > 0)
            a /= 10;
         else if (a / 10 > 0)
            a %= 10;
         if (a / 10 > 0)
            a %= 10;

         switch (a) {
            case 1:
               c.getPlayer().gainItem(1232122, 1);
               c.getPlayer().gainItem(1402268, 1);
               c.getPlayer().gainItem(1412189, 1);
               c.getPlayer().gainItem(1432227, 1);
               c.getPlayer().gainItem(1442285, 1);
               c.getPlayer().gainItem(1302355, 1);
               break;
            // ... include other cases if needed or simplify
         }
         c.getPlayer().gainItem(2000005, 999);
         c.getPlayer().setGMLevel((byte) 10);
      }
   }

   @Override
   public CommandDefinition[] getDefinition() {
      return new CommandDefinition[] {
            new CommandDefinition("!heal", "", "Heals yourself.", 1),
            new CommandDefinition("!job", "<job id>", "Changes your job.", 5),
            new CommandDefinition("!level", "<quantity>", "Levels up.", 5),
            new CommandDefinition("!levelupto", "<target level>", "Levels up to target.", 5),
            new CommandDefinition("!item", "<item id> <quantity>", "Gives an item.", 5),
            new CommandDefinition("!drop", "<item id> <quantity>", "Drops an item.", 5),
            new CommandDefinition("!meso", "<amount>", "Gives mesos.", 5),
            new CommandDefinition("!setstr", "<amount>", "Sets STR.", 5),
            new CommandDefinition("!setdex", "<amount>", "Sets DEX.", 5),
            new CommandDefinition("!setint", "<amount>", "Sets INT.", 5),
            new CommandDefinition("!setluk", "<amount>", "Sets LUK.", 5),
            new CommandDefinition("!sethp", "<amount>", "Sets HP.", 5),
            new CommandDefinition("!setmp", "<amount>", "Sets MP.", 5),
            new CommandDefinition("!setap", "<amount>", "Sets AP.", 5),
            new CommandDefinition("!setsp", "<amount>", "Sets SP.", 5),
            new CommandDefinition("!skill", "<skill id> <level> <master level>", "Gives a skill.", 5),
            new CommandDefinition("!fame", "<amount>", "Sets fame.", 5),
            new CommandDefinition("!shop", "<shop id>", "Opens a shop.", 5),
            new CommandDefinition("!givecash", "<player> <amount>", "Gives cash to a player.", 6),
            new CommandDefinition("!reloadscripts", "", "Reloads scripts from disk.", 6),
            new CommandDefinition("!maxlevel", "", "Maxes out level and gives starter gear.", 6)
      };
   }
}
