/*
Royal SkillSteal Ver.366
*/

importPackage(Packages.constants);
importPackage(Packages.client);
importPackage(Packages.client.skills);
importPackage(Packages.provider);
importPackage(Packages.tools);
importPackage(java.lang);
importPackage(java.io);
importPackage(Packages.objects.users.skills.Packages.objects.users.skills.SkillFactory);
importPackage(Packages.objects);

var GameConstants = Packages.constants.GameConstants;

var enter = '\r\n';
var reset = '#l#k';
var IS_DEBUGGING = false;

var status = -1;
var selectedStealSkillSlot;
var selectedJobIndex;
var selectedSkillIndex;
var selectedSkills;
var chat
var jobList = [];

var Job = function (name, id) {
    this.name = name
    this.id = id;
}

var stealSkills = [];
stealSkills[0] = [
    1001005, //Swordsman
    2001008, 2001002, //Magician
    3001004, //Archer
    3011004, //Pathfinder
    4001334, 4001344, 4001003, // Rogue
    4301004, //Semi Dualer
    5001002, 5001003, //Pirate
    5011000, 5011001 //Cannoneer    
];

stealSkills[1] = [
    1101011, 1101006, //Fighter
    1201013, 1201015, //Page
    1301012, 1301007, //Spearman
    2301005, 2301002, //Cleric
    2101004, 2101005, 2101001, 2101010, //Fire/Poison
    2201008, 2201005, 2201001, //Ice/Lightning
    3101005, //Hunter
    3201011, //Crossbowman
    3301003, //Pathfinder
    4101010, 4101013, //Assassin
    4201012, //Thief
    4311002, 4311003, //Dualer
    5201001, 5201018, //Gunslinger
    5101012, //Brawler
    5301000, 5301001, 5301003 //Cannon Shooter
];

stealSkills[2] = [
    1111010, 1111012, //Crusader
    1211010, 1211012, 1211013, 1211014, 1211011, 1211018, //Knight
    1311011, 1311012, 1311015, //Berserker
    2311004, 2311011, 2311012, 2311002, 2311001, 2311003, 2311009, //Priest
    2111002, 2111003, //Fire/Poison
    2211002, 2211014, //Ice/Lightning
    3111013, //Ranger
    3211011, //Sniper
    3311012, //Pathfinder
    4111010, 4111015, //Hermit
    4211011, 4211002, //Chief Bandit
    4321006, 4321002, //Dual Master
    4331000, 4331011, 4331006, //Slasher
    5211008, 5211010, 5211007, //Outlaw
    5111009, 5111007, 5111002, //Buccaneer
    5311000, 5311010, 5311004, 5311005 //Cannon Blaster
];

stealSkills[3] = [
    1121016, //Hero
    1221009, 1221014, 1221011, 1221016, //Paladin
    1321014, 1321012, //Dark Knight
    2321007, 2321008, 2321006, 2321005, //Bishop
    2121006, 2121007, 2121011, //Fire/Poison
    2221006, 2221011, 2221007, 2221012, //Ice/Lightning
    3121020, 3121015, 3121002, //Bowmaster
    3221007, 3221014, 3221002, //Marksman
    3321022, //Pathfinder
    4121013, 4121017, 4121016, 4121015, //Night Lord
    4221014, 4221010, 4221017, //Shadower
    4341002, 4341011, 4341004, 4341009, //Dual Blade
    5221004, 5221016, 5221015, 5221017, 5221013, 5221018, //Captain
    5121007, 5121013, 5121015, 5121010, 5121009, 5121016, //Viper
    5321000, 5321012, 5321001 //Cannon Master
];

stealSkills[4] = [
    1121054, //Hero
    1221054, //Paladin
    1321054, //Dark Knight
    2121054, //Fire/Poison
    2221054, //Ice/Lightning   
    3121054, //Bowmaster 
    3221054, //Marksman
    3321034, //Pathfinder 
    4121054, //Night Lord
    5221054, //Captain
    5121054, //Viper
]


var jobList = [];


