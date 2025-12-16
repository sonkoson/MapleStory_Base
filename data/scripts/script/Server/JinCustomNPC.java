package script.Server;

import database.DBConfig;
import database.DBConnection;
import database.loader.CharacterSaveFlag;
import logging.LoggingManager;
import logging.entry.ConsumeLog;
import logging.entry.EnchantLog;
import network.center.Center;
import network.center.praise.PraiseDonationMeso;
import network.center.praise.PraiseDonationMesoLog;
import network.center.praise.PraiseDonationMesoRank;
import network.center.praise.PraiseDonationMesoRankEntry;
import network.models.CField;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.item.*;
import objects.users.PraisePoint;
import objects.users.enchant.GradeRandomOption;
import objects.users.enchant.ItemFlag;
import objects.users.extra.*;
import objects.users.stone.ImprintedStoneOption;
import objects.users.stone.ImprintedStonePayType;
import objects.utils.Randomizer;
import scripting.ScriptMessageFlag;
import scripting.newscripting.ScriptEngineNPC;

import java.sql.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import constants.GameConstants;
import constants.QuestExConstants;

public class JinCustomNPC extends ScriptEngineNPC {

    String[] grades = new String[] {
            "Unrank", "C", "B", "A", "S", "SS", "SSS"
    };

    ItemEntry[] itemList = new ItemEntry[] {
            new ItemEntry(5680157, new int[] { 3500, 3200, 3200, 2800, 2500, 2000 }, new int[] { 1, 1, 2, 3, 3, 3 }, 1,
                    1),
            new ItemEntry(5069001, new int[] { 2200, 2000, 2000, 1700, 1500, 1300 }, new int[] { 1, 1, 2, 3, 3, 3 }, 2,
                    1),
            new ItemEntry(5060048, new int[] { 3500, 3200, 3200, 2800, 2500, 2000 }, new int[] { 1, 1, 2, 3, 3, 3 }, 3,
                    1),
            new ItemEntry(5068300, new int[] { 4500, 4000, 4000, 3500, 3000, 2500 }, new int[] { 1, 1, 2, 3, 3, 3 }, 4,
                    1),
            new ItemEntry(5069100, new int[] { 2200, 2000, 2000, 1700, 1500, 1300 }, new int[] { 1, 1, 2, 3, 3, 3 }, 5,
                    1),
            new ItemEntry(5121060, new int[] { 4500, 4000, 4000, 3500, 3000, 2500 }, new int[] { 1, 1, 1, 1, 1, 1 }, 6,
                    1),
            new ItemEntry(2049360, new int[] { 0, 6500, 6500, 5500, 5000, 4000 }, new int[] { 0, 1, 1, 2, 3, 3 }, 7, 2),
            new ItemEntry(2434557, new int[] { 0, 0, 8000, 7000, 6000, 5000 }, new int[] { 0, 0, 1, 2, 3, 3 }, 8, 3),
            new ItemEntry(5121060, new int[] { 0, 0, 4000, 3500, 3000, 2500 }, new int[] { 0, 0, 1, 1, 1, 1 }, 9, 3),
            new ItemEntry(2049360, new int[] { 0, 0, 6500, 5500, 5000, 4000 }, new int[] { 0, 0, 1, 2, 3, 3 }, 10, 3),
            new ItemEntry(2439660, new int[] { 0, 0, 0, 0, 12000, 10000 }, new int[] { 0, 0, 0, 0, 1, 2 }, 11, 5),
            new ItemEntry(5680409, new int[] { 0, 0, 0, 0, 0, 5000 }, new int[] { 0, 0, 0, 0, 0, 1 }, 12, 6),
            new ItemEntry(5680410, new int[] { 0, 0, 0, 0, 0, 10000 }, new int[] { 0, 0, 0, 0, 0, 1 }, 13, 6),
            new ItemEntry(5680411, new int[] { 0, 0, 0, 0, 0, 20000 }, new int[] { 0, 0, 0, 0, 0, 1 }, 14, 6),
    };

    public void black_bean_membership() {
        if (DBConfig.isGanglim) {
            return;
        }
        initNPC(MapleLifeFactory.getNPC(9062153));

        displayShop();
    }

    public void displayShop() {
        int grade = getPlayer().getOneInfoQuestInteger(100161, "lv");
        String gradeName = "#e<ร้านค้าสมาชิกระดับ " + grades[grade] + ">#n";
        String v0 = gradeName + "\r\nกล่องเสบียงจะได้รับฟรี 1 ครั้งต่อวัน";
        v0 += "\r\n#eคะแนนที่มี : " + NumberFormat.getInstance().format(getPlayer().getRealCash()) + "#n\r\n";
        int item = 2630688 + grade;

        if (grade > 0) {
            if (getPlayer().getOneInfoQuestInteger(100161, "0_buy_count") == 0) {
                v0 += "#L0##b#e#i" + item + "# #z" + item + "##n รับเสบียง#k#l\r\n";
            } else {
                v0 += "\r\n#b#e#i" + item + "# #z" + item + "##n รับเสบียง #e#k(รับแล้ว)#n#l";
            }
        }
        v0 += "\r\n\r\n#e<ร้านค้าสมาชิกรายวัน>#n\r\n#b";

        if (grade == 0) {
            grade = 1;
        }
        for (ItemEntry entry : itemList) {
            int itemID = entry.getItemID();
            int index = entry.getIndex();
            int buyCount = getPlayer().getOneInfoQuestInteger(100161, index + "_buy_count");
            int remain = entry.getWorldLimit(grade - 1) - buyCount; // Remaining purchases available today
            int gradeLimit = entry.getGradeLimit();
            int price = entry.getPrice(grade - 1);
            if (gradeLimit > getPlayer().getOneInfoQuestInteger(100161, "lv")) {
                // When grade requirement is not met
                v0 += "     #i" + itemID + "#  #z" + itemID + "# (" + NumberFormat.getInstance().format(price)
                        + " P)#l\r\n";
                v0 += "#k#e           - ซื้อได้วันนี้ : 0 ครั้ง#b#n #r(ระดับไม่พอ)#b\r\n";
            } else {
                if (remain <= 0) {
                    // When no purchases remain
                    v0 += "     #i" + itemID + "#  #z" + itemID + "# (" + NumberFormat.getInstance().format(price)
                            + " P)#l\r\n";
                    v0 += "#k#e           - ซื้อได้วันนี้ : " + remain + " ครั้ง#b#n\r\n";
                } else {
                    v0 += "#L" + index + "##i" + itemID + "#  #z" + itemID + "# ("
                            + NumberFormat.getInstance().format(price) + " P)#l\r\n";
                    v0 += "#k#e           - ซื้อได้วันนี้ : " + remain + " ครั้ง#b#n\r\n";
                }
            }
        }
        int v1 = self.askMenu(v0);
        if (v1 == 0) {
            if (getPlayer().getOneInfoQuestInteger(100161, "0_buy_count") != 0) {
                return;
            }
            getPlayer().updateOneInfo(100161, "0_buy_count", "1");
            if (1 == target.exchange(item, 1)) {
                if (0 == self.askMenu("#b#i" + item + "# #z" + item
                        + "##k ได้รับเรียบร้อยแล้ว\r\n\r\n#b#L0#กลับไปที่รายการไอเท็ม#l")) {
                    displayShop();
                }
            } else {
                getPlayer().updateOneInfo(100161, "0_buy_count", "0");
                self.say("กรุณาเพิ่มช่องว่างในกระเป๋าแล้วลองใหม่อีกครั้ง");
            }
        } else {
            if (v1 == -1) {
                return;
            }
            if (v1 > itemList.length) {
                return;
            }
            ItemEntry pick = itemList[v1 - 1];
            if (pick == null) {
                return;
            }
            if (pick.getGradeLimit() > grade) {
                return;
            }
            int buyCount = getPlayer().getOneInfoQuestInteger(100161, pick.getIndex() + "_buy_count");
            int remain = pick.getWorldLimit(grade - 1) - buyCount;
            if (0 >= remain) {
                return;
            }
            String v2 = "#e<ร้านค้าสมาชิกรายวัน>#n\r\n#b#i" + pick.getItemID() + "# #z" + pick.getItemID() + "##k";
            v2 += "\r\n\r\n#eซื้อได้วันนี้ : " + remain + " ครั้ง\r\nคะแนนที่มี : "
                    + NumberFormat.getInstance().format(getPlayer().getRealCash());
            v2 += "\r\n\r\n#nเมื่อซื้อจะหัก #b#e" + pick.getPrice(grade - 1) + " คะแนน#n#k ต้องการซื้อหรือไม่?";
            if (1 == self.askYesNo(v2)) {
                if (getPlayer().getRealCash() < pick.getPrice(grade - 1)) {
                    self.say("คะแนนไม่เพียงพอสำหรับการซื้อ");
                    return;
                }
                if (1 == target.exchange(pick.getItemID(), 1)) {
                    getPlayer().gainRealCash(-pick.getPrice(grade - 1));
                    // Process purchase
                    getPlayer().updateOneInfo(100161, pick.getIndex() + "_buy_count", String.valueOf(buyCount + 1));
                    if (0 == self.askMenu("#b#i" + pick.getItemID() + "# #z" + pick.getItemID()
                            + "##k ซื้อเรียบร้อยแล้ว\r\n\r\n#b#L0#กลับไปที่รายการไอเท็ม#l")) {
                        displayShop();
                    }
                } else {
                    self.say("กรุณาเพิ่มช่องว่างในกระเป๋าแล้วลองใหม่อีกครั้ง");
                }
            }
        }
    }

    public class ItemEntry {
        private int itemID;
        private int[] price;
        private int[] worldLimit;
        private int index;
        private int gradeLimit;

        public ItemEntry(int itemID, int[] price, int[] worldLimit, int index, int gradeLimit) {
            this.setItemID(itemID);
            this.setPrice(price);
            this.setWorldLimit(worldLimit);
            this.setIndex(index);
            this.setGradeLimit(gradeLimit);
        }

        public int getItemID() {
            return itemID;
        }

        public void setItemID(int itemID) {
            this.itemID = itemID;
        }

        public int getPrice(int index) {
            return price[index];
        }

        public void setPrice(int[] price) {
            this.price = price;
        }

        public int getWorldLimit(int index) {
            return worldLimit[index];
        }

        public void setWorldLimit(int[] worldLimit) {
            this.worldLimit = worldLimit;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getGradeLimit() {
            return gradeLimit;
        }

        public void setGradeLimit(int gradeLimit) {
            this.gradeLimit = gradeLimit;
        }
    }

    public void renewOptionRoyal() {
        // Ganglim server uses Brilliant Light Crystal as material
        int requestItemID = 4031227;

        while (true) {
            // Proceed with reset
            if (target.exchange(requestItemID, -100) > 0) {
                ExtraAbilityGrade grade = getPlayer().getExtraAbilityGrade();
                double rate = ExtraAbilityFactory.getGradeUpRate(grade, ExtraAbilityPayType.Donation);
                boolean legendaryOption = false;
                if (Randomizer.isSuccess((int) (rate * 10), 1000) && grade != ExtraAbilityGrade.Legendary) {
                    // Grade up!
                    legendaryOption = true;
                    grade = ExtraAbilityGrade.getGrade(grade.getGradeID() + 1);
                    getPlayer().send(
                            CField.addPopupSay(9062000, 5000, "#e#rอัพเกรดสู่ระดับ " + grade.getDesc() + " สำเร็จ!#k#n",
                                    ""));
                    getPlayer().setExtraAbilityGrade(grade);
                    if (grade == ExtraAbilityGrade.Legendary) {
                        Center.Broadcast.broadcastMessage(
                                CField.chatMsg(5, "[" + getPlayer().getName()
                                        + "] ได้บรรลุ Extra Ability 'ระดับ Legendary' แล้ว!"));
                    }
                }

                NumberFormat nf = NumberFormat.getInstance();
                int luckyPoint = getPlayer().getOneInfoQuestInteger(787878, "lp");
                boolean enableExtra = getPlayer().getExtraAbilityStats()[getPlayer().getExtraAbilitySlot()][5]
                        .getOption() != ExtraAbilityOption.None;
                if (!enableExtra) {
                    // If extra stat doesn't exist
                    // Grant with certain probability
                    if (grade == ExtraAbilityGrade.Legendary) {
                        // 1% chance to grant at Legendary grade
                        if (Randomizer.isSuccess(1)) {
                            enableExtra = true;
                        }
                    }
                }
                ExtraAbilityStatEntry[] entry = ExtraAbilityFactory.pickMeUpRoyal(grade, luckyPoint,
                        getPlayer().getHgrade(), enableExtra);
                if (luckyPoint >= 30) {
                    luckyPoint -= 30;
                    getPlayer().updateOneInfo(787878, "lp", String.valueOf(luckyPoint));
                }
                getPlayer().checkExtraAbility();
                luckyPoint += 3;
                getPlayer().updateOneInfo(787878, "lp", String.valueOf(luckyPoint));

                String v5 = "#fs11##bExtra Ability#k รีเซ็ตเรียบร้อยแล้ว.#r\r\n\r\n";

                v5 += "#e#kระดับปัจจุบัน : #r" + getPlayer().getExtraAbilityGrade().getDesc() + "#k#n#r\r\n\r\n";
                int index = 0;

                StringBuilder sb = new StringBuilder("Extra Ability Result (");
                sb.append("Account: " + getClient().getAccountName());
                sb.append(", Character: " + getPlayer().getName());
                sb.append(", Options [");
                for (ExtraAbilityStatEntry e : entry) {
                    if (++index == 6) {
                        v5 += "\r\n#k#e[Extra Stat]#n\r\n#r";
                    }
                    sb.append("Grade: ");
                    sb.append(getPlayer().getExtraAbilityGrade().getDesc());
                    sb.append(", ");
                    sb.append("[index:" + index);
                    sb.append("] " + String.format(e.getOption().getDesc(), e.getValue(), "%"));
                    if (index != 6) {
                        sb.append(",");
                    }
                    v5 += "  " + String.format(e.getOption().getDesc(), e.getValue(), "%");
                    v5 += "\r\n";
                }
                sb.append("])");
                LoggingManager.putLog(new EnchantLog(getPlayer(), 2, 0, 0, 14, 0, sb));
                getPlayer().setExtraAbilityStats(entry, getPlayer().getExtraAbilitySlot());
                getPlayer().checkExtraAbility();

                int remain = getPlayer().getItemQuantity(requestItemID, false);
                v5 += "\r\n#k#eมี #z" + requestItemID + "# : " + remain + " ชิ้น";
                v5 += "\r\nLucky Point : ";
                if (luckyPoint >= 30) {
                    v5 += "#b" + luckyPoint + " (Lucky!!)#k";
                } else {
                    v5 += luckyPoint;
                }

                if (legendaryOption) {
                    self.sayOk(v5, ScriptMessageFlag.NoEsc);
                    break;
                } else {
                    /*
                     * if (type == ExtraAbilityPayType.Donation) {
                     * v5 += "\r\n\r\n#b#e1,000 คะแนน#k#nต้องการรีเซ็ตตัวเลือกหรือไม่?";
                     * } else if (type == ExtraAbilityPayType.Promotion) {
                     * v5 += "\r\n\r\n#b#e500 คะแนนโปรโมชั่น#k#nต้องการรีเซ็ตตัวเลือกหรือไม่?";
                     * } else if (type == ExtraAbilityPayType.Meso) {
                     * v5 += "\r\n\r\n#b#e500,000,000 Meso#k#nต้องการรีเซ็ตตัวเลือกหรือไม่?";
                     * }
                     */
                    v5 += "\r\n\r\n#b#e#i" + requestItemID + "# #z" + requestItemID
                            + "##k#nต้องการรีเซ็ตตัวเลือกหรือไม่?";
                    v5 += "\r\n#b#L0#รีเซ็ตอีกครั้ง#l\r\n#L1#ปิดหน้าต่าง#l";
                    if (self.askMenu(v5) == 0) {
                        if (self.askMenu(
                                "#fs11##r#eต้องการรีเซ็ตจริงหรือไม่? การรีเซ็ตจะทำให้ตัวเลือกปัจจุบันหายไป#n#k\r\n\r\n#b#L0#รีเซ็ตอีกครั้ง#l\r\n#L1#ปิดหน้าต่าง#l") == 0) {
                            renewOptionRoyal();
                        }
                        break;
                    }
                    break;
                }
            } else {
                self.say("#fs11##zi" + requestItemID + "# #z" + requestItemID + "#ไม่เพียงพอสำหรับการรีเซ็ต");
                break;
            }
        }
    }

    public void renewOption(ExtraAbilityPayType type) {
        // Return resources
        while (true) {
            if (type == ExtraAbilityPayType.Donation) {
                if (getPlayer().getRealCash() < 1000) {
                    self.say("#bคะแนน#kไม่เพียงพอสำหรับการรีเซ็ต");
                    break;
                }
                getPlayer().gainRealCash(-1000, true);
            } else if (type == ExtraAbilityPayType.Promotion) {
                if (getPlayer().getHongboPoint() < 500) {
                    self.say("#bคะแนนโปรโมชั่น#kไม่เพียงพอสำหรับการรีเซ็ต");
                    break;
                }
                getPlayer().gainHongboPoint(-500, true);
            } else if (type == ExtraAbilityPayType.Meso) {
                if (getPlayer().getMeso() < 500000000L) {
                    self.say("#bMeso#kไม่เพียงพอสำหรับการรีเซ็ต");
                    break;
                }
                getPlayer().gainMeso(-500000000L, true);
            }

            // Proceed with reset
            ExtraAbilityGrade grade = getPlayer().getExtraAbilityGrade();
            if (grade == ExtraAbilityGrade.Legendary) {
                if (type == ExtraAbilityPayType.Meso) {
                    self.say("#bระดับ Legendary#kไม่สามารถใช้ Meso รีเซ็ตได้");
                    return;
                }
            }

            double rate = ExtraAbilityFactory.getGradeUpRate(grade, type);
            if (Randomizer.isSuccess((int) (rate * 10), 1000) && grade != ExtraAbilityGrade.Legendary) {
                // Grade up!
                grade = ExtraAbilityGrade.getGrade(grade.getGradeID() + 1);
                getPlayer().send(
                        CField.addPopupSay(9062000, 5000, "#e#rอัพเกรดสู่ระดับ " + grade.getDesc() + " สำเร็จ!#k#n",
                                ""));
                getPlayer().setExtraAbilityGrade(grade);
                if (grade == ExtraAbilityGrade.Legendary) {
                    Center.Broadcast.broadcastMessage(
                            CField.chatMsg(5,
                                    "[" + getPlayer().getName() + "] ได้บรรลุ Extra Ability 'ระดับ Legendary' แล้ว!"));
                }
            }

            NumberFormat nf = NumberFormat.getInstance();
            int luckyPoint = getPlayer().getOneInfoQuestInteger(787878, "lp");
            ExtraAbilityStatEntry[] entry = ExtraAbilityFactory.pickMeUp(grade, luckyPoint,
                    ExtraAbilityPayType.Donation);
            if (luckyPoint >= 30) {
                luckyPoint -= 30;
                getPlayer().updateOneInfo(787878, "lp", String.valueOf(luckyPoint));
            }
            boolean legendaryOption = false;
            if (grade == ExtraAbilityGrade.Legendary) {
                int result = ExtraAbilityFactory.checkAllMaxValue(entry);
                if (result == 2) {
                    String msg = getPlayer().getName() + " ได้รับ Extra Ability#r\r\n\r\n";
                    for (ExtraAbilityStatEntry e : entry) {
                        msg += String.format(e.getOption().getDesc(), e.getValue(), "%");
                        msg += "\r\n";
                    }
                    msg += "#k\r\nตัวเลือกปรากฏขึ้นแล้ว!";
                    Center.Broadcast.broadcastMessage(CField.addPopupSay(9062000, 5000, msg, ""));
                    legendaryOption = true;
                } else if (result == 1) {
                    String msg = "Extra Ability#r\r\n\r\n";
                    for (ExtraAbilityStatEntry e : entry) {
                        msg += String.format(e.getOption().getDesc(), e.getValue(), "%");
                        msg += "\r\n";
                    }
                    msg += "#k\r\nตัวเลือกปรากฏขึ้นแล้ว!";
                    getPlayer().send(CField.addPopupSay(9062000, 5000, msg, ""));
                    legendaryOption = true;
                }
            }
            getPlayer().checkExtraAbility();
            if (type == ExtraAbilityPayType.Donation) {
                luckyPoint += 3;
            } else if (type == ExtraAbilityPayType.Promotion) {
                luckyPoint += 2;
            } else if (type == ExtraAbilityPayType.Meso) {
                luckyPoint += 1;
            }
            getPlayer().updateOneInfo(787878, "lp", String.valueOf(luckyPoint));

            String v5 = "#bExtra Ability#k รีเซ็ตเรียบร้อยแล้ว.#r\r\n\r\n";

            v5 += "#e#kระดับปัจจุบัน : #r" + getPlayer().getExtraAbilityGrade().getDesc() + "#k#n#r\r\n\r\n";

            StringBuilder sb = new StringBuilder("Extra Ability 결 (");
            sb.append("Account: " + getClient().getAccountName());
            sb.append(", Character: " + getPlayer().getName());
            sb.append(", Options [");

            int index = 0;
            for (ExtraAbilityStatEntry e : entry) {
                v5 += "  " + String.format(e.getOption().getDesc(), e.getValue(), "%");
                v5 += "\r\n";

                index++;
                sb.append("Grade: ");
                sb.append(getPlayer().getExtraAbilityGrade().getDesc());
                sb.append(", ");
                sb.append("[index:" + index);
                sb.append("] " + String.format(e.getOption().getDesc(), e.getValue(), "%"));
                if (index != 2) {
                    sb.append(",");
                }
            }
            sb.append("])");
            LoggingManager.putLog(new EnchantLog(getPlayer(), 2, 0, 0, 14, 0, sb));

            getPlayer().setExtraAbilityStats(entry, getPlayer().getExtraAbilitySlot());
            getPlayer().checkExtraAbility();

            if (type == ExtraAbilityPayType.Donation) {
                v5 += "\r\n#k#eคะแนนที่มี : " + nf.format(getPlayer().getRealCash());
            } else if (type == ExtraAbilityPayType.Promotion) {
                v5 += "\r\n#k#eคะแนนที่มี : " + nf.format(getPlayer().getHongboPoint());
            } else if (type == ExtraAbilityPayType.Meso) {
                v5 += "\r\n#k#eMeso ที่มี : " + nf.format(getPlayer().getMeso());
            }
            v5 += "\r\nLucky Point : ";
            if (luckyPoint >= 30) {
                v5 += "#b" + luckyPoint + " (Lucky!!)#k";
            } else {
                v5 += luckyPoint;
            }

            if (legendaryOption) {
                self.sayOk(v5, ScriptMessageFlag.NoEsc);
                break;
            } else {
                if (type == ExtraAbilityPayType.Donation) {
                    v5 += "\r\n\r\n#b#e1,000 คะแนน#k#nต้องการรีเซ็ตตัวเลือกหรือไม่?";
                } else if (type == ExtraAbilityPayType.Promotion) {
                    v5 += "\r\n\r\n#b#e500 คะแนนโปรโมชั่น#k#nต้องการรีเซ็ตตัวเลือกหรือไม่?";
                } else if (type == ExtraAbilityPayType.Meso) {
                    v5 += "\r\n\r\n#b#e500,000,000 Meso#k#nต้องการรีเซ็ตตัวเลือกหรือไม่?";
                }
                v5 += "\r\n#b#L0#รีเซ็ตอีกครั้ง.#l\r\n#L1#ปิดหน้าต่าง.#l";
                if (self.askMenu(v5) == 0) {
                    if (self.askMenu(
                            "#fs11##r#eต้องการรีเซ็ตจริงหรือไม่? การรีเซ็ตจะทำให้ตัวเลือกปัจจุบันหายไป.#n#k\r\n\r\n#b#L0#รีเซ็ตอีกครั้ง.#l\r\n#L1#ปิดหน้าต่าง.#l") == 0) {
                        renewOption(type);
                    }
                    break;
                }
                break;
            }
        }

    }

