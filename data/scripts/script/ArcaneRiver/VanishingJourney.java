package script.ArcaneRiver;

import network.models.CField;
import network.models.CWvsContext;
import network.models.FontColorType;
import network.models.FontType;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.users.MapleCharacter;
import objects.users.stats.SecondaryStatEffect;
import objects.users.skills.SkillFactory;
import objects.utils.Randomizer;
import objects.utils.Timer;
import scripting.ScriptMessageFlag;
import scripting.newscripting.Script;
import scripting.newscripting.ScriptEngineNPC;

public class VanishingJourney extends ScriptEngineNPC {

    @Script
    public void north_450001000() {
        if (getPlayer().getLevel() >= 205) {
            registerTransferField(450001002, 1);
        } else {
            getPlayer().dropMessage(5, "เลเวล 205 ขึ้นไปเท่านั้นที่สามารถเข้าได้");
        }
    }

    @Script
    public void east_450001002() {
        registerTransferField(450014010, 2);
    }

    @Script
    public void east_450014090() {
        registerTransferField(450014100, 1);
    }

    @Script
    public void east_450014130() {
        registerTransferField(450014140, 1);
    }

    @Script
    public void east_450014200() {
        registerTransferField(450014210, 1);
    }

    @Script
    public void east_450014240() {
        getPlayer().dropMessage(5, "ตอนนี้ไม่มีธุระอะไร");
    }

    public void _450001005_PS00() {
        initNPC(MapleLifeFactory.getNPC(3003110));
        if (1 == self.askYesNo(
                "...ฉันกำลังจะไปเขตเพลิงแห่งการสูญสลายพอดี... ถ้าอยากไปฉันไปส่งให้ได้นะ...\r\n\r\n#b(หากยอมรับ จะนั่งเรือไปยังทะเลสาบแห่งการลืมเลือน)#k",
                ScriptMessageFlag.NpcReplacedByNpc)) {
            self.say("...งั้น ออกเดินทางกันเลย...", ScriptMessageFlag.NpcReplacedByNpc);
            target.registerTransferField(450001007);
        }
    }

    @Script
    public void enter_450001007() {
        bigScriptProgressMessage("สามารถบังคับเรือได้ด้วยปุ่มลูกศร", FontType.NanumGothicBold,
                FontColorType.LightGreen);
        SecondaryStatEffect effect = SkillFactory.getSkill(80002201).getEffect(1);
        effect.applyTo(getPlayer());
    }

    @Script
    public void _450001007_PCS00() {
        getPlayer().temporaryStatResetBySkillID(80002201);
        registerTransferField(450001005);
    }

    @Script
    public void arcane_water() {
        int random = Randomizer.rand(0, 2);
        getPlayer().send(CField.environmentChange("Sound/Foot.img/water/" + random, 5, 200));
    }

    @Script
    public void _450001007_PCS01() {
        getPlayer().temporaryStatResetBySkillID(80002201);
        registerTransferField(450001105, 1);
    }

    public void _450001105_PS00() {
        initNPC(MapleLifeFactory.getNPC(3003110));
        if (1 == self.askYesNo(
                "...ฉันกำลังจะกลับไปที่หมู่บ้านไร้นามพอดี... เจ้าอยากกลับไปเหมือนกันรึเปล่า?\r\n\r\n#b(หากยอมรับ จะนั่งเรือไปยังทะเลสาบแห่งการลืมเลือน)#k",
                ScriptMessageFlag.NpcReplacedByNpc)) {
            self.say("...งั้น ฉันจะไปส่งที่หมู่บ้านไร้นามให้เอง...", ScriptMessageFlag.NpcReplacedByNpc);
            getSc().flushSay();
            registerTransferField(450001007, 2);
        }
    }

    @Script
    public void _450001105_PS01() {
        registerTransferField(450001107, 1);
        bigScriptProgressMessage("สามารถปีนหน้าผาขึ้นไปเพื่อไปยังหน้าผาภาพลวงตาได้", FontType.NanumGothicBold,
                FontColorType.LightGreen);
    }

    @Script
    public void _450001107_PCS01() {
        registerTransferField(450001100, 0);
    }

    @Script
    public void _450001107_PCS02() {
        registerTransferField(450001105, 0);
    }

