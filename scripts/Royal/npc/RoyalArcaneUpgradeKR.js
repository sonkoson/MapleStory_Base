var lineBreak = "\r\n";
var status = -1;
var selectedItemIndex = -1;

var itemList = [{
    'id': 2439960,
    'quantity': 1,
    'allStat': 0,
    'attack': 0,
    'recipes': [[4310218, 30], [4310237, 3000], [4031227, 200], [4001715, 200]],
    'price': 0,
    'successChance': 80,
    'failRewards': [[4001715, 10]]
},
{
    'id': 2439961,
    'quantity': 1,
    'allStat': 0,
    'attack': 0,
    'recipes': [[4310218, 50], [4310237, 5000], [4031227, 400], [4001715, 300]],
    'price': 0,
    'successChance': 70,
    'failRewards': [[4001715, 10]]
},
{
    'id': 2439962,
    'quantity': 1,
    'allStat': 0,
    'attack': 0,
    'recipes': [[4310218, 60], [4310237, 10000], [4031227, 600], [4001715, 500]],
    'price': 0,
    'successChance': 60,
    'failRewards': [[4001715, 10]]
},
{
    'id': 2439961,
    'quantity': 1,
    'allStat': 0,
    'attack': 0,
    'recipes': [[4310218, 100], [4310237, 10000], [4031227, 800], [4001715, 600]],
    'price': 0,
    'successChance': 100,
    'failRewards': [[2439961, 1]]
},
{
    'id': 2439962,
    'quantity': 1,
    'allStat': 0,
    'attack': 0,
    'recipes': [[4310218, 120], [4310237, 20000], [4031227, 1200], [4001715, 1000]],
    'price': 0,
    'successChance': 100,
    'failRewards': [[2439962, 1]]
},
] // All stats and ATT/MATT are only applied to equipment items.

var targetItem;
var isEquipment = false;
var isCraftable = false;

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
        if (cm.getInvSlots(1) < 3 || cm.getInvSlots(2) < 3 || cm.getInvSlots(3) < 3 || cm.getInvSlots(4) < 3 || cm.getInvSlots(5) < 3) {
            cm.sendOkS("#fs11##fc0xFF6600CC#รบกวนเธอช่วยเว้นช่องว่างในกระเป๋าอย่างน้อย 3 ช่องในทุกแท็บด้วยนะจ๊ะ", 2);
            cm.dispose();
            return;
        }
        var dialogue = "#fs11#สวัสดีจ้ะ! เธอต้องการจะเลื่อนระดับอุปกรณ์ ArcaneShade หรือเปล่าจ๊ะ? รบกวนช่วยเลือกไอเท็มที่เธอต้องการสร้างหน่อยนะ" + lineBreak;
        dialogue += "#fs11#ข้อมูลสูตรและรางวัลจะแสดงขึ้นมาหลังจากที่เธอเลือกแล้วจ้ะ#fs12##b" + lineBreak;
        for (var i = 0; i < itemList.length; i++) {
            dialogue += "#fs11##L" + i + "##i" + itemList[i]['id'] + "##z" + itemList[i]['id'] + "# " + itemList[i]['quantity'] + " ชิ้น" + lineBreak;
        }
        cm.sendSimple(dialogue);
    } else if (status == 1) {
        selectedItemIndex = selection;
        targetItem = itemList[selection];
        isEquipment = Math.floor(targetItem['id'] / 1000000) == 1;

        isCraftable = checkRequiredItems(targetItem);

        var displayInfo = "#fs11#ไอเท็มที่เธอเลือกคือรายการนี้จ้ะ#fs11##b" + lineBreak;
        displayInfo += "#fs11#ไอเท็มเป้าหมาย : #i" + targetItem['id'] + "##z" + targetItem['id'] + "# " + targetItem['quantity'] + " ชิ้น" + lineBreak;

        if (isEquipment) {
            if (targetItem['allStat'] > 0)
                displayInfo += "All Stat : +" + targetItem['allStat'] + lineBreak;
            if (targetItem['attack'] > 0)
                displayInfo += "ATT / MATT : +" + targetItem['attack'] + lineBreak;
        }

        displayInfo += lineBreak;
        displayInfo += "#fs11##kนี่คือสูตรผลิตสำหรับสร้างไอเท็มที่เธอเลือกนะ#fs11##d" + lineBreak + lineBreak;

        if (targetItem['recipes'].length > 0) {
            for (var i = 0; i < targetItem['recipes'].length; i++) {
                displayInfo += "#b#i" + targetItem['recipes'][i][0] + "##z" + targetItem['recipes'][i][0] + "# " + targetItem['recipes'][i][1] + " ชิ้น #r/ #c" + targetItem['recipes'][i][0] + "# ชิ้นที่เธอมีอยู่#k" + lineBreak;
            }
        }

        if (targetItem['price'] == 0) {
            displayInfo += lineBreak + "#fs11##eโอกาสสร้างสำเร็จ : " + targetItem['successChance'] + "%#n" + lineBreak + lineBreak;
        }

        displayInfo += "#kถ้าสร้างล้มเหลว เธอจะได้รับไอเท็มเหล่านี้เป็นการตอบแทนนะจ๊ะ#fs11##d" + lineBreak + lineBreak;
        if (targetItem['failRewards'].length > 0) {
            for (var i = 0; i < targetItem['failRewards'].length; i++) {
                displayInfo += "#i" + targetItem['failRewards'][i][0] + "##z" + targetItem['failRewards'][i][0] + "# " + targetItem['failRewards'][i][1] + " ชิ้น" + lineBreak;
            }
        }
        displayInfo += "#fs11#" + lineBreak;
        displayInfo += isCraftable ? "#bวัตถุดิบในการสร้างไอเท็มที่เธอเลือกครบแล้วจ้ะ! " + lineBreak + "ถ้าเธอพร้อมจะสร้างแล้ว กด 'ตกลง' ได้เลยนะจ๊ะ" : "#rอ๊ะ ดูเหมือนวัตถุดิบในการสร้างไอเท็มของเธอยังไม่พอสินะจ๊ะ";

        if (isCraftable) {
            cm.sendYesNo(displayInfo);
        } else {
            cm.sendOk(displayInfo);
            cm.dispose();
        }

    } else if (status == 2) {
        isCraftable = checkRequiredItems(targetItem);

        if (!isCraftable) {
            cm.sendOk("อ๊ะ รบกวนเธอช่วยเช็ควัตถุดิบอีกรอบนะจ๊ะว่าครบไหม");
            cm.dispose();
            return;
        }
        consumeRequiredItems(targetItem);
        if (Packages.objects.utils.Randomizer.rand(1, 100) <= targetItem['successChance']) {
            obtainCraftedItem(targetItem);
            cm.sendOk("#fs11#ยินดีด้วยนะจ๊ะ! เธอสร้างไอเท็มสำเร็จแล้วจ้ะ! ลองเช็คในช่องเก็บของดูนะ");
            cm.worldGMMessage(21, "[Royal Arcane] เธอคนเก่ง " + cm.getPlayer().getName() + " ได้สร้างไอเท็ม [" + cm.getItemName(targetItem['id']) + "] สำเร็จแล้วจ้า!");
        } else {
            cm.sendOk("#fs11#โถ่... เสียใจด้วยนะจ๊ะ เธอสร้างไอเท็มล้มเหลว... ไม่เป็นไรนะ ลองใหม่อีกครั้งนะจ๊ะ");
            cm.worldGMMessage(21, "[Royal Arcane] น่าเสียดายจัง คุณ " + cm.getPlayer().getName() + " สร้างไอเท็ม [" + cm.getItemName(targetItem['id']) + "] ล้มเหลวไปจ้ะ...");
            obtainFailureRewards(targetItem);
        }
        cm.dispose();
    }
}

