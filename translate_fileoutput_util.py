
import re

file_path = 'src/objects/utils/FileoutputUtil.java'

with open(file_path, 'r', encoding='utf-8') as f:
    content = f.read()

# Fix Korean string: "에러원인 : " -> "Error Cause : "
content = content.replace('"에러원인 : "', '"Error Cause : "')

with open(file_path, 'w', encoding='utf-8') as f:
    f.write(content)

print(f"Processed {file_path}")
