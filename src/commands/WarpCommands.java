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
                     c.getPlayer().dropMessage(6, "เนเธกเนเธเธเนเธเธเธ—เธตเน");
                  }

                  Portal targetPortal = null;
                  if (splitted.length > 2) {
                     try {
                        targetPortal = target.getPortal(Integer.parseInt(splitted[2]));
                     } catch (IndexOutOfBoundsException var11) {
                        c.getPlayer().dropMessage(5, "เน€เธฅเธทเธญเธเธเธญเธฃเนเธ—เธฑเธฅเนเธกเนเธ–เธนเธเธ•เนเธญเธ");
                     } catch (NumberFormatException var12) {
                     }
                  }

                  if (targetPortal == null) {
                     targetPortal = target.getPortal(0);
                  }

                  c.getPlayer().changeMap(target, targetPortal);
               } else {
                  victim = GameServer.getInstance(ch).getPlayerStorage().getCharacterByName(splitted[1]);
                  c.getPlayer().dropMessage(6, "เธเธณเธฅเธฑเธเธขเนเธฒเธขเนเธเธเนเธเธฅเน€เธเธทเนเธญเธงเธฒเธฃเนเธเนเธเธซเธฒเธเธนเนเน€เธฅเนเธ");
                  if (victim.getMapId() != c.getPlayer().getMapId()) {
                     Field mapp = c.getChannelServer().getMapFactory().getMap(victim.getMapId());
                     c.getPlayer().changeMap(mapp, mapp.findClosestPortal(victim.getTruePosition()));
                  }

                  c.getPlayer().changeChannel(ch);
               }
            } catch (Exception var13) {
               c.getPlayer().dropMessage(6, "เน€เธเธดเธ”เธเนเธญเธเธดเธ”เธเธฅเธฒเธ”เธเธฒเธเธญเธขเนเธฒเธ");
            }
         } else if (splitted.length == 2) {
            c.getPlayer().changeMap(victim.getMap(), victim.getMap().findClosestSpawnpoint(victim.getTruePosition()));
         } else {
            Field targetx = GameServer.getInstance(c.getChannel()).getMapFactory()
                  .getMap(Integer.parseInt(splitted[2]));
            if (targetx == null) {
               c.getPlayer().dropMessage(6, "เนเธกเนเธเธเนเธเธเธ—เธตเน");
            }

            Portal targetPortalx = null;
            if (splitted.length > 3) {
               try {
                  targetPortalx = targetx.getPortal(Integer.parseInt(splitted[3]));
               } catch (IndexOutOfBoundsException var14) {
                  c.getPlayer().dropMessage(5, "เน€เธฅเธทเธญเธเธเธญเธฃเนเธ—เธฑเธฅเนเธกเนเธ–เธนเธเธ•เนเธญเธ");
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
               c.getPlayer().dropMessage(5, "เนเธกเนเธชเธฒเธกเธฒเธฃเธ–เธงเธฒเธฃเนเธเธเธนเนเน€เธฅเนเธเธเนเธณเน เนเธ”เน");
            }

            victim.changeMap(c.getPlayer().getMap(),
                  c.getPlayer().getMap().findClosestPortal(c.getPlayer().getTruePosition()));
         } else {
            int ch = Center.Find.findChannel(splitted[1]);
            if (ch < 0) {
               c.getPlayer().dropMessage(5, "เนเธกเนเธเธเธ•เธฑเธงเธฅเธฐเธเธฃ");
            }

            victim = GameServer.getInstance(ch).getPlayerStorage().getCharacterByName(splitted[1]);
            if (victim == null || victim.inPVP()
                  || !c.getPlayer().isGM() && (victim.isInBlockedMap() || victim.isGM())) {
               c.getPlayer().dropMessage(5, "เนเธกเนเธชเธฒเธกเธฒเธฃเธ–เธงเธฒเธฃเนเธเธเธนเนเน€เธฅเนเธเนเธ”เน");
            }

            c.getPlayer().dropMessage(5, "เธเธณเธฅเธฑเธเน€เธฃเธตเธขเธเธเธนเนเน€เธฅเนเธเธเธฒเธเนเธเธเนเธเธฅเธญเธทเนเธ");
            victim.dropMessage(5, "เธเธธเธ“เธ–เธนเธเน€เธฃเธตเธขเธเธ•เธฑเธงเนเธ”เธข GM");
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
            c.getPlayer().dropMessage(5, "เนเธกเนเธเธเนเธเธเธ—เธตเน " + Integer.parseInt(splitted[1]));
            return;
         }

         Portal targetPortalxx = null;
         if (splitted.length > 2) {
            try {
               targetPortalxx = targetxx.getPortal(Integer.parseInt(splitted[2]));
            } catch (IndexOutOfBoundsException var9) {
               c.getPlayer().dropMessage(5, "เน€เธฅเธทเธญเธเธเธญเธฃเนเธ—เธฑเธฅเนเธกเนเธ–เธนเธเธ•เนเธญเธ");
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
            new CommandDefinition("!warp", "<เธเธทเนเธญเธ•เธฑเธงเธฅเธฐเธเธฃ> (<เธฃเธซเธฑเธชเนเธเธเธ—เธตเน>)",
                  "เธงเธฒเธฃเนเธเธ•เธฑเธงเน€เธญเธเนเธเธซเธฒเธเธนเนเน€เธฅเนเธ เธซเธฃเธทเธญเธงเธฒเธฃเนเธเธเธนเนเน€เธฅเนเธเนเธเธขเธฑเธเนเธเธเธ—เธตเน", 2),
            new CommandDefinition("!warphere", "<เธเธทเนเธญเธ•เธฑเธงเธฅเธฐเธเธฃ>", "เน€เธฃเธตเธขเธเธเธนเนเน€เธฅเนเธเธกเธฒเธซเธฒเธเธธเธ“", 2),
            new CommandDefinition("!map", "<เธฃเธซเธฑเธชเนเธเธเธ—เธตเน>", "เธงเธฒเธฃเนเธเธ•เธฑเธงเน€เธญเธเนเธเธขเธฑเธเนเธเธเธ—เธตเนเธ—เธตเนเธฃเธฐเธเธธ", 2)
      };
   }
}
