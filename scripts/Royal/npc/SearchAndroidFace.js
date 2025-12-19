importPackage(Packages.tools.RandomStream);

var ii = Packages.objects.item.MapleItemInformationProvider.getInstance();
var MapleStat = Packages.client.MapleStat

purple = "#fMap/MapHelper.img/weather/starPlanet/7#";
blue = "#fMap/MapHelper.img/weather/starPlanet/8#";
starBlue = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
starYellow = "#fUI/GuildMark.img/Mark/Pattern/00004001/3#"
starWhite = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
starBrown = "#fUI/GuildMark.img/Mark/Pattern/00004001/5#"
starRed = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
starBlack = "#fUI/GuildMark.img/Mark/Pattern/00004001/16#"
starPurple = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
star = "#fUI/FarmUI.img/objectStatus/star/whole#"
S = "#fUI/CashShop.img/CSEffect/today/0#"
rewardIcon = "#fUI/UIWindow2.img/Quest/quest_info/summary_icon/reward#"
obtainIcon = "#fUI/UIWindow2.img/QuestIcon/4/0#"
color = "#fc0xFF6600CC#"
enter = "\r\n"
enter2 = "\r\n\r\n"

var enterVar = '\r\n';
var reset = '#l#k';
var IS_DEBUGGING = false;
var MIN_SEARCHNAME_LENGTH = 2;
var status = -1;
var chat

var hairs = [];
var faces = [];
var searchedMolding = []

var firstSelection;

function start() {
    initializeMoldingData();

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

    chat = '#fs11#'

    if (status == 0) {
        selection = 1; // 0 Hair 1 Face
        firstSelection = 1; // 0 Hair 1 Face

        chat += 'กรุณาพิมพ์ชื่อ' + (selection == 0 ? 'ทรงผม' : 'ใบหน้า') + 'ที่ต้องการค้นหาสำหรับ Android'
        cm.sendGetText(chat);
    } else if (status == 1) {
        var searchTarget = cm.getText();
        var isHairId = firstSelection == 0;

        if (searchTarget.length >= MIN_SEARCHNAME_LENGTH) {
            if (isHairId) {
                chat += '#fs11##fc0xFFFFFFFF#เราสามารถเปลี่ยนทรงผม Android เป็นสไตล์ใหม่ได้ ถ้าเบื่อลุคเดิมแล้ว ลองเลือกทรงผมที่อยากเปลี่ยนดู'
                searchedMolding = searchHair(searchTarget)
            } else {
                chat += '#fs11##fc0xFFFFFFFF#เราสามารถเปลี่ยนใบหน้า Android เป็นสไตล์ใหม่ได้ ถ้าเบื่อลุคเดิมแล้ว ลองเลือกใบหน้าที่อยากเปลี่ยนดู'
                searchedMolding = searchFace(searchTarget)
            }

            if (searchedMolding.length > 0) {
                cm.askAvatarAndroid(chat, searchedMolding);
            } else {
                cm.sendOk('#fs11# ไม่พบ' + (isHairId ? 'ทรงผม' : 'ใบหน้า') + 'ที่ค้นหา');
                cm.dispose();
            }
        } else {
            chat += 'ตัวอักษรสั้นเกินไป' + enterVar;
            chat += 'กรุณาพิมพ์อย่างน้อย ' + MIN_SEARCHNAME_LENGTH + ' ตัวอักษร'
            cm.sendOk(chat)
        }

    } else if (status == 2) {
        if (selection >= 0) {
            setAvatar(searchedMolding[selection]);
        }
        resetUserInput();
        cm.dispose();
    }

}

function resetUserInput() {
    firstSelection = null;
    searchedMolding = [];
}

function searchHair(searchName) {
    var validHairIds = []
    for (var i = 0; i < hairs.length; i++) {
        var hair = hairs[i];
        var hairName = hair.right
        var hairId = hair.left
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

function searchFace(searchName) {
    var validFaceIds = []
    for (var i = 0; i < faces.length; i++) {
        var face = faces[i];
        var faceId = face.left;
        var faceName = face.right
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

function initializeMoldingData() {
    var it = ii.getAllEquips().iterator();
    while (it.hasNext()) {
        var avatar = it.next();
        var avatarId = avatar.getLeft()

        if (isHair(avatarId)) {
            hairs.push(avatar)
        } else if (isFace(avatarId)) {
            faces.push(avatar)
        }
    }
}

function setAvatar(args) {
    if (isHair(args)) {
        cm.setHairAndroid(args);
    } else if (isFace(args)) {
        cm.setFaceAndroid(args);
    }
    cm.getPlayer().equipChanged();
}

function isHair(itemId) {
    return Math.floor(itemId / 10000) == 3 || Math.floor(itemId / 10000) == 4 || Math.floor(itemId / 10000) == 6;
}

function isFace(itemId) {
    return Math.floor(itemId / 10000) == 2 || Math.floor(itemId / 10000) == 5;
}