    public void test() {
        initNPC(MapleLifeFactory.getNPC(9010017));
        NumberFormat nf = NumberFormat.getInstance();
        String v0 = "#fs11##e<Extra Ability>#n\r\nปัจจุบัน #b#h0##k ได้รับ ข้อมูล Extra Ability มีดังนี้.\r\n\r\n";
        v0 += "#eกำลังใช้งาน Preset : #r" + getPlayer().getExtraAbilitySlot() + "#k\r\n";
        v0 += "ระดับปัจจุบัน : #r" + getPlayer().getExtraAbilityGrade().getDesc() + "#k#n#r\r\n\r\n";
        int index = 0;
        for (ExtraAbilityStatEntry entry : getPlayer().getExtraAbilityStats()[getPlayer().getExtraAbilitySlot()]) {
            if (++index == 6) {
                v0 += "\r\n#k#e[Extra Stat]#n#r\r\n";
            }
            v0 += "  " + String.format(entry.getOption().getDesc(), entry.getValue(), "%");
            v0 += "\r\n";
        }
        v0 += "\r\n#b#L0#ปัจจุบัน Preset ตัวเลือก รีเซ็ต한다.#l\r\n";
        v0 += "#b#L1#เปลี่ยน Preset.";
        boolean presetLock = getPlayer().getOneInfoQuestInteger(787878, "e_preset_open") == 0;
        if (presetLock) {
            v0 += " #e(ล็อก)#n";
        }
        v0 += "#l\r\n#b#L2#เรียนรู้เกี่ยวกับ Extra Ability.#l\r\n";
        int v1 = self.askMenu(v0);
        int requestItemID = 4031227;
        switch (v1) {
            case 0: { // รีเซ็ตตัวเลือก
                if (DBConfig.isGanglim) {
                    // 강림 Brilliant Light Crystal 재료임
                    int quantity = 100;
                    String v2 = "#fs11#";
                    boolean skipDesc = getPlayer().getOneInfoQuestInteger(787878, "skip_desc") == 1;
                    if (skipDesc) {
                        v2 = "#fs11##e<Extra Ability>#n\r\nปัจจุบัน #e#b" + getPlayer().getExtraAbilitySlot()
                                + "#k#n Preset กำลังใช้งาน และมีตัวเลือกดังนี้.\r\n";
                    } else {
                        v2 = "#fs11##e<Extra Ability>#n\r\nปัจจุบัน #e#b" + getPlayer().getExtraAbilitySlot()
                                + "#k#n Preset กำลังใช้งาน และมีตัวเลือกดังนี้.\r\n#rLucky Point ที่ได้รับ ตัวเลือก และโอกาสอัพเกรด#k รายละเอียดเพิ่มเติม #b#eเรียนรู้เกี่ยวกับ Extra Ability.#n#kโปรดดูที่.\r\n\r\n";
                    }
                    v2 += "#eระดับปัจจุบัน : #r" + getPlayer().getExtraAbilityGrade().getDesc() + "#k#n#r\r\n\r\n";
                    index = 0;
                    for (ExtraAbilityStatEntry entry : getPlayer().getExtraAbilityStats()[getPlayer()
                            .getExtraAbilitySlot()]) {
                        if (++index == 6) {
                            v2 += "\r\n#k#e[Extra Stat]#n\r\n#r";
                        }
                        v2 += "  " + String.format(entry.getOption().getDesc(), entry.getValue(), "%");
                        v2 += "\r\n";
                    }
                    v2 += "\r\n#b#L0##e#z" + requestItemID + "##nใช้รีเซ็ตตัวเลือกปัจจุบัน.#l\r\n";
                    if (skipDesc) {
                        v2 += "#L1#จะแสดงคำอธิบายด้านบนเมื่อเสริมแรง.#l\r\n";
                    } else {
                        v2 += "#L1#จะไม่แสดงคำอธิบายด้านบนเมื่อเสริมแรง.#l\r\n";
                    }
                    int v3 = self.askMenu(v2);
                    switch (v3) {
                        case 0: { // Brilliant Light Crystal
                            String v4 = "#fs11##e<Extra Ability>#n\r\n#b#z" + requestItemID + "# " + quantity
                                    + "ชิ้น#kต้องการรีเซ็ต Extra Ability หรือไม่?\r\n\r\n";

                            v4 += "#eระดับปัจจุบัน : #r" + getPlayer().getExtraAbilityGrade().getDesc() + "#k#n#r\r\n\r\n";
                            index = 0;
                            for (ExtraAbilityStatEntry entry : getPlayer().getExtraAbilityStats()[getPlayer()
                                    .getExtraAbilitySlot()]) {
                                if (++index == 6) {
                                    v4 += "\r\n#k#e[Extra Stat]#n\r\n#r";
                                }
                                v4 += "  " + String.format(entry.getOption().getDesc(), entry.getValue(), "%");
                                v4 += "\r\n";
                            }

                            int luckyPoint = getPlayer().getOneInfoQuestInteger(787878, "lp");
                            int q = getPlayer().getItemQuantity(requestItemID, false);
                            v4 += "\r\n#k#eมี #z" + requestItemID + "# : " + q + "ชิ้น";
                            v4 += "\r\nLucky Point : ";
                            if (luckyPoint >= 30) {
                                v4 += "#b" + luckyPoint + " (Lucky!!)#k";
                            } else {
                                v4 += luckyPoint;
                            }
                            if (self.askYesNo(v4) == 1) {
                                if (self.askYesNo("#fs11##r#eต้องการรีเซ็ตจริงหรือไม่? การรีเซ็ตจะทำให้ตัวเลือกปัจจุบันหายไป.#n#k") == 1) {
                                    renewOptionRoyal();
                                    return;
                                }
                            }
                            break;
                        }
                        case 1: { // คำอธิบายด้านบน on/off
                            if (skipDesc) {
                                self.say("#fs11##e#bแสดงคำอธิบายด้านบนเมื่อเสริมแรง#k#nตั้งค่าเรียบร้อยแล้ว.");
                                getPlayer().updateOneInfo(787878, "skip_desc", "0");
                            } else {
                                self.say("#fs11##e#bไม่แสดงคำอธิบายด้านบนเมื่อเสริมแรง#k#nตั้งค่าเรียบร้อยแล้ว.");
                                getPlayer().updateOneInfo(787878, "skip_desc", "1");
                            }
                            break;
                        }
                    }
                } else {
                    String v2 = "";
                    boolean skipDesc = getPlayer().getOneInfoQuestInteger(787878, "skip_desc") == 1;
                    if (skipDesc) {
                        v2 = "#e<Extra Ability>#n\r\nปัจจุบัน #e#b" + getPlayer().getExtraAbilitySlot()
                                + "#k#n Preset กำลังใช้งาน และมีตัวเลือกดังนี้.\r\n";
                    } else {
                        v2 = "#e<Extra Ability>#n\r\nปัจจุบัน #e#b" + getPlayer().getExtraAbilitySlot()
                                + "#k#n Preset กำลังใช้งาน และมีตัวเลือกดังนี้. #b3 ประเภท#k ตัวเลือก รีเซ็ตสามารถ , 종류별 #rLucky Point ที่ได้รับ ตัวเลือก และโอกาสอัพเกรด#kจะแตกต่างกัน รายละเอียดเพิ่มเติม #b#eเรียนรู้เกี่ยวกับ Extra Ability.#n#kโปรดดูที่.\r\n\r\n";
                    }
                    v2 += "#eระดับปัจจุบัน : #r" + getPlayer().getExtraAbilityGrade().getDesc() + "#k#n#r\r\n\r\n";
                    for (ExtraAbilityStatEntry entry : getPlayer().getExtraAbilityStats()[getPlayer()
                            .getExtraAbilitySlot()]) {
                        v2 += "  " + String.format(entry.getOption().getDesc(), entry.getValue(), "%");
                        v2 += "\r\n";
                    }
                    v2 += "\r\n#b#L0##eคะแนน#nใช้รีเซ็ตตัวเลือกปัจจุบัน.#l\r\n";
                    v2 += "#L1##eคะแนนโปรโมชั่น#nใช้รีเซ็ตตัวเลือกปัจจุบัน.#l\r\n";
                    v2 += "#L2##eMeso#nใช้รีเซ็ตตัวเลือกปัจจุบัน.#l\r\n";
                    if (skipDesc) {
                        v2 += "#L3#จะแสดงคำอธิบายด้านบนเมื่อเสริมแรง.#l\r\n";
                    } else {
                        v2 += "#L3#จะไม่แสดงคำอธิบายด้านบนเมื่อเสริมแรง.#l\r\n";
                    }
                    int v3 = self.askMenu(v2);
                    switch (v3) {
                        case 0: { // คะแนน
                            String v4 = "#e<Extra Ability>#n\r\n#b1,000 คะแนน#kต้องการรีเซ็ต Extra Ability หรือไม่?\r\n\r\n";

                            v4 += "#eระดับปัจจุบัน : #r" + getPlayer().getExtraAbilityGrade().getDesc() + "#k#n#r\r\n\r\n";
                            for (ExtraAbilityStatEntry entry : getPlayer().getExtraAbilityStats()[getPlayer()
                                    .getExtraAbilitySlot()]) {
                                v4 += "  " + String.format(entry.getOption().getDesc(), entry.getValue(), "%");
                                v4 += "\r\n";
                            }

                            int luckyPoint = getPlayer().getOneInfoQuestInteger(787878, "lp");
                            v4 += "\r\n#k#eคะแนนที่มี : " + nf.format(getPlayer().getRealCash());
                            v4 += "\r\nLucky Point : ";
                            if (luckyPoint >= 30) {
                                v4 += "#b" + luckyPoint + " (Lucky!!)#k";
                            } else {
                                v4 += luckyPoint;
                            }
                            if (self.askYesNo(v4) == 1) {
                                if (self.askYesNo("#fs11##r#eต้องการรีเซ็ตจริงหรือไม่? การรีเซ็ตจะทำให้ตัวเลือกปัจจุบันหายไป.#n#k") == 1) {
                                    renewOption(ExtraAbilityPayType.Donation);
                                    return;
                                }
                            }
                            break;
                        }
                        case 1: { // คะแนนโปรโมชั่น
                            String v4 = "#e<Extra Ability>#n\r\n#b500 คะแนนโปรโมชั่น#kต้องการรีเซ็ต Extra Ability หรือไม่?\r\n\r\n";

                            v4 += "#eระดับปัจจุบัน : #r" + getPlayer().getExtraAbilityGrade().getDesc() + "#k#n#r\r\n\r\n";
                            for (ExtraAbilityStatEntry entry : getPlayer().getExtraAbilityStats()[getPlayer()
                                    .getExtraAbilitySlot()]) {
                                v4 += "  " + String.format(entry.getOption().getDesc(), entry.getValue(), "%");
                                v4 += "\r\n";
                            }

                            int luckyPoint = getPlayer().getOneInfoQuestInteger(787878, "lp");
                            v4 += "\r\n#k#eคะแนนที่มี : " + nf.format(getPlayer().getHongboPoint());
                            v4 += "\r\nLucky Point : ";
                            if (luckyPoint >= 30) {
                                v4 += "#b" + luckyPoint + " (Lucky!!)#k";
                            } else {
                                v4 += luckyPoint;
                            }
                            if (self.askYesNo(v4) == 1) {
                                if (self.askYesNo("#fs11##r#eต้องการรีเซ็ตจริงหรือไม่? การรีเซ็ตจะทำให้ตัวเลือกปัจจุบันหายไป.#n#k") == 1) {
                                    renewOption(ExtraAbilityPayType.Promotion);
                                    return;
                                }
                            }
                            break;
                        }
                        case 2: { // Meso
                            String v4 = "#e<Extra Ability>#n\r\n#b500,000,000 Meso#kต้องการรีเซ็ต Extra Ability หรือไม่?\r\n\r\n";

                            v4 += "#eระดับปัจจุบัน : #r" + getPlayer().getExtraAbilityGrade().getDesc() + "#k#n#r\r\n\r\n";
                            for (ExtraAbilityStatEntry entry : getPlayer().getExtraAbilityStats()[getPlayer()
                                    .getExtraAbilitySlot()]) {
                                v4 += "  " + String.format(entry.getOption().getDesc(), entry.getValue(), "%");
                                v4 += "\r\n";
                            }

                            int luckyPoint = getPlayer().getOneInfoQuestInteger(787878, "lp");
                            v4 += "\r\n#k#eMeso ที่มี : " + nf.format(getPlayer().getMeso());
                            v4 += "\r\nLucky Point : ";
                            if (luckyPoint >= 30) {
                                v4 += "#b" + luckyPoint + " (Lucky!!)#k";
                            } else {
                                v4 += luckyPoint;
                            }
                            if (self.askYesNo(v4) == 1) {
                                if (getPlayer().getExtraAbilityGrade() == ExtraAbilityGrade.Legendary) {
                                    self.say("#bระดับ Legendary#k Meso ใช้ รีเซ็ตสามารถ ไม่มี.");
                                    return; // Legendary ไม่สามารถใช้ Meso
                                }
                                if (self.askYesNo("#fs11##r#eต้องการรีเซ็ตจริงหรือไม่? การรีเซ็ตจะทำให้ตัวเลือกปัจจุบันหายไป.#n#k") == 1) {
                                    renewOption(ExtraAbilityPayType.Meso);
                                    return;
                                }
                            }
                            break;
                        }
                        case 3: { // คำอธิบายด้านบน on/off
                            if (skipDesc) {
                                self.say("#e#bแสดงคำอธิบายด้านบนเมื่อเสริมแรง#k#nตั้งค่าเรียบร้อยแล้ว.");
                                getPlayer().updateOneInfo(787878, "skip_desc", "0");
                            } else {
                                self.say("#e#bไม่แสดงคำอธิบายด้านบนเมื่อเสริมแรง#k#nตั้งค่าเรียบร้อยแล้ว.");
                                getPlayer().updateOneInfo(787878, "skip_desc", "1");
                            }
                            break;
                        }
                    }
                }
                break;
            }
            case 1: { // เปลี่ยน Preset
                if (presetLock) {
                    String v2 = "#fs11##e<Extra Ability>#n\r\nปัจจุบัน Extra Ability Preset ถูกล็อกอยู่. #bแคช#k ใช้ ล็อก ปลดล็อกสามารถ .#b\r\n\r\n";
                    if (DBConfig.isGanglim) {
                        v2 += "#L0##e50,000 แคช#nใช้ปลดล็อก.#l\r\n";
                        v2 += "#L1#ปิดหน้าต่าง.#l\r\n";
                        int v3 = self.askMenu(v2);
                        if (v3 == 1) {
                            return;
                        }
                        switch (v3) {
                            case 0: {
                                if (1 == self.askYesNo(
                                        "#fs11##e<Extra Ability>#n\r\n#b50,000 แคช#kต้องการใช้ปลดล็อก Extra Ability Preset หรือไม่?\r\n\r\n#eคะแนนที่มี : "
                                                + nf.format(getPlayer().getCashPoint()))) {
                                    if (getPlayer().getCashPoint() < 50000) {
                                        self.say("แคชไม่เพียงพอสำหรับการปลดล็อก.");
                                        return;
                                    }
                                    getPlayer().gainCashPoint(-50000);
                                    getPlayer().updateOneInfo(787878, "e_preset_open", "1");
                                    self.say("#b50,000 แคช#kใช้ปลดล็อก Extra Ability Preset เรียบร้อยแล้ว.");
                                    return;
                                }
                                break;
                            }
                        }
                    } else {
                        v2 += "#L0#50,000 #eคะแนน#nใช้ปลดล็อก.#l\r\n";
                        v2 += "#L1#50,000 #eคะแนนโปรโมชั่น#nใช้ปลดล็อก.#l\r\n";
                        v2 += "#L2#ปิดหน้าต่าง.#l\r\n";
                        int v3 = self.askMenu(v2);
                        if (v3 == 2) {
                            return;
                        }
                        switch (v3) {
                            case 0: {
                                if (1 == self.askYesNo(
                                        "#e<Extra Ability>#n\r\n#b50,000 คะแนน#kต้องการใช้ปลดล็อก Extra Ability Preset หรือไม่?\r\n\r\n#eคะแนนที่มี : "
                                                + nf.format(getPlayer().getRealCash()))) {
                                    if (getPlayer().getRealCash() < 50000) {
                                        self.say("คะแนน ไม่เพียงพอ ไม่สามารถปลดล็อกได้.");
                                        return;
                                    }
                                    getPlayer().gainRealCash(-50000, true);
                                    getPlayer().updateOneInfo(787878, "e_preset_open", "1");
                                    self.say("#b50,000 คะแนน#kใช้ปลดล็อก Extra Ability Preset เรียบร้อยแล้ว.");
                                    return;
                                }
                                break;
                            }
                            case 1: {
                                if (1 == self.askYesNo(
                                        "#e<Extra Ability>#n\r\n#b50,000 คะแนน#kต้องการใช้ปลดล็อก Extra Ability Preset หรือไม่?\r\n\r\n#eคะแนนที่มี : "
                                                + nf.format(getPlayer().getHongboPoint()))) {
                                    if (getPlayer().getHongboPoint() < 50000) {
                                        self.say("คะแนนโปรโมชั่น ไม่เพียงพอ ไม่สามารถปลดล็อกได้.");
                                        return;
                                    }
                                    getPlayer().gainHongboPoint(-50000, true);
                                    getPlayer().updateOneInfo(787878, "e_preset_open", "1");
                                    self.say("#b50,000 คะแนนโปรโมชั่น#kใช้ปลดล็อก Extra Ability Preset เรียบร้อยแล้ว.");
                                    return;
                                }
                                break;
                            }
                        }
                    }
                } else {
                    int preset = getPlayer().getExtraAbilitySlot();
                    long meso = 3000000000L;
                    if (DBConfig.isGanglim) {
                        meso = 10000000000L;
                    }
                    String v2 = "#fs11##e<Extra Ability>\r\n\r\nกำลังใช้งาน Preset : #r" + preset + "#k\r\n";
                    v2 += "#eMeso ที่มี : #r" + nf.format(getPlayer().getMeso()) + "#k#n\r\n\r\n";
                    v2 += "#b" + nf.format(meso) + "Meso#k ใช้ #b" + (preset ^ 1) + " #k ต้องการเปลี่ยนเป็น Preset หรือไม่?\r\n\r\n";
                    if (1 == self.askYesNo(v2)) {
                        if (getPlayer().getMeso() < meso) {
                            self.say("Meso ไม่เพียงพอ ไม่สามารถเปลี่ยน Preset ได้.");
                            return;
                        }
                        getPlayer().gainMeso(-meso, true);
                        getPlayer().setExtraAbilitySlot((preset ^ 1));
                        getPlayer().checkExtraAbility();
                        String v3 = "#fs11##e<Extra Ability>#n\r\n\r\n#b#e" + getPlayer().getExtraAbilitySlot()
                                + " Preset#n#k으 เปลี่ยนแล้ว.#r\r\n\r\n";
                        for (ExtraAbilityStatEntry entry : getPlayer().getExtraAbilityStats()[getPlayer()
                                .getExtraAbilitySlot()]) {
                            v3 += "  " + String.format(entry.getOption().getDesc(), entry.getValue(), "%");
                            v3 += "\r\n";
                        }
                        self.say(v3);
                    }
                }
                break;
            }
            case 2: { // 어빌 대해
                if (DBConfig.isGanglim) {
                    self.say(
                            "#fs11##bExtra Ability ระบบ#k ตัวละคร เพิ่มเติม적인 성장 수단.\r\n\r\nExtra Ability #e#r여섯 ชิ้น สุ่ม ตัวเลือก#k#n 부여하 ห้อง식으, 재화 ใช้ 부여되 ตัวเลือก 종류 เปลี่ยนสามารถ .\r\n\r\nตัวเลือก เปลี่ยน #e#bBrilliant Light Crystal#k#n 재화 이용 เป็นไปได้.");
                    self.say(
                            "#fs11#네 째 줄 ตัวเลือก #eVVIP Classic#n ระดับ 등장,\r\n다섯 째 줄 ตัวเลือก #eMVP Prime#n ระดับ 등장.\r\n여섯 째 엑스트라 ตัวเลือก #b'레ก่อน드리' ระดับ#k รีเซ็ต시\r\n#e1% โอกาส#n 등장, 한  출현 หลัง  ต่อไป해서 รีเซ็ต.");
                    String v2 = "#fs11#ดู 자세한 ข้อมูล ด้านล่าง เลือก지 통해 ยืนยัน하실 수 .\r\n\r\n#b#L0#Extra Ability Preset 관해 알고 싶.#l\r\n#b#L1#Extra Ability ระดับ별 ตัวเลือก 알고 싶.#l\r\n#b#L2#Extra Ability ระดับ별 โอกาส 알고 싶.#l\r\n#L3#Extra Ability Lucky Point 대해 알고 싶.#l";
                    int v3 = self.askMenu(v2);
                    switch (v3) {
                        case 0: {
                            self.say(
                                    "#fs11#Extra Ability Preset เริ่มต้น 1ชิ้น Preset พื้นฐาน으 제공, #bแคช#k #e50,000#n ใช้ 1칸 추 확장สามารถ .\r\n\r\nแต่ละ Preset별 ต่างกัน ตัวเลือก ตั้งค่า 이용สามารถ , Preset เปลี่ยน할 때마다 #e100억 Meso#n ใช้.\r\n\r\n#e#r※ ตัวเลือก เปลี่ยน ปัจจุบัน ใช้ 중인 Preset 기준으 ใช้งาน되니 이용 สัปดาห์의하시기 โปรด.");
                            break;
                        }
                        case 1: {
                            self.say(
                                    "#fs11##e[ 엑스트라 ]#n\r\n\r\n#b- 재ใช้ รอ시ระหว่าง 1~2วินาที ลด\r\n- 부활 시 무적 시ระหว่าง 2~4วินาที 증\r\n- มอนสเตอร์ ชิ้น체수 1.5เท่า 증\r\n- สุดท้าย Damage 5~20%\r\n- EXP ได้รับ량 30~50%\r\n- Meso ได้รับ량 30~50%\r\n- ไอเท็ม ดรอป률 30~50%\r\n- สถานะ 이상 ภายใน성");
                            self.say(
                                    "#fs11##e[ 레ก่อน드리 ]#n\r\n\r\n#b- 재ใช้ รอ시ระหว่าง 1~2วินาที ลด\r\n- EXP ได้รับ량 30~50%\r\n- ไอเท็ม ดรอป률 20~30%\r\n- Meso ได้รับ량 20~30%\r\n- บอสมอนสเตอร์ โจมตี시 Damage 30~40%\r\n- มอนสเตอร์ ป้องกัน력 무시 30~40%\r\n- Critical โอกาส 12%\r\n- โจมตี력/마력 12~16%\r\n- โจมตี력/마력 +100~150\r\n- 올Stat +18~25%\r\n- 올Stat +200~300\r\n- HP +15~18%");
                            self.say(
                                    "#fs11##e[ Unique ]#n\r\n\r\n#b- EXP ได้รับ량 10~20%\r\n- ไอเท็ม ดรอป률 10~20%\r\n- Meso ได้รับ량 10~20%\r\n- บอสมอนสเตอร์ โจมตี시 Damage 20~30%\r\n- มอนสเตอร์ ป้องกัน력 무시 20~30%\r\n- Critical โอกาส 9~12%\r\n- โจมตี력/마력 9~12%\r\n- โจมตี력/마력 +80~100\r\n- 올Stat +15~21%\r\n- 올Stat +100~150\r\n- HP +12~15%");
                            self.say(
                                    "#fs11##e[ Epic ]#n\r\n\r\n#b- บอสมอนสเตอร์ โจมตี시 Damage 10~20%\r\n- มอนสเตอร์ ป้องกัน력 무시 8~12%\r\n- Critical โอกาส 6~9%\r\n- โจมตี력/마력 6~9%\r\n- โจมตี력/마력 +50~80\r\n- 올Stat +12~15%\r\n- 올Stat +80~100\r\n- HP +9~12%");
                            self.say(
                                    "#fs11##e[ Rare ]#n\r\n\r\n#b- 피격 시 받 Damage 60~80%\r\n- STR, DEX, INT, LUK +80~100\r\n- 올Stat +50~80\r\n- HP +6~9%\r\n- HP +3000~5000\r\n- โจมตี력/마력 +3~6%\r\n- โจมตี력/마력 +30~50");
                            self.say("#fs11##bทั้งหมด 줄#k ตัวเลือก ระดับปัจจุบัน 맞 ตัวเลือก 등장, ตัวเลือก 등장 โอกาส ต่างกัน ตัวเลือก 존재.");
                            break;
                        }
                        case 2: {
                            self.say(
                                    "#fs11##e[Rare → ระดับ Epic ขึ้น โอกาส]#n\r\n#b15% โอกาส 증\r\n\r\n\r\n#k#e[Epic → Unique 등긍 삽승 โอกาส]#n\r\n#b3.8% โอกาส 증\r\n\r\n\r\n#k#e[Unique → ระดับ Legendary ขึ้น โอกาส]#n\r\n#b1% โอกาส 증\r\n#e[엑스트라 ระดับ 등장 โอกาส]#n\r\n#b'레ก่อน드리' ระดับ 1% โอกาส 등장");
                            break;
                        }
                        case 3: {
                            self.say("#fs11#Lucky Point ใช้한 재화 따라 ชา등 ได้รับ ด้านล่าง เหมือนกัน ได้รับสามารถ .\r\n\r\n#e#r#z" + requestItemID
                                    + "# ใช้ 시 3คะแนน");
                            self.say(
                                    "#fs11#เสริมแรง 시 얻 Lucky Point #e#b30 คะแนน#n#k 넘 시 อัตโนมัติ으 30คะแนน หัก, 1~5줄 해당 ตัวเลือก สูงสุด 수치 등장.\r\n\r\n#e5줄 럭키 โอกาส 10%\r\n4줄 럭키 โอกาส 10%\r\n3줄 럭키 โอกาส 15%\r\n2줄 럭키 โอกาส 25%\r\n1줄 럭키 โอกาส 40% .");
                            break;
                        }
                    }
                } else {
                    self.say(
                            "#fs11##bExtra Ability ระบบ#k ตัวละคร เพิ่มเติม적인 성장 수단.\r\n\r\nExtra Ability #e#r세 ชิ้น สุ่ม ตัวเลือก#k#n 부여하 ห้อง식으, 재화 ใช้ 부여되 ตัวเลือก 종류 เปลี่ยนสามารถ .\r\n\r\nตัวเลือก เปลี่ยน #e#bคะแนน, คะแนนโปรโมชั่น, Meso#k#n 등 재화 이용 เป็นไปได้, ใช้하 재화 따라 등장하 ตัวเลือก สูงสุด ระดับ และ 수치 ชา 발생.");
                    self.say(
                            "#fs11#แต่ละ 재화별 ตัวเลือก เปลี่ยน ใช้하 비용 ด้านล่าง เหมือนกัน.\r\n\r\n#rคะแนน : 1,000\r\nคะแนนโปรโมชั่น : 500\r\nMeso : 500,000,000");
                    String v2 = "#fs11#ดู 자세한 ข้อมูล ด้านล่าง เลือก지 통해 ยืนยัน하실 수 .\r\n\r\n#b#L0#Extra Ability Preset 관해 알고 싶.#l\r\n#b#L1#Extra Ability ระดับ별 ตัวเลือก 알고 싶.#l\r\n#b#L2#Extra Ability ระดับ별 โอกาส 알고 싶.#l\r\n#L3#Extra Ability Lucky Point 대해 알고 싶.#l";
                    int v3 = self.askMenu(v2);
                    switch (v3) {
                        case 0: {
                            self.say(
                                    "#fs11#Extra Ability Preset เริ่มต้น 1ชิ้น Preset พื้นฐาน으 제공, #bคะแนน#k และ #bคะแนนโปรโมชั่น#k #e50,000#n ใช้ 1칸 추 확장สามารถ .\r\n\r\nแต่ละ Preset별 ต่างกัน ตัวเลือก ตั้งค่า 이용สามารถ , Preset เปลี่ยน할 때마다 #e30억 Meso#n ใช้.\r\n\r\n#e#r※ ตัวเลือก เปลี่ยน ปัจจุบัน ใช้ 중인 Preset 기준으 ใช้งาน되니 이용 สัปดาห์의하시기 โปรด.");
                            break;
                        }
                        case 1: {
                            self.say(
                                    "#fs11##e[레ก่อน드리]#n\r\n\r\n#b- EXP 10~20%\r\n- ไอเท็ม ดรอป률 10~20%\r\n- Meso ได้รับ량 10~20%\r\n- 재ใช้ รอ시ระหว่าง 1~2วินาที ลด\r\n- Critical Damage 5~8%\r\n- โจมตี력/마력 9~12%\r\n- บอส โจมตี 시 Damage 20~30%\r\n- มอนสเตอร์ ป้องกัน력 무시 20~30%\r\n- Critical โอกาส 9~12%\r\n- 올Stat +15~21%\r\n- hp +12~15%\r\n- โจมตี력/마력 +80~100\r\n- 올Stat +100~150");
                            self.say(
                                    "#fs11##e[ Unique ]#n\r\n\r\n#b- บอส โจมตี 시 Damage 10~20%\r\n- มอนสเตอร์ ป้องกัน력 무시 8~12%\r\n- Critical โอกาส 6~9%\r\n- โจมตี력/마력 6~9%\r\n- 올Stat +12~15%\r\n- hp +9~12%\r\n- โจมตี력/마력 +50~80\r\n- 올Stat +80~100");
                            self.say(
                                    "#fs11##e[ Epic ]#n\r\n\r\n#b- โจมตี력/마력 3~6%\r\n- 올Stat +9~12%\r\n- hp +6~9%\r\n- 피격 시 받 Damage 60~80%\r\n- str, dex, int, luk +80~100\r\n- 올Stat +50~80\r\n- hp +3000~5000\r\n- โจมตี력/마력 +30~50");
                            self.say(
                                    "#fs11##e[ Rare ]#n\r\n\r\n#b- 피격 시 받 Damage 40~60%\r\n- str, dex, int, luk +50~80\r\n- 올Stat +20~50\r\n- hp +1000~2000\r\n- โจมตี력/마력 +10~30");
                            self.say(
                                    "#fs11##b첫 째 줄#k #eระดับปัจจุบัน ตัวเลือก#n 등장, #b두 째 / 세 째 줄#k #eระดับปัจจุบันดู 한 단계 ด้านล่าง ตัวเลือก#n 출현, ใช้ 한 재화 따라 이탈 ตัวเลือก 등장สามารถ .");
                            self.say(
                                    "#fs11##b두 째 줄 ตัวเลือก#k #eคะแนน และ คะแนนโปรโมชั่น#n ใช้ 시 #r30% โอกาส#k #eระดับปัจจุบัน ตัวเลือก#n 등장, #b세 째 줄 ตัวเลือก#k #eคะแนน#n ใช้ 시 #r10% โอกาส#k #eระดับปัจจุบัน ตัวเลือก#n 등장.");
                            break;
                        }
                        case 2: {
                            self.say(
                                    "#fs11##e[Rare → ระดับ Epic ขึ้น โอกาส]#n\r\n#bคะแนน : 15%\r\nคะแนนโปรโมชั่น : 10%\r\nMeso : 5%\r\n\r\n\r\n#k#e[Epic → Unique 등긍 삽승 โอกาส]#n\r\n#bคะแนน : 3.8%\r\nคะแนนโปรโมชั่น : 2.5%\r\nMeso : 0.9%\r\n\r\n\r\n#k#e[Unique → ระดับ Legendary ขึ้น โอกาส]#n\r\n#bคะแนน : 1%\r\nคะแนนโปรโมชั่น : 0.7%\r\nMeso : 불");
                            break;
                        }
                        case 3: {
                            self.say(
                                    "#fs11#Lucky Point ใช้한 재화 따라 ชา등 ได้รับ ด้านล่าง เหมือนกัน ได้รับสามารถ .\r\n\r\n#e#rMeso ใช้ 시 : 1คะแนน\r\nคะแนนโปรโมชั่น ใช้ 시 : 2คะแนน\r\nคะแนน ใช้ 시 : 3คะแนน");
                            self.say(
                                    "#fs11#เสริมแรง 시 얻 Lucky Point #e#b30 คะแนน#n#k 넘 시 อัตโนมัติ으 30คะแนน หัก, 1~3줄 해당 ตัวเลือก สูงสุด 수치 등장.\r\n\r\n#e3줄 럭키 โอกาส 20%\r\n2줄 럭키 โอกาส 30%\r\n1줄 럭키 โอกาส 50% .");
                            break;
                        }
                    }
                }
                break;
            }
        }
    }