    @Script
    public void _450001107_PCS03() {
        registerTransferField(450001100, 0);
    }

    @Script
    public void enter_450001107() {
        if (getPlayer().getTruePosition().y < -1500) {
            bigScriptProgressMessage("สามารถปีนหน้าผาลงไปเพื่อไปยังริมทะเลสาบได้", FontType.NanumGothicBold,
                    FontColorType.LightGreen);
            getPlayer().send(CField.UIPacket.setIngameDirectionMode(false, false, false));
            getPlayer().send(CField.DirectionPacket.getDirectionInfo(15, 0));
            getPlayer().send(CField.DirectionPacket.getDirectionInfo(3, 7));
            getPlayer().send(CField.DirectionPacket.getDirectionInfo(3, 3));
            getPlayer().send(CField.DirectionPacket.getDirectionInfo(1, 10));
            getPlayer().send(CField.DirectionPacket.getCurNodeEventEnd(1));
            MapleCharacter player = getPlayer();
            Timer.MapTimer.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    player.send(CField.DirectionPacket.getDirectionInfo(3, 0));
                    player.send(CField.DirectionPacket.getDirectionInfo(22, 700));
                    player.send(CField.UIPacket.endInGameDirectionMode(1));
                    player.send(CWvsContext.enableActions(getPlayer()));
                }
            }, 700);
        } else {
            getPlayer().send(CField.UIPacket.setIngameDirectionMode(false, false, false));
            getPlayer().send(CField.DirectionPacket.getDirectionInfo(15, 0));
            getPlayer().send(CField.DirectionPacket.getDirectionInfo(3, 7));
            getPlayer().send(CField.DirectionPacket.getDirectionInfo(3, 3));
            getPlayer().send(CField.DirectionPacket.getDirectionInfo(1, 10));
            getPlayer().send(CField.DirectionPacket.getCurNodeEventEnd(1));
            MapleCharacter player = getPlayer();
            Timer.MapTimer.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    player.send(CField.DirectionPacket.getDirectionInfo(3, 0));
                    player.send(CField.DirectionPacket.getDirectionInfo(22, 700));
                    player.send(CField.UIPacket.endInGameDirectionMode(1));
                    player.send(CWvsContext.enableActions(getPlayer()));
                }
            }, 1000);
        }
    }

    public void _450001100_PS00() {
        initNPC(MapleLifeFactory.getNPC(3003136));
        self.say("ดูเหมือนอารมณ์ดีนะ ฉันพาไปถ้ำแห่งการพักผ่อนได้", ScriptMessageFlag.NpcReplacedByNpc);
        if (1 == self.askYesNo("#b(หากยอมรับ จะขี่นกเพลิงไปยังถ้ำแห่งการพักผ่อน)#k",
                ScriptMessageFlag.NpcReplacedByNpc)) {
            getPlayer().send(CField.environmentChange("Sound/SoundEff.img/ArcaneRiver/fireBird3", 5, 100));
            registerTransferField(450001200, 2);
        }
    }

    @Script
    public void _450001210_PS00() {
        playPortalSE();
        registerTransferField(450001215, 1);
    }

    @Script
    public void _450001215_PS00() {
        playPortalSE();
        registerTransferField(450001218, 1);
    }

    @Script
    public void _450001218_PS00() {
        playPortalSE();
        registerTransferField(450001219, 1);
    }

    @Script
    public void _450001219_PS00() {
        playPortalSE();
        registerTransferField(450001230, 1);
    }

    @Script
    public void _450001219_PS01() {
        playPortalSE();
        registerTransferField(450001240, 1);
    }

    @Script
    public void _450001240_PS00() {
        if (getPlayer().getLevel() >= 210) {
            playPortalSE();
            registerTransferField(450001250, 1);
        } else {
            getPlayer().dropMessage(5, "เลเวล 210 ขึ้นไปเท่านั้นที่สามารถเข้าได้");
        }
    }

    @Script
    public void _450001250_PCS00() {
        if (getPlayer().getLevel() >= 210) {
            playPortalSE();
            registerTransferField(450002015, 0);
        } else {
            getPlayer().dropMessage(5, "เลเวล 210 ขึ้นไปเท่านั้นที่สามารถเข้าได้");
        }
    }
}
