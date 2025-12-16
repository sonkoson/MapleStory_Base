package script.ArcaneRiver;

import constants.ServerConstants;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import scripting.ScriptMessageFlag;
import scripting.newscripting.Script;
import scripting.newscripting.ScriptEngineNPC;

public class LabyrinthofSuffering extends ScriptEngineNPC {

    public void q35740s() {
        target.say("มาโค่น #bVerus Hilla#k ตัวจริงที่ไม่ใช่ภาพลวงตาใน Labyrinth of Suffering กันเถอะ",
                ScriptMessageFlag.Scenario, ScriptMessageFlag.Self, ScriptMessageFlag.FlipImage);
        getQuest().forceStart(getPlayer(), getNpc().getId(), "");
    }

    public void q35740e() {
        target.say("อ๊ะ ท่าน Cygnus ติดต่อมา..?", ScriptMessageFlag.Scenario, ScriptMessageFlag.Self,
                ScriptMessageFlag.FlipImage);
        initNPC(MapleLifeFactory.getNPC(3003750));
        if (1 == self.askAccept(
                "#face0#คุณ #h0# ที่พิชิต Labyrinth of Suffering และโค่น #bVerus Hilla#k ได้...\r\n สิ่งนี้จะช่วยเชิดชูเกียรติยศของคุณ\r\n\r\n#i1143136# #r#t1143136#",
                ScriptMessageFlag.Scenario)) {
            if (target.exchange(1143136, 1) > 0) {
                getQuest().forceComplete(getPlayer(), getNpc().getId());
            } else {
                self.say("#face0#กรุณาทำช่องในกระเป๋า Equip ให้ว่าง 1 ช่องขึ้นไป แล้วกลับมาคุยใหม่อีกครั้งค่ะ",
                        ScriptMessageFlag.Scenario);
            }
        }
    }

    @Script
    public void go_MoonBridge() {
        if (getPlayer().getLevel() >= 250) {
            registerTransferField(450009300, 1);
        } else {
            getPlayer().dropMessage(5, "เลเวล 250 ขึ้นไปเท่านั้นที่สามารถเข้าได้");
        }
    }

    @Script
    public void west_450011320() {
        if (getPlayer().getLevel() >= 250) {
            registerTransferField(450011600, 1);
        } else {
            getPlayer().dropMessage(5, "เลเวล 250 ขึ้นไปเท่านั้นที่สามารถเข้าได้");
        }
    }

    @Script
    public void west_450011220() {
        if (getPlayer().getLevel() >= 250) {
            registerTransferField(450011530, 2);
        } else {
            getPlayer().dropMessage(5, "เลเวล 250 ขึ้นไปเท่านั้นที่สามารถเข้าได้");
        }
    }

    @Script
    public void west_450011510() {
        if (getPlayer().getLevel() >= 250) {
            registerTransferField(450011500, 2);
        } else {
            getPlayer().dropMessage(5, "เลเวล 250 ขึ้นไปเท่านั้นที่สามารถเข้าได้");
        }
    }

    @Script
    public void pt_450011500() {
        if (getPlayer().getLevel() >= 250) {
            registerTransferField(450011510, 1);
        } else {
            getPlayer().dropMessage(5, "เลเวล 250 ขึ้นไปเท่านั้นที่สามารถเข้าได้");
        }
    }

    @Script
    public void east_450011420() {
        if (getPlayer().getLevel() >= 250) {
            registerTransferField(450011120, 2);
        } else {
            getPlayer().dropMessage(5, "เลเวล 250 ขึ้นไปเท่านั้นที่สามารถเข้าได้");
        }
    }

    @Script
    public void south_450011220() {
        if (getPlayer().getLevel() >= 250) {
            registerTransferField(450011590, 0);
        } else {
            getPlayer().dropMessage(5, "เลเวล 250 ขึ้นไปเท่านั้นที่สามารถเข้าได้");
        }
    }

    @Script
    public void pt_nextMaze3() {
        if (getPlayer().getLevel() >= 250) {
            registerTransferField(450011320, 3);
        } else {
            getPlayer().dropMessage(5, "เลเวล 250 ขึ้นไปเท่านั้นที่สามารถเข้าได้");
        }
    }

    public void east_450011320() {
        initNPC(MapleLifeFactory.getNPC(9001000));
        if (getPlayer().getLevel() >= 250) {
            String text = "สัมผัสได้ถึงพลังงานผิดปกติ จะไปที่ไหนดี?\r\n\r\n#b#L0#ทางเข้า Altar of Desire#l";
            if (getPlayer().getLevel() >= 255) {
                text += "\r\n#L1#Limen - World's Tears#l";
            }
            int v = target.askMenu(text, ScriptMessageFlag.Scenario, ScriptMessageFlag.Self,
                    ScriptMessageFlag.FlipImage);
            switch (v) {
                case 0: { // Altar of Desire Entrance
                    registerTransferField(450011990, 1);
                    break;
                }
                case 1: { // Limen World's Tears
                    if (getPlayer().getLevel() >= 255) {
                        registerTransferField(450012000, 0);
                    }
                    break;
                }
            }
        } else {
            getPlayer().dropMessage(5, "เลเวล 250 ขึ้นไปเท่านั้นที่สามารถเข้าได้");
        }
    }

    @Script
    public void east_450011220() {
        if (getPlayer().getLevel() >= 250) {
            registerTransferField(450011540, 1);
        } else {
            getPlayer().dropMessage(5, "เลเวล 250 ขึ้นไปเท่านั้นที่สามารถเข้าได้");
        }
    }

    @Script
    public void pt_altarIn() {
        if (getPlayer().getLevel() >= 250) {
            getPlayer().updateOneInfo(450011580, "altar", getPlayer().getMap().getId() + "");
            registerTransferField(450011580, 0);
        } else {
            getPlayer().dropMessage(5, "เลเวล 250 ขึ้นไปเท่านั้นที่สามารถเข้าได้");
        }
    }

    @Script
    public void pt_altarOut() {
        if (getPlayer().getLevel() >= 250) {
            int mapid = getPlayer().getOneInfoQuestInteger(450011580, "altar");
            registerTransferField(mapid, 0);
        } else {
            getPlayer().dropMessage(5, "เลเวล 250 ขึ้นไปเท่านั้นที่สามารถเข้าได้");
        }
    }

    public void BPReturn_JinHill() {
        initNPC(MapleLifeFactory.getNPC(9001000));
        if (getPlayer().getLevel() >= 250) {
            int v = target.askMenu(
                    "จะไปที่ไหนดี?\r\n#b#L0#ไปยังจัตุรัส#l\r\n#L1#ศูนย์บัญชาการส่วนลึกสุดของ Labyrinth of Suffering#l");
            switch (v) {
                case 0: { // Square
                    registerTransferField(ServerConstants.TownMap);
                    break;
                }
                case 1: { // World's Tears Middle 3
                    registerTransferField(450011320, 7);
                    break;
                }
            }
        } else {
            getPlayer().dropMessage(5, "เลเวล 250 ขึ้นไปเท่านั้นที่สามารถเข้าได้");
        }
    }
}