    int[] equipList = new int[] {
            1012643, 1022289, 1132311, 1162079, 1113307, 1122431, 1032314, 1182282, 1190544
    };
    int[] baseEquipList = new int[] {
            1012632, 1022278, 1132308, 1162083, 1113306, 1122430, 1032316, 1182285, 1190555
    };
    int[] consumeList = new int[] {
            2435873, 2435874, 2435875, 2435876
    };
    int[] consumePrice = new int[] {
            120, 220, 450, 650
    };

    public void npc_2570100() {
        initNPC(MapleLifeFactory.getNPC(2570100));
        NumberFormat nf = NumberFormat.getNumberInstance();
        String v0 = "구경해 보겠ฉัน. ขวา리 발견한 알 수 없 기운 느껴지 ไอเท็ม. …자네 가지고 있 เขา것에서 비슷한 기운 느껴지는군. 괜찮다면 ภายใน 가진 ไอเท็ม แลกเปลี่ยน해 สัปดาห์겠네.\r\n검 기운 느껴지 อุปกรณ์라면  기운 조합 ใหม่ ไอเท็ม 만들 수 있 것 เหมือนกัน군. 한  시도해 보겠ฉัน?\r\n\r\n";
        v0 += "#b#L0##e칠흑 기운#n #e헬 คะแนน#n แลกเปลี่ยน 싶.#l\r\n";
        v0 += "#L1##e헬 คะแนน 상점#n 이용 싶.#l\r\n";
        v0 += "#L2##eแต่ละ성한 힘 깃든 ไอเท็ม#n สร้าง 싶.#l\r\n";
        v0 += "#L3#ถัดไป 다시 찾아오겠.#l";
        int v1 = self.askMenu(v0);
        switch (v1) {
            case 0: {
                int count = getPlayer().getItemQuantity(4036454, false);
                int hellPoint = getPlayer().getOneInfoQuestInteger(1234999, "hp");
                String v2 = "자네 가지고 있 #b#i4036454# #z4036454##k 좋지 않 느낌 드는군. …เขา래. 헬 คะแนน라고 부르기 하지. 자네 가진 #b#z4036454##k 하ฉัน #b3 헬 คะแนน#k 바꿔 สัปดาห์겠네. หน้า으 자네 แลกเปลี่ยน ใช้하게 될 คะแนน 될 테니 จำเป็น한 만큼 건네 สัปดาห์게ฉัน.\r\n\r\n";
                v2 += "#b#eมี 중인 #z4036454# 갯수 : " + count + "\r\n";
                v2 += "มี 중인 헬 คะแนน : " + nf.format(hellPoint) + "#n\r\n\r\n";
                v2 += "#b#L0##e#z4036454##n #e헬 คะแนน#n แลกเปลี่ยน해สัปดาห์세요.#l\r\n";
                v2 += "#L1#ถัดไป 다시 찾아오겠.#l";
                int v3 = self.askMenu(v2);
                if (v3 == 0) {
                    count = getPlayer().getItemQuantity(4036454, false);
                    if (count <= 0) {
                        self.say("자네에게서 어떠한 기운 느껴지지 않는군. 허상이라 보았ฉัน?");
                        return;
                    }
                    String v4 = "자네 #b#i4036454# #z4036454# " + count + "ชิ้น#k มี 있군. กี่ ชิ้น #b헬 คะแนน#k แลกเปลี่ยน 싶ฉัน?\r\n";
                    int v5 = self.askNumber(v4, count, 1, count);
                    if (v5 > count) {
                        return; // Hack
                    }
                    if (1 == self
                            .askYesNo("#b #b#i4036454# #z4036454# " + v5 + "ชิ้น#k #b" + (3 * v5) + " 헬 คะแนน#k แลกเปลี่ยน하겠ฉัน?")) {
                        if (1 == target.exchange(4036454, -v5)) {
                            getPlayer().updateOneInfo(1234999, "hp", String.valueOf(hellPoint + (3 * v5)));
                            self.say("#b" + (3 * v5) + " 헬 คะแนน#k แลกเปลี่ยน해줬으니 ยืนยัน해보게ฉัน.");
                        } else {
                            self.say("알 수 없 이유 แลกเปลี่ยน ล้มเหลว했군. 다시 시도해보겠ฉัน?");
                        }
                    }
                }
                break;
            }
            case 1: {
                int hellPoint = getPlayer().getOneInfoQuestInteger(1234999, "hp");
                String v2 = "탐욕스러운 자 말로 좋지 않아. 원하 ไอเท็ม 하ฉัน 골라 보게. 기사 한  ภายใน린 결정 복하지 않는다네. 자네 결정할 때 신중하게ฉัน. หลังครั้ง할 때 이미 늦었 테니.\r\n#b";
                v2 += "#eมี 중인 헬 คะแนน : " + nf.format(hellPoint) + "P\r\n\r\n";
                v2 += "#k#e[ไอเท็ม 리스트]#n#b\r\n";
                for (int i = 0; i < consumeList.length; ++i) {
                    v2 += "#L" + i + "##i" + consumeList[i] + "# #z" + consumeList[i] + "# #r(" + consumePrice[i]
                            + "P)#b#l\r\n";
                }
                int v3 = self.askMenu(v2);
                hellPoint = getPlayer().getOneInfoQuestInteger(1234999, "hp");
                if (consumePrice[v3] > hellPoint) {
                    self.say("#b헬 คะแนน#k ไม่พอ하군. 원하 것 얻기 ด้านบน해서 대 치러야 하지 않겠ฉัน?");
                    return;
                }
                if (1 == target.exchange(consumeList[v3], 1)) {
                    getPlayer().updateOneInfo(1234999, "hp", String.valueOf(hellPoint - consumePrice[v3]));
                    self.say("ที่นี่ 있네. 자네 กระเป๋า ยืนยัน해보게ฉัน.");
                } else {
                    self.say("자네 กระเป๋า 가득찬 것 เหมือนกัน군.");
                }
                break;
            }
            case 2: {
                String v2 = "금ห้อง이라 사라질 듯 불ใน정한 기운이군. 한  สำเร็จ하지 못สามารถ มี네. จริงๆ 시도하겠ฉัน? 두려울 때 도망치 것 ห้อง법이겠지. 하지 สำเร็จ ตอนนี้ดู แข็งแรง 힘 얻 수 있 걸세.\r\n\r\n#b(สร้าง ล้มเหลว 시 재료 ใช้된 칠흑 อุปกรณ์ 제นอก한 คะแนน 사라지며, สร้าง สำเร็จ 시 재료 ใช้된 칠흑 อุปกรณ์ ตัวเลือก แต่ละ성한 칠흑 อุปกรณ์ ก่อน승.)#n#b\r\n\r\n";
                v2 += "#k#e[ไอเท็ม 리스트]#n#b\r\n";
                for (int i = 0; i < equipList.length; ++i) {
                    if (i == 3)
                        continue;
                    v2 += "#L" + i + "##i" + equipList[i] + "# #z" + equipList[i] + "##l\r\n";
                }
                v2 += "\r\n\r\n#r※ 해당 อุปกรณ์ สร้าง 시 แลกเปลี่ยน불가สถานะ เปลี่ยน.";
                int v3 = self.askMenu(v2);
                if (v3 == 3)
                    return;
                String pointName = "";
                String pointKey = "";
                int baseEquipID = baseEquipList[v3];
                int equipID = equipList[v3];
                int p = 25;
                switch (v3) {
                    case 0: {
                        pointName = "스ขวา คะแนน";
                        pointKey = "hell_swoo_point";
                        break;
                    }
                    case 1: {
                        pointName = "데미ใน คะแนน";
                        pointKey = "hell_demian_point";
                        break;
                    }
                    case 2:
                        pointName = "루시드 คะแนน";
                        pointKey = "hell_lucid_point";
                        break;
                    case 3:
                        pointName = "윌 คะแนน";
                        pointKey = "hell_will_point";
                        break;
                    case 4:
                        pointName = "더스크 คะแนน";
                        pointKey = "hell_dusk_point";
                        break;
                    case 5:
                        pointName = "진 힐라 คะแนน";
                        pointKey = "hell_jinhillah_point";
                        break;
                    case 6:
                        pointName = "듄켈 คะแนน";
                        pointKey = "hell_dunkel_point";
                        break;
                    case 7:
                        pointName = "검은마법사 คะแนน";
                        pointKey = "hell_bm_point";
                        p = 5;
                        break;
                    case 8:
                        pointName = "세렌 คะแนน";
                        pointKey = "hell_seren_point";
                        break;
                }

                if (pointName.isEmpty() || pointKey.isEmpty()) {
                    self.say("알 수 없 오류 발생하였.");
                    return;
                }
                String v4 = "#b#i" + equipID + "# #z" + equipID + "##k 얻고 싶ฉัน보군. แข็งแรง 힘 얻기 ด้านบน해서 #b#z" + baseEquipID
                        + "# 1ชิ้น#k #b" + p + " " + pointName
                        + "#k, #b#z4036454# 50ชิ้น#k จำเป็นทำ네. สร้าง ล้มเหลว 재료 ใช้한 อุปกรณ์ 남지 คะแนน 강림 칠흑 기운 หายไป 되지. 시도해 보겠ฉัน?\r\n\r\n\n";
                int point = getPlayer().getOneInfoQuestInteger(787777, pointKey);
                v4 += "#b#eมี 중인 " + pointName + " : " + nf.format(point)
                        + "P#n#k\r\n\r\n#e#r※ กระเป๋า 정렬 기준으 가장 หน้า 있 อุปกรณ์ 재료 ใช้.";
                if (1 == self.askYesNo(v4)) {
                    Item item = null;
                    if (baseEquipID == 1162083) {
                        for (int i = 1162080; i <= 1162083; ++i) {
                            item = getPlayer().getInventory(MapleInventoryType.EQUIP).findByIdWithoutLock(i);
                            if (item != null) {
                                baseEquipID = item.getItemId();
                                break;
                            }
                        }
                    } else if (baseEquipID == 1190555) {
                        for (int i = 1190555; i <= 1190559; ++i) {
                            item = getPlayer().getInventory(MapleInventoryType.EQUIP).findByIdWithoutLock(i);
                            if (item != null) {
                                baseEquipID = item.getItemId();
                                break;
                            }
                        }
                    }
                    int count = getPlayer().getInventory(MapleInventoryType.EQUIP).countByIdWithoutLock(baseEquipID);
                    if (count <= 0) {
                        self.say("자네에게서 어떠한 기운 느껴지지 않는군. 허상이라 보았ฉัน?");
                        return;
                    }
                    if (point < p) {
                        self.say(pointName + " ไม่พอ해보이는군.");
                        return;
                    }
                    int count2 = getPlayer().getInventory(MapleInventoryType.ETC).countByIdWithoutLock(4036454);
                    int removeQty = 50;
                    if (count2 < removeQty) {
                        self.say("#b#i4036454# #z4036454# ไม่พอ해보이는군.");
                        return;
                    }
                    // 50% โอกาส สร้าง
                    getPlayer().updateOneInfo(787777, pointKey, String.valueOf(point - p));
                    if (Randomizer.isSuccess(50)) {
                        item = getPlayer().getInventory(MapleInventoryType.EQUIP).findByIdWithoutLock(baseEquipID);
                        if (item == null) {
                            self.say("자네에게서 어떠한 기운 느껴지지 않는군. 허상이라 보았ฉัน?");
                            return;
                        }
                        if (1 == target.exchange(baseEquipID, -1, 4036454, -removeQty)) {
                            // อุปกรณ์ ตัวเลือก 가져오장
                            MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
                            Equip equip = (Equip) item;
                            Item newItem = ii.getEquipById(equipID);
                            if (newItem == null) {
                                self.say("알 수 없 오류 발생하였.");
                                return;
                            }
                            Equip newEquip = (Equip) newItem;
                            Item baseItem = ii.getEquipById(item.getItemId());
                            if (baseItem == null) {
                                self.say("알 수 없 오류 발생하였.");
                                return;
                            }
                            Equip baseEquip = (Equip) baseItem;
                            int str = newEquip.getStr() - baseEquip.getStr();
                            int dex = newEquip.getDex() - baseEquip.getDex();
                            int int_ = newEquip.getInt() - baseEquip.getInt();
                            int luk = newEquip.getLuk() - baseEquip.getLuk();
                            int pad = newEquip.getWatk() - baseEquip.getWatk();
                            int mad = newEquip.getMatk() - baseEquip.getMatk();

                            newEquip.set2(equip);
                            newEquip.setStr((short) (newEquip.getStr() + str));
                            newEquip.setDex((short) (newEquip.getDex() + dex));
                            newEquip.setInt((short) (newEquip.getInt() + int_));
                            newEquip.setLuk((short) (newEquip.getLuk() + luk));
                            newEquip.setWatk((short) (newEquip.getWatk() + pad));
                            newEquip.setMatk((short) (newEquip.getMatk() + mad));
                            // 교불
                            newEquip.setFlag(newEquip.getFlag() | ItemFlag.POSSIBLE_TRADING.getValue());

                            MapleInventoryManipulator.addbyItem(getClient(), newEquip);

                            self.say("สำเร็จ했군. 자네 อุปกรณ์ 알 수 없 기운 느껴지네. …마치 เขา 악마처럼.");
                        }
                    } else {
                        target.exchange(4036454, -removeQty);
                        self.say("…ล้มเหลว인. 기운 사라졌지, 다행히 자네 อุปกรณ์ 남았으니 돌려สัปดาห์겠네");
                    }
                }
                break;
            }
        }
    }

