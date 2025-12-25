Star = "#fUI/FarmUI.img/objectStatus/star/whole#"
importPackage(Packages.tools.packet);
importPackage(java.lang);
importPackage(Packages.network.game);
var status;
var questcompleted = 0;
user = 0;

function start() {
    status = -1;
    action(1, 1, 0);
}

function action(mode, type, selection) {

    /* questlist
    // Collect Item
    // [itemCode, "mob", quantity, isSecretQuest, level]
    // Reach Level
    // [0, "level", 0, isSecretQuest, level]
    // Collect Meso
    // [0, "meso", amount, isSecretQuest, level]
    // Collect Cash
    // [0, "mpoint", amount, isSecretQuest, level]
    // Create Party
    // [0,"party",partyMemberCount,isSecretQuest,level]
    // Kill Boss
    // [bossName,"boss",numberOfKills,isSecretQuest,level]
    // Concurrent Users >= n
    // [0, "howmany", amount, isSecretQuest, level]
    // Kill Points
    // [0, "kpoint", amount, isSecretQuest, level]
    // Own Item (not taken)
    // [itemCode, "item", quantity, isSecretQuest, level]
    */

    questlist = [
        [4000001, "mob", 150, false, 10, 100010000],
        [4000621, "mob", 150, false, 30, 101030500],
        [4000036, "mob", 150, false, 50, 103020320],
        [4000208, "mob", 250, false, 80, 102040600],
        [4000182, "mob", 250, false, 100, 230040000],
        [4000179, "mob", 250, false, 100, 230040000],
        [4036491, "item", 20, false, 200, -1],
        [4033172, "item", 20, false, 210, 105200000],
        [4001843, "mob", 2, false, 230, 350060300],
        [4001869, "mob", 2, false, 240, 105300303],
        [4001879, "mob", 2, false, 245, 450004000],
        [4031227, "item", 10, false, 250, -1],
        [250, "level", 0, false, 250, -1]
    ];
    /* itemlist
    Regular Item
    [itemCode, "item", quantity, 0]
    Period Item
    [itemCode, "itemPeriod", quantity, period(unit: days)]
    Equipment Item with All Stats and ATK
    [itemCode, "EqpAllStatAtk", allStats, atkMatk]
    */
    itemlist = [
        [[4310237, "item", 100, 0]],
        [[4310237, "item", 200, 0]],
        [[4310237, "item", 300, 0]],
        [[4310266, "item", 100, 0]],
        [[4310266, "item", 200, 0]],
        [[4310266, "item", 300, 0]],
        [[5062010, "item", 200, 0]],
        [[4033172, "item", 20, 0]],
        [[4310237, "item", 350, 0]],
        [[4310237, "item", 350, 0]],
        [[4310266, "item", 100, 0]],
        [[4310266, "item", 100, 0]],
        [[4310266, "item", 1000, 0]]
    ];

    rewardlist = [
        [[4310237, 100]],
        [[4310237, 200]],
        [[4310237, 300]],
        [[4310266, 100]],
        [[4310266, 200]],
        [[4310266, 300]],
        [[5062010, 200]],
        [[4033172, 20]],
        [[4310237, 350]],
        [[4310237, 350]],
        [[4310266, 100]],
        [[4310266, 100]],
        [[4310266, 1000]]
    ];

    if (mode <= 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
        for (i = 0; i < GameServer.getAllInstances().length; i++) {
            user += /*GameServer.getAllInstances().get(i).getPlayerStorage().getAllCharacters().length;*/0;
        }
        msg = "#fc0xFF6600CC##e#fs11#[Growth Diary]#n#k\r\n\r\n";
        msg += "ทำภารกิจ #b#e[Growth Diary]#k#n ทั้งหมด #b" + questlist.length + "#k อย่างเพื่อรับรางวัล\r\n#r#e#fs12#[ คลิกที่ภารกิจเพื่อวาร์ปไปยังแผนที่ ]#k#n#fs11#\r\n";
        msg += "#rรางวัล Growth Diary สามารถรับได้เพียง 1 ครั้งต่อบัญชีเท่านั้น\r\n"
        msg += "#b * รางวัลเมื่อทำครบทั้งหมด: #b#i4310266##z4310266# #r2000 ชิ้น#k\r\n";

        for (i = 0; i < questlist.length; i++) {
            if (cm.getClient().getKeyValue("diary" + i) != null) {
                questcompleted++;
            } else {
                msg += "#fs11##L" + i + "#"
                if (cm.getPlayer().getLevel() >= questlist[i][4]) {
                    msg += "#b"
                } else {
                    msg += "#Cgray#"
                }
                msg += getQuestType(questlist[i][0], questlist[i][1], questlist[i][2], questlist[i][3], questlist[i][4])
                var statusText = isQuestCompletable(questlist[i][0], questlist[i][1], questlist[i][2], questlist[i][4]);
                var displayStatus = statusText == "Can Complete" ? "สำเร็จได้" : (statusText == "In Progress" ? "กำลังดำเนินการ" : "เลเวลไม่ถึง");
                msg += " #d(" + displayStatus + ")#k#l\r\n\r\n";
                for (j = 0; j < rewardlist[i].length; j++)
                    msg += "#fs11#     #fc0xFFF781D8#└─ รางวัล: #i" + rewardlist[i][j][0] + "# #b#z" + rewardlist[i][j][0] + "# " + rewardlist[i][j][1] + " ชิ้น#k\r\n"
            }
        }
        if (cm.getClient().getKeyValue("questallcompleted") == null) {
            msg += "\r\n#L1000# #b#eฉันทำภารกิจทั้งหมดเสร็จแล้ว! #d("
            if (questcompleted == questlist.length || cm.getPlayer().getGMLevel() >= 11) { // GM Test
                msg += "สำเร็จ)#k#l"
            } else {
                msg += "กำลังดำเนินการ)#r\r\n"
                msg += "[เสร็จสิ้น " + questcompleted + " ภารกิจ, กำลังดำเนินการ " + (questlist.length - questcompleted) + " ภารกิจ]"
            }
        }
        cm.sendSimple(msg);
    } else if (status == 1) {
        if (selection == 1000) {
            msg2 = "";
            if (questcompleted == questlist.length || cm.getPlayer().getGMLevel() >= 11) {
                gift = [[4310266, 2000]];
                for (i = 0; i < gift.length; i++) {
                    msg2 += "#i" + gift[i][0] + "# #b#z" + gift[i][0] + "# " + gift[i][1] + " ชิ้น#k\r\n"
                    cm.gainItem(gift[i][0], gift[i][1]);
                }
                cm.getClient().setKeyValue("questallcompleted", 1);
                msg = "คุณทำภารกิจทั้งหมดสำเร็จแล้ว! นี่คือรางวัลของคุณ:\r\n\r\n";
                msg += msg2;
                cm.sendOk(msg);
            } else {
                cm.sendOk("#fs11#คุณยังทำภารกิจไม่ครบทั้งหมด");
                cm.dispose();
            }
        } else {
            st = selection;

            if (st == -1) {
                cm.dispose();
                return;
            }

            qt = questlist[st];
            if (isQuestCompletable(qt[0], qt[1], qt[2], qt[4]) == "Can Complete") {
                for (i = 0; i < itemlist[st].length; i++) {
                    gainItemByType(itemlist[st][i][0], itemlist[st][i][1], itemlist[st][i][2], itemlist[st][i][3])
                }
                gainReqitemByType(qt[0], qt[1], qt[2]);
                cm.getClient().setKeyValue("diary" + st, 1);
                cm.sendOk("#fs11#ภารกิจเสร็จสิ้น! รับรางวัลเรียบร้อยแล้ว~");
            } else {
                mapid = qt[5];
                if (mapid < 0)
                    cm.sendOk("#fs11#เลเวลของคุณยังไม่ถึง หรือคุณยังทำเงื่อนไขภารกิจไม่ครบ!");
                else
                    cm.warp(qt[5]);
            }
        }
        cm.dispose();
    }
}

