import os
import re
import json

SOURCE_DIRS = ['src']
OUTPUT_ENCODING = 'encoding_report.json'
OUTPUT_LOCALIZATION = 'localization_report.json'

PLAYER_METHODS_REGEX = r'^(send.*Notice|broadcast.*Notice|send.*Message|broadcast.*Message|send.*Popup|announce.*|notify.*|worldMessage.*|chatMsg|dropMessage)$'

# Compiled regex for method checking
PLAYER_METHOD_PATTERN = re.compile(PLAYER_METHODS_REGEX)

# Regex to find strings: "..." taking care of escaped quotes
STRING_PATTERN = re.compile(r'"((?:[^"\\]|\\.)*)"')

# Regex for Comments
COMMENT_SINGLE = re.compile(r'//(.*)')
COMMENT_MULTI = re.compile(r'/\*([\s\S]*?)\*/')

# Regex for Korean
KOREAN_PATTERN = re.compile(r'[\uac00-\ud7a3]')

def detect_encoding(file_path):
    with open(file_path, 'rb') as f:
        raw = f.read()
    
    # Check for BOM
    if raw.startswith(b'\xef\xbb\xbf'):
        return 'utf-8-sig', raw
    
    encodings = ['utf-8', 'cp949', 'euc-kr', 'windows-1252', 'latin1']
    for enc in encodings:
        try:
            decoded = raw.decode(enc)
            return enc, raw
        except UnicodeDecodeError:
            continue
    return None, raw

def get_line_number(content, index):
    return content.count('\n', 0, index) + 1

def analyze():
    encoding_report = []
    localization_report = []

    for source_dir in SOURCE_DIRS:
        for root, dirs, files in os.walk(source_dir):
            for file in files:
                if not file.endswith('.java'):
                    continue
                
                file_path = os.path.join(root, file)
                abs_path = os.path.abspath(file_path)
                
                enc, raw_bytes = detect_encoding(file_path)
                
                status = 'OK'
                proposed = 'None'
                
                if enc == 'utf-8-sig':
                    status = 'Has BOM'
                    proposed = 'Convert to UTF-8 (Remove BOM)'
                elif enc != 'utf-8':
                    status = f'Non-Standard ({enc})'
                    proposed = 'Convert to UTF-8'
                
                encoding_report.append({
                    'file': abs_path,
                    'encoding': enc,
                    'status': status,
                    'proposed': proposed
                })
                
                if not enc:
                    # Can't read file text for localization analysis
                    continue

                try:
                    content = raw_bytes.decode(enc)
                except:
                    continue 

                # localization analysis
                
                # Check comments
                for match in COMMENT_SINGLE.finditer(content):
                    text = match.group(1)
                    if KOREAN_PATTERN.search(text):
                        localization_report.append({
                            'file': abs_path,
                            'line': get_line_number(content, match.start()),
                            'context': 'Comment',
                            'original': text.strip(),
                            'type': 'korean_comment'
                        })

                for match in COMMENT_MULTI.finditer(content):
                    text = match.group(1)
                    if KOREAN_PATTERN.search(text):
                        localization_report.append({
                            'file': abs_path,
                            'line': get_line_number(content, match.start()),
                            'context': 'Multi-line Comment',
                            'original': text.strip(),
                            'type': 'korean_comment'
                        })

                # Check strings
                for match in STRING_PATTERN.finditer(content):
                    text = match.group(1)
                    start_idx = match.start()
                    
                    # Lookbehind for method context
                    # Grab likely "methodname(" before the string
                    # We accept whitespace and open paren
                    
                    # Look backwards from start_idx
                    # Search for identifier then '('
                    # We iterate backwards through characters ignoring space/newlines until we hit '(', then identifier.
                    
                    # Heuristic: slice previous 500 chars
                    chunk = content[max(0, start_idx-500):start_idx]
                    
                    # Reverse chunk to search from right to left
                    rev_chunk = chunk[::-1]
                    
                    # Pattern in reverse: '(\s*eman_dohtem'
                    # i.e. find '(', then optional space ' ', then identifier 'methodname'
                    
                    # Note: We must skip comma if this is a 2nd argument.
                    # e.g. broadcastMessage(type, "String")
                    # If we hit a comma before an open paren, it's an argument.
                    # This simple lookbehind might fail for complex args, but usually works for simple calls.
                    
                    # Let's try to find the *nearest* unclosed '(' before this string?
                    # Too hard without parsing.
                    
                    # Simple Regex on reverse:
                    # Look for first '(' (which is ')' in regex if not reversed, wait. Reversed '(' is '(')
                    # No, '(' reversed is '('.
                    
                    match_method = re.search(r'^\s*[,]?\s*[\w\s,]*\(\s*(\w+)', rev_chunk) 
                    # This regex is messy.
                    
                    # Let's clean up logic:
                    # 1. Take chunk before string.
                    # 2. Find last '('
                    # 3. Check word before '('
                    
                    last_open_paren = chunk.rfind('(')
                    method_name = None
                    if last_open_paren != -1:
                        # extract text before '('
                        pre_paren = chunk[:last_open_paren].rstrip()
                        # get last word
                        m = re.search(r'(\w+)$', pre_paren)
                        if m:
                            method_name = m.group(1)
                    
                    if method_name:
                        # Check context
                        is_player = PLAYER_METHOD_PATTERN.match(method_name)
                        
                        is_log = False
                        if method_name in ['println', 'print', 'info', 'warn', 'error', 'debug', 'trace']:
                            is_log = True

                        if is_player:
                             localization_report.append({
                                'file': abs_path,
                                'line': get_line_number(content, start_idx),
                                'context': f'Method: {method_name}',
                                'original': text,
                                'type': 'player_message'
                             })
                        elif is_log and KOREAN_PATTERN.search(text):
                             localization_report.append({
                                'file': abs_path,
                                'line': get_line_number(content, start_idx),
                                'context': f'Log: {method_name}',
                                'original': text,
                                'type': 'log_message'
                             })
                        elif KOREAN_PATTERN.search(text):
                             localization_report.append({
                                'file': abs_path,
                                'line': get_line_number(content, start_idx),
                                'context': f'Unknown (Method: {method_name})',
                                'original': text,
                                'type': 'unknown_korean_string'
                             })
                    else:
                        if KOREAN_PATTERN.search(text):
                             localization_report.append({
                                'file': abs_path,
                                'line': get_line_number(content, start_idx),
                                'context': 'Variable/Other',
                                'original': text,
                                'type': 'unknown_korean_string'
                             })

    with open(OUTPUT_ENCODING, 'w', encoding='utf-8') as f:
        json.dump(encoding_report, f, indent=2)

    with open(OUTPUT_LOCALIZATION, 'w', encoding='utf-8') as f:
        json.dump(localization_report, f, indent=2)

if __name__ == '__main__':
    analyze()
