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
            cm.sayNpc("#fs11#ถ้าอย่างนั้น เมื่อไหร่ที่อยากเล่นเกมก็กลับมาหาฉันได้เสมอนะ~", GameObjectType.Npc, false, false, ScriptMessageFlag.NpcReplacedByNpc);
            cm.dispose();
            return;
        } else if (mode == 0 && type == 6) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;

        if (status == 0) {
            v0 = "#fs11#ลองมารับ #b#e#i4310307:# #t4310307##n#k จากมินิเกมที่ฉันเตรียมไว้สิ~\r\n";
            v0 += "#L1# #r#e<บัตรเชิญหลากสีสัน>#n เข้าร่วม#l#k\r\n";
            v0 += "#L2# #b#e<บัตรเชิญหลากสีสัน>#n ฟังคำอธิบาย#l#k\r\n\r\n";
            v0 += "#L3# #r#e<รอยัลคาสเซิลเสียดฟ้า>#n เข้าร่วม#l#k\r\n";
            v0 += "#L4# #b#e<รอยัลคาสเซิลเสียดฟ้า>#n ฟังคำอธิบาย#l#k\r\n\r\n";
            v0 += "#L5# #r#e<เรนโบว์รัช>#n เข้าร่วม#l#k\r\n";
            v0 += "#L6# #b#e<เรนโบว์รัช>#n ฟังคำอธิบาย#l#k\r\n\r\n";
            v0 += "#L100# ไม่มีข้อสงสัยอะไรแล้ว#l\r\n";

            cm.askMenu(v0, GameObjectType.Npc, ScriptMessageFlag.NpcReplacedByNpc);
        } else if (status == 1) {
            if (sel == -1) {
                sel = selection;
            }
            if (sel == 2) {
                var v0 = "#b#eบัตรเชิญหลากสีสัน#n#kคือเกมที่มีเป้าหมายคือการส่งบัตรเชิญ\r\nเพื่อเชิญผู้คนมาที่รอยัลคาสเซิล~";
                cm.sayNpc(v0, GameObjectType.Npc, false, true, ScriptMessageFlag.NpcReplacedByNpc);
            } else if (sel == 1) {
                var v0 = "ต้องการเข้าร่วม #e#b<บัตรเชิญหลากสีสัน>#n#k ตอนนี้เลยไหม~?\r\n\r\n#r(ความละเอียดหน้าจอจะเปลี่ยนเป็น 1024x768 ระหว่างเล่นเกม)#k";
                cm.askYesNo(v0, GameObjectType.Npc, ScriptMessageFlag.NpcReplacedByNpc);
            } else if (sel == 3) {
                var v0 = "ต้องการเข้าร่วม #e#b<รอยัลคาสเซิลเสียดฟ้า>#n#k ตอนนี้เลยไหม~?\r\n\r\n#r(ความละเอียดหน้าจอจะเปลี่ยนเป็น 1024x768 ระหว่างเล่นเกม)#k";
                cm.askYesNo(v0, GameObjectType.Npc, ScriptMessageFlag.NpcReplacedByNpc);
            } else if (sel == 4) {
                var v0 = "#b#eรอยัลคาสเซิลเสียดฟ้า#n#kคือเกมที่เราจะสร้างหอคอยของเรา\r\nให้สูงขึ้นไปเรื่อยๆ~";
                cm.sayNpc(v0, GameObjectType.Npc, false, true, ScriptMessageFlag.NpcReplacedByNpc);
            } else if (sel == 5) {
                var v0 = "ต้องการเข้าร่วม #e#b<เรนโบว์รัช>#n#k ตอนนี้เลยไหม~?\r\n\r\n#r(ความละเอียดหน้าจอจะเปลี่ยนเป็น 1366x768 ระหว่างเล่นเกม)#k";
                cm.askYesNo(v0, GameObjectType.Npc, ScriptMessageFlag.NpcReplacedByNpc);
            } else if (sel == 6) {
                var v0 = "#b#eเรนโบว์รัช#n#kคือเกมที่วิ่งไปบนถนนสายรุ้งแสนสวย~";
                cm.sayNpc(v0, GameObjectType.Npc, false, true, ScriptMessageFlag.NpcReplacedByNpc);
            } else if (sel == 100) {
                cm.dispose();
            }
        } else if (status == 2) {
            if (sel == 2) {
                var v0 = "วิธีการส่งบัตรเชิญเพียงแค่กด #b#eปุ่มสีเดียวกัน#n#k\r\nกับบัตรเชิญที่รออยู่เท่านั้นเอง~";
                cm.sayNpc(v0, GameObjectType.Npc, true, true, ScriptMessageFlag.NpcReplacedByNpc);
            } else if (sel == 1) {
                cm.enterMission2Space();
                cm.dispose();
                return;
            } else if (sel == 3) {
                cm.enterBuzzingHouse();
                cm.dispose();
                return;
            } else if (sel == 5) {
                cm.enterExtremeRail();
                cm.dispose();
                return;
            } else if (sel == 4) {
                var v0 = "เมื่อแต่ละชั้นปรากฏขึ้นจากด้านข้าง กดปุ่ม #r#eSpace#k#n เพื่อหยุดมัน\r\nสิ่งสำคัญคือต้องกะ #e#bจังหวะ#k#n ให้ดี~";
                cm.sayNpc(v0, GameObjectType.Npc, true, true, ScriptMessageFlag.NpcReplacedByNpc);
            } else if (sel == 6) {
                var v0 = "ขี่ #b#eกวางหิมะ#n#k สุดมหัศจรรย์แล้ววิ่งไปบนถนนสายรุ้งอย่างอิสระ~";
                cm.sayNpc(v0, GameObjectType.Npc, true, true, ScriptMessageFlag.NpcReplacedByNpc);
            }
        } else if (status == 3) {
            if (sel == 2) {
                var v0 = "บัตรเชิญมี #b#e3 ประเภท#n#k~\r\n\r\nแต่ละประเภทมีรูปร่างและสีต่างกัน แยกออกได้ง่ายแน่นอน~\r\n\r\n#i03801591##i03801592##i03801593#";
                cm.sayNpc(v0, GameObjectType.Npc, true, true, ScriptMessageFlag.NpcReplacedByNpc);
            } else if (sel == 4) {
                var v0 = "ถ้าหยุดไม่ตรงในขอบเขต ก็ไม่ได้หมายความว่าจะล้มเหลวทันที\r\nแต่ความกว้างของชั้นที่สร้างจะลดลง~\r\n\r\nแน่นอนว่าถ้าหยุดในตำแหน่งที่ผิดไปเลยก็ #e<GAME OVER>#n ทันที~";
                cm.sayNpc(v0, GameObjectType.Npc, true, true, ScriptMessageFlag.NpcReplacedByNpc);
            } else if (sel == 6) {
                var v0 = "ขี่กวางหิมะที่วิ่งเร็วขึ้นเรื่อยๆ และใช้ #e#bปุ่มทิศทางซ้ายขวา#k#n เพื่อหลบสิ่งกีดขวาง~\r\n\r\nต้องใช้ #b#eไหวพริบ#n#k หน่อยนะ~?";
                cm.sayNpc(v0, GameObjectType.Npc, true, true, ScriptMessageFlag.NpcReplacedByNpc);
            }
        } else if (status == 4) {
            if (sel == 2) {
                var v0 = "แต่มี #r#eบัตรเชิญพิเศษ#n#l#k หนึ่งใบที่ต้องระวัง~\r\n#i03801594#\r\nบัตรเชิญนี้มีรูปร่างเป็น #b#eทิศตะวันออก ตะวันตก ใต้ เหนือ#n#k\r\nมันจะซ่อนรูปร่างจริงไว้จนกว่าจะส่ง แล้วเปลี่ยนเป็นหนึ่งในสามรูปแบบที่บอกไป~";
                cm.sayNpc(v0, GameObjectType.Npc, true, true, ScriptMessageFlag.NpcReplacedByNpc);
            } else if (sel == 4) {
                var v0 = "ยิ่งความกว้างลดลง ก็ยิ่งสร้างให้สูงยากขึ้นใช่ไหมล่ะ~?\r\nดังนั้นต้องใช้ #e#bสมาธิ#k#n สูงนะ~";
                cm.sayNpc(v0, GameObjectType.Npc, true, true, ScriptMessageFlag.NpcReplacedByNpc);
            } else if (sel == 6) {
                var v0 = "ระวัง #r#eเมฆฝน#n#l#k ที่ขวางทางด้วยนะ~\r\nถ้าชนเข้าก็ #e<GAME OVER>#n ทันที~";
                cm.sayNpc(v0, GameObjectType.Npc, true, true, ScriptMessageFlag.NpcReplacedByNpc);
            }
        } else if (status == 5) {
            if (sel == 2) {
                var v0 = "และเมื่อส่งบัตรเชิญไปเรื่อยๆ #r#eเกจฟีเวอร์#n#l#k จะเต็ม~\r\n\r\nเมื่อ #r#eเกจฟีเวอร์#n#l#k เต็มกด #b#eSpacebar#n#k รัวๆ\r\nเพื่อส่งบัตรเชิญได้เร็วยิ่งขึ้น~\r\n\r\n#i3801199#";
                cm.sayNpc(v0, GameObjectType.Npc, true, true, ScriptMessageFlag.NpcReplacedByNpc);
            } else if (sel == 4) {
                var v0 = "สุดท้าย ยิ่งสร้างหอคอยได้สูงเท่าไหร่ ฉันจะให้ #b#eรางวัล#n#k มากขึ้นเท่านั้น~\r\nตอนนี้เข้าใจเกี่ยวกับ #b#eรอยัลคาสเซิลเสียดฟ้า#n#k แล้วใช่ไหม~?\r\n\r\nงั้นมาเริ่มกันเถอะ~";
                cm.sayNpc(v0, GameObjectType.Npc, true, true, ScriptMessageFlag.NpcReplacedByNpc);
            } else if (sel == 6) {
                var v0 = "สุดท้าย ยิ่งวิ่งไปได้ไกลเท่าไหร่ ฉันจะให้ #b#eรางวัล#n#k มากขึ้นเท่านั้น~\r\nตอนนี้เข้าใจเกี่ยวกับ #b#eเรนโบว์รัช#n#k แล้วใช่ไหม~?\r\nงั้นมาเริ่มกันเถอะ~";
                cm.sayNpc(v0, GameObjectType.Npc, true, true, ScriptMessageFlag.NpcReplacedByNpc);
            }
        } else if (status == 6) {
            if (sel == 2) {
                var v0 = "ช่วยส่งบัตรเชิญให้มากๆ เพื่อให้ผู้คนมาร่วมงานที่รอยัลคาสเซิลแสนสวยกันเถอะ~!\r\n\r\nแน่นอนว่าฉันจะให้ #b#eรางวัล#n#k อย่างงามเลยละ~";
                cm.sayNpc(v0, GameObjectType.Npc, true, true, ScriptMessageFlag.NpcReplacedByNpc);
            } else if (sel == 4 || sel == 6) {
                cm.dispose();
            }
        } else if (status == 7) {
            if (sel == 2) {
                cm.dispose();
            }
        }
    }
}