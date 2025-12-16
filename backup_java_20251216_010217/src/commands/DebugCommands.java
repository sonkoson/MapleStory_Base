package commands;

import constants.AutoHottimeManager;
import constants.GameConstants;
import constants.HottimeItemManager;
import constants.ServerConstants;
import constants.devtempConstants.MapleAutoNotice;
import constants.devtempConstants.MapleDailyGiftInfo;
import database.DBConfig;
import database.DBConnection;
import database.loader.CharacterSaveFlag;
import database.loader.CharacterSaveFlag2;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import network.SendPacketOpcode;
import network.auction.AuctionServer;
import network.center.Center;
import network.encode.PacketEncoder;
import network.game.GameServer;
import network.models.CField;
import network.models.CWvsContext;
import network.models.PacketHelper;
import network.shop.CashShopServer;
import objects.androids.Android;
import objects.context.MonsterCollection;
import objects.context.guild.GuildPacket;
import objects.effect.Effect;
import objects.effect.child.TextEffect;
import objects.fields.EliteMobEvent;
import objects.fields.Field;
import objects.fields.MapleMapObject;
import objects.fields.MapleMapObjectType;
import objects.fields.Portal;
import objects.fields.RandomPortal;
import objects.fields.RandomPortalGameType;
import objects.fields.RandomPortalType;
import objects.fields.child.etc.DamageMeasurementRank;
import objects.fields.child.etc.Field_EventRabbit;
import objects.fields.child.lucid.Field_LucidBattlePhase2;
import objects.fields.child.slime.Field_GuardianAngelSlime;
import objects.fields.child.will.Field_WillBattle;
import objects.fields.gameobject.AffectedArea;
import objects.fields.gameobject.Reactor;
import objects.fields.gameobject.ReactorFactory;
import objects.fields.gameobject.ReactorStats;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.fields.gameobject.lifes.MapleMonster;
import objects.fields.gameobject.lifes.MapleNPC;
import objects.fields.gameobject.lifes.MobTemporaryStatEffect;
import objects.fields.gameobject.lifes.MobTemporaryStatFlag;
import objects.fields.gameobject.lifes.Spawns;
import objects.fields.gameobject.lifes.mobskills.MobSkillFactory;
import objects.fields.gameobject.lifes.mobskills.MobSkillID;
import objects.fields.gameobject.lifes.mobskills.MobSkillInfo;
import objects.fields.gameobject.lifes.mobskills.MobSkillStat;
import objects.item.Equip;
import objects.item.IntensePowerCrystal;
import objects.item.Item;
import objects.item.MapleInventoryManipulator;
import objects.item.MapleInventoryType;
import objects.item.MapleItemInformationProvider;
import objects.quest.MapleQuest;
import objects.quest.MapleQuestStatus;
import objects.users.AntiMacroType;
import objects.users.MapleCabinet;
import objects.users.MapleCabinetItem;
import objects.users.MapleCharacter;
import objects.users.MapleClient;
import objects.users.MapleCoolDownValueHolder;
import objects.users.skills.Skill;
import objects.users.skills.SkillFactory;
import objects.users.stats.SecondaryStatEffect;
import objects.users.stats.SecondaryStatFlag;
import objects.utils.AdminClient;
import objects.utils.CurrentTime;
import objects.utils.HexTool;
import objects.utils.Pair;
import objects.utils.Randomizer;
import objects.utils.Rect;
import objects.utils.StringUtil;
import objects.utils.Timer;
import objects.wz.provider.MapleData;
import objects.wz.provider.MapleDataProvider;
import objects.wz.provider.MapleDataProviderFactory;
import objects.wz.provider.MapleDataTool;
import scripting.EventInstanceManager;
import scripting.NPCScriptManager;

