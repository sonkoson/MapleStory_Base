# Verification Scripts - Quick Reference
**Purpose:** PowerShell scripts for encoding conversion and verification  
**Platform:** Windows PowerShell  
**Repository:** MapleStory_Base

---

## 1. Encoding Detection Script

**Purpose:** Detect current encoding of all Java files

```powershell
# Detect encoding of all .java files
Get-ChildItem -Path "." -Filter *.java -Recurse -File | ForEach-Object {
    $bytes = [System.IO.File]::ReadAllBytes($_.FullName)
    
    if ($bytes.Length -ge 3 -and $bytes[0] -eq 0xEF -and $bytes[1] -eq 0xBB -and $bytes[2] -eq 0xBF) {
        Write-Output "$($_.FullName) : UTF-8 with BOM"
    } elseif ($bytes.Length -ge 2 -and $bytes[0] -eq 0xFF -and $bytes[1] -eq 0xFE) {
        Write-Output "$($_.FullName) : UTF-16 LE"
    } elseif ($bytes.Length -ge 2 -and $bytes[0] -eq 0xFE -and $bytes[1] -eq 0xFF) {
        Write-Output "$($_.FullName) : UTF-16 BE"
    } else {
        $content = Get-Content -Path $_.FullName -Raw -Encoding UTF8 -ErrorAction SilentlyContinue
        if ($content -match '[^\x00-\x7F]') {
            Write-Output "$($_.FullName) : UTF-8 (no BOM, contains non-ASCII)"
        } else {
            Write-Output "$($_.FullName) : ASCII/UTF-8 (no BOM)"
        }
    }
}
```

**Usage:**
```powershell
cd "C:\Users\sonku\OneDrive\เอกสาร\GitHub\MapleStory_Base"
.\encoding_detection.ps1 > encoding_report.txt
```

---

## 2. UTF-8 BOM Removal Script

**Purpose:** Convert all Java files from UTF-8 with BOM to UTF-8 without BOM

```powershell
# Remove BOM from all .java files
$totalFiles = 0
$convertedFiles = 0
$errorFiles = @()

Get-ChildItem -Path "." -Filter *.java -Recurse -File | ForEach-Object {
    $totalFiles++
    $filePath = $_.FullName
    
    try {
        # Read file content as UTF-8
        $content = Get-Content -Path $filePath -Raw -Encoding UTF8
        
        # Create UTF-8 encoding without BOM
        $utf8NoBom = New-Object System.Text.UTF8Encoding $false
        
        # Write back without BOM
        [System.IO.File]::WriteAllText($filePath, $content, $utf8NoBom)
        
        $convertedFiles++
        Write-Output "✅ Converted: $filePath"
    }
    catch {
        $errorFiles += $filePath
        Write-Output "❌ ERROR: $filePath - $($_.Exception.Message)"
    }
}

Write-Output "`n========================================="
Write-Output "CONVERSION SUMMARY"
Write-Output "========================================="
Write-Output "Total files processed: $totalFiles"
Write-Output "Successfully converted: $convertedFiles"
Write-Output "Errors: $($errorFiles.Count)"

if ($errorFiles.Count -gt 0) {
    Write-Output "`nFiles with errors:"
    $errorFiles | ForEach-Object { Write-Output "  - $_" }
}
```

**Usage:**
```powershell
# IMPORTANT: Backup your repository first!
cd "C:\Users\sonku\OneDrive\เอกสาร\GitHub\MapleStory_Base"
.\remove_bom.ps1 > conversion_log.txt
```

---

## 3. UTF-8 BOM Verification Script

**Purpose:** Verify no BOM exists after conversion

```powershell
# Verify no BOM in .java files
$filesWithBOM = @()
$filesChecked = 0

Get-ChildItem -Path "." -Filter *.java -Recurse -File | ForEach-Object {
    $filesChecked++
    $bytes = [System.IO.File]::ReadAllBytes($_.FullName)
    
    if ($bytes.Length -ge 3 -and $bytes[0] -eq 0xEF -and $bytes[1] -eq 0xBB -and $bytes[2] -eq 0xBF) {
        $filesWithBOM += $_.FullName
        Write-Output "❌ BOM FOUND: $($_.FullName)"
    }
}

Write-Output "`n========================================="
Write-Output "VERIFICATION SUMMARY"
Write-Output "========================================="
Write-Output "Total files checked: $filesChecked"
Write-Output "Files with BOM: $($filesWithBOM.Count)"

