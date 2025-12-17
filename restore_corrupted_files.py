
import os
import shutil

# Paths
PROJECT_ROOT = r"c:\Users\sonku\OneDrive\เอกสาร\GitHub\MapleStory_Base"
SRC_DIR = os.path.join(PROJECT_ROOT, "src")
BACKUP_DIR = os.path.join(PROJECT_ROOT, "backup_java_20251216_010217", "src")

# Mojibake markers based on user report and previous findings
# These are character sequences that shouldn't appear in valid Thai/English/Java
MOJIBAKE_MARKERS = [
    "์ž", "์—", "์ค", "๋", "๊", "เนŒ", "เนŠ", "เน\xa0", 
    "à¸", "à¹", "ð", "€", "š", "’", "“", "”", "•", "–", "—", "˜", "™", "š", "›", "œ", "ž", "Ÿ",
    "InsertOrUpdate ์", "InsertOrUpdateBatch ์"
]

def is_corrupted(content):
    # Check for specific mojibake sequences
    for marker in MOJIBAKE_MARKERS:
        if marker in content:
            return True, marker
    return False, None

def restore_files():
    restored_count = 0
    scanned_count = 0
    
    print(f"Scanning {SRC_DIR} for corrupted files...")
    
    for root, dirs, files in os.walk(SRC_DIR):
        for file in files:
            if file.endswith(".java"):
                scanned_count += 1
                current_path = os.path.join(root, file)
                
                # Get relative path to find backup
                rel_path = os.path.relpath(current_path, SRC_DIR)
                backup_path = os.path.join(BACKUP_DIR, rel_path)
                
                try:
                    with open(current_path, 'r', encoding='utf-8', errors='replace') as f:
                        content = f.read()
                        
                    corrupted, marker = is_corrupted(content)
                    
                    if corrupted:
                        if os.path.exists(backup_path):
                            print(f"[CORRUPTED] {rel_path} (Detected '{marker}')")
                            print(f"  -> Restoring from backup...")
                            
                            # Read backup to ensure it's readable (heuristic)
                            with open(backup_path, 'r', encoding='utf-8', errors='replace') as bf:
                                backup_content = bf.read()
                                
                            # Safe copy
                            shutil.copy2(backup_path, current_path)
                            restored_count += 1
                        else:
                            print(f"[WARNING] {rel_path} is corrupted ('{marker}') but NO BACKUP found.")
                            
                except Exception as e:
                    print(f"Error processing {current_path}: {e}")

    print(f"Scan complete. Scanned {scanned_count} files. Restored {restored_count} files.")

if __name__ == "__main__":
    restore_files()