public class DebugCommands implements Command {
   @Override
   public void execute(MapleClient c, String[] splitted) throws Exception {
      MapleCharacter player = c.getPlayer();
      if (splitted[0].equals("!quest")) {
         MapleQuest.getInstance(Integer.parseInt(splitted[1])).forfeit(c.getPlayer());
      } else if (splitted[0].equals("!nearestportal")) {
         Portal portal = player.getMap().findClosestSpawnpoint(player.getPosition());
         c.getPlayer().dropMessage(6, portal.getName() + " Portal ID: " + portal.getId() + " Script: " + portal.getScriptName());
      } else if (splitted[0].equals("!fakeinvite3")) {
         PacketEncoder p = new PacketEncoder();
         GuildPacket.InviteGuild g = new GuildPacket.InviteGuild(1, "TestGuild", 1, "Admin", 1, 112, 3, 3);
         g.encode(p);
         c.getPlayer().send(p.getPacket());
      } else if (splitted[0].equals("!testscript")) {
         NPCScriptManager.getInstance().dispose(c);
         NPCScriptManager.getInstance().start(c, 2003, splitted[1], true);
      } else if (splitted[0].equals("!debugspawn")) {
         c.getPlayer().dropMessage(6, c.getPlayer().getMap().spawnDebug());
      } else if (splitted[0].equals("!threads")) {
         Thread[] threads = new Thread[Thread.activeCount()];
         Thread.enumerate(threads);
         String filter = "";
         if (splitted.length > 1) {
            filter = splitted[1];
         }

         for (int i = 0; i < threads.length; i++) {
            String tstring = threads[i].toString();
            if (tstring.toLowerCase().indexOf(filter.toLowerCase()) > -1) {
               c.getPlayer().dropMessage(6, i + ": " + tstring);
            }
         }
      } else if (splitted[0].equals("!threadinfo")) {
         if (splitted.length < 1) {
            throw new IllegalCommandSyntaxException(1);
         }

         Thread[] threads = new Thread[Thread.activeCount()];
         Thread.enumerate(threads);
         Thread t = threads[Integer.parseInt(splitted[1])];
         c.getPlayer().dropMessage(6, t.toString() + ":");

         for (StackTraceElement elem : t.getStackTrace()) {
            c.getPlayer().dropMessage(6, elem.toString());
         }
      } else if (splitted[0].equals("!charinfo")) {
         c.getSession().writeAndFlush(CWvsContext.charInfo(player));
         player.getMap().removePlayer(player);
         player.getMap().addPlayer(player);
      } else if (splitted[0].equals("!toggledrops")) {
         player.getMap().toggleDrops();
      } else if (splitted[0].equalsIgnoreCase("!spawnreactor")) {
         ReactorStats reactorSt = ReactorFactory.getReactor(Integer.parseInt(splitted[1]));
         Reactor reactor = new Reactor(reactorSt, Integer.parseInt(splitted[1]));
         reactor.setDelay(-1);
         reactor.setPosition(c.getPlayer().getPosition());
         c.getPlayer().getMap().spawnReactor(reactor);
      } else if (splitted[0].equals("!hitreactor")) {
         c.getPlayer().getMap().getReactorByOid(Integer.parseInt(splitted[1])).hitReactor(c);
      } else if (!splitted[0].equals("!destroyreactor") && !splitted[0].equals("!mapreactors")) {
         if (splitted[0].equals("!destroyreactorrange")) {
            Field map = c.getPlayer().getMap();
            List<MapleMapObject> reactors = map.getMapObjectsInRange(
               c.getPlayer().getPosition(), Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.REACTOR)
            );
            if (splitted[1].equals("all")) {
               for (MapleMapObject reactorL : reactors) {
                  Reactor reactor2l = (Reactor)reactorL;
                  c.getPlayer().getMap().destroyReactor(reactor2l.getObjectId());
               }
            } else {
               c.getPlayer().getMap().destroyReactor(Integer.parseInt(splitted[1]));
            }
         } else if (splitted[0].equals("!resetreactors")) {
            c.getPlayer().getMap().resetReactors();
         } else if (splitted[0].equals("!setreactorstate")) {
            c.getPlayer().getMap().setReactorState(Byte.parseByte(splitted[1]));
         } else if (splitted[0].equals("!cleardrops")) {
            for (MapleMapObject ix : c.getPlayer()
               .getMap()
               .getMapObjectsInRange(c.getPlayer().getPosition(), Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.ITEM))) {
               c.getPlayer().getMap().removeMapObject(ix);
               c.getPlayer().getMap().broadcastMessage(CField.removeItemFromMap(ix.getObjectId(), 0, 0), ix.getPosition());
            }
         } else if (splitted[0].equals("!exprate")) {
            if (splitted.length > 1) {
               int rate = Integer.parseInt(splitted[1]);
               c.getChannelServer().setExpRate(rate);
               c.getPlayer().dropMessage(6, "Exp Rate has been changed to " + rate + "x.");
            } else {
               c.getPlayer().dropMessage(6, "เธงเธดเธเธตเนเธเน: !exprate <rate>");
            }
         } else if (splitted[0].equals("!mesorate")) {
            if (splitted.length > 1) {
               int rate = Integer.parseInt(splitted[1]);
               c.getChannelServer().setMesoRate(rate);
               c.getPlayer().dropMessage(6, "Meso Rate has been changed to " + rate + "x.");
            } else {
               c.getPlayer().dropMessage(6, "เธงเธดเธเธตเนเธเน: !mesorate <rate>");
            }
         } else if (splitted[0].equals("!dcall")) {
            for (GameServer cs : GameServer.getAllInstances()) {
               cs.getPlayerStorage().disconnectAll();
            }
         } else if (splitted[0].equals("!mapnpc")) {
            Field map = c.getPlayer().getMap();
            c.getPlayer().dropMessage(5, "Map : " + map.getId() + " - " + map.getStreetName() + " : " + map.getMapName());

            for (MapleMapObject mo : map.getAllNPCs()) {
               c.getPlayer().dropMessage(6, ((MapleNPC)mo).getId() + " : " + ((MapleNPC)mo).getName());
               c.getPlayer()
                  .dropMessage(
                     6,
                     "Coords (x: "
                        + mo.getPosition().x
                        + ", y: "
                        + mo.getPosition().y
                        + ", fh: "
                        + ((MapleNPC)mo).getFh()
                        + ", f: "
                        + ((MapleNPC)mo).getF()
                        + ", rx0: "
                        + ((MapleNPC)mo).getRx0()
                        + ", rx1: "
                        + ((MapleNPC)mo).getRx1()
                        + "), objectID: "
                        + mo.getObjectId()
                  );
            }
         } else if (splitted[0].equals("!mapportal")) {
            Field map = c.getPlayer().getMap();
            c.getPlayer().dropMessage(5, "Map : " + map.getId() + " - " + map.getStreetName() + " : " + map.getMapName());

            for (Portal mp : map.getPortals()) {
               c.getPlayer().dropMessage(6, mp.getId() + " : " + mp.getName() + " Script : " + mp.getScriptName() + " Target : " + mp.getTarget());
            }
         } else if (splitted[0].equals("!soulgauge")) {
            if (splitted.length < 2) {
               return;
            }

            int gauge = Integer.parseInt(splitted[1]);
            c.getPlayer().setSoulCount((short)gauge);
            c.getPlayer().temporaryStatResetBySkillID(c.getPlayer().getEquippedSoulSkill());
            SecondaryStatEffect skill = SkillFactory.getSkill(c.getPlayer().getEquippedSoulSkill())
               .getEffect(c.getPlayer().getSkillLevel(c.getPlayer().getEquippedSoulSkill()));
            if (c.getPlayer().getSoulCount() >= skill.getSoulMPCon()) {
               c.getPlayer()
                  .temporaryStatSet(c.getPlayer().getEquippedSoulSkill(), Integer.MAX_VALUE, SecondaryStatFlag.FullSoulMP, c.getPlayer().getSoulCount());
            }

            c.getPlayer().temporaryStatSet(c.getPlayer().getEquippedSoulSkill(), Integer.MAX_VALUE, SecondaryStatFlag.SoulMP, gauge);
            c.getSession().writeAndFlush(CWvsContext.enableActions(c.getPlayer(), false));
         } else if (splitted[0].equals("!setcombo")) {
            c.getPlayer().setLastCombo(System.currentTimeMillis());
            c.getPlayer().setCombo(Short.parseShort(splitted[1]));
            c.getSession().writeAndFlush(CField.aranCombo(Short.parseShort(splitted[1])));
         } else if (splitted[0].equals("!servermsg")) {
            TextEffect e = new TextEffect(-1, "[Daily Event] Daily event has started.\r\nThe event takes place 8-10 PM.", 50, 5000, 4, 0);
            Center.Broadcast.broadcastMessage(e.encodeForLocal());
         } else if (splitted[0].equals("!randomportal")) {
            int type = Randomizer.isSuccess(20) ? 3 : 2;
            int gameType = Integer.parseInt(splitted[1]);
            RandomPortalType portalType = RandomPortalType.get(type);
            RandomPortalGameType portalGameType = RandomPortalGameType.get(gameType);
            RandomPortal portal = new RandomPortal(portalType, Randomizer.rand(1000000, 9999999), player.getTruePosition(), player.getId(), portalGameType);
            int totalLevel = 0;
            long totalExp = 0L;
            long totalHp = 0L;
            int count = 0;

            for (Spawns s : player.getMap().getClosestSpawns(player.getTruePosition(), 30)) {
               int mobTemplateID = s.getMonster().getId();
               MapleMonster mob = MapleLifeFactory.getMonster(mobTemplateID);
               totalLevel += mob.getStats().getLevel();
               totalExp += mob.getStats().getExp();
               totalHp += mob.getStats().getMaxHp();
               count++;
            }

            totalLevel /= count;
            totalExp /= count;
            totalHp /= count;
            portal.setMobAvgHp(totalHp);
            portal.setMobAvgExp(totalExp);
            portal.setMobAvgLevel(totalLevel);
            player.updateOneInfo(26022, "exp", String.valueOf(totalExp));
            player.updateOneInfo(26022, "map", String.valueOf(player.getMapId()));
            if (type == 2) {
               player.send(CWvsContext.getScriptProgressMessage("A mysterious portal has appeared!"));
            } else {
               player.send(CWvsContext.getScriptProgressMessage("A chaotic portal has appeared!"));
            }

            player.setRandomPortal(portal);
            player.setRandomPortalSpawnedTime(System.currentTimeMillis());
            player.send(CField.randomPortalCreated(portal));
         } else if (splitted[0].equals("!randomportal2")) {
            for (int ix = 1; ix <= 3; ix++) {
               Field target = GameServer.getInstance(ix).getMapFactory().getMap(910010000);
               if (target == null) {
                  return;
               }

               Field_EventRabbit f = (Field_EventRabbit)target;
               if (f != null) {
                  f.resetFully(false);
                  MapleMonster mob = MapleLifeFactory.getMonster(9500006 + Randomizer.rand(0, 1));
                  mob.setHp(50000000L);
                  mob.getStats().setHp(50000000L);
                  target.spawnMonsterOnGroundBelow(mob, new Point(-82, 153));
                  f.setRabbitSpawnedTime(System.currentTimeMillis());
               }
            }

            for (GameServer cs : GameServer.getAllInstances()) {
               for (MapleCharacter chr : cs.getPlayerStorage().getAllCharacters()) {
                  if (chr != null) {
                     int type = 1;
                     int gameType = 9;
                     chr.removeRandomPortal();
                     RandomPortalType portalType = RandomPortalType.get(type);
                     RandomPortalGameType portalGameType = RandomPortalGameType.get(gameType);
                     Point pos = chr.getMap().calcDropPos(chr.getTruePosition(), chr.getPosition());
                     RandomPortal portal = new RandomPortal(portalType, Randomizer.rand(1000000, 9999999), pos, chr.getId(), portalGameType);
                     chr.updateOneInfo(15142, "gameType", "9");
                     chr.send(CField.addPopupSay(9062000, 5000, "The moon bunny event has started. Find the portal!", ""));
                     chr.send(CWvsContext.getScriptProgressMessage("The moon bunny event has started. Find the portal!"));
                     chr.setRandomPortal(portal);
                     chr.setRandomPortalSpawnedTime(System.currentTimeMillis());
                     chr.send(CField.randomPortalCreated(portal));
                  }
               }
            }
         } else if (splitted[0].equals("!fever")) {
            ServerConstants.expFeverRate = 1.0;
            ServerConstants.dropFeverRate = 1.0;
            ServerConstants.mesoFeverRate = 1.0;
            Center.cancelAutoFeverTask();
            AutoHottimeManager.loadAutoHottime();
            Center.registerAutoFever();
            c.getPlayer().dropMessage(5, "Fever event reloaded.");
         } else if (splitted[0].equals("!feveritem")) {
            Center.cancelAutoHotTimeItemTask();
            HottimeItemManager.loadHottimeItem();
            Center.registerHottimeItem();
            c.getPlayer().dropMessage(5, "Fever item event reloaded.");
         } else if (splitted[0].equals("!antimacro")) {
            player.tryAntiMacro(AntiMacroType.Auto, null);
         } else if (splitted[0].equals("!resetdailygift")) {
            for (GameServer cs : GameServer.getAllInstances()) {
               for (MapleCharacter chrx : cs.getPlayerStorage().getAllCharacters()) {
                  if (chrx != null) {
                     MapleDailyGiftInfo gift = chrx.getDailyGift();
                     gift.setDailyDay(0);
                     gift.setDailyCount(0);
                     chrx.send(CField.getDailyGiftRecord("count=0;day=0;date=" + CurrentTime.getCurrentTime2()));
                     chrx.updateInfoQuest(16700, "count=0;day=0;date=" + CurrentTime.getCurrentTime2());
                  }
               }
            }

            PreparedStatement ps = null;

            try (Connection con = DBConnection.getConnection()) {
               ps = con.prepareStatement("DELETE FROM `dailygift`");
               ps.executeUpdate();
            } catch (SQLException var151) {
               var151.printStackTrace();
            } finally {
               try {
                  if (ps != null) {
                     ps.close();
                     PreparedStatement var201 = null;
                  }
               } catch (SQLException var118) {
                  var118.printStackTrace();
               }
            }
         } else if (splitted[0].equals("!adminclient")) {
            AdminClient.main();
         } else if (splitted[0].equals("!resetcooldowns")) {
            for (MapleCoolDownValueHolder holder : c.getPlayer().getCooldowns()) {
               c.getPlayer().removeCooldown(holder.skillId);
               c.getSession().writeAndFlush(CField.skillCooldown(holder.skillId, 0));
            }
         } else if (splitted[0].equals("!stopdaily")) {
            ServerConstants.dailyEventType = null;
            c.getPlayer().dropMessage(5, "Daily Event stopped.");
            TextEffect e = new TextEffect(-1, "[Daily Event] Daily event has stopped.\r\nThe event takes place 8-10 PM.", 50, 5000, 4, 0);
            Center.Broadcast.broadcastMessage(e.encodeForLocal());
            Center.Broadcast.broadcastMessage(CField.chatMsg(3, "[Daily Event] เธเธดเธเธเธฃเธฃเธกเธฃเธฒเธขเธงเธฑเธเธซเธขเธธเธ”เนเธฅเนเธง เธเธดเธเธเธฃเธฃเธกเธกเธตเธเนเธงเธ 20:00-22:00 เธ."));
         } else if (splitted[0].equals("!skilllevel")) {
            if (splitted.length < 2) {
               c.getPlayer().dropMessage(5, "เธงเธดเธเธตเนเธเน: !skilllevel <skillID>");
               return;
            }

            int skillID = 0;

            try {
               skillID = Integer.valueOf(splitted[1]);
            } catch (NumberFormatException var139) {
               String searchName = "";

               for (int ix = 1; ix < splitted.length; ix++) {
                  String str = splitted[ix];
                  searchName = searchName + str;
                  if (ix < splitted.length - 1) {
                     searchName = searchName + " ";
                  }
               }

               MapleDataProvider dataProvider = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("net.sf.odinms.wzpath") + "/String.wz"));
               MapleData data = dataProvider.getData("Skill.img");
               List<Pair<Integer, String>> skillPairList = new LinkedList<>();

