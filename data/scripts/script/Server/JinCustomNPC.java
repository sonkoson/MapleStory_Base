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

            StringBuilder sb = new StringBuilder("Extra Ability Result (");
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
        v0 += "\r\n#b#L0#รีเซ็ตตัวเลือกใน Preset ปัจจุบัน#l\r\n";
        v0 += "#b#L1#เปลี่ยน Preset.";
        boolean presetLock = getPlayer().getOneInfoQuestInteger(787878, "e_preset_open") == 0;
        if (presetLock) {
            v0 += " #e(ล็อก)#n";
        }
        v0 += "#l\r\n#b#L2#เรียนรู้เกี่ยวกับ Extra Ability#l\r\n";
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

                            v4 += "#eระดับปัจจุบัน : #r" + getPlayer().getExtraAbilityGrade().getDesc()
                                    + "#k#n#r\r\n\r\n";
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
                                if (self.askYesNo(
                                        "#fs11##r#eต้องการรีเซ็ตจริงหรือไม่? การรีเซ็ตจะทำให้ตัวเลือกปัจจุบันหายไป.#n#k") == 1) {
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

                            v4 += "#eระดับปัจจุบัน : #r" + getPlayer().getExtraAbilityGrade().getDesc()
                                    + "#k#n#r\r\n\r\n";
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
                                if (self.askYesNo(
                                        "#fs11##r#eต้องการรีเซ็ตจริงหรือไม่? การรีเซ็ตจะทำให้ตัวเลือกปัจจุบันหายไป.#n#k") == 1) {
                                    renewOption(ExtraAbilityPayType.Donation);
                                    return;
                                }
                            }
                            break;
                        }
                        case 1: { // คะแนนโปรโมชั่น
                            String v4 = "#e<Extra Ability>#n\r\n#b500 คะแนนโปรโมชั่น#kต้องการรีเซ็ต Extra Ability หรือไม่?\r\n\r\n";

                            v4 += "#eระดับปัจจุบัน : #r" + getPlayer().getExtraAbilityGrade().getDesc()
                                    + "#k#n#r\r\n\r\n";
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
                                if (self.askYesNo(
                                        "#fs11##r#eต้องการรีเซ็ตจริงหรือไม่? การรีเซ็ตจะทำให้ตัวเลือกปัจจุบันหายไป.#n#k") == 1) {
                                    renewOption(ExtraAbilityPayType.Promotion);
                                    return;
                                }
                            }
                            break;
                        }
                        case 2: { // Meso
                            String v4 = "#e<Extra Ability>#n\r\n#b500,000,000 Meso#kต้องการรีเซ็ต Extra Ability หรือไม่?\r\n\r\n";

                            v4 += "#eระดับปัจจุบัน : #r" + getPlayer().getExtraAbilityGrade().getDesc()
                                    + "#k#n#r\r\n\r\n";
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
                                if (self.askYesNo(
                                        "#fs11##r#eต้องการรีเซ็ตจริงหรือไม่? การรีเซ็ตจะทำให้ตัวเลือกปัจจุบันหายไป.#n#k") == 1) {
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
                    v2 += "#b" + nf.format(meso) + " Meso#k ใช้เพื่อเปลี่ยนเป็น #bPreset " + (preset ^ 1)
                            + "#k หรือไม่?\r\n\r\n";
                    if (1 == self.askYesNo(v2)) {
                        if (getPlayer().getMeso() < meso) {
                            self.say("Meso ไม่เพียงพอ ไม่สามารถเปลี่ยน Preset ได้.");
                            return;
                        }
                        getPlayer().gainMeso(-meso, true);
                        getPlayer().setExtraAbilitySlot((preset ^ 1));
                        getPlayer().checkExtraAbility();
                        String v3 = "#fs11##e<Extra Ability>#n\r\n\r\nเปลี่ยนเป็น #b#ePreset "
                                + getPlayer().getExtraAbilitySlot()
                                + "#n#k เรียบร้อยแล้ว#r\r\n\r\n";
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
                            "#fs11##bระบบ Extra Ability#k เป็นช่องทางเติบโตเพิ่มเติมของตัวละคร\r\n\r\nExtra Ability ใช้ #e#rระบบสุ่มออปชั่น 6 แถว#k#n โดยใช้แต้มต่างๆ ในการเปลี่ยนออปชั่นได้\r\n\r\nการเปลี่ยนออปชั่นสามารถใช้ #e#bBrilliant Light Crystal#k#n ได้");
                    self.say(
                            "#fs11#ออปชั่นแถวที่ 4 จะออกระดับ #eVVIP Classic#n,\r\nออปชั่นแถวที่ 5 จะออกระดับ #eMVP Prime#n\r\nเมื่อรีเซ็ตออปชั่นแถวที่ 6 ระดับ #b'Legendary'#k\r\nจะมี #eโอกาสออก 1%#n, หลังจากออกแล้ว จะถูกรีเซ็ตในครั้งถัดไป");
                    String v2 = "#fs11#สามารถตรวจสอบรายละเอียดได้จากหัวข้อด้านล่าง\r\n\r\n#b#L0#อยากรู้เกี่ยวกับ Extra Ability Preset#l\r\n#b#L1#อยากรู้เกี่ยวกับตัวเลือก Extra Ability ตามระดับ#l\r\n#b#L2#อยากรู้เกี่ยวกับโอกาส Extra Ability ตามระดับ#l\r\n#L3#อยากรู้เกี่ยวกับ Extra Ability Lucky Point#l";
                    int v3 = self.askMenu(v2);
                    switch (v3) {
                        case 0: {
                            self.say(
                                    "#fs11#Extra Ability Preset เริ่มต้นมีให้ 1 ช่อง, สามารถใช้ #bCash#k #e50,000#n เพื่อขยายช่องเพิ่มได้\r\n\r\nแต่ละ Preset สามารถตั้งค่าออปชั่นแยกกันได้, ทุกครั้งที่เปลี่ยน Preset จะใช้ #e10,000 ล้าน Meso#n\r\n\r\n#e#r※ การเปลี่ยนออปชั่นจะใช้กับ Preset ปัจจุบัน กรุณาตรวจสอบให้ดีก่อนใช้งาน");
                            break;
                        }
                        case 1: {
                            self.say(
                                    "#fs11##e[ Extra ]#n\r\n\r\n#b- ลดคูลดาวน์ 1~2 วินาที\r\n- อมตะหลังคืนชีพ 2~4 วินาที\r\n- จำนวนมอนสเตอร์ 1.5 เท่า\r\n- Final Damage 5~20%\r\n- ได้รับ EXP 30~50%\r\n- ได้รับ Meso 30~50%\r\n- อัตราดรอปไอเทม 30~50%\r\n- ต้านทานสถานะผิดปกติ");
                            self.say(
                                    "#fs11##e[ Legendary ]#n\r\n\r\n#b- ลดคูลดาวน์ 1~2 วินาที\r\n- ได้รับ EXP 30~50%\r\n- อัตราดรอปไอเทม 20~30%\r\n- ได้รับ Meso 20~30%\r\n- Boss Damage 30~40%\r\n- Ignore Enemy Defense 30~40%\r\n- Critical Rate 12%\r\n- ATT/MATT 12~16%\r\n- ATT/MATT +100~150\r\n- All Stat +18~25%\r\n- All Stat +200~300\r\n- HP +15~18%");
                            self.say(
                                    "#fs11##e[ Unique ]#n\r\n\r\n#b- ได้รับ EXP 10~20%\r\n- อัตราดรอปไอเทม 10~20%\r\n- ได้รับ Meso 10~20%\r\n- Boss Damage 20~30%\r\n- Ignore Enemy Defense 20~30%\r\n- Critical Rate 9~12%\r\n- ATT/MATT 9~12%\r\n- ATT/MATT +80~100\r\n- All Stat +15~21%\r\n- All Stat +100~150\r\n- HP +12~15%");
                            self.say(
                                    "#fs11##e[ Epic ]#n\r\n\r\n#b- Boss Damage 10~20%\r\n- Ignore Enemy Defense 8~12%\r\n- Critical Rate 6~9%\r\n- ATT/MATT 6~9%\r\n- ATT/MATT +50~80\r\n- All Stat +12~15%\r\n- All Stat +80~100\r\n- HP +9~12%");
                            self.say(
                                    "#fs11##e[ Rare ]#n\r\n\r\n#b- ลดความเสียหายที่ได้รับ 60~80%\r\n- STR, DEX, INT, LUK +80~100\r\n- All Stat +50~80\r\n- HP +6~9%\r\n- HP +3000~5000\r\n- ATT/MATT +3~6%\r\n- ATT/MATT +30~50");
                            self.say(
                                    "#fs11##bในทุกแถว#k ตัวเลือกจะปรากฏตามระดับปัจจุบัน แต่โอกาสในแต่ละตัวเลือกอาจแตกต่างกัน");
                            break;
                        }
                        case 2: {
                            self.say(
                                    "#fs11##e[โอกาสอัพเกรดจาก Rare → Epic]#n\r\n#bเพิ่มโอกาส 15%\r\n\r\n\r\n#k#e[โอกาสอัพเกรดจาก Epic → Unique]#n\r\n#bเพิ่มโอกาส 3.8%\r\n\r\n\r\n#k#e[โอกาสอัพเกรดจาก Unique → Legendary]#n\r\n#bเพิ่มโอกาส 1%\r\n#e[โอกาสออกระดับ Extra]#n\r\n#bปรากฏโอกาส 1% ที่ระดับ 'Legendary'");
                            break;
                        }
                        case 3: {
                            self.say(
                                    "#fs11#จะได้รับคะแนนสะสมตามแต้มที่ใช้ดังนี้\r\n\r\n#e#rใช้ #z"
                                            + requestItemID
                                            + "# ได้รับ 3 คะแนน");
                            self.say(
                                    "#fs11#เมื่อเสริมพลัง Lucky Point จะสะสม #e#b30 คะแนน#n#k เมื่อครบจะหัก 30 คะแนนอัตโนมัติ และสุ่มออปชั่นค่าสูงสุด 1~5 แถว\r\n\r\n#eLucky 5 แถว โอกาส 10%\r\nLucky 4 แถว โอกาส 10%\r\nLucky 3 แถว โอกาส 15%\r\nLucky 2 แถว โอกาส 25%\r\nLucky 1 แถว โอกาส 40%");
                            break;
                        }
                    }
                } else {
                    self.say(
                            "#fs11##bระบบ Extra Ability#k เป็นช่องทางเติบโตเพิ่มเติมของตัวละคร\r\n\r\nExtra Ability ใช้ #e#rระบบสุ่มออปชั่น 3 แถว#k#n โดยใช้แต้มต่างๆ ในการเปลี่ยนออปชั่นได้\r\n\r\nการเปลี่ยนออปชั่นสามารถใช้ #e#bPraise Point, Promotion Piont, Meso#k#n ได้\r\nระดับและค่าสูงสุดของออปชั่นจะแตกต่างกันไปตามประเภทของแต้มที่ใช้");
                    self.say(
                            "#fs11#แต่ละ 재화별 ตัวเลือก เปลี่ยน ใช้하 비용 ด้านล่าง เหมือนกัน.\r\n\r\n#rคะแนน : 1,000\r\nคะแนนโปรโมชั่น : 500\r\nMeso : 500,000,000");
                    String v2 = "#fs11#สามารถตรวจสอบรายละเอียดได้จากหัวข้อด้านล่าง\r\n\r\n#b#L0#อยากรู้เกี่ยวกับ Extra Ability Preset#l\r\n#b#L1#อยากรู้เกี่ยวกับตัวเลือก Extra Ability ตามระดับ#l\r\n#b#L2#อยากรู้เกี่ยวกับโอกาส Extra Ability ตามระดับ#l\r\n#L3#อยากรู้เกี่ยวกับ Extra Ability Lucky Point#l";
                    int v3 = self.askMenu(v2);
                    switch (v3) {
                        case 0: {
                            self.say(
                                    "#fs11#เริ่มต้นจะมี Extra Ability Preset พื้นฐานให้ 1 ช่อง, สามารถใช้ #bคะแนน#k และ #bคะแนนโปรโมชั่น#k #e50,000#n เพื่อขยายช่องเพิ่มได้ 1 ช่อง\r\n\r\nแต่ละ Preset สามารถตั้งค่าออปชั่นแยกกันได้, ทุกครั้งที่เปลี่ยน Preset จะใช้ #e3,000 ล้าน Meso#n\r\n\r\n#e#r※ การเปลี่ยนออปชั่นจะใช้กับ Preset ปัจจุบันที่ใช้งานอยู่ กรุณาตรวจสอบให้ดีก่อนใช้งาน");
                            break;
                        }
                        case 1: {
                            self.say(
                                    "#fs11##e[Legendary]#n\r\n\r\n#b- EXP 10~20%\r\n- อัตราดรอปไอเทม 10~20%\r\n- ได้รับ Meso 10~20%\r\n- ลดคูลดาวน์ 1~2 วินาที\r\n- Critical Damage 5~8%\r\n- ATT/MATT 9~12%\r\n- Boss Damage 20~30%\r\n- Ignore Enemy Defense 20~30%\r\n- Critical Rate 9~12%\r\n- All Stat +15~21%\r\n- HP +12~15%\r\n- ATT/MATT +80~100\r\n- All Stat +100~150");
                            self.say(
                                    "#fs11##e[ Unique ]#n\r\n\r\n#b- Boss Damage 10~20%\r\n- Ignore Enemy Defense 8~12%\r\n- Critical Rate 6~9%\r\n- ATT/MATT 6~9%\r\n- All Stat +12~15%\r\n- HP +9~12%\r\n- ATT/MATT +50~80\r\n- All Stat +80~100");
                            self.say(
                                    "#fs11##e[ Epic ]#n\r\n\r\n#b- ATT/MATT 3~6%\r\n- All Stat +9~12%\r\n- HP +6~9%\r\n- ลดความเสียหายที่ได้รับ 60~80%\r\n- STR, DEX, INT, LUK +80~100\r\n- All Stat +50~80\r\n- HP +3000~5000\r\n- ATT/MATT +30~50");
                            self.say(
                                    "#fs11##e[ Rare ]#n\r\n\r\n#b- ลดความเสียหายที่ได้รับ 40~60%\r\n- STR, DEX, INT, LUK +50~80\r\n- All Stat +20~50\r\n- HP +1000~2000\r\n- ATT/MATT +10~30");
                            self.say(
                                    "#fs11##bแถวแรก#k จะสุ่ม #eตัวเลือกระดับปัจจุบัน#n, #bแถวที่สอง / สาม#k จะสุ่ม #eตัวเลือกระดับต่ำกว่าปัจจุบันหนึ่งขั้น#n, โดยมีโอกาสที่ตัวเลือกจะหลุดเรทตามแต้มที่ใช้");
                            self.say(
                                    "#fs11##bตัวเลือกแถวที่สอง#k หากใช้ #eคะแนนและคะแนนโปรโมชั่น#n มี #rโอกาส 30%#k ที่จะสุ่มได้ #eตัวเลือกระดับปัจจุบัน#n, #bตัวเลือกแถวที่สาม#k หากใช้ #eคะแนน#n มี #rโอกาส 10%#k ที่จะสุ่มได้ #eตัวเลือกระดับปัจจุบัน#n");
                            break;
                        }
                        case 2: {
                            self.say(
                                    "#fs11##e[โอกาสอัพเกรดจาก Rare → Epic]#n\r\n#bคะแนน : 15%\r\nคะแนนโปรโมชั่น : 10%\r\nMeso : 5%\r\n\r\n\r\n#k#e[โอกาสอัพเกรดจาก Epic → Unique]#n\r\n#bคะแนน : 3.8%\r\nคะแนนโปรโมชั่น : 2.5%\r\nMeso : 0.9%\r\n\r\n\r\n#k#e[โอกาสอัพเกรดจาก Unique → Legendary]#n\r\n#bคะแนน : 1%\r\nคะแนนโปรโมชั่น : 0.7%\r\nMeso : เป็นไปไม่ได้");
                            break;
                        }
                        case 3: {
                            self.say(
                                    "#fs11#จะได้รับ Lucky Point แตกต่างกันไปตามแต้มที่ใช้ดังนี้\r\n\r\n#e#rใช้ Meso : 1 คะแนน\r\nใช้ คะแนนโปรโมชั่น : 2 คะแนน\r\nใช้ คะแนน : 3 คะแนน");
                            self.say(
                                    "#fs11#เมื่อเสริมพลัง Lucky Point จะสะสม หากเกิน #e#b30 คะแนน#n#k จะหัก 30 คะแนนอัตโนมัติ และสุ่มออปชั่นค่าสูงสุด 1~3 แถว\r\n\r\n#eโอกาส Lucky 3 แถว 20%\r\nโอกาส Lucky 2 แถว 30%\r\nโอกาส Lucky 1 แถว 50%");
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
        String v0 = "ขอลองดูหน่อยสิ ไอเทมที่สัมผัสได้ถึงพลังลึกลับที่ค้นพบที่นี่... ถ้าเจ้ามีสิ่งนั้น ข้าก็สัมผัสได้ถึงพลังที่คล้ายกัน ถ้าไม่ว่าอะไร ข้าจะแลกเปลี่ยนไอเทมที่เจ้ามีให้ได้นะ\r\nถ้าเป็นอุปกรณ์ที่มีพลังสีดำ ข้าสามารถนำมารวมพลังเพื่อสร้างไอเทมใหม่ได้ ลองดูไหม?\r\n\r\n";
        v0 += "#b#L0##ePitch Black Aura#n แลกเปลี่ยน #eHell Point#n#l\r\n";
        v0 += "#L1#ใช้ #eร้านค้า Hell Point#n#l\r\n";
        v0 += "#L2#สร้าง #eไอเทมที่มีพลังที่ถูกปลุก#n#l\r\n";
        v0 += "#L3#ไว้มาใหม่คราวหน้า#l";
        int v1 = self.askMenu(v0);
        switch (v1) {
            case 0: {
                int count = getPlayer().getItemQuantity(4036454, false);
                int hellPoint = getPlayer().getOneInfoQuestInteger(1234999, "hp");
                String v2 = "เจ้ามี #b#i4036454# #z4036454##k อยู่สินะ รู้สึกไม่ค่อยดีเลย... นั่นแหละ ข้าจะเรียกว่า Hell Point แล้วกัน ข้าจะเปลี่ยน #b#z4036454##k ให้เป็น #b3 Hell Point#k ต่อชิ้น คะแนนนี้จะใช้ในการแลกเปลี่ยนกับข้าในอนาคต ส่งมาเท่าที่เจ้าต้องการสิ\r\n\r\n";
                v2 += "#b#eจำนวน #z4036454# ที่มี : " + count + "\r\n";
                v2 += "Hell Point ที่มี : " + nf.format(hellPoint) + "#n\r\n\r\n";
                v2 += "#b#L0#แลกเปลี่ยน #e#z4036454##n เป็น #eHell Point#n#l\r\n";
                v2 += "#L1#ไว้มาใหม่คราวหน้า#l";
                int v3 = self.askMenu(v2);
                if (v3 == 0) {
                    count = getPlayer().getItemQuantity(4036454, false);
                    if (count <= 0) {
                        self.say("ข้าไม่สัมผัสถึงพลังใดๆ จากเจ้าเลย เจ้าเห็นภาพหลอนหรือเปล่า?");
                        return;
                    }
                    String v4 = "เจ้ามี #b#i4036454# #z4036454# " + count
                            + " ชิ้น#k สินะ ต้องการแลกเปลี่ยนกี่ชิ้นเป็น #bHell Point#k?\r\n";
                    int v5 = self.askNumber(v4, count, 1, count);
                    if (v5 > count) {
                        return; // Hack
                    }
                    if (1 == self
                            .askYesNo("#bต้องการแลกเปลี่ยน #b#i4036454# #z4036454# " + v5 + " ชิ้น#k เป็น #b" + (3 * v5)
                                    + " Hell Point#k หรือไม่?")) {
                        if (1 == target.exchange(4036454, -v5)) {
                            getPlayer().updateOneInfo(1234999, "hp", String.valueOf(hellPoint + (3 * v5)));
                            self.say("แลกเปลี่ยนเป็น #b" + (3 * v5) + " Hell Point#k เรียบร้อยแล้ว ลองตรวจสอบดูสิ");
                        } else {
                            self.say("การแลกเปลี่ยนล้มเหลวด้วยเหตุผลที่ไม่ทราบสาเหตุ จะลองใหม่อีกครั้งไหม?");
                        }
                    }
                }
                break;
            }
            case 1: {
                int hellPoint = getPlayer().getOneInfoQuestInteger(1234999, "hp");
                String v2 = "จุดจบของผู้ที่โลภมากมักไม่สวยงาม อยากได้ไอเทมอะไรก็เลือกดูสิ อัศวินจะไม่คืนการตัดสินใจที่ทำไปแล้ว ตัดสินใจให้ดีล่ะ ถ้ามาเสียใจทีหลังก็สายไปแล้ว\r\n#b";
                v2 += "#eHell Point ที่มี : " + nf.format(hellPoint) + "P\r\n\r\n";
                v2 += "#k#e[รายการไอเทม]#n#b\r\n";
                for (int i = 0; i < consumeList.length; ++i) {
                    v2 += "#L" + i + "##i" + consumeList[i] + "# #z" + consumeList[i] + "# #r(" + consumePrice[i]
                            + "P)#b#l\r\n";
                }
                int v3 = self.askMenu(v2);
                hellPoint = getPlayer().getOneInfoQuestInteger(1234999, "hp");
                if (consumePrice[v3] > hellPoint) {
                    self.say("#bHell Point#k ไม่พอสินะ เพื่อที่จะได้สิ่งที่ต้องการ ก็ต้องจ่ายค่าตอบแทนไม่ใช่เหรอ?");
                    return;
                }
                if (1 == target.exchange(consumeList[v3], 1)) {
                    getPlayer().updateOneInfo(1234999, "hp", String.valueOf(hellPoint - consumePrice[v3]));
                    self.say("อยู่นี่แล้ว. ตรวจสอบกระเป๋าของเจ้าดูสิ");
                } else {
                    self.say("กระเป๋าของเจ้าดูเหมือนจะเต็มนะ");
                }
                break;
            }
            case 2: {
                String v2 = "พลังที่ไม่เสถียรราวกับรอยร้าวที่พร้อมจะหายไปได้ทุกเมื่อ อาจจะไม่สำเร็จก็ได้นะ จะลองดูจริงๆ เหรอ? แต่การหนีเมื่อหวาดกลัวก็เป็นทางเลือกหนึ่ง แต่ถ้าสำเร็จ เจ้าจะได้รับพลังที่แข็งแกร่งกว่าเดิม\r\n\r\n#b(หากสร้างล้มเหลว คะแนนแต้มจะหายไป ยกเว้นอุปกรณ์ Pitch Boss ที่ใช้เป็นวัตถุดิบ, หากสำเร็จ ออปชั่นของอุปกรณ์ Pitch Boss ที่ใช้เป็นวัตถุดิบจะถูกสืบทอดไปยังอุปกรณ์ที่ถูกปลุกพลัง)#n#b\r\n\r\n";
                v2 += "#k#e[รายการไอเทม]#n#b\r\n";
                for (int i = 0; i < equipList.length; ++i) {
                    if (i == 3)
                        continue;
                    v2 += "#L" + i + "##i" + equipList[i] + "# #z" + equipList[i] + "##l\r\n";
                }
                v2 += "\r\n\r\n#r※ เมื่อสร้างอุปกรณ์ดังกล่าว สถานะจะเปลี่ยนเป็นแลกเปลี่ยนไม่ได้";
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
                        pointName = "คะแนน Swoo";
                        pointKey = "hell_swoo_point";
                        break;
                    }
                    case 1: {
                        pointName = "คะแนน Damien";
                        pointKey = "hell_demian_point";
                        break;
                    }
                    case 2:
                        pointName = "คะแนน Lucid";
                        pointKey = "hell_lucid_point";
                        break;
                    case 3:
                        pointName = "คะแนน Will";
                        pointKey = "hell_will_point";
                        break;
                    case 4:
                        pointName = "คะแนน Dusk";
                        pointKey = "hell_dusk_point";
                        break;
                    case 5:
                        pointName = "คะแนน Jin Hilla";
                        pointKey = "hell_jinhillah_point";
                        break;
                    case 6:
                        pointName = "คะแนน Dunkel";
                        pointKey = "hell_dunkel_point";
                        break;
                    case 7:
                        pointName = "คะแนน Black Mage";
                        pointKey = "hell_bm_point";
                        p = 5;
                        break;
                    case 8:
                        pointName = "คะแนน Seren";
                        pointKey = "hell_seren_point";
                        break;
                }

                if (pointName.isEmpty() || pointKey.isEmpty()) {
                    self.say("เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุ");
                    return;
                }
                String v4 = "#b#i" + equipID + "# #z" + equipID
                        + "##k อยากได้สินะ เพื่อให้ได้พลังที่แข็งแกร่ง จำเป็นต้องมี #b#z"
                        + baseEquipID
                        + "# 1 ชิ้น#k, #b" + p + " " + pointName
                        + "#k, และ #b#z4036454# 50 ชิ้น#k หากสร้างล้มเหลว อุปกรณ์วัตถุดิบจะไม่หายไป แต่แต้มและพลัง Hell Point จะหายไป จะลองดูไหม?\r\n\r\n\n";
                int point = getPlayer().getOneInfoQuestInteger(787777, pointKey);
                v4 += "#b#eมี " + pointName + " : " + nf.format(point)
                        + "P#n#k\r\n\r\n#e#r※ ระบบจะใช้อุปกรณ์ที่อยู่ด้านหน้าสุดในช่องเก็บของเป็นวัตถุดิบ";
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
                        self.say("ข้าไม่สัมผัสถึงพลังใดๆ จากเจ้าเลย เจ้าเห็นภาพหลอนหรือเปล่า?");
                        return;
                    }
                    if (point < p) {
                        self.say("ดูเหมือนเจ้าจะมี " + pointName + " ไม่พอนะ");
                        return;
                    }
                    int count2 = getPlayer().getInventory(MapleInventoryType.ETC).countByIdWithoutLock(4036454);
                    int removeQty = 50;
                    if (count2 < removeQty) {
                        self.say("ดูเหมือนเจ้าจะมี #b#i4036454# #z4036454##k ไม่พอนะ");
                        return;
                    }
                    // โอกาสสำเร็จ 50%
                    getPlayer().updateOneInfo(787777, pointKey, String.valueOf(point - p));
                    if (Randomizer.isSuccess(50)) {
                        item = getPlayer().getInventory(MapleInventoryType.EQUIP).findByIdWithoutLock(baseEquipID);
                        if (item == null) {
                            self.say("ข้าไม่สัมผัสถึงพลังใดๆ จากเจ้าเลย เจ้าเห็นภาพหลอนหรือเปล่า?");
                            return;
                        }
                        if (1 == target.exchange(baseEquipID, -1, 4036454, -removeQty)) {
                            // ดึงออปชั่นของอุปกรณ์
                            MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
                            Equip equip = (Equip) item;
                            Item newItem = ii.getEquipById(equipID);
                            if (newItem == null) {
                                self.say("เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุ");
                                return;
                            }
                            Equip newEquip = (Equip) newItem;
                            Item baseItem = ii.getEquipById(item.getItemId());
                            if (baseItem == null) {
                                self.say("เกิดข้อผิดพลาดที่ไม่ทราบสาเหตุ");
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
                            // Untradable
                            newEquip.setFlag(newEquip.getFlag() | ItemFlag.POSSIBLE_TRADING.getValue());

                            MapleInventoryManipulator.addbyItem(getClient(), newEquip);

                            self.say(
                                    "สำเร็จแล้ว ข้าสัมผัสได้ถึงพลังที่ไม่รู้จักจากอุปกรณ์ของเจ้า... ราวกับเป็นของปีศาจนั่น");
                        }
                    } else {
                        target.exchange(4036454, -removeQty);
                        self.say("...ล้มเหลว พลังนั้นหายไปแล้ว แต่โชคดีที่อุปกรณ์ของเจ้ายังอยู่ ข้าจะคืนให้");
                    }
                }
                break;
            }
        }
    }

    public void Donation() {
        initNPC(MapleLifeFactory.getNPC(9000041));
        long totalMeso1 = PraiseDonationMeso.getTotalMeso();

        String v0 = "#fs11#สวัสดีนักผจญภัย #b#h0##k, #eGanglim World#n เปิด #e#bกล่องบริจาคช่วยเหลือผู้เล่นใหม่#k#n สำหรับ #bผู้เล่นใหม่#k ที่เพิ่งมาถึง\r\n"
                + "\r\n"
                + "#fs12##e[ ยอดบริจาคปัจจุบัน : #b"
                + NumberFormat.getInstance().format(totalMeso1)
                + " Meso ]#k#n#fs11#\r\n"
                + "\r\n"
                + "#b#L0#ช่วยอธิบายเกี่ยวกับกล่องบริจาคหน่อยครับ#l\r\n"
                + "#L1#ต้องการบริจาค Meso#l\r\n"
                + "#L2#ดูอันดับการบริจาค#l\r\n"
                + "#L3#รับรางวัลการบริจาค#l\r\n"
                + "#L4#รับเงินสนับสนุนผู้เล่นใหม่#l\r\n"
                + "#L5#ใช้ร้านค้า Praise Shop#l";
        int v1 = self.askMenu(v0);
        switch (v1) {
            case 0: {
                String v2 = "#fs11#เลือกหัวข้อที่ต้องการทราบคำอธิบาย\r\n\r\n";
                v2 += "#b#L0#จะบริจาค Meso ได้อย่างไร?#l\r\n";
                // v2 += "#b#L1#ช่วยอธิบายเกี่ยวกับรางวัลอันดับการบริจาคหน่อย#l\r\n";
                v2 += "#b#L2#ช่วยอธิบายเกี่ยวกับรางวัลยอดบริจาคสะสมหน่อย#l\r\n";
                v2 += "#b#L3#ช่วยอธิบายเกี่ยวกับเงินสนับสนุนผู้เล่นใหม่หน่อย#l\r\n";
                int v3 = self.askMenu(v2);
                switch (v3) {
                    case 0: {
                        self.say(
                                "#fs11#การบริจาค Meso ทำได้ในหน่วย #b100 ล้าน Meso#k ต่อ #b100 ล้าน Meso#k จะได้รับ #b100 Praise Point#k\r\n"
                                        + "ในหนึ่งวันสามารถสะสม Praise Point ได้สูงสุด #r1,000,000 คะแนน#k\r\n\r\n"
                                        + "#r※ การจัดอันดับจะนับเฉพาะคะแนนที่สะสมได้ภายในขีดจำกัดรายวัน");
                        break;
                    }
                    case 1: {
                        self.say(
                                "#fs11#อันดับการบริจาคจะจัดอันดับ #eทุกวันที่ 1 ของเดือน#n และมอบรางวัลตามเกณฑ์ด้านล่าง\r\n\r\n#bอันดับ 1 : 20 แต้ม + 100 Maple Point\r\nอันดับ 2 : 15 แต้ม + 70 Maple Point\r\nอันดับ 3~5 : 10 แต้ม + 50 Maple Point\r\nอันดับ 6~10 : 5 แต้ม + 30 Maple Point#k\r\n\r\nทุกวันที่ 1 ของเดือน หากยอดบริจาคสะสมในกล่องบริจาคเกิน #e100,000 ล้าน Meso#n จะทำการ #eสุ่มผู้โชคดี 3 คน#n จากผู้ที่บริจาคมากกว่า 3,000 ล้าน Meso เพื่อรับส่วนแบ่ง #e30% ของยอดบริจาคสะสม#n\r\n\r\n#r(เพื่อป้องกันการกระจุกตัวของ Meso มากเกินไป ยอด 30% สูงสุดจะจำกัดที่ 150,000 ล้าน Meso)");
                        break;
                    }
                    case 2: {
                        self.say(
                                "#fs11#เมื่อยอดบริจาคสะสมครบทุกๆ #e1,000 ล้าน Meso#n จะสามารถรับไอเทมด้านล่างได้\r\n\r\n#b#i4031227# #z4031227# 1 ชิ้น");
                        break;
                    }
                    case 3: {
                        self.say(
                                "#fs11#ผู้เล่นใหม่ #eที่สร้างบัญชีไม่เกิน 3 วัน#n\r\nสามารถรับ #eเงินสนับสนุน 50,000 ล้าน Meso#n\r\nได้ที่ NPC #b‘กล่องบริจาค’#k ในลานกว้าง\r\n\r\n#b└ เงินสนับสนุน 50,000 ล้าน Meso จะถูกหักจากยอดบริจาคสะสม\r\n\r\n└ ตัวละครต้องมีเลเวล 250 ขึ้นไปจึงจะรับได้\r\n\r\n└ ต้องมีเลเวล Union 8,000 ขึ้นไปจึงจะรับได้\r\n");
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
                        day == 1 && hours == 00 && minutes < 5) { // ไม่สามารถรับได้ทุกวันที่ 1 เวลา 00:00 - 00:04 น.
                    self.say("ไม่สามารถบริจาคได้ในช่วงเวลาประมวลผลอันดับ (00:04 น.)");
                    return;
                }

                Timestamp timestamp = getClient().getCreated();
                long delta = System.currentTimeMillis() - timestamp.getTime();

                if (delta < (1000 * 60 * 60 * 24 * 3L)) {
                    self.say("ต้องเป็นไอดีที่สร้างมาแล้วเกิน 3 วัน จึงจะสามารถเข้าร่วมการบริจาคได้");
                    return;
                }

                long totalMeso = PraiseDonationMeso.getTotalMeso();
                PraiseDonationMesoRankEntry entry = PraiseDonationMesoRank.getRank(getPlayer().getAccountID());
                long myTotalMeso = 0;
                if (entry != null) {
                    myTotalMeso = entry.getTotalMeso();
                }
                String v2 = "#fs12##e#b<บริจาค Meso>#k#n#fs11#\r\n\r\n"
                        + "บริจาคได้ครั้งละ #b100 ล้าน Meso#k,\r\nจะได้รับ #b100 Praise Point ต่อ 100 ล้าน Meso#k\r\n"
                        + "สะสม Praise Point ได้สูงสุด #r1,000,000 แต้มต่อวัน#k หากเกินจะไม่ถูกนับรวมในอันดับ\r\n\r\n";
                long meso = getPlayer().getMeso();
                int d = (int) (meso / 100000000);
                v2 += "ยอดบริจาคในกล่องปัจจุบัน : " + NumberFormat.getInstance().format(totalMeso) + " Meso#n\r\n";
                v2 += "#eยอดที่ฉันบริจาคในเดือนนี้ : " + NumberFormat.getInstance().format(myTotalMeso) + " Meso#n\r\n";
                v2 += "จำนวนที่บริจาคได้ในวันนี้ : " + NumberFormat.getInstance().format(d * 100000000L)
                        + " Meso#n \r\n                                  #r(" + d + " ครั้ง)#k\r\n\r\n";
                v2 += "ต้องการบริจาคเท่าไหร่? #b(ค่าที่ใส่ 1 = 100 ล้าน Meso)#k\r\n\r\n";
                int v3 = self.askNumber(v2, 1, 1, d);
                if (v3 <= 0) {
                    self.say("จำนวน Meso ขั้นต่ำในการบริจาคคือ 100 ล้าน Meso");
                    return;
                }
                long donationMeso = v3 * 100000000L;
                long donationMeso2 = v3;
                if (donationMeso > getPlayer().getMeso()) {
                    self.say("มี Meso ไม่เพียงพอสำหรับการบริจาค");
                    return;
                }
                int max = getPlayer().getOneInfoQuestInteger(1211345, "today");
                int p = Math.min(1000000 - max, v3 * 100);
                if (1 == self.askYesNo(
                        "#fs11#ต้องการบริจาค #e" + NumberFormat.getInstance().format(donationMeso)
                                + " Meso#n จริงหรือไม่?\r\nเมื่อบริจาคจะได้รับ #r"
                                + NumberFormat.getInstance().format(p) + " Praise Point#k สามารถรับได้")) {
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
                            "[" + getPlayer().getName() + "] บริจาค '" + donationMeso2
                                    + "00 ล้าน Meso' ลงในกล่องบริจาค"));

                    PraiseDonationMeso.doDonationMeso(getPlayer(), getPlayer().getAccountID(), donationMeso);
                    self.say("#fs11#บริจาค #e" + NumberFormat.getInstance().format(donationMeso)
                            + " Meso#n เรียบร้อยแล้ว\r\nได้รับ #r"
                            + NumberFormat.getInstance().format(p) + " Praise Point");
                }
                break;
            }
            case 2: {
                List<PraiseDonationMesoRankEntry> ranks = PraiseDonationMesoRank.getTopRanker(); // Top 50
                String v2 = "#fs12##e<อันดับบริจาค Top 50>#n\r\n\r\n";
                int count = 1;
                for (PraiseDonationMesoRankEntry r : ranks) {
                    if (count < 10) {
                        v2 = v2 + "#Cgray#00#b#eอันดับ " + count + "#n#k";
                    } else if (count >= 10 && count < 100) {
                        v2 = v2 + "#Cgray#0#b#eอันดับ " + count + "#n#k";
                    } else {
                        v2 = v2 + "#Cgray##b#eอันดับ " + count + "#n#k";
                    }
                    v2 = v2 + " " + r.getPlayerName() + " #e(Meso : "
                            + NumberFormat.getInstance().format(r.getTotalMeso()) + ")#n\r\n";
                    count++;
                }
                self.say(v2);
                break;
            }
            case 3: {
                String v2 = "#fs11#ต้องการรับรางวัลอะไร?\r\n\r\n";
                v2 += "#b#L0#รับรางวัลยอดบริจาคสะสม#l\r\n";
                // v2 += "#L1#기부 랭킹 รางวัล 수령하겠.#l";
                int v3 = self.askMenu(v2);
                switch (v3) {
                    case 0: {
                        long totalMeso = PraiseDonationMeso.getTotalMeso(getPlayer().getAccountID());
                        int getCount = getPlayer().getOneInfoQuestInteger(1211345, "get_reward");
                        int count = (int) (totalMeso / 1_000_000_000L);
                        int remainCount = count - getCount;
                        String v4 = "#fs11##e<รับรางวัลยอดบริจาคสะสม>#n\r\n#eยอดบริจาคสะสมทั้งหมด : "
                                + NumberFormat.getInstance().format(totalMeso) + "\r\n";
                        v4 += "จำนวนครั้งที่สามารถรับรางวัลได้ : " + remainCount + " ครั้ง\r\n\r\n"
                                + "#b#nรับรางวัลได้ 1 ครั้ง ต่อ 1,000 ล้าน Meso สะสม#k\r\n\r\n";
                        v4 += remainCount + " ครั้ง ต้องการใช้สิทธิ์ทั้งหมดรับไอเทมด้านล่างหรือไม่?\r\n\r\n";
                        v4 += "#b#i4031227# #z4031227# x" + remainCount;
                        if (1 == self.askYesNo(v4)) {
                            if (remainCount <= 0) {
                                self.say("จำนวนครั้งที่รับได้ไม่เพียงพอ ไม่สามารถรับรางวัลได้");
                                return;
                            }
                            if (target.exchange(4031227, remainCount) > 0) {
                                self.say("รับรางวัลสะสมเรียบร้อยแล้ว");
                                getPlayer().updateOneInfo(1211345, "get_reward",
                                        String.valueOf(getCount + remainCount));
                            } else {
                                self.say("#bช่องกระเป๋า เพิ่มพื้นที่ ลองใหม่อีกครั้ง โปรด.");
                            }
                        }
                        break;
                    }
                    case 1: {
                        // TODO: Implement Ranking Reward Claim, Daily Ranking Settlement, Lottery 1/n
                        // for 3 players donating > 3B when total exceeds 100B
                        int rank = getPlayer().getOneInfoQuestInteger(1234599, "praise_reward");
                        int get = getPlayer().getOneInfoQuestInteger(1234599, "praise_reward_get");
                        long meso = getPlayer().getOneInfoQuestLong(1234599, "praise_reward2");
                        int get2 = getPlayer().getOneInfoQuestInteger(1234599, "praise_reward2_get");

                        if ((rank > 10 || get > 0) && (meso <= 0 || get2 > 0)) {
                            self.say("คุณไม่มีสิทธิ์ได้รับรางวัลอันดับการบริจาค");
                            return;
                        }
                        NumberFormat nf = NumberFormat.getInstance();
                        if (meso > 0 && get2 == 0) {
                            getPlayer().gainMeso(meso, true);
                            getPlayer().updateOneInfo(1234599, "praise_reward2", "0");
                            getPlayer().updateOneInfo(1234599, "praise_reward2_get", "1");

                            getPlayer().dropMessage(5, "ได้รับ " + nf.format(meso) + " Meso");
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
                                    "ได้รับ " + nf.format(realPoint) + " คะแนน และ " + nf.format(maplePoint)
                                            + " Maple Point");
                        }
                        self.say("มอบรางวัลเรียบร้อยแล้ว");
                        break;
                    }
                }
                break;
            }
            case 4: {
                Timestamp timestamp = getClient().getCreated();
                long delta = System.currentTimeMillis() - timestamp.getTime();

                if (delta >= (1000 * 60 * 60 * 24 * 3L)) {
                    self.say("รับเงินสนับสนุนผู้เล่นใหม่ได้เฉพาะภายใน 3 วันหลังจากสร้างบัญชีเท่านั้น");
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
                        day == 1 && hours == 00 && minutes < 5) { // Cannot claim during settlement time 00:00 - 00:04
                                                                  // on 1st of month
                    self.say("ไม่สามารถรับเงินสนับสนุนได้ในช่วงเวลาคำนวณอันดับ 00:00 - 00:04 น.");
                    return;
                }

                if (getPlayer().getLevel() < 250) {
                    self.say("ตัวละครเลเวลต่ำกว่า 250 ไม่สามารถรับเงินสนับสนุนได้");
                    return;
                }

                if (getPlayer().getUnionLevel() < 8000) {
                    self.say("ต้องมีเลเวล Union 8000 ขึ้นไป จึงจะรับเงินสนับสนุนได้");
                    return;
                }

                if (getPlayer().getOneInfoQuestInteger(1211345, "get_meso") > 0) {
                    self.say("วันนี้รับเงินสนับสนุนไปแล้ว");
                    return;
                }

                long remainMeso = PraiseDonationMeso.getTotalMeso();
                long remainMeso2 = PraiseDonationMeso.getTotalMeso() - 500_0000_0000L;
                if (remainMeso < 500_0000_0000L) {
                    self.say("Meso ในกล่องบริจาคไม่เพียงพอ ไม่สามารถรับเงินสนับสนุนได้");
                    return;
                }

                PraiseDonationMeso.addTotalMeso(-500_0000_0000L);
                getPlayer().gainMeso(500_0000_0000L, true);
                getPlayer().updateOneInfo(1211345, "get_meso", "1");
                Center.Broadcast.broadcastMessage(
                        CField.chatMsg(5,
                                "[" + getPlayer().getName() + "] ได้รับเงินสนับสนุนผู้เล่นใหม่ '50,000 ล้าน Meso'!"));

                self.say(
                        "#fs11#รับเงินสนับสนุนผู้เล่นใหม่ #e#b50,000 ล้าน Meso#k#n เรียบร้อยแล้ว\r\n\r\n#eMeso คงเหลือในกล่อง : "
                                + NumberFormat.getInstance().format(remainMeso2));
                break;
            }
            case 5: { // Praise Shop
                int praise = getPlayer().getPraisePoint().getPoint();
                StringBuilder menu = new StringBuilder();
                menu.append("#fs14##e≪ ร้านค้า Praise Point ≫#n\r\n\r\n")
                        .append("#fs12#Praise Point ที่มี: #b")
                        .append(NumberFormat.getInstance().format(praise))
                        .append("P #k\r\n")
                        .append("#fs11#─────────────────────────────\r\n");

                int[] itemIDs = { 4036660, 4036661, 5068306 }; // Example Item
                int[] prices = { 100000, 500000, 1000000 }; // Praise Point Unit

                for (int i = 0; i < itemIDs.length; i++) {
                    menu.append("#fs11##L").append(i).append("#")
                            .append("#i").append(itemIDs[i]).append("# ")
                            .append("#z").append(itemIDs[i]).append("#    ")
                            .append("#fs11##b")
                            .append(NumberFormat.getInstance().format(prices[i]))
                            .append(" คะแนน#k#l\r\n");
                }

                menu.append("#fs11#\r\n─────────────────────────────\r\n")
                        .append("#fs11#โปรดเลือกสินค้าที่จะซื้อ");

                int sel = self.askMenu(menu.toString());
                int cost = prices[sel], item = itemIDs[sel];

                if (praise < cost) {
                    self.say("#fs12#Praise Point ไม่เพียงพอ");
                    break;
                }

                String confirm = new StringBuilder()
                        .append("#fs12#จริงๆ #b")
                        .append(NumberFormat.getInstance().format(cost))
                        .append("#k คะแนน ใช้\r\n")
                        .append("#fs12##i").append(item)
                        .append("# #z").append(item)
                        .append("##n\r\n")
                        .append("#fs12#ต้องการซื้อหรือไม่?")
                        .toString();

                if (self.askYesNo(confirm) == 1) {
                    // คะแนน หัก
                    PraisePoint pt = getPlayer().getPraisePoint();
                    pt.setPoint(pt.getPoint() - cost);
                    pt.setTotalPoint(pt.getTotalPoint() - cost);
                    getPlayer().updateOneInfo(3887, "point", String.valueOf(pt.getPoint()));
                    getPlayer().setSaveFlag(
                            getPlayer().getSaveFlag() | CharacterSaveFlag.PRAISE_POINT.getFlag());
                    // Grant Item
                    if (target.exchange(item, 1) > 0) {
                        self.say("#fs12#ซื้อเรียบร้อยแล้ว!");
                    } else {
                        self.say("#fs12#ช่องกระเป๋าไม่เพียงพอ");
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
        v0 += "#b" + nf.format(price) + " " + payType
                + "#k จะใช้สุ่มหินประทับไหม?\r\nต้องลองสุ่มดูถึงจะรู้ว่าจะได้หินแบบไหนออกมา\r\n\r\n#e"
                + payType + " ที่มี : " + NumberFormat.getInstance().format(getPrice);
        if (1 == self.askYesNo(v0)) {
            if (type == 0) {
                if (getPlayer().getRealCash() < price) {
                    self.say("#fs11#" + payType + " ดูเหมือนจะไม่พอนะ? ถึงจะเป็นเจ้าก็ต้องจ่ายค่าจ้างให้ครบนะ");
                    return;
                }
            } else if (type == 1) {
                if (getPlayer().getHongboPoint() < price) {
                    self.say("#fs11#" + payType + " ดูเหมือนจะไม่พอนะ? ถึงจะเป็นเจ้าก็ต้องจ่ายค่าจ้างให้ครบนะ");
                    return;
                }
            } else if (type == 2) {
                if (getPlayer().getMeso() < price) {
                    self.say("#fs11#" + payType + " ดูเหมือนจะไม่พอนะ? ถึงจะเป็นเจ้าก็ต้องจ่ายค่าจ้างให้ครบนะ");
                    return;
                }
            } else if (type == 3) {
                if (getPlayer().getCashPoint() < price) {
                    self.say("#fs11#" + payType + " ดูเหมือนจะไม่พอนะ? ถึงจะเป็นเจ้าก็ต้องจ่ายค่าจ้างให้ครบนะ");
                    return;
                }
            } else if (type == 4) {
                if (getPlayer().getItemQuantity(4031227, false) < price) {
                    self.say("#fs11#" + payType + " ดูเหมือนจะไม่พอนะ? ถึงจะเป็นเจ้าก็ต้องจ่ายค่าจ้างให้ครบนะ");
                    return;
                }
            }
            if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < count) {
                self.say("#bช่องกระเป๋าใช้#k ต้องมีที่ว่างอย่างน้อย " + count + " ช่อง ลองใหม่อีกครั้งนะ");
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
                    self.say("#fs11# ดูเหมือน " + payType + " จะไม่พอนะ? ถึงจะเป็นเจ้าก็ต้องจ่ายค่าจ้างให้ครบนะ");
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
                    "Imprinted Stone Gacha Result (Item : " + list + ", Currency : " + payType + ", User : "
                            + getPlayer().getName()
                            + ")");
            LoggingManager.putLog(new ConsumeLog(getPlayer(), 1, sb));

            // objects.utils.FileoutputUtil.log("./TextLog/BuyStoneCraft.txt", "หินประทับ 뽑기
            // (ไอเท็มID : " + itemID + ", 지불 ห้อง식 : หลัง원, ใช้자 : " +
            // getPlayer().getName() +
            // ")\r\n");
            String v7 = "";
            if (DBConfig.isGanglim) {
                v7 += "#fs11#";
            }
            v7 += "#b" + list
                    + "#k\r\nนี่คือหินประทับที่ได้มา\r\n\r\nจะเอาอีกไหม?\r\nถ้าต้องการ ฉันไปหยิบมาทีละ 10 ชิ้นก็ได้นะ\r\n\r\n#b#L0#";
            if (type == 2) {
                v7 += "1,500,000,000Meso รับหินประทับ 1 ชิ้น#l\r\n#L1#15,000,000,000Meso รับหินประทับ 10 ชิ้น#l";
            } else if (type == 0 || type == 1 || type == 3) {
                v7 += "3,000 " + payType + " รับหินประทับ 1 ชิ้น#l\r\n#L1#30,000 " + payType
                        + " รับหินประทับ 10 ชิ้น#l";
            } else {
                v7 += payType + " 600ชิ้น รับหินประทับ 1 ชิ้น#l\r\n#L1#" + payType
                        + " 6000ชิ้น รับหินประทับ 10 ชิ้น#l";
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
        if (DBConfig.isGanglim) { // If no Imprinted Stone Board, give one
            if (getPlayer().getItemQuantity(2432128, false) < 1) {
                getPlayer().gainItem(2432128, 1);
            }
        }
        if (DBConfig.isGanglim) {
            v0 += "#fs11#";
        }
        v0 += "#h0#! ไม่ได้เจอกันนานเลยนะ!\r\nอาจารย์พบหินประหลาดระหว่างวิจัยบันทึกที่ Black Mage ทิ้งไว้\r\n\r\n#b#i2432126# #z2432126#\r\n #i2432127#  #z2432127##k\n\r\n\r\nนี่แหละคือหินที่ว่า";

        self.say(v0);

        String v1 = "";
        if (DBConfig.isGanglim) {
            v1 += "#fs11#";
        }
        v1 += "อาจารย์บ่นพึมพัมว่า #b“ภายนอกดูเหมือนหินธรรมดา แต่สัมผัสได้ถึงพลังของ Black Mage จางๆ หากขัดเกลาพลังที่ไม่เสถียรนี้ อาจจะนำพลังมาใช้ได้บางส่วน แต่ทว่า...”#k แล้วจู่ๆ ท่านก็หายตัวไปไหนไม่รู้\r\n\r\nเป็นไง? สนใจไหม? (ดูเหมือนอยากจะเล่าต่อ ลองฟังหน่อยละกัน)\r\n\r\n";
        v1 += "#b#L0#หินที่ Black Mage ทิ้งไว้?#l\r\n#L1#ตรวจสอบแผ่นจารึกหินประทับ#l\r\n#L2#ใช้แต้มสุ่มหาหินประทับ#l\r\n#L3#ใช้แต้มเจียระไนหินประทับ#l";
        int v2 = self.askMenu(v1);
        switch (v2) {
            case 0: {
                String v3 = "";
                if (DBConfig.isGanglim) {
                    v3 += "#fs11#";
                }
                v3 += "อาจารย์เรียกหินนี้ว่า 'หินประทับ' \r\n\r\n#b(Elwin ปัดฝุ่นออกจากแผ่นหินแล้วยื่นให้ฉัน บนแผ่นหินมีร่องสำหรับใส่หินประทับเจาะอยู่เป็นระยะ)#k\r\n\r\nการขัดเกลาพลังที่ไม่เสถียรของหินเรียกว่า 'การเจียระไน' หากนำหินประทับที่เจียระไนเสร็จแล้วมาใส่ในร่องของแผ่นหิน ก็จะสามารถดึงพลังของหินออกมาใช้ได้ ฉันเองก็แค่ฟังอาจารย์พูดมา เลยไม่รู้รายละเอียดมากนัก...\r\n\r\n";
                v3 += "#b#L0#จะหาได้จากที่ไหน?#l\r\n#L1#วิธีเจียระไน?#l";
                int v4 = self.askMenu(v3);
                switch (v4) {
                    case 0: {
                        String v5 = "";
                        if (DBConfig.isGanglim) {
                            v5 = "#fs11#หินประทับถูกเก็บไว้ในหอสมุดใต้ดินของ Black Mage\r\nเนื่องจากมีเขตอาคม ถ้าไม่มีอาจารย์ก็เอาออกมาไม่ได้ ถ้าจำเป็นก็บอกฉันนะ\r\n\r\nถึงจะเป็น #h0# ก็เถอะ แต่ข้าต้องคิดค่าจ้างสำหรับจอมเวทย์เขตอาคมนะ\r\nฉันไม่รู้ว่าจะหยิบได้หินประทับแบบไหนออกมา จะได้อะไรก็ค่าจ้างเท่าเดิมนะ จำไว้ด้วย\r\n\r\n";
                            v5 += "#eGanglim Cash : 3,000 Cash\r\n#b[หินประทับละเอียด 80%, หินประทับทั่วไป 20%]#k\r\nBrilliant Light Crystal : 500 ชิ้น\r\n#b[หินประทับละเอียด 10%, หินประทับทั่วไป 90%]#k#k";
                        } else {
                            v5 = "หินประทับถูกเก็บไว้ในหอสมุดใต้ดินของ Black Mage\r\nเนื่องจากมีเขตอาคม ถ้าไม่มีอาจารย์ก็เอาออกมาไม่ได้ ถ้าจำเป็นก็บอกฉันนะ\r\n\r\nถึงจะเป็น #h0# ก็เถอะ แต่ข้าต้องคิดค่าจ้างสำหรับจอมเวทย์เขตอาคมนะ\r\nฉันไม่รู้ว่าจะหยิบได้หินประทับแบบไหนออกมา จะได้อะไรก็ค่าจ้างเท่าเดิมนะ จำไว้ด้วย\r\n\r\n";
                            v5 += "#ePraise Point : 3,000 #b(หินประทับละเอียด 80%, หินประทับทั่วไป 20%)#k\r\nPromotion Point : 3,000 #b(หินประทับละเอียด 60%, หินประทับทั่วไป 40%)#k\r\nMeso : 1,500,000,000 #b(หินประทับละเอียด 10%, หินประทับทั่วไป 90%)#k";
                        }
                        self.say(v5);

                        /*
                         * String v6 =
                         * "ถ้าอย่างนั้น 보니 검 마법사 ผลกระทบ 받 군단장 잔상 처치 หินประทับ 얻 수 있을지 몰라 #r(Swoo, Damien, 루시드, 윌, 더스크, 듄켈, 진 힐라, 검 마법사 처치 시 โอกาส적으 하급 หินประทับ 상자 และ 중급 หินประทับ 상자 ดรอปสามารถ ).#k"
                         * ;
                         * v6 +=
                         * "\r\n\r\n#e30% โอกาส ‘하급 หินประทับ 상자’ ดรอป(ชิ้น인 ดรอป, สูงสุด 1ชิ้น)#n\r\n#b└ 정밀한 หินประทับ 10%, 평범한 หินประทับ 90%#k\r\n\r\n#e10% โอกาส ‘중급 หินประทับ 상자’ ดรอป(ชิ้น인 ดรอป, สูงสุด 1ชิ้น)#n\r\n#b└ 정밀한 หินประทับ 30%, 평범한 หินประทับ 70%#k"
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
                        v5 += "ทำกันเองโดยอาจารย์ไม่อนุญาตแบบนี้จะดีเหรอ...?\r\n\r\nเอาเถอะ ถ้าพาหินประทับมา ฉันจะช่วยขัดเกลาพลังที่ไม่เสถียรให้\r\nแต่เพราะพลังในหินประทับนั้นไม่เสถียร การเจียระไนอาจจะล้มเหลวได้นะ\r\n\r\n";
                        self.sayReplacedNpc(v5, 1530040);

                        String v6 = "";
                        if (DBConfig.isGanglim) {
                            v6 = "#fs11#ถึงจะเป็น #h0# ก็เถอะ แต่ข้าต้องคิดค่าจ้างสำหรับจอมเวทย์ปรับแต่งนะ ต่อให้เจียระไนล้มเหลวก็ไม่คืนค่าจ้างนะ บอกไว้ก่อน\r\n\r\n#eGanglim Cash : 1,000 Cash\r\nPraise Point : 3,000 แต้ม\r\nBrilliant Light Crystal : 200 ชิ้น";
                        } else {
                            v6 = "ถึงจะเป็น #h0# ก็เถอะ แต่ข้าต้องคิดค่าจ้างสำหรับจอมเวทย์ปรับแต่งนะ ต่อให้เจียระไนล้มเหลวก็ไม่คืนค่าจ้างนะ บอกไว้ก่อน\r\n\r\n#ePraise Point : 1,000\r\nPromotion Point : 1,000\r\nMeso : 500,000,000";
                        }
                        self.sayReplacedNpc(v6, 1530040);

                        String v7 = "";
                        if (DBConfig.isGanglim) {
                            v7 += "#fs11#";
                        }
                        v7 += "#r※ หินประทับที่ติดตั้งแล้วไม่สามารถถอดออกได้ หากติดตั้งหินใหม่ทับ หินเดิมจะถูกทำลายและแทนที่ หินที่ถูกทำลายจะไม่เหลือร่องรอยและกู้คืนไม่ได้";
                        v7 += "\r\n\r\n※ หินประทับต้องผ่านการเจียระไน Plus 10 ครั้ง และ Minus 10 ครั้ง รวมทั้งหมด 20 ครั้ง จึงจะแสดงผลออปชั่น\r\n\r\n";
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
                v3 += "#e[석판 장착된 หินประทับ]#n\r\n\r\n";
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

                String lock1 = getPlayer().getOneInfoQuestInteger(133333, "lock1") > 0 ? "#r(ล็อก)" : "#b(ปลดล็อก)";
                String lock2 = getPlayer().getOneInfoQuestInteger(133333, "lock2") > 0 ? "#r(ล็อก)" : "#b(ปลดล็อก)";
                String lock3 = getPlayer().getOneInfoQuestInteger(133333, "lock3") > 0 ? "#r(ล็อก)" : "#b(ปลดล็อก)";
                String lock4 = getPlayer().getOneInfoQuestInteger(133333, "lock4") > 0 ? "#r(ล็อก)" : "#b(ปลดล็อก)";
                String lock5 = getPlayer().getOneInfoQuestInteger(133333, "lock5") > 0 ? "#r(ล็อก)" : "#b(ปลดล็อก)";

                int unlock1 = getPlayer().getOneInfoQuestInteger(133333, "unlock1");
                int unlock2 = getPlayer().getOneInfoQuestInteger(133333, "unlock2");

                v3 += "\r\n" + lock1 + " " + lock2 + " " + lock3 + " " + lock4 + " " + lock5;
                v3 += "\r\n\r\n#k";
                String item = item1 > 0 ? ("#b#z" + item1 + "##k") : "#rยังไม่ได้ติดตั้ง#k";
                String ImprintedCount = (plusCount1 + minusCount1) == 20 ? "#bเจียระไนเสร็จสิ้น#k"
                        : "#b[" + plusCount1 + "/10] #r[" + minusCount1 + "/10]#k";
                String ImprintedEnabled = (plusCount1 + minusCount1) == 20 ? "#fc0xFF9d1ffe#ใช้งาน#k"
                        : "#fc0xFFc983ff#ปิดการใช้งาน#k";
                ImprintedStoneOption plusOption1 = ImprintedStoneOption
                        .getByOption(getPlayer().getOneInfoQuestInteger(133333, "plusOption1"));
                ImprintedStoneOption minusOption1 = ImprintedStoneOption
                        .getByOption(getPlayer().getOneInfoQuestInteger(133333, "minusOption1"));
                v3 += "ช่องที่ 1 : " + item + " | " + ImprintedCount + "\r\n";
                v3 += "ㄴ #b[" + String.format(plusOption1.getDesc(), "+", optionPlus1, "%", "") + ", #r"
                        + String.format(minusOption1.getDesc(), "-", optionMinus1, "%", "") + "]#k | "
                        + ImprintedEnabled + "\r\n";

                item = item2 > 0 ? ("#b#z" + item2 + "##k") : "#rยังไม่ได้ติดตั้ง#k";
                ImprintedCount = (plusCount2 + minusCount2) == 20 ? "#bเจียระไนเสร็จสิ้น"
                        : "#b[" + plusCount2 + "/10] #r[" + minusCount2 + "/10]#k";
                ImprintedEnabled = (plusCount2 + minusCount2) == 20 ? "#fc0xFF9d1ffe#ใช้งาน#k"
                        : "#fc0xFFc983ff#미ใช้งาน#k";
                ImprintedStoneOption plusOption2 = ImprintedStoneOption
                        .getByOption(getPlayer().getOneInfoQuestInteger(133333, "plusOption2"));
                ImprintedStoneOption minusOption2 = ImprintedStoneOption
                        .getByOption(getPlayer().getOneInfoQuestInteger(133333, "minusOption2"));
                v3 += "ช่องที่ 2 : " + item + " | " + ImprintedCount + "\r\n";
                v3 += "ㄴ #b[" + String.format(plusOption2.getDesc(), "+", optionPlus2, "%", "") + ", #r"
                        + String.format(minusOption2.getDesc(), "-", optionMinus2, "%", "") + "]#k | "
                        + ImprintedEnabled + "\r\n";

                item = item3 > 0 ? ("#b#z" + item3 + "##k") : "#rยังไม่ได้ติดตั้ง#k";
                ImprintedCount = (plusCount3 + minusCount3) == 20 ? "#bเจียระไนเสร็จสิ้น"
                        : "#b[" + plusCount3 + "/10] #r[" + minusCount3 + "/10]#k";
                ImprintedEnabled = (plusCount3 + minusCount3) == 20 ? "#fc0xFF9d1ffe#ใช้งาน#k"
                        : "#fc0xFFc983ff#미ใช้งาน#k";
                ImprintedStoneOption plusOption3 = ImprintedStoneOption
                        .getByOption(getPlayer().getOneInfoQuestInteger(133333, "plusOption3"));
                ImprintedStoneOption minusOption3 = ImprintedStoneOption
                        .getByOption(getPlayer().getOneInfoQuestInteger(133333, "minusOption3"));
                v3 += "ช่องที่ 3 : " + item + " | " + ImprintedCount + "\r\n";
                v3 += "ㄴ #b[" + String.format(plusOption3.getDesc(), "+", optionPlus3, "%", "") + ", #r"
                        + String.format(minusOption3.getDesc(), "-", optionMinus3, "%", "") + "]#k | "
                        + ImprintedEnabled + "\r\n";

                item = item4 > 0 ? ("#b#z" + item4 + "##k") : "#rยังไม่ได้ติดตั้ง#k";
                ImprintedCount = (plusCount4 + minusCount4) == 20 ? "#bเจียระไนเสร็จสิ้น"
                        : "#b[" + plusCount4 + "/10] #r[" + minusCount4 + "/10]#k";
                ImprintedEnabled = (plusCount4 + minusCount4) == 20 ? "#fc0xFF9d1ffe#ใช้งาน#k"
                        : "#fc0xFFc983ff#미ใช้งาน#k";
                ImprintedStoneOption plusOption4 = ImprintedStoneOption
                        .getByOption(getPlayer().getOneInfoQuestInteger(133333, "plusOption4"));
                ImprintedStoneOption minusOption4 = ImprintedStoneOption
                        .getByOption(getPlayer().getOneInfoQuestInteger(133333, "minusOption4"));
                v3 += "ช่องที่ 4 : " + item + " | " + ImprintedCount + "\r\n";
                v3 += "ㄴ #b[" + String.format(plusOption4.getDesc(), "+", optionPlus4, "%", "") + ", #r"
                        + String.format(minusOption4.getDesc(), "-", optionMinus4, "%", "") + "]#k | "
                        + ImprintedEnabled + "\r\n";

                /*
                 * if (unlock1 == 0) {
                 * v3 += " #e(봉인됨)#n";
                 * }
                 * v3 += "\r\n";
                 */
                item = item5 > 0 ? ("#b#z" + item5 + "##k") : "#rยังไม่ได้ติดตั้ง#k";
                ImprintedCount = (plusCount5 + minusCount5) == 20 ? "#bเจียระไนเสร็จสิ้น"
                        : "#b[" + plusCount5 + "/10] #r[" + minusCount5 + "/10]#k";
                ImprintedEnabled = (plusCount5 + minusCount5) == 20 ? "#fc0xFF9d1ffe#ใช้งาน#k"
                        : "#fc0xFFc983ff#미ใช้งาน#k";
                ImprintedStoneOption plusOption5 = ImprintedStoneOption
                        .getByOption(getPlayer().getOneInfoQuestInteger(133333, "plusOption5"));
                ImprintedStoneOption minusOption5 = ImprintedStoneOption
                        .getByOption(getPlayer().getOneInfoQuestInteger(133333, "minusOption5"));
                v3 += "ช่องที่ 5 : " + item + " | " + ImprintedCount + "\r\n";
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
                v3 += "\r\n#b#L1#ตั้งค่าการล็อกช่องที่ 1#l";
                v3 += "\r\n#b#L2#ตั้งค่าการล็อกช่องที่ 2#l";
                v3 += "\r\n#b#L3#ตั้งค่าการล็อกช่องที่ 3#l";
                v3 += "\r\n#b#L4#ตั้งค่าการล็อกช่องที่ 4#l";
                v3 += "\r\n#b#L5#ตั้งค่าการล็อกช่องที่ 5#l";

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
                    v3 += "ต้องการหินประทับเพิ่มเหรอ? เดี๋ยวไปหยิบมาจากหอสมุดใต้ดินของ Black Mage ให้\r\n\r\nจะจ่ายค่าจ้างด้วยอะไรดี?\r\n\r\n#b";
                    v3 += "#L4#Ganglim 3,000 Cash รับหินประทับ#l\r\n";
                    v3 += "#L5#Brilliant Light Crystal 600 ชิ้น รับหินประทับ#l\r\n";
                    v3 += "#L3#ขอคิดดูก่อนนะ#l";
                } else {
                    v3 += "ต้องการหินประทับเพิ่มเหรอ? เดี๋ยวไปหยิบมาจากหอสมุดใต้ดินของ Black Mage ให้\r\n\r\nจะจ่ายค่าจ้างด้วยอะไรดี?\r\n\r\n#b";
                    v3 += "#L0#Praise Point 3,000 รับหินประทับ#l\r\n";
                    v3 += "#L1#Promotion Point 3,000 รับหินประทับ#l\r\n";
                    v3 += "#L2#Meso 1,500,000,000 รับหินประทับ#l\r\n";
                    v3 += "#L3#ขอคิดดูก่อนนะ#l";
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
                v3 += "สามารถเจียระไนหินประทับที่ติดตั้งอยู่ในแผ่นจารึกได้\r\nเมื่อเจียระไน ออปชั่นที่เพิ่มลดจะถูกสุ่ม แต่ประเภทของออปชั่นจะขึ้นอยู่กับชนิดของหินประทับที่ติดตั้งอยู่\r\n\r\n";
                v3 += "#eออปชั่น Plus 1 อย่างจากรายการด้านล่าง#n\r\n#b└ หินประทับละเอียด : All Stat 5%, Attack 2%, Magic Attack 2%, Critical Damage 2%, Boss Damage 5%, IED 5%, Critical Rate 5%, Final Damage 2%\r\n└ หินประทับทั่วไป : All Stat 2%, Boss Damage 2%, IED 2%, Critical Rate 2%\r\n\r\n#k";
                v3 += "#eออปชั่น Minus 1 อย่างจากรายการด้านล่าง#n\r\n#b└ หินประทับละเอียด : Damage 2% ลดลง (กรณีออปชั่น Plus เป็น Final Damage %, จะลด Final Damage 2% แน่นอน)\r\nหินประทับทั่วไป : Damage 1% ลดลง, All Stat 1% ลดลง#k\r\n\r\n";
                self.sayReplacedNpc(v3, 1530040);

                String v4 = "";
                if (DBConfig.isGanglim) {
                    v4 += "#fs11#";
                    v4 += "หินประทับสามารถเจียระไน #ePlus 10 ครั้ง และ Minus 10 ครั้ง รวมทั้งหมด 20 ครั้ง#n โปรดทราบว่าพลังอาจไม่เสถียรทำให้การเจียระไนล้มเหลวได้!\r\n\r\n#rไม่ว่าการเจียระไนจะสำเร็จหรือไม่ จำนวนครั้งที่เจียระไนได้จะถูกหักและไม่สามารถกู้คืนได้#k\r\n\r\n#b";
                    v4 += "#L4#จ่าย Ganglim 1,000 Cash เจียระไนหินประทับ#l\r\n";
                    v4 += "#L6#จ่าย Ganglim 3,000 Points เจียระไนหินประทับ#l\r\n";
                    v4 += "#L5#จ่าย Brilliant Light Crystal 200 ชิ้น เจียระไนหินประทับ#l\r\n";
                    v4 += "#L99#ขอคิดดูก่อนนะ#l\r\n";
                } else {
                    v4 += "หินประทับสามารถเจียระไน #ePlus 10 ครั้ง และ Minus 10 ครั้ง รวมทั้งหมด 20 ครั้ง#n โปรดทราบว่าพลังอาจไม่เสถียรทำให้การเจียระไนล้มเหลวได้!\r\n\r\n#rไม่ว่าการเจียระไนจะสำเร็จหรือไม่ จำนวนครั้งที่เจียระไนได้จะถูกหักและไม่สามารถกู้คืนได้#k\r\n\r\n#b";
                    v4 += "#L0#จ่าย 1,000 Points เจียระไนหินประทับ#l\r\n";
                    v4 += "#L1#จ่าย 1,000 Promotion Points เจียระไนหินประทับ#l\r\n";
                    v4 += "#L2#จ่าย 500,000,000 Meso เจียระไนหินประทับ#l\r\n";
                    v4 += "#L3#ขอคิดดูก่อนนะ#l\r\n";
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
        v6 += "กรุณาเลือกช่องที่ต้องการเจียระไน จะดำเนินการเจียระไนหินประทับที่ติดตั้งในช่องที่เลือก";
        // int unlock1 = getPlayer().getOneInfoQuestInteger(133333, "unlock1");
        // int unlock2 = getPlayer().getOneInfoQuestInteger(133333, "unlock2");

        v6 += "\r\n\r\n#b#L0#เจียระไนหินประทับช่องที่ 1#l\r\n";
        v6 += "#b#L1#เจียระไนหินประทับช่องที่ 2#l\r\n";
        v6 += "#b#L2#เจียระไนหินประทับช่องที่ 3#l\r\n";
        // if (unlock1 == 1) {
        v6 += "#b#L3#เจียระไนหินประทับช่องที่ 4#l\r\n";
        // }
        // if (unlock2 == 1) {
        v6 += "#b#L4#เจียระไนหินประทับช่องที่ 5#l\r\n";
        // }

        int v7 = self.askMenu(v6);

        int index = v7 + 1;

        int itemID = getPlayer().getOneInfoQuestInteger(133333, "equip" + index);

        if (itemID == 0) {
            self.say("ไม่มีหินประทับติดตั้งใน #eช่องที่ " + index + "#n กรุณาตรวจสอบและลองใหม่อีกครั้ง!");
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
        v8 += "#e[ช่องที่ " + index + "]#n\r\n";
        v8 += "#b#i" + itemID + "# #z" + itemID + "##k\r\n\r\n";
        v8 += "#eจำนวนครั้งเจียระไน Plus ที่เหลือ : " + remainCraftPlus + "\r\n";
        v8 += "#eจำนวนครั้งเจียระไน Minus ที่เหลือ : " + remainCraftMinus + "\r\n";
        v8 += "#bออปชั่น Plus : " + String.format(plusOption.getDesc(), "+", plusValue, "%", "เพิ่ม") + "\r\n";
        v8 += ("#rออปชั่น Minus : " + String.format(minusOption.getDesc(), "-", minusValue, "%", "ลด") + "\r\n");
        if (payType == ImprintedStonePayType.Donation) {
            v8 += "คะแนนที่มีปัจจุบัน : " + NumberFormat.getInstance().format(getPlayer().getRealCash());
        } else if (payType == ImprintedStonePayType.Promotion) {
            v8 += "คะแนนโปรโมชั่นที่มีปัจจุบัน : " + NumberFormat.getInstance().format(getPlayer().getHongboPoint());
        } else if (payType == ImprintedStonePayType.Meso) {
            v8 += "Meso ที่มีปัจจุบัน : " + NumberFormat.getInstance().format(getPlayer().getMeso());
        } else if (payType == ImprintedStonePayType.RoyalCash) {
            v8 += "#k#eGanglim Cash ที่มีปัจจุบัน : " + NumberFormat.getInstance().format(getPlayer().getCashPoint());
        } else if (payType == ImprintedStonePayType.RoyalDPoint) {
            v8 += "#k#eคะแนนที่มีปัจจุบัน : " + NumberFormat.getInstance().format(getPlayer().getDonationPoint());
        } else if (payType == ImprintedStonePayType.RoyalRedBall) {
            v8 += "#k#eBrilliant Light Crystal ที่มีปัจจุบัน : " + getPlayer().getItemQuantity(4031227, false);
        }
        v8 += "\r\n\r\n#k#nต้องการเจียระไนแบบไหน ระหว่าง Plus หรือ Minus?\r\n";
        v8 += "#b#L0#ดำเนินการเจียระไน Plus#l\r\n";
        v8 += "#b#L1#ดำเนินการเจียระไน Minus#l\r\n";
        v8 += "#b#L2#ขอคิดดูก่อน#l\r\n";
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
            if (0 == self.askYesNo("#fs11#ต้องการใช้ #e" + payMessage + "#n เพื่อเจียระไนจริงหรือไม่?")) {
                return;
            }
        } else {
            if (0 == self.askYesNo("ต้องการใช้ #e" + payMessage + "#n เพื่อเจียระไนจริงหรือไม่?")) {
                return;
            }
        }

        int craftCount = getPlayer().getOneInfoQuestInteger(133333, "craftMinus" + index);
        if (craftCount >= 10) {
            if (DBConfig.isGanglim) {
                self.say("#fs11#ดูเหมือนว่าหินประทับนี้จะเจียระไน Minus เสร็จสมบูรณ์แล้วนะ");
            } else {
                self.say("ดูเหมือนว่าหินประทับนี้จะเจียระไน Minus เสร็จสมบูรณ์แล้วนะ");
            }
            return;
        }
        if (payType == ImprintedStonePayType.Donation.getType()) {
            if (getPlayer().getRealCash() < 1000) {
                self.say("คะแนนไม่เพียงพอ ไม่สามารถดำเนินการเจียระไนได้");
                return;
            }
            getPlayer().gainRealCash(-1000, true);
        } else if (payType == ImprintedStonePayType.Promotion.getType()) {
            if (getPlayer().getHongboPoint() < 1000) {
                self.say("คะแนนโปรโมชั่นไม่เพียงพอ ไม่สามารถดำเนินการเจียระไนได้");
                return;
            }
            getPlayer().gainHongboPoint(-1000, true);
        } else if (payType == ImprintedStonePayType.Meso.getType()) {
            if (getPlayer().getMeso() < 500000000) {
                self.say("Meso ไม่เพียงพอ ไม่สามารถดำเนินการเจียระไนได้");
                return;
            }
            getPlayer().gainMeso(-500000000, true);
        } else if (payType == ImprintedStonePayType.RoyalCash.getType()) {
            if (getPlayer().getCashPoint() < 1000) {
                self.say("#fs11#Ganglim Cash ไม่เพียงพอ ไม่สามารถดำเนินการเจียระไนได้");
                return;
            }
            getPlayer().gainCashPoint(-1000);
        } else if (payType == ImprintedStonePayType.RoyalDPoint.getType()) {
            if (getPlayer().getDonationPoint() < 3000) {
                self.say("#fs11#คะแนนไม่เพียงพอ ไม่สามารถดำเนินการเจียระไนได้");
                return;
            }
            getPlayer().gainDonationPoint(-3000);
        } else if (payType == ImprintedStonePayType.RoyalRedBall.getType()) {
            if (target.exchange(4031227, -200) < 0) {
                self.say("#fs11#Brilliant Light Crystal ไม่เพียงพอ ไม่สามารถดำเนินการเจียระไนได้");
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
                self.say("#fs11#ต้องเจียระไน Plus อย่างน้อย 1 ครั้ง จึงจะสามารถเจียระไน Minus ได้");
            } else {
                self.say("ต้องเจียระไน Plus อย่างน้อย 1 ครั้ง จึงจะสามารถเจียระไน Minus ได้");
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
                // หากออปชั่น Plus เป็น Final Damage เพิ่ม, ออปชั่น Minus จะเป็น Final Damage
                // แน่นอน
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
        v10 += "#e[Minus Crafting]#n\r\n#bเจียระไนครั้งที่ "
                + (getPlayer().getOneInfoQuestInteger(133333, "craftMinus" + index))
                + " เสร็จสมบูรณ์แล้ว\r\n\r\n";
        v10 += "#eจำนวนครั้งเจียระไน Minus ที่เหลือ : " + (remainCraftMinus - 1) + "\r\n";
        v10 += ("#bออปชั่น Minus : " + String.format(minusOption.getDesc(), "-", minusValue, "%", "") + "\r\n");
        if (payType == ImprintedStonePayType.Donation) {
            v10 += "#rคะแนนที่มีปัจจุบัน : " + NumberFormat.getInstance().format(getPlayer().getRealCash());
        } else if (payType == ImprintedStonePayType.Promotion) {
            v10 += "#rคะแนนโปรโมชั่นที่มีปัจจุบัน : " + NumberFormat.getInstance().format(getPlayer().getHongboPoint());
        } else if (payType == ImprintedStonePayType.Meso) {
            v10 += "#rMeso ที่มีปัจจุบัน : " + NumberFormat.getInstance().format(getPlayer().getMeso());
        } else if (payType == ImprintedStonePayType.RoyalCash) {
            v10 += "#rGanglim Cash ที่มีปัจจุบัน : " + NumberFormat.getInstance().format(getPlayer().getCashPoint());
        } else if (payType == ImprintedStonePayType.RoyalDPoint) {
            v10 += "#rคะแนนที่มีปัจจุบัน : " + NumberFormat.getInstance().format(getPlayer().getDonationPoint());
        } else if (payType == ImprintedStonePayType.RoyalRedBall) {
            v10 += "#rBrilliant Light Crystal ที่มีปัจจุบัน : " + getPlayer().getItemQuantity(4031227, false);
        }
        objects.utils.FileoutputUtil.log("./TextLog/EquipStoneCraft.txt",
                "Imprinted Stone Craft(Minus) (Slot : " + index + ", ItemID : " + itemID + ", Option : "
                        + minusOption.name()
                        + ", Value : "
                        + minusValue + ", Pay Method : " + payType.name() + ", User : " + getPlayer().getName()
                        + ")\r\n");

        v10 += "\r\n#b#n#L0#เจียระไนต่อ#l\r\n";
        v10 += "#L1#หยุดเจียระไน#l";
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
            if (0 == self.askYesNo("#fs11#ต้องการใช้ #e" + payMessage + "#n เพื่อเจียระไนจริงหรือไม่?")) {
                return;
            }
        } else {
            if (0 == self.askYesNo("ต้องการใช้ #e" + payMessage + "#n เพื่อเจียระไนจริงหรือไม่?")) {
                return;
            }
        }

        int craftCount = getPlayer().getOneInfoQuestInteger(133333, "craftPlus" + index);
        if (craftCount >= 10) {
            if (DBConfig.isGanglim) {
                self.say("#fs11#ดูเหมือนว่าหินประทับนี้จะเจียระไน Plus เสร็จสมบูรณ์แล้วนะ");
            } else {
                self.say("ดูเหมือนว่าหินประทับนี้จะเจียระไน Plus เสร็จสมบูรณ์แล้วนะ");
            }
            return;
        }
        if (payType == ImprintedStonePayType.Donation.getType()) {
            if (getPlayer().getRealCash() < 1000) {
                self.say("คะแนนไม่เพียงพอ ไม่สามารถดำเนินการเจียระไนได้");
                return;
            }
            getPlayer().gainRealCash(-1000, true);
        } else if (payType == ImprintedStonePayType.Promotion.getType()) {
            if (getPlayer().getHongboPoint() < 1000) {
                self.say("คะแนนโปรโมชั่นไม่เพียงพอ ไม่สามารถดำเนินการเจียระไนได้");
                return;
            }
            getPlayer().gainHongboPoint(-1000, true);
        } else if (payType == ImprintedStonePayType.Meso.getType()) {
            if (getPlayer().getMeso() < 500000000) {
                self.say("Meso ไม่เพียงพอ ไม่สามารถดำเนินการเจียระไนได้");
                return;
            }
            getPlayer().gainMeso(-500000000, true);
        } else if (payType == ImprintedStonePayType.RoyalCash.getType()) {
            if (getPlayer().getCashPoint() < 1000) {
                self.say("#fs11#Ganglim Cash ไม่เพียงพอ ไม่สามารถดำเนินการเจียระไนได้");
                return;
            }
            getPlayer().gainCashPoint(-1000);
        } else if (payType == ImprintedStonePayType.RoyalDPoint.getType()) {
            if (getPlayer().getDonationPoint() < 3000) {
                self.say("#fs11#คะแนนไม่เพียงพอ ไม่สามารถดำเนินการเจียระไนได้");
                return;
            }
            getPlayer().gainDonationPoint(-3000);
        } else if (payType == ImprintedStonePayType.RoyalRedBall.getType()) {
            if (target.exchange(4031227, -200) < 0) {
                self.say("#fs11#Brilliant Light Crystal ไม่เพียงพอ ไม่สามารถดำเนินการเจียระไนได้");
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
        v10 += "#e[Plus Crafting]#n\r\n#bเจียระไนครั้งที่ "
                + (getPlayer().getOneInfoQuestInteger(133333, "craftPlus" + index))
                + " เสร็จสมบูรณ์แล้ว\r\n\r\n";
        v10 += "#eจำนวนครั้งเจียระไน Plus ที่เหลือ : " + (remainCraftPlus - 1) + "\r\n";
        v10 += ("#bออปชั่น Plus : " + String.format(plusOption.getDesc(), "+", plusValue, "%", "") + "\r\n");
        if (payType == ImprintedStonePayType.Donation) {
            v10 += "#rคะแนนที่มีปัจจุบัน : " + NumberFormat.getInstance().format(getPlayer().getRealCash());
        } else if (payType == ImprintedStonePayType.Promotion) {
            v10 += "#rคะแนนโปรโมชั่นที่มีปัจจุบัน : " + NumberFormat.getInstance().format(getPlayer().getHongboPoint());
        } else if (payType == ImprintedStonePayType.Meso) {
            v10 += "#rMeso ที่มีปัจจุบัน : " + NumberFormat.getInstance().format(getPlayer().getMeso());
        } else if (payType == ImprintedStonePayType.RoyalCash) {
            v10 += "#rGanglim Cash ที่มีปัจจุบัน : " + NumberFormat.getInstance().format(getPlayer().getCashPoint());
        } else if (payType == ImprintedStonePayType.RoyalDPoint) {
            v10 += "#rคะแนนที่มีปัจจุบัน : " + NumberFormat.getInstance().format(getPlayer().getDonationPoint());
        } else if (payType == ImprintedStonePayType.RoyalRedBall) {
            v10 += "#rBrilliant Light Crystal ที่มีปัจจุบัน : " + getPlayer().getItemQuantity(4031227, false);
        }
        v10 += "\r\n#b#n#L0#เจียระไนต่อ#l\r\n";
        v10 += "#L1#หยุดเจียระไน#l";
        // if (getPlayer().getOneInfoQuestInteger(133333, "craftMinus" + index) >= 10 &&
        // getPlayer().getOneInfoQuestInteger(133333, "craftPlus" + index) >= 10) {
        getPlayer().checkImprintedStone();
        // }
        objects.utils.FileoutputUtil.log("./TextLog/StoneCraft.txt",
                "หินประทับ 세공(플러스) (ช่อง : " + index + ", ไอเท็มID : " + itemID + ", ตัวเลือก : " + plusOption.name()
                        + ", 값 : "
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

        var v0 = "สวัสดี ท่านผู้ดูแล\r\nนี่คือบริการสำหรับแจกรางวัลโปรโมชั่นสะสม\r\nข้าจะมอบเหรียญรางวัลให้แก่ผู้เล่นที่สามารถทำยอดโปรโมชั่นได้ถึงตามระยะเวลาที่กำหนด\r\n\r\n";
        v0 += "#e[เกณฑ์การรับเหรียญรางวัลโปรโมชั่น]#n\r\n";
        v0 += "วันที่ 15 - #b#z1142070##k#r(All Stat 250, Att/Matt 200)#k\r\n";
        v0 += "วันที่ 100 - #b#z1142072##k#r(All Stat 400, Att/Matt 350)#k\r\n\r\n";
        v0 += "#b#L0#สร้าง Ganglim Supporter Medal#l\r\n";
        v0 += "#L1#สร้าง Ganglim Supporters Medal#l\r\n";

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
                self.say("สร้างเรียบร้อยแล้ว");
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
                self.say("สร้างเรียบร้อยแล้ว");
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
        String v0 = "กรุณาเลือกรางวัลที่จะรับ\r\n\r\n#b";
        if (totalPrice < 25000000) {
            self.say("ไม่มีรางวัลที่สามารถรับได้");
        } else {
            boolean find = false;
            if (totalPrice >= 25000000) {
                int check = getPlayer().getOneInfoQuestInteger(1235999, "get_reward_2500");
                if (0 == check) {
                    v0 += "#L2500#รับรางวัลยอด 25 ล้าน#l\r\n";
                    find = true;
                }
            }
            if (totalPrice >= 30000000) {
                int check = getPlayer().getOneInfoQuestInteger(1235999, "get_reward_3000");
                if (0 == check) {
                    v0 += "#L3000#รับรางวัลยอด 30 ล้าน#l\r\n";
                    find = true;
                }

            }
            if (totalPrice >= 35000000) {
                int check = getPlayer().getOneInfoQuestInteger(1235999, "get_reward_3500");
                if (0 == check) {
                    v0 += "#L3500#รับรางวัลยอด 35 ล้าน#l\r\n";
                    find = true;
                }
            }
            if (totalPrice >= 40000000) {
                int check = getPlayer().getOneInfoQuestInteger(1235999, "get_reward_4000");
                if (0 == check) {
                    v0 += "#L4000#รับรางวัลยอด 40 ล้าน#l\r\n";
                    find = true;
                }
            }
            if (totalPrice >= 43000000) {
                int under4000 = totalPrice - 43000000;
                int count = under4000 / 3000000 + 1; // 4300때 พื้นฐาน 1ครั้ง เป็นไปได้
                int getCount = getPlayer().getOneInfoQuestInteger(1235999, "get_reward_4300");
                int remain = count - getCount;
                if (remain > 0) {
                    v0 += "#L4300#รับรางวัลยอด 43 ล้าน (" + remain + " ครั้ง)#l\r\n";
                    find = true;
                }
            }
            if (!find) {
                self.say("ไม่มีรางวัลที่สามารถรับได้");
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
                            self.say("มอบรางวัลเรียบร้อยแล้ว");
                        }
                    }
                } else {
                    self.say("ช่องเก็บอุปกรณ์ไม่เพียงพอ กรุณาเพิ่มพื้นที่ว่างแล้วลองใหม่อีกครั้ง");
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
                        self.say("มอบรางวัลเรียบร้อยแล้ว");
                    }
                } else {
                    self.say("ช่องเก็บอุปกรณ์ไม่เพียงพอ กรุณาเพิ่มพื้นที่ว่างแล้วลองใหม่อีกครั้ง");
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
                            self.say("มอบรางวัลเรียบร้อยแล้ว");
                        }
                    }
                } else {
                    self.say("ช่องเก็บอุปกรณ์ไม่เพียงพอ กรุณาเพิ่มพื้นที่ว่างแล้วลองใหม่อีกครั้ง");
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
                            self.say("มอบรางวัล SAVIOR เรียบร้อยแล้ว");
                        }
                    }
                } else {
                    self.say("ช่องเก็บอุปกรณ์ไม่เพียงพอ กรุณาเพิ่มพื้นที่ว่างแล้วลองใหม่อีกครั้ง");
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
                        self.say("มอบรางวัล ENDLESS เรียบร้อยแล้ว");
                    } else {
                        self.say("ช่องเก็บอุปกรณ์ไม่เพียงพอขณะมอบรางวัล ENDLESS");
                        return;
                    }
                }
                if (!find) {
                    self.say(
                            "ไม่มีรางวัลยอด 43 ล้านที่สามารถรับได้ในขณะนี้\r\n#e(รับได้ " + count + " ครั้ง, รับไปแล้ว "
                                    + remain + " ครั้ง)");
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
        String text = "ข้าเตรียมของขวัญไว้ให้แล้วสำหรับการพักผ่อน!\r\n"
                + "ระบบจะนับเวลาพักผ่อนให้ทุกๆ 540 นาทีที่สะสมได้\r\n"
                + "มีของขวัญทั้งหมด 15 ชิ้นที่สามารถรับได้ หวังว่าจะถูกใจนะ!\r\n\r\n"
                + "เวลาพักผ่อนสะสมปัจจุบัน : " + restPoint + " นาที\r\n\r\n";

        text += "#L0# เรียนรู้เกี่ยวกับระบบพักผ่อน#l\r\n";
        text += "#L1# รับรางวัลเวลาพักผ่อนสะสม#l\r\n";
        text += "#L2# รีเซ็ตเวลาพักผ่อนสะสม#l\r\n";

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
            text = "สามารถรับรางวัลตามเวลาพักผ่อนสะสมได้ดังนี้\r\n";
            text += "เวลาจะสะสมเมื่อนั่งบนเก้าอี้หรือสัตว์ขี่ในเมือง\r\n";
            for (int[] timeReward : restTimeRewards) {
                int time = timeReward[0];
                int itemID = timeReward[1];
                int itemQty = timeReward[2];

                text += time + " นาที - #i" + itemID + "# #t" + itemID + "# " + itemQty + " ชิ้น \r\n";
            }
            self.sayOk(text);
            return;
        } else if (menu == 1) {
            text = "รางวัลเวลาพักผ่อนสะสมมีดังนี้ (สะสมปัจจุบัน : " + restPoint + " นาที)\r\n";
            for (int i = 0; i < restTimeRewards.length; i++) {
                int[] timeReward = restTimeRewards[i];
                int time = timeReward[0];
                int itemID = timeReward[1];
                int itemQty = timeReward[2];
                String timeKey = "recv" + time;
                int timeValue = getPlayer().getOneInfoQuestInteger(QuestExConstants.JinRestPointReward.getQuestID(),
                        timeKey);
                if (restPoint < time) {
                    text += "#L" + i + "#" + time + " นาที #i" + itemID + "# #t" + itemID + "# " + itemQty
                            + " ชิ้น #rยังรับไม่ได้#k#l\r\n";
                } else {
                    text += "#L" + i + "#" + time + " นาที #i" + itemID + "# #t" + itemID + "# " + itemQty + " ชิ้น "
                            + (timeValue == 0 ? "#bรับได้#k" : "#rรับแล้ว#k") + "#l\r\n";
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
                self.sayOk("เวลาสะสมไม่เพียงพอ ไม่สามารถรับรางวัลนี้ได้");
                return;
            }
            if (timeValue == 0) {
                if (getPlayer().getInventory(GameConstants.getInventoryType(itemID)).getNumFreeSlot() > 0) {
                    getPlayer().gainItem(itemID, itemQty);
                    getPlayer().updateOneInfo(QuestExConstants.JinRestPointReward.getQuestID(), timeKey, "1");
                    self.sayOk("ได้รับรางวัลแล้ว");
                } else {
                    self.sayOk("ช่องเก็บของไม่เพียงพอ ไม่สามารถรับรางวัลได้");
                }
            } else {
                self.sayOk("ได้รับรางวัลนี้ไปแล้ว");
            }
            return;
        } else if (menu == 2) {
            text = "การรีเซ็ตเวลาพักผ่อนสะสมต้องใช้ \r\n#b60,000 Points#k\r\n";
            text += "หากรีเซ็ตแล้ว รางวัลที่ยังไม่ได้รับจะไม่สามารถรับได้อีก\r\n";
            text += "กรุณาตรวจสอบให้แน่ใจว่าได้รับรางวัลครบทั้งหมดแล้ว\r\n\r\n";
            text += "#fc0xFFB2B2B2#เวลาพักผ่อนสะสมปัจจุบัน : " + restPoint + " นาที#k\r\n";
            text += "#L0#รีเซ็ตเวลาพักผ่อนสะสม#l";
            if (0 == self.askMenu(text)) {
                if (getPlayer().getRealCash() < 60000) {
                    self.say("Points ไม่เพียงพอ ไม่สามารถรีเซ็ตได้");
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
                self.sayOk("รีเซ็ตเรียบร้อยแล้ว");
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
            self.sayOk("คุณทำภารกิจประจำวันของวันนี้สำเร็จแล้ว");
            return;
        }
        String text = "ทำภารกิจด้านล่างให้สำเร็จเพื่อรับ 200 Neo Stones\r\n\r\n";
        text += "- กำจัดมอนสเตอร์ในระดับที่กำหนด 15,000 ตัว "
                + (checkLevelMob ? "#b(สำเร็จ)#k" : ("#b(กำลังดำเนินการ " + dailyLevelMob + "/15000)#k"))
                + "\r\n";
        text += "- ใช้ Rune 5 ครั้ง "
                + (checkRuneUse ? "#b(สำเร็จ)#k" : ("#b(กำลังดำเนินการ " + dailyRuneUse + "/5)#k"))
                + "\r\n\r\n";
        text += "#L0#รับรางวัลภารกิจประจำวัน#l";
        if (0 == self.askMenu(text)) {
            if (checkLevelMob && checkRuneUse) {
                getPlayer().gainStackEventGauge(0, 200, true);
                getPlayer().updateOneInfo(QuestExConstants.JinQuestExAccount.getQuestID(), "DailyMissionClear", "1");
                self.sayOk("ภารกิจประจำวันเสร็จสิ้น ได้รับ 200 Neo Stones");
            } else {
                self.sayOk("ยังทำภารกิจประจำวันไม่ครบถ้วน");
                return;
            }
        }
    }

    public void sendCubeLevelUpInfo() {
        GradeRandomOption[] options = { GradeRandomOption.Red, GradeRandomOption.Black, GradeRandomOption.Additional,
                GradeRandomOption.AmazingAdditional };
        String cubeTotalInfo = "ตรวจสอบ #bจำนวนครั้งการใช้ Cube ที่เหลือ#k สำหรับการเลื่อนระดับ\r\n"
                + "ค่าดังกล่าวจะถูกรีเซ็ตเมื่อระดับเพิ่มขึ้น และ #bแชร์กันภายในบัญชี#k\r\n\r\n";
        for (GradeRandomOption option : options) {
            String cubeString = "";
            switch (option) {
                case Red:
                    cubeString = "Red Cube";
                    break;
                case Black:
                    cubeString = "Black Cube";
                    break;
                case Additional:
                    cubeString = "Additional Cube";
                    break;
                case AmazingAdditional:
                    cubeString = "White Additional Cube";
                    break;
                default:
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
                    gradeString = "Unique Legendary";
                }
                int levelUpCount = GameConstants.getCubeLevelUpCount(option, grade);
                cubeTotalInfo += gradeString + " สะสม " + tryCount + " จาก " + levelUpCount + " ครั้ง\r\n";
            }
            if (option != GradeRandomOption.AmazingAdditional) {
                cubeTotalInfo += "\r\n";
            }
        }
        self.sayOk(cubeTotalInfo);
    }
}