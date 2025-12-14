importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);

importPackage(Packages.scripting);

var status = -1;
var sel = -1;
var sel2 = -1;

function start() {
    status = -1;
    sel = -1;
    sel2 = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0 && type == 3 && selection == -1) {
            cm.sayNpc("#fs11#ถ้าอยากเข้าร่วมมินิเกมเมื่อไหร่ ก็กลับมานะ~", GameObjectType.Npc, false, false, ScriptMessageFlag.NpcReplacedByNpc);
            cm.dispose();
            return;
        } else if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;

        if (status == 0) {
            v0 = "#fs11#ถ้าแสดงฝีมือในมินิเกมให้เห็น ข้าจะให้ #r#e#i4310307:# #t4310307##n#k เป็นรางวัล!\r\n";
            v0 += "ฮิฮิ! ไม่มีใครเล่นเกมเก่งไปกว่าฉันหรอก?!\r\n";
            v0 += "#L3# #r#e<Supernatural Yutnori>#n เข้าร่วม [2 คน]#l#k\r\n";
            v0 += "#L30# #b#e<Supernatural Yutnori>#n ฟังคำอธิบาย#l#k\r\n\r\n";
            v0 += "#L6# #r#e<Battle Reverse>#n เข้าร่วม [2 คน]#l#k\r\n\r\n";
            v0 += "#L4# #r#e<Maple One Card>#n เข้าร่วม [4 คน]#l#k\r\n\r\n";
            v0 += "#L5# #r#e<Maple Soccer>#n เข้าร่วม [10 คน]#l#k\r\n";

            v0 += "\r\n#L100# ไม่มีอะไรสงสัยแล้ว#l\r\n\r\n\r\n";
            //v0 += "#r※ #t4310307# สามารถรับได้สูงสุด 100 ชิ้นต่อวัน ต่อตัวละคร#k";

            cm.askMenu(v0, GameObjectType.Npc, ScriptMessageFlag.NpcReplacedByNpc);
        } else if (status == 1) {
            sel = selection;
            if (sel == 30) {
                v0 = "#fs11##b#e<Supernatural Yutnori>#k#n คือเกมที่ต้องทอย Yut เพื่อเดินหมาก\r\nใครพาหมากเข้าเส้นชัยครบก่อนเป็นฝ่ายชนะ!";

                cm.sayNpc(v0, GameObjectType.Npc, true, true, ScriptMessageFlag.NpcReplacedByNpc);
            } else if (sel == 100) {
                cm.dispose();
            } else if (sel == 3) {
                cm.askYesNo("#fs11#จะเข้าร่วม #e#b<Supernatural Yutnori>#k#n ตอนนี้เลยไหม?\r\n\r\n#r(หากตอบตกลง จะถูกลงทะเบียนในคิวรอ)#k", GameObjectType.Npc, ScriptMessageFlag.NpcReplacedByNpc);
            } else if (sel == 4) {
                cm.askYesNo("#fs11#จะเข้าร่วม #e#b<Maple One Card>#k#n ตอนนี้เลยไหม?\r\n\r\n#r(หากตอบตกลง จะถูกลงทะเบียนในคิวรอ)#k", GameObjectType.Npc, ScriptMessageFlag.NpcReplacedByNpc);
            } else if (sel == 5) {
                cm.askYesNo("#fs11#จะเข้าร่วม #e#b<Maple Soccer>#k#n ตอนนี้เลยไหม?\r\n\r\n#r(หากตอบตกลง จะถูกลงทะเบียนในคิวรอ)#k", GameObjectType.Npc, ScriptMessageFlag.NpcReplacedByNpc);
            } else if (sel == 6) {
                cm.askYesNo("#fs11#จะเข้าร่วม #e#b<Battle Reverse>#k#n ตอนนี้เลยไหม?\r\n\r\n#r(หากตอบตกลง จะถูกลงทะเบียนในคิวรอ)#k", GameObjectType.Npc, ScriptMessageFlag.NpcReplacedByNpc);
            }
        } else if (status == 2) {
            if (sel == 30) {
                v0 = "#fs11#เมื่อเริ่มเกม จะสุ่มเลือก #b#eพลังพิเศษ 1 ครั้ง 2 อย่าง#k#n \r\nและในตาของตัวเอง สามารถทอย Yut เพื่อเดินหมากตามผลลัพธ์ได้";
                cm.sayNpc(v0, GameObjectType.Npc, true, true, ScriptMessageFlag.NpcReplacedByNpc);


            } else if (sel == 3 || sel == 4 || sel == 5 || sel == 6) {
                var canTime = cm.getPlayer().getOneInfoQuestLong(1234569, "miniGame1_can_time");
                var now = System.currentTimeMillis();
                var delta = parseInt((canTime - now));
                if (canTime != 0 && delta > 0) {
                    var minute = parseInt((delta / 1000 / 60));
                    cm.sayNpc("#fs11#สามารถเข้าได้ในอีก " + minute + " นาที", GameObjectType.Npc, false, false, ScriptMessageFlag.NpcReplacedByNpc);
                    cm.dispose();
                    return;
                }

                if (cm.getClient().getChannel() != 1) {
                    cm.sayNpc("#fs11#มินิเกมเล่นหลายคน สามารถเข้าร่วมได้ที่แชแนล 1 เท่านั้น", GameObjectType.Npc, false, false, ScriptMessageFlag.NpcReplacedByNpc);
                    cm.dispose();
                    return;
                }

                if (sel == 3) {
                    cm.registerWaitQueue(993189800); // เข้าร่วมคิว
                    //cm.worldGMMessage(6, "[Mini Game] คุณ " + cm.getPlayer().getName() + " ได้เข้าร่วมคิว [ Supernatural Yutnori ]");
                    cm.worldGMMessage(6, "[Mini Game] คุณ " + cm.getPlayer().getName() + " ได้เข้าร่วมคิว [ Supernatural Yutnori ] [" + cm.loadWaitQueue(993189800) + " / 2]");
                } else if (sel == 4) {
                    cm.registerWaitQueue(993189400); // เข้าร่วมคิว
                    //cm.worldGMMessage(6, "[Mini Game] คุณ " + cm.getPlayer().getName() + " ได้เข้าร่วมคิว [ Maple One Card ]");
                    cm.worldGMMessage(6, "[Mini Game] คุณ " + cm.getPlayer().getName() + " ได้เข้าร่วมคิว [ Maple One Card ] [" + cm.loadWaitQueue(993189400) + " / 4]");
                } else if (sel == 5) {
                    cm.registerWaitQueue(993195100); // เข้าร่วมคิว
                    //cm.worldGMMessage(6, "[Mini Game] คุณ " + cm.getPlayer().getName() + " ได้เข้าร่วมคิว [ Maple Soccer ]");
                    cm.worldGMMessage(6, "[Mini Game] คุณ " + cm.getPlayer().getName() + " ได้เข้าร่วมคิว [ Maple Soccer ] [" + cm.loadWaitQueue(993195100) + " / 10]");
                } else if (sel == 6) {
                    cm.registerWaitQueue(993189600); // เข้าร่วมคิว
                    //cm.worldGMMessage(6, "[Mini Game] คุณ " + cm.getPlayer().getName() + " ได้เข้าร่วมคิว [ Battle Reverse ]");
                    cm.worldGMMessage(6, "[Mini Game] คุณ " + cm.getPlayer().getName() + " ได้เข้าร่วมคิว [ Battle Reverse ] [" + cm.loadWaitQueue(993189600) + " / 2]");
                }

                cm.dispose();
            }
        } else if (status == 3) {
            if (sel == 30) {
                v0 = "#fs11#หากทอยได้ Yut หรือ Mo หรือจับหมากฝ่ายตรงข้ามได้ จะได้ทอยอีกครั้ง!";
                cm.sayNpc(v0, GameObjectType.Npc, true, true, ScriptMessageFlag.NpcReplacedByNpc);
            }
        } else if (status == 4) {
            if (sel == 30) {
                v0 = "#fs11#การใช้ #b#eพลังพิเศษ#k#n ต้องเลือกพลังที่จะใช้ก่อน #b#eทอย Yut#k#n ในตาของตัวเอง";
                cm.sayNpc(v0, GameObjectType.Npc, true, true, ScriptMessageFlag.NpcReplacedByNpc);
            }
        } else if (status == 5) {
            if (sel == 30) {
                v0 = "#fs11#เมื่อใช้พลังพิเศษแล้วทอย Yut\r\nผลลัพธ์ของ Yut จะผสานกับพลังพิเศษ!\r\n\r\n#r(คำเตือน! หากเลือกพลังพิเศษแล้วแต่ไม่ใช้\r\nจนหมดเวลา พลังพิเศษจะหายไป)#k";
                cm.sayNpc(v0, GameObjectType.Npc, true, true, ScriptMessageFlag.NpcReplacedByNpc);
            }
        } else if (status == 6) {
            if (sel == 30) {
                v0 = "#fs11#ผู้ที่พาหมากทั้ง 4 ตัวเข้าเส้นชัยก่อนจะเป็นผู้ชนะ\r\nและจะได้รับรางวัลมากขึ้นตามผลแพ้ชนะ!\r\n\r\n#b#e<รางวัล Supernatural Yutnori>#k#n\r\n - ชนะ : #i4310307:# #b#t4310307:# 60 ชิ้น#k\r\n - เสมอ : #i4310307:# #b#t4310307:# 30 ชิ้น#k\r\n - แพ้ : #i4310307:# #b#t4310307:# 20 ชิ้น#k\r\n\r\n.#k";
                cm.sayNpc(v0, GameObjectType.Npc, true, true, ScriptMessageFlag.NpcReplacedByNpc);
            }
        } else if (status == 7) {
            if (sel == 30) {
                v0 = "#fs11#และถ้าไม่ทอย Yut หรือเดินหมากภายในเวลาที่กำหนด ระบบจะเล่นให้อัตโนมัติ";
                cm.sayNpc(v0, GameObjectType.Npc, true, true, ScriptMessageFlag.NpcReplacedByNpc);
            }
        } else if (status == 8) {
            if (sel == 30) {
                v0 = "#fs11#หากทำซ้ำ 5 ครั้ง จะถูกบังคับให้ออกจากเกม\r\nและจะไม่ได้รับรางวัล ระวังด้วยนะ!";
                cm.sayNpc(v0, GameObjectType.Npc, true, true, ScriptMessageFlag.NpcReplacedByNpc);
            }
        } else if (status == 9) {
            if (sel == 30) {
                cm.dispose();
            }
        }
    }
}