importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);
importPackage(java.text);
importPackage(java.awt);

importPackage(Packages.network.models);
importPackage(Packages.scripting);
importPackage(Packages.constants);
importPackage(Packages.database);

var status = -1;
var jobCode = 0;
var secondJob = 0;
var adventure = false;
var type_ = -1;
var isenglish = false;


importPackage(Packages.constants);

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }
    if (status == 0) {
        if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getJob() != 10112) {
            cm.dispose();
            cm.warp(ServerConstants.TownMap, 0);
            return;
        }

        con = DBConnection.getConnection();
        ps = con.prepareStatement("SELECT phonenumber FROM accounts WHERE id = '" + cm.getPlayer().getAccountID() + "'");
        rs = ps.executeQuery();
        while (rs.next()) {
            phonenumber = rs.getString("phonenumber");
        }
        rs.close();
        ps.close();
        con.close();

        if (phonenumber == "010-7777-7777") {
            isenglish = true;
            status = 0;
            action(1, 0, 0);
            return;
        }

        var text = "#fUI/Basic.img/RoyalBtn/StartImg/0#";

        cm.sendGetText(text);
    } else if (status == 1) {
        var text = cm.getText();

        if (text != "I Agree" && !isenglish) {
            cm.dispose();
            cm.sendOk("#fs11#หากคุณไม่ยอมรับ ฉันคงช่วยอะไรไม่ได้\r\nถ้า #bยอมรับ#k กรุณาพิมพ์ว่า '#r#eI Agree#k#n' อีกครั้ง");
            return;
        }

        var v0 = "คุณ #b#h0##k, กรุณาเลือกอาชีพที่ต้องการด้านล่าง#n\r\n\r\n";
        var job = cm.getPlayer().getJob();
        if (job == 0) { // Adventurer
            if (cm.getPlayer().getSubcategory() == 1) { // Dual Blade
                v0 += "#b#L400#Dual Blade#k#l\r\n";
            } else if (cm.getPlayer().getSubcategory() == 2) { // Cannoneer
                v0 += "#b#L501#Cannoneer#k#l\r\n";
            } else if (cm.getPlayer().getSubcategory() == 3) { // Pathfinder
                v0 += "#b#L301#Pathfinder#k#l\r\n";
            } else {
                v0 += "#b#L100#Warrior#k#l\r\n";
                v0 += "#b#L200#Magician#k#l\r\n";
                v0 += "#b#L300#Archer#k#l\r\n";
                v0 += "#b#L400#Thief#k#l\r\n";
                v0 += "#b#L500#Pirate#k#l\r\n";
            }
        } else if (job == 1000) { // Cygnus
            v0 += "#b#L1100#Dawn Warrior#k#l\r\n";
            v0 += "#b#L1200#Blaze Wizard#k#l\r\n";
            v0 += "#b#L1300#Wind Archer#k#l\r\n";
            v0 += "#b#L1400#Night Walker#k#l\r\n";
            v0 += "#b#L1500#Thunder Breaker#k#l\r\n";
        } else if (job == 5000) { // Mikhail
            v0 += "#b#L5100#Mikhail#k#l\r\n";
        } else if (job == 2000) { // Aran
            v0 += "#b#L2100#Aran#k#l\r\n";
        } else if (job == 2001) { // Evan
            v0 += "#b#L2200#Evan#k#l\r\n";
        } else if (job == 2002) { // Mercedes
            v0 += "#b#L2300#Mercedes#k#l\r\n";
        } else if (job == 2003) { // Phantom
            v0 += "#b#L2400#Phantom#k#l\r\n";
        } else if (job == 2004) { // Luminous
            v0 += "#b#L2700#Luminous#k#l\r\n";
        } else if (job == 2005) { // Shade
            v0 += "#b#L2500#Shade#k#l\r\n";
        } else if (job == 3000) { // Resistance
            v0 += "#b#L3200#Battle Mage#k#l\r\n";
            v0 += "#b#L3300#Wild Hunter#k#l\r\n";
            v0 += "#b#L3500#Mechanic#k#l\r\n";
            v0 += "#b#L3700#Blaster#k#l\r\n";
        } else if (job == 3001) { // Demon
            v0 += "#b#L3100#Demon Slayer#k#l\r\n";
            v0 += "#b#L3101#Demon Avenger [Not Recommended]#k#l\r\n";
        } else if (job == 3002) { // Xenon
            v0 += "#b#L3600#Xenon#k#l\r\n";
        } else if (job == 6000) { // Kaiser
            v0 += "#b#L6100#Kaiser#k#l\r\n";
        } else if (job == 6001) { // Angelic Buster
            v0 += "#b#L6500#Angelic Buster#k#l\r\n";
        } else if (job == 6002) { // Cadena
            v0 += "#b#L6400#Cadena#k#l\r\n";
        } else if (job == 10112) { // Zero
            v0 += "#b#L10112#Zero [Not Recommended]#k#l\r\n"
        } else if (job == 14000) { // Kinesis
            v0 += "#b#L14200#Kinesis#k#l\r\n"
        } else if (job == 15000) { // Illium
            v0 += "#b#L15200#Illium#k#l\r\n"
        } else if (job == 15001) { // Ark
            v0 += "#b#L15500#Ark#k#l\r\n"
        } else if (job == 16000) { // Hoyoung
            v0 += "#b#L16400#Hoyoung#k#l\r\n"
        } else if (job == 15002) { // Adele
            v0 += "#b#L15100#Adele#k#l\r\n"
        } else if (job == 6003) { // Kain
            v0 += "#b#L6300#Kain#k#l\r\n";
        } else if (job == 16001) { // Lara
            v0 += "#b#L16200#Lara#k#l\r\n"
        } else if (job == 15003) { // Khali
            v0 += "#b#L15400#Khali#k#l\r\n"
        }
        cm.askMenu(v0, 1, GameObjectType.User, ScriptMessageFlag.NoEsc, ScriptMessageFlag.BigScenario);
    } else if (status == 2) {
        jobCode = selection;
        if (selection == 100 || selection == 200 || selection == 300 || selection == 400 && cm.getPlayer().getSubcategory() != 1 || selection == 500) {
            adventure = true;
            var v0 = "กรุณาเลือกอาชีพคลาส 2 ที่ต้องการ เมื่อเลเวลถึงที่กำหนดจะเปลี่ยนคลาสให้อัตโนมัติ#b\r\n\r\n";
            if (selection == 100) {
                v0 += "#L110#Fighter (4th: Hero)#l\r\n";
                v0 += "#L120#Page (4th: Paladin)#l\r\n";
                v0 += "#L130#Spearman (4th: Dark Knight)#l\r\n";
            } else if (selection == 200) {
                v0 += "#L210#Wizard (Fire/Poison) (4th: Arch Mage F/P)#l\r\n";
                v0 += "#L220#Wizard (Ice/Lightning) (4th: Arch Mage I/L)#l\r\n";
                v0 += "#L230#Cleric (4th: Bishop)#l\r\n";
            } else if (selection == 300) {
                v0 += "#L310#Hunter (4th: Bowmaster)#l\r\n";
                v0 += "#L320#Crossbowman (4th: Marksman)#l\r\n";
            } else if (selection == 400) {
                v0 += "#L410#Assassin (4th: Night Lord)#l\r\n";
                v0 += "#L420#Bandit (4th: Shadower)#l\r\n";
            } else if (selection == 500) {
                v0 += "#L510#Brawler (4th: Buccaneer)#l\r\n";
                v0 += "#L520#Gunslinger (4th: Corsair)#l\r\n";
            }
            cm.sendSimple(v0);
        } else if (selection == 2700) {
            var v0 = "คุณเลือก #eLuminous#n กรุณาเลือกสายที่ต้องการ\r\n\r\n#b#L0##eDark#n#l\r\n#L1##eLight#n#l";
            cm.askMenu(v0, 1, GameObjectType.User, ScriptMessageFlag.NoEsc, ScriptMessageFlag.BigScenario);
        } else {
            changeJob();
            //selectJob(selection);
        }
    } else if (status == 3) {
        if (adventure) {
            secondJob = selection;
            changeJob();
        } else if (jobCode == 2700) {
            type_ = selection;
            changeJob();
            return;
        } else {
            changeJob();
            return;
        }
    } else if (status == 4) {
        changeJob();
    }
}

