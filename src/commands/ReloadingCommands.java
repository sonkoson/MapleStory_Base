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
         c.getPlayer().dropMessage(5, "[System] โหลดข้อมูลOps ใหม่เสร็จสิ้น");
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
         c.getPlayer().dropMessage(5, "[System] โหลดข้อมูลDrop ใหม่เสร็จสิ้น");
      } else if (splitted[0].equals("!reloadportals")) {
         PortalScriptManager.getInstance().clearScripts();
         c.getPlayer().dropMessage(5, "[System] โหลด Portal Script ใหม่เสร็จสิ้น");
      } else if (splitted[0].equals("!reloadshops")) {
         MapleShopFactory.getInstance().clear();
         c.getPlayer().dropMessage(5, "[System] โหลดข้อมูลShop ใหม่เสร็จสิ้น");
      } else if (splitted[0].equals("!reloadevents")) {
         for (GameServer instance : GameServer.getAllInstances()) {
            instance.reloadEvents();
         }
         c.getPlayer().dropMessage(5, "[System] โหลดข้อมูลEvent ใหม่เสร็จสิ้น");
      } else if (splitted[0].equals("!reloadskills")) {
         SkillFactory.load();
         c.getPlayer().dropMessage(5, "[System] โหลดข้อมูลSkill ใหม่เสร็จสิ้น");
      } else if (splitted[0].equals("!reloadweekly")) {
         WeeklyItemManager.loadWeeklyItems();
         c.getPlayer().dropMessage(5, "[System] โหลดข้อมูลWeekly Item ใหม่เสร็จสิ้น");
      } else if (splitted[0].equals("!saveweekly")) {
         WeeklyItemManager.saveWeeklyItems();
         c.getPlayer().dropMessage(5, "[System] บันทึก Weekly Item ลง DB แล้ว");
      } else if (splitted[0].equals("!resetgoldapple")) {
         RoyalStyle.resetGoldApple();
         c.getPlayer().dropMessage(5, "[System] รีเซ็ต Gold Apple เสร็จสิ้น");
      } else if (splitted[0].equals("!reloadmobhp")) {
         MapleMonsterCustomHP.Load();
         c.getPlayer().dropMessage(5, "[System] โหลดข้อมูลCustom Mob HP ใหม่เสร็จสิ้น");
      } else if (splitted[0].equals("!reloaddailygift")) {
         MapleDailyGift.Load();
         c.getPlayer().dropMessage(5, "[System] โหลดข้อมูลDaily Gift ใหม่เสร็จสิ้น");
      } else if (splitted[0].equals("!reloaddimen")) {
         MapleDimensionalMirror.Load();
         c.getPlayer().dropMessage(5, "[System] โหลดข้อมูลDimensional Mirror ใหม่เสร็จสิ้น");
      } else if (splitted[0].equals("!reloadeventlist")) {
         MapleEventList.Load();
         c.getPlayer().dropMessage(5, "[System] โหลดข้อมูลEvent List ใหม่เสร็จสิ้น");
      } else if (splitted[0].equals("!reloadfishing")) {
         MapleFishing.Load();
         c.getPlayer().dropMessage(5, "[System] โหลดข้อมูลFishing ใหม่เสร็จสิ้น");
      } else if (splitted[0].equals("!reloadgoldenchariot")) {
         MapleGoldenChariot.Load();
         c.getPlayer().dropMessage(5, "[System] โหลดข้อมูลGolden Chariot ใหม่เสร็จสิ้น");
      } else if (splitted[0].equals("!reloadcrc")) {
         MapleClientCRC.Load();
         c.getPlayer().dropMessage(5, "[System] โหลดข้อมูลWZ CRC ใหม่เสร็จสิ้น");
      } else if (splitted[0].equals("!reloadrank")) {
         DamageMeasurementRank.loadRank();
         c.getPlayer().dropMessage(5, "[System] โหลดอันดับวѴความเสียหายเสร็จสิ้น");
      }
   }

   @Override
   public CommandDefinition[] getDefinition() {
      return new CommandDefinition[] {
            new CommandDefinition("!reloadops", "", "โหลด Opcode รับ/ส่งใหม่จากไฟล์ properties", 6),
            new CommandDefinition("!setop", "<name> <value>", "ตั้งค่า opcode เฉพาะ", 6),
            new CommandDefinition("!reloadportals", "", "ล้างสคริปต์พอร์ทัลจากหน่วยความจำ", 6),
            new CommandDefinition("!reloaddrops", "", "ล้างข้อมูลดรอปจากหน่วยความจำ", 6),
            new CommandDefinition("!reloadshops", "", "ล้างข้อมูลร้านค้าจากหน่วยความจำ", 6),
            new CommandDefinition("!reloadevents", "", "โหลดกิจกรรมใหม่ในทุกเซิร์ฟเวอร์", 6),
            new CommandDefinition("!reloadskills", "", "โหลดสกิลใหม่จาก WZ", 6),
            new CommandDefinition("!reloadweekly", "", "โหลดไอเทมรายสัปดาห์ใหม่จาก DB", 6),
            new CommandDefinition("!saveweekly", "", "บันทึกไอเทมรายสัปดาห์ลง DB", 6),
            new CommandDefinition("!resetgoldapple", "", "รีเซ็ตไอเทม Gold Apple", 6),
            new CommandDefinition("!reloadmobhp", "", "โหลด HP มอนสเตอร์ที่กำหนดเองใหม่", 6),
            new CommandDefinition("!reloaddailygift", "", "โหลดข้อมูลของขวัญรายวันใหม่", 6),
            new CommandDefinition("!reloaddimen", "", "โหลดข้อมูลกระจกมิติใหม่", 6),
            new CommandDefinition("!reloadeventlist", "", "โหลดรายการกิจกรรมใหม่", 6),
            new CommandDefinition("!reloadfishing", "", "โหลดข้อมูลการตกปลาใหม่", 6),
            new CommandDefinition("!reloadgoldenchariot", "", "โหลดข้อมูล Golden Chariot ใหม่", 6),
            new CommandDefinition("!reloadcrc", "", "โหลดข้อมูล CRC ใหม่", 6),
            new CommandDefinition("!reloadrank", "", "โหลดอันดับวัดความเสียหายใหม่", 6)
      };
   }
}
