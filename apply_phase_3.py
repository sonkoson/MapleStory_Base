
import os
import re
import difflib
from translation_dict import TRANSLATIONS, VAR_RENAMES

SEARCH_DIR = "scripts"
PATCH_DIR = "patches"
os.makedirs(PATCH_DIR, exist_ok=True)

f_dialogue = open(os.path.join(PATCH_DIR, "patch_C_dialogue.diff"), 'w', encoding='utf-8')
f_ids = open(os.path.join(PATCH_DIR, "patch_E_identifiers.diff"), 'w', encoding='utf-8')

# Prepare Regex for Strings
# Sort by length desc to match longest phrases first
sorted_keys = sorted(TRANSLATIONS.keys(), key=len, reverse=True)
TRANS_REGEX = re.compile('|'.join(map(re.escape, sorted_keys)))

# Prepare Regex for Vars
sorted_vars = sorted(VAR_RENAMES.keys(), key=len, reverse=True)
VAR_REGEX = re.compile(r'\b(' + '|'.join(map(re.escape, sorted_vars)) + r')\b')

def replace_func_trans(match):
    return TRANSLATIONS[match.group(0)]

def replace_func_var(match):
    return VAR_RENAMES[match.group(0)]

# Regex to capture strings and comments to separate them from code
# Group 1: Double Quote String
# Group 2: Single Quote String
# Group 3: Single Line Comment
# Group 4: Multi Line Comment
TOKENIZER = re.compile(r'("(?:[^"\\]|\\.)*")|(\'(?:[^\'\\]|\\.)*\')|(//.*)|(/\*[\s\S]*?\*/)')

files_processed = 0
files_changed = 0

print("Starting translation & renaming...")

for root, dirs, files in os.walk(SEARCH_DIR):
    for file in files:
        if not file.endswith(".js"):
            continue
        
        path = os.path.join(root, file)
        files_processed += 1
        
        try:
            with open(path, 'r', encoding='utf-8') as f:
                original = f.read()
        except UnicodeDecodeError:
            print(f"Skipping {path} (encoding error)")
            continue
            
        # We need to process the file in parts:
        # 1. Identify "Code" vs "String/Comment"
        # 2. In "Code", apply VAR_RENAMES
        # 3. In "String", apply TRANSLATIONS
        # 4. In "Comment", try apply TRANSLATIONS or specific comment logic
        
        new_content_parts = []
        last_idx = 0
        
        is_changed_ids = False
        is_changed_dialogue = False
        
        # Iterate over tokens (strings/comments)
        for match in TOKENIZER.finditer(original):
            # Process the code block (between last match and this match)
            code_block = original[last_idx:match.start()]
            if code_block:
                # Apply Var Renaming to Code
                new_code, count = VAR_REGEX.subn(replace_func_var, code_block)
                if count > 0: is_changed_ids = True
                new_content_parts.append(new_code)
            
            # Process the token
            token = match.group(0)
            
            if match.group(1) or match.group(2): # String
                # Apply Translation to String Content
                # Use subn to check if changed
                new_token, count = TRANS_REGEX.subn(replace_func_trans, token)
                if count > 0: is_changed_dialogue = True
                new_content_parts.append(new_token)
            
            elif match.group(3) or match.group(4): # Comment
                # Translating comments is optional but good. We use same Dictionary.
                # English comments preferred.
                new_token, count = TRANS_REGEX.subn(replace_func_trans, token)
                new_content_parts.append(new_token)
            
            last_idx = match.end()
            
        # Process remaining code after last token
        if last_idx < len(original):
            code_block = original[last_idx:]
            new_code, count = VAR_REGEX.subn(replace_func_var, code_block)
            if count > 0: is_changed_ids = True
            new_content_parts.append(new_code)
            
        final_content = "".join(new_content_parts)
        
        if final_content != original:
            files_changed += 1
            
            # Generate Diffs
            # We need to diff aggressively.
            
            # Write to file
            with open(path, 'w', encoding='utf-8') as f:
                f.write(final_content)
                
            # Create diff entry
            diff = difflib.unified_diff(
                original.splitlines(keepends=True),
                final_content.splitlines(keepends=True),
                fromfile=path,
                tofile=path
            )
            diff_text = "".join(diff)
            
            if is_changed_ids:
                f_ids.write(diff_text)
            if is_changed_dialogue:
                f_dialogue.write(diff_text)
                
f_dialogue.close()
f_ids.close()

print(f"Processed {files_processed} files.")
print(f"Modified {files_changed} files.")
