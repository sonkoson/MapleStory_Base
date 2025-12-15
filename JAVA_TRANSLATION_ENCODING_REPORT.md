# Java Translation & Encoding Report

## 1. Overview
*   **Total .java Files Scanned**: ~300+ files (based on `find_by_name` results).
*   **Encoding Status**: 
    *   Sampled files (`SoulMaster.java`, `MapleCharacter.java`, `HiredMerchant.java`, `NPCConversationManager.java`) appear to be **UTF-8** encoded and are readable.
    *   No obvious "Mojibake" (garbled text) was observed in the sampled files, indicating that if Korean text exists, it is likely correctly encoded or already widely supported.
    *   **Recommendation**: Run a batch script to enforce UTF-8 without BOM across all `.java` files to ensure consistency, as visual inspection cannot detect BOMs or subtle encoding mixes in 300+ files.

## 2. Text Processing Targets Analysis
### A. Developer / Server Messages (English Target)
*   **Current State**: 
    *   Most logging and console output found in `CheatTracker.java` and `AdminClient.java` is already in **English**.
    *   Examples: "Disconnected Packet spamming", "You have been disconnected due to abnormal activity."
*   **Action Required**: Verification that no Korean comments remain. `grep` searches for Korean characters `[가-힣]` were mostly negative in `src` except for specific mixed strings in `MapleCharacter.java`, suggesting the codebase is largely English or localized already.
*   **Proposed Fix**: Systematic review of comments in `CheatTracker.java` and internal logic files.

### B. Player-Visible Text (Thai Target)
*   **Current State**: 
    *   **Mixed Language**: Significant mix of Thai, English, and occasional Korean found in player-facing methods.
    *   **Files Impacted**: 
        *   `src/scripting/NPCConversationManager.java`: Heavy usage of `sendNext`, `sendSimple` with hardcoded English and Thai strings.
        *   `src/objects/users/MapleCharacter.java`: Contains system messages (`dropMessage`) in mixed Thai, English, and Korean.
        *   `src/objects/shop/HiredMerchant.java`: Contains English system messages.
        *   `src/scripting/ReactorScriptManager.java`: Contains English messages.

## 3. Proposed Actions
1.  **Bulk Encoding Normalization**: Execute a script to read all `.java` files and rewrite them as UTF-8 (No BOM).
2.  **Localization Updates**: Apply translations to the identified files (detailed in the Text Localization Report). 

## 4. Verification Notes
*   Encoding check passed on key files.
*   No binary corruption detected.
*   Korean text presence is lower than expected in `src` (likely concentrated in `scripts/` or xmls, or `MapleCharacter.java`).