               for (MapleData skillIdData : data.getChildren()) {
                  try {
                     skillPairList.add(
                        new Pair<>(Integer.parseInt(skillIdData.getName()), MapleDataTool.getString(skillIdData.getChildByPath("name"), "NO-NAME"))
                     );
                  } catch (NumberFormatException var138) {
                  }
               }

               for (Pair<Integer, String> skillPair : skillPairList) {
                  if (skillPair.getRight().toLowerCase().equals(searchName)) {
                     skillID = skillPair.left;
                     break;
                  }
               }
            }

            if (skillID == 0) {
               c.getPlayer().dropMessage(5, "Skill (" + skillID + ") does not exist.");
               return;
            }

            int skillLevel = c.getPlayer().getTotalSkillLevel(skillID);
            Skill skill = SkillFactory.getSkill(skillID);
            if (skill == null) {
               c.getPlayer().dropMessage(5, "Skill (" + skillID + ") does not exist.");
               return;
            }

            c.getPlayer().dropMessage(5, skillID + "(" + SkillFactory.getSkill(skillID).getName() + ") Level: " + skillLevel + ".");
         } else if (splitted[0].equals("!cashrate")) {
            int setrate = 0;

            try {
               setrate = Integer.parseInt(splitted[1]);
            } catch (Exception var137) {
               c.getPlayer().dropMessage(5, "Error setting cash rate. Please check input.");
               return;
            }

            ServerConstants.cashPlusRate = setrate;
            c.getPlayer().dropMessage(5, "Cash rate set to " + setrate + "%(Total " + (100 + setrate) + "%).");
         } else if (splitted[0].equals("!checkcashrate")) {
            int cashrate = ServerConstants.cashPlusRate;
            c.getPlayer().dropMessage(5, "Current Cash Rate Plus: " + cashrate + "%(Total " + (100 + cashrate) + "%).");
         } else if (splitted[0].equals("!startmap")) {
            try {
               int startMap = Integer.parseInt(splitted[1]);
               ServerConstants.StartMap = startMap;
               c.getPlayer().dropMessage(5, "Start map set to " + startMap + ".");
            } catch (Exception var136) {
               c.getPlayer().dropMessage(5, "Error. check map id.");
            }
         } else if (splitted[0].equals("!townmap")) {
            try {
               int townMap = Integer.parseInt(splitted[1]);
               ServerConstants.TownMap = townMap;
               c.getPlayer().dropMessage(5, "เธ•เธฑเนเธเธเนเธฒเนเธเธเธ—เธตเนเน€เธกเธทเธญเธเน€เธเนเธ " + townMap + ".");
            } catch (Exception var135) {
               c.getPlayer().dropMessage(5, "Error. check map id.");
            }
         } else if (splitted[0].equals("!loadbgm")) {
            GameConstants.loadBGM();
            c.getPlayer().dropMessage(5, GameConstants.getBGMSize() + " BGMs loaded.");
         } else if (splitted[0].equals("!stopbgm")) {
            if (DBConfig.isGanglim) {
               for (GameServer gs : GameServer.getAllInstances()) {
                  gs.getMapFactory().getMap(c.getPlayer().getMapId()).clearMusicList();
               }

               c.getPlayer().dropMessage(5, "Stopped music in this map on all channels.");
            } else {
               c.getPlayer().getMap().clearMusicList();
               c.getPlayer().dropMessage(5, "Stopped music in this map.");
            }
         } else if (splitted[0].equals("!ungm")) {
            c.getPlayer().setGMLevel((byte)0);
            c.getPlayer().dropMessage(5, "GM Level set to 0.");
         } else if (splitted[0].equals("!completequest")) {
            try {
               int questid = Integer.parseInt(splitted[1]);
               c.getPlayer().updateOneInfo(questid, "clear", "");
               c.getPlayer().updateQuest(new MapleQuestStatus(MapleQuest.getInstance(questid), 0));
               c.getPlayer().updateQuest(new MapleQuestStatus(MapleQuest.getInstance(questid), 1));
            } catch (Exception var134) {
               c.getPlayer().dropMessage(5, "Error.");
            }
         } else if (splitted[0].equals("!togetherpoint")) {
            if (!DBConfig.isGanglim) {
               try {
                  int togetherPoint = Integer.parseInt(splitted[1]);
                  c.getPlayer().gainTogetherPoint(togetherPoint);
                  c.getPlayer().dropMessage(5, "Together Point: " + c.getPlayer().getTogetherPoint());
               } catch (Exception var133) {
                  c.getPlayer().dropMessage(5, "Invalid input.");
               }
            }

      } else if (splitted[0].equals("!giveitems")) {
         try {
            int TI = Integer.parseInt(splitted[1]);
            int slot = Integer.parseInt(splitted[2]);
            if (TI == 3) {
               TI = 4;
            } else if (TI == 4) {
               TI = 3;
            }

            int quantity = Integer.parseInt(splitted[3]);
            String targetx = splitted[4];
            MapleCharacter p = null;

            for (GameServer gs : GameServer.getAllInstances()) {
               for (Field map : gs.getMapFactory().getAllMaps()) {
                  for (MapleCharacter ch : new ArrayList<>(map.getCharacters())) {
                     if (ch != null && ch.getName().equals(targetx)) {
                        p = ch;
                        break;
                     }
                  }
               }
            }

            Item find = c.getPlayer().getInventory(MapleInventoryType.getByType((byte)TI)).getItem((short)slot);
            if (find == null) {
               c.getPlayer().dropMessage(5, "Item not found in your inventory.");
            } else {
               Item item = find.copy();
               item.setQuantity((short)quantity);
               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
               Calendar CAL = new GregorianCalendar(Locale.KOREA);
               String fDate = sdf.format(CAL.getTime());
               if (p == null) {
                  PreparedStatement ps = null;
                  PreparedStatement pse = null;
                  ResultSet rs = null;

                  try (Connection con = DBConnection.getConnection()) {
                     int accountID = 0;
                     int lastIndex = 0;
                     ps = con.prepareStatement("SELECT `accountid` FROM `characters` WHERE `name` = ?");
                     ps.setString(1, targetx);
                     rs = ps.executeQuery();

                     while (rs.next()) {
                        accountID = rs.getInt("accountid");
                     }

                     rs.close();
                     ps.close();
                     if (accountID <= 0) {
                        c.getPlayer().dropMessage(5, "Character or Account not found.");
                     } else {
                        for (GameServer gs : GameServer.getAllInstances()) {
                           for (Field map : gs.getMapFactory().getAllMaps()) {
                              for (MapleCharacter chx : new ArrayList<>(map.getCharacters())) {
                                 if (chx != null && chx.getAccountID() == accountID) {
                                    p = chx;
                                    break;
                                 }
                              }
                           }
                        }

                        if (p != null) {
                           MapleCabinet cabinet = p.getCabinet();
                           if (cabinet == null) {
                              c.getPlayer().dropMessage(5, "Target player's cabinet is full or invalid.");
                              return;
                           }

                           cabinet.addCabinetItem(
                              new MapleCabinetItem(
                                 cabinet.getNextIndex(),
                                 System.currentTimeMillis() + 604800000L,
                                 "[GM Gift]",
                                 fDate + " Sent by GM " + c.getPlayer().getName() + ".",
                                 item
                              )
                           );
                           p.send(CField.maplecabinetResult(8));
                           p.setSaveFlag(c.getPlayer().getSaveFlag() | CharacterSaveFlag.CABINET.getFlag());
                           p.dropMessage(5, "[System] เธเธธเธ“เนเธ”เนเธฃเธฑเธเธเธญเธเธเธงเธฑเธเธเธฒเธ GM " + c.getPlayer().getName() + ". Please check your cabinet.");
                           c.getPlayer().dropMessage(5, "Item sent to online player " + p.getName() + ".");
                        } else {
                           ps = con.prepareStatement("SELECT `cabinet_index` FROM `cabinet_items` WHERE `accountid` = ? ORDER BY `cabinet_index` DESC");
                           ps.setInt(1, accountID);
                           rs = ps.executeQuery();
                           if (rs.next()) {
                              lastIndex = rs.getInt("cabinet_index");
                           }

                           ps = con.prepareStatement(
                              "INSERT INTO `cabinet_items` (accountid, itemid, inventorytype, position, quantity, owner, GM_Log, uniqueid, expiredate, flag, `type`, sender, once_trade, cabinet_index, cabinet_expired_time, cabinet_title, cabinet_desc) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                              1
                           );
                           pse = con.prepareStatement(
                              "INSERT INTO `cabinet_equipment` VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                           );
                           MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
                           MapleInventoryType mit = MapleInventoryType.getByType((byte)(item.getItemId() / 1000000));
                           if (item.getPosition() == -55) {
                              return;
                           }

                           if (mit == MapleInventoryType.EQUIP && ii.isCash(item.getItemId())) {
                              mit = MapleInventoryType.CASH_EQUIP;
                           }

                           AtomicInteger idx = new AtomicInteger(1);
                           ps.setInt(idx.getAndIncrement(), accountID);
                           ps.setInt(idx.getAndIncrement(), item.getItemId());
                           ps.setInt(idx.getAndIncrement(), mit.getType());
                           ps.setInt(idx.getAndIncrement(), item.getPosition());
                           ps.setInt(idx.getAndIncrement(), item.getQuantity());
                           ps.setString(idx.getAndIncrement(), item.getOwner());
                           ps.setString(idx.getAndIncrement(), item.getGMLog());
                           if (item.getPet() != null) {
                              ps.setLong(idx.getAndIncrement(), Math.max(item.getUniqueId(), item.getPet().getUniqueId()));
                           } else {
                              ps.setLong(idx.getAndIncrement(), item.getUniqueId());
                           }

                           ps.setLong(idx.getAndIncrement(), item.getExpiration());
                           ps.setInt(idx.getAndIncrement(), item.getFlag());
                           ps.setByte(idx.getAndIncrement(), (byte)8);
                           ps.setString(idx.getAndIncrement(), item.getGiftFrom());
                           ps.setInt(idx.getAndIncrement(), item.getOnceTrade());
                           ps.setInt(idx.getAndIncrement(), lastIndex + 1);
                           ps.setLong(idx.getAndIncrement(), System.currentTimeMillis() + 604800000L);
                           ps.setString(idx.getAndIncrement(), "[GM Gift]");
                           ps.setString(idx.getAndIncrement(), fDate + " Sent by GM " + c.getPlayer().getName() + ".");
                           ps.executeUpdate();
                           rs = ps.getGeneratedKeys();
                           if (!rs.next()) {
                              rs.close();
                              return;
                           }

                           long iid = rs.getLong(1);
                           rs.close();
                           item.setInventoryId(iid);
                           if (mit.equals(MapleInventoryType.EQUIP)
                              || mit.equals(MapleInventoryType.EQUIPPED)
                              || mit.equals(MapleInventoryType.CASH_EQUIP)) {
                              Equip equip = (Equip)item;
                              if (equip.getUniqueId() > 0L && equip.getItemId() / 10000 == 166) {
                                 Android android = equip.getAndroid();
                                 if (android != null) {
                                    android.saveToDb();
                                 }
                              }

                              int index = 1;
                              pse.setLong(index++, iid);
                              pse.setInt(index++, 0);
                              pse.setInt(index++, accountID);
                              pse.setInt(index++, equip.getItemId());
                              pse.setInt(index++, Math.max(0, equip.getUpgradeSlots()));
                              pse.setInt(index++, equip.getLevel());
                              pse.setInt(index++, equip.getStr());
                              pse.setInt(index++, equip.getDex());
                              pse.setInt(index++, equip.getInt());
                              pse.setInt(index++, equip.getLuk());
                              pse.setInt(index++, equip.getArc());
                              pse.setInt(index++, equip.getArcEXP());
                              pse.setInt(index++, equip.getArcLevel());
                              pse.setInt(index++, equip.getHp());
                              pse.setInt(index++, equip.getMp());
                              pse.setInt(index++, equip.getHpR());
                              pse.setInt(index++, equip.getMpR());
                              pse.setInt(index++, equip.getWatk());
                              pse.setInt(index++, equip.getMatk());
                              pse.setInt(index++, equip.getWdef());
                              pse.setInt(index++, equip.getMdef());
                              pse.setInt(index++, equip.getAcc());
                              pse.setInt(index++, equip.getAvoid());
                              pse.setInt(index++, equip.getHands());
                              pse.setInt(index++, equip.getSpeed());
                              pse.setInt(index++, equip.getJump());
                              pse.setInt(index++, equip.getViciousHammer());
                              pse.setInt(index++, equip.getItemEXP());
                              pse.setInt(index++, equip.getDurability());
                              pse.setByte(index++, equip.getEnhance());
                              pse.setByte(index++, equip.getState());
                              pse.setByte(index++, equip.getLines());
                              pse.setInt(index++, equip.getPotential1());
                              pse.setInt(index++, equip.getPotential2());
                              pse.setInt(index++, equip.getPotential3());
                              pse.setInt(index++, equip.getPotential4());
                              pse.setInt(index++, equip.getPotential5());
                              pse.setInt(index++, equip.getPotential6());
                              pse.setInt(index++, equip.getFusionAnvil());
                              pse.setInt(index++, equip.getIncSkill());
                              pse.setShort(index++, equip.getCharmEXP());
                              pse.setShort(index++, equip.getPVPDamage());
                              pse.setShort(index++, equip.getSpecialAttribute());
                              pse.setByte(index++, equip.getReqLevel());
                              pse.setByte(index++, equip.getGrowthEnchant());
                              pse.setByte(index++, (byte)(equip.getFinalStrike() ? 1 : 0));
                              pse.setShort(index++, equip.getBossDamage());
                              pse.setShort(index++, equip.getIgnorePDR());
                              pse.setByte(index++, equip.getTotalDamage());
                              pse.setByte(index++, equip.getAllStat());
                              pse.setByte(index++, equip.getKarmaCount());
                              pse.setShort(index++, equip.getSoulName());
                              pse.setShort(index++, equip.getSoulEnchanter());
                              pse.setShort(index++, equip.getSoulPotential());
                              pse.setInt(index++, equip.getSoulSkill());
                              pse.setLong(index++, equip.getFire());
                              pse.setByte(index++, equip.getStarForce());
                              pse.setInt(index++, 0);
                              pse.setInt(index++, equip.getDownLevel());
                              pse.setInt(index++, equip.getSpecialPotential());
                              pse.setInt(index++, equip.getSPGrade());
                              pse.setInt(index++, equip.getSPAttack());
                              pse.setInt(index++, equip.getSPAllStat());
                              pse.setInt(index++, equip.getItemState());
                              pse.setInt(index++, equip.getCsGrade());
                              pse.setInt(index++, equip.getCsOption1());
                              pse.setInt(index++, equip.getCsOption2());
                              pse.setInt(index++, equip.getCsOption3());
                              pse.setLong(index++, equip.getCsOptionExpireDate());
                              pse.setLong(index++, equip.getExGradeOption());
                              pse.setInt(index++, equip.getCHUC());
                              pse.setInt(index++, equip.getClearCheck());
                              pse.setInt(index++, equip.isSpecialRoyal() ? 1 : 0);
                              pse.setLong(index++, equip.getSerialNumberEquip());
                              pse.setInt(index++, equip.getCashEnchantCount());
                              pse.executeUpdate();
                           }
                        }

                        c.getPlayer().dropMessage(5, "Item sent to offline account " + targetx + " (Cabinet).");
                     }
                  } catch (SQLException var155) {
                     new RuntimeException(var155);
                  } finally {
                     try {
                        if (ps != null) {
                           ps.close();
                           PreparedStatement var416 = null;
                        }

                        if (pse != null) {
                           pse.close();
                           PreparedStatement var419 = null;
                        }

                        if (rs != null) {
                           rs.close();
                           ResultSet var427 = null;
                        }
                     } catch (SQLException var119) {
                     }
                  }

                  MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.getByType((byte)TI), (short)slot, (short)quantity, false, false);
               } else {
                  MapleCabinet cabinet = p.getCabinet();
                  if (cabinet == null) {
                     c.getPlayer().dropMessage(5, "Target player's cabinet is full or invalid.");
                     return;
                  }

                  cabinet.addCabinetItem(
                     new MapleCabinetItem(
                        cabinet.getNextIndex(),
                        System.currentTimeMillis() + 604800000L,
                        "[GM Gift]",
                        fDate + " Sent by GM " + c.getPlayer().getName() + ".",
                        item
                     )
                  );
                  p.send(CField.maplecabinetResult(8));
                  p.setSaveFlag(p.getSaveFlag() | CharacterSaveFlag.CABINET.getFlag());
                  p.dropMessage(5, "[System] เธเธธเธ“เนเธ”เนเธฃเธฑเธเธเธญเธเธเธงเธฑเธเธเธฒเธ GM " + c.getPlayer().getName() + ". Please check your cabinet.");
                  MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.getByType((byte)TI), (short)slot, (short)quantity, false, false);
                  c.getPlayer().dropMessage(5, "Item sent to online player " + p.getName() + ".");
               }
            }
         } catch (NumberFormatException var157) {
            c.getPlayer().dropMessage(5, "เธงเธดเธเธตเนเธเน: !giveitems <type 1-5> <slot> <quantity> <name>");
         }
      } else if (splitted[0].equals("!goto")) {
         Field targetx = c.getChannelServer().getMapFactory().getMap(180000100);
         Portal targetPortal = null;
         if (splitted.length > 1) {
            try {
               targetPortal = targetx.getPortal(Integer.parseInt(splitted[1]));
            } catch (IndexOutOfBoundsException var127) {
               c.getPlayer().dropMessage(5, "Portal not found.");
            }
         }

         if (targetPortal == null) {
            targetPortal = targetx.getPortal(0);
         }

         if (c.getPlayer().getStat().getHp() != 0L) {
            EventInstanceManager eim = c.getPlayer().getEventInstance();
            if (eim != null && eim.hasEventTimer()) {
               eim.stopEventTimer();
               eim.dispose();
               c.getPlayer().setClock(0);
               c.getPlayer().setDeathCount(0);
            }

            c.getPlayer().changeMap(targetx, targetPortal);
         } else {
            c.getPlayer().dropMessage(5, "[System] เนเธกเนเธชเธฒเธกเธฒเธฃเธ–เธขเนเธฒเธขเนเธเธเธ—เธตเนเธเธ“เธฐเธ•เธฒเธขเธซเธฃเธทเธญเธ•เธดเธ”เธ เธฒเธฃเธเธดเธเนเธ”เน");
         }
      } else if (splitted[0].equals("!track")) {
         int channel = Integer.parseInt(splitted[1]);
         int mapID = Integer.parseInt(splitted[2]);
         GameServer gs = GameServer.getInstance(channel);
         Field map = gs.getMapFactory().getMap(mapID);
         List<String> onlines = new ArrayList<>();

         for (MapleCharacter p : map.getCharactersThreadsafe()) {
            onlines.add(p.getName());
         }

         c.getPlayer().dropMessage(5, "Monitoring Channel " + channel + " Map " + map.getMapName() + "(" + mapID + ").");
         String msg = "";
         msg = String.join(", ", onlines);
         c.getPlayer().dropMessage(5, msg);
      } else if (splitted[0].equals("!find")) {
         if (splitted.length < 2) {
            return;
         }

         boolean find;
         String v;
         String targetName;
         find = false;
         v = "";
         String targetxx = splitted[1];
         targetName = "";
         label3201:
         if (targetxx != null) {
            for (GameServer cs : GameServer.getAllInstances()) {
               for (Field map : cs.getMapFactory().getAllMaps()) {
                  for (MapleCharacter chrxxxxxxxx : new ArrayList<>(map.getCharacters())) {
                     if (chrxxxxxxxx != null && chrxxxxxxxx.getClient().getAccountName().equals(targetxx)) {
                        v = "Channel " + chrxxxxxxxx.getClient().getChannel() + " / Map ID: " + chrxxxxxxxx.getMapId();
                        targetName = chrxxxxxxxx.getName();
                        find = true;
                        break;
                     }
                  }
               }
            }

            for (MapleCharacter p : new ArrayList<>(CashShopServer.getPlayerStorage().getAllCharacters())) {
               if (p != null && p.getClient().getAccountName().equals(targetxx)) {
                  v = " Cash Shop";
                  find = true;
                  targetName = p.getName();
                  break;
               }
            }

            Iterator var319 = new ArrayList<>(AuctionServer.getPlayerStorage().getAllCharacters()).iterator();

            MapleCharacter px;
            do {
               if (!var319.hasNext()) {
                  break label3201;
               }

               px = (MapleCharacter)var319.next();
            } while (px == null || !px.getClient().getAccountName().equals(targetxx));

            v = " Auction House";
            find = true;
            targetName = px.getName();
         }

         if (find) {
            c.getPlayer().dropMessage(5, "Account " + splitted[1] + " (" + targetName + ") found at: " + v);
         } else {
            c.getPlayer().dropMessage(5, "Player not found.");
         }
      } else if (splitted[0].equals("!findacc")) {
         if (splitted.length < 2) {
            return;
         }

         boolean findx;
         String targetAccName;
         findx = false;
         String vx = "";
         String targetxxx = splitted[1];
         targetAccName = "";
         label3287:
         if (targetxxx != null) {
            for (GameServer cs : GameServer.getAllInstances()) {
               for (Field map : cs.getMapFactory().getAllMaps()) {
                  for (MapleCharacter chrxxxxxxxxx : new ArrayList<>(map.getCharacters())) {
                     if (chrxxxxxxxxx != null && chrxxxxxxxxx.getName().equals(targetxxx)) {
                        vx = "Channel " + chrxxxxxxxxx.getClient().getChannel() + " / Map ID: " + chrxxxxxxxxx.getMapId();
                        targetAccName = chrxxxxxxxxx.getClient().getAccountName();
                        findx = true;
                        break;
                     }
                  }
               }
            }

            for (MapleCharacter px : new ArrayList<>(CashShopServer.getPlayerStorage().getAllCharacters())) {
               if (px != null && px.getName().equals(targetxxx)) {
                  vx = " Cash Shop";
                  findx = true;
                  targetAccName = px.getClient().getAccountName();
                  break;
               }
            }

            Iterator var322 = new ArrayList<>(AuctionServer.getPlayerStorage().getAllCharacters()).iterator();

            MapleCharacter pxx;
            do {
               if (!var322.hasNext()) {
                  break label3287;
               }

               pxx = (MapleCharacter)var322.next();
            } while (pxx == null || !pxx.getName().equals(targetxxx));

            vx = " Auction House";
            findx = true;
            targetAccName = pxx.getClient().getAccountName();
         }

         if (findx) {
            c.getPlayer().dropMessage(5, "Character " + splitted[1] + " (Account: " + targetAccName + ") found at: " + vx);
         } else {
            try (
               Connection con = DBConnection.getConnection();
               PreparedStatement ps = con.prepareStatement("SELECT `accountid` FROM `characters` WHERE `name` = ?");
            ) {
               ps.setString(1, splitted[1]);

               try (ResultSet rs = ps.executeQuery()) {
                  if (rs.next()) {
                     int accid = rs.getInt("accountid");

                     try (PreparedStatement ps2 = con.prepareStatement("SELECT `name` FROM `accounts` WHERE `id` = ?")) {
                        ps2.setInt(1, accid);

                        try (ResultSet rs2 = ps2.executeQuery()) {
                           if (rs2.next()) {
                              findx = true;
                              targetAccName = rs2.getString("name");
                           }
                        }
                     }
                  }
               }
            } catch (Exception var148) {
            }

            if (findx) {
               c.getPlayer().dropMessage(5, "Character " + splitted[1] + " (Account: " + targetAccName + ") found (Offline).");
            } else {
               c.getPlayer().dropMessage(5, "Character not found.");
            }
         }
      } else if (splitted[0].equals("!dcplayer")) {
         if (splitted.length < 2) {
            return;
         }

         String targetxxxx = splitted[1];
         boolean findxx = false;
         String vxx = "";
         if (targetxxxx != null) {
            for (GameServer cs : GameServer.getAllInstances()) {
               for (Field map : cs.getMapFactory().getAllMaps()) {
                  for (MapleCharacter chrxxxxxxxxxx : new ArrayList<>(map.getCharacters())) {
                     if (chrxxxxxxxxxx != null && chrxxxxxxxxxx.getClient().getAccountName().equals(targetxxxx)) {
                        vxx = " Name: "
                           + chrxxxxxxxxxx.getName()
                           + " / Channel: "
                           + chrxxxxxxxxxx.getClient().getChannel()
                           + " / Map: "
                           + chrxxxxxxxxxx.getMapId();
                        map.removePlayer(chrxxxxxxxxxx);
                        chrxxxxxxxxxx.getClient().disconnect(false, map);
                        System.out.println("[System] DC Player command executed.");
                        chrxxxxxxxxxx.getClient().getSession().close();
                        findxx = true;
                     }
                  }
               }
            }

            for (MapleCharacter pxx : new ArrayList<>(CashShopServer.getPlayerStorage().getAllCharacters())) {
               if (pxx != null && pxx.getClient().getAccountName().equals(targetxxxx)) {
                  vxx = " Name: " + pxx.getName() + " / Channel: Cash Shop";
                  pxx.getClient().setPlayer(pxx);
                  pxx.getClient().disconnect(false);
                  pxx.getClient().getSession().close();
                  findxx = true;
               }
            }

            for (MapleCharacter pxxx : new ArrayList<>(AuctionServer.getPlayerStorage().getAllCharacters())) {
               if (pxxx != null && pxxx.getClient().getAccountName().equals(targetxxxx)) {
                  vxx = " Name: " + pxxx.getName() + " / Channel: Auction House";
                  pxxx.getClient().setPlayer(pxxx);
                  pxxx.getClient().disconnect(false);
                  pxxx.getClient().getSession().close();
                  findxx = true;
               }
            }
         }

         if (findxx) {
            c.getPlayer().dropMessage(5, "Player has been disconnected. (" + vxx + " )");
         } else {
            c.getPlayer().dropMessage(5, "Player not found.");
         }
      } else if (splitted[0].equals("!dcaccount")) {
         if (splitted.length < 2) {
            return;
         }

         String targetxxxxx = splitted[1];
         boolean findxxx = false;
         if (targetxxxxx != null) {
            for (GameServer cs : GameServer.getAllInstances()) {
               for (Field map : cs.getMapFactory().getAllMaps()) {
                  for (MapleCharacter chrxxxxxxxxxxx : new ArrayList<>(map.getCharacters())) {
                     if (chrxxxxxxxxxxx != null && chrxxxxxxxxxxx.getName().equals(targetxxxxx)) {
                        chrxxxxxxxxxxx.getClient().disconnect(false);
                        chrxxxxxxxxxxx.getClient().getSession().close();
                        findxxx = true;
                     }
                  }
               }
            }

            for (MapleCharacter pxxxx : new ArrayList<>(CashShopServer.getPlayerStorage().getAllCharacters())) {
               if (pxxxx.getName().equals(targetxxxxx)) {
                  pxxxx.getClient().setPlayer(pxxxx);
                  pxxxx.getClient().disconnect(false);
                  pxxxx.getClient().getSession().close();
                  findxxx = true;
               }
            }

            for (MapleCharacter pxxxxx : new ArrayList<>(AuctionServer.getPlayerStorage().getAllCharacters())) {
               if (pxxxxx.getName().equals(targetxxxxx)) {
                  pxxxxx.getClient().setPlayer(pxxxxx);
                  pxxxxx.getClient().disconnect(false);
                  pxxxxx.getClient().getSession().close();
                  findxxx = true;
               }
            }
         }

         if (findxxx) {
            c.getPlayer().dropMessage(5, "Account has been disconnected.");
         } else {
            c.getPlayer().dropMessage(5, "Account not found.");
         }
      } else if (splitted[0].equals("!captcha")) {
         c.getPlayer().dropMessage(5, "Captcha Code (" + splitted[1] + "): " + StringUtil.getRandomCaptcha(Integer.parseInt(splitted[1])));
      } else if (splitted[0].equals("!antimacrotest")) {
         String targetxxxxxx = splitted[1];
         MapleCharacter pxxxxxx = c.getPlayer().getMap().getCharacterByName(targetxxxxxx);
         if (player == null) {
            c.getPlayer().dropMessage(1, "Player not found.");
            return;
         }

         pxxxxxx.tryAntiMacro(AntiMacroType.FromGM, c.getPlayer());
      } else if (splitted[0].equals("!updatequestinfo") || splitted[0].equals("!qex")) {
         c.getPlayer().updateOneInfo(Integer.parseInt(splitted[1]), splitted[2], splitted[3]);
         c.getPlayer().dropMessage(5, "Quest (" + splitted[1] + ") Key (" + splitted[2] + ") updated to Value (" + splitted[3] + ").");
      } else if (splitted[0].equals("!cop")) {
         Item item = new Item(4001886, (short)0, (short)1, 0);
         item.setGMLog(CurrentTime.getAllCurrentTime() + " Created by " + c.getPlayer().getName() + " via !cop command.");
         MapleInventoryManipulator.addbyItem(c, item);
         long uniqueID = item.getUniqueId();
         int mobID = Integer.parseInt(splitted[1]);
         int partyMember = Integer.parseInt(splitted[2]);
         int price = Integer.parseInt(splitted[3]);
         c.getPlayer()
            .getIntensePowerCrystals()
            .put(
               uniqueID,
               new IntensePowerCrystal(c.getPlayer().getId(), uniqueID, partyMember, mobID, price, 0L, PacketHelper.getTime(System.currentTimeMillis()))
            );
         c.getPlayer().sendIntensePowerCrystalUpdate();
         c.getPlayer().setSaveFlag2(c.getPlayer().getSaveFlag2() | CharacterSaveFlag2.INTENSE_POWER_CRYSTAL.getFlag());
      } else if (splitted[0].equals("!mqtr")) {
         MapleQuest.loadModifiedQuestTime();
         GameServer.getAllInstances().forEach(game -> game.getPlayerStorage().getAllCharacters().forEach(MapleQuest::sendModifiedQuestTime));
         c.getPlayer().dropMessage(5, "Modified Quest Time reloaded.");
      } else if (splitted[0].equals("!autonoticereset")) {
         MapleAutoNotice.autoNotice = null;
         Center.cancelAutoNoticeTask();
         MapleAutoNotice.autoNotice = new MapleAutoNotice();
         MapleAutoNotice.autoNotice.Load();
         Center.registerAutoNotice();
         c.getPlayer().dropMessage(5, "Auto Notice has been reloaded.");
      } else if (splitted[0].equals("!resetdamagerank")) {
         DamageMeasurementRank.resetRank();
         c.getPlayer().dropMessage(5, "[System] เธฃเธตเน€เธเนเธ•เธญเธฑเธเธ”เธฑเธเธงเธฑเธ”เธเธงเธฒเธกเน€เธชเธตเธขเธซเธฒเธขเนเธฅเนเธง");

         for (GameServer cs : GameServer.getAllInstances()) {
            for (Field map : cs.getMapFactory().getAllMaps()) {
               for (MapleCharacter chrxxxxxxxxxxxx : new ArrayList<>(map.getCharacters())) {
                  if (chrxxxxxxxxxxxx != null) {
                     DamageMeasurementRank.applyDamageRankBuff(chrxxxxxxxxxxxx);
                  }
               }
            }
         }

         for (MapleCharacter chrxxxxxxxxxxxxx : CashShopServer.getPlayerStorage().getAllCharacters()) {
            if (chrxxxxxxxxxxxxx != null) {
               DamageMeasurementRank.applyDamageRankBuff(chrxxxxxxxxxxxxx);
            }
         }

         for (MapleCharacter chrxxxxxxxxxxxxxx : AuctionServer.getPlayerStorage().getAllCharacters()) {
            if (chrxxxxxxxxxxxxxx != null) {
               DamageMeasurementRank.applyDamageRankBuff(chrxxxxxxxxxxxxxx);
            }
         }

         Center.Broadcast.broadcastMessage(CField.chatMsg(5, "Damage Measurement Rank has been reset."));
      } else {
         Field map = c.getPlayer().getMap();

         for (MapleMapObject reactorL : map.getMapObjectsInRange(
            c.getPlayer().getPosition(), Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.REACTOR)
         )) {
            Reactor reactor2l = (Reactor)reactorL;
            c.getPlayer()
               .dropMessage(
                  6,
                  "Reactor: Name/OID: "
                     + reactor2l.getObjectId()
                     + " Reactor ID: "
                     + reactor2l.getReactorId()
                     + " State: "
                     + reactor2l.getState()
                     + " Position: "
                     + reactor2l.getPosition().toString()
               );
         }
      }

   }

   @Override
   public CommandDefinition[] getDefinition() {
      return new CommandDefinition[] {
            new CommandDefinition("!quest", "<id>", "Forfeits the specified quest.", 6),
            new CommandDefinition("!nearestportal", "", "Shows the info of the nearest portal.", 6),
            new CommandDefinition("!fakeinvite3", "", "Sends a fake guild invite packet.", 6),
            new CommandDefinition("!testscript", "<script>", "Tests an NPC script.", 6),
            new CommandDefinition("!debugspawn", "", "Debugs spawn points in the map.", 6),
            new CommandDefinition("!threads", "[filter]", "Lists active threads, optionally filtered.", 6),
            new CommandDefinition("!threadinfo", "<index>", "Shows stack trace for a thread.", 6),
            new CommandDefinition("!charinfo", "", "Shows character info packet.", 6),
            new CommandDefinition("!toggledrops", "", "Toggles drop visibility.", 6),
            new CommandDefinition("!spawnreactor", "<id>", "Spawns a reactor.", 6),
            new CommandDefinition("!hitreactor", "<oid>", "Hits a reactor by OID.", 6),
            new CommandDefinition("!destroyreactor", "<oid>", "Destroys a reactor by OID.", 6),
            new CommandDefinition("!destroyreactorrange", "<range/all>", "Destroys reactors in range or all.", 6),
            new CommandDefinition("!resetreactors", "", "Resets all reactors in the map.", 6),
            new CommandDefinition("!setreactorstate", "<state>", "Sets reactor state.", 6),
            new CommandDefinition("!cleardrops", "", "Clears all drops in the map.", 6),
            new CommandDefinition("!exprate", "<rate>", "Sets the EXP rate.", 6),
            new CommandDefinition("!mesorate", "<rate>", "Sets the Meso rate.", 6),
            new CommandDefinition("!dcall", "", "Disconnects all players.", 6),
            new CommandDefinition("!mapnpc", "", "Lists all NPCs in the map.", 6),
            new CommandDefinition("!mapportal", "", "Lists all portals in the map.", 6),
            new CommandDefinition("!soulgauge", "<amount>", "Sets soul gauge amount.", 6),
            new CommandDefinition("!setcombo", "<amount>", "Sets Aran combo amount.", 6),
            new CommandDefinition("!servermsg", "", "Broadcasts a server message.", 6),
            new CommandDefinition("!randomportal", "<gametype>", "Spawns a random portal.", 6),
            new CommandDefinition("!randomportal2", "", "Spawns event random portals.", 6),
            new CommandDefinition("!fever", "", "Reloads fever event.", 6),
            new CommandDefinition("!feveritem", "", "Reloads fever items.", 6),
            new CommandDefinition("!antimacro", "", "Tests antimacro.", 6),
            new CommandDefinition("!resetdailygift", "", "Resets daily gift for all.", 6),
            new CommandDefinition("!adminclient", "", "Starts AdminClient.", 6),
            new CommandDefinition("!resetcooldowns", "", "Resets skill cooldowns.", 6),
            new CommandDefinition("!stopdaily", "", "Stops daily event.", 6),
            new CommandDefinition("!skilllevel", "<skillid/name>", "Checks skill level.", 6),
            new CommandDefinition("!cashrate", "<rate>", "Sets cash rate.", 6),
            new CommandDefinition("!checkcashrate", "", "Checks cash rate.", 6),
            new CommandDefinition("!startmap", "<mapid>", "Sets start map.", 6),
            new CommandDefinition("!townmap", "<mapid>", "Sets town map.", 6),
            new CommandDefinition("!loadbgm", "", "Reloads BGMs.", 6),
            new CommandDefinition("!stopbgm", "", "Stops BGM.", 6),
            new CommandDefinition("!ungm", "", "Sets GM level to 0.", 6),
            new CommandDefinition("!completequest", "<questid>", "Completes a quest.", 6),
            new CommandDefinition("!togetherpoint", "<amount>", "Gains together points.", 6),
            new CommandDefinition("!giveitems", "<type> <slot> <qty> <player>", "Gives items to player/cabinet.", 6),
            new CommandDefinition("!goto", "<portalid>", "Warps to map 180000100.", 6),
            new CommandDefinition("!track", "<ch> <map>", "Tracks map users.", 6),
            new CommandDefinition("!find", "<name>", "Finds an account.", 6),
            new CommandDefinition("!findacc", "<name>", "Finds a character by name.", 6),
            new CommandDefinition("!dcplayer", "<accname>", "Disconnects a player by account name.", 6),
            new CommandDefinition("!dcaccount", "<accname>", "Disconnects an account.", 6),
            new CommandDefinition("!captcha", "<code>", "Tests captcha.", 6),
            new CommandDefinition("!antimacrotest", "<name>", "Tests antimacro on player.", 6),
            new CommandDefinition("!updatequestinfo", "<id> <key> <val>", "Updates quest info.", 6),
            new CommandDefinition("!cop", "<mobid> <party> <price>", "Creates intense power crystal.", 6),
            new CommandDefinition("!mqtr", "", "Reloads modified quest time.", 6),
            new CommandDefinition("!autonoticereset", "", "Reloads auto notice.", 6),
            new CommandDefinition("!resetdamagerank", "", "Resets damage rank.", 6)
      };
   }
}