    public void Donation() {
        initNPC(MapleLifeFactory.getNPC(9000041));
        long totalMeso1 = PraiseDonationMeso.getTotalMeso();

        String v0 = "#fs11#ใน녕ทำ 모험 #b#h0##k, #e강림เดือน드#n 새로온 #b신입 뉴비#k들 정착 ด้านบน한 #e#b뉴비 기부함#k#n 운영 .\r\n"
                + "\r\n"
                + "#fs12##e[ ปัจจุบัน 기부된 금액 : #b"
                + NumberFormat.getInstance().format(totalMeso1)
                + " Meso ]#k#n#fs11#\r\n"
                + "\r\n"
                + "#b#L0#뉴비 기부함 อะไร인지 좀 더 อธิบาย해สัปดาห์세요.#l\r\n"
                + "#L1#Meso 기부하겠.#l\r\n"
                + "#L2#기부 랭킹 보겠.#l\r\n"
                + "#L3#기부 รางวัล 수령하겠.#l\r\n"
                + "#L4#뉴비 สนับสนุน금 수령하겠.#l\r\n"
                + "#L5#칭찬 상점 이용하겠.#l";
        int v1 = self.askMenu(v0);
        switch (v1) {
            case 0: {
                String v2 = "#fs11#อธิบาย จำเป็น한 รายการ เลือก해สัปดาห์시기 โปรด.\r\n\r\n";
                v2 += "#b#L0#Meso 기부 อย่างไร 하ฉัน요?#l\r\n";
                // v2 += "#b#L1#기부 랭킹 รางวัล 대해 อธิบาย해 สัปดาห์세요.#l\r\n";
                v2 += "#b#L2#기부 누적 รางวัล 대해 อธิบาย해 สัปดาห์세요.#l\r\n";
                v2 += "#b#L3#뉴비 สนับสนุน금 대해 อธิบาย해 สัปดาห์세요.#l\r\n";
                int v3 = self.askMenu(v2);
                switch (v3) {
                    case 0: {
                        self.say("#fs11#기부 Meso 단ด้านบน #b1억 Meso#k #b1억 Meso#k 당 #b100 칭찬 คะแนน#k ได้รับสามารถ .\r\n"
                                + "วันวัน สูงสุด 누적 เป็นไปได้ 칭찬 คะแนน #r1,000,000으 จำกัด#k.\r\n\r\n"
                                + "#r※ วันวัน จำกัด คะแนน 채워 랭킹 반영.");
                        break;
                    }
                    case 1: {
                        self.say(
                                "#fs11#기부 랭킹 #e매เดือน 1วัน#n마다 ด้านล่าง 기준으 รางวัล 지급.\r\n\r\n#b1ด้านบน : 20 คะแนน + 100 메이플 คะแนน\r\n2ด้านบน : 15 คะแนน + 70 메이플 คะแนน\r\n3ด้านบน~5ด้านบน : 10 คะแนน + 50 메이플 คะแนน\r\n6ด้านบน~10ด้านบน : 5 คะแนน + 30 메이플 คะแนน#k\r\n\r\n매เดือน 1วัน마다 뉴비 기부함 누적된 Meso #e1000억 Meso#n 넘ถนน 경ขวา #e누적된 Meso 30%#n 기부 เข้าร่วม한 유ฉัน 중 30억 이상 기부자 #eสุ่ม 3명 1/n#n  지급.\r\n\r\n#r(단, Meso และ도하게 บ้าน중되 ประตู제 ห้อง지하기 ด้านบน해 30% สูงสุด 금액 1500억으 จำกัด.)");
                        break;
                    }
                    case 2: {
                        self.say(
                                "#fs11#자신 기부한 누적 Meso 기준으 #e10억 Meso#n 단ด้านบน 누적될 때마다 ด้านล่าง ไอเท็ม 지급받 수 .\r\n\r\n#b#i4031227# #z4031227# 1ชิ้น");
                        break;
                    }
                    case 3: {
                        self.say(
                                "#fs11#신규 유ฉัน #eบัญชี 생성 หลัง 3วัน 지ฉัน기 ก่อน#n\r\n광장 #b‘기부함’#k NPC 통해 #e뉴비 สนับสนุน금 500억 Meso#n\r\n수령สามารถ .\r\n\r\n#b└ 뉴비 สนับสนุน금으 지급된 500억 Meso 뉴비 기부함 누적 Meso หัก.\r\n\r\n└ 뉴비 สนับสนุน금 250เลเวล 이상 ตัวละคร 수령 เป็นไปได้.\r\n\r\n└ 뉴비 สนับสนุน금 유니온 8,000 이상 บัญชี 수령 เป็นไปได้.\r\n");
                        break;
                    }
                }
                break;
            }
            case 1: {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

                Calendar CAL = new GregorianCalendar(Locale.KOREA);
                String fDate = sdf.format(CAL.getTime());

                String[] dates = fDate.split("-");
                int year = Integer.parseInt(dates[0]);
                int month = Integer.parseInt(dates[1]);
                int day = Integer.parseInt(dates[2]);
                int hours = Integer.parseInt(dates[3]);
                int minutes = Integer.parseInt(dates[4]);

                if (day == 1 && hours == 00 && minutes >= 00 ||
                        day == 1 && hours == 00 && minutes < 5) { // 매달 1วัน 00시 00นาที 00시 04นาที 수령 불
                    self.say("랭킹 บ้าน계 인 00시 04นาที 기부 เข้าร่วมสามารถ ไม่มี.");
                    return;
                }

                Timestamp timestamp = getClient().getCreated();
                long delta = System.currentTimeMillis() - timestamp.getTime();

                if (delta < (1000 * 60 * 60 * 24 * 3L)) {
                    self.say("บัญชี 생성 หลัง 3วัน 지난 บัญชี 기부 เข้าร่วมสามารถ .");
                    return;
                }

                long totalMeso = PraiseDonationMeso.getTotalMeso();
                PraiseDonationMesoRankEntry entry = PraiseDonationMesoRank.getRank(getPlayer().getAccountID());
                long myTotalMeso = 0;
                if (entry != null) {
                    myTotalMeso = entry.getTotalMeso();
                }
                String v2 = "#fs12##e#b<Meso 기부>#k#n#fs11#\r\n\r\n"
                        + "Meso 기부 #b1억 Meso#k 단ด้านบน เป็นไปได้,\r\n#b1억 Meso 당 100 칭찬 คะแนน#k ได้รับสามารถ .\r\n"
                        + "วันวัน สูงสุด 누적 เป็นไปได้ 칭찬 คะแนน #r1,000,000으 จำกัด#k, วันวัน จำกัด คะแนน 넘어서 기부해 랭킹 반영.\r\n\r\n";
                long meso = getPlayer().getMeso();
                int d = (int) (meso / 100000000);
                v2 += "ปัจจุบัน 기부함 Meso : " + NumberFormat.getInstance().format(totalMeso) + " Meso#n\r\n";
                v2 += "#e 달 ฉัน 기부 : " + NumberFormat.getInstance().format(myTotalMeso) + " Meso#n\r\n";
                v2 += "ปัจจุบัน 기부 เป็นไปได้ Meso : " + NumberFormat.getInstance().format(d * 100000000L)
                        + " Meso#n \r\n                                  #r(" + d + "ครั้ง เป็นไปได้)#k\r\n\r\n";
                v2 += "얼MP 기부ต้องการหรือไม่? #b(입력 수치당 = 1억 Meso)#k\r\n\r\n";
                int v3 = self.askNumber(v2, 1, 1, d);
                if (v3 <= 0) {
                    self.say("기부สามารถ 있 ต่ำสุด Meso 단ด้านบนดู 가진 Meso 적어 기부 ดำเนินการสามารถ ไม่มี.");
                    return;
                }
                long donationMeso = v3 * 100000000L;
                long donationMeso2 = v3;
                if (donationMeso > getPlayer().getMeso()) {
                    self.say("기부하려 시도한 Mesoดู 가진 Meso 적어 기부 ดำเนินการสามารถ ไม่มี.");
                    return;
                }
                int max = getPlayer().getOneInfoQuestInteger(1211345, "today");
                int p = Math.min(1000000 - max, v3 * 100);
                if (1 == self.askYesNo(
                        "#fs11#จริงๆ #e" + NumberFormat.getInstance().format(donationMeso) + " Meso#n 기부ต้องการหรือไม่?\r\n기부 시 #r"
                                + NumberFormat.getInstance().format(p) + " 칭찬 คะแนน#k ได้รับสามารถ .")) {
                    getPlayer().gainMeso(-donationMeso, true);

                    if (p > 0) {
                        PraisePoint point = getPlayer().getPraisePoint();
                        point.setTotalPoint(point.getTotalPoint() + (p));
                        point.setPoint(point.getPoint() + (p));

                        getPlayer().updateOneInfo(3887, "point", String.valueOf(point.getPoint()));
                        getPlayer().setSaveFlag(getPlayer().getSaveFlag() | CharacterSaveFlag.PRAISE_POINT.getFlag());
                    }

                    getPlayer().updateOneInfo(1211345, "today", String.valueOf(max + p));

                    Center.Broadcast.broadcastMessage(CField.chatMsg(4,
                            "[" + getPlayer().getName() + "] 뉴비 기부함 '" + donationMeso2 + "억 Meso' 기부하셨."));

                    PraiseDonationMeso.doDonationMeso(getPlayer(), getPlayer().getAccountID(), donationMeso);
                    self.say("#fs11##e" + NumberFormat.getInstance().format(donationMeso) + " Meso#n 기부\r\n#r"
                            + NumberFormat.getInstance().format(p) + " 칭찬 คะแนน ได้รับ하였.");
                }
                break;
            }
            case 2: {
                List<PraiseDonationMesoRankEntry> ranks = PraiseDonationMesoRank.getTopRanker(); // 상ด้านบน 50명
                String v2 = "#fs12##e<기부 랭킹 상ด้านบน 50>#n\r\n\r\n";
                int count = 1;
                for (PraiseDonationMesoRankEntry r : ranks) {
                    if (count < 10) {
                        v2 = v2 + "#Cgray#00#b#e" + count + "ด้านบน#n#k";
                    } else if (count >= 10 && count < 100) {
                        v2 = v2 + "#Cgray#0#b#e" + count + "ด้านบน#n#k";
                    } else {
                        v2 = v2 + "#Cgray##b#e" + count + "ด้านบน#n#k";
                    }
                    v2 = v2 + " " + r.getPlayerName() + " #e(Meso : "
                            + NumberFormat.getInstance().format(r.getTotalMeso()) + ")#n\r\n";
                    count++;
                }
                self.say(v2);
                break;
            }
            case 3: {
                String v2 = "#fs11#어떤 รางวัล 수령ต้องการหรือไม่?\r\n\r\n";
                v2 += "#b#L0#누적 รางวัล 수령하겠.#l\r\n";
                // v2 += "#L1#기부 랭킹 รางวัล 수령하겠.#l";
                int v3 = self.askMenu(v2);
                switch (v3) {
                    case 0: {
                        long totalMeso = PraiseDonationMeso.getTotalMeso(getPlayer().getAccountID());
                        int getCount = getPlayer().getOneInfoQuestInteger(1211345, "get_reward");
                        int count = (int) (totalMeso / 1_000_000_000L);
                        int remainCount = count - getCount;
                        String v4 = "#fs11##e<누적 รางวัล 수령>#n\r\n#eทั้งหมด 누적 Meso : "
                                + NumberFormat.getInstance().format(totalMeso) + "\r\n";
                        v4 += "수령 เป็นไปได้ รางวัล 횟수 : " + remainCount + " ครั้ง\r\n\r\n"
                                + "#b#n누적 Meso 10억 당 1ครั้ง#k รางวัล 수령 เป็นไปได้.\r\n\r\n";
                        v4 += remainCount + "ครั้ง ทั้งหมด ใช้ ด้านล่าง ไอเท็ม ได้รับต้องการหรือไม่?\r\n\r\n";
                        v4 += "#b#i4031227# #z4031227# x" + remainCount;
                        if (1 == self.askYesNo(v4)) {
                            if (remainCount <= 0) {
                                self.say("수령 เป็นไปได้ 횟수 ไม่เพียงพอ รางวัล 수령สามารถ ไม่มี.");
                                return;
                            }
                            if (target.exchange(4031227, remainCount) > 0) {
                                self.say("누적 รางวัล 수령 เสร็จสมบูรณ์.");
                                getPlayer().updateOneInfo(1211345, "get_reward",
                                        String.valueOf(getCount + remainCount));
                            } else {
                                self.say("#bช่องกระเป๋า เพิ่มพื้นที่ ลองใหม่อีกครั้ง โปรด.");
                            }
                        }
                        break;
                    }
                    case 1: {
                        // TODO: 랭킹 รางวัล 수령, 1วัน 랭킹 정산, 1000억 넘 시 30억 이상 기부 เข้าร่วม한 3명 추첨 1/n 수령 구현
                        int rank = getPlayer().getOneInfoQuestInteger(1234599, "praise_reward");
                        int get = getPlayer().getOneInfoQuestInteger(1234599, "praise_reward_get");
                        long meso = getPlayer().getOneInfoQuestLong(1234599, "praise_reward2");
                        int get2 = getPlayer().getOneInfoQuestInteger(1234599, "praise_reward2_get");

                        if ((rank > 10 || get > 0) && (meso <= 0 || get2 > 0)) {
                            self.say("기부 랭킹 รางวัล 수령 เป็นไปได้ 대상자 아닙니다.");
                            return;
                        }
                        NumberFormat nf = NumberFormat.getInstance();
                        if (meso > 0 && get2 == 0) {
                            getPlayer().gainMeso(meso, true);
                            getPlayer().updateOneInfo(1234599, "praise_reward2", "0");
                            getPlayer().updateOneInfo(1234599, "praise_reward2_get", "1");

                            getPlayer().dropMessage(5, nf.format(meso) + "Meso 지급받았.");
                        }
                        if (rank > 0 && get == 0) {
                            int realPoint = 0;
                            int maplePoint = 0;
                            if (rank == 1) {
                                realPoint = 200000;
                                maplePoint = 10000000;
                            } else if (rank == 2) {
                                realPoint = 150000;
                                maplePoint = 700000;
                            } else if (rank >= 3 && rank <= 5) {
                                realPoint = 100000;
                                maplePoint = 500000;
                            } else if (rank >= 6 && rank <= 10) {
                                realPoint = 50000;
                                maplePoint = 300000;
                            }
                            getPlayer().gainRealCash(realPoint, true);
                            getPlayer().modifyCSPoints(2, maplePoint, true);

                            getPlayer().updateOneInfo(1234599, "praise_reward", "0");
                            getPlayer().updateOneInfo(1234599, "praise_reward_get", "1");

                            getPlayer().dropMessage(5,
                                    nf.format(realPoint) + " คะแนน " + nf.format(maplePoint) + " 메이플 คะแนน 지급받았.");
                        }
                        self.say("지급 เสร็จสมบูรณ์.");
                        break;
                    }
                }
                break;
            }
            case 4: {
                Timestamp timestamp = getClient().getCreated();
                long delta = System.currentTimeMillis() - timestamp.getTime();

                if (delta >= (1000 * 60 * 60 * 24 * 3L)) {
                    self.say("บัญชี 생성 หลัง 3วัน ก่อนถึง 뉴비 สนับสนุน금 수령สามารถ .");
                    return;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

                Calendar CAL = new GregorianCalendar(Locale.KOREA);
                String fDate = sdf.format(CAL.getTime());

                String[] dates = fDate.split("-");
                int year = Integer.parseInt(dates[0]);
                int month = Integer.parseInt(dates[1]);
                int day = Integer.parseInt(dates[2]);
                int hours = Integer.parseInt(dates[3]);
                int minutes = Integer.parseInt(dates[4]);

                if (day == 1 && hours == 00 && minutes >= 00 ||
                        day == 1 && hours == 00 && minutes < 5) { // 매달 1วัน 00시 00นาที 00시 04นาที 수령 불
                    self.say("랭킹 บ้าน계 인 00시 04นาที 뉴비 สนับสนุน금 수령สามารถ ไม่มี.");
                    return;
                }

                if (getPlayer().getLevel() < 250) {
                    self.say("250เลเวล 미 ตัวละคร 뉴비 สนับสนุน금 수령สามารถ ไม่มี.");
                    return;
                }

                if (getPlayer().getUnionLevel() < 8000) {
                    self.say("유니온 เลเวล 8000 이상이어야 뉴비 สนับสนุน금 수령สามารถ .");
                    return;
                }

                if (getPlayer().getOneInfoQuestInteger(1211345, "get_meso") > 0) {
                    self.say("금วัน 이미 뉴비 สนับสนุน금 수령하였.");
                    return;
                }

                long remainMeso = PraiseDonationMeso.getTotalMeso();
                long remainMeso2 = PraiseDonationMeso.getTotalMeso() - 500_0000_0000L;
                if (remainMeso < 500_0000_0000L) {
                    self.say("기부함 남 Meso ไม่เพียงพอ 뉴비 สนับสนุน금 수령สามารถ ไม่มี.");
                    return;
                }

                PraiseDonationMeso.addTotalMeso(-500_0000_0000L);
                getPlayer().gainMeso(500_0000_0000L, true);
                getPlayer().updateOneInfo(1211345, "get_meso", "1");
                Center.Broadcast.broadcastMessage(
                        CField.chatMsg(5, "[" + getPlayer().getName() + "] 뉴비 สนับสนุน금 '500억 Meso' ได้รับ하셨!"));

                self.say("#fs11#뉴비 สนับสนุน금 #e#b500억 Meso#k#n 수령하였.\r\n\r\n#e기부함 남 Meso : "
                        + NumberFormat.getInstance().format(remainMeso2));
                break;
            }
            case 5: { // 칭찬 상점
                int praise = getPlayer().getPraisePoint().getPoint();
                StringBuilder menu = new StringBuilder();
                menu.append("#fs14##e≪ 칭찬 คะแนน 상점 ≫#n\r\n\r\n")
                        .append("#fs12#ภายใน 칭찬 คะแนน: #b")
                        .append(NumberFormat.getInstance().format(praise))
                        .append("P #k\r\n")
                        .append("#fs11#─────────────────────────────\r\n");

                int[] itemIDs = { 4036660, 4036661, 5068306 }; // 예시 ไอเท็ม
                int[] prices = { 100000, 500000, 1000000 }; // 칭찬 คะแนน 단ด้านบน

                for (int i = 0; i < itemIDs.length; i++) {
                    menu.append("#fs11##L").append(i).append("#")
                            .append("#i").append(itemIDs[i]).append("# ")
                            .append("#z").append(itemIDs[i]).append("#    ")
                            .append("#fs11##b")
                            .append(NumberFormat.getInstance().format(prices[i]))
                            .append(" คะแนน#k#l\r\n");
                }

                menu.append("#fs11#\r\n─────────────────────────────\r\n")
                        .append("#fs11#ซื้อ할 상품 เลือก해 สัปดาห์세요.");

                int sel = self.askMenu(menu.toString());
                int cost = prices[sel], item = itemIDs[sel];

                if (praise < cost) {
                    self.say("#fs12#칭찬 คะแนน ไม่พอ.");
                    break;
                }

                String confirm = new StringBuilder()
                        .append("#fs12#จริงๆ #b")
                        .append(NumberFormat.getInstance().format(cost))
                        .append("#k คะแนน ใช้\r\n")
                        .append("#fs12##i").append(item)
                        .append("# #z").append(item)
                        .append("##n\r\n")
                        .append("#fs12#() ซื้อต้องการหรือไม่?")
                        .toString();

                if (self.askYesNo(confirm) == 1) {
                    // คะแนน หัก
                    PraisePoint pt = getPlayer().getPraisePoint();
                    pt.setPoint(pt.getPoint() - cost);
                    pt.setTotalPoint(pt.getTotalPoint() - cost);
                    getPlayer().updateOneInfo(3887, "point", String.valueOf(pt.getPoint()));
                    getPlayer().setSaveFlag(
                            getPlayer().getSaveFlag() | CharacterSaveFlag.PRAISE_POINT.getFlag());
                    // ไอเท็ม 지급
                    if (target.exchange(item, 1) > 0) {
                        self.say("#fs12#ซื้อ เสร็จสมบูรณ์!");
                    } else {
                        self.say("#fs12#กระเป๋า 공ระหว่าง ไม่พอ.");
                    }
                }
                break;
            }
        }
    }

    public void buyStone(long price, int count, int type, int rate) {
        String payType = "คะแนน";
        long getPrice = getPlayer().getRealCash();
        if (type == 1) {
            payType = "คะแนนโปรโมชั่น";
            getPrice = getPlayer().getHongboPoint();
        } else if (type == 2) {
            payType = "Meso";
            getPrice = getPlayer().getMeso();
        } else if (type == 3) {
            payType = "강림 แคช";
            getPrice = getPlayer().getCashPoint();
        } else if (type == 4) {
            payType = "Brilliant Light Crystal";
            getPrice = getPlayer().getItemQuantity(4031227, false);
        }

        NumberFormat nf = NumberFormat.getInstance();
        String payText = nf.format(price) + " " + payType;
        String v0 = "";
        if (DBConfig.isGanglim) {
            v0 += "#fs11#";
        }
        v0 += "#b" + nf.format(price) + " " + payType + "#k ใช้ แต่ละ인석 꺼ภายใน겠어?\r\n어떤 แต่ละ인석 꺼ภายใน질지 꺼ภายใน봐야 알아.\r\n\r\n#eมี 중인 "
                + payType + " : " + NumberFormat.getInstance().format(getPrice);
        if (1 == self.askYesNo(v0)) {
            if (type == 0) {
                if (getPlayer().getRealCash() < price) {
                    self.say(payType + " ไม่พอ한 거 เหมือนกัน은데? ใคร리 너라 고용비 확실해야 하 법이지.");
                    return;
                }
            } else if (type == 1) {
                if (getPlayer().getHongboPoint() < price) {
                    self.say(payType + " ไม่พอ한 거 เหมือนกัน은데? ใคร리 너라 고용비 확실해야 하 법이지.");
                    return;
                }
            } else if (type == 2) {
                if (getPlayer().getMeso() < price) {
                    self.say(payType + " ไม่พอ한 거 เหมือนกัน은데? ใคร리 너라 고용비 확실해야 하 법이지.");
                    return;
                }
            } else if (type == 3) {
                if (getPlayer().getCashPoint() < price) {
                    self.say("#fs11#" + payType + " ไม่พอ한 거 เหมือนกัน은데? ใคร리 너라 고용비 확실해야 하 법이지.");
                    return;
                }
            } else if (type == 4) {
                if (getPlayer().getItemQuantity(4031227, false) < price) {
                    self.say("#fs11#" + payType + " ไม่พอ한 거 เหมือนกัน은데? ใคร리 너라 고용비 확실해야 하 법이지.");
                    return;
                }
            }
            if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < count) {
                self.say("#bใช้ กระเป๋า#k ช่อง " + count + "칸 이상 เพิ่มพื้นที่ 다시 시도해줘.");
                return;
            }
            if (type == 0) {
                getPlayer().gainRealCash((int) -price, true);
            } else if (type == 1) {
                getPlayer().gainHongboPoint((int) -price, true);
            } else if (type == 2) {
                getPlayer().gainMeso(-price, true);
            } else if (type == 3) {
                getPlayer().gainCashPoint((int) -price);
            } else if (type == 4) {
                if (target.exchange(4031227, (int) -price) < 0) {
                    self.say("#fs11# " + payType + " ไม่พอ한 거 เหมือนกัน은데? ใคร리 너라 고용비 확실해야 하 법이지.");
                    return;
                }
            }

            int l1 = 0;
            int l2 = 0;
            for (int i = 0; i < count; ++i) {
                int itemID = 2432127;
                if (Randomizer.isSuccess(rate)) {
                    itemID = 2432126;
                    l2++;
                } else {
                    l1++;
                }
                if (target.exchange(itemID, 1) > 0) {
                }
            }

            String list = "";
            String list2 = "";
            if (l1 > 0) {
                list += "#i2432127# #z2432127# x" + l1;
                list2 += "2432127 " + l1 + "ชิ้น";
            }
            if (l2 > 0) {
                if (l1 > 0) {
                    list += "\r\n";
                    list2 += "\r\n";
                }
                list += "#i2432126# #z2432126# x" + l2;
                list2 += "2432126 " + l2 + "ชิ้น";
            }

            StringBuilder sb = new StringBuilder(
                    "แต่ละ인석 뽑기 결 (뽑 ไอเท็ม : " + list + ", ใช้재화 : " + payType + ", ใช้자 : " + getPlayer().getName() + ")");
            LoggingManager.putLog(new ConsumeLog(getPlayer(), 1, sb));

            // objects.utils.FileoutputUtil.log("./TextLog/BuyStoneCraft.txt", "แต่ละ인석 뽑기
            // (ไอเท็มID : " + itemID + ", 지불 ห้อง식 : หลัง원, ใช้자 : " + getPlayer().getName() +
            // ")\r\n");
            String v7 = "";
            if (DBConfig.isGanglim) {
                v7 += "#fs11#";
            }
            v7 += "#b" + list + "#k\r\n꺼ภายใน온 แต่ละ인석들이야.\r\n\r\n한  더 가지고 올까?\r\n원한다면 10ชิ้น씩 가지고 올 수 있어.\r\n\r\n#b#L0#";
            if (type == 2) {
                v7 += "1,500,000,000Meso 지불 แต่ละ인석 1ชิ้น ได้รับ.#l\r\n#L1#15,000,000,000Meso 지불 แต่ละ인석 10ชิ้น ได้รับ.#l";
            } else if (type == 0 || type == 1 || type == 3) {
                v7 += "3,000 " + payType + " 지불 แต่ละ인석 1ชิ้น ได้รับ.#l\r\n#L1#30,000 " + payType
                        + " 지불 แต่ละ인석 10ชิ้น ได้รับ.#l";
            } else {
                v7 += payType + " 600ชิ้น 지불 แต่ละ인석 1ชิ้น ได้รับ.#l\r\n#L1#" + payType + " 6000ชิ้น 지불 แต่ละ인석 10ชิ้น ได้รับ.#l";
            }

            int v6 = self.askMenu(v7);
            int c = 1;
            if (v6 == 1) {
                c = 10;
            }
            long p;
            if (type == 2) {
                // Meso
                p = 1500000000L * c;
            } else if (type == 4) {
                // Brilliant Light Crystal
                p = 600L * c;
            } else {
                // คะแนนโปรโมชั่น, 강림 แคช
                p = 3000L * c;
            }
            buyStone(p, c, type, rate);
            return;
        }
    }

