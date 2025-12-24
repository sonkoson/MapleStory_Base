// Convenience System
importPackage(Packages.scripting.newscripting);
importPackage(java.text);
importPackage(java.lang);
importPackage(Packages.constants);

var enter = "\r\n";
var seld = -1;

Blue = "#fc0xFF0054FF#";
LightBlue = "#fc0xFF6B66FF#";
LightPurple = "#fc0xFF8041D9#";
Purple = "#fc0xFF5F00FF#";
Yellow = "#fc0xFFEDD200#";
BlackColor = "#fc0xFF191919#";

Pet = "#fUI/CashShop.img/CashItem_label/5#";
Pet1 = "#fUI/CashShop.img/CashItem_label/8#";
Guild = "#fUI/Basic.img/RoyalBtn/theblackcoin/14#";
Gemstone = "#fUI/Basic.img/RoyalBtn/theblackcoin/19#";
NameChange = "#fUI/Basic.img/RoyalBtn/theblackcoin/20#";
JobChange = "#fUI/Basic.img/RoyalBtn/theblackcoin/21#";
Storage = "#fUI/Basic.img/RoyalBtn/theblackcoin/13#";
Ability = "#fUI/Basic.img/RoyalBtn/theblackcoin/22#";
Point = "#fUI/Basic.img/RoyalBtn/theblackcoin/7#";
Pocket = "#fUI/Basic.img/RoyalBtn/theblackcoin/23#";
Ranking = "#fUI/Basic.img/RoyalBtn/theblackcoin/24#";
DropItem = "#fUI/Basic.img/RoyalBtn/theblackcoin/25#";
Referral = "#fUI/Basic.img/RoyalBtn/theblackcoin/41#";
BlackMage = "#fUI/Basic.img/RoyalBtn/theblackcoin/42#";
Camo = "#fUI/Basic.img/RoyalBtn/theblackcoin/43#";
Spirit = "#fUI/UIWindow4.img/pointShop/100712/iconShop#";
Donation = "#fUI/UIWindow4.img/pointShop/501053/iconShop#";
Color = "#fc0xFF6600CC#"
Black = "#fc0xFF000000#"
PinkColor = "#fc0xFFFF3366#"
Pink = "#fc0xFFF781D8#"
BlueColor = "#fc0xFF479AEF#"

