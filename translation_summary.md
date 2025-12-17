
# Translation Summary

## Overview
This document summarizes the translation and Mojibake correction work performed on the `MapleStory_Base` project. The primary goal was to translate Korean strings to English (for internal logging) or Thai (for user-facing messages) and correct Thai Mojibake.

## Processed Directories and Files

### 1. `src/objects/users`
*   **MapleClient.java**:
    *   Translated Korean login/connection messages to English.
    *   Fixed Thai Mojibake in account messages.
*   **MapleCharacter.java**:
    *   Translated Korean internal states and logging to English.
    *   Corrected Thai messages for interactions.
*   **MapleTrade.java**:
    *   Fixed Thai Mojibake in trade messages.
    *   Translated Korean error messages to English.
*   **extra/ExtraAbilityGrade.java \ ExtraAbilityOption.java**:
    *   Translated Korean Ability grades ("Rare", "Unique", etc.) and options.

### 2. `src/objects/fields`
*   **MapleMapFactory.java**:
    *   Translated Korean map loading logs to English.
    *   Translated "Mano appeared..." message to Thai.
*   **MapScriptMethods.java**:
    *   Extensive translation of Korean map scripts (Bosses, Dojos, Events) to Thai/English.
    *   Fixed massive amounts of Thai Mojibake in dialogue.
*   **gameobject/lifes/MapleMonster.java**:
    *   Translated Korean boss attack logs to English.
    *   Fixed Thai Mojibake in monster stats/skills.
*   **child/** (Recursive Processing):
    *   Processed all subdirectories (e.g., `arkarium`, `blackmage`, `dojang`, `karrotte`, `lucid`, `will`, etc.).
    *   Translated specific Boss messages (Cutscenes, Phases) to Thai/English.
    *   Fixed Thai Mojibake in user-facing boss messages.
*   **fieldset/FieldSet.java & childs/**:
    *   Translated internal Korean values for `bossName` and `difficulty` to English (e.g., "칼로스" -> "Kalos", "헬" -> "Hell").
    *   Updated logic in `FieldSet.java` to check for English difficulty strings.

### 3. `src/objects/quest`
*   **MobQuest.java**:
    *   Translated Korean quest requirements (mob names, counts) to Thai.
*   **MapleQuestAction.java**:
    *   Translated Medal acquisition message to English.

### 4. `src/objects/item`
*   **MapleInventoryManipulator.java**:
    *   Translated Korean logging for item operations to English.
*   **MapleItemInformationProvider.java**:
    *   Fixed Thai formatting.

### 5. `src/objects/shop`
*   **MapleShop.java**:
    *   Translated Korean shop logging and errors to English.
    *   Fixed Thai Mojibake in purchase messages.

### 6. `src/objects/utils`
*   **ConnectorController.java**:
    *   Translated UI buttons/labels to English ("Unban Serial", "Threads").
*   **bitFalg.java**:
    *   Translated internal debug string to English.
*   **AutobanManager.java**:
    *   Translated Korean ban logs to English.
    *   Fixed Thai Mojibake in ban reasons.

## Tools Used
*   Multiple Python scripts were created and executed to safely automate replacements across the codebase.
*   `grep` was used iteratively to identify remaining Korean characters and Verify "clean" status.

## Remaining Code
*   `src/objects/utils/StringUtil.java`: Retained `MS949` charset and `replaceNonHangul` method as they are likely essential for low-level packet processing of legacy data.
*   Code comments in Korean were generally left as-is unless they appeared in string literals.

The codebase is now largely localized to English (internal) and Thai (user-facing).