    public void spinOff_UIOpen() {
        initNPC(MapleLifeFactory.getNPC(1530021));
        String v0 = "";
        if (DBConfig.isGanglim) { // แต่ละ인석판 없을경ขวา 1ครั้ง 지급
            if (getPlayer().getItemQuantity(2432128, false) < 1) {
                getPlayer().gainItem(2432128, 1);
            }
        }
        if (DBConfig.isGanglim) {
            v0 += "#fs11#";
        }
        v0 += "#h0#! 오랜만이야!\r\n스승님께서 검 마법사 남긴 기록 연구하시다 이상한 돌 발견하셨어.\r\n\r\n#b#i2432126# #z2432126#\r\n #i2432127#  #z2432127##k\n\r\n\r\n이게 바 เขา 돌이야.";

        self.say(v0);

        String v1 = "";
        if (DBConfig.isGanglim) {
            v1 += "#fs11#";
        }
        v1 += "스승님께서 #b“겉 평범해 보 돌이지 미약하게 검 마법사 기운 느껴진다. 불ใน정한 기운 다듬으면 힘 บางส่วน ใช้สามารถ 있을지 모른다. 하지……”#k 이렇게 중얼ถนน시더니 갑자기 ที่ไหน론 사라지셨어.\r\n\r\n어때? 관심 있어? (말 싶어 하 눈치다. 한  들어 ดู 할까.)\r\n\r\n";
        v1 += "#b#L0#검 마법사 남긴 돌?#l\r\n#L1#แต่ละ인 석판 ยืนยัน 싶어.#l\r\n#L2#คะแนน ใช้ แต่ละ인석 뽑고 싶어.#l\r\n#L3#คะแนน ใช้ แต่ละ인석 세공 싶어.#l";
        int v2 = self.askMenu(v1);
        switch (v2) {
            case 0: {
                String v3 = "";
                if (DBConfig.isGanglim) {
                    v3 += "#fs11#";
                }
                v3 += "스승님께서  돌 แต่ละ인석이라고 부르셨어.\r\n\r\n#b(엘윈 들고 있 석판 쌓인 먼지 털어ภายใน더니 ภายใน게 건넸다. แต่ละ인석 하ฉัน 겨ขวา 들어갈 크기 홈 석판 군데군데 파여 มี.)#k\r\n\r\n 돌 불ใน정한 기운 다듬 งาน 세공이라고 하는데, 세공 마친 แต่ละ인석 석판 홈 끼ขวา면 돌 남아 있 힘 บางส่วน ใช้สามารถ 있 거야. ฉัน 스승님께서 하시 พูด 듣기 한 거라서 정확하게 모르지…….\r\n\r\n";
                v3 += "#b#L0#ที่ไหน서 얻 수 있지?#l\r\n#L1#세공하 ห้อง법?#l";
                int v4 = self.askMenu(v3);
                switch (v4) {
                    case 0: {
                        String v5 = "";
                        if (DBConfig.isGanglim) {
                            v5 = "#fs11#แต่ละ인석 검 마법사 지하 서고 있 보관함 들어 있어.\r\n결계 때ประตู 스승ฉัน ภายใน 없으면 꺼낼 수 없으니까 จำเป็น ฉัน 말해.\r\n\r\n#h0# 너라고 해, 결계 마법사 고용비 제대 받 거야.\r\n보관함 어떤 แต่ละ인석 꺼ภายใน질지 ฉัน 몰라. 어떤 게 ฉัน오더라 고용비 똑เหมือนกัน으니까 알아둬.\r\n\r\n";
                            v5 += "#e강림 แคช : 3,000 แคช\r\n#b[정밀한 แต่ละ인석 80%, 평범한 แต่ละ인석 20%]#k\r\nBrilliant Light Crystal : 500 ชิ้น\r\n#b[정밀한 แต่ละ인석 10%, 평범한 แต่ละ인석 90%]#k#k";
                        } else {
                            v5 = "แต่ละ인석 검 마법사 지하 서고 있 보관함 들어 있어.\r\n결계 때ประตู 스승ฉัน ภายใน 없으면 꺼낼 수 없으니까 จำเป็น ฉัน 말해.\r\n\r\n#h0# 너라고 해, 결계 마법사 고용비 제대 받 거야.\r\n보관함 어떤 แต่ละ인석 꺼ภายใน질지 ฉัน 몰라. 어떤 게 ฉัน오더라 고용비 똑เหมือนกัน으니까 알아둬.\r\n\r\n";
                            v5 += "#eคะแนน : 3,000 #b(정밀한 แต่ละ인석 80%, 평범한 แต่ละ인석 20%)#k\r\nคะแนนโปรโมชั่น : 3,000 #b(정밀한 แต่ละ인석 60%, 평범한 แต่ละ인석 40%)#k\r\nMeso : 1,500,000,000 #b(정밀한 แต่ละ인석 10%, 평범한 แต่ละ인석 90%)#k";
                        }
                        self.say(v5);

                        /*
                         * String v6 =
                         * "เขา러고 보니 검 마법사 ผลกระทบ 받 군단장 잔상 처치 แต่ละ인석 얻 수 있을지 몰라 #r(스ขวา, 데미ใน, 루시드, 윌, 더스크, 듄켈, 진 힐라, 검 마법사 처치 시 โอกาส적으 하급 แต่ละ인석 상자 และ 중급 แต่ละ인석 상자 ดรอปสามารถ ).#k"
                         * ;
                         * v6 +=
                         * "\r\n\r\n#e30% โอกาส ‘하급 แต่ละ인석 상자’ ดรอป(ชิ้น인 ดรอป, สูงสุด 1ชิ้น)#n\r\n#b└ 정밀한 แต่ละ인석 10%, 평범한 แต่ละ인석 90%#k\r\n\r\n#e10% โอกาส ‘중급 แต่ละ인석 상자’ ดรอป(ชิ้น인 ดรอป, สูงสุด 1ชิ้น)#n\r\n#b└ 정밀한 แต่ละ인석 30%, 평범한 แต่ละ인석 70%#k"
                         * ;
                         * self.say(v6);
                         */
                        break;
                    }
                    case 1: {
                        String v5 = "";
                        if (DBConfig.isGanglim) {
                            v5 += "#fs11#";
                        }
                        v5 += "스승님 허락 없 ฉัน희끼리 이래 괜찮 걸까요……?\r\n\r\nแต่ละ인석 ฉัน 가지고 오시면 불ใน정한 기운 다듬어 드릴게요.\r\nแต่ละ인석 남아 있 기운 불ใน정해서 세공 ล้มเหลวสามารถ 있어요.\r\n\r\n";
                        self.sayReplacedNpc(v5, 1530040);

                        String v6 = "";
                        if (DBConfig.isGanglim) {
                            v6 = "#fs11##h0# 이라고 해, 조작 마법사 고용비 제대 받 거예요. 세공 ล้มเหลว하더라 고용비 똑เหมือนกัน으니까 알고 อยู่.\r\n\r\n#e강림 แคช : 1,000 แคช\r\nคะแนน : 3,000 คะแนน\r\nBrilliant Light Crystal : 200 ชิ้น";
                        } else {
                            v6 = "#h0# 이라고 해, 조작 마법사 고용비 제대 받 거예요. 세공 ล้มเหลว하더라 고용비 똑เหมือนกัน으니까 알고 อยู่.\r\n\r\n#eคะแนน : 1,000\r\nคะแนนโปรโมชั่น : 1,000\r\nMeso : 500,000,000";
                        }
                        self.sayReplacedNpc(v6, 1530040);

                        String v7 = "";
                        if (DBConfig.isGanglim) {
                            v7 += "#fs11#";
                        }
                        v7 += "#r※ 장착한 แต่ละ인석 임의 ปลดล็อกสามารถ ไม่มี, ต่างกัน แต่ละ인석 장착 기존 แต่ละ인석 파괴 เขา 자리 장착하게 . 파괴된 แต่ละ인석 흔적 남지 않으며 ครั้ง수 불가.";
                        v7 += "\r\n\r\n※ แต่ละ인석 플러스 10ครั้ง, 마이너스 10ครั้ง ทั้งหมด 20ครั้ง 세공 ทั้งหมด 마쳐야 ตัวเลือก ใช้งาน.\r\n\r\n";
                        self.sayReplacedNpc(v7, 1530040);
                        break;
                    }
                }
                break;
            }
            case 1: {
                String empty = "#fc0xFF6600CC##fUI/UIWindow.img/IconBase/0#";
                String icon = "#fs11##fc0xFF6600CC##i";
                String v3 = "";
                if (DBConfig.isGanglim) {
                    v3 += "#fs11#";
                }
                v3 += "#e[석판 장착된 แต่ละ인석]#n\r\n\r\n";
                int item1 = getPlayer().getOneInfoQuestInteger(133333, "equip1");
                int item2 = getPlayer().getOneInfoQuestInteger(133333, "equip2");
                int item3 = getPlayer().getOneInfoQuestInteger(133333, "equip3");
                int item4 = getPlayer().getOneInfoQuestInteger(133333, "equip4");
                int item5 = getPlayer().getOneInfoQuestInteger(133333, "equip5");

                v3 += item1 > 0 ? (icon + item1 + "# ") : (empty + " ");
                v3 += item2 > 0 ? (icon + item2 + "# ") : (empty + " ");
                v3 += item3 > 0 ? (icon + item3 + "# ") : (empty + " ");
                v3 += item4 > 0 ? (icon + item4 + "# ") : (empty + " ");
                v3 += item5 > 0 ? (icon + item5 + "# ") : (empty + " ");

                int plusCount1 = getPlayer().getOneInfoQuestInteger(133333, "craftPlus1");
                int plusCount2 = getPlayer().getOneInfoQuestInteger(133333, "craftPlus2");
                int plusCount3 = getPlayer().getOneInfoQuestInteger(133333, "craftPlus3");
                int plusCount4 = getPlayer().getOneInfoQuestInteger(133333, "craftPlus4");
                int plusCount5 = getPlayer().getOneInfoQuestInteger(133333, "craftPlus5");

                int minusCount1 = getPlayer().getOneInfoQuestInteger(133333, "craftMinus1");
                int minusCount2 = getPlayer().getOneInfoQuestInteger(133333, "craftMinus2");
                int minusCount3 = getPlayer().getOneInfoQuestInteger(133333, "craftMinus3");
                int minusCount4 = getPlayer().getOneInfoQuestInteger(133333, "craftMinus4");
                int minusCount5 = getPlayer().getOneInfoQuestInteger(133333, "craftMinus5");

                int optionPlus1 = getPlayer().getOneInfoQuestInteger(133333, "plusValue1");
                int optionPlus2 = getPlayer().getOneInfoQuestInteger(133333, "plusValue2");
                int optionPlus3 = getPlayer().getOneInfoQuestInteger(133333, "plusValue3");
                int optionPlus4 = getPlayer().getOneInfoQuestInteger(133333, "plusValue4");
                int optionPlus5 = getPlayer().getOneInfoQuestInteger(133333, "plusValue5");

                int optionMinus1 = getPlayer().getOneInfoQuestInteger(133333, "minusValue1");
                int optionMinus2 = getPlayer().getOneInfoQuestInteger(133333, "minusValue2");
                int optionMinus3 = getPlayer().getOneInfoQuestInteger(133333, "minusValue3");
                int optionMinus4 = getPlayer().getOneInfoQuestInteger(133333, "minusValue4");
                int optionMinus5 = getPlayer().getOneInfoQuestInteger(133333, "minusValue5");

                String lock1 = getPlayer().getOneInfoQuestInteger(133333, "lock1") > 0 ? "#r(ล็อก)" : "#b(열림)";
                String lock2 = getPlayer().getOneInfoQuestInteger(133333, "lock2") > 0 ? "#r(ล็อก)" : "#b(열림)";
                String lock3 = getPlayer().getOneInfoQuestInteger(133333, "lock3") > 0 ? "#r(ล็อก)" : "#b(열림)";
                String lock4 = getPlayer().getOneInfoQuestInteger(133333, "lock4") > 0 ? "#r(ล็อก)" : "#b(열림)";
                String lock5 = getPlayer().getOneInfoQuestInteger(133333, "lock5") > 0 ? "#r(ล็อก)" : "#b(열림)";

                int unlock1 = getPlayer().getOneInfoQuestInteger(133333, "unlock1");
                int unlock2 = getPlayer().getOneInfoQuestInteger(133333, "unlock2");

                v3 += "\r\n" + lock1 + " " + lock2 + " " + lock3 + " " + lock4 + " " + lock5;
                v3 += "\r\n\r\n#k";
                String item = item1 > 0 ? ("#b#z" + item1 + "##k") : "#r장착되지 않음#k";
                String ImprintedCount = (plusCount1 + minusCount1) == 20 ? "#b세공เสร็จสมบูรณ์#k"
                        : "#b[" + plusCount1 + "/10] #r[" + minusCount1 + "/10]#k";
                String ImprintedEnabled = (plusCount1 + minusCount1) == 20 ? "#fc0xFF9d1ffe#ใช้งาน#k"
                        : "#fc0xFFc983ff#미ใช้งาน#k";
                ImprintedStoneOption plusOption1 = ImprintedStoneOption
                        .getByOption(getPlayer().getOneInfoQuestInteger(133333, "plusOption1"));
                ImprintedStoneOption minusOption1 = ImprintedStoneOption
                        .getByOption(getPlayer().getOneInfoQuestInteger(133333, "minusOption1"));
                v3 += "1째 홈 : " + item + " | " + ImprintedCount + "\r\n";
                v3 += "ㄴ #b[" + String.format(plusOption1.getDesc(), "+", optionPlus1, "%", "") + ", #r"
                        + String.format(minusOption1.getDesc(), "-", optionMinus1, "%", "") + "]#k | "
                        + ImprintedEnabled + "\r\n";

                item = item2 > 0 ? ("#b#z" + item2 + "##k") : "#r장착되지 않음#k";
                ImprintedCount = (plusCount2 + minusCount2) == 20 ? "#b세공เสร็จสมบูรณ์"
                        : "#b[" + plusCount2 + "/10] #r[" + minusCount2 + "/10]#k";
                ImprintedEnabled = (plusCount2 + minusCount2) == 20 ? "#fc0xFF9d1ffe#ใช้งาน#k" : "#fc0xFFc983ff#미ใช้งาน#k";
                ImprintedStoneOption plusOption2 = ImprintedStoneOption
                        .getByOption(getPlayer().getOneInfoQuestInteger(133333, "plusOption2"));
                ImprintedStoneOption minusOption2 = ImprintedStoneOption
                        .getByOption(getPlayer().getOneInfoQuestInteger(133333, "minusOption2"));
                v3 += "2째 홈 : " + item + " | " + ImprintedCount + "\r\n";
                v3 += "ㄴ #b[" + String.format(plusOption2.getDesc(), "+", optionPlus2, "%", "") + ", #r"
                        + String.format(minusOption2.getDesc(), "-", optionMinus2, "%", "") + "]#k | "
                        + ImprintedEnabled + "\r\n";

                item = item3 > 0 ? ("#b#z" + item3 + "##k") : "#r장착되지 않음#k";
                ImprintedCount = (plusCount3 + minusCount3) == 20 ? "#b세공เสร็จสมบูรณ์"
                        : "#b[" + plusCount3 + "/10] #r[" + minusCount3 + "/10]#k";
                ImprintedEnabled = (plusCount3 + minusCount3) == 20 ? "#fc0xFF9d1ffe#ใช้งาน#k" : "#fc0xFFc983ff#미ใช้งาน#k";
                ImprintedStoneOption plusOption3 = ImprintedStoneOption
                        .getByOption(getPlayer().getOneInfoQuestInteger(133333, "plusOption3"));
                ImprintedStoneOption minusOption3 = ImprintedStoneOption
                        .getByOption(getPlayer().getOneInfoQuestInteger(133333, "minusOption3"));
                v3 += "3째 홈 : " + item + " | " + ImprintedCount + "\r\n";
                v3 += "ㄴ #b[" + String.format(plusOption3.getDesc(), "+", optionPlus3, "%", "") + ", #r"
                        + String.format(minusOption3.getDesc(), "-", optionMinus3, "%", "") + "]#k | "
                        + ImprintedEnabled + "\r\n";

                item = item4 > 0 ? ("#b#z" + item4 + "##k") : "#r장착되지 않음#k";
                ImprintedCount = (plusCount4 + minusCount4) == 20 ? "#b세공เสร็จสมบูรณ์"
                        : "#b[" + plusCount4 + "/10] #r[" + minusCount4 + "/10]#k";
                ImprintedEnabled = (plusCount4 + minusCount4) == 20 ? "#fc0xFF9d1ffe#ใช้งาน#k" : "#fc0xFFc983ff#미ใช้งาน#k";
                ImprintedStoneOption plusOption4 = ImprintedStoneOption
                        .getByOption(getPlayer().getOneInfoQuestInteger(133333, "plusOption4"));
                ImprintedStoneOption minusOption4 = ImprintedStoneOption
                        .getByOption(getPlayer().getOneInfoQuestInteger(133333, "minusOption4"));
                v3 += "4째 홈 : " + item + " | " + ImprintedCount + "\r\n";
                v3 += "ㄴ #b[" + String.format(plusOption4.getDesc(), "+", optionPlus4, "%", "") + ", #r"
                        + String.format(minusOption4.getDesc(), "-", optionMinus4, "%", "") + "]#k | "
                        + ImprintedEnabled + "\r\n";

                /*
                 * if (unlock1 == 0) {
                 * v3 += " #e(봉인됨)#n";
                 * }
                 * v3 += "\r\n";
                 */
                item = item5 > 0 ? ("#b#z" + item5 + "##k") : "#r장착되지 않음#k";
                ImprintedCount = (plusCount5 + minusCount5) == 20 ? "#b세공เสร็จสมบูรณ์"
                        : "#b[" + plusCount5 + "/10] #r[" + minusCount5 + "/10]#k";
                ImprintedEnabled = (plusCount5 + minusCount5) == 20 ? "#fc0xFF9d1ffe#ใช้งาน#k" : "#fc0xFFc983ff#미ใช้งาน#k";
                ImprintedStoneOption plusOption5 = ImprintedStoneOption
                        .getByOption(getPlayer().getOneInfoQuestInteger(133333, "plusOption5"));
                ImprintedStoneOption minusOption5 = ImprintedStoneOption
                        .getByOption(getPlayer().getOneInfoQuestInteger(133333, "minusOption5"));
                v3 += "5째 홈 : " + item + " | " + ImprintedCount + "\r\n";
                v3 += "ㄴ #b[" + String.format(plusOption5.getDesc(), "+", optionPlus5, "%", "") + ", #r"
                        + String.format(minusOption5.getDesc(), "-", optionMinus5, "%", "") + "]#k | "
                        + ImprintedEnabled + "\r\n";
                /*
                 * if (unlock2 == 0) {
                 * v3 += " #e(봉인됨)#n";
                 * }
                 * v3 += "\r\n";
                 */
                // v3 += "\r\n#b#L0#แต่ละ인 석판 กระเป๋า บ้าน어 넣.#l";
                v3 += "\r\n";
                v3 += "\r\n#b#L1#1째 석판 홈 ล็อก ตั้งค่า เปลี่ยน하겠.#l";
                v3 += "\r\n#b#L2#2째 석판 홈 ล็อก ตั้งค่า เปลี่ยน하겠.#l";
                v3 += "\r\n#b#L3#3째 석판 홈 ล็อก ตั้งค่า เปลี่ยน하겠.#l";
                v3 += "\r\n#b#L4#4째 석판 홈 ล็อก ตั้งค่า เปลี่ยน하겠.#l";
                v3 += "\r\n#b#L5#5째 석판 홈 ล็อก ตั้งค่า เปลี่ยน하겠.#l";

                int v4 = self.askMenu(v3);
                switch (v4) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5: {
                        if (getPlayer().getOneInfoQuestInteger(133333, "lock" + v4) > 0) {
                            getPlayer().updateOneInfo(133333, "lock" + v4, "0");
                            if (DBConfig.isGanglim) {
                                self.say("#fs11##b" + (v4) + "째 홈 ล็อก ปลดล็อก 되었");
                            } else {
                                self.say("#b" + (v4) + "째 홈 ล็อก ปลดล็อก 되었");
                            }

                        } else {
                            getPlayer().updateOneInfo(133333, "lock" + v4, "1");
                            if (DBConfig.isGanglim) {
                                self.say("#fs11##b" + (v4) + "째 홈 ล็อก ตั้งค่า 되었");
                            } else {
                                self.say("#b" + (v4) + "째 홈 ล็อก ปลดล็อก 되었");
                            }
                        }
                        break;
                    }
                }
                break;
            }

            case 2: {
                String v3 = "";
                if (DBConfig.isGanglim) {
                    v3 += "#fs11#";
                    v3 += "แต่ละ인석 더 จำเป็น해? 검 마법사 지하 서고 가지고 올게.\r\n\r\n고용비 อย่างไร 지불할래?\r\n\r\n#b";
                    v3 += "#L4#강림 3,000 แคช 지불 แต่ละ인석 ได้รับ.#l\r\n";
                    v3 += "#L5#Brilliant Light Crystal 600ชิ้น 지불 แต่ละ인석 ได้รับ.#l\r\n";
                    v3 += "#L3#นิดหน่อย 더 고민해 볼게.#l";
                } else {
                    v3 += "แต่ละ인석 더 จำเป็น해? 검 마법사 지하 서고 가지고 올게.\r\n\r\n고용비 อย่างไร 지불할래?\r\n\r\n#b";
                    v3 += "#L0#คะแนน 3,000 지불 แต่ละ인석 ได้รับ.#l\r\n";
                    v3 += "#L1#คะแนนโปรโมชั่น 3,000 지불 แต่ละ인석 ได้รับ.#l\r\n";
                    v3 += "#L2#Meso 1,500,000,000 지불 แต่ละ인석 ได้รับ.#l\r\n";
                    v3 += "#L3#นิดหน่อย 더 고민해 볼게.#l";
                }
                int v4 = self.askMenu(v3);
                switch (v4) {
                    case 0: {
                        buyStone(3000, 1, 0, 80);
                        break;
                    }
                    case 1: {
                        buyStone(3000, 1, 1, 60);
                        break;
                    }
                    case 2: {
                        buyStone(1500000000, 1, 2, 10);
                        break;
                    }
                    case 4: {
                        buyStone(3000, 1, 3, 80);
                        break;
                    }
                    case 5: {
                        buyStone(600, 1, 4, 10);
                        break;
                    }
                }
                break;
            }
            case 3: {
                initNPC(MapleLifeFactory.getNPC(1530040));
                String v3 = "";
                if (DBConfig.isGanglim) {
                    v3 += "#fs11#";
                }
                v3 += "세공 แต่ละ인 석판 끼워져 있 แต่ละ인석 เป็นไปได้해요.\r\nแต่ละ인석 세공 시 증감하 ตัวเลือก 최วินาที 세공 시 สุ่ม으 정해지는데, 석판 끼워져 있 แต่ละ인석 อะไร인지 따라 정해지 ตัวเลือก 종류 달라질 수 있어요.\r\n\r\n";
                v3 += "#eด้านล่าง ตัวเลือก 중 플러스 ตัวเลือก 1종#n\r\n#b└ 정밀한 แต่ละ인석 : 올Stat 5%, โจมตี력 2%, 마력 2%, Critical Damage 2%, บอส โจมตี력 5%, มอนสเตอร์ ป้องกัน력 무시 5%, Critical โอกาส 5%, สุดท้าย Damage 2%\r\n└ 평범한 แต่ละ인석 : 올Stat 2%, บอส โจมตี력 2%, มอนสเตอร์ ป้องกัน력 무시 2%, Critical โอกาส 2%\r\n\r\n#k";
                v3 += "#eด้านล่าง ตัวเลือก 중 마이너스 ตัวเลือก 1종#n\r\n#b└ 정밀한 แต่ละ인석 : Damage 2% ลด (플러스 ตัวเลือก สุดท้าย Damage %인 경ขวา, สุดท้าย Damage 2% ลด แน่นอน)\r\n평범한 แต่ละ인석 : Damage 1% ลด, 올Stat 1% ลด#k\r\n\r\n";
                self.sayReplacedNpc(v3, 1530040);

                String v4 = "";
                if (DBConfig.isGanglim) {
                    v4 += "#fs11#";
                    v4 += "แต่ละ인석 #e플러스 10ครั้ง 마이너스 10ครั้ง ทั้งหมด 20ครั้ง#n 세공 เป็นไปได้해요. 기운 불ใน정해서 세공 ล้มเหลวสามารถ มี 점 알고 อยู่!\r\n\r\n#r세공 시도 สำเร็จ 여부 무관하게 세공 เป็นไปได้ 횟수 หัก 복구สามารถ 없어요.#k\r\n\r\n#b";
                    v4 += "#L4#강림 1,000 แคช 지불 แต่ละ인석 세공.#l\r\n";
                    v4 += "#L6#강림 3,000 คะแนน 지불 แต่ละ인석 세공.#l\r\n";
                    v4 += "#L5#Brilliant Light Crystal 200ชิ้น 지불 แต่ละ인석 세공.#l\r\n";
                    v4 += "#L99#นิดหน่อย 더 고민해 볼게.#l\r\n";
                } else {
                    v4 += "แต่ละ인석 #e플러스 10ครั้ง 마이너스 10ครั้ง ทั้งหมด 20ครั้ง#n 세공 เป็นไปได้해요. 기운 불ใน정해서 세공 ล้มเหลวสามารถ มี 점 알고 อยู่!\r\n\r\n#r세공 시도 สำเร็จ 여부 무관하게 세공 เป็นไปได้ 횟수 หัก 복구สามารถ 없어요.#k\r\n\r\n#b";
                    v4 += "#L0#คะแนน 1,000 지불 แต่ละ인석 세공.#l\r\n";
                    v4 += "#L1#คะแนนโปรโมชั่น 1,000 지불 แต่ละ인석 세공.#l\r\n";
                    v4 += "#L2#Meso 500,000,000 지불 แต่ละ인석 세공.#l\r\n";
                    v4 += "#L3#นิดหน่อย 더 고민해 볼게.#l\r\n";
                }

                int v5 = self.askMenu(v4);
                doCraft(ImprintedStonePayType.getType(v5));
                break;
            }
        }
    }