function start() {
    status = -1;
    action(1, 0, 0);
}
function action(mode, type, sel) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }
    if (status == 0) {
        if (cm.inBoss()) {
            cm.getPlayer().dropMessage(5, "ไม่สามารถใช้งานได้ขณะอยู่ในบอส");
            cm.dispose();
            return;
        }

        var choose = "#fs11##fUI/Basic.img/Zenia/SC/5#\r\n";
        choose += "#Cgray##fs11#――――――――――――――――――――――――――――――――――――――――#fc0xFF000000#\r\n";
        //choose += "#L20##fc0xFFFF3636#" + DropItem + " ทิ้งไอเท็ม#l";

        choose += "　#L248##fc0xFFFF5E00#" + Ranking + " ดูอันดับต่างๆ#l　";
        choose += "#L6##fc0xFF6B66FF# รับเลี้ยง Pet ทั่วไป#l　";
        choose += "#L21##fc0xFF626262#" + Guild + " ไป Guild HQ#l　\r\n\r\n";

        choose += "　#L26##fc0xFF8041D9#" + Referral + " ระบบแนะนำ#l　";
        //choose += "#L13##fc0xFF7112FF#" + Camo + " สีพราง Mechanic#l\r\n";
        choose += "#L24##fc0xFF626262#" + BlackMage + " ใช้คลัง#l　　";
        choose += "#L9##fc0xFF626262#" + BlackMage + " รีเซ็ต Stat#l\r\n\r\n";
        //choose += "#L27##fc0xFFFF3636#" + BlackMage + " เรียนสกิลพิเศษ#l\r\n";

        choose += "　#L777##fc0xFF8041D9#" + Referral + " ระบบสุ่ม#l　  ";
        choose += "#L779##fc0xFF626262#" + BlackMage + " Phantom Steal Skill#l ";
        choose += "#L780##fc0xFF626262#" + BlackMage + " แก้ไขบัค Shade#l\r\n\r\n";

        choose += "　　　#L245#" + BlueColor + Pocket + " [อุปกรณ์] ระบบสะสม#l　";
        choose += "#L246#" + BlueColor + Pocket + " [รวม] ระบบ One-Click#l　\r\n\r\n";

        choose += "#fc0xFFD5D5D5#───────────────────────────#k\r\n";
        //choose += "#L11##fc0xFF6B66FF#" + Gemstone + " V Core Matrix#k。\r\n#l";
        //choose += "#L16##fc0xFFF29661#" + JobChange + " ระบบเปลี่ยนอาชีพ#k。\r\n#l";
        //choose += "#L9##fc0xFFF29661#" + Pocket + " ระบบเช็คดาเมจ#k。\r\n#l";
        //choose += "#L8##fc0xFFF29661#" + Ability + " ระบบ Union#k。\r\n#l";
        //choose += "#L7##fc0xFFF29661#" + NameChange + " เปลี่ยนชื่อตัวละคร#k。\r\n#l";

        //choose += "#L779#" + Color + Referral + " [Phantom] Steal Skill#k。\r\n#l";
        //choose += "#L780#" + Color + Spirit + " [Shade] แก้ไขบัคการโต้ตอบ#k。\r\n#l";
        //choose += "#L777#" + Color + Ability + " ระบบสุ่ม#k。\r\n#l";
        choose += "   #L778#" + Storage + " #bระบบส่งพัสดุ#k\r\n#l";
        //choose += "#L555#" + Color + Donation + " ระบบสนับสนุน#k。\r\n#l";
        //choose += "#L556#" + Color + Donation + " ระบบโปรโมต#k。\r\n#l";
        cm.sendSimple(choose);

    } else if (status == 1) {
        seld = sel;
        switch (sel) {
            case 9999:
                var msg = "#fs11#เมี๊ยว?";

                cm.sendOk(msg);
                cm.dispose();
                break;
            case 1:
            case 1:
                var msg = "\r\n#fs11##fUI/UIWindow8.img/EldaGauge/tooltip/47# #eเคล็ดลับการเล่นเบื้องต้น#n\r\n #b[1] เปิดกล่องอุปกรณ์ที่ได้รับเมื่อเปลี่ยนอาชีพและสวมใส่อุปกรณ์\r\n [2] คอนเทนต์การเติบโต -> เควส -> เคลียร์ไดอารี่การเติบโต\r\n [3] สร้างเครื่องประดับเบื้องต้นที่สามารถทำได้\r\n [4] ใช้ Cube เพื่อปรับปรุงศักยภาพของอุปกรณ์ให้ดีที่สุด\r\n [5] คอนเทนต์การเติบโต -> ตีบวกอุปกรณ์ -> ใช้ Meso Enhance เพื่อตีบวกอุปกรณ์อย่างคุ้มค่า\r\n [6] ปั้นตัวละครสำหรับ Union\r\n [7] หากต้องการแข็รแกร่งขึ้น โปรโมตเซิร์ฟเวอร์เพื่อรับแต้มโปรโมท\r\n [8] เมื่อพักผ่อนให้นั่งเก้าอี้ในเมือง (รับ Neo Gem ทุก 1 นาที ใช้ในร้านค้าพักผ่อน)\r\n\r\n";
                msg += "#fs11##fUI/UIWindow8.img/EldaGauge/tooltip/46# #eเคล็ดลับสกุลเงิน#n\r\n\r\n#r#i4033172##z4033172##k\r\n#fs 11#  วิธีได้รับ : #bChaos Root Abyss#k\r\n  วิธีใช้ : #bวัตถุดิบสร้าง Fafnir ระดับสูง#k\r\n\r\n#r#i4031227##z4031227##k\r\n#fs 11#  วิธีได้รับ : #bบอสตั้งแต่ Lotus ขึ้นไป, แผนที่ล่าแร่#k\r\n  วิธีใช้ : #bตีบวกอุปกรณ์#k\r\n\r\n";
                //msg += "#fs11##fUI/UIWindow8.img/EldaGauge/tooltip/45# #eHot Time#n\r\n\r\n- เวลา 22:30 น. เพียงแค่ออนไลน์ก็ OK\r\n- สามารถรับไอเท็มช่วยเหลือในการเล่นเกมมากมาย"+enter;


                cm.sendSimple(msg);
                break;
            case 2:
                var msg = "#fs11##fUI/UIWindow8.img/EldaGauge/tooltip/46# #rเลเวลสูงสุด : 500#k#b\r\n\r\n#fUI/UIWindow8.img/EldaGauge/tooltip/46# อัตราคูณ EXP\r\n0~200: 400x\r\n200~275: 150x\r\n275~300: 1500x\r\n300~320: 900x\r\n320~340: 800x\r\n340~370: 700x\r\n370~400: 600x\r\n400~410: 500x\r\n410~450: 400x\r\n450~490: 300x\r\n490~500: 100x#k";
                msg += "";

                cm.sendSimple(msg);
                break;
            case 3:
                var msg = "\r\n#fs11##fUI/UIWindow8.img/EldaGauge/tooltip/50# #eแนะนำการโปรโมต#n\r\n\r\n- กรุณาตรวจสอบข้อมูลการโปรโมตจากเว็บไซต์\r\n";
                msg += "" + enter;

                cm.sendSimple(msg);
                break;
            case 4:
                var msg = "\r\n#fs11##fUI/UIWindow8.img/EldaGauge/tooltip/52# #eระบบ Union & Link#n\r\n\r\n- ระบบ Union/Link มีผลต่อพลังโจมตีอย่างมาก\r\n- เนื้อหาเกี่ยวกับระบบ Union จะถูกเพิ่มในเว็บไซต์เร็วๆ นี้\r\n- ข้อมูล Link Skill ด้านล่างอ้างอิงจากเลเวล 120\r\n\r\n\r\n";
                msg += "\r\n#fs11##fUI/UIWindow8.img/EldaGauge/tooltip/43# #eผลของ Link Skill ตามอาชีพ#n\r\n\r\n- Mercedes : EXP ที่ได้รับเพิ่มขึ้น 15%\r\n- Aran : EXP จาก Combo Kill Orb เพิ่มขึ้น 650%\r\n- Evan : ระยะเวลา Rune เพิ่มขึ้น 50%\r\n- Luminous : เจาะเกราะมอนสเตอร์ 15%\r\n- Phantom : Critical Rate เพิ่มขึ้น 15%\r\n- Shade : มีโอกาส 10% ที่จะไม่ตายเมื่อโดนดาเมจถึงตาย\r\n" + enter;
                msg += "- Illium : ดาเมจเพิ่มขึ้นเมื่อเคลื่อนที่ในระยะทางที่กำหนดภายในเวลา\r\n- Cadena : ดาเมจเพิ่มขึ้น 6% เมื่อโจมตีมอนสเตอร์ที่มีเลเวลต่ำกว่าตัวละคร\r\n- Ark : ดาเมจเพิ่มขึ้นเมื่อต่อสู้ต่อเนื่อง\r\n" + enter;
                msg += "- Angelic Buster : เมื่อใช้ ดาเมจเพิ่มขึ้น 90% เป็นเวลา 10 วินาที\r\n- Kaiser : Max HP เพิ่มขึ้น 15%\r\n\r\n- Cannon Shooter : All Stat 25, Max HP 10%, Max MP 10%\r\n- Zero : ได้รับดาเมจลดลง 15%, เจาะเกราะ 10%\r\n- Kinesis : Critical Damage เพิ่มขึ้น 4%" + enter;
                msg += "- Cygnus Knights : ต้านทานสถานะเพิ่มขึ้น\r\n- Mihile : Stance 100% เป็นเวลา 110 วินาที\r\n\r\n- Resistance : เป็นอมตะ 8 วินาทีเมื่อคืนชีพ\r\n- Demon Slayer : โจมตีบอสแรงขึ้น 15%\r\n- Demon Avenger : ดาเมจเพิ่มขึ้น 10%\r\n- Xenon : All Stat 10%\r\n" + enter;
                msg += "- Pathfinder : กำลังเตรียมคำอธิบาย\r\n- Hoyoung : กำลังเตรียมคำอธิบาย" + enter;
                cm.sendSimple(msg);
                break;

            case 5:
                cm.resetStats(4, 4, 4, 4);
                cm.dispose();
                break;
            case 6:
                cm.dispose();
                cm.openNpc(1530330);
                break;
            case 7:
                Packages.scripting.newscripting.ScriptManager.runScript(cm.getPlayer().getClient(), "characterNameChange", null);
                cm.dispose();
                break;
            case 8:
                cm.dispose();
                cm.openNpc(9010106);
                break;
            case 13:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 1540432, "MechanicCamouflage");
                break;
            case 88:
                cm.dispose();
                cm.openNpc(2020001);
                break;
            case 89:
                cm.dispose();
                cm.openNpc(9201459);
                break;
            case 98:
                cm.dispose();
                cm.openNpc(9000302);
                break;
            case 99:
                cm.dispose();
                cm.openNpc(9001110);
                break;
            case 9: // Stat Reset
                //cm.resetStats(4, 4, 4, 4);
                cm.getPlayer().statReset();
                cm.sendOkS("#fs11##b#eรีเซ็ตค่าสถานะเรียบร้อยแล้ว", 2);
                cm.dispose();
                break;
            case 10:
                cm.dispose();
                cm.sendOkS("กำลังเตรียมการ", 0x24);
                break;
            case 11:
                cm.dispose();
                cm.openNpc(1540945);
                break;
            case 12:
                cm.dispose();
                cm.openNpc(1052208);
                break;
            case 13:
                cm.dispose();
                cm.openNpc(1530110);
                break;
            case 14:
                cm.dispose();
                cm.openShop(9001212);
                break;
            case 15:
                cm.dispose();
                cm.openNpc(9001205);
                break;
            case 16:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 9000213, "change_job");
                break;
            case 17:
                cm.dispose();
                cm.openNpc(1540101);
                break;
            case 18:
                cm.dispose();
                cm.openNpc(3003362);
                break;
            case 19:
                cm.dispose();
                cm.openShop(17);
                break;
            case 20:
                cm.dispose();
                cm.openNpc(1012121);
                break;
            case 21:
                cm.dispose();
                cm.warp(200000301);
                break;
            case 22:
                cm.dispose();
                cm.openNpc(3003429);
                break;
            case 23:
                cm.dispose();
                cm.openNpc(3002000);
                break;
            case 24:
                cm.dispose();
                cm.openNpc(1002005);
                break;
            case 245:
                cm.dispose();
                cm.openNpc(9000224);
                break;
            case 246:
            case 247:
                Packages.scripting.newscripting.ScriptManager.runScript(cm.getPlayer().getClient(), "levelUP", null);
                cm.dispose();
                break;
            case 248:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 9000213, 9076004);
                break;
            case 25:
                cm.dispose();
                cm.openNpc(1052014);
                break;
            case 26:
                cm.dispose();
                ScriptManager.runScript(cm.getClient(), "recommend", null)
                break;
            case 27:
                msg = "#fs11#" + 검은색 + "โปรดเลือกสกิลพิเศษที่ต้องการเรียนรู้\r\n";
                msg += 색 + "(สกิลพิเศษจะถูกบันทึกในสกิลคลาส 0)\r\n\r\n";

                msg += "#L1##fc0xFFFF3636##s80001829# Shinsoo's Blessing#l\r\n";
                msg += "#L2##fc0xFFFF3636##s80001825# Iaijutsu#l";
                cm.sendSimple(msg);
                break;
            case 555: // Donation Shop
                cm.dispose();
                cm.openNpc(1530050);
                break;
            case 556: // Promotion Shop
                cm.dispose();
                cm.openNpc(1530051);
                break;
            case 777: // Gachapon
                cm.dispose();
                cm.openNpc(1530056);
                break;
            case 778: // Storage
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 9010009, "ParcelService");
                break;
            case 779: // Phantom
                if (!GameConstants.isPhantom(cm.getPlayer().getJob())) {
                    cm.sendOk("#fs11#สำหรับอาชีพ Phantom เท่านั้น");
                    cm.dispose();
                    return;
                }
                msg = "#fs11#" + 검은색 + "โปรดเลือกระบบที่ต้องการ\r\n";
                msg += "#L1##fc0xFFFF3636##s20031207# Steal Skill System#l\r\n";
                msg += "#L2##fc0xFFFF3636##s20031208# Steal Skill Reset System#l";
                cm.sendSimple(msg);
                break;
            case 780: // Shade
                if (!GameConstants.isEunWol(cm.getPlayer().getJob())) {
                    cm.sendOk("#fs11#สำหรับอาชีพ Shade เท่านั้น");
                    cm.dispose();
                    return;
                }
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 9000213, "ShadeErrorFix");
                break;
        }
    } else if (status == 2) {
        switch (seld) {
            case 1:
                switch (sel) {
                    case 1:
                        cm.dispose();
                        cm.openNpc(9010106);
                        break;
                    case 2:
                        cm.dispose();
                        cm.openNpc(9010107);
                        break;
                    case 3:
                        cm.dispose();
                        cm.openNpc(3003162);
                        break;
                    case 4:
                        cm.dispose();
                        cm.openNpc(3003252);
                        break;
                    case 5:
                        cm.dispose();
                        cm.openNpc(3003480);
                        break;
                    case 6:
                        cm.dispose();
                        cm.openNpc(3003756);
                        break;
                }
                break;
            case 2:
                switch (sel) {
                    case 1:
                        cm.dispose();
                        cm.openNpc(2155000);
                        break;
                    case 2:
                        cm.dispose();
                        cm.openNpc(3003104);
                        break;
                    case 3:
                        cm.dispose();
                        cm.openNpc(3003162);
                        break;
                    case 4:
                        cm.dispose();
                        cm.openNpc(3003252);
                        break;
                    case 5:
                        cm.dispose();
                        cm.openNpc(3003480);
                        break;
                    case 6:
                        cm.dispose();
                        cm.openNpc(3003756);
                        break;
                    case 9:
                        cm.dispose();
                        cm.openNpc(3003151);
                        break;
                    case 8:
                        cm.dispose();
                        cm.openNpc(3003381);
                        break;
                    case 10:
                        cm.dispose();
                        cm.warp(450004000, 0);
                        break;
                }
                break;
            case 3:
                switch (sel) {
                    case 1:
                        cm.dispose();
                        cm.openNpc(2008);
                        break;
                    case 2:
                        cm.dispose();
                        cm.openNpc(2008);
                        break;
                    case 3:
                        cm.dispose();
                        cm.openNpc(2008);
                        break;
                    case 4:
                        cm.dispose();
                        cm.openNpc(2008);
                        break;
                    case 5:
                        cm.dispose();
                        cm.openNpc(2008);
                        break;
                }
                break;
            case 4:
                switch (sel) {
                    case 1:
                        cm.dispose();
                        cm.openNpc(2008);
                        break;
                    case 2:
                        cm.dispose();
                        cm.openNpc(2008);
                        break;
                    case 3:
                        cm.dispose();
                        cm.openNpc(2008);
                        break;
                    case 4:
                        cm.dispose();
                        cm.openNpc(2008);
                        break;
                    case 5:
                        cm.dispose();
                        cm.openNpc(2008);
                        break;
                }
                break;
            case 5:
                switch (sel) {
                    case 1:
                        cm.dispose();
                        cm.openNpc("guild_proc");
                        break;
                    case 2:
                        cm.dispose();
                        cm.openNpc(2010009);
                        break;
                    case 3:
                        cm.dispose();
                        cm.openNpc(2008);
                        break;
                    case 4:
                        cm.dispose();
                        cm.openNpc(2008);
                        break;
                    case 5:
                        cm.dispose();
                        cm.openNpc(2008);
                        break;
                }
                break;
            case 6:
                switch (sel) {
                    case 1:
                        cm.dispose();
                        cm.warp(680000000, 1);
                        break;
                    case 2:
                        cm.dispose();
                        cm.openNpc(1031001);
                        break;
                    case 3:
                        cm.dispose();
                        cm.openNpc(2008);
                        break;
                    case 4:
                        cm.dispose();
                        cm.openNpc(2008);
                        break;
                    case 5:
                        cm.dispose();
                        cm.openNpc(2008);
                        break;
                }
                break;
            case 7:
                switch (sel) {
                    case 1:
                        cm.dispose();
                        cm.warp(910130000, 0);
                        break;
                    case 2:
                        cm.dispose();
                        cm.warp(910530000, 0);
                        break;
                    case 3:
                        cm.dispose();
                        cm.warp(109040001, 0);
                        break;
                    case 4:
                        cm.dispose();
                        cm.warp(100000202, 0);
                        break;
                    case 5:
                        cm.dispose();
                        cm.warp(220000006, 0);
                        break;
                }
                break;
            case 8:
                switch (sel) {
                    case 1:
                        cm.dispose();
                        cm.warp(680000000, 1);
                        break;
                    case 2:
                        cm.dispose();
                        cm.openNpc(1031001);
                        break;
                    case 3:
                        cm.dispose();
                        cm.openNpc(2008);
                        break;
                    case 4:
                        cm.dispose();
                        cm.openNpc(2008);
                        break;
                    case 5:
                        cm.dispose();
                        cm.openNpc(2008);
                        break;
                }
                break;
            case 27:
                switch (sel) {
                    case 1:
                        cm.sendOkS("#fs11##b#eขอบคุณครับ~!", 2);
                        cm.dispose();
                        cm.teachSkill(80001829, 5, 5);
                        break;
                    case 2:
                        cm.sendOkS("#fs11##b#eขอบคุณครับ~!", 2);
                        cm.dispose();
                        cm.teachSkill(80001825, 5, 5);
                        break;
                }
                break;
            case 779:
                switch (sel) {
                    case 1:
                        cm.dispose();
                        cm.openNpcCustom(cm.getClient(), 9000213, "SkillSteal");
                        break;
                    case 2:
                        cm.getPlayer().getStolenSkills().clear();
                        cm.getPlayer().fakeRelog();
                        break;
                }
                break;
        }
    }
}
