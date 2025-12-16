package script.item;

import constants.GameConstants;
import network.models.CField;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.users.skills.Skill;
import objects.users.skills.SkillEntry;
import objects.users.skills.SkillFactory;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

import java.util.*;

public class MasteryBook extends ScriptEngineNPC {

    public void consume_2431789() {
        consumeMasteryBook20(2431789);
    }

    public void consume_2431892() {
        consumeMasteryBook20(2431892);
    }

    public void consume_2431935() {
        consumeMasteryBook20(2431935);
    }

    public void consume_2432643() {
        consumeMasteryBook20(2432643);
    }

    public void consume_2431790() {
        consumeMasteryBook30(2431790);
    }

    public void consume_2431936() {
        consumeMasteryBook30(2431936);
    }

    public void consume_2432644() {
        consumeMasteryBook30(2432644);
    }

    public void consumeMasteryBook20(int itemId) {
        initNPC(MapleLifeFactory.getNPC(2080008));
        List<Integer> s = new ArrayList<>();
        List<Integer> skills = SkillFactory.getSkillsByJob(getPlayer().getJob());
        if (skills != null) {
            for (int skill : skills) {
                final Skill skil = SkillFactory.getSkill(skill);
                if (GameConstants.isSkillNeedMasterLevel(skil.getId())) {
                    if (getPlayer().getSkills().get(skil) != null) {
                        if (getPlayer().getSkills().get(skil).masterlevel < 20 && skil.getMaxLevel() >= 20) {
                            s.add(skill);
                        }
                    }
                }
            }
        }

        if (s.size() > 0) {
            String skillList = "";
            for (int skill : s) {
                skillList += "#L" + skill + "# " + "#s" + skill + "# #fn돋움##fs14##e#q" + skill + "##n#fs##fn##l\r\n";
            }
            int v0 = self.askMenu("자네가 올릴 수 있는 สกิล รายการ은 ถัดไปและ 같네.\r\n\r\n" + skillList + "\r\n#r#L0# #fn돋움##fs14##e마스터리 북 ใช้을 ยกเลิก한다.#n#fs##fn##l", ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
            if (v0 != 0) { //마스터리북 ใช้ยกเลิก หรือ
                if (skills.contains(v0)) {
                    if (target.exchange(itemId, -1) > 0) {
                        Map<Skill, SkillEntry> list = new HashMap<>();
                        list.put(SkillFactory.getSkill(v0), new SkillEntry((byte) getPlayer().getSkillLevel(v0), (byte) 20, -1));
                        getPlayer().changeSkillsLevel(list);
                        //마스터리북 이펙트뜨게
                        getPlayer().dropMessage(5, "마스터리 북이 สำเร็จ적으로 ใช้되었.");
                        getPlayer().send(CField.environmentChange("masteryBook/EnchantSuccess", 5, 100));
                    }
                }
            }
        } else {
            self.askMenu("자네는 아직 어떤 4ชา สกิล 배우지 않았거ฉัน ปัจจุบัน  마스터리 북이 ใช้งาน될 สกิล 없는 모양วัน세. ยืนยัน해보고 ถัดไป에 다시 ใช้하게ฉัน.\r\n\r\n#r#L0# #fn돋움##fs14##e마스터리 북 ใช้을 ยกเลิก한다.#n#fs##fn##l", ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
        }
    }

    public void consumeMasteryBook30(int itemId) {
        initNPC(MapleLifeFactory.getNPC(2080008));
        List<Integer> s = new ArrayList<>();
        List<Integer> skills = SkillFactory.getSkillsByJob(getPlayer().getJob());
        if (skills != null) {
            for (int skill : skills) {
                final Skill skil = SkillFactory.getSkill(skill);
                if (GameConstants.isSkillNeedMasterLevel(skil.getId())) {
                    if (getPlayer().getSkills().get(skil) != null) {
                        if (getPlayer().getSkills().get(skil).masterlevel < 30 && skil.getMaxLevel() == 30) {
                            s.add(skill);
                        }
                    }
                }
            }
        }

        if (s.size() > 0) {
            String skillList = "";
            for (int skill : s) {
                skillList += "#L" + skill + "# " + "#s" + skill + "# #fn돋움##fs14##e#q" + skill + "##n#fs##fn##l\r\n";
            }
            int v0 = self.askMenu("자네가 올릴 수 있는 สกิล รายการ은 ถัดไปและ 같네.\r\n\r\n" + skillList + "\r\n#r#L0# #fn돋움##fs14##e마스터리 북 ใช้을 ยกเลิก한다.#n#fs##fn##l", ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
            if (v0 != 0) { //마스터리북 ใช้ยกเลิก หรือ
                if (skills.contains(v0)) {
                    if (target.exchange(itemId, -1) > 0) {
                        Map<Skill, SkillEntry> list = new HashMap<>();
                        list.put(SkillFactory.getSkill(v0), new SkillEntry((byte) getPlayer().getSkillLevel(v0), (byte) 30, -1));
                        getPlayer().changeSkillsLevel(list);
                        //마스터리북 이펙트뜨게
                        getPlayer().dropMessage(5, "마스터리 북이 สำเร็จ적으로 ใช้되었.");
                        getPlayer().send(CField.environmentChange("masteryBook/EnchantSuccess", 5, 100));
                    }
                }
            }
        } else {
            self.askMenu("자네는 아직 어떤 4ชา สกิล 배우지 않았거ฉัน ปัจจุบัน  마스터리 북이 ใช้งาน될 สกิล 없는 모양วัน세. ยืนยัน해보고 ถัดไป에 다시 ใช้하게ฉัน.\r\n\r\n#r#L0# #fn돋움##fs14##e마스터리 북 ใช้을 ยกเลิก한다.#n#fs##fn##l", ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
        }
    }
}
