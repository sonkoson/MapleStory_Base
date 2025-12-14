/*
    This file is part of the OdinMS Maple Story Server
    Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc> 
                       Matthias Butz <matze@odinms.de>
                       Jan Christian Meyer <vimes@odinms.de>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation version 3 as published by
    the Free Software Foundation. You may not use, modify or distribute
    this program under any other version of the GNU Affero General Public
    License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/**
 * Guild Alliance NPC
 */

var status;
var choice;
var guildName;
var partymembers;

function start() {
    //cm.sendOk("The Guild Alliance is currently under development.");
    //cm.dispose();
    partymembers = cm.getPartyMembers();
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
        cm.sendSimple("สวัสดี! ข้าคือ #bเลนาริโอ#k ผู้ดูแลเกี่ยวกับพันธมิตรกิลด์\r\n#b#L0#พันธมิตรกิลด์คืออะไร?#l\r\n#L1#ข้าจะสร้างพันธมิตรกิลด์ได้อย่างไร?#l\r\n#L2#ข้าต้องการสร้างพันธมิตรกิลด์#l\r\n#L3#ข้าต้องการเพิ่มจำนวนกิลด์ในพันธมิตร#l\r\n#L4#ข้าต้องการยุบพันธมิตรกิลด์#l");
    } else if (status == 1) {
        choice = selection;
        if (selection == 0) {
            cm.sendOk("พันธมิตรกิลด์คือการรวมกลุ่มกันของกิลด์ต่างๆ เข้าด้วยกัน และข้าเป็นคนดูแลมัน");
            cm.dispose();
        } else if (selection == 1) {
            cm.sendOk("ในการสร้างพันธมิตรกิลด์ หัวหน้ากิลด์ 2 คนต้องปาร์ตี้กัน หัวหน้าปาร์ตี้จะเป็นผู้นำพันธมิตร");
            cm.dispose();
        } else if (selection == 2) {
            if (cm.getPlayer().getParty() == null || partymembers == null || partymembers.size() != 2 || !cm.isLeader()) {
                cm.sendOk("#fs11#กรุณาตั้งปาร์ตี้กับหัวหน้ากิลด์อีกคน แล้วให้หัวหน้าปาร์ตี้มาคุยกับข้าใหม่"); //Not real text
                cm.dispose();
            } else if (partymembers.get(0).getGuildId() <= 0 || partymembers.get(0).getGuildRank() > 1) {
                cm.sendOk("มีสมาชิกในปาร์ตี้ที่ไม่มีกิลด์ หรือไม่ใช่หัวหน้ากิลด์");
                cm.dispose();
            } else if (partymembers.get(1).getGuildId() <= 0 || partymembers.get(1).getGuildRank() > 1) {
                cm.sendOk("มีสมาชิกในปาร์ตี้ที่ไม่มีกิลด์ หรือไม่ใช่หัวหน้ากิลด์");
                cm.dispose();
            } else {
                var gs = cm.getGuild(cm.getPlayer().getGuildId());
                var gs2 = cm.getGuild(partymembers.get(1).getGuildId());
                if (gs.getAllianceId() > 0) {
                    cm.sendOk("เจ้ามีพันธมิตรกิลด์อยู่แล้ว");
                    cm.dispose();
                } else if (gs2.getAllianceId() > 0) {
                    cm.sendOk("อีกกิลด์มีพันธมิตรอยู่แล้ว");
                    cm.dispose();
                } else if (cm.partyMembersInMap() < 2) {
                    cm.sendOk("สมาชิกปาร์ตี้ทั้งหมดต้องอยู่ในแผนที่เดียวกัน");
                    cm.dispose();
                } else
                    cm.sendYesNo("โอ้ เจ้าต้องการสร้างพันธมิตรจริงๆ หรือ?");
            }
        } else if (selection == 3) {
            if (cm.getPlayer().getGuildRank() == 1 && cm.getPlayer().getAllianceRank() == 1) {
                cm.sendYesNo("การเพิ่มจำนวนกิลด์ต้องใช้ 10,000,000 เมโซ เจ้าต้องการเพิ่มจริงๆ หรือ?"); //ExpandGuild Text
            } else {
                cm.sendOk("เฉพาะหัวหน้ากิลด์เท่านั้นที่ทำได้");
                cm.dispose();
            }
        } else if (selection == 4) {
            if (cm.getPlayer().getGuildRank() == 1 && cm.getPlayer().getAllianceRank() == 1) {
                cm.sendYesNo("เจ้าต้องการยุบมันจริงๆ หรือ?");
            } else {
                cm.sendOk("เฉพาะผู้นำพันธมิตรเท่านั้นที่ทำได้");
                cm.dispose();
            }
        }
    } else if (status == 2) {
        if (choice == 2) {
            cm.sendGetText("กรุณาระบุชื่อพันธมิตรกิลด์ที่ต้องการ (สูงสุด 12 ตัวอักษร)");
        } else if (choice == 3) {
            if (cm.getPlayer().getGuildId() <= 0) {
                cm.sendOk("เจ้าไม่สามารถทำได้");
                cm.dispose();
            } else {
                if (cm.addCapacityToAlliance()) {
                    cm.sendOk("เรียบร้อยแล้ว");
                } else {
                    cm.sendOk("เต็มแล้ว พันธมิตรมีได้สูงสุด 5 กิลด์");
                }
                cm.dispose();
            }
        } else if (choice == 4) {
            if (cm.getPlayer().getGuildId() <= 0) {
                cm.sendOk("เจ้าไม่มีกิลด์ด้วยซ้ำ จะยุบอะไรกัน?");
                cm.dispose();
            } else {
                if (cm.disbandAlliance()) {
                    cm.sendOk("ยุบเรียบร้อยแล้ว");
                } else {
                    cm.sendOk("เกิดข้อผิดพลาดในการยุบพันธมิตร");
                }
                cm.dispose();
            }
        }
    } else if (status == 3) {
        guildName = cm.getText();
        cm.sendYesNo("ชื่อพันธมิตรกิลด์คือ #b" + guildName + "#k ถูกต้องหรือไม่?");
    } else if (status == 4) {
        if (!cm.createAlliance(guildName)) {
            cm.sendNext("ชื่อนี้ถูกใช้ไปแล้ว กรุณาใช้ชื่ออื่น"); //Not real text
            status = 1;
            choice = 2;
        } else
            cm.sendOk("สร้างสำเร็จแล้ว");
        cm.dispose();
    }
}