    public void doCraft(ImprintedStonePayType payType) {
        String v6 = "";
        if (payType == null) {
            return;
        }
        if (DBConfig.isGanglim) {
            v6 += "#fs11#";
        }
        v6 += "세공 원하 홈 เลือก해สัปดาห์세요. เลือก한 홈 장착된 แต่ละ인석 세공 ดำเนินการ.";
        // int unlock1 = getPlayer().getOneInfoQuestInteger(133333, "unlock1");
        // int unlock2 = getPlayer().getOneInfoQuestInteger(133333, "unlock2");

        v6 += "\r\n\r\n#b#L0#1째 홈 แต่ละ인석 세공할게.#l\r\n";
        v6 += "#b#L1#2째 홈 แต่ละ인석 세공할게.#l\r\n";
        v6 += "#b#L2#3째 홈 แต่ละ인석 세공할게.#l\r\n";
        // if (unlock1 == 1) {
        v6 += "#b#L3#4째 홈 แต่ละ인석 세공할게.#l\r\n";
        // }
        // if (unlock2 == 1) {
        v6 += "#b#L4#5째 홈 แต่ละ인석 세공할게.#l\r\n";
        // }

        int v7 = self.askMenu(v6);

        int index = v7 + 1;

        int itemID = getPlayer().getOneInfoQuestInteger(133333, "equip" + index);

        if (itemID == 0) {
            self.say("#e" + index + "홈#n 장착된 แต่ละ인석 없 걸요? 다시 ยืนยัน해สัปดาห์시고 시도해สัปดาห์세요!");
            return;
        }
        int remainCraftPlus = 10 - getPlayer().getOneInfoQuestInteger(133333, "craftPlus" + index);
        int remainCraftMinus = 10 - getPlayer().getOneInfoQuestInteger(133333, "craftMinus" + index);
        int plusOption_ = getPlayer().getOneInfoQuestInteger(133333, "plusOption" + index);
        int minusOption_ = getPlayer().getOneInfoQuestInteger(133333, "minusOption" + index);
        int plusValue = getPlayer().getOneInfoQuestInteger(133333, "plusValue" + index);
        int minusValue = getPlayer().getOneInfoQuestInteger(133333, "minusValue" + index);

        ImprintedStoneOption plusOption = ImprintedStoneOption.getByOption(plusOption_);
        ImprintedStoneOption minusOption = ImprintedStoneOption.getByOption(minusOption_);

        String v8 = "";
        if (DBConfig.isGanglim) {
            v8 += "#fs11#";
        }
        v8 += "#e[" + index + "째 홈]#n\r\n";
        v8 += "#b#i" + itemID + "# #z" + itemID + "##k\r\n\r\n";
        v8 += "#e남 플러스 세공 횟수 : " + remainCraftPlus + "\r\n";
        v8 += "#e남 마이너스 세공 횟수 : " + remainCraftMinus + "\r\n";
        v8 += "#b플러스 ตัวเลือก : " + String.format(plusOption.getDesc(), "+", plusValue, "%", "증") + "\r\n";
        v8 += ("#r마이너스 ตัวเลือก : " + String.format(minusOption.getDesc(), "-", minusValue, "%", "ลด") + "\r\n");
        if (payType == ImprintedStonePayType.Donation) {
            v8 += "ปัจจุบัน มี คะแนน : " + NumberFormat.getInstance().format(getPlayer().getRealCash());
        } else if (payType == ImprintedStonePayType.Promotion) {
            v8 += "ปัจจุบัน มี คะแนนโปรโมชั่น : " + NumberFormat.getInstance().format(getPlayer().getHongboPoint());
        } else if (payType == ImprintedStonePayType.Meso) {
            v8 += "ปัจจุบัน Meso ที่มี : " + NumberFormat.getInstance().format(getPlayer().getMeso());
        } else if (payType == ImprintedStonePayType.RoyalCash) {
            v8 += "#k#eปัจจุบัน มี แคช : " + NumberFormat.getInstance().format(getPlayer().getCashPoint());
        } else if (payType == ImprintedStonePayType.RoyalDPoint) {
            v8 += "#k#eปัจจุบัน คะแนนที่มี : " + NumberFormat.getInstance().format(getPlayer().getDonationPoint());
        } else if (payType == ImprintedStonePayType.RoyalRedBall) {
            v8 += "#k#eปัจจุบัน มี Brilliant Light Crystal : " + getPlayer().getItemQuantity(4031227, false);
        }
        v8 += "\r\n\r\n#k#n플러스, 마이너스 중 어떤 것 세공ต้องการหรือไม่?\r\n";
        v8 += "#b#L0#플러스 세공 ดำเนินการ할게.#l\r\n";
        v8 += "#b#L1#마이너스 세공 ดำเนินการ할게.#l\r\n";
        v8 += "#b#L2#좀 더 생แต่ละ해볼게#l\r\n";
        int v9 = self.askMenu(v8);
        switch (v9) {
            case 0: {
                doPlusCraft(itemID, index, payType);
                break;
            }
            case 1: {
                doMinusCraft(itemID, index, payType);
                break;
            }
        }
    }

