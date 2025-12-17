package objects.fields.child.karing;

import network.models.CField;
import objects.fields.child.karing.FieldSkill.KaringFieldAction;
import objects.fields.gameobject.lifes.MapleMonster;
import objects.fields.gameobject.lifes.mobskills.MobSkillFactory;
import objects.users.MapleCharacter;
import objects.users.stats.SecondaryStatFlag;

public class Field_BossKaringPhase2 extends Field_BossKaring {
   private long nextParadeTime = 0L;
   private int FieldSkill = 100029;
   private int FieldSkillLevel = 1;

   public Field_BossKaringPhase2(int mapid, int channel, int returnMapId, float monsterRate) {
      super(mapid, channel, returnMapId, monsterRate);
   }

   @Override
   public void fieldUpdatePerSeconds() {
      super.fieldUpdatePerSeconds();
      MapleMonster boss = this.findBoss();
      if (this.nextParadeTime <= System.currentTimeMillis()) {
         this.nextParadeTime = System.currentTimeMillis() + 60000L;
         KaringFieldAction.InitPacket.Parade parade = new KaringFieldAction.InitPacket.Parade(this.FieldSkill, this.FieldSkillLevel);
         parade.broadcastPacket(this);
         this.broadcastMessage(CField.sendWeatherEffectNotice(385, 5000, false, "วิญญาณแค้นของสัตว์ประหลาดที่ Karing ดูดซับกำลังดิ้นรน ต้องหลบหลีกเพื่อไม่ให้ถูกกลืนกิน"));
      }

      if (boss != null) {
         ;
      }
   }

   @Override
   public void resetFully(boolean respawn) {
      super.resetFully(respawn);
   }

   @Override
   public void onEnter(MapleCharacter player) {
      super.onEnter(player);
      player.send(CField.sendWeatherEffectNotice(382, 5000, false, "Karing ที่ดูดซับ 4 สัตว์ร้ามา ดูเหมือนจะอาละวาดได้ทุกเมื่อ"));
   }

   @Override
   public void onUserHit(MapleCharacter player, int mobTemplateID, int skillID, int skillLevel, int attackIndex) {
      super.onUserHit(player, mobTemplateID, skillID, skillLevel, attackIndex);
      player.getStat().heal(player);
      MapleMonster mob = this.getMonsterById(mobTemplateID);
      if (!player.isInvincible()) {
         if (skillID == 277) {
         }

         if (mobTemplateID == 8880837 && attackIndex == 4) {
            player.giveDebuff(SecondaryStatFlag.karing2phaseConfine, MobSkillFactory.getMobSkill(277, 1));
         }
      }
   }

   @Override
   public void onLeave(MapleCharacter player) {
      super.onLeave(player);
   }

   @Override
   public void onMobEnter(MapleMonster mob) {
      super.onMobEnter(mob);
   }

   @Override
   public void onPlayerDead(MapleCharacter player) {
      super.onPlayerDead(player);
   }

   @Override
   public void onMobKilled(MapleMonster mob) {
      super.onMobKilled(mob);
   }

   @Override
   public void onMobSkill(MapleMonster mob, int skillID, int skillLevel) {
      super.onMobSkill(mob, skillID, skillLevel);
      switch (skillID) {
         case 274:
      }
   }
}
