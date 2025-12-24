importPackage(java.lang);

var status = -1;
var s = -1;
var gameType = -1;

var rewards = [
    "ล่านกอินทรี", "เก็บสมบัติ", "ขโมยไข่มังกร", "เต้นจีบสาว"
];

function start() {
    s = -1;
    gameType = -1;
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, sel) {
    if (mode == 0 && sel == -1 && type == 6) {
        cm.getPlayer().removeRandomPortal();
        cm.dispose();
        return;
    }
    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        status--;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
        if (cm.getPlayer().getMapId() == 993000300) {
            var stage = cm.getPlayer().getOneInfoQuestInteger(15142, "Stage");
            if (stage == 0) {
                cm.sendNextNoESC("อุ๊ย... ผ่านด่านแรกไม่ได้เลย โชคไม่ดีหน่อยนะ?");
            } else if (stage == 1) {
                cm.sendNextNoESC("อุ๊ย... พลาดที่ด่านสอง คราวหน้าสู้ต่อนะ!");
            } else if (stage == 2) {
                cm.sendNextNoESC("โอ้... มาถึงด่านสามได้ คราวหน้าต้องผ่านแน่!");
            } else if (stage == 3) {
                cm.sendNextNoESC("ว้าว~ มาถึงด่านสี่ได้ น่าเสียดายจังเลย!");
            } else if (stage == 4) {
                cm.sendNextNoESC("น่าเสียดายจริงๆ... ถ้าผ่านด่านนี้ได้ก็สำเร็จแล้ว คราวหน้าต้องผ่านแน่!");
            } else {
                cm.sendNextNoESC("หรือว่าเจ้าเข้ามาด้วยวิธีที่ไม่ปกติ?");
            }
        } else if (cm.getPlayer().getMapId() == 993000601) {
            gameType = cm.getPlayer().getOneInfoQuestInteger(26022, "gameType");
            var v0 = "สู้มาเหนื่อยแล้ว! เก่งกว่าที่คิดไว้นะ\r\n\r\n#b";
            if (gameType != -1) {
                v0 += "#L0#รับรางวัล " + rewards[gameType] + "#l\r\n";
            }
            v0 += "#L1#ส่งกลับไปที่เดิม#l";
            cm.sendSimple(v0);
        } else {
            if (!cm.getPlayer().CountCheck("random_portal", 20)) {
                cm.sendNext("วันนี้เข้าไม่ได้อีกแล้ว ไว้คราวหน้ามาใหม่นะ");
                cm.dispose();
                return;
            }
            cm.sendNext("หวัดดี! ข้าคือนักล่าค่าหัว #e#bPritto#k#n\r\nกับพี่ชาย #e#rPolo#n#k ลือลั่นไปทั่วแล้ว! ฮ่าๆ!");
        }
    } else if (status == 1) {
        if (cm.getPlayer().getMapId() == 993000300) {
            cm.warp(993000601);
            cm.dispose();
            return;
        } else if (cm.getPlayer().getMapId() == 993000601) {
            if (sel == 0) {
                if (!cm.canHold(2434639, 2)) {
                    cm.sendNext("ช่วยเช็คว่ามีที่ว่างในกระเป๋าก่อนแล้วมาคุยใหม่ได้ไหม?");
                    cm.dispose();
                    return;
                }
                if (gameType == 1) { // Treasure Collection
                    cm.sendNextNoESC("เห็น #b#eฝีมือขโมยสมบัติ#n#k ของข้าไหม?\r\nพอหมาป่าเพลิงกลับมาเห็น #e#rคลังสมบัติว่างเปล่า#k#n จะโมโหแน่ๆ! ฮิๆ!");
                    var score = cm.getPlayer().getOneInfoQuestInteger(26022, "score");
                    var itemID = 0;
                    var quantity = 0;
                    var exp = 0;

                    var avgExp = cm.getPlayer().getOneInfoQuestInteger(26022, "exp");
                    if (score >= 2000) {
                        itemID = 2434635;
                        quantity = 2;
                        exp = parseInt(avgExp) * 6000;
                    } else if (score >= 1000 && score < 2000) {
                        itemID = 2434635;
                        quantity = 1;
                        exp = parseInt(avgExp) * 4000;
                    } else {
                        itemID = 2434634;
                        quantity = 1;
                        exp = parseInt(avgExp) * 2000;
                    }
                    cm.getPlayer().updateOneInfo(26022, "exp", "");
                    cm.getPlayer().updateOneInfo(26022, "score", "");
                    cm.getPlayer().updateOneInfo(26022, "gameType", "-1");
                    cm.gainItem(itemID, quantity);
                    cm.gainExp(exp);
                } else if (gameType == 0) { // Eagle Hunting
                    cm.sendNextNoESC("ฮ่าๆ! #bล่านกอินทรี#k เป็นไงบ้าง?\r\nยิงจากไกลๆ ก็ง่ายดีใช่ไหม?");
                } else if (gameType == 2) { // Dragon Egg
                    cm.sendNextNoESC("ฮู้~ เกือบแล้ว! เกือบโดน #bมังกร#k จับได้!\r\nเห็นเจ้าค่อยๆ ปีนขึ้นไป เจ้าก็มีแววเป็นนักล่าค่าหัวเหมือนข้าเลย ฮ่าๆ!");
                } else if (gameType == 3) { // Courtship Dance
                    cm.sendNextNoESC("เห็น #e#bพรสวรรค์การปลอมตัว#n#k ของข้าไหม?\r\nฮ่าๆ! แม่ไก่ทั้งหลายหลงใหล #e#bท่าเต้นจีบสาว#n#k ของข้าหมดเลย!");
                }
            } else if (sel == 1) {
                cm.getPlayer().updateOneInfo(26022, "exp", "");
                cm.getPlayer().updateOneInfo(26022, "score", "");
                cm.getPlayer().updateOneInfo(26022, "gameType", "-1");
                var map = cm.getPlayer().getOneInfoQuestInteger(26022, "map");
                if (map == 0) {
                    map = 15;
                }
                cm.warp(map);
                cm.dispose();
            }
        } else {
            cm.sendSimple("คนอื่นคิดว่าข้าซุ่มซ่าม แต่ข้ามีฝีมือจริงๆ นะ ว่าไง จะไปผจญภัยกับข้าไหม?\r\n\r\n#b#L0#ตามไปด้วย#l\r\n#L1#ไม่ไป#l");
        }
    } else if (status == 2) {
        if (cm.getPlayer().getMapId() == 993000601) {
            if (gameType == 0) {
                cm.sendNextNoESC("การต่อสู้แบบพี่ไม่ใช่ทางเดียว! นักล่าที่ใช้หัวคิดอย่างข้าต่างหากคือนักล่าตัวจริง!");
            } else if (gameType == 2) {
                var stage = cm.getPlayer().getOneInfoQuestInteger(15142, "Stage");

                var itemID = 0;
                var quantity = 0;
                var exp = 0;
                var avgExp = cm.getPlayer().getOneInfoQuestInteger(26022, "exp");

                if (stage == 5) {
                    itemID = 2434636;
                    quantity = 2;
                    exp = parseInt(avgExp) * 12000;
                } else if (stage >= 3 && stage < 5) {
                    itemID = 2434635;
                    quantity = 1;
                    exp = parseInt(avgExp) * 10000;
                } else if (stage == 2) {
                    itemID = 2434634;
                    quantity = 1;
                    exp = parseInt(avgExp) * 4000;
                } else if (stage <= 1) {
                    itemID = 2434633;
                    quantity = 1;
                    exp = parseInt(avgExp) * 2000;
                }

                if (stage < 5 && stage >= 1) {
                    cm.sendNextNoESC("คราวนี้ปีนขึ้นไปได้ #e#b" + stage + "#n#k ชั้นเลย?\r\nนี่ ส่วนแบ่งของเจ้า #b#i" + itemID + "##z" + itemID + "##k กับ #bEXP#k! ไว้เจอกันใหม่นะ!");
                } else if (stage == 0) {
                    cm.sendNextNoESC("น่าเสียดาย ปีนขึ้นไปไม่ได้สักชั้นเลย?\r\nนี่ ส่วนแบ่งของเจ้า #b#i" + itemID + "##z" + itemID + "##k กับ #bEXP#k! ไว้เจอกันใหม่นะ!");
                } else {
                    cm.sendNextNoESC("ว้าว! เจ้าขโมย #bไข่มังกร#k ได้สำเร็จเลย? เจ๋งมาก! นี่ ส่วนแบ่งของเจ้า #b#i" + itemID + "##z" + itemID + "##k กับ #bEXP#k! ไว้เจอกันใหม่นะ!");
                }

                cm.getPlayer().updateOneInfo(15142, "Stage", "");
                cm.getPlayer().updateOneInfo(26022, "gameType", "-1");
                cm.getPlayer().updateOneInfo(26022, "exp", "");
                cm.gainItem(itemID, quantity);
                cm.gainExp(exp);
            } else if (gameType == 3) {
                cm.sendNextNoESC("ไม่เก่งเท่าข้าแต่เจ้าก็เต้น #e#bท่าจีบสาว#n#k ได้ดีนะ\r\nเห็นทีเจ้าก็เป็นนักล่า #e#bประเภทเดียว#n#k กับข้าแน่ๆ");
            } else {
                var map = cm.getPlayer().getOneInfoQuestInteger(26022, "map");
                if (map == 0) {
                    map = 15;
                }
                cm.warp(map);
                cm.dispose();
            }
        } else {
            if (sel == 0) {
                var portal = cm.getPlayer().getRandomPortal();
                if (portal != null) {
                    var mapID = portal.getMapID();
                    for (var i = 0; i < 20; ++i) {
                        if (cm.getPlayerCount(mapID + i) == 0) {
                            mapID = mapID + i;
                            break;
                        }
                    }
                    cm.warp(mapID, "sp");
                    cm.getPlayer().removeAllSummons();
                    cm.getPlayer().setEnterRandomPortal(true);
                    cm.getPlayer().CountAdd("random_portal");
                }
                cm.dispose();
            } else if (sel == 1) {
                cm.getPlayer().removeRandomPortal();
            }
        }
    } else if (status == 3) {
        if (gameType == 0) {
            var point = cm.getPlayer().getOneInfoQuestInteger(15141, "point");
            var itemID = 0;
            var quantity = 0;
            var exp = 0;
            var avgExp = cm.getPlayer().getOneInfoQuestInteger(26022, "exp");

            if (point >= 1000) {
                itemID = 2434635;
                quantity = 2;
                exp = parseInt(avgExp) * 6000;
            } else if (point >= 750 && point < 1000) {
                itemID = 2434635;
                quantity = 1;
                exp = parseInt(avgExp) * 4000;
            } else {
                itemID = 2434634;
                quantity = 1;
                exp = parseInt(avgExp) * 2000;
            }
            cm.getPlayer().updateOneInfo(15141, "point", "");
            cm.getPlayer().updateOneInfo(26022, "gameType", "-1");
            cm.getPlayer().updateOneInfo(26022, "exp", "");
            cm.sendNextNoESC("การล่าครั้งนี้ได้ #e#b" + point + "#k#n คะแนน?\r\nนี่ ส่วนแบ่งของเจ้า #b#i" + itemID + "##z" + itemID + "##k กับ #bEXP#k! ไว้เจอกันใหม่นะ!");
            cm.gainItem(itemID, quantity);
            cm.gainExp(exp);
        } else if (gameType == 2) {
            var map = cm.getPlayer().getOneInfoQuestInteger(26022, "map");
            if (map == 0) {
                map = 15;
            }
            cm.warp(map);
            cm.dispose();
        } else if (gameType == 3) {
            var point = cm.getPlayer().getOneInfoQuestInteger(15143, "success");
            var itemID = 0;
            var quantity = 0;
            var exp = 0;
            var avgExp = cm.getPlayer().getOneInfoQuestInteger(26022, "exp");

            if (point >= 10) {
                itemID = 2434636;
                quantity = 2;
                exp = parseInt(avgExp) * 10000;
            } else if (point >= 8 && point < 10) {
                itemID = 2434636;
                quantity = 1;
                exp = parseInt(avgExp) * 6000;
            } else if (point >= 4 && point < 8) {
                itemID = 2434635;
                quantity = 2;
                exp = parseInt(avgExp) * 4000;
            } else {
                itemID = 2434635;
                quantity = 1;
                exp = parseInt(avgExp) * 3000;
            }
            cm.getPlayer().updateOneInfo(15141, "point", "");
            cm.getPlayer().updateOneInfo(26022, "gameType", "-1");
            cm.getPlayer().updateOneInfo(26022, "exp", "");

            cm.sendNextNoESC("ท่าจีบสาว#k ครั้งนี้ เจ้าเต้นได้ #e#b" + point + " ท่า#k#n ยอดเยี่ยมเลย!\r\nนี่ ส่วนแบ่งของเจ้า #b#i" + itemID + "##z" + itemID + "##k กับ #bEXP#k! ไว้เจอกันใหม่นะ!");
            cm.gainItem(itemID, quantity);
            cm.gainExp(exp);
        }
    } else if (status == 4) {
        var map = cm.getPlayer().getOneInfoQuestInteger(26022, "map");
        if (map == 0) {
            map = 15;
        }
        cm.warp(map);
        cm.dispose();
    }
}