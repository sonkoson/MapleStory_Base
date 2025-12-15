package commands;

import network.center.Center;
import network.game.GameServer;
import objects.fields.Field;
import objects.fields.Portal;
import objects.users.MapleCharacter;
import objects.users.MapleClient;

public class WarpCommands implements Command {
   @Override
   public void execute(MapleClient c, String[] splitted) throws Exception, IllegalCommandSyntaxException {
      GameServer cserv = c.getChannelServer();
      if (splitted[0].equals("!warp")) {
         MapleCharacter victim = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
         if (victim == null || c.getPlayer().getGMLevel() < victim.getGMLevel() || victim.inPVP()
               || c.getPlayer().inPVP()) {
            try {
               victim = c.getPlayer();
               int ch = Center.Find.findChannel(splitted[1]);
               if (ch < 0) {
                  Field target = c.getChannelServer().getMapFactory().getMap(Integer.parseInt(splitted[1]));
                  if (target == null) {
                     c.getPlayer().dropMessage(6, "ไม่พบแผนที่");
                  }

                  Portal targetPortal = null;
                  if (splitted.length > 2) {
                     try {
                        targetPortal = target.getPortal(Integer.parseInt(splitted[2]));
                     } catch (IndexOutOfBoundsException var11) {
                        c.getPlayer().dropMessage(5, "เลือกพอร์ทัลไม่ถูกต้อง");
                     } catch (NumberFormatException var12) {
                     }
                  }

                  if (targetPortal == null) {
                     targetPortal = target.getPortal(0);
                  }

                  c.getPlayer().changeMap(target, targetPortal);
               } else {
                  victim = GameServer.getInstance(ch).getPlayerStorage().getCharacterByName(splitted[1]);
                  c.getPlayer().dropMessage(6, "กำลังย้ายแชนแนลเพื่อวาร์ปไปหาผู้เล่น");
                  if (victim.getMapId() != c.getPlayer().getMapId()) {
                     Field mapp = c.getChannelServer().getMapFactory().getMap(victim.getMapId());
                     c.getPlayer().changeMap(mapp, mapp.findClosestPortal(victim.getTruePosition()));
                  }

                  c.getPlayer().changeChannel(ch);
               }
            } catch (Exception var13) {
               c.getPlayer().dropMessage(6, "เกิดข้อผิดพลาดบางอย่าง");
            }
         } else if (splitted.length == 2) {
            c.getPlayer().changeMap(victim.getMap(), victim.getMap().findClosestSpawnpoint(victim.getTruePosition()));
         } else {
            Field targetx = GameServer.getInstance(c.getChannel()).getMapFactory()
                  .getMap(Integer.parseInt(splitted[2]));
            if (targetx == null) {
               c.getPlayer().dropMessage(6, "ไม่พบแผนที่");
            }

            Portal targetPortalx = null;
            if (splitted.length > 3) {
               try {
                  targetPortalx = targetx.getPortal(Integer.parseInt(splitted[3]));
               } catch (IndexOutOfBoundsException var14) {
                  c.getPlayer().dropMessage(5, "เลือกพอร์ทัลไม่ถูกต้อง");
               } catch (NumberFormatException var15) {
               }
            }

            if (targetPortalx == null) {
               targetPortalx = targetx.getPortal(0);
            }

            victim.changeMap(targetx, targetPortalx);
         }
      } else if (splitted[0].equals("!warphere")) {
         MapleCharacter victim = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
         if (victim != null) {
            if (c.getPlayer().inPVP() || !c.getPlayer().isGM() && (victim.isInBlockedMap() || victim.isGM())) {
               c.getPlayer().dropMessage(5, "ไม่สามารถวาร์ปผู้เล่นซ้ำๆ ได้");
            }

            victim.changeMap(c.getPlayer().getMap(),
                  c.getPlayer().getMap().findClosestPortal(c.getPlayer().getTruePosition()));
         } else {
            int ch = Center.Find.findChannel(splitted[1]);
            if (ch < 0) {
               c.getPlayer().dropMessage(5, "ไม่พบตัวละคร");
            }

            victim = GameServer.getInstance(ch).getPlayerStorage().getCharacterByName(splitted[1]);
            if (victim == null || victim.inPVP()
                  || !c.getPlayer().isGM() && (victim.isInBlockedMap() || victim.isGM())) {
               c.getPlayer().dropMessage(5, "ไม่สามารถวาร์ปผู้เล่นได้");
            }

            c.getPlayer().dropMessage(5, "กำลังเรียกผู้เล่นจากแชนแนลอื่น");
            victim.dropMessage(5, "คุณถูกเรียกตัวโดย GM");
            if (victim.getMapId() != c.getPlayer().getMapId()) {
               Field mapp = victim.getClient().getChannelServer().getMapFactory().getMap(c.getPlayer().getMapId());
               victim.changeMap(mapp, mapp.findClosestPortal(c.getPlayer().getTruePosition()));
            }

            victim.changeChannel(c.getChannel());
         }
      } else if (splitted[0].equals("!map")) {
         Field targetxx = null;
         if (c.getPlayer().getEventInstance() != null) {
            targetxx = c.getPlayer().getEventInstance().getMapFactory().getMap(Integer.parseInt(splitted[1]));
         } else {
            targetxx = cserv.getMapFactory().getMap(Integer.parseInt(splitted[1]));
         }

         if (targetxx == null) {
            c.getPlayer().dropMessage(5, "ไม่พบแผนที่ " + Integer.parseInt(splitted[1]));
            return;
         }

         Portal targetPortalxx = null;
         if (splitted.length > 2) {
            try {
               targetPortalxx = targetxx.getPortal(Integer.parseInt(splitted[2]));
            } catch (IndexOutOfBoundsException var9) {
               c.getPlayer().dropMessage(5, "เลือกพอร์ทัลไม่ถูกต้อง");
            } catch (NumberFormatException var10) {
            }
         }

         if (targetPortalxx == null) {
            targetPortalxx = targetxx.getPortal(0);
         }

         c.getPlayer().changeMap(targetxx, targetPortalxx);
      }
   }

   @Override
   public CommandDefinition[] getDefinition() {
      return new CommandDefinition[] {
            new CommandDefinition("!warp", "<ชื่อตัวละคร> (<รหัสแผนที่>)",
                  "วาร์ปตัวเองไปหาผู้เล่น หรือวาร์ปผู้เล่นไปยังแผนที่", 2),
            new CommandDefinition("!warphere", "<ชื่อตัวละคร>", "เรียกผู้เล่นมาหาคุณ", 2),
            new CommandDefinition("!map", "<รหัสแผนที่>", "วาร์ปตัวเองไปยังแผนที่ที่ระบุ", 2)
      };
   }
}