function getQuestType(qid, qtype, qnum, isSecret, qlevel) {
    if (!isSecret) {
        switch (qtype) {
            case "mob":
                return "#e[Lv." + qlevel + "]#n สะสม #fc0xFFF361A6##z" + qid + "##k #r" + nf(qnum) + " ชิ้น#k";
                break;
            case "level":
                return "#e[Lv." + qlevel + "]#n เก็บเลเวลถึง #r" + qlevel + "#k";
                break;
            case "item":
                return "#e[Lv." + qlevel + "]#n ครอบครอง #fc0xFFF361A6##z" + qid + "##k #r" + nf(qnum) + " ชิ้น#k ขึ้นไป";
                break;
            case "party":
                return "#e[Lv." + qlevel + "]#n เข้าร่วมปาร์ตี้กับสมาชิก #r" + qnum + "#k คนขึ้นไป";
                break;
            case "boss":
                return "#e[Lv." + qlevel + "]#n กำจัด #r" + qid + "#k " + qnum + " ครั้งขึ้นไป";
                break;
            case "meso":
                return "#e[Lv." + qlevel + "]#n ครอบครอง #r" + nf(qnum) + "#k Meso ขึ้นไป";
                break;
            case "mpoint":
                return "#e[Lv." + qlevel + "]#n ครอบครอง #r" + nf(qnum) + "#k Cash Points ขึ้นไป";
                break;
            case "kpoint":
                return "#e[Lv." + qlevel + "]#n ครอบครอง #r" + nf(qnum) + "#k Community Points ขึ้นไป";
                break;
            case "howmany":
                return "#e[Lv." + qlevel + "]#n #k#fc0xFFF361A6#ผู้เล่นออนไลน์ #r" + nf(qnum) + " คนขึ้นไป#k";
                break;
            default:
                return "เกิดข้อผิดพลาด";
                break;
        }
    } else {
        return "[Lv. " + qlevel + "] เงื่อนไขความสำเร็จเป็นความลับ";
    }
}

