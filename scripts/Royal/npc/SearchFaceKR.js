var itemInformationProvider = Packages.objects.item.MapleItemInformationProvider.getInstance();

var purpleIcon = "#fMap/MapHelper.img/weather/starPlanet/7#";
var blueIcon = "#fMap/MapHelper.img/weather/starPlanet/8#";
var blueStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#";
var yellowStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#";
var whiteStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#";
var brownStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#";
var redStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#";
var blackStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#";
var purpleStar = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#";
var starIcon = "#fUI/FarmUI.img/objectStatus/star/whole#";
var sIcon = "#fUI/CashShop.img/CSEffect/today/0#";
var rewardIcon = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#";
var obtainIcon = "#fUI/UIWindow2.img/QuestIcon/4/0#";
var colorCode = "#fc0xFF6600CC#";
var lineBreak = "\r\n";
var doubleLineBreak = "\r\n\r\n";

var enter = '\r\n';
var reset = '#l#k';
var IS_DEBUGGING = false;
var MIN_SEARCHNAME_LENGTH = 2;
var status = -1;
var chat;

var hairs = [];
var faces = [];
var searchedOptions = [];

var initialSelection;

function start() {
    //if(!cm.getPlayer().isGM()) return;
    loadMoldingData();

    dressUp = 0;
    checkAngelicBuster = false;

    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    action(mode, type, selection, 0);
}

function action(mode, type, selection, dressUp_) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (cm.getPlayer().getJob() >= 6500 && cm.getPlayer().getJob() <= 6512) {
            if (!checkAngelicBuster) {
                cm.askAngelicBuster();
                checkAngelicBuster = true;
                return;
            }

            if (dressUp_ > 0) {
                dressUp = 1;
            }
        }
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;

        chat = '#fs11#';

        if (status == 0) {
            selection = 1; // 0 Hair 1 Face
            initialSelection = 1; // 0 Hair 1 Face

            chat += 'กรุณาพิมพ์ชื่อ ' + (selection == 0 ? 'ทรงผม' : 'ใบหน้า') + ' ที่เธอต้องการจะค้นหาจ้ะ';
            cm.sendGetText(chat);
        } else if (status == 1) {
            var searchQuery = cm.getText();
            var isHairOption = initialSelection == 0;

            if (searchQuery.length >= MIN_SEARCHNAME_LENGTH) {
                if (isHairOption) { // Hair
                    chat += '#fs11##fc0xFFFFFFFF#ฉันสามารถเปลี่ยนทรงผมของเธอให้กลายเป็นสไตล์ใหม่ที่ไฉไลกว่าเดิมได้นะ! ถ้าเธอเริ่มเบื่อสไตล์เดิมๆ แล้ว ลองเลือกทรงผมที่ชอบดูสิจ๊ะ ไม่ต้องรีบร้อนจ้ะ';
                    searchedOptions = performHairSearch(searchQuery);
                } else { // Face
                    chat += '#fs11##fc0xFFFFFFFF#ฉันสามารถเปลี่ยนใบหน้าของเธอให้กลายเป็นสไตล์ใหม่ที่ไฉไลกว่าเดิมได้นะ! ถ้าเธอเริ่มเบื่อสไตล์เดิมๆ แล้ว ลองเลือกใบหน้าที่ชอบดูสิจ๊ะ ไม่ต้องรีบร้อนจ้ะ';
                    searchedOptions = performFaceSearch(searchQuery);
                }

                if (searchedOptions.length > 0) {
                    cm.sendStyle(chat, dressUp, searchedOptions);
                } else {
                    cm.sendOk('#fs11# อ๊ะ ไม่พบ ' + (isHairOption ? 'ทรงผม' : 'ใบหน้า') + ' ที่เธอค้นหาจ้ะ');
                    cm.dispose();
                }
            } else {
                chat += 'ตัวอักษรที่พิมพ์มาสั้นเกินไปนะจ๊ะ' + enter;
                chat += 'รบกวนเธอช่วยพิมพ์อย่างน้อย ' + MIN_SEARCHNAME_LENGTH + ' ตัวอักษรขึ้นไปนะจ๊ะ';
                cm.sendOk(chat);
            }
        } else if (status == 2) {
            if (selection >= 0) {
                applyAvatarOption(searchedOptions[selection]);
            }
            resetUserInput();
            cm.dispose();
        }
    }
}