function start() {
    //if(!cm.getPlayer().isGM()) return;
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    } else {
        cm.dispose();
        return;
    }

    chat = '#fs11#'
    if (status == 0) {
        chat += 'สวัสดีครับ! ผมเป็นผู้จัดการ Skill Steal ของ Phantom ใน Royal Maple!\r\n\r\n#r※ หากพบสกิลใดที่ไม่สามารถขโมยหรือติดตั้งได้ โปรดแจ้งให้ทราบ'
        cm.sendSimple(chat)
    } else if (status == 1) {
        if (GameConstants.isPhantom(cm.getPlayer().getJob())) {
            for (var i = 0; i < 5; i++) {
                chat += '#L' + i + '#';
                chat += getStealSkillSlotName(i);
                chat += enter;
            }

            cm.sendSimple(chat)
        } else {
            chat += 'ฟังก์ชั่นนี้สำหรับ Phantom เท่านั้น';
            cm.dispose()
            cm.sendOk(chat)
        }

    } else if (status == 2) { //직업출력        
        if (selectedStealSkillSlot == null) {
            selectedStealSkillSlot = selection;
        }

        chat += 'กรุณาเลือกอาชีพที่ต้องการขโมยสกิล' + enter

        //stealSkills에서 해당되는 차수의 스킬을 모두가져와서 
        //skillId에서 jobId을 잘라내서 직업목록을 파싱하는방식
        //직업별로 다 따로하는거보다는 이게 좀더 편해서그럼
        //가져온 목록은 0~해당차수의 스킬 길이만큼 iteration을 돌려서 
        //selection을 만들고 seletedStealSkillSlot은 글로벌 변수니까 
        //굳이 forkedList같은걸 만들지않고 필요할때마다 가져오기로함
        for (var i = 0; i < stealSkills[selectedStealSkillSlot].length; i++) {
            var skillId = stealSkills[selectedStealSkillSlot][i];
            var jobId = Math.floor(skillId / 10000);
            if (jobList[jobList.length - 1] != jobId) {
                chat += '#L' + jobList.length + '#'
                chat += getJobNameById(jobId);
                chat += enter;

                jobList.push(jobId);
            }
        }
        cm.sendSimple(chat)
    } else if (status == 3) { //스킬목록출력
        if (selectedJobIndex == null) {
            selectedJobIndex = selection;
        }

        chat += 'กรุณาเลือกสกิลที่ต้องการขโมย' + enter

        //selectedJobIndex는 한번만 쓰이는데 굳이해야할까? 했는데 언젠가 한번은 쓰겠지뭐
        //걍 셀렉션 한번 래핑하는겸에 일케하기로함        
        var selectedJobId = jobList[selectedJobIndex];
        for (var i = 0; i < stealSkills[selectedStealSkillSlot].length; i++) {
            var skillId = stealSkills[selectedStealSkillSlot][i];
            var jobId = Math.floor(skillId / 10000);
            if (jobId == selectedJobId) {
                chat += '#L' + i + '#';
                chat += '#s' + skillId + '#'; //스킬 이미지
                chat += Packages.objects.users.skills.SkillFactory.getSkillName(skillId); //스킬이름
                chat += enter;
            }
        }
        cm.sendSimple(chat);
    } else if (status == 4) { //스킬넣을 슬롯선택
        if (selectedSkillIndex == null) {
            selectedSkillIndex = selection;
        }
        chat += 'กรุณาเลือกช่องที่จะใส่สกิล' + enter;
        var stolenSkillArray = getStoleanSkillArray();
        //selectedStealSkillSlot값을 업데이트하는 셀렉션은 0부터하는데
        //해당 메서드와 인덱스차이가 1나므로 이렇게함 다른데쓰이는걸 고치는거보단 이게나은듯
        //어레이 인덱스로도 쓰이니까 이게 나은듯

        numstealslot = GameConstants.getNumSteal(selectedStealSkillSlot + 1);

        for (var i = 0; i < numstealslot; i++) {
            var stolenSkill = stolenSkillArray[selectedStealSkillSlot][i]; //.left, .right를 갖고있는 일종의 튜플
            chat += '#L' + i + '#';
            chat += (i + 1) + ' Slot: '
            if (stolenSkill != null) {
                chat += '#s' + stolenSkill.left + '#'; //Image output
                chat += Packages.objects.users.skills.SkillFactory.getSkillName(stolenSkill.left);

                if (stolenSkill.right) {
                    chat += '(Equipped Skill)';
                }

            } else {
                chat += 'Empty';
            }
            chat += enter;

        }

        cm.sendSimple(chat)

    } else if (status == 5) {
        var skill = Packages.objects.users.skills.SkillFactory.getSkill(stealSkills[selectedStealSkillSlot][selectedSkillIndex]);
        var tupleSlotSkill = getStoleanSkillArray()[selectedStealSkillSlot][selection]
        var skillLevel = skill.getMaxLevel()

        if (tupleSlotSkill == null || !tupleSlotSkill.right) {
            var isreplaceSkill = tupleSlotSkill == null ? false : true;

            chat += getStealSkillSlotName(selectedStealSkillSlot)
            chat += ' ในช่องที่ ' + (selection + 1) + enter

            //Dialogue handling
            if (isreplaceSkill) {
                chat += '#s' + tupleSlotSkill.left + '#';
                chat += Packages.objects.users.skills.SkillFactory.getSkillName(tupleSlotSkill.left) + enter
            }

            chat += '#s' + skill.getId() + '#';
            chat += Packages.objects.users.skills.SkillFactory.getSkillName(skill.getId());
            chat += (isreplaceSkill ? ' ถูกเปลี่ยนแล้ว' : ' ถูกเพิ่มแล้ว');

            //Skill addition part
            if (isreplaceSkill) {
                //cm.getPlayer().removeStolenSkill(tupleSlotSkill.left);
                cm.getPlayer().invokeJobMethod("removeStolenSkill", tupleSlotSkill.left);
            }
            cm.getPlayer().addStolenSkill(skill.getId(), skillLevel);
        } else {
            chat += 'ไม่สามารถเปลี่ยนสกิลที่กำลังใช้งานอยู่ได้'
            cm.sendOk(chat)
        }
        //cm.sendOk(chat)
        cm.dispose();
    }

}