    public void doMinusCraft(int itemID, int index, ImprintedStonePayType payType) {
        String payMessage = "1,000 คะแนน";
        if (payType == ImprintedStonePayType.Promotion) {
            payMessage = "1,000 คะแนนโปรโมชั่น";
        } else if (payType == ImprintedStonePayType.Meso) {
            payMessage = "500,000,000 Meso";
        } else if (payType == ImprintedStonePayType.RoyalCash) {
            payMessage = "1,000 강림 แคช";
        } else if (payType == ImprintedStonePayType.RoyalDPoint) {
            payMessage = "3,000 คะแนน";
        } else if (payType == ImprintedStonePayType.RoyalRedBall) {
            payMessage = "Brilliant Light Crystal 200ชิ้น";
        }
        if (DBConfig.isGanglim) {
            if (0 == self.askYesNo("#fs11#จริงๆ #e" + payMessage + "#n ใช้ 세공 할 건가요?")) {
                return;
            }
        } else {
            if (0 == self.askYesNo("จริงๆ #e" + payMessage + "#n ใช้ 세공 할 건가요?")) {
                return;
            }
        }

        int craftCount = getPlayer().getOneInfoQuestInteger(133333, "craftMinus" + index);
        if (craftCount >= 10) {
            if (DBConfig.isGanglim) {
                self.say("#fs11#이미 마이너스 세공 เสร็จสมบูรณ์한 แต่ละ인석인 거 เหมือนกัน네요.");
            } else {
                self.say("이미 마이너스 세공 เสร็จสมบูรณ์한 แต่ละ인석인 거 เหมือนกัน네요.");
            }
            return;
        }
        if (payType == ImprintedStonePayType.Donation.getType()) {
            if (getPlayer().getRealCash() < 1000) {
                self.say("คะแนน ไม่เพียงพอ 세공 ดำเนินการสามารถ 없어요.");
                return;
            }
            getPlayer().gainRealCash(-1000, true);
        } else if (payType == ImprintedStonePayType.Promotion.getType()) {
            if (getPlayer().getHongboPoint() < 1000) {
                self.say("คะแนนโปรโมชั่น ไม่เพียงพอ 세공 ดำเนินการสามารถ 없어요.");
                return;
            }
            getPlayer().gainHongboPoint(-1000, true);
        } else if (payType == ImprintedStonePayType.Meso.getType()) {
            if (getPlayer().getMeso() < 500000000) {
                self.say("Meso ไม่เพียงพอ 세공 ดำเนินการสามารถ 없어요.");
                return;
            }
            getPlayer().gainMeso(-500000000, true);
        } else if (payType == ImprintedStonePayType.RoyalCash.getType()) {
            if (getPlayer().getCashPoint() < 1000) {
                self.say("#fs11#강림 แคชไม่เพียงพอ 세공 ดำเนินการสามารถ 없어요.");
                return;
            }
            getPlayer().gainCashPoint(-1000);
        } else if (payType == ImprintedStonePayType.RoyalDPoint.getType()) {
            if (getPlayer().getDonationPoint() < 3000) {
                self.say("#fs11#คะแนน ไม่เพียงพอ 세공 ดำเนินการสามารถ 없어요.");
                return;
            }
            getPlayer().gainDonationPoint(-3000);
        } else if (payType == ImprintedStonePayType.RoyalRedBall.getType()) {
            if (target.exchange(4031227, -200) < 0) {
                self.say("#fs11#Brilliant Light Crystal ไม่เพียงพอ 세공 ดำเนินการสามารถ 없어요.");
                return;
            }
        }
        int plusValue = getPlayer().getOneInfoQuestInteger(133333, "plusValue" + index);
        int plusOption_ = getPlayer().getOneInfoQuestInteger(133333, "plusOption" + index);
        int remainCraftMinus = 10 - craftCount;
        int minusOption_ = getPlayer().getOneInfoQuestInteger(133333, "minusOption" + index);
        int minusValue = getPlayer().getOneInfoQuestInteger(133333, "minusValue" + index);

        ImprintedStoneOption minusOption = ImprintedStoneOption.getByOption(minusOption_);

        if (plusOption_ == 0) {
            if (DBConfig.isGanglim) {
                self.say("#fs11#플러스 세공 1ครั้ง 이상 해야 마이너스 세공 เป็นไปได้해요.");
            } else {
                self.say("플러스 세공 1ครั้ง 이상 해야 마이너스 세공 เป็นไปได้해요.");
            }
            return;
        }
        if (minusOption_ == 0) {
            int[] options = new int[] {
                    ImprintedStoneOption.DamR.getOption(),
                    ImprintedStoneOption.AllStatP.getOption()
            };
            int minusOption2 = itemID == 2432127 ? options[Randomizer.rand(0, 1)]
                    : ImprintedStoneOption.DamR.getOption();
            if (plusOption_ == ImprintedStoneOption.PMDR.getOption()) {
                // 플러스 ตัวเลือก สุดท้าย Damage เพิ่ม면 마이너스 ตัวเลือก แน่นอน으 สุดท้าย Damage
                minusOption2 = ImprintedStoneOption.PMDR.getOption();
            }
            getPlayer().updateOneInfo(133333, "minusOption" + index, String.valueOf(minusOption2));
        }
        int[] rate = new int[] {
                30, 30, 30, 30, 50, 50, 50, 70, 70, 70
        };
        if (Randomizer.isSuccess(rate[craftCount])) {
            int delta = itemID == 2432127 ? 1 : 2;
            int value = getPlayer().getOneInfoQuestInteger(133333, "minusValue" + index);

            getPlayer().updateOneInfo(133333, "minusValue" + index, String.valueOf(value + delta));
        }
        getPlayer().updateOneInfo(133333, "craftMinus" + index,
                String.valueOf(getPlayer().getOneInfoQuestInteger(133333, "craftMinus" + index) + 1));

        minusOption_ = getPlayer().getOneInfoQuestInteger(133333, "minusOption" + index);
        minusValue = getPlayer().getOneInfoQuestInteger(133333, "minusValue" + index);
        minusOption = ImprintedStoneOption.getByOption(minusOption_);
        String v10 = "";
        if (DBConfig.isGanglim) {
            v10 += "#fs11#";
        }
        v10 += "#e[마이너스 세공]#n\r\n#b" + (getPlayer().getOneInfoQuestInteger(133333, "craftMinus" + index))
                + "째 세공 เสร็จสมบูรณ์되었어요.\r\n\r\n";
        v10 += "#e남 마이너스 세공 횟수 : " + (remainCraftMinus - 1) + "\r\n";
        v10 += ("#b마이너스 ตัวเลือก : " + String.format(minusOption.getDesc(), "-", minusValue, "%", "") + "\r\n");
        if (payType == ImprintedStonePayType.Donation) {
            v10 += "#rปัจจุบัน มี คะแนน : " + NumberFormat.getInstance().format(getPlayer().getRealCash());
        } else if (payType == ImprintedStonePayType.Promotion) {
            v10 += "#rปัจจุบัน มี คะแนนโปรโมชั่น : " + NumberFormat.getInstance().format(getPlayer().getHongboPoint());
        } else if (payType == ImprintedStonePayType.Meso) {
            v10 += "#rปัจจุบัน Meso ที่มี : " + NumberFormat.getInstance().format(getPlayer().getMeso());
        } else if (payType == ImprintedStonePayType.RoyalCash) {
            v10 += "#rปัจจุบัน มี 강림 แคช : " + NumberFormat.getInstance().format(getPlayer().getCashPoint());
        } else if (payType == ImprintedStonePayType.RoyalDPoint) {
            v10 += "#rปัจจุบัน มี คะแนน : " + NumberFormat.getInstance().format(getPlayer().getDonationPoint());
        } else if (payType == ImprintedStonePayType.RoyalRedBall) {
            v10 += "#rปัจจุบัน มี Brilliant Light Crystal : " + getPlayer().getItemQuantity(4031227, false);
        }
        objects.utils.FileoutputUtil.log("./TextLog/EquipStoneCraft.txt",
                "แต่ละ인석 세공(마이너스) (ช่อง : " + index + ", ไอเท็มID : " + itemID + ", ตัวเลือก : " + minusOption.name() + ", 값 : "
                        + minusValue + ", 지불 ห้อง식 : " + payType.name() + ", ใช้자 : " + getPlayer().getName() + ")\r\n");

        v10 += "\r\n#b#n#L0#한  더 세공한다.#l\r\n";
        v10 += "#L1#세공 เขา만둔다.#l";
        int v11 = self.askMenu(v10);
        // if (getPlayer().getOneInfoQuestInteger(133333, "craftMinus" + index) >= 10 &&
        // getPlayer().getOneInfoQuestInteger(133333, "craftPlus" + index) >= 10) {
        getPlayer().checkImprintedStone();
        // }
        if (v11 == 0) {
            doMinusCraft(itemID, index, payType);
        }
    }

    public void doPlusCraft(int itemID, int index, ImprintedStonePayType payType) {
        String payMessage = "1,000 คะแนน";
        if (payType == ImprintedStonePayType.Promotion) {
            payMessage = "1,000 คะแนนโปรโมชั่น";
        } else if (payType == ImprintedStonePayType.Meso) {
            payMessage = "500,000,000 Meso";
        } else if (payType == ImprintedStonePayType.RoyalCash) {
            payMessage = "1,000 강림 แคช";
        } else if (payType == ImprintedStonePayType.RoyalDPoint) {
            payMessage = "3,000 คะแนน";
        } else if (payType == ImprintedStonePayType.RoyalRedBall) {
            payMessage = "Brilliant Light Crystal 200ชิ้น";
        }
        if (DBConfig.isGanglim) {
            if (0 == self.askYesNo("#fs11#จริงๆ #e" + payMessage + "#n ใช้ 세공 할 건가요?")) {
                return;
            }
        } else {
            if (0 == self.askYesNo("จริงๆ #e" + payMessage + "#n ใช้ 세공 할 건가요?")) {
                return;
            }
        }

        int craftCount = getPlayer().getOneInfoQuestInteger(133333, "craftPlus" + index);
        if (craftCount >= 10) {
            if (DBConfig.isGanglim) {
                self.say("#fs11#이미 플러스 세공 เสร็จสมบูรณ์한 แต่ละ인석인 거 เหมือนกัน네요.");
            } else {
                self.say("이미 플러스 세공 เสร็จสมบูรณ์한 แต่ละ인석인 거 เหมือนกัน네요.");
            }
            return;
        }
        if (payType == ImprintedStonePayType.Donation.getType()) {
            if (getPlayer().getRealCash() < 1000) {
                self.say("คะแนน ไม่เพียงพอ 세공 ดำเนินการสามารถ 없어요.");
                return;
            }
            getPlayer().gainRealCash(-1000, true);
        } else if (payType == ImprintedStonePayType.Promotion.getType()) {
            if (getPlayer().getHongboPoint() < 1000) {
                self.say("คะแนนโปรโมชั่น ไม่เพียงพอ 세공 ดำเนินการสามารถ 없어요.");
                return;
            }
            getPlayer().gainHongboPoint(-1000, true);
        } else if (payType == ImprintedStonePayType.Meso.getType()) {
            if (getPlayer().getMeso() < 500000000) {
                self.say("Meso ไม่เพียงพอ 세공 ดำเนินการสามารถ 없어요.");
                return;
            }
            getPlayer().gainMeso(-500000000, true);
        } else if (payType == ImprintedStonePayType.RoyalCash.getType()) {
            if (getPlayer().getCashPoint() < 1000) {
                self.say("#fs11#강림 แคชไม่เพียงพอ 세공 ดำเนินการสามารถ 없어요.");
                return;
            }
            getPlayer().gainCashPoint(-1000);
        } else if (payType == ImprintedStonePayType.RoyalDPoint.getType()) {
            if (getPlayer().getDonationPoint() < 3000) {
                self.say("#fs11#คะแนน ไม่เพียงพอ 세공 ดำเนินการสามารถ 없어요.");
                return;
            }
            getPlayer().gainDonationPoint(-3000);
        } else if (payType == ImprintedStonePayType.RoyalRedBall.getType()) {
            if (target.exchange(4031227, -200) < 0) {
                self.say("#fs11#Brilliant Light Crystal ไม่เพียงพอ 세공 ดำเนินการสามารถ 없어요.");
                return;
            }
        }
        int remainCraftPlus = 10 - craftCount;
        int plusOption_ = getPlayer().getOneInfoQuestInteger(133333, "plusOption" + index);
        int plusValue = getPlayer().getOneInfoQuestInteger(133333, "plusValue" + index);
        ImprintedStoneOption plusOption = ImprintedStoneOption.getByOption(plusOption_);

        if (plusOption_ == 0) {
            int[] options = new int[] {
                    ImprintedStoneOption.AllStatP.getOption(),
                    ImprintedStoneOption.BossDamageR.getOption(),
                    ImprintedStoneOption.IgnoreMobPdpR.getOption(),
                    ImprintedStoneOption.CriticalRate.getOption()
            };
            if (itemID == 2432126) {
                options = new int[] {
                        ImprintedStoneOption.AllStatP.getOption(),
                        ImprintedStoneOption.PadR.getOption(),
                        ImprintedStoneOption.MadR.getOption(),
                        ImprintedStoneOption.CriticalDamageR.getOption(),
                        ImprintedStoneOption.BossDamageR.getOption(),
                        ImprintedStoneOption.IgnoreMobPdpR.getOption(),
                        ImprintedStoneOption.CriticalRate.getOption(),
                        ImprintedStoneOption.PMDR.getOption()
                };
            }
            int plusOption2 = options[Randomizer.rand(0, options.length - 1)];
            if (Randomizer.isSuccess(2) && itemID == 2432126) {
                plusOption2 = ImprintedStoneOption.PMDR.getOption();
            }
            getPlayer().updateOneInfo(133333, "plusOption" + index, String.valueOf(plusOption2));
        }

        int[] rate = new int[] {
                70, 70, 70, 50, 50, 50, 50, 30, 30, 30
        };
        int delta = 2;
        int po = getPlayer().getOneInfoQuestInteger(133333, "plusOption" + index);
        if (itemID == 2432126) {
            if (po == ImprintedStoneOption.AllStatP.getOption() || po == ImprintedStoneOption.BossDamageR.getOption() ||
                    po == ImprintedStoneOption.IgnoreMobPdpR.getOption()
                    || po == ImprintedStoneOption.CriticalRate.getOption()) {
                delta = 5;
            }
        }

        if (Randomizer.isSuccess(rate[craftCount])) {
            int value = getPlayer().getOneInfoQuestInteger(133333, "plusValue" + index);
            getPlayer().updateOneInfo(133333, "plusValue" + index, String.valueOf(value + delta));
        }
        getPlayer().updateOneInfo(133333, "craftPlus" + index,
                String.valueOf(getPlayer().getOneInfoQuestInteger(133333, "craftPlus" + index) + 1));

        plusOption_ = getPlayer().getOneInfoQuestInteger(133333, "plusOption" + index);
        plusValue = getPlayer().getOneInfoQuestInteger(133333, "plusValue" + index);
        plusOption = ImprintedStoneOption.getByOption(plusOption_);
        String v10 = "";
        if (DBConfig.isGanglim) {
            v10 += "#fs11#";
        }
        v10 += "#e[플러스 세공]#n\r\n#b" + (getPlayer().getOneInfoQuestInteger(133333, "craftPlus" + index))
                + "째 세공 เสร็จสมบูรณ์되었어요.\r\n\r\n";
        v10 += "#e남 플러스 세공 횟수 : " + (remainCraftPlus - 1) + "\r\n";
        v10 += ("#b플러스 ตัวเลือก : " + String.format(plusOption.getDesc(), "+", plusValue, "%", "") + "\r\n");
        if (payType == ImprintedStonePayType.Donation) {
            v10 += "#rปัจจุบัน มี คะแนน : " + NumberFormat.getInstance().format(getPlayer().getRealCash());
        } else if (payType == ImprintedStonePayType.Promotion) {
            v10 += "#rปัจจุบัน มี คะแนนโปรโมชั่น : " + NumberFormat.getInstance().format(getPlayer().getHongboPoint());
        } else if (payType == ImprintedStonePayType.Meso) {
            v10 += "#rปัจจุบัน Meso ที่มี : " + NumberFormat.getInstance().format(getPlayer().getMeso());
        } else if (payType == ImprintedStonePayType.RoyalCash) {
            v10 += "#rปัจจุบัน มี 강림 แคช : " + NumberFormat.getInstance().format(getPlayer().getCashPoint());
        } else if (payType == ImprintedStonePayType.RoyalDPoint) {
            v10 += "#rปัจจุบัน มี คะแนน : " + NumberFormat.getInstance().format(getPlayer().getDonationPoint());
        } else if (payType == ImprintedStonePayType.RoyalRedBall) {
            v10 += "#rปัจจุบัน มี Brilliant Light Crystal : " + getPlayer().getItemQuantity(4031227, false);
        }
        v10 += "\r\n#b#n#L0#한  더 세공한다.#l\r\n";
        v10 += "#L1#세공 เขา만둔다.#l";
        // if (getPlayer().getOneInfoQuestInteger(133333, "craftMinus" + index) >= 10 &&
        // getPlayer().getOneInfoQuestInteger(133333, "craftPlus" + index) >= 10) {
        getPlayer().checkImprintedStone();
        // }
        objects.utils.FileoutputUtil.log("./TextLog/StoneCraft.txt",
                "แต่ละ인석 세공(플러스) (ช่อง : " + index + ", ไอเท็มID : " + itemID + ", ตัวเลือก : " + plusOption.name() + ", 값 : "
                        + plusValue + ", 지불 ห้อง식 : " + payType.name() + ", ใช้자 : " + getPlayer().getName() + ")\r\n");

        int v11 = self.askMenu(v10);
        if (v11 == 0) {
            doPlusCraft(itemID, index, payType);
        }
    }

    public void JinPromotionReward() {
        if (DBConfig.isGanglim) {
            return;
        }
        initNPC(MapleLifeFactory.getNPC(9010032));

        var v0 = "ใน녕ทำ, 운영자님.\r\n원활한 홍보 누적 รางวัล 지급 ด้านบน해 เตรียม된 บริการ.\r\n정해진 홍보 วัน수 달성한 유ฉัน ด้านล่าง 훈장 수령 지급해 สัปดาห์세요.\r\n\r\n";
        v0 += "#e[홍보 누적 훈장 지급 기준]#n\r\n";
        v0 += "15วันชา - #b#z1142070##k#r(올Stat 250, 공/마 200)#k\r\n";
        v0 += "100วันชา - #b#z1142072##k#r(올Stat 400, 공/마 350)#k\r\n\r\n";
        v0 += "#b#L0#강림 서포터 훈장 생성.#l\r\n";
        v0 += "#L1#강림 서포터즈 훈장 생성.#l\r\n";

        int v1 = self.askMenu(v0);
        if (v1 == 0) {
            Equip equip = (Equip) MapleItemInformationProvider.getInstance().getEquipById(1142070);
            if (equip != null) {
                equip.setStr((short) 250);
                equip.setDex((short) 250);
                equip.setInt((short) 250);
                equip.setLuk((short) 250);
                equip.setWatk((short) 200);
                equip.setMatk((short) 200);

                MapleInventoryManipulator.addFromDrop(getPlayer().getClient(), equip, true);
                self.say("생성 เสร็จสมบูรณ์.");
            }
        } else if (v1 == 1) {
            Equip equip = (Equip) MapleItemInformationProvider.getInstance().getEquipById(1142072);
            if (equip != null) {
                equip.setStr((short) 400);
                equip.setDex((short) 400);
                equip.setInt((short) 400);
                equip.setLuk((short) 400);
                equip.setWatk((short) 350);
                equip.setMatk((short) 350);

                MapleInventoryManipulator.addFromDrop(getPlayer().getClient(), equip, true);
                self.say("생성 เสร็จสมบูรณ์.");
            }
        }
    }

