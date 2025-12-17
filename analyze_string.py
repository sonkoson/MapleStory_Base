
s = "เธ เธดเธฅเธ”เนŒ"
print(f"String: {s}, Length: {len(s)}")
for c in s:
    print(f"Char: {c}, U+{ord(c):04X}")

# Attempt recovery
# Map chars back to bytes if possible
b = bytearray()
valid = True
for c in s:
    try:
        # Try encoding char to cp874 (Thai Windows)
        # But we need to handle "undefined" ones if they exist
        # If the file shows "เธ เ", usually 0x81 is unprintable.
        # Maybe it's not 0x81.
        # Let's see what the print output says for the third char.
        encoded_char = c.encode('cp874')
        b.extend(encoded_char)
    except:
        print(f"Failed to encode char {c} U+{ord(c):04X} to cp874")
        valid = False
        break

if valid:
    try:
        print("Recovered bytes:", b.hex())
        decoded = b.decode('utf-8')
        print("Recovered text:", decoded)
    except Exception as e:
        print("Failed to decode bytes as utf-8:", e)
