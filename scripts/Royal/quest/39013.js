importPackage(Packages.scripting);

var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 2) {
            qm.dispose();
            return;
        }
        status--;
    }
    if (status == 0) {
        qm.sayNpc("เลีย! เฮ้ย!", GameObjectType.Npc, false, true, 1, ScriptMessageFlag.Scenario);
    } else if (status == 1) {
        qm.sayNpc("?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Scenario, ScriptMessageFlag.Self, ScriptMessageFlag.FlipImage);
    } else if (status == 2) {
        qm.askAccept("#face0#มีเรื่องจะบอกนะ\nเป็นเรื่องสำคัญมากเลย เลีย!\n#r(ถ้ารับจะวาร์ปไปทันที)", GameObjectType.Npc, 1, ScriptMessageFlag.Scenario);
    } else if (status == 3) {
        qm.forceStartQuest();
        qm.warp(450002000);
        qm.dispose();
    }
}

function end(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
    if (status == 0) {
        qm.sayNpc("#face3#เลีย! มาแล้วเหรอ!", GameObjectType.Npc, false, true, 1, ScriptMessageFlag.Scenario);
    } else if (status == 1) {
        qm.sayNpc("มีอะไรหรอคะคุณหลอกลวง?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Scenario, ScriptMessageFlag.Self, ScriptMessageFlag.FlipImage);
    } else if (status == 2) {
        qm.sayNpc("เลีย! ยังโกรธอยู่เหรอ! ขอโทษนะ!\nเข้าใจผิดกันแน่ๆ เลีย!", GameObjectType.Npc, false, true, 1, ScriptMessageFlag.Scenario);
    } else if (status == 3) {
        qm.sayNpc("เรื่องกับมูโตะก็จบลงแล้ว ซิเมียก็เข้ามาเป็นหัวหน้าพ่อครัวอย่างเป็นทางการ คิดว่าครัวจะโล่งขึ้นสักหน่อย... แต่...", GameObjectType.Npc, false, true, 1, ScriptMessageFlag.Scenario);
    } else if (status == 4) {
        qm.sayNpc("ตอนนี้มอนสเตอร์รอบๆ ได้กลิ่นแล้วเริ่มบุกเข้ามาในครัวแล้ว! เลีย!", GameObjectType.Npc, false, true, 1, ScriptMessageFlag.Scenario);
    } else if (status == 5) {
        qm.sayNpc("หัวหน้าพ่อครัวกับซิเมียไม่เป็นไรใช่มั้ยคะ?", GameObjectType.User, false, true, 1, ScriptMessageFlag.Scenario, ScriptMessageFlag.Self, ScriptMessageFlag.FlipImage);
    } else if (status == 6) {
        qm.sayNpc("#face0#เลีย! รู้ว่าถ้าไม่มีพ่อครัวก็ไม่มีอาหารหรอ แค่แอบขโมยอาหารไปเฉยๆ ยิ่งทำให้โกรธเลย!! เลีย!!", GameObjectType.Npc, false, true, 1, ScriptMessageFlag.Scenario);
    } else if (status == 7) {
        qm.sayNpc("#face3#เลยอยากให้ไปสอนมอนสเตอร์พวกนั้นให้รู้จักกันหน่อย! เลีย?", GameObjectType.Npc, false, true, 1, ScriptMessageFlag.Scenario);
    } else if (status == 8) {
        qm.sayNpc("อืม...", GameObjectType.User, false, true, 1, ScriptMessageFlag.Scenario, ScriptMessageFlag.Self, ScriptMessageFlag.FlipImage);
    } else if (status == 9) {
        qm.sayNpc("คิดว่าเป็นงานช่วยครัวก็แล้วกัน! เลีย! เป็นงานเพื่อมูโตะกับชาวบ้าน\n#bค่าจ้าง#kก็จะไม่น้อยหน้าหรอก! เลีย!", GameObjectType.Npc, false, true, 1, ScriptMessageFlag.Scenario);
    } else if (status == 10) {
        qm.sayNpc("ถ้าเป็นเพื่อมูโตะกับที่นี่ ดิฉันยินดีช่วยค่ะ", GameObjectType.User, false, true, 1, ScriptMessageFlag.Scenario, ScriptMessageFlag.Self, ScriptMessageFlag.FlipImage);
    } else if (status == 11) {
        qm.sayNpc("#face3#เลียเลีย! ดีเลย\nงานช่วยจะให้ทุกวัน #bสามอย่าง#k\nพิเศษเลย! วันนี้ถ้างานไหนไม่ชอบก็เปลี่ยนได้นะ! เลียเลีย!", GameObjectType.Npc, false, true, 1, ScriptMessageFlag.Scenario);
    } else if (status == 12) {
        qm.sayNpc("แน่นอนว่ามีรางวัลสำหรับงานช่วยด้วย พร้อมเมื่อไหร่ก็มาคุยกับฉันได้เลย เลียเลีย!", GameObjectType.Npc, false, true, 1, ScriptMessageFlag.Scenario);
    } else if (status == 13) {
        qm.forceCompleteQuest();
        qm.dispose();
    }
}