    public void JinDonationReward() {
        if (DBConfig.isGanglim) {
            return;
        }
        initNPC(MapleLifeFactory.getNPC(9010033));

        int totalPrice = getTotalPrice();
        // totalPrice = 50000000; //TEST

        /*
         * if (true) {
         * self.say("점검중.");
         * return;
         * }
         */
        String v0 = "지급 받 รางวัล เลือก해สัปดาห์시기 โปรด.\r\n\r\n#b";
        if (totalPrice < 25000000) {
            self.say("지급 받 รางวัล ไม่มี.");
        } else {
            boolean find = false;
            if (totalPrice >= 25000000) {
                int check = getPlayer().getOneInfoQuestInteger(1235999, "get_reward_2500");
                if (0 == check) {
                    v0 += "#L2500#2500만원 รางวัล 수령받겠.#l\r\n";
                    find = true;
                }
            }
            if (totalPrice >= 30000000) {
                int check = getPlayer().getOneInfoQuestInteger(1235999, "get_reward_3000");
                if (0 == check) {
                    v0 += "#L3000#3000만원 รางวัล 수령받겠.#l\r\n";
                    find = true;
                }

            }
            if (totalPrice >= 35000000) {
                int check = getPlayer().getOneInfoQuestInteger(1235999, "get_reward_3500");
                if (0 == check) {
                    v0 += "#L3500#3500만원 รางวัล 수령받겠.#l\r\n";
                    find = true;
                }
            }
            if (totalPrice >= 40000000) {
                int check = getPlayer().getOneInfoQuestInteger(1235999, "get_reward_4000");
                if (0 == check) {
                    v0 += "#L4000#4000만원 รางวัล 수령받겠.#l\r\n";
                    find = true;
                }
            }
            if (totalPrice >= 43000000) {
                int under4000 = totalPrice - 43000000;
                int count = under4000 / 3000000 + 1; // 4300때 พื้นฐาน 1ครั้ง เป็นไปได้
                int getCount = getPlayer().getOneInfoQuestInteger(1235999, "get_reward_4300");
                int remain = count - getCount;
                if (remain > 0) {
                    v0 += "#L4300#4300만원 รางวัล 수령받겠. (" + remain + "ครั้ง 수령 เป็นไปได้)#l\r\n";
                    find = true;
                }
            }
            if (!find) {
                self.say("지급 받 รางวัล ไม่มี.");
                return;
            }
        }
        int v1 = self.askMenu(v0);
        switch (v1) {
            case 2500:
                if (getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() > 1) {
                    Equip equip = (Equip) MapleItemInformationProvider.getInstance().getEquipById(1142107);
                    if (equip != null) {
                        equip.setStr((short) 6500);
                        equip.setDex((short) 6500);
                        equip.setInt((short) 6500);
                        equip.setLuk((short) 6500);
                        equip.setWatk((short) 5500);
                        equip.setMatk((short) 5500);

                        if (target.exchange(2049376, 3, 2432098, 8, 5121060, 50) > 0) {
                            MapleInventoryManipulator.addFromDrop(getPlayer().getClient(), equip, true);
                            getPlayer().setEnchantPoint(getPlayer().getEnchantPoint() + 250000);
                            getPlayer().updateOneInfo(1235999, "get_reward_2500", "1");
                            self.say("รางวัล 지급 เสร็จสมบูรณ์.");
                        }
                    }
                } else {
                    self.say("อุปกรณ์ กระเป๋า 공ระหว่าง เพิ่มพื้นที่ 다시 시도해สัปดาห์세요.");
                }
                break;
            case 3000:
                if (getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() > 1) {
                    Equip equip = (Equip) MapleItemInformationProvider.getInstance().getEquipById(1142108);
                    if (equip != null) {
                        equip.setStr((short) 6800);
                        equip.setDex((short) 6800);
                        equip.setInt((short) 6800);
                        equip.setLuk((short) 6800);
                        equip.setWatk((short) 5800);
                        equip.setMatk((short) 5800);

                        MapleInventoryManipulator.addFromDrop(getPlayer().getClient(), equip, true);

                        getPlayer().gainRealCash(1500000, true);
                        getPlayer().updateOneInfo(1235999, "get_reward_3000", "1");
                        self.say("รางวัล 지급 เสร็จสมบูรณ์.");
                    }
                } else {
                    self.say("อุปกรณ์ กระเป๋า 공ระหว่าง เพิ่มพื้นที่ 다시 시도해สัปดาห์세요.");
                }
                break;
            case 3500:
                if (getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() > 1) {
                    Equip equip = (Equip) MapleItemInformationProvider.getInstance().getEquipById(1142109);
                    if (equip != null) {
                        equip.setStr((short) 7200);
                        equip.setDex((short) 7200);
                        equip.setInt((short) 7200);
                        equip.setLuk((short) 7200);
                        equip.setWatk((short) 6200);
                        equip.setMatk((short) 6200);

                        if (target.exchange(2049376, 5, 2432098, 8, 2432097, 1, 5121060, 50) > 0) {
                            MapleInventoryManipulator.addFromDrop(getPlayer().getClient(), equip, true);
                            getPlayer().setEnchantPoint(getPlayer().getEnchantPoint() + 300000);
                            getPlayer().updateOneInfo(1235999, "get_reward_3500", "1");
                            self.say("รางวัล 지급 เสร็จสมบูรณ์.");
                        }
                    }
                } else {
                    self.say("อุปกรณ์ กระเป๋า 공ระหว่าง เพิ่มพื้นที่ 다시 시도해สัปดาห์세요.");
                }
                break;
            case 4000:
                // SAVIOR
                if (getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() > 1) {
                    if (0 == getPlayer().getOneInfoQuestInteger(1235999, "get_reward_4000")) {
                        Equip equip = (Equip) MapleItemInformationProvider.getInstance().getEquipById(1142110);
                        if (equip != null) {
                            equip.setStr((short) 7500);
                            equip.setDex((short) 7500);
                            equip.setInt((short) 7500);
                            equip.setLuk((short) 7500);
                            equip.setWatk((short) 6500);
                            equip.setMatk((short) 6500);

                            getPlayer().gainRealCash(2000000, true);
                            MapleInventoryManipulator.addFromDrop(getPlayer().getClient(), equip, true);
                            getPlayer().updateOneInfo(1235999, "get_reward_4000", "1");
                            self.say("SAVIOR รางวัล 지급 เสร็จสมบูรณ์.");
                        }
                    }
                } else {
                    self.say("อุปกรณ์ กระเป๋า 공ระหว่าง เพิ่มพื้นที่ 다시 시도해สัปดาห์세요.");
                }
                break;
            case 4300:
                boolean find = false;
                // ENDLESS
                int under4000 = totalPrice - 43000000;
                int count = under4000 / 3000000 + 1;
                int getCount = getPlayer().getOneInfoQuestInteger(1235999, "get_reward_4300");
                int remain = count - getCount;
                if (remain > 0) {
                    if (getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() > 6) {
                        getPlayer().updateOneInfo(1235999, "get_reward_4300", String.valueOf(getCount + 1));

                        int[] endless = { 1142242, 1142243, 1142244, 1142245, 1142246, 1142247 };
                        for (int end : endless) {
                            Equip equip = (Equip) MapleItemInformationProvider.getInstance().getEquipById(end);
                            if (equip != null) {
                                equip.setStr((short) 12000);
                                equip.setDex((short) 12000);
                                equip.setInt((short) 12000);
                                equip.setLuk((short) 12000);
                                equip.setWatk((short) 10000);
                                equip.setMatk((short) 10000);
                                equip.setExpiration(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 30));

                                MapleInventoryManipulator.addFromDrop(getPlayer().getClient(), equip, true);
                            }
                        }
                        find = true;
                        self.say("ENDLESS รางวัล 지급 เสร็จสมบูรณ์.");
                    } else {
                        self.say("ENDLESS รางวัล 지급 중 กระเป๋า 공ระหว่าง ไม่พอ.");
                        return;
                    }
                }
                if (!find) {
                    self.say("4300만원 누적 รางวัล 중 받 수 있 누적 รางวัล ไม่มี.\r\n#e(" + count + "ครั้ง 수령 เป็นไปได้ 중 " + remain + "ครั้ง 수령함)");
                }
                break;
        }
    }

    public int getTotalPrice() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = DBConnection.getConnection()) {
            ps = con.prepareStatement("SELECT * FROM `donation_log` WHERE `account` = ?");
            ps.setString(1, getClient().getAccountName());
            rs = ps.executeQuery();

            int totalPoint = 0;
            while (rs.next()) {
                String name = rs.getString("name");
                String p = rs.getString("price").replace(",", "");

                if (!name.contains("패키지") && !name.contains("어린이날") || name.contains("추석") || name.contains("가정의달")
                        || name.contains("3สัปดาห์ปี") || name.contains("크리스마스") || name.contains("상시패키지")
                        || name.contains("2023")) {
                    totalPoint += Integer.parseInt(p);
                }
            }
            return totalPoint;
        } catch (SQLException e) {
            return -1;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                    ps = null;
                }
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            } catch (SQLException e) {
            }
        }
    }

    public void RestPoint() {
        if (DBConfig.isGanglim) {
            return;
        }
        initNPC(MapleLifeFactory.getNPC(9010033));
        int restPoint = getPlayer().getOneInfoQuestInteger(QuestExConstants.JinRestPointReward.getQuestID(),
                "RestPoint");
        String text = "ใน녕ทำ.\r\n"
                + "모험가님 성장 돕기 ด้านบน해 เตรียม된 출석 ระบบ.\r\n"
                + "잠시 앉아서 쉬고 계시면 ฉัน희 เตรียม한 선น้ำ 드릴게요!\r\n\r\n"
                + "선น้ำ 휴식 시ระหว่าง 540นาที 누적될 때마다 하ฉัน씩!\r\n"
                + "ทั้งหมด 15ชิ้น 받으실 수 있으니까 참고해 สัปดาห์세요!\r\n\r\n"
                + "ปัจจุบัน 누적 휴식 시ระหว่าง : " + restPoint + "นาที\r\n\r\n";

        text += "#L0# 출석 ระบบ 관해 자세히 알고 싶.#l\r\n";
        text += "#L1# 누적 휴식 시ระหว่าง รางวัล 수령 싶.#l\r\n";
        text += "#L2# 누적 휴식 시ระหว่าง เริ่มต้น화 싶.#l\r\n";

        int menu = self.askMenu(text);

        int[][] restTimeRewards = {
                { 540, 2632860, 1 },
                { 1080, 5680157, 3 },
                { 1620, 2049360, 10 },
                { 2160, 2434557, 3 },
                { 2700, 2439605, 5 },
                { 3240, 2028273, 5 },
                { 3780, 5068300, 3 },
                { 4320, 5121060, 2 },
                { 4860, 2049762, 1 },
                { 5400, 2049360, 10 },
                { 5940, 2434557, 5 },
                { 6480, 2049371, 3 },
                { 7020, 2434558, 3 },
                { 7560, 2049376, 1 },
                { 8100, 5060048, 10 }
        };

        if (menu == 0) {
            text = "누적 휴식 시ระหว่าง 따라 ด้านล่าง รางวัล ได้รับสามารถ ,\r\n";
            text += "휴식 시ระหว่าง หมู่บ้าน 의자 และ 라이딩 앉아 있 동ใน 누적.\r\n";
            for (int[] timeReward : restTimeRewards) {
                int time = timeReward[0];
                int itemID = timeReward[1];
                int itemQty = timeReward[2];

                text += time + "นาที - #i" + itemID + "# #t" + itemID + "# " + itemQty + "ชิ้น \r\n";
            }
            self.sayOk(text);
            return;
        } else if (menu == 1) {
            text = "휴식시ระหว่าง 누적 รางวัล ด้านล่าง เหมือนกัน. (ปัจจุบัน 누적 : " + restPoint + "นาที)\r\n";
            for (int i = 0; i < restTimeRewards.length; i++) {
                int[] timeReward = restTimeRewards[i];
                int time = timeReward[0];
                int itemID = timeReward[1];
                int itemQty = timeReward[2];
                String timeKey = "recv" + time;
                int timeValue = getPlayer().getOneInfoQuestInteger(QuestExConstants.JinRestPointReward.getQuestID(),
                        timeKey);
                if (restPoint < time) {
                    text += "#L" + i + "#" + time + "นาที #i" + itemID + "# #t" + itemID + "# " + itemQty
                            + "ชิ้น #r수령불#k#l\r\n";
                } else {
                    text += "#L" + i + "#" + time + "นาที #i" + itemID + "# #t" + itemID + "# " + itemQty + "ชิ้น "
                            + (timeValue == 0 ? "#b수령เป็นไปได้#k" : "#r수령เสร็จสมบูรณ์#k") + "#l\r\n";
                }
            }
            int sel = self.askMenu(text);
            if (sel < 0 || sel >= restTimeRewards.length)
                return;
            int[] timeReward = restTimeRewards[sel];
            int time = timeReward[0];
            int itemID = timeReward[1];
            int itemQty = timeReward[2];
            String timeKey = "recv" + time;
            int timeValue = getPlayer().getOneInfoQuestInteger(QuestExConstants.JinRestPointReward.getQuestID(),
                    timeKey);
            if (restPoint < time) {
                self.sayOk("휴식 시ระหว่าง ไม่เพียงพอ 해당รางวัล 수령하지 못แล้ว.");
                return;
            }
            if (timeValue == 0) {
                if (getPlayer().getInventory(GameConstants.getInventoryType(itemID)).getNumFreeSlot() > 0) {
                    getPlayer().gainItem(itemID, itemQty);
                    getPlayer().updateOneInfo(QuestExConstants.JinRestPointReward.getQuestID(), timeKey, "1");
                    self.sayOk("รางวัล 지급되었.");
                } else {
                    self.sayOk("กระเป๋า 공ระหว่าง ไม่เพียงพอ รางวัล 수령하지 못แล้ว.");
                }
            } else {
                self.sayOk("해당 รางวัล 이미 수령하였.");
            }
            return;
        } else if (menu == 2) {
            text = "누적 휴식 시ระหว่าง เริ่มต้น화하기 ด้านบน해서 \r\n#b60,000 คะแนน#k จำเป็น.\r\n";
            text += "เริ่มต้น화 시 수령하지 않 รางวัล 수령สามารถ 없으니,\r\n";
            text += "반드시 수령하지 않 รางวัล 있는지 ยืนยัน해 สัปดาห์세요.\r\n\r\n";
            text += "#fc0xFFB2B2B2#ปัจจุบัน 누적 휴식 시ระหว่าง : " + restPoint + "นาที#k\r\n";
            text += "#L0#누적 휴식 시ระหว่าง เริ่มต้น화.#l";
            if (0 == self.askMenu(text)) {
                if (getPlayer().getRealCash() < 60000) {
                    self.say("คะแนน ไม่เพียงพอ เริ่มต้น화สามารถ ไม่มี.");
                    return;
                }
                getPlayer().gainRealCash(-60000);
                getPlayer().updateOneInfo(QuestExConstants.JinRestPointReward.getQuestID(), "RestPoint", "0");
                for (int i = 0; i < restTimeRewards.length; i++) {
                    int[] timeReward = restTimeRewards[i];
                    int time = timeReward[0];
                    String timeKey = "recv" + time;
                    getPlayer().updateOneInfo(QuestExConstants.JinRestPointReward.getQuestID(), timeKey, "0");
                }
                self.sayOk("เริ่มต้น화 เสร็จสมบูรณ์.");
            }
            return;
        }
    }

    public void DailyMission() {
        if (DBConfig.isGanglim) {
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String today = sdf.format(System.currentTimeMillis());
        int missionTime = getPlayer().getOneInfoQuestInteger(QuestExConstants.JinQuestExAccount.getQuestID(),
                "DailyMissionTime");
        if (missionTime == 0 || Integer.parseInt(today) != missionTime) { // 첫เริ่ม และ 날짜바뀜
            getPlayer().updateOneInfo(QuestExConstants.JinQuestExAccount.getQuestID(), "DailyMissionTime", today);
            getPlayer().updateOneInfo(QuestExConstants.JinQuestExAccount.getQuestID(), "DailyMissionClear", "0");
            getPlayer().updateOneInfo(QuestExConstants.JinQuestExAccount.getQuestID(), "DailyLevelMob", "0");
            getPlayer().updateOneInfo(QuestExConstants.JinQuestExAccount.getQuestID(), "DailyRuneUse", "0");
        }
        int dailyLevelMob = getPlayer().getOneInfoQuestInteger(QuestExConstants.JinQuestExAccount.getQuestID(),
                "DailyLevelMob");
        int dailyRuneUse = getPlayer().getOneInfoQuestInteger(QuestExConstants.JinQuestExAccount.getQuestID(),
                "DailyRuneUse");
        int dailyMissionClear = getPlayer().getOneInfoQuestInteger(QuestExConstants.JinQuestExAccount.getQuestID(),
                "DailyMissionClear");
        boolean checkLevelMob = dailyLevelMob >= 15000;
        boolean checkRuneUse = dailyRuneUse >= 5;
        if (dailyMissionClear > 0) {
            self.sayOk("금วัน วันวัน ภารกิจ 이미 เสร็จสมบูรณ์하셨.");
            return;
        }
        String text = "ด้านล่าง ภารกิจ ก่อน부 เสร็จสมบูรณ์ 네오 스톤 200ชิ้น 수령สามารถ .\r\n\r\n";
        text += "- เลเวล 범ด้านบน มอนสเตอร์ 15,000 마리 처치 " + (checkLevelMob ? "#bเสร็จสมบูรณ์#k" : ("#bดำเนินการ중(" + dailyLevelMob + "/15000)#k"))
                + "\r\n";
        text += "- 룬 5ครั้ง ใช้ " + (checkRuneUse ? "#bเสร็จสมบูรณ์#k" : ("#bดำเนินการ중(" + dailyRuneUse + "/5)#k")) + "\r\n\r\n";
        text += "#L0#วันวันภารกิจ รางวัล 수령.#l";
        if (0 == self.askMenu(text)) {
            if (checkLevelMob && checkRuneUse) {
                getPlayer().gainStackEventGauge(0, 200, true);
                getPlayer().updateOneInfo(QuestExConstants.JinQuestExAccount.getQuestID(), "DailyMissionClear", "1");
                self.sayOk("วันวัน ภารกิจ เสร็จสมบูรณ์ 네오 스톤 200ชิ้น ได้รับ하였.");
            } else {
                self.sayOk("아직 วันวัน ภารกิจ ทั้งหมด 클리어하지 않았.");
                return;
            }
        }
    }

    public void sendCubeLevelUpInfo() {
        GradeRandomOption[] options = { GradeRandomOption.Red, GradeRandomOption.Black, GradeRandomOption.Additional,
                GradeRandomOption.AmazingAdditional };
        String cubeTotalInfo = "큐브 ระดับ ขึ้น #b남 큐브 ใช้ 횟수#k ยืนยัน해 ดู.\r\n"
                + "해당 수치 ระดับ ขึ้น 시 เริ่มต้น화, #bบัญชี ภายใน 공유#k.\r\n\r\n";
        for (GradeRandomOption option : options) {
            String cubeString = "";
            switch (option) {
                case Red:
                    cubeString = "레드 큐브";
                    break;
                case Black:
                    cubeString = "블랙 큐브";
                    break;
                case Additional:
                    cubeString = "에디셔널 큐브";
                    break;
                case AmazingAdditional:
                    cubeString = "화이트 에디셔널 큐브";
                    break;
            }
            cubeTotalInfo += "#e[" + cubeString + "]#n\r\n";
            String[] levelUps = { "RtoE", "EtoU", "UtoL" };
            for (String levelUp : levelUps) {
                int tryCount = getPlayer().getOneInfoQuestInteger(QuestExConstants.CubeLevelUp.getQuestID(),
                        option.toString() + levelUp);
                int grade = 1;
                String gradeString = "";
                if (levelUp.equals("RtoE")) {
                    grade = 1;
                    gradeString = "Rare Epic";
                } else if (levelUp.equals("EtoU")) {
                    grade = 2;
                    gradeString = "Epic Unique";
                } else if (levelUp.equals("UtoL")) {
                    grade = 3;
                    gradeString = "Unique 레ก่อน더리";
                }
                int levelUpCount = GameConstants.getCubeLevelUpCount(option, grade);
                cubeTotalInfo += gradeString + " " + levelUpCount + "ครั้ง 중 " + tryCount + "ครั้ง 누적\r\n";
            }
            if (option != GradeRandomOption.AmazingAdditional) {
                cubeTotalInfo += "\r\n";
            }
        }
        self.sayOk(cubeTotalInfo);
    }
}