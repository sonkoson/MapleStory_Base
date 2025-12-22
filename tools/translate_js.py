import os
import re
import sys
from pathlib import Path
from translate import Translator

# Ensure stdout can handle UTF‑8 (Thai characters)
if hasattr(sys.stdout, "reconfigure"):
    sys.stdout.reconfigure(encoding='utf-8')

# Initialize translator (LibreTranslate public instance)
translator = Translator(provider='libre', to_lang='en')  # default, will be overridden per call

# Functions considered player‑facing (translate to Thai)
PLAYER_FUNCS = [
    'sendSay', 'sendNext', 'sendPrev', 'sendOk', 'sendYesNo',
    'sendAskAccept', 'sendAskText', 'sendAskNumber', 'sendAskMenu',
    'sendSimple', r'cm\.send', r'sm\.send', r'cm\.sendSimple', r'sm\.sendSimple'
]
# Functions considered server‑log (translate to English)
LOG_FUNCS = [
    r'log\.info', r'log\.warn', r'log\.error',
    r'System\.out\.println', r'System\.err\.println', r'logger\.[a-zA-Z_]+'
]

# Regex patterns for detecting calls with string literals
player_pattern = re.compile(r'(' + '|'.join(PLAYER_FUNCS) + r')\s*\(\s*"([^"]*)"\s*\)')
log_pattern = re.compile(r'(' + '|'.join(LOG_FUNCS) + r')\s*\(\s*"([^"]*)"\s*\)')

# Detect Korean characters
korean_re = re.compile(r'[\uac00-\ud7a3]')
# Detect Thai characters (skip already‑translated strings)
thai_re = re.compile(r'[\u0e00-\u0e7f]')

# Preserve MapleStory control codes and placeholders during translation
CONTROL_CODE_RE = re.compile(r'(#[brklfns])')
PLACEHOLDER_RE = re.compile(r'(%[ds])')

def mask_text(text):
    text = CONTROL_CODE_RE.sub(lambda m: f'[[CTRL{m.group(1)}]]', text)
    text = PLACEHOLDER_RE.sub(lambda m: f'[[PH{m.group(1)}]]', text)
    return text

def unmask_text(text):
    text = re.sub(r'\[\[CTRL(#\w)\]\]', r'\1', text)
    text = re.sub(r'\[\[PH(%[ds])\]\]', r'\1', text)
    return text

def translate_string(original, target_lang):
    masked = mask_text(original)
    try:
        temp_trans = Translator(provider='libre', to_lang=target_lang)
        result = temp_trans.translate(masked)
    except Exception as e:
        print(f'Warning: translation error for "{original}": {e}', file=sys.stderr)
        return original
    return unmask_text(result)

def process_file(filepath: Path):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()
    original_content = content

    # Player‑facing strings → Thai
    def repl_player(match):
        func, string = match.group(1), match.group(2)
        if thai_re.search(string):
            return match.group(0)
        translated = translate_string(string, 'th')
        return f"{func}(\"{translated}\")"
    content = player_pattern.sub(repl_player, content)

    # Log strings → English (only if Korean detected)
    def repl_log(match):
        func, string = match.group(1), match.group(2)
        if not korean_re.search(string):
            return match.group(0)
        translated = translate_string(string, 'en')
        return f"{func}(\"{translated}\")"
    content = log_pattern.sub(repl_log, content)

    if content != original_content:
        with open(filepath, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f'Translated {filepath}')
    else:
        print(f'No changes in {filepath}')

def main():
    root = Path(os.getcwd())
    js_files = list(root.rglob('*.js'))
    print(f'Found {len(js_files)} JavaScript files')
    for js in js_files:
        process_file(js)

if __name__ == '__main__':
    main()
