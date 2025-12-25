importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);
importPackage(Packages.objects.utils);
importPackage(Packages.objects.item);

importPackage(Packages.objects.fields.child.dreambreaker);

var status = -1;
var sss = false;
var select = -1;
var selectStage = 0;
var quantity = 0;
var clear = false;
var coin = 0;
var exchangeMode = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, sel) {
    if (mode == 0 && type == 6 && sel == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        status--;
    } else if (mode == 1) {
        status++;
    }
    if (status == 0) {
        if (cm.getPlayer().getMapId() == 921171100) { // Reward Map
            var stage = cm.getPlayer().getOneInfoQuestInteger(15901, "stage");
            var selectedStage = cm.getPlayer().getOneInfoQuestInteger(15901, "selectedStage");
            if (stage == selectedStage) {
                cm.sendNext("อืม! การท้าทาย #rล้มเหลว#k สินะ?\r\nถ้ามันยากเกินไป ลองเริ่มจากระดับที่ต่ำกว่าดูไหม?");
            } else if (stage > selectedStage) {
                clear = true;
                var clearstage = stage - 1; // Success Stage

                talk = "ว้าว~ ผ่านถึง #bStage " + clearstage + "#k แล้วสินะ? ยอดเยี่ยมมาก~\r\n\r\n";

                var b_best = cm.getPlayer().getOneInfoQuestInteger(15901, "best_b");
                var b_besttime = cm.getPlayer().getOneInfoQuestInteger(15901, "besttime_b");
                var best = cm.getPlayer().getOneInfoQuestInteger(15901, "best");
                var besttime = cm.getPlayer().getOneInfoQuestInteger(15901, "besttime");
                var b_rank = cm.getPlayer().getOneInfoQuestInteger(15901, "rank_b");
                var b_score = b_best * 1000 + (180 - b_besttime);
                var score = clearstage * 1000 + (180 - besttime);
                DreamBreakerRank.editRecord(cm.getPlayer().getName(), best, besttime);
                var rank = DreamBreakerRank.getRank(cm.getPlayer().getName());
                if ((rank > b_rank || score > b_score) && (rank > 0 && rank <= 100)) {
                    talk += "นี่คือ #r#eสถิติใหม่ประจำสัปดาห์#k#n! ฉันจะบันทึกลงในอันดับให้นะ\r\n\r\n";
                }

                if (score > b_score) {
                    talk += "แถมยังเป็น #rสถิติส่วนตัวใหม่#k อีกด้วย!";
                }
                cm.sendNext(talk);
            }
        } else {
            talk = "#b#e<Dream Breaker>#k#n\r\n"
            talk += "เฮ้อ... เมื่อไหร่จะได้นอนหลับสบายๆ สักทีนะ?\r\n\r\n"
            talk += "#L0# #bท้าทาย <Dream Breaker>#l\r\n"
            talk += "#L1# ตรวจสอบบันทึกของฉัน#l\r\n"
            talk += "#L2# ตรวจสอบอันดับประจำสัปดาห์#l\r\n"
            talk += "#L4# แลกเปลี่ยน Dream Coin#l\r\n";
            talk += "#L3# ฟังคำอธิบาย#l";
            cm.sendSimple(talk);
        }
    } else if (status == 1) {
        if (cm.getPlayer().getMapId() == 921171100) {
            if (clear) {
                quantity = cm.getPlayer().getOneInfoQuestInteger(15901, "stage") - 1;
                if (!cm.canHold(4036068, quantity)) {
                    cm.sendNext("เคลียร์ช่องว่างใน Inventory แล้วคุยกับฉันใหม่อีกครั้ง!");
                    cm.dispose();
                    return;
                }
                cm.sendNext("นี่คือ #i4036068##b#z4036068##k #r" + quantity + " ชิ้น#k เป็นของขวัญ!\r\n\r\nไว้คราวหน้ามาช่วยอีกนะ!");
            } else {
                cm.warp(450004000);
                cm.dispose();
                return;
            }
        } else {
            if (sel == 0) {
                var bestStage = cm.getPlayer().getOneInfoQuestInteger(15901, "best");
                talk = "สถิติสูงสุดของคุณคือ #r#eStage " + bestStage + "#k#n สินะ?\r\n"
                talk += "Stage ที่สามารถท้าทายได้ในขณะนี้มีดังนี้\r\n\r\n"
                floor = bestStage - (bestStage % 10);
                for (var i = floor; i >= 0; i -= 10) {
                    i = i == 0 ? 1 : i;
                    talk += "#L" + (i) + "##bStage " + (i) + "#k\r\n";
                }
                cm.sendSimple(talk);
            } else if (sel == 1) {
                talk = "ฉันจะบอก #e<บันทึก Dream Breaker>#n ของคุณให้ฟัง!\r\n\r\n"
                talk += "#eสถิติสูงสุดส่วนตัว : #bStage " + cm.getPlayer().getOneInfoQuestInteger(15901, "best") + "#k#n\r\n"
                talk += "#eจำนวนเข้าวันนี้ : #b" + cm.getPlayer().GetCount("dream_breaker") + " ครั้ง#k#n\r\n"
                var lastWeek = cm.getPlayer().getOneInfoQuestInteger(20200128, "last_week_dream_breaker");
                var lw = "ไม่มีบันทึก";
                if (lastWeek != 0) {
                    lw = lastWeek + " อันดับ";
                }
                talk += "#eอันดับสัปดาห์ที่แล้ว : #b" + lw + "#k#n\r\n"
                cm.sendNext(talk);
                cm.dispose();
            } else if (sel == 2) {
                cm.displayDreamBreakerRank();
                cm.dispose();
            } else if (sel == 3) {
                sss = true;
                talk = "อยากทราบเรื่องอะไรเหรอ?\r\n\r\n";
                talk += "#L0# #eกฎ Dream Breaker#l\r\n"
                talk += "#L1# Dream Point และการใช้สกิล#l\r\n"
                talk += "#L2# รางวัล Dream Breaker#l\r\n"
                talk += "#L3# อันดับ Dream Breaker#l\r\n";
                talk += "#L4# ไม่ฟังคำอธิบาย"
                cm.sendSimple(talk);
            } else if (sel == 4) {
                if (cm.haveItem(4036068, 300)) {
                    coin = 10;
                } else if (cm.haveItem(4036068, 270)) {
                    coin = 9;
                } else if (cm.haveItem(4036068, 240)) {
                    coin = 8;
                } else if (cm.haveItem(4036068, 210)) {
                    coin = 7;
                } else if (cm.haveItem(4036068, 180)) {
                    coin = 6;
                } else if (cm.haveItem(4036068, 150)) {
                    coin = 5;
                } else if (cm.haveItem(4036068, 120)) {
                    coin = 4;
                } else if (cm.haveItem(4036068, 90)) {
                    coin = 3;
                } else if (cm.haveItem(4036068, 60)) {
                    coin = 2;
                } else if (cm.haveItem(4036068, 30)) {
                    coin = 1;
                } else if (cm.haveItem(4310227, 300)) {
                    coin = 10;
                } else if (cm.haveItem(4310227, 270)) {
                    coin = 9;
                } else if (cm.haveItem(4310227, 240)) {
                    coin = 8;
                } else if (cm.haveItem(4310227, 210)) {
                    coin = 7;
                } else if (cm.haveItem(4310227, 180)) {
                    coin = 6;
                } else if (cm.haveItem(4310227, 150)) {
                    coin = 5;
                } else if (cm.haveItem(4310227, 120)) {
                    coin = 4;
                } else if (cm.haveItem(4310227, 90)) {
                    coin = 3;
                } else if (cm.haveItem(4310227, 60)) {
                    coin = 2;
                } else if (cm.haveItem(4310227, 30)) {
                    coin = 1;
                }
                cm.sendGetNumber("#i4036068##b#z4036068##k แลกเป็น #i1712003##r#z1712003##k ไหม?\r\n(#bDream Coin 30 ชิ้น#k = #rArcane Symbol : Lachelein 1 ชิ้น#k)\r\n  แลกได้สูงสุด #e#r" + coin + "#n#k ชิ้น)", coin, 1, coin);
                exchangeMode = 1;
            }
        }
    } else if (status >= 2 && sss) {
        if (status == 2 && select == -1) {
            select = sel;
        }
        name = ["กฎ Dream Breaker", "Dream Point", "รางวัล Dream Breaker", "อันดับ Dream Breaker"];
        dialogue = [
            ["เพื่อหยุดฝันร้ายของ Lucid คุณต้องปกป้อง #b#eMusic Box แห่งการหลับใหล#k#n และทำลาย #r#eMusic Box แห่งฝันร้าย#k#n",
                "ใน #e5 ห้อง#n หากมี #b#eMusic Box แห่งการหลับใหล#k#n มากกว่า #b#eเกจสีเหลืองจะเพิ่มไปทางซ้าย#k#n, หากมี #r#eMusic Box แห่งฝันร้าย#k#n มากกว่า #r#eเกจสีม่วงจะเพิ่มไปทางขวา#k#n\r\n\r\nถ้าคุณทำให้ #bเกจสีเหลือง#k#n เต็มภายใน #e3 นาที#n คุณก็จะผ่าน Stage",
                "Dream Breaker เข้าได้ #eวันละ 3 ครั้ง#n",
                "งั้นช่วยฉันหยุด #r#eฝันร้ายของ Lucid#k#n ทีนะ!"
            ],
            ["#eDream Point#n คือคะแนนที่ได้ #r#eทุกครั้งที่เคลียร์ Stage#k#n ใช้สำหรับกด #b#eTactical Skill#k#n\r\n\r\nDream Point จะเพิ่มขึ้น #eทีละ 10#n ทุกๆ #e10 Stage#n สะสมได้สูงสุด #b#e3000 แต้ม#k#n",
                "#b#eTactical Skill#n#k ที่ใช้ได้มีดังนี้\r\n\r\n#e<Gauge Hold>#n\r\nDream Point: 200 / หยุดการเคลื่อนไหวของเกจ 5 วินาที\r\n\r\n#e<Bell of Awareness>#n\r\nDream Point: 300 / ลบ Music Box แห่งฝันร้าย 1 อันแบบสุ่ม\r\n\r\n#e<Ragdoll Summon>#n\r\nDream Point: 400 / เรียกตุ๊กตาผ้ามาดึงดูดมอนสเตอร์รอบๆ (15 วินาที)\r\n\r\n#e<Explosion>#n\r\nDream Point: 900 / กำจัดมอนสเตอร์ทั้งหมด, ป้องกันการเกิดใหม่ 10 วินาที",
                "ใน Stage เดียวกัน #r#eไม่สามารถใช้สกิลเดิมซ้ำสองครั้งได้#k#n ดังนั้นใช้ให้ดีนะ!"
            ],
            ["เมื่อเคลียร์ Stage จะได้รับเหรียญเท่ากับ #eStage สุดท้ายที่ทำได้#n",
                "หลังจากใช้ #bจำนวนท้าทายรายวันหมดแล้ว#k หากไปหา #rผู้เล่นอันดับ 1~5 ประจำสัปดาห์#k ที่อยู่ทางขวาของ #bLachelein Main Street#k จะได้รับ #bของขวัญวันละ 1 ครั้ง#k อย่าลืมไปรับนะ!"
            ],
            ["หากเป็น #bสถิติสูงสุดประจำสัปดาห์#k ตาม #bStage ที่ทำได้ / เวลาที่ใช้#k จะถูก #rลงทะเบียนในอันดับอัตโนมัติ#k",
                "อันดับประจำสัปดาห์จะรีเซ็ต #bเที่ยงคืนวันจันทร์#k\r\nเพื่อสรุปอันดับ จะเข้าไม่ได้ช่วง #rวันอาทิตย์ 23:30 ถึงวันจันทร์ 00:30#k",
                "และตัวละครอันดับ #e1~5#n จะถูกบันทึกรูปปั้นที่ #bLachelein Main Street#k เป็นเวลา 1 สัปดาห์ และทำหน้าที่มอบของขวัญให้ผู้เล่นคนอื่น",
                "มาเป็น #rสุดยอด Dream Breaker#k และเป็นที่ชื่นชมของผู้กล้าทุกคนดูสิ!"
            ]
        ];

        if (status - 2 == dialogue[select].length) {
            cm.dispose();
            return;
        }
        talk = "#e<" + name[select] + ">#n\r\n\r\n";
        talk += dialogue[select][parseInt(status - 2)];
        if (status == 2) {
            cm.sendNext(talk);
        } else {
            cm.sendNextPrev(talk);
        }
    } else if (status == 2) {
        if (exchangeMode == 1) {
            var leftslot = cm.getPlayer().getInventory(Packages.objects.item.MapleInventoryType.EQUIP).getNumFreeSlot();
            if (leftslot < sel) {
                cm.sendOk("ต้องการช่องว่างใน Inventory อย่างน้อย " + sel + " ช่อง");
                cm.dispose();
                return;
            }
            if (cm.haveItem(4036068, sel * 30)) {
                cm.gainItem(4036068, -sel * 30);
                for (var i = 0; i < sel; ++i) {
                    cm.gainItem(1712003, 1);
                }
                cm.sendOk("เอานี่ไป #i1712003# #b#z1712003##k " + sel + " ชิ้น");
                cm.dispose();
                return;
            } else if (cm.haveItem(4310227, sel * 30)) {
                cm.gainItem(4310227, -sel * 30);
                for (var i = 0; i < sel; ++i) {
                    cm.gainItem(1712003, 1);
                }
                cm.sendOk("เอานี่ไป #i1712003# #b#z1712003##k " + sel + " ชิ้น");
                cm.dispose();
                return;
            } else {
                cm.sendOk("ไม่มีวัตถุดิบอย่ามาเนียน!");
                cm.dispose();
                return;
            }
        }
        if (cm.getPlayer().getMapId() == 921171100) {
            cm.gainItem(4036068, quantity);
            cm.warp(450004000);
            cm.dispose();
            return;
        } else {
            selectStage = sel;
            talk = "จะท้าทาย <Dream Breaker> #bStage " + selectStage + "#k ไหม?\r\n\r\n";
            talk += "#bจำนวนท้าทายวันนี้ " + cm.getPlayer().GetCount("dream_breaker") + " / 3";
            cm.sendYesNo(talk);
        }
    } else if (status == 3) {
        if (cm.getPlayerCount(921171000) > 0) {
            cm.sendOk("มีคนกำลังท้าทายอยู่");
            cm.dispose();
            return;
        }

        if (cm.getPlayer().GetCount("dream_breaker") >= 3) {
            cm.sendOk("วันนี้ท้าทาย <Dream Breaker> ไม่ได้แล้ว\r\n\r\n#r#e(เข้าได้วันละ 3 ครั้ง)#k#n");
            cm.dispose();
        } else {
            cm.getPlayer().updateOneInfo(15901, "selectedStage", "" + selectStage);
            cm.getPlayer().CountAdd("dream_breaker");
            cm.resetMap(921171000);
            cm.warp(921171000);
            cm.dispose();
        }
    }
}
