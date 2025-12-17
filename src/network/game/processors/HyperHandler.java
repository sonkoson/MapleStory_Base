package network.game.processors;

import java.util.HashMap;
import network.SendPacketOpcode;
import network.decode.PacketDecoder;
import network.encode.PacketEncoder;
import network.models.CWvsContext;
import objects.users.MapleCharacter;
import objects.users.MapleClient;
import objects.users.skills.Skill;
import objects.users.skills.SkillEntry;
import objects.users.skills.SkillFactory;
import objects.users.stats.HyperStat;

public class HyperHandler {
   public static void changeHyperStatLevel(PacketDecoder slea, MapleClient c) {
      int tick = slea.readInt();
      int index = slea.readInt();
      int skillID = slea.readInt();
      MapleCharacter player = c.getPlayer();
      HyperStat hStat = player.getHyperStat();
      Skill skill = SkillFactory.getSkill(skillID);
      c.getSession().writeAndFlush(CWvsContext.enableActions(player));
      if (skill == null) {
         player.dropMessage(1, "ข้อมูลสกิลไม่ถูกต้อง");
      } else if (player.getLevel() < 140) {
         player.dropMessage(1, "เลเวลยังไม่ถึงกำหนดในกาõั้งค่ҁHyper Stat");
      } else if (hStat != null && hStat.currentIndex == index && hStat.getStat() != null) {
         int skillLV = hStat.getStat().getSkillLevel(skillID);
         if (skillLV >= HyperStat.getMaxHyperStatLevel(skillID)) {
            player.dropMessage(1, "ส൵ัสนี้ถึงเลเวลสูงสุดแล้ว");
         } else if (hStat.getStat().getRemainStatPoint() < HyperStat.getNeedHyperStatPoint(skillLV + 1)) {
            updateSkills(player, 0);
            player.dropMessage(1, "Hyper Stat Points ไม่เพียงพอสำหรับขั้นถѴไป");
         } else {
            hStat.getStat().setSkillLevel(skillID, skillLV + 1);
            updateSkills(player, skillID);
            player.setChangedSkills();
            player.getStat().recalcLocalStats(player);
         }
      } else {
         player.dropMessage(1, "เกิดข้อผิดพลาดในการโหลดข้อมูลHyper Stat");
      }
   }

   public static void changeHyperStatPreset(PacketDecoder in, MapleClient c) {
      int tick = in.readInt();
      int index = in.readInt();
      MapleCharacter player = c.getPlayer();
      HyperStat hStat = player.getHyperStat();
      c.getSession().writeAndFlush(CWvsContext.enableActions(player));
      if (player.getMeso() < 2000000L) {
         player.dropMessage(1, "Meso ไม่เพียงพอ");
      } else if (hStat != null && hStat.currentIndex != index && hStat.getStat() != null) {
         player.gainMeso(-2000000L, true);
         hStat.currentIndex = index;
         updateSkills(player, 0);
         player.getStat().recalcLocalStats(player);
      } else {
         player.dropMessage(1, "เกิดข้อผิดพลาดในการโหลดข้อมูลHyper Stat");
      }
   }

   public static void resetHyperSkillStat(PacketDecoder slea, MapleClient c) {
      int tick = slea.readInt();
      int index = slea.readInt();
      MapleCharacter player = c.getPlayer();
      HyperStat hStat = player.getHyperStat();
      c.getSession().writeAndFlush(CWvsContext.enableActions(c.getPlayer()));
      if (player.getMeso() < 10000000L) {
         player.dropMessage(1, "Meso ไม่เพียงพอสำหรับรีเซ็ต Hyper Stat");
      } else if (player.getLevel() < 140) {
         player.dropMessage(1, "เลเวลยังไม่ถึงกำหนดในกาõั้งค่ҁHyper Stat");
      } else if (hStat != null && hStat.currentIndex == index && hStat.getStat() != null) {
         player.gainMeso(-10000000L, true);
         hStat.getStat().resetHyperStats();
         updateSkills(player, 0);
         player.dropMessage(1, "รีเซ็ต Hyper Stat เรียบร้อยแล้ว");
         player.setChangedSkills();
         player.getStat().recalcLocalStats(player);
      } else {
         player.dropMessage(1, "เกิดข้อผิดพลาดในการโหลดข้อมูลHyper Stat");
      }
   }

   public static void updateSkills(MapleCharacter player, int skillID) {
      HashMap<Skill, SkillEntry> update = new HashMap<>();
      HyperStat hStat = player.getHyperStat();
      if (hStat.getStat().getRemainStatPoint() < 0) {
         hStat.getStat().resetHyperStats();
      }

      for (HyperStat.HyperStatInfo info : hStat.getStat().skillData) {
         if (info.skillID == skillID || skillID <= 0) {
            Skill skill = SkillFactory.getSkill(info.skillID);
            if (skill != null) {
               update.put(skill, new SkillEntry(info.skillLV, (byte) skill.getMaxLevel(), -1L));
            }
         }
      }

      player.getClient().getSession().writeAndFlush(CWvsContext.updateSkills(update, true, false, (byte) 7));
      PacketEncoder o = new PacketEncoder();
      o.writeShort(SendPacketOpcode.HYPER_STAT_SET.getValue());
      o.write(hStat.currentIndex);
      o.write(skillID <= 0);
      if (skillID <= 0) {
         for (HyperStat.HyperStatData data : hStat.statDataList) {
            data.encode(o);
         }
      }

      player.send(o.getPacket());
      if (player.getHexaCore() != null) {
         player.getHexaCore().updateSkills(player);
      }
   }

   public static void getSpecialStat(PacketDecoder slea, MapleClient c) {
      try {
         String tableName = slea.readMapleAsciiString();
         int index = slea.readInt();
         int mode = slea.readInt();
         int rate = 0;
         if (!tableName.startsWith("9200") && !tableName.startsWith("9201")) {
            if (tableName.equals("honorLeveling")) {
               c.getSession().writeAndFlush(
                     CWvsContext.updateSpecialStat(tableName, index, mode, c.getPlayer().getInnerNextExp()));
               return;
            }

            if ("incHyperStat".equals(tableName)) {
               int delta = index / 10 - 11;
               c.getSession().writeAndFlush(CWvsContext.updateSpecialStat(tableName, index, mode, delta));
               return;
            }

            if ("needHyperStatLv".equals(tableName)) {
               int delta = HyperStat.getNeedHyperStatPoint(index);
               if (delta == -1) {
                  delta = 0;
               }

               c.getSession().writeAndFlush(CWvsContext.updateSpecialStat(tableName, index, mode, delta));
            } else if (!"hyper".equals(tableName)) {
               rate = Math.max(0,
                     100 - (index + 1 - c.getPlayer().getProfessionLevel(Integer.parseInt(tableName))) * 20);
            }
         } else {
            rate = 100;
         }

         c.getSession().writeAndFlush(CWvsContext.updateSpecialStat(tableName, index, mode, rate));
      } catch (Exception var7) {
      }
   }
}
