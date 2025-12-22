importPackage(Packages.constants);

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    var bannedhair = [];
    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode != 1) {
        cm.dispose();
        return;
    } else {
        status++;
    }
    if (status == 0) {
        status = 1;
        selection = 3; // Defaulting to the menu logic
    }
    if (status == 1) {
        var codyList = [];
        var gColor = !cm.getPlayer().getGender() ? "#fs11##dFemale" : "#fs11##bMale";
        var seld = selection;
        var selStr = "";

        switch (seld) {
            case 1:
            case 2:
                cm.dispose();
                cm.openNpc(1540011);
                return;
                break;
            case 3:
                selStr += "#fs11##e#r[Character Color]#b#n\r\n";
                selStr += "#L8# #eย้อมสีผมทั่วไป#n\r\n#l";
                //     selStr += "#L21# #eMix Dye#n\r\n#l";
                //selStr += "#L7# #eLens Color#n Change\r\n#l";
                //   selStr += "#L9# #eSkin Color#n Change#l#k\r\n ";
                /*  if (cm.getPlayer().getAndroid() != null) {
                      selStr += "\r\n\r\n#e#r[Android COLOR]#b#n\r\n";
                      selStr += "#L19# #b#eAndroid#n#b General Dye#n\r\n#l";
                      selStr += "#L18# #b#eAndroid#n#b Lens Color#n Change\r\n#l";
                      selStr += "#L20# #b#eAndroid#n#b Skin Color#n Change#l#b\r\n";
                  }*/
                break;
            case 4:
                selStr += "#fs11#ฉันจะเปลี่ยนเพศให้คุณ #h0# เอง\r\n";
                selStr += "#L10# #eเปลี่ยนเป็น " + gColor + "#n#l#b\r\n";
                break;
            case 5:
                cm.dispose();
                cm.openShop(1022002);
                return;
                break;
            case 6:
                cm.dispose();
                cm.openNpcCustom(cm.getClient(), 1530340, "ItemSearch1")
                return;
                break;
            case 7:
                cm.sendOk("#fs11#Coming soon..");
                cm.dispose();
                return;
                break;
        }
        cm.sendSimpleS(selStr, 4);

    } else if (status == 2) {
        if (selection == 8 || selection == 21) {
            for (i = 0; i < bannedhair.length; i++) {
                if (cm.getPlayer().getHair() == bannedhair[i]) {
                    cm.sendOk("ทรงผมนี้ไม่สามารถย้อมสีได้จ้ะ");
                    cm.dispose();
                    return;
                }
            }
        }

        SEL_00 = selection;
        Gender = cm.getPlayer().getGender();

        // Redirects based on original selection logic (mostly unused or specific custom scripts)
        if (SEL_00 == 11) { cm.dispose(); cm.openNpc(1012117); return; }
        if (SEL_00 == 21) { cm.dispose(); cm.openNpc(9000161); return; }
        if (SEL_00 == 22) { cm.dispose(); cm.openShop(11); return; }
        if (SEL_00 == 23) { cm.dispose(); cm.openNpc(9000232); return; }
        if (SEL_00 == 28) { cm.dispose(); cm.openNpc(2001); return; }
        if (SEL_00 == 24) { cm.dispose(); cm.openNpcCustom(cm.getClient(), 3003273, "Itemsearch"); return; }
        if (SEL_00 == 300) { cm.dispose(); cm.openNpc(9330005); return; }
        if (SEL_00 == 400) { cm.dispose(); cm.openNpc(9330005); return; }

        if (SEL_00 == 10) {
            if (cm.getPlayer().getJob() == 10112) {
                cm.sendOkS("อาชีพ Zero ไม่สามารถเปลี่ยนเพศได้จ้ะ", 4);
                cm.dispose();
                return;
            }
            // gColor logic was inverted in prompt logic? 
            // Original: "Current gColor" -> Switch to it? 
            // If !Gender (Female), gColor = Female. But if I am Female I shouldn't switch to Female.
            // Original logic: gColor is just text label of current gender?
            // "gColor = !cm.getPlayer().getGender() ? ...Female : ...Male"
            // If gender is 0 (Male), !0 is true -> Female. So gColor is "Female".
            // So switching TO Female. Correct.

            var targetGenderText = !cm.getPlayer().getGender() ? "หญิง" : "ชาย";
            cm.sendYesNoS("#eเปลี่ยนเพศเป็น " + targetGenderText + "#n#k ใช่หรือไม่? \r\n(หากเปลี่ยนแล้วจะไม่สามารถสวมใส่ไอเทมของเพศเดิมได้)", 4);
        }

        var Beauty = SEL_00 < 2 ? "#eทรงผม#n" : SEL_00 == 2 ? "#eใบหน้า#n" : "#eสี#n";
        var selStr = "#fs11##fc0xFF000000#ฉันช่วยเปลี่ยน " + Beauty + " ให้คุณได้นะ เลือกสไตล์ที่ชอบได้เลยจ้ะ";

        var codyList = []; // Initialize logic for codyList

        switch (SEL_00) {
            // ... (Copying existing array logic, just structure)
            case 8: // Normal Dye
            case 19:
                for (var i = 0; i < 8; i++) {
                    var baseHair = (SEL_00 >= 12 ? cm.getPlayer().getAndroid().getHair() : Packages.constants.GameConstants.isAngelicBuster(cm.getPlayer().getJob()) ? cm.getPlayer().isDressUp() ? cm.getPlayer().getSecondHair() : cm.getPlayer().getHair() : cm.getPlayer().getHair());

                    // Logic to get color variations: (Hair ID / 10) * 10 + 0..7
                    codyList.push(Math.floor(baseHair / 10) * 10 + i);
                }
                break;
            default:
                break;
        }

        if (SEL_00 < 10) {
            cm.sendStyle(selStr, codyList);
        } else if (SEL_00 >= 12) {
            cm.askAvatarAndroid(selStr, codyList);
        }

    } else if (status == 3) {
        if (SEL_00 == 10) { // Gender Change
            if (!Gender) { // Male -> Female
                cm.getPlayer().setHair(31002);
                cm.getPlayer().setFace(21700);
                cm.getPlayer().setGender(1);
            } else { // Female -> Male
                cm.getPlayer().setHair(30000);
                cm.getPlayer().setFace(20100);
                cm.getPlayer().setGender(0);
            }
            cm.dispose();
            cm.getPlayer().fakeRelog();
            cm.updateChar();
            return;
        }

        var newItem = selection & 0xFF;
        // Android Logic
        if (SEL_00 >= 12) {
            // ... Android handling ...
            // Simplified for brevity in this step, need to copy cases if full functionality required or just this file's dyeing.
            // This file seems to focus on Dyeing (Case 8).
            // Original file had cases 1,2,3,4,5,6 (Hair/Face lists). 
            // But the menu (status 1) ONLY shows case 8 (Normal Dye) and case 10 (Gender switch).
            // So I only need to support Case 8 and Case 10 effectively.
            // The large arrays for hair/face seem unreachable from the menu unless I missed something.
            // "selection = 3" in "status == 0".
            // "case 3" in "status == 1" -> Shows #L8# (Normal Dye).
            // So only Case 8 is relevant for styling.

            cm.dispose();
            return;
        }

        if (Packages.constants.GameConstants.isAngelicBuster(cm.getPlayer().getJob())) {
            cm.sendNext("#fs11#ต้องการเปลี่ยนสีสำหรับร่างไหนจ๊ะ?\r\n\r\n#b#L1#ร่างปกติ (Normal)#l\r\n#L2#ร่างแปลงกาย (Dress Up)#l#k");
        } else {
            // Zero Logic
            if (Packages.constants.GameConstants.isZero(cm.getPlayer().getJob()) && Gender) {
                if (SEL_00 == 8) {
                    cm.getPlayer().setSecondBaseColor(-1);
                    cm.getPlayer().setSecondAddColor(0);
                    cm.getPlayer().setSecondBaseProb(0);
                }
                // Only Dye logic applies here since hair/face cases are unreachable
                // But wait, setZeroSecondHair/Face are valid calls. 
                // Assuming codyList is populated correctly for Case 8 (Hair colors).
                // Case 8 populates hair colors.
                cm.setZeroSecondHair(codyList[newItem]); // Wait, Case 8 is HAIR dye?? 
                // (Hair / 10) * 10 + i. Yes, that is hair color change.
            } else {
                if (SEL_00 == 8) {
                    cm.getPlayer().setBaseColor(-1);
                    cm.getPlayer().setAddColor(0);
                    cm.getPlayer().setBaseProb(0);
                }
                cm.setAvatar(4000000, codyList[newItem]);
            }
            cm.dispose();
            cm.updateChar();
        }

    } else if (status == 4) {
        // Angelic Buster Selection
        if (selection == 1) {
            cm.setAvatar(4000000, codyList[newItem]); // Normal
        } else if (selection == 2) {
            if (SEL_00 == 8) { // Dye
                cm.getPlayer().setSecondBaseColor(-1);
                cm.getPlayer().setSecondAddColor(0);
                cm.getPlayer().setSecondBaseProb(0);
            }
            cm.setAngelicSecondHair(codyList[newItem]); // Apply to dress up hair
        }
        cm.dispose();
        cm.updateChar();
    }

    if (selection == 58) {
        cm.dispose();
        cm.openNpc(2001);
    }
}



