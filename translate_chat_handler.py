
import os

filepath = "src/network/game/processors/ChatHandler.java"

with open(filepath, 'r', encoding='utf-8') as f:
    content = f.read()

replacements = [
    ('text.equals("지์— 권한받기")', 'text.equals("@gmperm")'),
    ('"20세 이상"', '"20+"'),
    ('"20세 이상"', '"20+"'), # Duplicate just in case
    ('ขณะนี้ไม่สามาöใช้งานแชทได้ กรسาลองใหม่ในเธ ายหลัง', 'ขณะนี้ไม่สามารถใช้งานแชทได้ กรุณาลองใหม่ในภายหลัง'),
    ('สามาö', 'สามารถ'), # Safe fallback
]

for old, new_str in replacements:
    content = content.replace(old, new_str)

with open(filepath, 'w', encoding='utf-8') as f:
    f.write(content)

print(f"Translated {filepath}")
