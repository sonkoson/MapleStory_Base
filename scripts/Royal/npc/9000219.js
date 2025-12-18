var status = -1;

var year, month, date2, date, day
var hour, minute;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    talk = ""
    for (i = 1; i <= 10; i++) {
        if (i != 1)
            talk += "\r\n";
        talk += "#e- " + i + " ~ " + (i * 4) + " ตัว: #i4009005##z4009005# " + (i * 10) + " ชิ้น#n"
    }
    dialogue = [
        ["nextprev", 0, "#b#e<Adventure Drill>#k#n คือภารกิจที่ต้องปราบ #eหุ่นฟางมอนสเตอร์#n ให้ได้มากที่สุดใน 1 นาที"],
        ["nextprev", 0, "มีทั้งหมด 10 แท่น แต่ละแท่นจะมีหุ่นฟางต่างชนิดกัน 4 แบบ"],
        ["nextprev", 0, "#b#e<หุ่นฟางปกติ>#k#n\r\n\r\n#fMob/9300799.img/stand/0#\r\nอันแรกคือ #eหุ่นฟางปกติ#n ไม่มีอะไรพิเศษ"],
        ["nextprev", 0, "#b#e<หุ่นฟาง Wonky>#k#n\r\n\r\n#fMob/9305681.img/stand/0#\r\nอันที่สองคือ #eหุ่นฟาง Wonky#n\r\nต่างจากหุ่นฟางปกติตรงที่มี #eDefense#n"],
        ["nextprev", 0, "#b#e<หุ่นฟาง Kkemdi>#k#n\r\n\r\n#fMob/9305678.img/stand/0#\r\nอันที่สามคือ #eหุ่นฟาง Kkemdi#n\r\nมี #eHP สูงและคุณสมบัติบอสมอนสเตอร์#n"],
        ["nextprev", 0, "#b#e<หุ่นฟาง Sogong>#k#n\r\n\r\n#fMob/9305679.img/stand/0#\r\nสุดท้ายคือ #eหุ่นฟาง Sogong#n\r\nยิ่งขึ้นไปแท่นบนๆ #eLevel#n จะยิ่งสูง"],
        ["nextprev", 0, "แน่นอนว่าหุ่นฟางแต่ละชนิดจะแกร่งขึ้นเรื่อยๆ ยิ่งขึ้นไปข้างบน"],
        ["nextprev", 0, "จะให้ #b#e#i4009005##z4009005##k#n ตามจำนวนหุ่นฟางที่ปราบได้ใน 1 นาที"],
        ["nextprev", 0, talk],
        ["nextprev", 0, "#b#e<Adventure Drill>#k#n สามารถรับรางวัลได้ #r#eวันละ 1 ครั้งต่อตัวละคร#k#n เท่านั้น จำไว้นะ"],
        ["nextprev", 0, "ถ้าผลลัพธ์ไม่ถูกใจ สามารถ #eยกเลิกรับรางวัล#n และ #eลองใหม่#n ได้ตลอด"],
        ["nextprev", 0, "มีแต่นักรบผู้แข็งแกร่งเท่านั้นที่มีสิทธิ์สนุกกับการผจญภัยใหม่!"],
        ["nextprev", 0, "ไปเลย! #b" + cm.getPlayer().getName() + "#k!\r\nพิชิต #b#e<Adventure Drill>#k#n แล้วกลับมา!"]
    ]
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
        talk = "พร้อมเริ่ม #e<Adventure Drill>#n หรือยัง?\r\n\r\n"
        talk += "#L0#เข้า #e<Adventure Drill>#n โหมดจริง#l\r\n"
        //talk += "#L1#เข้า #e<Adventure Drill>#n โหมดฝึกซ้อม#l\r\n"
        talk += "#L2#ฟังคำอธิบาย #e<Adventure Drill>#n อีกครั้ง"
        cm.sendSimple(talk);
    } else {
        if (status == 1) {
            st = selection;
        }
        if (st == 2) {
            if (status <= dialogue.length) {
                sendByType(dialogue[status - 1][0], dialogue[status - 1][1], dialogue[status][2]);
            } else {
                cm.dispose();
            }
        } else {
            if (status == 1) {
                if (selection == 0) {
                    getData();
                    if (cm.getPlayer().getParty() != null) {
                        cm.sendOk("#e<Adventure Drill>#n ไม่สามารถเข้าขณะอยู่ในปาร์ตี้ได้");
                        cm.dispose();
                        return;
                    }
                    if (cm.getPlayer().getKeyValue(date, "AdventureDrill") == 1) {
                        cm.sendNext("วันนี้ตัวละคร #b#e" + cm.getPlayer().getName() + "#k#n ทำ <Adventure Drill> เสร็จแล้ว!")
                    } else {
                        var event = cm.getEventManager("AdventureDrill").getInstance("AdventureDrill");
                        if (event == null) {
                            cm.getEventManager("AdventureDrill").startInstance_Solo("" + 993080200, cm.getPlayer());
                            cm.dispose();
                        } else {
                            cm.sendOk("มีคนกำลังท้าทาย #e<Adventure Drill>#n อยู่แล้ว ไปช่องอื่นเถอะ");
                            cm.dispose();
                            return;
                        }
                    }
                } else {
                    if (cm.getPlayerCount(180000000) != 0) {
                        cm.sendOk("มีคนกำลังฝึกซ้อม #e<Adventure Drill>#n อยู่แล้ว ไปช่องอื่นเถอะ");
                        cm.dispose();
                        return;
                    }
                    cm.resetMap(180000000);
                    cm.warp(180000000, 0);
                    var mobx = [45, 240, 440, 635];
                    var moby = [-357, -657, -957, -1257, -1557, -1857, -2157, -2457, -2757, -3057]
                    for (i = 0; i < mobx.length; i++) {
                        for (j = 0; j < moby.length; j++) {
                            var mobid = 9833338 + (i * 10) + j;
                            cm.spawnMob(mobid, mobx[i], moby[j]);
                        }
                    }
                    cm.dispose();
                }
            } else if (status == 2) {
                cm.sendOk("มาใหม่พรุ่งนี้นะ!");
                cm.dispose();
            }
        }
    }
}

function sendByType(type, type2, text) {
    switch (type) {
        case "next":
            cm.sendNextS(text, type2);
            break;
        case "nextprev":
            cm.sendNextPrevS(text, type2);
            break;
    }
}

function getData() {
    /*
        year = CurrentTime.년() + 1900;
        month = CurrentTime.월() + 1;
        date2 = CurrentTime.일();
        date = year * 10000 + month * 100 + date2;
        day = CurrentTime.요일();
        hour = CurrentTime.시();
        minute = CurrentTime.분();
    */
    time = new Date();
    year = time.getFullYear();
    month = time.getMonth() + 1;
    if (month < 10) {
        month = "0" + month;
    }
    date2 = time.getDate() < 10 ? "0" + time.getDate() : time.getDate();
    date = year + "" + month + "" + date2;
    day = time.getDay();
    hour = time.getHours();
    minute = time.getMinutes();

}