if ($filesWithBOM.Count -eq 0) {
    Write-Output "`n✅ SUCCESS: No BOM found in any Java files!"
} else {
    Write-Output "`n❌ WARNING: BOM found in $($filesWithBOM.Count) files:"
    $filesWithBOM | ForEach-Object { Write-Output "  - $_" }
}
```

**Usage:**
```powershell
cd "C:\Users\sonku\OneDrive\เอกสาร\GitHub\MapleStory_Base"
.\verify_no_bom.ps1
```

---

## 4. Korean Text Detection Script

**Purpose:** Find all Java files containing Korean characters

```powershell
# Find all .java files with Korean text
$filesWithKorean = @()
$totalFiles = 0
$totalMatches = 0

Get-ChildItem -Path "." -Filter *.java -Recurse -File | ForEach-Object {
    $totalFiles++
    $content = Get-Content -Path $_.FullName -Raw -Encoding UTF8 -ErrorAction SilentlyContinue
    
    if ($content -match '[가-힣]') {
        $filesWithKorean += $_.FullName
        $matches = [regex]::Matches($content, '[가-힣]+')
        $totalMatches += $matches.Count
        Write-Output "📝 Korean found: $($_.FullName) ($($matches.Count) occurrences)"
    }
}

Write-Output "`n========================================="
Write-Output "KOREAN TEXT DETECTION SUMMARY"
Write-Output "========================================="
Write-Output "Total Java files: $totalFiles"
Write-Output "Files with Korean: $($filesWithKorean.Count)"
Write-Output "Total Korean occurrences: $totalMatches"

if ($filesWithKorean.Count -eq 0) {
    Write-Output "`n✅ SUCCESS: No Korean text found!"
} else {
    Write-Output "`n⚠️ Korean text found in $($filesWithKorean.Count) files"
}
```

**Usage:**
```powershell
cd "C:\Users\sonku\OneDrive\เอกสาร\GitHub\MapleStory_Base"
.\detect_korean.ps1 > korean_files.txt
```

---

## 5. Complete Verification Script

**Purpose:** Run all verifications in sequence

```powershell
# Complete verification suite
Write-Output "========================================="
Write-Output "JAVA FILE VERIFICATION SUITE"
Write-Output "========================================="
Write-Output "Repository: MapleStory_Base"
Write-Output "Date: $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')"
Write-Output ""

# 1. Count total Java files
$javaFiles = Get-ChildItem -Path "." -Filter *.java -Recurse -File
Write-Output "1. TOTAL JAVA FILES: $($javaFiles.Count)"
Write-Output ""

# 2. Check for BOM
Write-Output "2. CHECKING FOR BOM..."
$filesWithBOM = 0
$javaFiles | ForEach-Object {
    $bytes = [System.IO.File]::ReadAllBytes($_.FullName)
    if ($bytes.Length -ge 3 -and $bytes[0] -eq 0xEF -and $bytes[1] -eq 0xBB -and $bytes[2] -eq 0xBF) {
        $filesWithBOM++
    }
}
Write-Output "   Files with BOM: $filesWithBOM"
if ($filesWithBOM -eq 0) {
    Write-Output "   ✅ No BOM found"
} else {
    Write-Output "   ❌ BOM found in $filesWithBOM files"
}
Write-Output ""

# 3. Check for Korean text
Write-Output "3. CHECKING FOR KOREAN TEXT..."
$filesWithKorean = 0
$koreanOccurrences = 0
$javaFiles | ForEach-Object {
    $content = Get-Content -Path $_.FullName -Raw -Encoding UTF8 -ErrorAction SilentlyContinue
    if ($content -match '[가-힣]') {
        $filesWithKorean++
        $matches = [regex]::Matches($content, '[가-힣]+')
        $koreanOccurrences += $matches.Count
    }
}
Write-Output "   Files with Korean: $filesWithKorean"
Write-Output "   Korean occurrences: $koreanOccurrences"
if ($filesWithKorean -eq 0) {
    Write-Output "   ✅ No Korean text found"
} else {
    Write-Output "   ⚠️ Korean text found in $filesWithKorean files"
}
Write-Output ""

# 4. Summary
Write-Output "========================================="
Write-Output "VERIFICATION SUMMARY"
Write-Output "========================================="
Write-Output "Total Java files: $($javaFiles.Count)"
Write-Output "Files with BOM: $filesWithBOM"
Write-Output "Files with Korean: $filesWithKorean"
Write-Output "Korean occurrences: $koreanOccurrences"
Write-Output ""