function selectJob(s) {
    var selectJob = getJobName(s);
    if (cm.getPlayer().getSubcategory() == 1) {
        selectJob = "Dual Blade";
    }
    if (selectJob == "" || s == 800 || s == 900 || s == 910) {
        cm.sendNext("?");
        cm.dispose();
        return;
    }
    var v0 = "คุณเลือกอาชีพ #e" + selectJob + "#n คุณต้องการเปลี่ยนเป็นอาชีพนี้หรือไม่?\r\n หากเลือกแล้ว จะทำการเปลี่ยนอาชีพทันทีพร้อมรับเงินเริ่มต้น\r\n\r\n#L0#ยืนยัน#l\r\n#L1#ขอคิดดูก่อน#l";
    cm.askMenu(v0, 1, GameObjectType.User, ScriptMessageFlag.NoEsc, ScriptMessageFlag.BigScenario);
    //	cm.sendYesNo("선택하신 직업은 #e" + selectJob + "#n입니다. #b예#k 버튼을 누르시면 해당 직업으로 전직되며 초기 자금이 지급됩니다.");
}

function changeJob() {
    cm.getPlayer().changeJob(jobCode);
    cm.getPlayer().maxskill(cm.getPlayer().getJob());

    //cm.gainItem(2431307, 1);
    //cm.gainItem(2432128, 1);
    cm.gainItem(2433444, 1);
    cm.gainItem(3010432, 1);
    cm.gainItem(2000005, 500);

    cm.gainMeso(10000000);
    cm.teachSkill(80001829, 5, 5);
    if (cm.getPlayer().getSkillLevel(80000545) > 0) {
        cm.gainItem(2633552, 1);
    }
    //cm.getPlayer().send(CField.addPopupSay(1540208, 20000, "기본적인 컨텐츠는 #r캐시샵#k을 눌러서 확인 할 수 있으며 게임내 명령어는 채팅창에 #b@도움말#k을 입력하여 확인할 수 있습니다. #b편의 시스템에서 초보자 지원#k, 전구를 통한 #b진:眞 성장 퀘스트#k를 진행하시면 성장에 많은 도움이됩니다. 게임을 이용하면서 어렵거나 궁금한점은 #b홈페이지 초보자 가이드#k를 참고해주세요.", ""));
    //cm.getPlayer().dropMessage(5, "컨텐츠는 ~ 키를 눌러서 확인 할 수 있으며 게임내 명령어는 채팅창에 @도움말을 입력하여 확인할 수 있습니다. 편의 시스템에서 뉴비 지원, 전구를 통한 진:眞 성장 퀘스트를 진행하시면 성장에 많은 도움이됩니다. 게임을 이용하면서 어렵거나 궁금한점은 홈페이지 초보자 가이드를 참고해주세요.")
    if (jobCode == 3600) {
        cm.teachSkill(30021236, 1, 1);
        cm.teachSkill(30021237, 1, 1);
    }
    if (jobCode == 2700) {
        if (type_ == 0) { // 어둠 계열
            cm.teachSkill(27001201, 20, 20);
            cm.teachSkill(27000207, 5, 5);
        } else if (type_ == 1) {
            cm.teachSkill(27001100, 20, 20);
            cm.teachSkill(27000106, 5, 5);
        }
    }
    if (jobCode == 2100) {
        cm.teachSkill(20001295, 1, 1);
    }
    if (jobCode == 16200) {
        cm.teachSkill(160011005, 1, 1);
        cm.gainItem(1354020, 1);
        cm.gainItem(1354021, 1);
        cm.gainItem(1354022, 1);
        cm.gainItem(1354023, 1);
    }
    if (jobCode == 15400) {
        cm.teachSkill(150031074, 1, 1);
        cm.teachSkill(150030079, 1, 1);
        cm.teachSkill(150031005, 1, 1);
    }

    var job_ = 0;
    if (jobCode == 10112) {
        job_ = 10112;
    } else if (jobCode == 501) {
        job_ = 530;
    } else if (jobCode == 301) {
        job_ = 330;
    } else if (jobCode == 3101) {
        job_ = 3120;
    } else if (cm.getPlayer().getSubcategory() == 1) {
        job_ = 430;
    } else {
        if (adventure) {
            job_ = secondJob;
        } else {
            job_ = jobCode + 10;
        }
    }
    cm.getPlayer().updateInfoQuest(122870, "AutoJob=" + job_);
    if (cm.getPlayer().getJob() != 10112) {
        for (var i = cm.getPlayer().getLevel(); i < 200; i++) {
            cm.gainExp(Packages.constants.GameConstants.getExpNeededForLevel(i));
        }
    }
    cm.warp(ServerConstants.TownMap, 0);
    //cm.resetStats(4, 4, 4, 4);
    cm.getPlayer().statReset();
    cm.autoSkillMaster();
    cm.getPlayer().send(Packages.network.models.CField.addPopupSay(1052206, 20000, "ข้อมูลพื้นฐานต่างๆ สามารถดูได้โดยกดปุ่ม #rสีแดง#k ทางขวาของแถบ HP หรือกดปุ่ม #r~#k \r\nคำสั่งในเกมสามารถตรวจสอบได้โดยพิมพ์ #b@help#k ในช่องแชท\r\nหากมีข้อสงสัยหรือปัญหา สามารถดู #rGuide#k ได้ที่ #bDiscord#k หรือถามในห้อง #rQ&A#k", ""));
    cm.dispose();
}