function resetUserInput() {
    initialSelection = null;
    searchedOptions = [];
}

function performHairSearch(searchName) {
    var validHairIds = [];
    for (var i = 0; i < hairs.length; i++) {
        var hair = hairs[i];
        var hairName = hair.right;
        var hairId = hair.left;
        if (hairId % 10 == 0) {
            if (hairName.indexOf(searchName) != -1) {
                validHairIds.push(hairId);
            } else if (hairName.indexOf(searchName[0]) != -1) {
                var index = 0;
                var isShortName = true;
                while (searchName[index] != null) {
                    var slicedSearchName = searchName[index];

                    if (hairName.indexOf(slicedSearchName) == -1) {
                        isShortName = false;
                        break;
                    }
                    index++;
                }
                if (isShortName) {
                    validHairIds.push(hairId);
                }
            }
        }

    }
    return validHairIds;
}

function performFaceSearch(searchName) {
    var validFaceIds = [];
    for (var i = 0; i < faces.length; i++) {
        var face = faces[i];
        var faceId = face.left;
        var faceName = face.right;
        if (faceName.indexOf(searchName) != -1) {

            if (Math.floor(faceId / 100 % 10) == 0) {
                validFaceIds.push(faceId);
            }
        } else if (faceName.indexOf(searchName[0]) != -1) {
            var index = 0;
            var isShortName = true;
            while (searchName[index] != null) {
                var slicedSearchName = searchName[index];

                if (faceName.indexOf(slicedSearchName) == -1) {
                    isShortName = false;
                    break;
                }
                index++;
            }
            if (isShortName) {
                validFaceIds.push(faceId);
            }
        }
    }
    return validFaceIds;
}

function loadMoldingData() {
    var it = itemInformationProvider.getAllEquips().iterator();
    while (it.hasNext()) {
        var avatar = it.next();
        var avatarId = avatar.getLeft();

        if (isHair(avatarId)) {
            hairs.push(avatar);
        } else if (isFace(avatarId)) {
            faces.push(avatar);
        }
    }
}

function applyAvatarOption(args) {
    if (isHair(args)) {
        if (Packages.constants.GameConstants.isZero(cm.getPlayer().getJob()) && cm.getPlayer().getZeroInfo().isBeta()) { // Zero - Beta
            cm.getPlayer().getZeroInfo().setSubHair(args);
            cm.getPlayer().getZeroInfo().sendUpdateZeroInfo(cm.getPlayer(), Packages.objects.users.looks.zero.ZeroInfoFlag.SubHair);
            cm.getPlayer().equipChanged();
            return;
        }
        if (dressUp == 1) { // Angelic Buster Dress Up
            cm.getPlayer().setSecondHair(args);
            cm.getPlayer().fakeRelog();
            cm.getPlayer().equipChanged();
            return;
        }
        cm.setHair(args);
    } else if (isFace(args)) {
        if (Packages.constants.GameConstants.isZero(cm.getPlayer().getJob()) && cm.getPlayer().getZeroInfo().isBeta()) { // Zero - Beta
            cm.getPlayer().getZeroInfo().setSubFace(args);
            cm.getPlayer().getZeroInfo().sendUpdateZeroInfo(cm.getPlayer(), Packages.objects.users.looks.zero.ZeroInfoFlag.SubFace);
            cm.getPlayer().equipChanged();
            return;
        }
        if (dressUp == 1) { // Angelic Buster Dress Up
            cm.getPlayer().setSecondFace(args);
            cm.getPlayer().fakeRelog();
            cm.getPlayer().equipChanged();
            return;
        }
        cm.setFace(args);
    }
}

function isHair(itemId) {
    return Math.floor(itemId / 10000) == 3 || Math.floor(itemId / 10000) == 4 || Math.floor(itemId / 10000) == 6;
}

function isFace(itemId) {
    return Math.floor(itemId / 10000) == 2 || Math.floor(itemId / 10000) == 5;
}