function getStoleanSkillArray() {
    var stolenSkills = cm.getPlayer().getStolenSkills();
    var stolenSkillIterator = stolenSkills.iterator();
    var stolenSkillArray = [];

    for (var i = 0; i < 5; i++) {
        stolenSkillArray[i] = [];
    }

    while (stolenSkillIterator.hasNext()) {
        var stolenSkill = stolenSkillIterator.next();
        var skillJobLevel = GameConstants.getJobNumber(stolenSkill.left) - 1;

        stolenSkillArray[skillJobLevel].push(stolenSkill);
    }
    return stolenSkillArray;
}

function getStealSkillSlotName(index) {
    var name = ''
    if (index == 4) {
        name += 'Hyper Skill';
    } else {
        name += (index + 1) + ' Job Steal Skill';
    }
    return name;
}

////////////

function getJobNameById(job) {
    switch (job) {
        case 0:
            return "Beginner";
        case 100:
            return "Swordsman";
        case 110:
            return "Fighter";
        case 111:
            return "Crusader";
        case 112:
            return "Hero";
        case 120:
            return "Page";
        case 121:
            return "Knight";
        case 122:
            return "Paladin";
        case 130:
            return "Spearman";
        case 131:
            return "Berserker";
        case 132:
            return "Dark Knight";
        case 200:
            return "Magician";
        case 210:
            return "Wizard (Fire/Poison)";
        case 211:
            return "Mage (Fire/Poison)";
        case 212:
            return "Arch Mage (Fire/Poison)";
        case 220:
            return "Wizard (Ice/Lightning)";
        case 221:
            return "Mage (Ice/Lightning)";
        case 222:
            return "Arch Mage (Ice/Lightning)";
        case 230:
            return "Cleric";
        case 231:
            return "Priest";
        case 232:
            return "Bishop";
        case 300:
            return "Archer";
        case 310:
            return "Hunter";
        case 311:
            return "Ranger";
        case 312:
            return "Bowmaster";
        case 320:
            return "Crossbowman";
        case 321:
            return "Sniper";
        case 322:
            return "Marksman";
        case 400:
            return "Rogue";
        case 410:
            return "Assassin";
        case 411:
            return "Hermit";
        case 412:
            return "Night Lord";
        case 420:
            return "Thief";
        case 421:
            return "Chief Bandit";
        case 422:
            return "Shadower";
        case 430:
            return "Semi Dualer";
        case 431:
            return "Dualer";
        case 432:
            return "Dual Master";
        case 433:
            return "Slasher";
        case 434:
            return "Dual Blade";
        case 500:
            return "Pirate";
        case 510:
            return "Brawler";
        case 511:
            return "Buccaneer";
        case 512:
            return "Viper";
        case 520:
            return "Gunslinger";
        case 521:
            return "Outlaw";
        case 522:
            return "Captain";
        case 800:
            return "Manager";
        case 900:
            return "GM";
        case 1000:
            return "Noblesse";
        case 1100:
        case 1110:
        case 1111:
        case 1112:
            return "Soul Master";
        case 1200:
        case 1210:
        case 1211:
        case 1212:
            return "Flame Wizard";
        case 1300:
        case 1310:
        case 1311:
        case 1312:
            return "Wind Breaker";
        case 1400:
        case 1410:
        case 1411:
        case 1412:
            return "Night Walker";
        case 1500:
        case 1510:
        case 1511:
        case 1512:
            return "Striker";
        case 2000:
            return "Legend";
        case 2100:
        case 2110:
        case 2111:
        case 2112:
            return "Aran";
        case 2001:
        case 2200:
        case 2210:
        case 2211:
        case 2212:
        case 2213:
        case 2214:
        case 2215:
        case 2216:
        case 2217:
        case 2218:
            return "Evan";
        case 3000:
            return "Citizen";
        case 3200:
        case 3210:
        case 3211:
        case 3212:
            return "Battle Mage";
        case 3300:
        case 3310:
        case 3311:
        case 3312:
            return "Wild Hunter";
        case 3500:
        case 3510:
        case 3511:
        case 3512:
            return "Mechanic";
        case 501:
            return "Pirate (Cannon Shooter)";
        case 530:
            return "Cannon Shooter";
        case 531:
            return "Cannon Blaster";
        case 532:
            return "Cannon Master";
        case 2002:
        case 2300:
        case 2310:
        case 2311:
        case 2312:
            return "Mercedes";
        case 3001:
        case 3100:
        case 3110:
        case 3111:
        case 3112:
            return "Demon Slayer";
        case 2003:
        case 2400:
        case 2410:
        case 2411:
        case 2412:
            return "Phantom";
        case 2004:
        case 2700:
        case 2710:
        case 2711:
        case 2712:
            return "Luminous";
        case 5000:
        case 5100:
        case 5110:
        case 5111:
        case 5112:
            return "Mikhail";
        case 6000:
        case 6100:
        case 6110:
        case 6111:
        case 6112:
            return "Kaiser";
        case 6001:
        case 6500:
        case 6510:
        case 6511:
        case 6512:
            return "Angelic Buster";
        case 3101:
        case 3120:
        case 3121:
        case 3122:
            return "Demon Avenger";
        case 3002:
        case 3600:
        case 3610:
        case 3611:
        case 3612:
            return "Xenon";
        case 10000:
            return "Zero JR";
        case 10100:
            return "Zero 10100";
        case 10110:
            return "Zero 10110";
        case 10111:
            return "Zero 10111";
        case 10112:
            return "Zero";
        case 2005:
            return "???";
        case 2500:
        case 2510:
        case 2511:
        case 2512:
            return "Eunwol";
        case 14000:
        case 14200:
        case 14210:
        case 14211:
        case 14212:
            return "Kinesis";
        case 15000:
        case 15200:
        case 15210:
        case 15211:
        case 15212:
            return "Illium";
        case 15001:
        case 15500:
        case 15510:
        case 15511:
        case 15512:
            return "Ark";

        case 301:
        case 330:
        case 331:
        case 332:
            return "Pathfinder";
        case 16000:
        case 16400:
        case 16410:
        case 16411:
        case 16412:
            return "Hoyoung";

        case 15002:
        case 15100:
        case 15110:
        case 15111:
        case 15112:
            return "Adele";

        default:
            return "Unknown";
    }
}