function getJobName(job) {
    selectJob = "";
    if (job == 100) {
        selectJob = "Warrior";
    } else if (job == 200) {
        selectJob = "Magician";
    } else if (job == 300) {
        selectJob = "Archer";
    } else if (job == 301) {
        selectJob = "Pathfinder";
    } else if (job == 400) {
        selectJob = "Thief";
    } else if (job == 500) {
        selectJob = "Pirate";
    } else if (job == 501) {
        selectJob = "Cannoneer";
    } else if (job == 1100) {
        selectJob = "Dawn Warrior";
    } else if (job == 1200) {
        selectJob = "Blaze Wizard";
    } else if (job == 1300) {
        selectJob = "Wind Archer";
    } else if (job == 1400) {
        selectJob = "Night Walker";
    } else if (job == 1500) {
        selectJob = "Thunder Breaker";
    } else if (job == 2100) {
        selectJob = "Aran";
    } else if (job == 2200) {
        selectJob = "Evan";
    } else if (job == 2300) {
        selectJob = "Mercedes";
    } else if (job == 2400) {
        selectJob = "Phantom";
    } else if (job == 2700) {
        selectJob = "Luminous";
    } else if (job == 2500) {
        selectJob = "Shade";
    } else if (job == 3100) {
        selectJob = "Demon Slayer";
    } else if (job == 3101) {
        selectJob = "Demon Avenger";
    } else if (job == 3200) {
        selectJob = "Battle Mage";
    } else if (job == 3300) {
        selectJob = "Wild Hunter";
    } else if (job == 3500) {
        selectJob = "Mechanic";
    } else if (job == 3600) {
        selectJob = "Xenon";
    } else if (job == 3700) {
        selectJob = "Blaster";
    } else if (job == 5100) {
        selectJob = "Mikhail";
    } else if (job == 6100) {
        selectJob = "Kaiser";
    } else if (job == 6500) {
        selectJob = "Angelic Buster";
    } else if (job == 6400) {
        selectJob = "Cadena";
    } else if (job == 10112) {
        selectJob = "Zero";
    } else if (job == 14200) {
        selectJob = "Kinesis";
    } else if (job == 15200) {
        selectJob = "Illium";
    } else if (job == 15500) {
        selectJob = "Ark";
    } else if (job == 16400) {
        selectJob = "Hoyoung";
    } else if (job == 15100) {
        selectJob = "Adele";
    } else if (job == 6300) {
        selectJob = "Kain";
    } else if (job == 16200) {
        selectJob = "Lara";
    } else if (job == 15400) {
        selectJob = "Khali";
    }

    return selectJob;
}