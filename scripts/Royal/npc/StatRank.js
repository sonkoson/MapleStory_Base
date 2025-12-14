importPackage(java.lang);
importPackage(Packages.server);

var status = -1;
var sel = 0;
var HuntCoin1 = 4310266;
var HuntCoin10000 = 4310269;
var needcoin = 0;
var needcoincount = 0;
var uptear = 0;
var tearname = "";
var tearnamestring = "";
var suc = 0;
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }

    if (status == 0) {
        txt = "#fs11##bนี่คือระบบที่สามารถเพิ่ม #eRank ของค่าสเตตัสต่างๆ#n#fc0xFF000000# ได้ ลองเลือกหัวข้อที่ต้องการดูสิ#n#k\r\n\r\n#b";
        txt += "#L0#Damage Rank\r\n";
        txt += "#L4#Boss Damage Rank\r\n";
        /*
if (cm.getPlayer().isGM()) {
        txt += "#L1#경험치 랭크\r\n";
        txt += "#L2#드롭률 랭크\r\n";
        txt += "#L3#크리티컬 데미지 랭크\r\n";

        //txt += "#L5#메소 랭크\r\n";
        //txt += "#L6#올스탯 랭크\r\n";
}
*/
        cm.sendSimple(txt);
    } else if (status == 1) {
        sel = selection;
        if (sel == 0) {
            // Damage Tier
            maxtear = 15;
            tearname = "DamageTear";
            tearnamestring = "Damage Rank";
            effect = "Damage";

            if (cm.getPlayer().getKeyValue(999, tearname) < 0) {
                cm.getPlayer().setKeyValue(999, tearname, "0");
            }

            mytear = cm.getPlayer().getKeyValue(999, tearname);
            uptear = mytear + 1;
            damageup = 0;

            if (mytear >= maxtear) {
                cm.sendOk("#fs11#ถึงระดับสูงสุดแล้ว\r\n\r\n#fc0xFF1A9714#สูงสุด " + tearnamestring + " : #fc0xFF000000##e[Rank " + maxtear + "]#n\r\n#bปัจจุบัน " + tearnamestring + " : #fc0xFF000000##e[Rank " + mytear + "]#n");
                cm.dispose();
                return;
            }

            switch (uptear) {
                case 1://1티어
                    needcoin = HuntCoin1;
                    needcoincount = 200;
                    damageup = 10;
                    suc = 100;
                    break;
                case 2:
                    needcoin = HuntCoin1;
                    needcoincount = 600;
                    damageup = 15;
                    suc = 100;
                    break;
                case 3:
                    needcoin = HuntCoin1;
                    needcoincount = 800;
                    damageup = 20;
                    suc = 100;
                    break;
                case 4:
                    needcoin = HuntCoin1;
                    needcoincount = 1400;
                    damageup = 25;
                    suc = 100;
                    break;
                case 5:
                    needcoin = HuntCoin1;
                    needcoincount = 2000;
                    damageup = 30;
                    suc = 100;
                    break;
                case 6:
                    needcoin = HuntCoin1;
                    needcoincount = 2600;
                    damageup = 40;
                    suc = 100;
                    break;
                case 7:
                    needcoin = HuntCoin1;
                    needcoincount = 3200;
                    damageup = 60;
                    suc = 100;
                    break;
                case 8:
                    needcoin = HuntCoin1;
                    needcoincount = 5000;
                    damageup = 80;
                    suc = 100;
                    break;
                case 9:
                    needcoin = HuntCoin1;
                    needcoincount = 7000;
                    damageup = 90;
                    suc = 100;
                    break;
                case 10:
                    needcoin = HuntCoin1;
                    needcoincount = 10000;
                    damageup = 100;
                    suc = 100;
                    break;
                case 11:
                    needcoin = HuntCoin1;
                    needcoincount = 14000;
                    damageup = 120;
                    suc = 100;
                    break;
                case 12:
                    needcoin = HuntCoin1;
                    needcoincount = 18000;
                    damageup = 140;
                    suc = 100;
                    break;
                case 13:
                    needcoin = HuntCoin1;
                    needcoincount = 22000;
                    damageup = 160;
                    suc = 100;
                    break;
                case 14:
                    needcoin = HuntCoin1;
                    needcoincount = 25000;
                    damageup = 180;
                    suc = 100;
                    break;
                case 15:
                    needcoin = HuntCoin1;
                    needcoincount = 30000;
                    damageup = 200;
                    suc = 100;
                    break;
                case 16:
                    needcoin = HuntCoin1;
                    needcoincount = 1;
                    damageup = 220;
                    suc = 100;
                    break;
                case 17:
                    needcoin = HuntCoin1;
                    needcoincount = 1;
                    damageup = 240;
                    suc = 100;
                    break;
                case 18:
                    needcoin = HuntCoin1;
                    needcoincount = 1;
                    damageup = 260;
                    suc = 100;
                    break;
                case 19:
                    needcoin = HuntCoin1;
                    needcoincount = 1;
                    damageup = 280;
                    suc = 100;
                    break;
                case 20:
                    needcoin = HuntCoin1;
                    needcoincount = 1;
                    damageup = 300;
                    suc = 100;
                    break;

            }
            txt = "#fs11#";

            txt += "#fc0xFF1A9714#สูงสุด " + tearnamestring + " : #fc0xFF000000##e[Rank " + maxtear + "]#n\r\n\r\n";

            txt += "#bปัจจุบัน " + tearnamestring + " : #fc0xFF000000##e[Rank " + mytear + "]#n\r\n";
            txt += "#bเลื่อนเป็น " + tearnamestring + " : #fc0xFF000000##e[Rank " + uptear + "]#n\r\n\r\n";

            txt += "โอกาสเลื่อนขั้น, ผลของ Rank ที่เลื่อนขั้น และ\r\n#rไอเทม#fc0xFF000000#ที่จำเป็นสำหรับการเลื่อนขั้น มีดังนี้\r\n\r\n";
            txt += "#b#eโอกาสสำเร็จ : " + suc + "%#k\r\n";
            txt += "#r#eเมื่อสำเร็จ " + effect + " รวม + #r#e" + damageup + "%#n#k\r\nวัตถุดิบ : #i" + needcoin + "# #b#z" + needcoin + "# " + needcoincount + "ชิ้น#k\r\n\r\n";

            txt += "#fs11##fc0xFF000000#ต้องการดำเนินการ #eเลื่อนขั้น#n หรือไม่?";
            cm.sendYesNo(txt);
        } else if (sel == 1) {
            // EXP Tier
            maxtear = 8;
            tearname = "ExpTear";
            tearnamestring = "EXP Rank";
            effect = "EXP";

            if (cm.getPlayer().getKeyValue(999, tearname) < 0) {
                cm.getPlayer().setKeyValue(999, tearname, "0");
            }

            mytear = cm.getPlayer().getKeyValue(999, tearname);
            uptear = mytear + 1;
            up = 0;

            if (mytear >= maxtear) {
                cm.sendOk("#fs11#Already at max rank.\r\n\r\n#fc0xFF1A9714#Max " + tearnamestring + " : #fc0xFF000000##e[Rank " + maxtear + "]#n\r\n#bCurrent " + tearnamestring + " : #fc0xFF000000##e[Rank " + mytear + "]#n");
                cm.dispose();
                return;
            }

            switch (uptear) {
                case 1://1티어
                    needcoin = HuntCoin1;
                    needcoincount = 5000;
                    up = 3;
                    suc = 100;
                    break;
                case 2:
                    needcoin = HuntCoin1;
                    needcoincount = 6000;
                    up = 7;
                    suc = 80;
                    break;
                case 3:
                    needcoin = HuntCoin1;
                    needcoincount = 7000;
                    up = 13;
                    suc = 60;
                    break;
                case 4:
                    needcoin = HuntCoin10000;
                    needcoincount = 2;
                    up = 18;
                    suc = 50;
                    break;
                case 5:
                    needcoin = HuntCoin10000;
                    needcoincount = 5;
                    up = 23;
                    suc = 45;
                    break;
                case 6:
                    needcoin = HuntCoin10000;
                    needcoincount = 10;
                    up = 30;
                    suc = 30;
                    break;
                case 7:
                    needcoin = HuntCoin10000;
                    needcoincount = 30;
                    up = 40;
                    suc = 20;
                    break;
                case 8:
                    needcoin = HuntCoin10000;
                    needcoincount = 50;
                    up = 50;
                    suc = 10;
                    break;
            }

            txt = "#fs11#";

            txt += "#fc0xFF1A9714#Max " + tearnamestring + " : #fc0xFF000000##e[Rank " + maxtear + "]#n\r\n\r\n";

            txt += "#bCurrent " + tearnamestring + " : #fc0xFF000000##e[Rank " + mytear + "]#n\r\n";
            txt += "#bUpgrade " + tearnamestring + " : #fc0xFF000000##e[Rank " + uptear + "]#n\r\n\r\n";

            txt += "Here are the upgrade probability, rank effect, and\r\nrequired #ritems#fc0xFF000000# for upgrade.\r\n\r\n";
            txt += "#b#eUpgrade Success Rate : " + suc + "%#k\r\n";
            txt += "#r#eTotal " + effect + " on Success + #r#e" + up + "%#n#k\r\nMaterials : #i" + needcoin + "# #b#z" + needcoin + "# " + needcoincount + " pcs#k\r\n\r\n";

            txt += "#fs11##fc0xFF000000#Do you really want to proceed with #eUpgrade#n?";
            cm.sendYesNo(txt);
        } else if (sel == 2) {
            // Drop Tier
            maxtear = 8;
            tearname = "DropTear";
            tearnamestring = "Drop Rate Rank";
            effect = "Drop Rate";

            if (cm.getPlayer().getKeyValue(999, tearname) < 0) {
                cm.getPlayer().setKeyValue(999, tearname, "0");
            }

            mytear = cm.getPlayer().getKeyValue(999, tearname);
            uptear = mytear + 1;
            up = 0;

            if (mytear >= maxtear) {
                cm.sendOk("#fs11#Already at max rank.\r\n\r\n#fc0xFF1A9714#Max " + tearnamestring + " : #fc0xFF000000##e[Rank " + maxtear + "]#n\r\n#bCurrent " + tearnamestring + " : #fc0xFF000000##e[Rank " + mytear + "]#n");
                cm.dispose();
                return;
            }

            switch (uptear) {
                case 1://1티어
                    needcoin = HuntCoin1;
                    needcoincount = 5000;
                    up = 10;
                    suc = 100;
                    break;
                case 2:
                    needcoin = HuntCoin1;
                    needcoincount = 6000;
                    up = 20;
                    suc = 80;
                    break;
                case 3:
                    needcoin = HuntCoin1;
                    needcoincount = 7000;
                    up = 40;
                    suc = 60;
                    break;
                case 4:
                    needcoin = HuntCoin10000;
                    needcoincount = 2;
                    up = 60;
                    suc = 50;
                    break;
                case 5:
                    needcoin = HuntCoin10000;
                    needcoincount = 5;
                    up = 80;
                    suc = 45;
                    break;
                case 6:
                    needcoin = HuntCoin10000;//만개짜리코인
                    needcoincount = 10;//2개필요
                    up = 120;//데미지
                    suc = 30;
                    break;
                case 7:
                    needcoin = HuntCoin10000;//만개짜리코인
                    needcoincount = 30;//2개필요
                    up = 180;//데미지
                    suc = 20;
                    break;
                case 8:
                    needcoin = HuntCoin10000;//만개짜리코인
                    needcoincount = 50;//2개필요
                    up = 300;//데미지
                    suc = 10;
                    break;
            }

            txt = "#fs11#";

            txt += "#fc0xFF1A9714#Max " + tearnamestring + " : #fc0xFF000000##e[Rank " + maxtear + "]#n\r\n\r\n";

            txt += "#bCurrent " + tearnamestring + " : #fc0xFF000000##e[Rank " + mytear + "]#n\r\n";
            txt += "#bUpgrade " + tearnamestring + " : #fc0xFF000000##e[Rank " + uptear + "]#n\r\n\r\n";

            txt += "Here are the upgrade probability, rank effect, and\r\nrequired #ritems#fc0xFF000000# for upgrade.\r\n\r\n";
            txt += "#b#eUpgrade Success Rate : " + suc + "%#k\r\n";
            txt += "#r#eTotal " + effect + " on Success + #r#e" + up + "%#n#k\r\nMaterials : #i" + needcoin + "# #b#z" + needcoin + "# " + needcoincount + " pcs#k\r\n\r\n";

            txt += "#fs11##fc0xFF000000#Do you really want to proceed with #eUpgrade#n?";
            cm.sendYesNo(txt);
        } else if (sel == 3) {
            // Crit Dmg Tier
            maxtear = 8;
            tearname = "CridamTear";
            tearnamestring = "Critical Damage Rank";
            effect = "Critical Damage";

            if (cm.getPlayer().getKeyValue(999, tearname) < 0) {
                cm.getPlayer().setKeyValue(999, tearname, "0");
            }

            mytear = cm.getPlayer().getKeyValue(999, tearname);
            uptear = mytear + 1;
            up = 0;

            if (mytear >= maxtear) {
                cm.sendOk("#fs11#Already at max rank.\r\n\r\n#fc0xFF1A9714#Max " + tearnamestring + " : #fc0xFF000000##e[Rank " + maxtear + "]#n\r\n#bCurrent " + tearnamestring + " : #fc0xFF000000##e[Rank " + mytear + "]#n");
                cm.dispose();
                return;
            }

            switch (uptear) {
                case 1://1티어
                    needcoin = HuntCoin1;
                    needcoincount = 400;
                    up = 10;
                    suc = 100;
                    break;
                case 2:
                    needcoin = HuntCoin1;
                    needcoincount = 800;
                    up = 15;
                    suc = 100;
                    break;
                case 3:
                    needcoin = HuntCoin1;
                    needcoincount = 1500;
                    up = 20;
                    suc = 100;
                    break;
                case 4:
                    needcoin = HuntCoin1;
                    needcoincount = 2500;
                    up = 25;
                    suc = 100;
                    break;
                case 5:
                    needcoin = HuntCoin1;
                    needcoincount = 3500;
                    up = 30;
                    suc = 100;
                    break;
                case 6:
                    needcoin = HuntCoin1;//만개짜리코인
                    needcoincount = 4800;
                    up = 35;//데미지
                    suc = 100;
                    break;
                case 7:
                    needcoin = HuntCoin1;
                    needcoincount = 6000;
                    up = 40;//데미지
                    suc = 100;
                    break;
                case 8:
                    needcoin = HuntCoin1;
                    needcoincount = 9000;
                    up = 45;//데미지
                    suc = 100;
                    break;
            }

            txt = "#fs11#";

            txt += "#fc0xFF1A9714#Max " + tearnamestring + " : #fc0xFF000000##e[Rank " + maxtear + "]#n\r\n\r\n";

            txt += "#bCurrent " + tearnamestring + " : #fc0xFF000000##e[Rank " + mytear + "]#n\r\n";
            txt += "#bUpgrade " + tearnamestring + " : #fc0xFF000000##e[Rank " + uptear + "]#n\r\n\r\n";

            txt += "Here are the upgrade probability, rank effect, and\r\nrequired #ritems#fc0xFF000000# for upgrade.\r\n\r\n";
            txt += "#b#eUpgrade Success Rate : " + suc + "%#k\r\n";
            txt += "#r#eTotal " + effect + " on Success + #r#e" + up + "%#n#k\r\nMaterials : #i" + needcoin + "# #b#z" + needcoin + "# " + needcoincount + " pcs#k\r\n\r\n";

            txt += "#fs11##fc0xFF000000#Do you really want to proceed with #eUpgrade#n?";
            cm.sendYesNo(txt);
        } else if (sel == 4) {
            // Boss Dmg Tier
            maxtear = 10;
            tearname = "BossdamTear";
            tearnamestring = "Boss Damage Rank";
            effect = "Boss Damage";

            if (cm.getPlayer().getKeyValue(999, tearname) < 0) {
                cm.getPlayer().setKeyValue(999, tearname, "0");
            }

            mytear = cm.getPlayer().getKeyValue(999, tearname);
            uptear = mytear + 1;
            up = 0;

            if (mytear >= maxtear) {
                cm.sendOk("#fs11#이미 최고등급입니다.\r\n\r\n#fc0xFF1A9714#최대 " + tearnamestring + " : #fc0xFF000000##e[" + maxtear + "랭크]#n\r\n#b현재 " + tearnamestring + " : #fc0xFF000000##e[" + mytear + "랭크]#n");
                cm.dispose();
                return;
            }

            switch (uptear) {
                case 1://1티어
                    needcoin = HuntCoin1;
                    needcoincount = 500;
                    up = 10;
                    suc = 100;
                    break;
                case 2:
                    needcoin = HuntCoin1;
                    needcoincount = 1000;
                    up = 20;
                    suc = 100;
                    break;
                case 3:
                    needcoin = HuntCoin1;
                    needcoincount = 2000;
                    up = 30;
                    suc = 85;
                    break;
                case 4:
                    needcoin = HuntCoin1;
                    needcoincount = 3000;
                    up = 50;
                    suc = 85;
                    break;
                case 5:
                    needcoin = HuntCoin1;
                    needcoincount = 5000;
                    up = 70;
                    suc = 80;
                    break;
                case 6:
                    needcoin = HuntCoin1;
                    needcoincount = 7000;
                    up = 100;//데미지
                    suc = 70;
                    break;
                case 7:
                    needcoin = HuntCoin1;
                    needcoincount = 10000;
                    up = 120;//데미지
                    suc = 60;
                    break;
                case 8:
                    needcoin = HuntCoin1;
                    needcoincount = 12000;
                    up = 150;
                    suc = 50;
                    break;
                case 9:
                    needcoin = HuntCoin1;
                    needcoincount = 15000;
                    up = 170;
                    suc = 50;
                    break;
                case 10:
                    needcoin = HuntCoin1;
                    needcoincount = 20000;
                    up = 200;
                    suc = 50;
                    break;
            }

            txt = "#fs11#";

            txt += "#fc0xFF1A9714#최대 " + tearnamestring + " : #fc0xFF000000##e[" + maxtear + "랭크]#n\r\n\r\n";

            txt += "#b현재 " + tearnamestring + " : #fc0xFF000000##e[" + mytear + "랭크]#n\r\n";
            txt += "#b승급 " + tearnamestring + " : #fc0xFF000000##e[" + uptear + "랭크]#n\r\n\r\n";

            txt += "승급확률, 승급한 랭크의 효과와\r\n승급에 필요한 #r아이템#fc0xFF000000#은 다음과 같다네\r\n\r\n";
            txt += "#b#e승급 성공 확률 : " + suc + "%#k\r\n";
            txt += "#r#e승급 성공 시 총 " + effect + " + #r#e" + up + "%#n#k\r\n승급 재료 : #i" + needcoin + "# #b#z" + needcoin + "# " + needcoincount + "개#k\r\n\r\n";

            txt += "#fs11##fc0xFF000000#정말 #e승급#n을 진행 하겠나?";
            cm.sendYesNo(txt);
        } else if (sel == 5) {
            // Meso Tier
            maxtear = 8;
            tearname = "MesoTear";
            tearnamestring = "Meso Rank";
            effect = "Meso";

            if (cm.getPlayer().getKeyValue(999, tearname) < 0) {
                cm.getPlayer().setKeyValue(999, tearname, "0");
            }

            mytear = cm.getPlayer().getKeyValue(999, tearname);
            uptear = mytear + 1;
            up = 0;

            if (mytear >= maxtear) {
                cm.sendOk("#fs11#Already at max rank.\r\n\r\n#fc0xFF1A9714#Max " + tearnamestring + " : #fc0xFF000000##e[Rank " + maxtear + "]#n\r\n#bCurrent " + tearnamestring + " : #fc0xFF000000##e[Rank " + mytear + "]#n");
                cm.dispose();
                return;
            }

            switch (uptear) {
                case 1://1티어
                    needcoin = HuntCoin1;
                    needcoincount = 5000;
                    up = 10;
                    suc = 100;
                    break;
                case 2:
                    needcoin = HuntCoin1;
                    needcoincount = 6000;
                    up = 20;
                    suc = 80;
                    break;
                case 3:
                    needcoin = HuntCoin1;
                    needcoincount = 7000;
                    up = 40;
                    suc = 60;
                    break;
                case 4:
                    needcoin = HuntCoin10000;
                    needcoincount = 2;
                    up = 60;
                    suc = 50;
                    break;
                case 5:
                    needcoin = HuntCoin10000;
                    needcoincount = 5;
                    up = 80;
                    suc = 45;
                    break;
                case 6:
                    needcoin = HuntCoin10000;//만개짜리코인
                    needcoincount = 10;//2개필요
                    up = 90;//데미지
                    suc = 30;
                    break;
                case 7:
                    needcoin = HuntCoin10000;//만개짜리코인
                    needcoincount = 30;//2개필요
                    up = 100;//데미지
                    suc = 20;
                    break;
                case 8:
                    needcoin = HuntCoin10000;//만개짜리코인
                    needcoincount = 50;//2개필요
                    up = 120;//데미지
                    suc = 10;
                    break;
            }

            txt = "#fs11#";

            txt += "#fc0xFF1A9714#Max " + tearnamestring + " : #fc0xFF000000##e[Rank " + maxtear + "]#n\r\n\r\n";

            txt += "#bCurrent " + tearnamestring + " : #fc0xFF000000##e[Rank " + mytear + "]#n\r\n";
            txt += "#bUpgrade " + tearnamestring + " : #fc0xFF000000##e[Rank " + uptear + "]#n\r\n\r\n";

            txt += "Here are the upgrade probability, rank effect, and\r\nrequired #ritems#fc0xFF000000# for upgrade.\r\n\r\n";
            txt += "#b#eUpgrade Success Rate : " + suc + "%#k\r\n";
            txt += "#r#eTotal " + effect + " on Success + #r#e" + up + "%#n#k\r\nMaterials : #i" + needcoin + "# #b#z" + needcoin + "# " + needcoincount + " pcs#k\r\n\r\n";

            txt += "#fs11##fc0xFF000000#Do you really want to proceed with #eUpgrade#n?";
            cm.sendYesNo(txt);
        }
    } else if (status == 2) {
        if (!cm.haveItem(needcoin, needcoincount)) {
            cm.sendOk("#fs11#Coin ที่จำเป็นสำหรับการเลื่อนขั้นไม่เพียงพอ");
            cm.dispose();
            return;
        }

        cm.gainItem(needcoin, -needcoincount);
        if (Packages.objects.utils.Randomizer.isSuccess(suc)) {
            try {
                if (uptear >= 10) // 10랭크 이상만 월드 메세지 전송
                    cm.worldGMMessage(22, "[Stat Rank] คุณ " + cm.getPlayer().getName() + " เลื่อนขั้น [" + tearnamestring + "] เป็น Rank " + uptear + " เรียบร้อยแล้ว");
                //Packages.scripting.NPCConversationManager.writeLog("TextLog/zenia/UpgradeRank/[StatRankUpgrade].log", "\r\nAccount : " + cm.getClient().getAccountName() + " (" + cm.getClient().getAccID() + ")\r\nName : " + cm.getPlayer().getName() + "\r\nUpgrade Rank : " + tearnamestring + "\r\nUpgrade Tier : " + uptear + "\r\n\r\n", true);
                cm.addCustomLog(3, "[Stat Rank] " + tearnamestring + " Upgrade Tier : " + uptear + "");
                cm.effectText("#fnArial##fs20#[Stat Rank] Upgraded < " + tearnamestring + " > to Rank " + uptear + "", 50, 1000, 6, 0, 330, -550);

                cm.getPlayer().setKeyValue(999, tearname, "" + uptear + "");
                cm.getPlayer().setBonusCTSStat();

                prevflag = cm.getPlayer().getSaveFlag();
                cm.getPlayer().setSaveFlag(64); // QuestInfo
                cm.getPlayer().saveToDB(false, false);
                cm.getPlayer().setSaveFlag(prevflag)

                cm.showEffect(false, "Effect/EventEffect.img/SalonDebut/screenEff/1366");
                cm.showEffect(false, "Effect/CharacterEff.img/GradeUp");

                cm.sendYesNo("#fs11##fc0xFF000000#เลื่อนขั้นสำเร็จ!\r\n\r\n#b" + tearnamestring + " : [Rank " + uptear + "]\r\n\r\n#fc0xFF000000#ต้องการลองเลื่อนขั้นต่อหรือไม่?");
            } catch (err) {
                cm.addCustomLog(50, "[StatRank.js] Error Occurred : " + err + "");
            }
        } else {
            cm.sendYesNo("#fs11##fc0xFF000000#เลื่อนขั้นล้มเหลว..\r\n\r\n#b" + tearnamestring + " : [Rank " + (uptear - 1) + "]\r\n\r\n#fc0xFF000000#ต้องการลองเลื่อนขั้นใหม่อีกครั้งหรือไม่?");
        }
    } else if (status == 3) {
        status = 0;
        action(1, 0, sel);
        return;
    }
}
