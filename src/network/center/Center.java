package network.center;

import api.DonationRequest;
import com.google.common.collect.UnmodifiableIterator;
import constants.AutoHottimeEntry;
import constants.AutoHottimeManager;
import constants.DailyEventType;
import constants.DailyQuests;
import constants.GameConstants;
import constants.HottimeItemEntry;
import constants.HottimeItemManager;
import constants.QuestExConstants;
import constants.ServerConstants;
import constants.TimeScheduleEntry;
import constants.WeeklyQuests;
import constants.devtempConstants.MapleAutoNotice;
import constants.devtempConstants.MapleDailyGiftInfo;
import database.DBConfig;
import database.DBConnection;
import database.DBEventManager;
import database.DBProcessor;
import database.DBSelectionKey;
import database.callback.DBCallback;
import database.loader.CharacterSaveFlag;
import database.loader.ItemLoader;
import java.awt.Point;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import logging.LoggingManager;
import logging.entry.DonationLog;
import logging.entry.MacroLog;
import network.SendPacketOpcode;
import network.auction.AuctionInfo;
import network.auction.AuctionItemPackage;
import network.auction.AuctionServer;
import network.center.praise.PraiseDonationMeso;
import network.center.praise.PraiseDonationMesoRank;
import network.center.praise.PraisePointRank;
import network.discordbot.DiscordBotHandler;
import network.encode.PacketEncoder;
import network.game.GameServer;
import network.game.PlayerStorage;
import network.game.processors.HyperHandler;
import network.models.CField;
import network.models.CWvsContext;
import network.shop.CashShopServer;
import objects.context.ReportLogEntry;
import objects.context.expedition.Expedition;
import objects.context.friend.Friend;
import objects.context.friend.FriendEntry;
import objects.context.guild.GuildCharacter;
import objects.context.guild.GuildPacket;
import objects.context.messenger.MessengerCharacter;
import objects.context.party.PartyMemberEntry;
import objects.context.party.PartyOperation;
import objects.context.party.PartySearch;
import objects.context.party.PartySearchType;
import objects.context.waitqueue.WaitQueue;
import objects.context.waitqueue.WaitQueueResult;
import objects.context.waitqueue.WaitQueueType;
import objects.effect.child.TextEffect;
import objects.fields.Field;
import objects.fields.FieldEvent;
import objects.fields.FieldLimitType;
import objects.fields.MapleDynamicFoothold;
import objects.fields.RandomPortal;
import objects.fields.RandomPortalGameType;
import objects.fields.RandomPortalType;
import objects.fields.SecondAtom;
import objects.fields.Wreckage;
import objects.fields.child.dojang.DojangRanking;
import objects.fields.child.dreambreaker.DreamBreakerRank;
import objects.fields.child.etc.DamageMeasurementRank;
import objects.fields.child.etc.Field_EventRabbit;
import objects.fields.child.will.Field_WillBattle;
import objects.fields.fieldset.FieldSet;
import objects.fields.gameobject.AffectedArea;
import objects.fields.gameobject.Drop;
import objects.fields.gameobject.RuneStone;
import objects.fields.gameobject.RuneStoneType;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.fields.gameobject.lifes.MapleMonster;
import objects.fields.gameobject.lifes.MapleNPC;
import objects.fields.gameobject.lifes.MobTemporaryStatEffect;
import objects.fields.gameobject.lifes.Spawns;
import objects.item.Equip;
import objects.item.Item;
import objects.item.MapleInventoryIdentifier;
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
import objects.users.MapleDiseaseValueHolder;
import objects.users.achievement.AchievementFactory;
import objects.users.stats.SecondaryStat;
import objects.users.stats.SecondaryStatFlag;
import objects.utils.AdminClient;
import objects.utils.CollectionUtil;
import objects.utils.CurrentTime;
import objects.utils.FileoutputUtil;
import objects.utils.Pair;
import objects.utils.Properties;
import objects.utils.Randomizer;
import objects.utils.Table;
import objects.utils.Timer;

public class Center {
   public static String RankerCustomText = "";
   public static String Ranker2CustomText = "";
   public static String Ranker3CustomText = "";
   private static final int CHANNELS_PER_THREAD = 3;
   private static ScheduledFuture<?> expHottimeTask = null;
   private static String hottime = "";
   private static int lastMonth = 0;
   public static boolean enableAuctionSave = true;
   private static AtomicInteger saveChrCount = new AtomicInteger(0);
   private static long lastAutoHottimeTime = 0L;
   private static long lastJuhunFeverTime = 0L;
   private static long lastAutoSaveTime = 0L;
   private static long lastCheckLoggedinTime = 0L;
   private static Map<Integer, Long> lastRespawnTime = new HashMap<>();
   private static ScheduledFuture<?> autoHottimeTask = null;
   private static ScheduledFuture<?> juhunHottimeTask = null;
   private static ScheduledFuture<?> autoSaveTask = null;
   private static ScheduledFuture<?> autoChargeTask = null;
   private static ScheduledFuture<?> autoNoticeTask = null;
   private static ScheduledFuture<?> autoFeverTask = null;
   private static ScheduledFuture<?> autoHottimeItemTask = null;
   private static ScheduledFuture<?> loggingSaveTask = null;
   private static Map<Integer, ScheduledFuture<?>> respawnTask = new HashMap<>();
   private static int lasthour = -1;
   private static int ccu = 0;
   private static int todayccu = 0;
   private static String todayBestCCUDate = "";
   private static long lastCheckAccountTime = 0L;
   private static Map<Integer, Integer> checkAccounts = new HashMap<>();

   public static void init() {
      Center.Find.findChannel(0);
      Center.Alliance.lock.toString();
      Center.Messenger.getMessenger(0);
      Center.Party.getParty(0);
   }

   public static String getStatus() {
      StringBuilder ret = new StringBuilder();
      int totalUsers = 0;

      for (GameServer cs : GameServer.getAllInstances()) {
         ret.append("Channel ");
         ret.append(cs.getChannel());
         ret.append(": ");
         int channelUsers = cs.getConnectedClients();
         totalUsers += channelUsers;
         ret.append(channelUsers);
         ret.append(" users\n");
      }

      ret.append("Total users online: ");
      ret.append(totalUsers);
      ret.append("\n");
      return ret.toString();
   }

   public static Map<Integer, Integer> getConnected() {
      Map<Integer, Integer> ret = new HashMap<>();
      int total = 0;

      for (GameServer cs : GameServer.getAllInstances()) {
         int curConnected = cs.getConnectedClients();
         ret.put(cs.getChannel(), curConnected);
         total += curConnected;
      }

      ret.put(0, total);
      return ret;
   }

   public static List<CheaterData> getCheaters() {
      List<CheaterData> allCheaters = new ArrayList<>();

      for (GameServer cs : GameServer.getAllInstances()) {
         allCheaters.addAll(cs.getCheaters());
      }

      Collections.sort(allCheaters);
      return CollectionUtil.copyFirst(allCheaters, 20);
   }

   public static List<CheaterData> getReports() {
      List<CheaterData> allCheaters = new ArrayList<>();

      for (GameServer cs : GameServer.getAllInstances()) {
         allCheaters.addAll(cs.getReports());
      }

      Collections.sort(allCheaters);
      return CollectionUtil.copyFirst(allCheaters, 20);
   }

   public static boolean isConnected(String charName) {
      return Center.Find.findChannel(charName) > 0;
   }

   public static void toggleMegaphoneMuteState() {
      for (GameServer cs : GameServer.getAllInstances()) {
         cs.toggleMegaphoneMuteState();
      }
   }

   public static void ChannelChange_Data(CharacterTransfer Data, int characterid, int toChannel) {
      getStorage(toChannel).registerPendingPlayer(Data, characterid);
   }

   public static String isCharacterListConnected(List<String> charName) {
      for (GameServer cs : GameServer.getAllInstances()) {
         for (Field map : cs.getMapFactory().getAllMaps()) {
            for (MapleCharacter chr : map.getCharactersThreadsafe()) {
               for (String c : charName) {
                  if (chr != null && chr.getName().equals(c)) {
                     return c;
                  }
               }
            }
         }
      }

      return null;
   }

   public static boolean hasMerchant(int accountID, int characterID) {
      for (GameServer cs : GameServer.getAllInstances()) {
         if (cs.containsMerchant(accountID, characterID)) {
            return true;
         }
      }

      return false;
   }

   public static PlayerStorage getStorage(int channel) {
      if (channel == -10) {
         return CashShopServer.getPlayerStorage();
      } else if (channel == -20) {
         return AuctionServer.getPlayerStorage();
      } else {
         return GameServer.getInstance(channel) == null ? null : GameServer.getInstance(channel).getPlayerStorage();
      }
   }

   public static int getPendingCharacterSize() {
      int ret = 0;

      for (GameServer cserv : GameServer.getAllInstances()) {
         ret += cserv.getPlayerStorage().pendingCharacterSize();
      }

      return ret;
   }

   public static boolean isChannelAvailable(int ch) {
      return GameServer.getInstance(ch) != null && GameServer.getInstance(ch).getPlayerStorage() != null
            ? GameServer.getInstance(ch).getPlayerStorage().getConnectedClients() < (ch == 1 ? 600 : 400)
            : false;
   }

   public static void registerLoadTop1Ranker() {
      updateTop1Ranker();
      Timer.WorldTimer.getInstance().register(new Runnable() {
         @Override
         public void run() {
            Center.updateTop1Ranker();
         }
      }, 43200000L);
   }

