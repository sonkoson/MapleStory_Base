var status = 0;
var beauty = 0;
var facenew;
var colors;
var hairnew;
var haircolor;


var getmhair = 0;
var getfhair = 0;
var getmface = 0;
var getfface = 0;

var mhair = [];
var fhair = [];
var mface = [];
var fface = [];

var face = [];
var hairList = [];
var select = -1;

var skin = Array(0, 1, 2, 3, 4, 9, 10, 11, 12, 13, 15, 16, 18, 19);

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status >= 0 && mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1)
        status++;
    else
        status--;
    if (status == 0) {
        if (cm.getPlayer().getAndroid() == null) {
            cm.sendOk("หากคุณไม่มี Android คุณจะไม่สามารถใช้บริการแต่งหน้า Android ได้ กรุณากลับมาพร้อมกับ Android");
            cm.dispose();
            return;
        }
        var v0 = "สวัสดี ฉันชื่อ #bSunny#k ผู้ดูแลความงามของ Android! กรุณาเลือกฟังก์ชันที่คุณต้องการ\r\n\r\n";
        v0 += "#L1##b#i5150057# ทรงผม#k#l     ";
        v0 += "#L2##b#i5151036# สีผม#k#l   ";
        v0 += "#L3##b#i5152057# ใบหน้า#k#l\r\n";
        v0 += "#L4##b#i5152100# คอนแทคเลนส์#k#l";
        v0 += "#L5##i5153015# สีผิว#l\r\n";
        cm.sendSimple(v0);
    } else if (status == 1) {
        if (selection == 5) {
            beauty = 1;
            cm.askAvatarAndroid("เลือกสไตล์ที่คุณชอบได้เลย", skin);
        } else if (selection == 1) {
            getmhair = 0;
            getfhair = 0;
            for (var i = 3000; i < 4899; i++) {
                if (cm.isExistFH(i * 10)) {
                    var check = true;
                    for (var s = 1; s <= 7; s++) {
                        if (!cm.isExistFH(i * 10 + s)) {
                            check = false;
                        }
                    }
                    if (i >= 4524 && i <= 4579) {
                        check = false;
                    } else if (i >= 3270 && i <= 3283) {
                        check = false;
                    } else if (i >= 4680 && i <= 4683) {
                        check = false;
                    }
                    if (check) {
                        var hair = parseInt(i / 100);
                        if (hair == 30 || hair == 33 || hair == 35 || hair == 36 || hair == 40 || hair == 43 || hair == 45 || hair == 46) {
                            gar = Math.floor(getmhair / 127);
                            if (getmhair % 127 == 0) {
                                mhair[gar] = [];
                            }
                            getmhair++;
                            mhair[gar].push(i * 10);
                        } else {
                            gar = Math.floor(getfhair / 127);
                            if (getfhair % 127 == 0) {
                                fhair[gar] = [];
                            }
                            getfhair++;
                            fhair[gar].push(i * 10);
                        }
                    }
                }
            }
        } else if (selection == 3) {
            getmface = 0;
            getfface = 0;
            for (var a = 0; a <= 6; a++) {
                for (var i = 0; i < 99; i++) {
                    if (cm.isExistFH(20000 + i + (a * 1000))) {
                        var check = true;
                        for (var s = 1; s <= 7; s++) {
                            if (!cm.isExistFH((20000 + (a * 1000) + i) + (s * 100))) {
                                check = false;
                            }
                        }
                        if (check) {
                            if (a == 0 || a == 3 || a == 5) {
                                gar = Math.floor(getmface / 127);
                                if (getmface % 127 == 0) {
                                    mface[gar] = [];
                                }
                                getmface++;
                                mface[gar].push(20000 + (a * 1000) + i);
                            } else {
                                gar = Math.floor(getfface / 127);
                                if (getfface % 127 == 0) {
                                    fface[gar] = [];
                                }
                                getfface++;
                                fface[gar].push(20000 + (a * 1000) + i);
                            }
                        }
                    }
                }
            }
        }
        if (selection == 1) {
            beauty = 2;
            hairList = cm.getAndroidGender() == 0 ? mhair : fhair;
            var msg = "เรามีทรงผมหลากหลายรูปแบบเตรียมไว้ให้คุณ ";
            var count = 0;
            count = hairList.length;
            msg = "กรุณาเลือกหน้าที่ต้องการ #e(ทั้งหมด " + count + " หน้า)#n\r\n\r\n#b";
            for (var i = 0; i < count; ++i) {
                msg += "#L" + i + "##eหน้า " + (i + 1) + "#n เลือกดูทรงผม#l\r\n";
            }
            cm.sendSimple(msg);
        } else if (selection == 2) {
            beauty = 3;
            haircolor = Array();
            var current = parseInt(cm.getPlayer().getAndroid().getHair() / 10) * 10;
            if (current == 30100) {
                haircolor = Array(current, current + 1, current + 2, current + 3, current + 4, current + 5, current + 6, current + 7);
            } else if (current == 30010) {
                haircolor = Array(current);
            } else {
                for (var i = 0; i < 8; i++) {
                    haircolor.push(current + i);
                }
            }
            cm.askAvatarAndroid("เลือกสไตล์ที่คุณชอบได้เลย", haircolor);
        } else if (selection == 3) {
            beauty = 4;
            face = cm.getAndroidGender() == 0 ? mface : fface;
            var msg = "เรามีรูปแบบใบหน้าหลากหลายรูปแบบเตรียมไว้ให้คุณ ";
            var count = 0;
            count = face.length;
            msg = "กรุณาเลือกหน้าที่ต้องการ #e(ทั้งหมด " + count + " หน้า)#n\r\n\r\n#b";
            for (var i = 0; i < count; ++i) {
                msg += "#L" + i + "##eหน้า " + (i + 1) + "#n เลือกดูใบหน้า#l\r\n";
            }
            cm.sendSimple(msg);
        } else if (selection == 4) {
            beauty = 5;
            var current = cm.getPlayer().getAndroid().getFace() % 100 + 20000;
            colors = Array();
            if (current == 20021 || current == 20022) {
                colors = Array(current, current + 100, current + 200, current + 300, current + 400, current + 600, current + 700);
            } else if (current == 20041 || current == 20042) {
                colors = Array(current, current + 100, current + 200, current + 300);
            } else {
                colors = Array(current, current + 100, current + 200, current + 300, current + 400, current + 500, current + 600, current + 700, current + 800);
            }
            cm.askAvatarAndroid("เลือกสไตล์ที่คุณชอบได้เลย", colors);
        }
    } else if (status == 2) {
        selection = selection & 0xFF;
        select = selection;
        if (beauty == 1) {
            cm.setSkinAndroid(skin[selection]);
        } else if (beauty == 6) {
            cm.setHairAndroid(hairnew[selection]);
        } else if (beauty == 3) {
            cm.setHairAndroid(haircolor[selection]);
        } else if (beauty == 7) {
            cm.setFaceAndroid(facenew[selection]);
        } else if (beauty == 5) {
            cm.setFaceAndroid(colors[selection]);
        } else if (beauty == 2) {
            cm.askAvatarAndroid("เลือกสไตล์ที่คุณชอบได้เลย", hairList[selection]);
        } else if (beauty == 4) {
            cm.askAvatarAndroid("เลือกสไตล์ที่คุณชอบได้เลย", face[selection]);
        }
        if (beauty != 2 && beauty != 4) {
            cm.dispose();
        }
    } else if (status == 3) {
        if (beauty == 2) {
            cm.setHairAndroid(hairList[select][selection]);
        } else if (beauty == 4) {
            cm.setFaceAndroid(face[select][selection]);
        }
        cm.dispose();
    }
}