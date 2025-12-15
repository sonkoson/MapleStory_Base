package commands;

import network.models.CField;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.fields.gameobject.lifes.MapleMonster;
import objects.fields.gameobject.lifes.OverrideMonsterStats;
import objects.users.MapleClient;
import objects.utils.StringUtil;

public class SpawnMonsterCommand implements Command {
   @Override
   public void execute(MapleClient c, String[] splitted) throws Exception, IllegalCommandSyntaxException {
      if (splitted[0].equals("!em")) {
         int mid = Integer.parseInt(splitted[1]);
         MapleMonster mob = MapleLifeFactory.getMonster(mid);
         mob.setElite();
         c.getPlayer().getMap().startMapEffect("เธเธฅเธฑเธเนเธซเนเธเธเธงเธฒเธกเธกเธทเธ”เธ—เธณเนเธซเน Elite Monster เธเธฃเธฒเธเธเธ•เธฑเธงเธเธถเนเธ", 5120124, false,
               5);
         c.getPlayer().getMap().broadcastMessage(CField.getSpecialMapSound("Field.img/eliteMonster/Regen"));
         c.getPlayer().getMap().spawnMonsterOnGroundBelow(mob, c.getPlayer().getPosition());
      } else if (splitted[0].equals("!mob")) {
         int mid = Integer.parseInt(splitted[1]);
         int num = Math.min(StringUtil.getOptionalIntArg(splitted, 2, 1), 300);
         Long hp = CommandProcessorUtil.getNamedLongArg(splitted, 1, "hp");
         Integer exp = CommandProcessorUtil.getNamedIntArg(splitted, 1, "exp");
         Double php = CommandProcessorUtil.getNamedDoubleArg(splitted, 1, "php");
         Double pexp = CommandProcessorUtil.getNamedDoubleArg(splitted, 1, "pexp");
         MapleMonster onemob = MapleLifeFactory.getMonster(mid);
         long newhp = 0L;
         long newexp = 0L;
         double oldExpRatio = (double) onemob.getHp() / onemob.getMobExp();
         if (hp != null) {
            newhp = hp;
         } else if (php != null) {
            newhp = (long) (onemob.getMobMaxHp() * (php / 100.0));
         } else {
            newhp = onemob.getMobMaxHp();
         }

         if (exp != null) {
            newexp = exp.intValue();
         } else if (pexp != null) {
            newexp = (int) (onemob.getMobExp() * (pexp / 100.0));
         } else {
            newexp = onemob.getMobExp();
         }

         if (newhp < 1L) {
            newhp = 1L;
         }

         double newExpRatio = (double) newhp / newexp;
         if (c.getPlayer().getGMLevel() <= 5
               && (mid == 8810018
                     || mid == 8810118
                     || mid == 5100001
                     || mid == 5130106
                     || mid == 8190001
                     || mid == 9001009
                     || mid == 9300256
                     || mid == 9300257
                     || mid == 9300280
                     || mid == 9300281
                     || mid == 9300282
                     || mid == 9300283
                     || mid == 9300284)) {
            c.getPlayer().dropMessage(6, "เธกเธญเธเธชเน€เธ•เธญเธฃเนเธ•เธฑเธงเธเธตเนเธ–เธนเธเธเธณเธเธฑเธ”");
            return;
         }

         OverrideMonsterStats overrideStats = new OverrideMonsterStats();
         overrideStats.setOHp(newhp);
         overrideStats.setOExp(newexp);
         overrideStats.setOMp(onemob.getMobMaxMp());

         for (int i = 0; i < num; i++) {
            MapleMonster mob = MapleLifeFactory.getMonster(mid);
            mob.setHp(newhp);
            mob.setOverrideStats(overrideStats);
            c.getPlayer().getMap().spawnMonsterOnGroundBelow(mob, c.getPlayer().getPosition());
         }
      }
   }

   @Override
   public CommandDefinition[] getDefinition() {
      return new CommandDefinition[] {
            new CommandDefinition("!mob", "<Monster ID> (hp <HP>) (exp <EXP>) (php <HP%>) (pexp <EXP%>)",
                  "เน€เธฃเธตเธขเธเธกเธญเธเธชเน€เธ•เธญเธฃเนเธ”เนเธงเธข ID เธ—เธตเนเธเธณเธซเธเธ”", 2),
            new CommandDefinition("!em", "<Monster ID>", "เน€เธฃเธตเธขเธ Elite Monster", 6)
      };
   }
}
