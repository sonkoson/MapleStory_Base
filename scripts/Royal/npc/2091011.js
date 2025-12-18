importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);
importPackage(java.text);
importPackage(java.awt);
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
    if (mode == 0 && type == 3 && sel == 0) {
        cm.sendNext("เปลี่ยนใจเหมือนน้ำเดือดเลยนะ\r\nที่นี่ไม่ใช่ที่ง่ายๆ นะ คิดให้ดีก่อนเข้าสิ!");
        cm.dispose();
        return;
    }
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;

        if (status == 0) {
            if (cm.getPlayer().getMapId() == 925020002 || cm.getPlayer().getMapId() == 925020003) {
                cm.displayDojangResult();
                cm.dispose();
                return;
            }
            if (!cm.canEnterDojang()) {
                cm.sendNext("#fs11##e#bวันเสาร์ 23:55 น.#k#n ถึง #e#bวันอาทิตย์ 00:04 น.#k#n ระบบจะ #bรวบรวมอันดับ#k จึงจำกัดการท้าทาย กรุณากลับมาท้าทายใหม่หลัง #b#e00:05 น.#n#k");
                cm.dispose();
                return;
            }
            var v0 = "#fs11#อาจารย์ของเราเป็นคนที่แข็งแกร่งที่สุดใน Mu Lung เลยนะ แกจะท้าทายท่านเหรอ? อย่ามาเสียใจทีหลังล่ะ\r\n#b";
            v0 += "#L0#ขอท้าทาย Mu Lung Dojo หน่อย#l\r\n";
            v0 += "#L6#ขอท้าทาย Mu Lung Dojo (Challenge) หน่อย#l\r\n";
            v0 += "#L1#Mu Lung Dojo คืออะไร?#l\r\n";
            v0 += "#L3#อยากเช็คจำนวนครั้งที่เหลือวันนี้#l\r\n\r\n";
            //v0 += "#L4#อยากรับคะแนน Dojo ที่คำนวณไว้#l\r\n";
            v0 += "#L5#อยากรับรางวัลผู้ติดอันดับ Dojo#l\r\n";
            v0 += "#L2#อยากดูรางวัลผู้ติดอันดับ Dojo#l\r\n\r\n";
            v0 += "#L7##rอยากใช้สนามฝึกป่าหมอก#l"

            cm.sendSimple(v0);
        } else if (status == 1) {
            sel = selection;
            if (sel == 0) {

                cm.sendYesNo("#fs11#เมื่อเข้า Mu Lung Dojo\r\n#fs16##b#eบัฟทั้งหมดจะถูกยกเลิก#k#fs11##n\r\n\r\n#fs16#และถ้ามีบันทึก #bChallenge#k อยู่ก็จะหายไปด้วย#fs11#\r\n\r\nยังจะท้าทายจริงๆ หรือเปล่า?");
            } else if (sel == 1) {
                cm.sendNext("#fs11#อาจารย์ของเราเป็นคนที่แข็งแกร่งที่สุดใน Mu Lung\r\nสถานที่ที่ท่านสร้างขึ้นก็คือ #bMu Lung Dojo#k นี่แหละ");
            } else if (sel == 2) {
                //cm.sendSimple("#fs11#รางวัลที่ได้จาก Mu Lung Dojo มีสองแบบ\r\nเป็น #rผู้ติดอันดับ#k ในแต่ละสาขาเพื่อรับรางวัล\r\nหรือแลกของผ่าน #rคะแนน#k ที่ได้จากการเข้าร่วม\r\n#b\r\n#L0#ถามเกี่ยวกับรางวัลผู้ติดอันดับ#l\r\n#L1#ถามเกี่ยวกับรางวัลการเข้าร่วม (คะแนน)#l");
                cm.sendSimple("#fs11#ถ้าเป็น #rผู้ติดอันดับ#k ในแต่ละสาขา ก็จะได้รับรางวัล\r\n#b\r\n#L0#ถามเกี่ยวกับรางวัลผู้ติดอันดับ#l");
            } else if (sel == 3) {
                cm.sendNext("#fs11#วันนี้แกสามารถเข้า Mu Lung Dojo ได้อีก " + (3 - cm.GetCount("dojang_count")) + " ครั้ง เรื่องแบบนี้ก็นับเองสิ");
                cm.dispose();
            } else if (sel == 4) {
                cm.tryGetDojangPoint();
            } else if (sel == 5) {
                cm.tryGetDojangRankerReward();
            } else if (sel == 6) {
                // Check if eligible for challenge mode
                if (cm.getPlayer().getOneInfoQuestInteger(1234590, "open_challenge") <= 0) {
                    cm.sendNext("#fs11#แกดูไม่มีคุณสมบัติท้าทาย Challenge Mode นะ?\r\nต้องทำบันทึก #b70 ชั้น#k ขึ้นไปในโหมด #bMastery#k ก่อนถึงจะมีคุณสมบัติ\r\n\r\nคนที่เคยผ่าน 70 ชั้นแล้วต้องท้าทายใหม่ถึงจะได้คุณสมบัติ จำไว้ด้วยล่ะ");
                    cm.dispose();
                    return;
                }
                cm.sendYesNo("#fs11#เมื่อเข้า Mu Lung Dojo (Challenge)\r\n#fs16##b#eบัฟทั้งหมดจะถูกยกเลิก#k#fs11##n\r\n\r\n#fs16#และบันทึก #bMastery#k จะหายไป#fs11#\r\n\r\nยังจะท้าทายจริงๆ หรือเปล่า?");
            } else if (sel == 7) {
                Packages.scripting.newscripting.ScriptManager.runScript(cm.getPlayer().getClient(), "mulung_forest", null);
                cm.dispose();
                return;
            }
        } else if (status == 2) {
            if (sel == 0) {
                if (cm.getPlayer().getParty() != null) {
                    cm.sendNext("#fs11#ปาร์ตี้เข้าไม่ได้นะ! ต้องท้าทายคนเดียว! หรือแกขี้ขลาด?");
                    cm.dispose();
                    return;
                }

                var check = true;
                for (var i = 0; i <= 80; ++i) {
                    var id = 925070000 + (i * 100);
                    if (cm.getPlayerCount(id) > 0) {
                        check = false;
                    }
                }
                if (!check) {
                    cm.sendNext("#fs11#มีผู้กล้าคนอื่นกำลังท้าทายอยู่แล้ว ไปช่องอื่นได้ไหม?");
                    cm.dispose();
                    return;
                }
                for (var i = 0; i <= 80; ++i) {
                    var id = 925070000 + (i * 100);
                    cm.resetMap(id);
                }
                if (cm.GetCount("dojang_count") >= 3) {
                    cm.sendNext("#fs11#วันนี้แกใช้โอกาสท้าทาย Mu Lung Dojo ครบ 3 ครั้งแล้วนะ? วันนี้ท้าทายไม่ได้อีกแล้ว กลับไปเถอะ");
                    cm.dispose();
                    return;
                }
                duration = 0;
                if (cm.getPlayer().getCooldownLimit(80002282) != 0) { // Prevent sealed rune power exploit
                    duration = cm.getPlayer().getRemainCooltime(80002282);
                }
                cm.getPlayer().cancelAllBuffs();
                if (duration != 0) {
                    cm.temporaryStatSet(80002282, duration, "RuneBlocked", 1);
                }
                cm.getPlayer().removeAllSummons();
                cm.CountAdd("dojang_count");
                cm.getPlayer().setDojangChallengeMode(0);
                cm.warp(925070000);

                cm.dispose();
            } else if (sel == 6) {
                if (cm.getPlayer().getParty() != null) {
                    cm.sendNext("#fs11#ปาร์ตี้เข้าไม่ได้นะ! ต้องท้าทายคนเดียว! หรือแกขี้ขลาด?");
                    cm.dispose();
                    return;
                }

                var check = true;
                for (var i = 0; i <= 80; ++i) {
                    var id = 925070000 + (i * 100);
                    if (cm.getPlayerCount(id) > 0) {
                        check = false;
                    }
                }
                if (!check) {
                    cm.sendNext("#fs11#มีผู้กล้าคนอื่นกำลังท้าทายอยู่แล้ว ไปช่องอื่นได้ไหม?");
                    cm.dispose();
                    return;
                }
                for (var i = 0; i <= 80; ++i) {
                    var id = 925070000 + (i * 100);
                    cm.resetMap(id);
                }
                if (cm.GetCount("dojang_count_c") >= 3) {
                    cm.sendNext("#fs11#วันนี้แกใช้โอกาสท้าทาย Mu Lung Dojo (Challenge) ครบ 3 ครั้งแล้วนะ? วันนี้ท้าทายไม่ได้อีกแล้ว กลับไปเถอะ");
                    cm.dispose();
                    return;
                }
                duration = 0;
                if (cm.getPlayer().getCooldownLimit(80002282) != 0) { // Prevent sealed rune power exploit
                    duration = cm.getPlayer().getRemainCooltime(80002282);
                }
                cm.getPlayer().cancelAllBuffs();
                if (duration != 0) {
                    cm.temporaryStatSet(80002282, duration, "RuneBlocked", 1);
                }
                cm.getPlayer().removeAllSummons();
                cm.CountAdd("dojang_count_c");
                cm.getPlayer().setDojangChallengeMode(1);
                cm.warp(925070000);
                cm.dispose();
            } else if (sel == 1) {
                cm.sendNext("#fs11#Mu Lung Dojo มี 79 ชั้น รวมชั้นพิเศษของอาจารย์ที่ชั้น 80 #e#bรวมทั้งหมด 80 ชั้น#k#n\r\nยิ่งแกร่งเท่าไหร่ ยิ่งไปได้สูงเท่านั้น\r\nแน่นอนว่าด้วยฝีมือของแก คงไปถึงจุดสิ้นสุดลำบาก");
            } else if (sel == 2) {
                sel2 = selection;
                if (sel2 == 0) {
                    cm.sendNext("#fs11#อาจารย์มอบรางวัลให้ #bผู้ติดอันดับ#k ทุกสัปดาห์\r\nความแข็งแกร่งคือค่านิยมสูงสุดของ Mu Lung Dojo เราจึงให้รางวัลเป็นเรื่องปกติใช่ไหมล่ะ?");
                } else if (sel2 == 1) {
                    cm.sendNext("#fs11#คะแนนจะถูกมอบตามระดับการมีส่วนร่วมของแกใน Mu Lung Dojo\r\nมีสองเกณฑ์ในการให้คะแนน:\r\n\r\n- คะแนน #bตามจำนวนชั้นที่ผ่าน#k ทุกครั้งที่ท้าทาย\r\n- คะแนนตาม #bเปอร์เซ็นไทล์อันดับสัปดาห์ที่แล้ว#k ของช่วงอันดับที่แกอยู่");
                }
            }
        } else if (status == 3) {
            if (sel == 2) {
                if (sel2 == 0) {
                    cm.sendNext("#fs11#เพื่อการแข่งขันที่ยุติธรรมขึ้น ช่วงอันดับจะแตกต่างกันตามเลเวล\r\nดูให้ดีว่าแกอยู่ช่วงไหน\r\n\r\n#e- ผู้เริ่มต้น#k : เลเวล 105~200\r\n- #bMastery#k : เลเวล 201 ขึ้นไป\r\n- #rChallenge#k : Mastery ชั้น 70 ขึ้นไป");
                } else if (sel2 == 1) {
                    cm.sendNext("#fs11#คะแนนตามจำนวนชั้น จะได้รับ 10 คะแนนต่อชั้นเป็นพื้นฐาน และเพิ่ม 100 คะแนนทุก 10 ชั้น");
                }
            } else if (sel == 1) {
                cm.sendNext("#fs11#นอกจากชั้น 80 ที่อาจารย์อยู่ แต่ละชั้นจะมี #rมอนสเตอร์จาก Maple World#k คอยรักษา รายละเอียดข้าก็ไม่รู้\r\nมีแต่อาจารย์เท่านั้นที่รู้");
            }
        } else if (status == 4) {
            if (sel == 2) {
                if (sel2 == 0) {
                    cm.sendNext("#fs11#แน่นอนว่ารางวัลจะแตกต่างตามช่วงอันดับ\r\n#bรางวัลทั้งหมดจะมอบตามช่วงอันดับปัจจุบันที่แกอยู่#k\r\nไม่ต้องมาร้องขอรางวัลจากช่วงอันดับที่เคยผ่านมาหรอกนะ?");
                } else if (sel2 == 1) {
                    cm.sendNext("#fs11#คะแนนตามเปอร์เซ็นไทล์อันดับ ยิ่งอยู่ในช่วงอันดับของผู้แกร่งกว่า และยิ่งได้ผลลัพธ์ดีเท่าไหร่ ก็ยิ่งได้คะแนนมากเท่านั้น");
                }
            } else if (sel == 1) {
                cm.sendNext("#fs11#เมื่อเข้าไป #rบัฟทั้งหมดจะถูกยกเลิก#k ในชั้นเริ่มต้น แข่งขันด้วยพละกำลังของตัวเองถึงจะยุติธรรมไม่ใช่หรอ?");
            }
        } else if (status == 5) {
            if (sel == 2) {
                if (sel2 == 0) {
                    cm.sendNext("#fs11##b< รางวัลผู้ติดอันดับ Mastery Mode >#k\r\nอันดับ Mastery #r 1#k   : #b#i3700526##z3700526##k\r\nอันดับ Mastery #r2~5#k : #b#i3700307##z3700307##k\r\n\r\n\r\n#b< รางวัลผู้ติดอันดับ Challenge Mode >#k\r\nอันดับ Challenge #r 1#k   : #b#i3700525##z3700525##k\r\nอันดับ Challenge #r2~5#k : #b#i3700308##z3700308##k");
                    cm.dispose();
                } else if (sel2 == 1) {
                    cm.sendNext("#fs11#คะแนนตามเปอร์เซ็นไทล์อันดับ ต้องอยู่ #bในเปอร์เซ็นต์ที่กำหนด#k ของแต่ละช่วงอันดับถึงจะได้คะแนน\r\nอยากได้คะแนนก็ต้องแกร่งกว่าคนอื่นสิ เฮ้อ เฮ้อ เฮ้อ...\r\n\r\n#e- #bผู้เริ่มต้น#k : Top 50%\r\n- #rMastery#k : Top 70%");
                }
            } else if (sel == 1) {
                cm.sendNext("#fs11#อยู่ชั้นเริ่มต้นนานแค่ไหนก็ได้\r\nแต่ #rตัวจับเวลาจะหยุดแค่ 30 วินาที#k เท่านั้น ถ้าอยากทำบันทึกดีๆ\r\nก็เตรียมตัวเร็วๆ แล้วไปชั้น 1 เลยดีกว่า");
            }
        } else if (status == 6) {
            if (sel == 2) {
                if (sel2 == 1) {
                    cm.sendNext("#fs11#อ้อ แล้วก็คะแนนมีได้สูงสุด #b500,000 คะแนน#k เท่านั้น ใช้บ่อยๆ เป็นนิสัยดีกว่านะ");
                    cm.dispose();
                }
            } else if (sel == 1) {
                cm.sendNext("#fs11##eชั้น 1 ~ 9#n, #eชั้น 11 ~ 19#n จะมี #bบอสหนึ่งตัว#k ปรากฏ\r\nแค่ปราบตัวเดียวก็ไปชั้นถัดไปได้เลย");
            }
        } else if (status == 7) {
            if (sel == 1) {
                cm.sendNext("#fs11##eชั้น 21 ~ 29#n จะมี #bบอสหนึ่งตัว#k กับ #bลูกน้องห้าตัว#k ปรากฏ\r\nต้องปราบทั้งบอสและลูกน้องถึงจะไปชั้นถัดไปได้");
            }
        } else if (status == 8) {
            if (sel == 1) {
                cm.sendNext("#fs11##eชั้น 31 ~ 39#n ต้องสู้กับ #bบอสสองตัวขึ้นไป#k\r\nไม่ใช่กลัวเสียแล้วใช่ไหม? เฮ้อ เฮ้อ เฮ้อ...");
            }
        } else if (status == 9) {
            if (sel == 1) {
                cm.sendNext("#fs11#ตั้งแต่ #eชั้น 41#n จะกลับมามี #bบอสแค่ตัวเดียว#k อีกครั้ง ไม่ต้องกังวลมาก\r\nแต่จะง่ายกว่าหรือเปล่านั้น ไม่รู้เหมือนกันนะ เฮ้อ เฮ้อ เฮ้อ...");
            }
        } else if (status == 10) {
            if (sel == 1) {
                cm.sendNext("#fs11#ยกเว้นชั้น 80 ที่อาจารย์อยู่ จนถึงชั้น 70\r\n#eทุก 10 ชั้น#n จะมี #bNamed Boss จาก Maple World#k ปรากฏ\r\nที่นี่สามารถใช้โพชั่นได้ทุก #r15 วินาที#k");
            }
        } else if (status == 11) {
            if (sel == 1) {
                cm.sendNext("#fs11#ตั้งแต่ #eชั้น 41 เป็นต้นไป#n ก็ใช้โพชั่นได้ทุก #r15 วินาที#k เหมือนกัน ทำไมน่ะหรอ?\r\nแกเข้าไปก็รู้เองแหละ เฮ้อ เฮ้อ เฮ้อ...");
            }
        } else if (status == 12) {
            if (sel == 1) {
                cm.sendNext("#fs11#ในแต่ละชั้นมีใครอยู่น่ะหรอ? ของแบบนั้นก็ขึ้นไปดูเอง\r\nยิ่งแกแกร่งก็ยิ่งรู้มากขึ้นใช่ไหมล่ะ? เฮ้อ เฮ้อ เฮ้อ...");
            }
        } else if (status == 13) {
            if (sel == 1) {
                cm.sendNext("#fs11#เอางี้ บอกให้อย่างเดียว...\r\n#eชั้น 74 ~ 79#n จะมี #bลูกศิษย์ของอาจารย์#k คอยรักษา\r\nถ้าฝีมือไม่ดีพอที่มาเจอ จะลำบากหน่อยนะ");
            }
        } else if (status == 14) {
            if (sel == 1) {
                cm.sendNext("#fs11#อ้อ ภายใน Mu Lung Dojo เพราะอาจารย์ใช้อาคม\r\nพลังที่เคยใช้ใน Maple World จะเหลือแค่ #b1 ใน 10#k เท่านั้น\r\nอย่าตกใจเมื่อเข้าไปล่ะ");
            }
        } else if (status == 15) {
            if (sel == 1) {
                cm.sendNext("#fs11#การรีเซ็ตอันดับคือ #e#rเที่ยงคืนวันอาทิตย์#k#n\r\nตั้งแต่ #e#bวันเสาร์ 23:55 น.#k#n ถึง #e#bวันอาทิตย์ 00:04 น.#k#n\r\nระบบจะ #bรวบรวมอันดับ#k จึงจำกัดการท้าทาย\r\n\r\nเข้าใจแล้วก็รีบเข้าไปเถอะ\r\nไม่ยุกยิกอยากลองแล้วหรอ?");
                cm.dispose();
            }
        }
    }
}