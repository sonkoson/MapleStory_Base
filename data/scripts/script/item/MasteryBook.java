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
            int v0 = self.askMenu(
                    "รายการสกิลที่คุณสามารถอัปเกรดได้มีดังนี้\r\n\r\n" + skillList
                            + "\r\n#r#L0# #fn돋움##fs14##eยกเลิกการใช้ Mastery Book#n#fs##fn##l",
                    ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
            if (v0 != 0) { // Cancel Mastery Book use or
                if (skills.contains(v0)) {
                    if (target.exchange(itemId, -1) > 0) {
                        Map<Skill, SkillEntry> list = new HashMap<>();
                        list.put(SkillFactory.getSkill(v0),
                                new SkillEntry((byte) getPlayer().getSkillLevel(v0), (byte) 20, -1));
                        getPlayer().changeSkillsLevel(list);
                        // Show Mastery Book effect
                        getPlayer().dropMessage(5, "ใช้ Mastery Book สำเร็จ");
                        getPlayer().send(CField.environmentChange("masteryBook/EnchantSuccess", 5, 100));
                    }
                }
            }
        } else {
            self.askMenu(
                    "ดูเหมือนว่าคุณยังไม่ได้เรียนสกิลคลาส 4 หรือไม่มีสกิลที่สามารถใช้ Mastery Book ได้ในขณะนี้ กรุณาตรวจสอบและลองใหม่อีกครั้งภายหลัง\r\n\r\n#r#L0# #fn돋움##fs14##eยกเลิกการใช้ Mastery Book#n#fs##fn##l",
                    ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
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
            int v0 = self.askMenu(
                    "รายการสกิลที่คุณสามารถอัปเกรดได้มีดังนี้\r\n\r\n" + skillList
                            + "\r\n#r#L0# #fn돋움##fs14##eยกเลิกการใช้ Mastery Book#n#fs##fn##l",
                    ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
            if (v0 != 0) { // Cancel Mastery Book use or
                if (skills.contains(v0)) {
                    if (target.exchange(itemId, -1) > 0) {
                        Map<Skill, SkillEntry> list = new HashMap<>();
                        list.put(SkillFactory.getSkill(v0),
                                new SkillEntry((byte) getPlayer().getSkillLevel(v0), (byte) 30, -1));
                        getPlayer().changeSkillsLevel(list);
                        // Show Mastery Book effect
                        getPlayer().dropMessage(5, "ใช้ Mastery Book สำเร็จ");
                        getPlayer().send(CField.environmentChange("masteryBook/EnchantSuccess", 5, 100));
                    }
                }
            }
        } else {
            self.askMenu(
                    "ดูเหมือนว่าคุณยังไม่ได้เรียนสกิลคลาส 4 หรือไม่มีสกิลที่สามารถใช้ Mastery Book ได้ในขณะนี้ กรุณาตรวจสอบและลองใหม่อีกครั้งภายหลัง\r\n\r\n#r#L0# #fn돋움##fs14##eยกเลิกการใช้ Mastery Book#n#fs##fn##l",
                    ScriptMessageFlag.NpcReplacedByNpc, ScriptMessageFlag.NoEsc);
        }
    }
}
