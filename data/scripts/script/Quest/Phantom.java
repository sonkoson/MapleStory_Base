package script.Quest;

import constants.GameConstants;
import objects.users.skills.SkillFactory;
import scripting.newscripting.ScriptEngineNPC;
import scripting.NPCScriptManager;

public class Phantom extends ScriptEngineNPC {

    public void phantomskill() {
        if (getPlayer().getLevel() >= 100) {
            // getClient().removeClickedNPC();
            // NPCScriptManager.getInstance().start(getClient(), 1052206, "บอสย้าย", true);
            // return;
            self.sayOk("#fs11#คำสั่งนี้ไม่สามารถใช้งานได้ในขณะนี้ครับ");
            return;
        }

        if (!GameConstants.isPhantom(getPlayer().getJob())) {
            self.sayOk("#fs11#คุณไม่ใช่ Phantom จึงไม่สามารถใช้งานฟังก์ชันนี้ได้ครับ");
            return;
        }
        String steal = "#fs11##e[สกิลคลาส 1]#n\r\n";
        if (getPlayer().getLevel() >= 10) {
            // Class 1 only
            steal += "#L1# #s4001003# Dark Sight#l\r\n";
            steal += "#L2# #s3011004# Cardinal Discharge#l\r\n";
            steal += "#L3# #s2001002# Magic Guard#l\r\n";
        }
        if (getPlayer().getLevel() >= 30) {

            steal += "\r\n\r\n#e[สกิลคลาส 2]#n\r\n";
            steal += "#L4# #s3301003# Cardinal Blast#l\r\n";
            steal += "#L5# #s2301002# Heal#l\r\n";
            steal += "#L6# #s1101006# Spirit Blade#l\r\n";
            // Class 2 limit
        }
        if (getPlayer().getLevel() >= 60) {
            steal += "\r\n\r\n#e[สกิลคลาส 3]#n\r\n";
            steal += "#L8# #s3111013# Arrow Platter#l\r\n";
            steal += "#L9# #s1311015# Cross Over Chain#l\r\n";
            steal += "#L10# #s4331011# Blade Ascension#l\r\n";
            steal += "#L11# #s4331006# Chains of Hell#l\r\n";
            steal += "#L12# #s2311009# Holy Magic Shell#l\r\n";
            steal += "#L13# #s2311003# Holy Symbol#l\r\n";
            steal += "#L14# #s2311011# Holy Fountain#l\r\n";
            // Class 3 limit
        }
        if (getPlayer().getLevel() >= 100) {
            // Class 4 limit
            steal += "\r\n\r\n#e[สกิลคลาส 4]#n\r\n";
            steal += "#L15# #s4341002# Final Cut#l\r\n";
            steal += "#L16# #s1221011# Sanctuary#l\r\n";
            steal += "#L17# #s2221012# Frozen Orb#l\r\n";
            steal += "#L18# #s2221011# Freezing Breath#l\r\n";
            steal += "#L19# #s5321000# Cannon Bazooka#l\r\n";
            steal += "#L20# #s5121010# Time Leap#l\r\n";
        }

        if (getPlayer().getLevel() >= 140) {
            steal += "\r\n#e[Hyper Skill]#n\r\n";
            steal += "#L21# #s3221054# Bullseye#l\r\n";
            steal += "#L22# #s1221054# Sacrosanctity#l\r\n";
            steal += "#L23# #s3121054# Preparation#l\r\n";
        }
        int v = self.askMenu(steal);
        if (getPlayer().getJob() == 2400) { // Phantom 1st Job
            if (v > 3) {
                self.sayOk("#fs11#คำขอไม่ถูกต้อง");
                return;
            }
        } else if (getPlayer().getJob() == 2410) { // Phantom 2nd Job
            if (v > 7) {
                self.sayOk("#fs11#คำขอไม่ถูกต้อง");
                return;
            }
        } else if (getPlayer().getJob() == 2411) { // Phantom 3rd Job
            if (v > 14) {
                self.sayOk("#fs11#คำขอไม่ถูกต้อง");
                return;
            }
        } else if (getPlayer().getJob() == 2412) { // Phantom 4th Job
            if (getPlayer().getLevel() < 140) {
                if (v > 21) {
                    self.sayOk("#fs11#คำขอไม่ถูกต้อง");
                    return;
                }
            }
        }
        int skillId = 0;
        switch (v) {
            case 1: // Dark Sight
                skillId = 4001003;
                break;
            case 2:
                skillId = 3011004;
                break;
            case 3:
                skillId = 2001002;
                break;
            case 4:
                skillId = 3301003;
                break;
            case 5:
                skillId = 2301002;
                break;
            case 6:
                skillId = 1101006;
                break;
            case 8:
                skillId = 3111013;
                break;
            case 9:
                skillId = 1311015;
                break;
            case 10:
                skillId = 4331011;
                break;
            case 11:
                skillId = 4331006;
                break;
            case 12:
                skillId = 2311009;
                break;
            case 13:
                skillId = 2311003;
                break;
            case 14:
                skillId = 2311011;
                break;
            case 15:
                skillId = 4341002;
                break;
            case 16:
                skillId = 1221011;
                break;
            case 17:
                skillId = 2221012;
                break;
            case 18:
                skillId = 2221011;
                break;
            case 19:
                skillId = 5321000;
                break;
            case 20:
                skillId = 5121010;
                break;
            case 21:
                skillId = 3221054;
                break;
            case 22:
                skillId = 1221054;
                break;
            case 23:
                skillId = 3121054;
                break;
        }
        int maxLevel = SkillFactory.getSkill(skillId).getMaxLevel();
        if (maxLevel < 1) {
            self.sayOk("#fs11#เกิดข้อผิดพลาดในการดำเนินการคำขอ");
            return;
        }
        getPlayer().addStolenSkill(skillId, maxLevel);
        // self.sayOk("Success. Check your skill management.");
    }
}