if ($filesWithBOM -eq 0 -and $filesWithKorean -eq 0) {
    Write-Output "✅ ALL CHECKS PASSED!"
    Write-Output "   - No BOM found"
    Write-Output "   - No Korean text found"
    Write-Output "   - Repository is ready for deployment"
} else {
    Write-Output "⚠️ ACTION REQUIRED:"
    if ($filesWithBOM -gt 0) {
        Write-Output "   - Run BOM removal script"
    }
    if ($filesWithKorean -gt 0) {
        Write-Output "   - Complete Korean text translation"
    }
}
```

**Usage:**
```powershell
cd "C:\Users\sonku\OneDrive\เอกสาร\GitHub\MapleStory_Base"
.\complete_verification.ps1
```

---

## 6. Backup Script

**Purpose:** Create backup before making changes

```powershell
# Create timestamped backup
$timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
$backupDir = "backup_$timestamp"
$sourceDir = "."

Write-Output "Creating backup..."
Write-Output "Source: $sourceDir"
Write-Output "Destination: $backupDir"

# Create backup directory
New-Item -ItemType Directory -Path $backupDir -Force | Out-Null

# Copy all Java files
Get-ChildItem -Path $sourceDir -Filter *.java -Recurse -File | ForEach-Object {
    $relativePath = $_.FullName.Substring((Get-Location).Path.Length + 1)
    $destPath = Join-Path $backupDir $relativePath
    $destDir = Split-Path $destPath -Parent
    
    # Create directory structure
    if (-not (Test-Path $destDir)) {
        New-Item -ItemType Directory -Path $destDir -Force | Out-Null
    }
    
    # Copy file
    Copy-Item -Path $_.FullName -Destination $destPath -Force
}

Write-Output "✅ Backup completed: $backupDir"
Write-Output "Total files backed up: $(Get-ChildItem -Path $backupDir -Filter *.java -Recurse -File | Measure-Object | Select-Object -ExpandProperty Count)"
```

**Usage:**
```powershell
cd "C:\Users\sonku\OneDrive\เอกสาร\GitHub\MapleStory_Base"
.\create_backup.ps1
```

---

## Recommended Execution Order

### Before Encoding Conversion
1. Run **Backup Script** (create safety backup)
2. Run **Encoding Detection Script** (baseline)
3. Run **Korean Text Detection Script** (baseline)

### Encoding Conversion
4. Run **UTF-8 BOM Removal Script**
5. Run **UTF-8 BOM Verification Script** (should show 0 files with BOM)

### After Translation
6. Run **Korean Text Detection Script** (should show 0 files with Korean)
7. Run **Complete Verification Script** (final check)

---

## Safety Notes

⚠️ **IMPORTANT:**
1. **Always backup** before running conversion scripts
2. **Test on a small subset** first (e.g., one directory)
3. **Verify results** before committing to repository
4. **Use version control** (Git) to track changes
5. **Keep backups** until verification is complete

---

## Troubleshooting

### Issue: Script execution disabled
**Error:** `cannot be loaded because running scripts is disabled`

**Solution:**
```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
```

### Issue: Path contains special characters
**Error:** Path encoding issues

**Solution:** Use absolute paths with proper escaping:
```powershell
cd "C:\Users\sonku\OneDrive\เอกสาร\GitHub\MapleStory_Base"
```

### Issue: File in use
**Error:** `The process cannot access the file`

**Solution:** Close IDE/editor and retry

---

## Quick Commands

### Count Java files
```powershell
(Get-ChildItem -Path "." -Filter *.java -Recurse -File).Count
```

### Find files with BOM
```powershell
Get-ChildItem -Path "." -Filter *.java -Recurse -File | Where-Object {
    $bytes = [System.IO.File]::ReadAllBytes($_.FullName)
    $bytes.Length -ge 3 -and $bytes[0] -eq 0xEF -and $bytes[1] -eq 0xBB -and $bytes[2] -eq 0xBF
} | Select-Object FullName
```

### Find files with Korean
```powershell
Get-ChildItem -Path "." -Filter *.java -Recurse -File | Where-Object {
    (Get-Content -Path $_.FullName -Raw -Encoding UTF8) -match '[가-힣]'
} | Select-Object FullName
```

---

**Last Updated:** 2025-12-16 00:53:19 +07:00  
**Repository:** MapleStory_Base
