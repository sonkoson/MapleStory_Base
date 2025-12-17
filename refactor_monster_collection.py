
import os
import re

filepath = "src/network/game/processors/monstercollection/MonsterCollectionHandler.java"

with open(filepath, 'r', encoding='utf-8') as f:
    content = f.read()

# 1. Imports
imports_to_add = [
    "import java.time.LocalDateTime;",
    "import java.time.format.DateTimeFormatter;"
]
imports_to_remove = [
    "import java.text.SimpleDateFormat;",
    "import java.util.Date;"
]

for imp in imports_to_remove:
    content = content.replace(imp, "")

if "package network.game.processors.monstercollection;" in content:
    import_block = "package network.game.processors.monstercollection;\n\n" + "\n".join(imports_to_add)
    content = content.replace("package network.game.processors.monstercollection;", import_block)

# 2. Translations
translations = [
    ('"몬스터 컬๋ ‰션 보상"', '"รางวัล Monster Collection"'),
    ('"몬스터 컬๋ ‰션 스페셜 보상"', '"รางวัลพิเศษ Monster Collection"'),
    ('"몬스터 컬๋ ‰션 훈장 보상"', '"รางวัลเหรียญ Monster Collection"'),
    ('"몬스터 컬๋ ‰션 탐험보상"', '"รางวัลการสำรวจ Monster Collection"')
]

for old, new_str in translations:
    content = content.replace(old, new_str)

# 3. Refactoring Date/SimpleDateFormat

# Replaces Case 4 Exploration Check
# Original:
#                   int defaultKey = 20 + exploreIndex;
#                   SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmm");
#                   Date cycleDate = new Date();
#                   String endTime = sdf2.format(cycleDate);
#                   String end = user.getOneInfo(defaultKey, "end");
# ...
#                   try {
#                      Date now = sdf2.parse(sdf2.format(new Date()));
#                      if (now.compareTo(sdf2.parse(end)) < 0) {
#                         c.getSession().writeAndFlush(CWvsContext.enableActions(user));
#                         return;
#                      }
#                   } catch (Exception var40) {
#                      c.getSession().writeAndFlush(CWvsContext.enableActions(user));
#                      return;
#                   }

# We need a regex or careful replacement because lines might differ slightly.
# Since I have the full file content, I can target specific blocks.

# Block 1: Date setup in Case 4
block1_search = (
    r'int defaultKey = 20 \+ exploreIndex;\s*'
    r'SimpleDateFormat sdf2 = new SimpleDateFormat\("yyyyMMddHHmm"\);\s*'
    r'Date cycleDate = new Date\(\);\s*'
    r'String endTime = sdf2.format\(cycleDate\);'
)
block1_replace = (
    'int defaultKey = 20 + exploreIndex;\n'
    '                   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");\n'
    '                   String endTime = LocalDateTime.now().format(formatter);'
)
content = re.sub(block1_search, block1_replace, content)

# Block 2: Date Comparison in Case 4
block2_search = (
    r'try \{\s*'
    r'Date now = sdf2\.parse\(sdf2\.format\(new Date\(\)\)\);\s*'
    r'if \(now\.compareTo\(sdf2\.parse\(end\)\) < 0\) \{\s*'
    r'c\.getSession\(\)\.writeAndFlush\(CWvsContext\.enableActions\(user\)\);\s*'
    r'return;\s*'
    r'\}\s*'
    r'\} catch \(Exception var40\) \{'
)
block2_replace = (
    'try {\n'
    '                      LocalDateTime now = LocalDateTime.now();\n'
    '                      LocalDateTime endDate = LocalDateTime.parse(end, formatter);\n'
    '                      if (now.isBefore(endDate)) {\n'
    '                         c.getSession().writeAndFlush(CWvsContext.enableActions(user));\n'
    '                         return;\n'
    '                      }\n'
    '                   } catch (Exception var40) {'
)
content = re.sub(block2_search, block2_replace, content)

# Block 3: ExploreMonsterCollection - Date Calculation
# Original:
#                SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmm");
#                Date cycleDate = new Date();
#                cycleDate.setTime(cycleDate.getTime() + 60000 * explorationCycle);
#                String endTime = sdf.format(cycleDate);

block3_search = (
    r'SimpleDateFormat sdf = new SimpleDateFormat\("YYYYMMddHHmm"\);\s*'
    r'Date cycleDate = new Date\(\);\s*'
    r'cycleDate.setTime\(cycleDate.getTime\(\) \+ 60000 \* explorationCycle\);\s*'
    r'String endTime = sdf.format\(cycleDate\);'
)
block3_replace = (
    'DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");\n'
    '               String endTime = LocalDateTime.now().plusMinutes(explorationCycle).format(formatter);'
)
content = re.sub(block3_search, block3_replace, content)

# Block 4: CancelExploreMonsterCollection
# Original:
#                SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmm");
#                Date cycleDate = new Date();
#                cycleDate.setTime(cycleDate.getTime() + 60000 * explorationCycle);
#                String endTime = sdf.format(cycleDate);

# This block is functionally seemingly for calculating what endTime WOULD have been?
# Wait, CancelExplore code: 
# cycleDate.setTime(cycleDate.getTime() + 60000 * explorationCycle); 
# Why calculation here? This function cancels the exploration.
# It seems to be re-using code logic or just generating a dummy string?
# Regardless, we match the original logic.

content = re.sub(block3_search, block3_replace, content) # The regex is the same

with open(filepath, 'w', encoding='utf-8') as f:
    f.write(content)

print(f"Refactor MonsterCollectionHandler.java completed")
