import json
import os

def generate():
    enc_data = json.load(open('encoding_report.json', encoding='utf-8'))
    loc_data = json.load(open('localization_report.json', encoding='utf-8'))

    # ENCODING REPORT
    # Filter for non-OK
    bad_enc = [x for x in enc_data if x['status'] != 'OK']
    
    with open('REPORT_Encoding.md', 'w', encoding='utf-8') as f:
        f.write('# Java Translation & Encoding Report\n\n')
        f.write('## Summary\n')
        f.write(f'- Total Files Scanned: {len(enc_data)}\n')
        f.write(f'- Files requiring UTF-8 Conversion: {len(bad_enc)}\n\n')
        
        if bad_enc:
            f.write('## Files Requiring Conversion\n')
            f.write('| File | Detected Encoding | Status | Proposed Action |\n')
            f.write('| --- | --- | --- | --- |\n')
            for item in bad_enc:
                rel_path = os.path.relpath(item['file'], os.getcwd())
                f.write(f"| `{rel_path}` | {item['encoding']} | {item['status']} | {item['proposed']} |\n")
        else:
            f.write('## Verification\nAll files are already UTF-8 without BOM.\n')

    # LOCALIZATION REPORTS
    # Split by type
    player_msgs = [x for x in loc_data if x['type'] == 'player_message']
    log_msgs = [x for x in loc_data if x['type'] == 'log_message' or x['type'] == 'korean_comment'] # treat korean comments as dev text (English)
    unknown_msgs = [x for x in loc_data if x['type'] == 'unknown_korean_string']
    
    # Helper to write table
    def write_loc_table(f, items, target_lang):
        f.write(f'| File | Line | Context | Original | Proposed Target ({target_lang}) |\n')
        f.write('| --- | --- | --- | --- | --- |\n')
        for item in items:
            rel_path = os.path.relpath(item['file'], os.getcwd())
            orig = item['original'].replace('\n', '\\n').replace('|', '\|')
            # Truncate original if too long for table
            if len(orig) > 50:
                orig = orig[:47] + '...'
            f.write(f"| `{rel_path}` | {item['line']} | {item['context']} | `{orig}` | Pending |\n")

    # Helper to check for Thai
    def has_thai(text):
        for char in text:
            if '\u0E00' <= char <= '\u0E7F':
                return True
        return False

    with open('REPORT_Localization.md', 'w', encoding='utf-8') as f:
        f.write('# Thai Game Text Localization Analysis\n\n')
        f.write('## Summary\n')
        f.write(f'- Player-Visible Messages (Total Candidates): {len(player_msgs)}\n')
        f.write(f'- Log/Dev Messages (Target: English): {len(log_msgs)}\n')
        f.write(f'- Unclassified Korean Strings (Target: TBD): {len(unknown_msgs)}\n\n')
        
        f.write('## 1. Player-Visible Messages (Thai)\n')
        f.write('> Messages detected in `sendNotice`, `dropMessage`, etc. Should be localized to immersive Thai.\n\n')
        
        f.write(f'| File | Line | Context | Original | Proposed Target (Thai) |\n')
        f.write('| --- | --- | --- | --- | --- |\n')
        for item in player_msgs:
            rel_path = os.path.relpath(item['file'], os.getcwd())
            orig = item['original'].replace('\n', '\\n').replace('|', '\|')
            # Truncate original if too long for table
            if len(orig) > 50:
                orig = orig[:47] + '...'
            
            status = 'Pending'
            if has_thai(item['original']):
                status = 'Already Thai'
            
            f.write(f"| `{rel_path}` | {item['line']} | {item['context']} | `{orig}` | {status} |\n")
        
        f.write('\n## 2. Developer/Log Messages (English)\n')
        f.write('> Korean found in logs or comments.\n\n')
        write_loc_table(f, log_msgs, "English")
        
        f.write('\n## 3. Unclassified Korean Strings\n')
        f.write('> Korean text found outside known method contexts. Requires manual review or heuristic check.\n\n')
        write_loc_table(f, unknown_msgs, "Context Dependent")

if __name__ == '__main__':
    generate()