   public static void updateTop1Ranker() {
      DBConnection db = new DBConnection();

      try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                  "SELECT * FROM characters WHERE gm = 0 ORDER BY level, tsd_total_point DESC LIMIT 1");) {
         ResultSet rs = ps.executeQuery();
         if (rs.next()) {
            ServerConstants.top1Ranker = MapleCharacter.loadCharFromDB(rs.getInt("id"), null, false);
         }

         rs.close();
         ps.close();
      } catch (SQLException var9) {
         var9.printStackTrace();
      }

      System.out.println("The ranking #1 character for the leaderboard has been updated.");
   }

   public static void unregisterAutoSave() {
      if (autoSaveTask != null) {
         autoSaveTask.cancel(true);
         autoSaveTask = null;
         System.out.println("Auto-save thread is terminating.");
      } else {
         registerAutoSave();
      }
   }

   public static void registerAutoSave() {
      if (!ServerConstants.workingSave) {
         ServerConstants.workingSave = true;
         autoSaveTask = Timer.SaveTimer.getInstance().register(new Runnable() {
            @Override
            public void run() {
               int totalCheck = ServerConstants.totalSaveCount.get();
               if (totalCheck > 0) {
                  if (Center.lastAutoSaveTime - System.currentTimeMillis() <= 1800000L) {
                     return;
                  }

                  System.out.println("Auto-save has not run for over 30 minutes, restarting save process.");
                  ServerConstants.totalSaveCount.set(0);
                  ServerConstants.currentSaveCount.set(0);
               }

               Timer.SaveTimer.getInstance().schedule(new Runnable() {
                  @Override
                  public void run() {
                     Center.saveChrCount.set(0);
                     ServerConstants.totalSaveCount.addAndGet(1);
                     int chrCount = 0;
                     long startTime = System.currentTimeMillis();

                     try {
                        for (GameServer cs : GameServer.getAllInstances()) {
                           for (MapleCharacter chr : cs.getPlayerStorage().getAllCharacters()) {
                              if (chr != null) {
                                 if (chrCount++ % 10 == 0) {
                                    if (chr.getClient().getPlayer() == null) {
                                       if (chr.getClient().getPlayer() == null && chr.getMap() != null) {
                                          chr.getMap().removePlayer(chr);
                                       }
                                    } else if (chr.getClient().getSession().isOpen()) {
                                       chr.saveToDB(false, false);
                                       Center.saveChrCount.addAndGet(1);
                                    }
                                 } else {
                                    Timer.EtcTimer.getInstance().schedule(() -> {
                                       if (chr.getClient().getPlayer() == null) {
                                          if (chr.getClient().getPlayer() == null && chr.getMap() != null) {
                                             chr.getMap().removePlayer(chr);
                                          }
                                       } else {
                                          if (chr.getClient().getSession().isOpen()) {
                                             chr.saveToDB(false, false);
                                             Center.saveChrCount.addAndGet(1);
                                          }
                                       }
                                    }, 1L);
                                 }
                              }
                           }
                        }
                     } catch (Exception var8) {
                        System.out.println("Character save error occurred");
                        var8.printStackTrace();
                     }

                     long endTime = System.currentTimeMillis();
                     ServerConstants.currentSaveCount.addAndGet(1);
                     System.out.println("Total " + Center.saveChrCount.get() + " characters auto-saved. ["
                           + (endTime - startTime) + "ms]");
                     int total = ServerConstants.totalSaveCount.get();
                     int current = ServerConstants.currentSaveCount.get();
                     if (current == total && total > 1) {
                        if (!DBConfig.isHosting) {
                           System.out.println(current + " / " + total);
                        }

                        System.out.println("All saves completed.");
                        ServerConstants.totalSaveCount.set(0);
                        ServerConstants.currentSaveCount.set(0);
                        ServerConstants.workingSave = false;
                     }
                  }
               }, 0L);
               if (!DBConfig.isGanglim) {
                  Timer.SaveTimer.getInstance().schedule(new Runnable() {
                     @Override
                     public void run() {
                        ServerConstants.totalSaveCount.addAndGet(1);

                        try {
                           WeeklyItemManager.saveWeeklyItems();
                        } catch (Exception var3) {
                           System.out.println("Weekly item save error occurred");
                           var3.printStackTrace();
                        }

                        ServerConstants.currentSaveCount.addAndGet(1);
                        System.out.println("Weekly item save completed");
                        int total = ServerConstants.totalSaveCount.get();
                        int current = ServerConstants.currentSaveCount.get();
                        if (current == total && total > 1) {
                           if (!DBConfig.isHosting) {
                              System.out.println(current + " / " + total);
                           }

                           System.out.println("All saves completed.");
                           ServerConstants.totalSaveCount.set(0);
                           ServerConstants.currentSaveCount.set(0);
                           ServerConstants.workingSave = false;
                        }
                     }
                  }, 0L);
               }

               if (!DBConfig.isGanglim) {
                  Timer.SaveTimer.getInstance().schedule(new Runnable() {
                     @Override
                     public void run() {
                        ServerConstants.totalSaveCount.addAndGet(1);
                        long startTime = System.currentTimeMillis();

                        try {
                           Center.Guild.save();
                        } catch (Exception var7) {
                           System.out.println("Guild save error occurred");
                           var7.printStackTrace();
                        }

                        long endTime = System.currentTimeMillis();
                        ServerConstants.currentSaveCount.addAndGet(1);
                        System.out.println("Guild save completed [" + (endTime - startTime) + "ms]");
                        int total = ServerConstants.totalSaveCount.get();
                        int current = ServerConstants.currentSaveCount.get();
                        if (current == total && total > 1) {
                           if (!DBConfig.isHosting) {
                              System.out.println(current + " / " + total);
                           }

                           System.out.println("All saves completed.");
                           ServerConstants.totalSaveCount.set(0);
                           ServerConstants.currentSaveCount.set(0);
                           ServerConstants.workingSave = false;
                        }
                     }
                  }, 0L);
                  Timer.SaveTimer.getInstance().schedule(new Runnable() {
                     @Override
                     public void run() {
                        ServerConstants.totalSaveCount.addAndGet(1);
                        long startTime = System.currentTimeMillis();

                        try {
                           Center.Alliance.save();
                        } catch (Exception var7) {
                           System.out.println("Alliance save error occurred");
                           var7.printStackTrace();
                        }

                        long endTime = System.currentTimeMillis();
                        ServerConstants.currentSaveCount.addAndGet(1);
                        System.out.println("Alliance save completed [" + (endTime - startTime) + "ms]");
                        int total = ServerConstants.totalSaveCount.get();
                        int current = ServerConstants.currentSaveCount.get();
                        if (current == total && total > 1) {
                           if (!DBConfig.isHosting) {
                              System.out.println(current + " / " + total);
                           }

                           System.out.println("All saves completed.");
                           ServerConstants.totalSaveCount.set(0);
                           ServerConstants.currentSaveCount.set(0);
                           ServerConstants.workingSave = false;
                        }
                     }
                  }, 0L);
               } else {
                  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                  Calendar CAL = new GregorianCalendar(Locale.KOREA);
                  String fDate = sdf.format(CAL.getTime());
                  String[] dates = fDate.split("-");
                  int hours = Integer.parseInt(dates[3]);
                  int minutes = Integer.parseInt(dates[4]);
                  if (hours == 23 && minutes >= 45 || ServerConstants.royalGuildSave) {
                     Timer.SaveTimer.getInstance().schedule(new Runnable() {
                        @Override
                        public void run() {
                           ServerConstants.totalSaveCount.addAndGet(1);
                           long startTime = System.currentTimeMillis();

                           try {
                              Center.Guild.saveNoLock();
                           } catch (Exception var7) {
                              System.out.println("Guild save error occurred");
                              var7.printStackTrace();
                           }

                           long endTime = System.currentTimeMillis();
                           ServerConstants.currentSaveCount.addAndGet(1);
                           System.out.println("Guild save completed [" + (endTime - startTime) + "ms]");
                           int total = ServerConstants.totalSaveCount.get();
                           int current = ServerConstants.currentSaveCount.get();
                           if (current == total && total > 1) {
                              if (!DBConfig.isHosting) {
                                 System.out.println(current + " / " + total);
                              }

                              System.out.println("All saves completed.");
                              ServerConstants.totalSaveCount.set(0);
                              ServerConstants.currentSaveCount.set(0);
                              ServerConstants.workingSave = false;
                           }
                        }
                     }, 0L);
                     Timer.SaveTimer.getInstance().schedule(new Runnable() {
                        @Override
                        public void run() {
                           ServerConstants.totalSaveCount.addAndGet(1);
                           long startTime = System.currentTimeMillis();

                           try {
                              Center.Alliance.save();
                           } catch (Exception var7) {
                              System.out.println("Alliance save error occurred");
                              var7.printStackTrace();
                           }

                           long endTime = System.currentTimeMillis();
                           ServerConstants.currentSaveCount.addAndGet(1);
                           System.out.println("Alliance save completed [" + (endTime - startTime) + "ms]");
                           int total = ServerConstants.totalSaveCount.get();
                           int current = ServerConstants.currentSaveCount.get();
                           if (current == total && total > 1) {
                              if (!DBConfig.isHosting) {
                                 System.out.println(current + " / " + total);
                              }

                              System.out.println("All saves completed.");
                              ServerConstants.totalSaveCount.set(0);
                              ServerConstants.currentSaveCount.set(0);
                              ServerConstants.workingSave = false;
                           }
                        }
                     }, 0L);
                  }
               }

               Timer.SaveTimer.getInstance().schedule(new Runnable() {
                  @Override
                  public void run() {
                     ServerConstants.totalSaveCount.addAndGet(1);

                     try {
                        DojangRanking.saveRanks();
                     } catch (Exception var3) {
                        System.out.println("Mu Lung save error occurred");
                        var3.printStackTrace();
                     }

                     ServerConstants.currentSaveCount.addAndGet(1);
                     System.out.println("Mu Lung ranking save completed");
                     int total = ServerConstants.totalSaveCount.get();
                     int current = ServerConstants.currentSaveCount.get();
                     if (current == total && total > 1) {
                        if (!DBConfig.isHosting) {
                           System.out.println(current + " / " + total);
                        }

                        System.out.println("All saves completed.");
                        ServerConstants.totalSaveCount.set(0);
                        ServerConstants.currentSaveCount.set(0);
                        ServerConstants.workingSave = false;
                     }
                  }
               }, 0L);
               if (!DBConfig.isGanglim) {
                  Timer.SaveTimer.getInstance().schedule(new Runnable() {
                     @Override
                     public void run() {
                        ServerConstants.totalSaveCount.addAndGet(1);
                        PraisePointRank.loadRanks();
                        ServerConstants.currentSaveCount.addAndGet(1);
                        System.out.println("Praise point save completed");
                        int total = ServerConstants.totalSaveCount.get();
                        int current = ServerConstants.currentSaveCount.get();
                        if (current == total && total > 1) {
                           if (!DBConfig.isHosting) {
                              System.out.println(current + " / " + total);
                           }

                           System.out.println("All saves completed.");
                           ServerConstants.totalSaveCount.set(0);
                           ServerConstants.currentSaveCount.set(0);
                           ServerConstants.workingSave = false;
                        }
                     }
                  }, 0L);
               }

               if (Center.enableAuctionSave) {
                  System.out.println("Auction auto-save is running. Do not terminate until Auction save is finished.");
                  Timer.SaveTimer.getInstance().schedule(new Runnable() {
                     @Override
                     public void run() {
                        ServerConstants.totalSaveCount.addAndGet(1);
                        long startTime = System.currentTimeMillis();
                        Center.Auction.save();
                        long endTime = System.currentTimeMillis();
                        ServerConstants.currentSaveCount.addAndGet(1);
                        System.out.println("Auction save completed [" + (endTime - startTime) + "ms]");
                        int total = ServerConstants.totalSaveCount.get();
                        int current = ServerConstants.currentSaveCount.get();
                        if (current == total && total > 1) {
                           if (!DBConfig.isHosting) {
                              System.out.println(current + " / " + total);
                           }

                           System.out.println("All saves completed.");
                           ServerConstants.totalSaveCount.set(0);
                           ServerConstants.currentSaveCount.set(0);
                           ServerConstants.workingSave = false;
                        }
                     }
                  }, 0L);
               }

               if (ServerConstants.timeScheduleEntry.isChange()) {
                  ServerConstants.timeScheduleEntry.save();
                  ServerConstants.timeScheduleEntry.setChange(false);
               }

               if (!DBConfig.isGanglim) {
                  PraiseDonationMesoRank.saveRank();
                  PraiseDonationMeso.saveLogs();
               }

               Timer.SaveTimer.getInstance().schedule(new Runnable() {
                  @Override
                  public void run() {
                     for (GameServer cs : GameServer.getAllInstances()) {
                        for (MapleCharacter player : cs.getPlayerStorage().getAllCharacters()) {
                           if (player != null && player.getName() != null) {
                              long lastCheckTime = player.getLastSpeedHackCheckTime();
                              long delta = System.currentTimeMillis() - lastCheckTime;
                              if (lastCheckTime != 0L && delta > 120000L) {
                                 for (GameServer gs : GameServer.getAllInstances()) {
                                    for (Field field : gs.getMapFactory().getAllMaps()) {
                                       if (field != null && field.getCharacterById(player.getId()) != null) {
                                          player.getClient().disconnect(false, field, false);
                                       }
                                    }
                                 }

                                 GameServer.getInstance(player.getClient().getChannel()).getPlayerStorage()
                                       .deregisterPlayer(player);
                                 AdminClient.updatePlayerList();
                              }
                           }
                        }
                     }

                     for (MapleCharacter playerx : AuctionServer.getPlayerStorage().getAllCharacters()) {
                        if (playerx != null) {
                           long lastCheckTime = playerx.getLastSpeedHackCheckTime();
                           long delta = System.currentTimeMillis() - lastCheckTime;
                           if (lastCheckTime != 0L && delta > 120000L) {
                              playerx.getClient().disconnect(false, null, false);
                              AuctionServer.getPlayerStorage().deregisterPlayer(playerx);
                              AdminClient.updatePlayerList();
                           }
                        }
                     }

                     for (MapleCharacter playerxx : CashShopServer.getPlayerStorage().getAllCharacters()) {
                        if (playerxx != null) {
                           long lastCheckTime = playerxx.getLastSpeedHackCheckTime();
                           long delta = System.currentTimeMillis() - lastCheckTime;
                           if (lastCheckTime != 0L && delta > 120000L) {
                              playerxx.getClient().disconnect(false, null, false);
                              CashShopServer.getPlayerStorage().deregisterPlayer(playerxx);
                              AdminClient.updatePlayerList();
                           }
                        }
                     }
                  }
               }, 0L);
               System.out.println("Auto-save thread running");
               Center.lastAutoSaveTime = System.currentTimeMillis();
            }
         }, 600000L);
      }
   }

   public static void gainItemExpiration(int itemID, int periodHour, short quantity) {
      for (GameServer cs : GameServer.getAllInstances()) {
         for (Field map : cs.getMapFactory().getAllMaps()) {
            for (MapleCharacter chr : new ArrayList<>(map.getCharacters())) {
               if (chr != null && (chr.getClient().getSession().isActive() || chr.getClient().getSession().isOpen())) {
                  Item item = null;
                  MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
                  if (itemID / 1000000 == 1) {
                     item = ii.randomizeStats((Equip) ii.getEquipById(itemID));
                  } else {
                     item = new Item(itemID, (short) 0, quantity, 0, MapleInventoryIdentifier.getInstance());
                  }

                  MapleCabinet cabinet = chr.getCabinet();
                  if (cabinet != null) {
                     SimpleDateFormat sdf = new SimpleDateFormat("yyyy๋… MM์” dd์ผ HH์ mm๋ถ");
                     Calendar CAL = new GregorianCalendar(Locale.KOREA);
                     String fDate = sdf.format(CAL.getTime());
                     cabinet.addCabinetItem(
                           new MapleCabinetItem(cabinet.getNextIndex(), System.currentTimeMillis() + 259200000L,
                                 "[Hottime Reward]", fDate + " Hottime reward distributed.", item));
                     chr.send(CField.maplecabinetResult(8));
                     chr.setSaveFlag(chr.getSaveFlag() | CharacterSaveFlag.CABINET.getFlag());
                     if (DBConfig.isGanglim) {
                        chr.send(CField.chatMsg(1,
                              "[Ganglim Hottime] เนเธเธเธฃเธฒเธเธงเธฑเธฅ Hottime เนเธฅเนเธง\r\nเธฃเธฑเธเนเธ”เนเธ—เธตเน [Maple Cabinet]"));
                        chr.dropMessage(1, "Hottime reward distributed. Claim from [Maple Cabinet].");
                     } else {
                        chr.dropMessage(5, "[Notice] เนเธเธเธฃเธฒเธเธงเธฑเธฅ Hottime เนเธฅเนเธง เธเธฃเธธเธ“เธฒเธ•เธฃเธงเธเธชเธญเธ [Maple Cabinet]");
                        chr.dropMessage(1, "Hottime reward distributed.\r\nPlease check [Maple Cabinet].");
                     }
                  }
               }
            }
         }
      }

      System.out.println(MapleItemInformationProvider.getInstance().getName(itemID) + "์ด(๊ฐ€) ํ•ซํ€์์ผ๋ก ์ง€๊ธ๋์—์ต๋๋ค.");
   }

   public static List<Integer> getStackRewards(long beforeTotalPoint, int point) {
      List<Integer> ret = new ArrayList<>();
      int[] rewards = new int[] {
            10,
            30,
            50,
            75,
            100,
            150,
            200,
            250,
            300,
            350,
            400,
            450,
            500,
            550,
            600,
            650,
            700,
            750,
            800,
            850,
            900,
            950,
            1000,
            1050,
            1100,
            1150,
            1200,
            1250,
            1300,
            1350,
            1400,
            1450,
            1500,
            1550,
            1600,
            1650,
            1700,
            1750,
            1800,
            1850,
            1900,
            1950,
            2000,
            2100,
            2200,
            2300,
            2400,
            2500,
            2600,
            2700,
            2800,
            2900,
            3000,
            3100,
            3200,
            3300,
            3400,
            3500,
            3600,
            3700,
            3800,
            3900,
            4000,
            4100,
            4200,
            4300,
            4400,
            4500,
            4600,
            4700,
            4800,
            4900,
            5000
      };
      if (DBConfig.isGanglim) {
         rewards = new int[] {
               10,
               30,
               50,
               75,
               100,
               150,
               200,
               250,
               300,
               350,
               400,
               450,
               500,
               600,
               750,
               900,
               1000,
               1100,
               1250,
               1400,
               1500,
               1600,
               1750,
               1900,
               2000,
               2100,
               2200,
               2300,
               2400,
               2500
         };
      }

      for (int reward : rewards) {
         if (reward * 10000 > beforeTotalPoint) {
            if (reward * 10000 > beforeTotalPoint + point) {
               break;
            }

            ret.add(reward);
         }
      }

      return ret;
   }

   public static boolean doCharge(String type, String accountName, int point) {
      long totalDonation = 0L;

      try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM `donation_log` WHERE `account` = ?");) {
         ps.setString(1, accountName);

         try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
               String sPrice = rs.getString("price").replace(",", "");
               long price = Long.parseLong(sPrice);
               totalDonation += price;
            }
         }
      } catch (Exception var99) {
         System.out.println("Error calculating recharge total");
         var99.printStackTrace();
         return false;
      }

      boolean first = false;
      int account_id = 0;
      String player_name = "";
      int player_id = 0;
      if (!type.equals("์ผ๋ฐ") && !type.equals("์ด๋ฒคํธ ์ฐธ์—ฌ") && !type.equals("์ ๋… ์ด๋ฒคํธ") && !type.equals("ํด์ค๋ง์ค ์ด๋ฒคํธ")
            && !type.equals("๋ณด๋์ค์ด๋ฒคํธ")) {
         if (type.equals("์ด์ฌ์ ํจํค์ง€")
               || type.contains("์ค๋ ")
               || type.contains("์–ด๋ฆฐ์ด๋ ")
               || type.contains("์ถ”์")
               || type.contains("๊ฐ€์ •์๋ฌ")
               || type.contains("3์ฃผ๋…")
               || type.contains("์์ํจํค์ง€")
               || type.contains("ํฌ๋ฆฌ์ค๋ง์ค")
               || type.contains("2023")
               || type.contains("5์”")) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            int id = 0;

            try (Connection con = DBConnection.getConnection()) {
               ps = con.prepareStatement("SELECT `id` FROM `accounts` WHERE `name` = ?");
               ps.setString(1, accountName);
               rs = ps.executeQuery();

               boolean findChar;
               for (findChar = false; rs.next(); findChar = true) {
                  id = rs.getInt("id");
                  account_id = id;
               }

               if (findChar) {
                  if (id == 0) {
                     System.out.println(
                           "[ERROR] Account not found, auto-charge failed. (Account : " + accountName + ", Amount : "
                                 + point + ")");
                     return false;
                  }

                  if (type.equals("์ด์ฌ์ ํจํค์ง€")) {
                     PreparedStatement ps2 = con
                           .prepareStatement("INSERT INTO `beginner_package` (`accountid`, `name`) VALUES (?, ?)");
                     ps2.setInt(1, id);
                     ps2.setString(2, null);
                     ps2.executeUpdate();
                     ps2.close();
                     ps.close();
                  }

                  if (type.contains("๊ฐ€์ •์๋ฌ")
                        || type.contains("์ถ”์")
                        || type.contains("3์ฃผ๋…")
                        || type.contains("์์ํจํค์ง€")
                        || type.contains("ํฌ๋ฆฌ์ค๋ง์ค")
                        || type.contains("2023")
                        || type.contains("5์”")) {
                     if (!DBConfig.isGanglim) {
                        Center.sunShineStorage.addSunShineGuage((int) (point * 0.2));
                        Center.sunShineStorage.save();
                     }

                     List<Integer> rewards = getStackRewards(totalDonation, point);
                     if (!DBConfig.isGanglim) {
                        for (int reward : rewards) {
                           ps = con.prepareStatement("INSERT INTO extreme_point_log (accountid, type) VALUES(?, ?)");
                           ps.setInt(1, id);
                           ps.setString(2, String.valueOf(reward));
                           ps.executeUpdate();
                           ps.close();
                        }
                     }
                  }

                  String typeName = "์ด์ฌ์ํจํค์ง€";
                  String price = "10,000";
                  if (type.contains("์ค๋ ")) {
                     typeName = type;
                     if (type.equals("์ค๋ C")) {
                        price = "50,000";
                     } else {
                        price = "100,000";
                     }
                  } else if (type.contains("์–ด๋ฆฐ์ด๋ ")) {
                     typeName = type;
                     price = "55,000";
                  } else if (type.contains("๊ฐ€์ •์๋ฌ")) {
                     typeName = type;
                     if (type.equals("๊ฐ€์ •์๋ฌC")) {
                        price = "50,000";
                     } else if (type.equals("๊ฐ€์ •์๋ฌB")) {
                        price = "100,000";
                     } else if (type.equals("๊ฐ€์ •์๋ฌA")) {
                        price = "300,000";
                     } else if (type.equals("๊ฐ€์ •์๋ฌS")) {
                        price = "500,000";
                     } else if (type.equals("๊ฐ€์ •์๋ฌSS")) {
                        price = "1,000,000";
                     } else if (type.equals("๊ฐ€์ •์๋ฌSSS")) {
                        price = "1,500,000";
                     }
                  } else if (type.contains("์ถ”์")) {
                     typeName = type;
                     if (type.equals("์ถ”์ํจํค์ง€I")) {
                        price = "10,000";
                     } else if (type.equals("์ถ”์ํจํค์ง€II")) {
                        price = "25,000";
                     } else if (type.equals("์ถ”์ํจํค์ง€III")) {
                        price = "75,000";
                     } else if (type.equals("์ถ”์ํจํค์ง€IV")) {
                        price = "110,000";
                     }
                  } else if (type.contains("3์ฃผ๋…")) {
                     typeName = type;
                     if (type.equals("3์ฃผ๋…ํจํค์ง€I")) {
                        price = "55,000";
                     } else if (type.equals("3์ฃผ๋…ํจํค์ง€II")) {
                        price = "55,000";
                     } else if (type.equals("3์ฃผ๋…ํจํค์ง€III")) {
                        price = "110,000";
                     }
                  } else if (type.contains("ํฌ๋ฆฌ์ค๋ง์ค")) {
                     typeName = type;
                     if (type.equals("ํฌ๋ฆฌ์ค๋ง์คํจํค์ง€1")) {
                        price = "30,000";
                     } else if (type.equals("ํฌ๋ฆฌ์ค๋ง์คํจํค์ง€2")) {
                        price = "50,000";
                     } else if (type.equals("ํฌ๋ฆฌ์ค๋ง์คํจํค์ง€3")) {
                        price = "50,000";
                     } else if (type.equals("ํฌ๋ฆฌ์ค๋ง์คํจํค์ง€4")) {
                        price = "110,000";
                     }
                  } else if (type.contains("2023")) {
                     typeName = type;
                     if (type.equals("2023ํจํค์ง€1")) {
                        price = "30,000";
                     } else if (type.equals("2023ํจํค์ง€2")) {
                        price = "50,000";
                     } else if (type.equals("2023ํจํค์ง€3")) {
                        price = "50,000";
                     } else if (type.equals("2023ํจํค์ง€4")) {
                        price = "50,000";
                     } else if (type.equals("2023ํจํค์ง€5")) {
                        price = "110,000";
                     } else if (type.equals("2023ํจํค์ง€6")) {
                        price = "50,000";
                     } else if (type.equals("2023ํจํค์ง€7")) {
                        price = "100,000";
                     } else if (type.equals("2023ํจํค์ง€8")) {
                        price = "300,000";
                     } else if (type.equals("2023ํจํค์ง€9")) {
                        price = "500,000";
                     } else if (type.equals("2023ํจํค์ง€10")) {
                        price = "1,000,000";
                     } else if (type.equals("2023ํจํค์ง€11")) {
                        price = "1,500,000";
                     }
                  } else if (type.contains("5์”")) {
                     typeName = type;
                     if (type.equals("5์”ํจํค์ง€1")) {
                        price = "30,000";
                     } else if (type.equals("5์”ํจํค์ง€2")) {
                        price = "50,000";
                     } else if (type.equals("5์”ํจํค์ง€3")) {
                        price = "50,000";
                     } else if (type.equals("5์”ํจํค์ง€4")) {
                        price = "50,000";
                     } else if (type.equals("5์”ํจํค์ง€5")) {
                        price = "110,000";
                     } else if (type.equals("5์”ํจํค์ง€6")) {
                        price = "50,000";
                     } else if (type.equals("5์”ํจํค์ง€7")) {
                        price = "100,000";
                     } else if (type.equals("5์”ํจํค์ง€8")) {
                        price = "300,000";
                     } else if (type.equals("5์”ํจํค์ง€9")) {
                        price = "500,000";
                     } else if (type.equals("5์”ํจํค์ง€10")) {
                        price = "1,000,000";
                     } else if (type.equals("5์”ํจํค์ง€11")) {
                        price = "1,500,000";
                     }
                  } else if (type.contains("์์ํจํค์ง€")) {
                     typeName = type;
                     price = "500,000";
                     if (type.equals("์์ํจํค์ง€2")) {
                        price = "1,000,000";
                     }
                  }

                  ps = con.prepareStatement(
                        "INSERT INTO `donation_log` (`date`, `account`, `name`, `price`) VALUES (?, ?, ?, ?)");
                  SimpleDateFormat sdf = new SimpleDateFormat("yyyy. M. d");
                  String fDate = sdf.format(System.currentTimeMillis());
                  ps.setString(1, fDate);
                  ps.setString(2, accountName);
                  ps.setString(3, typeName);
                  ps.setString(4, price);
                  ps.executeQuery();
                  rs.close();
                  ps.close();
                  ps = con.prepareStatement("SELECT `name` FROM `characters` WHERE `accountid` = ?");
                  ps.setInt(1, id);
                  rs = ps.executeQuery();
                  boolean found = false;

                  while (rs.next()) {
                     String name = rs.getString("name");

                     for (GameServer cs : GameServer.getAllInstances()) {
                        for (MapleCharacter chr : cs.getPlayerStorage().getAllCharacters()) {
                           if (chr.getName().equals(name)) {
                              if (type.equals("์ด์ฌ์ ํจํค์ง€")) {
                                 chr.dropMessage(5,
                                       "Beginner Package has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Beginner Package has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                              } else if (type.equals("์ค๋ A")) {
                                 chr.dropMessage(5,
                                       "Lunar New Year Enhancement Package has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Lunar New Year Enhancement Package has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "nyd_package1", "1");
                              } else if (type.equals("์ค๋ B")) {
                                 chr.dropMessage(5,
                                       "Lunar New Year Master Berry Package has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Lunar New Year Master Berry Package has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "nyd_package2", "1");
                              } else if (type.equals("์ค๋ C")) {
                                 chr.dropMessage(5,
                                       "Lunar New Year Value Package has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Lunar New Year Value Package has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "nyd_package3", "1");
                              } else if (type.equals("์–ด๋ฆฐ์ด๋ ")) {
                                 chr.dropMessage(5,
                                       "Children's Day Package has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Children's Day Package has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1235999, "cd_package",
                                       String.valueOf(chr.getOneInfoQuestInteger(1235999, "cd_package") + 1));
                              } else if (type.equals("๊ฐ€์ •์๋ฌC")) {
                                 chr.dropMessage(5,
                                       "Family Month Package C has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Family Month Package C has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "may_package_1", "1");
                              } else if (type.equals("๊ฐ€์ •์๋ฌB")) {
                                 chr.dropMessage(5,
                                       "Family Month Package B has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Family Month Package B has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "may_package_2", "1");
                              } else if (type.equals("๊ฐ€์ •์๋ฌA")) {
                                 chr.dropMessage(5,
                                       "Family Month Package A has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Family Month Package A has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "may_package_3", "1");
                              } else if (type.equals("๊ฐ€์ •์๋ฌS")) {
                                 chr.dropMessage(5,
                                       "Family Month Package S has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Family Month Package S has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "may_package_4", "1");
                              } else if (type.equals("๊ฐ€์ •์๋ฌSS")) {
                                 chr.dropMessage(5,
                                       "Family Month Package SS has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Family Month Package SS has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "may_package_5", "1");
                              } else if (type.equals("๊ฐ€์ •์๋ฌSSS")) {
                                 chr.dropMessage(5,
                                       "Family Month Package SSS has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Family Month Package SSS has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "may_package_6", "1");
                              } else if (type.equals("์์ํจํค์ง€1")) {
                                 chr.dropMessage(5,
                                       "Sealed Stat Box Package has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Sealed Stat Box Package has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1236001, "a_package_1", "1");
                              } else if (type.equals("์์ํจํค์ง€2")) {
                                 chr.dropMessage(5,
                                       "Medal Option Enhancement Ticket Package has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Medal Option Enhancement Ticket Package has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1236001, "a_package_2", "1");
                              } else if (type.equals("์ถ”์ํจํค์ง€I")) {
                                 chr.dropMessage(5,
                                       "Chuseok Package I has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Chuseok Package I has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "chuseok_package_1", "1");
                              } else if (type.equals("์ถ”์ํจํค์ง€II")) {
                                 chr.dropMessage(5,
                                       "Chuseok Package II has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Chuseok Package II has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "chuseok_package_2", "1");
                              } else if (type.equals("์ถ”์ํจํค์ง€III")) {
                                 chr.dropMessage(5,
                                       "Chuseok Package III has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Chuseok Package III has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "chuseok_package_3", "1");
                              } else if (type.equals("์ถ”์ํจํค์ง€IV")) {
                                 chr.dropMessage(5,
                                       "Chuseok Package IV has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Chuseok Package IV has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "chuseok_package_4", "1");
                              } else if (type.equals("์ถ”์ํจํค์ง€IV")) {
                                 chr.dropMessage(5,
                                       "Chuseok Package IV has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Chuseok Package IV has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "chuseok_package_4", "1");
                              } else if (type.equals("3์ฃผ๋…ํจํค์ง€I")) {
                                 chr.dropMessage(5,
                                       "Ganglim Amazing Label Package has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Ganglim Amazing Label Package has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "3aniv_package_1", "1");
                              } else if (type.equals("3์ฃผ๋…ํจํค์ง€II")) {
                                 chr.dropMessage(5,
                                       "Ganglim Wisp Enhancement Package has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Ganglim Wisp Enhancement Package has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "3aniv_package_2", "1");
                              } else if (type.equals("3์ฃผ๋…ํจํค์ง€III")) {
                                 chr.dropMessage(5,
                                       "Ganglim Remaster Package has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Ganglim Remaster Package has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "3aniv_package_3", "1");
                              } else if (type.equals("ํฌ๋ฆฌ์ค๋ง์คํจํค์ง€1")) {
                                 chr.dropMessage(5,
                                       "Merry Package has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Merry Package has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "xmas_package_1", "1");
                              } else if (type.equals("ํฌ๋ฆฌ์ค๋ง์คํจํค์ง€2")) {
                                 chr.dropMessage(5,
                                       "Chris Package has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Chris Package has been distributed. Please claim it from the Cash Shop.", ""));
                                 chr.updateOneInfo(1234579, "xmas_package_2", "1");
                              } else if (type.equals("ํฌ๋ฆฌ์ค๋ง์คํจํค์ง€3")) {
                                 chr.dropMessage(5,
                                       "Mas Package has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Mas Package has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "xmas_package_3", "1");
                              } else if (type.equals("ํฌ๋ฆฌ์ค๋ง์คํจํค์ง€4")) {
                                 chr.dropMessage(5,
                                       "Ganglim Christmas Package has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Ganglim Christmas Package has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "xmas_package_4", "1");
                              } else if (type.equals("2023ํจํค์ง€1")) {
                                 chr.dropMessage(5,
                                       "Hop hop to 300 Package has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Hop hop to 300 Package has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "2023_package_1", "1");
                              } else if (type.equals("2023ํจํค์ง€2")) {
                                 chr.dropMessage(5,
                                       "Strong Rabbit Package has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Strong Rabbit Package has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "2023_package_2", "1");
                              } else if (type.equals("2023ํจํค์ง€3")) {
                                 chr.dropMessage(5,
                                       "Rabbit Outfit Package has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Rabbit Outfit Package has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "2023_package_3", "1");
                              } else if (type.equals("2023ํจํค์ง€4")) {
                                 chr.dropMessage(5,
                                       "Cute Rabbit Pet Package has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Cute Rabbit Pet Package has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "2023_package_4", "1");
                              } else if (type.equals("2023ํจํค์ง€5")) {
                                 chr.dropMessage(5,
                                       "Savior Package has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Savior Package has been distributed. Please claim it from the Cash Shop.", ""));
                                 chr.updateOneInfo(1234579, "2023_package_5", "1");
                              } else if (type.equals("2023ํจํค์ง€6")) {
                                 chr.dropMessage(5,
                                       "Year of the Rabbit Package C has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Year of the Rabbit Package C has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "2023_package_6", "1");
                              } else if (type.equals("2023ํจํค์ง€7")) {
                                 chr.dropMessage(5,
                                       "Year of the Rabbit Package B has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Year of the Rabbit Package B has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "2023_package_7", "1");
                              } else if (type.equals("2023ํจํค์ง€8")) {
                                 chr.dropMessage(5,
                                       "Year of the Rabbit Package A has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Year of the Rabbit Package A has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "2023_package_8", "1");
                              } else if (type.equals("2023ํจํค์ง€9")) {
                                 chr.dropMessage(5,
                                       "Year of the Rabbit Package S has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Year of the Rabbit Package S has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "2023_package_9", "1");
                              } else if (type.equals("2023ํจํค์ง€10")) {
                                 chr.dropMessage(5,
                                       "Year of the Rabbit Package SS has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Year of the Rabbit Package SS has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "2023_package_10", "1");
                              } else if (type.equals("2023ํจํค์ง€11")) {
                                 chr.dropMessage(5,
                                       "Year of the Rabbit Package SSS has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Year of the Rabbit Package SSS has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "2023_package_11", "1");
                              } else if (type.equals("5์”ํจํค์ง€1")) {
                                 chr.dropMessage(5,
                                       "Love You 3000 Package has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Love You 3000 Package has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "2023_may_package_1", "1");
                              } else if (type.equals("5์”ํจํค์ง€2")) {
                                 chr.dropMessage(5,
                                       "Power Overwhelming Package has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Power Overwhelming Package has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "2023_may_package_2", "1");
                              } else if (type.equals("5์”ํจํค์ง€3")) {
                                 chr.dropMessage(5,
                                       "M@STERPIECE Package has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "M@STERPIECE Package has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "2023_may_package_3", "1");
                              } else if (type.equals("5์”ํจํค์ง€4")) {
                                 chr.dropMessage(5,
                                       "Life Turnaround? Package has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Life Turnaround? Package has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "2023_may_package_4", "1");
                              } else if (type.equals("5์”ํจํค์ง€5")) {
                                 chr.dropMessage(5,
                                       "Eris's Golden Apple Package has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Eris's Golden Apple Package has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "2023_may_package_5", "1");
                              } else if (type.equals("5์”ํจํค์ง€6")) {
                                 chr.dropMessage(5,
                                       "Family Month Package C has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Family Month Package C has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "2023_may_package_6", "1");
                              } else if (type.equals("5์”ํจํค์ง€7")) {
                                 chr.dropMessage(5,
                                       "Family Month Package B has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Family Month Package B has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "2023_may_package_7", "1");
                              } else if (type.equals("5์”ํจํค์ง€8")) {
                                 chr.dropMessage(5,
                                       "Family Month Package A has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Family Month Package A has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "2023_may_package_8", "1");
                              } else if (type.equals("5์”ํจํค์ง€9")) {
                                 chr.dropMessage(5,
                                       "Family Month Package S has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Family Month Package S has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "2023_may_package_9", "1");
                              } else if (type.equals("5์”ํจํค์ง€10")) {
                                 chr.dropMessage(5,
                                       "Family Month Package SS has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Family Month Package SS has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "2023_may_package_10", "1");
                              } else if (type.equals("5์”ํจํค์ง€11")) {
                                 chr.dropMessage(5,
                                       "Family Month Package SSS has been distributed. Please claim it from the Cash Shop.");
                                 chr.send(CField.addPopupSay(9062000, 3000,
                                       "Family Month Package SSS has been distributed. Please claim it from the Cash Shop.",
                                       ""));
                                 chr.updateOneInfo(1234579, "2023_may_package_11", "1");
                              }

                              found = true;
                              break;
                           }
                        }
                     }

                     if (!found) {
                        for (MapleCharacter chrx : CashShopServer.getPlayerStorage().getAllCharacters()) {
                           if (chrx.getName().equals(name)) {
                              player_name = name;
                              if (type.equals("์ด์ฌ์ ํจํค์ง€")) {
                                 chrx.dropMessage(5, "เนเธ”เนเธฃเธฑเธ Beginner Package เน€เธฃเธตเธขเธเธฃเนเธญเธขเนเธฅเนเธง เธเธฃเธธเธ“เธฒเธฃเธฑเธเธ—เธตเนเธฃเธฐเธเธเธฃเนเธฒเธเธเนเธฒ");
                              } else if (type.equals("์ค๋ A")) {
                                 chrx.dropMessage(5, "เนเธ”เนเธฃเธฑเธ Lunar New Year Enhancement Package เน€เธฃเธตเธขเธเธฃเนเธญเธขเนเธฅเนเธง เธเธฃเธธเธ“เธฒเธฃเธฑเธเธ—เธตเนเธฃเธฐเธเธเธฃเนเธฒเธเธเนเธฒ");
                                 chrx.updateOneInfo(1234579, "nyd_package1", "1");
                              } else if (type.equals("์ค๋ B")) {
                                 chrx.dropMessage(5, "เนเธ”เนเธฃเธฑเธ Lunar New Year Master Berry Package เน€เธฃเธตเธขเธเธฃเนเธญเธขเนเธฅเนเธง เธเธฃเธธเธ“เธฒเธฃเธฑเธเธ—เธตเนเธฃเธฐเธเธเธฃเนเธฒเธเธเนเธฒ");
                                 chrx.updateOneInfo(1234579, "nyd_package2", "1");
                              } else if (type.equals("์ค๋ C")) {
                                 chrx.dropMessage(5, "เนเธ”เนเธฃเธฑเธ Lunar New Year Value Package เน€เธฃเธตเธขเธเธฃเนเธญเธขเนเธฅเนเธง เธเธฃเธธเธ“เธฒเธฃเธฑเธเธ—เธตเนเธฃเธฐเธเธเธฃเนเธฒเธเธเนเธฒ");
                                 chrx.updateOneInfo(1234579, "nyd_package3", "1");
                              } else if (type.equals("์–ด๋ฆฐ์ด๋ ")) {
                                 chrx.dropMessage(5, "เนเธ”เนเธฃเธฑเธ Children's Day Package เน€เธฃเธตเธขเธเธฃเนเธญเธขเนเธฅเนเธง เธเธฃเธธเธ“เธฒเธฃเธฑเธเธ—เธตเนเธฃเธฐเธเธเธฃเนเธฒเธเธเนเธฒ");
                                 chrx.updateOneInfo(1235999, "cd_package",
                                       String.valueOf(chrx.getOneInfoQuestInteger(1235999, "cd_package") + 1));
                              } else if (type.equals("๊ฐ€์ •์๋ฌC")) {
                                 chrx.dropMessage(5, "เนเธ”เนเธฃเธฑเธ Family Month Package C เน€เธฃเธตเธขเธเธฃเนเธญเธขเนเธฅเนเธง เธเธฃเธธเธ“เธฒเธฃเธฑเธเธ—เธตเนเธฃเธฐเธเธเธฃเนเธฒเธเธเนเธฒ");
                                 chrx.updateOneInfo(1234579, "may_package_1", "1");
                              } else if (type.equals("๊ฐ€์ •์๋ฌB")) {
                                 chrx.dropMessage(5, "เนเธ”เนเธฃเธฑเธ Family Month Package B เน€เธฃเธตเธขเธเธฃเนเธญเธขเนเธฅเนเธง เธเธฃเธธเธ“เธฒเธฃเธฑเธเธ—เธตเนเธฃเธฐเธเธเธฃเนเธฒเธเธเนเธฒ");
                                 chrx.updateOneInfo(1234579, "may_package_2", "1");
                              } else if (type.equals("๊ฐ€์ •์๋ฌA")) {
                                 chrx.dropMessage(5, "เนเธ”เนเธฃเธฑเธ Family Month Package A เน€เธฃเธตเธขเธเธฃเนเธญเธขเนเธฅเนเธง เธเธฃเธธเธ“เธฒเธฃเธฑเธเธ—เธตเนเธฃเธฐเธเธเธฃเนเธฒเธเธเนเธฒ");
                                 chrx.updateOneInfo(1234579, "may_package_3", "1");
                              } else if (type.equals("๊ฐ€์ •์๋ฌS")) {
                                 chrx.dropMessage(5, "เนเธ”เนเธฃเธฑเธ Family Month Package S เน€เธฃเธตเธขเธเธฃเนเธญเธขเนเธฅเนเธง เธเธฃเธธเธ“เธฒเธฃเธฑเธเธ—เธตเนเธฃเธฐเธเธเธฃเนเธฒเธเธเนเธฒ");
                                 chrx.updateOneInfo(1234579, "may_package_4", "1");
                              } else if (type.equals("๊ฐ€์ •์๋ฌSS")) {
                                 chrx.dropMessage(5, "เนเธ”เนเธฃเธฑเธ Family Month Package SS เน€เธฃเธตเธขเธเธฃเนเธญเธขเนเธฅเนเธง เธเธฃเธธเธ“เธฒเธฃเธฑเธเธ—เธตเนเธฃเธฐเธเธเธฃเนเธฒเธเธเนเธฒ");
                                 chrx.updateOneInfo(1234579, "may_package_5", "1");
                              } else if (type.equals("๊ฐ€์ •์๋ฌSSS")) {
                                 chrx.dropMessage(5, "เนเธ”เนเธฃเธฑเธ Family Month Package SSS เน€เธฃเธตเธขเธเธฃเนเธญเธขเนเธฅเนเธง เธเธฃเธธเธ“เธฒเธฃเธฑเธเธ—เธตเนเธฃเธฐเธเธเธฃเนเธฒเธเธเนเธฒ");
                                 chrx.updateOneInfo(1234579, "may_package_6", "1");
                              } else if (type.equals("์์ํจํค์ง€1")) {
                                 chrx.dropMessage(5,
                                       "Sealed Stat Box Package has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1236001, "a_package_1", "1");
                              } else if (type.equals("์์ํจํค์ง€2")) {
                                 chrx.dropMessage(5,
                                       "Medal Option Enhancement Ticket Package has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1236001, "a_package_2", "1");
                              } else if (type.equals("์ถ”์ํจํค์ง€I")) {
                                 chrx.dropMessage(5,
                                       "Chuseok Package I has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "chuseok_package_1", "1");
                              } else if (type.equals("์ถ”์ํจํค์ง€II")) {
                                 chrx.dropMessage(5,
                                       "Chuseok Package II has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "chuseok_package_2", "1");
                              } else if (type.equals("์ถ”์ํจํค์ง€III")) {
                                 chrx.dropMessage(5,
                                       "Chuseok Package III has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "chuseok_package_3", "1");
                              } else if (type.equals("์ถ”์ํจํค์ง€IV")) {
                                 chrx.dropMessage(5,
                                       "Chuseok Package IV has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "chuseok_package_4", "1");
                              } else if (type.equals("3์ฃผ๋…ํจํค์ง€I")) {
                                 chrx.dropMessage(5,
                                       "Advent Amazing Label Package has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "3aniv_package_1", "1");
                              } else if (type.equals("3์ฃผ๋…ํจํค์ง€II")) {
                                 chrx.dropMessage(5,
                                       "Advent Wisp's Enhancement Package has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "3aniv_package_2", "1");
                              } else if (type.equals("3์ฃผ๋…ํจํค์ง€III")) {
                                 chrx.dropMessage(5,
                                       "Advent Remaster Package has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "3aniv_package_3", "1");
                              } else if (type.equals("ํฌ๋ฆฌ์ค๋ง์คํจํค์ง€1")) {
                                 chrx.dropMessage(5,
                                       "Merry Package has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "xmas_package_1", "1");
                              } else if (type.equals("ํฌ๋ฆฌ์ค๋ง์คํจํค์ง€2")) {
                                 chrx.dropMessage(5,
                                       "Chris Package has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "xmas_package_2", "1");
                              } else if (type.equals("ํฌ๋ฆฌ์ค๋ง์คํจํค์ง€3")) {
                                 chrx.dropMessage(5,
                                       "Mas Package has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "xmas_package_3", "1");
                              } else if (type.equals("ํฌ๋ฆฌ์ค๋ง์คํจํค์ง€4")) {
                                 chrx.dropMessage(5,
                                       "Advent Christmas Package has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "xmas_package_4", "1");
                              } else if (type.equals("2023ํจํค์ง€1")) {
                                 chrx.dropMessage(5,
                                       "Hopping to 300? Package has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "2023_package_1", "1");
                              } else if (type.equals("2023ํจํค์ง€2")) {
                                 chrx.dropMessage(5,
                                       "Even Rabbits Need Strength to Catch! Package has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "2023_package_2", "1");
                              } else if (type.equals("2023ํจํค์ง€3")) {
                                 chrx.dropMessage(5,
                                       "เนเธ”เนเธฃเธฑเธ What Do Rabbits Wear? Package เนเธฅเนเธง เธเธฃเธธเธ“เธฒเธฃเธฑเธเธ—เธตเน Cash Shop");
                                 chrx.updateOneInfo(1234579, "2023_package_3", "1");
                              } else if (type.equals("2023ํจํค์ง€4")) {
                                 chrx.dropMessage(5,
                                       "Come Out! Cute Rabbit-like Pet! Package has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "2023_package_4", "1");
                              } else if (type.equals("2023ํจํค์ง€5")) {
                                 chrx.dropMessage(5,
                                       "Savior Package has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "2023_package_5", "1");
                              } else if (type.equals("2023ํจํค์ง€6")) {
                                 chrx.dropMessage(5,
                                       "Year of the Rabbit Package C has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "2023_package_6", "1");
                              } else if (type.equals("2023ํจํค์ง€7")) {
                                 chrx.dropMessage(5,
                                       "Year of the Rabbit Package B has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "2023_package_7", "1");
                              } else if (type.equals("2023ํจํค์ง€8")) {
                                 chrx.dropMessage(5,
                                       "Year of the Rabbit Package A has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "2023_package_8", "1");
                              } else if (type.equals("2023ํจํค์ง€9")) {
                                 chrx.dropMessage(5,
                                       "Year of the Rabbit Package S has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "2023_package_9", "1");
                              } else if (type.equals("2023ํจํค์ง€10")) {
                                 chrx.dropMessage(5,
                                       "Year of the Rabbit Package SS has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "2023_package_10", "1");
                              } else if (type.equals("2023ํจํค์ง€11")) {
                                 chrx.dropMessage(5,
                                       "Year of the Rabbit Package SSS has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "2023_package_11", "1");
                              } else if (type.equals("5์”ํจํค์ง€1")) {
                                 chrx.dropMessage(5,
                                       "Love You 3000 Package has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "2023_may_package_1", "1");
                              } else if (type.equals("5์”ํจํค์ง€2")) {
                                 chrx.dropMessage(5,
                                       "Power Overwhelming Package has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "2023_may_package_2", "1");
                              } else if (type.equals("5์”ํจํค์ง€3")) {
                                 chrx.dropMessage(5,
                                       "M@STERPIECE Package has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "2023_may_package_3", "1");
                              } else if (type.equals("5์”ํจํค์ง€4")) {
                                 chrx.dropMessage(5,
                                       "Life Turnaround? Package has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "2023_may_package_4", "1");
                              } else if (type.equals("5์”ํจํค์ง€5")) {
                                 chrx.dropMessage(5,
                                       "Eris's Golden Apple Package has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "2023_may_package_5", "1");
                              } else if (type.equals("5์”ํจํค์ง€6")) {
                                 chrx.dropMessage(5,
                                       "Family Month Package C has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "2023_may_package_6", "1");
                              } else if (type.equals("5์”ํจํค์ง€7")) {
                                 chrx.dropMessage(5,
                                       "Family Month Package B has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "2023_may_package_7", "1");
                              } else if (type.equals("5์”ํจํค์ง€8")) {
                                 chrx.dropMessage(5,
                                       "Family Month Package A has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "2023_may_package_8", "1");
                              } else if (type.equals("5์”ํจํค์ง€9")) {
                                 chrx.dropMessage(5,
                                       "Family Month Package S has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "2023_may_package_9", "1");
                              } else if (type.equals("5์”ํจํค์ง€10")) {
                                 chrx.dropMessage(5,
                                       "Family Month Package SS has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "2023_may_package_10", "1");
                              } else if (type.equals("5์”ํจํค์ง€11")) {
                                 chrx.dropMessage(5,
                                       "Family Month Package SSS has been distributed. Please claim it from the Cash Shop.");
                                 chrx.updateOneInfo(1234579, "2023_may_package_11", "1");
                              }

                              found = true;
                              break;
                           }
                        }
                     }

                     if (!found) {
                        for (MapleCharacter chrxx : AuctionServer.getPlayerStorage().getAllCharacters()) {
                           if (chrxx.getName().equals(name)) {
                              player_name = name;
                              if (type.equals("์ด์ฌ์ ํจํค์ง€")) {
                                 chrxx.dropMessage(5,
                                       "Beginner Package has been distributed. Please claim it from the Cash Shop.");
                              } else if (type.equals("์ค๋ A")) {
                                 chrxx.dropMessage(5,
                                       "Lunar New Year Enhancement Package has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "nyd_package1", "1");
                              } else if (type.equals("์ค๋ B")) {
                                 chrxx.dropMessage(5,
                                       "Lunar New Year Master Berry Package has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "nyd_package2", "1");
                              } else if (type.equals("์ค๋ C")) {
                                 chrxx.dropMessage(5,
                                       "Lunar New Year Great Value Package has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "nyd_package3", "1");
                              } else if (type.equals("์–ด๋ฆฐ์ด๋ ")) {
                                 chrxx.dropMessage(5,
                                       "Children's Day Package has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1235999, "cd_package",
                                       String.valueOf(chrxx.getOneInfoQuestInteger(1235999, "cd_package") + 1));
                              } else if (type.equals("๊ฐ€์ •์๋ฌC")) {
                                 chrxx.dropMessage(5,
                                       "Family Month Package C has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "may_package_1", "1");
                              } else if (type.equals("๊ฐ€์ •์๋ฌB")) {
                                 chrxx.dropMessage(5,
                                       "Family Month Package B has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "may_package_2", "1");
                              } else if (type.equals("๊ฐ€์ •์๋ฌA")) {
                                 chrxx.dropMessage(5,
                                       "Family Month Package A has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "may_package_3", "1");
                              } else if (type.equals("๊ฐ€์ •์๋ฌS")) {
                                 chrxx.dropMessage(5,
                                       "Family Month Package S has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "may_package_4", "1");
                              } else if (type.equals("๊ฐ€์ •์๋ฌSS")) {
                                 chrxx.dropMessage(5,
                                       "Family Month Package SS has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "may_package_5", "1");
                              } else if (type.equals("๊ฐ€์ •์๋ฌSSS")) {
                                 chrxx.dropMessage(5,
                                       "Family Month Package SSS has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "may_package_6", "1");
                              } else if (type.equals("์์ํจํค์ง€1")) {
                                 chrxx.dropMessage(5,
                                       "Sealed Stat Box Package has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1236001, "a_package_1", "1");
                              } else if (type.equals("์์ํจํค์ง€2")) {
                                 chrxx.dropMessage(5,
                                       "Medal Option Enhancement Ticket Package has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1236001, "a_package_2", "1");
                              } else if (type.equals("์ถ”์ํจํค์ง€I")) {
                                 chrxx.dropMessage(5,
                                       "Chuseok Package I has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "chuseok_package_1", "1");
                              } else if (type.equals("์ถ”์ํจํค์ง€II")) {
                                 chrxx.dropMessage(5,
                                       "Chuseok Package II has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "chuseok_package_2", "1");
                              } else if (type.equals("์ถ”์ํจํค์ง€III")) {
                                 chrxx.dropMessage(5,
                                       "Chuseok Package III has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "chuseok_package_3", "1");
                              } else if (type.equals("์ถ”์ํจํค์ง€IV")) {
                                 chrxx.dropMessage(5,
                                       "Chuseok Package IV has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "chuseok_package_4", "1");
                              } else if (type.equals("3์ฃผ๋…ํจํค์ง€I")) {
                                 chrxx.dropMessage(5,
                                       "Advent Amazing Label Package has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "3aniv_package_1", "1");
                              } else if (type.equals("3์ฃผ๋…ํจํค์ง€II")) {
                                 chrxx.dropMessage(5,
                                       "Advent Wisp's Enhancement Package has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "3aniv_package_2", "1");
                              } else if (type.equals("3์ฃผ๋…ํจํค์ง€III")) {
                                 chrxx.dropMessage(5,
                                       "Advent Remaster Package has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "3aniv_package_3", "1");
                              } else if (type.equals("ํฌ๋ฆฌ์ค๋ง์คํจํค์ง€1")) {
                                 chrxx.dropMessage(5,
                                       "Merry Package has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "xmas_package_1", "1");
                              } else if (type.equals("ํฌ๋ฆฌ์ค๋ง์คํจํค์ง€2")) {
                                 chrxx.dropMessage(5,
                                       "Chris Package has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "xmas_package_2", "1");
                              } else if (type.equals("ํฌ๋ฆฌ์ค๋ง์คํจํค์ง€3")) {
                                 chrxx.dropMessage(5,
                                       "Mas Package has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "xmas_package_3", "1");
                              } else if (type.equals("ํฌ๋ฆฌ์ค๋ง์คํจํค์ง€4")) {
                                 chrxx.dropMessage(5,
                                       "Advent Christmas Package has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "xmas_package_4", "1");
                              } else if (type.equals("2023ํจํค์ง€1")) {
                                 chrxx.dropMessage(5,
                                       "Hopping to 300? Package has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "2023_package_1", "1");
                              } else if (type.equals("2023ํจํค์ง€2")) {
                                 chrxx.dropMessage(5,
                                       "Even Rabbits Need Strength to Catch! Package has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "2023_package_2", "1");
                              } else if (type.equals("2023ํจํค์ง€3")) {
                                 chrxx.dropMessage(5,
                                       "เนเธ”เนเธฃเธฑเธ What Do Rabbits Wear? Package เนเธฅเนเธง เธเธฃเธธเธ“เธฒเธฃเธฑเธเธ—เธตเน Cash Shop");
                                 chrxx.updateOneInfo(1234579, "2023_package_3", "1");
                              } else if (type.equals("2023ํจํค์ง€4")) {
                                 chrxx.dropMessage(5,
                                       "Come Out! Cute Rabbit-like Pet! Package has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "2023_package_4", "1");
                              } else if (type.equals("2023ํจํค์ง€5")) {
                                 chrxx.dropMessage(5,
                                       "Savior Package has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "2023_package_5", "1");
                              } else if (type.equals("2023ํจํค์ง€6")) {
                                 chrxx.dropMessage(5,
                                       "Year of the Rabbit Package C has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "2023_package_6", "1");
                              } else if (type.equals("2023ํจํค์ง€7")) {
                                 chrxx.dropMessage(5,
                                       "Year of the Rabbit Package B has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "2023_package_7", "1");
                              } else if (type.equals("2023ํจํค์ง€8")) {
                                 chrxx.dropMessage(5,
                                       "Year of the Rabbit Package A has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "2023_package_8", "1");
                              } else if (type.equals("2023ํจํค์ง€9")) {
                                 chrxx.dropMessage(5,
                                       "Year of the Rabbit Package S has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "2023_package_9", "1");
                              } else if (type.equals("2023ํจํค์ง€10")) {
                                 chrxx.dropMessage(5,
                                       "Year of the Rabbit Package SS has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "2023_package_10", "1");
                              } else if (type.equals("2023ํจํค์ง€11")) {
                                 chrxx.dropMessage(5,
                                       "Year of the Rabbit Package SSS has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "2023_package_11", "1");
                              } else if (type.equals("5์”ํจํค์ง€1")) {
                                 chrxx.dropMessage(5,
                                       "Love You 3000 Package has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "2023_may_package_1", "1");
                              } else if (type.equals("5์”ํจํค์ง€2")) {
                                 chrxx.dropMessage(5,
                                       "Power Overwhelming Package has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "2023_may_package_2", "1");
                              } else if (type.equals("5์”ํจํค์ง€3")) {
                                 chrxx.dropMessage(5,
                                       "M@STERPIECE Package has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "2023_may_package_3", "1");
                              } else if (type.equals("5์”ํจํค์ง€4")) {
                                 chrxx.dropMessage(5,
                                       "Life Turnaround? Package has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "2023_may_package_4", "1");
                              } else if (type.equals("5์”ํจํค์ง€5")) {
                                 chrxx.dropMessage(5,
                                       "Eris's Golden Apple Package has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "2023_may_package_5", "1");
                              } else if (type.equals("5์”ํจํค์ง€6")) {
                                 chrxx.dropMessage(5,
                                       "Family Month Package C has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "2023_may_package_6", "1");
                              } else if (type.equals("5์”ํจํค์ง€7")) {
                                 chrxx.dropMessage(5,
                                       "Family Month Package B has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "2023_may_package_7", "1");
                              } else if (type.equals("5์”ํจํค์ง€8")) {
                                 chrxx.dropMessage(5,
                                       "Family Month Package A has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "2023_may_package_8", "1");
                              } else if (type.equals("5์”ํจํค์ง€9")) {
                                 chrxx.dropMessage(5,
                                       "Family Month Package S has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "2023_may_package_9", "1");
                              } else if (type.equals("5์”ํจํค์ง€10")) {
                                 chrxx.dropMessage(5,
                                       "Family Month Package SS has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "2023_may_package_10", "1");
                              } else if (type.equals("5์”ํจํค์ง€11")) {
                                 chrxx.dropMessage(5,
                                       "Family Month Package SSS has been distributed. Please claim it from the Cash Shop.");
                                 chrxx.updateOneInfo(1234579, "2023_may_package_11", "1");
                              }

                              found = true;
                              break;
                           }
                        }
                     }
                  }

                  ps.close();
                  rs.close();
                  if (!found) {
                     ps = con.prepareStatement(
                           "INSERT INTO `questinfo_account` (`account_id`, `quest`, `customData`, `date`) VALUES(?, ?, ?, ?)");
                     ps.setInt(1, id);
                     if (type.equals("์ด์ฌ์ ํจํค์ง€")) {
                        ps.setInt(2, 1234555);
                        ps.setString(3, "beginner_package=1");
                     } else if (type.equals("์ค๋ A")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "nyd_package1=1");
                     } else if (type.equals("์ค๋ B")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "nyd_package2=1");
                     } else if (type.equals("์ค๋ C")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "nyd_package3=1");
                     } else if (type.equals("์–ด๋ฆฐ์ด๋ ")) {
                        PreparedStatement ps2 = con.prepareStatement(
                              "SELECT `customData` FROM `questinfo_account` WHERE `account_id` = ? and `quest` = ?");
                        ps2.setInt(1, id);
                        ps2.setInt(2, 1235999);
                        ResultSet rs2 = ps2.executeQuery();
                        boolean check = true;
                        if (rs2.next()) {
                           check = false;
                           PreparedStatement ps3 = con.prepareStatement(
                                 "DELETE FROM `questinfo_account` WHERE `account_id` = ? and `quest` = ?");
                           ps3.setInt(1, id);
                           ps3.setInt(2, 1235999);
                           ps3.executeQuery();
                           ps3.close();
                        }

                        rs2.close();
                        ps2.close();
                        ps.setInt(2, 1235999);
                        ps.setString(3, "cd_package=" + (check ? 1 : 2));
                     } else if (type.equals("๊ฐ€์ •์๋ฌC")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "may_package_1=1");
                     } else if (type.equals("๊ฐ€์ •์๋ฌB")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "may_package_2=1");
                     } else if (type.equals("๊ฐ€์ •์๋ฌA")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "may_package_3=1");
                     } else if (type.equals("๊ฐ€์ •์๋ฌS")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "may_package_4=1");
                     } else if (type.equals("๊ฐ€์ •์๋ฌSS")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "may_package_5=1");
                     } else if (type.equals("๊ฐ€์ •์๋ฌSSS")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "may_package_6=1");
                     } else if (type.equals("์์ํจํค์ง€1")) {
                        ps.setInt(2, 1236001);
                        ps.setString(3, "a_package_1=1");
                     } else if (type.equals("์์ํจํค์ง€2")) {
                        ps.setInt(2, 1236001);
                        ps.setString(3, "a_package_2=1");
                     } else if (type.equals("๊ฐ€์ •์๋ฌSSS")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "may_package_6=1");
                     } else if (type.equals("์ถ”์ํจํค์ง€I")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "chuseok_package_1=1");
                     } else if (type.equals("์ถ”์ํจํค์ง€II")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "chuseok_package_2=1");
                     } else if (type.equals("์ถ”์ํจํค์ง€III")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "chuseok_package_3=1");
                     } else if (type.equals("์ถ”์ํจํค์ง€IV")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "chuseok_package_4=1");
                     } else if (type.equals("3์ฃผ๋…ํจํค์ง€I")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "3aniv_package_1=1");
                     } else if (type.equals("3์ฃผ๋…ํจํค์ง€II")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "3aniv_package_2=1");
                     } else if (type.equals("3์ฃผ๋…ํจํค์ง€III")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "3aniv_package_3=1");
                     } else if (type.equals("ํฌ๋ฆฌ์ค๋ง์คํจํค์ง€1")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "xmas_package_1=1");
                     } else if (type.equals("ํฌ๋ฆฌ์ค๋ง์คํจํค์ง€2")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "xmas_package_2=1");
                     } else if (type.equals("ํฌ๋ฆฌ์ค๋ง์คํจํค์ง€3")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "xmas_package_3=1");
                     } else if (type.equals("ํฌ๋ฆฌ์ค๋ง์คํจํค์ง€4")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "xmas_package_4=1");
                     } else if (type.equals("2023ํจํค์ง€1")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "2023_package_1=1");
                     } else if (type.equals("2023ํจํค์ง€2")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "2023_package_2=1");
                     } else if (type.equals("2023ํจํค์ง€3")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "2023_package_3=1");
                     } else if (type.equals("2023ํจํค์ง€4")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "2023_package_4=1");
                     } else if (type.equals("2023ํจํค์ง€5")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "2023_package_5=1");
                     } else if (type.equals("2023ํจํค์ง€6")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "2023_package_6=1");
                     } else if (type.equals("2023ํจํค์ง€7")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "2023_package_7=1");
                     } else if (type.equals("2023ํจํค์ง€8")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "2023_package_8=1");
                     } else if (type.equals("2023ํจํค์ง€9")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "2023_package_9=1");
                     } else if (type.equals("2023ํจํค์ง€10")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "2023_package_10=1");
                     } else if (type.equals("2023ํจํค์ง€11")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "2023_package_11=1");
                     } else if (type.equals("5์”ํจํค์ง€1")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "2023_may_package_1=1");
                     } else if (type.equals("5์”ํจํค์ง€2")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "2023_may_package_2=1");
                     } else if (type.equals("5์”ํจํค์ง€3")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "2023_may_package_3=1");
                     } else if (type.equals("5์”ํจํค์ง€4")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "2023_may_package_4=1");
                     } else if (type.equals("5์”ํจํค์ง€5")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "2023_may_package_5=1");
                     } else if (type.equals("5์”ํจํค์ง€6")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "2023_may_package_6=1");
                     } else if (type.equals("5์”ํจํค์ง€7")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "2023_may_package_7=1");
                     } else if (type.equals("5์”ํจํค์ง€8")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "2023_may_package_8=1");
                     } else if (type.equals("5์”ํจํค์ง€9")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "2023_may_package_9=1");
                     } else if (type.equals("5์”ํจํค์ง€10")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "2023_may_package_10=1");
                     } else if (type.equals("5์”ํจํค์ง€11")) {
                        ps.setInt(2, 1234579);
                        ps.setString(3, "2023_may_package_11=1");
                     }

                     sdf = new SimpleDateFormat("yyyyMMdd");
                     fDate = sdf.format(System.currentTimeMillis());
                     ps.setString(4, fDate);
                     ps.executeQuery();
                  }

                  if (!player_name.equals("")) {
                     ps = con.prepareStatement("SELECT `id` FROM `characters` WHERE `name` = ?");
                     ps.setString(1, player_name);
                     rs = ps.executeQuery();

                     while (rs.next()) {
                        player_id = rs.getInt("id");
                     }

                     ps.close();
                     rs.close();
                  }

                  StringBuilder sb = new StringBuilder();
                  sb.append(type + " ์ง€๊ธ");
                  sb.append(" (๊ณ์ •ID : ");
                  sb.append(accountName);
                  sb.append(") ");
                  LoggingManager.putLog(new DonationLog(accountName, type, sb, player_name, player_id, account_id));
                  return true;
               }

               System.out
                     .println("[ERROR] Account not found, auto-recharge failed. (Account : " + accountName + ", ๊ธ์•ก : " + point + ")");
               return false;
            } catch (SQLException var90) {
               System.out.println(
                     "[ERROR] DB error occurred, auto-recharge failed. (Account : " + accountName + ", ๊ธ์•ก : " + point + ")");
               return true;
            } finally {
               try {
                  if (ps != null) {
                     ps.close();
                     PreparedStatement var107 = null;
                  }

                  if (rs != null) {
                     rs.close();
                     ResultSet var108 = null;
                  }
               } catch (SQLException var82) {
               }
            }
         }
      } else {
         int pricex = 0;

         try {
            pricex += point;
            if (!DBConfig.isGanglim) {
               pricex += point / 10;
            }

            if (first && !DBConfig.isGanglim) {
               pricex += Math.min(300000, point);
            }
         } catch (NumberFormatException var95) {
            return false;
         }

         if (!DBConfig.isGanglim) {
            Center.sunShineStorage.addSunShineGuage((int) (point * 0.2));
            Center.sunShineStorage.save();
         }

         long remainCash = 0L;
         List<Integer> rewards = getStackRewards(totalDonation, point);
         PreparedStatement ps = null;
         ResultSet rs = null;
         String targetName = "";

         try (Connection con = DBConnection.getConnection()) {
            ps = con.prepareStatement("SELECT `id` FROM `accounts` WHERE `name` = ?");
            ps.setString(1, accountName);
            rs = ps.executeQuery();
            boolean findCharx = false;

            int id;
            for (id = 0; rs.next(); findCharx = true) {
               id = rs.getInt("id");
               account_id = id;
            }

            if (!findCharx) {
               return false;
            }

            ps.close();
            rs.close();
            ps = con.prepareStatement("SELECT `name` FROM `characters` WHERE `accountid` = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            NumberFormat nf = NumberFormat.getInstance();
            boolean found = false;

            while (rs.next()) {
               String name = rs.getString("name");
               if (targetName.isEmpty()) {
                  targetName = name;
               }

               for (GameServer cs : GameServer.getAllInstances()) {
                  for (MapleCharacter chrxxx : cs.getPlayerStorage().getAllCharacters()) {
                     if (chrxxx.getName().equals(name)) {
                        if (DBConfig.isGanglim) {
                           if (ServerConstants.cashPlusRate > 0) {
                              int bonuscash = (int) ((double) pricex * ServerConstants.cashPlusRate / 100.0);
                              chrxxx.gainCashPointEvent(pricex, bonuscash);
                              int totalcash = pricex + bonuscash;
                              chrxxx.dropMessage(
                                    1, nf.format((long) totalcash) + "(" + nf.format((long) pricex) + "+Bonus"
                                          + nf.format((long) bonuscash) + ") Cash has been distributed.");
                              chrxxx.send(
                                    CField.addPopupSay(
                                          9062000,
                                          3000,
                                          "#b"
                                                + nf.format((long) totalcash)
                                                + "("
                                                + nf.format((long) pricex)
                                                + "+Bonus"
                                                + nf.format((long) bonuscash)
                                                + ") Cash#k has been distributed.",
                                          ""));
                           } else {
                              chrxxx.gainCashPoint(pricex);
                              chrxxx.dropMessage(1, nf.format((long) pricex) + " Cash has been distributed.");
                              chrxxx.send(CField.addPopupSay(9062000, 3000,
                                    "#b" + nf.format((long) pricex) + " Cash#k has been distributed.", ""));
                           }
                        } else {
                           chrxxx.gainRealCash(pricex);
                           chrxxx.dropMessage(1, "เนเธ”เนเธฃเธฑเธ " + nf.format((long) pricex) + " Advent Points.");
                           chrxxx.send(CField.addPopupSay(9062000, 3000,
                                 "เนเธ”เนเธฃเธฑเธ #b" + nf.format((long) pricex) + "#k Advent Points.", ""));
                        }

                        found = true;
                        targetName = name;
                        player_name = name;
                        remainCash = chrxxx.getCashPoint();
                        break;
                     }
                  }
               }

               for (MapleCharacter chrxxxx : CashShopServer.getPlayerStorage().getAllCharacters()) {
                  if (chrxxxx.getName().equals(name)) {
                     if (DBConfig.isGanglim) {
                        if (ServerConstants.cashPlusRate > 0) {
                           int bonuscash = (int) ((double) pricex * ServerConstants.cashPlusRate / 100.0);
                           chrxxxx.gainCashPointEvent(pricex, bonuscash);
                           int totalcash = pricex + bonuscash;
                           chrxxxx.dropMessage(
                                 1, nf.format((long) totalcash) + "(" + nf.format((long) pricex) + "+Bonus"
                                       + nf.format((long) bonuscash) + ") Cash has been distributed.");
                        } else {
                           chrxxxx.gainCashPoint(pricex);
                           chrxxxx.dropMessage(1, nf.format((long) pricex) + " Cash has been distributed.");
                        }
                     } else {
                        chrxxxx.gainRealCash(pricex);
                        chrxxxx.dropMessage(1, "เนเธ”เนเธฃเธฑเธ " + nf.format((long) pricex) + " Advent Points.");
                     }

                     targetName = name;
                     player_name = name;
                     found = true;
                     remainCash = chrxxxx.getCashPoint();
                     break;
                  }
               }

               for (MapleCharacter chrxxxxx : AuctionServer.getPlayerStorage().getAllCharacters()) {
                  if (chrxxxxx.getName().equals(name)) {
                     if (DBConfig.isGanglim) {
                        if (ServerConstants.cashPlusRate > 0) {
                           int bonuscash = (int) ((double) pricex * ServerConstants.cashPlusRate / 100.0);
                           chrxxxxx.gainCashPointEvent(pricex, bonuscash);
                           int totalcash = pricex + bonuscash;
                           chrxxxxx.dropMessage(
                                 1, nf.format((long) totalcash) + "(" + nf.format((long) pricex) + "+Bonus"
                                       + nf.format((long) bonuscash) + ") Cash has been distributed.");
                        } else {
                           chrxxxxx.gainCashPoint(pricex);
                           chrxxxxx.dropMessage(1, nf.format((long) pricex) + " Cash has been distributed.");
                        }
                     } else {
                        chrxxxxx.gainRealCash(pricex);
                        chrxxxxx.dropMessage(1, "เนเธ”เนเธฃเธฑเธ " + nf.format((long) pricex) + " Advent Points.");
                     }

                     targetName = name;
                     player_name = name;
                     found = true;
                     remainCash = chrxxxxx.getCashPoint();
                     break;
                  }
               }
            }

            if (!found) {
               boolean f = false;
               if (DBConfig.isGanglim) {
                  int accountID = 0;
                  PreparedStatement ps2 = con.prepareStatement("SELECT `id` FROM `accounts` WHERE `name` = ?");
                  ps2.setString(1, accountName);
                  ResultSet rs2 = ps2.executeQuery();
                  if (rs2.next()) {
                     accountID = rs2.getInt("id");
                     f = true;
                  }

                  ps2.close();
                  ps2 = con.prepareStatement("SELECT `value` FROM `acckeyvalue` WHERE `key` = ? and `id` = ?");
                  ps2.setString(1, "CashPoint");
                  ps2.setInt(2, accountID);
                  rs2 = ps2.executeQuery();
                  if (rs2.next()) {
                     int rc = rs2.getInt("value");
                     PreparedStatement ps3 = con
                           .prepareStatement("UPDATE `acckeyvalue` SET `value` = ? WHERE `id` = ? and `key` = ?");
                     if (ServerConstants.cashPlusRate > 0) {
                        int bonuscash = (int) ((double) pricex * ServerConstants.cashPlusRate / 100.0);
                        remainCash = rc + pricex + bonuscash;
                        ps3.setString(1, String.valueOf(rc + pricex + bonuscash));
                     } else {
                        remainCash = rc + pricex;
                        ps3.setString(1, String.valueOf(rc + pricex));
                     }

                     ps3.setInt(2, accountID);
                     ps3.setString(3, "CashPoint");
                     ps3.executeUpdate();
                     ps3.close();
                  } else {
                     PreparedStatement ps3 = con
                           .prepareStatement("INSERT INTO `acckeyvalue` (`id`, `key`, `value`) VALUES(?, ?, ?)");
                     ps3.setInt(1, accountID);
                     ps3.setString(2, "CashPoint");
                     if (ServerConstants.cashPlusRate > 0) {
                        int bonuscash = (int) ((double) pricex * ServerConstants.cashPlusRate / 100.0);
                        ps3.setString(3, String.valueOf(pricex + bonuscash));
                        remainCash = pricex + bonuscash;
                     } else {
                        ps3.setString(3, String.valueOf(pricex));
                        remainCash = pricex;
                     }

                     ps3.executeUpdate();
                     ps3.close();
                  }

                  rs2.close();
                  ps2.close();
                  ps2 = con.prepareStatement("SELECT `value` FROM `acckeyvalue` WHERE `key` = ? and `id` = ?");
                  ps2.setString(1, "DPointAll");
                  ps2.setInt(2, accountID);
                  rs2 = ps2.executeQuery();
                  if (rs2.next()) {
                     int rc = rs2.getInt("value");
                     PreparedStatement ps3 = con
                           .prepareStatement("UPDATE `acckeyvalue` SET `value` = ? WHERE `id` = ? and `key` = ?");
                     ps3.setString(1, String.valueOf(rc + pricex));
                     ps3.setInt(2, accountID);
                     ps3.setString(3, "DPointAll");
                     ps3.executeUpdate();
                     ps3.close();
                  } else {
                     PreparedStatement ps3 = con
                           .prepareStatement("INSERT INTO `acckeyvalue` (`id`, `key`, `value`) VALUES(?, ?, ?)");
                     ps3.setInt(1, accountID);
                     ps3.setString(2, "DPointAll");
                     ps3.setString(3, String.valueOf(pricex));
                     ps3.executeUpdate();
                     ps3.close();
                  }

                  rs2.close();
                  ps2.close();
               } else {
                  PreparedStatement ps2x = con.prepareStatement("SELECT `realCash` FROM `accounts` WHERE `name` = ?");
                  ps2x.setString(1, accountName);

                  ResultSet rs2x;
                  for (rs2x = ps2x.executeQuery(); rs2x.next(); f = true) {
                     int rc = rs2x.getInt("realCash");
                     PreparedStatement ps3 = con.prepareStatement("UPDATE accounts SET realCash = ? WHERE `name` = ?");
                     ps3.setInt(1, rc + pricex);
                     ps3.setString(2, accountName);
                     ps3.executeUpdate();
                     ps3.close();
                  }

                  rs2x.close();
                  ps2x.close();
               }

               if (!f) {
                  System.out.println(
                        "[ERROR] An error occurred during DB processing, so automatic charge was not processed. (Account : "
                              + accountName + ", Amount : " + point + ")");
                  return false;
               }
            }

            ps.close();
            if (!DBConfig.isGanglim) {
               for (int reward : rewards) {
                  ps = con.prepareStatement("INSERT INTO extreme_point_log (accountid, type) VALUES(?, ?)");
                  ps.setInt(1, id);
                  ps.setString(2, String.valueOf(reward));
                  ps.executeUpdate();
                  ps.close();
               }
            }

            if (!player_name.equals("")) {
               ps = con.prepareStatement("SELECT `id` FROM `characters` WHERE `name` = ?");
               ps.setString(1, player_name);
               rs = ps.executeQuery();

               while (rs.next()) {
                  player_id = rs.getInt("id");
               }

               ps.close();
               rs.close();
            }

            ps = con.prepareStatement(
                  "INSERT INTO `donation_log` (`date`, `account`, `name`, `price`) VALUES (?, ?, ?, ?)");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy. M. d");
            String fDate = sdf.format(System.currentTimeMillis());
            ps.setString(1, fDate);
            ps.setString(2, accountName);
            if (type.equals("์ด๋ฒคํธ ์ฐธ์—ฌ")) {
               targetName = "EventParticipation";
            } else if (type.equals("์ ๋… ์ด๋ฒคํธ")) {
               targetName = "NewYearEvent";
            } else if (type.equals("ํด์ค๋ง์ค ์ด๋ฒคํธ")) {
               targetName = "ChristmasEvent";
            } else if (type.equals("๋ณด๋์ค์ด๋ฒคํธ")) {
               targetName = "BonusEvent";
            }

            ps.setString(3, targetName);
            ps.setString(4, nf.format((long) point));
            ps.executeQuery();
         } catch (SQLException var93) {
            var93.printStackTrace();
            System.out.println(
                  "[ERROR] An error occurred during DB processing, so automatic charge was not processed. (Account : "
                        + accountName + ", Amount : " + point + ")");
            return false;
         } finally {
            try {
               if (ps != null) {
                  ps.close();
                  PreparedStatement var114 = null;
               }

               if (rs != null) {
                  rs.close();
                  ResultSet var116 = null;
               }
            } catch (SQLException var83) {
            }
         }

         StringBuilder sb = new StringBuilder();
         sb.append("Donation Point Distribution");
         sb.append(" (๊ณ์ •ID : ");
         sb.append(accountName);
         sb.append(") ");
         if (ServerConstants.cashPlusRate > 0) {
            int bonuscash = (int) ((double) pricex * ServerConstants.cashPlusRate / 100.0);
            sb.append(pricex + " Points, " + bonuscash + " Bonus (Remaining Amount : " + remainCash + ")");
         } else {
            sb.append(pricex + " Points (Remaining Amount : " + remainCash + ")");
         }

         LoggingManager
               .putLog(new DonationLog(accountName, String.valueOf(pricex), sb, player_name, player_id, account_id));
      }

      return true;
   }

   public static void cancelAutoFeverTask() {
      if (autoFeverTask != null) {
         autoFeverTask.cancel(true);
         autoFeverTask = null;
      }
   }

   public static void cancelAutoHotTimeItemTask() {
      if (autoHottimeItemTask != null) {
         autoHottimeItemTask.cancel(true);
         autoHottimeItemTask = null;
      }
   }

   public static void registerLoggingSave() {
      System.out.println("Log recording event has been registered.");
      loggingSaveTask = Timer.HottimeTimer.getInstance().register(new Runnable() {
         @Override
         public void run() {
            LoggingManager.insert();
         }
      }, 60000L);
   }

   public static void registerHottimeItem() {
      System.out.println("Auto Hot Time Item event has been registered.");
      autoHottimeItemTask = Timer.HottimeTimer.getInstance().register(() -> {
         for (HottimeItemEntry entry : new ArrayList<>(HottimeItemManager.entryList)) {
            if (System.currentTimeMillis() >= entry.getTime()) {
               gainItemExpiration(entry.getItemID(), 0, (short) entry.getQuantity());
               HottimeItemManager.entryList.remove(entry);
            }
         }
      }, 1000L);
   }

   public static void registerAutoFever() {
      System.out.println("Auto Hot Time Fever event has been registered.");
      autoFeverTask = Timer.HottimeTimer.getInstance().register(() -> {
         List<AutoHottimeEntry> removes = new ArrayList<>();

         for (AutoHottimeEntry entry : AutoHottimeManager.entryList) {
            if (System.currentTimeMillis() >= entry.getStartTime() && System.currentTimeMillis() < entry.getEndTime()
                  && !entry.isStarted()) {
               String type = "";
               if (entry.getEventType() == AutoHottimeEntry.EventType.Exp) {
                  ServerConstants.expFeverRate = entry.getRate();
                  type = "Exp";
               } else if (entry.getEventType() == AutoHottimeEntry.EventType.Drop) {
                  ServerConstants.dropFeverRate = entry.getRate();
                  type = "Drop";
               } else if (entry.getEventType() == AutoHottimeEntry.EventType.Meso) {
                  ServerConstants.mesoFeverRate = entry.getRate();
                  type = "Meso";
               } else if (entry.getEventType() == AutoHottimeEntry.EventType.Give) {
                  type = "Distribution";
               }

               if (type.equals("์ง€๊ธ")) {
                  Center.Broadcast.broadcastMessage(CField.chatMsg(3, "[FeverEvent] เธเธดเธเธเธฃเธฃเธก Hot Time เน€เธฃเธดเนเธกเธ•เนเธเนเธฅเนเธง"));
                  System.out.println("Hot Time Auto Item Distribution Started!");
               } else {
                  Center.Broadcast.broadcastMessage(
                        CField.chatMsg(3, "[FeverEvent] " + type + " " + entry.getRate() + "x Event has started."));
                  System.out.println(entry.getEventType().name() + " " + entry.getRate() + " Fever Started!");
               }

               entry.setStarted(true);
            }

            if (System.currentTimeMillis() >= entry.getEndTime()) {
               removes.add(entry);
               String typex = "";
               if (entry.getEventType() == AutoHottimeEntry.EventType.Exp) {
                  ServerConstants.expFeverRate = 1.0;
                  typex = "Exp";
               } else if (entry.getEventType() == AutoHottimeEntry.EventType.Drop) {
                  ServerConstants.dropFeverRate = 1.0;
                  typex = "Drop";
               } else if (entry.getEventType() == AutoHottimeEntry.EventType.Meso) {
                  ServerConstants.mesoFeverRate = 1.0;
                  typex = "Meso";
               } else if (entry.getEventType() == AutoHottimeEntry.EventType.Give) {
                  typex = "Distribution";
               }

               if (typex.equals("์ง€๊ธ")) {
                  Center.Broadcast.broadcastMessage(CField.chatMsg(3, "[FeverEvent] เธเธดเธเธเธฃเธฃเธก Hot Time เธเธเธฅเธเนเธฅเนเธง"));
                  System.out.println("Hot Time Auto Item Distribution Ended!");
               } else {
                  Center.Broadcast.broadcastMessage(
                        CField.chatMsg(3, "[FeverEvent] " + typex + " " + entry.getRate() + "x Event has ended."));
                  System.out.println(entry.getEventType().name() + " " + entry.getRate() + " Fever Ended");
               }
            }
         }

         removes.forEach(e -> AutoHottimeManager.entryList.remove(e));
      }, 1000L);
   }

   public static void cancelAutoNoticeTask() {
      if (autoNoticeTask != null) {
         autoNoticeTask.cancel(true);
         autoNoticeTask = null;
      }
   }

   public static void registerAutoNotice() {
      if (MapleAutoNotice.autoNotice == null) {
         System.err.println("[ERROR] Failed to load auto-notice data.");
      } else {
         System.out.println("Auto Notice System has been registered.");
         autoNoticeTask = Timer.HottimeTimer.getInstance().register(new Runnable() {
            @Override
            public void run() {
               MapleAutoNotice autoNotice = MapleAutoNotice.autoNotice;
               List<String> notices = new ArrayList<>(autoNotice.getNotice());
               Collections.shuffle(notices);
               String notice = notices.stream().collect(Collectors.toList()).stream().findAny().get();
               Center.Broadcast.broadcastMessage(CWvsContext.serverNotice(autoNotice.getNoticeType(),
                     "[" + ServerConstants.serverName + "] " + notice));
            }
         }, MapleAutoNotice.autoNotice.getInterval());
      }
   }

   public static void registerAutoCharge() {
      System.out.println("Donation Auto Charge System has been registered.");
      autoChargeTask = Timer.HottimeTimer.getInstance()
            .register(
                  new Runnable() {
                     @Override
                     public void run() {
                        PreparedStatement ps = null;
                        ResultSet rs = null;
                        boolean find = false;

                        try {
                           try (Connection con = DBConnection.getConnection()) {
                              ps = con.prepareStatement("SELECT * FROM `price` WHERE `check` = ?");
                              ps.setInt(1, 0);
                              rs = ps.executeQuery();

                              while (rs.next()) {
                                 String name = rs.getString("name").trim().replace("\n", "").replace("\t", "");
                                 name = name.split("\\(")[0];
                                 PreparedStatement ps2 = null;
                                 ResultSet rs2 = null;

                                 try {
                                    String price = rs.getString("price").replace(",", "");
                                    int p = Integer.parseInt(price);
                                    ps2 = con.prepareStatement(
                                          "SELECT * FROM `donation_request` WHERE `real_name` = ? and `point` = ? and `status` = ?");
                                    ps2.setString(1, name);
                                    ps2.setInt(2, p);
                                    ps2.setInt(3, 0);
                                    rs2 = ps2.executeQuery();

                                    while (rs2.next()) {
                                       int type = rs2.getInt("type");
                                       String t = "์ผ๋ฐ";
                                       if (type == 1) {
                                          t = "์ด๋ฒคํธ ์ฐธ์—ฌ";
                                       } else if (type == 2) {
                                          t = "์ด์ฌ์ ํจํค์ง€";
                                       } else if (type == 3) {
                                          t = "๋ณด๋์ค์ด๋ฒคํธ";
                                       } else if (type == 5) {
                                          t = "์ค๋ A";
                                       } else if (type == 6) {
                                          t = "์ค๋ B";
                                       } else if (type == 7) {
                                          t = "์ค๋ C";
                                       } else if (type == 8) {
                                          t = "์–ด๋ฆฐ์ด๋ ";
                                       } else if (type == 9) {
                                          t = "๊ฐ€์ •์๋ฌC";
                                       } else if (type == 10) {
                                          t = "๊ฐ€์ •์๋ฌB";
                                       } else if (type == 11) {
                                          t = "๊ฐ€์ •์๋ฌA";
                                       } else if (type == 12) {
                                          t = "๊ฐ€์ •์๋ฌS";
                                       } else if (type == 13) {
                                          t = "๊ฐ€์ •์๋ฌSS";
                                       } else if (type == 14) {
                                          t = "๊ฐ€์ •์๋ฌSSS";
                                       } else if (type == 15) {
                                          t = "์์ํจํค์ง€1";
                                       } else if (type == 16) {
                                          t = "์์ํจํค์ง€2";
                                       } else if (type == 17) {
                                          t = "์ถ”์ํจํค์ง€I";
                                       } else if (type == 18) {
                                          t = "์ถ”์ํจํค์ง€II";
                                       } else if (type == 19) {
                                          t = "์ถ”์ํจํค์ง€III";
                                       } else if (type == 20) {
                                          t = "์ถ”์ํจํค์ง€IV";
                                       } else if (type == 21) {
                                          t = "3์ฃผ๋…ํจํค์ง€I";
                                       } else if (type == 22) {
                                          t = "3์ฃผ๋…ํจํค์ง€II";
                                       } else if (type == 23) {
                                          t = "3์ฃผ๋…ํจํค์ง€III";
                                       } else if (type == 24) {
                                          t = "ํฌ๋ฆฌ์ค๋ง์คํจํค์ง€1";
                                       } else if (type == 25) {
                                          t = "ํฌ๋ฆฌ์ค๋ง์คํจํค์ง€2";
                                       } else if (type == 26) {
                                          t = "ํฌ๋ฆฌ์ค๋ง์คํจํค์ง€3";
                                       } else if (type == 27) {
                                          t = "ํฌ๋ฆฌ์ค๋ง์คํจํค์ง€4";
                                       } else if (type == 28) {
                                          t = "2023ํจํค์ง€1";
                                       } else if (type == 29) {
                                          t = "2023ํจํค์ง€2";
                                       } else if (type == 30) {
                                          t = "2023ํจํค์ง€3";
                                       } else if (type == 31) {
                                          t = "2023ํจํค์ง€4";
                                       } else if (type == 32) {
                                          t = "2023ํจํค์ง€5";
                                       } else if (type == 33) {
                                          t = "2023ํจํค์ง€6";
                                       } else if (type == 34) {
                                          t = "2023ํจํค์ง€7";
                                       } else if (type == 35) {
                                          t = "2023ํจํค์ง€8";
                                       } else if (type == 36) {
                                          t = "2023ํจํค์ง€9";
                                       } else if (type == 37) {
                                          t = "2023ํจํค์ง€10";
                                       } else if (type == 38) {
                                          t = "2023ํจํค์ง€11";
                                       } else if (type == 39) {
                                          t = "5์”ํจํค์ง€1";
                                       } else if (type == 40) {
                                          t = "5์”ํจํค์ง€2";
                                       } else if (type == 41) {
                                          t = "5์”ํจํค์ง€3";
                                       } else if (type == 42) {
                                          t = "5์”ํจํค์ง€4";
                                       } else if (type == 43) {
                                          t = "5์”ํจํค์ง€5";
                                       } else if (type == 44) {
                                          t = "5์”ํจํค์ง€6";
                                       } else if (type == 45) {
                                          t = "5์”ํจํค์ง€7";
                                       } else if (type == 46) {
                                          t = "5์”ํจํค์ง€8";
                                       } else if (type == 47) {
                                          t = "5์”ํจํค์ง€9";
                                       } else if (type == 48) {
                                          t = "5์”ํจํค์ง€10";
                                       } else if (type == 49) {
                                          t = "5์”ํจํค์ง€11";
                                       }

                                       if (DBConfig.isGanglim && !t.equals("์ผ๋ฐ")) {
                                          return;
                                       }

                                       if (Center.doCharge(t, rs2.getString("account_name"), p)) {
                                          find = true;
                                          PreparedStatement ps3 = null;
                                          ps3 = con.prepareStatement(
                                                "UPDATE `price` SET `check` = ? WHERE `name` = ? and `price` = ?");
                                          ps3.setInt(1, 1);
                                          ps3.setString(2, rs.getString("name"));
                                          ps3.setString(3, rs.getString("price"));
                                          ps3.executeUpdate();
                                          ps3.close();
                                          ps3 = con.prepareStatement(
                                                "UPDATE `donation_request` SET `status` = ? WHERE `real_name` = ? and `point` = ?");
                                          ps3.setInt(1, 1);
                                          ps3.setString(2, name);
                                          ps3.setInt(3, p);
                                          ps3.executeUpdate();
                                          ps3.close();
                                          System.out
                                                .println("Auto Charge Complete : Character("
                                                      + rs2.getString("player_name") + "), Depositor("
                                                      + name + "), Amount(" + rs.getString("price") + ")");
                                          DonationRequest.init();
                                          if (!DBConfig.isGanglim) {
                                             DiscordBotHandler.requestSendTelegram(
                                                   "Charge Complete > Character(" + rs2.getString("player_name")
                                                         + "), Depositor(" + name
                                                         + "), Amount(" + rs.getString("price") + ")",
                                                   -517653288L);
                                          } else {
                                             DiscordBotHandler.requestSendTelegramDonation(
                                                   "Charge Complete > Character(" + rs2.getString("player_name")
                                                         + "), Depositor(" + name
                                                         + "), Amount(" + rs.getString("price") + ")");
                                          }
                                       }
                                    }

                                    rs2.close();
                                    ps2.close();
                                 } catch (SQLException var28) {
                                    try {
                                       if (rs2 != null) {
                                          rs2.close();
                                          ResultSet var37 = null;
                                       }

                                       if (ps2 != null) {
                                          ps2.close();
                                          PreparedStatement var36 = null;
                                       }
                                    } catch (SQLException var27) {
                                    }
                                 }
                              }
                           } catch (SQLException var30) {
                           }
                        } finally {
                           try {
                              if (ps != null) {
                                 ps.close();
                                 PreparedStatement var32 = null;
                              }

                              if (rs != null) {
                                 rs.close();
                                 ResultSet var33 = null;
                              }
                           } catch (SQLException var25) {
                           }
                        }
                     }
                  },
                  60000L);
   }

   public static void registerAutoHottime() {
      System.out.println("Hot Time Auto Distribution System has been registered.");
      autoHottimeTask = Timer.HottimeTimer.getInstance()
            .register(
                  new Runnable() {
                     @Override
                     public void run() {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                        Calendar CAL = new GregorianCalendar(Locale.KOREA);
                        String fDate = sdf.format(CAL.getTime());
                        String[] dates = fDate.split("-");
                        int year = Integer.parseInt(dates[0]);
                        int month = Integer.parseInt(dates[1]);
                        int day = Integer.parseInt(dates[2]);
                        int hours = Integer.parseInt(dates[3]);
                        int minutes = Integer.parseInt(dates[4]);
                        int seconds = Integer.parseInt(dates[5]);
                        int zellerMonth = 0;
                        int zellerYear = 0;
                        if (month < 3) {
                           zellerMonth = month + 12;
                           zellerYear = year - 1;
                        } else {
                           zellerMonth = month;
                           zellerYear = year;
                        }

                        int computation = day + 26 * (zellerMonth + 1) / 10 + zellerYear + zellerYear / 4
                              + 6 * (zellerYear / 100) + zellerYear / 400;
                        int dayOfWeek = computation % 7;
                        if (Center.lastMonth == 0) {
                           Center.lastMonth = month;
                        }

                        if (Center.lastCheckLoggedinTime == 0L
                              || Center.lastCheckLoggedinTime + 300000L <= System.currentTimeMillis()) {
                           PreparedStatement ps = null;
                           ResultSet rs = null;
                           DBProcessor dis = DBEventManager.getNextProcessor();

                           try (Connection con = DBConnection.getConnection()) {
                              ps = con
                                    .prepareStatement("SELECT * FROM `accounts` WHERE `loggedin` > 0 or `allowed` > 0");
                              rs = ps.executeQuery();

                              while (rs.next()) {
                                 final int accountID = rs.getInt("id");
                                 if (rs.getInt("loggedin") > 0) {
                                    boolean find = false;

                                    for (GameServer cs : GameServer.getAllInstances()) {
                                       for (Field map : cs.getMapFactory().getAllMaps()) {
                                          for (MapleCharacter chr : new ArrayList<>(map.getCharacters())) {
                                             if (chr != null
                                                   && chr.getClient().getAccID() == accountID
                                                   && (!chr.getClient().getSession().isActive()
                                                         || !chr.getClient().getSession().isOpen()
                                                         || System.currentTimeMillis()
                                                               - chr.getLastHeartBeatTime() > 300000L)) {
                                                find = true;
                                             }
                                          }
                                       }
                                    }

                                    for (MapleCharacter p : new ArrayList<>(
                                          CashShopServer.getPlayerStorage().getAllCharacters())) {
                                       if (p.getAccountID() == accountID && (!p.getClient().getSession().isActive()
                                             || !p.getClient().getSession().isOpen())) {
                                          find = true;
                                       }
                                    }

                                    for (MapleCharacter px : new ArrayList<>(
                                          AuctionServer.getPlayerStorage().getAllCharacters())) {
                                       if (px.getAccountID() == accountID && (!px.getClient().getSession().isActive()
                                             || !px.getClient().getSession().isOpen())) {
                                          find = true;
                                       }
                                    }

                                    if (find) {
                                       dis.addQuery(DBSelectionKey.INSERT_OR_UPDATE,
                                             "UPDATE `accounts` SET `loggedin` = ? WHERE id = ?", new DBCallback() {
                                                @Override
                                                public void execute(PreparedStatement ps) throws SQLException {
                                                   ps.setInt(1, 0);
                                                   ps.setInt(2, accountID);
                                                }
                                             });
                                    }
                                 }
                              }
                           } catch (SQLException var153) {
                              var153.printStackTrace();
                           } finally {
                              try {
                                 if (ps != null) {
                                    ps.close();
                                    PreparedStatement var157 = null;
                                 }

                                 if (rs != null) {
                                    rs.close();
                                    ResultSet var162 = null;
                                 }
                              } catch (SQLException var138) {
                                 var138.printStackTrace();
                              }
                           }

                           Center.lastCheckLoggedinTime = System.currentTimeMillis();
                        }

                        if (Center.lastMonth != 0 && Center.lastMonth != month) {
                           for (GameServer cs : GameServer.getAllInstances()) {
                              for (Field map : cs.getMapFactory().getAllMaps()) {
                                 for (MapleCharacter chrx : new ArrayList<>(map.getCharacters())) {
                                    if (chrx != null) {
                                       chrx.updateOneInfo(1234570, "blackmage_clear", "");
                                       chrx.updateOneInfo(1234570, "lastMonth", String.valueOf(month));
                                    }
                                 }
                              }
                           }

                           System.out.println("[Notice] Monthly Boss has been reset.");
                           Center.lastMonth = month;
                        }

                        if (!DBConfig.isGanglim
                              && (month == 2 && day >= 25 || month == 3 || month == 4)
                              && (hours == 0 && minutes == 5 && seconds == 10
                                    || hours == 4 && minutes == 0 && seconds == 1
                                    || hours == 8 && minutes == 0 && seconds == 1
                                    || hours == 12 && minutes == 0 && seconds == 1
                                    || hours == 16 && minutes == 0 && seconds == 1
                                    || hours == 20 && minutes == 0 && seconds == 1)) {
                           for (int i = 1; i <= 3; i++) {
                              Field target = GameServer.getInstance(i).getMapFactory().getMap(910010000);
                              if (target == null) {
                                 return;
                              }

                              Field_EventRabbit f = (Field_EventRabbit) target;
                              if (f != null) {
                                 f.resetFully(false);
                                 MapleMonster mob = MapleLifeFactory.getMonster(9500006 + Randomizer.rand(0, 1));
                                 mob.setHp(20000000L);
                                 mob.getStats().setHp(20000000L);
                                 target.spawnMonsterOnGroundBelow(mob, new Point(-82, 153));
                                 f.setRabbitSpawnedTime(System.currentTimeMillis());
                              }
                           }

                           for (GameServer cs : GameServer.getAllInstances()) {
                              for (MapleCharacter chrxx : cs.getPlayerStorage().getAllCharacters()) {
                                 if (chrxx != null) {
                                    int type = 1;
                                    int gameType = 9;
                                    chrxx.removeRandomPortal();
                                    RandomPortalType portalType = RandomPortalType.get(type);
                                    RandomPortalGameType portalGameType = RandomPortalGameType.get(gameType);
                                    Point pos = chrxx.getMap().calcDropPos(chrxx.getTruePosition(),
                                          chrxx.getPosition());
                                    RandomPortal portal = new RandomPortal(portalType,
                                          Randomizer.rand(1000000, 9999999), pos, chrxx.getId(), portalGameType);
                                    chrxx.updateOneInfo(15142, "gameType", "9");
                                    chrxx.send(CField.addPopupSay(9062000, 5000,
                                          "Moon Bunny Event Lair has opened. You must enter before it is defeated to claim current rewards.",
                                          ""));
                                    chrxx.send(CWvsContext
                                          .getScriptProgressMessage(
                                                "Moon Bunny Event Lair has opened. You must enter before it is defeated to claim current rewards."));
                                    chrxx.setRandomPortal(portal);
                                    chrxx.setRandomPortalSpawnedTime(System.currentTimeMillis());
                                    chrxx.send(CField.randomPortalCreated(portal));
                                 }
                              }
                           }
                        }

                        TimeScheduleEntry timeScheduleEntry = ServerConstants.timeScheduleEntry;
                        if (day == 1) {
                           if (hours >= 0 && minutes >= 1 && seconds >= 0
                                 && timeScheduleEntry.getPraiseRankCheck1() != month) {
                              int count = 0;

                              for (GameServer cs : GameServer.getAllInstances()) {
                                 for (MapleCharacter chrxxx : cs.getPlayerStorage().getAllCharacters()) {
                                    if (chrxxx != null && chrxxx.getClient().getSession().isOpen()) {
                                       chrxxx.saveToDB(false, false);
                                       count++;
                                    }
                                 }
                              }

                              timeScheduleEntry.setPraiseRankCheck1(month);
                              timeScheduleEntry.setChange(true);
                              System.out.println("Auto-save completed for a total of " + count + " characters.");
                           } else if (hours >= 0 && minutes >= 3 && seconds >= 0
                                 && timeScheduleEntry.getPraiseRankCheck2() != month && !DBConfig.isGanglim) {
                              PraiseDonationMesoRank.recalculateRanks();
                              PraiseDonationMesoRank.loadRanks();
                              timeScheduleEntry.setPraiseRankCheck2(month);
                              timeScheduleEntry.setChange(true);
                           }
                        }

                        if (dayOfWeek == 5) {
                           if (hours >= 0 && minutes >= 0 && seconds >= 1
                                 && timeScheduleEntry.getWeekQuestCheck() != day) {
                              for (GameServer cs : GameServer.getAllInstances()) {
                                 for (Field map : cs.getMapFactory().getAllMaps()) {
                                    for (MapleCharacter chrxxxx : new ArrayList<>(map.getCharacters())) {
                                       if (chrxxxx != null) {
                                          chrxxxx.updateOneInfo(QuestExConstants.IntensePowerCrystal.getQuestID(),
                                                "count", "180");
                                          if (!DBConfig.isGanglim) {
                                             chrxxxx.updateOneInfo(1234569, "demian_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "demian_clear_single", "");
                                             chrxxxx.updateOneInfo(1234569, "demian_clear_multi", "");
                                             chrxxxx.updateOneInfo(1234569, "swoo_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "swoo_clear_single", "");
                                             chrxxxx.updateOneInfo(1234569, "swoo_clear_multi", "");
                                             chrxxxx.updateOneInfo(1234569, "lucid_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "lucid_clear_single", "");
                                             chrxxxx.updateOneInfo(1234569, "lucid_clear_multi", "");
                                             chrxxxx.updateOneInfo(1234569, "will_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "will_clear_single", "");
                                             chrxxxx.updateOneInfo(1234569, "will_clear_multi", "");
                                             chrxxxx.updateOneInfo(1234569, "hell_demian_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "hell_swoo_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "hell_lucid_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "hell_will_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "hell_dunkel_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "blackmage_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "guardian_angel_slime_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "guardian_angel_slime_clear_single", "");
                                             chrxxxx.updateOneInfo(1234569, "guardian_angel_slime_clear_multi", "");
                                             chrxxxx.updateOneInfo(1234569, "jinhillah_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "jinhillah_clear_single", "");
                                             chrxxxx.updateOneInfo(1234569, "jinhillah_clear_multi", "");
                                             chrxxxx.updateOneInfo(1234569, "chaos_papulatus_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "chaos_papulatus_clear_single", "");
                                             chrxxxx.updateOneInfo(1234569, "chaos_papulatus_clear_multi", "");
                                             chrxxxx.updateOneInfo(1234569, "chaos_zakum_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "chaos_pierre_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "chaos_banban_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "chaos_velum_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "chaos_velum_clear_single", "");
                                             chrxxxx.updateOneInfo(1234569, "chaos_velum_clear_multi", "");
                                             chrxxxx.updateOneInfo(1234569, "chaos_b_queen_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "hard_magnus_clear", "");
                                             chrxxxx.updateOneInfo(1234589, "dunkel_clear", "");
                                             chrxxxx.updateOneInfo(1234589, "dunkel_clear_single", "");
                                             chrxxxx.updateOneInfo(1234589, "dunkel_clear_multi", "");
                                             chrxxxx.updateOneInfo(1234589, "dusk_clear", "");
                                             chrxxxx.updateOneInfo(1234589, "dusk_clear_single", "");
                                             chrxxxx.updateOneInfo(1234589, "dusk_clear_multi", "");
                                             chrxxxx.updateOneInfo(QuestExConstants.ChaosPinkBeen.getQuestID(), "eNum",
                                                   "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.ChaosPinkBeen.getQuestID(),
                                                   "eNum_single", "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.ChaosPinkBeen.getQuestID(),
                                                   "eNum_multi", "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.HardMagnus.getQuestID(), "eNum",
                                                   "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.HardMagnus.getQuestID(),
                                                   "eNum_single", "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.HardMagnus.getQuestID(),
                                                   "eNum_multi", "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.Cygnus.getQuestID(), "eNum", "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.Cygnus.getQuestID(), "eNum_single",
                                                   "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.Cygnus.getQuestID(), "eNum_multi",
                                                   "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.ChaosZakum.getQuestID(), "eNum",
                                                   "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.ChaosZakum.getQuestID(),
                                                   "eNum_single", "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.ChaosZakum.getQuestID(),
                                                   "eNum_multi", "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.ChaosPierre.getQuestID(), "eNum",
                                                   "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.ChaosPierre.getQuestID(),
                                                   "eNum_single", "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.ChaosPierre.getQuestID(),
                                                   "eNum_multi", "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.ChaosVonBon.getQuestID(), "eNum",
                                                   "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.ChaosVonBon.getQuestID(),
                                                   "eNum_single", "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.ChaosVonBon.getQuestID(),
                                                   "eNum_multi", "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.ChaosCrimsonQueen.getQuestID(),
                                                   "eNum", "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.ChaosCrimsonQueen.getQuestID(),
                                                   "eNum_single", "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.ChaosCrimsonQueen.getQuestID(),
                                                   "eNum_multi", "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.ChaosVellum.getQuestID(), "eNum",
                                                   "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.ChaosVellum.getQuestID(),
                                                   "eNum_single", "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.ChaosVellum.getQuestID(),
                                                   "eNum_multi", "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.SerniumSeren.getQuestID(), "clear",
                                                   "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.SerniumSeren.getQuestID(),
                                                   "clear_single", "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.SerniumSeren.getQuestID(),
                                                   "clear_multi", "0");
                                             chrxxxx.updateInfoQuest(
                                                   QuestExConstants.WeeklyQuestResetCount.getQuestID(), "");
                                             chrxxxx.updateOneInfo(1234569, "normal_swoo_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "normal_demian_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "normal_lucid_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "normal_will_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "normal_dusk_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "normal_dunkel_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "hard_demian_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "hard_demian_clear_single", "");
                                             chrxxxx.updateOneInfo(1234569, "hard_demian_clear_multi", "");
                                             chrxxxx.updateOneInfo(1234569, "hard_lucid_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "hard_lucid_clear_single", "");
                                             chrxxxx.updateOneInfo(1234569, "hard_lucid_clear_multi", "");
                                             chrxxxx.updateOneInfo(1234569, "hard_will_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "hard_will_clear_single", "");
                                             chrxxxx.updateOneInfo(1234569, "hard_will_clear_multi", "");
                                             chrxxxx.updateOneInfo(1234569, "chaos_dusk_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "chaos_dusk_clear_single", "");
                                             chrxxxx.updateOneInfo(1234569, "chaos_dusk_clear_multi", "");
                                             chrxxxx.updateOneInfo(1234569, "hard_dunkel_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "hard_dunkel_clear_single", "");
                                             chrxxxx.updateOneInfo(1234569, "hard_dunkel_clear_multi", "");
                                             chrxxxx.updateOneInfo(1234569, "hard_swoo_clear", "");
                                             chrxxxx.updateOneInfo(1234569, "hard_swoo_clear_single", "");
                                             chrxxxx.updateOneInfo(1234569, "hard_swoo_clear_multi", "");
                                          }

                                          if (!DBConfig.isGanglim) {
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoCoreEvent.getQuestID(),
                                                   "reward_chaos_zakum", "");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoCoreEvent.getQuestID(),
                                                   "reward_chaos_pierre", "");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoCoreEvent.getQuestID(),
                                                   "reward_chaos_banban", "");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoCoreEvent.getQuestID(),
                                                   "reward_b_queen", "");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoCoreEvent.getQuestID(),
                                                   "reward_hard_magnus", "");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoCoreEvent.getQuestID(),
                                                   "reward_chaos_velum", "");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoCoreEvent.getQuestID(),
                                                   "reward_chaos_papulatus", "");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoCoreEvent.getQuestID(),
                                                   "reward_normal_swoo", "");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoCoreEvent.getQuestID(),
                                                   "reward_normal_demian", "");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoCoreEvent.getQuestID(),
                                                   "reward_normal_lucid", "");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoCoreEvent.getQuestID(),
                                                   "reward_normal_will", "");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoCoreEvent.getQuestID(),
                                                   "reward_normal_dusk", "");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoCoreEvent.getQuestID(),
                                                   "reward_normal_dunkel", "");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoCoreEvent.getQuestID(),
                                                   "reward_hard_demian", "");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoCoreEvent.getQuestID(),
                                                   "reward_hard_swoo", "");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoCoreEvent.getQuestID(),
                                                   "reward_hard_lucid", "");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoCoreEvent.getQuestID(),
                                                   "reward_hard_will", "");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoCoreEvent.getQuestID(),
                                                   "reward_chaos_dusk", "");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoCoreEvent.getQuestID(),
                                                   "reward_hard_dunkel", "");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoCoreEvent.getQuestID(),
                                                   "reward_jinhillah", "");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoCoreEvent.getQuestID(), "week",
                                                   "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoCoreEvent.getQuestID(), "lock",
                                                   "0");
                                          }

                                          if (!DBConfig.isGanglim) {
                                             int count = chrxxxx.getOneInfoQuestInteger(19770, "count");
                                             if (count > 0 && chrxxxx.getOneInfoQuestInteger(19770, "active") == 0) {
                                                chrxxxx.updateOneInfo(19770, "count", String.valueOf(count - 1));
                                             }
                                          }

                                          chrxxxx.updateOneInfo(1234569, "ride_vehicle_select", "");
                                          chrxxxx.updateOneInfo(1234571, "swoo_week_quest", "");
                                          chrxxxx.removeWeeklyQuest(1234571);
                                          chrxxxx.updateOneInfo(1234572, "demian_week_quest", "");
                                          chrxxxx.removeWeeklyQuest(1234572);
                                          chrxxxx.setStackEventGauge();

                                          for (int j = 0; j < 6; j++) {
                                             int itemID = 2431968 + j;
                                             if (DBConfig.isGanglim) {
                                                chrxxxx.updateOneInfo(1234569, "use_" + itemID, "0");
                                             } else {
                                                chrxxxx.updateOneInfo(1234569, "use_" + itemID + "_single", "0");
                                                chrxxxx.updateOneInfo(1234569, "use_" + itemID + "_multi", "0");
                                             }
                                          }

                                          MapleQuest quest = MapleQuest.getInstance(100717);
                                          MapleQuestStatus status = chrxxxx.getQuest(quest);
                                          if (chrxxxx.getOneInfoQuestInteger(
                                                QuestExConstants.NeoEventAdventureLog.getQuestID(), "start") == 1) {
                                             chrxxxx.updateQuest(
                                                   new MapleQuestStatus(MapleQuest.getInstance(100717), 0));
                                             chrxxxx.updateQuest(new MapleQuestStatus(MapleQuest
                                                   .getInstance(QuestExConstants.NeoEventNormalMob.getQuestID()), 0));
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoEventAdventureLog.getQuestID(),
                                                   "start", "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoEventAdventureLog.getQuestID(),
                                                   "mission", "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoEventAdventureLog.getQuestID(),
                                                   "state", "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoEventNormalMob.getQuestID(),
                                                   "m0", "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoEventEliteMob.getQuestID(), "m1",
                                                   "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoEventRuneAct.getQuestID(),
                                                   "RunAct", "0");
                                             chrxxxx.updateOneInfo(QuestExConstants.NeoEventRandomPortal.getQuestID(),
                                                   "count", "0");
                                             if (chrxxxx.getQuestStatus(
                                                   QuestExConstants.NeoEventEliteMob.getQuestID()) > 0) {
                                                chrxxxx.updateQuest(new MapleQuestStatus(MapleQuest
                                                      .getInstance(QuestExConstants.NeoEventEliteMob.getQuestID()), 0));
                                             }

                                             if (chrxxxx
                                                   .getQuestStatus(QuestExConstants.NeoEventRuneAct.getQuestID()) > 0) {
                                                chrxxxx.updateQuest(new MapleQuestStatus(MapleQuest
                                                      .getInstance(QuestExConstants.NeoEventRuneAct.getQuestID()), 0));
                                             }

                                             if (chrxxxx.getQuestStatus(
                                                   QuestExConstants.NeoEventRandomPortal.getQuestID()) > 0) {
                                                chrxxxx.updateQuest(
                                                      new MapleQuestStatus(
                                                            MapleQuest.getInstance(
                                                                  QuestExConstants.NeoEventRandomPortal.getQuestID()),
                                                            0));
                                             }
                                          }

                                          Calendar cal = Calendar.getInstance();
                                          cal.setTimeInMillis(System.currentTimeMillis());
                                          int currentWeek = cal.get(3);
                                          chrxxxx.updateOneInfo(1234570, "lastWeek5", String.valueOf(currentWeek));
                                          chrxxxx.updateOneInfo(QuestExConstants.IntensePowerCrystal.getQuestID(),
                                                "lastWeek5", String.valueOf(currentWeek));
                                       }
                                    }
                                 }
                              }

                              timeScheduleEntry.setWeekQuestCheck(day);
                              timeScheduleEntry.setChange(true);
                           }
                        } else if (dayOfWeek == 0) {
                           if (!DBConfig.isGanglim) {
                              if (hours == 20 && minutes == 59 && seconds == 50) {
                                 for (GameServer cs : GameServer.getAllInstances()) {
                                    for (Field map : cs.getMapFactory().getAllMaps()) {
                                       for (MapleCharacter chrxxxxx : new ArrayList<>(map.getCharacters())) {
                                          if (chrxxxxx != null) {
                                             chrxxxxx.send(CField.addPopupSay(9062000, 3000,
                                                   "Fairy Bros' Golden Box item distribution is in 10 seconds.", ""));
                                             chrxxxxx.dropMessage(5,
                                                   "Fairy Bros' Golden Box item distribution is in 10 seconds.");
                                          }
                                       }
                                    }
                                 }
                              }

                              if (hours == 21 && minutes == 0 && seconds == 0) {
                                 for (GameServer cs : GameServer.getAllInstances()) {
                                    for (Field map : cs.getMapFactory().getAllMaps()) {
                                       for (MapleCharacter chrxxxxxx : new ArrayList<>(map.getCharacters())) {
                                          if (chrxxxxxx != null) {
                                             chrxxxxxx.send(
                                                   CField.addPopupSay(9062000, 3000,
                                                         "Fairy Bros' Golden Box items have been distributed.", ""));
                                             chrxxxxxx.dropMessage(5,
                                                   "Fairy Bros' Golden Box items have been distributed. "
                                                         + (DBConfig.isGanglim ? "Donation Cash" : "Advent Point")
                                                         + " Available in the Shop.");
                                          }
                                       }
                                    }
                                 }
                              }
                           }

                           if (hours >= 23 && minutes >= 59 && seconds >= 1
                                 && timeScheduleEntry.getDojangRankCheck1() != day) {
                              for (GameServer cs : GameServer.getAllInstances()) {
                                 for (Field map : cs.getMapFactory().getAllMaps()) {
                                    for (MapleCharacter chrxxxxxxx : new ArrayList<>(map.getCharacters())) {
                                       if (chrxxxxxxx != null) {
                                          Item item = null;
                                          chrxxxxxxx.getInventory(MapleInventoryType.SETUP)
                                                .list()
                                                .stream()
                                                .collect(Collectors.toList())
                                                .forEach(
                                                      i -> {
                                                         if (i.getItemId() == 3700525 || i.getItemId() == 3700526
                                                               || i.getItemId() == 3700307
                                                               || i.getItemId() == 3700308) {
                                                            MapleInventoryManipulator.removeFromSlot(
                                                                  chrxxxxxxx.getClient(), MapleInventoryType.SETUP,
                                                                  i.getPosition(), i.getQuantity(), false, false);
                                                         }
                                                      });
                                          if (chrxxxxxxx.getStorage() != null) {
                                             chrxxxxxxx.getStorage().removeById(3700525);
                                             chrxxxxxxx.getStorage().removeById(3700526);
                                             chrxxxxxxx.getStorage().removeById(3700307);
                                             chrxxxxxxx.getStorage().removeById(3700308);
                                          }
                                       }
                                    }
                                 }
                              }

                              PreparedStatement ps = null;

                              try (Connection con = DBConnection.getConnection()) {
                                 ps = con.prepareStatement(
                                       "DELETE FROM `inventoryitems` WHERE itemid = ? or itemid = ? or itemid = ? or itemid = ?");
                                 ps.setInt(1, 3700525);
                                 ps.setInt(2, 3700526);
                                 ps.setInt(3, 3700308);
                                 ps.setInt(4, 3700307);
                                 ps.executeUpdate();
                              } catch (SQLException var150) {
                                 var150.printStackTrace();
                              } finally {
                                 try {
                                    if (ps != null) {
                                       ps.close();
                                       PreparedStatement var172 = null;
                                    }
                                 } catch (SQLException var137) {
                                    var137.printStackTrace();
                                 }
                              }

                              timeScheduleEntry.setDojangRankCheck1(day);
                              timeScheduleEntry.setChange(true);
                           }
                        } else if (dayOfWeek == 1) {
                           if (!DBConfig.isGanglim) {
                              if (hours == 14 && minutes == 59 && seconds == 50) {
                                 for (GameServer cs : GameServer.getAllInstances()) {
                                    for (Field map : cs.getMapFactory().getAllMaps()) {
                                       for (MapleCharacter chrxxxxxxxx : new ArrayList<>(map.getCharacters())) {
                                          if (chrxxxxxxxx != null) {
                                             chrxxxxxxxx.send(CField.addPopupSay(9062000, 3000,
                                                   "Fairy Bros' Golden Box item distribution is in 10 seconds.", ""));
                                             chrxxxxxxxx.dropMessage(5,
                                                   "Fairy Bros' Golden Box item distribution is in 10 seconds.");
                                          }
                                       }
                                    }
                                 }
                              }

                              if (hours == 15 && minutes == 0 && seconds == 0) {
                                 for (GameServer cs : GameServer.getAllInstances()) {
                                    for (Field map : cs.getMapFactory().getAllMaps()) {
                                       for (MapleCharacter chrxxxxxxxxx : new ArrayList<>(map.getCharacters())) {
                                          if (chrxxxxxxxxx != null) {
                                             chrxxxxxxxxx.send(
                                                   CField.addPopupSay(9062000, 3000,
                                                         "Fairy Bros' Golden Box items have been distributed.", ""));
                                             chrxxxxxxxxx.dropMessage(5,
                                                   "Fairy Bros' Golden Box items have been distributed. "
                                                         + (DBConfig.isGanglim ? "Donation Cash" : "Advent Point")
                                                         + " Available in the Shop.");
                                          }
                                       }
                                    }
                                 }
                              }
                           }

                           if (hours >= 0 && minutes >= 0 && seconds >= 1
                                 && timeScheduleEntry.getDojangRankCheck2() != day) {
                              Calendar cal = Calendar.getInstance();
                              cal.setTimeInMillis(System.currentTimeMillis());
                              int currentWeek = cal.get(3);
                              int weekYear = cal.getWeekYear();
                              int lastYear = weekYear;
                              int var227;
                              if (currentWeek == 1) {
                                 var227 = 52;
                                 lastYear = weekYear - 1;
                              } else {
                                 var227 = currentWeek - 1;
                              }

                              DojangRanking.saveRanks(var227, lastYear);
                              timeScheduleEntry.setDojangRankCheck2(day);
                              timeScheduleEntry.setChange(true);
                           }

                           if (hours >= 0 && minutes >= 0 && seconds >= 30
                                 && timeScheduleEntry.getDojangRankCheck3() != day) {
                              DojangRanking.nextWeekend();
                              timeScheduleEntry.setDojangRankCheck3(day);
                              timeScheduleEntry.setChange(true);
                           }

                           if (hours >= 0 && minutes >= 1 && seconds >= 1
                                 && timeScheduleEntry.getDojangRankCheck4() != day) {
                              DojangRanking.calculateRankerReward();
                              timeScheduleEntry.setDojangRankCheck4(day);
                              timeScheduleEntry.setChange(true);
                           }
                        } else if (dayOfWeek == 2 && hours >= 0 && minutes >= 0 && seconds >= 1
                              && timeScheduleEntry.getDreamBreakerRankCheck() != day) {
                           for (GameServer cs : GameServer.getAllInstances()) {
                              for (Field map : cs.getMapFactory().getAllMaps()) {
                                 for (MapleCharacter chrxxxxxxxxxx : new ArrayList<>(map.getCharacters())) {
                                    if (chrxxxxxxxxxx != null) {
                                       if (DBConfig.isGanglim) {
                                          chrxxxxxxxxxx.getClient().removeKeyValue("HgradeWeek");
                                       }

                                       if (DBConfig.isGanglim) {
                                          chrxxxxxxxxxx.updateOneInfo(1234569, "hell_lucid_clear", "");
                                          chrxxxxxxxxxx.updateOneInfo(1234569, "hell_demian_clear", "");
                                          chrxxxxxxxxxx.updateOneInfo(1234569, "hell_swoo_clear", "");
                                          chrxxxxxxxxxx.updateOneInfo(1234569, "hell_will_clear", "");
                                          chrxxxxxxxxxx.updateOneInfo(1234569, "hell_dunkel_clear", "");
                                          int count = chrxxxxxxxxxx.getOneInfoQuestInteger(19770, "count");
                                          if (count > 0 && chrxxxxxxxxxx.getOneInfoQuestInteger(19770, "active") == 0) {
                                             chrxxxxxxxxxx.updateOneInfo(19770, "count", String.valueOf(0));
                                          }
                                       }

                                       int[] weekQuests = new int[] { 26000, 34151 };

                                       for (int weekQuest : weekQuests) {
                                          if (chrxxxxxxxxxx.getQuestStatus(weekQuest) > 0) {
                                             chrxxxxxxxxxx.updateQuest(
                                                   new MapleQuestStatus(MapleQuest.getInstance(weekQuest), 0));
                                          }
                                       }

                                       UnmodifiableIterator var275 = WeeklyQuests.dailyQuests.keySet().iterator();

                                       while (var275.hasNext()) {
                                          int wQuest = (Integer) var275.next();
                                          if (chrxxxxxxxxxx.getQuestStatus(wQuest) > 0) {
                                             if (wQuest == 39165) {
                                                chrxxxxxxxxxx.updateOneInfo(39100, "FC", "0");
                                                chrxxxxxxxxxx.updateOneInfo(39105, "start", "0");
                                                chrxxxxxxxxxx.updateOneInfo(39105, "NpcSpeech", "0");
                                                chrxxxxxxxxxx.updateOneInfo(39106, "start", "0");
                                                chrxxxxxxxxxx.updateOneInfo(39106, "NpcSpeech", "0");
                                                chrxxxxxxxxxx.updateOneInfo(39107, "start", "0");
                                                chrxxxxxxxxxx.updateOneInfo(39107, "NpcSpeech", "0");
                                                chrxxxxxxxxxx.updateOneInfo(39108, "start", "0");
                                                chrxxxxxxxxxx.updateOneInfo(39108, "NpcSpeech", "0");
                                                chrxxxxxxxxxx.updateOneInfo(39116, "success", "0");
                                                chrxxxxxxxxxx.updateOneInfo(39125, "success", "0");
                                                chrxxxxxxxxxx.updateOneInfo(39152, "success", "0");
                                                chrxxxxxxxxxx.updateOneInfo(39161, "start", "0");
                                                chrxxxxxxxxxx.updateOneInfo(39162, "start", "0");
                                                chrxxxxxxxxxx.updateOneInfo(39163, "start", "0");
                                                chrxxxxxxxxxx.updateOneInfo(39164, "start", "0");
                                             } else if (wQuest == 39002) {
                                                chrxxxxxxxxxx.updateOneInfo(15708, "cq", "0");
                                             }

                                             chrxxxxxxxxxx.updateQuest(
                                                   new MapleQuestStatus(MapleQuest.getInstance(wQuest), 0));

                                             for (int wq : WeeklyQuests.dailyQuests.get(wQuest)) {
                                                chrxxxxxxxxxx
                                                      .updateQuest(new MapleQuestStatus(MapleQuest.getInstance(wq), 0));
                                             }
                                          }
                                       }

                                       int rank = DreamBreakerRank.getRank(chrxxxxxxxxxx.getName());
                                       if (rank > 0) {
                                          chrxxxxxxxxxx.updateOneInfo(20200128, "last_week_dream_breaker", fDate);
                                       }

                                       chrxxxxxxxxxx.send(CWvsContext.onCharacterModified(chrxxxxxxxxxx, -1L));
                                       HyperHandler.updateSkills(chrxxxxxxxxxx, 0);
                                       chrxxxxxxxxxx.updateMatrixSkillsNoLock();
                                       Calendar cal = Calendar.getInstance();
                                       cal.setTimeInMillis(System.currentTimeMillis());
                                       int currentWeek = cal.get(3);
                                       chrxxxxxxxxxx.updateOneInfo(1234570, "lastWeek2", String.valueOf(currentWeek));
                                       chrxxxxxxxxxx.updateOneInfo(QuestExConstants.IntensePowerCrystal.getQuestID(),
                                             "lastWeek2", String.valueOf(currentWeek));
                                    }
                                 }
                              }
                           }

                           DreamBreakerRank.clearRank();
                           timeScheduleEntry.setDreamBreakerRankCheck(day);
                           timeScheduleEntry.setChange(true);
                           DamageMeasurementRank.resetRank();

                           for (GameServer cs : GameServer.getAllInstances()) {
                              for (Field map : cs.getMapFactory().getAllMaps()) {
                                 for (MapleCharacter chrxxxxxxxxxxx : new ArrayList<>(map.getCharacters())) {
                                    if (chrxxxxxxxxxxx != null) {
                                       DamageMeasurementRank.applyDamageRankBuff(chrxxxxxxxxxxx);
                                    }
                                 }
                              }
                           }

                           for (MapleCharacter chrxxxxxxxxxxxx : CashShopServer.getPlayerStorage().getAllCharacters()) {
                              if (chrxxxxxxxxxxxx != null) {
                                 DamageMeasurementRank.applyDamageRankBuff(chrxxxxxxxxxxxx);
                              }
                           }

                           for (MapleCharacter chrxxxxxxxxxxxxx : AuctionServer.getPlayerStorage().getAllCharacters()) {
                              if (chrxxxxxxxxxxxxx != null) {
                                 DamageMeasurementRank.applyDamageRankBuff(chrxxxxxxxxxxxxx);
                              }
                           }

                           Center.Broadcast
                                 .broadcastMessage(CField.chatMsg(5,
                                       "Combat Power Ranking has been reset, and ranking rewards/buffs have been distributed."));
                           System.out.println("Combat Power Ranking has been reset.");
                        }

                        if (dayOfWeek != 0 && dayOfWeek != 1) {
                           if (hours >= 21 && minutes >= 0 && seconds >= 1
                                 && timeScheduleEntry.getHottimeCheck() != day) {
                              Center.gainItemExpiration(DBConfig.isGanglim ? 2435885 : 2433424, -1, (short) 1);
                              Center.hottime = dayOfWeek + "2100";
                              timeScheduleEntry.setHottimeCheck(day);
                              timeScheduleEntry.setChange(true);
                           }
                        } else if (hours >= 21 && minutes >= 0 && seconds >= 1
                              && timeScheduleEntry.getHottimeCheck() != day) {
                           Center.gainItemExpiration(DBConfig.isGanglim ? 2435885 : 2435885, -1, (short) 1);
                           Center.hottime = dayOfWeek + "2100";
                           timeScheduleEntry.setHottimeCheck(day);
                           timeScheduleEntry.setChange(true);
                        }

                        if (hours >= 0 && minutes >= 0 && seconds >= 0
                              && timeScheduleEntry.getDailyGiftCheck() != day) {
                           for (GameServer cs : GameServer.getAllInstances()) {
                              for (Field map : cs.getMapFactory().getAllMaps()) {
                                 for (MapleCharacter chrxxxxxxxxxxxxxx : new ArrayList<>(map.getCharacters())) {
                                    if (chrxxxxxxxxxxxxxx != null) {
                                       AchievementFactory.resetDayChange(chrxxxxxxxxxxxxxx);
                                       if (chrxxxxxxxxxxxxxx.getLevel() >= 33) {
                                          if (day == 1) {
                                             MapleDailyGiftInfo gift = chrxxxxxxxxxxxxxx.getDailyGift();
                                             gift.setDailyDay(0);
                                             gift.setDailyCount(0);
                                             chrxxxxxxxxxxxxxx.send(CField.getDailyGiftRecord(
                                                   "count=0;day=0;date=" + CurrentTime.getCurrentTime2()));
                                             chrxxxxxxxxxxxxxx.updateInfoQuest(16700,
                                                   "count=0;day=0;date=" + CurrentTime.getCurrentTime2());
                                          } else {
                                             String value = chrxxxxxxxxxxxxxx.getOneInfoQuest(16700, "count");
                                             if (value != null && !value.isEmpty()) {
                                                chrxxxxxxxxxxxxxx.updateInfoQuest(
                                                      16700,
                                                      "count=0;day=" + chrxxxxxxxxxxxxxx.getDailyGift().getDailyDay()
                                                            + ";date=" + CurrentTime.getCurrentTime2());
                                             } else {
                                                chrxxxxxxxxxxxxxx.updateInfoQuest(16700,
                                                      "count=0;day=0;date=" + CurrentTime.getCurrentTime2());
                                             }

                                             chrxxxxxxxxxxxxxx.getDailyGift().setDailyCount(0);
                                             chrxxxxxxxxxxxxxx.getDailyGift()
                                                   .saveDailyGift(
                                                         chrxxxxxxxxxxxxxx.getAccountID(),
                                                         chrxxxxxxxxxxxxxx.getDailyGift().getDailyDay(),
                                                         chrxxxxxxxxxxxxxx.getDailyGift().getDailyCount(),
                                                         chrxxxxxxxxxxxxxx.getDailyGift().getDailyData());
                                             chrxxxxxxxxxxxxxx.send(
                                                   CField.getDailyGiftRecord(
                                                         "count="
                                                               + chrxxxxxxxxxxxxxx.getDailyGift().getDailyCount()
                                                               + ";day="
                                                               + chrxxxxxxxxxxxxxx.getDailyGift().getDailyDay()
                                                               + ";date="
                                                               + GameConstants.getCurrentDate_NoTime()));
                                          }
                                       }

                                       if (chrxxxxxxxxxxxxxx.getLevel() >= 101 && chrxxxxxxxxxxxxxx
                                             .getQuestStatus(QuestExConstants.HasteEventInit.getQuestID()) >= 1) {
                                          String[] customData = new String[] { "", "", "", "", "count=0", "count=0",
                                                "RunAct=0", "suddenMK=0" };

                                          for (int i = QuestExConstants.HasteEventInit
                                                .getQuestID(); i <= QuestExConstants.HasteEventSuddenMK
                                                      .getQuestID(); i++) {
                                             if (i != QuestExConstants.HasteEventEliteBoss.getQuestID()) {
                                                MapleQuest.getInstance(i).forceStart(chrxxxxxxxxxxxxxx, 9010010, "");
                                                chrxxxxxxxxxxxxxx.updateInfoQuest(i,
                                                      customData[i - QuestExConstants.HasteEventInit.getQuestID()]);
                                             }
                                          }

                                          chrxxxxxxxxxxxxxx.updateInfoQuest(
                                                QuestExConstants.HasteEvent.getQuestID(),
                                                "M1=0;M2=0;M3=0;M4=0;M5=0;M6=0;date=21/11/03;booster=0;openBox=0;unlockBox=0;str=Stage 1 Box Challenge! Complete 1 Daily Mission!");
                                       }

                                       if (chrxxxxxxxxxxxxxx.getMapleUnion() != null) {
                                          chrxxxxxxxxxxxxxx.updateOneInfo(18790, "damage", "0");
                                          if (chrxxxxxxxxxxxxxx.getFieldUnion() != null) {
                                             chrxxxxxxxxxxxxxx.getFieldUnion().updateTotalDamage();
                                          }
                                       }

                                       chrxxxxxxxxxxxxxx.updateOneInfo(1211345, "get_meso", "0");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(1211345, "today", "0");
                                       if (!DBConfig.isGanglim) {
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234570, "lastDay", String.valueOf(day));
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1068, "lastDay", String.valueOf(day));
                                       }

                                       chrxxxxxxxxxxxxxx.setTodayContribution(0);
                                       chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.SerniumSeren.getQuestID(),
                                             "practice", "0");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.SerniumSeren.getQuestID(),
                                             "enter", "0");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(1234567, "daily1", "");
                                       chrxxxxxxxxxxxxxx.removeMobQuest(1400);
                                       chrxxxxxxxxxxxxxx.updateOneInfo(1234567, "daily2", "");
                                       chrxxxxxxxxxxxxxx.removeMobQuest(1401);
                                       chrxxxxxxxxxxxxxx.updateOneInfo(1234568, "arkana", "");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(1234579, "clear_mPark", "");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(1235858, "praise", "");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(501045, "mp", "");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(1234567, "praise_hongbo", "");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(1234567, "jumpMap", "");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(1234569, "hell_boss_count", "");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(1234567, "UnionMiniGame_Count", "");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(1234567, "UnionMiniGame_Q", "");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(1235859, "praise_dailyQuest_ClearCount", "");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(1235859, "praise_dailyQuest_MobCount", "");
                                       if (DBConfig.isGanglim) {
                                          for (int ix = 0; ix <= 14; ix++) {
                                             chrxxxxxxxxxxxxxx.updateOneInfo(100778, ix + "_buy_count", "0");
                                          }
                                       } else {
                                          for (int ix = 0; ix <= 14; ix++) {
                                             chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.KillPoint.getQuestID(),
                                                   ix + "_buy_count", "0");
                                          }
                                       }

                                       chrxxxxxxxxxxxxxx.getTraits().forEach((key, value) -> value.setTodayExp(0));
                                       int lastDay = chrxxxxxxxxxxxxxx.getOneInfoQuestInteger(1234570, "lastDay");
                                       if (DBConfig.isGanglim && lastDay != day) {
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234570, "lastDay", String.valueOf(day));
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1068, "lastDay", String.valueOf(day));
                                          chrxxxxxxxxxxxxxx.getClient().setKeyValue("day_qitem", "0");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234569, "miniGame4_count", "0");
                                          QuestExConstants.bossQuests.forEach((key, value) -> {
                                             if (value > 0) {
                                                if (chrxxxxxxxxxxxxxx.getOneInfoQuestInteger(value, "mobid") > 0) {
                                                   chrxxxxxxxxxxxxxx.updateOneInfo(value, "mobid", "");
                                                }

                                                if (chrxxxxxxxxxxxxxx.getOneInfoQuestInteger(value, "mobDead") > 0) {
                                                   chrxxxxxxxxxxxxxx.updateOneInfo(value, "mobDead", "");
                                                }
                                             }
                                          });

                                          for (int ix = 0; ix < 2; ix++) {
                                             chrxxxxxxxxxxxxxx.updateOneInfo(1234569 + ix, "demian_clear", "");
                                             chrxxxxxxxxxxxxxx.updateOneInfo(1234569 + ix, "swoo_clear", "");
                                             chrxxxxxxxxxxxxxx.updateOneInfo(1234569 + ix, "lucid_clear", "");
                                             chrxxxxxxxxxxxxxx.updateOneInfo(1234569 + ix, "will_clear", "");
                                             chrxxxxxxxxxxxxxx.updateOneInfo(1234569 + ix, "tengu_clear", "");
                                             chrxxxxxxxxxxxxxx.updateOneInfo(1234569 + ix, "normal_mitsuhide_clear",
                                                   "");
                                             chrxxxxxxxxxxxxxx.updateOneInfo(1234569 + ix, "hard_mitsuhide_clear", "");
                                             chrxxxxxxxxxxxxxx.updateOneInfo(1234569 + ix, "guardian_angel_slime_clear",
                                                   "");
                                             chrxxxxxxxxxxxxxx.updateOneInfo(1234569 + ix, "jinhillah_clear", "");
                                             chrxxxxxxxxxxxxxx.updateOneInfo(1234569 + ix, "chaos_papulatus_clear", "");
                                             chrxxxxxxxxxxxxxx.updateOneInfo(1234569 + ix, "chaos_zakum_clear", "");
                                             chrxxxxxxxxxxxxxx.updateOneInfo(1234569 + ix, "chaos_pierre_clear", "");
                                             chrxxxxxxxxxxxxxx.updateOneInfo(1234569 + ix, "chaos_banban_clear", "");
                                             chrxxxxxxxxxxxxxx.updateOneInfo(1234569 + ix, "chaos_velum_clear", "");
                                             chrxxxxxxxxxxxxxx.updateOneInfo(1234569 + ix, "chaos_b_queen_clear", "");
                                             chrxxxxxxxxxxxxxx.updateOneInfo(1234569 + ix, "hard_magnus_clear", "");
                                             chrxxxxxxxxxxxxxx.updateOneInfo(1234569 + ix, "akairum_clear", "");
                                          }

                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234569, "pinkbean_clear", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234569, "vonleon_clear", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234570, "vonleon_clear", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234571, "vonleon_clear", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.SerniumSeren.getQuestID(),
                                                "clear", "0");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234569, "dunkel_clear", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234589, "dunkel_clear", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234590, "dusk_clear", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234589, "dusk_clear", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234570, "blackmage_clear", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.ChaosPinkBeen.getQuestID(),
                                                "eNum", "0");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.HardMagnus.getQuestID(),
                                                "eNum", "0");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.Cygnus.getQuestID(), "eNum",
                                                "0");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.ChaosZakum.getQuestID(),
                                                "eNum", "0");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.ChaosPierre.getQuestID(),
                                                "eNum", "0");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.ChaosVonBon.getQuestID(),
                                                "eNum", "0");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(
                                                QuestExConstants.ChaosCrimsonQueen.getQuestID(), "eNum", "0");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.ChaosVellum.getQuestID(),
                                                "eNum", "0");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234569, "normal_swoo_clear", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234569, "normal_demian_clear", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234569, "normal_lucid_clear", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234569, "normal_dusk_clear", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234569, "normal_dunkel_clear", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234569, "hard_demian_clear", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234569, "hard_lucid_clear", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234569, "hard_will_clear", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234569, "chaos_dusk_clear", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234569, "hard_dunkel_clear", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234569, "normal_will_clear", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234569, "hard_swoo_clear", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234569, "ResetBoss", "0");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234569, "OffsetCount", "0");
                                       }

                                       UnmodifiableIterator var279 = DailyQuests.dailyQuests.keySet().iterator();

                                       while (var279.hasNext()) {
                                          int arcaneDailyQuest = (Integer) var279.next();
                                          if (chrxxxxxxxxxxxxxx.getQuestStatus(arcaneDailyQuest) > 0) {
                                             chrxxxxxxxxxxxxxx.updateQuest(
                                                   new MapleQuestStatus(MapleQuest.getInstance(arcaneDailyQuest), 0));

                                             for (Integer DailyQuest : DailyQuests.dailyQuests.get(arcaneDailyQuest)) {
                                                chrxxxxxxxxxxxxxx.updateQuest(
                                                      new MapleQuestStatus(MapleQuest.getInstance(DailyQuest), 0));
                                             }

                                             chrxxxxxxxxxxxxxx
                                                   .send(CWvsContext.onCharacterModified(chrxxxxxxxxxxxxxx, -1L));
                                          }
                                       }

                                       HyperHandler.updateSkills(chrxxxxxxxxxxxxxx, 0);
                                       chrxxxxxxxxxxxxxx.updateMatrixSkillsNoLock();
                                       if (chrxxxxxxxxxxxxxx.getQuestStatus(16011) > 0) {
                                          chrxxxxxxxxxxxxxx
                                                .updateQuest(new MapleQuestStatus(MapleQuest.getInstance(16011), 0));
                                       }

                                       if (chrxxxxxxxxxxxxxx.getQuestStatus(16012) > 0) {
                                          chrxxxxxxxxxxxxxx
                                                .updateQuest(new MapleQuestStatus(MapleQuest.getInstance(16012), 0));
                                       }

                                       chrxxxxxxxxxxxxxx.updateOneInfo(100711, "today", "0");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(100712, "today", "0");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(100711, "lock", "0");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(100712, "lock", "0");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(1234567, "buy_lp_2", "0");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(1234567, "buy_lp_3", "0");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(1234567, "buy_lp_4", "0");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(1234567, "buy_lp_5", "0");
                                       chrxxxxxxxxxxxxxx.setStackEventGauge();
                                       chrxxxxxxxxxxxxxx.updateOneInfo(1234569, "papulatus_clear", "");
                                       if (!DBConfig.isGanglim) {
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234569, "papulatus_clear_single", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1234569, "papulatus_clear_multi", "");
                                       }

                                       chrxxxxxxxxxxxxxx.updateOneInfo(1234569, "zakum_clear", "");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.Zakum.getQuestID(), "eNum",
                                             "0");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.Horntail.getQuestID(), "eNum",
                                             "0");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.PinkBeen.getQuestID(), "eNum",
                                             "0");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.Arkarium.getQuestID(), "eNum",
                                             "0");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.Magnus.getQuestID(), "eNum",
                                             "0");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.VonLeon.getQuestID(), "eNum",
                                             "0");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.Hillah.getQuestID(), "eNum",
                                             "0");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.Pierre.getQuestID(), "eNum",
                                             "0");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.VonBon.getQuestID(), "eNum",
                                             "0");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.CrimsonQueen.getQuestID(),
                                             "eNum", "0");
                                       chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.Vellum.getQuestID(), "eNum",
                                             "0");
                                       if (!DBConfig.isGanglim) {
                                          chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.PinkBeen.getQuestID(),
                                                "eNum_single", "0");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.PinkBeen.getQuestID(),
                                                "eNum_multi", "0");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.Pierre.getQuestID(),
                                                "eNum_single", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.Pierre.getQuestID(),
                                                "eNum_multi", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.CrimsonQueen.getQuestID(),
                                                "eNum_single", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.CrimsonQueen.getQuestID(),
                                                "eNum_multi", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.VonBon.getQuestID(),
                                                "eNum_single", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.VonBon.getQuestID(),
                                                "eNum_multi", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.Vellum.getQuestID(),
                                                "eNum_single", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.Vellum.getQuestID(),
                                                "eNum_multi", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.Arkarium.getQuestID(),
                                                "eNum_single", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.Arkarium.getQuestID(),
                                                "eNum_multi", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.VonLeon.getQuestID(),
                                                "eNum_single", "0");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.VonLeon.getQuestID(),
                                                "eNum_multi", "0");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.Horntail.getQuestID(),
                                                "eNum_single", "0");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(QuestExConstants.Horntail.getQuestID(),
                                                "eNum_multi", "0");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(1235859, "eliteMonster_Count", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(7003, "Single", "");
                                          chrxxxxxxxxxxxxxx.updateOneInfo(7003, "Multi", "");
                                          chrxxxxxxxxxxxxxx.updateInfoQuest(
                                                QuestExConstants.DailyQuestResetCount.getQuestID(), "");
                                       }

                                       if (chrxxxxxxxxxxxxxx.getQuestStatus(3528) > 0) {
                                          chrxxxxxxxxxxxxxx
                                                .updateQuest(new MapleQuestStatus(MapleQuest.getInstance(3528), 0));
                                       }
                                    }
                                 }
                              }
                           }

                           PreparedStatement ps = null;

                           try (Connection con = DBConnection.getConnection()) {
                              ps = con.prepareStatement("UPDATE `characters` SET `todayContribution` = ?");
                              ps.setInt(1, 0);
                              ps.executeUpdate();
                           } catch (SQLException var147) {
                              var147.printStackTrace();
                           } finally {
                              try {
                                 if (ps != null) {
                                    ps.close();
                                    PreparedStatement var182 = null;
                                 }
                              } catch (SQLException var136) {
                                 var136.printStackTrace();
                              }
                           }

                           if (day == 1) {
                              ps = null;

                              try (Connection con = DBConnection.getConnection()) {
                                 ps = con.prepareStatement("DELETE FROM `dailygift`");
                                 ps.executeUpdate();
                              } catch (SQLException var144) {
                                 var144.printStackTrace();
                              } finally {
                                 try {
                                    if (ps != null) {
                                       ps.close();
                                       PreparedStatement var184 = null;
                                    }
                                 } catch (SQLException var135) {
                                    var135.printStackTrace();
                                 }
                              }
                           }

                           timeScheduleEntry.setDailyGiftCheck(day);
                           timeScheduleEntry.setChange(true);
                        }

                        Center.lastAutoHottimeTime = System.currentTimeMillis();
                     }
                  },
                  1000L);
   }

   public static void cancelExpHottimeTask(boolean forced) {
      if (expHottimeTask != null) {
         expHottimeTask.cancel(true);
         expHottimeTask = null;
         if (!forced && ServerConstants.currentHottimeRate > 1.0) {
            Center.Broadcast.broadcastMessage(CField.chatMsg(3, "[ExpFever] เธเธดเธเธเธฃเธฃเธก Exp เธเธเธฅเธเนเธฅเนเธง"));
         }

         ServerConstants.currentHottimeRate = 1.0;
      }
   }

   public static void cancelJuhunHottimeTask(boolean forced) {
      if (juhunHottimeTask != null) {
         juhunHottimeTask.cancel(true);
         juhunHottimeTask = null;
         if (!forced && ServerConstants.JuhunFever == 1) {
            Center.Broadcast.broadcastMessage(CField.chatMsg(3, "[FeverTime] Spell Trace Fever Time เธเธเธฅเธเนเธฅเนเธง"));
         }

         ServerConstants.JuhunFever = 0;
      }
   }

   public static void registerAutoJuhunHottime() {
      cancelJuhunHottimeTask(true);
      System.out.println("Spell Trace Fever Time System has been registered.");
      juhunHottimeTask = Timer.HottimeTimer.getInstance()
            .register(
                  new Runnable() {
                     @Override
                     public void run() {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                        Calendar CAL = new GregorianCalendar(Locale.KOREA);
                        String fDate = sdf.format(CAL.getTime());
                        String[] dates = fDate.split("-");
                        int year = Integer.parseInt(dates[0]);
                        int month = Integer.parseInt(dates[1]);
                        int day = Integer.parseInt(dates[2]);
                        int hours = Integer.parseInt(dates[3]);
                        int minutes = Integer.parseInt(dates[4]);
                        int seconds = Integer.parseInt(dates[5]);
                        int zellerMonth = 0;
                        int zellerYear = 0;
                        if (month < 3) {
                           zellerMonth = month + 12;
                           zellerYear = year - 1;
                        } else {
                           zellerMonth = month;
                           zellerYear = year;
                        }

                        int computation = day + 26 * (zellerMonth + 1) / 10 + zellerYear + zellerYear / 4
                              + 6 * (zellerYear / 100) + zellerYear / 400;
                        int dayOfWeek = computation % 7;
                        if (DBConfig.isGanglim) {
                           TextEffect e = null;
                           String str = null;
                           switch (dayOfWeek) {
                              case 0:
                              case 1:
                                 if (ServerConstants.dailyEventType != DailyEventType.ExpRateFever_) {
                                    ServerConstants.dailyEventType = DailyEventType.ExpRateFever_;
                                    e = new TextEffect(-1,
                                          "[Weekend Advent Burning] Exp Bonus\r\nGain 100% extra Exp when hunting monsters in level range!!",
                                          50, 5000, 4, 0);
                                    str = "[Weekend Advent Burning] '100% Extra Exp' event is active. It is not shown in the chat log, but it is applied.";
                                 }
                                 break;
                              case 2:
                                 if (ServerConstants.dailyEventType != DailyEventType.DropRateFever) {
                                    ServerConstants.dailyEventType = DailyEventType.DropRateFever;
                                    e = new TextEffect(-1,
                                          "[Monday Advent Burning] Item Drop Rate Increase\r\nItem Drop Rate increased by 50% when hunting!!",
                                          50,
                                          5000, 4, 0);
                                    str = "[Monday Advent Burning] 'Item Drop Rate 50% Increase' event is active.";
                                 }
                                 break;
                              case 3:
                              case 5:
                                 if (ServerConstants.dailyEventType != DailyEventType.StarForceDiscount) {
                                    ServerConstants.dailyEventType = DailyEventType.StarForceDiscount;
                                    e = new TextEffect(
                                          -1,
                                          String.format(
                                                "[%s Advent Burning] Star Force Cost Discount\r\nStar Force enhancement cost %d%s discount applied!!",
                                                dayOfWeek == 3 ? "Tuesday" : "Thursday", 30, "%"),
                                          50,
                                          5000,
                                          4,
                                          0);
                                    str = String.format(
                                          "[%s Advent Burning] 'Star Force Cost Discount' event is active.",
                                          dayOfWeek == 3 ? "Tuesday" : "Thursday");
                                 }
                                 break;
                              case 4:
                                 if (ServerConstants.dailyEventType != DailyEventType.ExpRateFever) {
                                    ServerConstants.dailyEventType = DailyEventType.ExpRateFever;
                                    e = new TextEffect(-1,
                                          "[Wednesday Advent Burning] Exp Bonus\r\nGain 50% extra Exp when hunting monsters in level range!!",
                                          50, 5000, 4, 0);
                                    str = "[Wednesday Advent Burning] '50% Extra Exp' event is active. It is not shown in the chat log, but it is applied.";
                                 }
                                 break;
                              case 6:
                                 if (ServerConstants.dailyEventType != DailyEventType.MesoRateFever) {
                                    ServerConstants.dailyEventType = DailyEventType.MesoRateFever;
                                    e = new TextEffect(-1,
                                          "[Friday Advent Burning] Meso Bonus\r\nGain 100% extra Meso when hunting!!",
                                          50, 5000, 4,
                                          0);
                                    str = "[Friday Advent Burning] '100% Extra Meso' event is active.";
                                 }
                           }

                           if (e != null) {
                              Center.Broadcast.broadcastMessage(e.encodeForLocal());
                           }

                           if (str != null) {
                              Center.Broadcast.broadcastMessage(CField.chatMsg(5, str));
                           }
                        }

                        TimeScheduleEntry timeScheduleEntry = ServerConstants.timeScheduleEntry;
                        if (!DBConfig.isGanglim && timeScheduleEntry.getDailyEventCheck1() != day) {
                           if (dayOfWeek == 0 || dayOfWeek == 1) {
                              ServerConstants.dailyEventType = DailyEventType.ExpRateFever;
                              TextEffect e = new TextEffect(-1,
                                    "[Weekend Daily] Exp Bonus\r\nGain 20% extra Exp when hunting monsters in level range!",
                                    50, 5000, 4, 0);
                              Center.Broadcast.broadcastMessage(e.encodeForLocal());
                              Center.Broadcast
                                    .broadcastMessage(
                                          CField.chatMsg(3, "[Weekend Daily] เธเธดเธเธเธฃเธฃเธก EXP +20% เธเธณเธฅเธฑเธเธ”เธณเน€เธเธดเธเธญเธขเธนเน"));
                           } else if (dayOfWeek == 2) {
                              ServerConstants.dailyEventType = DailyEventType.MesoRateFever;
                              TextEffect e = new TextEffect(-1,
                                    "[Monday Daily] Meso Bonus\r\nGain 20% extra Meso when hunting!",
                                    50, 5000, 4,
                                    0);
                              Center.Broadcast.broadcastMessage(e.encodeForLocal());
                              Center.Broadcast
                                    .broadcastMessage(
                                          CField.chatMsg(3, "[Monday Daily] เธเธดเธเธเธฃเธฃเธก Meso +20% เธเธณเธฅเธฑเธเธ”เธณเน€เธเธดเธเธญเธขเธนเน"));
                           } else if (dayOfWeek == 3) {
                              ServerConstants.dailyEventType = DailyEventType.DropRateFever;
                              TextEffect e = new TextEffect(-1,
                                    "[Tuesday Daily] Item Drop Rate Increase\r\nItem Drop Rate increased by 20% when hunting!",
                                    50,
                                    5000, 4, 0);
                              Center.Broadcast.broadcastMessage(e.encodeForLocal());
                              Center.Broadcast
                                    .broadcastMessage(CField.chatMsg(3,
                                          "[Tuesday Daily] เธเธดเธเธเธฃเธฃเธกเน€เธเธดเนเธกเธญเธฑเธ•เธฃเธฒเธ”เธฃเธญเธ 20% เธเธณเธฅเธฑเธเธ”เธณเน€เธเธดเธเธญเธขเธนเน"));
                           } else if (dayOfWeek == 4) {
                              ServerConstants.dailyEventType = DailyEventType.MobGenFever;
                              TextEffect e = new TextEffect(-1,
                                    "[Wednesday Daily] Monster Spawn Rate Increase\r\nMonster Spawn Rate increased by 20%!",
                                    50,
                                    5000, 4,
                                    0);
                              Center.Broadcast.broadcastMessage(e.encodeForLocal());
                              Center.Broadcast
                                    .broadcastMessage(CField.chatMsg(3,
                                          "[Wednesday Daily] เธเธดเธเธเธฃเธฃเธกเน€เธเธดเนเธกเธญเธฑเธ•เธฃเธฒเธเธฒเธฃเน€เธเธดเธ”เธกเธญเธเธชเน€เธ•เธญเธฃเนเธเธณเธฅเธฑเธเธ”เธณเน€เธเธดเธเธญเธขเธนเน"));
                           } else if (dayOfWeek == 5) {
                              ServerConstants.dailyEventType = DailyEventType.StarForceDiscount;
                              TextEffect e = new TextEffect(-1,
                                    "[Thursday Daily] Star Force Cost Discount\r\nStar Force enhancement cost is discounted by 30%!",
                                    50,
                                    5000, 4, 0);
                              Center.Broadcast.broadcastMessage(e.encodeForLocal());
                              Center.Broadcast
                                    .broadcastMessage(CField.chatMsg(3,
                                          "[Thursday Daily] เธเธดเธเธเธฃเธฃเธกเธชเนเธงเธเธฅเธ” Star Force เธเธณเธฅเธฑเธเธ”เธณเน€เธเธดเธเธญเธขเธนเน"));
                           } else if (dayOfWeek == 6) {
                              ServerConstants.dailyEventType = DailyEventType.CubeFever;
                              TextEffect e = new TextEffect(-1,
                                    "[Friday Daily] Monster Collection Registration Rate Increase\r\nMonster Collection new monster registration rate increased by 20%!",
                                    50, 5000, 4,
                                    0);
                              Center.Broadcast.broadcastMessage(e.encodeForLocal());
                              Center.Broadcast.broadcastMessage(
                                    CField.chatMsg(3,
                                          "[Friday Daily] เธเธดเธเธเธฃเธฃเธกเน€เธเธดเนเธกเธญเธฑเธ•เธฃเธฒเธเธฒเธฃเธฅเธเธ—เธฐเน€เธเธตเธขเธ Monster Collection เธเธณเธฅเธฑเธเธ”เธณเน€เธเธดเธเธญเธขเธนเน"));
                           }

                           timeScheduleEntry.setDailyEventCheck1(day);
                           timeScheduleEntry.setChange(true);
                        }

                        String[] startTime = ServerConstants.juhunHottimeStartTime.split(":");
                        String[] endTime = ServerConstants.juhunHottimeEndTime.split(":");
                        double rate = ServerConstants.expHottimeRate;
                        if (hours == Integer.parseInt(startTime[0]) && minutes == Integer.parseInt(startTime[1])
                              && seconds == 1) {
                           if (DBConfig.isGanglim) {
                              Center.Broadcast
                                    .broadcastMessage(
                                          CField.chatMsg(5,
                                                "[FeverTime] Spell Trace Fever Time เธเธฐเธกเธตเธเธเธ–เธถเธ 22:00 เธ."));
                           } else {
                              Center.Broadcast
                                    .broadcastMessage(CField.chatMsg(3,
                                          "[FeverTime] Spell Trace Fever Time เธเธฐเธกเธตเธเธเธ–เธถเธ 22:00 เธ."));
                           }

                           Center.Broadcast.broadcastMessage(CWvsContext.scrollUpgradeFeverTime(2));
                           ServerConstants.JuhunFever = 1;
                        }

                        if (hours == Integer.parseInt(endTime[0]) && minutes == Integer.parseInt(endTime[1])
                              && seconds == 1) {
                           if (DBConfig.isGanglim) {
                              Center.Broadcast
                                    .broadcastMessage(CField.chatMsg(5,
                                          "[FeverTime] Spell Trace Fever Time เธเธณเธฅเธฑเธเธเธฐเธเธเธฅเธ"));
                           } else {
                              Center.Broadcast.broadcastMessage(
                                    CField.chatMsg(3, "[FeverTime] Spell Trace Fever Time เธเธณเธฅเธฑเธเธเธฐเธเธเธฅเธ"));
                           }

                           ServerConstants.JuhunFever = 0;
                        }

                        Center.lastJuhunFeverTime = System.currentTimeMillis();
                     }
                  },
                  1000L);
   }

   public static void registerCheckTask() {
      Timer.EtcTimer.getInstance().register(new Runnable() {
         @Override
         public void run() {
            if (Center.lastAutoHottimeTime != 0L && System.currentTimeMillis() - Center.lastAutoHottimeTime <= 5000L) {
               Center.lastAutoHottimeTime = 0L;
               Center.autoHottimeTask.cancel(true);
               Center.autoHottimeTask = null;
               Center.registerAutoHottime();
            }

            if (Center.lastAutoSaveTime != 0L && System.currentTimeMillis() - Center.lastAutoSaveTime <= 600000L) {
               Center.lastAutoSaveTime = 0L;
               Center.autoSaveTask.cancel(true);
               Center.autoSaveTask = null;
               Center.registerAutoSave();
            }

            if (Center.lastJuhunFeverTime != 0L && System.currentTimeMillis() - Center.lastJuhunFeverTime <= 5000L) {
               Center.lastJuhunFeverTime = 0L;
               Center.juhunHottimeTask.cancel(true);
               Center.juhunHottimeTask = null;
               Center.registerAutoJuhunHottime();
            }

            for (Integer c : Center.respawnTask.keySet()) {
               if (Center.lastRespawnTime.get(c) != 0L
                     && System.currentTimeMillis() - Center.lastRespawnTime.get(c) <= 10000L) {
                  Center.lastRespawnTime.remove(c);
                  Center.lastRespawnTime.put(c, 0L);
                  Center.respawnTask.get(c).cancel(true);
                  Center.respawnTask.remove(c);
                  Integer[] chs = GameServer.getAllInstance().toArray(new Integer[0]);
                  Center.respawnTask.put(c, Timer.WorldTimer.getInstance().register(new Center.Respawn(chs, c), 1000L));
               }
            }
         }
      }, 60000L);
   }

   public static void checkRegenThread() {
      System.out.println("Mob Regen Thread Check Timer has been registered.");
      Timer.EtcTimer.getInstance().register(new Runnable() {
         @Override
         public void run() {
            try {
               for (Integer c : Center.respawnTask.keySet()) {
                  if (Center.lastRespawnTime.get(c) != 0L
                        && System.currentTimeMillis() - Center.lastRespawnTime.get(c) >= 30000L) {
                     try {
                        Center.respawnTask.get(c).cancel(true);
                     } catch (Exception var4) {
                     }

                     Integer[] chs = GameServer.getAllInstance().toArray(new Integer[0]);
                     Center.respawnTask.put(c,
                           Timer.WorldTimer.getInstance().register(new Center.Respawn(chs, c), 1000L));
                  }
               }
            } catch (Exception var5) {
            }
         }
      }, 300000L);
   }

   public static void registerAutoExpBuff() {
      cancelExpHottimeTask(true);
      System.out.println("Auto Hot Time Exp Event System has been registered.");
      expHottimeTask = Timer.WorldTimer.getInstance().register(new Runnable() {
         @Override
         public void run() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            Calendar CAL = new GregorianCalendar(Locale.KOREA);
            String fDate = sdf.format(CAL.getTime());
            String[] dates = fDate.split("-");
            int year = Integer.parseInt(dates[0]);
            int month = Integer.parseInt(dates[1]);
            int day = Integer.parseInt(dates[2]);
            int hours = Integer.parseInt(dates[3]);
            int minutes = Integer.parseInt(dates[4]);
            int seconds = Integer.parseInt(dates[5]);
            String[] startTime = ServerConstants.expHottimeStartTime.split(":");
            String[] endTime = ServerConstants.expHottimeEndTime.split(":");
            double rate = ServerConstants.expHottimeRate;
            if (hours == Integer.parseInt(startTime[0]) && minutes == Integer.parseInt(startTime[1]) && seconds == 1) {
               Center.Broadcast.broadcastMessage(CField.chatMsg(3,
                     "[Event] Exp " + rate + "x event will run until " + endTime[0] + ":" + endTime[1] + "."));
               ServerConstants.currentHottimeRate = rate;
            }

            if (hours == Integer.parseInt(endTime[0]) && minutes == Integer.parseInt(endTime[1]) && seconds == 1) {
               Center.Broadcast.broadcastMessage(CField.chatMsg(3, "[Event] เธเธดเธเธเธฃเธฃเธก Exp เธเธเธฅเธเนเธฅเนเธง"));
               ServerConstants.currentHottimeRate = 1.0;
               Center.expHottimeTask.cancel(true);
               Center.expHottimeTask = null;
            }
         }
      }, 1000L);
   }

   public static void autoCCU() {
      System.out.println("CCU Notifier On");
      Timer.EtcTimer.getInstance()
            .register(
                  new Runnable() {
                     @Override
                     public void run() {
                        int count = 0;
                        int fakeCount = 0;

                        for (GameServer gameServer : GameServer.getAllInstances()) {
                           int size = Math.round((float) gameServer.getPlayerCountInChannel());
                           count += size;
                           fakeCount = (int) (fakeCount + size * ServerConstants.connectedRate);
                        }

                        int csCount = 0;

                        for (MapleCharacter p : new ArrayList<>(CashShopServer.getPlayerStorage().getAllCharacters())) {
                           if (p != null) {
                              csCount++;
                           }
                        }

                        csCount = Math.round((float) csCount);
                        count += csCount;
                        fakeCount = (int) (fakeCount + csCount * ServerConstants.connectedRate);
                        int auctionCount = 0;

                        for (MapleCharacter px : new ArrayList<>(AuctionServer.getPlayerStorage().getAllCharacters())) {
                           if (px != null) {
                              auctionCount++;
                           }
                        }

                        auctionCount = Math.round((float) auctionCount);
                        count += auctionCount;
                        fakeCount = (int) (fakeCount + auctionCount * ServerConstants.connectedRate);
                        if (Center.ccu < count) {
                           Center.ccu = count;
                        }

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                        Calendar CAL = new GregorianCalendar(Locale.KOREA);
                        String fDate = sdf.format(CAL.getTime());
                        String[] dates = fDate.split("-");
                        int year = Integer.parseInt(dates[0]);
                        int month = Integer.parseInt(dates[1]);
                        int day = Integer.parseInt(dates[2]);
                        int hours = Integer.parseInt(dates[3]);
                        int minutes = Integer.parseInt(dates[4]);
                        int seconds = Integer.parseInt(dates[5]);
                        if (Center.todayccu < count) {
                           Center.todayccu = count;
                           Center.todayBestCCUDate = hours + ":" + minutes + ":" + seconds + " ";
                        }

                        if (Center.lasthour == -1) {
                           if (!DBConfig.isGanglim) {
                              Center.lasthour = hours;
                              DiscordBotHandler.requestSendTelegram(
                                    "[" + hours
                                          + ":59:59] Recording hourly concurrent users.\r\nConcurrent users notification will be sent at the top of the hour.",
                                    -593683313L);
                           } else if (DiscordBotHandler.requestSendTelegramCCU(
                                 "[" + hours
                                       + ":59:59] Recording hourly concurrent users.\r\nConcurrent users notification will be sent at the top of the hour.")) {
                              Center.lasthour = hours;
                           }
                        }

                        if (Center.lasthour != hours && minutes == 0) {
                           if (!DBConfig.isGanglim) {
                              DiscordBotHandler.requestSendTelegram(
                                    "[" + Center.lasthour + ":59:59] Max Concurrent Users: " + Center.ccu,
                                    -593683313L);
                              Center.ccu = 0;
                              Center.lasthour = hours;
                              if (Center.lasthour == 23) {
                                 DiscordBotHandler.requestSendTelegram(
                                       "[Today's Peak Time]\r\nRecorded " + Center.todayccu
                                             + " max concurrent users at [ "
                                             + Center.todayBestCCUDate + " ].",
                                       -593683313L);
                                 Center.todayccu = 0;
                              }
                           } else {
                              DiscordBotHandler.requestSendTelegramCCU(
                                    "[" + Center.lasthour + ":59:59] Max Concurrent Users: " + Center.ccu);
                              Center.ccu = 0;
                              Center.lasthour = hours;
                              if (Center.lasthour == 23) {
                                 DiscordBotHandler.requestSendTelegramCCU(
                                       "[Today's Peak Time]\r\nRecorded " + Center.todayccu
                                             + " max concurrent users at [ "
                                             + Center.todayBestCCUDate + " ].");
                                 Center.todayccu = 0;
                              }
                           }
                        }

                        if (seconds == 0) {
                           PreparedStatement ps = null;

                           try (Connection con = DBConnection.getConnection()) {
                              ps = con.prepareStatement(
                                    "INSERT INTO `ccu` (`id`, `timestamp`, `count`) VALUES(1, CURRENT_TIMESTAMP(), ?) ON DUPLICATE KEY UPDATE `timestamp` = CURRENT_TIMESTAMP(), `count` = ?");
                              ps.setInt(1, DBConfig.isGanglim ? fakeCount : count);
                              ps.setInt(2, DBConfig.isGanglim ? fakeCount : count);
                              ps.executeQuery();
                           } catch (SQLException var31) {
                              var31.printStackTrace();
                           } finally {
                              try {
                                 if (ps != null) {
                                    ps.close();
                                    PreparedStatement var46 = null;
                                 }
                              } catch (SQLException var28) {
                                 var28.printStackTrace();
                              }
                           }
                        }
                     }
                  },
                  1000L);
   }

   public static void registerRespawn() {
      Integer[] chs = GameServer.getAllInstance().toArray(new Integer[0]);

      for (int i = 0; i < chs.length; i += 3) {
         respawnTask.put(i, Timer.WorldTimer.getInstance().register(new Center.Respawn(chs, i), 1000L));
      }
   }

   public static void registerRespawn(int channel) {
      Integer[] chs = GameServer.getAllInstance().toArray(new Integer[0]);
      respawnTask.put(channel, Timer.WorldTimer.getInstance().register(new Center.Respawn(channel), 1000L));
   }

   public static void handleMap(Field map, int numTimes, int size, long now) {
      if (AdminClient.getNextDisableChatTime() != 0L
            && AdminClient.getNextDisableChatTime() <= System.currentTimeMillis()) {
         AdminClient.setNextDisableChatTime(0L);
         AdminClient.setDisabledChat(true);
      }

      try {
         if (map.getItemsSize() > 0) {
            for (Drop item : map.getAllItemsThreadsafe()) {
               if (item.shouldExpire(now)) {
                  item.expire(map);
               } else if (item.shouldFFA(now)) {
                  item.setDropType((byte) 2);
               }
            }
         }
      } catch (Exception var18) {
         System.out.println("[Error] Error occurred while removing field items : " + var18);
         FileoutputUtil.outputFileError("Log_FieldItem_Except.rtf", var18);
         var18.printStackTrace();
      }

      FieldEvent event = map.getFieldEvent();
      if (event != null) {
         try {
            event.onUpdatePerSecond(System.currentTimeMillis());
         } catch (Exception var15) {
            System.out.println("[Error] Error occurred during event field update : " + var15);
            FileoutputUtil.outputFileError("Log_EventField_Except.rtf", var15);
            var15.printStackTrace();
         }
      }

      if (map.characterSize() > 0) {
         try {
            map.respawn(false, now);
         } catch (Exception var14) {
            FileoutputUtil.outputFileError("Log_Respawn_Except.rtf", var14);
            System.out.println("Map Respawn Error Occurred");
            var14.printStackTrace();
         }

         try {
            boolean hurt = map.canHurt(now);

            for (MapleCharacter chr : map.getCharactersThreadsafe()) {
               try {
                  handleCooldowns(chr, numTimes, hurt, now);
               } catch (Exception var13) {
                  FileoutputUtil.outputFileError("Log_PlayerUpdate_Except.rtf", var13);
                  System.out.println("[Error] Error occurred in handleCooldowns (Name : " + chr.getName() + ")");
                  var13.printStackTrace();
               }
            }
         } catch (Exception var17) {
            System.out.println("[Error] Error occurred during character update : " + var17);
            var17.printStackTrace();
         }

         try {
            map.fieldUpdatePerSeconds();
         } catch (Exception var12) {
            FileoutputUtil.outputFileError("Log_MapUpdate_Except.rtf", var12);
            System.out.println("[Error] Error occurred during field update : " + var12);
            var12.printStackTrace();
         }

         try {
            if (map.getAllFieldAttackObj().size() > 0) {
               map.getAllFieldAttackObj().stream().filter(obj -> obj.checkRemove(System.currentTimeMillis()))
                     .forEach(map::removeFieldAttackObj);
            }

            if (map.getAllWreakage().size() > 0) {
               for (Wreckage wreakage : new ArrayList<>(map.getAllWreakage())) {
                  if (wreakage.getEndTime() <= now) {
                     wreakage.removeWreckage(wreakage.getOwner().getMap(), false);
                     map.broadcastMessage(
                           CField.DelWreckage(wreakage.getOwner().getId(), Collections.singletonList(wreakage), false));
                     if (wreakage.getOwner() != null) {
                        wreakage.getOwner().decAndGetWreckageCount();
                     }
                  }
               }
            }

            if (map.getAllDynamicFoodhold().size() > 0) {
               for (MapleDynamicFoothold foothold : new ArrayList<>(map.getAllDynamicFoodhold())) {
                  if (foothold.getEndTime() <= now) {
                     foothold.clear();
                     map.broadcastMessage(CField.syncDynamicFoothold(foothold));
                     map.removeMapObject(foothold);
                  }
               }
            }

            if (map.getAllMistsThreadsafe().size() > 0) {
               for (AffectedArea aa : new ArrayList<>(map.getAllMistsThreadsafe())) {
                  aa.updatePerSecond(map, now);
               }
            }

            if (map.getSecondAtoms().size() > 0) {
               ArrayList<SecondAtom.Atom> removes = new ArrayList<>();

               for (Entry<Integer, SecondAtom.Atom> atom : map.getSecondAtoms().entrySet()) {
                  SecondAtom.Atom a = atom.getValue();
                  if (a != null && a.getExpire() > 0
                        && System.currentTimeMillis() - a.getCreateTime() >= a.getExpire()) {
                     removes.add(a);
                  }
               }

               for (SecondAtom.Atom a : removes) {
                  int owner = a.getPlayerID();
                  MapleCharacter p = map.getCharacterById(owner);
                  boolean check = true;
                  if (a.getType() == SecondAtom.SecondAtomType.WhereTheRiverFlows && p != null
                        && p.hasBuffBySkillID(162111002)) {
                     check = false;
                  }

                  if (check) {
                     map.removeSecondAtom(a.getKey());
                     if (p != null) {
                        p.removeSecondAtom(a.getKey());
                     }
                  }
               }
            }

            if (map.getMobsSize() > 0) {
               for (MapleMonster mons : map.getAllMonstersThreadsafe()) {
                  if (mons.isAlive() && mons.shouldKill(now)) {
                     map.killMonster(mons, mons.getStats().getSelfD() == 6);
                  } else if (mons.isAlive() && mons.shouldDrop(now)) {
                     mons.doDropItem(now);
                  } else if (mons.isAlive() && mons.getStatiSize() > 0) {
                     for (MobTemporaryStatEffect mse : mons.getAllBuffs()) {
                        if (mse.shouldCancel(now)) {
                           mons.cancelSingleStatus(mse);
                        }
                     }

                     for (MobTemporaryStatEffect e : mons.getAllPoison()) {
                        if (e.shouldCancel(now)) {
                           mons.cancelSingleStatus(e);
                        } else {
                           mons.doPoison(e, e.getPoisonOwner());
                        }
                     }
                  }

                  if (mons.isAlive()) {
                     mons.onUpdatePerSecond();
                  }
               }

               if (!map.isTown()
                     && !FieldLimitType.NO_EXP_DECREASE.check(map.getFieldLimit())
                     && map.getId() != 993026800
                     && map.getId() != 272030410
                     && map.getId() != 272020600
                     && map.getId() != 272020500
                     && map.getId() != 272020310
                     && map.getId() != 272020300
                     && map.getId() != 271041300
                     && map.getId() != 271040300
                     && (map.getId() < 105200529 || map.getId() > 105200520)
                     && (map.getId() < 105200129 || map.getId() > 105200120)) {
                  if (map.getLastCheckMacroTime() == 0L) {
                     map.setLastCheckMacroTime(System.currentTimeMillis());
                  } else if (System.currentTimeMillis() - map.getLastCheckMacroTime() >= 2100000L) {
                     if (Randomizer.isSuccess(80)) {
                        for (MapleCharacter player : map.getCharacters()) {
                           if (!player.isEnterRandomPortal()) {
                              player.tryAntiMacro(AntiMacroType.Auto, null);
                              StringBuilder sb = new StringBuilder();
                              sb.append("Anti-Macro Detection Attempt");
                              sb.append(" (Account ID : ");
                              sb.append(player.getClient().getAccountName());
                              sb.append(", Character Name : ");
                              sb.append(player.getName());
                              sb.append(")");
                              LoggingManager.putLog(new MacroLog(player, 0, 0, sb));
                           }
                        }
                     }

                     map.setLastCheckMacroTime(System.currentTimeMillis());
                  }

                  if (map.getLastSpawnRuneTime() == 0L) {
                     map.setLastSpawnRuneTime(System.currentTimeMillis());
                     if (map.getAllRune().isEmpty()) {
                        for (Spawns spawns : map.getClosestSpawns(
                              new Point(Randomizer.rand(map.getLeft(), map.getRight()),
                                    Randomizer.rand(map.getTop(), map.getBottom())),
                              1)) {
                           RuneStone rune = new RuneStone(RuneStoneType.get(Randomizer.rand(0, 9)), 0,
                                 spawns.getPosition().x, spawns.getPosition().y, map);
                           map.addMapObject(rune);
                           map.broadcastMessage(CField.spawnRune(rune, false));
                           map.broadcastMessage(CField.spawnRune(rune, true));
                        }

                        map.setLastSpawnRuneTime(System.currentTimeMillis());
                     }
                  } else if (System.currentTimeMillis() - map.getLastSpawnRuneTime() >= 600000L
                        && map.getAllRune().isEmpty()) {
                     for (Spawns spawns : map.getClosestSpawns(
                           new Point(Randomizer.rand(map.getLeft(), map.getRight()),
                                 Randomizer.rand(map.getTop(), map.getBottom())),
                           1)) {
                        RuneStone rune = new RuneStone(RuneStoneType.get(Randomizer.rand(0, 9)), 0,
                              spawns.getPosition().x, spawns.getPosition().y, map);
                        map.addMapObject(rune);
                        map.broadcastMessage(CField.spawnRune(rune, false));
                        map.broadcastMessage(CField.spawnRune(rune, true));
                     }

                     map.setLastSpawnRuneTime(System.currentTimeMillis());
                  }

                  if (!map.getAllRune().isEmpty()) {
                     if (map.getEliteBossCurseLevel() < 4) {
                        if (map.getLastUpdateEliteBossCurseTime() == 0L) {
                           map.setLastUpdateEliteBossCurseTime(System.currentTimeMillis());
                        } else if (System.currentTimeMillis() - map.getLastUpdateEliteBossCurseTime() >= 300000L) {
                           map.setEliteBossCurseLevel(map.getEliteBossCurseLevel() + 1);
                           map.setLastUpdateEliteBossCurseTime(System.currentTimeMillis());
                        }
                     }

                     if (!map.getAllRune().isEmpty()) {
                        RuneStone rune = map.getAllRune().get(0);
                        if (rune != null && rune.getLastRuneTouchTime() != 0L
                              && System.currentTimeMillis() >= rune.getLastRuneTouchTime() + 12000L) {
                           rune.clearOccupier();
                           rune.setLastRuneTouchTime(0L);
                        }
                     }
                  }
               }
            }

            map.updateFieldFallingCatcher(now);
         } catch (Exception var16) {
            System.out.println("[Error] Error occurred during field update : " + var16);
            FileoutputUtil.outputFileError("Log_FieldUpdate_Except.rtf", var16);
            var16.printStackTrace();
         }
      }
   }

   public static void handleCooldowns(MapleCharacter chr, int numTimes, boolean hurt, long now) {
      if (chr != null) {
         if (chr.getLastMoveTime() != 0L && System.currentTimeMillis() - chr.getLastMoveTime() >= 300000L) {
            chr.setNotMovingCount(chr.getNotMovingCount() + 1);
            chr.setLastMoveTime(System.currentTimeMillis());
         }

         if (chr.getCooldownSize() > 0) {
            for (MapleCoolDownValueHolder m : chr.getCooldowns()) {
               int reduceR = chr.getStat().reduceCooltimeR;
               if (reduceR > 0) {
                  int reduc = (int) ((System.currentTimeMillis() - m.startTime) * reduceR / 100L);
                  chr.changeCooldown(m.skillId, -reduc);
               }

               if (m.startTime + m.length < now) {
                  int skil = m.skillId;
                  chr.clearCooldown(skil);
                  if (skil == 400051074 || skil == 500061029) {
                     PacketEncoder packet = new PacketEncoder();
                     packet.writeShort(SendPacketOpcode.POOL_MAKER_REQUEST.getValue());
                     packet.write(0);
                     chr.send(packet.getPacket());
                  }

                  if (skil == 400051015) {
                     chr.invokeJobMethod("resetSerpentScrewValue");
                  }
               }
            }
         }

         chr.update();
         if (chr.isAlive()) {
            if (chr.canRecover(now)) {
               chr.doRecovery();
            }

            if (chr.canHPRecover(now)) {
               chr.addHP((int) chr.getStat().getHealHP());
            }

            if (chr.canMPRecover(now)) {
               chr.addMP((int) chr.getStat().getHealMP());
            }

            if (chr.canFairy(now)) {
               chr.doFairy();
            }

            if (chr.canRoadRing(now)) {
               chr.doRoadRing();
            }

            if (chr.canDOT(now)) {
               chr.doDOT();
            }
         }

         if (chr.getDiseaseSize() > 0) {
            for (MapleDiseaseValueHolder m : chr.getDiseasesList()) {
               if (m != null && m.startTime + m.length < now) {
                  chr.dispelDebuff(m.disease);
                  if (m.disease == SecondaryStatFlag.WillPoison && chr.getMap() instanceof Field_WillBattle) {
                     Field_WillBattle f = (Field_WillBattle) chr.getMap();
                     f.sendWillRemovePoison(chr);
                     chr.setLastWillAttackTime(0L);
                  }
               }
            }
         }

         if (hurt && chr.isAlive()
               && chr.getInventory(MapleInventoryType.EQUIPPED).findById(chr.getMap().getHPDecProtect()) == null) {
            if (chr.getMapId() == 749040100 && chr.getInventory(MapleInventoryType.CASH).findById(5451000) == null) {
               chr.addHP(-chr.getMap().getHPDec());
            } else if (chr.getMapId() != 749040100) {
            }
         }
      }
   }

   public static class Alliance {
      private static final Map<Integer, objects.context.guild.alliance.Alliance> alliances = new LinkedHashMap<>();
      public static Map<Integer, Integer> inviteList = new HashMap<>();
      public static Map<Integer, Long> inviteTime = new HashMap<>();
      private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

      public static objects.context.guild.alliance.Alliance getAlliance(int allianceid) {
         objects.context.guild.alliance.Alliance ret = null;
         lock.readLock().lock();

         try {
            ret = alliances.get(allianceid);
         } finally {
            lock.readLock().unlock();
         }

         if (ret == null) {
            lock.writeLock().lock();

            Object var2;
            try {
               ret = new objects.context.guild.alliance.Alliance(allianceid);
               if (ret != null && ret.getId() > 0) {
                  alliances.put(allianceid, ret);
                  return ret;
               }

               var2 = null;
            } finally {
               lock.writeLock().unlock();
            }

            return (objects.context.guild.alliance.Alliance) var2;
         } else {
            return ret;
         }
      }

      public static int getAllianceLeader(int allianceid) {
         objects.context.guild.alliance.Alliance mga = getAlliance(allianceid);
         return mga != null ? mga.getLeaderId() : 0;
      }

      public static void updateAllianceRanks(int allianceid, String[] ranks) {
         objects.context.guild.alliance.Alliance mga = getAlliance(allianceid);
         if (mga != null) {
            mga.setRank(ranks);
         }
      }

      public static void updateAllianceNotice(int allianceid, String notice) {
         objects.context.guild.alliance.Alliance mga = getAlliance(allianceid);
         if (mga != null) {
            mga.setNotice(notice);
         }
      }

      public static boolean canInvite(int allianceid) {
         objects.context.guild.alliance.Alliance mga = getAlliance(allianceid);
         return mga != null ? mga.getCapacity() > mga.getGuildCount() : false;
      }

      public static boolean changeAllianceLeader(int allianceid, int cid) {
         objects.context.guild.alliance.Alliance mga = getAlliance(allianceid);
         return mga != null ? mga.setLeaderId(cid) : false;
      }

      public static boolean changeAllianceLeader(int allianceid, int cid, boolean sameGuild) {
         objects.context.guild.alliance.Alliance mga = getAlliance(allianceid);
         return mga != null ? mga.setLeaderId(cid, sameGuild) : false;
      }

      public static boolean changeAllianceRank(int allianceid, int cid, int change) {
         objects.context.guild.alliance.Alliance mga = getAlliance(allianceid);
         return mga != null ? mga.changeAllianceRank(cid, change) : false;
      }

      public static boolean changeAllianceCapacity(int allianceid) {
         objects.context.guild.alliance.Alliance mga = getAlliance(allianceid);
         return mga != null ? mga.setCapacity() : false;
      }

      public static boolean disbandAlliance(int allianceid) {
         objects.context.guild.alliance.Alliance mga = getAlliance(allianceid);
         return mga != null ? mga.disband() : false;
      }

      public static boolean addGuildToAlliance(int allianceid, int gid) {
         objects.context.guild.alliance.Alliance mga = getAlliance(allianceid);
         return mga != null ? mga.addGuild(gid) : false;
      }

      public static boolean removeGuildFromAlliance(int allianceid, int gid, boolean expelled) {
         objects.context.guild.alliance.Alliance mga = getAlliance(allianceid);
         return mga != null ? mga.removeGuild(gid, expelled) : false;
      }

      public static void sendGuild(int allianceid) {
         objects.context.guild.alliance.Alliance alliance = getAlliance(allianceid);
         if (alliance != null) {
         }
      }

      public static void sendGuild(byte[] packet, int exceptionId, int allianceid) {
         objects.context.guild.alliance.Alliance alliance = getAlliance(allianceid);
         if (alliance != null) {
            for (int i = 0; i < alliance.getGuildCount(); i++) {
               int gid = alliance.getGuildId(i);
               if (gid > 0 && gid != exceptionId) {
                  Center.Guild.guildPacket(gid, packet);
               }
            }
         }
      }

      public static boolean createAlliance(String alliancename, int cid, int cid2, int gid, int gid2) {
         int allianceid = objects.context.guild.alliance.Alliance.createToDb(cid, alliancename, gid, gid2);
         if (allianceid <= 0) {
            return false;
         } else {
            objects.context.guild.Guild g = Center.Guild.getGuild(gid);
            objects.context.guild.Guild g_ = Center.Guild.getGuild(gid2);
            g.setAllianceId(allianceid);
            g_.setAllianceId(allianceid);
            g.changeARank(true);
            g_.changeARank(false);
            objects.context.guild.alliance.Alliance alliance = getAlliance(allianceid);
            PacketEncoder p = new PacketEncoder();
            GuildPacket.CreateAlliance createAlliance = new GuildPacket.CreateAlliance(alliance);
            createAlliance.encode(p);
            sendGuild(p.getPacket(), -1, allianceid);
            sendGuild(CWvsContext.AlliancePacket.createGuildAlliance(alliance), -1, allianceid);
            return true;
         }
      }

      public static void allianceChat(int gid, MapleCharacter chr, int cid, String msg, Item item, String itemName,
            int achievementID, long achievementTime) {
         objects.context.guild.Guild g = Center.Guild.getGuild(gid);
         if (g != null) {
            objects.context.guild.alliance.Alliance ga = getAlliance(g.getAllianceId());
            if (ga != null) {
               for (int i = 0; i < ga.getGuildCount(); i++) {
                  objects.context.guild.Guild g_ = Center.Guild.getGuild(ga.getGuildId(i));
                  if (g_ != null) {
                     g_.allianceChat(chr, cid, msg, item, itemName, achievementID, achievementTime);
                  }
               }
            }
         }
      }

      public static void JoinGuildInAlliance(int guildID, int allianceID) {
         objects.context.guild.alliance.Alliance alliance = getAlliance(allianceID);
         objects.context.guild.Guild guild = Center.Guild.getGuild(guildID);
         if (alliance != null && guild != null) {
            for (int i = 0; i < alliance.getGuildCount(); i++) {
               if (guildID == alliance.getGuildId(i)) {
                  guild.setAllianceId(allianceID);
                  PacketEncoder packet = new PacketEncoder();
                  GuildPacket.JoinGuildInAlliance info = new GuildPacket.JoinGuildInAlliance(guild);
                  info.encode(packet);
                  guild.changeARank();
                  guild.broadcast(packet.getPacket());
                  guild.writeToDB(false);
               } else {
                  objects.context.guild.Guild g_ = Center.Guild.getGuild(alliance.getGuildId(i));
                  if (g_ != null) {
                     PacketEncoder packet = new PacketEncoder();
                     GuildPacket.JoinGuildInAlliance info = new GuildPacket.JoinGuildInAlliance(g_);
                     info.encode(packet);
                     g_.broadcast(packet.getPacket());
                  }
               }
            }
         }
      }

      public static void withdrawGuildInAlliance(int guildID, boolean kick, int allianceID) {
         objects.context.guild.alliance.Alliance alliance = getAlliance(allianceID);
         objects.context.guild.Guild targetGuild = Center.Guild.getGuild(guildID);
         if (alliance != null) {
            for (int i = 0; i < alliance.getGuildCount(); i++) {
               objects.context.guild.Guild guild = Center.Guild.getGuild(alliance.getGuildId(i));
               if (guild != null) {
                  if (targetGuild != null) {
                     PacketEncoder p = new PacketEncoder();
                     GuildPacket.WithdrawGuildInAlliance a = new GuildPacket.WithdrawGuildInAlliance(allianceID,
                           targetGuild.getId(), kick);
                     a.encode(p);
                     guild.broadcast(p.getPacket());
                  } else {
                     guild.changeARank(5);
                     guild.setAllianceId(0);
                  }
               }
            }

            if (guildID > 0) {
               alliance.removeGuild_(guildID);
            }

            if (targetGuild != null) {
               targetGuild.changeARank(5);
               targetGuild.setAllianceId(0);
            } else {
               PacketEncoder p = new PacketEncoder();
               GuildPacket.DisbandAlliance disband = new GuildPacket.DisbandAlliance(allianceID);
               disband.encode(p);
               alliance.broadcast(p.getPacket());
            }
         }

         if (guildID == -1) {
            lock.writeLock().lock();

            try {
               alliances.remove(allianceID);
            } finally {
               lock.writeLock().unlock();
            }
         }
      }

      public static List<byte[]> getAllianceInfo(int allianceid, boolean start) {
         List<byte[]> ret = new ArrayList<>();
         objects.context.guild.alliance.Alliance alliance = getAlliance(allianceid);
         if (alliance != null) {
            if (start) {
               ret.add(CWvsContext.AlliancePacket.getAllianceInfo(alliance));
               ret.add(CWvsContext.AlliancePacket.getGuildAlliance(alliance));
            }

            ret.add(CWvsContext.AlliancePacket.getAllianceUpdate(alliance));
         }

         return ret;
      }

      public static void save() {
         lock.writeLock().lock();

         try {
            for (objects.context.guild.alliance.Alliance a : alliances.values()) {
               a.saveToDb();
            }
         } finally {
            lock.writeLock().unlock();
         }
      }

      static {
         for (objects.context.guild.alliance.Alliance g : objects.context.guild.alliance.Alliance.loadAll()) {
            alliances.put(g.getId(), g);
         }
      }
   }

   public static class Auction {
      static List<AuctionItemPackage> items = new ArrayList<>();
      static Map<Integer, List<AuctionInfo>> auctions = new HashMap<>();

      public static void addAuction(int cid, long bid, int iid, byte status) {
         if (auctions.get(iid) == null) {
            auctions.put(iid, new ArrayList<>());
         }

         boolean isBest = true;
         boolean isExist = false;

         for (AuctionInfo ai : auctions.get(iid)) {
            if (ai.getCharacterId() == cid) {
               isExist = true;
               auctions.get(iid).set(auctions.get(iid).indexOf(ai), new AuctionInfo(bid, cid, status));
            }

            if (bid < ai.getBid()) {
               isBest = false;
            }
         }

         if (!isExist) {
            auctions.get(iid).add(new AuctionInfo(bid, cid, status));
         }

         if (isBest) {
            findByIid(iid).setBid(bid);
         }
      }

      public static long getBidById(int cid, int iid) {
         long bid = 0L;

         for (AuctionInfo ai : auctions.get(iid)) {
            if (ai.getCharacterId() == cid && ai.getBid() >= bid) {
               bid = ai.getBid();
            }
         }

         return bid;
      }

      public static AuctionItemPackage getItem(int auctionID) {
         for (AuctionItemPackage aitem : new ArrayList<>(items)) {
            if (aitem.getItem().getInventoryId() == auctionID) {
               return aitem;
            }
         }

         return null;
      }

      public static AuctionItemPackage getItemByHistoryID(int historyID) {
         for (AuctionItemPackage aitem : new ArrayList<>(items)) {
            if (aitem.getHistoryID() == historyID) {
               return aitem;
            }
         }

         return null;
      }

      public static List<AuctionItemPackage> getItems() {
         List<AuctionItemPackage> items_ = new ArrayList<>();

         for (AuctionItemPackage aitem : new ArrayList<>(items)) {
            if ((aitem.getBuyer() == 999999 || aitem.getBuyer() == 0)
                  && aitem.getExpiredTime() > System.currentTimeMillis()) {
               items_.add(aitem);
            }
         }

         return items_;
      }

      public static List<AuctionItemPackage> getHistoryItems(int accountID, int playerID) {
         List<AuctionItemPackage> items_ = new ArrayList<>();
         items.stream().sorted(Comparator.comparingInt(AuctionItemPackage::getHistoryID)).collect(Collectors.toList())
               .forEach(item -> {
                  if (DBConfig.isGanglim) {
                     LocalDateTime date = LocalDateTime.of(2022, 4, 26, 0, 0, 0);
                     long milliSeconds = Timestamp.valueOf(date).getTime();
                     if (item.getExpiredTime() < milliSeconds) {
                        int type = item.getType(true, true);
                        if ((type == 2 || type == 7) && item.getBid() == playerID
                              || type != 7 && type >= 3 && item.getAccountID() == accountID) {
                           items_.add(item);
                        }
                     } else if (item.getItem().getQuantity() > 0
                           && (item.getOwnerId() == playerID || item.getBuyer() == playerID)) {
                        items_.add(item);
                     }
                  } else if (item.getItem().getQuantity() > 0
                        && (item.getOwnerId() == playerID || item.getBuyer() == playerID)) {
                     items_.add(item);
                  }
               });
         return items_;
      }

      public static List<AuctionItemPackage> getMarketPriceItems(int playerID) {
         List<AuctionItemPackage> items_ = new ArrayList<>();
         items.stream().sorted((a, b) -> b.getHistoryID() - a.getHistoryID()).collect(Collectors.toList())
               .forEach(item -> {
                  int temp = item.getType(item.getOwnerId() == playerID, false);
                  int type = item.getOwnerId() != playerID && temp == 2 ? 3 : temp;
                  if (item.getItem().getQuantity() > 0 && item.getBuyer() != 0 && (type == 3 || type >= 7 && type <= 9)
                        && item.getBuyTime() > 0L) {
                     items_.add(item);
                  }
               });
         return items_;
      }

      public static List<AuctionItemPackage> getMyItems(int accountID, int playerID) {
         List<AuctionItemPackage> items_ = new ArrayList<>();

         for (AuctionItemPackage aitem : new ArrayList<>(items)) {
            if (aitem.getBuyer() == 0 && aitem.getType(false, true) == 0 && aitem.getOwnerId() == playerID) {
               items_.add(aitem);
            }
         }

         return items_;
      }

      public static final void addItem(AuctionItemPackage aitem) {
         aitem.getItem().setInventoryId(items.size() + 1);
         items.add(aitem);
      }

      public static final void load() {
         try {
            ItemLoader.AUCTION.loadItems(false, -1, 0);

            try (
                  Connection con = DBConnection.getConnection();
                  PreparedStatement ps = con.prepareStatement("SELECT * FROM `auctions`");
                  ResultSet rs = ps.executeQuery();) {
               while (rs.next()) {
                  addAuction(rs.getInt("characterid"), rs.getLong("bid"), rs.getInt("inventoryid"),
                        rs.getByte("status"));
               }
            }

            System.out.println("A total of " + items.size() + " Maple Auction items have been loaded.");
         } catch (SQLException var11) {
            var11.printStackTrace();
         }
      }

      public static final AuctionItemPackage findByIid(int id) {
         for (AuctionItemPackage item : new ArrayList<>(items)) {
            if (item.getItem().getInventoryId() == id) {
               return item;
            }
         }

         return null;
      }

      public static int getBuyAllItemsCount(int charid) {
         int count = 0;

         for (AuctionItemPackage aitem : new ArrayList<>(items)) {
            int type = aitem.getType(charid == aitem.getOwnerId(), false);
            if ((type == 3 || type == 0) && aitem.getOwnerId() == charid) {
               count++;
            }
         }

         return count;
      }

      public static int getReturnItemCount(int charid) {
         int count = 0;

         for (AuctionItemPackage aitem : new ArrayList<>(items)) {
            int type = aitem.getType(charid == aitem.getOwnerId(), false);
            if (type == 2 && aitem.getBuyer() == charid) {
               count++;
            }
         }

         return count;
      }

      public static final synchronized void save() {
         DBConnection db = new DBConnection();

         try (Connection con = DBConnection.getConnection()) {
            List<Pair<Item, MapleInventoryType>> itemlist = new ArrayList<>();

            for (AuctionItemPackage aitem : new ArrayList<>(items)) {
               itemlist.add(new Pair<>(aitem.getItem(), GameConstants.getInventoryType(aitem.getItem().getItemId())));
            }

            ItemLoader.AUCTION.saveItems(itemlist, con, -1, items);
         } catch (SQLException var7) {
            var7.printStackTrace();
         }
      }
   }

   public static class BossPartyRecruiment {
      private static List<objects.context.party.Party> queue = new ArrayList<>();

      public static void checkExpiredRecruiment() {
         queue.stream()
               .collect(Collectors.toList())
               .forEach(
                     party -> {
                        if (party.getBossPartyRecruiment().getEndRecruimentTime() <= System.currentTimeMillis()) {
                           party.getBossPartyRecruiment().setEntry(null);
                           party.getPartyMember().clearRegisterRequestPlayer();
                           queue.remove(party);

                           for (PartyMemberEntry entry : party.getPartyMemberList()) {
                              int ch = Center.Find.findChannel(entry.getName());
                              if (ch > 0) {
                                 MapleCharacter player = GameServer.getInstance(ch).getPlayerStorage()
                                       .getCharacterByName(entry.getName());
                                 if (player != null) {
                                    PacketEncoder packet = new PacketEncoder();
                                    objects.context.party.Party.PartyPacket.PartyDataUpdate p = new objects.context.party.Party.PartyPacket.PartyDataUpdate(
                                          party, player.getClient().getChannel());
                                    p.encode(packet);
                                    player.send(packet.getPacket());
                                    player.dropMessage(5,
                                          "The party recruitment period has expired, so the recruitment has been completed.");
                                 }
                              }
                           }
                        }
                     });
      }

      public static void registerBossPartyRecruiment(objects.context.party.Party party) {
         removeBossPartyRecruiment(party);
         queue.add(party);
      }

      public static void displayBossPartyRecruiment(MapleCharacter player, int bossType, byte difficulty) {
         List<objects.context.party.Party> list = new ArrayList<>();
         queue.stream().collect(Collectors.toList()).forEach(p -> {
            objects.context.party.boss.BossPartyRecruiment recruiment = p.getBossPartyRecruiment();
            if (recruiment != null && recruiment.getEntry() != null) {
               list.add(p);
            }
         });
         PacketEncoder packet = new PacketEncoder();
         objects.context.party.Party.BossPartyRecruimentPacket.DisplayList display = new objects.context.party.Party.BossPartyRecruimentPacket.DisplayList(
               bossType, difficulty, list);
         display.encode(packet);
         player.send(packet.getPacket());
      }

      public static void openBossPartyRecruiment(MapleCharacter player, int id, int bossType, int bossDifficulty) {
         AtomicBoolean find = new AtomicBoolean(false);
         queue.stream().collect(Collectors.toList()).forEach(p -> {
            if (p.getBossPartyRecruiment() != null && p.getId() == id
                  && p.getBossPartyRecruiment().getEndRecruimentTime() >= System.currentTimeMillis()) {
               find.set(true);
               PacketEncoder packetx = new PacketEncoder();
               objects.context.party.Party.BossPartyRecruimentPacket.Open openx = new objects.context.party.Party.BossPartyRecruimentPacket.Open(
                     p);
               openx.encode(packetx);
               player.send(packetx.getPacket());
            }
         });
         if (!find.get()) {
            List<objects.context.party.Party> list = new ArrayList<>();
            queue.stream()
                  .collect(Collectors.toList())
                  .forEach(
                        p -> {
                           objects.context.party.boss.BossPartyRecruiment recruiment = p.getBossPartyRecruiment();
                           if (recruiment != null
                                 && recruiment.getEntry() != null
                                 && recruiment.getBossType() == bossType
                                 && recruiment.getEntry().getBossDifficulty() == bossDifficulty) {
                              list.add(p);
                           }
                        });
            PacketEncoder packet = new PacketEncoder();
            objects.context.party.Party.BossPartyRecruimentPacket.Open open = new objects.context.party.Party.BossPartyRecruimentPacket.Open(
                  id, bossType, (byte) bossDifficulty, list);
            open.encode(packet);
            player.send(packet.getPacket());
         }
      }

      public static void removeBossPartyRecruiment(objects.context.party.Party party) {
         for (objects.context.party.Party p : new ArrayList<>(queue)) {
            if (p.getId() == party.getId()) {
               queue.remove(p);
            }
         }
      }

      public static void joinRequestFromBossPartyRecruiment(MapleCharacter player, int id) {
         boolean find = false;

         for (objects.context.party.Party p : new ArrayList<>(queue)) {
            if (p.getBossPartyRecruiment() != null && p.getId() == id) {
               if (p.getPartyMember().getPartyMemberList().size() >= 6) {
                  player.dropMessage(1, "The party is full, so you cannot apply.");
                  return;
               }

               find = true;
               PartyMemberEntry entry = new PartyMemberEntry(player);
               p.getPartyMember().addRegisterRequestPlayer(entry);
               PacketEncoder packet = new PacketEncoder();
               objects.context.party.Party.BossPartyRecruimentPacket.UpdateJoinMember member = new objects.context.party.Party.BossPartyRecruimentPacket.UpdateJoinMember(
                     p.getId(), entry);
               member.encode(packet);
               player.send(packet.getPacket());
               packet = new PacketEncoder();
               objects.context.party.Party.BossPartyRecruimentMessage.CompleteJoinRequest message = new objects.context.party.Party.BossPartyRecruimentMessage.CompleteJoinRequest(
                     p.getLeader().getName(), p.getId(), p);
               message.encode(packet);
               player.send(packet.getPacket());
               packet = new PacketEncoder();
               objects.context.party.Party.RequestJoinPartyFromRecruiment.RequestJoinParty join = new objects.context.party.Party.RequestJoinPartyFromRecruiment.RequestJoinParty(
                     entry);
               join.encode(packet);

               for (PartyMemberEntry e : p.getPartyMemberList()) {
                  int ch = Center.Find.findChannel(e.getName());
                  if (ch > 0) {
                     MapleCharacter player_ = GameServer.getInstance(ch).getPlayerStorage()
                           .getCharacterByName(e.getName());
                     if (player_ != null) {
                        player_.send(packet.getPacket());
                     }
                  }
               }
            }
         }

         if (!find) {
            player.dropMessage(1, "The party does not exist.");
         }
      }
   }

   public static class Broadcast {
      public static void broadcastEliteBossWMI(int mapID, int state, int eliteMobID, int remainTime,
            GameServer channel) {
         PacketEncoder packet = new PacketEncoder();
         packet.writeShort(SendPacketOpcode.ELITE_MOB_WORLD_MAP_NOTICE.getValue());
         packet.write(state);
         packet.write(0);
         packet.writeInt(mapID);
         if (state != 1) {
            packet.writeInt(eliteMobID);
            packet.writeInt(remainTime * 1000);
         }

         packet.write(0);
         channel.broadcastPacket(packet.getPacket());
      }

      public static void broadcastSmega(byte[] message) {
         for (GameServer cs : GameServer.getAllInstances()) {
            cs.broadcastSmega(message);
         }
      }

      public static void broadcastGMMessage(byte[] message) {
         for (GameServer cs : GameServer.getAllInstances()) {
            cs.broadcastGMMessage(message);
         }
      }

      public static void broadcastMessage(byte[] message) {
         for (GameServer cs : GameServer.getAllInstances()) {
            cs.broadcastMessage(message);
         }
      }

      public static void broadcastMessageCheckQuest(byte[] message, String questStr) {
         for (GameServer cs : GameServer.getAllInstances()) {
            cs.broadcastPacketCheckQuest(message, questStr, false);
         }
      }

      public static void broadcastMessageCheckQuest(byte[] message, String questStr, boolean force) {
         for (GameServer cs : GameServer.getAllInstances()) {
            cs.broadcastPacketCheckQuest(message, questStr, force);
         }
      }

      public static void broadcastMessageLadderGame(byte[] message) {
         for (GameServer cs : GameServer.getAllInstances()) {
            cs.broadcastPacketLadderGame(message);
         }
      }

      public static void broadcastGachaponMessage(String message, int gachaponItemID, Item item) {
         for (GameServer cs : GameServer.getAllInstances()) {
            cs.broadcastMessage(CWvsContext.getBroadcastMsgGachapon(message, gachaponItemID, item));
         }
      }

      public static void sendPacket(List<Integer> targetIds, byte[] packet, int exception) {
         for (int i : targetIds) {
            if (i != exception) {
               int ch = Center.Find.findChannel(i);
               if (ch >= 0) {
                  MapleCharacter c = GameServer.getInstance(ch).getPlayerStorage().getCharacterById(i);
                  if (c != null) {
                     c.getClient().getSession().writeAndFlush(packet);
                  }
               }
            }
         }
      }

      public static void sendPacket(int targetId, byte[] packet) {
         int ch = Center.Find.findChannel(targetId);
         if (ch >= 0) {
            MapleCharacter c = GameServer.getInstance(ch).getPlayerStorage().getCharacterById(targetId);
            if (c != null) {
               c.getClient().getSession().writeAndFlush(packet);
            }
         }
      }

      public static void sendGuildPacket(int targetIds, byte[] packet, int exception, int guildid) {
         if (targetIds != exception) {
            int ch = Center.Find.findChannel(targetIds);
            if (ch >= 0) {
               MapleCharacter c = GameServer.getInstance(ch).getPlayerStorage().getCharacterById(targetIds);
               if (c != null && c.getGuildId() == guildid) {
                  c.getClient().getSession().writeAndFlush(packet);
               }
            }
         }
      }
   }

   public static class Buddy {
      public static void buddyChat(
            int[] recipientCharacterIds,
            MapleCharacter user,
            String nameFrom,
            String chattext,
            Item item,
            String itemName,
            int achievementID,
            long achievementTime) {
         for (int characterId : recipientCharacterIds) {
            int ch = Center.Find.findChannel(characterId);
            if (ch >= 0) {
               MapleCharacter chr = GameServer.getInstance(ch).getPlayerStorage().getCharacterById(characterId);
               if (chr != null && chr.getBuddylist().containsVisible(user.getAccountID())) {
                  chr.getClient()
                        .getSession()
                        .writeAndFlush(
                              CField.multiChat(
                                    user, chattext, 0, item, itemName, achievementID, achievementTime,
                                    new ReportLogEntry(user.getName(), chattext, user.getId())));
                  if (chr.getClient().isMonitored()) {
                     Center.Broadcast.broadcastGMMessage(
                           CWvsContext.serverNotice(6,
                                 "[GM Message] " + nameFrom + " said to " + chr.getName() + " (Buddy): " + chattext));
                  }
               }
            }
         }
      }

      public static void updateBuddies(String name, int characterId, int channel, int[] buddies, int accId,
            boolean offline) {
         try {
            for (int buddy : buddies) {
               int ch = Center.Find.findAccChannel(buddy);
               if (ch >= 0) {
                  MapleClient c = GameServer.getInstance(ch).getPlayerStorage().getClientById(buddy);
                  if (c != null && c.getPlayer() != null) {
                     FriendEntry ble = c.getPlayer().getBuddylist().get(accId);
                     if (ble != null && ble.isVisible()) {
                        int mcChannel;
                        if (offline) {
                           ble.setChannel(-1);
                           mcChannel = -1;
                        } else {
                           ble.setChannel(channel);
                           mcChannel = channel - 1;
                        }

                        ble.setName(name);
                        ble.setCharacterId(characterId);
                        c.getSession().writeAndFlush(CWvsContext.BuddylistPacket
                              .updateBuddyChannel(ble.getCharacterId(), accId, mcChannel, name));
                     }
                  }
               }
            }
         } catch (Exception var14) {
            System.out.println("[Error] Error occurred during updateBuddies function execution " + var14);
            var14.printStackTrace();
         }
      }

      public static void buddyChanged(
            int cid, int cidFrom, int accId, String name, int channel, Friend.BuddyOperation operation, int level,
            int job, String memo) {
         int ch = Center.Find.findChannel(cid);
         if (ch > 0) {
            MapleCharacter addChar = GameServer.getInstance(ch).getPlayerStorage().getCharacterById(cid);
            if (addChar != null) {
               Friend buddylist = addChar.getBuddylist();
               switch (operation) {
                  case ADDED:
                     if (buddylist.contains(accId)) {
                        buddylist.put(
                              new FriendEntry(name, accId, cidFrom, "Unassigned", channel, true, level, job, memo));
                        addChar.getClient()
                              .getSession()
                              .writeAndFlush(CWvsContext.BuddylistPacket.updateBuddyChannel(cidFrom, accId, channel,
                                    buddylist.get(accId).getName()));
                     }
                     break;
                  case DELETED:
                     if (buddylist.contains(accId)) {
                        buddylist.put(new FriendEntry(name, accId, cidFrom, "Unassigned", -1,
                              buddylist.get(accId).isVisible(), level, job, memo));
                        addChar.getClient()
                              .getSession()
                              .writeAndFlush(CWvsContext.BuddylistPacket.updateBuddyChannel(cidFrom, accId, -1,
                                    buddylist.get(accId).getName()));
                     }
               }
            }
         }
      }

      public static Friend.BuddyAddResult requestBuddyAdd(
            String addName, int channelFrom, int cidFrom, int accIdFrom, String nameFrom, int levelFrom, int jobFrom,
            String groupName, String memo) {
         for (GameServer server : GameServer.getAllInstances()) {
            MapleCharacter addChar = server.getPlayerStorage().getCharacterByName(addName);
            if (addChar != null) {
               Friend buddylist = addChar.getBuddylist();
               if (buddylist.isFull()) {
                  return Friend.BuddyAddResult.BUDDYLIST_FULL;
               }

               if (addChar.getQuestNoAdd(MapleQuest.getInstance(122902)) != null) {
                  return Friend.BuddyAddResult.ADD_BLOCKED;
               }

               if (!buddylist.contains(accIdFrom)) {
                  buddylist.addBuddyRequest(addChar.getClient(), cidFrom, accIdFrom, nameFrom, channelFrom, levelFrom,
                        jobFrom, groupName, memo);
               } else if (buddylist.containsVisible(accIdFrom)) {
                  return Friend.BuddyAddResult.ALREADY_ON_LIST;
               }
            }
         }

         return Friend.BuddyAddResult.OK;
      }

      public static void loggedOn(String name, int characterId, int accId, int channel, int[] buddies) {
         updateBuddies(name, characterId, channel, buddies, accId, false);
      }

      public static void loggedOff(String name, int characterId, int accId, int channel, int[] buddies) {
         updateBuddies(name, characterId, channel, buddies, accId, true);
      }
   }

   public static class Find {
      private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
      private static HashMap<Integer, Integer> idToChannel = new HashMap<>();
      private static HashMap<Integer, Integer> accIdToChannel = new HashMap<>();
      private static HashMap<String, Integer> nameToChannel = new HashMap<>();

      public static void register(int id, int accId, String name, int channel) {
         lock.writeLock().lock();

         try {
            idToChannel.put(id, channel);
            accIdToChannel.put(accId, channel);
            nameToChannel.put(name.toLowerCase(), channel);
         } finally {
            lock.writeLock().unlock();
         }
      }

      public static void forceDeregister(int id) {
         lock.writeLock().lock();

         try {
            idToChannel.remove(id);
         } finally {
            lock.writeLock().unlock();
         }
      }

      public static void forceDeregister(String id) {
         lock.writeLock().lock();

         try {
            nameToChannel.remove(id.toLowerCase());
         } finally {
            lock.writeLock().unlock();
         }
      }

      public static void forceDeregister(int id, String name, int accId) {
         lock.writeLock().lock();

         try {
            idToChannel.remove(id);
            nameToChannel.remove(name.toLowerCase());
            accIdToChannel.remove(accId);
         } finally {
            lock.writeLock().unlock();
         }
      }

      public static int findAccChannel(int id) {
         lock.readLock().lock();

         Integer ret;
         try {
            ret = accIdToChannel.get(id);
         } finally {
            lock.readLock().unlock();
         }

         if (ret != null) {
            if (ret != -10 && GameServer.getInstance(ret) == null) {
               forceDeregister(id);
               return -1;
            } else {
               return ret;
            }
         } else {
            return -1;
         }
      }

      public static int findChannel(int id) {
         lock.readLock().lock();

         Integer ret;
         try {
            ret = idToChannel.get(id);
         } finally {
            lock.readLock().unlock();
         }

         if (ret != null) {
            if (ret != -10 && GameServer.getInstance(ret) == null) {
               forceDeregister(id);
               return -1;
            } else {
               return ret;
            }
         } else {
            return -1;
         }
      }

      public static int findChannel(String st) {
         lock.readLock().lock();

         Integer ret;
         try {
            ret = nameToChannel.get(st.toLowerCase());
         } finally {
            lock.readLock().unlock();
         }

         if (ret != null) {
            if (ret != -10 && GameServer.getInstance(ret) == null) {
               forceDeregister(st);
               return -1;
            } else {
               return ret;
            }
         } else {
            for (GameServer gs : GameServer.getAllInstances()) {
               if (gs.getChannel() > 0) {
                  MapleCharacter findchr = gs.getPlayerStorage().getCharacterByName(st);
                  if (findchr != null) {
                     int accid = findchr.getAccountID();
                     int chrid = findchr.getId();
                     int channel = gs.getChannel();
                     forceDeregister(chrid, st, accid);
                     register(chrid, accid, st, channel);
                     return channel;
                  }
               }
            }

            return -1;
         }
      }

      public static AccountIdChannelPair[] multiBuddyFind(Friend bl, int[] accIds) {
         List<AccountIdChannelPair> foundsChars = new ArrayList<>(accIds.length);

         for (int i : accIds) {
            int ret = findAccChannel(i);
            if (ret >= 0) {
               MapleClient c = GameServer.getInstance(ret).getPlayerStorage().getClientById(i);
               if (bl.contains(i) && c != null) {
                  FriendEntry ble = bl.get(i);
                  ble.setCharacterId(c.getPlayer().getId());
                  ble.setName(c.getPlayer().getName());
               }

               foundsChars.add(new AccountIdChannelPair(i, ret));
            }
         }

         Collections.sort(foundsChars);
         return foundsChars.toArray(new AccountIdChannelPair[foundsChars.size()]);
      }
   }

   public static class GameWaitQueue {
      private static final Map<Integer, WaitQueue> queues = new HashMap<>();
      private static final AtomicInteger SN = new AtomicInteger(0);

      public static void deleteQueue(MapleCharacter player) {
         for (Entry<Integer, WaitQueue> entry : new HashMap<>(queues).entrySet()) {
            for (MapleCharacter p : entry.getValue().getPlayers()) {
               if (p.getId() == player.getId()) {
                  entry.getValue().sendWaitQueueSuccess(player, WaitQueueResult.DeleteWaiting);

                  for (MapleCharacter p_ : new ArrayList<>(entry.getValue().getPlayers())) {
                     if (p_.getId() != player.getId()) {
                        entry.getValue().sendWaitQueueSuccess(p, WaitQueueResult.CancelByAnother);
                     }
                  }

                  entry.getValue().sendWaitQueueType(WaitQueueType.Cancel);
                  entry.getValue().deletePlayer(player);
                  break;
               }
            }
         }
      }

      public static void deleteQueue(MapleCharacter player, int queueType, int queueID) {
         WaitQueue queue = queues.get(queueID);
         if (queue != null) {
            if (queue.getPlayers().size() >= 2) {
               queue.sendWaitQueueSuccess(player, WaitQueueResult.DeleteWaiting);
               queue.sendWaitQueueTypeOnce(WaitQueueType.Cancel, player);
               queue.deletePlayer(player);

               for (MapleCharacter p : new ArrayList<>(queue.getPlayers())) {
                  if (p.getId() != player.getId()) {
                     p.dropMessage(5,
                           player.getName() + " has left the queue. [Players in Queue: " + queue.getPlayers().size()
                                 + "]");
                  }
               }
            } else {
               queue.sendWaitQueueSuccess(player, WaitQueueResult.DeleteWaiting);

               for (MapleCharacter px : new ArrayList<>(queue.getPlayers())) {
                  if (px.getId() != player.getId()) {
                     queue.sendWaitQueueSuccess(px, WaitQueueResult.CancelByAnother);
                     queue.sendWaitQueueType(WaitQueueType.Cancel);
                     queue.deletePlayer(px);
                  }
               }

               queue.sendWaitQueueType(WaitQueueType.Cancel);
               queue.deletePlayer(player);
            }

            if (queue.getWaitPlayerCount() <= 0 || queue.getAcceptPlayerCount() > 0) {
               queues.remove(queueID);
            }

            queue.clearAcceptPlayers();
         }
      }

      public static void acceptCompleteUser(MapleCharacter player, int queueType, int queueID) {
         WaitQueue queue = queues.get(queueID);
         if (queue != null) {
            queue.addAcceptPlayer(player);
            if (queue.getAcceptPlayerCount() >= queue.getMaxUserCount()) {
               queue.sendWaitQueueType(WaitQueueType.Cancel);
               queue.allPlayerTransferField();
               queues.remove(queueID);
            }
         }
      }

      public static void cancelCompleteUser(MapleCharacter player, int queueType, int queueID) {
         WaitQueue queue = queues.get(queueID);
         if (queue != null) {
            for (MapleCharacter p : new ArrayList<>(queue.getPlayers())) {
               if (p.getId() != player.getId()) {
                  queue.sendWaitQueueSuccess(p, WaitQueueResult.CancelByAnother);
                  queue.sendWaitQueueType(WaitQueueType.Cancel);
                  p.dropMessage(5, player.getName() + " has cancelled the game.");
               }
            }

            queue.sendWaitQueueSuccess(player, WaitQueueResult.DeleteWaiting);
            queue.sendWaitQueueType(WaitQueueType.Cancel);
            queues.remove(queueID);
         }
      }

      public static void registerQueue(int fieldID, MapleCharacter player) {
         WaitQueue findQueue = null;

         for (Entry<Integer, WaitQueue> entry : new HashMap<>(queues).entrySet()) {
            if (entry.getValue().getFieldID() == fieldID) {
               findQueue = entry.getValue();
               break;
            }
         }

         if (findQueue != null) {
            for (Entry<Integer, WaitQueue> entryx : new HashMap<>(queues).entrySet()) {
               for (MapleCharacter p : entryx.getValue().getPlayers()) {
                  if (p.getId() == player.getId()) {
                     return;
                  }
               }
            }

            if (findQueue.getWaitPlayerCount() >= findQueue.getMaxUserCount()) {
               return;
            }

            findQueue.addPlayer(player);
            findQueue.sendWaitQueueSuccess(player, WaitQueueResult.RegisterWaiting);
            if (findQueue.getWaitPlayerCount() >= findQueue.getMaxUserCount()) {
               findQueue.sendWaitQueueType(WaitQueueType.End);
               return;
            }
         } else {
            switch (fieldID) {
               case 993189400:
                  findQueue = new WaitQueue(4, 2, SN.getAndIncrement(), fieldID, player);
                  queues.put(findQueue.getWaitingQueueID(), findQueue);
                  findQueue.sendWaitQueueSuccess(player, WaitQueueResult.RegisterWaiting);
                  break;
               case 993189600:
                  findQueue = new WaitQueue(2, 2, SN.getAndIncrement(), fieldID, player);
                  queues.put(findQueue.getWaitingQueueID(), findQueue);
                  findQueue.sendWaitQueueSuccess(player, WaitQueueResult.RegisterWaiting);
                  break;
               case 993189800:
                  findQueue = new WaitQueue(2, 2, SN.getAndIncrement(), fieldID, player);
                  queues.put(findQueue.getWaitingQueueID(), findQueue);
                  findQueue.sendWaitQueueSuccess(player, WaitQueueResult.RegisterWaiting);
                  break;
               case 993195100:
                  findQueue = new WaitQueue(10, 2, SN.getAndIncrement(), fieldID, player);
                  queues.put(findQueue.getWaitingQueueID(), findQueue);
                  findQueue.sendWaitQueueSuccess(player, WaitQueueResult.RegisterWaiting);
            }
         }

         System.out.println("Total GameWaitQueue count : " + queues.size());
      }

      public static Map<Integer, WaitQueue> retrieveAllQueues() {
         return queues;
      }

      private static String getMiniGameName(int fieldId) {
         if (fieldId == 993189800) {
            return "Psychic Yutnori";
         } else if (fieldId == 993189400) {
            return "Maple One Card";
         } else if (fieldId == 993195100) {
            return "Maple Soccer";
         } else {
            return fieldId == 993189600 ? "Maple Battle Revers" : "";
         }
      }
   }

   public static class Guild {
      private static final Map<Integer, objects.context.guild.Guild> guilds = new LinkedHashMap<>();
      private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

      public static void LoadAllGuild() {
         DBConnection db = new DBConnection();

         try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT guildid FROM guilds");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
               int guildid = rs.getInt("guildid");
               objects.context.guild.Guild guild = getGuild(guildid);
               if (guild != null && !guilds.equals(guild)) {
                  guilds.put(guildid, guild);
               }
            }

            rs.close();
            ps.close();
         } catch (Exception var8) {
            System.out.println("Guild Loading Error Occurred");
            var8.printStackTrace();
         }
      }

      public static void addLoadedGuild(objects.context.guild.Guild f) {
         if (f.isProper()) {
            guilds.put(f.getId(), f);
         }
      }

      public static int createGuild(int leaderId, String name) {
         return objects.context.guild.Guild.createGuild(leaderId, name);
      }

      public static objects.context.guild.Guild getGuild(int id) {
         objects.context.guild.Guild ret = null;
         lock.readLock().lock();

         try {
            ret = guilds.get(id);
         } finally {
            lock.readLock().unlock();
         }

         if (ret == null) {
            lock.writeLock().lock();

            Object var2;
            try {
               ret = new objects.context.guild.Guild(id);
               if (ret != null && ret.getId() > 0 && ret.isProper()) {
                  guilds.put(id, ret);
                  return ret;
               }

               var2 = null;
            } finally {
               lock.writeLock().unlock();
            }

            return (objects.context.guild.Guild) var2;
         } else {
            return ret;
         }
      }

      public static List<objects.context.guild.Guild> getGuildForJoinRequest(int playerID) {
         List<objects.context.guild.Guild> ret = new LinkedList<>();
         lock.readLock().lock();

         try {
            guilds.values().stream().collect(Collectors.toList()).forEach(g -> {
               boolean find = false;

               for (objects.context.guild.Guild.JoinRequester j : new HashMap<>(g.getJoinRequesters()).values()) {
                  if (j.getPlayerID() == playerID) {
                     find = true;
                     break;
                  }
               }

               if (find) {
                  ret.add(g);
               }
            });
         } finally {
            lock.readLock().unlock();
         }

         return ret;
      }

      public static List<objects.context.guild.Guild> getGuildForSearch(
            int world, int minGuildLevel, int maxGuildLevel, int minMemberSize, int maxMemberSize, int minAvgLevel,
            int maxAvgLevel) {
         List<objects.context.guild.Guild> ret = new LinkedList<>();
         lock.readLock().lock();

         try {
            guilds.values()
                  .stream()
                  .filter(g -> g.getLevel() >= minGuildLevel && g.getLevel() <= maxGuildLevel)
                  .filter(g -> g.getMembers().size() >= minMemberSize && g.getMembers().size() <= maxMemberSize)
                  .filter(g -> g.getAvgLevel() >= minAvgLevel && g.getAvgLevel() <= maxAvgLevel)
                  .collect(Collectors.toList())
                  .forEach(ret::add);
         } finally {
            lock.readLock().unlock();
         }

         return ret;
      }

      public static List<objects.context.guild.Guild> getAllRecruitmentGuilds() {
         lock.readLock().lock();

         List var0;
         try {
            var0 = guilds.values().stream().collect(Collectors.toList());
         } finally {
            lock.readLock().unlock();
         }

         return var0;
      }

      public static List<objects.context.guild.Guild.RecruitmentGuildData> getAllRecruitmentGuildByPlayerID(
            int playerID) {
         List<objects.context.guild.Guild.RecruitmentGuildData> temp = new LinkedList<>();
         lock.readLock().lock();

         Object var2;
         try {
            guilds.values().stream().collect(Collectors.toList()).forEach(g -> {
               if (g.getJoinRequesters().containsKey(playerID)) {
                  temp.add(new objects.context.guild.Guild.RecruitmentGuildData(g, playerID, false));
               }
            });
            var2 = temp;
         } finally {
            lock.readLock().unlock();
         }

         return (List<objects.context.guild.Guild.RecruitmentGuildData>) var2;
      }

      public static List<objects.context.guild.Guild> getGuildForSearch(int worldid, byte mode, String text,
            boolean likeSearch) {
         List<objects.context.guild.Guild> ret = new LinkedList<>();
         lock.readLock().lock();

         try {
            if (mode == 1 || mode == 2) {
               List<objects.context.guild.Guild> guild;
               if (likeSearch) {
                  guild = guilds.values().stream().filter(g -> g.getName().contains(text)).collect(Collectors.toList());
               } else {
                  guild = guilds.values().stream().filter(g -> g.getName().equals(text)).collect(Collectors.toList());
               }

               if (guild.size() > 0) {
                  guild.forEach(g -> ret.add(g));
               }
            }

            if (mode == 1 || mode == 3) {
               List<objects.context.guild.Guild> var10;
               if (likeSearch) {
                  var10 = guilds.values().stream().filter(g -> g.getLeaderName().contains(text))
                        .collect(Collectors.toList());
               } else {
                  var10 = guilds.values().stream().filter(g -> g.getLeaderName().equals(text))
                        .collect(Collectors.toList());
               }

               if (var10.size() > 0) {
                  var10.forEach(g -> {
                     if (ret.stream().filter(gg -> gg.getId() == g.getId()).count() == 0L) {
                        ret.add(g);
                     }
                  });
               }
            }
         } finally {
            lock.readLock().unlock();
         }

         return ret;
      }

      public static objects.context.guild.Guild getGuildByName(String guildName) {
         lock.readLock().lock();

         objects.context.guild.Guild var3;
         try {
            Iterator var1 = guilds.values().iterator();

            objects.context.guild.Guild g;
            do {
               if (!var1.hasNext()) {
                  return null;
               }

               g = (objects.context.guild.Guild) var1.next();
            } while (!g.getName().equalsIgnoreCase(guildName));

            var3 = g;
         } finally {
            lock.readLock().unlock();
         }

         return var3;
      }

      public static Collection<objects.context.guild.Guild> getGuilds() {
         return guilds.values();
      }

      public static objects.context.guild.Guild getGuild(MapleCharacter mc) {
         return getGuild(mc.getGuildId());
      }

      public static void setGuildMemberOnline(GuildCharacter mc, boolean bOnline, int channel) {
         setGuildMemberOnline(mc, bOnline, channel, false);
      }

      public static void setGuildMemberOnline(GuildCharacter mc, boolean bOnline, int channel, boolean forceBroadcast) {
         setGuildMemberOnline(mc, bOnline, channel, forceBroadcast, true);
      }

      public static void setGuildMemberOnline(GuildCharacter mc, boolean bOnline, int channel, boolean forceBroadcast,
            boolean show) {
         objects.context.guild.Guild g = getGuild(mc.getGuildId());
         if (g != null) {
            g.setOnline(mc.getId(), mc.getName(), bOnline, channel, forceBroadcast, show);
         }
      }

      public static void guildPacket(int gid, byte[] message) {
         objects.context.guild.Guild g = getGuild(gid);
         if (g != null) {
            g.broadcast(message);
         }
      }

      public static int addGuildMember(GuildCharacter mc) {
         objects.context.guild.Guild g = getGuild(mc.getGuildId());
         return g != null ? g.addGuildMember(mc) : 0;
      }

      public static void leaveGuild(GuildCharacter mc) {
         objects.context.guild.Guild g = getGuild(mc.getGuildId());
         if (g != null) {
            g.leaveGuild(mc);
         }
      }

      public static void guildChat(int gid, MapleCharacter chr, int cid, String msg, Item item, String itemName,
            int achievementID, long achievementTime) {
         objects.context.guild.Guild g = getGuild(gid);
         if (g != null) {
            g.guildChat(chr, cid, msg, item, itemName, achievementID, achievementTime);
         }
      }

      public static void changeRank(int gid, int cid, int newRank) {
         objects.context.guild.Guild g = getGuild(gid);
         if (g != null) {
            g.changeRank(cid, newRank);
         }
      }

      public static void expelMember(GuildCharacter initiator, String name, int cid) {
         objects.context.guild.Guild g = getGuild(initiator.getGuildId());
         if (g != null) {
            g.expelMember(initiator, name, cid);
         }
      }

      public static void setGuildNotice(int gid, String notice, int playerID) {
         objects.context.guild.Guild g = getGuild(gid);
         if (g != null) {
            g.setGuildNotice(notice, playerID);
         }
      }

      public static void editJoinSetting(int guildID, int playerID, boolean allowJoinRequest, int connectTimeFlag,
            int activityFlag, int ageGroupFlag) {
         objects.context.guild.Guild g = getGuild(guildID);
         if (g != null) {
            g.setJoinSetting(playerID, allowJoinRequest, connectTimeFlag, activityFlag, ageGroupFlag);
         }
      }

      public static void removeAllJoinRequester(int playerID) {
         lock.readLock().lock();

         try {
            guilds.values().stream().collect(Collectors.toList()).forEach(g -> {
               if (g.getJoinRequesters().containsKey(playerID)) {
                  g.removeJoinRequester(playerID, true);
               }
            });
         } finally {
            lock.readLock().unlock();
         }
      }

      public static void setGuildLeader(int gid, int cid) {
         objects.context.guild.Guild g = getGuild(gid);
         if (g != null) {
            g.changeGuildLeader(cid);
         }
      }

      public static int getSkillLevel(int gid, int sid) {
         objects.context.guild.Guild g = getGuild(gid);
         return g != null ? g.getSkillLevel(sid) : 0;
      }

      public static boolean purchaseSkill(int gid, int sid, int levelUp, String name, int cid) {
         objects.context.guild.Guild g = getGuild(gid);
         return g != null ? g.purchaseSkill(sid, levelUp, name, cid) : false;
      }

      public static boolean purchaseNobleSkill(int gid, int sid, String name, int cid, int inclevel) {
         objects.context.guild.Guild g = getGuild(gid);
         return g != null ? g.purchaseNobleSkill(sid, name, cid, inclevel) : false;
      }

      public static void memberLevelJobUpdate(GuildCharacter mc) {
         objects.context.guild.Guild g = getGuild(mc.getGuildId());
         if (g != null) {
            g.memberLevelJobUpdate(mc);
         }
      }

      public static void changeRankTitleRole(boolean add, int gid, int playerID, int index, String newName,
            int newRole) {
         objects.context.guild.Guild g = getGuild(gid);
         if (g != null) {
            g.changeRankTitleRole(add, playerID, index, newName, newRole);
         }
      }

      public static void removeRankTitleRole(int gid, int playerID, int index) {
         objects.context.guild.Guild g = getGuild(gid);
         if (g != null) {
            g.removeRankTitleRole(playerID, index);
         }
      }

      public static boolean setGuildEmblem(int gid, short bg, byte bgcolor, short logo, byte logocolor,
            objects.context.guild.Guild.BCOp bcop, byte[] imageData) {
         objects.context.guild.Guild g = getGuild(gid);
         return g != null ? g.setGuildEmblem(bg, bgcolor, logo, logocolor, bcop, imageData) : false;
      }

      public static void attendanceCheck(int gid, int playerID, int date) {
         objects.context.guild.Guild g = getGuild(gid);
         if (g != null) {
            g.attendanceCheck(playerID, date);
         }
      }

      public static void disbandGuild(int gid) {
         objects.context.guild.Guild g = getGuild(gid);
         List<Integer> memberIds = new ArrayList<>();
         lock.writeLock().lock();

         try {
            if (g != null) {
               for (GuildCharacter gchr : g.getMembers()) {
                  memberIds.add(gchr.getId());
               }

               g.disbandGuild();
               guilds.remove(gid);
            }
         } finally {
            lock.writeLock().unlock();
         }

         for (int memberId : memberIds) {
            boolean find = false;

            for (GameServer cs : GameServer.getAllInstances()) {
               for (MapleCharacter chr : cs.getPlayerStorage().getAllCharacters()) {
                  if (chr.getId() == memberId) {
                     chr.setGuildRank((byte) 5);
                     find = true;
                     break;
                  }
               }
            }

            if (!find) {
               for (MapleCharacter chrx : CashShopServer.getPlayerStorage().getAllCharacters()) {
                  if (chrx.getId() == memberId) {
                     chrx.setGuildRank((byte) 5);
                     find = true;
                     break;
                  }
               }

               if (!find) {
                  for (MapleCharacter chrxx : AuctionServer.getPlayerStorage().getAllCharacters()) {
                     if (chrxx.getId() == memberId) {
                        chrxx.setGuildRank((byte) 5);
                        find = true;
                        break;
                     }
                  }

                  if (!find) {
                     try (Connection con = DBConnection.getConnection()) {
                        try (PreparedStatement ps = con
                              .prepareStatement("UPDATE `characters` SET `guildrank` = 5 WHERE `id` = ?")) {
                           ps.setInt(1, memberId);
                           ps.executeUpdate();
                           ps.close();
                        }

                        con.close();
                     } catch (Exception var20) {
                        FileoutputUtil.outputFileErrorReason("Log_DBProcessor_Except.rtf", "DB Error Occurred", var20);
                     }
                  }
               }
            }
         }
      }

      public static void deleteGuildCharacter(int guildid, int charid) {
         objects.context.guild.Guild g = getGuild(guildid);
         if (g != null) {
            GuildCharacter mc = g.getMGC(charid);
            if (mc != null) {
               if (mc.getGuildRank() > 1) {
                  g.leaveGuild(mc);
               } else {
                  g.disbandGuild();
               }
            }
         }
      }

      public static boolean increaseGuildCapacity(int gid, boolean b) {
         objects.context.guild.Guild g = getGuild(gid);
         return g != null ? g.increaseCapacity(b) : false;
      }

      public static void gainContribution(int gid, int amount) {
         objects.context.guild.Guild g = getGuild(gid);
         if (g != null) {
            g.gainGP(amount);
         }
      }

      public static void gainContribution(int gid, int amount, int cid) {
         objects.context.guild.Guild g = getGuild(gid);
         if (g != null) {
            g.gainGP(amount, false, cid);
         }
      }

      public static int getGP(int gid) {
         objects.context.guild.Guild g = getGuild(gid);
         return g != null ? g.getGP() : 0;
      }

      public static int getInvitedId(int gid) {
         objects.context.guild.Guild g = getGuild(gid);
         return g != null ? g.getInvitedId() : 0;
      }

      public static void setInvitedId(int gid, int inviteid) {
         objects.context.guild.Guild g = getGuild(gid);
         if (g != null) {
            g.setInvitedId(inviteid);
         }
      }

      public static int getGuildLeader(int guildId) {
         objects.context.guild.Guild mga = getGuild(guildId);
         return mga != null ? mga.getLeaderId() : 0;
      }

      public static int getGuildLeader(String guildName) {
         objects.context.guild.Guild mga = getGuildByName(guildName);
         return mga != null ? mga.getLeaderId() : 0;
      }

      public static void save() {
         lock.writeLock().lock();

         try {
            int count = 0;
            ServerConstants.guildSaveTime = System.currentTimeMillis();

            for (objects.context.guild.Guild a : guilds.values()) {
               if (count++ % 10 == 0) {
                  if (a != null) {
                     long startTime = System.currentTimeMillis();
                     a.writeToDB(false);
                     a.nobleSPAdjustment();
                     long var5 = System.currentTimeMillis();
                  }
               } else {
                  Timer.EtcTimer.getInstance().schedule(() -> {
                     if (a != null) {
                        long startTimex = System.currentTimeMillis();
                        a.writeToDB(false);
                        a.nobleSPAdjustment();
                        long var3x = System.currentTimeMillis();
                     }
                  }, 0L);
               }
            }
         } finally {
            lock.writeLock().unlock();
         }
      }

      public static void saveNoLock() {
         int count = 0;
         ServerConstants.guildSaveTime = System.currentTimeMillis();

         for (objects.context.guild.Guild a : guilds.values()) {
            if (count++ % 10 == 0) {
               if (a != null) {
                  long startTime = System.currentTimeMillis();
                  a.writeToDB(false);
                  a.nobleSPAdjustment();
                  long var5 = System.currentTimeMillis();
               }
            } else {
               Timer.EtcTimer.getInstance().schedule(() -> {
                  if (a != null) {
                     long startTimex = System.currentTimeMillis();
                     a.writeToDB(false);
                     a.nobleSPAdjustment();
                     long var3x = System.currentTimeMillis();
                  }
               }, 0L);
            }
         }
      }

      public static void changeEmblem(int gid, int affectedPlayers, objects.context.guild.Guild mgs, boolean isCustom,
            byte[] imageData, int playerID) {
         GuildPacket.ChangeEmblem changeEmblem = new GuildPacket.ChangeEmblem(
               gid, playerID, (short) mgs.getLogoBG(), (byte) mgs.getLogoBGColor(), (short) mgs.getLogo(),
               (byte) mgs.getLogoColor(), imageData);
         PacketEncoder p = new PacketEncoder();
         changeEmblem.encode(p);
         Center.Broadcast.sendGuildPacket(affectedPlayers, p.getPacket(), -1, gid);
         if (isCustom) {
            GuildPacket.UpdateCustomGuildMark ucgm = new GuildPacket.UpdateCustomGuildMark(getGuild(gid));
            p = new PacketEncoder();
            ucgm.encode(p);
            int ch = Center.Find.findChannel(affectedPlayers);
            MapleCharacter mc = Center.getStorage(ch).getCharacterById(affectedPlayers);
            if (mc != null && ch > 0) {
               mc.dropMessage(5,
                     "The guild mark has been changed. The changed guild mark will be applied after changing channels.");
               mc.getMap().broadcastMessage(p.getPacket());
            }
         }

         setGuildAndRank(affectedPlayers, -1, -1, -1, -1);
      }

      public static void setGuildAndRank(int cid, int guildid, int rank, int contribution, int alliancerank) {
         int ch = Center.Find.findChannel(cid);
         if (ch != -1) {
            MapleCharacter mc = Center.getStorage(ch).getCharacterById(cid);
            if (mc != null) {
               boolean bDifferentGuild;
               if (guildid == -1 && rank == -1) {
                  bDifferentGuild = true;
               } else {
                  bDifferentGuild = guildid != mc.getGuildId();
                  mc.setGuildId(guildid);
                  mc.setGuildRank((byte) rank);
                  mc.setGuildContribution(contribution);
                  mc.setAllianceRank((byte) alliancerank);
                  mc.saveGuildStatus();
               }

               if (bDifferentGuild && ch > 0) {
                  mc.getMap().broadcastMessage(mc, CField.loadGuildData(mc), false);
               }
            }
         }
      }
   }

   public static class InstanceFieldMan {
      private static final ReentrantLock Static_lock = new ReentrantLock();
      private static final HashMap<Integer, Center.InstanceFieldMan.ChannelInstance> Static_map = new HashMap<>();

      public static void init() {
         Static_lock.lock();

         try {
            for (GameServer cserv : GameServer.getAllInstances()) {
               Static_map.put(cserv.getChannel(), new Center.InstanceFieldMan.ChannelInstance(cserv.getChannel()));
            }
         } finally {
            Static_lock.unlock();
         }
      }

      public static Pair<Integer, Integer> getEmptyOne(int start, int end) {
         Static_lock.lock();

         try {
            List<Center.InstanceFieldMan.ChannelInstance> list = new ArrayList<>(Static_map.values());
            Collections.shuffle(list);

            for (Center.InstanceFieldMan.ChannelInstance cinstance : list) {
               for (int i = start; i <= end; i++) {
                  if (!cinstance.maps.contains(i)) {
                     cinstance.maps.contains(i);
                     return new Pair<>(cinstance.chid, i);
                  }
               }
            }

            return null;
         } finally {
            Static_lock.unlock();
         }
      }

      public static void clearInstance(int ch, int mapid) {
         Static_lock.lock();

         try {
            Static_map.get(ch).maps.remove(mapid);
         } finally {
            Static_lock.unlock();
         }
      }

      private static class ChannelInstance {
         int chid;
         HashSet<Integer> maps = new HashSet<>();

         public ChannelInstance(int chid) {
            this.chid = chid;
         }
      }
   }

   public static class Messenger {
      private static Map<Integer, objects.context.messenger.Messenger> messengers = new HashMap<>();
      private static final AtomicInteger runningMessengerId = new AtomicInteger();

      public static objects.context.messenger.Messenger createMessenger(MessengerCharacter chrfor) {
         int messengerid = runningMessengerId.getAndIncrement();
         objects.context.messenger.Messenger messenger = new objects.context.messenger.Messenger(messengerid, chrfor);
         messengers.put(messenger.getId(), messenger);
         return messenger;
      }

      public static void declineChat(String target, String namefrom) {
         int ch = Center.Find.findChannel(target);
         if (ch > 0) {
            GameServer cs = GameServer.getInstance(ch);
            MapleCharacter chr = cs.getPlayerStorage().getCharacterByName(target);
            if (chr != null) {
               objects.context.messenger.Messenger messenger = chr.getMessenger();
               if (messenger != null) {
                  chr.getClient().getSession().writeAndFlush(CField.messengerNote(namefrom, 5, 0));
               }
            }
         }
      }

      public static objects.context.messenger.Messenger getMessenger(int messengerid) {
         return messengers.get(messengerid);
      }

      public static void leaveMessenger(int messengerid, MessengerCharacter target) {
         try {
            objects.context.messenger.Messenger messenger = getMessenger(messengerid);
            if (messenger == null) {
               throw new IllegalArgumentException("No messenger with the specified messengerid exists");
            }

            int position = messenger.getPositionByName(target.getName());
            messenger.removeMember(target);

            for (MessengerCharacter mmc : messenger.getMembers()) {
               if (mmc != null) {
                  int ch = Center.Find.findChannel(mmc.getId());
                  if (ch > 0) {
                     MapleCharacter chr = GameServer.getInstance(ch).getPlayerStorage()
                           .getCharacterByName(mmc.getName());
                     if (chr != null) {
                        chr.getClient().getSession().writeAndFlush(CField.removeMessengerPlayer(position));
                     }
                  }
               }
            }
         } catch (Exception var8) {
            System.out.println("[Error] Error occurred during leaveMessenger function execution! " + var8.toString());
            var8.printStackTrace();
         }
      }

      public static void silentLeaveMessenger(int messengerid, MessengerCharacter target) {
         objects.context.messenger.Messenger messenger = getMessenger(messengerid);
         if (messenger == null) {
            throw new IllegalArgumentException("No messenger with the specified messengerid exists");
         } else {
            messenger.silentRemoveMember(target);
         }
      }

      public static void silentJoinMessenger(int messengerid, MessengerCharacter target) {
         objects.context.messenger.Messenger messenger = getMessenger(messengerid);
         if (messenger == null) {
            throw new IllegalArgumentException("No messenger with the specified messengerid exists");
         } else {
            messenger.silentAddMember(target);
         }
      }

      public static void updateMessenger(int messengerid, String namefrom, int fromchannel) {
         objects.context.messenger.Messenger messenger = getMessenger(messengerid);
         int position = messenger.getPositionByName(namefrom);

         for (MessengerCharacter messengerchar : messenger.getMembers()) {
            if (messengerchar != null && !messengerchar.getName().equals(namefrom)) {
               int ch = Center.Find.findChannel(messengerchar.getName());
               if (ch > 0) {
                  MapleCharacter chr = GameServer.getInstance(ch).getPlayerStorage()
                        .getCharacterByName(messengerchar.getName());
                  if (chr != null) {
                     MapleCharacter from = GameServer.getInstance(fromchannel).getPlayerStorage()
                           .getCharacterByName(namefrom);
                     chr.getClient().getSession()
                           .writeAndFlush(CField.updateMessengerPlayer(namefrom, from, position, fromchannel - 1));
                  }
               }
            }
         }
      }

      public static void joinMessenger(int messengerid, MessengerCharacter target, String from, int fromchannel) {
         objects.context.messenger.Messenger messenger = getMessenger(messengerid);
         if (messenger == null) {
            throw new IllegalArgumentException("No messenger with the specified messengerid exists");
         } else {
            messenger.addMember(target);
            int position = messenger.getPositionByName(target.getName());

            for (MessengerCharacter messengerchar : messenger.getMembers()) {
               if (messengerchar != null) {
                  int mposition = messenger.getPositionByName(messengerchar.getName());
                  int ch = Center.Find.findChannel(messengerchar.getName());
                  if (ch > 0) {
                     MapleCharacter chr = GameServer.getInstance(ch).getPlayerStorage()
                           .getCharacterByName(messengerchar.getName());
                     if (chr != null) {
                        if (!messengerchar.getName().equals(from)) {
                           MapleCharacter fromCh = GameServer.getInstance(fromchannel).getPlayerStorage()
                                 .getCharacterByName(from);
                           if (fromCh != null) {
                              chr.getClient().getSession()
                                    .writeAndFlush(CField.addMessengerPlayer(from, fromCh, position, fromchannel - 1));
                              fromCh.getClient()
                                    .getSession()
                                    .writeAndFlush(CField.addMessengerPlayer(chr.getName(), chr, mposition,
                                          messengerchar.getChannel() - 1));
                           }
                        } else {
                           chr.getClient().getSession().writeAndFlush(CField.joinMessenger(mposition));
                        }
                     }
                  }
               }
            }
         }
      }

      public static void messengerChat(int messengerid, String charname, String text, String namefrom) {
         objects.context.messenger.Messenger messenger = getMessenger(messengerid);
         if (messenger == null) {
            throw new IllegalArgumentException("No messenger with the specified messengerid exists");
         } else {
            for (MessengerCharacter messengerchar : messenger.getMembers()) {
               if (messengerchar != null && !messengerchar.getName().equals(namefrom)) {
                  int ch = Center.Find.findChannel(messengerchar.getName());
                  if (ch > 0) {
                     MapleCharacter chr = GameServer.getInstance(ch).getPlayerStorage()
                           .getCharacterByName(messengerchar.getName());
                     if (chr != null) {
                        chr.getClient().getSession().writeAndFlush(
                              CField.messengerChat(charname, text, new ReportLogEntry(charname, text, 0)));
                     }
                  }
               }
            }
         }
      }

      public static void messengerWhisperChat(int messengerid, String charname, String text, String namefrom) {
         objects.context.messenger.Messenger messenger = getMessenger(messengerid);
         if (messenger == null) {
            throw new IllegalArgumentException("No messenger with the specified messengerid exists");
         } else {
            for (MessengerCharacter messengerchar : messenger.getMembers()) {
               if (messengerchar != null && !messengerchar.getName().equals(namefrom)) {
                  int ch = Center.Find.findChannel(messengerchar.getName());
                  if (ch > 0) {
                     MapleCharacter chr = GameServer.getInstance(ch).getPlayerStorage()
                           .getCharacterByName(messengerchar.getName());
                     if (chr != null) {
                        chr.getClient().getSession().writeAndFlush(
                              CField.messengerWhisperChat(charname, text, new ReportLogEntry(charname, text, 0)));
                     }
                  }
               }
            }
         }
      }

      public static void messengerInvite(String sender, int messengerid, String target, int fromchannel, boolean gm) {
         if (Center.isConnected(target)) {
            int ch = Center.Find.findChannel(target);
            if (ch > 0) {
               MapleCharacter from = GameServer.getInstance(fromchannel).getPlayerStorage().getCharacterByName(sender);
               MapleCharacter targeter = GameServer.getInstance(ch).getPlayerStorage().getCharacterByName(target);
               if (targeter == null || targeter.getMessenger() != null) {
                  from.getClient()
                        .getSession()
                        .writeAndFlush(CField.messengerChat(sender,
                              " : " + target + " is already using Maple Messenger", new ReportLogEntry(sender, "", 0)));
               } else if (targeter.isIntern() && !gm) {
                  from.getClient().getSession().writeAndFlush(CField.messengerNote(target, 4, 0));
               } else {
                  targeter.getClient().getSession().writeAndFlush(CField.messengerInvite(sender, messengerid));
                  from.getClient().getSession().writeAndFlush(CField.messengerNote(target, 4, 1));
               }
            }
         }
      }

      static {
         runningMessengerId.set(1);
      }
   }

   public static class Party {
      private static Map<Integer, objects.context.party.Party> parties = new HashMap<>();
      private static Map<Integer, Expedition> expeds = new HashMap<>();
      private static Map<PartySearchType, List<PartySearch>> searches = new EnumMap<>(PartySearchType.class);
      private static final AtomicInteger runningPartyId = new AtomicInteger(1);
      private static final AtomicInteger runningExpedId = new AtomicInteger(1);

      public static void partyChat(int partyid, String chattext, MapleCharacter user, Item item, String itemName,
            int achievementID, long achievementTime) {
         partyChat(partyid, chattext, user, 1, item, itemName, achievementID, achievementTime);
      }

      public static void expedChat(int expedId, String chattext, MapleCharacter user, Item item, String itemName,
            int achievementID, long achievementTime) {
         Expedition party = getExped(expedId);
         if (party != null) {
            for (int i : party.getParties()) {
               partyChat(i, chattext, user, 4, item, itemName, achievementID, achievementTime);
            }
         }
      }

      public static void expedPacket(int expedId, byte[] packet, PartyMemberEntry exception) {
         Expedition party = getExped(expedId);
         if (party != null) {
            for (int i : party.getParties()) {
               partyPacket(i, packet, exception);
            }
         }
      }

      public static void partyPacket(int partyid, byte[] packet, PartyMemberEntry exception) {
         objects.context.party.Party party = getParty(partyid);
         if (party != null) {
            for (PartyMemberEntry partychar : party.getPartyMemberList()) {
               int ch = Center.Find.findChannel(partychar.getName());
               if (ch > 0 && (exception == null || partychar.getId() != exception.getId())) {
                  MapleCharacter chr = GameServer.getInstance(ch).getPlayerStorage()
                        .getCharacterByName(partychar.getName());
                  if (chr != null) {
                     chr.getClient().getSession().writeAndFlush(packet);
                  }
               }
            }
         }
      }

      public static void partyChat(
            int partyid, String chattext, MapleCharacter user, int mode, Item item, String itemName, int achievementID,
            long achievementTime) {
         objects.context.party.Party party = getParty(partyid);
         if (party != null) {
            for (PartyMemberEntry partychar : party.getPartyMemberList()) {
               int ch = Center.Find.findChannel(partychar.getName());
               if (ch > 0) {
                  MapleCharacter chr = GameServer.getInstance(ch).getPlayerStorage()
                        .getCharacterByName(partychar.getName());
                  if (chr != null && !chr.getName().equalsIgnoreCase(user.getName())) {
                     chr.getClient()
                           .getSession()
                           .writeAndFlush(
                                 CField.multiChat(
                                       user, chattext, mode, item, itemName, achievementID, achievementTime,
                                       new ReportLogEntry(user.getName(), chattext, user.getId())));
                     if (chr.getClient().isMonitored()) {
                        Center.Broadcast.broadcastGMMessage(
                              CWvsContext.serverNotice(6, "[GM Message] " + user.getName() + " said to " + chr.getName()
                                    + " (Party): " + chattext));
                     }
                  }
               }
            }
         }
      }

      public static void partyMessage(int partyid, String chattext) {
         objects.context.party.Party party = getParty(partyid);
         if (party != null) {
            for (PartyMemberEntry partychar : party.getPartyMemberList()) {
               int ch = Center.Find.findChannel(partychar.getName());
               if (ch > 0) {
                  MapleCharacter chr = GameServer.getInstance(ch).getPlayerStorage()
                        .getCharacterByName(partychar.getName());
                  if (chr != null) {
                     chr.dropMessage(5, chattext);
                  }
               }
            }
         }
      }

      public static void expedMessage(int expedId, String chattext) {
         Expedition party = getExped(expedId);
         if (party != null) {
            for (int i : party.getParties()) {
               partyMessage(i, chattext);
            }
         }
      }

      public static void updateParty(int partyID, PartyOperation operation, PartyMemberEntry memberEntry) {
         try {
            objects.context.party.Party party = getParty(partyID);
            if (party == null) {
               return;
            }

            switch (operation) {
               case Join:
                  party.getPartyMember().addMember(memberEntry);
                  if (party.getPartyMemberList().size() >= 6) {
                     PartySearch toRemove = getSearchByParty(partyID);
                     if (toRemove != null) {
                        removeSearch(toRemove, "Party listing is ended because the party is full.");
                     }
                  }
                  break;
               case KickParty:
               case Withdraw:
                  party.getPartyMember().removeMember(memberEntry);
                  break;
               case Disband:
                  disbandParty(partyID);
                  break;
               case SilentUpdate:
               case LogOnOff:
                  party.getPartyMember().updateMember(memberEntry);
                  break;
               case ChangeLeader:
               case ChangeLeaderDisconnect:
                  party.getPartyMember().setLeader(memberEntry);
               case PartySetting:
                  break;
               default:
                  throw new RuntimeException("Unknown party operation. " + operation.name());
            }

            if (operation == PartyOperation.Withdraw || operation == PartyOperation.KickParty) {
               int channel = Center.Find.findChannel(memberEntry.getName());
               if (channel > 0) {
                  MapleCharacter player = Center.getStorage(channel).getCharacterByName(memberEntry.getName());
                  if (player != null) {
                     player.setParty(null);
                     PacketEncoder packet = new PacketEncoder();
                     objects.context.party.Party.PartyPacket.WithdrawParty p = new objects.context.party.Party.PartyPacket.WithdrawParty(
                           party, player.getName(), channel, player.getId(), operation);
                     p.encode(packet);
                     player.send(packet.getPacket());
                  }
               }

               if (memberEntry.getId() == party.getLeader().getId()
                     && party.getPartyMember().getPartyMemberList().size() > 0) {
                  PartyMemberEntry entry = null;

                  for (PartyMemberEntry e : party.getPartyMember().getPartyMemberList()) {
                     if (e != null && (entry == null || entry.getLevel() < e.getLevel())) {
                        entry = e;
                     }
                  }

                  if (entry != null) {
                     updateParty(partyID, PartyOperation.ChangeLeaderDisconnect, entry);
                  }
               }
            }

            if (party.getPartyMember().getPartyMemberList().size() <= 0) {
               disbandParty(partyID);
            }

            for (PartyMemberEntry entry : party.getPartyMember().getPartyMemberList()) {
               if (entry != null) {
                  int channelx = Center.Find.findChannel(entry.getName());
                  if (channelx > 0) {
                     MapleCharacter player = Center.getStorage(channelx).getCharacterByName(entry.getName());
                     if (player != null) {
                        if (operation == PartyOperation.Disband) {
                           player.setParty(null);
                        } else {
                           player.setParty(party);
                        }

                        if (operation == PartyOperation.Join) {
                           PacketEncoder packet = new PacketEncoder();
                           objects.context.party.Party.PartyPacket.JoinMember p = new objects.context.party.Party.PartyPacket.JoinMember(
                                 party, memberEntry.getName(), memberEntry.getChannel());
                           p.encode(packet);
                           player.send(packet.getPacket());
                        } else if (operation == PartyOperation.SilentUpdate || operation == PartyOperation.LogOnOff) {
                           PacketEncoder packet = new PacketEncoder();
                           objects.context.party.Party.PartyPacket.PartyDataUpdate p = new objects.context.party.Party.PartyPacket.PartyDataUpdate(
                                 party, player.getClient().getChannel());
                           p.encode(packet);
                           player.send(packet.getPacket());
                        } else if (operation == PartyOperation.ChangeLeader
                              || operation == PartyOperation.ChangeLeaderDisconnect) {
                           PacketEncoder packet = new PacketEncoder();
                           objects.context.party.Party.PartyPacket.ChangeLeader p = new objects.context.party.Party.PartyPacket.ChangeLeader(
                                 memberEntry.getId(), operation == PartyOperation.ChangeLeaderDisconnect);
                           p.encode(packet);
                           player.send(packet.getPacket());
                        } else if (operation == PartyOperation.PartySetting) {
                           PacketEncoder packet = new PacketEncoder();
                           objects.context.party.Party.PartyPacket.ChangePartySetting p = new objects.context.party.Party.PartyPacket.ChangePartySetting(
                                 party.getPatryTitle(), party.isPrivateParty(), party.isOnlyLeaderPickUp());
                           p.encode(packet);
                           player.send(packet.getPacket());
                        } else if (operation == PartyOperation.Disband) {
                           PacketEncoder packet = new PacketEncoder();
                           objects.context.party.Party.PartyPacket.WithdrawParty p = new objects.context.party.Party.PartyPacket.WithdrawParty(
                                 party, player.getName(), channelx, player.getId(), operation);
                           p.encode(packet);
                           player.send(packet.getPacket());
                        } else if (operation == PartyOperation.Withdraw || operation == PartyOperation.KickParty) {
                           PacketEncoder packet = new PacketEncoder();
                           objects.context.party.Party.PartyPacket.WithdrawParty p = new objects.context.party.Party.PartyPacket.WithdrawParty(
                                 party, memberEntry.getName(), channelx, memberEntry.getId(), operation);
                           p.encode(packet);
                           player.send(packet.getPacket());
                        }
                     }
                  }
               }
            }
         } catch (Exception var10) {
            System.out.println("[Error] Error occurred during updateParty function execution! " + var10.toString());
            var10.printStackTrace();
         }
      }

      public static objects.context.party.Party createParty(PartyMemberEntry chrfor) {
         objects.context.party.Party party = new objects.context.party.Party(runningPartyId.getAndIncrement(), chrfor);
         parties.put(party.getId(), party);
         return party;
      }

      public static objects.context.party.Party getParty(int partyid) {
         return parties.get(partyid);
      }

      public static Expedition getExped(int partyid) {
         return expeds.get(partyid);
      }

      public static Expedition disbandExped(int partyid) {
         PartySearch toRemove = getSearchByExped(partyid);
         if (toRemove != null) {
            removeSearch(toRemove, "The Party Listing was removed because the party disbanded.");
         }

         Expedition ret = expeds.remove(partyid);
         if (ret != null) {
            for (int p : ret.getParties()) {
               objects.context.party.Party pp = getParty(p);
               if (pp != null) {
                  updateParty(p, PartyOperation.Disband, pp.getLeader());
               }
            }
         }

         return ret;
      }

      public static objects.context.party.Party disbandParty(int partyid) {
         PartySearch toRemove = getSearchByParty(partyid);
         if (toRemove != null) {
            removeSearch(toRemove, "The Party Listing was removed because the party disbanded.");
         }

         objects.context.party.Party ret = parties.remove(partyid);
         if (ret == null) {
            return null;
         } else {
            Center.BossPartyRecruiment.removeBossPartyRecruiment(ret);
            ret.disband();
            return ret;
         }
      }

      public static List<PartySearch> searchParty(PartySearchType pst) {
         return searches.get(pst);
      }

      public static void removeSearch(PartySearch ps, String text) {
         List<PartySearch> ss = searches.get(ps.getType());
         if (ss.contains(ps)) {
            ss.remove(ps);
            ps.cancelRemoval();
            if (ps.getType().exped) {
               expedMessage(ps.getId(), text);
            } else {
               partyMessage(ps.getId(), text);
            }
         }
      }

      public static void addSearch(PartySearch ps) {
         searches.get(ps.getType()).add(ps);
      }

      public static PartySearch getSearch(objects.context.party.Party party) {
         for (List<PartySearch> ps : searches.values()) {
            for (PartySearch p : ps) {
               if (p.getId() == party.getId() && !p.getType().exped
                     || p.getId() == party.getExpeditionId() && p.getType().exped) {
                  return p;
               }
            }
         }

         return null;
      }

      public static PartySearch getSearchByParty(int partyId) {
         for (List<PartySearch> ps : searches.values()) {
            for (PartySearch p : ps) {
               if (p.getId() == partyId && !p.getType().exped) {
                  return p;
               }
            }
         }

         return null;
      }

      public static PartySearch getSearchByExped(int partyId) {
         for (List<PartySearch> ps : searches.values()) {
            for (PartySearch p : ps) {
               if (p.getId() == partyId && p.getType().exped) {
                  return p;
               }
            }
         }

         return null;
      }

      public static boolean partyListed(objects.context.party.Party party) {
         return getSearchByParty(party.getId()) != null;
      }

      static {
         DBConnection db = new DBConnection();

         try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE characters SET party = -1, fatigue = 0");
            ps.executeUpdate();
            ps.close();
         } catch (SQLException var7) {
            var7.printStackTrace();
         }

         for (PartySearchType pst : PartySearchType.values()) {
            searches.put(pst, new ArrayList<>());
         }
      }
   }

   public static class RemainBuffStorage {
      public static HashMap<Integer, List<Center.RemainBuffStorage.BuffInfo>> buffStorage = new HashMap<>();

      private static Object[][] checkBuffList() {
         return new Object[][] {
               { SecondaryStatFlag.ExpBuffRate, -2450134 },
               { SecondaryStatFlag.ExpBuffRate, -2450155 },
               { SecondaryStatFlag.ExpBuffRate, -2450163 },
               { SecondaryStatFlag.ExpBuffRate, -2450164 },
               { SecondaryStatFlag.ExpBuffRate, -2450019 },
               { SecondaryStatFlag.ExpBuffRate, -2450042 },
               { SecondaryStatFlag.ExpBuffRate, -2450043 },
               { SecondaryStatFlag.ExpBuffRate, -2450062 },
               { SecondaryStatFlag.ExpBuffRate, -2450064 },
               { SecondaryStatFlag.ExpBuffRate, -2450070 },
               { SecondaryStatFlag.ExpBuffRate, -2450085 },
               { SecondaryStatFlag.ExpBuffRate, -2450086 },
               { SecondaryStatFlag.ExpBuffRate, -2450087 },
               { SecondaryStatFlag.ExpBuffRate, -2450088 },
               { SecondaryStatFlag.ExpBuffRate, -2450090 },
               { SecondaryStatFlag.ExpBuffRate, -2450091 },
               { SecondaryStatFlag.ExpBuffRate, -2450092 },
               { SecondaryStatFlag.ExpBuffRate, -2450093 },
               { SecondaryStatFlag.ExpBuffRate, -2450100 },
               { SecondaryStatFlag.ExpBuffRate, -2450101 },
               { SecondaryStatFlag.ExpBuffRate, -2450102 },
               { SecondaryStatFlag.ExpBuffRate, -2450103 },
               { SecondaryStatFlag.ExpBuffRate, -2450115 },
               { SecondaryStatFlag.ExpBuffRate, -2450116 },
               { SecondaryStatFlag.ExpBuffRate, -2450127 },
               { SecondaryStatFlag.ExpBuffRate, -2450130 },
               { SecondaryStatFlag.ExpBuffRate, -2450135 },
               { SecondaryStatFlag.ExpBuffRate, -2450141 },
               { SecondaryStatFlag.ExpBuffRate, -2450144 },
               { SecondaryStatFlag.ExpBuffRate, -2450153 },
               { SecondaryStatFlag.ExpBuffRate, -2450159 },
               { SecondaryStatFlag.ExpBuffRate, -2450162 },
               { SecondaryStatFlag.ExpBuffRate, -2450166 },
               { SecondaryStatFlag.ExpBuffRate, -2450167 },
               { SecondaryStatFlag.ExpBuffRate, -2450171 },
               { SecondaryStatFlag.ExpBuffRate, -2450175 },
               { SecondaryStatFlag.ExpBuffRate, -2450179 },
               { SecondaryStatFlag.ExpBuffRate, -2450181 },
               { SecondaryStatFlag.DropRate, -2003551 },
               { SecondaryStatFlag.MesoUpByItem, -2003551 },
               { SecondaryStatFlag.DropRate, -2003559 },
               { SecondaryStatFlag.MesoUpByItem, -2003559 },
               { SecondaryStatFlag.DropRate, -2003575 },
               { SecondaryStatFlag.MesoUpByItem, -2003575 },
               { SecondaryStatFlag.LuckOfUnion, -2023661 },
               { SecondaryStatFlag.LuckOfUnion, -2023662 },
               { SecondaryStatFlag.LuckOfUnion, -2023663 },
               { SecondaryStatFlag.WealthOfUnion, -2023664 },
               { SecondaryStatFlag.WealthOfUnion, -2023665 },
               { SecondaryStatFlag.WealthOfUnion, -2023666 },
               { SecondaryStatFlag.ExpBuffRate, -2450147 },
               { SecondaryStatFlag.ExpBuffRate, -2450148 },
               { SecondaryStatFlag.ExpBuffRate, -2450149 }
         };
      }

      private static Object[][] checkIndieBuffList() {
         return new Object[][] {
               { SecondaryStatFlag.indieEXP, -2023128 },
               { SecondaryStatFlag.indieEXP, -2023556 },
               { SecondaryStatFlag.indieBDR, -2003596 },
               { SecondaryStatFlag.indieDamR, -2003597 },
               { SecondaryStatFlag.indieIgnoreMobPdpR, -2003598 },
               { SecondaryStatFlag.indieStatR, -2003599 },
               { SecondaryStatFlag.indieEXP, -2450124 },
               { SecondaryStatFlag.indieEXP, -2023882 },
               { SecondaryStatFlag.indiePAD, -2023658 },
               { SecondaryStatFlag.indiePAD, -2023659 },
               { SecondaryStatFlag.indiePAD, -2023660 },
               { SecondaryStatFlag.indieMAD, -2023658 },
               { SecondaryStatFlag.indieMAD, -2023659 },
               { SecondaryStatFlag.indieMAD, -2023660 }
         };
      }

      public static synchronized void processSaveBuff(MapleCharacter user) {
         if (buffStorage.get(user.getId()) != null) {
            buffStorage.get(user.getId()).clear();
         }

         buffStorage.putIfAbsent(user.getId(), new ArrayList<>());
         SecondaryStat sc = user.getSecondaryStat();

         for (int i = 0; i < checkBuffList().length; i++) {
            SecondaryStatFlag buffStat = (SecondaryStatFlag) checkBuffList()[i][0];
            int buffId = (Integer) checkBuffList()[i][1];
            long till = sc.getTill(buffStat);
            int reason = sc.getReason(buffStat);
            if (till != 0L && buffId == reason) {
               int value = sc.getValue(buffStat);
               buffStorage.get(user.getId()).add(new Center.RemainBuffStorage.BuffInfo(buffStat, value, buffId, till));
            }
         }

         for (int ix = 0; ix < checkIndieBuffList().length; ix++) {
            SecondaryStatFlag indieStat = (SecondaryStatFlag) checkIndieBuffList()[ix][0];
            int buffId = (Integer) checkIndieBuffList()[ix][1];
            long indieTill = sc.getIndieTill(indieStat, buffId);
            if (indieTill != 0L) {
               int value = sc.getIndieValue(indieStat, buffId);
               buffStorage.get(user.getId())
                     .add(new Center.RemainBuffStorage.BuffInfo(indieStat, value, buffId, indieTill));
            }
         }
      }

      public static synchronized void processSetBuff(MapleCharacter user) {
         if (buffStorage.get(user.getId()) != null) {
            for (Center.RemainBuffStorage.BuffInfo bi : buffStorage.get(user.getId())) {
               if (bi.remainTill > System.currentTimeMillis()) {
                  user.temporaryStatSet(bi.buffID, (int) (bi.remainTill - System.currentTimeMillis()), bi.flag,
                        bi.value);
               }
            }

            buffStorage.get(user.getId()).clear();
         }
      }

      private static class BuffInfo {
         final SecondaryStatFlag flag;
         final int value;
         final int buffID;
         final long remainTill;

         public BuffInfo(SecondaryStatFlag flag, int value, int buffID, long remainTill) {
            this.flag = flag;
            this.value = value;
            this.buffID = buffID;
            this.remainTill = remainTill;
         }
      }
   }

   public static class Respawn implements Runnable {
      private int numTimes = 0;
      private int c = 0;
      private long lastUpdateCheckRecruimentTime = 0L;
      private final List<GameServer> cservs = new ArrayList<>(3);

      public Respawn(Integer[] chs, int c) {
         StringBuilder s = new StringBuilder("[Respawn Worker] Registered for channels ");

         for (int i = 1; i <= 3 && chs.length >= c + i; i++) {
            this.cservs.add(GameServer.getInstance(c + i));
            s.append(c + i).append(" ");
         }

         this.c = c;
         System.out.println(s);
      }

      public Respawn(int c) {
         this.cservs.add(GameServer.getInstance(c));
         this.c = c;
      }

      @Override
      public void run() {
         this.numTimes++;
         long now = System.currentTimeMillis();

         for (GameServer cserv : this.cservs) {
            if (!cserv.hasFinishedShutdown()) {
               for (Field map : cserv.getMapFactory().getAllLoadedMaps()) {
                  try {
                     Center.handleMap(map, this.numTimes, map.getCharactersSize(), now);
                  } catch (Exception var9) {
                     FileoutputUtil.outputFileError("Log_FieldSetUpdate_Except.rtf", var9);
                     System.out.println("Field Set Update Error Occurred");
                     var9.printStackTrace();
                  }
               }

               try {
                  for (FieldSet fs : cserv.updateFieldSet) {
                     fs.updateFieldSetPS();
                  }
               } catch (Exception var10) {
                  FileoutputUtil.outputFileError("Log_FieldSetUpdate_Except.rtf", var10);
                  System.out.println("FieldSet update error");
                  var10.printStackTrace();
               }
            }
         }

         if (this.lastUpdateCheckRecruimentTime == 0L
               || this.lastUpdateCheckRecruimentTime <= System.currentTimeMillis()) {
            try {
               Center.BossPartyRecruiment.checkExpiredRecruiment();
               this.lastUpdateCheckRecruimentTime = System.currentTimeMillis() + 10000L;
            } catch (Exception var8) {
               FileoutputUtil.outputFileError("Log_FieldSetUpdate_Except.rtf", var8);
               System.out.println("FieldSet update error");
               var8.printStackTrace();
            }
         }

         Center.lastRespawnTime.put(this.c, System.currentTimeMillis());
      }
   }

   public static class ServerSave {
      public static final Map<Integer, Long> characterMesoMap = Collections.synchronizedMap(new HashMap<>());
   }

   public static class sunShineStorage {
      private static int sunShineGuage = 0;
      public static boolean bloomFlower = false;
      public static int randomBuff = 0;
      public static long endTime = 0L;
      private static final int townMan = 680000710;
      public static ScheduledFuture<?> timer = null;

      public static boolean save() {
         StringBuilder sb = new StringBuilder();
         sb.append("sunShineGuage = ")
               .append(sunShineGuage)
               .append("\r\n")
               .append("randomBuff = ")
               .append(randomBuff)
               .append("\r\n")
               .append("endTime = ")
               .append(endTime);

         try {
            OutputStream output = new FileOutputStream("data/bloomFlower.data");
            String str = sb.toString();
            byte[] by = str.getBytes();
            output.write(by);
            return true;
         } catch (Exception var4) {
            return false;
         }
      }

      public static void autoSave() {
         Timer.EtcTimer.getInstance()
               .register(
                     new Runnable() {
                        @Override
                        public void run() {
                           StringBuilder sb = new StringBuilder();
                           sb.append("sunShineGuage = ")
                                 .append(Center.sunShineStorage.sunShineGuage)
                                 .append("\r\n")
                                 .append("randomBuff = ")
                                 .append(Center.sunShineStorage.randomBuff)
                                 .append("\r\n")
                                 .append("endTime = ")
                                 .append(Center.sunShineStorage.endTime);

                           try {
                              OutputStream output = new FileOutputStream("data/bloomFlower.data");
                              String str = sb.toString();
                              byte[] by = str.getBytes();
                              output.write(by);
                           } catch (Exception var5) {
                              System.out.println("Error occurred during Center auto-save");
                              var5.printStackTrace();
                           }
                        }
                     },
                     30000L,
                     2000L);
      }

      public static boolean loadBloomflower() {
         if (DBConfig.isGanglim) {
            return false;
         } else {
            Table table = Properties.loadTable("data", "bloomFlower.data");
            if (table == null) {
               return false;
            } else {
               sunShineGuage = Integer.parseInt(table.getProperty("sunShineGuage", "0"));
               randomBuff = Integer.parseInt(table.getProperty("randomBuff", "0"));
               endTime = Long.parseLong(table.getProperty("endTime", "0"));
               long now = System.currentTimeMillis();
               if (now < endTime) {
                  bloomFlower = true;
                  SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
                  String endTime2 = sdf.format(endTime);
                  System.out.println("Blooming event will proceed until " + endTime2 + ".");
                  timer = Timer.MapTimer.getInstance().schedule(new Runnable() {
                     @Override
                     public void run() {
                        Center.sunShineStorage.randomBuff = 0;
                        Center.sunShineStorage.bloomFlower = false;
                        Center.sunShineStorage.endTime = 0L;
                        Center.sunShineStorage.setBloomFlower(false);
                     }
                  }, endTime - now);
               }

               return true;
            }
         }
      }

      public static boolean addSunShineGuage(int add) {
         boolean redFlower = sunShineGuage < 333333;
         boolean blueFlower = sunShineGuage < 666666;
         boolean yellowFlower = sunShineGuage < 1000000;
         if (sunShineGuage >= 1000000) {
            return false;
         } else {
            boolean changeFlower = false;
            MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            int remainGuage = sunShineGuage + add - 1000000;
            sunShineGuage = Math.min(1000000, sunShineGuage + add);
            if (redFlower && sunShineGuage >= 333333) {
               changeFlower = true;

               for (GameServer g : GameServer.getAllInstances()) {
                  final MapleNPC npc = g.getMapFactory().getMap(680000710).getNPCById(9062530);
                  final Field map = g.getMapFactory().getMap(680000710);
                  if (!npc.isBlossom()) {
                     npc.setBlossom(true);
                     map.broadcastMessage(CField.NPCPacket.npcSpecialAction(npc.getObjectId(), "special", 3000, 1));
                     Timer.MapTimer.getInstance().schedule(new Runnable() {
                        @Override
                        public void run() {
                           map.broadcastMessage(
                                 CField.NPCPacket.npcSpecialAction(npc.getObjectId(), "special2", 210000000, 1));
                        }
                     }, 3000L);
                  }
               }

               Center.Broadcast.broadcastMessage(CWvsContext.serverNotice(5, "Red Flower has bloomed."));
            }

            if (blueFlower && sunShineGuage >= 666666) {
               changeFlower = true;

               for (GameServer gx : GameServer.getAllInstances()) {
                  final MapleNPC npc = gx.getMapFactory().getMap(680000710).getNPCById(9062531);
                  final Field map = gx.getMapFactory().getMap(680000710);
                  if (!npc.isBlossom()) {
                     npc.setBlossom(true);
                     map.broadcastMessage(CField.NPCPacket.npcSpecialAction(npc.getObjectId(), "special", 3000, 1));
                     Timer.MapTimer.getInstance().schedule(new Runnable() {
                        @Override
                        public void run() {
                           map.broadcastMessage(
                                 CField.NPCPacket.npcSpecialAction(npc.getObjectId(), "special2", 210000000, 1));
                        }
                     }, 3000L);
                  }
               }

               Center.Broadcast.broadcastMessage(CWvsContext.serverNotice(5, "Blue Flower has bloomed."));
            }

            if (yellowFlower && sunShineGuage == 1000000) {
               bloomFlower = true;
               changeFlower = true;

               for (GameServer gxx : GameServer.getAllInstances()) {
                  final MapleNPC npc = gxx.getMapFactory().getMap(180000000).getNPCById(9062532);
                  final Field map = gxx.getMapFactory().getMap(180000000);
                  if (!npc.isBlossom()) {
                     npc.setBlossom(true);
                     map.broadcastMessage(CField.NPCPacket.npcSpecialAction(npc.getObjectId(), "special", 3000, 1));
                     Timer.MapTimer.getInstance().schedule(new Runnable() {
                        @Override
                        public void run() {
                           map.broadcastMessage(
                                 CField.NPCPacket.npcSpecialAction(npc.getObjectId(), "special2", 210000000, 1));
                        }
                     }, 3000L);
                  }
               }

               Center.Broadcast.broadcastMessage(CWvsContext.serverNotice(5, "Yellow Flower has bloomed."));
               randomBuff = Randomizer.rand(1, 5);
               endTime = System.currentTimeMillis() + 21600000L;
               SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
               String endTime2 = sdf.format(endTime);
               TextEffect e = new TextEffect(-1, "Flowers have bloomed in the square, event is in progress.", 100, 1000,
                     4, 0);
               Center.Broadcast.broadcastMessage(e.encodeForLocal());
               if (randomBuff == 1) {
                  Center.Broadcast.broadcastMessage(CWvsContext.serverNotice(5,
                        "Flowers have bloomed in the square! [Exp 1.5x][Arcane Symbol Drop Rate 1.5x] event will be active until "
                              + endTime2 + "!"));
                  DiscordBotHandler.requestSendMegaphone(
                        "@everyone Flowers have bloomed in the square! [Exp 1.5x][Arcane Symbol Drop Rate 1.5x] event will be active until "
                              + endTime2 + "!");
               } else if (randomBuff == 2) {
                  Center.Broadcast.broadcastMessage(
                        CWvsContext.serverNotice(5,
                              "Flowers have bloomed in the square! [Exp 1.5x][Drop Rate 1.5x] event will be active until "
                                    + endTime2 + "!"));
                  DiscordBotHandler
                        .requestSendMegaphone(
                              "@everyone Flowers have bloomed in the square! [Exp 1.5x][Drop Rate 1.5x] event will be active until "
                                    + endTime2 + "!");
               } else if (randomBuff == 3) {
                  Center.Broadcast.broadcastMessage(
                        CWvsContext.serverNotice(5,
                              "Flowers have bloomed in the square! [Exp 1.5x][Meso Rate 1.5x] event will be active until "
                                    + endTime2 + "!"));
                  DiscordBotHandler.requestSendMegaphone(
                        "@everyone Flowers have bloomed in the square! [Exp 1.5x][Meso Rate 1.5x] event will be active until "
                              + endTime2 + "!");
               } else if (randomBuff == 4) {
                  Center.Broadcast.broadcastMessage(CWvsContext.serverNotice(5,
                        "Flowers have bloomed in the square! [Arcane Symbol Drop Rate 1.5x][Meso Rate 1.5x] event will be active until "
                              + endTime2 + "!"));
                  DiscordBotHandler.requestSendMegaphone(
                        "@everyone Flowers have bloomed in the square! [Arcane Symbol Drop Rate 1.5x][Meso Rate 1.5x] event will be active until "
                              + endTime2 + "!");
               } else if (randomBuff == 5) {
                  Center.Broadcast.broadcastMessage(
                        CWvsContext.serverNotice(5,
                              "Flowers have bloomed in the square! [Drop Rate 1.5x][Meso Rate 1.5x] event will be active until "
                                    + endTime2 + "!"));
                  DiscordBotHandler.requestSendMegaphone(
                        "@everyone Flowers have bloomed in the square! [Drop Rate 1.5x][Meso Rate 1.5x] event will be active until "
                              + endTime2 + "!");
               }

               sunShineGuage = 0;
               if (remainGuage > 0) {
                  sunShineGuage = remainGuage;
               }

               if (timer != null) {
                  timer.cancel(false);
                  timer = null;
               }

               timer = Timer.MapTimer.getInstance().schedule(new Runnable() {
                  @Override
                  public void run() {
                     Center.sunShineStorage.randomBuff = 0;
                     Center.sunShineStorage.bloomFlower = false;
                     Center.sunShineStorage.endTime = 0L;
                     Center.sunShineStorage.setBloomFlower(false);
                  }
               }, 21600000L);
            }

            if (changeFlower) {
               for (GameServer gxxx : GameServer.getAllInstances()) {
                  for (Field map : gxxx.getMapFactory().getAllLoadedMaps()) {
                     int buffItemId = 5121112;
                     map.startMapEffect("Warm spring sunshine pours down.", buffItemId);
                     int buff = 2024012;

                     for (MapleCharacter mChar : map.getCharactersThreadsafe()) {
                        ii.getItemEffect(2024012).applyTo(mChar);
                     }

                     for (MapleCharacter mChar : map.getCharactersThreadsafe()) {
                        ii.getItemEffect(2024011).applyTo(mChar);
                     }
                  }
               }
            }

            return true;
         }
      }

      public static int getSunShineGuage() {
         return sunShineGuage;
      }

      public static void setBloomFlower(boolean bloom) {
         bloomFlower = bloom;
         if (bloom) {
            randomBuff = Randomizer.rand(1, 5);
            endTime = System.currentTimeMillis() + 21600000L;
            TextEffect e = new TextEffect(-1, "Flowers have bloomed in the square, event is in progress.", 100, 1000, 4,
                  0);
            Center.Broadcast.broadcastMessage(e.encodeForLocal());
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
            String endTime2 = sdf.format(endTime);
            if (randomBuff == 1) {
               Center.Broadcast.broadcastMessage(
                     CWvsContext.serverNotice(5,
                           "Flowers have bloomed in the square! [Exp 1.5x][Drop Rate 1.5x] event will be active until "
                                 + endTime2 + "!"));
            } else if (randomBuff == 2) {
               Center.Broadcast.broadcastMessage(
                     CWvsContext.serverNotice(5,
                           "Flowers have bloomed in the square! [Exp 1.5x][Meso Rate 1.5x] event will be active until "
                                 + endTime2 + "!"));
            } else if (randomBuff == 3) {
               Center.Broadcast.broadcastMessage(
                     CWvsContext.serverNotice(5,
                           "Flowers have bloomed in the square! [Exp 1.5x][Meso Rate 1.5x] event will be active until "
                                 + endTime2 + "!"));
            } else if (randomBuff == 4) {
               Center.Broadcast.broadcastMessage(
                     CWvsContext.serverNotice(5,
                           "Flowers have bloomed in the square! [Drop Rate 1.5x][Meso Rate 1.5x] event will be active until "
                                 + endTime2 + "!"));
            } else if (randomBuff == 5) {
               Center.Broadcast.broadcastMessage(
                     CWvsContext.serverNotice(5,
                           "Flowers have bloomed in the square! [Drop Rate 1.5x][Symbol Drop Rate 2x] event will be active until "
                                 + endTime2 + "!"));
            } else if (randomBuff == 6) {
               Center.Broadcast.broadcastMessage(
                     CWvsContext.serverNotice(5,
                           "Flowers have bloomed in the square! [Meso Rate 1.5x][Symbol Drop Rate 2x] event will be active until "
                                 + endTime2 + "!"));
            }

            sunShineGuage = 0;
            if (timer != null) {
               timer.cancel(false);
               timer = null;
            }

            timer = Timer.MapTimer.getInstance().schedule(new Runnable() {
               @Override
               public void run() {
                  Center.sunShineStorage.randomBuff = 0;
                  Center.sunShineStorage.bloomFlower = false;
                  Center.sunShineStorage.endTime = 0L;
                  Center.sunShineStorage.setBloomFlower(false);
               }
            }, 21600000L);
            MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();

            for (GameServer g : GameServer.getAllInstances()) {
               for (Field map : g.getMapFactory().getAllLoadedMaps()) {
                  int buffItemId = 5121112;
                  map.startMapEffect("Warm spring sunshine pours down.", buffItemId);
                  int buff = 2024012;

                  for (MapleCharacter mChar : map.getCharactersThreadsafe()) {
                     ii.getItemEffect(2024012).applyTo(mChar);
                  }

                  for (MapleCharacter mChar : map.getCharactersThreadsafe()) {
                     ii.getItemEffect(2024011).applyTo(mChar);
                  }
               }

               final Field map = g.getMapFactory().getMap(680000710);
               final MapleNPC npc = map.getNPCById(9062530);
               if (!npc.isBlossom()) {
                  npc.setBlossom(true);
                  map.broadcastMessage(CField.NPCPacket.npcSpecialAction(npc.getObjectId(), "special", 3000, 1));
                  Timer.MapTimer.getInstance().schedule(new Runnable() {
                     @Override
                     public void run() {
                        map.broadcastMessage(
                              CField.NPCPacket.npcSpecialAction(npc.getObjectId(), "special2", 210000000, 1));
                     }
                  }, 3000L);
               }

               final MapleNPC npc1 = map.getNPCById(9062531);
               if (!npc1.isBlossom()) {
                  npc1.setBlossom(true);
                  map.broadcastMessage(CField.NPCPacket.npcSpecialAction(npc1.getObjectId(), "special", 3000, 1));
                  Timer.MapTimer.getInstance().schedule(new Runnable() {
                     @Override
                     public void run() {
                        map.broadcastMessage(
                              CField.NPCPacket.npcSpecialAction(npc1.getObjectId(), "special2", 210000000, 1));
                     }
                  }, 3000L);
               }

               final MapleNPC npc2 = map.getNPCById(9062532);
               if (!npc2.isBlossom()) {
                  npc2.setBlossom(true);
                  map.broadcastMessage(CField.NPCPacket.npcSpecialAction(npc2.getObjectId(), "special", 3000, 1));
                  Timer.MapTimer.getInstance().schedule(new Runnable() {
                     @Override
                     public void run() {
                        map.broadcastMessage(
                              CField.NPCPacket.npcSpecialAction(npc2.getObjectId(), "special2", 210000000, 1));
                     }
                  }, 3000L);
               }
            }
         } else {
            randomBuff = 0;

            for (GameServer g : GameServer.getAllInstances()) {
               Field mapx = g.getMapFactory().getMap(680000710);
               MapleNPC npcx = mapx.getNPCById(9062530);
               if (npcx.isBlossom()) {
                  mapx.broadcastMessage(CField.NPCPacket.npcSpecialAction(npcx.getObjectId(), "special2", 0, 1));
                  npcx.setBlossom(false);
               }

               npcx = g.getMapFactory().getMap(680000710).getNPCById(9062531);
               if (npcx.isBlossom()) {
                  mapx.broadcastMessage(CField.NPCPacket.npcSpecialAction(npcx.getObjectId(), "special2", 0, 1));
                  npcx.setBlossom(false);
               }

               npcx = g.getMapFactory().getMap(680000710).getNPCById(9062532);
               if (npcx.isBlossom()) {
                  mapx.broadcastMessage(CField.NPCPacket.npcSpecialAction(npcx.getObjectId(), "special2", 0, 1));
                  npcx.setBlossom(false);
               }
            }
         }
      }
   }
}
