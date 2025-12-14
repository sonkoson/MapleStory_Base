importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);

importPackage(Packages.objects.utils);
importPackage(Packages.objects.item);
importPackage(Packages.constants);

importPackage(Packages.objects.fields.child.dreambreaker);

var status = -1;
var sss = false;
var select = -1;
var selectStage = 0;
var quantity = 0;
var clear = false;
var coin = 0;
var coinMode = 0;

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
                cm.sendNext("อืม! การท้าทาย #rล้มเหลว#k งั้นเหรอ?\r\nถ้ายากเกินไป ลองลดระดับความยากลงหน่อยไหม?");
            } else if (stage > selectedStage) {
                clear = true;
                var clearstage = stage - 1; // Cleared Stage

                talk = "ว้าว~ ผ่านถึง #bStage " + clearstage + "#k แล้วเหรอ? สุดยอดไปเลย~\r\n\r\n";

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
                    talk += "นี่เป็น #r#eสถิติใหม่ประจำสัปดาห์#k#n! ฉันจะลงทะเบียนในอันดับให้นะ\r\n\r\n";
                }

                if (score > b_score) {
                    talk += "แถมยังสร้าง #rสถิติใหม่ส่วนตัว#k อีกด้วย!";
                }
                cm.sendNext(talk);
            }
        } else {
            talk = "#b#e<Dream Breaker>#k#n\r\n"
            talk += "งือ.... เมื่อไหร่จะได้นอนหลับสบายๆ สักทีนะ?\r\n\r\n"
            talk += "#L0# #bท้าทาย <Dream Breaker>#l\r\n"
            talk += "#L1# ตรวจสอบสถิติของฉัน#l\r\n"
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
                    cm.sendNext("กรุณาเคลียร์ช่องเก็บของแล้วมาคุยกันใหม่นะ!");
                    cm.dispose();
                    return;
                }
                cm.sendNext("นี่ครับ รับ #i4036068##b#z4036068##k #r" + quantity + " ชิ้น#k เป็นของขวัญไปเลย!\r\n\r\nไว้มาช่วยอีกนะ!");
            } else {
                cm.warp(ServerConstants.TownMap, 0);
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
                talk = "ฉันจะบอก #e<สถิติ Dream Breaker>#n ของคุณให้ฟัง!\r\n\r\n"
                talk += "#eสถิติสูงสุดส่วนตัว : #bStage " + cm.getPlayer().getOneInfoQuestInteger(15901, "best") + "#k#n\r\n"
                talk += "#eจำนวนครั้งที่เข้าวันนี้ : #b" + cm.getPlayer().GetCount("dream_breaker") + " ครั้ง#k#n\r\n"
                var lastWeek = cm.getPlayer().getOneInfoQuestInteger(20200128, "last_week_dream_breaker");
                var lw = "ไม่มีสถิติ";
                if (lastWeek != 0) {
                    lw = "อันดับ " + lastWeek;
                }
                talk += "#eอันดับสัปดาห์ที่แล้ว : #b" + lw + "#k#n\r\n"
                cm.sendNext(talk);
                cm.dispose();
            } else if (sel == 2) {
                cm.displayDreamBreakerRank();
                cm.dispose();
            } else if (sel == 3) {
                sss = true;
                talk = "อยากรู้อะไรเหรอ?\r\n\r\n";
                talk += "#L0# #eกฎของ Dream Breaker#l\r\n"
                talk += "#L1# การรับ Dream Point และการใช้สกิล#l\r\n"
                talk += "#L2# รางวัลจาก Dream Breaker#l\r\n"
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
                cm.sendGetNumber("Do you want to exchange #i4036068##b#z4036068##k for #i1712003##r#z1712003##k?\r\n(#b30 Dream Coins#k = #r1 Arcane Symbol : Lachelein#k)\r\n Max exchange: #e#r" + coin + "#n#k pieces)", coin, 1, coin);
                coinMode = 1;
            }
        }
    } else if (status >= 2 && sss) {
        if (status == 2 && select == -1) {
            select = sel;
        }
        name = ["กฎของ Dream Breaker", "Dream Point", "รางวัล Dream Breaker", "อันดับ Dream Breaker"];
        dialogue = [
            ["เพื่อหยุดฝันร้ายของ Lucid คุณต้องปกป้อง #b#eMusic Box of Sleep#k#n และทำลาย #r#eMusic Box of Nightmare#k#n",
                "ในบรรดา #eห้องทั้ง 5#n ถ้ามี #b#eMusic Box of Sleep#k#n มากกว่า #b#eเกจสีเหลืองจะเพิ่มไปทางซ้าย#k#n และถ้ามี #r#eMusic Box of Nightmare#k#n มากกว่า #r#eเกจสีม่วงจะเพิ่มไปทางขวา#k#n\r\n\r\nสรุปคือถ้าทำให้ #bเกจสีเหลือง#k#n เต็มภายในเวลาจำกัด #e3 นาที#n ก็จะผ่าน Stage",
                "Dream Breaker สามารถเข้าได้ #eวันละ 3 ครั้ง#n",
                "งั้นช่วยฉันหยุด #r#eฝันร้ายของ Lucid#k#n หน่อยนะ!"
            ],
            ["#eDream Point#n คือคะแนนที่ได้รับเมื่อ #r#eผ่าน Stage#k#n ใน Dream Breaker ใช้สำหรับเปิดใช้งาน #b#eTactical Skill#k#n\r\n\r\nDream Point ที่ได้รับจะ #eเพิ่มขึ้นทีละ 10#n ทุกๆ #e10 Stage#n และสะสมได้สูงสุด #b#e3000 แต้ม#k#n",
                "Skill ที่ใช้ได้มีดังนี้\r\n\r\n#e<Gauge Hold>#n\r\nDream Point: 200 / หยุดการเคลื่อนที่ของเกจ 5 วินาที\r\n\r\n#e<Bell of Awakening>#n\r\nDream Point: 300 / ลบ Music Box of Nightmare 1 อันแบบสุ่ม\r\n\r\n#e<Summon Ragdoll>#n\r\nDream Point: 400 / เรียกตุ๊กตาผ้ามาดึงดูดความสนใจมอนสเตอร์ (อยู่นาน 15 วินาที)\r\n\r\n#e<Explosion>#n\r\nDream Point: 900 / กำจัดมอนสเตอร์ทั้งหมด, ป้องกันการเกิดใหม่ 10 วินาที",
                "ในหนึ่ง Stage #r#eไม่สามารถใช้สกิลเดิมซ้ำได้#k#n ควรใช้สกิลอย่างระมัดระวังนะ!"
            ],
            ["เมื่อเคลียร์ Stage จะได้รับ Coin ตามจำนวน #eStage ที่ไปถึงล่าสุด#n",
                "สุดท้ายหลังจาก #bใช้จำนวนครั้งท้าทายหมดแล้ว#k ถ้าไปหา #rผู้เล่นอันดับ 1-5 ประจำสัปดาห์#k ที่อยู่ทางขวาของ #bLachelein Main Street#k จะได้รับ #bของขวัญต่างๆ วันละ 1 ครั้ง#k อย่าลืมไปหาด้วยนะ!"
            ],
            ["เมื่อเคลียร์ Stage หากเป็น #bสถิติสูงสุดประจำสัปดาห์#k โดยดูจาก #bStage ที่ทำได้ / เวลาที่ใช้#k จะถูก #rลงทะเบียนในอันดับอัตโนมัติ#k",
                "อันดับประจำสัปดาห์จะรีเซ็ต #bทุกวันจันทร์เวลาเที่ยงคืน#k\r\nและจะจำกัดการเข้าตั้งแต่ #rวันอาทิตย์ 23:30 น. ถึง วันจันทร์ 00:30 น.#k เพื่อคำนวณอันดับ",
                "และตัวละครที่ได้อันดับ #e1~5#n ประจำสัปดาห์ จะถูกบันทึกที่ #bLachelein Main Street#k เป็นเวลา 1 สัปดาห์ และทำหน้าที่มอบของขวัญให้กับเหล่านักรบ",
                "#rจงเป็นสุดยอด Dream Breaker#k และเป็นที่น่าอิจฉาของเหล่านักรบดูสิ!"
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
        if (coinMode == 1) {
            var leftslot = cm.getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot();
            if (leftslot < sel) {
                cm.sendOk("ช่องเก็บของต้องว่างอย่างน้อย " + sel + " ช่อง กรุณาเคลียร์ช่องเก็บของแท็บ [Equip] ให้ว่างอย่างน้อย " + sel + " ช่อง");
                cm.dispose();
                return;
            }
            if (cm.haveItem(4036068, sel * 30)) {
                cm.gainItem(4036068, -sel * 30);
                for (var i = 0; i < sel; ++i) {
                    cm.gainItem(1712003, 1);
                }
                cm.sendOk("นี่ครับ รับ #i1712003# #b#z1712003##k " + sel + " ชิ้น ไปเลย");
                cm.dispose();
                return;
            } else if (cm.haveItem(4310227, sel * 30)) {
                cm.gainItem(4310227, -sel * 30);
                for (var i = 0; i < sel; ++i) {
                    cm.gainItem(1712003, 1);
                }
                cm.sendOk("นี่ครับ รับ #i1712003# #b#z1712003##k " + sel + " ชิ้น ไปเลย");
                cm.dispose();
                return;
            } else {
                cm.sendOk("ไม่มีของแล้วยังจะมาขออีก!");
                cm.dispose();
                return;
            }
        }
        if (cm.getPlayer().getMapId() == 921171100) {
            cm.gainItem(4036068, quantity);
            cm.warp(ServerConstants.TownMap);
            cm.dispose();
            return;
        } else {
            selectStage = sel;
            talk = "ต้องการท้าทาย <Dream Breaker> #bStage " + selectStage + "#k หรือไม่?\r\n\r\n";
            talk += "#bจำนวนท้าทายวันนี้ " + cm.getPlayer().GetCount("dream_breaker") + " / 3";
            cm.sendYesNo(talk);
        }
    } else if (status == 3) {
        if (cm.getPlayerCount(921171000) > 0) {
            cm.sendOk("ตอนนี้มีคนกำลังท้าทายอยู่");
            cm.dispose();
            return;
        }

        if (cm.getPlayer().GetCount("dream_breaker") >= 3) {
            cm.sendOk("วันนี้ไม่สามารถท้าทาย #b#e<Dream Breaker>#k#n ได้อีกแล้ว\r\n\r\n#r#e(เข้าได้วันละ 3 ครั้ง)#k#n");
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
