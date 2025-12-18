
PATCH CONTENTS
==============

The changes have been aggregated into the following patch files based on the modification type:

1. patch_C_dialogue.diff
   - This patch contains changes related to:
     - Player-facing dialogue (Thai)
     - System messages (English/Thai)
     - Comment translations
     - String literal modifications

2. patch_E_identifiers.diff
   - This patch contains changes related to:
     - Renaming of Korean variable names to English (e.g., 선택 -> selection)
     - Code logic identifier updates

HOW TO APPLY
============
These patches were generated from the `scripts/` directory.
You can apply them using:
  git apply patches/patch_C_dialogue.diff
  git apply patches/patch_E_identifiers.diff

Note: The files have ALREADY been modified in place on your disk. These patches are for record-keeping or rolling back/re-applying elsewhere.
