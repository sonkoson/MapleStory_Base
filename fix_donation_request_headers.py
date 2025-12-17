
import os

file_path = os.path.join("src", "api", "DonationRequest.java")

with open(file_path, 'r', encoding='utf-8') as f:
    lines = f.readlines()

for i, line in enumerate(lines):
    if 'new String[]{"id", "' in line and "เน" in line:
        print(f"Replacing line {i+1}")
        lines[i] = '             new String[]{"id", "Date", "Account Name", "Player Name", "Real Name", "Point", "Type", "Status"}\n'
        
    if 'this.jLabel1.setText(' in line and 'เน' in line:
        lines[i] = '       this.jLabel1.setText("Search Character Name -");\n'
        
    if 'this.jButton1.setText(' in line and 'เน' in line:
        lines[i] = '       this.jButton1.setText("Search");\n'
        
    if 'this.jTextField1.setFont(' in line and 'เน' in line:
        # Keep original logic but maybe safe font
        pass 
        
    if 'this.jTextField1.setText(' in line and 'เน' in line:
         lines[i] = '       this.jTextField1.setText("Enter Name");\n'

    if 'this.jButton2.setText(' in line and 'เน' in line:
         lines[i] = '       this.jButton2.setText("Refuse");\n'
         
    if 'this.jButton3.setText(' in line and 'เน' in line:
         lines[i] = '       this.jButton3.setText("Pending");\n'

    if 'this.jButton4.setText(' in line and 'เน' in line:
         lines[i] = '       this.jButton4.setText("Approve");\n'

with open(file_path, 'w', encoding='utf-8') as f:
    f.writelines(lines)

print("Done.")
