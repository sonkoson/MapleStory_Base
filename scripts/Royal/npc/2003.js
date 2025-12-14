
/*
    * Script generated via SimpleNPC Automatic Script Maker.
    * (Guardian Project Development Source Script)
    Created by White.
    NPC ID : 2004
    NPC Name : Todd
    Map : The Black : Night Festival (100000000)
    Description : MISSINGNO
*/
importPackage(java.lang);
importPackage(Packages.constants);
importPackage(Packages.handling.channel.handler);
importPackage(Packages.tools.packet);
importPackage(Packages.handling.world);
importPackage(Packages.server.items);
importPackage(Packages.client.items);
importPackage(Packages.launch.world);
importPackage(Packages.client.inventory);
importPackage(Packages.server.enchant);
importPackage(java.sql);
importPackage(Packages.database);
importPackage(java.util);
importPackage(java.io);
importPackage(Packages.client);
importPackage(Packages.server);

var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {

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
        //cm.Entertuto(true);
        cm.sendScreenText("#fs25##fc0xFFF2CB61#ไอเท็ม Eternal#k ข้าจะอธิบายให้ฟัง", false);
    } else if (status == 1) {
        cm.sendScreenText("#fs25##fc0xFFF2CB61#ไอเท็ม Eternal#k ข้าจะอธิบายให้ฟัง", false);
    } else if (status == 2) {
        cm.sendScreenText("#fs17##i1005980# #z1005980# (หมวก)\r\n#i1042433# #z1042433# (เสื้อ)\r\n#i1062285# #z1062285# (กางเกง)", false);
    } else if (status == 3) {
        cm.sendScreenText("#fc0xFFF2CB61#ไอเท็ม Eternal#k แบ่งออกเป็น #fc0xFF6B66FF#3 ส่วน#k", false);
    } else if (status == 4) {
        cm.sendScreenText("\r\n#fc0xFFF2CB61#หมวก#k สามารถอัปเกรดเป็น #fc0xFF6B66FF#[Transcend Arcane Shade Hat]#k\r\n#fc0xFFF2CB61#เสื้อ#k สามารถอัปเกรดเป็น #fc0xFF6B66FF#[Transcend Fafnir Top]#k\r\n#fc0xFFF2CB61#กางเกง#k สามารถอัปเกรดเป็น #fc0xFF6B66FF#[Transcend Fafnir Bottom]#k", true);
    } else if (status == 5) {
        cm.sendScreenText("#fs25##fc0xFFF2CB61#'ระบบอัปเกรด' คืออะไร?#k", false);
    } else if (status == 6) {
        cm.sendScreenText("#fs17##fc0xFFFFBB00#ระบบอัปเกรด#k มีฟังก์ชันเฉพาะคือการถ่ายโอนค่าพลังไอเท็ม", false);
    } else if (status == 7) {
        cm.sendScreenText("#fc0xFF6B66FF##fs20#1. [การถ่ายโอนการตีบวก]#k\r\n#fs17#การตีบวกด้วยสกอรอลล์, การตีบวกด้วยเมโซ และสตาร์ฟอร์ซ จะ #fc0xFFF29661#ถูกถ่ายโอนทั้งหมด#k", false);
    } else if (status == 8) {
        cm.sendScreenText("#fc0xFF6B66FF##fs20#2. [การถ่ายโอนออฟชั่นเสริม]#k\r\n#fs17#ออฟชั่นเสริมจะไม่ถูกถ่ายโอนเป็นออฟชั่นเสริม แต่จะกลายเป็นค่าพลังตีบวกทั่วไป (สีฟ้า + สเตตัส)\r\nดังนั้นขอแนะนำให้ #fc0xFFF29661#ทำออฟชั่นเสริมให้ดีก่อนแล้วค่อยถ่ายโอน#k", false);
    } else if (status == 9) {
        cm.sendScreenText("\r\n#fs15#ค่าออฟชั่นเสริมพื้นฐานจะถูกปรับเป็นค่าสเตตัสตีบวก\r\nและหลังจากอัปเกรดแล้ว #fc0xFFF29661#คุณยังสามารถตั้งค่าออฟชั่นเสริมใหม่#k ได้อีกครั้ง", true);
    } else if (status == 10) {
        cm.sendScreenText("#fs24##fc0xFFFFBB00#วัตถุดิบในการอัปเกรด#k สามารถหาได้จาก #rExtreme Boss#k", false);
    } else if (status == 11) {
        cm.sendScreenText("\r\n#fs22##fc0xFFF15F5F#อย่างแรก#k, #fs20#โหมด Extreme ไม่สามารถลงเป็นปาร์ตี้ได้ ต้อง #rลงคนเดียว#k เท่านั้น\r\n#fs15##fc0xFFF29661#เพื่อป้องกันการแบกและการสมดุลไอเท็มระดับสูง#k", false);
    } else if (status == 12) {
        cm.sendScreenText("#fs22##fc0xFFF15F5F#อย่างที่สอง#k, #fs20#ในโหมด Extreme ดาเมจจะถูกลดลง 90%\r\n#fs15#เลือดของบอสจะเท่ากับโหมด #fc0xFFF29661#Hard#k", true);
    } else if (status == 13) {
        cm.sendScreenText("#fs25##fc0xFF6B66FF#ข้อควรระวัง#k ของระบบ #fc0xFFA566FF#อัปเกรด#k", false);
    } else if (status == 14) {
        cm.sendScreenText("#fs20#\r\n#fc0xFF6B66FF#ไอเท็มที่อัปเกรดแล้ว#k จะเป็น #fc0xFFF15F5F#ไอเท็มผูกมัด#k และไม่สามารถแลกเปลี่ยนได้", false);
    } else if (status == 15) {
        cm.sendScreenText("#fs20##fc0xFF6B66FF#ไอเท็มที่อัปเกรดแล้ว#k จะเป็น #fc0xFFF15F5F#ไอเท็มผูกมัด#k ตัดสามารถเก็บเข้าคลังได้", false);
    } else if (status == 16) {
        cm.sendScreenText("#fs20##fc0xFF6B66FF#ไอเท็มที่อัปเกรดแล้ว#k จะเป็น #fc0xFFF15F5F#ไอเท็มผูกมัด#k และไม่สามารถยกเลิกการอัปเกรดได้", false);
    } else if (status == 17) {
        cm.sendScreenText("#fs16#\r\n\r\nขอบคุณที่ใช้บริการ #fc0xFFF2CB61#Zenia Returns#k เสมอมา", true);
    } else if (status == 18) {
        //cm.Endtuto();
        cm.dispose();
    }
}
