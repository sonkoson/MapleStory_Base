package script.item;

import constants.GameConstants;
import network.SendPacketOpcode;
import network.encode.PacketEncoder;
import objects.item.*;
import objects.users.achievement.AchievementFactory;
import objects.utils.Randomizer;
import objects.utils.Triple;
import scripting.newscripting.ScriptEngineNPC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TheSeedRing extends ScriptEngineNPC {

    public void the_seed_ring() {
        if (getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 1
                || getPlayer().getInventory(MapleInventoryType.ETC).getNumFreeSlot() < 1
                || getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 1) {
            self.sayOk("ช่องเก็บของไม่เพียงพอ กรุณาทำช่องว่างในช่อง Equip, Use, และ Etc อย่างน้อยช่องละ 1 ช่อง");
            return;
        }
        if (getPlayer().getItemQuantity(itemID, false) <= 0) {
            getPlayer().ban("Duplicate Seed Ring Bug Detected", true, true, true);
            return;
        }
        int[][] rewards = reward(itemID);
        List<Triple<Integer, Integer, Integer>> itemsByPercent = new ArrayList<>();
        for (int i = 0; i < rewards.length; i++) {
            itemsByPercent.add(new Triple<>(rewards[i][0], rewards[i][1], rewards[i][2]));
        }
        Collections.shuffle(itemsByPercent);

        int selectItem = 0;
        int rewardqty = 1;
        while (true) {
            int Random = Randomizer.nextInt(100);
            Collections.shuffle(itemsByPercent);
            if (itemsByPercent.get(0).getRight() >= Random) {
                selectItem = itemsByPercent.get(0).getLeft();
                rewardqty = itemsByPercent.get(0).getMid();
                break;
            }
        }

        if (selectItem > 0) {
            gainItem(itemID, (short) -1, false, -1, -1, "", getClient(), false);
            if (GameConstants.isTheSeedRing(selectItem)) {
                MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
                Item rewardItem = ii.getEquipById(selectItem);
                Equip rewardEquip = null;
                if (rewardItem != null) {
                    rewardEquip = (Equip) rewardItem;
                }
                int seedLevel = rewardSeedLevel(itemID, selectItem);
                rewardEquip.setTheSeedRingLevel((byte) seedLevel);
                AchievementFactory.checkLotteryResultItem(getPlayer(), itemID, rewardEquip.getItemId(), seedLevel);
                short TI = MapleInventoryManipulator.addbyItem(getClient(), rewardEquip, false);
                TheSeedGetItemPacket(selectItem, rewardqty, getPlayer().getItemQuantity(itemID, false) > 0);
            } else {
                MapleInventoryManipulator.addById(getClient(), selectItem, (short) rewardqty,
                        "Items obtained from Alicia's Ring Box");
                TheSeedGetItemPacket(selectItem, rewardqty, getPlayer().getItemQuantity(itemID, false) > 0);
            }
        }
    }

    public int[][] reward(int item) {
        switch (item) {
            case 2028271: { // Grade 9
                int[][] rewards = {
                        { 1113117, 1, 9 }, // Swift Ring
                        { 1113118, 1, 9 }, // Overpass Ring
                        { 1113120, 1, 9 }, // Reflective Ring
                        { 5062500, 5, 5 }, // Additional Cube
                        { 5062010, 5, 10 }, // Black Cube
                        { 4001832, 500, 63 }, // Spell Trace
                };
                return rewards;
            }
            case 2028270: { // Grade 8
                int[][] rewards = {
                        { 1113117, 1, 9 }, // Swift Ring
                        { 1113118, 1, 9 }, // Overpass Ring
                        { 1113120, 1, 9 }, // Reflective Ring
                        { 5062500, 5, 10 }, // Additional Cube
                        { 5062010, 5, 10 }, // Black Cube
                        { 4001832, 500, 58 }, // Spell Trace
                };
                return rewards;
            }
            case 2028269: { // Grade 7
                int[][] rewards = {
                        { 1113117, 1, 9 }, // Swift Ring
                        { 1113118, 1, 9 }, // Overpass Ring
                        { 1113120, 1, 9 }, // Reflective Ring
                        { 5062500, 5, 13 }, // Additional Cube
                        { 5062010, 5, 13 }, // Black Cube
                        { 4001832, 500, 52 }, // Spell Trace
                };
                return rewards;
            }
            case 2028268:
            case 2028267:
            case 2028266: { // Grade 6, 5, 4
                int[][] rewards = {
                        { 1113117, 1, 8 }, // Swift Ring
                        { 1113118, 1, 8 }, // Overpass Ring
                        { 1113120, 1, 8 }, // Reflective Ring

                        { 1113103, 1, 2 }, // Durability Ring
                        { 1113100, 1, 2 }, // Limit Ring
                        { 1113101, 1, 2 }, // Health Cut Ring
                        { 1113102, 1, 2 }, // Mana Cut Ring

                        { 5062500, 10, 10 }, // Additional Cube
                        { 5062010, 20, 10 }, // Black Cube
                        { 4001832, 500, 48 }, // Spell Trace
                };
                return rewards;
            }
            case 2028265:
            case 2028264: { // Grade 3, 2
                int[][] rewards = {
                        { 1113117, 1, 8 }, // Swift Ring
                        { 1113118, 1, 8 }, // Overpass Ring
                        { 1113120, 1, 8 }, // Reflective Ring
                        { 1113103, 1, 2 }, // Durability Ring
                        { 1113100, 1, 2 }, // Limit Ring
                        { 1113101, 1, 2 }, // Health Cut Ring
                        { 1113102, 1, 2 }, // Mana Cut Ring

                        { 1113098, 1, 2 }, // Restraint Ring
                        { 1113099, 1, 2 }, // Ultimatum Ring
                        { 1113122, 1, 2 }, // Risk Taker Ring
                        { 1113104, 1, 2 }, // Crit Damage Ring
                        { 1113119, 1, 2 }, // Shield Swap Ring

                        { 5062500, 10, 10 }, // Additional Cube
                        { 5062010, 20, 10 }, // Black Cube
                        { 5062503, 20, 10 }, // White Additional Cube
                        { 2048723, 20, 10 }, // Eternal Rebirth Flame

                        { 4001832, 500, 48 }, // Spell Trace
                };
                return rewards;
            }
            case 2028263: { // Grade 1
                int[][] rewards = {
                        { 1113117, 1, 3 }, // Swift Ring
                        { 1113118, 1, 3 }, // Overpass Ring
                        { 1113120, 1, 3 }, // Reflective Ring
                        { 1113103, 1, 3 }, // Durability Ring
                        { 1113100, 1, 3 }, // Limit Ring
                        { 1113101, 1, 3 }, // Health Cut Ring
                        { 1113102, 1, 3 }, // Mana Cut Ring
                        { 1113098, 1, 3 }, // Restraint Ring
                        { 1113099, 1, 3 }, // Ultimatum Ring
                        { 1113122, 1, 3 }, // Risk Taker Ring
                        { 1113104, 1, 3 }, // Crit Damage Ring
                        { 1113119, 1, 3 }, // Shield Swap Ring
                        { 1113113, 1, 3 }, // Weapon Puff - S Ring
                        { 1113114, 1, 3 }, // Weapon Puff - D Ring
                        { 1113115, 1, 3 }, // Weapon Puff - I Ring
                        { 1113116, 1, 3 }, // Weapon Puff - L Ring
                        { 1113108, 1, 3 }, // Ring of Sum
                        { 1113126, 1, 3 }, // Recover Stance Ring
                        { 1113125, 1, 3 }, // Crisis HM Ring
                        { 1113109, 1, 3 }, // Level Puff - S Ring
                        { 1113110, 1, 3 }, // Level Puff - D Ring
                        { 1113111, 1, 3 }, // Level Puff - I Ring
                        { 1113112, 1, 3 }, // Level Puff - L Ring
                        { 1113121, 1, 3 }, // Burden Lift Ring
                        { 1113105, 1, 3 }, // Crit Defense Ring
                        { 1113106, 1, 3 }, // Crit Shift Ring
                        { 1113107, 1, 3 }, // Stance Shift Ring
                        { 1113123, 1, 3 }, // Crisis H Ring
                        { 1113124, 1, 3 }, // Crisis M Ring
                        { 1113127, 1, 3 }, // Recover Defense Ring

                        { 5062500, 20, 2 }, // Additional Cube
                        { 5062010, 20, 2 }, // Black Cube
                        { 5062503, 10, 2 }, // White Additional Cube
                        { 2048753, 10, 2 }, // Eternal Rebirth Flame
                        { 4001832, 500, 2 }, // Spell Trace
                };
                return rewards;
            }
            case 2028273: { // Alicia's Ring Box
                int[][] rewards = {
                        { 1113117, 1, 3 }, // Swift Ring
                        { 1113118, 1, 3 }, // Overpass Ring
                        { 1113120, 1, 3 }, // Reflective Ring
                        { 1113103, 1, 3 }, // Durability Ring
                        { 1113100, 1, 3 }, // Limit Ring
                        { 1113101, 1, 3 }, // Health Cut Ring
                        { 1113102, 1, 3 }, // Mana Cut Ring
                        { 1113098, 1, 3 }, // Restraint Ring
                        { 1113099, 1, 3 }, // Ultimatum Ring
                        { 1113122, 1, 3 }, // Risk Taker Ring
                        { 1113104, 1, 3 }, // Crit Damage Ring
                        { 1113119, 1, 3 }, // Shield Swap Ring
                        { 1113113, 1, 3 }, // Weapon Puff - S Ring
                        { 1113114, 1, 3 }, // Weapon Puff - D Ring
                        { 1113115, 1, 3 }, // Weapon Puff - I Ring
                        { 1113116, 1, 3 }, // Weapon Puff - L Ring
                        { 1113108, 1, 3 }, // Ring of Sum
                        { 1113126, 1, 3 }, // Recover Stance Ring
                        { 1113125, 1, 3 }, // Crisis HM Ring
                        { 1113109, 1, 3 }, // Level Puff - S Ring
                        { 1113110, 1, 3 }, // Level Puff - D Ring
                        { 1113111, 1, 3 }, // Level Puff - I Ring
                        { 1113112, 1, 3 }, // Level Puff - L Ring
                        { 1113121, 1, 3 }, // Burden Lift Ring
                        { 1113105, 1, 3 }, // Crit Defense Ring
                        { 1113106, 1, 3 }, // Crit Shift Ring
                        { 1113107, 1, 3 }, // Stance Shift Ring
                        { 1113123, 1, 3 }, // Crisis H Ring
                        { 1113124, 1, 3 }, // Crisis M Ring
                        { 1113127, 1, 3 }, // Recover Defense Ring
                };
                return rewards;
            }
            default: {
                int[][] rewards = {
                        { 1113117, 1, 7 }, // Swift Ring
                        { 1113118, 1, 7 }, // Overpass Ring
                        { 1113120, 1, 7 }, // Reflective Ring
                        { 5062500, 1, 5 }, // Additional Cube
                        { 5062010, 1, 10 }, // Black Cube
                        { 4001832, 500, 69 }, // Spell Trace
                };
                return rewards;
            }
        }
    }

    public int rewardSeedLevel(int seedbox, int item) {
        switch (seedbox) {
            case 2028272: // Ring 10
            case 2028271: // Ring 9
                return 1;
            case 2028270: // Ring 8
                if (Randomizer.nextBoolean()) {
                    return 2;
                } else {
                    return 1;
                }
            case 2028269: // Ring 7
                return Randomizer.rand(1, 3);
            case 2028268: // Ring 6
                if (item == 1113117 || item == 1113118 || item == 1113120) {
                    return Randomizer.rand(1, 3);
                } else {
                    return 1;
                }
            case 2028267: // Ring 5
            case 2028266: // Ring 4
                if (item == 1113117 || item == 1113118 || item == 1113120) {
                    return Randomizer.rand(1, 4);
                } else {
                    return Randomizer.rand(1, 2);
                }
            case 2028265: // Ring 3
                if (item == 1113117 || item == 1113118 || item == 1113120 || item == 1113103 || item == 1113100
                        || item == 1113101 || item == 1113102) {
                    return Randomizer.rand(1, 4);
                } else {
                    return Randomizer.rand(1, 2);
                }
            case 2028264: // Ring 2
                if (item == 1113117 || item == 1113118 || item == 1113120 || item == 1113103 || item == 1113100
                        || item == 1113101 || item == 1113102) {
                    return Randomizer.rand(1, 4);
                } else {
                    return Randomizer.rand(1, 3);
                }
            case 2028263: // Ring 1
                return Randomizer.rand(1, 4);
            case 2028273:
                return Randomizer.rand(3, 4);
            default:
                return 1;
        }
    }

    private void TheSeedGetItemPacket(int itemID, int quantity, boolean canReUnboxing) {
        PacketEncoder p = new PacketEncoder();
        p.writeShort(SendPacketOpcode.USER_ON_EFFECT.getValue());
        p.write(53);
        p.write(0);
        p.write(!canReUnboxing); // If 1, reopen window appears (after confirmation)
        p.write(0);
        p.writeInt(itemID);
        p.writeInt(quantity);
        p.write(0);
        p.writeZeroBytes(1000);
        getPlayer().send(p.getPacket());
    }
}
