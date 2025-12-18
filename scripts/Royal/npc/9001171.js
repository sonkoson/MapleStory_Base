var status = -1;
var sel = 0;

var limit = 6; // Daily limit
var ccoin = 2000005; // Required coin
var quantity = 15; // Coin quantity

function action(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
    switch (status) {
        case 0:
            var t = "สนามล่ามอนที่ดีที่สุดในจักรวาล Maple!\r\nยินดีต้อนรับสู่ #b#e<Frozen Link>#n#k นะ~!\r\n";
            t += "#L0##b<Frozen Link> อยากเข้าไปเลย\r\n";
            t += "#L1##bช่วยอธิบายเกี่ยวกับ <Frozen Link> อีกทีได้ไหม\r\n";
            t += "#L2##bอยากรู้จำนวนครั้งที่ใช้ <Frozen Link> วันนี้#k";
            cm.sendSimple(t);
            break;
        case 1:
            sel = selection;
            if (selection == 0) {
                cm.getPlayer().changeMap(cm.getPlayer().getWarpMap(993014200));
                cm.getPlayer().startFrozenLinkTask();
                cm.dispose();
            } else if (selection == 1) {
                cm.sendNext("#fs12##b#e<Frozen Link>#k คืออะไร?#n\r\n\r\n- เป็นสนามล่าที่เห็นและล่าได้แค่ #bมอนสเตอร์ของตัวเอง#k เท่านั้น\r\n\r\n- จะมองไม่เห็น #bมอนสเตอร์ของผู้เล่นคนอื่น#k และล่าได้\r\n\r\n- เมื่อล่ามอนสเตอร์ จะได้รับ #bโบนัส EXP จาก Frozen Link 150%#k เพิ่มเติม");
            } else if (selection == 2) {
                cm.sendNext("วันนี้ #h # ใช้ Frozen Link ไปแล้ว " + cm.GetCount("frozen_link") + " ครั้ง");
                cm.dispose();
            }
            break;
        case 2:
            cm.sendNextPrev("#fs12##e#b<Frozen Link>#k วิธีใช้งาน#n\r\n\r\n- พูดคุยกับ NPC Tartt ที่อยู่ในแมพ <Frozen Link>\r\n  ใช้ #bNeo Stone 15 ชิ้น#k วันละ #b" + limit + " ครั้ง#k เพื่อ #bเติมมอนสเตอร์ที่ล่าได้#k #b500 ตัว#k\r\n\r\n- เมื่อ #bจำนวนมอนสเตอร์ที่ล่าได้#k ถูกเติมแล้ว มอนสเตอร์จะถูกเรียกออกมาและจะลดลงเมื่อล่า\r\n\r\n- เมื่อ #bจำนวนมอนสเตอร์ที่ล่าได้#k หมดแล้ว มอนสเตอร์จะไม่ถูกเรียกออกมาอีก");
            break;
        case 3:
            cm.sendNextPrev("#fs12##e#b<Frozen Link>#k วิธีใช้งาน#n\r\n\r\n- แม้จะย้ายแมพหรือออกจากเกม #bจำนวนมอนสเตอร์ที่ล่าได้#k ที่เหลือก็ยังคงอยู่\r\n\r\n- แม้วันจะเปลี่ยน #bจำนวนมอนสเตอร์ที่ล่าได้#k ที่เหลือก็ยังคงอยู่\r\n\r\n- ไม่สามารถเติมใหม่ได้จนกว่า #bจำนวนมอนสเตอร์ที่ล่าได้#k จะหมดก่อน");
            break;
        case 4:
            cm.sendNextPrev("#fs12##e#b<Frozen Link>#k วิธีใช้งาน#n\r\n\r\n- เมื่อมอนสเตอร์กำลังถูกเรียก สามารถพูดคุยกับ NPC #bTartt#k เพื่อ #bเปลี่ยนประเภทมอนสเตอร์#k ที่ถูกเรียกได้\r\n\r\n- ช่วงมอนสเตอร์ที่เลือกได้คือ #bเลเวลตัวละคร ± 20 เลเวล#k");
            break;
        case 5:
            cm.dispose();
            break;
    }
}