function isQuestCompletable(qid, qtype, qnum, qlevel) {
    if (cm.getPlayer().getLevel() >= qlevel) {
        switch (qtype) {
            case "mob":
                if (cm.itemQuantity(qid) >= qnum) {
                    return "Can Complete"
                } else {
                    return "In Progress"
                }
                break;
            case "level":
                return "Can Complete"
                break;
            case "item":
                if (cm.itemQuantity(qid) >= qnum) {
                    return "Can Complete"
                } else {
                    return "In Progress"
                }
                break;
            case "party":
                if (cm.getPlayer().getParty() != null && cm.getPartyMembers().size() >= qnum) {
                    return "Can Complete"
                } else {
                    return "In Progress"
                }
                break;
            case "boss":
                if (cm.GetCount(qid, 1) >= qnum) {
                    return "Can Complete"
                } else {
                    return "In Progress"
                }
                break;
            case "meso":
                if (cm.getPlayer().getMeso() >= qnum) {
                    return "Can Complete"
                } else {
                    return "In Progress"
                }
                break;
            case "mpoint":
                if (cm.getPlayer().getCSPoints(1) >= qnum) {
                    return "Can Complete"
                } else {
                    return "In Progress"
                }
                break;
            case "kpoint":
                /*                if (cm.getPlayer().getbounscoin() >= qnum) { // Temporary
                                    return "Can Complete"
                                } else {
                                    return "In Progress"
                                }*/
                break;
            case "howmany":
                if (user >= qnum) {
                    return "Can Complete"
                } else {
                    return "In Progress"
                }
                break;
            default:
                return "Error.";
                break;
        }
    } else {
        return "Level Required"
    }
}

function gainReqitemByType(qid, qtype, qnum) {
    switch (qtype) {
        case "mob":
            cm.gainItem(qid, -qnum);
            break;
        case "meso":
            cm.gainMeso(-qnum);
            break;
        case "mpoint":
            cm.getPlayer().modifyCSPoints(2, -qnum, false);
            break;
        case "kpoint":
            cm.getPlayer().gainbounscoin(-qnum);
            break;
        default:
            break;
    }
}

function gainItemByType(iid, itype, i1, i2) {
    switch (itype) {
        case "item":
            cm.gainItem(iid, i1);
            break;
        case "meso":
            cm.gainMeso(i1);
            break;
        case "itemPeriod":
            cm.gainItemPeriod(iid, i1, i2);
            break;
        case "EqpAllStatAtk":
            break;
        case "Point":
            //cm.getPlayer().AddStarDustCoin(i1);
            break;
        default:
            cm.sendOk("เกิดข้อผิดพลาด");
            cm.dispose();
            break;
    }
}

function nf(paramint) {
    return java.text.NumberFormat.getInstance().format(paramint);
}