function checkRequiredItems(target) {
    var recipeList = target['recipes'];
    var canCraft = true;

    for (var j = 0; j < recipeList.length; j++) {
        if (!cm.haveItem(recipeList[j][0], recipeList[j][1])) {
            canCraft = false;
            break;
        }
    }
    if (canCraft) {
        canCraft = cm.getPlayer().getMeso() >= target['price'];
    }

    return canCraft;
}

function consumeRequiredItems(target) {
    var recipeList = target['recipes'];
    for (var j = 0; j < recipeList.length; j++) {
        if (Math.floor(recipeList[j][0] / 1000000) == 1) {
            Packages.objects.item.MapleInventoryManipulator.removeById(cm.getClient(), Packages.objects.item.MapleInventoryType.EQUIP, recipeList[j][0], 1, false, false);
        } else {
            cm.gainItem(recipeList[j][0], -recipeList[j][1]);
        }
    }
}

function obtainCraftedItem(target) {
    var isEquipFlag = Math.floor(target['id'] / 1000000) == 1;
    if (isEquipFlag) {
        var newItem = Packages.objects.item.MapleItemInformationProvider.getInstance().getEquipById(target['id']);
        if (target['allStat'] > 0) {
            newItem.setStr(target['allStat']);
            newItem.setDex(target['allStat']);
            newItem.setInt(target['allStat']);
            newItem.setLuk(target['allStat']);
        }
        if (target['attack'] > 0) {
            newItem.setWatk(target['attack']);
            newItem.setMatk(target['attack']);
        }
        Packages.objects.item.MapleInventoryManipulator.addFromDrop(cm.getClient(), newItem, false);
    } else {
        cm.gainItem(target['id'], target['quantity']);
    }
}

function obtainFailureRewards(target) {
    var failRewardList = target['failRewards'];

    for (var j = 0; j < failRewardList.length; j++) {
        cm.gainItem(failRewardList[j][0], failRewardList[j][1]);
    }
}






