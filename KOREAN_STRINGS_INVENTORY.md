# Korean Strings Inventory
**Generated:** 2025-12-16 01:03:00 +07:00  
**Purpose:** Complete list of files containing Korean text for translation

---

## Summary

| Metric | Count |
|--------|-------|
| **Files with Korean Text** | 60 |
| **Total Korean Occurrences** | ~34,553 |
| **Location** | `data/scripts/script/` |

---

## Files Requiring Translation

### Priority 1: Server NPCs (HIGH - Core Gameplay)

#### 1. `data/scripts/script/Server/JinCustomNPC.java`
- **Lines:** 2,700
- **Estimated Korean Strings:** ~1,200
- **Context:** Extra Ability system, membership shop, NPC dialogue
- **Estimated Time:** 10-12 hours
- **Impact:** CRITICAL - Core feature

#### 2. `data/scripts/script/Server/RoyalCustomNPC.java`
- **Context:** Royal server custom NPC features
- **Estimated Time:** 8-10 hours
- **Impact:** HIGH

#### 3. `data/scripts/script/Server/ZeniaCustomNPC.java`
- **Context:** Zenia server custom NPC features
- **Estimated Time:** 6-8 hours
- **Impact:** MEDIUM-HIGH

**Subtotal Priority 1:** 24-30 hours

---

### Priority 2: Boss Scripts (MEDIUM - Gameplay Content)

1. `data/scripts/script/Boss/Arkarium.java`
2. `data/scripts/script/Boss/BossOutPortal.java`
3. `data/scripts/script/Boss/Cygnus.java`
4. `data/scripts/script/Boss/GuardianAngelSlime.java`
5. `data/scripts/script/Boss/Hillah.java`
6. `data/scripts/script/Boss/Horntail.java`
7. `data/scripts/script/Boss/Kalos.java`
8. `data/scripts/script/Boss/Magnus.java`
9. `data/scripts/script/Boss/Mitsuhide.java`
10. `data/scripts/script/Boss/Papulatus.java`
11. `data/scripts/script/Boss/PinkBeen.java`
12. `data/scripts/script/Boss/RootAbyss.java`
13. `data/scripts/script/Boss/Tengu.java`
14. `data/scripts/script/Boss/VonLeon.java`
15. `data/scripts/script/Boss/Zakum.java`

**Subtotal Priority 2:** 12-15 hours

---

### Priority 3: Quest Scripts (MEDIUM - Story Content)

1. `data/scripts/script/Quest/GenesisQuest.java`
2. `data/scripts/script/Quest/GoldenWagon.java`
3. `data/scripts/script/Quest/HongboNPC.java`
4. `data/scripts/script/Quest/MapleGM.java`
5. `data/scripts/script/Quest/Phantom.java`
6. `data/scripts/script/Quest/StepUp.java`
7. `data/scripts/script/Quest/YetiXPinkBean.java`

**Subtotal Priority 3:** 8-10 hours

---

### Priority 4: Arcane River Scripts (LOW - Regional Content)

1. `data/scripts/script/ArcaneRiver/Arcana.java`
2. `data/scripts/script/ArcaneRiver/Cernium.java`
3. `data/scripts/script/ArcaneRiver/ChewChewIsland.java`
4. `data/scripts/script/ArcaneRiver/Esfera.java`
5. `data/scripts/script/ArcaneRiver/HotelArcs.java`
6. `data/scripts/script/ArcaneRiver/LabyrinthofSuffering.java`
7. `data/scripts/script/ArcaneRiver/Lacheln.java`
8. `data/scripts/script/ArcaneRiver/Limen.java`
9. `data/scripts/script/ArcaneRiver/MoonBridge.java`
10. `data/scripts/script/ArcaneRiver/Morass.java`
11. `data/scripts/script/ArcaneRiver/VanishingJourney.java`

**Subtotal Priority 4:** 6-8 hours

---

### Priority 5: Daily Quest Scripts (LOW)

1. `data/scripts/script/DailyQuest/ArcaneRiver.java`
2. `data/scripts/script/DailyQuest/DarkWorldTree.java`
3. `data/scripts/script/DailyQuest/ErdaSpectrum.java`
4. `data/scripts/script/DailyQuest/Haven.java`
5. `data/scripts/script/DailyQuest/SpiritSavior.java`

**Subtotal Priority 5:** 3-4 hours

---

### Priority 6: Miscellaneous Scripts (LOW)

#### Event Scripts
1. `data/scripts/script/Event/HasteEvent.java`
2. `data/scripts/script/Event/UIEventInfo.java`

#### FieldSet Scripts
1. `data/scripts/script/FieldSet/DamageMeasurement.java`
2. `data/scripts/script/FieldSet/FlagRace.java`
3. `data/scripts/script/FieldSet/SharenianUndergroundCulvert.java`
4. `data/scripts/script/FieldSet/TangyoonKitchen.java`
5. `data/scripts/script/FieldSet/YutaFarm.java`

#### Item Scripts
1. `data/scripts/script/item/Consume.java`
2. `data/scripts/script/item/MasteryBook.java`
3. `data/scripts/script/item/SoulPiece.java`
4. `data/scripts/script/item/SymbolVoucher.java`
5. `data/scripts/script/item/TheSeedRing.java`

#### Job Scripts
1. `data/scripts/script/Job/Luminous.java`
2. `data/scripts/script/Job/Zero.java`

#### Profession Scripts
1. `data/scripts/script/Profession/Profession.java`

#### Utility Scripts
1. `data/scripts/script/Util/GMUtil.java`
2. `data/scripts/script/Util/OneClickSet.java`
3. `data/scripts/script/Util/Recommend.java`
4. `data/scripts/script/Util/Util.java`

**Subtotal Priority 6:** 8-10 hours

---

## Total Estimated Translation Time

| Priority | Files | Time Estimate |
|----------|-------|---------------|
| Priority 1 (Server NPCs) | 3 | 24-30 hours |
| Priority 2 (Boss) | 15 | 12-15 hours |
| Priority 3 (Quest) | 7 | 8-10 hours |
| Priority 4 (Arcane River) | 11 | 6-8 hours |
| Priority 5 (Daily Quest) | 5 | 3-4 hours |
| Priority 6 (Misc) | 19 | 8-10 hours |
| **TOTAL** | **60** | **61-77 hours** |

---

## Translation Workflow

### For Each File:

1. **Extract Korean Strings**
   - Identify all Korean text
   - Classify as player-facing or developer-facing
   - Note method context (self.say, self.askMenu, etc.)

2. **Translate**
   - Player-facing → Thai (natural MMORPG style)
   - Developer comments → English
   - Preserve formatting codes
   - Maintain game terminology

3. **Review**
   - Check tone appropriateness
   - Verify formatting codes
   - Ensure terminology consistency

4. **Test**
   - Verify no Korean remains
   - Check string length (UI fit)
   - In-game testing (if possible)

---

## Next Steps

1. ✅ Phase 1 Complete: Encoding converted
2. ⏳ **Current:** Extract Korean strings from Priority 1 files
3. ⏳ Begin translation of JinCustomNPC.java
4. ⏳ Continue with remaining priorities
5. ⏳ Quality assurance and testing

---

**Status:** Ready for translation  
**Encoding:** ✅ UTF-8 without BOM  
**Backup:** ✅ Created (backup_java_20251216_010217)
