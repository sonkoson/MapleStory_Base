importPackage(java.lang);
importPackage(Packages.objects.utils);

var status = -1;

var damage = 0;
var killWolf = 0;

function start() {
    damage = 0;
    killWolf = 0;
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, sel) {
    if (mode == 0 && sel == -1 && type == 6) {
        if (cm.getPlayer().getMapId() == 99300600) {
            cm.getPlayer().removeRandomPortal();
        }
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
        if (cm.getPlayer().getMapId() == 993000600) {
            var gameType = cm.getPlayer().getOneInfoQuestInteger(15142, "gameType");

            if (gameType == 8) {
                if (!cm.canHold(2434639, 3)) {
                    cm.sendNext("ช่วยเช็คว่ามีที่ว่างในกระเป๋าก่อนแล้วมาคุยใหม่ได้ไหม?");
                    cm.dispose();
                    return;
                }
                killWolf = cm.getPlayer().getOneInfoQuestInteger(15142, "kill_wolf");
                if (killWolf == 1) {
                    cm.sendNextNoESC("ปราบ #e#rหมาป่าเพลิง#n#k ที่โหดร้ายได้เลย! เก่งมากเลยนะ");
                } else {
                    d = cm.getPlayer().getOneInfoQuest(15142, "wolf_damage");
                    if (d == null || d == "") {
                        d = "0";
                    }
                    damage = Long.parseLong(d);
                    var text = "";
                    if (damage < 1250000000) {
                        text = "พอประมาณ";
                    } else if (damage >= 1250000000 && damage < 12500000000) {
                        text = "มาก";
                    } else if (damage >= 12500000000 && damage < 75000000000) {
                        text = "มหาศาล";
                    } else if (damage >= 75000000000) {
                        text = "ร้ายแรง";
                    }
                    cm.sendNextNoESC("เจ้าสร้างความเสียหาย #e#b" + text + "#n#k ให้หมาป่าเพลิงได้เลย");
                }
            } else {
                cm.sendSimple("เจ้าก็เป็นนักล่าที่เก่งเหมือนกัน... สู้มาเหนื่อยแล้ว\r\n\r\n#b#L0#ส่งกลับไปที่เดิม#l");
            }
        } else {
            var gameType = cm.getPlayer().getOneInfoQuestInteger(15142, "gameType");

            /*if (gameType == 9) {
                cm.sendYesNo("New Year event den is open.");
            } else {*/
            if (gameType == 8) {
                if (!cm.getPlayer().CountCheck("spark_wolf", 5)) {
                    cm.sendNext("วันนี้เข้าไม่ได้อีกแล้ว ไว้คราวหน้ามาใหม่นะ");
                    cm.dispose();
                    return;
                }
            } else {
                if (!cm.getPlayer().CountCheck("random_portal", 20)) {
                    cm.sendNext("วันนี้เข้าไม่ได้อีกแล้ว ไว้คราวหน้ามาใหม่นะ");
                    cm.dispose();
                    return;
                }
            }
            cm.sendNext("ข้าคือ #e#rPolo#n#k นักล่าค่าหัวที่เก่งที่สุดใน Maple World\r\nกับน้องชาย #e#bPritto#n#k กำลังปราบมารร้ายอยู่");
            //}
        }
    } else if (status == 1) {
        if (cm.getPlayer().getMapId() == 993000600) {
            var gameType = cm.getPlayer().getOneInfoQuestInteger(15142, "gameType");
            if (gameType == 8) {
                if (killWolf) {
                    cm.sendNextNoESC("#e#rหมาป่าเพลิง#n#k คือศัตรูที่พี่น้องเราตามล่ามานานมาก...\r\nแน่นอนว่ามันจะกลับมาอีก แต่เพราะเจ้า มันจะปล้นนักเดินทางไม่ได้สักพักใหญ่");
                } else {
                    var reward = 0;
                    var quantity = 0;
                    if (damage < 1250000000) {
                        reward = 2434634;
                        quantity = 1;
                    } else if (damage >= 1250000000 && damage < 12500000000) {
                        reward = 2434634;
                        quantity = 2;
                    } else if (damage >= 12500000000 && damage < 75000000000) {
                        reward = 2434635;
                        quantity = 2;
                    } else if (damage >= 75000000000) {
                        reward = 2434636;
                        quantity = 1;
                    }
                    cm.sendNextNoESC("นี่ #b#i" + reward + "# #z" + reward + "# " + quantity + " ชิ้น#k เป็นของขวัญ งั้นไว้เจอกันคราวหน้านะ!");
                    cm.gainItem(reward, quantity);
                    cm.getPlayer().updateOneInfo(15142, "wolf_damage", "0");
                    cm.getPlayer().updateOneInfo(15142, "kill_wolf", "0");
                }
            } else {
                cm.sendNextNoESC("ฮู้... ล่ามาเหนื่อยเลย\r\nเจ้าฝีมือดีกว่าที่คิดนะ ขอบคุณ");
            }
        } else {
            var portal = cm.getPlayer().getRandomPortal();
            if (portal != null) {
                var gameType = cm.getPlayer().getOneInfoQuestInteger(15142, "gameType");
                if (portal.getType().getType() == 3) {
                    cm.sendSimple("ในที่สุดพี่น้องเราก็พบถ้ำของมอนสเตอร์ที่แข็งแกร่งที่สุด #e#rหมาป่าเพลิง#n#k ที่ตามล่ามานาน มันปล้นนักเดินทางใน Maple World อย่างโหดร้าย... ว่าไง จะไปปราบมันกับข้าไหม?\r\n\r\n#b#L0#ตามไปด้วย#l\r\n#L1#ไม่ไป#l");
                    /*} else if (gameType == 9) {
              var ret = cm.checkEnterRabbit();
                        if (ret == -1) {
                            cm.getPlayer().removeRandomPortal();
                            cm.sendNext("Giant snowman has been defeated.");
                            cm.dispose();
                            return;
                        }
                        cm.getPlayer().removeAllSummons();
                        cm.warpChangeChannel(ret, 910010000);
                        cm.getPlayer().setEnterRandomPortal(true);
                        cm.dispose();*/
                } else {
                    cm.sendSimple("ข้ากำลังจะออกไปล่าพอดี เจ้าจะไปปราบ #bมาร#k กับข้าไหม?\r\n\r\n#b#L0#ตามไปด้วย#l\r\n#L1#ไม่ไป#l");
                }
            }
        }
    } else if (status == 2) {
        if (cm.getPlayer().getMapId() == 993000600) {
            var gameType = cm.getPlayer().getOneInfoQuestInteger(15142, "gameType");
            if (gameType == 8) {
                var reward = 0;
                var quantity = 0;
                if (killWolf) {
                    reward = 2434636;
                    quantity = 2;
                } else {

                    var map = cm.getPlayer().getOneInfoQuestInteger(26022, "map");
                    if (map == 0) {
                        map = 15;
                    }
                    cm.warp(map);
                    cm.dispose();
                    return;
                }
                cm.sendNextNoESC("นี่ ข้าเตรียมของขวัญเล็กๆ น้อยๆ ให้เจ้า\r\n#b#i" + reward + "# #z" + reward + "# " + quantity + " ชิ้น#k\r\nเล็กน้อยแต่รับไว้นะ");
                cm.gainItem(reward, quantity);
                cm.getPlayer().updateOneInfo(15142, "wolf_damage", "0");
                cm.getPlayer().updateOneInfo(15142, "kill_wolf", "0");
            } else {
                cm.sendNextNoESC("ถ้าล่าไปเรื่อยๆ จะได้เจอ #e#rพี่น้องนักล่าค่าหัว#n#k อีกนะ");
            }
        } else {
            if (sel == 0) {
                var portal = cm.getPlayer().getRandomPortal();
                if (portal != null) {
                    if (portal.getType().getType() == 3) {
                        cm.getPlayer().CountAdd("spark_wolf");
                        var mapID = portal.getMapID();
                        //cm.warp(mapID, "sp");
                        //cm.getPlayer().changeChannel(1);
                        cm.getPlayer().removeAllSummons();
                        cm.warpChangeChannel(1, mapID);
                        cm.getPlayer().setEnterRandomPortal(true);
                    } else {
                        cm.getPlayer().CountAdd("random_portal");
                        var mapID = portal.getMapID();
                        for (var i = 0; i < 20; ++i) {
                            if (cm.getPlayerCount(mapID + i) == 0) {
                                mapID = mapID + i;
                                break;
                            }
                        }
                        cm.getPlayer().removeAllSummons();
                        cm.warp(mapID, "sp");
                        cm.getPlayer().setEnterRandomPortal(true);
                    }
                }
                cm.dispose();
            } else if (sel == 1) {
                cm.getPlayer().removeRandomPortal();
            }
        }
    } else if (status == 3) {
        var map = cm.getPlayer().getOneInfoQuestInteger(26022, "map");
        if (map == 0) {
            map = 15;
        }
        cm.warp(map);
        cm.dispose();
    }
}
