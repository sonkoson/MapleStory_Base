package objects.utils;

import constants.AutoHottimeManager;
import constants.HottimeItemManager;
import constants.ServerConstants;
import constants.devtempConstants.MapleFishing;
import java.util.Scanner;
import network.RecvPacketOpcode;
import network.SendPacketOpcode;
import network.ShutdownServer;
import network.center.Center;
import network.discordbot.DiscordBotHandler;
import network.encode.PacketEncoder;
import network.game.GameServer;
import network.models.CField;
import network.models.CWvsContext;
import objects.context.guild.Guild;
import objects.fields.child.etc.DamageMeasurementRank;
import objects.fields.gameobject.lifes.MapleMonsterInformationProvider;
import objects.users.MapleCharacter;
import objects.users.extra.ExtraAbilityFactory;
import scripting.newscripting.ScriptManager;

public class CMDCommand {
   private static CMDCommand instance = new CMDCommand();
   public static String target = "";
   private static int a;

   public static void main() {
      Scanner scan = new Scanner(System.in);

      while (true) {
         System.out.print("Input Command : ");
         target = scan.nextLine();
         String[] command = target.split(" ");
         String var2 = command[0];
         switch (var2) {
            case "낚시리셋":
            case "fr":
               MapleFishing.Load();
               break;
            case "핫타임아이템리셋":
            case "hir":
               HottimeItemManager.loadHottimeItem();
               break;
            case "피버리셋":
            case "feverreset":
               ServerConstants.expFeverRate = 1.0;
               ServerConstants.dropFeverRate = 1.0;
               ServerConstants.mesoFeverRate = 1.0;
               Center.cancelAutoFeverTask();
               AutoHottimeManager.loadAutoHottime();
               Center.registerAutoFever();
               break;
            case "전투력리셋":
               DamageMeasurementRank.resetRank();
               System.out.println("Combat power ranking reset.");
               break;
            case "LoadCombatPower":
               DamageMeasurementRank.loadRank();
               System.out.println("Combat power ranking loaded.");
               break;
            case "ForceNobleSettlement":
               for (Guild g : Center.Guild.getGuilds()) {
                  g.nobleSPAdjustmentF();
               }
               break;
            case "블라썸저장":
               if (Center.sunShineStorage.save()) {
                  System.out.println("Saved successfully.");
               } else {
                  System.out.println("Save failed error");
               }
               break;
            case "ScriptReset":
               ScriptManager.resetScript(null);
               break;
            case "봇테스트":
               DiscordBotHandler.requestSendTelegramWithChatID("봇에서 보내는 테스트입니다. 1", -671926475L);
               DiscordBotHandler.requestSendTelegramWithChatID("봇에서 보내는 테스트입니다. 2", -627738806L);
               DiscordBotHandler.requestSendTelegramWithChatID("봇에서 보내는 테스트입니다. 3", -1001603835720L);
               break;
            case "도움말":
               System.out.println("< CMD Command Help >");
               System.out.println("[Command List] :: \r\n");
               System.out.println("<Notice> - Sends a notice.");
               System.out.println("<ShutdownAll> - Disconnects all users.");
               System.out.println("<SaveShop> - Saves all open Hired Merchants.");
               System.out.println("<Appoint> - Grants GM level to player.");
               System.out.println("<Packet> - Sends a packet string to server.");
               System.out.println("<OR> - Reloads opcodes.");
               System.out.println("<ShutdownServer> - Safely saves and shuts down the server.");
               break;
            case "Notice":
               for (GameServer ch : GameServer.getAllInstances()) {
                  for (MapleCharacter chr : ch.getPlayerStorage().getAllCharacters()) {
                     chr.dropMessage(1, "[Notice사항]\r\n" + StringUtil.joinStringFrom(command, 1));
                  }
               }
               break;
            case "모두종료":
               for (GameServer ch : GameServer.getAllInstances()) {
                  ch.getPlayerStorage().disconnectAll();
               }

               System.out.println("Disconnected all users on server.");
               break;
            case "SaveShop":
               for (GameServer ch : GameServer.getAllInstances()) {
                  ch.closeAllMerchant();
               }

               System.out.println("Hired Merchant saved");
               break;
            case "Appoint":
               a = 0;

               for (GameServer cserv : GameServer.getAllInstances()) {
                  MapleCharacter player = null;
                  player = cserv.getPlayerStorage().getCharacterByName(command[1]);
                  if (player != null) {
                     byte number = Byte.parseByte(command[2]);
                     player.getClient().getSession().writeAndFlush(CWvsContext.getScriptProgressMessage("해당 플레이어가 GM " + command[2] + "레벨이 되었습니다."));
                     System.out.println(command[1] + " Player GM level " + command[2] + " set to.");
                     player.setGMLevel(number);
                     a = 1;
                  } else if (player == null && a == 0) {
                     System.out.println(command[1] + " Player not found.");
                     a = 1;
                  }
               }
               break;
            case "Packet":
               PacketEncoder mplew = new PacketEncoder();
               mplew.encodeBuffer(HexTool.getByteArrayFromHexString(StringUtil.joinStringFrom(command, 1)));

               for (GameServer ch : GameServer.getAllInstances()) {
                  for (MapleCharacter chr : ch.getPlayerStorage().getAllCharacters()) {
                     chr.getClient().getSession().writeAndFlush(mplew.getPacket());
                  }
               }

               System.out.println(StringUtil.joinStringFrom(command, 1));
               break;
            case "서버종료":
               ShutdownServer.getInstance().run();
               ShutdownServer.getInstance().run();
               break;
            case "ear":
               ExtraAbilityFactory.loadData();
               break;
            case "or":
               RecvPacketOpcode.reloadValues();
               SendPacketOpcode.reloadValues();
               System.out.print("Opcode reload completed.");
               break;
            case "enddir":
               for (GameServer cserv : GameServer.getAllInstances()) {
                  cserv.broadcastPacket(CField.UIPacket.IntroLock(false));
               }
               break;
            case "endingamedir":
               for (GameServer cserv : GameServer.getAllInstances()) {
                  cserv.broadcastPacket(CField.DirectionPacket.IntroEnableUI(1));
               }
               break;
            case "드롭리셋":
            case "dr":
               MapleMonsterInformationProvider.getInstance().clearDrops();
         }
      }
   }

   public static String converToDecimalFromHex(String hex) {
      String decimal = "";
      hex = hex.trim();

      for (int i = 0; i < hex.length(); i += 2) {
         String tmp = hex.substring(i, i + 2);
         long val = Long.parseLong(tmp, 16);
         decimal = decimal + val;
         decimal = decimal + ",";
      }

      return decimal;
   }
}
