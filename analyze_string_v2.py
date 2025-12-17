
import sys
# Avoid encoding errors on print
sys.stdout.reconfigure(encoding='utf-8')

s = "เธ เธดเธฅเธ”เนŒ"
print(f"Length: {len(s)}")
for c in s:
    print(f"U+{ord(c):04X}")

b = bytearray()
for c in s:
    # We suspect Windows-1252 or similar usage
    # If U+0E40 (เ) corresponds to 0xE0
    # If U+0152 (Œ) corresponds to 0x8C (in cp1252)
    # Let's try to map back to bytes using cp1252?
    # Wait, 'เ' is NOT in cp1252. 'เ' is in cp874.
    
    # If the file IS Mojibake, it means:
    # Original (UTF-8) bytes -> Read as CP874 -> Yields Thai chars.
    # Ex: 0xE0 -> เ
    # Ex: 0xB8 -> ธ
    # But 0x81 -> Undefined in CP874.
    
    # However, if we see 'Œ', that's 0x8C in CP1252.
    # This suggests MIXED encoding interpretation or "cp1252" was used instead of "cp874" for some bytes?
    # Or maybe Python's "cp874" has holes, but Windows "cp874" maps 0x80-0x9F to something?
    
    # Let's try to determine the byte based on common encodings.
    # If c is in Thai block (0E00-0E7F), it maps to A1-FE in CP874 (mostly).
    # If c is 'Œ' (0152), it maps to 8C in CP1252.
    
    val = -1
    try:
        # Try cp874 first
        bs = c.encode('cp874')
        if len(bs) == 1:
            val = bs[0]
    except:
        pass
        
    if val == -1:
        try:
            # Try cp1252
            bs = c.encode('cp1252')
            if len(bs) == 1:
                val = bs[0]
        except:
            pass
            
    if val != -1:
        b.append(val)
        print(f"Mapped U+{ord(c):04X} -> 0x{val:02X}")
    else:
        print(f"Could not map U+{ord(c):04X}")

print(" recovered bytes:", b.hex())
try:
    print(" decoded utf-8:", b.decode('utf-8'))
except Exception as e:
    print(" decode failed:", e)
